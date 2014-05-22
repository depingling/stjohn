
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        WorkOrderAssetData
 * Description:  This is a ValueObject class wrapping the database table CLW_WORK_ORDER_ASSET.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.WorkOrderAssetDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>WorkOrderAssetData</code> is a ValueObject class wrapping of the database table CLW_WORK_ORDER_ASSET.
 */
public class WorkOrderAssetData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -1636111484213176549L;
    private int mWorkOrderAssetId;// SQL type:NUMBER, not null
    private int mWorkOrderItemId;// SQL type:NUMBER, not null
    private int mAssetId;// SQL type:NUMBER, not null
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public WorkOrderAssetData ()
    {
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public WorkOrderAssetData(int parm1, int parm2, int parm3, Date parm4, String parm5, Date parm6, String parm7)
    {
        mWorkOrderAssetId = parm1;
        mWorkOrderItemId = parm2;
        mAssetId = parm3;
        mAddDate = parm4;
        mAddBy = parm5;
        mModDate = parm6;
        mModBy = parm7;
        
    }

    /**
     * Creates a new WorkOrderAssetData
     *
     * @return
     *  Newly initialized WorkOrderAssetData object.
     */
    public static WorkOrderAssetData createValue ()
    {
        WorkOrderAssetData valueData = new WorkOrderAssetData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this WorkOrderAssetData object
     */
    public String toString()
    {
        return "[" + "WorkOrderAssetId=" + mWorkOrderAssetId + ", WorkOrderItemId=" + mWorkOrderItemId + ", AssetId=" + mAssetId + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("WorkOrderAsset");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mWorkOrderAssetId));

        node =  doc.createElement("WorkOrderItemId");
        node.appendChild(doc.createTextNode(String.valueOf(mWorkOrderItemId)));
        root.appendChild(node);

        node =  doc.createElement("AssetId");
        node.appendChild(doc.createTextNode(String.valueOf(mAssetId)));
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
    * creates a clone of this object, the WorkOrderAssetId field is not cloned.
    *
    * @return WorkOrderAssetData object
    */
    public Object clone(){
        WorkOrderAssetData myClone = new WorkOrderAssetData();
        
        myClone.mWorkOrderItemId = mWorkOrderItemId;
        
        myClone.mAssetId = mAssetId;
        
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

        if (WorkOrderAssetDataAccess.WORK_ORDER_ASSET_ID.equals(pFieldName)) {
            return getWorkOrderAssetId();
        } else if (WorkOrderAssetDataAccess.WORK_ORDER_ITEM_ID.equals(pFieldName)) {
            return getWorkOrderItemId();
        } else if (WorkOrderAssetDataAccess.ASSET_ID.equals(pFieldName)) {
            return getAssetId();
        } else if (WorkOrderAssetDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (WorkOrderAssetDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (WorkOrderAssetDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (WorkOrderAssetDataAccess.MOD_BY.equals(pFieldName)) {
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
        return WorkOrderAssetDataAccess.CLW_WORK_ORDER_ASSET;
    }

    
    /**
     * Sets the WorkOrderAssetId field. This field is required to be set in the database.
     *
     * @param pWorkOrderAssetId
     *  int to use to update the field.
     */
    public void setWorkOrderAssetId(int pWorkOrderAssetId){
        this.mWorkOrderAssetId = pWorkOrderAssetId;
        setDirty(true);
    }
    /**
     * Retrieves the WorkOrderAssetId field.
     *
     * @return
     *  int containing the WorkOrderAssetId field.
     */
    public int getWorkOrderAssetId(){
        return mWorkOrderAssetId;
    }

    /**
     * Sets the WorkOrderItemId field. This field is required to be set in the database.
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
     * Sets the AssetId field. This field is required to be set in the database.
     *
     * @param pAssetId
     *  int to use to update the field.
     */
    public void setAssetId(int pAssetId){
        this.mAssetId = pAssetId;
        setDirty(true);
    }
    /**
     * Retrieves the AssetId field.
     *
     * @return
     *  int containing the AssetId field.
     */
    public int getAssetId(){
        return mAssetId;
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
