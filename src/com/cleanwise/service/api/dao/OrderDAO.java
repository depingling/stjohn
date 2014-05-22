package com.cleanwise.service.api.dao;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Category;

import com.cleanwise.service.api.session.Order;
import com.cleanwise.service.api.session.User;
import com.cleanwise.service.api.session.UserBean;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.PipelineUtil;
import com.cleanwise.service.api.util.PropertyUtil;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.AddressData;
import com.cleanwise.service.api.value.AddressDataVector;
import com.cleanwise.service.api.value.AssetData;
import com.cleanwise.service.api.value.AssetDataVector;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.BusEntityDataVector;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.InventoryOrderQtyData;
import com.cleanwise.service.api.value.InventoryOrderQtyDataVector;
import com.cleanwise.service.api.value.InvoiceCustData;
import com.cleanwise.service.api.value.InvoiceCustDataVector;
import com.cleanwise.service.api.value.InvoiceCustDetailData;
import com.cleanwise.service.api.value.InvoiceCustDetailDataVector;
import com.cleanwise.service.api.value.InvoiceDistData;
import com.cleanwise.service.api.value.InvoiceDistDataVector;
import com.cleanwise.service.api.value.InvoiceDistDetailData;
import com.cleanwise.service.api.value.InvoiceDistDetailDataVector;
import com.cleanwise.service.api.value.ItemData;
import com.cleanwise.service.api.value.ItemDataVector;
import com.cleanwise.service.api.value.ItemSubstitutionData;
import com.cleanwise.service.api.value.ItemSubstitutionDataVector;
import com.cleanwise.service.api.value.OrderAddOnChargeData;
import com.cleanwise.service.api.value.OrderAddOnChargeDataVector;
import com.cleanwise.service.api.value.OrderAddressData;
import com.cleanwise.service.api.value.OrderAddressDataVector;
import com.cleanwise.service.api.value.OrderData;
import com.cleanwise.service.api.value.OrderDataVector;
import com.cleanwise.service.api.value.OrderFreightData;
import com.cleanwise.service.api.value.OrderFreightDataVector;
import com.cleanwise.service.api.value.OrderItemActionData;
import com.cleanwise.service.api.value.OrderItemActionDataVector;
import com.cleanwise.service.api.value.OrderItemActionDescData;
import com.cleanwise.service.api.value.OrderItemActionDescDataVector;
import com.cleanwise.service.api.value.OrderItemData;
import com.cleanwise.service.api.value.OrderItemDataVector;
import com.cleanwise.service.api.value.OrderItemDescData;
import com.cleanwise.service.api.value.OrderItemDescDataVector;
import com.cleanwise.service.api.value.OrderItemMetaData;
import com.cleanwise.service.api.value.OrderItemMetaDataVector;
import com.cleanwise.service.api.value.OrderMetaData;
import com.cleanwise.service.api.value.OrderMetaDataVector;
import com.cleanwise.service.api.value.OrderPropertyData;
import com.cleanwise.service.api.value.OrderPropertyDataVector;
import com.cleanwise.service.api.value.OrderSiteSummaryData;
import com.cleanwise.service.api.value.OrderStatusCriteriaData;
import com.cleanwise.service.api.value.PurchaseOrderData;
import com.cleanwise.service.api.value.ReplacedOrderItemView;
import com.cleanwise.service.api.value.ReplacedOrderItemViewVector;
import com.cleanwise.service.api.value.UserData;
import com.cleanwise.service.api.value.UserDataVector;
import com.cleanwise.service.api.value.UserSearchCriteriaData;
import com.cleanwise.view.utils.Constants;


public class OrderDAO {

  private static final double ZERO = 0.00;
  private final static SimpleDateFormat DATE_FORMAT_4_SQL = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

  private static Category log =
    Category.getInstance(OrderDAO.class.getName());

  private String mOrderNum = "0";
  private OrderData mOrderData;

  public OrderData getOrderData() {

    return mOrderData;
  }

  private OrderItemDataVector mOrderItems;

  public OrderItemDataVector getOrderItems() {

    return mOrderItems;
  }

  public void updateStatus(Connection pCon, String pOrderNum,
                           String pOrderStatus) throws Exception {

    String sql = "update " + OrderDataAccess.CLW_ORDER +
                 " set order_status_cd = '" + pOrderStatus + "'" +
                 " where order_num = '" + pOrderNum + "'";
    java.sql.Statement stmt = pCon.createStatement();
    stmt.executeUpdate(sql);
    stmt.close();
  }

  public OrderDAO() {
  }

  public OrderDAO(Connection pCon, int pOrderId) throws Exception {
    lookupOrderData(pCon, null, null, pOrderId);
  }

  public OrderDAO(Connection pCon, String pOrderNum, String pOrderStatus) throws Exception {
    lookupOrderData(pCon, pOrderNum, pOrderStatus, 0);
  }

  public void refreshOrderData(Connection pCon) throws Exception {
    lookupOrderData(pCon, mOrderNum, null, 0);
  }

  public void lookupOrderData(Connection pCon, String pOrderNum,
                              String pOrderStatus, int pOrderId) throws Exception {
    mOrderNum = pOrderNum;
    boolean validCrit = false;

    DBCriteria dbc;
    dbc = new DBCriteria();

    if (pOrderId > 0) {
      validCrit = true;
      dbc.addEqualTo(OrderDataAccess.ORDER_ID, pOrderId);
    }

    if (pOrderNum != null) {
      validCrit = true;
      dbc.addEqualTo(OrderDataAccess.ORDER_NUM, pOrderNum);
    }

    // Check on wether we have enough info.
    if (!validCrit) {
      String emsg = " lookupOrderData, pOrderNum is null "
                    + " and pOrderId = 0 one value must be specified";
      throw new Exception(emsg);
    }

    if (pOrderStatus != null) {
      dbc.addEqualTo(OrderDataAccess.ORDER_STATUS_CD, pOrderStatus);
    }

    OrderDataVector orderDV = OrderDataAccess.select(pCon, dbc);
    String errorMess = "";

    if (orderDV.size() == 0) {
      errorMess = "No order found. pOrderNum=" +
                  pOrderNum + ", pOrderStatus=" + pOrderStatus;
      throw new Exception(errorMess);
    }

    if (orderDV.size() > 1) {
      errorMess = "More than one record for pOrderNum=" +
                  pOrderNum + ", pOrderStatus=" + pOrderStatus;
      throw new Exception(errorMess);
    }

    mOrderData = (OrderData) orderDV.get(0);
    dbc = new DBCriteria();
    dbc.addEqualTo(OrderItemDataAccess.ORDER_ID, mOrderData.getOrderId());
    dbc.addOrderBy(OrderItemDataAccess.ORDER_LINE_NUM);

    OrderItemDataVector orderItemDV =
      OrderItemDataAccess.select(pCon, dbc);

    if (orderItemDV.size() == 0) {
      errorMess = "Order has no items. Order id: " +
                  mOrderData.getOrderId();
      throw new Exception(errorMess);
    }

    mOrderItems = orderItemDV;
  }

  public static void updateOrderStatusCd(Connection pConn,
                                         int pOrderId,
                                         String pNewStatus) {
    try {
      Statement stmt = pConn.createStatement();
      String sql = "  update clw_order o " +
                   " set o.order_status_cd = '" + pNewStatus + "'" +
                   " where o.order_id = " + pOrderId;
      log.debug("SQL: " + sql);
      stmt.executeUpdate(sql);
      stmt.close();
    } catch (Exception e) {
      log.error("Error setting pNewStatus=" + pNewStatus +
                " pOrderId=" + pOrderId + ", error: " +
                e.getMessage());
    }
  }

  public static void addOrderItemNote(Connection pConn,
                                      int pOrderId,
                                      int pOrderItemId,
                                      String pValue,
                                      String pUserName
    ) {

    OrderDAO o = new OrderDAO();
    o.enterOrderProperty2
      (pConn,
       RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE,
       RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE,
       pValue,
       pOrderId,
       pOrderItemId, 0, 0, 0, 0, 0,
       pUserName,
       null, null, null, null, null, null, null);
  }

  public static void addOrderNote(Connection pConn,
                                  int pOrderId,
                                  String pValue,
                                  String pUserName
    ) {

    OrderDAO o = new OrderDAO();
    o.enterOrderProperty2
      (pConn,
       RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE,
       RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE,
       pValue,
       pOrderId,
       0, 0, 0, 0, 0, 0,
       pUserName,
       null, null, null, null, null, null, null);
  }

  public static void enterOrderProperty(Connection pConn,
                                        String pType,
                                        String pShortDesc,
                                        String pValue,
                                        int pOrderId,
                                        int pOrderItemId,
                                        int pInvoiceDistId,
                                        int pInvoiceDistDetailId,
                                        int pInvoiceCustId,
                                        int pInvoiceCustDetailId,
                                        int pOrderAddressId,
                                        String pUserName) {
    OrderDAO o = new OrderDAO();
    o.enterOrderProperty2
      (pConn,
       pType,
       pShortDesc,
       pValue,
       pOrderId,
       pOrderItemId,
       pInvoiceDistId,
       pInvoiceDistDetailId,
       pInvoiceCustId,
       pInvoiceCustDetailId,
       pOrderAddressId,
       pUserName,
       null, null, null, null, null, null, null);
  }

  public static void enterOrderProperty(Connection pConn,
                                        String pType,
                                        String pShortDesc,
                                        String pValue,
                                        int pOrderId,
                                        int pOrderItemId,
                                        int pInvoiceDistId,
                                        int pInvoiceDistDetailId,
                                        int pInvoiceCustId,
                                        int pInvoiceCustDetailId,
                                        int pOrderAddressId,
                                        String pUserName,
                                        String pMessageKey,
                                        String pArg0,
                                        String pArg0TypeCd,
                                        String pArg1,
                                        String pArg1TypeCd) {
    OrderDAO o = new OrderDAO();
    o.enterOrderProperty2
      (pConn,
       pType,
       pShortDesc,
       pValue,
       pOrderId,
       pOrderItemId,
       pInvoiceDistId,
       pInvoiceDistDetailId,
       pInvoiceCustId,
       pInvoiceCustDetailId,
       pOrderAddressId,
       pUserName,
       pMessageKey, pArg0, pArg0TypeCd, pArg1, pArg1TypeCd, null, null);
  }

  public static void enterOrderProperty(Connection pConn,
                                        String pType,
                                        String pShortDesc,
                                        String pValue,
                                        int pOrderId,
                                        int pOrderItemId,
                                        int pInvoiceDistId,
                                        int pInvoiceDistDetailId,
                                        int pInvoiceCustId,
                                        int pInvoiceCustDetailId,
                                        int pOrderAddressId,
                                        String pUserName,
                                        String pMessageKey,
                                        String pArg0,
                                        String pArg0TypeCd,
                                        String pArg1,
                                        String pArg1TypeCd,
                                        String pArg2,
                                        String pArg2TypeCd) {
    OrderDAO o = new OrderDAO();
    o.enterOrderProperty2
      (pConn,
       pType,
       pShortDesc,
       pValue,
       pOrderId,
       pOrderItemId,
       pInvoiceDistId,
       pInvoiceDistDetailId,
       pInvoiceCustId,
       pInvoiceCustDetailId,
       pOrderAddressId,
       pUserName,
       pMessageKey, pArg0, pArg0TypeCd, pArg1, pArg1TypeCd, pArg2, pArg2TypeCd);
  }

  private void enterOrderProperty2(Connection pConn,
                                   String pType,
                                   String pShortDesc,
                                   String pValue,
                                   int pOrderId,
                                   int pOrderItemId,
                                   int pInvoiceDistId,
                                   int pInvoiceDistDetailId,
                                   int pInvoiceCustId,
                                   int pInvoiceCustDetailId,
                                   int pOrderAddressId,
                                   String pUserName,
                                   String pMessageKey,
                                   String pArg0,
                                   String pArg0TypeCd,
                                   String pArg1,
                                   String pArg1TypeCd,
                                   String pArg2,
                                   String pArg2TypeCd) {

    try {

      OrderPropertyData opd = OrderPropertyData.createValue();

      if (pOrderId > 0) {
        opd.setOrderId(pOrderId);
      }

      if (pOrderItemId > 0) {
        opd.setOrderItemId(pOrderItemId);
      }

      if (pInvoiceDistId > 0) {
        opd.setInvoiceDistId(pInvoiceDistId);
      }

      if (pInvoiceDistDetailId > 0) {
        opd.setInvoiceDistDetailId(pInvoiceDistDetailId);
      }

      if (pInvoiceCustId > 0) {
        opd.setInvoiceCustId(pInvoiceCustId);
      }

      if (pInvoiceCustDetailId > 0) {
        opd.setInvoiceCustDetailId(pInvoiceCustDetailId);
      }

      if (pOrderAddressId > 0) {
        opd.setOrderAddressId(pOrderAddressId);
      }

      if (null == pValue || pValue.length() == 0) {
        log.error("enterOrderProperty, error, " +
                  "null value for order property: " + pShortDesc);
        pValue = "---";
      }

      if (pValue.length() > 2000) {
        pValue = pValue.substring(0, 2000);
      }

      if (pShortDesc.length() > 255) {
        pShortDesc = pShortDesc.substring(0, 255);
      }

      opd.setValue(pValue);
      opd.setOrderPropertyStatusCd("ACTIVE");
      opd.setOrderPropertyTypeCd(pType);
      opd.setShortDesc(pShortDesc);

      if (pUserName == null || pUserName.length() == 0) {
        opd.setAddBy("none");
        opd.setModBy("none");
      } else {
        opd.setAddBy(pUserName);
        opd.setModBy(pUserName);
      }

      opd.setMessageKey(pMessageKey);
      opd.setArg0(pArg0);
      opd.setArg0TypeCd(pArg0TypeCd);
      opd.setArg1(pArg1);
      opd.setArg1TypeCd(pArg1TypeCd);
      opd.setArg2(pArg2);
      opd.setArg2TypeCd(pArg2TypeCd);

      OrderPropertyDataAccess.insert(pConn, opd);
    } catch (Exception e) {
      log.error("enterOrderProperty, error, " + e +
                " \n trying to add order note: " + pValue);
    }
  }

  public static String getOrderPropertyValue(Connection pConn, int pOrderId, String pPropertyType) {
    OrderPropertyData opd = getOrderProperty(pConn, pOrderId, pPropertyType);
    if (opd == null) {
      return "";
    } else {
      return opd.getValue();
    }
  }

    public static OrderPropertyData getEventOrderProperty(Connection pConn, int pOrderId, String shortDesc) {
        DBCriteria dbc = new DBCriteria();
        try {
            dbc.addEqualTo(OrderPropertyDataAccess.ORDER_ID, pOrderId);
            dbc.addEqualTo(OrderPropertyDataAccess.ORDER_PROPERTY_TYPE_CD, RefCodeNames.ORDER_PROPERTY_TYPE_CD.EVENT);
            dbc.addEqualTo(OrderPropertyDataAccess.SHORT_DESC, shortDesc);
            OrderPropertyDataVector orderPropertyDV = OrderPropertyDataAccess.select(pConn, dbc);
            if (orderPropertyDV.size() >= 1) {
                return (OrderPropertyData) orderPropertyDV.get(0);
            } else {
                return null;
            }
        } catch (SQLException exc) {
            exc.printStackTrace();
            log.error("getEventOrderProperty, error, " + exc +
                    " \n trying to get pPropertyType=" + RefCodeNames.ORDER_PROPERTY_TYPE_CD.EVENT +
                    " for pOrderId=" + pOrderId);
        }
        return null;
    }

  public static OrderPropertyData getOrderProperty(Connection pConn, int pOrderId, String pPropertyType) {
    try {
      DBCriteria dbc = new DBCriteria();
      dbc.addEqualTo(OrderPropertyDataAccess.ORDER_ID, pOrderId);
      dbc.addEqualTo(OrderPropertyDataAccess.ORDER_PROPERTY_TYPE_CD, pPropertyType);
      OrderPropertyDataVector orderPropertyDV = OrderPropertyDataAccess.select(pConn, dbc);
      if (orderPropertyDV.size() >= 1) {
        return (OrderPropertyData) orderPropertyDV.get(0);
      } else {
        return null;
      }
    } catch (SQLException exc) {
      exc.printStackTrace();
      log.error("getOrderPropertyValue, error, " + exc +
                " \n trying to get pPropertyType=" + pPropertyType +
                " for pOrderId=" + pOrderId);
    }
    return null;
  }


  public static OrderAddressData getShippingAddress(Connection pConn,
    int pOrderId) {
    return getOrderAddress(pConn, pOrderId,
                           RefCodeNames.ADDRESS_TYPE_CD.SHIPPING);

  }

  public static OrderAddressData getBillingAddress(Connection pConn,
    int pOrderId) {
    return getOrderAddress(pConn, pOrderId,
                           RefCodeNames.ADDRESS_TYPE_CD.BILLING);

  }

  public static OrderAddressData getOrderAddress(Connection pConn,
                                                 int pOrderId,
                                                 String pAddrType) {

    OrderAddressData ad = OrderAddressData.createValue();
    try {
      DBCriteria db = new DBCriteria();
      db.addEqualTo(OrderAddressDataAccess.ORDER_ID,
                    pOrderId);
      db.addEqualTo(OrderAddressDataAccess.ADDRESS_TYPE_CD,
                    pAddrType);

      OrderAddressDataVector adVec =
        OrderAddressDataAccess.select(pConn, db);

      if (adVec != null && adVec.size() > 0) {
        ad = (OrderAddressData) adVec.get(0);
      }
      return ad;
    } catch (Exception e) {
      e.printStackTrace();
      return ad;
    }
  }

  public static OrderMetaDataVector
    getOrderMetaDV(Connection pConn, int pOrderId) {

    try {

      DBCriteria dbc = new DBCriteria();
      dbc.addEqualTo(OrderMetaDataAccess.ORDER_ID, pOrderId);
      OrderMetaDataVector orderMetaDV = OrderMetaDataAccess.select
                                        (pConn, dbc);

      if (null != orderMetaDV &&
          orderMetaDV.size() > 0) {
        return orderMetaDV;
      }
    } catch (SQLException exc) {
      exc.printStackTrace();
      log.error("getOrderMetaValue, error, " + exc +
                " pOrderId=" + pOrderId);
    }

    return null;
  }

  public static OrderItemDataVector getOrderItems
    (Connection pConn, int pOrderId) {

    try {

      DBCriteria dbc = new DBCriteria();
      dbc.addEqualTo(OrderItemDataAccess.ORDER_ID, pOrderId);
      return OrderItemDataAccess.select(pConn, dbc);
    } catch (SQLException exc) {
      exc.printStackTrace();
      log.error("getOrderItems, error, " + exc +
                " pOrderId=" + pOrderId);
    }
    return null;
  }

  public static String kGoodOrderStatusSqlList = "(" +
                                                 "'" + RefCodeNames.ORDER_STATUS_CD.ORDERED + "', " +
                                                 "'" + RefCodeNames.ORDER_STATUS_CD.INVOICED + "', " +
                                                 "'" + RefCodeNames.ORDER_STATUS_CD.ERP_RELEASED + "', " +
                                                 "'" + RefCodeNames.ORDER_STATUS_CD.PROCESS_ERP_PO + "'" +
                                                 ")";


  /**ERP Work may need to be done after cancelling an order*/
  public static void cancelAndUpdateOrder(Connection conn, OrderData orderD, String pUser) throws SQLException,
    DataNotFoundException {

    if (!(RefCodeNames.ORDER_STATUS_CD.CANCELLED.equals(orderD.getOrderStatusCd()))) {
      orderD.setOrderStatusCd(RefCodeNames.ORDER_STATUS_CD.CANCELLED);
      OrderDataAccess.update(conn, orderD);

      log.info(">>>>>>>>>Order cacnelled!!!!");
      DBCriteria crit = new DBCriteria();
      crit.addEqualTo(OrderItemDataAccess.ORDER_ID, orderD.getOrderId());
      crit.addNotEqualTo(OrderItemDataAccess.ORDER_ITEM_STATUS_CD,
                         RefCodeNames.ORDER_STATUS_CD.CANCELLED);
      OrderItemDataVector oiDV = OrderItemDataAccess.select(conn, crit);
      //Change status for canceled items
      for (int ii = 0; ii < oiDV.size(); ii++) {
        OrderItemData oiD = (OrderItemData) oiDV.get(ii);
        if (!(RefCodeNames.ORDER_ITEM_STATUS_CD.CANCELLED.equals(oiD.getOrderItemStatusCd()))) {
          oiD.setOrderItemStatusCd(RefCodeNames.ORDER_ITEM_STATUS_CD.CANCELLED);
          oiD.setAckStatusCd(null);
          oiD.setModBy(pUser);
          OrderItemDataAccess.update(conn, oiD);
        }
      }
    }

  }

  /**
   *This method may change the status of the order item passed in
   */
  public static void updateOrderItem(Connection pCon, OrderItemData pOrderItemData) throws SQLException {

    if (pOrderItemData.getTotalQuantityOrdered() == pOrderItemData.getTotalQuantityShipped()
        &&
        (pOrderItemData.getOrderItemStatusCd() == null ||
         pOrderItemData.getOrderItemStatusCd().equals(RefCodeNames.ORDER_ITEM_STATUS_CD.PENDING_FULFILLMENT) ||
         pOrderItemData.getOrderItemStatusCd().equals(RefCodeNames.ORDER_ITEM_STATUS_CD.PENDING_REVIEW) ||
         pOrderItemData.getOrderItemStatusCd().equals(RefCodeNames.ORDER_ITEM_STATUS_CD.SENT_TO_DISTRIBUTOR) ||
         pOrderItemData.getOrderItemStatusCd().equals(RefCodeNames.ORDER_ITEM_STATUS_CD.SENT_TO_DISTRIBUTOR_FAILED) ||
         pOrderItemData.getOrderItemStatusCd().equals(RefCodeNames.ORDER_ITEM_STATUS_CD.PO_ACK_ERROR) ||
         pOrderItemData.getOrderItemStatusCd().equals(RefCodeNames.ORDER_ITEM_STATUS_CD.PO_ACK_REJECT) ||
         pOrderItemData.getOrderItemStatusCd().equals(RefCodeNames.ORDER_ITEM_STATUS_CD.PO_ACK_SUCCESS))) {
      pOrderItemData.setOrderItemStatusCd(RefCodeNames.ORDER_ITEM_STATUS_CD.INVOICED);
    }
    OrderItemDataAccess.update(pCon, pOrderItemData);
  }

  /**
   *These are the action codes that are obtained through other mechanisms for displaying.
   *RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.CUST_INVOICED = any cust invoice records for an item
   *RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.DIST_INVOICED = any dist invoice records for an item
   *RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.ACCEPTED = any purchase orders for an item
   */
  private static ArrayList ACTION_CDS_TO_FILTER = new ArrayList();
  static {
    ACTION_CDS_TO_FILTER.add(RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.CUST_INVOICED);
    ACTION_CDS_TO_FILTER.add(RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.DIST_INVOICED);
  }

  /**
   *Gets the order item actions for this order item that are displayed filtering out the ones that
   *are obtained from other bits of data:
   *RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.CUST_INVOICED = any cust invoice records for an item
   *RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.DIST_INVOICED = any dist invoice records for an item
   *RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.ACCEPTED = any purchase orders for an item
   *<b><i>*NOTE:</b></i> This does not do the ui persmissions based filtering, it is left up to
   *the view layer to verify that a user is seeing only the order item actions for which they should
   *be allowed.
   */
  public static OrderItemActionDataVector getFilteredOrderItemActionsForOrderItem(Connection pCon, int pOrderItemId) throws
    SQLException {
    DBCriteria crit = new DBCriteria();
    crit.addNotOneOf(OrderItemActionDataAccess.ACTION_CD, ACTION_CDS_TO_FILTER);
    crit.addEqualTo(OrderItemActionDataAccess.ORDER_ITEM_ID, pOrderItemId);
    OrderItemActionDataVector v = OrderItemActionDataAccess.select(pCon, crit);
    if (v == null) {
      return new OrderItemActionDataVector();
    }
    return v;
  }

  public static boolean orderHasCustomerBillingAddress
    (Connection conn, OrderData pWebOrder) throws Exception {

    BusEntityData acct =
      BusEntityDataAccess.select(conn, pWebOrder.getAccountId());

    OrderAddressData oad = getBillingAddress(conn, pWebOrder.getOrderId());

    if (null != acct &&
        null != acct.getErpNum() &&
        null != oad &&
        null != oad.getErpNum() &&
        !acct.getErpNum().equals(oad.getErpNum())
      ) {
      // The billing erp number for this order is
      // different from the account erp number.
      return true;
    }

    return false;

  }

  /**
   *Populates an order site summary data object based off the passed in order data object.
   *Assumes that the orderData object passed in exists in the database and has a valid order id already set.
   */
  public static OrderSiteSummaryData populateOrderSiteSummryData(Connection conn, OrderData order) throws SQLException,
    RemoteException {

    OrderSiteSummaryData ossd = OrderSiteSummaryData.createValue();
    ossd.setOrderData(order);
    DBCriteria crit = new DBCriteria();
    // Get site information for this order.
    crit = new DBCriteria();
    crit.addEqualTo(OrderAddressDataAccess.ORDER_ID,
                    order.getOrderId());
    crit.addEqualTo(OrderAddressDataAccess.ADDRESS_TYPE_CD,
                    RefCodeNames.ADDRESS_TYPE_CD.SHIPPING);

    OrderAddressDataVector adv = OrderAddressDataAccess.select(
      conn, crit);

    if (adv.size() > 0) {

      OrderAddressData ad = (OrderAddressData) adv.get(0);
      ossd.setOrderAddressData(ad);
    }

    // Get the process on date and modification data
    crit = new DBCriteria();
    crit.addEqualTo(OrderMetaDataAccess.ORDER_ID, order.getOrderId());
    LinkedList ll = new LinkedList();
    ll.add(Order.PENDING_DATE);
    ll.add(Order.MODIFICATION_STARTED);
    crit.addOneOf(OrderMetaDataAccess.NAME, ll);
    OrderMetaDataVector omdv = OrderMetaDataAccess.select(conn, crit);
    for (Iterator iter = omdv.iterator(); iter.hasNext(); ) {
      OrderMetaData omd = (OrderMetaData) iter.next();
      if (Order.MODIFICATION_STARTED.equals(omd.getName())) {
        ossd.setModifStartedBy(omd.getValue());
        ossd.setModifStartedOn(omd.getModDate());

      } else if (Order.PENDING_DATE.equals(omd.getName())) {
        ossd.setProcessOnDate(omd.getValue());
      }
    }

    ////////////
    UserBean ub = new UserBean();
    UserSearchCriteriaData ucrit = new UserSearchCriteriaData();
    ucrit.setStoreId(order.getStoreId());
    ucrit.setUserName(order.getAddBy());
    ucrit.setUserNameMatch(User.NAME_EXACT);
    UserDataVector udv = ub.getUsersCollectionByCriteria(ucrit);
    if (udv.size() > 0) {
      UserData ud = (UserData) udv.get(0);
      ossd.setPlacedBy(ud);
    }

    // Pick up order history
    ossd.setShoppingHistory
      (ShoppingDAO.getOrderHistory(conn, order.getOrderId()));
    
    // Get the Discount for the order, if it exists
    String discountAmt = "";;
    crit = new DBCriteria();
    crit.addEqualTo(OrderMetaDataAccess.ORDER_ID, order.getOrderId());
    crit.addEqualTo(OrderMetaDataAccess.NAME,RefCodeNames.CHARGE_CD.DISCOUNT);
    OrderMetaDataVector omDV = OrderMetaDataAccess.select(conn, crit);
    log.info("***RRR: omDV = " + omDV);
	if ( !omDV.isEmpty() ) {
 	      OrderMetaData omd = (OrderMetaData) omDV.get(0);
 	      discountAmt = omd.getValue(); // save found value of the Discount
 	      log.info("**********MMM: discountAmt = " + discountAmt);
	}
	try {
	  ossd.setDiscountAmount(getDiscountAmt(conn, order.getOrderId()));
	} catch(Exception e) {
        log.error(e);
        log.info("Java exception was reveived trying to get Discount from the Database");
    }
	ossd.setStringDiscountAmount(discountAmt);
    
	log.info("*** DDD: ossd = " + ossd);
	return ossd;
  }

  public static void updateReplacedOrderItems(Connection pCon,
                                              OrderItemDataVector pOrderItemDataVector,
                                              ReplacedOrderItemViewVector pReplacedOrderItems,
                                              String pUserName) throws Exception {
    //First update replaced order quantities
    if (pReplacedOrderItems != null && pReplacedOrderItems.size() > 0) {
      for (Iterator iter = pReplacedOrderItems.iterator(); iter.hasNext(); ) {
        ReplacedOrderItemView roiVw = (ReplacedOrderItemView) iter.next();
        OrderItemData oiD = OrderItemDataAccess.select(pCon, roiVw.getOrderItemId());
        int quantity = roiVw.getQuantity();
        oiD.setTotalQuantityOrdered(quantity);
        oiD.setModBy(pUserName);

        if (quantity > 0) {
          oiD.setOrderItemStatusCd(null);
        } else {
          oiD.setOrderItemStatusCd(RefCodeNames.ORDER_ITEM_STATUS_CD.CANCELLED);
        }
        OrderItemDataAccess.update(pCon, oiD);
      }
    }
    DBCriteria dbc;
    //Check and update sku changes
    for (Iterator iter = pOrderItemDataVector.iterator(); iter.hasNext(); ) {
      OrderItemData oiD = (OrderItemData) iter.next();
      int orderItemId = oiD.getOrderItemId();
      if (orderItemId != 0) {
        OrderItemData dbOiD = OrderItemDataAccess.select(pCon, orderItemId);
        //if(oiD.getItemSkuNum()!=dbOiD.getItemSkuNum()) {
        int orderId = oiD.getOrderId();
        int itemId = oiD.getItemId();

        dbc = new DBCriteria();
        dbc.addEqualTo(OrderAssocDataAccess.ORDER2_ID, orderId);
        dbc.addEqualTo(OrderAssocDataAccess.ORDER_ASSOC_CD,
                       RefCodeNames.ORDER_ASSOC_CD.CONSOLIDATED);
        String replOrderIdReq = OrderAssocDataAccess.
                                getSqlSelectIdOnly(OrderAssocDataAccess.ORDER1_ID, dbc);

        dbc = new DBCriteria();
        dbc.addOneOf(OrderItemDataAccess.ORDER_ID, replOrderIdReq);
        OrderItemDataVector oiDV = OrderItemDataAccess.select(pCon, dbc);

        for (Iterator iter1 = oiDV.iterator(); iter1.hasNext(); ) {
          OrderItemData replOiD = (OrderItemData) iter1.next();
          if (replOiD.getItemId() == dbOiD.getItemId()) {
            replOiD.setItemId(oiD.getItemId());
            replOiD.setItemSkuNum(oiD.getItemSkuNum());
            replOiD.setItemShortDesc(oiD.getItemShortDesc());
            replOiD.setItemUom(oiD.getItemUom());
            replOiD.setItemPack(oiD.getItemPack());
            replOiD.setItemSize(oiD.getItemSize());
            replOiD.setItemCost(oiD.getItemCost());
            replOiD.setDistItemCost(oiD.getDistItemCost());
            replOiD.setCustContractPrice(oiD.getCustContractPrice());
            replOiD.setDistItemShortDesc(oiD.getDistItemShortDesc());
            replOiD.setDistItemSkuNum(oiD.getDistItemSkuNum());
            replOiD.setDistItemUom(oiD.getDistItemUom());
            replOiD.setDistItemPack(oiD.getDistItemPack());
            replOiD.setDistErpNum(oiD.getDistErpNum());
            replOiD.setCustItemSkuNum(oiD.getCustItemSkuNum());
            replOiD.setCustItemShortDesc(oiD.getCustItemShortDesc());
            replOiD.setCustItemUom(oiD.getCustItemUom());
            replOiD.setCustItemPack(oiD.getCustItemPack());
            replOiD.setManuItemSkuNum(oiD.getManuItemSkuNum());
            replOiD.setManuItemMsrp(oiD.getManuItemMsrp());
            replOiD.setManuItemUpcNum(oiD.getManuItemUpcNum());
            replOiD.setManuPackUpcNum(oiD.getManuPackUpcNum());
            replOiD.setManuItemShortDesc(oiD.getManuItemShortDesc());
            replOiD.setModBy(pUserName);
            OrderItemDataAccess.update(pCon, replOiD);
          }
        }

        //  }
      }
    }

  }

  /**
   *Cancels the order items and updates status messages, 855 system, actions etc.
   */
  public static void cancelAndUpdateOrderItems(Connection conn, OrderItemDataVector oiDV, OrderData pOrder, String pUser) throws
    SQLException {

    //Change status for canceled items
    IdVector cancelledItemIds = new IdVector();
    for (int ii = 0; ii < oiDV.size(); ii++) {
      OrderItemData oiD = (OrderItemData) oiDV.get(ii);
      if (!(RefCodeNames.ORDER_ITEM_STATUS_CD.CANCELLED.equals(oiD.getOrderItemStatusCd()))) {
        oiD.setOrderItemStatusCd(RefCodeNames.ORDER_ITEM_STATUS_CD.CANCELLED);
        oiD.setAckStatusCd(null);
        oiD.setModBy(pUser);
        updateOrderItem(conn, oiD);
        cancelledItemIds.add(new Integer(oiD.getItemId()));
      }
      /*int orderItemId = oiD.getOrderItemId();
                     for(int jj=0; jj<pOrderItemIdV.size(); jj++) {
         Integer oiIdI = (Integer) pOrderItemIdV.get(jj);
         if(orderItemId==oiIdI.intValue()) {
           pOrderItemIdV.remove(jj);
         }
                     }*/
    }
    //Update order
    pOrder.setModBy(pUser);
    OrderDataAccess.update(conn, pOrder);
    //If consolidated order
    if (RefCodeNames.ORDER_TYPE_CD.CONSOLIDATED.equals(pOrder.getOrderTypeCd())) {
      DBCriteria crit = new DBCriteria();
      crit.addEqualTo(OrderAssocDataAccess.ORDER2_ID, pOrder.getOrderId());
      crit.addEqualTo(OrderAssocDataAccess.ORDER_ASSOC_CD,
                      RefCodeNames.ORDER_ASSOC_CD.CONSOLIDATED);
      String replOrderIdReq = OrderAssocDataAccess.
                              getSqlSelectIdOnly(OrderAssocDataAccess.ORDER1_ID, crit);

      crit = new DBCriteria();
      crit.addOneOf(OrderItemDataAccess.ORDER_ID, replOrderIdReq);
      crit.addOneOf(OrderItemDataAccess.ITEM_ID, cancelledItemIds);
      OrderItemDataVector replOiDV = OrderItemDataAccess.select(conn, crit);
      for (Iterator iter = replOiDV.iterator(); iter.hasNext(); ) {
        OrderItemData oiD = (OrderItemData) iter.next();
        //oiD.setTotalQuantityOrdered(0);
        oiD.setOrderItemStatusCd(RefCodeNames.ORDER_ITEM_STATUS_CD.CANCELLED);
        oiD.setModBy(pUser);
        OrderItemDataAccess.update(conn, oiD);
      }
    }
  }


  /**
   *Returns a list of order data objects that match the passed in criteria
   */
  public static OrderDataVector getOrdersInCriteria(Connection conn,
    OrderStatusCriteriaData pOrderStatusCriteria,
    IdVector pOrderIdV) throws Exception {

    String siteId = pOrderStatusCriteria.getSiteId();
    IdVector siteIdVector = pOrderStatusCriteria.getSiteIdVector();
    String accountId = pOrderStatusCriteria.getAccountId();
    IdVector accountIdVector = pOrderStatusCriteria.getAccountIdVector();
    IdVector storeIdVector = pOrderStatusCriteria.getStoreIdVector();
    List excludeOrderStatus = pOrderStatusCriteria.getExcludeOrderStatusList();
    List allowedOrderStatus = pOrderStatusCriteria.getOrderStatusList();

    boolean orderNotRecvd = pOrderStatusCriteria.getOrdersNotReceivedOnly();

    if (accountId != null) {
      accountId = accountId.trim();
    }
    if (siteId != null) {
      siteId = siteId.trim();
    }
    
    DBCriteria dbc = new DBCriteria();
    
    StringBuffer queryBuilder = new StringBuffer();
	queryBuilder.append("SELECT");
	queryBuilder.append(" ");
	queryBuilder.append(OrderDataAccess.CLW_ORDER);
	queryBuilder.append(".");
	queryBuilder.append(OrderDataAccess.ORDER_ID);
	queryBuilder.append(" ");
	queryBuilder.append("FROM");
	queryBuilder.append(" ");
	queryBuilder.append(OrderDataAccess.CLW_ORDER);
	queryBuilder.append(" ");    
   
    dbc.addIsNotNull(OrderDataAccess.ORDER_ID);
    
    if(Utility.isSet(excludeOrderStatus)) {
    	dbc.addNotOneOf(OrderDataAccess.ORDER_STATUS_CD, excludeOrderStatus);
    }
    
    if (Utility.isSet(allowedOrderStatus)) {
		dbc.addOneOf(OrderDataAccess.ORDER_STATUS_CD, allowedOrderStatus);
	}
    
    if(Utility.isSet(accountId)) {
		dbc.addEqualTo(OrderDataAccess.ACCOUNT_ID, accountId);
	}   
    
    if (Utility.isSet(storeIdVector)) {
		 dbc.addOneOf(OrderDataAccess.STORE_ID, storeIdVector);
	 }
    
    if (Utility.isSet(accountIdVector)) {
		 dbc.addOneOf(OrderDataAccess.ACCOUNT_ID, accountIdVector);
	 }
    
    if (Utility.isSet(pOrderStatusCriteria.getCustPONum())) {
		 dbc.addBeginsWith(OrderDataAccess.REQUEST_PO_NUM, pOrderStatusCriteria.getCustPONum().trim());
	 }
    
    if(Utility.isSet(pOrderStatusCriteria.getOrderBudgetTypeCd())) {
    	dbc.addEqualToIgnoreCase(OrderDataAccess.ORDER_BUDGET_TYPE_CD, pOrderStatusCriteria.getOrderBudgetTypeCd().trim().toUpperCase());
    }
    
    String country = pOrderStatusCriteria.getSiteCountry().trim().toUpperCase();
    String webOrderConfirmationNum = pOrderStatusCriteria.getWebOrderConfirmationNum();
    if((Utility.isSet(webOrderConfirmationNum)) && (pOrderStatusCriteria.getNewXpedex()) ){
    	siteId = "";
    }
    
    if (!Utility.isSet(siteId)) {
	   	 if (pOrderStatusCriteria.getUserTypeCd().length() > 0 &&
	   	          (
	   	            pOrderStatusCriteria.getUserTypeCd().equals
	   	            (RefCodeNames.USER_TYPE_CD.MSB) ||
	   	            pOrderStatusCriteria.getUserTypeCd().equals
	   	            (RefCodeNames.USER_TYPE_CD.CUSTOMER)
	   	          ) &&
	   	          pOrderStatusCriteria.getUserId() != null &&
	   	          !pOrderStatusCriteria.getUserId().equals("0")
	   	        ) {
	   		 
	   		 StringBuffer sqlBuf = new StringBuffer();
	   		 sqlBuf.append("SELECT DISTINCT");
	   		 sqlBuf.append(" ");
	   		 sqlBuf.append(BusEntityDataAccess.CLW_BUS_ENTITY);
	   		 sqlBuf.append(".");
	   		 sqlBuf.append(BusEntityDataAccess.BUS_ENTITY_ID);
	   		 sqlBuf.append(" ");
	   		 sqlBuf.append("FROM");
	   		 sqlBuf.append(" ");
	   		 sqlBuf.append(BusEntityDataAccess.CLW_BUS_ENTITY);
	   		 sqlBuf.append(" ");
	   		
	   		 DBCriteria dbCriteria  = new DBCriteria();
	   		  	  
	   		 String beTable = BusEntityDataAccess.CLW_BUS_ENTITY;
	   		 String uaTable = UserAssocDataAccess.CLW_USER_ASSOC; 
	   		    
	   		 dbCriteria.addJoinTable(uaTable);
	   		 dbCriteria.addJoinCondition(beTable,BusEntityDataAccess.BUS_ENTITY_ID,uaTable,UserAssocDataAccess.BUS_ENTITY_ID); 
	   		 dbCriteria.addJoinTableEqualTo(uaTable, UserAssocDataAccess.USER_ID, pOrderStatusCriteria.getUserId());
	   		 
	   		 String subQuery = buildSubQuery(dbCriteria,sqlBuf,beTable);
	   		 
	   		 log.debug("if site is not empty == "+subQuery);
	   		 
	   		 dbc.addOneOf(OrderDataAccess.SITE_ID, subQuery);
	   		 
	   	 }
    }
    
    if (Utility.isSet(pOrderStatusCriteria.getWebOrderConfirmationNum())) {
	   	 String webNum = pOrderStatusCriteria.getWebOrderConfirmationNum().trim();
	   	 
	   	 StringBuffer sqlBuf = new StringBuffer();
	   	 sqlBuf.append("SELECT");
	   	 sqlBuf.append(" ");
	   	 sqlBuf.append(OrderDataAccess.REF_ORDER_ID);
	   	 sqlBuf.append(" ");
	   	 sqlBuf.append("FROM");
	   	 sqlBuf.append(" ");
	   	 sqlBuf.append(OrderDataAccess.CLW_ORDER);
	   	 sqlBuf.append(" ");
	   	 
	   	 DBCriteria dbCriteria1  = new DBCriteria();
	   	 dbCriteria1.addEqualTo(OrderDataAccess.ORDER_NUM, webNum);
	   	 
	   	 DBCriteria dbCriteria2  = new DBCriteria();
	   	 dbCriteria2.addEqualTo(OrderDataAccess.ORDER_NUM, webNum);
	   	 
	   	 String oTable = OrderDataAccess.CLW_ORDER;
	   	
	   	 String subQuery = buildSubQuery(dbCriteria2,sqlBuf,oTable);
	   	 log.debug("Sub query=="+subQuery);
	   	 
	   	 DBCriteria dbCriteria3  = new DBCriteria();	    	 
	   	 dbCriteria3.addOneOf(OrderDataAccess.ORDER_ID, subQuery);
	   	 
	   	 dbCriteria1.addOrCriteria(dbCriteria3);
	   	 dbc.addIsolatedCriterita(dbCriteria1);
    }

    else if (Utility.isSet(pOrderStatusCriteria.getRefOrderNum())) {
   	 	dbc.addContains(OrderDataAccess.REF_ORDER_NUM, pOrderStatusCriteria.getRefOrderNum().trim());
    }
    
    if (Utility.isSet(siteId)) {
   	 	dbc.addEqualTo(OrderDataAccess.SITE_ID, siteId);
    }
    if (Utility.isSet(siteIdVector)) {
        dbc.addOneOf(OrderDataAccess.SITE_ID, siteIdVector);
    }
    
    if (Utility.isSet(pOrderStatusCriteria.getSiteZipCode()) ||
    		Utility.isSet(pOrderStatusCriteria.getSiteCity()) ||
    		Utility.isSet(pOrderStatusCriteria.getSiteState())  ||
	        ((Utility.isSet(country)) && (pOrderStatusCriteria.getNewXpedex()))) {
	 
		 String shiptoSql = "";
		 
		 StringBuffer sqlBuf = new StringBuffer();
		 sqlBuf.append("SELECT DISTINCT ");
		 sqlBuf.append(" ");
		 sqlBuf.append(OrderAddressDataAccess.CLW_ORDER_ADDRESS);
		 sqlBuf.append(".");
		 sqlBuf.append(OrderAddressDataAccess.ORDER_ID);
		 sqlBuf.append(" ");
		 sqlBuf.append("FROM");
		 sqlBuf.append(" ");
		 sqlBuf.append(OrderAddressDataAccess.CLW_ORDER_ADDRESS);
		 sqlBuf.append(" ");
		 
		 DBCriteria dbCriteria  = new DBCriteria();
		 
		 String oTable = OrderDataAccess.CLW_ORDER;
		 String oaTable = OrderAddressDataAccess.CLW_ORDER_ADDRESS;
		 
		 dbCriteria.addJoinTable(oTable);
		 dbCriteria.addJoinCondition(oaTable,OrderAddressDataAccess.ORDER_ID,oTable,OrderDataAccess.ORDER_ID); 
		 dbCriteria.addEqualTo(OrderAddressDataAccess.ADDRESS_TYPE_CD, RefCodeNames.ADDRESS_TYPE_CD.SHIPPING);
		 
		 if (pOrderStatusCriteria.getSiteZipCode().trim().length() > 0) {
			 dbCriteria.addBeginsWith(OrderAddressDataAccess.POSTAL_CODE,pOrderStatusCriteria.getSiteZipCode().trim());
		 }
		 
		 if (pOrderStatusCriteria.getSiteCity().trim().length() > 0) {
			 dbCriteria.addContains(OrderAddressDataAccess.CITY, pOrderStatusCriteria.getSiteCity().trim().toUpperCase());
		 }
		 
		 if (pOrderStatusCriteria.getSiteState().trim().length() > 0) {
			 dbCriteria.addEqualToIgnoreCase(OrderAddressDataAccess.STATE_PROVINCE_CD, 
					 pOrderStatusCriteria.getSiteState().trim());
		 }
		  
		 if (pOrderStatusCriteria.getSiteCountry().trim().length() > 0) {
			 dbCriteria.addEqualToIgnoreCase( OrderAddressDataAccess.COUNTRY_CD, 
					 pOrderStatusCriteria.getSiteCountry().trim());
		 }
		 
		 shiptoSql = buildSubQuery(dbCriteria,sqlBuf,oaTable);
		 
		 log.debug("Order Address=="+shiptoSql);
		 
		 dbc.addOneOf(OrderDataAccess.ORDER_ID, shiptoSql);
    }
    
    if (Utility.isSet(pOrderStatusCriteria.getOrderStatus())) {
   	 	dbc.addEqualTo(OrderDataAccess.ORDER_STATUS_CD, pOrderStatusCriteria.getOrderStatus().trim());
    }
    
    if (Utility.isSet(pOrderStatusCriteria.getErpOrderNum())) {
   	 	dbc.addEqualTo(OrderDataAccess.ERP_ORDER_NUM, pOrderStatusCriteria.getErpOrderNum().trim());
    }
    
    if (Utility.isSet(pOrderStatusCriteria.getMethod())) {
    	dbc.addEqualTo(OrderDataAccess.ORDER_SOURCE_CD , pOrderStatusCriteria.getMethod().trim());
    }
    
    if (Utility.isSet(pOrderStatusCriteria.getPlacedBy())) {
   	 	dbc.addEqualTo(OrderDataAccess.ADD_BY, pOrderStatusCriteria.getPlacedBy().trim());
    }
    
    String invSql = "";
    if (Utility.isSet(pOrderStatusCriteria.getInvoiceDistNum())||
    		Utility.isSet(pOrderStatusCriteria.getShipFromId())) {
   	 
    	StringBuffer invSqlBuffer = new StringBuffer();
    	invSqlBuffer.append("SELECT DISTINCT");
    	invSqlBuffer.append(" ");
    	invSqlBuffer.append(InvoiceDistDataAccess.ORDER_ID);
    	invSqlBuffer.append(" ");
    	invSqlBuffer.append("FROM");
    	invSqlBuffer.append(" ");
    	invSqlBuffer.append(InvoiceDistDataAccess.CLW_INVOICE_DIST);
    	invSqlBuffer.append(" ");
	   	
	   	 String idTable = InvoiceDistDataAccess.CLW_INVOICE_DIST;
	   	 
	   	 DBCriteria dbCriteria  = new DBCriteria();
	   	 dbCriteria.addGreaterThan(InvoiceDistDataAccess.ORDER_ID, 0);
	   	 
	   	 if (pOrderStatusCriteria.getInvoiceDistNum()!=null && pOrderStatusCriteria.getInvoiceDistNum().trim().length() > 0) {
	   		 dbCriteria.addEqualTo(InvoiceDistDataAccess.INVOICE_NUM, pOrderStatusCriteria.getInvoiceDistNum().trim());
	   	 }
   	 
	   	 //invSql
	   	 if (pOrderStatusCriteria.getShipFromId().trim().length() > 0) {
	   		 dbCriteria.addEqualTo(AddressDataAccess.ADDRESS_ID,pOrderStatusCriteria.getShipFromId().trim());
	   		 dbCriteria.addEqualToIgnoreCase(AddressDataAccess.ADDRESS_TYPE_CD,RefCodeNames.ADDRESS_TYPE_CD.DIST_SHIP_FROM);
		         
	   		 AddressDataVector addressV = AddressDataAccess.select(conn,dbCriteria);
		         
	         if (null != addressV && addressV.size() > 0) {
	
		           AddressData shipFromAddress = (AddressData) addressV.get(0);
		           if(shipFromAddress!=null ) {
		        	   if(shipFromAddress.getAddress1()!=null) {
			        	   dbCriteria.addEqualToIgnoreCase(InvoiceDistDataAccess.SHIP_FROM_ADDRESS_1,shipFromAddress.getAddress1());
			           }
			           if(shipFromAddress.getCity()!=null) {
			        	   dbCriteria.addEqualToIgnoreCase(InvoiceDistDataAccess.SHIP_FROM_CITY,shipFromAddress.getCity());
			           }
			           if(shipFromAddress.getStateProvinceCd()!=null) {
			        	   dbCriteria.addEqualToIgnoreCase(InvoiceDistDataAccess.SHIP_FROM_STATE,shipFromAddress.getStateProvinceCd());
			           }
			           if(shipFromAddress.getPostalCode()!=null) {
			        	   dbCriteria.addEqualToIgnoreCase(InvoiceDistDataAccess.SHIP_FROM_POSTAL_CODE,shipFromAddress.getPostalCode());
			           }
		           }
	         }
		         
	   	 }
	   	 
	   	 invSql = buildSubQuery(dbCriteria,invSqlBuffer,idTable);
		 log.debug("Invoice Dist SQL=="+invSql);
    }
    
    String invCustSql = "";
    if (Utility.isSet(pOrderStatusCriteria.getInvoiceCustNum())) {
   	 
	     StringBuffer invCustSqlBuffer = new StringBuffer();
	     invCustSqlBuffer.append("SELECT DISTINCT");
	     invCustSqlBuffer.append(" ");
	     invCustSqlBuffer.append(InvoiceCustDataAccess.ORDER_ID);
	     invCustSqlBuffer.append(" ");
	     invCustSqlBuffer.append("FROM");
	     invCustSqlBuffer.append(InvoiceCustDataAccess.CLW_INVOICE_CUST);
	     invCustSqlBuffer.append(" ");
	     
	   	 DBCriteria dbCriteria  = new DBCriteria();
	   	 dbCriteria.addGreaterThan(InvoiceCustDataAccess.ORDER_ID, 0);
	   	 dbCriteria.addEqualTo( InvoiceCustDataAccess.INVOICE_NUM,  pOrderStatusCriteria.getInvoiceCustNum().trim());
	   	 
	   	 String icTable = InvoiceCustDataAccess.CLW_INVOICE_CUST;
	   	 
	   	 invCustSql = buildSubQuery(dbCriteria,invCustSqlBuffer,icTable);
			 
		 log.debug("invCustSql=="+invCustSql);
        
    }
    
    String orderPropSql = "";
    if (Utility.isSet(pOrderStatusCriteria.getReferenceCode())) {
   	 
	     StringBuffer orderPropSqlBuffer = new StringBuffer("SELECT DISTINCT ORDER_ID FROM CLW_ORDER_PROPERTY");
	     orderPropSqlBuffer.append("SELECT DISTINCT");
	     orderPropSqlBuffer.append(" ");
	     orderPropSqlBuffer.append(OrderPropertyDataAccess.ORDER_ID);
	     orderPropSqlBuffer.append(" ");
	     orderPropSqlBuffer.append("FROM");
	     orderPropSqlBuffer.append(" ");
	     orderPropSqlBuffer.append(OrderPropertyDataAccess.CLW_ORDER_PROPERTY);
	     orderPropSqlBuffer.append(" ");
	     
	   	 DBCriteria dbCriteria  = new DBCriteria();
	   	 dbCriteria.addGreaterThan(OrderPropertyDataAccess.ORDER_ID, 0);
	   	 dbCriteria.addEqualTo( OrderPropertyDataAccess.CLW_VALUE,  pOrderStatusCriteria.getReferenceCode().trim());
	   	 
	   	 String opTable = "CLW_ORDER_PROPERTY";
	   	 orderPropSql = buildSubQuery(dbCriteria,orderPropSqlBuffer,opTable);
			 
		 log.debug("orderPropSql=="+orderPropSql);
      
    }
    
    if(orderNotRecvd) {
   	 
	   	 StringBuffer subQuery = new StringBuffer();
	   	 subQuery.append("SELECT");
	   	 subQuery.append(" ");
	     subQuery.append(OrderPropertyDataAccess.ORDER_ID);
	     subQuery.append(" ");
	   	 subQuery.append("FROM");
	   	 subQuery.append(" ");
	   	 subQuery.append(OrderPropertyDataAccess.CLW_ORDER_PROPERTY);
	   	 subQuery.append(" ");
	   	 
	   	 String clwValue = "TRUE";
	   	 
	   	 DBCriteria dbCriteria = new DBCriteria();
	   	 dbCriteria.addEqualToIgnoreCase(OrderPropertyDataAccess.CLW_VALUE, clwValue);
	   	 dbCriteria.addEqualTo(OrderPropertyDataAccess.ORDER_PROPERTY_TYPE_CD, RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_RECEIVED);
	   	 
	   	 String query = buildSubQuery(dbCriteria,subQuery,OrderPropertyDataAccess.CLW_ORDER_PROPERTY);
			 
		 log.debug("subQuery=="+query);
		 
		 dbc.addNotOneOf(OrderDataAccess.ORDER_ID, query);
   	 
    }
    
    String workflowSql = "";
    if (Utility.isSet(pOrderStatusCriteria.getWorkflowId())) {
   	 
    	StringBuffer workflowSqlBuffer = new StringBuffer();
    	workflowSqlBuffer.append("SELECT DISTINCT");
    	workflowSqlBuffer.append(" ");
    	workflowSqlBuffer.append(WorkflowQueueDataAccess.ORDER_ID);
    	workflowSqlBuffer.append(" ");
    	workflowSqlBuffer.append("FROM");
    	workflowSqlBuffer.append(" ");
    	workflowSqlBuffer.append(WorkflowQueueDataAccess.CLW_WORKFLOW_QUEUE);
    	workflowSqlBuffer.append(" ");
	   	 
	   	DBCriteria dbCriteria  = new DBCriteria();
	   	dbCriteria.addEqualTo(WorkflowQueueDataAccess.WORKFLOW_ID, pOrderStatusCriteria.getWorkflowId().trim());
	   	 
	   	workflowSql = buildSubQuery(dbCriteria,workflowSqlBuffer,WorkflowQueueDataAccess.CLW_WORKFLOW_QUEUE);
			 
		log.debug("workflowSql=="+workflowSql);
    }

    if (Utility.isSet(pOrderStatusCriteria.getOrderDateRangeBegin()) &&
    		Utility.isSet(pOrderStatusCriteria.getOrderDateRangeEnd())&&
	        pOrderStatusCriteria.getOrderDateRangeBegin().trim().equals(pOrderStatusCriteria.getOrderDateRangeEnd().trim())) {
	 
		  SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.SIMPLE_DATE_PATTERN);
		  try {
			  Date orderDate = simpleDateFormat.parse(pOrderStatusCriteria.getOrderDateRangeBegin().trim());
				if (null != orderDate) {
					//STJ-4759
					if (pOrderStatusCriteria.isFilterByRevisedOrderDate()) {
				    	dbc.addEqualTo("NVL(" + OrderDataAccess.REVISED_ORDER_DATE + "," +
				    			OrderDataAccess.ORIGINAL_ORDER_DATE + ") ", orderDate);
					}
					else {
						dbc.addEqualTo(OrderDataAccess.ORIGINAL_ORDER_DATE, orderDate);
					}
				}
		  } catch (Exception e) {
			//just skip adding the date if the supplied date was not parseable 
		  }
    }
    
    	else {
      	  if (Utility.isSet(pOrderStatusCriteria.getOrderDateRangeBegin()) ) {

   	        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.SIMPLE_DATE_PATTERN);
   	        Date orderDate = new Date();

   	        try {
   	          orderDate = simpleDateFormat.parse(pOrderStatusCriteria.getOrderDateRangeBegin().trim());
   	        } catch (Exception e) {
   	        	orderDate = simpleDateFormat.parse("1/1/1900");
   	        }

			//STJ-4759
			if (pOrderStatusCriteria.isFilterByRevisedOrderDate()) {
				dbc.addGreaterOrEqual("NVL(" + OrderDataAccess.REVISED_ORDER_DATE + "," +
		    			OrderDataAccess.ORIGINAL_ORDER_DATE + ") ", orderDate);
		   	    dbc.addCondition(getTimeSubCondition (OrderDataAccess.REVISED_ORDER_DATE, OrderDataAccess.REVISED_ORDER_TIME,
		   	    		OrderDataAccess.ORIGINAL_ORDER_DATE, OrderDataAccess.ORIGINAL_ORDER_TIME,
                        pOrderStatusCriteria.getOrderDateRangeBegin(), ">=") );
			}
			else {
				dbc.addGreaterOrEqual(OrderDataAccess.ORIGINAL_ORDER_DATE, orderDate);
		   	    dbc.addCondition(getTimeSubCondition (OrderDataAccess.ORIGINAL_ORDER_DATE, OrderDataAccess.ORIGINAL_ORDER_TIME,
                           pOrderStatusCriteria.getOrderDateRangeBegin(), ">=") );
			}
      	 }
	      	 
 	     if (Utility.isSet(pOrderStatusCriteria.getOrderDateRangeEnd())) {
 	        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.SIMPLE_DATE_PATTERN);
 	        Date orderDate = new Date();
 	        try {
 	          orderDate = simpleDateFormat.parse(pOrderStatusCriteria.getOrderDateRangeEnd().trim());
 	        } catch (Exception e) {
 	        	orderDate = simpleDateFormat.parse("1/1/3000");
 	        }
 	        Date dd = Utility.addDays(orderDate, 1);
 	        String nextD = simpleDateFormat.format(dd);
 	        
			//STJ-4759
			if (pOrderStatusCriteria.isFilterByRevisedOrderDate()) {
				dbc.addCondition(getTimeSubCondition(OrderDataAccess.REVISED_ORDER_DATE, OrderDataAccess.REVISED_ORDER_TIME,
						OrderDataAccess.ORIGINAL_ORDER_DATE, OrderDataAccess.ORIGINAL_ORDER_TIME,
                        nextD, "<="));
			}
			else {
				dbc.addCondition(getTimeSubCondition (OrderDataAccess.ORIGINAL_ORDER_DATE, OrderDataAccess.ORIGINAL_ORDER_TIME,
 	                                           nextD, "<="));
			}
 	      }
   }
    
    if (Utility.isSet(pOrderStatusCriteria.getErpPONum()) ||
    		Utility.isSet(pOrderStatusCriteria.getDistributorId()) ||
    		Utility.isSet(pOrderStatusCriteria.getDistributorIdVector()) ||
    		Utility.isSet(pOrderStatusCriteria.getOutboundPONum())) {
	 
		 String distributorErpNum = new String("");
		 if (pOrderStatusCriteria.getDistributorId().trim().length() > 0) {
			 
			 	DBCriteria subcrit = new DBCriteria();
			 	subcrit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_ID,pOrderStatusCriteria.getDistributorId().trim());
			 	subcrit.addEqualToIgnoreCase(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,RefCodeNames.BUS_ENTITY_TYPE_CD.DISTRIBUTOR);
		        
		        BusEntityDataVector distV = BusEntityDataAccess.select(conn,subcrit);
	
		        if (null != distV && 0 < distV.size()) {
		          BusEntityData distD = (BusEntityData) distV.get(0);
		          distributorErpNum = distD.getErpNum();
		        }
		 }
		 
		 ArrayList distributorErpNumList = new ArrayList();
     	 if (pOrderStatusCriteria.getDistributorIdVector() != null && pOrderStatusCriteria.getDistributorIdVector().size() > 0) {
     		 
     		 DBCriteria subcrit = new DBCriteria();
     		 subcrit.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID,IdVector.toCommaString(pOrderStatusCriteria.getDistributorIdVector()));
	        BusEntityDataVector distV = BusEntityDataAccess.select(conn, subcrit);
	        if (null != distV) {
	          Iterator dit = distV.iterator();
	          while (dit.hasNext()) {
	            BusEntityData distD = (BusEntityData) dit.next();
	            distributorErpNumList.add(distD.getErpNum().toUpperCase());
	          }
	        }
     	 }
	     	 
     	  StringBuffer buf = new StringBuffer();
	      buf.append("  SELECT ");
	      buf.append(OrderItemDataAccess.ORDER_ID);
	      buf.append(" FROM ");
	      buf.append(OrderItemDataAccess.CLW_ORDER_ITEM);
	      
	      DBCriteria dbCriteria = new DBCriteria();
	      
	      //add erp po num crit
	      if (Utility.isSet(pOrderStatusCriteria.getErpPONum())) {
	    	  dbCriteria.addEqualTo(OrderItemDataAccess.ERP_PO_NUM, pOrderStatusCriteria.getErpPONum().trim());
	      }
	      
	      //add outbound po num crit
		  if (Utility.isSet(pOrderStatusCriteria.getOutboundPONum())) {
			  dbCriteria.addEqualTo(OrderItemDataAccess.OUTBOUND_PO_NUM, pOrderStatusCriteria.getOutboundPONum().trim());
		  }
		  
		  //add dist erp num
	      if (Utility.isSet(pOrderStatusCriteria.getDistributorId())) {
	    	  dbCriteria.addEqualTo(OrderItemDataAccess.DIST_ERP_NUM, distributorErpNum.toUpperCase());
	      }
	      
	      //add dist list erp nums
	      if (Utility.isSet(distributorErpNumList) ) {
	    	  dbCriteria.addOneOf(OrderItemDataAccess.DIST_ERP_NUM, Utility.toCommaSting(distributorErpNumList, '\''));
	      }
	      
	     String subQuery = buildSubQuery(dbCriteria,buf,OrderItemDataAccess.CLW_ORDER_ITEM);
			 
		 log.debug("subQuery"+subQuery);
		 
	 	 dbc.addOneOf(OrderDataAccess.ORDER_ID, subQuery);
    }

    // Limit the number or rows returned.
    int maxRows = pOrderStatusCriteria.getMaxRows();

    // Place the most recent orders at the beginning of the vector.
    boolean isAscending = false;
    DBCriteria crit = new DBCriteria();
    // The pOrderIdV contains some list of orders to be included.

    if (null != pOrderIdV && pOrderIdV.size() > 0) {
      crit.addOneOf(OrderDataAccess.ORDER_ID, pOrderIdV);
    }

    //down to the second
    if (pOrderStatusCriteria.getExactOrderDateRangeEnd() != null) {
      crit.addLessOrEqual(OrderDataAccess.ORIGINAL_ORDER_DATE, pOrderStatusCriteria.getExactOrderDateRangeEnd());
      // crit.addCondition(OrderDataAccess.ORIGINAL_ORDER_TIME +"<" +DBAccess.getSQLDateTimeExpr(pOrderStatusCriteria.getExactOrderDateRangeEnd()));
    }

    // Join all the various order id select statements.
    String finalSql = "";
    boolean finalNeedsIntersect = false;
    
    String sql = buildSubQuery(dbc,queryBuilder,OrderDataAccess.CLW_ORDER);
    
    if (sql.length() > 0) {
      if (finalNeedsIntersect) {
        finalSql += " INTERSECT ";
      }
      finalNeedsIntersect = true;
      finalSql += sql;
    }

    if (invSql.length() > 0) {
      crit.addOneOf(OrderDataAccess.ORDER_ID, invSql);
      crit.addOrderBy(OrderDataAccess.ORDER_ID, isAscending);
      return OrderDataAccess.select(conn, crit, maxRows);
    }

    if (invCustSql.length() > 0) {
      crit.addOneOf(OrderDataAccess.ORDER_ID, invCustSql);
      crit.addOrderBy(OrderDataAccess.ORDER_ID, isAscending);
      return OrderDataAccess.select(conn, crit, maxRows);
    }

    if (orderPropSql.length() > 0) {
      if (finalNeedsIntersect) {
        finalSql += " INTERSECT ";
      }
      finalNeedsIntersect = true;
      finalSql += orderPropSql;
    }

    if (workflowSql.length() > 0) {
      if (finalNeedsIntersect) {
        finalSql += " INTERSECT ";
      }
      finalNeedsIntersect = true;
      finalSql += workflowSql;
    }

    // Get the order ids.
    crit.addOneOf(OrderDataAccess.ORDER_ID, finalSql);
    
    if (pOrderStatusCriteria.isIncludeRelatedOrder()){
    	IdVector orderIds = OrderDataAccess.selectIdOnly(conn, crit);
    	if (orderIds.isEmpty())
    		return new OrderDataVector();
    	else{
    		IdVector allRelatedIds = new IdVector();
    		sql = "SELECT ORDER1_ID, ORDER2_ID FROM CLW_ORDER_ASSOC WHERE ORDER1_ID = ? OR ORDER2_ID = ?";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		for (int i = 0; i < orderIds.size(); i++){
    			int orderId = (Integer)orderIds.get(i);
    			getRelatedOrderIds(pstmt, orderId, allRelatedIds);
    		}
    		crit = new DBCriteria();
    		crit.addOneOf(OrderDataAccess.ORDER_ID, allRelatedIds);
    	}
    }

    //STJ-4759
    if (Utility.isSet(pOrderStatusCriteria.getOrderBy())) {
    	crit.addOrderBy(pOrderStatusCriteria.getOrderBy(), 
    			Constants.DB_SORT_ORDER_ASCENDING.equals(pOrderStatusCriteria.getOrderDirection()));
    }
    else {
    	crit.addOrderBy(OrderDataAccess.ORDER_ID, isAscending);
    }
    log.debug("************************");
    log.info(OrderDataAccess.getSqlSelectIdOnly("*", crit));
    log.debug("************************");
    OrderDataVector returnValue = OrderDataAccess.select(conn, crit, maxRows);
    return returnValue;
  }

  /**
   * 
   * @return Return a list of order ids that related to orderId (REPLACED, SPLIT, CONSOLIDATED....)
   * 1. Place order A
   * 2. Order A was split into two orders: B and C
   * 3. Order C was replaced by order D

    Search order A should return A, B, C and D
    Search order B should return A, B, C and D
    Search order C should return A, B, C and D
    Search order D should return A, B, C and D
   * @throws Exception
   */
  private static IdVector getRelatedOrderIds(PreparedStatement pstmt, int orderId, IdVector ids) throws Exception{		
	  ids.add(orderId);
	  pstmt.setInt(1, orderId);
	  pstmt.setInt(2, orderId);
	  ResultSet rs = pstmt.executeQuery();
	  List<Integer> newIds = new ArrayList<Integer>();
	  while (rs.next()){				
		  int id1 = rs.getInt(1);
		  int id2 = rs.getInt(2);
		  if (!ids.contains(id1)){
			  newIds.add(id1);
		  }
		  if (!ids.contains(id2)){
			  newIds.add(id2);
		  }				
	  }
	  rs.close();
	  for (int id : newIds){
		  ids = getRelatedOrderIds(pstmt, id, ids);			
	  }
	  return ids;
  }

  /**
   * Returns a listing of order data objects that match the passed in criteria
   * Copy from getOrdersInCriteria();
   * Date Range SQL 'where' filling was updated
   */
  public static OrderDataVector getStoreOrdersInCriteria(Connection conn,
    OrderStatusCriteriaData pOrderStatusCriteria,
    IdVector pOrderIdV) throws Exception {
    log.info("2 Order search criteria: " + pOrderStatusCriteria);

    // determine the user type if crc, then not check the userId or account info
    String siteId = pOrderStatusCriteria.getSiteId();
    IdVector siteIdVector = pOrderStatusCriteria.getSiteIdVector();
    String accountId = pOrderStatusCriteria.getAccountId();
    IdVector accountIdVector = pOrderStatusCriteria.getAccountIdVector();
    IdVector storeIdVector = pOrderStatusCriteria.getStoreIdVector();
    List excludeOrderStatus = pOrderStatusCriteria.getExcludeOrderStatusList();
    List allowedOrderStatus = pOrderStatusCriteria.getOrderStatusList();

    boolean orderNotRecvd = pOrderStatusCriteria.getOrdersNotReceivedOnly();

    if (accountId != null) {
      accountId = accountId.trim();
    }
    if (siteId != null) {
      siteId = siteId.trim();
    }

    String sql = " select "
                 + " o.order_id from clw_order o "
                 + " where o.order_id is not null ";
    boolean needsAnd = true;

    if (!excludeOrderStatus.isEmpty()) {
      if (needsAnd) {
        sql += " AND ";
      }
      sql += "ORDER_STATUS_CD NOT IN (";
      Iterator it = excludeOrderStatus.iterator();
      while (it.hasNext()) {
        String os = (String) it.next();
        sql += "'" + os + "'";
        if (it.hasNext()) {
          sql += ",";
        }
      }
      sql += ")";
      needsAnd = true;
    }

    if (!allowedOrderStatus.isEmpty()) {
      if (needsAnd) {
        sql += " AND ";
      }
      sql += "ORDER_STATUS_CD IN (";
      Iterator it = allowedOrderStatus.iterator();
      while (it.hasNext()) {
        String os = (String) it.next();
        sql += "'" + os + "'";
        if (it.hasNext()) {
          sql += ",";
        }
      }
      sql += ")";
      needsAnd = true;
    }

    if (Utility.isSet(accountId)) {
      if (needsAnd) {
        sql += " AND ";
      }
      sql += OrderDataAccess.ACCOUNT_ID + " = '" + accountId + "'";
      needsAnd = true;
    }

    if (storeIdVector != null && storeIdVector.size() > 0) {
      if (needsAnd) {
        sql += " AND ";
      }
      sql += OrderDataAccess.STORE_ID + " in (" +
        IdVector.toCommaString(storeIdVector) + ")";
      needsAnd = true;
    }
    if (accountIdVector != null && accountIdVector.size() > 0) {
      if (needsAnd) {
        sql += " AND ";
      }
      sql += OrderDataAccess.ACCOUNT_ID + " in (" +
        IdVector.toCommaString(accountIdVector) + ")";
      needsAnd = true;
    }
    if (pOrderStatusCriteria.getCustPONum() != null && pOrderStatusCriteria.getCustPONum().trim().length() > 0) {
      if (needsAnd) {
        sql += " AND ";
      }
      sql += OrderDataAccess.REQUEST_PO_NUM + " like '" +
        pOrderStatusCriteria.getCustPONum().trim() + "%'";
      needsAnd = true;
    }

    if (!Utility.isSet(siteId)) {

      // if no site is specified and this is an MSB or CUSTOMER
      // then limit the orders they see.
      if (pOrderStatusCriteria.getUserTypeCd().length() > 0 &&
          (
            pOrderStatusCriteria.getUserTypeCd().equals
            (RefCodeNames.USER_TYPE_CD.MSB) ||
            pOrderStatusCriteria.getUserTypeCd().equals
            (RefCodeNames.USER_TYPE_CD.CUSTOMER)
          ) &&
          pOrderStatusCriteria.getUserId() != null &&
          pOrderStatusCriteria.getUserId().equals("0") == false
        ) {
        if (needsAnd) {
          sql += " AND ";
        }
        sql += " o.site_id in ( select distinct s.bus_entity_id from clw_bus_entity s," +
          " clw_user_assoc ua where ua.user_id = " + pOrderStatusCriteria.getUserId() +
          " and ua.bus_entity_id = s.bus_entity_id )";
        needsAnd = true;
      }
    }

    if (pOrderStatusCriteria.getWebOrderConfirmationNum().trim().length() > 0) {
      if (needsAnd) {
        sql += " AND ";
      }
      String webNum = pOrderStatusCriteria.getWebOrderConfirmationNum().trim();
      sql += " ( " + OrderDataAccess.ORDER_NUM + " = '" +
        webNum + "' or " + OrderDataAccess.ORDER_ID +
        " IN (SELECT " + OrderDataAccess.REF_ORDER_ID
        + " FROM clw_order WHERE " +
        OrderDataAccess.ORDER_NUM + " = '" + webNum + "'))";

      needsAnd = true;
    } else if (pOrderStatusCriteria.getRefOrderNum().trim().length() > 0) {
      if (needsAnd) {
        sql += " AND ";
      }
      sql += OrderDataAccess.REF_ORDER_NUM + " like '%" +
        pOrderStatusCriteria.getRefOrderNum().trim() + "%'";
      needsAnd = true;
    }

    if (Utility.isSet(siteId)) {
      if (needsAnd) {
        sql += " AND ";
      }
      sql += OrderDataAccess.SITE_ID + " = '" +
        siteId + "'";
      needsAnd = true;
    }
    if (siteIdVector != null && siteIdVector.size() > 0) {
        if (needsAnd) {
          sql += " AND ";
        }
        sql += OrderDataAccess.SITE_ID + " in (" +
          IdVector.toCommaString(siteIdVector) + ")";
        needsAnd = true;
    }

    if (pOrderStatusCriteria.getSiteZipCode().trim().length() > 0 ||
        pOrderStatusCriteria.getSiteCity().trim().length() > 0 ||
        pOrderStatusCriteria.getSiteState().trim().length() > 0) {
      String shiptoSql = "";

      shiptoSql += " select distinct oa.order_id from clw_order_address oa where " +
        " oa.order_id = o.order_id and " +
        OrderAddressDataAccess.ADDRESS_TYPE_CD + " = '" +
        RefCodeNames.ADDRESS_TYPE_CD.SHIPPING + "'";

      if (pOrderStatusCriteria.getSiteZipCode().trim().length() > 0) {
        shiptoSql += " and " + OrderAddressDataAccess.POSTAL_CODE +
          " like '" + pOrderStatusCriteria.getSiteZipCode().trim() + "%'";
      }

      if (pOrderStatusCriteria.getSiteCity().trim().length() > 0) {
        shiptoSql += " and upper(" + OrderAddressDataAccess.CITY + ")" +
          "like '%" + pOrderStatusCriteria.getSiteCity().trim().toUpperCase() + "%'";
      }

      if (pOrderStatusCriteria.getSiteState().trim().length() > 0) {
        shiptoSql += " and upper(" + OrderAddressDataAccess.STATE_PROVINCE_CD + ")" +
          " = '" + pOrderStatusCriteria.getSiteState().trim().toUpperCase() + "'";
      }

      if (pOrderStatusCriteria.getSiteCountry().trim().length() > 0) {
        shiptoSql += " and upper(" + OrderAddressDataAccess.COUNTRY_CD + ")" +
          " = '" + pOrderStatusCriteria.getSiteCountry().trim().toUpperCase() + "'";
      }

      if (needsAnd) {
        sql += " and ";
      }
      sql += " o.order_id in ( " + shiptoSql + ") ";

    }

    if (pOrderStatusCriteria.getOrderStatus().trim().length() > 0) {
      if (needsAnd) {
        sql += " AND ";
      }
      sql += OrderDataAccess.ORDER_STATUS_CD + " = '" +
        pOrderStatusCriteria.getOrderStatus().trim() + "'";
      needsAnd = true;
    }

    if (pOrderStatusCriteria.getErpOrderNum().trim().length() > 0) {
      if (needsAnd) {
        sql += " AND ";
      }
      sql += OrderDataAccess.ERP_ORDER_NUM + " = '" +
        pOrderStatusCriteria.getErpOrderNum().trim() + "'";
      needsAnd = true;
    }

    if (pOrderStatusCriteria.getMethod().trim().length() > 0) {
      if (needsAnd) {
        sql += " AND ";
      }
      sql += OrderDataAccess.ORDER_SOURCE_CD + " = '" +
        pOrderStatusCriteria.getMethod().trim() + "'";
      needsAnd = true;
    }

    if (pOrderStatusCriteria.getPlacedBy().trim().length() > 0) {
      if (needsAnd) {
        sql += " AND ";
      }
      sql += OrderDataAccess.ADD_BY + " IN("; 
      Iterator<String> placeByIterator = Utility.parseStringToList(pOrderStatusCriteria.getPlacedBy().trim(), ",").iterator();
      while(placeByIterator.hasNext()){
    	  sql += "'" + placeByIterator.next() + "'";
    	  if(placeByIterator.hasNext()) sql+= ",";    	  
      }
      sql +=" )";
      
      needsAnd = true;
    }

    String invSql = "";
    if (pOrderStatusCriteria.getInvoiceDistNum().trim().length() > 0 ||
        pOrderStatusCriteria.getShipFromId().trim().length() > 0) {
      invSql += " select distinct order_id from clw_invoice_dist where " +
        InvoiceDistDataAccess.ORDER_ID + " > 0 ";
      if (pOrderStatusCriteria.getInvoiceDistNum().trim().length() > 0) {
        invSql += " and " + InvoiceDistDataAccess.INVOICE_NUM + " = '" +
          pOrderStatusCriteria.getInvoiceDistNum().trim() + "'";
      }

      if (pOrderStatusCriteria.getShipFromId().trim().length() > 0) {

        DBCriteria subcrit2 = new DBCriteria();
        subcrit2.addEqualTo(AddressDataAccess.ADDRESS_ID,
                            pOrderStatusCriteria.getShipFromId().trim());
        subcrit2.addEqualToIgnoreCase(
          AddressDataAccess.ADDRESS_TYPE_CD,
          RefCodeNames.ADDRESS_TYPE_CD.DIST_SHIP_FROM);

        AddressDataVector addressV = AddressDataAccess.select(conn,
          subcrit2);

        if (null != addressV && addressV.size() > 0) {
          DBCriteria subcrit = new DBCriteria();

          AddressData shipFromAddress = (AddressData) addressV.get(0);
          subcrit.addEqualToIgnoreCase(
            InvoiceDistDataAccess.SHIP_FROM_ADDRESS_1,
            shipFromAddress.getAddress1());
          subcrit.addEqualToIgnoreCase(
            InvoiceDistDataAccess.SHIP_FROM_CITY,
            shipFromAddress.getCity());
          subcrit.addEqualToIgnoreCase(
            InvoiceDistDataAccess.SHIP_FROM_STATE,
            shipFromAddress.getStateProvinceCd());
          subcrit.addEqualToIgnoreCase(
            InvoiceDistDataAccess.SHIP_FROM_POSTAL_CODE,
            shipFromAddress.getPostalCode());

          invSql += " and " + subcrit.getWhereClause();
        }
      }

    }

    String invCustSql = "";
    if (pOrderStatusCriteria.getInvoiceCustNum().trim().length() > 0) {
      invCustSql += " select distinct order_id from clw_invoice_cust where " +
        InvoiceCustDataAccess.ORDER_ID + " > 0 and " +
        InvoiceCustDataAccess.INVOICE_NUM + " = '" +
        pOrderStatusCriteria.getInvoiceCustNum().trim() + "'";
    }

    String orderPropSql = "";
    if (pOrderStatusCriteria.getReferenceCode().trim().length() > 0) {
      orderPropSql += " select distinct order_id from clw_order_property where" +
        " order_id > 0 and " +
        OrderPropertyDataAccess.CLW_VALUE + " = '" +
        pOrderStatusCriteria.getReferenceCode().trim() + "'";
    }

    if (orderNotRecvd) {
      if (needsAnd) {
        sql += " and ";
      }
      sql += "not exists (select * from " + OrderPropertyDataAccess.CLW_ORDER_PROPERTY + " opnr where opnr." +
        OrderPropertyDataAccess.ORDER_ID +
        " = " + OrderDataAccess.CLW_ORDER + "." + OrderDataAccess.ORDER_ID + " and UPPER(" +
        OrderPropertyDataAccess.CLW_VALUE + ") = 'TRUE' and " +
        "opnr." + OrderPropertyDataAccess.ORDER_PROPERTY_TYPE_CD + " = '" +
        RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_RECEIVED + "')";
    }

    String workflowSql = "";
    if (pOrderStatusCriteria.getWorkflowId().trim().length() > 0) {

      workflowSql += " select distinct order_id from clw_workflow_queue where " +
        WorkflowQueueDataAccess.WORKFLOW_ID + " = " +
        pOrderStatusCriteria.getWorkflowId().trim();
    }

    if (pOrderStatusCriteria.getOrderDateRangeBegin().trim().length() > 0 &&
        pOrderStatusCriteria.getOrderDateRangeEnd().trim().length() > 0 &&
        pOrderStatusCriteria.getOrderDateRangeBegin().trim().equals(pOrderStatusCriteria.getOrderDateRangeEnd().trim())) {

      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
      java.util.Date orderDate = new java.util.Date();
      DBCriteria date_dbc = new DBCriteria();
      try {
        orderDate = simpleDateFormat.parse(pOrderStatusCriteria.getOrderDateRangeBegin().trim());
        if (null != orderDate) {
          date_dbc.addEqualTo(OrderDataAccess.ORIGINAL_ORDER_DATE, orderDate);
          date_dbc.addIsNull(OrderDataAccess.REVISED_ORDER_DATE);
          if (needsAnd) {
            sql += " and (";
          }
          sql += date_dbc.getWhereClause();
          date_dbc = new DBCriteria();
          date_dbc.addEqualTo(OrderDataAccess.REVISED_ORDER_DATE, orderDate);
          sql += " or ";
          sql += date_dbc.getWhereClause();
          sql += ")";
        }
      } catch (Exception e) {
        //just skip adding the date if the supplied date was not parseable
      }

    } else {
      if (pOrderStatusCriteria.getOrderDateRangeBegin().trim().length() > 0) {
       SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
       java.util.Date orderDate = new java.util.Date();
        try {
          orderDate = simpleDateFormat.parse(pOrderStatusCriteria.getOrderDateRangeBegin().trim());
        } catch (Exception e) {
          orderDate = simpleDateFormat.parse("1/1/1900");
        }
       DBCriteria date_dbc = new DBCriteria();

       date_dbc.addGreaterOrEqual(OrderDataAccess.ORIGINAL_ORDER_DATE, orderDate);
       date_dbc.addCondition(getTimeSubCondition (OrderDataAccess.ORIGINAL_ORDER_DATE,
                                                 OrderDataAccess.ORIGINAL_ORDER_TIME,
                                                 pOrderStatusCriteria.getOrderDateRangeBegin(), ">=") );

        date_dbc.addIsNull(OrderDataAccess.REVISED_ORDER_DATE);
        sql += " and (";
        sql += date_dbc.getWhereClause();
        date_dbc = new DBCriteria();
        date_dbc.addGreaterOrEqual(OrderDataAccess.REVISED_ORDER_DATE, orderDate);
        sql += " or ";
        sql += date_dbc.getWhereClause();
        sql += ")";
      }
      if (pOrderStatusCriteria.getOrderDateRangeEnd().trim().length() > 0) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        java.util.Date orderDate = new java.util.Date();
        try {
          orderDate = simpleDateFormat.parse(pOrderStatusCriteria.getOrderDateRangeEnd().trim());
        } catch (Exception e) {
          orderDate = simpleDateFormat.parse("1/1/3000");
        }
        DBCriteria date_dbc = new DBCriteria();
        
        java.util.Date dd = Utility.addDays(orderDate, 1);
        String nextD = simpleDateFormat.format(dd);       
        
        // adding 1 day here is necessary to be sure that range will be wider than in the time specified condition
        /*date_dbc.addLessOrEqual(OrderDataAccess.ORIGINAL_ORDER_DATE, Utility.addDays(orderDate, 1));
        date_dbc.addCondition(getTimeSubCondition (OrderDataAccess.ORIGINAL_ORDER_DATE,
                                           OrderDataAccess.ORIGINAL_ORDER_TIME,
                                           pOrderStatusCriteria.getOrderDateRangeEnd(), "<=") );*/
        date_dbc.addCondition(getTimeSubCondition (OrderDataAccess.ORIGINAL_ORDER_DATE,
                                           OrderDataAccess.ORIGINAL_ORDER_TIME,
                                           nextD, "<="));
        
        date_dbc.addIsNull(OrderDataAccess.REVISED_ORDER_DATE);
        sql += " and (";
        sql += date_dbc.getWhereClause();
        date_dbc = new DBCriteria();
        date_dbc.addLessOrEqual(OrderDataAccess.REVISED_ORDER_DATE, orderDate);
        sql += " or ";
        sql += date_dbc.getWhereClause();
        sql += ")";

      }
    }

    if (pOrderStatusCriteria.getErpPONum().trim().length() > 0 ||
        pOrderStatusCriteria.getDistributorId().trim().length() > 0 ||
        pOrderStatusCriteria.getDistributorIdVector().size() > 0||
        pOrderStatusCriteria.getOutboundPONum().trim().length()>0) {

      String distributorErpNum = new String("");

      if (pOrderStatusCriteria.getDistributorId().trim().length() > 0) {

        DBCriteria subcrit = new DBCriteria();
        subcrit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_ID,
                           pOrderStatusCriteria.getDistributorId().trim());
        subcrit.addEqualToIgnoreCase(
          BusEntityDataAccess.BUS_ENTITY_TYPE_CD,
          RefCodeNames.BUS_ENTITY_TYPE_CD.DISTRIBUTOR);

        BusEntityDataVector distV = BusEntityDataAccess.select(conn,
          subcrit);

        if (null != distV && 0 < distV.size()) {

          BusEntityData distD = (BusEntityData) distV.get(0);
          distributorErpNum = distD.getErpNum();
        }
      }

      ArrayList distributorErpNumList = new ArrayList();
      if (pOrderStatusCriteria.getDistributorIdVector() != null && pOrderStatusCriteria.getDistributorIdVector().size() > 0) {
        DBCriteria subcrit = new DBCriteria();
        subcrit.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID,
                         IdVector.toCommaString(pOrderStatusCriteria.getDistributorIdVector()));
        BusEntityDataVector distV = BusEntityDataAccess.select(conn, subcrit);
        if (null != distV) {
          Iterator dit = distV.iterator();
          while (dit.hasNext()) {
            BusEntityData distD = (BusEntityData) dit.next();
            distributorErpNumList.add(distD.getErpNum().toUpperCase());
          }
        }
      }

      StringBuffer buf = new StringBuffer();
      buf.append("  SELECT oi.");
      buf.append(OrderItemDataAccess.ORDER_ID);
      buf.append(" FROM ");
      buf.append(OrderItemDataAccess.CLW_ORDER_ITEM);
      buf.append(" oi WHERE  ");

      //add erp po num crit
      boolean subNeedsAnd = false;
      if (pOrderStatusCriteria.getErpPONum().trim().length() > 0) {
        buf.append(OrderItemDataAccess.ERP_PO_NUM);
        buf.append(" = ");
        buf.append("'" + pOrderStatusCriteria.getErpPONum().trim() + "'");
        subNeedsAnd = true;
      }
     //add outbound po num crit
     if (pOrderStatusCriteria.getOutboundPONum().trim().length() > 0) {
            if (subNeedsAnd) {
                buf.append(" AND ");
            }
            buf.append(OrderItemDataAccess.OUTBOUND_PO_NUM);
            buf.append(" = ");
            buf.append("'" + pOrderStatusCriteria.getOutboundPONum().trim() + "'");
            subNeedsAnd = true;
       }
      //add dist erp num
      if (pOrderStatusCriteria.getDistributorId().trim().length() > 0) {
        if (subNeedsAnd) {
          buf.append(" AND ");
        }
        buf.append(OrderItemDataAccess.DIST_ERP_NUM);
        buf.append(" = ");
        buf.append("'" + distributorErpNum.toUpperCase() + "'");
      }

      //add dist list erp nums
      if (distributorErpNumList != null && distributorErpNumList.size() > 0) {
        if (subNeedsAnd) {
          buf.append(" AND ");
        }
        buf.append(OrderItemDataAccess.DIST_ERP_NUM);
        buf.append(" IN ");
        buf.append("(" + Utility.toCommaSting(distributorErpNumList, '\'') + ")");
      }

      if (needsAnd) {
        sql += " and ";
      }
      sql += " o.order_id in (" + buf.toString() + " ) ";
    }

    // Limit the number or rows returned.
    int maxRows = 500;
    //Jd begin
    if ("jd".equals(Utility.getStoreDir()))
      maxRows = 1001;
    //Jd end

    // Place the most recent orders at the begining
    // of the vector.
    boolean isAscending = false;
    DBCriteria crit = new DBCriteria();
    // The pOrderIdV contains some list of orders to be included.

    if (null != pOrderIdV && pOrderIdV.size() > 0) {
      crit.addOneOf(OrderDataAccess.ORDER_ID, pOrderIdV);
    }

    //down to the second

    // Join all the various order id select statements.
    String finalSql = "";
    boolean finalNeedsIntersect = false;
    if (sql.length() > 0 && needsAnd) {
      if (finalNeedsIntersect) {
        finalSql += " intersect ";
      }
      finalNeedsIntersect = true;
      finalSql += sql;
    }

    if (invSql.length() > 0) {
      crit.addOneOf(OrderDataAccess.ORDER_ID, invSql);
      crit.addOrderBy(OrderDataAccess.ORDER_ID, isAscending);
      return OrderDataAccess.select(conn, crit, maxRows);
    }

    if (invCustSql.length() > 0) {
      crit.addOneOf(OrderDataAccess.ORDER_ID, invCustSql);
      crit.addOrderBy(OrderDataAccess.ORDER_ID, isAscending);
      return OrderDataAccess.select(conn, crit, maxRows);
    }

    if (orderPropSql.length() > 0) {
      if (finalNeedsIntersect) {
        finalSql += " intersect ";
      }
      finalNeedsIntersect = true;
      finalSql += orderPropSql;
    }

    if (workflowSql.length() > 0) {
      if (finalNeedsIntersect) {
        finalSql += " intersect ";
      }
      finalNeedsIntersect = true;
      finalSql += workflowSql;
    }

    // Get the order ids.
    crit.addOneOf(OrderDataAccess.ORDER_ID, finalSql);
    crit.addOrderBy(OrderDataAccess.ORDER_ID, isAscending);
    log.info("************************");
    log.info(OrderDataAccess.getSqlSelectIdOnly("*", crit));
    log.info("************************");
    return OrderDataAccess.select(conn, crit, maxRows);

  }


  public static OrderItemDescDataVector getOrderItemDescCollection(Connection conn, int pOrderId,
    String pErpPoNum,
    int pPurchaseOrderId) throws SQLException, DataNotFoundException {
    OrderItemDescDataVector orderItemDescV = null;

    // Caches bus entity data for this request.
    BusEntityDAO beDAO = new BusEntityDAO();

    PropertyUtil lPropUtil = new PropertyUtil(conn);
    OrderData orderD = OrderDataAccess.select(conn, pOrderId);
    DBCriteria crit = new DBCriteria();
    List orderHistory = null;
    // get the OrderItem data for this order status.
    crit = new DBCriteria();

    if (pOrderId > 0) {
      crit.addEqualTo(OrderItemDataAccess.ORDER_ID, pOrderId);
      orderHistory = ShoppingDAO.getOrderHistory(conn, pOrderId);
    }

    if (null != pErpPoNum && !"".equals(pErpPoNum.trim())) {
      crit.addEqualTo(OrderItemDataAccess.ERP_PO_NUM, pErpPoNum);
    }

    if (pPurchaseOrderId > 0) {
      crit.addEqualTo(OrderItemDataAccess.PURCHASE_ORDER_ID, pPurchaseOrderId);
    }

    OrderItemDataVector orderItemV = OrderItemDataAccess.select(conn, crit);

    Map distributorBEMap = new HashMap();
    Map orderFreightMap = new HashMap();
    Map distributorPropMap = new HashMap();
    Map purchaseOrderMap = new HashMap();
    Map invoiceDistMap = new HashMap();
    Map assetMap= new HashMap();
    Map itemMap=new HashMap();
    Map orderDiscountMap = new HashMap();

    if (null != orderItemV && 0 < orderItemV.size()) {
      orderItemDescV = new OrderItemDescDataVector();
      //get the order property data
      IdVector itmIds = new IdVector();
      IdVector itemIds= new IdVector();
      IdVector assetIds = new IdVector();

      for (int i = 0; i < orderItemV.size(); i++) {
        itmIds.add(new Integer(((OrderItemData) orderItemV.get(i)).getOrderItemId()));
        itemIds.add(new Integer(((OrderItemData) orderItemV.get(i)).getItemId()));
        assetIds.add(new Integer(((OrderItemData) orderItemV.get(i)).getAssetId()));
      }

        crit = new DBCriteria();
        crit.addOneOf(ItemDataAccess.ITEM_ID, itemIds);
        ItemDataVector itemDV = ItemDataAccess.select(conn, crit);
        if (itemDV != null && itemDV.size() > 0) {
            Iterator it = itemDV.iterator();
            while (it.hasNext()) {
                ItemData itemData = (ItemData) it.next();
                String itemKey = String.valueOf(itemData.getItemId());
                if (!itemMap.containsKey(itemKey)) {
                    itemMap.put(itemKey, itemData);
                }
            }
        }
        if (PipelineUtil.isSimpleServiceOrder(itemDV)) {
            crit = new DBCriteria();
            crit.addOneOf(AssetDataAccess.ASSET_ID, assetIds);
            AssetDataVector assetDV = AssetDataAccess.select(conn, crit);
            if (assetDV != null && assetDV.size() > 0) {
                Iterator it = assetDV.iterator();
                while (it.hasNext()) {
                    AssetData assetData = (AssetData) it.next();
                    String assetKey = String.valueOf(assetData.getAssetId());
                    if (!assetMap.containsKey(assetKey)) {
                        assetMap.put(assetKey, assetData);
                    }
                }
            }
        }

      crit = new DBCriteria();
      crit.addEqualTo(OrderPropertyDataAccess.ORDER_PROPERTY_STATUS_CD, RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
      crit.addOneOf(OrderPropertyDataAccess.ORDER_ITEM_ID, itmIds);
      //just get the notes
      //crit.addEqualToIgnoreCase(OrderPropertyDataAccess.ORDER_PROPERTY_TYPE_CD,RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
      OrderPropertyDataVector orderPropertyDataV = OrderPropertyDataAccess.select(conn, crit);

      // get the invoiceDists' Ids for this order
      crit = new DBCriteria();
      crit.addEqualTo(InvoiceDistDataAccess.ORDER_ID, pOrderId);
      crit.addNotEqualTo(InvoiceDistDataAccess.INVOICE_STATUS_CD,
                         RefCodeNames.INVOICE_STATUS_CD.CANCELLED);
      crit.addNotEqualTo(InvoiceDistDataAccess.INVOICE_STATUS_CD,
                         RefCodeNames.INVOICE_STATUS_CD.DUPLICATE);
      crit.addNotEqualTo(InvoiceDistDataAccess.INVOICE_STATUS_CD,
                         RefCodeNames.INVOICE_STATUS_CD.REJECTED);

      IdVector idV = InvoiceDistDataAccess.selectIdOnly(conn,
        InvoiceDistDataAccess.INVOICE_DIST_ID,
        crit);

      // get the invoiceDistDetails for this order
      crit = new DBCriteria();
      crit.addOneOf(InvoiceDistDetailDataAccess.INVOICE_DIST_ID, idV);
      crit.addOrderBy(InvoiceDistDetailDataAccess.INVOICE_DIST_DETAIL_ID);
      InvoiceDistDetailDataVector invoiceDistDetailV = InvoiceDistDetailDataAccess.select(conn, crit);

      // get the invoiceCusts' Ids for this order
      crit = new DBCriteria();
      crit.addEqualTo(InvoiceCustDataAccess.ORDER_ID, pOrderId);
      crit.addNotEqualTo(InvoiceCustDataAccess.INVOICE_STATUS_CD,
                         RefCodeNames.INVOICE_STATUS_CD.CANCELLED);
      crit.addNotEqualTo(InvoiceCustDataAccess.INVOICE_STATUS_CD,
                         RefCodeNames.INVOICE_STATUS_CD.DUPLICATE);
      crit.addNotEqualTo(InvoiceCustDataAccess.INVOICE_STATUS_CD,
                         RefCodeNames.INVOICE_STATUS_CD.REJECTED);
      InvoiceCustDataVector tempInvoiceCustlV = InvoiceCustDataAccess.select(conn, crit);
      idV = Utility.toIdVector(tempInvoiceCustlV);

      // get the invoiceCustDetails for this order
      crit = new DBCriteria();
      crit.addOneOf(InvoiceCustDetailDataAccess.INVOICE_CUST_ID, idV);

      InvoiceCustDetailDataVector invoiceCustDetailV =
        InvoiceCustDetailDataAccess.select(conn, crit);

      // get the orderItemSubstitutions  for this order
      crit = new DBCriteria();
      crit.addEqualTo(ItemSubstitutionDataAccess.ORDER_ID, pOrderId);

      ItemSubstitutionDataVector orderItemSubstitutionV =
        ItemSubstitutionDataAccess.select(conn, crit);

      // get the orderItemActions  for this order
      crit = new DBCriteria();
      crit.addEqualTo(OrderItemActionDataAccess.ORDER_ID, pOrderId);
      crit.addOrderBy(OrderItemActionDataAccess.ACTION_DATE, false);
      crit.addOrderBy(OrderItemActionDataAccess.ACTION_TIME, false);
      OrderItemActionDataVector orderItemActionV =
        OrderItemActionDataAccess.select(conn, crit);

      //get distributor po notes
      crit = new DBCriteria();
      crit.addEqualTo(OrderPropertyDataAccess.ORDER_ID, pOrderId);
      crit.addEqualTo(OrderPropertyDataAccess.ORDER_PROPERTY_TYPE_CD,
                      RefCodeNames.ORDER_PROPERTY_TYPE_CD.DISTRIBUTOR_PO_NOTE);
      crit.addEqualTo(OrderPropertyDataAccess.ORDER_PROPERTY_STATUS_CD,
                      RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
      crit.addOneOf(OrderPropertyDataAccess.ORDER_ITEM_ID, itmIds);
      OrderPropertyDataVector distNotes =
        OrderPropertyDataAccess.select(conn, crit);
      java.util.Date currentDate = new java.util.Date();

      //get standart product list info
      IdVector orderItemIdV = new IdVector();
      for (Iterator iter = orderItemV.iterator(); iter.hasNext(); ) {
        OrderItemData orderItemD = (OrderItemData) iter.next();
        orderItemIdV.add(new Integer(orderItemD.getOrderItemId()));
      }
      crit = new DBCriteria();
      crit.addOneOf(OrderItemMetaDataAccess.ORDER_ITEM_ID, orderItemIdV);
      crit.addEqualTo(OrderItemMetaDataAccess.NAME,
                      RefCodeNames.ORDER_ITEM_META_NAME.STANDARD_PRODUCT_LIST);
      OrderItemMetaDataVector splOrderItemMetaDV =
        OrderItemMetaDataAccess.select(conn, crit);

      //match up the selected data with the items
      for (int i = 0; i < orderItemV.size(); i++) {

        OrderItemData orderItemD = (OrderItemData) orderItemV.get(i);
        OrderItemDescData orderItemDescD = OrderItemDescData.createValue();
        if (RefCodeNames.ITEM_SALE_TYPE_CD.RE_SALE.equals(orderItemD.getSaleTypeCd())) {
          orderItemDescD.setReSale(true);
        } else {
          orderItemDescD.setReSale(false);
        }

        orderItemDescD.setTaxExempt(Utility.isTrue(orderItemD.getTaxExempt()));

        //match order property data
        OrderPropertyDataVector tempOrderPropertyDataV = new OrderPropertyDataVector();
        for (int j = 0; j < orderPropertyDataV.size(); j++) {
          OrderPropertyData opd = (OrderPropertyData) orderPropertyDataV.get(j);
          if (orderItemD.getOrderItemId() == opd.getOrderItemId()) {
            if (opd.getShortDesc() != null &&
                opd.getShortDesc().equals(RefCodeNames.ORDER_PROPERTY_TYPE_CD.OPEN_LINE_STATUS_CD)) {
              orderItemDescD.setOpenLineStatusCd(opd.getValue());
            } else if (opd.getOrderPropertyTypeCd().equals(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE)) {
              tempOrderPropertyDataV.add(opd);
            }
          }
        }
        orderItemDescD.setOrderItemNotes(tempOrderPropertyDataV);

        //match substitution data
        //not in use, durval 2006-8-14
        ItemSubstitutionDataVector tempOrderItemSubstitutionV = new ItemSubstitutionDataVector();
        for (int j = 0; j < orderItemSubstitutionV.size(); j++) {
          ItemSubstitutionData orderItemSubstitutionD =
            (ItemSubstitutionData) orderItemSubstitutionV.get(j);

          if (orderItemD.getOrderItemId() == orderItemSubstitutionD.getOrderItemId()) {
            tempOrderItemSubstitutionV.add(
              orderItemSubstitutionD);
          }
        }

        // match the invoiceCustDetail with orderItem or itemSubstitutions
        InvoiceCustDetailDataVector tempInvoiceCustDetailV = new InvoiceCustDetailDataVector();

        for (int j = 0; j < invoiceCustDetailV.size(); j++) {

          InvoiceCustDetailData invoiceCustDetailD =
            (InvoiceCustDetailData) invoiceCustDetailV.get(j);

          if (invoiceCustDetailD.getItemSkuNum() == orderItemD.getItemSkuNum()) {
            tempInvoiceCustDetailV.add(invoiceCustDetailD);
          } else {

            // match for the substitutions of this item
            for (int k = 0; k < tempOrderItemSubstitutionV.size(); k++) {

              ItemSubstitutionData orderItemSubstitutionD =
                (ItemSubstitutionData) tempOrderItemSubstitutionV.get(k);

              if (invoiceCustDetailD.getItemSkuNum() == orderItemSubstitutionD.getItemSkuNum()) {
                tempInvoiceCustDetailV.add(
                  invoiceCustDetailD);

                break;
              }
            } //end of k-loop
          }
        } // end of j-loop

        // match the invoiceDistDetail with orderItem or itemSubstitutions
        InvoiceDistDetailDataVector tempInvoiceDistDetailV = new InvoiceDistDetailDataVector();
        InvoiceDistDataVector tempInvoiceDistV = new InvoiceDistDataVector();
        for (int j = 0; j < invoiceDistDetailV.size(); j++) {
          InvoiceDistDetailData invoiceDistDetailD =
            (InvoiceDistDetailData) invoiceDistDetailV.get(j);

          if (invoiceDistDetailD.getOrderItemId() == orderItemD.getOrderItemId()) {
            tempInvoiceDistDetailV.add(invoiceDistDetailD);
            Integer key = new Integer(invoiceDistDetailD.getInvoiceDistId());
            InvoiceDistData invoiceDist = (InvoiceDistData) invoiceDistMap.get(key);
            if (invoiceDist == null) {
              invoiceDist = InvoiceDistDataAccess.select(conn, key.intValue());
              invoiceDistMap.put(key, invoiceDist);
            }
            if (invoiceDist != null) {
              tempInvoiceDistV.add(invoiceDist);
            }
          }

        } // end of j-loop

        OrderItemActionDataVector tempOrderItemActionV =
          new OrderItemActionDataVector();

        for (int j = 0; j < orderItemActionV.size(); j++) {

          OrderItemActionData orderItemActionD =
            (OrderItemActionData) orderItemActionV.get(j);

          if (orderItemD.getOrderItemId() == orderItemActionD.getOrderItemId()) {
            tempOrderItemActionV.add(orderItemActionD);
          }
        }

        //get the po
        PurchaseOrderData po = null;
        if (orderItemD.getPurchaseOrderId() != 0) {
          po = (PurchaseOrderData) purchaseOrderMap.get(
            new Integer(orderItemD.getPurchaseOrderId()));
          if (po == null) {
            po = PurchaseOrderDataAccess.select(conn, orderItemD.getPurchaseOrderId());
            purchaseOrderMap.put(new Integer(orderItemD.getPurchaseOrderId()), po);
          }
          orderItemDescD.setPurchaseOrderData(po);
        }

        // get the dist name
        String distName = null;
        String distErpNum = orderItemD.getDistErpNum();
        if (null != distErpNum) {
          //String lookupDistName = (String)
          BusEntityData distBE = (BusEntityData) distributorBEMap.get(distErpNum);
          if (distBE == null) {
            distBE = beDAO.getDistributorByErpNum(conn, distErpNum);
            distributorBEMap.put(distErpNum, distBE);
          }
          String distDispName = (String) distributorPropMap.get(distErpNum);
          if (distDispName == null && distBE != null) {
            try {
              distDispName = lPropUtil.fetchValueIgnoreMissing(0, distBE.getBusEntityId(),
                RefCodeNames.PROPERTY_TYPE_CD.RUNTIME_DISPLAY_NAME);
            } catch (RemoteException e) {
              e.printStackTrace();
            }
            if (!Utility.isSet(distDispName)) {
              distDispName = distBE.getShortDesc();
            }
              distributorPropMap.put(distErpNum , distDispName);
          }
          orderItemDescD.setDistRuntimeDisplayName(distDispName);
          orderItemDescD.setDistName(distBE.getShortDesc());
          orderItemDescD.setDistId(distBE.getBusEntityId());

        }
        
        
        /*if (orderItemDescD.getDistId() > 0) {
          String key = pOrderId + "::" + orderItemDescD.getDistId();
          if (orderFreightMap.containsKey(key)) {
            orderItemDescD.setOrderFreight((OrderFreightData) orderFreightMap.get(key));
          }
          crit = new DBCriteria();
          crit.addEqualTo(OrderFreightDataAccess.BUS_ENTITY_ID, orderItemDescD.getDistId());
          crit.addEqualTo(OrderFreightDataAccess.ORDER_ID, pOrderId);
          OrderFreightDataVector ofrt = OrderFreightDataAccess.select(conn, crit);
          if (ofrt == null || ofrt.size() == 0) {
            orderItemDescD.setOrderFreight(null);
          } else {
            orderItemDescD.setOrderFreight((OrderFreightData) ofrt.get(0));
          }
          orderFreightMap.put(key, orderItemDescD.getOrderFreight());
        }*/
        
        if(orderItemDescD.getDistId() >0){
        	crit = new DBCriteria();
            crit.addEqualTo(OrderFreightDataAccess.BUS_ENTITY_ID, orderItemDescD.getDistId());
            crit.addEqualTo(OrderFreightDataAccess.ORDER_ID, pOrderId);
            OrderFreightDataVector ofrt = OrderFreightDataAccess.select(conn, crit);
            
            for(int ii=0; ii<ofrt.size(); ii++){
            	OrderFreightData frD = (OrderFreightData)ofrt.get(ii);
            	if(frD.getShortDesc().equalsIgnoreCase("Freight")){
            		
            		orderItemDescD.setOrderFreight(frD);
            		
            	}else if(frD.getShortDesc().equalsIgnoreCase("Handling")){
            		
            		orderItemDescD.setOrderHandling(frD);
            		
            	}
            }
        }

        // set Order Discount for each Distributor (new staff for Discounts)
        if (orderItemDescD.getDistId() > 0) {
            String key = pOrderId + "::" + orderItemDescD.getDistId();
            if (orderDiscountMap.containsKey(key)) {
              orderItemDescD.setOrderDiscount((OrderAddOnChargeData) orderDiscountMap.get(key));
            }
            crit = new DBCriteria();
            crit.addEqualTo(OrderAddOnChargeDataAccess.BUS_ENTITY_ID, orderItemDescD.getDistId());
            crit.addEqualTo(OrderAddOnChargeDataAccess.ORDER_ID, pOrderId);
            OrderAddOnChargeDataVector oaoc = OrderAddOnChargeDataAccess.select(conn, crit);
            /*if (oaoc == null || oaoc.size() == 0) {
              orderItemDescD.setOrderDiscount(null);
            } else {
              orderItemDescD.setOrderDiscount((OrderAddOnChargeData) oaoc.get(0));
            }
            orderDiscountMap.put(key, orderItemDescD.getOrderDiscount());
            */
            
            for(int j=0; j<oaoc.size(); j++){
            	OrderAddOnChargeData charge = (OrderAddOnChargeData)oaoc.get(j);
            	if(charge.getDistFeeChargeCd().equals(RefCodeNames.CHARGE_CD.DISCOUNT)){
            		orderItemDescD.setOrderDiscount(charge);
            		orderDiscountMap.put(key, orderItemDescD.getOrderDiscount());
            		log.info("======Discount====="+charge.getAmount());
            		
            	}else if(charge.getDistFeeChargeCd().equals(RefCodeNames.CHARGE_CD.FUEL_SURCHARGE)){
            		orderItemDescD.setOrderFuelSurcharge(charge);
            		log.info("======FUEL SURCHARGE====="+charge.getAmount());
            		
            	}else if(charge.getDistFeeChargeCd().equals(RefCodeNames.CHARGE_CD.SMALL_ORDER_FEE)){
            		orderItemDescD.setOrderSmallOrderFee(charge);
            		log.info("======SMALL ORDER FEE====="+charge.getAmount());
            		
            	}
            }
            
        }
        
        //get the ship status of the item
        /**if (orderItemD.getQuantityConfirmed() > 0)
                         {

            if (orderItemD.getTargetShipDate()!= null && orderItemD.getTargetShipDate().compareTo(
                        currentDate) >= 0)
            {
                orderItemDescD.setShipStatus(
                    RefCodeNames.ORDER_PROPERTY_SHIP_STATUS.SCHEDULED);
            }
            else
            {
                if (orderItemD.getTargetShipDate()!= null){
                    orderItemDescD.setShipStatus(RefCodeNames.ORDER_PROPERTY_SHIP_STATUS.SHIPPED);
                }else{
                    orderItemDescD.setShipStatus(RefCodeNames.ORDER_PROPERTY_SHIP_STATUS.SCHEDULED);
                }
            }
                         }
                         else if (orderItemD.getQuantityBackordered() > 0)
                         {
            orderItemDescD.setShipStatus(
                RefCodeNames.ORDER_PROPERTY_SHIP_STATUS.BACKORDERED);
                         }
                         else
                         {
            orderItemDescD.setShipStatus("");
                         }*/
        orderItemDescD.setShipStatus("");
        // set the orderItemActionDescList, which including the substitution info
        // to display.
        //Create action objects
        OrderItemActionDataVector orderItemActionDV =
          new OrderItemActionDataVector();

        OrderItemActionData templOiaD = OrderItemActionData.createValue();
        templOiaD.setOrderId(orderD.getOrderId());
        templOiaD.setAffectedOrderNum(orderD.getOrderNum());
        templOiaD.setOrderItemId(orderItemD.getOrderItemId());
        templOiaD.setAffectedSku("" + orderItemD.getItemSkuNum());
        templOiaD.setAffectedLineItem(orderItemD.getOrderLineNum());

        //Create cust invoice actions
        for (Iterator iterInvCustDet = tempInvoiceCustDetailV.iterator();
                                       iterInvCustDet.hasNext(); ) {

          InvoiceCustDetailData icdD =
            (InvoiceCustDetailData) iterInvCustDet.next();

          // Is this invoice detail for this item.
          if (icdD.getItemSkuNum() !=
              orderItemD.getItemSkuNum())continue;

          InvoiceCustData icD = null;
          for (Iterator iterInvCust = tempInvoiceCustlV.iterator();
                                      iterInvCust.hasNext(); ) {
            // This invoice detail is for this item,
            // get the invoice cust entry.
            icD = (InvoiceCustData) iterInvCust.next();
            if (icD.getInvoiceCustId() == icdD.getInvoiceCustId())break;
          }

          // the assumption is that detail entries only
          // exist if an invoice cust entry has been made.
          OrderItemActionData oiaD =
            (OrderItemActionData) templOiaD.clone();
          oiaD.setActionCd(RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.CUST_INVOICED);
          oiaD.setActionDate(icD.getInvoiceDate());
          oiaD.setQuantity(icdD.getItemQuantity());
          orderItemActionDV.add(oiaD);
        }

        //Create dist invoice actions
        for (Iterator iterInvDistDet = tempInvoiceDistDetailV.iterator(),
                                       iterInvDist = tempInvoiceDistV.iterator();
          iterInvDistDet.hasNext() && iterInvDist.hasNext(); ) {
          InvoiceDistDetailData icdD =
            (InvoiceDistDetailData) iterInvDistDet.next();
          InvoiceDistData icD = (InvoiceDistData) iterInvDist.next();
          OrderItemActionData oiaD =
            (OrderItemActionData) templOiaD.clone();
          oiaD.setActionCd(RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.DIST_INVOICED);
          oiaD.setActionDate(icD.getInvoiceDate());
          int qty = icdD.getItemQuantity();
          if(qty==0) qty = icdD.getDistItemQtyReceived();
          oiaD.setQuantity(qty);
          orderItemActionDV.add(oiaD);
        }

        //Set po date
        if (po != null) {
          OrderItemActionData oiaD =
            (OrderItemActionData) templOiaD.clone();
          oiaD.setActionCd(RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.SYSTEM_ACCEPTED);
          oiaD.setActionDate(po.getPoDate());
          oiaD.setQuantity(orderItemD.getTotalQuantityOrdered());
          orderItemActionDV.add(oiaD);
        }

        orderItemActionDV.addAll(OrderDAO.getFilteredOrderItemActionsForOrderItem(conn, orderItemD.getOrderItemId()));

        //Order action by date (desc)
        Object[] orderItemActionA = orderItemActionDV.toArray();

        //sort the list
        for (int ii = 0; ii < orderItemActionA.length - 1; ii++) {
          boolean exitFl = true;
          for (int jj = 0; jj < orderItemActionA.length - ii - 1; jj++) {
            OrderItemActionData oiaD1 = (OrderItemActionData) orderItemActionA[jj];
            OrderItemActionData oiaD2 = (OrderItemActionData) orderItemActionA[jj + 1];
            //scrub and sort the dates
            java.util.Date date1 = oiaD1.getAddDate();
            java.util.Date date2 = oiaD2.getAddDate();
            if (date1 == null) {
              date1 = oiaD1.getActionDate();
            }
            if (date2 == null) {
              date2 = oiaD1.getActionDate();
            }
            if (date1 == null) {
              date1 = new java.util.Date();
            }
            if (date2 == null) {
              date2 = new java.util.Date();
            }
            if (oiaD1.getAddDate() == null) {
              oiaD1.setAddDate(date1);
            }
            if (oiaD2.getAddDate() == null) {
              oiaD2.setAddDate(date2);
            }
            if (date1.before(date2)) {
              orderItemActionA[jj] = oiaD2;
              orderItemActionA[jj + 1] = oiaD1;
              exitFl = false;
            }
          }
          if (exitFl) {
            break;
          }
        }
        orderItemActionDV = new OrderItemActionDataVector();
        OrderItemActionDescDataVector tempOrderItemActionDescV =
          new OrderItemActionDescDataVector();

        for (int ii = 0; ii < orderItemActionA.length; ii++) {
          OrderItemActionData oiaD = (OrderItemActionData) orderItemActionA[ii];
          orderItemActionDV.add(oiaD);
          OrderItemActionDescData oiadD = OrderItemActionDescData.createValue();
          oiadD.setOrderItemAction(oiaD);
          tempOrderItemActionDescV.add(oiadD);
        }

        ItemData itemInfo = (ItemData) itemMap.get(String.valueOf(orderItemD.getItemId()));
        AssetData assetInfo = (AssetData) assetMap.get(String.valueOf(orderItemD.getAssetId()));

        orderItemDescD.setOrderItemActionList(orderItemActionDV);
        orderItemDescD.setOrderItemActionDescList(tempOrderItemActionDescV);
        orderItemDescD.setOrderItem(orderItemD);
        orderItemDescD.setAssetInfo(assetInfo);
        orderItemDescD.setItemInfo(itemInfo);
        orderItemDescD.setShoppingHistory(orderHistory);
        orderItemDescD.setOrderItemSubstitutionList(tempOrderItemSubstitutionV);

        orderItemDescD.setInvoiceDistDetailList(tempInvoiceDistDetailV);
        orderItemDescD.setInvoiceDistDataVector(tempInvoiceDistV);
        orderItemDescD.setInvoiceCustDetailList(tempInvoiceCustDetailV);

        //OrderPropertyDataVector orderNotes = getOrderItemPropertyCollection(orderItemD.getOrderItemId(),RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
        //orderItemDescD.setOrderItemNotes(orderNotes);

        if (orderItemDescD.getOrderItemNotes().size() > 0) {
          orderItemDescD.setHasNote(true);
        } else {
          orderItemDescD.setHasNote(false);
        }

        //Set distributor po notes
        int orderItemId = orderItemD.getOrderItemId();
        for (Iterator iter = distNotes.iterator(); iter.hasNext(); ) {
          OrderPropertyData distNote = (OrderPropertyData) iter.next();
          if (orderItemId == distNote.getOrderItemId()) {
            orderItemDescD.setDistPoNote(distNote);
            break;
          }
        }

        // get standard product list flag
        for (Iterator iter = splOrderItemMetaDV.iterator(); iter.hasNext(); ) {
          OrderItemMetaData oimD = (OrderItemMetaData) iter.next();
          if (oimD.getOrderItemId() == orderItemId) {
            orderItemDescD.setStandardProductList(oimD.getValue());
            break;
          }
        }
        orderItemDescV.add(orderItemDescD);
      }
    }

    return orderItemDescV;
  }

    public static Collection saveInventoryOrderQtyLog(Connection conn,Collection qtyLog,UserData user) throws SQLException {
        InventoryOrderQtyDataVector  result = new InventoryOrderQtyDataVector();
        if( qtyLog!=null && !qtyLog.isEmpty()) {
            Iterator it = qtyLog.iterator();
            while(it.hasNext()){
                InventoryOrderQtyData log = (InventoryOrderQtyData) it.next();
                if(log.getInventoryOrderQtyId()>0){
                    log.setModBy(user.getUserName());
                    InventoryOrderQtyDataAccess.update(conn,log);
                } else {
                    log.setModBy(user.getUserName());
                    log.setAddBy(user.getUserName());
                    log = InventoryOrderQtyDataAccess.insert(conn,log);
                }
                result.add(log);
            }
        }
        return result;
    }
    
    // combine Date and Time for subCondition to enchance queries if TimeZone is specified
    private static String getTimeSubCondition (String pDateName, String pDateTime, String pValue, String pOperation ){
      String attrName = "(TO_DATE (TO_CHAR("+ pDateName + ",'MM/DD/YYYY')||" +
          "TO_CHAR(" + pDateTime + ",'hh24:mi:ss'), 'MM/DD/YYYY hh24:mi:ss'))" ;
      String attrValue = "TO_DATE('" + pValue.trim() + "', 'MM/DD/YYYY hh24:mi:ss')";

      return attrName + pOperation + attrValue;
    }
    
    // combine Date and Time for subCondition to enhance queries if TimeZone is specified
    private static String getTimeSubCondition (String pDateName, String pDateTime, 
    		String altDateName, String altDateTime, String pValue, String pOperation ){
      String attrName = "(TO_DATE (TO_CHAR(NVL("+ pDateName + "," + altDateName + "),'MM/DD/YYYY')||" +
          "TO_CHAR(NVL(" + pDateTime + "," + altDateTime + "),'hh24:mi:ss'), 'MM/DD/YYYY hh24:mi:ss'))" ;
      String attrValue = "TO_DATE('" + pValue.trim() + "', 'MM/DD/YYYY hh24:mi:ss')";

      return attrName + pOperation + attrValue;
    }

    public static BigDecimal getDiscountAmt(Connection pCon, int orderId) throws Exception {

        BigDecimal discountAmt = new BigDecimal(ZERO);

        DBCriteria crit = new DBCriteria();
        crit.addEqualTo(OrderMetaDataAccess.NAME, RefCodeNames.CHARGE_CD.DISCOUNT);
        crit.addEqualTo(OrderMetaDataAccess.ORDER_ID, orderId);

        OrderMetaDataVector discounts = OrderMetaDataAccess.select(pCon, crit);
        for (Object oDiscount : discounts) {
            OrderMetaData discount = (OrderMetaData) oDiscount;
            String dVal = discount.getValue();
            if(dVal.startsWith("-")){
            	dVal = dVal.replaceAll("-", "");
            }
            try {
                discountAmt = Utility.addAmt(discountAmt, new BigDecimal(dVal));
            } catch (java.lang.NumberFormatException e) {
                log.error(e);
                throw new Exception("Wrong number format of discount: " + dVal);
            }
        }
        return discountAmt;
    }

    public static IdVector getOrderIdsWithinPeriod(Connection pCon,
                                                   int pSiteId,
                                                   Date pDateRangeBegin,
                                                   Date pDateRangeEnd,
                                                   List pOrderStatusList) throws SQLException {

        String sql = "SELECT order_id FROM clw_order WHERE site_id = " + pSiteId +
                (pOrderStatusList != null ? " AND order_status_cd IN (" + Utility.toCommaSting(pOrderStatusList, '\'') + ")" : "") +
                (pDateRangeBegin != null ? "AND " + getTimeSubCondition("original_order_date", "original_order_time", DATE_FORMAT_4_SQL.format(pDateRangeBegin), ">=") : "") +
                (pDateRangeEnd != null ? "AND " + getTimeSubCondition("original_order_date", "original_order_time", DATE_FORMAT_4_SQL.format(pDateRangeEnd), "<=") : "");

        DBCriteria dbc = new DBCriteria();
        dbc.addOneOf(OrderDataAccess.ORDER_ID, sql);

        return OrderDataAccess.selectIdOnly(pCon, dbc);

    }

    public static String getOrdersTotalAmtSQL(String pOrdersSubSql) {
        return "SELECT SUM(nvl(o.total_price,0)" +
                "+nvl(o.total_freight_cost,0)" +
                "+nvl(o.total_misc_cost,0)" +
                "+nvl(o.total_rush_charge,0)" +
                "+nvl(o.total_tax_cost,0)" +
                "+nvl(meta.discount,0)" +
                "+nvl(meta.small_order_fee,0)" +
                "+nvl(meta.fuel_surcharge,0)) as orders_total FROM clw_order o"
                + " LEFT JOIN " +
                "(SELECT order_id, " +
                "MAX (CASE WHEN name = '" + RefCodeNames.CHARGE_CD.DISCOUNT + "' THEN clw_value ELSE NULL END) AS discount," +
                "MAX (CASE WHEN name = '" + RefCodeNames.CHARGE_CD.SMALL_ORDER_FEE + "' THEN clw_value ELSE NULL END) AS small_order_fee," +
                "MAX (CASE WHEN name = '" + RefCodeNames.CHARGE_CD.FUEL_SURCHARGE + "' THEN clw_value ELSE NULL END) AS fuel_surcharge " +
                "FROM clw_order_meta GROUP BY order_id) meta ON o.order_id = meta.order_id " +
                " WHERE o.order_id IN (" + pOrdersSubSql + ")";

    }
    
    
    private static String buildSubQuery(DBCriteria dbc,StringBuffer sqlBuf,String tableName) {
	   
    	String where;	    
	    Collection otherTables = dbc.getJoinTables();
	   
        if(otherTables == null || otherTables.isEmpty()){
                where = dbc.getSqlClause();
        }else{
            where = dbc.getSqlClause(tableName);

            Iterator it = otherTables.iterator();
            while(it.hasNext()){
                    String otherTable = (String) it.next();
                    if(!tableName.equals(otherTable)){
                    sqlBuf.append(",");
                    sqlBuf.append(otherTable);
				}
            }
        }

	    if (where != null && !where.equals("")) {
	        sqlBuf.append(" WHERE ");
	        sqlBuf.append(where);
	    }

	    String sql = sqlBuf.toString();
		return sql;
	}
    
 }
