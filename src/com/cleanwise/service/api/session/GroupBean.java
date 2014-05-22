package com.cleanwise.service.api.session;

/**
 * Title:        GroupBean
 * Description:  Bean implementation for Group Stateless Session Bean
 * Purpose:      Provides access to the table-level Group methods (add by, add date, mod by, mod date)
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 *
 */

import javax.ejb.*;
import java.rmi.*;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.dao.*;

import java.util.Map;
import java.util.TreeMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;
import java.util.Collection;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.naming.NamingException;

public class GroupBean extends ApplicationServicesAPI {

    private final String CLASS_NANE = "GroupBean";

    /**
     *
     */
    public GroupBean() {}

    /**
     *
     */
    public void ejbCreate() throws CreateException, RemoteException {}

    /**
     *@param pUserId - the userId of the user that we want the groups for
     *@return a map with the groupId as the key and the the group name as the value
     *@throws RemoteException if an error occurs
     */
    public Collection getGroupNamesUserIsMemberOf(int pUserId) throws RemoteException{
        return getGroupsUserIsMemberOf(pUserId).values();
    }

    /**
     *@param  pUserId - the userId of the user that we want the groups for
     *@return a map with the groupId as the key and the the group name as the value
     *@throws RemoteException if an error occurs
     */
    public Map getGroupsUserIsMemberOf(int pUserId) throws RemoteException{
    	return getGroupsUserIsMemberOf(pUserId, false);
    }
    
    public Map getGroupsUserIsMemberOf(int pUserId, boolean accountGroup) throws RemoteException{
        Connection conn = null;
        try{
            conn = getConnection();
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(GroupAssocDataAccess.GROUP_ASSOC_CD,RefCodeNames.GROUP_ASSOC_CD.USER_OF_GROUP);
            crit.addEqualTo(GroupAssocDataAccess.USER_ID,pUserId);
            GroupAssocDataVector gdv = GroupAssocDataAccess.select(conn,crit);

            Map retValue = new HashMap();
            for(int i=0,len=gdv.size();i<len;i++){
                GroupAssocData gd = (GroupAssocData) gdv.get(i);
                Integer groupId = new Integer(gd.getGroupId());
                GroupData group = GroupDataAccess.select(conn,groupId.intValue());
                
                if (group.getGroupStatusCd().equals(RefCodeNames.GROUP_STATUS_CD.ACTIVE)){
                	if(accountGroup){
                		if(RefCodeNames.GROUP_TYPE_CD.ACCOUNT.equals(group.getGroupTypeCd())){
                			retValue.put(groupId, group.getShortDesc());
                		}            		
                	}
                	if(RefCodeNames.GROUP_TYPE_CD.USER.equals(group.getGroupTypeCd())){
                		retValue.put(groupId, group.getShortDesc());
                	}
                }
                
                /*
                if (group.getGroupStatusCd().equals(RefCodeNames.GROUP_STATUS_CD.ACTIVE)
                        && RefCodeNames.GROUP_TYPE_CD.USER.equals(group.getGroupTypeCd())){
                    retValue.put(groupId, group.getShortDesc());
                }*/
            }
            //now add the implied group names
            UserData ud = UserDataAccess.select(conn, pUserId);
            crit = new DBCriteria();
            crit.addEqualTo(GroupDataAccess.SHORT_DESC,ud.getUserTypeCd());
            crit.addEqualTo(GroupDataAccess.GROUP_TYPE_CD,RefCodeNames.GROUP_TYPE_CD.USER);

            DBCriteria orCrit = new DBCriteria();
            orCrit.addEqualTo(GroupDataAccess.SHORT_DESC,"EVERYONE");
            orCrit.addEqualTo(GroupDataAccess.GROUP_TYPE_CD,RefCodeNames.GROUP_TYPE_CD.USER);
            crit.addOrCriteria(orCrit);
            GroupDataVector grpdv = GroupDataAccess.select(conn,crit);
            Iterator it = grpdv.iterator();
            while(it.hasNext()){
                GroupData group = (GroupData) it.next();
                if (group.getGroupStatusCd().equals(RefCodeNames.GROUP_STATUS_CD.ACTIVE)){
                    retValue.put(new Integer(group.getGroupId()), group.getShortDesc());
                }
            }
            return retValue;
        }catch(Exception e){
            throw processException(e);
        }finally{
            closeConnection(conn);
        }
    }

    /**
     *@return a map with the groupId as the key and the the group name as the value
     *@throws RemoteException if an error occurs
     */
    public Map getUserGroups() throws RemoteException{
        return getUserGroups(null);
    }
    
    public Map getUserGroups(boolean accountGroup) throws RemoteException{
    	return getUserGroups(null,accountGroup);
    }
    
    /**
     *@param pStoreIds. If not null, that return groups, connected with stores from pStoreIds.
     *@return a map with the groupId as the key and the the group name as the value
     *@throws RemoteException if an error occurs
     */
    public Map getUserGroups(IdVector pStoreIds, String groupAssocCd, boolean accountGroup) throws RemoteException{
        Connection conn = null;
        try{
            Map retValue = new HashMap();
            List<String> groupTypes = new ArrayList<String>();
            groupTypes.add(RefCodeNames.GROUP_TYPE_CD.USER);
            
            conn = getConnection();
            DBCriteria crit = new DBCriteria();
            if (pStoreIds != null) {
                IdVector groupIds = null;
                if (pStoreIds.size() > 0) {
                    crit.addOneOf(GroupAssocDataAccess.BUS_ENTITY_ID, IdVector.toCommaString(pStoreIds));
                    crit.addEqualTo(GroupAssocDataAccess.GROUP_ASSOC_CD, groupAssocCd);
                    groupIds = GroupAssocDataAccess.selectIdOnly(conn, GroupAssocDataAccess.GROUP_ID, crit);
                }
                if (groupIds != null && groupIds.size() > 0) {
                    crit = new DBCriteria();
                    crit.addOneOf(GroupDataAccess.GROUP_ID, IdVector.toCommaString(groupIds));
                } else {
                    return retValue;
                }
            }
            
            if(accountGroup){
            	groupTypes.add(RefCodeNames.GROUP_TYPE_CD.ACCOUNT);
            }
            crit.addOneOf(GroupDataAccess.GROUP_TYPE_CD,groupTypes);
           
            crit.addEqualTo(GroupDataAccess.GROUP_STATUS_CD,RefCodeNames.GROUP_STATUS_CD.ACTIVE);
            GroupDataVector gdv = GroupDataAccess.select(conn,crit);
            String groupIds = "";
            logDebug("getUserGroups, found " + gdv.size() + " groups");
            for(int i=0,len=gdv.size();i<len;i++){
                GroupData gd = (GroupData) gdv.get(i);
                Integer groupId = new Integer(gd.getGroupId());
				if ( groupIds.length() > 1 ) {
				    groupIds += ", ";
				}
				groupIds += groupId;
                retValue.put(groupId, gd.getShortDesc());
            }
            logDebug("getUserGroups, anaylized groups: "+ groupIds);
            return retValue;
        }catch(Exception e){
            throw processException(e);
        }finally{
            closeConnection(conn);
        }
    }

    public Map getUserGroups(IdVector pStoreIds) throws RemoteException{
    	return getUserGroups(pStoreIds, RefCodeNames.GROUP_ASSOC_CD.STORE_OF_GROUP);
    }
    
    public Map getUserGroups(IdVector pStoreIds, String groupAssocCd) throws RemoteException{
    	return getUserGroups(pStoreIds, groupAssocCd,false);
    }
    
    public Map getUserGroups(IdVector pStoreIds,boolean accountGroup) throws RemoteException{
    	return getUserGroups(pStoreIds, RefCodeNames.GROUP_ASSOC_CD.STORE_OF_GROUP,accountGroup);
    }
    
    public GroupDataVector getUserGroupsByType(int pUserId, String pGroupTypeCd) throws RemoteException{
    	Connection conn = null;
    	GroupDataVector gdv = new GroupDataVector();
    	
    	try{
    		conn = getConnection();
    		DBCriteria crit = new DBCriteria();
    		
    		Map userGrps = getGroupsUserIsMemberOf(pUserId, true);
    		if(userGrps!=null){
    			Set gIds = userGrps.keySet();
    			crit.addOneOf(GroupDataAccess.GROUP_ID, new ArrayList(gIds));
    			if(Utility.isSet(pGroupTypeCd)){
    				crit.addEqualTo(GroupDataAccess.GROUP_TYPE_CD, pGroupTypeCd);
    			}
    			gdv = GroupDataAccess.select(conn, crit);
    		}
    		
    		
    	}catch(Exception e){
            throw processException(e);
        }finally{
            closeConnection(conn);
        }
        return gdv;
    }
    
    //returns the goup id or 0 if no goup, or multiple groups was/were found by this name
    private GroupData getGroupFromGroupName(String groupName,String pType,Connection conn, boolean pOnlyActive) throws Exception{
        int groupId = 0;
        DBCriteria crit = new DBCriteria();
        crit.addEqualTo(GroupDataAccess.GROUP_TYPE_CD,pType);
        if(pOnlyActive){
            crit.addEqualTo(GroupDataAccess.GROUP_STATUS_CD,RefCodeNames.GROUP_STATUS_CD.ACTIVE);
        }
        crit.addEqualTo(GroupDataAccess.SHORT_DESC,groupName);
        GroupDataVector gdv = GroupDataAccess.select(conn,crit);
        if(gdv.size() == 1){
            return (GroupData)gdv.get(0);
        }
        return null;
    }

    private void addUserGroupAssociation(int pUserId,String pName,int pGroupId,String pGroupName,String pUserDoingModName,String groupAssociationTypeCd)
    throws RemoteException{
        Connection conn = null;
        try{
            conn = getConnection();
            addUserGroupAssociation(pUserId,pName, pGroupId, pGroupName, pUserDoingModName,conn,groupAssociationTypeCd);
        }catch(SQLException e){
            throw new RemoteException(e.getMessage());
        }catch(NamingException e){
            throw new RemoteException(e.getMessage());
        }finally{
            closeConnection(conn);
        }
    }

    //worker method to add an assocaition to a user group, if the pId is 0 it will attempt to
    //find the id of this thing (user, report etc) using the pName, if the pId is supplied the
    //pName is ignored.  If the groupId is 0 it will use the group
    //name to find the group id.  If both are supplied only the groupId is used.  If an invalid
    //groupAssociationTypeCd is supplied unpredictable results will occur.  The validaty of the
    //groupAssociationTypeCd is not validated.
    private void addUserGroupAssociation(int pId,String pName,int pGroupId,String pGroupName,String pUserDoingModName,
    Connection conn,String groupAssociationTypeCd)
    throws RemoteException{
        if(pGroupId == 0 && pGroupName==null){
            throw new RemoteException("groupName or groupId was not set");
        }
        if(pId == 0 && pName == null){
            throw new RemoteException("could not find association to relate group to");
        }
        if(pUserDoingModName==null || pUserDoingModName.length()==0){
            throw new RemoteException("User doing modification must be supplied");
        }
        try{
            //validate that the group does in fact exist and is valid, if not try to find
            //the group based off the group name
            boolean validGroup = false;
            if (pGroupId != 0){
                GroupData gd = GroupDataAccess.select(conn,pGroupId);
                if (gd!=null &&
                (gd.getGroupTypeCd().equalsIgnoreCase(RefCodeNames.GROUP_TYPE_CD.USER)
                		|| gd.getGroupTypeCd().equalsIgnoreCase(RefCodeNames.GROUP_TYPE_CD.ACCOUNT))
                && gd.getGroupStatusCd().equalsIgnoreCase(RefCodeNames.GROUP_STATUS_CD.ACTIVE)){
                    validGroup = true;
                }
            }
            //find group based off name
            if(pGroupId == 0){
                GroupData gd = getGroupFromGroupName(pGroupName,RefCodeNames.GROUP_TYPE_CD.USER,conn,true);
                if(gd != null){
                    pGroupId = gd.getGroupId();
                    validGroup = true;
                }
            }
            if(validGroup == false){
                throw new RemoteException("group info supplied is not valid");
            }

            //find the id of this association if the id was not supplied
            if(pId == 0){
                if(groupAssociationTypeCd.equals(RefCodeNames.GROUP_ASSOC_CD.USER_OF_GROUP)){
                    UserDataVector udv = getAPIAccess().getUserAPI().getUsersByName(pName, 0, User.NAME_EXACT,User.ORDER_BY_ID);
                    if(udv.size() == 1){
                        pId = ((UserData) udv.get(0)).getUserId();
                    }else{
                        throw new RemoteException("Found " + udv.size() + " for username: " + pName +
                        " expecting to find only 1");
                    }
                }else if(groupAssociationTypeCd.equals(RefCodeNames.GROUP_ASSOC_CD.REPORT_OF_GROUP)){
                    DBCriteria crit = new DBCriteria();
                    crit.addEqualTo(GenericReportDataAccess.NAME,pName);
                    GenericReportDataVector rdv = GenericReportDataAccess.select(conn,crit);
                    if(rdv.size() == 1){
                        pId = ((GenericReportData) rdv.get(0)).getGenericReportId();
                    }else{
                        throw new RemoteException("Found " + rdv.size() + " for report name: " + pName +
                        " expecting to find only 1");
                    }
                }
            }

            //check if the relationship already exists
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(GroupAssocDataAccess.GROUP_ASSOC_CD, RefCodeNames.GROUP_ASSOC_CD.USER_OF_GROUP);
            crit.addEqualTo(GroupAssocDataAccess.GROUP_ID, pGroupId);
            if(groupAssociationTypeCd.equals(RefCodeNames.GROUP_ASSOC_CD.USER_OF_GROUP)){
                crit.addEqualTo(GroupAssocDataAccess.USER_ID,pId);
            }else if(groupAssociationTypeCd.equals(RefCodeNames.GROUP_ASSOC_CD.REPORT_OF_GROUP)){
                crit.addEqualTo(GroupAssocDataAccess.GENERIC_REPORT_ID,pId);
            }

            GroupAssocDataVector gdv = GroupAssocDataAccess.select(conn, crit);
            if(gdv.size() == 0){
                //add it
                GroupAssocData gad=GroupAssocData.createValue();
                gad.setAddBy(pUserDoingModName);
                gad.setGroupAssocCd(RefCodeNames.GROUP_ASSOC_CD.USER_OF_GROUP);

                if(groupAssociationTypeCd.equals(RefCodeNames.GROUP_ASSOC_CD.USER_OF_GROUP)){
                    gad.setUserId(pId);
                }else if(groupAssociationTypeCd.equals(RefCodeNames.GROUP_ASSOC_CD.REPORT_OF_GROUP)){
                    gad.setGenericReportId(pId);
                }
                gad.setGroupId(pGroupId);
                GroupAssocDataAccess.insert(conn, gad);
            }
        }catch (Exception e){
            throw processException(e);
        }
    }


    //worker method to remove the user from the group, if the groupId is 0 it will use the group
    //name to find the group id.  If both are supplied only the id is used.
    private void removeUserFromGroup(int pUserId,int pGroupId,String pGroupName,String pUserDoingModName)
    throws RemoteException{
        Connection conn = null;
        try{
            conn = getConnection();
            removeUserFromGroup(pUserId, pGroupId, pGroupName, pUserDoingModName,conn);
        }catch(SQLException e){
            throw new RemoteException(e.getMessage());
        }catch(NamingException e){
            throw new RemoteException(e.getMessage());
        }finally{
            closeConnection(conn);
        }
    }

    //worker method to remove the user from the group, if the groupId is 0 it will use the group
    //name to find the group id.  If both are supplied only the id is used.
    private void removeUserFromGroup(int pUserId,int pGroupId,String pGroupName,String pUserDoingModName,Connection conn)
    throws RemoteException{
        if(pGroupId == 0 && pGroupName==null){
            throw new RemoteException("groupName or groupId was not set");
        }
        try{
            //find group based off name
            if(pGroupId == 0){
                GroupData gd = getGroupFromGroupName(pGroupName,RefCodeNames.GROUP_TYPE_CD.USER,conn,true);
                if(gd != null){
                    pGroupId = gd.getGroupId();
                }
            }
            if(pGroupId == 0){
                throw new RemoteException("group info supplied is not valid");
            }
            //check if the relationship already exists
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(GroupAssocDataAccess.GROUP_ASSOC_CD,RefCodeNames.GROUP_ASSOC_CD.USER_OF_GROUP);
            crit.addEqualTo(GroupAssocDataAccess.USER_ID,pUserId);
            crit.addEqualTo(GroupAssocDataAccess.GROUP_ID,pGroupId);
            IdVector idv = GroupAssocDataAccess.selectIdOnly(conn,crit);
            //remove all of the returned rows
            if(idv.size() > 0){
                for (int i=0,len=idv.size();i<len;i++){
                    Integer id = (Integer) idv.get(i);
                    GroupAssocDataAccess.remove(conn,id.intValue());
                }
            }
        }catch (Exception e){
            throw processException(e);
        }
    }

    /**
     *@param pUserId the user of the user that we want the groups for
     *@param pGroupName the group name to add this user to
     *@param pUserDoingModName the name of the admin user who is making this change
     *@throws RemoteException if an error occurs
     */
    public void addUserToGroup(int pUserId,String pGroupName,String pUserDoingModName) throws RemoteException{
        addUserGroupAssociation(pUserId,null,0,pGroupName, pUserDoingModName,RefCodeNames.GROUP_ASSOC_CD.USER_OF_GROUP);
    }

    /**
     *@param pUserId the userId of the user that we want the groups for
     *@param pGroupId the groupId to add this user to
     *@param pUserDoingModName the name of the admin user who is making this change
     *@throws RemoteException if an error occurs
     */
    public void addUserToGroupId(int pUserId,int pGroupId,String pUserDoingModName) throws RemoteException{
        addUserGroupAssociation(pUserId,null,pGroupId,null, pUserDoingModName,RefCodeNames.GROUP_ASSOC_CD.USER_OF_GROUP);
    }

    /**
     *@param pUserId the user Id of the user that we want the groups for
     *@param pGroupName the groupName to add this user to
     *@param pUserDoingModName the name of the admin user who is making this change
     *@throws RemoteException if an error occurs
     */
    public void removeUserFromGroup(int pUserId,String pGroupName,String pUserDoingModName) throws RemoteException{
        removeUserFromGroup(pUserId,0, pGroupName, pUserDoingModName);
    }

    /**
     *@param pUserId the userId of the user that we want the groups for
     *@param pGroupId the groupId to add this user to
     *@param pUserDoingModName the name of the admin user who is making this change
     *@throws RemoteException if an error occurs
     */
    public void removeUserFromGroupId(int pUserId,int pGroupId,String pUserDoingModName) throws RemoteException{
        removeUserFromGroup(pUserId,pGroupId, null, pUserDoingModName);
    }




    /**
     *@param pGroup the group to add or update
     *@param pUserDoingModName the admin user that is adding this group
     *@returns the id of the group that was added
     *@throws RemoteException if the group could not be added or errors occured during.
     */
    public int addGroup(GroupData pGroup,String pUserDoingModName) throws RemoteException{
        Connection conn = null;
        try{
            conn=getConnection();
            //check if this group already exists
            GroupData currentGrp = getGroupFromGroupName(pGroup.getShortDesc(),pGroup.getGroupTypeCd(),conn,false);

            if (currentGrp != null){
                if(pGroup.getGroupId() != currentGrp.getGroupId()){
                    throw new RemoteException("Error: Found another group by this name, id (" + currentGrp.getGroupId()+")");
                }
                pGroup.setModBy(currentGrp.getAddBy());
                pGroup.setGroupId(currentGrp.getGroupId());
                GroupDataAccess.update(conn, pGroup);
                return pGroup.getGroupId();
            }else{
                if(pGroup.getGroupId() > 0){
                    pGroup.setModBy(pUserDoingModName);
                    GroupDataAccess.update(conn, pGroup);
                    return pGroup.getGroupId();
                }else{
                    pGroup.setAddBy(pUserDoingModName);
                    return GroupDataAccess.insert(conn, pGroup).getGroupId();
                }
            }
        }catch (Exception e){
            throw processException(e);
        }finally{
            closeConnection(conn);
        }
    }

    //worker method to remove the group, if the groupId is 0 it will use the group
    //name to find the group id.  If both are supplied only the id is used.
    private void removeGroup(int pGroupId, String pGroupName, String pUserDoingModName)
    throws RemoteException{
        Connection conn = null;
        try{
            conn=getConnection();
            GroupDataVector toInactivate = new GroupDataVector();
            if(pGroupId == 0){
                //we will do our own lookup here so that a user may remove multiple
                //groups if they exists (data integrity error)
                int groupId = 0;
                DBCriteria crit = new DBCriteria();

                //crit.addEqualTo(GroupDataAccess.GROUP_TYPE_CD,RefCodeNames.GROUP_TYPE_CD.USER);
                crit.addEqualTo(GroupDataAccess.GROUP_STATUS_CD,RefCodeNames.GROUP_STATUS_CD.ACTIVE);
                crit.addEqualTo(GroupDataAccess.SHORT_DESC,pGroupName);
                GroupDataVector gdv = GroupDataAccess.select(conn,crit);
                if(gdv == null || gdv.size() == 0){
                    throw new RemoteException("Error: Could not find group "+pGroupName+" to delete.");
                }
                toInactivate.addAll(gdv);
            }else{
                GroupData gd = GroupDataAccess.select(conn,pGroupId);
                if(gd == null){
                    throw new RemoteException("Error: Could not find group id "+pGroupId+" to delete.");
                }
                toInactivate.add(gd);
            }

            for(int i=0,len=toInactivate.size();i<len;i++){
                GroupData gd = (GroupData) toInactivate.get(i);
                gd.setModBy(pUserDoingModName);
                gd.setGroupStatusCd(RefCodeNames.GROUP_STATUS_CD.INACTIVE);
                GroupDataAccess.update(conn, gd);
            }

        }catch (Exception e){
            throw processException(e);
        }finally{
            closeConnection(conn);
        }
    }

    /**
     *@param pGroupName the group name to remove (deactivate)
     *@param pUserDoingModName the admin user that is removing (deactivating) this group
     *@throws RemoteException if the group could not be removed (deactivated) or errors occured.
     */
    public void removeGroup(String pGroupName,String pUserDoingModName) throws RemoteException{
        removeGroup(0, pGroupName,pUserDoingModName);
    }

    /**
     *@param pGroupId the group id to remove (deactivate)
     *@param pUserDoingModName the admin user that is removing (deactivating) this group
     *@throws RemoteException if the group could not be removed (deactivated) or errors occured.
     */
    public void removeGroup(int pGroupId, String pUserDoingModName) throws RemoteException{
        removeGroup(pGroupId, null,pUserDoingModName);
    }

    /**
     *@param pReportName the report name to add to the group
     *@param pGroupName the group name to add this report to
     *@param pUserDoingModName the name of the admin user who is making this change
     *@throws RemoteException if an error occurs
     */
    public void addReportToUserGroup(String pReportName,String pGroupName,String pUserDoingModName)
    throws RemoteException{
        addUserGroupAssociation(0,pReportName,0,pGroupName, pUserDoingModName,RefCodeNames.GROUP_ASSOC_CD.USER_OF_GROUP);
    }


    /**
     *@param pReportName the report name to add to the group
     *@param pGroupId the groupId to add this report to
     *@param pUserDoingModName the name of the admin user who is making this change
     *@throws RemoteException if an error occurs
     */
    public void addReportToUserGroupId(String pReportName,int pGroupId,String pUserDoingModName)
    throws RemoteException{
        addUserGroupAssociation(0,pReportName,pGroupId,null, pUserDoingModName,RefCodeNames.GROUP_ASSOC_CD.USER_OF_GROUP);
    }

    /**
     *Will update the association table to reflect a group having identified by pGroupId
     *having the relationship to the reports identified by the List of pReportNames.  This
     *will both add and remove data from the associations.  So if an empty list is passed in
     *all associations beetween reports and the supplied group id will be removed.
     *@param pReportNames  - a list of String Objects representing the report names.  Invalid reports names
     *                       will trigger a RemoteException
     *@param pGroupId      - the group id of the group
     *@param pUserDoingMod - the user making this change
     *@throws RemoteException if an error occurs
     */
    public void updateReportToGroupAssociations(List pReportNames, int pGroupId, String pUserDoingMod)
    throws RemoteException{
        Connection conn = null;
        try{
            conn = getConnection();
            DBCriteria gcrit = new DBCriteria();
            gcrit.addEqualTo(GroupAssocDataAccess.GROUP_ID,pGroupId);
            gcrit.addNotEqualTo(GroupAssocDataAccess.GENERIC_REPORT_ID,0);
            gcrit.addIsNotNull(GroupAssocDataAccess.GENERIC_REPORT_ID);
            gcrit.addEqualTo(GroupAssocDataAccess.GROUP_ASSOC_CD,RefCodeNames.GROUP_ASSOC_CD.REPORT_OF_GROUP);
            String gSql = GroupAssocDataAccess.getSqlSelectIdOnly(GroupAssocDataAccess.GENERIC_REPORT_ID, gcrit);
            DBCriteria rcrit = new DBCriteria();
            rcrit.addOneOf(GenericReportDataAccess.GENERIC_REPORT_ID,gSql);
            GenericReportDataVector currentAssoc = GenericReportDataAccess.select(conn,rcrit);
            Iterator currIt = currentAssoc.iterator();
            while(currIt.hasNext()){
                GenericReportData currReportData = (GenericReportData) currIt.next();
                Iterator newIt = pReportNames.iterator();
                boolean removeAssoc = true;
                while(newIt.hasNext()){
                    String newReportName = (String) newIt.next();
                    if(currReportData.getName().equalsIgnoreCase(newReportName)){
                        //mark it for removal and remove it from the list.  Whatever is
                        //left in the list at the end will be added to the association table
                        removeAssoc = false;
                        newIt.remove();
                        break;
                    }
                }
                if(removeAssoc){
                    DBCriteria removeCrit = new DBCriteria();
                    removeCrit.addEqualTo(GroupAssocDataAccess.GROUP_ID,pGroupId);
                    removeCrit.addEqualTo(GroupAssocDataAccess.GENERIC_REPORT_ID,currReportData.getGenericReportId());
                    GroupAssocDataAccess.remove(conn,removeCrit);
                }
            }

            //in the previous list we removed everything we found, so now lets add all anything
            //that is left in the list as it is a new associtation
            Iterator newIt = pReportNames.iterator();
            while(newIt.hasNext()){
                String newReportName = (String) newIt.next();
                rcrit = new DBCriteria();
                rcrit.addEqualToIgnoreCase(GenericReportDataAccess.NAME,newReportName);
                GenericReportDataVector reports = GenericReportDataAccess.select(conn,rcrit);
                if(reports.size() != 1){
                    throw new RemoteException("Error: Expecting to find 1 report for report name: " +
                    newReportName + " found: " + reports.size());
                }
                GenericReportData report = (GenericReportData) reports.get(0);
                GroupAssocData newAssoc = GroupAssocData.createValue();
                newAssoc.setAddBy(pUserDoingMod);
                newAssoc.setGenericReportId(report.getGenericReportId());
                newAssoc.setGroupAssocCd(RefCodeNames.GROUP_ASSOC_CD.REPORT_OF_GROUP);
                newAssoc.setGroupId(pGroupId);
                GroupAssocDataAccess.insert(conn, newAssoc);
            }
        }catch(Exception e){
            throw processException(e);
        }finally{
            closeConnection(conn);
        }
    }

    /**
     *Will update the association table to reflect a group having identified by pGroupId
     *having the relationship to the application functions identified by the List of pApplicationFunctions.
     *This will both add and remove data from the associations.  So if an empty list is passed in
     *all associations beetween application functions and the supplied group id will be removed.
     *@param pApplicationFunctions -  a list of String Objects representing the application functions.
     *@param groupId              -  the group id of the group
     *@param userName         -  the user making this change
     *@throws RemoteException if an error occurs
     */
    public void updateApplicationFunctionToGroupAssociations(List pApplicationFunctions, int groupId, String userName)
    throws RemoteException{
        Connection conn = null;
        try{
            conn = getConnection();
            DBCriteria gcrit = new DBCriteria();
            gcrit.addEqualTo(GroupAssocDataAccess.GROUP_ID,groupId);
            gcrit.addEqualTo(GroupAssocDataAccess.GROUP_ASSOC_CD,RefCodeNames.GROUP_ASSOC_CD.FUNCTION_OF_GROUP);
            gcrit.addJoinCondition(GroupAssocDataAccess.APPLICATION_FUNCTION, RefCdDataAccess.CLW_REF_CD, RefCdDataAccess.CLW_VALUE);
            gcrit.addJoinTableEqualTo(RefCdDataAccess.CLW_REF_CD, RefCdDataAccess.REF_CD, "APPLICATION_FUNCTIONS");
            Collection<GroupAssocData> currentAssoc = GroupAssocDataAccess.select(conn, gcrit);
            
            Iterator currIt = currentAssoc.iterator();
            while(currIt.hasNext()){
                GroupAssocData currAssoc = (GroupAssocData) currIt.next();
                Iterator newIt = pApplicationFunctions.iterator();
                boolean removeAssoc = true;
                while(newIt.hasNext()){
                    String newAppFunc = (String) newIt.next();
                    if(currAssoc.getApplicationFunction().equalsIgnoreCase(newAppFunc)){
                        //Whatever is
                        //left in the list at the end will be added to the association table
                        removeAssoc = false;
                        newIt.remove();
                        break;
                    }
                }
                //if it was not found remove it
                if(removeAssoc){
                    //DBCriteria removeCrit = new DBCriteria();
                    //removeCrit.addEqualTo(GroupAssocDataAccess.GROUP_ID,pGroupId);
                    //removeCrit.addEqualTo(GroupAssocDataAccess.APPLICATION_FUNCTION,currReportData.getGenericReportId());
                    GroupAssocDataAccess.remove(conn,currAssoc.getGroupAssocId());
                }
            }

            //in the previous list we removed everything we didn't find, so now lets add all anything
            //that is left in the list as it is a new associtation
            Iterator newIt = pApplicationFunctions.iterator();
            boolean removeAssoc = true;
            while(newIt.hasNext()){
                String newAppFunc = (String) newIt.next();

                GroupAssocData newAssoc = GroupAssocData.createValue();
                newAssoc.setAddBy(userName);
                newAssoc.setApplicationFunction(newAppFunc);
                newAssoc.setGroupAssocCd(RefCodeNames.GROUP_ASSOC_CD.FUNCTION_OF_GROUP);
                newAssoc.setGroupId(groupId);
                GroupAssocDataAccess.insert(conn, newAssoc);
            }
        }catch(Exception e){
            throw processException(e);
        }finally{
            closeConnection(conn);
        }
    }

    /**
     *@param pGroupId the group id of the group in question
     *@returns a map with the association type as the key and an list of the names of
     * the function/group as the values.
     *@throws RemoteException if an error occurs
     */
    public Map getAllValidGroupAssociations(int pGroupId)
    throws RemoteException{

        Connection conn = null;
        try{
            conn = getConnection();
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(GroupAssocDataAccess.GROUP_ID,pGroupId);
            String sql = GroupAssocDataAccess.getSqlSelectIdOnly("*", crit);
            ResultSet rs = conn.createStatement().executeQuery(sql);

            if(rs==null){
                return new HashMap();
            }
            HashMap myMap = new HashMap();
            getAssocMapFromResultSet(conn,rs,myMap);
            return myMap;
        }catch(Exception e){
            throw processException(e);
        }finally{
            closeConnection(conn);
        }
    }

    private void getAssocMapFromResultSet(Connection pCon,ResultSet rs, Map pMap) throws SQLException{
        //set of the reports that we have anylized so we don't lookup this report
        //more than once in case the user belongs to two groups that have access to
        //this report
        HashSet retrievedReports = new HashSet();
        while(rs.next()){
            //String key = RefCodeNames.GROUP_ASSOC_CD.REPORT_OF_GROUP;
            String key = rs.getString(GroupAssocDataAccess.GROUP_ASSOC_CD);
            Set value=(Set) pMap.get(key);
            if(value==null){
                value=new HashSet();
                pMap.put(key,value);
            }
            String name=null;
            if(RefCodeNames.GROUP_ASSOC_CD.REPORT_OF_GROUP.equals(key)){
                int reportIdInt=rs.getInt(GroupAssocDataAccess.GENERIC_REPORT_ID);
                Integer reportId = new Integer(reportIdInt);
                if (retrievedReports.add(reportId)){
                    try{
                        GenericReportData rep = GenericReportDataAccess.select(pCon,reportIdInt);
                        rep.setScriptText("");
                        rep.setSqlText("");
                        value.add(rep);
                    }catch (DataNotFoundException e){
                        logError("Configured report id: "+reportId+" could not be found");
                    }
                }
            }//end report of group
            else if(RefCodeNames.GROUP_ASSOC_CD.FUNCTION_OF_GROUP.equals(key)){
                name = rs.getString(GroupAssocDataAccess.APPLICATION_FUNCTION);
                value.add(name);

            }

        }
    }


    private static final String groupAssocMapSQL0 =
    "SELECT DISTINCT ras.* FROM "+GroupAssocDataAccess.CLW_GROUP_ASSOC+" uas,"+GroupAssocDataAccess.CLW_GROUP_ASSOC+" ras, "+GroupDataAccess.CLW_GROUP+" g "+
    "WHERE ras."+GroupAssocDataAccess.GROUP_ASSOC_CD+" != '"+RefCodeNames.GROUP_ASSOC_CD.USER_OF_GROUP+"' " +
    "AND g."+GroupDataAccess.GROUP_STATUS_CD+"='"+RefCodeNames.GROUP_STATUS_CD.ACTIVE+"' "+
    "AND uas."+GroupAssocDataAccess.GROUP_ASSOC_CD+"='"+RefCodeNames.GROUP_ASSOC_CD.USER_OF_GROUP+"' AND g."+GroupDataAccess.GROUP_ID+"=uas."+GroupAssocDataAccess.GROUP_ID+" AND uas."+GroupAssocDataAccess.USER_ID+"=? "+
    "AND g."+GroupDataAccess.GROUP_ID+" = ras."+GroupAssocDataAccess.GROUP_ID +" ";

    private static final String groupAssocMapSQL1 =
    "SELECT DISTINCT ras.* FROM "+GroupAssocDataAccess.CLW_GROUP_ASSOC+" ras, "+GroupDataAccess.CLW_GROUP+" g "+
    "WHERE ras."+GroupAssocDataAccess.GROUP_ASSOC_CD+" != '"+RefCodeNames.GROUP_ASSOC_CD.USER_OF_GROUP+"' " +
    "AND g."+GroupDataAccess.GROUP_STATUS_CD+"='"+RefCodeNames.GROUP_STATUS_CD.ACTIVE+"' "+
    "AND (g."+GroupDataAccess.SHORT_DESC+"=? OR g."+GroupDataAccess.SHORT_DESC+"='EVERYONE') "+
    "AND g."+GroupDataAccess.GROUP_ID+" = ras."+GroupAssocDataAccess.GROUP_ID +" ";

    /**
     *Returns all the group assoctions regardless of user
     */
    private Map getAllGroupAssociations(Connection conn) throws SQLException{
        HashMap map = new HashMap();
        //select all the reports
        GenericReportDataVector gdv = GenericReportDataAccess.selectAll(conn);
        HashSet allReports = new HashSet();
        Iterator it = gdv.iterator();
        while(it.hasNext()){
            //null out that which we cannot send through RMI
            GenericReportData gd = (GenericReportData) it.next();
            gd.setScriptText(null);
            gd.setSqlText(null);
            allReports.add(gd);
        }
        map.put(RefCodeNames.GROUP_ASSOC_CD.REPORT_OF_GROUP,allReports);

        //select all application functions
        DBCriteria crit = new DBCriteria();
        crit.addEqualTo(RefCdDataAccess.REF_CD,"APPLICATION_FUNCTIONS");
        RefCdDataVector dv = RefCdDataAccess.select(conn,crit);
        //add the names to a list
        it = dv.iterator();
        HashSet allFunctions = new HashSet();
        while(it.hasNext()){
            RefCdData r = (RefCdData) it.next();
            allFunctions.add(r.getValue());
        }
        map.put(RefCodeNames.GROUP_ASSOC_CD.FUNCTION_OF_GROUP,allFunctions);
        return map;
    }


    /**
     *@param pUserId the user id of the user in question
     *@returns a map with the association type as the key and an arraylist of the names of
     * the function/group as the values.
     *@throws RemoteException if an error occurs
     */
    public Map getAllValidUserGroupAssociations(int pUserId)
    throws RemoteException{
    	if(pUserId == 0){
        	return null;
        }
        Connection conn = null;
        try{
            conn = getConnection();
            UserData user = UserDataAccess.select(conn,pUserId);
            if(RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(user.getUserTypeCd())){
                return getAllGroupAssociations(conn);
            }
            if(isLoggingDebugEnabled()){
                logDebug("Group SQL 1:");
                logDebug(groupAssocMapSQL0);
                logDebug("Group SQL 2:");
                logDebug(groupAssocMapSQL1);
            }

            UserData ud = UserDataAccess.select(conn,pUserId);

            PreparedStatement stmt = conn.prepareStatement(groupAssocMapSQL0);
            stmt.setInt(1,ud.getUserId());

            ResultSet rs = stmt.executeQuery();
            Map assocMap = new HashMap();
            if(rs==null){
                logInfo("User not directly associated with any groups");
            }else{
                logInfo("User IS directly associated with groups");
                getAssocMapFromResultSet(conn,rs,assocMap);
            }


            stmt = conn.prepareStatement(groupAssocMapSQL1);
            stmt.setString(1,ud.getUserTypeCd());
            rs = stmt.executeQuery();
            if(rs!=null){
                if(isLoggingDebugEnabled()){
                    logDebug("BEFORE:"+assocMap.size());
                }
                getAssocMapFromResultSet(conn,rs,assocMap);
                if(isLoggingDebugEnabled()){
                    logDebug("AFTER:"+assocMap.size());
                }
            }

            return assocMap;
        }catch(Exception e){
            throw processException(e);
        }finally{
            closeConnection(conn);
        }
    }

    /**
     *@param pGroupCrit the criteria for searching
     *@param pMatch the match type to use
     *@param pOrder the ordering of the returned GroupDataVector
     *returns GroupDataVector matching the given criteria
     *@throws RemoteException if an error occurs
     */
    public GroupDataVector getGroups(GroupSearchCriteriaView pGroupCrit, int pMatch, int pOrder)
            throws RemoteException{

        Connection conn = null;

        try{

            conn = getConnection();
            int storeId = pGroupCrit.getStoreId();
            String storeGroupReq = null;

            if(storeId!=0) {
                DBCriteria dbc;
                dbc = new DBCriteria();
                dbc.addEqualTo(GroupAssocDataAccess.BUS_ENTITY_ID, storeId);
                dbc.addEqualTo(GroupAssocDataAccess.GROUP_ASSOC_CD, RefCodeNames.GROUP_ASSOC_CD.STORE_OF_GROUP);

                storeGroupReq = GroupAssocDataAccess.getSqlSelectIdOnly(GroupDataAccess.GROUP_ID, dbc);
                /*

                String groupTypeCd = pGroupCrit.getGroupType();

                if(RefCodeNames.GROUP_TYPE_CD.ACCOUNT.equals(groupTypeCd)) {

                    dbc = new DBCriteria();
                    dbc.addEqualTo(GroupAssocDataAccess.BUS_ENTITY_ID, storeId);
                    dbc.addEqualTo(GroupAssocDataAccess.GROUP_ASSOC_CD, RefCodeNames.GROUP_ASSOC_CD.STORE_OF_GROUP);

                    storeGroupReq = GroupAssocDataAccess.getSqlSelectIdOnly(GroupDataAccess.GROUP_ID, dbc);
                }

                else if(RefCodeNames.GROUP_TYPE_CD.DISTRIBUTOR.equals(groupTypeCd)) {
                    dbc = new DBCriteria();
                    dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY2_ID,storeId);
                    dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,
                            RefCodeNames.BUS_ENTITY_ASSOC_CD.DISTRIBUTOR_STORE);
                    String storeAccountReq = BusEntityAssocDataAccess.getSqlSelectIdOnly(
                            BusEntityAssocDataAccess.BUS_ENTITY1_ID, dbc);

                    dbc = new DBCriteria();
                    dbc.addOneOf(GroupAssocDataAccess.BUS_ENTITY_ID,storeAccountReq);
                    dbc.addEqualTo(GroupAssocDataAccess.GROUP_ASSOC_CD,
                            RefCodeNames.GROUP_ASSOC_CD.BUS_ENTITY_OF_GROUP);

                    storeGroupReq = GroupAssocDataAccess.getSqlSelectIdOnly(GroupDataAccess.GROUP_ID,dbc);
                }

                else if(RefCodeNames.GROUP_TYPE_CD.MANUFACTURER.equals(groupTypeCd)) {
                    dbc = new DBCriteria();
                    dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY2_ID,storeId);
                    dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,
                            RefCodeNames.BUS_ENTITY_ASSOC_CD.MANUFACTURER_STORE);
                    String storeManReq = BusEntityAssocDataAccess.getSqlSelectIdOnly(
                            BusEntityAssocDataAccess.BUS_ENTITY1_ID, dbc);

                    dbc = new DBCriteria();
                    dbc.addOneOf(GroupAssocDataAccess.BUS_ENTITY_ID,storeManReq);
                    dbc.addEqualTo(GroupAssocDataAccess.GROUP_ASSOC_CD,
                            RefCodeNames.GROUP_ASSOC_CD.BUS_ENTITY_OF_GROUP);

                    storeGroupReq = GroupAssocDataAccess.getSqlSelectIdOnly(GroupDataAccess.GROUP_ID,dbc);
                }

                else if(RefCodeNames.GROUP_TYPE_CD.STORE.equals(groupTypeCd)) {
                    dbc = new DBCriteria();
                    dbc.addEqualTo(GroupAssocDataAccess.BUS_ENTITY_ID,storeId);
                    dbc.addEqualTo(GroupAssocDataAccess.GROUP_ASSOC_CD,
                            RefCodeNames.GROUP_ASSOC_CD.BUS_ENTITY_OF_GROUP);

                    storeGroupReq = GroupAssocDataAccess.getSqlSelectIdOnly(GroupDataAccess.GROUP_ID,dbc);
                }

                else if(RefCodeNames.GROUP_TYPE_CD.SITE.equals(groupTypeCd)) {
                    dbc = new DBCriteria();
                    dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY2_ID,storeId);
                    dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,
                            RefCodeNames.BUS_ENTITY_ASSOC_CD.ACCOUNT_STORE);
                    String storeAccountReq = BusEntityAssocDataAccess.getSqlSelectIdOnly(
                            BusEntityAssocDataAccess.BUS_ENTITY1_ID, dbc);

                    dbc = new DBCriteria();
                    dbc.addOneOf(BusEntityAssocDataAccess.BUS_ENTITY2_ID,storeAccountReq);
                    dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,
                            RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT);

                    String storeSiteReq = BusEntityAssocDataAccess.getSqlSelectIdOnly(
                            BusEntityAssocDataAccess.BUS_ENTITY1_ID, dbc);

                    dbc = new DBCriteria();
                    dbc.addOneOf(GroupAssocDataAccess.BUS_ENTITY_ID,storeSiteReq);
                    dbc.addEqualTo(GroupAssocDataAccess.GROUP_ASSOC_CD,
                            RefCodeNames.GROUP_ASSOC_CD.BUS_ENTITY_OF_GROUP);

                    storeGroupReq = GroupAssocDataAccess.getSqlSelectIdOnly(GroupDataAccess.GROUP_ID, dbc);
                }

                else if(RefCodeNames.GROUP_TYPE_CD.USER.equals(groupTypeCd)) {
                                        dbc = new DBCriteria();
                    dbc.addEqualTo(UserAssocDataAccess.BUS_ENTITY_ID,storeId);
                    dbc.addEqualTo(UserAssocDataAccess.USER_ASSOC_CD,
                                     RefCodeNames.USER_ASSOC_CD.STORE);
                    String storeUserReq = UserAssocDataAccess.getSqlSelectIdOnly(
                        UserAssocDataAccess.USER_ID, dbc);


                    String storeUserReq =
                            " select user_id from clw_user_assoc  "+
                                    " where user_assoc_cd = 'STORE'  "+
                                    " and bus_entity_id = "+storeId;

                    dbc = new DBCriteria();
                    dbc.addOneOf(GroupAssocDataAccess.USER_ID, storeUserReq);
                    dbc.addEqualTo(GroupAssocDataAccess.GROUP_ASSOC_CD, RefCodeNames.GROUP_ASSOC_CD.USER_OF_GROUP);
                    storeGroupReq = GroupAssocDataAccess.getSqlSelectIdOnly(
                            GroupDataAccess.GROUP_ID, dbc);
                } else {
                    dbc = new DBCriteria();
                    dbc.addCondition("1=2");
                    storeGroupReq = GroupAssocDataAccess.getSqlSelectIdOnly(
                            GroupDataAccess.GROUP_ID,dbc);
                }
            */
            }


            DBCriteria crit = new DBCriteria();
            if(Utility.isSet(pGroupCrit.getGroupType())){
                crit.addEqualTo(GroupDataAccess.GROUP_TYPE_CD, pGroupCrit.getGroupType());
            }

            if(storeId>0) {
                crit.addOneOf(GroupDataAccess.GROUP_ID, storeGroupReq);
            }

            if(Utility.isSet(pGroupCrit.getGroupStatus())) {
                crit.addEqualTo(GroupDataAccess.GROUP_STATUS_CD, pGroupCrit.getGroupStatus());
            }

            if(Utility.isSet(pGroupCrit.getGroupName())) {
                switch (pMatch) {
                    case Group.NAME_BEGINS_WITH:
                        crit.addLike(GroupDataAccess.SHORT_DESC, pGroupCrit.getGroupName() + "%");
                        break;
                    case Group.NAME_BEGINS_WITH_IGNORE_CASE:
                        crit.addLikeIgnoreCase(GroupDataAccess.SHORT_DESC,pGroupCrit.getGroupName() + "%");
                        break;
                    case Group.NAME_CONTAINS:
                        crit.addLike(GroupDataAccess.SHORT_DESC,"%" + pGroupCrit.getGroupName() + "%");
                        break;
                    case Group.NAME_CONTAINS_IGNORE_CASE:
                        crit.addLikeIgnoreCase(GroupDataAccess.SHORT_DESC,"%" + pGroupCrit.getGroupName() + "%");
                        break;
                    case Group.NAME_EXACT:
                        crit.addEqualTo(GroupDataAccess.SHORT_DESC,pGroupCrit.getGroupName());
                        break;
                    case Group.NAME_EXACT_IGNORE_CASE:
                        crit.addEqualToIgnoreCase(GroupDataAccess.SHORT_DESC,pGroupCrit.getGroupName());
                        break;
                    default:
                        throw new RemoteException("bad match");
                }
            }

            switch (pOrder) {
                case Group.ORDER_BY_ID:
                    crit.addOrderBy(GroupDataAccess.GROUP_ID, true);
                    break;
                case Group.ORDER_BY_NAME:
                    crit.addOrderBy(GroupDataAccess.SHORT_DESC, true);
                    break;
                default:
                    throw new RemoteException("Bad order specification");
            }

            String sss = GroupDataAccess.getSqlSelectIdOnly("*",crit);
            GroupDataVector gdV = GroupDataAccess.select(conn,crit);
            return gdV;

        }catch(Exception e){
            throw processException(e);
        }finally{
            closeConnection(conn);
        }
    }

    /**
     *returns GroupDataVector of all the groups
     *@throws RemoteException if an error occurs
     */
    public GroupDataVector getAllGroups() throws RemoteException{
        Connection conn = null;
        try{
            conn=getConnection();
            return GroupDataAccess.selectAll(conn);
        }catch(Exception e){
            throw processException(e);
        }finally{
            closeConnection(conn);
        }
    }

    public GroupDataVector getAllGroupsForStore(int pStoreId) throws RemoteException{
    	Connection conn = null;
        try{
            conn=getConnection();
            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(GroupAssocDataAccess.BUS_ENTITY_ID, pStoreId);
            dbc.addEqualTo(GroupAssocDataAccess.GROUP_ASSOC_CD, RefCodeNames.GROUP_ASSOC_CD.STORE_OF_GROUP);
            String storeGroupReq = GroupAssocDataAccess.getSqlSelectIdOnly(GroupDataAccess.GROUP_ID, dbc);
            
            dbc = new DBCriteria();
            dbc.addOneOf(GroupDataAccess.GROUP_ID, storeGroupReq);
            return GroupDataAccess.select(conn,dbc);
        }catch(Exception e){
            throw processException(e);
        }finally{
            closeConnection(conn);
        }
    }
    

    /*
     *@param pGroupId the group id
     *@returns a populated GroupData object
     *@throws DataNotFoundException if group with pGroupId doesn't exist
     *@throws RemoteException if an error occurs
     */
    public GroupData getGroupDetail(int pGroupId) throws RemoteException,
        DataNotFoundException {
        Connection conn = null;
        try{
            conn=getConnection();
            return GroupDataAccess.select(conn,pGroupId);
        } catch (DataNotFoundException e) {
            throw e;
        }catch(Exception e){
            throw processException(e);
        }finally{
            closeConnection(conn);
        }
    }
    /*
     *@param pGroupIds the group ids
     *@returns a populated GroupDataVector object
     *@throws RemoteException if an error occurs
     */
    public GroupDataVector getGroupsByIds(IdVector pGroupIds) throws RemoteException  {
        Connection conn = null;
        try{
            conn=getConnection();
            DBCriteria dbc;
            dbc = new DBCriteria();
            dbc.addOneOf(GroupDataAccess.GROUP_ID, pGroupIds);

            return GroupDataAccess.select(conn,dbc);
        }catch(Exception e){
            throw processException(e);
        }finally{
            closeConnection(conn);
        }
    }

    /**
     *@param searchCriteria - the search criteria to use when retrieving the results
     *@param pMatch         - the match type to use
     *@param pOrderBy       - the order by to use
     *@returns a populated UserDataVector with all the users that are a member of the supplied Group
     *@throws RemoteException if an error occurs
     */
    public UserDataVector getUsersForGroup(GroupSearchCriteriaView searchCriteria,int pMatch,int pOrderBy) throws RemoteException{
        Connection conn = null;
        try{
            conn=getConnection();
            DBCriteria gcrit = new DBCriteria();
            gcrit.addEqualTo(GroupAssocDataAccess.GROUP_ID,searchCriteria.getGroupId());
            gcrit.addNotEqualTo(GroupAssocDataAccess.USER_ID,0);
            gcrit.addIsNotNull(GroupAssocDataAccess.USER_ID);
            String gSql = GroupAssocDataAccess.getSqlSelectIdOnly(GroupAssocDataAccess.USER_ID, gcrit);
            DBCriteria ucrit = new DBCriteria();
            ucrit.addOneOf(UserDataAccess.USER_ID,gSql);
            if(Utility.isSet(searchCriteria.getUserName())){
                switch (pMatch) {
                    case Group.NAME_BEGINS_WITH:
                        ucrit.addLike(UserDataAccess.USER_NAME, searchCriteria.getUserName() + "%");
                        break;
                    case Group.NAME_BEGINS_WITH_IGNORE_CASE:
                        ucrit.addLikeIgnoreCase(UserDataAccess.USER_NAME,searchCriteria.getUserName() + "%");
                        break;
                    case Group.NAME_CONTAINS:
                        ucrit.addLike(UserDataAccess.USER_NAME,"%" + searchCriteria.getUserName() + "%");
                        break;
                    case Group.NAME_CONTAINS_IGNORE_CASE:
                        ucrit.addLikeIgnoreCase(UserDataAccess.USER_NAME,"%" + searchCriteria.getUserName() + "%");
                        break;
                    case Group.NAME_EXACT:
                        ucrit.addEqualTo(UserDataAccess.USER_NAME,searchCriteria.getUserName());
                        break;
                    case Group.NAME_EXACT_IGNORE_CASE:
                        ucrit.addEqualToIgnoreCase(UserDataAccess.USER_NAME,searchCriteria.getUserName());
                        break;
                    default:
                        throw new RemoteException("bad match");
                }
            }


            if(pOrderBy>0) {
              switch (pOrderBy) {
                  case Group.ORDER_BY_ID:
                      ucrit.addOrderBy(UserDataAccess.USER_ID, true);
                      break;
                  case Group.ORDER_BY_NAME:
                      ucrit.addOrderBy(UserDataAccess.USER_NAME, true);
                      break;
                  default:
                      throw new RemoteException("Bad order specification");
              }
            }
            return UserDataAccess.select(conn,ucrit);
        }catch(Exception e){
            throw processException(e);
        }finally{
            closeConnection(conn);
        }
    }

    /**
     *@param searchCriteria the search criteria to use when retrieving the results
     *@param pMatch the match type to use
     *@param pOrderBy the order by to use
     *@returns a populated GenericReportDataVector with all the reports that are a member of the supplied Group
     *@throws RemoteException if an error occurs
     */
    public GenericReportDataVector getReportsForGroup(GroupSearchCriteriaView searchCriteria,int pMatch,int pOrderBy) throws RemoteException{
        Connection conn = null;
        try{
            conn=getConnection();
            DBCriteria gcrit = new DBCriteria();
            gcrit.addEqualTo(GroupAssocDataAccess.GROUP_ID,searchCriteria.getGroupId());
            gcrit.addNotEqualTo(GroupAssocDataAccess.GENERIC_REPORT_ID,0);
            gcrit.addIsNotNull(GroupAssocDataAccess.GENERIC_REPORT_ID);
            String gSql = GroupAssocDataAccess.getSqlSelectIdOnly(GroupAssocDataAccess.GENERIC_REPORT_ID, gcrit);
            DBCriteria rcrit = new DBCriteria();
            rcrit.addOneOf(GenericReportDataAccess.GENERIC_REPORT_ID,gSql);
            if(Utility.isSet(searchCriteria.getReportName())){
                switch (pMatch) {
                    case Group.NAME_BEGINS_WITH:
                        rcrit.addLike(GenericReportDataAccess.NAME, searchCriteria.getReportName() + "%");
                        break;
                    case Group.NAME_BEGINS_WITH_IGNORE_CASE:
                        rcrit.addLikeIgnoreCase(GenericReportDataAccess.NAME,searchCriteria.getReportName() + "%");
                        break;
                    case Group.NAME_CONTAINS:
                        rcrit.addLike(GenericReportDataAccess.NAME,"%" + searchCriteria.getReportName() + "%");
                        break;
                    case Group.NAME_CONTAINS_IGNORE_CASE:
                        rcrit.addLikeIgnoreCase(GenericReportDataAccess.NAME,"%" + searchCriteria.getReportName() + "%");
                        break;
                    case Group.NAME_EXACT:
                        rcrit.addEqualTo(GenericReportDataAccess.NAME,searchCriteria.getReportName());
                        break;
                    case Group.NAME_EXACT_IGNORE_CASE:
                        rcrit.addEqualToIgnoreCase(GenericReportDataAccess.NAME,searchCriteria.getReportName());
                        break;
                    default:
                        throw new RemoteException("bad match");
                }

            }

            switch (pOrderBy) {
                case Group.ORDER_BY_ID:
                    rcrit.addOrderBy(GenericReportDataAccess.GENERIC_REPORT_ID, true);
                    break;
                case Group.ORDER_BY_NAME:
                    rcrit.addOrderBy(GenericReportDataAccess.NAME, true);
                    break;
                default:
                    throw new RemoteException("Bad order specification");
            }

            GenericReportDataVector dv = GenericReportDataAccess.select(conn,rcrit);
            for(int i=0;i<dv.size();i++){
                GenericReportData rep = (GenericReportData) dv.get(i);
                rep.setScriptText(null);
                rep.setSqlText(null);
            }
            return dv;
        }catch(Exception e){
            throw processException(e);
        }finally{
            closeConnection(conn);
        }
    }


    /**
     *Returns a BusEntityDataVector of the appropriate type (if specified) that are
     *members of the specified group.
     *
     *@param pGroupId pGroupId      - the group id of the group we want the members of.
     *@param pOptionalBusEntityType - pOptionalBusEntityType the bus entity types that we want returned.
     *@returns BusEntityDataVector possibly empty of all the BusEntities that meet
     *  the specified criteria.
     *@throws RemoteException if an error occurs
     */
    public BusEntityDataVector getBusEntitysForGroup(int pGroupId, String pOptionalBusEntityType)
    throws RemoteException{
        Connection conn = null;
        try{
            conn=getConnection();
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(GroupAssocDataAccess.GROUP_ID,pGroupId);
            crit.addEqualTo(GroupAssocDataAccess.GROUP_ASSOC_CD,RefCodeNames.GROUP_ASSOC_CD.BUS_ENTITY_OF_GROUP);
            String sqlIn = GroupAssocDataAccess.getSqlSelectIdOnly(GroupAssocDataAccess.BUS_ENTITY_ID, crit);
            crit = new DBCriteria();
            crit.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID,sqlIn);
            if(Utility.isSet(pOptionalBusEntityType)){
                crit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,pOptionalBusEntityType);
            }
            crit.addOrderBy(BusEntityDataAccess.SHORT_DESC);
            return BusEntityDataAccess.select(conn,crit,500);
        }catch(Exception e){
            throw processException(e);
        }finally{
            closeConnection(conn);
        }
    }

    public IdVector getBusEntityIds()
    throws RemoteException{
        Connection conn = null;
        try{
            conn=getConnection();
            DBCriteria crit = getAllUiGroupCriteria(null);

            IdVector groupIds = GroupDataAccess.selectIdOnly(conn, crit);

            crit = new DBCriteria();
            crit.addOneOf(GroupAssocDataAccess.GROUP_ID, groupIds);
            crit.addEqualTo(GroupAssocDataAccess.GROUP_ASSOC_CD,RefCodeNames.GROUP_ASSOC_CD.BUS_ENTITY_OF_GROUP);
            String sqlIn = GroupAssocDataAccess.getSqlSelectIdOnly(GroupAssocDataAccess.BUS_ENTITY_ID, crit);

            crit = new DBCriteria();
            crit.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, sqlIn);

            return BusEntityDataAccess.selectIdOnly(conn, crit);
        }catch(Exception e){
            throw processException(e);
        }finally{
            closeConnection(conn);
        }
    }

    private DBCriteria getAllUiGroupCriteria(DBCriteria pDBCrit) throws SQLException {
        ArrayList groupTypeCdList = new ArrayList();
        groupTypeCdList.add(RefCodeNames.GROUP_TYPE_CD.ACCOUNT_UI);
        groupTypeCdList.add(RefCodeNames.GROUP_TYPE_CD.STORE_UI);
        groupTypeCdList.add(RefCodeNames.GROUP_TYPE_CD.USER_UI);

        if (pDBCrit == null) {
            pDBCrit = new DBCriteria();
        }

        pDBCrit.addOneOf(GroupDataAccess.GROUP_TYPE_CD, groupTypeCdList);
        pDBCrit.addOrderBy(GroupDataAccess.SHORT_DESC);
        pDBCrit.addOrderBy(GroupDataAccess.GROUP_TYPE_CD);

        return pDBCrit;

    }



    /**
     * Returns a BusEntityDataVector of the appropriate type (if specified) that are
     * members of the specified group.
     * @param pGroupId
     * @param pOptionalBusEntityType
     * @return
     * @throws RemoteException
     */
    public BusEntityDataVector getStoresForGroup(int pGroupId, String pOptionalBusEntityType)
            throws RemoteException{

        Connection conn = null;

        try{
            conn=getConnection();

            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(GroupAssocDataAccess.GROUP_ID, pGroupId);
            crit.addEqualTo(GroupAssocDataAccess.GROUP_ASSOC_CD, RefCodeNames.GROUP_ASSOC_CD.STORE_OF_GROUP);

            String sqlIn = GroupAssocDataAccess.getSqlSelectIdOnly(GroupAssocDataAccess.BUS_ENTITY_ID, crit);

            crit = new DBCriteria();
            crit.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, sqlIn);

            if(Utility.isSet(pOptionalBusEntityType)){
                crit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD, pOptionalBusEntityType);
            }

            return BusEntityDataAccess.select(conn, crit, 500);

        }catch(Exception e){
            throw processException(e);
        }finally{
            closeConnection(conn);
        }
    }

    /**
     *Adds the specified users to the specified group.
     *@param pUserIds      - the user ids of the users to add to this group
     *@param pGroupId      - the group id to add these users to
     *@param pUserDoingMod - the users user name who is doing the modification
     *@throws RemoteException if an error occurs
     */
    public void addUsersToGroup(int pGroupId, IdVector pUserIds, String pUserDoingMod)
    throws RemoteException {
        if(pGroupId == 0){
            throw new RemoteException("group id cannot be 0");
        }
        Connection conn = null;
        try{
            conn=getConnection();
            //find any existing assocations so we don't add them twice
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(GroupAssocDataAccess.GROUP_ID,pGroupId);
            crit.addEqualTo(GroupAssocDataAccess.GROUP_ASSOC_CD,RefCodeNames.GROUP_ASSOC_CD.USER_OF_GROUP);
            crit.addOneOf(GroupAssocDataAccess.USER_ID,pUserIds);
            IdVector existingAssociations = GroupAssocDataAccess.selectIdOnly(conn,GroupAssocDataAccess.USER_ID,crit);

            Iterator it = pUserIds.iterator();
            while(it.hasNext()){
                Integer userIdToAdd = (Integer) it.next();
                if(existingAssociations.contains(userIdToAdd)){
                    it.remove();
                }
            }

            //add anything that is left
            it = pUserIds.iterator();
            while(it.hasNext()){
                Integer userIdToAdd = (Integer) it.next();
                GroupAssocData gad=GroupAssocData.createValue();
                gad.setAddBy(pUserDoingMod);
                gad.setGroupAssocCd(RefCodeNames.GROUP_ASSOC_CD.USER_OF_GROUP);
                gad.setUserId(userIdToAdd.intValue());
                gad.setGroupId(pGroupId);
                GroupAssocDataAccess.insert(conn, gad);
            }
        }catch(Exception e){
            throw processException(e);
        }finally{
            closeConnection(conn);
        }
    }

    /**
     *Removes the specified users to the specified group.
     *@param pGroupId - the user ids of the users to remove from this group
     *@param pUserIds - the group id to remove these users from
     *@throws RemoteException if an error occurs
     */
    public void removeUsersFromGroup(int pGroupId, IdVector pUserIds)
    throws RemoteException {
        Connection conn = null;
        try{
            conn=getConnection();
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(GroupAssocDataAccess.GROUP_ASSOC_CD,RefCodeNames.GROUP_ASSOC_CD.USER_OF_GROUP);
            crit.addEqualTo(GroupAssocDataAccess.GROUP_ID,pGroupId);
            crit.addOneOf(GroupAssocDataAccess.USER_ID,pUserIds);
            GroupAssocDataAccess.remove(conn,crit);
        }catch(Exception e){
            throw processException(e);
        }finally{
            closeConnection(conn);
        }
    }

    /**
     *Adds the specified bus entities to the specified group.
     *@param pGroupId      - the bus entity ids of the bus entities to add to this group
     *@param pBusEntityIds - the group id to add these bus entities to
     *@param pUserDoingMod - the users user name who is doing the modification
     *@throws RemoteException if an error occurs
     */
    public void addBusEntitiesToGroup(int pGroupId, IdVector pBusEntityIds, String pUserDoingMod)
    throws RemoteException {
        if(pGroupId == 0){
            throw new RemoteException("group id cannot be 0");
        }
        Connection conn = null;
        try{
            conn=getConnection();
            //find any existing assocations so we don't add them twice
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(GroupAssocDataAccess.GROUP_ID, pGroupId);
            crit.addEqualTo(GroupAssocDataAccess.GROUP_ASSOC_CD, RefCodeNames.GROUP_ASSOC_CD.BUS_ENTITY_OF_GROUP);
            crit.addOneOf(GroupAssocDataAccess.BUS_ENTITY_ID, pBusEntityIds);
            IdVector existingAssociations = GroupAssocDataAccess.selectIdOnly(conn,GroupAssocDataAccess.BUS_ENTITY_ID, crit);

            Iterator it = pBusEntityIds.iterator();
            while(it.hasNext()){
                Integer beIdToAdd = (Integer) it.next();
                if(existingAssociations.contains(beIdToAdd)){
                    it.remove();
                }
            }

            //add anything that is left
            it = pBusEntityIds.iterator();
            while(it.hasNext()){
                Integer beIdToAdd = (Integer) it.next();
                GroupAssocData gad=GroupAssocData.createValue();
                gad.setAddBy(pUserDoingMod);
                gad.setGroupAssocCd(RefCodeNames.GROUP_ASSOC_CD.BUS_ENTITY_OF_GROUP);
                gad.setBusEntityId(beIdToAdd.intValue());
                gad.setGroupId(pGroupId);
                GroupAssocDataAccess.insert(conn, gad);
            }
        }catch(Exception e){
            throw processException(e);
        }finally{
            closeConnection(conn);
        }
    }

    /**
     *Adds the specified bus entities to the specified group.
     *@param pGroupId      - the group id to add these bus entities to
     *@param storesIds     - the bus entity ids of the bus entities to add to this group
     *@param pUserDoingMod - the users user name who is doing the modification
     *@throws RemoteException if an error occurs
     */
    public void addStoresToGroup(int pGroupId, IdVector storesIds, String pUserDoingMod)
            throws RemoteException {

        if(pGroupId == 0){
            throw new RemoteException("group id cannot be 0");
        }

        Connection conn = null;

        try{

            conn = getConnection();

            //find any existing assocations so we don't add them twice
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(GroupAssocDataAccess.GROUP_ID, pGroupId);
            crit.addEqualTo(GroupAssocDataAccess.GROUP_ASSOC_CD, RefCodeNames.GROUP_ASSOC_CD.STORE_OF_GROUP);

            IdVector existingAssociations = GroupAssocDataAccess.selectIdOnly(conn, GroupAssocDataAccess.BUS_ENTITY_ID, crit);

            Iterator it = storesIds.iterator();
            while(it.hasNext()){

                Integer beIdToAdd = (Integer) it.next();

                if(existingAssociations.contains(beIdToAdd)){
                    it.remove();
                }
            }

            //add anything that is left
            it = storesIds.iterator();

            while(it.hasNext()){
                Integer beIdToAdd = (Integer) it.next();

                GroupAssocData gad;
                gad = GroupAssocData.createValue();
                gad.setAddBy(pUserDoingMod);
                gad.setGroupAssocCd(RefCodeNames.GROUP_ASSOC_CD.STORE_OF_GROUP);
                gad.setBusEntityId(beIdToAdd.intValue());
                gad.setGroupId(pGroupId);

                GroupAssocDataAccess.insert(conn, gad);
            }

        }catch(Exception e){
            throw processException(e);
        }finally{
            closeConnection(conn);
        }
    }

    public void addAccountsToGroup(int pGroupId, IdVector accountsIds, String pUserDoingMod)throws RemoteException {

        if(pGroupId == 0) throw new RemoteException("group id cannot be 0");
        Connection conn = null;

        try{
            conn = getConnection();
            //find any existing assocations so we don't add them twice
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(GroupAssocDataAccess.GROUP_ID, pGroupId);
            crit.addEqualTo(GroupAssocDataAccess.GROUP_ASSOC_CD, RefCodeNames.GROUP_ASSOC_CD.ACCOUNT_OF_GROUP);

            IdVector existingAssociations = GroupAssocDataAccess.selectIdOnly(conn, GroupAssocDataAccess.BUS_ENTITY_ID, crit);
            Iterator it = accountsIds.iterator();
            while(it.hasNext()){
                Integer beIdToAdd = (Integer) it.next();
                if(existingAssociations.contains(beIdToAdd)){
                	it.remove();
                }
            }

            //add anything that is left
            it = accountsIds.iterator();
            while(it.hasNext()){
                Integer beIdToAdd = (Integer) it.next();
                GroupAssocData gad;
                gad = GroupAssocData.createValue();
                gad.setAddBy(pUserDoingMod);
                gad.setGroupAssocCd(RefCodeNames.GROUP_ASSOC_CD.ACCOUNT_OF_GROUP);
                gad.setBusEntityId(beIdToAdd.intValue());
                gad.setGroupId(pGroupId);
                GroupAssocDataAccess.insert(conn, gad);
            }
        }catch(Exception e){
            throw processException(e);
        }finally{
            closeConnection(conn);
        }
    }
    
    
    /**
     *Removes the specified bus entities to the specified group.
     *@param pBusEntityIds - the bus entities ids of the bus entities to remove from this group
     *@param pGroupId      - the group id to remove these bus entities from
     *@throws RemoteException if an error occurs
     */
    public void removeBusEntitiesFromGroup(int pGroupId, IdVector pBusEntityIds)
    throws RemoteException {
        Connection conn = null;
        try{
            conn=getConnection();
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(GroupAssocDataAccess.GROUP_ASSOC_CD,RefCodeNames.GROUP_ASSOC_CD.BUS_ENTITY_OF_GROUP);
            crit.addEqualTo(GroupAssocDataAccess.GROUP_ID,pGroupId);
            crit.addOneOf(GroupAssocDataAccess.BUS_ENTITY_ID,pBusEntityIds);
            GroupAssocDataAccess.remove(conn,crit);
        }catch(Exception e){
            throw processException(e);
        }finally{
            closeConnection(conn);
        }
    }

    public java.util.ArrayList getUserGroupsReport(int pUserId) throws RemoteException {
        Connection conn = null;
        try{
            conn = getConnection();
            String sql = "select distinct g.group_id  "
                    + " , nvl(g.short_desc,' ') group_desc "
                    + " , nvl(ga2.APPLICATION_FUNCTION,' ') app_function"
                    + " , ga2.GENERIC_REPORT_ID "
                    + " , nvl( (select name from clw_generic_report r "
                    + "        where r.generic_report_id = "
                    + "        ga2.GENERIC_REPORT_ID), ' ') report_name "
                    + " from clw_group_assoc ga, clw_group g "
                    + " , clw_group_assoc ga2 where ga.user_id = " + pUserId
                    + " and g.group_id = ga.group_id "
                    + " and ga2.group_id = g.group_id order by 2,3"
                    ;

            logDebug("  getUserGroupsReport sql=" + sql);
            return UniversalDAO.getData(conn, sql);


        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }
        return null;
    }

    public GroupDataVector getUIGroups() throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            return getUIGroups(conn, null);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
    }

    private GroupDataVector getUIGroups(Connection conn, DBCriteria pDBCrit) throws SQLException {
        return GroupDataAccess.select(conn, getAllUiGroupCriteria(pDBCrit));
    }


    public GroupData getUserUiGroup(int pUserId) throws RemoteException{
        Connection conn = null;
        try {
            conn = getConnection();
            return getUserUiGroup(conn,pUserId);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
    }

    public GroupData getStoreUiGroup(int pStoreId) throws RemoteException{
        Connection conn = null;
        try {
            conn = getConnection();
            return getStoreUiGroup(conn,pStoreId);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
    }

    public GroupData getAccountUiGroup(int pAccountId) throws RemoteException{
        Connection conn = null;
        try {
            conn = getConnection();
            return getAccountUiGroup(conn,pAccountId);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
    }

    private GroupData getAccountUiGroup(Connection pConn, int pAccountId) throws Exception {

        DBCriteria crit = new DBCriteria();
        GroupData group = null;
        if (pAccountId > 0) {

            crit.addJoinTableEqualTo(GroupAssocDataAccess.CLW_GROUP_ASSOC, GroupAssocDataAccess.BUS_ENTITY_ID, pAccountId);
            crit.addJoinTableEqualTo(GroupAssocDataAccess.CLW_GROUP_ASSOC, GroupAssocDataAccess.GROUP_ASSOC_CD, RefCodeNames.GROUP_ASSOC_CD.BUS_ENTITY_OF_GROUP);
            crit.addEqualTo(GroupDataAccess.GROUP_TYPE_CD, RefCodeNames.GROUP_TYPE_CD.ACCOUNT_UI);
            crit.addEqualTo(GroupDataAccess.GROUP_STATUS_CD, RefCodeNames.GROUP_STATUS_CD.ACTIVE);
            crit.addJoinCondition(GroupDataAccess.GROUP_ID, GroupAssocDataAccess.CLW_GROUP_ASSOC, GroupAssocDataAccess.GROUP_ID);

            GroupDataVector groups = GroupDataAccess.select(pConn, crit);
            if (groups.size() > 1) {
                throw new Exception("Multiple ui groups for account.AccountId:" + pAccountId);
            }
            if (!groups.isEmpty()) {
                group = (GroupData) groups.get(0);
            }
        }

        return group;
    }

    private GroupData getStoreUiGroup(Connection pConn, int pStoreId) throws Exception {

        DBCriteria crit = new DBCriteria();
        GroupData group = null;
        if (pStoreId > 0) {

            crit.addJoinTableEqualTo(GroupAssocDataAccess.CLW_GROUP_ASSOC, GroupAssocDataAccess.BUS_ENTITY_ID, pStoreId);
            crit.addJoinTableEqualTo(GroupAssocDataAccess.CLW_GROUP_ASSOC, GroupAssocDataAccess.GROUP_ASSOC_CD, RefCodeNames.GROUP_ASSOC_CD.BUS_ENTITY_OF_GROUP);
            crit.addEqualTo(GroupDataAccess.GROUP_TYPE_CD, RefCodeNames.GROUP_TYPE_CD.STORE_UI);
            crit.addEqualTo(GroupDataAccess.GROUP_STATUS_CD, RefCodeNames.GROUP_STATUS_CD.ACTIVE);
            crit.addJoinCondition(GroupDataAccess.GROUP_ID, GroupAssocDataAccess.CLW_GROUP_ASSOC, GroupAssocDataAccess.GROUP_ID);

            GroupDataVector groups = GroupDataAccess.select(pConn, crit);
            if (groups.size() > 1) {
                throw new Exception("Multiple ui groups for store.pStoreId:" + pStoreId);
            }
            if (!groups.isEmpty()) {
                group = (GroupData) groups.get(0);
            }
        }

        return group;
    }

    private GroupData getUserUiGroup(Connection pConn, int pUserId) throws Exception {

        DBCriteria crit = new DBCriteria();
        GroupData group = null;
        if (pUserId > 0) {

            UserData user;
            try {
                user = UserDataAccess.select(pConn, pUserId);
            } catch (Exception e) {
                e.printStackTrace();
                return group;
            }

            crit.addJoinTableEqualTo(GroupAssocDataAccess.CLW_GROUP_ASSOC, GroupAssocDataAccess.USER_ID, user.getUserId());
            crit.addJoinTableEqualTo(GroupAssocDataAccess.CLW_GROUP_ASSOC, GroupAssocDataAccess.GROUP_ASSOC_CD, RefCodeNames.GROUP_ASSOC_CD.USER_OF_GROUP);
            crit.addEqualTo(GroupDataAccess.GROUP_TYPE_CD, RefCodeNames.GROUP_TYPE_CD.USER_UI);
            crit.addEqualTo(GroupDataAccess.GROUP_STATUS_CD, RefCodeNames.GROUP_STATUS_CD.ACTIVE);
            crit.addJoinCondition(GroupDataAccess.GROUP_ID, GroupAssocDataAccess.CLW_GROUP_ASSOC, GroupAssocDataAccess.GROUP_ID);

            GroupDataVector groups = GroupDataAccess.select(pConn, crit);
            if (groups.size() > 1) {
                throw new Exception("Multiple ui groups for user.pUserId:" + pUserId);
            }

            if(groups.isEmpty()){

                crit = new DBCriteria();
                crit.addEqualTo(GroupDataAccess.GROUP_TYPE_CD, RefCodeNames.GROUP_TYPE_CD.USER_UI);
                crit.addEqualTo(GroupDataAccess.GROUP_STATUS_CD, RefCodeNames.GROUP_STATUS_CD.ACTIVE);
                crit.addEqualToIgnoreCase(GroupDataAccess.SHORT_DESC,user.getUserTypeCd());

                groups = GroupDataAccess.select(pConn, crit);
                if (groups.size() > 1) {
                    throw new Exception("Multiple ui groups for user.pUserId:" + pUserId);
                }

            }

            if(!groups.isEmpty()) {
                group = (GroupData) groups.get(0);
            }
        }

        return group;

    }

    private IdVector getUiGroupIdsAssociatedWithStore(Connection connection, int busEntityId) throws RemoteException {
        IdVector groupIds = new IdVector();
        try {
            PreparedStatement stmt = null;
            String sql = 
                "SELECT DISTINCT " +
                    "a.GROUP_ID " +
                "FROM " +
                    "CLW_GROUP_ASSOC a, CLW_GROUP g, CLW_BUS_ENTITY b " +
                "WHERE " +
                    "g.GROUP_ID = a.GROUP_ID " + 
                    "AND a.BUS_ENTITY_ID = b.BUS_ENTITY_ID " + 
                    "AND b.BUS_ENTITY_TYPE_CD = ? " + 
                    "AND g.GROUP_STATUS_CD = ? " + 
                    "AND g.GROUP_TYPE_CD = ? " + 
                    "AND a.GROUP_ASSOC_CD = ? " + 
                    "AND a.BUS_ENTITY_ID = ? ";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, RefCodeNames.BUS_ENTITY_TYPE_CD.STORE);
            stmt.setString(2, RefCodeNames.GROUP_STATUS_CD.ACTIVE);
            stmt.setString(3, RefCodeNames.GROUP_TYPE_CD.STORE_UI);
            stmt.setString(4, RefCodeNames.GROUP_ASSOC_CD.BUS_ENTITY_OF_GROUP);
            stmt.setInt(5, busEntityId);
            ResultSet resSet = stmt.executeQuery();
            while (resSet.next()) {
                int groupId = resSet.getInt("GROUP_ID");
                groupIds.add(new Integer(groupId));
            }
            try {
                resSet.close();
            } catch (Exception ex) {
            }
            try {
                stmt.close();
            } catch (Exception ex) {
            }
        }
        catch (Exception ex) {
            throw new RemoteException(ex.getMessage());
        }
        return groupIds;
    }

    private IdVector getUiGroupIdsAssociatedWithAccount(Connection connection, int busEntityId) throws RemoteException {
        IdVector groupIds = new IdVector();
        try {
            PreparedStatement stmt = null;
            String sql = 
                "SELECT DISTINCT " +
                    "a.GROUP_ID " +
                "FROM " +
                    "CLW_GROUP_ASSOC a, CLW_GROUP g, CLW_BUS_ENTITY b " +
                "WHERE " +
                    "g.GROUP_ID = a.GROUP_ID " + 
                    "AND a.BUS_ENTITY_ID = b.BUS_ENTITY_ID " + 
                    "AND b.BUS_ENTITY_TYPE_CD = ? " + 
                    "AND g.GROUP_STATUS_CD = ? " + 
                    "AND g.GROUP_TYPE_CD = ? " + 
                    "AND a.GROUP_ASSOC_CD = ? " + 
                    "AND a.BUS_ENTITY_ID = ? ";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT);
            stmt.setString(2, RefCodeNames.GROUP_STATUS_CD.ACTIVE);
            stmt.setString(3, RefCodeNames.GROUP_TYPE_CD.ACCOUNT_UI);
            stmt.setString(4, RefCodeNames.GROUP_ASSOC_CD.BUS_ENTITY_OF_GROUP);
            stmt.setInt(5, busEntityId);
            ResultSet resSet = stmt.executeQuery();
            while (resSet.next()) {
                int groupId = resSet.getInt("GROUP_ID");
                groupIds.add(new Integer(groupId));
            }
            try {
                resSet.close();
            } catch (Exception ex) {
            }
            try {
                stmt.close();
            } catch (Exception ex) {
            }
        }
        catch (Exception ex) {
            throw new RemoteException(ex.getMessage());
        }
        return groupIds;
    }

    /**
     * Adds the specified bus entities to the specified group.
     * This method is similar to a method "addBusEntitiesToGroup".
     * Unlike a method "addBusEntitiesToGroup" this method checks existing associations between stores and 'STORE_UI' groups.
     * This method generates an exception if the store has more than one associations with 'STORE_UI' groups.
     */
    public void addBusEntitiesToStoreUiGroup(int pGroupId, IdVector pBusEntityIds, String pUserDoingMod) throws RemoteException {
        if (pGroupId == 0) {
            throw new RemoteException("group id cannot be 0");
        }
        Connection conn = null;
        try {
            conn = getConnection();

            //find all existing assocations
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(GroupAssocDataAccess.GROUP_ID, pGroupId);
            crit.addEqualTo(GroupAssocDataAccess.GROUP_ASSOC_CD, RefCodeNames.GROUP_ASSOC_CD.BUS_ENTITY_OF_GROUP);
            IdVector allExistingAssociations = GroupAssocDataAccess.selectIdOnly(conn, GroupAssocDataAccess.BUS_ENTITY_ID, crit);

            //find any existing assocations so we don't add them twice
            if (allExistingAssociations != null && allExistingAssociations.size() > 0) {
                Iterator it = pBusEntityIds.iterator();
                while (it.hasNext()) {
                    Integer beIdToAdd = (Integer) it.next();
                    if (allExistingAssociations.contains(beIdToAdd)) {
                        it.remove();
                    }
                }
            }

            // search multiple associations between store and groups (STORE UI)
            StringBuilder errorMessages = new StringBuilder();
            boolean existsMultiAssociation = false;
            Iterator it = pBusEntityIds.iterator();
            while (it.hasNext()) {
                Integer beIdToAdd = (Integer) it.next();
                if (!allExistingAssociations.contains(beIdToAdd)) {
                    allExistingAssociations.add(beIdToAdd);
                }
            }
            if (allExistingAssociations != null && allExistingAssociations.size() > 0) {
                TreeMap<Integer, IdVector> storeUiAssociations = new TreeMap<Integer, IdVector>();
                for (int i = 0; i < allExistingAssociations.size(); ++i) {
                    Integer busEntityIdObj = (Integer)allExistingAssociations.get(i);
                    int busEntityId = busEntityIdObj.intValue();
                    IdVector groupIds = getUiGroupIdsAssociatedWithStore(conn, busEntityId);
                    storeUiAssociations.put(busEntityIdObj, groupIds);
                    if (groupIds != null && groupIds.size() > 1) {
                        existsMultiAssociation = true;
                        errorMessages.append("Store (id: " + busEntityId + ") can't have multiple association with 'STORE_UI' groups (id: ");
                        for (int j = 0; j < groupIds.size(); ++j) {
                            int groupId = ((Integer)groupIds.get(j)).intValue();
                            if (j > 0) {
                                errorMessages.append(", ");
                            }
                            errorMessages.append(groupId);
                        }
                        errorMessages.append("). ");
                    }
                }
                it = pBusEntityIds.iterator();
                while (it.hasNext()) {
                    Integer busEntityIdObj = (Integer) it.next();
                    if (storeUiAssociations.containsKey(busEntityIdObj)) {
                        IdVector groupIds = (IdVector)storeUiAssociations.get(busEntityIdObj);
                        if (groupIds != null && groupIds.size() > 0) {
                            existsMultiAssociation = true;
                            errorMessages.append("Store (id: " + busEntityIdObj + ") already associated with 'STORE_UI' group (id: ");
                            for (int j = 0; j < groupIds.size(); ++j) {
                                int groupId = ((Integer)groupIds.get(j)).intValue();
                                if (j > 0) {
                                    errorMessages.append(", ");
                                }
                                errorMessages.append(groupId);
                            }
                            errorMessages.append("). ");
                        }
                    }
                }
            }
            if (existsMultiAssociation) {
                throw new RemoteException(errorMessages.toString());
            }

            //add anything that is left
            it = pBusEntityIds.iterator();
            while (it.hasNext()) {
                Integer beIdToAdd = (Integer) it.next();
                GroupAssocData gad = GroupAssocData.createValue();
                gad.setAddBy(pUserDoingMod);
                gad.setGroupAssocCd(RefCodeNames.GROUP_ASSOC_CD.BUS_ENTITY_OF_GROUP);
                gad.setBusEntityId(beIdToAdd.intValue());
                gad.setGroupId(pGroupId);
                GroupAssocDataAccess.insert(conn, gad);
            }
        } catch(Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
    }

    /**
     * Adds the specified bus entities to the specified group.
     * This method is similar to a method "addBusEntitiesToGroup".
     * Unlike a method "addBusEntitiesToGroup" this method checks existing associations between accounts and 'ACCOUNT_UI' groups.
     * This method generates an exception if the account has more than one associations with 'ACCOUNT_UI' groups.
     */
    public void addBusEntitiesToAccountUiGroup(int pGroupId, IdVector pBusEntityIds, String pUserDoingMod) throws RemoteException {
        if (pGroupId == 0) {
            throw new RemoteException("group id cannot be 0");
        }
        Connection conn = null;
        try {
            conn = getConnection();

            //find all existing assocations
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(GroupAssocDataAccess.GROUP_ID, pGroupId);
            crit.addEqualTo(GroupAssocDataAccess.GROUP_ASSOC_CD, RefCodeNames.GROUP_ASSOC_CD.BUS_ENTITY_OF_GROUP);
            IdVector allExistingAssociations = GroupAssocDataAccess.selectIdOnly(conn, GroupAssocDataAccess.BUS_ENTITY_ID, crit);

            //find any existing assocations so we don't add them twice
            if (allExistingAssociations != null && allExistingAssociations.size() > 0) {
                Iterator it = pBusEntityIds.iterator();
                while (it.hasNext()) {
                    Integer beIdToAdd = (Integer) it.next();
                    if (allExistingAssociations.contains(beIdToAdd)) {
                        it.remove();
                    }
                }
            }

            // search multiple associations between account and groups (ACCOUNT UI)
            StringBuilder errorMessages = new StringBuilder();
            boolean existsMultiAssociation = false;
            Iterator it = pBusEntityIds.iterator();
            while (it.hasNext()) {
                Integer beIdToAdd = (Integer) it.next();
                if (!allExistingAssociations.contains(beIdToAdd)) {
                    allExistingAssociations.add(beIdToAdd);
                }
            }
            if (allExistingAssociations != null && allExistingAssociations.size() > 0) {
                TreeMap<Integer, IdVector> accountUiAssociations = new TreeMap<Integer, IdVector>();
                for (int i = 0; i < allExistingAssociations.size(); ++i) {
                    Integer busEntityIdObj = (Integer)allExistingAssociations.get(i);
                    int busEntityId = busEntityIdObj.intValue();
                    IdVector groupIds = getUiGroupIdsAssociatedWithAccount(conn, busEntityId);
                    accountUiAssociations.put(busEntityIdObj, groupIds);
                    if (groupIds != null && groupIds.size() > 1) {
                        existsMultiAssociation = true;
                        errorMessages.append("Account (id: " + busEntityId + ") can't have multiple association with 'ACCOUNT_UI' groups (id: ");
                        for (int j = 0; j < groupIds.size(); ++j) {
                            int groupId = ((Integer)groupIds.get(j)).intValue();
                            if (j > 0) {
                                errorMessages.append(", ");
                            }
                            errorMessages.append(groupId);
                        }
                        errorMessages.append("). ");
                    }
                }
                it = pBusEntityIds.iterator();
                while (it.hasNext()) {
                    Integer busEntityIdObj = (Integer) it.next();
                    if (accountUiAssociations.containsKey(busEntityIdObj)) {
                        IdVector groupIds = (IdVector)accountUiAssociations.get(busEntityIdObj);
                        if (groupIds != null && groupIds.size() > 0) {
                            existsMultiAssociation = true;
                            errorMessages.append("Account (id: " + busEntityIdObj + ") already associated with 'ACCOUNT_UI' group (id: ");
                            for (int j = 0; j < groupIds.size(); ++j) {
                                int groupId = ((Integer)groupIds.get(j)).intValue();
                                if (j > 0) {
                                    errorMessages.append(", ");
                                }
                                errorMessages.append(groupId);
                            }
                            errorMessages.append("). ");
                        }
                    }
                }
            }
            if (existsMultiAssociation) {
                throw new RemoteException(errorMessages.toString());
            }

            //add anything that is left
            it = pBusEntityIds.iterator();
            while (it.hasNext()) {
                Integer beIdToAdd = (Integer) it.next();
                GroupAssocData gad = GroupAssocData.createValue();
                gad.setAddBy(pUserDoingMod);
                gad.setGroupAssocCd(RefCodeNames.GROUP_ASSOC_CD.BUS_ENTITY_OF_GROUP);
                gad.setBusEntityId(beIdToAdd.intValue());
                gad.setGroupId(pGroupId);
                GroupAssocDataAccess.insert(conn, gad);
            }
        } catch(Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
    }

    public UiGroupDataViewVector getUiGroupDataViewVector() throws RemoteException{
        GroupDataVector uiGroups = getUIGroups();
        return fetchUiGroupData(uiGroups);
    }

    private UiGroupDataViewVector fetchUiGroupData(GroupDataVector pGroupDataV) throws RemoteException{
        UiGroupDataViewVector result = new UiGroupDataViewVector();
        Iterator i = pGroupDataV.iterator();
        while (i.hasNext()) {
            GroupData groupData = (GroupData)i.next();
            BusEntityDataVector groupDataAssociations = getBusEntitysForGroup(groupData.getGroupId(), null);
            UiGroupDataView groupDataView = new UiGroupDataView(groupData, groupDataAssociations, null);
            result.add(groupDataView);
        }
        return result;
    }


    public List<String> getGroupAssociationsStartWith(String pName, int pMaxRows) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            return getGroupAssociationsStartWith(conn, pName, pMaxRows);
        } catch (Exception exc) {
            exc.printStackTrace();
            throw processException(exc);
        } finally {
            closeConnection(conn);
        }
    }


    private List<String> getGroupAssociationsStartWith(Connection pConn, String pName, int pMaxRows) throws Exception {

        IdVector busEntityIds = getBusEntityIds();
        StringBuffer sqlBusEntCondition = new StringBuffer("BUS_ENTITY_ID IN (");
        for (int i=0; i<busEntityIds.size(); i++) {
            if (i > 0) {
                sqlBusEntCondition.append(",");
            }
            sqlBusEntCondition.append(busEntityIds.get(i));
        }
        sqlBusEntCondition.append(")");


        List<String> list = new ArrayList<String>();

        String reqName = pName.toUpperCase().replaceAll("'", "''");
        String sql = "SELECT DISTINCT SHORT_DESC FROM CLW_BUS_ENTITY WHERE " + sqlBusEntCondition +
                        "AND UPPER(SHORT_DESC) LIKE '" + reqName + "%' ORDER BY SHORT_DESC";

        PreparedStatement pstmt = pConn.prepareStatement(sql);

        pstmt.setMaxRows(pMaxRows);

        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            list.add(rs.getString(1));
        }
        return list;
    }


    public UiGroupDataViewVector searchUiGroupIdsByAssocName(String pAssocName) throws RemoteException {
        Connection conn = null;
        try{
            conn=getConnection();
            IdVector busEntIds = getBusEntityIds();
            DBCriteria crit = new DBCriteria();
            crit.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, busEntIds);
            crit.addContainsIgnoreCase(BusEntityDataAccess.SHORT_DESC, pAssocName);
            busEntIds = BusEntityDataAccess.selectIdOnly(conn, crit);

            crit = new DBCriteria();
            crit.addOneOf(GroupAssocDataAccess.BUS_ENTITY_ID, busEntIds);
            crit.addEqualTo(GroupAssocDataAccess.GROUP_ASSOC_CD,RefCodeNames.GROUP_ASSOC_CD.BUS_ENTITY_OF_GROUP);
            String sqlIn = GroupAssocDataAccess.getSqlSelectIdOnly(GroupAssocDataAccess.GROUP_ID, crit);

            crit = new DBCriteria();
            crit.addOneOf(GroupDataAccess.GROUP_ID, sqlIn);
            GroupDataVector groupIds = getUIGroups(conn, crit);

            return fetchUiGroupData(groupIds);

        }catch(Exception e){
            throw processException(e);
        }finally{
            closeConnection(conn);
        }

    }

    public void removeAccountsFromGroup(int pGroupId, IdVector pAccountIds)
    throws RemoteException {
        Connection conn = null;
        try{
            conn=getConnection();
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(GroupAssocDataAccess.GROUP_ASSOC_CD,RefCodeNames.GROUP_ASSOC_CD.ACCOUNT_OF_GROUP);
            crit.addEqualTo(GroupAssocDataAccess.GROUP_ID,pGroupId);
            crit.addOneOf(GroupAssocDataAccess.BUS_ENTITY_ID, pAccountIds);
            GroupAssocDataAccess.remove(conn,crit);
        }catch(Exception e){
            throw processException(e);
        }finally{
            closeConnection(conn);
        }
    }

    public BusEntityDataVector getAccountsForGroup(int pGroupId)throws RemoteException {
		Connection conn = null;
        try{
            conn=getConnection();
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(GroupAssocDataAccess.GROUP_ID,pGroupId);
            crit.addEqualTo(GroupAssocDataAccess.GROUP_ASSOC_CD, RefCodeNames.GROUP_ASSOC_CD.ACCOUNT_OF_GROUP);
            String sqlIn = GroupAssocDataAccess.getSqlSelectIdOnly(GroupAssocDataAccess.BUS_ENTITY_ID, crit);
            crit = new DBCriteria();
            crit.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID,sqlIn);
            crit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD, RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT);
            crit.addOrderBy(BusEntityDataAccess.SHORT_DESC);
            return BusEntityDataAccess.select(conn,crit,500);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoteException(e.getMessage());
		} finally {
			closeConnection(conn);
		}
	}

    
    public UserDataVector getUiGroupUsers(int pGroupId) throws RemoteException{
        Connection conn = null;
        try {
            conn = getConnection();
            return getUiGroupUsers(conn, pGroupId);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
    }

    private UserDataVector getUiGroupUsers(Connection pConn, int pGroupId) throws Exception {

        DBCriteria crit = new DBCriteria();
        UserDataVector users = new UserDataVector();
        if (pGroupId > 0) {

            crit.addEqualTo(GroupAssocDataAccess.GROUP_ID, pGroupId);
            crit.addEqualTo(GroupAssocDataAccess.GROUP_ASSOC_CD, RefCodeNames.GROUP_ASSOC_CD.USER_OF_GROUP);

            String sqlIn = GroupAssocDataAccess.getSqlSelectIdOnly(GroupAssocDataAccess.USER_ID, crit);

            crit = new DBCriteria();
            crit.addOneOf(UserDataAccess.USER_ID, sqlIn);

            users = UserDataAccess.select(pConn, crit);

        }

        return users;

    }


}
