package com.cleanwise.service.api.session;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;

import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.TaxCalculationException;
import com.cleanwise.service.api.value.AddressData;
import com.cleanwise.service.api.value.BackorderDataVector;
import com.cleanwise.service.api.value.CostCenterData;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.InvoiceCreditCardTransViewVector;
import com.cleanwise.service.api.value.InvoiceCustCritView;
import com.cleanwise.service.api.value.InvoiceCustData;
import com.cleanwise.service.api.value.InvoiceCustDataVector;
import com.cleanwise.service.api.value.InvoiceCustDescDataVector;
import com.cleanwise.service.api.value.InvoiceCustDetailDataVector;
import com.cleanwise.service.api.value.InvoiceCustDetailDescDataVector;
import com.cleanwise.service.api.value.InvoiceCustViewVector;
import com.cleanwise.service.api.value.InvoiceDistData;
import com.cleanwise.service.api.value.InvoiceDistDataVector;
import com.cleanwise.service.api.value.InvoiceDistDetailData;
import com.cleanwise.service.api.value.InvoiceDistDetailDataVector;
import com.cleanwise.service.api.value.InvoiceDistDetailDescDataVector;
import com.cleanwise.service.api.value.ItemRequestData;
import com.cleanwise.service.api.value.ItemSubstitutionData;
import com.cleanwise.service.api.value.JanitorClosetData;
import com.cleanwise.service.api.value.JdOrderStatusViewVector;
import com.cleanwise.service.api.value.OrderAddressData;
import com.cleanwise.service.api.value.OrderCreditCardData;
import com.cleanwise.service.api.value.OrderCreditCardDescData;
import com.cleanwise.service.api.value.OrderData;
import com.cleanwise.service.api.value.OrderDataVector;
import com.cleanwise.service.api.value.OrderHistoryDataVector;
import com.cleanwise.service.api.value.OrderInfoDataView;
import com.cleanwise.service.api.value.OrderItemActionData;
import com.cleanwise.service.api.value.OrderItemData;
import com.cleanwise.service.api.value.OrderItemDataVector;
import com.cleanwise.service.api.value.OrderItemDescData;
import com.cleanwise.service.api.value.OrderItemDescDataVector;
import com.cleanwise.service.api.value.OrderJoinData;
import com.cleanwise.service.api.value.OrderPropertyData;
import com.cleanwise.service.api.value.OrderPropertyDataVector;
import com.cleanwise.service.api.value.OrderStatusCriteriaData;
import com.cleanwise.service.api.value.OrderStatusDescData;
import com.cleanwise.service.api.value.OrderStatusDescDataVector;
import com.cleanwise.service.api.value.ReorderItemData;
import com.cleanwise.service.api.value.ReplacedOrderItemViewVector;
import com.cleanwise.service.api.value.ReplacedOrderViewVector;
import com.cleanwise.service.api.value.SiteData;
import com.cleanwise.service.api.value.TaxQuery;
import com.cleanwise.service.api.value.TaxQueryResponse;
import com.cleanwise.service.api.value.UserData;
import com.cleanwise.service.api.value.WorkOrderData;

/**
 * Describe interface <code>Order</code> here.
 *
 * @author <a href="mailto:dvieira@DVIEIRA"></a>
 */
public interface Order extends javax.ejb.EJBObject
{

    public final static String
	BYPASS_ORDER_ROUTING = "BypassOrderRouting";
    public final static String
	PENDING_DATE = "Pending Date";
    public final static String
	REQUESTED_SHIP_DATE = "Requested Ship Date";
    public final static String
	MODIFICATION_STARTED = "Modification Started";

  /**
   * Adds the janitor closet item information values to be used by the request.
   * @param pJanitorClosetItem  the janitor closet item data.
   * @param request  the item request data.
   * @return new ItemRequestData()
   * @throws            RemoteException Required by EJB 1.0
   */
  public ItemRequestData addJanitorClosetItem(JanitorClosetData pJanitorClosetItem,
                ItemRequestData request)
      throws RemoteException;



  /**
   * Creates the order information values to be used by the request.
   * @param pData  the order  data.
   * @return OrderData
   * @throws            RemoteException Required by EJB 1.0
   */
  public OrderData createOrder(OrderData pData)
      throws RemoteException;

  /**
   * Creates the order information values to be used by the request.
   * @param pItemId  the item identifier.
   * @param pBusEntityId  the business entity identifier.
   * @return OrderData
   * @throws            RemoteException Required by EJB 1.0
   */
  public OrderData createOrder(int pItemId, int pBusEntityId)
      throws RemoteException;

  /**
   * Generates the order table  to be used by the request.
   * @param pOrder  the order data.
   * @return java.util.Hashtable
   * @throws            RemoteException Required by EJB 1.0
   */
  public Hashtable generateOrderTable(OrderData pOrder)
      throws RemoteException;

  /**
   * Gets the item price information to be used by the request.
   * @param pOrder  the order data.
   * @return double
   * @throws            RemoteException Required by EJB 1.0
   */
  public double getItemPrice(OrderData pOrder)
      throws RemoteException;

  /**
   * Gets the item contract price information to be used by the request.
   * @param pOrder  the order data.
   * @return double
   * @throws            RemoteException Required by EJB 1.0
   */
  public double getItemContractPrice(OrderData pOrder)
      throws RemoteException;

  /**
   * Gets the order information to be used by the request.
   * @param pItem  the re-order item data.
   * @return OrderData
   * @throws            RemoteException Required by EJB 1.0
   */
  public OrderData getOrder(ReorderItemData pItem)
      throws RemoteException;

  /**
   * Gets the item quantity information to be used by the request.
   * @param pOrder  the order data.
   * @return int
   * @throws            RemoteException Required by EJB 1.0
   */
  public int getItemQty(OrderData pOrder)
      throws RemoteException;


  /**
   * Initializes order number for the store, if it does not exist (temporary decesion until admin service would do it)
   * @param pStoreId the store identifier
   * @param pUser  the user login name
   * @throws            RemoteException
   */
   public void initOrderNumber(int pStoreId, String pUser)
      throws RemoteException;

    /**
     * Get order status data vector according to the orderStatusCriteriaData
     * @param pOrderStatusCriteria an <code>OrderStatusCriteriaData</code> value
     * @return an <code>OrderDataVector</code> value
     * @exception            RemoteException
     */
    public OrderDataVector getOrderStatusCollection(OrderStatusCriteriaData pOrderStatusCriteria)
        throws RemoteException;


    /**
     * Get exceptional order data vector according to the orderStatusCriteriaData
     * @param pOrderStatusCriteria an <code>OrderStatusCriteriaData</code> value
     * @return an <code>OrderDataVector</code> value
     * @exception            RemoteException
     */
    public OrderDataVector getExceptionOrderCollection(OrderStatusCriteriaData pOrderStatusCriteria)
        throws RemoteException;


    /**
     * Get order status data according to the orderStatusId
     * @param pOrderId an <code>int</code> value
     * @return an <code>OrderData</code> value
     * @exception RemoteException if an error occurs
     * @exception DataNotFoundException if an error occurs
     * @exception            RemoteException, DataNotFoundException
     */
    public OrderData getOrderStatus(int pOrderId)
        throws RemoteException, DataNotFoundException;

    /**
     * Get order status Desc data according to the orderStatusId
     * @param pOrderId an <code>int</code> value
     * @return an <code>OrderStatusDescData</code> value
     * @exception RemoteException if an error occurs
     * @exception DataNotFoundException if an error occurs
     * @exception            RemoteException, DataNotFoundException
     */
    public OrderStatusDescData getOrderStatusDesc(int pOrderId)
        throws RemoteException, DataNotFoundException;

    /**
     * Get exception order status Desc data according to the orderId
     * @param pOrderId an <code>int</code> value
     * @return an <code>OrderStatusDescData</code> value
     * @exception RemoteException if an error occurs
     * @exception DataNotFoundException if an error occurs
     * @exception            RemoteException, DataNotFoundException
     */
    public OrderStatusDescData getExceptionOrderDesc(int pOrderId)
        throws RemoteException, DataNotFoundException;

    /**
     * Get order item data vector according to the orderId
     * @param pOrderId the order Id to search the order
     * @return an <code>OrderItemDataVector</code> value
     * @exception            RemoteException
     */
    public OrderItemDataVector getOrderItemCollection(int pOrderId)
        throws RemoteException;

    /**
     * Get order item data vector according to the orderItemIds
     * @param pOrderItemIds the order item Ids
     * @return an <code>OrderItemDataVector</code> value
     * @exception            RemoteException
     */
    public OrderItemDataVector getOrderItemCollection(IdVector pOrderItemIds)
        throws RemoteException;


    /**
     * Get order item data vector according to the orderId and erpPoNum
     * @param pOrderId the order Id to search the order item
     * @param pErpPoNum the erpPo to search the order item
     * @return an <code>OrderItemDataVector</code> value
     * @exception            RemoteException
     */
    public OrderItemDataVector getOrderItemCollection(int pOrderId,
                                                      String pErpPoNum)
        throws RemoteException;

    /**
     * Get order item  data according to the orderItemId
     * @param pOrderItemId the order item Id to search the order item
     * @return an <code>OrderItemData</code> value
     * @exception RemoteException if an error occurs
     * @exception DataNotFoundException if an error occurs
     * @exception            RemoteException, DataNotFoundException
     */
    public OrderItemData getOrderItem(int pOrderItemId)
        throws RemoteException, DataNotFoundException;


     /**Get order item desc data vector according to the orderId
     * @param pOrderId the order item Id to search the order item (optional)
     * @param pErpPoNum the erp po number (optional)
     * @param pPurchaseOrderId the po id  (optional)
     * @throws            RemoteException
     */
    public OrderItemDescDataVector getOrderItemDescCollection(int pOrderId,
                                                              String pErpPoNum,
                                                              int pPurchaseOrderId)
        throws RemoteException;


    /**Get order item desc data vector according to the orderId
     * @param pOrderId the order Id to search the order item
     * @return an <code>OrderItemDescDataVector</code> value
     * @exception            RemoteException
     */
    public OrderItemDescDataVector getOrderItemDescCollection(int pOrderId)
    throws RemoteException;




    /**
     * Get order item desc data vector according to the orderId
     * @param pOrderId the order Id to search the order (Requiered)
     * @param pErpPoNum the erp po number
     * @throws            RemoteException
     * @param pOrderId the order Id to search the order
     * @param pErpPoNum a <code>String</code> value
     * @return an <code>OrderItemDescDataVector</code> value
     * @exception            RemoteException
     */
    public OrderItemDescDataVector getOrderItemDescCollection(int pOrderId,
                                                              String pErpPoNum)
        throws RemoteException;

    /**
     * Get order status desc data vector according to the orderStatusCriteriaData
     * @param pOrderStatusCriteria an <code>OrderStatusCriteriaData</code> value
     * @return an <code>OrderStatusDescDataVector</code> value
     * @exception            RemoteException
     */
    public OrderStatusDescDataVector getOrderStatusDescCollection_OLD(OrderStatusCriteriaData pOrderStatusCriteria)
        throws RemoteException ;
    
    /**
     * Get order status desc data vector according to the
     * orderStatusCriteriaData and pStoreIds
     * 
     * @param pOrderStatusCriteria
     *            an <code>OrderStatusCriteriaData</code> value
     *@param pStoreIds
     *            an <code>IdVector</code> value
     * @return an <code>OrderStatusDescDataVector</code> value
     * @exception RemoteException
     */
    public OrderStatusDescDataVector getOrderStatusDescCollection(
            OrderStatusCriteriaData pOrderStatusCriteria,
            IdVector pStoreIds)
            throws RemoteException;
    
    /**
     * Get order status desc data vector according to the orderStatusCriteriaData
     * 
     * @param pOrderStatusCriteria    an <code>OrderStatusCriteriaData</code> value
     * @return an <code>OrderStatusDescDataVector</code> value
     * @exception RemoteException
     */
    public OrderStatusDescDataVector getOrderStatusDescCollection(
            OrderStatusCriteriaData pOrderStatusCriteria) throws RemoteException;

    /**
     * Get a light weight order status desc data vector according to the orderStatusCriteriaData
     * @param pOrderStatusCriteria an <code>OrderStatusCriteriaData</code> value
     * @return an <code>OrderStatusDescDataVector</code> value
     * @exception            RemoteException
     */
    public OrderStatusDescDataVector getLightOrderStatusDescCollection(OrderStatusCriteriaData pOrderStatusCriteria)
        throws RemoteException ;

    /**
     * Get Jd order status desc data vector according to the orderStatusCriteriaData
     * @param pOrderStatusCriteria an <code>OrderStatusCriteriaData</code> value
     * @return an <code>OrderStatusDescDataVector</code> value
     * @exception            RemoteException
     */
    public JdOrderStatusViewVector getJdOrderStatusDescCollection(OrderStatusCriteriaData pOrderStatusCriteria)
        throws RemoteException;

    /**
     * Get exceptional order status desc data vector according to the orderStatusCriteriaData
     * @param pOrderStatusCriteria an <code>OrderStatusCriteriaData</code> value
     * @return an <code>OrderStatusDescDataVector</code> value
     * @exception            RemoteException
     */
    public OrderStatusDescDataVector getExceptionOrderDescCollection
	(OrderStatusCriteriaData pOrderStatusCriteria)
        throws RemoteException;

    /**
     * Add a note to a particular order
     * @param pOrderPropertyData the order property data to set
     * @return an <code>OrderPropertyData</code> value
     * @exception RemoteException if an error occurs
     * @exception DataNotFoundException if an error occurs
     * @exception            RemoteException, DataNotFoundException
     */
    public OrderPropertyData addNote(OrderPropertyData pOrderPropertyData)
        throws RemoteException, DataNotFoundException;


    /**
     * Get order property data vector according to the orderId
     * @param pOrderId the order status Id to search the order property
     * @param pPropertyTypeCd a <code>String</code> value
     * @return an <code>OrderPropertyDataVector</code> value
     * @exception RemoteException if an error occurs
     * @exception DataNotFoundException if an error occurs
     * @exception            RemoteException, DataNotFoundException
     */
    public OrderPropertyDataVector getOrderPropertyCollection(int pOrderId,
                                                              String pPropertyTypeCd)
        throws RemoteException, DataNotFoundException;

    /**
     * Get order property data vector according to the orderItemId
     * @param pOrderItemId an <code>int</code> value
     * @param pPropertyTypeCd a <code>String</code> value
     * @return an <code>OrderPropertyDataVector</code> value
     * @exception RemoteException if an error occurs
     * @exception DataNotFoundException if an error occurs
     * @exception            RemoteException, DataNotFoundException
     */
    public OrderPropertyDataVector getOrderItemPropertyCollection(int pOrderItemId,
                                                              String pPropertyTypeCd)
        throws RemoteException, DataNotFoundException;

    /**
     * Get order property data according to the orderPropertyId
     * @param pOrderPropertyId an <code>int</code> value
     * @param pPropertyTypeCd a <code>String</code> value
     * @return an <code>OrderPropertyDataVector</code> value
     * @exception RemoteException if an error occurs
     * @exception DataNotFoundException if an error occurs
     * @exception            RemoteException, DataNotFoundException
     */
    public OrderPropertyData getOrderProperty(int pOrderPropertyId,
                                              String pPropertyTypeCd)
        throws RemoteException, DataNotFoundException;


    /**
     * Get order property data according to the orderStatusId
     * @param pOrderStatusId an <code>int</code> value
     * @return an <code>OrderPropertyDataVector</code> value
     * @exception RemoteException if an error occurs
     * @exception DataNotFoundException if an error occurs
     * @exception            RemoteException, DataNotFoundException
     */
    public OrderPropertyDataVector getOrderPropertyVec(int pOrderStatusId)
        throws RemoteException, DataNotFoundException;


    /**
     * Get order property data vector of order item detail according to the orderItemDetailId
     * @param pOrderItemDetailId the order item detail Id to search the order property
     * @return an <code>OrderPropertyDataVector</code> value
     * @exception RemoteException if an error occurs
     * @exception DataNotFoundException if an error occurs
     * @exception            RemoteException, DataNotFoundException
     */
    public OrderPropertyDataVector getOrderPropertyCollectionByItemDetail(int pOrderItemDetailId)
        throws RemoteException, DataNotFoundException;


    /**
     * Get order item  desc data according to the orderitemId
     * @param pOrderItemId the order item Id to search the order item
     * @return an <code>OrderItemDescData</code> value
     * @exception            RemoteException
     */
    public OrderItemDescData getOrderItemDescByItem(int pOrderItemId)
        throws RemoteException;

    /**
     * Get invoiceDistDataVector according to the OrderId
     * @param pOrderId the order status Id to search the invoiceDist
     * @return an <code>InvoiceDistDataVector</code> value
     * @exception            RemoteException
     */
    public InvoiceDistDataVector getInvoiceDistCollection(int pOrderId)
        throws RemoteException;

    /**
     * Get invoiceDistDataVector according to the OrderItemId
     * @param pOrderItemId the order status Id to search the invoiceDist
     * @return an <code>InvoiceDistDataVector</code> value
     * @exception            RemoteException
     */
    public InvoiceDistDataVector getInvoiceDistCollectionByItem(int pOrderItemId)
        throws RemoteException;

    /**
     * Get invoiceDist data according to the invoiceDistId
     * @param pInvoiceDistId the invoiceDist Id to search the invoiceDist
     * @return an <code>InvoiceDistData</code> value
     * @exception RemoteException if an error occurs
     * @exception DataNotFoundException if an error occurs
     * @exception            RemoteException, DataNotFoundException
     */
    public InvoiceDistData getInvoiceDist(int pInvoiceDistId)
        throws RemoteException, DataNotFoundException;

    /**
     * Get invoiceDistDetailDesc data vector according to the invoiceDistId
     * @param pInvoiceDistId the invoice dist Id to search the invoiceDistDetail
     * @return an <code>InvoiceDistDetailDescDataVector</code> value
     * @exception            RemoteException
     */
    public InvoiceDistDetailDescDataVector getInvoiceDistDetailDescCollection(int pInvoiceDistId)
        throws RemoteException;


    /**
     * Get invoiceCustDataVector according to the OrderId
     * @param pOrderId the order status Id to search the invoiceCust
     * @return an <code>InvoiceCustDataVector</code> value
     * @exception            RemoteException
     */
    public InvoiceCustDataVector getInvoiceCustCollection(int pOrderId)
        throws RemoteException;


    /**
     * Get InvoiceCustViewVector according to the supplied criteria object
     * @param pCrit the criteria
     * @param pHeavyWeight wheather you want the detail popultated or left null
     * @return an <code>InvoiceCustViewVector</code> value
     * @exception            RemoteException
     */
    public InvoiceCustViewVector getInvoiceCustViewCollection(InvoiceCustCritView pCrit,boolean pHeavyWeight)
    throws RemoteException;

    /**
     * Get invoiceCust data according to the invoiceCustId
     * @param pInvoiceCustId the invoiceCust Id to search the invoiceCust
     * @return an <code>InvoiceCustData</code> value
     * @exception RemoteException if an error occurs
     * @exception DataNotFoundException if an error occurs
     * @exception            RemoteException, DataNotFoundException
     */
    public InvoiceCustData getInvoiceCust(int pInvoiceCustId)
        throws RemoteException, DataNotFoundException;


    /**
     * Get invoiceCustDetail data vector according to the orderId
     * @param pOrderId the order Id to search the invoiceCustDetail
     * @return an <code>InvoiceCustDetailDescDataVector</code> value
     * @exception            RemoteException
     */
    public InvoiceCustDetailDataVector getInvoiceCustDetailCollectionByOrder(int pOrderId)
        throws RemoteException;

    /**
     * Get invoiceCustDetailDesc data vector according to the invoiceCustId
     * @param pInvoiceCustId the invoice dist Id to search the invoiceCustDetail
     * @return an <code>InvoiceCustDetailDescDataVector</code> value
     * @exception            RemoteException
     */
    public InvoiceCustDetailDescDataVector getInvoiceCustDetailDescCollection(int pInvoiceCustId)
        throws RemoteException;


    /**
     * Get invoiceCustDetailDesc data vector according to the invoiceCustId
     * @param pInvoiceCustId the invoice dist Id to search the invoiceCustDetail
     * @return an <code>InvoiceCustDetailDescDataVector</code> value
     * @exception            RemoteException
     */
     public InvoiceCustDetailDataVector getInvoiceCustDetailCollection(int pInvoiceCustId)
        throws RemoteException;


    /**
     * Get order item detail data, and order status data according to the date range supplied
     * Returns only items in Order_item_detail_table where action == 2.
     * @param DateRangeBegin a <code>String</code> value
     * @param DateRangeEnd a <code>String</code> value
     * @return an <code>OrderHistoryDataVector</code> value
     * @exception            RemoteException
     */
    public OrderHistoryDataVector getOrderHistoryCollection(String DateRangeBegin, String DateRangeEnd)        	 throws RemoteException;


    /**
     * Describe <code>addOrder</code> method here.
     *
     * @param pOrderData a <code>OrderData</code> value
     * @return a <code>OrderData</code> value
     * @exception RemoteException if an error occurs
     */
    public OrderData addOrder(OrderData pOrderData)
	throws RemoteException;

    /**
     * Updates the Order information values to be used by the request.
     * @param pOrderData  the OrderData .
     * @return a <code>OrderData</code> value
     * @throws            RemoteException Required by EJB 1.0
     */
    public OrderData updateOrder(OrderData pOrderData)
	throws RemoteException;

    /**
     * Describe <code>addOrderAddress</code> method here.
     *
     * @param pOrderAddressData a <code>OrderAddressData</code> value
     * @return a <code>OrderAddressData</code> value
     * @exception RemoteException if an error occurs
     */
    public OrderAddressData addOrderAddress(OrderAddressData pOrderAddressData)
	throws RemoteException;

    /**
     * Updates the OrderAddress information values to be used by the request.
     * @param pOrderAddressData  the OrderAddressData .
     * @return a <code>OrderAddressData</code> value
     * @throws            RemoteException Required by EJB 1.0
     */
    public OrderAddressData updateOrderAddress(OrderAddressData pOrderAddressData)
	throws RemoteException;


    /**
     * Updates the OrderItem information values to be used by the request.
     * @param pOrderItemData  the OrderItemData .
     * @return a <code>OrderItemData</code> value
     * @throws            RemoteException Required by EJB 1.0
     */
    public OrderItemData updateOrderItem(OrderItemData pOrderItemData)
	throws RemoteException;


    /**
     * Updates the OrderItem collection information values to be used by the request.
     * @param pOrderItemDataVector  the OrderItemDataVector .
     * @return a <code>OrderItemDataVector</code> value
     * @throws            RemoteException Required by EJB 1.0
     */
    public OrderItemDataVector updateOrderItemCollection(OrderItemDataVector pOrderItemDataVector)
	throws RemoteException;

    /**
     * Updates consolidated order item collection
     * @param pOrderItemDataVector  the OrderItemDataVector .
     * @param pReplacedOrderItems  replaced order items with modified quantities
     * @return a <code>OrderItemDataVector</code> value
     * @throws            RemoteException Required by EJB 1.0
     */
    public OrderItemDataVector updateOrderItemCollection
          (OrderItemDataVector pOrderItemDataVector,
           ReplacedOrderItemViewVector pReplacedOrderItems,
           String pUserName)
    throws RemoteException;

    /**
     * Updates the InvoiceDistDetail collection information values to be used by the request.
     * @param pInvoiceDistDetailDataVector  the InvoiceDistDetailDataVector .
     * @return a <code>InvoiceDistDetailDataVector</code> value
     * @throws            RemoteException Required by EJB 1.0
     */
    public InvoiceDistDetailDataVector updateInvoiceDistDetailCollection(InvoiceDistDetailDataVector pInvoiceDistDetailDataVector)
	throws RemoteException;

    /**
     * Updates the InvoiceCustDetail collection information values to be used by the request.
     * @param pInvoiceCustDetailDataVector  the InvoiceCustDetailDataVector .
     * @return a <code>InvoiceCustDetailDataVector</code> value
     * @throws            RemoteException Required by EJB 1.0
     */
    public InvoiceCustDetailDataVector updateInvoiceCustDetailCollection(InvoiceCustDetailDataVector pInvoiceCustDetailDataVector)
	throws RemoteException;


    /**
     * Describe <code>addOrderItemAction</code> method here.
     *
     * @param pOrderItemActionData a <code>OrderItemActionData</code> value
     * @return a <code>OrderItemActionData</code> value
     * @exception RemoteException if an error occurs
     */
    public OrderItemActionData addOrderItemAction(OrderItemActionData pOrderItemActionData)
	throws RemoteException;

    /**
     * Updates the OrderItemAction information values to be used by the request.
     * @param pOrderItemActionData  the OrderItemActionData .
     * @return a <code>OrderItemActionData</code> value
     * @throws            RemoteException Required by EJB 1.0
     */
    public OrderItemActionData updateOrderItemAction(OrderItemActionData pOrderItemActionData)
	throws RemoteException;

    /**
     * Describe <code>addItemSubstitution</code> method here.
     *
     * @param pItemSubstitutionData a <code>ItemSubstitutionData</code> value
     * @return a <code>ItemSubstitutionData</code> value
     * @exception RemoteException if an error occurs
     */
    public ItemSubstitutionData addItemSubstitution(ItemSubstitutionData pItemSubstitutionData)
	throws RemoteException;

    /**
     * Updates the ItemSubstitution information values to be used by the request.
     * @param pItemSubstitutionData  the ItemSubstitutionData .
     * @return a <code>ItemSubstitutionData</code> value
     * @throws            RemoteException Required by EJB 1.0
     */
    public ItemSubstitutionData updateItemSubstitution(ItemSubstitutionData pItemSubstitutionData)
	throws RemoteException;



    /**
     * Describe <code>addInvoiceDistDetail</code> method here.
     *
     * @param pInvoiceDistDetailData a <code>InvoiceDistDetailData</code> value
     * @return a <code>InvoiceDistDetailData</code> value
     * @exception RemoteException if an error occurs
     */
    public InvoiceDistDetailData addInvoiceDistDetail(InvoiceDistDetailData pInvoiceDistDetailData)
	throws RemoteException;

    /**
     * Updates the InvoiceDistDetail information values to be used by the request.
     * @param pInvoiceDistDetailData  the InvoiceDistDetailData .
     * @return a <code>InvoiceDistDetailData</code> value
     * @throws            RemoteException Required by EJB 1.0
     */
    public InvoiceDistDetailData updateInvoiceDistDetail(InvoiceDistDetailData pInvoiceDistDetailData)
	throws RemoteException;


    /**
     * Describe <code>addInvoiceCust</code> method here.
     *
     * @param pInvoiceCustData a <code>InvoiceCustData</code> value
     * @return a <code>InvoiceCustData</code> value
     * @exception RemoteException if an error occurs
     */
    public InvoiceCustData addInvoiceCust(InvoiceCustData pInvoiceCustData)
	throws RemoteException;

    /**
     * Updates the InvoiceCust information values to be used by the request.
     * @param pInvoiceCustData  the InvoiceCustData .
     * @return a <code>InvoiceCustData</code> value
     * @throws            RemoteException Required by EJB 1.0
     */
    public InvoiceCustData updateInvoiceCust(InvoiceCustData pInvoiceCustData)
	throws RemoteException;

    /**
     * Get address data according to the addressId
     * @param pAddressId the address Id to search the address
     * @param pAddressTypeCd a <code>String</code> value
     * @return an <code>AddressData</code> value
     * @exception RemoteException if an error occurs
     * @exception DataNotFoundException if an error occurs
     * @exception            RemoteException, DataNotFoundException
     */
    public AddressData getAddress(int pAddressId,
                                  String pAddressTypeCd)
        throws RemoteException, DataNotFoundException;

    /**
     * Gets the current backorders in the system.
     * @param sqlBuf a <code>StringBuffer</code> value
     * @return a <code>OrderHistoryDataVector</code> value
     * @exception    RemoteException Required by EJB 1.0
     */
    public OrderHistoryDataVector getTroubledOrders(StringBuffer sqlBuf)
	throws RemoteException;

  /**
  * Saves to database the order schedule
  * @param pBusEntityId  the site id
  * @param pUserId the user id
  * @param pOrderScheduleView the schedule data
  * @param pUser the user logon name
  * @return the order schedule identifier
  * @throws   RemoteException Required by EJB 1.0
  */
//  public int saveOrderSchedule(int pBusEntityId, int pUserId, OrderScheduleViewData pOrderScheduleView, String pUser)
//  throws RemoteException;


  /**
  * Gets the list of Order schedules
  * @param pBusEntityId  the site id
  * @param pUserId the user id
  * @return a list of OrderScheduleViewData objects
  * @throws   RemoteException Required by EJB 1.0
  */
//  public List getOrderSchedules(int pBusEntityId, int pUserId)
//  throws RemoteException;

  /**
  * Deletes to database the order schedule
  * @param pOrderScheduleId the order schedule id
  * @param pUser the user logon name
  * @throws   RemoteException Required by EJB 1.0
  */
//  public void deleteOrderSchedule(int pOrderScheduleId, String pUser)
//  throws RemoteException;

    /**
     * Describe <code>fetchOrder</code> method here.
     *
     * @param pOrderId an <code>int</code> value
     * @return an <code>OrderJoinData</code> value
     * @exception RemoteException if an error occurs
     * @exception DataNotFoundException if an error occurs
     */
    public OrderJoinData fetchOrder(int pOrderId)
      throws RemoteException, DataNotFoundException;

    /**
     * Sets PENDING_DATE order status and generates ORDER_META and WORKFLOW_QUEUE entries.
     *
     * @param pOrderId an order identifier
     * @param pStringDate a date when order must be processed
     * @param pModUserName a user name
     * @return an <code>OrderJoinData</code> value
     * @exception RemoteException if an error occurs
     * @exception DataNotFoundException if an error occurs
     */
    public OrderJoinData setPendingDate(int pOrderId, String pStringDate,
                                         String pModUserName)
      throws RemoteException, DataNotFoundException;
    /**
     * Describe <code>updateOrderInfo</code> method here.
     *
     * @param pOrderId an <code>int</code> value
     * @param pUpdateReq a <code>String</code> value
     * @param pModUserName a <code>String</code> value
     * @return an <code>OrderJoinData</code> value
     * @exception RemoteException if an error occurs
     * @exception DataNotFoundException if an error occurs
     */
    public OrderJoinData updateOrderInfo(int pOrderId, String pUpdateReq,
				       String pModUserName)
      throws RemoteException, DataNotFoundException;

    /**
     * Approves orders having PENDING_APPROVAL or PENDING_DATE status
     *
     * @param pOrderId an <code>int</code> value
     * @param pPropertyIdV list of approved properties
     * @param pPendingDate approve on date (null if immediately
     * @param pRequestPoNum customer PO number
     * @param pUserId the user id
     * @param pUserName the user login name
     * @return an <code>OrderJoinData</code> value
     * @exception RemoteException if an error occurs
     * @exception DataNotFoundException if an error occurs
     */
    public OrderJoinData approveOrder(int pOrderId,
            IdVector pPropertyIdV,
            Date pPendingDate,
            String pRequestPoNum,
            int pUserId, String pUserName)
    throws RemoteException;
    /**
     * Get invoiceCust desc data vector according to the orderId
     * @param pOrderId the order Id to search the order item
     * @return an <code>InvoiceCustDescDataVector</code> value
     * @exception            RemoteException
     */
    public InvoiceCustDescDataVector getInvoiceCustDescCollection(int pOrderId)
        throws RemoteException;


    /**
     *<code>deleteOrderItemActions</code> "Deletes" the selected order item actions.  This
     *will remove them from the distributor and the customer display and certain reports
     *and add them as a historical status that admins and customer service users can view.
     *
     *@param pOrderItemActionIdsToDelete the order item action ids to delete
     *@param pUserName the user name of the user who is deleting the order item actions (requiered)
     *@throws RemoteException on any error
     */
    public void deleteOrderItemActions(IdVector pOrderItemActionIdsToDelete, String pUserName)
    throws RemoteException;

     /**
     *Updates or adds the writeable proeperties of an orderItemDesc Object.
     *@params pOrderItemDescList the OrderItemDescDataVector to be updated.
     *@param pName the user name updating the item
     *@return an updated <code>OrderItemDescDataVector</code> value (if any changes were made)
     *@exception            RemoteException
     */
    public OrderItemDescDataVector updateOrderItemDescCollection(
        OrderItemDescDataVector pOrderItemDescList,String pName)throws RemoteException;

    /**
      * Get invoiceDistDataVector according to the search criteria.
      * @param
      * @return an <code>InvoiceDistDataVector</code> value
      * @exception            RemoteException
      */
     public InvoiceDistDataVector getInvoiceDistCollection
         (Date pInvoiceSearchStartDate,
          Date pInvoiceSeaqrchEndDate, String pDateSearchType,
          String pErpPoNum, String pInvoiceNum,
      String pVoucherNumber,
      String pOptionalInvoiceStatusCd )
        throws RemoteException;


    /**
     * Get invoiceDistDataVector according to the search criteria.
     * @param
     * @return an <code>InvoiceDistDataVector</code> value
     * @exception            RemoteException
     */
    public InvoiceDistDataVector getInvoiceDistCollectionForStore
	(Date pInvoiceSearchStartDate,
	 Date pInvoiceSeaqrchEndDate, String pDateSearchType,
	 String pErpPoNum, String pInvoiceNum,
     String pVoucherNumber,
     String pOptionalInvoiceStatusCd, Integer pStoreId )
        throws RemoteException;

    public InvoiceDistDetailDataVector getInvoiceDistDetailCollection
    (int pInvoiceDistId)
    throws RemoteException;

    /**
    * Get invoiceCustDataVector according to the pCitTransactionDate,
    * pCitAssignmentNumber and pInvoiceNum
    * @param pCitTransactionDate the CIT transaction date to search the related CIT invoices
    * @param pCitAssignmentNumber the CIT assignment num to search the related CIT invoices
    * @param pInvoiceNum the invoice num to search the related CIT invoices
    * @param pInvoiceStatusCd the invoice status cd
    * @param pCitStatusCd the cit status cd
    * @return an <code>InvoiceCustDataVector</code> value
    * @exception            RemoteException
    */
    public InvoiceCustDataVector getInvoiceCustCollection(Date pCitTransactionDate,
                                                          int pCitAssignmentNumber,
                                                          String pInvoiceNum,
                                                          String pInvoiceStatusCd,
                                                          String pCitStatusCd)
    throws RemoteException;

    public OrderPropertyDataVector getNotes(int pId, String pPropertyTypeCd)
        throws RemoteException, DataNotFoundException;

    /**
     *  Description of the Method
     *
     *@param  pOrderItemIdV  Description of Parameter
     *@exception  RemoteException     Description of Exception
     */
/*
    public void removeOrderItems(IdVector pOrderItemIdV)
             throws RemoteException;
*/
    /**
     *  Description of the Method
     *@param pOrder order
     *@param  pOrderItemIdV  Description of Parameter
     *@param pUser the user login name
     *@exception  RemoteException     Description of Exception
     */
    public void cancelOrderItems(OrderData pOrder, IdVector pOrderItemIdV, String pUser
				 , boolean pDoFreightUpdate )
    throws RemoteException;


    public void updateInvoiceVoucherNumber
	(int pInvoiceDistId, String pNewVoucherNumber, String pUsername)
        throws RemoteException, DataNotFoundException;


    /**
     *Returns all of the order properties associated with the specified invoice id.
     *@param pInvoiceDistId the invoice id
     *@param pPropertyType <i>Optional</i> the property type, if null all propertie types will be retrieved
     *@returns a populated OrderPropertyDataVector
     */
    public OrderPropertyDataVector getOrderPropertyCollectionByInvoiceDist(int pInvoiceDistId,String pPropertyType)
    throws RemoteException;

    public String fetchInvoiceVoucherNumber
    (int pInvoiceDistId) throws RemoteException;

     /**
     *  Description of the Method
     *
     *@param  pOrderId
     *@param pUser the user login name
     *@exception  RemoteException     Description of Exception
     */
    public void cancelOrder(int pOrderId, String pUser)
    throws RemoteException;


    /**
     *  Updates order items, if they were not canceled.
     *  Initiates request to change item in Lawson (cost and quantiy so far)
     *@param pOrder order
     *@param  pOrderItemDV the list of modified OrderItemData objects
     *@param pOrderPropertyDV the list of item quntity modification requests
     *@param pUser the user login name
     *@exception  RemoteException     Description of Exception
     */
    /*
    public void updateOrderSentToErp(OrderData pOrder, OrderItemDataVector pOrderItemDV,
                                     OrderPropertyDataVector pOrderPropertyDV,
                                     String pUser)
    throws RemoteException;
*/
    /**
     *  Updates order items, if they were not canceled.
     *  Initiates request to change item in Lawson (cost and quantiy so far)
     *@param pOrder order
     *@param  pOrderItemDV the list of modified OrderItemData objects
     *@param pOrderPropertyDV the list of item quntity modification requests
     *@param pUser the user login name
     *@exception  RemoteException     Description of Exception
     */
    public void updateOrderSentToErp(OrderData pOrder,
                                     OrderItemDataVector pOrderItemDV,
                                     OrderPropertyDataVector pOrderPropertyDV,
                                     ReplacedOrderItemViewVector pReplacedOrderItemVwV,
                                     String pUser)
    throws RemoteException;



    public void setMetaAttribute(int pOrderId,
				 String pMetaDataShortDesc,
				 String pValue,
				 String pUser)
	throws RemoteException;

    public void removeMetaAttribute(int pOrderId,
                                    String pMetaDataShortDesc,
                                    String pUser) throws RemoteException;

    /**
     * Gets approved orders
     * @return the set of approved orders
     * @throws   RemoteException, LawsonException
     */
    public OrderDataVector getApprovedOrders()
    throws RemoteException;

    /**
     * Gets orders by the type
     * @return the set of received orders
     * @throws   RemoteException, LawsonException
     */
    public OrderDataVector getOrdersByType(String pOrderType)
    throws RemoteException;

    /**
     *Retrieves all of the order notes.  Notes are not those that strictly fall under
     *the Notes type code, but also customer cart comments, and anything else that are misc
     *order properties intended for Human consumption.
     *@param pOrderId the order Id
     * @return an <code>OrderPropertyDataVector</code> value
     * @exception RemoteException if an error occurs
     */
    public OrderPropertyDataVector getOrderNotes(int pOrderId) throws RemoteException;

    /**
     * Sets PENDING_CONSOLIDATION status for orders replaced by consolidated order,
     * and cancels the consolidated order
     * @throws   RemoteException
     */
    public void deconsolidateOrder(int pOrderId, String pUserName)
    throws RemoteException;

    /**
     * Gets orders replaced by consolidated orders
     * @param pOrderId the consolidated order id
     * @return a set of ReplacedOrderView objects
     * @throws   RemoteException
     */
    public ReplacedOrderViewVector getReplacedOrders(int pOrderId)
    throws RemoteException;


    /**
     *fetches the order credit card data and associated data
     *@param orderCreditCreditCardId the order creit card id.
     *@returns a populated OrderCreditCardData object
     */
    public OrderCreditCardDescData getOrderCreditCardDesc(int orderCreditCreditCardId)
    throws RemoteException;
    
    /**
     * Get InvoiceCreditCardTransViewVector according to the supplied criteria
     * @param pOrderCreditCreditCardId the criteria
     * @return an <code>InvoiceCreditCardTransViewVector</code> value
     * @exception            RemoteException
     */
    public InvoiceCreditCardTransViewVector getInvoiceCCTransList(int pOrderCreditCreditCardId)
    throws RemoteException;

    /**
     *saves a order credit card. A call to this method will result
     *in an encrypted card number being saved to the databse.
     */
    public OrderCreditCardData saveOrderCreditCard(String unencryptedCreditCardNumber,OrderCreditCardData pOrderCreditCardData)
    throws RemoteException;

    /**
     *saves a order credit card.
     */
    public OrderCreditCardData saveOrderCreditCard(OrderCreditCardData pOrderCreditCardData)
    throws RemoteException;

    public void updateBillTo( int pOrderId, int pBillToId )
    throws RemoteException ;

    /**
     *Performs tax calculation on on the supplied data.
     */
    public BigDecimal calculateTax(TaxQuery query) throws RemoteException, TaxCalculationException;

    /**
     * Returns an initialized TaxQueryResponse object that can be used for
     * multiple tax querys for the same site/address
     * @param query query for the tax system.  The amount param is ignored.
     * @return an initialized TaxQueryResponse
     * @throws RemoteException
     */
    public TaxQueryResponse getTaxQueryResponse(TaxQuery query) throws RemoteException;

    /**
     *Returnes orders ready ready to resume pipeline
     */
    public OrderDataVector getOrdersPipelineToResume()
       throws RemoteException;

    /**
    * Describe <code>OrderPropertyDataVector</code> method here.
    * Makes order no fullfilment
    * @param pOrderPropertyDV a <code>OrderPropertyDataVector</code> value
    * @exception RemoteException if an error occurs
    */
    public void makeOrderNoFulfillment(OrderPropertyDataVector pOrderPropertyDV)
    throws RemoteException;

    /**
     * Picks not approved workflow notes, which the user has rights to approve
     * @param pUser the user
     * @param pOrderId the orderId
     * @return set of order property ids
     * @throws RemoteException
     */
    public IdVector getPropertiesUserCanApprove(UserData pUser, int pOrderId)
    throws RemoteException ;

    // Update the shipping information for this order.
    // Return a status message with the outcome of the
    // update request.
    public String updateOrderShippingForDist( int pOrderId,
					      int pToFreightHandlerId,
					      String pForDistErpNum,
					      String pModUserName )
	throws RemoteException;

    /**
     * If account,store and the site has taxable attribute
     * which is true in that case a method returns true
     * @param storeId   store identifier
     * @param accountId  account identifier
     * @param siteId  site identifier
     * @return taxable flag
     * @throws RemoteException if an errors
     */
    public boolean isTaxableOrder(int storeId,int accountId,int siteId) throws RemoteException ;

    /**
     * Gets items which have been bought
     * @param pOrderId order_id
     * @param pPoId    purchase_order_id
     * @return order item collection
     * @throws RemoteException if an errors
     */
    public OrderItemDataVector getOrderItemsByPoIdOrderId(int pOrderId, int pPoId) throws RemoteException;

    public OrderData getOrder(int orderId) throws RemoteException;

    public OrderAddressData getOrderAddress(int pOrderId, String pAddrType) throws RemoteException;

    /**
     * gets  approved invoice flag
     */
    public boolean getApprovedInvoiceFlag(int invoiceDistId) throws RemoteException;

    public CostCenterData getCostCenter(int costCenterId) throws RemoteException, DataNotFoundException;

    public void enterOrderProperty(String pType,
                                   String pShortDesc,
                                   String pValue,
                                   int pOrderId,
                                   int pOrderItemId,
                                   int pInvoiceDistId,
                                   int pInvoiceDistDetailId,
                                   int pInvoiceCustId,
                                   int pInvoiceCustDetailId,
                                   int pOrderAddressId,
                                   String pUserName) throws RemoteException;

   public OrderInfoDataView getOrderInfoData(int orderId) throws RemoteException;

   public OrderPropertyData getEventOrderProperty(int pOrderId, String shortDesc) throws RemoteException;

   public Collection saveInventoryOrderQtyLog(Collection qtyLog,UserData user) throws RemoteException;

   /**
    * Returns a list of orders that match this customer po number from a given date.
    * Can be used to check if a customer has entered this po number before since a
    * particular date.
    * @param requestPoNum the po number the customer used (exact match)
    * @param accountId the account id to look for this po number
    * @param fromDate the date from which to begin searching. Uses revised order date then defaults to original order date
    * @return list of orders
    */
   public OrderDataVector getOrderByCustomerPoNumber(String requestPoNum, int accountId, Date fromDate) throws RemoteException;

   public OrderDataVector getOrdersByItem(int workOrderItemId) throws RemoteException;


    public InvoiceDistData updateInvoiceDist(InvoiceDistData pInvoiceDist) throws RemoteException;
    
    public BackorderDataVector getBackorderDetail(int orderId, int orderItemId)throws RemoteException;

    public OrderDataVector getReplacedOrdersFor(int pOrderId) throws RemoteException;
    
    public void updateOrderItemStatusCd(OrderItemData pOrderItem) throws RemoteException;
    
    public void updateOrderToWorkOrderAssoc(int orderId, int workOrderItemId, String userName) throws RemoteException;
    
    public WorkOrderData getAssociatedWorkOrder(int orderId) throws RemoteException;

    public IdVector getAssociatedServiceTickets(int orderId) throws RemoteException;

    public int getOrderItemQuantity(int restrictDays, int itemId, int siteId) 
    throws RemoteException;

    
    // Location Budget
    /**
     * This method returns the orders in pending approval status
     * @param pOrderStatusCriteria
     * @param pOrderStatusCriteria
     * @return OrderStatusDescDataVector
     */
    public OrderStatusDescDataVector getOrderStatusDesc(OrderStatusCriteriaData pOrderStatusCriteria)
    throws RemoteException;
    
    
    //Order-Detail for the NEW UI
    public OrderPropertyDataVector getOrderPropertyCollectionByOrderId(int pOrderId)
    throws RemoteException, DataNotFoundException;
   
    //STJ-5261 site data is needed for mobile All-Orders for displaying unspent budget for 
  	//orders in pending approval status.
    public SiteData getSiteData(OrderData pOrderData) throws Exception;
    public BigDecimal getTotalBudgetForOrder(OrderData orderD)
    throws Exception;
}


