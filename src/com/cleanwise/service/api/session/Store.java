package com.cleanwise.service.api.session;

import com.cleanwise.service.api.dto.StoreProfileDto;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.ICleanwiseUser;
import com.cleanwise.service.api.value.*;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 *  Remote interface for the <code>Store</code> stateless session bean.
 *  <ol>
 *    <b>Store access control</b>. 
 *    <br>Store acces is determined by the store's state.  The state
 *    of a store is determined by calling:
 *    &nbsp;&nbsp; StoreData.getBusEntity().getBusEntityStatusCd().
 *    <p>
 *    <li> State: <b>ACTIVE</b></li> , operational state for a store.
 *    <ul>
 *      <li> Behavior for: <b>ADMINISTRATOR</b></li> <br>
 *      The administrator can access all store screens while a store is in
 *      ACTIVE.
 *      <li> Behavior for: <b>STORE MANAGER</b></li> <br>
 *      The store manager can access all functionality for the store.
 *      <li> Behavior for: <b>BUYER</b></li> <br>
 *      Buyers can place orders.
 *    </ul>
 *
 *    <li> State: <b>STAGING</b></li> , this is a test state to deploy a new store, or
 *    test some aspect of a store
 *    <ul>
 *      <li> Behavior for: <b>ADMINISTRATOR</b></li> <br>
 *      The administrator can access all store screens while a store is in
 *      STAGING.
 *      <li> Behavior for: <b>STORE MANAGER</b></li> <br>
 *      The store manager can access all functionality for the store.
 *      <li> Behavior for: <b>BUYER</b></li> <br>
 *      If a regular buyer tries to acces a catalog for this store, the buyer is
 *      informed that the store is not available at this time. No purchases are
 *      allowed unless the buyer has access to a staging store. Buyers with
 *      staging access are special buyers, used for testing the store.
 *    </ul>
 *
 *    <li> State: <b>INACTIVE</b></li> , non-operational state for a store.
 *    <ul>
 *      <li> Behavior for: <b>ADMINISTRATOR</b></li> <br>
 *      The administrator can access all store screens while a store is
 *      INACTIVE.
 *      <li> Behavior for: <b>STORE MANAGER</b></li> <br>
 *      If a store manager tries to access this store, the store manager is
 *      informed that the store is not active and to contact a representative.
 *
 *      <li> Behavior for: <b>BUYER</b></li> <br>
 *      If a buyer tries to access a catalog for this store, the buyer is
 *      informed that the store is not available at this time. No purchases are
 *      allowed.
 *    </ul>
 *
 *  </ol>
 *
 *
 *@author     <a href="mailto:tbesser@cleanwise.com"</a>
 *@created    August 21, 2001
 */
public interface Store extends javax.ejb.EJBObject {
    /**
     *  Flag indicating that name of store should exactly match given string
     */
    public final static int EXACT_MATCH = 10000;
    /**
     *  Flag indicating that name of store should match beginning of 
     *  given string
     */
    public final static int BEGINS_WITH = 10001;
    /**
     *  Flag indicating that name of store should contain given string
     */
    public final static int CONTAINS = 10002;
    /**
     *  Flag indicating that name of store should exactly match, ignoring
     *  case, given string
     */
    public final static int EXACT_MATCH_IGNORE_CASE = 10003;
    /**
     *  Flag indicating that name of store should match, ignoring case, 
     *  beginning given string
     */
    public final static int BEGINS_WITH_IGNORE_CASE = 10004;
    /**
     *  Flag indicating that name of store should contain, ignoring case,
     *  given string
     */
    public final static int CONTAINS_IGNORE_CASE = 10005;

    /**
     *  Flag indicating that returned vector of stores should be ordered
     *  by ids
     */
    public final static int ORDER_BY_ID = 10006;
    /**
     *  Flag indicating that returned vector of stores should be ordered
     *  by names
     */
    public final static int ORDER_BY_NAME = 10007;

    /**
     *  Gets the store information values to be used by the request.
     *
     *@param  storeId                    Description of Parameter
     *@return                            StoreData
     *@exception  DataNotFoundException  Description of Exception
     *@throws  RemoteException           Required by EJB 1.0
     *      DataNotFoundException if store with pStoreId doesn't exist
     */
    public StoreData getStore(int storeId)
             throws RemoteException, DataNotFoundException;


    /**
     *  Get all the stores.
     *
     *@param  pOrder               one of ORDER_BY_ID, ORDER_BY_NAME
     *@return                      a <code>StoreDataVector</code> with all
     *      stores.
     *@exception  RemoteException  if an error occurs
     */
    public StoreDataVector getAllStores(int pOrder)
             throws RemoteException;

    public DomainDataVector getAllDomains(int pOrder)
    	throws RemoteException;

    public DomainData getDomain(int busEntId) throws RemoteException;
    
    public PropertyData getSSLName(int busEntId) throws RemoteException;
    	
    /**
     *  Get all the stores.
     *
     *@param  pOrder       one of ORDER_BY_ID, ORDER_BY_NAME
     *@return              a <code>BusEntityDataVector</code> with all stores.
     *@exception  RemoteException  if an error occurs
     */
    public BusEntityDataVector getAllStoresBusEntityData(int pOrder) throws RemoteException;

    public BusEntityDataVector getAllDomainsBusEntityData(int pOrder) throws RemoteException;    

    public BusEntityDataVector getStoresCollectionByBusEntity(int pBusEntityId) throws RemoteException;
    
    public BusEntityDataVector getStoresCollectionByBusEntity(String pName, int pMatch, int pOrder)
    	    throws RemoteException;
    public BusEntityDataVector getStoresCollectionByBusEntity(int pBusEntityId,
    	    String pName,
    	    int pMatch,
    	    int pOrder		
    ) throws RemoteException;
    
    
    public BusEntityDataVector getDefaultStoreByBusEntity(int pBusEntityId) throws RemoteException;
    
    public BusEntityAssocData addStoreAssoc(Connection con, int pStoreId,
            int pBusEntityId, String pType, String adderName)
    			throws SQLException, DataNotFoundException, RemoteException;
    
    public BusEntityAssocData addStoreAssoc(int pStoreId, int pBusEntityId, String pType, String adderName)
    	throws RemoteException, DataNotFoundException;
    
    public BusEntityAssocData addStoreAssoc(Connection con, BusEntityAssocData pStoreAssoc)
    	throws RemoteException, SQLException;

    public void removeStoreAssoc(int pStoreId, int pBusEntityId, String pType)
    	throws RemoteException;
    
    public int updateStoreAssoc(int pStoreId, int pBusEntityId, String pType)
    	throws SQLException, DataNotFoundException, RemoteException;
    
    /**
     *  Describe <code>addStore</code> method here.
     *
     *@param  pStoreData           a <code>StoreData</code> value
     *@return                      a <code>StoreData</code> value
     *@exception  RemoteException  if an error occurs
     */
    public StoreData addStore(StoreData pStoreData)
             throws RemoteException;

    public DomainData updateDomain(DomainData pDomainData)
		throws RemoteException;
    
    
    /**
     *  Updates the store information values to be used by the request.
     *
     *@param  pStoreData        Description of Parameter
     *@return                   a <code>StoreData</code> value
     *@throws  RemoteException  Required by EJB 1.0
     */
    public StoreData updateStore(StoreData pStoreData)
             throws RemoteException;


    /**
     *  <code>removeStore</code> may be used to remove an 'unused' store. An
     *  unused store is a store with no database references other than the
     *  default primary address, phone numbers, email addresses and properties.
     *  Attempting to remove a store that is used will result in a failure
     *  initially reported as a SQLException and consequently caught and
     *  rethrown as a RemoteException.
     *
     *@param  pStoreData           a <code>StoreData</code> value
     *@exception  RemoteException  if an error occurs
     */
    public void removeStore(StoreData pStoreData)
             throws RemoteException;
    
    
    /**
     *Gets a list of StoreData objects based off the supplied search criteria object
     *@param BusEntitySearchCriteria the search criteria
     *@return                      a set of StoreData objects
     *@exception  RemoteException  if an error occurs
     */
    public StoreDataVector getStoresByCriteria(BusEntitySearchCriteria pCrit)
             throws RemoteException;

   public IdVector getStoreIdsByCriteria(BusEntitySearchCriteria pCrit) throws RemoteException;

    /**
     * Gets linked store-item id pairs for a managed item
     * @param parentItemId
     * @param userId
     * @param userTypeCd
     * @return PairViewVector store-item id pairs
     * @throws RemoteException
     */
    public PairViewVector getLinkStoreItemPairIdsByParentItem(int parentItemId,int userId,String userTypeCd)
             throws RemoteException;

    public PairViewVector getLinkStoreItemPairIdsByParentItemBetweenStores(int parentItemId,int userId,String userTypeCd)
        throws RemoteException;

    /**
     * gets store identifier
     *
     * @param accountId account identifier
     * @return store identifier
     * @throws RemoteException if an errors
     */
    public int getStoreIdByAccount(int accountId) throws RemoteException,DataNotFoundException;
    /**
     * gets store bus entity data
     *
     * @param pAccountId account identifier
     * @return bus entity data
     * @throws RemoteException if an errors
     * @throws com.cleanwise.service.api.util.DataNotFoundException if data not found
     */
     public BusEntityData getStoreBusEntityByAccount(int pAccountId) throws RemoteException, DataNotFoundException;

    /**
     * gets store identifiers
     *
     * @param pStoreType store type
     * @return store identifiers
     * @throws RemoteException if an errors
     */
    public IdVector getStoreIdsByType(String pStoreType) throws RemoteException;


    /**
     * Seperates the store ids into their associatied types:
     * input:
     * 1,2,3,4
     * return:
     * DISTIBUTOR: 1,2
     * MLA: 3
     * MANUFACTURER 4
     *
     * @throws com.cleanwise.service.api.util.DataNotFoundException
     *         when one of the passed in stores does not hae a type configured
     * @throws java.rmi.RemoteException if there was a problem retrieving
     *         the store types from the passed in ids
     */
    public Map seperateStoresByType(IdVector pStoreIds) throws DataNotFoundException, RemoteException;
    
    public String getDefaultEmailForStoreUser(int pUserId) throws RemoteException;

    public BusEntityDataVector getStoresBusEntByCriteria(BusEntitySearchCriteria criteria) throws RemoteException;
    
    public BusEntityDataVector getChildStores(int storeId) throws RemoteException;

    public void synchronizeChildStore(int childStoreId, ICleanwiseUser user)  throws RemoteException;
    
    public void synchronizeMasterAsset(int parentStoreId, int masterAssetId, ICleanwiseUser user)  throws RemoteException;
    
    public void loadMasterAsset(String loadingTableName, int storeId, String user)  throws RemoteException;

    public void loadMasterAsset(String loadingTableName, boolean stageUnmatched, ICleanwiseUser user)  throws RemoteException; 

  	public void loadMasterAsset(String loadingTableName, ICleanwiseUser user)  throws RemoteException;
    
    public void loadMasterItem(String loadingTableName, ICleanwiseUser user)  throws RemoteException;
    
    public void loadPhysicalAsset(String loadingTableName, ICleanwiseUser user)  throws RemoteException;
	
    public void loadPhysicalAsset(String loadingTableName, int storeId, String userName)  throws RemoteException;

	public void loadMasterAsset(int staged_assetId, ICleanwiseUser appUser)  throws RemoteException;

	public void loadMasterAsset(int staged_assetId, int assetId, ICleanwiseUser appUser)  throws RemoteException;

    public void splitAssetItemTable(String loadingTableName)  	throws RemoteException;

	public void synchronizeParentChildStoreItems(int parentStoreId, int childStoreId, int parentItemId, ICleanwiseUser user)
			throws RemoteException;

	public void synchronizeParentChildStoreCateg(int parentStoreId, int childStoreId, ICleanwiseUser user)
			throws RemoteException;

    public void loadMasterAssetFromStaged(ICleanwiseUser user)  throws RemoteException;

    public void loadMasterItemFromStaged(ICleanwiseUser user)  throws RemoteException;

    public void loadMasterItem(int staged_itemId, int itemId, ICleanwiseUser appUser)  throws RemoteException;

	public void loadMasterItem(int staged_itemId, ICleanwiseUser appUser)  throws RemoteException;

    public void loadMasterItem(String loadingTableName, int storeId, String userName)  throws RemoteException;

	public List checkForChildDuplItems(int pParentStoreId)  
	throws RemoteException;
  
    public List checkForChildDuplItems(int pParentStoreId, int pParentManufId, String pManufSku, String pUom)
    throws RemoteException;
	
    public String getStoreType(int pStoreId) throws RemoteException;
    
    public StoreProfileDataVector getStoreProfile(int pStoreId) throws RemoteException;
    
    public StoreProfileData getStoreProfile(int pStoreId, String pShortDesc) throws RemoteException;
    
    public void updateStoreProfile(String pUser,int pStoreId, StoreProfileDto pStoreProfile, List allLanguages) 
    throws RemoteException ;
   
}

