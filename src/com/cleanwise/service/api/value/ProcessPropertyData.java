
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ProcessPropertyData
 * Description:  This is a ValueObject class wrapping the database table CLW_PROCESS_PROPERTY.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.ProcessPropertyDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>ProcessPropertyData</code> is a ValueObject class wrapping of the database table CLW_PROCESS_PROPERTY.
 */
public class ProcessPropertyData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 3565847741773670900L;
    private int mProcessPropertyId;// SQL type:NUMBER, not null
    private int mProcessId;// SQL type:NUMBER, not null
    private String mTaskVarName;// SQL type:VARCHAR2
    private String mPropertyTypeCd;// SQL type:VARCHAR2, not null
    private byte[] mVarValue;// SQL type:BLOB
    private String mVarClass;// SQL type:VARCHAR2
    private String mAddBy;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private int mNumberVal;// SQL type:NUMBER
    private String mStringVal;// SQL type:VARCHAR2
    private Date mDateVal;// SQL type:DATE
    private String mBinaryDataServer;// SQL type:VARCHAR2
    private String mBlobValueSystemRef;// SQL type:VARCHAR2
    private String mStorageTypeCd;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public ProcessPropertyData ()
    {
        mTaskVarName = "";
        mPropertyTypeCd = "";
        mVarClass = "";
        mAddBy = "";
        mModBy = "";
        mStringVal = "";
        mBinaryDataServer = "";
        mBlobValueSystemRef = "";
        mStorageTypeCd = "";
    }

    /**
     * Constructor.
     */
    public ProcessPropertyData(int parm1, int parm2, String parm3, String parm4, byte[] parm5, String parm6, String parm7, Date parm8, String parm9, Date parm10, int parm11, String parm12, Date parm13, String parm14, String parm15, String parm16)
    {
        mProcessPropertyId = parm1;
        mProcessId = parm2;
        mTaskVarName = parm3;
        mPropertyTypeCd = parm4;
        mVarValue = parm5;
        mVarClass = parm6;
        mAddBy = parm7;
        mAddDate = parm8;
        mModBy = parm9;
        mModDate = parm10;
        mNumberVal = parm11;
        mStringVal = parm12;
        mDateVal = parm13;
        mBinaryDataServer = parm14;
        mBlobValueSystemRef = parm15;
        mStorageTypeCd = parm16;
        
    }

    /**
     * Creates a new ProcessPropertyData
     *
     * @return
     *  Newly initialized ProcessPropertyData object.
     */
    public static ProcessPropertyData createValue ()
    {
        ProcessPropertyData valueData = new ProcessPropertyData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ProcessPropertyData object
     */
    public String toString()
    {
        return "[" + "ProcessPropertyId=" + mProcessPropertyId + ", ProcessId=" + mProcessId + ", TaskVarName=" + mTaskVarName + ", PropertyTypeCd=" + mPropertyTypeCd + ", VarValue=" + mVarValue + ", VarClass=" + mVarClass + ", AddBy=" + mAddBy + ", AddDate=" + mAddDate + ", ModBy=" + mModBy + ", ModDate=" + mModDate + ", NumberVal=" + mNumberVal + ", StringVal=" + mStringVal + ", DateVal=" + mDateVal + ", BinaryDataServer=" + mBinaryDataServer + ", BlobValueSystemRef=" + mBlobValueSystemRef + ", StorageTypeCd=" + mStorageTypeCd + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("ProcessProperty");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mProcessPropertyId));

        node =  doc.createElement("ProcessId");
        node.appendChild(doc.createTextNode(String.valueOf(mProcessId)));
        root.appendChild(node);

        node =  doc.createElement("TaskVarName");
        node.appendChild(doc.createTextNode(String.valueOf(mTaskVarName)));
        root.appendChild(node);

        node =  doc.createElement("PropertyTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mPropertyTypeCd)));
        root.appendChild(node);

        node =  doc.createElement("VarValue");
        node.appendChild(doc.createTextNode(String.valueOf(mVarValue)));
        root.appendChild(node);

        node =  doc.createElement("VarClass");
        node.appendChild(doc.createTextNode(String.valueOf(mVarClass)));
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
    * creates a clone of this object, the ProcessPropertyId field is not cloned.
    *
    * @return ProcessPropertyData object
    */
    public Object clone(){
        ProcessPropertyData myClone = new ProcessPropertyData();
        
        myClone.mProcessId = mProcessId;
        
        myClone.mTaskVarName = mTaskVarName;
        
        myClone.mPropertyTypeCd = mPropertyTypeCd;
        
        myClone.mVarValue = mVarValue;
        
        myClone.mVarClass = mVarClass;
        
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

        if (ProcessPropertyDataAccess.PROCESS_PROPERTY_ID.equals(pFieldName)) {
            return getProcessPropertyId();
        } else if (ProcessPropertyDataAccess.PROCESS_ID.equals(pFieldName)) {
            return getProcessId();
        } else if (ProcessPropertyDataAccess.TASK_VAR_NAME.equals(pFieldName)) {
            return getTaskVarName();
        } else if (ProcessPropertyDataAccess.PROPERTY_TYPE_CD.equals(pFieldName)) {
            return getPropertyTypeCd();
        } else if (ProcessPropertyDataAccess.VAR_VALUE.equals(pFieldName)) {
            return getVarValue();
        } else if (ProcessPropertyDataAccess.VAR_CLASS.equals(pFieldName)) {
            return getVarClass();
        } else if (ProcessPropertyDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (ProcessPropertyDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (ProcessPropertyDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (ProcessPropertyDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (ProcessPropertyDataAccess.NUMBER_VAL.equals(pFieldName)) {
            return getNumberVal();
        } else if (ProcessPropertyDataAccess.STRING_VAL.equals(pFieldName)) {
            return getStringVal();
        } else if (ProcessPropertyDataAccess.DATE_VAL.equals(pFieldName)) {
            return getDateVal();
        } else if (ProcessPropertyDataAccess.BINARY_DATA_SERVER.equals(pFieldName)) {
            return getBinaryDataServer();
        } else if (ProcessPropertyDataAccess.BLOB_VALUE_SYSTEM_REF.equals(pFieldName)) {
            return getBlobValueSystemRef();
        } else if (ProcessPropertyDataAccess.STORAGE_TYPE_CD.equals(pFieldName)) {
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
        return ProcessPropertyDataAccess.CLW_PROCESS_PROPERTY;
    }

    
    /**
     * Sets the ProcessPropertyId field. This field is required to be set in the database.
     *
     * @param pProcessPropertyId
     *  int to use to update the field.
     */
    public void setProcessPropertyId(int pProcessPropertyId){
        this.mProcessPropertyId = pProcessPropertyId;
        setDirty(true);
    }
    /**
     * Retrieves the ProcessPropertyId field.
     *
     * @return
     *  int containing the ProcessPropertyId field.
     */
    public int getProcessPropertyId(){
        return mProcessPropertyId;
    }

    /**
     * Sets the ProcessId field. This field is required to be set in the database.
     *
     * @param pProcessId
     *  int to use to update the field.
     */
    public void setProcessId(int pProcessId){
        this.mProcessId = pProcessId;
        setDirty(true);
    }
    /**
     * Retrieves the ProcessId field.
     *
     * @return
     *  int containing the ProcessId field.
     */
    public int getProcessId(){
        return mProcessId;
    }

    /**
     * Sets the TaskVarName field.
     *
     * @param pTaskVarName
     *  String to use to update the field.
     */
    public void setTaskVarName(String pTaskVarName){
        this.mTaskVarName = pTaskVarName;
        setDirty(true);
    }
    /**
     * Retrieves the TaskVarName field.
     *
     * @return
     *  String containing the TaskVarName field.
     */
    public String getTaskVarName(){
        return mTaskVarName;
    }

    /**
     * Sets the PropertyTypeCd field. This field is required to be set in the database.
     *
     * @param pPropertyTypeCd
     *  String to use to update the field.
     */
    public void setPropertyTypeCd(String pPropertyTypeCd){
        this.mPropertyTypeCd = pPropertyTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the PropertyTypeCd field.
     *
     * @return
     *  String containing the PropertyTypeCd field.
     */
    public String getPropertyTypeCd(){
        return mPropertyTypeCd;
    }

    /**
     * Sets the VarValue field.
     *
     * @param pVarValue
     *  byte[] to use to update the field.
     */
    public void setVarValue(byte[] pVarValue){
        this.mVarValue = pVarValue;
        setDirty(true);
    }
    /**
     * Retrieves the VarValue field.
     *
     * @return
     *  byte[] containing the VarValue field.
     */
    public byte[] getVarValue(){
        return mVarValue;
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
