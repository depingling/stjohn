
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        PipelineLogData
 * Description:  This is a ValueObject class wrapping the database table CLW_PIPELINE_LOG.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.PipelineLogDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>PipelineLogData</code> is a ValueObject class wrapping of the database table CLW_PIPELINE_LOG.
 */
public class PipelineLogData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -5955608649108008353L;
    private int mPipelineLogId;// SQL type:NUMBER, not null
    private int mPipelineId;// SQL type:NUMBER
    private Date mDate;// SQL type:DATE
    private Date mTime;// SQL type:DATE
    private String mChange;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public PipelineLogData ()
    {
        mChange = "";
    }

    /**
     * Constructor.
     */
    public PipelineLogData(int parm1, int parm2, Date parm3, Date parm4, String parm5)
    {
        mPipelineLogId = parm1;
        mPipelineId = parm2;
        mDate = parm3;
        mTime = parm4;
        mChange = parm5;
        
    }

    /**
     * Creates a new PipelineLogData
     *
     * @return
     *  Newly initialized PipelineLogData object.
     */
    public static PipelineLogData createValue ()
    {
        PipelineLogData valueData = new PipelineLogData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this PipelineLogData object
     */
    public String toString()
    {
        return "[" + "PipelineLogId=" + mPipelineLogId + ", PipelineId=" + mPipelineId + ", Date=" + mDate + ", Time=" + mTime + ", Change=" + mChange + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("PipelineLog");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mPipelineLogId));

        node =  doc.createElement("PipelineId");
        node.appendChild(doc.createTextNode(String.valueOf(mPipelineId)));
        root.appendChild(node);

        node =  doc.createElement("Date");
        node.appendChild(doc.createTextNode(String.valueOf(mDate)));
        root.appendChild(node);

        node =  doc.createElement("Time");
        node.appendChild(doc.createTextNode(String.valueOf(mTime)));
        root.appendChild(node);

        node =  doc.createElement("Change");
        node.appendChild(doc.createTextNode(String.valueOf(mChange)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the PipelineLogId field is not cloned.
    *
    * @return PipelineLogData object
    */
    public Object clone(){
        PipelineLogData myClone = new PipelineLogData();
        
        myClone.mPipelineId = mPipelineId;
        
        if(mDate != null){
                myClone.mDate = (Date) mDate.clone();
        }
        
        if(mTime != null){
                myClone.mTime = (Date) mTime.clone();
        }
        
        myClone.mChange = mChange;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (PipelineLogDataAccess.PIPELINE_LOG_ID.equals(pFieldName)) {
            return getPipelineLogId();
        } else if (PipelineLogDataAccess.PIPELINE_ID.equals(pFieldName)) {
            return getPipelineId();
        } else if (PipelineLogDataAccess.CLW_DATE.equals(pFieldName)) {
            return getDate();
        } else if (PipelineLogDataAccess.CLW_TIME.equals(pFieldName)) {
            return getTime();
        } else if (PipelineLogDataAccess.CHANGE.equals(pFieldName)) {
            return getChange();
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
        return PipelineLogDataAccess.CLW_PIPELINE_LOG;
    }

    
    /**
     * Sets the PipelineLogId field. This field is required to be set in the database.
     *
     * @param pPipelineLogId
     *  int to use to update the field.
     */
    public void setPipelineLogId(int pPipelineLogId){
        this.mPipelineLogId = pPipelineLogId;
        setDirty(true);
    }
    /**
     * Retrieves the PipelineLogId field.
     *
     * @return
     *  int containing the PipelineLogId field.
     */
    public int getPipelineLogId(){
        return mPipelineLogId;
    }

    /**
     * Sets the PipelineId field.
     *
     * @param pPipelineId
     *  int to use to update the field.
     */
    public void setPipelineId(int pPipelineId){
        this.mPipelineId = pPipelineId;
        setDirty(true);
    }
    /**
     * Retrieves the PipelineId field.
     *
     * @return
     *  int containing the PipelineId field.
     */
    public int getPipelineId(){
        return mPipelineId;
    }

    /**
     * Sets the Date field.
     *
     * @param pDate
     *  Date to use to update the field.
     */
    public void setDate(Date pDate){
        this.mDate = pDate;
        setDirty(true);
    }
    /**
     * Retrieves the Date field.
     *
     * @return
     *  Date containing the Date field.
     */
    public Date getDate(){
        return mDate;
    }

    /**
     * Sets the Time field.
     *
     * @param pTime
     *  Date to use to update the field.
     */
    public void setTime(Date pTime){
        this.mTime = pTime;
        setDirty(true);
    }
    /**
     * Retrieves the Time field.
     *
     * @return
     *  Date containing the Time field.
     */
    public Date getTime(){
        return mTime;
    }

    /**
     * Sets the Change field.
     *
     * @param pChange
     *  String to use to update the field.
     */
    public void setChange(String pChange){
        this.mChange = pChange;
        setDirty(true);
    }
    /**
     * Retrieves the Change field.
     *
     * @return
     *  String containing the Change field.
     */
    public String getChange(){
        return mChange;
    }


}
