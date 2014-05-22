
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        UploadValueData
 * Description:  This is a ValueObject class wrapping the database table CLW_UPLOAD_VALUE.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.UploadValueDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>UploadValueData</code> is a ValueObject class wrapping of the database table CLW_UPLOAD_VALUE.
 */
public class UploadValueData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -4373447928816458832L;
    private int mUploadId;// SQL type:NUMBER, not null
    private int mUploadValueId;// SQL type:NUMBER, not null
    private int mColumnNum;// SQL type:NUMBER, not null
    private int mColumnNumOrig;// SQL type:NUMBER, not null
    private int mRowNum;// SQL type:NUMBER, not null
    private int mRowNumOrig;// SQL type:NUMBER, not null
    private String mUploadValue;// SQL type:VARCHAR2
    private String mUploadValueOrig;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public UploadValueData ()
    {
        mUploadValue = "";
        mUploadValueOrig = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public UploadValueData(int parm1, int parm2, int parm3, int parm4, int parm5, int parm6, String parm7, String parm8, Date parm9, String parm10, Date parm11, String parm12)
    {
        mUploadId = parm1;
        mUploadValueId = parm2;
        mColumnNum = parm3;
        mColumnNumOrig = parm4;
        mRowNum = parm5;
        mRowNumOrig = parm6;
        mUploadValue = parm7;
        mUploadValueOrig = parm8;
        mAddDate = parm9;
        mAddBy = parm10;
        mModDate = parm11;
        mModBy = parm12;
        
    }

    /**
     * Creates a new UploadValueData
     *
     * @return
     *  Newly initialized UploadValueData object.
     */
    public static UploadValueData createValue ()
    {
        UploadValueData valueData = new UploadValueData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this UploadValueData object
     */
    public String toString()
    {
        return "[" + "UploadId=" + mUploadId + ", UploadValueId=" + mUploadValueId + ", ColumnNum=" + mColumnNum + ", ColumnNumOrig=" + mColumnNumOrig + ", RowNum=" + mRowNum + ", RowNumOrig=" + mRowNumOrig + ", UploadValue=" + mUploadValue + ", UploadValueOrig=" + mUploadValueOrig + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("UploadValue");
        
        Element node;

        node =  doc.createElement("UploadId");
        node.appendChild(doc.createTextNode(String.valueOf(mUploadId)));
        root.appendChild(node);

        root.setAttribute("Id", String.valueOf(mUploadValueId));

        node =  doc.createElement("ColumnNum");
        node.appendChild(doc.createTextNode(String.valueOf(mColumnNum)));
        root.appendChild(node);

        node =  doc.createElement("ColumnNumOrig");
        node.appendChild(doc.createTextNode(String.valueOf(mColumnNumOrig)));
        root.appendChild(node);

        node =  doc.createElement("RowNum");
        node.appendChild(doc.createTextNode(String.valueOf(mRowNum)));
        root.appendChild(node);

        node =  doc.createElement("RowNumOrig");
        node.appendChild(doc.createTextNode(String.valueOf(mRowNumOrig)));
        root.appendChild(node);

        node =  doc.createElement("UploadValue");
        node.appendChild(doc.createTextNode(String.valueOf(mUploadValue)));
        root.appendChild(node);

        node =  doc.createElement("UploadValueOrig");
        node.appendChild(doc.createTextNode(String.valueOf(mUploadValueOrig)));
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
    * creates a clone of this object, the UploadValueId field is not cloned.
    *
    * @return UploadValueData object
    */
    public Object clone(){
        UploadValueData myClone = new UploadValueData();
        
        myClone.mUploadId = mUploadId;
        
        myClone.mColumnNum = mColumnNum;
        
        myClone.mColumnNumOrig = mColumnNumOrig;
        
        myClone.mRowNum = mRowNum;
        
        myClone.mRowNumOrig = mRowNumOrig;
        
        myClone.mUploadValue = mUploadValue;
        
        myClone.mUploadValueOrig = mUploadValueOrig;
        
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

        if (UploadValueDataAccess.UPLOAD_ID.equals(pFieldName)) {
            return getUploadId();
        } else if (UploadValueDataAccess.UPLOAD_VALUE_ID.equals(pFieldName)) {
            return getUploadValueId();
        } else if (UploadValueDataAccess.COLUMN_NUM.equals(pFieldName)) {
            return getColumnNum();
        } else if (UploadValueDataAccess.COLUMN_NUM_ORIG.equals(pFieldName)) {
            return getColumnNumOrig();
        } else if (UploadValueDataAccess.ROW_NUM.equals(pFieldName)) {
            return getRowNum();
        } else if (UploadValueDataAccess.ROW_NUM_ORIG.equals(pFieldName)) {
            return getRowNumOrig();
        } else if (UploadValueDataAccess.UPLOAD_VALUE.equals(pFieldName)) {
            return getUploadValue();
        } else if (UploadValueDataAccess.UPLOAD_VALUE_ORIG.equals(pFieldName)) {
            return getUploadValueOrig();
        } else if (UploadValueDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (UploadValueDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (UploadValueDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (UploadValueDataAccess.MOD_BY.equals(pFieldName)) {
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
        return UploadValueDataAccess.CLW_UPLOAD_VALUE;
    }

    
    /**
     * Sets the UploadId field. This field is required to be set in the database.
     *
     * @param pUploadId
     *  int to use to update the field.
     */
    public void setUploadId(int pUploadId){
        this.mUploadId = pUploadId;
        setDirty(true);
    }
    /**
     * Retrieves the UploadId field.
     *
     * @return
     *  int containing the UploadId field.
     */
    public int getUploadId(){
        return mUploadId;
    }

    /**
     * Sets the UploadValueId field. This field is required to be set in the database.
     *
     * @param pUploadValueId
     *  int to use to update the field.
     */
    public void setUploadValueId(int pUploadValueId){
        this.mUploadValueId = pUploadValueId;
        setDirty(true);
    }
    /**
     * Retrieves the UploadValueId field.
     *
     * @return
     *  int containing the UploadValueId field.
     */
    public int getUploadValueId(){
        return mUploadValueId;
    }

    /**
     * Sets the ColumnNum field. This field is required to be set in the database.
     *
     * @param pColumnNum
     *  int to use to update the field.
     */
    public void setColumnNum(int pColumnNum){
        this.mColumnNum = pColumnNum;
        setDirty(true);
    }
    /**
     * Retrieves the ColumnNum field.
     *
     * @return
     *  int containing the ColumnNum field.
     */
    public int getColumnNum(){
        return mColumnNum;
    }

    /**
     * Sets the ColumnNumOrig field. This field is required to be set in the database.
     *
     * @param pColumnNumOrig
     *  int to use to update the field.
     */
    public void setColumnNumOrig(int pColumnNumOrig){
        this.mColumnNumOrig = pColumnNumOrig;
        setDirty(true);
    }
    /**
     * Retrieves the ColumnNumOrig field.
     *
     * @return
     *  int containing the ColumnNumOrig field.
     */
    public int getColumnNumOrig(){
        return mColumnNumOrig;
    }

    /**
     * Sets the RowNum field. This field is required to be set in the database.
     *
     * @param pRowNum
     *  int to use to update the field.
     */
    public void setRowNum(int pRowNum){
        this.mRowNum = pRowNum;
        setDirty(true);
    }
    /**
     * Retrieves the RowNum field.
     *
     * @return
     *  int containing the RowNum field.
     */
    public int getRowNum(){
        return mRowNum;
    }

    /**
     * Sets the RowNumOrig field. This field is required to be set in the database.
     *
     * @param pRowNumOrig
     *  int to use to update the field.
     */
    public void setRowNumOrig(int pRowNumOrig){
        this.mRowNumOrig = pRowNumOrig;
        setDirty(true);
    }
    /**
     * Retrieves the RowNumOrig field.
     *
     * @return
     *  int containing the RowNumOrig field.
     */
    public int getRowNumOrig(){
        return mRowNumOrig;
    }

    /**
     * Sets the UploadValue field.
     *
     * @param pUploadValue
     *  String to use to update the field.
     */
    public void setUploadValue(String pUploadValue){
        this.mUploadValue = pUploadValue;
        setDirty(true);
    }
    /**
     * Retrieves the UploadValue field.
     *
     * @return
     *  String containing the UploadValue field.
     */
    public String getUploadValue(){
        return mUploadValue;
    }

    /**
     * Sets the UploadValueOrig field.
     *
     * @param pUploadValueOrig
     *  String to use to update the field.
     */
    public void setUploadValueOrig(String pUploadValueOrig){
        this.mUploadValueOrig = pUploadValueOrig;
        setDirty(true);
    }
    /**
     * Retrieves the UploadValueOrig field.
     *
     * @return
     *  String containing the UploadValueOrig field.
     */
    public String getUploadValueOrig(){
        return mUploadValueOrig;
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
