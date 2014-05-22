package com.cleanwise.service.api.session;

/**
 * Title:        IntegrationServices
 * Description:  Remote Interface for IntegrationServices
 * Stateless Session Bean
 * Purpose:      Provides access to the application integration
 * methods and programs.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.

 */

import javax.ejb.*;
import javax.naming.*;
import java.sql.*;
import java.rmi.*;
import java.util.*;
import java.util.Date;
import java.math.BigDecimal;

import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.BudgetRuleException;
import com.cleanwise.service.api.util.InvalidLoginException;
import com.cleanwise.service.api.util.OrderTotalException;
import com.cleanwise.service.api.util.OrderWorkflowException;
/**
 * The interface <code>IntegrationServices</code>
 * provides the methods to move order requests through
 * the various processing steps.
 * @author <a href="mailto:dvieira@DVIEIRA"></a>
 */
public interface IntegrationServices extends javax.ejb.EJBObject
{

    /**
     * <code>processOrderRequest</code>
     *  EDI order request method.
     *
     * @param pOrderReq an <code>OrderRequestData</code> value
     * @return a <code>ProcessOrderResultData</code> value
     * @exception RemoteException if an error occurs
     * @exception BudgetRuleException if an error occurs
     * @exception OrderTotalException if an error occurs
     */
    public ProcessOrderResultData processOrderRequest
	( OrderRequestData pOrderReq )
	throws RemoteException, BudgetRuleException,
	       OrderTotalException;

    /**
     * Processes a change order request.  This will invoke the
     * pipeline processing logic that is defined for the change order
     * pipeline.
     */
    public void webProcessOrderChangeRequest
        (CustomerOrderChangeRequestData pOrderReq)
        throws RemoteException,
               BudgetRuleException,
               OrderTotalException,
               OrderWorkflowException;

    /**
     * <code>processOrderRequest</code>
     * WEB order request method.
     *
     * @param pOrderReq a <code>CustomerOrderRequestData</code> value
     * @return a <code>ProcessOrderResultData</code> value
     * @exception RemoteException if an error occurs
     * @exception BudgetRuleException if an error occurs
     * @exception OrderTotalException if an error occurs
     */
    public ProcessOrderResultData processOrderRequest
	( CustomerOrderRequestData pOrderReq )
	throws RemoteException, BudgetRuleException,
	       OrderTotalException, OrderWorkflowException, OrderWorkflowException;

     /**
     * <code>processIntegrationRequests</code>
     *
     * @param pIntegrationReqV an <code>IntegrationRequestsVector</code> value
     * @param pErpNum erp number of what we are processing if applicable
     * @param tradingPartnerId the id of the trading parnter that was used for translation
     * @exception RemoteException if an error occurs
     */
    public void processIntegrationRequests
	(IntegrationRequestsVector pIntegrationReqV, String pErpNum, int tradingPartnerId)
      throws RemoteException;

    /**
     * <code>sendOrderRequestsToERP</code>
     *   Send order requests to the ERP system for
     *    construction of a PO.
     *     All orders that have an approved status is sent.
     *
     * @param pTestFlag a <code>String</code> value
     * @exception RemoteException if an error occurs
     */
    public void sendOrderRequestsToERP(String pTestFlag)
	throws RemoteException;


    /**
     * <code>processOrderResponseFromERP</code>
     * Process POs from the ERP system.
     *
     * @param pTestFlag a <code>String</code> value
     * @exception RemoteException if an error occurs
     */
    public void processOrderResponseFromERP( String pTestFlag )
	throws RemoteException;

    /**
     * <code>fetchOrderFulfillmentRequests</code>
     * Get the requests to be sent to the distrubutors.
     *
     * @param pErpNumDistributor an <code>int</code> value
     * @param pReserveEntries a <code>boolean</code> value
     * @return an <code>OrderFulfillmentData</code> value
     * @exception RemoteException if an error occurs
     */
    public OrderFulfillmentData fetchOrderFulfillmentRequests
	( String pErpNumDistributor, boolean pReserveEntries )
	throws RemoteException;

    /**
     * <code>checkOrders</code>,
     * Analyze the orders in an unfinished state and determine
     * if any actions are required.
     *
     * @exception RemoteException if an error occurs
     */
    public void checkOrders()
	throws RemoteException;

    /**
     * Describe <code>sendInvoicesToERP</code> method here.
     *
     * @param pOrderId an <code>int</code> value
     * @param pProcessFlag a <code>String</code> value
     * @return an <code>int</code> value
     * @exception RemoteException if an error occurs
     */
    public int sendInvoicesToERP
	(int pOrderId, String pProcessFlag) throws RemoteException;

    /**
     * Describe <code>processInvoicesReleasedFromERP</code> method here.
     *
     * @param pOrderId an <code>int</code> value
     * @param pProcessFlag a <code>String</code> value
     * @return an <code>int</code> value
     * @exception RemoteException if an error occurs
     */
    public int processInvoicesReleasedFromERP
	(int pOrderId, String pProcessFlag) throws RemoteException;

    /**
     * <code>reprocessOrder</code>
     * @param pOrderId an <code>int</code> value
     * @param pOptionalOrderStatus a <code>String</code> value
     * @return a <code>ProcessOrderResultData</code> value
     * @exception RemoteException if an error occurs
     * @exception BudgetRuleException if an error occurs
     * @exception OrderTotalException if an error occurs
     */
    public ProcessOrderResultData reprocessOrderRequest
	( int pOrderId, String pOptionalOrderStatus, String pUserName )
	throws RemoteException, BudgetRuleException,
	       OrderTotalException;

    /**
     * Describe <code>getTradingProfileConfig</code> method here.
     *
     * @param profileId an <code>int</code> value
     * @param setType a <code>String</code> value
     * @param direction a <code>String</code> value
     * @return a <code>TradingProfileConfigData</code> value
     * @exception RemoteException if an error occurs
     */
    public TradingPartnerDescView getTradingProfileConfig(
    int profileId, String setType, String direction)
    throws RemoteException;

    /**
     *Returns a lightly populated TradingPartnerDescView object where the config is populated and the
     *TradingPropertyMappingDataVector property is populated
     */
    public TradingPartnerDescView getOutboundTradingProfileConfig(
        String erpNum,int busEntityId, int incommingProfileId, String setType)
    throws RemoteException;

  public List getOutboundBusEntityByTradingType(String tradingType, String setType)
  throws RemoteException;


  /**
     *When a transaction is made between a client and server the orders are set to
     *a pending state (sending to lawson).  These orders should then be processed
     *in whatever manner is appropriate (EDI file out as in JWP, etc.) and when
     *the client has succesfully processed them it will notify the server which should
     *then update them to the proper state.
     *@param orderIdVector all the order ids that should be acknowledged as being succesfully processed
     *@throws RemoteException if there were any problems (most likely due to database connectivity)
     */
    public void acknowledgeOrderTransaction(IdVector orderIdVector)
    throws RemoteException;

    /**
     * Describe <code>getEDIOrdersByErpNumAndSetType</code> method here.
     *
     * @param erpNum a <code>String</code> value
     * @param setType a <code>String</code> value
     * @return an <code>OutboundEDIRequestDataVector</code> value
     * @exception RemoteException if an error occurs
     */
    public OutboundEDIRequestDataVector getEDIOrdersByErpNumAndSetType(String erpNum, String setType, int busEntityId)
        throws RemoteException;


    public OrderData getOrderDataByPoNum(String poNum, int tradingPartnerId, String siteKey, String accountKey, String poNumType)
    throws RemoteException;

    public PurchaseOrderData getPurchaseOrderByPoNum(String poNum, int tradingPartnerId, String siteKey, String accountKey, String poNumType)
    throws RemoteException;

    /**
     * Describe <code>checkOrderRequest</code> method here.
     *
     * @param pOrderId an <code>int</code> value
     * @return a <code>ProcessOrderResultData</code> value
     * @exception RemoteException if an error occurs
     */
    public ProcessOrderResultData checkOrderRequest
	( int pOrderId ) throws RemoteException;

    /**
     * Describe <code>cancelOrderRequest</code> method here.
     *
     * @param pOrderId an <code>int</code> value
     * @param pUser a <code>String</code> value
     * @return a <code>ProcessOrderResultData</code> value
     * @exception RemoteException if an error occurs
     */
    public ProcessOrderResultData cancelOrderRequest
	( int pOrderId, String pUser ) throws RemoteException;

    /**
     * Describe <code>checkDistInvoice</code> method here.
     *
     * @param pDistInvoiceId an <code>int</code> value
     * @return a <code>ProcessInvoiceResponseData</code> value
     * @exception RemoteException if an error occurs
     */
    public ProcessInvoiceResponseData checkDistInvoice
	( int pDistInvoiceId ) throws RemoteException;

    /**
     * Describe <code>reprocessDistInvoice</code> method here.
     *
     * @param pDistInvoiceId an <code>int</code> value
     * @return a <code>ProcessInvoiceResponseData</code> value
     * @exception RemoteException if an error occurs
     */
    public ProcessInvoiceResponseData reprocessDistInvoice
	( int pDistInvoiceId ) throws RemoteException;

    /**
     * Describe <code>checkCustInvoice</code> method here.
     *
     * @param pCustInvoiceId an <code>int</code> value
     * @return a <code>ProcessInvoiceResponseData</code> value
     * @exception RemoteException if an error occurs
     */
    public ProcessInvoiceResponseData checkCustInvoice
	( int pCustInvoiceId ) throws RemoteException;

    /**
     * Describe <code>reprocessCustInvoice</code> method here.
     *
     * @param pCustInvoice an <code>InvoiceCustData</code> value
     * @return a <code>ProcessInvoiceResponseData</code> value
     * @exception RemoteException if an error occurs
     */
    public ProcessInvoiceResponseData reprocessCustInvoice
	( InvoiceCustData pCustInvoice) throws RemoteException;

    /**
     * Describe <code>updateJanitorsCloset</code> method here.
     *
     * @param pOrderJoinData an <code>OrderItemData</code> value
     */
    public void updateJanitorsCloset(OrderJoinData pOrderJoinData)
	throws RemoteException;
    public void updateJanitorsCloset(OrderJoinData ojd,
				     boolean pMakeLedgerEntryFlag )
	throws RemoteException ;

    /**
    * Gets new value of Order nuber
    * @param pConn - the connection Stjohn database
    * @paran pStoreId - the store identifier
    * @return next order number for the store
    * @throws RemoteException, SQLException
    */
    public int getNextOrderNumber (Connection pConn, int pStoreId )
	throws RemoteException, SQLException;

    public int copyOrder ( int pSourceOrderId )
    throws RemoteException, SQLException;

    /**
     *Jwp's edi file maintains the GSControl number on a per account basis.
     *@param erpAccountNumber the erp account number to use
     */
    public int getNextGSNumber(String erpAccountNumber) throws RemoteException;

    /**
     *Returns the email address to use for integration related messeges that need sending.
     */
    public String getIntegrationEmailAddress() throws RemoteException;

    /**
     *Returns a unique manifest id.  Will repeate only once it reaches 8 digits (99,999,999)
     */
    public String getUniqueManifestId() throws RemoteException;

    /** Saves reingneered customer invocies data to database
     *
     */
    public void reengineerCustInvoices(ArrayList pInvoices)
    throws RemoteException;

    /** Gets max add_date from clw_invoice_cust_reeng table
     *
     */
    public Date getInvoiceCustReengMaxDate()
    throws RemoteException;

    /** Checks pending date orders and reprocesses ready ones
     *
     */
    public void processPendingDateOrders()
    throws RemoteException;

    /**
     *Creates a valid access token.  An access toekn will allow a user to log into our site
     *without supplying a username and password, instead they supply the access token and
     *not supply the user name and password.  This method will validate the user name and
     *password that was supplied with the LdapItemData.
     */
    public UserAcessTokenViewData createAccessToken(LdapItemData pKey)
    throws RemoteException, com.cleanwise.service.api.util.InvalidLoginException;

    public UserAcessTokenViewData createAccessToken(LdapItemData pKey, boolean checkPwd)
    throws RemoteException, InvalidLoginException;

    /**
     *Returns a String representation of a file path which should be suitable for
     *new File(<String>).  This path is the location where files are stored when
     *an inline integration request is made, interface when something is run through
     *the translation logic that does not use files.
     *@throws RemoteException
     */
    public String getPathForArchiveIntegrationFiles()
    throws RemoteException;

    /**
     *Returns a String representation of a file path which should be suitable for
     *new File(<String>).  This path is the location where failed files are stored when
     *an inline integration request is made, interface when something is run through
     *the translation logic that does not use files.
     *@throws RemoteException
     */
    public String getPathForArchiveFailedIntegrationFiles()
    throws RemoteException;

    /**
     *Returns a populated siteData object as specified by the pSiteId
     *@throws RemoteException
     */
    public SiteData getSite(int pSiteId)throws RemoteException;

    public int getSiteIdByPropertyName(String pName,
			String val, List parentIds, boolean parentIsAccount) throws RemoteException;
    
    public int getBusEntityIdByProperty(String propertyTypeCd, String val) throws RemoteException;
    
    public ProcessOrderResultData resendFailedPOs
	(int pOrderId,String userName)
	throws RemoteException,
	       BudgetRuleException ;


    public OrderItemData matchDistributorInvoiceItem(InvoiceDistDetailData invDistDetailData,
                                                     List itemNotesToLog,
                                                     List notesToLog,
                                                     OrderItemDataVector poItems,
                                                     OrderData orderData) throws RemoteException;
    public OrderItemData matchDistributorInvoiceItem(InvoiceDistDetailData invDistDetailData,
            List itemNotesToLog,
            List notesToLog,
            OrderItemDataVector poItems,
            OrderData orderData,
            boolean usePoLineNumForInvoiceMatch) throws RemoteException;

    public InvoiceDistData insertInvoiceDistData(InvoiceDistData invoiceD,
                                                 int exceptionCount,
                                                 BigDecimal recvdTotal,
                                                 List notesToLog,
                                                 List insertedInvoices,
                                                 boolean checkDuplInvoiceNum) throws RemoteException;

    public void insertInvoiceDistDetailAndProperties(InvoiceDistDetailDescDataVector toInsertItems,
                                                     int invoiceDistId,
                                                     OrderData order) throws RemoteException;

    public InvoiceDistDataVector existingInvoiceFound(String pInvoiceNum,
                                                      String pErpPoNum,
                                                      int storeId,
                                                      List insertedInvoices) throws RemoteException;

    public void insertDistributorInvoiceNotes(InvoiceDistData invoiceD,
                                              OrderData matchOrder,
                                              List notesToLog,
                                              Map miscInvoiceNotes) throws RemoteException;


    public int convertCostAndQty(BigDecimal divisor, Object obj,
                                 List itemNotesToLog,
                                 boolean exceptionOnQtyConversion,
                                 boolean convertRequestedCost) throws RemoteException;

    public int setItemAdjustedCost(TradingPartnerData pTradingPartnerData,
                                   Object obj,
                                   OrderItemData itemMatch,
                                   List itemNotesToLog,
                                   List invoiceNotesToLog,
                                   DistributorData pDistributor) throws RemoteException;

    public TradingPartnerDescView getOutboundTradingProfileConfig(String erpNum
                                                                  ,int incommingProfileId,
                                                                  String setType,
                                                                  String entityType,
                                                                  String direction)  throws RemoteException;

    public String getErpNumByBusEntityId(int busEntityId) throws RemoteException ;

    public void processInvoicesOfNetworkService(InvoiceNetworkServiceDataVector data, int storeId)
    throws RemoteException;

    public void processSiteDelivery(SiteDeliveryUploadViewVector data, ErrorHolderViewVector errorHolder)
            throws RemoteException;

    public void processSiteDelivery(SiteDeliveryUploadViewVector data, ErrorHolderViewVector errorHolder, IdVector storeIds)
    	throws RemoteException;
    public void processSiteDelivery(SiteDeliveryUploadViewVector data, ErrorHolderViewVector errorHolder, IdVector storeIds, String pUser)
        throws RemoteException ;

    public boolean isUniqCustomerPoNumber(String pCustPoNum) throws RemoteException;


    /*Pollock site loader*/
      public void processSite(Connection conn,String siteloader, String siteActionAdd,String siteActionChange, String siteActionDelete, int storeNum,String tempTable) throws RemoteException;
      public boolean siteCheck(String siteloader, String siteActionAdd,String siteActionChange, String siteActionDelete, int storeNum,String accRefNum,String siteRefNum, Connection conn,String tmpTableName) throws RemoteException;
      public int createSite(String siteloader, String siteActionAdd,String siteActionChange, String siteActionDelete, int storeNum,String accountRefNum,String siteRefNum,Connection conn,String tempTable) throws RemoteException;
      public int updateSite(String siteloader, String siteActionAdd,String siteActionChange, String siteActionDelete, int storeNum,String accountRefNum,String siteRefNum,Connection conn,String tempTable) throws RemoteException;
      public void inactivateSite(String accRefNum, String siteRefNum, int storeNum, String siteloader, Connection conn) throws RemoteException;
    ///**************************************************************************

      /*PollackAccount loader*/
      public void processAccount(Connection conn,String accountloader,String accountActionAdd,String accountActionChange,String accountActionDelete,int storeNum,String tempTable) throws RemoteException;
      public boolean accountCheck(String accountloader,String accountActionAdd,String accountActionChange,String accountActionDelete,int storeNum,String accRefNum,Connection conn) throws RemoteException;
      public void createAccount(String accountloader, String accountActionAdd,String accountActionChange,int storeNum,String accountRefNum,Connection conn,String tempTable) throws RemoteException;
      public void updateAccount(String accountloader,String accountActionAdd,String accountActionChange,int storeNum,String accountRefNum,Connection conn,String tempTable) throws RemoteException;
      public void inactivateAccount(String accountloader,String accountActionDelete,int storeNum,String accRefNum,Connection conn) throws RemoteException;
      public int getAccountIdByName(Connection conn,String accountName,int storeNum) throws RemoteException;
      public void updateProductUiTemplate(Connection conn,int storeNum,int accountId,String accountNameTemplate,String accountloader) throws RemoteException;
      public void createProductUiTemplate(Connection conn,int storeNum,int accountId,String accountNameTemplate,String accountloader) throws RemoteException;
      /*PollockItem loader */
      public void processItem(Connection conn, String itemloader, String itemActionAdd,String itemActionChange, String itemActionDelete, int storeNum,int tradingPartnerId) throws RemoteException;
      public int[] itemCheck(Connection conn, String distSku, String distName,String uomCd,int storeNum) throws RemoteException;
      public void createItem(Connection conn,int storeNum, int distID, PollockItemView pollockItemView, String itemloader,int alternateCatalogId) throws RemoteException;
      public int getManufId(Connection conn,String busEntityTypeCd,String shortDesc, int storeNum) throws RemoteException;
      public int getStoreCatalog(Connection conn,int storeNum) throws RemoteException;
      public int checkAndCreateCategory(Connection conn,int storeCatalogId,String catName,String itemloader,int sortOrder) throws RemoteException;
      public void checkCreateCategoryAssoc(Connection conn,int itemID,PollockItemView pollockItemView,String itemloader,int storeCatalogId,int storeNum) throws RemoteException;
      public void updateItem(Connection conn, int itemID,int distID,PollockItemView pollockItemView,String itemloader,int storeNum) throws RemoteException;
      public void inactivateItem(Connection conn,int itemID, String itemloader) throws RemoteException;
      public int getCertifiedCompId(Connection conn,String certifiedCompany) throws RemoteException;
      public void associateItem(Connection conn,int item1ID,int item2ID,int insertOrUpdate,String productOrCat,int storeCatalogId,String itemloader) throws RemoteException;
      public int checkItemCatAssoc(Connection conn,int item1ID,int item2ID,int catalogId) throws RemoteException;
      public int checkCatSucatAssoc(Connection conn,int item1ID,int item2ID,int catalogId) throws RemoteException;
      public void checkAndAddCertifiedCompany(Connection conn,int itemID,String certifiedCompanies,String itemloader,int storeNum) throws RemoteException;



      /*Pollock User Loader*/
      public void processUser(Connection conn, String userloader, String userActionAdd,String userActionChange, String userActionDelete, int storeNum) throws RemoteException;
      public int userCheck(Connection conn,String userN, int storeNum)  throws RemoteException;
      public void createUser(Connection conn,String userN,int storeNum,String userloader) throws RemoteException;
      public int getUserId(Connection conn,String userName,int storeNum) throws RemoteException;
      public int getAccountId(Connection conn,String accountRefNum,int storeNum) throws RemoteException;
      public void checkAndCreateUserAccountAssoc(Connection conn,int userId, int accountID,String userloader) throws RemoteException;
      public int getSiteId(Connection conn,String siteRefNum,String accRefNum,int storeNum) throws RemoteException;
      public void checkAndCreateUserSiteAssoc(Connection conn,int userId,int siteID,int accountID,String userloader) throws RemoteException;
      public void updateUser(Connection conn,String userN,int storeNum,String userloader) throws RemoteException;
      public int checkUserGroupAssoc(Connection conn,int groupId,int userId) throws RemoteException;
      public void CheckAndChangeUserStatus(Connection conn,int userId,String userloader)  throws RemoteException;
      //Order Guide Loader
      public void processOrderGuide(Connection conn, String orderguideloader,int storeNum) throws RemoteException;
      public int createOrderGuide(Connection conn,PollockOrderGuideView pollockOrderGuideView,String orderguideloader,int storeNum) throws RemoteException;
      public int getItemId(Connection conn,String distSku,String uom,int storeNum) throws RemoteException;
      public int getSiteCatalogId(Connection conn,int siteId) throws RemoteException;
      public int checkIfOrderGuideExists(Connection conn,int catalogId,String orderGuideName,String orderGuideType,int siteId) throws RemoteException;
      public void deleteOrderGuideStructure(Connection conn,int orderGuideId) throws RemoteException;
      public void createOrderGuideStructure(Connection conn,int orderGuideId,PollockOrderGuideView pollockOrderGuideView,String orderguideloader,int storeNum) throws RemoteException;

      //Catalog Loader
      public void processCatalog(Connection conn,String catalogloader,int storeNum, String tempTable) throws RemoteException;
      public int getAccountCatalogId(Connection conn,int accountId) throws RemoteException;
      public int getShoppingCatalogId(Connection conn,int accountId,String catalogKey) throws RemoteException;
      public int createAccountCatalog(Connection conn,int accountId,int storeNum,String catalogloader) throws RemoteException;
      public void createCatlogStructure(Connection conn,int busEntityId,int catalogId,int storeCatalogId,String accRefNum,String catalogKey,String catalogloader,int storeNum, String tmpTableName) throws RemoteException;
      public int createShoppingCatalog(Connection conn,int busEntityId,int storeNum,String catalogloader,String catalogKey,String accRefNum,String tmpTableName) throws RemoteException;
      public void addCategoryCatalogStructure(Connection conn,int catalogId,int categoryId,int sortOrder,String catalogloader) throws RemoteException;
      public void updateAccountCatalogStructure(Connection conn,int accountCatalogId,int storeCatalogId,String accRefNum,String catalogKey,String catalogloader,int storeNum,String tmpTableName) throws RemoteException;
      public void checkAndAddItemAccountCatalog(Connection conn,int itemId,int catalogId,int sortOrder,String catalogloader) throws RemoteException;
      public void checkAndAddCategoryAccountCatalog(Connection conn,int itemId,int catalogId,int sortOrder,String catalogloader) throws RemoteException;
      public int getDistId(Connection conn,String distName,int storeNum) throws RemoteException;
      public int createContract(Connection conn,int shoppingCatalogId,String catalogKey,int storeNum,String catalogloader) throws RemoteException;
      public void createContractItem(Connection conn,int contractId,String accRefNum,String catalogKey,int storeNum,String catalogloader,String tmpTableName) throws RemoteException;
      public void deleteCatalogStructure(Connection conn,int shoppingCatalogId) throws RemoteException;
      public int getContractId(Connection conn,int shoppingCatalogId) throws RemoteException;
      public void deleteContractItem(Connection conn, int contractId) throws RemoteException;
      public int createTemplateOrderGuide(Connection conn,int shoppingCatalogId,String catalogKey,String catalogloader) throws RemoteException;
      public void createTemplateOrderGuideStruct(Connection conn,int templateOrderGuideId,String accRefNum,String catalogKey,String catalogloader,int storeNum,String tmpTableName) throws RemoteException;
      public int checkIfOrderItemExist(Connection conn,int templateOrderGuideId,int itemId) throws RemoteException;
      public int getTemplateOrderGuideId(Connection conn,int shoppingCatalogId) throws RemoteException;
      public void checkCreateTemplateOrderGuide(Connection conn,int shoppingCatalogId,String accRefNum,String catalogKey,String catalogloader,int storeNum,String tmpTableName) throws RemoteException;
      public void updateCatalogDate(Connection conn,int catalogId,String catalogloader) throws RemoteException;
      public void updateContractDate(Connection conn,int contractId,String catalogloader) throws RemoteException;
      public void updateTemplateOrderGuideDate(Connection conn,int templateOrderGuideId,String catalogloader) throws RemoteException;
      //Catalog Assoc Loader
      public void processCatalogAssoc(Connection conn,String catalogassocloader,int storeNum) throws RemoteException;
      public void checkAndCreateSiteCatalogAssoc(Connection conn,int siteId,int catalogId,String catalogassocloader) throws RemoteException;

      public OrderData getOrderByPONum(String pCustPoNum) throws RemoteException;
      //Buy List Loader
      public int processBuyList (BuyListViewVector buyListVV,  int storeId , int distributorId, String loader) throws RemoteException;
      
      //Pricing List Loader      
      public int processPricingList (PricingListViewVector priceListVV,  int storeId , int distributorId, String listType, String loader) throws RemoteException; 
      
      public HashMap checkAndCreateCategoryN(Connection conn,int storeCatalogId,String catName,String itemloader,int sortOrder,HashMap categoryHashMap) throws RemoteException;
      public HashMap addCategoryCatalogStructureN(Connection conn,int catalogId,int categoryId,int sortOrder,String catalogloader,HashMap catStructHashMap) throws RemoteException;
      public void updateCategorySortOrder(Connection conn,int catalogId,int categoryId,int sortOrder,String catalogloader) throws RemoteException;
      public void updateShopCatlogStructure(Connection conn,int busEntityId,int catalogId,int storeCatalogId,String accRefNum,String catalogKey,String catalogloader,int storeNum, String tmpTableName,HashMap itemIdHashMap) throws RemoteException;
      public void deleteShoppingCatStruct(Connection conn,ArrayList itemIdList,int catalogId) throws RemoteException;
      public void updateAccountCatalogStructureN(Connection conn,int accountCatalogId,int storeCatalogId,String accRefNum,String catalogKey,String catalogloader,int storeNum,String tmpTableName,HashMap itemIdHashMap) throws RemoteException;
      public void createContractItemN(Connection conn,int contractId,String accRefNum,String catalogKey,int storeNum,String catalogloader,String tmpTableName,HashMap itemIdHashMap) throws RemoteException;
      public void createCatlogStructureN(Connection conn,int busEntityId,int catalogId,int storeCatalogId,String accRefNum,String catalogKey,String catalogloader,int storeNum, String tmpTableName,HashMap itemIdHashMap) throws RemoteException;
      public int createContractN(Connection conn,int shoppingCatalogId,String catalogKey,int storeNum,String catalogloader,String accRefNum) throws RemoteException;
      public int createOrderGuideN(Connection conn,PollockOrderGuideView pollockOrderGuideView,String orderguideloader,int storeNum,HashMap itemIdHashMap) throws RemoteException;
      public void createOrderGuideStructureN(Connection conn,int orderGuideId,PollockOrderGuideView pollockOrderGuideView,String orderguideloader,int storeNum,HashMap itemIdHashMap) throws RemoteException;
      public void pollockItemPostProcess(Connection conn,String itemloader,int storeNum) throws RemoteException;
      
      public void checkInputDataAgainstCreatedPriceListTbl(Connection conn, ArrayList listNames, String listType) throws RemoteException; //new: SVC
      public void checkInputDataAgainstCreatedPriceListDetailTbl(Connection conn, HashMap newListNamesMap, HashMap existListNamesMap, String listType) throws RemoteException; //new: SVC
      //Xpedx Catalog Loader
      public void processXpedxCatalog(Connection conn, String catalogloader, int storeNum) throws RemoteException;
      //NSC Sap Loaders
      public int processPricing (ContractItemPriceViewVector priceViewV, HashSet catalogKeys,  int storeId ) throws RemoteException;
      public void processNSCSapCatalog(Connection conn,String catalogloader,int storeNum,String tempTable) throws Exception, RemoteException;
      public void processNSCSapSite(Connection conn,String siteloader, String siteActionAdd,String siteActionChange, String siteActionDelete, int storeNum, String tempTable) throws RemoteException;
      public void processNSCSapAccount(Connection conn,String accountloader,String accountActionAdd,String accountActionChange,String accountActionDelete,int storeNum, String tempTable) throws RemoteException;
      public void updateFiscalCalendar(Connection conn, int accountId, String fiscalCalendarYear, String fiscalCalendarStart, String fiscalCalendarPeriod, String accountloader ) throws RemoteException;
      public HashMap processInventoryUpdate(int pSiteId, HashMap pItemMap, String pUserName) throws RemoteException;
      public void createOrder850Events(OrderRequestDataVector orderReqs) throws Exception;
      public void processUpdateCatalog(Connection conn,String catalogloader,int storeNum, boolean isVersion1, String tmpTableName) throws RemoteException;

}
