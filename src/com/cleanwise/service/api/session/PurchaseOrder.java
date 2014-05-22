/**
 * Title:        PurchaseOrder
 * Description:  Remote Interface for PurchaseOrder Stateless Session Bean
 * Purpose:      Provides access to the services for managing the purchase order information, properties and relationships.
 * Copyright:    Copyright (c) 2001
 * Company:      Cleanwise, Inc.
 * @author       Brook Stevens, CleanWise, Inc.
 */
package com.cleanwise.service.api.session;

import com.cleanwise.service.api.util.DuplicateNameException;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.value.*;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;


/**
 * Remote interface for the <code>PurchaseOrder</code> stateless session bean.
 * @author  bstevens
 */
public interface PurchaseOrder extends javax.ejb.EJBObject {

    /**
     * Adds a purchase order to the database
     *
     * @param pPurchaseOrderData a <code>PurchaseOrderData</code> value
     * @return a <code>PurchaseOrderData</code> value
     * @exception RemoteException if an error occurs
     */
    public PurchaseOrderData addPurchaseOrder(PurchaseOrderData pPurchaseOrderData)
    throws RemoteException;


    /**
     * Updates the total and subtotal fields from a purchase order with the data from the order
     * items table.
     *
     * @param pPurchaseOrderId the purchase order id to update
     * @exception RemoteException if an error occurs
     */
    public void updatePurchaseOrderFromOrderItems(int pPurchaseOrderId) throws RemoteException;

    /**
     * Updates the PurchaseOrder information values.
     * @param pPurchaseOrderData  the PurchaseOrderData .
     * @return a <code>PurchaseOrderData</code> value
     * @throws            RemoteException Required by EJB 1.0
     */
    public PurchaseOrderData updatePurchaseOrder(PurchaseOrderData pPurchaseOrderData)
    throws RemoteException;

    /**
     * Find pos and their associated data and returns a getPurchaseOrderStatusDescCollection of 0 or more
     * results.
     * @param PurchaseOrderStatusCriteriaData the search criteria
     * @return a <code>PurchaseOrderStatusDescDataViewVector</code>
     * @throws            RemoteException Required by EJB 1.0
     */
    public PurchaseOrderStatusDescDataViewVector getPurchaseOrderStatusDescCollection(PurchaseOrderStatusCriteriaData searchCrit)
    throws RemoteException;

    /**
     * Find pos and their associated data and returns a getPurchaseOrderStatusDescCollection of 0 or more
     * results.
     * @param PurchaseOrderStatusCriteriaData the search criteria
     * @param SearchPos if not invoiceSaerch
     * @return a <code>PurchaseOrderStatusDescDataViewVector</code>
     * @throws            RemoteException Required by EJB 1.0
     */
    public PurchaseOrderStatusDescDataViewVector getPurchaseOrderStatusDescCollection(PurchaseOrderStatusCriteriaData searchCrit, boolean searchPos)
    throws RemoteException;

    /**
     * Finds pos and returns a PurchaseOrderDataVector of 0 or moreresults.
     * @param PurchaseOrderStatusCriteriaData the search criteria
     * @return a <code>PurchaseOrderDataVector</code>
     * @throws            RemoteException Required by EJB 1.0
     */
    public PurchaseOrderDataVector getPurchaseOrderCollection(
    PurchaseOrderStatusCriteriaData searchCrit) throws RemoteException;

    /**
     * Finds pos and returns a RealPurchaseOrderNumViewVector of 0 or moreresults.
     * @param PurchaseOrderStatusCriteriaData the search criteria
     * @return a <code>PurchaseOrderDataVector</code>
     * @throws            RemoteException Required by EJB 1.0
     */
    public RealPurchaseOrderNumViewVector getRealPurchaseOrderNum(int storeId, String poNum)
    throws RemoteException;

    /**
     * Returns a purchase order and it's associated properties and objects
     * @param purchaseOrderId the purchase order id
     * @return a <code>PurchaseOrderStatusDescDataView</code> object describing a purchase order
     * @throws            RemoteException Required by EJB 1.0
     */
    public PurchaseOrderStatusDescDataView getPurchaseOrderStatusDesc(int purchaseOrderId)
    throws RemoteException;

    /**
     * Populates a PurchaseOrderStatusDescDataView from the starting point of an invoice dist id with the posibility of there
     * not being an associated purchase order.
     * @param int pInvoiceDistId the invoice dist id.
     * @return a <code>PurchaseOrderStatusDescDataView</code>
     * @throws RemoteException Required by EJB 1.0
     */
    public PurchaseOrderStatusDescDataView getDistributorInvoiceDesc(int pInvoiceDistId) throws RemoteException;

    /**
     *Adds or updates a return request depending on if the return request id is populated.
     *@param user name of user doing modification
     *@returns ReturnRequestDescDataView with whatever changes reflected (new ids etc).
     *@throws RemoteException            RemoteException Required by EJB 1.0
     */
    public ReturnRequestDescDataView addUpdateReturnRequest(ReturnRequestDescDataView view,String userName) throws RemoteException;

    /**
     *Fetches the Return request out of the database.
     *@returns a populated ReturnRequestDescDataView object
     *@param the id of the return data to fetch from the database
     *@throws RemoteException            RemoteException Required by EJB 1.0
     */
    public ReturnRequestDescDataView getReturnRequestDetail(int returnId) throws RemoteException;

    /**
     *Saves all savable data from a ERPPurchaseOrderLineDescViewVector data vector.  This data can only
     *be saved if there is a valid reference within our Database, it will not modify erp data.
     *
     *@param ERPPurchaseOrderLineDescViewVector data to save
     *@param String pUserName user doing modification
     *@throws RemoteException            RemoteException Required by EJB 1.0
     */
    public void saveERPPurchaseOrderLineCollection(ERPPurchaseOrderLineDescViewVector pERPPurchaseOrderLineDescViewVector, String pUserName)
    throws RemoteException;

    /**
     *Updates the manifest information for this po and updates purchaseorder data passed in. Will do
     *inserts and updates of both data elements, but it only deals with the manifestItemData, and the
     *purchaseOrderData elements of the supplied PurchaseOrderStatusDescDataViewVector.
     *
     *@param pPurchaseOrdersWithManifests
     *@returns the supplied PurchaseOrderStatusDescDataViewVector with any changes made by the update
     */
    public PurchaseOrderStatusDescDataViewVector updateManifestPackagesAndPurchaseOrderData(PurchaseOrderStatusDescDataViewVector pPurchaseOrdersWithManifests)
    throws RemoteException;

    /**
     *Assigns the package ids to the passed in manifest records.  Does not save them, or update the database
     *in any way other than to modify the manifest counter.
     *@param pPurchaseOrdersWithManifests pruchase order view vector with their Manifests populated.
     *@return the passed in PurchaseOrderStatusDescDataViewVector with the manifest package ids populated.
     */
    public PurchaseOrderStatusDescDataViewVector assignManifestPackageIds(PurchaseOrderStatusDescDataViewVector pPurchaseOrdersWithManifests)
    throws RemoteException;

    /**
     *Mark the currently initiated manifest item records as ready to send to freight handler for
     *the specified distribution center and distributor ids supplied.
     *@param pDistributionCenterId the distribution center id to process this request for
     *@param pDistributorIds the list of distributor ids
     */
    public void processManifestComplete(String pDistributionCenterId,IdVector pDistributorIds)
    throws RemoteException;

    /**
     *Returns a OrderItemDescDataVector lightly populated.  The only data that is returned is the orderItemData object and the
     *invoiceDistDetail data object.
     *@param int pInvoiceDistId the distributor invoie id to take as the base for population.
     *@param int int pPurchaseOrderId the purchase order id to which this invoice belongs if avaliable
     *@rturns a lightly populated OrderItemDescDataVector
     */
    public OrderItemDescDataVector getDistriutorInvoiceItemDetailLightWeight(int pInvoiceDistId, int pPurchaseOrderId) throws RemoteException;


    /**
     *Simple method to update the data in the database based off the passed in InvoiceDistData.
     */
    public InvoiceDistData addUpdateInvoiceDistData(InvoiceDistData pInvoiceDist) throws RemoteException;

    /**
     *Encapsulates the business logice of updating a distributor invoice object.
     *@Returns the invoice dist id @see InvoiceDistData.getInvoiceDistId
     */
    public int processInvoiceDistUpdate(PurchaseOrderStatusDescDataView pInvoiceDesc,String userDoingMod,boolean approve)
    throws RemoteException, DuplicateNameException, SQLException;

    /**
     *Moves the invoice from the current assignment to a new po
     */
    public void reAssignInvoicePo(InvoiceDistData pInvoiceDist,PurchaseOrderData pPurchaseOrder, String pUserDoingMod) throws RemoteException;

    /**
     *Rejects an invoice from the system.  This will add any appropriate order item actions and re-set any changed quantities.  The invoice
     *remains in the system but is set to a "Rejected" status and all of the items qty are set to 0.
     *Assumes that the invoice in a state that should allow this action.
     */
     public InvoiceDistData rejectInvoice(InvoiceDistData pInvoiceDist, String pUserDoingMod) throws RemoteException;


     public PurchaseOrderData getOrderByErpPoNumSimple(String pStoreType,
                                                       IdVector pStoreIds,
                                                       IdVector pDistributorId,
                                                       String pErpPoNum,
                                                       IdVector pAccIds) throws RemoteException;

      /**
   *Simple method to update the data in the database.
   */
   public InvoiceDistData updateInvoiceDistData(InvoiceDistData pInvoiceDist) throws RemoteException;

   public List getInvoiceDistDetail(int orderItemId, String erpPoNum) throws RemoteException;

   public String getNewInvoiceNumForDistInvoiceLike(String invoiceNum, int distId) throws RemoteException;

   public BigDecimal getTotalAmountWhithALLOperations(OrderItemDataVector poItems, OrderData orderData) throws RemoteException;

   public PurchaseOrderData getPurchaseOrder(int orderId,String distrErpoNum,boolean throwDataNotFoundExc) throws RemoteException,DataNotFoundException;

   public String getDistributorPoType(int accountId) throws RemoteException;

   public String generateOutboundPoNum(int orderId,String distrErpNum,String erpoPoNum,String erpPoSuffix) throws RemoteException;

   public IdVector getOrdersWithoutOutboundPoNum(String pBegDate, String pEndDate)
   throws RemoteException;

   public void setOutboundPoNum(int pPurchaseOrderId, String pUserName)
   throws RemoteException;
   
   // method to find busunessEntityId off the Invoice Distributor Id (SVC)
   public int getBusEntityIdOffInvoiceDistId(int pInvoiceDistId)
   throws RemoteException;
   
   // method to update Invoice Status Code
   public void updateInvoiceStatusCd(int pInvoiceDistId, String invoiceStatusCd) throws RemoteException;

   // method to fetch ERP Accounts 
   public ArrayList fetchErpAccountNum(int pInvoiceDistId) throws RemoteException;

}
