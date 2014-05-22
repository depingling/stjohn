
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        OrderPropertyData
 * Description:  This is a ValueObject class wrapping the database table CLW_ORDER_PROPERTY.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.OrderPropertyDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>OrderPropertyData</code> is a ValueObject class wrapping of the database table CLW_ORDER_PROPERTY.
 */
public class OrderPropertyData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 8989802948863104997L;
    private int mOrderPropertyId;// SQL type:NUMBER, not null
    private int mOrderId;// SQL type:NUMBER
    private int mOrderItemId;// SQL type:NUMBER
    private int mInvoiceDistId;// SQL type:NUMBER
    private int mInvoiceDistDetailId;// SQL type:NUMBER
    private int mInvoiceCustId;// SQL type:NUMBER
    private int mInvoiceCustDetailId;// SQL type:NUMBER
    private int mOrderAddressId;// SQL type:NUMBER
    private String mShortDesc;// SQL type:VARCHAR2
    private String mValue;// SQL type:VARCHAR2
    private String mOrderPropertyStatusCd;// SQL type:VARCHAR2, not null
    private String mOrderPropertyTypeCd;// SQL type:VARCHAR2, not null
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private String mOrderStatusCd;// SQL type:VARCHAR2
    private int mWorkflowRuleId;// SQL type:NUMBER
    private Date mApproveDate;// SQL type:DATE
    private int mApproveUserId;// SQL type:NUMBER
    private String mMessageKey;// SQL type:VARCHAR2
    private String mArg0;// SQL type:VARCHAR2
    private String mArg0TypeCd;// SQL type:VARCHAR2
    private String mArg1;// SQL type:VARCHAR2
    private String mArg1TypeCd;// SQL type:VARCHAR2
    private String mArg2;// SQL type:VARCHAR2
    private String mArg2TypeCd;// SQL type:VARCHAR2
    private String mArg3;// SQL type:VARCHAR2
    private String mArg3TypeCd;// SQL type:VARCHAR2
    private int mPurchaseOrderId;// SQL type:NUMBER

    /**
     * Constructor.
     */
    public OrderPropertyData ()
    {
        mShortDesc = "";
        mValue = "";
        mOrderPropertyStatusCd = "";
        mOrderPropertyTypeCd = "";
        mAddBy = "";
        mModBy = "";
        mOrderStatusCd = "";
        mMessageKey = "";
        mArg0 = "";
        mArg0TypeCd = "";
        mArg1 = "";
        mArg1TypeCd = "";
        mArg2 = "";
        mArg2TypeCd = "";
        mArg3 = "";
        mArg3TypeCd = "";
    }

    /**
     * Constructor.
     */
    public OrderPropertyData(int parm1, int parm2, int parm3, int parm4, int parm5, int parm6, int parm7, int parm8, String parm9, String parm10, String parm11, String parm12, Date parm13, String parm14, Date parm15, String parm16, String parm17, int parm18, Date parm19, int parm20, String parm21, String parm22, String parm23, String parm24, String parm25, String parm26, String parm27, String parm28, String parm29, int parm30)
    {
        mOrderPropertyId = parm1;
        mOrderId = parm2;
        mOrderItemId = parm3;
        mInvoiceDistId = parm4;
        mInvoiceDistDetailId = parm5;
        mInvoiceCustId = parm6;
        mInvoiceCustDetailId = parm7;
        mOrderAddressId = parm8;
        mShortDesc = parm9;
        mValue = parm10;
        mOrderPropertyStatusCd = parm11;
        mOrderPropertyTypeCd = parm12;
        mAddDate = parm13;
        mAddBy = parm14;
        mModDate = parm15;
        mModBy = parm16;
        mOrderStatusCd = parm17;
        mWorkflowRuleId = parm18;
        mApproveDate = parm19;
        mApproveUserId = parm20;
        mMessageKey = parm21;
        mArg0 = parm22;
        mArg0TypeCd = parm23;
        mArg1 = parm24;
        mArg1TypeCd = parm25;
        mArg2 = parm26;
        mArg2TypeCd = parm27;
        mArg3 = parm28;
        mArg3TypeCd = parm29;
        mPurchaseOrderId = parm30;
        
    }

    /**
     * Creates a new OrderPropertyData
     *
     * @return
     *  Newly initialized OrderPropertyData object.
     */
    public static OrderPropertyData createValue ()
    {
        OrderPropertyData valueData = new OrderPropertyData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this OrderPropertyData object
     */
    public String toString()
    {
        return "[" + "OrderPropertyId=" + mOrderPropertyId + ", OrderId=" + mOrderId + ", OrderItemId=" + mOrderItemId + ", InvoiceDistId=" + mInvoiceDistId + ", InvoiceDistDetailId=" + mInvoiceDistDetailId + ", InvoiceCustId=" + mInvoiceCustId + ", InvoiceCustDetailId=" + mInvoiceCustDetailId + ", OrderAddressId=" + mOrderAddressId + ", ShortDesc=" + mShortDesc + ", Value=" + mValue + ", OrderPropertyStatusCd=" + mOrderPropertyStatusCd + ", OrderPropertyTypeCd=" + mOrderPropertyTypeCd + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", OrderStatusCd=" + mOrderStatusCd + ", WorkflowRuleId=" + mWorkflowRuleId + ", ApproveDate=" + mApproveDate + ", ApproveUserId=" + mApproveUserId + ", MessageKey=" + mMessageKey + ", Arg0=" + mArg0 + ", Arg0TypeCd=" + mArg0TypeCd + ", Arg1=" + mArg1 + ", Arg1TypeCd=" + mArg1TypeCd + ", Arg2=" + mArg2 + ", Arg2TypeCd=" + mArg2TypeCd + ", Arg3=" + mArg3 + ", Arg3TypeCd=" + mArg3TypeCd + ", PurchaseOrderId=" + mPurchaseOrderId + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("OrderProperty");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mOrderPropertyId));

        node =  doc.createElement("OrderId");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderId)));
        root.appendChild(node);

        node =  doc.createElement("OrderItemId");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderItemId)));
        root.appendChild(node);

        node =  doc.createElement("InvoiceDistId");
        node.appendChild(doc.createTextNode(String.valueOf(mInvoiceDistId)));
        root.appendChild(node);

        node =  doc.createElement("InvoiceDistDetailId");
        node.appendChild(doc.createTextNode(String.valueOf(mInvoiceDistDetailId)));
        root.appendChild(node);

        node =  doc.createElement("InvoiceCustId");
        node.appendChild(doc.createTextNode(String.valueOf(mInvoiceCustId)));
        root.appendChild(node);

        node =  doc.createElement("InvoiceCustDetailId");
        node.appendChild(doc.createTextNode(String.valueOf(mInvoiceCustDetailId)));
        root.appendChild(node);

        node =  doc.createElement("OrderAddressId");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderAddressId)));
        root.appendChild(node);

        node =  doc.createElement("ShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mShortDesc)));
        root.appendChild(node);

        node =  doc.createElement("Value");
        node.appendChild(doc.createTextNode(String.valueOf(mValue)));
        root.appendChild(node);

        node =  doc.createElement("OrderPropertyStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderPropertyStatusCd)));
        root.appendChild(node);

        node =  doc.createElement("OrderPropertyTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderPropertyTypeCd)));
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

        node =  doc.createElement("OrderStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderStatusCd)));
        root.appendChild(node);

        node =  doc.createElement("WorkflowRuleId");
        node.appendChild(doc.createTextNode(String.valueOf(mWorkflowRuleId)));
        root.appendChild(node);

        node =  doc.createElement("ApproveDate");
        node.appendChild(doc.createTextNode(String.valueOf(mApproveDate)));
        root.appendChild(node);

        node =  doc.createElement("ApproveUserId");
        node.appendChild(doc.createTextNode(String.valueOf(mApproveUserId)));
        root.appendChild(node);

        node =  doc.createElement("MessageKey");
        node.appendChild(doc.createTextNode(String.valueOf(mMessageKey)));
        root.appendChild(node);

        node =  doc.createElement("Arg0");
        node.appendChild(doc.createTextNode(String.valueOf(mArg0)));
        root.appendChild(node);

        node =  doc.createElement("Arg0TypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mArg0TypeCd)));
        root.appendChild(node);

        node =  doc.createElement("Arg1");
        node.appendChild(doc.createTextNode(String.valueOf(mArg1)));
        root.appendChild(node);

        node =  doc.createElement("Arg1TypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mArg1TypeCd)));
        root.appendChild(node);

        node =  doc.createElement("Arg2");
        node.appendChild(doc.createTextNode(String.valueOf(mArg2)));
        root.appendChild(node);

        node =  doc.createElement("Arg2TypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mArg2TypeCd)));
        root.appendChild(node);

        node =  doc.createElement("Arg3");
        node.appendChild(doc.createTextNode(String.valueOf(mArg3)));
        root.appendChild(node);

        node =  doc.createElement("Arg3TypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mArg3TypeCd)));
        root.appendChild(node);

        node =  doc.createElement("PurchaseOrderId");
        node.appendChild(doc.createTextNode(String.valueOf(mPurchaseOrderId)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the OrderPropertyId field is not cloned.
    *
    * @return OrderPropertyData object
    */
    public Object clone(){
        OrderPropertyData myClone = new OrderPropertyData();
        
        myClone.mOrderId = mOrderId;
        
        myClone.mOrderItemId = mOrderItemId;
        
        myClone.mInvoiceDistId = mInvoiceDistId;
        
        myClone.mInvoiceDistDetailId = mInvoiceDistDetailId;
        
        myClone.mInvoiceCustId = mInvoiceCustId;
        
        myClone.mInvoiceCustDetailId = mInvoiceCustDetailId;
        
        myClone.mOrderAddressId = mOrderAddressId;
        
        myClone.mShortDesc = mShortDesc;
        
        myClone.mValue = mValue;
        
        myClone.mOrderPropertyStatusCd = mOrderPropertyStatusCd;
        
        myClone.mOrderPropertyTypeCd = mOrderPropertyTypeCd;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        myClone.mOrderStatusCd = mOrderStatusCd;
        
        myClone.mWorkflowRuleId = mWorkflowRuleId;
        
        if(mApproveDate != null){
                myClone.mApproveDate = (Date) mApproveDate.clone();
        }
        
        myClone.mApproveUserId = mApproveUserId;
        
        myClone.mMessageKey = mMessageKey;
        
        myClone.mArg0 = mArg0;
        
        myClone.mArg0TypeCd = mArg0TypeCd;
        
        myClone.mArg1 = mArg1;
        
        myClone.mArg1TypeCd = mArg1TypeCd;
        
        myClone.mArg2 = mArg2;
        
        myClone.mArg2TypeCd = mArg2TypeCd;
        
        myClone.mArg3 = mArg3;
        
        myClone.mArg3TypeCd = mArg3TypeCd;
        
        myClone.mPurchaseOrderId = mPurchaseOrderId;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (OrderPropertyDataAccess.ORDER_PROPERTY_ID.equals(pFieldName)) {
            return getOrderPropertyId();
        } else if (OrderPropertyDataAccess.ORDER_ID.equals(pFieldName)) {
            return getOrderId();
        } else if (OrderPropertyDataAccess.ORDER_ITEM_ID.equals(pFieldName)) {
            return getOrderItemId();
        } else if (OrderPropertyDataAccess.INVOICE_DIST_ID.equals(pFieldName)) {
            return getInvoiceDistId();
        } else if (OrderPropertyDataAccess.INVOICE_DIST_DETAIL_ID.equals(pFieldName)) {
            return getInvoiceDistDetailId();
        } else if (OrderPropertyDataAccess.INVOICE_CUST_ID.equals(pFieldName)) {
            return getInvoiceCustId();
        } else if (OrderPropertyDataAccess.INVOICE_CUST_DETAIL_ID.equals(pFieldName)) {
            return getInvoiceCustDetailId();
        } else if (OrderPropertyDataAccess.ORDER_ADDRESS_ID.equals(pFieldName)) {
            return getOrderAddressId();
        } else if (OrderPropertyDataAccess.SHORT_DESC.equals(pFieldName)) {
            return getShortDesc();
        } else if (OrderPropertyDataAccess.CLW_VALUE.equals(pFieldName)) {
            return getValue();
        } else if (OrderPropertyDataAccess.ORDER_PROPERTY_STATUS_CD.equals(pFieldName)) {
            return getOrderPropertyStatusCd();
        } else if (OrderPropertyDataAccess.ORDER_PROPERTY_TYPE_CD.equals(pFieldName)) {
            return getOrderPropertyTypeCd();
        } else if (OrderPropertyDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (OrderPropertyDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (OrderPropertyDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (OrderPropertyDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (OrderPropertyDataAccess.ORDER_STATUS_CD.equals(pFieldName)) {
            return getOrderStatusCd();
        } else if (OrderPropertyDataAccess.WORKFLOW_RULE_ID.equals(pFieldName)) {
            return getWorkflowRuleId();
        } else if (OrderPropertyDataAccess.APPROVE_DATE.equals(pFieldName)) {
            return getApproveDate();
        } else if (OrderPropertyDataAccess.APPROVE_USER_ID.equals(pFieldName)) {
            return getApproveUserId();
        } else if (OrderPropertyDataAccess.MESSAGE_KEY.equals(pFieldName)) {
            return getMessageKey();
        } else if (OrderPropertyDataAccess.ARG0.equals(pFieldName)) {
            return getArg0();
        } else if (OrderPropertyDataAccess.ARG0_TYPE_CD.equals(pFieldName)) {
            return getArg0TypeCd();
        } else if (OrderPropertyDataAccess.ARG1.equals(pFieldName)) {
            return getArg1();
        } else if (OrderPropertyDataAccess.ARG1_TYPE_CD.equals(pFieldName)) {
            return getArg1TypeCd();
        } else if (OrderPropertyDataAccess.ARG2.equals(pFieldName)) {
            return getArg2();
        } else if (OrderPropertyDataAccess.ARG2_TYPE_CD.equals(pFieldName)) {
            return getArg2TypeCd();
        } else if (OrderPropertyDataAccess.ARG3.equals(pFieldName)) {
            return getArg3();
        } else if (OrderPropertyDataAccess.ARG3_TYPE_CD.equals(pFieldName)) {
            return getArg3TypeCd();
        } else if (OrderPropertyDataAccess.PURCHASE_ORDER_ID.equals(pFieldName)) {
            return getPurchaseOrderId();
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
        return OrderPropertyDataAccess.CLW_ORDER_PROPERTY;
    }

    
    /**
     * Sets the OrderPropertyId field. This field is required to be set in the database.
     *
     * @param pOrderPropertyId
     *  int to use to update the field.
     */
    public void setOrderPropertyId(int pOrderPropertyId){
        this.mOrderPropertyId = pOrderPropertyId;
        setDirty(true);
    }
    /**
     * Retrieves the OrderPropertyId field.
     *
     * @return
     *  int containing the OrderPropertyId field.
     */
    public int getOrderPropertyId(){
        return mOrderPropertyId;
    }

    /**
     * Sets the OrderId field.
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
     * Sets the OrderItemId field.
     *
     * @param pOrderItemId
     *  int to use to update the field.
     */
    public void setOrderItemId(int pOrderItemId){
        this.mOrderItemId = pOrderItemId;
        setDirty(true);
    }
    /**
     * Retrieves the OrderItemId field.
     *
     * @return
     *  int containing the OrderItemId field.
     */
    public int getOrderItemId(){
        return mOrderItemId;
    }

    /**
     * Sets the InvoiceDistId field.
     *
     * @param pInvoiceDistId
     *  int to use to update the field.
     */
    public void setInvoiceDistId(int pInvoiceDistId){
        this.mInvoiceDistId = pInvoiceDistId;
        setDirty(true);
    }
    /**
     * Retrieves the InvoiceDistId field.
     *
     * @return
     *  int containing the InvoiceDistId field.
     */
    public int getInvoiceDistId(){
        return mInvoiceDistId;
    }

    /**
     * Sets the InvoiceDistDetailId field.
     *
     * @param pInvoiceDistDetailId
     *  int to use to update the field.
     */
    public void setInvoiceDistDetailId(int pInvoiceDistDetailId){
        this.mInvoiceDistDetailId = pInvoiceDistDetailId;
        setDirty(true);
    }
    /**
     * Retrieves the InvoiceDistDetailId field.
     *
     * @return
     *  int containing the InvoiceDistDetailId field.
     */
    public int getInvoiceDistDetailId(){
        return mInvoiceDistDetailId;
    }

    /**
     * Sets the InvoiceCustId field.
     *
     * @param pInvoiceCustId
     *  int to use to update the field.
     */
    public void setInvoiceCustId(int pInvoiceCustId){
        this.mInvoiceCustId = pInvoiceCustId;
        setDirty(true);
    }
    /**
     * Retrieves the InvoiceCustId field.
     *
     * @return
     *  int containing the InvoiceCustId field.
     */
    public int getInvoiceCustId(){
        return mInvoiceCustId;
    }

    /**
     * Sets the InvoiceCustDetailId field.
     *
     * @param pInvoiceCustDetailId
     *  int to use to update the field.
     */
    public void setInvoiceCustDetailId(int pInvoiceCustDetailId){
        this.mInvoiceCustDetailId = pInvoiceCustDetailId;
        setDirty(true);
    }
    /**
     * Retrieves the InvoiceCustDetailId field.
     *
     * @return
     *  int containing the InvoiceCustDetailId field.
     */
    public int getInvoiceCustDetailId(){
        return mInvoiceCustDetailId;
    }

    /**
     * Sets the OrderAddressId field.
     *
     * @param pOrderAddressId
     *  int to use to update the field.
     */
    public void setOrderAddressId(int pOrderAddressId){
        this.mOrderAddressId = pOrderAddressId;
        setDirty(true);
    }
    /**
     * Retrieves the OrderAddressId field.
     *
     * @return
     *  int containing the OrderAddressId field.
     */
    public int getOrderAddressId(){
        return mOrderAddressId;
    }

    /**
     * Sets the ShortDesc field.
     *
     * @param pShortDesc
     *  String to use to update the field.
     */
    public void setShortDesc(String pShortDesc){
        this.mShortDesc = pShortDesc;
        setDirty(true);
    }
    /**
     * Retrieves the ShortDesc field.
     *
     * @return
     *  String containing the ShortDesc field.
     */
    public String getShortDesc(){
        return mShortDesc;
    }

    /**
     * Sets the Value field.
     *
     * @param pValue
     *  String to use to update the field.
     */
    public void setValue(String pValue){
        this.mValue = pValue;
        setDirty(true);
    }
    /**
     * Retrieves the Value field.
     *
     * @return
     *  String containing the Value field.
     */
    public String getValue(){
        return mValue;
    }

    /**
     * Sets the OrderPropertyStatusCd field. This field is required to be set in the database.
     *
     * @param pOrderPropertyStatusCd
     *  String to use to update the field.
     */
    public void setOrderPropertyStatusCd(String pOrderPropertyStatusCd){
        this.mOrderPropertyStatusCd = pOrderPropertyStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the OrderPropertyStatusCd field.
     *
     * @return
     *  String containing the OrderPropertyStatusCd field.
     */
    public String getOrderPropertyStatusCd(){
        return mOrderPropertyStatusCd;
    }

    /**
     * Sets the OrderPropertyTypeCd field. This field is required to be set in the database.
     *
     * @param pOrderPropertyTypeCd
     *  String to use to update the field.
     */
    public void setOrderPropertyTypeCd(String pOrderPropertyTypeCd){
        this.mOrderPropertyTypeCd = pOrderPropertyTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the OrderPropertyTypeCd field.
     *
     * @return
     *  String containing the OrderPropertyTypeCd field.
     */
    public String getOrderPropertyTypeCd(){
        return mOrderPropertyTypeCd;
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
     * Sets the WorkflowRuleId field.
     *
     * @param pWorkflowRuleId
     *  int to use to update the field.
     */
    public void setWorkflowRuleId(int pWorkflowRuleId){
        this.mWorkflowRuleId = pWorkflowRuleId;
        setDirty(true);
    }
    /**
     * Retrieves the WorkflowRuleId field.
     *
     * @return
     *  int containing the WorkflowRuleId field.
     */
    public int getWorkflowRuleId(){
        return mWorkflowRuleId;
    }

    /**
     * Sets the ApproveDate field.
     *
     * @param pApproveDate
     *  Date to use to update the field.
     */
    public void setApproveDate(Date pApproveDate){
        this.mApproveDate = pApproveDate;
        setDirty(true);
    }
    /**
     * Retrieves the ApproveDate field.
     *
     * @return
     *  Date containing the ApproveDate field.
     */
    public Date getApproveDate(){
        return mApproveDate;
    }

    /**
     * Sets the ApproveUserId field.
     *
     * @param pApproveUserId
     *  int to use to update the field.
     */
    public void setApproveUserId(int pApproveUserId){
        this.mApproveUserId = pApproveUserId;
        setDirty(true);
    }
    /**
     * Retrieves the ApproveUserId field.
     *
     * @return
     *  int containing the ApproveUserId field.
     */
    public int getApproveUserId(){
        return mApproveUserId;
    }

    /**
     * Sets the MessageKey field.
     *
     * @param pMessageKey
     *  String to use to update the field.
     */
    public void setMessageKey(String pMessageKey){
        this.mMessageKey = pMessageKey;
        setDirty(true);
    }
    /**
     * Retrieves the MessageKey field.
     *
     * @return
     *  String containing the MessageKey field.
     */
    public String getMessageKey(){
        return mMessageKey;
    }

    /**
     * Sets the Arg0 field.
     *
     * @param pArg0
     *  String to use to update the field.
     */
    public void setArg0(String pArg0){
        this.mArg0 = pArg0;
        setDirty(true);
    }
    /**
     * Retrieves the Arg0 field.
     *
     * @return
     *  String containing the Arg0 field.
     */
    public String getArg0(){
        return mArg0;
    }

    /**
     * Sets the Arg0TypeCd field.
     *
     * @param pArg0TypeCd
     *  String to use to update the field.
     */
    public void setArg0TypeCd(String pArg0TypeCd){
        this.mArg0TypeCd = pArg0TypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the Arg0TypeCd field.
     *
     * @return
     *  String containing the Arg0TypeCd field.
     */
    public String getArg0TypeCd(){
        return mArg0TypeCd;
    }

    /**
     * Sets the Arg1 field.
     *
     * @param pArg1
     *  String to use to update the field.
     */
    public void setArg1(String pArg1){
        this.mArg1 = pArg1;
        setDirty(true);
    }
    /**
     * Retrieves the Arg1 field.
     *
     * @return
     *  String containing the Arg1 field.
     */
    public String getArg1(){
        return mArg1;
    }

    /**
     * Sets the Arg1TypeCd field.
     *
     * @param pArg1TypeCd
     *  String to use to update the field.
     */
    public void setArg1TypeCd(String pArg1TypeCd){
        this.mArg1TypeCd = pArg1TypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the Arg1TypeCd field.
     *
     * @return
     *  String containing the Arg1TypeCd field.
     */
    public String getArg1TypeCd(){
        return mArg1TypeCd;
    }

    /**
     * Sets the Arg2 field.
     *
     * @param pArg2
     *  String to use to update the field.
     */
    public void setArg2(String pArg2){
        this.mArg2 = pArg2;
        setDirty(true);
    }
    /**
     * Retrieves the Arg2 field.
     *
     * @return
     *  String containing the Arg2 field.
     */
    public String getArg2(){
        return mArg2;
    }

    /**
     * Sets the Arg2TypeCd field.
     *
     * @param pArg2TypeCd
     *  String to use to update the field.
     */
    public void setArg2TypeCd(String pArg2TypeCd){
        this.mArg2TypeCd = pArg2TypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the Arg2TypeCd field.
     *
     * @return
     *  String containing the Arg2TypeCd field.
     */
    public String getArg2TypeCd(){
        return mArg2TypeCd;
    }

    /**
     * Sets the Arg3 field.
     *
     * @param pArg3
     *  String to use to update the field.
     */
    public void setArg3(String pArg3){
        this.mArg3 = pArg3;
        setDirty(true);
    }
    /**
     * Retrieves the Arg3 field.
     *
     * @return
     *  String containing the Arg3 field.
     */
    public String getArg3(){
        return mArg3;
    }

    /**
     * Sets the Arg3TypeCd field.
     *
     * @param pArg3TypeCd
     *  String to use to update the field.
     */
    public void setArg3TypeCd(String pArg3TypeCd){
        this.mArg3TypeCd = pArg3TypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the Arg3TypeCd field.
     *
     * @return
     *  String containing the Arg3TypeCd field.
     */
    public String getArg3TypeCd(){
        return mArg3TypeCd;
    }

    /**
     * Sets the PurchaseOrderId field.
     *
     * @param pPurchaseOrderId
     *  int to use to update the field.
     */
    public void setPurchaseOrderId(int pPurchaseOrderId){
        this.mPurchaseOrderId = pPurchaseOrderId;
        setDirty(true);
    }
    /**
     * Retrieves the PurchaseOrderId field.
     *
     * @return
     *  int containing the PurchaseOrderId field.
     */
    public int getPurchaseOrderId(){
        return mPurchaseOrderId;
    }


}
