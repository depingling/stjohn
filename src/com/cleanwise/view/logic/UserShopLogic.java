/**
 *  Title: UserShopLogic Description: This is the business logic class for the
 *  UserShopAction and UserShopForm. Purpose: Copyright: Copyright (c) 2001
 *  Company: CleanWise, Inc.
 *
 *@author     Yuriy Kupershmidt
 */
package com.cleanwise.view.logic;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.util.MessageResources;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.APIServiceAccessException;
import com.cleanwise.service.api.reporting.ReportingUtils;
import com.cleanwise.service.api.session.CatalogInformation;
import com.cleanwise.service.api.session.Content;
import com.cleanwise.service.api.session.ContractInformation;
import com.cleanwise.service.api.session.Country;
import com.cleanwise.service.api.session.EmailClient;
import com.cleanwise.service.api.session.Order;
import com.cleanwise.service.api.session.OrderGuide;
import com.cleanwise.service.api.session.ShoppingServices;
import com.cleanwise.service.api.session.Site;
import com.cleanwise.service.api.session.Store;
import com.cleanwise.service.api.util.BeanComparator;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.I18nUtil;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.UserRightsTool;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.AccCategoryToCostCenterView;
import com.cleanwise.service.api.value.AccountData;
import com.cleanwise.service.api.value.AddressData;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.BusEntityDataVector;
import com.cleanwise.service.api.value.CatalogCategoryData;
import com.cleanwise.service.api.value.CatalogCategoryDataVector;
import com.cleanwise.service.api.value.CategoryInfoView;
import com.cleanwise.service.api.value.ContentData;
import com.cleanwise.service.api.value.CountryData;
import com.cleanwise.service.api.value.CurrencyData;
import com.cleanwise.service.api.value.GenericReportColumnView;
import com.cleanwise.service.api.value.GenericReportColumnViewVector;
import com.cleanwise.service.api.value.GenericReportResultView;
import com.cleanwise.service.api.value.GenericReportResultViewVector;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.ItemMappingData;
import com.cleanwise.service.api.value.ItemMappingDataVector;
import com.cleanwise.service.api.value.MenuItemView;
import com.cleanwise.service.api.value.OrderData;
import com.cleanwise.service.api.value.OrderGuideData;
import com.cleanwise.service.api.value.OrderGuideDataVector;
import com.cleanwise.service.api.value.OrderItemData;
import com.cleanwise.service.api.value.PairView;
import com.cleanwise.service.api.value.PairViewVector;
import com.cleanwise.service.api.value.ProductData;
import com.cleanwise.service.api.value.ProductDataVector;
import com.cleanwise.service.api.value.PropertyData;
import com.cleanwise.service.api.value.ShoppingCartData;
import com.cleanwise.service.api.value.ShoppingCartItemData;
import com.cleanwise.service.api.value.ShoppingCartItemDataVector;
import com.cleanwise.service.api.value.ShoppingItemRequest;
import com.cleanwise.service.api.value.SiteData;
import com.cleanwise.service.api.value.SiteInventoryConfigViewVector;
import com.cleanwise.service.api.value.SiteInventoryInfoView;
import com.cleanwise.service.api.value.StoreData;
import com.cleanwise.service.api.value.UserData;
import com.cleanwise.view.forms.OnsiteServicesForm;
import com.cleanwise.view.forms.UserShopForm;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.ClwCustomizer;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.ContactUsInfo;
import com.cleanwise.view.utils.DisplayListSort;
import com.cleanwise.view.utils.Pager;
import com.cleanwise.view.utils.ReportRequest;
import com.cleanwise.view.utils.ReportWritter;
import com.cleanwise.view.utils.SessionAttributes;
import com.cleanwise.view.utils.SessionTool;
import com.cleanwise.view.utils.ShopTool;
import com.cleanwise.view.utils.SimplePager;
import com.cleanwise.view.utils.ValidateActionMessage;
import com.cleanwise.view.utils.pdf.PdfCatalogProduct;
import com.cleanwise.view.utils.pdf.PdfOrderGuide;

/**
 *  Class description.
 *
 *@author     Dvieira
 *@created    July 7, 2004
 */
public class UserShopLogic {

    private static final Logger log = Logger.getLogger(UserShopLogic.class);

    /**
     *  Description of the Method
     *
     *@param  request        Description of the Parameter
     *@param  pForm          Description of the Parameter
     *@return                Description of the Return Value
     *@exception  Exception  Description of the Exception
     */
    public static ActionErrors init(HttpServletRequest request,
            UserShopForm pForm)
            throws Exception {

        ActionErrors ae = new ActionErrors();
        int shopm = -1;
        CleanwiseUser appUser = ShopTool.getCurrentUser(request);

        if (pForm == null) {
            String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.noShipToChosen",null);
            ae.add("error",
                    new ActionError("error.systemError",
                    errorMess));
            return ae;
        }

        shopm = pForm.getShopMethod();
        pForm.setQuantity(new String[1]);

        if (-1 == shopm) {
            shopm = Constants.SHOP_BY_ORDER_GUIDE;
        }

        switch (shopm) {

            case Constants.SHOP_BY_CATALOG:
                ae = initCatalogShopping(request, pForm,true);

                break;
            case Constants.SHOP_BY_CATEGORY:
                ae = initCategoryShopping(request, pForm);
                break;
            default:
                ae = initOrderGuide(request, pForm);
        }
        return ae;
    }

/**
 * Populate the form with a map of categories to items
 * @param request
 * @param pForm
 * @return
 * @throws Exception
 */
    public static ActionErrors populateCategoryToItemMap(HttpServletRequest request,
            UserShopForm pForm) throws Exception {
        ActionErrors ae = new ActionErrors();

        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);

        if (factory == null) {
            ae.add("error", new ActionError("error.systemError", "No Ejb access"));
            return ae;
        }

        CatalogInformation catalogInfEjb = null;
        try {
            catalogInfEjb = factory.getCatalogInformationAPI();
        } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
            ae.add("error", new ActionError("error.systemError", "No catalog Ejb pointer"));
            return ae;
        }
        Integer catalogId = (Integer) session.getAttribute(Constants.CATALOG_ID);
        if (catalogId == null) {
            ae.add("error", new ActionError("error.systemError", "No " + Constants.CATALOG_ID + " session attrbute found"));
            return ae;
        }
        int siteId = pForm.getAppUser().getSite().getSiteId();
        AccCategoryToCostCenterView categoryToCostCenter = SessionTool.getCategoryToCostCenterView(session, siteId);
        Map<Integer,ProductDataVector> categoryToItemMap = catalogInfEjb.getCatalogCategoryToItemMap(siteId, 
        		Utility.toIdVector(pForm.getContractCategoryIds()), 
                ShopTool.createShoppingItemRequest(request), categoryToCostCenter);
        Iterator<Integer> categoryIdIterator = categoryToItemMap.keySet().iterator();
        while (categoryIdIterator.hasNext()) {
        	Integer categoryId = categoryIdIterator.next();
        	ProductDataVector products = categoryToItemMap.get(categoryId);
        	ProductDataVector updatedProducts = getProductInfo(catalogInfEjb, products, siteId, categoryToCostCenter);
        	categoryToItemMap.put(categoryId, updatedProducts);
        }
        pForm.setCatalogMenuCategoryToItemMap(categoryToItemMap);
    	return ae;
    }

    //Jd begin
    /**
     *  Description of the Method
     *
     *@param  request  Description of the Parameter
     *@param  pForm    Description of the Parameter
     *@return          Description of the Return Value
     */
    /*
    public static ActionErrors initJd(HttpServletRequest request,
            UserShopForm pForm) {
        ActionErrors ae = new ActionErrors();
        CleanwiseUser cu = pForm.getAppUser();
        SiteData site = cu.getSite();
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
    */
    //Jd end

    /**
     * Description of the Method
     *
     * @param request Description of the Parameter
     * @param pForm   Description of the Parameter
     * @return Description of the Return Value
     * @throws Exception Description of the Exception
     */
    public static ActionErrors initCatalogShopping(HttpServletRequest request, UserShopForm pForm, boolean getProducts) throws Exception {

        ActionErrors ae = new ActionErrors();

        HttpSession session = request.getSession();

        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        if (factory == null) {
            ae.add("error", new ActionError("error.systemError", "No Ejb access"));
            return ae;
        }

        CatalogInformation catalogInfEjb;
        try {
            catalogInfEjb = factory.getCatalogInformationAPI();
        } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
            exc.printStackTrace();
            ae.add("error", new ActionError("error.systemError", "No catalog Ejb pointer"));
            return ae;
        }

        ContractInformation contractInfEjb;
        try {
            contractInfEjb = factory.getContractInformationAPI();
        } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
            ae.add("error", new ActionError("error.systemError", "No contract Ejb pointer"));
            return ae;
        }

        ShoppingServices shoppingServEjb;
        try {
            shoppingServEjb = factory.getShoppingServicesAPI();
        } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
            ae.add("error", new ActionError("error.systemError", "No shopping services Ejb pointer"));
            return ae;
        }

        ae = init(request, pForm, catalogInfEjb, contractInfEjb, shoppingServEjb);
        if (ae.size() > 0) {
            return ae;
        }

        CleanwiseUser appUser = pForm.getAppUser();

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

        boolean contractOnly = appUser.isUserOnContract();
        if (contractOnly && contractId == 0) {
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

        //initialize category navigation;
        CatalogCategoryDataVector categories = new CatalogCategoryDataVector();
        ProductDataVector products = new ProductDataVector();
        try {
            categories = catalogInfEjb.getTopCatalogCategories(catalogId);
            if(getProducts){
            	//have not tested all code paths yet.  It is unknown what this code
            	//is actually here for, but it does not seem to be used from the
            	//shop catalog section, and causes performance issues when the catalog is big
	            products = sortProducts(storeTypeProperty.getValue(),
	                    catalogInfEjb.getTopCatalogProducts(catalogId, appUser.getSite().getSiteId(), SessionTool.getCategoryToCostCenterView(session,appUser.getSite().getSiteId() )),
	                    pForm.getOrderBy());
            }else{
            	products = new ProductDataVector();
            }
        } catch (Exception exc) {
            exc.printStackTrace();
            ae.add("error", new ActionError("error.systemError", exc.getMessage()));
            return ae;
        }

        if (pForm.getAppUser().isUserOnContract() || pForm.getAppUser().isSpecialPermissionRequired()) {
            categories = filterShooppingCategories(pForm, categories);
            products = filterShoppingProducts(pForm, products);
        }
        
        //select manufacturers
        BusEntityDataVector manufacturers;
        try {
            manufacturers = shoppingServEjb.getCatalogManufacturers(ShopTool.createShoppingItemRequest(request));
        } catch (Exception exc) {
            exc.printStackTrace();
            ae.add("error", new ActionError("error.systemError", exc.getMessage()));
            return ae;
        }

        pForm.setManufacturers(manufacturers);

        ArrayList<String> menuMfgLables = new ArrayList<String>();
        ArrayList<String> menuMfgOptions = new ArrayList<String>();

        for (int ii = 0; ii < manufacturers.size(); ii++) {
            BusEntityData beD = (BusEntityData) manufacturers.get(ii);
            menuMfgLables.add(beD.getShortDesc());
            menuMfgOptions.add("" + beD.getBusEntityId());
        }

        pForm.setMenuMfgLabels(menuMfgLables);
        pForm.setMenuMfgOptions(menuMfgOptions);

        List categoryPath = new LinkedList();
        pForm.setCategoryPath(categoryPath);

        List categoryDataPath = new LinkedList();
        pForm.setCategoryDataPath(categoryDataPath);

        categories.sort("CatalogCategoryShortDesc");

        List categoryList = new LinkedList();
        categoryList.addAll(categories);
        pForm.setCategoryList(categoryList);

        List productList = new LinkedList();
        productList.addAll(products);
        pForm.setProductList(products);

        ae = prepareCartItems(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }

        //Clear quantities
        String[] qty = pForm.getQuantity();
        for (int ii = 0; ii < qty.length; ii++) {
            qty[ii] = "";
        }
        pForm.setQuantity(qty);

        int[] ids = pForm.getItemIds();
        for (int ii = 0; ii < ids.length; ii++) {
            ids[ii] = 0;
        }

        pForm.setItemIds(ids);
        pForm.setNavigationFlag(true);
        pForm.setShopMethod(Constants.SHOP_BY_CATALOG);

        /* for new categories showing */
        int[] contractCategoryIds = pForm.getContractCategoryIds();

        MenuItemView root = catalogInfEjb.getCatalogMenu(catalogId,
            Utility.toIdVector(contractCategoryIds),
            "../store/shop.do?action=navigatev2&categoryKey={0}");

        pForm.setCatalogMenu(root);
        pForm.setMultiLevelCategoryExists(isMultiLevelExists(root));
        pForm.setCategoryPath(new ArrayList());
        pForm.setShopNavigatorInfo(UserShopLogic.createShopNavigationInfo());

        return ae;
    }


    //********************************************************************
    /**
     *  Description of the Method
     *
     *@param  request  Description of the Parameter
     *@param  pForm    Description of the Parameter
     *@return          Description of the Return Value
     */
    public static ActionErrors initOrderGuide(HttpServletRequest request,
            UserShopForm pForm) {
        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess) session.getAttribute(
                Constants.APIACCESS);

        if (factory == null) {
            ae.add("error",
                    new ActionError("error.systemError", "No Ejb access"));

            return ae;
        }

        CatalogInformation catalogInfEjb = null;

        try {
            catalogInfEjb = factory.getCatalogInformationAPI();
        } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
            exc.printStackTrace();
            ae.add("error",
                    new ActionError("error.systemError",
                    "No catalog Ejb pointer"));

            return ae;
        }

        ContractInformation contractInfEjb = null;

        try {
            contractInfEjb = factory.getContractInformationAPI();
        } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
            ae.add("error",
                    new ActionError("error.systemError",
                    "No contract Ejb pointer"));

            return ae;
        }

        ShoppingServices shoppingServEjb = null;

        try {
            shoppingServEjb = factory.getShoppingServicesAPI();
        } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
            ae.add("error",
                    new ActionError("error.systemError",
                    "No shopping services Ejb pointer"));

            return ae;
        }

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(
                Constants.APP_USER);

        if (appUser == null) {
            ae.add("error",
                    new ActionError("error.systemError",
                    "No session " + Constants.APP_USER +
                    " object found "));

            return ae;
        }

        StoreData store = appUser.getUserStore();

        if (store == null) {
            ae.add("error",
                    new ActionError("error.systemError",
                    "No store information was loaded"));

            return ae;
        }

        PropertyData storeTypeProperty = store.getStoreType();

        if (storeTypeProperty == null) {
            ae.add("error",
                    new ActionError("error.systemError",
                    "No store type information was loaded"));

            return ae;
        }

        UserData user = appUser.getUser();
        if (appUser.getSite() == null) {
            String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.noShipToAddressDefined",null);
            ae.add("error",
                    new ActionError("error.simpleGenericError",errorMess));

            return ae;
        }

        int accountId = appUser.getSite().getAccountId();
        int siteId = appUser.getSite().getBusEntity().getBusEntityId();
        Integer catalogIdI = (Integer) session.getAttribute(
                Constants.CATALOG_ID);

        if (catalogIdI == null) {
            ae.add("error",
                    new ActionError("error.systemError",
                    "No session " + Constants.CATALOG_ID +
                    " object found "));

            return ae;
        }

        int catalogId = catalogIdI.intValue();

        Integer contractIdI = (Integer) session.getAttribute(
                Constants.CONTRACT_ID);
        int contractId = 0;

        if (catalogIdI != null) {
            contractId = contractIdI.intValue();
        }

        //initialize order guide list
        OrderGuideDataVector userOrderGuideDV = null;
        OrderGuideDataVector templateOrderGuideDV = null;
        OrderGuideDataVector customOrderGuideDV = null;

        int userid = user.getUserId();
  if ( appUser.getSite().isSetupForSharedOrderGuides() ) {
      // This will force the system to get all available order
      // schedules and order guides.
      userid = 0;
  }

        try {

            userOrderGuideDV     = shoppingServEjb.getUserOrderGuides(userid,siteId);
            templateOrderGuideDV = shoppingServEjb.getTemplateOrderGuides(catalogId, siteId);
            customOrderGuideDV = shoppingServEjb.getCustomOrderGuides(accountId, siteId);

            pForm.setUserOrderGuides(userOrderGuideDV);
            pForm.setTemplateOrderGuides(templateOrderGuideDV);
            pForm.setCustomOrderGuides(customOrderGuideDV);

        } catch (RemoteException exc) {
            exc.printStackTrace();
            ae.add("error",
                    new ActionError("error.systemError", exc.getMessage()));

            return ae;
        }

        int orderGuideId = pForm.getOrderGuideId();
        ShoppingCartItemDataVector items = null;

        if (orderGuideId > 0 &&
                (!appUser.getSite().hasInventoryShoppingOn()
                        || (appUser.getSite().hasInventoryShoppingOn()
                        && appUser.getSite().hasModernInventoryShopping()))) {

            try {
            	pForm.setOrderBy(0);
                int orderBy = pForm.getOrderBy();

                if(pForm.isCustomOrderGuide(orderGuideId)){
                	orderBy =  Constants.ORDER_BY_CUSTOM_CATEGORY;
                }

                Properties configProps = ClwCustomizer.getUiProperties(request);
                String cfgSortBy = configProps.getProperty("orderGuideSecondarySorting");

                if (cfgSortBy != null && cfgSortBy.equalsIgnoreCase("ORDER_BY_CATEGORY_AND_SKU")) {
                    orderBy = Constants.ORDER_BY_CATEGORY_AND_SKU;
                }

                items = shoppingServEjb.getOrderGuideItems(storeTypeProperty.getValue(),
                        appUser.getSite(),
                        orderGuideId,
                        ShopTool.createShoppingItemRequest(request),
                        orderBy,SessionTool.getCategoryToCostCenterView(session, appUser.getSite().getSiteId()));

              } catch (RemoteException exc) {
                  exc.printStackTrace();
                  ae.add("error", new ActionError("error.systemError", exc.getMessage()));
                  return ae;
              } catch (IOException ioe) {
                  ioe.printStackTrace();
                  ae.add("error", new ActionError("error.systemError", ioe.getMessage()));
                  return ae;
              } catch (Exception e) {
                    e.printStackTrace();
                    ae.add("error", new ActionError("error.systemError", e.getMessage()));
                    return ae;
              }
              pForm.setCartItems(items);
              pForm.setOrderGuideCartItems(items);

            for (int ii = 0; ii < userOrderGuideDV.size(); ii++) {

                OrderGuideData ogD = (OrderGuideData) userOrderGuideDV.get(ii);

                if (ogD.getOrderGuideId() == orderGuideId) {
                    pForm.setCartItems(pForm.getOrderGuideCartItems());
                    pForm.setShopMethod(Constants.SHOP_BY_ORDER_GUIDE);
                    return ae;
                }
            }

            for (int ii = 0; ii < templateOrderGuideDV.size(); ii++) {

                OrderGuideData ogD = (OrderGuideData) templateOrderGuideDV.get(ii);

                if (ogD.getOrderGuideId() == orderGuideId) {
                    pForm.setCartItems(pForm.getOrderGuideCartItems());
                    pForm.setShopMethod(Constants.SHOP_BY_ORDER_GUIDE);
                    return ae;
                }
            }

            for (int ii = 0; ii < customOrderGuideDV.size(); ii++) {

                OrderGuideData ogD = (OrderGuideData) customOrderGuideDV.get(ii);

                if (ogD.getOrderGuideId() == orderGuideId) {
                    pForm.setCartItems(pForm.getOrderGuideCartItems());
                    pForm.setShopMethod(Constants.SHOP_BY_ORDER_GUIDE);
                    return ae;
                }
            }
        }

        if (pForm.getContractCategoryIds().length == 0) // prevent call twice. It may have called from initCategoryShopping()
        	ae = init(request, pForm, catalogInfEjb, contractInfEjb, shoppingServEjb);

        pForm.setShopMethod(Constants.SHOP_BY_ORDER_GUIDE);
        pForm.setQuantity(new String[pForm.getPageSize()]);

        pForm.setOrderGuideId(0);
        pForm.setUserOrderGuideId("-1");
        pForm.setTemplateOrderGuideId("-1");
        pForm.setCustomOrderGuideId("-1");
        pForm.setModifiedName(null);
        return ae;
    }


    //********************************************************************
    /**
     *  Description of the Method
     *
     *@param  request  Description of the Parameter
     *@param  pForm    Description of the Parameter
     *@return          Description of the Return Value
     */
    public static ActionErrors initAndSelectOrderGuide(HttpServletRequest request,
            UserShopForm pForm) {
        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);

        if (factory == null) {
            ae.add("error", new ActionError("error.systemError", "No Ejb access"));
            return ae;
        }

        ShoppingServices shoppingServEjb = null;

        try {
            shoppingServEjb = factory.getShoppingServicesAPI();
        } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
            ae.add("error",
                    new ActionError("error.systemError",
                    "No shopping services Ejb pointer"));

            return ae;
        }

        // check that app user not null, contract id not null and catalog id not null
        ae = init(request, pForm, null, null, shoppingServEjb);
        if (ae.size() > 0) {
          return ae;
        }


        CleanwiseUser appUser = pForm.getAppUser();
        UserData user = appUser.getUser();
        if (appUser.getSite() == null) {
            String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.noShipToAddressDefined",null);
            ae.add("error",
                    new ActionError("error.simpleGenericError",errorMess));

            return ae;
        }

        int accountId = appUser.getSite().getAccountId();
        int siteId = appUser.getSite().getBusEntity().getBusEntityId();
        int catalogId = ((Integer) session.getAttribute(Constants.CATALOG_ID)).intValue();

        initManufactures(shoppingServEjb, pForm, session);

        //initialize order guide list
        OrderGuideDataVector userOrderGuideDV = null;
        OrderGuideDataVector templateOrderGuideDV = null;
        OrderGuideDataVector customOrderGuideDV = null;

        int userid = user.getUserId();
        if ( appUser.getSite().isSetupForSharedOrderGuides() ) {
          // This will force the system to get all available order
          // schedules and order guides.
          userid = 0;
        }

        try {
            userOrderGuideDV = shoppingServEjb.getUserOrderGuides( userid,catalogId,siteId);
            templateOrderGuideDV = shoppingServEjb.getTemplateOrderGuides(catalogId, siteId);
            customOrderGuideDV = shoppingServEjb.getCustomOrderGuides(accountId, siteId);
            pForm.setUserOrderGuides(userOrderGuideDV);
            pForm.setTemplateOrderGuides(templateOrderGuideDV);
            pForm.setCustomOrderGuides(customOrderGuideDV);
        } catch (RemoteException exc) {
            exc.printStackTrace();
            ae.add("error",
                    new ActionError("error.systemError", exc.getMessage()));
            return ae;
        }

        int orderGuideId = pForm.getOrderGuideId();
/*
        if (orderGuideId > 0&&(appUser.getSite().hasInventoryShoppingOn() == false
                              ||(appUser.getSite().hasInventoryShoppingOn()
                              &&appUser.getSite().hasModernInventoryShopping()))) {

            for (int ii = 0; ii < userOrderGuideDV.size(); ii++) {

                OrderGuideData ogD = (OrderGuideData) userOrderGuideDV.get(ii);

                if (ogD.getOrderGuideId() == orderGuideId) {
                    pForm.setCartItems(pForm.getOrderGuideCartItems());
                    pForm.setShopMethod(Constants.SHOP_BY_ORDER_GUIDE);
                    return ae;
                }
            }

            for (int ii = 0; ii < templateOrderGuideDV.size(); ii++) {

                OrderGuideData ogD = (OrderGuideData) templateOrderGuideDV.get(ii);

                if (ogD.getOrderGuideId() == orderGuideId) {
                    pForm.setCartItems(pForm.getOrderGuideCartItems());
                    pForm.setShopMethod(Constants.SHOP_BY_ORDER_GUIDE);
                    return ae;
                }
            }

        }
*/
        pForm.setShopMethod(Constants.SHOP_BY_ORDER_GUIDE);
        pForm.setQuantity(new String[pForm.getPageSize()]);

        // customizing for XPDEX/Apple Store
        if (userOrderGuideDV.size() + templateOrderGuideDV.size() + customOrderGuideDV.size() == 1 ) {
          String orderGuideIdS = "";
         if (customOrderGuideDV.size() == 1) {
             orderGuideIdS = "" + ((OrderGuideData) customOrderGuideDV.get(0)).getOrderGuideId();
             pForm.setCustomOrderGuideId(orderGuideIdS);
         } else if (userOrderGuideDV.size() == 1) {
            orderGuideIdS = "" + ((OrderGuideData) userOrderGuideDV.get(0)).getOrderGuideId();
            pForm.setUserOrderGuideId(orderGuideIdS);
          } else {
            orderGuideIdS = "" + ((OrderGuideData) templateOrderGuideDV.get(0)).getOrderGuideId();
            pForm.setTemplateOrderGuideId(orderGuideIdS);
          }
          try {
            ae = orderGuideSelect(request, pForm);
          } catch (Exception e) {
            e.printStackTrace();
          }
        } else {
          pForm.setOrderGuideId(0);
          pForm.setUserOrderGuideId("-1");
          pForm.setTemplateOrderGuideId("-1");
          pForm.setCustomOrderGuideId("-1");
        }
        return ae;
    }


    //*****************************************************************************
    /**
     * Description of the Method
     *
     * @param request         Description of the Parameter
     * @param pForm           Description of the Parameter
     * @param catalogInfEjb   Description of the Parameter
     * @param contractInfEjb  Description of the Parameter
     * @param shoppingServEjb Description of the Parameter
     * @return Description of the Return Value
     */
    private static ActionErrors init(HttpServletRequest request,
                                     UserShopForm pForm,
                                     CatalogInformation catalogInfEjb,
                                     ContractInformation contractInfEjb,
                                     ShoppingServices shoppingServEjb) {


        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        if (appUser == null) {
            ae.add("error", new ActionError("error.systemError", "No session user information found"));
            return ae;
        }

        pForm.setAppUser(appUser);

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


        pForm.setItemIds(new int[0]);
        pForm.setQuantity(new String[0]);

        return ae;
    }

    //*****************************************************************************

    /*
     *
     */
    /**
     *  Description of the Method
     *
     *@param  pForm        Description of the Parameter
     *@param  pCategories  Description of the Parameter
     *@return              Description of the Return Value
     */
    private static CatalogCategoryDataVector filterShooppingCategories(UserShopForm pForm,  CatalogCategoryDataVector pCategories) {

        int[] filter = pForm.getContractCategoryIds();
        CatalogCategoryDataVector filteredCategories = new CatalogCategoryDataVector();
        for (int ii = 0; ii < pCategories.size(); ii++) {
            CatalogCategoryData categoryD = (CatalogCategoryData) pCategories.get(ii);
            int id = categoryD.getCatalogCategoryId();
            for (int jj = 0; jj < filter.length; jj++) {
                if (id == filter[jj]) {
                    filteredCategories.add(categoryD);
                    break;
                }
            }
        }
        return filteredCategories;
    }


    /*
     *
     */

    /**
     * Description of the Method
     *
     * @param pForm     Description of the Parameter
     * @param pProducts Description of the Parameter
     * @return Description of the Return Value
     */
    private static ProductDataVector filterShoppingProducts(UserShopForm pForm, ProductDataVector pProducts) {
        int[] filter = pForm.getContractItemIds();
        ProductDataVector filteredProducts = new ProductDataVector();
        for (int ii = 0; ii < pProducts.size(); ii++) {
            ProductData productD = (ProductData) pProducts.get(ii);
            int id = productD.getProductId();
            for (int jj = 0; jj < filter.length; jj++) {
                if (id == filter[jj]) {
                    filteredProducts.add(productD);
                    break;
                }
                if (id < filter[jj]) {
                    break;
                }
            }
        }
        return filteredProducts;
    }


    //*****************************************************************************

    /**
     *  Processes catalog down navigating
     *
     *@param  request        Description of the Parameter
     *@param  pForm          Description of the Parameter
     *@return                Description of the Return Value
     *@exception  Exception  Description of the Exception
     */
    public static ActionErrors select(HttpServletRequest request, UserShopForm pForm) throws Exception {

        log.info("select()=> BEGIN");

        ActionErrors ae = new ActionErrors();

        CleanwiseUser appUser = pForm.getAppUser();

        int categoryId = pForm.getNavigateCategoryId();
        int siteId = pForm.getAppUser().getSite().getSiteId();

        HttpSession session = request.getSession();

        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        if (factory == null) {
            ae.add("error", new ActionError("error.systemError", "No Ejb access"));
            return ae;
        }

        CatalogInformation catalogInfEjb = null;
        try {
            catalogInfEjb = factory.getCatalogInformationAPI();
        } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
            exc.printStackTrace();
            ae.add("error", new ActionError("error.systemError", "No catalog Ejb pointer"));
            return ae;
        }

        if (catalogInfEjb == null) {
            ae.add("error", new ActionError("error.systemError", "No catalog Ejb found"));
            return ae;
        }

        ShoppingServices shoppingServEjb;
        try {
            shoppingServEjb = factory.getShoppingServicesAPI();
        } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
            ae.add("error", new ActionError("error.systemError", "No shopping services Ejb pointer"));
            return ae;
        }

        if (shoppingServEjb == null) {
            ae.add("error", new ActionError("error.systemError", "No shopping services Ejb found"));
            return ae;
        }

        AccountData account = appUser.getUserAccount();
        if (account == null) {
            ae.add("error", new ActionError("error.systemError", "No account information was loaded"));
            return ae;
        }

        Integer catalogId = (Integer) session.getAttribute(Constants.CATALOG_ID);
        if (catalogId == null) {
            ae.add("error",  new ActionError("error.systemError", "No " + Constants.CATALOG_ID + " session attrbute found"));
            return ae;
        }

        Integer contractId = Utility.intNN((Integer) session.getAttribute(Constants.CONTRACT_ID));
        if (appUser.isUserOnContract() && contractId == 0) {
            ae.add("error", new ActionError("error.systemError", "There is no contract found"));
            return ae;
        }

        int accountCatalogId = account.getAccountCatalogId();
        if (appUser.isSpecialPermissionRequired() && accountCatalogId == 0) {
            ae.add("error", new ActionError("error.systemError", "There is no accaunt catalog found"));
            return ae;
        }

        ae = navigateDown(shoppingServEjb, catalogInfEjb, request, pForm, catalogId, categoryId);
        if (ae.size() > 0) {
            return ae;
        }

        ArrayList<CategoryInfoView> path= new ArrayList<CategoryInfoView>();
        PairViewVector navInfo = new PairViewVector();
        String categoryIdS = ""+pForm.getNavigateCategoryId();

        pupulateCategoryActivePath(pForm.getCatalogMenu(), categoryIdS, path);
        pupulateShopNavigationInfo(path,pForm.getCatalogMenu(),navInfo);
        refreshMenuStatus(pForm.getCatalogMenu(), (ArrayList) path.clone());

        log.info("select()=> path: "+path);

        pForm.setCategoryPath(path);

        Integer categoryKeyInt = Integer.parseInt(categoryIdS);
        IdVector availableCategoryIds = Utility.toIdVector(pForm.getContractCategoryIds());
        int storeCatalogId = catalogInfEjb.getStoreCatalogId(pForm.getAppUser().getUserStore().getStoreId());


        ProductDataVector products = new ProductDataVector();
        if (availableCategoryIds.contains(categoryKeyInt)) {
            products = getProductInfo(catalogInfEjb,
                    shoppingServEjb.getCategoryChildProducts(storeCatalogId, siteId,categoryKeyInt,
                            ShopTool.createShoppingItemRequest(request), null), siteId,
                            SessionTool.getCategoryToCostCenterView(session, siteId));
        }

        pForm.setProductList(products);

        ae = prepareCartItems(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }

        pForm.setNavigationFlag(true);
        pForm.clearSearchFilters();

        log.info("select()=> END.");

        return ae;
    }


    //********************************************************************************

    /**
     *  Processes catalog up navigating
     *@depricated was replaced on navigatev2
     *@param  request        Description of the Parameter
     *@param  pForm          Description of the Parameter
     *@return                Description of the Return Value
     *@exception  Exception  Description of the Exception
     *
     */
    public static ActionErrors navigate(HttpServletRequest request,UserShopForm pForm)
            throws Exception {


        log.info("navigate()=> BEGIN");

        ActionErrors ae = new ActionErrors();
        int categoryId = pForm.getNavigateCategoryId();

        log.info("navigate()=> categoryId:"+categoryId);

        if (categoryId == 0) {
            ae = navigateUp(request, pForm);
        } else {

            List categoryPath = pForm.getCategoryDataPath();
            //List categoryPath = pForm.getCategoryList();
            int ii = 0;
            for (; ii < categoryPath.size(); ii++) {
                CatalogCategoryData ccD = (CatalogCategoryData) categoryPath.get(ii);
                if (ccD.getCatalogCategoryId() == categoryId) {
                    ae = navigateUp(request, pForm);
                    if (ae.size() > 0) {
                        return ae;
                    }
                    break;
                }
            }

            log.info("navigate()=> ii:"+ii+", categoryPath.size(): "+categoryPath.size());

            if (ii == categoryPath.size()) {
                ae = select(request, pForm);
                if (ae.size() > 0) {
                    return ae;
                }
            }
        }

        log.info("navigate()=> END.");

        return ae;
    }

    public static ActionErrors navigatev2(HttpServletRequest request, UserShopForm pForm) throws Exception {

        log.info("navigatev2()=> BEGIN");

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();


        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        if (factory == null) {
            ae.add("error", new ActionError("error.systemError", "No Ejb access"));
            return ae;
        }

        CatalogInformation catalogEjb = factory.getCatalogInformationAPI();
        ShoppingServices shoppingServEjb = factory.getShoppingServicesAPI();

        Integer catalogIdI = (Integer) session.getAttribute(Constants.CATALOG_ID);
        if (catalogIdI == null) {
            ae.add("error", new ActionError("error.systemError", "No " + Constants.CATALOG_ID + " session attrbute found"));
            return ae;
        }

        String categoryKey = request.getParameter("categoryKey");

        log.info("navigatev2()=> categoryKey:" + categoryKey);

        if (Utility.isSet(categoryKey)) {

            ArrayList<CategoryInfoView> path = new ArrayList<CategoryInfoView>();
            ArrayList<CategoryInfoView> relPath = new ArrayList<CategoryInfoView>();
            PairViewVector navInfo = new PairViewVector();

            refreshMenuDisplayStaus(pForm.getCatalogMenu(),
                    null,
                    MenuItemView.DISPLAY_STATUS.UNAVAILABLE,
                    true);

            pupulateCategoryActivePath(pForm.getCatalogMenu(), categoryKey, path);
            pupulateShopNavigationInfo(path, pForm.getCatalogMenu(), navInfo);

            log.info("navigatev2 => path: "+path);

            refreshMenuDisplayStaus(pForm.getCatalogMenu(),
                    path,
                    MenuItemView.DISPLAY_STATUS.CLOSE,
                    true);

            for(int i=1;i<path.size();i++){
                relPath.add(path.get(i));
            }

            refreshMenuDisplayStaus(pForm.getCatalogMenu(),
                    relPath,
                    MenuItemView.DISPLAY_STATUS.BLOCK_FOR_CLOSE,
                    false);

            Integer categoryKeyInt = Integer.parseInt(categoryKey);
            IdVector availableCategoryIds = Utility.toIdVector(pForm.getContractCategoryIds());

            ProductDataVector prods = new ProductDataVector();
            int siteId = pForm.getAppUser().getSite().getSiteId();
            int storeCatalogId = catalogEjb.getStoreCatalogId(pForm.getAppUser().getUserStore().getStoreId());

            if (availableCategoryIds.contains(categoryKeyInt)) {
                if (siteId > 0) {
                    prods = shoppingServEjb.getCategoryChildProducts(storeCatalogId, siteId,
                            categoryKeyInt,
                            ShopTool.createShoppingItemRequest(request),
                            SessionTool.getCategoryToCostCenterView(session, siteId));
                } else {
                    prods = shoppingServEjb.getCategoryChildProducts(storeCatalogId, 0,
                            categoryKeyInt,
                            ShopTool.createShoppingItemRequest(request),
                            SessionTool.getCategoryToCostCenterView(session, siteId));
                }
            }      
            
            ProductDataVector products = getProductInfo(catalogEjb, prods, siteId,
                            SessionTool.getCategoryToCostCenterView(session, siteId));

            StoreData store = pForm.getAppUser().getUserStore();
            PropertyData storeTypeProperty = store.getStoreType();
            if (pForm.getOrderBy() == 0) {
                pForm.setOrderBy(Constants.ORDER_BY_CATEGORY);
            }

            products = sortProducts(storeTypeProperty.getValue(), products, pForm.getOrderBy());

            pForm.setProductList(products);
            pForm.setCategoryKey(categoryKey);
            pForm.setCategoryPath(path);
            pForm.setShopNavigatorInfo(navInfo);

            ae = prepareCartItems(request, pForm);
            if (ae.size() > 0) {
                return ae;
            }
        }

        log.info("navigatev2()=> END.");

        return ae;
    }

    private static void refreshMenuDisplayStaus(MenuItemView pItem,
                                                ArrayList<CategoryInfoView> pActivePath,
                                                String pStatus,
                                                boolean pIncludeChilds) {

        if (pActivePath != null) {
            int i = 0;
            for (CategoryInfoView oPath : pActivePath) {
                MenuItemView pathItem = getMenuItem(pItem, oPath.getCategoryKey());
                pathItem.setDisplayStatus(pStatus);
                if ((++i) == pActivePath.size() && pIncludeChilds) {
                    refreshMenuDisplayStaus(pathItem, null, pStatus, pIncludeChilds);
                }
            }
        } else {
            pItem.setDisplayStatus(pStatus);
            if (pIncludeChilds && pItem.getSubItems() != null && !pItem.getSubItems().isEmpty()) {
                for (int i = 0; i < pItem.getSubItems().size(); i++) {
                    MenuItemView subItem = (MenuItemView) pItem.getSubItems().get(i);
                    refreshMenuDisplayStaus(subItem, pActivePath, pStatus, pIncludeChilds);
                }
            }
        }
    }


    //***********************************************************************
    /**
     *  Description of the Method
     *
     *@param  request        Description of the Parameter
     *@param  pForm          Description of the Parameter
     *@return                Description of the Return Value
     *@exception  Exception  Description of the Exception
     */
    public static ActionErrors navigateUp(HttpServletRequest request, UserShopForm pForm) throws Exception {

        log.info("navigateUp()=> BEGIN");

        ActionErrors ae = new ActionErrors();

        int categoryId = pForm.getNavigateCategoryId();

        HttpSession session = request.getSession();

        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        if (factory == null) {
            ae.add("error", new ActionError("error.systemError", "No Ejb access"));
            return ae;
        }

        CatalogInformation catalogInfEjb = null;
        try {
            catalogInfEjb = factory.getCatalogInformationAPI();
        } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
            exc.printStackTrace();
            ae.add("error", new ActionError("error.systemError", "No catalog Ejb pointer"));
            return ae;
        }

        if (catalogInfEjb == null) {
            ae.add("error", new ActionError("error.systemError", "No catalog Ejb found"));
            return ae;
        }

        ContractInformation contractInfEjb = null;
        try {
            contractInfEjb = factory.getContractInformationAPI();
        } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
            ae.add("error", new ActionError("error.systemError", "No contract Ejb pointer"));
            return ae;
        }

        if (contractInfEjb == null) {
            ae.add("error", new ActionError("error.systemError", "No contract Ejb found"));
            return ae;
        }


        ShoppingServices shoppingServEjb;
        try {
            shoppingServEjb = factory.getShoppingServicesAPI();
        } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
            ae.add("error", new ActionError("error.systemError", "No shopping services Ejb pointer"));
            return ae;
        }

        if (shoppingServEjb == null) {
            ae.add("error", new ActionError("error.systemError", "No shopping services Ejb found"));
            return ae;
        }

        //Pickup selected category
        List categoryPath = pForm.getCategoryDataPath();
        //List categoryPath = pForm.getCategoryList();

        log.info("navigateUp()=> categoryId: " + categoryId);
        if (categoryId != 0) {

            int ii = 0;
            for (; ii < categoryPath.size(); ii++) {
                CatalogCategoryData ccD = (CatalogCategoryData) categoryPath.get(ii);
                if (ccD.getCatalogCategoryId() == categoryId) {
                    break;
                }
            }

            if (ii == categoryPath.size()) {
                String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.noCategoryWithSelectedId", null);
                ae.add("error", new ActionError("error.simpleGenericError", errorMess));
                return ae;
            }

            for (ii = ii + 1; ii < categoryPath.size();) {
                Object o = categoryPath.remove(ii);
                log.info("navigateUp()=> remove: " + o);
            }
        } else {
            categoryPath = new LinkedList();
        }

        Integer catalogId = (Integer) session.getAttribute(Constants.CATALOG_ID);
        if (catalogId == null) {
            ae.add("error", new ActionError("error.systemError", "No " + Constants.CATALOG_ID + " session attrbute found"));
            return ae;
        }

        StoreData store = pForm.getAppUser().getUserStore();
        if (store == null) {
            ae.add("error", new ActionError("error.systemError", "No store information was loaded"));
            return ae;
        }

        PropertyData storeTypeProperty = store.getStoreType();
        if (storeTypeProperty == null) {
            ae.add("error", new ActionError("error.systemError", "No store type information was loaded"));
            return ae;
        }

        //Category navigation;
        CatalogCategoryDataVector categories;
        ProductDataVector products;
        try {

            if (categoryId == 0) {
                categories = catalogInfEjb.getTopCatalogCategories(catalogId);
                products = sortProducts(storeTypeProperty.getValue(),
                        shoppingServEjb.getTopCatalogProducts(0, ShopTool.createShoppingItemRequest(request), SessionTool.getCategoryToCostCenterView(session, 0, catalogId)),
                        pForm.getOrderBy());
            } else {

                int siteId = 0;
                CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
                if (appUser != null && appUser.getSite() != null) {
                    siteId = appUser.getSite().getSiteId();
                }

                categories = catalogInfEjb.getCatalogChildCategories(catalogId, categoryId);
                products = sortProducts(storeTypeProperty.getValue(),
                        shoppingServEjb.getCatalogChildProducts(siteId,
                                categoryId,
                                ShopTool.createShoppingItemRequest(request),
                                SessionTool.getCategoryToCostCenterView(session, siteId, catalogId)),
                        pForm.getOrderBy());
            }
        } catch (Exception exc) {
            exc.printStackTrace();
            ae.add("error", new ActionError("error.systemError", exc.getMessage()));
            return ae;
        }

        if (pForm.getAppUser().isUserOnContract() || pForm.getAppUser().isSpecialPermissionRequired()) {
            categories = filterShooppingCategories(pForm, categories);
            products = filterShoppingProducts(pForm, products);
        }

        pForm.setOffset(0);

        //Clear quantities
        String[] qty = pForm.getQuantity();
        for (int ii = 0; ii < qty.length; ii++) {
            qty[ii] = "";
        }
        pForm.setQuantity(qty);

        int[] ids = pForm.getItemIds();
        for (int ii = 0; ii < ids.length; ii++) {
            ids[ii] = 0;
        }
        pForm.setItemIds(ids);

        pForm.setCategoryDataPath(categoryPath);

        List<String> categoryNamePath = new LinkedList<String>();
        for (int j = 0; j < categoryPath.size(); j++) {
            CatalogCategoryData catD = (CatalogCategoryData) categoryPath.get(j);
            categoryNamePath.add(catD.getCatalogCategoryShortDesc());
        }
        pForm.setCategoryPath(categoryNamePath);

        List categoryList = new LinkedList();
        categories.sort("CatalogCategoryShortDesc");
        categoryList.addAll(categories);
        pForm.setCategoryList(categoryList);

        List productList = new LinkedList();
        productList.addAll(products);
        pForm.setProductList(products);

        log.info("navigateUp()=> categoryList.size(): " + categoryList.size());
        log.info("navigateUp()=> productList.size(): " + productList.size());

        if (categoryList.size() == 1 && productList.size() == 0) {
            CatalogCategoryData ccD = (CatalogCategoryData) categoryList.get(0);
            ae = navigateDown(shoppingServEjb, catalogInfEjb, request, pForm, catalogId, ccD.getCatalogCategoryId());
        }

        ae = prepareCartItems(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }

        pForm.setNavigationFlag(true);
        pForm.clearSearchFilters();

        log.info("navigateUp()=> END.");

        return ae;
    }

    //******************************************************************************

    /*
     *
     */
    /**
     *  Description of the Method
     *
     *@param  pCatalogInfEjb  Description of the Parameter
     *@param  pForm           Description of the Parameter
     *@param  pCatalogId      Description of the Parameter
     *@param  pCategoryId     Description of the Parameter
     *@return                 Description of the Return Value
     */
    private static ActionErrors navigateDown(ShoppingServices pShoppingServEjb,
                                             CatalogInformation pCatalogInfEjb,
                                             HttpServletRequest request,
                                             UserShopForm pForm,
                                             int pCatalogId,
                                             int pCategoryId) {

        ActionErrors ae = new ActionErrors();

        //List categoryPath = pForm.getCategoryPath();
        List categoryList;
        LinkedList<CatalogCategoryData> categoryPath = new LinkedList<CatalogCategoryData>();
        CatalogCategoryDataVector categories = null;
        ProductDataVector products = null;

        int categoryId = pCategoryId;

        StoreData store = pForm.getAppUser().getUserStore();
        if (store == null) {
            ae.add("error", new ActionError("error.systemError", "No store information was loaded"));
            return ae;
        }

        PropertyData storeTypeProperty = store.getStoreType();
        if (storeTypeProperty == null) {
            ae.add("error", new ActionError("error.systemError", "No store type information was loaded"));
            return ae;
        }

        while (categoryId > 0) {

            //Category navigation;
            try {

                CatalogCategoryDataVector allCategories = pCatalogInfEjb.getTopCatalogCategories(pCatalogId);
                allCategories.add(pCatalogInfEjb.getCatalogCategory(pCatalogId, categoryId));
                allCategories.addAll(pCatalogInfEjb.getCatalogChildCategories(pCatalogId, pCategoryId));

                int ii = 0;

                for (; ii < allCategories.size(); ii++) {

                    CatalogCategoryData ccD = (CatalogCategoryData) allCategories.get(ii);

                    if (ccD.getCatalogCategoryId() == categoryId) {

                        int currentCatId = categoryId;
                        CatalogCategoryDataVector catVec = new CatalogCategoryDataVector();

                        while (currentCatId != 0) {

                            catVec = pCatalogInfEjb.getCatalogParentCategory(pCatalogId, currentCatId);

                            if (catVec.size() > 0) {
                                CatalogCategoryData ccgData = (CatalogCategoryData) catVec.get(0);
                                categoryPath.addFirst(ccgData);
                                currentCatId = ccgData.getCatalogCategoryId();
                            } else {
                                categoryPath.add(ccD);
                                currentCatId = 0;
                            }
                        }

                        break;
                    }
                }

                if (ii == allCategories.size()) {
                    ae.add("error", new ActionError("error.systemError", "No category with selected id in the list"));
                    return ae;
                }

                categories = pCatalogInfEjb.getCatalogChildCategories(pCatalogId, categoryId);

                int siteId = 0;
                if (pForm != null && pForm.getAppUser() != null && pForm.getAppUser().getSite() != null) {
                    siteId = pForm.getAppUser().getSite().getSiteId();
                }

                ProductDataVector productDV = pShoppingServEjb.getCatalogChildProducts(siteId,
                        categoryId,
                        ShopTool.createShoppingItemRequest(request),
                        SessionTool.getCategoryToCostCenterView(request.getSession(), siteId));

                if (productDV != null && productDV.size() > 0 && pForm.getSearchGreenCertifiedFl()) {
                    Iterator it = productDV.iterator();
                    while (it.hasNext()) {
                        ProductData productD = (ProductData) it.next();
                        if (!productD.isCertificated()) {
                            it.remove();
                        }
                    }
                }

                products = sortProducts(storeTypeProperty.getValue(), productDV, pForm.getOrderBy());

            } catch (Exception exc) {
                exc.printStackTrace();
                ae.add("error", new ActionError("error.systemError", exc.getMessage()));
                return ae;
            }

            if (pForm.getAppUser().isUserOnContract() || pForm.getAppUser().isSpecialPermissionRequired()) {
                categories = filterShooppingCategories(pForm, categories);
                products = filterShoppingProducts(pForm, products);
            }

            categoryId = -1;
            if (categories.size() == 1 && products.size() == 0) {
                CatalogCategoryData ccD = (CatalogCategoryData) categories.get(0);
                categoryId = ccD.getCatalogCategoryId();
                categoryList = categories;
            }
        }

        pForm.setCategoryDataPath(categoryPath);
        categoryList = new LinkedList();
        categoryList.addAll(categories);
        pForm.setCategoryList(categoryList);

        List productList = new LinkedList();
        productList.addAll(products);
        pForm.setProductList(products);

        return ae;
    }


    //*****************************************************************************
    /**
     *  Description of the Method
     *
     *@param  pStoreType  Description of the Parameter
     *@param  pProducts   Description of the Parameter
     *@param  pSortBy     Description of the Parameter
     *@return             Description of the Return Value
     */
    public static ProductDataVector sortProducts(String pStoreType,
            ProductDataVector pProducts,
            int pSortBy) {

        switch (pSortBy) {

            case Constants.ORDER_BY_CATEGORY:
                pProducts = sortProductsByCategory(pProducts);

                break;
            case Constants.ORDER_BY_CUST_SKU:
                pProducts = sortProductsBySku(pStoreType, pProducts, Constants.ORDER_DIRECTION_ASC);
                break;
            case Constants.ORDER_BY_NAME:
                pProducts = sortProductsByName(pProducts, Constants.ORDER_DIRECTION_ASC);

                break;
            case Constants.ORDER_BY_NAME_DESC:
                pProducts = sortProductsByName(pProducts, Constants.ORDER_DIRECTION_DESC);

                break;
            case Constants.ORDER_BY_GROUP_SIZE:
                pProducts = sortProductsByItemGroupIdSize(pProducts, Constants.ORDER_DIRECTION_ASC);

                break;
            case Constants.ORDER_BY_CUST_SKU_DESC:
                pProducts = sortProductsBySku(pStoreType, pProducts, Constants.ORDER_DIRECTION_DESC);

                break;
        }

        return pProducts;
    }


    //*****************************************************************************
    /**
     *  Description of the Method
     *
     *@param  pProducts  Description of the Parameter
     *@return            Description of the Return Value
     */
    private static ProductDataVector sortProductsByCategory(ProductDataVector pProducts) {

        Object[] products = pProducts.toArray();
        int size = products.length;

        for (int ii = 0; ii < size - 1; ii++) {

            boolean finishFlag = true;

            for (int jj = 1; jj < size - ii; jj++) {

                ProductData prD1 = (ProductData) products[jj - 1];
                String name1 = "";
                CatalogCategoryDataVector ccDV1 = prD1.getCatalogCategories();

                if (ccDV1 != null && ccDV1.size() > 0) {

                    CatalogCategoryData ccD1 = (CatalogCategoryData) ccDV1.get(
                            0);
                    String ss1 = ccD1.getCatalogCategoryShortDesc();

                    if (ss1 != null) {
                        name1 = ss1;
                    }
                }

                ProductData prD2 = (ProductData) products[jj];
                String name2 = "";
                CatalogCategoryDataVector ccDV2 = prD2.getCatalogCategories();

                if (ccDV2 != null && ccDV2.size() > 0) {

                    CatalogCategoryData ccD2 = (CatalogCategoryData) ccDV2.get(
                            0);
                    String ss2 = ccD2.getCatalogCategoryShortDesc();

                    if (ss2 != null) {
                        name2 = ss2;
                    }
                }

                if (name1.compareToIgnoreCase(name2) > 0) {
                    finishFlag = false;
                    products[jj - 1] = prD2;
                    products[jj] = prD1;
                }
            }

            if (finishFlag) {

                break;
            }
        }

        pProducts = new ProductDataVector();

        for (int ii = 0; ii < size; ii++) {
            pProducts.add(products[ii]);
        }

        return pProducts;
    }


    //*****************************************************************************
    /**
     *  Description of the Method
     *
     *@param  pStoreType  Description of the Parameter
     *@param  pProducts   Description of the Parameter
     *@return             Description of the Return Value
     */
    private static ProductDataVector sortProductsBySku(String pStoreType,  ProductDataVector pProducts, int pDirection) {

        Object[] products = pProducts.toArray();
        int size = products.length;

        for (int ii = 0; ii < size - 1; ii++) {

            boolean finishFlag = true;

            for (int jj = 1; jj < size - ii; jj++) {

                ProductData prD1 = (ProductData) products[jj - 1];
//                String name1 = prD1.getCatalogSkuNum();
                String name1 = getActualSku(pStoreType, prD1);//.trim();

                /*** Statement below added by Sergei Cher ***/
                if (name1 == null) { //if the value of the ITEM_NUM column is NULL where ITEM_MAPPING_CODE = "ITEM_DISTRIBUTOR" in the CLW_ITEM_MAPPING db table
                	name1 = "";
                }


                ProductData prD2 = (ProductData) products[jj];
//                String name2 = prD2.getCatalogSkuNum();
                String name2 = getActualSku(pStoreType, prD2);//.trim();

                //name1 = StringUtils.fillingZero(name1, name1.length() - name2.length() ) ;
                //name2 = StringUtils.fillingZero(name2, name2.length() - name1.length() ) ;

                /*** Statement below added by Sergei Cher ***/
                if (name2 == null) { //if the value of the ITEM_NUM column is NULL where ITEM_MAPPING_CODE = "ITEM_DISTRIBUTOR" in the CLW_ITEM_MAPPING db table
                	name2 = "";
                }
                if ( (pDirection == Constants.ORDER_DIRECTION_ASC  && name1.compareToIgnoreCase(name2) > 0) ||
                     (pDirection == Constants.ORDER_DIRECTION_DESC && name1.compareToIgnoreCase(name2) < 0)
                ) {
                    finishFlag = false;
                    products[jj - 1] = prD2;
                    products[jj] = prD1;

                }
            }

            if (finishFlag) {
                break;
            }
        }

        pProducts = new ProductDataVector();

        for (int ii = 0; ii < size; ii++) {
            pProducts.add(products[ii]);
        }

        return pProducts;
    }


    //*****************************************************************************
    /**
     *  Description of the Method
     *
     *@param  pProducts  Description of the Parameter
     *@return            Description of the Return Value
     */
    private static ProductDataVector sortProductsByName(ProductDataVector pProducts, int pDirection) {

        Object[] products = pProducts.toArray();
        int size = products.length;

        for (int ii = 0; ii < size - 1; ii++) {

            boolean finishFlag = true;

            for (int jj = 1; jj < size - ii; jj++) {

                ProductData prD1 = (ProductData) products[jj - 1];
                String name1 = prD1.getCatalogProductShortDesc();
                ProductData prD2 = (ProductData) products[jj];
                String name2 = prD2.getCatalogProductShortDesc();

                if (pDirection == Constants.ORDER_DIRECTION_ASC) {
                    if (name1.compareToIgnoreCase(name2) > 0) {
                        finishFlag = false;
                        products[jj - 1] = prD2;
                        products[jj] = prD1;
                    }
                }
                if (pDirection == Constants.ORDER_DIRECTION_DESC) {
                    if (name1.compareToIgnoreCase(name2) < 0) {
                        finishFlag = false;
                        products[jj - 1] = prD2;
                        products[jj] = prD1;
                    }
                }
            }

            if (finishFlag) {

                break;
            }
        }

        pProducts = new ProductDataVector();

        for (int ii = 0; ii < size; ii++) {
            pProducts.add(products[ii]);
        }

        return pProducts;
    }

    public static ProductDataVector sortProductsByItemGroupIdSize(ProductDataVector pProducts, int pDirection) {

        Object[] products = pProducts.toArray();
        int size = products.length;

        for (int ii = 0; ii < size - 1; ii++) {

            boolean finishFlag = true;

            for (int jj = 1; jj < size - ii; jj++) {

                ProductData prD1 = (ProductData) products[jj - 1];
                String name1 = prD1.getItemGroupId()+ "~" + prD1.getSize();
                ProductData prD2 = (ProductData) products[jj];
                String name2 = prD2.getItemGroupId()+ "~" + prD2.getSize();

                if (pDirection == Constants.ORDER_DIRECTION_ASC) {
                    if (name1.compareToIgnoreCase(name2) > 0) {
                        finishFlag = false;
                        products[jj - 1] = prD2;
                        products[jj] = prD1;
                    }
                }
                if (pDirection == Constants.ORDER_DIRECTION_DESC) {
                    if (name1.compareToIgnoreCase(name2) < 0) {
                        finishFlag = false;
                        products[jj - 1] = prD2;
                        products[jj] = prD1;
                    }
                }
            }

            if (finishFlag) {

                break;
            }
        }

        pProducts = new ProductDataVector();

        for (int ii = 0; ii < size; ii++) {
            pProducts.add(products[ii]);
        }

        return pProducts;
    }

    //*****************************************************************************
    /**
     *  Description of the Method
     *
     *@param  pCategories  Description of the Parameter
     *@return              Description of the Return Value
     */
    private static CatalogCategoryDataVector sortCategoriesByName(CatalogCategoryDataVector pCategories) {

        Object[] categories = pCategories.toArray();
        int size = categories.length;

        for (int ii = 0; ii < size - 1; ii++) {

            boolean finishFlag = true;

            for (int jj = 1; jj < size - ii; jj++) {

                CatalogCategoryData ccD1 = (CatalogCategoryData) categories[jj - 1];
                String name1 = ccD1.getCatalogCategoryShortDesc();
                CatalogCategoryData ccD2 = (CatalogCategoryData) categories[jj];
                String name2 = ccD2.getCatalogCategoryShortDesc();

                if (name1.compareToIgnoreCase(name2) > 0) {
                    finishFlag = false;
                    categories[jj - 1] = ccD2;
                    categories[jj] = ccD1;
                }
            }

            if (finishFlag) {

                break;
            }
        }

        pCategories = new CatalogCategoryDataVector();

        for (int ii = 0; ii < size; ii++) {
            pCategories.add(categories[ii]);
        }

        return pCategories;
    }


    public static ActionErrors sort(HttpServletRequest request, UserShopForm form)
    throws Exception{
		HttpSession session = request.getSession();
		if (form == null){
		    return new ActionErrors();
		}
		List scidvList = form.getCartItems();
		if (scidvList==null){
		    return new ActionErrors();
		}
		ShoppingCartItemDataVector scidv = new ShoppingCartItemDataVector();
		scidv.addAll(scidvList);

		String sortField = request.getParameter("sortField");
		boolean desc = false;
		if(sortField != null && sortField.length() > 4 && sortField.endsWith("Desc") && !sortField.endsWith(" Desc")){
			sortField = sortField.substring(0,sortField.length()-4);
			desc = true;
		}
		DisplayListSort.sort(scidv, sortField);
		if(desc){
		   Collections.reverse(scidv);
		}
		scidvList.clear();
		scidvList.addAll(scidv);
		return new ActionErrors();
	}


    //*****************************************************************************
    /**
     *  Description of the Method
     *
     *@param  request        Description of the Parameter
     *@param  pForm          Description of the Parameter
     *@return                Description of the Return Value
     *@exception  Exception  Description of the Exception
     */
    public static ActionErrors sortCatalogItems(HttpServletRequest request,
            UserShopForm pForm)
            throws Exception {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        Integer catalogIdI = (Integer) session.getAttribute(
                Constants.CATALOG_ID);

        if (catalogIdI == null) {
            ae.add("error",
                    new ActionError("error.systemError",
                    "No " + Constants.CATALOG_ID +
                    " session object found"));

            return ae;
        }

        int catalogId = catalogIdI.intValue();
        APIAccess factory = (APIAccess) session.getAttribute(
                Constants.APIACCESS);

        if (factory == null) {
            ae.add("error",
                    new ActionError("error.systemError", "No Ejb access"));

            return ae;
        }

        ShoppingServices shoppingServEjb = null;

        try {
            shoppingServEjb = factory.getShoppingServicesAPI();
        } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
            exc.printStackTrace();
            ae.add("error",
                    new ActionError("error.systemError",
                    "No shopping services Ejb pointer"));

            return ae;
        }

        StoreData store = pForm.getAppUser().getUserStore();

        if (store == null) {
            ae.add("error",
                    new ActionError("error.systemError",
                    "No store information was loaded"));

            return ae;
        }

        PropertyData storeTypeProperty = store.getStoreType();

        if (storeTypeProperty == null) {
            ae.add("error",
                    new ActionError("error.systemError",
                    "No store type information was loaded"));

            return ae;
        }

        List items = pForm.getProductList();

        if (items == null || items.size() <= 1) {

            return ae;
        }

        Object item = items.get(0);
        int sortBy = pForm.getOrderBy();

        if (item instanceof Integer) {

            IdVector itemIdV = new IdVector();

            for (int ii = 0; ii < items.size(); ii++) {
                itemIdV.add(items.get(ii));
            }

            try {
                itemIdV = shoppingServEjb.sortItems(storeTypeProperty.getValue(),
                        catalogId, itemIdV, sortBy);
            } catch (RemoteException exc) {
                ae.add("error",
                        new ActionError("error.systemError", exc.getMessage()));
                exc.printStackTrace();

                return ae;
            }

            pForm.setOffset(0);
            pForm.setProductList(itemIdV);
            ae = prepareCartItems(request, pForm);

            if (ae.size() > 0) {

                return ae;
            }
        } else if (item instanceof ProductData) {
            items = sortProducts(storeTypeProperty.getValue(),
                    (ProductDataVector) items, sortBy);
            pForm.setOffset(0);
            pForm.setProductList(items);
            ae = prepareCartItems(request, pForm);

            if (ae.size() > 0) {

                return ae;
            }
        }

        return ae;
    }


    //*****************************************************************************
    /**
     *  Description of the Method
     *
     *@param  request        Description of the Parameter
     *@param  pForm          Description of the Parameter
     *@return                Description of the Return Value
     *@exception  Exception  Description of the Exception
     */
    public static ActionErrors goPage(HttpServletRequest request,
            UserShopForm pForm)
            throws Exception {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        String pageNumS = request.getParameter("page");

        if (pageNumS == null || pageNumS.trim().length() == 0) {
            ae.add("error",
                    new ActionError("error.systemError",
                    "No page number parameter found"));

            return ae;
        }

        int pageNum = 0;

        try {
            pageNum = Integer.parseInt(pageNumS);
        } catch (NumberFormatException exc) {
            ae.add("error",
                    new ActionError("error.systemError",
                    "Bad page number format"));

            return ae;
        }

        int pageSize = pForm.getPageSize();
        int offset = pageNum * pageSize;

        if (offset < 0 || offset >= pForm.getProductListSize()) {
            ae.add("error",
                    new ActionError("error.systemError",
                    "Requeated page is out of the product list"));

            return ae;
        }

        pForm.setOffset(offset);

        ae = prepareCartItems(request, pForm);

        if (ae.size() > 0) {

            return ae;
        }

        return ae;
    }



    //******************************************************************************
    /**
     *  Description of the Method
     *
     *@param  request        Description of the Parameter
     *@param  pForm          Description of the Parameter
     *@return                Description of the Return Value
     *@exception  Exception  Description of the Exception
     */
    private static ActionErrors prepareCartItems(HttpServletRequest request,
            UserShopForm pForm)
            throws Exception {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess) session.getAttribute(
                Constants.APIACCESS);

        if (factory == null) {
            ae.add("error",
                    new ActionError("error.systemError", "No Ejb access"));

            return ae;
        }

        ShoppingServices shoppingServEjb = null;

        try {
            shoppingServEjb = factory.getShoppingServicesAPI();
        } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
            exc.printStackTrace();
            ae.add("error",
                    new ActionError("error.systemError",
                    "No shopping services Ejb pointer"));

            return ae;
        }

        List productList = pForm.getProductList();
        pForm.setOffset(0);
        pForm.setPageSize(pForm.getProductListSize());

        int offset = pForm.getOffset();
        int pageSize = pForm.getPageSize();
        int listSize = pForm.getProductListSize();
        Integer catalogIdI = (Integer) session.getAttribute(
                Constants.CATALOG_ID);

        if (catalogIdI == null) {
            ae.add("error",
                    new ActionError("error.systemError",
                    "No " + Constants.CATALOG_ID +
                    " session attrbute found"));

            return ae;
        }

        int catalogId = catalogIdI.intValue();
        Integer contractIdI = (Integer) session.getAttribute(
                Constants.CONTRACT_ID);
        int contractId = 0;

        if (catalogIdI != null) {
            contractId = contractIdI.intValue();
        }

        int size = (offset + pageSize > listSize) ? listSize : offset +
                pageSize;
        ShoppingCartItemDataVector cartItems = new ShoppingCartItemDataVector();
        List requestedItems = new LinkedList();

        for (int ii = offset; ii < size; ii++) {

            Object elem = productList.get(ii);
            requestedItems.add(elem);
        }

        StoreData store = pForm.getAppUser().getUserStore();

        if (store == null) {
            ae.add("error",
                    new ActionError("error.systemError",
                    "No store information was loaded"));

            return ae;
        }

        PropertyData storeTypeProperty = store.getStoreType();

        if (storeTypeProperty == null) {
            ae.add("error",
                    new ActionError("error.systemError",
                    "No store type information was loaded"));

            return ae;
        }

        try {
           cartItems = shoppingServEjb.prepareShoppingItems(storeTypeProperty.getValue(), pForm.getAppUser().getSite(),
                        catalogId,
                        contractId,
                        requestedItems,
                        SessionTool.getCategoryToCostCenterView(request.getSession(),pForm.getAppUser().getSite().getSiteId()));
            //Jd end
        } catch (RemoteException exc) {
            exc.printStackTrace();
            ae.add("error",
                    new ActionError("error.systemError", exc.getMessage()));

            return ae;
        }
        cartItems = shoppingServEjb.sortShoppingCardItems(pForm.getOrderBy(), cartItems);
        pForm.setCartItems(cartItems);
        return ae;
    }


    //***************************************************************
    /**
     *  Adds a feature to the ToCart attribute of the UserShopLogic class
     *
     *@param  request  The feature to be added to the ToCart attribute
     *@param  pForm    The feature to be added to the ToCart attribute
     *@return          Description of the Return Value
     */
    public static ActionErrors addToCart(HttpServletRequest request,
            UserShopForm pForm) {

        ActionErrors ae = new ActionErrors();
        pForm.setConfirmMessage(null);
        String itemsSubmitStamp = pForm.getItemsSubmitStamp();
        String itemsStamp = pForm.getItemsStamp();
        if(Utility.isSet(itemsSubmitStamp) && Utility.isSet(itemsStamp) &&
           !itemsSubmitStamp.equals(itemsStamp)) {
            String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.pageExpired",null);
            ae.add("error",
                    new ActionError("error.simpleGenericError",errorMess));
            return ae;
        }

        if (ShopTool.isShoppingCartConsolidated(request)) {
            String errorMsg = ClwI18nUtil.getMessage(request, "shop.errors.addItemsIntoConsolidatedCart", null);
            ae.add("error", new ActionError("error.simpleGenericError", errorMsg));
            return ae;
        }

        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess) session.getAttribute(
                Constants.APIACCESS);
        if (factory == null) {
            ae.add("error",
                    new ActionError("error.systemError", "No Ejb access"));
            return ae;
        }

        String[] quantityS = pForm.getQuantity();
        int[] itemIds = pForm.getItemIds();

        List cartItems = pForm.getCartItems();
        int cartItemsSize = cartItems.size();

        int[] quantity = new int[quantityS.length];

        if (itemIds.length < quantity.length) {
            ae.add("error",
                    new ActionError("error.systemError",
                    "Form processing error: 78.1 " +
                    itemIds.length + " " + quantity.length));

            return ae;
        }

        List<String> msgList = new ArrayList<String>();

        if(quantity.length==0 || quantity==null){

        	String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.enterQty",null);
        	ae.add("error",
                    new ActionError("error.simpleGenericError",errorMess));
        	return ae;
        }

        for (int ii = 0; ii < quantityS.length; ii++) {

            ShoppingCartItemData sciTemplateD = (ShoppingCartItemData) cartItems.get(ii);

            String qqS = quantityS[ii];
            if (qqS != null && qqS.trim().length() > 0) {

                if (ii >= cartItemsSize) {
                    ae.add("error", new ActionError("error.simpleGenericError", "Form processing error: 78.2"));
                    return ae;
                }
                log.info("UserShopLogic.addToCart()======> (sciTemplateD.getProduct().getProductId()="+ sciTemplateD.getProduct().getProductId()+ ", itemIds["+ii+"]="+itemIds[ii]);
                if (sciTemplateD.getProduct().getProductId() != itemIds[ii]) {
                    ae.add("error", new ActionError("error.simpleGenericError", "Form processing error: 78.3"));
                    return ae;
                }

            }

            setQuantityString(cartItems, ii, quantityS[ii]);

            if (qqS != null && qqS.trim().length() > 0) {
                try {
                    int qq = Integer.parseInt(qqS);
                    if (qq <= 0) {
                        msgList.add(sciTemplateD.getActualSkuNum());
                    }
                    //
                	//STJ-6069 - make sure that if this item is already in the cart that the new quantity added to the
                	//old quantity doesn't exceed the max value for an Integer
			        ShoppingCartData cartD = (ShoppingCartData) session.getAttribute(Constants.SHOPPING_CART);
			        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
			        if (appUser == null) {
			            ae.add("error", new ActionError("error.systemError", "No session user found"));
			            return ae;
			        }
			        if (cartD == null) {
			            cartD = new ShoppingCartData();
			            cartD.setUser(appUser.getUser());
			        }
                	Iterator<ShoppingCartItemData> shoppingCartItemIterator = cartD.getItems().iterator();
                	boolean foundItem = false;
                	while (!foundItem && shoppingCartItemIterator.hasNext()) {
                		ShoppingCartItemData shoppingCartItem = shoppingCartItemIterator.next();
                		foundItem = (sciTemplateD.getItemId() == shoppingCartItem.getProduct().getItemData().getItemId());
                		if (foundItem) {
                			Long newQuantity = new Long(new Long(qq) + new Long(shoppingCartItem.getQuantity()));
                			if (newQuantity > Integer.MAX_VALUE) {
                	        	String[] args = new String[2];
                	        	args[0] = Integer.MAX_VALUE + "";
                	        	args[1] = sciTemplateD.getActualSkuNum();
                	            String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.exceededMaxQtyMessage", args);
                	            ae.add("error", new ActionError("error.simpleGenericError",errorMess));
                	            return ae;
                			}
                		}
                	}
					//if we got to here it is safe to add the new quantity
                    quantity[ii] = qq;
                } catch (NumberFormatException exc) {
                    msgList.add(sciTemplateD.getActualSkuNum());
                }
            } else {
                quantity[ii] = 0;
            }

        }

        if(msgList.size()>0 && !msgList.isEmpty()){
        	Object[] param = new Object[1];
        	param[0] = Utility.toCommaSting(msgList);
            String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.invalidQtyMessage",param);
            ae.add("error", new ActionError("error.simpleGenericError",errorMess));
        	return ae;
        }

        if (ae.size() > 0) {
            return ae;
        }

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(
                Constants.APP_USER);

        if (appUser == null) {
            ae.add("error",
                    new ActionError("error.systemError",
                    "No session user found"));

            return ae;
        }

        ShoppingCartItemDataVector newItems = new ShoppingCartItemDataVector();
        ShoppingCartData cartD = ShopTool.getCurrentShoppingCart(request);
        cartD.clearNewItems();

        for (int ii = 0; ii < quantity.length; ii++) {

            if (quantity[ii] > 0) {
                ShoppingCartItemData sciTemplateD = (ShoppingCartItemData) cartItems.get(ii);
                sciTemplateD.setQuantity(0);
                ShoppingCartItemData sciD = sciTemplateD.copy();
                sciD.setQuantity(quantity[ii]);
                newItems.add(sciD);
                //cartD.addItem(sciD);
            }

            quantityS[ii] = "";
        }

        if (newItems.isEmpty()) {
            String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.enterQty",null);
            ae.add("error", new ActionError("error.simpleGenericError",errorMess));                     
            return ae;
        }

        cartD.setSite(appUser.getSite());

        if (!ae.isEmpty()) {
            return ae;
        }

        ValidateActionMessage am = ShopTool.addItemsToCart(request, cartD, newItems);
        if (am.hasErrors()) {
            return am.getActionErrors();
        }
        if (am.hasWarnings()) {
            cartD.addWarningMessages(am.getWarnings());
        }

        ae = ShoppingCartLogic.saveShoppingCart(session, newItems);
        if (!ae.isEmpty()) {
            return ae;
        }
        List itemMessages = ShopTool.getCurrentShoppingCart(request).getItemMessages();
        if (itemMessages == null || itemMessages.size() == 0) {
            pForm.setConfirmMessage(ClwI18nUtil.getMessage(request, "shoppingCart.text.actionMessage.itemAdded", null));
        }
        else {
        	pForm.setConfirmMessage(null);
        }

        clearQuantities(pForm);

        return ae;
    }

/////////////////////////////////////////
    public static boolean isSomethingSelected(HttpServletRequest request,
            UserShopForm pForm) {
        String[] quantityS = pForm.getQuantity();
		if(quantityS!=null) {
			for (int ii = 0; ii < quantityS.length; ii++) {
				String qqS = quantityS[ii];
				if (qqS != null && qqS.trim().length() > 0) {
					return true;
				}
			}
		}
		return false;
	}
//////////////////////////////////////////
    //***************************************************************
    /**
     *  Description of the Method
     *
     *@param  request        Description of the Parameter
     *@param  pForm          Description of the Parameter
     *@return                Description of the Return Value
     *@exception  Exception  Description of the Exception
     */
    public static ActionErrors itemToCart(HttpServletRequest request,
            UserShopForm pForm)
            throws Exception {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess) session.getAttribute(
                Constants.APIACCESS);

        if (factory == null) {
            ae.add("error",
                    new ActionError("error.systemError", "No Ejb access"));

            return ae;
        }

        ShoppingServices shoppingServEjb = null;

        try {
            shoppingServEjb = factory.getShoppingServicesAPI();
        } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
            ae.add("error",
                    new ActionError("error.systemError",
                    "No shopping services Ejb pointer"));

            return ae;
        }

        Order orderEjb;
        try {
        	orderEjb = factory.getOrderAPI();
        } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
            ae.add("error", new ActionError("error.systemError", "No order Ejb pointer"));
            return ae;
        }

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(
                Constants.APP_USER);

        if (appUser == null) {
            ae.add("error",
                    new ActionError("error.systemError",
                    "No session user found"));

            return ae;
        }

        ShoppingCartData cartD = (ShoppingCartData) session.getAttribute(
                Constants.SHOPPING_CART);

        if (cartD == null) {
            cartD = new ShoppingCartData();
            cartD.setUser(appUser.getUser());
        }

        String quantityS = pForm.getQuantityDetail();
        int quantity = 0;

        List<String> msgList = new ArrayList<String>();

        if (quantityS != null && quantityS.trim().length() > 0) {

            try {
                quantity = Integer.parseInt(quantityS);
                if(quantity < 0){
                	msgList.add(pForm.getItemDetail().getActualSkuNum());
                }
                else {
                	//STJ-6069 - make sure that if this item is already in the cart that the new quantity added to the
                	//old quantity doesn't exceed the max value for an Integer
                	Iterator<ShoppingCartItemData> shoppingCartItemIterator = cartD.getItems().iterator();
                	boolean foundItem = false;
                	while (!foundItem && shoppingCartItemIterator.hasNext()) {
                		ShoppingCartItemData shoppingCartItem = shoppingCartItemIterator.next();
                		foundItem = (pForm.getItemId() == shoppingCartItem.getProduct().getItemData().getItemId());
                		if (foundItem) {
                			Long newQuantity = new Long(new Long(quantity) + new Long(shoppingCartItem.getQuantity()));
                			if (newQuantity > Integer.MAX_VALUE) {
                	        	String[] args = new String[2];
                	        	args[0] = Integer.MAX_VALUE + "";
                	        	args[1] = pForm.getItemDetail().getActualSkuNum();
                	            String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.exceededMaxQtyMessage", args);
                	            ae.add("error", new ActionError("error.simpleGenericError",errorMess));
                	            return ae;
                			}
                		}
                	}
                }
            } catch (NumberFormatException exc) {
            	msgList.add(pForm.getItemDetail().getActualSkuNum());
            }
        } else {
            String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.enterQty",null);
            ae.add("error",
                    new ActionError("error.simpleGenericError",errorMess));

            return ae;
        }

        if(msgList.size()>0 && !msgList.isEmpty()){
        	Object[] param = new Object[1];
        	param[0] = Utility.toCommaSting(msgList);
            String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.invalidQtyMessage",param);
            //pForm.setInvalidQtyMessage(errorMess);
            ae.add("error",
                    new ActionError("error.simpleGenericError",errorMess));
        	return ae;
        }

        if (quantity == 0) {
            String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.enterQty",null);
            ae.add("error",
                    new ActionError("error.simpleGenericError",errorMess));

            return ae;
        }

        int itemId = pForm.getItemId();
        List cartItems = pForm.getCartItems();
        ShoppingCartItemData shoppingCartItem = null;

        if (cartItems != null) {

            for (int ii = 0; ii < cartItems.size(); ii++) {

                ShoppingCartItemData sciD = (ShoppingCartItemData) cartItems.get(
                        ii);

                if (sciD.getProduct().getProductId() == itemId) {
                    shoppingCartItem = sciD;
                    break;
                }
            }
        }

        if (shoppingCartItem == null) {

            List<Integer> items = new LinkedList<Integer>();
            items.add(itemId);

            Integer catalogIdI = (Integer) session.getAttribute(
                    Constants.CATALOG_ID);

            if (catalogIdI == null) {
                ae.add("error",
                        new ActionError("error.systemError",
                        "No " + Constants.CATALOG_ID +
                        " session attrbute found"));

                return ae;
            }

            int catalogId = catalogIdI.intValue();
            Integer contractIdI = (Integer) session.getAttribute(
                    Constants.CONTRACT_ID);
            int contractId = 0;

            if (catalogIdI != null) {
                contractId = contractIdI.intValue();
            }

            StoreData store = pForm.getAppUser().getUserStore();

            if (store == null) {
                ae.add("error",
                        new ActionError("error.systemError",
                        "No store information was loaded"));

                return ae;
            }

            PropertyData storeTypeProperty = store.getStoreType();

            if (storeTypeProperty == null) {
                ae.add("error",
                        new ActionError("error.systemError",
                        "No store type information was loaded"));

                return ae;
            }

            ShoppingCartItemDataVector itemV = null;

            try {
                //Jd begin
                if (!"jd".equals(ClwCustomizer.getStoreDir())) {
                    //Jd end
                    itemV = shoppingServEjb.prepareShoppingItems(storeTypeProperty.getValue(), appUser.getSite(),
                            catalogId,
                            contractId, items,
                            SessionTool.getCategoryToCostCenterView(request.getSession(),appUser.getSite().getSiteId()));
                    //Jd begin
                } else {
                    itemV = shoppingServEjb.prepareJdShoppingItems(pForm.getPriceCode(),
                            catalogId,
                            contractId, items,SessionTool.getCategoryToCostCenterView(session, appUser.getSite().getSiteId()));
                }
                //Jd end
            } catch (RemoteException exc) {
                exc.printStackTrace();
                ae.add("error",
                        new ActionError("error.systemError",
                        "Can't prepare item data. Item id = " + itemId));

                return ae;
            }

            if (itemV.size() == 0) {
                ae.add("error",
                        new ActionError("error.systemError",
                        "Can't prepare item data. Item id = " + itemId));

                return ae;
            }

            shoppingCartItem = (ShoppingCartItemData) itemV.get(0);
        }

        pForm.setItemDeatail(shoppingCartItem);

        ShoppingCartItemDataVector newItems = new ShoppingCartItemDataVector();
        ShoppingCartItemData sciD = shoppingCartItem.copy();
        sciD.setQuantity(quantity);
        cartD.clearNewItems();
        newItems.add(sciD);
        cartD.setSite(pForm.getAppUser().getSite());

        ValidateActionMessage am = ShopTool.addItemsToCart(request, cartD, newItems);
        if (am.hasErrors()) {
            return am.getActionErrors();
        }
        if (am.hasWarnings()) {
            cartD.addWarningMessages(am.getWarnings());
        }

        session.setAttribute(Constants.SHOPPING_CART, cartD);
        ae = ShoppingCartLogic.saveShoppingCart(session, newItems);
        if(ae.isEmpty()){
            pForm.setQuantityDetail("");
        }

        fetchItemMessages(ae, request);

        if (ae.size() > 0) {
            return ae;
        }else{
            List itemMessages = ShopTool.getCurrentShoppingCart(request).getItemMessages();
            if (!Utility.isSet(itemMessages)) {
            	pForm.setConfirmMessage(ClwI18nUtil.getMessage(request, "shoppingCart.text.actionMessage.itemAdded", null));
            }
            else {
            	pForm.setConfirmMessage(null);
            }
        }

        return ae;
    }

    private static void fetchItemMessages(ActionErrors ae, HttpServletRequest request) {
        ShoppingCartData cartD = ShopTool.getCurrentShoppingCart(request);
        for(int m=0; m<cartD.getItemMessages().size(); m++){

        	ShoppingCartData.CartItemInfo cii =
                (ShoppingCartData.CartItemInfo) cartD.getItemMessages().get(m);

        	ArrayList al = cii.getI18nItemMessageAL();
        	String key = (String) al.get(0);

        	Object[]  params = null;
        	if(al.size()>1) {
        		params = new Object[6];
        		for(int ii=0; ii<al.size()-1; ii++) {
        			params[ii] = (Object)al.get(ii+1);
        		}
        	}

//        	String errorMess = ClwI18nUtil.getMessage(request,key,params);
//            ae.add("error",
//                    new ActionError("error.simpleGenericError",errorMess));
        }
//        cartD.setItemMessages(null);
        ShopTool.setCurrentShoppingCart(request.getSession(), cartD);

    }



    //******************************************************************************
    /**
     * Description of the Method
     *
     * @param request Description of the Parameter
     * @param pForm   Description of the Parameter
     * @return Description of the Return Value
     */
    public static ActionErrors clear(HttpServletRequest request, UserShopForm pForm) {

        ActionErrors ae = clearQuantities(pForm);

        if (ae.size() == 0) {
            pForm.setConfirmMessage(ClwI18nUtil.getMessage(request, "shoppingCart.text.actionMessage.qtyCleared", null));
        }

        return ae;
    }

    //******************************************************************************
    /**
     * Description of the Method
     *
     * @param pForm   Description of the Parameter
     * @return Description of the Return Value
     */
    public static ActionErrors clearQuantities(UserShopForm pForm) {

        ActionErrors ae = new ActionErrors();
        String[] quantityS = pForm.getQuantity();

        for (int ii = 0; ii < quantityS.length; ii++) {
            quantityS[ii] = "";
        }

        List sciDV = pForm.getCartItems();
        clearQuantities(sciDV);

        pForm.setQuantity(quantityS);

        return ae;
    }

    private static void setQuantityString(List cartItems, int itemIndex, String qtyVal) {
        ShoppingCartItemData item = (ShoppingCartItemData) cartItems.get(itemIndex);
        item.setQuantityString(qtyVal);
    }

    //*****************************************************************
    /**
     *  Description of the Method
     *
     *@param  request  Description of the Parameter
     *@param  pForm    Description of the Parameter
     *@return          Description of the Return Value
     */
    public static ActionErrors recalculate(HttpServletRequest request,
            UserShopForm pForm) {

        ActionErrors ae = new ActionErrors();
        APIAccess factory = (APIAccess) request.getSession().getAttribute(
                Constants.APIACCESS);
        if (factory == null) {
            ae.add("error",
                    new ActionError("error.systemError", "No Ejb access"));
            return ae;
        }

        Site siteEjb = null;
        try {
            siteEjb = factory.getSiteAPI();
        } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
            ae.add("error",
                    new ActionError("error.systemError",
                    "No EJB services available 101."));
            return ae;
        }

        String[] quantityS = pForm.getQuantity();

        List cartItems = pForm.getCartItems();
        int[] itemIds = pForm.getItemIds();

        int[] quantity = new int[quantityS.length];
        List<String> msgList = new ArrayList<String>();

        if(quantity.length==0 || quantity==null){

        	String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.enterQty",null);
        	ae.add("error",
                    new ActionError("error.simpleGenericError",errorMess));
        	return ae;
        }

        //Check validity
        for (int ii = 0; quantityS != null &&
                ii < quantityS.length; ii++) {
        	ShoppingCartItemData sciTemplateD =
                (ShoppingCartItemData) cartItems.get(ii);

            String qqS = quantityS[ii];

            if (qqS != null && qqS.trim().length() > 0) {

                try {
                    int qq = Integer.parseInt(qqS);
                    quantity[ii] = qq;

                    if(qq<0){
                    	/*String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.enterQty",null);
	                    ae.add("error",new ActionError("error.simpleGenericError",errorMess));*/
                    	msgList.add(sciTemplateD.getActualSkuNum());
                    }
                } catch (NumberFormatException exc) {
                	msgList.add(sciTemplateD.getActualSkuNum());
                }
            } else {
                quantity[ii] = 0;
            }


            sciTemplateD.setQuantity(quantity[ii]);
        }

        if(msgList.size()>0 && !msgList.isEmpty()){
        	Object[] param = new Object[1];
        	param[0] = Utility.toCommaSting(msgList);
            String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.invalidQtyMessage",param);
            //pForm.setInvalidQtyMessage(errorMess);
            ae.add("error",
                    new ActionError("error.simpleGenericError",errorMess));
        	return ae;
        }
        return ae;
    }


    //******************************************************************************
    /**
     *  Description of the Method
     *
     *@param  request  Description of the Parameter
     *@param  pForm    Description of the Parameter
     *@return          Description of the Return Value
     */
    public static ActionErrors itemClear(HttpServletRequest request,
            UserShopForm pForm) {

        ActionErrors ae = new ActionErrors();
        pForm.setQuantityDetail("");

        return ae;
    }


    //****************************************************************************
    /**
     * Description of the Method
     *
     * @param request Description of the Parameter
     * @param pForm   Description of the Parameter
     * @return Description of the Return Value
     * @throws Exception Description of the Exception
     */
    public static ActionErrors search(HttpServletRequest request, UserShopForm pForm) throws Exception {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();

        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        if (factory == null) {
            ae.add("error", new ActionError("error.systemError", "No Ejb access"));
            return ae;
        }

        ShoppingServices shoppingServEjb;
        try {
            shoppingServEjb = factory.getShoppingServicesAPI();
        } catch (APIServiceAccessException exc) {
            exc.printStackTrace();
            ae.add("error", new ActionError("error.systemError", "No shopping services Ejb access"));
            return ae;
        }

        pForm.setCategoryPath(new LinkedList());
        pForm.setCategoryList(new LinkedList());
        pForm.setOffset(0);
        pForm.setNavigationFlag(false);

        IdVector searchRes;

        String custSku = (pForm.getSearchSkuType().equals("Cust.Sku")) ? pForm.getSearchSku() : "";
        String mfgSku = (pForm.getSearchSkuType().equals("Mfg.Sku")) ? pForm.getSearchSku() : "";
        String name = pForm.getSearchName();
        String desc = pForm.getSearchDesc();
        String category = pForm.getSearchCategory();
        String size = pForm.getSearchSize();
        String mfgIdS = pForm.getSearchManufacturer();
        String upcNum = pForm.getSearchUPCNum();

        int mfgId = 0;
        if (mfgIdS != null && mfgIdS.trim().length() > 0) {
            try {
                mfgId = Integer.parseInt(mfgIdS);
            } catch (NumberFormatException exc) {
                ae.add("error", new ActionError("error.systemError", "Can't convert manufacturer id to int"));
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

        int accountCatalogId = account.getAccountCatalogId();
        if (appUser.isUserOnContract() && contractId == 0) {
            ae.add("error", new ActionError("error.systemError", "There is no contract found"));
            return ae;
        }

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

            int fOrderBy = Constants.ORDER_BY_CATEGORY;
            if (pForm != null) {
                fOrderBy = pForm.getOrderBy();
            }

            searchRes = shoppingServEjb.searchShoppingItems(storeTypeProperty.getValue(),
                    custSku,
                    mfgSku,
                    name,
                    desc,
                    category,
                    size,
                    mfgId,
                    fOrderBy,
                    pForm.getSearchGreenCertifiedFl(),
                    upcNum,
                    ShopTool.createShoppingItemRequest(request));

        } catch (RemoteException exc) {
            exc.printStackTrace();
            ae.add("error", new ActionError("error.systemError", exc.getMessage()));
            return ae;
        }

        pForm.setProductList(searchRes);

        ae = prepareCartItems(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }

        return ae;
    }

      public static ActionErrors shopByGreenProduct(HttpServletRequest request, UserShopForm pForm) throws Exception {
          ActionErrors  ae= new ActionErrors();
          if(pForm!=null)
         {
             pForm.setSearchGreenCertifiedFl(true);
             ae = UserShopLogic.initCatalogShopping(request,pForm,true);
             pForm.setShopMethod(Constants.SHOP_BY_GREEN_PRODUCT);
             ae.add(search(request,pForm));
         }
          return ae;
      }
    //****************************************************************************
    /**
     *  Description of the Method
     *
     *@param  request        Description of the Parameter
     *@param  pForm          Description of the Parameter
     *@return                Description of the Return Value
     *@exception  Exception  Description of the Exception
     */
    public static ActionErrors item(HttpServletRequest request,
            UserShopForm pForm)
            throws Exception {

        ActionErrors ae = new ActionErrors();
        String itemIdS = request.getParameter("itemId");

        if (itemIdS == null || itemIdS.trim().length() == 0) {
            ae.add("error",
                    new ActionError("error.systemError", "No item id found"));

            return ae;
        }

        int itemId = 0;

        try {
            itemId = Integer.parseInt(itemIdS);
        } catch (NumberFormatException exc) {
            ae.add("error",
                    new ActionError("error.systemError",
                    "Wrong item id format. Item id = " + itemIdS));

            return ae;
        }

        String qtyS = request.getParameter("qty");

        if (qtyS == null) {
            qtyS = "";
        }
        //try to find item in current list
        pForm.setItemId(itemId);
        pForm.setQuantityDetail(qtyS);
        List cartItems = pForm.getCartItems();
        ShoppingCartItemData shoppingCartItem = null;
        int cartItemsIndex = -1;
        if (cartItems != null) {
            for (int ii = 0; ii < cartItems.size(); ii++) {
                ShoppingCartItemData sciD = (ShoppingCartItemData) cartItems.get(ii);

                if (sciD.getProduct().getProductId() == itemId) {
                    shoppingCartItem = sciD;
                    cartItemsIndex = ii;
                    break;
                }
            }
        }
        {
            HttpSession session = request.getSession();
            APIAccess factory = (APIAccess) session.getAttribute(
                    Constants.APIACCESS);

            if (factory == null) {
                ae.add("error",
                        new ActionError("error.systemError", "No Ejb access"));

                return ae;
            }

            ShoppingServices shoppingServEjb = null;

            try {
                shoppingServEjb = factory.getShoppingServicesAPI();
            } catch (APIServiceAccessException exc) {
                exc.printStackTrace();
                ae.add("error",
                        new ActionError("error.systemError",
                        "No shopping services Ejb access"));

                return ae;
            }
            List<Integer> items = new LinkedList<Integer>();
            items.add(itemId);

            Integer catalogIdI = (Integer) session.getAttribute(
                    Constants.CATALOG_ID);
            if (catalogIdI == null) {
                ae.add("error",
                        new ActionError("error.systemError",
                        "No " + Constants.CATALOG_ID +
                        " session object found"));

                return ae;
            }

            int catalogId = catalogIdI.intValue();
            int contractId = 0;
            Integer contractIdI = (Integer) session.getAttribute(
                    Constants.CONTRACT_ID);

            if (contractIdI != null) {
                contractId = contractIdI.intValue();
            }
            ShoppingCartItemDataVector itemV = null;
            StoreData store = pForm.getAppUser().getUserStore();

            if (store == null) {
                ae.add("error",
                        new ActionError("error.systemError",
                        "No store information was loaded"));

                return ae;
            }
            PropertyData storeTypeProperty = store.getStoreType();

            if (storeTypeProperty == null) {
                ae.add("error",
                        new ActionError("error.systemError",
                        "No store type information was loaded"));

                return ae;
            }
            try {
               itemV = shoppingServEjb.prepareShoppingItems(storeTypeProperty.getValue(),
                       pForm.getAppUser().getSite(),
                       catalogId,
                       contractId, items,
                       SessionTool.getCategoryToCostCenterView(request.getSession(),pForm.getAppUser().getSite().getSiteId()));
            } catch (RemoteException exc) {
                exc.printStackTrace();
                ae.add("error",
                        new ActionError("error.systemError",
                        "Can't prepare item data. Item id = " + itemIdS));

                return ae;
            }

            if (itemV.size() == 0) {
                ae.add("error",
                        new ActionError("error.systemError",
                        "Can't prepare item data. Item id = " + itemIdS));

                return ae;
            }

            // Enhancement STJ-3778: Begin
            Country countryEjb = null;
            try {
            	countryEjb = factory.getCountryAPI();
            } catch (APIServiceAccessException exc) {
                exc.printStackTrace();
                ae.add("error", new ActionError("error.systemError", "No country Ejb access"));
                return ae;
            }
            
            String userLocale = pForm.getAppUser().getUser().getPrefLocaleCd().trim();
            String countryShortDesc = pForm.getAppUser().getSite().getSiteAddress().getCountryCd();
            CountryData countryData = countryEjb.getCountryByShortDesc(countryShortDesc);
            String countryCode = countryData.getCountryCode().trim();
            String storeLocale = pForm.getAppUser().getUserStore().getBusEntity().getLocaleCd().trim(); 
            
            try {
            	List<Integer> problemItemIds = shoppingServEjb.populateMSDSInformation(itemV, userLocale, countryCode, storeLocale);
            	//add an error for any items for which msds information could not be retrieved
            	if (Utility.isSet(problemItemIds)) {
            		//create a map of item ids to items so we can retrieve the information needed in the error message
            		Map<Integer,ShoppingCartItemData> idToItemMap = new HashMap<Integer,ShoppingCartItemData>();
            		Iterator<ShoppingCartItemData> itemIterator = itemV.iterator();
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
            
            // Enhancement STJ-3778: End  

            shoppingCartItem = (ShoppingCartItemData) itemV.get(0);
            if (cartItemsIndex >= 0) {
                ShoppingCartItemData sciD =
                        (ShoppingCartItemData) cartItems.get(cartItemsIndex);
                int qty = sciD.getQuantity();
                qtyS = sciD.getQuantityString();
                shoppingCartItem.setQuantity(qty);
                shoppingCartItem.setQuantityString(qtyS);
                cartItems.remove(cartItemsIndex);
                cartItems.add(cartItemsIndex, shoppingCartItem);
            }
        }
        //Set distributor inventory info
        String showDistInventory = ShopTool.getShowDistInventoryCode(request);
        if(RefCodeNames.DIST_INVENTORY_DISPLAY.SHOW_FLAG.equals(showDistInventory) ||
           RefCodeNames.DIST_INVENTORY_DISPLAY.SHOW_QUANTITIES.equals(showDistInventory)) {
           ItemMappingData distMappingD = shoppingCartItem.getProduct().getCatalogDistrMapping();
            if(distMappingD!=null) {
                String distSku = distMappingD.getItemNum();

                if(distSku != null) {
                    int distInventoryQty = ShopTool.getDistInventory(request,
              distSku, distMappingD.getBusEntityId());
                    shoppingCartItem.setDistInventoryQty(distInventoryQty);
                }
            }
        }
        pForm.setItemDeatail(shoppingCartItem);
        
        // STJ-5164: Move document(JPEG, PDF, etc. formats) content from Databse Blob to E3 Storage: Begin
        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        Content contentBean = factory.getContentAPI();
        
        // Find Storage type of the product item  (E3 or Database) and store it in the Action Form: Begin      	
        String msdsUrl = pForm.getItemDetail().getProduct().getMsds();
        String dedUrl = pForm.getItemDetail().getProduct().getDed();        
        String specUrl = pForm.getItemDetail().getProduct().getSpec();
        log.info("msdsUrl = " + msdsUrl);
        log.info("dedUrl = " + dedUrl);
        log.info("specUrl = " + specUrl);
        if (Utility.isSet(msdsUrl)) {
        	msdsUrl = "." + msdsUrl;
        	ContentData contentMsdsData = contentBean.getContent(msdsUrl);
        	if(contentMsdsData != null){
        		pForm.setMsdsStorageTypeCd(contentMsdsData.getStorageTypeCd());
        	}
        }
        if (Utility.isSet(dedUrl)) {
        	dedUrl = "." + dedUrl;
        	ContentData contentDedData = contentBean.getContent(dedUrl);
        	if(contentDedData != null){
        		pForm.setDedStorageTypeCd(contentDedData.getStorageTypeCd());
        	}
        }
        if (Utility.isSet(specUrl)) {
        	specUrl = "." + specUrl;
        	ContentData contentSpecData = contentBean.getContent(specUrl);
        	if(contentSpecData != null){
        		pForm.setSpecStorageTypeCd(contentSpecData.getStorageTypeCd());
        	}
        }
        // Find Storage type of the product item  (E3 or Database) and store it in the Action Form: End      
	
        // STJ-5164: Move document(JPEG, PDF, etc. formats) content from Databse Blob to E3 Storage: End
        
        return ae;
    }


    public static ActionErrors itemOrder(HttpServletRequest request,
            UserShopForm pForm)
            throws Exception {

        ActionErrors ae = new ActionErrors();
        String orderItemIdS = request.getParameter("orderItemId");
        if (orderItemIdS == null || orderItemIdS.trim().length() == 0) {
            ae.add("error",
                    new ActionError("error.systemError", "No item id found"));
            return ae;
        }

        int orderItemId = 0;

        try {
            orderItemId = Integer.parseInt(orderItemIdS);
        } catch (NumberFormatException exc) {
            ae.add("error",
                    new ActionError("error.systemError",
                    "Wrong order item id format. Order Item id = " + orderItemIdS));
            return ae;
        }

        String orderIdS = request.getParameter("orderId");
        int orderId = 0;
        try {
            orderId = Integer.parseInt(orderIdS);
        } catch (NumberFormatException exc) {
            ae.add("error",
                    new ActionError("error.systemError",
                    "Wrong order id format. Order id = " + orderIdS));

            return ae;
        }

        ShoppingCartItemDataVector itemV = null;

        {
            HttpSession session = request.getSession();
            APIAccess factory = (APIAccess) session.getAttribute(
                    Constants.APIACCESS);

            if (factory == null) {
                ae.add("error",
                        new ActionError("error.systemError", "No Ejb access"));
                return ae;
            }

            ShoppingServices shoppingServEjb = null;

            try {
                shoppingServEjb = factory.getShoppingServicesAPI();
            } catch (APIServiceAccessException exc) {
                exc.printStackTrace();
                ae.add("error",
                        new ActionError("error.systemError",
                        "No shopping services Ejb access"));

                return ae;
            }

            StoreData store = pForm.getAppUser().getUserStore();

            if (store == null) {
                ae.add("error",
                        new ActionError("error.systemError",
                        "No store information was loaded"));
                return ae;
            }
            PropertyData storeTypeProperty = store.getStoreType();

            if (storeTypeProperty == null) {
                ae.add("error",
                        new ActionError("error.systemError",
                        "No store type information was loaded"));
                return ae;
            }

            Order orderEjb = null;
            try {
            	orderEjb = factory.getOrderAPI();
            } catch (APIServiceAccessException exc) {
                exc.printStackTrace();
                ae.add("error",
                        new ActionError("error.systemError",
                        "No order Ejb access"));
                return ae;
            }
            OrderData order = orderEjb.getOrder(orderId);
            int contractId = order.getContractId();
            int siteId = order.getSiteId();

            Site siteEjb = null;

            try {
            	siteEjb = factory.getSiteAPI();
            } catch (APIServiceAccessException exc) {
                exc.printStackTrace();
                ae.add("error",
                        new ActionError("error.systemError",
                        "No site Ejb access"));
                return ae;
            }

            SiteData site = siteEjb.getSite(siteId);

            try {
                OrderItemData orderItem = orderEjb.getOrderItem(orderItemId);
                List<Integer> items = new LinkedList<Integer>();
                items.add(orderItem.getItemId());

               itemV = shoppingServEjb.prepareShoppingItems(
                       storeTypeProperty.getValue(),
                       site,
                       site.getSiteCatalogId(),
                       contractId,
                       items,
                       SessionTool.getCategoryToCostCenterView(request.getSession(),siteId));

            } catch (RemoteException exc) {
                exc.printStackTrace();
                ae.add("error",
                        new ActionError("error.systemError",
                        "Can't prepare order item data. Order item id = " + orderItemId));
                return ae;
            }

            if (itemV.size() == 0) {
                ae.add("error",
                        new ActionError("error.systemError",
                        "Can't prepare order item data. Order Item id = " + orderItemId));
                return ae;
            }

            Country countryEjb = null;
            try {
            	countryEjb = factory.getCountryAPI();
            } catch (APIServiceAccessException exc) {
                exc.printStackTrace();
                ae.add("error", new ActionError("error.systemError", "No country Ejb access"));
                return ae;
            }
            String userLocale = pForm.getAppUser().getUser().getPrefLocaleCd().trim();
            String countryShortDesc = site.getSiteAddress().getCountryCd();
            CountryData countryData = countryEjb.getCountryByShortDesc(countryShortDesc);
            String countryCode = countryData.getCountryCode().trim();
            String storeLocale = pForm.getAppUser().getUserStore().getBusEntity().getLocaleCd().trim();
            
            try {
            	List<Integer> problemItemIds = shoppingServEjb.populateMSDSInformation(itemV, userLocale, countryCode, storeLocale);
            	//add an error for any items for which msds information could not be retrieved
            	if (Utility.isSet(problemItemIds)) {
            		//create a map of item ids to items so we can retrieve the information needed in the error message
            		Map<Integer,ShoppingCartItemData> idToItemMap = new HashMap<Integer,ShoppingCartItemData>();
            		Iterator<ShoppingCartItemData> itemIterator = itemV.iterator();
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
        }
        
        ShoppingCartItemData shoppingCartItem = (ShoppingCartItemData) itemV.get(0);
        int itemId = shoppingCartItem.getProduct().getItemData().getItemId();

        String qtyS = request.getParameter("qty");
        if (qtyS == null) {
            qtyS = "";
        }
        //try to find item in current list
        pForm.setItemId(itemId);
        pForm.setQuantityDetail(qtyS);
        List cartItems = pForm.getCartItems();
        int cartItemsIndex = -1;
        if (cartItems != null) {
            for (int ii = 0; ii < cartItems.size(); ii++) {
                ShoppingCartItemData sciD = (ShoppingCartItemData) cartItems.get(ii);
                if (sciD.getProduct().getProductId() == itemId) {
                    cartItemsIndex = ii;
                    break;
                }
            }
        }
        if (cartItemsIndex >= 0) {
                ShoppingCartItemData sciD =
                        (ShoppingCartItemData) cartItems.get(cartItemsIndex);
                int qty = sciD.getQuantity();
                qtyS = sciD.getQuantityString();
                shoppingCartItem.setQuantity(qty);
                shoppingCartItem.setQuantityString(qtyS);
                cartItems.remove(cartItemsIndex);
                cartItems.add(cartItemsIndex, shoppingCartItem);
        }

        //Set distributor inventory info
        String showDistInventory = ShopTool.getShowDistInventoryCode(request);
        if(RefCodeNames.DIST_INVENTORY_DISPLAY.SHOW_FLAG.equals(showDistInventory) ||
          RefCodeNames.DIST_INVENTORY_DISPLAY.SHOW_QUANTITIES.equals(showDistInventory)) {
          ItemMappingData distMappingD = shoppingCartItem.getProduct().getCatalogDistrMapping();
          if(distMappingD!=null) {
            String distSku = distMappingD.getItemNum();
            if(distSku != null) {
                int distInventoryQty = ShopTool.getDistInventory(request,
                          distSku, distMappingD.getBusEntityId());
                shoppingCartItem.setDistInventoryQty(distInventoryQty);
            }
          }
        }
        pForm.setItemDeatail(shoppingCartItem);
        return ae;
    }

    //****************************************************************************
    /**
     * Description of the Method
     *
     * @param request Description of the Parameter
     * @param pForm   Description of the Parameter
     * @return Description of the Return Value
     * @throws Exception Description of the Exception
     */
    public static ActionErrors listAll(HttpServletRequest request, UserShopForm pForm) throws Exception {


        ActionErrors ae = new ActionErrors();

        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);

        if (factory == null) {
            ae.add("error", new ActionError("error.systemError", "No Ejb access"));
            return ae;
        }

        CleanwiseUser appUser = pForm.getAppUser();

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

        ShoppingServices shoppingServEjb;
        try {
            shoppingServEjb = factory.getShoppingServicesAPI();
        } catch (APIServiceAccessException exc) {
            exc.printStackTrace();
            ae.add("error", new ActionError("error.systemError", "No shopping services Ejb access"));
            return ae;
        }

        pForm.setCategoryPath(new LinkedList());
        pForm.setCategoryList(new LinkedList());
        pForm.setOffset(0);
        pForm.setNavigationFlag(false);

        Integer catalogIdI = (Integer) session.getAttribute(Constants.CATALOG_ID);

        if (catalogIdI == null) {
            ae.add("error", new ActionError("error.systemError", "No " + Constants.CATALOG_ID + " session attrbute found"));
            return ae;
        }

        Integer contractId = Utility.intNN((Integer) session.getAttribute(Constants.CONTRACT_ID));
        if (!pForm.getShowWholeCatalog() && contractId == 0) {
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

        IdVector searchRes;
        try {
            searchRes = shoppingServEjb.getAllCatalogItems(storeTypeProperty.getValue(),
                    ShopTool.createShoppingItemRequest(request),
                    pForm.getOrderBy());
        } catch (RemoteException exc) {
            exc.printStackTrace();
            ae.add("error", new ActionError("error.systemError", exc.getMessage()));
            return ae;
        }

        pForm.setProductList(searchRes);

        ae = prepareCartItems(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }

        pForm.clearSearchFilters();

        return ae;
    }


    //******************************************************************************
    /**
     *  Description of the Method
     *
     *@param  request  Description of the Parameter
     *@param  pForm    Description of the Parameter
     *@return          Description of the Return Value
     */
    public static ActionErrors onsiteServices(HttpServletRequest request,
            OnsiteServicesForm pForm) {

        ActionErrors ae = new ActionErrors();

        if (null != pForm) {

            // Send out an email request with the detaild from the form.
            String mailmsg = "";
            CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
            String toEmail = appUser.getUserStore().getContactUsEmail().getEmailAddress();
            AddressData address = appUser.getSite().getSiteAddress();
            String shippingAddress = address.getAddress1() + " ";
            String addr2 = address.getAddress2();

            if (addr2 != null && addr2.trim().length() > 0) {
                shippingAddress += addr2 + " ";
            }

            String addr3 = address.getAddress3();

            if (addr3 != null && addr3.trim().length() > 0) {
                shippingAddress += addr3 + " ";
            }

            String addr4 = address.getAddress4();

            if (addr4 != null && addr4.trim().length() > 0) {
                shippingAddress += addr4 + " ";
            }

            shippingAddress += address.getCity() + ", ";
            shippingAddress += address.getStateProvinceCd() + " ";
            shippingAddress += address.getPostalCode() + " ";
            shippingAddress += Constants.getCountryName(address.getCountryCd());

            String customerName = appUser.getUser().getFirstName() + " " +
                    appUser.getUser().getLastName() + " ";
            String accountName = appUser.getSite().getAccountBusEntity().getShortDesc();
            mailmsg += "\n  " + customerName;
            mailmsg += "\n  " + accountName;
            mailmsg += "\n  " + shippingAddress;
            mailmsg += "\n  On Site Service Type: " + pForm.getOnSiteServiceType() + "\n\n  Location Same As Shipping: " + pForm.getLocationSameAsShipping() + "\n\nComments:\n\n" + pForm.getComments();

            try {

                APIAccess factory = new APIAccess();
                String subj = "Onsite service request from: " +
                        customerName;
                EmailClient emc = factory.getEmailClientAPI();
                emc.send(toEmail,
       emc.getDefaultEmailAddress(),
       subj, mailmsg,
       Constants.EMAIL_FORMAT_PLAIN_TEXT,0,0);
            } catch (Exception e) {
                pForm.setResultMsg("NOTOK");

                return ae;
            }

            pForm.setResultMsg("OK");
        }

        return ae;
    }
    //******************************************************************************


    /**
     *  Description of the Method
     *
     *@param  request  Description of the Parameter
     *@param  pForm    Description of the Parameter
     */
    public static void initOnsiteServices(HttpServletRequest request,
            OnsiteServicesForm pForm) {

        OnsiteServicesForm form = null;

        if (null == pForm) {
            form = new OnsiteServicesForm();
            request.getSession().setAttribute("ONSITE_SVC_FORM", form);
            form.setResultMsg("");
        } else {
            request.getSession().setAttribute("ONSITE_SVC_FORM", pForm);
            pForm.setResultMsg("");
        }
    }


    //****************************************************************************
    /**
     *  Description of the Method
     *
     *@param  request        Description of the Parameter
     *@param  pForm          Description of the Parameter
     *@return                Description of the Return Value
     *@exception  Exception  Description of the Exception
     */
    public static ActionErrors refreshGuide(HttpServletRequest request,
            UserShopForm pForm)
            throws Exception {
        pForm.setOrderGuideId(0);
        return orderGuideSelect(request, pForm);
    }

    //****************************************************************************

    /**
     *  Description of the Method
     *
     *@param  request        Description of the Parameter
     *@param  pForm          Description of the Parameter
     *@return                Description of the Return Value
     *@exception  Exception  Description of the Exception
     */
    public static ActionErrors orderGuideSelect(HttpServletRequest request,
            UserShopForm pForm)
            throws Exception {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess) session.getAttribute(
                Constants.APIACCESS);

        if (factory == null) {
            ae.add("error",
                    new ActionError("error.systemError", "No Ejb access"));

            return ae;
        }

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(
                Constants.APP_USER);

        if (appUser == null) {
            ae.add("error",
                    new ActionError("error.systemError",
                    "No session " + Constants.APP_USER +
                    " object found "));

            return ae;
        }

        StoreData store = appUser.getUserStore();

        if (store == null) {
            ae.add("error",
                    new ActionError("error.systemError",
                    "No store information was loaded"));

            return ae;
        }

        PropertyData storeTypeProperty = store.getStoreType();

        if (storeTypeProperty == null) {
            ae.add("error",
                    new ActionError("error.systemError",
                    "No store type information was loaded"));

            return ae;
        }

        ShoppingServices shoppingServEjb = null;

        try {
            shoppingServEjb = factory.getShoppingServicesAPI();
        } catch (APIServiceAccessException exc) {
            exc.printStackTrace();
            ae.add("error",
                    new ActionError("error.systemError",
                    "No shopping services Ejb access"));

            return ae;
        }

        Integer catalogIdI = (Integer) session.getAttribute(
                Constants.CATALOG_ID);

        if (catalogIdI == null) {
            ae.add("error",
                    new ActionError("error.systemError",
                    "No " + Constants.CATALOG_ID +
                    " session attrbute found"));

            return ae;
        }

        int catalogId = catalogIdI.intValue();
        Integer contractIdI = (Integer) session.getAttribute(
                Constants.CONTRACT_ID);
        int contractId = 0;

        if (catalogIdI != null) {
            contractId = contractIdI.intValue();
        }

        int orderGuideId = 0;
        int orderBy = pForm.getOrderBy();
        String tOrderGuideIdS = pForm.getTemplateOrderGuideId();

        if (tOrderGuideIdS != null) {
            try {
                orderGuideId = Integer.parseInt(tOrderGuideIdS);
            } catch (NumberFormatException exc) {
                exc.printStackTrace();
                ae.add("error",
                        new ActionError("error.systemError",
                        "Wrong template oder guide menu"));

                return ae;
            }
        }

        if (orderGuideId <= 0) {
            tOrderGuideIdS = request.getParameter("templateOrderGuideId");
            if (tOrderGuideIdS != null) {
                try {
                    orderGuideId = Integer.parseInt(tOrderGuideIdS);
                } catch (NumberFormatException exc) {
                    exc.printStackTrace();
                    ae.add("error",
                            new ActionError("error.systemError",
                            "Wrong template oder guide menu"));

                    return ae;
                }
            }
        }
        if (orderGuideId <= 0) {
            String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.noOrderGuideSelected",null);
            ae.add("error",
                    new ActionError("error.simpleGenericError",errorMess));

            return ae;
        } else {

            boolean notFound = true;

            for (int i = 0; i < pForm.getTemplateOrderGuides().size(); i++) {

                if (orderGuideId == ((OrderGuideData) pForm.getTemplateOrderGuides()
                .get(i)).getOrderGuideId()) {
                    pForm.setOrderGuideName(((OrderGuideData)
                            pForm.getTemplateOrderGuides()
                    .get(i)).getShortDesc());
                    pForm.setOrderGuideId(orderGuideId);
                    notFound = false;
                    break;
                }
            }

            if (notFound) {

                for (int i = 0; i < pForm.getUserOrderGuides().size(); i++) {

                    if (orderGuideId == ((OrderGuideData) pForm.getUserOrderGuides()
                    .get(i)).getOrderGuideId()) {
                        pForm.setOrderGuideId(orderGuideId);
                        pForm.setOrderGuideName(((OrderGuideData)
                        pForm.getUserOrderGuides()
                        .get(i)).getShortDesc());
                        notFound = false;
                        break;
                    }
                }
            }

             if (notFound) {

                for (int i = 0; i < pForm.getCustomOrderGuides().size(); i++) {

                    if (orderGuideId == ((OrderGuideData) pForm.getCustomOrderGuides().get(i)).getOrderGuideId()) {
                        pForm.setOrderGuideId(orderGuideId);
                        pForm.setOrderGuideName(((OrderGuideData)pForm.getCustomOrderGuides().get(i)).getShortDesc());
                        if (Constants.ORDER_BY_CATEGORY == orderBy) {
                            orderBy = Constants.ORDER_BY_CUSTOM_CATEGORY;
                        }
                        break;
                    }
                }
            }
        }

        ShoppingCartItemDataVector items = null;

        /*
         OrderBy - get from config.txt
         if orderGuideSecondarySorting= ORDER_BY_CATEGORY_AND_SKU orderBy=5
         else orderBy=default(0)
         */
        Properties configProps = ClwCustomizer.getUiProperties(request);
        String cfgSortBy = configProps.getProperty("orderGuideSecondarySorting");

        if (cfgSortBy != null && cfgSortBy.equalsIgnoreCase("ORDER_BY_CATEGORY_AND_SKU")) {
            orderBy = Constants.ORDER_BY_CATEGORY_AND_SKU;
        }

        try {
          /*items = shoppingServEjb.getOrderGuidesItems
                  (storeTypeProperty.getValue(),
                  appUser.getSite(),
                  catalogId, contractId,
                  //!pForm.getShowWholeCatalog(),
                  orderGuideId,
                  pForm.getOrderBy());*/

            items = shoppingServEjb.getOrderGuideItems(storeTypeProperty.getValue(),
                    appUser.getSite(),
                    orderGuideId,
                    ShopTool.createShoppingItemRequest(request),
                    orderBy,SessionTool.getCategoryToCostCenterView(session, appUser.getSite().getSiteId()));

        } catch (RemoteException exc) {
            exc.printStackTrace();
            ae.add("error",
                    new ActionError("error.systemError", exc.getMessage()));

            return ae;
        }

        pForm.setOffset(0);
        pForm.setCartItems(items);
        pForm.setOrderGuideCartItems(items);

        int size = items.size();
        String[] qq = new String[(size <= 0) ? 1 : size];
        for (int ii = 0; ii < size; ii++) {
            ShoppingCartItemData sciD = (ShoppingCartItemData) items.get(ii);
            qq[ii] = "" + sciD.getQuantity();
        }
        pForm.setQuantity(qq);

        return ae;

    }


    /**
     *  Description of the Method
     *
     *@param  storeD        Description of the Parameter
     *@param  ad            Description of the Parameter
     *@param  sd            Description of the Parameter
     *@param  items         Description of the Parameter
     *@param  personalized  Description of the Parameter
     *@return               Description of the Return Value
     */
    private static GenericReportResultView generateExcelSiteAddress
            (HttpServletRequest request, CleanwiseUser appUser, StoreData storeD,
            AccountData ad,
            SiteData sd, List items, boolean personalized) {

        GenericReportResultView xlog = GenericReportResultView.createValue();

        String addressStr = ClwI18nUtil.getMessage(request,"report.tab.Address",null);
        xlog.setName(addressStr);

        ArrayList<ArrayList<String>> restable = new ArrayList<ArrayList<String>>();
        // Store information
        restable.add(mkExcelNVP(" ",storeD.getStoreBusinessName().getValue()));

        if (sd.getBSC() != null
                && sd.getBSC().getBusEntityData() != null
                && sd.getBSC().getBusEntityData().getShortDesc() != null
                ) {
            String bscName = sd.getBSC().getBusEntityData().getShortDesc();
            if (bscName.length() > 0) {
                restable.add(mkExcelNVP(" ",bscName));
            }
        }
        String accountNameStr =
              ClwI18nUtil.getMessage(request,"shop.og.text.accountName",null);
        restable.add(mkExcelNVP(accountNameStr,sd.getAccountBusEntity().getShortDesc()));

        restable.add(mkExcelBlankLine());
        String addressInformationStr =
              ClwI18nUtil.getMessage(request,"shop.og.text.addressInformation",null);
        restable.add(mkExcelNVP(addressInformationStr," "));

        if (personalized) {
            restable.add(mkExcelNVP(" ",sd.getBusEntity().getShortDesc()));
            String address1Str =
                  ClwI18nUtil.getMessage(request,"shop.og.text.address1:",null);
            restable.add(mkExcelNVP(address1Str+" ",sd.getSiteAddress().getAddress1()));
            String address2Str =
                  ClwI18nUtil.getMessage(request,"shop.og.text.address2:",null);
            restable.add(mkExcelNVP(address2Str+" ",sd.getSiteAddress().getAddress2()));
            String address3Str =
                  ClwI18nUtil.getMessage(request,"shop.og.text.address3:",null);
            restable.add(mkExcelNVP(address3Str+" ",sd.getSiteAddress().getAddress3()));
            String address4Str =
                  ClwI18nUtil.getMessage(request,"shop.og.text.address4:",null);
            restable.add(mkExcelNVP(address4Str+" ",sd.getSiteAddress().getAddress4()));
            String cityStr =
                  ClwI18nUtil.getMessage(request,"shop.og.text.city:",null);
            restable.add(mkExcelNVP(cityStr+" ",sd.getSiteAddress().getCity()));

            String state = sd.getSiteAddress().getStateProvinceCd();
            if (state != null && state.length() > 0) {
              String stateStr =
                ClwI18nUtil.getMessage(request, "shop.og.text.state:", null);
              restable.add(mkExcelNVP(stateStr + " ", state));
            }
            String zipStr =
                  ClwI18nUtil.getMessage(request,"shop.og.text.zip:",null);
            restable.add(mkExcelNVP(zipStr+" ",sd.getSiteAddress().getPostalCode()));
        } else {
            restable.add(mkExcelNVP(" "," "));
            String address1Str =
                  ClwI18nUtil.getMessage(request,"shop.og.text.address1:",null);
            restable.add(mkExcelNVP(address1Str+" "," "));
            String address2Str =
                  ClwI18nUtil.getMessage(request,"shop.og.text.address2:",null);
            restable.add(mkExcelNVP(address2Str+" "," "));
            String address3Str =
                  ClwI18nUtil.getMessage(request,"shop.og.text.address3:",null);
            restable.add(mkExcelNVP(address3Str+" "," "));
            String address4Str =
                  ClwI18nUtil.getMessage(request,"shop.og.text.address4:",null);
            restable.add(mkExcelNVP(address4Str+" "," "));
            String cityStr =
                  ClwI18nUtil.getMessage(request,"shop.og.text.city:",null);
            restable.add(mkExcelNVP(cityStr+" "," "));
            String state = sd.getSiteAddress().getStateProvinceCd();
            if (state != null && state.length() > 0) {
              String stateStr =
                ClwI18nUtil.getMessage(request, "shop.og.text.state:", null);
              restable.add(mkExcelNVP(stateStr + " ", " "));
            }
            String zipStr =
                  ClwI18nUtil.getMessage(request,"shop.og.text.zip:",null);
            restable.add(mkExcelNVP(zipStr+" "," "));
        }

        restable.add(mkExcelBlankLine());
        String orderOnlineStr =
                  ClwI18nUtil.getMessage(request,"shop.og.text.orderOnLine:",null);
        restable.add(mkExcelNVP(orderOnlineStr+" ", storeD.getStorePrimaryWebAddress().getValue()));

        String orderFaxNumber = null;
        if (sd.getBSC() != null &&
                sd.getBSC().getFaxNumber() != null &&
                sd.getBSC().getFaxNumber().getPhoneNum() != null) {
            orderFaxNumber =
                    sd.getBSC().getFaxNumber().getPhoneNum();
        }
        ContactUsInfo contact=null;
        if(appUser.getContactUsList().size() == 1){
            contact = (ContactUsInfo) appUser.getContactUsList().get(0);
        }
        if(orderFaxNumber == null && contact != null){
            orderFaxNumber = contact.getFax();
        }
        if(orderFaxNumber != null){
          String faxOrderStr =
                  ClwI18nUtil.getMessage(request,"shop.og.text.faxOrder:",null);
          restable.add(mkExcelNVP(faxOrderStr+" ",orderFaxNumber));
        }

        if(contact != null){
          String contactUsStr =
                  ClwI18nUtil.getMessage(request,"shop.og.text.contactUs",null);
          restable.add(mkExcelNVP(contactUsStr," "));
          String phoneStr =
                  ClwI18nUtil.getMessage(request,"shop.og.text.phone:",null);
          restable.add(mkExcelNVP(phoneStr+" ", contact.getPhone() + "  "+ contact.getCallHours()));
          String emailStr =
                  ClwI18nUtil.getMessage(request,"shop.og.text.email:",null);
            restable.add(mkExcelNVP(emailStr+" ", contact.getEmail()));
        }

        String ogcomments = "";

        if (null != sd.getComments()) {
            ogcomments = sd.getComments().getValue();
        }

        if (null == ogcomments || ogcomments.length() == 0) {
            if (null != ad) {
                ogcomments = ad.getComments().getValue();
            }
        }

        //add the comments if there are any
        if (ogcomments != null && ogcomments.length() > 0) {
          String commentsStr =
                  ClwI18nUtil.getMessage(request,"shop.og.text.comments:",null);
          restable.add(mkExcelNVP(commentsStr+" ",ogcomments));
        }

        xlog.setTable(restable);

        GenericReportColumnViewVector header =
                new GenericReportColumnViewVector();
        header.add(ReportingUtils.createGenericReportColumnView
                ("java.lang.String",
                "Order_Guide", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView
                ("java.lang.String",
                " ", 0, 255, "VARCHAR2"));
        xlog.setHeader(header);
        xlog.setColumnCount(header.size());

        return xlog;
    }


    /**
     *  Description of the Method
     *
     *@return    Description of the Return Value
     */
    private static ArrayList<String> mkExcelBlankLine() {
        ArrayList<String> r = new ArrayList<String>();
        r.add(" ");
        r.add(" ");
        return r;
    }

    private static ArrayList<String> mkExcelNVP(String name, String val) {
        ArrayList<String> r = new ArrayList<String>();
        r.add(name);
        r.add(val);
        return r;
    }


    /**
     *  Description of the Method
     *
     *@param  storeD  Description of the Parameter
     *@param  sd      Description of the Parameter
     *@param  items   Description of the Parameter
     *@return         Description of the Return Value
     */
    private static GenericReportResultView generateExcelItems(HttpServletRequest request, UserShopForm sForm, StoreData storeD,
            SiteData sd, List items, boolean catalogListingOnly, String pCatalogLocaleCd, boolean showPrice) {

        GenericReportResultView xlog = GenericReportResultView.createValue();

        try {
            String itemsStr = ClwI18nUtil.getMessage(request,"report.tab.Items",null);
            xlog.setName(itemsStr);
            boolean pInventoryHeader = sd.hasInventoryShoppingOn();
            boolean showSize = isShowValue(request, "showSize");
            boolean showMfg = isShowValue(request, "showMfg");
            boolean showMfgSku = isShowValue(request, "showMfgSku");
            boolean isPhysicalCountSheet = isShowValue(request,"isPhysicalCountSheet");
            boolean showPack = isShowValue(request, "showPack");
            boolean showUom = isShowValue(request, "showUom");
            boolean printXpedxCols = false;
            
            //Bug # 5077 - Pollock store requires Excel order guide changes. 
            boolean isPollockStore = false;
            boolean isPollockCustomOrderGuide = false;

            Locale lLocale = request.getLocale();
            java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance(lLocale);

            CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute("ApplicationUser");
        	AccountData mAccount = appUser.getUserAccount();

            //Check config.txt to set specific columns for xpedx
            Properties configProps = new Properties();
            String fileName = ClwCustomizer.getAbsFilePath(request,"config.txt");
            if(Utility.isSet(fileName)) {
          	  FileInputStream configIS = new FileInputStream(fileName);
                configProps.load(configIS);
                String xpedxCols = configProps.getProperty("PrintOnHandReqQtyCols");
                
                String pollockCustSkuNumCol = configProps.getProperty("custSkuNumColumnForExcel");
                String pollockCatagoryCol = configProps.getProperty("categoryColumnForExcel");
                String pollockCurrencyCodeCol = configProps.getProperty("currencyCodeColumnForExcel");
                
				if(xpedxCols!=null && xpedxCols.equals("true")){
					printXpedxCols = true;
				} 
				
				boolean pollockCustSkuNum = false;
	            boolean pollockCatagory = false;
	            boolean currencyCode = true;
	            
				if(pollockCustSkuNumCol!=null && pollockCustSkuNumCol.trim().equals("true")) {
					pollockCustSkuNum = true;
				}
				if(pollockCatagoryCol!=null && pollockCatagoryCol.trim().equals("true")) {
					pollockCatagory = true;
				}
				if(pollockCurrencyCodeCol!=null && pollockCurrencyCodeCol.trim().equals("false")) {
					currencyCode = false;
				}  
				
				if(pollockCustSkuNum && pollockCatagory && !currencyCode){
					isPollockStore = true;
					//Bug # 5279 - custom order guide for Pollock store.
					if(!catalogListingOnly) {
						int orderGuideId = sForm.getOrderGuideId();
			        	if(orderGuideId>0) {
			        		OrderGuideDataVector customOrderGuideDV = sForm.getCustomOrderGuides();
			            	if(customOrderGuideDV!=null && customOrderGuideDV.size()>0 && sForm.isCustomOrderGuide(orderGuideId)) {
			            		isPollockCustomOrderGuide = true;
			            	}
			        	}
					}
				}
            }

            ArrayList<Serializable> r = null;

            ArrayList<ArrayList<Serializable>> restable = new ArrayList<ArrayList<Serializable>>();

            for (int i = 0; null != items && i < items.size(); i++) {
                r = new ArrayList<Serializable>();
                ShoppingCartItemData pItm = (ShoppingCartItemData) items.get(i);
                if(!catalogListingOnly){
                    if (pInventoryHeader && pItm.getIsaInventoryItem()) {
                        // Inventory is on, this is an inventory item.
                        r.add(" ");
                        r.add("i");
                    } else if (pInventoryHeader && !pItm.getIsaInventoryItem()) {
                        // Inventory is on, this is not an inventory item.
                        r.add(" ");
                        r.add(" ");
                    } else {
                        // No inventory shopping, just present a place to put an order
                        // quantity.
                        r.add(" ");
                    }
                }

                if(printXpedxCols){
                	if (pInventoryHeader) {
                		if(isPhysicalCountSheet){
                			r.add("");
                		}else{
                			r.add(pItm.getInventoryQtyOnHand());
                		}
                	}
                	if(pItm.getMaxOrderQty() >= 0){
                		r.add(pItm.getMaxOrderQty());
                	}else{
                		r.add("");
                	}
                	if(!isPhysicalCountSheet){
            			r.add(pItm.getOrderQuantity());
            		}
                }
                
                if(isPollockStore) {//Dist sku # is for pollock store.
                	 String distSku = "";
                	 try {
                		 distSku = pItm.getProduct().getCatalogDistrMapping().getItemNum();
                	 }catch(Exception e) {
                	 }
                	 r.add((distSku==null?"":distSku));
                } else { //Actual sku for remaining stores.
                	  String t0 = pItm.getActualSkuNum();
                      r.add(t0);
                }              
              
                if(isPollockStore) { //Cust sku #                	
                	String custSku = "";
                	try {
                		//custSku = pItm.getProduct().getCatalogStructure().getCustomerSkuNum();
                		custSku = pItm.getProduct().getActualCustomerSkuNum();
                	}catch(Exception e){
                	}
                	r.add((custSku==null?"":custSku));
                }
                r.add(pItm.getProduct().getCatalogProductShortDesc());
                
                if(isPollockStore) { //Add Category
                	if(isPollockCustomOrderGuide) { //Category Name
                		String customCategoryName = pItm.getCategoryPathForNewUI();
                		r.add((customCategoryName==null?"":customCategoryName));
                	} else {
                		//Category path
                    	String categoryPath = pItm.getCategoryPath();
                    	r.add((categoryPath==null?"":categoryPath));
                	}
                }
                
                if (showSize) {
                  String t = pItm.getProduct().getSize();
                  if (t == null || t.length() == 0) {
                    t = " ";
                  }
                  r.add(t);
                }
                if (showPack) {
                    r.add(pItm.getProduct().getPack());
                }
                if (showUom) {
                    r.add(pItm.getProduct().getUom());
                }
                if (showMfg) {
                  r.add(pItm.getProduct().getManufacturerName());
                }
                if (showMfgSku) {
                  r.add(pItm.getProduct().getManufacturerSku());
                }
                if (showPrice) {
                  BigDecimal price = new BigDecimal(pItm.getPrice());
                  //r.add(nf.format(price));
                  r.add(price);
                  if (!catalogListingOnly) {
                    // amount column
                    r.add(" ");
                  }
                }

                if(mAccount.isShowSPL()){
                if(pItm.getProduct() == null || pItm.getProduct().getCatalogDistrMapping() == null){
                    r.add(ClwI18nUtil.getMessage(request,"shoppingItems.text.n",null));
                }else{
                    if(Utility.isTrue(pItm.getProduct().getCatalogDistrMapping().getStandardProductList())){
                        r.add(ClwI18nUtil.getMessage(request,"shoppingItems.text.y",null));
                    }else{
                        r.add(ClwI18nUtil.getMessage(request,"shoppingItems.text.n",null));
                    }
                }
                }
                if(!isPollockStore) {// Bug # 5077 - Do not display Currency type for Pollock store
	                if (showPrice) { 
	                  if (pCatalogLocaleCd == null) {
	                      //pCatalogLocaleCd = "en_US";
	                      pCatalogLocaleCd = appUser.getUserLocaleCode(Locale.getDefault()).toString();
	                  }
	                  CurrencyData currencyD = ClwI18nUtil.getCurrency(pCatalogLocaleCd);
	                  r.add(currencyD.getGlobalCode());
	                }
                }
                restable.add(r);
                r = null;
            }

            xlog.setTable(restable);

            GenericReportColumnViewVector itemHeader =
                    new GenericReportColumnViewVector();
            if(printXpedxCols){

                if (pInventoryHeader) {
                    itemHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String","On_Hand",0,255,"VARCHAR2"));
                }
                itemHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Max_Qty",0,255,"VARCHAR2"));
                if(!isPhysicalCountSheet){
                	itemHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Order_Qty",0,255,"VARCHAR2"));
                }
            }else{
            	if(!catalogListingOnly){
	                String c1 = "";
	                if (pInventoryHeader) {
	                    itemHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String","On_Hand",0,255,"VARCHAR2"));
	                }
	                itemHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Order_Qty",0,255,"VARCHAR2"));
	            }
            }
            itemHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Our_Sku_#",0,255,"VARCHAR2"));
            
            if(isPollockStore) { // Bug # 5077 - Pollock Excel order guide needs Cust. Sku # column
            	itemHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Cust._Sku_#",0,255,"VARCHAR2"));
            }

            itemHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Product_Name",0,255,"VARCHAR2"));
            
            if(isPollockStore) { // Bug # 5077 - Pollock Excel order guide needs Category column
            	itemHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Category",0,255,"VARCHAR2"));
            }
            
            if (showSize) {
              itemHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Size", 0, 255, "VARCHAR2"));
            }
            if (showPack) {
                itemHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Pack",0,255,"VARCHAR2"));
            }
            if (showUom) {
                itemHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Uom",0,255,"VARCHAR2"));
            }
            if (showMfg) {
              itemHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Mfg.", 0, 255, "VARCHAR2"));
            }
            if (showMfgSku) {
              itemHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Mfg.Sku", 0, 255, "VARCHAR2"));
            }
            if (showPrice) {
              itemHeader.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "Price_money", 2, 20,
                "NUMBER"));
              if (!catalogListingOnly) {
                itemHeader.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "Amount_money", 2, 20,
                  "NUMBER"));
              }
            }
            if(mAccount.isShowSPL()){
            	itemHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String",ClwI18nUtil.getMessage(request,"shoppingItems.text.spl",null),0,255,"VARCHAR2"));
            }
            if (showPrice) {
            	 if(!isPollockStore) { // Bug # 5077 - Pollock Excel order guide does not need Currency Code column
	              itemHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Currency_Code", 0, 255,"VARCHAR2"));
            	 }
            }
            xlog.setHeader(itemHeader);
            xlog.setColumnCount(itemHeader.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return xlog;
    }


    /**
     *  Description of the Method
     *
     *@param  s  Description of the Parameter
     *@return    Description of the Return Value
     */
    private static GenericReportColumnView makeExcelCell(String s) {
        return ReportingUtils.createGenericReportColumnView
                ("java.lang.String", s, 0, 255, "VARCHAR2");
    }

    public static ActionErrors printDetail(HttpServletResponse response,
            HttpServletRequest request,
            ActionForm form,
            String userReq
            )
            throws Exception {
      return printDetail(response,request,form,userReq,null);
    }

    /**
     *  <code>print</code>
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@param  response       Description of the Parameter
     *@param  userReq        Description of the Parameter
     *@return                Description of the Return Value
     *@exception  Exception  if an error occurs
     */

    public static ActionErrors printDetail(HttpServletResponse response,
            HttpServletRequest request,
            ActionForm form,
            String userReq,
            MessageResources mr
            )
            throws Exception {

        HttpSession session = request.getSession();
        UserShopForm sForm = (UserShopForm) form;
        APIAccess factory = (APIAccess) session.getAttribute(
                Constants.APIACCESS);
        boolean catalog = false;
        boolean personal = false;
        boolean inventoryOnly = false;
        if (userReq.endsWith("Pers")) {
            personal = true;
        }
        if (userReq.indexOf("Catalog") >= 0) {
            catalog = true;
        }

        if (userReq.indexOf("Inventory") >= 0) {
            inventoryOnly = true;
            request.setAttribute("isPhysicalCountSheet","true");
        }

        if (null == factory) {
            throw new Exception("Without APIAccess.");
        }
        Store storeEjb = factory.getStoreAPI();
        CleanwiseUser appUser = (CleanwiseUser)
        session.getAttribute("ApplicationUser");
        StoreData storeD = appUser.getUserStore();
        String imgPath =
            ClwCustomizer.getCustomizeImgElement(request,"pages.logo1.image");
        List items = sForm.getCartItems();

        if (userReq.indexOf("CatalogCatalog") >= 0) {
        	ShoppingServices shoppingServEjb  = factory.getShoppingServicesAPI();
        	StoreData store = sForm.getAppUser().getUserStore();

        	PropertyData storeTypeProperty = store.getStoreType();
        	Integer catalogIdI = (Integer) session.getAttribute(Constants.CATALOG_ID);

            int catalogId = catalogIdI.intValue();
            Integer contractIdI = (Integer) session.getAttribute(Constants.CONTRACT_ID);
            int contractId = 0;
            if (catalogIdI != null) {
                contractId = contractIdI.intValue();
            }

            List<Integer> citems = new ArrayList<Integer>();
            for (int i = 0; i < sForm.getContractItemIds().length; i++) {
            	citems.add(new Integer(sForm.getContractItemIds()[i]));
			}

        	items = shoppingServEjb.prepareShoppingItems(storeTypeProperty.getValue(), sForm.getAppUser().getSite(),
                    catalogId,
                    contractId,
                    citems,
                    SessionTool.getCategoryToCostCenterView(request.getSession(),sForm.getAppUser().getSite().getSiteId()));

        	if(inventoryOnly){
        		Iterator it = items.iterator();
        		while(it.hasNext()){
        			ShoppingCartItemData anItem = (ShoppingCartItemData) it.next();
        			if(!anItem.getIsaInventoryItem()){
        				it.remove();
        			}
        		}
        	}
        	//decide grouping
        	Iterator it = items.iterator();
        	while(it.hasNext()){
    			ShoppingCartItemData anItem = (ShoppingCartItemData) it.next();
    			if(!Utility.isSet(anItem.getProduct().getCostCenterName())){
    				sForm.setGroupByCostCenter(false);
    			break;
    			}
    		}
        	BeanComparator comparator = null;
        	//BeanComparator comparator = new BeanComparator(new String[]{"getCategoryName", "getItemDesc"});
        	if(sForm.isGroupByCostCenter()){
        		comparator = new BeanComparator(new String[]{"getProduct-getCostCenterName",
        			"getProduct-getCatalogProductShortDesc"});
        	}else{
        		comparator = new BeanComparator(new String[]{"getCategoryName", "getProduct-getCatalogProductShortDesc"});
        	}
        	Collections.sort(items,comparator);

        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        String catalogLocaleCd = (String) session.getAttribute(Constants.CATALOG_LOCALE);

        if (userReq.startsWith("excel")) {

        	response.setContentType("application/x-excel");
    		String browser = (String)  request.getHeader("User-Agent");
    		boolean isMSIE6 = browser!=null && browser.indexOf("MSIE 6")>=0;
    		if (!isMSIE6){
    			response.setHeader("extension", "xls");
    		}
    		if(isMSIE6){
    			response.setHeader("Pragma", "public");
    			response.setHeader("Expires", "0");
    			response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
    			response.setHeader("Cache-Control", "public");

    		}
    		if(catalog){
    			response.setHeader("Content-disposition", "attachment; filename=" +userReq + "List.xls");
    		}else{
    			response.setHeader("Content-disposition", "attachment; filename=" +userReq + "OrderGuide.xls");
    		}

        	SiteData sd = appUser.getSite();
        	GenericReportResultViewVector excel_og =
	        	new GenericReportResultViewVector();
        	if(!catalog){
	        	excel_og.add(generateExcelSiteAddress
	        			(request, appUser,storeD,
	        					sForm.getAppUser().getUserAccount(),
	        					sd, items, personal));
        	}
	        excel_og.add(generateExcelItems(request, sForm, storeD, sd, items, catalog,
	            catalogLocaleCd, appUser.getShowPrice()));

	        try {
	        	if(mr!=null) {
	        		SessionTool st = new SessionTool(request);
	                String dateFmt = ClwI18nUtil.getCountryDateFormat(request);
	                ReportRequest rr = new ReportRequest(st.getUserData(),mr,dateFmt);
	                ReportWritter.writeExcelReportMulti(excel_og, out, rr);
	        	} else {
	        		ReportWritter.writeExcelReportMulti(excel_og, out);
	        	}
	        } catch (Exception e) {
	        	e.printStackTrace();
	        }

        } else {

	            //sets the content type so the browser knows this is a pdf
        	response.setContentType("application/pdf");
        	String browser = (String)  request.getHeader("User-Agent");
        	boolean isMSIE6 = browser!=null && browser.indexOf("MSIE 6")>=0;
        	//if (!isMSIE6){
        		response.setHeader("extension", "pdf");
        		if(catalog){
        			response.setHeader("Content-disposition", "attachment; filename=" +userReq + "List.pdf");
        		}else{
        			response.setHeader("Content-disposition", "attachment; filename=" +userReq + "OrderGuide.pdf");
        		}
        	//}

            int decimals = -1;
            Integer catalogDecimalsI = (Integer) session.getAttribute(Constants.CATALOG_DECIMALS);
            if (catalogDecimalsI!=null) decimals = catalogDecimalsI.intValue();

            Properties configProps = new Properties();
            String fileName = ClwCustomizer.getAbsFilePath(request,"config.txt");
            if(Utility.isSet(fileName)) {
                FileInputStream configIS = new FileInputStream(fileName);
                configProps.load(configIS);
            }
            if(catalog) {
                PdfCatalogProduct pdf = null;
                String pdfCatClass = configProps.getProperty("PdfCatalog");
                if(Utility.isSet(pdfCatClass)) {
                    try {
                        Class<?> clazz = Thread.currentThread().getContextClassLoader().loadClass(pdfCatClass);
                        pdf = (PdfCatalogProduct) clazz.newInstance();
                    } catch (Exception exc) {
                        log.info("!!!!ATTENTION failed creating PdfCatalogProduct using class name: "+pdfCatClass);
                    }
                }
                if (pdf == null) pdf = new PdfCatalogProduct();
                pdf.init(request, catalogLocaleCd);
                pdf.generatePdf(sForm, items, storeD, out, imgPath, personal,catalog,inventoryOnly);
            } else {
                PdfOrderGuide pdf = null;
                String pdfClass = configProps.getProperty("PdfOrderGuide");

                if(Utility.isSet(pdfClass)) {
                    try {
                        Class<?> clazz = Thread.currentThread().getContextClassLoader().loadClass(pdfClass);
                        pdf = (PdfOrderGuide) clazz.newInstance();
                    } catch (Exception exc) {
                        log.info("!!!!ATTENTION failed creating PdfOrderGuide using class name: "+pdfClass);
                    }
                }
                if(pdf==null) pdf = new PdfOrderGuide();
                pdf.init(request, catalogLocaleCd);

                //PdfOrderGuide pdf = new PdfOrderGuide(request, catalogLocaleCd);
                pdf.generatePdf(sForm, items, storeD, out, imgPath, personal,catalog,catalogLocaleCd);
            }
        }

        try{
	        response.setContentLength(out.size());
	        out.writeTo(response.getOutputStream());
	        response.flushBuffer();
	        response.getOutputStream().close();
	        return new ActionErrors();
        }catch(IOException e){
        	response.getOutputStream().close();
	        return new ActionErrors();
        }
    }


    //**********************************************************************
    /**
     *  Description of the Method
     *
     *@param  request  Description of the Parameter
     *@param  pForm    Description of the Parameter
     *@return          Description of the Return Value
     */
    public static ActionErrors updateInventory(HttpServletRequest request,
            UserShopForm pForm) {

        ActionErrors ae = new ActionErrors();
        APIAccess factory = (APIAccess) request.getSession().getAttribute(
                Constants.APIACCESS);
        if (factory == null) {
            ae.add("error",
                    new ActionError("error.systemError", "No Ejb access"));
            return ae;
        }

        Site siteEjb = null;
        try {
            siteEjb = factory.getSiteAPI();
        } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
            ae.add("error",
                    new ActionError("error.systemError",
                    "No EJB services available 101."));
            return ae;
        }

        List cartItems = pForm.getCartItems();
        int siteid = ShopTool.getCurrentSiteId(request);

        try {
            ae = saveInventoryOnHandValues(request, siteid, cartItems, true);
        } catch (Exception e) {
            ae.add("error",
                    new ActionError("error.systemError",
                    "Cannot save inventory on hand values"));
        }
        return ae;
    }


    /**
     *  Description of the Method
     *
     *@param  request            Description of the Parameter
     *@param  pSiteId            Description of the Parameter
     *@param  cartItems          Description of the Parameter
     *@param  pUpdateNullValues  Description of the Parameter
     *@return                    Description of the Return Value
     *@exception  Exception      Description of the Exception
     */
    public static ActionErrors saveInventoryOnHandValues
            (HttpServletRequest request, int pSiteId, List cartItems,
            boolean pUpdateNullValues)
            throws Exception {
        ActionErrors ae = new ActionErrors();
        APIAccess factory = new APIAccess();
        SiteInventoryConfigViewVector onhv =
                new SiteInventoryConfigViewVector();
        CleanwiseUser ud = ShopTool.getCurrentUser(request);
        HttpSession session = request.getSession();
        ShoppingCartData shoppingCart =
               (ShoppingCartData) session.getAttribute(Constants.SHOPPING_CART);
        ShoppingCartItemDataVector scItems = shoppingCart.getItems();
        if(scItems==null) scItems = new ShoppingCartItemDataVector();

        Site siteEjb = factory.getSiteAPI();
        ShoppingServices shoppingServEjb  = factory.getShoppingServicesAPI();

        for (int i = 0; cartItems != null && i < cartItems.size(); i++) {

            ShoppingCartItemData scid =
                    (ShoppingCartItemData) cartItems.get(i);
            if (!scid.getIsaInventoryItem()) {
                continue;
            }

            SiteInventoryInfoView invItem = ShopTool.getInventoryItem
                    (request, scid.getProduct().getProductId());
            String onhstr = scid.getInventoryQtyOnHandString();
            if (null == onhstr) {
                onhstr = "";
            }
            if (scid.getInventoryParValue() > 0 &&
                    onhstr.length() == 0) {
                Object[] param = new Object[1];
                param[0] = scid.getActualSkuNum();
                String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.onHandQuantityMissing",param);
                ae.add("error",
                        new ActionError
                        ("error.simpleGenericError",errorMess));
                return ae;
            } else if (scid.getInventoryParValue() <= 0) {
                onhstr = "0";
            }
            int onHandQty = 0;
            try {
                onHandQty = Integer.parseInt(onhstr);
            } catch (Exception e) {
                Object[] param = new Object[1];
                param[0] = scid.getActualSkuNum();
                String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.onHandQuantityInvalid",param);
                ae.add("error",
                        new ActionError
                        ("error.simpleGenericError",errorMess));
                return ae;
            }

            scid.setInventoryQtyIsSet(true);
            invItem.setQtyOnHand(onhstr);
            invItem.setUpdateUser(ud.getUser().getUserName());
            if ( invItem.getSumOfAllParValues() > 0 ) {
                onhv.add(invItem);
                if(invItem.getParValue()-onHandQty>0) {
                    boolean foundFl = false;
                    for(Iterator iter=scItems.iterator(); iter.hasNext();) {
                        ShoppingCartItemData si = (ShoppingCartItemData) iter.next();
                        if(si.getItemId()==scid.getItemId()) {
                            foundFl = true;
                            break;
                        }
                    }
                    if(!foundFl) {
                        shoppingCart.addItem(scid);
                    }
                }
            }
        }



        try {
            if (pUpdateNullValues) {
                ShopTool.setInventoryItems
                        (request, siteEjb.updateAndResetInventory(onhv, ud.getUser()));
            } else {
                ShopTool.setInventoryItems
                        (request, siteEjb.updateInventory(onhv, ud.getUser()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Reload shopping cart
        try {
            shoppingCart =
                shoppingServEjb.getShoppingCart(shoppingCart.getStoreType(),
                                                  ud.getUser(), ud.getSite(),
                                                  shoppingCart.getCatalogId(),
                                                  shoppingCart.getContractId(),
                                                  SessionTool.getCategoryToCostCenterView(session, ud.getSite().getSiteId()));

            session.setAttribute(Constants.SHOPPING_CART,shoppingCart);

        } catch(RemoteException exc) {
          ae.add("error",new ActionError("error.systemError",
          "Failed to get the shopping cart data"));
          return ae;
        }

        return ae;
    }


    //************************************************
    /**
     *  Description of the Method
     *
     *@param  request  Description of the Parameter
     *@param  pForm    Description of the Parameter
     *@return          Description of the Return Value
     */
    public static ActionErrors recalculateQuantities
            (HttpServletRequest request,
            UserShopForm pForm) {

        ActionErrors ae = new ActionErrors();

        //Check validity of the quantities entered.
        List cartItems = pForm.getCartItems();

        if(cartItems == null){
        	pForm.setCartItems(cartItems);
        	return ae;

        }
        Iterator it = cartItems.iterator();
        List<String> msgList = new ArrayList<String>();
        while(it.hasNext()){
            ShoppingCartItemData sciTemplateD =(ShoppingCartItemData) it.next();
            int qq = 0;
            String qqS = sciTemplateD.getQuantityString();

            if (qqS != null && qqS.trim().length() > 0) {

                try {
                    qq = Integer.parseInt(qqS);
                    /*if(qq<0){
                    	String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.enterQty",null);
	                    ae.add("error",new ActionError("error.simpleGenericError",errorMess));
                    }*/
                    if(qq <= 0){
                    	it.remove();
                    }else{
                    	sciTemplateD.setQuantity(qq);
                    }
                } catch (NumberFormatException exc) {

                	msgList.add(sciTemplateD.getActualSkuNum());
                }
            }

        }
        if(msgList.size()>0 && !msgList.isEmpty()){
        	Object[] param = new Object[1];
        	param[0] = Utility.toCommaSting(msgList);
            String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.invalidQtyMessage",param);
            ae.add("error",
                    new ActionError("error.simpleGenericError",errorMess));
        	return ae;
        }
        pForm.setCartItems(cartItems);
        return ae;
    }


    //***************************************************************
    /**
     *  Adds a feature to the ItemsToCart attribute of the UserShopLogic class
     *
     *@param  request  The feature to be added to the ItemsToCart attribute
     *@param  pForm    The feature to be added to the ItemsToCart attribute
     *@return          Description of the Return Value
     */
    public static ActionErrors addItemsToCart
            (HttpServletRequest request,
            UserShopForm pForm) {

        ActionErrors ae = new ActionErrors();
        String itemsSubmitStamp = pForm.getItemsSubmitStamp();
        String itemsStamp = pForm.getItemsStamp();
        if(Utility.isSet(itemsSubmitStamp) && Utility.isSet(itemsStamp) &&
           !itemsSubmitStamp.equals(itemsStamp)) {
            String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.pageExpired",null);
            ae.add("error",
                    new ActionError("error.simpleGenericError",errorMess));
            return ae;
        }
        //ae = recalculateQuantities(request, pForm);

        if (ae.size() > 0) {
            String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.checkQuantityValues",null);
            ae.add("error",
                    new ActionError("error.simpleGenericError",errorMess));
            return ae;
        }

        if (ShopTool.isShoppingCartConsolidated(request)) {
            String errorMsg = ClwI18nUtil.getMessage(request, "shop.errors.addItemsIntoConsolidatedCart", null);
            ae.add("error", new ActionError("error.simpleGenericError", errorMsg));
            return ae;
        }

        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess) session.getAttribute
                (Constants.APIACCESS);

        if (factory == null) {
            ae.add("error",
                    new ActionError("error.systemError", "No Ejb access"));

            return ae;
        }

        ShoppingServices shoppingServEjb = null;
        Site siteEjb = null;

        try {
            shoppingServEjb = factory.getShoppingServicesAPI();
            siteEjb = factory.getSiteAPI();
        } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
            ae.add("error",
                    new ActionError("error.systemError",
                    "No EJB services available 100."));

            return ae;
        }

        Order orderEjb;
        try {
        	orderEjb = factory.getOrderAPI();
        } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
            ae.add("error", new ActionError("error.systemError", "No order Ejb pointer"));
            return ae;
        }

        List cartItems = pForm.getCartItems();

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute
                (Constants.APP_USER);

        if (appUser == null) {
            ae.add("error",
                    new ActionError("error.systemError",
                    "No session user found"));

            return ae;
        }

        ShoppingCartData cartD = ShopTool.getCurrentShoppingCart(request);
        ShoppingCartItemDataVector newItems = new ShoppingCartItemDataVector();

        List<String> msgList = new ArrayList<String>();
        //first loop through and parse qty
        for (int ii = 0; cartItems != null && ii < cartItems.size(); ii++) {
        	ShoppingCartItemData sciTemplateD = (ShoppingCartItemData) cartItems.get(ii);
			if(Utility.isSet(sciTemplateD.getQuantityString())) {
				try{
					int qtyentered = Integer.parseInt(sciTemplateD.getQuantityString());
					if (qtyentered <= 0) {

						msgList.add(sciTemplateD.getActualSkuNum());
                    }
				}catch(NumberFormatException exc){

					msgList.add(sciTemplateD.getActualSkuNum());
				}
			}

        }
        if(msgList.size()>0 && !msgList.isEmpty()){
        	Object[] param = new Object[1];
        	param[0] = Utility.toCommaSting(msgList);
            String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.invalidQtyMessage",param);
            //pForm.setInvalidQtyMessage(errorMess);
            ae.add("error",
                    new ActionError("error.simpleGenericError",errorMess));
        	return ae;
        }
        if(ae.size() > 0){
        	return ae;
        }


        for (int ii = 0; cartItems != null && ii < cartItems.size(); ii++) {
            ShoppingCartItemData sciTemplateD =
                    (ShoppingCartItemData) cartItems.get(ii);

            if ((!sciTemplateD.getIsaInventoryItem()
                    ||(sciTemplateD.getIsaInventoryItem() && ShopTool.isModernInventoryShopping(request)))
                    &&sciTemplateD.getQuantityString() != null
                    &&sciTemplateD.getQuantityString().trim().length() > 0) {

            		try{

            			int qtyentered = Integer.parseInt
	                        (sciTemplateD.getQuantityString());
            			if (qtyentered > 0) {
		                    sciTemplateD.setQuantity(qtyentered);
		                    ShoppingCartItemData nitem = sciTemplateD.copy();
		                    newItems.add(nitem);
            			}
            		}catch(NumberFormatException exc){
                               //was checked above should not happen
            			Object[] params = new Object[1];
            	        params[0] = sciTemplateD.getQuantityString();
            	        String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.wrongQuantityNumberFormat",params);
            	        ae.add("error",new ActionError("error.simpleGenericError",errorMess));

            		}

            }
        }

        if (newItems.isEmpty()) {
           String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.enterQty",null);
        	ae.add("error",
                    new ActionError("error.simpleGenericError",errorMess));

            return ae;
        }


        cartD.setSite(pForm.getAppUser().getSite());

        ValidateActionMessage am = ShopTool.addItemsToCart(request, cartD, newItems);
        if (am.hasErrors()) {
            return am.getActionErrors();
        }
        if (am.hasWarnings()) {
            cartD.addWarningMessages(am.getWarnings());
        }

        ae = ShoppingCartLogic.saveShoppingCart(session, newItems);

        /*for(int m=0; m<cartD.getItemMessages().size(); m++){

        	ShoppingCartData.CartItemInfo cii =
                (ShoppingCartData.CartItemInfo) cartD.getItemMessages().get(m);

        	ArrayList al = cii.getI18nItemMessageAL();
        	String key = (String) al.get(0);

        	Object[]  params = null;
        	if(al.size()>1) {
        		params = new Object[6];
        		for(int ii=0; ii<al.size()-1; ii++) {
        			params[ii] = (Object)al.get(ii+1);
        		}
        	}

        	String errorMess = ClwI18nUtil.getMessage(request,key,params);
            ae.add("error",
                    new ActionError("error.simpleGenericError",errorMess));
        }*/

        if (ae.size() > 0) {
            return ae;
        }else{
        	pForm.setConfirmMessage(ClwI18nUtil.getMessage(request,
                    "shoppingCart.text.actionMessage.itemAdded", null));
        }

        ae=initOrderGuide(request,pForm);
        //clearQuantities(cartItems);
        return ae;
    }



    //  ***************************************************************
    /**
     *  Adds a feature to the addToNewXpedxInvCart attribute of the UserShopLogic class
     *
     *@param  request  The feature to be added to the ItemsToCart attribute
     *@param  pForm    The feature to be added to the ItemsToCart attribute
     *@return          Description of the Return Value
     */
    public static ActionErrors addToNewXpedxInvCart
            (HttpServletRequest request,
            UserShopForm pForm) {

        ActionErrors ae = new ActionErrors();
        String itemsSubmitStamp = pForm.getItemsSubmitStamp();
        String itemsStamp = pForm.getItemsStamp();
        if(Utility.isSet(itemsSubmitStamp) && Utility.isSet(itemsStamp) &&
           !itemsSubmitStamp.equals(itemsStamp)) {
            String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.pageExpired",null);
            ae.add("error",
                    new ActionError("error.simpleGenericError",errorMess));
            return ae;
        }
        //ae = recalculateQuantities(request, pForm);

        if (ae.size() > 0) {
            String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.checkQuantityValues",null);
            ae.add("error",
                    new ActionError("error.simpleGenericError",errorMess));
            return ae;
        }

        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess) session.getAttribute
                (Constants.APIACCESS);

        if (factory == null) {
            ae.add("error",
                    new ActionError("error.systemError", "No Ejb access"));

            return ae;
        }

        ShoppingServices shoppingServEjb = null;
        Site siteEjb = null;

        try {
            shoppingServEjb = factory.getShoppingServicesAPI();
            siteEjb = factory.getSiteAPI();
        } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
            ae.add("error",
                    new ActionError("error.systemError",
                    "No EJB services available 100."));

            return ae;
        }

        Order orderEjb;
        try {
        	orderEjb = factory.getOrderAPI();
        } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
            ae.add("error", new ActionError("error.systemError", "No order Ejb pointer"));
            return ae;
        }

        List cartItems = pForm.getCartItems();

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute
                (Constants.APP_USER);

        if (appUser == null) {
            ae.add("error",
                    new ActionError("error.systemError",
                    "No session user found"));

            return ae;
        }

        ShoppingCartData cartD = ShopTool.getCurrentInventoryCart(request.getSession());

        List<String> msgList = new ArrayList<String>();
        ShoppingCartItemDataVector newItems = new ShoppingCartItemDataVector();
        cartD.setItemMessages(new ArrayList());
        for (int ii = 0; cartItems != null && ii < cartItems.size(); ii++) {
            ShoppingCartItemData sciTemplateD =
                    (ShoppingCartItemData) cartItems.get(ii);

            if (!sciTemplateD.getIsaInventoryItem()
                    &&sciTemplateD.getQuantityString() != null
                    &&sciTemplateD.getQuantityString().trim().length() > 0) {

            	try{
	                int qtyentered = Integer.parseInt
	                        (sciTemplateD.getQuantityString());
	                if (qtyentered > 0) {
	                    sciTemplateD.setQuantity(qtyentered);
	                    ShoppingCartItemData nitem = sciTemplateD.copy();
	                    newItems.add(nitem);
                    }
            	}catch(NumberFormatException exc){
            		msgList.add(sciTemplateD.getActualSkuNum());
        		}
            }
        }

        if(msgList.size()>0 && !msgList.isEmpty()){
        	Object[] param = new Object[1];
        	param[0] = Utility.toCommaSting(msgList);
            String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.invalidQtyMessage",param);
            ae.add("error",
                    new ActionError("error.simpleGenericError",errorMess));
        	return ae;
        }

        if (newItems.isEmpty()) {
           String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.enterQty",null);
        	ae.add("error",
                    new ActionError("error.simpleGenericError",errorMess));

            return ae;
        }


        cartD.setSite(pForm.getAppUser().getSite());

        ValidateActionMessage am = ShopTool.addItemsToCart(request, cartD, newItems);
        if (am.hasErrors()) {
            return am.getActionErrors();
        }
        if (am.hasWarnings()) {
            cartD.addWarningMessages(am.getWarnings());
        }

        if (cartD.getItemsQty() > 0) {
            try {

                if (!appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.OVERRIDE_SHOPPING_RESTRICTION)) {
                    //cartD.reviseQuantities();
                	int siteId = pForm.getAppUser().getSite().getSiteId();
                	log.info("addToNewXpedxInvCart() method: cartD = " + cartD);
            		ShoppingCartLogic.reviseShoppingCartQuantities(cartD, orderEjb, siteId, newItems);
                }

                boolean generateInvAuditEntry = false;
                shoppingServEjb.saveInventoryShoppingCart(cartD, generateInvAuditEntry);

            } catch (Exception e) {
                //String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.noItemsSelected",null);
                ae.add("error", new ActionError("error.simpleGenericError", e.getMessage()));
                return ae;
            }
        } else {
            String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.enterQty", null);
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }

        ae = ShopTool.reloadInvShoppingCart(shoppingServEjb, request.getSession(), null, appUser.getSite(), appUser.getUser(), appUser.getUserStore().getStoreType().getValue());

        ShoppingCartData newShoppingCart = ShopTool.getCurrentInventoryCart(session);
        newShoppingCart.addItemMessages(cartD.getItemMessages());
        newShoppingCart.addWarningMessages(cartD.getWarningMessages());
        session.setAttribute(Constants.INVENTORY_SHOPPING_CART,newShoppingCart);

        ae=initOrderGuide(request,pForm);
        //clearQuantities(cartItems);

        for(int m=0; m<newShoppingCart.getItemMessages().size(); m++){

        	ShoppingCartData.CartItemInfo cii =
                (ShoppingCartData.CartItemInfo) newShoppingCart.getItemMessages().get(m);

        	ArrayList al = cii.getI18nItemMessageAL();
        	String key = (String) al.get(0);

        	Object[]  params = null;
        	if(al.size()>1) {
        		params = new Object[6];
        		for(int ii=0; ii<al.size()-1; ii++) {
        			params[ii] = (Object)al.get(ii+1);
        		}
        	}


        	String errorMess = ClwI18nUtil.getMessage(request,key,params);


            ae.add("error",
                    new ActionError("error.simpleGenericError",errorMess));
        }
        return ae;
    }


    /**
     *  Description of the Method
     *
     *@param  cartItems  Description of the Parameter
     */
    private static void clearQuantities(List cartItems) {
        // Clear the quantity values.
        for (int ii = 0; cartItems != null &&
                ii < cartItems.size(); ii++) {
            ShoppingCartItemData sciTemplateD =
                    (ShoppingCartItemData) cartItems.get(ii);
            sciTemplateD.setQuantity(0);
            sciTemplateD.setQuantityString("");
        }
    }


    //****************************************************************************
    /**
     * Description of the Method
     *
     * @param request Description of the Parameter
     * @param pForm   Description of the Parameter
     * @return Description of the Return Value
     * @throws Exception Description of the Exception
     */
    public static ActionErrors searchOG(HttpServletRequest request, UserShopForm pForm) throws Exception {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();

        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        if (factory == null) {
            ae.add("error", new ActionError("error.systemError", "No Ejb access"));
            return ae;
        }

        ShoppingServices shoppingServEjb;
        try {
            shoppingServEjb = factory.getShoppingServicesAPI();
        } catch (APIServiceAccessException exc) {
            exc.printStackTrace();
            ae.add("error", new ActionError("error.systemError", "No shopping services Ejb access"));
            return ae;
        }

        ArrayList templateOrderGuideIds = pForm.getTemplateOrderGuideIds();
        ArrayList userOrderGuideIds = pForm.getUserOrderGuideIds();

        if (templateOrderGuideIds == null ||
                userOrderGuideIds == null ||
                templateOrderGuideIds.size() + userOrderGuideIds.size() == 0) {
            ae.add("error", new ActionError("error.systemError", "Wrong template oder guide menu"));
            return ae;
        }

        IdVector orderGuideIds = new IdVector();

        for (Object templateOrderGuideId : templateOrderGuideIds) {
            orderGuideIds.add(templateOrderGuideId);
        }

        for (Object userOrderGuideId : userOrderGuideIds) {
            orderGuideIds.add(userOrderGuideId);
        }

        IdVector searchRes;

        String custSku = "";
        String mfgSku = "";
        String name = pForm.getOgSearchName();
        String desc = "";
        String category = "";
        String size = "";
        int mfgId = 0;

        CleanwiseUser appUser = pForm.getAppUser();

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

            searchRes = shoppingServEjb.searchOrderGuideItems(storeTypeProperty.getValue(),
                    orderGuideIds,
                    custSku,
                    mfgSku,
                    name,
                    desc,
                    category,
                    size,
                    mfgId,
                    pForm.getSearchGreenCertifiedFl(),
                    ShopTool.createShoppingItemRequest(request));

        } catch (RemoteException exc) {
            exc.printStackTrace();
            ae.add("error", new ActionError("error.systemError", exc.getMessage()));
            return ae;
        }

        pForm.setProductList(searchRes);

        ae = prepareCartItems(request, pForm);
        pForm.setCartItems(shoppingServEjb.orderList(pForm.getCartItems(), Constants.ORDER_BY_CATEGORY));

        return ae;
    }

    private static ActionErrors initManufactures(ShoppingServices shoppingServEjb,
                                                 UserShopForm pForm,
                                                 HttpSession session) {
        //select manufacturers
        BusEntityDataVector manufacturers;

        ActionErrors ae = new ActionErrors();

        CleanwiseUser appUser = ShopTool.getCurrentUser(session);

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

        try {
            manufacturers = shoppingServEjb.getCatalogManufacturers(ShopTool.createShoppingItemRequest(session));
        } catch (Exception exc) {
            exc.printStackTrace();
            ae.add("error", new ActionError("error.systemError", exc.getMessage()));
            return ae;
        }

        pForm.setManufacturers(manufacturers);

        ArrayList<String> menuMfgLables = new ArrayList<String>();
        ArrayList<String> menuMfgOptions = new ArrayList<String>();
        for (int ii = 0; ii < manufacturers.size(); ii++) {
            BusEntityData beD = (BusEntityData) manufacturers.get(ii);
            menuMfgLables.add(beD.getShortDesc());
            menuMfgOptions.add("" + beD.getBusEntityId());
        }

        pForm.setMenuMfgLabels(menuMfgLables);
        pForm.setMenuMfgOptions(menuMfgOptions);

        return ae;

    }


    private static boolean isShowValue(HttpServletRequest request, String pParamName) {
      String value = request.getParameter(pParamName);
      if (Utility.isSet(value) && !Utility.isTrue(value)) {
        return false;
      }
      return true;
    }

    public static ActionErrors addToActiveXpedxCart(HttpServletRequest request, UserShopForm pForm) {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();

        if (!ShopTool.hasDiscretionaryCartAccessOpen(request)) {
            if (session.getAttribute(Constants.INVENTORY_SHOPPING_CART) != null) {
                ae = addToInventoryCart(request, pForm);
            } else {
                String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.scheduleOrderNoLongerAvailable", null);
                ae.add("error", new ActionError("error.simpleGenericError", errorMess));
                return ae;
            }
        } else {
            ae = addToCart(request, pForm);
        }

        Pager pager = pForm.getPager();
        if (pager != null ) {
            pager.setPageItemList(pForm.getPageIndex(), pForm.getCartItems());
        }

        return ae;
    }

    public static ActionErrors addToActiveNewXpedxCart(HttpServletRequest request, UserShopForm pForm) {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();

        if (!ShopTool.hasDiscretionaryCartAccessOpen(request)) {
            if (session.getAttribute(Constants.INVENTORY_SHOPPING_CART) != null) {
                ae = addToNewXpedxInvCart(request, pForm);
            } else {
                String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.scheduleOrderNoLongerAvailable", null);
                ae.add("error", new ActionError("error.simpleGenericError", errorMess));
                return ae;
            }
        } else {
            ae = addItemsToCart(request, pForm);
        }

        Pager pager = pForm.getPager();
        if (pager != null ) {
            pager.setPageItemList(pForm.getPageIndex(), pForm.getCartItems());
        }

        return ae;
    }

    public static ActionErrors addItemToActiveXpedxCart(HttpServletRequest request,UserShopForm pForm)
	throws Exception
	{
        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
         if(!ShopTool.hasDiscretionaryCartAccessOpen(request)) {
           if(session.getAttribute(Constants.INVENTORY_SHOPPING_CART) != null) {
				ae = itemToInvCart(request,pForm);
		   } else {
				String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.scheduleOrderNoLongerAvailable",null);
				ae.add("error", new ActionError("error.simpleGenericError", errorMess));
				return ae;
		   }
        } else {
			ae = itemToCart(request,pForm);
        }
		return ae;
	}


    public static ActionErrors addItemsToInventoryCart(HttpServletRequest request,UserShopForm pForm) {

        ActionErrors ae = new ActionErrors();
        String itemsSubmitStamp = pForm.getItemsSubmitStamp();
        String itemsStamp = pForm.getItemsStamp();
        if(Utility.isSet(itemsSubmitStamp) && Utility.isSet(itemsStamp) &&
           !itemsSubmitStamp.equals(itemsStamp)) {
            String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.pageExpired",null);
            ae.add("error",
                    new ActionError("error.simpleGenericError",errorMess));
            return ae;
        }

        ae = checkQuantities(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }

        ae = recalculateQuantities(request, pForm);
        if (ae.size() > 0) {
            String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.checkQuantityValues",null);
            ae.add("error", new ActionError("error.simpleGenericError",errorMess));
            return ae;
        }

        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);

        ShoppingServices shoppingServEjb = null;
        Site siteEjb = null;

        if (factory == null) {
            ae.add("error",new ActionError("error.systemError", "No Ejb access"));
            return ae;
        }

        try {
            shoppingServEjb = factory.getShoppingServicesAPI();
            siteEjb = factory.getSiteAPI();
        } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
            ae.add("error",new ActionError("error.systemError","No EJB services available."));
            return ae;
        }

        Order orderEjb;
        try {
        	orderEjb = factory.getOrderAPI();
        } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
            ae.add("error", new ActionError("error.systemError", "No order Ejb pointer"));
            return ae;
        }

        SiteData site                = ShopTool.getCurrentSite(request);
        CleanwiseUser clwUser        = ShopTool.getCurrentUser(request);
        UserData user                = clwUser.getUser();
        StoreData store              = clwUser.getUserStore();


        if(!ShopTool.hasInventoryCartAccessOpen(request)) {
            String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.scheduleOrderNoLongerAvailable",null);
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }

        ShoppingCartData currentCart = ShopTool.getCurrentInventoryCart(session);
        if (currentCart == null) {
            currentCart = new ShoppingCartData();
            currentCart.setUser(clwUser.getUser());
            currentCart.setSite(clwUser.getSite());
        }

        List items = pForm.getCartItems();
        ShoppingCartItemDataVector newItems = new ShoppingCartItemDataVector();
        boolean cartAvailable = ShopTool.isModernInventoryCartAvailable(request);
        int i = 0;
        if(items!=null && items.size()>0){
            Iterator it = items.iterator();
            while(it.hasNext()){
                ShoppingCartItemData scid = (ShoppingCartItemData) it.next();
                if(scid.getQuantity()>0 &&cartAvailable) {
                    if (!scid.getIsaInventoryItem()) {
                        newItems.add(scid);
                    } else {
                    Object[] param = new Object[1];
                    param[0] = scid.getActualSkuNum();
                    String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.attemptToAddInvItemToInvCart", param);
                    ae.add("error" + (++i), new ActionError("error.simpleGenericError", errorMess));
                  }
                }
            }
        }
        if (ae.size() > 0) {
          return ae;
        }

        ValidateActionMessage am = ShopTool.addItemsToCart(request, currentCart, newItems);
        if (am.hasErrors()) {
            return am.getActionErrors();
        }
        if (am.hasWarnings()) {
            currentCart.addWarningMessages(am.getWarnings());
        }

        if (currentCart.getItemsQty()>0){
            try {

                CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);
                if (!appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.OVERRIDE_SHOPPING_RESTRICTION)) {
                    //currentCart.reviseQuantities();
                	int siteId = clwUser.getSite().getSiteId();
                	log.info("addItemsToInventoryCart() method: currentCart  = " + currentCart);
            		ShoppingCartLogic.reviseShoppingCartQuantities(currentCart, orderEjb, siteId, newItems);
                }

                boolean generateInvAuditEntry = false;
                shoppingServEjb.saveInventoryShoppingCart(currentCart, generateInvAuditEntry);
                clearQuantities(pForm.getCartItems());
            } catch (Exception e) {
                //String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.noItemsSelected",null);
                ae.add("error",new ActionError("error.simpleGenericError",e.getMessage()));
                return ae;
            }
        } else {
            String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.noItemsSelected",null);
            ae.add("error",new ActionError("error.simpleGenericError",errorMess));
            return ae;
        }

        ae = ShopTool.reloadInvShoppingCart(shoppingServEjb,
                session,
                null,
                site,
                user,
                clwUser.getUserStore().getStoreType().getValue());

        ShoppingCartData shoppingCart = (ShoppingCartData) session.getAttribute(Constants.INVENTORY_SHOPPING_CART);

        shoppingCart.addItemMessages(currentCart.getItemMessages());
        shoppingCart.addWarningMessages(currentCart.getWarningMessages());
        session.setAttribute(Constants.INVENTORY_SHOPPING_CART,shoppingCart);

        return ae;
    }

    public static ActionErrors addToInventoryCart(HttpServletRequest request, UserShopForm pForm) {

        ActionErrors ae = new ActionErrors();
        String itemsSubmitStamp = pForm.getItemsSubmitStamp();
        String itemsStamp = pForm.getItemsStamp();
        if(Utility.isSet(itemsSubmitStamp) && Utility.isSet(itemsStamp) &&
           !itemsSubmitStamp.equals(itemsStamp)) {
            String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.pageExpired",null);
            ae.add("error",
                    new ActionError("error.simpleGenericError",errorMess));
            return ae;
        }

        HttpSession session = request.getSession();

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        if (appUser == null) {
            ae.add("error", new ActionError("error.systemError", "No session user found"));
            return ae;
        }

        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        if (factory == null) {
            ae.add("error", new ActionError("error.systemError", "No Ejb access"));
            return ae;
        }

        ShoppingServices shoppingServEjb = null;
        try {
            shoppingServEjb = factory.getShoppingServicesAPI();
        } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
            ae.add("error",new ActionError("error.systemError","No EJB services available 100."));
            return ae;
        }

        Order orderEjb;
        try {
        	orderEjb = factory.getOrderAPI();
        } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
            ae.add("error", new ActionError("error.systemError", "No order Ejb pointer"));
            return ae;
        }

        if(!ShopTool.hasInventoryCartAccessOpen(request)) {
            String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.scheduleOrderNoLongerAvailable",null);
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }

        String[] quantityS = pForm.getQuantity();
        int[] itemIds = pForm.getItemIds();

        List cartItems = pForm.getCartItems();
        int cartItemsSize = cartItems.size();

        int[] quantity = new int[quantityS.length];

        if (itemIds.length < quantity.length) {
            ae.add("error", new ActionError("error.systemError", "Wrong item identificator array size"));
            return ae;
        }

        List<String> msgList = new ArrayList<String>();

        if(quantity.length==0 || quantity==null){

        	String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.enterQty",null);
        	ae.add("error",
                    new ActionError("error.simpleGenericError",errorMess));
        	return ae;
        }

        for (int ii = 0; ii < quantityS.length; ii++) {

            ShoppingCartItemData sciTemplateD = (ShoppingCartItemData) cartItems.get(ii);

            String qqS = quantityS[ii];
            if (qqS != null && qqS.trim().length() > 0) {

                if (ii >= cartItemsSize) {
                    String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.pageInfoDoesntMatchServer", null);
                    ae.add("error", new ActionError("error.simpleGenericError", errorMess));
                    return ae;
                }

                if (sciTemplateD.getProduct().getProductId() != itemIds[ii]) {
                    String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.pageInfoDoesntMatchServer", null);
                    ae.add("error", new ActionError("error.simpleGenericError", errorMess));
                    return ae;
                }

            }

            setQuantityString(cartItems, ii, quantityS[ii]);

            if (qqS != null && qqS.trim().length() > 0) {
                try {
                    int qq = Integer.parseInt(qqS);
                    if (qq <= 0) {
                        msgList.add(sciTemplateD.getActualSkuNum());
                    }
                    quantity[ii] = qq;
                } catch (NumberFormatException exc) {
                    msgList.add(sciTemplateD.getActualSkuNum());
                }
            } else {
                quantity[ii] = 0;
            }

        }

        if(msgList.size()>0 && !msgList.isEmpty()){
        	Object[] param = new Object[1];
        	param[0] = Utility.toCommaSting(msgList);
            String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.invalidQtyMessage",param);
            //pForm.setInvalidQtyMessage(errorMess);
            ae.add("error",
                    new ActionError("error.simpleGenericError",errorMess));
        	return ae;
        }
        if (ae.size() > 0) {
            return ae;
          }else{
          	pForm.setConfirmMessage(ClwI18nUtil.getMessage(request,
                      "shoppingCart.text.actionMessage.itemAdded", null));
          }

        ShoppingCartData cartD = ShopTool.getCurrentInventoryCart(session);
        if (cartD == null) {
            cartD = new ShoppingCartData();
            cartD.setUser(appUser.getUser());
            cartD.setSite(appUser.getSite());
        }

        ShoppingCartItemDataVector newItems = new ShoppingCartItemDataVector();
        cartD.clearNewItems();
        cartD.setItemMessages(new ArrayList());
        int i = 0;
        for (int ii = 0; ii < quantity.length; ii++) {
            if (quantity[ii] > 0) {
                ShoppingCartItemData sciTemplateD = (ShoppingCartItemData) cartItems.get(ii);
                if(!sciTemplateD.getIsaInventoryItem()){
                    sciTemplateD.setQuantity(0);
                    ShoppingCartItemData sciD = sciTemplateD.copy();
                    sciD.setQuantity(quantity[ii]);
                    newItems.add(sciD);
                } else {
                  Object[] param = new Object[1];
                  param[0] = sciTemplateD.getActualSkuNum();
                  String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.attemptToAddInvItemToInvCart", param);
                  ae.add("error" + (++i), new ActionError("error.simpleGenericError", errorMess));
                }
            }
            quantityS[ii] = "";
        }

        if (ae.size() > 0) {
          return ae;
        }

        if (newItems.isEmpty()) {
        	String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.enterQty",null);
            ae.add("error", new ActionError("error.simpleGenericError",errorMess));
            return ae;
        }

        ValidateActionMessage am = ShopTool.addItemsToCart(request, cartD, newItems);
        if (am.hasErrors()) {
            return am.getActionErrors();
        }
        if (am.hasWarnings()) {
            cartD.addWarningMessages(am.getWarnings());
        }

        if (cartD.getItemsQty() > 0) {
            try {

                if (!appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.OVERRIDE_SHOPPING_RESTRICTION)) {
                    //cartD.reviseQuantities();
                	int siteId = appUser.getSite().getSiteId();
                	log.info("addToInventoryCart() method: cartD = " + cartD);
            		ShoppingCartLogic.reviseShoppingCartQuantities(cartD, orderEjb, siteId, newItems);
                }
                boolean generateInvAuditEntry = false;
                shoppingServEjb.saveInventoryShoppingCart(cartD, generateInvAuditEntry);
            } catch (Exception e) {
                //String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.noItemsSelected",null);
                ae.add("error", new ActionError("error.simpleGenericError", e.getMessage()));
                return ae;
            }
        } else {
            String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.noItemsSelected", null);
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }

        ae = ShopTool.reloadInvShoppingCart(shoppingServEjb,
                request.getSession(),
                null,
                appUser.getSite(),
                appUser.getUser(),
                appUser.getUserStore().getStoreType().getValue());

        ShoppingCartData newShoppingCart = ShopTool.getCurrentInventoryCart(session);
        newShoppingCart.setItemMessages(new ArrayList());
        newShoppingCart.addItemMessages(cartD.getItemMessages());
        newShoppingCart.addWarningMessages(cartD.getWarningMessages());
        session.setAttribute(Constants.INVENTORY_SHOPPING_CART,newShoppingCart);

        for(int m=0; m<newShoppingCart.getItemMessages().size(); m++){

        	ShoppingCartData.CartItemInfo cii =
                (ShoppingCartData.CartItemInfo) newShoppingCart.getItemMessages().get(m);

        	ArrayList al = cii.getI18nItemMessageAL();
        	String key = (String) al.get(0);

        	Object[]  params = null;
        	if(al.size()>1) {
        		params = new Object[6];
        		for(int ii=0; ii<al.size()-1; ii++) {
        			params[ii] = (Object)al.get(ii+1);
        		}
        	}


        	String errorMess = ClwI18nUtil.getMessage(request,key,params);


            ae.add("error",
                    new ActionError("error.simpleGenericError",errorMess));
        }

        clearQuantities(pForm);

        return ae;
    }

    public static ActionErrors itemToInvCart(HttpServletRequest request, UserShopForm pForm) throws Exception {


        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
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

        Order orderEjb;
        try {
        	orderEjb = factory.getOrderAPI();
        } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
            ae.add("error", new ActionError("error.systemError", "No order Ejb pointer"));
            return ae;
        }

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        if (appUser == null) {
            ae.add("error", new ActionError("error.systemError", "No session user found"));
            return ae;
        }

        StoreData store = pForm.getAppUser().getUserStore();
        if (store == null) {
            ae.add("error", new ActionError("error.systemError", "No store information was loaded"));
            return ae;
        }

        PropertyData storeTypeProperty = store.getStoreType();
        if (storeTypeProperty == null) {
            ae.add("error", new ActionError("error.systemError", "No store type information was loaded"));
            return ae;
        }

         if(!ShopTool.hasInventoryCartAccessOpen(request)) {
            String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.scheduleOrderNoLongerAvailable",null);
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }

        ShoppingCartData cartD = ShopTool.getCurrentInventoryCart(session);

        if (cartD == null) {
            cartD = new ShoppingCartData();
            cartD.setUser(appUser.getUser());
            cartD.setSite(appUser.getSite());
        }

        ShoppingCartItemDataVector newItems = new ShoppingCartItemDataVector();
        List<String> msgList = new ArrayList<String>();
        String quantityS = pForm.getQuantityDetail();
        int quantity = 0;
        if (quantityS != null && quantityS.trim().length() > 0) {
            try {
                quantity = Integer.parseInt(quantityS);
                if(quantity<0){
                	msgList.add(pForm.getItemDetail().getActualSkuNum());
                }
            } catch (NumberFormatException exc) {
            	msgList.add(pForm.getItemDetail().getActualSkuNum());
            }
        } else {
            String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.enterQty", null);
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }

        if(msgList.size()>0 && !msgList.isEmpty()){
        	Object[] param = new Object[1];
        	param[0] = Utility.toCommaSting(msgList);
            String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.invalidQtyMessage",param);
            //pForm.setInvalidQtyMessage(errorMess);
            ae.add("error",
                    new ActionError("error.simpleGenericError",errorMess));
        	return ae;
        }

        if (quantity == 0) {
            String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.enterQty", null);
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }

        int itemId = pForm.getItemId();
        List cartItems = pForm.getCartItems();
        ShoppingCartItemData shoppingCartItem = null;
        if (cartItems != null) {
            for (int ii = 0; ii < cartItems.size(); ii++) {
                ShoppingCartItemData sciD = (ShoppingCartItemData) cartItems.get(ii);
                if (sciD.getProduct().getProductId() == itemId) {
                    shoppingCartItem = sciD;
                    break;
                }
            }
        }


        if (shoppingCartItem == null) {

            List<Integer> items = new LinkedList<Integer>();
            items.add(new Integer(itemId));

            Integer catalogIdI = (Integer) session.getAttribute(Constants.CATALOG_ID);

            if (catalogIdI == null) {
                ae.add("error", new ActionError("error.systemError", "No " + Constants.CATALOG_ID + " session attrbute found"));
                return ae;
            }

            int catalogId = catalogIdI.intValue();
            Integer contractIdI = (Integer) session.getAttribute(Constants.CONTRACT_ID);
            int contractId = 0;
            if (catalogIdI != null) {
                contractId = contractIdI.intValue();
            }


            ShoppingCartItemDataVector itemV = null;

            try {
                //Jd begin
                if (!"jd".equals(ClwCustomizer.getStoreDir())) {
                    //Jd end
                    itemV = shoppingServEjb.prepareShoppingItems(storeTypeProperty.getValue(), appUser.getSite(),
                            catalogId, contractId, items,
                             SessionTool.getCategoryToCostCenterView(request.getSession(),appUser.getSite().getSiteId()));
                    //Jd begin
                } else {
                    itemV = shoppingServEjb.prepareJdShoppingItems(pForm.getPriceCode(),
                            catalogId, contractId, items,SessionTool.getCategoryToCostCenterView(session, appUser.getSite().getSiteId()));
                }
                //Jd end
            } catch (RemoteException exc) {
                exc.printStackTrace();
                ae.add("error", new ActionError("error.systemError", "Can't prepare item data. Item id = " + itemId));
                return ae;
            }

            if (itemV.size() == 0) {
                ae.add("error", new ActionError("error.systemError", "Can't prepare item data. Item id = " + itemId));
                return ae;
            }

            shoppingCartItem = (ShoppingCartItemData) itemV.get(0);
        }

        pForm.setItemDeatail(shoppingCartItem);

        ShoppingCartItemData sciD = shoppingCartItem.copy();
        sciD.setQuantity(quantity);
        cartD.setItemMessages(new ArrayList());
        cartD.clearNewItems();
        if (!sciD.getIsaInventoryItem()) {

            newItems.add(sciD);

            ValidateActionMessage am = ShopTool.addItemsToCart(request, cartD, newItems);
            if (am.hasErrors()) {
                return am.getActionErrors();
            }
            if (am.hasWarnings()) {
                cartD.addWarningMessages(am.getWarnings());
            }

            if (!appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.OVERRIDE_SHOPPING_RESTRICTION)) {
                //cartD.reviseQuantities();
            	int siteId = appUser.getSite().getSiteId();
            	log.info("itemToInvCart() method: cartD = " + cartD);
        		ShoppingCartLogic.reviseShoppingCartQuantities(cartD, orderEjb, siteId, newItems);
            }

            boolean generateInvAuditEntry = false;
            shoppingServEjb.saveInventoryShoppingCart(cartD, generateInvAuditEntry);
            pForm.setQuantityDetail("");
        } else {
          Object[] param = new Object[1];
          param[0] = sciD.getActualSkuNum();
          String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.attemptToAddInvItemToInvCart", param);
          ae.add("error", new ActionError("error.simpleGenericError", errorMess));

        }
        if (ae.size() > 0) {
          return ae;
        }else{
        	pForm.setConfirmMessage(ClwI18nUtil.getMessage(request,
                    "shoppingCart.text.actionMessage.itemAdded", null));
        }
        ae = ShopTool.reloadInvShoppingCart(shoppingServEjb, session, null, appUser.getSite(), appUser.getUser(), storeTypeProperty.getValue());

        ShoppingCartData newShoppingCart = ShopTool.getCurrentInventoryCart(session);
        newShoppingCart.setItemMessages(new ArrayList());
        newShoppingCart.addItemMessages(cartD.getItemMessages());
        newShoppingCart.addWarningMessages(cartD.getWarningMessages());
        session.setAttribute(Constants.INVENTORY_SHOPPING_CART,newShoppingCart);

        for(int m=0; m<newShoppingCart.getItemMessages().size(); m++){

        	//if(newShoppingCart.getItem(m).getItemId() == sciD.getItemId()){
        	ShoppingCartData.CartItemInfo cii =
                (ShoppingCartData.CartItemInfo) newShoppingCart.getItemMessages().get(m);

        	ArrayList al = cii.getI18nItemMessageAL();
        	String key = (String) al.get(0);

        	Object[]  params = null;
        	if(al.size()>1) {
        		params = new Object[6];
        		for(int ii=0; ii<al.size()-1; ii++) {
        			params[ii] = (Object)al.get(ii+1);
        		}
        	}


        	String errorMess = ClwI18nUtil.getMessage(request,key,params);


            ae.add("error",
                    new ActionError("error.simpleGenericError",errorMess));
        	}


        return ae;
    }

    public static ActionErrors openCategory(HttpServletRequest request, UserShopForm theForm) throws Exception {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();

        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        if (factory == null) {
            ae.add("error", new ActionError("error.systemError", "No Ejb access"));
            return ae;
        }

        CatalogInformation catalogEjb = factory.getCatalogInformationAPI();
        ShoppingServices shoppingServEjb = factory.getShoppingServicesAPI();

        Integer catalogIdI = (Integer) session.getAttribute(Constants.CATALOG_ID);
        if (catalogIdI == null) {
            ae.add("error", new ActionError("error.systemError", "No " + Constants.CATALOG_ID + " session attrbute found"));
            return ae;
        }

        String categoryKey = request.getParameter("categoryKey");
        if (Utility.isSet(categoryKey)) {

            ArrayList<CategoryInfoView> path= new ArrayList<CategoryInfoView>();
            PairViewVector navInfo = new PairViewVector();
            if (theForm.getCatalogMenu() == null){// assume following method has been called if menu loaded
	            ae = initCategoryShopping(request, theForm);
            }

            pupulateCategoryActivePath(theForm.getCatalogMenu(), categoryKey, path);
            pupulateShopNavigationInfo(path,theForm.getCatalogMenu(),navInfo);
            refreshMenuStatus(theForm.getCatalogMenu(), (ArrayList) path.clone());

            theForm.setCategoryPath(path);
            theForm.setShopNavigatorInfo(navInfo);

            Integer categoryKeyInt = Integer.parseInt(categoryKey);
            IdVector availableCategoryIds = Utility.toIdVector(theForm.getContractCategoryIds());

            ProductDataVector prods = new ProductDataVector();
            int siteId = theForm.getAppUser().getSite().getSiteId();
            int storeId = theForm.getAppUser().getUserStore().getStoreId();
            int storeCatalogId = catalogEjb.getStoreCatalogId(storeId);

            if (availableCategoryIds.contains(categoryKeyInt)) {
                if (siteId > 0) {
                    prods = shoppingServEjb.getCategoryChildProducts(storeCatalogId, siteId,
                            categoryKeyInt,
                            ShopTool.createShoppingItemRequest(request),
                            SessionTool.getCategoryToCostCenterView(session, siteId));
                } else {
                    prods = shoppingServEjb.getCategoryChildProducts(storeCatalogId, 0,
                            categoryKeyInt,
                            ShopTool.createShoppingItemRequest(request),
                            SessionTool.getCategoryToCostCenterView(session, siteId));
                }
            }            

            ProductDataVector products = getProductInfo(catalogEjb, prods, siteId,
                            SessionTool.getCategoryToCostCenterView(session, siteId));

            StoreData store = theForm.getAppUser().getUserStore();
            PropertyData storeTypeProperty = store.getStoreType();
            if(theForm.getOrderBy()==0){
            	theForm.setOrderBy(Constants.ORDER_BY_NAME);
            }

            products = sortProducts(storeTypeProperty.getValue(), products, theForm.getOrderBy());

            theForm.setProductList(products);
            theForm.setCategoryKey(categoryKey);
            ae = prepareCartItems(request, theForm);
            if(ae.size()>0){
                return ae;
            }
        }

        SimplePager pager;
        if (RefCodeNames.SHOP_UI_TYPE.B2C.equals(ShopTool.getShopUIType(request, RefCodeNames.SHOP_UI_TYPE.B2C))) {
            pager = new SimplePager(theForm.getCartItems());
        } else {
            pager = new SimplePager(theForm.getCartItems(), theForm.getCartItems().size(), 1);
        }

        theForm.setPager(pager);
        int pageIndex = 0;

        String pageIndexStr = request.getParameter("pageIndex");
        if (Utility.isSet(pageIndexStr) && (pageIndexStr.equalsIgnoreCase("all") || pageIndexStr.equals("-1"))) {
            pageIndex = -1;
        }

        ae = preparePageItems(request,theForm,pageIndex);

        theForm.setItemIds(new int[0]);
        theForm.setQuantity(new String[0]);

        return ae;
    }


    private static void pupulateShopNavigationInfo(ArrayList activePath, MenuItemView menu, PairViewVector navInfo) {

    	CategoryInfoView cinfo = new CategoryInfoView(menu.getKey(),menu.getName(),menu.getLink());
    	for(int i=0; i<activePath.size(); i++){
    		CategoryInfoView thisinfo = (CategoryInfoView)activePath.get(i);
	    	if (thisinfo.getCategoryKey().equals(cinfo.getCategoryKey())) {
	    		//check since there can be 2 categories with the same name
	            navInfo.add(new PairView(menu.getName(), menu.getLink()));
	        }
    	}

        if (menu.getSubItems() != null && !menu.getSubItems().isEmpty()) {
            for (int i = 0; i < menu.getSubItems().size(); i++) {
                pupulateShopNavigationInfo(activePath, (MenuItemView) menu.getSubItems().get(i), navInfo);
            }
        }
    }


    private static ProductDataVector getProductInfoOld(CatalogInformation catalogInformationAPI, ProductDataVector products, int siteId, AccCategoryToCostCenterView pCategToCostCenterView) throws Exception {

        log.info("getProductInfoOld()=>Begin");
    	ProductDataVector vProducts = new ProductDataVector();
        IdVector groupIds = new IdVector();
        HashMap<Integer, String> imgMap  = new HashMap<Integer, String>();//For loading images of item to the group
        HashMap<Integer, String> dedMap  = new HashMap<Integer, String>();//For loading ded of item to the group
        HashMap<Integer, String> specMap = new HashMap<Integer, String>();//For loading spec of item to the group
        HashMap<Integer, String> msdsMap = new HashMap<Integer, String>();//For loading msds of item to the group
        HashMap<Integer, String> descMap = new HashMap<Integer, String>();//For loading desciption of item to the group
        HashMap<Integer, ItemMappingDataVector> greenCertMap = new HashMap<Integer, ItemMappingDataVector>();//For loading green certifier of item to the group
        HashMap<Integer, CatalogCategoryDataVector> categMap = new HashMap<Integer, CatalogCategoryDataVector>();//For loading category of item to the group

        if (products != null && !products.isEmpty()) {
            Iterator it = products.iterator();
            while (it.hasNext()) {
                ProductData product = (ProductData) it.next();
                if (product.hasItemGroup()) {

                    Integer itemGroupIdI = new Integer(product.getItemGroupId());
                    String imgPath = (Utility.isSet(product.getThumbnail()))? product.getThumbnail() : 
                    				((Utility.isSet(product.getImage())) ? product.getImage() : null);
                    if (!groupIds.contains(itemGroupIdI)) {
                        groupIds.add(itemGroupIdI);
                    }

                    if(!descMap.containsKey(itemGroupIdI) &&  Utility.isSet(imgPath)){
                       descMap.put(itemGroupIdI,product.getLongDesc());
                    }

                    if(!imgMap.containsKey(itemGroupIdI) &&  Utility.isSet(imgPath)){
                       imgMap.put(itemGroupIdI,imgPath);
                    }

                    if(!dedMap.containsKey(itemGroupIdI) &&  Utility.isSet(imgPath)){
                       dedMap.put(itemGroupIdI,product.getDed());
                    }

                    if(!specMap.containsKey(itemGroupIdI) &&  Utility.isSet(imgPath)){
                       specMap.put(itemGroupIdI,product.getSpec());
                    }

                    if(!msdsMap.containsKey(itemGroupIdI) &&  Utility.isSet(imgPath)){
                       msdsMap.put(itemGroupIdI,product.getMsds());
                    }
                    if(!greenCertMap.containsKey(itemGroupIdI) &&  product.getCertifiedCompanies() != null){
                        greenCertMap.put(itemGroupIdI,product.getCertifiedCompanies());
                     }

                    if (!categMap.containsKey(itemGroupIdI) && product.getCatalogCategories() != null) {
                        categMap.put(itemGroupIdI, product.getCatalogCategories());
                    }

                } else {
                    vProducts.add(product);
                }
            }

            try {
                ProductDataVector groups = catalogInformationAPI.getProductCollection(groupIds,siteId, pCategToCostCenterView);
                Iterator groupIt = groups.iterator();

                while(groupIt.hasNext()) {
                    ProductData group = ((ProductData) groupIt.next());
                    // If the group has no image,spec,ded,msds or long description then it is necessary
                    // to load the first value from children's items
                    if(!Utility.isSet(group.getImage())){

                        Integer key = group.getItemData().getItemId();

                        group.setImage(imgMap.get(key));
                        group.setMsds(msdsMap.get(key));
                        group.setDed(dedMap.get(key));
                        group.setSpec(specMap.get(key));
                        group.setLongDesc(descMap.get(key));
                        group.setCatalogCategories(categMap.get(key));
                        group.setCertifiedCompanies(greenCertMap.get(key));
                    }
                  vProducts.add(group);
                }

            } catch (DataNotFoundException e) {
                e.printStackTrace();
            }
        }

        return vProducts;
    }
    public static ProductDataVector getProductInfo(CatalogInformation catalogInformationAPI, ProductDataVector products, int siteId, AccCategoryToCostCenterView pCategToCostCenterView) throws Exception {
       
    	ProductDataVector sortedProducts = UserShopLogic.sortProductsByItemGroupIdSize(products, Constants.ORDER_DIRECTION_ASC);

        log.info("getProductInfo()=>Begin");
    	ProductDataVector vProducts = new ProductDataVector();
        IdVector groupIds = new IdVector();
        HashMap<Integer, String> imgMap  = new HashMap<Integer, String>();//For loading images of item to the group
        HashMap<Integer, String> thumbMap  = new HashMap<Integer, String>();//For loading images of item to the group
        HashMap<Integer, String> dedMap  = new HashMap<Integer, String>();//For loading ded of item to the group
        HashMap<Integer, String> specMap = new HashMap<Integer, String>();//For loading spec of item to the group
        HashMap<Integer, String> msdsMap = new HashMap<Integer, String>();//For loading msds of item to the group
        HashMap<Integer, String> descMap = new HashMap<Integer, String>();//For loading desciption of item to the group
        HashMap<Integer, ItemMappingDataVector> greenCertMap = new HashMap<Integer, ItemMappingDataVector>();//For loading green certifier of item to the group
        HashMap<Integer, CatalogCategoryDataVector> categMap = new HashMap<Integer, CatalogCategoryDataVector>();//For loading category of item to the group
        HashMap<Integer, PairView> costCenterMap = new HashMap<Integer, PairView>();//For loading costCenter info of item to the group

//        if (products != null && !products.isEmpty()) {
//            Iterator it = products.iterator();
        if (sortedProducts != null && !sortedProducts.isEmpty()) {
            Iterator it = sortedProducts.iterator();
            while (it.hasNext()) {
                ProductData product = (ProductData) it.next();
                if (product.hasItemGroup()) {

                    Integer itemGroupIdI = new Integer(product.getItemGroupId());
//                     String imgPath = (Utility.isSet(product.getThumbnail()))? product.getThumbnail() : 
//                    				((Utility.isSet(product.getImage())) ? product.getImage() : null);
                    if (!groupIds.contains(itemGroupIdI)) {
                        groupIds.add(itemGroupIdI);
                    }

                    String val = product.getImage();
                    if(!imgMap.containsKey(itemGroupIdI) &&  Utility.isSet(val)){
                       imgMap.put(itemGroupIdI,val);
                       if (Utility.isSet(product.getLongDesc())) { descMap.put(itemGroupIdI, product.getLongDesc()) ;}
                       if (Utility.isSet(product.getThumbnail())) { thumbMap.put(itemGroupIdI, product.getThumbnail()) ;} 
                       if (Utility.isSet(product.getDed())) { dedMap.put(itemGroupIdI, product.getDed()) ;}
                       if (Utility.isSet(product.getSpec())) { specMap.put(itemGroupIdI, product.getSpec()); } 
                       if (Utility.isSet(product.getMsds())) { msdsMap.put(itemGroupIdI, product.getMsds()) ;} 
                       if (Utility.isSet(product.getCertifiedCompanies())) { greenCertMap.put(itemGroupIdI,product.getCertifiedCompanies()) ;}
                       if (Utility.isSet(product.getCostCenterName())) { costCenterMap.put(itemGroupIdI, new PairView(product.getCostCenterId(),product.getCostCenterName()) ) ;} 
                    }
                    val = product.getLongDesc();
                    if(!descMap.containsKey(itemGroupIdI) &&  Utility.isSet(val)){
                       descMap.put(itemGroupIdI,val);
                    }
                    val = product.getThumbnail();
                    if(!thumbMap.containsKey(itemGroupIdI) &&  Utility.isSet(val)){
                       thumbMap.put(itemGroupIdI,val);
                    }
                    val= product.getDed();
                    if(!dedMap.containsKey(itemGroupIdI) &&  Utility.isSet(val)){
                       dedMap.put(itemGroupIdI,val);
                    }
                    val= product.getSpec();
                    if(!specMap.containsKey(itemGroupIdI) &&  Utility.isSet(val)){
                       specMap.put(itemGroupIdI,val);
                    }
                    val= product.getMsds();
                    if(!msdsMap.containsKey(itemGroupIdI) &&  Utility.isSet(val)){
                       msdsMap.put(itemGroupIdI,val);
                    }
                    if(!greenCertMap.containsKey(itemGroupIdI) &&  Utility.isSet(product.getCertifiedCompanies())){
                        greenCertMap.put(itemGroupIdI,product.getCertifiedCompanies());
                     }

                    if (!categMap.containsKey(itemGroupIdI) && product.getCatalogCategories() != null) {
                        categMap.put(itemGroupIdI, product.getCatalogCategories());
                    }
                    val= product.getCostCenterName();
                    if(!costCenterMap.containsKey(itemGroupIdI) &&  Utility.isSet(val)){
                       costCenterMap.put(itemGroupIdI,new PairView(new Integer(product.getCostCenterId()),val));
                    }
                    //log.info("getProductInfo() ======>itemGroupIdI = "+itemGroupIdI+", itemId ="+ product.getItemData().getItemId()  + ", imgPath = " + imgPath + ", CertifiedCompanies=" + ((product.getCertifiedCompanies()!=null)? product.getCertifiedCompanies().size(): "NULL"));

                } else {
                    vProducts.add(product);
                }
            }
            //log.info("getProductInfo() ======> imgMap : "+imgMap.toString());
            //log.info("getProductInfo() ======> descMap.size() : "+descMap.size());
            //log.info("getProductInfo() ======> greenCertMap : "+greenCertMap.toString());

            try {
                ProductDataVector groups = catalogInformationAPI.getProductCollection(groupIds,siteId, pCategToCostCenterView);
                Iterator groupIt = groups.iterator();

                while(groupIt.hasNext()) {
                    ProductData group = ((ProductData) groupIt.next());
                    // If the group has no image,spec,ded,msds or long description then it is necessary
                    // to load the first value from children's items
                    if(!Utility.isSet(group.getImage() )){
                        Integer key = group.getItemData().getItemId();
                        //log.info("getProductInfo() ======> loading the first value from children's items. KEY="+ key);

                        group.setImage(imgMap.get(key));
                        group.setThumbnail(thumbMap.get(key));
                        group.setMsds(msdsMap.get(key));
                        group.setDed(dedMap.get(key));
                        group.setSpec(specMap.get(key));
                        group.setLongDesc(descMap.get(key));
                        group.setCatalogCategories(categMap.get(key));
                        group.setCertifiedCompanies(greenCertMap.get(key));
                        if (costCenterMap.get(key) != null) {
                        group.setCostCenterId(((Integer)(((PairView)costCenterMap.get(key)).getObject1())).intValue());
                        group.setCostCenterName((String)(((PairView)costCenterMap.get(key)).getObject2()));
                        }
                    }
                    
                  vProducts.add(group);
                }

            } catch (DataNotFoundException e) {
                e.printStackTrace();
            }
        }

        return vProducts;
    }

    public static ActionErrors msItem(HttpServletRequest request, UserShopForm pForm) throws Exception {

        ActionErrors ae = item(request, pForm);

        ShoppingCartItemData item = pForm.getItemDetail();
        if (item != null) {
        	pForm.setShopMethod(Constants.SHOP_BY_CATEGORY);
            PairViewVector navInfo = createShopNavigationInfo();
            populateShopNavigationInfo(navInfo, item.getProduct());
            pForm.setShopNavigatorInfo(navInfo);
            refreshMenuStatus(pForm.getCatalogMenu(), new ArrayList());

            ShoppingCartItemDataVector cartItems = new ShoppingCartItemDataVector();
            cartItems.add(item);
            pForm.setCartItems(cartItems);

            pForm.setItemIds(new int[0]);
            pForm.setQuantity(new String[0]);
            pForm.setPageSize(pForm.getCartItems().size());

        }

        return ae;
    }

    private static void populateShopNavigationInfo(PairViewVector navInfo, ProductData product) {

        if (product != null && navInfo != null) {
            CatalogCategoryDataVector path = product.getCatalogCategories();
            if (path != null && !path.isEmpty()) {
                for (int i = path.size() - 1; i >= 0; i--) {
                    navInfo.add(new PairView(((CatalogCategoryData) path.get(i)).getCatalogCategoryShortDesc(),
                            "../store/shop.do?action=openCategory&categoryKey=" + ((CatalogCategoryData) path.get(i)).getCatalogCategoryId()));
                }
            }
            navInfo.add(new PairView(product.getCatalogProductShortDesc(), null));
        }
    }

    public static PairViewVector createShopNavigationInfo() {

       PairViewVector navInfo = new PairViewVector();
       navInfo.add(new PairView(Constants.ROOT,null));

       return navInfo;
    }

    public static MenuItemView getMenuItem(MenuItemView pCategoryMenu, String pPathPointKey) {

        if (pPathPointKey.equals(pCategoryMenu.getKey())) {
            return pCategoryMenu;
        } else {
            if (pCategoryMenu.getSubItems() != null && !pCategoryMenu.getSubItems().isEmpty()) {
                for (int i = 0; i < pCategoryMenu.getSubItems().size(); i++) {
                    MenuItemView pathMenu = getMenuItem((MenuItemView) pCategoryMenu.getSubItems().get(i), pPathPointKey);
                    if (pathMenu != null) {
                        return pathMenu;
                    }
                }
            }
        }

        return null;

    }

    public static void refreshMenuStatus(MenuItemView categoryMenu, ArrayList activePath) {
        if (activePath.contains(categoryMenu.getName())) {
            categoryMenu.setDisplayStatus(MenuItemView.DISPLAY_STATUS.OPEN);
        } else {
            categoryMenu.setDisplayStatus(MenuItemView.DISPLAY_STATUS.CLOSE);
        }
        if (categoryMenu.getSubItems() != null && !categoryMenu.getSubItems().isEmpty()) {
            for (int i = 0; i < categoryMenu.getSubItems().size(); i++) {
                refreshMenuStatus((MenuItemView) categoryMenu.getSubItems().get(i), activePath);
            }
        }
    }

    private static boolean pupulateCategoryActivePath(MenuItemView categoryMenu, String categoryKey, List path) {

    	CategoryInfoView cinfo = new CategoryInfoView(categoryMenu.getKey(),categoryMenu.getName(),categoryMenu.getLink());

    	path.add(cinfo);
    	if (categoryKey.equals(categoryMenu.getKey())) {
            return true;
        }
    	if (categoryMenu.getSubItems() != null && !categoryMenu.getSubItems().isEmpty()) {
    		for (int i = 0; i < categoryMenu.getSubItems().size(); i++) {
                if (! pupulateCategoryActivePath((MenuItemView) categoryMenu.getSubItems().get(i), categoryKey, path)) {

                } else {
                    return true;
                }
            }
    	}
    	path.remove(path.size() - 1);
    	return false;
    }


    /**
     * Description of the Method
     *
     * @param request Description of the Parameter
     * @param pForm   Description of the Parameter
     * @return Description of the Return Value
     * @throws Exception Description of the Exception
     */
    public static ActionErrors initCategoryShopping(HttpServletRequest request, UserShopForm pForm) throws Exception {

        ActionErrors ae = new ActionErrors();

        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);

        if (factory == null) {
            ae.add("error", new ActionError("error.systemError", "No Ejb access"));
            return ae;
        }

        CatalogInformation catalogInfEjb = null;
        try {
            catalogInfEjb = factory.getCatalogInformationAPI();
        } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
            exc.printStackTrace();
            ae.add("error", new ActionError("error.systemError", "No catalog Ejb pointer"));
            return ae;
        }

        ContractInformation contractInfEjb = null;
        try {
            contractInfEjb = factory.getContractInformationAPI();
        } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
            ae.add("error", new ActionError("error.systemError", "No contract Ejb pointer"));
            return ae;
        }

        ShoppingServices shoppingServEjb = null;
        try {
            shoppingServEjb = factory.getShoppingServicesAPI();
        } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
            ae.add("error", new ActionError("error.systemError", "No shopping services Ejb pointer"));
            return ae;
        }

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        if (appUser == null) {
            ae.add("error", new ActionError("error.systemError", "No session " + Constants.APP_USER + " object found "));
            return ae;
        }

        ae = init(request, pForm, catalogInfEjb, contractInfEjb, shoppingServEjb);
        if (ae.size() > 0) {
            return ae;
        }

        Integer catalogId = (Integer) session.getAttribute(Constants.CATALOG_ID);
        if (catalogId == null) {
            ae.add("error", new ActionError("error.systemError", "No " + Constants.CATALOG_ID + " session attrbute found"));
            return ae;
        }        

        //initialize category navigation;
        String patternLink = "../store/shop.do?action=openCategory&categoryKey={0}";
        int[] contractCategoryIds = pForm.getContractCategoryIds();

        MenuItemView root = catalogInfEjb.getCatalogMenu(catalogId, Utility.toIdVector(contractCategoryIds), patternLink);
        pForm.setCatalogMenu(root);

        OrderGuideDataVector userOrderGuideDV;
        OrderGuideDataVector templateOrderGuideDV;
        OrderGuideDataVector customOrderGuideDV;

        int userid = appUser.getUserId();
        if (appUser.getSite().isSetupForSharedOrderGuides()) {
            // This will force the system to get all available order
            // schedules and order guides.
            userid = 0;
        }

        try {
            userOrderGuideDV = shoppingServEjb.getUserOrderGuides(userid, catalogId, appUser.getSite().getSiteId());
            templateOrderGuideDV = shoppingServEjb.getTemplateOrderGuides(catalogId, appUser.getSite().getSiteId());
            customOrderGuideDV = shoppingServEjb.getCustomOrderGuides(appUser.getSite().getAccountId(), appUser.getSite().getSiteId());
        } catch (RemoteException exc) {
            exc.printStackTrace();
            ae.add("error", new ActionError("error.systemError", exc.getMessage()));
            return ae;
        }

        pForm.setItemIds(new int[0]);
        pForm.setQuantity(new String[0]);
        pForm.setShopMethod(Constants.SHOP_BY_CATEGORY);
        pForm.setUserOrderGuides(userOrderGuideDV);
        pForm.setTemplateOrderGuides(templateOrderGuideDV);
        pForm.setCustomOrderGuides(customOrderGuideDV);

        return ae;
    }

    public static ActionErrors itemGroup(HttpServletRequest request, UserShopForm pForm) throws Exception {

        ActionErrors ae = new ActionErrors();
        String itemIdS = request.getParameter("itemId");

        if (itemIdS == null || itemIdS.trim().length() == 0) {
            ae.add("error", new ActionError("error.systemError", "No item id found"));
            return ae;
        }

        int itemId = 0;
        try {
            itemId = Integer.parseInt(itemIdS);
        } catch (NumberFormatException exc) {
            ae.add("error", new ActionError("error.systemError", "Wrong item id format. Item id = " + itemIdS));
            return ae;
        }


        //try to find item in current list
        pForm.setItemId(itemId);

        List cartItems = pForm.getCartItems();

        ShoppingCartItemData itemGroup = null;
        int cartItemsIndex = -1;
        if (cartItems != null) {
            for (int ii = 0; ii < cartItems.size(); ii++) {
                ShoppingCartItemData sciD = (ShoppingCartItemData) cartItems.get(ii);
                if (sciD.getProduct().getProductId() == itemId) {
                    cartItemsIndex = ii;
                    itemGroup = sciD;
                    break;
                }
            }
         }
        log.info("itemGroup()=====> Name of Group item :"+ ((itemGroup != null && itemGroup.getProduct()!= null) ? itemGroup.getProduct().getShortDesc() : "NULL"));

        if (cartItemsIndex < 0 || itemGroup == null) {
            //try if cached view is a match.  This would indicate that the user went back and then came back to this screen
            if (pForm.getItemGroupCachedView() != null && pForm.getItemGroupCachedView().getProduct().getProductId() == itemId) {
                itemGroup = pForm.getItemGroupCachedView();
            } else {
                String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.pageInfoDoesntMatchServer", null);
                ae.add("error", new ActionError("error.simpleGenericError", errorMess));
                return ae;
            }
        }

        pForm.setItemGroupCachedView(itemGroup);

        HttpSession session = request.getSession();

        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        if (factory == null) {
            ae.add("error", new ActionError("error.systemError", "No Ejb access"));
            return ae;
        }

        ShoppingServices shoppingServEjb;
        try {
            shoppingServEjb = factory.getShoppingServicesAPI();
        } catch (APIServiceAccessException exc) {
            exc.printStackTrace();
            ae.add("error", new ActionError("error.systemError", "No shopping services Ejb access"));
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

        pForm.setGroupedItemView(true);
        ShoppingCartItemDataVector itemV;
        try {
            itemV = shoppingServEjb.prepareShoppingGroupItems(storeTypeProperty.getValue(),
                    appUser.getSite(),
                    itemGroup.getItemId(),
                    ShopTool.createShoppingItemRequest(request));
        } catch (RemoteException exc) {
            exc.printStackTrace();
            ae.add("error", new ActionError("error.systemError", "Can't prepare item data. Item id = " + itemId));
            return ae;
        }

        if (itemV.size() == 0) {
            ae.add("error", new ActionError("error.systemError", "Item group is empty. Item Group id = " + itemId));
            return ae;
        }

        if (pForm.getAppUser().isUserOnContract()) {
            itemV = (ShoppingCartItemDataVector) Utility.filter(itemV, Utility.toIdVector(pForm.getContractItemIds()));
        }

        /// Generation of navigation information for the multi product.
        PairViewVector navInfo = createShopNavigationInfo();
        ShoppingCartItemData firstItemInGroup = (ShoppingCartItemData) itemV.get(0);
        log.info("itemGroup()=====> Name of 1-st item :"+ firstItemInGroup.getProduct().getShortDesc());
        populateShopNavigationInfo(navInfo, firstItemInGroup.getProduct());
        pForm.setShopNavigatorInfo(navInfo);
        log.info("itemGroup()=====> Navigation Info :"+ pForm.getShopNavigatorInfo().toString());

        if (itemGroup!=null && itemGroup.getCategory() != null ){
            String categoryKey = ""+itemGroup.getCategory().getCatalogCategoryId();
	        ArrayList<CategoryInfoView> path = new ArrayList<CategoryInfoView>();
	        pupulateCategoryActivePath(pForm.getCatalogMenu(), categoryKey, path);
	        pForm.setCategoryPath(path);
	        log.info("itemGroup()=====> Active Path :"+ pForm.getCategoryPath().toString());
        }
        //sort items by size
        ShoppingCartItemDataVector res = DisplayListSort.sortMultiItems(itemV, "size");

        cartItems = res != null ? res : itemV;

        ArrayList<ProductData> productList = new ArrayList<ProductData>();
        for (Object oCartItem : cartItems) {
            productList.add(((ShoppingCartItemData) oCartItem).getProduct());
        }
        
        pForm.setItemDeatail(itemGroup);
        pForm.setCartItems(cartItems);
        pForm.setProductList(productList);

        pForm.setItemIds(new int[0]);
        pForm.setQuantityDetail("");
        pForm.setQuantity(new String[0]);
        pForm.setOffset(0);
        pForm.setPageSize(pForm.getProductListSize());

        return ae;

    }

    public static ActionErrors getPage(HttpServletRequest request, UserShopForm theForm) throws Exception {
        ActionErrors ae = new ActionErrors();
        int pageIndex = 0;
        if (theForm.getPager() != null) {

            String pageIndexStr = request.getParameter("pageIndex");
            if (pageIndexStr.equalsIgnoreCase("all")) {
                pageIndexStr = "-1";
            }
            if (Utility.isSet(pageIndexStr)) {
                try {
                    pageIndex = Integer.parseInt(pageIndexStr);
                } catch (NumberFormatException e) {

                    ae.add("error", new ActionError("error.systemError", "Bad page number format"));
                    return ae;
                }
            } else {
                ae.add("error", new ActionError("error.systemError", "No page number parameter found"));
                return ae;
            }

            int numPages = theForm.getPager().getMountedList().size() / theForm.getPager().getMaxItems()
                    + (theForm.getPager().getMountedList().size() % theForm.getPager().getMaxItems() != 0 ? 1 : 0);

            if (pageIndex + 1 > numPages || pageIndex < -1) {
                ae.add("error", new ActionError("error.systemError", "Requested page is out of the product list"));
                theForm.setPageIndex(--pageIndex);
                return ae;
            }
        } else {
            ae.add("error", new ActionError("error.systemError", "'Pager' has not been created."));
            return ae;
        }


        ae = preparePageItems(request,theForm,pageIndex);
        return ae;
    }


    public static ActionErrors preparePageItems(HttpServletRequest request, UserShopForm theForm, int pageIndex) throws Exception {

    	ActionErrors ae = new ActionErrors();

        List cartItems = theForm.getPager().getPageItemList(pageIndex);
        List productList = new ProductDataVector();
        ShoppingCartItemDataVector cartItems1 = new ShoppingCartItemDataVector();

        if (cartItems != null) {
            Iterator it = cartItems.iterator();
            while (it.hasNext()) {
            	ShoppingCartItemData sciD = (ShoppingCartItemData) it.next();
                productList.add(sciD.getProduct());
                cartItems1.add(sciD);
            }
        }

        theForm.setProductList(productList);
        theForm.setPageIndex(pageIndex);
        theForm.setCartItems(cartItems1);
        theForm.setPageSize(theForm.getProductListSize());

        //ae = prepareCartItems(request, theForm);

        return ae;

    }



//  ****************************************************************************
    public static ActionErrors updateOrderGuide(HttpServletRequest request, UserShopForm pForm)
    throws Exception
    {
      ActionErrors ae = new ActionErrors();
      int selectedId = pForm.getOrderGuideId();
      pForm.setSelectedShoppingListId("-1"); // set to initial value

      //Get user order guides
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

      CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
      if(appUser==null) {
        ae.add("error",new ActionError("error.systemError","No "+Constants.APP_USER+"session object found"));
        return ae;
      }
      UserData user = appUser.getUser();
      int siteId = appUser.getSite().getBusEntity().getBusEntityId();

      OrderGuide orderGuideEjb = factory.getOrderGuideAPI();
      OrderGuideData orderGuideD = orderGuideEjb.getOrderGuide(selectedId);

      String modName = pForm.getModifiedName();

      if( modName!=null && !(modName.equalsIgnoreCase(pForm.getOrderGuideName())) ){

    	  orderGuideD.setShortDesc(modName);
    	  pForm.setOrderGuideName(modName);

      }


      long[] toDelete = pForm.getSelectBox();

      List currentItems = pForm.getOrderGuideCartItems();
      List<ShoppingCartItemData> removedItems = new ArrayList<ShoppingCartItemData>();

      for(int ii=0; ii<currentItems.size();) {
        ShoppingCartItemData sciD = (ShoppingCartItemData)currentItems.get(ii);
        int orderNum = sciD.getOrderNumber();
        int id = sciD.getProduct().getProductId();
        long code = (long)id*10000+(long)orderNum;
        int jj = 0;
	        for(; jj<toDelete.length; jj++) {
	          if(code==toDelete[jj]) {
	        	currentItems.remove(ii);
	            removedItems.add(sciD);
	            break;
	          }
        }
        if(jj==toDelete.length) ii++;
      }
      pForm.setRemovedItems(removedItems);
      pForm.setOrderGuideCartItems(currentItems);

      ae = recalculateQuantities(request,pForm);
      if(ae.size()>0){
    	  return ae;
      }

      ValidateActionMessage am = ShopTool.validateSpecialPermission(request, pForm.getOrderGuideCartItems());
      if (am.hasErrors()) {
          return am.getActionErrors();
      }

      String name = null ;
      int orderGuideId = 0;
      try {
        orderGuideId = shoppingServEjb.saveUserOrderGuide(orderGuideD,pForm.getOrderGuideCartItems(),appUser.getUser().getUserName());
      } catch(Exception exc) {
        ae.add("error",new ActionError("error.systemError","Can't save order guide to database"));
        return ae;
      }
      pForm.setOrderGuideId(orderGuideId);


      name = pForm.getOrderGuideName();
      if(ae.size()==0) {
        Object[] params = new Object[1];
        params[0] = name;
        pForm.setConfirmMessage(ClwI18nUtil.getMessage(request,
                  "shoppingCart.text.actionMessage.listUpdated", params));
     }

      //ae=init(request,pForm);
      return ae;
    }


//  *****************************************************************************
    public static ActionErrors removeSelected(HttpServletRequest request, UserShopForm pForm)
    {
      ActionErrors ae = new ActionErrors();
      if(ae.size()>0) {
        return ae;
      }
      HttpSession session = request.getSession();
      long[] toDelete = pForm.getSelectBox();

      if(toDelete == null || !(toDelete.length>0) ){
    	  //pForm.setWarningMessage("There are no items selected for deletion");
    	  String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.noItemsForDeletion",null);
      		ae.add("error",
                  new ActionError("error.simpleGenericError",errorMess));

    	  return ae;
      }

      List currentItems = pForm.getOrderGuideCartItems();


      //ShoppingCartData shoppingCartD = (ShoppingCartData) session.getAttribute(Constants.SHOPPING_CART);
      //ShoppingCartItemDataVector cartItems = shoppingCartD.getItems();
      List<ShoppingCartItemData> removedItems = new ArrayList<ShoppingCartItemData>();

      for(int ii=0; ii<currentItems.size();) {
        ShoppingCartItemData sciD = (ShoppingCartItemData)currentItems.get(ii);
        int orderNum = sciD.getOrderNumber();
        int id = sciD.getProduct().getProductId();
        long code = (long)id*10000+(long)orderNum;
        int jj = 0;
        for(; jj<toDelete.length; jj++) {
          if(code==toDelete[jj]) {
        	  currentItems.remove(ii);
            removedItems.add(sciD);
            break;
          }
        }
        if(jj==toDelete.length) ii++;
      }
      pForm.setRemovedItems(removedItems);
      //shoppingCartD.setItems(cartItems);
      pForm.setOrderGuideCartItems(currentItems);
      //ae = saveShoppingCart(session);
      try{
    	  ae = updateOrderGuide(request,pForm);
      }catch(Exception exc) {
          ae.add("error",new ActionError("error.systemError","Can't save order guide to database"));
          return ae;
        }
      String name = pForm.getOrderGuideName();
      if(ae.size()>0) {
        return ae;
      } else {
        if (currentItems.size()>0){
        	Object[] params = new Object[1];
            params[0] = name;

            pForm.setConfirmMessage(ClwI18nUtil.getMessage(request,
                      "shoppingCart.text.actionMessage.itemsDeleted", params));
        }
      }

      return ae;
    }

    public static ActionErrors deleteOrderGuide(HttpServletRequest request, UserShopForm pForm) throws Exception {

    	ActionErrors ae = new ActionErrors();

        if(ae.size()>0) {
          return ae;
        }
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
          ae.add("error",new ActionError("error.systemError","No shopping services Ejb pointer"));
          return ae;
        }
        int orderGuideId = pForm.getOrderGuideId();

        try{
        	orderGuideEjb.removeOrderGuide(orderGuideId);
        }catch(Exception exc) {
            ae.add("error",new ActionError("error.systemError","Can't delete order guide from database"));
            return ae;
        }
        pForm.setOrderGuideId(0);
        String name = pForm.getOrderGuideName();
        if(ae.size()>0) {
          return ae;
        } else {
          	Object[] params = new Object[1];
              params[0] = name;
              pForm.setConfirmMessage(ClwI18nUtil.getMessage(request,
                        "shoppingCart.text.actionMessage.listDeleted", params));

        }
        if (pForm.getUserOrderGuides() != null) {
            Iterator it = pForm.getUserOrderGuides().iterator();
            while (it.hasNext()) {
                OrderGuideData ogd = (OrderGuideData)it.next();
                if (ogd.getOrderGuideId() == orderGuideId) {
                    it.remove();
                    break;
                }
            }
        }
        pForm.setModifiedName(null);
        pForm.setOrderGuideName(null);
        refreshOrderGuideSessionAttr(request, pForm);
        return ae;
    }

    //****************************************************************************
    /**
     *  Description of the Method
     *
     *@param  request        Description of the Parameter
     *@param  pForm          Description of the Parameter
     *@return                Description of the Return Value
     *@exception  Exception  Description of the Exception
     */
    public static ActionErrors integratedSearch(HttpServletRequest request,
            UserShopForm pForm)
            throws Exception {
        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess) session.getAttribute(
                Constants.APIACCESS);

        ShoppingServices shoppingServEjb =
                factory.getShoppingServicesAPI();

        pForm.setCategoryPath(new LinkedList());
        pForm.setCategoryList(new LinkedList());
        pForm.setOffset(0);
        pForm.setNavigationFlag(false);


        CleanwiseUser appUser  = ShopTool.getCurrentUser(request);
        UserData userD  = appUser.getUser();
        int accountId = appUser.getSite().getAccountId();
        int siteId = appUser.getSite().getSiteId();
        String rights = appUser.getRightsForAccount(accountId);
        UserRightsTool userRights = new UserRightsTool(userD, rights);

        StoreData store  = appUser.getUserStore();
        if (store == null) {
            ae.add("error",
                    new ActionError("error.systemError",
                    "No store information was loaded"));

            return ae;
        }
        int storeId = store.getBusEntity().getBusEntityId();
        PropertyData storeTypeProp = store.getStoreType();
        if (storeTypeProp == null) {
            ae.add("error",
                    new ActionError("error.systemError",
                    "No store type information was loaded"));

            return ae;
        }
        String storeType = storeTypeProp.getValue();
        if(!RefCodeNames.STORE_TYPE_CD.DISTRIBUTOR.equals(storeType)
           ) {
            ae.add("error",
                    new ActionError("error.systemError",
                    "This function works for DISTRIBUTOR type store only"));

            return ae;
        }

        Integer catalogIdI =
                (Integer) session.getAttribute(Constants.CATALOG_ID);

        if (catalogIdI == null) {
            ae.add("error",
                    new ActionError("error.systemError",
                    "No " + Constants.CATALOG_ID +
                    " session attrbute found"));

            return ae;
        }
        int catalogId = catalogIdI.intValue();

        Integer contractIdI =
                (Integer) session.getAttribute(Constants.CONTRACT_ID);
        int contractId = 0;
        if (contractIdI != null) {
            contractId = contractIdI.intValue();
        }

        String searchString = pForm.getSearchName();
        if(!Utility.isSet(searchString)) {

            String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.searchFieldIsEmpty",null);
            ae.add("error",
                    new ActionError("error.simpleGenericError",errorMess));
            return ae;
        }

        int fOrderBy = Constants.ORDER_BY_CATEGORY;
        ProductDataVector searchRes = new ProductDataVector();

        try {
            searchRes = shoppingServEjb.integratedSearch(storeType, ShopTool.createShoppingItemRequest(request), searchString,
                                                         SessionTool.getCategoryToCostCenterView(session, siteId));
        } catch (RemoteException exc) {
            exc.printStackTrace();
            ae.add("error", new ActionError("error.systemError", exc.getMessage()));
            return ae;
        }
//
        // prepare multyProduct group items
        CatalogInformation catalogEjb = factory.getCatalogInformationAPI();
        ProductDataVector products = getProductInfo(catalogEjb, searchRes, siteId,
                            SessionTool.getCategoryToCostCenterView(session, siteId));
        // Sort Items
        PropertyData storeTypeProperty = store.getStoreType();
        int orderBy = pForm.getOrderBy();
        if (orderBy == 0) {
            orderBy = Constants.ORDER_BY_NAME;
        }
        products = sortProducts(storeTypeProperty.getValue(), products, orderBy);

        pForm.setProductList(products);
        ae = prepareCartItems(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }
        pForm.setSearchName("");

        SimplePager pager = new SimplePager(pForm.getCartItems());
        pForm.setPager(pager);

        ae = preparePageItems(request,pForm,0);

        pForm.setItemIds(new int[0]);
        pForm.setQuantity(new String[0]);

        PairViewVector pvV = new PairViewVector();
        PairView pv = new PairView();
        pv.setObject1("ROOT");
        pvV.add(pv);

        pv = new PairView();
        Object[] wrkA = new Object[1];
        wrkA[0] = searchString;
        String mess = ClwI18nUtil.getMessage(request,"shoppingMessages.text.searchResults",wrkA);
        pv.setObject1(mess);
        pv.setObject2("javascript:search('"+searchString+"')");
        pvV.add(pv);
        pForm.setShopNavigatorInfo(pvV);
        if(searchRes.size()==0) {
            String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.noResultsFound",null);
            ae.add("warning",
                    new ActionError("error.simpleGenericError",errorMess));
            return ae;

        }

        return ae;
    }

    public static ActionErrors saveUserOrderGuide(HttpServletRequest request,
            UserShopForm pForm) throws Exception {
        return saveUserOrderGuide(request, pForm, false);
    }
            
    //****************************************************************************
    public static ActionErrors saveUserOrderGuide(HttpServletRequest request, UserShopForm pForm,
            boolean newXpedx) throws Exception {

        ActionErrors ae = new ActionErrors();
        pForm.setSelectedShoppingListId("-1"); // reset to initial value

        //Get user order guides
        HttpSession session = request.getSession();

        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        if (factory == null) {
            ae.add("error", new ActionError("error.systemError", "No Ejb access"));
            return ae;
        }

        ShoppingServices shoppingServEjb;
        try {
            shoppingServEjb = factory.getShoppingServicesAPI();
        } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
            ae.add("error", new ActionError("error.systemError", "No shopping services Ejb pointer"));
            return ae;
        }

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        if (appUser == null) {
            ae.add("error", new ActionError("error.systemError", "No " + Constants.APP_USER + "session object found"));
            return ae;
        }

        UserData user = appUser.getUser();
        int siteId = appUser.getSite().getBusEntity().getBusEntityId();

        Integer catalogIdI = (Integer) session.getAttribute(Constants.CATALOG_ID);
        if (catalogIdI == null) {
            ae.add("error", new ActionError("error.systemError", "No " + Constants.CATALOG_ID + " session object found"));
            return ae;
        }

        int catalogId = catalogIdI.intValue();
        OrderGuideDataVector userOrderGuideDV;
        try {
            userOrderGuideDV = shoppingServEjb.getUserOrderGuides(user.getUserId(), catalogId, siteId);
        } catch (RemoteException exc) {
            exc.printStackTrace();
            ae.add("error", new ActionError("error.systemError", "Can't pick up order guides"));
            return ae;
        }

        //Check new order guide name
        String name = pForm.getOrderGuideName();
        if (name == null || name.trim().length() == 0) {
            String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.provideOrderGuideName", null);
            if (newXpedx) {
                errorMess = ClwI18nUtil.getMessage(request, "shop.errors.provideShoppingListName", null);
            }
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }
        name = name.trim();
        name = I18nUtil.getUtf8Str(name);
        pForm.setOrderGuideName(name);

        //Check uniqueness of the name
        if (userOrderGuideDV != null) {
            int ii = 0;
            for (; ii < userOrderGuideDV.size(); ii++) {
                OrderGuideData ogD = (OrderGuideData) userOrderGuideDV.get(ii);
                String ss = ogD.getShortDesc();
                if (ss != null && ss.trim().equals(name)) {
                    break;
                }
            }
            if (ii < userOrderGuideDV.size()) {
                String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.nameExists", null);
                ae.add("error", new ActionError("error.simpleGenericError", errorMess));
                return ae;
            }
        }

        //Criate new order guide;
        OrderGuideData orderGuideD = OrderGuideData.createValue();
        orderGuideD.setShortDesc(name);
        orderGuideD.setCatalogId(catalogIdI.intValue());
        orderGuideD.setBusEntityId(appUser.getSite().getBusEntity().getBusEntityId());
        orderGuideD.setUserId(appUser.getUser().getUserId());
        orderGuideD.setOrderGuideTypeCd(RefCodeNames.ORDER_GUIDE_TYPE_CD.BUYER_ORDER_GUIDE);

        //Pickup items
        ae = recalculate(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }

        if(isEmpty(pForm.getCartItems())){
            String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.noItemsSelected",null);
            ae.add("error",new ActionError("error.simpleGenericError",errorMess));
            return ae;
        }

        ShoppingCartItemDataVector ogItems = ShopTool.addItemsToOgList(null,pForm.getCartItems());

        ValidateActionMessage am = ShopTool.validateSpecialPermission(request, ogItems);
        if (am.hasErrors()) {
            return am.getActionErrors();
        }

        int orderGuideId;
        try {
            orderGuideId = shoppingServEjb.saveUserOrderGuide(orderGuideD, ogItems, appUser.getUser().getUserName());
        } catch (Exception exc) {
            ae.add("error", new ActionError("error.systemError", "Can't save order guide to database"));
            return ae;
        }

        try {
            userOrderGuideDV = shoppingServEjb.getUserOrderGuides(user.getUserId(), catalogId, siteId);
        } catch (RemoteException exc) {
            exc.printStackTrace();
            ae.add("error", new ActionError("error.systemError", "Can't pick up order guides"));
            return ae;
        }

        pForm.setUserOrderGuides(userOrderGuideDV);
        pForm.setOrderGuideId(orderGuideId);
        if (ae.size() == 0) {
            Object[] params = new Object[1];
            params[0] = name;
            pForm.setConfirmMessage(ClwI18nUtil.getMessage(request, "shoppingCart.text.actionMessage.listSaved", params));
        }

        refreshOrderGuideSessionAttr(request, pForm);
        //bug - 5155
        pForm.setOrderGuideName("");
        return ae;
    }

    //****************************************************************************
    public static ActionErrors updateUserOrderGuide(HttpServletRequest request, UserShopForm pForm) throws Exception {

        ActionErrors ae = new ActionErrors();
        int selectedId = Integer.parseInt(pForm.getSelectedShoppingListId());
        pForm.setSelectedShoppingListId("-1"); // set to initial value

        //Get user order guides
        HttpSession session = request.getSession();
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

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        if (appUser == null) {
            ae.add("error", new ActionError("error.systemError", "No " + Constants.APP_USER + "session object found"));
            return ae;
        }

        StoreData store = appUser.getUserStore();
        if (store == null) {
            ae.add("error", new ActionError("error.systemError", "No store information was loaded"));
            return ae;
        }

        PropertyData storeTypeProperty = store.getStoreType();

        if (storeTypeProperty == null) {
            ae.add("error", new ActionError("error.systemError", "No store type information was loaded"));
            return ae;
        }

        UserData user = appUser.getUser();
        int siteId = appUser.getSite().getBusEntity().getBusEntityId();

        Integer catalogIdI = (Integer) session.getAttribute(Constants.CATALOG_ID);
        if (catalogIdI == null) {
            ae.add("error", new ActionError("error.systemError", "No " + Constants.CATALOG_ID + " session object found"));
            return ae;
        }

        //Check  order guide id
        if (selectedId < 0) {
            String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.provideOrderGuideName", null);
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }

        int contractId = 0;
        int catalogId = catalogIdI.intValue();

        Integer contractIdI = (Integer) session.getAttribute(Constants.CONTRACT_ID);
        if (contractIdI != null) {
            contractId = contractIdI.intValue();
        }

        OrderGuideDataVector userOrderGuideDV;
        try {
            userOrderGuideDV = shoppingServEjb.getUserOrderGuides(user.getUserId(), catalogId, siteId);
        } catch (RemoteException exc) {
            exc.printStackTrace();
            ae.add("error", new ActionError("error.systemError", "Can't pick up order guides"));
            return ae;
        }

        //Check uniqueness of the name
        String name = null;
        OrderGuideData myOg = null;
        if (userOrderGuideDV != null) {
            int ii = 0;
            for (; ii < userOrderGuideDV.size(); ii++) {
                OrderGuideData ogD = (OrderGuideData) userOrderGuideDV.get(ii);
                int id = ogD.getOrderGuideId();
                if (id == selectedId) {
                    name = ogD.getShortDesc();
                    myOg = ogD;
                }
            }
        }

        if (name == null || (name.trim().length() == 0)) {
            String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.provideOrderGuideName", null);
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }

        //Criate new order guide;
        ShoppingCartItemDataVector currOgItems;
        try {
            currOgItems = shoppingServEjb.getOrderGuideItems(storeTypeProperty.getValue(),
                    appUser.getSite(),
                    myOg.getOrderGuideId(),
                    ShopTool.createShoppingItemRequest(request),
                    0,SessionTool.getCategoryToCostCenterView(session, appUser.getSite().getSiteId()));
        } catch (RemoteException exc) {
            exc.printStackTrace();
            ae.add("error", new ActionError("error.systemError", "Can't pick up order guide items"));
            return ae;
        }

        //Pickup items
        ae = recalculate(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }

        if(isEmpty(pForm.getCartItems())){
        	String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.enterQty",null);
            ae.add("error",
                    new ActionError("error.simpleGenericError",errorMess));
            return ae;
        }

        ShoppingCartItemDataVector myOgItems = ShopTool.addItemsToOgList(currOgItems,  pForm.getCartItems());

        ValidateActionMessage am = ShopTool.validateSpecialPermission(request, myOgItems);
        if (am.hasErrors()) {
            return am.getActionErrors();
        }

        myOg.setShortDesc(name);

        int orderGuideId;
        try {
            orderGuideId = shoppingServEjb.saveUserOrderGuide(myOg, myOgItems, appUser.getUser().getUserName());
        } catch (Exception exc) {
            ae.add("error", new ActionError("error.systemError", "Can't save order guide to database"));
            return ae;
        }

        try {
            userOrderGuideDV = shoppingServEjb.getUserOrderGuides(user.getUserId(), catalogId, siteId);
        } catch (RemoteException exc) {
            exc.printStackTrace();
            ae.add("error", new ActionError("error.systemError", "Can't pick up order guides"));
            return ae;
        }

        pForm.setUserOrderGuides(userOrderGuideDV);
        pForm.setOrderGuideId(orderGuideId);
        if (ae.size() == 0) {
            Object[] params = new Object[1];
            params[0] = name;
            pForm.setConfirmMessage(ClwI18nUtil.getMessage(request, "shoppingCart.text.actionMessage.listUpdated", params));
        }

        refreshOrderGuideSessionAttr(request, pForm);

        return ae;
    }

    private static boolean isEmpty(List cartItems) {
        for (int i = 0; i < cartItems.size(); i++) {
            if (((ShoppingCartItemData) cartItems.get(i)).getQuantity() > 0) {
                return false;
            }
        }
        return true;
    }

    private static void refreshOrderGuideSessionAttr(HttpServletRequest request, UserShopForm pForm) throws Exception {
        OrderGuideDataVector newOrderGuides = new OrderGuideDataVector();

        if (pForm.getUserOrderGuides() != null) {
            newOrderGuides.addAll(pForm.getUserOrderGuides());
        }

        if (pForm.getTemplateOrderGuides() != null) {
            newOrderGuides.addAll(pForm.getTemplateOrderGuides());
        }

        if (pForm.getCustomOrderGuides() != null) {
            newOrderGuides.addAll(pForm.getCustomOrderGuides());
        }

        DisplayListSort.sort(newOrderGuides, "short_desc");
        request.getSession().setAttribute(SessionAttributes.ORDER_GUIDE.ALL_ORDER_GUIDES, newOrderGuides);

    }


    public static ActionErrors addItemToOrderGuide(HttpServletRequest request, UserShopForm pForm) throws Exception {

        ActionErrors ae = new ActionErrors();

        if (pForm.getItemDetail() != null) {
            if (pForm.getCartItems() != null && pForm.getCartItems().size() == 1) {
                if (pForm.getCartItems().get(0) instanceof ShoppingCartItemData) {
                    if (((ShoppingCartItemData) pForm.getCartItems().get(0)).getItemId() == pForm.getItemDetail().getItemId()) {

                        String opCd = request.getParameter("operation");

                        ShoppingCartItemData cartItem = (ShoppingCartItemData) pForm.getCartItems().get(0);

                        pForm.setItemIdsElement(0, cartItem.getItemId());
                        pForm.setQuantityElement(0, pForm.getQuantityDetail());

                        if ("saveOrderGuide".equals(opCd)) {
                            ae = saveUserOrderGuide(request, pForm);
                        } else if ("updateOrderGuide".equals(opCd)) {
                            ae = updateUserOrderGuide(request, pForm);
                        } else {
                            String errorMess = "UserShopLogic.addItemToOrderGuide => Unknown operation code: [" + opCd + "]";
                            ae.add("error", new ActionError("error.systemError", errorMess));
                            return ae;
                        }

                    } else {
                        String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.pageExpired", null);
                        ae.add("error", new ActionError("error.simpleGenericError", errorMess));
                        return ae;
                    }
                } else {
                    String errorMess = "UserShopLogic.addItemToOrderGuide => Unknown type of requested element: " + pForm.getCartItems().getClass().getName();
                    ae.add("error", new ActionError("error.systemError", errorMess));
                    return ae;
                }
            } else {
                String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.pageExpired", null);
                ae.add("error", new ActionError("error.simpleGenericError", errorMess));
                return ae;
            }
        } else {
            String errorMess = "UserShopLogic.addItemToOrderGuide => No mounted data.";
            ae.add("error", new ActionError("error.systemError", errorMess));
            return ae;
        }

        return ae;
    }

    private static String getActualSku(String pStoreType, ProductData prd) {
      String sku = prd.getCatalogSkuNum();
      if (pStoreType.equals(RefCodeNames.STORE_TYPE_CD.DISTRIBUTOR)) {
        if (prd.getActualCustomerSkuNum() != null && prd.getActualCustomerSkuNum().length() != 0) {
          sku = prd.getActualCustomerSkuNum();
        }
        else {
          ItemMappingData imp = prd.getCatalogDistrMapping();
          if (imp != null) {
            sku = imp.getItemNum();
          }
        }
      }
      return sku;
    }



    public static ActionErrors checkQuantities(HttpServletRequest request, UserShopForm pForm) {
        ActionErrors ae = new ActionErrors();
        List cartItems = pForm.getCartItems();
        if (cartItems == null) {
            return ae;
        }
        List<String> msgList = new ArrayList<String>();
        Iterator it = cartItems.iterator();
        while (it.hasNext()) {
            ShoppingCartItemData sciTemplateD =(ShoppingCartItemData)it.next();
            String qtyEnteredStr = sciTemplateD.getQuantityString();
            if (Utility.isSet(qtyEnteredStr)) {
                try {
                    int qtyEntered = Integer.parseInt(qtyEnteredStr);
                    if (qtyEntered <= 0) {
                        msgList.add(sciTemplateD.getActualSkuNum());
                    }
                } catch (NumberFormatException ex) {
                    msgList.add(sciTemplateD.getActualSkuNum());
                }
            }
        }
        if (msgList.size() > 0 && !msgList.isEmpty()) {
            Object[] param = new Object[1];
            param[0] = Utility.toCommaSting(msgList);
            String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.invalidQtyMessage", param);
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
        }
        return ae;
    }

    /*
     * Returns true if any top level category have a child category.
     */
    public static boolean isMultiLevelExists(MenuItemView root){
    	if (MenuItemView.ATTR.ROOT.equals(root.getKey())) {
    		if (root.getSubItems() != null && !root.getSubItems().isEmpty()) {
          for (Object o : root.getSubItems()) {
              MenuItemView menuItem = (MenuItemView) o;
              if (menuItem.getSubItems() != null && !menuItem.getSubItems().isEmpty()) {
              	return true;
              }
          }
    		}
    	}
    	return false;
    }
    /**
     * Prepares the product catalog products
     * @param request
     * @param pForm
     * @return
     * @throws Exception
     */
    public static ActionErrors navigateCatalogProducts(HttpServletRequest request, UserShopForm userShopForm) throws Exception {

        log.info("navigateCatalogProducts()=> BEGIN");

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();


        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        if (factory == null) {
            ae.add("error", new ActionError("error.systemError", "No Ejb access"));
            return ae;
        }

        CatalogInformation catalogEjb = factory.getCatalogInformationAPI();
        ShoppingServices shoppingServEjb = factory.getShoppingServicesAPI();

        Integer catalogIdI = (Integer) session.getAttribute(Constants.CATALOG_ID);
        if (catalogIdI == null) {
            ae.add("error", new ActionError("error.systemError", "No " + Constants.CATALOG_ID + " session attrbute found"));
            return ae;
        }

        String categoryKey = (String)request.getAttribute("catalogItemKey");
        session.setAttribute("catalogItemKey", categoryKey);

        log.info("navigateCatalogProducts()=> categoryKey:" + categoryKey);

        if (Utility.isSet(categoryKey)) {

            ArrayList<CategoryInfoView> path = new ArrayList<CategoryInfoView>();
            ArrayList<CategoryInfoView> relPath = new ArrayList<CategoryInfoView>();
            PairViewVector navInfo = new PairViewVector();

            refreshMenuDisplayStaus(userShopForm.getCatalogMenu(),
                    null,
                    MenuItemView.DISPLAY_STATUS.UNAVAILABLE,
                    true);

            pupulateCategoryActivePath(userShopForm.getCatalogMenu(), categoryKey, path);
            pupulateShopNavigationInfo(path, userShopForm.getCatalogMenu(), navInfo);

            log.info("navigateCatalogProducts => path: "+path);

            refreshMenuDisplayStaus(userShopForm.getCatalogMenu(),
                    path,
                    MenuItemView.DISPLAY_STATUS.CLOSE,
                    true);

            for(int i=1;i<path.size();i++){
                relPath.add(path.get(i));
            }

            refreshMenuDisplayStaus(userShopForm.getCatalogMenu(),
                    relPath,
                    MenuItemView.DISPLAY_STATUS.BLOCK_FOR_CLOSE,
                    false);

            Integer categoryKeyInt = Integer.parseInt(categoryKey);
            IdVector availableCategoryIds = Utility.toIdVector(userShopForm.getContractCategoryIds());

            ProductDataVector prods = new ProductDataVector();
            int siteId = userShopForm.getAppUser().getSite().getSiteId();
            int storeId = userShopForm.getAppUser().getUserStore().getStoreId();
            int storeCatalogId = catalogEjb.getStoreCatalogId(storeId);

            if (availableCategoryIds.contains(categoryKeyInt)) {
                if (siteId > 0) {
                    prods = shoppingServEjb.getCategoryChildProducts(storeCatalogId, siteId,
                            categoryKeyInt,
                            ShopTool.createShoppingItemRequest(request),
                            SessionTool.getCategoryToCostCenterView(session, siteId));
                } else {
                    prods = shoppingServEjb.getCategoryChildProducts(storeCatalogId, 0,
                            categoryKeyInt,
                            ShopTool.createShoppingItemRequest(request),
                            SessionTool.getCategoryToCostCenterView(session, siteId));
                }
            }    

            ProductDataVector products = getProductInfo(catalogEjb, prods, siteId,
                            SessionTool.getCategoryToCostCenterView(session, siteId));

            StoreData store = userShopForm.getAppUser().getUserStore();
            PropertyData storeTypeProperty = store.getStoreType();
            if (userShopForm.getOrderBy() == 0) {
            	userShopForm.setOrderBy(Constants.ORDER_BY_CATEGORY);
            }

            products = sortProducts(storeTypeProperty.getValue(), products, userShopForm.getOrderBy());

            userShopForm.setProductList(products);
            userShopForm.setCategoryKey(categoryKey);
            userShopForm.setCategoryPath(path);
            userShopForm.setShopNavigatorInfo(navInfo);

            ae = prepareCartItems(request, userShopForm);
           
            if (ae.size() > 0) {
                return ae;
            }
        }

        log.info("navigateCatalogProducts()=> END.");

        return ae;
    }

}

