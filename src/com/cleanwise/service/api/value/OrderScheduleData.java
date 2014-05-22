
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        OrderScheduleData
 * Description:  This is a ValueObject class wrapping the database table CLW_ORDER_SCHEDULE.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.OrderScheduleDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>OrderScheduleData</code> is a ValueObject class wrapping of the database table CLW_ORDER_SCHEDULE.
 */
public class OrderScheduleData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 2696435617262120094L;
    private int mOrderScheduleId;// SQL type:NUMBER, not null
    private String mRecordStatusCd;// SQL type:VARCHAR2, not null
    private int mOrderGuideId;// SQL type:NUMBER
    private int mUserId;// SQL type:NUMBER, not null
    private int mBusEntityId;// SQL type:NUMBER, not null
    private String mOrderScheduleCd;// SQL type:VARCHAR2, not null
    private String mOrderScheduleRuleCd;// SQL type:VARCHAR2, not null
    private int mCycle;// SQL type:NUMBER
    private Date mEffDate;// SQL type:DATE, not null
    private Date mExpDate;// SQL type:DATE
    private String mCcEmail;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private int mWorkOrderId;// SQL type:NUMBER

    /**
     * Constructor.
     */
    public OrderScheduleData ()
    {
        mRecordStatusCd = "";
        mOrderScheduleCd = "";
        mOrderScheduleRuleCd = "";
        mCcEmail = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public OrderScheduleData(int parm1, String parm2, int parm3, int parm4, int parm5, String parm6, String parm7, int parm8, Date parm9, Date parm10, String parm11, Date parm12, String parm13, Date parm14, String parm15, int parm16)
    {
        mOrderScheduleId = parm1;
        mRecordStatusCd = parm2;
        mOrderGuideId = parm3;
        mUserId = parm4;
        mBusEntityId = parm5;
        mOrderScheduleCd = parm6;
        mOrderScheduleRuleCd = parm7;
        mCycle = parm8;
        mEffDate = parm9;
        mExpDate = parm10;
        mCcEmail = parm11;
        mAddDate = parm12;
        mAddBy = parm13;
        mModDate = parm14;
        mModBy = parm15;
        mWorkOrderId = parm16;
        
    }

    /**
     * Creates a new OrderScheduleData
     *
     * @return
     *  Newly initialized OrderScheduleData object.
     */
    public static OrderScheduleData createValue ()
    {
        OrderScheduleData valueData = new OrderScheduleData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this OrderScheduleData object
     */
    public String toString()
    {
        return "[" + "OrderScheduleId=" + mOrderScheduleId + ", RecordStatusCd=" + mRecordStatusCd + ", OrderGuideId=" + mOrderGuideId + ", UserId=" + mUserId + ", BusEntityId=" + mBusEntityId + ", OrderScheduleCd=" + mOrderScheduleCd + ", OrderScheduleRuleCd=" + mOrderScheduleRuleCd + ", Cycle=" + mCycle + ", EffDate=" + mEffDate + ", ExpDate=" + mExpDate + ", CcEmail=" + mCcEmail + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", WorkOrderId=" + mWorkOrderId + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("OrderSchedule");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mOrderScheduleId));

        node =  doc.createElement("RecordStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mRecordStatusCd)));
        root.appendChild(node);

        node =  doc.createElement("OrderGuideId");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderGuideId)));
        root.appendChild(node);

        node =  doc.createElement("UserId");
        node.appendChild(doc.createTextNode(String.valueOf(mUserId)));
        root.appendChild(node);

        node =  doc.createElement("BusEntityId");
        node.appendChild(doc.createTextNode(String.valueOf(mBusEntityId)));
        root.appendChild(node);

        node =  doc.createElement("OrderScheduleCd");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderScheduleCd)));
        root.appendChild(node);

        node =  doc.createElement("OrderScheduleRuleCd");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderScheduleRuleCd)));
        root.appendChild(node);

        node =  doc.createElement("Cycle");
        node.appendChild(doc.createTextNode(String.valueOf(mCycle)));
        root.appendChild(node);

        node =  doc.createElement("EffDate");
        node.appendChild(doc.createTextNode(String.valueOf(mEffDate)));
        root.appendChild(node);

        node =  doc.createElement("ExpDate");
        node.appendChild(doc.createTextNode(String.valueOf(mExpDate)));
        root.appendChild(node);

        node =  doc.createElement("CcEmail");
        node.appendChild(doc.createTextNode(String.valueOf(mCcEmail)));
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

        node =  doc.createElement("WorkOrderId");
        node.appendChild(doc.createTextNode(String.valueOf(mWorkOrderId)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the OrderScheduleId field is not cloned.
    *
    * @return OrderScheduleData object
    */
    public Object clone(){
        OrderScheduleData myClone = new OrderScheduleData();
        
        myClone.mRecordStatusCd = mRecordStatusCd;
        
        myClone.mOrderGuideId = mOrderGuideId;
        
        myClone.mUserId = mUserId;
        
        myClone.mBusEntityId = mBusEntityId;
        
        myClone.mOrderScheduleCd = mOrderScheduleCd;
        
        myClone.mOrderScheduleRuleCd = mOrderScheduleRuleCd;
        
        myClone.mCycle = mCycle;
        
        if(mEffDate != null){
                myClone.mEffDate = (Date) mEffDate.clone();
        }
        
        if(mExpDate != null){
                myClone.mExpDate = (Date) mExpDate.clone();
        }
        
        myClone.mCcEmail = mCcEmail;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        myClone.mWorkOrderId = mWorkOrderId;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (OrderScheduleDataAccess.ORDER_SCHEDULE_ID.equals(pFieldName)) {
            return getOrderScheduleId();
        } else if (OrderScheduleDataAccess.RECORD_STATUS_CD.equals(pFieldName)) {
            return getRecordStatusCd();
        } else if (OrderScheduleDataAccess.ORDER_GUIDE_ID.equals(pFieldName)) {
            return getOrderGuideId();
        } else if (OrderScheduleDataAccess.USER_ID.equals(pFieldName)) {
            return getUserId();
        } else if (OrderScheduleDataAccess.BUS_ENTITY_ID.equals(pFieldName)) {
            return getBusEntityId();
        } else if (OrderScheduleDataAccess.ORDER_SCHEDULE_CD.equals(pFieldName)) {
            return getOrderScheduleCd();
        } else if (OrderScheduleDataAccess.ORDER_SCHEDULE_RULE_CD.equals(pFieldName)) {
            return getOrderScheduleRuleCd();
        } else if (OrderScheduleDataAccess.CYCLE.equals(pFieldName)) {
            return getCycle();
        } else if (OrderScheduleDataAccess.EFF_DATE.equals(pFieldName)) {
            return getEffDate();
        } else if (OrderScheduleDataAccess.EXP_DATE.equals(pFieldName)) {
            return getExpDate();
        } else if (OrderScheduleDataAccess.CC_EMAIL.equals(pFieldName)) {
            return getCcEmail();
        } else if (OrderScheduleDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (OrderScheduleDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (OrderScheduleDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (OrderScheduleDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (OrderScheduleDataAccess.WORK_ORDER_ID.equals(pFieldName)) {
            return getWorkOrderId();
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
        return OrderScheduleDataAccess.CLW_ORDER_SCHEDULE;
    }

    
    /**
     * Sets the OrderScheduleId field. This field is required to be set in the database.
     *
     * @param pOrderScheduleId
     *  int to use to update the field.
     */
    public void setOrderScheduleId(int pOrderScheduleId){
        this.mOrderScheduleId = pOrderScheduleId;
        setDirty(true);
    }
    /**
     * Retrieves the OrderScheduleId field.
     *
     * @return
     *  int containing the OrderScheduleId field.
     */
    public int getOrderScheduleId(){
        return mOrderScheduleId;
    }

    /**
     * Sets the RecordStatusCd field. This field is required to be set in the database.
     *
     * @param pRecordStatusCd
     *  String to use to update the field.
     */
    public void setRecordStatusCd(String pRecordStatusCd){
        this.mRecordStatusCd = pRecordStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the RecordStatusCd field.
     *
     * @return
     *  String containing the RecordStatusCd field.
     */
    public String getRecordStatusCd(){
        return mRecordStatusCd;
    }

    /**
     * Sets the OrderGuideId field.
     *
     * @param pOrderGuideId
     *  int to use to update the field.
     */
    public void setOrderGuideId(int pOrderGuideId){
        this.mOrderGuideId = pOrderGuideId;
        setDirty(true);
    }
    /**
     * Retrieves the OrderGuideId field.
     *
     * @return
     *  int containing the OrderGuideId field.
     */
    public int getOrderGuideId(){
        return mOrderGuideId;
    }

    /**
     * Sets the UserId field. This field is required to be set in the database.
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
     * Sets the BusEntityId field. This field is required to be set in the database.
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
     * Sets the OrderScheduleCd field. This field is required to be set in the database.
     *
     * @param pOrderScheduleCd
     *  String to use to update the field.
     */
    public void setOrderScheduleCd(String pOrderScheduleCd){
        this.mOrderScheduleCd = pOrderScheduleCd;
        setDirty(true);
    }
    /**
     * Retrieves the OrderScheduleCd field.
     *
     * @return
     *  String containing the OrderScheduleCd field.
     */
    public String getOrderScheduleCd(){
        return mOrderScheduleCd;
    }

    /**
     * Sets the OrderScheduleRuleCd field. This field is required to be set in the database.
     *
     * @param pOrderScheduleRuleCd
     *  String to use to update the field.
     */
    public void setOrderScheduleRuleCd(String pOrderScheduleRuleCd){
        this.mOrderScheduleRuleCd = pOrderScheduleRuleCd;
        setDirty(true);
    }
    /**
     * Retrieves the OrderScheduleRuleCd field.
     *
     * @return
     *  String containing the OrderScheduleRuleCd field.
     */
    public String getOrderScheduleRuleCd(){
        return mOrderScheduleRuleCd;
    }

    /**
     * Sets the Cycle field.
     *
     * @param pCycle
     *  int to use to update the field.
     */
    public void setCycle(int pCycle){
        this.mCycle = pCycle;
        setDirty(true);
    }
    /**
     * Retrieves the Cycle field.
     *
     * @return
     *  int containing the Cycle field.
     */
    public int getCycle(){
        return mCycle;
    }

    /**
     * Sets the EffDate field. This field is required to be set in the database.
     *
     * @param pEffDate
     *  Date to use to update the field.
     */
    public void setEffDate(Date pEffDate){
        this.mEffDate = pEffDate;
        setDirty(true);
    }
    /**
     * Retrieves the EffDate field.
     *
     * @return
     *  Date containing the EffDate field.
     */
    public Date getEffDate(){
        return mEffDate;
    }

    /**
     * Sets the ExpDate field.
     *
     * @param pExpDate
     *  Date to use to update the field.
     */
    public void setExpDate(Date pExpDate){
        this.mExpDate = pExpDate;
        setDirty(true);
    }
    /**
     * Retrieves the ExpDate field.
     *
     * @return
     *  Date containing the ExpDate field.
     */
    public Date getExpDate(){
        return mExpDate;
    }

    /**
     * Sets the CcEmail field.
     *
     * @param pCcEmail
     *  String to use to update the field.
     */
    public void setCcEmail(String pCcEmail){
        this.mCcEmail = pCcEmail;
        setDirty(true);
    }
    /**
     * Retrieves the CcEmail field.
     *
     * @return
     *  String containing the CcEmail field.
     */
    public String getCcEmail(){
        return mCcEmail;
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
     * Sets the WorkOrderId field.
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


}
