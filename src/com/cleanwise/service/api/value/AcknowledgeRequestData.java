package com.cleanwise.service.api.value;

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.util.RefCodeNames;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.math.BigDecimal;


/**
 * <code>AcknowledgeRequestData</code> is a ValueObject class
 *  representing an order request.
 */
public class AcknowledgeRequestData extends ValueObject
{
	//Do not remove or modify next line. It would break object DB saving
    private static final long serialVersionUID = 4713774671352566758L;
    private AckItemDataVector mAckItemDV;
    private String mErpPoNum;
    private String mVendorOrderNum;
    private String mCustPoNum;
    private int mStoreId;
    private Date mAckDate;
    String siteKey;
    String accountKey;
    private boolean requestCreateOrderIfNotExists;
    private String headerLevelActionCode;
    int accountId;
    int orderId;
    private boolean priceFromContract = false;
    private String matchPoNumType = RefCodeNames.MATCH_PO_NUM_TYPE_CD.DEFAULT;
    
    public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	
	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
    
    /** Holds value of property distributorsCompanyCode. */
    private String distributorsCompanyCode;

    /**
     * Creates a new AcknowledgeRequestData
     *
     * @return
     *  Newly initialized AcknowledgeRequestData object.
     */
    public static AcknowledgeRequestData createValue ()
    {
        AcknowledgeRequestData valueData = new AcknowledgeRequestData();

        return valueData;
    }

    /**
     * Get the value of AckItemDV.
     * @return value of AckItemDV.
     */
    public AckItemDataVector getAckItemDV() {
	    return mAckItemDV;
    }

    /**
     * Set the value of AckItemDV.
     * @param v  Value to assign to AckItemDV.
     */
    public void setAckItemDV(AckItemDataVector  v) {
	    mAckItemDV = v;
    }

    /**
     * Get the value of ErpPoNum.
     * @return value of ErpPoNum.
     */
    public String getErpPoNum() {
	    return mErpPoNum;
    }

    /**
     * Set the value of ErpPoNum.
     * @param v  Value to assign to ErpPoNum.
     */
    public void setErpPoNum(String v) {
	    mErpPoNum = v;
    }

    /**
     * Get the value of VendorOrderNum.
     * @return value of VendorOrderNum.
     */
    public String getVendorOrderNum() {
	    return mVendorOrderNum;
    }

    /**
     * Set the value of VendorOrderNum.
     * @param v  Value to assign to VendorOrderNum.
     */
    public void setVendorOrderNum(String v) {
	    mVendorOrderNum = v;
    }

    /**
     * Get the value of CustPoNum.
     * @return value of CustPoNum.
     */
    public String getCustPoNum() {
	    return mCustPoNum;
    }

    /**
     * Set the value of CustPoNum.
     * @param v  Value to assign to CustPoNum.
     */
    public void setCustPoNum(String v) {
	    mCustPoNum = v;
    }
    
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
    public void setStoreId(int v) {
	    mStoreId = v;
    }

    /**
     * Get the value of AckDate.
     * @return value of AckDate.
     */
    public Date getAckDate() {
	    return mAckDate;
    }

    /**
     * Set the value of AckDate.
     * @param v  Value to assign to AckDate.
     */
    public void setAckDate(Date v) {
	    mAckDate = v;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this OrderData object
     */
    public String toString()
    {
    return "[Erp PO Number=" + mErpPoNum + ", Vendor Order Number=" + mVendorOrderNum+ ",Store Id="+mStoreId+",Ack Date=" + mAckDate+ ",SiteKey="+siteKey + ",AccountKey="+accountKey+", Acknowledged Items=" + mAckItemDV + "]";
    }

    /** Getter for property distributorsCompanyCode.
     * @return Value of property distributorsCompanyCode.
     *
     */
    public String getDistributorsCompanyCode() {
        return this.distributorsCompanyCode;
    }

    /** Setter for property distributorsCompanyCode.
     * @param distributorsCompanyCode New value of property distributorsCompanyCode.
     *
     */
    public void setDistributorsCompanyCode(String distributorsCompanyCode) {
        this.distributorsCompanyCode = distributorsCompanyCode;
    }

    /**
     * The site key to find the site.  This is used when the 855 is used to create an order
     * automatically.  The site key is analogous to the siteName in the order reqquest object
     * @return the site key
     */
	public String getSiteKey() {
		return siteKey;
	}
	/**
     * The site key to find the site.  This is used when the 855 is used to create an order
     * automatically.  The site key is analogous to the siteName in the order reqquest object
     */
	public void setSiteKey(String siteKey) {
		this.siteKey = siteKey;
	}

	/**
     * The account key to find the account.  This is used when the 855 is used to create an order
     * automatically.
     * @return the account key
     */
	public String getAccountKey() {
		return accountKey;
	}
	/**
     * The account key to find the account.  This is used when the 855 is used to create an order
     * automatically.
     */
	public void setAccountKey(String accountKey) {
		this.accountKey = accountKey;
	}

	public boolean isRequestCreateOrderIfNotExists() {
		return requestCreateOrderIfNotExists;
	}

	public void setRequestCreateOrderIfNotExists(
			boolean requestCreateOrderIfNotExists) {
		this.requestCreateOrderIfNotExists = requestCreateOrderIfNotExists;
	}

	/**
	 * Action code at the header level.  Will set all of the items in the order to this action
	 * @return
	 */
	public String getHeaderLevelActionCode() {
		return headerLevelActionCode;
	}

	/**
	 * Action code at the header level.  Will set all of the items in the order to this action
	 */
	public void setHeaderLevelActionCode(String headerLevelActionCode) {
		this.headerLevelActionCode = headerLevelActionCode;
	}

	public void setPriceFromContract(boolean priceFromContract) {
		this.priceFromContract = priceFromContract;
	}

	public boolean isPriceFromContract() {
		return priceFromContract;
	}
	
	public void setMatchPoNumType(String matchPoNumType) {
		this.matchPoNumType = matchPoNumType;
	}

	public String getMatchNumType() {
		return matchPoNumType;
	}
	
	/**
    * creates a clone of this object, the mAckItemDV field is not cloned.
    *
    * @return AcknowledgeRequestData object
    */
    public Object clone() throws CloneNotSupportedException {
    	AcknowledgeRequestData myClone = AcknowledgeRequestData.createValue();
    	myClone.mErpPoNum = mErpPoNum;
    	myClone.mVendorOrderNum = mVendorOrderNum;
    	myClone.mStoreId = mStoreId;
    	myClone.mAckDate = (Date) mAckDate.clone();
    	myClone.siteKey = siteKey;
    	myClone.accountKey = accountKey;
    	myClone.requestCreateOrderIfNotExists = requestCreateOrderIfNotExists;
    	myClone.headerLevelActionCode = headerLevelActionCode;
    	myClone.matchPoNumType = matchPoNumType;
    	myClone.accountId = accountId;
    	myClone.priceFromContract = priceFromContract;
    	myClone.accountKey = accountKey;
    	
        return myClone;
    }

	
}
