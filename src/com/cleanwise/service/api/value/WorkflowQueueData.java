
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        WorkflowQueueData
 * Description:  This is a ValueObject class wrapping the database table CLW_WORKFLOW_QUEUE.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.WorkflowQueueDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>WorkflowQueueData</code> is a ValueObject class wrapping of the database table CLW_WORKFLOW_QUEUE.
 */
public class WorkflowQueueData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 4771395857333217254L;
    private int mWorkflowQueueId;// SQL type:NUMBER, not null
    private String mShortDesc;// SQL type:VARCHAR2, not null
    private int mOrderId;// SQL type:NUMBER
    private int mBusEntityId;// SQL type:NUMBER
    private String mWorkflowRoleCd;// SQL type:VARCHAR2, not null
    private int mWorkflowId;// SQL type:NUMBER
    private int mWorkflowRuleId;// SQL type:NUMBER
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private String mMessage;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public WorkflowQueueData ()
    {
        mShortDesc = "";
        mWorkflowRoleCd = "";
        mAddBy = "";
        mModBy = "";
        mMessage = "";
    }

    /**
     * Constructor.
     */
    public WorkflowQueueData(int parm1, String parm2, int parm3, int parm4, String parm5, int parm6, int parm7, Date parm8, String parm9, Date parm10, String parm11, String parm12)
    {
        mWorkflowQueueId = parm1;
        mShortDesc = parm2;
        mOrderId = parm3;
        mBusEntityId = parm4;
        mWorkflowRoleCd = parm5;
        mWorkflowId = parm6;
        mWorkflowRuleId = parm7;
        mAddDate = parm8;
        mAddBy = parm9;
        mModDate = parm10;
        mModBy = parm11;
        mMessage = parm12;
        
    }

    /**
     * Creates a new WorkflowQueueData
     *
     * @return
     *  Newly initialized WorkflowQueueData object.
     */
    public static WorkflowQueueData createValue ()
    {
        WorkflowQueueData valueData = new WorkflowQueueData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this WorkflowQueueData object
     */
    public String toString()
    {
        return "[" + "WorkflowQueueId=" + mWorkflowQueueId + ", ShortDesc=" + mShortDesc + ", OrderId=" + mOrderId + ", BusEntityId=" + mBusEntityId + ", WorkflowRoleCd=" + mWorkflowRoleCd + ", WorkflowId=" + mWorkflowId + ", WorkflowRuleId=" + mWorkflowRuleId + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", Message=" + mMessage + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("WorkflowQueue");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mWorkflowQueueId));

        node =  doc.createElement("ShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mShortDesc)));
        root.appendChild(node);

        node =  doc.createElement("OrderId");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderId)));
        root.appendChild(node);

        node =  doc.createElement("BusEntityId");
        node.appendChild(doc.createTextNode(String.valueOf(mBusEntityId)));
        root.appendChild(node);

        node =  doc.createElement("WorkflowRoleCd");
        node.appendChild(doc.createTextNode(String.valueOf(mWorkflowRoleCd)));
        root.appendChild(node);

        node =  doc.createElement("WorkflowId");
        node.appendChild(doc.createTextNode(String.valueOf(mWorkflowId)));
        root.appendChild(node);

        node =  doc.createElement("WorkflowRuleId");
        node.appendChild(doc.createTextNode(String.valueOf(mWorkflowRuleId)));
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

        node =  doc.createElement("Message");
        node.appendChild(doc.createTextNode(String.valueOf(mMessage)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the WorkflowQueueId field is not cloned.
    *
    * @return WorkflowQueueData object
    */
    public Object clone(){
        WorkflowQueueData myClone = new WorkflowQueueData();
        
        myClone.mShortDesc = mShortDesc;
        
        myClone.mOrderId = mOrderId;
        
        myClone.mBusEntityId = mBusEntityId;
        
        myClone.mWorkflowRoleCd = mWorkflowRoleCd;
        
        myClone.mWorkflowId = mWorkflowId;
        
        myClone.mWorkflowRuleId = mWorkflowRuleId;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        myClone.mMessage = mMessage;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (WorkflowQueueDataAccess.WORKFLOW_QUEUE_ID.equals(pFieldName)) {
            return getWorkflowQueueId();
        } else if (WorkflowQueueDataAccess.SHORT_DESC.equals(pFieldName)) {
            return getShortDesc();
        } else if (WorkflowQueueDataAccess.ORDER_ID.equals(pFieldName)) {
            return getOrderId();
        } else if (WorkflowQueueDataAccess.BUS_ENTITY_ID.equals(pFieldName)) {
            return getBusEntityId();
        } else if (WorkflowQueueDataAccess.WORKFLOW_ROLE_CD.equals(pFieldName)) {
            return getWorkflowRoleCd();
        } else if (WorkflowQueueDataAccess.WORKFLOW_ID.equals(pFieldName)) {
            return getWorkflowId();
        } else if (WorkflowQueueDataAccess.WORKFLOW_RULE_ID.equals(pFieldName)) {
            return getWorkflowRuleId();
        } else if (WorkflowQueueDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (WorkflowQueueDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (WorkflowQueueDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (WorkflowQueueDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (WorkflowQueueDataAccess.MESSAGE.equals(pFieldName)) {
            return getMessage();
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
        return WorkflowQueueDataAccess.CLW_WORKFLOW_QUEUE;
    }

    
    /**
     * Sets the WorkflowQueueId field. This field is required to be set in the database.
     *
     * @param pWorkflowQueueId
     *  int to use to update the field.
     */
    public void setWorkflowQueueId(int pWorkflowQueueId){
        this.mWorkflowQueueId = pWorkflowQueueId;
        setDirty(true);
    }
    /**
     * Retrieves the WorkflowQueueId field.
     *
     * @return
     *  int containing the WorkflowQueueId field.
     */
    public int getWorkflowQueueId(){
        return mWorkflowQueueId;
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
     * Sets the OrderId field.
     *
     * @param pOrderId
     *  int to use to update the field.
     */
    public void setOrderId(int pOrderId){
        this.mOrderId = pOrderId;
        setDirty(true);
    }
    /**
     * Retrieves the OrderId field.
     *
     * @return
     *  int containing the OrderId field.
     */
    public int getOrderId(){
        return mOrderId;
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
     * Sets the WorkflowRuleId field.
     *
     * @param pWorkflowRuleId
     *  int to use to update the field.
     */
    public void setWorkflowRuleId(int pWorkflowRuleId){
        this.mWorkflowRuleId = pWorkflowRuleId;
        setDirty(true);
    }
    /**
     * Retrieves the WorkflowRuleId field.
     *
     * @return
     *  int containing the WorkflowRuleId field.
     */
    public int getWorkflowRuleId(){
        return mWorkflowRuleId;
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
     * Sets the Message field.
     *
     * @param pMessage
     *  String to use to update the field.
     */
    public void setMessage(String pMessage){
        this.mMessage = pMessage;
        setDirty(true);
    }
    /**
     * Retrieves the Message field.
     *
     * @return
     *  String containing the Message field.
     */
    public String getMessage(){
        return mMessage;
    }


}
