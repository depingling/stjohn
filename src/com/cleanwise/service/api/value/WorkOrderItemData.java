
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        WorkOrderItemData
 * Description:  This is a ValueObject class wrapping the database table CLW_WORK_ORDER_ITEM.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.WorkOrderItemDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>WorkOrderItemData</code> is a ValueObject class wrapping of the database table CLW_WORK_ORDER_ITEM.
 */
public class WorkOrderItemData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -1L;
    private int mWorkOrderItemId;// SQL type:NUMBER, not null
    private int mWorkOrderId;// SQL type:NUMBER, not null
    private int mWarrantyId;// SQL type:NUMBER
    private int mBusEntityId;// SQL type:NUMBER
    private String mStatusCd;// SQL type:VARCHAR2, not null
    private String mShortDesc;// SQL type:VARCHAR2
    private String mLongDesc;// SQL type:VARCHAR2
    private int mSequence;// SQL type:NUMBER
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private java.math.BigDecimal mEstimateLabor;// SQL type:NUMBER
    private java.math.BigDecimal mEstimatePart;// SQL type:NUMBER
    private java.math.BigDecimal mActualLabor;// SQL type:NUMBER
    private java.math.BigDecimal mActualPart;// SQL type:NUMBER

    /**
     * Constructor.
     */
    public WorkOrderItemData ()
    {
        mStatusCd = "";
        mShortDesc = "";
        mLongDesc = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public WorkOrderItemData(int parm1, int parm2, int parm3, int parm4, String parm5, String parm6, String parm7, int parm8, Date parm9, String parm10, Date parm11, String parm12, java.math.BigDecimal parm13, java.math.BigDecimal parm14, java.math.BigDecimal parm15, java.math.BigDecimal parm16)
    {
        mWorkOrderItemId = parm1;
        mWorkOrderId = parm2;
        mWarrantyId = parm3;
        mBusEntityId = parm4;
        mStatusCd = parm5;
        mShortDesc = parm6;
        mLongDesc = parm7;
        mSequence = parm8;
        mAddDate = parm9;
        mAddBy = parm10;
        mModDate = parm11;
        mModBy = parm12;
        mEstimateLabor = parm13;
        mEstimatePart = parm14;
        mActualLabor = parm15;
        mActualPart = parm16;
        
    }

    /**
     * Creates a new WorkOrderItemData
     *
     * @return
     *  Newly initialized WorkOrderItemData object.
     */
    public static WorkOrderItemData createValue ()
    {
        WorkOrderItemData valueData = new WorkOrderItemData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this WorkOrderItemData object
     */
    public String toString()
    {
        return "[" + "WorkOrderItemId=" + mWorkOrderItemId + ", WorkOrderId=" + mWorkOrderId + ", WarrantyId=" + mWarrantyId + ", BusEntityId=" + mBusEntityId + ", StatusCd=" + mStatusCd + ", ShortDesc=" + mShortDesc + ", LongDesc=" + mLongDesc + ", Sequence=" + mSequence + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", EstimateLabor=" + mEstimateLabor + ", EstimatePart=" + mEstimatePart + ", ActualLabor=" + mActualLabor + ", ActualPart=" + mActualPart + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("WorkOrderItem");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mWorkOrderItemId));

        node =  doc.createElement("WorkOrderId");
        node.appendChild(doc.createTextNode(String.valueOf(mWorkOrderId)));
        root.appendChild(node);

        node =  doc.createElement("WarrantyId");
        node.appendChild(doc.createTextNode(String.valueOf(mWarrantyId)));
        root.appendChild(node);

        node =  doc.createElement("BusEntityId");
        node.appendChild(doc.createTextNode(String.valueOf(mBusEntityId)));
        root.appendChild(node);

        node =  doc.createElement("StatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mStatusCd)));
        root.appendChild(node);

        node =  doc.createElement("ShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mShortDesc)));
        root.appendChild(node);

        node =  doc.createElement("LongDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mLongDesc)));
        root.appendChild(node);

        node =  doc.createElement("Sequence");
        node.appendChild(doc.createTextNode(String.valueOf(mSequence)));
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

        node =  doc.createElement("EstimateLabor");
        node.appendChild(doc.createTextNode(String.valueOf(mEstimateLabor)));
        root.appendChild(node);

        node =  doc.createElement("EstimatePart");
        node.appendChild(doc.createTextNode(String.valueOf(mEstimatePart)));
        root.appendChild(node);

        node =  doc.createElement("ActualLabor");
        node.appendChild(doc.createTextNode(String.valueOf(mActualLabor)));
        root.appendChild(node);

        node =  doc.createElement("ActualPart");
        node.appendChild(doc.createTextNode(String.valueOf(mActualPart)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the WorkOrderItemId field is not cloned.
    *
    * @return WorkOrderItemData object
    */
    public Object clone(){
        WorkOrderItemData myClone = new WorkOrderItemData();
        
        myClone.mWorkOrderId = mWorkOrderId;
        
        myClone.mWarrantyId = mWarrantyId;
        
        myClone.mBusEntityId = mBusEntityId;
        
        myClone.mStatusCd = mStatusCd;
        
        myClone.mShortDesc = mShortDesc;
        
        myClone.mLongDesc = mLongDesc;
        
        myClone.mSequence = mSequence;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        myClone.mEstimateLabor = mEstimateLabor;
        
        myClone.mEstimatePart = mEstimatePart;
        
        myClone.mActualLabor = mActualLabor;
        
        myClone.mActualPart = mActualPart;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (WorkOrderItemDataAccess.WORK_ORDER_ITEM_ID.equals(pFieldName)) {
            return getWorkOrderItemId();
        } else if (WorkOrderItemDataAccess.WORK_ORDER_ID.equals(pFieldName)) {
            return getWorkOrderId();
        } else if (WorkOrderItemDataAccess.WARRANTY_ID.equals(pFieldName)) {
            return getWarrantyId();
        } else if (WorkOrderItemDataAccess.BUS_ENTITY_ID.equals(pFieldName)) {
            return getBusEntityId();
        } else if (WorkOrderItemDataAccess.STATUS_CD.equals(pFieldName)) {
            return getStatusCd();
        } else if (WorkOrderItemDataAccess.SHORT_DESC.equals(pFieldName)) {
            return getShortDesc();
        } else if (WorkOrderItemDataAccess.LONG_DESC.equals(pFieldName)) {
            return getLongDesc();
        } else if (WorkOrderItemDataAccess.SEQUENCE.equals(pFieldName)) {
            return getSequence();
        } else if (WorkOrderItemDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (WorkOrderItemDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (WorkOrderItemDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (WorkOrderItemDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (WorkOrderItemDataAccess.ESTIMATE_LABOR.equals(pFieldName)) {
            return getEstimateLabor();
        } else if (WorkOrderItemDataAccess.ESTIMATE_PART.equals(pFieldName)) {
            return getEstimatePart();
        } else if (WorkOrderItemDataAccess.ACTUAL_LABOR.equals(pFieldName)) {
            return getActualLabor();
        } else if (WorkOrderItemDataAccess.ACTUAL_PART.equals(pFieldName)) {
            return getActualPart();
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
        return WorkOrderItemDataAccess.CLW_WORK_ORDER_ITEM;
    }

    
    /**
     * Sets the WorkOrderItemId field. This field is required to be set in the database.
     *
     * @param pWorkOrderItemId
     *  int to use to update the field.
     */
    public void setWorkOrderItemId(int pWorkOrderItemId){
        this.mWorkOrderItemId = pWorkOrderItemId;
        setDirty(true);
    }
    /**
     * Retrieves the WorkOrderItemId field.
     *
     * @return
     *  int containing the WorkOrderItemId field.
     */
    public int getWorkOrderItemId(){
        return mWorkOrderItemId;
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
     * Sets the WarrantyId field.
     *
     * @param pWarrantyId
     *  int to use to update the field.
     */
    public void setWarrantyId(int pWarrantyId){
        this.mWarrantyId = pWarrantyId;
        setDirty(true);
    }
    /**
     * Retrieves the WarrantyId field.
     *
     * @return
     *  int containing the WarrantyId field.
     */
    public int getWarrantyId(){
        return mWarrantyId;
    }

    /**
     * Sets the BusEntityId field.
     *
     * @param pBusEntityId
     *  int to use to update the field.
     */
    public void setBusEntityId(int pBusEntityId){
        this.mBusEntityId = pBusEntityId;
        setDirty(true);
    }
    /**
     * Retrieves the BusEntityId field.
     *
     * @return
     *  int containing the BusEntityId field.
     */
    public int getBusEntityId(){
        return mBusEntityId;
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
     * Sets the LongDesc field.
     *
     * @param pLongDesc
     *  String to use to update the field.
     */
    public void setLongDesc(String pLongDesc){
        this.mLongDesc = pLongDesc;
        setDirty(true);
    }
    /**
     * Retrieves the LongDesc field.
     *
     * @return
     *  String containing the LongDesc field.
     */
    public String getLongDesc(){
        return mLongDesc;
    }

    /**
     * Sets the Sequence field.
     *
     * @param pSequence
     *  int to use to update the field.
     */
    public void setSequence(int pSequence){
        this.mSequence = pSequence;
        setDirty(true);
    }
    /**
     * Retrieves the Sequence field.
     *
     * @return
     *  int containing the Sequence field.
     */
    public int getSequence(){
        return mSequence;
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
     * Sets the EstimateLabor field.
     *
     * @param pEstimateLabor
     *  java.math.BigDecimal to use to update the field.
     */
    public void setEstimateLabor(java.math.BigDecimal pEstimateLabor){
        this.mEstimateLabor = pEstimateLabor;
        setDirty(true);
    }
    /**
     * Retrieves the EstimateLabor field.
     *
     * @return
     *  java.math.BigDecimal containing the EstimateLabor field.
     */
    public java.math.BigDecimal getEstimateLabor(){
        return mEstimateLabor;
    }

    /**
     * Sets the EstimatePart field.
     *
     * @param pEstimatePart
     *  java.math.BigDecimal to use to update the field.
     */
    public void setEstimatePart(java.math.BigDecimal pEstimatePart){
        this.mEstimatePart = pEstimatePart;
        setDirty(true);
    }
    /**
     * Retrieves the EstimatePart field.
     *
     * @return
     *  java.math.BigDecimal containing the EstimatePart field.
     */
    public java.math.BigDecimal getEstimatePart(){
        return mEstimatePart;
    }

    /**
     * Sets the ActualLabor field.
     *
     * @param pActualLabor
     *  java.math.BigDecimal to use to update the field.
     */
    public void setActualLabor(java.math.BigDecimal pActualLabor){
        this.mActualLabor = pActualLabor;
        setDirty(true);
    }
    /**
     * Retrieves the ActualLabor field.
     *
     * @return
     *  java.math.BigDecimal containing the ActualLabor field.
     */
    public java.math.BigDecimal getActualLabor(){
        return mActualLabor;
    }

    /**
     * Sets the ActualPart field.
     *
     * @param pActualPart
     *  java.math.BigDecimal to use to update the field.
     */
    public void setActualPart(java.math.BigDecimal pActualPart){
        this.mActualPart = pActualPart;
        setDirty(true);
    }
    /**
     * Retrieves the ActualPart field.
     *
     * @return
     *  java.math.BigDecimal containing the ActualPart field.
     */
    public java.math.BigDecimal getActualPart(){
        return mActualPart;
    }


}
