
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        StoreProfileData
 * Description:  This is a ValueObject class wrapping the database table CLW_STORE_PROFILE.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.StoreProfileDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>StoreProfileData</code> is a ValueObject class wrapping of the database table CLW_STORE_PROFILE.
 */
public class StoreProfileData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 1L;
    private int mStoreProfileId;// SQL type:NUMBER, not null
    private int mStoreId;// SQL type:NUMBER, not null
    private String mShortDesc;// SQL type:VARCHAR2
    private String mDisplay;// SQL type:VARCHAR2
    private String mEdit;// SQL type:VARCHAR2
    private String mOptionTypeCd;// SQL type:VARCHAR2, not null
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public StoreProfileData ()
    {
        mShortDesc = "";
        mDisplay = "";
        mEdit = "";
        mOptionTypeCd = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public StoreProfileData(int parm1, int parm2, String parm3, String parm4, String parm5, String parm6, Date parm7, String parm8, Date parm9, String parm10)
    {
        mStoreProfileId = parm1;
        mStoreId = parm2;
        mShortDesc = parm3;
        mDisplay = parm4;
        mEdit = parm5;
        mOptionTypeCd = parm6;
        mAddDate = parm7;
        mAddBy = parm8;
        mModDate = parm9;
        mModBy = parm10;
        
    }

    /**
     * Creates a new StoreProfileData
     *
     * @return
     *  Newly initialized StoreProfileData object.
     */
    public static StoreProfileData createValue ()
    {
        StoreProfileData valueData = new StoreProfileData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this StoreProfileData object
     */
    public String toString()
    {
        return "[" + "StoreProfileId=" + mStoreProfileId + ", StoreId=" + mStoreId + ", ShortDesc=" + mShortDesc + ", Display=" + mDisplay + ", Edit=" + mEdit + ", OptionTypeCd=" + mOptionTypeCd + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("StoreProfile");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mStoreProfileId));

        node =  doc.createElement("StoreId");
        node.appendChild(doc.createTextNode(String.valueOf(mStoreId)));
        root.appendChild(node);

        node =  doc.createElement("ShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mShortDesc)));
        root.appendChild(node);

        node =  doc.createElement("Display");
        node.appendChild(doc.createTextNode(String.valueOf(mDisplay)));
        root.appendChild(node);

        node =  doc.createElement("Edit");
        node.appendChild(doc.createTextNode(String.valueOf(mEdit)));
        root.appendChild(node);

        node =  doc.createElement("OptionTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mOptionTypeCd)));
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
    * creates a clone of this object, the StoreProfileId field is not cloned.
    *
    * @return StoreProfileData object
    */
    public Object clone(){
        StoreProfileData myClone = new StoreProfileData();
        
        myClone.mStoreId = mStoreId;
        
        myClone.mShortDesc = mShortDesc;
        
        myClone.mDisplay = mDisplay;
        
        myClone.mEdit = mEdit;
        
        myClone.mOptionTypeCd = mOptionTypeCd;
        
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

        if (StoreProfileDataAccess.STORE_PROFILE_ID.equals(pFieldName)) {
            return getStoreProfileId();
        } else if (StoreProfileDataAccess.STORE_ID.equals(pFieldName)) {
            return getStoreId();
        } else if (StoreProfileDataAccess.SHORT_DESC.equals(pFieldName)) {
            return getShortDesc();
        } else if (StoreProfileDataAccess.DISPLAY.equals(pFieldName)) {
            return getDisplay();
        } else if (StoreProfileDataAccess.EDIT.equals(pFieldName)) {
            return getEdit();
        } else if (StoreProfileDataAccess.OPTION_TYPE_CD.equals(pFieldName)) {
            return getOptionTypeCd();
        } else if (StoreProfileDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (StoreProfileDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (StoreProfileDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (StoreProfileDataAccess.MOD_BY.equals(pFieldName)) {
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
        return StoreProfileDataAccess.CLW_STORE_PROFILE;
    }

    
    /**
     * Sets the StoreProfileId field. This field is required to be set in the database.
     *
     * @param pStoreProfileId
     *  int to use to update the field.
     */
    public void setStoreProfileId(int pStoreProfileId){
        this.mStoreProfileId = pStoreProfileId;
        setDirty(true);
    }
    /**
     * Retrieves the StoreProfileId field.
     *
     * @return
     *  int containing the StoreProfileId field.
     */
    public int getStoreProfileId(){
        return mStoreProfileId;
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
     * Sets the Display field.
     *
     * @param pDisplay
     *  String to use to update the field.
     */
    public void setDisplay(String pDisplay){
        this.mDisplay = pDisplay;
        setDirty(true);
    }
    /**
     * Retrieves the Display field.
     *
     * @return
     *  String containing the Display field.
     */
    public String getDisplay(){
        return mDisplay;
    }

    /**
     * Sets the Edit field.
     *
     * @param pEdit
     *  String to use to update the field.
     */
    public void setEdit(String pEdit){
        this.mEdit = pEdit;
        setDirty(true);
    }
    /**
     * Retrieves the Edit field.
     *
     * @return
     *  String containing the Edit field.
     */
    public String getEdit(){
        return mEdit;
    }

    /**
     * Sets the OptionTypeCd field. This field is required to be set in the database.
     *
     * @param pOptionTypeCd
     *  String to use to update the field.
     */
    public void setOptionTypeCd(String pOptionTypeCd){
        this.mOptionTypeCd = pOptionTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the OptionTypeCd field.
     *
     * @return
     *  String containing the OptionTypeCd field.
     */
    public String getOptionTypeCd(){
        return mOptionTypeCd;
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
