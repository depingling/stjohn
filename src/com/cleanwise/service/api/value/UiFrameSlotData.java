
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        UiFrameSlotData
 * Description:  This is a ValueObject class wrapping the database table CLW_UI_FRAME_SLOT.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.UiFrameSlotDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>UiFrameSlotData</code> is a ValueObject class wrapping of the database table CLW_UI_FRAME_SLOT.
 */
public class UiFrameSlotData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 1L;
    private int mUiFrameSlotId;// SQL type:NUMBER, not null
    private int mUiFrameId;// SQL type:NUMBER, not null
    private String mSlotTypeCd;// SQL type:VARCHAR2, not null
    private String mValue;// SQL type:VARCHAR2, not null
    private String mUrl;// SQL type:VARCHAR2
    private byte[] mBlob;// SQL type:BLOB
    private String mUrlTargetBlank;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2, not null
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public UiFrameSlotData ()
    {
        mSlotTypeCd = "";
        mValue = "";
        mUrl = "";
        mUrlTargetBlank = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public UiFrameSlotData(int parm1, int parm2, String parm3, String parm4, String parm5, byte[] parm6, String parm7, Date parm8, String parm9, Date parm10, String parm11)
    {
        mUiFrameSlotId = parm1;
        mUiFrameId = parm2;
        mSlotTypeCd = parm3;
        mValue = parm4;
        mUrl = parm5;
        mBlob = parm6;
        mUrlTargetBlank = parm7;
        mAddDate = parm8;
        mAddBy = parm9;
        mModDate = parm10;
        mModBy = parm11;
        
    }

    /**
     * Creates a new UiFrameSlotData
     *
     * @return
     *  Newly initialized UiFrameSlotData object.
     */
    public static UiFrameSlotData createValue ()
    {
        UiFrameSlotData valueData = new UiFrameSlotData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this UiFrameSlotData object
     */
    public String toString()
    {
        return "[" + "UiFrameSlotId=" + mUiFrameSlotId + ", UiFrameId=" + mUiFrameId + ", SlotTypeCd=" + mSlotTypeCd + ", Value=" + mValue + ", Url=" + mUrl + ", Blob=" + mBlob + ", UrlTargetBlank=" + mUrlTargetBlank + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("UiFrameSlot");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mUiFrameSlotId));

        node =  doc.createElement("UiFrameId");
        node.appendChild(doc.createTextNode(String.valueOf(mUiFrameId)));
        root.appendChild(node);

        node =  doc.createElement("SlotTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mSlotTypeCd)));
        root.appendChild(node);

        node =  doc.createElement("Value");
        node.appendChild(doc.createTextNode(String.valueOf(mValue)));
        root.appendChild(node);

        node =  doc.createElement("Url");
        node.appendChild(doc.createTextNode(String.valueOf(mUrl)));
        root.appendChild(node);

        node =  doc.createElement("Blob");
        node.appendChild(doc.createTextNode(String.valueOf(mBlob)));
        root.appendChild(node);

        node =  doc.createElement("UrlTargetBlank");
        node.appendChild(doc.createTextNode(String.valueOf(mUrlTargetBlank)));
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
    * creates a clone of this object, the UiFrameSlotId field is not cloned.
    *
    * @return UiFrameSlotData object
    */
    public Object clone(){
        UiFrameSlotData myClone = new UiFrameSlotData();
        
        myClone.mUiFrameId = mUiFrameId;
        
        myClone.mSlotTypeCd = mSlotTypeCd;
        
        myClone.mValue = mValue;
        
        myClone.mUrl = mUrl;
        
        myClone.mBlob = mBlob;
        
        myClone.mUrlTargetBlank = mUrlTargetBlank;
        
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

        if (UiFrameSlotDataAccess.UI_FRAME_SLOT_ID.equals(pFieldName)) {
            return getUiFrameSlotId();
        } else if (UiFrameSlotDataAccess.UI_FRAME_ID.equals(pFieldName)) {
            return getUiFrameId();
        } else if (UiFrameSlotDataAccess.SLOT_TYPE_CD.equals(pFieldName)) {
            return getSlotTypeCd();
        } else if (UiFrameSlotDataAccess.VALUE.equals(pFieldName)) {
            return getValue();
        } else if (UiFrameSlotDataAccess.URL.equals(pFieldName)) {
            return getUrl();
        } else if (UiFrameSlotDataAccess.BLOB.equals(pFieldName)) {
            return getBlob();
        } else if (UiFrameSlotDataAccess.URL_TARGET_BLANK.equals(pFieldName)) {
            return getUrlTargetBlank();
        } else if (UiFrameSlotDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (UiFrameSlotDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (UiFrameSlotDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (UiFrameSlotDataAccess.MOD_BY.equals(pFieldName)) {
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
        return UiFrameSlotDataAccess.CLW_UI_FRAME_SLOT;
    }

    
    /**
     * Sets the UiFrameSlotId field. This field is required to be set in the database.
     *
     * @param pUiFrameSlotId
     *  int to use to update the field.
     */
    public void setUiFrameSlotId(int pUiFrameSlotId){
        this.mUiFrameSlotId = pUiFrameSlotId;
        setDirty(true);
    }
    /**
     * Retrieves the UiFrameSlotId field.
     *
     * @return
     *  int containing the UiFrameSlotId field.
     */
    public int getUiFrameSlotId(){
        return mUiFrameSlotId;
    }

    /**
     * Sets the UiFrameId field. This field is required to be set in the database.
     *
     * @param pUiFrameId
     *  int to use to update the field.
     */
    public void setUiFrameId(int pUiFrameId){
        this.mUiFrameId = pUiFrameId;
        setDirty(true);
    }
    /**
     * Retrieves the UiFrameId field.
     *
     * @return
     *  int containing the UiFrameId field.
     */
    public int getUiFrameId(){
        return mUiFrameId;
    }

    /**
     * Sets the SlotTypeCd field. This field is required to be set in the database.
     *
     * @param pSlotTypeCd
     *  String to use to update the field.
     */
    public void setSlotTypeCd(String pSlotTypeCd){
        this.mSlotTypeCd = pSlotTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the SlotTypeCd field.
     *
     * @return
     *  String containing the SlotTypeCd field.
     */
    public String getSlotTypeCd(){
        return mSlotTypeCd;
    }

    /**
     * Sets the Value field. This field is required to be set in the database.
     *
     * @param pValue
     *  String to use to update the field.
     */
    public void setValue(String pValue){
        this.mValue = pValue;
        setDirty(true);
    }
    /**
     * Retrieves the Value field.
     *
     * @return
     *  String containing the Value field.
     */
    public String getValue(){
        return mValue;
    }

    /**
     * Sets the Url field.
     *
     * @param pUrl
     *  String to use to update the field.
     */
    public void setUrl(String pUrl){
        this.mUrl = pUrl;
        setDirty(true);
    }
    /**
     * Retrieves the Url field.
     *
     * @return
     *  String containing the Url field.
     */
    public String getUrl(){
        return mUrl;
    }

    /**
     * Sets the Blob field.
     *
     * @param pBlob
     *  byte[] to use to update the field.
     */
    public void setBlob(byte[] pBlob){
        this.mBlob = pBlob;
        setDirty(true);
    }
    /**
     * Retrieves the Blob field.
     *
     * @return
     *  byte[] containing the Blob field.
     */
    public byte[] getBlob(){
        return mBlob;
    }

    /**
     * Sets the UrlTargetBlank field.
     *
     * @param pUrlTargetBlank
     *  String to use to update the field.
     */
    public void setUrlTargetBlank(String pUrlTargetBlank){
        this.mUrlTargetBlank = pUrlTargetBlank;
        setDirty(true);
    }
    /**
     * Retrieves the UrlTargetBlank field.
     *
     * @return
     *  String containing the UrlTargetBlank field.
     */
    public String getUrlTargetBlank(){
        return mUrlTargetBlank;
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
