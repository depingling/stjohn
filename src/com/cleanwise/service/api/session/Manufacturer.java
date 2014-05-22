package com.cleanwise.service.api.session;

/**
 * Title:        Manufacturer
 * Description:  Remote Interface for Manufacturer Stateless Session Bean
 * Purpose:      Provides access to the services for managing the manufacturer information, properties and relationships.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 */

import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.value.*;

import java.rmi.RemoteException;

/**
 * Remote interface for the <code>Manufacturer</code> stateless session bean.
 *
 * @author <a href="mailto:tbesser@cleanwise.com"></a>
 */
public interface Manufacturer extends javax.ejb.EJBObject
{
    /**
     *  Flag indicating that name of manufacturer should exactly match given string
     */
    public final static int EXACT_MATCH = 10000;
    /**
     *  Flag indicating that name of manufacturer should match beginning of 
     *  given string
     */
    public final static int BEGINS_WITH = 10001;
    /**
     *  Flag indicating that name of manufacturer should contain given string
     */
    public final static int CONTAINS = 10002;
    /**
     *  Flag indicating that name of manufacturer should exactly match, ignoring
     *  case, given string
     */
    public final static int EXACT_MATCH_IGNORE_CASE = 10003;
    /**
     *  Flag indicating that name of manufacturer should match, ignoring case, 
     *  beginning given string
     */
    public final static int BEGINS_WITH_IGNORE_CASE = 10004;
    /**
     *  Flag indicating that name of manufacturer should contain, ignoring case,
     *  given string
     */
    public final static int CONTAINS_IGNORE_CASE = 10005;

    /**
     *  Flag indicating that returned vector of manufacturers should be ordered
     *  by ids
     */
    public final static int ORDER_BY_ID = 10006;
    /**
     *  Flag indicating that returned vector of manufacturers should be ordered
     *  by names
     */
    public final static int ORDER_BY_NAME = 10007;

    /**
     * Gets the manufacturer information values to be used by the request.
     * @param pManufacturerId the manufacturer identifier.
     * @return ManufacturerData
     * @throws RemoteException Required by EJB 1.0
     *         DataNotFoundException if manufacturer with pManufacturerId doesn't exist
     */
    public ManufacturerData getManufacturer(int manufacturerId)
	throws RemoteException, DataNotFoundException;
      
    /**
     * Get all the manufacturers.
     * @param pOrder one of ORDER_BY_ID, ORDER_BY_NAME
     * @return a <code>ManufacturerDataVector</code> with all manufacturers.
     * @exception RemoteException if an error occurs
     */
    public ManufacturerDataVector getAllManufacturers(int pOrder)
	throws RemoteException;

    /**
     * Get all manufacturers that match the given criteria.
     * @param BusEntitySearchCriteria the criteria to use in selecting the manufacturer
     * @return a <code>ManufacturerDataVector</code> of matching manufacturers
     * @exception RemoteException if an error occurs
     */
    public ManufacturerDataVector getManufacturerByCriteria(BusEntitySearchCriteria pCrit)
	throws RemoteException;

    /**
     * Get all manufacturers that match the given criteria.
     * @param BusEntitySearchCriteria the criteria to use in selecting the manufacturer
     * @return a <code>BusEntityDataVector</code> of matching manufacturers
     * @exception RemoteException if an error occurs
     */
    public BusEntityDataVector getManufacturerBusEntitiesByCriteria(BusEntitySearchCriteria pCrit)
	throws RemoteException;
    
    /**
     * Describe <code>addManufacturer</code> method here.
     *
     * @param pManufacturerData a <code>ManufacturerData</code> value
     * @return a <code>ManufacturerData</code> value
     * @exception RemoteException if an error occurs
     */
    public ManufacturerData addManufacturer(ManufacturerData pManufacturerData)
	throws RemoteException;

    /**
     * Updates the manufacturer information values to be used by the request.
     * @param pUpdateManufacturerData  the ManufacturerData manufacturer data.
     * @return a <code>ManufacturerData</code> value
     * @throws            RemoteException Required by EJB 1.0
     */
    public ManufacturerData updateManufacturer(ManufacturerData pManufacturerData)
	throws RemoteException;

    /**
     * <code>removeManufacturer</code> may be used to remove an 'unused' manufacturer.
     * An unused manufacturer is a manufacturer with no database references other than
     * the default primary address, phone numbers, email addresses and
     * properties.  Attempting to remove a manufacturer that is used will
     * result in a failure initially reported as a SQLException and
     * consequently caught and rethrown as a RemoteException.
     *
     * @param pManufacturerData a <code>ManufacturerData</code> value
     * @return none
     * @exception RemoteException if an error occurs
     */
    public void removeManufacturer(ManufacturerData pManufacturerData)
	throws RemoteException;

    /**
     * Gets the Manufacturer information values to be used by the request.
     * @param pManufacturerId an <code>int</code> value
     * @param pStoreIds an <code>IdVector</code> value
     * @param pInactiveFl an <code>boolean</code> value
     * @return ManufacturerData
     * @exception RemoteException Required by EJB 1.0
     * DataNotFoundException if Manufacturer with pManufacturerId
     * doesn't exist
     * @exception DataNotFoundException if an error occurs
     */
    public ManufacturerData getManufacturerForStore(int pManufacturerId, IdVector pStoreIds, boolean pInactiveFl)
    throws RemoteException, DataNotFoundException ;

    public BusEntityDataVector getManufacturerByUserId(String userId) throws RemoteException;    

    public PairViewVector getEnterpriseAssoc(int pEnterpriseStoreId, int pManagedStoreId) throws RemoteException;

    public void addEnterpriseManufAssoc(PairViewVector pMfgIdPairs, String pUser) throws RemoteException;

    public void removeEnterpriseManufAssoc(PairViewVector pMfgIdPairs) throws RemoteException;
    
    public BusEntityDataVector getManufBusEntityByCriteria(BusEntitySearchCriteria pCrit) throws RemoteException;
    
    public BusEntityDataVector getManufacturerByName(String manufName, int storeId) throws RemoteException;
    
    public PropertyDataVector getManufacturerProps(int manufacturerId) throws RemoteException;
    
    public BusEntityDataVector getManufacturerByUserInAccountCatalogs(int pUserId)throws RemoteException;
}
