
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        LocaleMapData
 * Description:  This is a ValueObject class wrapping the database table CLW_LOCALE_MAP.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.LocaleMapDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>LocaleMapData</code> is a ValueObject class wrapping of the database table CLW_LOCALE_MAP.
 */
public class LocaleMapData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 881937895176380906L;
    private int mLocaleMapId;// SQL type:NUMBER, not null
    private String mTableName;// SQL type:VARCHAR2, not null
    private String mColumnName;// SQL type:VARCHAR2, not null
    private int mLocaleId;// SQL type:NUMBER
    private String mPath;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public LocaleMapData ()
    {
        mTableName = "";
        mColumnName = "";
        mPath = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public LocaleMapData(int parm1, String parm2, String parm3, int parm4, String parm5, Date parm6, String parm7, Date parm8, String parm9)
    {
        mLocaleMapId = parm1;
        mTableName = parm2;
        mColumnName = parm3;
        mLocaleId = parm4;
        mPath = parm5;
        mAddDate = parm6;
        mAddBy = parm7;
        mModDate = parm8;
        mModBy = parm9;
        
    }

    /**
     * Creates a new LocaleMapData
     *
     * @return
     *  Newly initialized LocaleMapData object.
     */
    public static LocaleMapData createValue ()
    {
        LocaleMapData valueData = new LocaleMapData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this LocaleMapData object
     */
    public String toString()
    {
        return "[" + "LocaleMapId=" + mLocaleMapId + ", TableName=" + mTableName + ", ColumnName=" + mColumnName + ", LocaleId=" + mLocaleId + ", Path=" + mPath + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("LocaleMap");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mLocaleMapId));

        node =  doc.createElement("TableName");
        node.appendChild(doc.createTextNode(String.valueOf(mTableName)));
        root.appendChild(node);

        node =  doc.createElement("ColumnName");
        node.appendChild(doc.createTextNode(String.valueOf(mColumnName)));
        root.appendChild(node);

        node =  doc.createElement("LocaleId");
        node.appendChild(doc.createTextNode(String.valueOf(mLocaleId)));
        root.appendChild(node);

        node =  doc.createElement("Path");
        node.appendChild(doc.createTextNode(String.valueOf(mPath)));
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
    * creates a clone of this object, the LocaleMapId field is not cloned.
    *
    * @return LocaleMapData object
    */
    public Object clone(){
        LocaleMapData myClone = new LocaleMapData();
        
        myClone.mTableName = mTableName;
        
        myClone.mColumnName = mColumnName;
        
        myClone.mLocaleId = mLocaleId;
        
        myClone.mPath = mPath;
        
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

        if (LocaleMapDataAccess.LOCALE_MAP_ID.equals(pFieldName)) {
            return getLocaleMapId();
        } else if (LocaleMapDataAccess.TABLE_NAME.equals(pFieldName)) {
            return getTableName();
        } else if (LocaleMapDataAccess.COLUMN_NAME.equals(pFieldName)) {
            return getColumnName();
        } else if (LocaleMapDataAccess.LOCALE_ID.equals(pFieldName)) {
            return getLocaleId();
        } else if (LocaleMapDataAccess.PATH.equals(pFieldName)) {
            return getPath();
        } else if (LocaleMapDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (LocaleMapDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (LocaleMapDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (LocaleMapDataAccess.MOD_BY.equals(pFieldName)) {
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
        return LocaleMapDataAccess.CLW_LOCALE_MAP;
    }

    
    /**
     * Sets the LocaleMapId field. This field is required to be set in the database.
     *
     * @param pLocaleMapId
     *  int to use to update the field.
     */
    public void setLocaleMapId(int pLocaleMapId){
        this.mLocaleMapId = pLocaleMapId;
        setDirty(true);
    }
    /**
     * Retrieves the LocaleMapId field.
     *
     * @return
     *  int containing the LocaleMapId field.
     */
    public int getLocaleMapId(){
        return mLocaleMapId;
    }

    /**
     * Sets the TableName field. This field is required to be set in the database.
     *
     * @param pTableName
     *  String to use to update the field.
     */
    public void setTableName(String pTableName){
        this.mTableName = pTableName;
        setDirty(true);
    }
    /**
     * Retrieves the TableName field.
     *
     * @return
     *  String containing the TableName field.
     */
    public String getTableName(){
        return mTableName;
    }

    /**
     * Sets the ColumnName field. This field is required to be set in the database.
     *
     * @param pColumnName
     *  String to use to update the field.
     */
    public void setColumnName(String pColumnName){
        this.mColumnName = pColumnName;
        setDirty(true);
    }
    /**
     * Retrieves the ColumnName field.
     *
     * @return
     *  String containing the ColumnName field.
     */
    public String getColumnName(){
        return mColumnName;
    }

    /**
     * Sets the LocaleId field.
     *
     * @param pLocaleId
     *  int to use to update the field.
     */
    public void setLocaleId(int pLocaleId){
        this.mLocaleId = pLocaleId;
        setDirty(true);
    }
    /**
     * Retrieves the LocaleId field.
     *
     * @return
     *  int containing the LocaleId field.
     */
    public int getLocaleId(){
        return mLocaleId;
    }

    /**
     * Sets the Path field.
     *
     * @param pPath
     *  String to use to update the field.
     */
    public void setPath(String pPath){
        this.mPath = pPath;
        setDirty(true);
    }
    /**
     * Retrieves the Path field.
     *
     * @return
     *  String containing the Path field.
     */
    public String getPath(){
        return mPath;
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
