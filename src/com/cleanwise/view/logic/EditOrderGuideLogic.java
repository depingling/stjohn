/**
 * Title:        EditOrderGuideLogic
 * Description:  This is the business logic class for the UserShopAction and UserShopForm.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Yuriy Kupershmidt
 */

package com.cleanwise.view.logic;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.util.I18nUtil;
import com.cleanwise.service.api.value.*;
import com.cleanwise.view.forms.EditOrderGuideForm;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.ShopTool;
import com.cleanwise.view.utils.ValidateActionMessage;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.cleanwise.view.utils.SessionTool;

/**
 * Class description.
 *
 */
public class EditOrderGuideLogic {
  public static ActionErrors init(HttpServletRequest request, EditOrderGuideForm pForm)
  {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    if(factory==null) {
      ae.add("error",new ActionError("error.systemError","No Ejb access"));
      return ae;
    }
    CatalogInformation catalogInfEjb = null;
    try {
      catalogInfEjb = factory.getCatalogInformationAPI();
    } catch(com.cleanwise.service.api.APIServiceAccessException exc) {
      exc.printStackTrace();
      ae.add("error",new ActionError("error.systemError","No catalog Ejb pointer"));
      return ae;
    }

    ContractInformation contractInfEjb = null;
    try {
      contractInfEjb = factory.getContractInformationAPI();
    } catch(com.cleanwise.service.api.APIServiceAccessException exc) {
      ae.add("error",new ActionError("error.systemError","No contract Ejb pointer"));
      return ae;
    }

    ShoppingServices shoppingServEjb = null;
    try {
      shoppingServEjb = factory.getShoppingServicesAPI();
    } catch(com.cleanwise.service.api.APIServiceAccessException exc) {
      ae.add("error",new ActionError("error.systemError","No shopping services Ejb pointer"));
      return ae;
    }

    if(ae.size()>0) {
      return ae;
    }
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    if(appUser==null) {
      ae.add("error",new ActionError("error.systemError","No "+Constants.APP_USER+"session object found"));
      return ae;
    }
    UserData user = appUser.getUser();
    int siteId = appUser.getSite().getBusEntity().getBusEntityId();
    String roleCd = user.getUserRoleCd();
    boolean contractOnly = appUser.isUserOnContract();
    Integer catalogIdI = (Integer) session.getAttribute(Constants.CATALOG_ID);
    if(catalogIdI==null) {
      ae.add("error",new ActionError("error.systemError","No "+Constants.CATALOG_ID+ " session object found"));
      return ae;
    }
    int catalogId = catalogIdI.intValue();
    //initialize order guide list
    OrderGuideDataVector userOrderGuideDV = null;
    try {
  	int userid = user.getUserId();
	if ( appUser.getSite().isSetupForSharedOrderGuides() ) {
	    userid = 0;
	}

	pForm.setAllUserOrderGuides(null);
	userOrderGuideDV = shoppingServEjb.getUserOrderGuides(userid,catalogId,siteId);
    } catch(RemoteException exc) {
	exc.printStackTrace();
	ae.add("error",new ActionError("error.systemError","Can't pick up order guides"));
	return ae;
    }
    pForm.setUserOrderGuides(userOrderGuideDV);

    // Shared carts have been set up list all available.
    if ( appUser.getSite().isSetupForSharedOrderGuides() ||
         appUser.isaAdmin() || appUser.isaCustServiceRep() ) {
	OrderGuideDataVector allUserOrderGuideDV = null;
	try {
	    allUserOrderGuideDV = shoppingServEjb.getUserOrderGuides
		(0,catalogId,siteId);
	} catch(RemoteException exc) {
	    exc.printStackTrace();
	    ae.add("error",new ActionError("error.systemError",
					   "Failed to get all order guides."));
	    return ae;
	}
	pForm.setAllUserOrderGuides(allUserOrderGuideDV);
    } else {
	pForm.setAllUserOrderGuides(null);
    }


    pForm.setOrderGuideId(-1);
    pForm.setQuantity(new String[pForm.getPageSize()]);
    pForm.setChangesFlag(false);
    pForm.setSelectBox(new long[0]);
    pForm.setShortDesc("");
    return ae;
  }


  //****************************************************************************
  private static void orderByName(List pItems, EditOrderGuideForm pForm) {
    //return if nothing to sord
    if(pItems==null) {
      pForm.setItems(new ArrayList());
      return;
    }
    if(pItems.size()<=1) {
      pForm.setItems(pItems);
      return;
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
        ss1 += item1.getActualSkuNum();
        String ss2 = item2.getProduct().getCatalogProductShortDesc();
        ss2 += item2.getActualSkuNum();
        if(ss1.compareToIgnoreCase(ss2)>0) {
          itemArray[jj-1] = item2;
          itemArray[jj] = item1;
          exitFlag = false;
        }
      }
      if(exitFlag) break;
    }
    List items = new ArrayList();
    for(int ii=0; ii<itemArray.length; ii++) {
      items.add(itemArray[ii]);
    }
    pForm.setItems(items);
    return;
  }
  //****************************************************************************
  private static void orderBySku(List pItems, EditOrderGuideForm pForm) {
    //return if nothing to sord
    if(pItems==null) {
      pForm.setItems(new ArrayList());
      return;
    }
    if(pItems.size()<=1) {
      pForm.setItems(pItems);
      return;
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
        String ss1 = item1.getActualSkuNum();
        ss1 += item1.getProduct().getCatalogProductShortDesc();
        String ss2 = item2.getActualSkuNum();
        ss2 += item2.getProduct().getCatalogProductShortDesc();
        if(ss1.compareToIgnoreCase(ss2)>0) {
          itemArray[jj-1] = item2;
          itemArray[jj] = item1;
          exitFlag = false;
        }
      }
      if(exitFlag) break;
    }
    List items = new ArrayList();
    for(int ii=0; ii<itemArray.length; ii++) {
      items.add(itemArray[ii]);
    }
    pForm.setItems(items);
    return;
  }
  //****************************************************************************
  private static void orderByCategory(List pItems, EditOrderGuideForm pForm) {
    //return if nothing to sord
    if(pItems==null) {
      pForm.setItems(new ArrayList());
      return;
    }
    if(pItems.size()<=1) {
      pForm.setItems(pItems);
      return;
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
        String ss1 = item1.getCategoryName();
        ss1 += item1.getProduct().getCatalogProductShortDesc();
        String ss2 = item2.getCategoryName();
        ss2 += item2.getProduct().getCatalogProductShortDesc();
        if(ss1.compareToIgnoreCase(ss2)>0) {
          itemArray[jj-1] = item2;
          itemArray[jj] = item1;
          exitFlag = false;
        }
      }
      if(exitFlag) break;
    }
    List items = new ArrayList();
    for(int ii=0; ii<itemArray.length; ii++) {
      items.add(itemArray[ii]);
    }
    pForm.setItems(items);
    return;
  }
  //****************************************************************************
  private static CatalogCategoryData[] orderCategories(CatalogCategoryDataVector pCcDV) {
    CatalogCategoryData[] categories = new CatalogCategoryData[pCcDV.size()];
    for(int ii=0; ii<categories.length; ii++) {
      categories[ii] = (CatalogCategoryData) pCcDV.get(ii);
    }
    if(categories.length<=1) {
      return categories;
    }
    for(int ii=0; ii<categories.length-1; ii++) {
      boolean exitFlag = true;
      for(int jj=1; jj<categories.length-ii; jj++) {
        CatalogCategoryData category1 = categories[jj-1];
        CatalogCategoryData category2 = categories[jj];
        String ss1 = category1.getCatalogCategoryShortDesc();
        if(ss1==null) ss1="";
        String ss2 = category2.getCatalogCategoryShortDesc();
        if(ss2==null) ss2="";
        if(ss1.compareToIgnoreCase(ss2)>0) {
          categories[jj-1] = category2;
          categories[jj] = category1;
          exitFlag = false;
        }
      }
      if(exitFlag) break;
    }
    return categories;
  }
  //****************************************************************************
  public static ActionErrors deleteOrderGuide(HttpServletRequest request, EditOrderGuideForm pForm)
  {
    ActionErrors ae = new ActionErrors();
    int inputOrderGuide = pForm.getInputOrderGuideId();
    //
    HttpSession session = request.getSession();
    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    if(factory==null) {
      ae.add("error",new ActionError("error.systemError","No Ejb access"));
      return ae;
    }
    OrderGuide orderGuideEjb = null;
    try {
      orderGuideEjb = factory.getOrderGuideAPI();
    } catch(com.cleanwise.service.api.APIServiceAccessException exc) {
      ae.add("error",new ActionError("error.systemError","No order guide Ejb pointer"));
      return ae;
    }
    try {
      orderGuideEjb.removeOrderGuide(inputOrderGuide);
    } catch(Exception exc) {
      ae.add("error",new ActionError("error.systemError","Can't save order guide to database"));
      return ae;
    }
    pForm.setOrderGuideId(-1);
    ae = init(request,pForm);
    if(ae.size()>0) return ae;
    return ae;
  }
  //****************************************************************************
  public static ActionErrors select(HttpServletRequest request, EditOrderGuideForm pForm)
  throws Exception
  {
    ActionErrors ae = new ActionErrors();
    int orderGuideId = pForm.getOrderGuideId();
    if(orderGuideId<=0) {
      String errorMess=ClwI18nUtil.getMessage(request,"shop.errors.noOrderGuideSelected",null);
      ae.add("error",new ActionError("error.simpleGenericError",errorMess));
      return ae;
    }

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
      ae.add("error",new ActionError("error.systemError","No shopping services Ejb pointer"));
      return ae;
    }
    //
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    Integer catalogIdI = (Integer) session.getAttribute(Constants.CATALOG_ID);
    if(catalogIdI==null) {
      ae.add("error",new ActionError("error.systemError","No "+Constants.CATALOG_ID+"session object found"));
      return ae;
    }
    Integer contractIdI = (Integer) session.getAttribute(Constants.CONTRACT_ID);
    if(contractIdI==null) {
       contractIdI = new Integer(0);
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

    ShoppingCartItemDataVector shoppingCartDV = null;
    try {
      SiteData site = appUser.getSite();
        shoppingCartDV = shoppingServEjb.getOrderGuidesItems(storeTypeProperty.getValue(),
                site,
                orderGuideId,
                ShopTool.createShoppingItemRequest(request),
                Constants.ORDER_BY_CATEGORY,
                SessionTool.getCategoryToCostCenterView(session, site.getSiteId()));

    } catch(Exception exc) {
      ae.add("error",new ActionError("error.systemError","Can't load order guide from database 2"));
      return ae;
    }
    List items = new ArrayList();
    String[] quantityS = new String[shoppingCartDV.size()];
    int[] categoryIds = new int[shoppingCartDV.size()];
    for(int ii=0; ii<shoppingCartDV.size(); ii++) {
      ShoppingCartItemData sciD = ((ShoppingCartItemData) shoppingCartDV.get(ii)).copy();
      sciD.setOrderNumber(ii+1);
      items.add(sciD);
      quantityS[ii] = ""+sciD.getQuantity();
      CatalogCategoryData ccD = sciD.getCategory();
      if(ccD==null) {
        CatalogCategoryDataVector ccDV = (CatalogCategoryDataVector) sciD.getProduct().getCatalogCategories();
        if(ccDV!=null && ccDV.size()>0) {
          ccD = (CatalogCategoryData)ccDV.get(0);
          sciD.setCategory(ccD);
        }
      }
      categoryIds[ii] = (ccD!=null)?ccD.getCatalogCategoryId():-1;
    }
    pForm.setItems(items);
    pForm.setQuantity(quantityS);
    pForm.setItemIds(new int[quantityS.length]);
    pForm.setOrderNumbers(new int[quantityS.length]);
    pForm.setCategoryIds(categoryIds);
    //
    OrderGuide orderGuideEjb = null;
    try {
      orderGuideEjb = factory.getOrderGuideAPI();
    } catch(com.cleanwise.service.api.APIServiceAccessException exc) {
      ae.add("error",new ActionError("error.systemError","No order guide Ejb pointer"));
      return ae;
    }
    OrderGuideData orderGuideD = null;
    try {
      orderGuideD = orderGuideEjb.getOrderGuide(orderGuideId);
    } catch(Exception exc) {
      ae.add("error",new ActionError("error.systemError","Can't get order guide object"));
      return ae;
    }
    pForm.setOrderGuideId(orderGuideD.getOrderGuideId());
    pForm.setOrderGuide(orderGuideD);
    pForm.setShortDesc(orderGuideD.getShortDesc());
    pForm.setSelectBox(new long[0]);
    pForm.setChangesFlag(false);
    //sort
    ae = sort(request,pForm);
    if(ae.size()>0) return ae;
    return ae;
  }
/*
   //Jd begin

   public static ActionErrors setJdProperties(HttpServletRequest request,
                                    EditOrderGuideForm pForm)
    {
      ActionErrors ae = new ActionErrors();
      HttpSession session = request.getSession();
      CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
      if(appUser==null) {
        ae.add("error",new ActionError("error.systemError","No "+Constants.APP_USER+"session object found"));
        return ae;
      }
      SiteData site = appUser.getSite();
      BigDecimal weightThreshold = site.getWeightThreshold();
      BigDecimal priceThreshold = site.getPriceThreshold();
      BigDecimal contractThreshold = site.getContractThreshold();
      String priceCode = site.getPriceCode();
      pForm.setPriceCode(priceCode);
      pForm.setWeightThreshold(weightThreshold);
      pForm.setPriceThreshold(priceThreshold);
      pForm.setContractThreshold(contractThreshold);
      return ae;
    }
    //Jd end
 */
  //****************************************************************************
  public static ActionErrors addFromCart(HttpServletRequest request, EditOrderGuideForm pForm)
  throws Exception
  {
    ActionErrors ae = new ActionErrors();

    //Clear selection
    pForm.setSelectBox(new long[0]);
    //adjust quantities
    ae = pickupQuantities(request,pForm);

    int orderGuideId = pForm.getInputOrderGuideId();

    HttpSession session = request.getSession();
    ShoppingCartData shoppingCartD = (ShoppingCartData) session.getAttribute(Constants.SHOPPING_CART);
    if(shoppingCartD==null) {
      ae.add("error",new ActionError("error.systemError","No "+Constants.SHOPPING_CART+"session object found"));
      return ae;
    }

    List newItems = shoppingCartD.getItems();
    if(newItems.size()>0) {

      ValidateActionMessage am = ShopTool.validateSpecialPermission(request, newItems);
      if (am.hasErrors()) {
          return am.getActionErrors();
      }

      List items = pForm.getItems();
      int itemSize = items.size();

      for(int ii=0; ii<newItems.size(); ii++) {
        ShoppingCartItemData sciD = (ShoppingCartItemData) newItems.get(ii);
        int id = sciD.getProduct().getProductId();
        //Try to find the same item
        int jj = 0;
        for(; jj<itemSize; jj++) {
          ShoppingCartItemData sciD1 = (ShoppingCartItemData) items.get(jj);
          if(sciD1.getProduct().getProductId()==id) {
            int qty = sciD1.getQuantity();
            qty += sciD.getQuantity();
            sciD1.setQuantity(qty);
            break;
          }
        }
        if(jj==itemSize) {
          items.add(sciD.copy());
        }
      }

      String[] qtyS = new String[items.size()];

      for(int ii=0; ii<items.size(); ii++) {
        ShoppingCartItemData sciD = (ShoppingCartItemData) items.get(ii);
        sciD.setOrderNumber(ii+1);
        qtyS[ii] = ""+sciD.getQuantity();
      }
      pForm.setItems(items);
      pForm.setQuantity(qtyS);
      pForm.setItemIds(new int[qtyS.length]);
      pForm.setCategoryIds(new int[qtyS.length]);
      pForm.setOrderNumbers(new int[qtyS.length]);
      pForm.setChangesFlag(true);
    }
    ae = save(request, pForm);
    if(ae.size()>0) return ae;
    //sort
    ae = sort(request,pForm);
    if(ae.size()>0) return ae;
    //
    return ae;
  }
  //****************************************************************************
  public static ActionErrors save(HttpServletRequest request, EditOrderGuideForm pForm)
  throws Exception
  {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    //Clear select box
    pForm.setSelectBox(new long[0]);

    //Pick up quantities
    ae = pickupQuantities(request, pForm);
    if(ae.size()>0) return ae;

    //Check new order guide name
    String name = pForm.getShortDesc();
    if(name==null || name.trim().length()==0) {
      String errorMess=ClwI18nUtil.getMessage(request,"shop.errors.provideOrderGuideName",null);
      ae.add("error",new ActionError("error.simpleGenericError",errorMess));
      return ae;
    }
    name = name.trim();
    name = I18nUtil.getUtf8Str(name);
    PropertyService psBean = factory.getPropertyServiceAPI();
    pForm.setShortDesc(name);
    int id = pForm.getInputOrderGuideId();

    //Check uniqueness of the name
    OrderGuideDataVector guides = pForm.getUserOrderGuides();
    OrderGuideData orderGuideD = null;
    int ii=0;
    for(;ii<guides.size();ii++) {
      OrderGuideData ogD = (OrderGuideData) guides.get(ii);
      if(ogD.getOrderGuideId()==id) {
        orderGuideD = ogD;
        continue;
      }
      String ss = ogD.getShortDesc();
      if(ss!=null && ss.trim().equals(name)) {
        break;
      }
    }
    if(ii<guides.size()) {
      String errorMess=ClwI18nUtil.getMessage(request,"shop.errors.nameExists",null);
      ae.add("error",new ActionError("error.simpleGenericError",errorMess));
      return ae;
    }

    if(orderGuideD==null) {
      ae.add("error",new ActionError("error.systemError","Order guide not found in the list"));
      return ae;
    }
    //Criate new order guide object;
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    if(appUser==null){
      ae.add("error",new ActionError("error.systemError","No "+Constants.APP_USER+" session object found"));
      return ae;
    }
    Integer catalogIdI = (Integer) session.getAttribute(Constants.CATALOG_ID);
    if(catalogIdI==null){
      ae.add("error",new ActionError("error.systemError","No "+Constants.CATALOG_ID+" session object found"));
      return ae;
    }
    boolean nameChangedFlag = false;
    if(!name.trim().equals(orderGuideD.getShortDesc())) {
      nameChangedFlag=true;
      orderGuideD.setShortDesc(name);
    }
    ShoppingServices shoppingServEjb = null;
    try {
      shoppingServEjb = factory.getShoppingServicesAPI();
    } catch(com.cleanwise.service.api.APIServiceAccessException exc) {
      ae.add("error",new ActionError("error.systemError","No shopping services Ejb pointer"));
      return ae;
    }
    OrderGuide orderGuideEjb = null;
    try {
      orderGuideEjb = factory.getOrderGuideAPI();
    } catch(com.cleanwise.service.api.APIServiceAccessException exc) {
      ae.add("error",new ActionError("error.systemError","No order guide Ejb pointer"));
      return ae;
    }

    ValidateActionMessage am = ShopTool.validateSpecialPermission(request, pForm.getItems());
    if (am.hasErrors()) {
        return am.getActionErrors();
    }

    try {
      int ogid = shoppingServEjb.saveUserOrderGuide(orderGuideD,pForm.getItems(),appUser.getUser().getUserName());
      OrderGuideData togd = orderGuideEjb.getOrderGuide(ogid);
      pForm.setOrderGuide(togd);

    } catch(Exception exc) {
      ae.add("error",new ActionError("error.systemError","Can't save order guide to database"));
      return ae;
    }

    ii=0;
    for(;ii<guides.size();ii++) {
      OrderGuideData ogD = (OrderGuideData) guides.get(ii);
      if(ogD.getOrderGuideId()==id) {
        break;
      }
    if(ii==guides.size()) {
        String errorMess=ClwI18nUtil.getMessage(request,"shop.errors.orderGuideWasNotLoaded",null);
        ae.add("error",new ActionError("error.simpleGenericError","Order guide was not loaded"));
        return ae;
      }
    }

    UserData user = appUser.getUser();
    int siteId = appUser.getSite().getBusEntity().getBusEntityId();

    OrderGuideDataVector userOrderGuideDV = null;
    try {
  	int userid = user.getUserId();
	if ( appUser.getSite().isSetupForSharedOrderGuides() ) {
	    userid = 0;
	}
      userOrderGuideDV = shoppingServEjb.getUserOrderGuides(userid,catalogIdI.intValue(),siteId);
    } catch(Exception exc) {
      ae.add("error",new ActionError("error.systemError","Can't load order guides from database"));
      return ae;
    }
    pForm.setUserOrderGuides(userOrderGuideDV);
    pForm.setChangesFlag(false);
    //sort
    ae = sort(request,pForm);
    if(ae.size()>0) return ae;
    //
    return ae;
  }
  //****************************************************************************
  public static ActionErrors recalc(HttpServletRequest request, EditOrderGuideForm pForm)
  throws Exception
  {
    ActionErrors ae = new ActionErrors();
    pForm.setSelectBox(new long[0]);
    ae = pickupQuantities(request, pForm);
    if(ae.size()>0) return ae;
    ae = save(request, pForm);
    if(ae.size()>0) return ae;
    ae = sort(request,pForm);
    if(ae.size()>0) return ae;
    //
    return ae;
  }
  //*****************************************************************************
  private static ActionErrors pickupQuantities(HttpServletRequest request, EditOrderGuideForm pForm) {
    ActionErrors ae = new ActionErrors();
    String[] quantityS = pForm.getQuantity();
    int[] orderNums = pForm.getOrderNumbers();
    int[] ids = pForm.getItemIds();
    int[] quantity = new int[quantityS.length];
    List items = pForm.getItems();
    //Check validity
    for(int ii=0; ii<quantityS.length; ii++) {
      String qqS = quantityS[ii];
      if(qqS!=null && qqS.trim().length()>0) {
        try {
          int qq = Integer.parseInt(qqS);
          quantity[ii] = qq;
          
          // check negative qty
          if (qq < 0 ) {
              Object[] params = new Object[2];
              params[0] = ((ShoppingCartItemData)items.get(ii)).getActualSkuNum();
              params[1] = qq;
              String errorMess=ClwI18nUtil.getMessage(request,"shop.errors.skuQuantityIsNegative",params);
              ae.add("error"+ii,new ActionError("error.simpleGenericError",errorMess));
          }
        } catch (NumberFormatException exc) {
          Object[] params = new Object[1];
          params[0] = qqS;
          String errorMess=ClwI18nUtil.getMessage(request,"shop.errors.wrongQuantityNumberFormat",params);
          ae.add("error"+ii,new ActionError("error.simpleGenericError",errorMess));
        }
      }else{
        quantity[ii] = 0;
      }
    }
    if(ae.size()>0) {
      return ae;
    }
    int[] categoryIds = pForm.getCategoryIds();
    for(int ii=0; ii<items.size(); ii++) {
      ShoppingCartItemData sciD = (ShoppingCartItemData)items.get(ii);
      int orderNum = sciD.getOrderNumber();
      int id = sciD.getProduct().getProductId();
      for(int jj=0; jj<quantity.length; jj++) {
        if(ids[jj]==id && orderNums[jj]==orderNum) {
          if(sciD.getCategory()!=null && sciD.getCategory().getCatalogCategoryId()!=categoryIds[jj]) {
            CatalogCategoryDataVector ccDV = sciD.getProduct().getCatalogCategories();
            if(ccDV!=null && ccDV.size()>0) {
              for(int zz=0; zz<ccDV.size(); zz++) {
                CatalogCategoryData ccD = (CatalogCategoryData) ccDV.get(zz);
                if(ccD.getCatalogCategoryId()==categoryIds[jj]){
                  sciD.setCategory(ccD);
                  pForm.setChangesFlag(true);
                  break;
                }
              }
            }
            pForm.setChangesFlag(true);
          }
          if(sciD.getQuantity()!= quantity[jj]) {
              sciD.setQuantity(quantity[jj]);
              pForm.setChangesFlag(true);
          }
          break;
        }
      }
    }
    if(ae.size()>0) {
        return ae;
    }

    pForm.setItems(items);
    return ae;
  }

  //****************************************************************************
  public static ActionErrors sort(HttpServletRequest request, EditOrderGuideForm pForm)
  {
    ActionErrors ae = new ActionErrors();
    //Clear selection
    pForm.setSelectBox(new long[0]);

    //Pick up quantities
    ae = pickupQuantities(request, pForm);
    if(ae.size()>0) return ae;

    int orderBy = pForm.getOrderBy();
    switch(orderBy) {
      case Constants.ORDER_BY_CATEGORY:
        orderByCategory(pForm.getItems(),pForm);
        break;
      case Constants.ORDER_BY_CUST_SKU:
        orderBySku(pForm.getItems(),pForm);
        break;
      case Constants.ORDER_BY_NAME:
        orderByName(pForm.getItems(),pForm);
        break;
      default:
        pForm.setOrderBy(Constants.ORDER_BY_CATEGORY);
        orderByCategory(pForm.getItems(),pForm);
    }
    //init wrk arrays
    List items = pForm.getItems();
    String[] qtyS = pForm.getQuantity();
    int[] categoryIds = pForm.getCategoryIds();
    for(int ii=0; ii<items.size(); ii++) {
      ShoppingCartItemData sciD = (ShoppingCartItemData) items.get(ii);
      qtyS[ii] = "" + sciD.getQuantity();
      CatalogCategoryData ccD = sciD.getCategory();
      categoryIds[ii] = (ccD!=null)? ccD.getCatalogCategoryId():-1;
    }
    pForm.setQuantity(qtyS);
    pForm.setCategoryIds(categoryIds);
    return ae;
  }
  //*****************************************************************************
  public static ActionErrors removeSelected(HttpServletRequest request, EditOrderGuideForm pForm)
  throws Exception
  {
    ActionErrors ae = new ActionErrors();
    //Pick up quantities
    //ae = pickupQuantities(request, pForm);
    //if(ae.size()>0) return ae;

    HttpSession session = request.getSession();
    long[] toDelete = pForm.getSelectBox();

    List items = pForm.getItems();
    List removedItems = new ArrayList();
    for(int ii=0; ii<items.size();) {
      ShoppingCartItemData sciD = (ShoppingCartItemData)items.get(ii);
      int orderNum = sciD.getOrderNumber();
      int id = sciD.getProduct().getProductId();
      long code = (long)id*10000+(long)orderNum;
      int jj = 0;
      for(; jj<toDelete.length; jj++) {
        if(code==toDelete[jj]) {
          items.remove(ii);
          removedItems.add(sciD);
          break;
        }
      }
      if(jj==toDelete.length) ii++;
    }
    if(removedItems.size()>0) {
      pForm.setChangesFlag(true);
    }
    pForm.setRemovedItems(removedItems);
    pForm.setItems(items);
    pForm.setSelectBox(new long[0]);

    String[] quantityS = new String[items.size()];
    int[] orderNums = new int[items.size()];
    int[] ids = new int[items.size()];
    int idx = 0;
    for(Iterator iter = items.iterator();iter.hasNext();idx++ ) {
      ShoppingCartItemData sciD = (ShoppingCartItemData)iter.next();
      int orderNum = sciD.getOrderNumber();
      int id = sciD.getProduct().getProductId();
      int qty = sciD.getQuantity();
      quantityS[idx] = ""+qty;
      orderNums[idx] = orderNum;
      ids[idx] = id;
    }
    pForm.setOrderNumbers(orderNums);
    pForm.setItemIds(ids);
    pForm.setQuantity(quantityS);
//    ae = init(request,pForm);

    ae = save(request, pForm);
    if(ae.size()>0) return ae;

    ae = sort(request,pForm);
    if(ae.size()>0) return ae;
    //
    return ae;
  }
  //*****************************************************************************
  public static ActionErrors removeAll(HttpServletRequest request, EditOrderGuideForm pForm)
  throws Exception
  {
    ActionErrors ae = new ActionErrors();
    //Clear selection
    pForm.setSelectBox(new long[0]);

    //Pick up quantities
    ae = pickupQuantities(request, pForm);
    if(ae.size()>0) return ae;

    HttpSession session = request.getSession();
    List items = pForm.getItems();
    pForm.setRemovedItems(items);
    pForm.setItems(new ArrayList());
    if(items!=null && items.size()>0) {
      pForm.setChangesFlag(true);
    }
    ae = save(request, pForm);
    if(ae.size()>0) return ae;
    return ae;
  }

    public static ActionErrors addItemsSelected(HttpServletRequest request,
            EditOrderGuideForm pForm)
    throws Exception{
	ActionErrors ae = new ActionErrors();
	HttpSession session = request.getSession();
	int inputOrderGuideId = pForm.getInputOrderGuideId();

	APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
	ShoppingServices shoppingServEjb = factory.getShoppingServicesAPI();
	//
	CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
	Integer catalogIdI = (Integer) session.getAttribute(Constants.CATALOG_ID);
	if(catalogIdI==null) {
	    ae.add("error",new ActionError("error.systemError","No "
					   +Constants.CATALOG_ID+"session object found"));
	    return ae;
	}
	Integer contractIdI = (Integer) session.getAttribute(Constants.CONTRACT_ID);
	if(contractIdI==null) {
	    contractIdI = new Integer(0);
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

    List items = pForm.getItems();
    List newItems = new ArrayList();

    long [] itemsSelected = pForm.getSelectToAddBox();
    for ( int i = 0; i < itemsSelected.length; i++ ) {
        List itemsAvailable = pForm.getItemsAvailable();
        if(itemsAvailable!=null) {
            for ( Iterator iter = itemsAvailable.iterator(); iter.hasNext();) {
                ShoppingCartItemData sciD = (ShoppingCartItemData)iter.next();
                int id = sciD.getProduct().getProductId();
                int orderNum = sciD.getOrderNumber();
                long code = (long)id*10000+(long)orderNum;
                if ( code == itemsSelected[i] ) {
                    newItems.add(sciD);
                }
            }
        }
    }


    if (!newItems.isEmpty()) {

        ValidateActionMessage am = ShopTool.validateSpecialPermission(request, newItems);
        if (am.hasErrors()) {
            return am.getActionErrors();
        }

        ShoppingCartItemDataVector tempVector = new ShoppingCartItemDataVector();
        tempVector.addAll(items);

        items = ShopTool.addItemsToOgList(tempVector, newItems);

    }

    pForm.setSelectToAddBox(new long[0]);

    String[] qtyS = new String[items.size()];
    for(int ii=0; ii<items.size(); ii++) {
        ShoppingCartItemData sciD = (ShoppingCartItemData) items.get(ii);
        sciD.setOrderNumber(ii+1);
        qtyS[ii] = ""+sciD.getQuantity();
    }

    pForm.setItems(items);
    pForm.setQuantity(qtyS);
    pForm.setItemIds(new int[qtyS.length]);
    pForm.setCategoryIds(new int[qtyS.length]);
    pForm.setOrderNumbers(new int[qtyS.length]);
    pForm.setChangesFlag(true);

    ae = save(request, pForm);
    if(ae.size()>0) return ae;

	return ae;
    }


 public static ActionErrors findItemsToAdd(HttpServletRequest request,
         EditOrderGuideForm pForm)
 throws Exception{
	ActionErrors ae = new ActionErrors();
	HttpSession session = request.getSession();
	int inputOrderGuideId = pForm.getInputOrderGuideId();
    String name = pForm.getShortDesc();
    name = name.trim();
    name = I18nUtil.getUtf8Str(name);
    pForm.setShortDesc(name);

	APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
	if(factory==null) {
	    ae.add("error",new ActionError("error.systemError","No Ejb access"));
	    return ae;
	}
	ShoppingServices shoppingServEjb = null;
	try {
	    shoppingServEjb = factory.getShoppingServicesAPI();
	} catch(com.cleanwise.service.api.APIServiceAccessException exc) {
	    ae.add("error",new ActionError("error.systemError","No shopping services Ejb pointer"));
	    return ae;
	}
	//
	CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
	Integer catalogIdI = (Integer) session.getAttribute(Constants.CATALOG_ID);
	if(catalogIdI==null) {
	    ae.add("error",new ActionError("error.systemError","No "
					   +Constants.CATALOG_ID+"session object found"));
	    return ae;
	}
	Integer contractIdI = (Integer) session.getAttribute(Constants.CONTRACT_ID);
	if(contractIdI==null) {
	    contractIdI = new Integer(0);
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
    ShoppingCartData shoppingCartD =
        (ShoppingCartData) session.getAttribute(Constants.SHOPPING_CART);
    if(shoppingCartD==null) {
      String errorMess=ClwI18nUtil.getMessage(request,"shop.errors.shoppiningCartNotFound",null);
      ae.add("error",new ActionError("error.simpleGenericError",errorMess));
      return ae;
    }
    ShoppingCartItemDataVector shoppingCartDV = shoppingCartD.getItems();
    if(shoppingCartDV==null || shoppingCartDV.size()==0) {
      String errorMess=ClwI18nUtil.getMessage(request,"shop.errors.shoppiningCartIsEmpty",null);
      ae.add("error",new ActionError("error.simpleGenericError",errorMess));
      return ae;
    }
	pForm.setItemsAvailable(shoppingCartDV);
	return ae;
    }


  //*****************************************************************************
  public static ActionErrors merge(HttpServletRequest request, EditOrderGuideForm pForm)
  throws Exception
  {
    ActionErrors ae = new ActionErrors();
    //Clear selection
    pForm.setSelectBox(new long[0]);

    //Pick up quantities
    ae = pickupQuantities(request, pForm);
    if(ae.size()>0) return ae;

    HttpSession session = request.getSession();
    long[] toDelete = pForm.getSelectBox();

    List items = pForm.getItems();
    List removedItems = new ArrayList();
    for(int ii=0; ii<items.size()-1;ii++) {
      ShoppingCartItemData sciD = (ShoppingCartItemData)items.get(ii);
      int id = sciD.getProduct().getProductId();
      int qty=sciD.getQuantity();
      for(int jj=ii+1; jj<items.size();) {
        ShoppingCartItemData sciD1 = (ShoppingCartItemData)items.get(jj);
        if(id==sciD1.getProduct().getProductId()) {
          qty+=sciD1.getQuantity();
          items.remove(jj);
          pForm.setChangesFlag(true);
          continue;
        }
        else {
          jj++;
        }
      }
      sciD.setQuantity(qty);
    }
    String[] quantityS = new String[items.size()];
    for(int ii=0; ii<items.size(); ii++) {
      ShoppingCartItemData sciD = (ShoppingCartItemData) items.get(ii);
      quantityS[ii] = ""+sciD.getQuantity();
    }
    pForm.setQuantity(quantityS);
    ae = save(request, pForm);
    if(ae.size()>0) return ae;
    //sort
    ae = sort(request,pForm);
    if(ae.size()>0) return ae;
    //
    return ae;
  }
  //*****************************************************************************
  public static ActionErrors restoreQuant(HttpServletRequest request, EditOrderGuideForm pForm)
  throws Exception
  {
    ActionErrors ae = new ActionErrors();
    pForm.setSelectBox(new long[0]);
    List items = pForm.getItems();
    String[] qty = new String[items.size()];
    for(int ii=0; ii<items.size(); ii++) {
      ShoppingCartItemData sciD = (ShoppingCartItemData) items.get(ii);
      qty[ii] = ""+sciD.getQuantity();
    }
    pForm.setQuantity(qty);
    ae = save(request, pForm);
    if(ae.size()>0) return ae;
    //sort
    ae = sort(request,pForm);
    if(ae.size()>0) return ae;
    //
    return ae;
  }
  //*****************************************************************************
  public static ActionErrors undoRemove(HttpServletRequest request, EditOrderGuideForm pForm)
  throws Exception
  {
    pForm.setInputOrderGuideId(pForm.getOrderGuideId());
    ActionErrors ae = new ActionErrors();
    pForm.setSelectBox(new long[0]);
    HttpSession session = request.getSession();

    List items = pForm.getItems();
    List removedItems = pForm.getRemovedItems();
    items.addAll(removedItems);
    pForm.setItems(items);
    pForm.setRemovedItems(new ArrayList());
//    ae = init(request,pForm);

    String[] qtyS = new String[items.size()];
    for(int ii=0; ii<items.size(); ii++) {
        ShoppingCartItemData sciD = (ShoppingCartItemData) items.get(ii);
        qtyS[ii] = "" + sciD.getQuantity();
    }
    pForm.setQuantity(qtyS);
    pForm.setItemIds(new int[qtyS.length]);
    pForm.setCategoryIds(new int[qtyS.length]);
    pForm.setOrderNumbers(new int[qtyS.length]);

    ae = save(request, pForm);
    if(ae.size()>0) return ae;
    //sort
    ae = sort(request,pForm);
    if(ae.size()>0) return ae;
    //
    return ae;
  }
  //*****************************************************************************
}
