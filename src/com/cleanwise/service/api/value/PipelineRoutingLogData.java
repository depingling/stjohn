
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        PipelineRoutingLogData
 * Description:  This is a ValueObject class wrapping the database table CLW_PIPELINE_ROUTING_LOG.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.PipelineRoutingLogDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>PipelineRoutingLogData</code> is a ValueObject class wrapping of the database table CLW_PIPELINE_ROUTING_LOG.
 */
public class PipelineRoutingLogData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 1853779382677480481L;
    private int mPipelineRoutingLogId;// SQL type:NUMBER, not null
    private int mRoutingId;// SQL type:NUMBER
    private Date mDate;// SQL type:DATE
    private Date mTime;// SQL type:DATE
    private String mChange;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public PipelineRoutingLogData ()
    {
        mChange = "";
    }

    /**
     * Constructor.
     */
    public PipelineRoutingLogData(int parm1, int parm2, Date parm3, Date parm4, String parm5)
    {
        mPipelineRoutingLogId = parm1;
        mRoutingId = parm2;
        mDate = parm3;
        mTime = parm4;
        mChange = parm5;
        
    }

    /**
     * Creates a new PipelineRoutingLogData
     *
     * @return
     *  Newly initialized PipelineRoutingLogData object.
     */
    public static PipelineRoutingLogData createValue ()
    {
        PipelineRoutingLogData valueData = new PipelineRoutingLogData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this PipelineRoutingLogData object
     */
    public String toString()
    {
        return "[" + "PipelineRoutingLogId=" + mPipelineRoutingLogId + ", RoutingId=" + mRoutingId + ", Date=" + mDate + ", Time=" + mTime + ", Change=" + mChange + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("PipelineRoutingLog");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mPipelineRoutingLogId));

        node =  doc.createElement("RoutingId");
        node.appendChild(doc.createTextNode(String.valueOf(mRoutingId)));
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
    * creates a clone of this object, the PipelineRoutingLogId field is not cloned.
    *
    * @return PipelineRoutingLogData object
    */
    public Object clone(){
        PipelineRoutingLogData myClone = new PipelineRoutingLogData();
        
        myClone.mRoutingId = mRoutingId;
        
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

        if (PipelineRoutingLogDataAccess.PIPELINE_ROUTING_LOG_ID.equals(pFieldName)) {
            return getPipelineRoutingLogId();
        } else if (PipelineRoutingLogDataAccess.ROUTING_ID.equals(pFieldName)) {
            return getRoutingId();
        } else if (PipelineRoutingLogDataAccess.CLW_DATE.equals(pFieldName)) {
            return getDate();
        } else if (PipelineRoutingLogDataAccess.CLW_TIME.equals(pFieldName)) {
            return getTime();
        } else if (PipelineRoutingLogDataAccess.CHANGE.equals(pFieldName)) {
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
        return PipelineRoutingLogDataAccess.CLW_PIPELINE_ROUTING_LOG;
    }

    
    /**
     * Sets the PipelineRoutingLogId field. This field is required to be set in the database.
     *
     * @param pPipelineRoutingLogId
     *  int to use to update the field.
     */
    public void setPipelineRoutingLogId(int pPipelineRoutingLogId){
        this.mPipelineRoutingLogId = pPipelineRoutingLogId;
        setDirty(true);
    }
    /**
     * Retrieves the PipelineRoutingLogId field.
     *
     * @return
     *  int containing the PipelineRoutingLogId field.
     */
    public int getPipelineRoutingLogId(){
        return mPipelineRoutingLogId;
    }

    /**
     * Sets the RoutingId field.
     *
     * @param pRoutingId
     *  int to use to update the field.
     */
    public void setRoutingId(int pRoutingId){
        this.mRoutingId = pRoutingId;
        setDirty(true);
    }
    /**
     * Retrieves the RoutingId field.
     *
     * @return
     *  int containing the RoutingId field.
     */
    public int getRoutingId(){
        return mRoutingId;
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
