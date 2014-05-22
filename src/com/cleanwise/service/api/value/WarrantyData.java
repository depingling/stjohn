
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        WarrantyData
 * Description:  This is a ValueObject class wrapping the database table CLW_WARRANTY.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.WarrantyDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>WarrantyData</code> is a ValueObject class wrapping of the database table CLW_WARRANTY.
 */
public class WarrantyData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -6255342023768974406L;
    private int mWarrantyId;// SQL type:NUMBER, not null
    private String mWarrantyNumber;// SQL type:VARCHAR2, not null
    private String mShortDesc;// SQL type:VARCHAR2, not null
    private String mLongDesc;// SQL type:VARCHAR2
    private String mBusinessName;// SQL type:VARCHAR2
    private String mStatusCd;// SQL type:VARCHAR2, not null
    private String mTypeCd;// SQL type:VARCHAR2, not null
    private int mDuration;// SQL type:NUMBER, not null
    private java.math.BigDecimal mCost;// SQL type:NUMBER
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private String mDurationTypeCd;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public WarrantyData ()
    {
        mWarrantyNumber = "";
        mShortDesc = "";
        mLongDesc = "";
        mBusinessName = "";
        mStatusCd = "";
        mTypeCd = "";
        mAddBy = "";
        mModBy = "";
        mDurationTypeCd = "";
    }

    /**
     * Constructor.
     */
    public WarrantyData(int parm1, String parm2, String parm3, String parm4, String parm5, String parm6, String parm7, int parm8, java.math.BigDecimal parm9, Date parm10, String parm11, Date parm12, String parm13, String parm14)
    {
        mWarrantyId = parm1;
        mWarrantyNumber = parm2;
        mShortDesc = parm3;
        mLongDesc = parm4;
        mBusinessName = parm5;
        mStatusCd = parm6;
        mTypeCd = parm7;
        mDuration = parm8;
        mCost = parm9;
        mAddDate = parm10;
        mAddBy = parm11;
        mModDate = parm12;
        mModBy = parm13;
        mDurationTypeCd = parm14;
        
    }

    /**
     * Creates a new WarrantyData
     *
     * @return
     *  Newly initialized WarrantyData object.
     */
    public static WarrantyData createValue ()
    {
        WarrantyData valueData = new WarrantyData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this WarrantyData object
     */
    public String toString()
    {
        return "[" + "WarrantyId=" + mWarrantyId + ", WarrantyNumber=" + mWarrantyNumber + ", ShortDesc=" + mShortDesc + ", LongDesc=" + mLongDesc + ", BusinessName=" + mBusinessName + ", StatusCd=" + mStatusCd + ", TypeCd=" + mTypeCd + ", Duration=" + mDuration + ", Cost=" + mCost + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", DurationTypeCd=" + mDurationTypeCd + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.getDocumentElement();
        
        Element node;

        root.setAttribute("Id", String.valueOf(mWarrantyId));

        node =  doc.createElement("WarrantyNumber");
        node.appendChild(doc.createTextNode(String.valueOf(mWarrantyNumber)));
        root.appendChild(node);

        node =  doc.createElement("ShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mShortDesc)));
        root.appendChild(node);

        node =  doc.createElement("LongDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mLongDesc)));
        root.appendChild(node);

        node =  doc.createElement("BusinessName");
        node.appendChild(doc.createTextNode(String.valueOf(mBusinessName)));
        root.appendChild(node);

        node =  doc.createElement("StatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mStatusCd)));
        root.appendChild(node);

        node =  doc.createElement("TypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mTypeCd)));
        root.appendChild(node);

        node =  doc.createElement("Duration");
        node.appendChild(doc.createTextNode(String.valueOf(mDuration)));
        root.appendChild(node);

        node =  doc.createElement("Cost");
        node.appendChild(doc.createTextNode(String.valueOf(mCost)));
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

        node =  doc.createElement("DurationTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mDurationTypeCd)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the WarrantyId field is not cloned.
    *
    * @return WarrantyData object
    */
    public Object clone(){
        WarrantyData myClone = new WarrantyData();
        
        myClone.mWarrantyNumber = mWarrantyNumber;
        
        myClone.mShortDesc = mShortDesc;
        
        myClone.mLongDesc = mLongDesc;
        
        myClone.mBusinessName = mBusinessName;
        
        myClone.mStatusCd = mStatusCd;
        
        myClone.mTypeCd = mTypeCd;
        
        myClone.mDuration = mDuration;
        
        myClone.mCost = mCost;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        myClone.mDurationTypeCd = mDurationTypeCd;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (WarrantyDataAccess.WARRANTY_ID.equals(pFieldName)) {
            return getWarrantyId();
        } else if (WarrantyDataAccess.WARRANTY_NUMBER.equals(pFieldName)) {
            return getWarrantyNumber();
        } else if (WarrantyDataAccess.SHORT_DESC.equals(pFieldName)) {
            return getShortDesc();
        } else if (WarrantyDataAccess.LONG_DESC.equals(pFieldName)) {
            return getLongDesc();
        } else if (WarrantyDataAccess.BUSINESS_NAME.equals(pFieldName)) {
            return getBusinessName();
        } else if (WarrantyDataAccess.STATUS_CD.equals(pFieldName)) {
            return getStatusCd();
        } else if (WarrantyDataAccess.TYPE_CD.equals(pFieldName)) {
            return getTypeCd();
        } else if (WarrantyDataAccess.DURATION.equals(pFieldName)) {
            return getDuration();
        } else if (WarrantyDataAccess.COST.equals(pFieldName)) {
            return getCost();
        } else if (WarrantyDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (WarrantyDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (WarrantyDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (WarrantyDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (WarrantyDataAccess.DURATION_TYPE_CD.equals(pFieldName)) {
            return getDurationTypeCd();
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
        return WarrantyDataAccess.CLW_WARRANTY;
    }

    
    /**
     * Sets the WarrantyId field. This field is required to be set in the database.
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
     * Sets the WarrantyNumber field. This field is required to be set in the database.
     *
     * @param pWarrantyNumber
     *  String to use to update the field.
     */
    public void setWarrantyNumber(String pWarrantyNumber){
        this.mWarrantyNumber = pWarrantyNumber;
        setDirty(true);
    }
    /**
     * Retrieves the WarrantyNumber field.
     *
     * @return
     *  String containing the WarrantyNumber field.
     */
    public String getWarrantyNumber(){
        return mWarrantyNumber;
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
     * Sets the BusinessName field.
     *
     * @param pBusinessName
     *  String to use to update the field.
     */
    public void setBusinessName(String pBusinessName){
        this.mBusinessName = pBusinessName;
        setDirty(true);
    }
    /**
     * Retrieves the BusinessName field.
     *
     * @return
     *  String containing the BusinessName field.
     */
    public String getBusinessName(){
        return mBusinessName;
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
     * Sets the Duration field. This field is required to be set in the database.
     *
     * @param pDuration
     *  int to use to update the field.
     */
    public void setDuration(int pDuration){
        this.mDuration = pDuration;
        setDirty(true);
    }
    /**
     * Retrieves the Duration field.
     *
     * @return
     *  int containing the Duration field.
     */
    public int getDuration(){
        return mDuration;
    }

    /**
     * Sets the Cost field.
     *
     * @param pCost
     *  java.math.BigDecimal to use to update the field.
     */
    public void setCost(java.math.BigDecimal pCost){
        this.mCost = pCost;
        setDirty(true);
    }
    /**
     * Retrieves the Cost field.
     *
     * @return
     *  java.math.BigDecimal containing the Cost field.
     */
    public java.math.BigDecimal getCost(){
        return mCost;
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
     * Sets the DurationTypeCd field.
     *
     * @param pDurationTypeCd
     *  String to use to update the field.
     */
    public void setDurationTypeCd(String pDurationTypeCd){
        this.mDurationTypeCd = pDurationTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the DurationTypeCd field.
     *
     * @return
     *  String containing the DurationTypeCd field.
     */
    public String getDurationTypeCd(){
        return mDurationTypeCd;
    }


}
