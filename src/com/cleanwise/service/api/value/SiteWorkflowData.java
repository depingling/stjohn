
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        SiteWorkflowData
 * Description:  This is a ValueObject class wrapping the database table CLW_SITE_WORKFLOW.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.SiteWorkflowDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>SiteWorkflowData</code> is a ValueObject class wrapping of the database table CLW_SITE_WORKFLOW.
 */
public class SiteWorkflowData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -5251004386957082525L;
    private int mSiteWorkflowId;// SQL type:NUMBER, not null
    private int mWorkflowId;// SQL type:NUMBER
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private int mSiteId;// SQL type:NUMBER

    /**
     * Constructor.
     */
    public SiteWorkflowData ()
    {
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public SiteWorkflowData(int parm1, int parm2, Date parm3, String parm4, Date parm5, String parm6, int parm7)
    {
        mSiteWorkflowId = parm1;
        mWorkflowId = parm2;
        mAddDate = parm3;
        mAddBy = parm4;
        mModDate = parm5;
        mModBy = parm6;
        mSiteId = parm7;
        
    }

    /**
     * Creates a new SiteWorkflowData
     *
     * @return
     *  Newly initialized SiteWorkflowData object.
     */
    public static SiteWorkflowData createValue ()
    {
        SiteWorkflowData valueData = new SiteWorkflowData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this SiteWorkflowData object
     */
    public String toString()
    {
        return "[" + "SiteWorkflowId=" + mSiteWorkflowId + ", WorkflowId=" + mWorkflowId + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", SiteId=" + mSiteId + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("SiteWorkflow");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mSiteWorkflowId));

        node =  doc.createElement("WorkflowId");
        node.appendChild(doc.createTextNode(String.valueOf(mWorkflowId)));
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

        node =  doc.createElement("SiteId");
        node.appendChild(doc.createTextNode(String.valueOf(mSiteId)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the SiteWorkflowId field is not cloned.
    *
    * @return SiteWorkflowData object
    */
    public Object clone(){
        SiteWorkflowData myClone = new SiteWorkflowData();
        
        myClone.mWorkflowId = mWorkflowId;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        myClone.mSiteId = mSiteId;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (SiteWorkflowDataAccess.SITE_WORKFLOW_ID.equals(pFieldName)) {
            return getSiteWorkflowId();
        } else if (SiteWorkflowDataAccess.WORKFLOW_ID.equals(pFieldName)) {
            return getWorkflowId();
        } else if (SiteWorkflowDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (SiteWorkflowDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (SiteWorkflowDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (SiteWorkflowDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (SiteWorkflowDataAccess.SITE_ID.equals(pFieldName)) {
            return getSiteId();
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
        return SiteWorkflowDataAccess.CLW_SITE_WORKFLOW;
    }

    
    /**
     * Sets the SiteWorkflowId field. This field is required to be set in the database.
     *
     * @param pSiteWorkflowId
     *  int to use to update the field.
     */
    public void setSiteWorkflowId(int pSiteWorkflowId){
        this.mSiteWorkflowId = pSiteWorkflowId;
        setDirty(true);
    }
    /**
     * Retrieves the SiteWorkflowId field.
     *
     * @return
     *  int containing the SiteWorkflowId field.
     */
    public int getSiteWorkflowId(){
        return mSiteWorkflowId;
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
     * Sets the SiteId field.
     *
     * @param pSiteId
     *  int to use to update the field.
     */
    public void setSiteId(int pSiteId){
        this.mSiteId = pSiteId;
        setDirty(true);
    }
    /**
     * Retrieves the SiteId field.
     *
     * @return
     *  int containing the SiteId field.
     */
    public int getSiteId(){
        return mSiteId;
    }


}
