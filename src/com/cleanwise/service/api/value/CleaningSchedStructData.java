
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        CleaningSchedStructData
 * Description:  This is a ValueObject class wrapping the database table CLW_CLEANING_SCHED_STRUCT.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.CleaningSchedStructDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>CleaningSchedStructData</code> is a ValueObject class wrapping of the database table CLW_CLEANING_SCHED_STRUCT.
 */
public class CleaningSchedStructData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 4201990151515960374L;
    private int mCleaningSchedStructId;// SQL type:NUMBER, not null
    private int mCleaningScheduleId;// SQL type:NUMBER, not null
    private int mCleaningProcId;// SQL type:NUMBER, not null
    private int mSeqNum;// SQL type:NUMBER, not null
    private int mReiteration;// SQL type:NUMBER, not null
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public CleaningSchedStructData ()
    {
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public CleaningSchedStructData(int parm1, int parm2, int parm3, int parm4, int parm5, Date parm6, String parm7, Date parm8, String parm9)
    {
        mCleaningSchedStructId = parm1;
        mCleaningScheduleId = parm2;
        mCleaningProcId = parm3;
        mSeqNum = parm4;
        mReiteration = parm5;
        mAddDate = parm6;
        mAddBy = parm7;
        mModDate = parm8;
        mModBy = parm9;
        
    }

    /**
     * Creates a new CleaningSchedStructData
     *
     * @return
     *  Newly initialized CleaningSchedStructData object.
     */
    public static CleaningSchedStructData createValue ()
    {
        CleaningSchedStructData valueData = new CleaningSchedStructData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this CleaningSchedStructData object
     */
    public String toString()
    {
        return "[" + "CleaningSchedStructId=" + mCleaningSchedStructId + ", CleaningScheduleId=" + mCleaningScheduleId + ", CleaningProcId=" + mCleaningProcId + ", SeqNum=" + mSeqNum + ", Reiteration=" + mReiteration + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("CleaningSchedStruct");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mCleaningSchedStructId));

        node =  doc.createElement("CleaningScheduleId");
        node.appendChild(doc.createTextNode(String.valueOf(mCleaningScheduleId)));
        root.appendChild(node);

        node =  doc.createElement("CleaningProcId");
        node.appendChild(doc.createTextNode(String.valueOf(mCleaningProcId)));
        root.appendChild(node);

        node =  doc.createElement("SeqNum");
        node.appendChild(doc.createTextNode(String.valueOf(mSeqNum)));
        root.appendChild(node);

        node =  doc.createElement("Reiteration");
        node.appendChild(doc.createTextNode(String.valueOf(mReiteration)));
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
    * creates a clone of this object, the CleaningSchedStructId field is not cloned.
    *
    * @return CleaningSchedStructData object
    */
    public Object clone(){
        CleaningSchedStructData myClone = new CleaningSchedStructData();
        
        myClone.mCleaningScheduleId = mCleaningScheduleId;
        
        myClone.mCleaningProcId = mCleaningProcId;
        
        myClone.mSeqNum = mSeqNum;
        
        myClone.mReiteration = mReiteration;
        
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

        if (CleaningSchedStructDataAccess.CLEANING_SCHED_STRUCT_ID.equals(pFieldName)) {
            return getCleaningSchedStructId();
        } else if (CleaningSchedStructDataAccess.CLEANING_SCHEDULE_ID.equals(pFieldName)) {
            return getCleaningScheduleId();
        } else if (CleaningSchedStructDataAccess.CLEANING_PROC_ID.equals(pFieldName)) {
            return getCleaningProcId();
        } else if (CleaningSchedStructDataAccess.SEQ_NUM.equals(pFieldName)) {
            return getSeqNum();
        } else if (CleaningSchedStructDataAccess.REITERATION.equals(pFieldName)) {
            return getReiteration();
        } else if (CleaningSchedStructDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (CleaningSchedStructDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (CleaningSchedStructDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (CleaningSchedStructDataAccess.MOD_BY.equals(pFieldName)) {
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
        return CleaningSchedStructDataAccess.CLW_CLEANING_SCHED_STRUCT;
    }

    
    /**
     * Sets the CleaningSchedStructId field. This field is required to be set in the database.
     *
     * @param pCleaningSchedStructId
     *  int to use to update the field.
     */
    public void setCleaningSchedStructId(int pCleaningSchedStructId){
        this.mCleaningSchedStructId = pCleaningSchedStructId;
        setDirty(true);
    }
    /**
     * Retrieves the CleaningSchedStructId field.
     *
     * @return
     *  int containing the CleaningSchedStructId field.
     */
    public int getCleaningSchedStructId(){
        return mCleaningSchedStructId;
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
     * Sets the CleaningProcId field. This field is required to be set in the database.
     *
     * @param pCleaningProcId
     *  int to use to update the field.
     */
    public void setCleaningProcId(int pCleaningProcId){
        this.mCleaningProcId = pCleaningProcId;
        setDirty(true);
    }
    /**
     * Retrieves the CleaningProcId field.
     *
     * @return
     *  int containing the CleaningProcId field.
     */
    public int getCleaningProcId(){
        return mCleaningProcId;
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
     * Sets the Reiteration field. This field is required to be set in the database.
     *
     * @param pReiteration
     *  int to use to update the field.
     */
    public void setReiteration(int pReiteration){
        this.mReiteration = pReiteration;
        setDirty(true);
    }
    /**
     * Retrieves the Reiteration field.
     *
     * @return
     *  int containing the Reiteration field.
     */
    public int getReiteration(){
        return mReiteration;
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
