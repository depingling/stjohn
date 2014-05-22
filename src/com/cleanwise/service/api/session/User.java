package com.cleanwise.service.api.session;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.DuplicateNameException;
import com.cleanwise.service.api.util.InvalidAccessTokenException;
import com.cleanwise.service.api.util.InvalidLoginException;
import com.cleanwise.service.api.value.BusEntityDataVector;
import com.cleanwise.service.api.value.AllStoreDataVector;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.LdapItemData;
import com.cleanwise.service.api.value.NscUsViewVector;
import com.cleanwise.service.api.value.RefCdDataVector;
import com.cleanwise.service.api.value.SiteDataVector;
import com.cleanwise.service.api.value.UserAccountRightsViewVector;
import com.cleanwise.service.api.value.UserAcessTokenViewData;
import com.cleanwise.service.api.value.UserAssocData;
import com.cleanwise.service.api.value.UserAssocDataVector;
import com.cleanwise.service.api.value.UserData;
import com.cleanwise.service.api.value.UserDataVector;
import com.cleanwise.service.api.value.UserInfoData;
import com.cleanwise.service.api.value.UserInfoDataVector;
import com.cleanwise.service.api.value.UserPropertyData;
import com.cleanwise.service.api.value.UserPropertyDataVector;
import com.cleanwise.service.api.value.UserSearchCriteriaData;

import com.cleanwise.service.api.value.LoginInfoView;
/**
 *  The <code>User</code> interface provides the methods to add and update
 *  users. It also provides the login method to authenticate user names and
 *  passwords.
 *
 *@author     durval
 *@created    August 28, 2001
 */
public interface User extends javax.ejb.EJBObject {
    /**
     *  When searching for a user name, match the search string to the begining
     *  of user name found. Case sensitive match.
     */
    public final static int NAME_BEGINS_WITH = 10;
    /**
     *  When searching for a user name, match the search string to the begining
     *  of user name found. Case insensitive match.
     */
    public final static int NAME_BEGINS_WITH_IGNORE_CASE = 11;
    /**
     *  When searching for a user name, match the search string to the complete
     *  user name found. Case sensitive match.
     */
    public final static int NAME_EXACT = 20;
    /**
     *  When searching for a user name, match the search string to the complete
     *  user name found. Case insensitive match.
     */
    public final static int NAME_EXACT_IGNORE_CASE = 21;
    /**
     *  When searching for a user name, match the search string to any part of
     *  user name found. Case sensitive match.
     */
    public final static int NAME_CONTAINS = 30;
    /**
     *  When searching for a user name, match the search string to any part of
     *  user name found. Case insensitive match.
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
     *  login the userName
     *
     *@param  pKey                    the reference of LdapItemData
     *@param  pSiteId                 the application site identifier
     *@return                         UserData
     *@throws  RemoteException        Required by EJB 1.0
     *@throws  InvalidLoginException
     */
    public UserData login(LdapItemData pKey, String pSiteId)
             throws RemoteException, InvalidLoginException, InvalidAccessTokenException;

    public UserData login(LdapItemData pKey, String pSiteId, boolean checkPwd)
    throws RemoteException, InvalidLoginException;

    /**
     *  Send an email mesage to the user containing their password. Optionally,
     *  auto-generate a new password.
     *
     *@param  pUserId
     *@param  pSubject                   subject line for the email
     *@param  pMessageStart              body of the email before the password
     *@param  pMessageEnd                body of the email after the password
     *@param  pGeneratePassword          boolean, if true, create a new
     *      password.
     *@exception  RemoteException        Description of Exception
     *@exception  InvalidLoginException  Description of Exception
     *@exception  DataNotFoundException  Description of Exception
     */
    public void sendPasswordEmail(boolean pGeneratePassword,
            int pUserId, String pSubject,
            String pMessageStart,
            String pMessageEnd)
             throws RemoteException, InvalidLoginException,
            DataNotFoundException;


    /**
     *  determines if the user effective date is within the current date.
     *
     *@param  pUserName         the userName of the user
     *@param  pEffDate          the user effective date
     *@param  pNow              the current date
     *@return                   true if the user effective date is equal to or
     *      after the current date.
     *@throws  RemoteException  Required by EJB 1.0
     */
    public boolean checkUserEffDate(String pUserName, Date pEffDate, Date pNow)
             throws RemoteException;


    /**
     *  determines if the user expiration date is within the current date.
     *
     *@param  pUserName         the userName of the user
     *@param  pExpDate          the user expiration date
     *@param  pNow              the current date
     *@return                   true if the user expiration date is equal to or
     *      before the current date.
     *@throws  RemoteException  Required by EJB 1.0
     */
    public boolean checkUserExpDate(String pUserName, Date pExpDate, Date pNow)
             throws RemoteException;


    /**
     *  Gets the LdapItem information to be used by the request.
     *
     *@param  pUserName            the user name
     *@param  pSiteId              a <code>String</code> value
     *@return                      LdapItemData
     *@exception  RemoteException  Required by EJB 1.0
     */
    public LdapItemData getLdapItem(String pUserName, String pSiteId)
             throws RemoteException;

    /**
     *  Gets user data vector
     *
     *@param  pUserIds a collection of user ids
     *@return                   BusEntityUserDataVector
     *@throws  RemoteException  Required by EJB 1.0
     */
    public UserDataVector getUsersCollection(IdVector pUserIds)
	throws RemoteException;

    /**
     *  Gets the users associated with the business entity.
     *
     *@param  pBusEntityId      the business entity identifier.
     *@param  pOptionalWorkflowRoleCd, If this parameter is
     *  null, then it is ignored.  Otherwise, this method
     *  will get all users for this site with the specified
     *  role (example: ORDER APPROVER).
     *
     *@return                   UserDataVector
     *@throws  RemoteException  Required by EJB 1.0
     */
    public UserDataVector getUsersCollectionByBusEntity
	(int pBusEntityId, String pOptionalWorkflowRoleCd)
	throws RemoteException;


    /**
     *  Gets the business entity user vector values to be used by the request.
     *
     *@param  pBusEntityId      the business entity identifier.
     *@param  pOptionalWorkflowRoleCd, If this parameter is
     *  null, then it is ignored.  Otherwise, this method
     *  will get all users for this site with the specified
     *  role (example: ORDER APPROVER).
     *@param usertype  the user type to be excluded
     *@return                   BusEntityUserDataVector
     *@throws  RemoteException  Required by EJB 1.0
     */
    public UserDataVector getUsersCollectionByBusEntityExcludingType
    (int pBusEntityId, String usertype, String pOptionalWorkflowRoleCd)
    throws RemoteException ;

    /**
     *  Gets the business entity user vector values to be used by the request.
     *
     *@param pBusEntityIdVector      the collection of business entity identifiers.
     *@param pName
     *@param usertype                the usertype to be excluded
     *@param pMatch
     *@param pOrder
     *@return                   BusEntityUserDataVector
     *@throws  RemoteException  Required by EJB 1.0
     */
    public UserDataVector getUsersCollectionByBusEntityCollectionExcludingType(IdVector pBusEntityIdVector,
    String pName,
    String usertype,
    int pMatch,
    int pOrder)
    throws RemoteException ;

    /**
     *  Gets the business entity user vector values to be used by the request.
     *
     *@param  pBusEntityIdVector      the vector of business entity identifiers.
     *@param  pOptionalWorkflowRoleCd, If this parameter is
     *  null, then it is ignored.  Otherwise, this method
     *  will get all users for this site with the specified
     *  role (example: ORDER APPROVER).
     *
     *@return                   BusEntityUserDataVector
     *@throws  RemoteException  Required by EJB 1.0
     */
    public UserDataVector getUsersCollectionByBusEntityCollection
	(IdVector pBusEntityIdVector, String pOptionalWorkflowRoleCd)
	throws RemoteException;


    /**
     *  Gets the business entity user vector values to be used by the request.
     *
     *@param pBusEntityId      the business entity identifier.
     *@param pName
     *@param pMatch
     *@param pOrder
     *@return                   BusEntityUserDataVector
     *@throws  RemoteException  Required by EJB 1.0
     */
    public UserDataVector getUsersCollectionByBusEntity(int pBusEntityId,
							String pName,
							int pMatch,
							int pOrder)
	throws RemoteException;

   /**
     *  Gets the business entity user vector values to be used by the request.
     *
     *@param pBusEntityIdVector      the collection of business entity identifiers.
     *@param pName
     *@param pMatch
     *@param pOrder
     *@return                   BusEntityUserDataVector
     *@throws  RemoteException  Required by EJB 1.0
     */
    public UserDataVector getUsersCollectionByBusEntityCollection (IdVector pBusEntityIdVector,
							String pName,
							int pMatch,
							int pOrder)
	throws RemoteException ;

    /**
     *  Gets the upt to <code>pReturnLimit</code> user values starting with the
     *  id in <code>pStartUserId</code> .
     *
     *@param  pStartUserId         an <code>int</code> value, specifies the
     *      value at which to start the serach.
     *@param  pReturnLimit         an <code>int</code> value, specifies the
     *      maximum number of entries to be returned.
     *@return                      UserDataVector
     *@exception  RemoteException  Required by EJB 1.0
     */
    public UserDataVector getUsersCollection(int pStartUserId, int pReturnLimit)
             throws RemoteException;


    /**
     *  Gets the user vector values to be used by the request.
     *
     *@param  pUserName            a <code>String</code> value
     *@param  pNameMatchType       an <code>int</code> value, expected values
     *      are: User.NAME_
     *@return                      UserDataVector
     *@exception  RemoteException  Required by EJB 1.0
     */
    public UserDataVector getUsersCollectionByName(String pUserName,
            int pNameMatchType, int pOptionalAccountId)
             throws RemoteException;



    /**
     *Returns a user that matches the given user name.  If the user name does not exist a data not found exception is thrown.
     */
    public UserData getUserByName
    (String pUserName, int pStoreId)
    throws RemoteException, DataNotFoundException;

    /**
     *  Gets the user vector values to be used by the request.
     *
     *@param  pUserType            a <code>String</code> value
     *@return                      UserDataVector
     *@exception  RemoteException  Required by EJB 1.0
     */
    public UserDataVector getUsersCollectionByType(String pUserType)
	throws RemoteException;


    /**
     *  Gets the business entity user vector values to be used by the request.
     *
     *@param pSearchCriteria      the UserSearchCriteria.
     *@return                   BusEntityUserDataVector
     *@throws  RemoteException  Required by EJB 1.0
     */
    public UserDataVector getUsersCollectionByCriteria(UserSearchCriteriaData pSearchCriteria)
	throws RemoteException;


    /**
     *  Gets the user informatiom to be used by the request.
     *
     *@param  pUserID                    an <code>int</code> value
     *@return                            UserData
     *@exception  DataNotFoundException  if an error occurs
     *@exception  RemoteException        Required by EJB 1.0
     */
    public UserData getUser(int pUserID)
             throws DataNotFoundException, RemoteException;


    /**
     *  Gets the user informatiom to be used by the request.
     *
     *@param  pUserID                    an <code>int</code> value
     *@return                            UserInfoData
     *@exception  DataNotFoundException  if an error occurs
     *@exception  RemoteException        Required by EJB 1.0
     */
    public UserInfoData getUserContact(int pUserID)
             throws DataNotFoundException, RemoteException;


    /**
     *  Adds the User information values to be used by the request.
     *
     *@param  pAddUserData         the User data.
     *@exception  RemoteException  Required by EJB 1.0
     */
    public void addUser(UserData pAddUserData)
             throws RemoteException;


    /**
     *  Updates the user information values to be used by the request.
     *
     *@param  pUpdateUserData      the user data.
     *@param  pUserId              the user identifier.
     *@exception  RemoteException  Required by EJB 1.0
     */
    public void updateUser(UserData pUpdateUserData, int pUserId)
             throws RemoteException;
    public void updateUser(UserData pUpdateUserData, int pUserId, boolean isResetPassword)
    throws RemoteException;

    public void updateUserGroups( int pUserId,
				  java.util.ArrayList pGroupIds,
				  String pModUser)
             throws RemoteException;


    /**
     *  Updates the user information values to be used by the request.
     *
     *@param  pUpdateUserData      the user contact info data.
     *@param  pUserId              the user identifier.
     *@exception  RemoteException  Required by EJB 1.0
     */
    public void updateContact(UserInfoData pUpdateUserData, int pUserId)
             throws RemoteException;


    /**
     *  Describe <code>addUserInfo</code> method here.
     *
     *@param  pUserInfoData               a <code>UserInfoData</code> value
     *@return                             a <code>UserInfoData</code> value
     *@exception  RemoteException         if an error occurs
     *@exception  DuplicateNameException  Description of Exception
     */
    public UserInfoData addUserInfo(UserInfoData pUserInfoData)
             throws RemoteException, DuplicateNameException;


    /**
     *  Updates the user information values to be used by the request.
     *
     *@param  pUserInfoData               the UserInfoData user info data.
     *@return                             a <code>UserInfoData</code> value
     *@exception  DuplicateNameException  Description of Exception
     *@throws  RemoteException            Required by EJB 1.0
     */
    public UserInfoData updateUserInfo(UserInfoData pUserInfoData)
             throws RemoteException, DuplicateNameException;

    /**
     *  Updates the user profile. Similar to updateUserInfo, but doesn't save user configuration
     *
     *@param  pUserInfoData               the UserInfoData user info data.
     *@return                             a <code>UserInfoData</code> value
     *@exception  DuplicateNameException  Description of Exception
     *@throws  RemoteException            Required by EJB 1.0
     */
    public UserInfoData updateUserProfile(UserInfoData pUserInfoData)
    throws RemoteException, DuplicateNameException;


    /**
     *  Adds the User Property information values to be used by the request.
     *
     *@param  pAddUserPropertyData  the User Property data.
     *@exception  RemoteException   Required by EJB 1.0
     */
    public void addUserProperty(UserPropertyData pAddUserPropertyData)
             throws RemoteException;


    /**
     *  Updates the user Property information values to be used by the request.
     *
     *@param  pUpdateUserPropertyData  the user Property data.
     *@param  pPropertyId              the property identifier.
     *@param  pUserId                  the user identifier.
     *@exception  RemoteException      Required by EJB 1.0
     */
    public void updateUserProperty(UserPropertyData pUpdateUserPropertyData,
            int pPropertyId, int pUserId)
             throws RemoteException;
    
    public void updateUserProperty(int pUserId, int pBusEntityId, String pPropertyTypeCd)
    throws RemoteException;

    /**
     *  Gets the user property vector values to be used by the request.
     *
     *@param  pUserId           the user identifier.
     *@return                   UserPropertyDataVector
     *@throws  RemoteException  Required by EJB 1.0
     */
    public UserPropertyDataVector getUserPropertiesCollection(int pUserId)
             throws RemoteException;


    /**
     *  Gets the user property informatiom to be used by the request.
     *
     *@param  pUserID              an <code>int</code> value
     *@param  pPropertyId          the property identifier.
     *@return                      UserPropertyData
     *@exception  RemoteException  Required by EJB 1.0
     */
    public UserPropertyData getUserProperty(int pUserID, int pPropertyId)
             throws RemoteException;


    /**
     *Returns true if the specified user id has a valid relationship to the specified site id, false
     *otherwise.
     *@param pUserId the user id
     *@param pSiteId the site id
     *@throws RemoteException
     */
    public boolean isSiteOfUser(int pSiteId, int pUserId)
    throws RemoteException;

    /**
     *Updates the most recent visit date/time for a user/site pair.
     *@param pSiteId the site id
     *@param pUserId the user id
     *@param pUserName the user name
     *@param visitInfo the date/time of the most recent user visit to the site.
     *@throws RemoteException
     */
    public void updateLastUserVisit(int pSiteId, int pUserId, String pUserName, Date visitInfo)
    throws RemoteException;

    /**
     *  Gets the array-like business entity association vector
     *  values to be used
     *  by the request.
     *
     *@param  pUserId           Description of Parameter
     *@return                   The SiteCollection value
     *@throws  RemoteException  Required by EJB 1.0
     */
    public BusEntityDataVector getSiteCollection(int pUserId)
             throws RemoteException;
    public BusEntityDataVector getSiteCollection(int pUserId,
      int pOptionalMaxSites)
             throws RemoteException;


    /**
     *  Gets the array-like business entity association vector values to be used
     *  by the request.
     *
     *@param  pUserId           the user Id
     *@param  pNameFilter       string to filter Site on shortDesc.
     *@return                   BusEntityDataVector object of BusEntityData
     *      Account type objects
     *@throws  RemoteException  Required by EJB 1.0
     */
    public BusEntityDataVector getSiteCollection(int pUserId, String pNameFilter)
             throws RemoteException;


    /**
     *  Gets the Site collection for the user. Applies of filter for site name
     *  (contains ignore case) Picks up only BusEntityData, BusEntityAssocData
     *  and shipping AddressData objects
     *
     *@param  pUserId              Description of Parameter
     *@param  pFilter              Filter string. If is null or empty does not
     *      apply
     *@return                      The SiteDataVector value
     *@exception  RemoteException  Description of Exception
     */

    public SiteDataVector getSiteDescCollection(int pUserId,
            String pFilter)
             throws RemoteException;


    /**
     *  Gets the array-like business entity association vector values to be used
     *  by the request.
     *
     *@param  pBusEntityTypeCd  business entity type code (STORE, ACCOUNT, SITE)
     *@param  pUserId           Description of Parameter
     *@return                   The BusEntityCollection value
     *@throws  RemoteException  Required by EJB 1.0
     */
    public BusEntityDataVector getBusEntityCollection(int pUserId, String pBusEntityTypeCd)
             throws RemoteException;


    /**
     *  Gets the array-like business entity association vector values to be used
     *  by the request.
     *
     *@param  pUserId           the user id
     *@param  pBusEntityTypeCd  business entity type code (STORE, ACCOUNT, SITE)
     *@param  pNameFilter       shortDesc filter
     *@return                   The BusEntityCollection value
     *@throws  RemoteException  Required by EJB 1.0
     */
    public BusEntityDataVector getBusEntityCollection(int pUserId,
            String pBusEntityTypeCd,
            String pNameFilter)
             throws RemoteException;


    /**
     *  Adds the catalog association information values to be used by the
     *  request.
     *
     *@param  pBusEntityId               the business entity id.
     *@param  pCatalogId                 The feature to be added to the
     *      UserAssoc attribute
     *@param  pType                      The feature to be added to the
     *      UserAssoc attribute
     *@return                            new UserAssocData()
     *@exception  DataNotFoundException  Description of Exception
     *@throws  RemoteException           Required by EJB 1.0,
     *      DataNotFoundException
     */
    public UserAssocData addUserAssoc(int pCatalogId, int pBusEntityId, String pType)
             throws RemoteException, DataNotFoundException;


    /**
     *  Adds a feature to the UserAssoc attribute of the User object
     *
     *@param  con                        The feature to be added to the
     *      UserAssoc attribute
     *@param  pUserId                    The feature to be added to the
     *      UserAssoc attribute
     *@param  pBusEntityId               The feature to be added to the
     *      UserAssoc attribute
     *@param  pType                      The feature to be added to the
     *      UserAssoc attribute
     *@return                            Description of the Returned Value
     *@exception  SQLException           Description of Exception
     *@exception  DataNotFoundException  Description of Exception
     *@exception  RemoteException        Description of Exception
     */
    public UserAssocData addUserAssoc(Connection con, int pUserId, int pBusEntityId, String pType)
             throws SQLException, DataNotFoundException, RemoteException;


    /**
     *  Adds a feature to the UserAssoc attribute of the User object
     *
     *@param  con                  The feature to be added to the UserAssoc
     *      attribute
     *@param  pUserAssoc           The feature to be added to the UserAssoc
     *      attribute
     *@return                      Description of the Returned Value
     *@exception  RemoteException  Description of Exception
     *@exception  SQLException     Description of Exception
     */
    public UserAssocData addUserAssoc(Connection con, UserAssocData pUserAssoc)
             throws RemoteException, SQLException;


    /**
     *  Removes the user association information values
     *
     *@param  pUserId           the user id.
     *@param  pBusEntityId      the business entity id.
     *@throws  RemoteException  Required by EJB 1.0
     */
    public void removeUserAssoc(int pUserId, int pBusEntityId)
             throws RemoteException;


    /**
     *  Determins wherther exists the user-bus entity association
     *
     *@param  pUserId
     *@param  pBusEntityId
     *@return                   boolean true if found
     *@throws  RemoteException
     */
    public boolean doesUserAssocExist(int pUserId, int pBusEntityId)
             throws RemoteException;


    /**
     *  Get all the users for a given business entity.
     *
     *@param  pBusEntityId         the Id of the user busEntity. If zero, all
     *      users without regard to the busEntity, will be returned
     *@param  pOrder               one of ORDER_BY_ID, ORDER_BY_NAME
     *@return                      a <code>UserDataVector</code> with all users.
     *@exception  RemoteException  if an error occurs
     */
    public UserDataVector getAllUsers(int pBusEntityId, int pOrder)
             throws RemoteException;

    /**
     *  Get all active users for the given business entity.
     *
     *@param  pBusEntityId         the Id of the user busEntity.
     *@param  pOrder               one of ORDER_BY_ID, ORDER_BY_NAME
     *@return                      a <code>UserDataVector</code>  object
     *@exception  RemoteException
     */
    public UserDataVector getAllActiveUsers(int pBusEntityId, int pOrder)
    throws RemoteException;


    /**
     *  Get all users that match the given name. The arguments specify whether
     *  the name is interpreted as a pattern or exact match.
     *
     *@param  pName                a <code>String</code> value with user name or
     *      pattern
     *@param  pBusEntityId         the id of the business entity. If nonzero
     *      will only return a users associated with that business entity.
     *      Otherwise will return all matching users.
     *@param  pMatch               one of EXACT_MATCH, BEGINS_WITH, CONTAINS,
     *      EXACT_MATCH_IGNORE_CASE, BEGINS_WITH_IGNORE_CASE and
     *      CONTAINS_IGNORE_CASE.
     *@param  pOrder               one of ORDER_BY_ID, ORDER_BY_NAME
     *@return                      a <code>UserDataVector</code> of matching
     *      accounts
     *@exception  RemoteException  if an error occurs
     */
    public UserDataVector getUsersByName(String pName, int pBusEntityId,
            int pMatch, int pOrder)
             throws RemoteException;


    /**
     *  Find all the users somehow linked to this store. This will list all
     *  users directly linked to the store, users linked to the store's
     *  accounts, and users linked to the sites for the store's accounts.
     *
     *@param  pStoreId             Description of Parameter
     *@param  pOrder               Description of Parameter
     *@return                      The AllStoreUsers value
     *@exception  RemoteException  Description of Exception
     */
    public UserDataVector getAllStoreUsers
            (int pStoreId, int pOrder)
             throws RemoteException;


    /**
     *  Get all store users that match the given name. The arguments specify
     *  whether the name is interpreted as a pattern or exact match.
     *
     *@param  pName                a <code>String</code> value with user name or
     *      pattern
     *@param  pMatch               one of EXACT_MATCH, BEGINS_WITH, CONTAINS,
     *      EXACT_MATCH_IGNORE_CASE, BEGINS_WITH_IGNORE_CASE and
     *      CONTAINS_IGNORE_CASE.
     *@param  pOrder               one of ORDER_BY_ID, ORDER_BY_NAME
     *@param  pStoreId             Description of Parameter
     *@return                      a <code>UserDataVector</code> of matching
     *      accounts
     *@exception  RemoteException  if an error occurs
     */
    public UserDataVector getStoreUsersByName(String pName, int pStoreId,
            int pMatch, int pOrder)
             throws RemoteException;


    /**
     *  Get all the customers for a given account. The method will find all
     *  CUSTOMERS belonging to all the sites for the account specified.
     *
     *@param  pAccountId
     *@param  pOrder               one of ORDER_BY_ID, ORDER_BY_NAME
     *@param  pExcludeSite         Description of Parameter
     *@return                      a <code>UserDataVector</code>
     *@exception  RemoteException  if an error occurs
     */
    public UserDataVector getAllAccountCustomers
            (int pAccountId, int pOrder, int pExcludeSite)
             throws RemoteException;

     /**
     *  Updates a feature to the UserAssoc attribute of the UserBean object
     *
     *@param  pUserId                    The feature to be added to the
     *      UserAssoc attribute
     *@param  pBusEntityId               The feature to be added to the
     *      UserAssoc attribute
     *@param  pType                      The feature to be added to the
     *      UserAssoc attribute
     *@return                            Description of the Returned Value
     *@exception  SQLException           Description of Exception
     *@exception  DataNotFoundException  Description of Exception
     *@exception  RemoteException        Description of Exception
     */
    public int updateUserAssoc(int pUserId, int pBusEntityId, String pType)
             throws SQLException, DataNotFoundException, RemoteException;

    /**
     *  Creates new user using another user as template
     *
     *@param  pUserId      the template user id
     *@param  pAdmin     the current user
     *@return created user id
     *@exception  RemoteException        Description of Exception
     *@exception  DataNotFoundException  Description of Exception
     */
    public int createUserClone(int pUserId, String pAdmin)
    throws DataNotFoundException, RemoteException;
    public int createUserClone(int pUserId, String pAdmin,
			       String pNewUserTypeCd,
			       String pNewUserStatusCd)
    throws DataNotFoundException, RemoteException;

    /**
     *  Assigns all account sites to the user
     *
     *@param  pUserId      the template user id
     *@param  pAccountId the account identifier
     *@param  pAdmin     the current user
     *@return created user id
     *@exception  RemoteException        Description of Exception
     *@exception  DataNotFoundException  Description of Exception
     */
    public void assignAllSites(int pUserId, int pAccountId, String pAdmin)
    throws DataNotFoundException, RemoteException;

    /**
     *  Gets bus_entity account object sites assigned to the user
     *
     *@param  pUserId      the  user id
     *@param pStoreId   the store id
     *@param pGetInactiveFl filters out inactive accounts if false
     *@return a set of BusEnityData objects
     *@exception  RemoteException        Description of Exception
     */
    public IdVector getUserAccountIds(int pUserId, int pStoreId, boolean pGetInactiveFl)
    throws RemoteException;

    /**
     *  Gets bus_entity account object sites assigned to the user
     *
     *@param  pUserId      the  user id
     *@return a set of BusEnityData objects
     *@exception  RemoteException        Description of Exception
     */
    public BusEntityDataVector getUserSiteAccounts(int pUserId)
    throws RemoteException;

    public  UserAccountRightsViewVector  getUserAccountRights(int pUserId)
    throws RemoteException;

    public void updateUserAccountRights(int pUserId,
                                        int pAccountId,
                                        String pRights,
                                        String pUpdateUserName )
        throws RemoteException ;

    /**
     *  Gets user rights vector
     *
     *@param  pUserId      the  user id
     *@param  pAccountIdV a set of accounts
     *@return a set of UserAccountRightsView objects
     *@exception  RemoteException
     */
    public  UserAccountRightsViewVector  getUserAccountRights(int pUserId, IdVector pAccountIdV)
        throws RemoteException ;

    
    public UserAcessTokenViewData createAccessToken(LdapItemData pKey)
    throws RemoteException, InvalidLoginException;
    
    public UserAcessTokenViewData createAccessToken(LdapItemData pKey, boolean checkPwd)
    throws RemoteException, InvalidLoginException;

    
    /**
     *Creates a valid access token.  An access token will allow a user to log into our site
     *without supplying a username and password, instead they supply the access token and
     *not supply the user name and password.  This method will validate the user name and
     *optionally the password that was supplied with the LdapItemData.
     *@param pKey the user credentials
     *@param checkPwd if true the password will be validated, otherwise the password will not be checked
     *@param busEntityId the store id to associate this access token to 
     */
    public UserAcessTokenViewData createAccessToken(LdapItemData pKey, boolean checkPwd, int busEntityId)
    throws RemoteException, InvalidLoginException;

    public java.util.ArrayList getBilltoCollection(int pUserId)
	throws RemoteException;


    /**
     * Associates specified bus entity ids to the supplied user.
     *@param pUserId         the user id
     *@param busEntityIds    the bus entity ids to associate to the user
     *@param busEntityTypeCd used to determine the association type, no actual checking is done
     */
    public void addBusEntityAssociations(int pUserId, IdVector busEntityIds, String busEntityTypeCd, String userName)
    throws RemoteException;


    /**
     *Removes associates between the specifed user and the supplied list of bus entity ids
     *@param pUserId         the user id
     *@param busEntityIds    the bus entity ids to "de"associate to the user
     */
    public void removeBusEntityAssociations(int pUserId, IdVector busEntityIds)
    throws RemoteException;

  /**
   *Gets user types, which the adminstrator may manage
   *@param pAdminType the of the administrator user
   *@return a set of RefCdData objects
   */
  public RefCdDataVector getManageableUserTypes(String pAdminType)
  throws RemoteException;

  /**
     *Associates specified user with all accounts of the store
     *@param pUserId         the user id
     *@param pStoreId   the store id
     *@param userName  the admininstrator name
     *@param pAccAdminId  the admininstrator Id ( for account admin > 0, otherwise -1)
     */
    public void configureAllAccounts(int pUserId, int pStoreId, String userName, int pAccAdminId)
    throws RemoteException;

    public void configureAllAccounts(int pUserId, int pStoreId, String userName)
    throws RemoteException;

    /**
     * Returns related distributor ids using both orders and the catalogs.  Goes throughthe sites that the user has access
     * to to figure out what distributors to retrieve.
     * @param pUserId the user id
     * @return a populated BusEntityDataVector
     * @throws RemoteException
     */
    public BusEntityDataVector getDistributorBusEntitiesForReports(int pUserId)
    throws RemoteException;

    /**
     * Returns RefCdDataVector of functions which are autorized for user
     * @param userId
     * @param userTypeCd
     * @return RefCdDataVector
     * @throws RemoteException
     */
    public RefCdDataVector getAuthorizedFunctions(int userId, String userTypeCd) throws RemoteException;

     /**
     *  Gets the user informatiom to be used by the request.
     *
     *@param  pUserId  an <code>int</code> value
     *@param  pStoreIds   an <code>IdVector</code> value
     *@param  pInactiveFl   an <code>boolean</code> value
     *@return                            UserInfoData
     *@exception  DataNotFoundException  if an error occurs
     *@exception  RemoteException        Required by EJB 1.0
     */

     public UserInfoData getUserContactForStore(int pUserId,IdVector pStoreIds,boolean pInactiveFl)
     throws DataNotFoundException, RemoteException ;

    /**
     *  Gets the user informatiom to be used by the request.
     *
     *@param  pSiteId      an <code>int</code> value
     *@param  pAccountId   an <code>int</code> value
     *@param  pRights      an <code>String</code> value
     *@return     UserInfoData
     *@exception  RemoteException        Required by EJB 1.0
     */

    public UserInfoDataVector getUserInfoCollection(int pSiteId, int pAccountId, String pRights) throws RemoteException;

    /**
     * Gets the user informatiom to be used by the request.
     *
     * @param  pUserIds    an <code>IdVector</code> value
     * @return UserInfoDataVector
     * @throws RemoteException Required by EJB 1.0
     */
    public UserInfoDataVector getUserInfoCollection(IdVector pUserIds) throws RemoteException;

    public UserAssocDataVector getUserAssocCollecton(int pUserId, String pAssocType) throws RemoteException;

    public IdVector getUserAssocCollecton(IdVector pUserIds, String pAssocType,String pStatusCd)
    throws RemoteException;

    public List saveNscUsers(NscUsViewVector pUsers, int pStoreId, int pDistributorId)
    throws RemoteException;
    public List saveNscUsers(NscUsViewVector pUsers, int pStoreId, int pDistributorId, boolean pSaveCatalogFl, String pAddBy)
    throws RemoteException;


    public HashMap getUserIdCodeMap(int storeId,String custMaj, List userIdCodes) throws RemoteException;
    public UserInfoDataVector getUserInfoDataCollection(int siteId, int accountId, String rights) throws RemoteException;
    public UserInfoData getUserContactForNotification(int pUserId)  throws DataNotFoundException, RemoteException;
    public UserDataVector getUsersCollectionByCriteria(DBCriteria pCriteria)    throws RemoteException ;
    public IdVector getUsersForGroupSiteRights( int siteId, IdVector groupIds, IdVector userIds ) throws RemoteException;

    public void updateUserAssoc(int pUserId,
                                IdVector pStoreIdsToAdd,
                                IdVector pAccountIdsToAdd,
                                IdVector pSiteIdsToAdd,
                                IdVector pStoreIdsToDelete,
                                IdVector pAccountIdsToDelete,
                                IdVector pSiteIdsToDelete,
                                String pUserName) throws RemoteException;
    public BusEntityDataVector getSiteCollection(int pUserId, String siteStatusCd, int pOptionalMaxSites, boolean pCompleteCondition )
        throws RemoteException ;

    public BusEntityDataVector getBusEntityCollection(int pUserId,
    	    String pBusEntityTypeCd,
    	    String pNameFilter, int pMaxRows, boolean pShowInactive)
    	    throws RemoteException;
    public IdVector getSuperRightUserCollection(UserData userD, int pStoreId) throws RemoteException;

    public IdVector getAccountIdsForUserSitesAssoc(int pStoreId, int pUserId) throws RemoteException;

    public IdVector getAccountIdsForUserAccountsAssoc(int pStoreId, int pUserId) throws RemoteException;
    public UserInfoDataVector getUsersByRight(int siteId, int accountId, String rights) throws RemoteException;
    
    /**
     *  Get list of user ids with user name length excced maxSize for a given store id.
     *
     *@param  pStoreId         the Store Id of the user associate. 
     *@param  maxSize          the max size user name should be. 
     *@return                  list of user ids associated with the store id and user name size excced maxSize.
     *@exception  RemoteException  if an error occurs
     */
    public IdVector getUserIdsWithNameExccedMaxSize(int pStoreId, int maxSize) throws RemoteException;

    public UserDataVector getUsersCollectionByCriteriaMod(UserSearchCriteriaData pSearchCriteria)
	throws RemoteException;
    
    // Methods for Multi Store Database Schemas
    
    public UserData login(LoginInfoView pLoginInfo) throws RemoteException, InvalidLoginException;

    public UserData login(LoginInfoView pLoginInfo, boolean pCheckPwd) throws RemoteException, InvalidLoginException;

    public void removeBusEntityAssociationsMultiStoreDb(int pUserId, AllStoreDataVector pAllStoreDataVector) throws RemoteException;

    public void addBusEntityAssociationsMultiStoreDb(int pUserId, String userAssocCode, String pUserName, AllStoreDataVector pAllStoreDataVector) throws RemoteException;

    public UserInfoData updateUserInfo(UserInfoData pUserInfoData, String pDatasource)
    throws RemoteException, DuplicateNameException;
    
    public UserData getUserByNameStoreDbSchema(String pUserName, String pDatasource)  throws RemoteException;
    
    public UserData getUserByUserIdFromStoreDbSchema(int pUserId, String pDatasource) throws RemoteException;
    
    public void addBusEntityAssociationsSingleStoreDb(int pUserId, String userAssocCode, String pUserName, String pDatasource, int pStoreId) throws RemoteException;
    
    public long getPasswordExpiryInDays(int userId, String userTypeCd) throws RemoteException;
    
    public boolean isPasswordNeedReset(UserData userD) throws RemoteException;
    public boolean isPasswordExpired(UserData userD) throws RemoteException; 

    public UserData login(LoginInfoView pLoginInfo, boolean pCheckPwd, boolean pEncrypted) throws RemoteException, InvalidLoginException;
}


