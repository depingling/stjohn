
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        QueueData
 * Description:  This is a ValueObject class wrapping the database table CLW_QUEUE.
 * Purpose:      
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import java.lang.*;
import java.util.*;
import java.io.*;

import org.w3c.dom.*;

/**
 * <code>QueueData</code> is a ValueObject class wrapping of the database table CLW_QUEUE.
 */
public class QueueData extends ValueObject
{
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = 8507915148846871430L;
    
    private int mQueueId;// SQL type:NUMBER, not null
    private String mShortDesc;// SQL type:VARCHAR2, not null
    private int mBusEntityId;// SQL type:NUMBER
    private String mWorkflowRoleCd;// SQL type:VARCHAR2, not null
    private int mWorkflowId;// SQL type:NUMBER
    private int mWorkflowItemLogId;// SQL type:NUMBER
    private Date mStartTime;// SQL type:DATE
    private Date mStartDate;// SQL type:DATE
    private Date mEndTime;// SQL type:DATE
    private Date mEndDate;// SQL type:DATE 

    /**
     * Constructor.
     */
    private QueueData ()
    {
        mShortDesc = "";
        mWorkflowRoleCd = "";
    }

    /**
     * Constructor. 
     */
    public QueueData(int parm1, String parm2, int parm3, String parm4, int parm5, int parm6, Date parm7, Date parm8, Date parm9, Date parm10)
    {
        mQueueId = parm1;
        mShortDesc = parm2;
        mBusEntityId = parm3;
        mWorkflowRoleCd = parm4;
        mWorkflowId = parm5;
        mWorkflowItemLogId = parm6;
        mStartTime = parm7;
        mStartDate = parm8;
        mEndTime = parm9;
        mEndDate = parm10;
        
    }

    /**
     * Creates a new QueueData
     *
     * @return
     *  Newly initialized QueueData object.
     */
    public static QueueData createValue () 
    {
        QueueData valueData = new QueueData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this QueueData object
     */
    public String toString()
    {
        return "[" + "QueueId=" + mQueueId + ", ShortDesc=" + mShortDesc + ", BusEntityId=" + mBusEntityId + ", WorkflowRoleCd=" + mWorkflowRoleCd + ", WorkflowId=" + mWorkflowId + ", WorkflowItemLogId=" + mWorkflowItemLogId + ", StartTime=" + mStartTime + ", StartDate=" + mStartDate + ", EndTime=" + mEndTime + ", EndDate=" + mEndDate + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return ElementNode.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("Queue");
	root.setAttribute("Id", String.valueOf(mQueueId));

	Element node;

        node = doc.createElement("ShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mShortDesc)));
        root.appendChild(node);

        node = doc.createElement("BusEntityId");
        node.appendChild(doc.createTextNode(String.valueOf(mBusEntityId)));
        root.appendChild(node);

        node = doc.createElement("WorkflowRoleCd");
        node.appendChild(doc.createTextNode(String.valueOf(mWorkflowRoleCd)));
        root.appendChild(node);

        node = doc.createElement("WorkflowId");
        node.appendChild(doc.createTextNode(String.valueOf(mWorkflowId)));
        root.appendChild(node);

        node = doc.createElement("WorkflowItemLogId");
        node.appendChild(doc.createTextNode(String.valueOf(mWorkflowItemLogId)));
        root.appendChild(node);

        node = doc.createElement("StartTime");
        node.appendChild(doc.createTextNode(String.valueOf(mStartTime)));
        root.appendChild(node);

        node = doc.createElement("StartDate");
        node.appendChild(doc.createTextNode(String.valueOf(mStartDate)));
        root.appendChild(node);

        node = doc.createElement("EndTime");
        node.appendChild(doc.createTextNode(String.valueOf(mEndTime)));
        root.appendChild(node);

        node = doc.createElement("EndDate");
        node.appendChild(doc.createTextNode(String.valueOf(mEndDate)));
        root.appendChild(node);

        return root;
    }
    
    /**
     * Sets the QueueId field. This field is required to be set in the database.
     *
     * @param pQueueId
     *  int to use to update the field.
     */
    public void setQueueId(int pQueueId){
        this.mQueueId = pQueueId;
        setDirty(true);
    }
    /**
     * Retrieves the QueueId field.
     *
     * @return
     *  int containing the QueueId field.
     */
    public int getQueueId(){
        return mQueueId;
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
     * Sets the WorkflowRoleCd field. This field is required to be set in the database.
     *
     * @param pWorkflowRoleCd
     *  String to use to update the field.
     */
    public void setWorkflowRoleCd(String pWorkflowRoleCd){
        this.mWorkflowRoleCd = pWorkflowRoleCd;
        setDirty(true);
    }
    /**
     * Retrieves the WorkflowRoleCd field.
     *
     * @return
     *  String containing the WorkflowRoleCd field.
     */
    public String getWorkflowRoleCd(){
        return mWorkflowRoleCd;
    }

    /**
     * Sets the WorkflowId field.
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
     * Sets the WorkflowItemLogId field.
     *
     * @param pWorkflowItemLogId
     *  int to use to update the field.
     */
    public void setWorkflowItemLogId(int pWorkflowItemLogId){
        this.mWorkflowItemLogId = pWorkflowItemLogId;
        setDirty(true);
    }
    /**
     * Retrieves the WorkflowItemLogId field.
     *
     * @return
     *  int containing the WorkflowItemLogId field.
     */
    public int getWorkflowItemLogId(){
        return mWorkflowItemLogId;
    }

    /**
     * Sets the StartTime field.
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
     * Sets the StartDate field.
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

    
}
