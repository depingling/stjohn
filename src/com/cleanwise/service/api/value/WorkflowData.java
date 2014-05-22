
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        WorkflowData
 * Description:  This is a ValueObject class wrapping the database table CLW_WORKFLOW.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.WorkflowDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>WorkflowData</code> is a ValueObject class wrapping of the database table CLW_WORKFLOW.
 */
public class WorkflowData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 8360239139586048008L;
    private int mWorkflowId;// SQL type:NUMBER, not null
    private int mBusEntityId;// SQL type:NUMBER, not null
    private String mShortDesc;// SQL type:VARCHAR2, not null
    private String mWorkflowTypeCd;// SQL type:VARCHAR2, not null
    private String mWorkflowStatusCd;// SQL type:VARCHAR2, not null
    private Date mEffDate;// SQL type:DATE
    private Date mExpDate;// SQL type:DATE
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public WorkflowData ()
    {
        mShortDesc = "";
        mWorkflowTypeCd = "";
        mWorkflowStatusCd = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public WorkflowData(int parm1, int parm2, String parm3, String parm4, String parm5, Date parm6, Date parm7, Date parm8, String parm9, Date parm10, String parm11)
    {
        mWorkflowId = parm1;
        mBusEntityId = parm2;
        mShortDesc = parm3;
        mWorkflowTypeCd = parm4;
        mWorkflowStatusCd = parm5;
        mEffDate = parm6;
        mExpDate = parm7;
        mAddDate = parm8;
        mAddBy = parm9;
        mModDate = parm10;
        mModBy = parm11;
        
    }

    /**
     * Creates a new WorkflowData
     *
     * @return
     *  Newly initialized WorkflowData object.
     */
    public static WorkflowData createValue ()
    {
        WorkflowData valueData = new WorkflowData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this WorkflowData object
     */
    public String toString()
    {
        return "[" + "WorkflowId=" + mWorkflowId + ", BusEntityId=" + mBusEntityId + ", ShortDesc=" + mShortDesc + ", WorkflowTypeCd=" + mWorkflowTypeCd + ", WorkflowStatusCd=" + mWorkflowStatusCd + ", EffDate=" + mEffDate + ", ExpDate=" + mExpDate + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("Workflow");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mWorkflowId));

        node =  doc.createElement("BusEntityId");
        node.appendChild(doc.createTextNode(String.valueOf(mBusEntityId)));
        root.appendChild(node);

        node =  doc.createElement("ShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mShortDesc)));
        root.appendChild(node);

        node =  doc.createElement("WorkflowTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mWorkflowTypeCd)));
        root.appendChild(node);

        node =  doc.createElement("WorkflowStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mWorkflowStatusCd)));
        root.appendChild(node);

        node =  doc.createElement("EffDate");
        node.appendChild(doc.createTextNode(String.valueOf(mEffDate)));
        root.appendChild(node);

        node =  doc.createElement("ExpDate");
        node.appendChild(doc.createTextNode(String.valueOf(mExpDate)));
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
    * creates a clone of this object, the WorkflowId field is not cloned.
    *
    * @return WorkflowData object
    */
    public Object clone(){
        WorkflowData myClone = new WorkflowData();
        
        myClone.mBusEntityId = mBusEntityId;
        
        myClone.mShortDesc = mShortDesc;
        
        myClone.mWorkflowTypeCd = mWorkflowTypeCd;
        
        myClone.mWorkflowStatusCd = mWorkflowStatusCd;
        
        if(mEffDate != null){
                myClone.mEffDate = (Date) mEffDate.clone();
        }
        
        if(mExpDate != null){
                myClone.mExpDate = (Date) mExpDate.clone();
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

        if (WorkflowDataAccess.WORKFLOW_ID.equals(pFieldName)) {
            return getWorkflowId();
        } else if (WorkflowDataAccess.BUS_ENTITY_ID.equals(pFieldName)) {
            return getBusEntityId();
        } else if (WorkflowDataAccess.SHORT_DESC.equals(pFieldName)) {
            return getShortDesc();
        } else if (WorkflowDataAccess.WORKFLOW_TYPE_CD.equals(pFieldName)) {
            return getWorkflowTypeCd();
        } else if (WorkflowDataAccess.WORKFLOW_STATUS_CD.equals(pFieldName)) {
            return getWorkflowStatusCd();
        } else if (WorkflowDataAccess.EFF_DATE.equals(pFieldName)) {
            return getEffDate();
        } else if (WorkflowDataAccess.EXP_DATE.equals(pFieldName)) {
            return getExpDate();
        } else if (WorkflowDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (WorkflowDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (WorkflowDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (WorkflowDataAccess.MOD_BY.equals(pFieldName)) {
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
        return WorkflowDataAccess.CLW_WORKFLOW;
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
     * Sets the EffDate field.
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
