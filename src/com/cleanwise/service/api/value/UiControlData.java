
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        UiControlData
 * Description:  This is a ValueObject class wrapping the database table CLW_UI_CONTROL.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.UiControlDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>UiControlData</code> is a ValueObject class wrapping of the database table CLW_UI_CONTROL.
 */
public class UiControlData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 1L;
    private int mUiControlId;// SQL type:NUMBER, not null
    private int mUiPageId;// SQL type:NUMBER, not null
    private String mShortDesc;// SQL type:VARCHAR2, not null
    private String mStatusCd;// SQL type:VARCHAR2, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null

    /**
     * Constructor.
     */
    public UiControlData ()
    {
        mShortDesc = "";
        mStatusCd = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public UiControlData(int parm1, int parm2, String parm3, String parm4, String parm5, Date parm6, String parm7, Date parm8)
    {
        mUiControlId = parm1;
        mUiPageId = parm2;
        mShortDesc = parm3;
        mStatusCd = parm4;
        mAddBy = parm5;
        mAddDate = parm6;
        mModBy = parm7;
        mModDate = parm8;
        
    }

    /**
     * Creates a new UiControlData
     *
     * @return
     *  Newly initialized UiControlData object.
     */
    public static UiControlData createValue ()
    {
        UiControlData valueData = new UiControlData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this UiControlData object
     */
    public String toString()
    {
        return "[" + "UiControlId=" + mUiControlId + ", UiPageId=" + mUiPageId + ", ShortDesc=" + mShortDesc + ", StatusCd=" + mStatusCd + ", AddBy=" + mAddBy + ", AddDate=" + mAddDate + ", ModBy=" + mModBy + ", ModDate=" + mModDate + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("UiControl");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mUiControlId));

        node =  doc.createElement("UiPageId");
        node.appendChild(doc.createTextNode(String.valueOf(mUiPageId)));
        root.appendChild(node);

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
    * creates a clone of this object, the UiControlId field is not cloned.
    *
    * @return UiControlData object
    */
    public Object clone(){
        UiControlData myClone = new UiControlData();
        
        myClone.mUiPageId = mUiPageId;
        
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

        if (UiControlDataAccess.UI_CONTROL_ID.equals(pFieldName)) {
            return getUiControlId();
        } else if (UiControlDataAccess.UI_PAGE_ID.equals(pFieldName)) {
            return getUiPageId();
        } else if (UiControlDataAccess.SHORT_DESC.equals(pFieldName)) {
            return getShortDesc();
        } else if (UiControlDataAccess.STATUS_CD.equals(pFieldName)) {
            return getStatusCd();
        } else if (UiControlDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (UiControlDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (UiControlDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (UiControlDataAccess.MOD_DATE.equals(pFieldName)) {
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
        return UiControlDataAccess.CLW_UI_CONTROL;
    }

    
    /**
     * Sets the UiControlId field. This field is required to be set in the database.
     *
     * @param pUiControlId
     *  int to use to update the field.
     */
    public void setUiControlId(int pUiControlId){
        this.mUiControlId = pUiControlId;
        setDirty(true);
    }
    /**
     * Retrieves the UiControlId field.
     *
     * @return
     *  int containing the UiControlId field.
     */
    public int getUiControlId(){
        return mUiControlId;
    }

    /**
     * Sets the UiPageId field. This field is required to be set in the database.
     *
     * @param pUiPageId
     *  int to use to update the field.
     */
    public void setUiPageId(int pUiPageId){
        this.mUiPageId = pUiPageId;
        setDirty(true);
    }
    /**
     * Retrieves the UiPageId field.
     *
     * @return
     *  int containing the UiPageId field.
     */
    public int getUiPageId(){
        return mUiPageId;
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
