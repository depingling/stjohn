package com.cleanwise.service.api.value;


import com.cleanwise.service.api.framework.*;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.math.*;

/**
 *  <code>InvoiceDistDetailDescData</code> is a ValueObject 
 *  describbing an order status.
 *
 *@author     liang
 *@created    Feb 25, 2002
 */
public class InvoiceDistDetailDescData extends ValueObject {
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = 225037827212734398L;

    private InvoiceDistDetailData mInvoiceDistDetail;
    private OrderItemData mOrderItem;
    private String mDistName = new String("");
    private String mItemSkuNumS = new String("");
    private String mOrderItemIdS = new String("");
    private String mCwCostS = new String("");
    private String mItemQuantityS = new String ("");

    /** Holds value of property invoiceDistDetailNotes. */
    private List invoiceDistDetailNotes;    
    
    /**
     *  Constructor.
     */
    public InvoiceDistDetailDescData() { }


    /**
     *  Constructor.
     *
     *@param  parm1  Description of Parameter
     *@param  parm2  Description of Parameter
     *@param  parm3  Description of Parameter
     */
    public InvoiceDistDetailDescData(InvoiceDistDetailData parm1, OrderItemData parm2) {
        mInvoiceDistDetail = parm1;
        mOrderItem = parm2;
    }


    /**
     *  Set the mInvoiceDistDetail field.
     *
     *@param  v   The new InvoiceDistDetail value
     */
    public void setInvoiceDistDetail(InvoiceDistDetailData v) {
        mInvoiceDistDetail = v;
    }



    /**
     *  Get the mInvoiceDistDetail field.
     *
     *@return    InvoiceDistDetailData
     */
    public InvoiceDistDetailData getInvoiceDistDetail() {
        return mInvoiceDistDetail;
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
     *  Set the CwCostS field.
     *
     *@param  v   The new CwCostS value
     */
    public void setCwCostS(String v) {
        mCwCostS = v;
    }
    
    /**
     *  Get the CwCostS field.
     *
     *@return    String
     */
    public String getCwCostS() {
        return mCwCostS;
    }


    
    
    /**
     *  Returns a String representation of the value object
     *
     *@return    The String representation of this OrderItemStatusDescData object
     */
    public String toString() {
        
        
        return "[" +  "InvoiceDistDetail=" +
                mInvoiceDistDetail.toString() 
                 + "]";
    }


    /**
     *  Creates a new OrderItemStatusDescData
     *
     *@return    Newly initialized OrderItemStatusDescData object.
     */
    public static InvoiceDistDetailDescData createValue() {
        InvoiceDistDetailDescData valueData = new InvoiceDistDetailDescData();
        return valueData;
    }

    /** Getter for property distInvoiceDetailNotes.
     * @return Value of property distInvoiceDetailNotes.
     *
     */
    public List getInvoiceDistDetailNotes() {
        return this.invoiceDistDetailNotes;
    }
    
    /** Setter for property distInvoiceDetailNotes.
     * @param distInvoiceDetailNotes New value of property distInvoiceDetailNotes.
     *
     */
    public void setInvoiceDistDetailNotes(List invoiceDistDetailNotes) {
        this.invoiceDistDetailNotes = invoiceDistDetailNotes;
    }
    
}

