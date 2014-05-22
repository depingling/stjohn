
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        WarrantyNoteData
 * Description:  This is a ValueObject class wrapping the database table CLW_WARRANTY_NOTE.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.WarrantyNoteDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>WarrantyNoteData</code> is a ValueObject class wrapping of the database table CLW_WARRANTY_NOTE.
 */
public class WarrantyNoteData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 1818393106011342660L;
    private int mWarrantyNoteId;// SQL type:NUMBER, not null
    private int mWarrantyId;// SQL type:NUMBER
    private int mAssetWarrantyId;// SQL type:NUMBER
    private String mShortDesc;// SQL type:VARCHAR2, not null
    private String mNote;// SQL type:VARCHAR2, not null
    private String mTypeCd;// SQL type:VARCHAR2, not null
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public WarrantyNoteData ()
    {
        mShortDesc = "";
        mNote = "";
        mTypeCd = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public WarrantyNoteData(int parm1, int parm2, int parm3, String parm4, String parm5, String parm6, Date parm7, String parm8, Date parm9, String parm10)
    {
        mWarrantyNoteId = parm1;
        mWarrantyId = parm2;
        mAssetWarrantyId = parm3;
        mShortDesc = parm4;
        mNote = parm5;
        mTypeCd = parm6;
        mAddDate = parm7;
        mAddBy = parm8;
        mModDate = parm9;
        mModBy = parm10;
        
    }

    /**
     * Creates a new WarrantyNoteData
     *
     * @return
     *  Newly initialized WarrantyNoteData object.
     */
    public static WarrantyNoteData createValue ()
    {
        WarrantyNoteData valueData = new WarrantyNoteData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this WarrantyNoteData object
     */
    public String toString()
    {
        return "[" + "WarrantyNoteId=" + mWarrantyNoteId + ", WarrantyId=" + mWarrantyId + ", AssetWarrantyId=" + mAssetWarrantyId + ", ShortDesc=" + mShortDesc + ", Note=" + mNote + ", TypeCd=" + mTypeCd + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("WarrantyNote");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mWarrantyNoteId));

        node =  doc.createElement("WarrantyId");
        node.appendChild(doc.createTextNode(String.valueOf(mWarrantyId)));
        root.appendChild(node);

        node =  doc.createElement("AssetWarrantyId");
        node.appendChild(doc.createTextNode(String.valueOf(mAssetWarrantyId)));
        root.appendChild(node);

        node =  doc.createElement("ShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mShortDesc)));
        root.appendChild(node);

        node =  doc.createElement("Note");
        node.appendChild(doc.createTextNode(String.valueOf(mNote)));
        root.appendChild(node);

        node =  doc.createElement("TypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mTypeCd)));
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
    * creates a clone of this object, the WarrantyNoteId field is not cloned.
    *
    * @return WarrantyNoteData object
    */
    public Object clone(){
        WarrantyNoteData myClone = new WarrantyNoteData();
        
        myClone.mWarrantyId = mWarrantyId;
        
        myClone.mAssetWarrantyId = mAssetWarrantyId;
        
        myClone.mShortDesc = mShortDesc;
        
        myClone.mNote = mNote;
        
        myClone.mTypeCd = mTypeCd;
        
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

        if (WarrantyNoteDataAccess.WARRANTY_NOTE_ID.equals(pFieldName)) {
            return getWarrantyNoteId();
        } else if (WarrantyNoteDataAccess.WARRANTY_ID.equals(pFieldName)) {
            return getWarrantyId();
        } else if (WarrantyNoteDataAccess.ASSET_WARRANTY_ID.equals(pFieldName)) {
            return getAssetWarrantyId();
        } else if (WarrantyNoteDataAccess.SHORT_DESC.equals(pFieldName)) {
            return getShortDesc();
        } else if (WarrantyNoteDataAccess.NOTE.equals(pFieldName)) {
            return getNote();
        } else if (WarrantyNoteDataAccess.TYPE_CD.equals(pFieldName)) {
            return getTypeCd();
        } else if (WarrantyNoteDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (WarrantyNoteDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (WarrantyNoteDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (WarrantyNoteDataAccess.MOD_BY.equals(pFieldName)) {
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
        return WarrantyNoteDataAccess.CLW_WARRANTY_NOTE;
    }

    
    /**
     * Sets the WarrantyNoteId field. This field is required to be set in the database.
     *
     * @param pWarrantyNoteId
     *  int to use to update the field.
     */
    public void setWarrantyNoteId(int pWarrantyNoteId){
        this.mWarrantyNoteId = pWarrantyNoteId;
        setDirty(true);
    }
    /**
     * Retrieves the WarrantyNoteId field.
     *
     * @return
     *  int containing the WarrantyNoteId field.
     */
    public int getWarrantyNoteId(){
        return mWarrantyNoteId;
    }

    /**
     * Sets the WarrantyId field.
     *
     * @param pWarrantyId
     *  int to use to update the field.
     */
    public void setWarrantyId(int pWarrantyId){
        this.mWarrantyId = pWarrantyId;
        setDirty(true);
    }
    /**
     * Retrieves the WarrantyId field.
     *
     * @return
     *  int containing the WarrantyId field.
     */
    public int getWarrantyId(){
        return mWarrantyId;
    }

    /**
     * Sets the AssetWarrantyId field.
     *
     * @param pAssetWarrantyId
     *  int to use to update the field.
     */
    public void setAssetWarrantyId(int pAssetWarrantyId){
        this.mAssetWarrantyId = pAssetWarrantyId;
        setDirty(true);
    }
    /**
     * Retrieves the AssetWarrantyId field.
     *
     * @return
     *  int containing the AssetWarrantyId field.
     */
    public int getAssetWarrantyId(){
        return mAssetWarrantyId;
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
     * Sets the Note field. This field is required to be set in the database.
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
