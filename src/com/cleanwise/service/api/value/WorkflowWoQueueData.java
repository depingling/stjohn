
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        WorkflowWoQueueData
 * Description:  This is a ValueObject class wrapping the database table CLW_WORKFLOW_WO_QUEUE.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.WorkflowWoQueueDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>WorkflowWoQueueData</code> is a ValueObject class wrapping of the database table CLW_WORKFLOW_WO_QUEUE.
 */
public class WorkflowWoQueueData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 4771395838333217254L;
    private int mWorkflowWoQueueId;// SQL type:NUMBER, not null
    private String mShortDesc;// SQL type:VARCHAR2, not null
    private int mWorkOrderId;// SQL type:NUMBER
    private int mBusEntityId;// SQL type:NUMBER
    private String mWorkflowRoleCd;// SQL type:VARCHAR2, not null
    private int mWorkflowId;// SQL type:NUMBER
    private int mWorkflowRuleId;// SQL type:NUMBER
    private int mActionDays;// SQL type:NUMBER
    private String mActionType;// SQL type:VARCHAR2
    private String mStatusCd;// SQL type:VARCHAR2, not null
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public WorkflowWoQueueData ()
    {
        mShortDesc = "";
        mWorkflowRoleCd = "";
        mActionType = "";
        mStatusCd = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public WorkflowWoQueueData(int parm1, String parm2, int parm3, int parm4, String parm5, int parm6, int parm7, int parm8, String parm9, String parm10, Date parm11, String parm12, Date parm13, String parm14)
    {
        mWorkflowWoQueueId = parm1;
        mShortDesc = parm2;
        mWorkOrderId = parm3;
        mBusEntityId = parm4;
        mWorkflowRoleCd = parm5;
        mWorkflowId = parm6;
        mWorkflowRuleId = parm7;
        mActionDays = parm8;
        mActionType = parm9;
        mStatusCd = parm10;
        mAddDate = parm11;
        mAddBy = parm12;
        mModDate = parm13;
        mModBy = parm14;
        
    }

    /**
     * Creates a new WorkflowWoQueueData
     *
     * @return
     *  Newly initialized WorkflowWoQueueData object.
     */
    public static WorkflowWoQueueData createValue ()
    {
        WorkflowWoQueueData valueData = new WorkflowWoQueueData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this WorkflowWoQueueData object
     */
    public String toString()
    {
        return "[" + "WorkflowWoQueueId=" + mWorkflowWoQueueId + ", ShortDesc=" + mShortDesc + ", WorkOrderId=" + mWorkOrderId + ", BusEntityId=" + mBusEntityId + ", WorkflowRoleCd=" + mWorkflowRoleCd + ", WorkflowId=" + mWorkflowId + ", WorkflowRuleId=" + mWorkflowRuleId + ", ActionDays=" + mActionDays + ", ActionType=" + mActionType + ", StatusCd=" + mStatusCd + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("WorkflowWoQueue");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mWorkflowWoQueueId));

        node =  doc.createElement("ShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mShortDesc)));
        root.appendChild(node);

        node =  doc.createElement("WorkOrderId");
        node.appendChild(doc.createTextNode(String.valueOf(mWorkOrderId)));
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

        node =  doc.createElement("ActionDays");
        node.appendChild(doc.createTextNode(String.valueOf(mActionDays)));
        root.appendChild(node);

        node =  doc.createElement("ActionType");
        node.appendChild(doc.createTextNode(String.valueOf(mActionType)));
        root.appendChild(node);

        node =  doc.createElement("StatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mStatusCd)));
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
    * creates a clone of this object, the WorkflowWoQueueId field is not cloned.
    *
    * @return WorkflowWoQueueData object
    */
    public Object clone(){
        WorkflowWoQueueData myClone = new WorkflowWoQueueData();
        
        myClone.mShortDesc = mShortDesc;
        
        myClone.mWorkOrderId = mWorkOrderId;
        
        myClone.mBusEntityId = mBusEntityId;
        
        myClone.mWorkflowRoleCd = mWorkflowRoleCd;
        
        myClone.mWorkflowId = mWorkflowId;
        
        myClone.mWorkflowRuleId = mWorkflowRuleId;
        
        myClone.mActionDays = mActionDays;
        
        myClone.mActionType = mActionType;
        
        myClone.mStatusCd = mStatusCd;
        
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

        if (WorkflowWoQueueDataAccess.WORKFLOW_WO_QUEUE_ID.equals(pFieldName)) {
            return getWorkflowWoQueueId();
        } else if (WorkflowWoQueueDataAccess.SHORT_DESC.equals(pFieldName)) {
            return getShortDesc();
        } else if (WorkflowWoQueueDataAccess.WORK_ORDER_ID.equals(pFieldName)) {
            return getWorkOrderId();
        } else if (WorkflowWoQueueDataAccess.BUS_ENTITY_ID.equals(pFieldName)) {
            return getBusEntityId();
        } else if (WorkflowWoQueueDataAccess.WORKFLOW_ROLE_CD.equals(pFieldName)) {
            return getWorkflowRoleCd();
        } else if (WorkflowWoQueueDataAccess.WORKFLOW_ID.equals(pFieldName)) {
            return getWorkflowId();
        } else if (WorkflowWoQueueDataAccess.WORKFLOW_RULE_ID.equals(pFieldName)) {
            return getWorkflowRuleId();
        } else if (WorkflowWoQueueDataAccess.ACTION_DAYS.equals(pFieldName)) {
            return getActionDays();
        } else if (WorkflowWoQueueDataAccess.ACTION_TYPE.equals(pFieldName)) {
            return getActionType();
        } else if (WorkflowWoQueueDataAccess.STATUS_CD.equals(pFieldName)) {
            return getStatusCd();
        } else if (WorkflowWoQueueDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (WorkflowWoQueueDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (WorkflowWoQueueDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (WorkflowWoQueueDataAccess.MOD_BY.equals(pFieldName)) {
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
        return WorkflowWoQueueDataAccess.CLW_WORKFLOW_WO_QUEUE;
    }

    
    /**
     * Sets the WorkflowWoQueueId field. This field is required to be set in the database.
     *
     * @param pWorkflowWoQueueId
     *  int to use to update the field.
     */
    public void setWorkflowWoQueueId(int pWorkflowWoQueueId){
        this.mWorkflowWoQueueId = pWorkflowWoQueueId;
        setDirty(true);
    }
    /**
     * Retrieves the WorkflowWoQueueId field.
     *
     * @return
     *  int containing the WorkflowWoQueueId field.
     */
    public int getWorkflowWoQueueId(){
        return mWorkflowWoQueueId;
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
     * Sets the ActionDays field.
     *
     * @param pActionDays
     *  int to use to update the field.
     */
    public void setActionDays(int pActionDays){
        this.mActionDays = pActionDays;
        setDirty(true);
    }
    /**
     * Retrieves the ActionDays field.
     *
     * @return
     *  int containing the ActionDays field.
     */
    public int getActionDays(){
        return mActionDays;
    }

    /**
     * Sets the ActionType field.
     *
     * @param pActionType
     *  String to use to update the field.
     */
    public void setActionType(String pActionType){
        this.mActionType = pActionType;
        setDirty(true);
    }
    /**
     * Retrieves the ActionType field.
     *
     * @return
     *  String containing the ActionType field.
     */
    public String getActionType(){
        return mActionType;
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
