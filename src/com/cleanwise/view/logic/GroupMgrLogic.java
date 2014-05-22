/*
 * GroupMgrLogic.java
 *
 * Created on January 16, 2003, 3:23 PM
 */

package com.cleanwise.view.logic;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import com.cleanwise.service.api.*;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.*;
import java.util.Map;
import java.util.List;
import java.util.Set;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Comparator;
import com.cleanwise.view.forms.GroupForm;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.ClwComparatorFactory;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.SelectableObjects;
/**
 *
 * @author  bstevens
 */
public class GroupMgrLogic {
    
    /**
     *Initializes any request or session values needed for the group manager functions
     */
    public static void init(HttpServletResponse response,
    HttpServletRequest request, ActionForm pForm){
        
        try{
            HttpSession session = request.getSession();
            APIAccess factory = new APIAccess();
            ListService listEJB = factory.getListServiceAPI();
            Group groupEJB = factory.getGroupAPI();
            Report reportEJB = factory.getReportAPI();
            
            //Sets up the user groups list
            Map userGroups = groupEJB.getUserGroups();
            session.setAttribute("Users.groups.map",userGroups);
            
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
            if(session.getAttribute("group.type.vector")==null){
                RefCdDataVector rfdv = listEJB.getRefCodesCollection("GROUP_TYPE_CD");
                session.setAttribute("group.type.vector",rfdv);
            }
            if(session.getAttribute("group.status.vector")==null){
                RefCdDataVector rfdv = listEJB.getRefCodesCollection("GROUP_STATUS_CD");
                session.setAttribute("group.status.vector",rfdv);
            }
            if(session.getAttribute("group.status.vector")==null){
                RefCdDataVector rfdv = listEJB.getRefCodesCollection("GROUP_STATUS_CD");
                session.setAttribute("group.status.vector",rfdv);
            }
            if(session.getAttribute("Generic.Report.name.vector")==null){
                List reportNames = reportEJB.getGenericReportNames(null);
                session.setAttribute("Generic.Report.name.vector",reportNames);
            }
            if(session.getAttribute("Application.Functions.name.vector")==null){
                RefCdDataVector rfdv = listEJB.getRefCodesCollection("APPLICATION_FUNCTIONS");
                session.setAttribute("Application.Functions.name.vector",rfdv);
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    /**
     *Adds or updates the groupData object based off the data in the form object
     */
    public static ActionErrors addUpdateGroup(HttpServletResponse response,
    HttpServletRequest request, ActionForm pForm){
        ActionErrors errors = new ActionErrors();
        try{
            HttpSession session = request.getSession();
            APIAccess factory = new APIAccess();
            Group groupEJB = factory.getGroupAPI();
            GroupForm sForm = (GroupForm) pForm;
            if(!Utility.isSet(sForm.getGroupData().getGroupTypeCd())){
                errors.add("goupData.groupTypeCd",new ActionError("variable.empty.error","Group Type"));
            }
            if(!Utility.isSet(sForm.getGroupData().getShortDesc()) && !Utility.isSet(sForm.getGroupNameSelect())){
                errors.add("groupNameSelect",new ActionError("variable.empty.error","Group Name"));
            }
            if(!Utility.isSet(sForm.getGroupData().getGroupStatusCd())){
                errors.add("goupData.groupStatusCd",new ActionError("variable.empty.error","Group Status"));
            }
            if(Utility.isSet(sForm.getGroupData().getShortDesc()) && (Utility.isSet(sForm.getGroupNameSelect()))){
                if(!sForm.getGroupData().getShortDesc().equals(sForm.getGroupNameSelect())){
                    errors.add("groupNameSelect",new ActionError("group.name_and_selected"));
                }
            }
            if(errors.size()>0){
                return errors;
            }
            
            if(Utility.isSet(sForm.getGroupNameSelect())){
                sForm.getGroupData().setShortDesc(sForm.getGroupNameSelect());
            }
            
            int groupId = groupEJB.addGroup(sForm.getGroupData(), (String) session.getAttribute(Constants.USER_NAME));
            sForm.getGroupData().setGroupId(groupId);
            
            
            session.setAttribute("groupId", Integer.toString(groupId));
        }catch(Exception e){
            e.printStackTrace();
            errors.add("GroupMgrLogic",new ActionError("error.simpleGenericError",e.getMessage()));
        }
        return errors;
    }
    
    /**
     *Updates the special properties that only user groups have (reports and functions)
     */
    public static ActionErrors updateSupplementaryUserGroup(HttpServletRequest request, ActionForm pForm){
        ActionErrors errors = new ActionErrors();
        try{
            HttpSession session = request.getSession();
            APIAccess factory = new APIAccess();
            Group groupEJB = factory.getGroupAPI();
            GroupForm sForm = (GroupForm) pForm;
            int groupId = sForm.getGroupData().getGroupId();
            if(groupId == 0){
                errors.add("GroupMgrLogic",new ActionError("error.simpleGenericError","Group does not exist yet (group id = 0)"));
                return errors;
            }
            String[] reports = sForm.getReports();
            List rl =new ArrayList();
            if(reports != null){
                for(int i=0;i<reports.length;i++){
                    //don't add the -none- value (admin.none)
                    if(reports[i].length()>0){
                        rl.add(reports[i]);
                    }
                }
            }
            groupEJB.updateReportToGroupAssociations(rl, groupId,(String) session.getAttribute(Constants.USER_NAME));
            
            String[] functions = sForm.getApplicationFunctions();
            List afl =new ArrayList();
            if(functions != null){
                for(int i=0;i<functions.length;i++){
                    //don't add the -none- value (admin.none)
                    if(functions[i].length()>0){
                        afl.add(functions[i]);
                    }
                }
            }
            
            groupEJB.updateApplicationFunctionToGroupAssociations(afl, groupId,(String) session.getAttribute(Constants.USER_NAME));
        }catch(Exception e){
            e.printStackTrace();
            errors.add("GroupMgrLogic",new ActionError("error.simpleGenericError",e.getMessage()));
        }
        return errors;
    }
    
    /**
     *Removes the specified group from the list of valid groups.  To the user this appears as if
     *the group has been removed, no further guarantee is made as to the status of the group.
     */
    public static ActionErrors removeGroup(HttpServletResponse response,
    HttpServletRequest request, ActionForm pForm){
        
        ActionErrors errors = new ActionErrors();
        try{
            HttpSession session = request.getSession();
            APIAccess factory = new APIAccess();
            Group groupEJB = factory.getGroupAPI();
            GroupForm sForm = (GroupForm) pForm;
            if(!Utility.isSet(sForm.getRemoveGroupIdS())){
                errors.add("GroupMgrLogic",new ActionError("variable.empty.error","Group Name To Remove"));
            }
            int groupIdToRemove = 0;
            try{
                groupIdToRemove = Integer.parseInt(sForm.getRemoveGroupIdS());
            }catch (NumberFormatException e){
                errors.add("GroupMgrLogic",new ActionError("error.invalidNumber","Group Name To Remove"));
            }
            if(errors.size()>0){
                return errors;
            }
            groupEJB.removeGroup(groupIdToRemove, (String) session.getAttribute(Constants.USER_NAME));
        }catch(Exception e){
            e.printStackTrace();
            errors.add("GroupMgrLogic",new ActionError("error.simpleGenericError",e.getMessage()));
        }
        return errors;
    }
    
    /**
     *Fetches all Groups and puts them into a session attribute named: Groups.found.vector
     */
    public static ActionErrors viewAll(HttpServletResponse response,
    HttpServletRequest request, ActionForm pForm){
        ActionErrors errors = new ActionErrors();
        try{
            HttpSession session = request.getSession();
            CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
            int storeId = appUser.getUserStore().getStoreId();
            APIAccess factory = new APIAccess();
            Group groupEJB = factory.getGroupAPI();
            GroupDataVector gdv= groupEJB.getAllGroupsForStore(storeId);
            session.setAttribute("Groups.found.vector",gdv);
        }catch(Exception e){
            e.printStackTrace();
            errors.add("GroupMgrLogic",new ActionError("error.simpleGenericError",e.getMessage()));
            
        }
        return errors;
    }
    
    public static ActionErrors adminGroupViewAll(HttpServletResponse response,
    		HttpServletRequest request, ActionForm pForm){
    	ActionErrors errors = new ActionErrors();
    	try{
    		HttpSession session = request.getSession();
    		CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    		
    		APIAccess factory = new APIAccess();
    		Group groupEJB = factory.getGroupAPI();
    		GroupDataVector gdv= groupEJB.getAllGroups();
    		session.setAttribute("Groups.found.vector",gdv);
    	}catch(Exception e){
    		e.printStackTrace();
    		errors.add("GroupMgrLogic",new ActionError("error.simpleGenericError",e.getMessage()));
    	            
    	}
    	return errors;
    }
    
    /**
     *Fetches all Groups that match the criteria specified in the form object
     *and puts them into a session attribute named: Groups.found.vector
     */
    public static ActionErrors search(HttpServletResponse response,
    HttpServletRequest request, ActionForm pForm){
        ActionErrors errors = new ActionErrors();
        GroupForm sForm = (GroupForm) pForm;
        boolean critSet = false;
        if(Utility.isSet(sForm.getGroupName())){
            critSet = true;
        }
        if(Utility.isSet(sForm.getGroupType())){
            critSet = true;
        }
        if(!critSet){
            errors.add("search",new ActionError("error.invalidSearchCritera"));
        }
        if(errors.size()>0){
            return errors;
        }
        try{
            HttpSession session = request.getSession();
            CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
            int storeId = appUser.getUserStore().getStoreId();
            APIAccess factory = new APIAccess();
            Group groupEJB = factory.getGroupAPI();
            GroupSearchCriteriaView gcrit = GroupSearchCriteriaView.createValue();
            if(storeId>0){
            	gcrit.setStoreId(storeId);
            }
            gcrit.setGroupName(sForm.getGroupName());
            gcrit.setGroupType(sForm.getGroupType());
            GroupDataVector gdv = groupEJB.getGroups(gcrit,Group.NAME_CONTAINS_IGNORE_CASE,Group.ORDER_BY_NAME);
            session.setAttribute("Groups.found.vector",gdv);
        }catch(Exception e){
            e.printStackTrace();
            errors.add("GroupMgrLogic",new ActionError("error.simpleGenericError",e.getMessage()));
        }
        return errors;
    }
    
    public static ActionErrors adminGroupSearch(HttpServletResponse response,
    	    HttpServletRequest request, ActionForm pForm){
    	ActionErrors errors = new ActionErrors();
    	GroupForm sForm = (GroupForm) pForm;
    	boolean critSet = false;
    	if(Utility.isSet(sForm.getGroupName())){
    		critSet = true;
    	}
    	if(Utility.isSet(sForm.getGroupType())){
    		critSet = true;
    	}
    	if(!critSet){
    		errors.add("search",new ActionError("error.invalidSearchCritera"));
    	}
    	if(errors.size()>0){
    		return errors;
    	}
    	try{
    		HttpSession session = request.getSession();
    		CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    	    
    		APIAccess factory = new APIAccess();
    		Group groupEJB = factory.getGroupAPI();
    		GroupSearchCriteriaView gcrit = GroupSearchCriteriaView.createValue();
    	           
    		gcrit.setGroupName(sForm.getGroupName());
    		gcrit.setGroupType(sForm.getGroupType());
    		GroupDataVector gdv = groupEJB.getGroups(gcrit,Group.NAME_CONTAINS_IGNORE_CASE,Group.ORDER_BY_NAME);
    		session.setAttribute("Groups.found.vector",gdv);
    	}catch(Exception e){
    		e.printStackTrace();
    		errors.add("GroupMgrLogic",new ActionError("error.simpleGenericError",e.getMessage()));
    	}
    	return errors;
    }
    
    /**
     *Iniitilizes the form to process a new group.  Sets default values and creates the
     *group object.
     */
    public static ActionErrors initEmptyGroup(HttpServletResponse response,
    HttpServletRequest request, ActionForm pForm){
        ActionErrors errors = new ActionErrors();
        try{
            GroupForm sForm = (GroupForm) pForm;
            resetFormForDifferentGroup(sForm);
            GroupData g = GroupData.createValue();
            g.setGroupStatusCd(RefCodeNames.GROUP_STATUS_CD.ACTIVE);
            sForm.setGroupData(g);
            sForm.setReports(new String[0]);
            sForm.setApplicationFunctions(new String[0]);
            sForm.setGroupNameSelect(null);
        }catch(Exception e){
            e.printStackTrace();
            errors.add("GroupMgrLogic",new ActionError("error.simpleGenericError",e.getMessage()));
        }
        return errors;
    }
    
    
    /*
     *Called if the user has searched for a new group and the form needs to be reset in any way
     */
    private static void resetFormForDifferentGroup(GroupForm sForm){
        sForm.setConfigResults(null);
        sForm.setConfigResultsType(null);
        sForm.setConfigSearchField(null);
        sForm.setConfigSearchType(null);
        sForm.setConfigType(null);
    }
    
    /**
     *Fetches the detail and populates the form based off the request parameter "groupId"
     */
    public static ActionErrors getGroupDetail(HttpServletResponse response,
    HttpServletRequest request, ActionForm pForm){
        ActionErrors errors = new ActionErrors();
        
        try{
            GroupForm sForm = (GroupForm) pForm;
            HttpSession session = request.getSession();
            APIAccess factory = new APIAccess();
            Group groupEJB = factory.getGroupAPI();
            int groupId;
            String groupIdS=null;
            try{
                groupIdS =request.getParameter("groupId");
                if(!Utility.isSet(groupIdS)){
                    groupIdS = (String) session.getAttribute("groupId");
                }
                groupId = Integer.parseInt(groupIdS);
                
                if(sForm.getGroupData() == null || sForm.getGroupData().getGroupId() != groupId){
                    resetFormForDifferentGroup(sForm);
                }
                session.setAttribute("groupId", groupIdS);
            }catch(RuntimeException e){
                e.printStackTrace();
                initEmptyGroup(response, request, pForm);
                errors.add("GroupMgrLogic",new ActionError("error.invalidNumber","group id ("+groupIdS+")"));
                return errors;
            }
            GroupData grp = groupEJB.getGroupDetail(groupId);
            sForm.setGroupData(grp);
            //GroupSearchCriteriaView crit = GroupSearchCriteriaView.createValue();
            //crit.setGroupId(groupId);
            
            Map associations = groupEJB.getAllValidGroupAssociations(groupId);
            //get reports
            {
                Set reports = (Set) associations.get(RefCodeNames.GROUP_ASSOC_CD.REPORT_OF_GROUP);
                if(reports == null){
                    sForm.setReports(new String[0]);
                }else{
                    String[] lReportNames = new String[reports.size()];
                    Iterator it = reports.iterator();
                    int i=0;
                    while(it.hasNext()){
                        lReportNames[i] = ((GenericReportData) it.next()).getName();
                        i++;
                    }
                    sForm.setReports(lReportNames);
                }
            }
            
            //get application functions
            {
                Set functions = (Set) associations.get(RefCodeNames.GROUP_ASSOC_CD.FUNCTION_OF_GROUP);
                if(functions == null){
                    sForm.setApplicationFunctions(new String[0]);
                }else{
                    String[] lFunctionNames = new String[functions.size()];
                    Iterator it = functions.iterator();
                    int i=0;
                    while(it.hasNext()){
                        lFunctionNames[i] = (String) it.next();
                        i++;
                    }
                    sForm.setApplicationFunctions(lFunctionNames);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            errors.add("GroupMgrLogic",new ActionError("error.simpleGenericError",e.getMessage()));
        }
        return errors;
    }
    
    /**
     *Handles an update request when configuring groups (groupConfig screen).
     */
    public static ActionErrors updateGroupConfig(HttpServletRequest request, ActionForm pForm){
        ActionErrors errors = new ActionErrors();
        try{
            GroupForm sForm = (GroupForm) pForm;
            HttpSession session = request.getSession();
            APIAccess factory = new APIAccess();
            Group groupEJB = factory.getGroupAPI();
            CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
            int groupId = sForm.getGroupData().getGroupId();
            if(groupId == 0){
                errors.add("GroupMgrLogic",new ActionError("error.simpleGenericError","Group does not exist yet (group id = 0)"));
                return errors;
            }
            
            List newlySelected = sForm.getConfigResults().getNewlySelected();
            List newlyDeSelected = sForm.getConfigResults().getDeselected();
            IdVector newlySelectedIdV = Utility.toIdVector(newlySelected);
            IdVector newlyDeSelectedIdV = Utility.toIdVector(newlyDeSelected);
            if(RefCodeNames.GROUP_TYPE_CD.USER.equals(sForm.getConfigResultsType())){
                groupEJB.removeUsersFromGroup(groupId, newlyDeSelectedIdV);
                groupEJB.addUsersToGroup(groupId, newlySelectedIdV,appUser.getUserName());
            }else{
                groupEJB.removeBusEntitiesFromGroup(groupId, newlyDeSelectedIdV);
                groupEJB.addBusEntitiesToGroup(groupId, newlySelectedIdV,appUser.getUserName());
            }
            sForm.getConfigResults().resetState();
        }catch(Exception e){
            e.printStackTrace();
            errors.add("GroupMgrLogic",new ActionError("error.simpleGenericError",e.getMessage()));
        }
        return errors;
    }
    
    /**
     *Preforms the searching necessary when configuring a groups relationships to various different entities
     */
    public static ActionErrors searchGroupConfig(HttpServletRequest request, ActionForm pForm){
        ActionErrors errors = new ActionErrors();
        
        try{
            GroupForm sForm = (GroupForm) pForm;
            HttpSession session = request.getSession();
            APIAccess factory = new APIAccess();
            Group groupEJB = factory.getGroupAPI();
            int groupId = sForm.getGroupData().getGroupId();
            if(groupId == 0){
                errors.add("GroupMgrLogic",new ActionError("error.simpleGenericError","Group does not exist yet (group id = 0)"));
                return errors;
            }
            
            List options;
            List existing;
            Comparator comp = null;
            String fieldValue = sForm.getConfigSearchField();
            String fieldSearchType = sForm.getConfigSearchType();
            if(!Utility.isSet(fieldSearchType)){
                fieldSearchType = "nameContains";
            }
             IdVector storeIds = null;
             try{
                if(Utility.isSet(sForm.getSearchStoreId())){
                    Integer storeId = new Integer(sForm.getSearchStoreId());
                    storeIds = new IdVector();
                    storeIds.add(storeId);
                }
            }catch(NumberFormatException e){
                errors.add("storeId",new ActionError("error.invalidNumber","Store"));
                return errors;
            }
            if(RefCodeNames.GROUP_TYPE_CD.USER.equals(sForm.getConfigType())){
                GroupSearchCriteriaView gsc = GroupSearchCriteriaView.createValue();
                gsc.setGroupId(groupId);
                existing = groupEJB.getUsersForGroup(gsc,Group.NAME_EXACT,Group.ORDER_BY_NAME);
                options = UserMgrLogic.search(request, fieldValue, fieldSearchType, 0, null,null,null,storeIds);
                comp = USER_EQUALITY_COMPARE;
            }else if(RefCodeNames.GROUP_TYPE_CD.ACCOUNT.equals(sForm.getConfigType())){
                existing = groupEJB.getBusEntitysForGroup(groupId,RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT);
                options = AccountMgrLogic.search(request, fieldValue, fieldSearchType, null);
            }else if(RefCodeNames.GROUP_TYPE_CD.DISTRIBUTOR.equals(sForm.getConfigType())){
                existing = groupEJB.getBusEntitysForGroup(groupId,RefCodeNames.BUS_ENTITY_TYPE_CD.DISTRIBUTOR);
                options = DistMgrLogic.search(request, fieldValue, fieldSearchType, null, null, null, null, null);
//            }else if(RefCodeNames.GROUP_TYPE_CD.SITE.equals(sForm.getConfigType())){
//                existing = groupEJB.getBusEntitysForGroup(groupId,RefCodeNames.BUS_ENTITY_TYPE_CD.SITE);
//                options = SiteMgrLogic.search(request, fieldValue, fieldSearchType, null, null, null, null, null);
            }else if(RefCodeNames.GROUP_TYPE_CD.MANUFACTURER.equals(sForm.getConfigType())){
                existing = groupEJB.getBusEntitysForGroup(groupId,RefCodeNames.BUS_ENTITY_TYPE_CD.MANUFACTURER);
                options = ManufMgrLogic.search(request, fieldValue, fieldSearchType);
            }else if(RefCodeNames.GROUP_TYPE_CD.STORE.equals(sForm.getConfigType())){
                existing = groupEJB.getBusEntitysForGroup(groupId,RefCodeNames.BUS_ENTITY_TYPE_CD.STORE);
                options = StoreMgrLogic.search(request, fieldValue, fieldSearchType,null);
            }else{
                throw new IllegalStateException(sForm.getConfigType() + " not a valid config type");
            }
            //default comparator to use
            if (comp == null){
                comp = ClwComparatorFactory.getBusEntityComparator();
            }
            SelectableObjects results = new SelectableObjects(existing, options, comp);
            sForm.setConfigResults(results);
            sForm.setConfigResultsType(sForm.getConfigType());
        }catch(Exception e){
            e.printStackTrace();
            errors.add("GroupMgrLogic",new ActionError("error.simpleGenericError",e.getMessage()));
        }
        return errors;
    }
    
    
    private static final Comparator USER_EQUALITY_COMPARE = new Comparator() {
        public int compare(Object o1, Object o2) {
            int id1 = ((UserData)o1).getUserId();
            int id2 = ((UserData)o2).getUserId();
            return id1 - id2;
        }
    };

}
