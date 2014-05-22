/**
 * Title:        MsdsSpecsLogic
 * Description:  This is the business logic class for the MsdsSpecsAction and MsdsSpecsForm.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Yuriy Kupershmidt
 */

package com.cleanwise.view.logic;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Category;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.APIServiceAccessException;
import com.cleanwise.service.api.session.Country;
import com.cleanwise.service.api.session.ItemInformation;
import com.cleanwise.service.api.session.MsdsSpecs;
import com.cleanwise.service.api.session.ShoppingServices;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.AccountData;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.BusEntityDataVector;
import com.cleanwise.service.api.value.CatalogCategoryData;
import com.cleanwise.service.api.value.CatalogCategoryDataVector;
import com.cleanwise.service.api.value.CountryData;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.ItemData;
import com.cleanwise.service.api.value.ItemDataDocUrlsView;
import com.cleanwise.service.api.value.ItemDataDocUrlsViewVector;
import com.cleanwise.service.api.value.ItemDataVector;
import com.cleanwise.service.api.value.ProductData;
import com.cleanwise.service.api.value.ProductDataVector;
import com.cleanwise.service.api.value.PropertyData;
import com.cleanwise.service.api.value.ShoppingCartItemData;
import com.cleanwise.service.api.value.ShoppingCartItemDataVector;
import com.cleanwise.service.api.value.ShoppingItemRequest;
import com.cleanwise.service.api.value.StoreData;
import com.cleanwise.view.forms.MsdsSpecsForm;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.SessionTool;
import com.cleanwise.view.utils.ShopTool;

/**
 * Class description.
 *
 */
public class MsdsSpecsLogic {
	
	private static final Category log = Category.getInstance(MsdsSpecsLogic.class);

    public static ActionErrors init(HttpServletRequest request, MsdsSpecsForm pForm) {

        ActionErrors ae = new ActionErrors();

        HttpSession session = request.getSession();
        pForm.setContentPage(0);
        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);

        if (factory == null) {
            ae.add("error", new ActionError("error.systemError", "No Ejb access"));
            return ae;
        }

        ShoppingServices shoppingServEjb = null;
        try {
            shoppingServEjb = factory.getShoppingServicesAPI();
        } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
            ae.add("error", new ActionError("error.systemError", "No shopping services Ejb pointer"));
            return ae;
        }

        MsdsSpecs msdsSpecsEjb = null;
        try {
            msdsSpecsEjb = factory.getMsdsSpecsAPI();
        } catch (APIServiceAccessException exc) {
            ae.add("error", new ActionError("error.systemError", "No MsdsSpecs Ejb access"));
            return ae;
        }

        ItemInformation itemInformationEjb = null;
        try {
            itemInformationEjb = factory.getItemInformationAPI();
        } catch (APIServiceAccessException exc) {
            exc.printStackTrace();
            ae.add("error", new ActionError("error.systemError", "No ItemInformation Ejb access"));
            return ae;
        }

        Country countryEjb = null;

        try {
        	countryEjb = factory.getCountryAPI();
        } catch (APIServiceAccessException exc) {
            exc.printStackTrace();
            ae.add("error",
                    new ActionError("error.systemError",
                    "No country Ejb access"));

            return ae;
        }
        
        ae = init(request, pForm, shoppingServEjb);
        if (ae.size() > 0) {
            return ae;
        }

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        if (appUser == null) {
            ae.add("error", new ActionError("error.systemError", "No " + Constants.APP_USER + " session object found"));
            return ae;
        }

        StoreData store = appUser.getUserStore();
        if (store == null) {
            ae.add("error", new ActionError("error.systemError", "No store information was loaded"));
            return ae;
        }

        AccountData account = appUser.getUserAccount();
        if (account == null) {
            ae.add("error", new ActionError("error.systemError", "No account information was loaded"));
            return ae;
        }

        Integer catalogId = (Integer) session.getAttribute(Constants.CATALOG_ID);
        if (catalogId == null) {
            ae.add("error", new ActionError("error.systemError", "No " + Constants.CATALOG_ID + " session attrbute found"));
            return ae;
        }

        Integer contractIdI = (Integer) session.getAttribute(Constants.CONTRACT_ID);

        int contractId = Utility.intNN(contractIdI);
        if (appUser.isUserOnContract() && contractId == 0) {
            ae.add("error", new ActionError("error.systemError", "There is no contract found"));
            return ae;
        }

        int accountCatalogId = account.getAccountCatalogId();
        if (appUser.isSpecialPermissionRequired() && accountCatalogId == 0) {
            ae.add("error", new ActionError("error.systemError", "There is no accaunt catalog found"));
            return ae;
        }

        PropertyData storeTypeProperty = store.getStoreType();
        if (storeTypeProperty == null) {
            ae.add("error", new ActionError("error.systemError", "No store type information was loaded"));
            return ae;
        }

        //select manufacturers
        BusEntityDataVector manufacturers;
        ItemDataVector categories;
        try {

          manufacturers = shoppingServEjb.getCatalogManufacturers(ShopTool.createShoppingItemRequest(request));
          categories = shoppingServEjb.getCatalogCategories(ShopTool.createShoppingItemRequest(request));

        } catch (Exception exc) {
            exc.printStackTrace();
            ae.add("error", new ActionError("error.systemError", exc.getMessage()));
            return ae;
        }

        pForm.setManufacturers(manufacturers);

        ArrayList<String> menuMfgLables = new ArrayList<String>();
        ArrayList<String> menuMfgOptions = new ArrayList<String>();

        menuMfgLables.add("");
        menuMfgOptions.add("0");
        for (Object manufacturer : manufacturers) {
            BusEntityData beD = (BusEntityData) manufacturer;
            menuMfgLables.add(beD.getShortDesc());
            menuMfgOptions.add("" + beD.getBusEntityId());
        }
        pForm.setMenuMfgLabels(menuMfgLables);
        pForm.setMenuMfgOptions(menuMfgOptions);

        pForm.setCategories(categories);

        ArrayList<String> menuCategoryLables = new ArrayList<String>();
        ArrayList<String> menuCategoryOptions = new ArrayList<String>();

        menuCategoryLables.add("");
        menuCategoryOptions.add("0");
        for (Object category : categories) {
            ItemData iD = (ItemData) category;
            menuCategoryLables.add(iD.getShortDesc());
            menuCategoryOptions.add("" + iD.getItemId());
        }
        pForm.setMenuCategoryLabels(menuCategoryLables);
        pForm.setMenuCategoryOptions(menuCategoryOptions);

        //Select stored documents
        IdVector msdsItemIds = null;
        IdVector specItemIds = null;
        IdVector dedItemIds = null;
        int siteId = appUser.getSite().getBusEntity().getBusEntityId();

        String userLocale = appUser.getUser().getPrefLocaleCd().trim();
        
        //String countryCode = appUser.getSite().getSiteAddress().getCountryCd();
        String countryShortDesc = appUser.getSite().getSiteAddress().getCountryCd();
        log.info("countryShortDesc = " + countryShortDesc);
        CountryData countryData = null;
        try {
            countryData = countryEjb.getCountryByShortDesc(countryShortDesc);
        } catch (Exception exc) {
            exc.printStackTrace();
            ae.add("error", new ActionError("error.systemError", exc.getMessage()));
            return ae;
        }   
        String countryCode = countryData.getCountryCode().trim();
        log.info("countryCode = " + countryCode);
        
        String storeLocale = appUser.getUserStore().getBusEntity().getLocaleCd().trim(); 
        
        /**
         * Added new code to select ordered items (with meta info) for
         * last year. BEGIN
         */
        if (true) {
            try {
            	log.info("before calling itemInformationEjb.getOrderedItemDataForLastYear()");
            	//ItemDataDocUrlsViewVector docUrlsV = itemInformationEjb.getOrderedItemDataForLastYear(siteId); //old method
            	ItemDataDocUrlsViewVector docUrlsV = itemInformationEjb.getOrderedItemDataForLastYear(siteId, userLocale, countryCode, storeLocale); //new method
                sortDocUrlsAndFillForm(pForm, docUrlsV);
            } catch (Exception exc) {
                exc.printStackTrace();
                ae.add("error", new ActionError("error.systemError", exc.getMessage()));
                return ae;
            }
            /**
             * Added new code to select ordered items (with meta info) for
             * last year. END
             */
        } else {
        	try {
                msdsItemIds = msdsSpecsEjb.getItemsDocCloset(siteId, "MSDS");
                specItemIds = msdsSpecsEjb.getItemsDocCloset(siteId, "SPEC");
                dedItemIds = msdsSpecsEjb.getItemsDocCloset(siteId, "DED");
            } catch (Exception exc) {
                exc.printStackTrace();
                ae.add("error", new ActionError("error.systemError", exc.getMessage()));
                return ae;
            }

            IdVector itemIds = mergeIds(msdsItemIds, specItemIds, dedItemIds);
            ShoppingCartItemDataVector shoppingCartItemDV = null;
            try {
                shoppingCartItemDV = shoppingServEjb.prepareShoppingItems(storeTypeProperty.getValue(),
                        appUser.getSite(),
                        catalogId,
                        contractId,
                        itemIds,
                        SessionTool.getCategoryToCostCenterView(session, appUser.getSite().getSiteId()));
            } catch (Exception exc) {
                exc.printStackTrace();
                ae.add("error", new ActionError("error.systemError", exc.getMessage()));
                return ae;
            }

            ShoppingCartItemDataVector msdsVector = new ShoppingCartItemDataVector();
            for (int ii = 0, jj = 0; ii < shoppingCartItemDV.size() && jj < msdsItemIds.size(); ii++) {
                int id = (Integer) msdsItemIds.get(jj);
                ShoppingCartItemData sciD = (ShoppingCartItemData) shoppingCartItemDV.get(ii);
                if (id == sciD.getProduct().getProductId()) {
                    msdsVector.add(sciD);
                    jj++;
                }
            }
            msdsVector = orderByName(msdsVector);
            pForm.setMyMsds(msdsVector);

            ShoppingCartItemDataVector specVector = new ShoppingCartItemDataVector();
            for (int ii = 0, jj = 0; ii < shoppingCartItemDV.size() && jj < specItemIds.size(); ii++) {
                int id = (Integer) specItemIds.get(jj);
                ShoppingCartItemData sciD = (ShoppingCartItemData) shoppingCartItemDV.get(ii);
                if (id == sciD.getProduct().getProductId()) {
                    specVector.add(sciD);
                    jj++;
                }
            }
            specVector = orderByName(specVector);
            pForm.setMySpec(specVector);

            ShoppingCartItemDataVector dedVector = new ShoppingCartItemDataVector();
            for (int ii = 0, jj = 0; ii < shoppingCartItemDV.size() && jj < dedItemIds.size(); ii++) {
                int id = (Integer) dedItemIds.get(jj);
                ShoppingCartItemData sciD = (ShoppingCartItemData) shoppingCartItemDV.get(ii);
                if (id == sciD.getProduct().getProductId()) {
                    dedVector.add(sciD);
                    jj++;
                }
            }
            dedVector = orderByName(dedVector);
            pForm.setMyDed(dedVector);
        }

        pForm.setProductList(new LinkedList());

        return ae;
    }

  //*****************************************************************************
  private static IdVector mergeIds(IdVector msdsItemIds, IdVector specItemIds, IdVector dedItemIds)
  {
    IdVector itemIds = new IdVector();
    int msdsSize = msdsItemIds.size();
    int specSize = specItemIds.size();
    int dedSize = dedItemIds.size();
    int msdsId = 0;
    int specId = 0;
    int dedId =0;
    int msdsMax = (msdsSize>0)?((Integer)msdsItemIds.get(msdsSize-1)).intValue():0;
    int specMax = (specSize>0)?((Integer)specItemIds.get(specSize-1)).intValue():0;
    int dedMax = (dedSize>0)?((Integer)dedItemIds.get(dedSize-1)).intValue():0;
    int max = (msdsMax>=specMax)?msdsMax:specMax;
    if(max<dedMax) max = dedMax;
    max++;
    for(int ii=0,jj=0,kk=0; ii<msdsSize || jj<specSize || kk<dedSize; ) {
      msdsId = (ii<msdsSize)?((Integer)msdsItemIds.get(ii)).intValue():max;
      specId = (jj<specSize)?((Integer)specItemIds.get(jj)).intValue():max;
      dedId = (kk<dedSize)?((Integer)dedItemIds.get(kk)).intValue():max;
      if(msdsId<=specId && msdsId<=dedId) {
        itemIds.add(new Integer(msdsId));
        ii++;
        if(msdsId==specId) jj++;
        if(msdsId==dedId) kk++;
      } else if(specId<=dedId) {
        itemIds.add(new Integer(specId));
        jj++;
        if(specId==dedId) kk++;
      } else {
        itemIds.add(new Integer(dedId));
        kk++;
      }
    }
    return itemIds;
  }
  //*****************************************************************************
  private static ShoppingCartItemDataVector orderByName(ShoppingCartItemDataVector pItems) {
    //return if nothing to sord
    if(pItems==null) {
      return new ShoppingCartItemDataVector();
    }
    if(pItems.size()<=1) {
      return pItems;
    }
    //Order now
    ShoppingCartItemData[] itemArray = new ShoppingCartItemData[pItems.size()];
    for(int ii=0; ii<pItems.size(); ii++) {
      ShoppingCartItemData sciD = (ShoppingCartItemData) pItems.get(ii);
      itemArray[ii] = sciD;
    }
    for(int ii=0; ii<itemArray.length-1; ii++) {
      boolean exitFlag = true;
      for(int jj=1; jj<itemArray.length-ii; jj++) {
        ShoppingCartItemData item1 = itemArray[jj-1];
        ShoppingCartItemData item2 = itemArray[jj];
        String ss1 = item1.getProduct().getCatalogProductShortDesc();
//        ss1 += item1.getActualSkuNum();
        String ss2 = item2.getProduct().getCatalogProductShortDesc();
//        ss2 += item2.getActualSkuNum();
        if(ss1.compareToIgnoreCase(ss2)>0) {
          itemArray[jj-1] = item2;
          itemArray[jj] = item1;
          exitFlag = false;
        }
      }
      if(exitFlag) break;
    }
    ShoppingCartItemDataVector items = new ShoppingCartItemDataVector();
    for(int ii=0; ii<itemArray.length; ii++) {
      items.add(itemArray[ii]);
    }
    return items;
  }

    //*****************************************************************************
    private static ActionErrors init(HttpServletRequest request, MsdsSpecsForm pForm, ShoppingServices shoppingServEjb) {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        if (appUser == null) {
            ae.add("error", new ActionError("error.systemError", "No " + Constants.APP_USER + " session object found"));
            return ae;
        }

        ShoppingItemRequest shoppingItemRequest = ShopTool.createShoppingItemRequest(request);
        try {
            int[] categoryIds = shoppingServEjb.getShoppingCategoryIds(shoppingItemRequest);
            pForm.setContractCategoryIds(categoryIds);
        } catch (RemoteException exc) {
            ae.add("error", new ActionError("error.systemError", "Can't pickup shopping categories." +
                    " Contract id = " + shoppingItemRequest.getContractId() +
                    " Shopping Catalog id = " + shoppingItemRequest.getShoppingCatalogId() +
                    " Account Catalog id = " + shoppingItemRequest.getAccountCatalogId()));
            return ae;
        }

        try {
            int[] itemIds = shoppingServEjb.getShoppingItemIds(shoppingItemRequest);
            pForm.setContractItemIds(itemIds);
        } catch (RemoteException exc) {
            ae.add("error", new ActionError("error.systemError", "Can't pickup shopping items." +
                    " Contract id = " + shoppingItemRequest.getContractId() +
                    " Shopping Catalog id = " + shoppingItemRequest.getShoppingCatalogId() +
                    " Account Catalog id = " + shoppingItemRequest.getAccountCatalogId()));
            return ae;
        }

        return ae;
    }


  //*****************************************************************************
  /*
  */
  private static CatalogCategoryDataVector filterCategoriesByContract(MsdsSpecsForm pForm, CatalogCategoryDataVector pCategories) {
    int filter[] = pForm.getContractCategoryIds();
    CatalogCategoryDataVector filteredCategories = new CatalogCategoryDataVector();
    for(int ii=0; ii<pCategories.size(); ii++) {
      CatalogCategoryData categoryD = (CatalogCategoryData) pCategories.get(ii);
      int id = categoryD.getCatalogCategoryId();
      for(int jj=0; jj<filter.length; jj++) {
        if(id==filter[jj]) {
          filteredCategories.add(categoryD);
          break;
        }
        if(id<filter[jj]) {
          break;
        }
      }
    }
    return filteredCategories;
  }
  /*
  */
  private static ProductDataVector filterProductsByContract(MsdsSpecsForm pForm, ProductDataVector pProducts) {
    int filter[] = pForm.getContractItemIds();
    ProductDataVector filteredProducts = new ProductDataVector();
    for(int ii=0; ii<pProducts.size(); ii++) {
      ProductData productD = (ProductData) pProducts.get(ii);
      int id = productD.getProductId();
      for(int jj=0; jj<filter.length; jj++) {
        if(id==filter[jj]) {
          filteredProducts.add(productD);
          break;
        }
        if(id<filter[jj]) {
          break;
        }
      }
    }
    return filteredProducts;
  }
  //*****************************************************************************
  public static ActionErrors sortCatalogItems(HttpServletRequest request, MsdsSpecsForm pForm)
  {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    Integer catalogIdI = (Integer) session.getAttribute(Constants.CATALOG_ID);
    if(catalogIdI==null) {
      ae.add("error",new ActionError("error.systemError","No "+Constants.CATALOG_ID+" session object found"));
      return ae;
    }
    int catalogId = catalogIdI.intValue();

    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    if(factory==null) {
      ae.add("error",new ActionError("error.systemError","No Ejb access"));
      return ae;
    }
    ShoppingServices shoppingServEjb = null;
    try {
      shoppingServEjb = factory.getShoppingServicesAPI();
    } catch(com.cleanwise.service.api.APIServiceAccessException exc) {
      exc.printStackTrace();
      ae.add("error",new ActionError("error.systemError","No shopping services Ejb pointer"));
      return ae;
    }

    CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);
    if(appUser==null) {
      ae.add("error",new ActionError("error.systemError","No "+Constants.APP_USER+" session object found"));
      return ae;
    }
    StoreData store = appUser.getUserStore();
    if(store==null) {
      ae.add("error",new ActionError("error.systemError","No store information was loaded"));
      return ae;
    }
    PropertyData storeTypeProperty = store.getStoreType();
    if(storeTypeProperty==null) {
      ae.add("error",new ActionError("error.systemError","No store type information was loaded"));
      return ae;
    }

    List items = pForm.getProductList();
    if(items==null || items.size()<=1) {
      return ae;
    }

    List cart= pForm.getCartItems();

    Object item = items.get(0);
    int sortBy = pForm.getOrderBy();

    ae = sortMsds(request,pForm,cart,sortBy);

    /*
    if(item instanceof Integer) {
      IdVector itemIdV = new IdVector();
      for(int ii=0; ii<items.size(); ii++) {
        itemIdV.add(items.get(ii));
      }
      try {

        itemIdV = shoppingServEjb.sortItems(storeTypeProperty.getValue(),catalogId,itemIdV,sortBy);
      } catch(RemoteException exc) {
        ae.add("error",new ActionError("error.systemError",exc.getMessage()));
        exc.printStackTrace();
        return ae;
      }

      pForm.setOffset(0);
      pForm.setProductList(itemIdV);
      ae = prepareCartItems(request,pForm);
      if(ae.size()>0) {
        return ae;
      }
    }
*/
    return ae;
  }

  //*************************sort func*****************

  private static ActionErrors sortMsds(HttpServletRequest request,MsdsSpecsForm pForm, List sList, int sSortBy){

	  final Comparator CompCategory = new Comparator() {

			public int compare(Object o1, Object o2) {
				ShoppingCartItemData s1 = (ShoppingCartItemData) o1;
				ShoppingCartItemData s2 = (ShoppingCartItemData) o2;

				return s1.getCategoryName().compareTo(s2.getCategoryName());
			}
	  };

	  final Comparator CompSkuNum = new Comparator() {

			public int compare(Object o1, Object o2) {
				ShoppingCartItemData s1 = (ShoppingCartItemData) o1;
				ShoppingCartItemData s2 = (ShoppingCartItemData) o2;
				int SN1 = Integer.parseInt(s1.getActualSkuNum());
				int SN2 = Integer.parseInt(s2.getActualSkuNum());

				if(SN1>SN2)
					return 1;
				else
					return -1;
			}
	  };

	  final Comparator CompProductName = new Comparator() {

			public int compare(Object o1, Object o2) {
				ShoppingCartItemData s1 = (ShoppingCartItemData) o1;
				ShoppingCartItemData s2 = (ShoppingCartItemData) o2;

				return s1.getItemDesc().compareTo(s2.getItemDesc());
			}
	  };

	  final Comparator CompManufName = new Comparator() {

			public int compare(Object o1, Object o2) {
				ShoppingCartItemData s1 = (ShoppingCartItemData) o1;
				ShoppingCartItemData s2 = (ShoppingCartItemData) o2;

				return s1.getProduct().getManufacturerName().compareTo(s2.getProduct().getManufacturerName());
			}
	  };

	  final Comparator CompManufSkuNum = new Comparator() {

			public int compare(Object o1, Object o2) {
				ShoppingCartItemData s1 = (ShoppingCartItemData) o1;
				ShoppingCartItemData s2 = (ShoppingCartItemData) o2;

				return s1.getProduct().getManufacturerSku().compareToIgnoreCase(s2.getProduct().getManufacturerSku());
			}
	  };

	  ActionErrors ae = new ActionErrors();

	  ShoppingCartItemDataVector scidv = new ShoppingCartItemDataVector();
	  for (int i = 0; sList != null && i < sList.size(); i++) {
			ShoppingCartItemData sd = (ShoppingCartItemData) sList.get(i);

			scidv.add(sd);

	  }

	  switch(sSortBy){
	  	case 0:
	  		Collections.sort(scidv,CompCategory);
	  		break;
	  	case 1:
	  		Collections.sort(scidv,CompSkuNum);
	  		break;
  		case 2:
  			Collections.sort(scidv,CompProductName);
  			break;
  		case 3:
  			Collections.sort(scidv,CompManufName);
  			break;
  		case 4:
  			Collections.sort(scidv,CompManufSkuNum);
  			break;
  		default:

  			Collections.sort(scidv,CompProductName);
	  }

	  pForm.setCartItems(scidv);

	  return ae;
  }


  //*****************************************************************************
  public static ActionErrors goPage(HttpServletRequest request, MsdsSpecsForm pForm)
  {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    String pageNumS = request.getParameter("page");
    if(pageNumS==null || pageNumS.trim().length()==0) {
      ae.add("error",new ActionError("error.systemError","No page number parameter found"));
      return ae;
    }
    int pageNum = 0;
    try {
      pageNum = Integer.parseInt(pageNumS);
    } catch (NumberFormatException exc) {
      ae.add("error",new ActionError("error.systemError","Bad page number format"));
      return ae;
    }
    int pageSize = pForm.getPageSize();
    int offset = pageNum*pageSize;
    if(offset<0 || offset>=pForm.getProductListSize()) {
      ae.add("error",new ActionError("error.systemError","Requeated page is out of the product list"));
      return ae;
    }
    pForm.setOffset(offset);

    ae = prepareCartItems(request, pForm);

    if(ae.size()>0) {
      return ae;
    }
    return ae;
  }

 //******************************************************************************
  private static ActionErrors prepareCartItems(HttpServletRequest request, MsdsSpecsForm pForm){
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    if(factory==null) {
      ae.add("error",new ActionError("error.systemError","No Ejb access"));
      return ae;
    }
    ShoppingServices shoppingServEjb = null;
    try {
      shoppingServEjb = factory.getShoppingServicesAPI();
    } catch(com.cleanwise.service.api.APIServiceAccessException exc) {
      exc.printStackTrace();
      ae.add("error",new ActionError("error.systemError","No shopping services Ejb pointer"));
      return ae;
    }

    List productList = pForm.getProductList();
    int offset = pForm.getOffset();
    int pageSize = pForm.getPageSize();
    int listSize = pForm.getProductListSize();
    Integer catalogIdI = (Integer) session.getAttribute(Constants.CATALOG_ID);
    if(catalogIdI==null) {
      ae.add("error",new ActionError("error.systemError","No "+Constants.CATALOG_ID+" session attrbute found"));
      return ae;
    }
    int catalogId = catalogIdI.intValue();

    Integer contractIdI = (Integer) session.getAttribute(Constants.CONTRACT_ID);
    int contractId = 0;
    if(catalogIdI!=null) {
      contractId = contractIdI.intValue();
    }
    //int size = (offset+pageSize>listSize)?listSize:offset+pageSize;

    int size = listSize;
    ShoppingCartItemDataVector cartItems = new ShoppingCartItemDataVector();
    List requestedItems = new LinkedList();
    for(int ii=offset; ii<size; ii++) {

    	Object elem = productList.get(ii);
      requestedItems.add(elem);
      }
    log.info("###############Requested items size "+requestedItems.size());
    CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);
    if(appUser==null) {
      ae.add("error",new ActionError("error.systemError","No "+Constants.APP_USER+" session object found"));
      return ae;
    }
    StoreData store = appUser.getUserStore();
    if(store==null) {
      ae.add("error",new ActionError("error.systemError","No store information was loaded"));
      return ae;
    }
    PropertyData storeTypeProperty = store.getStoreType();
    if(storeTypeProperty==null) {
      ae.add("error",new ActionError("error.systemError","No store type information was loaded"));
      return ae;
    }
            
    try {
        cartItems = shoppingServEjb.prepareShoppingItems(storeTypeProperty.getValue(),appUser.getSite(),catalogId,contractId,requestedItems,SessionTool.getCategoryToCostCenterView(session, appUser.getSite().getSiteId()));
    }catch (Exception exc) {
      exc.printStackTrace();
      ae.add("error",new ActionError("error.systemError",exc.getMessage()));
      return ae;
    }
    
    //SOW-3778: JD MSDS Plug-in Project: Begin
    Country countryEjb = null;
    try {
    	countryEjb = factory.getCountryAPI();
    } catch (APIServiceAccessException exc) {
        exc.printStackTrace();
        ae.add("error",
                new ActionError("error.systemError",
                "No country Ejb access"));
        return ae;
    }
    String userLocale = appUser.getUser().getPrefLocaleCd().trim();    
    String countryShortDesc = appUser.getSite().getSiteAddress().getCountryCd();
    log.info(".prepareCartItems() countryShortDesc = " + countryShortDesc);
    CountryData countryData = null;
    try {
        countryData = countryEjb.getCountryByShortDesc(countryShortDesc);
    } catch (Exception exc) {
        exc.printStackTrace();
        ae.add("error", new ActionError("error.systemError", exc.getMessage()));
        return ae;
    }   
    String countryCode = countryData.getCountryCode().trim();
    log.info(".prepareCartItems() countryCode = " + countryCode);    
    String storeLocale = appUser.getUserStore().getBusEntity().getLocaleCd().trim(); 
    
    try {
    	List<Integer> problemItemIds = shoppingServEjb.populateMSDSInformation(cartItems, userLocale, countryCode, storeLocale);
    	//add an error for any items for which msds information could not be retrieved
    	if (Utility.isSet(problemItemIds)) {
    		//create a map of item ids to items so we can retrieve the information needed in the error message
    		Map<Integer,ShoppingCartItemData> idToItemMap = new HashMap<Integer,ShoppingCartItemData>();
    		Iterator<ShoppingCartItemData> itemIterator = cartItems.iterator();
    		while (itemIterator.hasNext()) {
    			ShoppingCartItemData item = itemIterator.next();
    			idToItemMap.put(item.getItemId(), item);
    		}
    		Iterator<Integer> problemItemIdIterator = problemItemIds.iterator();
    		while (problemItemIdIterator.hasNext()) {
    			Integer problemItemId = problemItemIdIterator.next();
    			ShoppingCartItemData item = idToItemMap.get(problemItemId);
    			ae.add("error", new ActionError("shop.errors.unableToRetrieveMSDSInformation", item.getActualSkuNum()));
    		}
    	}
    }
    catch (Exception exc) {
    	exc.printStackTrace();
    	ae.add("error",new ActionError("error.systemError",exc.getMessage()));
    	return ae;
    }
    
    //SOW-3778: JD MSDS Plug-in Project: End
    
    pForm.setCartItems(cartItems);

    List cart= pForm.getCartItems();
    int sortBy = pForm.getOrderBy();
    log.info("sortBy="+sortBy);
    ae = sortMsds(request,pForm,cart,sortBy);

    String docType = pForm.getDocType();
    int[] myDocDescriptor = new int[cartItems.size()];
    Object[] myDocItems = null;
    if("MSDS".equals(docType)&& pForm.getMyMsds()!=null) {
      myDocItems = pForm.getMyMsds().toArray();
    }else if("SPEC".equals(docType)&& pForm.getMySpec()!=null) {
      myDocItems = pForm.getMySpec().toArray();
    }else if("DED".equals(docType)&& pForm.getMyDed()!=null) {
      myDocItems = pForm.getMyDed().toArray();
    }

    if(myDocItems !=null){
    for(int ii=0; ii<cartItems.size(); ii++) {
      int jj = 0;
      ShoppingCartItemData sciD = (ShoppingCartItemData) cartItems.get(ii);
      int id = sciD.getProduct().getProductId();

      for(;jj<myDocItems.length; jj++) {
        int curId = ((ShoppingCartItemData) myDocItems[jj]).getProduct().getProductId();
        if(curId==id) {
          myDocDescriptor[ii] = 0;
          break;
        }
      }
      if(jj==myDocItems.length) {
        myDocDescriptor[ii] = 1;
      }
    }
    }
    pForm.setMyDocDescriptor(myDocDescriptor);


    return ae;
  }

  //****************************************************************************
  public static ActionErrors search(HttpServletRequest request, MsdsSpecsForm pForm)
  {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    if(factory==null) {
      ae.add("error",new ActionError("error.systemError","No Ejb access"));
      return ae;
    }
    ShoppingServices shoppingServEjb = null;
    try {
      shoppingServEjb = factory.getShoppingServicesAPI();
    } catch(APIServiceAccessException exc) {
      exc.printStackTrace();
      ae.add("error",new ActionError("error.systemError","No shopping services Ejb access"));
      return ae;
    }
    pForm.setOffset(0);
    String searchType = pForm.getSearchSkuType();
    IdVector searchRes = null;

    String custSku = (pForm.getSearchSkuType().equals("Cust.Sku"))?pForm.getSearchSku():"";
    String mfgSku = (!pForm.getSearchSkuType().equals("Cust.Sku"))?pForm.getSearchSku():"";
    String name = (pForm.getSearchDescType().equals("Name"))?pForm.getSearchDesc():"";
    String desc = (!pForm.getSearchDescType().equals("Name"))?pForm.getSearchDesc():"";
    String categoryIdS = pForm.getSearchCategory();
    int categoryId = 0;
    if(categoryIdS!=null && categoryIdS.trim().length()>0) {
      try {
        categoryId = Integer.parseInt(categoryIdS);
      } catch (NumberFormatException exc) {
        ae.add("error",new ActionError("error.systemError","Can't convert category id to int"));
        return ae;
      }
    }
//    String size = pForm.getSearchSize();
    String mfgIdS = pForm.getSearchManufacturer();
    int mfgId = 0;
    if(mfgIdS!=null && mfgIdS.trim().length()>0) {
      try {
        mfgId = Integer.parseInt(mfgIdS);
      } catch (NumberFormatException exc) {
        ae.add("error",new ActionError("error.systemError","Can't convert manufacturer id to int"));
        return ae;
      }
    }

      CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
      if (appUser == null) {
          ae.add("error", new ActionError("error.systemError", "No " + Constants.APP_USER + " session object found"));
          return ae;
      }

      StoreData store = appUser.getUserStore();
      if (store == null) {
          ae.add("error", new ActionError("error.systemError", "No store information was loaded"));
          return ae;
      }

      AccountData account = appUser.getUserAccount();
      if (account == null) {
          ae.add("error", new ActionError("error.systemError", "No account information was loaded"));
          return ae;
      }

      Integer catalogId = (Integer) session.getAttribute(Constants.CATALOG_ID);
      if (catalogId == null) {
          ae.add("error", new ActionError("error.systemError", "No " + Constants.CATALOG_ID + " session attrbute found"));
          return ae;
      }

      Integer contractIdI = (Integer) session.getAttribute(Constants.CONTRACT_ID);

      int contractId = Utility.intNN(contractIdI);
      if (appUser.isUserOnContract() && contractId == 0) {
          ae.add("error", new ActionError("error.systemError", "There is no contract found"));
          return ae;
      }

      int accountCatalogId = account.getAccountCatalogId();
      if (appUser.isSpecialPermissionRequired() && accountCatalogId == 0) {
          ae.add("error", new ActionError("error.systemError", "There is no accaunt catalog found"));
          return ae;
      }

      PropertyData storeTypeProperty = store.getStoreType();
      if (storeTypeProperty == null) {
          ae.add("error", new ActionError("error.systemError", "No store type information was loaded"));
          return ae;
      }

      try {

          log.info("Search:::: " + "cat id=" + catalogId +
                  " contract Id=" + contractId + " custSku=" + custSku + " mfg sku=" + mfgSku +
                  " name = " + name + " desc = " + desc + " categoryId = " + categoryId + " docType = " + pForm.getDocType() + " mfgId = " +
                  mfgId + " orderBy = " + pForm.getOrderBy());

          searchRes = shoppingServEjb.searchItemDocs(storeTypeProperty.getValue(),
                  custSku,
                  mfgSku,
                  name,
                  desc,
                  categoryId,
                  pForm.getDocType(), /* 'MSDS' or 'Specs' or 'Ded' */
                  mfgId,
                  pForm.getOrderBy(),
                  ShopTool.createShoppingItemRequest(request));

      } catch (RemoteException exc) {
          exc.printStackTrace();
          ae.add("error", new ActionError("error.systemError", exc.getMessage()));
          return ae;
      }
    log.info("SVCSVCSVC searchRes = " + searchRes);
    for(int x=0; x<searchRes.size(); x++){
    	log.info("Item::"+searchRes.get(x).toString());
    }
    pForm.setProductList(searchRes);
    ae = prepareCartItems(request,pForm);

    if(ae.size()>0) {
      return ae;
    }
    pForm.setContentPage(1);
    return ae;
  }
  //****************************************************************************
  public static ActionErrors addDoc(HttpServletRequest request, MsdsSpecsForm pForm)
  {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    if(factory==null) {
      ae.add("error",new ActionError("error.systemError","No Ejb access"));
      return ae;
    }
    MsdsSpecs msdsSpecsEjb = null;
    try {
      msdsSpecsEjb = factory.getMsdsSpecsAPI();
    } catch(APIServiceAccessException exc) {
      exc.printStackTrace();
      ae.add("error",new ActionError("error.systemError","No MsdsSpecs Ejb access"));
      return ae;
    }
    Integer catalogIdI = (Integer) session.getAttribute(Constants.CATALOG_ID);
    if(catalogIdI==null) {
      ae.add("error",new ActionError("error.systemError","No "+Constants.CATALOG_ID+" session attrbute found"));
      return ae;
    }
    int catalogId = catalogIdI.intValue();
    CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);
    if(appUser==null) {
      ae.add("error",new ActionError("error.systemError","No "+Constants.APP_USER+" session object found"));
      return ae;
    }
    String itemIdS = request.getParameter("itemId");
    int itemId = 0;
    try {
      itemId = Integer.parseInt(itemIdS);
    } catch(NumberFormatException exc) {
      ae.add("error",new ActionError("error.systemError","Wrong item id format. Item id: "+itemIdS));
      return ae;
    }
    IdVector itemIds = new IdVector();
    itemIds.add(new Integer(itemId));

    String docType = request.getParameter("docType");
    if(!"MSDS".equals(docType) && !"SPEC".equals(docType) && !"DED".equals(docType)) {
      ae.add("error",new ActionError("error.systemError","Wrong document type: "+docType));
      return ae;
    }
    try {
      msdsSpecsEjb.addItemsToDocCloset
          (catalogId,appUser.getUser().getUserId(),itemIds, docType,appUser.getUser().getUserName());
    } catch(RemoteException exc) {
      exc.printStackTrace();
      ae.add("error",new ActionError("error.systemError",exc.getMessage()));
      return ae;
    }
    List sciDV = pForm.getCartItems();
    for(int ii=0; ii<sciDV.size(); ii++) {
      ShoppingCartItemData sciD = (ShoppingCartItemData) sciDV.get(ii);
      if(sciD.getProduct().getProductId()==itemId) {
        int[] myDocDescriptor = pForm.getMyDocDescriptor();
        myDocDescriptor[ii]=0;
        pForm.setMyDocDescriptor(myDocDescriptor);
      }
    }
    return ae;
  }
  //****************************************************************************
  public static ActionErrors removeDoc(HttpServletRequest request, MsdsSpecsForm pForm)
  {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    if(factory==null) {
      ae.add("error",new ActionError("error.systemError","No Ejb access"));
      return ae;
    }
    MsdsSpecs msdsSpecsEjb = null;
    try {
      msdsSpecsEjb = factory.getMsdsSpecsAPI();
    } catch(APIServiceAccessException exc) {
      exc.printStackTrace();
      ae.add("error",new ActionError("error.systemError","No MsdsSpecs Ejb access"));
      return ae;
    }
    Integer catalogIdI = (Integer) session.getAttribute(Constants.CATALOG_ID);
    if(catalogIdI==null) {
      ae.add("error",new ActionError("error.systemError","No "+Constants.CATALOG_ID+" session attrbute found"));
      return ae;
    }
    int catalogId = catalogIdI.intValue();
    CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);
    if(appUser==null) {
      ae.add("error",new ActionError("error.systemError","No "+Constants.APP_USER+" session object found"));
      return ae;
    }
    String itemIdS = request.getParameter("itemId");
    int itemId = 0;
    try {
      itemId = Integer.parseInt(itemIdS);
    } catch(NumberFormatException exc) {
      ae.add("error",new ActionError("error.systemError","Wrong item id format. Item id: "+itemIdS));
      return ae;
    }
    IdVector itemIds = new IdVector();
    itemIds.add(new Integer(itemId));

    String docType = request.getParameter("docType");
    if(!"MSDS".equals(docType) && !"SPEC".equals(docType) && !"DED".equals(docType)) {
      ae.add("error",new ActionError("error.systemError","Wrong document type: "+docType));
      return ae;
    }
    try {
        int userId = appUser.getUser().getUserId();
      msdsSpecsEjb.removeItemsFromDocCloset
          (catalogId,userId,itemIds, docType,appUser.getUser().getUserName());
    } catch(RemoteException exc) {
      exc.printStackTrace();
      ae.add("error",new ActionError("error.systemError",exc.getMessage()));
      return ae;
    }
    //Remove from list
    ShoppingCartItemDataVector sciDV = null;
    if("MSDS".equals(docType)) {
      sciDV = pForm.getMyMsds();
      for(int ii=0; ii<sciDV.size(); ii++) {
        ShoppingCartItemData sciD = (ShoppingCartItemData) sciDV.get(ii);
        if(sciD.getProduct().getProductId()==itemId) {
          sciDV.remove(ii);
          break;
        }
      }
      pForm.setMyMsds(sciDV);
    } else if("SPEC".equals(docType)) {
      sciDV = pForm.getMySpec();
      for(int ii=0; ii<sciDV.size(); ii++) {
        ShoppingCartItemData sciD = (ShoppingCartItemData) sciDV.get(ii);
        if(sciD.getProduct().getProductId()==itemId) {
          sciDV.remove(ii);
          break;
        }
      }
      pForm.setMySpec(sciDV);
    } else {
      sciDV = pForm.getMyDed();
      for(int ii=0; ii<sciDV.size(); ii++) {
        ShoppingCartItemData sciD = (ShoppingCartItemData) sciDV.get(ii);
        if(sciD.getProduct().getProductId()==itemId) {
          sciDV.remove(ii);
          break;
        }
      }
      pForm.setMyDed(sciDV);
     }
    return ae;
  }
	private final static void sortDocUrlsAndFillForm(MsdsSpecsForm pForm,
			ItemDataDocUrlsViewVector docUrlsV) {
		final ItemDataDocUrlsViewVector dedUrls = new ItemDataDocUrlsViewVector();
		final ItemDataDocUrlsViewVector msdsUrls = new ItemDataDocUrlsViewVector();
		final ItemDataDocUrlsViewVector specUrls = new ItemDataDocUrlsViewVector();
		final Comparator comparatorByName = new Comparator() {
			/**
			 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
			 */
			public int compare(Object o1, Object o2) {
				ItemDataDocUrlsView i1 = (ItemDataDocUrlsView) o1;
				ItemDataDocUrlsView i2 = (ItemDataDocUrlsView) o2;
				return i1.getItemName().compareTo(i2.getItemName());
			}
		};
		for (int i = 0; docUrlsV != null && i < docUrlsV.size(); i++) {
			final ItemDataDocUrlsView item = (ItemDataDocUrlsView) docUrlsV.get(i);
			if (item.getDed() != null) {
				dedUrls.add(item);
			}
			if (item.getMsds() != null) {
				msdsUrls.add(item);
			}
			if (item.getSpec() != null) {
				specUrls.add(item);
			}
		}
		Collections.sort(dedUrls, comparatorByName);
		Collections.sort(msdsUrls, comparatorByName);
		Collections.sort(specUrls, comparatorByName);
		pForm.setDedUrls(dedUrls);
		pForm.setMsdsUrls(msdsUrls);
		pForm.setSpecUrls(specUrls);
	}
}

