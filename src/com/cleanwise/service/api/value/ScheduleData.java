
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ScheduleData
 * Description:  This is a ValueObject class wrapping the database table CLW_SCHEDULE.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.ScheduleDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>ScheduleData</code> is a ValueObject class wrapping of the database table CLW_SCHEDULE.
 */
public class ScheduleData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -3921106488816676795L;
    private int mScheduleId;// SQL type:NUMBER, not null
    private String mShortDesc;// SQL type:VARCHAR2
    private int mBusEntityId;// SQL type:NUMBER
    private String mScheduleStatusCd;// SQL type:VARCHAR2, not null
    private String mScheduleTypeCd;// SQL type:VARCHAR2, not null
    private String mScheduleRuleCd;// SQL type:VARCHAR2, not null
    private int mCycle;// SQL type:NUMBER
    private Date mEffDate;// SQL type:DATE, not null
    private Date mExpDate;// SQL type:DATE
    private Date mLastProcessedDt;// SQL type:DATE
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public ScheduleData ()
    {
        mShortDesc = "";
        mScheduleStatusCd = "";
        mScheduleTypeCd = "";
        mScheduleRuleCd = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public ScheduleData(int parm1, String parm2, int parm3, String parm4, String parm5, String parm6, int parm7, Date parm8, Date parm9, Date parm10, Date parm11, String parm12, Date parm13, String parm14)
    {
        mScheduleId = parm1;
        mShortDesc = parm2;
        mBusEntityId = parm3;
        mScheduleStatusCd = parm4;
        mScheduleTypeCd = parm5;
        mScheduleRuleCd = parm6;
        mCycle = parm7;
        mEffDate = parm8;
        mExpDate = parm9;
        mLastProcessedDt = parm10;
        mAddDate = parm11;
        mAddBy = parm12;
        mModDate = parm13;
        mModBy = parm14;
        
    }

    /**
     * Creates a new ScheduleData
     *
     * @return
     *  Newly initialized ScheduleData object.
     */
    public static ScheduleData createValue ()
    {
        ScheduleData valueData = new ScheduleData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ScheduleData object
     */
    public String toString()
    {
        return "[" + "ScheduleId=" + mScheduleId + ", ShortDesc=" + mShortDesc + ", BusEntityId=" + mBusEntityId + ", ScheduleStatusCd=" + mScheduleStatusCd + ", ScheduleTypeCd=" + mScheduleTypeCd + ", ScheduleRuleCd=" + mScheduleRuleCd + ", Cycle=" + mCycle + ", EffDate=" + mEffDate + ", ExpDate=" + mExpDate + ", LastProcessedDt=" + mLastProcessedDt + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("Schedule");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mScheduleId));

        node =  doc.createElement("ShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mShortDesc)));
        root.appendChild(node);

        node =  doc.createElement("BusEntityId");
        node.appendChild(doc.createTextNode(String.valueOf(mBusEntityId)));
        root.appendChild(node);

        node =  doc.createElement("ScheduleStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mScheduleStatusCd)));
        root.appendChild(node);

        node =  doc.createElement("ScheduleTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mScheduleTypeCd)));
        root.appendChild(node);

        node =  doc.createElement("ScheduleRuleCd");
        node.appendChild(doc.createTextNode(String.valueOf(mScheduleRuleCd)));
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

        node =  doc.createElement("LastProcessedDt");
        node.appendChild(doc.createTextNode(String.valueOf(mLastProcessedDt)));
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
    * creates a clone of this object, the ScheduleId field is not cloned.
    *
    * @return ScheduleData object
    */
    public Object clone(){
        ScheduleData myClone = new ScheduleData();
        
        myClone.mShortDesc = mShortDesc;
        
        myClone.mBusEntityId = mBusEntityId;
        
        myClone.mScheduleStatusCd = mScheduleStatusCd;
        
        myClone.mScheduleTypeCd = mScheduleTypeCd;
        
        myClone.mScheduleRuleCd = mScheduleRuleCd;
        
        myClone.mCycle = mCycle;
        
        if(mEffDate != null){
                myClone.mEffDate = (Date) mEffDate.clone();
        }
        
        if(mExpDate != null){
                myClone.mExpDate = (Date) mExpDate.clone();
        }
        
        if(mLastProcessedDt != null){
                myClone.mLastProcessedDt = (Date) mLastProcessedDt.clone();
        }
        
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

        if (ScheduleDataAccess.SCHEDULE_ID.equals(pFieldName)) {
            return getScheduleId();
        } else if (ScheduleDataAccess.SHORT_DESC.equals(pFieldName)) {
            return getShortDesc();
        } else if (ScheduleDataAccess.BUS_ENTITY_ID.equals(pFieldName)) {
            return getBusEntityId();
        } else if (ScheduleDataAccess.SCHEDULE_STATUS_CD.equals(pFieldName)) {
            return getScheduleStatusCd();
        } else if (ScheduleDataAccess.SCHEDULE_TYPE_CD.equals(pFieldName)) {
            return getScheduleTypeCd();
        } else if (ScheduleDataAccess.SCHEDULE_RULE_CD.equals(pFieldName)) {
            return getScheduleRuleCd();
        } else if (ScheduleDataAccess.CYCLE.equals(pFieldName)) {
            return getCycle();
        } else if (ScheduleDataAccess.EFF_DATE.equals(pFieldName)) {
            return getEffDate();
        } else if (ScheduleDataAccess.EXP_DATE.equals(pFieldName)) {
            return getExpDate();
        } else if (ScheduleDataAccess.LAST_PROCESSED_DT.equals(pFieldName)) {
            return getLastProcessedDt();
        } else if (ScheduleDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (ScheduleDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (ScheduleDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (ScheduleDataAccess.MOD_BY.equals(pFieldName)) {
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
        return ScheduleDataAccess.CLW_SCHEDULE;
    }

    
    /**
     * Sets the ScheduleId field. This field is required to be set in the database.
     *
     * @param pScheduleId
     *  int to use to update the field.
     */
    public void setScheduleId(int pScheduleId){
        this.mScheduleId = pScheduleId;
        setDirty(true);
    }
    /**
     * Retrieves the ScheduleId field.
     *
     * @return
     *  int containing the ScheduleId field.
     */
    public int getScheduleId(){
        return mScheduleId;
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
     * Sets the ScheduleStatusCd field. This field is required to be set in the database.
     *
     * @param pScheduleStatusCd
     *  String to use to update the field.
     */
    public void setScheduleStatusCd(String pScheduleStatusCd){
        this.mScheduleStatusCd = pScheduleStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the ScheduleStatusCd field.
     *
     * @return
     *  String containing the ScheduleStatusCd field.
     */
    public String getScheduleStatusCd(){
        return mScheduleStatusCd;
    }

    /**
     * Sets the ScheduleTypeCd field. This field is required to be set in the database.
     *
     * @param pScheduleTypeCd
     *  String to use to update the field.
     */
    public void setScheduleTypeCd(String pScheduleTypeCd){
        this.mScheduleTypeCd = pScheduleTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the ScheduleTypeCd field.
     *
     * @return
     *  String containing the ScheduleTypeCd field.
     */
    public String getScheduleTypeCd(){
        return mScheduleTypeCd;
    }

    /**
     * Sets the ScheduleRuleCd field. This field is required to be set in the database.
     *
     * @param pScheduleRuleCd
     *  String to use to update the field.
     */
    public void setScheduleRuleCd(String pScheduleRuleCd){
        this.mScheduleRuleCd = pScheduleRuleCd;
        setDirty(true);
    }
    /**
     * Retrieves the ScheduleRuleCd field.
     *
     * @return
     *  String containing the ScheduleRuleCd field.
     */
    public String getScheduleRuleCd(){
        return mScheduleRuleCd;
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
     * Sets the LastProcessedDt field.
     *
     * @param pLastProcessedDt
     *  Date to use to update the field.
     */
    public void setLastProcessedDt(Date pLastProcessedDt){
        this.mLastProcessedDt = pLastProcessedDt;
        setDirty(true);
    }
    /**
     * Retrieves the LastProcessedDt field.
     *
     * @return
     *  Date containing the LastProcessedDt field.
     */
    public Date getLastProcessedDt(){
        return mLastProcessedDt;
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
