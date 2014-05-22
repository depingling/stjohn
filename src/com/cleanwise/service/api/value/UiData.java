
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        UiData
 * Description:  This is a ValueObject class wrapping the database table CLW_UI.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.UiDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>UiData</code> is a ValueObject class wrapping of the database table CLW_UI.
 */
public class UiData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 1L;
    private int mUiId;// SQL type:NUMBER, not null
    private String mShortDesc;// SQL type:VARCHAR2
    private String mStatusCd;// SQL type:VARCHAR2, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null

    /**
     * Constructor.
     */
    public UiData ()
    {
        mShortDesc = "";
        mStatusCd = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public UiData(int parm1, String parm2, String parm3, String parm4, Date parm5, String parm6, Date parm7)
    {
        mUiId = parm1;
        mShortDesc = parm2;
        mStatusCd = parm3;
        mAddBy = parm4;
        mAddDate = parm5;
        mModBy = parm6;
        mModDate = parm7;
        
    }

    /**
     * Creates a new UiData
     *
     * @return
     *  Newly initialized UiData object.
     */
    public static UiData createValue ()
    {
        UiData valueData = new UiData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this UiData object
     */
    public String toString()
    {
        return "[" + "UiId=" + mUiId + ", ShortDesc=" + mShortDesc + ", StatusCd=" + mStatusCd + ", AddBy=" + mAddBy + ", AddDate=" + mAddDate + ", ModBy=" + mModBy + ", ModDate=" + mModDate + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("Ui");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mUiId));

        node =  doc.createElement("ShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mShortDesc)));
        root.appendChild(node);

        node =  doc.createElement("StatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mStatusCd)));
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
    * creates a clone of this object, the UiId field is not cloned.
    *
    * @return UiData object
    */
    public Object clone(){
        UiData myClone = new UiData();
        
        myClone.mShortDesc = mShortDesc;
        
        myClone.mStatusCd = mStatusCd;
        
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

        if (UiDataAccess.UI_ID.equals(pFieldName)) {
            return getUiId();
        } else if (UiDataAccess.SHORT_DESC.equals(pFieldName)) {
            return getShortDesc();
        } else if (UiDataAccess.STATUS_CD.equals(pFieldName)) {
            return getStatusCd();
        } else if (UiDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (UiDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (UiDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (UiDataAccess.MOD_DATE.equals(pFieldName)) {
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
        return UiDataAccess.CLW_UI;
    }

    
    /**
     * Sets the UiId field. This field is required to be set in the database.
     *
     * @param pUiId
     *  int to use to update the field.
     */
    public void setUiId(int pUiId){
        this.mUiId = pUiId;
        setDirty(true);
    }
    /**
     * Retrieves the UiId field.
     *
     * @return
     *  int containing the UiId field.
     */
    public int getUiId(){
        return mUiId;
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


}
