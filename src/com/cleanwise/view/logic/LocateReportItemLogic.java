/*
 * LocateReportAccountLogic.java
 *
 * Created on October, 2008
 */
package com.cleanwise.view.logic;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import com.cleanwise.view.utils.*;
import com.cleanwise.service.api.*;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.view.forms.LocateReportAccountForm;
import com.cleanwise.view.forms.StorePortalForm;
import com.cleanwise.view.forms.LocateReportItemForm;
import com.cleanwise.service.api.session.DWOperation;
import com.cleanwise.service.api.util.*;


public class LocateReportItemLogic {

    public static ActionErrors processAction(HttpServletRequest request, StorePortalForm baseForm, String action) throws Exception {
        LocateReportItemForm form = baseForm.getLocateReportItemForm();

        try {
            ActionErrors ae = new ActionErrors();
            HttpSession session = request.getSession();
            if (LocatePropertyNames.INIT_SEARCH_ACTION.equals(action)) {
                ae = initSearch(request, baseForm);
                return ae;
            }

            int myLevel = form.getLevel() + 1;
            form.setItemsToReturn(null);

            if (LocatePropertyNames.CANCEL_ACTION.equals(action)) {
                ae = returnNoValue(request, form);
            }
            else if (LocatePropertyNames.SEARCH_ACTION.equals(action)) {
                ae = search(request, form);
            }
            else if (LocatePropertyNames.RETURN_SELECTED_ACTION.equals(action)) {
                ae = returnSelected(request, form);
            }
            return ae;
        }
        finally {
            if (form != null) {
                form.reset(null, null);
            }
        }
    }

    public static ActionErrors initSearch(HttpServletRequest request, StorePortalForm baseForm) throws Exception {
        ActionErrors ae = new ActionErrors();
        HttpSession session  = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);
        StoreData storeD = appUser.getUserStore();
        if(storeD == null) {
            ae.add("error",new ActionError("error.simpleGenericError","No store info"));
            return ae;
        }

        LocateReportItemForm form = baseForm.getLocateReportItemForm();
        if (form == null) {
            form = new LocateReportItemForm();
            form.setLevel(1);
            baseForm.setLocateReportItemForm(form);
        }

        form.setCategoryTempl("");
        form.setCategoryTemplType(LocatePropertyNames.NAME_BEGINS_SEARCH_TYPE);
        form.setShortDescTempl("");
        form.setShortDescTemplType(LocatePropertyNames.NAME_BEGINS_SEARCH_TYPE);
        form.setManufTempl("");
        form.setManufTemplType(LocatePropertyNames.NAME_BEGINS_SEARCH_TYPE);
        form.setSkuTempl("");
        form.setSkuTemplType(SearchCriteria.DISTRIBUTOR_SKU_NUMBER);

        ItemViewVector itemDV = form.getItems();
        if (itemDV == null) {
            form.setItems(new ItemViewVector());
        }
        form.setItemsToReturn(null);
        return ae;
    }

    public static ActionErrors returnNoValue(HttpServletRequest request, LocateReportItemForm form) throws Exception {
        HttpSession session = request.getSession();
        session.removeAttribute(SessionAttributes.SEARCH_RESULT.REPORT_ITEMS);
        return new ActionErrors();
    }

    public static ActionErrors search(HttpServletRequest request, LocateReportItemForm pForm) throws Exception {
        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
        CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();

        pForm.setItems(new ItemViewVector());

        //Create a set of filters
        LinkedList searchConditions = new LinkedList();

        CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);
        int storeId = appUser.getUserStore().getStoreId();
        SearchCriteria scStore = new
            SearchCriteria(SearchCriteria.STORE_ID,
               SearchCriteria.EXACT_MATCH,new Integer(storeId));
           searchConditions.add(scStore);

       //Category
       String fieldCategory = pForm.getCategoryTempl();
       if(Utility.isSet(fieldCategory)) {
            String fieldType = pForm.getCategoryTemplType();
            int type = SearchCriteria.BEGINS_WITH_IGNORE_CASE;
            if (fieldType.equals(LocatePropertyNames.NAME_CONTAINS_SEARCH_TYPE)) {
                type =  SearchCriteria.CONTAINS_IGNORE_CASE;
            }
             SearchCriteria sc = new
                SearchCriteria(SearchCriteria.CATALOG_CATEGORY,type,fieldCategory);
            searchConditions.add(sc);
       }

      //Short Desc
       String fieldShortDesc = pForm.getShortDescTempl();
       if(Utility.isSet(fieldShortDesc)) {
            String fieldType = pForm.getShortDescTemplType();
            int type = SearchCriteria.BEGINS_WITH_IGNORE_CASE;
            if (fieldType.equals(LocatePropertyNames.NAME_CONTAINS_SEARCH_TYPE)) {
                type =  SearchCriteria.CONTAINS_IGNORE_CASE;
            }
             SearchCriteria sc = new
                SearchCriteria(SearchCriteria.PRODUCT_SHORT_DESC,type,fieldShortDesc);
            searchConditions.add(sc);
       }

       //Manufacturer
       String fieldManuf = pForm.getManufTempl();
       if(Utility.isSet(fieldManuf)) {
            String fieldType = pForm.getManufTemplType();
            int type = SearchCriteria.BEGINS_WITH_IGNORE_CASE;
            if (fieldType.equals(LocatePropertyNames.NAME_CONTAINS_SEARCH_TYPE)) {
                type =  SearchCriteria.CONTAINS_IGNORE_CASE;
            }
            SearchCriteria sc = new
                SearchCriteria(SearchCriteria.MANUFACTURER_SHORT_DESC, type,fieldManuf);
           searchConditions.add(sc);
       }

        //sku
        String fieldSku = pForm.getSkuTempl();
        if (Utility.isSet(fieldSku)) {
       	    String fieldType = pForm.getSkuTemplType();
            SearchCriteria sc = new
                SearchCriteria(fieldType, SearchCriteria.EXACT_MATCH_IGNORE_CASE, fieldSku);
           	searchConditions.add(sc);
       }

        // catalogs
        IdVector catalogIdV = catalogInfEjb.getCatalogIdsCollectionByUser(appUser.getUserId());
        if (catalogIdV.size() > 0) {
            SearchCriteria sc = new SearchCriteria(SearchCriteria.CATALOG_ID, SearchCriteria.CONTAINS, catalogIdV);
            searchConditions.add(sc);
        }

        ItemViewVector items = catalogInfEjb.searchStoreItems(searchConditions, false);

        pForm.setItems(items);
        pForm.setItemsToReturn(items);

        return ae;
    }


    public static ActionErrors returnSelected(HttpServletRequest request, LocateReportItemForm form) throws Exception {
        int[] selected = form.getSelected();
        ItemViewVector dv = form.getItems();
        ItemViewVector retDV = new ItemViewVector();

        for (Iterator iter = dv.iterator(); iter.hasNext();) {
            ItemView aD = (ItemView) iter.next();
            for (int i = 0; i < selected.length; i++) {
                if (aD.getItemId() == selected[i]) {
                    retDV.add(aD);
                }
            }
        }
        form.setItemsToReturn(retDV);

        clearMultiItemFilterInSession(request, getFilterName(form));
	    putMultiItemFilter2Session(request, retDV, getFilterName(form));

        return new ActionErrors();
    }

    public static ActionErrors clearFilter(HttpServletRequest request, StorePortalForm form) throws Exception {
        HttpSession session = request.getSession();
        LocateReportItemForm itemForm = form.getLocateReportItemForm();
        if( itemForm !=null ){
          org.apache.commons.beanutils.BeanUtils.setProperty(session.getAttribute(itemForm.getName()), itemForm.getProperty(), null);
        }

        clearMultiItemFilterInSession(request, getFilterName(itemForm));
        return new ActionErrors();
    }

    public static ActionErrors setFilter(HttpServletRequest request, StorePortalForm form) throws Exception {

        HttpSession session = request.getSession();
        LocateReportItemForm acctForm = form.getLocateReportItemForm();
        ItemViewVector acctDV = acctForm.getItemsToReturn();
        org.apache.commons.beanutils.BeanUtils.setProperty(session.getAttribute(acctForm.getName()), acctForm.getProperty(), acctDV);
        acctForm.setLocateItemFl(false);
        form.setReturnLocateTypeCd(RefCodeNames.RETURN_LOCATE_TYPE_CD.ITEM);
        return new ActionErrors();
    }


    public static HashMap getReportSearchFilter(HttpServletRequest request) {
    	HttpSession session = request.getSession();
    	if (session.getAttribute("REPORT_CONTROL_FILTER_MAP") == null) {
            session.setAttribute("REPORT_CONTROL_FILTER_MAP", new HashMap());
        }
  		HashMap hashMapFilter = (HashMap)session.getAttribute("REPORT_CONTROL_FILTER_MAP");
    	return hashMapFilter;
    }

    public static ActionErrors clearMultiItemFilterInSession(HttpServletRequest request, String filterName) {
    	HashMap hashMapFilter = getReportSearchFilter(request);
    	if (hashMapFilter != null) {
    		hashMapFilter.put(filterName, new LinkedList());
    	}
    	return new ActionErrors();
    }


    public static ActionErrors putMultiItemFilter2Session(HttpServletRequest request, ItemViewVector items, String filterName) {

    	HashMap hashMapFilter = null;
    	boolean canPutItems2Session = false;
    	ActionErrors errors = new ActionErrors();

    	/// Checking of the presence of the found store in the session attribute
    	hashMapFilter = getReportSearchFilter(request);
    	if (hashMapFilter == null) {
    		// to-do 'error'
		}
		else {
			canPutItems2Session = true;
		}

    	/// Putting the found items into proper session attribute
    	if (canPutItems2Session) {
    		LinkedList itemList = new LinkedList();
    		if (items != null && items.size() > 0) {
    			for (int i = 0; i < items.size(); ++i) {
    				ItemView item = (ItemView)items.get(i);
                    itemList.add(new Integer(item.getItemId()));

    			}
    		}
    		hashMapFilter.put(filterName, itemList);
    	}

    	return errors;
    }

    private static String getFilterName (LocateReportItemForm pForm){
      String filterName = Constants.ITEM_FILTER_NAME;
      return filterName;
     }


}
