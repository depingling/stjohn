package com.cleanwise.view.logic;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.*;
import com.cleanwise.view.forms.StoreGroupForm;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.ClwComparatorFactory;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.DisplayListSort;
import com.cleanwise.view.utils.SelectableObjects;
import com.cleanwise.view.utils.SessionTool;

/**
 * @author Evgeny Vlasov
 * Date: 12/04/2006
 * Code: StoreGroupMgrLogic implements the logic needed to manipulate
 * groups in storeportal.
 */
public class StoreGroupMgrLogic {

    private static final Logger log = Logger.getLogger(StoreGroupMgrLogic.class);

    private static String className = "StoreGroupMgrLogic";

    /**
     * Reset a Group Id  for the menu visualization
     * @param request - request for getting the session
     */
    public static void resetForm(HttpServletRequest request){

        HttpSession session = request.getSession();

        if (session.getAttribute("STORE_GROUP_FORM") != null) {
            StoreGroupForm grForm = (StoreGroupForm) session.getAttribute("STORE_GROUP_FORM");
            GroupData groupData = grForm.getGroupData();
            if(groupData!=null)
            {
                groupData.setGroupId(0);
                groupData.setShortDesc("");
                grForm.setGroupData(groupData);

            }
//            grForm.setConfigResults(null);
        }

    }
    /**
     * Initializes any request or session values needed for the group manager functions
     */
    public static void init(HttpServletResponse response,
                            HttpServletRequest request, ActionForm pForm) {

        try {

            HttpSession session = request.getSession();
            APIAccess factory = new APIAccess();
            ListService listEJB = factory.getListServiceAPI();
            Group groupEJB = factory.getGroupAPI();
            Report reportEJB = factory.getReportAPI();
            CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

            //Sets up the user groups list
            Map userGroups = groupEJB.getUserGroups();
            session.setAttribute("Users.groups.map", userGroups);

            // Set up the user types list.
            if (session.getAttribute("group.intrinsic.name.vector") == null) {
                RefCdDataVector rfdv = listEJB.getRefCodesCollection("USER_TYPE_CD");
                //add any other group types here
                RefCdData ref = RefCdData.createValue();
                ref.setRefCd("EVERYONE");
                ref.setValue("EVERYONE");
                rfdv.add(ref);
                session.setAttribute("group.intrinsic.name.vector", rfdv);
            }
            if (session.getAttribute("group.type.vector") == null) {
                RefCdDataVector rfdv = listEJB.getRefCodesCollection("GROUP_TYPE_CD");
                rfdv = correctGroupTypeVectorByUserType(rfdv, appUser);
                session.setAttribute("group.type.vector", rfdv);
            }
            if (session.getAttribute("group.status.vector") == null) {
                RefCdDataVector rfdv = listEJB.getRefCodesCollection("GROUP_STATUS_CD");
                session.setAttribute("group.status.vector", rfdv);
            }
            if (session.getAttribute("group.status.vector") == null) {
                RefCdDataVector rfdv = listEJB.getRefCodesCollection("GROUP_STATUS_CD");
                session.setAttribute("group.status.vector", rfdv);
            }
            if (session.getAttribute("Generic.Report.name.vector") == null) {

                List reportNames = new ArrayList();

                if(!RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.getUser().getUserTypeCd()))
                {
                    reportNames.addAll(reportEJB.getAllReportNamesList(appUser.getUserId(),appUser.getUser().getUserTypeCd()));
                }else{
                    reportNames.addAll(reportEJB.getGenericReportNames(null));
                }

                session.setAttribute("Generic.Report.name.vector", reportNames);
            }
            if (session.getAttribute("Application.Functions.name.vector") == null) {

                //get all functions
                RefCdDataVector rfdv = new RefCdDataVector();

                //get all functions for current user  and remove if it is not authorized
                if(!RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.getUser().getUserTypeCd()))
                {
                    rfdv.addAll( factory.getUserAPI().getAuthorizedFunctions(appUser.getUserId(),appUser.getUser().getUserTypeCd()));
                }else{
                    rfdv.addAll(listEJB.getRefCodesCollection("APPLICATION_FUNCTIONS"));
                }

                if(rfdv.size()>0){
                    DisplayListSort.sort(rfdv, "value");
                }

                session.setAttribute("Application.Functions.name.vector", rfdv);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds or updates the groupData object based off the data in the form object
     */
    public static ActionErrors addUpdateGroup(HttpServletResponse response,
                                              HttpServletRequest request, ActionForm pForm) {

        ActionErrors errors = new ActionErrors();
        try {
            HttpSession session = request.getSession();
            APIAccess factory = new APIAccess();
            Group groupEJB = factory.getGroupAPI();
            StoreGroupForm sForm = (StoreGroupForm) pForm;
            CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

            if (appUser==null )
            {
                errors.add("error", new ActionError("error.simpleGenericError", "There isn't info about users in system"));
                return errors;
            }
            if (!Utility.isSet(sForm.getGroupData().getGroupTypeCd())) {
                errors.add("goupData.groupTypeCd", new ActionError("variable.empty.error", "Group Type"));
            }

            if (!Utility.isSet(sForm.getGroupData().getShortDesc()) && !Utility.isSet(sForm.getGroupNameSelect())) {
                errors.add("groupNameSelect", new ActionError("variable.empty.error", "Group Name"));
            }
            if (!Utility.isSet(sForm.getGroupData().getGroupStatusCd())) {
                errors.add("goupData.groupStatusCd", new ActionError("variable.empty.error", "Group Status"));
            }
            if (Utility.isSet(sForm.getGroupData().getShortDesc()) && (Utility.isSet(sForm.getGroupNameSelect()))) {
                if (!sForm.getGroupData().getShortDesc().equals(sForm.getGroupNameSelect())) {
                    errors.add("groupNameSelect", new ActionError("group.name_and_selected"));
                }
            }
            if (errors.size() > 0) {
                return errors;
            }

            if (Utility.isSet(sForm.getGroupNameSelect())) {
                sForm.getGroupData().setShortDesc(sForm.getGroupNameSelect());
            }

            IdVector storeIdsOfGroupAssoc = new IdVector();
            if(RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.getUser().getUserTypeCd()))
            {
                if(sForm.getAssignGroupStoreAssocFl())
                {
                    storeIdsOfGroupAssoc.add(new Integer(appUser.getUserStore().getStoreId()));
                }
            }
            else
            {
                storeIdsOfGroupAssoc.add(new Integer(appUser.getUserStore().getStoreId()));
            }

            int groupId;
            groupId = groupEJB.addGroup(sForm.getGroupData(), (String) session.getAttribute(Constants.USER_NAME));

            if ((RefCodeNames.GROUP_TYPE_CD.STORE_UI.equals(sForm.getGroupData().getGroupTypeCd()) ||
                    RefCodeNames.GROUP_TYPE_CD.ACCOUNT_UI.equals(sForm.getGroupData().getGroupTypeCd()) ||
                    RefCodeNames.GROUP_TYPE_CD.USER_UI.equals(sForm.getGroupData().getGroupTypeCd())) && groupId > 0) {
                createUiForGroup(groupId, sForm.getGroupData().getShortDesc(), appUser.getUserName());
            }

            //Add store to group
            if (storeIdsOfGroupAssoc.size() > 0 && groupId > 0) {
                groupEJB.addStoresToGroup(groupId, storeIdsOfGroupAssoc, appUser.getUser().getUserName());
            }

            sForm.getGroupData().setGroupId(groupId);

            session.setAttribute("groupId", Integer.toString(groupId));
            //STJ-3921
            sForm.setShowGropTypeFl(false);
        } catch (Exception e) {
            e.printStackTrace();
            errors.add("StoreGroupMgrLogic", new ActionError("error.simpleGenericError", e.getMessage()));
        }
        return errors;
    }

    /**
     * Updates the special properties that only user groups have (reports and functions)
     */
    public static ActionErrors updateSupplementaryUserGroup(HttpServletRequest request, ActionForm pForm, boolean isReportOfGroup) {
        ActionErrors errors = new ActionErrors();
        try {
            HttpSession session = request.getSession();
            APIAccess factory = new APIAccess();
            Group groupEJB = factory.getGroupAPI();
            StoreGroupForm sForm = (StoreGroupForm) pForm;

            int groupId = sForm.getGroupData().getGroupId();

            if (groupId == 0) {
                errors.add("StoreGroupMgrLogic", new ActionError("error.genericError", "Group does not exist yet (group id = 0)"));
                return errors;
            }

            if (isReportOfGroup){
	            String[] reports = sForm.getReports();
	            List rl = new ArrayList();
	            if (reports != null) {
	                for (int i = 0; i < reports.length; i++) {
	                    //don't add the -none- value (admin.none)
	                    if (reports[i].length() > 0) {
	                        rl.add(reports[i]);
	                    }
	                }
	            }
	            groupEJB.updateReportToGroupAssociations(rl, groupId, (String) session.getAttribute(Constants.USER_NAME));
            }else{

	            String[] functions = sForm.getApplicationFunctions();
	            List afl = new ArrayList();
	            if (functions != null) {
	                for (int i = 0; i < functions.length; i++) {
	                    //don't add the -none- value (admin.none)
	                    if (functions[i].length() > 0) {
	                        afl.add(functions[i]);
	                    }
	                }
	            }

	            groupEJB.updateApplicationFunctionToGroupAssociations(afl, groupId, (String) session.getAttribute(Constants.USER_NAME));
            }
        } catch (Exception e) {
            e.printStackTrace();
            errors.add("StoreGroupMgrLogic", new ActionError("error.genericError", e.getMessage()));
        }
        return errors;
    }

    /**
     * Removes the specified group from the list of valid groups.  To the user this appears as if
     * the group has been removed, no further guarantee is made as to the status of the group.
     */
    public static ActionErrors removeGroup(HttpServletResponse response,
                                           HttpServletRequest request, ActionForm pForm) {

        ActionErrors errors = new ActionErrors();
        try {
            HttpSession session = request.getSession();
            APIAccess factory = new APIAccess();
            Group groupEJB = factory.getGroupAPI();
            StoreGroupForm sForm = (StoreGroupForm) pForm;

            if (!Utility.isSet(sForm.getRemoveGroupIdS())) {
                errors.add("StoreGroupMgrLogic", new ActionError("variable.empty.error", "Group Name To Remove"));
            }

            int groupIdToRemove = 0;

            try {
                groupIdToRemove = Integer.parseInt(sForm.getRemoveGroupIdS());
            } catch (NumberFormatException e) {
                errors.add("StoreGroupMgrLogic", new ActionError("error.invalidNumber", "Group Name To Remove"));
            }

            if (errors.size() > 0) {
                return errors;
            }

            groupEJB.removeGroup(groupIdToRemove, (String) session.getAttribute(Constants.USER_NAME));

        } catch (Exception e) {
            e.printStackTrace();
            errors.add("StoreGroupMgrLogic", new ActionError("error.genericError", e.getMessage()));
        }
        return errors;
    }

    /**
     * Fetches all Groups and puts them into a session attribute named: Groups.found.vector
     */
    public static ActionErrors viewAll(HttpServletResponse response,
                                       HttpServletRequest request, ActionForm pForm) {

        ActionErrors errors = new ActionErrors();
        try {

            HttpSession session = request.getSession();
            APIAccess factory = new APIAccess();
            Group groupEJB = factory.getGroupAPI();
            GroupDataVector gdv = groupEJB.getAllGroups();

            setStoreInfoOfGroup(factory, (StoreGroupForm)pForm, gdv);

            session.setAttribute("Groups.found.vector", gdv);

        } catch (Exception e) {
            e.printStackTrace();
            errors.add("StoreGroupMgrLogic", new ActionError("error.genericError", e.getMessage()));

        }

        return errors;
    }

    /**
     * Fetches all Groups that match the criteria specified in the form object
     * and puts them into a session attribute named: Groups.found.vector
     */
    public static ActionErrors search(HttpServletResponse response,
                                      HttpServletRequest request, ActionForm form) {

        ActionErrors errors = new ActionErrors();

        StoreGroupForm sForm = (StoreGroupForm) form;
        boolean critSet = false;

        log.info("inside search() method");
        
        if (Utility.isSet(sForm.getGroupName())) {
            critSet = true;
        }

        if (Utility.isSet(sForm.getGroupType())) {
            critSet = true;
        }

        try {

            HttpSession session = request.getSession();
            APIAccess factory = new APIAccess();
            Group groupEJB = factory.getGroupAPI();
            GroupSearchCriteriaView gcrit;

            gcrit = GroupSearchCriteriaView.createValue();
            gcrit.setGroupName(sForm.getGroupName());
            String groupType = sForm.getGroupType();
            gcrit.setGroupType(groupType);

            //add rules for searching groups only in the current store
            CleanwiseUser appUser = null;
            try{

                appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

                if(!RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.getUser().getUserTypeCd())){

                    int storId = appUser.getUserStore().getStoreId();
                    gcrit.setStoreId(storId);
                    gcrit.setUserName(appUser.getUserName());

                }

            }catch (NullPointerException e){
                error("User wasn't added to session", e);
            }

            if(sForm.getShowGroupInactiveFl()){

            }else{
                gcrit.setGroupStatus(RefCodeNames.GROUP_STATUS_CD.ACTIVE);
            }

            GroupDataVector gdv = new GroupDataVector();

            // check group type selected
            if (!groupTypeToHide(appUser, groupType)) {
                gdv = groupEJB.getGroups(gcrit, Group.NAME_CONTAINS_IGNORE_CASE, Group.ORDER_BY_NAME);
            }
            setStoreInfoOfGroup(factory, (StoreGroupForm)form, gdv);

            session.setAttribute("Groups.found.vector", gdv);

        } catch (Exception e) {
            e.printStackTrace();
            errors.add("StoreGroupMgrLogic", new ActionError("error.genericError", e.getMessage()));
        }

        return errors;
    }

    /**
     * Iniitilizes the form to process a new group.  Sets default values and creates the
     * group object.
     */
    public static ActionErrors initEmptyGroup(HttpServletResponse response,
                                              HttpServletRequest request, ActionForm pForm) {
        ActionErrors errors = new ActionErrors();

        try {
            StoreGroupForm sForm = (StoreGroupForm) pForm;
            resetFormForDifferentGroup(sForm);

            GroupData g = GroupData.createValue();
            g.setGroupStatusCd(RefCodeNames.GROUP_STATUS_CD.ACTIVE);
            sForm.setGroupData(g);
            sForm.setReports(new String[0]);
            sForm.setApplicationFunctions(new String[0]);
            sForm.setGroupNameSelect(null);
            //STJ-3921
            sForm.setShowGropTypeFl(true);
        } catch (Exception e) {
            e.printStackTrace();
            errors.add("StoreGroupMgrLogic", new ActionError("error.genericError", e.getMessage()));
        }
        return errors;
    }


    /*
     *Called if the user has searched for a new group and the form needs to be reset in any way
     */
    private static void resetFormForDifferentGroup(StoreGroupForm sForm) {
//        sForm.setConfigResults(null);
        sForm.setConfigResultsType(null);
        sForm.setConfigSearchField(null);
        sForm.setConfigSearchType("nameBegins");
        sForm.setConfigType(null);
    }

    /**
     * Fetches the detail and populates the form based off the request parameter "groupId"
     */
    public static ActionErrors getGroupDetail(HttpServletResponse response,
                                              HttpServletRequest request, ActionForm pForm) {
        ActionErrors errors = new ActionErrors();

        try {

            StoreGroupForm sForm = (StoreGroupForm) pForm;
            HttpSession session = request.getSession();
            APIAccess factory = new APIAccess();
            Group groupEJB = factory.getGroupAPI();
            int groupId;
            String groupIdS = null;

            log.info("inside getGroupDetail method");
            
            try {

                groupIdS = request.getParameter("groupId");
                if (!Utility.isSet(groupIdS)) {
                    groupIdS = (String) session.getAttribute("groupId");
                }

                groupId = Integer.parseInt(groupIdS);
                if (sForm.getGroupData() == null || sForm.getGroupData().getGroupId() != groupId) {
                    resetFormForDifferentGroup(sForm);
                }

                session.setAttribute("groupId", groupIdS);

            } catch (RuntimeException e) {
                e.printStackTrace();
                initEmptyGroup(response, request, pForm);
                errors.add("StoreGroupMgrLogic", new ActionError("error.invalidNumber", "group id (" + groupIdS + ")"));
                return errors;
            }

            GroupData grp = null;
            grp = groupEJB.getGroupDetail(groupId);

            sForm.setGroupData(grp);
            sForm.setGroupType(grp.getGroupTypeCd());

        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            errors.add("StoreGroupMgrLogic", new ActionError("error.genericError", e.getMessage()));
        }

        return errors;
    }
    /**
     * Handles an update request when configuring groups (groupConfig screen).
     */
    public static ActionErrors updateGroupConfig(HttpServletRequest request, ActionForm pForm) {
        ActionErrors errors = new ActionErrors();
        try {
            StoreGroupForm sForm = (StoreGroupForm) pForm;
            HttpSession session = request.getSession();
            APIAccess factory = new APIAccess();
            Group groupEJB = factory.getGroupAPI();
            CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
            int groupId = sForm.getGroupData().getGroupId();
            if (groupId == 0) {
                errors.add("StoreGroupMgrLogic", new ActionError("error.genericError", "Group does not exist yet (group id = 0)"));
                return errors;
            }

            if (sForm.getConfigResults() != null) {
                List newlySelected = sForm.getConfigResults().getNewlySelected();
                List newlyDeSelected = sForm.getConfigResults().getDeselected();
                IdVector newlySelectedIdV = Utility.toIdVector(newlySelected);
                IdVector newlyDeSelectedIdV = Utility.toIdVector(newlyDeSelected);
                if(RefCodeNames.GROUP_TYPE_CD.USER.equals(sForm.getGroupData().getGroupTypeCd()) &&
                        RefCodeNames.GROUP_TYPE_CD.ACCOUNT.equals(sForm.getConfigType()) ){
                	groupEJB.removeAccountsFromGroup(groupId, newlyDeSelectedIdV);
                	groupEJB.addAccountsToGroup(groupId, newlySelectedIdV, appUser.getUser().getUserName());
                }
                else if (RefCodeNames.GROUP_TYPE_CD.USER.equals(sForm.getConfigResultsType()) ||
                		RefCodeNames.GROUP_TYPE_CD.USER_UI.equals(sForm.getConfigResultsType())) {
                    groupEJB.removeUsersFromGroup(groupId, newlyDeSelectedIdV);
                    groupEJB.addUsersToGroup(groupId, newlySelectedIdV, appUser.getUserName());
                }
                else {
                    groupEJB.removeBusEntitiesFromGroup(groupId, newlyDeSelectedIdV);
                    if (RefCodeNames.GROUP_TYPE_CD.STORE_UI.equals(sForm.getGroupData().getGroupTypeCd())) {
                        /// Calling the specific method to add the entity into 'store ui'-group
                        groupEJB.addBusEntitiesToStoreUiGroup(groupId, newlySelectedIdV, appUser.getUserName());
                    } else if(RefCodeNames.GROUP_TYPE_CD.ACCOUNT_UI.equals(sForm.getGroupData().getGroupTypeCd())) {
                        /// Calling the specific method to add the entity into 'account ui'-group
                        groupEJB.addBusEntitiesToAccountUiGroup(groupId, newlySelectedIdV, appUser.getUserName());
                    }
                    else {
                        groupEJB.addBusEntitiesToGroup(groupId, newlySelectedIdV, appUser.getUserName());
                    }
                }
                sForm.getConfigResults().resetState();
            }
        } catch (Exception e) {
            e.printStackTrace();
            errors.add("StoreGroupMgrLogic", new ActionError("error.simpleGenericError", e.getMessage()));
        }
        return errors;
    }

    /**
     * Preforms the searching necessary when configuring a groups relationships to various different entities
     */
    public static ActionErrors searchGroupConfig(HttpServletRequest request, ActionForm pForm) {
        ActionErrors errors = new ActionErrors();

        try {
            HttpSession session = request.getSession();
            CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
            StoreGroupForm sForm = (StoreGroupForm) pForm;
            APIAccess factory = new APIAccess();
            Group groupEJB = factory.getGroupAPI();
            User userEJB = factory.getUserAPI();
            int groupId = sForm.getGroupData().getGroupId();

            if (groupId == 0) {
                errors.add("StoreGroupMgrLogic", new ActionError("error.genericError", "Group does not exist yet (group id = 0)"));
                return errors;
            }

            List options = new DistributorDataVector();
            List existing;

            Comparator comp = null;
            String fieldValue = sForm.getConfigSearchField();
            String fieldSearchType = sForm.getConfigSearchType();

            if (!Utility.isSet(fieldSearchType)) {
                fieldSearchType = "nameContains";
            }

            IdVector storeIds = null;

            try {

                if (Utility.isSet(sForm.getSearchStoreId())) {
                    Integer storeId = new Integer(sForm.getSearchStoreId());
                    storeIds = new IdVector();
                    storeIds.add(storeId);
                }

            } catch (NumberFormatException e) {
                errors.add("storeId", new ActionError("error.invalidNumber", "Store"));
                return errors;
            }

            if (RefCodeNames.GROUP_TYPE_CD.USER.equals(sForm.getConfigType()) || RefCodeNames.GROUP_TYPE_CD.USER_UI.equals(sForm.getConfigType())) {
                GroupSearchCriteriaView gsc = GroupSearchCriteriaView.createValue();
                gsc.setGroupId(groupId);

                String firstUserName = sForm.getFirstUserName();
                String lastUserName = sForm.getLastUserName();
                IdVector accountIds = new IdVector();

                if (sForm.getAccountFilter() != null) {
                    AccountDataVector accountDataVector = sForm.getAccountFilter();
                    for (Iterator iter = accountDataVector.iterator(); iter.hasNext();) {
                        AccountData accountData = (AccountData) iter.next();
                        accountIds.add(new Integer(accountData.getBusEntity().getBusEntityId()));
                    }
                }
                existing = groupEJB.getUsersForGroup(gsc, Group.NAME_EXACT, Group.ORDER_BY_NAME);                
                //get all users which have the code equals to the name of group
                List<UserData> defaultUsers = null;

                defaultUsers = UserMgrLogic.search(request, fieldValue, fieldSearchType, 0, firstUserName,
                    lastUserName, sForm.getGroupData().getShortDesc(), storeIds, accountIds);
                existing.addAll(defaultUsers);
                //log.info("searchGroupConfig().existing 1 size = " + existing.size());
                //log.info("searchGroupConfig().existing 1 = " + existing);

                UserSearchCriteriaData searchCriteria = UserSearchCriteriaData.createValue();
                searchCriteria.setUserStoreIds(Utility.toIdVector(appUser.getAssociatedStores()));
                searchCriteria.setUserIds(Utility.toIdVector(existing));
                searchCriteria.setAccountIds(accountIds);

                //existing = userEJB.getUsersCollectionByCriteria(searchCriteria);
                
                //log.info("searchGroupConfig().existing 1.A size = " + existing.size()); 
                //log.info("searchGroupConfig().existing 1.A = " + existing);
                
                
                // Bug # 5082:modified to display  'Show Configured Only' users
                
                if(sForm.getShowConfiguredOnlyFl()){
                	if(searchCriteria.getUserIds()!=null && searchCriteria.getUserIds().size()>0) {
                		log.info("show configured only users");
                		existing = userEJB.getUsersCollectionByCriteriaMod(searchCriteria);
                		
                        //log.info("searchGroupConfig().existing 1.1 = " + existing);    
                	}                	
                    options.addAll(existing); 
                    
                    //log.info("searchGroupConfig().existing 1.B size = " + existing.size());    
                    //log.info("searchGroupConfig().existing 1.B = " + existing);    
                } else{
                    log.info("show all users");
                	//existing = userEJB.getUsersCollectionByCriteria(searchCriteria);
                	existing = userEJB.getUsersCollectionByCriteriaMod(searchCriteria);
                	
                    //log.info("searchGroupConfig().existing 1.C size = " + existing.size()); 
                    //log.info("searchGroupConfig().existing 1.C = " + existing); 
                	
                    options = UserMgrLogic.searchMod(request, fieldValue, fieldSearchType, 0, firstUserName,
                        lastUserName, null, storeIds, accountIds);
                    //log.info("options size = " + options.size());
                    //log.info("options = " + options);
                }
                //log.info("searchGroupConfig().existing 2 size = " + existing.size());
                //log.info("searchGroupConfig().existing 2 = " + existing);
                
                comp = USER_EQUALITY_COMPARE;
                existing = searchUserGroups(existing, fieldValue, fieldSearchType, firstUserName, lastUserName);
            
                //log.info("searchGroupConfig().existing 3 size = " + existing.size());
                //log.info("searchGroupConfig().existing 3 = " + existing);
            
            }else if (RefCodeNames.GROUP_TYPE_CD.ACCOUNT.equals(sForm.getConfigType())
            		|| RefCodeNames.GROUP_TYPE_CD.ACCOUNT_UI.equals(sForm.getConfigType())) {
            	if(RefCodeNames.GROUP_TYPE_CD.USER.equals(sForm.getGroupData().getGroupTypeCd())){
            		existing = factory.getGroupAPI().getAccountsForGroup(groupId);
                }else{
					existing = groupEJB.getBusEntitysForGroup(groupId,RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT);
                }
                if(sForm.getShowConfiguredOnlyFl()){
						for (int i = 0; i < existing.size(); i++) {
							BusEntityData busEntity = (BusEntityData) existing.get(i);
							options.add(factory.getAccountAPI().getAccountDetails(busEntity));
						}
                } else{
                    options = searchAccounts(request,sForm.getSearchStoreId(), fieldValue,fieldSearchType);
                }
                if(appUser.isaSystemAdmin()){
                	existing = searchEntityGroups(existing,fieldValue, fieldSearchType, sForm.getSearchStoreId());
                }else {
                	existing = searchEntityGroups(existing,fieldValue, fieldSearchType, null);
                }
            } else if (RefCodeNames.GROUP_TYPE_CD.DISTRIBUTOR.equals(sForm.getConfigType())) {

                BusEntityDataVector busEntitys;
                busEntitys = groupEJB.getBusEntitysForGroup(groupId, RefCodeNames.BUS_ENTITY_TYPE_CD.DISTRIBUTOR);
                existing = busEntitys;
                if(sForm.getShowConfiguredOnlyFl()){
                    options.addAll(factory.getDistributorAPI().getDistributorsByBusEntityCollection(busEntitys));
                }else{
                    options = DistMgrLogic.search(request, fieldValue, fieldSearchType, null, null, null, null, null);
                }
                existing = searchEntityGroups(existing,fieldValue, fieldSearchType, null);
//            } else if (RefCodeNames.GROUP_TYPE_CD.SITE.equals(sForm.getConfigType())) {
//
//                existing = groupEJB.getBusEntitysForGroup(groupId, RefCodeNames.BUS_ENTITY_TYPE_CD.SITE);
//
//                if(sForm.getShowConfiguredOnlyFl()){
//                    IdVector ids = new IdVector();
//                    for (int i = 0; i < existing.size(); i++) {
//                        BusEntityData busEntity = (BusEntityData) existing.get(i);
//                        ids.add(Integer.getInteger(String.valueOf(busEntity.getBusEntityId())));
//                    }
//                    int oneOfSiteId =(ids!= null)? ((Integer)ids.get(0)).intValue() : 0 ;
//                    options.addAll(factory.getSiteAPI().getSiteCollection(ids, SessionTool.getCategoryToCostCenterView(session, oneOfSiteId )));
//
//                }else{
//                    options = SiteMgrLogic.search(request, fieldValue, fieldSearchType, null, null, null, null, null);
//                }
//                existing = searchEntityGroups(existing,fieldValue, fieldSearchType);
            } else if (RefCodeNames.GROUP_TYPE_CD.MANUFACTURER.equals(sForm.getConfigType())) {

                existing = groupEJB.getBusEntitysForGroup(groupId, RefCodeNames.BUS_ENTITY_TYPE_CD.MANUFACTURER);

                if(sForm.getShowConfiguredOnlyFl()){

                    for (int i = 0; i < existing.size(); i++) {
                        BusEntityData busEntity = (BusEntityData) existing.get(i);
                        options.add(factory.getManufacturerAPI().getManufacturer(busEntity.getBusEntityId()));
                    }

                } else{
                    options = ManufMgrLogic.search(request, fieldValue, fieldSearchType);
                }
                existing = searchEntityGroups(existing,fieldValue, fieldSearchType, null);
            } else if (RefCodeNames.GROUP_TYPE_CD.STORE.equals(sForm.getConfigType()) || RefCodeNames.GROUP_TYPE_CD.STORE_UI.equals(sForm.getConfigType())) {

                existing = groupEJB.getBusEntitysForGroup(groupId, RefCodeNames.BUS_ENTITY_TYPE_CD.STORE);

                if(sForm.getShowConfiguredOnlyFl()){
                    for (int i = 0; i < existing.size(); i++) {
                        BusEntityData busEntity = (BusEntityData) existing.get(i);
                        options.add(factory.getStoreAPI().getStore(busEntity.getBusEntityId()));
                    }
                } else {
                    options = StoreMgrLogic.search(request, fieldValue, fieldSearchType, null);
                }
                if (options != null && storeIds != null && storeIds.size() > 0 ) {
                    Iterator it = options.iterator();
                    while (it.hasNext() == true) {
                        StoreData sd = (StoreData) it.next();
                        if (storeIds.contains(sd.getStoreId()) == false) {
                            it.remove();
                        }
                    }
                }
                existing = searchEntityGroups(existing,fieldValue, fieldSearchType, null);
            } else {
                throw new IllegalStateException(sForm.getConfigType() + " not a valid config type");
            }

            //default comparator to use
            if (comp == null) {
                comp = ClwComparatorFactory.getBusEntityComparator();
            }


            SelectableObjects results = new SelectableObjects(existing, options, comp, sForm.getShowConfiguredOnlyFl());
                        
            sForm.setConfigResults(results);

            sForm.setConfigResultsType(sForm.getConfigType());

        } catch (Exception e) {
            e.printStackTrace();
            errors.add("StoreGroupMgrLogic", new ActionError("error.genericError", e.getMessage()));
        }
        return errors;
    }

    private static AccountDataVector searchAccounts(HttpServletRequest request,
                                                    String pFieldSearchStoreId,
                                                    String pFieldSearchValue,
                                                    String pFieldSearchType) throws Exception {

        log.info("searchAccounts => BEGIN");

        // Get a reference to the admin facade
        HttpSession session = request.getSession();

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        APIAccess factory = new APIAccess();
        Account acctEjb = factory.getAccountAPI();

        AccountDataVector dv = new AccountDataVector();
        BusEntitySearchCriteria crit = new BusEntitySearchCriteria();
        crit.setResultLimit(Constants.MAX_ACCOUNTS_TO_RETURN);

        if (Utility.isSet(pFieldSearchStoreId)) {
            Integer storeId = new Integer(pFieldSearchStoreId);
            if (appUser.isaSystemAdmin()) {
                crit.setStoreBusEntityIds(Utility.toIdVector(storeId));
            } else if (appUser.getUserStoreAsIdVector().contains(storeId)) {
                crit.setStoreBusEntityIds(Utility.toIdVector(storeId));
            } else {
                return dv;
            }
        } else if (!appUser.isaSystemAdmin()) {
            crit.setStoreBusEntityIds(appUser.getUserStoreAsIdVector());
        }

        if (Utility.isSet(pFieldSearchValue)) {
            pFieldSearchValue = pFieldSearchValue.trim();
            if (Constants.ID.equalsIgnoreCase(pFieldSearchType)) {
                crit.setSearchId(pFieldSearchValue);
            } else {
                if (Constants.NAME_BEGINS.equalsIgnoreCase(pFieldSearchType)) {
                    crit.setSearchName(pFieldSearchValue);
                    crit.setSearchNameType(BusEntitySearchCriteria.NAME_STARTS_WITH);
                } else {
                    crit.setSearchName(pFieldSearchValue);
                    crit.setSearchNameType(BusEntitySearchCriteria.NAME_CONTAINS);
                }
            }
        }

        crit.setSearchForInactive(true);

        crit.setOrder(BusEntitySearchCriteria.ORDER_BY_NAME);
        dv = acctEjb.getAccountsByCriteria(crit);

        log.info("searchAccounts => END.Found: " + dv.size() + " accounts");

        return dv;
    }

    private static final Comparator USER_EQUALITY_COMPARE = new Comparator() {
        public int compare(Object o1, Object o2) {
            int id1 = ((UserData) o1).getUserId();
            int id2 = ((UserData) o2).getUserId();
            return id1 - id2;
        }
    };

    /**
     * Method sets store information for the group
     * @param pFactory
     * @param sForm
     * @param gdv
     * @throws Exception
     */
    public static void setStoreInfoOfGroup(APIAccess pFactory, StoreGroupForm sForm, GroupDataVector gdv)
            throws Exception {

        if(gdv!=null && gdv.size()>0)  {

            Group groupEJB = pFactory.getGroupAPI();
            Iterator it    = gdv.iterator();

            while(it.hasNext())
            {
                GroupData gd = (GroupData)it.next();
                BusEntityDataVector busEntitysDV = groupEJB.getStoresForGroup(gd.getGroupId(), RefCodeNames.BUS_ENTITY_TYPE_CD.STORE);

                if((busEntitysDV!=null)&&(busEntitysDV.size()>0))
                {
                    Iterator it2        = busEntitysDV.iterator();
                    String strId        = "";
                    String strShortDesc = "";
                    int i = 0;

                    while(it2.hasNext()){

                        BusEntityData bed = (BusEntityData) it2.next();

                        if(i!=0){
                            strId = strId.concat("<br/>");
                            strShortDesc = strShortDesc.concat("<br/>");
                        }

                        strId        = strId.concat(String.valueOf(bed.getBusEntityId()));
                        strShortDesc = strShortDesc.concat(bed.getShortDesc());
                        i++;
                    }

                    if(sForm!=null && sForm.getStoreInfoOfGroup()!=null){
                        sForm.getStoreInfoOfGroup().put(new Integer(gd.getGroupId()), new PairView(strId, strShortDesc));
                    }
                }
            }
        }
    }

    public static ActionErrors initGroupConfig(HttpServletRequest request, ActionForm form) {
        ActionErrors errors  = new ActionErrors();
        StoreGroupForm sForm = (StoreGroupForm) form;

        if(sForm!=null)
        {
                sForm.setConfigResults(null);
                sForm.setConfigType(null);
        }
        if(sForm.getGroupType().equals(RefCodeNames.GROUP_TYPE_CD.USER)){
        	sForm.setConfigType(RefCodeNames.GROUP_TYPE_CD.USER);
        }
        return errors;
    }

    public static ActionErrors getFunctionsOrReports(HttpServletRequest request, ActionForm form, boolean isReportOfGroup) {

        ActionErrors errors = new ActionErrors();

        try {

            APIAccess factory = new APIAccess();
            Group groupEJB = factory.getGroupAPI();
            StoreGroupForm sForm = (StoreGroupForm) form;

            if (sForm != null &&sForm.getGroupData()!=null) {
                int groupId = sForm.getGroupData().getGroupId();
                Map associations = groupEJB.getAllValidGroupAssociations(groupId);
                if (isReportOfGroup){
	                Set reports = (Set) associations.get(RefCodeNames.GROUP_ASSOC_CD.REPORT_OF_GROUP);
	                if (reports == null) {
	                    sForm.setReports(new String[0]);
	                } else {
	                    String[] lReportNames = new String[reports.size()];
	                    Iterator it = reports.iterator();
	                    int i = 0;

	                    while (it.hasNext()) {
	                        lReportNames[i] = ((GenericReportData) it.next()).getName();
	                        i++;
	                    }
	                    sForm.setReports(lReportNames);
	                }
                }else{

	                //get application functions
	                Set functions = (Set) associations.get(RefCodeNames.GROUP_ASSOC_CD.FUNCTION_OF_GROUP);

	                if (functions == null) {
	                    sForm.setApplicationFunctions(new String[0]);
	                } else {
	                    String[] lFunctionNames = new String[functions.size()];
	                    Iterator it = functions.iterator();
	                    int i = 0;
	                    while (it.hasNext()) {
	                        lFunctionNames[i] = (String) it.next();
	                        i++;
	                    }
	                    sForm.setApplicationFunctions(lFunctionNames);
	                }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            errors.add("StoreGroupMgrLogic", new ActionError("error.genericError", e.getMessage()));
        }
        return errors;
    }



    /**
     * Error logging
     * @param message - message which will be pasted to log
     * @param e       - Excepiton
     */
    private static void error(String message, Exception e){

        String errorMessage;
        StringWriter wr = new StringWriter();
        PrintWriter prW = new PrintWriter(wr);
        e.printStackTrace(prW);
        errorMessage = wr.getBuffer().toString();

        log.info("ERROR in " + className + " :: " + errorMessage);
    }

    private static RefCdDataVector correctGroupTypeVectorByUserType(RefCdDataVector pRfcdV, CleanwiseUser pAppUser) {
        if (pRfcdV == null && pAppUser == null) {
            return pRfcdV;
        }
        RefCdDataVector resV = new RefCdDataVector();
        Iterator i = pRfcdV.iterator();
        while (i.hasNext()) {
            RefCdData refcdD = (RefCdData)i.next();
            if (!groupTypeToHide(pAppUser, refcdD.getShortDesc())) {
                resV.add(refcdD);
            }
        }
        return resV;
    }

    private static void createUiForGroup(int pGroupId, String pGroupName, String pUserName) throws Exception {

        Ui uiEjb = APIAccess.getAPIAccess().getUiAPI();

        UiView uiView = null;
        try {
            uiView = uiEjb.getUiForGroup(pGroupId);
        } catch (DataNotFoundException e) {
        }

        if (uiView == null) {

            uiView = UiView.createValue();

            uiView.setUiData(UiData.createValue());
            uiView.getUiData().setShortDesc(pGroupName);
            uiView.getUiData().setStatusCd(RefCodeNames.UI_STATUS_CD.ACTIVE);

            uiView.setUiAssociations(new UiAssocDataVector());
            UiAssocData uiAssoc = UiAssocData.createValue();
            uiAssoc.setGroupId(pGroupId);
            uiView.getUiAssociations().add(uiAssoc);

            uiView = uiEjb.saveUi(uiView, pUserName);

        }
    }

    private static boolean groupTypeToHide(CleanwiseUser pAppUser, String pGroupType) {
        if (pGroupType == null || pAppUser == null) {
            return false;
        }
        String userType = pAppUser.getUser().getUserTypeCd();
        return
            pGroupType.equals(RefCodeNames.GROUP_TYPE_CD.USER_UI) ||
            ( userType.equals(RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR) &&
              (pGroupType.equals(RefCodeNames.GROUP_TYPE_CD.ACCOUNT_UI) ||
               pGroupType.equals(RefCodeNames.GROUP_TYPE_CD.STORE_UI))
            );
    }


    private static final Comparator<? super PairView> USER_GROUP_ID_COMPARE = new Comparator<PairView>() {
        public int compare(PairView o1, PairView o2) {
            int id1 = (Integer) o1.getObject1();
            int id2 = (Integer) o2.getObject1();
            return id1 - id2;
        }
    };

    private static List<UserData> searchUserGroups(List entities, String pFieldValue, String pSearchType, String firstName, String lastName){
    	return searchEntityGroups(entities, pFieldValue, pSearchType, firstName, lastName);
    }

    private static List searchEntityGroups(List entities, String pFieldValue, String pSearchType, String... additional) {

    	boolean additionalIsNull = additional == null;
   		if(!additionalIsNull)
	    	for(String s : additional){
	   			if(!Utility.isSet(s))additionalIsNull = true;
	   		}

    	if(Utility.isSet(pFieldValue.trim()) && !additionalIsNull)
    		return Collections.EMPTY_LIST;
    	else if(!Utility.isSet(pFieldValue) && (additional == null || additional.length == 0))
    		return entities;

        List result = new ArrayList();

		if (entities != null)
				for (Object item : entities) {
					String name = null;

					//only for users
					String field1 = "",
						   field2 = "",
						   searchFirstName = "",
						   searchLastName = "";

					if (item instanceof UserData){
						name = ((UserData) item).getUserName();

						if(((UserData) item).getFirstName() != null)
						field1 = ((UserData) item).getFirstName();//first name

						if(((UserData) item).getLastName() != null)
						field2 = ((UserData) item).getLastName(); //last name

						if(additional[0] != null)
							searchFirstName = additional[0];
						if(additional[1] != null)
							searchLastName = additional[1];
					}else if (item instanceof BusEntityData){
						name = ((BusEntityData) item).getShortDesc();
						if(additional != null && additional.length > 0){
							field1 = String.valueOf(((BusEntityData) item).getBusEntityId());//store id
							searchFirstName = additional[0];
						}
					}
					if (Constants.NAME_BEGINS.equals(pSearchType)){
						if(Utility.isSet(pFieldValue)){
							if(name.toUpperCase().startsWith(pFieldValue.toUpperCase()))
								result.add(item);
						}
						else if(((!Utility.isSet(searchFirstName)
								&& Utility.isSet(searchLastName)) &&
								(field2.toUpperCase().startsWith(searchLastName.toUpperCase()))) ||

								((!Utility.isSet(searchLastName) && Utility.isSet(searchFirstName)) &&
								(field1.toUpperCase().startsWith(searchFirstName.toUpperCase()))) ||

								((field1.toUpperCase().startsWith(searchFirstName.toUpperCase())&&
								field2.toUpperCase().startsWith(searchLastName.toUpperCase()))))

							result.add(item);

					} else if (Constants.NAME_CONTAINS.equals(pSearchType)){
						if(Utility.isSet(pFieldValue)){
							if(name.toUpperCase().contains(pFieldValue.toUpperCase()))
								result.add(item);
						}
						else if(((!Utility.isSet(searchFirstName)
								&& Utility.isSet(searchLastName)) &&
								(field2.toUpperCase().contains(searchLastName.toUpperCase()))) ||

								((!Utility.isSet(searchLastName) && Utility.isSet(searchFirstName)) &&
								(field1.toUpperCase().contains(searchFirstName.toUpperCase()))) ||

								((field1.toUpperCase().contains(searchFirstName.toUpperCase())&&
								field2.toUpperCase().contains(searchLastName.toUpperCase()))))

							result.add(item);
						}
					}

		//log.info("searchEntityGroups().result = " + result);
		
        return result;
    }


}
