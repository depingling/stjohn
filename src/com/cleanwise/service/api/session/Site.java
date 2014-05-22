package com.cleanwise.service.api.session;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.cleanwise.service.api.dto.LocationSearchDto;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.DuplicateNameException;
import com.cleanwise.service.api.util.QueryRequest;
import com.cleanwise.service.api.value.AccCategoryToCostCenterView;
import com.cleanwise.service.api.value.AddressData;
import com.cleanwise.service.api.value.BudgetSpendView;
import com.cleanwise.service.api.value.BudgetSpendViewVector;
import com.cleanwise.service.api.value.BusEntityDataVector;
import com.cleanwise.service.api.value.BusEntitySearchCriteria;
import com.cleanwise.service.api.value.CopySiteRequest;
import com.cleanwise.service.api.value.CostCenterData;
import com.cleanwise.service.api.value.CostCenterDataVector;
import com.cleanwise.service.api.value.CustomerOrderRequestData;
import com.cleanwise.service.api.value.DistributorData;
import com.cleanwise.service.api.value.DistributorDataVector;
import com.cleanwise.service.api.value.ErrorHolderViewVector;
import com.cleanwise.service.api.value.Fedstrip058Data;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.NscSiteViewVector;
import com.cleanwise.service.api.value.PairViewVector;
import com.cleanwise.service.api.value.ProcessOrderResultData;
import com.cleanwise.service.api.value.ProductDataVector;
import com.cleanwise.service.api.value.PropertyDataVector;
import com.cleanwise.service.api.value.ScheduleOrderDates;
import com.cleanwise.service.api.value.ShoppingCartData;
import com.cleanwise.service.api.value.ShoppingControlData;
import com.cleanwise.service.api.value.ShoppingControlDataVector;
import com.cleanwise.service.api.value.ShoppingRestrictionsViewVector;
import com.cleanwise.service.api.value.SiteData;
import com.cleanwise.service.api.value.SiteDataVector;
import com.cleanwise.service.api.value.SiteDeliveryDataVector;
import com.cleanwise.service.api.value.SiteInventoryConfigViewVector;
import com.cleanwise.service.api.value.SiteLedgerDataVector;
import com.cleanwise.service.api.value.SiteViewVector;
import com.cleanwise.service.api.value.UserData;


/**
 *  Remote interface for the <code>Site</code> stateless session bean.
 *
 *@author     <a href="mailto:tbesser@cleanwise.com"></a>
 *@created    October 29, 2001
 */
public interface Site
    extends javax.ejb.EJBObject {





  /**
   *  Flag indicating that name of site should exactly match given string
   */
  public final static int EXACT_MATCH = 10000;

  /**
   *  Flag indicating that name of site should match beginning of given string
   */
  public final static int BEGINS_WITH = 10001;

  /**
   *  Flag indicating that name of site should contain given string
   */
  public final static int CONTAINS = 10002;

  /**
   *  Flag indicating that name of site should exactly match, ignoring case,
   *  given string
   */
  public final static int EXACT_MATCH_IGNORE_CASE = 10003;

  /**
   *  Flag indicating that name of site should match, ignoring case, beginning
   *  given string
   */
  public final static int BEGINS_WITH_IGNORE_CASE = 10004;

  /**
   *  Flag indicating that name of site should contain, ignoring case, given
   *  string
   */
  public final static int CONTAINS_IGNORE_CASE = 10005;

  /**
   *  Flag indicating that returned vector of sites should be ordered by ids
   */
  public final static int ORDER_BY_ID = 10006;

  /**
   *  Flag indicating that returned vector of sites should be ordered by site
   *  name.
   */
  public final static int ORDER_BY_NAME = 10007;

  /**
   *  Flag indicating that returned vector of sites should be ordered by site
   *  status.
   */
  public final static int ORDER_BY_STATUS = 10008;

  /**
   *  Flag indicating that returned vector of sites should be ordered by
   *  site's account name.
   */
  public final static int ORDER_BY_ACCOUNT_NAME = 10009;

  /**
   *  Gets the site information values to be used by the request.  Does not include
   *  inactive sites.
   *
   *@param  pSiteId                    the site identifier.
   *@param  pAccountId                 the id of the site account. If nonzero
   *      will only return an Site that belongs to that account. i.e. if the
   *      Site belongs to a different account, it won't be returned
   *@return                            SiteData
   *@exception  DataNotFoundException  Description of Exception
   *@throws  RemoteException           Required by EJB 1.0
   *      DataNotFoundException if site with pSiteId doesn't exist
   */
  public SiteData getSite(int pSiteId, int pAccountId) throws RemoteException,
      DataNotFoundException;


  /**
   *  Gets the site information values to be used by the request.
   *
   *@param  pSiteId                    the site identifier.
   *@param  pAccountId                 the id of the site account. If nonzero
   *      will only return an Site that belongs to that account. i.e. if the
   *      Site belongs to a different account, it won't be returned
   *@param pShowInactiveFl             Will return data even if the site is inactive
   *@return                            SiteData
   *@exception  DataNotFoundException  Description of Exception
   *@throws  RemoteException           Required by EJB 1.0
   *      DataNotFoundException if site with pSiteId doesn't exist
   */
  public SiteData getSite(int pSiteId, int pAccountId, boolean pShowInactiveFl) throws RemoteException,
      DataNotFoundException;
  public SiteData getSite(int pSiteId, int pAccountId, boolean pShowInactiveFl, AccCategoryToCostCenterView pCategToCostCenterView) throws RemoteException,
      DataNotFoundException;
  
  /**
   * Gets the side information with budgets by given parameters.
   * @param pSiteId 					- the site identifier.
   * @param pAccountId				- the id of the site account. If nonzero
   *      							  will only return an Site that belongs to that account. i.e. if the
   *      							  Site belongs to a different account, it won't be returned
   * @param pShowInactiveFl			- Will return data even if the site is inactive
   * @param pCategToCostCenterView	- Category To Cost Center View
   * @param budgetYear				- Budget year is used to get the the site budgets details by specified year. 
   * 									  If the budgetYear value is -100 then site budgets will be fetched based on current
   * 									  fiscal year.
   * @return
   * @throws RemoteException			- If any error occurs.
   * @throws DataNotFoundException	- If any error occurs.
   */
  public SiteData getSiteByBudgetYear(int pSiteId, int pAccountId, boolean pShowInactiveFl, AccCategoryToCostCenterView  pCategToCostCenterView,int pBudgetYear)
		throws RemoteException, DataNotFoundException ;

  /**
   *  Gets the site information.
   *
   *@param  pSiteId                    the site identifier.
   *@return                            SiteData
   *@exception  DataNotFoundException  Description of Exception
   *@throws  RemoteException           Required by EJB 1.0
   *      DataNotFoundException if site with pSiteId doesn't exist
   */
  public SiteData getSite(int pSiteId) throws RemoteException,
      DataNotFoundException;

  /**
   *  Get all the sites or all sites for a given account.
   *
   *@param  pAccountId           the Id of the sites account. If zero, all
   *      sites without regard to the account, will be returned
   *@param  pOrder               one of ORDER_BY_ID, ORDER_BY_NAME
   *@return                      a <code>SiteDataVector</code> with all sites.
   *@exception  RemoteException  if an error occurs
   */
  public SiteDataVector getAllSites(int pAccountId, int pOrder) throws
      RemoteException;
  public SiteDataVector getAllSites(int pAccountId, int pOrder, AccCategoryToCostCenterView pCategToCostCenterView) throws
      RemoteException;

  public IdVector getAllSiteIds(int pAccountId)throws RemoteException;
  /**
   *  Get all sites that match the given name. The arguments specify whether
   *  the name is interpreted as a pattern or exact match.
   *
   *@param  pName                a <code>String</code> value with site name or
   *      pattern
   *@param  pAccountId           the id of the site account. If nonzero will
   *      only return a Sites that belongs to that account. Otherwise will
   *      return all matching Sites.
   *@param  pMatch               one of EXACT_MATCH, BEGINS_WITH, CONTAINS,
   *      EXACT_MATCH_IGNORE_CASE, BEGINS_WITH_IGNORE_CASE and
   *      CONTAINS_IGNORE_CASE.
   *@param  pOrder               one of ORDER_BY_ID, ORDER_BY_NAME
   *@return                      a <code>SiteDataVector</code> of matching
   *      sites
   *@exception  RemoteException  if an error occurs
   */
  public SiteDataVector getSiteByName(String pName, int pAccountId,
                                      int pMatch, int pOrder) throws
      RemoteException;
  public SiteDataVector getSiteByName(String pName, int pAccountId,
                                      int pMatch, int pOrder,
                                      AccCategoryToCostCenterView pCategToCostCenterView) throws
      RemoteException;

  /** DEPRECATED
   *  Gets all sites that match the given name. The arguments specify whether
   *  the name is interpreted as a pattern or exact match.
   *
   *@param  pName                a <code>String</code> value with site name or
   *      pattern
   *@param  pAccountId           the id of the site account. Will not apply if zero or less
   *@param  pStoreId             the id of the site store. Will not apply if zero or less
   *@param  pGetInactive         filters out inactive sites if false
   *@param  pMatch               one of EXACT_MATCH, BEGINS_WITH,
   *      EXACT_MATCH_IGNORE_CASE, BEGINS_WITH_IGNORE_CASE
   *@param  pOrder               one of ORDER_BY_ID, ORDER_BY_NAME
   *@return                      a <code>SiteDataVector</code> of matching
   *      sites
   *@exception  RemoteException  if an error occurs
   */
  public SiteViewVector getSiteByNameDeprecated(String pName, int pAccountId,
                                      int pStoreId,
                                      boolean pGetInactive, int pMatch,
                                      int pOrder) throws RemoteException;

  /**
   *  Gets all sites that match the given site property.
   *
   *@param  pName                property Short_desc value
   *@param  pAccountId           the id of the site account
   *@return                      a set of SiteData objects ordered by ids
   *      sites
   *@exception  RemoteException  if an error occurs
   */
  public SiteDataVector getSiteByProperty(String pName, String pValue,
                                          int pAccountId) throws
      RemoteException;
  public SiteDataVector getSiteByProperty(String pName, String pValue,
                                          int pAccountId, AccCategoryToCostCenterView pCategToCostCenterView) throws
      RemoteException;

  /**
   *  Gets all sites that match the given site property.
   *
   *@param  pName                property Short_desc value
   *@param  pAccountId           the id of the site account
   *@return                      a set of SiteData objects ordered by ids
   *      sites
   *@exception  RemoteException  if an error occurs
   */
  public SiteDataVector getSiteByPropertyForStore(String pName, String pValue, int pStoreId)
      throws RemoteException ;
  public SiteDataVector getSiteByPropertyForStore(String pName, String pValue, int pStoreId, AccCategoryToCostCenterView pCategToCostCenterView)
      throws RemoteException ;

  /**
   *  Get all sites that match the given address.
   *
   *@param pAccountId the id of the site account. If nonzero will
   *      only return a Sites that belongs to that account. Otherwise will
   *      return all matching Sites.
   *@param pOrder one of ORDER_BY_ID, ORDER_BY_NAME
   *@return a <code>SiteDataVector</code> of matching sites
   *@exception  RemoteException  if an error occurs
   */
  public SiteDataVector getSitesByAddress(AddressData pAddress, int pAccountId,
                                          int pOrder) throws RemoteException;
  public SiteDataVector getSitesByAddress(AddressData pAddress, int pAccountId,
                                          int pOrder, AccCategoryToCostCenterView pCategToCostCenterView) throws RemoteException;

  /**
   *  Describe <code>addSite</code> method here.
   *
   *@param  pSiteData                   a <code>SiteData</code> value
   *@param  pAccountId                  the id of the site account.
   *@return                             an <code>SiteData</code> value
   *@exception  RemoteException         if an error occurs
   *@exception  DuplicateNameException  Description of Exception
   */
  public SiteData addSite(SiteData pSiteData, int pAccountId) throws
      RemoteException, DuplicateNameException;

  public SiteData updateSiteAddress(SiteData pSiteData) throws RemoteException;

  /**
   *  Updates the site information values to be used by the request.
   *
   *@param  pSiteData                   Description of Parameter
   *@return                             an <code>SiteData</code> value
   *@exception  DuplicateNameException  Description of Exception
   *@throws  RemoteException            Required by EJB 1.0
   */
  public SiteData updateSite(SiteData pSiteData) throws RemoteException,
      DuplicateNameException;

  /**
   *  <code>removeSite</code> may be used to remove an 'unused' site. An
   *  unused site is a site with no database references other than the default
   *  primary address, phone numbers, email addresses and properties.
   *  Attempting to remove a site that is used will result in a failure
   *  initially reported as a SQLException and consequently caught and
   *  rethrown as a RemoteException.
   *
   *@param  pSiteData            a <code>SiteData</code> value
   *@exception  RemoteException  if an error occurs
   */
  public void removeSite(SiteData pSiteData) throws RemoteException;

  /**
   *  Find all sites related to this order guide.  The sites listed
   *  are limited to the sites configured for a user.  This
   *  relationship is rather complex due to the default account
   *  catalog relationship.
   *
   *@param  pOrderGuideId
   *@param  pUserId, limit the sites displayed to those
   *  configured for this user.
   *@return                      Description of the Returned Value
   *@exception RemoteException Description of Exception
   */
  public SiteDataVector fetchSitesForOrderGuide(int pOrderGuideId,
                                                int pUserId) throws
      RemoteException;
  public SiteDataVector fetchSitesForOrderGuide(int pOrderGuideId,
                                                int pUserId,AccCategoryToCostCenterView pCategToCostCenterView) throws
      RemoteException;

  public SiteDataVector fetchSitesForWorkflow(int pWorkflowId) throws
      RemoteException;
  public SiteDataVector fetchSitesForWorkflow(int pWorkflowId, AccCategoryToCostCenterView pCategToCostCenterView) throws
       RemoteException;

  public BudgetSpendViewVector getAllBudgetSpent2(int pSiteId) throws
      RemoteException;

  /**
   *Calculates the budget spent for the supplied site.  This depends greatly
   *upon the account configuration and the budget configuration.  If the budget is
   *setup at the account level then this is taken into account for example.  The
   *design is that the caller does not need to know this, but in the context of the
   *current site this will return the amount spent against the budget.
   */
  public BudgetSpendView getBudgetSpent(int pSiteId, int pCostCenterId) throws
      RemoteException;

  /**
   * <code>getSiteCollection</code> returns a vector of SiteView's
   * meeting the criteria of the QueryRequest
   *
   * @param req a <code>QueryRequest</code> filtering on any of:
   * SITE_NAME, SITE_ID, ACCOUNT_NAME, ACCOUNT_ID, CITY, STATE
   * @return a <code>SiteViewVector</code> value
   * @exception RemoteException if an error occurs
   */
  public SiteViewVector getSiteCollection(QueryRequest req) throws
      RemoteException, SQLException ;

  /**
   *  <code>getSiteCollection</code> returns a vector of SiteView's for the user
   *  and site filter
   *
   *@param  pUserId user id
   *@param  pSiteId site id (active if > 0)
   *@param  pNameTempl  site name (active if the parameter has some value)
   *@param  pNameBeginsFl affects search
   *  If it is true site name should start with pNameTeml
   *  It it is false site name should contain pNameTempl
   *@param  pCityTempl city name (active if it has some vale).
   * City of the sites should start wite pCityTempl
   *@param pState site state (active if it has some value)
   *@param pAccountIds list of accout id (active if it is not null)
   *@param pGetInactiveFl ignores inactive sites if true;
   *@param pResultLimit limits number of sites (if >0)
   *@return      a <code>SiteViewVector</code> value
   *@exception  RemoteException  if an error occurs
   */
  public SiteViewVector getUserSites(int pStoreId, int pUserId, int pSiteId,
                                     String pNameTempl,boolean pNameBeginsFl,
                                     String pRefNum, boolean pRefNumNameBeginsF1,
                                     String pCityTempl,
                                     String pState,
                                     IdVector pAccountIds,
                                     boolean pGetIncativeFl, int pResultLimit) throws
      RemoteException;

  public void saveSiteFields(int pSiteId, PropertyDataVector pFieldProps) throws
      RemoteException;

  /**
   *  Gets the site based on the account erp number and the site Name
   *
   *@param  pSiteData                   Description of Parameter
   *@return                             an <code>SiteData</code> value
   *@exception  DuplicateNameException  Description of Exception
   *@throws  RemoteException            Required by EJB 1.0
   */
  public SiteData getSiteByAcctErp(String pAccountErpNum, String pSiteName) throws
      RemoteException, DataNotFoundException,
      DuplicateNameException;

  public BudgetSpendView getBudgetSpendView(int pSiteId) throws  RemoteException;

  public java.util.ArrayList getOrderReport
      (String pIdType,
       int pId, int pBudgetYear,
       int pBudgetPeriod) throws RemoteException;

  public java.util.ArrayList getAccountReportPeriods(int pAccountId) throws
      RemoteException;

  public SiteInventoryConfigViewVector
      updateInventory(SiteInventoryConfigViewVector v,
                      UserData pUser) throws RemoteException;
  public SiteInventoryConfigViewVector
      updateInventory(SiteInventoryConfigViewVector v,
                      UserData pUser, AccCategoryToCostCenterView pCategToCostCenterView) throws RemoteException;

  public SiteInventoryConfigViewVector
      updateAndResetInventory(SiteInventoryConfigViewVector v,
                              UserData pUser) throws RemoteException;
  public SiteInventoryConfigViewVector
      updateAndResetInventory(SiteInventoryConfigViewVector v,
                              UserData pUser, AccCategoryToCostCenterView pCategToCostCenterView) throws RemoteException;

  public SiteInventoryConfigViewVector
      updateInventoryConfig(SiteInventoryConfigViewVector v) throws
      RemoteException;
  public SiteInventoryConfigViewVector
      updateInventoryConfig(SiteInventoryConfigViewVector v,  AccCategoryToCostCenterView pCategToCostCenterView) throws
      RemoteException;

  /**
   *  Gets a set of cutoff and delivery pairs for the site
   *
   *@param  pSiteId           Site indentifier
   *@param  pAccountId        Account indentifier
   *@param  pBegDate          the start month of the interval
   *@param  pEndDate          the end month of the interval
   *@return                   a set of ScheduleOrderDates
   *@throws  RemoteException
   */

  public ArrayList getOrderScheduleDates(int pSiteId, int pAccountId,
                                         Date pBegDate, Date pEndDate) throws
      RemoteException;

  //******************************************************************************
   /**
    *  Gets a set of cutoff and delivery pairs for the site.
    * It doesn't throw Exception when error found but puts error message as first element of reurned list.
    *
    *@param  pSiteId           Site identifier
    *@param  pAccountId        Account identifier
    *@param  pBegDate          the start month of the interval
    *@param  pEndDate          the end month of the interval
    *@return                   a set of ScheduleOrderDates
    *@throws  RemoteException
    */

   public ArrayList getOrderScheduleDatesNoExc(int pSiteId, int pAccountId,
                                               Date pBegDate, Date pEndDate) throws
       RemoteException;

  /**
   *Returns all of the distributors that are servicing this site.
   *@param pSiteId the id of the site that we want to find rlated distributors
   *@returns a populated DistributorDataVector
   *@throws RemoteException if an error occurs
   */
  public DistributorDataVector getAllDistributorsForSite(int pSiteId) throws
      RemoteException, DataNotFoundException;

  /**
   *  Gets the major distributor for the site
   *
   *@param  pSite      the Site id
   *@return  DistributorData object
   *@throws RemoteException
   */
  public DistributorData getMajorSiteDist(int pSiteId) throws RemoteException;

  /**
   *  Removes records from clw_fedstrip058 placed by the USPS fedstrip
   *  file processing
   *
   *@param  pFileName the fedstrip file name
   *@exception  RemoteException  if an error occurs
   */
  public void cleanFedstrip058(String pFileName) throws RemoteException;

  /**
   *  Adds record to clw_fedstrip058 also checks errors
   *
   *@param  pFileName the fedstrip file name
   *@return source Fedstrip058Data object with id and error message
   *@exception  RemoteException  if an error occurs
   */
  public Fedstrip058Data addFedstrip058(Fedstrip058Data pFedstrip, String pUser) throws
      RemoteException;

  public SiteInventoryConfigViewVector
      lookupInventoryConfig(int pSiteId) throws RemoteException;

  public SiteInventoryConfigViewVector
  	  lookupInventoryConfig(int pSiteId, boolean inCatalog) throws RemoteException;

  public SiteInventoryConfigViewVector
          lookupInventoryConfig(int pSiteId, boolean inCatalog, AccCategoryToCostCenterView pCategToCostCenterView ) throws RemoteException;

  public void storeInventoryConfig(SiteInventoryConfigViewVector v) throws RemoteException;

  public IdVector getInventorySiteCollection() throws RemoteException;

  public String placeInventoryOrder(int pSiteId, Date pCutoffDate) throws
      RemoteException, DataNotFoundException;

  /**
   *Gets a list of SiteData objects based off the supplied search criteria object
   *@param BusEntitySearchCriteria the search criteria
   *@return                      a set of SiteData objects
   *@exception  RemoteException  if an error occurs
   */
  public SiteDataVector getSitesByCriteria(BusEntitySearchCriteria pCrit) throws
      RemoteException;
  public SiteDataVector getSitesByCriteria(BusEntitySearchCriteria pCrit, AccCategoryToCostCenterView pCategToCostCenterView) throws
      RemoteException;

  /**
   * Retrieves a list of SiteData objects based off the supplied search criteria object and sets the
   * list of matching SiteData objects on the searchCriteria
   * @param 		LocationSearchDto searchCriteria the search criteria
   * @exception     RemoteException  if an error occurs
   */
  public void getLocationsByCriteria(LocationSearchDto searchCriteria) throws
      RemoteException;

  /**
   * Retrieves a list of SiteData objects based off the supplied search criteria object and sets the
   * list of matching SiteData objects on the searchCriteria
   * @param 		LocationSearchDto searchCriteria the search criteria
   * @param 		int maxLocations the maximum number of locations to return
   * @exception     RemoteException  if an error occurs
   */
  public void getLocationsByCriteria(LocationSearchDto searchCriteria, int maxLocations) throws
      RemoteException;

  /**
   *  Gets a list of SiteData objects related to users accounts based off the supplied search criteria
   *  object
   *
   *@param  pCrit                   Description of the Parameter
   *@param pUserAssignedFl          picks sites assinged to the users if true;
   *@return                          a set of SiteData objects
   *@exception  RemoteException      if an error occurs
   */
  public SiteDataVector getUserAccountSitesByCriteria(BusEntitySearchCriteria
      pCrit, boolean pUserAssignedFl) throws RemoteException;

  public ShoppingControlDataVector updateShoppingControls
      (ShoppingControlDataVector pShopCtrlv, boolean returnUpdated) throws RemoteException;

  public void updateShoppingControl(ShoppingControlData scd)
  throws RemoteException;
  
  public void updateShoppingControlNewXpedx(ShoppingControlData scd) 
  throws RemoteException;

  public void deleteShoppingControl(int pSiteId, int pItemId)
  throws RemoteException;
  
  public void deleteShoppingControlNewXpedx(int pSiteId, int pItemId) 
  throws RemoteException;
  
  public void deleteAllSiteControlsForItem(int pAcctId, int pItemId) 
  throws RemoteException;

  public String getSiteNameById(int pSiteId) throws RemoteException;

  /**
   *  Gets a list of shopping controls related to the site
   *
   *@param  pSiteId
   *@param pAccountId
   *@return                          ShoppingControlDataVector
   *@exception  RemoteException      if an error occurs
   */
  public ShoppingControlDataVector getSiteShoppingControls(int pSiteId,
      int pAccountId) throws RemoteException;

  /**
   *fetches the sites identified by the supplied site ids
   *@param IdVector pSite ids
   *@returns the populated SiteDataVector
   *@throws DataNotFoundException if ANY of the sites in the id vector are not found
   *@exception  RemoteException  if an error occurs*
   */
  public SiteDataVector getSiteCollection(IdVector pSiteIds) throws
      DataNotFoundException, RemoteException;
  public SiteDataVector getSiteCollection(IdVector pSiteIds, AccCategoryToCostCenterView pCategToCostCenterView) throws
      DataNotFoundException, RemoteException;

  // Clone a site
  public SiteData copySite(CopySiteRequest pCopySiteReq) throws
      DataNotFoundException, RemoteException;
  public SiteData copySite(CopySiteRequest pCopySiteReq, AccCategoryToCostCenterView pCategToCostCenterView) throws
      DataNotFoundException, RemoteException;

  public SiteLedgerDataVector getSiteLedgerCollection(int siteId,
      int costCenterId) throws DataNotFoundException, RemoteException;

  public SiteLedgerDataVector getValidSiteLedgerCollection(int siteId,
      int costCenterId) throws DataNotFoundException, RemoteException;

  public SiteLedgerDataVector getSiteLedgerCollection(int siteId) throws
      DataNotFoundException, RemoteException;

  public void adjustSiteLedgerCollection(SiteLedgerDataVector data) throws
      RemoteException;

  public void updateSiteLedgerCollection(SiteLedgerDataVector data) throws
      RemoteException;

  public IdVector getSiteIdsOnlyForAccount(int busEntityId) throws
      RemoteException;

  /**
   * Gets the site information values to be used by the request.
   * @param pSiteId an <code>int</code> value
   * @param pStoreIds an <code>IdVector</code> value
   * @param pInactiveFl an <code>boolean</code> value
   * @return SiteData
   * @exception RemoteException Required by EJB 1.0
   * DataNotFoundException if site with pSiteId
   * doesn't exist
   * @exception DataNotFoundException if an error occurs
   */
  public SiteData getSiteForStore(int pSiteId, IdVector pStoreIds,
                                  boolean pInactiveFl) throws RemoteException,
      DataNotFoundException;
  public SiteData getSiteForStore(int pSiteId, IdVector pStoreIds,
                                  boolean pInactiveFl, AccCategoryToCostCenterView pCategToCostCenterView)
          throws RemoteException, DataNotFoundException ;

  /**
   *  Gets next delivery date for the site
   *@param  pSiteId  Site Id
   *@param  pAccountId Account Id or 0 (will be determined from DB)
   *@param  pMaxAge  Days for cached data (0 if disregard cache)
   *@return          Next cutoff and delivery dates
   */
  public ScheduleOrderDates calculateNextOrderDates(int pSiteId, int pAccountId,
      int pMaxAge) throws RemoteException;

  /**
   *  Returns site inventory level info
   *
   *@param  pSiteId  Site Id
   *@return       set of SiteInventoryConfigView objects
   */
  public SiteInventoryConfigViewVector lookupSiteInventory(int pSiteId) throws
      RemoteException;
  public SiteInventoryConfigViewVector lookupSiteInventory(int pSiteId, AccCategoryToCostCenterView pCategToCostCenterView) throws
      RemoteException;

  public SiteInventoryConfigViewVector lookupSiteInventory(int pSiteId, ProductDataVector productDV, AccCategoryToCostCenterView pCategToCostCenterView) throws
  RemoteException;
  /**
   * gets tax rate
   *
   * @param siteId site id
   * @return tax rate
   * @throws RemoteException if an errors
   */
  public BigDecimal getTaxRate(int siteId) throws RemoteException;

  public String placeScheduledOrder(int pSiteId, Date pRunForDate,
                                    Date startDate,
                                    boolean calculateNextOrderDatesFl) throws
      RemoteException;
  public IdVector getInventorySiteCollection(boolean invShoppingModernTypeFl) throws
      RemoteException;
  public IdVector getCorpInventorySiteCollection() throws RemoteException;
  
  public SiteInventoryConfigViewVector updateInventoryConfigMod(int siteId,
      SiteInventoryConfigViewVector v, boolean pUpdateFromPhysCart) throws RemoteException;

  public SiteInventoryConfigViewVector updateInventoryConfigMod(int siteId,
      SiteInventoryConfigViewVector v, boolean pUpdateFromPhysCart,  AccCategoryToCostCenterView pCategToCostCenterView) throws RemoteException;

  public ProcessOrderResultData inventoryEarlyRelease(SiteData site,
      UserData user, ShoppingCartData cart,
      CustomerOrderRequestData orderRequest) throws RemoteException;
  public ProcessOrderResultData inventoryEarlyRelease(SiteData site,
      UserData user, ShoppingCartData cart,
      CustomerOrderRequestData orderRequest, AccCategoryToCostCenterView pCategToCostCenterView) throws RemoteException;

  public CustomerOrderRequestData constructOrderRequest(SiteData siteData,
      ShoppingCartData cartData, UserData user) throws RemoteException;

  public int getSiteIdByOrderId(int orderId) throws RemoteException;

  public BusEntityDataVector getSiteByErpNum(int accountId, String siteNumber) throws
      RemoteException;

  /**
   *  Calculates pairs cutoff and delivery dates to sites of one account
   *
   *@param  pSiteIds                 set of site ids or null if whole account
   *@param  pAccountId               account id
   *@return HashMap object. Keys are siteIds. Values are ScheduleOrderDates objects
   *@exception  RemoteException        if an error occurs
   */

  public HashMap calculateNextOrderDates(IdVector pSiteIds, int pAccountId) throws
      RemoteException;

  /**
   *Returns the catalog id for the supplied site.  If 0 or more than 1 exist an exception is throw (0 throws a DataNotFoundException).
   */
  public int getShoppigCatalogIdForSite(int pSiteId) throws RemoteException,
      DataNotFoundException;

  public IdVector getSiteIdsByProperty(String pName, String pValue,
                                       int pAccountId) throws RemoteException;

  public IdVector getActiveSiteIdsByProperty(String pName, String pValue,
          int pAccountId) throws RemoteException;

  public SiteDataVector addSites(SiteDataVector sites) throws RemoteException,
      DuplicateNameException;

  public SiteDeliveryDataVector getSiteDeliveryDateCollection(int siteId) throws
      DataNotFoundException, RemoteException;
  public SiteDeliveryDataVector getNextSiteDeliveryData(int siteId) throws RemoteException;

    /**
     * Adding(updating) distributor association for site.
     */
  public int addDistributorAssoc(int siteId, int distId, String userName,
          ErrorHolderViewVector errorHolder) throws RemoteException;

    public void addDeliveryDateForSite(String deliveryDate, int pOrderId, String pUserName )  throws RemoteException;

 public SiteViewVector getSiteCollectionByDistributor(String pName, int pDistId, int pMatch) throws RemoteException;
 public SiteViewVector getSiteCollectionByDistributor(QueryRequest pQueryRequest, int pDistId) throws RemoteException;
 public ErrorHolderViewVector removeDistributorAssoc(int siteId, int distId, String userName) throws RemoteException;

 public SiteViewVector getSiteCollectionByServiceProvider(QueryRequest pQueryRequest, int pSpId) throws RemoteException;

 /**
  * Get all states supported for sites of this user.
  *
  * @param the user id
  * @return set of string objects
  * @throws RemoteException Required by EJB 1.0
  */
 public List getSiteStatesForUserDesc(int userId) throws RemoteException ;

  /**
   * Get all states supported for sites.
   *
   * @param siteIds site ids
   * @return set of string objects
   * @throws RemoteException Required by EJB 1.0
   */
 public List getSiteStates(IdVector siteIds) throws RemoteException;

 public SiteData updateBudgetSpendingInfo(SiteData siteD) throws RemoteException;

 public HashMap getAcrossAccountsSiteRefNumberMap(int storeId, int userId, List custMajSiteRefNums) throws RemoteException;

 public HashMap getAcrossAccountsSiteRefNumberMapConfigOnly(int storeId,int userId) throws RemoteException;

 public NscSiteViewVector getAllNscSites(int accountId, String custMaj) throws RemoteException;
 
 public void updateNscSites(NscSiteViewVector nscSites, String user) throws RemoteException;

 public PairViewVector getSiteIdAndName(int pUserId,
                                   int pSiteId,
                                   String pCountry,
                                   String pState,
                                   String pStatus,
                                   int pMaxRows) throws RemoteException;

 public ShoppingRestrictionsViewVector getShoppingRestrictionsByAccountOnly(
	        int accountId) throws RemoteException ;

 public ShoppingRestrictionsViewVector getShoppingRestrictionsByAccountAndItem(
            int accountId, int itemId) throws RemoteException;
 
 public ShoppingControlDataVector getSiteShoppingControlsForItem(IdVector pSites, int pItemId)
 throws RemoteException;

    public ShoppingRestrictionsViewVector getAllShoppingRestrictions(
        int accountId, int catalogId, int siteId) throws RemoteException;

    public ShoppingRestrictionsViewVector getAllShoppingRestrictions(
    		int accountId, int catalogId, int siteId, AccCategoryToCostCenterView pCategToCostCenterView) 
    throws RemoteException;
        
    public ShoppingRestrictionsViewVector getShoppingRestrictions(
        int accountId, int catalogId, int siteId) throws RemoteException;

    public ShoppingRestrictionsViewVector getShoppingRestrictions(
    		int accountId, int catalogId, int siteId, AccCategoryToCostCenterView pCategToCostCenterView) 
    throws RemoteException;
    
    public void updateShoppingRestrictions(
        ShoppingRestrictionsViewVector shoppingRestrictions) throws RemoteException;

 public SiteViewVector getUserServiceProviderSites( int pUserId,
                                                    int pStoreId,
                                                    String pSearchField, String pSearchType,
                                                    String pRefNum, boolean pRefNumNameBeginsFl,
                                                    String pCity,
                                                    String pState,
                                                    boolean pGetInactiveFl,
                                                    int pResultLimit) throws RemoteException;

    /**
     * Return date for cutoff remainder.
     */
    public GregorianCalendar getRemCutoffCalendar(int pAccountId,
        Date nextOrdercutoffDate, Date nextOrdercutoffTime) throws RemoteException;

    /**
     * Sends emails to notify about missing of inventory items.
     */
    public void sendInventoryMissingEmail(int siteId, Date runDate, Date startDate)
        throws RemoteException;

    public SiteDataVector getSitesForAccount(QueryRequest qr)
                 throws RemoteException;

    /**
     * Gets all the sites of an account for specified fiscal year. 
     * @param qr				-	Conditions that are passed to query.
     * @param pAccountId		-	Account Id.
     * @param pBudgetYear		-	Budget Year.
     * @return SiteDataVector	- 	List of all sites.
     * @throws RemoteException	-	If an error occurs.
     */
    public SiteDataVector getSitesForAccountWithBudgets(QueryRequest qr, int pAccountId, int pBudgetYear)
                 throws RemoteException;

    /**
     * Returns true if corporate emails should be sent
     */
    public boolean getSendCorporateEmail(int pSiteId, int pAcctId,
    		Date runDate, Date startDate) throws RemoteException;

    public IdVector getSiteIdsByCriteria(BusEntitySearchCriteria pCrit) throws RemoteException;

    public Map<Integer, Set<Integer>> getSitesForItems(IdVector pItemIds,
            IdVector pSiteIds) throws RemoteException;
    public void populateNscSiteProperties( NscSiteViewVector nscSites) throws RemoteException;

    public AddressData getShipToAddress(int siteId) throws DataNotFoundException, RemoteException;
    
    public ShoppingControlDataVector lookupSiteShoppingControls2(int pSiteId, int pAccountId)
    
    throws RemoteException;
    
    public ShoppingControlDataVector lookupSiteShoppingControlsNew(int pSiteId, int pAccountId)
    
    throws RemoteException;
    
    public ShoppingControlDataVector lookupSiteShoppingControlsAcctAdmPortal(int pSiteId, int pAccountId)
    
    throws RemoteException;    
    
    public ShoppingControlDataVector lookupSiteShoppingControlsByItemId(int accountId, int itemId)
	throws RemoteException; 
    
    /*
     * Returns only Site Ctrls for the account, site. 
     * Filter the item with item id list if itemIds is not empty
     *
     */
    public ShoppingControlDataVector lookupSiteShoppingControlsAcctAdmPortal(IdVector siteIds, IdVector itemIds)    
    throws RemoteException;
    
  //Location Budget
    /**
     * getCurrentPeriodAmount returns the Budget's current period amount,
     * to find whether an account has unlimited budget or not
     * @param accountId      - Account Id
     * @param siteId         -  Site Id   
     * @param costCenterId   -  Cost Center Id.
     * @param currentPeriod     - current period 
     * @return BigDecimal value  -  current period amount.
     * @throws RemoteException  -	If an error occurs.
     */
    public BigDecimal getCurrentPeriodAmount(int accountId,int siteId,int costCenterId,int currentPeriod)
    throws RemoteException;
    
    public BusEntityDataVector getSiteBusEntityByCriteria(DBCriteria crit) throws
    RemoteException;
    
    public CostCenterDataVector getCostCentersForSite(int pSiteId) throws Exception, RemoteException;
    public BudgetSpendView getBudgetSpent(int pSiteId, CostCenterData pCostCenterId, int pPeriod, int pYear)
    throws RemoteException;
    
    public HashMap getCostCentersForSitesList(ArrayList siteIds) throws Exception, RemoteException;
    public ArrayList getCostCentersForSites(ArrayList siteIds) throws Exception, RemoteException;
    /**
     * Retrieves information for 1-n locations.
     * @param  pLocationIds  - a <code>IdVector</code> of location ids.
     * @return  SiteDataVector - a <code>SiteDataVector</code> containing the location information.
     * @throws  RemoteException, DataNotFoundException
     */
    public SiteDataVector getLocations(IdVector pLocationIds) throws RemoteException, DataNotFoundException;

    /**
     * Retrieves information for 1-n locations.
     * @param  pLocationIds  - a <code>IdVector</code> of location ids.
     * @param  pAccountIds  - a <code>IdVector</code> of account ids.
     * @return  SiteDataVector - a <code>SiteDataVector</code> containing the location information.
     * @throws  RemoteException, DataNotFoundException
     */
    public SiteDataVector getLocations(IdVector pLocationIds, IdVector pAccountIds) 
    			throws RemoteException, DataNotFoundException;

    /**
     * Retrieves information for 1-n locations.
     * @param  pLocationIds  - a <code>IdVector</code> of location ids.
     * @param  pAccountIds  - a <code>IdVector</code> of account ids.
     * @param  pShowInactive - a boolean indicating if inactive locations 
     * 			should be returned.
     * @return  SiteDataVector - a <code>SiteDataVector</code> containing the location information.
     * @throws  RemoteException, DataNotFoundException
     */
    public SiteDataVector getLocations(IdVector pLocationIds, IdVector pAccountIds, 
    		boolean pShowInactive) throws RemoteException, DataNotFoundException;

    /**
     * Retrieves information for 1-n locations.
     * @param  pSiteIds  - a <code>IdVector</code> of location ids.
     * @param  pAccountIds  - a <code>IdVector</code> of account ids.
     * @param  pShowInactive - a boolean indicating if inactive locations 
     * 			should be returned.
     * @param  budgetYears  - a <code>List</code> of <code>Integers</code> used to get the the location 
     * 			budgets details by specified year. 
     * @return  SiteDataVector - a <code>SiteDataVector</code> containing the location information.
     * @throws  RemoteException, DataNotFoundException
     */
    public SiteDataVector getLocationsByBudgetYear(IdVector pLocationIds, IdVector pAccountIds, 
    		boolean pShowInactive, List<Integer> pBudgetYears) throws RemoteException, DataNotFoundException;

    public ArrayList getOrderScheduleDatesForMainDistributor(int pSiteId, int pAccountId, Date pBegDate, Date pEndDate)
    throws RemoteException;
    
    public ShoppingControlDataVector getShoppingControls(int accountId, int siteId) throws RemoteException;

}
