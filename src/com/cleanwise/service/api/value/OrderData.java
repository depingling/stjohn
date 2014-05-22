
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        OrderData
 * Description:  This is a ValueObject class wrapping the database table CLW_ORDER.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.OrderDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>OrderData</code> is a ValueObject class wrapping of the database table CLW_ORDER.
 */
public class OrderData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -88341481870472213L;
    private int mOrderId;// SQL type:NUMBER, not null
    private String mExceptionInd;// SQL type:VARCHAR2
    private String mOrderNum;// SQL type:VARCHAR2
    private String mRefOrderNum;// SQL type:VARCHAR2
    private int mCostCenterId;// SQL type:NUMBER
    private String mCostCenterName;// SQL type:VARCHAR2
    private String mWorkflowInd;// SQL type:VARCHAR2
    private String mWorkflowStatusCd;// SQL type:VARCHAR2
    private String mAccountErpNum;// SQL type:VARCHAR2
    private String mSiteErpNum;// SQL type:VARCHAR2
    private String mRequestPoNum;// SQL type:VARCHAR2
    private int mUserId;// SQL type:NUMBER
    private String mUserFirstName;// SQL type:VARCHAR2
    private String mUserLastName;// SQL type:VARCHAR2
    private String mOrderSiteName;// SQL type:VARCHAR2
    private String mOrderContactName;// SQL type:VARCHAR2
    private String mOrderContactPhoneNum;// SQL type:VARCHAR2
    private String mOrderContactEmail;// SQL type:VARCHAR2
    private String mOrderContactFaxNum;// SQL type:VARCHAR2
    private int mContractId;// SQL type:NUMBER
    private String mContractShortDesc;// SQL type:VARCHAR2
    private String mOrderTypeCd;// SQL type:VARCHAR2
    private String mOrderSourceCd;// SQL type:VARCHAR2
    private String mOrderStatusCd;// SQL type:VARCHAR2
    private String mTaxNum;// SQL type:VARCHAR2
    private java.math.BigDecimal mOriginalAmount;// SQL type:NUMBER
    private java.math.BigDecimal mTotalPrice;// SQL type:NUMBER
    private java.math.BigDecimal mTotalFreightCost;// SQL type:NUMBER
    private java.math.BigDecimal mTotalMiscCost;// SQL type:NUMBER
    private java.math.BigDecimal mTotalTaxCost;// SQL type:NUMBER
    private java.math.BigDecimal mTotalCleanwiseCost;// SQL type:NUMBER
    private java.math.BigDecimal mGrossWeight;// SQL type:NUMBER
    private Date mOriginalOrderDate;// SQL type:DATE
    private Date mOriginalOrderTime;// SQL type:DATE
    private Date mRevisedOrderDate;// SQL type:DATE
    private Date mRevisedOrderTime;// SQL type:DATE
    private String mComments;// SQL type:VARCHAR2
    private int mIncomingTradingProfileId;// SQL type:NUMBER
    private String mLocaleCd;// SQL type:VARCHAR2
    private String mCurrencyCd;// SQL type:VARCHAR2
    private int mErpOrderNum;// SQL type:NUMBER
    private Date mAddDate;// SQL type:DATE
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE
    private String mModBy;// SQL type:VARCHAR2
    private int mSiteId;// SQL type:NUMBER
    private int mAccountId;// SQL type:NUMBER
    private int mStoreId;// SQL type:NUMBER
    private String mErpSystemCd;// SQL type:VARCHAR2
    private int mPreOrderId;// SQL type:NUMBER
    private int mRefOrderId;// SQL type:NUMBER
    private java.math.BigDecimal mTotalRushCharge;// SQL type:NUMBER
    private String mOrderBudgetTypeCd;// SQL type:VARCHAR2
    private Date mErpOrderDate;// SQL type:DATE

    /**
     * Constructor.
     */
    public OrderData ()
    {
        mExceptionInd = "";
        mOrderNum = "";
        mRefOrderNum = "";
        mCostCenterName = "";
        mWorkflowInd = "";
        mWorkflowStatusCd = "";
        mAccountErpNum = "";
        mSiteErpNum = "";
        mRequestPoNum = "";
        mUserFirstName = "";
        mUserLastName = "";
        mOrderSiteName = "";
        mOrderContactName = "";
        mOrderContactPhoneNum = "";
        mOrderContactEmail = "";
        mOrderContactFaxNum = "";
        mContractShortDesc = "";
        mOrderTypeCd = "";
        mOrderSourceCd = "";
        mOrderStatusCd = "";
        mTaxNum = "";
        mComments = "";
        mLocaleCd = "";
        mCurrencyCd = "";
        mAddBy = "";
        mModBy = "";
        mErpSystemCd = "";
        mOrderBudgetTypeCd = "";
    }

    /**
     * Constructor.
     */
    public OrderData(int parm1, String parm2, String parm3, String parm4, int parm5, String parm6, String parm7, String parm8, String parm9, String parm10, String parm11, int parm12, String parm13, String parm14, String parm15, String parm16, String parm17, String parm18, String parm19, int parm20, String parm21, String parm22, String parm23, String parm24, String parm25, java.math.BigDecimal parm26, java.math.BigDecimal parm27, java.math.BigDecimal parm28, java.math.BigDecimal parm29, java.math.BigDecimal parm30, java.math.BigDecimal parm31, java.math.BigDecimal parm32, Date parm33, Date parm34, Date parm35, Date parm36, String parm37, int parm38, String parm39, String parm40, int parm41, Date parm42, String parm43, Date parm44, String parm45, int parm46, int parm47, int parm48, String parm49, int parm50, int parm51, java.math.BigDecimal parm52, String parm53, Date parm54)
    {
        mOrderId = parm1;
        mExceptionInd = parm2;
        mOrderNum = parm3;
        mRefOrderNum = parm4;
        mCostCenterId = parm5;
        mCostCenterName = parm6;
        mWorkflowInd = parm7;
        mWorkflowStatusCd = parm8;
        mAccountErpNum = parm9;
        mSiteErpNum = parm10;
        mRequestPoNum = parm11;
        mUserId = parm12;
        mUserFirstName = parm13;
        mUserLastName = parm14;
        mOrderSiteName = parm15;
        mOrderContactName = parm16;
        mOrderContactPhoneNum = parm17;
        mOrderContactEmail = parm18;
        mOrderContactFaxNum = parm19;
        mContractId = parm20;
        mContractShortDesc = parm21;
        mOrderTypeCd = parm22;
        mOrderSourceCd = parm23;
        mOrderStatusCd = parm24;
        mTaxNum = parm25;
        mOriginalAmount = parm26;
        mTotalPrice = parm27;
        mTotalFreightCost = parm28;
        mTotalMiscCost = parm29;
        mTotalTaxCost = parm30;
        mTotalCleanwiseCost = parm31;
        mGrossWeight = parm32;
        mOriginalOrderDate = parm33;
        mOriginalOrderTime = parm34;
        mRevisedOrderDate = parm35;
        mRevisedOrderTime = parm36;
        mComments = parm37;
        mIncomingTradingProfileId = parm38;
        mLocaleCd = parm39;
        mCurrencyCd = parm40;
        mErpOrderNum = parm41;
        mAddDate = parm42;
        mAddBy = parm43;
        mModDate = parm44;
        mModBy = parm45;
        mSiteId = parm46;
        mAccountId = parm47;
        mStoreId = parm48;
        mErpSystemCd = parm49;
        mPreOrderId = parm50;
        mRefOrderId = parm51;
        mTotalRushCharge = parm52;
        mOrderBudgetTypeCd = parm53;
        mErpOrderDate = parm54;
        
    }

    /**
     * Creates a new OrderData
     *
     * @return
     *  Newly initialized OrderData object.
     */
    public static OrderData createValue ()
    {
        OrderData valueData = new OrderData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this OrderData object
     */
    public String toString()
    {
        return "[" + "OrderId=" + mOrderId + ", ExceptionInd=" + mExceptionInd + ", OrderNum=" + mOrderNum + ", RefOrderNum=" + mRefOrderNum + ", CostCenterId=" + mCostCenterId + ", CostCenterName=" + mCostCenterName + ", WorkflowInd=" + mWorkflowInd + ", WorkflowStatusCd=" + mWorkflowStatusCd + ", AccountErpNum=" + mAccountErpNum + ", SiteErpNum=" + mSiteErpNum + ", RequestPoNum=" + mRequestPoNum + ", UserId=" + mUserId + ", UserFirstName=" + mUserFirstName + ", UserLastName=" + mUserLastName + ", OrderSiteName=" + mOrderSiteName + ", OrderContactName=" + mOrderContactName + ", OrderContactPhoneNum=" + mOrderContactPhoneNum + ", OrderContactEmail=" + mOrderContactEmail + ", OrderContactFaxNum=" + mOrderContactFaxNum + ", ContractId=" + mContractId + ", ContractShortDesc=" + mContractShortDesc + ", OrderTypeCd=" + mOrderTypeCd + ", OrderSourceCd=" + mOrderSourceCd + ", OrderStatusCd=" + mOrderStatusCd + ", TaxNum=" + mTaxNum + ", OriginalAmount=" + mOriginalAmount + ", TotalPrice=" + mTotalPrice + ", TotalFreightCost=" + mTotalFreightCost + ", TotalMiscCost=" + mTotalMiscCost + ", TotalTaxCost=" + mTotalTaxCost + ", TotalCleanwiseCost=" + mTotalCleanwiseCost + ", GrossWeight=" + mGrossWeight + ", OriginalOrderDate=" + mOriginalOrderDate + ", OriginalOrderTime=" + mOriginalOrderTime + ", RevisedOrderDate=" + mRevisedOrderDate + ", RevisedOrderTime=" + mRevisedOrderTime + ", Comments=" + mComments + ", IncomingTradingProfileId=" + mIncomingTradingProfileId + ", LocaleCd=" + mLocaleCd + ", CurrencyCd=" + mCurrencyCd + ", ErpOrderNum=" + mErpOrderNum + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", SiteId=" + mSiteId + ", AccountId=" + mAccountId + ", StoreId=" + mStoreId + ", ErpSystemCd=" + mErpSystemCd + ", PreOrderId=" + mPreOrderId + ", RefOrderId=" + mRefOrderId + ", TotalRushCharge=" + mTotalRushCharge + ", OrderBudgetTypeCd=" + mOrderBudgetTypeCd + ", ErpOrderDate=" + mErpOrderDate + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("Order");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mOrderId));

        node =  doc.createElement("ExceptionInd");
        node.appendChild(doc.createTextNode(String.valueOf(mExceptionInd)));
        root.appendChild(node);

        node =  doc.createElement("OrderNum");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderNum)));
        root.appendChild(node);

        node =  doc.createElement("RefOrderNum");
        node.appendChild(doc.createTextNode(String.valueOf(mRefOrderNum)));
        root.appendChild(node);

        node =  doc.createElement("CostCenterId");
        node.appendChild(doc.createTextNode(String.valueOf(mCostCenterId)));
        root.appendChild(node);

        node =  doc.createElement("CostCenterName");
        node.appendChild(doc.createTextNode(String.valueOf(mCostCenterName)));
        root.appendChild(node);

        node =  doc.createElement("WorkflowInd");
        node.appendChild(doc.createTextNode(String.valueOf(mWorkflowInd)));
        root.appendChild(node);

        node =  doc.createElement("WorkflowStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mWorkflowStatusCd)));
        root.appendChild(node);

        node =  doc.createElement("AccountErpNum");
        node.appendChild(doc.createTextNode(String.valueOf(mAccountErpNum)));
        root.appendChild(node);

        node =  doc.createElement("SiteErpNum");
        node.appendChild(doc.createTextNode(String.valueOf(mSiteErpNum)));
        root.appendChild(node);

        node =  doc.createElement("RequestPoNum");
        node.appendChild(doc.createTextNode(String.valueOf(mRequestPoNum)));
        root.appendChild(node);

        node =  doc.createElement("UserId");
        node.appendChild(doc.createTextNode(String.valueOf(mUserId)));
        root.appendChild(node);

        node =  doc.createElement("UserFirstName");
        node.appendChild(doc.createTextNode(String.valueOf(mUserFirstName)));
        root.appendChild(node);

        node =  doc.createElement("UserLastName");
        node.appendChild(doc.createTextNode(String.valueOf(mUserLastName)));
        root.appendChild(node);

        node =  doc.createElement("OrderSiteName");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderSiteName)));
        root.appendChild(node);

        node =  doc.createElement("OrderContactName");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderContactName)));
        root.appendChild(node);

        node =  doc.createElement("OrderContactPhoneNum");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderContactPhoneNum)));
        root.appendChild(node);

        node =  doc.createElement("OrderContactEmail");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderContactEmail)));
        root.appendChild(node);

        node =  doc.createElement("OrderContactFaxNum");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderContactFaxNum)));
        root.appendChild(node);

        node =  doc.createElement("ContractId");
        node.appendChild(doc.createTextNode(String.valueOf(mContractId)));
        root.appendChild(node);

        node =  doc.createElement("ContractShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mContractShortDesc)));
        root.appendChild(node);

        node =  doc.createElement("OrderTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderTypeCd)));
        root.appendChild(node);

        node =  doc.createElement("OrderSourceCd");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderSourceCd)));
        root.appendChild(node);

        node =  doc.createElement("OrderStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderStatusCd)));
        root.appendChild(node);

        node =  doc.createElement("TaxNum");
        node.appendChild(doc.createTextNode(String.valueOf(mTaxNum)));
        root.appendChild(node);

        node =  doc.createElement("OriginalAmount");
        node.appendChild(doc.createTextNode(String.valueOf(mOriginalAmount)));
        root.appendChild(node);

        node =  doc.createElement("TotalPrice");
        node.appendChild(doc.createTextNode(String.valueOf(mTotalPrice)));
        root.appendChild(node);

        node =  doc.createElement("TotalFreightCost");
        node.appendChild(doc.createTextNode(String.valueOf(mTotalFreightCost)));
        root.appendChild(node);

        node =  doc.createElement("TotalMiscCost");
        node.appendChild(doc.createTextNode(String.valueOf(mTotalMiscCost)));
        root.appendChild(node);

        node =  doc.createElement("TotalTaxCost");
        node.appendChild(doc.createTextNode(String.valueOf(mTotalTaxCost)));
        root.appendChild(node);

        node =  doc.createElement("TotalCleanwiseCost");
        node.appendChild(doc.createTextNode(String.valueOf(mTotalCleanwiseCost)));
        root.appendChild(node);

        node =  doc.createElement("GrossWeight");
        node.appendChild(doc.createTextNode(String.valueOf(mGrossWeight)));
        root.appendChild(node);

        node =  doc.createElement("OriginalOrderDate");
        node.appendChild(doc.createTextNode(String.valueOf(mOriginalOrderDate)));
        root.appendChild(node);

        node =  doc.createElement("OriginalOrderTime");
        node.appendChild(doc.createTextNode(String.valueOf(mOriginalOrderTime)));
        root.appendChild(node);

        node =  doc.createElement("RevisedOrderDate");
        node.appendChild(doc.createTextNode(String.valueOf(mRevisedOrderDate)));
        root.appendChild(node);

        node =  doc.createElement("RevisedOrderTime");
        node.appendChild(doc.createTextNode(String.valueOf(mRevisedOrderTime)));
        root.appendChild(node);

        node =  doc.createElement("Comments");
        node.appendChild(doc.createTextNode(String.valueOf(mComments)));
        root.appendChild(node);

        node =  doc.createElement("IncomingTradingProfileId");
        node.appendChild(doc.createTextNode(String.valueOf(mIncomingTradingProfileId)));
        root.appendChild(node);

        node =  doc.createElement("LocaleCd");
        node.appendChild(doc.createTextNode(String.valueOf(mLocaleCd)));
        root.appendChild(node);

        node =  doc.createElement("CurrencyCd");
        node.appendChild(doc.createTextNode(String.valueOf(mCurrencyCd)));
        root.appendChild(node);

        node =  doc.createElement("ErpOrderNum");
        node.appendChild(doc.createTextNode(String.valueOf(mErpOrderNum)));
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

        node =  doc.createElement("SiteId");
        node.appendChild(doc.createTextNode(String.valueOf(mSiteId)));
        root.appendChild(node);

        node =  doc.createElement("AccountId");
        node.appendChild(doc.createTextNode(String.valueOf(mAccountId)));
        root.appendChild(node);

        node =  doc.createElement("StoreId");
        node.appendChild(doc.createTextNode(String.valueOf(mStoreId)));
        root.appendChild(node);

        node =  doc.createElement("ErpSystemCd");
        node.appendChild(doc.createTextNode(String.valueOf(mErpSystemCd)));
        root.appendChild(node);

        node =  doc.createElement("PreOrderId");
        node.appendChild(doc.createTextNode(String.valueOf(mPreOrderId)));
        root.appendChild(node);

        node =  doc.createElement("RefOrderId");
        node.appendChild(doc.createTextNode(String.valueOf(mRefOrderId)));
        root.appendChild(node);

        node =  doc.createElement("TotalRushCharge");
        node.appendChild(doc.createTextNode(String.valueOf(mTotalRushCharge)));
        root.appendChild(node);

        node =  doc.createElement("OrderBudgetTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderBudgetTypeCd)));
        root.appendChild(node);

        node =  doc.createElement("ErpOrderDate");
        node.appendChild(doc.createTextNode(String.valueOf(mErpOrderDate)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the OrderId field is not cloned.
    *
    * @return OrderData object
    */
    public Object clone(){
        OrderData myClone = new OrderData();
        
        myClone.mExceptionInd = mExceptionInd;
        
        myClone.mOrderNum = mOrderNum;
        
        myClone.mRefOrderNum = mRefOrderNum;
        
        myClone.mCostCenterId = mCostCenterId;
        
        myClone.mCostCenterName = mCostCenterName;
        
        myClone.mWorkflowInd = mWorkflowInd;
        
        myClone.mWorkflowStatusCd = mWorkflowStatusCd;
        
        myClone.mAccountErpNum = mAccountErpNum;
        
        myClone.mSiteErpNum = mSiteErpNum;
        
        myClone.mRequestPoNum = mRequestPoNum;
        
        myClone.mUserId = mUserId;
        
        myClone.mUserFirstName = mUserFirstName;
        
        myClone.mUserLastName = mUserLastName;
        
        myClone.mOrderSiteName = mOrderSiteName;
        
        myClone.mOrderContactName = mOrderContactName;
        
        myClone.mOrderContactPhoneNum = mOrderContactPhoneNum;
        
        myClone.mOrderContactEmail = mOrderContactEmail;
        
        myClone.mOrderContactFaxNum = mOrderContactFaxNum;
        
        myClone.mContractId = mContractId;
        
        myClone.mContractShortDesc = mContractShortDesc;
        
        myClone.mOrderTypeCd = mOrderTypeCd;
        
        myClone.mOrderSourceCd = mOrderSourceCd;
        
        myClone.mOrderStatusCd = mOrderStatusCd;
        
        myClone.mTaxNum = mTaxNum;
        
        myClone.mOriginalAmount = mOriginalAmount;
        
        myClone.mTotalPrice = mTotalPrice;
        
        myClone.mTotalFreightCost = mTotalFreightCost;
        
        myClone.mTotalMiscCost = mTotalMiscCost;
        
        myClone.mTotalTaxCost = mTotalTaxCost;
        
        myClone.mTotalCleanwiseCost = mTotalCleanwiseCost;
        
        myClone.mGrossWeight = mGrossWeight;
        
        if(mOriginalOrderDate != null){
                myClone.mOriginalOrderDate = (Date) mOriginalOrderDate.clone();
        }
        
        if(mOriginalOrderTime != null){
                myClone.mOriginalOrderTime = (Date) mOriginalOrderTime.clone();
        }
        
        if(mRevisedOrderDate != null){
                myClone.mRevisedOrderDate = (Date) mRevisedOrderDate.clone();
        }
        
        if(mRevisedOrderTime != null){
                myClone.mRevisedOrderTime = (Date) mRevisedOrderTime.clone();
        }
        
        myClone.mComments = mComments;
        
        myClone.mIncomingTradingProfileId = mIncomingTradingProfileId;
        
        myClone.mLocaleCd = mLocaleCd;
        
        myClone.mCurrencyCd = mCurrencyCd;
        
        myClone.mErpOrderNum = mErpOrderNum;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        myClone.mSiteId = mSiteId;
        
        myClone.mAccountId = mAccountId;
        
        myClone.mStoreId = mStoreId;
        
        myClone.mErpSystemCd = mErpSystemCd;
        
        myClone.mPreOrderId = mPreOrderId;
        
        myClone.mRefOrderId = mRefOrderId;
        
        myClone.mTotalRushCharge = mTotalRushCharge;
        
        myClone.mOrderBudgetTypeCd = mOrderBudgetTypeCd;
        
        if(mErpOrderDate != null){
                myClone.mErpOrderDate = (Date) mErpOrderDate.clone();
        }
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (OrderDataAccess.ORDER_ID.equals(pFieldName)) {
            return getOrderId();
        } else if (OrderDataAccess.EXCEPTION_IND.equals(pFieldName)) {
            return getExceptionInd();
        } else if (OrderDataAccess.ORDER_NUM.equals(pFieldName)) {
            return getOrderNum();
        } else if (OrderDataAccess.REF_ORDER_NUM.equals(pFieldName)) {
            return getRefOrderNum();
        } else if (OrderDataAccess.COST_CENTER_ID.equals(pFieldName)) {
            return getCostCenterId();
        } else if (OrderDataAccess.COST_CENTER_NAME.equals(pFieldName)) {
            return getCostCenterName();
        } else if (OrderDataAccess.WORKFLOW_IND.equals(pFieldName)) {
            return getWorkflowInd();
        } else if (OrderDataAccess.WORKFLOW_STATUS_CD.equals(pFieldName)) {
            return getWorkflowStatusCd();
        } else if (OrderDataAccess.ACCOUNT_ERP_NUM.equals(pFieldName)) {
            return getAccountErpNum();
        } else if (OrderDataAccess.SITE_ERP_NUM.equals(pFieldName)) {
            return getSiteErpNum();
        } else if (OrderDataAccess.REQUEST_PO_NUM.equals(pFieldName)) {
            return getRequestPoNum();
        } else if (OrderDataAccess.USER_ID.equals(pFieldName)) {
            return getUserId();
        } else if (OrderDataAccess.USER_FIRST_NAME.equals(pFieldName)) {
            return getUserFirstName();
        } else if (OrderDataAccess.USER_LAST_NAME.equals(pFieldName)) {
            return getUserLastName();
        } else if (OrderDataAccess.ORDER_SITE_NAME.equals(pFieldName)) {
            return getOrderSiteName();
        } else if (OrderDataAccess.ORDER_CONTACT_NAME.equals(pFieldName)) {
            return getOrderContactName();
        } else if (OrderDataAccess.ORDER_CONTACT_PHONE_NUM.equals(pFieldName)) {
            return getOrderContactPhoneNum();
        } else if (OrderDataAccess.ORDER_CONTACT_EMAIL.equals(pFieldName)) {
            return getOrderContactEmail();
        } else if (OrderDataAccess.ORDER_CONTACT_FAX_NUM.equals(pFieldName)) {
            return getOrderContactFaxNum();
        } else if (OrderDataAccess.CONTRACT_ID.equals(pFieldName)) {
            return getContractId();
        } else if (OrderDataAccess.CONTRACT_SHORT_DESC.equals(pFieldName)) {
            return getContractShortDesc();
        } else if (OrderDataAccess.ORDER_TYPE_CD.equals(pFieldName)) {
            return getOrderTypeCd();
        } else if (OrderDataAccess.ORDER_SOURCE_CD.equals(pFieldName)) {
            return getOrderSourceCd();
        } else if (OrderDataAccess.ORDER_STATUS_CD.equals(pFieldName)) {
            return getOrderStatusCd();
        } else if (OrderDataAccess.TAX_NUM.equals(pFieldName)) {
            return getTaxNum();
        } else if (OrderDataAccess.ORIGINAL_AMOUNT.equals(pFieldName)) {
            return getOriginalAmount();
        } else if (OrderDataAccess.TOTAL_PRICE.equals(pFieldName)) {
            return getTotalPrice();
        } else if (OrderDataAccess.TOTAL_FREIGHT_COST.equals(pFieldName)) {
            return getTotalFreightCost();
        } else if (OrderDataAccess.TOTAL_MISC_COST.equals(pFieldName)) {
            return getTotalMiscCost();
        } else if (OrderDataAccess.TOTAL_TAX_COST.equals(pFieldName)) {
            return getTotalTaxCost();
        } else if (OrderDataAccess.TOTAL_CLEANWISE_COST.equals(pFieldName)) {
            return getTotalCleanwiseCost();
        } else if (OrderDataAccess.GROSS_WEIGHT.equals(pFieldName)) {
            return getGrossWeight();
        } else if (OrderDataAccess.ORIGINAL_ORDER_DATE.equals(pFieldName)) {
            return getOriginalOrderDate();
        } else if (OrderDataAccess.ORIGINAL_ORDER_TIME.equals(pFieldName)) {
            return getOriginalOrderTime();
        } else if (OrderDataAccess.REVISED_ORDER_DATE.equals(pFieldName)) {
            return getRevisedOrderDate();
        } else if (OrderDataAccess.REVISED_ORDER_TIME.equals(pFieldName)) {
            return getRevisedOrderTime();
        } else if (OrderDataAccess.COMMENTS.equals(pFieldName)) {
            return getComments();
        } else if (OrderDataAccess.INCOMING_TRADING_PROFILE_ID.equals(pFieldName)) {
            return getIncomingTradingProfileId();
        } else if (OrderDataAccess.LOCALE_CD.equals(pFieldName)) {
            return getLocaleCd();
        } else if (OrderDataAccess.CURRENCY_CD.equals(pFieldName)) {
            return getCurrencyCd();
        } else if (OrderDataAccess.ERP_ORDER_NUM.equals(pFieldName)) {
            return getErpOrderNum();
        } else if (OrderDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (OrderDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (OrderDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (OrderDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (OrderDataAccess.SITE_ID.equals(pFieldName)) {
            return getSiteId();
        } else if (OrderDataAccess.ACCOUNT_ID.equals(pFieldName)) {
            return getAccountId();
        } else if (OrderDataAccess.STORE_ID.equals(pFieldName)) {
            return getStoreId();
        } else if (OrderDataAccess.ERP_SYSTEM_CD.equals(pFieldName)) {
            return getErpSystemCd();
        } else if (OrderDataAccess.PRE_ORDER_ID.equals(pFieldName)) {
            return getPreOrderId();
        } else if (OrderDataAccess.REF_ORDER_ID.equals(pFieldName)) {
            return getRefOrderId();
        } else if (OrderDataAccess.TOTAL_RUSH_CHARGE.equals(pFieldName)) {
            return getTotalRushCharge();
        } else if (OrderDataAccess.ORDER_BUDGET_TYPE_CD.equals(pFieldName)) {
            return getOrderBudgetTypeCd();
        } else if (OrderDataAccess.ERP_ORDER_DATE.equals(pFieldName)) {
            return getErpOrderDate();
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
        return OrderDataAccess.CLW_ORDER;
    }

    
    /**
     * Sets the OrderId field. This field is required to be set in the database.
     *
     * @param pOrderId
     *  int to use to update the field.
     */
    public void setOrderId(int pOrderId){
        this.mOrderId = pOrderId;
        setDirty(true);
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

    /**
     * Sets the ExceptionInd field.
     *
     * @param pExceptionInd
     *  String to use to update the field.
     */
    public void setExceptionInd(String pExceptionInd){
        this.mExceptionInd = pExceptionInd;
        setDirty(true);
    }
    /**
     * Retrieves the ExceptionInd field.
     *
     * @return
     *  String containing the ExceptionInd field.
     */
    public String getExceptionInd(){
        return mExceptionInd;
    }

    /**
     * Sets the OrderNum field.
     *
     * @param pOrderNum
     *  String to use to update the field.
     */
    public void setOrderNum(String pOrderNum){
        this.mOrderNum = pOrderNum;
        setDirty(true);
    }
    /**
     * Retrieves the OrderNum field.
     *
     * @return
     *  String containing the OrderNum field.
     */
    public String getOrderNum(){
        return mOrderNum;
    }

    /**
     * Sets the RefOrderNum field.
     *
     * @param pRefOrderNum
     *  String to use to update the field.
     */
    public void setRefOrderNum(String pRefOrderNum){
        this.mRefOrderNum = pRefOrderNum;
        setDirty(true);
    }
    /**
     * Retrieves the RefOrderNum field.
     *
     * @return
     *  String containing the RefOrderNum field.
     */
    public String getRefOrderNum(){
        return mRefOrderNum;
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
     * Sets the CostCenterName field.
     *
     * @param pCostCenterName
     *  String to use to update the field.
     */
    public void setCostCenterName(String pCostCenterName){
        this.mCostCenterName = pCostCenterName;
        setDirty(true);
    }
    /**
     * Retrieves the CostCenterName field.
     *
     * @return
     *  String containing the CostCenterName field.
     */
    public String getCostCenterName(){
        return mCostCenterName;
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
     * Sets the WorkflowStatusCd field.
     *
     * @param pWorkflowStatusCd
     *  String to use to update the field.
     */
    public void setWorkflowStatusCd(String pWorkflowStatusCd){
        this.mWorkflowStatusCd = pWorkflowStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the WorkflowStatusCd field.
     *
     * @return
     *  String containing the WorkflowStatusCd field.
     */
    public String getWorkflowStatusCd(){
        return mWorkflowStatusCd;
    }

    /**
     * Sets the AccountErpNum field.
     *
     * @param pAccountErpNum
     *  String to use to update the field.
     */
    public void setAccountErpNum(String pAccountErpNum){
        this.mAccountErpNum = pAccountErpNum;
        setDirty(true);
    }
    /**
     * Retrieves the AccountErpNum field.
     *
     * @return
     *  String containing the AccountErpNum field.
     */
    public String getAccountErpNum(){
        return mAccountErpNum;
    }

    /**
     * Sets the SiteErpNum field.
     *
     * @param pSiteErpNum
     *  String to use to update the field.
     */
    public void setSiteErpNum(String pSiteErpNum){
        this.mSiteErpNum = pSiteErpNum;
        setDirty(true);
    }
    /**
     * Retrieves the SiteErpNum field.
     *
     * @return
     *  String containing the SiteErpNum field.
     */
    public String getSiteErpNum(){
        return mSiteErpNum;
    }

    /**
     * Sets the RequestPoNum field.
     *
     * @param pRequestPoNum
     *  String to use to update the field.
     */
    public void setRequestPoNum(String pRequestPoNum){
        this.mRequestPoNum = pRequestPoNum;
        setDirty(true);
    }
    /**
     * Retrieves the RequestPoNum field.
     *
     * @return
     *  String containing the RequestPoNum field.
     */
    public String getRequestPoNum(){
        return mRequestPoNum;
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
     * Sets the UserFirstName field.
     *
     * @param pUserFirstName
     *  String to use to update the field.
     */
    public void setUserFirstName(String pUserFirstName){
        this.mUserFirstName = pUserFirstName;
        setDirty(true);
    }
    /**
     * Retrieves the UserFirstName field.
     *
     * @return
     *  String containing the UserFirstName field.
     */
    public String getUserFirstName(){
        return mUserFirstName;
    }

    /**
     * Sets the UserLastName field.
     *
     * @param pUserLastName
     *  String to use to update the field.
     */
    public void setUserLastName(String pUserLastName){
        this.mUserLastName = pUserLastName;
        setDirty(true);
    }
    /**
     * Retrieves the UserLastName field.
     *
     * @return
     *  String containing the UserLastName field.
     */
    public String getUserLastName(){
        return mUserLastName;
    }

    /**
     * Sets the OrderSiteName field.
     *
     * @param pOrderSiteName
     *  String to use to update the field.
     */
    public void setOrderSiteName(String pOrderSiteName){
        this.mOrderSiteName = pOrderSiteName;
        setDirty(true);
    }
    /**
     * Retrieves the OrderSiteName field.
     *
     * @return
     *  String containing the OrderSiteName field.
     */
    public String getOrderSiteName(){
        return mOrderSiteName;
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
     * Sets the OrderContactPhoneNum field.
     *
     * @param pOrderContactPhoneNum
     *  String to use to update the field.
     */
    public void setOrderContactPhoneNum(String pOrderContactPhoneNum){
        this.mOrderContactPhoneNum = pOrderContactPhoneNum;
        setDirty(true);
    }
    /**
     * Retrieves the OrderContactPhoneNum field.
     *
     * @return
     *  String containing the OrderContactPhoneNum field.
     */
    public String getOrderContactPhoneNum(){
        return mOrderContactPhoneNum;
    }

    /**
     * Sets the OrderContactEmail field.
     *
     * @param pOrderContactEmail
     *  String to use to update the field.
     */
    public void setOrderContactEmail(String pOrderContactEmail){
        this.mOrderContactEmail = pOrderContactEmail;
        setDirty(true);
    }
    /**
     * Retrieves the OrderContactEmail field.
     *
     * @return
     *  String containing the OrderContactEmail field.
     */
    public String getOrderContactEmail(){
        return mOrderContactEmail;
    }

    /**
     * Sets the OrderContactFaxNum field.
     *
     * @param pOrderContactFaxNum
     *  String to use to update the field.
     */
    public void setOrderContactFaxNum(String pOrderContactFaxNum){
        this.mOrderContactFaxNum = pOrderContactFaxNum;
        setDirty(true);
    }
    /**
     * Retrieves the OrderContactFaxNum field.
     *
     * @return
     *  String containing the OrderContactFaxNum field.
     */
    public String getOrderContactFaxNum(){
        return mOrderContactFaxNum;
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
     * Sets the ContractShortDesc field.
     *
     * @param pContractShortDesc
     *  String to use to update the field.
     */
    public void setContractShortDesc(String pContractShortDesc){
        this.mContractShortDesc = pContractShortDesc;
        setDirty(true);
    }
    /**
     * Retrieves the ContractShortDesc field.
     *
     * @return
     *  String containing the ContractShortDesc field.
     */
    public String getContractShortDesc(){
        return mContractShortDesc;
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
     * Sets the OrderStatusCd field.
     *
     * @param pOrderStatusCd
     *  String to use to update the field.
     */
    public void setOrderStatusCd(String pOrderStatusCd){
        this.mOrderStatusCd = pOrderStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the OrderStatusCd field.
     *
     * @return
     *  String containing the OrderStatusCd field.
     */
    public String getOrderStatusCd(){
        return mOrderStatusCd;
    }

    /**
     * Sets the TaxNum field.
     *
     * @param pTaxNum
     *  String to use to update the field.
     */
    public void setTaxNum(String pTaxNum){
        this.mTaxNum = pTaxNum;
        setDirty(true);
    }
    /**
     * Retrieves the TaxNum field.
     *
     * @return
     *  String containing the TaxNum field.
     */
    public String getTaxNum(){
        return mTaxNum;
    }

    /**
     * Sets the OriginalAmount field.
     *
     * @param pOriginalAmount
     *  java.math.BigDecimal to use to update the field.
     */
    public void setOriginalAmount(java.math.BigDecimal pOriginalAmount){
        this.mOriginalAmount = pOriginalAmount;
        setDirty(true);
    }
    /**
     * Retrieves the OriginalAmount field.
     *
     * @return
     *  java.math.BigDecimal containing the OriginalAmount field.
     */
    public java.math.BigDecimal getOriginalAmount(){
        return mOriginalAmount;
    }

    /**
     * Sets the TotalPrice field.
     *
     * @param pTotalPrice
     *  java.math.BigDecimal to use to update the field.
     */
    public void setTotalPrice(java.math.BigDecimal pTotalPrice){
        this.mTotalPrice = pTotalPrice;
        setDirty(true);
    }
    /**
     * Retrieves the TotalPrice field.
     *
     * @return
     *  java.math.BigDecimal containing the TotalPrice field.
     */
    public java.math.BigDecimal getTotalPrice(){
        return mTotalPrice;
    }

    /**
     * Sets the TotalFreightCost field.
     *
     * @param pTotalFreightCost
     *  java.math.BigDecimal to use to update the field.
     */
    public void setTotalFreightCost(java.math.BigDecimal pTotalFreightCost){
        this.mTotalFreightCost = pTotalFreightCost;
        setDirty(true);
    }
    /**
     * Retrieves the TotalFreightCost field.
     *
     * @return
     *  java.math.BigDecimal containing the TotalFreightCost field.
     */
    public java.math.BigDecimal getTotalFreightCost(){
        return mTotalFreightCost;
    }

    /**
     * Sets the TotalMiscCost field.
     *
     * @param pTotalMiscCost
     *  java.math.BigDecimal to use to update the field.
     */
    public void setTotalMiscCost(java.math.BigDecimal pTotalMiscCost){
        this.mTotalMiscCost = pTotalMiscCost;
        setDirty(true);
    }
    /**
     * Retrieves the TotalMiscCost field.
     *
     * @return
     *  java.math.BigDecimal containing the TotalMiscCost field.
     */
    public java.math.BigDecimal getTotalMiscCost(){
        return mTotalMiscCost;
    }

    /**
     * Sets the TotalTaxCost field.
     *
     * @param pTotalTaxCost
     *  java.math.BigDecimal to use to update the field.
     */
    public void setTotalTaxCost(java.math.BigDecimal pTotalTaxCost){
        this.mTotalTaxCost = pTotalTaxCost;
        setDirty(true);
    }
    /**
     * Retrieves the TotalTaxCost field.
     *
     * @return
     *  java.math.BigDecimal containing the TotalTaxCost field.
     */
    public java.math.BigDecimal getTotalTaxCost(){
        return mTotalTaxCost;
    }

    /**
     * Sets the TotalCleanwiseCost field.
     *
     * @param pTotalCleanwiseCost
     *  java.math.BigDecimal to use to update the field.
     */
    public void setTotalCleanwiseCost(java.math.BigDecimal pTotalCleanwiseCost){
        this.mTotalCleanwiseCost = pTotalCleanwiseCost;
        setDirty(true);
    }
    /**
     * Retrieves the TotalCleanwiseCost field.
     *
     * @return
     *  java.math.BigDecimal containing the TotalCleanwiseCost field.
     */
    public java.math.BigDecimal getTotalCleanwiseCost(){
        return mTotalCleanwiseCost;
    }

    /**
     * Sets the GrossWeight field.
     *
     * @param pGrossWeight
     *  java.math.BigDecimal to use to update the field.
     */
    public void setGrossWeight(java.math.BigDecimal pGrossWeight){
        this.mGrossWeight = pGrossWeight;
        setDirty(true);
    }
    /**
     * Retrieves the GrossWeight field.
     *
     * @return
     *  java.math.BigDecimal containing the GrossWeight field.
     */
    public java.math.BigDecimal getGrossWeight(){
        return mGrossWeight;
    }

    /**
     * Sets the OriginalOrderDate field.
     *
     * @param pOriginalOrderDate
     *  Date to use to update the field.
     */
    public void setOriginalOrderDate(Date pOriginalOrderDate){
        this.mOriginalOrderDate = pOriginalOrderDate;
        setDirty(true);
    }
    /**
     * Retrieves the OriginalOrderDate field.
     *
     * @return
     *  Date containing the OriginalOrderDate field.
     */
    public Date getOriginalOrderDate(){
        return mOriginalOrderDate;
    }

    /**
     * Sets the OriginalOrderTime field.
     *
     * @param pOriginalOrderTime
     *  Date to use to update the field.
     */
    public void setOriginalOrderTime(Date pOriginalOrderTime){
        this.mOriginalOrderTime = pOriginalOrderTime;
        setDirty(true);
    }
    /**
     * Retrieves the OriginalOrderTime field.
     *
     * @return
     *  Date containing the OriginalOrderTime field.
     */
    public Date getOriginalOrderTime(){
        return mOriginalOrderTime;
    }

    /**
     * Sets the RevisedOrderDate field.
     *
     * @param pRevisedOrderDate
     *  Date to use to update the field.
     */
    public void setRevisedOrderDate(Date pRevisedOrderDate){
        this.mRevisedOrderDate = pRevisedOrderDate;
        setDirty(true);
    }
    /**
     * Retrieves the RevisedOrderDate field.
     *
     * @return
     *  Date containing the RevisedOrderDate field.
     */
    public Date getRevisedOrderDate(){
        return mRevisedOrderDate;
    }

    /**
     * Sets the RevisedOrderTime field.
     *
     * @param pRevisedOrderTime
     *  Date to use to update the field.
     */
    public void setRevisedOrderTime(Date pRevisedOrderTime){
        this.mRevisedOrderTime = pRevisedOrderTime;
        setDirty(true);
    }
    /**
     * Retrieves the RevisedOrderTime field.
     *
     * @return
     *  Date containing the RevisedOrderTime field.
     */
    public Date getRevisedOrderTime(){
        return mRevisedOrderTime;
    }

    /**
     * Sets the Comments field.
     *
     * @param pComments
     *  String to use to update the field.
     */
    public void setComments(String pComments){
        this.mComments = pComments;
        setDirty(true);
    }
    /**
     * Retrieves the Comments field.
     *
     * @return
     *  String containing the Comments field.
     */
    public String getComments(){
        return mComments;
    }

    /**
     * Sets the IncomingTradingProfileId field.
     *
     * @param pIncomingTradingProfileId
     *  int to use to update the field.
     */
    public void setIncomingTradingProfileId(int pIncomingTradingProfileId){
        this.mIncomingTradingProfileId = pIncomingTradingProfileId;
        setDirty(true);
    }
    /**
     * Retrieves the IncomingTradingProfileId field.
     *
     * @return
     *  int containing the IncomingTradingProfileId field.
     */
    public int getIncomingTradingProfileId(){
        return mIncomingTradingProfileId;
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

    /**
     * Sets the ErpOrderNum field.
     *
     * @param pErpOrderNum
     *  int to use to update the field.
     */
    public void setErpOrderNum(int pErpOrderNum){
        this.mErpOrderNum = pErpOrderNum;
        setDirty(true);
    }
    /**
     * Retrieves the ErpOrderNum field.
     *
     * @return
     *  int containing the ErpOrderNum field.
     */
    public int getErpOrderNum(){
        return mErpOrderNum;
    }

    /**
     * Sets the AddDate field.
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
     * Sets the ModDate field.
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
     * Sets the StoreId field.
     *
     * @param pStoreId
     *  int to use to update the field.
     */
    public void setStoreId(int pStoreId){
        this.mStoreId = pStoreId;
        setDirty(true);
    }
    /**
     * Retrieves the StoreId field.
     *
     * @return
     *  int containing the StoreId field.
     */
    public int getStoreId(){
        return mStoreId;
    }

    /**
     * Sets the ErpSystemCd field.
     *
     * @param pErpSystemCd
     *  String to use to update the field.
     */
    public void setErpSystemCd(String pErpSystemCd){
        this.mErpSystemCd = pErpSystemCd;
        setDirty(true);
    }
    /**
     * Retrieves the ErpSystemCd field.
     *
     * @return
     *  String containing the ErpSystemCd field.
     */
    public String getErpSystemCd(){
        return mErpSystemCd;
    }

    /**
     * Sets the PreOrderId field.
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
     * Sets the TotalRushCharge field.
     *
     * @param pTotalRushCharge
     *  java.math.BigDecimal to use to update the field.
     */
    public void setTotalRushCharge(java.math.BigDecimal pTotalRushCharge){
        this.mTotalRushCharge = pTotalRushCharge;
        setDirty(true);
    }
    /**
     * Retrieves the TotalRushCharge field.
     *
     * @return
     *  java.math.BigDecimal containing the TotalRushCharge field.
     */
    public java.math.BigDecimal getTotalRushCharge(){
        return mTotalRushCharge;
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
     * Sets the ErpOrderDate field.
     *
     * @param pErpOrderDate
     *  Date to use to update the field.
     */
    public void setErpOrderDate(Date pErpOrderDate){
        this.mErpOrderDate = pErpOrderDate;
        setDirty(true);
    }
    /**
     * Retrieves the ErpOrderDate field.
     *
     * @return
     *  Date containing the ErpOrderDate field.
     */
    public Date getErpOrderDate(){
        return mErpOrderDate;
    }


}
