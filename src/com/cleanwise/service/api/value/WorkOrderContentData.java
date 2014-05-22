
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        WorkOrderContentData
 * Description:  This is a ValueObject class wrapping the database table CLW_WORK_ORDER_CONTENT.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.WorkOrderContentDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>WorkOrderContentData</code> is a ValueObject class wrapping of the database table CLW_WORK_ORDER_CONTENT.
 */
public class WorkOrderContentData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 9119473467730990774L;
    private int mWorkOrderContentId;// SQL type:NUMBER, not null
    private int mWorkOrderId;// SQL type:NUMBER, not null
    private int mWorkOrderItemId;// SQL type:NUMBER
    private int mContentId;// SQL type:NUMBER
    private String mUrl;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public WorkOrderContentData ()
    {
        mUrl = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public WorkOrderContentData(int parm1, int parm2, int parm3, int parm4, String parm5, Date parm6, String parm7, Date parm8, String parm9)
    {
        mWorkOrderContentId = parm1;
        mWorkOrderId = parm2;
        mWorkOrderItemId = parm3;
        mContentId = parm4;
        mUrl = parm5;
        mAddDate = parm6;
        mAddBy = parm7;
        mModDate = parm8;
        mModBy = parm9;
        
    }

    /**
     * Creates a new WorkOrderContentData
     *
     * @return
     *  Newly initialized WorkOrderContentData object.
     */
    public static WorkOrderContentData createValue ()
    {
        WorkOrderContentData valueData = new WorkOrderContentData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this WorkOrderContentData object
     */
    public String toString()
    {
        return "[" + "WorkOrderContentId=" + mWorkOrderContentId + ", WorkOrderId=" + mWorkOrderId + ", WorkOrderItemId=" + mWorkOrderItemId + ", ContentId=" + mContentId + ", Url=" + mUrl + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("WorkOrderContent");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mWorkOrderContentId));

        node =  doc.createElement("WorkOrderId");
        node.appendChild(doc.createTextNode(String.valueOf(mWorkOrderId)));
        root.appendChild(node);

        node =  doc.createElement("WorkOrderItemId");
        node.appendChild(doc.createTextNode(String.valueOf(mWorkOrderItemId)));
        root.appendChild(node);

        node =  doc.createElement("ContentId");
        node.appendChild(doc.createTextNode(String.valueOf(mContentId)));
        root.appendChild(node);

        node =  doc.createElement("Url");
        node.appendChild(doc.createTextNode(String.valueOf(mUrl)));
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
    * creates a clone of this object, the WorkOrderContentId field is not cloned.
    *
    * @return WorkOrderContentData object
    */
    public Object clone(){
        WorkOrderContentData myClone = new WorkOrderContentData();
        
        myClone.mWorkOrderId = mWorkOrderId;
        
        myClone.mWorkOrderItemId = mWorkOrderItemId;
        
        myClone.mContentId = mContentId;
        
        myClone.mUrl = mUrl;
        
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

        if (WorkOrderContentDataAccess.WORK_ORDER_CONTENT_ID.equals(pFieldName)) {
            return getWorkOrderContentId();
        } else if (WorkOrderContentDataAccess.WORK_ORDER_ID.equals(pFieldName)) {
            return getWorkOrderId();
        } else if (WorkOrderContentDataAccess.WORK_ORDER_ITEM_ID.equals(pFieldName)) {
            return getWorkOrderItemId();
        } else if (WorkOrderContentDataAccess.CONTENT_ID.equals(pFieldName)) {
            return getContentId();
        } else if (WorkOrderContentDataAccess.URL.equals(pFieldName)) {
            return getUrl();
        } else if (WorkOrderContentDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (WorkOrderContentDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (WorkOrderContentDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (WorkOrderContentDataAccess.MOD_BY.equals(pFieldName)) {
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
        return WorkOrderContentDataAccess.CLW_WORK_ORDER_CONTENT;
    }

    
    /**
     * Sets the WorkOrderContentId field. This field is required to be set in the database.
     *
     * @param pWorkOrderContentId
     *  int to use to update the field.
     */
    public void setWorkOrderContentId(int pWorkOrderContentId){
        this.mWorkOrderContentId = pWorkOrderContentId;
        setDirty(true);
    }
    /**
     * Retrieves the WorkOrderContentId field.
     *
     * @return
     *  int containing the WorkOrderContentId field.
     */
    public int getWorkOrderContentId(){
        return mWorkOrderContentId;
    }

    /**
     * Sets the WorkOrderId field. This field is required to be set in the database.
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
     * Sets the WorkOrderItemId field.
     *
     * @param pWorkOrderItemId
     *  int to use to update the field.
     */
    public void setWorkOrderItemId(int pWorkOrderItemId){
        this.mWorkOrderItemId = pWorkOrderItemId;
        setDirty(true);
    }
    /**
     * Retrieves the WorkOrderItemId field.
     *
     * @return
     *  int containing the WorkOrderItemId field.
     */
    public int getWorkOrderItemId(){
        return mWorkOrderItemId;
    }

    /**
     * Sets the ContentId field.
     *
     * @param pContentId
     *  int to use to update the field.
     */
    public void setContentId(int pContentId){
        this.mContentId = pContentId;
        setDirty(true);
    }
    /**
     * Retrieves the ContentId field.
     *
     * @return
     *  int containing the ContentId field.
     */
    public int getContentId(){
        return mContentId;
    }

    /**
     * Sets the Url field.
     *
     * @param pUrl
     *  String to use to update the field.
     */
    public void setUrl(String pUrl){
        this.mUrl = pUrl;
        setDirty(true);
    }
    /**
     * Retrieves the Url field.
     *
     * @return
     *  String containing the Url field.
     */
    public String getUrl(){
        return mUrl;
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
