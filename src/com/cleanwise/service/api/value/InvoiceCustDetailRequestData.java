package com.cleanwise.service.api.value;

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.util.RefCodeNames;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.math.BigDecimal;


/**
 * <code>InvoiceCustDetailRequestData</code> is a ValueObject class
 *  representing an order request.
 */
public class InvoiceCustDetailRequestData extends ValueObject
{
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = -9107746209238368563L;
    private InvoiceAbstractionDetailView mInvoiceDetailD;
    private OrderItemData mOrderItemD;
    private String mStoreCategory;
    
    
    public void setStoreCategory(String pVal) {
    	mStoreCategory = pVal;
    }
    
    public String getStoreCategory() {
	    return mStoreCategory;
    }
    
    /**
     * Creates a new InvoiceCustDetailRequestData
     *
     * @return
     *  Newly initialized InvoiceCustDetailRequestData object.
     */
    public static InvoiceCustDetailRequestData createValue ()
    {
        InvoiceCustDetailRequestData valueData = new InvoiceCustDetailRequestData();

        return valueData;
    }
    /**
     * Get the value of InvoiceDetailD.
     * @return value of InvoiceDetailD.
     */
    public InvoiceAbstractionDetailView getInvoiceDetailD() {
	    return mInvoiceDetailD;
    }

    /**
     * Set the value of InvoiceDetailD.
     * @param v  Value to assign to InvoiceDetailD.
     */
    public void setInvoiceDetailD(InvoiceCustDetailData v) {
	    mInvoiceDetailD = new InvoiceAbstractionDetailView(v);
    }
    
    public void setInvoiceDetailD(InvoiceDistDetailData v) {
	    mInvoiceDetailD = new InvoiceAbstractionDetailView(v);
    }

    /**
     * Get the value of OrderItemD.
     * @return value of OrderItemD.
     */
    public OrderItemData getOrderItemD() {
	    return mOrderItemD;
    }

    /**
     * Set the value of OrderItemD.
     * @param v  Value to assign to OrderItemD.
     */
    public void setOrderItemD(OrderItemData v) {
	    mOrderItemD = v;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this OrderData object
     */
    public String toString()
    {
    return "[" + "Order Item=" + mOrderItemD + ", Invoice Detail =" + mInvoiceDetailD + "]";
    }
}
