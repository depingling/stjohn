package com.cleanwise.service.api.value;


import com.cleanwise.service.api.framework.*;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.math.*;

/**
 *  <code>OrderHistoryData</code> is a ValueObject 
 *  describing a backordered item and the order status info associated with it.
 *
 *@author     kevin
 *@created    December 11, 2001
 */
public class OrderHistoryData extends ValueObject {
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = -4405689341173261307L;

    private OrderData  mOrderStatusData;
    private OrderItemData mOrderItemStatusData;
	 
    /**
     *  Constructor.
     */
    public OrderHistoryData() { }


    /**
     *  Constructor.
     *
     *@param  parm1  Description of Parameter
     *@param  parm2  Description of Parameter     
     */
    public OrderHistoryData(OrderData parm1, OrderItemData parm2 ) {
        mOrderStatusData = parm1;
        mOrderItemStatusData = parm2;
    }


    /**
     *  Sets the OrderStatusData attribute of the OrderHistoryData object
     *
     *@param  pVal  The new OrderStatusData value
     */
    public void setOrderStatus(OrderData pVal) {
        this.mOrderStatusData = pVal;
    }


    /**
     *  Set the mOrderItemStatusData field.
     *
     *@param  pVal   The new OrderItemStatusData value
     */
    public void setOrderItemStatus(OrderItemData pVal) {
        this.mOrderItemStatusData = pVal;
    }
	
    /**
     *  Gets the OrderStatusData attribute of the OrderHistoryData object
     *
     *@return    The OrderStatusData value
     */
    public OrderData getOrderStatus() {
        return mOrderStatusData;
    }


    /**
     *  Get the mOrderItemStatusData field.
     *
     *@return    OrderItemStatusData
     */
    public OrderItemData getOrderItemStatus() {
        return mOrderItemStatusData;
    }
	
    /**
     *  Returns a String representation of the value object
     *
     *@return    The String representation of this OrderHistoryData object
     */
    public String toString() {      
        
        
        return "[" + "OrderStatusData=" + mOrderStatusData.toString() + 
		       ", OrderItemStatusData=" + mOrderItemStatusData.toString() +    "]";
    }


    /**
     *  Creates a new OrderHistoryData
     *
     *@return    Newly initialized OrderHistoryData object.
     */
    public static OrderHistoryData createValue() {
        OrderHistoryData valueData = new OrderHistoryData();
        return valueData;
    }

}

