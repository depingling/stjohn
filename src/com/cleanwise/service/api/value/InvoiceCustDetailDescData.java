package com.cleanwise.service.api.value;


import com.cleanwise.service.api.framework.*;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.math.*;

/**
 *  <code>InvoiceCustDetailDescData</code> is a ValueObject 
 *  describbing an order status.
 *
 *@author     liang
 *@created    Feb 25, 2002
 */
public class InvoiceCustDetailDescData extends ValueObject {
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = 1706738435852197569L;

    private InvoiceCustDetailData mInvoiceCustDetail;
    private OrderItemData mOrderItem;
    private String mDistName = new String("");
    private String mItemSkuNumS = new String("");
    private String mOrderItemIdS = new String("");
    private String mItemPriceS = new String("");
    private String mItemQuantityS = new String ("");

    
    /**
     *  Constructor.
     */
    public InvoiceCustDetailDescData() { }


    /**
     *  Constructor.
     *
     *@param  parm1  Description of Parameter
     *@param  parm2  Description of Parameter
     *@param  parm3  Description of Parameter
     */
    public InvoiceCustDetailDescData(InvoiceCustDetailData parm1, OrderItemData parm2) {
        mInvoiceCustDetail = parm1;
        mOrderItem = parm2;
    }


    /**
     *  Set the mInvoiceCustDetail field.
     *
     *@param  v   The new InvoiceCustDetail value
     */
    public void setInvoiceCustDetail(InvoiceCustDetailData v) {
        mInvoiceCustDetail = v;
    }



    /**
     *  Get the mInvoiceCustDetail field.
     *
     *@return    InvoiceCustDetailData
     */
    public InvoiceCustDetailData getInvoiceCustDetail() {
        return mInvoiceCustDetail;
    }



    /**
     *  Set the mOrderItem field.
     *
     *@param  v   The new OrderItem value
     */
    public void setOrderItem(OrderItemData v) {
        mOrderItem = v;
    }


    /**
     *  Set the DistName field.
     *
     *@param  v   The new DistName value
     */
    public void setDistName(String v) {
        mDistName = v;
    }
    
    /**
     *  Get the DistName field.
     *
     *@return    String
     */
    public String getDistName() {
        return mDistName;
    }


    
    /**
     *  Set the ItemSkuNumS field.
     *
     *@param  v   The new ItemSkuNumS value
     */
    public void setItemSkuNumS(String v) {
        mItemSkuNumS = v;
    }
    
    /**
     *  Get the ItemSkuNumS field.
     *
     *@return    String
     */
    public String getItemSkuNumS() {
        return mItemSkuNumS;
    }


    /**
     *  Set the OrderItemIdS field.
     *
     *@param  v   The new OrderItemIdS value
     */
    public void setOrderItemIdS(String v) {
        mOrderItemIdS = v;
    }
    
    /**
     *  Get the OrderItemIdS field.
     *
     *@return    String
     */
    public String getOrderItemIdS() {
        return mOrderItemIdS;
    }

    

    /**
     *  Get the mOrderItem field.
     *
     *@return    OrderItemData
     */
    public OrderItemData getOrderItem() {
        return mOrderItem;
    }



    /**
     *  Set the ItemQuantityS field.
     *
     *@param  v   The new ItemQuantityS value
     */
    public void setItemQuantityS(String v) {
        mItemQuantityS = v;
    }
    
    /**
     *  Get the ItemQuantityS field.
     *
     *@return    String
     */
    public String getItemQuantityS() {
        return mItemQuantityS;
    }


    /**
     *  Set the ItemPriceS field.
     *
     *@param  v   The new ItemPriceS value
     */
    public void setItemPriceS(String v) {
        mItemPriceS = v;
    }
    
    /**
     *  Get the ItemPriceS field.
     *
     *@return    String
     */
    public String getItemPriceS() {
        return mItemPriceS;
    }


    
    
    /**
     *  Returns a String representation of the value object
     *
     *@return    The String representation of this OrderItemStatusDescData object
     */
    public String toString() {
        
        
        return "[" +  "InvoiceCustDetail=" +
                mInvoiceCustDetail.toString() 
                 + "]";
    }


    /**
     *  Creates a new OrderItemStatusDescData
     *
     *@return    Newly initialized OrderItemStatusDescData object.
     */
    public static InvoiceCustDetailDescData createValue() {
        InvoiceCustDetailDescData valueData = new InvoiceCustDetailDescData();
        return valueData;
    }

}

