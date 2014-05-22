package com.cleanwise.view.logic;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.Group;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.LocatePropertyNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.GroupData;
import com.cleanwise.service.api.value.GroupDataVector;
import com.cleanwise.service.api.value.GroupSearchCriteriaView;
import com.cleanwise.service.api.value.StoreData;
import com.cleanwise.view.forms.LocateStoreGroupForm;
import com.cleanwise.view.forms.StorePortalForm;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;


public class LocateStoreGroupLogic {

    public static ActionErrors processAction(HttpServletRequest request, 
        StorePortalForm baseForm, String action) throws Exception {
        LocateStoreGroupForm locateForm = baseForm.getLocateStoreGroupForm();

        try {
            ActionErrors ae = new ActionErrors();
            if(LocatePropertyNames.INIT_SEARCH_ACTION.equals(action)) {
                ae = initSearch(request, baseForm);
                return ae;
            }

            locateForm.setGroupsToReturn(null);
            if (LocatePropertyNames.CANCEL_ACTION.equals(action)) {
                ae = returnNoValue(request, locateForm);
            } else if(LocatePropertyNames.SEARCH_ACTION.equals(action)) {
                ae = search(request, locateForm);
            } else if(LocatePropertyNames.RETURN_SELECTED_ACTION.equals(action)) {
                ae = returnSelected(request, locateForm);
            }

            return ae;
        } finally {
            if (locateForm != null) {
                locateForm.reset(null, null);
            }
        }
    }

    public static ActionErrors initSearch(HttpServletRequest request, 
        StorePortalForm baseForm) throws Exception {
        ActionErrors errors = new ActionErrors();
        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);
        StoreData storeData = appUser.getUserStore();
        if (storeData == null) {
            errors.add("error", new ActionError("error.simpleGenericError", "No store info"));
            return errors;
        }

        LocateStoreGroupForm locateForm = baseForm.getLocateStoreGroupForm();

        if (locateForm == null) {
            locateForm = new LocateStoreGroupForm();
            locateForm.setLevel(1);
            baseForm.setLocateStoreGroupForm(locateForm);
        }

        locateForm.setGroupsToReturn(null);
        locateForm.setSearchStoreId(-1);
        locateForm.setGroupType("");

        return errors;
    }

    public static ActionErrors returnNoValue(HttpServletRequest request, 
        LocateStoreGroupForm locateForm) throws Exception {
        locateForm.setGroupsToReturn(new GroupDataVector());
        return new ActionErrors();
    }

    public static ActionErrors search(HttpServletRequest request, 
        LocateStoreGroupForm locateForm) throws Exception {
        ActionErrors errors = new ActionErrors();
        try{
            String groupName = locateForm.getGroupName();
            String groupType = locateForm.getGroupType();
            boolean showInactiveFl = locateForm.getShowInactiveFl();
            HttpSession session = request.getSession();
            CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);
            StoreData storeData = appUser.getUserStore();
            int storeId = storeData.getBusEntity().getBusEntityId();

            GroupSearchCriteriaView criteriaView = GroupSearchCriteriaView.createValue();
            criteriaView.setStoreId(storeId);
            if (Utility.isSet(groupName)) {
                criteriaView.setGroupName(groupName);
            }
            if (Utility.isSet(groupType)) {
                criteriaView.setGroupType(groupType);
            }
            if (!showInactiveFl) {
                criteriaView.setGroupStatus(RefCodeNames.GROUP_STATUS_CD.ACTIVE);
            }

            APIAccess factory = new APIAccess();
            Group groupEjb = factory.getGroupAPI();
            GroupDataVector groupDataVector = groupEjb.getGroups(criteriaView, 
                Group.NAME_CONTAINS_IGNORE_CASE, Group.ORDER_BY_NAME);
            locateForm.setGroups(groupDataVector);
            locateForm.setSearchStoreId(storeId);
        }catch(Exception ex){
            ex.printStackTrace();
            errors.add("LocateStoreGroupLogic", new ActionError("error.genericError", ex.getMessage()));
        }
        return errors;
    }

    public static ActionErrors returnSelected(HttpServletRequest request, 
        LocateStoreGroupForm locateForm) throws Exception {
        if (locateForm == null) {
            return new ActionErrors();
        }
        GroupDataVector resDataVector = new GroupDataVector();
        GroupDataVector srcDataVector = locateForm.getGroups();
        int[] selectedIds = locateForm.getSelected();
        if (selectedIds != null && srcDataVector != null) {
            for (Iterator iter = srcDataVector.iterator(); iter.hasNext();) {
                GroupData data = (GroupData) iter.next();
                for (int i = 0; i < selectedIds.length; i++) {
                    if (data.getGroupId() == selectedIds[i]) {
                        resDataVector.add(data);
                    }
                }
            }
        }
        locateForm.setGroupsToReturn(resDataVector);
        return new ActionErrors();
    }

    public static ActionErrors clearFilter(HttpServletRequest request, StorePortalForm baseForm) throws Exception{
        LocateStoreGroupForm locateForm = baseForm.getLocateStoreGroupForm();
        if (locateForm != null) {
            HttpSession session = request.getSession();
            org.apache.commons.beanutils.BeanUtils.setProperty(
                session.getAttribute(locateForm.getName()), locateForm.getProperty(), null);
        }
        return new ActionErrors();
    }

    public static ActionErrors setFilter(HttpServletRequest request, 
        StorePortalForm baseForm) throws Exception {
        LocateStoreGroupForm locateForm = baseForm.getLocateStoreGroupForm();
        if (locateForm != null) {
            HttpSession session = request.getSession();
            GroupDataVector groups = locateForm.getGroupsToReturn();
            org.apache.commons.beanutils.BeanUtils.setProperty(
                session.getAttribute(locateForm.getName()), locateForm.getProperty(), groups);
            locateForm.setLocateStoreGroupFl(false);
        }
        return new ActionErrors();
    }

    protected void finalize() throws Throwable {
        super.finalize();
    }

}
