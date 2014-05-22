
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        UploadData
 * Description:  This is a ValueObject class wrapping the database table CLW_UPLOAD.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.UploadDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>UploadData</code> is a ValueObject class wrapping of the database table CLW_UPLOAD.
 */
public class UploadData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -1322401518780577363L;
    private int mUploadId;// SQL type:NUMBER, not null
    private int mStoreId;// SQL type:NUMBER, not null
    private String mFileName;// SQL type:VARCHAR2, not null
    private String mFileType;// SQL type:VARCHAR2
    private String mUploadStatusCd;// SQL type:VARCHAR2, not null
    private int mCoulumnQty;// SQL type:NUMBER, not null
    private int mRowQty;// SQL type:NUMBER, not null
    private String mNote;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public UploadData ()
    {
        mFileName = "";
        mFileType = "";
        mUploadStatusCd = "";
        mNote = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public UploadData(int parm1, int parm2, String parm3, String parm4, String parm5, int parm6, int parm7, String parm8, Date parm9, String parm10, Date parm11, String parm12)
    {
        mUploadId = parm1;
        mStoreId = parm2;
        mFileName = parm3;
        mFileType = parm4;
        mUploadStatusCd = parm5;
        mCoulumnQty = parm6;
        mRowQty = parm7;
        mNote = parm8;
        mAddDate = parm9;
        mAddBy = parm10;
        mModDate = parm11;
        mModBy = parm12;
        
    }

    /**
     * Creates a new UploadData
     *
     * @return
     *  Newly initialized UploadData object.
     */
    public static UploadData createValue ()
    {
        UploadData valueData = new UploadData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this UploadData object
     */
    public String toString()
    {
        return "[" + "UploadId=" + mUploadId + ", StoreId=" + mStoreId + ", FileName=" + mFileName + ", FileType=" + mFileType + ", UploadStatusCd=" + mUploadStatusCd + ", CoulumnQty=" + mCoulumnQty + ", RowQty=" + mRowQty + ", Note=" + mNote + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("Upload");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mUploadId));

        node =  doc.createElement("StoreId");
        node.appendChild(doc.createTextNode(String.valueOf(mStoreId)));
        root.appendChild(node);

        node =  doc.createElement("FileName");
        node.appendChild(doc.createTextNode(String.valueOf(mFileName)));
        root.appendChild(node);

        node =  doc.createElement("FileType");
        node.appendChild(doc.createTextNode(String.valueOf(mFileType)));
        root.appendChild(node);

        node =  doc.createElement("UploadStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mUploadStatusCd)));
        root.appendChild(node);

        node =  doc.createElement("CoulumnQty");
        node.appendChild(doc.createTextNode(String.valueOf(mCoulumnQty)));
        root.appendChild(node);

        node =  doc.createElement("RowQty");
        node.appendChild(doc.createTextNode(String.valueOf(mRowQty)));
        root.appendChild(node);

        node =  doc.createElement("Note");
        node.appendChild(doc.createTextNode(String.valueOf(mNote)));
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
    * creates a clone of this object, the UploadId field is not cloned.
    *
    * @return UploadData object
    */
    public Object clone(){
        UploadData myClone = new UploadData();
        
        myClone.mStoreId = mStoreId;
        
        myClone.mFileName = mFileName;
        
        myClone.mFileType = mFileType;
        
        myClone.mUploadStatusCd = mUploadStatusCd;
        
        myClone.mCoulumnQty = mCoulumnQty;
        
        myClone.mRowQty = mRowQty;
        
        myClone.mNote = mNote;
        
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

        if (UploadDataAccess.UPLOAD_ID.equals(pFieldName)) {
            return getUploadId();
        } else if (UploadDataAccess.STORE_ID.equals(pFieldName)) {
            return getStoreId();
        } else if (UploadDataAccess.FILE_NAME.equals(pFieldName)) {
            return getFileName();
        } else if (UploadDataAccess.FILE_TYPE.equals(pFieldName)) {
            return getFileType();
        } else if (UploadDataAccess.UPLOAD_STATUS_CD.equals(pFieldName)) {
            return getUploadStatusCd();
        } else if (UploadDataAccess.COULUMN_QTY.equals(pFieldName)) {
            return getCoulumnQty();
        } else if (UploadDataAccess.ROW_QTY.equals(pFieldName)) {
            return getRowQty();
        } else if (UploadDataAccess.NOTE.equals(pFieldName)) {
            return getNote();
        } else if (UploadDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (UploadDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (UploadDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (UploadDataAccess.MOD_BY.equals(pFieldName)) {
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
        return UploadDataAccess.CLW_UPLOAD;
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
     * Sets the StoreId field. This field is required to be set in the database.
     *
     * @param pStoreId
     *  int to use to update the field.
     */
    public void setStoreId(int pStoreId){
        this.mStoreId = pStoreId;
        setDirty(true);
    }
    /**
     * Retrieves the StoreId field.
     *
     * @return
     *  int containing the StoreId field.
     */
    public int getStoreId(){
        return mStoreId;
    }

    /**
     * Sets the FileName field. This field is required to be set in the database.
     *
     * @param pFileName
     *  String to use to update the field.
     */
    public void setFileName(String pFileName){
        this.mFileName = pFileName;
        setDirty(true);
    }
    /**
     * Retrieves the FileName field.
     *
     * @return
     *  String containing the FileName field.
     */
    public String getFileName(){
        return mFileName;
    }

    /**
     * Sets the FileType field.
     *
     * @param pFileType
     *  String to use to update the field.
     */
    public void setFileType(String pFileType){
        this.mFileType = pFileType;
        setDirty(true);
    }
    /**
     * Retrieves the FileType field.
     *
     * @return
     *  String containing the FileType field.
     */
    public String getFileType(){
        return mFileType;
    }

    /**
     * Sets the UploadStatusCd field. This field is required to be set in the database.
     *
     * @param pUploadStatusCd
     *  String to use to update the field.
     */
    public void setUploadStatusCd(String pUploadStatusCd){
        this.mUploadStatusCd = pUploadStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the UploadStatusCd field.
     *
     * @return
     *  String containing the UploadStatusCd field.
     */
    public String getUploadStatusCd(){
        return mUploadStatusCd;
    }

    /**
     * Sets the CoulumnQty field. This field is required to be set in the database.
     *
     * @param pCoulumnQty
     *  int to use to update the field.
     */
    public void setCoulumnQty(int pCoulumnQty){
        this.mCoulumnQty = pCoulumnQty;
        setDirty(true);
    }
    /**
     * Retrieves the CoulumnQty field.
     *
     * @return
     *  int containing the CoulumnQty field.
     */
    public int getCoulumnQty(){
        return mCoulumnQty;
    }

    /**
     * Sets the RowQty field. This field is required to be set in the database.
     *
     * @param pRowQty
     *  int to use to update the field.
     */
    public void setRowQty(int pRowQty){
        this.mRowQty = pRowQty;
        setDirty(true);
    }
    /**
     * Retrieves the RowQty field.
     *
     * @return
     *  int containing the RowQty field.
     */
    public int getRowQty(){
        return mRowQty;
    }

    /**
     * Sets the Note field.
     *
     * @param pNote
     *  String to use to update the field.
     */
    public void setNote(String pNote){
        this.mNote = pNote;
        setDirty(true);
    }
    /**
     * Retrieves the Note field.
     *
     * @return
     *  String containing the Note field.
     */
    public String getNote(){
        return mNote;
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
