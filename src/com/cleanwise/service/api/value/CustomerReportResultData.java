package com.cleanwise.service.api.value;


import com.cleanwise.service.api.framework.*;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.math.*;

/**
 *  <code>CustomerReportResultData</code> is a ValueObject 
 *  describbing an order status.
 *
 *@author     liang
 *@created    June 13, 2002
 */
public class CustomerReportResultData extends ValueObject {
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = 2896071789063280805L;

    private int    mSiteId   = 0; 
    private String mSiteName = new String("");
    private String mMainCategoryDesc = new String("");
    private String mSubCategoryDesc =  new String("");
    private BigDecimal mAmountNow = new BigDecimal(0);
    private BigDecimal mAmountBefore = new BigDecimal(0);
    private BigDecimal mAmountChange = new BigDecimal(0);
    private int mOrderNumNow = 0;
    private int mOrderNumBefore = 0;
    private int mOrderNumChange = 0;
    private BigDecimal mAvgOrderSizeNow = new BigDecimal(0);
    private BigDecimal mAvgOrderSizeBefore = new BigDecimal(0);
    private BigDecimal mAvgOrderSizeChange = new BigDecimal(0);
    private String mProductName =  new String("");
    private int mItemId =  0;
    
    /**
     *  Constructor.
     */
    public CustomerReportResultData() { }


    /**
     *  Constructor.
     *
     *@param  parm1  Description of Parameter
     *@param  parm2  Description of Parameter
     *@param  parm3  Description of Parameter
     */
    public CustomerReportResultData(BigDecimal parm1, BigDecimal parm2, BigDecimal parm3) {
        mAmountNow = parm1;
        mAmountBefore = parm2;
        mAmountChange = parm3;
    }


    /**
     *  Get the mSiteId field.
     *
     *@return    int
     */
    public int getSiteId() {
        return mSiteId;
    }

    /**
     *  Set the mSiteId field.
     *
     *@param  v   The new SiteId value
     */
    public void setSiteId(int v) {
        mSiteId = v;
    }

    
    /**
     *  Get the mSiteName field.
     *
     *@return    String
     */
    public String getSiteName() {
        return mSiteName;
    }

    /**
     *  Set the mSiteName field.
     *
     *@param  v   The new SiteName value
     */
    public void setSiteName(String v) {
        mSiteName = v;
    }


    /**
     *  Get the mMainCategoryDesc field.
     *
     *@return    String
     */
    public String getMainCategoryDesc() {
        return mMainCategoryDesc;
    }

    /**
     *  Set the mMainCategoryDesc field.
     *
     *@param  v   The new MainCategoryDesc value
     */
    public void setMainCategoryDesc(String v) {
        mMainCategoryDesc = v;
    }


    /**
     *  Get the mSubCategoryDesc field.
     *
     *@return    String
     */
    public String getSubCategoryDesc() {
        return mSubCategoryDesc;
    }

    /**
     *  Set the mSubCategoryDesc field.
     *
     *@param  v   The new SubCategoryDesc value
     */
    public void setSubCategoryDesc(String v) {
        mSubCategoryDesc = v;
    }


    /**
     *  Get the mAmountNow field.
     *
     *@return    BigDecimal
     */
    public BigDecimal getAmountNow() {
        if (null == mAmountNow) {
            mAmountNow = new BigDecimal(0);
        }
        return mAmountNow;
    }

    /**
     *  Set the mAmountNow field.
     *
     *@param  v   The new AmountNow value
     */
    public void setAmountNow(BigDecimal v) {
        mAmountNow = v;
    }


    
    /**
     *  Get the mAmountBefore field.
     *
     *@return    BigDecimal
     */
    public BigDecimal getAmountBefore() {
        if (null == mAmountBefore) {
            mAmountBefore = new BigDecimal(0);
        }
        return mAmountBefore;
    }

    /**
     *  Set the mAmountBefore field.
     *
     *@param  v   The new AmountBefore value
     */
    public void setAmountBefore(BigDecimal v) {
        mAmountBefore = v;
    }


    /**
     *  Get the mAmountChange field.
     *
     *@return    BigDecimal
     */
    public BigDecimal getAmountChange() {
        if (null == mAmountChange) {
            mAmountChange = new BigDecimal(0);
        }
        return mAmountChange;
    }

    /**
     *  Set the mAmountChange field.
     *
     *@param  v   The new AmountChange value
     */
    public void setAmountChange(BigDecimal v) {
        mAmountChange = v;
    }


    /**
     *  Get the mOrderNumNow field.
     *
     *@return    int
     */
    public int getOrderNumNow() {
        return mOrderNumNow;
    }

    /**
     *  Set the mOrderNumNow field.
     *
     *@param  v   The new OrderNumNow value
     */
    public void setOrderNumNow(int v) {
        mOrderNumNow = v;
    }


    /**
     *  Get the mOrderNumBefore field.
     *
     *@return    int
     */
    public int getOrderNumBefore() {
        return mOrderNumBefore;
    }

    /**
     *  Set the mOrderNumBefore field.
     *
     *@param  v   The new OrderNumBefore value
     */
    public void setOrderNumBefore(int v) {
        mOrderNumBefore = v;
    }


    /**
     *  Get the mOrderNumChange field.
     *
     *@return    int
     */
    public int getOrderNumChange() {
        return mOrderNumChange;
    }

    /**
     *  Set the mOrderNumChange field.
     *
     *@param  v   The new OrderNumChange value
     */
    public void setOrderNumChange(int v) {
        mOrderNumChange = v;
    }


    
    /**
     *  Get the mAvgOrderSizeNow field.
     *
     *@return    BigDecimal
     */
    public BigDecimal getAvgOrderSizeNow() {
        if (null == mAvgOrderSizeNow) {
            mAvgOrderSizeNow = new BigDecimal(0);
        }
        return mAvgOrderSizeNow;
    }

    /**
     *  Set the mAvgOrderSizeNow field.
     *
     *@param  v   The new AvgOrderSizeNow value
     */
    public void setAvgOrderSizeNow(BigDecimal v) {
        mAvgOrderSizeNow = v;
    }


    /**
     *  Get the mAvgOrderSizeBefore field.
     *
     *@return    BigDecimal
     */
    public BigDecimal getAvgOrderSizeBefore() {
        if (null == mAvgOrderSizeBefore) {
            mAvgOrderSizeBefore = new BigDecimal(0);
        }
        return mAvgOrderSizeBefore;
    }

    /**
     *  Set the mAvgOrderSizeBefore field.
     *
     *@param  v   The new AvgOrderSizeBefore value
     */
    public void setAvgOrderSizeBefore(BigDecimal v) {
        mAvgOrderSizeBefore = v;
    }


    /**
     *  Get the mAvgOrderSizeChange field.
     *
     *@return    BigDecimal
     */
    public BigDecimal getAvgOrderSizeChange() {
        if (null == mAvgOrderSizeChange) {
            mAvgOrderSizeChange = new BigDecimal(0);
        }
        return mAvgOrderSizeChange;
    }

    /**
     *  Set the mAvgOrderSizeChange field.
     *
     *@param  v   The new AvgOrderSizeChange value
     */
    public void setAvgOrderSizeChange(BigDecimal v) {
        mAvgOrderSizeChange = v;
    }


    
    /**
     *  Get the mProductName field.
     *
     *@return    String
     */
    public String getProductName() {
        return mProductName;
    }

    /**
     *  Set the mProductName field.
     *
     *@param  v   The new ProductName value
     */
    public void setProductName(String v) {
        mProductName = v;
    }


    /**
     *  Get the mItemId field.
     *
     *@return    int
     */
    public int getItemId() {
        return mItemId;
    }

    /**
     *  Set the mItemId field.
     *
     *@param  v   The new ItemId value
     */
    public void setItemId(int v) {
        mItemId = v;
    }


        
    /**
     *  Returns a String representation of the value object
     *
     *@return    The String representation of this OrderItemStatusDescData object
     */
    public String toString() {
        
        
        return "[" +  "AmountNow=" +
                mAmountNow.toString() + ", AmountBefore=" + mAmountBefore.toString() +
                ", AmountChange=" + mAmountChange.toString()
                 + "]";
    }


    /**
     *  Creates a new CustomerReportResultData
     *
     *@return    Newly initialized CustomerReportResultData object.
     */
    public static CustomerReportResultData createValue() {
        CustomerReportResultData valueData = new CustomerReportResultData();
        return valueData;
    }

}

