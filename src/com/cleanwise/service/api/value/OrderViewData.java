package com.cleanwise.service.api.value;
import com.cleanwise.service.api.framework.*;

public class OrderViewData  extends ValueObject{
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = 8644370431259259704L;
  private OrderData _order = null;
  private OrderItemData _orderItem = null;
  private OrderItemDataVector _orderItems = null;
  private OrderPropertyDataVector _orderPropertyDataVector = null;
  private OrderPropertyDataVector _uniqueKeyOrderPropertyData = null;
  private String _orderNumber = null;

  private OrderViewData(){}
  
  public static OrderViewData createValue(){
      OrderViewData ovd = new OrderViewData();
      ovd.setOrder(null);
      ovd.setOrderItem(null);
      ovd.setOrderPropertyDataVector(null);
      ovd.setUniqueKeyOrderPropertyData(null);
      return ovd;
  }

  public void setOrder(OrderData pValue) {_order = pValue;}
  public OrderData getOrder() {return _order;}

  public void setOrderItem(OrderItemData pValue) {_orderItem = pValue;}
  public OrderItemData getOrderItem() {return _orderItem;}
  
  public void setOrderItems(OrderItemDataVector pValue) {_orderItems = pValue;}
  public OrderItemDataVector getOrderItems() {return _orderItems;}
  
  public void setOrderPropertyDataVector(OrderPropertyDataVector pValue) {_orderPropertyDataVector = pValue;}
  public OrderPropertyDataVector getOrderPropertyDataVector() {return _orderPropertyDataVector;}
  
  public void setOrderNumber(String pValue) {_orderNumber = pValue;}
  public String getOrderNumber() {return _orderNumber;}
  
  /**
   *Used as a key to determine order uniqueness for orders where the order number is not
   *unique.  This is the case for JWP fro example.
   */
  public void setUniqueKeyOrderPropertyData(OrderPropertyDataVector pValue) {_uniqueKeyOrderPropertyData = pValue;}
  /**
   *Used as a key to determine order uniqueness for orders where the order number is not
   *unique.  This is the case for JWP fro example.
   */
  public OrderPropertyDataVector getUniqueKeyOrderPropertyData() {return _uniqueKeyOrderPropertyData;}
}
