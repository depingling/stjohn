
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        GenericReportData
 * Description:  This is a ValueObject class wrapping the database table CLW_GENERIC_REPORT.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.GenericReportDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>GenericReportData</code> is a ValueObject class wrapping of the database table CLW_GENERIC_REPORT.
 */
public class GenericReportData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 8313729562043844208L;
    private int mGenericReportId;// SQL type:NUMBER, not null
    private String mCategory;// SQL type:VARCHAR2
    private String mName;// SQL type:VARCHAR2
    private String mParameterToken;// SQL type:VARCHAR2
    private String mReportSchemaCd;// SQL type:VARCHAR2
    private String mInterfaceTable;// SQL type:VARCHAR2
    private String mSqlText;// SQL type:CLOB
    private String mScriptText;// SQL type:CLOB
    private Date mAddDate;// SQL type:DATE
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE
    private String mModBy;// SQL type:VARCHAR2
    private String mGenericReportType;// SQL type:VARCHAR2
    private String mClassname;// SQL type:VARCHAR2
    private String mSupplementaryControls;// SQL type:VARCHAR2
    private String mRuntimeEnabled;// SQL type:VARCHAR2
    private String mLongDesc;// SQL type:VARCHAR2
    private String mUserTypes;// SQL type:VARCHAR2
    private String mDbName;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public GenericReportData ()
    {
        mCategory = "";
        mName = "";
        mParameterToken = "";
        mReportSchemaCd = "";
        mInterfaceTable = "";
        mSqlText = "";
        mScriptText = "";
        mAddBy = "";
        mModBy = "";
        mGenericReportType = "";
        mClassname = "";
        mSupplementaryControls = "";
        mRuntimeEnabled = "";
        mLongDesc = "";
        mUserTypes = "";
        mDbName = "";
    }

    /**
     * Constructor.
     */
    public GenericReportData(int parm1, String parm2, String parm3, String parm4, String parm5, String parm6, String parm7, String parm8, Date parm9, String parm10, Date parm11, String parm12, String parm13, String parm14, String parm15, String parm16, String parm17, String parm18, String parm19)
    {
        mGenericReportId = parm1;
        mCategory = parm2;
        mName = parm3;
        mParameterToken = parm4;
        mReportSchemaCd = parm5;
        mInterfaceTable = parm6;
        mSqlText = parm7;
        mScriptText = parm8;
        mAddDate = parm9;
        mAddBy = parm10;
        mModDate = parm11;
        mModBy = parm12;
        mGenericReportType = parm13;
        mClassname = parm14;
        mSupplementaryControls = parm15;
        mRuntimeEnabled = parm16;
        mLongDesc = parm17;
        mUserTypes = parm18;
        mDbName = parm19;
        
    }

    /**
     * Creates a new GenericReportData
     *
     * @return
     *  Newly initialized GenericReportData object.
     */
    public static GenericReportData createValue ()
    {
        GenericReportData valueData = new GenericReportData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this GenericReportData object
     */
    public String toString()
    {
        return "[" + "GenericReportId=" + mGenericReportId + ", Category=" + mCategory + ", Name=" + mName + ", ParameterToken=" + mParameterToken + ", ReportSchemaCd=" + mReportSchemaCd + ", InterfaceTable=" + mInterfaceTable + ", SqlText=" + mSqlText + ", ScriptText=" + mScriptText + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", GenericReportType=" + mGenericReportType + ", Classname=" + mClassname + ", SupplementaryControls=" + mSupplementaryControls + ", RuntimeEnabled=" + mRuntimeEnabled + ", LongDesc=" + mLongDesc + ", UserTypes=" + mUserTypes + ", DbName=" + mDbName + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("GenericReport");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mGenericReportId));

        node =  doc.createElement("Category");
        node.appendChild(doc.createTextNode(String.valueOf(mCategory)));
        root.appendChild(node);

        node =  doc.createElement("Name");
        node.appendChild(doc.createTextNode(String.valueOf(mName)));
        root.appendChild(node);

        node =  doc.createElement("ParameterToken");
        node.appendChild(doc.createTextNode(String.valueOf(mParameterToken)));
        root.appendChild(node);

        node =  doc.createElement("ReportSchemaCd");
        node.appendChild(doc.createTextNode(String.valueOf(mReportSchemaCd)));
        root.appendChild(node);

        node =  doc.createElement("InterfaceTable");
        node.appendChild(doc.createTextNode(String.valueOf(mInterfaceTable)));
        root.appendChild(node);

        node =  doc.createElement("SqlText");
        node.appendChild(doc.createTextNode(String.valueOf(mSqlText)));
        root.appendChild(node);

        node =  doc.createElement("ScriptText");
        node.appendChild(doc.createTextNode(String.valueOf(mScriptText)));
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

        node =  doc.createElement("GenericReportType");
        node.appendChild(doc.createTextNode(String.valueOf(mGenericReportType)));
        root.appendChild(node);

        node =  doc.createElement("Classname");
        node.appendChild(doc.createTextNode(String.valueOf(mClassname)));
        root.appendChild(node);

        node =  doc.createElement("SupplementaryControls");
        node.appendChild(doc.createTextNode(String.valueOf(mSupplementaryControls)));
        root.appendChild(node);

        node =  doc.createElement("RuntimeEnabled");
        node.appendChild(doc.createTextNode(String.valueOf(mRuntimeEnabled)));
        root.appendChild(node);

        node =  doc.createElement("LongDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mLongDesc)));
        root.appendChild(node);

        node =  doc.createElement("UserTypes");
        node.appendChild(doc.createTextNode(String.valueOf(mUserTypes)));
        root.appendChild(node);

        node =  doc.createElement("DbName");
        node.appendChild(doc.createTextNode(String.valueOf(mDbName)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the GenericReportId field is not cloned.
    *
    * @return GenericReportData object
    */
    public Object clone(){
        GenericReportData myClone = new GenericReportData();
        
        myClone.mCategory = mCategory;
        
        myClone.mName = mName;
        
        myClone.mParameterToken = mParameterToken;
        
        myClone.mReportSchemaCd = mReportSchemaCd;
        
        myClone.mInterfaceTable = mInterfaceTable;
        
        myClone.mSqlText = mSqlText;
        
        myClone.mScriptText = mScriptText;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        myClone.mGenericReportType = mGenericReportType;
        
        myClone.mClassname = mClassname;
        
        myClone.mSupplementaryControls = mSupplementaryControls;
        
        myClone.mRuntimeEnabled = mRuntimeEnabled;
        
        myClone.mLongDesc = mLongDesc;
        
        myClone.mUserTypes = mUserTypes;
        
        myClone.mDbName = mDbName;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (GenericReportDataAccess.GENERIC_REPORT_ID.equals(pFieldName)) {
            return getGenericReportId();
        } else if (GenericReportDataAccess.CATEGORY.equals(pFieldName)) {
            return getCategory();
        } else if (GenericReportDataAccess.NAME.equals(pFieldName)) {
            return getName();
        } else if (GenericReportDataAccess.PARAMETER_TOKEN.equals(pFieldName)) {
            return getParameterToken();
        } else if (GenericReportDataAccess.REPORT_SCHEMA_CD.equals(pFieldName)) {
            return getReportSchemaCd();
        } else if (GenericReportDataAccess.INTERFACE_TABLE.equals(pFieldName)) {
            return getInterfaceTable();
        } else if (GenericReportDataAccess.SQL_TEXT.equals(pFieldName)) {
            return getSqlText();
        } else if (GenericReportDataAccess.SCRIPT_TEXT.equals(pFieldName)) {
            return getScriptText();
        } else if (GenericReportDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (GenericReportDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (GenericReportDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (GenericReportDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (GenericReportDataAccess.GENERIC_REPORT_TYPE.equals(pFieldName)) {
            return getGenericReportType();
        } else if (GenericReportDataAccess.CLASSNAME.equals(pFieldName)) {
            return getClassname();
        } else if (GenericReportDataAccess.SUPPLEMENTARY_CONTROLS.equals(pFieldName)) {
            return getSupplementaryControls();
        } else if (GenericReportDataAccess.RUNTIME_ENABLED.equals(pFieldName)) {
            return getRuntimeEnabled();
        } else if (GenericReportDataAccess.LONG_DESC.equals(pFieldName)) {
            return getLongDesc();
        } else if (GenericReportDataAccess.USER_TYPES.equals(pFieldName)) {
            return getUserTypes();
        } else if (GenericReportDataAccess.DB_NAME.equals(pFieldName)) {
            return getDbName();
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
        return GenericReportDataAccess.CLW_GENERIC_REPORT;
    }

    
    /**
     * Sets the GenericReportId field. This field is required to be set in the database.
     *
     * @param pGenericReportId
     *  int to use to update the field.
     */
    public void setGenericReportId(int pGenericReportId){
        this.mGenericReportId = pGenericReportId;
        setDirty(true);
    }
    /**
     * Retrieves the GenericReportId field.
     *
     * @return
     *  int containing the GenericReportId field.
     */
    public int getGenericReportId(){
        return mGenericReportId;
    }

    /**
     * Sets the Category field.
     *
     * @param pCategory
     *  String to use to update the field.
     */
    public void setCategory(String pCategory){
        this.mCategory = pCategory;
        setDirty(true);
    }
    /**
     * Retrieves the Category field.
     *
     * @return
     *  String containing the Category field.
     */
    public String getCategory(){
        return mCategory;
    }

    /**
     * Sets the Name field.
     *
     * @param pName
     *  String to use to update the field.
     */
    public void setName(String pName){
        this.mName = pName;
        setDirty(true);
    }
    /**
     * Retrieves the Name field.
     *
     * @return
     *  String containing the Name field.
     */
    public String getName(){
        return mName;
    }

    /**
     * Sets the ParameterToken field.
     *
     * @param pParameterToken
     *  String to use to update the field.
     */
    public void setParameterToken(String pParameterToken){
        this.mParameterToken = pParameterToken;
        setDirty(true);
    }
    /**
     * Retrieves the ParameterToken field.
     *
     * @return
     *  String containing the ParameterToken field.
     */
    public String getParameterToken(){
        return mParameterToken;
    }

    /**
     * Sets the ReportSchemaCd field.
     *
     * @param pReportSchemaCd
     *  String to use to update the field.
     */
    public void setReportSchemaCd(String pReportSchemaCd){
        this.mReportSchemaCd = pReportSchemaCd;
        setDirty(true);
    }
    /**
     * Retrieves the ReportSchemaCd field.
     *
     * @return
     *  String containing the ReportSchemaCd field.
     */
    public String getReportSchemaCd(){
        return mReportSchemaCd;
    }

    /**
     * Sets the InterfaceTable field.
     *
     * @param pInterfaceTable
     *  String to use to update the field.
     */
    public void setInterfaceTable(String pInterfaceTable){
        this.mInterfaceTable = pInterfaceTable;
        setDirty(true);
    }
    /**
     * Retrieves the InterfaceTable field.
     *
     * @return
     *  String containing the InterfaceTable field.
     */
    public String getInterfaceTable(){
        return mInterfaceTable;
    }

    /**
     * Sets the SqlText field.
     *
     * @param pSqlText
     *  String to use to update the field.
     */
    public void setSqlText(String pSqlText){
        this.mSqlText = pSqlText;
        setDirty(true);
    }
    /**
     * Retrieves the SqlText field.
     *
     * @return
     *  String containing the SqlText field.
     */
    public String getSqlText(){
        return mSqlText;
    }

    /**
     * Sets the ScriptText field.
     *
     * @param pScriptText
     *  String to use to update the field.
     */
    public void setScriptText(String pScriptText){
        this.mScriptText = pScriptText;
        setDirty(true);
    }
    /**
     * Retrieves the ScriptText field.
     *
     * @return
     *  String containing the ScriptText field.
     */
    public String getScriptText(){
        return mScriptText;
    }

    /**
     * Sets the AddDate field.
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
     * Sets the ModDate field.
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
     * Sets the GenericReportType field.
     *
     * @param pGenericReportType
     *  String to use to update the field.
     */
    public void setGenericReportType(String pGenericReportType){
        this.mGenericReportType = pGenericReportType;
        setDirty(true);
    }
    /**
     * Retrieves the GenericReportType field.
     *
     * @return
     *  String containing the GenericReportType field.
     */
    public String getGenericReportType(){
        return mGenericReportType;
    }

    /**
     * Sets the Classname field.
     *
     * @param pClassname
     *  String to use to update the field.
     */
    public void setClassname(String pClassname){
        this.mClassname = pClassname;
        setDirty(true);
    }
    /**
     * Retrieves the Classname field.
     *
     * @return
     *  String containing the Classname field.
     */
    public String getClassname(){
        return mClassname;
    }

    /**
     * Sets the SupplementaryControls field.
     *
     * @param pSupplementaryControls
     *  String to use to update the field.
     */
    public void setSupplementaryControls(String pSupplementaryControls){
        this.mSupplementaryControls = pSupplementaryControls;
        setDirty(true);
    }
    /**
     * Retrieves the SupplementaryControls field.
     *
     * @return
     *  String containing the SupplementaryControls field.
     */
    public String getSupplementaryControls(){
        return mSupplementaryControls;
    }

    /**
     * Sets the RuntimeEnabled field.
     *
     * @param pRuntimeEnabled
     *  String to use to update the field.
     */
    public void setRuntimeEnabled(String pRuntimeEnabled){
        this.mRuntimeEnabled = pRuntimeEnabled;
        setDirty(true);
    }
    /**
     * Retrieves the RuntimeEnabled field.
     *
     * @return
     *  String containing the RuntimeEnabled field.
     */
    public String getRuntimeEnabled(){
        return mRuntimeEnabled;
    }

    /**
     * Sets the LongDesc field.
     *
     * @param pLongDesc
     *  String to use to update the field.
     */
    public void setLongDesc(String pLongDesc){
        this.mLongDesc = pLongDesc;
        setDirty(true);
    }
    /**
     * Retrieves the LongDesc field.
     *
     * @return
     *  String containing the LongDesc field.
     */
    public String getLongDesc(){
        return mLongDesc;
    }

    /**
     * Sets the UserTypes field.
     *
     * @param pUserTypes
     *  String to use to update the field.
     */
    public void setUserTypes(String pUserTypes){
        this.mUserTypes = pUserTypes;
        setDirty(true);
    }
    /**
     * Retrieves the UserTypes field.
     *
     * @return
     *  String containing the UserTypes field.
     */
    public String getUserTypes(){
        return mUserTypes;
    }

    /**
     * Sets the DbName field.
     *
     * @param pDbName
     *  String to use to update the field.
     */
    public void setDbName(String pDbName){
        this.mDbName = pDbName;
        setDirty(true);
    }
    /**
     * Retrieves the DbName field.
     *
     * @return
     *  String containing the DbName field.
     */
    public String getDbName(){
        return mDbName;
    }


}
