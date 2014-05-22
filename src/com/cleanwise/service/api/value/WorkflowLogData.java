
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        WorkflowLogData
 * Description:  This is a ValueObject class wrapping the database table CLW_WORKFLOW_LOG.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.WorkflowLogDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>WorkflowLogData</code> is a ValueObject class wrapping of the database table CLW_WORKFLOW_LOG.
 */
public class WorkflowLogData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 5769751979513213148L;
    private int mWorkflowLogId;// SQL type:NUMBER, not null
    private int mWorkflowId;// SQL type:NUMBER, not null
    private Date mStartDate;// SQL type:DATE, not null
    private Date mStartTime;// SQL type:DATE, not null
    private String mWorkflowTypeCd;// SQL type:VARCHAR2, not null
    private String mWorkflowStatusCd;// SQL type:VARCHAR2, not null
    private Date mEndDate;// SQL type:DATE
    private Date mEndTime;// SQL type:DATE

    /**
     * Constructor.
     */
    public WorkflowLogData ()
    {
        mWorkflowTypeCd = "";
        mWorkflowStatusCd = "";
    }

    /**
     * Constructor.
     */
    public WorkflowLogData(int parm1, int parm2, Date parm3, Date parm4, String parm5, String parm6, Date parm7, Date parm8)
    {
        mWorkflowLogId = parm1;
        mWorkflowId = parm2;
        mStartDate = parm3;
        mStartTime = parm4;
        mWorkflowTypeCd = parm5;
        mWorkflowStatusCd = parm6;
        mEndDate = parm7;
        mEndTime = parm8;
        
    }

    /**
     * Creates a new WorkflowLogData
     *
     * @return
     *  Newly initialized WorkflowLogData object.
     */
    public static WorkflowLogData createValue ()
    {
        WorkflowLogData valueData = new WorkflowLogData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this WorkflowLogData object
     */
    public String toString()
    {
        return "[" + "WorkflowLogId=" + mWorkflowLogId + ", WorkflowId=" + mWorkflowId + ", StartDate=" + mStartDate + ", StartTime=" + mStartTime + ", WorkflowTypeCd=" + mWorkflowTypeCd + ", WorkflowStatusCd=" + mWorkflowStatusCd + ", EndDate=" + mEndDate + ", EndTime=" + mEndTime + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("WorkflowLog");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mWorkflowLogId));

        node =  doc.createElement("WorkflowId");
        node.appendChild(doc.createTextNode(String.valueOf(mWorkflowId)));
        root.appendChild(node);

        node =  doc.createElement("StartDate");
        node.appendChild(doc.createTextNode(String.valueOf(mStartDate)));
        root.appendChild(node);

        node =  doc.createElement("StartTime");
        node.appendChild(doc.createTextNode(String.valueOf(mStartTime)));
        root.appendChild(node);

        node =  doc.createElement("WorkflowTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mWorkflowTypeCd)));
        root.appendChild(node);

        node =  doc.createElement("WorkflowStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mWorkflowStatusCd)));
        root.appendChild(node);

        node =  doc.createElement("EndDate");
        node.appendChild(doc.createTextNode(String.valueOf(mEndDate)));
        root.appendChild(node);

        node =  doc.createElement("EndTime");
        node.appendChild(doc.createTextNode(String.valueOf(mEndTime)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the WorkflowLogId field is not cloned.
    *
    * @return WorkflowLogData object
    */
    public Object clone(){
        WorkflowLogData myClone = new WorkflowLogData();
        
        myClone.mWorkflowId = mWorkflowId;
        
        if(mStartDate != null){
                myClone.mStartDate = (Date) mStartDate.clone();
        }
        
        if(mStartTime != null){
                myClone.mStartTime = (Date) mStartTime.clone();
        }
        
        myClone.mWorkflowTypeCd = mWorkflowTypeCd;
        
        myClone.mWorkflowStatusCd = mWorkflowStatusCd;
        
        if(mEndDate != null){
                myClone.mEndDate = (Date) mEndDate.clone();
        }
        
        if(mEndTime != null){
                myClone.mEndTime = (Date) mEndTime.clone();
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

        if (WorkflowLogDataAccess.WORKFLOW_LOG_ID.equals(pFieldName)) {
            return getWorkflowLogId();
        } else if (WorkflowLogDataAccess.WORKFLOW_ID.equals(pFieldName)) {
            return getWorkflowId();
        } else if (WorkflowLogDataAccess.START_DATE.equals(pFieldName)) {
            return getStartDate();
        } else if (WorkflowLogDataAccess.START_TIME.equals(pFieldName)) {
            return getStartTime();
        } else if (WorkflowLogDataAccess.WORKFLOW_TYPE_CD.equals(pFieldName)) {
            return getWorkflowTypeCd();
        } else if (WorkflowLogDataAccess.WORKFLOW_STATUS_CD.equals(pFieldName)) {
            return getWorkflowStatusCd();
        } else if (WorkflowLogDataAccess.END_DATE.equals(pFieldName)) {
            return getEndDate();
        } else if (WorkflowLogDataAccess.END_TIME.equals(pFieldName)) {
            return getEndTime();
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
        return WorkflowLogDataAccess.CLW_WORKFLOW_LOG;
    }

    
    /**
     * Sets the WorkflowLogId field. This field is required to be set in the database.
     *
     * @param pWorkflowLogId
     *  int to use to update the field.
     */
    public void setWorkflowLogId(int pWorkflowLogId){
        this.mWorkflowLogId = pWorkflowLogId;
        setDirty(true);
    }
    /**
     * Retrieves the WorkflowLogId field.
     *
     * @return
     *  int containing the WorkflowLogId field.
     */
    public int getWorkflowLogId(){
        return mWorkflowLogId;
    }

    /**
     * Sets the WorkflowId field. This field is required to be set in the database.
     *
     * @param pWorkflowId
     *  int to use to update the field.
     */
    public void setWorkflowId(int pWorkflowId){
        this.mWorkflowId = pWorkflowId;
        setDirty(true);
    }
    /**
     * Retrieves the WorkflowId field.
     *
     * @return
     *  int containing the WorkflowId field.
     */
    public int getWorkflowId(){
        return mWorkflowId;
    }

    /**
     * Sets the StartDate field. This field is required to be set in the database.
     *
     * @param pStartDate
     *  Date to use to update the field.
     */
    public void setStartDate(Date pStartDate){
        this.mStartDate = pStartDate;
        setDirty(true);
    }
    /**
     * Retrieves the StartDate field.
     *
     * @return
     *  Date containing the StartDate field.
     */
    public Date getStartDate(){
        return mStartDate;
    }

    /**
     * Sets the StartTime field. This field is required to be set in the database.
     *
     * @param pStartTime
     *  Date to use to update the field.
     */
    public void setStartTime(Date pStartTime){
        this.mStartTime = pStartTime;
        setDirty(true);
    }
    /**
     * Retrieves the StartTime field.
     *
     * @return
     *  Date containing the StartTime field.
     */
    public Date getStartTime(){
        return mStartTime;
    }

    /**
     * Sets the WorkflowTypeCd field. This field is required to be set in the database.
     *
     * @param pWorkflowTypeCd
     *  String to use to update the field.
     */
    public void setWorkflowTypeCd(String pWorkflowTypeCd){
        this.mWorkflowTypeCd = pWorkflowTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the WorkflowTypeCd field.
     *
     * @return
     *  String containing the WorkflowTypeCd field.
     */
    public String getWorkflowTypeCd(){
        return mWorkflowTypeCd;
    }

    /**
     * Sets the WorkflowStatusCd field. This field is required to be set in the database.
     *
     * @param pWorkflowStatusCd
     *  String to use to update the field.
     */
    public void setWorkflowStatusCd(String pWorkflowStatusCd){
        this.mWorkflowStatusCd = pWorkflowStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the WorkflowStatusCd field.
     *
     * @return
     *  String containing the WorkflowStatusCd field.
     */
    public String getWorkflowStatusCd(){
        return mWorkflowStatusCd;
    }

    /**
     * Sets the EndDate field.
     *
     * @param pEndDate
     *  Date to use to update the field.
     */
    public void setEndDate(Date pEndDate){
        this.mEndDate = pEndDate;
        setDirty(true);
    }
    /**
     * Retrieves the EndDate field.
     *
     * @return
     *  Date containing the EndDate field.
     */
    public Date getEndDate(){
        return mEndDate;
    }

    /**
     * Sets the EndTime field.
     *
     * @param pEndTime
     *  Date to use to update the field.
     */
    public void setEndTime(Date pEndTime){
        this.mEndTime = pEndTime;
        setDirty(true);
    }
    /**
     * Retrieves the EndTime field.
     *
     * @return
     *  Date containing the EndTime field.
     */
    public Date getEndTime(){
        return mEndTime;
    }


}
