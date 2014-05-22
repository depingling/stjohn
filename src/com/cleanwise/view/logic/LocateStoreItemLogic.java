/*
 * LocateStoreItemLogic.java
 *
 * Created on May 9, 2005, 11:44 AM
 */

package com.cleanwise.view.logic;

import java.util.List;
import java.util.Vector;
import java.util.LinkedList;
import java.util.Collection;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.math.BigDecimal;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.service.api.value.AddressData;
import com.cleanwise.service.api.value.PhoneData;
import com.cleanwise.view.utils.*;
import com.cleanwise.view.forms.*;
import com.cleanwise.service.api.*;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.SearchCriteria;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import java.util.Iterator;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import com.cleanwise.view.forms.LocateStoreItemForm;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.upload.FormFile;
import com.cleanwise.view.i18n.ClwI18nUtil;

/**
 *
 * @author Ykupershmidt
 */
public class LocateStoreItemLogic {

 public static ActionErrors processAction(HttpServletRequest request,
         StorePortalForm baseForm, String action)
      throws Exception
    {
    LocateStoreItemForm pForm = null;
    //int myLevel = baseForm.getLevel()+1;
    //baseForm.setLevel(myLevel);

    String searchStr = ClwI18nUtil.getMessage(request, "global.action.label.search",null);
    String cancelStr = ClwI18nUtil.getMessage(request, "global.action.label.cancel",null);
    String returnSelectedStr = ClwI18nUtil.getMessage(request, "admin.button.returnSelected",null);
    try {
    ActionErrors ae = new ActionErrors();
    HttpSession session  = request.getSession();
    if("initSearch".equals(action)) {
      ae = initSearch(request,baseForm);
      return ae;
    }

    pForm = baseForm.getLocateStoreItemForm();

    if(cancelStr.equals(action)) {
      ae = returnNoValue(request,pForm);
    } else if(searchStr.equals(action)) {
      if (pForm.getDataSourceType().equals("DW")) {
        ae = searchDW(request,pForm);
      } else {
        ae = search(request,pForm, baseForm);
      }
    } else if("Search Items".equals(action)) {
      ae = searchAllItems(request,pForm);
    } else if(returnSelectedStr.equals(action)) {
      ae = returnSelected(request,pForm);
    }

    return ae;
    }finally {
      if(pForm!=null)  pForm.reset(null, null);
    }
 }



 public static ActionErrors initSearch(HttpServletRequest request, StorePortalForm baseForm)
      throws Exception
    {
    ActionErrors ae = new ActionErrors();
    HttpSession session  = request.getSession();

    APIAccess factory = new APIAccess();
    CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();
    Manufacturer manufEjb = factory.getManufacturerAPI();

    CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);
    StoreData storeD = appUser.getUserStore();
    if(storeD==null) {
      ae.add("error",new ActionError("error.simpleGenericError","No store info"));
      return ae;
    }
    LocateStoreItemForm pForm = baseForm.getLocateStoreItemForm();
    if(pForm==null) {
      pForm = new LocateStoreItemForm();
      baseForm.setLocateStoreItemForm(pForm);
    }
    //baseForm.setEmbeddedForm(pForm);

    pForm.setLoginStore(storeD);
    pForm.setStore(storeD);

    return ae;
 }


 public static ActionErrors returnNoValue(HttpServletRequest request,
                      LocateStoreItemForm pForm)
      throws Exception
    {
    ActionErrors ae = new ActionErrors();
    HttpSession session  = request.getSession();
    pForm.setItemsToReturn(null);
    return ae;
 }

  public static ActionErrors search(HttpServletRequest request,
            LocateStoreItemForm pForm, StorePortalForm baseForm)
             throws Exception {

      HttpSession session = request.getSession();
      ActionErrors ae = new ActionErrors();
      APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
      CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();

       pForm.setItemsSelected(new ItemViewVector());
       pForm.setDistInfoFlag(false);
      CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);
      StoreData storeD = pForm.getStore();
      if(storeD==null) {
        ae.add("error",new ActionError("error.simpleGenericError","No store info"));
        return ae;
      }

      int storeId = storeD.getBusEntity().getBusEntityId();
      if (baseForm instanceof AnalyticReportForm) {
        IdVector selectedStoreIds = AnalyticReportLogic.getStoreSelectedFilter(request, Constants.STORE_FILTER_NAME);
        if (selectedStoreIds != null && selectedStoreIds.size() > 0) {
          storeId = ( (Integer) selectedStoreIds.get(0)).intValue();
        }
      }

      CatalogDataVector catalogDV =
          catalogInfEjb.getCatalogsCollectionByBusEntity(storeId,RefCodeNames.CATALOG_TYPE_CD.STORE);

      CatalogData cD = null;
      for(Iterator iter=catalogDV.iterator(); iter.hasNext();) {
        cD = (CatalogData) iter.next();
        if(RefCodeNames.CATALOG_STATUS_CD.ACTIVE.equals(cD.getCatalogStatusCd())) {
          break;
        }
      }
      if(cD==null) {
        ae.add("error",new ActionError("error.simpleGenericError","No store catalog found"));
        return ae;
      }

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
      if(!Utility.isSet(skuType)) skuType = SearchCriteria.STORE_SKU_NUMBER;
      if(SearchCriteria.DISTRIBUTOR_SKU_NUMBER.equals(skuType)) pForm.setDistInfoFlag(true);
      if(pForm.getDistInfoRequest())  pForm.setDistInfoFlag(true);
      pForm.setSkuType(skuType);
      //Create a set of filters
      LinkedList searchConditions = new LinkedList();

      SearchCriteria scStore = new
            SearchCriteria(SearchCriteria.STORE_ID,
               SearchCriteria.EXACT_MATCH,new Integer(storeId));
           searchConditions.add(scStore);
       //Category
       if(Utility.isSet(category)) {
         SearchCriteria sc = new
            SearchCriteria(SearchCriteria.CATALOG_CATEGORY,
               SearchCriteria.CONTAINS_IGNORE_CASE,category);
           searchConditions.add(sc);
       }
      //Short Desc
      if(Utility.isSet(shortDesc)){
         SearchCriteria sc = new
           SearchCriteria(SearchCriteria.PRODUCT_SHORT_DESC,
                 SearchCriteria.CONTAINS_IGNORE_CASE,shortDesc);
            searchConditions.add(sc);
      }

      //Long Desc
      if(Utility.isSet(longDesc)){
         SearchCriteria sc = new
         SearchCriteria(SearchCriteria.PRODUCT_LONG_DESC,
                 SearchCriteria.CONTAINS_IGNORE_CASE,longDesc);
            searchConditions.add(sc);
      }

      //Item Meta Property
      if(Utility.isSet(itemPropName) && Utility.isSet(itemPropTempl)) {
        SearchCriteria sc = new
        SearchCriteria(SearchCriteria.ITEM_META+itemPropName,
                SearchCriteria.CONTAINS_IGNORE_CASE,itemPropTempl);
           searchConditions.add(sc);
      }

       //Manufacturer
       if(Utility.isSet(manuNameTempl)) {
        SearchCriteria sc = new
        SearchCriteria(SearchCriteria.MANUFACTURER_SHORT_DESC,
                SearchCriteria.CONTAINS_IGNORE_CASE,manuNameTempl);
           searchConditions.add(sc);
       }

       //Distributor
       if(Utility.isSet(distNameTempl)) {
        SearchCriteria sc = new
        SearchCriteria(SearchCriteria.DISTRIBUTOR_SHORT_DESC,
                SearchCriteria.CONTAINS_IGNORE_CASE,distNameTempl);
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

      // selected catalogs
      if (pForm.getSearchInSelectedCatalogs()) {
        String selectedCatalogs = pForm.getSelectedCatalogList();
        SearchCriteria sc = new SearchCriteria(SearchCriteria.CATALOG_ID, SearchCriteria.CONTAINS, selectedCatalogs);
        searchConditions.add(sc);
      }
      if(!pForm.getShowInactiveFl())
      {
        SearchCriteria sc = new SearchCriteria(SearchCriteria.ITEM_STATUS_CD, SearchCriteria.CONTAINS, RefCodeNames.ITEM_STATUS_CD.ACTIVE);
        searchConditions.add(sc);
      }

      ItemViewVector items = catalogInfEjb.searchStoreItems(searchConditions, pForm.getDistInfoFlag());



      pForm.setItemsSelected(items);
      return ae;

    }

    public static ActionErrors searchDW(HttpServletRequest request,
              LocateStoreItemForm pForm)
               throws Exception {
        HttpSession session = request.getSession();
        ActionErrors ae = new ActionErrors();
        APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
//        CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();
        DWOperation dwBean = factory.getDWOperationAPI();

         pForm.setItemsSelected(new ItemViewVector());
         pForm.setDistInfoFlag(false);
        CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);
        // Store FILTER
        IdVector selectedStoreIds = AnalyticReportLogic.getStoreSelectedFilter(request, Constants.DW_STORE_FILTER_NAME);
        int storeId = (selectedStoreIds != null) ? ((Integer)selectedStoreIds.get(0)).intValue() : -1;

      //get the search data from the form
        String category = pForm.getCategoryTempl();
        String shortDesc = pForm.getShortDescTempl();
//        String longDesc = pForm.getLongDescTempl();
//        String itemPropName = pForm.getItemPropertyName();
//        String itemPropTempl = pForm.getItemPropertyTempl();
        String manuNameTempl = pForm.getManuNameTempl();
//        String distNameTempl = pForm.getDistNameTempl();
        String skuNumber = pForm.getSkuTempl();
        String skuType = pForm.getSkuType();
        if(!Utility.isSet(skuType)) skuType = SearchCriteria.STORE_SKU_NUMBER;
        if(SearchCriteria.DISTRIBUTOR_SKU_NUMBER.equals(skuType)) pForm.setDistInfoFlag(true);
        if(pForm.getDistInfoRequest())  pForm.setDistInfoFlag(true);
        pForm.setSkuType(skuType);
        //Create a set of filters
        LinkedList searchConditions = new LinkedList();

        SearchCriteria scStore = new
              SearchCriteria(SearchCriteria.STORE_ID,
                 SearchCriteria.EXACT_MATCH,new Integer(storeId));
             searchConditions.add(scStore);
         //Category
         if(Utility.isSet(category)) {
           SearchCriteria sc = new
              SearchCriteria(SearchCriteria.CATALOG_CATEGORY,
                 SearchCriteria.CONTAINS_IGNORE_CASE,category);
             searchConditions.add(sc);
         }
        //Short Desc
        if(Utility.isSet(shortDesc)){
           SearchCriteria sc = new
             SearchCriteria(SearchCriteria.PRODUCT_SHORT_DESC,
                   SearchCriteria.CONTAINS_IGNORE_CASE,shortDesc);
              searchConditions.add(sc);
        }

         //Manufacturer
         if(Utility.isSet(manuNameTempl)) {
          SearchCriteria sc = new
          SearchCriteria(SearchCriteria.MANUFACTURER_SHORT_DESC,
                  SearchCriteria.CONTAINS_IGNORE_CASE,manuNameTempl);
             searchConditions.add(sc);
         }


         //sku
         if(Utility.isSet(skuNumber)) {
           SearchCriteria sc = new
                SearchCriteria(skuType,
                        SearchCriteria.EXACT_MATCH, skuNumber);
                      searchConditions.add(sc);
         }

        // selected catalogs
        if (pForm.getSearchInSelectedCatalogs()) {
          String selectedCatalogs = pForm.getSelectedCatalogList();
          SearchCriteria sc = new SearchCriteria(SearchCriteria.CATALOG_ID, SearchCriteria.CONTAINS, selectedCatalogs);
          searchConditions.add(sc);
        }
        if(!pForm.getShowInactiveFl())
        {
          SearchCriteria sc = new SearchCriteria(SearchCriteria.ITEM_STATUS_CD, SearchCriteria.CONTAINS, RefCodeNames.ITEM_STATUS_CD.ACTIVE);
          searchConditions.add(sc);
        }

//        ItemViewVector items = catalogInfEjb.searchStoreItems(searchConditions, pForm.getDistInfoFlag());
        ItemViewVector items = dwBean.searchStoreItems(searchConditions, pForm.getDistInfoFlag());

        pForm.setItemsSelected(items);
        return ae;

      }


  public static ActionErrors returnSelected(HttpServletRequest request,
            LocateStoreItemForm pForm)
             throws Exception {

    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    int[] selected = pForm.getSelectedItemIds();
    ItemViewVector itemVwV = pForm.getItemsSelected();
    ItemViewVector retItemVwV = new ItemViewVector();
    if(itemVwV!=null)
    for(Iterator iter=itemVwV.iterator(); iter.hasNext();) {
      ItemView iVw = (ItemView) iter.next();
      boolean found = false;
      for(int ii=0; ii<selected.length; ii++) {
        if(iVw.getItemId()==selected[ii]) {
          if(!found) {
            found = true;
            retItemVwV.add(iVw);
          }
          selected[ii]=0;
        }
      }
    }
    pForm.setItemsToReturn(retItemVwV);
    return ae;
    }


  public static ActionErrors clearFilter(HttpServletRequest request,StorePortalForm pForm) throws Exception{
      HttpSession session = request.getSession();
      LocateStoreItemForm form = pForm.getLocateStoreItemForm();
      org.apache.commons.beanutils.BeanUtils.setProperty(session.getAttribute(form.getName()),form.getProperty(),null);
      return new ActionErrors();
  }

  public static ActionErrors setFilter(HttpServletRequest request,StorePortalForm pForm)
             throws Exception {

    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    LocateStoreItemForm form = pForm.getLocateStoreItemForm();
    Object o = form.getItemsToReturn();
    org.apache.commons.beanutils.BeanUtils.setProperty(session.getAttribute(form.getName()),form.getProperty(),o);
    form.setLocateItemFl(false);
    return ae;
    }


  /*
   * search items cross the stores if the selected storedId is null
   *
   */
    public static ActionErrors searchAllItems(HttpServletRequest request,
            LocateStoreItemForm pForm)
             throws Exception {

        HttpSession session = request.getSession();
        ActionErrors ae = new ActionErrors();
        APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
        CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();

        pForm.setItemsSelected(new ItemViewVector());
        pForm.setDistInfoFlag(false);
        CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);
        //StoreData storeD = pForm.getStore();
        //if(storeD==null) {
        //  ae.add("error",new ActionError("error.simpleGenericError","No store info"));
        //  return ae;
        //}

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
        if(!Utility.isSet(skuType)) skuType = SearchCriteria.STORE_SKU_NUMBER;
        if(SearchCriteria.DISTRIBUTOR_SKU_NUMBER.equals(skuType)) pForm.setDistInfoFlag(true);
        if(pForm.getDistInfoRequest())  pForm.setDistInfoFlag(true);
        pForm.setSkuType(skuType);

        IdVector storeIdV = new IdVector();

        Store storeEjb = factory.getStoreAPI();
        BusEntityDataVector storeDV = appUser.getStores();
        StoreDataVector storeDataV = new StoreDataVector();
        for (int i = 0; i < storeDV.size(); i++) {
            BusEntityData storeD = (BusEntityData)storeDV.get(i);

            StoreData   storeDetail = storeEjb.getStore(storeD.getBusEntityId());
            if ( ! RefCodeNames.STORE_TYPE_CD.ENTERPRISE.equalsIgnoreCase(storeDetail.getStoreType().getValue())) {
                storeDataV.add(storeDetail);
                storeIdV.add(new Integer(storeDetail.getBusEntity().getBusEntityId()));
            }
        }


        String storeIdStr = pForm.getStoreId();
        int storeId = 0;
        if (null != storeIdStr && 0 < storeIdStr.trim().length()) {
            storeId = Integer.parseInt(storeIdStr);
            if(storeIdV.contains(new Integer(storeId))) {
                storeIdV = new IdVector();
                storeIdV.add(new Integer(storeId));
            }
            else {
                storeIdV = new IdVector();
            }
        }

        ItemViewVector itemV = new ItemViewVector();
        if (null != storeIdV && storeIdV.size() > 0) {
            for(int i = 0; i < storeIdV.size(); i++) {
                storeId = ((Integer)storeIdV.get(i)).intValue();

                CatalogDataVector catalogDV =
                    catalogInfEjb.getCatalogsCollectionByBusEntity(storeId,RefCodeNames.CATALOG_TYPE_CD.STORE);

                CatalogData cD = null;
                for(Iterator iter=catalogDV.iterator(); iter.hasNext();) {
                    cD = (CatalogData) iter.next();
                    if(RefCodeNames.CATALOG_STATUS_CD.ACTIVE.equals(cD.getCatalogStatusCd())) {
                        break;
                    }
                }
                if(cD==null) {
                    ae.add("error",new ActionError("error.simpleGenericError","No store catalog found"));
                    return ae;
                }

                //Create a set of filters
                LinkedList searchConditions = new LinkedList();

                SearchCriteria scStore = new
                    SearchCriteria(SearchCriteria.STORE_ID,
                        SearchCriteria.EXACT_MATCH,new Integer(storeId));
                searchConditions.add(scStore);
                //Category
                if(Utility.isSet(category)) {
                    SearchCriteria sc = new
                        SearchCriteria(SearchCriteria.CATALOG_CATEGORY,
                             SearchCriteria.CONTAINS_IGNORE_CASE,category);
                    searchConditions.add(sc);
                }
                //Short Desc
                if(Utility.isSet(shortDesc)){
                    SearchCriteria sc = new
                    SearchCriteria(SearchCriteria.PRODUCT_SHORT_DESC,
                         SearchCriteria.CONTAINS_IGNORE_CASE,shortDesc);
                    searchConditions.add(sc);
                }

                //Long Desc
                if(Utility.isSet(longDesc)){
                    SearchCriteria sc = new
                        SearchCriteria(SearchCriteria.PRODUCT_LONG_DESC,
                            SearchCriteria.CONTAINS_IGNORE_CASE,longDesc);
                    searchConditions.add(sc);
                }

                //Item Meta Property
                if(Utility.isSet(itemPropName) && Utility.isSet(itemPropTempl)) {
                    SearchCriteria sc = new
                        SearchCriteria(SearchCriteria.ITEM_META+itemPropName,
                            SearchCriteria.CONTAINS_IGNORE_CASE,itemPropTempl);
                    searchConditions.add(sc);
                }

                //Manufacturer
                if(Utility.isSet(manuNameTempl)) {
                    SearchCriteria sc = new
                        SearchCriteria(SearchCriteria.MANUFACTURER_SHORT_DESC,
                            SearchCriteria.CONTAINS_IGNORE_CASE,manuNameTempl);
                    searchConditions.add(sc);
                }

                //Distributor
                if(Utility.isSet(distNameTempl)) {
                    SearchCriteria sc = new
                        SearchCriteria(SearchCriteria.DISTRIBUTOR_SHORT_DESC,
                            SearchCriteria.CONTAINS_IGNORE_CASE,distNameTempl);
                    searchConditions.add(sc);
                    pForm.setDistInfoFlag(true);
                }

                //sku
                 if(Utility.isSet(skuNumber)) {
                    SearchCriteria sc = new
                        SearchCriteria(skuType,
                      SearchCriteria.EXACT_MATCH, skuNumber);
                    searchConditions.add(sc);
                }

                ItemViewVector items = catalogInfEjb.searchStoreItems(searchConditions, pForm.getDistInfoFlag());
                if (null != items && items.size() > 0) {
                    for (int k = 0; k < items.size(); k++) {
                        ItemView itemVw = (ItemView)items.get(k);
                        for (int j = 0; j < storeDV.size(); j++) {
                            BusEntityData storeD = (BusEntityData)storeDV.get(j);
                            if(itemVw.getStoreId() == storeD.getBusEntityId()) {
                                itemVw.setStoreName(storeD.getShortDesc());
                                break;
                            }
                        }

                        itemV.add(itemVw);
                    }
                }
            } // end of for loop for storeV

        }
        pForm.setItemsSelected(itemV);
        return ae;

    }

}




