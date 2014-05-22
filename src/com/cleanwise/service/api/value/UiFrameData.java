
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        UiFrameData
 * Description:  This is a ValueObject class wrapping the database table CLW_UI_FRAME.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.UiFrameDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>UiFrameData</code> is a ValueObject class wrapping of the database table CLW_UI_FRAME.
 */
public class UiFrameData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 1L;
    private int mUiFrameId;// SQL type:NUMBER, not null
    private String mShortDesc;// SQL type:VARCHAR2, not null
    private String mLongDesc;// SQL type:VARCHAR2
    private String mJsp;// SQL type:VARCHAR2, not null
    private int mBusEntityId;// SQL type:NUMBER, not null
    private String mFrameTypeCd;// SQL type:VARCHAR2, not null
    private int mParentUiFrameId;// SQL type:NUMBER
    private String mStatusCd;// SQL type:VARCHAR2, not null
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2, not null
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private String mLocaleCd;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public UiFrameData ()
    {
        mShortDesc = "";
        mLongDesc = "";
        mJsp = "";
        mFrameTypeCd = "";
        mStatusCd = "";
        mAddBy = "";
        mModBy = "";
        mLocaleCd = "";
    }

    /**
     * Constructor.
     */
    public UiFrameData(int parm1, String parm2, String parm3, String parm4, int parm5, String parm6, int parm7, String parm8, Date parm9, String parm10, Date parm11, String parm12, String parm13)
    {
        mUiFrameId = parm1;
        mShortDesc = parm2;
        mLongDesc = parm3;
        mJsp = parm4;
        mBusEntityId = parm5;
        mFrameTypeCd = parm6;
        mParentUiFrameId = parm7;
        mStatusCd = parm8;
        mAddDate = parm9;
        mAddBy = parm10;
        mModDate = parm11;
        mModBy = parm12;
        mLocaleCd = parm13;
        
    }

    /**
     * Creates a new UiFrameData
     *
     * @return
     *  Newly initialized UiFrameData object.
     */
    public static UiFrameData createValue ()
    {
        UiFrameData valueData = new UiFrameData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this UiFrameData object
     */
    public String toString()
    {
        return "[" + "UiFrameId=" + mUiFrameId + ", ShortDesc=" + mShortDesc + ", LongDesc=" + mLongDesc + ", Jsp=" + mJsp + ", BusEntityId=" + mBusEntityId + ", FrameTypeCd=" + mFrameTypeCd + ", ParentUiFrameId=" + mParentUiFrameId + ", StatusCd=" + mStatusCd + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", LocaleCd=" + mLocaleCd + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("UiFrame");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mUiFrameId));

        node =  doc.createElement("ShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mShortDesc)));
        root.appendChild(node);

        node =  doc.createElement("LongDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mLongDesc)));
        root.appendChild(node);

        node =  doc.createElement("Jsp");
        node.appendChild(doc.createTextNode(String.valueOf(mJsp)));
        root.appendChild(node);

        node =  doc.createElement("BusEntityId");
        node.appendChild(doc.createTextNode(String.valueOf(mBusEntityId)));
        root.appendChild(node);

        node =  doc.createElement("FrameTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mFrameTypeCd)));
        root.appendChild(node);

        node =  doc.createElement("ParentUiFrameId");
        node.appendChild(doc.createTextNode(String.valueOf(mParentUiFrameId)));
        root.appendChild(node);

        node =  doc.createElement("StatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mStatusCd)));
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

        node =  doc.createElement("LocaleCd");
        node.appendChild(doc.createTextNode(String.valueOf(mLocaleCd)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the UiFrameId field is not cloned.
    *
    * @return UiFrameData object
    */
    public Object clone(){
        UiFrameData myClone = new UiFrameData();
        
        myClone.mShortDesc = mShortDesc;
        
        myClone.mLongDesc = mLongDesc;
        
        myClone.mJsp = mJsp;
        
        myClone.mBusEntityId = mBusEntityId;
        
        myClone.mFrameTypeCd = mFrameTypeCd;
        
        myClone.mParentUiFrameId = mParentUiFrameId;
        
        myClone.mStatusCd = mStatusCd;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        myClone.mLocaleCd = mLocaleCd;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (UiFrameDataAccess.UI_FRAME_ID.equals(pFieldName)) {
            return getUiFrameId();
        } else if (UiFrameDataAccess.SHORT_DESC.equals(pFieldName)) {
            return getShortDesc();
        } else if (UiFrameDataAccess.LONG_DESC.equals(pFieldName)) {
            return getLongDesc();
        } else if (UiFrameDataAccess.JSP.equals(pFieldName)) {
            return getJsp();
        } else if (UiFrameDataAccess.BUS_ENTITY_ID.equals(pFieldName)) {
            return getBusEntityId();
        } else if (UiFrameDataAccess.FRAME_TYPE_CD.equals(pFieldName)) {
            return getFrameTypeCd();
        } else if (UiFrameDataAccess.PARENT_UI_FRAME_ID.equals(pFieldName)) {
            return getParentUiFrameId();
        } else if (UiFrameDataAccess.STATUS_CD.equals(pFieldName)) {
            return getStatusCd();
        } else if (UiFrameDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (UiFrameDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (UiFrameDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (UiFrameDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (UiFrameDataAccess.LOCALE_CD.equals(pFieldName)) {
            return getLocaleCd();
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
        return UiFrameDataAccess.CLW_UI_FRAME;
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
     * Sets the Jsp field. This field is required to be set in the database.
     *
     * @param pJsp
     *  String to use to update the field.
     */
    public void setJsp(String pJsp){
        this.mJsp = pJsp;
        setDirty(true);
    }
    /**
     * Retrieves the Jsp field.
     *
     * @return
     *  String containing the Jsp field.
     */
    public String getJsp(){
        return mJsp;
    }

    /**
     * Sets the BusEntityId field. This field is required to be set in the database.
     *
     * @param pBusEntityId
     *  int to use to update the field.
     */
    public void setBusEntityId(int pBusEntityId){
        this.mBusEntityId = pBusEntityId;
        setDirty(true);
    }
    /**
     * Retrieves the BusEntityId field.
     *
     * @return
     *  int containing the BusEntityId field.
     */
    public int getBusEntityId(){
        return mBusEntityId;
    }

    /**
     * Sets the FrameTypeCd field. This field is required to be set in the database.
     *
     * @param pFrameTypeCd
     *  String to use to update the field.
     */
    public void setFrameTypeCd(String pFrameTypeCd){
        this.mFrameTypeCd = pFrameTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the FrameTypeCd field.
     *
     * @return
     *  String containing the FrameTypeCd field.
     */
    public String getFrameTypeCd(){
        return mFrameTypeCd;
    }

    /**
     * Sets the ParentUiFrameId field.
     *
     * @param pParentUiFrameId
     *  int to use to update the field.
     */
    public void setParentUiFrameId(int pParentUiFrameId){
        this.mParentUiFrameId = pParentUiFrameId;
        setDirty(true);
    }
    /**
     * Retrieves the ParentUiFrameId field.
     *
     * @return
     *  int containing the ParentUiFrameId field.
     */
    public int getParentUiFrameId(){
        return mParentUiFrameId;
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

    /**
     * Sets the LocaleCd field.
     *
     * @param pLocaleCd
     *  String to use to update the field.
     */
    public void setLocaleCd(String pLocaleCd){
        this.mLocaleCd = pLocaleCd;
        setDirty(true);
    }
    /**
     * Retrieves the LocaleCd field.
     *
     * @return
     *  String containing the LocaleCd field.
     */
    public String getLocaleCd(){
        return mLocaleCd;
    }


}
