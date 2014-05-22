package com.cleanwise.service.api.session;

/**
 * Title:        Group
 * Description:  Remote Interface for Group Stateless Session Bean
 * Purpose:      Provides access to the table-level grop methods (add by, add date, mod by, mod date)
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 *
 */

import javax.ejb.*;
import java.rmi.*;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.value.*;

public interface Group extends javax.ejb.EJBObject
{
/**
     *  When searching for a group name, match the search string to the begining
     *  of group name found. Case sensitive match.
     */
    public final static int NAME_BEGINS_WITH = 10;
    /**
     *  When searching for a group name, match the search string to the begining
     *  of group name found. Case insensitive match.
     */
    public final static int NAME_BEGINS_WITH_IGNORE_CASE = 11;
    /**
     *  When searching for a group name, match the search string to the complete
     *  group name found. Case sensitive match.
     */
    public final static int NAME_EXACT = 20;
    /**
     *  When searching for a group name, match the search string to the complete
     *  group name found. Case insensitive match.
     */
    public final static int NAME_EXACT_IGNORE_CASE = 21;
    /**
     *  When searching for a group name, match the search string to any part of
     *  group name found. Case sensitive match.
     */
    public final static int NAME_CONTAINS = 30;
    /**
     *  When searching for a group name, match the search string to any part of
     *  group name found. Case insensitive match.
     */
    public final static int NAME_CONTAINS_IGNORE_CASE = 31;
    
    /**
     *  Flag indicating that returned vector of sites should be ordered by ids
     */
    public final static int ORDER_BY_ID = 10006;
    /**
     *  Flag indicating that returned vector of sites should be ordered by names
     */
    public final static int ORDER_BY_NAME = 10007;

    /**
     *@param the userId of the user that we want the groups for
     *@return a map with the groupId as the key and the the group name as the value
     *@throws RemoteException if an error occurs
     */
    public java.util.Collection getGroupNamesUserIsMemberOf(int pUserId) throws RemoteException;

    
  /**
     *@param the userId of the user that we want the groups for
     *@return a map with the groupId as the key and the the group name as the value
     *@throws RemoteException if an error occurs
     */
    public java.util.Map getGroupsUserIsMemberOf(int pUserId) throws RemoteException;
    
    public Map getGroupsUserIsMemberOf(int pUserId, boolean accountGroup) throws RemoteException;
    
    /**
     *@return a map with the groupId as the key and the the group name as the value
     *@throws RemoteException if an error occurs
     */
    public java.util.Map getUserGroups() throws RemoteException;
    
    public Map getUserGroups(boolean accountGroup) throws RemoteException;
    
    public Map getUserGroups(IdVector pStoreIds, String groupAssocCd) throws RemoteException;
    
    public Map getUserGroups(IdVector pStoreIds, String groupAssocCd, boolean accountGroup) throws RemoteException;
    
    public Map getUserGroups(IdVector pStoreIds,boolean accountGroup) throws RemoteException;
    
    public GroupDataVector getUserGroupsByType(int pUserId, String pGroupTypeCd) throws RemoteException;
    /**
     *@param pStoreIds. If not null, that return groups, connected with stores from pStoreIds.  
     *@return a map with the groupId as the key and the the group name as the value
     *@throws RemoteException if an error occurs
     */
    public java.util.Map getUserGroups(IdVector pStoreIds) throws RemoteException;
    
    /**
     *@param pUserId the user of the user that we want the groups for
     *@param pGroupName the group name to add this user to
     *@param pUserDoingModName the name of the admin user who is making this change
     *@throws RemoteException if an error occurs
     */
    public void addUserToGroup(int pUserId,String pGroupName,String pUserDoingModName) 
    throws RemoteException;

    
    public void addAccountsToGroup(int pGroupId, IdVector accountsIds, String pUserDoingMod)
    throws RemoteException;
    
    /**
     *@param pUserId the userId of the user that we want the groups for
     *@param pGroupId the groupId to add this user to
     *@param pUserDoingModName the name of the admin user who is making this change
     *@throws RemoteException if an error occurs
     */
    public void addUserToGroupId(int pUserId,int pGroupId,String pUserDoingModName) 
    throws RemoteException;
    
    /**
    /**
     *@param pUserId the user Id of the user that we want the groups for
     *@param pGroupName the groupName to add this user to
     *@param pUserDoingModName the name of the admin user who is making this change
     *@throws RemoteException if an error occurs
     */
    public void removeUserFromGroup(int pUserId,String pGroupName,String pUserDoingModName) 
    throws RemoteException;
    
    /**
     *@param pUserId the userId of the user that we want the groups for
     *@param pGroupId the groupId to add this user to
     *@param pUserDoingModName the name of the admin user who is making this change
     *@throws RemoteException if an error occurs
     */
    public void removeUserFromGroupId(int pUserId,int pGroupId,String pUserDoingModName) 
    throws RemoteException;
    
    /**
     *@param pGroup the group to add or update
     *@param pUserDoingModName the admin user that is adding this group
     *@returns the id of the group that was added
     *@throws RemoteException if the group could not be added or errors occured during.
     */
    public int addGroup(GroupData pGroup,String pUserDoingModName) throws RemoteException;
    
    /**
     *@param pGroupName the group name to remove (deactivate)
     *@param pUserDoingModName the admin user that is removing (deactivating) this group
     *@throws RemoteException if the group could not be removed (deactivated) or errors occured.
     */
    public void removeGroup(String pGroupName,String pUserDoingModName) throws RemoteException;
    
    /**
     *@param pGroupName the group id to remove (deactivate)
     *@param pUserDoingModName the admin user that is removing (deactivating) this group
     *@throws RemoteException if the group could not be removed (deactivated) or errors occured.
     */
    public void removeGroup(int pGroupId,String pUserDoingModName) throws RemoteException;

    
    
    
    /**
     *@param pReportName the report name to add to the group
     *@param pGroupName the group name to add this report to
     *@param pUserDoingModName the name of the admin user who is making this change
     *@throws RemoteException if an error occurs
     */
    public void addReportToUserGroup(String pReportName,String pGroupName,String pUserDoingModName) 
    throws RemoteException;

    
    /**
     *@param pReportName the report name to add to the group
     *@param pGroupId the groupId to add this report to
     *@param pUserDoingModName the name of the admin user who is making this change
     *@throws RemoteException if an error occurs
     */
    public void addReportToUserGroupId(String pReportName,int pGroupId,String pUserDoingModName) 
    throws RemoteException;
    
    /**
     *Will update the association table to reflect a group having identified by pGroupId
     *having the relationship to the reports identified by the List of pReportNames.  This
     *will both add and remove data from the associations.  So if an empty list is passed in
     *all associations beetween reports and the supplied group id will be removed.
     *@param List a list of String Objects representing the report names.  Invalid reports names
     *  will trigger a RemoteException
     *@param int the group id of the group
     *@param String the user making this change
     *@throws RemoteException if an error occurs
     */
    public void updateReportToGroupAssociations(List pReportNames,int pGroupId,String pUserDoingMod)
    throws RemoteException;
    
    
    /**
     *Will update the association table to reflect a group having identified by pGroupId
     *having the relationship to the application functions identified by the List of pApplicationFunctions.
     *This will both add and remove data from the associations.  So if an empty list is passed in
     *all associations beetween application functions and the supplied group id will be removed.
     *@param List a list of String Objects representing the application functions.
     *@param int the group id of the group
     *@param String the user making this change
     *@throws RemoteException if an error occurs
     */
    public void updateApplicationFunctionToGroupAssociations(List pApplicationFunctions,
    int pGroupId,String pUserDoingMod)throws RemoteException;
    
    /**
     *@param pUserId the user id of the user in question
     *@returns a map with the association type as the key and an list of the names of 
        the function/group as the values.
     *@throws RemoteException if an error occurs
     */
    public Map getAllValidUserGroupAssociations(int pUserId)
    throws RemoteException;
    
    /**
     *@param pGroupId the group id of the group in question
     *@returns a map with the association type as the key and an list of the names of 
        the function/group as the values.
     *@throws RemoteException if an error occurs
     */
    public Map getAllValidGroupAssociations(int pGroupId)
    throws RemoteException;
    
    /**
     *@param pGroupName the name or part of the name to match
     *@param pMatch the match type to use
     *@param pOrder the ordering of the returned GroupDataVector
     *returns GroupDataVector matching the given criteria
     *@throws RemoteException if an error occurs
     */
    public GroupDataVector getGroups(GroupSearchCriteriaView pGroupCrit,int pMatch,int pOrder) 
    throws RemoteException;
    
    /**
     *returns GroupDataVector of all the groups
     *@throws RemoteException if an error occurs
     */
    public GroupDataVector getAllGroups() throws RemoteException;

    public GroupDataVector getAllGroupsForStore(int pStoreId) throws RemoteException;
    
    /**
     *@param pGroupId the group id
     *@returns a populated GroupData object
     *@throws DataNotFoundException if group with pGroupId doesn't exist
     *@throws RemoteException if an error occurs
     */
    public GroupData getGroupDetail(int pGroupId) throws RemoteException, DataNotFoundException;
 
    /*
     *@param pGroupIds the group ids
     *@returns a populated GroupDataVector object
     *@throws RemoteException if an error occurs
     */
    public GroupDataVector getGroupsByIds(IdVector pGroupIds) throws RemoteException  ;
    
    /**
     *@param GroupSearchCriteria the search criteria to use when retrieving the results
     *@param pMatch the match type to use
     *@param pOrderBy the order by to use
     *@returns a populated UserDataVector with all the users that are a member of the supplied Group
     *@throws RemoteException if an error occurs
     */
    public UserDataVector getUsersForGroup(GroupSearchCriteriaView searchCriteria,int pMatch,
    int pOrderBy) throws RemoteException;
    
    /**
     *@param GroupSearchCriteria the search criteria to use when retrieving the results
     *@param pMatch the match type to use
     *@param pOrderBy the order by to use
     *@returns a populated GenericReportDataVector with all the reports that are a member of the supplied Group
     *@throws RemoteException if an error occurs
     */
    public GenericReportDataVector getReportsForGroup(GroupSearchCriteriaView searchCriteria,
    int pMatch,int pOrderBy) throws RemoteException;
    
    /**
     *Returns a BusEntityDataVector of the appropriate type (if specified) that are
     *members of the specified group.
     *
     *@param int pGroupId the group id of the group we want the members of.
     *@param String pOptionalBusEntityType the bus entity types that we want returned.
     *@returns BusEntityDataVector possibly empty of all the BusEntities that meet 
     *  the specified criteria.
     *@throws RemoteException if an error occurs
     */
    public BusEntityDataVector getBusEntitysForGroup(int pGroupId, String pOptionalBusEntityType) 
    throws RemoteException;

/**
     * Returns a BusEntityDataVector of the appropriate type (if specified) that are
     * members of the specified group.
     * @param pGroupId
     * @param pOptionalBusEntityType
     * @return
     * @throws RemoteException
     */
    public BusEntityDataVector getStoresForGroup(int pGroupId, String pOptionalBusEntityType)
            throws RemoteException;    
    /**
     *Adds the specified users to the specified group.
     *@param IdVector the user ids of the users to add to this group
     *@param int the group id to add these users to
     *@param String the users user name who is doing the modification
     *@throws RemoteException if an error occurs
     */
    public void addUsersToGroup(int pGroupId,IdVector pUserIds, String pUserDoingMod)
    throws RemoteException;
    
    /**
     *Removes the specified users to the specified group.
     *@param IdVector the user ids of the users to remove from this group
     *@param int the group id to remove these users from
     *@throws RemoteException if an error occurs
     */
    public void removeUsersFromGroup(int pGroupId,IdVector pUserIds)
    throws RemoteException;
    
    /**
     *Adds the specified bus entities to the specified group.
     *@param IdVector the bus entity ids of the bus entities to add to this group
     *@param int the group id to add these bus entities to
     *@param String the users user name who is doing the modification
     *@throws RemoteException if an error occurs
     */
    public void addBusEntitiesToGroup(int pGroupId,IdVector pBusEntityIds, String pUserDoingMod)
    throws RemoteException;

    /**
     *Adds the specified bus entities to the specified group.
     *@param pGroupId      - the group id to add these bus entities to
     *@param storesIds     - the bus entity ids of the bus entities to add to this group
     *@param pUserDoingMod - the users user name who is doing the modification
     *@throws RemoteException if an error occurs
     */    
    public void addStoresToGroup(int pGroupId, IdVector storesIds, String pUserDoingMod)
            throws RemoteException;
    /**
     *Removes the specified bus entities to the specified group.
     *@param IdVector the bus entities ids of the bus entities to remove from this group
     *@param int the group id to remove these bus entities from
     *@throws RemoteException if an error occurs
     */
    public void removeBusEntitiesFromGroup(int pGroupId,IdVector pBusEntityIds) 
    throws RemoteException;

    public java.util.ArrayList getUserGroupsReport(int pUserId) throws RemoteException;

    public GroupDataVector getUIGroups() throws RemoteException;

    public GroupData getUserUiGroup(int pUserId) throws RemoteException;

    public GroupData getStoreUiGroup(int pStoreId) throws RemoteException;

    public GroupData getAccountUiGroup(int pAccountId) throws RemoteException;

    /**
     * Adds the specified bus entities to the specified group.
     * This method is similar to a method "addBusEntitiesToGroup".
     * Unlike a method "addBusEntitiesToGroup" this method checks existing associations between stores and 'STORE_UI' groups.
     * This method generates an exception if the store has more than one associations with 'STORE_UI' groups.
     */
    public void addBusEntitiesToStoreUiGroup(int pGroupId, IdVector pBusEntityIds, String pUserDoingMod) throws RemoteException;

    /**
     * Adds the specified bus entities to the specified group.
     * This method is similar to a method "addBusEntitiesToGroup".
     * Unlike a method "addBusEntitiesToGroup" this method checks existing associations between accounts and 'ACCOUNT_UI' groups.
     * This method generates an exception if the account has more than one associations with 'ACCOUNT_UI' groups.
     */
    public void addBusEntitiesToAccountUiGroup(int pGroupId, IdVector pBusEntityIds, String pUserDoingMod) throws RemoteException;


    public UiGroupDataViewVector getUiGroupDataViewVector() throws RemoteException;

    public List<String> getGroupAssociationsStartWith(String pName, int pMaxRows) throws RemoteException;

    public UiGroupDataViewVector searchUiGroupIdsByAssocName(String pAssocName) throws RemoteException;

    public BusEntityDataVector getAccountsForGroup(int pGroupId)throws RemoteException;

    public UserDataVector getUiGroupUsers(int pGroupId) throws RemoteException;

    public void removeAccountsFromGroup(int pGroupId, IdVector pAccountIds) throws RemoteException;
}
