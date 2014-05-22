package com.cleanwise.service.api.value;

import com.cleanwise.service.api.framework.*;
import java.lang.*;
import java.util.*;
import java.io.*;


/**
 * <code>OrderFulfillmentRequestData</code> is a ValueObject class 
 *  representing an order fulfillment request.
 * <p>
 * <pre>
 * Composition:
 * <br><br>
 *     OrderFulfillmentRequestData ::=
 *       distributor erp num, 1*order item entries
 *     order item entries ::= OrderItemData
 *
 *     Aggregate the items purchased for this site to be
 *     fulfilled by this distributor.
 * </p>
 * @author <a href="mailto:dvieira@DVIEIRA"></a>
 */
public class OrderFulfillmentData extends ValueObject
{
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = -4189744978798966867L;
    private String mErpNumDistributor;
    
    /**
     * Get the value of ErpNumDistributor.
     * @return value of ErpNumDistributor.
     */
    public String getErpNumDistributor() {
	return mErpNumDistributor;
    }
    
    /**
     * Set the value of ErpNumDistributor.
     * @param v  Value to assign to ErpNumDistributor.
     */
    public void setErpNumDistributor(String  v) {
	this.mErpNumDistributor = v;
    }
    
    private OrderItemDataVector mEntriesCollection = null;

    /**
     * Describe <code>addItemEntry</code> method here.
     *
     * @param pItemId an <code>int</code> value
     * @param pQty an <code>int</code> value
     * @param pPrice a <code>double</code> value
     */
    public void addItemEntry( OrderItemData poi ) {
	if ( null == mEntriesCollection ) {
	    mEntriesCollection = new OrderItemDataVector();
	}
	mEntriesCollection.add(poi);
    }


    /**
     * Get the value of EntriesCollection.
     * @return value of EntriesCollection.
     */
    public OrderItemDataVector getEntriesCollection() {
	if ( null == mEntriesCollection ) {
	    mEntriesCollection = new OrderItemDataVector();
	}
	return mEntriesCollection;
    }
    
    /**
     * Set the value of EntriesCollection.
     * @param v  Value to assign to EntriesCollection.
     */
    public void setEntriesCollection(OrderItemDataVector  v) {
	this.mEntriesCollection = v;
    }
    

    /**
     * Creates a new <code>OrderFulfillmentRequestData</code> instance.
     *
     * @param pAccountId an <code>int</code> value
     * @param pUserId an <code>int</code> value
     */
    public OrderFulfillmentData 
	(String pErpNumDistributor ) {
	mErpNumDistributor = pErpNumDistributor;
    }

    private OrderFulfillmentData () {
    }

    /**
     * Creates a new OrderFulfillmentRequestData
     *
     * @return
     *  Newly initialized OrderFulfillmentRequestData.java object.
     */
    public static OrderFulfillmentData createValue () 
    {
        OrderFulfillmentData valueData = 
	    new OrderFulfillmentData();
        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this OrderFulfillmentRequestData object
     */
    public String toString() {
        return "[" + 
	    " ErpNumDistributor=" + mErpNumDistributor +
	    ", EntriesCollection=" + mEntriesCollection +
	    "]";
    }
    
}
