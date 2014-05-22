
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        TradingPartnerPropData
 * Description:  This is a ValueObject class wrapping the database table CLW_TRADING_PARTNER_PROP.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.TradingPartnerPropDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>TradingPartnerPropData</code> is a ValueObject class wrapping of the database table CLW_TRADING_PARTNER_PROP.
 */
public class TradingPartnerPropData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 1L;
    private int mTradingPartnerPropertyId;// SQL type:NUMBER, not null
    private int mTradingPartnerId;// SQL type:NUMBER
    private String mShortDesc;// SQL type:VARCHAR2
    private String mValue;// SQL type:VARCHAR2
    private String mPropertyStatusCd;// SQL type:VARCHAR2, not null
    private String mPropertyTypeCd;// SQL type:VARCHAR2, not null
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public TradingPartnerPropData ()
    {
        mShortDesc = "";
        mValue = "";
        mPropertyStatusCd = "";
        mPropertyTypeCd = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public TradingPartnerPropData(int parm1, int parm2, String parm3, String parm4, String parm5, String parm6, Date parm7, String parm8, Date parm9, String parm10)
    {
        mTradingPartnerPropertyId = parm1;
        mTradingPartnerId = parm2;
        mShortDesc = parm3;
        mValue = parm4;
        mPropertyStatusCd = parm5;
        mPropertyTypeCd = parm6;
        mAddDate = parm7;
        mAddBy = parm8;
        mModDate = parm9;
        mModBy = parm10;
        
    }

    /**
     * Creates a new TradingPartnerPropData
     *
     * @return
     *  Newly initialized TradingPartnerPropData object.
     */
    public static TradingPartnerPropData createValue ()
    {
        TradingPartnerPropData valueData = new TradingPartnerPropData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this TradingPartnerPropData object
     */
    public String toString()
    {
        return "[" + "TradingPartnerPropertyId=" + mTradingPartnerPropertyId + ", TradingPartnerId=" + mTradingPartnerId + ", ShortDesc=" + mShortDesc + ", Value=" + mValue + ", PropertyStatusCd=" + mPropertyStatusCd + ", PropertyTypeCd=" + mPropertyTypeCd + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("TradingPartnerProp");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mTradingPartnerPropertyId));

        node =  doc.createElement("TradingPartnerId");
        node.appendChild(doc.createTextNode(String.valueOf(mTradingPartnerId)));
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

        return root;
    }

    /**
    * creates a clone of this object, the TradingPartnerPropId field is not cloned.
    *
    * @return TradingPartnerPropData object
    */
    public Object clone(){
        TradingPartnerPropData myClone = new TradingPartnerPropData();
        
        myClone.mTradingPartnerId = mTradingPartnerId;
        
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
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (TradingPartnerPropDataAccess.TRADING_PARTNER_PROPERTY_ID.equals(pFieldName)) {
            return getTradingPartnerPropertyId();
        } else if (TradingPartnerPropDataAccess.TRADING_PARTNER_ID.equals(pFieldName)) {
            return getTradingPartnerId();
        } else if (TradingPartnerPropDataAccess.SHORT_DESC.equals(pFieldName)) {
            return getShortDesc();
        } else if (TradingPartnerPropDataAccess.CLW_VALUE.equals(pFieldName)) {
            return getValue();
        } else if (TradingPartnerPropDataAccess.PROPERTY_STATUS_CD.equals(pFieldName)) {
            return getPropertyStatusCd();
        } else if (TradingPartnerPropDataAccess.PROPERTY_TYPE_CD.equals(pFieldName)) {
            return getPropertyTypeCd();
        } else if (TradingPartnerPropDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (TradingPartnerPropDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (TradingPartnerPropDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (TradingPartnerPropDataAccess.MOD_BY.equals(pFieldName)) {
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
        return TradingPartnerPropDataAccess.CLW_TRADING_PARTNER_PROP;
    }

    
    /**
     * Sets the TradingPartnerPropertyId field. This field is required to be set in the database.
     *
     * @param pTradingPartnerPropertyId
     *  int to use to update the field.
     */
    public void setTradingPartnerPropertyId(int pTradingPartnerPropertyId){
        this.mTradingPartnerPropertyId = pTradingPartnerPropertyId;
        setDirty(true);
    }
    /**
     * Retrieves the TradingPartnerPropertyId field.
     *
     * @return
     *  int containing the TradingPartnerPropertyId field.
     */
    public int getTradingPartnerPropertyId(){
        return mTradingPartnerPropertyId;
    }

    /**
     * Sets the TradingPartnerId field.
     *
     * @param pTradingPartnerId
     *  int to use to update the field.
     */
    public void setTradingPartnerId(int pTradingPartnerId){
        this.mTradingPartnerId = pTradingPartnerId;
        setDirty(true);
    }
    /**
     * Retrieves the TradingPartnerId field.
     *
     * @return
     *  int containing the TradingPartnerId field.
     */
    public int getTradingPartnerId(){
        return mTradingPartnerId;
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


}
