
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        WorkOrderPropertyData
 * Description:  This is a ValueObject class wrapping the database table CLW_WORK_ORDER_PROPERTY.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.WorkOrderPropertyDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>WorkOrderPropertyData</code> is a ValueObject class wrapping of the database table CLW_WORK_ORDER_PROPERTY.
 */
public class WorkOrderPropertyData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -1L;
    private int mWorkOrderPropertyId;// SQL type:NUMBER, not null
    private int mWorkOrderId;// SQL type:NUMBER
    private int mWorkOrderItemId;// SQL type:NUMBER
    private String mShortDesc;// SQL type:VARCHAR2
    private String mPropertyCd;// SQL type:VARCHAR2, not null
    private String mStatusCd;// SQL type:VARCHAR2, not null
    private String mValue;// SQL type:VARCHAR2
    private String mAddBy;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null

    /**
     * Constructor.
     */
    public WorkOrderPropertyData ()
    {
        mShortDesc = "";
        mPropertyCd = "";
        mStatusCd = "";
        mValue = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public WorkOrderPropertyData(int parm1, int parm2, int parm3, String parm4, String parm5, String parm6, String parm7, String parm8, Date parm9, String parm10, Date parm11)
    {
        mWorkOrderPropertyId = parm1;
        mWorkOrderId = parm2;
        mWorkOrderItemId = parm3;
        mShortDesc = parm4;
        mPropertyCd = parm5;
        mStatusCd = parm6;
        mValue = parm7;
        mAddBy = parm8;
        mAddDate = parm9;
        mModBy = parm10;
        mModDate = parm11;
        
    }

    /**
     * Creates a new WorkOrderPropertyData
     *
     * @return
     *  Newly initialized WorkOrderPropertyData object.
     */
    public static WorkOrderPropertyData createValue ()
    {
        WorkOrderPropertyData valueData = new WorkOrderPropertyData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this WorkOrderPropertyData object
     */
    public String toString()
    {
        return "[" + "WorkOrderPropertyId=" + mWorkOrderPropertyId + ", WorkOrderId=" + mWorkOrderId + ", WorkOrderItemId=" + mWorkOrderItemId + ", ShortDesc=" + mShortDesc + ", PropertyCd=" + mPropertyCd + ", StatusCd=" + mStatusCd + ", Value=" + mValue + ", AddBy=" + mAddBy + ", AddDate=" + mAddDate + ", ModBy=" + mModBy + ", ModDate=" + mModDate + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("WorkOrderProperty");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mWorkOrderPropertyId));

        node =  doc.createElement("WorkOrderId");
        node.appendChild(doc.createTextNode(String.valueOf(mWorkOrderId)));
        root.appendChild(node);

        node =  doc.createElement("WorkOrderItemId");
        node.appendChild(doc.createTextNode(String.valueOf(mWorkOrderItemId)));
        root.appendChild(node);

        node =  doc.createElement("ShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mShortDesc)));
        root.appendChild(node);

        node =  doc.createElement("PropertyCd");
        node.appendChild(doc.createTextNode(String.valueOf(mPropertyCd)));
        root.appendChild(node);

        node =  doc.createElement("StatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mStatusCd)));
        root.appendChild(node);

        node =  doc.createElement("Value");
        node.appendChild(doc.createTextNode(String.valueOf(mValue)));
        root.appendChild(node);

        node =  doc.createElement("AddBy");
        node.appendChild(doc.createTextNode(String.valueOf(mAddBy)));
        root.appendChild(node);

        node =  doc.createElement("AddDate");
        node.appendChild(doc.createTextNode(String.valueOf(mAddDate)));
        root.appendChild(node);

        node =  doc.createElement("ModBy");
        node.appendChild(doc.createTextNode(String.valueOf(mModBy)));
        root.appendChild(node);

        node =  doc.createElement("ModDate");
        node.appendChild(doc.createTextNode(String.valueOf(mModDate)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the WorkOrderPropertyId field is not cloned.
    *
    * @return WorkOrderPropertyData object
    */
    public Object clone(){
        WorkOrderPropertyData myClone = new WorkOrderPropertyData();
        
        myClone.mWorkOrderId = mWorkOrderId;
        
        myClone.mWorkOrderItemId = mWorkOrderItemId;
        
        myClone.mShortDesc = mShortDesc;
        
        myClone.mPropertyCd = mPropertyCd;
        
        myClone.mStatusCd = mStatusCd;
        
        myClone.mValue = mValue;
        
        myClone.mAddBy = mAddBy;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
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

        if (WorkOrderPropertyDataAccess.WORK_ORDER_PROPERTY_ID.equals(pFieldName)) {
            return getWorkOrderPropertyId();
        } else if (WorkOrderPropertyDataAccess.WORK_ORDER_ID.equals(pFieldName)) {
            return getWorkOrderId();
        } else if (WorkOrderPropertyDataAccess.WORK_ORDER_ITEM_ID.equals(pFieldName)) {
            return getWorkOrderItemId();
        } else if (WorkOrderPropertyDataAccess.SHORT_DESC.equals(pFieldName)) {
            return getShortDesc();
        } else if (WorkOrderPropertyDataAccess.PROPERTY_CD.equals(pFieldName)) {
            return getPropertyCd();
        } else if (WorkOrderPropertyDataAccess.STATUS_CD.equals(pFieldName)) {
            return getStatusCd();
        } else if (WorkOrderPropertyDataAccess.CLW_VALUE.equals(pFieldName)) {
            return getValue();
        } else if (WorkOrderPropertyDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (WorkOrderPropertyDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (WorkOrderPropertyDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (WorkOrderPropertyDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
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
        return WorkOrderPropertyDataAccess.CLW_WORK_ORDER_PROPERTY;
    }

    
    /**
     * Sets the WorkOrderPropertyId field. This field is required to be set in the database.
     *
     * @param pWorkOrderPropertyId
     *  int to use to update the field.
     */
    public void setWorkOrderPropertyId(int pWorkOrderPropertyId){
        this.mWorkOrderPropertyId = pWorkOrderPropertyId;
        setDirty(true);
    }
    /**
     * Retrieves the WorkOrderPropertyId field.
     *
     * @return
     *  int containing the WorkOrderPropertyId field.
     */
    public int getWorkOrderPropertyId(){
        return mWorkOrderPropertyId;
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
     * Sets the ShortDesc field.
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
     * Sets the PropertyCd field. This field is required to be set in the database.
     *
     * @param pPropertyCd
     *  String to use to update the field.
     */
    public void setPropertyCd(String pPropertyCd){
        this.mPropertyCd = pPropertyCd;
        setDirty(true);
    }
    /**
     * Retrieves the PropertyCd field.
     *
     * @return
     *  String containing the PropertyCd field.
     */
    public String getPropertyCd(){
        return mPropertyCd;
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
     * Sets the Value field.
     *
     * @param pValue
     *  String to use to update the field.
     */
    public void setValue(String pValue){
        this.mValue = pValue;
        setDirty(true);
    }
    /**
     * Retrieves the Value field.
     *
     * @return
     *  String containing the Value field.
     */
    public String getValue(){
        return mValue;
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


}
