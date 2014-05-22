
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        CallPropertyData
 * Description:  This is a ValueObject class wrapping the database table CLW_CALL_PROPERTY.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.CallPropertyDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>CallPropertyData</code> is a ValueObject class wrapping of the database table CLW_CALL_PROPERTY.
 */
public class CallPropertyData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -7133127836009652695L;
    private int mCallPropertyId;// SQL type:NUMBER, not null
    private int mCallId;// SQL type:NUMBER
    private String mShortDesc;// SQL type:VARCHAR2
    private String mValue;// SQL type:VARCHAR2
    private String mCallPropertyStatusCd;// SQL type:VARCHAR2, not null
    private String mCallPropertyTypeCd;// SQL type:VARCHAR2, not null
    private Date mAddDate;// SQL type:DATE, not null
    private Date mAddTime;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public CallPropertyData ()
    {
        mShortDesc = "";
        mValue = "";
        mCallPropertyStatusCd = "";
        mCallPropertyTypeCd = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public CallPropertyData(int parm1, int parm2, String parm3, String parm4, String parm5, String parm6, Date parm7, Date parm8, String parm9, Date parm10, String parm11)
    {
        mCallPropertyId = parm1;
        mCallId = parm2;
        mShortDesc = parm3;
        mValue = parm4;
        mCallPropertyStatusCd = parm5;
        mCallPropertyTypeCd = parm6;
        mAddDate = parm7;
        mAddTime = parm8;
        mAddBy = parm9;
        mModDate = parm10;
        mModBy = parm11;
        
    }

    /**
     * Creates a new CallPropertyData
     *
     * @return
     *  Newly initialized CallPropertyData object.
     */
    public static CallPropertyData createValue ()
    {
        CallPropertyData valueData = new CallPropertyData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this CallPropertyData object
     */
    public String toString()
    {
        return "[" + "CallPropertyId=" + mCallPropertyId + ", CallId=" + mCallId + ", ShortDesc=" + mShortDesc + ", Value=" + mValue + ", CallPropertyStatusCd=" + mCallPropertyStatusCd + ", CallPropertyTypeCd=" + mCallPropertyTypeCd + ", AddDate=" + mAddDate + ", AddTime=" + mAddTime + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("CallProperty");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mCallPropertyId));

        node =  doc.createElement("CallId");
        node.appendChild(doc.createTextNode(String.valueOf(mCallId)));
        root.appendChild(node);

        node =  doc.createElement("ShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mShortDesc)));
        root.appendChild(node);

        node =  doc.createElement("Value");
        node.appendChild(doc.createTextNode(String.valueOf(mValue)));
        root.appendChild(node);

        node =  doc.createElement("CallPropertyStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mCallPropertyStatusCd)));
        root.appendChild(node);

        node =  doc.createElement("CallPropertyTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mCallPropertyTypeCd)));
        root.appendChild(node);

        node =  doc.createElement("AddDate");
        node.appendChild(doc.createTextNode(String.valueOf(mAddDate)));
        root.appendChild(node);

        node =  doc.createElement("AddTime");
        node.appendChild(doc.createTextNode(String.valueOf(mAddTime)));
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
    * creates a clone of this object, the CallPropertyId field is not cloned.
    *
    * @return CallPropertyData object
    */
    public Object clone(){
        CallPropertyData myClone = new CallPropertyData();
        
        myClone.mCallId = mCallId;
        
        myClone.mShortDesc = mShortDesc;
        
        myClone.mValue = mValue;
        
        myClone.mCallPropertyStatusCd = mCallPropertyStatusCd;
        
        myClone.mCallPropertyTypeCd = mCallPropertyTypeCd;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        if(mAddTime != null){
                myClone.mAddTime = (Date) mAddTime.clone();
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

        if (CallPropertyDataAccess.CALL_PROPERTY_ID.equals(pFieldName)) {
            return getCallPropertyId();
        } else if (CallPropertyDataAccess.CALL_ID.equals(pFieldName)) {
            return getCallId();
        } else if (CallPropertyDataAccess.SHORT_DESC.equals(pFieldName)) {
            return getShortDesc();
        } else if (CallPropertyDataAccess.CLW_VALUE.equals(pFieldName)) {
            return getValue();
        } else if (CallPropertyDataAccess.CALL_PROPERTY_STATUS_CD.equals(pFieldName)) {
            return getCallPropertyStatusCd();
        } else if (CallPropertyDataAccess.CALL_PROPERTY_TYPE_CD.equals(pFieldName)) {
            return getCallPropertyTypeCd();
        } else if (CallPropertyDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (CallPropertyDataAccess.ADD_TIME.equals(pFieldName)) {
            return getAddTime();
        } else if (CallPropertyDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (CallPropertyDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (CallPropertyDataAccess.MOD_BY.equals(pFieldName)) {
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
        return CallPropertyDataAccess.CLW_CALL_PROPERTY;
    }

    
    /**
     * Sets the CallPropertyId field. This field is required to be set in the database.
     *
     * @param pCallPropertyId
     *  int to use to update the field.
     */
    public void setCallPropertyId(int pCallPropertyId){
        this.mCallPropertyId = pCallPropertyId;
        setDirty(true);
    }
    /**
     * Retrieves the CallPropertyId field.
     *
     * @return
     *  int containing the CallPropertyId field.
     */
    public int getCallPropertyId(){
        return mCallPropertyId;
    }

    /**
     * Sets the CallId field.
     *
     * @param pCallId
     *  int to use to update the field.
     */
    public void setCallId(int pCallId){
        this.mCallId = pCallId;
        setDirty(true);
    }
    /**
     * Retrieves the CallId field.
     *
     * @return
     *  int containing the CallId field.
     */
    public int getCallId(){
        return mCallId;
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
     * Sets the CallPropertyStatusCd field. This field is required to be set in the database.
     *
     * @param pCallPropertyStatusCd
     *  String to use to update the field.
     */
    public void setCallPropertyStatusCd(String pCallPropertyStatusCd){
        this.mCallPropertyStatusCd = pCallPropertyStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the CallPropertyStatusCd field.
     *
     * @return
     *  String containing the CallPropertyStatusCd field.
     */
    public String getCallPropertyStatusCd(){
        return mCallPropertyStatusCd;
    }

    /**
     * Sets the CallPropertyTypeCd field. This field is required to be set in the database.
     *
     * @param pCallPropertyTypeCd
     *  String to use to update the field.
     */
    public void setCallPropertyTypeCd(String pCallPropertyTypeCd){
        this.mCallPropertyTypeCd = pCallPropertyTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the CallPropertyTypeCd field.
     *
     * @return
     *  String containing the CallPropertyTypeCd field.
     */
    public String getCallPropertyTypeCd(){
        return mCallPropertyTypeCd;
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
     * Sets the AddTime field. This field is required to be set in the database.
     *
     * @param pAddTime
     *  Date to use to update the field.
     */
    public void setAddTime(Date pAddTime){
        this.mAddTime = pAddTime;
        setDirty(true);
    }
    /**
     * Retrieves the AddTime field.
     *
     * @return
     *  Date containing the AddTime field.
     */
    public Date getAddTime(){
        return mAddTime;
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
