package com.cleanwise.service.api.value;

import com.cleanwise.service.api.framework.*;
import java.lang.*;
import java.util.*;
import java.io.*;


/**
 * <code>ProcessOrderResultData</code> is a ValueObject
 * class wrapping of the database table CLW_ORDER.
 * @author <a href="mailto:dvieira@DVIEIRA"></a>
 */
public class ProcessOrderResultData extends ValueObject
{
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = 3128504657706586127L;

    private int mOrderId;
    private String mOrderStatusCd;
    private String mOrderNum = "";
    private java.util.Date mOrderDate;
    private int mSiteId = -1;
    private int mAccountId = -1;
    private int mStoreId = -1;
    private String mOrderSourceCd=null; 

    /**
     * Get the value of AccountId.
     * @return value of AccountId.
     */
    public int getAccountId() {
        return mAccountId;
    }

    /**
     * Set the value of AccountId.
     * @param v  Value to assign to AccountId.
     */
    public void setAccountId(int  v) {
        this.mAccountId = v;
    }

    /**
     * Get the value of SiteId.
     * @return value of SiteId.
     */
    public int getSiteId() {
        return mSiteId;
    }

    /**
     * Set the value of SiteId.
     * @param v  Value to assign to SiteId.
     */
    public void setSiteId(int  v) {
        this.mSiteId = v;
    }

    /**
     * Describe <code>getOrderNum</code> method here.
     *
     * @return a <code>String</code> value
     */
    public String getOrderNum() {
        return mOrderNum;
    }

    /**
     * Describe <code>setOrderNum</code> method here.
     *
     * @param v a <code>String</code> value
     */
    public void setOrderNum(String v) {
        mOrderNum = v;
    }

    /**
     * Describe <code>getOrderDate</code> method here.
     *
     * @return a <code>java.util.Date</code> value
     */
    public java.util.Date getOrderDate() {
        return mOrderDate;
    }
    /**
     * Describe <code>setOrderDate</code> method here.
     *
     * @param v a <code>java.util.Date</code> value
     */
    public void setOrderDate(java.util.Date v) {
        mOrderDate = v;
    }

    /**
     * Get the value of OrderStatusCd.
     * @return value of OrderStatusCd.
     */
    public String getOrderStatusCd() {
        return mOrderStatusCd;
    }

    /**
     * Set the value of OrderStatusCd.
     * @param v  Value to assign to OrderStatusCd.
     */
    public void setOrderStatusCd(String  v) {
        this.mOrderStatusCd = v;
    }


    private ProcessOrderResultData ()
    {
        mOrderId = -1;
        mOrderStatusCd = "---";
    }

    /**
     * Creates a new ProcessOrderResultData
     *
     * @return
     *  Newly initialized ProcessOrderResultData object.
     */
    public static ProcessOrderResultData createValue ()
    {
        ProcessOrderResultData valueData =
            new ProcessOrderResultData();
        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ProcessOrderResultData object
     */
    public String toString()
    {
        String s = "[" + "OrderId=" + mOrderId +
                   ", OrderNum=" + mOrderNum +
                   ", OrderDate=" + mOrderDate +
                   ", OrderStatusCd=" + mOrderStatusCd +
                   ", SiteId=" + mSiteId +
                   ", AccountId=" + mAccountId + "] \n";

        int n = mMsgs.size();
        for ( int i = 0; i < n; i++ )
        {
            s += "\n  [" + i + "] " + (String)mMsgs.get(i);
        }

        return s;
    }


    /**
     * Sets the OrderId field. This field is required to be set in the database.
     *
     * @param pOrderId
     *  int to use to update the field.
     */
    public void setOrderId(int pOrderId){
        this.mOrderId = pOrderId;
    }
    /**
     * Retrieves the OrderId field.
     *
     * @return
     *  int containing the OrderId field.
     */
    public int getOrderId(){
        return mOrderId;
    }

    int StoreId;

    /**
     * Get the value of StoreId.
     * @return value of StoreId.
     */
    public int getStoreId() {
        return mStoreId;
    }

    /**
     * Set the value of StoreId.
     * @param v  Value to assign to StoreId.
     */
    public void setStoreId(int  v) {
        this.mStoreId = v;
    }


    boolean mOK = true;
    public boolean isOK() {
        return mOK;
    }
    public void setOKFlag(boolean v) {
        mOK = v;
    }

    ArrayList mMsgs = new ArrayList(5);
    
    /** Holds value of property next. */
    private ProcessOrderResultData next;
    
    public void addResponseMsg(String pMsg) {
        mMsgs.add(pMsg);
    }

    public String [] getMessages() {
        int n = mMsgs.size();
        String [] marr = new String[n];
        for ( int i = 0; i < n; i++ )
        {
            marr[i] = (String)mMsgs.get(i);
        }

        return marr;
    }
    
    /** Getter for property next.
     * @return Value of property next.
     *
     */
    public ProcessOrderResultData getNext() {
        return this.next;
    }
    
    /** Setter for property next.
     * @param next New value of property next.
     *
     */
    public void setNext(ProcessOrderResultData next) {
        this.next = next;
    }

    public void setOrderSourceCd(String orderSourceCd) {
        this.mOrderSourceCd = orderSourceCd;
    }

    public String getOrderSourceCd() {
        return this.mOrderSourceCd;
    }
}
