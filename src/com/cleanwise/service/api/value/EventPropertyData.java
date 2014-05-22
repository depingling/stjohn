
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        EventPropertyData
 * Description:  This is a ValueObject class wrapping the database table CLW_EVENT_PROPERTY.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.EventPropertyDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>EventPropertyData</code> is a ValueObject class wrapping of the database table CLW_EVENT_PROPERTY.
 */
public class EventPropertyData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -7287214697078413155L;
    private int mEventPropertyId;// SQL type:NUMBER, not null
    private int mEventId;// SQL type:NUMBER, not null
    private String mShortDesc;// SQL type:VARCHAR2, not null
    private String mType;// SQL type:VARCHAR2, not null
    private byte[] mBlobValue;// SQL type:BLOB
    private int mNum;// SQL type:NUMBER, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private int mNumberVal;// SQL type:NUMBER
    private String mStringVal;// SQL type:VARCHAR2
    private Date mDateVal;// SQL type:DATE
    private String mVarClass;// SQL type:VARCHAR2
    private String mBinaryDataServer;// SQL type:VARCHAR2
    private String mBlobValueSystemRef;// SQL type:VARCHAR2
    private String mStorageTypeCd;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public EventPropertyData ()
    {
        mShortDesc = "";
        mType = "";
        mAddBy = "";
        mModBy = "";
        mStringVal = "";
        mVarClass = "";
        mBinaryDataServer = "";
        mBlobValueSystemRef = "";
        mStorageTypeCd = "";
    }

    /**
     * Constructor.
     */
    public EventPropertyData(int parm1, int parm2, String parm3, String parm4, byte[] parm5, int parm6, String parm7, Date parm8, String parm9, Date parm10, int parm11, String parm12, Date parm13, String parm14, String parm15, String parm16, String parm17)
    {
        mEventPropertyId = parm1;
        mEventId = parm2;
        mShortDesc = parm3;
        mType = parm4;
        mBlobValue = parm5;
        mNum = parm6;
        mAddBy = parm7;
        mAddDate = parm8;
        mModBy = parm9;
        mModDate = parm10;
        mNumberVal = parm11;
        mStringVal = parm12;
        mDateVal = parm13;
        mVarClass = parm14;
        mBinaryDataServer = parm15;
        mBlobValueSystemRef = parm16;
        mStorageTypeCd = parm17;
        
    }

    /**
     * Creates a new EventPropertyData
     *
     * @return
     *  Newly initialized EventPropertyData object.
     */
    public static EventPropertyData createValue ()
    {
        EventPropertyData valueData = new EventPropertyData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this EventPropertyData object
     */
    public String toString()
    {
        return "[" + "EventPropertyId=" + mEventPropertyId + ", EventId=" + mEventId + ", ShortDesc=" + mShortDesc + ", Type=" + mType + ", BlobValue=" + mBlobValue + ", Num=" + mNum + ", AddBy=" + mAddBy + ", AddDate=" + mAddDate + ", ModBy=" + mModBy + ", ModDate=" + mModDate + ", NumberVal=" + mNumberVal + ", StringVal=" + mStringVal + ", DateVal=" + mDateVal + ", VarClass=" + mVarClass + ", BinaryDataServer=" + mBinaryDataServer + ", BlobValueSystemRef=" + mBlobValueSystemRef + ", StorageTypeCd=" + mStorageTypeCd + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("EventProperty");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mEventPropertyId));

        node =  doc.createElement("EventId");
        node.appendChild(doc.createTextNode(String.valueOf(mEventId)));
        root.appendChild(node);

        node =  doc.createElement("ShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mShortDesc)));
        root.appendChild(node);

        node =  doc.createElement("Type");
        node.appendChild(doc.createTextNode(String.valueOf(mType)));
        root.appendChild(node);

        node =  doc.createElement("BlobValue");
        node.appendChild(doc.createTextNode(String.valueOf(mBlobValue)));
        root.appendChild(node);

        node =  doc.createElement("Num");
        node.appendChild(doc.createTextNode(String.valueOf(mNum)));
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

        node =  doc.createElement("NumberVal");
        node.appendChild(doc.createTextNode(String.valueOf(mNumberVal)));
        root.appendChild(node);

        node =  doc.createElement("StringVal");
        node.appendChild(doc.createTextNode(String.valueOf(mStringVal)));
        root.appendChild(node);

        node =  doc.createElement("DateVal");
        node.appendChild(doc.createTextNode(String.valueOf(mDateVal)));
        root.appendChild(node);

        node =  doc.createElement("VarClass");
        node.appendChild(doc.createTextNode(String.valueOf(mVarClass)));
        root.appendChild(node);

        node =  doc.createElement("BinaryDataServer");
        node.appendChild(doc.createTextNode(String.valueOf(mBinaryDataServer)));
        root.appendChild(node);

        node =  doc.createElement("BlobValueSystemRef");
        node.appendChild(doc.createTextNode(String.valueOf(mBlobValueSystemRef)));
        root.appendChild(node);

        node =  doc.createElement("StorageTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mStorageTypeCd)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the EventPropertyId field is not cloned.
    *
    * @return EventPropertyData object
    */
    public Object clone(){
        EventPropertyData myClone = new EventPropertyData();
        
        myClone.mEventId = mEventId;
        
        myClone.mShortDesc = mShortDesc;
        
        myClone.mType = mType;
        
        myClone.mBlobValue = mBlobValue;
        
        myClone.mNum = mNum;
        
        myClone.mAddBy = mAddBy;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mNumberVal = mNumberVal;
        
        myClone.mStringVal = mStringVal;
        
        if(mDateVal != null){
                myClone.mDateVal = (Date) mDateVal.clone();
        }
        
        myClone.mVarClass = mVarClass;
        
        myClone.mBinaryDataServer = mBinaryDataServer;
        
        myClone.mBlobValueSystemRef = mBlobValueSystemRef;
        
        myClone.mStorageTypeCd = mStorageTypeCd;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (EventPropertyDataAccess.EVENT_PROPERTY_ID.equals(pFieldName)) {
            return getEventPropertyId();
        } else if (EventPropertyDataAccess.EVENT_ID.equals(pFieldName)) {
            return getEventId();
        } else if (EventPropertyDataAccess.SHORT_DESC.equals(pFieldName)) {
            return getShortDesc();
        } else if (EventPropertyDataAccess.TYPE.equals(pFieldName)) {
            return getType();
        } else if (EventPropertyDataAccess.BLOB_VALUE.equals(pFieldName)) {
            return getBlobValue();
        } else if (EventPropertyDataAccess.NUM.equals(pFieldName)) {
            return getNum();
        } else if (EventPropertyDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (EventPropertyDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (EventPropertyDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (EventPropertyDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (EventPropertyDataAccess.NUMBER_VAL.equals(pFieldName)) {
            return getNumberVal();
        } else if (EventPropertyDataAccess.STRING_VAL.equals(pFieldName)) {
            return getStringVal();
        } else if (EventPropertyDataAccess.DATE_VAL.equals(pFieldName)) {
            return getDateVal();
        } else if (EventPropertyDataAccess.VAR_CLASS.equals(pFieldName)) {
            return getVarClass();
        } else if (EventPropertyDataAccess.BINARY_DATA_SERVER.equals(pFieldName)) {
            return getBinaryDataServer();
        } else if (EventPropertyDataAccess.BLOB_VALUE_SYSTEM_REF.equals(pFieldName)) {
            return getBlobValueSystemRef();
        } else if (EventPropertyDataAccess.STORAGE_TYPE_CD.equals(pFieldName)) {
            return getStorageTypeCd();
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
        return EventPropertyDataAccess.CLW_EVENT_PROPERTY;
    }

    
    /**
     * Sets the EventPropertyId field. This field is required to be set in the database.
     *
     * @param pEventPropertyId
     *  int to use to update the field.
     */
    public void setEventPropertyId(int pEventPropertyId){
        this.mEventPropertyId = pEventPropertyId;
        setDirty(true);
    }
    /**
     * Retrieves the EventPropertyId field.
     *
     * @return
     *  int containing the EventPropertyId field.
     */
    public int getEventPropertyId(){
        return mEventPropertyId;
    }

    /**
     * Sets the EventId field. This field is required to be set in the database.
     *
     * @param pEventId
     *  int to use to update the field.
     */
    public void setEventId(int pEventId){
        this.mEventId = pEventId;
        setDirty(true);
    }
    /**
     * Retrieves the EventId field.
     *
     * @return
     *  int containing the EventId field.
     */
    public int getEventId(){
        return mEventId;
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
     * Sets the Type field. This field is required to be set in the database.
     *
     * @param pType
     *  String to use to update the field.
     */
    public void setType(String pType){
        this.mType = pType;
        setDirty(true);
    }
    /**
     * Retrieves the Type field.
     *
     * @return
     *  String containing the Type field.
     */
    public String getType(){
        return mType;
    }

    /**
     * Sets the BlobValue field.
     *
     * @param pBlobValue
     *  byte[] to use to update the field.
     */
    public void setBlobValue(byte[] pBlobValue){
        this.mBlobValue = pBlobValue;
        setDirty(true);
    }
    /**
     * Retrieves the BlobValue field.
     *
     * @return
     *  byte[] containing the BlobValue field.
     */
    public byte[] getBlobValue(){
        return mBlobValue;
    }

    /**
     * Sets the Num field. This field is required to be set in the database.
     *
     * @param pNum
     *  int to use to update the field.
     */
    public void setNum(int pNum){
        this.mNum = pNum;
        setDirty(true);
    }
    /**
     * Retrieves the Num field.
     *
     * @return
     *  int containing the Num field.
     */
    public int getNum(){
        return mNum;
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

    /**
     * Sets the NumberVal field.
     *
     * @param pNumberVal
     *  int to use to update the field.
     */
    public void setNumberVal(int pNumberVal){
        this.mNumberVal = pNumberVal;
        setDirty(true);
    }
    /**
     * Retrieves the NumberVal field.
     *
     * @return
     *  int containing the NumberVal field.
     */
    public int getNumberVal(){
        return mNumberVal;
    }

    /**
     * Sets the StringVal field.
     *
     * @param pStringVal
     *  String to use to update the field.
     */
    public void setStringVal(String pStringVal){
        this.mStringVal = pStringVal;
        setDirty(true);
    }
    /**
     * Retrieves the StringVal field.
     *
     * @return
     *  String containing the StringVal field.
     */
    public String getStringVal(){
        return mStringVal;
    }

    /**
     * Sets the DateVal field.
     *
     * @param pDateVal
     *  Date to use to update the field.
     */
    public void setDateVal(Date pDateVal){
        this.mDateVal = pDateVal;
        setDirty(true);
    }
    /**
     * Retrieves the DateVal field.
     *
     * @return
     *  Date containing the DateVal field.
     */
    public Date getDateVal(){
        return mDateVal;
    }

    /**
     * Sets the VarClass field.
     *
     * @param pVarClass
     *  String to use to update the field.
     */
    public void setVarClass(String pVarClass){
        this.mVarClass = pVarClass;
        setDirty(true);
    }
    /**
     * Retrieves the VarClass field.
     *
     * @return
     *  String containing the VarClass field.
     */
    public String getVarClass(){
        return mVarClass;
    }

    /**
     * Sets the BinaryDataServer field.
     *
     * @param pBinaryDataServer
     *  String to use to update the field.
     */
    public void setBinaryDataServer(String pBinaryDataServer){
        this.mBinaryDataServer = pBinaryDataServer;
        setDirty(true);
    }
    /**
     * Retrieves the BinaryDataServer field.
     *
     * @return
     *  String containing the BinaryDataServer field.
     */
    public String getBinaryDataServer(){
        return mBinaryDataServer;
    }

    /**
     * Sets the BlobValueSystemRef field.
     *
     * @param pBlobValueSystemRef
     *  String to use to update the field.
     */
    public void setBlobValueSystemRef(String pBlobValueSystemRef){
        this.mBlobValueSystemRef = pBlobValueSystemRef;
        setDirty(true);
    }
    /**
     * Retrieves the BlobValueSystemRef field.
     *
     * @return
     *  String containing the BlobValueSystemRef field.
     */
    public String getBlobValueSystemRef(){
        return mBlobValueSystemRef;
    }

    /**
     * Sets the StorageTypeCd field.
     *
     * @param pStorageTypeCd
     *  String to use to update the field.
     */
    public void setStorageTypeCd(String pStorageTypeCd){
        this.mStorageTypeCd = pStorageTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the StorageTypeCd field.
     *
     * @return
     *  String containing the StorageTypeCd field.
     */
    public String getStorageTypeCd(){
        return mStorageTypeCd;
    }


}
