/*
 * StoreItemMgrLogic.java
 *
 * Created on March 17, 2005, 2:59 PM
 */
package com.cleanwise.view.logic;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Category;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.upload.FormFile;
import org.apache.xml.serialize.Method;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.Asset;
import com.cleanwise.service.api.session.Catalog;
import com.cleanwise.service.api.session.CatalogInformation;
import com.cleanwise.service.api.session.Content;
import com.cleanwise.service.api.session.Contract;
import com.cleanwise.service.api.session.Distributor;
import com.cleanwise.service.api.session.ItemInformation;
import com.cleanwise.service.api.session.ListService;
import com.cleanwise.service.api.session.Manufacturer;
import com.cleanwise.service.api.session.ProductInformation;
import com.cleanwise.service.api.session.PropertyService;
import com.cleanwise.service.api.session.Store;
import com.cleanwise.service.api.session.User;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.PropertyFieldUtil;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.SearchCriteria;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.BusEntityDataVector;
import com.cleanwise.service.api.value.BusEntityFieldsData;
import com.cleanwise.service.api.value.BusEntitySearchCriteria;
import com.cleanwise.service.api.value.CatalogCategoryData;
import com.cleanwise.service.api.value.CatalogCategoryDataVector;
import com.cleanwise.service.api.value.CatalogData;
import com.cleanwise.service.api.value.CatalogDataVector;
import com.cleanwise.service.api.value.ContentData;
import com.cleanwise.service.api.value.ContractData;
import com.cleanwise.service.api.value.ContractDataVector;
import com.cleanwise.service.api.value.ContractItemPriceView;
import com.cleanwise.service.api.value.ContractItemPriceViewVector;
import com.cleanwise.service.api.value.DistItemViewVector;
import com.cleanwise.service.api.value.DistributorData;
import com.cleanwise.service.api.value.DistributorDataVector;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.ItemData;
import com.cleanwise.service.api.value.ItemDataVector;
import com.cleanwise.service.api.value.ItemMappingData;
import com.cleanwise.service.api.value.ItemMappingDataVector;
import com.cleanwise.service.api.value.ItemMetaData;
import com.cleanwise.service.api.value.ItemMetaDataVector;
import com.cleanwise.service.api.value.ItemView;
import com.cleanwise.service.api.value.ItemViewVector;
import com.cleanwise.service.api.value.ManufacturerData;
import com.cleanwise.service.api.value.ManufacturerDataVector;
import com.cleanwise.service.api.value.MultiproductView;
import com.cleanwise.service.api.value.MultiproductViewVector;
import com.cleanwise.service.api.value.PairView;
import com.cleanwise.service.api.value.PairViewVector;
import com.cleanwise.service.api.value.ProductData;
import com.cleanwise.service.api.value.ProductDataVector;
import com.cleanwise.service.api.value.PropertyData;
import com.cleanwise.service.api.value.PropertyDataVector;
import com.cleanwise.service.api.value.RefCdData;
import com.cleanwise.service.api.value.RefCdDataVector;
import com.cleanwise.service.api.value.StagedItemAssocData;
import com.cleanwise.service.api.value.StagedItemAssocDataVector;
import com.cleanwise.service.api.value.StagedItemData;
import com.cleanwise.service.api.value.StoreData;
import com.cleanwise.service.api.value.StoreDataVector;
import com.cleanwise.service.api.value.StoreProductView;
import com.cleanwise.service.api.value.StoreProductViewVector;
import com.cleanwise.view.forms.LocateStoreItemForm;
import com.cleanwise.view.forms.StoreItemLoaderMgrForm;
import com.cleanwise.view.forms.StoreItemMgrForm;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.utils.CategoryUtil;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.ClwCustomizer;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.CurrencyFormat;
import com.cleanwise.view.utils.DisplayListSort;
import com.cleanwise.view.utils.HttpClientUtil;
import com.cleanwise.view.utils.SelectableObjects;
import com.cleanwise.view.utils.SessionTool;
import com.cleanwise.view.utils.StringUtils;

import java.util.Properties;

//new for the Enhancement STJ-3778: Begin
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.rpc.ServiceException;
import javax.xml.soap.SOAPException;
import java.net.MalformedURLException;

import com.cleanwise.service.api.util.DiverseyMSDSRetrieve;
//new for the Enhancement STJ-3778: End

import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;
import org.apache.log4j.*;

/**
 *
 * @author Ykupershmidt
 */
public class StoreItemMgrLogic {
	private static final Logger log =Logger.getLogger(StoreItemMgrLogic.class);
    private final static String THUMBNAILS_DIR = "thumbnails";
    private final static String TYPE_IMAGES = "images";
    private final static String TYPE_MSDS = "msds";
    private final static String TYPE_DED = "ded";
    private final static String TYPE_SPEC = "spec";


   static final Comparator BUS_ENTITY_ID_COMPARE = new Comparator() {
	    public int compare(Object o1, Object o2)
	    {
		int id1 = ((BusEntityData)o1).getBusEntityId();
		int id2 = ((BusEntityData)o2).getBusEntityId();
        return id2 - id1;
	    }
	};
     static final Comparator ITEM_MAPPING_BUS_ENTITY_ID_COMPARE = new Comparator() {
	    public int compare(Object o1, Object o2)
	    {
		int id1 = ((ItemMappingData)o1).getBusEntityId();
		int id2 = ((ItemMappingData)o2).getBusEntityId();
        return id2 - id1;
	    }
	};
    /** Creates a new instance of StoreItemMgrLogic */
    public StoreItemMgrLogic() {
    }

    public static ActionErrors init(HttpServletRequest request,
                                  StoreItemMgrForm pForm) throws Exception {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();
    Manufacturer manufEjb = factory.getManufacturerAPI();
    ProductInformation productInfoEjb = factory.getProductInformationAPI();

    ListService lsvc = factory.getListServiceAPI();
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.
      APP_USER);
    StoreData storeD = appUser.getUserStore();
    if (storeD == null) {
      ae.add("error",
             new ActionError("error.simpleGenericError", "No store info"));
      return ae;
    }

    int storeId = storeD.getBusEntity().getBusEntityId();
    if (storeD == null) {
      ae.add("error",
             new ActionError("error.simpleGenericError", "No store info"));
      return ae;
    }

    PropertyDataVector propertyDV = storeD.getMiscProperties();
    String autoSkuFlagS = null;
    for (Iterator iter = propertyDV.iterator(); iter.hasNext(); ) {
      PropertyData pD = (PropertyData) iter.next();
      if (RefCodeNames.PROPERTY_TYPE_CD.ERP_SYSTEM.equals(pD.
        getPropertyTypeCd())) {
        pForm.setErpSystem(pD.getValue());
      }
      if (RefCodeNames.PROPERTY_TYPE_CD.AUTO_SKU_ASSIGN.equals(pD.
        getPropertyTypeCd())) {
        autoSkuFlagS = pD.getValue();
      }
    }

    if (autoSkuFlagS == null) autoSkuFlagS = "";
    boolean autoSkuFlag = false;
    if (Utility.isTrue(autoSkuFlagS)) {
      autoSkuFlag = true;
    }
    pForm.setAutoSkuFlag(autoSkuFlag);

    // Set up the item status list.

    if (session.getAttribute("Item.status.vector") == null) {
            RefCdDataVector statusv =
                    lsvc.getRefCodesCollection("ITEM_STATUS_CD");
         session.setAttribute("Item.status.vector", statusv);
    }

//    if (request.getServletPath().indexOf("itemCategories") >=0 ||
//        request.getServletPath().indexOf("itemMultiproducts")>=0
//       ){

      HashMap categoriesForStoreFlag = (HashMap)session.getAttribute(Constants.CATEGORIES_FOR_STORE_FLAG);
      if (categoriesForStoreFlag == null) {
        categoriesForStoreFlag = new HashMap();
        categoriesForStoreFlag.put(String.valueOf(storeId), Boolean.FALSE);
      }
      Boolean isSetCategoriesForStore = (Boolean)categoriesForStoreFlag.get(String.valueOf(storeId));
      if (isSetCategoriesForStore == null ||
          (isSetCategoriesForStore != null && !isSetCategoriesForStore.booleanValue() )) {

        CatalogDataVector catalogDV = catalogInfEjb.
            getCatalogsCollectionByBusEntity(storeId,
                                             RefCodeNames.CATALOG_TYPE_CD.STORE);

        CatalogData cD = null;
        for (Iterator iter = catalogDV.iterator(); iter.hasNext(); ) {
          cD = (CatalogData) iter.next();
          if (RefCodeNames.CATALOG_STATUS_CD.ACTIVE.equals(cD.
              getCatalogStatusCd())) {
            break;
          }
        }
        if (cD == null) {
          ae.add("error", new ActionError("error.simpleGenericError",
                                          "No store catalog found"));
          return ae;
        }
        pForm.setStoreCatalogId(cD.getCatalogId());
        readStoreCategories(catalogInfEjb, pForm);
//        ItemDataVector majorCategoryDV = catalogInfEjb.getStoreMajorCategories(cD.getCatalogId());

//        pForm.setStoreMajorCategories(majorCategoryDV);

        readStoreManufacturers(manufEjb, pForm, storeId);
        readStoreMultiproducts(catalogInfEjb, pForm, storeId);
        categoriesForStoreFlag.put(String.valueOf(storeId), Boolean.TRUE);
        session.setAttribute(Constants.CATEGORIES_FOR_STORE_FLAG, categoriesForStoreFlag);
      }
//    }


      //Check stores for import
      Store storeEjb = factory.getStoreAPI();
      String userTypeCd = appUser.getUser().getUserTypeCd();
      BusEntityDataVector storeBusEntDV = new BusEntityDataVector();

      if (RefCodeNames.USER_TYPE_CD.ADMINISTRATOR.equals(userTypeCd) ||
              RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(userTypeCd)) {
          storeBusEntDV.addAll(storeEjb.getAllStoresBusEntityData(Store.ORDER_BY_NAME));
      } else {
          storeBusEntDV.addAll(appUser.getStores());
      }

      //add enterprise store for STORE_ADMINISTRATOR
      if(RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR.equals(userTypeCd)){

          //create criteria for searching
          BusEntitySearchCriteria criteria = new BusEntitySearchCriteria();
          criteria.addPropertyCriteria(RefCodeNames.PROPERTY_TYPE_CD.STORE_TYPE, RefCodeNames.STORE_TYPE_CD.ENTERPRISE);

          //get all ENTERPRISE stores
          StoreDataVector storeDataVector = storeEjb.getStoresByCriteria(criteria);
          Iterator itStore = storeDataVector.iterator();

          //add to store data vector
          while(itStore.hasNext()){
              StoreData storeData = (StoreData) itStore.next();
              storeBusEntDV.add(storeData.getBusEntity());
          }
      }

      pForm.setUserStoreEntities(storeBusEntDV);

      boolean allowImportFl = false;
      if (storeBusEntDV != null && storeBusEntDV.size() > 1){
          allowImportFl = true;
      }
      pForm.setAllowImporFl(allowImportFl);

      BusEntityDataVector certifiedCompanies = productInfoEjb.getBusEntityByCriteria(new BusEntitySearchCriteria(),
                      RefCodeNames.BUS_ENTITY_TYPE_CD.CERTIFIED_COMPANY);
      SelectableObjects selectCertCompanies =
          new SelectableObjects(new BusEntityDataVector(), certifiedCompanies, BUS_ENTITY_ID_COMPARE);
      pForm.setSelectCertCompanies(selectCertCompanies);

      // get the Store Property "EQUAL_COST_AND_PRICE"
      // and, based on the value of this property,
      // set the corresponding flag in the form "StoreItemMgrForm"

      //Store storeEjb = factory.getStoreAPI();
      StoreData sd = storeEjb.getStore(storeId);
      PropertyDataVector prop = sd.getMiscProperties();
      for (int ii = 0; ii < prop.size(); ii++) {
          PropertyData pD = (PropertyData) prop.get(ii);
          String propType = pD.getPropertyTypeCd();
          String propValue = pD.getValue();
          if (RefCodeNames.PROPERTY_TYPE_CD.EQUAL_COST_AND_PRICE.equals(propType)) {
              pForm.setShowDistCostFl(Utility.isTrue(propValue));
          } else if (RefCodeNames.PROPERTY_TYPE_CD.ALLOW_MIXED_CATEGORY_AND_ITEM.equals(propType)) {
              pForm.setAllowMixedCategoryAndItemUnderSameParent(Utility.isTrue(propValue));
          }
      }

      return ae;
  }

	public static ActionErrors loadStoreItems(HttpServletRequest request,
			StoreItemMgrForm form) throws Exception{
	    HttpSession session = request.getSession();
	    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.
	    	      APP_USER);
	    ActionErrors ae = new ActionErrors();
	    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
	    Store storeEjb = factory.getStoreAPI();
	    try {
	    	storeEjb.loadMasterItem("TMP_TCSLOAD_ITEM", appUser);
	    }
	    catch(Exception e) {
	    	ae.add("LOAD", new ActionError(e.getClass().getName(), e.getMessage()));
	    }


	    return ae;
	}

  public static ActionErrors searchForStoreItems(HttpServletRequest request,
    StoreItemMgrForm pForm) throws
    Exception {
    HttpSession session = request.getSession();
    ActionErrors ae = new ActionErrors();
    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();

    if (!pForm.getMatchSearch()) {
        pForm.setItems(new ItemViewVector());
    }
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.
      APP_USER);
    StoreData storeD = appUser.getUserStore();
    if (storeD == null) {
      ae.add("error",
             new ActionError("error.simpleGenericError", "No store info"));
      return ae;
    }

    int storeId = storeD.getBusEntity().getBusEntityId();

    //get the search data from the form
    String category = pForm.getCategoryTempl();
    String shortDesc = pForm.getShortDescTempl();
    String longDesc = pForm.getLongDescTempl();
    String itemPropName = pForm.getItemPropertyName();
    String itemPropTempl = pForm.getItemPropertyTempl();
    String manuNameTempl = pForm.getManuNameTempl();
    String distNameTempl = pForm.getDistNameTempl();
    String skuNumber = pForm.getSkuTempl();
    String skuType = pForm.getSkuType();
    String searchNumType = pForm.getSearchNumType();

    if (!Utility.isSet(skuType)) skuType = SearchCriteria.STORE_SKU_NUMBER;
    if (SearchCriteria.DISTRIBUTOR_SKU_NUMBER.equals(skuType)) pForm.
      setDistInfoFlag(true);
    pForm.setSkuType(skuType);
    //Create a set of filters
    LinkedList searchConditions = new LinkedList();

    SearchCriteria scStore = new
                             SearchCriteria(SearchCriteria.STORE_ID,
                                            SearchCriteria.EXACT_MATCH,
                                            new Integer(storeId));
    searchConditions.add(scStore);
    //Category
    if (Utility.isSet(category)) {
      SearchCriteria sc = new
                          SearchCriteria(SearchCriteria.CATALOG_CATEGORY,
                                         SearchCriteria.CONTAINS_IGNORE_CASE,
                                         category);
      searchConditions.add(sc);
    }
    //Short Desc
    if (Utility.isSet(shortDesc)) {
      SearchCriteria sc = new
                          SearchCriteria(SearchCriteria.PRODUCT_SHORT_DESC,
                                         SearchCriteria.CONTAINS_IGNORE_CASE,
                                         shortDesc);
      searchConditions.add(sc);
    }

    //Long Desc
    if (Utility.isSet(longDesc)) {
      SearchCriteria sc = new
                          SearchCriteria(SearchCriteria.PRODUCT_LONG_DESC,
                                         SearchCriteria.CONTAINS_IGNORE_CASE,
                                         longDesc);
      searchConditions.add(sc);
    }

    //Item Meta Property
    if (Utility.isSet(itemPropName) && Utility.isSet(itemPropTempl)) {
      SearchCriteria sc = new
                          SearchCriteria(SearchCriteria.ITEM_META +
                                         itemPropName,
                                         SearchCriteria.CONTAINS_IGNORE_CASE,
                                         itemPropTempl);
      searchConditions.add(sc);
    }

    //Manufacturer
    if (Utility.isSet(manuNameTempl)) {
      SearchCriteria sc = new
                          SearchCriteria(SearchCriteria.
                                         MANUFACTURER_SHORT_DESC,
                                         SearchCriteria.CONTAINS_IGNORE_CASE,
                                         manuNameTempl);
      searchConditions.add(sc);
    }

    //Distributor
    if (Utility.isSet(distNameTempl)) {
      SearchCriteria sc = new
                          SearchCriteria(SearchCriteria.
                                         DISTRIBUTOR_SHORT_DESC,
                                         SearchCriteria.CONTAINS_IGNORE_CASE,
                                         distNameTempl);
      searchConditions.add(sc);
      pForm.setDistInfoFlag(true);
    }

    //sku

    if (Utility.isSet(skuNumber)) {
    	SearchCriteria sc = new SearchCriteria();

    	if(searchNumType.equals("nameBegins")){
    		sc = new SearchCriteria(skuType,SearchCriteria.BEGINS_WITH,skuNumber);
    	}else if(searchNumType.equals("nameContains")){
    		sc = new SearchCriteria(skuType,SearchCriteria.CONTAINS,skuNumber);
    	}else{
    		sc = new SearchCriteria(skuType,SearchCriteria.EXACT_MATCH,skuNumber);
    	}
    	searchConditions.add(sc);
    }
    if(!pForm.getShowInactiveFl())
    {
      SearchCriteria sc = new
                          SearchCriteria(SearchCriteria.ITEM_STATUS_CD,
                                         SearchCriteria.CONTAINS,
                                         RefCodeNames.ITEM_STATUS_CD.ACTIVE);
      searchConditions.add(sc);
    }

    if(pForm.getSearchGreenCertifiedFl())
    {

        SearchCriteria sc = new
                          SearchCriteria(SearchCriteria.CERTIFIED,
                                         SearchCriteria.EXACT_MATCH,
                                         String.valueOf(Boolean.TRUE));
      searchConditions.add(sc);
    }

    ItemViewVector items = catalogInfEjb.searchStoreItems(searchConditions, pForm.getDistInfoFlag());
    // records limitation to display
    int maxRec = 1000;
    if (items.size() >= maxRec){
      ItemViewVector itemsDisp = new ItemViewVector();
      for (int i = 0; i < maxRec; i++) {
        itemsDisp.add(items.get(i));
      }
      if (!pForm.getMatchSearch()) {
          pForm.setItems(itemsDisp);
      } else {
          pForm.setItemMatchSearchResult(itemsDisp);
      }
      ae.add("error",  new ActionError("error.simpleGenericError", "More then " + maxRec + " Items have been found. Need to specify search criteria more detail."));
    } else {
        if (!pForm.getMatchSearch()) {
            pForm.setItems(items);
        } else {
            pForm.setItemMatchSearchResult(items);
        }
    }

    return ae;

  }

  public static ActionErrors createNotMatchedItem (HttpServletRequest request, StoreItemMgrForm pForm) throws Exception {
        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        ItemView notMatchedItem = pForm.getStagedItemMatch();
        if (notMatchedItem != null) {
        	APIAccess factory = new APIAccess();
        	Store storeEJB = factory.getStoreAPI();
        	try {
        		storeEJB.loadMasterItem(notMatchedItem.getItemId(), appUser);
        	}
        	catch(RemoteException e)
        	{
        		ae.add("Create", new ActionError("Create Master Asset From Staged Asset Failed"));
        	}
        }
        else {
        	ae.add("Create", new ActionError("Internal Error. There is no selected Master Asset"));
        }
        return ae;
    }

    public static ActionErrors createAllNotMatchedItems(HttpServletRequest request, StoreItemMgrForm pForm) throws Exception {
        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        APIAccess factory = new APIAccess();

    	Store storeEJB = factory.getStoreAPI();
    	try {
    		storeEJB.loadMasterItemFromStaged(appUser);
    	}
    	catch(RemoteException e)
    	{
    		ae.add("Create", new ActionError("Create Master Asset From Staged Asset Failed"));
    	}
        return ae;
    }

    private static void createNotMatchedItems(ItemInformation itemInfoEJB,
                                              Collection itemsToCreate,
                                              int storeId,
                                              String userName) throws Exception {

        Iterator it = itemsToCreate.iterator();
        StagedItemData notMatchedItem;
        ItemData itemToCreate;
        while (it.hasNext()) {
            notMatchedItem = (StagedItemData)it.next();

            itemToCreate = ItemData.createValue();
            itemToCreate.setItemId(0);
            itemToCreate.setShortDesc(notMatchedItem.getShortDescription());
            if (Utility.isSet(notMatchedItem.getMfgSku())) {
                try {
                    int skuNum = Integer.parseInt(notMatchedItem.getMfgSku());
                    itemToCreate.setSkuNum(skuNum);
                } catch (NumberFormatException e) {}
            }
            itemToCreate.setLongDesc(notMatchedItem.getLongDescription());
            itemToCreate.setItemStatusCd(RefCodeNames.ASSET_STATUS_CD.ACTIVE);

            //itemToCreate = productEJB.addProduct(pProduct, request)updateItemData(itemToCreate, userName);

            // remove the corresponding staged item in the staged items table
            StagedItemData stagedItem = itemInfoEJB.getStagedItemData(notMatchedItem.getStagedItemId());
            if (stagedItem != null) {
                //stagedItem.setStagedItemStatusCd(RefCodeNames.ITEM_STATUS_CD.INACTIVE);
                itemInfoEJB.removeStagedItemData(stagedItem.getStagedItemId());
            }

            // make the corresponding staged item association in staged item assoc table inactive
            StagedItemAssocDataVector stagedAssocV = itemInfoEJB.getStagedItemAssocDataVector(notMatchedItem.getStagedItemId());
            StagedItemAssocData stagedAssoc;
            if (!stagedAssocV.isEmpty()) {
                stagedAssoc = (StagedItemAssocData)stagedAssocV.get(0);
                stagedAssoc.setAssocStatusCd(RefCodeNames.ASSET_ASSOC_STATUS_CD.INACTIVE);
                itemInfoEJB.updateStagedItemAssocData(stagedAssoc, userName);
            }
        }
    }

    public static ActionErrors matchStagedItem (HttpServletRequest request, StoreItemMgrForm pForm) throws Exception {
        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        ItemView notMatchedItem = pForm.getStagedItemMatch();
        String selectedItemIdStr = request.getParameter("selectedItemId");
        int itemId = Integer.parseInt(selectedItemIdStr);

        if (notMatchedItem != null) {
        	APIAccess factory = new APIAccess();
        	Store storeEJB = factory.getStoreAPI();
        	try {
                        // remove the staged item from the list of the unmatched staged items
                        pForm.getStagedItems().remove(notMatchedItem);
        		final int staged_item_id = notMatchedItem.getItemId();
				storeEJB.loadMasterItem(staged_item_id, itemId, appUser);
        	}
        	catch(RemoteException e)
        	{
        		ae.add("Create", new ActionError("Create Master Asset From Staged Asset Failed"));
        	}
        }
        else {
        	ae.add("Create", new ActionError("Internal Error. There is no selected Master Asset"));
        }
        return ae;
    }

    public static ActionErrors unmatchMatchedItem (HttpServletRequest request, StoreItemMgrForm pForm) throws Exception {
        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        ItemInformation itemInfoEjb = factory.getItemInformationAPI();

        String selectedItemIndexStr = request.getParameter("masterItemIndex");

        int selectedItemIndex = -1;
        StagedItemData matchedItem = null;
        ItemView selectedItem = null;
        if (selectedItemIndexStr != null) {
            try {
                selectedItemIndex = Integer.parseInt(selectedItemIndexStr);
                if (pForm.getStagedItems() != null) {
                    selectedItem = (ItemView)pForm.getStagedItems().get(selectedItemIndex);
                    if (selectedItem != null) {
                        matchedItem = itemInfoEjb.getStagedItemData(selectedItem.getItemId());
                    }
                }
            } catch (NumberFormatException e) {}
        }

        if (matchedItem != null) {
            // remove the matched staged item from the list of the matched staged items
            //pForm.getStagedItems().remove(selectedItemIndex);

            selectedItem.setMatchedItemId(0);
                matchedItem.setMatchedItemId(0);
                itemInfoEjb.updateStagedItemData(matchedItem, appUser.getUserName());
        }
        return ae;
    }

  public static ActionErrors sortStaged (HttpServletRequest request, StoreItemMgrForm pForm) throws Exception {
      ActionErrors ae = new ActionErrors();
      ItemViewVector items = pForm.getStagedItems();

      if (items != null) {
          String sortField = request.getParameter("sortField");
          DisplayListSort.sort(items, sortField);
      }
      return ae;
  }

  public static ActionErrors sort(HttpServletRequest request,
                                  StoreItemMgrForm pForm) throws Exception {
    ActionErrors ae = new ActionErrors();
    String sortField = request.getParameter("sortField");
    ItemViewVector items = pForm.getItems();
    sortItems(items, sortField);
    pForm.setItems(items);

    return ae;
  }

  public static ActionErrors sortMatch(HttpServletRequest request,
                                  StoreItemMgrForm pForm) throws Exception {
    ActionErrors ae = new ActionErrors();
    String sortField = request.getParameter("sortField");
    ItemViewVector items = pForm.getItemMatchSearchResult();
    sortItems(items, sortField);
    pForm.setItemMatchSearchResult(items);

    return ae;
  }

  private static void sortItems(ItemViewVector items, String sortField) {
       if ("id".equals(sortField)) {
          items.sort("ItemId");
       } else if ("sku".equals(sortField)) {
          items.sort("Sku");
       } else if ("name".equals(sortField)) {
          items.sort("Name");
       } else if ("size".equals(sortField)) {
          items.sort("Size");
       } else if ("pack".equals(sortField)) {
          items.sort("Pack");
       } else if ("uom".equals(sortField)) {
          items.sort("Uom");
       } else if ("color".equals(sortField)) {
          items.sort("Color");
       } else if ("manufacturer".equals(sortField)) {
          items.sort("ManufName");
       } else if ("msku".equals(sortField)) {
          items.sort("ManufSku");
       } else if ("category".equals(sortField)) {
          items.sort("Category");
       } else if ("dist".equals(sortField)) {
          items.sort("DistName");
       } else if ("dsku".equals(sortField)) {
          items.sort("DistSku");
       }
  }


  public static ActionErrors initItem(HttpServletRequest request,
                                      StoreItemMgrForm pForm,
                                      ProductData pProduct) throws Exception {
    HttpSession session = request.getSession();
    ActionErrors ae = new ActionErrors();
    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);

    // pForm.setItems(new ItemViewVector());
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    StoreData storeD = appUser.getUserStore();
    if (storeD == null) {
      ae.add("error",
             new ActionError("error.simpleGenericError", "No store info"));
      return ae;
    }

    boolean isUseItemsLinksBetweenStores = getUseItemsLinksBetweenStores(request);

    // Get all distributors when the store is a distributor store
    initAllDistributorsList(request, pForm, pProduct, storeD);

    pForm.setProduct(pProduct);

      ProductInformation productInfoEjb = factory.getProductInformationAPI();
      Asset assetEjb = factory.getAssetAPI();
      ItemInformation itemInfoEjb = factory.getItemInformationAPI();
      Catalog catalogEjb = factory.getCatalogAPI();

      int itemStoreId = 0;
      if (pProduct.getCatalogStructure() != null) {
        BusEntityData store = catalogEjb.getStoreForCatalog(pProduct.getCatalogStructure().getCatalogId());
        if (store != null) {
            itemStoreId = store.getBusEntityId();
            pForm.setItemStoreId(itemStoreId);
        }
      }

      BusEntityDataVector certifiedCompanies = productInfoEjb.getBusEntityByCriteria(new BusEntitySearchCriteria(), RefCodeNames.BUS_ENTITY_TYPE_CD.CERTIFIED_COMPANY);
      SelectableObjects selectCertCompanies = new SelectableObjects(pProduct.getCertCompaniesBusEntityDV(), certifiedCompanies, BUS_ENTITY_ID_COMPARE);
      pForm.setSelectCertCompanies(selectCertCompanies);

      CatalogCategoryDataVector ccDV = pProduct.getCatalogCategories();
    if (ccDV.size() > 0) {
      CatalogCategoryData ccD = (CatalogCategoryData) ccDV.get(0);
      pForm.setSelectedCategoryId(ccD.getCatalogCategoryId());
    } else {
      pForm.setSelectedCategoryId(0);
    }

    BusEntityData manufBusEntity = pProduct.getManufacturer();
    if (manufBusEntity != null) {
      pForm.setSelectedManufId(manufBusEntity.getBusEntityId());
    } else {
      pForm.setSelectedManufId(0);
    }

    initConstantList(request);

    pForm.setHazmat(pProduct.getHazmat());
    pForm.setSkuNum("" + pForm.getProduct().getCustomerSkuNum());
    String uomCd = pProduct.getUom();
    RefCdDataVector uomCdV = (RefCdDataVector) session.getAttribute(
      "Item.uom.vector");

    boolean isPredefined = false;
    for (int i = 0; i < uomCdV.size(); i++) {
      if (uomCd.equals(((RefCdData) uomCdV.get(i)).getValue())) {
        isPredefined = true;
        break;
      }
    }
    if (isPredefined || "".equals(uomCd)) {
      pForm.setUom(uomCd);
    } else {
      pForm.setUom(RefCodeNames.ITEM_UOM_CD.UOM_OTHER);
    }

    Date effDate = pProduct.getEffDate();
    String dateString = "";
    if (effDate != null) {
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
      dateString = simpleDateFormat.format(effDate);
    }
    pForm.setEffDate(dateString);

    Date expDate = pProduct.getExpDate();
    dateString = "";
    if (expDate != null) {
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
      dateString = simpleDateFormat.format(expDate);
    }
    pForm.setExpDate(dateString);

    pForm.setStatusCd(pProduct.getStatusCd());

    BusEntityData manuD = pProduct.getManufacturer();
    if (manuD != null) {
      int manuId = pProduct.getManufacturer().getBusEntityId();
      pForm.setManufacturerId(manuId);
      pForm.setManufacturerName("" + pProduct.getManufacturer().getShortDesc());
      String manuSku = pProduct.getManufacturerSku();
      if (manuSku == null) manuSku = "";
      pForm.setManufacturerSku(manuSku);
    } else {
      pForm.setManufacturerId(0);
      pForm.setManufacturerName("");
      pForm.setManufacturerSku("");
    }

    String listPriceS = "";
    double listPrice = pProduct.getListPrice();
    listPriceS = "" + listPrice;
    BigDecimal listPriceBD = new BigDecimal("0.0");
    try {
      listPriceBD = CurrencyFormat.parse(listPriceS);
    } catch (ParseException pe) {
      // this should only happen if bad value in db
      listPriceBD = new BigDecimal("0.0");
    }
    pForm.setListPrice(CurrencyFormat.format(listPriceBD));

    String costPriceS = "";
    double costPrice = pProduct.getCostPrice();
    costPriceS = "" + costPrice;
    BigDecimal costPriceBD = new BigDecimal("0.0");
    try {
      costPriceBD = CurrencyFormat.parse(costPriceS);
    } catch (ParseException pe) {
      // this should only happen if bad value in db
      costPriceBD = new BigDecimal("0.0");
    }
    pForm.setCostPrice(CurrencyFormat.format(costPriceBD));

    pForm.setNewDistributorId("");
    pForm.setNewDistributorSku("");
    pForm.setNewDistributorUom("");
    pForm.setNewDistributorPack("");
    pForm.setNewDistributor(null);
    pForm.setDistributorBox(new String[0]);

    pForm.setLinkedProductItems(new StoreProductViewVector());
    pForm.setLinkedProductItemsBetweenStores(new StoreProductViewVector());
    Map orgProductPropertyMap = new HashMap();
    orgProductPropertyMap = initOrgProductProperty(
    	      orgProductPropertyMap,
    	      pProduct); 
    	      
    addStorageTypeToForm(pProduct, request, pForm);
    	      
    log.info("pForm.getDedStorageTypeCd() = " + pForm.getDedStorageTypeCd());
    log.info("orgProductPropertyMap1 = " + orgProductPropertyMap);
    
    pForm.setOrgProductPropertyMap(orgProductPropertyMap);
    pForm.setProductUpdated(false);
    pForm.setWarnedForManagedItem(false);

    // check if this item is managed item
    try {
        IdVector linkedItemIdV = new IdVector();
        if (isUseItemsLinksBetweenStores) {
            linkedItemIdV = productInfoEjb.getItemIdCollectionByManagedItemBetweenStores(pProduct.getProductId());
            if (null != linkedItemIdV && 0 < linkedItemIdV.size()) {
                pForm.setManagedItemBetweenStoresFlag(true);
            }
            else {
                pForm.setManagedItemBetweenStoresFlag(false);
            }
        }
        else {
            if (RefCodeNames.STORE_TYPE_CD.ENTERPRISE.equalsIgnoreCase(storeD.getStoreType().getValue())) {
                linkedItemIdV = productInfoEjb.getItemIdCollectionByManagedParentItem(pProduct.getProductId());
            }
            else {
                linkedItemIdV = productInfoEjb.getItemIdCollectionByManagedChildItem(pProduct.getProductId());
            }
            if (null != linkedItemIdV && 0 < linkedItemIdV.size()) {
                pForm.setManagedItemFlag(true);
            }
            else {
                pForm.setManagedItemFlag(false);
            }
        }
    }
    catch (Exception exc) {
        log.info("Error: " + exc.getMessage());
    }

    // reset the fielss for import item function
    pForm.setFromImport(false);
    pForm.setParentProductId(0);

    // get prices for editing
    initPrices(request, pForm, pProduct, storeD.getBusEntity().getBusEntityId());

    pForm.setChangesOnItemEditPageFl(Boolean.FALSE);
    pForm.setDataFieldProperties(pForm.getProduct().getDataFieldProperties());

    String isParentStore = Utility.getPropertyValue(storeD.getMiscProperties(),
                                RefCodeNames.PROPERTY_TYPE_CD.IS_PARENT_STORE);
    pForm.setShowLinkedStores(Utility.isTrue(isParentStore, false));
    pForm.setLinkedStores(assetEjb.getMasterItemLinkedStores(pProduct.getProductId()));
    pForm.setIsEditable(itemInfoEjb.canEditMasterItem(pProduct.getProductId(), appUser.getUserStore().getStoreId()));
    
    String host = (new SessionTool(request)).getInternalHostToken();
    
    // init
    return ae;

  }

    public static ActionErrors searchStaged(HttpServletRequest request, StoreItemMgrForm form) throws Exception {
        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        StoreData storeD = appUser.getUserStore();
        if (storeD == null) {
            ae.add("error", new ActionError("error.simpleGenericError", "No store info"));
        return ae;
        }
        int storeId = storeD.getStoreId();
        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        ItemInformation itemInfoEjb = factory.getItemInformationAPI();

        //Create a set of filters
        LinkedList searchConditions = new LinkedList();

        //Short Desc
        int searchType;
        if (Utility.isSet(form.getShortDescTempl())) {
            if ((RefCodeNames.SEARCH_TYPE_CD.BEGINS).equals(form.getNameSearchType())) {
                searchType = SearchCriteria.BEGINS_WITH_IGNORE_CASE;
            } else {
                searchType = SearchCriteria.CONTAINS_IGNORE_CASE;
            }
            SearchCriteria sc = new SearchCriteria(SearchCriteria.PRODUCT_SHORT_DESC,
                                                   searchType,
                                                   form.getShortDescTempl());
            searchConditions.add(sc);
        }
        if (Utility.isSet(form.getSkuTempl())) {
            if ((RefCodeNames.SEARCH_TYPE_CD.BEGINS).equals(form.getSkuSearchType())) {
                searchType = SearchCriteria.BEGINS_WITH_IGNORE_CASE;
            } else {
                searchType = SearchCriteria.CONTAINS_IGNORE_CASE;
            }
            SearchCriteria sc = new SearchCriteria(SearchCriteria.CLW_SKU_NUMBER,
                                                   searchType,
                                                   form.getSkuTempl());
            searchConditions.add(sc);
        }

        //if (Utility.isSet(form.getStagedSearchType())) {
        //    SearchCriteria sc = new SearchCriteria(SearchCriteria.STAGED_SEARCH_TYPE,
        //                                           SearchCriteria.EXACT_MATCH,
        //                                           form.getStagedSearchType());
        //    searchConditions.add(sc);
        //}

        SearchCriteria sc = new SearchCriteria(SearchCriteria.STORE_ID,
                                               SearchCriteria.EXACT_MATCH,
                                               new Integer(storeId));
        searchConditions.add(sc);

        form.setStagedItems(itemInfoEjb.getStagedItemVector(searchConditions));

        return ae;
    }

  public static ActionErrors chooseItemToMatch(HttpServletRequest request, StoreItemMgrForm form) throws Exception {
        ActionErrors ae = new ActionErrors();

        String itemToMatchStr = request.getParameter("masterItemIndex");

        if (Utility.isSet(itemToMatchStr)) {
            if (form.getStagedItems() != null) {
                try {
                    ItemView itemToMatch = (ItemView)form.getStagedItems().get(Integer.valueOf(itemToMatchStr));
                    form.setStagedItemMatch(itemToMatch);
                } catch (NumberFormatException e) {
                    form.setStagedItemMatch(null);
                }
            }
        }
        return ae;
  }

  private static void initPrices(HttpServletRequest request,
                                 StoreItemMgrForm pForm,
                                 ProductData pProduct,
                                 int storeId) throws Exception
  {
    HttpSession session = request.getSession();
    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    Contract contract = factory.getContractAPI();
    CatalogInformation catalog = factory.getCatalogInformationAPI();
    BusEntityDataVector accounts = contract.getItemAccounts(pProduct.getProductId(), storeId);
    pForm.setAccounts(accounts);
    ArrayList contracts = new ArrayList();
    ArrayList prices = new ArrayList();
    String showPricing = request.getParameter("showPricing");
    if (showPricing == null) showPricing = "false";
    if(accounts.size() > 200 || (accounts.size() > 10 && showPricing.equals("false"))) {
        pForm.setAccounts(new BusEntityDataVector());
        pForm.setContracts(contracts);
        pForm.setPrices(prices);
        pForm.setMultipleAccount(new boolean[0]);
        pForm.setShowPricing(false);
        return;
    }
    boolean[] multipleAccount = new boolean[accounts.size()];
    IdVector itemids = new IdVector();
    itemids.add(new Integer(pProduct.getProductId()));
    for (int i=0; i<accounts.size(); i++) {
      BusEntityData account = (BusEntityData)accounts.get(i);
      // active contracts list
      ContractDataVector cdv = contract.getContractsByAccount(account.getBusEntityId());
      // price list
      ContractItemPriceViewVector contractPrices = contract.getPriceItems(itemids, cdv);
      checkSamePrices(contractPrices);
      // account list for catalog
      multipleAccount[i] = false;
      Set<Integer> catalogIds = new HashSet<Integer>();
      for (int j=0; j<cdv.size(); j++) {
        ContractData cd = (ContractData)cdv.get(j);
        catalogIds.add(new Integer(cd.getCatalogId()));
      }
      Map<Integer, List<Integer>> catalogIdToAccountIdMap = catalog.getCatalogIdToAccountIdMap(catalogIds);
      Iterator<Integer> catalogIdIterator = catalogIdToAccountIdMap.keySet().iterator();
      boolean multipleFound = false;
      while (catalogIdIterator.hasNext() && !multipleFound) {
    	  Integer catalogId = catalogIdIterator.next();
    	  List<Integer> accountList = catalogIdToAccountIdMap.get(catalogId);
    	  if (accountList.size() > 1) {
    	        multipleAccount[i] = true;
    	        multipleFound = true;
    	  }
      }
      contracts.add(cdv);
      prices.add(contractPrices);
    }
    pForm.setContracts(contracts);
    pForm.setPrices(prices);
    pForm.setMultipleAccount(multipleAccount);
    pForm.setShowPricing(true);
  }

  private static void checkSamePrices(ContractItemPriceViewVector prices) {
    if (prices != null && prices.size() > 1) {
      boolean theSame = true;
      BigDecimal distCost = ((ContractItemPriceView)prices.get(0)).getDistCost();
      BigDecimal custPrice = ((ContractItemPriceView)prices.get(0)).getPrice();
      for (int i=1; i<prices.size(); i++) {
        ContractItemPriceView price = (ContractItemPriceView)prices.get(i);
        BigDecimal distCostNext = price.getDistCost();
        BigDecimal custPriceNext = price.getPrice();
        if ( !distCostNext.equals(distCost) || !custPriceNext.equals(custPrice)) {
          theSame = false;
          break;
        }
      }
      if (theSame) {
        for (int i=1; i<prices.size(); i++) {
          prices.remove(i);
        }
      }
    }
  }

  public static ActionErrors initItem(HttpServletRequest request,
                                      StoreItemMgrForm pForm) throws
    Exception {
    HttpSession session = request.getSession();
    ActionErrors ae = new ActionErrors();
    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();
    boolean isUseItemsLinksBetweenStores = getUseItemsLinksBetweenStores(request);

    // pForm.setItems(new ItemViewVector());
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.
      APP_USER);
    StoreData storeD = appUser.getUserStore();
    if (storeD == null) {
      ae.add("error",
             new ActionError("error.simpleGenericError", "No store info"));
      return ae;
    }

    int storeId = storeD.getBusEntity().getBusEntityId();

    String itemIdS = request.getParameter("itemId");
    int itemId = Integer.parseInt(itemIdS);
    ProductData productD = null;
    try {
      productD = catalogInfEjb.getStoreProduct(storeId, itemId);
      log.info("initItem(): productD = " + productD);
    } catch (Exception exc) {
      String mess = exc.getMessage();
      if (mess == null) mess = "";
      int ind = mess.indexOf("^clw^");
      if (ind >= 0) {
        mess = mess.substring(ind + "^clw^".length());
        ind = mess.indexOf("^clw^");
        if (ind > 0) mess = mess.substring(0, ind);
        ae.add("error", new ActionError("error.simpleGenericError", mess));
        return ae;
      } else {
        throw exc;
      }
    }
    ae = initItem(request, pForm, productD);
    if (ae.size() > 0) {
      return ae;
    }

    if (isUseItemsLinksBetweenStores) {
        ae = getLinkedItems(request, pForm);
    }
    else {
        if (RefCodeNames.STORE_TYPE_CD.ENTERPRISE.equalsIgnoreCase(storeD.getStoreType().getValue())) {
            ae = getLinkedItems(request, pForm);
        }
    }

      return ae;

  }



  private static void initAllDistributorsList(HttpServletRequest request,
                                              StoreItemMgrForm pForm, ProductData pProduct,
                                              StoreData storeD
    ) throws Exception
  {
    if (storeD.getStoreType().getValue().equals(RefCodeNames.STORE_TYPE_CD.DISTRIBUTOR)) {
      HttpSession session = request.getSession();
      APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
      if (null == factory) {
        throw new Exception("Without APIAccess.");
      }
      CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

      Distributor distBean = factory.getDistributorAPI();
      BusEntitySearchCriteria crit = new BusEntitySearchCriteria();
      crit.setStoreBusEntityIds(appUser.getUserStoreAsIdVector());
      DistributorDataVector dv = distBean.getDistributorByCriteria(crit);
      if (dv != null && dv.size() >= 0) {
        BusEntityDataVector mappedDist = pProduct.getMappedDistributors();
        DistItemViewVector div = new DistItemViewVector();
        for (int i = 0; i < dv.size(); i++) {
          DistributorData d = (DistributorData) dv.get(i);
          boolean found = false;
          for (int j = 0; j < mappedDist.size(); j++) {
            if (((BusEntityData) mappedDist.get(j)).getBusEntityId() ==
                d.getBusEntity().getBusEntityId()) {
              found = true;
              break;
            }
          }
          if (!found) {
            div.add(d);
          }
        }
        pForm.setDistItemsFullList(div);
      }
    }
  }

  public static void initConstantList(HttpServletRequest request) throws
    Exception {

    HttpSession session = request.getSession();
    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    if (null == factory) {
      throw new Exception("Without APIAccess.");
    }

    ListService listServiceEjb = factory.getListServiceAPI();
    if (null == session.getAttribute("Item.uom.vector")) {
      RefCdDataVector uomv =
        listServiceEjb.getRefCodesCollection("ITEM_UOM_CD");
      session.setAttribute("Item.uom.vector", uomv);
    }

  }

  private static ActionErrors readStoreManufacturers(Manufacturer manufEjb,
    StoreItemMgrForm pForm, int storeId) throws Exception {

    ActionErrors ae = new ActionErrors();

    BusEntitySearchCriteria besc = new BusEntitySearchCriteria();
    IdVector storeIdAsV = new IdVector();
    storeIdAsV.add(new Integer(storeId));
    besc.setStoreBusEntityIds(storeIdAsV);

//    ManufacturerDataVector manufDV = manufEjb.getManufacturerByCriteria(besc);
    ManufacturerDataVector manufDV= null;
    BusEntityDataVector beDV = manufEjb.getManufacturerBusEntitiesByCriteria(besc);
  //  if (beDV.size() > 1) {			//Commented to solve bug in new item creation
      Object[] manufA = beDV.toArray();
      for (int ii = 0; ii < manufA.length - 1; ii++) {
        boolean exitFl = true;
        for (int jj = 0; jj < manufA.length - ii - 1; jj++) {
//          ManufacturerData mD1 = (ManufacturerData) manufA[jj];
//          ManufacturerData mD2 = (ManufacturerData) manufA[jj + 1];
          BusEntityData mD1 = (BusEntityData) manufA[jj];
          BusEntityData mD2 = (BusEntityData) manufA[jj + 1];
          int comp = mD1.getShortDesc().compareToIgnoreCase(mD2.getShortDesc());
//          int comp = mD1.getBusEntity().getShortDesc().compareToIgnoreCase(mD2.getBusEntity().getShortDesc());
          if (comp > 0) {
            manufA[jj] = mD2;
            manufA[jj + 1] = mD1;
            exitFl = false;
          }
        }
        if (exitFl)break;
      }
      manufDV = new ManufacturerDataVector();
      Set<Integer> manufIds = new HashSet<Integer>();
      for (int ii = 0; ii < manufA.length; ii++) {
          BusEntityData be = (BusEntityData) manufA[ii];
          manufIds.add(be.getBusEntityId());
      }
      PropertyService ps = APIAccess.getAPIAccess().getPropertyServiceAPI();
      IdVector ids = new IdVector();
      ids.addAll(manufIds);
      PropertyDataVector items = ps.getProperties(ids, RefCodeNames.PROPERTY_TYPE_CD.MSDS_PLUGIN);
      Map<Integer, PropertyData> map = new HashMap<Integer, PropertyData>();
      for (int i = 0; i < items.size(); i++) {
          PropertyData item = (PropertyData) items.get(i);
          map.put(item.getBusEntityId(), item);
      }
      for (int ii = 0; ii < manufA.length; ii++) {
        BusEntityData be = (BusEntityData) manufA[ii];
        PropertyDataVector miscProps = null;
        PropertyData miscProp = map.get(be.getBusEntityId()); 
        if (miscProp != null) {
            miscProps = new PropertyDataVector();
            miscProps.add(miscProp);
        }
        ManufacturerData mD1 =  new ManufacturerData(be,storeId, null,null,null,null,null,null,null,null,null,null,miscProps,null);
//        ManufacturerData mD1 = (ManufacturerData) manufA[ii];
        manufDV.add(mD1);
      }

   // }

    pForm.setStoreManufacturers(manufDV);

    return ae;
  }

  private static ActionErrors readStoreMultiproducts(CatalogInformation catalogInfEjb, StoreItemMgrForm pForm, int pStoreId)
      throws Exception {

      ActionErrors ae = new ActionErrors();

      MultiproductViewVector multiproductVV = catalogInfEjb.getStoreMultiproducts(pStoreId);
      pForm.setMultiproducts(multiproductVV);

      return ae;
  }

  private static ActionErrors readStoreCategories(CatalogInformation
    catalogInfEjb,
    StoreItemMgrForm pForm) throws
    Exception {

    ActionErrors ae = new ActionErrors();

    CatalogCategoryDataVector categoryDV = catalogInfEjb.getAllStoreCatalogCategories(pForm.getStoreCatalogId());

    pForm.setStoreCategories(categoryDV);
    return ae;
  }

  /**
   * Deletes product image
   *
   */
  public static ActionErrors deleteImage(HttpServletRequest request,
                                         StoreItemMgrForm pForm) throws
    Exception {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    String user = (String) session.getAttribute(Constants.USER_NAME);
    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();
    Catalog catalogEjb = factory.getCatalogAPI();
    Manufacturer manufacturerEjb = factory.getManufacturerAPI();

    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.
      APP_USER);
    StoreData storeD = appUser.getUserStore();
    if (storeD == null) {
      ae.add("error",
             new ActionError("error.simpleGenericError", "No store info"));
      return ae;
    }

    int storeId = storeD.getBusEntity().getBusEntityId();
    ProductData productD = pForm.getProduct();
    productD.setImage("");
    if (productD.getItemData().getItemId() > 0) {
      try {
        productD =
          catalogEjb.saveStoreCatalogProduct(storeId, pForm.getStoreCatalogId(),
                                             productD, user);
        pForm.setProduct(productD);
      } catch (Exception exc) {
        String mess = exc.getMessage();
        if (mess == null) mess = "";
        int ind = mess.indexOf("^clw^");
        if (ind >= 0) {
          mess = mess.substring(ind + "^clw^".length());
          ind = mess.indexOf("^clw^");
          if (ind > 0) mess = mess.substring(0, ind);
          ae.add("error", new ActionError("error.simpleGenericError", mess));
          return ae;
        } else {
          throw exc;
        }
      }
    }
    pForm.setImageFile(null);
    return ae;
  }

  /**
   * Deletes product thumbnail
   *
   */
  public static ActionErrors deleteThumbnail(HttpServletRequest request,
                                         StoreItemMgrForm pForm) throws
    Exception {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    String user = (String) session.getAttribute(Constants.USER_NAME);
    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();
    Catalog catalogEjb = factory.getCatalogAPI();
    Manufacturer manufacturerEjb = factory.getManufacturerAPI();

    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.
      APP_USER);
    StoreData storeD = appUser.getUserStore();
    if (storeD == null) {
      ae.add("error",
             new ActionError("error.simpleGenericError", "No store info"));
      return ae;
    }

    int storeId = storeD.getBusEntity().getBusEntityId();
    ProductData productD = pForm.getProduct();
    productD.setThumbnail("");
    if (productD.getItemData().getItemId() > 0) {
      try {
        productD =
          catalogEjb.saveStoreCatalogProduct(storeId, pForm.getStoreCatalogId(),
                                             productD, user);
        pForm.setProduct(productD);
      } catch (Exception exc) {
        String mess = exc.getMessage();
        if (mess == null) mess = "";
        int ind = mess.indexOf("^clw^");
        if (ind >= 0) {
          mess = mess.substring(ind + "^clw^".length());
          ind = mess.indexOf("^clw^");
          if (ind > 0) mess = mess.substring(0, ind);
          ae.add("error", new ActionError("error.simpleGenericError", mess));
          return ae;
        } else {
          throw exc;
        }
      }
    }
    pForm.setThumbnailFile(null);
    return ae;
  }

  /**
   * Deletes product image
   *
   */
  public static ActionErrors deleteMsds(HttpServletRequest request,
                                        StoreItemMgrForm pForm) throws
    Exception {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    String user = (String) session.getAttribute(Constants.USER_NAME);
    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();
    Catalog catalogEjb = factory.getCatalogAPI();
    Manufacturer manufacturerEjb = factory.getManufacturerAPI();

    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.
      APP_USER);
    StoreData storeD = appUser.getUserStore();
    if (storeD == null) {
      ae.add("error",
             new ActionError("error.simpleGenericError", "No store info"));
      return ae;
    }

    int storeId = storeD.getBusEntity().getBusEntityId();
    ProductData productD = pForm.getProduct();
    productD.setMsds(null);
    if (productD.getItemData().getItemId() > 0) {
      try {
        productD =
          catalogEjb.saveStoreCatalogProduct(storeId, pForm.getStoreCatalogId(),
                                             productD, user);
        pForm.setProduct(productD);
      } catch (Exception exc) {
        String mess = exc.getMessage();
        if (mess == null) mess = "";
        int ind = mess.indexOf("^clw^");
        if (ind >= 0) {
          mess = mess.substring(ind + "^clw^".length());
          ind = mess.indexOf("^clw^");
          if (ind > 0) mess = mess.substring(0, ind);
          ae.add("error", new ActionError("error.simpleGenericError", mess));
          return ae;
        } else {
          throw exc;
        }
      }
    }
    pForm.setMsdsFile(null);
    return ae;
  }

  /**
   * Deletes product ded
   *
   */
  public static ActionErrors deleteDed(HttpServletRequest request,
                                       StoreItemMgrForm pForm) throws
    Exception {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    String user = (String) session.getAttribute(Constants.USER_NAME);
    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();
    Catalog catalogEjb = factory.getCatalogAPI();
    Manufacturer manufacturerEjb = factory.getManufacturerAPI();

    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.
      APP_USER);
    StoreData storeD = appUser.getUserStore();
    if (storeD == null) {
      ae.add("error",
             new ActionError("error.simpleGenericError", "No store info"));
      return ae;
    }

    int storeId = storeD.getBusEntity().getBusEntityId();
    ProductData productD = pForm.getProduct();
    productD.setDed(null);
    if (productD.getItemData().getItemId() > 0) {
      try {
        productD =
          catalogEjb.saveStoreCatalogProduct(storeId, pForm.getStoreCatalogId(),
                                             productD, user);
        pForm.setProduct(productD);
      } catch (Exception exc) {
        String mess = exc.getMessage();
        if (mess == null) mess = "";
        int ind = mess.indexOf("^clw^");
        if (ind >= 0) {
          mess = mess.substring(ind + "^clw^".length());
          ind = mess.indexOf("^clw^");
          if (ind > 0) mess = mess.substring(0, ind);
          ae.add("error", new ActionError("error.simpleGenericError", mess));
          return ae;
        } else {
          throw exc;
        }
      }
    }
    pForm.setDedFile(null);
    return ae;
  }

  /**
   * Deletes product spec
   *
   */
  public static ActionErrors deleteProductSpec(HttpServletRequest request,
                                               StoreItemMgrForm pForm) throws
    Exception {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    String user = (String) session.getAttribute(Constants.USER_NAME);
    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();
    Catalog catalogEjb = factory.getCatalogAPI();
    Manufacturer manufacturerEjb = factory.getManufacturerAPI();

    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.
      APP_USER);
    StoreData storeD = appUser.getUserStore();
    if (storeD == null) {
      ae.add("error",
             new ActionError("error.simpleGenericError", "No store info"));
      return ae;
    }

    int storeId = storeD.getBusEntity().getBusEntityId();
    ProductData productD = pForm.getProduct();
    productD.setSpec(null);
    if (productD.getItemData().getItemId() > 0) {
      try {
        productD =
          catalogEjb.saveStoreCatalogProduct(storeId, pForm.getStoreCatalogId(),
                                             productD, user);
        pForm.setProduct(productD);
      } catch (Exception exc) {
        String mess = exc.getMessage();
        if (mess == null) mess = "";
        int ind = mess.indexOf("^clw^");
        if (ind >= 0) {
          mess = mess.substring(ind + "^clw^".length());
          ind = mess.indexOf("^clw^");
          if (ind > 0) mess = mess.substring(0, ind);
          ae.add("error", new ActionError("error.simpleGenericError", mess));
          return ae;
        } else {
          throw exc;
        }
      }
    }
    pForm.setSpecFile(null);
    return ae;
  }

  /**
   * Updates existing of adds new product to MASTER product
   *
   */
  public static ActionErrors saveStoreItem(HttpServletRequest request,
                                           StoreItemMgrForm pForm) throws
    Exception {

    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    String user = (String) session.getAttribute(Constants.USER_NAME);
    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    ItemInformation itemInfEjb=factory.getItemInformationAPI();
    CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();
    Catalog catalogEjb = factory.getCatalogAPI();
    Manufacturer manufacturerEjb = factory.getManufacturerAPI();
    boolean isUseItemsLinksBetweenStores = getUseItemsLinksBetweenStores(request);

    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.
      APP_USER);
    StoreData storeD = appUser.getUserStore();
    if (storeD == null) {
      ae.add("error",
             new ActionError("error.simpleGenericError", "No store info"));
      return ae;
    }

    // for non-ENTERPRISE store, if this item is managed item, we will show warning
    // first, then we will allow the user to update
    if (isUseItemsLinksBetweenStores) {
        boolean managedItemFlag = pForm.isManagedItemBetweenStoresFlag();
        if (true == managedItemFlag) {
            boolean warnedForManagedItem = pForm.isWarnedForManagedItemBetweenStores();
            if (false == warnedForManagedItem) {
                ae.add("error", new ActionError("error.simpleGenericError", "Are you sure you want to update a managed item? If yes, select Save Item again."));
                pForm.setWarnedForManagedItemBetweenStores(true);
                return ae;
            }
        }
    }
    else {
        if (!RefCodeNames.STORE_TYPE_CD.ENTERPRISE.equalsIgnoreCase(storeD.getStoreType().getValue())) {
            boolean managedItemFlag = pForm.isManagedItemFlag();
            if (true == managedItemFlag) {
                boolean warnedForManagedItem = pForm.isWarnedForManagedItem();
                if (false == warnedForManagedItem) {
                    ae.add("error", new ActionError("error.simpleGenericError", "Are you sure you want to update a managed item? If yes, select Save Item again."));
                    pForm.setWarnedForManagedItem(true);
                    return ae;
                }
            }
        }
    }

    int storeId = storeD.getBusEntity().getBusEntityId();
    boolean autoSkuFl = pForm.getAutoSkuFlag();
    ProductData productD = pForm.getProduct();
    if (!autoSkuFl) {
      if (pForm.getSkuNum() == null || pForm.getSkuNum().trim().length() == 0) {
        ae.add("error1", new ActionError("variable.empty.error", "Sku"));
      }
    }
    if (productD.getShortDesc() == null ||
        productD.getShortDesc().trim().length() == 0) {
      ae.add("error1", new ActionError("variable.empty.error", "Item Name"));
    }
    /*if (productD.getSize() == null || productD.getSize().trim().length() == 0) {
      ae.add("error3", new ActionError("variable.empty.error", "Item Size"));
    }*/
    if (productD.getPack() == null || productD.getPack().trim().length() == 0) {
      ae.add("error4", new ActionError("variable.empty.error", "Pack"));
    }
    if (productD.getUom() == null || productD.getUom().trim().length() == 0) {
      ae.add("error5", new ActionError("variable.empty.error", "UOM"));
    }
    if (pForm.getManufacturerSku() == null ||
        pForm.getManufacturerSku().trim().length() == 0) {
      ae.add("error6",
             new ActionError("variable.empty.error", "Manufacturer SKU"));
    }
    if (pForm.getSelectedManufId() <= 0) {
      ae.add("error7",
             new ActionError("variable.empty.error", "Manufacturer Id"));
    }
    if (productD.getLongDesc() == null ||
        productD.getLongDesc().trim().length() == 0) {
      ae.add("error8",
             new ActionError("variable.empty.error", "Long Description"));
    }
    if (pForm.getSelectedCategoryId() <= 0) {
      ae.add("error9", new ActionError("variable.empty.error", "Category"));
    }else{
    	int categoryId = pForm.getSelectedCategoryId();
        if (categoryId > 0) {
        	//String allowMixedCategoryAndItemUnderSameParentStr = Utility.getPropertyValue(storeD.getMiscProperties(),
            //        RefCodeNames.PROPERTY_TYPE_CD.ALLOW_MIXED_CATEGORY_AND_ITEM);
        	//boolean allowMixedCategoryAndItemUnderSameParent = Utility.isTrue(allowMixedCategoryAndItemUnderSameParentStr, false);
        	if (!pForm.getAllowMixedCategoryAndItemUnderSameParent() && !catalogInfEjb.isLowestLevelCategory(pForm.getStoreCatalogId(), categoryId)){
        		ae.add("error9", new ActionError("error.simpleGenericError", "Category is a parent category. A parent category cannot be assigned to a Master Item. Select the appropriate child category."));
        	}
        }
    }
      ////////////////////////////////////////////////////////////////
      SelectableObjects selectCertCompanies = pForm.getSelectCertCompanies();
      List delCertCom = selectCertCompanies.getDeselected();
      Iterator it = delCertCom.iterator();
      while (it.hasNext()) {
          BusEntityData busD = (BusEntityData) it.next();
          productD.removeCertCompaniesMapping(busD.getBusEntityId());
          productD.removeCertCompaniesBusEntityDV(busD.getBusEntityId());
      }
      List newlyCertComp = selectCertCompanies.getNewlySelected();
      it = newlyCertComp.iterator();

      addCertCompanies(productD, it);

      // STJ-4620
      if (pForm.isFromImport()) {
          ItemMappingDataVector allcert = productD.getCertifiedCompanies();
          Iterator it1 = allcert.iterator();
          while (it1.hasNext()) {
            ItemMappingData cert = (ItemMappingData)it1.next();
            if (cert.getItemMappingId() > 0) {
                cert.setItemMappingId(0);
            }
          }
      }
     /////////////////////////////////////////////////////////////
     //Set up Item status code
     /////////////////////////////////////////////////////////////
      if(pForm.getStatusCd()!=null &&pForm.getStatusCd().length()>0){
          if(pForm.getStatusCd().equals(RefCodeNames.ITEM_STATUS_CD.INACTIVE))
          {
              IdVector catIds = itemInfEjb.getCatalogIdsExcludeStoreAndSystem(pForm.getProduct().getProductId());
              if(catIds!=null &&catIds.size()>0)
              {
                  int size=catIds.size();
                  String errMessage="Product is used in "+size +" catalogs.Catalog ids : ";

                  for(int i=0;i<(size>10?10:size);i++)
                  {
                      errMessage=errMessage.concat((i>0?", ":"")+String.valueOf(catIds.get(i)));
                  }
                  if(size>10) errMessage+=" ...";
                  pForm.setStatusCd(RefCodeNames.ITEM_STATUS_CD.ACTIVE);
                  ae.add("error10", new ActionError("error.simpleGenericError",errMessage ));
              }
              else  if(catIds!=null && catIds.size()==0)
              {
                  pForm.getProduct().setStatusCd(pForm.getStatusCd());
                  productD.setStatusCd(pForm.getStatusCd());
              }
              else
              {
                  ae.add("error11", new ActionError("error.simpleGenericError", "No info  about use Product in catalogs "));
                  pForm.setStatusCd(RefCodeNames.ITEM_STATUS_CD.ACTIVE);
              }
          }else if(pForm.getStatusCd().equals(RefCodeNames.ITEM_STATUS_CD.ACTIVE)){

              pForm.getProduct().setStatusCd(pForm.getStatusCd());
              productD.setStatusCd(pForm.getStatusCd());

          } else {
              ae.add("error12", new ActionError("error.simpleGenericError", "Unknown is value StatusCd"));
          }
      }else {
          ae.add("error13", new ActionError("variable.empty.error", "StatusCd"));
      }

      String shipWeight = pForm.getProduct().getShipWeight();
        if (shipWeight != null && shipWeight.length() > 0) {
            try {
                BigDecimal testShipWeight = new BigDecimal(shipWeight);
            } catch (NumberFormatException nfe) {
                ae.add("error14", new ActionError("error.simpleGenericError",
                        "Shipping Weight must be number!"));
            }
        }


      /////////////////////////////////////////////////////
    /////////////////////////////////////////////////////
    //Set sku number
    if (!autoSkuFl) {
      productD.setCustomerSkuNum(pForm.getSkuNum());
//      productD.setSkuNum(0);
    }
    //Set effective and expiration dates
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    Date effDate = sdf.parse(sdf.format(new Date()));
    productD.setEffDate(effDate);
    productD.setExpDate(null);

    try {
      int manuId = pForm.getSelectedManufId();
      ManufacturerData manufacturerD = manufacturerEjb.getManufacturer(manuId);
      productD.setManufacturer(manufacturerD.getBusEntity());
      String manuSku = pForm.getManufacturerSku();
      productD.setManuMapping(manufacturerD.getBusEntity(), manuSku);

    } catch (DataNotFoundException exc) {
      ae.add("error", new ActionError("item.master.no_manufacturer"));
    }

    //Set list price
    String listPriceS = pForm.getListPrice();
    if (listPriceS != null && listPriceS.trim().length() > 0) {
      BigDecimal listPriceBD = new BigDecimal("0.0");
      try {
        listPriceBD = CurrencyFormat.parse(listPriceS);
        productD.setListPrice(listPriceBD.doubleValue());
      } catch (ParseException pe) {
        ae.add("error", new ActionError("item.master.wrong_list_price"));
      }
    } else {
      productD.setListPrice(0);
    }

    //Set cost price
    String costPriceS = pForm.getCostPrice();
    if (costPriceS != null && costPriceS.trim().length() > 0) {
      BigDecimal costPriceBD = new BigDecimal("0.0");
      try {
        costPriceBD = CurrencyFormat.parse(costPriceS);
        productD.setCostPrice(costPriceBD.doubleValue());
      } catch (ParseException pe) {
        ae.add("error", new ActionError("item.master.wrong_cost_price"));
      }
    } else {
      productD.setCostPrice(0);
    }

    //check for another item with the same Item_Num, Manuf_Name, UOM in current store
    //get manuf other names

    String currentManufName = "";
    int currentManufId = pForm.getSelectedManufId();
    ManufacturerDataVector storeManufs = pForm.getStoreManufacturers();
    if (storeManufs != null) {
        ManufacturerData manuf;
        for (int i = 0; i < storeManufs.size(); i++) {
            manuf = (ManufacturerData)storeManufs.get(i);
            if (currentManufId == manuf.getBusEntity().getBusEntityId()) {
                currentManufName = manuf.getBusEntity().getShortDesc();
                break;
            }
        }
    }
    String [] otherNamesArray = null;
    BusEntityDataVector manufs = manufacturerEjb.getManufacturerByName(currentManufName, storeId);
    if (!manufs.isEmpty()) {
        int manufId = ((BusEntityData)manufs.get(0)).getBusEntityId();

        PropertyDataVector props = manufacturerEjb.getManufacturerProps(manufId);
        if (!props.isEmpty()) {
            String otherNames = Utility.getPropertyValue(props, RefCodeNames.PROPERTY_TYPE_CD.OTHER_NAMES);
            if (Utility.isSet(otherNames)) {
                otherNamesArray = otherNames.split("\r\n");
            }
        }
    }
    ArrayList manufacturerNames = new ArrayList();
    manufacturerNames.add(currentManufName);
    if (otherNamesArray != null) {
        for (int i = 0; i < otherNamesArray.length; i++) {
            manufacturerNames.add(otherNamesArray[i]);
        }
    }

    if (storeD != null) {
        String parentStoreIdStr = Utility.getPropertyValue(storeD.getMiscProperties(),
                                    RefCodeNames.PROPERTY_TYPE_CD.PARENT_STORE_ID);
		int parentStoreId = 0;
        int itemId = Integer.valueOf(pForm.getProduct().getProductId());
        String uom;
        if (!RefCodeNames.ITEM_UOM_CD.UOM_OTHER.equals(pForm.getUom())) {
            uom = pForm.getUom();
        } else {
            uom = pForm.getProduct().getUom();
        }
        if (Utility.isSet(parentStoreIdStr)) { // CHILD STORE
            try {
                parentStoreId = Integer.parseInt(parentStoreIdStr);
            } catch (NumberFormatException e) { }

            if (parentStoreId > 0) {
                //get manuf other names
                manufs = manufacturerEjb.getManufacturerByName(currentManufName, parentStoreId);
                if (!manufs.isEmpty()) {
                    int manufId = ((BusEntityData)manufs.get(0)).getBusEntityId();

                    PropertyDataVector props = manufacturerEjb.getManufacturerProps(manufId);
                    if (!props.isEmpty()) {
                        String otherNames = Utility.getPropertyValue(props, RefCodeNames.PROPERTY_TYPE_CD.OTHER_NAMES);
                        if (Utility.isSet(otherNames)) {
                            otherNamesArray = otherNames.split("\r\n");
                        }
                    }
                }
                manufacturerNames = new ArrayList();
                manufacturerNames.add(currentManufName);
                if (otherNamesArray != null) {
                    for (int i = 0; i < otherNamesArray.length; i++) {
                        manufacturerNames.add(otherNamesArray[i]);
                    }
                }
                // get the same Master Assets from the Parent Store
                IdVector itemIds = itemInfEjb.checkItemStoreUnique(manufacturerNames, pForm.getManufacturerSku(), uom, parentStoreId);
                if (!itemIds.isEmpty()) { // there are the same Master Items in the Parent Store
                    String mess = "";
                    if (itemId == 0) {
                        mess = ClwI18nUtil.getMessageOrNull(request, "error.store.itemManuf.ParentMasterItemExist");
                    } else {
                        mess = ClwI18nUtil.getMessageOrNull(request, "error.store.itemManuf.ParentMasterItemNotUniqueUpdate");
                    }
                    ae.add("ParentMasterItemExist", new ActionError("error.simpleError", mess));
                    return ae;
                }
            }
        }
        String isParentStore = Utility.getPropertyValue(storeD.getMiscProperties(),
                                RefCodeNames.PROPERTY_TYPE_CD.IS_PARENT_STORE);
        if(Utility.isTrue(isParentStore, false)) { //PARENT STORE
                IdVector itemIds = itemInfEjb.checkItemStoreUnique(manufacturerNames, pForm.getManufacturerSku(), uom, storeId);
			if (!itemIds.isEmpty() && !itemIds.contains(itemId)) {
				String mess = "";
                                if (itemId == 0) {
                                    mess = ClwI18nUtil.getMessageOrNull(request, "error.store.itemManuf.notUnique");
                                } else {
                                    mess = ClwI18nUtil.getMessageOrNull(request, "error.store.itemManuf.notUniqueUpdate");
                                }
				ae.add("ItemManufNotUnique", new ActionError("error.simpleError", mess));
				return ae;
			}

		}

    }

    if (ae.size() > 0) {
      return ae;
    }

    //set category is exists
    int categoryId = pForm.getSelectedCategoryId();
    if (categoryId > 0) {
      CatalogCategoryDataVector ccDV = pForm.getStoreCategories();
      for (Iterator iter = ccDV.iterator(); iter.hasNext(); ) {
        CatalogCategoryData ccD = (CatalogCategoryData) iter.next();
        if (categoryId == ccD.getCatalogCategoryId()) {
          CatalogCategoryDataVector productCcDV = new
            CatalogCategoryDataVector();
          productCcDV.add(ccD);
          productD.setCatalogCategories(productCcDV);
        }
      }

    }

    // set data fields properties
    //PropertyService psvcBean = factory.getPropertyServiceAPI();
    //BusEntityFieldsData fieldsData = psvcBean.fetchMasterItemFieldsData(storeD.getStoreId());
    ItemMetaDataVector fieldsData = pForm.getDataFieldProperties();
    if (fieldsData != null) {
        Iterator i = fieldsData.iterator();
        while (i.hasNext()) {
            ItemMetaData itemMetaD = (ItemMetaData)i.next();
            productD.setItemMeta(itemMetaD, itemMetaD.getNameValue());
        }
    }


    // If inserting, do it now.  Update can wait until after any
    // files are uploaded.  Reason is that the upload needs the
    // item_id to name the uploaded files.  This is not known until
    // after insertion.  So for a new item, we'll do an insert,
    // followed by an update.  Not the most efficient, but if we
    // want to use the item_id as the basis for the filenames...
    boolean createFlag = false;
    boolean needToSave = true;
    ProductInformation productInfoEjb;
    productInfoEjb = factory. getProductInformationAPI();

     if (productD.getItemData().getItemId() == 0) {
      try {
        productD = catalogEjb.saveStoreCatalogProduct(storeId,
          pForm.getStoreCatalogId(), productD, user);
      } catch (Exception exc) {
        String mess = exc.getMessage();
        if (mess == null) mess = "";
        int ind = mess.indexOf("^clw^");
        if (ind >= 0) {
          mess = mess.substring(ind + "^clw^".length());
          ind = mess.indexOf("^clw^");
          if (ind > 0) mess = mess.substring(0, ind);
          ae.add("error", new ActionError("error.simpleGenericError", mess));
          return ae;
        } else {
          throw exc;
        }
      }
      pForm.setProduct(productD);
      needToSave = false;
      createFlag = true;

      // add the managed item link to clw_item_assoc table if import between ENTERPRISE
      // store and non-ENTERPRISE store. if both of them are ENTERPRISE or non-ENTERPRISE
      // we will skip.
      if (true == pForm.isFromImport()) {
        Store storeEjb = factory.getStoreAPI();
        if (0 != pForm.getImportStoreId()) {
          StoreData impStoreD = storeEjb.getStore(pForm.getImportStoreId());
          if (null != impStoreD) {
            String storeType = storeD.getStoreType().getValue();
            String impStoreType = impStoreD.getStoreType().getValue();
            String userName = appUser.getUser().getUserName();

            if (isUseItemsLinksBetweenStores) {
                int linkedItemCatalogId = 0;
                if (productD.getCatalogStructure() != null) {
                    linkedItemCatalogId = productD.getCatalogStructure().getCatalogId();
                }
                productInfoEjb.addItemsLinkBetweenStores(pForm.getParentProductId(),
                    productD.getProductId(), linkedItemCatalogId, userName);
                pForm.setManagedItemBetweenStoresFlag(true);
            }
            else {
                if (!RefCodeNames.STORE_TYPE_CD.ENTERPRISE.equalsIgnoreCase(storeType) &&
                     RefCodeNames.STORE_TYPE_CD.ENTERPRISE.equalsIgnoreCase(impStoreType)) {
                    // import from ENTERPRISE store to non-ENTERPRISE store
                    productInfoEjb.addManagedItemLinkByManagedParentItem(productD.getProductId(),
                        pForm.getParentProductId(), userName);
                    pForm.setManagedItemFlag(true);
                }
                else if (RefCodeNames.STORE_TYPE_CD.ENTERPRISE.equalsIgnoreCase(storeType)&&
                         !RefCodeNames.STORE_TYPE_CD.ENTERPRISE.equalsIgnoreCase(impStoreType)) {
                    // import from non-ENTERPRISE store to ENTERPRISE store
                    productInfoEjb.addManagedItemLinkByManagedChildItem(productD.getProductId(),
                        pForm.getParentProductId(), userName);
                }
            }

          }
		}
      }

    }
if (pForm.isUseUrls() == true) {
	log.info("pForm.isUseUrls() == true");
    String types[] = { TYPE_IMAGES, THUMBNAILS_DIR, TYPE_MSDS, TYPE_DED, TYPE_SPEC };
            String urls[] = { pForm.getImageUrl(), pForm.getThumbnailUrl(),
                    pForm.getMsdsUrl(), pForm.getDedUrl(), pForm.getSpecUrl() };
            System.err.println("Try load from:'" + pForm.getThumbnailUrl()
                    + "'");


            for (int i = 0; i < types.length; i++) {
                if (urls[i] == null || urls[i].length() == 0) {
                    continue;
                }
                try {
                   String requiredMimeType = null;
                   if (types[i].equals(TYPE_IMAGES) || types[i].equals(THUMBNAILS_DIR)) {
                    requiredMimeType = "image";
                   }
                    byte[] data = HttpClientUtil.load(urls[i], requiredMimeType);

                    ActionError ne = uploadFile(productD, types[i], urls[i]);
                    if (ne != null) {
                        ae.add("error", ne);
                        return ae;
                    }
                    tagContentAsUpdated(request, productD, types[i], pForm);
                    needToSave = true;
                } catch (IOException ioe) {
                    ae.add("error", new ActionError("error.simpleGenericError",
                            "Can't load data from:" + urls[i]));
                    return ae;
                }
            }
} else {
	log.info("pForm.isUseUrls() != true");
	
	/***
    // Find out if we save Blobs/binary data in the clw_content table or in E3 Storage    
    String storageType = System.getProperty("storage.system.item");
    log.info(".saveStoreItem(): storageType = " + storageType);
    if ((storageType == null) || storageType.trim().equals("")  || storageType.trim().equals("@storage.system.item@")) {
    	storageType = RefCodeNames.BINARY_DATA_STORAGE_TYPE.DATABASE;
    }
    ***/
	
    FormFile imageFile = pForm.getImageFile();
    if (imageFile != null && !imageFile.getFileName().equals("")) {
      ActionError ne = uploadFile(productD, TYPE_IMAGES, imageFile);
      if (ne != null) {
        ae.add("error", ne);
        return ae;
      }
      tagContentAsUpdated(request, productD, TYPE_IMAGES, pForm);
      needToSave = true;
    } else {
      if (createFlag && Utility.isSet(productD.getImage())) {
        ae = cloneFile(productD, TYPE_IMAGES, productD.getImage());
        if (ae.size() > 0) {
          return ae;
        }
        tagContentAsUpdated(request, productD, TYPE_IMAGES, pForm);
        needToSave = true;
      }
    }

    FormFile thumbnailFile = pForm.getThumbnailFile();
    if (thumbnailFile != null && !thumbnailFile.getFileName().equals("")) {
      ActionError ne = uploadFile(productD, THUMBNAILS_DIR, thumbnailFile);
      if (ne != null) {
        ae.add("error", ne);
        return ae;
      }
      tagContentAsUpdated(request, productD, THUMBNAILS_DIR, pForm);
      needToSave = true;
    } else {
      if (createFlag && Utility.isSet(productD.getThumbnail())) {
        ae = cloneFile(productD, THUMBNAILS_DIR, productD.getThumbnail());
        if (ae.size() > 0) {
          return ae;
        }
        tagContentAsUpdated(request, productD, THUMBNAILS_DIR, pForm);
        needToSave = true;
      }
    }

    FormFile msdsFile = pForm.getMsdsFile();
    if (msdsFile != null && !msdsFile.getFileName().equals("")) {
      ActionError ne = uploadFile(productD, TYPE_MSDS, msdsFile);
      if (ne != null) {
        ae.add("error", ne);
        return ae;
      }
      tagContentAsUpdated(request, productD, TYPE_MSDS, pForm);
      needToSave = true;
    } else {
      if (createFlag && Utility.isSet(productD.getMsds())) {
        ae = cloneFile(productD, TYPE_MSDS, productD.getMsds());
        if (ae.size() > 0) {
          return ae;
        }
        tagContentAsUpdated(request, productD, TYPE_MSDS, pForm);
        needToSave = true;
      }
    }

    FormFile dedFile = pForm.getDedFile();
    if (dedFile != null && !dedFile.getFileName().equals("")) {
      log.info("dedFile is not empty");      
      ActionError ne = uploadFile(productD, TYPE_DED, dedFile);
      if (ne != null) {
        ae.add("error", ne);
        return ae;
      }
      tagContentAsUpdated(request, productD, TYPE_DED, pForm);
      needToSave = true;
    } else {
      log.info("dedFile == null || dedFile.getFileName().equals...");
      if (createFlag && Utility.isSet(productD.getDed())) {
    	log.info("Utility.isSet(productD.getDed() = true");
        ae = cloneFile(productD, TYPE_DED, productD.getDed());
        if (ae.size() > 0) {
          return ae;
        }
        tagContentAsUpdated(request, productD, TYPE_DED, pForm);
        needToSave = true;
      }
    }

    FormFile specFile = pForm.getSpecFile();
    if (specFile != null && !specFile.getFileName().equals("")) {
      ActionError ne = uploadFile(productD, TYPE_SPEC, specFile);
      if (ne != null) {
        ae.add("error", ne);
        return ae;
      }
      tagContentAsUpdated(request, productD, TYPE_SPEC, pForm);
      needToSave = true;
    } else {
      if (createFlag && Utility.isSet(productD.getSpec())) {
        ae = cloneFile(productD, TYPE_SPEC, productD.getSpec());
        if (ae.size() > 0) {
          return ae;
        }
        tagContentAsUpdated(request, productD, TYPE_SPEC, pForm);
        needToSave = true;
      }
    }
}
    // if we're doing an update, or an insert with one or more
    // uploaded files
    if (needToSave) {
      try {
        productD = catalogEjb.saveStoreCatalogProduct(storeId,
          pForm.getStoreCatalogId(), productD, user);
      } catch (Exception exc) {
        String mess = exc.getMessage();
        String oraNum= null;
        if (mess == null) mess = "";
        int ind = mess.indexOf("^clw^");
        if (ind >= 0) {
          mess = mess.substring(ind + "^clw^".length());
          ind = mess.indexOf("^clw^");
          if (ind > 0) mess = mess.substring(0, ind);
          ae.add("error", new ActionError("error.simpleGenericError", mess));
          return ae;
        } else {
        	int oraInd = mess.indexOf("ORA-") ;
        	if(oraInd > 0){
        		oraNum = mess.substring(oraInd, mess.indexOf(":", oraInd));
        	}
        	ae.add("error", new ActionError("error.systemError", oraNum));
          //throw exc;
        }
      }
    }

    int iId = productD.getProductId();
    ItemViewVector items = pForm.getItems();
    ItemView foundItemVw = null;
    for (int ii = 0; ii < items.size(); ii++) {
      ItemView iVw = (ItemView) items.get(ii);
      if (iId == iVw.getItemId()) {
        foundItemVw = iVw;
        break;
      }
    }
    if (foundItemVw == null) {
      foundItemVw = ItemView.createValue();
      items.add(0, foundItemVw);
      foundItemVw.setItemId(iId);
    }
    foundItemVw.setStoreId(storeId);
    foundItemVw.setCatalogId(pForm.getStoreCatalogId());
    CatalogCategoryDataVector ccDV = productD.getCatalogCategories();
    int categId = 0;
    String categName = "";
    if (ccDV != null && ccDV.size() > 0) {
      CatalogCategoryData ccD = (CatalogCategoryData) ccDV.get(0);
      categId = ccD.getCatalogCategoryId();
      categName = ccD.getCatalogCategoryShortDesc();
    }
    foundItemVw.setCategoryId(categId);
    foundItemVw.setCategory(categName);

    int mId = 0;
    BusEntityData mfgD = productD.getManufacturer();
    if (mfgD != null) {
      mId = mfgD.getBusEntityId();
    }
    foundItemVw.setManufId(mId);
    foundItemVw.setManufName(productD.getManufacturerName());
    foundItemVw.setManufSku(productD.getManufacturerSku());

    foundItemVw.setName(productD.getItemData().getShortDesc());
    foundItemVw.setSku(productD.getCatalogSkuNum());
    foundItemVw.setSize(productD.getSize());
    foundItemVw.setPack(productD.getPack());
    foundItemVw.setUom(productD.getUom());
    foundItemVw.setColor(productD.getColor());

    int dId = 0;
    BusEntityData dBeD = productD.getCatalogDistributor();
    if (dBeD != null) dId = dBeD.getBusEntityId();
    foundItemVw.setDistId(dId);

    foundItemVw.setDistName(productD.getCatalogDistributorName());
    foundItemVw.setDistSku(productD.getDistributorSku(dId));
    foundItemVw.setSelected(false);

    BusEntityDataVector certifiedCompanies = productInfoEjb.
              getBusEntityByCriteria(new BusEntitySearchCriteria(),
                      RefCodeNames.BUS_ENTITY_TYPE_CD.CERTIFIED_COMPANY);
    selectCertCompanies =
            new SelectableObjects(productD.getCertCompaniesBusEntityDV(),certifiedCompanies, BUS_ENTITY_ID_COMPARE);
    pForm.setSelectCertCompanies(selectCertCompanies);
    pForm.setProductUpdated(true);

    if (isUseItemsLinksBetweenStores) {
        getLinkedItems(request, pForm);
        pForm.setChangesOnItemEditPageFl(Boolean.TRUE);
    }
    else {
        if (RefCodeNames.STORE_TYPE_CD.ENTERPRISE.equalsIgnoreCase(appUser.getUserStore().getStoreType().getValue())) {
            getLinkedItems(request, pForm);
            pForm.setChangesOnItemEditPageFl(Boolean.TRUE);
        }
    }
	//Syncronize if parent store
	String isParentStore =
	    Utility.getPropertyValue(storeD.getMiscProperties(),
		      RefCodeNames.PROPERTY_TYPE_CD.IS_PARENT_STORE);
	boolean parentStoreFl = Utility.isTrue(isParentStore, false);
    if(parentStoreFl) {
	    try {
            Store storeEjb = factory.getStoreAPI();
			//List duplItems = storeEjb.checkForChildDuplItems(storeId);
			List duplItems = new LinkedList(); //Temporaraly !!!!!!!!!!!!!!! YK
			if(duplItems.size()>0) {
				String errMess = "Synchronization failed. Duplicated items found in child store(s): \n\r";
				for(Iterator iter = duplItems.iterator(); iter.hasNext();) {
					HashMap hm = (HashMap) iter.next();
					errMess += "Store = "+ hm.get("Child Store")+" "+
					"Parent Manuf = "+ hm.get("Parent Manuf") +" "+
					"Manuf Sku = "+ hm.get("Manuf Sku") + " "+
					"Item UOM = " + hm.get("UOM") +" "+
					"Duplicated Skus = "+ hm.get("Sku Numbers") + "\n\r";
				}
				ae.add("error", new ActionError("error.simpleGenericError", errMess));
				return ae;

			}
			storeEjb.synchronizeParentChildStoreItems(storeId, 0, productD.getProductId(), appUser);

		} catch (Exception e) {
			String errorMess = e.getMessage();
            String errorToShow = StringUtils.prepareUIMessage(request, errorMess);
			if(errorMess.equals(errorToShow)) {
				errorToShow = "System Error SIML 0001. Please contact the system support";
			}
            ae.add("error", new ActionError("error.simpleGenericError", errorToShow));
			return ae;
		}
	}

    return ae;
  }

  /**
   * Updates existing of adds new product to MASTER product
   *
   */
  public static ActionErrors saveDistItemInfo(HttpServletRequest request,
                                              StoreItemMgrForm pForm) throws
    Exception {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    String user = (String) session.getAttribute(Constants.USER_NAME);
    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    Catalog catalogEjb = factory.getCatalogAPI();

    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.
      APP_USER);
    StoreData storeD = appUser.getUserStore();
    if (storeD == null) {
      ae.add("error",
             new ActionError("error.simpleGenericError", "No store info"));
      return ae;
    }

    ProductData productD = pForm.getProduct();

    // save distributor sku, uom, pack
    BusEntityDataVector mappedDist = productD.getMappedDistributors();
    for (int ii = 0; ii < mappedDist.size(); ii++) {
      BusEntityData dist = (BusEntityData) mappedDist.get(ii);
      int distId = dist.getBusEntityId();
      String pack = request.getParameter("distributor[" + distId + "].pack");
      String sku = request.getParameter("distributor[" + distId + "].sku");
      String uom = request.getParameter("distributor[" + distId + "].uom");
      productD.setDistributorPack(distId, pack);
      productD.setDistributorSku(distId, sku);
      productD.setDistributorUom(distId, uom);
    }

    // add new mappings
    String[] newMappings = request.getParameterValues("newMapping");
    if (newMappings != null && newMappings.length > 0) {
      for (int i = 0; i < newMappings.length; i++) {
        ItemMappingData newMapping = ItemMappingData.createValue();
        int distId = (new Integer(newMappings[i])).intValue();
        String uom = request.getParameter("newMapping[" + newMappings[i] +
                                          "].uom");
        String pack = request.getParameter("newMapping[" + newMappings[i] +
                                           "].pack");
        String sku = request.getParameter("newMapping[" + newMappings[i] +
                                          "].sku");

        newMapping.setItemUom(uom);
        newMapping.setItemPack(pack);
        newMapping.setItemNum(sku);
        newMapping.setBusEntityId(distId);
        newMapping.setItemMappingCd(RefCodeNames.ITEM_MAPPING_CD.
                                    ITEM_DISTRIBUTOR);
        newMapping.setStatusCd(RefCodeNames.ITEM_STATUS_CD.ACTIVE);
        productD.addDistributorMapping(newMapping);

        BusEntityData newDist = BusEntityData.createValue();
        newDist.setBusEntityId(distId);
        DistributorData d = pForm.getDistItemByEntityId(distId);
        if (d != null) {
          newDist.setShortDesc(d.getBusEntity().getShortDesc());
          newDist.setErpNum(d.getBusEntity().getErpNum());
        }
        productD.addMappedDistributor(newDist);
        productD.setDistributorPack(distId, pack);
        productD.setDistributorSku(distId, sku);
        productD.setDistributorUom(distId, uom);
      }
    }

    try {
      productD = catalogEjb.saveDistributorMapping(productD, user);
    } catch (Exception exc) {
      String mess = exc.getMessage();
      if (mess == null) mess = "";
      int ind = mess.indexOf("^clw^");
      if (ind >= 0) {
        mess = mess.substring(ind + "^clw^".length());
        ind = mess.indexOf("^clw^");
        if (ind > 0) mess = mess.substring(0, ind);
        ae.add("error", new ActionError("error.simpleGenericError", mess));
        return ae;
      } else {
        throw exc;
      }
    }

    return ae;
  }


  public static ActionErrors savePricing(HttpServletRequest request,
                                         StoreItemMgrForm pForm) throws
    Exception {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    String user = (String) session.getAttribute(Constants.USER_NAME);
    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    Contract contractEjb = factory.getContractAPI();

    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.
      APP_USER);
    StoreData storeD = appUser.getUserStore();
    if (storeD == null) {
      ae.add("error",
             new ActionError("error.simpleGenericError", "No store info"));
      return ae;
    }

    ProductData productD = pForm.getProduct();
    // 1. checked account for updating
    String[] savedPrices = request.getParameterValues("savePrice");
    if (savedPrices == null || savedPrices.length == 0) {
      return ae;
    }

    try {
      // 2. save prices for all active contracts of every selected account
      for (int i=0; i<savedPrices.length; i++) {
        int accountId = (new Integer(savedPrices[i])).intValue();
        // contract list for this account
        ContractDataVector contracts = pForm.getAccountContractList(accountId);
        ContractItemPriceViewVector prices = pForm.getAccountItemPriceList(accountId);
        ContractItemPriceView price = (ContractItemPriceView)prices.get(0);
        BigDecimal distCost = new BigDecimal(request.getParameter("distCost["+accountId+"]"));
        BigDecimal custPrice = new BigDecimal(request.getParameter("custPrice["+accountId+"]"));
        price.setDistCost(distCost);
        price.setPrice(custPrice);
        for (int j=0; j<contracts.size(); j++) {
          ContractData contract = (ContractData)contracts.get(j);
          contractEjb.updateContractItemCosts(price, contract.getContractId(), user);
        }
      }
      initPrices(request, pForm, productD, storeD.getBusEntity().getBusEntityId());
    } catch (Exception exc) {
      String mess = exc.getMessage();
      if (mess == null) mess = "";
      int ind = mess.indexOf("^clw^");
      if (ind >= 0) {
        mess = mess.substring(ind + "^clw^".length());
        ind = mess.indexOf("^clw^");
        if (ind > 0) mess = mess.substring(0, ind);
        ae.add("error", new ActionError("error.simpleGenericError", mess));
        return ae;
      } else {
        throw exc;
      }
    }

    return ae;
  }


///////////////////////////////////////
  /**
   * <code>ile</code> is a method that completes the action of
   * uploading an image file, a MSDS file, a DED file or a product spec.
   *
   * @param productD a <code>ProductData</code> value that is used
   * to get the product item_id.  This is used to name the resulting
   * upload file (e.g. 1010.jpg, 1010.pdf, etc.)  The product image,
   * msds, ded field is filled with the resultant upload filename.
   * @param fileType a <code>String</code> value that is one of
   * "images", "msds", "ded", "spec"
   * @param file a <code>FormFile</code> value  The FormFile to be
   * read.
   * @return an <code>ActionError</code> value
   */
    public static ActionError uploadFile(ProductData productD, String fileType,
            FormFile file) {
        try {
            ActionError ae = uploadFile(productD, fileType, file.getFileName(),
                    file.getFileSize(), file.getInputStream());
            return ae;
        } catch (IOException ioe) {
            return new ActionError("item.master.upload_file_error",
                                 ioe.getMessage());
        } finally {
            if (file != null) {
                file.destroy();
            }
        }
    }

    public static ActionError uploadFile(ProductData productD, String fileType,
            String url) {
        try {
            byte data[] = HttpClientUtil.load(url);
            ByteArrayInputStream bas =new ByteArrayInputStream(data);
            return uploadFile(productD, fileType, url, data.length, bas);
        } catch (Exception e) {
            return new ActionError("error.simpleGenericError",
                    "Can't load data from '" + url + "'!");
        }

    }
    public static ActionError uploadFile(ProductData productD, String fileType,
            String uploadFileName, int fileSize, InputStream stream) {
    ActionError ae = null;
    String fileExt = null;
    log.info("uploadFile(): uploadFileName: "+uploadFileName);
    // Don't know any other way to discern if the file exists
    // or is readable, or some other problem
    if (fileSize == 0) {
      ae = new ActionError("item.master.bad_upload_file",
                           uploadFileName);
      return ae;
    }

    // get the file extension (e.g. ".jpg", ".pdf", etc.)
    int i = uploadFileName.lastIndexOf(".");
    if (i < 0) {
      fileExt = "";
    } else {
      fileExt = uploadFileName.substring(i);
    }

    String basepath = "";
    String fileName = "";
    File file = null;
    boolean fileNameLoop = true;
    int index = 0;
    String stringIndex = "";
    do {
    // this is the path to be saved in the database
	    basepath =
	      "/en/products/" + fileType + "/"
	      + String.valueOf(productD.getItemData().getItemId())
	      + stringIndex
	      + fileExt;

	    // this is the absolute path where we will be writing
	    fileName = System.getProperty("webdeploy") + basepath;
	    file = new File(fileName);
	    boolean nameIsBusy = file.exists();
	    if (!nameIsBusy) break;
	    index++;
	    stringIndex = "-" + Integer.toString(index);
    } while(fileNameLoop);

    try {
      //retrieve the file data
      //ByteArrayOutputStream baos = new ByteArrayOutputStream();

      //InputStream stream = file.getInputStream();
      ///File file = new File(fileName);
      log.info("uploadFile(): fileName: "+fileName);
      boolean boo = file.exists();
log.info("uploadFile(): exists: "+boo);
      boo = file.canRead();
log.info("uploadFile(): can read: "+boo);
      boo = file.canWrite();
log.info("uploadFile(): can write: "+boo);
      if(boo){
          boo = file.delete();
log.info("uploadFile(): deleted: "+boo);
      }else{
    	  String parentDir = file.getParent();
    	  File dir = new File(parentDir);
          if(!dir.exists()){
              dir.mkdir();
          }
      }
      boo = file.createNewFile();
log.info("uploadFile(): boo2: "+boo);
      OutputStream bos = new FileOutputStream(file);
      int bytesRead = 0;
      byte[] buffer = new byte[8192];
      while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
        bos.write(buffer, 0, bytesRead);
      }
      bos.flush();
      bos.close();
      bos = null;
      //close the stream
      stream.close();
    } catch (IOException ioe) {
        ioe.printStackTrace();
      ae = new ActionError("item.master.upload_file_error",
                           ioe.getMessage());
      return ae;
    }

    if (fileType.equals("images")) {
      productD.setImage(basepath);
    } else if (fileType.equals("msds")) {
      productD.setMsds(basepath);
    } else if (fileType.equals("ded")) {
      productD.setDed(basepath);
    } else if (fileType.equals("spec")) {
      productD.setSpec(basepath);
    } else if (fileType.equals(THUMBNAILS_DIR)) {
      productD.setThumbnail(basepath);
    }
    return ae;
  }

  private static void tagContentAsUpdated
    (HttpServletRequest request, ProductData productD,
     String fileType, StoreItemMgrForm pForm) throws Exception {

    HttpSession session = request.getSession();
    // tag this content as updated in the clw_content table
    // for other servers to pull.
    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    Content cont = factory.getContentAPI();
    String host = (new SessionTool(request)).getInternalHostToken();

    String basepath = "";

    // Find out if we save Blobs/binary data in the clw_content table or in E3 Storage    
    String storageType = Utility.strNN(System.getProperty("storage.system.item"));
    log.info(".tagContentAsUpdated(): storageType1 = " + storageType);
    if (storageType.trim().equals(RefCodeNames.BINARY_DATA_STORAGE_TYPE.E3_STORAGE)) {
    	storageType = RefCodeNames.BINARY_DATA_STORAGE_TYPE.E3_STORAGE;
    } else {
    	storageType = RefCodeNames.BINARY_DATA_STORAGE_TYPE.DATABASE;
    }
    log.info(".tagContentAsUpdated(): storageType2 = " + storageType);
    
    if (fileType.equals("images")) {
      String t = productD.getImage();
      if (null == t || t.length() == 0) {
        return;
      }
      basepath = productD.getImage();
      pForm.setImageStorageTypeCd(storageType);
    } else if (fileType.equals("msds")) {
      String t = productD.getMsds();
      if (null == t || t.length() == 0) {
        return;
      }
      basepath = productD.getMsds();
      pForm.setMsdsStorageTypeCd(storageType);
    } else if (fileType.equals("ded")) {
      String t = productD.getDed();
      if (null == t || t.length() == 0) {
        return;
      }
      basepath = productD.getDed();
      pForm.setDedStorageTypeCd(storageType);
    } else if (fileType.equals("spec")) {
      String t = productD.getSpec();
      if (null == t || t.length() == 0) {
        return;
      }
      basepath = productD.getSpec();
      pForm.setSpecStorageTypeCd(storageType);
    } else if (fileType.equals("thumbnails")) {
      String t = productD.getThumbnail();
      if (null == t || t.length() == 0) {
        return;
      }
      basepath = productD.getThumbnail();
      pForm.setThumbnailStorageTypeCd(storageType);
    } else {
      log.info(" tagContentAsUpdated, unsupported type="
                         + fileType);
      String t = productD.getImage();
      if (null == t || t.length() == 0) {
        return;
      }
      basepath = productD.getImage();
      pForm.setImageStorageTypeCd(storageType);
    }

    if (basepath.indexOf(".") != 0) {
      basepath = "." + basepath;
    }

    log.info(" tagContentAsUpdated, host=" + host
                       + " fileType=" + fileType
                       + " basepath=" + basepath);
    // Find out if we save Blobs/binary data in the clw_content table or in E3 Storage   
    
    //String storageType = System.getProperty("storage.system.item");
    //log.info(".tagContentAsUpdated(): storageType = " + storageType);
    
    if ((storageType == null) || storageType.trim().equals("")  || storageType.trim().equals("@storage.system.item@")) {
    	storageType = RefCodeNames.BINARY_DATA_STORAGE_TYPE.DATABASE;
    }
    if (storageType.trim().equals(RefCodeNames.BINARY_DATA_STORAGE_TYPE.E3_STORAGE)) {
    	cont.addContentSaveImageE3Storage(host, basepath, "ItemImage");
    } else if (storageType.trim().equals(RefCodeNames.BINARY_DATA_STORAGE_TYPE.DATABASE)) {    	
        cont.addContentSaveImage(host, basepath, "ItemImage");
    } else { 
        log.info(" tagContentAsUpdated, unsupported storage type="
                + storageType);
    	cont.addContentSaveImage(host, basepath, "ItemImage"); //Default
    }
    //    cont.refreshBinaryData(true);
  }


  public static ActionErrors cloneFile(ProductData productD,
                                       String fileType,
                                       String fromFileName) {
    ActionErrors ae = new ActionErrors();

    if (!fromFileName.startsWith("/")) {
      fromFileName = "/" + fromFileName;
    }

    File file = new File(System.getProperty("webdeploy") + fromFileName);
    if (!file.exists()) {
      String errMess = "Can't find " + fileType + " file";
      ae.add("error", new ActionError("error.systemError", errMess));
      return ae;
    }
    long fileLength = file.length();
    //create output name
    int ind = fromFileName.lastIndexOf(".");
    String ext = (ind < 0) ? "" : fromFileName.substring(ind);
    String cloneFileName = (ind < 0) ? fromFileName :
                           fromFileName.substring(0, ind);

    ind = cloneFileName.lastIndexOf("/");
    if (ind >= 0) cloneFileName = cloneFileName.substring(0, ind);
    cloneFileName = cloneFileName + "/" + productD.getProductId() + ext;

    try {

      FileInputStream fis = new FileInputStream(file);
      FileOutputStream fos = new FileOutputStream(System.getProperty("webdeploy") + cloneFileName);
      byte[] buffer = new byte[(int) file.length()];
      fis.read(buffer);
      fos.write(buffer);
      fos.close();
      fis.close();
    } catch (IOException ioe) {
      ae.add("error", new ActionError("item.master.upload_file_error",
                                      ioe.getMessage()));
      return ae;
    }

    if (fileType.equals("images")) {
      productD.setImage(cloneFileName);
    } else if (fileType.equals(THUMBNAILS_DIR)) {
        productD.setThumbnail(cloneFileName);
    }else if (fileType.equals("msds")) {
      productD.setMsds(cloneFileName);
    } else if (fileType.equals("ded")) {
      productD.setDed(cloneFileName);
    } else if (fileType.equals("spec")) {
      productD.setSpec(cloneFileName);
    }

    return ae;
  }

  /**
   * <code>init</code> method.
   *
   * @param request a <code>HttpServletRequest</code> value
   * @param pForm an <code>ActionForm</code> value
   * @exception Exception if an error occurs
   */
  public static ActionErrors initNew(HttpServletRequest request,
                                     StoreItemMgrForm pForm) throws Exception {
    ActionErrors ae = new ActionErrors();
    
    HttpSession session = request.getSession();
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    StoreData storeD = appUser.getUserStore();
    int storeId = storeD.getBusEntity().getBusEntityId();
    
    pForm.setRetAction(request.getParameter("retaction"));
    ProductData productD = new ProductData();
    productD.setCatalogStructure(pForm.getStoreCatalogId());
    productD.setImage("");
    
    //STJ-3858
    parseItemDataFields(productD,storeId);
    if(productD.getDataFieldProperties()!=null && productD.getDataFieldProperties().size()>0) {
    	pForm.setDataFieldProperties(productD.getDataFieldProperties());
    }
    
    pForm.setProduct(productD);
    pForm.setSelectedManufId(0);
    pForm.setSelectedCategoryId(0);
    pForm.setManufacturerName("");
    pForm.setManufacturerSku("");
    pForm.setListPrice("");
    pForm.setCostPrice("");
    pForm.setNewDistributorId("");
    pForm.setNewDistributorSku("");
    pForm.setNewDistributorUom("");
    pForm.setNewDistributorPack("");
    pForm.setUom("");
    pForm.setHazmat("false");
    pForm.setNewDistributor(null);
    pForm.setDistributorBox(new String[0]);
    pForm.setDistrListOffset(0);
    pForm.setSkuNum("");
    initConstantList(request);

    pForm.setMfg1Id("");
    pForm.setMfg1Name("");
    pForm.setMfg1ItemSku("");

    pForm.setDistInfoFlag(false);

    //pForm.setCategoryTree(null);
    pForm.setNewDistributor(null);
    pForm.setRetAction(null);

    pForm.setImageFile(null);
    pForm.setMsdsFile(null);
    pForm.setDedFile(null);
    pForm.setSpecFile(null);
    pForm.setIsEditable(true);

    //Bug # 1296
    APIAccess factory = APIAccess.getAPIAccess();
    Manufacturer manufEjb = factory.getManufacturerAPI();
    
    ae = readStoreManufacturers(manufEjb, pForm, storeId);
    
    return ae;
  }

  public static ActionErrors initClone(HttpServletRequest request,
                                       StoreItemMgrForm pForm) throws
    Exception {

    ActionErrors ae = new ActionErrors();
    ae = initItem(request, pForm);
    if (ae.size() > 0)return ae;
    ProductData productD = pForm.getProduct();
    productD.setProductId(0);
    productD.setSkuNum(0);
    productD.setExpDate(null);
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    Date today = new Date();
    productD.setEffDate(today);
    pForm.setEffDate(sdf.format(today));
    pForm.setExpDate(null);
    String shortDesc = "Clone of >>>" + productD.getShortDesc();
    if (shortDesc.length() > 255) shortDesc = shortDesc.substring(0, 255);
    productD.setShortDesc(shortDesc);
    productD.setDistributorMappings(null);
    productD.setMappedDistributors(null);
    productD.setCatalogDistrMapping(null);
    pForm.setProduct(productD);
    pForm.setIsEditable(true);

    return ae;
  }


  public static ActionErrors initMjCategory(HttpServletRequest request,
                                            StoreItemMgrForm pForm) throws
    Exception {

    ActionErrors ae = new ActionErrors();
    String categoryIdS = request.getParameter("categoryId");
    int categoryId = 0;
    try {
      categoryId = Integer.parseInt(categoryIdS);

    } catch (Exception exc) {
      String errorMess = "Wrong major categoryId format: " + categoryIdS;
      ae.add("error", new ActionError("error.simpleGenericError", errorMess));
      return ae;
    }
    boolean foundFl = false;
    ItemDataVector categDV = pForm.getStoreMajorCategories();
    for (Iterator iter = categDV.iterator(); iter.hasNext(); ) {
      ItemData categD = (ItemData) iter.next();
      if (categD.getItemId() == categoryId) {
        foundFl = true;
        pForm.setMajorCategoryId(categoryId);
        pForm.setMajorCategoryName(categD.getShortDesc());
        break;
      }
    }
    if (!foundFl) {
      String errorMess = "Major category doesn't exist. CategoryId: " +
                         categoryIdS;
      ae.add("error", new ActionError("error.simpleGenericError", errorMess));
      return ae;
    }

    return ae;
  }


  public static ActionErrors initNewMjCategory(HttpServletRequest request,
                                               StoreItemMgrForm pForm) throws
    Exception {

    ActionErrors ae = new ActionErrors();
    pForm.setMajorCategoryId(0);
    pForm.setMajorCategoryName("");
    return ae;
  }

  public static ActionErrors saveMjCategory(HttpServletRequest request,
                                            StoreItemMgrForm pForm) throws
    Exception {

    HttpSession session = request.getSession();
    String user = (String) session.getAttribute(Constants.USER_NAME);
    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();
    Catalog catalogEjb = factory.getCatalogAPI();

    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.
      APP_USER);
    String userName = appUser.getUser().getUserName();

    ActionErrors ae = new ActionErrors();
    if (!Utility.isSet(pForm.getMajorCategoryName())) {
      String errorMess = "Empty major category name";
      ae.add("error", new ActionError("error.simpleGenericError", errorMess));
      return ae;
    }

    int categoryId = pForm.getMajorCategoryId();
    String categoryName = pForm.getMajorCategoryName();
    ItemDataVector categDV = pForm.getStoreMajorCategories();

    ae = checkOnCategoryExistence(categoryId, categoryName, null, categDV);
    if (ae.size() > 0) {
        return ae;
    }

    try {
      catalogEjb.saveStoreMajorCategory(pForm.getStoreCatalogId(), categoryId,
                                        categoryName, userName);
    } catch (Exception exc) {
      String mess = exc.getMessage();
      if (mess == null) mess = "";
      int ind = mess.indexOf("^clw^");
      if (ind >= 0) {
        mess = mess.substring(ind + "^clw^".length());
        ind = mess.indexOf("^clw^");
        if (ind > 0) mess = mess.substring(0, ind);
        ae.add("error", new ActionError("error.simpleGenericError", mess));
        return ae;
      } else {
        throw exc;
      }
    }
    ItemDataVector majorCategoryDV =
      catalogInfEjb.getStoreMajorCategories(pForm.getStoreCatalogId());
    pForm.setStoreMajorCategories(majorCategoryDV);
    readStoreCategories(catalogInfEjb, pForm);
    return ae;
  }

  public static ActionErrors deleteMjCategory(HttpServletRequest request,
                                              StoreItemMgrForm pForm) throws
    Exception {

    HttpSession session = request.getSession();
    String user = (String) session.getAttribute(Constants.USER_NAME);
    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();
    Catalog catalogEjb = factory.getCatalogAPI();

    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.
      APP_USER);
    String userName = appUser.getUser().getUserName();

    ActionErrors ae = new ActionErrors();
    int categoryId = pForm.getMajorCategoryId();
    String categoryName = pForm.getMajorCategoryName();
    ItemDataVector categDV = pForm.getStoreMajorCategories();
    boolean foundFl = false;
    for (Iterator iter = categDV.iterator(); iter.hasNext(); ) {
      ItemData categD = (ItemData) iter.next();
      if (categoryId == categD.getItemId()) {
        foundFl = true;
        break;
      }
    }
    if (!foundFl) {
      String errorMess = "The major category doesn't exist: " + categoryName;
      ae.add("error", new ActionError("error.simpleGenericError", errorMess));
      return ae;
    }
    try {
      catalogEjb.deleteStoreMajorCategory(pForm.getStoreCatalogId(),
                                          categoryId);
      pForm.setMajorCategoryId(0);
      pForm.setMajorCategoryName("");
    } catch (Exception exc) {
      String mess = exc.getMessage();
      if (mess == null) mess = "";
      int ind = mess.indexOf("^clw^");
      if (ind >= 0) {
        mess = mess.substring(ind + "^clw^".length());
        ind = mess.indexOf("^clw^");
        if (ind > 0) mess = mess.substring(0, ind);
        ae.add("error", new ActionError("error.simpleGenericError", mess));
        return ae;
      } else {
        throw exc;
      }
    }
    ItemDataVector majorCategoryDV =
      catalogInfEjb.getStoreMajorCategories(pForm.getStoreCatalogId());
    pForm.setStoreMajorCategories(majorCategoryDV);
    readStoreCategories(catalogInfEjb, pForm);
    return ae;
  }

  public static ActionErrors initMultiproduct(HttpServletRequest request,
    StoreItemMgrForm pForm) throws Exception {

    ActionErrors ae = new ActionErrors();
    String multiproductIdS = request.getParameter("multiproductId");
    int multiproductId = 0;
    try {
        multiproductId = Integer.parseInt(multiproductIdS);

    } catch (Exception exc) {
        String errorMess = "Wrong multiproductId format: " + multiproductIdS;
        ae.add("error", new ActionError("error.simpleGenericError", errorMess));
        return ae;
    }
    boolean foundFl = false;
    MultiproductViewVector multiproductsV = pForm.getMultiproducts();
    for (Iterator iter = multiproductsV.iterator(); iter.hasNext(); ) {
        MultiproductView multiproductsView = (MultiproductView) iter.next();
        ItemData itemD = multiproductsView.getItemData();
        if (itemD.getItemId() == multiproductId) {
            foundFl = true;
            pForm.setEditMultiproductId(multiproductId);
            pForm.setEditMultiproductName(itemD.getShortDesc());
            break;
        }
    }
    if (!foundFl) {
        String errorMess = "Multi product doesn't exist. MultiproductId: " + multiproductIdS;
        ae.add("error", new ActionError("error.simpleGenericError", errorMess));
        return ae;
    }

    return ae;
  }

  public static ActionErrors initCategory(HttpServletRequest request,
                                          StoreItemMgrForm pForm) throws
    Exception {

    ActionErrors ae = new ActionErrors();
    String categoryIdS = request.getParameter("categoryId");
    int categoryId = 0;
    try {
      categoryId = Integer.parseInt(categoryIdS);

    } catch (Exception exc) {
      String errorMess = "Wrong categoryId format: " + categoryIdS;
      ae.add("error", new ActionError("error.simpleGenericError", errorMess));
      return ae;
    }
    boolean foundFl = false;
    // NG moved from init()
    ae = prepareStoreCategories ( request, pForm );
    if ( ae.size() != 0){
       return ae;
    }
   //
    CatalogCategoryDataVector categDV = pForm.getStoreCategories();
    for (Iterator iter = categDV.iterator(); iter.hasNext(); ) {
      CatalogCategoryData categD = (CatalogCategoryData) iter.next();
      ItemData categItemD = categD.getItemData();
      if (categItemD.getItemId() == categoryId) {
        foundFl = true;
        pForm.setEditCategoryId(categoryId);
        pForm.setEditCategoryName(categItemD.getShortDesc());
        pForm.setAdminCategoryName(categItemD.getLongDesc());
        ItemData mjCategD = categD.getMajorCategory();
        if (mjCategD != null) {
          pForm.setMajorCategoryId(mjCategD.getItemId());
          pForm.setMajorCategoryName("");
        } else {
          pForm.setMajorCategoryId(0);
          pForm.setMajorCategoryName("");
        }
		boolean editFl = isCategoryEditable(request, pForm);
		pForm.setCanEditFl(editFl);
        break;
      }
    }
    if (!foundFl) {
      String errorMess = "Category doesn't exist. CategoryId: " + categoryIdS;
      ae.add("error", new ActionError("error.simpleGenericError", errorMess));
      return ae;
    }

    return ae;
  }

   private static boolean isCategoryEditable(HttpServletRequest request,
                                          StoreItemMgrForm pForm)
   throws  Exception {
    HttpSession session = request.getSession();
	boolean editFl = true;
    CleanwiseUser appUser =
	    (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    StoreData storeD = appUser.getUserStore();
    String parentStoreIdStr =
	    Utility.getPropertyValue(storeD.getMiscProperties(),
                                 RefCodeNames.PROPERTY_TYPE_CD.PARENT_STORE_ID);
    if (Utility.isSet(parentStoreIdStr)) {
		int parentStoreId = Integer.parseInt(parentStoreIdStr);
		APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
		CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();
		ItemDataVector parentCategItemDV =
		          catalogInfEjb.getStoreCategories(parentStoreId);
		String categName = pForm.getEditCategoryName();
		if(categName==null) {
		   categName = "";
		} else {
		   categName = categName.trim();
		}
		String adminCategName = pForm.getAdminCategoryName();
		if(adminCategName==null) {
		   adminCategName = "";
		} else {
		   adminCategName = adminCategName.trim();
		}
		String fullCategName = categName+"@"+adminCategName;
		for(Iterator iter = parentCategItemDV.iterator(); iter.hasNext(); ) {
			ItemData iD = (ItemData) iter.next();
			String parentCateg =
			    iD.getShortDesc().trim() +"@"+
				      ((iD.getLongDesc()!=null)? iD.getLongDesc().trim():"");
			if(fullCategName.equalsIgnoreCase(parentCateg)) {
			    editFl = false;
				break;
			}
		}
	}
	return editFl;
  }

  public static ActionErrors initNewCategory(HttpServletRequest request,
                                             StoreItemMgrForm pForm) throws
    Exception {

    ActionErrors ae = new ActionErrors();
    pForm.setEditCategoryId(0);
    pForm.setEditCategoryName("");
	pForm.setCanEditFl(true);
    pForm.setAdminCategoryName("");
    pForm.setMajorCategoryId(0);
    pForm.setMajorCategoryName("");
    return ae;
  }

  public static ActionErrors initNewMultiproduct(HttpServletRequest request,
          StoreItemMgrForm pForm) throws Exception {
    ActionErrors ae = new ActionErrors();
    pForm.setEditMultiproductId(0);
    pForm.setEditMultiproductName("");
    return ae;
  }


  public static ActionErrors saveMultiproduct(HttpServletRequest request,
                                          StoreItemMgrForm pForm) throws Exception {
    HttpSession session = request.getSession();
    String user = (String) session.getAttribute(Constants.USER_NAME);
    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();
    Catalog catalogEjb = factory.getCatalogAPI();
    ActionErrors ae = new ActionErrors();

    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    StoreData storeD = appUser.getUserStore();
    if (storeD == null) {
      ae.add("error",
             new ActionError("error.simpleGenericError", "No store info"));
      return ae;
    }
    int storeId = storeD.getBusEntity().getBusEntityId();
    String userName = appUser.getUser().getUserName();

    if (!Utility.isSet(pForm.getEditMultiproductName())) {
      String errorMess = "Empty multi product name";
      ae.add("error", new ActionError("error.simpleGenericError", errorMess));
      return ae;
    }

    int multiproductId = pForm.getEditMultiproductId();
    String multiproductName = pForm.getEditMultiproductName();
    CatalogCategoryDataVector categDV = pForm.getStoreCategories();
//    for (Iterator iter = categDV.iterator(); iter.hasNext(); ) {
//      CatalogCategoryData categD = (CatalogCategoryData) iter.next();
//      ItemData categItemD = categD.getItemData();
//      if (categItemD.getShortDesc().equals(categoryName)) {
//        if ((categoryId != categItemD.getItemId()) && (categD.getTreeLevel()==1)) {
//          String errorMess = "The name already exists: " + categoryName;
//          ae.add("error",
//                 new ActionError("error.simpleGenericError", errorMess));
//          return ae;
//        }
//      }
//    }
    try {
      MultiproductViewVector multiproductVV = catalogInfEjb.getStoreMultiproducts(storeId, multiproductName);
      if (Utility.isSet(multiproductVV) 
    		  && ((MultiproductView)multiproductVV.get(0)).getMultiproductId() != multiproductId ) {
    	  
		String errorMess = ClwI18nUtil.getMessage(request,"error.store.item.multiproduct.duplicateName", null);
        ae.add("error", new ActionError("error.simpleGenericError", errorMess));
        return ae;
      }
	
      catalogEjb.saveStoreMultiproduct(pForm.getStoreCatalogId(),
          multiproductId, multiproductName, userName
      );
    } catch (Exception exc) {
      String mess = exc.getMessage();
      if (mess == null) mess = "";
      int ind = mess.indexOf("^clw^");
      if (ind >= 0) {
        mess = mess.substring(ind + "^clw^".length());
        ind = mess.indexOf("^clw^");
        if (ind > 0) mess = mess.substring(0, ind);
        ae.add("error", new ActionError("error.simpleGenericError", mess));
        return ae;
      } else {
        throw exc;
      }
    }
    readStoreMultiproducts(catalogInfEjb, pForm, storeId);
    return ae;
  }

  public static ActionErrors saveCategory(HttpServletRequest request,
                                          StoreItemMgrForm pForm) throws
    Exception {

    HttpSession session = request.getSession();
    String user = (String) session.getAttribute(Constants.USER_NAME);
    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();
    Catalog catalogEjb = factory.getCatalogAPI();
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.
      APP_USER);
    String userName = appUser.getUser().getUserName();

    ActionErrors ae = new ActionErrors();
    if (!Utility.isSet(pForm.getEditCategoryName())) {
      String errorMess = "Empty category name";
      ae.add("error", new ActionError("error.simpleGenericError", errorMess));
      return ae;
    }

    int categoryId = pForm.getEditCategoryId();
    String categoryName = pForm.getEditCategoryName();
    String adminCategoryName = pForm.getAdminCategoryName();
    pForm.setStoreCategories(catalogInfEjb.getAllStoreCatalogCategories(pForm.getStoreCatalogId()));
    CatalogCategoryDataVector categDV = pForm.getStoreCategories();
    ae = checkOnCategoryExistence(categoryId, categoryName, adminCategoryName, categDV);
    if (ae.size() > 0) {
        return ae;
    }

    try {
        catalogEjb.saveStoreCategory(pForm.getStoreCatalogId(), categoryId, categoryName,
            adminCategoryName, pForm.getMajorCategoryId(), userName);
    } catch (Exception exc) {
      String mess = exc.getMessage();
      if (mess == null) mess = "";
      int ind = mess.indexOf("^clw^");
      if (ind >= 0) {
        mess = mess.substring(ind + "^clw^".length());
        ind = mess.indexOf("^clw^");
        if (ind > 0) mess = mess.substring(0, ind);
        ae.add("error", new ActionError("error.simpleGenericError", mess));
        return ae;
      } else {
        throw exc;
      }
    }
    ae = synchronyzeCategories(request,pForm);
	if(ae.size()>0) {
	   return ae;
	}
    readStoreCategories(catalogInfEjb, pForm);
    return ae;
  }

  private static ActionErrors synchronyzeCategories(HttpServletRequest request,
                                          StoreItemMgrForm pForm)
  throws  Exception {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
	Store storeEjb = factory.getStoreAPI();
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.
      APP_USER);
    String userName = appUser.getUser().getUserName();
    StoreData storeD = appUser.getUserStore();
    String isParentStore = Utility.getPropertyValue(storeD.getMiscProperties(),
                                RefCodeNames.PROPERTY_TYPE_CD.IS_PARENT_STORE);
    boolean parentStoreFl = Utility.isTrue(isParentStore, false);
    if(parentStoreFl) {
	    try {
			storeEjb.synchronizeParentChildStoreCateg(storeD.getBusEntity().getBusEntityId(), 0, appUser);
        } catch (Exception exc) {
			String mess = exc.getMessage();
			String errMess = StringUtils.prepareUIMessage(request,mess);
			if(!Utility.isSet(errMess)) {
				errMess = "System error during categroy sychronization";
			}
			ae.add("error", new ActionError("error.simpleGenericError", errMess));
		    return ae;
		}
	}
	return ae;
  }

  public static ActionErrors moveCategory(HttpServletRequest request,
          StoreItemMgrForm pForm) throws
          Exception {

      HttpSession session = request.getSession();
      String user = (String) session.getAttribute(Constants.USER_NAME);
      APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
      CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();
      Catalog catalogEjb = factory.getCatalogAPI();
      CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
      String userName = appUser.getUser().getUserName();

      ActionErrors ae = new ActionErrors();
      if (!Utility.isSet(pForm.getEditCategoryName())) {
          String errorMess = "Empty category name";
          ae.add("error", new ActionError("error.simpleGenericError", errorMess));
          return ae;
      }

      if (pForm.getEditCategoryId() == pForm.getSelectedId()) {
          String errorMess = "A category cannot be moved to itself";
          ae.add("error", new ActionError("error.simpleGenericError", errorMess));
          return ae;
      }

      int categoryId = pForm.getEditCategoryId();
      String categoryName = pForm.getEditCategoryName();
      String adminCategoryName = pForm.getAdminCategoryName();
      try {
            CatalogCategoryDataVector categrsDV = catalogInfEjb.getCatalogChildCategories(pForm.getStoreCatalogId(), pForm.getSelectedId());
            ae = checkOnCategoryExistence(categoryId, categoryName, adminCategoryName, categrsDV);
            if (ae.size() > 0) {
                return ae;
            }

          int pLev = 0;
          CatalogCategoryDataVector myChildCategoriesDV = catalogInfEjb.getCatalogChildCategories(pForm.getStoreCatalogId(), categoryId);
          if (myChildCategoriesDV.size() > 0) {
              pLev = 1;
              for (Iterator iter = myChildCategoriesDV.iterator(); iter.hasNext();) {
                  CatalogCategoryData categD = (CatalogCategoryData) iter.next();
                  ItemData categItemD = categD.getItemData();
                  int categId = categItemD.getItemId();
                  CatalogCategoryDataVector myGChildCategoriesDV = catalogInfEjb.getCatalogChildCategories(pForm.getStoreCatalogId(), categId);
                  if (myGChildCategoriesDV.size() > 0) {
                      pLev = 2;
                      for (Iterator iter2 = myGChildCategoriesDV.iterator(); iter2.hasNext();) {
                          CatalogCategoryData categ2D = (CatalogCategoryData) iter2.next();
                          ItemData categ2ItemD = categ2D.getItemData();
                          int categ2Id = categ2ItemD.getItemId();
                          CatalogCategoryDataVector myGChildCategories2DV = catalogInfEjb.getCatalogChildCategories(pForm.getStoreCatalogId(), categ2Id);
                          if (myGChildCategories2DV.size() > 0) {
                              pLev = 3;
                              break;
                          }
                      }
                      break;
                  }
              }
          }

          if (pLev == 3) {
              String errorMess = "Category already has 3 levels of descendant categories. This limit cannot be exceeded";
              ae.add("error", new ActionError("error.simpleGenericError", errorMess));
              return ae;
          }

    	  int newParentLev = 0;

          CatalogCategoryDataVector newParentParentCategoryDV = catalogInfEjb.getCatalogParentCategory(pForm.getStoreCatalogId(), pForm.getSelectedId());
          if (newParentParentCategoryDV.size() > 0) {
              newParentLev = 1;
              for (Iterator iter = newParentParentCategoryDV.iterator(); iter.hasNext();) {
                  CatalogCategoryData categD = (CatalogCategoryData) iter.next();
                  ItemData categItemD = categD.getItemData();
                  int categId = categItemD.getItemId();
                  CatalogCategoryDataVector newParentGrandParentsCategoriesDV = catalogInfEjb.getCatalogParentCategory(pForm.getStoreCatalogId(), categId);
                  if (newParentGrandParentsCategoriesDV.size() > 0) {
                      newParentLev = 2;
                      for (Iterator iter2 = newParentGrandParentsCategoriesDV.iterator(); iter2.hasNext();) {
                          CatalogCategoryData categ2D = (CatalogCategoryData) iter2.next();
                          ItemData categ2ItemD = categ2D.getItemData();
                          int categ2Id = categ2ItemD.getItemId();
                          CatalogCategoryDataVector newParentGrandParentsCategories2DV = catalogInfEjb.getCatalogParentCategory(pForm.getStoreCatalogId(), categ2Id);
                          if (newParentGrandParentsCategories2DV.size() > 0) {
                              newParentLev = 3;
                              break;
                          }
                      }
                      break;
                  }
              }
          }

          if (newParentLev == 3) {
              String errorMess = "That category already has 3 levels of ancestor categories. This limit cannot be exceeded";
              ae.add("error", new ActionError("error.simpleGenericError", errorMess));
              return ae;
          }

          if (pLev + newParentLev > 2) {
              String errorMess = "Only four category levels supported";
              ae.add("error", new ActionError("error.simpleGenericError", errorMess));
              return ae;
    	  }

          catalogEjb.untieCatalogSubTree(pForm.getStoreCatalogId(), categoryId, userName);
          catalogEjb.addCatalogTreeNode(pForm.getStoreCatalogId(),pForm.getSelectedId(), categoryId, userName, pForm.getAllowMixedCategoryAndItemUnderSameParent());

      } catch (Exception exc) {
              String mess = exc.getMessage();
              if (mess == null) mess = "";
              int ind = mess.indexOf("^clw^");
              if (ind >= 0) {
                  mess = mess.substring(ind + "^clw^".length());
                  ind = mess.indexOf("^clw^");
                  if (ind > 0) mess = mess.substring(0, ind);
                  ae.add("error", new ActionError("error.simpleGenericError", mess));
                  return ae;
              } else {
                  throw exc;
              }
      }
      ae = synchronyzeCategories(request,pForm);
	  if(ae.size()>0) {
	     return ae;
	  }

      readStoreCategories(catalogInfEjb, pForm);
      return ae;
  }

  public static ActionErrors moveCategoryToTop(HttpServletRequest request,
          StoreItemMgrForm pForm) throws
          Exception {

      HttpSession session = request.getSession();
      String user = (String) session.getAttribute(Constants.USER_NAME);
      APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
      CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();
      Catalog catalogEjb = factory.getCatalogAPI();
      CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
      String userName = appUser.getUser().getUserName();

      ActionErrors ae = new ActionErrors();
      if (!Utility.isSet(pForm.getEditCategoryName())) {
          String errorMess = "Empty category name";
          ae.add("error", new ActionError("error.simpleGenericError", errorMess));
          return ae;
      }

      int categoryId = pForm.getEditCategoryId();
      String categoryName = pForm.getEditCategoryName();
      String adminCategoryName = pForm.getAdminCategoryName();
      try {
            CatalogCategoryDataVector categrsDV = catalogInfEjb.getTopCatalogCategories(pForm.getStoreCatalogId());
            ae = checkOnCategoryExistence(categoryId, categoryName, adminCategoryName, categrsDV);
            if (ae.size() > 0) {
                return ae;
            }
          catalogEjb.untieCatalogSubTree(pForm.getStoreCatalogId(), categoryId, userName);
      } catch (Exception exc) {
              String mess = exc.getMessage();
              if (mess == null) mess = "";
              int ind = mess.indexOf("^clw^");
              if (ind >= 0) {
                  mess = mess.substring(ind + "^clw^".length());
                  ind = mess.indexOf("^clw^");
                  if (ind > 0) mess = mess.substring(0, ind);
                  ae.add("error", new ActionError("error.simpleGenericError", mess));
                  return ae;
              } else {
                  throw exc;
              }
      }
      ae = synchronyzeCategories(request,pForm);
	  if(ae.size()>0) {
	     return ae;
	  }
      readStoreCategories(catalogInfEjb, pForm);
      return ae;
  }


  public static ActionErrors deleteCategory(HttpServletRequest request,
                                            StoreItemMgrForm pForm) throws
    Exception {

    HttpSession session = request.getSession();
    String user = (String) session.getAttribute(Constants.USER_NAME);
    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();
    Catalog catalogEjb = factory.getCatalogAPI();

    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.
      APP_USER);
    String userName = appUser.getUser().getUserName();

    ActionErrors ae = new ActionErrors();
    int categoryId = pForm.getEditCategoryId();

    CatalogCategoryDataVector categDV = pForm.getStoreCategories();
    boolean foundFl = false;
    for (Iterator iter = categDV.iterator(); iter.hasNext(); ) {
      CatalogCategoryData categD = (CatalogCategoryData) iter.next();
      ItemData categItemD = categD.getItemData();
      if (categoryId == categItemD.getItemId()) {
        foundFl = true;
        break;
      }
    }
    if (!foundFl) {
      String errorMess = "The category doesn't exist: " +
                         pForm.getEditCategoryName();
      ae.add("error", new ActionError("error.simpleGenericError", errorMess));
      return ae;
    }
    try {
      ProductDataVector myProducts = catalogInfEjb.getCatalogChildProducts(pForm.getStoreCatalogId(), categoryId);
      if (myProducts.size() > 0) {
          String errorMess = "A category with product(s) cannot be removed";
          ae.add("error", new ActionError("error.simpleGenericError", errorMess));
          return ae;
      }
      CatalogCategoryDataVector categrsDV = catalogInfEjb.getCatalogChildCategories(pForm.getStoreCatalogId(), categoryId);
      for (Iterator iter = categrsDV.iterator(); iter.hasNext(); ) {
          CatalogCategoryData categD = (CatalogCategoryData) iter.next();
          ItemData categItemD = categD.getItemData();
          int childCategoryId = categItemD.getItemId();
          ProductDataVector productsOfMyChild = catalogInfEjb.getCatalogChildProducts(pForm.getStoreCatalogId(), childCategoryId);
          if (productsOfMyChild.size() > 0) {
              String errorMess = "Category cannot be removed: descendant level 2 category has product(s)";
              ae.add("error", new ActionError("error.simpleGenericError", errorMess));
              return ae;
          }
          CatalogCategoryDataVector grandChildCategrsDV = catalogInfEjb.getCatalogChildCategories(pForm.getStoreCatalogId(), childCategoryId);
          for (Iterator iter2 = grandChildCategrsDV.iterator(); iter2.hasNext(); ) {
              CatalogCategoryData categD2 = (CatalogCategoryData) iter2.next();
              ItemData categItemD2 = categD2.getItemData();
              int gChildCategoryId = categItemD2.getItemId();
              ProductDataVector productsOfMyGChild = catalogInfEjb.getCatalogChildProducts(pForm.getStoreCatalogId(), gChildCategoryId);
              if (productsOfMyGChild.size() > 0) {
                  String errorMess = "Category cannot be removed: descendant level 3 category has product(s)";
                  ae.add("error", new ActionError("error.simpleGenericError", errorMess));
                  return ae;
              }
          }
      }
//      CatalogCategoryDataVector categrsDV = catalogInfEjb.getCatalogChildCategories(pForm.getStoreCatalogId(), categoryId);
//      catalogEjb.deleteStoreCategory(pForm.getStoreCatalogId(), categoryId);
      for (Iterator it = categrsDV.iterator(); it.hasNext(); ) {
          CatalogCategoryData categD = (CatalogCategoryData) it.next();
          ItemData categItemD = categD.getItemData();
          int childCategoryId = categItemD.getItemId();
//          ProductDataVector productsOfMyChild = catalogInfEjb.getCatalogChildProducts(pForm.getStoreCatalogId(), childCategoryId);
//          if (productsOfMyChild.size() > 0) {
//              String errorMess = "Category cannot be removed: descendant level 2 category has product(s).";
//              ae.add("error", new ActionError("error.simpleGenericError", errorMess));
//              return ae;
//          }
          CatalogCategoryDataVector grandChildCategrsDV = catalogInfEjb.getCatalogChildCategories(pForm.getStoreCatalogId(), childCategoryId);
          for (Iterator iter2 = grandChildCategrsDV.iterator(); iter2.hasNext(); ) {
              CatalogCategoryData categD2 = (CatalogCategoryData) iter2.next();
              ItemData categItemD2 = categD2.getItemData();
              int gChildCategoryId = categItemD2.getItemId();
              catalogEjb.deleteStoreCategory(pForm.getStoreCatalogId(), gChildCategoryId);
//              ProductDataVector productsOfMyGChild = catalogInfEjb.getCatalogChildProducts(pForm.getStoreCatalogId(), gChildCategoryId);
//              if (productsOfMyGChild.size() > 0) {
//                  String errorMess = "Category cannot be removed: descendant level 3 category has product(s).";
//                  ae.add("error", new ActionError("error.simpleGenericError", errorMess));
//                  return ae;
//              }
          }
          catalogEjb.deleteStoreCategory(pForm.getStoreCatalogId(), childCategoryId);
      }
      catalogEjb.deleteStoreCategory(pForm.getStoreCatalogId(), categoryId);


      pForm.setMajorCategoryId(0);
      pForm.setMajorCategoryName("");
      pForm.setEditCategoryId(0);
      pForm.setEditCategoryName("");
      pForm.setAdminCategoryName("");
	  pForm.setCanEditFl(true);
    } catch (Exception exc) {
      String mess = exc.getMessage();
      if (mess == null) mess = "";
      int ind = mess.indexOf("^clw^");
      if (ind >= 0) {
        mess = mess.substring(ind + "^clw^".length());
        ind = mess.indexOf("^clw^");
        if (ind > 0) mess = mess.substring(0, ind);
        ae.add("error", new ActionError("error.simpleGenericError", mess));
        return ae;
      } else {
        throw exc;
      }
    }
    ae = synchronyzeCategories(request,pForm);
	if(ae.size()>0) {
	   return ae;
	}
    readStoreCategories(catalogInfEjb, pForm);
    return ae;
  }

  public static ActionErrors deleteMultiproduct(HttpServletRequest request,
      StoreItemMgrForm pForm) throws
      Exception {

    HttpSession session = request.getSession();
    String user = (String) session.getAttribute(Constants.USER_NAME);
    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();
    Catalog catalogEjb = factory.getCatalogAPI();

    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    String userName = appUser.getUser().getUserName();

    ActionErrors ae = new ActionErrors();

    StoreData storeD = appUser.getUserStore();
    if (storeD == null) {
      ae.add("error",
             new ActionError("error.simpleGenericError", "No store info"));
      return ae;
    }
    int storeId = storeD.getBusEntity().getBusEntityId();

    int multiproductId = pForm.getEditMultiproductId();

    try {
    	IdVector groupItemsIds = catalogInfEjb.getMultiproductItems(pForm.getStoreCatalogId(), multiproductId);
        if (Utility.isSet(groupItemsIds)) {
			String errorMess = ClwI18nUtil.getMessage(request,"error.store.item.multiproduct.cannotDelete", new Object[]{groupItemsIds.toString()});
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }
        catalogEjb.deleteStoreMultiproduct(pForm.getStoreCatalogId(), multiproductId);
        pForm.setEditMultiproductId(0);
        pForm.setEditMultiproductName("");
    } catch (Exception exc) {
    String mess = exc.getMessage();
    if (mess == null) mess = "";
    int ind = mess.indexOf("^clw^");
    if (ind >= 0) {
        mess = mess.substring(ind + "^clw^".length());
        ind = mess.indexOf("^clw^");
        if (ind > 0) mess = mess.substring(0, ind);
            ae.add("error", new ActionError("error.simpleGenericError", mess));
            return ae;
        } else {
            throw exc;
        }
    }
    readStoreMultiproducts(catalogInfEjb, pForm, storeId);
    return ae;
}


  public static ActionErrors initItemImport(HttpServletRequest request,
                                            StoreItemMgrForm pForm) throws
    Exception {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    String user = (String) session.getAttribute(Constants.USER_NAME);
    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    int impStoreId = pForm.getImportStoreId();
    if (impStoreId <= 0) {
      String errorMess = "No store selected";
      ae.add("error", new ActionError("error.simpleGenericError", errorMess));
      return ae;
    }
    Store storeEjb = factory.getStoreAPI();
    StoreData storeD = storeEjb.getStore(impStoreId);

    /*XXX may be better way of doing all this*/
    LocateStoreItemForm locateItemForm = pForm.getLocateStoreItemForm();
    if (locateItemForm != null) {
      StoreData curStoreD = locateItemForm.getStore();
      if (curStoreD != null &&
          curStoreD.getBusEntity().getBusEntityId() != impStoreId) {
        locateItemForm.setItemsSelected(null);
      }
    }

    ae = LocateStoreItemLogic.processAction(request, pForm, "initSearch");
    if (ae.size() > 0) {
      return ae;
    }
    if (locateItemForm == null) locateItemForm = pForm.getLocateStoreItemForm();
    locateItemForm.setStore(storeD);
    locateItemForm.setLocateItemFl(true);
    return ae;
  }

  public static ActionErrors getImportedItems(HttpServletRequest request,
                                              StoreItemMgrForm pForm) throws
    Exception {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    ItemViewVector impItemVwV = pForm.getItemsToImport();
    if (impItemVwV == null || impItemVwV.size() == 0) {
      String errorMess = "No items to import";
      ae.add("error", new ActionError("error.simpleGenericError", errorMess));
      return ae;
    }
    pForm.setProduct(null);
    if (impItemVwV.size() == 1) {
      ItemView iVw = (ItemView) impItemVwV.get(0);
      ae = impItem(request, pForm, iVw.getItemId());
    }
    return ae;
  }

  public static ActionErrors impItem(HttpServletRequest request,
                                     StoreItemMgrForm pForm) throws Exception {
    ActionErrors ae = new ActionErrors();
    pForm.setProduct(null);
    String itemIdS = request.getParameter("itemId");
    int itemId = 0;
    try {
      itemId = Integer.parseInt(itemIdS);
    } catch (Exception exc) {}
    if (itemId <= 0) {
      String errorMess = "Wrong item id: " + itemIdS;
      ae.add("error", new ActionError("error.simpleGenericError", errorMess));
      return ae;
    }

    ae = impItem(request, pForm, itemId);
    return ae;
  }

  public static ActionErrors impItem(HttpServletRequest request,
                                     StoreItemMgrForm pForm, int pItemId) throws
    Exception {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();

    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.
      APP_USER);
    StoreData storeD = appUser.getUserStore();
    int storeId = storeD.getBusEntity().getBusEntityId();

    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();
    ProductData productD = null;
    boolean isUseItemsLinksBetweenStores = getUseItemsLinksBetweenStores(request);

    int impStoreId = pForm.getImportStoreId();
    try {
      productD = catalogInfEjb.getStoreProduct(impStoreId, pItemId);
    } catch (Exception exc) {
      String mess = exc.getMessage();
      if (mess == null) mess = "";
      int ind = mess.indexOf("^clw^");
      if (ind >= 0) {
        mess = mess.substring(ind + "^clw^".length());
        ind = mess.indexOf("^clw^");
        if (ind > 0) mess = mess.substring(0, ind);
        ae.add("error", new ActionError("error.simpleGenericError", mess));
        return ae;
      } else {
        throw exc;
      }
    }

    String shortDesc = productD.getShortDesc();

    LinkedList searchConditions = new LinkedList();

    SearchCriteria scStore = new
                             SearchCriteria(SearchCriteria.STORE_ID,
                                            SearchCriteria.EXACT_MATCH,
                                            new Integer(storeId));
    searchConditions.add(scStore);
    searchConditions.add(scStore);

    SearchCriteria scName = new
                            SearchCriteria(SearchCriteria.PRODUCT_SHORT_DESC,
                                           SearchCriteria.
                                           EXACT_MATCH_IGNORE_CASE, shortDesc);
    searchConditions.add(scName);

    ItemViewVector items = catalogInfEjb.searchStoreItems(searchConditions,
      pForm.getDistInfoFlag());
    for (Iterator iter = items.iterator(); iter.hasNext(); ) {
      ItemView iVw = (ItemView) iter.next();
      String errorMess = "Item with the same name already exists. Sku num: " +
                         iVw.getSku();
      ae.add("error", new ActionError("error.simpleGenericError", errorMess));
      return ae;
    }

    String userName = appUser.getUser().getUserName();
    productD = catalogInfEjb.importStoreItem(storeId, impStoreId, productD,
                                             userName);
    ae = initItem(request, pForm, productD);

    if (ae.size() > 0) {
      return ae;
    }

    pForm.setProduct(productD);
    // prepare for adding the managed item link to clw_item_assoc table if import between ENTERPRISE
    // store and non-ENTERPRISE store. if both of them are ENTERPRISE or non-ENTERPRISE
    // we will skip.
    Store storeEjb = factory.getStoreAPI();
    StoreData impStoreD = storeEjb.getStore(impStoreId);
    String storeType = storeD.getStoreType().getValue();
    String impStoreType = impStoreD.getStoreType().getValue();
    if (isUseItemsLinksBetweenStores) {
        pForm.setFromImport(true);
        pForm.setParentProductId(pItemId);
        pForm.setImportStoreId(impStoreId);
    }
    else {
        if (RefCodeNames.STORE_TYPE_CD.ENTERPRISE.equalsIgnoreCase(storeType) &&
            !RefCodeNames.STORE_TYPE_CD.ENTERPRISE.equalsIgnoreCase(impStoreType)
            ||
            !RefCodeNames.STORE_TYPE_CD.ENTERPRISE.equalsIgnoreCase(storeType) &&
            RefCodeNames.STORE_TYPE_CD.ENTERPRISE.equalsIgnoreCase(impStoreType)) {
            pForm.setFromImport(true);
            pForm.setParentProductId(pItemId);
            pForm.setImportStoreId(impStoreId);
        }
    }

    return ae;
  }

  public static ActionErrors checkMissingData(HttpServletRequest request,
                                              StoreItemMgrForm pForm) throws
    Exception {
    ActionErrors ae = new ActionErrors();
    ProductData productD = pForm.getProduct();
    if (productD == null) {
      String errorMess = "No imported product found";
      ae.add("error", new ActionError("error.simpleGenericError", errorMess));
      return ae;
    }
    HttpSession session = request.getSession();
    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    ProductInformation productInfoEjb =  factory.getProductInformationAPI();
    BusEntityDataVector certifiedCompanies = productInfoEjb.getBusEntityByCriteria(new BusEntitySearchCriteria(), RefCodeNames.BUS_ENTITY_TYPE_CD.CERTIFIED_COMPANY);
    SelectableObjects selectCertCompanies = new SelectableObjects(productD.getCertCompaniesBusEntityDV(), certifiedCompanies, BUS_ENTITY_ID_COMPARE);
    pForm.setSelectCertCompanies(selectCertCompanies);

    //Check manufacturer
    if (productD.getManufacturer() == null ||
        productD.getManufacturer().getBusEntityId() <= 0) {
      String errorMess = "No manufacture found";
      ae.add("error", new ActionError("error.simpleGenericError", errorMess));

    }
    //Check category
    if (productD.getCatalogCategories() == null ||
        productD.getCatalogCategories().size() == 0) {
      String errorMess = "No category found";
      ae.add("error", new ActionError("error.simpleGenericError", errorMess));

    }
    return ae;
  }

  public static ActionErrors editUpload(HttpServletRequest request,
                                        StoreItemMgrForm pForm) throws
    Exception {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    StoreItemLoaderMgrForm itemLoaderForm =
      (StoreItemLoaderMgrForm) session.getAttribute("STORE_ITEM_LOADER_FORM");

    int itemId = itemLoaderForm.getEditItemId();

    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.
      APP_USER);
    StoreData storeD = appUser.getUserStore();
    int storeId = storeD.getBusEntity().getBusEntityId();

    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();
    ProductData productD = null;

    try {
      productD = catalogInfEjb.getStoreProduct(storeId, itemId);
    } catch (Exception exc) {
      String mess = exc.getMessage();
      if (mess == null) mess = "";
      int ind = mess.indexOf("^clw^");
      if (ind >= 0) {
        mess = mess.substring(ind + "^clw^".length());
        ind = mess.indexOf("^clw^");
        if (ind > 0) mess = mess.substring(0, ind);
        ae.add("error", new ActionError("error.simpleGenericError", mess));
        return ae;
      } else {
        throw exc;
      }
    }

    ae = initItem(request, pForm, productD);
    if (ae.size() > 0) {
      return ae;
    }

    pForm.setProduct(productD);
    return ae;
  }


/**  public static ActionErrors getLinkedItems(HttpServletRequest request,
                                            StoreItemMgrForm pForm) throws
    Exception {

    HttpSession session = request.getSession();
    ActionErrors ae = new ActionErrors();

    ProductData managedItemParent = pForm.getProduct();
    int parentItemId = managedItemParent.getProductId();

    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();
    ProductInformation productInfoEjb = factory.getProductInformationAPI();
    Store storeEjb = factory.getStoreAPI();

    IdVector linkedItemIdV = productInfoEjb.
                             getItemIdCollectionByManagedParentItem(
                               parentItemId);

    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.
      APP_USER);
    BusEntityDataVector storeDV = appUser.getStores();
    StoreDataVector storeDataV = new StoreDataVector();
    for (int i = 0; i < storeDV.size(); i++) {
      BusEntityData storeD = (BusEntityData) storeDV.get(i);

      StoreData storeDetail = storeEjb.getStore(storeD.getBusEntityId());
      if (!RefCodeNames.STORE_TYPE_CD.ENTERPRISE.equalsIgnoreCase(storeDetail.
        getStoreType().getValue())) {
        storeDataV.add(storeDetail);
      }
    }

    Map original = pForm.getOrgProductPropertyMap();
    ProductData current = pForm.getProduct();
    boolean productUpdated = pForm.isProductUpdated();
    StoreProductViewVector productDV = new StoreProductViewVector();
    if (null != linkedItemIdV && null != storeDataV && 0 < linkedItemIdV.size() &&
        0 < storeDataV.size()) {
      for (int i = 0; i < storeDataV.size(); i++) {
        StoreData storeDetail = (StoreData) storeDataV.get(i);
        int storeId = storeDetail.getStoreId();
        for (int j = 0; j < linkedItemIdV.size(); j++) {
          int itemId = ((Integer) linkedItemIdV.get(j)).intValue();
          try {
            ProductData productD = catalogInfEjb.getStoreProduct(storeId,
              itemId);
            StoreProductView storeProdD = new StoreProductView(storeDetail,
              productD);
            storeProdD = setupLinkedItemUpdateOptions(original, current,
              storeProdD, productUpdated);
            productDV.add(storeProdD);
          } catch (Exception e) {
          }
        }
      }
    }

    pForm.setLinkedProductItems(productDV);
    return ae;

  }
   */
    public static ActionErrors getLinkedItems(HttpServletRequest request,
                                                 StoreItemMgrForm pForm) throws Exception {

        HttpSession session = request.getSession();
        ActionErrors ae = new ActionErrors();

        ProductData managedItemParent = pForm.getProduct();
        int parentItemId = managedItemParent.getProductId();

        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();
        Store storeEjb = factory.getStoreAPI();

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        PairViewVector idPairVV = null;

        boolean isUseItemsLinksBetweenStores = getUseItemsLinksBetweenStores(request);

        if (isUseItemsLinksBetweenStores) {
            idPairVV = storeEjb.getLinkStoreItemPairIdsByParentItemBetweenStores(parentItemId,
                    appUser.getUserId(), appUser.getUser().getUserTypeCd());
        }
        else {
            idPairVV = storeEjb.getLinkStoreItemPairIdsByParentItem(parentItemId,
                appUser.getUserId(), appUser.getUser().getUserTypeCd());
        }
        Iterator iterator=idPairVV.iterator();
        Map original = pForm.getOrgProductPropertyMap();
        ProductData current = pForm.getProduct();
        boolean productUpdated = pForm.isProductUpdated();
        StoreProductViewVector productDV = new StoreProductViewVector();
        while(iterator.hasNext())
        {
            PairView idPair= (PairView)iterator.next();
            int storeId=((Integer)idPair.getObject1()).intValue();
            int itemId=((Integer)idPair.getObject2()).intValue();
            ProductData productD = catalogInfEjb.getStoreProduct(storeId,itemId);
            StoreData storeDetail = storeEjb.getStore(storeId);
            StoreProductView storeProdD = new StoreProductView(storeDetail,productD);
            storeProdD = setupLinkedItemUpdateOptions(original, current,storeProdD, productUpdated);
            productDV.add(storeProdD);
        }
        if (isUseItemsLinksBetweenStores) {
            pForm.setLinkedProductItemsBetweenStores(productDV);
        }
        else {
            pForm.setLinkedProductItems(productDV);
        }
        return ae;

    }

  public static ActionErrors updateLinkedItems(HttpServletRequest request,
                                               StoreItemMgrForm pForm) throws
    Exception {

    HttpSession session = request.getSession();
    ActionErrors ae = new ActionErrors();
    String user = (String) session.getAttribute(Constants.USER_NAME);

    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();
    ProductInformation productInfoEjb = factory.getProductInformationAPI();
    Store storeEjb = factory.getStoreAPI();
    Catalog catalogEjb = factory.getCatalogAPI();
    boolean isUseItemsLinksBetweenStores = getUseItemsLinksBetweenStores(request);

    ProductData parentProductD = pForm.getProduct();
    StoreProductViewVector linkedProductDV = null;
    if (isUseItemsLinksBetweenStores) {
        linkedProductDV = pForm.getLinkedProductItemsBetweenStores();
    }
    else {
        linkedProductDV = pForm.getLinkedProductItems();
    }
    if (null != linkedProductDV && 0 < linkedProductDV.size()) {
      for (int i = 0; i < linkedProductDV.size(); i++) {
        StoreProductView linkedProduct = (StoreProductView) linkedProductDV.
                                         get(
                                           i);

        StoreData storeD = linkedProduct.getStore();
        CatalogDataVector catalogDV = catalogInfEjb.
                                      getCatalogsCollectionByBusEntity(storeD.
          getBusEntity().getBusEntityId(), RefCodeNames.CATALOG_TYPE_CD.STORE);
        CatalogData cD = null;
        for (Iterator iter = catalogDV.iterator(); iter.hasNext(); ) {
          cD = (CatalogData) iter.next();
          if (RefCodeNames.CATALOG_STATUS_CD.ACTIVE.equals(cD.
            getCatalogStatusCd())) {
            break;
          }
        }
        if (cD == null) {
          ae.add("error",
                 new ActionError("error.simpleGenericError",
                                 "No store catalog found"));
          return ae;
        }
        int storeCatalogId = cD.getCatalogId();
        ProductData productD = linkedProduct.getProduct();
        if (linkedProduct.isUpdateShortDesc()) {
          productD.setShortDesc(parentProductD.getShortDesc());
        }
        if (linkedProduct.isUpdateOtherDesc()) {
          productD.setOtherDesc(parentProductD.getOtherDesc());
        }
        if (linkedProduct.isUpdateLongDesc()) {
          productD.setLongDesc(parentProductD.getLongDesc());
        }
        if (linkedProduct.isUpdateImage()) {
          productD.setImage(parentProductD.getImage());
        }
        if (linkedProduct.isUpdateManufacturer()) {
          productD.setManufacturer(parentProductD.getManufacturer());
        }
        if (linkedProduct.isUpdateManufacturerSku()) {
          productD.setManuMapping(productD.getManufacturer(),
                                  parentProductD.getManufacturerSku());
        }
        if (linkedProduct.isUpdateUpc()) {
          productD.setUpc(parentProductD.getUpc());
        }
        if (linkedProduct.isUpdatePkgUpc()) {
          productD.setPkgUpc(parentProductD.getPkgUpc());
        }
        if (linkedProduct.isUpdateMsds()) {
          productD.setMsds(parentProductD.getMsds());
        }
        if (linkedProduct.isUpdateUnspscCd()) {
          productD.setUnspscCd(parentProductD.getUnspscCd());
        }
        if (linkedProduct.isUpdateColor()) {
          productD.setColor(parentProductD.getColor());
        }
        if (linkedProduct.isUpdateSize()) {
          productD.setSize(parentProductD.getSize());
        }
        if (linkedProduct.isUpdateScent()) {
          productD.setScent(parentProductD.getScent());
        }
        if (linkedProduct.isUpdateDed()) {
          productD.setDed(parentProductD.getDed());
        }
        if (linkedProduct.isUpdateShipWeight()) {
          productD.setShipWeight(parentProductD.getShipWeight());
        }
        if (linkedProduct.isUpdateCubeSize()) {
          productD.setCubeSize(parentProductD.getCubeSize());
        }
        if (linkedProduct.isUpdateListPrice()) {
          productD.setListPrice(parentProductD.getListPrice());
        }
        if (linkedProduct.isUpdatePsn()) {
          productD.setPsn(parentProductD.getPsn());
        }
        if (linkedProduct.isUpdateSpec()) {
          productD.setSpec(parentProductD.getSpec());
        }
        if (linkedProduct.isUpdateNsn()) {
          productD.setNsn(parentProductD.getNsn());
        }
        if (linkedProduct.isUpdateUom()) {
          productD.setUom(parentProductD.getUom());
        }
        if (linkedProduct.isUpdatePackProblemSku()) {
          productD.setPackProblemSku(parentProductD.isPackProblemSku());
        }
        if (linkedProduct.isUpdatePack()) {
          productD.setPack(parentProductD.getPack());
        }
        if (linkedProduct.isUpdateCostPrice()) {
          productD.setCostPrice(parentProductD.getCostPrice());
        }
        if (linkedProduct.isUpdateHazmat()) {
          productD.setHazmat(parentProductD.getHazmat());
        }

        if (linkedProduct.isUpdateCertifiedCompanies()) {
              ItemMappingDataVector parentCertComp = parentProductD.getCertifiedCompanies();
              if (parentCertComp != null) {
                  ItemMappingDataVector copyCertCompanies = new ItemMappingDataVector();
                  Iterator it = parentCertComp.iterator();
                  while (it.hasNext()) {
                      ItemMappingData copyVal = ItemMappingData.createValue();

                      ItemMappingData imd = (ItemMappingData) it.next();
                      copyVal.setShortDesc(imd.getShortDesc());
                      copyVal.setLongDesc(imd.getLongDesc());
                      copyVal.setStatusCd(imd.getStatusCd());
                      copyVal.setItemId(productD.getItemData().getItemId());
                      copyVal.setBusEntityId(imd.getBusEntityId());
                      copyVal.setItemMappingCd(imd.getItemMappingCd());
                      copyCertCompanies.add(copyVal);

                  }
                  productD.setCertifiedCompanies(copyCertCompanies);
                  productD.setCertCompaniesBusEntityDV(parentProductD.getCertCompaniesBusEntityDV());
              }
          }

        try {
          productD = catalogEjb.saveStoreCatalogProduct(storeD.getBusEntity().
            getBusEntityId(), storeCatalogId, productD, user);
        } catch (Exception exc) {
          String mess = exc.getMessage();
          if (mess == null) mess = "";
          int ind = mess.indexOf("^clw^");
          if (ind >= 0) {
            mess = mess.substring(ind + "^clw^".length());
            ind = mess.indexOf("^clw^");
            if (ind > 0) mess = mess.substring(0, ind);
            ae.add("error", new ActionError("error.simpleGenericError", mess));
            return ae;
          } else {
            throw exc;
          }
        }

      }
    }

    //reset the original product properties after update the linked items
    Map orgProductPropertyMap = new HashMap();
    pForm.setOrgProductPropertyMap(initOrgProductProperty(
      orgProductPropertyMap,
      parentProductD));
    pForm.setProductUpdated(false);

    getLinkedItems(request, pForm);
    pForm.setChangesOnItemEditPageFl(Boolean.FALSE);
    return ae;

  }


  public static ActionErrors breakManagedItemLink(HttpServletRequest request,
    StoreItemMgrForm pForm) throws
    Exception {

    HttpSession session = request.getSession();
    ActionErrors ae = new ActionErrors();

    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    StoreData storeD = appUser.getUserStore();
    if (storeD != null) {

      ProductData managedItemParent = pForm.getProduct();
      int parentItemId = managedItemParent.getProductId();

      boolean isUseItemsLinksBetweenStores = getUseItemsLinksBetweenStores(request);

      APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
      ProductInformation productInfoEjb = factory.getProductInformationAPI();

        try {
            if (isUseItemsLinksBetweenStores) {
                productInfoEjb.removeItemsLinkBetweenStores(parentItemId);
                pForm.setLinkedProductItemsBetweenStores(new StoreProductViewVector());
                pForm.setManagedItemBetweenStoresFlag(false);
            }
            else {
                if (RefCodeNames.STORE_TYPE_CD.ENTERPRISE.equalsIgnoreCase(storeD.getStoreType().getValue())) {
                    productInfoEjb.removeManagedItemLinksByManagedParentItem(parentItemId);
                }
                else {
                    productInfoEjb.removeManagedItemLinksByManagedChildItem(parentItemId);
                }
                pForm.setLinkedProductItems(new StoreProductViewVector());
                pForm.setManagedItemFlag(false);
            }
        }
        catch (Exception e) {
            ae.add("error",
                new ActionError("error.simpleGenericError",
                    "Cannot break the managed item links."));
        }
    }

    return ae;

  }
    public static ActionErrors unlinkManagedItem(HttpServletRequest request,StoreItemMgrForm pForm) throws
    Exception {

        HttpSession session = request.getSession();
        ActionErrors ae = new ActionErrors();
        try{
            CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
            StoreData storeD = appUser.getUserStore();
            APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
            ProductInformation productInfoEjb = factory.getProductInformationAPI();
            boolean isUseItemsLinksBetweenStores = getUseItemsLinksBetweenStores(request);

            if (isUseItemsLinksBetweenStores) {
                ProductData managedItemParent = pForm.getProduct();
                int itemId = pForm.getUnlinkedItemIdBetweenStores();
                if (storeD != null && itemId > 0) {
                    productInfoEjb.removeItemsLinkBetweenStores(managedItemParent.getProductId(), itemId);
                }
                pForm.setLinkedProductItemsBetweenStores(new StoreProductViewVector());
                pForm.setManagedItemBetweenStoresFlag(false);
            }
            else {
                int itemId = pForm.getUnlinkedItemId();
                if (storeD != null&&itemId>0) {
                    productInfoEjb.removeManagedItemLinksByManagedChildItem(itemId);
                }
                pForm.setLinkedProductItems(new StoreProductViewVector());
                pForm.setManagedItemFlag(false);
            }

            getLinkedItems(request, pForm);
        }
        catch (Exception e) {
            ae.add("error",
                    new ActionError("error.simpleGenericError",
                            "Cannot break the managed item links."));
        }
        return ae;
    }

    public static ActionErrors getQRcode(HttpServletRequest request, HttpServletResponse response, StoreItemMgrForm pForm) throws Exception {
        log.info("getQRCode()=> BEGIN");

        ActionErrors ae = new ActionErrors();

        try {
            ProductData product = pForm.getProduct();
            
            if (pForm.getItemStoreId() > 0) {
                byte[] stream = ((QRCode.from("http://IT/" + pForm.getItemStoreId() + "/" + product.getProductId() + "/" + product.getShortDesc()).to(ImageType.GIF).withSize(175, 175).stream()).toByteArray());

                OutputStream out = response.getOutputStream();
                out.write(stream);
                out.flush();
                out.close();
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            ae.add("error", new ActionError("error.simpleGenericError", "Cannot get QRcode for the given item"));
        }
        
        log.info("getQRCode()=> END");
        return ae;
    }

  private static StoreProductView setupLinkedItemUpdateOptions(Map original,
    ProductData product, StoreProductView linkedProduct,
    boolean productUpdated) {
    if (!productUpdated || null == original || null == product ||
        null == linkedProduct || null == linkedProduct.getProduct()) {

      linkedProduct.setUpdateShortDesc(false);
      linkedProduct.setUpdateOtherDesc(false);
      linkedProduct.setUpdateLongDesc(false);
      linkedProduct.setUpdateImage(false);
      linkedProduct.setUpdateManufacturer(false);
      linkedProduct.setUpdateManufacturerSku(false);
      linkedProduct.setUpdateUpc(false);
      linkedProduct.setUpdatePkgUpc(false);
      linkedProduct.setUpdateMsds(false);
      linkedProduct.setUpdateUnspscCd(false);
      linkedProduct.setUpdateColor(false);
      linkedProduct.setUpdateSize(false);
      linkedProduct.setUpdateScent(false);
      linkedProduct.setUpdateDed(false);
      linkedProduct.setUpdateShipWeight(false);
      linkedProduct.setUpdateCubeSize(false);
      linkedProduct.setUpdateListPrice(false);
      linkedProduct.setUpdatePsn(false);
      linkedProduct.setUpdateSpec(false);
      linkedProduct.setUpdateNsn(false);
      linkedProduct.setUpdateUom(false);
      linkedProduct.setUpdatePackProblemSku(false);
      linkedProduct.setUpdatePack(false);
      linkedProduct.setUpdateCostPrice(false);
      linkedProduct.setUpdateHazmat(false);
      linkedProduct.setUpdateCertifiedCompanies(false);
    } else {
      boolean update = false;
      update = isUpdateStringProperty(original.get("shortDesc"),
                                      product.getShortDesc(),
                                      linkedProduct.getProduct().getShortDesc());
      linkedProduct.setUpdateShortDesc(update);

      update = isUpdateStringProperty(original.get("otherDesc"),
                                      product.getOtherDesc(),
                                      linkedProduct.getProduct().getOtherDesc());
      linkedProduct.setUpdateOtherDesc(update);

      update = isUpdateStringProperty(original.get("longDesc"),
                                      product.getLongDesc(),
                                      linkedProduct.getProduct().getLongDesc());
      linkedProduct.setUpdateLongDesc(update);

      update = isUpdateStringProperty(original.get("image"), product.getImage(),
                                      linkedProduct.getProduct().getImage());
      linkedProduct.setUpdateImage(update);

      update = isUpdateIntProperty(((Integer) original.get("manufacturerId")).
                                   intValue(),
                                   product.getManufacturer().getBusEntityId(),
                                   linkedProduct.getProduct().getManufacturer().
                                   getBusEntityId());
      linkedProduct.setUpdateManufacturer(update);

      update = isUpdateStringProperty(original.get("manufacturerSku"),
                                      product.getManufacturerSku(),
                                      linkedProduct.getProduct().
                                      getManufacturerSku());
      linkedProduct.setUpdateManufacturerSku(update);

      update = isUpdateStringProperty(original.get("upc"), product.getUpc(),
                                      linkedProduct.getProduct().getUpc());
      linkedProduct.setUpdateUpc(update);

      update = isUpdateStringProperty(original.get("pkgUpc"),
                                      product.getPkgUpc(),
                                      linkedProduct.getProduct().getPkgUpc());
      linkedProduct.setUpdatePkgUpc(update);

      update = isUpdateStringProperty(original.get("msds"), product.getMsds(),
                                      linkedProduct.getProduct().getMsds());
      linkedProduct.setUpdateMsds(update);

      update = isUpdateStringProperty(original.get("unspscCd"),
                                      product.getUnspscCd(),
                                      linkedProduct.getProduct().getUnspscCd());
      linkedProduct.setUpdateUnspscCd(update);

      update = isUpdateStringProperty(original.get("color"), product.getColor(),
                                      linkedProduct.getProduct().getColor());
      linkedProduct.setUpdateColor(update);

      update = isUpdateStringProperty(original.get("size"), product.getSize(),
                                      linkedProduct.getProduct().getSize());
      linkedProduct.setUpdateSize(update);

      update = isUpdateStringProperty(original.get("scent"), product.getScent(),
                                      linkedProduct.getProduct().getScent());
      linkedProduct.setUpdateScent(update);

      update = isUpdateStringProperty(original.get("ded"), product.getDed(),
                                      linkedProduct.getProduct().getDed());
      linkedProduct.setUpdateDed(update);

      update = isUpdateStringProperty(original.get("shipWeight"),
                                      product.getShipWeight(),
                                      linkedProduct.getProduct().
                                      getShipWeight());
      linkedProduct.setUpdateShipWeight(update);

      update = isUpdateStringProperty(original.get("cubeSize"),
                                      product.getCubeSize(),
                                      linkedProduct.getProduct().getCubeSize());
      linkedProduct.setUpdateCubeSize(update);

      update = isUpdateDoubleProperty(((Double) original.get("listPrice")).
                                      doubleValue(), product.getListPrice(),
                                      linkedProduct.getProduct().getListPrice());
      linkedProduct.setUpdateListPrice(update);

      update = isUpdateStringProperty(original.get("psn"), product.getPsn(),
                                      linkedProduct.getProduct().getPsn());
      linkedProduct.setUpdatePsn(update);

      update = isUpdateStringProperty(original.get("spec"), product.getSpec(),
                                      linkedProduct.getProduct().getSpec());
      linkedProduct.setUpdateSpec(update);

      update = isUpdateStringProperty(original.get("nsn"), product.getNsn(),
                                      linkedProduct.getProduct().getNsn());
      linkedProduct.setUpdateNsn(update);

      update = isUpdateStringProperty(original.get("uom"), product.getUom(),
                                      linkedProduct.getProduct().getUom());
      linkedProduct.setUpdateUom(update);

      update = isUpdateBooleanProperty(((Boolean) original.get(
        "packProblemSku")).
                                       booleanValue(),
                                       product.isPackProblemSku(),
                                       linkedProduct.getProduct().
                                       isPackProblemSku());
      linkedProduct.setUpdatePackProblemSku(update);

      update = isUpdateStringProperty(original.get("pack"), product.getPack(),
                                      linkedProduct.getProduct().getPack());
      linkedProduct.setUpdatePack(update);

      update = isUpdateDoubleProperty(((Double) original.get("costPrice")).
                                      doubleValue(), product.getCostPrice(),
                                      linkedProduct.getProduct().getCostPrice());
      linkedProduct.setUpdateCostPrice(update);

      update = isUpdateStringProperty(original.get("hazmat"),
                                      product.getHazmat(),
                                      linkedProduct.getProduct().getHazmat());
      linkedProduct.setUpdateHazmat(update);

      update =isUpdateStringProperty(original.get("certifiedCompanies"),
                                      getBusEntityIds(product.getCertifiedCompanies(),true),
                                      getBusEntityIds(linkedProduct.getProduct().getCertifiedCompanies(),true));
      linkedProduct.setUpdateCertifiedCompanies(update);
    }

    return linkedProduct;
  }

    private static String getBusEntityIds(ItemMappingDataVector certifiedCompanies, boolean sort) {
        String ids = "";
        if (certifiedCompanies != null && certifiedCompanies.size() > 0) {
            if (sort)
                Collections.sort(certifiedCompanies, ITEM_MAPPING_BUS_ENTITY_ID_COMPARE);
            Iterator it = certifiedCompanies.iterator();
            int i = 0;
            while (it.hasNext()) {
                ItemMappingData imd = (ItemMappingData) it.next();
                if (i != 0) ids = ids + ",";

                ids = ids + imd.getBusEntityId();
            }
        }
        return ids;
    }


    private static Map initOrgProductProperty(Map orgMap, ProductData product) {
    orgMap.put("shortDesc", product.getShortDesc());
    orgMap.put("otherDesc", product.getOtherDesc());
    orgMap.put("longDesc", product.getLongDesc());
    orgMap.put("image", product.getImage());
    orgMap.put("manufacturerId",
               new Integer(product.getManufacturer().getBusEntityId()));
    orgMap.put("manufacturerSku", product.getManufacturerSku());
    orgMap.put("upc", product.getUpc());
    orgMap.put("pkgUpc", product.getPkgUpc());
    orgMap.put("msds", product.getMsds());
    orgMap.put("unspscCd", product.getUnspscCd());
    orgMap.put("color", product.getColor());
    orgMap.put("size", product.getSize());
    orgMap.put("scent", product.getScent());
    orgMap.put("ded", product.getDed());
    orgMap.put("shipWeight", product.getShipWeight());
    orgMap.put("cubeSize", product.getCubeSize());
    orgMap.put("listPrice", new Double(product.getListPrice()));
    orgMap.put("psn", product.getPsn());
    orgMap.put("spec", product.getSpec());
    orgMap.put("nsn", product.getNsn());
    orgMap.put("uom", product.getUom());
    orgMap.put("packProblemSku", new Boolean(product.isPackProblemSku()));
    orgMap.put("pack", product.getPack());
    orgMap.put("costPrice", new Double(product.getCostPrice()));
    orgMap.put("hazmat", product.getHazmat());
    orgMap.put("certifiedCompanies", getBusEntityIds(product.getCertifiedCompanies(),true));         

    return orgMap;
  }

    
    private static Map addStorageTypeToOrgProductProperty(Map orgMap, ProductData product, HttpServletRequest request) throws Exception {       
                
        // STJ-5164: Move document(JPEG, PDF, etc. formats) content from Database Blob to E3 Storage: Begin
        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        Content contentBean = factory.getContentAPI();
        
        // Find Storage type of the product item  (E3 or Database) and store it 
        String imageUrl = product.getImage();
        String thumbnailUrl = product.getThumbnail();
        String msdsUrl = product.getMsds();
        String dedUrl = product.getDed();        
        String specUrl = product.getSpec();
        
        log.info("imageUrl = " + imageUrl);
        log.info("thumbnailUrl = " + thumbnailUrl);
        log.info("msdsUrl = " + msdsUrl);
        log.info("dedUrl = " + dedUrl);
        log.info("specUrl = " + specUrl);
        
        if (Utility.isSet(imageUrl)) {
        	imageUrl = "." + imageUrl;
        	ContentData contentImageData = contentBean.getContent(imageUrl);
        	//product.setImageStorageTypeCd(contentImageData.getStorageTypeCd());
        	orgMap.put("imageStorageTypeCd", contentImageData.getStorageTypeCd());
        }
        if (Utility.isSet(thumbnailUrl)) {
        	thumbnailUrl = "." + thumbnailUrl;
        	ContentData contentThumbnailData = contentBean.getContent(thumbnailUrl);
        	//product.setThumbnailStorageTypeCd(contentThumbnailData.getStorageTypeCd());
        	orgMap.put("thumbnailStorageTypeCd", contentThumbnailData.getStorageTypeCd());
        }
        if (Utility.isSet(msdsUrl)) {
        	msdsUrl = "." + msdsUrl;
        	ContentData contentMsdsData = contentBean.getContent(msdsUrl);
        	//product.setMsdsStorageTypeCd(contentMsdsData.getStorageTypeCd());
        	orgMap.put("msdsStorageTypeCd", contentMsdsData.getStorageTypeCd());
        }
        if (Utility.isSet(dedUrl)) {
        	dedUrl = "." + dedUrl;
        	ContentData contentDedData = contentBean.getContent(dedUrl);
        	//product.setDedStorageTypeCd(contentDedData.getStorageTypeCd());
        	orgMap.put("dedStorageTypeCd", contentDedData.getStorageTypeCd());
        }
        if (Utility.isSet(specUrl)) {
        	specUrl = "." + specUrl;
        	ContentData contentSpecData = contentBean.getContent(specUrl);
        	//product.setSpecStorageTypeCd(contentSpecData.getStorageTypeCd());
        	orgMap.put("specStorageTypeCd", contentSpecData.getStorageTypeCd());
        }
        // Find Storage type of the product item  (E3 or Database) and store it    

        // STJ-5164: Move document(JPEG, PDF, etc. formats) content from Databse Blob to E3 Storage: End      

        return orgMap;
      }
    
    private static void addStorageTypeToForm(ProductData product, HttpServletRequest request, StoreItemMgrForm pForm) throws Exception {       
        
        // STJ-5164: Move document(JPEG, PDF, etc. formats) content from Database Blob to E3 Storage: Begin
        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        Content contentBean = factory.getContentAPI();
        
        // Find Storage type of the product item  (E3 or Database) and store it 
        String imageUrl = product.getImage();
        String thumbnailUrl = product.getThumbnail();
        String msdsUrl = product.getMsds();
        String dedUrl = product.getDed();        
        String specUrl = product.getSpec();
        
        log.info("imageUrl = " + imageUrl);
        log.info("thumbnailUrl = " + thumbnailUrl);
        log.info("msdsUrl = " + msdsUrl);
        log.info("dedUrl = " + dedUrl);
        log.info("specUrl = " + specUrl);
        
        if (Utility.isSet(imageUrl)) {
        	imageUrl = "." + imageUrl;
            ContentData contentImageData = contentBean.getContent(imageUrl);
            //log.info("addStorageTypeToForm(): contentImageData = " + contentImageData);
            if (contentImageData != null) {
                pForm.setImageStorageTypeCd(contentImageData.getStorageTypeCd());
            } else {
            	pForm.setImageStorageTypeCd(null);
            }
        }
        if (Utility.isSet(thumbnailUrl)) {
        	thumbnailUrl = "." + thumbnailUrl;
        	ContentData contentThumbnailData = contentBean.getContent(thumbnailUrl); 
        	//log.info("addStorageTypeToForm(): contentThumbnailData = " + contentThumbnailData);
            if (contentThumbnailData != null) {
                pForm.setThumbnailStorageTypeCd(contentThumbnailData.getStorageTypeCd());
            } else {
            	pForm.setThumbnailStorageTypeCd(null);
            }
        }
        if (Utility.isSet(msdsUrl)) {
        	msdsUrl = "." + msdsUrl;
        	ContentData contentMsdsData = contentBean.getContent(msdsUrl);
        	//log.info("addStorageTypeToForm(): contentMsdsData = " + contentMsdsData);
            if (contentMsdsData != null) {
                pForm.setMsdsStorageTypeCd(contentMsdsData.getStorageTypeCd());
            } else {
            	pForm.setMsdsStorageTypeCd(null);
            }
        }
        if (Utility.isSet(dedUrl)) {
        	dedUrl = "." + dedUrl;
        	ContentData contentDedData = contentBean.getContent(dedUrl);
        	//log.info("addStorageTypeToForm(): contentDedData  = " + contentDedData);
            if (contentDedData != null) {
                pForm.setDedStorageTypeCd(contentDedData.getStorageTypeCd());
            } else {
            	pForm.setDedStorageTypeCd(null);
            }
        }
        if (Utility.isSet(specUrl)) {
        	specUrl = "." + specUrl;
        	ContentData contentSpecData = contentBean.getContent(specUrl);
        	//log.info("addStorageTypeToForm(): contentSpecData  = " + contentSpecData);
            if (contentSpecData != null) {
                pForm.setSpecStorageTypeCd(contentSpecData.getStorageTypeCd());
            } else {
            	pForm.setSpecStorageTypeCd(null);
            }
        }
        // Find Storage type of the product item  (E3 or Database) and store it    

        // STJ-5164: Move document(JPEG, PDF, etc. formats) content from Databse Blob to E3 Storage: End      

      }   
    
  private static boolean isUpdateStringProperty(Object original,
                                                Object current,
                                                Object linked) {
    boolean updateProperty = false;

    if (null == original) {
      original = new String("");
    }
    if (null == current) {
      current = new String("");
    }
    if (null == linked) {
      linked = new String("");
    }

    if (((String) linked).equalsIgnoreCase((String) original) &&
        !((String) original).equalsIgnoreCase((String) current)) {
      updateProperty = true;
    }
    return updateProperty;
  }

  private static boolean isUpdateDoubleProperty(double original,
                                                double current,
                                                double linked) {
    boolean updateProperty = false;
    double eps = 0.00001;

    if (Math.abs(linked - original) < eps &&
        Math.abs(original - current) > eps) {
      updateProperty = true;
    }

    return updateProperty;
  }

  private static boolean isUpdateBooleanProperty(boolean original,
    boolean current,
    boolean linked) {
    boolean updateProperty = false;

    if (linked == original && original != current) {
      updateProperty = true;
    }

    return updateProperty;
  }

  private static boolean isUpdateIntProperty(int original, int current,
                                             int linked) {
    boolean updateProperty = false;

    if (linked == original && original != current) {
      updateProperty = true;
    }

    return updateProperty;
  }


  public static ActionErrors initLinkManagedItems(HttpServletRequest request,
    StoreItemMgrForm pForm) throws
    Exception {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    String user = (String) session.getAttribute(Constants.USER_NAME);
    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    Store storeEjb = factory.getStoreAPI();
    boolean isUseItemsLinksBetweenStores = getUseItemsLinksBetweenStores(request);

    /*XXX may be better way of doing all this*/
    LocateStoreItemForm locateItemForm = pForm.getLocateStoreItemForm();
    if (locateItemForm != null) {
      locateItemForm.setItemsSelected(null);
    }

    ae = LocateStoreItemLogic.processAction(request, pForm, "initSearch");
    if (ae.size() > 0) {
      return ae;
    }
    if (locateItemForm == null) locateItemForm = pForm.getLocateStoreItemForm();
    locateItemForm.setStore(null);
    locateItemForm.setLocateItemFl(true);

    //added 20.10.2006
    locateItemForm.setManuNameTempl(pForm.getProduct().getManufacturerName());
    locateItemForm.setSkuTempl(pForm.getProduct().getManufacturerSku());
    locateItemForm.setSkuType(SearchCriteria.MANUFACTURER_SKU_NUMBER);
    if (isUseItemsLinksBetweenStores) {
        pForm.setOpenItemLinkManagedBetweenStoresFl(true);
    }
    else {
        pForm.setOpenItemLinkManagedFl(true);
    }
    //
    return ae;
  }
  public static ActionErrors linkManagedItems(HttpServletRequest request,
                                              StoreItemMgrForm pForm) throws
    Exception {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    ItemViewVector linkItemVwV = pForm.getItemsToLink();
    if (linkItemVwV == null || linkItemVwV.size() == 0) {
      String errorMess = "No items to link";
      ae.add("error", new ActionError("error.simpleGenericError", errorMess));
      return ae;
    }

    ProductData managedItemParent = pForm.getProduct();
    if (null == managedItemParent) {
      ae.add("error",
             new ActionError("error.simpleGenericError",
                             "Cannot get the managed item"));
      return ae;
    }

    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    ProductInformation productInfoEjb = factory.getProductInformationAPI();
    boolean isUseItemsLinksBetweenStores = getUseItemsLinksBetweenStores(request);

    IdVector linkedItemIdV = null;
    if (isUseItemsLinksBetweenStores) {
        linkedItemIdV = productInfoEjb.getItemIdCollectionByManagedItemBetweenStores(managedItemParent.getProductId());
        if (linkedItemIdV == null) {
            linkedItemIdV = new IdVector();
        }
    }
    else {
        linkedItemIdV = productInfoEjb.getItemIdCollectionByManagedParentItem( managedItemParent.getProductId());
    }

    PairViewVector selectedItems = new PairViewVector();
    if ( null != linkedItemIdV ) {
        // remove the selected items already be linked
        for (int i = 0; i < linkItemVwV.size(); i++) {

            ItemView itemVw = (ItemView) linkItemVwV.get(i);
            int itemId = itemVw.getItemId();
            int catalogId = itemVw.getCatalogId();

            if (!linkedItemIdV.contains(new Integer(itemId))) {
                selectedItems.add(new PairView(new Integer(itemId), new Integer(catalogId)));
            }
        }
    }

    if (selectedItems.size() > 0) {
        String user = (String) session.getAttribute(Constants.USER_NAME);
        for (int i = 0; i < selectedItems.size(); i++) {
            PairView pairView = (PairView)selectedItems.get(i);
            int childItemId = ((Integer) pairView.getObject1()).intValue();
            int childItemCatalogId = ((Integer) pairView.getObject2()).intValue();

            if (isUseItemsLinksBetweenStores) {
                productInfoEjb.addItemsLinkBetweenStores(
                    managedItemParent.getProductId(), childItemId, childItemCatalogId, user);
            }
            else {
                productInfoEjb.addManagedItemLinkByManagedChildItem(
                    managedItemParent.getProductId(), childItemId, user);
            }
        }
    }

    getLinkedItems(request, pForm);

    //added 20.10.2006
    if (isUseItemsLinksBetweenStores) {
        pForm.setOpenItemLinkManagedBetweenStoresFl(false);
    }
    else {
        pForm.setOpenItemLinkManagedFl(false);
    }
    pForm.setChangesOnItemEditPageFl(Boolean.TRUE);
    //
   return ae;
  }
    public static ManufacturerData getManufacturerDataByIdFromCollection(ManufacturerDataVector manufacturerDV,int manufacturerId)
    {
        ManufacturerData resultData=null;
        if(manufacturerDV!=null)
        {
            Iterator iterator =manufacturerDV.iterator();
            while(iterator.hasNext())
            {
                resultData= (ManufacturerData)iterator.next();
                if(resultData.getBusEntity().getBusEntityId()==manufacturerId)
                return resultData;
                else resultData=null;
            }
        }
       return resultData;
    }

    public static void closeItemLinkManaged(HttpServletRequest request,   StoreItemMgrForm pForm){
        pForm.setOpenItemLinkManagedFl(false);
    }
    public static void openItemLinkManaged(HttpServletRequest request,   StoreItemMgrForm pForm){
        pForm.setOpenItemLinkManagedFl(true);
    }

    public static boolean getItemLinkManagedFl(HttpServletRequest request, StoreItemMgrForm sForm) {
        if(sForm!=null)
        return sForm.getOpenItemLinkManagedFl();
        return false;
    }

    public static void closeItemLinkManagedBetweenStores(HttpServletRequest request, StoreItemMgrForm form){
        form.setOpenItemLinkManagedBetweenStoresFl(false);
    }

    public static void openItemLinkManagedBetweenStores(HttpServletRequest request, StoreItemMgrForm form){
        form.setOpenItemLinkManagedBetweenStoresFl(true);
    }

    public static boolean getItemLinkManagedBetweenStoresFl(HttpServletRequest request, StoreItemMgrForm form) {
        if (form != null) {
        	return form.getOpenItemLinkManagedBetweenStoresFl();
        }
        return false;
    }

    public final static void addCertCompanies(ProductData productD, Iterator it) {
        while (it.hasNext()) {
            BusEntityData busD = (BusEntityData) it.next();
            ItemMappingData newD = ItemMappingData.createValue();
            newD.setItemId(productD.getItemData().getItemId());
            newD.setBusEntityId(busD.getBusEntityId());
            newD.setItemMappingCd(RefCodeNames.ITEM_MAPPING_CD.ITEM_CERTIFIED_COMPANY);
            productD.addCertCompaniesMapping(newD);
        }
    }

    /**
     * Compare categories
     */
    public static ActionErrors compareItemCategoryNames(int categoryId, String editCategoryName,
        String adminCategoryName, ItemData itemData) {
		ActionErrors aErrors = new ActionErrors();
		if (editCategoryName == null) {
            return aErrors;
        }
        if (itemData == null) {
            return aErrors;
        }
        if (categoryId == itemData.getItemId()) {
        	return aErrors;
		}
		int compareResult = CategoryUtil.compareCategoriesByNames(editCategoryName, adminCategoryName,
        	itemData.getShortDesc(), itemData.getLongDesc());
		if (compareResult == 0) {
			String errorMsg = "Category with identical name already exists there (categories are compared by a short and long name)";
			aErrors.add("error", new ActionError("error.simpleGenericError", errorMsg));
		}
       	return aErrors;
	}

    /**
     * Check on the category existence
     */
    public static ActionErrors checkOnCategoryExistence(int categoryId, String editCategoryName,
        String adminCategoryName, CatalogCategoryDataVector categoryVector) {
        if (editCategoryName == null) {
            return new ActionErrors();
        }
        if (categoryVector == null) {
            return new ActionErrors();
        }
        if (categoryVector.size() == 0) {
            return new ActionErrors();
        }
        ActionErrors aErrors = null;
        for (Iterator it = categoryVector.iterator(); it.hasNext();) {
            CatalogCategoryData tempCategory = (CatalogCategoryData)it.next();
            ItemData itemData = tempCategory.getItemData();
            aErrors = compareItemCategoryNames(categoryId, editCategoryName, adminCategoryName, itemData);
			if (aErrors.size() > 0) {
				break;
			}
        }
        if (aErrors == null) {
        	aErrors = new ActionErrors();
        }
        return aErrors;
    }

    /**
     * Check on the category existence
     */
    public static ActionErrors checkOnCategoryExistence(int categoryId, String editCategoryName,
        String adminCategoryName, ItemDataVector itemVector) {
        if (editCategoryName == null) {
            return new ActionErrors();
        }
        if (itemVector == null) {
            return new ActionErrors();
        }
        if (itemVector.size() == 0) {
            return new ActionErrors();
        }
        ActionErrors aErrors = null;
        for (Iterator it = itemVector.iterator(); it.hasNext();) {
            ItemData itemData = (ItemData)it.next();
            aErrors = compareItemCategoryNames(categoryId, editCategoryName, adminCategoryName, itemData);
			if (aErrors.size() > 0) {
				break;
			}
        }
        if (aErrors == null) {
        	aErrors = new ActionErrors();
        }
        return aErrors;
    }


    private static ActionErrors prepareStoreCategories (HttpServletRequest request,StoreItemMgrForm pForm ) throws Exception {
      ActionErrors ae = new ActionErrors();
      HttpSession session = request.getSession();
      APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);

      CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
      StoreData storeD = appUser.getUserStore();
      if (storeD == null) {
        ae.add("error",
               new ActionError("error.simpleGenericError", "No store info"));
        return ae;
      }

      int storeId = storeD.getBusEntity().getBusEntityId();

      CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();

      CatalogDataVector catalogDV = catalogInfEjb.getCatalogsCollectionByBusEntity(
          storeId,
          RefCodeNames.CATALOG_TYPE_CD.STORE);

      CatalogData cD = null;
      for (Iterator iter = catalogDV.iterator(); iter.hasNext(); ) {
        cD = (CatalogData) iter.next();
        if (RefCodeNames.CATALOG_STATUS_CD.ACTIVE.equals(cD.getCatalogStatusCd())) {
          break;
        }
      }
      if (cD == null) {
        ae.add("error", new ActionError("error.simpleGenericError", "No store catalog found"));
        return ae;
      }
      pForm.setStoreCatalogId(cD.getCatalogId());

      readStoreCategories(catalogInfEjb, pForm);
      ItemDataVector majorCategoryDV = catalogInfEjb.getStoreMajorCategories(cD.getCatalogId());
      pForm.setStoreMajorCategories(majorCategoryDV);
      return ae;
    }

    public static boolean getUseItemsLinksBetweenStores(HttpServletRequest request) {
        boolean isUseItemsLinksBetweenStores = false;
        String useItemsLinksBetweenStores = request.getParameter("useItemsLinksForAllStores");
        if (useItemsLinksBetweenStores != null) {
            if (useItemsLinksBetweenStores.equalsIgnoreCase("true")) {
                isUseItemsLinksBetweenStores = true;
            }
        }
        return isUseItemsLinksBetweenStores;
    }
    
    private static void parseItemDataFields(ProductData productD, int pStoreId) throws Exception {
        APIAccess factory = new APIAccess();
        PropertyService psvcBean = factory.getPropertyServiceAPI();
        BusEntityFieldsData fieldsData = psvcBean.fetchMasterItemFieldsData(pStoreId);
        ItemMetaDataVector itemMetaDV = productD.getProductAttributes();
        Iterator j = itemMetaDV.iterator();

        if (fieldsData != null && PropertyFieldUtil.isShowInAdmin(fieldsData)) {
          ItemMetaDataVector dataFields = new ItemMetaDataVector();
          for (int i=1; i<=15; i++) {
              if (PropertyFieldUtil.getShowInAdmin(fieldsData, i)) {
                  String tagName = PropertyFieldUtil.getTag(fieldsData, i);
                  ItemMetaData dataField = productD.getItemMeta(tagName);
                  if (dataField != null) {
                      dataFields.add(dataField);
                      productD.removeItemMeta(tagName);
                  } else {
                      dataField = ItemMetaData.createValue();
                      dataField.setNameValue(tagName);
                      dataField.setItemId(productD.getItemData().getItemId());
                      dataFields.add(dataField);
                  }
              }
          }
          if (dataFields.size() > 0) {
              productD.setDataFieldProperties(dataFields);
          }
        }
    }
    
    public static ActionErrors getMsdsUrlThroughWebService(HttpServletRequest request,
    	    StoreItemMgrForm pForm) throws Exception {
    	
    	log.debug("inside StoreItemMgrLogic.getMsdsUrlThroughWebServices() method");
        ActionErrors ae = new ActionErrors();
        
        ProductData productD = pForm.getProduct();
        String manufSku = productD.getManufacturerSku();
        log.debug("StoreItemMgrLogic.getMsdsUrlThroughWebServices()=>manufSku = " + manufSku);
        
        // if manufacturer sku could not be found using "productD.getManufacturerSku();"
        
        //int itemId = productD.getItemData().getItemId();
        //find user's country code and user's language code via Store's Locale
        
        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        User userEjb=factory.getUserAPI();
        
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.
          APP_USER);
        
        String storeLocale = appUser.getUserStore().getBusEntity().getLocaleCd().trim();
        log.debug("SVC_MSDS=>: StoreItemMgrLogic.getMsdsUrlThroughWebServices()=>storeLocale = " + storeLocale);        
        
        //split Store loacale into language code and country code
        String delimiter = "_";
        String[] localeArray = storeLocale.split(delimiter);
        String languageCode = localeArray[0];
        String countryCode = localeArray[1];
        log.debug("SVC_MSDS=>: StoreItemMgrLogic.getMsdsUrlThroughWebServices()=>languageCode = " + languageCode);
        log.debug("SVC_MSDS=>: StoreItemMgrLogic.getMsdsUrlThroughWebServices()=>countryCode = " + countryCode);
        
        // Calling JohnsonDiversey MSDS Web Service: Begin
        
        try {
           String wsMsdsUrl = DiverseyMSDSRetrieve.retrieveMsdsUrl(manufSku, countryCode, languageCode); //method returns Web Services MSDS URL
           pForm.setWsMsdsUrl(wsMsdsUrl);
        } catch (ServiceException exc) {
        	ae.add("error", new ActionError("error.simpleGenericError", "ServiceException happened."));
            exc.printStackTrace();
            //throw new RemoteException(
            //    "Error. StoreItemMgrLogic; DiverseyMSDSRetrieve.retrieveMsdsUrl: ServiceException happened. "
            //        + exc.getMessage());
        } catch (SOAPException exc) {
        	  ae.add("error", new ActionError("error.simpleGenericError", "SOAPException happened."));
              exc.printStackTrace();
              //throw new RemoteException(
              //    "Error. StoreItemMgrLogic; DiverseyMSDSRetrieve.retrieveMsdsUrl: SOAPException happened. "
              //        + exc.getMessage());
        } catch (MalformedURLException exc) {
      	      ae.add("error", new ActionError("error.simpleGenericError", "MalformedURLException happened."));
              exc.printStackTrace();
              //throw new RemoteException(
              //    "Error. StoreItemMgrLogic; DiverseyMSDSRetrieve.retrieveMsdsUrl: MalformedURLException happened. "
              //        + exc.getMessage());
        } catch (IOException exc) {
    	      ae.add("error", new ActionError("error.simpleGenericError", "IOException happened."));
              exc.printStackTrace();
              //throw new RemoteException(
              //    "Error. StoreItemMgrLogic; DiverseyMSDSRetrieve.retrieveMsdsUrl: IOException happened. "
              //        + exc.getMessage());
        } catch (Exception exc) {
  	        ae.add("error", new ActionError("error.simpleGenericError", "Exception happened."));
            exc.printStackTrace();
            //throw new RemoteException(
            //    "Error. StoreItemMgrLogic; DiverseyMSDSRetrieve.retrieveMsdsUrl: Exception happened. "
            //        + exc.getMessage());
        }
      
       
        // Calling JohnsonDiversey MSDS Web Service: End
        return ae;
    	
    }
    
    public static ActionErrors dummyMethod(HttpServletRequest request,
    	    StoreItemMgrForm pForm) throws Exception {
    	
    	//this method does nothing;
        ActionErrors ae = new ActionErrors();
        
        return ae;
    }   
    
    public static Element confirmMoveCategory(HttpServletRequest request,HttpServletResponse response, StoreItemMgrForm pForm) throws IOException {
    	log.info("confirmMoveTo");
    	String warningMessage = null;
    	String errorMessage = null;
    	try {
    		if (!pForm.getAllowMixedCategoryAndItemUnderSameParent()){    			
	    		HttpSession session = request.getSession();
	    		APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
	    		CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();
	    		int categoryId = pForm.getEditCategoryId();
	    		if (categoryId > 0 && pForm.getSelectedId() > 0 && (categoryId!=pForm.getSelectedId())){
		    		if (catalogInfEjb.doesCategoryContainProduct(pForm.getStoreCatalogId(), pForm.getSelectedId())){
		    			CatalogCategoryDataVector myChildCategoriesDV = catalogInfEjb.getCatalogChildCategories(pForm.getStoreCatalogId(), categoryId);
		        		if (myChildCategoriesDV.size() == 0){// lowest category
		    				warningMessage = "Warning!  There are items under this category. If you continue, all items will be moved to the new child category.";
		    			}else{
		    				errorMessage = "Error:  There are items under the selected category.  You need to move the items out of this category before you can add the new sub-category.";
		    			}
		    		}
	    		}
    		}
    	} catch (Exception exc) {
    		exc.printStackTrace();
    		errorMessage = "Error Cccurred";
    	}

    	response.setContentType("application/xml");
    	response.setHeader("Cache-Control", "no-cache");

    	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    	factory.setNamespaceAware(true);
    	DocumentBuilder docBuilder = null;

    	try {
    		docBuilder = factory.newDocumentBuilder();
    	} catch (ParserConfigurationException ex) { }

    	Document doc = docBuilder.getDOMImplementation().createDocument("", "response", null);
    	Element root =  doc.getDocumentElement();

    	root.setAttribute("warningMessage", warningMessage);
    	root.setAttribute("errorMessage", errorMessage);
    	OutputFormat format = new OutputFormat( Method.XML, "UTF-8", true );
    	XMLSerializer serializer = new XMLSerializer(response.getWriter(), format);
    	serializer.serialize(root);
    	return root;
    }

	public static ActionErrors showItemDocumentFromE3Storage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		ActionErrors errors = new ActionErrors();
		
        CleanwiseUser appUser = (CleanwiseUser) request.getSession()
                .getAttribute(Constants.APP_USER);
        //pForm.setAppUser(appUser); //I think I don't need pForm as a method parameter
        String path = request.getParameter("path");
        log.info("pathInit = " + path);
        
        //find contentName in the clw_content table based on the path; 
        //clw_content table: path is unique for a combination of (type of the item + item id)
        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        Content cont = factory.getContentAPI();
        
    	//add starting "." to parameter "path"
        path = "." + path;
        log.info("pathFinal = " + path);        
        
        ContentData contentD = cont.getContent(path);
        String contentName = contentD.getContentSystemRef();
        log.info("contentName = " + contentName);       
        //Check for E3 Storage Data: 
        //if we have JPG, GIF, PDF, DOC, or DOCX type document for a product item,
        //read it
        log.info("Get E3 Storage Data or document (image) from the server => BEGIN");              
        if (null == contentName || contentName.trim().equals("")) {
        	log.info("Document " + path + " was not found in E3 Storage");
        	String errorMess = "Document " + path + " was not found in E3 Storage";
        	errors.add("error", new ActionError("error.simpleGenericError", errorMess));
        	return errors; 
        }
        InputStream is = cont.getContentDataFromE3StorageSystem(contentName);
        log.info("is = " + is); //it should NOT be NULL
        
        //convert InputStream to byte[] Array        
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        try {
            for (int readNum; (readNum = is.read(buf)) != -1;) {
                bos.write(buf, 0, readNum); 
                //no doubt here is 0
                /*Writes len bytes from the specified byte array starting at offset 
                off to this byte array output stream.
                */
                //log.info("read " + readNum + " bytes,");
            }
        } catch (IOException ex) {
        	log.info("Error reading Document as ByteArray from the E3 Storage");
        	String errorMess = "Error reading Document from the E3 Storage";
        	errors.add("error", new ActionError("error.simpleGenericError", errorMess));
        	return errors;
        }
        byte[] bytes = bos.toByteArray();
        //"bytes" is the ByteArray we need
        log.info("response = " + response);
        
        if(response != null) {
        	if (contentD.getPath() != null && contentD.getPath().endsWith(".pdf")) { //.pdf file
        	    response.setContentType( "application/pdf" );
        	    response.setHeader("cache-control", "no-cache");
        	    response.setHeader("Pragma", "public");        	
            } else if (contentD.getPath() != null && contentD.getPath().endsWith(".gif")){ //.gif file: sometimes it doesn't work      	        	
                response.setContentType("image/gif");
            } else if (contentD.getPath() != null && contentD.getPath().endsWith(".jpg")){ //.jpg file  
                response.setContentType("image/jpeg");                
            } else if (contentD.getPath() != null && contentD.getPath().endsWith(".doc")){ //.doc file (MSWord)     	        	
                response.setContentType("application/msword");
            } else if (contentD.getPath() != null && contentD.getPath().endsWith(".docx")){ //.docx file (MSWord 2007)     	        	
                response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
            } else {
        	    String errorMess = "Cannot load the Document " + contentD.getPath() + " : unknown type";
        	    log.info(errorMess);
        	    errors.add("error", new ActionError("error.simpleGenericError", errorMess));
                return errors;
            }
	        response.getOutputStream().write(bytes);
 
        }
        log.info("Get E3 Storage Data or document (image) from the server => END"); 
        return errors; 
	}
	
	public static ActionErrors showItemDocumentFromDb(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
        log.info("Get Document (image, etc.) from the Database => BEGIN");
        
		ActionErrors errors = new ActionErrors();
		
        CleanwiseUser appUser = (CleanwiseUser) request.getSession()
                .getAttribute(Constants.APP_USER);
        //pForm.setAppUser(appUser); //I think I don't need pForm as a method parameter
        String path = request.getParameter("path");
        log.info("pathInit = " + path);
        
        //find contentName in the clw_content table based on the path; 
        //clw_content table: path is unique for a combination of (type of the item + item id)
        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        Content cont = factory.getContentAPI();
        
    	//add starting "." to parameter "path"
        path = "." + path;
        log.info("pathFinal1 = " + path);        
        
        ContentData contentD = cont.getContent(path);
        if (null == contentD) {
        	log.error("Record for the document " + path + " was not found in the Database");
        	String errorMess = "Record for the document " + path + " was not found in the Database";
     	    errors.add("error", new ActionError("error.simpleGenericError", errorMess));
     	    return errors; 
        }
                
        byte[] documentBinaryData = contentD.getBinaryData();              
        //Check the Database data: 
        //if we have JPG, GIF, PDF, DOC, or DOCX type document for a product item,
        //read it  
        if (null == documentBinaryData) {
        	log.error("Binary data for the document " + path + " was not found in the Database");
        	String path2 = "." + path; //there are some logos in the Database which path errorneously begins with ".." instead of "."(fix the Database data later) 
            contentD = cont.getContent(path2);
            if (null == contentD) {
        	   String errorMess = "Record for the document " + path + " was not found in the Database";
        	   errors.add("error", new ActionError("error.simpleGenericError", errorMess));
        	   return errors;
            } else {
            	documentBinaryData = contentD.getBinaryData(); 
            	if (null == documentBinaryData) {
                	log.error("Binary data for the document " + path2 + " was not found in the Database");
                	String errorMess = "Binary data for the document " + path2 + " was not found in the Database";
             	    errors.add("error", new ActionError("error.simpleGenericError", errorMess));
             	    return errors;
            	}                	
            }        	   
        }
        //Convert byte[] array to InputStream
        InputStream is = new ByteArrayInputStream(documentBinaryData);
        log.info("is = " + is); //it should NOT be NULL
        
        //convert InputStream to byte[] Array        
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        try {
            for (int readNum; (readNum = is.read(buf)) != -1;) {
                bos.write(buf, 0, readNum); 
                //no doubt here is 0
                /*Writes len bytes from the specified byte array starting at offset 
                off to this byte array output stream.
                */
                //log.info("read " + readNum + " bytes,");
            }
        } catch (IOException ex) {
        	log.info("Error reading Document as ByteArray from the Database");
        	String errorMess = "Error reading Document from the Database";
        	errors.add("error", new ActionError("error.simpleGenericError", errorMess));
        	return errors;
        }
        byte[] bytes = bos.toByteArray();
        //"bytes" is the ByteArray we need
        log.info("response = " + response);
        
        if(response != null) {
        	if (contentD.getPath() != null && contentD.getPath().endsWith(".pdf")) { //.pdf file
        	    response.setContentType( "application/pdf" );
        	    response.setHeader("cache-control", "no-cache");
        	    response.setHeader("Pragma", "public");        	
            } else if (contentD.getPath() != null && contentD.getPath().endsWith(".gif")){ //.gif file: sometimes it doesn't work      	        	
                response.setContentType("image/gif");
            } else if (contentD.getPath() != null && (contentD.getPath().endsWith(".jpg") || 
            		contentD.getPath().endsWith(".jpeg"))){ //.jpg && .jpeg file  
            	//STJ-5516
                response.setContentType("image/jpeg");                
            } else if (contentD.getPath() != null && contentD.getPath().endsWith(".png")){ //.png file      	        	
                response.setContentType("image/png");
            } else if (contentD.getPath() != null && contentD.getPath().endsWith(".bmp")){ //.bmp file      	        	
                response.setContentType("image/bmp");
            } else if (contentD.getPath() != null && contentD.getPath().endsWith(".doc")){ //.doc file (MSWord)     	        	
                response.setContentType("application/msword");
            } else if (contentD.getPath() != null && contentD.getPath().endsWith(".docx")){ //.docx file (MSWord 2007)     	        	
                response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
            } else {
        	    String errorMess = "Cannot load the Document " + contentD.getPath() + " : unknown type";
        	    log.info(errorMess);
        	    errors.add("error", new ActionError("error.simpleGenericError", errorMess));
                return errors;
            }
	        response.getOutputStream().write(bytes);
 
        }
        log.info("Get Document (image, etc.) from the Database => END"); 
        return errors; 
	}
    
}


