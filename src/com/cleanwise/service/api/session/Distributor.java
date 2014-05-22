package com.cleanwise.service.api.session;

/**
 * Title:        Distributor
 * Description:  Remote Interface for Distributor Stateless Session Bean
 * Purpose:      Provides access to the services for managing the
 * distributor information, properties and relationships.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 */

import javax.ejb.*;
import java.rmi.*;
import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Hashtable;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.DataNotFoundException;
import org.apache.struts.action.ActionErrors;

/**
 * Remote interface for the <code>Distributor</code> stateless session bean.
 *
 * @author <a href="mailto:tbesser@cleanwise.com"></a>
 */
public interface Distributor extends javax.ejb.EJBObject
{
    /**
     *  Flag indicating that name of distributor should exactly match
     *  given string
     */
    public final static int EXACT_MATCH = 10000;
    /**
     *  Flag indicating that name of distributor should match beginning of
     *  given string
     */
    public final static int BEGINS_WITH = 10001;
    /**
     *  Flag indicating that name of distributor should contain given
     *  string
     */
    public final static int CONTAINS = 10002;
    /**
     *  Flag indicating that name of distributor should exactly match, ignoring
     *  case, given string
     */
    public final static int EXACT_MATCH_IGNORE_CASE = 10003;
    /**
     *  Flag indicating that name of distributor should match,
     *  ignoring case, beginning given string
     */
    public final static int BEGINS_WITH_IGNORE_CASE = 10004;
    /**
     *  Flag indicating that name of distributor should contain, ignoring case,
     *  given string
     */
    public final static int CONTAINS_IGNORE_CASE = 10005;

    /**
     *  Flag indicating that returned vector of distributors should be ordered
     *  by ids
     */
    public final static int ORDER_BY_ID = 10006;
    /**
     *  Flag indicating that returned vector of distributors should be ordered
     *  by names
     */
    public final static int ORDER_BY_NAME = 10007;

    /**
     *  Gets BusEntityData object for the distributor
     *
     *@param  pDistributorId             an <code>int</code> value
     *@return                            a <code>BusEntityData</code> value
     *@exception  RemoteException        if an error occurs
     *@exception  DataNotFoundException  if an error occurs
     */
    public BusEntityData getDistributorBusEntity(int pDistributorId)
             throws RemoteException, DataNotFoundException;

    /**
     * Gets the distributor information values to be used by the request.
     * @param distributorId an <code>int</code> value
     * @return DistributorData
     * @exception RemoteException Required by EJB 1.0
     * DataNotFoundException if distributor with pDistributorId
     * doesn't exist
     * @exception DataNotFoundException if an error occurs
     */
    public DistributorData getDistributor(int distributorId)
	throws RemoteException, DataNotFoundException;
    /**
     * Gets the distributor information values to be used by the request.
     * @param pDistributorId an <code>int</code> value
     * @param pStoreIds an <code>IdVector</code> value
     * @param pInactiveFl an <code>boolean</code> value
     * @return DistributorData
     * @exception RemoteException Required by EJB 1.0
     * DataNotFoundException if distributor with pDistributorId
     * doesn't exist
     * @exception DataNotFoundException if an error occurs
     */
    public DistributorData getDistributorForStore(int pDistributorId, IdVector pStoreIds,boolean pInactiveFl)
    throws RemoteException, DataNotFoundException;

    /**
     * Get all distributor ids for given store
     */
    public IdVector getDistributorIdsForStore(int pStoreId)
    throws RemoteException,DataNotFoundException ;
    public IdVector getDistributorIdsForStore(int pStoreId, String pDistrStatus)
    throws RemoteException,DataNotFoundException ;

    /**
     * Get all the distributors.
     * @param pOrder one of ORDER_BY_ID, ORDER_BY_NAME
     * @return a <code>DistributorDataVector</code> with all distributors.
     * @exception RemoteException if an error occurs
     */
    public DistributorDataVector getAllDistributors(int pOrder)
	throws RemoteException;

    /**
     * Get all distributors that match the given name.  The arguments specify
     * whether the name is interpreted as a pattern or exact match.
     * @param pName a <code>String</code> value with distributor name or pattern
     * @param pMatch one of EXACT_MATCH, BEGINS_WITH, CONTAINS,
     * EXACT_MATCH_IGNORE_CASE, BEGINS_WITH_IGNORE_CASE and
     * CONTAINS_IGNORE_CASE.
     * @param pOrder one of ORDER_BY_ID, ORDER_BY_NAME
     * @return a <code>DistributorDataVector</code> of matching distributors
     * @exception RemoteException if an error occurs
     */
    public DistributorDataVector getDistributorByName(String pName,
						      int pMatch,
						      int pOrder)
	throws RemoteException;

    /**
     *Gets a list of DistributorData objects based off the supplied search criteria object
     *@param pCrit the search criteria
     *@return                      a set of DistributorData objects
     *@exception  RemoteException  if an error occurs
     */
    public DistributorDataVector getDistributorByCriteria(BusEntitySearchCriteria pCrit)
             throws RemoteException;

    /**
     *Gets a list of BusEntityDataVector objects based off the supplied search criteria object
     *@param pCrit the search criteria
     *@return                      a set of DistributorData objects
     *@exception  RemoteException  if an error occurs
     */
    public BusEntityDataVector getDistributrBusEntitiesByCriteria(BusEntitySearchCriteria pCrit)
             throws RemoteException;


    /**
     *  Describe <code>getDistributorByErpNum</code> method here.
     *
     *@param  pErpNum             an <code>int</code> value
     *@return                            a <code>DistributorData</code> value
     *@exception  RemoteException        if an error occurs
     *@exception  DataNotFoundException  if an error occurs
     */
    public DistributorData getDistributorByErpNum(String pErpNum)
	throws RemoteException;


    /**
     *  Describe <code>getDistributorCollectionByIdList</code> method here.
     *
     *@param  pBusEntityIdList             an <code>int</code> value
     *@return                            a <code>DistributorData</code> value
     *@exception  RemoteException        if an error occurs
     *@exception  DataNotFoundException  if an error occurs
     */
    public DistributorDataVector getDistributorCollectionByIdList(IdVector pBusEntityIdList)
	throws RemoteException;

    /**
     * Describe <code>addDistributor</code> method here.
     *
     * @param pDistributorData a <code>DistributorData</code> value
     * @return a <code>DistributorData</code> value
     * @exception RemoteException if an error occurs
     */
    public DistributorData addDistributor(DistributorData pDistributorData)
	throws RemoteException;

    /**
     * Updates the distributor information values to be used by the request.
     * @param pDistributorData a <code>DistributorData</code> value
     * @return a <code>DistributorData</code> value
     * @exception            RemoteException Required by EJB 1.0
     */
    public DistributorData updateDistributor(DistributorData pDistributorData)
	throws RemoteException;

    /**
     * <code>removeDistributor</code> may be used to remove an 'unused' distributor.
     * An unused distributor is a distributor with no database references other than
     * the default primary address, phone numbers, email addresses and
     * properties.  Attempting to remove a distributor that is used will
     * result in a failure initially reported as a SQLException and
     * consequently caught and rethrown as a RemoteException.
     *
     * @param pDistributorData a <code>DistributorData</code> value
     * @exception RemoteException if an error occurs
     */
    public void removeDistributor(DistributorData pDistributorData)
	throws RemoteException;

    /**
     * Describe <code>addShipFromAddress</code> method here.
     *
     * @param pShipFromAddr an <code>AddressData</code> value
     * @return a <code>boolean</code> value
     * @exception RemoteException if an error occurs
     */
    public boolean addShipFromAddress(AddressData pShipFromAddr)
	throws RemoteException;

    /**
     * Describe <code>updateShipFromAddress</code> method here.
     *
     * @param pShipFromAddr an <code>AddressData</code> value
     * @return a <code>boolean</code> value
     * @exception RemoteException if an error occurs
     */
    public boolean updateShipFromAddress(AddressData pShipFromAddr)
	throws RemoteException;

    /**
     * Describe <code>getShipFromAddressCollection</code> method here.
     *
     * @param pDistributorId an <code>int</code> value
     * @param pOptionalAddressStatusCd a <code>String</code> value
     * @return a <code>DistShipFromAddressViewVector</code> value
     * @exception RemoteException if an error occurs
     */
    public DistShipFromAddressViewVector getShipFromAddressCollection
	(int pDistributorId, String pOptionalAddressStatusCd)
	throws RemoteException;

    /**
     * Describe <code>removeShipFrom</code> method here.
     *
     * @param pShipFromAddrId an <code>int</code> value
     * @exception RemoteException if an error occurs
     */
    public void removeShipFrom(int pShipFromAddrId)
	throws RemoteException ;

    public DistShipFromAddressViewVector locateShipFrom
	(String pSearchType, String pSearchVal)
	throws RemoteException;

    public DistShipFromAddressViewVector locateShipFrom
	(String pSearchType, String pSearchVal, Integer pDistId)
	throws RemoteException;
    /**
     *  Describe <code>getDistributorCounties</code> method here.
     *
     *@param  pDistId distributor identifier
     *@param  pConditions set of coditions. Keys: state, county, postalCode
     *@return                            a <code>DistributorData</code> value
     *@exception  RemoteException        if an error occurs
     */
    public BusEntityTerrViewVector getDistributorCounties(int pDistId, Hashtable pConditions)
	throws RemoteException;

    /**
     *  Describe <code>getDistributorZipCodes</code> method here.
     *
     *@param  pDistId distributor identifier
     *@param  pConditions set of coditions. Keys: state, county, postalCode
     *@return                            a <code>DistributorData</code> value
     *@exception  RemoteException        if an error occurs
     */
    public BusEntityTerrViewVector getDistributorZipCodes(int pDistId, Hashtable pConditions)
	throws RemoteException;

    /**
     *  Gets Net Supply Distributor Business Entity
     *
     *@param  pStore              store identifier
     *@return    Set of BusEntityData objects (if correc must be one object only)
     *@exception  RemoteException  if an error occurs
     */

     public BusEntityDataVector getNscStoreDistributor(int pStoreId)
              throws RemoteException;

     /**
      *  Describe <code>getSubDistributorForSite(</code> method here.
      *
      *@param  pSiteId              site identifier
      *@return                      a <code>DistributorData</code> value
      *@exception  RemoteException  if an error occurs
      */
     public DistributorData getSubDistributorForSite(int pSiteId)
              throws RemoteException ;
    /**
     *  Describe <code>updateDistributorCounties</code> method here.
     *
     *@param  pCounties set of BusEntityTerrView objects
     *@param  pUser the user login name
     *@exception  RemoteException        if an error occurs
     */
    public void updateDistributorCounties(BusEntityTerrViewVector pCounties, String pUser)
	throws RemoteException;
    /**
     *  Describe <code>updateDistributorZipCodes</code> method here.
     *
     *@param  pZipCodes set of BusEntityTerrView objects
     *@param  pUser the user login name
     *@exception  RemoteException        if an error occurs
     */
    public void updateDistributorZipCodes(BusEntityTerrViewVector pZipCodes, String pUser)
	throws RemoteException;

    /**
     *  Get all FreightHandlers that match the given criteria.
     *
     *@param  pBusEntitySearchCriteria the search criteria to use
     *@return a <code>FreightHandlerViewVector</code> of matching FreightHandlers
     *@exception  RemoteException  if an error occurs
     */
    public FreightHandlerViewVector getFreightHandlersByCriteria(
            BusEntitySearchCriteria pBusEntitySearchCriteria)throws RemoteException;



    public FreightHandlerView saveFreightHandler(FreightHandlerView pFreightHandler)
	throws RemoteException ;
    /**
     *  Gets a list of delivery schedule view objects
     *@param  pDistributorId id of the BusEntity object
     *@param pFilter set of criteria. Accepts keys:
     * - "STATE_PROVINCE_CD", "COUNTY_CD", "POSTAL_CD", "SHORT_DESC", "ID"
     *@param  pStartWithFl applies two search types: start with ignore case (true)
     * and contains ignore case (false)
     *@return a set of DeliveryScheduleView objects
     *@exception  RemoteException        if an error occurs
     */
  public DeliveryScheduleViewVector getDeliverySchedules(int pDistributorId, Hashtable pFilter, boolean pStartWithFl)
  throws RemoteException;

    /**
     *  Gets a delivery schedule
     *@param  pScheduleId the schedule id
     *@return a set of ScheduleJoinView objects
     *@exception  RemoteException        if an error occurs
     */
  public ScheduleJoinView getDeliveryScheduleById (int pScheduleId)
  throws RemoteException;

   /**
   *  Gets a delivery schedule
   *@param  pScheduleId the schedule id
   *@param  zipCdFl if false wont return rows where schedule_detail_cd = "ZIP_CODE"
   *@return a set of ScheduleJoinView objects
   *@exception  RemoteException        if an error occurs
   */
  public ScheduleJoinView getDeliveryScheduleById(int pScheduleId, boolean zipCdFl)
  throws RemoteException;

  /**
     *  Deletes the delivery schedule
     *@param  pScheduleId the schedule id
     *@exception  RemoteException        if an error occurs
     */
  public void deleteDeliverySchedule (int pScheduleId)
  throws RemoteException;

  /**
     *  Updates existing or inserts new schedule depending on scheduleId property
     *@param  pSchedule the schedule
     *@param pScheduleDetailDV the set of ScheduleDetail objects. It will skip all zip code confure properties
     *@param  pUser the user login name
     *@exception  RemoteException        if an error occurs
     */
     public ScheduleJoinView saveDeliverySchedule(ScheduleData pSchedule,
                                              ScheduleDetailDataVector pScheduleDetailDV,
                                              String pUser)
     throws RemoteException;

    /**
     *  Describe <code>getDeliveryScheduleCounties</code> method here.
     *
     *@param  pScheduleId the schedule identifier
     *@param  pFilter a set of criteria. Keys: "STATE_PROVINCE_CD", "COUNTY_CD", "POSTAL_CD"
     *@param  pStartWithFl indicates two search types: start with ignore case (true)
     *@param  pConfOnlyFl flag to return configured counties only
     *@return  a set of BusEntityTerrView objects
     *@exception  RemoteException        if an error occurs
     */
    public BusEntityTerrViewVector
      getDeliveryScheduleCounties(int pScheduleId,
              Hashtable pFilter,
              boolean pStartWithFl,
              boolean pConfOnlyFl)
	throws RemoteException;

    /**
     *  Describe <code>getDeliveryScheduleZipCodes</code> method here.
     *
     *@param  pScheduleId the schedule identifier
     *@param  pFilter a set of criteria. Keys: "STATE_PROVINCE_CD", "COUNTY_CD", "POSTAL_CD"
     *@param  pStartWithFl indicates two search types: start with ignore case (true)
     *@param  pConfOnlyFl flag to return configured counties only
     *@return  a set of BusEntityTerrView objects
     *@exception  RemoteException        if an error occurs
     */
    public BusEntityTerrViewVector
      getDeliveryScheduleZipCodes(int pScheduleId,
              Hashtable pFilter,
              boolean pStartWithFl,
              boolean pConfOnlyFl)
	throws RemoteException;

    /**
     *  Describe <code>updateDeliveryScheduleCounties</code> method here.
     *@param pScheduleId the schedule id
     *@param  pCounties set of BusEntityTerrView objects
     *@param  pUser the user login name
     *@exception  RemoteException        if an error occurs
     */
    public void updateDeliveryScheduleCounties(int pScheduleId, BusEntityTerrViewVector pCounties, String pUser)
	throws RemoteException;


    /**
     *  Describe <code>updateDeliveryScheduleZipCodes</code> method here.
     *@param pScheduleId the schedule id
     *@param  pZipCodes set of BusEntityTerrView objects
     *@param  pUser the user login name
     *@exception  RemoteException        if an error occurs
     */
    public void updateDeliveryScheduleZipCodes(int pScheduleId, BusEntityTerrViewVector pZipCodes, String pUser)
	    throws RemoteException;

    /**
     *  Gets configured schedule account ids
     *
     *@param  pScheduleId          the schedule id
     *@param  pAccountIds list of account id to filter or null
     *@returns List of account ids configured to the schedule
     *@exception  RemoteException  if an error occurs
     */
    public IdVector getDeliveryScheduleAccountIds(int pScheduleId, IdVector pAccountIds)
             throws RemoteException;

     /**
     *  Updates accounts configrued to the distributor schedule
     *
     *@param  pScheduleId          the schedule id
     *@param  pAcctIdToAdd         the configured accounts
     *@param  pAcctIdToDel         the unconfigured accounts
     *@param  pUser                the user login name
     *@exception  RemoteException  if an error occurs
     */
    public void updateDeliveryScheduleAccounts(int pScheduleId,
            IdVector pAcctIdToAdd, IdVector pAcctIdToDel, String pUser)
             throws RemoteException;

    /**
     *  Calculates next delivery date for the schedule
     *
     *@param pSiteId the site identifier (0 is acceptable)
     *@param  pScheduleId  of the Schedule identifier
     *@return                      Date object
     *@exception  RemoteException  if an error occurs
     */
    public Date getNextDeliveryDate(int pSiteId,int pScheduleId)
             throws RemoteException;

    /**
     *  Calculates delivery date for the order.
     *
     *@param  pDistId              the distributor id. If 0 pDistErpNum would be
     *      used
     *@param  pDistErpNum          the distributor erp number
     *@param  pSiteId              the site identifier (if 0 would not chech site schedule)
     *@param  pOrderDate           the date of the order
     *@param  pOrderTime           the time of the order
     *@param  pZipCode             site zip code
     *@return                      Date object
     *@exception  RemoteException  if an error occurs
     */
    public Date getDeliveryDate(int pDistId, String pDistErpNum, int pSiteId, Date pOrderDate, Date pOrderTime, String pZipCode)
             throws RemoteException;

    /**
     *  Calculates cutoff and delivery dates for site-distributor
     *
     *@param  pSiteId     The site identifier
     *@param  pDistId     The distributor identifier
     *@param  pZipCode     The Site Zip Code
     *@param  pOrderDate  Order day and time
     *@return    Cutoff and Delivery days pair
     *@exception  RemoteException  Description of the Exception
     */
    public ScheduleOrderDates calculateNextOrderDates
            (int pSiteId, int pDistId, String pZipCode, Date pOrderDate)
    throws RemoteException;

    public boolean isFreightFreeZone(int pDistId, String pZip)
        throws RemoteException ;

    public OrderRoutingData getPrefferedFreightHandler(int pDistId, String pZip)
        throws RemoteException ;

    /**
     *  Gets items for the distributor
     *
     *@param  pDistId       the distributor identifier
     *@param  pItemIds      the set of item ids
     *@return   a set of DistItemView objects
     *@exception  RemoteException  if an error occurs
     */
    public DistItemViewVector  getDistItems(int pDistId, IdVector pItemIds)
    throws RemoteException;

    /**
     *  Saves generic manufacturer item information. Creates new or updates existing
     *  Corrects duplicated relations
     *@param  pDistItem   the  distributor item data
     *@param  pUser      the user login name
     *@return   input DistItemView object with ids set
     *@exception  RemoteException
     */
    public DistItemView updateDitstItemMfgInfo(DistItemView pDistItem, String pUser)
    throws RemoteException;

    /**
     *  Removes generic manufacturer item information. Creates new or updates existing
     *@param  pDistItem   the  distributor item data
     *@exception  RemoteException
     */
    public void removeDitstItemMfgInfo(DistItemView pDistItem)
    throws RemoteException;

    /**
     *  Gets distributor contacts
     *@param  pDistId   the  distributor id
     *@return set of ContactView objects
     *@exception  RemoteException
     */
    public ContactViewVector getDistributorContacts (int pDistId)
    throws RemoteException;

    /**
     * Updates or inserts distributor contact inforemation
     *@param  pContactView   contact data
     *@param  pUser   user login name
     *@return ContactView object
     *@exception  RemoteException
     */
    public ContactView updateDistributorContact (ContactView pContactView, String pUser)
    throws RemoteException;

    /**
     * Deletes distributor contact inforemation
     *@param  pContactId   contact id
     *@exception  RemoteException
     */
    public void deleteDistributorContact (int pContactId)
    throws RemoteException;

    /**
     * Gets active groups for the distributor
     *@param  pDistId   distributor id
     *@return a vector of GroupData objects
     *@exception  RemoteException
     */
    public GroupDataVector getDistributorGroups (int pDistId)
    throws RemoteException;

    /**
     * Gets primary manufacturers for the distibutor
     *@param  pDistId   distributor id
     *@return a vector of BusEntityData objects
     *@exception  RemoteException
     */
    public BusEntityDataVector getPrimaryManufacturers (int pDistId)
    throws RemoteException;

    /**
     * Adds primary manufacturer for the distibutor
     *@param  pDistId   distributor id
     *@param  pMfgId   manufacturer id
     *@param pUser the user login name
     *@exception  RemoteException
     */
    public void addPrimaryManufacturer (int pDistId, int pMfgId, String pUser)
    throws RemoteException;

    /**
     * Removes primary manufacturer for the distibutor
     *@param  pDistId   distributor id
     *@param  pMfgId   manufacturer id
     *@exception  RemoteException
     */
    public void deletePrimaryManufacturer (int pDistId, int pMfgId)
    throws RemoteException;

    /**
     * Gets distributor related addresses
     *@param  pDistId   distributor id
     *@return a vector of AddressData objects
     *@exception  RemoteException
     */
    public AddressDataVector getAddresses (int pDistId,String pAddressTypeCd)
    throws RemoteException;

    /**
     * Adds or Updates distributor related address
     *@param  pAddress address to save
     *@param pUser the user login name
     *@return address
     *@exception  RemoteException
     */
    public AddressData saveAddress (AddressData pAddress, String pUser)
    throws RemoteException;

    /**
     * Removes distributor related address
     *@exception  RemoteException
     */
    public void deleteAddress (int pAddressId)
    throws RemoteException;

    /**
     * Gets a list of states served by the distributor
     * @param pDistId  distributor id
     * @exception  RemoteException
     */
    public ArrayList getServedStates (int pDistId)
    throws RemoteException;

    /**
     * Gets a list of accounts served by the distributor
     * @param pDistId  distributor id
     * @exception  RemoteException
     */
    public BusEntityDataVector getServedAccounts (int pDistId)
    throws RemoteException;

    public ArrayList getBranchesCollection( int pDistId)
    throws RemoteException;

    public FreightHandlerView getFreightHandlerById
	(int pFhid ) throws RemoteException ;

    public String getDistRuntimeDisplayName(int pDistId) throws RemoteException;

   /**
   * Gets a distributor shedule for zip code and account active for provided date
   * @param pDistId  distributor id
   * @param pZipCode zip code
   * @param pAcctId account id
   * @param pDate date when schedule should be active
   * @return ScheduleData object or null if not found or multiple schedules found
   * @exception  RemoteException
   */
   public ScheduleData getScheduleForZipCode(int pDistId, String pZipCode, int pAcctId,
            Date pDate)
     throws RemoteException;

        /**
     * Method for conversion from BusEntityDataVector to DistributorDataVector
     * @param busEntitys - collection of BusEntitys
     * @return DistributorDataVector
     * @throws RemoteException
     */
    public DistributorDataVector getDistributorsByBusEntityCollection(BusEntityDataVector busEntitys) throws RemoteException;

    /**
     * Get the major distributor for the catalog.
     * @param catId catalog id
     * @return  distributor id
     * @throws RemoteException if an errpors
     */
    public int getMajorDistforCatalog(int catId) throws RemoteException;

    public int addZipCode(int pScheduleId, ScheduleDetailData scheduleDetailData, String pUser) throws RemoteException ;

        /**
     * Return all codes for ScheduleId
     * @param pScheduleId
     * @param countryCd
     * @param value
     * @return
     * @throws RemoteException
     */
    public BusEntityTerrViewVector getDeliveryScheduleZipCodes(
            int pScheduleId,
            String countryCd,
            String value)
            throws RemoteException ;

    public IdVector getSchedules(Date pDate) throws RemoteException ;

    public IdVector  getSiteIdsForSchedule(int pDistributorId, int pScheduleId) throws RemoteException ;

    public OrderAddOnChargeDataVector getOrderAddOnCharges(int pDistId, int pOrderId)
    throws RemoteException ;

    public BusEntityDataVector getDistributorForOrderItem(int pOrderId, int pStoreId, String pErpNum, Connection conn) throws RemoteException;

    public  BusEntityData getDistributorForOrderItem(int pOrderId, int pStoreId, String pErpNum)
    throws RemoteException;
    
    public EmailData getRejectedInvEmail(int distributorId) 
    throws RemoteException;
}
