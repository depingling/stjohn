
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        DispatchWorkOrderData
 * Description:  This is a ValueObject class wrapping the database table CLW_DISPATCH_WORK_ORDER.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.DispatchWorkOrderDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>DispatchWorkOrderData</code> is a ValueObject class wrapping of the database table CLW_DISPATCH_WORK_ORDER.
 */
public class DispatchWorkOrderData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 7272822164720327434L;
    private int mDispatchWorkOrderId;// SQL type:NUMBER, not null
    private int mWorkOrderId;// SQL type:NUMBER, not null
    private int mDispatchId;// SQL type:NUMBER, not null
    private String mTypeCd;// SQL type:VARCHAR2, not null
    private String mStatusCd;// SQL type:VARCHAR2, not null
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public DispatchWorkOrderData ()
    {
        mTypeCd = "";
        mStatusCd = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public DispatchWorkOrderData(int parm1, int parm2, int parm3, String parm4, String parm5, Date parm6, String parm7, Date parm8, String parm9)
    {
        mDispatchWorkOrderId = parm1;
        mWorkOrderId = parm2;
        mDispatchId = parm3;
        mTypeCd = parm4;
        mStatusCd = parm5;
        mAddDate = parm6;
        mAddBy = parm7;
        mModDate = parm8;
        mModBy = parm9;
        
    }

    /**
     * Creates a new DispatchWorkOrderData
     *
     * @return
     *  Newly initialized DispatchWorkOrderData object.
     */
    public static DispatchWorkOrderData createValue ()
    {
        DispatchWorkOrderData valueData = new DispatchWorkOrderData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this DispatchWorkOrderData object
     */
    public String toString()
    {
        return "[" + "DispatchWorkOrderId=" + mDispatchWorkOrderId + ", WorkOrderId=" + mWorkOrderId + ", DispatchId=" + mDispatchId + ", TypeCd=" + mTypeCd + ", StatusCd=" + mStatusCd + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("DispatchWorkOrder");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mDispatchWorkOrderId));

        node =  doc.createElement("WorkOrderId");
        node.appendChild(doc.createTextNode(String.valueOf(mWorkOrderId)));
        root.appendChild(node);

        node =  doc.createElement("DispatchId");
        node.appendChild(doc.createTextNode(String.valueOf(mDispatchId)));
        root.appendChild(node);

        node =  doc.createElement("TypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mTypeCd)));
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
    * creates a clone of this object, the DispatchWorkOrderId field is not cloned.
    *
    * @return DispatchWorkOrderData object
    */
    public Object clone(){
        DispatchWorkOrderData myClone = new DispatchWorkOrderData();
        
        myClone.mWorkOrderId = mWorkOrderId;
        
        myClone.mDispatchId = mDispatchId;
        
        myClone.mTypeCd = mTypeCd;
        
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

        if (DispatchWorkOrderDataAccess.DISPATCH_WORK_ORDER_ID.equals(pFieldName)) {
            return getDispatchWorkOrderId();
        } else if (DispatchWorkOrderDataAccess.WORK_ORDER_ID.equals(pFieldName)) {
            return getWorkOrderId();
        } else if (DispatchWorkOrderDataAccess.DISPATCH_ID.equals(pFieldName)) {
            return getDispatchId();
        } else if (DispatchWorkOrderDataAccess.TYPE_CD.equals(pFieldName)) {
            return getTypeCd();
        } else if (DispatchWorkOrderDataAccess.STATUS_CD.equals(pFieldName)) {
            return getStatusCd();
        } else if (DispatchWorkOrderDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (DispatchWorkOrderDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (DispatchWorkOrderDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (DispatchWorkOrderDataAccess.MOD_BY.equals(pFieldName)) {
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
        return DispatchWorkOrderDataAccess.CLW_DISPATCH_WORK_ORDER;
    }

    
    /**
     * Sets the DispatchWorkOrderId field. This field is required to be set in the database.
     *
     * @param pDispatchWorkOrderId
     *  int to use to update the field.
     */
    public void setDispatchWorkOrderId(int pDispatchWorkOrderId){
        this.mDispatchWorkOrderId = pDispatchWorkOrderId;
        setDirty(true);
    }
    /**
     * Retrieves the DispatchWorkOrderId field.
     *
     * @return
     *  int containing the DispatchWorkOrderId field.
     */
    public int getDispatchWorkOrderId(){
        return mDispatchWorkOrderId;
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
     * Sets the DispatchId field. This field is required to be set in the database.
     *
     * @param pDispatchId
     *  int to use to update the field.
     */
    public void setDispatchId(int pDispatchId){
        this.mDispatchId = pDispatchId;
        setDirty(true);
    }
    /**
     * Retrieves the DispatchId field.
     *
     * @return
     *  int containing the DispatchId field.
     */
    public int getDispatchId(){
        return mDispatchId;
    }

    /**
     * Sets the TypeCd field. This field is required to be set in the database.
     *
     * @param pTypeCd
     *  String to use to update the field.
     */
    public void setTypeCd(String pTypeCd){
        this.mTypeCd = pTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the TypeCd field.
     *
     * @return
     *  String containing the TypeCd field.
     */
    public String getTypeCd(){
        return mTypeCd;
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
