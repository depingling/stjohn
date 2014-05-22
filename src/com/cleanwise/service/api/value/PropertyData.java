
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        PropertyData
 * Description:  This is a ValueObject class wrapping the database table CLW_PROPERTY.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.PropertyDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>PropertyData</code> is a ValueObject class wrapping of the database table CLW_PROPERTY.
 */
public class PropertyData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 8116864918440830884L;
    private int mPropertyId;// SQL type:NUMBER, not null
    private int mBusEntityId;// SQL type:NUMBER
    private int mUserId;// SQL type:NUMBER
    private String mShortDesc;// SQL type:VARCHAR2
    private String mValue;// SQL type:VARCHAR2
    private String mPropertyStatusCd;// SQL type:VARCHAR2, not null
    private String mPropertyTypeCd;// SQL type:VARCHAR2, not null
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private String mLocaleCd;// SQL type:VARCHAR2
    private int mOriginalUserId;// SQL type:NUMBER

    /**
     * Constructor.
     */
    public PropertyData ()
    {
        mShortDesc = "";
        mValue = "";
        mPropertyStatusCd = "";
        mPropertyTypeCd = "";
        mAddBy = "";
        mModBy = "";
        mLocaleCd = "";
    }

    /**
     * Constructor.
     */
    public PropertyData(int parm1, int parm2, int parm3, String parm4, String parm5, String parm6, String parm7, Date parm8, String parm9, Date parm10, String parm11, String parm12, int parm13)
    {
        mPropertyId = parm1;
        mBusEntityId = parm2;
        mUserId = parm3;
        mShortDesc = parm4;
        mValue = parm5;
        mPropertyStatusCd = parm6;
        mPropertyTypeCd = parm7;
        mAddDate = parm8;
        mAddBy = parm9;
        mModDate = parm10;
        mModBy = parm11;
        mLocaleCd = parm12;
        mOriginalUserId = parm13;
        
    }

    /**
     * Creates a new PropertyData
     *
     * @return
     *  Newly initialized PropertyData object.
     */
    public static PropertyData createValue ()
    {
        PropertyData valueData = new PropertyData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this PropertyData object
     */
    public String toString()
    {
        return "[" + "PropertyId=" + mPropertyId + ", BusEntityId=" + mBusEntityId + ", UserId=" + mUserId + ", ShortDesc=" + mShortDesc + ", Value=" + mValue + ", PropertyStatusCd=" + mPropertyStatusCd + ", PropertyTypeCd=" + mPropertyTypeCd + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", LocaleCd=" + mLocaleCd + ", OriginalUserId=" + mOriginalUserId + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("Property");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mPropertyId));

        node =  doc.createElement("BusEntityId");
        node.appendChild(doc.createTextNode(String.valueOf(mBusEntityId)));
        root.appendChild(node);

        node =  doc.createElement("UserId");
        node.appendChild(doc.createTextNode(String.valueOf(mUserId)));
        root.appendChild(node);

        node =  doc.createElement("ShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mShortDesc)));
        root.appendChild(node);

        node =  doc.createElement("Value");
        node.appendChild(doc.createTextNode(String.valueOf(mValue)));
        root.appendChild(node);

        node =  doc.createElement("PropertyStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mPropertyStatusCd)));
        root.appendChild(node);

        node =  doc.createElement("PropertyTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mPropertyTypeCd)));
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

        node =  doc.createElement("OriginalUserId");
        node.appendChild(doc.createTextNode(String.valueOf(mOriginalUserId)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the PropertyId field is not cloned.
    *
    * @return PropertyData object
    */
    public Object clone(){
        PropertyData myClone = new PropertyData();
        
        myClone.mBusEntityId = mBusEntityId;
        
        myClone.mUserId = mUserId;
        
        myClone.mShortDesc = mShortDesc;
        
        myClone.mValue = mValue;
        
        myClone.mPropertyStatusCd = mPropertyStatusCd;
        
        myClone.mPropertyTypeCd = mPropertyTypeCd;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        myClone.mLocaleCd = mLocaleCd;
        
        myClone.mOriginalUserId = mOriginalUserId;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (PropertyDataAccess.PROPERTY_ID.equals(pFieldName)) {
            return getPropertyId();
        } else if (PropertyDataAccess.BUS_ENTITY_ID.equals(pFieldName)) {
            return getBusEntityId();
        } else if (PropertyDataAccess.USER_ID.equals(pFieldName)) {
            return getUserId();
        } else if (PropertyDataAccess.SHORT_DESC.equals(pFieldName)) {
            return getShortDesc();
        } else if (PropertyDataAccess.CLW_VALUE.equals(pFieldName)) {
            return getValue();
        } else if (PropertyDataAccess.PROPERTY_STATUS_CD.equals(pFieldName)) {
            return getPropertyStatusCd();
        } else if (PropertyDataAccess.PROPERTY_TYPE_CD.equals(pFieldName)) {
            return getPropertyTypeCd();
        } else if (PropertyDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (PropertyDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (PropertyDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (PropertyDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (PropertyDataAccess.LOCALE_CD.equals(pFieldName)) {
            return getLocaleCd();
        } else if (PropertyDataAccess.ORIGINAL_USER_ID.equals(pFieldName)) {
            return getOriginalUserId();
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
        return PropertyDataAccess.CLW_PROPERTY;
    }

    
    /**
     * Sets the PropertyId field. This field is required to be set in the database.
     *
     * @param pPropertyId
     *  int to use to update the field.
     */
    public void setPropertyId(int pPropertyId){
        this.mPropertyId = pPropertyId;
        setDirty(true);
    }
    /**
     * Retrieves the PropertyId field.
     *
     * @return
     *  int containing the PropertyId field.
     */
    public int getPropertyId(){
        return mPropertyId;
    }

    /**
     * Sets the BusEntityId field.
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
     * Sets the UserId field.
     *
     * @param pUserId
     *  int to use to update the field.
     */
    public void setUserId(int pUserId){
        this.mUserId = pUserId;
        setDirty(true);
    }
    /**
     * Retrieves the UserId field.
     *
     * @return
     *  int containing the UserId field.
     */
    public int getUserId(){
        return mUserId;
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
     * Sets the Value field.
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
     * Sets the PropertyStatusCd field. This field is required to be set in the database.
     *
     * @param pPropertyStatusCd
     *  String to use to update the field.
     */
    public void setPropertyStatusCd(String pPropertyStatusCd){
        this.mPropertyStatusCd = pPropertyStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the PropertyStatusCd field.
     *
     * @return
     *  String containing the PropertyStatusCd field.
     */
    public String getPropertyStatusCd(){
        return mPropertyStatusCd;
    }

    /**
     * Sets the PropertyTypeCd field. This field is required to be set in the database.
     *
     * @param pPropertyTypeCd
     *  String to use to update the field.
     */
    public void setPropertyTypeCd(String pPropertyTypeCd){
        this.mPropertyTypeCd = pPropertyTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the PropertyTypeCd field.
     *
     * @return
     *  String containing the PropertyTypeCd field.
     */
    public String getPropertyTypeCd(){
        return mPropertyTypeCd;
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

    /**
     * Sets the OriginalUserId field.
     *
     * @param pOriginalUserId
     *  int to use to update the field.
     */
    public void setOriginalUserId(int pOriginalUserId){
        this.mOriginalUserId = pOriginalUserId;
        setDirty(true);
    }
    /**
     * Retrieves the OriginalUserId field.
     *
     * @return
     *  int containing the OriginalUserId field.
     */
    public int getOriginalUserId(){
        return mOriginalUserId;
    }


}
