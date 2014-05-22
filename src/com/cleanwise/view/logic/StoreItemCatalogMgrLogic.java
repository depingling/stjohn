/*OrderGuideFl
 * StoreItemCatalogMgrLogic.java
 *
 * Created on May 5, 2005, 2:17 PM
 */

package com.cleanwise.view.logic;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Category;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.APIServiceAccessException;
import com.cleanwise.service.api.session.Catalog;
import com.cleanwise.service.api.session.CatalogInformation;
import com.cleanwise.service.api.session.Contract;
import com.cleanwise.service.api.session.Distributor;
import com.cleanwise.service.api.session.ItemInformation;
import com.cleanwise.service.api.session.Manufacturer;
import com.cleanwise.service.api.session.OrderGuide;
import com.cleanwise.service.api.session.PropertyService;
import com.cleanwise.service.api.session.Store;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.AccountData;
import com.cleanwise.service.api.value.AccountDataVector;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.BusEntityDataVector;
import com.cleanwise.service.api.value.BusEntitySearchCriteria;
import com.cleanwise.service.api.value.CatalogAssocData;
import com.cleanwise.service.api.value.CatalogAssocDataVector;
import com.cleanwise.service.api.value.CatalogCategoryData;
import com.cleanwise.service.api.value.CatalogCategoryDataVector;
import com.cleanwise.service.api.value.CatalogData;
import com.cleanwise.service.api.value.CatalogDataVector;
import com.cleanwise.service.api.value.CatalogStructureData;
import com.cleanwise.service.api.value.ContractData;
import com.cleanwise.service.api.value.ContractDataVector;
import com.cleanwise.service.api.value.DistItemView;
import com.cleanwise.service.api.value.DistItemViewVector;
import com.cleanwise.service.api.value.DistributorData;
import com.cleanwise.service.api.value.DistributorDataVector;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.ItemCatalogAggrView;
import com.cleanwise.service.api.value.ItemCatalogAggrViewVector;
import com.cleanwise.service.api.value.ItemData;
import com.cleanwise.service.api.value.ItemDataVector;
import com.cleanwise.service.api.value.ItemView;
import com.cleanwise.service.api.value.ItemViewVector;
import com.cleanwise.service.api.value.ManufacturerData;
import com.cleanwise.service.api.value.ManufacturerDataVector;
import com.cleanwise.service.api.value.MultiproductView;
import com.cleanwise.service.api.value.MultiproductViewVector;
import com.cleanwise.service.api.value.OrderGuideDescData;
import com.cleanwise.service.api.value.OrderGuideDescDataVector;
import com.cleanwise.service.api.value.ProductData;
import com.cleanwise.service.api.value.PropertyData;
import com.cleanwise.service.api.value.PropertyDataVector;
import com.cleanwise.service.api.value.StoreData;
import com.cleanwise.service.api.value.UploadSkuData;
import com.cleanwise.service.api.value.UploadSkuView;
import com.cleanwise.service.api.value.UploadSkuViewVector;
import com.cleanwise.view.forms.LocateUploadItemForm;
import com.cleanwise.view.forms.StoreItemCatalogMgrForm;
import com.cleanwise.view.forms.StoreItemOrderGuideMgrForm;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.SessionAttributes;
import com.cleanwise.view.utils.SessionTool;
import com.cleanwise.view.utils.StringUtils;
/**
 *
 * @author Ykupershmidt
 */
public class StoreItemCatalogMgrLogic {
    private static final Category log = Category.getInstance(StoreItemCatalogMgrLogic.class);

    private final static String className = "StoreItemCatalogMgrLogic";

    public static ActionErrors init(HttpServletRequest request,
                                  ActionForm form) throws Exception {
    log.info("init START");

    ActionErrors ae = new ActionErrors();
    StoreItemCatalogMgrForm pForm = (StoreItemCatalogMgrForm) form;
    HttpSession session = request.getSession();
    String theUrl = SessionTool.getActualRequestedURI(request);
    String prevPage =
      (String) session.getAttribute(SessionAttributes.ITEM_MANAGER_PAGE.
                                    CURRENT_PAGE);
    String newPage = "itemcontract";
    if (theUrl.indexOf("cat-item") >= 0) {
      newPage = "cat-item";
    }
    else if (theUrl.indexOf("orderguide-item") >= 0) {
    	newPage = "orderguide-item";
    }
    if (!newPage.equals(prevPage)) {
      clearFilters(request, pForm);
      session.setAttribute(SessionAttributes.ITEM_MANAGER_PAGE.CURRENT_PAGE,
                           newPage);
    }

    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);

    ItemInformation itemEjb = factory.getItemInformationAPI();
    CatalogInformation catInfEjb = factory.getCatalogInformationAPI();
    Manufacturer manufEjb = factory.getManufacturerAPI();
    PropertyService propServEjb = factory.getPropertyServiceAPI();

    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.
      APP_USER);
    
    Store storeEjb = factory.getStoreAPI();
    int storeId = appUser.getUserStore().getBusEntity().getBusEntityId();
    StoreData storeD = storeEjb.getStore(storeId);
    pForm.setStore(storeD);
    PropertyDataVector prop = storeD.getMiscProperties();
    for (int ii = 0; ii < prop.size(); ii++) {
        PropertyData pD = (PropertyData) prop.get(ii);
        String propType = pD.getPropertyTypeCd();
        String propValue = pD.getValue();
        if (RefCodeNames.PROPERTY_TYPE_CD.EQUAL_COST_AND_PRICE.equals(propType)) {
        	log.info("propValue = " + propValue);
        	log.info("flagCatalogItems_ItemCatalogs = " + Utility.isTrue(propValue));
            pForm.setShowDistCostFl(!Utility.isTrue(propValue));
        }else if (RefCodeNames.PROPERTY_TYPE_CD.ALLOW_MIXED_CATEGORY_AND_ITEM.equals(propType)) {
        	pForm.setAllowMixedCategoryAndItemUnderSameParent(Utility.isTrue(propValue));
		}
    }
    
    int storeCatalogId = catInfEjb.getStoreCatalogId(storeId);
    pForm.setStoreErpSystem(storeD.getErpSystemCode());    
    
    CatalogCategoryDataVector storeCategories = null;
    if (pForm.getAllowMixedCategoryAndItemUnderSameParent()){
    	storeCategories = catInfEjb.getAllStoreCatalogCategories(storeCatalogId);
    }else{
    	storeCategories = catInfEjb.getLowestStoreCatalogCategories(storeCatalogId);
    }
        
    storeCategories = sortCategories(storeCategories);
    pForm.setStoreCategories(storeCategories);

    readStoreManufacturers(manufEjb, pForm, storeId);    
    MultiproductViewVector storeMultiproducts = catInfEjb.getStoreMultiproducts(storeId);
    pForm.setStoreMultiproducts(storeMultiproducts);

    return ae;
  }

  public static ActionErrors setItemFilter(HttpServletRequest request,
                                           ActionForm form) throws Exception {
      ActionErrors ae = new ActionErrors();
    StoreItemCatalogMgrForm sForm = (StoreItemCatalogMgrForm) form;

    ItemViewVector itemViewV = sForm.getItemFilter();
    ItemView itemVw = null;
    if (itemViewV == null || itemViewV.size() == 0) {
      return ae;
    }
    HttpSession session = request.getSession();
    String page =
      (String) session.getAttribute(SessionAttributes.ITEM_MANAGER_PAGE.
                                    CURRENT_PAGE);
    if (page == null || page.equals("itemcontract")) {
      itemVw = (ItemView) itemViewV.get(0);
      ae = loadItem(request, form, itemVw.getItemId());
      if (ae.size() > 0) {
        return ae;
      }
      ae = loadAggrData(request, form);
    }

    else if (page.equals("cat-item")) {
      if (sForm.getCatalogFilter() == null ||
          sForm.getCatalogFilter().size() == 0) {
        return ae;
      }
      ae = loadCatalogData(request, form);
      return ae;

      /*
       if(sForm.getCatalogFilter()==null && sForm.getCatalogFilter().size() != 0 ){
           APIAccess factory = new APIAccess();
           CatalogInformation cejb = factory.getCatalogInformationAPI();
       CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);
           StoreData storeD = appUser.getUserStore();
           int storeId = storeD.getBusEntity().getBusEntityId();
           int storeCatId = cejb.getStoreCatalogId(storeId);
           CatalogDataVector catalogDV = sForm.getCatalogFilter();
           int catId = ((CatalogData)catalogDV.get(0)).getCatalogId();
           IdVector itemIdV = new IdVector();
           Iterator iter=itemViewV.iterator();
           while(iter.hasNext()) {
               itemVw = (ItemView) iter.next();
               itemIds.add(new Integer(itemVw.getItemId()));
           }
           int orderGuideId = sForm.getSelectedOrderGuideId();
           ItemCatalogAggrViewVector items = cejb.getItemCatalogMgrSet(storeCatId, catId, orderGuideId, itemIds);
           sForm.setItemAggrVector(items);
       }
       sForm.setNeedToSaveFl(false);
       */
    }

    return ae;
  }

    public static ActionErrors setItemOrderGuideFilter(HttpServletRequest request,
        ActionForm form) throws Exception {
        log.info("setItemOrderGuideFilter()=> BEGIN");

        ActionErrors ae = new ActionErrors();
        StoreItemOrderGuideMgrForm orderGuideForm = (StoreItemOrderGuideMgrForm) form;

        ItemViewVector itemViewV = orderGuideForm.getItemFilter();
        ItemView itemVw = null;
        if (itemViewV == null || itemViewV.size() == 0) {
            return ae;
        }

        HttpSession session = request.getSession();

        String page = (String) session.getAttribute(SessionAttributes.ITEM_MANAGER_PAGE.CURRENT_PAGE);
        if (!"orderguide-item".equals(page)) {
            return ae;
        }
        OrderGuideDescDataVector orderGuideDescVector = orderGuideForm.getOrderGuideFilter();
        if (orderGuideDescVector == null || orderGuideDescVector.size() == 0) {
            return ae;
        }
        ae = loadOrderGuideData(request, form);
        return ae;
    }

  public static ActionErrors setCatalogFilter(HttpServletRequest request,
                                              ActionForm form) throws Exception {

    ActionErrors ae = new ActionErrors();
    StoreItemCatalogMgrForm pForm = (StoreItemCatalogMgrForm) form;
    HttpSession session = request.getSession();
    String page = (String) session.getAttribute(SessionAttributes.
                                                ITEM_MANAGER_PAGE.CURRENT_PAGE);
    CatalogDataVector catalogDV = pForm.getCatalogFilter();
    String sessionParameterName = "catalogListSelectedCatItems";
    if ("itemcontract".equals(page)) {
      sessionParameterName = "catalogListSelectedItemContract";
    }
    if (catalogDV != null && catalogDV.size() > 0) {
      session.setAttribute(sessionParameterName, catalogDV);
    } else {
      session.removeAttribute(sessionParameterName);
    }
    if ("itemcontract".equals(page)) {
      return ae;
    }
    pForm.setSelectedOrderGuideId( -1);
    ae = loadCatalogData(request, form);
    return ae;
  }

    public static ActionErrors setOrderGuideFilter(HttpServletRequest request,
        ActionForm form) throws Exception {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        StoreItemOrderGuideMgrForm orderGuideForm = (StoreItemOrderGuideMgrForm) form;

        String page = (String) session.getAttribute(SessionAttributes.ITEM_MANAGER_PAGE.CURRENT_PAGE);
        if (!"orderguide-item".equals(page)) {
            return ae;
        }
        OrderGuideDescDataVector orderGuideDescVector = orderGuideForm.getOrderGuideFilter();
        if (orderGuideDescVector == null || orderGuideDescVector.size() == 0) {
            return ae;
        }
        ae = loadOrderGuideData(request, form);
        return ae;
    }

  public static ActionErrors setDistFilter(HttpServletRequest request,
                                           ActionForm form) throws Exception {

    ActionErrors ae = new ActionErrors();
    StoreItemCatalogMgrForm pForm = (StoreItemCatalogMgrForm) form;
    HttpSession session = request.getSession();
    String page = (String) session.getAttribute(SessionAttributes.
                                                ITEM_MANAGER_PAGE.CURRENT_PAGE);
    if ("itemcontract".equals(page)) {
      return ae;
    }
    if (pForm.getCatalogFilter() == null ||
        pForm.getCatalogFilter().size() == 0) {
      return ae;
    }
    ae = loadCatalogData(request, form);
    return ae;
  }

    public static ActionErrors setDistOrderGuideFilter(HttpServletRequest request,
        ActionForm form) throws Exception {

        ActionErrors ae = new ActionErrors();
        StoreItemOrderGuideMgrForm orderGuideForm = (StoreItemOrderGuideMgrForm)form;
        HttpSession session = request.getSession();

        String page = (String) session.getAttribute(SessionAttributes.ITEM_MANAGER_PAGE.CURRENT_PAGE);
        if (!"orderguide-item".equals(page)) {
            return ae;
        }
        OrderGuideDescDataVector orderGuideDescVector = orderGuideForm.getOrderGuideFilter();
        if (orderGuideDescVector == null || orderGuideDescVector.size() == 0) {
            return ae;
        }
        ae = loadOrderGuideData(request, form);
        return ae;
    }

  public static ActionErrors setManufFilter(HttpServletRequest request,
                                            ActionForm form) throws Exception {

    ActionErrors ae = new ActionErrors();
    StoreItemCatalogMgrForm pForm = (StoreItemCatalogMgrForm) form;
    HttpSession session = request.getSession();
    String page = (String) session.getAttribute(SessionAttributes.
                                                ITEM_MANAGER_PAGE.CURRENT_PAGE);
    if ("itemcontract".equals(page)) {
      return ae;
    }
    if (pForm.getCatalogFilter() == null) {
      return ae;
    }
    ae = loadCatalogData(request, form);
    return ae;
  }

    public static ActionErrors setManufOrderGuideFilter(HttpServletRequest request,
        ActionForm form) throws Exception {

        ActionErrors ae = new ActionErrors();
        StoreItemOrderGuideMgrForm orderGuideForm = (StoreItemOrderGuideMgrForm)form;
        HttpSession session = request.getSession();

        String page = (String) session.getAttribute(SessionAttributes.ITEM_MANAGER_PAGE.CURRENT_PAGE);
        if (!"orderguide-item".equals(page)) {
            return ae;
        }
        OrderGuideDescDataVector orderGuideDescVector = orderGuideForm.getOrderGuideFilter();
        if (orderGuideDescVector == null || orderGuideDescVector.size() == 0) {
            return ae;
        }
        ae = loadOrderGuideData(request, form);
        return ae;
    }

    public static ActionErrors updateFromXls(HttpServletRequest request, ActionForm form)
            throws Exception {

        ActionErrors ae = new ActionErrors();

        boolean needToSaveFl = false;
        boolean equalCostAndPrice = false;
        StoreItemCatalogMgrForm pForm = (StoreItemCatalogMgrForm) form;

        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        StoreData storeD = appUser.getUserStore();

        PropertyDataVector prop = storeD.getMiscProperties();
        for (int ii = 0; ii < prop.size(); ii++) {

            PropertyData pD = (PropertyData) prop.get(ii);
            String propType = pD.getPropertyTypeCd();
            String propValue = pD.getValue();

            if (RefCodeNames.PROPERTY_TYPE_CD.EQUAL_COST_AND_PRICE.equals(propType)) {
                equalCostAndPrice = (Utility.isTrue(propValue));
            }
        }

        LocateUploadItemForm luiForm = pForm.getLocateUploadItemForm();
        boolean addToOrderGuideFl = luiForm.getRetAddToOrderGuideFl();
        boolean updateCategoryFl = luiForm.getRetUpdateCategoryFl();
        boolean updateDistSkuFl = luiForm.getRetUpdateDistSkuFl();
        boolean updateDistUomPackFl = luiForm.getRetUpdateDistUomPackFl();
        boolean updateDistCostFl = luiForm.getRetUpdateDistCostFl();
        boolean updateServiceFeeCodeFl = luiForm.getRetUpdateServiceFeeCodeFl();
        boolean updateBaseCostFl = luiForm.getRetUpdateBaseCostFl();
        boolean updateCatalogPriceFl = luiForm.getRetUpdateCatalogPriceFl();
        boolean updateSplFl = luiForm.getRetUpdateSplFl();
        boolean updateTaxExemptFl = luiForm.getRetUpdateTaxExemptFl();
        boolean updateSpecialPermissionFl = luiForm.getRetUpdateSpecialPermissionFl();
        boolean updateCustomerSkuFl = luiForm.getRetUpdateCustomerSkuFl();
        boolean updateGenManufNameFl = luiForm.getRetUpdateGenManufNameFl();
        boolean updateGenManufSkuFl = luiForm.getRetUpdateGenManufSkuFl();
        int updateInsert = luiForm.getUpdateInsert();
        boolean insertFl = (updateInsert != LocateUploadItemForm.UPDATE_ONLY) ? true : false;
        boolean updateFl = (updateInsert != LocateUploadItemForm.INSERT_ONLY) ? true : false;
        int distProc = luiForm.getDistProc();

        ManufacturerDataVector mDV = pForm.getManufFilter();
        DistributorDataVector dDV = pForm.getDistFilter();

        ItemViewVector iVwV = pForm.getItemFilter();
        if (insertFl &&
                (mDV != null && mDV.size() > 0 || dDV != null && dDV.size() > 0 ||
                        iVwV != null && iVwV.size() > 0)) {
            String errorMess =
                    "Can't istert items if distributor or manufacturer filter is set";
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }

        UploadSkuViewVector uploadSkus = luiForm.getUploadSkusToReturn();
        ae = checkDecimalPlaces(request, uploadSkus, pForm);

       if (ae.size() > 0){
          return ae;
       }


        ItemCatalogAggrViewVector icaVwV = pForm.getItemAggrVector();
        IdVector itemsToAddIdV = new IdVector();

        UploadSkuViewVector itemsToAdd = new UploadSkuViewVector();
        if (uploadSkus == null) {
            return ae;
        }

        for (Iterator iter = uploadSkus.iterator(); iter.hasNext(); ) {
            UploadSkuView usVw = (UploadSkuView) iter.next();
            UploadSkuData usD = usVw.getUploadSku();
            int itemId = usD.getItemId();
            boolean foundFl = false;

            for (Iterator iter1 = icaVwV.iterator(); iter1.hasNext(); ) {

                ItemCatalogAggrView icaVw = (ItemCatalogAggrView) iter1.next();

                if (itemId == icaVw.getItemId()) {

                    foundFl = true;

                    if (!updateFl) {
                        continue; // looking only for new items
                    }

                    if (distProc == LocateUploadItemForm.DISTRIBUTOR_EXCEPTION) {
                        if (icaVw.getDistId() > 0 && usD.getDistId() > 0) {
                            if (icaVw.getDistId() != usD.getDistId()) {
                                String errorMess = "Distributor doesn't match. Sku: " +
                                        icaVw.getSkuNum();
                                ae.add("error",
                                        new ActionError("error.simpleGenericError", errorMess));
                                return ae;
                            }
                        } else {
                            continue; //Do not do anithig if one distributor is 0
                        }
                    }

                    if (distProc == LocateUploadItemForm.DISTRIBUTOR_MATCH) {
                        if (icaVw.getDistId() == 0 ||
                                usD.getDistId() == 0 ||
                                icaVw.getDistId() != usD.getDistId()) {
                            continue; //Distributor doesn't match
                        }
                    }

                    if (distProc == LocateUploadItemForm.DISTRIBUTOR_ASSIGN) {
                        if (usD.getDistId() > 0) {
                            needToSaveFl = true;
                            icaVw.setDistIdInp("" + usD.getDistId());
                        }
                    }

                    if (updateDistSkuFl) {
                        needToSaveFl = true;
                        icaVw.setDistSkuNumInp(usD.getDistSku());
                    }

                    if (updateDistUomPackFl) {
                        needToSaveFl = true;
                        icaVw.setDistSkuUomInp(usD.getDistUom());
                        icaVw.setDistSkuPackInp(usD.getDistPack());
                        if (usD.getDistUomMult() != null) {
                            icaVw.setDistConversInp(usD.getDistUomMult());
                        }
                    }

                    if (updateDistCostFl) {
                        needToSaveFl = true;
                        icaVw.setCostInp(usD.getDistCost());
                    }
                    if (updateServiceFeeCodeFl) {
                        needToSaveFl = true;
                        icaVw.setServiceFeeCodeInp(usD.getServiceFeeCode());
                    }
                    if (updateBaseCostFl) {
                        needToSaveFl = true;
                        icaVw.setBaseCostInp(usD.getBaseCost());
                    }

                    if (updateCatalogPriceFl) {
                        needToSaveFl = true;
                        icaVw.setPriceInp(usD.getCatalogPrice());

                        if(equalCostAndPrice){
                            icaVw.setCostInp(usD.getCatalogPrice());
                        }
                    }

                    if (updateSplFl) {
                        String splFlS = usD.getSpl();
                        if (splFlS != null && splFlS.trim().length() >= 1) {
                            splFlS = splFlS.trim().toUpperCase();
                            icaVw.setDistSPLFlInp(Utility.isTrue(splFlS));
                            needToSaveFl = true;
                        }
                    }

                    if (updateTaxExemptFl) {
                        String taxExemptS = usD.getTaxExempt();
                        if (taxExemptS != null && taxExemptS.trim().length() >= 1) {
                            taxExemptS = taxExemptS.trim().toUpperCase();
                            needToSaveFl = true;
                            icaVw.setTaxExemptFlInp(Utility.isTrue(taxExemptS));
                        }
                    }

                    if (updateSpecialPermissionFl) {
                        needToSaveFl = true;
                        icaVw.setSpecialPermissionFlInp(Utility.isTrue(usD.getSpecialPermission()));
                    }

                    if (updateCustomerSkuFl) {
                        needToSaveFl = true;
                        icaVw.setCatalogSkuNumInp(usD.getCustomerSkuNum());
                    }

                    if (addToOrderGuideFl) {
                        needToSaveFl = true;
                        icaVw.setOrderGuideFlInp(true);
                        icaVw.setContractFlInp(true);
                    }

                    if (updateCategoryFl) {
                        if (usVw.getCategoryId() > 0) {
                            needToSaveFl = true;
                            icaVw.setCategoryIdInp("" + usVw.getCategoryId());
                        }
                    }

                    if (updateGenManufNameFl) {
                        if (usD.getGenManufId() > 0) {
                            needToSaveFl = true;
                            icaVw.setGenManufIdInp("" + usD.getGenManufId());
                            icaVw.setGenManufName(usD.getGenManufName());
                            if (updateGenManufSkuFl) {
                                icaVw.setGenManufSkuNumInp(usD.getGenManufSku());
                            }
                        }
                    }

                    break;

                }
            }

            if (!foundFl && insertFl) {
                needToSaveFl = true;
                itemsToAddIdV.add(new Integer(usD.getItemId()));
                itemsToAdd.add(usVw);
            }
        }

        int orderGuideId = pForm.getSelectedOrderGuideId();
        CatalogDataVector catalogDV = pForm.getCatalogFilter();
        CatalogData catalogD = (CatalogData) catalogDV.get(0);

        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);

        ItemInformation itemEjb = factory.getItemInformationAPI();
        CatalogInformation catInfEjb = factory.getCatalogInformationAPI();
        Catalog catalogEjb = factory.getCatalogAPI();
        Manufacturer manufEjb = factory.getManufacturerAPI();

        int storeId = storeD.getBusEntity().getBusEntityId();
        int storeCatId = catInfEjb.getStoreCatalogId(storeId);

        ItemCatalogAggrViewVector itemCatAggrVwV =
                catInfEjb.getItemCatalogMgrSet(storeCatId, catalogD.getCatalogId(),
                        orderGuideId, itemsToAddIdV, null, null,RefCodeNames.ITEM_TYPE_CD.PRODUCT);

        int index = 0;
        List<Integer> itemsOfInactiveDist = new ArrayList<Integer>();
        HashSet categIdHS = new HashSet();
        for (Iterator iter = itemCatAggrVwV.iterator(); iter.hasNext(); ) {
            ItemCatalogAggrView icaVw = (ItemCatalogAggrView) iter.next();
            int itemId = icaVw.getItemId();
            for (Iterator iter1 = itemsToAdd.iterator(); iter1.hasNext(); ) {
                UploadSkuView usVw = (UploadSkuView) iter1.next();
                UploadSkuData usD = usVw.getUploadSku();
                if (itemId == usD.getItemId()) {
                    icaVw.setCatalogSkuNumInp(usD.getCustomerSkuNum());
                    icaVw.setCatalogFlInp(true);
                    icaVw.setContractFlInp(true);
                    icaVw.setCostInp(usD.getDistCost());
                    icaVw.setBaseCostInp(usD.getBaseCost());
                    icaVw.setPriceInp(usD.getCatalogPrice());
                    icaVw.setServiceFeeCodeInp(usD.getServiceFeeCode());
                    icaVw.setSpecialPermissionFlInp(Utility.isTrue(usD.getSpecialPermission()));

                    if (usD.getDistId() > 0) {
                        icaVw.setDistIdInp("" + usD.getDistId());
                        icaVw.setDistName(usD.getDistName());
                        icaVw.setDistSkuNumInp(usD.getDistSku());
                        icaVw.setDistSkuPackInp(usD.getDistPack());
                        icaVw.setDistSkuUomInp(usD.getDistUom());
                        //icaVw.setDistSkuNumInp(usD.getDistUomMult());
                        icaVw.setDistConversInp(usD.getDistUomMult());
                        if (usD.getGenManufId() > 0) {
                            icaVw.setGenManufIdInp("" + usD.getGenManufId());
                            icaVw.setGenManufName(usD.getGenManufName());
                            icaVw.setGenManufSkuNumInp(usD.getGenManufSku());
                        }
                        String splFlS = usD.getSpl();
                        if (splFlS != null && splFlS.trim().length() >= 1) {
                            splFlS = splFlS.trim().toUpperCase();
                            if (Utility.isTrue(splFlS)) {
                                icaVw.setDistSPLFlInp(true);
                                //icaVw.setOrderGuideFlInp(true);
                            }
                        }

                        String taxExemptFlS = usD.getTaxExempt();
                        if (taxExemptFlS != null && taxExemptFlS.trim().length() >= 1) {
                            taxExemptFlS = taxExemptFlS.trim().toUpperCase();
                            if (Utility.isTrue(taxExemptFlS)) {
                                icaVw.setTaxExemptFlInp(true);
                            }
                        }
                    }
                    if (addToOrderGuideFl) {
                        icaVw.setOrderGuideFlInp(true);
                        icaVw.setContractFlInp(true);
                    }
                    if (usVw.getCategoryId() > 0) {
                        icaVw.setCategoryIdInp("" + usVw.getCategoryId());
                        Integer categoryIdI = new Integer(usVw.getCategoryId());
                        if (!categIdHS.contains(categoryIdI)) {
                            categIdHS.add(categoryIdI);
                        }
                    }
                    //Items associated with inactive distributor should not be loaded into shopping catalog.
                    if(catalogD.getCatalogTypeCd().equals(RefCodeNames.CATALOG_TYPE_CD.SHOPPING)
                    		&& (usD.getDistStatus()==null || usD.getDistStatus().equals("") 
                    		|| usD.getDistStatus().equals(RefCodeNames.DISTRIBUTOR_STATUS_CD.INACTIVE))){
                    	itemsOfInactiveDist.add(itemId);
                    } else {
                    	icaVwV.add(index++, icaVw);
                    }
                    break;
                }
            }
        }
        
        //bug # 4793: Items of inactive distributor should not be loaded into shopping catalog.
        if(itemsOfInactiveDist.size()>0) { 
        	String item = "Item";
        	String distributor = "it's distributor is";
        	if(itemsOfInactiveDist.size()>1) {
        		item = "Items";
        		distributor = "their distributors are";
        	}
        	String errorMess = item+" "+itemsOfInactiveDist+" could not be loaded into catalog" +
        			" because "+distributor+" inactive.";
        	ae.add("error",
            new ActionError("error.simpleGenericError", errorMess));
        }
        
        //add categories to the catalog
        String userName = appUser.getUser().getUserName();
        IdVector catalogIdV = new IdVector();
        catalogIdV.add(new Integer(catalogD.getCatalogId()));
        boolean reloadCategFl = false;

        ItemDataVector categoryV = pForm.getCategories();
        for (Iterator iter = categIdHS.iterator(); iter.hasNext(); ) {
            Integer categIdI = (Integer) iter.next();
            int categId = categIdI.intValue();
            boolean foundFl = false;
            for (Iterator iter1 = categoryV.iterator(); iter1.hasNext(); ) {
                ItemData categD = (ItemData) iter1.next();
                if (categId == categD.getItemId()) {
                    foundFl = true;
                    break;
                }
            }
            if (!foundFl) {
            	catalogEjb.addCategoryWithParentCategoriesToCatalogs(catalogIdV, categId, userName);
                reloadCategFl = true;
            }
        }
        if (reloadCategFl) {
            ItemDataVector imDV = catInfEjb.getCatalogCategories(storeCatId,
                    catalogIdV, pForm.getAllowMixedCategoryAndItemUnderSameParent());
            pForm.setCategories(imDV);
        }
        ItemDataVector categDV = pForm.getCategories();
        HashMap categHM = new HashMap();
        for (Iterator iter = categDV.iterator(); iter.hasNext(); ) {
            ItemData categ = (ItemData) iter.next();
            categHM.put(new Integer(categ.getItemId()),  Utility.getCategoryFullName(categ));
        }
        for (Iterator iter = itemCatAggrVwV.iterator(); iter.hasNext(); ) {
            ItemCatalogAggrView icaVw = (ItemCatalogAggrView) iter.next();
            try {
                int categId = Integer.parseInt(icaVw.getCategoryIdInp());
                String categName = (String) categHM.get(new Integer(categId));
                if (categName != null) {
                    icaVw.setCategoryName(categName);
                }
            } catch (Exception exc) {}
        }

        //pForm.setShowDistSkuNumFl(true);
        //pForm.setShowDistSkuNumFlDef(true);
        if (needToSaveFl) {
            pForm.setNeedToSaveFl(true);
        }
        return ae;
    }

  public static ActionErrors loadItem(HttpServletRequest request,
                                      ActionForm form, int itemId) throws
    Exception {

    ActionErrors ae = new ActionErrors();
    StoreItemCatalogMgrForm pForm = (StoreItemCatalogMgrForm) form;
    HttpSession session = request.getSession();
    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);

    ItemInformation itemEjb = factory.getItemInformationAPI();
    CatalogInformation catInfEjb = factory.getCatalogInformationAPI();

    ProductData currManagingItem = catInfEjb.getProduct(itemId);
    pForm.setCurrManagingItem(currManagingItem);
    pForm.setNeedToSaveFl(false);
    return ae;
  }

  /*
      public static ActionErrors search(HttpServletRequest request,
           ActionForm form)
       throws Exception {
     ActionErrors ae = new ActionErrors();
     return ae;
   }
   */

  private static CatalogCategoryDataVector
    sortCategories(CatalogCategoryDataVector categories) {

    if (categories == null || categories.size() < 2) {
      return categories;
    }
    Object[] categA = categories.toArray();
    for (int ii = 0; ii < categA.length - 1; ii++) {
      boolean exitFl = true;
      for (int jj = 0; jj < categA.length - ii - 1; jj++) {
        CatalogCategoryData ccD1 = (CatalogCategoryData) categA[jj];
        CatalogCategoryData ccD2 = (CatalogCategoryData) categA[jj + 1];
        String shortDesc1 = ccD1.getCatalogCategoryShortDesc();
        String shortDesc2 = ccD2.getCatalogCategoryShortDesc();
        if(shortDesc1 == null){shortDesc1="";}
        if(shortDesc2 == null){shortDesc2="";}
        int comp = shortDesc1.compareToIgnoreCase(shortDesc2);
        if (comp > 0) {
          exitFl = false;
          categA[jj] = ccD2;
          categA[jj + 1] = ccD1;
        }
      }
      if (exitFl)break;
    }
    categories.clear();
    for (int ii = 0; ii < categA.length; ii++) {
      CatalogCategoryData ccD1 = (CatalogCategoryData) categA[ii];
      categories.add(ccD1);
    }
    return categories;
  }


  public static ActionErrors reload(HttpServletRequest request,
                                    ActionForm form) throws Exception {

    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    String page =
      (String) session.getAttribute(SessionAttributes.ITEM_MANAGER_PAGE.
                                    CURRENT_PAGE);
    if (page == null || page.equals("itemcontract")) {
      ae = loadAggrData(request, form);
    } else if (page.equals("cat-item")) {
      ae = loadCatalogData(request, form);
    } else if (page.equals("orderguide-item")) {
      ae = loadOrderGuideData(request, form);
    }
    if (ae.size() > 0) {
      return ae;
    }
    return ae;
  }

    public static ActionErrors loadAggrData(HttpServletRequest request,
                                            ActionForm form) throws Exception {
        ActionErrors ae = new ActionErrors();
        StoreItemCatalogMgrForm pForm = (StoreItemCatalogMgrForm) form;
        //saveSelectBox(request,form);
        HttpSession session = request.getSession();
        CatalogDataVector catalogDV = pForm.getCatalogFilter();
        DistributorDataVector distDV = pForm.getDistFilter();
        AccountDataVector accountDV = pForm.getAccountFilter();
        ProductData prodD = pForm.getCurrManagingItem();

        if (prodD == null ||
                ((catalogDV == null || catalogDV.size() == 0)
                        && (distDV == null || distDV.size() == 0)
                        && (accountDV == null || accountDV.size() == 0)
                )
                )
        {
            pForm.setItemAggrVector(null);
            pForm.setNeedToSaveFl(false);
            clearInputFields(request, form);
            pForm.setCategories(new ItemDataVector());
            return ae;
        }

        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        CatalogInformation catInfEjb = factory.getCatalogInformationAPI();

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        StoreData storeD = appUser.getUserStore();

        int storeId = storeD.getBusEntity().getBusEntityId();
        int storeCatId = catInfEjb.getStoreCatalogId(storeId);

        IdVector distIdV = new IdVector();
        if (distDV != null) {
            for (Iterator iter = distDV.iterator(); iter.hasNext(); ) {
                DistributorData dD = (DistributorData) iter.next();
                distIdV.add(new Integer(dD.getBusEntity().getBusEntityId()));
            }
        }

        IdVector accountIdV = new IdVector();
        if (accountDV != null) {
            for (Iterator iter = accountDV.iterator(); iter.hasNext(); ) {
                AccountData aD = (AccountData) iter.next();
                accountIdV.add(new Integer(aD.getBusEntity().getBusEntityId()));
            }
        }

        IdVector catIdV = new IdVector();
        if (catalogDV != null) {
            for (Iterator iter = catalogDV.iterator(); iter.hasNext(); ) {
                CatalogData cD = (CatalogData) iter.next();
                catIdV.add(new Integer(cD.getCatalogId()));
            }
        }

        MultiproductViewVector multiproductVV = catInfEjb.getStoreMultiproducts(storeId);
        pForm.setStoreMultiproducts(multiproductVV);

        ItemCatalogAggrViewVector itemCatAggrVwV;
        itemCatAggrVwV = catInfEjb.getItemCatalogMgrSet(storeCatId, prodD.getProductId(), distIdV, catIdV, accountIdV);
        IdVector catalogIds = Utility.toIdVector(pForm.getCatalogFilter());

        Iterator iter2 = itemCatAggrVwV.iterator();
        while (iter2.hasNext()) {
            ItemCatalogAggrView icaVw = (ItemCatalogAggrView) iter2.next();
            if (icaVw.getCatalogStatus().equals(RefCodeNames.CATALOG_STATUS_CD.INACTIVE)
                    && !catalogIds.contains(new Integer(icaVw.getCatalogId()))) {
                iter2.remove();
            }
        }

        if(pForm.getControlMultipleCatalogFl())
        {    //removes multiple record from collection .
            setMultipleFilterByCatalog(itemCatAggrVwV);
            clearOrderGuideOfItemCatalogAggrCollection(itemCatAggrVwV);
        }

        String sortField = pForm.getLastSortField();
        if (sortField == null){
            pForm.setLastSortField("CatalogId");
        }

        pForm.setItemAggrVector(itemCatAggrVwV);
        pForm.setNeedToSaveFl(false);
        clearInputFields(request, form);

        IdVector catalogIdV = new IdVector();
        for (Iterator iter = itemCatAggrVwV.iterator(); iter.hasNext(); ) {
            ItemCatalogAggrView icaVw = (ItemCatalogAggrView) iter.next();
            int catalogId = icaVw.getCatalogId();
            catalogIdV.add(new Integer(catalogId));
        }

        ItemDataVector categoryDV = catInfEjb.getCatalogCategories(storeCatId, catalogIdV, pForm.getAllowMixedCategoryAndItemUnderSameParent());
        pForm.setCategories(categoryDV);
        //resetSelectBox(request,form);
        ae = sort(request, form);
        if (ae.size() > 0) {
            return ae;
        }

        return ae;
    }

    public static ActionErrors loadOrderGuideData(HttpServletRequest request,
            ActionForm form) throws Exception {

    	ActionErrors ae = new ActionErrors();
    	StoreItemOrderGuideMgrForm pForm = (StoreItemOrderGuideMgrForm) form;

    	OrderGuideDescDataVector orderGuideVector = pForm.getOrderGuideFilter();
    	if (orderGuideVector == null || orderGuideVector.size() == 0) {
        	String errorMess = "Order guide should be selected";
        	ae.add("error", new ActionError("error.simpleGenericError", errorMess));
        	pForm.setOrderGuideFilter(null);
  	      	return ae;
        }
        if (orderGuideVector != null && orderGuideVector.size() > 1) {
        	String errorMess = "Only one order guide is allowed";
        	ae.add("error", new ActionError("error.simpleGenericError", errorMess));
        	pForm.setOrderGuideFilter(null);
  	      	return ae;
        }

        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        CatalogInformation catInfEjb = factory.getCatalogInformationAPI();

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        StoreData storeD = appUser.getUserStore();
        int storeId = storeD.getBusEntity().getBusEntityId();
        int storeCatId = catInfEjb.getStoreCatalogId(storeId);

        MultiproductViewVector multiproducts = catInfEjb.getStoreMultiproducts(storeId);
        pForm.setStoreMultiproducts(multiproducts);

        Store storeBean = factory.getStoreAPI();
        StoreData std = storeBean.getStore(storeId);
        PropertyDataVector propDV = std.getMiscProperties();
        for (int ii = 0; ii < propDV.size(); ii++) {
            PropertyData prD = (PropertyData) propDV.get(ii);
            String prType = prD.getPropertyTypeCd();
            String prValue = prD.getValue();
            if (RefCodeNames.PROPERTY_TYPE_CD.EQUAL_COST_AND_PRICE.equals(prType)) {
            	log.info("prValue = " + prValue);
            	log.info("flagOrderGuideItems = " + Utility.isTrue(prValue));
                pForm.setShowDistCostFl(!Utility.isTrue(prValue));
            }
        }

        IdVector manufIdV = null;
        ManufacturerDataVector manufDV = pForm.getManufFilter();
        if (manufDV != null && manufDV.size() > 0) {
        	manufIdV = new IdVector();
        	for (Iterator iter = manufDV.iterator(); iter.hasNext(); ) {
        		ManufacturerData mD = (ManufacturerData) iter.next();
        		manufIdV.add(new Integer(mD.getBusEntity().getBusEntityId()));
        	}
        }

        IdVector distIdV = null;
        DistributorDataVector distDV = pForm.getDistFilter();
        if (distDV != null && distDV.size() > 0) {
        	distIdV = new IdVector();
        	for (Iterator iter = distDV.iterator(); iter.hasNext(); ) {
        		DistributorData dD = (DistributorData) iter.next();
        		distIdV.add(new Integer(dD.getBusEntity().getBusEntityId()));
        	}
        }

        IdVector itemIdV = null;
        ItemViewVector itemVwV = pForm.getItemFilter();
        if (itemVwV != null && itemVwV.size() > 0) {
        	itemIdV = new IdVector();
        	for (Iterator iter = itemVwV.iterator(); iter.hasNext(); ) {
        		ItemView iVw = (ItemView) iter.next();
        		int itemId = iVw.getItemId();
        		itemIdV.add(new Integer(itemId));
        	}
        }

        OrderGuideDescData orderGuideDesc = (OrderGuideDescData)orderGuideVector.get(0);
        int catalogId = orderGuideDesc.getCatalogId();
        int orderGuideId = orderGuideDesc.getOrderGuideId();
        ItemCatalogAggrViewVector itemCatAggrVwV = catInfEjb.getItemCatalogMgrSet(storeCatId,
            catalogId, orderGuideId, itemIdV, manufIdV, distIdV, RefCodeNames.ITEM_TYPE_CD.PRODUCT);

        clearInputFields(request, form);

        String sortField = pForm.getLastSortField();
        if (sortField == null)
        	pForm.setLastSortField("SkuNum");
        pForm.setItemAggrVector(itemCatAggrVwV);
        pForm.setNeedToSaveFl(false);

        ae = sort(request, form);
        if (ae.size() > 0) {
        	return ae;
        }
        return ae;
    }

    public static ActionErrors loadCatalogData(HttpServletRequest request,
                                             ActionForm form) throws Exception {
    ActionErrors ae = new ActionErrors();
    StoreItemCatalogMgrForm pForm = (StoreItemCatalogMgrForm) form;
    HttpSession session = request.getSession();

    //Get catalog order guides
    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    CatalogInformation catInfEjb = factory.getCatalogInformationAPI();
    OrderGuide orderGuideEjb = factory.getOrderGuideAPI();

    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.
      APP_USER);
    StoreData storeD = appUser.getUserStore();
    int storeId = storeD.getBusEntity().getBusEntityId();
    int storeCatId = catInfEjb.getStoreCatalogId(storeId);

    CatalogDataVector catalogDV = pForm.getCatalogFilter();
    if (catalogDV != null && catalogDV.size() > 1) {
      String errorMess = "Only one catalog is allowed ";
      ae.add("error", new ActionError("error.simpleGenericError", errorMess));
      pForm.setCatalogFilter(null);
      return ae;
    }

    CatalogData catalogD = (CatalogData) catalogDV.get(0);

    int catalogId = catalogD.getCatalogId();
    IdVector catalogIdV = new IdVector();
    catalogIdV.add(catalogId);

    //Get catalog categories
    ItemDataVector imDV = catInfEjb.getCatalogCategories(storeCatId, catalogIdV, pForm.getAllowMixedCategoryAndItemUnderSameParent());
    
    pForm.setCategories(imDV);

    MultiproductViewVector multiproducts = catInfEjb.getStoreMultiproducts(storeId);

    pForm.setStoreMultiproducts(multiproducts);

    //Get catalog order guides
    OrderGuideDescDataVector orderGuideDescDV =
      orderGuideEjb.getCollectionByCatalog(catalogId, OrderGuide.TYPE_TEMPLATE);

    pForm.setOrderGuides(orderGuideDescDV);
    int orderGuideId = pForm.getSelectedOrderGuideId();
    if (orderGuideId != -1) {
      boolean foundFl = false;
      int newOrderGuideId = 0;
      for (Iterator iter = orderGuideDescDV.iterator(); iter.hasNext(); ) {
        OrderGuideDescData ogdD = (OrderGuideDescData) iter.next();
        if (newOrderGuideId == 0) newOrderGuideId = ogdD.getOrderGuideId();
        if (orderGuideId == ogdD.getOrderGuideId()) {
          foundFl = true;
          break;
        }
      }
      if (!foundFl) {
        orderGuideId = -1; //newOrderGuideId;
      }
    }
    pForm.setSelectedOrderGuideId(orderGuideId);

    IdVector manufIdV = null;
    ManufacturerDataVector manufDV = pForm.getManufFilter();
    if (manufDV != null && manufDV.size() > 0) {
      manufIdV = new IdVector();
      for (Iterator iter = manufDV.iterator(); iter.hasNext(); ) {
        ManufacturerData mD = (ManufacturerData) iter.next();
        manufIdV.add(new Integer(mD.getBusEntity().getBusEntityId()));
      }
    }

    IdVector distIdV = null;
    DistributorDataVector distDV = pForm.getDistFilter();
    if (distDV != null && distDV.size() > 0) {
      distIdV = new IdVector();
      for (Iterator iter = distDV.iterator(); iter.hasNext(); ) {
        DistributorData dD = (DistributorData) iter.next();
        distIdV.add(new Integer(dD.getBusEntity().getBusEntityId()));
      }
    }

    IdVector itemIdV = null;
    ItemViewVector itemVwV = pForm.getItemFilter();
    if (itemVwV != null && itemVwV.size() > 0) {
      itemIdV = new IdVector();
      for (Iterator iter = itemVwV.iterator(); iter.hasNext(); ) {
        ItemView iVw = (ItemView) iter.next();
        int itemId = iVw.getItemId();
        itemIdV.add(new Integer(itemId));
      }
    }

    ItemCatalogAggrViewVector itemCatAggrVwV =
      catInfEjb.getItemCatalogMgrSet(storeCatId, catalogId, orderGuideId,
                                     itemIdV,
                                     manufIdV, distIdV,RefCodeNames.ITEM_TYPE_CD.PRODUCT);

    clearInputFields(request, form);

    String sortField = pForm.getLastSortField();
    if (sortField == null) pForm.setLastSortField("SkuNum"); ;
    pForm.setItemAggrVector(itemCatAggrVwV);
    pForm.setNeedToSaveFl(false);

    ae = sort(request, form);
    if (ae.size() > 0) {
      return ae;
    }
    return ae;
  }


  public static ActionErrors sort(HttpServletRequest request,
                                  ActionForm form) throws Exception {
    ActionErrors ae = new ActionErrors();
    StoreItemCatalogMgrForm pForm = (StoreItemCatalogMgrForm) form;
    String sortField = request.getParameter("sortField");
    if (sortField == null || "BBBBBBB".equals(sortField)) {
      sortField = pForm.getLastSortField();
    } else {
      pForm.setLastSortField(sortField);
    }
    ItemCatalogAggrViewVector icaVwV = pForm.getItemAggrVector();
    boolean sortAscFl = pForm.getAscSortOrderFl();
    if (icaVwV != null) {
      if ("DistIdInp".equalsIgnoreCase(sortField) ||
          "MultiproductIdInp".equalsIgnoreCase(sortField) ||
          "GenManufIdInp".equalsIgnoreCase(sortField)) {
        icaVwV = sortInt(icaVwV, sortField, sortAscFl);
        pForm.setItemAggrVector(icaVwV);
      } else if ("CostInp".equalsIgnoreCase(sortField) ||
                 "PriceInp".equalsIgnoreCase(sortField) ||
                 "BaseCostInp".equalsIgnoreCase(sortField) ||
                 "DistConversInp".equalsIgnoreCase(sortField)) {
        icaVwV = sortBD(icaVwV, sortField, sortAscFl);
        pForm.setItemAggrVector(icaVwV);
      } else {
        icaVwV.sort(sortField, sortAscFl);
      }
    }
    return ae;
  }

  private static ItemCatalogAggrViewVector
    sortInt(ItemCatalogAggrViewVector icaVwV, String sortField, boolean pAscFl) {
    if (icaVwV == null || icaVwV.size() <= 1) {
      return icaVwV;
    }
    int[] valA = new int[icaVwV.size()];
    Object[] icaVwA = icaVwV.toArray();
    if ("DistIdInp".equalsIgnoreCase(sortField)) {
      for (int ii = 0; ii < icaVwA.length; ii++) {
        ItemCatalogAggrView icaVw = (ItemCatalogAggrView) icaVwA[ii];
        valA[ii] = 0;
        try {
          valA[ii] = Integer.parseInt(icaVw.getDistIdInp());
        } catch (Exception exc) {}
      }
    } else if ("MultiproductIdInp".equalsIgnoreCase(sortField)) {
        for (int ii = 0; ii < icaVwA.length; ii++) {
          ItemCatalogAggrView icaVw = (ItemCatalogAggrView) icaVwA[ii];
          valA[ii] = 0;
          try {
            valA[ii] = Integer.parseInt(icaVw.getMultiproductIdInp());
          } catch (Exception exc) {}
        }
    } else if ("GenManufIdInp".equalsIgnoreCase(sortField)) {
      for (int ii = 0; ii < icaVwA.length; ii++) {
        ItemCatalogAggrView icaVw = (ItemCatalogAggrView) icaVwA[ii];
        valA[ii] = 0;
        try {
          valA[ii] = Integer.parseInt(icaVw.getGenManufIdInp());
        } catch (Exception exc) {}
      }
    }
    //sort
    for (int ii = 0; ii < valA.length - 1; ii++) {
      boolean exitFl = true;
      for (int jj = 0; jj < valA.length - 1 - ii; jj++) {
        if ((valA[jj] > valA[jj + 1] && pAscFl) ||
            (valA[jj] < valA[jj + 1] && !pAscFl)) {
          exitFl = false;
          int wInt = valA[jj + 1];
          ItemCatalogAggrView wIcaVw = (ItemCatalogAggrView) icaVwA[jj + 1];
          valA[jj + 1] = valA[jj];
          icaVwA[jj + 1] = icaVwA[jj];
          valA[jj] = wInt;
          icaVwA[jj] = wIcaVw;
        }
      }
      if (exitFl) {
        break;
      }
    }
    icaVwV = new ItemCatalogAggrViewVector();
    for (int ii = 0; ii < icaVwA.length; ii++) {
      icaVwV.add(icaVwA[ii]);
    }
    return icaVwV;
  }

  private static ItemCatalogAggrViewVector
    sortBD(ItemCatalogAggrViewVector icaVwV, String sortField, boolean pAscFl) {
    if (icaVwV == null || icaVwV.size() <= 1) {
      return icaVwV;
    }
    double[] valA = new double[icaVwV.size()];
    Object[] icaVwA = icaVwV.toArray();
    if ("CostInp".equalsIgnoreCase(sortField)) {
      for (int ii = 0; ii < icaVwA.length; ii++) {
        ItemCatalogAggrView icaVw = (ItemCatalogAggrView) icaVwA[ii];
        valA[ii] = 0;
        try {
          valA[ii] = Double.parseDouble(icaVw.getCostInp());
        } catch (Exception exc) {}
      }
    } else if ("PriceInp".equalsIgnoreCase(sortField)) {
      for (int ii = 0; ii < icaVwA.length; ii++) {
        ItemCatalogAggrView icaVw = (ItemCatalogAggrView) icaVwA[ii];
        valA[ii] = 0;
        try {
          valA[ii] = Double.parseDouble(icaVw.getPriceInp());
        } catch (Exception exc) {}
      }
    } else if ("BaseCostInp".equalsIgnoreCase(sortField)) {
      for (int ii = 0; ii < icaVwA.length; ii++) {
        ItemCatalogAggrView icaVw = (ItemCatalogAggrView) icaVwA[ii];
        valA[ii] = 0;
        try {
          valA[ii] = Double.parseDouble(icaVw.getBaseCostInp());
        } catch (Exception exc) {}
      }
    } else if ("DistConversInp".equalsIgnoreCase(sortField)) {
      for (int ii = 0; ii < icaVwA.length; ii++) {
        ItemCatalogAggrView icaVw = (ItemCatalogAggrView) icaVwA[ii];
        valA[ii] = 0;
        try {
          valA[ii] = Double.parseDouble(icaVw.getDistConversInp());
        } catch (Exception exc) {}
      }
    }
    //sort
    for (int ii = 0; ii < valA.length - 1; ii++) {
      boolean exitFl = true;
      for (int jj = 0; jj < valA.length - 1 - ii; jj++) {
        if ((valA[jj] > valA[jj + 1] && pAscFl) ||
            (valA[jj] < valA[jj + 1] && !pAscFl)) {
          exitFl = false;
          double wDouble = valA[jj + 1];
          ItemCatalogAggrView wIcaVw = (ItemCatalogAggrView) icaVwA[jj + 1];
          valA[jj + 1] = valA[jj];
          icaVwA[jj + 1] = icaVwA[jj];
          valA[jj] = wDouble;
          icaVwA[jj] = wIcaVw;
        }
      }
      if (exitFl) {
        break;
      }
    }
    icaVwV = new ItemCatalogAggrViewVector();
    for (int ii = 0; ii < icaVwA.length; ii++) {
      icaVwV.add(icaVwA[ii]);
    }
    return icaVwV;
  }

  public static ActionErrors propagateAll(HttpServletRequest request,
                                          ActionForm form) throws Exception {
    ActionErrors ae = new ActionErrors();
    StoreItemCatalogMgrForm pForm = (StoreItemCatalogMgrForm) form;

    String categoryIdS = pForm.getCategoryIdDummy();
    int categoryId = 0;
    try {
      categoryId = Integer.parseInt(categoryIdS);
    } catch (Exception exc) {}
    if (categoryId > 0) {
      ae = StoreItemCatalogMgrLogic.propagateCategoryId(request, pForm);
      if (ae.size() > 0)return ae;
    }

    if (pForm.getDistDummy() != null) {
      ae = StoreItemCatalogMgrLogic.propagateDistId(request, pForm);
      if (ae.size() > 0)return ae;
    }

    if (Utility.isSet(pForm.getCostDummy())) {
      ae = StoreItemCatalogMgrLogic.propagateCost(request, pForm);
      if (ae.size() > 0)return ae;
    }

    if (Utility.isSet(pForm.getPriceDummy())) {
      ae = StoreItemCatalogMgrLogic.propagatePrice(request, pForm);
      if (ae.size() > 0)return ae;
    }

    //ae = StoreItemCatalogMgrLogic.propagateCostCenterId(request,sForm);

    if (pForm.getShowCustSkuNumFlDef() &&
        Utility.isSet(pForm.getCatalogSkuNumDummy())) {
      ae = StoreItemCatalogMgrLogic.propagateCatalogSkuNum(request, pForm);
      if (ae.size() > 0)return ae;
    }

    if (pForm.getShowBaseCostFlDef() && Utility.isSet(pForm.getBaseCostDummy())) {
      ae = StoreItemCatalogMgrLogic.propagateBaseCost(request, pForm);
      if (ae.size() > 0)return ae;
    }

    if (pForm.getShowDistSkuNumFlDef() &&
        Utility.isSet(pForm.getDistSkuNumDummy())) {
      ae = StoreItemCatalogMgrLogic.propagateDistSkuNum(request, pForm);
      if (ae.size() > 0)return ae;
    }

    if (pForm.getShowDistUomPackFlDef() &&
        Utility.isSet(pForm.getDistSkuUomDummy())) {
      ae = StoreItemCatalogMgrLogic.propagateDistSkuUom(request, pForm);
      if (ae.size() > 0)return ae;
    }

    if (pForm.getShowDistUomPackFlDef() &&
        Utility.isSet(pForm.getDistSkuPackDummy())) {
      ae = StoreItemCatalogMgrLogic.propagateDistSkuPack(request, pForm);
      if (ae.size() > 0)return ae;
    }

    if (pForm.getShowDistUomPackFlDef() &&
        Utility.isSet(pForm.getDistConversDummy())) {
      ae = StoreItemCatalogMgrLogic.propagateDistConvers(request, pForm);
      if (ae.size() > 0)return ae;
    }

    if (pForm.getShowGenManufFlDef()) {
      String genManufIdS = pForm.getGenManufIdDummy();
      int genManufId = 0;
      try {
        genManufId = Integer.parseInt(genManufIdS);
      } catch (Exception exc) {}
      if (genManufId > 0) {
        ae = StoreItemCatalogMgrLogic.propagateGenManufId(request, pForm);
        if (ae.size() > 0)return ae;
      }
    }

    if (pForm.getShowMultiproductsFlDef()) {
        String multiproductIdS = pForm.getMultiproductIdDummy();
        int multiproductId = 0;
        try {
            multiproductId = Integer.parseInt(multiproductIdS);
        } catch (Exception exc) {}
        if (multiproductId > 0) {
            ae = StoreItemCatalogMgrLogic.propagateMultiproductId(request, pForm);
            if (ae.size() > 0)return ae;
        }
    }

    if (pForm.getShowGenManufSkuNumFlDef() &&
        Utility.isSet(pForm.getGenManufSkuNumDummy())) {
      ae = StoreItemCatalogMgrLogic.propagateGenManufSkuNum(request, pForm);
      if (ae.size() > 0)return ae;
    }

    return ae;
  }

  public static ActionErrors propagateCategoryId(HttpServletRequest request,
                                                 ActionForm form) throws
    Exception {
    ActionErrors ae = new ActionErrors();
    StoreItemCatalogMgrForm pForm = (StoreItemCatalogMgrForm) form;
    String categoryIdS = pForm.getCategoryIdDummy();
    int categoryId = 0;
    try {
      categoryId = Integer.parseInt(categoryIdS);
    } catch (Exception exc) {
      String errorMess = "Category id is not numeric: " + categoryIdS;
      ae.add("error", new ActionError("error.simpleGenericError", errorMess));
      return ae;
    }
    ItemDataVector categoryDV = pForm.getCategories();
    String categoryName = null;
    if (categoryDV != null) {
      for (Iterator iter = categoryDV.iterator(); iter.hasNext(); ) {
        ItemData categoryD = (ItemData) iter.next();
        if (categoryD.getItemId() == categoryId) {
          categoryName = Utility.getCategoryFullName(categoryD);
          break;
        }
      }
    }
    if (categoryName == null) {
      String errorMess = (categoryId == 0) ?
                         "No category selected" :
                         "Category not found. Category id: " + categoryIdS;
      ae.add("error", new ActionError("error.simpleGenericError", errorMess));
      return ae;
    }
    ItemCatalogAggrViewVector icaVwV = pForm.getItemAggrVector();
    if (icaVwV != null) {
      for (Iterator iter = icaVwV.iterator(); iter.hasNext(); ) {
        ItemCatalogAggrView icaVw = (ItemCatalogAggrView) iter.next();
        if (icaVw.getCatalogFlInp() && icaVw.getSelectFl()) {
          icaVw.setCategoryIdInp("" + categoryId);
          icaVw.setCategoryName(categoryName);
          pForm.setNeedToSaveFl(true);
        }
      }
    }
    return ae;
  }

  public static ActionErrors propagateCostCenterId(HttpServletRequest request,
    ActionForm form) throws Exception {
    ActionErrors ae = new ActionErrors();
    StoreItemCatalogMgrForm pForm = (StoreItemCatalogMgrForm) form;

    return ae;
  }

  public static ActionErrors propagateCatalogSkuNum(HttpServletRequest request,
    ActionForm form) throws Exception {
    ActionErrors ae = new ActionErrors();
    StoreItemCatalogMgrForm pForm = (StoreItemCatalogMgrForm) form;
    String catalogSkuNum = pForm.getCatalogSkuNumDummy();
    ItemCatalogAggrViewVector icaVwV = pForm.getItemAggrVector();
    if (icaVwV != null) {
      for (Iterator iter = icaVwV.iterator(); iter.hasNext(); ) {
        ItemCatalogAggrView icaVw = (ItemCatalogAggrView) iter.next();
        if (icaVw.getCatalogFlInp() && icaVw.getSelectFl()) {
          icaVw.setCatalogSkuNumInp(catalogSkuNum);
          pForm.setNeedToSaveFl(true);
        }
      }
    }
    return ae;
  }

  public static ActionErrors propagateDistId(HttpServletRequest request,
                                             ActionForm form) throws Exception {
    ActionErrors ae = new ActionErrors();
    StoreItemCatalogMgrForm pForm = (StoreItemCatalogMgrForm) form;
    HttpSession session = request.getSession();
    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    Distributor distEjb = factory.getDistributorAPI();
    ItemCatalogAggrViewVector icaVwV = pForm.getItemAggrVector();
    DistributorData dD = pForm.getDistDummy();
    if (dD == null) {
      if (icaVwV != null) {
        for (Iterator iter = icaVwV.iterator(); iter.hasNext(); ) {
          ItemCatalogAggrView icaVw = (ItemCatalogAggrView) iter.next();
          if (icaVw.getCatalogFlInp() && icaVw.getSelectFl()) {
            icaVw.setDistIdInp(null);
            icaVw.setDistName(null);
            icaVw.setDistStatus(null);
            pForm.setNeedToSaveFl(true);
          }
        }
      }
    } else {    	
      int distId = dD.getBusEntity().getBusEntityId();
      boolean distInactive = dD.getBusEntity().getBusEntityStatusCd().equals(RefCodeNames.DISTRIBUTOR_STATUS_CD.INACTIVE);
  	  String distName = dD.getBusEntity().getShortDesc();
  	  if (distInactive){
  		String errorMess = "Cannot set inactive distributor: " + distName;
  		ae.add("error", new ActionError("error.simpleGenericError", errorMess));
        return ae;
  	  }
      
      if (icaVwV != null) {
        IdVector itemIdV = new IdVector();
        for (Iterator iter = icaVwV.iterator(); iter.hasNext(); ) {
          ItemCatalogAggrView icaVw = (ItemCatalogAggrView) iter.next();
          if (icaVw.getCatalogFlInp() && icaVw.getSelectFl()) {
            icaVw.setDistIdInp(Integer.toString(distId));
            icaVw.setDistName(distName);
            icaVw.setDistStatus(dD.getBusEntity().getBusEntityStatusCd());
            pForm.setNeedToSaveFl(true);
            int itemId = icaVw.getItemId();
            itemIdV.add(new Integer(itemId));
          }
        }

        //Set dist sku info
        if (pForm.getDistSkuAutoFl()) {
          DistItemView distItemVw = null;
          try {
            DistItemViewVector distItemVwV =
              distEjb.getDistItems(distId, itemIdV);
            if (distItemVwV != null && distItemVwV.size() > 0) {
              for (Iterator iter = icaVwV.iterator(); iter.hasNext(); ) {
                ItemCatalogAggrView icaVw = (ItemCatalogAggrView) iter.next();
                if (icaVw.getCatalogFlInp() && icaVw.getSelectFl()) {
                  int itemId = icaVw.getItemId();
                  for (Iterator iter1 = distItemVwV.iterator(); iter1.hasNext(); ) {
                    distItemVw = (DistItemView) iter1.next();
                    if (itemId == distItemVw.getItemId()) {
                      assigntDistItemInfo(request, form, distItemVw, icaVw);
                    }
                  }
                }
              }
            }
          } catch (Exception exc) {
            distItemVw = null;
            exc.printStackTrace();
          }
        }
      }
    }

    return ae;
  }

   public static void  propagateDistId(BusEntityData busEntityData,ItemCatalogAggrView itemCatalogAggrView ,HttpServletRequest request,
                                             ActionForm form) throws Exception {
     ActionErrors ae = new ActionErrors();
    StoreItemCatalogMgrForm pForm = (StoreItemCatalogMgrForm) form;
    HttpSession session = request.getSession();
    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    Distributor distEjb = factory.getDistributorAPI();
    BusEntityData busEntityD = busEntityData;
    ItemCatalogAggrView icaVw =  itemCatalogAggrView;
    if (busEntityData == null) {
         if (icaVw.getCatalogFlInp() && icaVw.getSelectFl()) {
            icaVw.setDistIdInp(null);
            icaVw.setDistName(null);
            pForm.setNeedToSaveFl(true);
          }
        }
    else {

       int distId = busEntityData.getBusEntityId();
       String distName = busEntityData.getShortDesc();
       IdVector itemIdV = new IdVector();
        if (icaVw.getCatalogFlInp() && icaVw.getSelectFl()) {
            icaVw.setDistIdInp(Integer.toString(distId));
            icaVw.setDistName(distName);
            pForm.setNeedToSaveFl(true);
            int itemId = icaVw.getItemId();
            itemIdV.add(new Integer(itemId));
          }


        //Set dist sku info
        if (pForm.getDistSkuAutoFl()) {
          DistItemView distItemVw = null;
          try {
            DistItemViewVector distItemVwV =distEjb.getDistItems(distId, itemIdV);
            if (distItemVwV != null && distItemVwV.size() > 0) {
              if (icaVw.getCatalogFlInp() && icaVw.getSelectFl()) {
                  int itemId = icaVw.getItemId();
                  for (Iterator iter1 = distItemVwV.iterator(); iter1.hasNext(); ) {
                    distItemVw = (DistItemView) iter1.next();
                    if (itemId == distItemVw.getItemId()) {
                      assigntDistItemInfo(request, form, distItemVw, icaVw);
                    }
                  }
                }

            }
          } catch (Exception exc) {
            distItemVw = null;
            exc.printStackTrace();
          }
        }
      }
    }


    public static ActionErrors setDefaulDistForCatalogs(HttpServletRequest request,
                                                        ActionForm form) throws Exception {


        ActionErrors ae = new ActionErrors();
        StoreItemCatalogMgrForm pForm = (StoreItemCatalogMgrForm) form;
        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        CatalogInformation catinfo=factory.getCatalogInformationAPI();
        ItemCatalogAggrViewVector icaVwV = pForm.getItemAggrVector();
        CatalogAssocDataVector catAssocDV=null;
        Hashtable thBusEntity=new Hashtable();

        if (icaVwV != null) {

            IdVector errLimit=new IdVector();
            IdVector errEmpty=new IdVector();
            String except="";
            boolean currErrLimitFl=false;
            boolean currErrEmptyFl=false;

            if(icaVwV!=null)
                for(Iterator it=icaVwV.iterator();it.hasNext();)
                {
                    ItemCatalogAggrView icaVw=(ItemCatalogAggrView)it.next();
                    if (icaVw.getCatalogFlInp() && icaVw.getSelectFl())
                    {
                        BusEntityData  busEntityD=null;
                        int catId=icaVw.getCatalogId();
                        int orderGuideId=icaVw.getOrderGuideId();
                        log.info("Catatalog->"+catId +" OrderGuide->"+orderGuideId);

                        if(!thBusEntity.containsKey(String.valueOf(catId)))
                        {    catAssocDV = catinfo.getCatalogAssoc(catId,0,RefCodeNames.CATALOG_ASSOC_CD.CATALOG_MAIN_DISTRIBUTOR);

                            if(catAssocDV!=null&&catAssocDV.size()>1){
                                Integer catIdInt = new Integer(catId);
                                if(errLimit.indexOf(catIdInt)==-1)
                                errLimit.add(catIdInt);
                                currErrLimitFl=true;
                            }
                            if(catAssocDV!=null&&catAssocDV.size()==0) {
                                Integer catIdInt = new Integer(catId);
                                if(errEmpty.indexOf(catIdInt)==-1)
                                errEmpty.add(new Integer(catId));
                                currErrEmptyFl=true;
                            }


                            if(currErrEmptyFl||currErrLimitFl||catAssocDV==null)
                            {
                                propagateDistId(null,icaVw,request,form);
                            }
                            else
                            {

                                CatalogAssocData catAssocD= (CatalogAssocData)catAssocDV.get(0);
                                busEntityD = catinfo.getBusEntity(catAssocD.getBusEntityId());

                                try {
                                    propagateDistId(busEntityD,icaVw,request,form);
                                    if(busEntityD!=null)
                                    {
                                    thBusEntity.put(String.valueOf(catId),busEntityD);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    except=except.concat(e.getMessage()+" ");
                                }

                            }


              } else{
                            try {
                                propagateDistId((BusEntityData) thBusEntity.get(String.valueOf(catId)),icaVw,request,form);
                            } catch (Exception e) {
                                e.printStackTrace();
                                except=except.concat(e.getMessage()+" ");
                            }
                        }

                        currErrLimitFl=false;
                        currErrEmptyFl=false;
                    }

                }

            if(errLimit.size()>0)
            {
                String errId=new String();
                for(int i=0;i<errLimit.size();i++)
                {
                    if(i>0)  errId=errId.concat(", ");
                    errId=errId.concat(String.valueOf(errLimit.get(i)));
                }
                ae.add("error1",new ActionError("error.simpleGenericError","Catalog "+errId+" has multiple main distributors ."));
            }

            if(errEmpty.size()>0)
            {
                String errId=new String();
                for(int i=0;i<errEmpty.size();i++)
                {
                    if(i>0)  errId=errId.concat(", ");
                    errId=errId.concat(String.valueOf(errEmpty.get(i)));
                }
                ae.add("error2",new ActionError("error.simpleGenericError", "Catalog "+errId+" doesn't have main distributor"));
            }
            if(!except.equals(""))  ae.add("error3",new ActionError("error.simpleGenericError",except));

        }


        return ae;
    }
  public static ActionErrors propagateCost(HttpServletRequest request,
                                           ActionForm form) throws Exception {
    ActionErrors ae = new ActionErrors();
    StoreItemCatalogMgrForm pForm = (StoreItemCatalogMgrForm) form;
    String costS = pForm.getCostDummy();
    if (!Utility.isSet(costS)) {
      String errorMess = "No distriutor cost value provided";
      ae.add("error", new ActionError("error.simpleGenericError", errorMess));
      return ae;
    }
    double costDb = -1;
    try {
      costDb = Double.parseDouble(costS);
    } catch (Exception exc) {}
    if (costDb < 0) {
      String errorMess = "Illegal distriutor cost value: " + costS;
      ae.add("error", new ActionError("error.simpleGenericError", errorMess));
      return ae;
    }
    ItemCatalogAggrViewVector icaVwV = pForm.getItemAggrVector();
    if (icaVwV != null) {
      for (Iterator iter = icaVwV.iterator(); iter.hasNext(); ) {
        ItemCatalogAggrView icaVw = (ItemCatalogAggrView) iter.next();
        if (icaVw.getContractFlInp() && icaVw.getSelectFl()) {
          icaVw.setCostInp(costS);
          pForm.setNeedToSaveFl(true);
        }
      }
    }
    return ae;
  }

  public static ActionErrors propagatePrice(HttpServletRequest request,
                                            ActionForm form) throws Exception {
    ActionErrors ae = new ActionErrors();
    StoreItemCatalogMgrForm pForm = (StoreItemCatalogMgrForm) form;
    String priceS = pForm.getPriceDummy();
    if (!Utility.isSet(priceS)) {
      String errorMess = "No price value provided";
      ae.add("error", new ActionError("error.simpleGenericError", errorMess));
      return ae;
    }
    double priceDb = -1;
    try {
      priceDb = Double.parseDouble(priceS);
    } catch (Exception exc) {}
    if (priceDb < 0) {
      String errorMess = "Illegal price value: " + priceS;
      ae.add("error", new ActionError("error.simpleGenericError", errorMess));
      return ae;
    }
    ItemCatalogAggrViewVector icaVwV = pForm.getItemAggrVector();
    if (icaVwV != null) {
      for (Iterator iter = icaVwV.iterator(); iter.hasNext(); ) {
        ItemCatalogAggrView icaVw = (ItemCatalogAggrView) iter.next();
        if (icaVw.getContractFlInp() && icaVw.getSelectFl()) {
          icaVw.setPriceInp(priceS);
          pForm.setNeedToSaveFl(true);
        }
      }
    }
    return ae;
  }

  public static ActionErrors propagateBaseCost(HttpServletRequest request,
                                               ActionForm form) throws
    Exception {
    ActionErrors ae = new ActionErrors();
    StoreItemCatalogMgrForm pForm = (StoreItemCatalogMgrForm) form;
    String baseCostS = pForm.getBaseCostDummy();
    if (!Utility.isSet(baseCostS)) {
      String errorMess = "No base cost value provided";
      ae.add("error", new ActionError("error.simpleGenericError", errorMess));
      return ae;
    }
    double baseCostDb = -1;
    try {
      baseCostDb = Double.parseDouble(baseCostS);
    } catch (Exception exc) {}
    if (baseCostDb < 0) {
      String errorMess = "Illegal base cost value: " + baseCostS;
      ae.add("error", new ActionError("error.simpleGenericError", errorMess));
      return ae;
    }
    ItemCatalogAggrViewVector icaVwV = pForm.getItemAggrVector();
    if (icaVwV != null) {
      for (Iterator iter = icaVwV.iterator(); iter.hasNext(); ) {
        ItemCatalogAggrView icaVw = (ItemCatalogAggrView) iter.next();
        if (icaVw.getContractFlInp() && icaVw.getSelectFl()) {
          icaVw.setBaseCostInp(baseCostS);
          pForm.setNeedToSaveFl(true);
        }
      }
    }
    return ae;
  }

  public static ActionErrors propagateDistSkuNum(HttpServletRequest request,
                                                 ActionForm form) throws
    Exception {
    ActionErrors ae = new ActionErrors();
    StoreItemCatalogMgrForm pForm = (StoreItemCatalogMgrForm) form;
    String distSkuNum = pForm.getDistSkuNumDummy();
    ItemCatalogAggrViewVector icaVwV = pForm.getItemAggrVector();
    if (icaVwV != null) {
      for (Iterator iter = icaVwV.iterator(); iter.hasNext(); ) {
        ItemCatalogAggrView icaVw = (ItemCatalogAggrView) iter.next();
        if (icaVw.getCatalogFlInp() && icaVw.getSelectFl()) {
          icaVw.setDistSkuNumInp(distSkuNum);
          pForm.setNeedToSaveFl(true);
        }
      }
    }
    return ae;
  }

  public static ActionErrors propagateDistSkuPack(HttpServletRequest request,
                                                  ActionForm form) throws
    Exception {
    ActionErrors ae = new ActionErrors();
    StoreItemCatalogMgrForm pForm = (StoreItemCatalogMgrForm) form;
    String distSkuPack = pForm.getDistSkuPackDummy();
    ItemCatalogAggrViewVector icaVwV = pForm.getItemAggrVector();
    if (icaVwV != null) {
      for (Iterator iter = icaVwV.iterator(); iter.hasNext(); ) {
        ItemCatalogAggrView icaVw = (ItemCatalogAggrView) iter.next();
        if (icaVw.getCatalogFlInp() && icaVw.getSelectFl()) {
          icaVw.setDistSkuPackInp(distSkuPack);
          pForm.setNeedToSaveFl(true);
        }
      }
    }
    return ae;
  }

  public static ActionErrors propagateDistSkuUom(HttpServletRequest request,
                                                 ActionForm form) throws
    Exception {
    ActionErrors ae = new ActionErrors();
    StoreItemCatalogMgrForm pForm = (StoreItemCatalogMgrForm) form;
    String distSkuUom = pForm.getDistSkuUomDummy();
    ItemCatalogAggrViewVector icaVwV = pForm.getItemAggrVector();
    if (icaVwV != null) {
      for (Iterator iter = icaVwV.iterator(); iter.hasNext(); ) {
        ItemCatalogAggrView icaVw = (ItemCatalogAggrView) iter.next();
        if (icaVw.getCatalogFlInp() && icaVw.getSelectFl()) {
          icaVw.setDistSkuUomInp(distSkuUom);
          pForm.setNeedToSaveFl(true);
        }
      }
    }
    return ae;
  }

  public static ActionErrors propagateDistConvers(HttpServletRequest request,
                                                  ActionForm form) throws
    Exception {
    ActionErrors ae = new ActionErrors();
    StoreItemCatalogMgrForm pForm = (StoreItemCatalogMgrForm) form;
    String distUomMultS = pForm.getDistConversDummy();
    if (Utility.isSet(distUomMultS)) {
      int distUomMult = 0;
      try {
        distUomMult = Integer.parseInt(distUomMultS);
      } catch (Exception exc) {}
      if (distUomMult <= 0) {
        String errorMess = "Illegal distributor uom multiplier: " +
                           distUomMultS + " (should be positive integer)";
        ae.add("error", new ActionError("error.simpleGenericError", errorMess));
      }
    }
    ItemCatalogAggrViewVector icaVwV = pForm.getItemAggrVector();
    if (icaVwV != null) {
      for (Iterator iter = icaVwV.iterator(); iter.hasNext(); ) {
        ItemCatalogAggrView icaVw = (ItemCatalogAggrView) iter.next();
        if (icaVw.getCatalogFlInp() && icaVw.getSelectFl()) {
          icaVw.setDistConversInp(distUomMultS);
          pForm.setNeedToSaveFl(true);
        }
      }
    }
    return ae;
  }

  public static ActionErrors propagateGenManufId(HttpServletRequest request,
                                                 ActionForm form) throws
    Exception {
    ActionErrors ae = new ActionErrors();
    StoreItemCatalogMgrForm pForm = (StoreItemCatalogMgrForm) form;
    String genManufIdS = pForm.getGenManufIdDummy();
    int genManufId = 0;
    try {
      genManufId = Integer.parseInt(genManufIdS);
    } catch (Exception exc) {
      String errorMess = "Generic manufacturer id is not numeric: " +
                         genManufIdS;
      ae.add("error", new ActionError("error.simpleGenericError", errorMess));
      return ae;
    }
    BusEntityDataVector manufDV = pForm.getStoreManufBusEntitys();
    String genManufName = null;
    if (manufDV != null && genManufId != 0) {
      for (Iterator iter = manufDV.iterator(); iter.hasNext(); ) {
    	  BusEntityData mD = (BusEntityData) iter.next();
        if (mD.getBusEntityId() == genManufId) {
          genManufName = mD.getShortDesc();
          break;
        }
      }
      if (genManufName == null) {
        String errorMess = "Generic manufactruer not found. Manufacturer id: " +
                           genManufIdS;
        ae.add("error", new ActionError("error.simpleGenericError", errorMess));
        return ae;
      }
    }
    ItemCatalogAggrViewVector icaVwV = pForm.getItemAggrVector();
    if (icaVwV != null) {
      for (Iterator iter = icaVwV.iterator(); iter.hasNext(); ) {
        ItemCatalogAggrView icaVw = (ItemCatalogAggrView) iter.next();
        if (icaVw.getCatalogFlInp() && icaVw.getSelectFl()) {
          icaVw.setGenManufIdInp("" + genManufId);
          icaVw.setGenManufName(genManufName);
          pForm.setNeedToSaveFl(true);
        }
      }
    }
    return ae;
  }

    public static ActionErrors propagateMultiproductId(HttpServletRequest request,
        ActionForm form) throws Exception {

        ActionErrors ae = new ActionErrors();
        StoreItemCatalogMgrForm pForm = (StoreItemCatalogMgrForm) form;
        String multiproductIdS = pForm.getMultiproductIdDummy();
        int multiproductId = 0;
        try {
          multiproductId = Integer.parseInt(multiproductIdS);
        } catch (Exception exc) {
          String errorMess = "Multiproduct id is not numeric: " + multiproductIdS;
          ae.add("error", new ActionError("error.simpleGenericError", errorMess));
          return ae;
        }
        MultiproductViewVector multiproductVV = pForm.getStoreMultiproducts();
        String multiproductName = null;
        if (multiproductVV != null) {
          for (Iterator iter = multiproductVV.iterator(); iter.hasNext(); ) {
              MultiproductView multiproductView = (MultiproductView) iter.next();
            if (multiproductView.getMultiproductId() == multiproductId) {
              multiproductName = multiproductView.getMultiproductName();
              break;
            }
          }
        }
        if (multiproductName == null) {
          String errorMess = (multiproductId == 0) ?
                             "No multi product selected" :
                             "Multi product not found. Multi product id: " + multiproductIdS;
          ae.add("error", new ActionError("error.simpleGenericError", errorMess));
          return ae;
        }
        ItemCatalogAggrViewVector icaVwV = pForm.getItemAggrVector();
        if (icaVwV != null) {
          for (Iterator iter = icaVwV.iterator(); iter.hasNext(); ) {
            ItemCatalogAggrView icaVw = (ItemCatalogAggrView) iter.next();
            if (icaVw.getCatalogFlInp() && icaVw.getSelectFl()) {
              icaVw.setMultiproductIdInp("" + multiproductId);
              icaVw.setMultiproductName(multiproductName);
              pForm.setNeedToSaveFl(true);
            }
          }
        }
        return ae;
    }


  public static ActionErrors propagateGenManufSkuNum(HttpServletRequest request,
    ActionForm form) throws Exception {
    ActionErrors ae = new ActionErrors();
    StoreItemCatalogMgrForm pForm = (StoreItemCatalogMgrForm) form;
    String genManufSkuNum = pForm.getGenManufSkuNumDummy();
    ItemCatalogAggrViewVector icaVwV = pForm.getItemAggrVector();
    if (icaVwV != null) {
      for (Iterator iter = icaVwV.iterator(); iter.hasNext(); ) {
        ItemCatalogAggrView icaVw = (ItemCatalogAggrView) iter.next();
        if (icaVw.getCatalogFlInp() && icaVw.getSelectFl()) {
          icaVw.setGenManufSkuNumInp(genManufSkuNum);
          pForm.setNeedToSaveFl(true);
        }
      }
    }
    return ae;
  }

  public static ActionErrors actionAdd(HttpServletRequest request,
                                       ActionForm form) throws Exception {
    ActionErrors ae = new ActionErrors();
    StoreItemCatalogMgrForm pForm = (StoreItemCatalogMgrForm) form;
    String genManufSkuNum = pForm.getGenManufSkuNumDummy();
    ItemCatalogAggrViewVector icaVwV = pForm.getItemAggrVector();
    boolean catalogFl = pForm.getTickItemsToCatalog();
    boolean contractFl = pForm.getTickItemsToContract();
    boolean orderGuideFl = pForm.getTickItemsToOrderGuide();
    if (icaVwV != null) {
      for (Iterator iter = icaVwV.iterator(); iter.hasNext(); ) {
        ItemCatalogAggrView icaVw = (ItemCatalogAggrView) iter.next();
        if (icaVw.getSelectFl()) {
          if (catalogFl) {
            icaVw.setCatalogFlInp(true);
            pForm.setNeedToSaveFl(true);
          }
          if (contractFl && icaVw.getCatalogFlInp()) {
            icaVw.setContractFlInp(true);
            pForm.setNeedToSaveFl(true);
          }
          if (orderGuideFl && icaVw.getContractFlInp()) {
            icaVw.setOrderGuideFlInp(true);
            icaVw.setDistSPLFlInp(true);
            pForm.setNeedToSaveFl(true);
          }
        }
      }
    }
    return ae;
  }

  public static ActionErrors cpoC(HttpServletRequest request,
                                  ActionForm form) throws Exception {
    ActionErrors ae = new ActionErrors();
    StoreItemCatalogMgrForm pForm = (StoreItemCatalogMgrForm) form;
    String itemIdS = pForm.getItemCpoId();
    int itemId = Integer.parseInt(itemIdS);
    String catalogIdS = pForm.getCatalogCpoId();
    int catalogId = Integer.parseInt(catalogIdS);
    ItemCatalogAggrViewVector icaVwV = pForm.getItemAggrVector();
    if (icaVwV != null) {
      for (Iterator iter = icaVwV.iterator(); iter.hasNext(); ) {
        ItemCatalogAggrView icaVw = (ItemCatalogAggrView) iter.next();
        if (catalogId == icaVw.getCatalogId() && itemId == icaVw.getItemId()) {
          boolean catalogFl = !icaVw.getCatalogFlInp();
          icaVw.setCatalogFlInp(!icaVw.getCatalogFlInp());
          pForm.setNeedToSaveFl(true);
          if (!catalogFl) {
            icaVw.setContractFlInp(false);
            icaVw.setOrderGuideFlInp(false);
          }
        }
      }
    }
    return ae;
  }

  public static ActionErrors cpoP(HttpServletRequest request,
                                  ActionForm form) throws Exception {
    ActionErrors ae = new ActionErrors();
    StoreItemCatalogMgrForm pForm = (StoreItemCatalogMgrForm) form;
    String itemIdS = pForm.getItemCpoId();
    int itemId = Integer.parseInt(itemIdS);
    String catalogIdS = pForm.getCatalogCpoId();
    int catalogId = Integer.parseInt(catalogIdS);
    ItemCatalogAggrViewVector icaVwV = pForm.getItemAggrVector();
    if (icaVwV != null) {
      for (Iterator iter = icaVwV.iterator(); iter.hasNext(); ) {
        ItemCatalogAggrView icaVw = (ItemCatalogAggrView) iter.next();
        if (catalogId == icaVw.getCatalogId() && itemId == icaVw.getItemId()) {
          boolean contractFl = !icaVw.getContractFlInp();
          if (contractFl) {
            icaVw.setCatalogFlInp(true);
          }
          icaVw.setContractFlInp(contractFl);
          pForm.setNeedToSaveFl(true);
          if (!contractFl) {
            icaVw.setOrderGuideFlInp(false);
          }
        }
      }
    }
    return ae;
  }

  public static ActionErrors cpoOg(HttpServletRequest request,
                                   ActionForm form) throws Exception {
    ActionErrors ae = new ActionErrors();
    StoreItemCatalogMgrForm pForm = (StoreItemCatalogMgrForm) form;
    String itemIdS = pForm.getItemCpoId();
    int itemId = Integer.parseInt(itemIdS);
    String catalogIdS = pForm.getCatalogCpoId();
    int catalogId = Integer.parseInt(catalogIdS);
    String ogIdS = pForm.getOgCpoId();
    int ogId = Integer.parseInt(ogIdS);
    ItemCatalogAggrViewVector icaVwV = pForm.getItemAggrVector();
    if (icaVwV != null) {
      boolean flag = false;
      for (Iterator iter = icaVwV.iterator(); iter.hasNext(); ) {
        ItemCatalogAggrView icaVw = (ItemCatalogAggrView) iter.next();
        if (catalogId == icaVw.getCatalogId() &&
            itemId == icaVw.getItemId() &&
            ogId == icaVw.getOrderGuideId()) {
          boolean ogFl = !icaVw.getOrderGuideFlInp();
          if (ogFl) {
            flag = true;
          }
          icaVw.setOrderGuideFlInp(ogFl);
          pForm.setNeedToSaveFl(true);
        }
      }
      if (flag) {
        for (Iterator iter = icaVwV.iterator(); iter.hasNext(); ) {
          ItemCatalogAggrView icaVw = (ItemCatalogAggrView) iter.next();
          if (catalogId == icaVw.getCatalogId()) {
            icaVw.setCatalogFlInp(true);
            icaVw.setContractFlInp(true);
          }
        }
      }
    }
    return ae;
  }

  public static ActionErrors actionRemove(HttpServletRequest request,
                                          ActionForm form) throws Exception {
    ActionErrors ae = new ActionErrors();
    StoreItemCatalogMgrForm pForm = (StoreItemCatalogMgrForm) form;
    String genManufSkuNum = pForm.getGenManufSkuNumDummy();
    ItemCatalogAggrViewVector icaVwV = pForm.getItemAggrVector();
    boolean catalogFl = pForm.getTickItemsToCatalog();
    boolean contractFl = pForm.getTickItemsToContract();
    boolean orderGuideFl = pForm.getTickItemsToOrderGuide();
    if (icaVwV != null) {
      for (Iterator iter = icaVwV.iterator(); iter.hasNext(); ) {
        ItemCatalogAggrView icaVw = (ItemCatalogAggrView) iter.next();
        if (icaVw.getSelectFl()) {
          if (orderGuideFl) {
            icaVw.setOrderGuideFlInp(false);
            pForm.setNeedToSaveFl(true);
          }
          if (contractFl) {
            icaVw.setContractFlInp(false);
            icaVw.setOrderGuideFlInp(false);
            pForm.setNeedToSaveFl(true);
          }
          if (catalogFl) {
            icaVw.setContractFlInp(false);
            icaVw.setOrderGuideFlInp(false);
            icaVw.setCatalogFlInp(false);
            pForm.setNeedToSaveFl(true);
          }
        }
      }
    }
    return ae;
  }


  private static ActionErrors readStoreManufacturers(Manufacturer manufEjb,
    StoreItemCatalogMgrForm pForm, int storeId) throws Exception {

    ActionErrors ae = new ActionErrors();

    BusEntitySearchCriteria besc = new BusEntitySearchCriteria();
    IdVector storeIdAsV = new IdVector();
    storeIdAsV.add(new Integer(storeId));
    besc.setStoreBusEntityIds(storeIdAsV);

    BusEntityDataVector manufBusEntityDV = manufEjb.getManufacturerBusEntitiesByCriteria(besc);
    if (manufBusEntityDV.size() > 1) {
      Object[] manufA = manufBusEntityDV.toArray();
      for (int ii = 0; ii < manufA.length - 1; ii++) {
        boolean exitFl = true;
        for (int jj = 0; jj < manufA.length - ii - 1; jj++) {
        	BusEntityData mD1 = (BusEntityData) manufA[jj];
        	BusEntityData mD2 = (BusEntityData) manufA[jj + 1];
          int comp = mD1.getShortDesc().compareToIgnoreCase(mD2.getShortDesc());
          if (comp > 0) {
            manufA[jj] = mD2;
            manufA[jj + 1] = mD1;
            exitFl = false;
          }
        }
        if (exitFl)break;
      }
      manufBusEntityDV = new BusEntityDataVector();
      for (int ii = 0; ii < manufA.length; ii++) {
    	  BusEntityData mD1 = (BusEntityData) manufA[ii];
    	  manufBusEntityDV.add(mD1);
      }
    }

    pForm.setStoreManufBusEntitys(manufBusEntityDV);

    return ae;
  }

  public static ActionErrors setView(HttpServletRequest request,
                                     ActionForm form) throws Exception {
    ActionErrors ae = new ActionErrors();
    StoreItemCatalogMgrForm pForm = (StoreItemCatalogMgrForm) form;
    pForm.setShowBaseCostFlDef(pForm.getShowBaseCostFl());
    pForm.setShowDistSkuNumFlDef(pForm.getShowDistSkuNumFl());
    pForm.setShowCustSkuNumFlDef(pForm.getShowCustSkuNumFl());
    pForm.setShowServiceFeeCodeFlDef(pForm.getShowServiceFeeCodeFl());
    pForm.setShowDistSplFlDef(pForm.getShowDistSplFl());
    pForm.setShowTaxExemptFlDef(pForm.getShowTaxExemptFl());
    pForm.setShowSpecialPermissionFlDef(pForm.getShowSpecialPermissionFl());
    pForm.setShowDistUomPackFlDef(pForm.getShowDistUomPackFl());
    pForm.setShowGenManufFlDef(pForm.getShowGenManufFl());
    pForm.setShowGenManufSkuNumFlDef(pForm.getShowGenManufSkuNumFl());
    pForm.setShowMultiproductsFlDef(pForm.getShowMultiproductsFl());

    return ae;
  }

  public static ActionErrors resetView(HttpServletRequest request,
                                       ActionForm form) throws Exception {
    ActionErrors ae = new ActionErrors();
    StoreItemCatalogMgrForm pForm = (StoreItemCatalogMgrForm) form;
    pForm.setShowBaseCostFl(pForm.getShowBaseCostFlDef());
    pForm.setShowDistSkuNumFl(pForm.getShowDistSkuNumFlDef());
    pForm.setShowDistSplFl(pForm.getShowDistSplFlDef());
    pForm.setShowTaxExemptFl(pForm.getShowTaxExemptFlDef());
    pForm.setShowSpecialPermissionFl(pForm.getShowSpecialPermissionFlDef());
    pForm.setShowCustSkuNumFl(pForm.getShowCustSkuNumFlDef());
    pForm.setShowServiceFeeCodeFl(pForm.getShowServiceFeeCodeFlDef());
    pForm.setShowDistUomPackFl(pForm.getShowDistUomPackFlDef());
    pForm.setShowGenManufFl(pForm.getShowGenManufFlDef());
    pForm.setShowGenManufSkuNumFl(pForm.getShowGenManufSkuNumFlDef());
    pForm.setShowMultiproductsFl(pForm.getShowMultiproductsFlDef());
    return ae;
  }

  public static ActionErrors saveSelectBox(HttpServletRequest request,
                                           ActionForm form) throws Exception {
    ActionErrors ae = new ActionErrors();
    StoreItemCatalogMgrForm pForm = (StoreItemCatalogMgrForm) form;
    ItemCatalogAggrViewVector icaVwV = pForm.getItemAggrVector();
    pForm.setSelectedLines(new String[0]);
    if (icaVwV != null) {
      int size = 0;
      for (Iterator iter = icaVwV.iterator(); iter.hasNext(); ) {
        ItemCatalogAggrView icaVw = (ItemCatalogAggrView) iter.next();
        if (icaVw.getSelectFl()) {
          size++;
        }
      }
      String[] selectedLines = new String[size];
      int ind = 0;
      for (Iterator iter = icaVwV.iterator(); iter.hasNext(); ) {
        ItemCatalogAggrView icaVw = (ItemCatalogAggrView) iter.next();
        if (icaVw.getSelectFl()) {
          int catalogId = icaVw.getCatalogId();
          int itemId = icaVw.getItemId();
          int orderGuideId = icaVw.getOrderGuideId();
          selectedLines[ind++] = itemId + ":" + catalogId + ":" + orderGuideId;
        }
      }
      pForm.setSelectedLines(selectedLines);
    }
    return ae;
  }

  public static ActionErrors clearSelectBox(HttpServletRequest request,
                                            ActionForm form) throws Exception {
    ActionErrors ae = new ActionErrors();
    StoreItemCatalogMgrForm pForm = (StoreItemCatalogMgrForm) form;
    pForm.setSelectedLines(new String[0]);
    return ae;
  }

  public static ActionErrors resetSelectBox(HttpServletRequest request,
                                            ActionForm form) throws Exception {

    ActionErrors ae = new ActionErrors();
    StoreItemCatalogMgrForm pForm = (StoreItemCatalogMgrForm) form;
    ItemCatalogAggrViewVector icaVwV = pForm.getItemAggrVector();
    if (icaVwV != null) {
      String[] selectedLines = pForm.getSelectedLines();
      for (Iterator iter = icaVwV.iterator(); iter.hasNext(); ) {
        ItemCatalogAggrView icaVw = (ItemCatalogAggrView) iter.next();
        icaVw.setSelectFl(false);
        int itemId = icaVw.getItemId();
        int catalogId = icaVw.getCatalogId();
        int orderGuideId = icaVw.getOrderGuideId();
        String key = itemId + ":" + catalogId + ":" + orderGuideId;
        for (int ii = 0; ii < selectedLines.length; ii++) {
          if (key.equals(selectedLines[ii])) {
            icaVw.setSelectFl(true);
            break;
          }
        }
      }
    }
    pForm.setSelectedLines(new String[0]);
    return ae;
  }

  public static ActionErrors clearFilters(HttpServletRequest request,
                                          ActionForm form) throws Exception {
    ActionErrors ae = new ActionErrors();
    StoreItemCatalogMgrForm pForm = (StoreItemCatalogMgrForm) form;
    pForm.setCurrManagingItem(null);
    pForm.setAccountFilter(null);
    pForm.setCatalogFilter(null);
    pForm.setDistFilter(null);
    pForm.setManufFilter(null);
    pForm.setItemFilter(null);
    pForm.setDistDummy(null);
    clearInputFields(request, form);
    pForm.setNeedToSaveFl(false);
    pForm.setCategories(new ItemDataVector());
    pForm.setSelectedLines(new String[0]);
    pForm.setLastSortField(null);
//    pForm.setStoreMultiproducts(new MultiproductViewVector());
    clearCatalogFilter(request);
    return ae;
  }


  public static ActionErrors clearCatalogFilter(HttpServletRequest request) throws
    Exception {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    session.removeAttribute("catalogListSelectedCatItems");
    session.removeAttribute("catalogListSelectedItemContract");
    return ae;
  }

    public static ActionErrors clearOrderGuideFilter(HttpServletRequest request)
        throws Exception {
        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        session.removeAttribute("orderGuideCatalogListSelectedCatItems");
        return ae;
    }

  public static ActionErrors clearInputFields(HttpServletRequest request,
                                              ActionForm form) throws Exception {
    ActionErrors ae = new ActionErrors();
    StoreItemCatalogMgrForm pForm = (StoreItemCatalogMgrForm) form;
    pForm.setCategoryIdDummy("");
    pForm.setCostCenterIdDummy("");
    //pForm.setDistDummy(null);
    pForm.setCostDummy("");
    pForm.setPriceDummy("");
    pForm.setBaseCostDummy("");
    pForm.setCatalogSkuNumDummy("");
    pForm.setDistSkuNumDummy("");
    pForm.setDistSkuPackDummy("");
    pForm.setDistSkuUomDummy("");
    pForm.setDistConversDummy("");
    pForm.setGenManufIdDummy("");
    pForm.setGenManufSkuNumDummy("");
    pForm.setStoreCategoryId(0);
    pForm.setMultiproductIdDummy("");
    pForm.setMultiproductNameDummy("");
    return ae;
  }

  public static ActionErrors save(HttpServletRequest request,
                                  ActionForm form) throws Exception {
    ActionErrors ae = new ActionErrors();
    StoreItemCatalogMgrForm pForm = (StoreItemCatalogMgrForm) form;
    ItemCatalogAggrViewVector icaVwV = pForm.getItemAggrVector();
    HttpSession session = request.getSession();
    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);

    Distributor distEjb = factory.getDistributorAPI();
    Catalog catalogEjb = factory.getCatalogAPI();
    HashMap distHM = new HashMap();
    HashSet errors = new HashSet();
    IdVector distIdV = new IdVector();
    //Map<Integer, Set<Integer>> multiProdToCategoryMap = new HashMap<Integer, Set<Integer>>();
    //Set<Integer> categIds = null;
    if (icaVwV == null || icaVwV.size() == 0) {
      String errorMess = "Nothing to save";
      ae.add("error", new ActionError("error.simpleGenericError", errorMess));
      return ae;
    }
    for (Iterator iter = icaVwV.iterator(); iter.hasNext(); ) {
        ItemCatalogAggrView icaVw = (ItemCatalogAggrView) iter.next();
        icaVw.setCatalogSkuNumInp(Utility.trimRight(icaVw.getCatalogSkuNumInp(), " "));
        icaVw.setDistSkuNumInp(Utility.trimRight(icaVw.getDistSkuNumInp(), " "));
        icaVw.setGenManufSkuNumInp(Utility.trimRight(icaVw.getGenManufSkuNumInp(), " "));

/*        Integer mpId = new Integer(icaVw.getMultiproductId());
        if (icaVw.getMultiproductId() > 0) {
	        if ( multiProdToCategoryMap.containsKey(mpId)){
	        	categIds = multiProdToCategoryMap.get(mpId);
	        } else {
	        	categIds = new HashSet<Integer>();
	        	multiProdToCategoryMap.put(mpId, categIds);
	        }
	        categIds.add(icaVw.getCategoryId());
        }*/
    }
    Map inpMultiProdToCategoryMap = new HashMap();
    Map categories = null;
    for (Iterator iter = icaVwV.iterator(); iter.hasNext(); ) {
      ItemCatalogAggrView icaVw = (ItemCatalogAggrView) iter.next();
      int itemId = icaVw.getItemId();
      int catalogId = icaVw.getCatalogId();
      Integer catalogIdI = new Integer(catalogId);

      //String categoryIdInpS = icaVw.getCategoryIdInp();
      //String costCenterIdInpS = icaVw.getCostCenterIdInp();
      if (!icaVw.getCatalogFlInp()) {
        continue;
      }

      String itemStatusCd = icaVw.getItemStatusCd();
      if(!itemStatusCd.equalsIgnoreCase("active")){
    	  String errorMess = "Inactive Item. Sku Number: " + icaVw.getSkuNum();
    	  errors.add(errorMess);
      }

      String distIdInpS = icaVw.getDistIdInp();
      int distId = 0;
      if (Utility.isSet(distIdInpS)) {
        try {
          distId = Integer.parseInt(distIdInpS);
        } catch (Exception exc) {}
        if (distId <= 0) {
          String errorMess = "Illegal distributor id: " + distIdInpS;
          errors.add(errorMess);
        }
      }

      String multiproductIdInpS = icaVw.getMultiproductIdInp();
      int multiproductId = 0;
      if (Utility.isSet(multiproductIdInpS)) {
          try {
              multiproductId = Integer.parseInt(multiproductIdInpS);
          } catch (Exception exc) {}
          if (multiproductId < 0) {
            String errorMess = "Illegal multiproduct id: " + multiproductIdInpS;
            errors.add(errorMess);
          } /*else {
        	  categories = (Map)inpMultiProdToCategoryMap.get(new Integer(multiproductId));
              if (!Utility.isSet(categories)){
            	 categories = new HashMap(); 
                 inpMultiProdToCategoryMap.put(new Integer(multiproductId), categories); 
              }
              categories.put(icaVw.getCategoryId(), icaVw.getCategoryName());
              
          }*/
      }

      if (distId > 0) {
    	boolean distInactive = icaVw.getDistStatus().equals(RefCodeNames.DISTRIBUTOR_STATUS_CD.INACTIVE);
    	if (distInactive){
    		String errorMess = "Item with inactive distributor assigned should not be added to catalog. Sku Number: " + icaVw.getSkuNum();
            errors.add(errorMess);
            continue;
    	}
        String distConvS = icaVw.getDistConversInp();
        String distSkuNumInpS = Utility.strNN(icaVw.getDistSkuNumInp());
        String distSkuPackInpS = Utility.strNN(icaVw.getDistSkuPackInp());
        String distSkuUomInpS = Utility.strNN(icaVw.getDistSkuUomInp());
        boolean distSPLFlInp = icaVw.getDistSPLFlInp();
        BigDecimal distConvInp = new BigDecimal(1);
        if (Utility.isSet(distConvS)) {
          try {
            distConvInp = new BigDecimal(Double.parseDouble(distConvS));
          } catch (Exception exc) {
            errors.add("Illegal distibutor uom multiplier: " + distConvS);
            continue;
          }
        }
        if (distConvInp.doubleValue() <= 0) {
          String errorMess = "Illegal distributor uom multiplier: " + distConvS;
          errors.add(errorMess);
          continue;
        }
        String distSkuNum = Utility.strNN(icaVw.getDistSkuNum());
        String distSkuPack = Utility.strNN(icaVw.getDistSkuPack());
        String distSkuUom = Utility.strNN(icaVw.getDistSkuUom());
        boolean distSPLFl = icaVw.getDistSPLFl();
        BigDecimal distConv = icaVw.getDistConvers();
        if (distConv == null) distConv = new BigDecimal(1);
        if (distSkuNum.equals(distSkuNumInpS) &&
            distSPLFlInp == distSPLFl &&
            distSkuUom.equals(distSkuUomInpS) &&
            distSkuPack.equals(distSkuPackInpS) &&
            distConv.subtract(distConvInp).abs().doubleValue() < 0.0001) {
          continue;
        }
        String distItemKey = distId + ":" + itemId;
        ItemCatalogAggrView icaVw1 = (ItemCatalogAggrView) distHM.get(
          distItemKey);
        if (icaVw1 == null) {
          distHM.put(distItemKey, icaVw);
          distIdV.add(new Integer(distId));
        } else {
          if (!distSkuNumInpS.equals(Utility.strNN(icaVw1.getDistSkuNumInp()))) {
            String errorMess =
              "Distributor sku# inconsistency. DistributorId: " +
              distId;
            if (!errors.contains(errorMess)) {
              errors.add(errorMess);
            }
          }
          if (!distSkuPackInpS.equals(Utility.strNN(icaVw1.getDistSkuPackInp()))) {
            String errorMess =
              "Distributor sku pack inconsistency. DistributorId: " +
              distId;
            if (!errors.contains(errorMess)) {
              errors.add(errorMess);
            }
          }
          if (!distSkuUomInpS.equals(Utility.strNN(icaVw1.getDistSkuUomInp()))) {
            String errorMess =
              "Distributor sku uom inconsistency. DistributorId: " +
              distId;
            if (!errors.contains(errorMess)) {
              errors.add(errorMess);
            }
          }
          if (!distSPLFlInp == icaVw1.getDistSPLFlInp()) {
            String errorMess =
              "Distributor sku SPL inconsistency. DistributorId: " + distId;
            if (!errors.contains(errorMess)) {
              errors.add(errorMess);
            }
          }
          BigDecimal prevDistConv = icaVw1.getDistConvers();
          if (prevDistConv == null) prevDistConv = new BigDecimal(1);
          if (distConv.subtract(prevDistConv).abs().doubleValue() > 0.0001) {
            String errorMess =
              "Distributor sku uom multiplier inconsistency. DistributorId: " +
              distId;
            if (!errors.contains(errorMess)) {
              errors.add(errorMess);
            }
          }
        }
      }
    }
    HashMap distManufHM = new HashMap();
    for (Iterator iter = icaVwV.iterator(); iter.hasNext(); ) {
      ItemCatalogAggrView icaVw = (ItemCatalogAggrView) iter.next();
      int itemId = icaVw.getItemId();
      int catalogId = icaVw.getCatalogId();
      Integer catalogIdI = new Integer(catalogId);
      
      // validation on item exists in super or sub catalog when adding or removing item from catalog.
      // if item not exists in super catalog(account) when adding item to a catalog(shopping), set error message
      // if item exists in sub-catalog(shopping) when delete item from a catalog(account), set error message
      if (icaVw.getCatalogFlInp()) {// add new or update existing item
    	  CatalogInformation catinfo = factory.getCatalogInformationAPI();
          CatalogData superCatalogD = catinfo.getSuperCatalog(catalogId);
          if (superCatalogD != null){
        	  int superCatalogId = superCatalogD.getCatalogId();
        	  boolean checkItemInSuperCatalog = true;
        	  for (Iterator iter1 = icaVwV.iterator(); iter1.hasNext(); ) {
            	  ItemCatalogAggrView icaVw1 = (ItemCatalogAggrView) iter1.next();
            	  if (icaVw1.getCatalogId() == superCatalogId){
            		  if (icaVw1.getCatalogFlInp())
            			  checkItemInSuperCatalog = false;
            		  break;
            	  }        	  
              }
        	  
        	  if (checkItemInSuperCatalog){
    	    	  CatalogStructureData csd = null;
    	          try {
    	              csd = catalogEjb.getCatalogStructureData(superCatalogId, itemId);
    	          } catch (DataNotFoundException e) {
    	              e.printStackTrace();
    	          }
    	          if (csd == null){
    	        	  String errorMess = "Item " + icaVw.getSkuNum() 
    	        	  	+ " can not be saved into catalog: " + catalogId + " since it does not exist in master catalog: " + superCatalogId;
    	        	  if (!errors.contains(errorMess)) {
    	        		  errors.add(errorMess);
    	        	  }
    	          }
        	  }
          }
      }else{ // might need to delete item
    	  CatalogStructureData csd = null;
    	  try {
              csd = catalogEjb.getCatalogStructureData(catalogId, itemId);
          } catch (DataNotFoundException e) {
          }
          if (csd != null){
        	  IdVector subCatalogIdV = catalogEjb.getSubCatalogs(catalogId, itemId);
        	  if (subCatalogIdV.size() > 0){
        		  String subCatalogStr = "";
        		  int count = 0;

        		  // need to exclude the item that included to the removed list
        		  for (int i = 0; i < subCatalogIdV.size() ; i++){
        			  int subCatalogId = ((Integer) subCatalogIdV.get(i)).intValue();
        			  boolean checkItemInSubCatalog = true;
        			  for (Iterator iter1 = icaVwV.iterator(); iter1.hasNext(); ) {
                    	  ItemCatalogAggrView icaVw1 = (ItemCatalogAggrView) iter1.next();
                    	  if (icaVw1.getCatalogId() == subCatalogId){
                    		  if (!icaVw1.getCatalogFlInp()){
                    			  checkItemInSubCatalog = false;                    			  
                    		  }
                    		  break;
                    	  }
                      }
        			  
        			  if (checkItemInSubCatalog){
	        			  count++;
	        			  if (count > 10){
	        				  subCatalogStr += ",...";
	        				  break;
	        			  }else{
	        				  subCatalogStr += subCatalogIdV.get(i);
	        				  if (i != subCatalogIdV.size()-1)
	        					  subCatalogStr += ", ";
	        			  }   
        			  }
        		  }
        		  if (count != 0){
	        		  String errorMess = "Item " + icaVw.getSkuNum() 
		        	  	+ " can not be removed from catalog: " + catalogId + " since it exists in following sub-catalog: " + subCatalogStr;
		        	  if (!errors.contains(errorMess)) {
		        		  errors.add(errorMess);
		        	  }
        		  }
        	  }
          }
    	  
      }

      if (!icaVw.getCatalogFlInp()) {
        continue;
      }

      int multiproductId = (Utility.isSet(icaVw.getMultiproductIdInp()) ) ? Integer.parseInt(icaVw.getMultiproductIdInp()) : 0;
      String categoryIdS = icaVw.getCategoryIdInp();
      int categoryId = 0;
      try {
        categoryId = Integer.parseInt(categoryIdS);        
      } catch (Exception exc) {}
      if (categoryId <= 0) {
        String errorMess = "No category defined in catalog: " +
                           icaVw.getCatalogId();
        if (!errors.contains(errorMess)) {
          errors.add(errorMess);
        }
      } else {
    	  if (multiproductId >0 ){
	    	  categories= (Map)inpMultiProdToCategoryMap.get(new Integer(multiproductId));
	          if (!Utility.isSet(categories)){
	           	  categories = new HashMap();
	        	  inpMultiProdToCategoryMap.put(new Integer(multiproductId),categories ); 
	          } 
	          categories.put(new Integer(categoryId), icaVw.getCategoryName());
    	  }
      }

      String distIdInpS = icaVw.getDistIdInp();
      int distId = 0;
      if (Utility.isSet(distIdInpS)) {
        try {
          distId = Integer.parseInt(distIdInpS);
        } catch (Exception exc) {}
        if (distId <= 0) {
          continue;
        }
      }

      String genManufIdInpS = icaVw.getGenManufIdInp();
      int genManufId = 0;
      if (Utility.isSet(genManufIdInpS)) {
        try {
          genManufId = Integer.parseInt(genManufIdInpS);
        } catch (Exception exc) {
          String errorMess = "Illegal generic manufacturer id: " +
                             genManufIdInpS;
          if (!errors.contains(errorMess)) {
            errors.add(errorMess);
          }
          continue;
        }
      }
      if (genManufId == 0) {
        continue;
      }
      int oldDistId = icaVw.getDistId();
      String genManufSkuNum = Utility.strNN(icaVw.getGenManufSkuNumInp());
      int oldGenManufId = icaVw.getGenManufId();
      String oldGenManufSkuNum = Utility.strNN(icaVw.getGenManufSkuNum());
      if (oldGenManufId == genManufId &&
          genManufSkuNum.equals(oldGenManufSkuNum)) {
        continue;
      }
      //Integer distIdI = new Integer(distId);
      String distItemKey = distId + ":" + itemId;
      if (distManufHM.get(distItemKey) == null) {
        distManufHM.put(distItemKey, icaVw);
      } else {
        ItemCatalogAggrView icaVw1 = (ItemCatalogAggrView) distManufHM.get(
          distItemKey);
        String prevGenManufIdS = icaVw1.getGenManufIdInp();
        int prevGenManufId = 0;
        if (Utility.isSet(prevGenManufIdS)) {
          prevGenManufId = Integer.parseInt(prevGenManufIdS);
        }
        if (prevGenManufId != genManufId) {
          String errorMess =
            "Genenric manufacturer inconsistency. DistributorId: " +
            distId;
          if (!errors.contains(errorMess)) {
            errors.add(errorMess);
          }
          continue;
        }
        String prevGenManufSkuNum = Utility.strNN(icaVw1.getGenManufSkuNumInp());
        String oldPrevGenManufSkuNum = Utility.strNN(icaVw1.getGenManufSkuNum());
        if (oldPrevGenManufSkuNum.equals(prevGenManufSkuNum)) {
          continue;
        }
        if (!prevGenManufSkuNum.equals(genManufSkuNum)) {
          String errorMess =
            "Genenric manufacturer inconsistency. DistributorId: " +
            distId;
          if (!errors.contains(errorMess)) {
            errors.add(errorMess);
          }
          continue;
        }
      }
      if (icaVw.getContractFlInp()) {
        String costInpS = icaVw.getCostInp();
        if (Utility.isSet(costInpS)) {
          try {
            Double.parseDouble(costInpS);
          } catch (Exception exc) {
            String errorMess = "Illegal cost value: " + costInpS;
            if (!errors.contains(errorMess)) {
              errors.add(errorMess);
            }
          }
        }
        String priceInpS = icaVw.getPriceInp();
        if (Utility.isSet(priceInpS)) {
          try {
            Double.parseDouble(priceInpS);
          } catch (Exception exc) {
            String errorMess = "Illegal price value: " + priceInpS;
            if (!errors.contains(errorMess)) {
              errors.add(errorMess);
            }
          }
        }
        String baseCostInpS = icaVw.getBaseCostInp();
        if (Utility.isSet(baseCostInpS)) {
          try {
            Double.parseDouble(baseCostInpS);
          } catch (Exception exc) {
            String errorMess = "Illegal base cost value: " + baseCostInpS;
            if (!errors.contains(errorMess)) {
              errors.add(errorMess);
            }
          }
        }
      }
    }
    Collection distColl = distHM.values();
    BusEntityDataVector manufBusEntityDV = pForm.getStoreManufBusEntitys();
    DistributorDataVector distDV = distEjb.getDistributorCollectionByIdList(
      distIdV);

    for (Iterator iter = distColl.iterator(); iter.hasNext(); ) {
      ItemCatalogAggrView icaVw = (ItemCatalogAggrView) iter.next();
      String genManufIdInpS = icaVw.getGenManufIdInp();
      try {
        int genManufId = Integer.parseInt(genManufIdInpS);
        if (genManufId > 0) {
          boolean foundFl = false;
          for (Iterator iter1 = manufBusEntityDV.iterator(); iter1.hasNext(); ) {
        	  BusEntityData mD = (BusEntityData) iter1.next();
            if (mD.getBusEntityId() == genManufId) {
              foundFl = true;
              break;
            }
          }
          if (!foundFl) {
            String errorMess =
              "No generic manufacturer found. Manufacturer id: " + genManufId;
            if (!errors.contains(errorMess)) {
              errors.add(errorMess);
            }
          }
        }
      } catch (Exception exc) {}
      String distIdS = icaVw.getDistIdInp();
      if (Utility.isSet(distIdS)) {
        try {
          int distId = Integer.parseInt(distIdS);
          if (distId > 0) {
            boolean foundFl = false;
            for (Iterator iter1 = distDV.iterator(); iter1.hasNext(); ) {
              DistributorData dD = (DistributorData) iter1.next();
              if (dD.getBusEntity().getBusEntityId() == distId) {
                foundFl = true;
                break;
              }
            }
            if (!foundFl) {
              String errorMess = "No distributor found. Distributor id: " +
                                 distId;
              errors.add(errorMess);
            }
          }
        } catch (Exception exc) {}
      }
    }
    
    validateMultiproduct(request, icaVwV, inpMultiProdToCategoryMap,  errors);
    if (errors.size() > 0) {
      int ind = 0;
      for (Iterator iter = errors.iterator(); iter.hasNext(); ) {
        String errorMess = (String) iter.next();
        ae.add("error" + (++ind),
               new ActionError("error.simpleGenericError", errorMess));
      }
      return ae;
    }
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.
      APP_USER);
    String userName = appUser.getUser().getUserName();
    try {
      catalogEjb.saveItemCatalogMgrSet1(icaVwV, userName,RefCodeNames.ITEM_TYPE_CD.PRODUCT);
    } catch (Exception exc) {
      String errorMess = exc.getMessage();
      int ind1 = -1;
      int ind2 = -1;
      if (errorMess == null)throw exc;
      ind1 = errorMess.indexOf("^clw^");
      if (ind1 >= 0) {
        ind2 = errorMess.indexOf("^clw^", ind1 + 3);
        if (ind2 > 0) {
          errorMess = errorMess.substring(ind1 + 5, ind2);
          ae.add("error", new ActionError("error.simpleGenericError", errorMess));
          return ae;
        }
      }
      throw exc;
    }

    ae = reload(request, form);
    if (ae.size() > 0) {
      return ae;
    }

    return ae;
  }

  private static void validateMultiproduct(HttpServletRequest request, ItemCatalogAggrViewVector icaVwV, Map<Integer, Set<String>> inpMultiProdToCategoryMap, HashSet errors) throws Exception {
	  log.info("validateMultiproduct() =====> BEGIN.");
	  // for Catalog-Item view
	  Integer catalogId = ((ItemCatalogAggrView)icaVwV.get(0)).getCatalogId();
	  
	  CatalogInformation catalogEjb = APIAccess.getAPIAccess().getCatalogInformationAPI();
	  Map errMap = new HashMap();
	  if (!inpMultiProdToCategoryMap.isEmpty()){
		  
		  	 Map inpCategoryToItemMap = new HashMap();
		  	 Set itemsToUpd = null;
		  	 for (Object icaVw: icaVwV) {
		  		Integer categoryIdInp = Integer.parseInt(((ItemCatalogAggrView)icaVw).getCategoryIdInp());
		  		itemsToUpd = (Set)inpCategoryToItemMap.get(categoryIdInp); 
		  		if (!Utility.isSet(itemsToUpd)) {
		  			itemsToUpd = new HashSet();
		  			inpCategoryToItemMap.put(categoryIdInp, itemsToUpd);
		  		}
		  		itemsToUpd.add(icaVw);
		  	 }
		 
			 Set inpMultiproducts = inpMultiProdToCategoryMap.keySet();
	//		 if (Utility.isSet(newMultiproducts)) {
				 // Look for existing associations in DB
				 Map<Integer, Set<String>> baseMultiProdToCategoryMap =  catalogEjb.getMultiproductToCategoryMap(catalogId, inpMultiproducts);
				 
				 if (!baseMultiProdToCategoryMap.isEmpty()) {
					 Iterator it = baseMultiProdToCategoryMap.keySet().iterator();
					 while (it.hasNext()){
						 Integer key = (Integer) it.next();
						 log.info("validateMultiproduct() =====>key = " + key);						 
						 Map categories = (Map)baseMultiProdToCategoryMap.get(key);
						 Map inpCategory = (Map)inpMultiProdToCategoryMap.get(key);
						 if (inpCategory != null) {
							 if (!inpCategory.isEmpty() && inpCategory.size() > 1){
								 log.info("validateMultiproduct() =====>inpCategory = " + inpCategory.toString());						 
								 String errorMess = ClwI18nUtil.getMessage(request,"error.store.item.multiproduct.multipleCategoryAssoc",new Object[] {key.toString(), inpCategory.values().toString()});
							     errors.add(errorMess);
							 } else if ( !inpCategory.isEmpty() && inpCategory.size() == 1 &&
									 	 (categories.keySet().containsAll(inpCategory.keySet()) || !isAnyItemOfDifferentCategoryForMultiproduct(catalogId, (Set)inpCategoryToItemMap.get((Integer)(((inpCategory.keySet()).toArray())[0])), key))) {
								 continue;
							 } else {
								 log.info("validateMultiproduct() =====>inpCategory = " + inpCategory.toString());						 
								 String errorMess = ClwI18nUtil.getMessage(request,"error.store.item.multiproduct.otherCategoryAssoc",new Object[] {key.toString(), categories.values().toString(), inpCategory.values().toString()});
							     errors.add(errorMess);
							 }
						 }
					 }
				 } else {
					 Iterator it = inpMultiProdToCategoryMap.keySet().iterator();
					 while (it.hasNext()){
						 Integer key = (Integer) it.next();
						 Map inpCategory = (Map)inpMultiProdToCategoryMap.get(key);
						 if (!inpCategory.isEmpty() && inpCategory.size() > 1){
							 String errorMess = ClwI18nUtil.getMessage(request,"error.store.item.multiproduct.otherCategoryAssoc",new Object[] {key.toString(), inpCategory.values().toString()});
						     errors.add(errorMess);
						 }
					 }
				 }
				 
			 //}
	  }
  }
  private static boolean isAnyItemOfDifferentCategoryForMultiproduct(int catalogId, Set items, Integer multiproductId) throws Exception {
	  CatalogInformation catalogEjb = APIAccess.getAPIAccess().getCatalogInformationAPI();
	  IdVector baseCategories = new IdVector();
	  IdVector itemIds = new IdVector();
	  for (Object item : items){
		  baseCategories.add(((ItemCatalogAggrView)item).getCategoryId());
		  itemIds.add(((ItemCatalogAggrView)item).getItemId());
	  }
	  
	  IdVector baseItemIds =  catalogEjb.getMultiproductItems(catalogId, multiproductId, baseCategories);
	  log.info("isAnyItemOfDifferentCategoryForMultiproduct() =====> baseItemIds=" + baseItemIds);
	  if (Utility.isSet(itemIds) && itemIds.containsAll(baseItemIds) ){
		  return false;
	  }
	  return true;
  }


  public static ActionErrors addCategoryToCatalogs(HttpServletRequest request,
    ActionForm form) throws Exception {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    String page =
      (String) session.getAttribute(SessionAttributes.ITEM_MANAGER_PAGE.
                                    CURRENT_PAGE);
    if (page == null || page.equals("itemcontract")) {
      ae = addCategoryToSelectedCatalogs(request, form);
    } else if (page.equals("cat-item")) {
      ae = addCategoryToTheCatalog(request, form);
    }

    return ae;
  }

  public static ActionErrors addCategoryToSelectedCatalogs(HttpServletRequest
    request,
    ActionForm form) throws Exception {
    ActionErrors ae = new ActionErrors();
    StoreItemCatalogMgrForm pForm = (StoreItemCatalogMgrForm) form;
    HttpSession session = request.getSession();
    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();

    ItemCatalogAggrViewVector icaVwV = pForm.getItemAggrVector();
    IdVector allCatalogIdV = new IdVector();
    IdVector catalogIdV = new IdVector();
    if (icaVwV != null) {
      for (Iterator iter = icaVwV.iterator(); iter.hasNext(); ) {
        ItemCatalogAggrView icaVw = (ItemCatalogAggrView) iter.next();
        int catalogId = icaVw.getCatalogId();
        allCatalogIdV.add(new Integer(catalogId));
        if (icaVw.getSelectFl()) {
          catalogIdV.add(new Integer(catalogId));
        }
      }
    }

    int categoryId = pForm.getStoreCategoryId();
    if (categoryId == 0) {
      String errorMess = "No category selected";
      ae.add("error", new ActionError("error.simpleGenericError", errorMess));
      return ae;
    }
    if (catalogIdV.size() == 0) {
      String errorMess = "No catalogs selected";
      ae.add("error", new ActionError("error.simpleGenericError", errorMess));
      return ae;
    }

    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.
      APP_USER);
    String userName = appUser.getUser().getUserName();
    StoreData storeD = appUser.getUserStore();
    int storeId = storeD.getBusEntity().getBusEntityId();
    Catalog catalogEjb = factory.getCatalogAPI();
    catalogEjb.addCategoryWithParentCategoriesToCatalogs(catalogIdV, categoryId, userName);

    int storeCatalogId = catalogInfEjb.getStoreCatalogId(storeId);
    ItemDataVector imDV = catalogInfEjb.getCatalogCategories(storeCatalogId,
      allCatalogIdV, pForm.getAllowMixedCategoryAndItemUnderSameParent());
    pForm.setCategories(imDV);
    pForm.setStoreCategoryId(0);
    return ae;
  }

  public static ActionErrors addCategoryToTheCatalog(HttpServletRequest request,
    ActionForm form) throws Exception {
    ActionErrors ae = new ActionErrors();
    StoreItemCatalogMgrForm pForm = (StoreItemCatalogMgrForm) form;
    HttpSession session = request.getSession();
    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();

    CatalogData catalogD = null;
    IdVector catalogIdV = new IdVector();
    int catalogId = 0;
    CatalogDataVector catalogDV = pForm.getCatalogFilter();
    if (catalogDV != null && catalogDV.size() > 0) {
      catalogD = (CatalogData) catalogDV.get(0);
      catalogId = catalogD.getCatalogId();
      catalogIdV.add(new Integer(catalogId));
    }


    int categoryId = pForm.getStoreCategoryId();
    if (categoryId == 0) {
      String errorMess = "No category selected";
      ae.add("error", new ActionError("error.simpleGenericError", errorMess));
      return ae;
    }
    if (catalogIdV.size() == 0) {
      String errorMess = "No catalogs selected";
      ae.add("error", new ActionError("error.simpleGenericError", errorMess));
      return ae;
    }

    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.
      APP_USER);
    String userName = appUser.getUser().getUserName();

    Catalog catalogEjb = factory.getCatalogAPI();
    catalogEjb.addCategoryWithParentCategoriesToCatalogs(catalogIdV, categoryId, userName);

    //Get catalog categories
    CatalogCategoryDataVector categories = catalogInfEjb.getStoreCatalogCategories(catalogId);
    categories = sortCategories(categories);
    ItemDataVector categItemDV = new ItemDataVector();
    for (Iterator iter = categories.iterator(); iter.hasNext(); ) {
      CatalogCategoryData cD = (CatalogCategoryData) iter.next();
      categItemDV.add(cD.getItemData());
    }
    pForm.setCategories(categItemDV);


    pForm.setStoreCategoryId(0);
    return ae;
  }

  public static ActionErrors removeCategoryFromCatalogs(HttpServletRequest
    request,
    ActionForm form) throws Exception {
    ActionErrors ae = new ActionErrors();
    StoreItemCatalogMgrForm pForm = (StoreItemCatalogMgrForm) form;
    HttpSession session = request.getSession();
    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    Catalog catalogEjb = factory.getCatalogAPI();
    CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();

    ItemCatalogAggrViewVector icaVwV = pForm.getItemAggrVector();
    IdVector allCatalogIdV = new IdVector();
    IdVector catalogIdV = new IdVector();
    if (icaVwV != null) {
      for (Iterator iter = icaVwV.iterator(); iter.hasNext(); ) {
        ItemCatalogAggrView icaVw = (ItemCatalogAggrView) iter.next();
        int catalogId = icaVw.getCatalogId();
        allCatalogIdV.add(new Integer(catalogId));
        if (icaVw.getSelectFl()) {
          catalogIdV.add(new Integer(catalogId));
        }
      }
    }

    String categoryIdS = pForm.getCategoryIdDummy();
    int categoryId = 0;
    try {
      categoryId = Integer.parseInt(categoryIdS);
    } catch (Exception exc) {}
    if (categoryId == 0) {
      String errorMess = "No category selected";
      ae.add("error", new ActionError("error.simpleGenericError", errorMess));
      return ae;
    }
    if (catalogIdV.size() == 0) {
      String errorMess = "No catalogs selected";
      ae.add("error", new ActionError("error.simpleGenericError", errorMess));
      return ae;
    }

    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.
      APP_USER);
    String userName = appUser.getUser().getUserName();
    StoreData storeD = appUser.getUserStore();
    int storeId = storeD.getBusEntity().getBusEntityId();
    try {
      catalogEjb.removeCategoryFromCatalogs(categoryId, catalogIdV,RefCodeNames.ITEM_TYPE_CD.PRODUCT);
    } catch (Exception exc) {
      String errorMess = exc.getMessage();
      int ind1 = -1;
      int ind2 = -1;
      if (errorMess == null)throw exc;
      ind1 = errorMess.indexOf("^clw^");
      if (ind1 >= 0) {
        ind2 = errorMess.indexOf("^clw^", ind1 + 3);
        if (ind2 > 0) {
          errorMess = errorMess.substring(ind1 + 5, ind2);
          ae.add("error", new ActionError("error.simpleGenericError", errorMess));
          return ae;
        }
      }
      throw exc;
    }

    int storeCatalogId = catalogInfEjb.getStoreCatalogId(storeId);
    ItemDataVector imDV = catalogInfEjb.getCatalogCategories(storeCatalogId,
      allCatalogIdV, pForm.getAllowMixedCategoryAndItemUnderSameParent());
    pForm.setCategories(imDV);
    pForm.setStoreCategoryId(0);
    return ae;
  }


  public static ActionErrors cleanAssignDist(HttpServletRequest request,
                                             ActionForm form) throws Exception {
    ActionErrors ae = new ActionErrors();
    StoreItemCatalogMgrForm pForm = (StoreItemCatalogMgrForm) form;
    pForm.setDistDummy(null);
    return ae;
  }

  public static Element getDistItemInfo(HttpServletRequest request,
                                       ActionForm form) throws Exception {
    HttpSession session = request.getSession();

    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    Distributor distEjb = factory.getDistributorAPI();

    String index = request.getParameter("index");
    String itemIdS = request.getParameter("itemId");
    int itemId = 0;
    try {
      itemId = Integer.parseInt(itemIdS);
    } catch (Exception exc) {}

    String distIdS = request.getParameter("distId");
    int distId = 0;
    try {
      distId = Integer.parseInt(distIdS);
    } catch (Exception exc) {}

    if (distId <= 0 || itemId <= 0) {
      return null;
    }

    IdVector itemIdV = new IdVector();
    itemIdV.add(new Integer(itemId));

    DistItemViewVector distItemVwV = null;
    try {
      distItemVwV = distEjb.getDistItems(distId, itemIdV);
    } catch (Exception exc) {
      return null;
    }
    if (distItemVwV == null || distItemVwV.size() == 0) {
      return null;
    }
    DistItemView distItemVw = (DistItemView) distItemVwV.get(0);
    DocumentBuilderFactory docfactory = DocumentBuilderFactory.newInstance();
    docfactory.setNamespaceAware(true);
    DocumentBuilder docBuilder = null;

    try {
        docBuilder = docfactory.newDocumentBuilder();
    } catch (ParserConfigurationException ex) {
    }
    Document doc = docBuilder.getDOMImplementation().createDocument("", "DistItem", null);
    Element root = doc.getDocumentElement();
    Element node;

    node = doc.createElement("ItemId");
    node.appendChild(doc.createTextNode(String.valueOf(itemId)));
    root.appendChild(node);

    int skuNum = distItemVw.getSku();
    node = doc.createElement("Sku");
    node.appendChild(doc.createTextNode(String.valueOf(skuNum)));
    root.appendChild(node);

    int retDistId = distItemVw.getDistId();
    if (retDistId == 0) {
      retDistId = distId;
      distItemVw.setDistName(null);
    }
    node = doc.createElement("DistId");
    node.appendChild(doc.createTextNode(String.valueOf(retDistId)));
    root.appendChild(node);

    String distName = distItemVw.getDistName();
    if (Utility.isSet(distName)) {
      node = doc.createElement("DistName");
      node.appendChild(doc.createTextNode(String.valueOf(distName)));
      root.appendChild(node);
    }

    String distItemSku = distItemVw.getDistItemSku();
    if (Utility.isSet(distItemSku)) {
      node = doc.createElement("DistItemSku");
      node.appendChild(doc.createTextNode(String.valueOf(distItemSku)));
      root.appendChild(node);
    }

    String distItemPack = distItemVw.getDistItemPack();
    if (Utility.isSet(distItemPack)) {
      node = doc.createElement("DistItemPack");
      node.appendChild(doc.createTextNode(String.valueOf(distItemPack)));
      root.appendChild(node);
    }

    String distItemUom = distItemVw.getDistItemUom();
    if (Utility.isSet(distItemUom)) {
      node = doc.createElement("DistItemUom");
      node.appendChild(doc.createTextNode(String.valueOf(distItemUom)));
      root.appendChild(node);
    }

    int mfg1Id = distItemVw.getMfg1Id();
    if (mfg1Id > 0) {
      node = doc.createElement("Mfg1Id");
      node.appendChild(doc.createTextNode(String.valueOf(mfg1Id)));
      root.appendChild(node);
    }

    String mfg1Name = distItemVw.getMfg1Name();
    if (Utility.isSet(mfg1Name)) {
      node = doc.createElement("Mfg1Name");
      node.appendChild(doc.createTextNode(String.valueOf(mfg1Name)));
      root.appendChild(node);
    }

    int mult1Id = distItemVw.getMult1Id();
    if (mult1Id > 0) {
      node = doc.createElement("Mult1Id");
      node.appendChild(doc.createTextNode(String.valueOf(mult1Id)));
      root.appendChild(node);
    }

    String mult1Name = distItemVw.getMult1Name();
    if (Utility.isSet(mult1Name)) {
      node = doc.createElement("Mult1Name");
      node.appendChild(doc.createTextNode(String.valueOf(mult1Name)));
      root.appendChild(node);
    }

    String mfg1ItemSku = distItemVw.getMfg1ItemSku();
    if (Utility.isSet(mfg1ItemSku)) {
      node = doc.createElement("Mfg1ItemSku");
      node.appendChild(doc.createTextNode(String.valueOf(mfg1ItemSku)));
      root.appendChild(node);
    }

    BigDecimal distUomConvMultiplier = distItemVw.getDistUomConvMultiplier();
    if (Utility.isSet(distUomConvMultiplier)) {
      node = doc.createElement("DistUomConvMultiplier");
      node.appendChild(doc.createTextNode(String.valueOf(distUomConvMultiplier)));
      root.appendChild(node);
    }

    node = doc.createElement("index");
    node.appendChild(doc.createTextNode(index));
    root.appendChild(node);

   // String distItemVwXmlStr = root.toString();
   // distItemVwXmlStr = distItemVwXmlStr.replaceAll("\"", "&quot;");
   // return distItemVwXmlStr;
      return root;

  }

  private static void assigntDistItemInfo(HttpServletRequest request,
                                          ActionForm form,
                                          DistItemView distItemVw,
                                          ItemCatalogAggrView icaVw) throws
    Exception {
    HttpSession session = request.getSession();
    StoreItemCatalogMgrForm pForm = (StoreItemCatalogMgrForm) form;

    String distName = Utility.strNN(distItemVw.getDistName());
    icaVw.setDistName(distName);

    String distItemSku = Utility.strNN(distItemVw.getDistItemSku());
    if (pForm.getShowDistSkuNumFl()) {
      icaVw.setDistSkuNumInp(distItemSku);
    }

    String distItemPack = Utility.strNN(distItemVw.getDistItemPack());
    if (pForm.getShowDistUomPackFl()) {
      icaVw.setDistSkuPackInp(distItemPack);
    }

    String distItemUom = Utility.strNN(distItemVw.getDistItemUom());
    if (pForm.getShowDistUomPackFl()) {
      icaVw.setDistSkuUomInp(distItemUom);
    }

    int mfg1Id = distItemVw.getMfg1Id();
    if (pForm.getShowGenManufFl()) {
      if (mfg1Id > 0) {
        icaVw.setGenManufIdInp("" + mfg1Id);
      } else {
        icaVw.setGenManufIdInp("");
      }
    }

    int mult1Id = distItemVw.getMult1Id();
    if (pForm.getShowMultiproductsFl()) {
      if (mult1Id > 0) {
        icaVw.setMultiproductIdInp("" + mult1Id);
      } else {
        icaVw.setMultiproductIdInp("");
      }
    }

    String mfg1Name = Utility.strNN(distItemVw.getMfg1Name());
    if (pForm.getShowGenManufFl()) {
      icaVw.setGenManufName(mfg1Name);
    }

    String mult1Name = Utility.strNN(distItemVw.getMult1Name());
    if (pForm.getShowMultiproductsFl()) {
      icaVw.setMultiproductName(mult1Name);
    }

    String mfg1ItemSku = Utility.strNN(distItemVw.getMfg1ItemSku());
    if (pForm.getShowGenManufSkuNumFl()) {
      icaVw.setGenManufSkuNumInp(mfg1ItemSku);
    }

    BigDecimal distUomConvMultiplier = distItemVw.getDistUomConvMultiplier();
    if (pForm.getShowDistUomPackFl())
      if (Utility.isSet(distUomConvMultiplier)) {
        icaVw.setDistConversInp(String.valueOf(distUomConvMultiplier.toString()));
      } else {
        icaVw.setDistConversInp("");
      }
  }

    public static void clearOrderGuideOfItemCatalogAggrCollection(ItemCatalogAggrViewVector itemCatalogAggrVwV)
    {
        for (Iterator iter = itemCatalogAggrVwV.iterator(); iter.hasNext(); ) {
            ItemCatalogAggrView itemCatalogAggrV = (ItemCatalogAggrView) iter.next();
            itemCatalogAggrV.setOrderGuideId(0);
            itemCatalogAggrV.setOrderGuideName("");
        }
    }

    private static void setMultipleFilterByCatalog(ItemCatalogAggrViewVector itemCatalogAggrVwV) {

        IdVector useCatId = new IdVector();

        for (Iterator iter = itemCatalogAggrVwV.iterator(); iter.hasNext();) {

            ItemCatalogAggrView itemCatalogAggrV = (ItemCatalogAggrView) iter.next();
            Integer cId = new Integer(itemCatalogAggrV.getCatalogId());

            if (!useCatId.contains(cId)) {
                useCatId.add(cId);
            } else {
                iter.remove();
            }

        }
    }

    public static ActionErrors setViewOrderGuides(HttpServletRequest request, StoreItemCatalogMgrForm sForm) throws Exception {
        ActionErrors ae = new ActionErrors();
        try {
            HttpSession session = request.getSession();
            APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
            CatalogInformation catInfEjb = factory.getCatalogInformationAPI();
            ItemCatalogAggrViewVector itemCatAggrVwV=null;
            CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.
            APP_USER);
            StoreData storeD = appUser.getUserStore();
            int storeId = storeD.getBusEntity().getBusEntityId();

            if(sForm!=null)
            {
                if(sForm.getControlMultipleCatalogFl())
                {   //removes multiple record from collection .
                    itemCatAggrVwV =sForm.getItemAggrVector();
                    if(itemCatAggrVwV!=null){
                        setMultipleFilterByCatalog(itemCatAggrVwV);
                        clearOrderGuideOfItemCatalogAggrCollection(itemCatAggrVwV);
                    }
                }
                else
                {
                    int storeCatId = catInfEjb.getStoreCatalogId(storeId);
                    itemCatAggrVwV =
                            catInfEjb.getItemCatalogMgrSet(storeCatId,sForm.getCurrManagingItem().getProductId(),
                                Utility.toIdVector(sForm.getDistFilter()),Utility.toIdVector(sForm.getCatalogFilter()),
                                    Utility.toIdVector(sForm.getAccountFilter()));
                    sForm.setItemAggrVector(itemCatAggrVwV);
                }
            }
        } catch (APIServiceAccessException e) {
            ae=StringUtils.getUiErrorMess(e);
        } catch (RemoteException e) {
            ae=StringUtils.getUiErrorMess(e);
        }
        return ae;
    }

    private static ActionErrors checkDecimalPlaces(HttpServletRequest request, UploadSkuViewVector uploadSkuVV, StoreItemCatalogMgrForm pForm)
              throws Exception {
      HttpSession session = request.getSession();
      APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
      CatalogDataVector catalogDV = pForm.getCatalogFilter();
      ActionErrors ae =new ActionErrors();
      Contract contractEjb = null;
      String locale = "";
      try {
        contractEjb = factory.getContractAPI();
        if (catalogDV != null && catalogDV.size() > 0) {
          CatalogData catalogD = (CatalogData) catalogDV.get(0);
          int catalogId = catalogD.getCatalogId();
          ContractDataVector contractDV = contractEjb.getContractsByCatalog(catalogId);
          if (contractDV != null && contractDV.size() > 0) {
            ContractData contractD = (ContractData) contractDV.get(0);
            locale = contractD.getLocaleCd();
          }
        }
     }
      catch (APIServiceAccessException exc) {
         ae.add("error", new ActionError("error.systemError","No Contract Ejb access"));
         return ae;
      }


      int decimalPlaces = 0;
      try {
        com.cleanwise.service.api.session.Currency currencyEjb = factory.getCurrencyAPI();
//        currencyEjb = factory.getCurrencyAPI();
        decimalPlaces = currencyEjb.getDecimalPlacesForLocale(locale);
      }
      catch (APIServiceAccessException exc) {
        ae.add("error", new ActionError("error.systemError","No Currency Ejb access"));
        return ae;
      }

      if (uploadSkuVV == null || uploadSkuVV.size() == 0){
        return ae;
      }
      ArrayList errorL = new ArrayList();
      for (int i = 0; i < uploadSkuVV.size(); i++) {
        UploadSkuView uploadSku = (UploadSkuView) uploadSkuVV.get(i);
        UploadSkuData data = uploadSku.getUploadSku();
        String newCostS = data.getDistCost();
        String newPriceS = data.getCatalogPrice();
        String newBaseCostS = data.getBaseCost();
        String skuNum = data.getSkuNum();
        String err = isValidDecimalPlaces(request, newCostS, decimalPlaces, "cost", skuNum );
        if (Utility.isSet(err) && !errorL.contains(err)){
          errorL.add(err);
        }
        err = isValidDecimalPlaces(request, newPriceS, decimalPlaces, "price", skuNum );
        if (Utility.isSet(err) && !errorL.contains(err)){
          errorL.add(err);
        }
        err = isValidDecimalPlaces(request, newBaseCostS, decimalPlaces, "base cost", skuNum);
        if (Utility.isSet(err) && !errorL.contains(err)){
          errorL.add(err);
        }
      }
      if (errorL.size() > 0){
        String errorMess = "";
        for (int ii = 0; ii < errorL.size(); ii++) {
          if (ii != 0) {
            errorMess += "; ";
          }
          errorMess += errorL.get(ii);
        }
        ae.add("error",new ActionError("error.simpleGenericError",errorMess));
      }
      return ae;
    }

    private static String isValidDecimalPlaces(HttpServletRequest request, String valueS, int decimalPlaces, String pName, String pSkuNum ){
      String errMess = null;

      if (Utility.isSet(valueS)) {
        try {
          double db = Double.parseDouble(valueS.trim());
 //         BigDecimal newValue = new BigDecimal(db);
          //check decimal places
          BigDecimal valueBD = new BigDecimal(valueS.trim());
          if (valueBD.scale() > decimalPlaces) {
            errMess=ClwI18nUtil.getMessage(request,"shop.errors.tooManyDecimalPoints",new Object[]{pName,pSkuNum, new Integer(decimalPlaces)},false);
          }
        }
        catch (Exception exc) {
          errMess = "Illegal "+pName+" format. Sku num: " +pSkuNum+" "+pName+": "+ valueS;
        }
      }
      return errMess;
    }
}
