
package com.cleanwise.service.api.session;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.naming.NamingException;

import org.apache.log4j.Logger;


import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.APIServiceAccessException;
import com.cleanwise.service.api.dao.AddressDataAccess;
import com.cleanwise.service.api.dao.BusEntityAssocDataAccess;
import com.cleanwise.service.api.dao.BusEntityDAO;
import com.cleanwise.service.api.dao.BusEntityDataAccess;
import com.cleanwise.service.api.dao.CatalogAssocDataAccess;
import com.cleanwise.service.api.dao.CatalogDataAccess;
import com.cleanwise.service.api.dao.ContractDataAccess;
import com.cleanwise.service.api.dao.EmailDataAccess;
import com.cleanwise.service.api.dao.GroupAssocDataAccess;
import com.cleanwise.service.api.dao.GroupDataAccess;
import com.cleanwise.service.api.dao.OrderGuideDataAccess;
import com.cleanwise.service.api.dao.PasswordHistoryDataAccess;
import com.cleanwise.service.api.dao.PhoneDataAccess;
import com.cleanwise.service.api.dao.PropertyDataAccess;
import com.cleanwise.service.api.dao.RefCdDataAccess;
import com.cleanwise.service.api.dao.StoreProfileDataAccess;
import com.cleanwise.service.api.dao.UserAssocDataAccess;
import com.cleanwise.service.api.dao.UserDAO;
import com.cleanwise.service.api.dao.UserDataAccess;
import com.cleanwise.service.api.framework.BusEntityServicesAPI;
import com.cleanwise.service.api.util.CacheManager;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.DuplicateNameException;
import com.cleanwise.service.api.util.InvalidAccessTokenException;
import com.cleanwise.service.api.util.InvalidLoginException;
import com.cleanwise.service.api.util.PasswordUtil;
import com.cleanwise.service.api.util.PropertyUtil;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.UserRightsTool;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.AddressData;
import com.cleanwise.service.api.value.AddressDataVector;
import com.cleanwise.service.api.value.BusEntityAssocData;
import com.cleanwise.service.api.value.BusEntityAssocDataVector;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.BusEntityDataVector;
import com.cleanwise.service.api.value.CatalogAssocData;
import com.cleanwise.service.api.value.CatalogAssocDataVector;
import com.cleanwise.service.api.value.CatalogData;
import com.cleanwise.service.api.value.CatalogDataVector;
import com.cleanwise.service.api.value.ContractData;
import com.cleanwise.service.api.value.EmailData;
import com.cleanwise.service.api.value.EmailDataVector;
import com.cleanwise.service.api.value.GroupAssocData;
import com.cleanwise.service.api.value.GroupAssocDataVector;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.LdapItemData;
import com.cleanwise.service.api.value.NscUsView;
import com.cleanwise.service.api.value.NscUsViewVector;
import com.cleanwise.service.api.value.OrderGuideData;
import com.cleanwise.service.api.value.PasswordHistoryData;
import com.cleanwise.service.api.value.PasswordHistoryDataVector;
import com.cleanwise.service.api.value.PhoneData;
import com.cleanwise.service.api.value.PhoneDataVector;
import com.cleanwise.service.api.value.PropertyData;
import com.cleanwise.service.api.value.PropertyDataVector;
import com.cleanwise.service.api.value.RefCdData;
import com.cleanwise.service.api.value.RefCdDataVector;
import com.cleanwise.service.api.value.SiteData;
import com.cleanwise.service.api.value.SiteDataVector;
import com.cleanwise.service.api.value.StoreProfileData;
import com.cleanwise.service.api.value.StoreProfileDataVector;
import com.cleanwise.service.api.value.UserAccountRightsView;
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
import com.cleanwise.service.api.value.AllStoreData;
import com.cleanwise.service.api.value.AllStoreDataVector;

/**
 *  The <code>UserBean</code> class implements the <code>User</code> interface.
 *
 *@author     durval
 *@created    August 28, 2001
 *@see        com.cleanwise.service.api.session.User
 */
public class UserBean extends BusEntityServicesAPI {

    private static final Logger log = Logger.getLogger(UserBean.class);

    /**
     *  Creates a new <code>UserBean</code> instance.
     */
    public UserBean() { }


    /**
     *  Gets the LdapItem information to be used by the request.
     *
     *@param  pUserName            the user name
     *@param  pSiteId              a <code>String</code> value
     *@return                      LdapItemData
     *@exception  RemoteException  Required by EJB 1.0
     */
    public LdapItemData getLdapItem(String pUserName, String pSiteId)
    throws RemoteException {
        return new LdapItemData();
    }

    /**
     *  Gets user data vector
     *
     *@param  pUserIds a collection of user ids
     *@return                   BusEntityUserDataVector
     *@throws  RemoteException  Required by EJB 1.0
     */
    public UserDataVector getUsersCollection(IdVector pUserIds)
    throws RemoteException {

        Connection conn = null;

        // will not return null vector unless exception thrown
        UserDataVector udv = null;

        try {
            conn = getConnection();
            DBCriteria crit = new DBCriteria();
            crit.addOneOf(UserDataAccess.USER_ID,pUserIds);

            udv = UserDataAccess.select(conn, crit);
        } catch (Exception e) {
            throw new RemoteException("getUsersCollection() error: "
            + e.getMessage());
        } finally {
            try {if (conn != null) conn.close();} catch (Exception ex) {}
        }

        return udv;
    }


    /**
     *  Gets the business entity user vector values to be used by the request.
     *
     *@param  pBusEntityId      the business entity identifier.
     *@param  pOptionalWorkflowRoleCd, If this parameter is
     *  null, then it is ignored.  Otherwise, this method
     *  will get all users for this site with the specified
     *  role (example: ORDER APPROVER).
     *
     *@return                   BusEntityUserDataVector
     *@throws  RemoteException  Required by EJB 1.0
     */
    public UserDataVector getUsersCollectionByBusEntity
    (int pBusEntityId, String pOptionalWorkflowRoleCd)
    throws RemoteException {

        Connection conn = null;

        // will not return null vector unless exception thrown
        UserDataVector udv = null;

        try {
            conn = getConnection();
            DBCriteria crit = new DBCriteria();
            StringBuffer buf = new StringBuffer();

            if ( null != pOptionalWorkflowRoleCd &&
            pOptionalWorkflowRoleCd.length() > 0 ) {
                // Only find the users with the specified role.
                crit.addEqualTo(UserDataAccess.WORKFLOW_ROLE_CD,
                pOptionalWorkflowRoleCd);
            }

            buf.append(UserDataAccess.USER_ID);
            buf.append(" IN (SELECT ");
            buf.append(UserAssocDataAccess.USER_ID);
            buf.append(" FROM ");
            buf.append(UserAssocDataAccess.CLW_USER_ASSOC);
            buf.append(" WHERE ");
            buf.append(UserAssocDataAccess.BUS_ENTITY_ID);
            buf.append(" = ");
            buf.append(pBusEntityId);
            buf.append(")");
            crit.addCondition(buf.toString());
            udv = UserDataAccess.select(conn, crit);
        } catch (Exception e) {
            throw new RemoteException("getUsersCollectionByBusEntity error: "
            + e.getMessage());
        } finally {
            try {if (conn != null) conn.close();} catch (Exception ex) {}
        }

        return udv;
    }

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
    throws RemoteException {

        Connection conn = null;

        // will not return null vector unless exception thrown
        UserDataVector udv = null;

        try {
            conn = getConnection();
            DBCriteria crit = new DBCriteria();
            StringBuffer buf = new StringBuffer();

            if ( null != pOptionalWorkflowRoleCd &&
            pOptionalWorkflowRoleCd.length() > 0 ) {
                // Only find the users with the specified role.
                crit.addEqualTo(UserDataAccess.WORKFLOW_ROLE_CD,
                pOptionalWorkflowRoleCd);
            }

            buf.append(UserDataAccess.USER_ID);
            buf.append(" IN (SELECT ");
            buf.append(UserAssocDataAccess.USER_ID);
            buf.append(" FROM ");
            buf.append(UserAssocDataAccess.CLW_USER_ASSOC);
            buf.append(" WHERE ");
            buf.append(UserAssocDataAccess.BUS_ENTITY_ID);
            buf.append(" = ");
            buf.append(pBusEntityId);
            buf.append(")");
            crit.addCondition(buf.toString());
            crit.addNotOneOf(UserDataAccess.USER_TYPE_CD, "'"+usertype+"'");
            udv = UserDataAccess.select(conn, crit);
        } catch (Exception e) {
            throw new RemoteException("getUsersCollectionByBusEntity error: "
            + e.getMessage());
        } finally {
            try {if (conn != null) conn.close();} catch (Exception ex) {}
        }

        return udv;
    }

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
    throws RemoteException {

        Connection conn = null;

        // will not return null vector unless exception thrown
        UserDataVector udv = null;

        try {
            conn = getConnection();
            DBCriteria crit = new DBCriteria();
            crit.addOneOf(UserAssocDataAccess.BUS_ENTITY_ID,pBusEntityIdVector);
            IdVector userIds = UserAssocDataAccess.selectIdOnly(conn,UserAssocDataAccess.USER_ID, crit);

            crit = new DBCriteria();
            crit.addOneOf(UserDataAccess.USER_ID,userIds);
            if (pOptionalWorkflowRoleCd!=null && pOptionalWorkflowRoleCd.trim().length() > 0 ) {
                // Only find the users with the specified role.
                crit.addEqualTo(UserDataAccess.WORKFLOW_ROLE_CD, pOptionalWorkflowRoleCd);
            }
            crit.addOrderBy(UserDataAccess.USER_ID);
            udv = UserDataAccess.select(conn, crit);
        } catch (Exception e) {
            throw new RemoteException("getUsersCollectionByBusEntityCollection error: "
            + e.getMessage());
        } finally {
            try {if (conn != null) conn.close();} catch (Exception ex) {}
        }

        return udv;
    }


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
    throws RemoteException {

        IdVector busEntityIds = new IdVector();
        busEntityIds.add(new Integer(pBusEntityId));
        return getUsersCollectionByBusEntityCollection(busEntityIds, pName, pMatch, pOrder);
    }

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
    public UserDataVector getUsersCollectionByBusEntityCollection(IdVector pBusEntityIdVector,
    String pName,
    int pMatch,
    int pOrder)
    throws RemoteException {

        Connection conn = null;

        // will not return null vector unless exception thrown
        UserDataVector udv = null;

        try {
            conn = getConnection();
            DBCriteria crit = new DBCriteria();
            crit.addOneOf(UserAssocDataAccess.BUS_ENTITY_ID,pBusEntityIdVector);
            IdVector userIds = UserAssocDataAccess.selectIdOnly(conn,UserDataAccess.USER_ID, crit);
            crit = new DBCriteria();
            crit.addOneOf(UserDataAccess.USER_ID,userIds);
            switch (pMatch) {
                case User.NAME_BEGINS_WITH:
                    crit.addLike(UserDataAccess.USER_NAME, pName + "%");
                    break;
                case User.NAME_BEGINS_WITH_IGNORE_CASE:
                    crit.addLikeIgnoreCase(UserDataAccess.USER_NAME,
                    pName + "%");
                    break;
                case User.NAME_CONTAINS:
                    crit.addLike(UserDataAccess.USER_NAME,
                    "%" + pName + "%");
                    break;
                case User.NAME_CONTAINS_IGNORE_CASE:
                    crit.addLikeIgnoreCase(UserDataAccess.USER_NAME,
                    "%" + pName + "%");
                    break;
                case User.NAME_EXACT:
                    crit.addEqualTo(UserDataAccess.USER_NAME,
                    pName);
                    break;
                case User.NAME_EXACT_IGNORE_CASE:
                    crit.addEqualToIgnoreCase(UserDataAccess.USER_NAME,
                    pName);
                    break;
                default:
                    throw new RemoteException("bad match");
            }

            switch (pOrder) {
                case User.ORDER_BY_ID:
                    crit.addOrderBy(UserDataAccess.USER_ID, true);
                    break;
                case User.ORDER_BY_NAME:
                    crit.addOrderBy(UserDataAccess.USER_NAME, true);
                    break;
                default:
                    throw new RemoteException("Bad order specification");
            }

            udv = UserDataAccess.select(conn, crit);
        } catch (Exception e) {
            throw new RemoteException("getUsersCollectionByBusEntityCollection error: "
            + e.getMessage());
        } finally {
            try {if (conn != null) conn.close();} catch (Exception ex) {}
        }

        return udv;
    }

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
    throws RemoteException {

        Connection conn = null;

        // will not return null vector unless exception thrown
        UserDataVector udv = null;

        try {
            conn = getConnection();
            DBCriteria crit = new DBCriteria();
            crit.addOneOf(UserAssocDataAccess.BUS_ENTITY_ID,pBusEntityIdVector);
            IdVector userIds = UserAssocDataAccess.selectIdOnly(conn,UserDataAccess.USER_ID, crit);
            crit = new DBCriteria();
            crit.addOneOf(UserDataAccess.USER_ID,userIds);
            switch (pMatch) {
                case User.NAME_BEGINS_WITH:
                    crit.addLike(UserDataAccess.USER_NAME, pName + "%");
                    break;
                case User.NAME_BEGINS_WITH_IGNORE_CASE:
                    crit.addLikeIgnoreCase(UserDataAccess.USER_NAME,
                    pName + "%");
                    break;
                case User.NAME_CONTAINS:
                    crit.addLike(UserDataAccess.USER_NAME,
                    "%" + pName + "%");
                    break;
                case User.NAME_CONTAINS_IGNORE_CASE:
                    crit.addLikeIgnoreCase(UserDataAccess.USER_NAME,
                    "%" + pName + "%");
                    break;
                case User.NAME_EXACT:
                    crit.addEqualTo(UserDataAccess.USER_NAME,
                    pName);
                    break;
                case User.NAME_EXACT_IGNORE_CASE:
                    crit.addEqualToIgnoreCase(UserDataAccess.USER_NAME,
                    pName);
                    break;
                default:
                    throw new RemoteException("bad match");
            }

            switch (pOrder) {
                case User.ORDER_BY_ID:
                    crit.addOrderBy(UserDataAccess.USER_ID, true);
                    break;
                case User.ORDER_BY_NAME:
                    crit.addOrderBy(UserDataAccess.USER_NAME, true);
                    break;
                default:
                    throw new RemoteException("Bad order specification");
            }

            crit.addNotOneOf(UserDataAccess.USER_TYPE_CD, "'"+usertype+"'");
            udv = UserDataAccess.select(conn, crit);
        } catch (Exception e) {
            throw new RemoteException("getUsersCollectionByBusEntityCollection error: "
            + e.getMessage());
        } finally {
            try {if (conn != null) conn.close();} catch (Exception ex) {}
        }

        return udv;
    }


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
    public UserDataVector getUsersCollection(int pStartUserId,
    int pReturnLimit)
    throws RemoteException {

        UserDataVector uv;

        Connection conn = null;
        try {
            conn = getConnection();
            DBCriteria crit = new DBCriteria();
            crit.addGreaterOrEqual(UserDataAccess.USER_ID, pStartUserId);
            crit.addOrderBy(UserDataAccess.USER_ID);
            uv = UserDataAccess.select(conn, crit, pReturnLimit);
        }
        catch (Exception e) {
            String msg = "getUsersCollection: " + e.getMessage();
            logError(msg);
            throw new RemoteException(msg);
        } finally {
            try {if (conn != null) conn.close();} catch (Exception ex) {}
        }

        return uv;
    }


    /**
     *  Gets the user vector values to be used by the request.
     *
     *@param  pUserName            a <code>String</code> value
     *@param  pNameMatchType       an <code>int</code> value, expected values
     *      are: User.NAME_
     *@return                      UserDataVector
     *@exception  RemoteException  Required by EJB 1.0
     */
    public UserDataVector getUsersCollectionByName
    (String pUserName, int pNameMatchType,
    int pOptionalAccountId)
    throws RemoteException {
        UserDataVector uv;

        Connection conn = null;
        try {
            conn = getConnection();
            DBCriteria crit = new DBCriteria();

            switch (pNameMatchType) {
                case User.NAME_BEGINS_WITH: {
                    String nname = pUserName + "%";
                    crit.addLike(UserDataAccess.USER_NAME, nname);
                    break;
                }
                case User.NAME_BEGINS_WITH_IGNORE_CASE: {
                    String nname = pUserName + "%";
                    crit.addLikeIgnoreCase(UserDataAccess.USER_NAME, nname);
                    break;
                }
                case User.NAME_CONTAINS: {
                    String nname = "%" + pUserName + "%";
                    crit.addLike(UserDataAccess.USER_NAME, nname);
                    break;
                }
                case User.NAME_CONTAINS_IGNORE_CASE: {
                    String nname = "%" + pUserName + "%";
                    crit.addLikeIgnoreCase(UserDataAccess.USER_NAME, nname);
                    break;
                }
                case User.NAME_EXACT: {
                    crit.addEqualTo(UserDataAccess.USER_NAME, pUserName);
                    break;
                }
                case User.NAME_EXACT_IGNORE_CASE: {
                    crit.addEqualToIgnoreCase(UserDataAccess.USER_NAME,
                    pUserName);
                    break;
                }
                default: {
                    throw new
                    RemoteException
                    ("getUsersCollectionByName error: unknown match type " +
                    pNameMatchType);
                }
            } // End of switch.

            if (pOptionalAccountId > 0) {
                String subquery = "select distinct " +
                UserAssocDataAccess.USER_ID + " from " +
                UserAssocDataAccess.CLW_USER_ASSOC +
                " where " +
                UserAssocDataAccess.BUS_ENTITY_ID + " = " +
                pOptionalAccountId;
                crit.addOneOf(UserDataAccess.USER_ID,
                subquery);
            }
            crit.addOrderBy(UserDataAccess.USER_ID);
            uv = UserDataAccess.select(conn, crit);

        }
        catch (Exception e) {
            String msg = "getUsersCollectionByName: " + e.getMessage();
            logError(msg);
            throw new RemoteException(msg);
        } finally {
            try {if (conn != null) conn.close();} catch (Exception ex) {}
        }
        return uv;
    }

    /**
     *Returns a user that matches the given user name.  If the user name does not exist a data not found exception is thrown.
     */
    public UserData getUserByName (String pUserName, int pStoreId) throws RemoteException, DataNotFoundException {
        UserSearchCriteriaData crit = new UserSearchCriteriaData();
        if(pStoreId > 0){
            crit.setStoreId(pStoreId);
        }
        crit.setUserName(pUserName);
        crit.setUserNameMatch(User.NAME_EXACT);

        UserDataVector udv = getUsersCollectionByCriteria(crit);
        if(udv.size() > 0){
            UserData ud = (UserData) udv.get(0);
            return ud;
        }
        throw new DataNotFoundException("Could not find user matching user name: "+pUserName+" belonging to store "+pStoreId);
    }

    /**
     *  Gets the user vector values to be used by the request.
     *
     *@param  pUserType            a <code>String</code> value
     *@return                      UserDataVector
     *@exception  RemoteException  Required by EJB 1.0
     */
    public UserDataVector getUsersCollectionByType(String pUserType)
    throws RemoteException {

        UserDataVector uv;

        Connection conn = null;
        try {
            conn = getConnection();
            DBCriteria crit = new DBCriteria();

            crit.addEqualToIgnoreCase(UserDataAccess.USER_TYPE_CD,
            pUserType);

            crit.addOrderBy(UserDataAccess.USER_NAME);
            uv = UserDataAccess.select(conn, crit);
        }
        catch (Exception e) {
            String msg = "getUsersCollectionByType: " + e.getMessage();
            logError(msg);
            throw new RemoteException(msg);
        } finally {
            try {if (conn != null) conn.close();} catch (Exception ex) {}
        }

        return uv;
    }


    /**
     *Returns the users that match the selected search criteria.
     *
     *@param pSearchCriteria      the UserSearchCriteria.
     *@return                   UserDataVector
     *@throws  RemoteException  Required by EJB 1.0
     */
    public UserDataVector getUsersCollectionByCriteria(UserSearchCriteriaData pSearchCriteria)
    throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            log.info("*****BBB inside UserBean.getUsersCollectionByCriteria()");
            return UserDAO.getUsersCollectionByCriteria(conn, pSearchCriteria);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }

    }

    /**
     *Returns the users that match the selected search criteria.
     *
     *@param pSearchCriteria      the UserSearchCriteria.
     *@return                   UserDataVector
     *@throws  RemoteException  Required by EJB 1.0
     */
    public UserDataVector getUsersCollectionByCriteriaMod(UserSearchCriteriaData pSearchCriteria)
    throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            log.info("*****AAA inside UserBean.getUsersCollectionByCriteriaMod()");
            return UserDAO.getUsersCollectionByCriteriaMod(conn, pSearchCriteria);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }

    }
    
    public UserDataVector getUsersCollectionByCriteria(DBCriteria pCriteria)
    throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            int max = 1000;

            return UserDataAccess.select(conn, pCriteria, max);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }

    }


    /**
     *  Gets the user informatiom to be used by the request.
     *
     *@param  pUserId                    an <code>int</code> value
     *@return                            UserData
     *@exception  DataNotFoundException  if an error occurs
     *@exception  RemoteException        Required by EJB 1.0
     */
    public UserData getUser(int pUserId)
    throws DataNotFoundException, RemoteException {

        UserData ud;

        Connection conn = null;
        try {
            conn = getConnection();
            ud = UserDataAccess.select(conn, pUserId);
        }
        catch (Exception e) {
            String msg = "getUser: for id: " + pUserId +
            " error " + e.getMessage();
            logError(msg);
            throw new DataNotFoundException(msg);
        } finally {
            try {if (conn != null) conn.close();} catch (Exception ex) {}
        }

        return ud;
    }


    /**
     *  Gets the user informatiom to be used by the request.
     *
     *@param  pUserId                    an <code>int</code> value
     *@return                            UserInfoData
     *@exception  DataNotFoundException  if an error occurs
     *@exception  RemoteException        Required by EJB 1.0
     */
    public UserInfoData getUserContact(int pUserId)
    throws DataNotFoundException, RemoteException {

        UserInfoData uid;

        Connection conn = null;
        try {
            conn = getConnection();
            uid = UserInfoData.createValue();

            UserData ud = UserDataAccess.select(conn, pUserId);
            uid = getUserInfoData(conn, ud);
        }
        catch (Exception e) {
            String msg = "getUserContact: for id: " + pUserId +
            " error " + e.getMessage();
            logError(msg);
            throw new DataNotFoundException(msg);
        } finally {
            try {if (conn != null) conn.close();} catch (Exception ex) {}
        }

        return uid;
    }
    /**
     *  Gets the user informatiom to be used by the request.
     *
     *@param  pUserId                    an <code>int</code> value
     *@return                            UserInfoData
     *@exception  DataNotFoundException  if an error occurs
     *@exception  RemoteException        Required by EJB 1.0
     */
    public UserInfoData getUserContactForNotification(int pUserId)
    throws DataNotFoundException, RemoteException {

        UserInfoData uid;

        Connection conn = null;
        try {
            conn = getConnection();
            uid = UserInfoData.createValue();

            UserData ud = UserDataAccess.select(conn, pUserId);
            uid = getUserInfoDataForNotification(conn, ud);
        }
        catch (Exception e) {
            String msg = "getUserContact: for id: " + pUserId +
            " error " + e.getMessage();
            logError(msg);
            throw new DataNotFoundException(msg);
        } finally {
            try {if (conn != null) conn.close();} catch (Exception ex) {}
        }

        return uid;
    }

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
    throws DataNotFoundException, RemoteException {

         UserInfoData uid;
         UserData userData = null;
         Connection conn = null;
         try {
             conn = getConnection();
             if (pStoreIds !=null && pStoreIds.size()>0&& pUserId > 0) {
                 UserSearchCriteriaData searchCriteria = UserSearchCriteriaData.createValue();
                 searchCriteria.setStoreIds(pStoreIds);
                 searchCriteria.setUserId(String.valueOf(pUserId));
                 searchCriteria.setIncludeInactiveFl(pInactiveFl);
                 UserDataVector userDV = getUsersCollectionByCriteria(searchCriteria);
                 if (userDV != null && userDV.size() > 0) {
                     if (userDV.size() == 1) {
                         userData = (UserData) userDV.get(0);
                     } else {
                         throw new RemoteException("Multiple users for store id : " + pStoreIds);
                     }
                 }
             }
             if (userData != null) {
                 uid = UserInfoData.createValue();
                 uid.setUserData(userData);
                 // Get the address.
                 DBCriteria crit = new DBCriteria();
                 crit.addEqualTo(AddressDataAccess.USER_ID, pUserId);
                 AddressDataVector ad = AddressDataAccess.select(conn, crit);
                 if (ad.size() > 0) {
                     uid.setAddressData((AddressData) ad.get(0));
                 }
                 // Get the Email.
                 crit = new DBCriteria();
                 crit.addEqualTo(EmailDataAccess.USER_ID, pUserId);
                 EmailDataVector ed = EmailDataAccess.select(conn, crit);
                 if (ed.size() > 0) {
                     uid.setEmailData((EmailData) ed.get(0));
                 }
                 // Get the phone.
                 crit = new DBCriteria();
                 crit.addEqualTo(PhoneDataAccess.USER_ID, pUserId);
                 PhoneDataVector pd = PhoneDataAccess.select(conn, crit);
                 Iterator phoneI = pd.iterator();
                 while (phoneI.hasNext()) {
                     PhoneData phone = (PhoneData) phoneI.next();
                     String phoneType = phone.getPhoneTypeCd();
                     if (phoneType.compareTo(RefCodeNames.PHONE_TYPE_CD.PHONE) == 0) {
                         uid.setPhone(phone);
                     } else if (phoneType.compareTo(RefCodeNames.PHONE_TYPE_CD.FAX) == 0) {
                         uid.setFax(phone);
                     } else if (phoneType.compareTo(RefCodeNames.PHONE_TYPE_CD.MOBILE) == 0) {
                         uid.setMobile(phone);
                     } else {
                         // unidentified - ignore it
                     }
                 }
                 PropertyUtil putil = new PropertyUtil(conn);
                 uid.setCustomerSystemKey(putil.fetchValueIgnoreMissing(pUserId, 0, RefCodeNames.PROPERTY_TYPE_CD.CUSTOMER_SYSTEM_KEY));
                 uid.setUserAccountRights
                         (getUserAccountRights(conn, pUserId));
             } else {
                 throw new DataNotFoundException("User is not found");
             }
         }
         catch (Exception e) {
             String msg = "getUserContact: for id: " + pUserId +
                     " error " + e.getMessage();
             logError(msg);
             throw new DataNotFoundException(msg);
         } finally {
             try {
                 if (conn != null) conn.close();
             } catch (Exception ex) {
             }
         }
         return uid;
     }

    /**
     *  Gets the user property vector values to be used by the request.
     *
     *@param  pUserId           the user identifier.
     *@return                   UserPropertyDataVector
     *@throws  RemoteException  Required by EJB 1.0
     */
    public UserPropertyDataVector getUserPropertiesCollection(int pUserId)
    throws RemoteException {
        return new UserPropertyDataVector();
    }


    /**
     *  Gets the user property informatiom to be used by the request.
     *
     *@param  pUserID              an <code>int</code> value
     *@param  pPropertyId          the property identifier.
     *@return                      UserPropertyData
     *@exception  RemoteException  Required by EJB 1.0
     */
    public UserPropertyData getUserProperty(int pUserID, int pPropertyId)
    throws RemoteException {
        return new UserPropertyData();
    }

    /**
     *Returns true if the specified user id has a valid relationship to the specified site id, false
     *otherwise.
     *@param pUserId the user id
     *@param pSiteId the site id
     *@throws RemoteException
     */
    public boolean isSiteOfUser(int pSiteId, int pUserId)
    throws RemoteException {
        Connection con = null;
        try{
            con = getConnection();
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(UserAssocDataAccess.BUS_ENTITY_ID,pSiteId);
            crit.addEqualTo(UserAssocDataAccess.USER_ID,pUserId);
            crit.addEqualTo(UserAssocDataAccess.USER_ASSOC_CD,RefCodeNames.USER_ASSOC_CD.SITE);
            logDebug(">>>>>>>>>>>>"+UserAssocDataAccess.getSqlSelectIdOnly("*", crit));
            UserAssocDataVector uadv = UserAssocDataAccess.select(con,crit,1);
            return (uadv.size() > 0);
        }catch(Exception e){
            throw processException(e);
        }finally{
            closeConnection(con);
        }
    }

    /**
     *Updates the most recent visit date/time for a user/site pair.
     *@param pSiteId the site id
     *@param pUserId the user id
     *@param pUserName the user name
     *@param visitInfo the date/time of the most recent user visit to the site.
     *@throws RemoteException
     */
    public void updateLastUserVisit(int pSiteId, int pUserId, String pUserName, Date visitInfo) throws RemoteException {
	    Connection con = null;
	    try{
	        con = getConnection();
	        DBCriteria crit = new DBCriteria();
	        crit.addEqualTo(UserAssocDataAccess.BUS_ENTITY_ID,pSiteId);
	        crit.addEqualTo(UserAssocDataAccess.USER_ID,pUserId);
	        crit.addEqualTo(UserAssocDataAccess.USER_ASSOC_CD,RefCodeNames.USER_ASSOC_CD.SITE);
	        UserAssocDataVector uadv = UserAssocDataAccess.select(con,crit,1);
	        if (uadv.size() == 1) {
	        	UserAssocData userAssoc = (UserAssocData)uadv.get(0);
	        	userAssoc.setLastUserVisitDateTime(visitInfo);
	        	if (Utility.isSet(pUserName)) {
	        		userAssoc.setModBy(pUserName);
	        	}
	        	UserAssocDataAccess.update(con, userAssoc);
	        }
	        else {
	        	throw new DataNotFoundException("User association between user (" + pUserId + ") and site (" + pSiteId + ") not found.");
	        }
	    }
	    catch(Exception e){
	        throw processException(e);
	    }
	    finally{
	        closeConnection(con);
	    }
    }

    /**
     *  Gets the array-like business entity association vector values to be used
     *  by the request.
     *
     *@param  pUserId           Description of Parameter
     *@return                   The SiteCollection value
     *@throws  RemoteException  Required by EJB 1.0
     */
    public BusEntityDataVector getSiteCollection(int pUserId)
    throws RemoteException {
        return getBusEntityCollection(pUserId, RefCodeNames.BUS_ENTITY_TYPE_CD.SITE);
    }

    public BusEntityDataVector getSiteCollection(int pUserId,
    int pOptionalMaxSites)
    throws RemoteException {
        return getBusEntityCollection(pUserId, RefCodeNames.BUS_ENTITY_TYPE_CD.SITE, null, pOptionalMaxSites);
    }

    public BusEntityDataVector getSiteCollection(int pUserId, String pSiteStatusCd,
    int pOptionalMaxSites, boolean pCompleteCondition)
    throws RemoteException {
       DBCriteria dbc = new DBCriteria();
       dbc.addJoinTable(UserAssocDataAccess.CLW_USER_ASSOC);
       dbc.addJoinCondition(BusEntityDataAccess.CLW_BUS_ENTITY, BusEntityDataAccess.BUS_ENTITY_ID, UserAssocDataAccess.CLW_USER_ASSOC, UserAssocDataAccess.BUS_ENTITY_ID);
       DBCriteria dbc0 = new DBCriteria();
       dbc0.addCondition(UserAssocDataAccess.CLW_USER_ASSOC + "." + UserAssocDataAccess.USER_ID + "=" + pUserId);
       dbc0.addCondition(UserAssocDataAccess.USER_ASSOC_CD + "='" + RefCodeNames.USER_ASSOC_CD.SITE+ "'");
       dbc.addIsolatedCriterita(dbc0);
       if (!pCompleteCondition && Utility.isSet(pSiteStatusCd) ){
         dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_STATUS_CD, pSiteStatusCd);
       }
       ArrayList siteAddTypes = new ArrayList();
       siteAddTypes.add(RefCodeNames.ADDRESS_TYPE_CD.SHIPPING);
       siteAddTypes.add(RefCodeNames.ADDRESS_TYPE_CD.CUSTOMER_SHIPPING);
       dbc.addJoinTable(AddressDataAccess.CLW_ADDRESS);
       dbc.addJoinCondition(BusEntityDataAccess.CLW_BUS_ENTITY, BusEntityDataAccess.BUS_ENTITY_ID, AddressDataAccess.CLW_ADDRESS, AddressDataAccess.BUS_ENTITY_ID);
       dbc.addJoinTableOneOf(AddressDataAccess.CLW_ADDRESS, AddressDataAccess.ADDRESS_TYPE_CD, siteAddTypes);
       if (pCompleteCondition){
           dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_STATUS_CD, RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);
           DBCriteria dbc1 = new DBCriteria();
           dbc1.addCondition("NVL(" +BusEntityDataAccess.CLW_BUS_ENTITY+"."+BusEntityDataAccess.EFF_DATE +", SYSDATE)<= SYSDATE");
           dbc.addIsolatedCriterita(dbc1);
           DBCriteria dbc2 = new DBCriteria();
           dbc2.addCondition("NVL(" + BusEntityDataAccess.CLW_BUS_ENTITY+"."+BusEntityDataAccess.EXP_DATE +", SYSDATE)>= SYSDATE");
           dbc.addIsolatedCriterita(dbc2);
           dbc.addJoinTable(CatalogAssocDataAccess.CLW_CATALOG_ASSOC);
           dbc.addJoinTable(ContractDataAccess.CLW_CONTRACT);
           dbc.addJoinCondition(BusEntityDataAccess.CLW_BUS_ENTITY, BusEntityDataAccess.BUS_ENTITY_ID, CatalogAssocDataAccess.CLW_CATALOG_ASSOC, CatalogAssocDataAccess.BUS_ENTITY_ID );
           dbc.addJoinCondition(CatalogAssocDataAccess.CLW_CATALOG_ASSOC, CatalogAssocDataAccess.CATALOG_ID, ContractDataAccess.CLW_CONTRACT, ContractDataAccess.CATALOG_ID );
           dbc.addJoinTableEqualTo(CatalogAssocDataAccess.CLW_CATALOG_ASSOC, CatalogAssocDataAccess.CATALOG_ASSOC_CD, RefCodeNames.CATALOG_ASSOC_CD.CATALOG_SITE);
           dbc.addJoinTableEqualTo(ContractDataAccess.CLW_CONTRACT, ContractDataAccess.CONTRACT_STATUS_CD, RefCodeNames.CONTRACT_STATUS_CD.ACTIVE );
           //dbc.addJoinTable(ContractItemDataAccess.CLW_CONTRACT_ITEM);
           //dbc.addJoinCondition(ContractDataAccess.CLW_CONTRACT, ContractDataAccess.CONTRACT_ID, ContractItemDataAccess.CLW_CONTRACT_ITEM, ContractItemDataAccess.CONTRACT_ID );
       }
       return getBusEntityCollection( dbc, pOptionalMaxSites);
    }


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
    throws RemoteException {
        Connection con = null;
        SiteDataVector siteDV = new SiteDataVector();
        try {
            con = getConnection();
            BusEntityDataVector busEntityDV =
            getBusEntityCollection(pUserId,
            RefCodeNames.BUS_ENTITY_TYPE_CD.SITE,
            pFilter);

            APIAccess factory = new APIAccess();
            Site siteBean = factory.getSiteAPI();

            for (int ii = 0; ii < busEntityDV.size(); ii++) {
                BusEntityData busD = (BusEntityData)
                busEntityDV.get(ii);
                try {
                    SiteData siteD =
                    siteBean.getSite(busD.getBusEntityId(), 0);
                    siteDV.add(siteD);
                }
                catch (DataNotFoundException nodata) {
                    logError("Error. UserBean.getSiteCollection(): "
                    + nodata.getMessage()
                    );
                }
            }
        }

        catch (APIServiceAccessException exc) {
            logError("exc.getMessage");
            exc.printStackTrace();
            throw new RemoteException("Error. UserBean.getSiteCollection(), APIAccess error.");
        }
        catch (NamingException exc) {
            logError("exc.getMessage");
            exc.printStackTrace();
            throw new RemoteException("Error. UserBean.getSiteCollection() Naming Exception happened");
        }
        catch (SQLException exc) {
            logError("exc.getMessage");
            exc.printStackTrace();
            throw new RemoteException("Error. UserBean.getSiteCollection() SQL Exception happened");
        } finally {
            try {if (con != null) con.close();} catch (Exception ex) {}
        }

        return siteDV;
    }



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
    throws RemoteException {
        return getBusEntityCollection(pUserId, RefCodeNames.BUS_ENTITY_TYPE_CD.SITE, pNameFilter);
    }


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
    throws RemoteException {
        return getBusEntityCollection(pUserId, pBusEntityTypeCd, null);
    }


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
    throws RemoteException {
        return getBusEntityCollection(pUserId, pBusEntityTypeCd, pNameFilter, 0);
    }

    /**
     *Returns an IdVector for the bus entities associated with the user passed in that meet all of
     *the specifed criteria (type name (a.k.a. shorDesc).
     */
    private IdVector getBusEntityIds(int pUserId,
    String pBusEntityTypeCd,
    String pNameFilter, int pMaxRows, Connection con) throws SQLException, RemoteException{
        DBCriteria dbc = new DBCriteria();
        dbc.addEqualTo(UserAssocDataAccess.USER_ID, pUserId);
        if (pNameFilter != null && pNameFilter.trim().length() > 0) {
            String filter = "%" + pNameFilter.trim().toUpperCase() + "%";
            DBCriteria dbcF = new DBCriteria();
            dbcF.addLikeIgnoreCase(BusEntityDataAccess.SHORT_DESC, filter);
            dbcF.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,
            pBusEntityTypeCd);
            String busEntityReq =
            BusEntityDataAccess.getSqlSelectIdOnly
            (BusEntityDataAccess.BUS_ENTITY_ID, dbcF);
            dbc.addOneOf(UserAssocDataAccess.BUS_ENTITY_ID, busEntityReq);
        }
        String userAssocCd = UserDAO.translateBusEntityTypeCdToAssocCd(pBusEntityTypeCd);
        dbc.addEqualTo(UserAssocDataAccess.USER_ASSOC_CD, userAssocCd);
        dbc.addOrderBy(UserAssocDataAccess.BUS_ENTITY_ID);
        IdVector beids = UserAssocDataAccess.selectIdOnly(con,UserAssocDataAccess.BUS_ENTITY_ID, dbc);
        return beids;
    }

    public BusEntityDataVector getBusEntityCollection(int pUserId,
    	    String pBusEntityTypeCd,
    	    String pNameFilter, int pMaxRows) throws RemoteException{
    	try {
    		return getBusEntityCollection(pUserId, pBusEntityTypeCd, pNameFilter, pMaxRows,false);
    	}catch (RemoteException exc) {
            throw new RemoteException("Error. UserBean.getBusEntityCollection() Naming Exception happened");
        }

    }
    /**
     *Returns a populated BusEntityData vecotr for the bus entities associated with the user passed in that meet all of
     *the specifed criteria (type name (a.k.a. shorDesc).
     */
    public BusEntityDataVector getBusEntityCollection(int pUserId,
    String pBusEntityTypeCd,
    String pNameFilter, int pMaxRows, boolean pShowInactive)
    throws RemoteException {
        Connection con = null;
        BusEntityDataVector busEntityDV = new BusEntityDataVector();
        try {
            con = getConnection();
            List beids = getBusEntityIds(pUserId, pBusEntityTypeCd, pNameFilter, pMaxRows, con);

            if ( beids != null && beids.size() > 0 ) {
            	if (pMaxRows > 0 && beids.size() > pMaxRows){
            		beids = beids.subList(0, pMaxRows);
            	}
                DBCriteria dbc = new DBCriteria();
                dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,
                pBusEntityTypeCd);
                if(!pShowInactive){
                	dbc.addNotEqualTo(BusEntityDataAccess.BUS_ENTITY_STATUS_CD, RefCodeNames.BUS_ENTITY_STATUS_CD.INACTIVE);
                }
                dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, beids);
                dbc.addOrderBy(BusEntityDataAccess.BUS_ENTITY_STATUS_CD);
                dbc.addOrderBy(BusEntityDataAccess.SHORT_DESC);
                busEntityDV = BusEntityDataAccess.select(con, dbc, pMaxRows);
            }
            else {
                logDebug("No sites set up for user pUserId=" + pUserId);
                return busEntityDV;
            }

        }
        catch (NamingException exc) {
            logError("exc.getMessage");
            exc.printStackTrace();
            throw new RemoteException("Error. UserBean.getBusEntityCollection() Naming Exception happened");
        }
        catch (SQLException exc) {
            logError("exc.getMessage");
            exc.printStackTrace();
            throw new RemoteException("Error. UserBean.getBusEntityCollection() SQL Exception happened");
        } finally {
            try {if (con != null) con.close();} catch (Exception ex) {}
        }
        return busEntityDV;
    }

    /**
     *Returns a populated BusEntityData vecotr for the bus entities associated with the user passed in that meet all of
     *the specifed criteria (type name (a.k.a. shorDesc).
     */
    public BusEntityDataVector getBusEntityCollection(DBCriteria pCrit, int pMaxRows)
    throws RemoteException {
        Connection con = null;
        BusEntityDataVector busEntityDV = new BusEntityDataVector();
        try {
            con = getConnection();
            if (pCrit == null){
              pCrit = new DBCriteria();
            }
            pCrit.addJoinTableOrderBy(BusEntityDataAccess.CLW_BUS_ENTITY, BusEntityDataAccess.SHORT_DESC);
            busEntityDV = BusEntityDataAccess.select(con, pCrit, pMaxRows);
        }
        catch (NamingException exc) {
            logError("exc.getMessage");
            exc.printStackTrace();
            throw new RemoteException("Error. UserBean.getBusEntityCollection() Naming Exception happened");
        }
        catch (SQLException exc) {
            logError("exc.getMessage");
            exc.printStackTrace();
            throw new RemoteException("Error. UserBean.getBusEntityCollection() SQL Exception happened");
        } finally {
            try {if (con != null) con.close();} catch (Exception ex) {}
        }
        return busEntityDV;
    }


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
    throws RemoteException {

        UserDataVector userVec = new UserDataVector();

        Connection conn = null;
        try {
            conn = getConnection();
            DBCriteria crit = new DBCriteria();
            if (pBusEntityId != 0) {
                crit.addEqualTo(UserAssocDataAccess.BUS_ENTITY_ID,
                pBusEntityId);
                IdVector ids = UserAssocDataAccess.selectIdOnly(conn,
                UserAssocDataAccess.USER_ID, crit);
                if (ids.size() == 0) {
                    return userVec;
                }
                crit = new DBCriteria();
                crit.addOneOf(UserDataAccess.USER_ID, ids);
            }

            switch (pOrder) {
                case User.ORDER_BY_ID:
                    crit.addOrderBy(UserDataAccess.USER_ID, true);
                    break;
                case User.ORDER_BY_NAME:
                    crit.addOrderBy(UserDataAccess.USER_NAME, true);
                    break;
                default:
                    throw new RemoteException("getAllUsers: Bad order specification");
            }

            userVec =
            UserDataAccess.select(conn, crit);

        }
        catch (Exception e) {
            throw new RemoteException("getAllUsers: " + e.getMessage());
        } finally {
            try {if (conn != null) conn.close();} catch (Exception ex) {}
        }

        return userVec;
    }

    /**
     *  Get all active users for the given business entity.
     *
     *@param  pBusEntityId         the Id of the user busEntity.
     *@param  pOrder               one of ORDER_BY_ID, ORDER_BY_NAME
     *@return                      a <code>UserDataVector</code>  object
     *@exception  RemoteException
     */
    public UserDataVector getAllActiveUsers(int pBusEntityId, int pOrder)
    throws RemoteException {

        UserDataVector userVec = new UserDataVector();

        Connection conn = null;
        try {
            conn = getConnection();
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(UserAssocDataAccess.BUS_ENTITY_ID,
            pBusEntityId);
            IdVector ids = UserAssocDataAccess.selectIdOnly(conn,
            UserAssocDataAccess.USER_ID, crit);
            if (ids.size() == 0) {
                return userVec;
            }
            crit = new DBCriteria();
            crit.addOneOf(UserDataAccess.USER_ID, ids);
            crit.addEqualTo(UserDataAccess.USER_STATUS_CD,
                                          RefCodeNames.USER_STATUS_CD.ACTIVE);

            switch (pOrder) {
                case User.ORDER_BY_ID:
                    crit.addOrderBy(UserDataAccess.USER_ID, true);
                    break;
                case User.ORDER_BY_NAME:
                    crit.addOrderBy(UserDataAccess.USER_NAME, true);
                    break;
                default:
                    throw new RemoteException("getAllUsers: Bad order specification");
            }

            userVec =  UserDataAccess.select(conn, crit);

        }
        catch (Exception e) {
            throw new RemoteException("getAllUsers: " + e.getMessage());
        } finally {
            try {if (conn != null) conn.close();} catch (Exception ex) {}
        }

        return userVec;
    }

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
    throws RemoteException {

        UserDataVector userVec = new UserDataVector();

        Connection conn = null;
        try {
            conn = getConnection();
            DBCriteria crit = new DBCriteria();
            if (pBusEntityId != 0) {
                crit.addEqualTo(UserAssocDataAccess.BUS_ENTITY_ID,
                pBusEntityId);
                IdVector ids =
                UserAssocDataAccess.selectIdOnly(conn,
                UserAssocDataAccess.USER_ID, crit);
                if (ids.size() == 0) {
                    // no users for this bus entity
                    return userVec;
                }
                crit = new DBCriteria();
                crit.addOneOf(UserDataAccess.USER_ID, ids);
            }

            switch (pMatch) {
                case User.NAME_BEGINS_WITH:
                    crit.addLike(UserDataAccess.USER_NAME, pName + "%");
                    break;
                case User.NAME_BEGINS_WITH_IGNORE_CASE:
                    crit.addLikeIgnoreCase(UserDataAccess.USER_NAME,
                    pName + "%");
                    break;
                case User.NAME_CONTAINS:
                    crit.addLike(UserDataAccess.USER_NAME,
                    "%" + pName + "%");
                    break;
                case User.NAME_CONTAINS_IGNORE_CASE:
                    crit.addLikeIgnoreCase(UserDataAccess.USER_NAME,
                    "%" + pName + "%");
                    break;
                case User.NAME_EXACT:
                    crit.addEqualTo(UserDataAccess.USER_NAME,
                    pName);
                    break;
                case User.NAME_EXACT_IGNORE_CASE:
                    crit.addEqualToIgnoreCase(UserDataAccess.USER_NAME,
                    pName);
                    break;
                default:
                    throw new RemoteException("bad match");
            }

            switch (pOrder) {
                case User.ORDER_BY_ID:
                    crit.addOrderBy(UserDataAccess.USER_ID, true);
                    break;
                case User.ORDER_BY_NAME:
                    crit.addOrderBy(UserDataAccess.USER_NAME, true);
                    break;
                default:
                    throw new RemoteException("Bad order specification");
            }

            userVec =
            UserDataAccess.select(conn, crit);

        }
        catch (Exception e) {
            throw new RemoteException("getUsersByName: " + e.getMessage());
        } finally {
            try {if (conn != null) conn.close();} catch (Exception ex) {}
        }

        return userVec;
    }


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
    throws RemoteException {
        UserDataVector userVec = new UserDataVector();

        Connection conn = null;
        try {
            conn = getConnection();
            Statement stmt = conn.createStatement();

            String query =
            "SELECT distinct " +
            UserAssocDataAccess.USER_ID +
            " FROM " + UserAssocDataAccess.CLW_USER_ASSOC +
            " WHERE " +
            UserAssocDataAccess.BUS_ENTITY_ID + " = " + pStoreId +
            " or "
            + UserAssocDataAccess.BUS_ENTITY_ID +
            " in  ( SELECT distinct " +
            UserAssocDataAccess.BUS_ENTITY_ID +
            " FROM " + BusEntityAssocDataAccess.CLW_BUS_ENTITY_ASSOC +
            " WHERE " + BusEntityAssocDataAccess.BUS_ENTITY2_ID +
            " = " + pStoreId + " )" +
            " or " +
            UserAssocDataAccess.BUS_ENTITY_ID +
            " in  ( SELECT distinct " +
            BusEntityAssocDataAccess.BUS_ENTITY1_ID +
            " FROM " + BusEntityAssocDataAccess.CLW_BUS_ENTITY_ASSOC +
            " WHERE " + BusEntityAssocDataAccess.BUS_ENTITY2_ID +
            " in ( SELECT distinct " +
            BusEntityAssocDataAccess.BUS_ENTITY1_ID +
            " FROM " + BusEntityAssocDataAccess.CLW_BUS_ENTITY_ASSOC +
            " WHERE " + BusEntityAssocDataAccess.BUS_ENTITY2_ID +
            " = " + pStoreId + " ) )";

            ResultSet rs = stmt.executeQuery(query);
            IdVector uids = new IdVector();
            while (rs.next()) {
                int uid = rs.getInt(1);
                uids.add(new Integer(uid));
            }

            DBCriteria crit = new DBCriteria();
            crit.addOneOf(UserDataAccess.USER_ID, uids);

            switch (pOrder) {
                case User.ORDER_BY_ID:
                    crit.addOrderBy(UserDataAccess.USER_ID, true);
                    break;
                case User.ORDER_BY_NAME:
                    crit.addOrderBy(UserDataAccess.USER_NAME, true);
                    break;
                default:
                    throw new RemoteException
                    ("getAllStoreUsers: Bad order specification");
            }

            userVec = UserDataAccess.select(conn, crit);

        }
        catch (Exception e) {
            throw new RemoteException("getAllStoreUsers: " + e.getMessage());
        } finally {
            try {if (conn != null) conn.close();} catch (Exception ex) {}
        }

        return userVec;
    }


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
    throws RemoteException {

        UserDataVector userVec = new UserDataVector();

        Connection conn = null;
        try {
            conn = getConnection();

            Statement stmt = conn.createStatement();

            String query =
            "SELECT distinct " +
            UserAssocDataAccess.USER_ID +
            " FROM " + UserAssocDataAccess.CLW_USER_ASSOC +
            " WHERE " +
            UserAssocDataAccess.BUS_ENTITY_ID + " = " + pStoreId +
            " or "
            + UserAssocDataAccess.BUS_ENTITY_ID +
            " in  ( SELECT distinct " +
            UserAssocDataAccess.BUS_ENTITY_ID +
            " FROM " + BusEntityAssocDataAccess.CLW_BUS_ENTITY_ASSOC +
            " WHERE " + BusEntityAssocDataAccess.BUS_ENTITY2_ID +
            " = " + pStoreId + " )" +
            " or " +
            UserAssocDataAccess.BUS_ENTITY_ID +
            " in  ( SELECT distinct " +
            BusEntityAssocDataAccess.BUS_ENTITY1_ID +
            " FROM " + BusEntityAssocDataAccess.CLW_BUS_ENTITY_ASSOC +
            " WHERE " + BusEntityAssocDataAccess.BUS_ENTITY2_ID +
            " in ( SELECT distinct " +
            BusEntityAssocDataAccess.BUS_ENTITY1_ID +
            " FROM " + BusEntityAssocDataAccess.CLW_BUS_ENTITY_ASSOC +
            " WHERE " + BusEntityAssocDataAccess.BUS_ENTITY2_ID +
            " = " + pStoreId + " ) )";

            ResultSet rs = stmt.executeQuery(query);
            IdVector uids = new IdVector();
            while (rs.next()) {
                int uid = rs.getInt(1);
                uids.add(new Integer(uid));
            }

            DBCriteria crit = new DBCriteria();
            crit.addOneOf(UserDataAccess.USER_ID, uids);

            switch (pMatch) {
                case User.NAME_BEGINS_WITH:
                    crit.addLike(UserDataAccess.USER_NAME, pName + "%");
                    break;
                case User.NAME_BEGINS_WITH_IGNORE_CASE:
                    crit.addLikeIgnoreCase(UserDataAccess.USER_NAME,
                    pName + "%");
                    break;
                case User.NAME_CONTAINS:
                    crit.addLike(UserDataAccess.USER_NAME,
                    "%" + pName + "%");
                    break;
                case User.NAME_CONTAINS_IGNORE_CASE:
                    crit.addLikeIgnoreCase(UserDataAccess.USER_NAME,
                    "%" + pName + "%");
                    break;
                case User.NAME_EXACT:
                    crit.addEqualTo(UserDataAccess.USER_NAME,
                    pName);
                    break;
                case User.NAME_EXACT_IGNORE_CASE:
                    crit.addEqualToIgnoreCase(UserDataAccess.USER_NAME,
                    pName);
                    break;
                default:
                    throw new RemoteException("bad match");
            }

            switch (pOrder) {
                case User.ORDER_BY_ID:
                    crit.addOrderBy(UserDataAccess.USER_ID, true);
                    break;
                case User.ORDER_BY_NAME:
                    crit.addOrderBy(UserDataAccess.USER_NAME, true);
                    break;
                default:
                    throw new RemoteException("Bad order specification");
            }

            userVec =
            UserDataAccess.select(conn, crit);

        }
        catch (Exception e) {
            throw new RemoteException("getStoreUsersByName: " + e.getMessage());
        } finally {
            try {if (conn != null) conn.close();} catch (Exception ex) {}
        }

        return userVec;
    }


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
    throws RemoteException {
        UserDataVector userVec = new UserDataVector();

        Connection conn = null;
        try {
            conn = getConnection();

            // Get all the sites for the account.
            APIAccess factory = getAPIAccess();
            Site siteBean = factory.getSiteAPI();
            SiteDataVector sdv = siteBean.getAllSites
            (pAccountId, Site.ORDER_BY_ID);

            if (sdv.size() == 0) {
                return userVec;
            }

            IdVector siteIds = new IdVector();
            for (int i = 0; i < sdv.size(); i++) {
                SiteData sd = (SiteData) sdv.get(i);
                Integer siteid = new Integer
                (sd.getBusEntity().getBusEntityId());
                siteIds.add(siteid);
            }

            DBCriteria crit = new DBCriteria();
            crit.addOneOf(UserAssocDataAccess.BUS_ENTITY_ID,
            siteIds);
            crit.addEqualTo(UserAssocDataAccess.USER_ASSOC_CD,
            RefCodeNames.USER_ASSOC_CD.SITE);

            IdVector uids = UserAssocDataAccess.selectIdOnly
            (conn, UserAssocDataAccess.USER_ID, crit);
            if (uids.size() == 0) {
                return userVec;
            }

            crit = new DBCriteria();
            crit.addEqualTo(UserAssocDataAccess.BUS_ENTITY_ID,
            pExcludeSite);
            crit.addEqualTo(UserAssocDataAccess.USER_ASSOC_CD,
            RefCodeNames.USER_ASSOC_CD.SITE);

            IdVector exuids = UserAssocDataAccess.selectIdOnly
            (conn, UserAssocDataAccess.USER_ID, crit);

            crit = new DBCriteria();
            crit.addOneOf(UserDataAccess.USER_ID, uids);
            if (exuids.size() > 0) {
                crit.addNotOneOf(UserDataAccess.USER_ID, exuids);
            }

            switch (pOrder) {
                case User.ORDER_BY_ID:
                    crit.addOrderBy(UserDataAccess.USER_ID, true);
                    break;
                case User.ORDER_BY_NAME:
                    crit.addOrderBy(UserDataAccess.USER_NAME, true);
                    break;
                default:
                    throw new RemoteException
                    ("getAllAccountUsers: Bad order specification");
            }

            userVec = UserDataAccess.select(conn, crit);

        }
        catch (Exception e) {
            throw new RemoteException("getAllAccountUsers: " + e.getMessage());
        } finally {
            try {if (conn != null) conn.close();} catch (Exception ex) {}
        }

        return userVec;
    }


    /**
     *  Empty <code>ejbCreate</code> method.
     *
     *@exception  CreateException  if an error occurs
     *@exception  RemoteException  if an error occurs
     */
    public void ejbCreate() throws CreateException, RemoteException { }


    public UserData login(LdapItemData pKey, String pSiteId, boolean checkPwd)
    throws RemoteException, InvalidLoginException {
        Connection conn = null;
        try {
            conn = getConnection();
            return login(pKey, pSiteId, conn,checkPwd);
        }catch(InvalidLoginException e){
            throw e;
        }catch(Exception e){
            throw processException(e);
        }finally{
            closeConnection(conn);
        }
    }
    /**
     *  login the userName <p>
     *
     *  Get the user information and verify that the password stored matches the
     *  password provided. <p>
     *
     *  TODO: The pSiteId parameter should specify the store id? This would
     *  allow the same user name to exist in seperate stores. <p>
     *
     *
     *
     *@param  pKey                       the reference of LdapItemData
     *@param  pSiteId                    the application site identifier
     *@return                            UserData
     *@exception  RemoteException        Required by EJB 1.0
     *@exception  InvalidLoginException
     */
    public UserData login(LdapItemData pKey, String pSiteId)
    throws RemoteException, InvalidLoginException, InvalidAccessTokenException {
        Connection conn = null;
        try {
            conn = getConnection();
            return login(pKey, pSiteId, conn);
        }catch(InvalidLoginException e){
            throw e;
        }catch(InvalidAccessTokenException e){
            throw e;
        }catch(Exception e){
            throw processException(e);
        }finally{
            closeConnection(conn);
        }
    }

    private UserData login(LdapItemData pKey, String siteId, Connection conn)
    throws RemoteException, InvalidLoginException, InvalidAccessTokenException, SQLException {
    	return login(pKey,siteId,conn, true);
    }

    private UserData login(LdapItemData pKey, String siteId, Connection conn, boolean checkPwd)
    throws RemoteException, InvalidLoginException, InvalidAccessTokenException, SQLException {
        UserData lu = null;

        DBCriteria crit = new DBCriteria();
        if (Utility.isSet(pKey.getAccessToken())) {
            crit.addEqualTo(PropertyDataAccess.CLW_VALUE,pKey.getAccessToken());
            crit.addEqualTo(PropertyDataAccess.PROPERTY_TYPE_CD,RefCodeNames.PROPERTY_TYPE_CD.ACCESS_TOKEN);
            crit.addEqualTo(PropertyDataAccess.PROPERTY_STATUS_CD,RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
            String columnsToSelect = PropertyDataAccess.MOD_DATE + ",SYSDATE,"+PropertyDataAccess.USER_ID;
            String sql = PropertyDataAccess.getSqlSelectIdOnly(columnsToSelect, crit);
            log.info(sql);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                //compare the access token prop's mod date with the date OF THE DATABASE
                //an access token only is alive for a limited time and a logon request
                //may come from a different server than the actual logon is handled from,
                //so difference in computer clocks may be significant
                Timestamp modDate = rs.getTimestamp(PropertyDataAccess.MOD_DATE);
                Timestamp dataBaseTime = rs.getTimestamp("SYSDATE");
                int userId = rs.getInt(PropertyDataAccess.USER_ID);
                long timePassed = dataBaseTime.getTime() - modDate.getTime();
                log.info(dataBaseTime.getTime() +"-"+ modDate.getTime()+"="+timePassed);
                if(timePassed > (300 * 1000)){
                    throw new
                    InvalidAccessTokenException("Access token: '" + pKey.getAccessToken() + "' has expired.");
                }
                if(rs.next()){
                    throw new
                    InvalidAccessTokenException("Multiple users found for access token: '" + pKey.getAccessToken() + "'");
                }
                try{
                    return UserDataAccess.select(conn,userId);
                }catch(DataNotFoundException e){
                    throw new InvalidLoginException(e.getMessage());
                }
            }
            throw new InvalidAccessTokenException("No users found for access token: '" + pKey.getAccessToken() + "'");
        }
        else {
            crit.addEqualTo(UserDataAccess.USER_NAME, pKey.getUserName());
            UserDataVector udv = UserDataAccess.select(conn, crit);
            if (udv.size() == 1) {
                lu = (UserData)udv.get(0);
            } else if (udv.size() > 1) {
                logError("Multiple users found: '" + pKey.getUserName() + "'");
            }
            if (lu == null) {
                throw new InvalidLoginException("login error: Username not found.");
            }
            if(checkPwd){
            	if ( lu.getPassword() == null || lu.getPassword().length() == 0 ) {
            		return lu;
            	}
            	String passwordHash = null;
            	if (pKey.isPasswordHashed()) {
            		passwordHash = pKey.getPassword();
            	}
            	else {
            		passwordHash = PasswordUtil.getHash(pKey.getUserName(), pKey.getPassword());
            	}
            	if (!passwordHash.equals(lu.getPassword())) {
            		throw new
            		InvalidLoginException("login error: Password does not match.");
            	}
            }
            return lu;
        }
    }

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
    throws RemoteException {
        return true;
    }


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
    throws RemoteException {
        return true;
    }


    /**
     *  Insert the User information into the database.
     *
     *@param  pAddUserData         the User data.
     *@exception  RemoteException  Required by EJB 1.0
     */
    public void addUser(UserData pAddUserData)
    throws RemoteException {

        Connection conn = null;

        try {
            conn = getConnection();
            updateUserAndPasswordHistory(conn, pAddUserData, true);
        }
        catch (Exception e) {
            String msg = "addUser error: " + e.getMessage();
            logError(msg);
            throw new EJBException(msg);
        } finally {
            try {if (conn != null) conn.close();} catch (Exception ex) {}
        }
    }

    public void updateUserAccountRights(int pUserId,
					int pAccountId,
					String pRights,
					String pUpdateUserName )
        throws RemoteException {

        Connection conn = null;

        try {
            conn = getConnection();
	    BusEntityDAO.updateUserAccountRights(conn,pUserId,
						 pAccountId,
						 pRights,
						 pUpdateUserName );
	}
        catch (Exception e) {
            String msg = "updateUserAccountRights error: "
                + e.getMessage();
            logError(msg);
            throw new RemoteException(msg);
        }
        finally {
            closeConnection(conn);
        }
	}
    
    public void updateUser(UserData pUpdateUserData, int pUserId)
    throws RemoteException {
    	updateUser(pUpdateUserData, pUserId, false);
    }


    /**
     *  Updates the user information values to be used by the request.
     *
     *@param  pUpdateUserData      the user data.
     *@param  pUserId              the user identifier.
     *@exception  RemoteException  Required by EJB 1.0
     */
    public void updateUser(UserData pUpdateUserData, int pUserId, boolean isResetPassword)
    throws RemoteException {

        Connection conn = null;

        try {
            conn = getConnection();
            pUpdateUserData.setUserId(pUserId);
            updateUserAndPasswordHistory(conn, pUpdateUserData, false, isResetPassword);
        }
        catch (Exception e) {
            String msg = "updateUser error: " + e.getMessage();
            logError(msg);
            throw new EJBException(msg);
        } finally {
            // notify of updates should any sistes be cached.
            CacheManager.rollCacheVersion();
            closeConnection(conn);
        }

    }


    public void updateUserGroups( int pUserId,
				  java.util.ArrayList pGroupIds ,
				  String pModUser)
    throws RemoteException {

        Connection conn = null;
        String usrName = pModUser;
        try {
            conn = getConnection();

	    logDebug("updateUserGroups newGroupIdList size::"+
		     pGroupIds.size());
	    Group group = getAPIAccess().getGroupAPI();
	    Map existingUserMap=group.getGroupsUserIsMemberOf(pUserId,true);

	    Iterator itx = existingUserMap.keySet().iterator();
	    String usergrps = "pUserId=" + pUserId + " groups=";
	    logDebug("1 " +usergrps);
	    while(itx.hasNext()){
	    	logDebug("1.1 " +usergrps);
	    	Object o = (Object)itx.next();
	    	if ( null == o ) continue;
	    	Integer groupId = (Integer) o;
	    	usergrps += " " + groupId;
	    	logDebug("1.2 " +usergrps);
	    }
	    logDebug(usergrps);

	    //first find out what we need to add
	    Iterator it = pGroupIds.iterator();
	    while(it.hasNext()){
	    	Integer groupId = (Integer) it.next();
	    	logDebug("examining group id: " + groupId);
	    	if(!existingUserMap.containsKey(groupId)){
	    		//add mapping
	    		group.addUserToGroupId(pUserId,groupId.intValue(),usrName);

	    		logDebug("adding: " + groupId
	    				+ " for user: " + pUserId );
	    	}else{
	    		//anything left at the end will need to be removed.
	    		existingUserMap.remove(groupId);
	    	}
	    }
	    //now remove the leftovers
	    java.util.Set keys = existingUserMap.keySet();
	    Iterator it2 = keys.iterator();
	    while(it2.hasNext()){
	    	Integer key = (Integer) it2.next();
	    	group.removeUserFromGroupId(pUserId,key.intValue(), usrName);
	    	logDebug("1 removing group: " + key.intValue()
			 + " for user: " + pUserId );
	    }
        }
        catch (Exception e) {
            String msg = "updateUserGroups error: " + e.getMessage();
            logError(msg);
            throw new EJBException(msg);
        } finally {
	    closeConnection(conn);
        }

    }


    /**
     *  Updates the user information values to be used by the request.
     *
     *@param  pUpdateContact       the users contact info data.
     *@param  pUserId              the user identifier.
     *@exception  RemoteException  Required by EJB 1.0
     */
    public void updateContact(UserInfoData pUpdateContact, int pUserId)
    throws RemoteException {

        Connection conn = null;
        try {
            conn = getConnection();

            UserData userData = pUpdateContact.getUserData();
            AddressData addressData = pUpdateContact.getAddressData();
            EmailData emailData = pUpdateContact.getEmailData();

            userData.setUserId(pUserId);
            updateUserAndPasswordHistory(conn, userData, false);

            addressData.setUserId(pUserId);
            AddressDataAccess.update(conn, addressData);

            emailData.setUserId(pUserId);
            EmailDataAccess.update(conn, emailData);

        }
        catch (Exception e) {
            String msg = "updateContact error: " + e.getMessage();
            logError(msg);
            throw new EJBException(msg);
        } finally {
            try {if (conn != null) conn.close();} catch (Exception ex) {}
        }
    }



    /**
     *  Describe <code>addUserInfo</code> method here.
     *
     *@param  pUserInfoData               a <code>UserInfoData</code> value
     *@return                             a <code>UserInfoData</code> value
     *@exception  RemoteException         if an error occurs
     *@exception  DuplicateNameException  Description of Exception
     */
    public UserInfoData addUserInfo(UserInfoData pUserInfoData)
    throws RemoteException, DuplicateNameException {
        return updateUserInfo(pUserInfoData);
    }


    /**
     *  Updates the user information values to be used by the request.
     *
     *@param  pUserInfoData               the UserInfoData user info data.
     *@return                             a <code>UserInfoData</code> value
     *@exception  DuplicateNameException  Description of Exception
     *@throws  RemoteException            Required by EJB 1.0
     */
    public UserInfoData updateUserInfo(UserInfoData pUserInfoData)
    throws RemoteException, DuplicateNameException {
        Connection conn = null;

        try {
            conn = getConnection();
            return updateUserInfo(conn, pUserInfoData);
        }
        catch (DuplicateNameException ne) {
            throw ne;
        }
        catch (Exception e) {
        	e.printStackTrace();
        	if (e.getMessage()== null)
        		throw new RemoteException("updateUserInfo: " + e);
        	else
        		throw new RemoteException("updateUserInfo: " + e.getMessage());
        } finally {
            try {if (conn != null) conn.close();} catch (Exception ex) {}
        }
    }

    /**
     *  Updates the user information values to be used by the request.
     *
     *@param  pUserInfoData               the UserInfoData user info data.
     *@param  pDatasource                 the String Datasource
     *@return                             a <code>UserInfoData</code> value
     *@exception  DuplicateNameException  Description of Exception
     *@throws  RemoteException            Required by EJB 1.0
     */
    public UserInfoData updateUserInfo(UserInfoData pUserInfoData, String pDatasource)
    throws RemoteException, DuplicateNameException {
        Connection conn = null;

        try {
            conn = getConnection(pDatasource);
            return updateUserInfo(conn, pUserInfoData);
        }
        catch (DuplicateNameException ne) {
            throw ne;
        }
        catch (Exception e) {
        	e.printStackTrace();
        	if (e.getMessage()== null)
        		throw new RemoteException("updateUserInfo: " + e);
        	else
        		throw new RemoteException("updateUserInfo: " + e.getMessage());
        } finally {
            try {if (conn != null) conn.close();} catch (Exception ex) {}
        }
    }
    
    /**
     *  Updates the user information values to be used by the request.
     *
     *@param  pUserInfoData               the UserInfoData user info data.
     *@return                             a <code>UserInfoData</code> value
     *@exception  DuplicateNameException  Description of Exception
     *@throws  RemoteException            Required by EJB 1.0
     */
    UserInfoData updateUserInfo(Connection conn, UserInfoData pUserInfoData)
    throws Exception{
        UserData user = pUserInfoData.getUserData();
        if (user.isDirty()) {
            // check that name is unique
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(UserDataAccess.USER_NAME,
            user.getUserName());
            if (user.getUserId() != 0) {
                crit.addNotEqualTo(UserDataAccess.USER_ID,
                user.getUserId());
            }
            log.info("UserBean UUUUUUUUUUUUUUUUU check duplicated user: "+UserDataAccess.getSqlSelectIdOnly("*", crit));
            IdVector dups =
            UserDataAccess.selectIdOnly(conn, crit);
            if (dups.size() > 0) {
                throw new
                DuplicateNameException(UserDataAccess.USER_NAME);
            }

            if(!Utility.isSet(user.getUserStatusCd())){
                user.setUserStatusCd(RefCodeNames.USER_STATUS_CD.ACTIVE);
            }

            if (user.getUserId() == 0) {
            	updateUserAndPasswordHistory(conn, user, true);
            }
            else {
            	updateUserAndPasswordHistory(conn, user, false);
            }
        }
        int userId = pUserInfoData.getUserData().getUserId();

        EmailData email =
        pUserInfoData.getEmailData();
        if (email.getEmailAddress()!=null)
        	email.setEmailAddress(email.getEmailAddress().trim());
        if (email.isDirty()) {
        	EmailData ex = getPrimaryEmailForUser(conn, userId);
        	if(ex != null){
        		email.setEmailId(ex.getEmailId());
        		email.setAddBy(ex.getAddBy());
        		email.setAddDate(ex.getAddDate());
        	}

            email.setUserId(userId);
            email.setEmailTypeCd(RefCodeNames.EMAIL_TYPE_CD.PRIMARY_CONTACT);
            email.setEmailStatusCd(RefCodeNames.EMAIL_STATUS_CD.ACTIVE);
            email.setPrimaryInd(true);
            if (email.getEmailId() == 0) {
                EmailDataAccess.insert(conn, email);
            }else {
                EmailDataAccess.update(conn, email);
            }
        }

        PhoneData phone = pUserInfoData.getPhone();
        if (phone.getPhoneNum()!=null)
        	phone.setPhoneNum(phone.getPhoneNum().trim());
        if (phone.isDirty()) {
        	PhoneData p = getPhoneForUser(conn,userId,RefCodeNames.PHONE_TYPE_CD.PHONE);
        	if(p != null){
        		phone.setPhoneId(p.getPhoneId());
        		phone.setAddBy(p.getAddBy());
        		phone.setAddDate(p.getAddDate());
        	}

            phone.setUserId(userId);
            phone.setPhoneStatusCd(RefCodeNames.PHONE_STATUS_CD.ACTIVE);
            phone.setPhoneTypeCd(RefCodeNames.PHONE_TYPE_CD.PHONE);
            phone.setPrimaryInd(true);
            if (phone.getPhoneId() == 0) {
                PhoneDataAccess.insert(conn, phone);
            }else {
                PhoneDataAccess.update(conn, phone);
            }
        }

        PhoneData fax = pUserInfoData.getFax();
        if (fax.getPhoneNum()!=null)
        	fax.setPhoneNum(fax.getPhoneNum().trim());
        if (fax.isDirty()) {
        	PhoneData p = getPhoneForUser(conn,userId,RefCodeNames.PHONE_TYPE_CD.FAX);
        	if(p != null){
        		fax.setPhoneId(p.getPhoneId());
        		fax.setAddBy(p.getAddBy());
        		fax.setAddDate(p.getAddDate());
        	}

            fax.setUserId(userId);
            fax.setPhoneStatusCd(RefCodeNames.PHONE_STATUS_CD.ACTIVE);
            fax.setPhoneTypeCd(RefCodeNames.PHONE_TYPE_CD.FAX);
            fax.setPrimaryInd(true);
            if (fax.getPhoneId() == 0) {
                PhoneDataAccess.insert(conn, fax);
            }else {
                PhoneDataAccess.update(conn, fax);
            }
        }

        PhoneData mobile = pUserInfoData.getMobile();
        if (mobile.getPhoneNum() != null)
        	mobile.setPhoneNum(mobile.getPhoneNum().trim());
        if (mobile.isDirty()) {
        	PhoneData p = getPhoneForUser(conn,userId,RefCodeNames.PHONE_TYPE_CD.MOBILE);
        	if(p != null){
        		mobile.setPhoneId(p.getPhoneId());
        		mobile.setAddBy(p.getAddBy());
        		mobile.setAddDate(p.getAddDate());
        	}

            mobile.setUserId(userId);
            mobile.setPhoneStatusCd(RefCodeNames.PHONE_STATUS_CD.ACTIVE);
            mobile.setPhoneTypeCd(RefCodeNames.PHONE_TYPE_CD.MOBILE);
            mobile.setPrimaryInd(true);
            if (mobile.getPhoneId() == 0) {
                PhoneDataAccess.insert(conn, mobile);
            }else {
                PhoneDataAccess.update(conn, mobile);
            }
        }

        AddressData address = pUserInfoData.getAddressData();
        if (address.isDirty()) {
        	AddressData a = getPrimaryAddressForUser(conn,userId);
        	if(a != null){
        		address.setAddressId(a.getAddressId());
        		address.setAddBy(a.getAddBy());
        		address.setAddDate(a.getAddDate());
        	}

            address.setUserId(userId);
            address.setAddressStatusCd(RefCodeNames.ADDRESS_STATUS_CD.ACTIVE);
            address.setAddressTypeCd(RefCodeNames.ADDRESS_TYPE_CD.PRIMARY_CONTACT);
            address.setPrimaryInd(true);
            if (address.getAddressId() == 0) {
                AddressDataAccess.insert(conn, address);
            }
            else {
                AddressDataAccess.update(conn, address);
            }
        }

	    // Groups are updated by calling updateUserGroups.
            // Update any changes to the permissions per account.
            pUserInfoData.setUserAccountRights
                (getUserAccountRights(conn,userId));



        PropertyUtil putil = new PropertyUtil(conn);
        if(Utility.isSet(pUserInfoData.getCustomerSystemKey())){
        	String pt = RefCodeNames.PROPERTY_TYPE_CD.CUSTOMER_SYSTEM_KEY;
        	putil.saveValue(userId,0,pt,pt,pUserInfoData.getCustomerSystemKey());
        }



        return pUserInfoData;
    }

    /**
     * Returns the phone data for the user of the specified type (mobile, fax, etc)
     */
    private PhoneData getPhoneForUser(Connection pCon, int userId, String pType)
    throws SQLException{
    	DBCriteria crit = new DBCriteria();
    	crit.addEqualTo(PhoneDataAccess.USER_ID,userId);
    	crit.addEqualTo(PhoneDataAccess.PHONE_TYPE_CD,pType);
    	PhoneDataVector dv = PhoneDataAccess.select(pCon,crit);
    	if(!dv.isEmpty()){
    		return (PhoneData)(dv.get(0));
    	}
    	return null;
    }

    /**
     * Returns the primary email address for this user, null if there is not one currently setup
     */
    private EmailData getPrimaryEmailForUser(Connection pCon, int userId)
    throws SQLException{
    	DBCriteria crit = new DBCriteria();
    	crit.addEqualTo(EmailDataAccess.USER_ID,userId);
    	crit.addEqualTo(EmailDataAccess.EMAIL_TYPE_CD,RefCodeNames.EMAIL_TYPE_CD.PRIMARY_CONTACT);
    	EmailDataVector dv = EmailDataAccess.select(pCon,crit);
    	if(!dv.isEmpty()){
    		return (EmailData)(dv.get(0));
    	}
    	return null;
    }

    /**
     * Returns the primary email address for this user, null if there is not one currently setup
     */
    private AddressData getPrimaryAddressForUser(Connection pCon, int userId)
    throws SQLException{
    	DBCriteria crit = new DBCriteria();
    	crit.addEqualTo(AddressDataAccess.USER_ID,userId);
    	crit.addEqualTo(AddressDataAccess.ADDRESS_TYPE_CD,RefCodeNames.ADDRESS_TYPE_CD.PRIMARY_CONTACT);
    	AddressDataVector dv = AddressDataAccess.select(pCon,crit);
    	if(!dv.isEmpty()){
    		return (AddressData)(dv.get(0));
    	}
    	return null;
    }

    /**
     *  Updates the user profile. Similar to updateUserInfo, but doesn't save user configuration
     *
     *@param  pUserInfoData               the UserInfoData user info data.
     *@return                             a <code>UserInfoData</code> value
     *@exception  DuplicateNameException  Description of Exception
     *@throws  RemoteException            Required by EJB 1.0
     */
    public UserInfoData updateUserProfile(UserInfoData pUserInfoData)
    throws RemoteException, DuplicateNameException {
        Connection conn = null;

        try {
            conn = getConnection();

            UserData user = pUserInfoData.getUserData();
            if (user.isDirty()) {
                // check that name is unique
                DBCriteria crit = new DBCriteria();
                crit.addEqualTo(UserDataAccess.USER_NAME,
                user.getUserName());
                if (user.getUserId() != 0) {
                    crit.addNotEqualTo(UserDataAccess.USER_ID,
                    user.getUserId());
                }
                IdVector dups =
                UserDataAccess.selectIdOnly(conn, crit);
                if (dups.size() > 0) {
                    throw new
                    DuplicateNameException(UserDataAccess.USER_NAME);
                }
                if (user.getUserId() == 0) {
                	updateUserAndPasswordHistory(conn, user, true);
                }
                else {
                	updateUserAndPasswordHistory(conn, user, false);
                }
            }
            int userId = pUserInfoData.getUserData().getUserId();

            EmailData email =
            pUserInfoData.getEmailData();
            if (email.isDirty()) {
                if (email.getEmailId() == 0) {
                    email.setUserId(userId);
                    email.setEmailTypeCd(RefCodeNames.EMAIL_TYPE_CD.PRIMARY_CONTACT);
                    email.setEmailStatusCd(RefCodeNames.EMAIL_STATUS_CD.ACTIVE);
                    email.setPrimaryInd(true);
                    EmailDataAccess.insert(conn, email);
                }
                else {
                    EmailDataAccess.update(conn, email);
                }
            }

            PhoneData phone = pUserInfoData.getPhone();
            if (phone.isDirty()) {
                if (phone.getPhoneId() == 0) {
                    phone.setUserId(userId);
                    phone.setPhoneStatusCd(RefCodeNames.PHONE_STATUS_CD.ACTIVE);
                    phone.setPhoneTypeCd(RefCodeNames.PHONE_TYPE_CD.PHONE);
                    phone.setPrimaryInd(true);
                    PhoneDataAccess.insert(conn, phone);
                }
                else {
                    PhoneDataAccess.update(conn, phone);
                }
            }

            PhoneData fax = pUserInfoData.getFax();
            if (fax.isDirty()) {
                if (fax.getPhoneId() == 0) {
                    fax.setUserId(userId);
                    fax.setPhoneStatusCd(RefCodeNames.PHONE_STATUS_CD.ACTIVE);
                    fax.setPhoneTypeCd(RefCodeNames.PHONE_TYPE_CD.FAX);
                    fax.setPrimaryInd(true);
                    PhoneDataAccess.insert(conn, fax);
                }
                else {
                    PhoneDataAccess.update(conn, fax);
                }
            }

            PhoneData mobile = pUserInfoData.getMobile();
            if (mobile.isDirty()) {
                if (mobile.getPhoneId() == 0) {
                    mobile.setUserId(userId);
                    mobile.setPhoneStatusCd(RefCodeNames.PHONE_STATUS_CD.ACTIVE);
                    mobile.setPhoneTypeCd(RefCodeNames.PHONE_TYPE_CD.MOBILE);
                    mobile.setPrimaryInd(true);
                    PhoneDataAccess.insert(conn, mobile);
                }
                else {
                    PhoneDataAccess.update(conn, mobile);
                }
            }

            AddressData address = pUserInfoData.getAddressData();
            if (address.isDirty()) {
                if (address.getAddressId() == 0) {
                    address.setUserId(userId);
                    address.setAddressStatusCd(RefCodeNames.ADDRESS_STATUS_CD.ACTIVE);
                    address.setAddressTypeCd(RefCodeNames.ADDRESS_TYPE_CD.PRIMARY_CONTACT);
                    address.setPrimaryInd(true);
                    AddressDataAccess.insert(conn, address);
                }
                else {
                    AddressDataAccess.update(conn, address);
                }
            }
        }
        catch (DuplicateNameException ne) {
            throw ne;
        }
        catch (Exception e) {
           e.printStackTrace();
           throw new RemoteException("updateUserProfile: " + e.getMessage());
        } finally {
            try {if (conn != null) conn.close();} catch (Exception ex) {}
        }

        return pUserInfoData;
    }

    /**
     *  Adds the User Property information values to be used by the request.
     *
     *@param  pAddUserPropertyData  the User Property data.
     *@exception  RemoteException   Required by EJB 1.0
     */
    public void addUserProperty(UserPropertyData pAddUserPropertyData)
    throws RemoteException { }


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
    throws RemoteException { }

    public void updateUserProperty(int pUserId, int pBusEntityId, String pPropertyTypeCd)
    throws RemoteException{
    	
    	PropertyService propService =null;
    	PropertyData pData = PropertyData.createValue();
    	
    	try{
    		
    		propService = APIAccess.getAPIAccess().getPropertyServiceAPI();
    		
    		PropertyDataVector pdv = propService.getProperties(pUserId, pBusEntityId, pPropertyTypeCd);
    		
    		if(pdv!=null && pdv.size()>0){
    			pData = (PropertyData)pdv.get(0);
    			pData.setBusEntityId(pBusEntityId);
    			pData.setValue(Integer.toString(pBusEntityId));
    			propService.update(pData);
    		}else{
    			propService.setUserProperty(pUserId, pPropertyTypeCd, Integer.toString(pBusEntityId));
    		}
    		
    	}catch (APIServiceAccessException e) {
    		logError("e.getMessage");
    		e.printStackTrace();
    		throw new RemoteException("Error. UserBean.updateUserProperty(), APIAccess error.");
         
    	}catch (Exception e) {
    		logError("e.getMessage");
    		e.printStackTrace();
            throw new RemoteException("Error. UserBean.updateUserProperty(), Exception happened");
        } 
    }


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
    public UserAssocData addUserAssoc(int pUserId, int pBusEntityId, String pType)
    throws RemoteException, DataNotFoundException {
        UserAssocData userAssocRD = null;
        Connection con = null;
        BusEntityData busEntityD = null;
        try {
            con = getConnection();
            userAssocRD = addUserAssoc(con, pUserId, pBusEntityId, pType);
        }
        catch (NamingException exc) {
            logError("exc.getMessage");
            exc.printStackTrace();
            throw new RemoteException("Error. UserBean.addUserAssoc() Naming Exception happened");
        }
        catch (SQLException exc) {
            logError("exc.getMessage");
            exc.printStackTrace();
            throw new RemoteException("Error. UserBean.addUserAssoc() SQL Exception happened");
        } finally {
            try {if (con != null) con.close();} catch (Exception ex) {}
        }
        return userAssocRD;
    }


    /**
     *  Adds a feature to the UserAssoc attribute of the UserBean object
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
    public UserAssocData addUserAssoc(Connection con, int pUserId,
                                      int pBusEntityId, String pType)
    throws SQLException, DataNotFoundException, RemoteException {
        DBCriteria dbc = new DBCriteria();
        dbc.addEqualTo(UserAssocDataAccess.USER_ID, pUserId);
        dbc.addEqualTo(UserAssocDataAccess.BUS_ENTITY_ID, pBusEntityId);

        logDebug("add user assoc: "
                 + " pUserId=" + pUserId
                 + " pBusEntityId=" + pBusEntityId
                 + " pType=" + pType
                 );

        UserAssocDataVector userAssocV = UserAssocDataAccess.select(con, dbc);
        if (userAssocV.size() > 0) {
            UserAssocData userAssocD = (UserAssocData) userAssocV.get(0);
            logDebug("found user assoc: " + userAssocD);
            return userAssocD;
        }

        UserData userD = UserDataAccess.select(con, pUserId);
        //just to be sure that it exists
        BusEntityData busEntityD = BusEntityDataAccess.select(con, pBusEntityId);
        //String assocType = RefCodeNames.USER_ASSOC_CD.SITE;
        String assocType = pType;
        String busEntityType = busEntityD.getBusEntityTypeCd();

        UserAssocData userAssocD = UserAssocData.createValue();
        userAssocD.setUserId(pUserId);
        userAssocD.setBusEntityId( pBusEntityId);
        userAssocD.setUserAssocCd(assocType);

        return addUserAssoc(con, userAssocD);
    }

    public void updateUserAssoc(int pUserId,
                                IdVector pStoreIdsToAdd,
                                IdVector pAccountIdsToAdd,
                                IdVector pSiteIdsToAdd,
                                IdVector pStoreIdsToDelete,
                                IdVector pAccountIdsToDelete,
                                IdVector pSiteIdsToDelete,
                                String pUserName) throws RemoteException {

        if (pSiteIdsToDelete != null && !pSiteIdsToDelete.isEmpty()) {
            logDebug("updateUserAssoc() => Site Ids To Remove: " + pSiteIdsToDelete.size());
            for (Object oSiteId : pSiteIdsToDelete) {
                removeUserAssoc(pUserId, (Integer) oSiteId);
            }
        }

        if (pAccountIdsToDelete != null && !pAccountIdsToDelete.isEmpty()) {
            logDebug("updateUserAssoc() => Account Ids To Remove: " + pAccountIdsToDelete);
            for (Object oAccountId : pAccountIdsToDelete) {
                removeUserAssoc(pUserId, (Integer) oAccountId);
            }
        }

        if (pStoreIdsToDelete != null && !pStoreIdsToDelete.isEmpty()) {
            logDebug("updateUserAssoc() => Store Ids To Remove: " + pStoreIdsToDelete);
            for (Object oStoreId : pStoreIdsToDelete) {
                removeUserAssoc(pUserId, (Integer) oStoreId);
            }
        }

        if (pStoreIdsToAdd != null && !pStoreIdsToAdd.isEmpty()) {
            logDebug("updateUserAssoc() => Store Ids To Add: " + pStoreIdsToAdd);
            List<IdVector> packs = Utility.createPackages(pStoreIdsToAdd, 1000);
            for (IdVector pack : packs) {
                addBusEntityAssociations(pUserId, pack, RefCodeNames.BUS_ENTITY_TYPE_CD.STORE, pUserName);
            }
        }

        if (pAccountIdsToAdd != null && !pAccountIdsToAdd.isEmpty()) {
            logDebug("updateUserAssoc() => Account Ids To Add: " + pAccountIdsToAdd);
            List<IdVector> packs = Utility.createPackages(pAccountIdsToAdd, 1000);
            for (IdVector pack : packs) {
                addBusEntityAssociations(pUserId, pack, RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT, pUserName);
            }
        }

        if (pSiteIdsToAdd != null && !pSiteIdsToAdd.isEmpty()) {
            logDebug("updateUserAssoc() => Site Ids To Add: " + pSiteIdsToAdd);
            List<IdVector> packs = Utility.createPackages(pSiteIdsToAdd, 1000);
            for (IdVector pack : packs) {
                addBusEntityAssociations(pUserId, pack, RefCodeNames.BUS_ENTITY_TYPE_CD.SITE, pUserName);
            }
        }

    }


    /**
     *  Adds a feature to the UserAssoc attribute of the UserBean object
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
    throws RemoteException, SQLException {
        Date date = new Date(System.currentTimeMillis());
        pUserAssoc.setAddBy("-1-");
        pUserAssoc.setAddDate(date);
        pUserAssoc.setModBy("-1-");
        pUserAssoc.setModDate(date);

        DBCriteria crit = new DBCriteria();
        crit.addEqualTo(UserAssocDataAccess.USER_ID,pUserAssoc.getUserId());
        crit.addEqualTo(UserAssocDataAccess.USER_ASSOC_CD,pUserAssoc.getUserAssocCd());
        crit.addEqualTo(UserAssocDataAccess.BUS_ENTITY_ID,pUserAssoc.getBusEntityId());

        UserAssocDataVector udv = UserAssocDataAccess.select(con,crit);
        if(udv.isEmpty()){
        	UserAssocData mUserAssoc = UserAssocDataAccess.insert(con, pUserAssoc);
        	return mUserAssoc;
        }else{
        	return (UserAssocData) udv.get(0);
        }

    }


    /**
     *  Removes the user association information values
     *
     *@param  pUserId           the catalog id.
     *@param  pBusEntityId      the business entity id.
     *@throws  RemoteException  Required by EJB 1.0
     */
    public void removeUserAssoc(int pUserId, int pBusEntityId)
    throws RemoteException {
        Connection con = null;
        try {
            con = getConnection();

            // The following condition takes care of removing
            // direct bus entity relations to this user
            // as well as any bus entities related to this
            // bus entity.
            String allbusentities = " select bus_entity1_id from "
            + " clw_bus_entity_assoc where"
            + " bus_entity2_id = " + pBusEntityId
            + " union select " + pBusEntityId + " from dual ";

            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(UserAssocDataAccess.USER_ID, pUserId);
            dbc.addOneOf(UserAssocDataAccess.BUS_ENTITY_ID, allbusentities);
            UserAssocDataAccess.remove(con, dbc);
        }
        catch (Exception e) {
            throw new RemoteException("Error. UserBean.removeUserAssoc() Exception happened");
        } finally {
            closeConnection(con);
        }
    }


    /**
     *  Determins wherther exists the user-bus entity association
     *
     *@param  pUserId
     *@param  pBusEntityId
     *@return                   boolean true if found
     *@throws  RemoteException
     */
    public boolean doesUserAssocExist(int pUserId, int pBusEntityId)
    throws RemoteException {
        Connection con = null;
        boolean retValue = false;
        BusEntityDataVector busEntityDV = new BusEntityDataVector();
        try {
            con = getConnection();
            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(UserAssocDataAccess.USER_ID, pUserId);
            dbc.addEqualTo(UserAssocDataAccess.BUS_ENTITY_ID, pBusEntityId);
            IdVector idV = UserAssocDataAccess.selectIdOnly(con, dbc);
            if (idV.size() > 0) {
                retValue = true;
            }
        }
        catch (Exception e) {
            throw new RemoteException("Error. UserBean.doesUserAssocExist() Exception happened");
        } finally {
            try {if (con != null) con.close();} catch (Exception ex) {}
        }
        return retValue;
    }


    /**
     *  Description of the Method
     *
     *@param  pGeneratePassword          Description of Parameter
     *@param  pUserId                    Description of Parameter
     *@param  pSubject                   Description of Parameter
     *@param  pMessageStart              Description of Parameter
     *@param  pMessageEnd                Description of Parameter
     *@exception  RemoteException        Description of Exception
     *@exception  InvalidLoginException  Description of Exception
     *@exception  DataNotFoundException  Description of Exception
     */
    public void sendPasswordEmail(boolean pGeneratePassword,
    int pUserId, String pSubject,
    String pMessageStart,
    String pMessageEnd)
    throws RemoteException, InvalidLoginException, DataNotFoundException {

        UserInfoData ud = getUserContact(pUserId);
        if (ud == null) {
            logError("User.sendPasswordEmail: Error: " +
		     " pUserId=" + pUserId +
		     "\n  missing user data, ud=" + ud );

            throw new RemoteException("Email could not be sent.");
        }

        PasswordUtil passUtil = PasswordUtil.getGeneratorInstance();
        String newpwd = passUtil.generatePhrase(2, 4, 6, "-");
        ud.getUserData().setPassword
	    (PasswordUtil.getHash(ud.getUserData().getUserName(), newpwd));

        String msg = pMessageStart + newpwd + pMessageEnd;
        APIAccess factory = null;
        EmailClient emailClientEjb = null;
        Store storeEjb = null;
        try {
            factory = APIAccess.getAPIAccess();
            emailClientEjb = factory.getEmailClientAPI();
            storeEjb = factory.getStoreAPI();
        }
        catch (Exception exc) {
            String mess = "UserBean.sendPasswordEmail. No API access";
            logError(mess);
            throw new RemoteException(mess);
        }

	logDebug("email data: " + ud.getEmailData());

        if (ud.getEmailData() == null ||
	    ud.getEmailData().getEmailAddress() == null ||
	    ud.getEmailData().getEmailAddress().trim().length() <= 2) {
            logError("User.sendPasswordEmail: Error: invalid email address. " +
            "\n  user data:" + ud + "\n  could not send email message:" +
            "\n  " + msg);
            throw new RemoteException("Email could not be sent.");
        }
        else {

            try {
                //should figure out the store and grab appropriate from address here, but users belong to multiple stores.
                //maybe determine by url user came in on?  For now just leave as default from.
     /*           emailClientEjb.send
		(ud.getEmailData().getEmailAddress(),null,
		 null,pSubject,msg,null,pUserId,
		 RefCodeNames.EMAIL_TRACKING_CD.USER_PASSWORD,
		 0, pUserId, "User Id: "+pUserId);*/

            	//get the default email address from store for from_addr
            	if(ud.getUserData().getUserTypeCd().equals(RefCodeNames.USER_TYPE_CD.ADMINISTRATOR)){
            		emailClientEjb.send
            		(ud.getEmailData().getEmailAddress(),null,
            		 null,pSubject,msg,null,pUserId,
            		 RefCodeNames.EMAIL_TRACKING_CD.USER_PASSWORD,
            		 0, pUserId, "User Id: "+pUserId);
            	}else{
            		String fromEmail = storeEjb.getDefaultEmailForStoreUser(pUserId);
            		emailClientEjb.send
            		(ud.getEmailData().getEmailAddress(),fromEmail,
            		 null,pSubject,msg,null,pUserId,
            		 RefCodeNames.EMAIL_TRACKING_CD.USER_PASSWORD,
            		 0, pUserId, "User Id: "+pUserId);
            	}

             } catch (Exception exc) {
                String mess = "UserBean.sendPasswordEmail. Email Error";
                logError(mess);
                throw new RemoteException(mess);
            }

            updateUser(ud.getUserData(), pUserId);
        }
        return;
    }

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
    throws SQLException, DataNotFoundException, RemoteException {

        UserAssocData userAssocRes = null;
        Connection con = null;
        int result = 0;

        try{
            con = getConnection();
            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(UserAssocDataAccess.USER_ID, pUserId);
            dbc.addEqualTo(UserAssocDataAccess.BUS_ENTITY_ID, pBusEntityId);
            UserAssocDataVector userAssocV = UserAssocDataAccess.select(con, dbc);

            if(userAssocV.size() == 0){
                //the associated account may have changed so we'll get it by type
                DBCriteria dbc2 = new DBCriteria();
                dbc2.addEqualTo(UserAssocDataAccess.USER_ID, pUserId);
                dbc2.addEqualTo(UserAssocDataAccess.USER_ASSOC_CD, pType);
                userAssocV = UserAssocDataAccess.select(con, dbc2);
            }
            UserAssocData userAssoc = (UserAssocData) userAssocV.get(0);

            if (userAssocV.size() > 1 || userAssocV.size() == 0) {
                return 0;
            }

            String assocType = pType;
            Date date = new Date(System.currentTimeMillis());
            userAssoc.setBusEntityId(pBusEntityId);
            userAssoc.setUserAssocCd(assocType);
            userAssoc.setModDate(date);

            result = UserAssocDataAccess.update(con, userAssoc);

        }catch (NamingException exc) {
            logError("exc.getMessage");
            exc.printStackTrace();
            throw new RemoteException("Error. UserBean.addUserAssoc() Naming Exception happened");
        }
        catch (SQLException exc) {
            logError("exc.getMessage");
            exc.printStackTrace();
            throw new RemoteException("Error. UserBean.addUserAssoc() SQL Exception happened");
        } finally {
            try {if (con != null) con.close();} catch (Exception ex) {}
        }
        return result;
    }
    //--------------------------------------------------------------------------
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
    throws DataNotFoundException, RemoteException {
	return createUserClone(pUserId,pAdmin, null,null);
    }

    public int createUserClone
	(int pUserId, String pAdmin,
	 String pNewUserTypeCd, String pNewUserStatusCd)
    throws DataNotFoundException, RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            //Read data
            UserData userD = UserDataAccess.select(conn,pUserId);
            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(UserAssocDataAccess.USER_ID,pUserId);
            UserAssocDataVector userAssocDV = UserAssocDataAccess.select(conn,dbc);
            dbc = new DBCriteria();
            dbc.addEqualTo(GroupAssocDataAccess.USER_ID,pUserId);
            GroupAssocDataVector groupAssocDV = GroupAssocDataAccess.select(conn,dbc);

            dbc = new DBCriteria();
            dbc.addEqualTo(AddressDataAccess.USER_ID,pUserId);
            AddressDataVector addressDV = AddressDataAccess.select(conn,dbc);

            dbc = new DBCriteria();
            dbc.addEqualTo(PhoneDataAccess.USER_ID,pUserId);
            PhoneDataVector phoneDV = PhoneDataAccess.select(conn,dbc);

            dbc = new DBCriteria();
            dbc.addEqualTo(PropertyDataAccess.USER_ID, pUserId);
            dbc.addEqualTo(PropertyDataAccess.SHORT_DESC, RefCodeNames.PROPERTY_TYPE_CD.USER_ACCOUNT_RIGHTS);
            PropertyDataVector acctRightsDV = PropertyDataAccess.select(conn, dbc);

            //Modify data
            Date now = new Date();
            UserData newUserD = UserData.createValue();

            String userName = "Created from user "+pUserId;
            if(userName.length()>30) userName = userName.substring(0,30);
            newUserD.setUserName(userName);


	    if ( null != pNewUserStatusCd &&
		 pNewUserStatusCd.length() > 0 ) {
		newUserD.setUserStatusCd(pNewUserStatusCd);
	    } else {
		newUserD.setUserStatusCd(RefCodeNames.USER_STATUS_CD.LIMITED);
	    }
            newUserD.setUserRoleCd(userD.getUserRoleCd());

	    if ( null != pNewUserTypeCd && pNewUserTypeCd.length() > 0) {
		newUserD.setUserTypeCd(pNewUserTypeCd);
	    } else {
		newUserD.setUserTypeCd(userD.getUserTypeCd());
	    }
            newUserD.setEffDate(now);
            newUserD.setExpDate(null);
            newUserD.setPrefLocaleCd(userD.getPrefLocaleCd());
            newUserD.setWorkflowRoleCd(userD.getWorkflowRoleCd());
            newUserD.setAddBy(pAdmin);
            newUserD.setModBy(pAdmin);

            newUserD = updateUserAndPasswordHistory(conn, newUserD, true);
            int nUserId = newUserD.getUserId();
            for(int ii=0; ii<userAssocDV.size(); ii++) {
                UserAssocData uaD = (UserAssocData) userAssocDV.get(ii);
                uaD.setUserAssocId(0);
                uaD.setUserId(nUserId);
                uaD.setAddBy(pAdmin);
                uaD.setModBy(pAdmin);
                UserAssocDataAccess.insert(conn, uaD);
            }
            for(int ii=0; ii<groupAssocDV.size(); ii++) {
                GroupAssocData gaD = (GroupAssocData) groupAssocDV.get(ii);
                gaD.setGroupAssocId(0);
                gaD.setUserId(nUserId);
                gaD.setAddBy(pAdmin);
                gaD.setModBy(pAdmin);
                GroupAssocDataAccess.insert(conn,gaD);
            }

            for(int ii=0; ii<addressDV.size(); ii++) {
                AddressData aD = (AddressData) addressDV.get(ii);
                aD.setAddressId(0);
                aD.setUserId(nUserId);
                aD.setName1(null);
                aD.setName2(null);
                aD.setModBy(pAdmin);
                aD.setAddBy(pAdmin);
                AddressDataAccess.insert(conn, aD);
            }

            for(int ii=0; ii<phoneDV.size(); ii++) {
                PhoneData pD = (PhoneData) phoneDV.get(ii);
                pD.setUserId(nUserId);
                pD.setPhoneId(0);
                pD.setAddBy(pAdmin);
                pD.setModBy(pAdmin);
                PhoneDataAccess.insert(conn,pD);
            }


            for(int i=0; i<acctRightsDV.size(); i++){
            	PropertyData acctRight = (PropertyData)acctRightsDV.get(i);
            	acctRight.setPropertyId(0);
            	acctRight.setUserId(nUserId);
            	acctRight.setAddBy(pAdmin);
            	acctRight.setModBy(pAdmin);
            	PropertyDataAccess.insert(conn, acctRight);
            }

            return nUserId;

        } catch (Exception exc) {
            logError(exc.getMessage());
            exc.printStackTrace();
            throw new RemoteException("Error. UserBean.createUserClone. "+exc.getMessage());
        } finally {
            try {if (conn != null) conn.close();} catch (Exception ex) {}
        }

    }

    //--------------------------------------------------------------------------
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
    throws DataNotFoundException, RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            //Read data
            UserData userD = UserDataAccess.select(conn,pUserId);
            DBCriteria dbc = new DBCriteria();

            dbc = new DBCriteria();
            dbc.addEqualTo(UserAssocDataAccess.USER_ID,pUserId);
            dbc.addEqualTo(UserAssocDataAccess.USER_ASSOC_CD,
            RefCodeNames.USER_ASSOC_CD.SITE);
            dbc.addOrderBy(UserAssocDataAccess.BUS_ENTITY_ID);
            IdVector siteIdV = UserAssocDataAccess.selectIdOnly(conn,
            UserAssocDataAccess.BUS_ENTITY_ID, dbc);

            dbc = new DBCriteria();
            dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY2_ID, pAccountId);
            dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,
            RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT);
            dbc.addOrderBy(BusEntityAssocDataAccess.BUS_ENTITY1_ID);
            IdVector allSiteIdV = BusEntityAssocDataAccess.selectIdOnly(conn,
            BusEntityAssocDataAccess.BUS_ENTITY1_ID,dbc);

            HashMap sitesMap = new HashMap();
            for ( int cidx = 0; cidx < siteIdV.size(); cidx++)
            {
                Integer isid = (Integer)siteIdV.get(cidx);
                sitesMap.put(isid, isid);
            }

            // add associations for those sites in this account
            // to which this user does not belong.
            for(int ii=0, jj=0; ii<allSiteIdV.size(); ii++) {
                Integer sI = (Integer) allSiteIdV.get(ii);
                if ( sitesMap.containsKey(sI) ) {
                     continue;
                }

                int siteId = sI.intValue();
                UserAssocData uaD = UserAssocData.createValue();
                uaD.setUserId(pUserId);
                uaD.setBusEntityId(siteId);
                uaD.setUserAssocCd(RefCodeNames.USER_ASSOC_CD.SITE);
                uaD.setAddBy(pAdmin);
                uaD.setModBy(pAdmin);
                UserAssocDataAccess.insert(conn,uaD);
            }

        } catch (Exception exc) {
            logError(exc.getMessage());
            exc.printStackTrace();
            throw new RemoteException("UserBean.assignAllSites. "
                                      +exc.getMessage());
        } finally {
            closeConnection(conn);
        }

    }

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
    throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(UserAssocDataAccess.USER_ID,pUserId);
            dbc.addEqualTo(UserAssocDataAccess.USER_ASSOC_CD,
                    RefCodeNames.USER_ASSOC_CD.ACCOUNT);
            String acctUserIdReq =
               UserAssocDataAccess.getSqlSelectIdOnly(UserAssocDataAccess.BUS_ENTITY_ID,dbc);
            dbc = new DBCriteria();
            dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY2_ID, pStoreId);
            dbc.addOneOf(BusEntityAssocDataAccess.BUS_ENTITY1_ID, acctUserIdReq);
            dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,
                    RefCodeNames.BUS_ENTITY_ASSOC_CD.ACCOUNT_STORE);
            String acctUserStoreReq =
               BusEntityAssocDataAccess.getSqlSelectIdOnly(BusEntityAssocDataAccess.BUS_ENTITY1_ID,dbc);

            dbc = new DBCriteria();
            dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID,acctUserStoreReq);
            dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,
                    RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT);
            if(!pGetInactiveFl) {
              dbc.addNotEqualTo(BusEntityDataAccess.BUS_ENTITY_STATUS_CD,
                    RefCodeNames.BUS_ENTITY_STATUS_CD.INACTIVE);
            }

            IdVector acctIdV =
                    BusEntityDataAccess.selectIdOnly(conn,BusEntityDataAccess.BUS_ENTITY_ID,dbc);
            return acctIdV;
        } catch (Exception exc) {
            logError(exc.getMessage());
            exc.printStackTrace();
            throw new RemoteException("Error. UserBean.getUserSiteAccounts. "
            +exc.getMessage());
        } finally {
            closeConnection(conn);
        }

    }

    /**
     *  Gets bus_entity account object sites assigned to the user
     *
     *@param  pUserId      the  user id
     *@return a set of BusEnityData objects
     *@exception  RemoteException        Description of Exception
     */
    public BusEntityDataVector getUserSiteAccounts(int pUserId)
    throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            return getUserSiteAccounts(conn,pUserId);
        } catch (Exception exc) {
            logError(exc.getMessage());
            exc.printStackTrace();
            throw new RemoteException("Error. UserBean.getUserSiteAccounts. "
            +exc.getMessage());
        } finally {
            closeConnection(conn);
        }

    }

    private BusEntityDataVector getUserSiteAccounts(Connection conn, int pUserId)
    throws Exception {

        DBCriteria dbc = new DBCriteria();

        dbc = new DBCriteria();
        String allacct = "( select distinct BUS_ENTITY_ID "
            + " from CLW_user_ASSOC "
            + " where USER_ID = " + pUserId
            + " and USER_ASSOC_CD = 'ACCOUNT' "
            + " union "
            + " select distinct ba.bus_entity2_id from "
            + " clw_bus_entity_assoc ba "
            + " ,  CLW_user_ASSOC ua where USER_ID = " + pUserId
            + " and USER_ASSOC_CD = 'SITE' "
            + " and ua.BUS_ENTITY_ID =  ba.bus_entity1_id )";

        dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, allacct);
        dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_STATUS_CD,
                       RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);
        dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,
                       RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT);
        dbc.addOrderBy(BusEntityDataAccess.SHORT_DESC);

        logDebug("sss get accounts");
        BusEntityDataVector busEntityDV = BusEntityDataAccess.select(conn,dbc);
        logDebug("sss busEntityDV.size=" + busEntityDV.size());

        return busEntityDV;


    }

    public  UserAccountRightsViewVector  getUserAccountRights(int pUserId)
    throws RemoteException {
        Connection con = null;
        try {
            con = getConnection();
            return getUserAccountRights(con, pUserId);
        }
        catch (Exception exc) {
            logError(exc.getMessage());
            exc.printStackTrace();
            throw new RemoteException("Error. UserBean.getUserAccountRights. "
            +exc.getMessage());
        } finally {
            closeConnection(con);
        }

    }

    public  UserAccountRightsViewVector  getUserAccountRights
        (Connection con, int pUserId)
    throws Exception {

        UserAccountRightsViewVector rights = new UserAccountRightsViewVector();
        UserData ud = UserDataAccess.select(con, pUserId);
        UserRightsTool usert = new UserRightsTool(ud);
        if ( ! usert.hasAccounts() ) {
            return rights;
        }

        BusEntityDataVector accts = getUserSiteAccounts(con, pUserId) ;
        IdVector acctIdV = new IdVector();
        for(Iterator iter=accts.iterator(); iter.hasNext();) {
            BusEntityData beD = (BusEntityData) iter.next();
            acctIdV.add(new Integer(beD.getBusEntityId()));
        }
        PropertyDataVector rightsPropDV =
            getAccountSpecificRights(con, pUserId, acctIdV);

        for (Iterator iter=accts.iterator(); iter.hasNext();) {
            UserAccountRightsView uar = UserAccountRightsView.createValue();
            BusEntityData beD = (BusEntityData) iter.next();
            uar.setAccountData(beD);
            int acctId = beD.getBusEntityId();
            boolean foundFl = false;
            for(Iterator iter1=rightsPropDV.iterator(); iter1.hasNext();) {
                PropertyData pD = (PropertyData) iter1.next();
                if(pD.getBusEntityId()==acctId) {
                    uar.setUserSettings(pD);
                    foundFl = true;
                    break;
                }
            }
            if(!foundFl) {
                 PropertyData pD = PropertyData.createValue();
                 pD.setUserId(pUserId);
                 pD.setBusEntityId(acctId);
                 pD.setPropertyTypeCd
                   (RefCodeNames.PROPERTY_TYPE_CD.USER_ACCOUNT_RIGHTS);
                 pD.setPropertyStatusCd
                    (RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
                 pD.setShortDesc(pD.getPropertyTypeCd());
                 uar.setUserSettings(pD);
            }
            rights.add(uar);
        }

        return rights;
    }
    
    private  String  getUserAccountRights
    (Connection con, UserData ud, int accountId)
    throws Exception {
    	int userId = ud.getUserId();    	
    	UserRightsTool usert = new UserRightsTool(ud);
    	if ( ! usert.hasAccounts() ) {
    		return null;
    	}
    	IdVector pAcctIds = new IdVector();
    	pAcctIds.add(accountId);

    	PropertyDataVector pDV = getAccountSpecificRights(con,userId,pAcctIds);
    	if (pDV.size() > 0) 
    		return ((PropertyData)pDV.get(0)).getValue();    	
		
		return null;
    }

    private PropertyDataVector getAccountSpecificRights
    (Connection conn, int pUserId, IdVector pAcctIds)
    throws Exception {

        PropertyDataVector propDV =
            BusEntityDAO.getAccountSpecificRights(conn,pUserId,pAcctIds);
        return propDV;

    }

    /**
     *  Gets user rights vector
     *
     *@param  pUserId      the  user id
     *@param pAccountIdV a set of accounts
     *@return a set of UserAccountRightsView objects
     *@exception  RemoteException
     */
    public  UserAccountRightsViewVector  getUserAccountRights(int pUserId, IdVector pAccountIdV)
    throws RemoteException {
        UserAccountRightsViewVector rights = new UserAccountRightsViewVector();
        Connection con = null;
        try {
          con = getConnection();
          pAccountIdV.sort();
          DBCriteria dbc = new DBCriteria();
          if(pAccountIdV!=null) {
            dbc.addOneOf(UserAssocDataAccess.BUS_ENTITY_ID,pAccountIdV);
          }
          dbc.addEqualTo(UserAssocDataAccess.USER_ID, pUserId);
          dbc.addEqualTo(UserAssocDataAccess.USER_ASSOC_CD,
                                     RefCodeNames.USER_ASSOC_CD.ACCOUNT);
          String userBusEntityReq =
             UserAssocDataAccess.getSqlSelectIdOnly(UserAssocDataAccess.BUS_ENTITY_ID, dbc);

          dbc = new DBCriteria();
          dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, userBusEntityReq);
          dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,
                  RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT);
          dbc.addOrderBy(BusEntityDataAccess.BUS_ENTITY_ID);
          BusEntityDataVector acctBeDV = BusEntityDataAccess.select(con,dbc);
          IdVector userAccountIdV = new IdVector();
          for(Iterator iter = acctBeDV.iterator(); iter.hasNext();) {
            BusEntityData beD = (BusEntityData) iter.next();
            userAccountIdV.add(new Integer(beD.getBusEntityId()));
          }

          //get user role
          UserData ud = getUser(pUserId);
          String userRoles = ud.getUserRoleCd();

          dbc = new DBCriteria();
          dbc.addEqualTo(PropertyDataAccess.USER_ID, pUserId);
          dbc.addOneOf(PropertyDataAccess.BUS_ENTITY_ID, userAccountIdV);
          dbc.addEqualTo(PropertyDataAccess.PROPERTY_TYPE_CD,
                    RefCodeNames.PROPERTY_TYPE_CD.USER_ACCOUNT_RIGHTS);
          dbc.addOrderBy(PropertyDataAccess.BUS_ENTITY_ID);
          PropertyDataVector pDV = PropertyDataAccess.select(con,dbc);

            PropertyData wrkPropD = null;
            for(Iterator iter = acctBeDV.iterator(),iter1=pDV.iterator(); iter.hasNext();) {
              BusEntityData beD = (BusEntityData) iter.next();
              int acctId = beD.getBusEntityId();
              boolean assignedFl = false;
              while(wrkPropD!=null || iter1.hasNext()) {
                if(wrkPropD==null) wrkPropD = (PropertyData) iter1.next();
                if(acctId==wrkPropD.getBusEntityId()) {
                  UserAccountRightsView uar = UserAccountRightsView.createValue();
                  uar.setAccountData(beD);
                  uar.setUserSettings(wrkPropD);
                  rights.add(uar);
                  wrkPropD = null;
                  assignedFl = true;
                  break;
                } else if(acctId > wrkPropD.getBusEntityId()) {
                  wrkPropD = null;
                  continue;
                } else {
                  break;
                }
              }
              if(!assignedFl) {
                UserAccountRightsView uar = UserAccountRightsView.createValue();
                uar.setAccountData(beD);
                PropertyData pd = PropertyData.createValue();
                pd.setUserId(pUserId);
                pd.setBusEntityId(acctId);
                pd.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.USER_ACCOUNT_RIGHTS);
                pd.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
                pd.setShortDesc(pd.getPropertyTypeCd());
                pd.setValue(userRoles);
                PropertyDataAccess.insert(con, pd);
                uar.setUserSettings(pd);
                rights.add(uar);
              }
            }
            return rights;
        }
        catch (Exception exc) {
            logError(exc.getMessage());
            exc.printStackTrace();
            throw new RemoteException(exc.getMessage());
        } finally {
            closeConnection(con);
        }

    }

    public UserAcessTokenViewData createAccessToken(LdapItemData pKey)
    throws RemoteException, InvalidLoginException{
    	return createAccessToken(pKey,true,0);
    }
    
    public UserAcessTokenViewData createAccessToken(LdapItemData pKey, boolean checkPwd)
    throws RemoteException, InvalidLoginException{
    	return createAccessToken(pKey,checkPwd,0);
    }
    
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
    throws RemoteException, InvalidLoginException
    {
        Connection conn = null;
        try{
            conn = getConnection();

            UserData ud = login(pKey,null,checkPwd);
            log.info("createAccessToken "+ud);
            if(ud == null){
                throw new RemoteException("Could not log user in");
            }

            //create a pseudo random number for use as the access token
            long seed = ud.getUserId()*System.currentTimeMillis();
            java.util.Random rand = new java.util.Random(seed);
            int randInt = rand.nextInt();
            long randLon;
            if(randInt< 0){
                randLon = randInt + Integer.MAX_VALUE;
            }else{
                randLon = randInt;
            }
            String accessToken = Long.toString(randLon);

            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(PropertyDataAccess.PROPERTY_TYPE_CD,RefCodeNames.PROPERTY_TYPE_CD.ACCESS_TOKEN);
            crit.addEqualTo(PropertyDataAccess.PROPERTY_STATUS_CD,RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
            crit.addEqualTo(PropertyDataAccess.USER_ID,ud.getUserId());
            crit.addOrderBy(PropertyDataAccess.PROPERTY_ID,false);
            PropertyDataVector pdv = PropertyDataAccess.select(conn,crit);
            PropertyData theProp;
            if(pdv.size() == 1){
                theProp = (PropertyData) pdv.get(0);
            }else if(pdv.size() == 0){
                theProp = PropertyData.createValue();
                theProp.setUserId(ud.getUserId());
                theProp.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
                theProp.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.ACCESS_TOKEN);
                theProp.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.ACCESS_TOKEN);
                theProp.setAddBy("system");
                theProp.setModBy("system");
                theProp = PropertyDataAccess.insert(conn, theProp);
            }else{
                theProp = (PropertyData) pdv.get(0);
                //remove the bogus ones
                IdVector toRemove = new IdVector();
                for(int i=1;i<pdv.size();i++){
                    PropertyData aProp=(PropertyData) pdv.get(i);
                    toRemove.add(new Integer(aProp.getPropertyId()));
                }
                DBCriteria remCrit = new DBCriteria();
                remCrit.addOneOf(PropertyDataAccess.PROPERTY_ID,toRemove);
                PropertyDataAccess.remove(conn,remCrit);
            }

            //update the prop with our new access token and the mod date OF THE DATABASE
            //an access token only is alive for a limited time and a login request
            //may come from a different server than the actual login is handled from,
            //so difference in computer clocks may be significant
            StringBuffer updateSqlBuf = new StringBuffer();
            updateSqlBuf.append("Update ");
            updateSqlBuf.append(PropertyDataAccess.CLW_PROPERTY);
            updateSqlBuf.append(" set ");
            if(busEntityId != 0){
            	updateSqlBuf.append(PropertyDataAccess.BUS_ENTITY_ID);
            	updateSqlBuf.append(" = ");
            	updateSqlBuf.append(busEntityId);
            	updateSqlBuf.append(", ");
            }
            updateSqlBuf.append(PropertyDataAccess.MOD_DATE);
            updateSqlBuf.append(" = sysdate,");
            updateSqlBuf.append(PropertyDataAccess.CLW_VALUE);
            updateSqlBuf.append(" = '");
            updateSqlBuf.append(accessToken);
            updateSqlBuf.append("' where ");
            updateSqlBuf.append(PropertyDataAccess.PROPERTY_ID);
            updateSqlBuf.append(" = ");
            updateSqlBuf.append(theProp.getPropertyId());

            log.debug(updateSqlBuf);

            Statement stmt = conn.createStatement();
            int updCt = stmt.executeUpdate(updateSqlBuf.toString());
            if(1!=updCt){
                throw new RemoteException("Not one row updated ("+updCt+") expected one.");
            }

            UserAcessTokenViewData uat = new UserAcessTokenViewData();
            uat.setAccessToken(accessToken);
            uat.setUserData(ud);
            uat.setUserSiteIds(getBusEntityIds(ud.getUserId(), RefCodeNames.BUS_ENTITY_TYPE_CD.SITE, null,10, conn));
            
            crit = new DBCriteria();
            crit.addEqualTo(PropertyDataAccess.USER_ID, ud.getUserId());
            crit.addEqualTo(PropertyDataAccess.PROPERTY_TYPE_CD, RefCodeNames.PROPERTY_TYPE_CD.DEFAULT_SITE);
            crit.addEqualTo(PropertyDataAccess.PROPERTY_STATUS_CD,RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
            PropertyDataVector propDV = PropertyDataAccess.select(conn, crit);
            
            if(propDV!=null && propDV.size()>0){
            	PropertyData propD = (PropertyData)propDV.get(0);
            	String defaultSite = propD.getValue();
            	uat.setDefaultSite(defaultSite);
            }
            
            return uat;
        }catch(InvalidLoginException e){
                throw e;
        }catch(Exception e){
            throw processException(e);
        }finally{
            closeConnection(conn);
        }
    }

    public ArrayList getBilltoCollection(int pUserId)
    throws RemoteException
    {
	ArrayList biltos = new ArrayList();
        Connection conn = null;
        try{
            conn = getConnection();
	}
	catch (Exception e) {
	}
	finally {
	    closeConnection(conn);
	}
	return biltos;
    }


    /**
     *Associates specified bus entity ids to the supplied user.
     *@param pUserId         the user id
     *@param busEntityIds    the bus entity ids to associate to the user
     *@param busEntityTypeCd used to determine the association type, no actual checking is done
     */
    public void addBusEntityAssociations(int pUserId, IdVector busEntityIds, String busEntityTypeCd, String userName)
    throws RemoteException{
        Connection conn = null;
        try{
            conn = getConnection();
            UserDAO.addBusEntityAssociations(conn, pUserId, busEntityIds,busEntityTypeCd,userName);
        }catch (Exception e) {
            throw processException(e);
		}finally {
		    closeConnection(conn);
		}

    }







    /**
     *Removes associates between the specifed user and the supplied list of bus entity ids
     *@param pUserId         the user id
     *@param busEntityIds    the bus entity ids to "de"associate to the user
     */
    public void removeBusEntityAssociations(int pUserId, IdVector busEntityIds)
    throws RemoteException{
        Connection conn = null;
        try{
            conn = getConnection();
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(UserAssocDataAccess.USER_ID,pUserId);
            crit.addOneOf(UserAssocDataAccess.BUS_ENTITY_ID,busEntityIds);
            UserAssocDataAccess.remove(conn,crit);
	}catch (Exception e) {
            throw processException(e);
	}finally {
	    closeConnection(conn);
	}
    }

  /**
   *Gets user types, which the adminstrator may manage
   *@param pAdminType the of the administrator user
   *@return a set of RefCdData objects
   */
  public RefCdDataVector getManageableUserTypes(String pAdminType)
  throws RemoteException
  {
    Connection conn = null;
    try{
      conn = getConnection();
      log.info("UserBean UUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUU pAdminType: "+pAdminType);
      DBCriteria dbc = new DBCriteria();
      dbc.addEqualTo(RefCdDataAccess.REF_CD,"USER_TYPE_CD");
      if(RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(pAdminType)) {
        //nothing
      } else if(RefCodeNames.USER_TYPE_CD.ADMINISTRATOR.equals(pAdminType)) {
         dbc.addNotEqualTo(RefCdDataAccess.CLW_VALUE,
                 RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR);
      } else if(RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR.equals(pAdminType)) {
        LinkedList typesAllowedLL = new LinkedList();
        typesAllowedLL.add(RefCodeNames.USER_TYPE_CD.ACCOUNT_ADMINISTRATOR);
        typesAllowedLL.add(RefCodeNames.USER_TYPE_CD.CUSTOMER);
        typesAllowedLL.add(RefCodeNames.USER_TYPE_CD.DISTRIBUTOR);
        typesAllowedLL.add(RefCodeNames.USER_TYPE_CD.MSB);
        typesAllowedLL.add(RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR);
        typesAllowedLL.add(RefCodeNames.USER_TYPE_CD.REPORTING_USER);
        dbc.addOneOf(RefCdDataAccess.CLW_VALUE,typesAllowedLL);
      } else if (RefCodeNames.USER_TYPE_CD.ACCOUNT_ADMINISTRATOR.equals(pAdminType)) {
          List typesAllowed = Utility.getAsList(RefCodeNames.USER_TYPE_CD.MSB, RefCodeNames.USER_TYPE_CD.CUSTOMER);
          dbc.addOneOf(RefCdDataAccess.CLW_VALUE, typesAllowed);
      } else {
        return new RefCdDataVector();
      }
      log.info("UserBean UUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUU sql: "+(RefCdDataAccess.getSqlSelectIdOnly("*",dbc)));
      RefCdDataVector refCdDV = RefCdDataAccess.select(conn,dbc);
      return refCdDV;
    }catch (Exception e) {
      throw processException(e);
    }finally {
      closeConnection(conn);
    }
  }
    /**
     *Associates specified user with all accounts of the store
     *@param pUserId         the user id
     *@param pStoreId   the store id
     *@param userName  the admininstrator name
     *@param pAccAdminId  the admininstrator Id ( for account admin > 0, otherwise -1)
     */
    public void configureAllAccounts(int pUserId, int pStoreId, String userName )
    throws RemoteException{
          configureAllAccounts(pUserId, pStoreId,  userName, -1);
    }

    public void configureAllAccounts(int pUserId, int pStoreId, String userName, int pAccAdminId)
    throws RemoteException{
        Connection conn = null;
        try{
            conn = getConnection();
            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY2_ID,pStoreId);
            dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,
                    RefCodeNames.BUS_ENTITY_ASSOC_CD.ACCOUNT_STORE);
            String acctStoreReq =
               BusEntityAssocDataAccess.getSqlSelectIdOnly(BusEntityAssocDataAccess.BUS_ENTITY1_ID,dbc);

            dbc = new DBCriteria();
            dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID,acctStoreReq);
            dbc.addNotEqualTo(BusEntityDataAccess.BUS_ENTITY_STATUS_CD,
                    RefCodeNames.BUS_ENTITY_STATUS_CD.INACTIVE);
            dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,
                    RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT);
            IdVector acctIds = BusEntityDataAccess.selectIdOnly(conn,BusEntityDataAccess.BUS_ENTITY_ID,dbc);
            if (pAccAdminId >=0){
              dbc = new DBCriteria();
              dbc.addEqualTo(UserAssocDataAccess.USER_ID, pAccAdminId);
              dbc.addEqualTo(UserAssocDataAccess.USER_ASSOC_CD, RefCodeNames.USER_ASSOC_CD.ACCOUNT);
              dbc.addOneOf(UserAssocDataAccess.BUS_ENTITY_ID,acctIds);
              acctIds = UserAssocDataAccess.selectIdOnly(conn, UserAssocDataAccess.BUS_ENTITY_ID, dbc);
            }
            UserDAO.addBusEntityAssociations(conn, pUserId, acctIds,
                    RefCodeNames.USER_ASSOC_CD.ACCOUNT,userName);
        }catch (Exception e) {
            throw processException(e);
		}finally {
		    closeConnection(conn);
		}

    }

    /**
     * Returns related distributor ids using both orders and the catalogs.  Goes throughthe sites that the user has access
     * to to figure out what distributors to retrieve.
     * @param pUserId the user id
     * @return a populated BusEntityDataVector
     * @throws RemoteException
     */
    public BusEntityDataVector getDistributorBusEntitiesForReports(int pUserId)
    throws RemoteException
    {
    	Connection con = null;
    	try{
    		con = getConnection();
    		BusEntityDataAccess beda = new BusEntityDataAccess();
	    	String sql = "select distinct "
	    	+beda.getSelectColumns()+
	    	" from clw_bus_entity, clw_catalog_structure cs, clw_catalog c, clw_catalog_assoc ca, clw_user_assoc ua where "+
			"ua.user_id = ? and "+
			"ua.user_assoc_cd = 'SITE' and "+
			"ua.bus_entity_id = ca.bus_entity_id and "+
			"clw_bus_entity.bus_entity_id =  cs.bus_entity_id and  "+
			"c.catalog_id = ca.catalog_id and "+
			"c.catalog_id = cs.catalog_id and "+
			"c.catalog_status_cd = 'ACTIVE' and "+
			"c.catalog_type_cd = 'SHOPPING' "+
			"union "+
			"select "
			+beda.getSelectColumns()+
			" from clw_order o, clw_purchase_order po, clw_bus_entity , clw_user_assoc ua where "+
			"o.order_id = po.order_id " +
			"and clw_bus_entity.erp_num = po.dist_erp_num " +
			"and ua.bus_entity_id = o.site_id " +
			"and ua.user_id = ? " +
			"and bus_entity_type_cd = 'DISTRIBUTOR' " +
			"and bus_entity_status_cd = 'ACTIVE' "+
			"order by short_desc";
	    	log.info(sql);
	    	PreparedStatement stmt = con.prepareStatement(sql);
	    	stmt.setInt(1,pUserId);
	    	stmt.setInt(2,pUserId);
	        ResultSet rs=stmt.executeQuery();
	        BusEntityDataVector bedv = new BusEntityDataVector();
	        while(rs.next()){
	        	bedv.add(beda.parseResultSet(rs));
	        }
	        return bedv;
    	}catch(Exception e){
    		throw processException(e);
    	}finally{
    		closeConnection(con);
    	}
    }

    /**
     * Returns RefCdDataVector of functions which are autorized for user
     * @param userId
     * @param userTypeCd
     * @return RefCdDataVector
     * @throws RemoteException
     */
    public RefCdDataVector getAuthorizedFunctions(int userId, String userTypeCd) throws RemoteException {

        RefCdDataVector allFunctions;
        Connection con = null;

        try{
            allFunctions = (new APIAccess()).getListServiceAPI().getRefCodesCollection("APPLICATION_FUNCTIONS");

            //Find all groups for the user
            con = getConnection();
            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(GroupAssocDataAccess.USER_ID, userId);
            dbc.addEqualTo(GroupAssocDataAccess.GROUP_ASSOC_CD, RefCodeNames.GROUP_ASSOC_CD.USER_OF_GROUP);
            IdVector groupIdV = GroupAssocDataAccess.selectIdOnly(con, GroupAssocDataAccess.GROUP_ID, dbc);

            //Get group for everyone
            dbc = new DBCriteria();
            LinkedList defaultGroups = new LinkedList();
            defaultGroups.add("EVERYONE");
            defaultGroups.add(userTypeCd);
            dbc.addOneOf(GroupDataAccess.SHORT_DESC, defaultGroups);
            IdVector everyoneIdV = GroupDataAccess.selectIdOnly(con, GroupDataAccess.GROUP_ID, dbc);
            groupIdV.addAll(everyoneIdV);

            Iterator itG = groupIdV.iterator();
            while(itG.hasNext()){
                Integer id = (Integer) itG.next();
                log("getAuthorizedFunctions() => " + id);
            }

            //Get all functions for groups
            dbc = new DBCriteria();
            dbc.addOneOf(GroupAssocDataAccess.GROUP_ID, groupIdV);
            dbc.addEqualTo(GroupAssocDataAccess.GROUP_ASSOC_CD, RefCodeNames.GROUP_ASSOC_CD.FUNCTION_OF_GROUP);
            dbc.addOrderBy(GroupAssocDataAccess.APPLICATION_FUNCTION);
            GroupAssocDataVector functionIdV = GroupAssocDataAccess.select(con, dbc);

            //check functions
            Iterator itAll = allFunctions.iterator();
            while (itAll.hasNext()){

                RefCdData ref = (RefCdData) itAll.next();
                boolean is = false;

                Iterator it = functionIdV.iterator();
                while (it.hasNext()){
                    GroupAssocData groupAssoc = (GroupAssocData) it.next();
                    String functionName = groupAssoc.getApplicationFunction();

                    if(ref.getValue().equals(functionName.trim())){
                        is = true;
                    }
                }
                //remove if function is not authorized for user
                if(!is){
                    itAll.remove();
                }

            }

            return allFunctions;

        }catch(Exception e){
            throw processException(e);
        }finally{
            closeConnection(con);
    	}
    }


    public UserInfoDataVector getUserInfoCollection(int siteId, int accountId, String rights) throws RemoteException {

        UserDataVector users = getAllActiveUsers(siteId, User.ORDER_BY_ID);
        UserInfoDataVector result = new UserInfoDataVector();
        for (int i = 0; users != null && i < users.size(); i++) {
            UserData ud = (UserData) users.get(i);
            UserInfoData userInfo = null;
            try {
                userInfo = getUserContact(ud.getUserId());
            } catch (DataNotFoundException e) {
                e.printStackTrace();
                continue;
            }
            if (userInfo.subscribesTo(rights, accountId)) {
                result.add(userInfo);
            }
        }
        return result;
    }
    
    public UserInfoDataVector getUsersByRight(int siteId, int accountId, String rights) throws RemoteException {
    	UserInfoDataVector returnValue = new UserInfoDataVector();
    	 Connection con = null;
         try {
             con = getConnection();
             UserDataVector users = getAllActiveUsers(siteId, User.ORDER_BY_ID);
             for (int i = 0; users != null && i < users.size(); i++) {
                 UserData ud = (UserData) users.get(i);
                 boolean hasRights = false;
                 String accountRight = getUserAccountRights(con, ud, accountId);
                 if (accountRight != null){
                	 hasRights = (accountRight.indexOf(UserInfoData.USER_GETS_EMAIL_ORDER_SHIPPED) >= 0);
                 }else{
                	 hasRights = (ud.getUserRoleCd().indexOf(UserInfoData.USER_GETS_EMAIL_ORDER_SHIPPED) >= 0);
                 }
                  
                 if (hasRights){
                	 UserInfoData user = new UserInfoData(ud, null, null, null, null, null,null,null);
                	// Get the Email.
                	 DBCriteria crit = new DBCriteria();
                     crit.addEqualTo(EmailDataAccess.USER_ID, ud.getUserId());
                     EmailDataVector ed = EmailDataAccess.select(con, crit);
                     if (ed.size() > 0) {
                    	 user.setEmailData((EmailData) ed.get(0));
                     }
                 }
             }
             return returnValue;
         }
         catch (Exception exc) {
             logError(exc.getMessage());
             exc.printStackTrace();
             throw new RemoteException("Error. UserBean.getEmailAddrByRight. "
             +exc.getMessage());
         } finally {
             closeConnection(con);
         }

    }

    public UserInfoDataVector getUserInfoCollection(IdVector userIds) throws RemoteException {
        Connection conn = null;
        try {
            conn=getConnection();
            UserDataVector users = getUsersCollection(userIds);
            return getUserInfo(conn, users);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
    }

    public UserInfoDataVector getUserInfoDataCollection(int siteId, int accountId, String rights) throws RemoteException {
        UserDataVector users = new UserDataVector();
        UserInfoDataVector result = new UserInfoDataVector();

        Connection conn = null;
        try {
          conn = getConnection();
          DBCriteria crit = new DBCriteria();
          crit.addEqualTo(UserAssocDataAccess.BUS_ENTITY_ID, siteId);
          IdVector ids = UserAssocDataAccess.selectIdOnly(conn, UserAssocDataAccess.USER_ID, crit);
          if (ids.size() == 0) {
            return result;
          }
          crit = new DBCriteria();
          crit.addOneOf(UserDataAccess.USER_ID, ids);
          crit.addEqualTo(UserDataAccess.USER_STATUS_CD,RefCodeNames.USER_STATUS_CD.ACTIVE);
          crit.addOrderBy(UserDataAccess.USER_ID, true);

          users = UserDataAccess.select(conn, crit);

          for (int i = 0; users != null && i < users.size(); i++) {
              UserData ud = (UserData) users.get(i);
              UserInfoData userInfo = null;
              try {
                  userInfo = getUserInfoDataForNotification(conn, ud);
              } catch (DataNotFoundException e) {
                  e.printStackTrace();
                  continue;
              }
              if (userInfo.subscribesTo(rights, accountId)) {
                  result.add(userInfo);
              }
          }

        }
        catch (Exception e) {
          throw new RemoteException("getAllUsers: " + e.getMessage());
        }
        finally {
          try {
            if (conn != null)
              conn.close();
          }
          catch (Exception ex) {}
        }
        return result;
    }


    private UserInfoDataVector getUserInfo(Connection conn, UserDataVector pUsers) throws Exception {

        UserInfoDataVector info = new UserInfoDataVector();
        if (!pUsers.isEmpty()) {
            Iterator it = pUsers.iterator();
            while (it.hasNext()) {
                UserData userData = (UserData) it.next();
                UserInfoData uid = getUserInfoData(conn, userData);
                info.add(uid);
            }
        }
        return info;
    }

    private UserInfoData getUserInfoData(Connection conn, UserData pUser) throws Exception {

        int userId = pUser.getUserId();
        UserInfoData uid = UserInfoData.createValue();
        uid.setUserData(pUser);

        DBCriteria crit;

        // Get the address.
        crit = new DBCriteria();
        crit.addEqualTo(AddressDataAccess.USER_ID, userId);
        AddressDataVector ad = AddressDataAccess.select(conn, crit);
        if (ad.size() > 0) {
            uid.setAddressData((AddressData) ad.get(0));
        }

        // Get the Email.
        crit = new DBCriteria();
        crit.addEqualTo(EmailDataAccess.USER_ID, userId);
        EmailDataVector ed = EmailDataAccess.select(conn, crit);
        if (ed.size() > 0) {
            uid.setEmailData((EmailData) ed.get(0));
        }

        // Get the phone.
        crit = new DBCriteria();
        crit.addEqualTo(PhoneDataAccess.USER_ID, userId);
        PhoneDataVector pd = PhoneDataAccess.select(conn, crit);
        Iterator phoneI = pd.iterator();
        while (phoneI.hasNext()) {
            PhoneData phone = (PhoneData) phoneI.next();
            String phoneType = phone.getPhoneTypeCd();
            if (phoneType.compareTo(RefCodeNames.PHONE_TYPE_CD.PHONE) == 0) {
                uid.setPhone(phone);
            } else if (phoneType.compareTo(RefCodeNames.PHONE_TYPE_CD.FAX) == 0) {
                uid.setFax(phone);
            } else if (phoneType.compareTo(RefCodeNames.PHONE_TYPE_CD.MOBILE) == 0) {
                uid.setMobile(phone);
            } else {
                // unidentified - ignore it
            }
        }

        PropertyUtil putil = new PropertyUtil(conn);
        uid.setCustomerSystemKey(putil.fetchValueIgnoreMissing(userId, 0, RefCodeNames.PROPERTY_TYPE_CD.CUSTOMER_SYSTEM_KEY));
        uid.setUserAccountRights(getUserAccountRights(conn, userId));

        return uid;
    }

    private UserInfoData getUserInfoDataForNotification(Connection conn, UserData pUser) throws Exception {

     int userId = pUser.getUserId();
     UserInfoData uid = UserInfoData.createValue();
     uid.setUserData(pUser);

     DBCriteria crit;

     // Get the address.
     crit = new DBCriteria();
     crit.addEqualTo(AddressDataAccess.USER_ID, userId);
     AddressDataVector ad = AddressDataAccess.select(conn, crit);
     if (ad.size() > 0) {
         uid.setAddressData((AddressData) ad.get(0));
     }

     // Get the Email.
     crit = new DBCriteria();
     crit.addEqualTo(EmailDataAccess.USER_ID, userId);
     EmailDataVector ed = EmailDataAccess.select(conn, crit);
     if (ed.size() > 0) {
         uid.setEmailData((EmailData) ed.get(0));
     }

     // Get the phone.
     crit = new DBCriteria();
     crit.addEqualTo(PhoneDataAccess.USER_ID, userId);
     PhoneDataVector pd = PhoneDataAccess.select(conn, crit);
     Iterator phoneI = pd.iterator();
     while (phoneI.hasNext()) {
         PhoneData phone = (PhoneData) phoneI.next();
         String phoneType = phone.getPhoneTypeCd();
         if (phoneType.compareTo(RefCodeNames.PHONE_TYPE_CD.PHONE) == 0) {
             uid.setPhone(phone);
         } else if (phoneType.compareTo(RefCodeNames.PHONE_TYPE_CD.FAX) == 0) {
             uid.setFax(phone);
         } else if (phoneType.compareTo(RefCodeNames.PHONE_TYPE_CD.MOBILE) == 0) {
             uid.setMobile(phone);
         } else {
             // unidentified - ignore it
         }
     }
     return uid;
 }


    private void log(String message){
        log.info("UserBean :: " + message);
    }


    public UserAssocDataVector getUserAssocCollecton(int pUserId, String pAssocType) throws RemoteException  {
        Connection con = null;
        try {
            con = getConnection();
            return getUserAssocCollecton(con, pUserId, pAssocType);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(con);
        }
    }


    private UserAssocDataVector getUserAssocCollecton(Connection con, int pUserId, String pAssocType)  throws Exception {
        DBCriteria dbc = new DBCriteria();
        dbc.addEqualTo(UserAssocDataAccess.USER_ID, pUserId);
        dbc.addEqualTo(UserAssocDataAccess.USER_ASSOC_CD, pAssocType);
        return UserAssocDataAccess.select(con, dbc);
    }

    public IdVector getUserAssocCollecton(IdVector pUserIds, String pAssocType,String pStatusCd) throws RemoteException {
        Connection con = null;
        try {
            con = getConnection();
            return getUserAssocCollecton(con, pUserIds, pAssocType,pStatusCd);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(con);
        }
    }

    private IdVector getUserAssocCollecton(Connection con, IdVector pUserIds, String pAssocType,String pStatusCd) throws Exception {

        DBCriteria dbc = new DBCriteria();

        dbc.addJoinTableOneOf(UserAssocDataAccess.CLW_USER_ASSOC, UserAssocDataAccess.USER_ID, pUserIds);
        dbc.addJoinTableEqualTo(UserAssocDataAccess.CLW_USER_ASSOC, UserAssocDataAccess.USER_ASSOC_CD, pAssocType);
        dbc.addJoinCondition(BusEntityDataAccess.BUS_ENTITY_ID, UserAssocDataAccess.CLW_USER_ASSOC, UserAssocDataAccess.BUS_ENTITY_ID);
        dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_STATUS_CD, pStatusCd);

        IdVector ids = new IdVector();
        HashSet<Integer> resultSet = new HashSet<Integer>(Utility.toIdVector(BusEntityDataAccess.select(con,dbc)));
        ids.addAll(resultSet);

        return ids;
    }

    //public void saveNscUsers()
/*
SELECT
sua.bus_entity_id store_id, sua.user_assoc_id store_assoc_id,
aua.bus_entity_id account_id, aua.user_assoc_id account_assoc_id,
apr.property_id cust_maj_property_id, apr.clw_value cust_maj,
lua.bus_entity_id site_id, lua.user_assoc_id site_assoc_id,
lpr.property_id location_num_property_id, lpr.clw_value site_ref_num, lpr.short_desc,
e.email_id, e.email_address,
u.*
FROM clw_user u
left join clw_email e ON u.user_id = e.user_id
left join clw_user_assoc sua ON u.user_id = sua.user_id AND sua.user_assoc_cd = 'STORE'
left join clw_user_assoc aua ON u.user_id = aua.user_id AND aua.user_assoc_cd = 'ACCOUNT'
left join clw_user_assoc lua ON u.user_id = lua.user_id AND lua.user_assoc_cd = 'SITE'
left join clw_property apr ON aua.bus_entity_id = apr.bus_entity_id AND apr.short_desc = 'CUST_MAJ'
left join clw_property lpr ON lua.bus_entity_id = lpr.bus_entity_id AND lpr.short_desc = 'SITE_REFERENCE_NUMBER'
WHERE u.user_name = 'SD000101'
ORDER BY user_name, cust_maj, site_ref_num
;
*/
    public List saveNscUsers(NscUsViewVector pUsers, int pStoreId, int pDistributorId)
    throws RemoteException
    {
      return saveNscUsers(pUsers, pStoreId, pDistributorId, true, "Nsc User Loader");
    }
    public List saveNscUsers(NscUsViewVector pUsers, int pStoreId, int pDistributorId, boolean pSaveCatalogFl, String pAddBy)
    throws RemoteException
    {
        Connection con = null;
        LinkedList errors = new LinkedList();
        DBCriteria dbc = null;
        try {
            con = getConnection();
            //Get users on manual control
            dbc = new DBCriteria();
            dbc.addEqualTo(GroupDataAccess.SHORT_DESC, "NetSupply Ignore User Loader");
            String manualUserReq = GroupDataAccess.getSqlSelectIdOnly(GroupDataAccess.GROUP_ID, dbc);
            dbc = new DBCriteria();
            dbc.addOneOf(GroupAssocDataAccess.GROUP_ID, manualUserReq);
            IdVector ignoreUserIdV =
                    GroupAssocDataAccess.selectIdOnly(con, GroupAssocDataAccess.USER_ID,dbc);
            HashSet ignoreUserHS = new HashSet();
            for(Iterator iter=ignoreUserIdV.iterator(); iter.hasNext();) {
                ignoreUserHS.add(iter.next());
            }

            //sort users
            pUsers.sort("UserName");

            //Prepare assocciations
            HashSet custMajHS = new HashSet();
            HashSet siteRefNumHS = new HashSet();
            HashSet catalogNameHS = new HashSet();
            HashSet memberNumHS = new HashSet();
            LinkedList userName1000 = new LinkedList();
            IdVector userNames = new IdVector();
            userName1000.add(userNames);
            for(Iterator iter=pUsers.iterator(); iter.hasNext();) {
                NscUsView nscUsVw = (NscUsView) iter.next();
                if(userNames.size()>=999) {
                    userNames = new IdVector();
                    userName1000.add(userNames);
                }
                userNames.add(nscUsVw.getUserName());
                String custMaj = nscUsVw.getCustomerNumber();
                String siteRefNum = nscUsVw.getLocationNumber();
                String catalogName = nscUsVw.getCatalogName();
                String memberNum  = nscUsVw.getMemberNumber();
                if(!custMajHS.contains(custMaj)) custMajHS.add(custMaj);
                if(!siteRefNumHS.contains(siteRefNum)) siteRefNumHS.add(siteRefNum);
                if(!catalogNameHS.contains(catalogName)) catalogNameHS.add(catalogName);
                if(!memberNumHS.contains(memberNum)) memberNumHS.add(memberNum);
            }
            //Set accounts
            IdVector custMajV = new IdVector();
            for(Iterator iter = custMajHS.iterator(); iter.hasNext();) {
                custMajV.add(iter.next());
            }

            dbc = new DBCriteria();
            dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY2_ID, pStoreId);
            dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,
                    RefCodeNames.BUS_ENTITY_ASSOC_CD.ACCOUNT_STORE);
            String acctStoreReq =
                    BusEntityAssocDataAccess.getSqlSelectIdOnly(BusEntityAssocDataAccess.BUS_ENTITY1_ID, dbc);
            dbc = new DBCriteria();
            dbc.addOneOf(PropertyDataAccess.BUS_ENTITY_ID, acctStoreReq);
            dbc.addOneOf(PropertyDataAccess.CLW_VALUE, custMajV);
            dbc.addEqualTo(PropertyDataAccess.SHORT_DESC, "CUST_MAJ");
            dbc.addOrderBy(PropertyDataAccess.BUS_ENTITY_ID);

            log.info("[UserBean].saveNscUsers -> account by CUST_MAJ sql: "+BusEntityDataAccess.getSqlSelectIdOnly("*", dbc));
            HashMap accountHM = new HashMap();
            PropertyDataVector propertyDV = PropertyDataAccess.select(con, dbc);
            int prevAcctId = 0;
            String prevCustMaj = "";
            for(Iterator iter = propertyDV.iterator(); iter.hasNext();) {
                PropertyData pD = (PropertyData) iter.next();
                int acctId = pD.getBusEntityId();
                String custMaj = Utility.strNN(pD.getValue());
                if(prevAcctId!=acctId) {
                    prevAcctId = acctId;
                    prevCustMaj = custMaj;
                    Integer aIdI = (Integer) accountHM.get(custMaj);
                    if(aIdI!=null) {
                        if(aIdI.intValue()!=acctId) {
                            String errorMess = "Account "+acctId+" has multiple CUST_MAJ values";
                            errors.add(errorMess);
                        }
                    } else {
                        accountHM.put(custMaj, new Integer(acctId));
                    }
                } else {
                    if(!prevCustMaj.equals(custMaj)) {
                        String errorMess = "Account "+acctId+" has multiple CUST_MAJ values";
                        errors.add(errorMess);
                    }
                }
            }
            if (accountHM.size()==0){
              String errorMess = "No Accounts found for Customer Numbers: "+custMajV.toString();
              errors.add(errorMess);
            }
            for(Iterator iter=pUsers.iterator(); iter.hasNext();) {
                NscUsView nscUsVw = (NscUsView) iter.next();
                String custMaj = nscUsVw.getCustomerNumber();
                Integer acctIdI = (Integer) accountHM.get(custMaj);
                if(acctIdI==null) {
                    String errorMess = "Can't find account by CUST_MAJ: "+custMaj;
                    errors.add(errorMess);
                } else {
                    nscUsVw.setAccountId(acctIdI.intValue());
                }
            }

            //Set site id
            IdVector accountIdV = new IdVector();
            for(Iterator iter = accountHM.values().iterator(); iter.hasNext();) {
                accountIdV.add(iter.next());
            }

            LinkedList siteRefNumVV = new LinkedList();
            IdVector siteRefNumV = new IdVector();
            siteRefNumVV.add(siteRefNumV);
            for(Iterator iter=siteRefNumHS.iterator(); iter.hasNext();) {
                String siteRefNum = (String) iter.next();
                if(siteRefNumV.size()>=999) {
                    siteRefNumV = new IdVector();
                    siteRefNumVV.add(siteRefNumV);
                }
                siteRefNumV.add(siteRefNum);
            }

            HashMap siteAcctHM = new HashMap();
            for(Iterator iter=siteRefNumVV.iterator(); iter.hasNext();) {
               siteRefNumV = (IdVector) iter.next();

               String oneOfAccountsCond = (accountIdV.size()==0) ? "AND (1=2) " : "  AND  bea.bus_entity2_id in ("+IdVector.toCommaString(accountIdV)+") ";
               String oneOfSiteRefNumsCond = (siteRefNumV.size()==0) ? "AND (1=2) " : "  AND clw_value IN ("+IdVector.toCommaString(siteRefNumV)+") ";
               String siteReqSql =
                "SELECT DISTINCT p.bus_entity_id site_id, p.clw_value ref_num, bea.bus_entity2_id account_id " +
                " FROM clw_property p " +
                " join clw_bus_entity_assoc bea " +
                "  ON p.bus_entity_id = bea.bus_entity1_id " +
                "  AND bea.bus_entity_assoc_cd = '"+RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT+"' " +
                oneOfAccountsCond +
//                "  AND  bea.bus_entity2_id in ("+IdVector.toCommaString(accountIdV)+") " +
                "  WHERE short_desc = 'SITE_REFERENCE_NUMBER' " +
                oneOfSiteRefNumsCond;
//                "  AND clw_value IN ("+IdVector.toCommaString(siteRefNumV)+") ";

               log.info("[UserBean].saveNscUsers -> site by CUST_MAJ and site ref num sql: "+siteReqSql);

               Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(siteReqSql);
                while (rs.next()) {
                    int siteId = rs.getInt("site_id");
                    String siteRefNum = rs.getString("ref_num");
                    int acctId = rs.getInt("account_id");
                    String key = siteRefNum+"*"+acctId;
                    Integer sIdI = (Integer) siteAcctHM.get(key);
                    if(sIdI!=null) {
                        if(sIdI.intValue()!=siteId) {
                            String errorMess = "Account "+acctId+" " +
                                    "and Site Reference Number"+siteRefNum+" pair is not unique";
                            errors.add(errorMess);
                        }
                    } else {
                    	log.info("[UserBean].saveNscUsers -> key to add: "+key +" value: "+siteId);
                        siteAcctHM.put(key, new Integer(siteId));
                    }
                }
                rs.close();
                stmt.close();

            }

            for(Iterator iter=pUsers.iterator(); iter.hasNext();) {
                NscUsView nscUsVw = (NscUsView) iter.next();
                int acctId = nscUsVw.getAccountId();
                String siteRefNum = nscUsVw.getLocationNumber();
                String key = siteRefNum+"*"+acctId;
                Integer siteIdI = (Integer) siteAcctHM.get(key);
                if(siteIdI==null) {
                    String errorMess = "Can't find site in account: "+acctId+
                            " with site ref number: "+siteRefNum;
                    errors.add(errorMess);
                } else {
                    nscUsVw.setSiteId(siteIdI.intValue());
                }
            }

            if (pSaveCatalogFl) {
              //Catalogs
              dbc = new DBCriteria();
              dbc.addEqualTo(CatalogAssocDataAccess.BUS_ENTITY_ID, pStoreId);
              dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD,
                             RefCodeNames.CATALOG_ASSOC_CD.CATALOG_STORE);
              String catalogStoreReq =
                  CatalogAssocDataAccess.getSqlSelectIdOnly(CatalogAssocDataAccess.CATALOG_ID, dbc);


              IdVector catalogNameV = new IdVector();
              for(Iterator iter = catalogNameHS.iterator(); iter.hasNext();) {
                catalogNameV.add(iter.next());
              }

              dbc = new DBCriteria();
              dbc.addOneOf(CatalogDataAccess.CATALOG_ID, catalogStoreReq);
              dbc.addOneOf(CatalogDataAccess.SHORT_DESC,catalogNameV);
              dbc.addEqualTo(CatalogDataAccess.CATALOG_TYPE_CD,
                             RefCodeNames.CATALOG_TYPE_CD.SHOPPING);
              log.info("[UserBean].saveNscUsers -> catalogs by name sql: "+ CatalogDataAccess.getSqlSelectIdOnly("*", dbc));
              CatalogDataVector catalogDV = CatalogDataAccess.select(con, dbc);

              HashMap catalogHM = new HashMap();
              for(Iterator iter=catalogDV.iterator(); iter.hasNext();) {
                CatalogData catalogD = (CatalogData) iter.next();
                String catalogName = catalogD.getShortDesc();
                Integer catalogIdI = (Integer) catalogHM.get(catalogName);
                if(catalogIdI!=null) {
                    if(catalogIdI.intValue()!=catalogD.getCatalogId()) {
                        String errorMess = "Catalog name is not unique. " +
                                "Catalog name: "+catalogName +" Catalog ids: "+catalogIdI+
                                " and "+catalogD.getCatalogId();
                        errors.add(errorMess);
                    }
                } else {
                    catalogHM.put(catalogName, new Integer(catalogD.getCatalogId()));
                }
              }
              for(Iterator iter=pUsers.iterator(); iter.hasNext();) {
                NscUsView nscUsVw = (NscUsView) iter.next();
                String catalogName = nscUsVw.getCatalogName();
                Integer catalogIdI = (Integer) catalogHM.get(catalogName);
                if(catalogIdI==null) {
                    String mess = "Can't find catalog. Catalog name: "+catalogName;
                    log.info("UserBean UUUUUUUUUUUUU: "+mess);
                    //errors.add(errorMess);
                } else {
                    nscUsVw.setCatalogId(catalogIdI.intValue());
                }
            }


            //Members
            LinkedList memberNumVV = new LinkedList();
            IdVector memberNumV = new IdVector();
            memberNumVV.add(memberNumV);
            for(Iterator iter=memberNumHS.iterator(); iter.hasNext();) {
                String memberNum = (String) iter.next();
                if(memberNumV.size()>=999) {
                    memberNumV = new IdVector();
                    memberNumVV.add(memberNumV);
                }
                memberNumV.add(memberNum);
            }

            HashMap memberHM = new HashMap();
            for(Iterator iter=memberNumVV.iterator(); iter.hasNext();) {
               memberNumV = (IdVector) iter.next();
                dbc = new DBCriteria();
                dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY2_ID, pStoreId);
                dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,
                        RefCodeNames.BUS_ENTITY_ASSOC_CD.DISTRIBUTOR_STORE);
                String distStoreReq =
                        BusEntityAssocDataAccess.getSqlSelectIdOnly(BusEntityAssocDataAccess.BUS_ENTITY1_ID, dbc);

                //select only active distributors
                dbc = new DBCriteria();
                dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, distStoreReq);
                dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_STATUS_CD, RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);
                IdVector activeDists = BusEntityDataAccess.selectIdOnly(con, dbc);

                dbc = new DBCriteria();
                dbc.addOneOf(PropertyDataAccess.BUS_ENTITY_ID, activeDists);
                dbc.addOneOf(PropertyDataAccess.CLW_VALUE, memberNumV);
                dbc.addEqualTo(PropertyDataAccess.SHORT_DESC,
                        RefCodeNames.PROPERTY_TYPE_CD.DISTRIBUTORS_COMPANY_CODE);
                dbc.addOrderBy(PropertyDataAccess.BUS_ENTITY_ID);

                log.info("[UserBean].saveNscUsers -> serviceing members sql: "+BusEntityDataAccess.getSqlSelectIdOnly("*", dbc));
                PropertyDataVector memberPropDV = PropertyDataAccess.select(con, dbc);
                int prevMemberId = 0;
                String prevMemberNum = "";
                for(Iterator iter1 = memberPropDV.iterator(); iter1.hasNext();) {
                    PropertyData pD = (PropertyData) iter1.next();
                    int memberId = pD.getBusEntityId();
                    String memberNum = Utility.strNN(pD.getValue());
                    if(prevMemberId!=memberId) {
                        prevMemberId = memberId;
                        prevMemberNum = memberNum;
                        Integer mIdI = (Integer) memberHM.get(memberNum);
                        if(mIdI!=null) {
                            if(mIdI.intValue()!=memberId) {
                                String errorMess = "Distributor "+memberId+
                                        " has multiple Distributor Company Code values";
                            errors.add(errorMess);
                            }
                        } else {
                            memberHM.put(memberNum, new Integer(memberId));
                        }
                    } else {
                        if(!prevMemberNum.equals(memberNum)) {
                            String errorMess = "Distributor "+memberId+
                                    " has multiple Distributor Company Code values";
                            errors.add(errorMess);
                        }
                    }
                }
              }

              for(Iterator iter=pUsers.iterator(); iter.hasNext();) {
                NscUsView nscUsVw = (NscUsView) iter.next();
                String memberNum = nscUsVw.getMemberNumber();
                Integer memberIdI = (Integer) memberHM.get(memberNum);
                if(memberIdI==null) {
                    String errorMess = "No distributor exists for member number: "+memberNum;
                    errors.add(errorMess);
                } else {
                    nscUsVw.setMemberId(memberIdI.intValue());
                }
              }
            } //end  if (pSaveCatalogFl)

            //////////// Finish if errors found
            if(errors.size()>0) {
                return errors;
            }
            UserDataVector userDV = new UserDataVector();

            //Existing users
            for(Iterator iter=userName1000.iterator(); iter.hasNext();) {
                IdVector unIdV = (IdVector) iter.next();
                dbc = new DBCriteria();
                dbc.addOneOf(UserDataAccess.USER_NAME, unIdV);
                dbc.addOrderBy(UserDataAccess.USER_NAME);
                log.info("[UserBean].saveNscUsers -> Existing users sql: "+UserDataAccess.getSqlSelectIdOnly("*", dbc));
                UserDataVector uDV = UserDataAccess.select(con, dbc);
                userDV.addAll(uDV);
             }

            //Match with the provided users
            NscUsViewVector usersToCreate = new NscUsViewVector();
            UserDataVector usersToUpdate = new UserDataVector();
            NscUsViewVector usersExist = new NscUsViewVector();
            UserData wrkUsD = null;
            int prevUserId = -1;
            for(Iterator iter=pUsers.iterator(),iter1 = userDV.iterator(); iter.hasNext();) {
                NscUsView nscUsVw = (NscUsView) iter.next();
                String userName = nscUsVw.getUserName();
                boolean foundFl = false;
                while(iter1.hasNext() || wrkUsD!=null) {
                    if(wrkUsD==null) wrkUsD = (UserData) iter1.next();
                    int comp = userName.compareTo(wrkUsD.getUserName());
                    if(comp==0) {
                        foundFl = true;
                        int userId = wrkUsD.getUserId();
                        nscUsVw.setUserId(userId);
                        usersExist.add(nscUsVw);
                        if(prevUserId!=userId) {
                            prevUserId = userId;
                            if(!wrkUsD.getFirstName().equals(nscUsVw.getFirstName()) ||
                               !wrkUsD.getLastName().equals(nscUsVw.getLastName()) ||
                               !wrkUsD.getPassword().equals(nscUsVw.getPassword()) ||
                               !wrkUsD.getUserStatusCd().equals(RefCodeNames.USER_STATUS_CD.ACTIVE)) {
                                wrkUsD.setFirstName(nscUsVw.getFirstName());
                                wrkUsD.setLastName(nscUsVw.getLastName());
                                wrkUsD.setPassword(nscUsVw.getPassword());
                                wrkUsD.setUserStatusCd(RefCodeNames.USER_STATUS_CD.ACTIVE);
                                wrkUsD.setModBy(pAddBy);
                                usersToUpdate.add(wrkUsD);
                            }
                        }
                        break;
                    }
                    if(comp>0) {
                        wrkUsD = null;
                        continue;
                    }
                    break;
                }
                if(!foundFl) {
                    usersToCreate.add(nscUsVw);
                }
            }

            //Process existing users
            IdVector userIdV = new IdVector();
            ///////////////////////////////
            IdVector userPropertyIdToDelete = new IdVector();
            PropertyDataVector userPropertyToAdd = new PropertyDataVector();
            PropertyDataVector userPropertyToUpdate = new PropertyDataVector();

            IdVector userAssocIdToDelete = new IdVector();
            UserAssocDataVector userAssocToAdd = new UserAssocDataVector();
            UserAssocDataVector userAssocToUpdate = new UserAssocDataVector();
            if(!usersExist.isEmpty()) {

                usersExist.sort("UserId"); //!!!!!!!!!!!!!!!!!!!!

                for(Iterator iter = usersExist.iterator(); iter.hasNext();) {
                    NscUsView nscUsVw = (NscUsView) iter.next();
                    userIdV.add(new Integer(nscUsVw.getUserId()));
                }

                //User id codes
                dbc = new DBCriteria();
                dbc.addOneOf(PropertyDataAccess.USER_ID,userIdV);
                dbc.addEqualTo(PropertyDataAccess.SHORT_DESC,
                        RefCodeNames.PROPERTY_TYPE_CD.USER_ID_CODE);
                dbc.addOrderBy(PropertyDataAccess.USER_ID);
                log.info("[UserBean].saveNscUsers -> user code request: "+UserAssocDataAccess.getSqlSelectIdOnly("*", dbc));
                prevUserId = 0;
                PropertyDataVector userCodePropDV = PropertyDataAccess.select(con, dbc);
                for(Iterator iter=usersExist.iterator(),iter1 = userCodePropDV.iterator(); iter.hasNext();) {
                    NscUsView nscUsVw = (NscUsView) iter.next();
                    int userId = nscUsVw.getUserId();
                    String userCode = nscUsVw.getCustomerNumber()+"*"+nscUsVw.getLocationNumber();
                    if(userId==prevUserId) {
                        continue;
                    }
                    prevUserId = userId;
                    PropertyData wrkPrD = null;
                    int count = 0;
                    while(iter1.hasNext()||wrkPrD!=null) {
                        if(wrkPrD==null) wrkPrD = (PropertyData) iter1.next();
                        int uId = wrkPrD.getUserId();
                        if(userId==uId) {
							count++;
                            if(!userCode.equals(wrkPrD.getValue()) && count==1){
                                wrkPrD.setValue(userCode);
                                wrkPrD.setModBy(pAddBy);
                                userPropertyToUpdate.add(wrkPrD);
                            }
                            if(count>1) {
                                userPropertyIdToDelete.add(new Integer(wrkPrD.getPropertyId()));
                            }
                            wrkPrD = null;
                            continue;
                        }
                        if(userId<uId) {
                            break;
                        }
                        wrkPrD = null;
                        continue;
                    }
                    if(count==0) {
                        PropertyData pD = new PropertyData();
                        pD.setAddBy(pAddBy);
                        pD.setModBy(pAddBy);
                        pD.setUserId(userId);
                        pD.setValue(userCode);
                        pD.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
                        pD.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.USER_ID_CODE);
                        pD.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.USER_ID_CODE);
                        userPropertyToAdd.add(pD);
                    }
                }


                //Get account associations
                dbc = new DBCriteria();
                dbc.addOneOf(UserAssocDataAccess.USER_ID, userIdV);
                dbc.addEqualTo(UserAssocDataAccess.USER_ASSOC_CD,
                        RefCodeNames.USER_ASSOC_CD.ACCOUNT);
                dbc.addOrderBy(UserAssocDataAccess.USER_ID);
                log.info("[UserBean].saveNscUsers -> user account assoc: "+UserAssocDataAccess.getSqlSelectIdOnly("*", dbc));
                UserAssocDataVector uaDV = UserAssocDataAccess.select(con, dbc);
                int index = 0;
                int groupIndex = 0;
                prevUserId = 0;
                for(Iterator iter=usersExist.iterator(); iter.hasNext();) {
                    NscUsView nscUsVw = (NscUsView) iter.next();
                    int userId = nscUsVw.getUserId();
                    int acctId = nscUsVw.getAccountId();
                    if(prevUserId==userId) {
                        index = groupIndex;
                    } else {
                        groupIndex = index;
                    }
                    boolean foundFl = false;
                    int count = 0;
                    for(;index<uaDV.size(); index++) {
                        UserAssocData uaD = (UserAssocData) uaDV.get(index);
                        int uId = uaD.getUserId();
                        if(uId<userId) {
                            continue;
                        }
                        if(uId==userId) {
                            count++;
                            if(acctId==uaD.getBusEntityId()) {
                                foundFl = true;
                            }
                        }
                        if(uId>userId) {
                            break;
                        }
                    }
                    if(!foundFl) {
                        if(count>0) {
                            UserAssocData uaD = (UserAssocData) uaDV.get(groupIndex);
                            uaD.setBusEntityId(acctId);
                            uaD.setModBy(pAddBy);
                            userAssocToUpdate.add(uaD);
                            for(int ii=groupIndex+1; ii<index; ii++ ) {
                                uaD = (UserAssocData) uaDV.get(ii);
                                userAssocIdToDelete.add(new Integer(uaD.getUserAssocId()));
                            }
                        } else {
                            UserAssocData uaD = new UserAssocData();
                            uaD.setUserId(userId);
                            uaD.setBusEntityId(acctId);
                            uaD.setUserAssocCd(RefCodeNames.USER_ASSOC_CD.ACCOUNT);
                            uaD.setAddBy(pAddBy);
                            uaD.setModBy(pAddBy);
                            userAssocToAdd.add(uaD);
                        }
                    }
                }
                //Site assocations

                dbc = new DBCriteria();
                dbc.addOneOf(UserAssocDataAccess.USER_ID, userIdV);
                dbc.addEqualTo(UserAssocDataAccess.USER_ASSOC_CD,
                        RefCodeNames.USER_ASSOC_CD.SITE);
                dbc.addOrderBy(UserAssocDataAccess.USER_ID);
                log.info("[UserBean].saveNscUsers -> user account assoc: "+UserAssocDataAccess.getSqlSelectIdOnly("*", dbc));
                uaDV = UserAssocDataAccess.select(con, dbc);
                index = 0;
                groupIndex = 0;
                prevUserId = 0;
                for(Iterator iter=usersExist.iterator(); iter.hasNext();) {
                    NscUsView nscUsVw = (NscUsView) iter.next();
                    int userId = nscUsVw.getUserId();
                    int siteId = nscUsVw.getSiteId();
                    if(prevUserId==userId) {
                        index = groupIndex;
                    } else {
                        groupIndex = index;
                    }
                    boolean foundFl = false;
                    int count = 0;
                    for(;index<uaDV.size(); index++) {
                        UserAssocData uaD = (UserAssocData) uaDV.get(index);
                        int uId = uaD.getUserId();
                        if(uId<userId) {
                            continue; //never should happen
                        }
                        if(uId==userId) {
                            count++;
                            if(siteId==uaD.getBusEntityId()) {
                                foundFl = true;
                            }
                        }
                        if(uId>userId) {
                            break;
                        }
                    }
                    if(!foundFl) {
                        if(count>0) {
                            UserAssocData uaD = (UserAssocData) uaDV.get(groupIndex);
                            uaD.setBusEntityId(siteId);
                            uaD.setModBy(pAddBy);
                            userAssocToUpdate.add(uaD);
                            for(int ii=groupIndex+1; ii<index; ii++ ) {
                                uaD = (UserAssocData) uaDV.get(ii);
                                userAssocIdToDelete.add(new Integer(uaD.getUserAssocId()));
                            }
                        } else {
                            UserAssocData uaD = new UserAssocData();
                            uaD.setUserId(userId);
                            uaD.setBusEntityId(siteId);
                            uaD.setUserAssocCd(RefCodeNames.USER_ASSOC_CD.SITE);
                            uaD.setAddBy(pAddBy);
                            uaD.setModBy(pAddBy);
                            userAssocToAdd.add(uaD);
                        }
                    }
                }

                //get users to inactivate
                dbc = new DBCriteria();
                dbc.addOneOf(UserAssocDataAccess.BUS_ENTITY_ID, accountIdV);
                dbc.addEqualTo(UserAssocDataAccess.USER_ASSOC_CD,
                        RefCodeNames.USER_ASSOC_CD.ACCOUNT);
                String userAccountReq =
                        UserAssocDataAccess.getSqlSelectIdOnly(UserAssocDataAccess.USER_ID, dbc);
                dbc = new DBCriteria();
                dbc.addOneOf(UserDataAccess.USER_ID, userAccountReq);
                dbc.addEqualTo(UserDataAccess.USER_STATUS_CD,
                        RefCodeNames.USER_STATUS_CD.ACTIVE);
				dbc.addEqualTo(UserDataAccess.USER_TYPE_CD,
				        RefCodeNames.USER_TYPE_CD.MSB);
                dbc.addOrderBy(UserDataAccess.USER_ID);
                log.info("[UserBean].saveNscUsers -> active users req: "+UserDataAccess.getSqlSelectIdOnly("*", dbc));
                UserDataVector activeUserDV = UserDataAccess.select(con, dbc);
                prevUserId = 0;
                UserData wrkUserD = null;
                Iterator iter1 = iter1=activeUserDV.iterator();
                for(Iterator iter=usersExist.iterator(); iter.hasNext();) {
                    NscUsView nscUsVw = (NscUsView) iter.next();
                    int userId = nscUsVw.getUserId();
                    if(userId==prevUserId) {
                        continue;
                    }
                    prevUserId = userId;
                    while(iter1.hasNext() || wrkUserD!=null) {
                        if(wrkUserD==null) wrkUserD = (UserData) iter1.next();
                        int uId = wrkUserD.getUserId();
                        if(uId==userId) {
                            wrkUserD = null;
                            break;
                        }
                        if(uId<userId) {
                            if(!ignoreUserHS.contains(new Integer(uId))) {
                                wrkUserD.setUserStatusCd(RefCodeNames.USER_STATUS_CD.INACTIVE);
                                wrkUserD.setModBy(pAddBy);
                                usersToUpdate.add(wrkUserD);
                            }
                            wrkUserD = null;
                            continue;
                        }
                        break;
                    }
                }
                while(iter1.hasNext() || wrkUserD!=null) {
                    if(wrkUserD==null) wrkUserD = (UserData) iter1.next();
					int uId = wrkUserD.getUserId();
                    if(!ignoreUserHS.contains(new Integer(uId))) {
                       wrkUserD.setUserStatusCd(RefCodeNames.USER_STATUS_CD.INACTIVE);
                       wrkUserD.setModBy(pAddBy);
                       usersToUpdate.add(wrkUserD);
                    }
                    wrkUserD = null;
				}

            }

        //---------------------------------------------------
        pUsers.sort("SiteId"); //!!!!!!!!!!!!
        IdVector siteIdV = new IdVector();
        NscUsViewVector siteDistinctV = new NscUsViewVector();
        int prevSiteId = 0;
        for(Iterator iter=pUsers.iterator(); iter.hasNext();) {
            NscUsView nscUsVw = (NscUsView) iter.next();
            int siteId = nscUsVw.getSiteId();
            if(siteId!=prevSiteId) {
                prevSiteId = siteId;
                siteIdV.add(new Integer(nscUsVw.getSiteId()));
                siteDistinctV.add(nscUsVw);
            }
        }
        //----------------------------------------------------
        IdVector catalogAssocIdToDelete = new IdVector();
        CatalogAssocDataVector catalogAssocToAdd = new CatalogAssocDataVector();
        CatalogAssocDataVector catalogAssocToUpdate = new CatalogAssocDataVector();

        IdVector memberAssocIdToDelete = new IdVector();
        BusEntityAssocDataVector memberAssocToAdd = new BusEntityAssocDataVector();
        BusEntityAssocDataVector memberAssocToUpdate = new BusEntityAssocDataVector();

        if (pSaveCatalogFl) {
            /////////////////////////////
            //Site - Catalog Assoc
            dbc = new DBCriteria();
            dbc.addOneOf(CatalogAssocDataAccess.BUS_ENTITY_ID, siteIdV);
            dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD,
                    RefCodeNames.CATALOG_ASSOC_CD.CATALOG_SITE);
            dbc.addOrderBy(CatalogAssocDataAccess.BUS_ENTITY_ID);
            log.info("[UserBean].saveNscUsers -> site catalog assoc: "+CatalogAssocDataAccess.getSqlSelectIdOnly("*", dbc));

            CatalogAssocDataVector catalogAssocDV = CatalogAssocDataAccess.select(con, dbc);
            CatalogAssocData wrkCatAssocD = null;
//            IdVector catalogAssocIdToDelete = new IdVector();
//            CatalogAssocDataVector catalogAssocToAdd = new CatalogAssocDataVector();
//            CatalogAssocDataVector catalogAssocToUpdate = new CatalogAssocDataVector();
            for(Iterator iter=siteDistinctV.iterator(), iter1=catalogAssocDV.iterator(); iter.hasNext();) {
                NscUsView nscUsVw = (NscUsView) iter.next();
                int siteId = nscUsVw.getSiteId();
                int catalogId = nscUsVw.getCatalogId();
                if(catalogId<=0) {
                    continue;
                }
                int count = 0;
                while(iter1.hasNext()|| wrkCatAssocD!=null) {
                    if(wrkCatAssocD==null) wrkCatAssocD = (CatalogAssocData)iter1.next();
                    int sId = wrkCatAssocD.getBusEntityId();
                    int cId = wrkCatAssocD.getCatalogId();
                    if(siteId==sId) {
                        count++;
                        if(catalogId!=cId && count==1) {
                            wrkCatAssocD.setCatalogId(catalogId);
                            wrkCatAssocD.setModBy(pAddBy);
                            catalogAssocToUpdate.add(wrkCatAssocD);
                            wrkCatAssocD = null;
                        }
                        if(count>1) {
                            catalogAssocIdToDelete.add(new Integer(wrkCatAssocD.getCatalogAssocId()));
                        }
                        wrkCatAssocD = null;
                        continue;
                    }
                    if(siteId<sId) {
                        break;
                    }
                    wrkCatAssocD = null;
                    continue;
                }
                if(count==0) {
                    CatalogAssocData caD = new CatalogAssocData();
                    caD.setAddBy(pAddBy);
                    caD.setModBy(pAddBy);
                    caD.setBusEntityId(siteId);
                    caD.setCatalogId(catalogId);
                    caD.setCatalogAssocCd(RefCodeNames.CATALOG_ASSOC_CD.CATALOG_SITE);
                    catalogAssocToAdd.add(caD);
                }
            }
            /////////////////////////////
            //Site - Service Agent
            dbc = new DBCriteria();
            dbc.addOneOf(BusEntityAssocDataAccess.BUS_ENTITY2_ID, siteIdV);
            dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,
                    RefCodeNames.BUS_ENTITY_ASSOC_CD.DISTRIBUTOR_SITE);
            dbc.addOrderBy(BusEntityAssocDataAccess.BUS_ENTITY2_ID);
            log.info("[UserBean].saveNscUsers -> site member assoc: "+BusEntityAssocDataAccess.getSqlSelectIdOnly("*", dbc));

            BusEntityAssocDataVector memberAssocDV = BusEntityAssocDataAccess.select(con, dbc);
            BusEntityAssocData wrkMemberAssocD = null;
//            IdVector memberAssocIdToDelete = new IdVector();
//            BusEntityAssocDataVector memberAssocToAdd = new BusEntityAssocDataVector();
//            BusEntityAssocDataVector memberAssocToUpdate = new BusEntityAssocDataVector();
            for(Iterator iter=siteDistinctV.iterator(), iter1=memberAssocDV.iterator(); iter.hasNext();) {
                NscUsView nscUsVw = (NscUsView) iter.next();
                int siteId = nscUsVw.getSiteId();
                int memberId = nscUsVw.getMemberId();
                int count = 0;
                while(iter1.hasNext()|| wrkMemberAssocD!=null) {
                    if(wrkMemberAssocD==null) wrkMemberAssocD = (BusEntityAssocData)iter1.next();
                    int sId = wrkMemberAssocD.getBusEntity2Id();
                    int mId = wrkMemberAssocD.getBusEntity1Id();
                    if(siteId==sId) {
                        count++;
                        if(memberId!=mId && count==1) {
                            wrkMemberAssocD.setBusEntity1Id(memberId);
                            wrkMemberAssocD.setModBy(pAddBy);
                            memberAssocToUpdate.add(wrkMemberAssocD);
                            wrkMemberAssocD = null;
                        }
                        if(count>1) {
                            memberAssocIdToDelete.add(new Integer(wrkMemberAssocD.getBusEntityAssocId()));
                        }
                        wrkMemberAssocD = null;
                        continue;
                    }
                    if(siteId<sId) {
                        break;
                    }
                    wrkMemberAssocD = null;
                    continue;
                }
                if(count==0) {
                    BusEntityAssocData maD = new BusEntityAssocData();
                    maD.setAddBy(pAddBy);
                    maD.setModBy(pAddBy);
                    maD.setBusEntity2Id(siteId);
                    maD.setBusEntity1Id(memberId);
                    maD.setBusEntityAssocCd(RefCodeNames.BUS_ENTITY_ASSOC_CD.DISTRIBUTOR_SITE);
                    memberAssocToAdd.add(maD);
                }
            }
         } // end if (pSaveCatalogFl)
            /////////////////////////////
            //Site - Email
            dbc = new DBCriteria();
            dbc.addOneOf(PropertyDataAccess.BUS_ENTITY_ID, siteIdV);
            dbc.addEqualTo(PropertyDataAccess.SHORT_DESC,"EMAIL");
            dbc.addEqualTo(PropertyDataAccess.PROPERTY_TYPE_CD,
                    RefCodeNames.PROPERTY_TYPE_CD.SITE_FIELD_CD);
            dbc.addOrderBy(PropertyDataAccess.BUS_ENTITY_ID);
            log.info("[UserBean].saveNscUsers -> site email assoc: "+PropertyDataAccess.getSqlSelectIdOnly("*", dbc));

            PropertyDataVector siteEmailDV = PropertyDataAccess.select(con, dbc);
            PropertyData wrkPropD = null;
            IdVector sitePropertyIdToDelete = new IdVector();
            PropertyDataVector sitePropertyToAdd = new PropertyDataVector();
            PropertyDataVector sitePropertyToUpdate = new PropertyDataVector();
            for(Iterator iter=siteDistinctV.iterator(), iter1=siteEmailDV.iterator(); iter.hasNext();) {
                NscUsView nscUsVw = (NscUsView) iter.next();
                int siteId = nscUsVw.getSiteId();
                String email = nscUsVw.getEmailAddress();
                int count = 0;
                while(iter1.hasNext()|| wrkPropD!=null) {
                    if(wrkPropD==null) wrkPropD = (PropertyData)iter1.next();
                    int sId = wrkPropD.getBusEntityId();
                    String sss = Utility.strNN(wrkPropD.getValue());
                    if(siteId==sId) {
                        count++;
                        if(!sss.equals(email) && count==1) {
                            wrkPropD.setValue(email);
                            wrkPropD.setModBy(pAddBy);
                            sitePropertyToUpdate.add(wrkPropD);
                            wrkPropD = null;
                        }
                        if(count>1) {
                            sitePropertyIdToDelete.add(new Integer(wrkPropD.getPropertyId()));
                        }
                        wrkPropD = null;
                        continue;
                    }
                    if(siteId<sId) {
                        break;
                    }
                    wrkPropD = null;
                    continue;
                }
                if(count==0) {
                    PropertyData propD = new PropertyData();
                    propD.setAddBy(pAddBy);
                    propD.setModBy(pAddBy);
                    propD.setBusEntityId(siteId);
                    propD.setValue(email);
                    propD.setShortDesc("EMAIL");
                    propD.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.SITE_FIELD_CD);
                    propD.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
                    sitePropertyToAdd.add(propD);
                }
            }
          HashMap catalogSiteHM = new HashMap();
          HashMap catalogAcctHM = new HashMap();
          if (pSaveCatalogFl) {
            // New  catalogs /////////////////////////////////////////
            for(Iterator iter=pUsers.iterator(); iter.hasNext(); ) {
               NscUsView nscUsVw = (NscUsView) iter.next();
               if(nscUsVw.getCatalogId()==0) {
                   String catalogName = nscUsVw.getCatalogName();
                   Integer siteIdI = new Integer(nscUsVw.getSiteId());
                   Integer acctIdI = new Integer(nscUsVw.getAccountId());
                   HashSet siteHS = (HashSet) catalogSiteHM.get(catalogName);
                   if(siteHS==null) {
                       siteHS = new HashSet();
                       catalogSiteHM.put(catalogName,siteHS);
                       siteHS.add(siteIdI);
                       catalogAcctHM.put(catalogName,acctIdI);
                   } else {
                       if(siteHS.contains(siteIdI)) {
                           siteHS.add(siteIdI);
                       }
                   }
               }
            }
          }  // end if (pSaveCatalogFl)
            ///////////////////////////////////////////////////////////////////////
            /////////// Starting DB Updates ///////////////////////////////////////
            // Users
          	log.info("[UserBean].saveNscUsers -> updating users");
            for(Iterator iter=usersToUpdate.iterator();iter.hasNext();) {
                UserData uD = (UserData) iter.next();
                updateUserAndPasswordHistory(con, uD, false);
            }

            // User associations
            log.info("[UserBean].saveNscUsers -> removing users assoc");
            if(!userAssocIdToDelete.isEmpty()) {
                dbc = new DBCriteria();
                dbc.addOneOf(UserAssocDataAccess.USER_ASSOC_ID, userAssocIdToDelete);
                UserAssocDataAccess.remove(con, dbc);
            }
            log.info("[UserBean].saveNscUsers -> adding users assoc");
            for(Iterator iter=userAssocToAdd.iterator(); iter.hasNext(); ) {
                UserAssocData uaD = (UserAssocData) iter.next();
                UserAssocDataAccess.insert(con, uaD);
            }
            log.info("[UserBean].saveNscUsers -> updating users assoc");
            for(Iterator iter=userAssocToUpdate.iterator(); iter.hasNext(); ) {
                UserAssocData uaD = (UserAssocData) iter.next();
                UserAssocDataAccess.update(con, uaD);
            }
          if (pSaveCatalogFl) {
            // Site Catalog Associations
        	  log.info("[UserBean].saveNscUsers -> removing catalog assoc");
            if(!catalogAssocIdToDelete.isEmpty()) {
                dbc = new DBCriteria();
                dbc.addOneOf(CatalogAssocDataAccess.CATALOG_ASSOC_ID, catalogAssocIdToDelete);
                CatalogAssocDataAccess.remove(con, dbc);
            }
            log.info("[UserBean].saveNscUsers -> adding catalog assoc");
            for(Iterator iter=catalogAssocToAdd.iterator(); iter.hasNext(); ) {
                CatalogAssocData caD = (CatalogAssocData) iter.next();
                CatalogAssocDataAccess.insert(con, caD);
            }
            log.info("[UserBean].saveNscUsers -> updating catalog assoc");
            for(Iterator iter=catalogAssocToUpdate.iterator(); iter.hasNext(); ) {
                CatalogAssocData caD = (CatalogAssocData) iter.next();
                CatalogAssocDataAccess.update(con, caD);
            }

            //Servicing member assoc
            log.info("[UserBean].saveNscUsers -> removing site servicing agent assoc");
            if(!memberAssocIdToDelete.isEmpty()) {
                dbc = new DBCriteria();
                dbc.addOneOf(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_ID, memberAssocIdToDelete);
                BusEntityAssocDataAccess.remove(con, dbc);
            }
            log.info("[UserBean].saveNscUsers -> adding site servicing agent assoc");
            for(Iterator iter=memberAssocToAdd.iterator(); iter.hasNext(); ) {
                BusEntityAssocData maD = (BusEntityAssocData) iter.next();
                BusEntityAssocDataAccess.insert(con, maD);
            }
            log.info("[UserBean].saveNscUsers -> updating site servicing agent assoc");
            for(Iterator iter=memberAssocToUpdate.iterator(); iter.hasNext(); ) {
                BusEntityAssocData maD = (BusEntityAssocData) iter.next();
                BusEntityAssocDataAccess.update(con, maD);
            }
          } // end if (pSaveCatalogFl)
            //User properties
          	log.info("[UserBean].saveNscUsers -> removing user properties");
            if(!userPropertyIdToDelete.isEmpty()) {
                dbc = new DBCriteria();
                dbc.addOneOf(PropertyDataAccess.PROPERTY_ID, userPropertyIdToDelete);
                PropertyDataAccess.remove(con, dbc);
            }
            log.info("[UserBean].saveNscUsers -> adding user properties");
            for(Iterator iter=userPropertyToAdd.iterator(); iter.hasNext(); ) {
                PropertyData pD = (PropertyData) iter.next();
                PropertyDataAccess.insert(con, pD);
            }
            log.info("[UserBean].saveNscUsers -> updating user properties");
            for(Iterator iter=userPropertyToUpdate.iterator(); iter.hasNext(); ) {
                PropertyData pD = (PropertyData) iter.next();
                PropertyDataAccess.update(con, pD);
            }

            log.info("[UserBean].saveNscUsers -> removing user properties");
            if(!sitePropertyIdToDelete.isEmpty()) {
                dbc = new DBCriteria();
                dbc.addOneOf(PropertyDataAccess.PROPERTY_ID, sitePropertyIdToDelete);
                PropertyDataAccess.remove(con, dbc);
            }
            log.info("[UserBean].saveNscUsers -> adding site email properties");
            for(Iterator iter=sitePropertyToAdd.iterator(); iter.hasNext(); ) {
                PropertyData pD = (PropertyData) iter.next();
                PropertyDataAccess.insert(con, pD);
            }
            log.info("[UserBean].saveNscUsers -> updating site email properties");
            for(Iterator iter=sitePropertyToUpdate.iterator(); iter.hasNext(); ) {
                PropertyData pD = (PropertyData) iter.next();
                PropertyDataAccess.update(con, pD);
            }
          if (pSaveCatalogFl) {
            /////////////////////////////////////////////////////////////
            // Create shopping catalogs
            Set catalogEntries = catalogSiteHM.entrySet();
            for(Iterator iter = catalogEntries.iterator(); iter.hasNext();) {
                Map.Entry entry = (Map.Entry) iter.next();
                String catalogName = (String) entry.getKey();
                HashSet siteHS = (HashSet) entry.getValue();
                Integer accountIdI = (Integer) catalogAcctHM.get(catalogName);
                int accountId = accountIdI.intValue();
                log.info("[UserBean].saveNscUsers -> creating catalog: "+catalogName);
                createCatalog(con, catalogName, pStoreId, accountId,
                        pDistributorId, siteHS, pAddBy );
            }
          }
            String userRole = "SP^CI^OA^";
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            Date effDate = sdf.parse(sdf.format(new Date()));
            //Create users
            String prevUserName = "";
            HashSet wrkAcctHS = null;
            HashSet wrkSiteHS = null;
            for(Iterator iter=usersToCreate.iterator(); iter.hasNext();) {
                NscUsView nscUsVw = (NscUsView) iter.next();
                String userName = nscUsVw.getUserName();
                if(nscUsVw.getUserId()>0) {
                    throw new Exception ("User which should not have id has it.User name "+userName+" userId: "+nscUsVw.getUserId());
                }
                log.info("[UserBean].saveNscUsers -> creating user: "+userName);
                int userId = 0;
                if(!prevUserName.equals(userName)) {
                    prevUserName = userName;
                    wrkAcctHS = new HashSet();
                    wrkSiteHS = new HashSet();

                    UserData uD = new UserData();
                    uD.setEffDate(effDate);
                    uD.setFirstName(nscUsVw.getFirstName());
                    uD.setLastName(nscUsVw.getLastName());
                    uD.setPassword(nscUsVw.getPassword());
                    uD.setPrefLocaleCd(RefCodeNames.LOCALE_CD.EN_US);
                    uD.setUserName(userName);
                    uD.setUserRoleCd(userRole);
                    uD.setUserStatusCd(RefCodeNames.USER_STATUS_CD.ACTIVE);
                    uD.setUserTypeCd(RefCodeNames.USER_TYPE_CD.MSB);
                    uD.setWorkflowRoleCd(RefCodeNames.WORKFLOW_ROLE_CD.UNKNOWN);
                    uD.setAddBy(pAddBy);
                    uD.setModBy(pAddBy);
                    uD = updateUserAndPasswordHistory(con, uD, true);
                    userId = uD.getUserId();

                    UserAssocData uaD = new UserAssocData();
                    uaD.setBusEntityId(pStoreId);
                    uaD.setUserAssocCd(RefCodeNames.USER_ASSOC_CD.STORE);
                    uaD.setUserId(userId);
                    uaD.setAddBy(pAddBy);
                    uaD.setModBy(pAddBy);
                    UserAssocDataAccess.insert(con, uaD);
                }
                int accountId = nscUsVw.getAccountId();
                Integer accountIdI = new Integer(accountId);
                if(!wrkAcctHS.contains(accountIdI)) {
                    wrkAcctHS.add(accountIdI);
                    UserAssocData uaD = new UserAssocData();
                    uaD.setBusEntityId(accountId);
                    uaD.setUserAssocCd(RefCodeNames.USER_ASSOC_CD.ACCOUNT);
                    uaD.setUserId(userId);
                    uaD.setAddBy(pAddBy);
                    uaD.setModBy(pAddBy);
                    UserAssocDataAccess.insert(con, uaD);
                }
                int siteId = nscUsVw.getSiteId();
                Integer siteIdI = new Integer(siteId);
                if(!wrkSiteHS.contains(siteIdI)) {
                    wrkSiteHS.add(siteIdI);
                    UserAssocData uaD = new UserAssocData();
                    uaD.setBusEntityId(siteId);
                    uaD.setUserAssocCd(RefCodeNames.USER_ASSOC_CD.SITE);
                    uaD.setUserId(userId);
                    uaD.setAddBy(pAddBy);
                    uaD.setModBy(pAddBy);
                    UserAssocDataAccess.insert(con, uaD);
                }
            }
            log.info("[UserBean].saveNscUsers -> DONE UUUUUUUUUUUUUUU");

        } catch (Exception e) {
		    e.printStackTrace();
            throw processException(e);
        } finally {
            closeConnection(con);
        }
        return errors;
    }

    private void createCatalog(Connection con, String pCatalogName, int pStoreId, int pAccountId,
            int pDistId, HashSet pSites, String pUserName )
    throws Exception{

        CatalogData catalogD = CatalogData.createValue();
        catalogD.setCatalogTypeCd(RefCodeNames.CATALOG_TYPE_CD.SHOPPING);
        catalogD.setCatalogStatusCd(RefCodeNames.CATALOG_STATUS_CD.ACTIVE);
        catalogD.setAddBy(pUserName);
        catalogD.setModBy(pUserName);
        catalogD.setShortDesc(pCatalogName);
        catalogD = CatalogDataAccess.insert(con, catalogD);
        int catalogId = catalogD.getCatalogId();
        {
        CatalogAssocData storeAssocD = new CatalogAssocData();
        storeAssocD.setBusEntityId(pStoreId);
        storeAssocD.setCatalogId(catalogId);
        storeAssocD.setCatalogAssocCd(RefCodeNames.CATALOG_ASSOC_CD.CATALOG_STORE);
        storeAssocD.setAddBy(pUserName);
        storeAssocD.setModBy(pUserName);
        CatalogAssocDataAccess.insert(con, storeAssocD);
        }
        { //account
        CatalogAssocData acctAssocD = new CatalogAssocData();
        acctAssocD.setBusEntityId(pAccountId);
        acctAssocD.setCatalogId(catalogId);
        acctAssocD.setCatalogAssocCd(RefCodeNames.CATALOG_ASSOC_CD.CATALOG_ACCOUNT);
        acctAssocD.setAddBy(pUserName);
        acctAssocD.setModBy(pUserName);
        CatalogAssocDataAccess.insert(con, acctAssocD);
        }

        //sites
        for(Iterator iter=pSites.iterator(); iter.hasNext();) {
            Integer siteIdI = (Integer) iter.next();
            CatalogAssocData siteAssocD = new CatalogAssocData();
            siteAssocD.setBusEntityId(siteIdI.intValue());
            siteAssocD.setCatalogId(catalogId);
            siteAssocD.setCatalogAssocCd(RefCodeNames.CATALOG_ASSOC_CD.CATALOG_SITE);
            siteAssocD.setAddBy(pUserName);
            siteAssocD.setModBy(pUserName);
            CatalogAssocDataAccess.insert(con, siteAssocD);
        }
        //distributors
        {
        CatalogAssocData distAssocD = new CatalogAssocData();
        distAssocD.setBusEntityId(pDistId);
        distAssocD.setCatalogId(catalogId);
        distAssocD.setCatalogAssocCd(RefCodeNames.CATALOG_ASSOC_CD.CATALOG_DISTRIBUTOR);
        distAssocD.setAddBy(pUserName);
        distAssocD.setModBy(pUserName);
        CatalogAssocDataAccess.insert(con, distAssocD);
        }
        {
        CatalogAssocData mainDistAssocD = new CatalogAssocData();
        mainDistAssocD.setBusEntityId(pDistId);
        mainDistAssocD.setCatalogId(catalogId);
        mainDistAssocD.setCatalogAssocCd(RefCodeNames.CATALOG_ASSOC_CD.CATALOG_MAIN_DISTRIBUTOR);
        mainDistAssocD.setAddBy(pUserName);
        mainDistAssocD.setModBy(pUserName);
        CatalogAssocDataAccess.insert(con, mainDistAssocD);
        }

        //Order guide
        OrderGuideData ogD = OrderGuideData.createValue();
        String orderGuideName = pCatalogName;
        if(orderGuideName.length() > 30) {
            orderGuideName = orderGuideName.substring(0, 29);
        }
        ogD.setShortDesc(orderGuideName);
        ogD.setAddBy(pUserName);
        ogD.setModBy(pUserName);
        ogD.setOrderGuideTypeCd(RefCodeNames.ORDER_GUIDE_TYPE_CD.ORDER_GUIDE_TEMPLATE);
        ogD.setCatalogId(catalogId);
        OrderGuideDataAccess.insert(con, ogD);

		//freight table get account freigh table
        String sql = "SELECT  con.freight_table_id \n "+
           " FROM clw_catalog_assoc ca, clw_catalog c, clw_contract con  \n "+
           " WHERE ca.bus_entity_id = "+ pAccountId +" \n "+
           " AND c.catalog_id = ca.catalog_id \n "+
           " AND c.catalog_type_cd = 'ACCOUNT' \n "+
           " AND c.catalog_id = con.catalog_id \n ";

		Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
		int freightTableId = 0;
        while (rs.next()) {
           freightTableId = rs.getInt(1);
        }
		rs.close();
		stmt.close();

        ContractData contractD = ContractData.createValue();
        contractD.setCatalogId(catalogId);
        contractD.setContractItemsOnlyInd(false);
        contractD.setContractStatusCd(catalogD.getCatalogStatusCd());
        contractD.setContractTypeCd("UNKNOWN");
        contractD.setEffDate(new Date());
        contractD.setFreightTableId(freightTableId);
        contractD.setHidePricingInd(false);
        contractD.setLocaleCd(RefCodeNames.LOCALE_CD.EN_US);
        contractD.setRankWeight(0);
        contractD.setShortDesc(catalogD.getShortDesc());
        contractD.setRefContractNum("0");
        contractD.setModBy(pUserName);
        contractD.setAddBy(pUserName);
        ContractDataAccess.insert(con, contractD);
    }

   public HashMap getUserIdCodeMap(int storeId,String custMaj, List userIdCodes) throws RemoteException {

        Connection conn = null;
        HashMap result = new HashMap();

        try {
            if (userIdCodes != null && !userIdCodes.isEmpty()) {

                conn = getConnection();
                String sql;
                int pack = 1000;

                for (int j = 0; j <= (userIdCodes.size() / pack); j++) {

                    IdVector ids = new IdVector();
                    for (int i = 0; i < (j == userIdCodes.size() / pack ? (userIdCodes.size() % pack) : pack); i++) {
                        ids.add(userIdCodes.get((j * pack) + i));
                    }

                    sql = "select u.user_id,up.clw_value from clw_user u ,clw_bus_entity_assoc beas,clw_user_assoc ua,clw_property up,\n" +
                            " (select bus_entity_id from clw_property where short_desc='" + RefCodeNames.PROPERTY_TYPE_CD.CUST_MAJ + "' and clw_value='" + custMaj + "') ap\n" +
                            " where up.user_id=u.user_id \n" +
                            " and up.short_desc='"+ RefCodeNames.PROPERTY_TYPE_CD.USER_ID_CODE +"' \n" +
                            " and up.clw_value in ("+ Utility.toCommaSting(ids,'\'') + ")\n" +
                            " and ua.user_id=u.user_id\n" +
                            " and ap. bus_entity_id = ua.bus_entity_id "+
                            " and   beas.BUS_ENTITY_ASSOC_CD ='"+ RefCodeNames.BUS_ENTITY_ASSOC_CD.ACCOUNT_STORE+"'\n" +
                            " and   beas.bus_entity1_id=ap.bus_entity_id\n" +
                            " and   beas.bus_entity2_id = "+storeId;

                    Statement stmt = conn.createStatement();

                    ResultSet rs = stmt.executeQuery(sql);
                    while (rs.next()) {
                        result.put(rs.getString(2), new Integer(rs.getInt(1)));
                    }

                    rs.close();
                    stmt.close();

                }
            }
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }

    public IdVector getUsersForGroupSiteRights( int siteId, IdVector groupIds, IdVector userIds ) throws RemoteException{

      if (groupIds == null || groupIds.size() == 0 || siteId == 0) {
        return null;
      }
      Connection conn = null;
      IdVector udV = null;
      try {
           conn = getConnection();
           DBCriteria dbc = new DBCriteria();
//           dbc.addOneOf(GroupAssocDataAccess.GROUP_ID, String.valueOf(groupId));
           dbc.addOneOf(GroupAssocDataAccess.GROUP_ID, groupIds);
           dbc.addEqualTo(GroupAssocDataAccess.GROUP_ASSOC_CD,RefCodeNames.GROUP_ASSOC_CD.USER_OF_GROUP);
           String userIdGroupReq = GroupAssocDataAccess.getSqlSelectIdOnly(GroupAssocDataAccess.USER_ID, dbc);

           dbc = new DBCriteria();
           dbc.addEqualTo(UserAssocDataAccess.USER_ASSOC_CD, RefCodeNames.USER_ASSOC_CD.SITE);
           dbc.addEqualTo(UserAssocDataAccess.BUS_ENTITY_ID, siteId);
           String userIdSiteReq = UserAssocDataAccess.getSqlSelectIdOnly(UserAssocDataAccess.USER_ID, dbc);

           dbc = new DBCriteria();
           dbc.addOneOf(UserDataAccess.USER_ID,userIdGroupReq);
           dbc.addOneOf(UserDataAccess.USER_ID,userIdSiteReq);
           dbc.addEqualTo(UserDataAccess.USER_STATUS_CD,RefCodeNames.USER_STATUS_CD.ACTIVE);
           if (userIds != null && userIds.size() > 0){
             dbc.addOneOf(UserDataAccess.USER_ID,userIds);
           }

           udV = UserDataAccess.selectIdOnly(conn, UserDataAccess.USER_ID,dbc);
       }
       catch (Exception e) {
          String msg = "[UserBean] getUsersForGroupSiteRights: " + e.getMessage();
          logError(msg);
          throw new RemoteException(msg);
      } finally {
          try {if (conn != null) conn.close();} catch (Exception ex) {}
      }
      return udV;
    }

    public IdVector getSuperRightUserCollection(UserData userD, int pStoreId) throws RemoteException{
      IdVector ids = null;
      Connection conn = null;
      try {
        conn = getConnection();
        if (RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR.equals(userD.getUserTypeCd())) {
          ids = new IdVector();
          String sql =
              "SELECT user_id, COUNT (BUS_ENTITY_ID) store_count " +
              "FROM CLW_USER_ASSOC ua " +
              " WHERE USER_ASSOC_CD = '" + RefCodeNames.USER_ASSOC_CD.STORE + "'" +
              " AND user_id IN " +
              "      (SELECT ua.user_id " +
              "         FROM CLW_USER_ASSOC ua " +
              "         JOIN CLW_USER u ON UA.USER_ID = U.USER_ID " +
              "         WHERE bus_entity_id = " + pStoreId +
              "           AND USER_ASSOC_CD = '" + RefCodeNames.USER_ASSOC_CD.STORE + "'" +
              "           AND U.USER_TYPE_CD IN ('" + userD.getUserTypeCd() + "') " +
              "      ) "+
              " GROUP BY user_id ";
          log.info("[UserBean].getSuperRightUserCollection() -------> SQL :" + sql);
          Statement stmt = conn.createStatement();
          ResultSet rs = stmt.executeQuery(sql);
          HashMap result = new HashMap();
          int appUserStoreCount = 0;
          while (rs.next()) {
            int userId = rs.getInt(1);
            int storeCount = rs.getInt(2);
            result.put(new Integer(userId), new Integer(storeCount));
            if (userId == userD.getUserId() ){
              appUserStoreCount = storeCount;
            }
          }
          Set keySet = result.keySet();
          for (Iterator iter = keySet.iterator(); iter.hasNext(); ) {
             Integer uid = (Integer)iter.next();
             Integer count = (Integer)result.get(uid);
             if (count > appUserStoreCount){
               ids.add(uid);
             }
          }

        }
      }
      catch (Exception e) {
         e.printStackTrace();
         throw new RemoteException(e.getMessage());
      } finally {
        try {if (conn != null) conn.close();} catch (Exception ex) {}
      }

      return ids;
    }

    public IdVector getAccountIdsForUserSitesAssoc(int pStoreId, int pUserId) throws RemoteException {
        IdVector res = new IdVector();
        Connection conn = null;
        try {
            conn = getConnection();
            String sql = 
                "SELECT DISTINCT " +
                    "bea1.BUS_ENTITY2_ID " +
                "FROM " +
                    "CLW_USER_ASSOC ua, " +
                    "CLW_BUS_ENTITY_ASSOC bea1, " +
                    "CLW_BUS_ENTITY_ASSOC bea2 " +
                "WHERE " +
                    "bea2.BUS_ENTITY_ASSOC_CD='" + RefCodeNames.BUS_ENTITY_ASSOC_CD.ACCOUNT_STORE + "' " +
                    "AND bea2.BUS_ENTITY2_ID=" + pStoreId + " " +
                    "AND bea2.BUS_ENTITY1_ID=bea1.BUS_ENTITY2_ID " +
                    "AND bea1.BUS_ENTITY_ASSOC_CD='" + RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT + "' " +
                    "AND bea1.BUS_ENTITY1_ID=ua.BUS_ENTITY_ID " +
                    "AND ua.USER_ASSOC_CD='" + RefCodeNames.USER_ASSOC_CD.SITE + "' " +
                    "AND ua.USER_ID=" + pUserId;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                res.add(new Integer(rs.getInt(1)));
            }
            rs.close();
            stmt.close();
            logInfo("[getAccountIdsForUserSitesAssoc] sql: " + sql + ", size: " + res.size());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }
        return res;
    }

    public IdVector getAccountIdsForUserAccountsAssoc(int pStoreId, int pUserId) throws RemoteException {
        IdVector res = new IdVector();
        Connection conn = null;
        try {
            conn = getConnection();
            String sql = 
                "SELECT DISTINCT " +
                    "bea.BUS_ENTITY1_ID " +
                "FROM " +
                    "CLW_USER_ASSOC ua, " +
                    "CLW_BUS_ENTITY_ASSOC bea " +
                "WHERE " +
                    "bea.BUS_ENTITY_ASSOC_CD='" + RefCodeNames.BUS_ENTITY_ASSOC_CD.ACCOUNT_STORE + "' " +
                    "AND bea.BUS_ENTITY2_ID=" + pStoreId + " " +
                    "AND bea.BUS_ENTITY1_ID=ua.BUS_ENTITY_ID " +
                    "AND ua.USER_ASSOC_CD='" + RefCodeNames.USER_ASSOC_CD.ACCOUNT + "' " +
                    "AND ua.USER_ID=" + pUserId;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                res.add(new Integer(rs.getInt(1)));
            }
            rs.close();
            stmt.close();
            logInfo("[getAccountIdsForUserAccountsAssoc] sql: " + sql + ", size: " + res.size());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }
        return res;
    }
    /**
     *  Get list of user ids with user name length excced maxSize for a given store id.
     *
     *@param  pStoreId         the Store Id of the user associate. 
     *@param  maxSize          the max size user name should be. 
     *@return                  list of user ids associated with the store id and user name size excced maxSize.
     *@exception  RemoteException  if an error occurs
     */
    public IdVector getUserIdsWithNameExccedMaxSize(int pStoreId, int maxSize) throws RemoteException {       

        Connection conn = null;
        IdVector returnUserIds = new IdVector();
        try {
            conn = getConnection();
            String sql = "SELECT B.USER_ID " +
    		"FROM CLW_USER_ASSOC A, CLW_USER B " +
    		"WHERE A.USER_ID = B.USER_ID " +
    		"AND A.BUS_ENTITY_ID = " + pStoreId + " " +
    		"AND LENGTH(USER_NAME) > " + maxSize + " " +
    		"AND USER_STATUS_CD != '" + RefCodeNames.USER_STATUS_CD.INACTIVE + "' " +
    		"AND USER_TYPE_CD in ('" + RefCodeNames.USER_TYPE_CD.MSB + "', '" 
    							+ RefCodeNames.USER_TYPE_CD.CUSTOMER + "', '" 
    							+ RefCodeNames.USER_TYPE_CD.DISTRIBUTOR + "') ";
            Statement stmt = conn.createStatement();            
            ResultSet rs=stmt.executeQuery(sql);
            while (rs.next()){
            	returnUserIds.add(rs.getInt(1));
            }
            return returnUserIds;
        }
        catch (Exception e) {
            throw new RemoteException("getAllUsers: " + e.getMessage());
        } finally {
            try {if (conn != null) conn.close();} catch (Exception ex) {}
        }
    }

   /*
   public UserDefaultShopInfoView getUserDefaultShopInfo(int pUserId)
   throws RemoteException, EjbException {

        Connection conn = null;
        try {
            conn = getConnection();
            UserDefaultShopInfoView defShopInfo = null;

            String sql =
                "SELECT be.bus_entity_id FROM clw_user_assoc ua "+
                "  join clw_bus_entity be ON ua.bus_entity_id = be.bus_entity_id "+
                "  WHERE ua.user_id = ? "+
                "  AND user_assoc_cd = 'STORE' "+
                "  AND be.bus_entity_status_cd = 'ACTIVE' ";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1,pUserId);
            ResultSet rs=stmt.executeQuery();
            IdVector storeIdV = new IdVector();
            StringBuffer storeIdParams = new StringBuffer();

            while(rs.next()){
                int id = rs.getInt("bus_entity_id");
                storeIdV.add(new Integer(id));
                if(storeIdParams.length()==0) {
                    storeIdParams.append('?');
                } else {
                    storeIdParams.append(",?");
                }
            }
            stmt.close();
            rs.close();

            if(storeIdV.isEmpty()) {
                return defShopInfo;
            }
            sql =
                "SELECT ua.USER_ID, " +
                "  sbe.BUS_ENTITY_ID site_id, sbe.short_desc, abe.BUS_ENTITY_ID account_id, " +
                "  asta.BUS_ENTITY2_ID store_id " +
                "  FROM clw_user_assoc ua " +
                "  join clw_bus_entity sbe " +
                "    ON ua.bus_entity_id = sbe.bus_entity_id " +
                "    AND sbe.bus_entity_type_cd = '"+RefCodeNames.BUS_ENTITY_TYPE_CD.SITE+"' " +
                "    AND sbe.bus_entity_status_cd = '"+RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE+"' " +
                "    AND (sbe.eff_date IS NULL or sbe.eff_date <= SYSDATE) " +
                "    AND (sbe.exp_date IS NULL or sbe.exp_date > SYSDATE) " +
                "  join clw_bus_entity_assoc saa ON sbe.bus_entity_id = saa.bus_entity1_id " +
                "  join clw_bus_entity abe " +
                "    ON saa.bus_entity2_id = abe.bus_entity_id " +
                "    AND abe.bus_entity_type_cd = '"+RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT+"' " +
                "    AND abe.bus_entity_status_cd = '"+RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE+"' " +
                "    AND (abe.eff_date IS NULL or abe.eff_date <= SYSDATE) " +
                "    AND (abe.exp_date IS NULL or abe.exp_date > SYSDATE) " +
                "  join clw_bus_entity_assoc asta " +
                "    ON abe.bus_entity_id = asta.bus_entity1_id " +
                "    AND asta.bus_entity2_id IN  (" + storeIdParams.toString()+") " +
                "  WHERE ua.user_id = ? " +
                "  AND ROWNUM <= 2 ";

            stmt = conn.prepareStatement(sql);
            int indx = 1;
            for(Iterator iter = storeIdV.iterator();iter.hasNext();) {
                Integer storeIdI = (Integer) iter.next();
                stmt.setInt(indx++,storeIdI.intValue());
            }
            stmt.setInt(indx,pUserId);
            rs=stmt.executeQuery();
            while(rs.next()){
                if(defShopInfo==null) {
                    defShopInfo = new UserDefaultShopInfoView();
                    defShopInfo.setMultipleSiteFl(false);
                    int siteId = rs.getInt("site_id");
                    int accountId = rs.getInt("account_id");
                    int storeId = rs.getInt("store_id");
                    String siteName = rs.getString("short_desc");
                    defShopInfo.setSiteId(siteId);
                    defShopInfo.setAccountId(accountId);
                    defShopInfo.setSiteId(storeId);
                    defShopInfo.setSiteName(siteName);
                } else {
                    defShopInfo.setMultipleSiteFl(true);
                    break;
                }
            }
            stmt.close();
            rs.close();
            if(defShopInfo==null) {
                return defShopInfo;
            }

            sql =
                "SELECT ca.catalog_id, cat.catalog_status_cd, cat.catalog_type_cd "+
                "  FROM clw_catalog_assoc ca "+
                "  join clw_catalog cat ON ca.catalog_id = cat.catalog_id " +
                "    AND catalog_type_cd = '"+RefCodeNames.CATALOG_TYPE_CD.SHOPPING+"' "+
                "  WHERE ca.bus_entity_id = ?";
            stmt.setInt(1,defShopInfo.getSiteId());
            rs=stmt.executeQuery();
            while(rs.next()){
               int catalogId = rs.getInt("catalog_id");
               String catalogStatusCd = rs.getString("catalog_status_cd");
               String catalogTypeCd = rs.getString("catalog_type_cd");
               defShopInfo.setCatalogId(catalogId);
               defShopInfo.setCatalogStatusCd(catalogStatusCd);
               defShopInfo.setCatalogTypeCd(catalogTypeCd);
               if(RefCodeNames.CATALOG_STATUS_CD.ACTIVE.equals(catalogStatusCd)){
                    break;
               }
            }
            stmt.close();
            rs.close();
            if(defShopInfo==null) {
                return defShopInfo;
            }


            return defShopInfo;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }
	*/
    public UserData login(LoginInfoView pLoginInfo) throws RemoteException, InvalidLoginException {
        return login(pLoginInfo, true);
    }
    
    public UserData login(LoginInfoView pLoginInfo, boolean pCheckPwd) throws RemoteException, InvalidLoginException {
    	log.info("login()=> " + pLoginInfo.getUserName() + ", " + pLoginInfo.getPassword());
    	
    	Connection conn = null;
    	DBCriteria dbCrit;
    	UserData lu = null;
    	try {
    		conn = getConnection();
    		dbCrit = new DBCriteria();
            dbCrit.addEqualTo(UserDataAccess.USER_NAME, pLoginInfo.getUserName());
            UserDataVector sqlRes = UserDataAccess.select(conn, dbCrit);
            if (sqlRes.size() > 1) {
                log.error("login()=> Multiple users found: '" + pLoginInfo.getUserName() + "'");
                //throw new Exception("Multiple users found: '" + pLoginInfo.getUserName() + "'");
            }
            if (sqlRes.size() == 0) {
                throw new Exception("login error: Username not found.");
            }
            if (sqlRes.size() == 1) {
                lu = (UserData) sqlRes.get(0);
            }
            if (sqlRes.size() > 1) {
                log.error("login()=> Multiple users found: '" + pLoginInfo.getUserName() + "'");
            }
            if (pCheckPwd) {

                if (lu.getPassword() == null || lu.getPassword().length() == 0) {
                    return lu;
                }

                String passwordHash = PasswordUtil.getHash(pLoginInfo.getUserName(), pLoginInfo.getPassword());
                if (!passwordHash.equals(lu.getPassword())) {
                    log.info("login()=> password " + passwordHash);
                    throw new InvalidLoginException("login error: Password does not match.");
                }
            }
            
            return lu;
            
        } catch (InvalidLoginException e) {
            throw e;
        } catch (Exception e) {
            throw new RemoteException(e.getMessage());
        }               
    }
    
    public UserData login(LoginInfoView pLoginInfo, boolean pCheckPwd, boolean pEncrypted) throws RemoteException, InvalidLoginException {
    	log.info("login()=> " + pLoginInfo.getUserName() + ", " + pLoginInfo.getPassword());
    	
    	Connection conn = null;
    	DBCriteria dbCrit;
    	UserData lu = null;
    	try {
    		conn = getConnection();
    		dbCrit = new DBCriteria();
            dbCrit.addEqualTo(UserDataAccess.USER_NAME, pLoginInfo.getUserName());
            UserDataVector sqlRes = UserDataAccess.select(conn, dbCrit);
            if (sqlRes.size() > 1) {
                log.error("login()=> Multiple users found: '" + pLoginInfo.getUserName() + "'");
                //throw new Exception("Multiple users found: '" + pLoginInfo.getUserName() + "'");
            }
            if (sqlRes.size() == 0) {
                throw new Exception("login error: Username not found.");
            }
            if (sqlRes.size() == 1) {
                lu = (UserData) sqlRes.get(0);
            }
            if (sqlRes.size() > 1) {
                log.error("login()=> Multiple users found: '" + pLoginInfo.getUserName() + "'");
            }
            if (pCheckPwd) {

                if (lu.getPassword() == null || lu.getPassword().length() == 0) {
                    return lu;
                }

                String passwordHash = pEncrypted ? pLoginInfo.getPassword() : PasswordUtil.getHash(pLoginInfo.getUserName(), pLoginInfo.getPassword());

                if (!passwordHash.equals(lu.getPassword())) {
                    log.info("login()=> password " + passwordHash);
                    throw new InvalidLoginException("login error: Password does not match.");
                }
            }
            
            return lu;
            
        } catch (InvalidLoginException e) {
            throw e;
        } catch (Exception e) {
            throw new RemoteException(e.getMessage());
        }               
    }
    
    public void removeBusEntityAssociationsMultiStoreDb(int pUserId, AllStoreDataVector pAllStoreDataVector) throws RemoteException {
    	log.info("removeBusEntityAssociationsMultiStoreDb().Begin");
    	Connection conn = null;
    	DBCriteria crit;
    	AllStoreData allStoreData = null;
    	String datasource = null;
    	log.info("pUserId = " + pUserId);
    	log.info("pAllStoreDataVector.size() = " + pAllStoreDataVector.size());
    	log.info("pAllStoreDataVector = " + pAllStoreDataVector);
        for (int i = 0; i < pAllStoreDataVector.size(); i++) {        	
        	datasource = ((AllStoreData) pAllStoreDataVector.get(i)).getDatasource();
        	log.info("datasource = " + datasource);
        	if(datasource != null && datasource.length() != 0) {
    	       try {
    		       conn = getConnection(datasource);
    		       crit = new DBCriteria();
                   crit.addEqualTo(UserAssocDataAccess.USER_ID, pUserId);
                   int storeId = ((AllStoreData) pAllStoreDataVector.get(i)).getStoreId();
                   log.info("storeId = " + storeId);
                   crit.addEqualTo(UserAssocDataAccess.BUS_ENTITY_ID, storeId);
                   UserAssocDataAccess.remove(conn,crit);    		
               } catch (Exception e) {
                   e.printStackTrace();
                   throw new RemoteException(e.getMessage());
               } finally {
                   closeConnection(conn);
               } 
            }
        } 
        log.info("removeBusEntityAssociationsMultiStoreDb().End");
    }
    
    public void addBusEntityAssociationsMultiStoreDb(int pUserId, String userAssocCode, String pUserName, AllStoreDataVector pAllStoreDataVector) throws RemoteException {
    	log.info("addBusEntityAssociationsMultiStoreDb().Begin");
    	
    	log.info("pUserId = " + pUserId);
    	log.info("pAllStoreDataVector.size() = " + pAllStoreDataVector.size());
    	log.info("pAllStoreDataVector = " + pAllStoreDataVector);
    	log.info("userAssocCode = " + userAssocCode);
    	Connection conn = null;
    	DBCriteria crit;
    	String datasource = null;
    	UserAssocDataVector uadv;
    	for (int i = 0; i < pAllStoreDataVector.size(); i++) {        	
        	datasource = ((AllStoreData) pAllStoreDataVector.get(i)).getDatasource();
        	log.info("i = " + i + " datasource = " + datasource);
        	if(datasource != null && datasource.length() != 0) {
               try {
    		       conn = getConnection(datasource); // connect to a SPECIFIC Single Store DB Schema
    		       crit = new DBCriteria();
    		       crit.addEqualTo(UserAssocDataAccess.USER_ID, pUserId);
    		       int storeId = ((AllStoreData) pAllStoreDataVector.get(i)).getStoreId();
                   log.info("i = " + i + " storeId = " + storeId);
                   crit.addEqualTo(UserAssocDataAccess.BUS_ENTITY_ID, storeId);
                   uadv = UserAssocDataAccess.select(conn, crit);
                   log.info("i = " + i + " uadv = " + uadv);                   
                   if (uadv.size() == 0) {
                	   log.info("Inserting record into clw_user_assoc DB table...");
                	   UserAssocData uad = UserAssocData.createValue();
                       uad.setUserId(pUserId);
                       uad.setAddBy(pUserName);
                       uad.setModBy(pUserName);
                       uad.setBusEntityId(storeId);
                       uad.setUserAssocCd(userAssocCode);
                       UserAssocDataAccess.insert(conn, uad);            
                   }
               } catch (Exception e) {
                   e.printStackTrace();
                   throw new RemoteException(e.getMessage());
               } finally {
                   closeConnection(conn);
               } 
            } else {
            	String storeName =  ((AllStoreData) pAllStoreDataVector.get(i)).getStoreName();
            	log.info("Datasource for the Store " + storeName + " is not found in the Main DB. Corresponding Single Store DB Schema was not updated");
    	    	throw new RemoteException("Datasource for the Store is not found in the Main DB");
            }
        } //for      	        	        	    
        log.info("addBusEntityAssociationsMultiStoreDb().End");
    }
    
    public UserData getUserByNameStoreDbSchema(String pUserName, String pDatasource) throws RemoteException {
    	
    	log.info("getUserByNameStoreDbSchema().Begin");
    	
    	log.info("pUserName = " + pUserName);
    	log.info("pDatasource = " + pDatasource);
    	
    	Connection conn = null;
    	DBCriteria crit;
    	UserDataVector udv = null;
    	UserData ud = null;
        try {
		       conn = getConnection(pDatasource); // connect to a SPECIFIC Single Store DB Schema
		       crit = new DBCriteria();
		       crit.addEqualTo(UserDataAccess.USER_NAME, pUserName);
		       udv = UserDataAccess.select(conn, crit);
		       if (udv == null || udv.size() == 0) {
            	   log.info("No user with the name = " + pUserName);
            	   //throw new RemoteException("No user with this name");     
               } else {
		           ud = (UserData) udv.get(0);
               }
        } catch (Exception e) {
               e.printStackTrace();
               throw new RemoteException(e.getMessage());
        } finally {
               closeConnection(conn);
        } 
    	
        log.info("getUserByNameStoreDbSchema().End");
        
        return ud;
    }
    
    public UserData getUserByUserIdFromStoreDbSchema(int pUserId, String pDatasource) throws RemoteException {
    	
    	log.info("getUserByUserIdFromStoreDbSchema().Begin");
    	
    	log.info("pUserId = " + pUserId);
    	log.info("pDatasource = " + pDatasource);
    	
    	Connection conn = null;
    	DBCriteria crit;
    	UserDataVector udv = null;
    	UserData ud = null;
        try {
		       conn = getConnection(pDatasource); // connect to a SPECIFIC Single Store DB Schema
		       crit = new DBCriteria();
		       crit.addEqualTo(UserDataAccess.USER_ID, pUserId);
		       udv = UserDataAccess.select(conn, crit);
		       if (udv == null || udv.size() == 0) {
            	   log.info("No user with the userId = " + pUserId);
            	   //throw new RemoteException("No user with this name");     
               } else {
		           ud = (UserData) udv.get(0);
               }
        } catch (Exception e) {
               e.printStackTrace();
               throw new RemoteException(e.getMessage());
        } finally {
               closeConnection(conn);
        } 
    	
        log.info("getUserByUserIdFromStoreDbSchema().End");
        
        return ud;
    }
    
    public void addBusEntityAssociationsSingleStoreDb(int pUserId, String userAssocCode, String pUserName, String pDatasource, int pStoreId) throws RemoteException {
        log.info("addBusEntityAssociationsSingleStoreDb().Begin");
    	
    	log.info("pUserId = " + pUserId);
    	log.info("pDatasource = " + pDatasource);    	
    	log.info("userAssocCode = " + userAssocCode);
    	Connection conn = null;
    	DBCriteria crit;
    	UserAssocDataVector uadv;
    	try {
		       conn = getConnection(pDatasource); // connect to a SPECIFIC Single Store DB Schema
		       crit = new DBCriteria();
		       crit.addEqualTo(UserAssocDataAccess.USER_ID, pUserId);
		       crit.addEqualTo(UserAssocDataAccess.BUS_ENTITY_ID, pStoreId);
               uadv = UserAssocDataAccess.select(conn, crit);
               log.info("uadv = " + uadv);                   
               if (uadv.size() == 0) {
            	   log.info("Inserting record into clw_user_assoc DB table...");
            	   UserAssocData uad = UserAssocData.createValue();
                   uad.setUserId(pUserId);
                   uad.setAddBy(pUserName);
                   uad.setModBy(pUserName);
                   uad.setBusEntityId(pStoreId);
                   uad.setUserAssocCd(userAssocCode);
                   UserAssocDataAccess.insert(conn, uad);            
               }
        } catch (Exception e) {
               e.printStackTrace();
               throw new RemoteException(e.getMessage());
        } finally {
               closeConnection(conn);
        }
        
        log.info("addBusEntityAssociationsSingleStoreDb().End");
    }
        
	/**
     * return number of days that password will expire for notification. 
     * user type SYSTEM_ADMINISTRATOR and ADMINISTRATOR should be exclued
     * user type STORE_ADMINISTRATOR could have multiple store associated and need to check each stores
     * user type MSB will need to check store profile property CHANGE_PASSWORD be true
     * and all associated account property RESET_PASSWORD_WITHIN_DAYS need to be true
     * store property RESET_PASSWORD_WITHIN_DAYS and NOTIFY_PASSWORD_EXPIRY_IN_DAYS both need to be set     * 
     * if password histroy record exists for the user and use the last changed date to calculate.
     * passwordExpiryInDays=(lastChangedDate+resetPasswordInDays)-today
     * only return passwordExpiryInDays if passwordExpiryInDays <= notifyResetInDays
     * 
     */
    public long getPasswordExpiryInDays(int userId, String userTypeCd) throws RemoteException{
    	long expiryInDays = 0;
    	if (userTypeCd.equals(RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR) ||
    			userTypeCd.equals(RefCodeNames.USER_TYPE_CD.ADMINISTRATOR))
			return expiryInDays;
    	
    	Connection con = null;
    	try {
            con = getConnection();
            // get user store ids
        	IdVector storeIds = getUserAssocCollecton(con, Utility.toIdVector(userId), RefCodeNames.USER_ASSOC_CD.STORE, RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);
        	
        	int resetPasswordInDays = 0;
        	int notifyResetInDays = 0;
        	PropertyService propService = APIAccess.getAPIAccess().getPropertyServiceAPI();
        	for (int i = 0; i < storeIds.size(); i++){
        		int storeId = (Integer)storeIds.get(i);        		
        		if (!allowUserChangePassword(con, userId, userTypeCd, storeId))
        			continue;
        		
        		PropertyDataVector pdv1 = propService.getProperties(Utility.toIdVector(storeId), RefCodeNames.PROPERTY_TYPE_CD.RESET_PASSWORD_WITHIN_DAYS);
        		if (pdv1.size() > 0){
        			PropertyData pd = (PropertyData) pdv1.get(0);
        			if (Utility.isSet(pd.getValue())){
        				resetPasswordInDays = Integer.parseInt(pd.getValue());
        				if (resetPasswordInDays > 0){
        					PropertyDataVector pdv2 = propService.getProperties(Utility.toIdVector((Integer)storeIds.get(i)), RefCodeNames.PROPERTY_TYPE_CD.NOTIFY_PASSWORD_EXPIRY_IN_DAYS);
        					pd = (PropertyData) pdv2.get(0);
        					if (Utility.isSet(pd.getValue())){
	        					notifyResetInDays = Integer.parseInt(pd.getValue());
	        					if (notifyResetInDays > 0){
	        						if (resetPasswordInDays > 0 && notifyResetInDays > 0){
	        			        		// get last password history record. 
	        							PasswordHistoryData phD = getMostRecentPasswordHistoryData(con, userId);
	        			        		if (phD != null){
	        			        			Date lastModDate = phD.getAddDate();
	        			        			log.info("Password last changed date: " + lastModDate);
	        			        			GregorianCalendar cal = new GregorianCalendar();
	        			        			cal.setTime(lastModDate);
	        			        			cal.add(Calendar.DAY_OF_YEAR, resetPasswordInDays);
	        			        			log.info("Password expire on date: " + cal.getTime());
	        			        			
	        			        			GregorianCalendar cal2 = new GregorianCalendar();
	        			        			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	        			        	        cal2.setTime(sdf.parse(sdf.format(new Date())));
	        			        	        log.info("Today: " + cal2.getTime());        	        
	        			        	        
	        			        	        
	        			        	        // Find date difference in milliseconds
	        			        	        long diffInMSec = cal.getTimeInMillis() - cal2.getTimeInMillis();
	        			        	        // Find date difference in days 
	        			        	        // (24 hours 60 minutes 60 seconds 1000 millisecond)
	        			        	        long diffOfDays = diffInMSec / (24 * 60 * 60 * 1000);
	
	        			        	        if (diffOfDays > 0 && diffOfDays <= notifyResetInDays){
	        			        	        	expiryInDays = diffOfDays;
	        			        	        	log.info("Password will expire in " + diffOfDays + " days");
	        			        	        	break;
	        			        	        }
	        			        		}
	        			        	}
	        					}
        					}
        				}
        			}        			
        		}
        	}        	
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(con);
        }
    	
    	return expiryInDays;
    }
    
    private UserData updateUserAndPasswordHistory(Connection con, UserData userD, boolean isNewUser) throws Exception {
    	return updateUserAndPasswordHistory(con, userD, isNewUser, false);
    }
    
    private UserData updateUserAndPasswordHistory(Connection con, UserData userD, boolean isNewUser, boolean isResetPassword) throws Exception {
    	if (isNewUser){
    		UserDataAccess.insert(con, userD);
    	}else{
    		UserDataAccess.update(con, userD);
    	}
    	createPasswordHistoryIfChanged(con, userD, isNewUser, isResetPassword);
    	return userD;
    }
    
    // If password changed (current password is different from most recent password from history table)
    // will add a new password history record. Column NEED_INITIAL_RESET flag is set based on
    // user's type and store property RESET_PASSWORD_UPON_INIT_LOGIN, store profile property CHANGE_PASSWORD and 
    // account property CHANGE_PASSWORD. If isResetPassword is true, NEED_INITIAL_RESET should be false to prevent reset again
    void createPasswordHistoryIfChanged(Connection con, UserData userD, boolean isNewUser, boolean isResetPassword) throws Exception {
    	int userId = userD.getUserId();
    	if (!Utility.isSet(userD.getPassword()))
    		return;
    	String userTypeCd = userD.getUserTypeCd();
    	if (!isNewUser){
	    	// check if password changed. If password history exists and last password is same as current, do noting
	    	PasswordHistoryData phD = getMostRecentPasswordHistoryData(con, userId);
	    	
	    	if (phD != null){
	    		String password = phD.getPassword();
	    		if (password.equals(userD.getPassword())){
	    			return;
	    		}
	    	}
    	}
    	
    	
    	// get NEED_INITIAL_RESET flag for new password.
    	boolean resetOponInitLogin = false;
    	if (!isResetPassword){
	    	if (userTypeCd.equals(RefCodeNames.USER_TYPE_CD.MSB) || userTypeCd.equals(RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR)){
		    	// get user store ids
		    	IdVector storeIds = getUserAssocCollecton(con, Utility.toIdVector(userId), RefCodeNames.USER_ASSOC_CD.STORE, RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);
		    	PropertyService propService = APIAccess.getAPIAccess().getPropertyServiceAPI();
		    	for (int i = 0; i < storeIds.size(); i++){
		    		int storeId = (Integer)storeIds.get(i);
		    		if (!allowUserChangePassword(con, userId, userTypeCd, storeId))
		    			continue;	    		
		    		
	    			PropertyDataVector pdv1 = propService.getProperties(Utility.toIdVector(storeId), RefCodeNames.PROPERTY_TYPE_CD.RESET_PASSWORD_UPON_INIT_LOGIN);
		    		if (pdv1.size() > 0){
		    			PropertyData pd = (PropertyData) pdv1.get(0);
		    			String propertyValue = pd.getValue();
			    		if (Utility.isTrue(propertyValue)) {
			    			resetOponInitLogin = true;
			    			break;
			    		}
			    	}
	    		}
		    }
    	}
    	    	
    	// password history
    	PasswordHistoryData phD = PasswordHistoryData.createValue();
    	phD.setUserId(userId);
    	phD.setPassword(userD.getPassword());
    	if (resetOponInitLogin)
    		phD.setNeedInitialReset("true");
    	else
    		phD.setNeedInitialReset("false");
    	phD.setAddBy(userD.getModBy());
    	phD.setModBy(userD.getModBy());
    	PasswordHistoryDataAccess.insert(con, phD);
    }
    
    /**
     * return false if user is a MSB and store profile property CHANGE_PASSWORD is false or one of the 
     * account property ALLOW_USER_CHANGE_PASSWORD is not true
     */
    private boolean allowUserChangePassword(Connection con, int userId, String userTypeCd, int storeId) throws Exception {
    	boolean allowChangePassword = true;
    	if (userTypeCd.equals(RefCodeNames.USER_TYPE_CD.MSB)){
			// get user store profile property CHANGE_PASSWORD and CHANGE_PASSWORD value need to be true
			DBCriteria crit = new DBCriteria();
			crit.addEqualTo(StoreProfileDataAccess.STORE_ID, storeId);
			crit.addEqualTo(StoreProfileDataAccess.SHORT_DESC, RefCodeNames.STORE_PROFILE_FIELD.CHANGE_PASSWORD);
			StoreProfileDataVector spDV = StoreProfileDataAccess.select(con, crit);
			if (spDV.size() > 0){
				StoreProfileData spD = (StoreProfileData) spDV.get(0);
				if (!Utility.isTrue(spD.getDisplay()))
					return false;
			}
			
			// get user account property ALLOW_USER_CHANGE_PASSWORD, all the values must be true
			String sql = "select distinct clw_value from clw_property ap, clw_user_assoc ua, clw_bus_entity a" +
					" where ap.bus_entity_id = ua.bus_entity_id" +
					" and ua.user_id=?" +
					" and ua.bus_entity_id = a.bus_entity_id" +
					" and a.bus_entity_type_cd = ?" +
					" and a.bus_entity_status_cd = ?" +
					" and ap.short_desc = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
    		pstmt.setInt(1, userId);
    		pstmt.setString(2, RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT);
    		pstmt.setString(3, RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);
    		pstmt.setString(4, RefCodeNames.PROPERTY_TYPE_CD.ALLOW_USER_CHANGE_PASSWORD);
    		ResultSet rs = pstmt.executeQuery();
    		boolean changePassword = false;
    		while (rs.next()){
    			String value = rs.getString(1);
    			if (Utility.isTrue(value)){
    				changePassword = true;
    			}else{
    				changePassword = false;
    				break;
    			}
    		}
    		if (!changePassword)
    			return false;
		}else{
			return true;
		}
    	return allowChangePassword;
    }
    
    public boolean isPasswordNeedReset(UserData userD) throws RemoteException {
    	int userId = userD.getUserId();
    	String userTypeCd = userD.getUserTypeCd();
    	
    	if (userTypeCd.equals(RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR) || userTypeCd.equals(RefCodeNames.USER_TYPE_CD.ADMINISTRATOR)){
    		return false;
    	}
    	
    	Connection con = null;
    	try {
            con = getConnection();
    	
	    	PasswordHistoryData phD = getMostRecentPasswordHistoryData(con, userId);
	    	if (phD != null){
	    		String needInitReset = phD.getNeedInitialReset();
	    		if (Utility.isTrue(needInitReset)){
	    			// get user store ids
	    	    	IdVector storeIds = getUserAssocCollecton(con, Utility.toIdVector(userId), RefCodeNames.USER_ASSOC_CD.STORE, RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);
	    	    	PropertyService propService = APIAccess.getAPIAccess().getPropertyServiceAPI();
	    	    	for (int i = 0; i < storeIds.size(); i++){
	    	    		int storeId = (Integer)storeIds.get(i);
	    	    		if (!allowUserChangePassword(con, userId, userTypeCd, storeId))
	    	    			continue;	    		
	    	    		
	        			PropertyDataVector pdv1 = propService.getProperties(Utility.toIdVector(storeId), RefCodeNames.PROPERTY_TYPE_CD.RESET_PASSWORD_UPON_INIT_LOGIN);
	    	    		if (pdv1.size() > 0){
	    	    			PropertyData pd = (PropertyData) pdv1.get(0);
	    	    			String propertyValue = pd.getValue();
	    		    		if (Utility.isTrue(propertyValue)) {
	    		    			return true;
	    		    		}
	    		    	}
	        		}
	    		}
	    	}
	    	
    	} catch (Exception e) {
    		String msg = "isPasswordNeedReset: " + e.getMessage();
            logError(msg);
            throw new RemoteException(msg);
        } finally {
            closeConnection(con);
        }
    	
    	return false;
    }


	public boolean isPasswordExpired(UserData userD) throws RemoteException {
    	int userId = userD.getUserId();
    	String userTypeCd = userD.getUserTypeCd();
    	if (userTypeCd.equals(RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR) || userTypeCd.equals(RefCodeNames.USER_TYPE_CD.ADMINISTRATOR)){
    		return false;
    	}
    	Connection con = null;
    	try {
            con = getConnection();    	
	    	
			// get user store ids
	    	IdVector storeIds = getUserAssocCollecton(con, Utility.toIdVector(userId), RefCodeNames.USER_ASSOC_CD.STORE, RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);
	    	PropertyService propService = APIAccess.getAPIAccess().getPropertyServiceAPI();
	    	for (int i = 0; i < storeIds.size(); i++){
	    		int storeId = (Integer)storeIds.get(i);
	    		if (!allowUserChangePassword(con, userId, userTypeCd, storeId))
	    			continue;	    		
	    		
	    		PropertyDataVector pdv1 = propService.getProperties(Utility.toIdVector(storeId), RefCodeNames.PROPERTY_TYPE_CD.RESET_PASSWORD_WITHIN_DAYS);
        		if (pdv1.size() > 0){
        			PropertyData pd = (PropertyData) pdv1.get(0);
        			if (Utility.isSet(pd.getValue())){
        				int resetPasswordInDays = Integer.parseInt(pd.getValue());
        				if (resetPasswordInDays > 0){
        					// get last password history record. 
							PasswordHistoryData phD = getMostRecentPasswordHistoryData(con, userId);
			        		if (phD != null){
			        			Date lastModDate = phD.getAddDate();
			        			log.info("Password last changed date: " + lastModDate);
			        			GregorianCalendar cal = new GregorianCalendar();
			        			cal.setTime(lastModDate);
			        			cal.add(Calendar.DAY_OF_YEAR, resetPasswordInDays);
			        			log.info("Password expire on date: " + cal.getTime());
			        			
			        			GregorianCalendar cal2 = new GregorianCalendar();
			        			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			        	        cal2.setTime(sdf.parse(sdf.format(new Date())));
			        	        log.info("Today: " + cal2.getTime());
			        	        
			        	        // Find date difference in milliseconds
			        	        long diffInMSec = cal.getTimeInMillis() - cal2.getTimeInMillis();
			        	        // Find date difference in days 
			        	        // (24 hours 60 minutes 60 seconds 1000 millisecond)
			        	        long diffOfDays = diffInMSec / (24 * 60 * 60 * 1000);

			        	        if (diffOfDays <= 0){			        	        	
			        	        	log.info("Password expired");
			        	        	return true;
			        	        }
			        		}
        				}
	        		}
	    		}
	    	}	    	
    	} catch (Exception e) {
    		String msg = "isPasswordExpired: " + e.getMessage();
            logError(msg);
            throw new RemoteException(msg);
        } finally {
            closeConnection(con);
        }
        return false;
    }
    
	private PasswordHistoryData getMostRecentPasswordHistoryData(Connection con, int userId) throws SQLException {
		String sql = "password_history_id in (select max(phd.password_history_id) " +
					"from clw_password_history phd where phd.user_id = " + userId + ")";
		DBCriteria crit = new DBCriteria();
		crit.addCondition(sql);
		PasswordHistoryDataVector phDV = PasswordHistoryDataAccess.select(con, crit);
		if (phDV.size() > 0){
    		return (PasswordHistoryData)phDV.get(0);
		}
		return null;
	}
    
    
    
}



