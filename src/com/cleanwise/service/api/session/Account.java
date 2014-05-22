package com.cleanwise.service.api.session;

/**
 * Title:        Account
 * Description:  Remote Interface for Account Stateless Session Bean
 * Purpose: Provides access to the services for managing the account
 * information, properties and relationships.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 */

import javax.ejb.*;
import java.rmi.*;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.MultipleDataException;
import com.cleanwise.service.api.util.DuplicateNameException;

/**
 * Remote interface for the <code>Account</code> stateless session bean.
 *
 * @author <a href="mailto:tbesser@cleanwise.com"></a>
 */
public interface Account extends javax.ejb.EJBObject
{
    /**
     *  Flag indicating that name of account, cost center or site budget
     *  should exactly match given string
     */
    public final static int EXACT_MATCH = 10000;
    /**
     *  Flag indicating that name of account, cost center or site budget
     *  should match beginning of
     *  given string
     */
    public final static int BEGINS_WITH = 10001;
    /**
     *  Flag indicating that name of account, cost center or site budget
     *  should contain given string
     */
    public final static int CONTAINS = 10002;
    /**
     *  Flag indicating that name of account, cost center or site budget
     *  should exactly match, ignoring case, given string
     */
    public final static int EXACT_MATCH_IGNORE_CASE = 10003;
    /**
     *  Flag indicating that name of account, cost center or site budget
     *  should match, ignoring case, beginning given string
     */
    public final static int BEGINS_WITH_IGNORE_CASE = 10004;
    /**
     *  Flag indicating that name of account, cost center or site budget
     *  should contain, ignoring case, given string
     */
    public final static int CONTAINS_IGNORE_CASE = 10005;

    /**
     *  Flag indicating that returned vector of accounts, cost centers or
     *  site budgets should be ordered by ids
     */
    public final static int ORDER_BY_ID = 10006;
    /**
     *  Flag indicating that returned vector of accounts, cost centers or
     *  site budgets should be ordered by names
     */
    public final static int ORDER_BY_NAME = 10007;

    /**
     *Gets a list of Account type BusEntityData objects based off the supplied search criteria object
     *@param BusEntitySearchCriteria the search criteria
     *@return                      a set of AccountData objects
     *@exception  RemoteException  if an error occurs
     */
    public BusEntityDataVector getAccountBusEntByCriteria(BusEntitySearchCriteria pCrit)
    throws RemoteException;

    /**
     *Gets a list of AccountData objects based off the supplied search criteria object
     *@param BusEntitySearchCriteria the search criteria
     *@return                      a set of AccountData objects
     *@exception  RemoteException  if an error occurs
     */
    public AccountDataVector getAccountsByCriteria(BusEntitySearchCriteria pCrit)
             throws RemoteException;

   /**
    *Gets a list of AccountData objects based off the supplied search criteria object
    *@param BusEntitySearchCriteria the search criteria
    *@return                      a set of AccountData objects
    *@exception  RemoteException  if an error occurs
    */
     public AccountUIViewVector getAccountsUIByCriteria(BusEntitySearchCriteria pCrit)
     throws RemoteException ;

    /**
     *Gets a list of AccountView objects based off the supplied search criteria object
     *@param BusEntitySearchCriteria the search criteria
     *@return                      a set of AccountView objects
     *@exception  RemoteException  if an error occurs
     */
    public AccountViewVector getAccountsViewList (BusEntitySearchCriteria pCrit)
             throws RemoteException;

    /**
     * Gets the account information values to be used by the request.
     * @param pAccountId the account identifier.
     * @param pStoreId the id of the account store.  If nonzero will only
     *        return an Account that belongs to that store.  i.e. if the
     *        Account belongs to a different store, it won't be returned
     * @return AccountData
     * @throws RemoteException Required by EJB 1.0
     *         DataNotFoundException if account with pAccountId doesn't exist
     */
    public AccountData getAccount(int pAccountId, int pStoreId)
	throws RemoteException, DataNotFoundException;

    /**
     * Gets the lightweight account information values to be used by the request.
     * @param pAccountId the account identifier.
     * @return AccountData
     * @throws RemoteException Required by EJB 1.0
     *         DataNotFoundException if account with pAccountId doesn't exist
     */
    public BusEntityData getAccountBusEntity(int pAccountId)
	throws RemoteException, DataNotFoundException;

    /**
     * Describe <code>getAccountDetails</code> method here.
     *
     * @param pBusEntity a <code>BusEntityData</code> value
     * @return a <code>AccountData</code> value
     * @exception RemoteException if an error occurs
     */
    public AccountData getAccountDetails(BusEntityData pBusEntity)
	throws RemoteException;

    public AccountData getAccountForSite(int pSiteId)
	throws RemoteException, DataNotFoundException;

    /**
     * Returns Account Id for company code (assume unique)
     */

    public int getAccountIdForCompany(String companyNo) throws RemoteException,DataNotFoundException;

    public IdVector getAllShoppingCatalogsForAcct(int pAccountId) throws RemoteException;

    /**
     *Returns the Account Id for the account associated to this site
     *@param pSiteId
     */
    public int getAccountIdForSite(int pSiteId)
                                  throws RemoteException,DataNotFoundException;
    /**
     * Get all the accounts or all accounts for a given store.
     * @param pStoreId the Id of the accounts store.  If zero, all accounts
     *        without regard to the store, will be returned
     * @param pOrder one of ORDER_BY_ID, ORDER_BY_NAME
     * @return a <code>AccountDataVector</code> with all accounts.
     * @exception RemoteException if an error occurs
     */
    public AccountDataVector getAllAccounts(int pStoreId, int pOrder)
	throws RemoteException;

    /**
     * Get all accounts that match the given name.  The arguments specify
     * whether the name is interpreted as a pattern or exact match.
     *
     * @param pName a <code>String</code> value with account name or pattern
     * @param pStoreId the id of the account store.  If nonzero will only
     * return a Accounts that belongs to that store.  Otherwise will
     * return all matching Accounts.
     * @param pMatch one of EXACT_MATCH, BEGINS_WITH, CONTAINS,
     * EXACT_MATCH_IGNORE_CASE, BEGINS_WITH_IGNORE_CASE and
     * CONTAINS_IGNORE_CASE.
     * @param pOrder one of ORDER_BY_ID, ORDER_BY_NAME
     * @return a <code>AccountDataVector</code> of matching accounts
     * @exception RemoteException if an error occurs
     */
    public AccountDataVector getAccountByName(String pName, int pStoreId,
					      int pMatch, int pOrder)
	throws RemoteException;

    /**
     * Describe <code>addAccount</code> method here.
     *
     * @param pAccountData a <code>AccountData</code> value
     * @param pStoreId the id of the account store.
     * @return an <code>AccountData</code> value
     * @exception RemoteException if an error occurs
     */
    public AccountData addAccount(AccountData pAccountData, int pStoreId)
	throws RemoteException;

    /**
     * Updates the account information values to be used by the request.
     * @param pUpdateAccountData  the AccountData account data.
     * @return an <code>AccountData</code> value
     * @throws RemoteException Required by EJB 1.0
     */
    public AccountData updateAccount(AccountData pAccountData)
	throws RemoteException;

    /**
     * <code>removeAccount</code> may be used to remove an 'unused' account.
     * An unused account is a account with no database references other than
     * the default primary address, phone numbers, email addresses and
     * properties.  Attempting to remove a account that is used will
     * result in a failure initially reported as a SQLException and
     * consequently caught and rethrown as a RemoteException.
     *
     * @param pAccountData a <code>AccountData</code> value
     * @return none
     * @exception RemoteException if an error occurs
     */
    public void removeAccount(AccountData pAccountData)
	throws RemoteException;

    /**
     * Gets the CostCenter information values to be used by the request.
     * @param pCostCenterId the account identifier.
     * @param pAccountId the id of the cost center account.  If nonzero will
     *        only return a Cost Center that belongs to that account.  i.e.
     *        if the Cost Center belongs to a different account, it won't be
     *        returned
     * @param pAccountId the id of the CostCenter Account.
     * @return CostCenterData
     * @throws RemoteException Required by EJB 1.0
     * DataNotFoundException if CostCenter with pCostCenterId for the
     * given Account doesn't exist
     */
    public CostCenterData getCostCenter(int pCostCenterId, int pAccountId)
	throws RemoteException, DataNotFoundException;

    /**
     * Get all the CostCenters for a given account
     * @param pAccountId the Id of the CostCenter's Account.
     * @param pOrder one of ORDER_BY_ID, ORDER_BY_NAME
     * @return a <code>CostCenterDataVector</code> with all CostCenters.
     * @exception RemoteException if an error occurs
     */
    public CostCenterDataVector getAllCostCenters(int pAccountId, int pOrder)
	throws RemoteException;

    /**
     * Get all CostCenters that match the given name.  The arguments specify
     * whether the name is interpreted as a pattern or exact match.
     *
     * @param pName a <code>String</code> value with CostCenter name or pattern
     * @param pAccountId the id of the CostCenter Account.
     * @param pMatch one of EXACT_MATCH, BEGINS_WITH, CONTAINS,
     * EXACT_MATCH_IGNORE_CASE, BEGINS_WITH_IGNORE_CASE and
     * CONTAINS_IGNORE_CASE.
     * @param pOrder one of ORDER_BY_ID, ORDER_BY_NAME
     * @return a <code>CostCenterDataVector</code> of matching CostCenters
     * @exception RemoteException if an error occurs
     */
    public CostCenterDataVector getCostCenterByName(String pName,
						    int pAccountId,
						    int pMatch,
						    int pOrder)
	throws RemoteException;

    /**
     * Describe <code>addCostCenter</code> method here.
     *
     * @param pCostCenterData a <code>CostCenterData</code> value
     * @param pAccountId the id of the account store.
     * @return an <code>CostCenterData</code> value
     * @exception RemoteException if an error occurs
     * DuplicateNameException if another cost center with same name exists
     */
    /*
    public CostCenterData addCostCenter(CostCenterData pCostCenterData,
					int pAccountId)
	  throws RemoteException, DuplicateNameException;
   */
    /**
     * Updates the CostCenter information values to be used by the request.
     * @param pUpdateCostCenterData  the CostCenterData object.
     * @return an <code>CostCenterData</code> value
     * @throws RemoteException Required by EJB 1.0
     * DuplicateNameException if another cost center with same name exists
     */

    public CostCenterData updateCostCenter(CostCenterData pCostCenterData)
	  throws RemoteException, DuplicateNameException;


    /**
     * Updates budget status depending on cost center type (ACCOUNT_BUDGET or SITE_BUDGET)
     * @param pCostCenterData  the CostCenterData object.
     * @throws RemoteException Required by EJB 1.0
     */
     public void setBudgetTypeStatus(CostCenterData pCostCenterData)
                                    throws RemoteException;

    /**
     * <code>removeCostCenter</code> may be used to remove an 'unused'
     * CostCenter. An unused CostCenter is a CostCenter with no database
     * references other than the default properties.
     * Attempting to remove a CostCenter that is used will
     * result in a failure initially reported as a SQLException and
     * consequently caught and rethrown as a RemoteException.
     *
     * @param pCostCenterData a <code>CostCenterData</code> value
     * @return none
     * @exception RemoteException if an error occurs
     */
    public void removeCostCenter(CostCenterData pCostCenterData)
	throws RemoteException;

    public CategoryToCostCenterViewVector getAllCategoryToCostCenters(int pAccountId)
    throws RemoteException;

    public ArrayList getAllItemToCostCenters(int pAccountId)
    throws RemoteException;

    public void setCategoryCostCenter(int pAccountId, String pCatalogCategoryName, int pNewCostCenterId)
    throws RemoteException;

    /**
     *Saves a collection of OrderRoutingData or OrderRoutingDescView (the OrderRoutingData portion of it).
     *
     *@param pOrderRoutingCollection a list containing OrderRoutingDescView data, and or OrderRoutingData
     *@throws RemoteException if an error occurs
     */
    public void updateAccountOrderRoutingCollection(java.util.List pOrderRoutingCollection)
    throws RemoteException;

    public OrderRoutingDescViewVector getAccountOrderRoutingCollection(int pAccountId)
    throws RemoteException;

    public void resetCostCenters(int pAccountId)
    throws RemoteException;

    public void updateOrderRoutingEntry(OrderRoutingData pOrd)
    throws RemoteException;

    public void deleteOrderRoutingEntry(int pAccountId, String pZip)
    throws RemoteException;


    public SiteDeliveryScheduleViewVector
        getAccountDeliveryScheduleCollection(int pAccountId)
    throws RemoteException;

    public void updateSiteDeliverySchedule
        (int pSiteId, String pSchedType, String [] pWeeks, String intervWeek)
    throws RemoteException;

    /**
     * Get account substitutions for the item
     * @param pAccountId the account identifier
     * @param pItemId the item identifier
     * @return a set of ItemSubstitutionDefData objects
     * @exception RemoteException
     */
   public ItemSubstitutionDefDataVector getAccountItemSubstiutions
                          (int pAccountId, int pItemId, boolean pActiveOnlyFlag)
    throws RemoteException;
    /**
     * Saves account substitution. Updates the state if the substitution exists and
     *  differs or inserts new substitution record if does not exist
     * @param pSubstitution ItemSubstitutionDefData object
     * @exception RemoteException
     */
   public ItemSubstitutionDefData saveAccountItemSubstiutions
                            (ItemSubstitutionDefData pSubstitution, String pUser)
   throws RemoteException;

   /**
     * Removes account item substitutions.
     * @param pSubstitutionId ItemSubstitutionDef identifiers
     * @retorun number of removed records
     * @exception RemoteException
     */
   public int removeAccountItemSubstiutions (IdVector pSubstitutionIds)
   throws RemoteException;

   /**
   * Gets substitutions for the account.
   * @param pItemIds  the vector of item identifiers.
   * @param pAccountId the account identifier.
   * @param pIncludeNull the flag, which indicates to include items with no substitutions
   * @return vector of AccountItemSubstView objects
   * @throws            RemoteException Required by EJB 1.0
   */
   public AccountItemSubstViewVector getAccountItemSubstitutions(IdVector pItemIds, int pAccountId, boolean pIncludeNull)
  throws RemoteException;

  /**
   * Gets account all of the accounts for this trading partner that have a site that matches,
   * uses begings with name match first and exact match if multiple accounts found.  If sitename is null the site
   * name is not analyized.
   * @param pTrPartnerId the trading partner identifier
   * @param String pSiteName the site name
   * @return AccountDataVector
   * @throws DataNotFoundException if the account could not be found
   * @throws RemoteException when error happened
   */
   public AccountDataVector getAccountsByTrParnterIdAndSiteName(int pTrPartnerId, String pSiteName)
   throws RemoteException, DataNotFoundException, MultipleDataException;

    public FiscalPeriodView getFiscalInfo(int pAccountId)
    throws RemoteException, DataNotFoundException;

    public FiscalPeriodView createDefaultFiscalInfo(int pAccountId)
    throws RemoteException, DataNotFoundException;

    public FiscalCalenderView updateFiscalCal(FiscalCalenderView pCal)
	throws RemoteException;

    public FiscalCalenderData updateFiscalCalender(FiscalCalenderData pCal)
    throws RemoteException;

    public FiscalCalenderViewVector getFiscalCalCollection(int pBusEntityId)
	throws RemoteException ;

    public FiscalCalenderView getCurrentFiscalCalenderV(int pAccountId)
    throws RemoteException;

    // Return a list of InventoryItemsDataJoin describing the
    // inventory items for this account.
    public ArrayList getInventoryItems(int pAccountId)
    throws RemoteException, DataNotFoundException;

    // Return a list of InventoryItemsDataJoin describing the
    // inventory items available for this account.
    public ArrayList getInventoryItemsAvailable(int pAccountId)
    throws RemoteException, DataNotFoundException;

    /**
    Add the item ids specified to the inventory program
    for this account.
    **/
    public void addInventoryItems(int pAccountId, String [] pItemIds, String pUserAddingItems)
    throws RemoteException, DataNotFoundException;

    /**
    Update the item ids specified from the inventory program
    for this account.
    **/
    public void updateInventoryItems(int pAccountId, String [] pItemIds,
    String pUser, String pReqAction)
    throws RemoteException, DataNotFoundException;

    public ArrayList getAccountBillTos(int pAccountId)
    throws RemoteException, DataNotFoundException;
    public void addBillTo(BillToData pBillToData)
    throws RemoteException, DataNotFoundException;
    public BillToData getBillToDetail(int pBillToId)
    throws RemoteException, DataNotFoundException;

    public BillToData lookupBillTo(AccountData pAcctData,
            OrderAddressData pCustBillToAddr) throws RemoteException ;


    /**
     *fetches the accounts identified by the supplied account ids
     *@param IdVector pAccount ids
     *@returns the populated AccountDataVector
     *@throws DataNotFoundException if ANY of the accounts in the id vector are not found
     *@exception  RemoteException  if an error occurs
     */
    public AccountDataVector getAccountCollection(IdVector pAccountIds) throws DataNotFoundException, RemoteException;


    public CatalogData getAccountCatalog(int pAccountId)
    throws RemoteException;

    public FiscalCalenderDataVector getAccountFiscalCalenders(int pAccountId)
    throws RemoteException;

    public ShoppingControlDataVector updateShoppingControls
	(ShoppingControlDataVector pShopCtrlv )
	throws RemoteException;
    
    public void updateShoppingControls
    (ShoppingControlDataVector pAccountShoppingCtrols, ShoppingControlDataVector pSiteShoppingCtrols)
    throws RemoteException;

    /**
     * Creates the cache tables for all accounts that have fiscal calendars.
     * @throws RemoteException if an error occurs
     */
    public void createFiscalCalCacheTablesForAll()
    throws RemoteException;

    public FiscalCalenderData getCurrentFiscalCalender(int accountId)
    throws RemoteException;

    public int getAccountIdByOrderId(int orderId)
    throws RemoteException;

    public boolean ledgerSwitchOff(int pAccountId)
    throws RemoteException;

    public IdVector getAccountsForStore(int pStoreId)
    throws RemoteException;

    public FiscalCalenderData getFiscalCalender(int accountId, Date orderDate)
    throws RemoteException;

    public int cloneAccount(int cloneAccountId, int storeId, String accountName, String user)
    throws RemoteException;

    public ProductViewDefDataVector getProductViewDefData(int accountId)
    throws RemoteException;

    public void updateProductViewDefData(int accountId, ProductViewDefDataVector list, String userName)
    throws RemoteException;

    public AccountSearchResultViewVector search(String stores,
                String fieldValue, String fieldSearchType ,
                String searchGroupId , boolean showInactive )
    throws RemoteException;

    public AccountSearchResultViewVector search(String stores,
                String fieldValue, String fieldSearchType ,
                String refNumValue, String refNumSearchType,
                String searchGroupId , boolean showInactive )
    throws RemoteException;

    public Map<Integer, String> getPropertyValues(IdVector accountIds,
            String propertyType) throws RemoteException;

    public Map<Integer, List> getPropertiesForAccounts(IdVector accountIds, List shortDescriptions,
            List propertyTypes, boolean omitNullValues) throws RemoteException;
    
    public AccountSearchResultViewVector getAccountsAsSrchResultVector(IdVector pAccountIds)
			throws DataNotFoundException, RemoteException;

    public AccountSearchResultViewVector searchAccounts(    int userId,
                                                            IdVector stores,
                                                            String fieldValue,
                                                            String fieldSearchType,
                                                            String searchGroupId,
                                                            boolean showInactive)
                                                        throws RemoteException;

    public IdVector getAccountAssocCollection(IdVector pAccountIds,
                                              String pAssocCd,
                                              String pStatusCd) throws RemoteException;


    public String getDefaultEmail(int pAccountId, int pStoreId) throws RemoteException;

    public FiscalCalenderView getFiscalCalenderV(int pAccountId, Date pOrderDate) throws RemoteException;

    public IdVector getInactiveAccountIds(IdVector pAccountIds) throws RemoteException;
    // Move Cost Centers to Account
    public AccCategoryToCostCenterView getCategoryToCostCenterView(int pSiteId)  throws RemoteException ;
    public AccCategoryToCostCenterView getCategoryToCostCenterViewByCatalog(int pCatalogId)  throws RemoteException ;
    public AccCategoryToCostCenterView getCategoryToCostCenterViewByAccount(int pAccountId)  throws RemoteException ;
    public boolean checkCostCentersForSite( int pSiteId, int pSiteCatalogId, AccCategoryToCostCenterView pCategToCCView )  throws RemoteException;
    public boolean isAccountForSite( int pSiteId, int pSiteCatalogId, int pAccountId ) throws RemoteException;
    public AccCategoryToCostCenterView refreshCategoryToCostCenterView( AccCategoryToCostCenterView pCategToCCView, int pSiteId)  throws RemoteException;
    public AccCategoryToCostCenterView refreshCategoryToCostCenterView( AccCategoryToCostCenterView pCategToCCView, int pSiteId, int pSiteCatalogId)  throws RemoteException;
    
    /**
     * Gets the fiscal calendar data for required fiscal year.
     * @param pAccountId - Account Id.
     * @param pFiscalYear - Fiscal year.
     * @return FiscalCalenderData - Fiscal Calendar Data.
     * @throws RemoteException
     */
     public FiscalCalenderData getFiscalCalenderForYear(int pAccountId,int pFiscalYear)throws RemoteException;
     
     /**
      * Gets the fiscal calendar view for required fiscal year.
      * @param pAccountId - Account Id.
      * @param pFiscalYear - Fiscal year.
      * @return FiscalCalenderView - Fiscal Calendar View.
      * @throws RemoteException - if any error occurs.
      */
     public FiscalCalenderView getFiscalCalenderVForYear(int pAccountId,int pFiscalYear) throws RemoteException;
     
     /**
      * Gets all accounts that were configured in all groups for a user.
      * @param stores - Store Id
      * @param fieldValue
      * @param fieldSearchType
      * @param refNumValue
      * @param refNumSearchType
      * @param userId - User Id.
      * @param showInactive
      * @return
      * @throws RemoteException - if any error occurs.
      */
     public AccountSearchResultViewVector searchGroupsByUserId(String stores,
				String fieldValue,
				String fieldSearchType,
				String searchGroupId,
				int userId,
				boolean showInactive ) throws RemoteException;
     
     public ShoppingControlDataVector getShoppingControls(int accountId, IdVector itemIds) throws RemoteException;
     public BusEntityDataVector getAccountBusEntByCriteria(DBCriteria pCrit) throws RemoteException;
}
