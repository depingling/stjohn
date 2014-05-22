/*
 * LocateStoreOrderGuideLogic.java
 *
 * Created on September, 2008
 */
 
package com.cleanwise.view.logic;

import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.cleanwise.view.utils.*;
import com.cleanwise.service.api.*;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.LocatePropertyNames;
import com.cleanwise.view.forms.LocateStoreOrderGuideForm;
import com.cleanwise.view.forms.StorePortalForm;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;


public class LocateStoreOrderGuideLogic {

    public static ActionErrors processAction(HttpServletRequest request, 
        StorePortalForm baseForm, String action) throws Exception {

        LocateStoreOrderGuideForm locateForm = baseForm.getLocateStoreOrderGuideForm();

        try {
            ActionErrors ae = new ActionErrors();
            HttpSession session = request.getSession();
            
            if (LocatePropertyNames.INIT_SEARCH_ACTION.equals(action)) {
                ae = initSearch(request, baseForm);
                return ae;
            }

            int myLevel = locateForm.getLevel() + 1;
            locateForm.setObjectsToReturn(null);    

            if (LocatePropertyNames.CANCEL_ACTION.equals(action)) {
                ae = returnNoValue(request, locateForm);
            } 
            else if (LocatePropertyNames.SEARCH_ACTION.equals(action)) {
                ae = search(request, locateForm);
            } 
            else if (LocatePropertyNames.RETURN_SELECTED_ACTION.equals(action)) {
                ae = returnSelected(request, locateForm);
            }
            return ae;
        }
        finally {
            if (locateForm != null)
                locateForm.reset(null, null);
        }
    }

    public static ActionErrors initSearch(HttpServletRequest request, 
        StorePortalForm baseForm) throws Exception {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        APIAccess factory = new APIAccess();
        Group groupEjb = factory.getGroupAPI();

        CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);
        StoreData storeD = appUser.getUserStore();
        if (storeD == null) {
            ae.add("error", new ActionError("error.simpleGenericError","No store info"));
            return ae;
        }
        
        LocateStoreOrderGuideForm locateForm = baseForm.getLocateStoreOrderGuideForm();

        if (locateForm == null) {
            locateForm = new LocateStoreOrderGuideForm();
            locateForm.setLevel(1);
            baseForm.setLocateStoreOrderGuideForm(locateForm);
        }

        String searchType = locateForm.getSearchObjectType(); 
        if (searchType == null || searchType.trim().length() == 0) {
            searchType = LocatePropertyNames.NAME_BEGINS_SEARCH_TYPE;
        }
        locateForm.setSearchObjectType(searchType);
        locateForm.setObjectsToReturn(null);

        return ae;
    }

    public static ActionErrors returnNoValue(HttpServletRequest request, 
        LocateStoreOrderGuideForm locateForm) throws Exception {
        if (locateForm == null) {
            return new ActionErrors();
        }
        locateForm.setObjectsToReturn(null);
        return new ActionErrors();
    }

    public static ActionErrors search(HttpServletRequest request, 
        LocateStoreOrderGuideForm locateForm) throws Exception {
        if (locateForm == null) {
            return new ActionErrors();
        }
        
        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        APIAccess factory = new APIAccess();
        
        String searchField = locateForm.getSearchObjectField();
        String searchType = locateForm.getSearchObjectType();
        String typeFilter = locateForm.getObjectTypeFilter();
        boolean showInactiveFl = locateForm.getShowInactiveObjectFl();


        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        IdVector storeIds = appUser.getUserStoreAsIdVector();
        
        OrderGuide orderGuideEjb = factory.getOrderGuideAPI();
        LinkedList orderGuideTypes = new LinkedList();
        orderGuideTypes.add(RefCodeNames.ORDER_GUIDE_TYPE_CD.ORDER_GUIDE_TEMPLATE);
        orderGuideTypes.add(RefCodeNames.ORDER_GUIDE_TYPE_CD.SITE_ORDER_GUIDE_TEMPLATE);
        //orderGuideTypes.add(RefCodeNames.ORDER_GUIDE_TYPE_CD.SHOPPING_CART);
        //orderGuideTypes.add(RefCodeNames.ORDER_GUIDE_TYPE_CD.INVENTORY_CART);
        OrderGuideDescDataVector orderGuideDescVector = orderGuideEjb.getCollectionByStore(
            storeIds, searchField, searchType, orderGuideTypes);
       //STJ-3790
        if(!showInactiveFl) {
            for(Iterator iter=orderGuideDescVector.iterator(); iter.hasNext();) {
                  OrderGuideDescData ogd = (OrderGuideDescData) iter.next();
              if(RefCodeNames.BUS_ENTITY_STATUS_CD.INACTIVE.
                      equals(ogd.getStatus())) {
                iter.remove();
              }
            }
          }

        locateForm.setObjectsSelected(orderGuideDescVector);        

        return ae;
    }

    public static ActionErrors returnSelected(HttpServletRequest request,
        LocateStoreOrderGuideForm locateForm) throws Exception {
        if (locateForm == null) {
            return new ActionErrors();
        }
        OrderGuideDescDataVector resDataVector = new OrderGuideDescDataVector();
        OrderGuideDescDataVector srcDataVector = locateForm.getObjectsSelected();
        int[] selectedIds = locateForm.getSelectedObjectIds();        
        if (selectedIds != null && srcDataVector != null) {
            for (Iterator iter = srcDataVector.iterator(); iter.hasNext();) {
                OrderGuideDescData data = (OrderGuideDescData) iter.next();
                for (int i = 0; i < selectedIds.length; i++) {
                    if (data.getOrderGuideId() == selectedIds[i]) {
                        resDataVector.add(data);
                    }
                }
            }
        }
        locateForm.setObjectsToReturn(resDataVector);
        return new ActionErrors();
    }

    public static ActionErrors clearFilter(HttpServletRequest request, 
        StorePortalForm baseForm) throws Exception {
        LocateStoreOrderGuideForm locateForm = baseForm.getLocateStoreOrderGuideForm();
        if (locateForm != null) {
            HttpSession session = request.getSession();
            org.apache.commons.beanutils.BeanUtils.setProperty(
                session.getAttribute(locateForm.getName()), locateForm.getProperty(), null);
        }
        return new ActionErrors();
    }

    public static ActionErrors setFilter(HttpServletRequest request, 
        StorePortalForm baseForm) throws Exception {
        LocateStoreOrderGuideForm locateForm = baseForm.getLocateStoreOrderGuideForm();
        if (locateForm != null) {
            HttpSession session = request.getSession();
            OrderGuideDescDataVector dataVector = locateForm.getObjectsToReturn();
            org.apache.commons.beanutils.BeanUtils.setProperty(
                session.getAttribute(locateForm.getName()), locateForm.getProperty(), dataVector);
            locateForm.setLocateOrderGuideFl(false);
        }
        return new ActionErrors();
    }
}

