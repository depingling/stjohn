package com.cleanwise.service.api.session;

/**
 * Title:        ServiceProviderType
 * Description:  Remote Interface for ServiceProviderType Stateless Session Bean
 * Purpose:      Provides access to the services for managing the ServiceProviderType information, properties and relationships.
 * Copyright:    Copyright (c) 2008
 * Company:      CleanWise, Inc.
 * @author       alexxey
 */

import javax.ejb.*;
import java.rmi.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.DataNotFoundException;

/**
 * Remote interface for the <code>ServiceProviderType</code> stateless session bean.
 *
 */
public interface ServiceProviderType extends javax.ejb.EJBObject
{
    
    /**
     * Gets the ServiceProviderType information values to be used by the request.
     * @param pServiceProviderTypeId the manufacturer identifier.
     * @return ManufacturerData
     * @throws RemoteException Required by EJB 1.0
     *         DataNotFoundException if manufacturer with pManufacturerId doesn't exist
     */
    public BusEntityData getServiceProviderType(int pServiceProviderTypeId)
	throws RemoteException, DataNotFoundException;
      
    /**
     * Get all the ServiceProviderTypes.
     * @param pOrder one of ORDER_BY_ID, ORDER_BY_NAME
     * @return a <code>BusEntityDataVector</code> with all ServiceProviderTypes.
     * @exception RemoteException if an error occurs
     */
    public BusEntityDataVector getAllServiceProviderTypes(String pOrder)
	throws RemoteException;

    /**
     * Get all ServiceProviderTypes that match the given criteria.
     * @param BusEntitySearchCriteria the criteria to use in selecting the manufacturer
     * @return a <code>ManufacturerDataVector</code> of matching ServiceProviderTypes
     * @exception RemoteException if an error occurs
     */
    public BusEntityDataVector getServiceProviderTypeByCriteria(BusEntitySearchCriteria pCrit)
	throws RemoteException;
    
    /**
     * Describe <code>addServiceProviderType</code> method here.
     *
     * @param pBusEntityData a <code>BusEntityData</code> value
     * @return a <code>BusEntityData</code> value
     * @exception RemoteException if an error occurs
     */
    public BusEntityData addServiceProviderType(BusEntityData pServiceProviderType, int storeId)
	throws RemoteException;

    /**
     * Updates the ServiceProviderType information values to be used by the request.
     * @param pServiceProviderType the ServiceProviderType data.
     * @return a <code>BusEntityData</code> value
     * @throws            RemoteException Required by EJB 1.0
     */
    public BusEntityData updateServiceProviderType(BusEntityData pServiceProviderType, int storeId)
	throws RemoteException;

    /**
     * <code>removeServiceProviderType</code> may be used to remove an 'unused' ServiceProviderType.
     * An unused ServiceProviderType is a ServiceProviderType with no database references.
     * Attempting to remove a ServiceProviderType that is used will
     * result in a failure initially reported as a SQLException and
     * consequently caught and rethrown as a RemoteException.
     *
     * @param pServiceProviderType a <code>BusEntityData</code> value
     * @return none
     * @exception RemoteException if an error occurs
     */
    public void removeServiceProviderType(int serviceProviderTypeId)
	throws RemoteException;

    /**
     * Gets the ServiceProviderType information values to be used by the request.
     * @param pStoreIds an <code>IdVector</code> value
     * @param pInactiveFl an <code>boolean</code> value
     * @return BusEntityData
     * @exception RemoteException Required by EJB 1.0
     * DataNotFoundException if ServiceProviderTypes for pStoreId
     * doesn't exist
     * @exception DataNotFoundException if an error occurs
     */
    public BusEntityDataVector getServiceProviderTypesForStore(int pStoreId, boolean pInactiveFl)
        throws RemoteException, DataNotFoundException;
    
    public BusEntityDataVector getUserServiceProviderTypes(int userId, int storeId) throws RemoteException;

}
