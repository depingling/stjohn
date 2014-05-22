
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        WorkOrderData
 * Description:  This is a ValueObject class wrapping the database table CLW_WORK_ORDER.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.WorkOrderDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>WorkOrderData</code> is a ValueObject class wrapping of the database table CLW_WORK_ORDER.
 */
public class WorkOrderData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -1L;
    private int mWorkOrderId;// SQL type:NUMBER, not null
    private int mBusEntityId;// SQL type:NUMBER
    private String mTypeCd;// SQL type:VARCHAR2, not null
    private String mStatusCd;// SQL type:VARCHAR2, not null
    private String mShortDesc;// SQL type:VARCHAR2, not null
    private String mLongDesc;// SQL type:VARCHAR2
    private String mCategoryCd;// SQL type:VARCHAR2, not null
    private String mPriority;// SQL type:VARCHAR2, not null
    private int mEstimateHours;// SQL type:NUMBER
    private java.math.BigDecimal mEstimateCost;// SQL type:NUMBER
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private Date mRequestDate;// SQL type:DATE
    private Date mActualStartDate;// SQL type:DATE
    private Date mActualFinishDate;// SQL type:DATE
    private Date mActualStartTime;// SQL type:DATE
    private Date mActualFinishTime;// SQL type:DATE
    private String mActionCd;// SQL type:VARCHAR2
    private Date mEstimateStartDate;// SQL type:DATE
    private Date mEstimateFinishDate;// SQL type:DATE
    private Date mEstimateStartTime;// SQL type:DATE
    private Date mEstimateFinishTime;// SQL type:DATE
    private int mCostCenterId;// SQL type:NUMBER
    private String mWorkOrderNum;// SQL type:VARCHAR2
    private String mPoNumber;// SQL type:VARCHAR2
    private java.math.BigDecimal mNte;// SQL type:NUMBER
    private Date mReceivedDate;// SQL type:DATE
    private Date mReceivedTime;// SQL type:DATE

    /**
     * Constructor.
     */
    public WorkOrderData ()
    {
        mTypeCd = "";
        mStatusCd = "";
        mShortDesc = "";
        mLongDesc = "";
        mCategoryCd = "";
        mPriority = "";
        mAddBy = "";
        mModBy = "";
        mActionCd = "";
        mWorkOrderNum = "";
        mPoNumber = "";
    }

    /**
     * Constructor.
     */
    public WorkOrderData(int parm1, int parm2, String parm3, String parm4, String parm5, String parm6, String parm7, String parm8, int parm9, java.math.BigDecimal parm10, Date parm11, String parm12, Date parm13, String parm14, Date parm15, Date parm16, Date parm17, Date parm18, Date parm19, String parm20, Date parm21, Date parm22, Date parm23, Date parm24, int parm25, String parm26, String parm27, java.math.BigDecimal parm28, Date parm29, Date parm30)
    {
        mWorkOrderId = parm1;
        mBusEntityId = parm2;
        mTypeCd = parm3;
        mStatusCd = parm4;
        mShortDesc = parm5;
        mLongDesc = parm6;
        mCategoryCd = parm7;
        mPriority = parm8;
        mEstimateHours = parm9;
        mEstimateCost = parm10;
        mAddDate = parm11;
        mAddBy = parm12;
        mModDate = parm13;
        mModBy = parm14;
        mRequestDate = parm15;
        mActualStartDate = parm16;
        mActualFinishDate = parm17;
        mActualStartTime = parm18;
        mActualFinishTime = parm19;
        mActionCd = parm20;
        mEstimateStartDate = parm21;
        mEstimateFinishDate = parm22;
        mEstimateStartTime = parm23;
        mEstimateFinishTime = parm24;
        mCostCenterId = parm25;
        mWorkOrderNum = parm26;
        mPoNumber = parm27;
        mNte = parm28;
        mReceivedDate = parm29;
        mReceivedTime = parm30;
        
    }

    /**
     * Creates a new WorkOrderData
     *
     * @return
     *  Newly initialized WorkOrderData object.
     */
    public static WorkOrderData createValue ()
    {
        WorkOrderData valueData = new WorkOrderData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this WorkOrderData object
     */
    public String toString()
    {
        return "[" + "WorkOrderId=" + mWorkOrderId + ", BusEntityId=" + mBusEntityId + ", TypeCd=" + mTypeCd + ", StatusCd=" + mStatusCd + ", ShortDesc=" + mShortDesc + ", LongDesc=" + mLongDesc + ", CategoryCd=" + mCategoryCd + ", Priority=" + mPriority + ", EstimateHours=" + mEstimateHours + ", EstimateCost=" + mEstimateCost + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", RequestDate=" + mRequestDate + ", ActualStartDate=" + mActualStartDate + ", ActualFinishDate=" + mActualFinishDate + ", ActualStartTime=" + mActualStartTime + ", ActualFinishTime=" + mActualFinishTime + ", ActionCd=" + mActionCd + ", EstimateStartDate=" + mEstimateStartDate + ", EstimateFinishDate=" + mEstimateFinishDate + ", EstimateStartTime=" + mEstimateStartTime + ", EstimateFinishTime=" + mEstimateFinishTime + ", CostCenterId=" + mCostCenterId + ", WorkOrderNum=" + mWorkOrderNum + ", PoNumber=" + mPoNumber + ", Nte=" + mNte + ", ReceivedDate=" + mReceivedDate + ", ReceivedTime=" + mReceivedTime + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("WorkOrder");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mWorkOrderId));

        node =  doc.createElement("BusEntityId");
        node.appendChild(doc.createTextNode(String.valueOf(mBusEntityId)));
        root.appendChild(node);

        node =  doc.createElement("TypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mTypeCd)));
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

        node =  doc.createElement("CategoryCd");
        node.appendChild(doc.createTextNode(String.valueOf(mCategoryCd)));
        root.appendChild(node);

        node =  doc.createElement("Priority");
        node.appendChild(doc.createTextNode(String.valueOf(mPriority)));
        root.appendChild(node);

        node =  doc.createElement("EstimateHours");
        node.appendChild(doc.createTextNode(String.valueOf(mEstimateHours)));
        root.appendChild(node);

        node =  doc.createElement("EstimateCost");
        node.appendChild(doc.createTextNode(String.valueOf(mEstimateCost)));
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

        node =  doc.createElement("RequestDate");
        node.appendChild(doc.createTextNode(String.valueOf(mRequestDate)));
        root.appendChild(node);

        node =  doc.createElement("ActualStartDate");
        node.appendChild(doc.createTextNode(String.valueOf(mActualStartDate)));
        root.appendChild(node);

        node =  doc.createElement("ActualFinishDate");
        node.appendChild(doc.createTextNode(String.valueOf(mActualFinishDate)));
        root.appendChild(node);

        node =  doc.createElement("ActualStartTime");
        node.appendChild(doc.createTextNode(String.valueOf(mActualStartTime)));
        root.appendChild(node);

        node =  doc.createElement("ActualFinishTime");
        node.appendChild(doc.createTextNode(String.valueOf(mActualFinishTime)));
        root.appendChild(node);

        node =  doc.createElement("ActionCd");
        node.appendChild(doc.createTextNode(String.valueOf(mActionCd)));
        root.appendChild(node);

        node =  doc.createElement("EstimateStartDate");
        node.appendChild(doc.createTextNode(String.valueOf(mEstimateStartDate)));
        root.appendChild(node);

        node =  doc.createElement("EstimateFinishDate");
        node.appendChild(doc.createTextNode(String.valueOf(mEstimateFinishDate)));
        root.appendChild(node);

        node =  doc.createElement("EstimateStartTime");
        node.appendChild(doc.createTextNode(String.valueOf(mEstimateStartTime)));
        root.appendChild(node);

        node =  doc.createElement("EstimateFinishTime");
        node.appendChild(doc.createTextNode(String.valueOf(mEstimateFinishTime)));
        root.appendChild(node);

        node =  doc.createElement("CostCenterId");
        node.appendChild(doc.createTextNode(String.valueOf(mCostCenterId)));
        root.appendChild(node);

        node =  doc.createElement("WorkOrderNum");
        node.appendChild(doc.createTextNode(String.valueOf(mWorkOrderNum)));
        root.appendChild(node);

        node =  doc.createElement("PoNumber");
        node.appendChild(doc.createTextNode(String.valueOf(mPoNumber)));
        root.appendChild(node);

        node =  doc.createElement("Nte");
        node.appendChild(doc.createTextNode(String.valueOf(mNte)));
        root.appendChild(node);

        node =  doc.createElement("ReceivedDate");
        node.appendChild(doc.createTextNode(String.valueOf(mReceivedDate)));
        root.appendChild(node);

        node =  doc.createElement("ReceivedTime");
        node.appendChild(doc.createTextNode(String.valueOf(mReceivedTime)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the WorkOrderId field is not cloned.
    *
    * @return WorkOrderData object
    */
    public Object clone(){
        WorkOrderData myClone = new WorkOrderData();
        
        myClone.mBusEntityId = mBusEntityId;
        
        myClone.mTypeCd = mTypeCd;
        
        myClone.mStatusCd = mStatusCd;
        
        myClone.mShortDesc = mShortDesc;
        
        myClone.mLongDesc = mLongDesc;
        
        myClone.mCategoryCd = mCategoryCd;
        
        myClone.mPriority = mPriority;
        
        myClone.mEstimateHours = mEstimateHours;
        
        myClone.mEstimateCost = mEstimateCost;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        if(mRequestDate != null){
                myClone.mRequestDate = (Date) mRequestDate.clone();
        }
        
        if(mActualStartDate != null){
                myClone.mActualStartDate = (Date) mActualStartDate.clone();
        }
        
        if(mActualFinishDate != null){
                myClone.mActualFinishDate = (Date) mActualFinishDate.clone();
        }
        
        if(mActualStartTime != null){
                myClone.mActualStartTime = (Date) mActualStartTime.clone();
        }
        
        if(mActualFinishTime != null){
                myClone.mActualFinishTime = (Date) mActualFinishTime.clone();
        }
        
        myClone.mActionCd = mActionCd;
        
        if(mEstimateStartDate != null){
                myClone.mEstimateStartDate = (Date) mEstimateStartDate.clone();
        }
        
        if(mEstimateFinishDate != null){
                myClone.mEstimateFinishDate = (Date) mEstimateFinishDate.clone();
        }
        
        if(mEstimateStartTime != null){
                myClone.mEstimateStartTime = (Date) mEstimateStartTime.clone();
        }
        
        if(mEstimateFinishTime != null){
                myClone.mEstimateFinishTime = (Date) mEstimateFinishTime.clone();
        }
        
        myClone.mCostCenterId = mCostCenterId;
        
        myClone.mWorkOrderNum = mWorkOrderNum;
        
        myClone.mPoNumber = mPoNumber;
        
        myClone.mNte = mNte;
        
        if(mReceivedDate != null){
                myClone.mReceivedDate = (Date) mReceivedDate.clone();
        }
        
        if(mReceivedTime != null){
                myClone.mReceivedTime = (Date) mReceivedTime.clone();
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

        if (WorkOrderDataAccess.WORK_ORDER_ID.equals(pFieldName)) {
            return getWorkOrderId();
        } else if (WorkOrderDataAccess.BUS_ENTITY_ID.equals(pFieldName)) {
            return getBusEntityId();
        } else if (WorkOrderDataAccess.TYPE_CD.equals(pFieldName)) {
            return getTypeCd();
        } else if (WorkOrderDataAccess.STATUS_CD.equals(pFieldName)) {
            return getStatusCd();
        } else if (WorkOrderDataAccess.SHORT_DESC.equals(pFieldName)) {
            return getShortDesc();
        } else if (WorkOrderDataAccess.LONG_DESC.equals(pFieldName)) {
            return getLongDesc();
        } else if (WorkOrderDataAccess.CATEGORY_CD.equals(pFieldName)) {
            return getCategoryCd();
        } else if (WorkOrderDataAccess.PRIORITY.equals(pFieldName)) {
            return getPriority();
        } else if (WorkOrderDataAccess.ESTIMATE_HOURS.equals(pFieldName)) {
            return getEstimateHours();
        } else if (WorkOrderDataAccess.ESTIMATE_COST.equals(pFieldName)) {
            return getEstimateCost();
        } else if (WorkOrderDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (WorkOrderDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (WorkOrderDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (WorkOrderDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (WorkOrderDataAccess.REQUEST_DATE.equals(pFieldName)) {
            return getRequestDate();
        } else if (WorkOrderDataAccess.ACTUAL_START_DATE.equals(pFieldName)) {
            return getActualStartDate();
        } else if (WorkOrderDataAccess.ACTUAL_FINISH_DATE.equals(pFieldName)) {
            return getActualFinishDate();
        } else if (WorkOrderDataAccess.ACTUAL_START_TIME.equals(pFieldName)) {
            return getActualStartTime();
        } else if (WorkOrderDataAccess.ACTUAL_FINISH_TIME.equals(pFieldName)) {
            return getActualFinishTime();
        } else if (WorkOrderDataAccess.ACTION_CD.equals(pFieldName)) {
            return getActionCd();
        } else if (WorkOrderDataAccess.ESTIMATE_START_DATE.equals(pFieldName)) {
            return getEstimateStartDate();
        } else if (WorkOrderDataAccess.ESTIMATE_FINISH_DATE.equals(pFieldName)) {
            return getEstimateFinishDate();
        } else if (WorkOrderDataAccess.ESTIMATE_START_TIME.equals(pFieldName)) {
            return getEstimateStartTime();
        } else if (WorkOrderDataAccess.ESTIMATE_FINISH_TIME.equals(pFieldName)) {
            return getEstimateFinishTime();
        } else if (WorkOrderDataAccess.COST_CENTER_ID.equals(pFieldName)) {
            return getCostCenterId();
        } else if (WorkOrderDataAccess.WORK_ORDER_NUM.equals(pFieldName)) {
            return getWorkOrderNum();
        } else if (WorkOrderDataAccess.PO_NUMBER.equals(pFieldName)) {
            return getPoNumber();
        } else if (WorkOrderDataAccess.NTE.equals(pFieldName)) {
            return getNte();
        } else if (WorkOrderDataAccess.RECEIVED_DATE.equals(pFieldName)) {
            return getReceivedDate();
        } else if (WorkOrderDataAccess.RECEIVED_TIME.equals(pFieldName)) {
            return getReceivedTime();
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
        return WorkOrderDataAccess.CLW_WORK_ORDER;
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
     * Sets the TypeCd field. This field is required to be set in the database.
     *
     * @param pTypeCd
     *  String to use to update the field.
     */
    public void setTypeCd(String pTypeCd){
        this.mTypeCd = pTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the TypeCd field.
     *
     * @return
     *  String containing the TypeCd field.
     */
    public String getTypeCd(){
        return mTypeCd;
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
     * Sets the ShortDesc field. This field is required to be set in the database.
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
     * Sets the CategoryCd field. This field is required to be set in the database.
     *
     * @param pCategoryCd
     *  String to use to update the field.
     */
    public void setCategoryCd(String pCategoryCd){
        this.mCategoryCd = pCategoryCd;
        setDirty(true);
    }
    /**
     * Retrieves the CategoryCd field.
     *
     * @return
     *  String containing the CategoryCd field.
     */
    public String getCategoryCd(){
        return mCategoryCd;
    }

    /**
     * Sets the Priority field. This field is required to be set in the database.
     *
     * @param pPriority
     *  String to use to update the field.
     */
    public void setPriority(String pPriority){
        this.mPriority = pPriority;
        setDirty(true);
    }
    /**
     * Retrieves the Priority field.
     *
     * @return
     *  String containing the Priority field.
     */
    public String getPriority(){
        return mPriority;
    }

    /**
     * Sets the EstimateHours field.
     *
     * @param pEstimateHours
     *  int to use to update the field.
     */
    public void setEstimateHours(int pEstimateHours){
        this.mEstimateHours = pEstimateHours;
        setDirty(true);
    }
    /**
     * Retrieves the EstimateHours field.
     *
     * @return
     *  int containing the EstimateHours field.
     */
    public int getEstimateHours(){
        return mEstimateHours;
    }

    /**
     * Sets the EstimateCost field.
     *
     * @param pEstimateCost
     *  java.math.BigDecimal to use to update the field.
     */
    public void setEstimateCost(java.math.BigDecimal pEstimateCost){
        this.mEstimateCost = pEstimateCost;
        setDirty(true);
    }
    /**
     * Retrieves the EstimateCost field.
     *
     * @return
     *  java.math.BigDecimal containing the EstimateCost field.
     */
    public java.math.BigDecimal getEstimateCost(){
        return mEstimateCost;
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
     * Sets the RequestDate field.
     *
     * @param pRequestDate
     *  Date to use to update the field.
     */
    public void setRequestDate(Date pRequestDate){
        this.mRequestDate = pRequestDate;
        setDirty(true);
    }
    /**
     * Retrieves the RequestDate field.
     *
     * @return
     *  Date containing the RequestDate field.
     */
    public Date getRequestDate(){
        return mRequestDate;
    }

    /**
     * Sets the ActualStartDate field.
     *
     * @param pActualStartDate
     *  Date to use to update the field.
     */
    public void setActualStartDate(Date pActualStartDate){
        this.mActualStartDate = pActualStartDate;
        setDirty(true);
    }
    /**
     * Retrieves the ActualStartDate field.
     *
     * @return
     *  Date containing the ActualStartDate field.
     */
    public Date getActualStartDate(){
        return mActualStartDate;
    }

    /**
     * Sets the ActualFinishDate field.
     *
     * @param pActualFinishDate
     *  Date to use to update the field.
     */
    public void setActualFinishDate(Date pActualFinishDate){
        this.mActualFinishDate = pActualFinishDate;
        setDirty(true);
    }
    /**
     * Retrieves the ActualFinishDate field.
     *
     * @return
     *  Date containing the ActualFinishDate field.
     */
    public Date getActualFinishDate(){
        return mActualFinishDate;
    }

    /**
     * Sets the ActualStartTime field.
     *
     * @param pActualStartTime
     *  Date to use to update the field.
     */
    public void setActualStartTime(Date pActualStartTime){
        this.mActualStartTime = pActualStartTime;
        setDirty(true);
    }
    /**
     * Retrieves the ActualStartTime field.
     *
     * @return
     *  Date containing the ActualStartTime field.
     */
    public Date getActualStartTime(){
        return mActualStartTime;
    }

    /**
     * Sets the ActualFinishTime field.
     *
     * @param pActualFinishTime
     *  Date to use to update the field.
     */
    public void setActualFinishTime(Date pActualFinishTime){
        this.mActualFinishTime = pActualFinishTime;
        setDirty(true);
    }
    /**
     * Retrieves the ActualFinishTime field.
     *
     * @return
     *  Date containing the ActualFinishTime field.
     */
    public Date getActualFinishTime(){
        return mActualFinishTime;
    }

    /**
     * Sets the ActionCd field.
     *
     * @param pActionCd
     *  String to use to update the field.
     */
    public void setActionCd(String pActionCd){
        this.mActionCd = pActionCd;
        setDirty(true);
    }
    /**
     * Retrieves the ActionCd field.
     *
     * @return
     *  String containing the ActionCd field.
     */
    public String getActionCd(){
        return mActionCd;
    }

    /**
     * Sets the EstimateStartDate field.
     *
     * @param pEstimateStartDate
     *  Date to use to update the field.
     */
    public void setEstimateStartDate(Date pEstimateStartDate){
        this.mEstimateStartDate = pEstimateStartDate;
        setDirty(true);
    }
    /**
     * Retrieves the EstimateStartDate field.
     *
     * @return
     *  Date containing the EstimateStartDate field.
     */
    public Date getEstimateStartDate(){
        return mEstimateStartDate;
    }

    /**
     * Sets the EstimateFinishDate field.
     *
     * @param pEstimateFinishDate
     *  Date to use to update the field.
     */
    public void setEstimateFinishDate(Date pEstimateFinishDate){
        this.mEstimateFinishDate = pEstimateFinishDate;
        setDirty(true);
    }
    /**
     * Retrieves the EstimateFinishDate field.
     *
     * @return
     *  Date containing the EstimateFinishDate field.
     */
    public Date getEstimateFinishDate(){
        return mEstimateFinishDate;
    }

    /**
     * Sets the EstimateStartTime field.
     *
     * @param pEstimateStartTime
     *  Date to use to update the field.
     */
    public void setEstimateStartTime(Date pEstimateStartTime){
        this.mEstimateStartTime = pEstimateStartTime;
        setDirty(true);
    }
    /**
     * Retrieves the EstimateStartTime field.
     *
     * @return
     *  Date containing the EstimateStartTime field.
     */
    public Date getEstimateStartTime(){
        return mEstimateStartTime;
    }

    /**
     * Sets the EstimateFinishTime field.
     *
     * @param pEstimateFinishTime
     *  Date to use to update the field.
     */
    public void setEstimateFinishTime(Date pEstimateFinishTime){
        this.mEstimateFinishTime = pEstimateFinishTime;
        setDirty(true);
    }
    /**
     * Retrieves the EstimateFinishTime field.
     *
     * @return
     *  Date containing the EstimateFinishTime field.
     */
    public Date getEstimateFinishTime(){
        return mEstimateFinishTime;
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
     * Sets the WorkOrderNum field.
     *
     * @param pWorkOrderNum
     *  String to use to update the field.
     */
    public void setWorkOrderNum(String pWorkOrderNum){
        this.mWorkOrderNum = pWorkOrderNum;
        setDirty(true);
    }
    /**
     * Retrieves the WorkOrderNum field.
     *
     * @return
     *  String containing the WorkOrderNum field.
     */
    public String getWorkOrderNum(){
        return mWorkOrderNum;
    }

    /**
     * Sets the PoNumber field.
     *
     * @param pPoNumber
     *  String to use to update the field.
     */
    public void setPoNumber(String pPoNumber){
        this.mPoNumber = pPoNumber;
        setDirty(true);
    }
    /**
     * Retrieves the PoNumber field.
     *
     * @return
     *  String containing the PoNumber field.
     */
    public String getPoNumber(){
        return mPoNumber;
    }

    /**
     * Sets the Nte field.
     *
     * @param pNte
     *  java.math.BigDecimal to use to update the field.
     */
    public void setNte(java.math.BigDecimal pNte){
        this.mNte = pNte;
        setDirty(true);
    }
    /**
     * Retrieves the Nte field.
     *
     * @return
     *  java.math.BigDecimal containing the Nte field.
     */
    public java.math.BigDecimal getNte(){
        return mNte;
    }

    /**
     * Sets the ReceivedDate field.
     *
     * @param pReceivedDate
     *  Date to use to update the field.
     */
    public void setReceivedDate(Date pReceivedDate){
        this.mReceivedDate = pReceivedDate;
        setDirty(true);
    }
    /**
     * Retrieves the ReceivedDate field.
     *
     * @return
     *  Date containing the ReceivedDate field.
     */
    public Date getReceivedDate(){
        return mReceivedDate;
    }

    /**
     * Sets the ReceivedTime field.
     *
     * @param pReceivedTime
     *  Date to use to update the field.
     */
    public void setReceivedTime(Date pReceivedTime){
        this.mReceivedTime = pReceivedTime;
        setDirty(true);
    }
    /**
     * Retrieves the ReceivedTime field.
     *
     * @return
     *  Date containing the ReceivedTime field.
     */
    public Date getReceivedTime(){
        return mReceivedTime;
    }


}
