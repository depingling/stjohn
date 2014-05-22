
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        PreOrderData
 * Description:  This is a ValueObject class wrapping the database table CLW_PRE_ORDER.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.PreOrderDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>PreOrderData</code> is a ValueObject class wrapping of the database table CLW_PRE_ORDER.
 */
public class PreOrderData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 972033844014739069L;
    private int mPreOrderId;// SQL type:NUMBER, not null
    private int mAccountId;// SQL type:NUMBER
    private int mSiteId;// SQL type:NUMBER
    private String mSiteName;// SQL type:VARCHAR2
    private int mContractId;// SQL type:NUMBER
    private int mIncomingProfileId;// SQL type:NUMBER
    private int mTradingPartnerId;// SQL type:NUMBER
    private int mCostCenterId;// SQL type:NUMBER
    private int mUserId;// SQL type:NUMBER
    private String mUserName;// SQL type:VARCHAR2
    private String mSkuTypeCd;// SQL type:VARCHAR2
    private String mBillingOrder;// SQL type:VARCHAR2
    private String mBypassOrderRouting;// SQL type:VARCHAR2
    private String mBypassPreCapturePipeline;// SQL type:VARCHAR2
    private String mFreeFormatAddress;// SQL type:VARCHAR2
    private String mCustomerComments;// SQL type:VARCHAR2
    private String mCustomerOrderDate;// SQL type:VARCHAR2
    private String mCustomerPoNumber;// SQL type:VARCHAR2
    private java.math.BigDecimal mFreightCharge;// SQL type:NUMBER
    private java.math.BigDecimal mHandlingCharge;// SQL type:NUMBER
    private Date mHoldUntilDate;// SQL type:DATE
    private String mOrderContactName;// SQL type:VARCHAR2
    private String mOrderEmail;// SQL type:VARCHAR2
    private String mOrderFaxNumber;// SQL type:VARCHAR2
    private String mOrderNote;// SQL type:VARCHAR2
    private String mOrderRefNumber;// SQL type:VARCHAR2
    private Date mOrderRequestedShipDate;// SQL type:DATE
    private String mOrderSourceCd;// SQL type:VARCHAR2
    private String mOrderTelephoneNumber;// SQL type:VARCHAR2
    private String mPaymentType;// SQL type:VARCHAR2
    private String mOrderStatusCdOveride;// SQL type:VARCHAR2
    private String mAccountErpNumberOveride;// SQL type:VARCHAR2
    private String mCustomerBillingUnit;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private int mRefOrderId;// SQL type:NUMBER
    private String mOrderTypeCd;// SQL type:VARCHAR2
    private String mWorkflowInd;// SQL type:VARCHAR2
    private String mUserNameKey;// SQL type:VARCHAR2
    private java.math.BigDecimal mRushCharge;// SQL type:NUMBER
    private String mOrderBudgetTypeCd;// SQL type:VARCHAR2
    private String mLocaleCd;// SQL type:VARCHAR2
    private String mCurrencyCd;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public PreOrderData ()
    {
        mSiteName = "";
        mUserName = "";
        mSkuTypeCd = "";
        mBillingOrder = "";
        mBypassOrderRouting = "";
        mBypassPreCapturePipeline = "";
        mFreeFormatAddress = "";
        mCustomerComments = "";
        mCustomerOrderDate = "";
        mCustomerPoNumber = "";
        mOrderContactName = "";
        mOrderEmail = "";
        mOrderFaxNumber = "";
        mOrderNote = "";
        mOrderRefNumber = "";
        mOrderSourceCd = "";
        mOrderTelephoneNumber = "";
        mPaymentType = "";
        mOrderStatusCdOveride = "";
        mAccountErpNumberOveride = "";
        mCustomerBillingUnit = "";
        mAddBy = "";
        mModBy = "";
        mOrderTypeCd = "";
        mWorkflowInd = "";
        mUserNameKey = "";
        mOrderBudgetTypeCd = "";
        mLocaleCd = "";
        mCurrencyCd = "";
    }

    /**
     * Constructor.
     */
    public PreOrderData(int parm1, int parm2, int parm3, String parm4, int parm5, int parm6, int parm7, int parm8, int parm9, String parm10, String parm11, String parm12, String parm13, String parm14, String parm15, String parm16, String parm17, String parm18, java.math.BigDecimal parm19, java.math.BigDecimal parm20, Date parm21, String parm22, String parm23, String parm24, String parm25, String parm26, Date parm27, String parm28, String parm29, String parm30, String parm31, String parm32, String parm33, Date parm34, String parm35, Date parm36, String parm37, int parm38, String parm39, String parm40, String parm41, java.math.BigDecimal parm42, String parm43, String parm44, String parm45)
    {
        mPreOrderId = parm1;
        mAccountId = parm2;
        mSiteId = parm3;
        mSiteName = parm4;
        mContractId = parm5;
        mIncomingProfileId = parm6;
        mTradingPartnerId = parm7;
        mCostCenterId = parm8;
        mUserId = parm9;
        mUserName = parm10;
        mSkuTypeCd = parm11;
        mBillingOrder = parm12;
        mBypassOrderRouting = parm13;
        mBypassPreCapturePipeline = parm14;
        mFreeFormatAddress = parm15;
        mCustomerComments = parm16;
        mCustomerOrderDate = parm17;
        mCustomerPoNumber = parm18;
        mFreightCharge = parm19;
        mHandlingCharge = parm20;
        mHoldUntilDate = parm21;
        mOrderContactName = parm22;
        mOrderEmail = parm23;
        mOrderFaxNumber = parm24;
        mOrderNote = parm25;
        mOrderRefNumber = parm26;
        mOrderRequestedShipDate = parm27;
        mOrderSourceCd = parm28;
        mOrderTelephoneNumber = parm29;
        mPaymentType = parm30;
        mOrderStatusCdOveride = parm31;
        mAccountErpNumberOveride = parm32;
        mCustomerBillingUnit = parm33;
        mAddDate = parm34;
        mAddBy = parm35;
        mModDate = parm36;
        mModBy = parm37;
        mRefOrderId = parm38;
        mOrderTypeCd = parm39;
        mWorkflowInd = parm40;
        mUserNameKey = parm41;
        mRushCharge = parm42;
        mOrderBudgetTypeCd = parm43;
        mLocaleCd = parm44;
        mCurrencyCd = parm45;
        
    }

    /**
     * Creates a new PreOrderData
     *
     * @return
     *  Newly initialized PreOrderData object.
     */
    public static PreOrderData createValue ()
    {
        PreOrderData valueData = new PreOrderData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this PreOrderData object
     */
    public String toString()
    {
        return "[" + "PreOrderId=" + mPreOrderId + ", AccountId=" + mAccountId + ", SiteId=" + mSiteId + ", SiteName=" + mSiteName + ", ContractId=" + mContractId + ", IncomingProfileId=" + mIncomingProfileId + ", TradingPartnerId=" + mTradingPartnerId + ", CostCenterId=" + mCostCenterId + ", UserId=" + mUserId + ", UserName=" + mUserName + ", SkuTypeCd=" + mSkuTypeCd + ", BillingOrder=" + mBillingOrder + ", BypassOrderRouting=" + mBypassOrderRouting + ", BypassPreCapturePipeline=" + mBypassPreCapturePipeline + ", FreeFormatAddress=" + mFreeFormatAddress + ", CustomerComments=" + mCustomerComments + ", CustomerOrderDate=" + mCustomerOrderDate + ", CustomerPoNumber=" + mCustomerPoNumber + ", FreightCharge=" + mFreightCharge + ", HandlingCharge=" + mHandlingCharge + ", HoldUntilDate=" + mHoldUntilDate + ", OrderContactName=" + mOrderContactName + ", OrderEmail=" + mOrderEmail + ", OrderFaxNumber=" + mOrderFaxNumber + ", OrderNote=" + mOrderNote + ", OrderRefNumber=" + mOrderRefNumber + ", OrderRequestedShipDate=" + mOrderRequestedShipDate + ", OrderSourceCd=" + mOrderSourceCd + ", OrderTelephoneNumber=" + mOrderTelephoneNumber + ", PaymentType=" + mPaymentType + ", OrderStatusCdOveride=" + mOrderStatusCdOveride + ", AccountErpNumberOveride=" + mAccountErpNumberOveride + ", CustomerBillingUnit=" + mCustomerBillingUnit + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", RefOrderId=" + mRefOrderId + ", OrderTypeCd=" + mOrderTypeCd + ", WorkflowInd=" + mWorkflowInd + ", UserNameKey=" + mUserNameKey + ", RushCharge=" + mRushCharge + ", OrderBudgetTypeCd=" + mOrderBudgetTypeCd + ", LocaleCd=" + mLocaleCd + ", CurrencyCd=" + mCurrencyCd + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("PreOrder");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mPreOrderId));

        node =  doc.createElement("AccountId");
        node.appendChild(doc.createTextNode(String.valueOf(mAccountId)));
        root.appendChild(node);

        node =  doc.createElement("SiteId");
        node.appendChild(doc.createTextNode(String.valueOf(mSiteId)));
        root.appendChild(node);

        node =  doc.createElement("SiteName");
        node.appendChild(doc.createTextNode(String.valueOf(mSiteName)));
        root.appendChild(node);

        node =  doc.createElement("ContractId");
        node.appendChild(doc.createTextNode(String.valueOf(mContractId)));
        root.appendChild(node);

        node =  doc.createElement("IncomingProfileId");
        node.appendChild(doc.createTextNode(String.valueOf(mIncomingProfileId)));
        root.appendChild(node);

        node =  doc.createElement("TradingPartnerId");
        node.appendChild(doc.createTextNode(String.valueOf(mTradingPartnerId)));
        root.appendChild(node);

        node =  doc.createElement("CostCenterId");
        node.appendChild(doc.createTextNode(String.valueOf(mCostCenterId)));
        root.appendChild(node);

        node =  doc.createElement("UserId");
        node.appendChild(doc.createTextNode(String.valueOf(mUserId)));
        root.appendChild(node);

        node =  doc.createElement("UserName");
        node.appendChild(doc.createTextNode(String.valueOf(mUserName)));
        root.appendChild(node);

        node =  doc.createElement("SkuTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mSkuTypeCd)));
        root.appendChild(node);

        node =  doc.createElement("BillingOrder");
        node.appendChild(doc.createTextNode(String.valueOf(mBillingOrder)));
        root.appendChild(node);

        node =  doc.createElement("BypassOrderRouting");
        node.appendChild(doc.createTextNode(String.valueOf(mBypassOrderRouting)));
        root.appendChild(node);

        node =  doc.createElement("BypassPreCapturePipeline");
        node.appendChild(doc.createTextNode(String.valueOf(mBypassPreCapturePipeline)));
        root.appendChild(node);

        node =  doc.createElement("FreeFormatAddress");
        node.appendChild(doc.createTextNode(String.valueOf(mFreeFormatAddress)));
        root.appendChild(node);

        node =  doc.createElement("CustomerComments");
        node.appendChild(doc.createTextNode(String.valueOf(mCustomerComments)));
        root.appendChild(node);

        node =  doc.createElement("CustomerOrderDate");
        node.appendChild(doc.createTextNode(String.valueOf(mCustomerOrderDate)));
        root.appendChild(node);

        node =  doc.createElement("CustomerPoNumber");
        node.appendChild(doc.createTextNode(String.valueOf(mCustomerPoNumber)));
        root.appendChild(node);

        node =  doc.createElement("FreightCharge");
        node.appendChild(doc.createTextNode(String.valueOf(mFreightCharge)));
        root.appendChild(node);

        node =  doc.createElement("HandlingCharge");
        node.appendChild(doc.createTextNode(String.valueOf(mHandlingCharge)));
        root.appendChild(node);

        node =  doc.createElement("HoldUntilDate");
        node.appendChild(doc.createTextNode(String.valueOf(mHoldUntilDate)));
        root.appendChild(node);

        node =  doc.createElement("OrderContactName");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderContactName)));
        root.appendChild(node);

        node =  doc.createElement("OrderEmail");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderEmail)));
        root.appendChild(node);

        node =  doc.createElement("OrderFaxNumber");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderFaxNumber)));
        root.appendChild(node);

        node =  doc.createElement("OrderNote");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderNote)));
        root.appendChild(node);

        node =  doc.createElement("OrderRefNumber");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderRefNumber)));
        root.appendChild(node);

        node =  doc.createElement("OrderRequestedShipDate");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderRequestedShipDate)));
        root.appendChild(node);

        node =  doc.createElement("OrderSourceCd");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderSourceCd)));
        root.appendChild(node);

        node =  doc.createElement("OrderTelephoneNumber");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderTelephoneNumber)));
        root.appendChild(node);

        node =  doc.createElement("PaymentType");
        node.appendChild(doc.createTextNode(String.valueOf(mPaymentType)));
        root.appendChild(node);

        node =  doc.createElement("OrderStatusCdOveride");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderStatusCdOveride)));
        root.appendChild(node);

        node =  doc.createElement("AccountErpNumberOveride");
        node.appendChild(doc.createTextNode(String.valueOf(mAccountErpNumberOveride)));
        root.appendChild(node);

        node =  doc.createElement("CustomerBillingUnit");
        node.appendChild(doc.createTextNode(String.valueOf(mCustomerBillingUnit)));
        root.appendChild(node);

        node =  doc.createElement("AddDate");
        node.appendChild(doc.createTextNode(String.valueOf(mAddDate)));
        root.appendChild(node);

        node =  doc.createElement("AddBy");
        node.appendChild(doc.createTextNode(String.valueOf(mAddBy)));
        root.appendChild(node);

        node =  doc.createElement("ModDate");
        node.appendChild(doc.createTextNode(String.valueOf(mModDate)));
        root.appendChild(node);

        node =  doc.createElement("ModBy");
        node.appendChild(doc.createTextNode(String.valueOf(mModBy)));
        root.appendChild(node);

        node =  doc.createElement("RefOrderId");
        node.appendChild(doc.createTextNode(String.valueOf(mRefOrderId)));
        root.appendChild(node);

        node =  doc.createElement("OrderTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderTypeCd)));
        root.appendChild(node);

        node =  doc.createElement("WorkflowInd");
        node.appendChild(doc.createTextNode(String.valueOf(mWorkflowInd)));
        root.appendChild(node);

        node =  doc.createElement("UserNameKey");
        node.appendChild(doc.createTextNode(String.valueOf(mUserNameKey)));
        root.appendChild(node);

        node =  doc.createElement("RushCharge");
        node.appendChild(doc.createTextNode(String.valueOf(mRushCharge)));
        root.appendChild(node);

        node =  doc.createElement("OrderBudgetTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderBudgetTypeCd)));
        root.appendChild(node);

        node =  doc.createElement("LocaleCd");
        node.appendChild(doc.createTextNode(String.valueOf(mLocaleCd)));
        root.appendChild(node);

        node =  doc.createElement("CurrencyCd");
        node.appendChild(doc.createTextNode(String.valueOf(mCurrencyCd)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the PreOrderId field is not cloned.
    *
    * @return PreOrderData object
    */
    public Object clone(){
        PreOrderData myClone = new PreOrderData();
        
        myClone.mAccountId = mAccountId;
        
        myClone.mSiteId = mSiteId;
        
        myClone.mSiteName = mSiteName;
        
        myClone.mContractId = mContractId;
        
        myClone.mIncomingProfileId = mIncomingProfileId;
        
        myClone.mTradingPartnerId = mTradingPartnerId;
        
        myClone.mCostCenterId = mCostCenterId;
        
        myClone.mUserId = mUserId;
        
        myClone.mUserName = mUserName;
        
        myClone.mSkuTypeCd = mSkuTypeCd;
        
        myClone.mBillingOrder = mBillingOrder;
        
        myClone.mBypassOrderRouting = mBypassOrderRouting;
        
        myClone.mBypassPreCapturePipeline = mBypassPreCapturePipeline;
        
        myClone.mFreeFormatAddress = mFreeFormatAddress;
        
        myClone.mCustomerComments = mCustomerComments;
        
        myClone.mCustomerOrderDate = mCustomerOrderDate;
        
        myClone.mCustomerPoNumber = mCustomerPoNumber;
        
        myClone.mFreightCharge = mFreightCharge;
        
        myClone.mHandlingCharge = mHandlingCharge;
        
        if(mHoldUntilDate != null){
                myClone.mHoldUntilDate = (Date) mHoldUntilDate.clone();
        }
        
        myClone.mOrderContactName = mOrderContactName;
        
        myClone.mOrderEmail = mOrderEmail;
        
        myClone.mOrderFaxNumber = mOrderFaxNumber;
        
        myClone.mOrderNote = mOrderNote;
        
        myClone.mOrderRefNumber = mOrderRefNumber;
        
        if(mOrderRequestedShipDate != null){
                myClone.mOrderRequestedShipDate = (Date) mOrderRequestedShipDate.clone();
        }
        
        myClone.mOrderSourceCd = mOrderSourceCd;
        
        myClone.mOrderTelephoneNumber = mOrderTelephoneNumber;
        
        myClone.mPaymentType = mPaymentType;
        
        myClone.mOrderStatusCdOveride = mOrderStatusCdOveride;
        
        myClone.mAccountErpNumberOveride = mAccountErpNumberOveride;
        
        myClone.mCustomerBillingUnit = mCustomerBillingUnit;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        myClone.mRefOrderId = mRefOrderId;
        
        myClone.mOrderTypeCd = mOrderTypeCd;
        
        myClone.mWorkflowInd = mWorkflowInd;
        
        myClone.mUserNameKey = mUserNameKey;
        
        myClone.mRushCharge = mRushCharge;
        
        myClone.mOrderBudgetTypeCd = mOrderBudgetTypeCd;
        
        myClone.mLocaleCd = mLocaleCd;
        
        myClone.mCurrencyCd = mCurrencyCd;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (PreOrderDataAccess.PRE_ORDER_ID.equals(pFieldName)) {
            return getPreOrderId();
        } else if (PreOrderDataAccess.ACCOUNT_ID.equals(pFieldName)) {
            return getAccountId();
        } else if (PreOrderDataAccess.SITE_ID.equals(pFieldName)) {
            return getSiteId();
        } else if (PreOrderDataAccess.SITE_NAME.equals(pFieldName)) {
            return getSiteName();
        } else if (PreOrderDataAccess.CONTRACT_ID.equals(pFieldName)) {
            return getContractId();
        } else if (PreOrderDataAccess.INCOMING_PROFILE_ID.equals(pFieldName)) {
            return getIncomingProfileId();
        } else if (PreOrderDataAccess.TRADING_PARTNER_ID.equals(pFieldName)) {
            return getTradingPartnerId();
        } else if (PreOrderDataAccess.COST_CENTER_ID.equals(pFieldName)) {
            return getCostCenterId();
        } else if (PreOrderDataAccess.USER_ID.equals(pFieldName)) {
            return getUserId();
        } else if (PreOrderDataAccess.USER_NAME.equals(pFieldName)) {
            return getUserName();
        } else if (PreOrderDataAccess.SKU_TYPE_CD.equals(pFieldName)) {
            return getSkuTypeCd();
        } else if (PreOrderDataAccess.BILLING_ORDER.equals(pFieldName)) {
            return getBillingOrder();
        } else if (PreOrderDataAccess.BYPASS_ORDER_ROUTING.equals(pFieldName)) {
            return getBypassOrderRouting();
        } else if (PreOrderDataAccess.BYPASS_PRE_CAPTURE_PIPELINE.equals(pFieldName)) {
            return getBypassPreCapturePipeline();
        } else if (PreOrderDataAccess.FREE_FORMAT_ADDRESS.equals(pFieldName)) {
            return getFreeFormatAddress();
        } else if (PreOrderDataAccess.CUSTOMER_COMMENTS.equals(pFieldName)) {
            return getCustomerComments();
        } else if (PreOrderDataAccess.CUSTOMER_ORDER_DATE.equals(pFieldName)) {
            return getCustomerOrderDate();
        } else if (PreOrderDataAccess.CUSTOMER_PO_NUMBER.equals(pFieldName)) {
            return getCustomerPoNumber();
        } else if (PreOrderDataAccess.FREIGHT_CHARGE.equals(pFieldName)) {
            return getFreightCharge();
        } else if (PreOrderDataAccess.HANDLING_CHARGE.equals(pFieldName)) {
            return getHandlingCharge();
        } else if (PreOrderDataAccess.HOLD_UNTIL_DATE.equals(pFieldName)) {
            return getHoldUntilDate();
        } else if (PreOrderDataAccess.ORDER_CONTACT_NAME.equals(pFieldName)) {
            return getOrderContactName();
        } else if (PreOrderDataAccess.ORDER_EMAIL.equals(pFieldName)) {
            return getOrderEmail();
        } else if (PreOrderDataAccess.ORDER_FAX_NUMBER.equals(pFieldName)) {
            return getOrderFaxNumber();
        } else if (PreOrderDataAccess.ORDER_NOTE.equals(pFieldName)) {
            return getOrderNote();
        } else if (PreOrderDataAccess.ORDER_REF_NUMBER.equals(pFieldName)) {
            return getOrderRefNumber();
        } else if (PreOrderDataAccess.ORDER_REQUESTED_SHIP_DATE.equals(pFieldName)) {
            return getOrderRequestedShipDate();
        } else if (PreOrderDataAccess.ORDER_SOURCE_CD.equals(pFieldName)) {
            return getOrderSourceCd();
        } else if (PreOrderDataAccess.ORDER_TELEPHONE_NUMBER.equals(pFieldName)) {
            return getOrderTelephoneNumber();
        } else if (PreOrderDataAccess.PAYMENT_TYPE.equals(pFieldName)) {
            return getPaymentType();
        } else if (PreOrderDataAccess.ORDER_STATUS_CD_OVERIDE.equals(pFieldName)) {
            return getOrderStatusCdOveride();
        } else if (PreOrderDataAccess.ACCOUNT_ERP_NUMBER_OVERIDE.equals(pFieldName)) {
            return getAccountErpNumberOveride();
        } else if (PreOrderDataAccess.CUSTOMER_BILLING_UNIT.equals(pFieldName)) {
            return getCustomerBillingUnit();
        } else if (PreOrderDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (PreOrderDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (PreOrderDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (PreOrderDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (PreOrderDataAccess.REF_ORDER_ID.equals(pFieldName)) {
            return getRefOrderId();
        } else if (PreOrderDataAccess.ORDER_TYPE_CD.equals(pFieldName)) {
            return getOrderTypeCd();
        } else if (PreOrderDataAccess.WORKFLOW_IND.equals(pFieldName)) {
            return getWorkflowInd();
        } else if (PreOrderDataAccess.USER_NAME_KEY.equals(pFieldName)) {
            return getUserNameKey();
        } else if (PreOrderDataAccess.RUSH_CHARGE.equals(pFieldName)) {
            return getRushCharge();
        } else if (PreOrderDataAccess.ORDER_BUDGET_TYPE_CD.equals(pFieldName)) {
            return getOrderBudgetTypeCd();
        } else if (PreOrderDataAccess.LOCALE_CD.equals(pFieldName)) {
            return getLocaleCd();
        } else if (PreOrderDataAccess.CURRENCY_CD.equals(pFieldName)) {
            return getCurrencyCd();
        } else {
            return null;
        }

    }
    /**
    * Gets table name
    *
    * @return Table name
    */
    public String getTable() {
        return PreOrderDataAccess.CLW_PRE_ORDER;
    }

    
    /**
     * Sets the PreOrderId field. This field is required to be set in the database.
     *
     * @param pPreOrderId
     *  int to use to update the field.
     */
    public void setPreOrderId(int pPreOrderId){
        this.mPreOrderId = pPreOrderId;
        setDirty(true);
    }
    /**
     * Retrieves the PreOrderId field.
     *
     * @return
     *  int containing the PreOrderId field.
     */
    public int getPreOrderId(){
        return mPreOrderId;
    }

    /**
     * Sets the AccountId field.
     *
     * @param pAccountId
     *  int to use to update the field.
     */
    public void setAccountId(int pAccountId){
        this.mAccountId = pAccountId;
        setDirty(true);
    }
    /**
     * Retrieves the AccountId field.
     *
     * @return
     *  int containing the AccountId field.
     */
    public int getAccountId(){
        return mAccountId;
    }

    /**
     * Sets the SiteId field.
     *
     * @param pSiteId
     *  int to use to update the field.
     */
    public void setSiteId(int pSiteId){
        this.mSiteId = pSiteId;
        setDirty(true);
    }
    /**
     * Retrieves the SiteId field.
     *
     * @return
     *  int containing the SiteId field.
     */
    public int getSiteId(){
        return mSiteId;
    }

    /**
     * Sets the SiteName field.
     *
     * @param pSiteName
     *  String to use to update the field.
     */
    public void setSiteName(String pSiteName){
        this.mSiteName = pSiteName;
        setDirty(true);
    }
    /**
     * Retrieves the SiteName field.
     *
     * @return
     *  String containing the SiteName field.
     */
    public String getSiteName(){
        return mSiteName;
    }

    /**
     * Sets the ContractId field.
     *
     * @param pContractId
     *  int to use to update the field.
     */
    public void setContractId(int pContractId){
        this.mContractId = pContractId;
        setDirty(true);
    }
    /**
     * Retrieves the ContractId field.
     *
     * @return
     *  int containing the ContractId field.
     */
    public int getContractId(){
        return mContractId;
    }

    /**
     * Sets the IncomingProfileId field.
     *
     * @param pIncomingProfileId
     *  int to use to update the field.
     */
    public void setIncomingProfileId(int pIncomingProfileId){
        this.mIncomingProfileId = pIncomingProfileId;
        setDirty(true);
    }
    /**
     * Retrieves the IncomingProfileId field.
     *
     * @return
     *  int containing the IncomingProfileId field.
     */
    public int getIncomingProfileId(){
        return mIncomingProfileId;
    }

    /**
     * Sets the TradingPartnerId field.
     *
     * @param pTradingPartnerId
     *  int to use to update the field.
     */
    public void setTradingPartnerId(int pTradingPartnerId){
        this.mTradingPartnerId = pTradingPartnerId;
        setDirty(true);
    }
    /**
     * Retrieves the TradingPartnerId field.
     *
     * @return
     *  int containing the TradingPartnerId field.
     */
    public int getTradingPartnerId(){
        return mTradingPartnerId;
    }

    /**
     * Sets the CostCenterId field.
     *
     * @param pCostCenterId
     *  int to use to update the field.
     */
    public void setCostCenterId(int pCostCenterId){
        this.mCostCenterId = pCostCenterId;
        setDirty(true);
    }
    /**
     * Retrieves the CostCenterId field.
     *
     * @return
     *  int containing the CostCenterId field.
     */
    public int getCostCenterId(){
        return mCostCenterId;
    }

    /**
     * Sets the UserId field.
     *
     * @param pUserId
     *  int to use to update the field.
     */
    public void setUserId(int pUserId){
        this.mUserId = pUserId;
        setDirty(true);
    }
    /**
     * Retrieves the UserId field.
     *
     * @return
     *  int containing the UserId field.
     */
    public int getUserId(){
        return mUserId;
    }

    /**
     * Sets the UserName field.
     *
     * @param pUserName
     *  String to use to update the field.
     */
    public void setUserName(String pUserName){
        this.mUserName = pUserName;
        setDirty(true);
    }
    /**
     * Retrieves the UserName field.
     *
     * @return
     *  String containing the UserName field.
     */
    public String getUserName(){
        return mUserName;
    }

    /**
     * Sets the SkuTypeCd field.
     *
     * @param pSkuTypeCd
     *  String to use to update the field.
     */
    public void setSkuTypeCd(String pSkuTypeCd){
        this.mSkuTypeCd = pSkuTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the SkuTypeCd field.
     *
     * @return
     *  String containing the SkuTypeCd field.
     */
    public String getSkuTypeCd(){
        return mSkuTypeCd;
    }

    /**
     * Sets the BillingOrder field.
     *
     * @param pBillingOrder
     *  String to use to update the field.
     */
    public void setBillingOrder(String pBillingOrder){
        this.mBillingOrder = pBillingOrder;
        setDirty(true);
    }
    /**
     * Retrieves the BillingOrder field.
     *
     * @return
     *  String containing the BillingOrder field.
     */
    public String getBillingOrder(){
        return mBillingOrder;
    }

    /**
     * Sets the BypassOrderRouting field.
     *
     * @param pBypassOrderRouting
     *  String to use to update the field.
     */
    public void setBypassOrderRouting(String pBypassOrderRouting){
        this.mBypassOrderRouting = pBypassOrderRouting;
        setDirty(true);
    }
    /**
     * Retrieves the BypassOrderRouting field.
     *
     * @return
     *  String containing the BypassOrderRouting field.
     */
    public String getBypassOrderRouting(){
        return mBypassOrderRouting;
    }

    /**
     * Sets the BypassPreCapturePipeline field.
     *
     * @param pBypassPreCapturePipeline
     *  String to use to update the field.
     */
    public void setBypassPreCapturePipeline(String pBypassPreCapturePipeline){
        this.mBypassPreCapturePipeline = pBypassPreCapturePipeline;
        setDirty(true);
    }
    /**
     * Retrieves the BypassPreCapturePipeline field.
     *
     * @return
     *  String containing the BypassPreCapturePipeline field.
     */
    public String getBypassPreCapturePipeline(){
        return mBypassPreCapturePipeline;
    }

    /**
     * Sets the FreeFormatAddress field.
     *
     * @param pFreeFormatAddress
     *  String to use to update the field.
     */
    public void setFreeFormatAddress(String pFreeFormatAddress){
        this.mFreeFormatAddress = pFreeFormatAddress;
        setDirty(true);
    }
    /**
     * Retrieves the FreeFormatAddress field.
     *
     * @return
     *  String containing the FreeFormatAddress field.
     */
    public String getFreeFormatAddress(){
        return mFreeFormatAddress;
    }

    /**
     * Sets the CustomerComments field.
     *
     * @param pCustomerComments
     *  String to use to update the field.
     */
    public void setCustomerComments(String pCustomerComments){
        this.mCustomerComments = pCustomerComments;
        setDirty(true);
    }
    /**
     * Retrieves the CustomerComments field.
     *
     * @return
     *  String containing the CustomerComments field.
     */
    public String getCustomerComments(){
        return mCustomerComments;
    }

    /**
     * Sets the CustomerOrderDate field.
     *
     * @param pCustomerOrderDate
     *  String to use to update the field.
     */
    public void setCustomerOrderDate(String pCustomerOrderDate){
        this.mCustomerOrderDate = pCustomerOrderDate;
        setDirty(true);
    }
    /**
     * Retrieves the CustomerOrderDate field.
     *
     * @return
     *  String containing the CustomerOrderDate field.
     */
    public String getCustomerOrderDate(){
        return mCustomerOrderDate;
    }

    /**
     * Sets the CustomerPoNumber field.
     *
     * @param pCustomerPoNumber
     *  String to use to update the field.
     */
    public void setCustomerPoNumber(String pCustomerPoNumber){
        this.mCustomerPoNumber = pCustomerPoNumber;
        setDirty(true);
    }
    /**
     * Retrieves the CustomerPoNumber field.
     *
     * @return
     *  String containing the CustomerPoNumber field.
     */
    public String getCustomerPoNumber(){
        return mCustomerPoNumber;
    }

    /**
     * Sets the FreightCharge field.
     *
     * @param pFreightCharge
     *  java.math.BigDecimal to use to update the field.
     */
    public void setFreightCharge(java.math.BigDecimal pFreightCharge){
        this.mFreightCharge = pFreightCharge;
        setDirty(true);
    }
    /**
     * Retrieves the FreightCharge field.
     *
     * @return
     *  java.math.BigDecimal containing the FreightCharge field.
     */
    public java.math.BigDecimal getFreightCharge(){
        return mFreightCharge;
    }

    /**
     * Sets the HandlingCharge field.
     *
     * @param pHandlingCharge
     *  java.math.BigDecimal to use to update the field.
     */
    public void setHandlingCharge(java.math.BigDecimal pHandlingCharge){
        this.mHandlingCharge = pHandlingCharge;
        setDirty(true);
    }
    /**
     * Retrieves the HandlingCharge field.
     *
     * @return
     *  java.math.BigDecimal containing the HandlingCharge field.
     */
    public java.math.BigDecimal getHandlingCharge(){
        return mHandlingCharge;
    }

    /**
     * Sets the HoldUntilDate field.
     *
     * @param pHoldUntilDate
     *  Date to use to update the field.
     */
    public void setHoldUntilDate(Date pHoldUntilDate){
        this.mHoldUntilDate = pHoldUntilDate;
        setDirty(true);
    }
    /**
     * Retrieves the HoldUntilDate field.
     *
     * @return
     *  Date containing the HoldUntilDate field.
     */
    public Date getHoldUntilDate(){
        return mHoldUntilDate;
    }

    /**
     * Sets the OrderContactName field.
     *
     * @param pOrderContactName
     *  String to use to update the field.
     */
    public void setOrderContactName(String pOrderContactName){
        this.mOrderContactName = pOrderContactName;
        setDirty(true);
    }
    /**
     * Retrieves the OrderContactName field.
     *
     * @return
     *  String containing the OrderContactName field.
     */
    public String getOrderContactName(){
        return mOrderContactName;
    }

    /**
     * Sets the OrderEmail field.
     *
     * @param pOrderEmail
     *  String to use to update the field.
     */
    public void setOrderEmail(String pOrderEmail){
        this.mOrderEmail = pOrderEmail;
        setDirty(true);
    }
    /**
     * Retrieves the OrderEmail field.
     *
     * @return
     *  String containing the OrderEmail field.
     */
    public String getOrderEmail(){
        return mOrderEmail;
    }

    /**
     * Sets the OrderFaxNumber field.
     *
     * @param pOrderFaxNumber
     *  String to use to update the field.
     */
    public void setOrderFaxNumber(String pOrderFaxNumber){
        this.mOrderFaxNumber = pOrderFaxNumber;
        setDirty(true);
    }
    /**
     * Retrieves the OrderFaxNumber field.
     *
     * @return
     *  String containing the OrderFaxNumber field.
     */
    public String getOrderFaxNumber(){
        return mOrderFaxNumber;
    }

    /**
     * Sets the OrderNote field.
     *
     * @param pOrderNote
     *  String to use to update the field.
     */
    public void setOrderNote(String pOrderNote){
        this.mOrderNote = pOrderNote;
        setDirty(true);
    }
    /**
     * Retrieves the OrderNote field.
     *
     * @return
     *  String containing the OrderNote field.
     */
    public String getOrderNote(){
        return mOrderNote;
    }

    /**
     * Sets the OrderRefNumber field.
     *
     * @param pOrderRefNumber
     *  String to use to update the field.
     */
    public void setOrderRefNumber(String pOrderRefNumber){
        this.mOrderRefNumber = pOrderRefNumber;
        setDirty(true);
    }
    /**
     * Retrieves the OrderRefNumber field.
     *
     * @return
     *  String containing the OrderRefNumber field.
     */
    public String getOrderRefNumber(){
        return mOrderRefNumber;
    }

    /**
     * Sets the OrderRequestedShipDate field.
     *
     * @param pOrderRequestedShipDate
     *  Date to use to update the field.
     */
    public void setOrderRequestedShipDate(Date pOrderRequestedShipDate){
        this.mOrderRequestedShipDate = pOrderRequestedShipDate;
        setDirty(true);
    }
    /**
     * Retrieves the OrderRequestedShipDate field.
     *
     * @return
     *  Date containing the OrderRequestedShipDate field.
     */
    public Date getOrderRequestedShipDate(){
        return mOrderRequestedShipDate;
    }

    /**
     * Sets the OrderSourceCd field.
     *
     * @param pOrderSourceCd
     *  String to use to update the field.
     */
    public void setOrderSourceCd(String pOrderSourceCd){
        this.mOrderSourceCd = pOrderSourceCd;
        setDirty(true);
    }
    /**
     * Retrieves the OrderSourceCd field.
     *
     * @return
     *  String containing the OrderSourceCd field.
     */
    public String getOrderSourceCd(){
        return mOrderSourceCd;
    }

    /**
     * Sets the OrderTelephoneNumber field.
     *
     * @param pOrderTelephoneNumber
     *  String to use to update the field.
     */
    public void setOrderTelephoneNumber(String pOrderTelephoneNumber){
        this.mOrderTelephoneNumber = pOrderTelephoneNumber;
        setDirty(true);
    }
    /**
     * Retrieves the OrderTelephoneNumber field.
     *
     * @return
     *  String containing the OrderTelephoneNumber field.
     */
    public String getOrderTelephoneNumber(){
        return mOrderTelephoneNumber;
    }

    /**
     * Sets the PaymentType field.
     *
     * @param pPaymentType
     *  String to use to update the field.
     */
    public void setPaymentType(String pPaymentType){
        this.mPaymentType = pPaymentType;
        setDirty(true);
    }
    /**
     * Retrieves the PaymentType field.
     *
     * @return
     *  String containing the PaymentType field.
     */
    public String getPaymentType(){
        return mPaymentType;
    }

    /**
     * Sets the OrderStatusCdOveride field.
     *
     * @param pOrderStatusCdOveride
     *  String to use to update the field.
     */
    public void setOrderStatusCdOveride(String pOrderStatusCdOveride){
        this.mOrderStatusCdOveride = pOrderStatusCdOveride;
        setDirty(true);
    }
    /**
     * Retrieves the OrderStatusCdOveride field.
     *
     * @return
     *  String containing the OrderStatusCdOveride field.
     */
    public String getOrderStatusCdOveride(){
        return mOrderStatusCdOveride;
    }

    /**
     * Sets the AccountErpNumberOveride field.
     *
     * @param pAccountErpNumberOveride
     *  String to use to update the field.
     */
    public void setAccountErpNumberOveride(String pAccountErpNumberOveride){
        this.mAccountErpNumberOveride = pAccountErpNumberOveride;
        setDirty(true);
    }
    /**
     * Retrieves the AccountErpNumberOveride field.
     *
     * @return
     *  String containing the AccountErpNumberOveride field.
     */
    public String getAccountErpNumberOveride(){
        return mAccountErpNumberOveride;
    }

    /**
     * Sets the CustomerBillingUnit field.
     *
     * @param pCustomerBillingUnit
     *  String to use to update the field.
     */
    public void setCustomerBillingUnit(String pCustomerBillingUnit){
        this.mCustomerBillingUnit = pCustomerBillingUnit;
        setDirty(true);
    }
    /**
     * Retrieves the CustomerBillingUnit field.
     *
     * @return
     *  String containing the CustomerBillingUnit field.
     */
    public String getCustomerBillingUnit(){
        return mCustomerBillingUnit;
    }

    /**
     * Sets the AddDate field. This field is required to be set in the database.
     *
     * @param pAddDate
     *  Date to use to update the field.
     */
    public void setAddDate(Date pAddDate){
        this.mAddDate = pAddDate;
        setDirty(true);
    }
    /**
     * Retrieves the AddDate field.
     *
     * @return
     *  Date containing the AddDate field.
     */
    public Date getAddDate(){
        return mAddDate;
    }

    /**
     * Sets the AddBy field.
     *
     * @param pAddBy
     *  String to use to update the field.
     */
    public void setAddBy(String pAddBy){
        this.mAddBy = pAddBy;
        setDirty(true);
    }
    /**
     * Retrieves the AddBy field.
     *
     * @return
     *  String containing the AddBy field.
     */
    public String getAddBy(){
        return mAddBy;
    }

    /**
     * Sets the ModDate field. This field is required to be set in the database.
     *
     * @param pModDate
     *  Date to use to update the field.
     */
    public void setModDate(Date pModDate){
        this.mModDate = pModDate;
        setDirty(true);
    }
    /**
     * Retrieves the ModDate field.
     *
     * @return
     *  Date containing the ModDate field.
     */
    public Date getModDate(){
        return mModDate;
    }

    /**
     * Sets the ModBy field.
     *
     * @param pModBy
     *  String to use to update the field.
     */
    public void setModBy(String pModBy){
        this.mModBy = pModBy;
        setDirty(true);
    }
    /**
     * Retrieves the ModBy field.
     *
     * @return
     *  String containing the ModBy field.
     */
    public String getModBy(){
        return mModBy;
    }

    /**
     * Sets the RefOrderId field.
     *
     * @param pRefOrderId
     *  int to use to update the field.
     */
    public void setRefOrderId(int pRefOrderId){
        this.mRefOrderId = pRefOrderId;
        setDirty(true);
    }
    /**
     * Retrieves the RefOrderId field.
     *
     * @return
     *  int containing the RefOrderId field.
     */
    public int getRefOrderId(){
        return mRefOrderId;
    }

    /**
     * Sets the OrderTypeCd field.
     *
     * @param pOrderTypeCd
     *  String to use to update the field.
     */
    public void setOrderTypeCd(String pOrderTypeCd){
        this.mOrderTypeCd = pOrderTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the OrderTypeCd field.
     *
     * @return
     *  String containing the OrderTypeCd field.
     */
    public String getOrderTypeCd(){
        return mOrderTypeCd;
    }

    /**
     * Sets the WorkflowInd field.
     *
     * @param pWorkflowInd
     *  String to use to update the field.
     */
    public void setWorkflowInd(String pWorkflowInd){
        this.mWorkflowInd = pWorkflowInd;
        setDirty(true);
    }
    /**
     * Retrieves the WorkflowInd field.
     *
     * @return
     *  String containing the WorkflowInd field.
     */
    public String getWorkflowInd(){
        return mWorkflowInd;
    }

    /**
     * Sets the UserNameKey field.
     *
     * @param pUserNameKey
     *  String to use to update the field.
     */
    public void setUserNameKey(String pUserNameKey){
        this.mUserNameKey = pUserNameKey;
        setDirty(true);
    }
    /**
     * Retrieves the UserNameKey field.
     *
     * @return
     *  String containing the UserNameKey field.
     */
    public String getUserNameKey(){
        return mUserNameKey;
    }

    /**
     * Sets the RushCharge field.
     *
     * @param pRushCharge
     *  java.math.BigDecimal to use to update the field.
     */
    public void setRushCharge(java.math.BigDecimal pRushCharge){
        this.mRushCharge = pRushCharge;
        setDirty(true);
    }
    /**
     * Retrieves the RushCharge field.
     *
     * @return
     *  java.math.BigDecimal containing the RushCharge field.
     */
    public java.math.BigDecimal getRushCharge(){
        return mRushCharge;
    }

    /**
     * Sets the OrderBudgetTypeCd field.
     *
     * @param pOrderBudgetTypeCd
     *  String to use to update the field.
     */
    public void setOrderBudgetTypeCd(String pOrderBudgetTypeCd){
        this.mOrderBudgetTypeCd = pOrderBudgetTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the OrderBudgetTypeCd field.
     *
     * @return
     *  String containing the OrderBudgetTypeCd field.
     */
    public String getOrderBudgetTypeCd(){
        return mOrderBudgetTypeCd;
    }

    /**
     * Sets the LocaleCd field.
     *
     * @param pLocaleCd
     *  String to use to update the field.
     */
    public void setLocaleCd(String pLocaleCd){
        this.mLocaleCd = pLocaleCd;
        setDirty(true);
    }
    /**
     * Retrieves the LocaleCd field.
     *
     * @return
     *  String containing the LocaleCd field.
     */
    public String getLocaleCd(){
        return mLocaleCd;
    }

    /**
     * Sets the CurrencyCd field.
     *
     * @param pCurrencyCd
     *  String to use to update the field.
     */
    public void setCurrencyCd(String pCurrencyCd){
        this.mCurrencyCd = pCurrencyCd;
        setDirty(true);
    }
    /**
     * Retrieves the CurrencyCd field.
     *
     * @return
     *  String containing the CurrencyCd field.
     */
    public String getCurrencyCd(){
        return mCurrencyCd;
    }


}
