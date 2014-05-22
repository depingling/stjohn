
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        HistoryObjectData
 * Description:  This is a ValueObject class wrapping the database table CLW_HISTORY_OBJECT.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.HistoryObjectDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>HistoryObjectData</code> is a ValueObject class wrapping of the database table CLW_HISTORY_OBJECT.
 */
public class HistoryObjectData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 1L;
    private int mHistoryObjectId;// SQL type:NUMBER, not null
    private int mHistoryId;// SQL type:NUMBER, not null
    private int mObjectId;// SQL type:NUMBER, not null
    private String mObjectTypeCd;// SQL type:VARCHAR2, not null
    private String mAddBy;// SQL type:VARCHAR2, not null
    private Date mAddDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE

    /**
     * Constructor.
     */
    public HistoryObjectData ()
    {
        mObjectTypeCd = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public HistoryObjectData(int parm1, int parm2, int parm3, String parm4, String parm5, Date parm6, String parm7, Date parm8)
    {
        mHistoryObjectId = parm1;
        mHistoryId = parm2;
        mObjectId = parm3;
        mObjectTypeCd = parm4;
        mAddBy = parm5;
        mAddDate = parm6;
        mModBy = parm7;
        mModDate = parm8;
        
    }

    /**
     * Creates a new HistoryObjectData
     *
     * @return
     *  Newly initialized HistoryObjectData object.
     */
    public static HistoryObjectData createValue ()
    {
        HistoryObjectData valueData = new HistoryObjectData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this HistoryObjectData object
     */
    public String toString()
    {
        return "[" + "HistoryObjectId=" + mHistoryObjectId + ", HistoryId=" + mHistoryId + ", ObjectId=" + mObjectId + ", ObjectTypeCd=" + mObjectTypeCd + ", AddBy=" + mAddBy + ", AddDate=" + mAddDate + ", ModBy=" + mModBy + ", ModDate=" + mModDate + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("HistoryObject");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mHistoryObjectId));

        node =  doc.createElement("HistoryId");
        node.appendChild(doc.createTextNode(String.valueOf(mHistoryId)));
        root.appendChild(node);

        node =  doc.createElement("ObjectId");
        node.appendChild(doc.createTextNode(String.valueOf(mObjectId)));
        root.appendChild(node);

        node =  doc.createElement("ObjectTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mObjectTypeCd)));
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
    * creates a clone of this object, the HistoryObjectId field is not cloned.
    *
    * @return HistoryObjectData object
    */
    public Object clone(){
        HistoryObjectData myClone = new HistoryObjectData();
        
        myClone.mHistoryId = mHistoryId;
        
        myClone.mObjectId = mObjectId;
        
        myClone.mObjectTypeCd = mObjectTypeCd;
        
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

        if (HistoryObjectDataAccess.HISTORY_OBJECT_ID.equals(pFieldName)) {
            return getHistoryObjectId();
        } else if (HistoryObjectDataAccess.HISTORY_ID.equals(pFieldName)) {
            return getHistoryId();
        } else if (HistoryObjectDataAccess.OBJECT_ID.equals(pFieldName)) {
            return getObjectId();
        } else if (HistoryObjectDataAccess.OBJECT_TYPE_CD.equals(pFieldName)) {
            return getObjectTypeCd();
        } else if (HistoryObjectDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (HistoryObjectDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (HistoryObjectDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (HistoryObjectDataAccess.MOD_DATE.equals(pFieldName)) {
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
        return HistoryObjectDataAccess.CLW_HISTORY_OBJECT;
    }

    
    /**
     * Sets the HistoryObjectId field. This field is required to be set in the database.
     *
     * @param pHistoryObjectId
     *  int to use to update the field.
     */
    public void setHistoryObjectId(int pHistoryObjectId){
        this.mHistoryObjectId = pHistoryObjectId;
        setDirty(true);
    }
    /**
     * Retrieves the HistoryObjectId field.
     *
     * @return
     *  int containing the HistoryObjectId field.
     */
    public int getHistoryObjectId(){
        return mHistoryObjectId;
    }

    /**
     * Sets the HistoryId field. This field is required to be set in the database.
     *
     * @param pHistoryId
     *  int to use to update the field.
     */
    public void setHistoryId(int pHistoryId){
        this.mHistoryId = pHistoryId;
        setDirty(true);
    }
    /**
     * Retrieves the HistoryId field.
     *
     * @return
     *  int containing the HistoryId field.
     */
    public int getHistoryId(){
        return mHistoryId;
    }

    /**
     * Sets the ObjectId field. This field is required to be set in the database.
     *
     * @param pObjectId
     *  int to use to update the field.
     */
    public void setObjectId(int pObjectId){
        this.mObjectId = pObjectId;
        setDirty(true);
    }
    /**
     * Retrieves the ObjectId field.
     *
     * @return
     *  int containing the ObjectId field.
     */
    public int getObjectId(){
        return mObjectId;
    }

    /**
     * Sets the ObjectTypeCd field. This field is required to be set in the database.
     *
     * @param pObjectTypeCd
     *  String to use to update the field.
     */
    public void setObjectTypeCd(String pObjectTypeCd){
        this.mObjectTypeCd = pObjectTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the ObjectTypeCd field.
     *
     * @return
     *  String containing the ObjectTypeCd field.
     */
    public String getObjectTypeCd(){
        return mObjectTypeCd;
    }

    /**
     * Sets the AddBy field. This field is required to be set in the database.
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


}
