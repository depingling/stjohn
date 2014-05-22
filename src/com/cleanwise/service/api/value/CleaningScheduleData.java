
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        CleaningScheduleData
 * Description:  This is a ValueObject class wrapping the database table CLW_CLEANING_SCHEDULE.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.CleaningScheduleDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>CleaningScheduleData</code> is a ValueObject class wrapping of the database table CLW_CLEANING_SCHEDULE.
 */
public class CleaningScheduleData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -1533093254308597167L;
    private int mCleaningScheduleId;// SQL type:NUMBER, not null
    private String mName;// SQL type:VARCHAR2
    private int mEstimatorFacilityId;// SQL type:NUMBER
    private String mCleaningScheduleCd;// SQL type:VARCHAR2, not null
    private java.math.BigDecimal mFrequency;// SQL type:NUMBER, not null
    private String mTimePeriodCd;// SQL type:VARCHAR2, not null
    private int mSeqNum;// SQL type:NUMBER, not null
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public CleaningScheduleData ()
    {
        mName = "";
        mCleaningScheduleCd = "";
        mTimePeriodCd = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public CleaningScheduleData(int parm1, String parm2, int parm3, String parm4, java.math.BigDecimal parm5, String parm6, int parm7, Date parm8, String parm9, Date parm10, String parm11)
    {
        mCleaningScheduleId = parm1;
        mName = parm2;
        mEstimatorFacilityId = parm3;
        mCleaningScheduleCd = parm4;
        mFrequency = parm5;
        mTimePeriodCd = parm6;
        mSeqNum = parm7;
        mAddDate = parm8;
        mAddBy = parm9;
        mModDate = parm10;
        mModBy = parm11;
        
    }

    /**
     * Creates a new CleaningScheduleData
     *
     * @return
     *  Newly initialized CleaningScheduleData object.
     */
    public static CleaningScheduleData createValue ()
    {
        CleaningScheduleData valueData = new CleaningScheduleData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this CleaningScheduleData object
     */
    public String toString()
    {
        return "[" + "CleaningScheduleId=" + mCleaningScheduleId + ", Name=" + mName + ", EstimatorFacilityId=" + mEstimatorFacilityId + ", CleaningScheduleCd=" + mCleaningScheduleCd + ", Frequency=" + mFrequency + ", TimePeriodCd=" + mTimePeriodCd + ", SeqNum=" + mSeqNum + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("CleaningSchedule");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mCleaningScheduleId));

        node =  doc.createElement("Name");
        node.appendChild(doc.createTextNode(String.valueOf(mName)));
        root.appendChild(node);

        node =  doc.createElement("EstimatorFacilityId");
        node.appendChild(doc.createTextNode(String.valueOf(mEstimatorFacilityId)));
        root.appendChild(node);

        node =  doc.createElement("CleaningScheduleCd");
        node.appendChild(doc.createTextNode(String.valueOf(mCleaningScheduleCd)));
        root.appendChild(node);

        node =  doc.createElement("Frequency");
        node.appendChild(doc.createTextNode(String.valueOf(mFrequency)));
        root.appendChild(node);

        node =  doc.createElement("TimePeriodCd");
        node.appendChild(doc.createTextNode(String.valueOf(mTimePeriodCd)));
        root.appendChild(node);

        node =  doc.createElement("SeqNum");
        node.appendChild(doc.createTextNode(String.valueOf(mSeqNum)));
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
    * creates a clone of this object, the CleaningScheduleId field is not cloned.
    *
    * @return CleaningScheduleData object
    */
    public Object clone(){
        CleaningScheduleData myClone = new CleaningScheduleData();
        
        myClone.mName = mName;
        
        myClone.mEstimatorFacilityId = mEstimatorFacilityId;
        
        myClone.mCleaningScheduleCd = mCleaningScheduleCd;
        
        myClone.mFrequency = mFrequency;
        
        myClone.mTimePeriodCd = mTimePeriodCd;
        
        myClone.mSeqNum = mSeqNum;
        
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

        if (CleaningScheduleDataAccess.CLEANING_SCHEDULE_ID.equals(pFieldName)) {
            return getCleaningScheduleId();
        } else if (CleaningScheduleDataAccess.NAME.equals(pFieldName)) {
            return getName();
        } else if (CleaningScheduleDataAccess.ESTIMATOR_FACILITY_ID.equals(pFieldName)) {
            return getEstimatorFacilityId();
        } else if (CleaningScheduleDataAccess.CLEANING_SCHEDULE_CD.equals(pFieldName)) {
            return getCleaningScheduleCd();
        } else if (CleaningScheduleDataAccess.FREQUENCY.equals(pFieldName)) {
            return getFrequency();
        } else if (CleaningScheduleDataAccess.TIME_PERIOD_CD.equals(pFieldName)) {
            return getTimePeriodCd();
        } else if (CleaningScheduleDataAccess.SEQ_NUM.equals(pFieldName)) {
            return getSeqNum();
        } else if (CleaningScheduleDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (CleaningScheduleDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (CleaningScheduleDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (CleaningScheduleDataAccess.MOD_BY.equals(pFieldName)) {
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
        return CleaningScheduleDataAccess.CLW_CLEANING_SCHEDULE;
    }

    
    /**
     * Sets the CleaningScheduleId field. This field is required to be set in the database.
     *
     * @param pCleaningScheduleId
     *  int to use to update the field.
     */
    public void setCleaningScheduleId(int pCleaningScheduleId){
        this.mCleaningScheduleId = pCleaningScheduleId;
        setDirty(true);
    }
    /**
     * Retrieves the CleaningScheduleId field.
     *
     * @return
     *  int containing the CleaningScheduleId field.
     */
    public int getCleaningScheduleId(){
        return mCleaningScheduleId;
    }

    /**
     * Sets the Name field.
     *
     * @param pName
     *  String to use to update the field.
     */
    public void setName(String pName){
        this.mName = pName;
        setDirty(true);
    }
    /**
     * Retrieves the Name field.
     *
     * @return
     *  String containing the Name field.
     */
    public String getName(){
        return mName;
    }

    /**
     * Sets the EstimatorFacilityId field.
     *
     * @param pEstimatorFacilityId
     *  int to use to update the field.
     */
    public void setEstimatorFacilityId(int pEstimatorFacilityId){
        this.mEstimatorFacilityId = pEstimatorFacilityId;
        setDirty(true);
    }
    /**
     * Retrieves the EstimatorFacilityId field.
     *
     * @return
     *  int containing the EstimatorFacilityId field.
     */
    public int getEstimatorFacilityId(){
        return mEstimatorFacilityId;
    }

    /**
     * Sets the CleaningScheduleCd field. This field is required to be set in the database.
     *
     * @param pCleaningScheduleCd
     *  String to use to update the field.
     */
    public void setCleaningScheduleCd(String pCleaningScheduleCd){
        this.mCleaningScheduleCd = pCleaningScheduleCd;
        setDirty(true);
    }
    /**
     * Retrieves the CleaningScheduleCd field.
     *
     * @return
     *  String containing the CleaningScheduleCd field.
     */
    public String getCleaningScheduleCd(){
        return mCleaningScheduleCd;
    }

    /**
     * Sets the Frequency field. This field is required to be set in the database.
     *
     * @param pFrequency
     *  java.math.BigDecimal to use to update the field.
     */
    public void setFrequency(java.math.BigDecimal pFrequency){
        this.mFrequency = pFrequency;
        setDirty(true);
    }
    /**
     * Retrieves the Frequency field.
     *
     * @return
     *  java.math.BigDecimal containing the Frequency field.
     */
    public java.math.BigDecimal getFrequency(){
        return mFrequency;
    }

    /**
     * Sets the TimePeriodCd field. This field is required to be set in the database.
     *
     * @param pTimePeriodCd
     *  String to use to update the field.
     */
    public void setTimePeriodCd(String pTimePeriodCd){
        this.mTimePeriodCd = pTimePeriodCd;
        setDirty(true);
    }
    /**
     * Retrieves the TimePeriodCd field.
     *
     * @return
     *  String containing the TimePeriodCd field.
     */
    public String getTimePeriodCd(){
        return mTimePeriodCd;
    }

    /**
     * Sets the SeqNum field. This field is required to be set in the database.
     *
     * @param pSeqNum
     *  int to use to update the field.
     */
    public void setSeqNum(int pSeqNum){
        this.mSeqNum = pSeqNum;
        setDirty(true);
    }
    /**
     * Retrieves the SeqNum field.
     *
     * @return
     *  int containing the SeqNum field.
     */
    public int getSeqNum(){
        return mSeqNum;
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
