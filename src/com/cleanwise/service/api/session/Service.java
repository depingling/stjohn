package com.cleanwise.service.api.session;

import javax.ejb.*;
import java.rmi.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.DataNotFoundException;

public interface Service extends javax.ejb.EJBObject
{
    /**
     *  Flag indicating that name of service provider should exactly match given string
     */
    public final static int EXACT_MATCH = 10000;
    /**
     *  Flag indicating that name of service provider should match beginning of 
     *  given string
     */
    public final static int BEGINS_WITH = 10001;
    /**
     *  Flag indicating that name of service provider should contain given string
     */
    public final static int CONTAINS = 10002;
    /**
     *  Flag indicating that name of service provider should exactly match, ignoring
     *  case, given string
     */
    public final static int EXACT_MATCH_IGNORE_CASE = 10003;
    /**
     *  Flag indicating that name of service provider should match, ignoring case, 
     *  beginning given string
     */
    public final static int BEGINS_WITH_IGNORE_CASE = 10004;
    /**
     *  Flag indicating that name of service provider should contain, ignoring case,
     *  given string
     */
    public final static int CONTAINS_IGNORE_CASE = 10005;

    /**
     *  Flag indicating that returned vector of service providers should be ordered
     *  by ids
     */
    public final static int ORDER_BY_ID = 10006;
    /**
     *  Flag indicating that returned vector of service providers should be ordered
     *  by names
     */
    public final static int ORDER_BY_NAME = 10007;

    /**
     * Gets the service provider information values to be used by the request.
     * @param pSvcProvId the service provider identifier.
     * @return ServiceProviderData
     * @throws RemoteException Required by EJB 1.0
     *         DataNotFoundException if service provider with pSvcProvId doesn't exist
     */
    public ServiceProviderData getServiceProvider(int svcProvId)
	throws RemoteException, DataNotFoundException;
      
    /**
     * Get all the service providers.
     * @param pOrder one of ORDER_BY_ID, ORDER_BY_NAME
     * @return a <code>ServiceProviderDataVector</code> with all service providers.
     * @exception RemoteException if an error occurs
     */
    public ServiceProviderDataVector getAllServiceProviders(int pOrder)
	throws RemoteException;

    /**
     * Get all service providers that match the given criteria.
     * @param BusEntitySearchCriteria the criteria to use in selecting the service provider
     * @return a <code>ServiceProviderDataVector</code> of matching service providers
     * @exception RemoteException if an error occurs
     */
    public ServiceProviderDataVector getServiceProviderByCriteria(BusEntitySearchCriteria pCrit)
	throws RemoteException;

    /**
     * Get all service providers that match the given criteria.
     * @param BusEntitySearchCriteria the criteria to use in selecting the service provider
     * @return a <code>BusEntityDataVector</code> of matching service providers
     * @exception RemoteException if an error occurs
     */
    public BusEntityDataVector getServiceProviderBusEntitiesByCriteria(BusEntitySearchCriteria pCrit)
	throws RemoteException;
    
    /**
     * Describe <code>addServiceProvider</code> method here.
     *
     * @param pServiceProviderData a <code>ServiceProviderData</code> value
     * @return a <code>ServiceProviderData</code> value
     * @exception RemoteException if an error occurs
     */
    public ServiceProviderData addServiceProvider(ServiceProviderData pServiceProviderData)
	throws RemoteException;

    /**
     * Updates the service provider information values to be used by the request.
     * @param pUpdateServiceProviderData  the ServiceProviderData service provider data.
     * @return a <code>ServiceProviderData</code> value
     * @throws            RemoteException Required by EJB 1.0
     */
    public ServiceProviderData updateServiceProvider(ServiceProviderData pServiceProviderData)
	throws RemoteException;

    /**
     * <code>removeServiceProvider</code> may be used to remove an 'unused' service provider.
     * An unused service provider is a service provider with no database references other than
     * the default primary address, phone numbers, email addresses and
     * properties.  Attempting to remove a service provider that is used will
     * result in a failure initially reported as a SQLException and
     * consequently caught and rethrown as a RemoteException.
     *
     * @param pServiceProviderData a <code>ServiceProviderData</code> value
     * @return none
     * @exception RemoteException if an error occurs
     */
    public void removeServiceProvider(ServiceProviderData pServiceProviderData)
	throws RemoteException;

    /**
     * Gets the ServiceProvider information values to be used by the request.
     * @param pSvcProvId an <code>int</code> value
     * @param pStoreIds an <code>IdVector</code> value
     * @param pInactiveFl an <code>boolean</code> value
     * @return ServiceProviderData
     * @exception RemoteException Required by EJB 1.0
     * DataNotFoundException if ServiceProvider with pSvcProvId
     * doesn't exist
     * @exception DataNotFoundException if an error occurs
     */
    public ServiceProviderData getServiceProviderForStore(int pSvcProvId, IdVector pStoreIds, boolean pInactiveFl)
    throws RemoteException, DataNotFoundException ;

    public void configureAllAccounts(int pSvcProviderId, int pStoreId, String userName)
        throws RemoteException;
    
    public BusEntityAssocData addAssoc(Connection con, int pSPId,
            int pBusEntityId, String pType)
        throws SQLException, DataNotFoundException, RemoteException;
    
    public BusEntityAssocData addAssoc(int pSvcProvId, int pBusEntityId, String pType)
        throws RemoteException, DataNotFoundException;
    
    public UserAssocData addAssoc(Connection con, UserAssocData pUserAssoc)
        throws RemoteException, SQLException;
    
    public IdVector getSvcProviderAccountIds(int pSvcProviderId, int pStoreId, boolean pGetInactiveFl)
        throws RemoteException;
    
    public IdVector getSvcProviderSiteIds(int pSvcProviderId, int pStoreId, boolean pGetInactiveFl)
        throws RemoteException;
    
    public void removeAssoc(int pServiceProviderId, int pBusEntityId)
        throws RemoteException;
    
   /**
     * Gets the Service Providers by type
     * @param pStoreId a store identifier
     * @param pProviderCode provider type (returns all provider if null)
     * @param pInactiveFl returns only active proviedes if false 
     * @return set of ServiceProviderData objects
     * @exception RemoteException Required by EJB 1.0
     */
    public ServiceProviderDataVector getServiceProvidersForStore
            (int pStoreId, String pProviderCode, boolean pInactiveFl)
    throws RemoteException;
    
    /**
     * Gets the Service Providers by type
     * @param pAccountId a store identifier
     * @param pProviderCode provider type (returns all provider if null)
     * @param pInactiveFl returns only active proviedes if false 
     * @return set of ServiceProviderData objects
     * @exception RemoteException Required by EJB 1.0
     */
    public ServiceProviderDataVector getServiceProvidersForAccount
            (int pAccountId, String pProviderCode, boolean pInactiveFl)
    throws RemoteException;
    
    /**
     * Returns Service Providers associated with given User
     * @param pUserId a user identifier
     * @return set of ServiceProviderData objects
     * @exception RemoteException Required by EJB 1.0
     */
    public ServiceProviderDataVector getServiceProvidersForUser(int pUserId)
            throws RemoteException;

}
