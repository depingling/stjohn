
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        WorkOrderDetailData
 * Description:  This is a ValueObject class wrapping the database table CLW_WORK_ORDER_DETAIL.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.WorkOrderDetailDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>WorkOrderDetailData</code> is a ValueObject class wrapping of the database table CLW_WORK_ORDER_DETAIL.
 */
public class WorkOrderDetailData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 1L;
    private int mWorkOrderDetailId;// SQL type:NUMBER, not null
    private int mWorkOrderId;// SQL type:NUMBER, not null
    private int mLineNum;// SQL type:NUMBER, not null
    private String mStatusCd;// SQL type:VARCHAR2, not null
    private String mPaymentTypeCd;// SQL type:VARCHAR2, not null
    private String mPartNumber;// SQL type:VARCHAR2
    private String mShortDesc;// SQL type:VARCHAR2
    private java.math.BigDecimal mPartPrice;// SQL type:NUMBER, not null
    private int mQuantity;// SQL type:NUMBER, not null
    private java.math.BigDecimal mLabor;// SQL type:NUMBER
    private java.math.BigDecimal mTravel;// SQL type:NUMBER
    private String mComments;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public WorkOrderDetailData ()
    {
        mStatusCd = "";
        mPaymentTypeCd = "";
        mPartNumber = "";
        mShortDesc = "";
        mComments = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public WorkOrderDetailData(int parm1, int parm2, int parm3, String parm4, String parm5, String parm6, String parm7, java.math.BigDecimal parm8, int parm9, java.math.BigDecimal parm10, java.math.BigDecimal parm11, String parm12, Date parm13, String parm14, Date parm15, String parm16)
    {
        mWorkOrderDetailId = parm1;
        mWorkOrderId = parm2;
        mLineNum = parm3;
        mStatusCd = parm4;
        mPaymentTypeCd = parm5;
        mPartNumber = parm6;
        mShortDesc = parm7;
        mPartPrice = parm8;
        mQuantity = parm9;
        mLabor = parm10;
        mTravel = parm11;
        mComments = parm12;
        mAddDate = parm13;
        mAddBy = parm14;
        mModDate = parm15;
        mModBy = parm16;
        
    }

    /**
     * Creates a new WorkOrderDetailData
     *
     * @return
     *  Newly initialized WorkOrderDetailData object.
     */
    public static WorkOrderDetailData createValue ()
    {
        WorkOrderDetailData valueData = new WorkOrderDetailData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this WorkOrderDetailData object
     */
    public String toString()
    {
        return "[" + "WorkOrderDetailId=" + mWorkOrderDetailId + ", WorkOrderId=" + mWorkOrderId + ", LineNum=" + mLineNum + ", StatusCd=" + mStatusCd + ", PaymentTypeCd=" + mPaymentTypeCd + ", PartNumber=" + mPartNumber + ", ShortDesc=" + mShortDesc + ", PartPrice=" + mPartPrice + ", Quantity=" + mQuantity + ", Labor=" + mLabor + ", Travel=" + mTravel + ", Comments=" + mComments + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("WorkOrderDetail");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mWorkOrderDetailId));

        node =  doc.createElement("WorkOrderId");
        node.appendChild(doc.createTextNode(String.valueOf(mWorkOrderId)));
        root.appendChild(node);

        node =  doc.createElement("LineNum");
        node.appendChild(doc.createTextNode(String.valueOf(mLineNum)));
        root.appendChild(node);

        node =  doc.createElement("StatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mStatusCd)));
        root.appendChild(node);

        node =  doc.createElement("PaymentTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mPaymentTypeCd)));
        root.appendChild(node);

        node =  doc.createElement("PartNumber");
        node.appendChild(doc.createTextNode(String.valueOf(mPartNumber)));
        root.appendChild(node);

        node =  doc.createElement("ShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mShortDesc)));
        root.appendChild(node);

        node =  doc.createElement("PartPrice");
        node.appendChild(doc.createTextNode(String.valueOf(mPartPrice)));
        root.appendChild(node);

        node =  doc.createElement("Quantity");
        node.appendChild(doc.createTextNode(String.valueOf(mQuantity)));
        root.appendChild(node);

        node =  doc.createElement("Labor");
        node.appendChild(doc.createTextNode(String.valueOf(mLabor)));
        root.appendChild(node);

        node =  doc.createElement("Travel");
        node.appendChild(doc.createTextNode(String.valueOf(mTravel)));
        root.appendChild(node);

        node =  doc.createElement("Comments");
        node.appendChild(doc.createTextNode(String.valueOf(mComments)));
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

        return root;
    }

    /**
    * creates a clone of this object, the WorkOrderDetailId field is not cloned.
    *
    * @return WorkOrderDetailData object
    */
    public Object clone(){
        WorkOrderDetailData myClone = new WorkOrderDetailData();
        
        myClone.mWorkOrderId = mWorkOrderId;
        
        myClone.mLineNum = mLineNum;
        
        myClone.mStatusCd = mStatusCd;
        
        myClone.mPaymentTypeCd = mPaymentTypeCd;
        
        myClone.mPartNumber = mPartNumber;
        
        myClone.mShortDesc = mShortDesc;
        
        myClone.mPartPrice = mPartPrice;
        
        myClone.mQuantity = mQuantity;
        
        myClone.mLabor = mLabor;
        
        myClone.mTravel = mTravel;
        
        myClone.mComments = mComments;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (WorkOrderDetailDataAccess.WORK_ORDER_DETAIL_ID.equals(pFieldName)) {
            return getWorkOrderDetailId();
        } else if (WorkOrderDetailDataAccess.WORK_ORDER_ID.equals(pFieldName)) {
            return getWorkOrderId();
        } else if (WorkOrderDetailDataAccess.LINE_NUM.equals(pFieldName)) {
            return getLineNum();
        } else if (WorkOrderDetailDataAccess.STATUS_CD.equals(pFieldName)) {
            return getStatusCd();
        } else if (WorkOrderDetailDataAccess.PAYMENT_TYPE_CD.equals(pFieldName)) {
            return getPaymentTypeCd();
        } else if (WorkOrderDetailDataAccess.PART_NUMBER.equals(pFieldName)) {
            return getPartNumber();
        } else if (WorkOrderDetailDataAccess.SHORT_DESC.equals(pFieldName)) {
            return getShortDesc();
        } else if (WorkOrderDetailDataAccess.PART_PRICE.equals(pFieldName)) {
            return getPartPrice();
        } else if (WorkOrderDetailDataAccess.QUANTITY.equals(pFieldName)) {
            return getQuantity();
        } else if (WorkOrderDetailDataAccess.LABOR.equals(pFieldName)) {
            return getLabor();
        } else if (WorkOrderDetailDataAccess.TRAVEL.equals(pFieldName)) {
            return getTravel();
        } else if (WorkOrderDetailDataAccess.COMMENTS.equals(pFieldName)) {
            return getComments();
        } else if (WorkOrderDetailDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (WorkOrderDetailDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (WorkOrderDetailDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (WorkOrderDetailDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
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
        return WorkOrderDetailDataAccess.CLW_WORK_ORDER_DETAIL;
    }

    
    /**
     * Sets the WorkOrderDetailId field. This field is required to be set in the database.
     *
     * @param pWorkOrderDetailId
     *  int to use to update the field.
     */
    public void setWorkOrderDetailId(int pWorkOrderDetailId){
        this.mWorkOrderDetailId = pWorkOrderDetailId;
        setDirty(true);
    }
    /**
     * Retrieves the WorkOrderDetailId field.
     *
     * @return
     *  int containing the WorkOrderDetailId field.
     */
    public int getWorkOrderDetailId(){
        return mWorkOrderDetailId;
    }

    /**
     * Sets the WorkOrderId field. This field is required to be set in the database.
     *
     * @param pWorkOrderId
     *  int to use to update the field.
     */
    public void setWorkOrderId(int pWorkOrderId){
        this.mWorkOrderId = pWorkOrderId;
        setDirty(true);
    }
    /**
     * Retrieves the WorkOrderId field.
     *
     * @return
     *  int containing the WorkOrderId field.
     */
    public int getWorkOrderId(){
        return mWorkOrderId;
    }

    /**
     * Sets the LineNum field. This field is required to be set in the database.
     *
     * @param pLineNum
     *  int to use to update the field.
     */
    public void setLineNum(int pLineNum){
        this.mLineNum = pLineNum;
        setDirty(true);
    }
    /**
     * Retrieves the LineNum field.
     *
     * @return
     *  int containing the LineNum field.
     */
    public int getLineNum(){
        return mLineNum;
    }

    /**
     * Sets the StatusCd field. This field is required to be set in the database.
     *
     * @param pStatusCd
     *  String to use to update the field.
     */
    public void setStatusCd(String pStatusCd){
        this.mStatusCd = pStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the StatusCd field.
     *
     * @return
     *  String containing the StatusCd field.
     */
    public String getStatusCd(){
        return mStatusCd;
    }

    /**
     * Sets the PaymentTypeCd field. This field is required to be set in the database.
     *
     * @param pPaymentTypeCd
     *  String to use to update the field.
     */
    public void setPaymentTypeCd(String pPaymentTypeCd){
        this.mPaymentTypeCd = pPaymentTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the PaymentTypeCd field.
     *
     * @return
     *  String containing the PaymentTypeCd field.
     */
    public String getPaymentTypeCd(){
        return mPaymentTypeCd;
    }

    /**
     * Sets the PartNumber field.
     *
     * @param pPartNumber
     *  String to use to update the field.
     */
    public void setPartNumber(String pPartNumber){
        this.mPartNumber = pPartNumber;
        setDirty(true);
    }
    /**
     * Retrieves the PartNumber field.
     *
     * @return
     *  String containing the PartNumber field.
     */
    public String getPartNumber(){
        return mPartNumber;
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
     * Sets the PartPrice field. This field is required to be set in the database.
     *
     * @param pPartPrice
     *  java.math.BigDecimal to use to update the field.
     */
    public void setPartPrice(java.math.BigDecimal pPartPrice){
        this.mPartPrice = pPartPrice;
        setDirty(true);
    }
    /**
     * Retrieves the PartPrice field.
     *
     * @return
     *  java.math.BigDecimal containing the PartPrice field.
     */
    public java.math.BigDecimal getPartPrice(){
        return mPartPrice;
    }

    /**
     * Sets the Quantity field. This field is required to be set in the database.
     *
     * @param pQuantity
     *  int to use to update the field.
     */
    public void setQuantity(int pQuantity){
        this.mQuantity = pQuantity;
        setDirty(true);
    }
    /**
     * Retrieves the Quantity field.
     *
     * @return
     *  int containing the Quantity field.
     */
    public int getQuantity(){
        return mQuantity;
    }

    /**
     * Sets the Labor field.
     *
     * @param pLabor
     *  java.math.BigDecimal to use to update the field.
     */
    public void setLabor(java.math.BigDecimal pLabor){
        this.mLabor = pLabor;
        setDirty(true);
    }
    /**
     * Retrieves the Labor field.
     *
     * @return
     *  java.math.BigDecimal containing the Labor field.
     */
    public java.math.BigDecimal getLabor(){
        return mLabor;
    }

    /**
     * Sets the Travel field.
     *
     * @param pTravel
     *  java.math.BigDecimal to use to update the field.
     */
    public void setTravel(java.math.BigDecimal pTravel){
        this.mTravel = pTravel;
        setDirty(true);
    }
    /**
     * Retrieves the Travel field.
     *
     * @return
     *  java.math.BigDecimal containing the Travel field.
     */
    public java.math.BigDecimal getTravel(){
        return mTravel;
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


}
