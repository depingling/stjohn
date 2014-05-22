
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        PriceRuleData
 * Description:  This is a ValueObject class wrapping the database table CLW_PRICE_RULE.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.PriceRuleDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>PriceRuleData</code> is a ValueObject class wrapping of the database table CLW_PRICE_RULE.
 */
public class PriceRuleData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 6332526833348629833L;
    private int mPriceRuleId;// SQL type:NUMBER, not null
    private String mShortDesc;// SQL type:VARCHAR2
    private String mPriceRuleTypeCd;// SQL type:VARCHAR2
    private String mPriceRuleStatusCd;// SQL type:VARCHAR2
    private Date mEffDate;// SQL type:DATE
    private Date mExpDate;// SQL type:DATE
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private int mBusEntityId;// SQL type:NUMBER

    /**
     * Constructor.
     */
    public PriceRuleData ()
    {
        mShortDesc = "";
        mPriceRuleTypeCd = "";
        mPriceRuleStatusCd = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public PriceRuleData(int parm1, String parm2, String parm3, String parm4, Date parm5, Date parm6, Date parm7, String parm8, Date parm9, String parm10, int parm11)
    {
        mPriceRuleId = parm1;
        mShortDesc = parm2;
        mPriceRuleTypeCd = parm3;
        mPriceRuleStatusCd = parm4;
        mEffDate = parm5;
        mExpDate = parm6;
        mAddDate = parm7;
        mAddBy = parm8;
        mModDate = parm9;
        mModBy = parm10;
        mBusEntityId = parm11;
        
    }

    /**
     * Creates a new PriceRuleData
     *
     * @return
     *  Newly initialized PriceRuleData object.
     */
    public static PriceRuleData createValue ()
    {
        PriceRuleData valueData = new PriceRuleData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this PriceRuleData object
     */
    public String toString()
    {
        return "[" + "PriceRuleId=" + mPriceRuleId + ", ShortDesc=" + mShortDesc + ", PriceRuleTypeCd=" + mPriceRuleTypeCd + ", PriceRuleStatusCd=" + mPriceRuleStatusCd + ", EffDate=" + mEffDate + ", ExpDate=" + mExpDate + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", BusEntityId=" + mBusEntityId + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("PriceRule");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mPriceRuleId));

        node =  doc.createElement("ShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mShortDesc)));
        root.appendChild(node);

        node =  doc.createElement("PriceRuleTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mPriceRuleTypeCd)));
        root.appendChild(node);

        node =  doc.createElement("PriceRuleStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mPriceRuleStatusCd)));
        root.appendChild(node);

        node =  doc.createElement("EffDate");
        node.appendChild(doc.createTextNode(String.valueOf(mEffDate)));
        root.appendChild(node);

        node =  doc.createElement("ExpDate");
        node.appendChild(doc.createTextNode(String.valueOf(mExpDate)));
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

        node =  doc.createElement("BusEntityId");
        node.appendChild(doc.createTextNode(String.valueOf(mBusEntityId)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the PriceRuleId field is not cloned.
    *
    * @return PriceRuleData object
    */
    public Object clone(){
        PriceRuleData myClone = new PriceRuleData();
        
        myClone.mShortDesc = mShortDesc;
        
        myClone.mPriceRuleTypeCd = mPriceRuleTypeCd;
        
        myClone.mPriceRuleStatusCd = mPriceRuleStatusCd;
        
        if(mEffDate != null){
                myClone.mEffDate = (Date) mEffDate.clone();
        }
        
        if(mExpDate != null){
                myClone.mExpDate = (Date) mExpDate.clone();
        }
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        myClone.mBusEntityId = mBusEntityId;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (PriceRuleDataAccess.PRICE_RULE_ID.equals(pFieldName)) {
            return getPriceRuleId();
        } else if (PriceRuleDataAccess.SHORT_DESC.equals(pFieldName)) {
            return getShortDesc();
        } else if (PriceRuleDataAccess.PRICE_RULE_TYPE_CD.equals(pFieldName)) {
            return getPriceRuleTypeCd();
        } else if (PriceRuleDataAccess.PRICE_RULE_STATUS_CD.equals(pFieldName)) {
            return getPriceRuleStatusCd();
        } else if (PriceRuleDataAccess.EFF_DATE.equals(pFieldName)) {
            return getEffDate();
        } else if (PriceRuleDataAccess.EXP_DATE.equals(pFieldName)) {
            return getExpDate();
        } else if (PriceRuleDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (PriceRuleDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (PriceRuleDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (PriceRuleDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (PriceRuleDataAccess.BUS_ENTITY_ID.equals(pFieldName)) {
            return getBusEntityId();
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
        return PriceRuleDataAccess.CLW_PRICE_RULE;
    }

    
    /**
     * Sets the PriceRuleId field. This field is required to be set in the database.
     *
     * @param pPriceRuleId
     *  int to use to update the field.
     */
    public void setPriceRuleId(int pPriceRuleId){
        this.mPriceRuleId = pPriceRuleId;
        setDirty(true);
    }
    /**
     * Retrieves the PriceRuleId field.
     *
     * @return
     *  int containing the PriceRuleId field.
     */
    public int getPriceRuleId(){
        return mPriceRuleId;
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
     * Sets the PriceRuleTypeCd field.
     *
     * @param pPriceRuleTypeCd
     *  String to use to update the field.
     */
    public void setPriceRuleTypeCd(String pPriceRuleTypeCd){
        this.mPriceRuleTypeCd = pPriceRuleTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the PriceRuleTypeCd field.
     *
     * @return
     *  String containing the PriceRuleTypeCd field.
     */
    public String getPriceRuleTypeCd(){
        return mPriceRuleTypeCd;
    }

    /**
     * Sets the PriceRuleStatusCd field.
     *
     * @param pPriceRuleStatusCd
     *  String to use to update the field.
     */
    public void setPriceRuleStatusCd(String pPriceRuleStatusCd){
        this.mPriceRuleStatusCd = pPriceRuleStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the PriceRuleStatusCd field.
     *
     * @return
     *  String containing the PriceRuleStatusCd field.
     */
    public String getPriceRuleStatusCd(){
        return mPriceRuleStatusCd;
    }

    /**
     * Sets the EffDate field.
     *
     * @param pEffDate
     *  Date to use to update the field.
     */
    public void setEffDate(Date pEffDate){
        this.mEffDate = pEffDate;
        setDirty(true);
    }
    /**
     * Retrieves the EffDate field.
     *
     * @return
     *  Date containing the EffDate field.
     */
    public Date getEffDate(){
        return mEffDate;
    }

    /**
     * Sets the ExpDate field.
     *
     * @param pExpDate
     *  Date to use to update the field.
     */
    public void setExpDate(Date pExpDate){
        this.mExpDate = pExpDate;
        setDirty(true);
    }
    /**
     * Retrieves the ExpDate field.
     *
     * @return
     *  Date containing the ExpDate field.
     */
    public Date getExpDate(){
        return mExpDate;
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


}
