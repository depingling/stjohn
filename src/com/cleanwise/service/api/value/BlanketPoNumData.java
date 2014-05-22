
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        BlanketPoNumData
 * Description:  This is a ValueObject class wrapping the database table CLW_BLANKET_PO_NUM.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.BlanketPoNumDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>BlanketPoNumData</code> is a ValueObject class wrapping of the database table CLW_BLANKET_PO_NUM.
 */
public class BlanketPoNumData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 2495825689297791368L;
    private int mBlanketPoNumId;// SQL type:NUMBER, not null
    private String mShortDesc;// SQL type:VARCHAR2
    private String mPoNumber;// SQL type:VARCHAR2
    private String mSeperator;// SQL type:VARCHAR2
    private int mCurrentRelease;// SQL type:NUMBER
    private String mBlanketCustPoNumberTypeCd;// SQL type:VARCHAR2
    private String mStatusCd;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public BlanketPoNumData ()
    {
        mShortDesc = "";
        mPoNumber = "";
        mSeperator = "";
        mBlanketCustPoNumberTypeCd = "";
        mStatusCd = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public BlanketPoNumData(int parm1, String parm2, String parm3, String parm4, int parm5, String parm6, String parm7, Date parm8, String parm9, Date parm10, String parm11)
    {
        mBlanketPoNumId = parm1;
        mShortDesc = parm2;
        mPoNumber = parm3;
        mSeperator = parm4;
        mCurrentRelease = parm5;
        mBlanketCustPoNumberTypeCd = parm6;
        mStatusCd = parm7;
        mAddDate = parm8;
        mAddBy = parm9;
        mModDate = parm10;
        mModBy = parm11;
        
    }

    /**
     * Creates a new BlanketPoNumData
     *
     * @return
     *  Newly initialized BlanketPoNumData object.
     */
    public static BlanketPoNumData createValue ()
    {
        BlanketPoNumData valueData = new BlanketPoNumData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this BlanketPoNumData object
     */
    public String toString()
    {
        return "[" + "BlanketPoNumId=" + mBlanketPoNumId + ", ShortDesc=" + mShortDesc + ", PoNumber=" + mPoNumber + ", Seperator=" + mSeperator + ", CurrentRelease=" + mCurrentRelease + ", BlanketCustPoNumberTypeCd=" + mBlanketCustPoNumberTypeCd + ", StatusCd=" + mStatusCd + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("BlanketPoNum");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mBlanketPoNumId));

        node =  doc.createElement("ShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mShortDesc)));
        root.appendChild(node);

        node =  doc.createElement("PoNumber");
        node.appendChild(doc.createTextNode(String.valueOf(mPoNumber)));
        root.appendChild(node);

        node =  doc.createElement("Seperator");
        node.appendChild(doc.createTextNode(String.valueOf(mSeperator)));
        root.appendChild(node);

        node =  doc.createElement("CurrentRelease");
        node.appendChild(doc.createTextNode(String.valueOf(mCurrentRelease)));
        root.appendChild(node);

        node =  doc.createElement("BlanketCustPoNumberTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mBlanketCustPoNumberTypeCd)));
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

        return root;
    }

    /**
    * creates a clone of this object, the BlanketPoNumId field is not cloned.
    *
    * @return BlanketPoNumData object
    */
    public Object clone(){
        BlanketPoNumData myClone = new BlanketPoNumData();
        
        myClone.mShortDesc = mShortDesc;
        
        myClone.mPoNumber = mPoNumber;
        
        myClone.mSeperator = mSeperator;
        
        myClone.mCurrentRelease = mCurrentRelease;
        
        myClone.mBlanketCustPoNumberTypeCd = mBlanketCustPoNumberTypeCd;
        
        myClone.mStatusCd = mStatusCd;
        
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

        if (BlanketPoNumDataAccess.BLANKET_PO_NUM_ID.equals(pFieldName)) {
            return getBlanketPoNumId();
        } else if (BlanketPoNumDataAccess.SHORT_DESC.equals(pFieldName)) {
            return getShortDesc();
        } else if (BlanketPoNumDataAccess.PO_NUMBER.equals(pFieldName)) {
            return getPoNumber();
        } else if (BlanketPoNumDataAccess.SEPERATOR.equals(pFieldName)) {
            return getSeperator();
        } else if (BlanketPoNumDataAccess.CURRENT_RELEASE.equals(pFieldName)) {
            return getCurrentRelease();
        } else if (BlanketPoNumDataAccess.BLANKET_CUST_PO_NUMBER_TYPE_CD.equals(pFieldName)) {
            return getBlanketCustPoNumberTypeCd();
        } else if (BlanketPoNumDataAccess.STATUS_CD.equals(pFieldName)) {
            return getStatusCd();
        } else if (BlanketPoNumDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (BlanketPoNumDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (BlanketPoNumDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (BlanketPoNumDataAccess.MOD_BY.equals(pFieldName)) {
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
        return BlanketPoNumDataAccess.CLW_BLANKET_PO_NUM;
    }

    
    /**
     * Sets the BlanketPoNumId field. This field is required to be set in the database.
     *
     * @param pBlanketPoNumId
     *  int to use to update the field.
     */
    public void setBlanketPoNumId(int pBlanketPoNumId){
        this.mBlanketPoNumId = pBlanketPoNumId;
        setDirty(true);
    }
    /**
     * Retrieves the BlanketPoNumId field.
     *
     * @return
     *  int containing the BlanketPoNumId field.
     */
    public int getBlanketPoNumId(){
        return mBlanketPoNumId;
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
     * Sets the PoNumber field.
     *
     * @param pPoNumber
     *  String to use to update the field.
     */
    public void setPoNumber(String pPoNumber){
        this.mPoNumber = pPoNumber;
        setDirty(true);
    }
    /**
     * Retrieves the PoNumber field.
     *
     * @return
     *  String containing the PoNumber field.
     */
    public String getPoNumber(){
        return mPoNumber;
    }

    /**
     * Sets the Seperator field.
     *
     * @param pSeperator
     *  String to use to update the field.
     */
    public void setSeperator(String pSeperator){
        this.mSeperator = pSeperator;
        setDirty(true);
    }
    /**
     * Retrieves the Seperator field.
     *
     * @return
     *  String containing the Seperator field.
     */
    public String getSeperator(){
        return mSeperator;
    }

    /**
     * Sets the CurrentRelease field.
     *
     * @param pCurrentRelease
     *  int to use to update the field.
     */
    public void setCurrentRelease(int pCurrentRelease){
        this.mCurrentRelease = pCurrentRelease;
        setDirty(true);
    }
    /**
     * Retrieves the CurrentRelease field.
     *
     * @return
     *  int containing the CurrentRelease field.
     */
    public int getCurrentRelease(){
        return mCurrentRelease;
    }

    /**
     * Sets the BlanketCustPoNumberTypeCd field.
     *
     * @param pBlanketCustPoNumberTypeCd
     *  String to use to update the field.
     */
    public void setBlanketCustPoNumberTypeCd(String pBlanketCustPoNumberTypeCd){
        this.mBlanketCustPoNumberTypeCd = pBlanketCustPoNumberTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the BlanketCustPoNumberTypeCd field.
     *
     * @return
     *  String containing the BlanketCustPoNumberTypeCd field.
     */
    public String getBlanketCustPoNumberTypeCd(){
        return mBlanketCustPoNumberTypeCd;
    }

    /**
     * Sets the StatusCd field.
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
     * Sets the AddDate field.
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
     * Sets the ModDate field.
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
