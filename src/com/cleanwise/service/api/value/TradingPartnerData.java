
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        TradingPartnerData
 * Description:  This is a ValueObject class wrapping the database table CLW_TRADING_PARTNER.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.TradingPartnerDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>TradingPartnerData</code> is a ValueObject class wrapping of the database table CLW_TRADING_PARTNER.
 */
public class TradingPartnerData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -3935969572328048691L;
    private int mTradingPartnerId;// SQL type:NUMBER, not null
    private String mShortDesc;// SQL type:VARCHAR2, not null
    private String mTradingTypeCd;// SQL type:VARCHAR2, not null
    private String mTradingPartnerTypeCd;// SQL type:VARCHAR2, not null
    private String mTradingPartnerStatusCd;// SQL type:VARCHAR2, not null
    private String mSkuTypeCd;// SQL type:VARCHAR2, not null
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private String mUomConversionTypeCd;// SQL type:VARCHAR2
    private String mSiteIdentifierTypeCd;// SQL type:VARCHAR2
    private String mValidateContractPrice;// SQL type:VARCHAR2
    private String mPoNumberType;// SQL type:VARCHAR2
    private String mAccountIdentifierInbound;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public TradingPartnerData ()
    {
        mShortDesc = "";
        mTradingTypeCd = "";
        mTradingPartnerTypeCd = "";
        mTradingPartnerStatusCd = "";
        mSkuTypeCd = "";
        mAddBy = "";
        mModBy = "";
        mUomConversionTypeCd = "";
        mSiteIdentifierTypeCd = "";
        mValidateContractPrice = "";
        mPoNumberType = "";
        mAccountIdentifierInbound = "";
    }

    /**
     * Constructor.
     */
    public TradingPartnerData(int parm1, String parm2, String parm3, String parm4, String parm5, String parm6, Date parm7, String parm8, Date parm9, String parm10, String parm11, String parm12, String parm13, String parm14, String parm15)
    {
        mTradingPartnerId = parm1;
        mShortDesc = parm2;
        mTradingTypeCd = parm3;
        mTradingPartnerTypeCd = parm4;
        mTradingPartnerStatusCd = parm5;
        mSkuTypeCd = parm6;
        mAddDate = parm7;
        mAddBy = parm8;
        mModDate = parm9;
        mModBy = parm10;
        mUomConversionTypeCd = parm11;
        mSiteIdentifierTypeCd = parm12;
        mValidateContractPrice = parm13;
        mPoNumberType = parm14;
        mAccountIdentifierInbound = parm15;
        
    }

    /**
     * Creates a new TradingPartnerData
     *
     * @return
     *  Newly initialized TradingPartnerData object.
     */
    public static TradingPartnerData createValue ()
    {
        TradingPartnerData valueData = new TradingPartnerData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this TradingPartnerData object
     */
    public String toString()
    {
        return "[" + "TradingPartnerId=" + mTradingPartnerId + ", ShortDesc=" + mShortDesc + ", TradingTypeCd=" + mTradingTypeCd + ", TradingPartnerTypeCd=" + mTradingPartnerTypeCd + ", TradingPartnerStatusCd=" + mTradingPartnerStatusCd + ", SkuTypeCd=" + mSkuTypeCd + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", UomConversionTypeCd=" + mUomConversionTypeCd + ", SiteIdentifierTypeCd=" + mSiteIdentifierTypeCd + ", ValidateContractPrice=" + mValidateContractPrice + ", PoNumberType=" + mPoNumberType + ", AccountIdentifierInbound=" + mAccountIdentifierInbound + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("TradingPartner");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mTradingPartnerId));

        node =  doc.createElement("ShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mShortDesc)));
        root.appendChild(node);

        node =  doc.createElement("TradingTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mTradingTypeCd)));
        root.appendChild(node);

        node =  doc.createElement("TradingPartnerTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mTradingPartnerTypeCd)));
        root.appendChild(node);

        node =  doc.createElement("TradingPartnerStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mTradingPartnerStatusCd)));
        root.appendChild(node);

        node =  doc.createElement("SkuTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mSkuTypeCd)));
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

        node =  doc.createElement("UomConversionTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mUomConversionTypeCd)));
        root.appendChild(node);

        node =  doc.createElement("SiteIdentifierTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mSiteIdentifierTypeCd)));
        root.appendChild(node);

        node =  doc.createElement("ValidateContractPrice");
        node.appendChild(doc.createTextNode(String.valueOf(mValidateContractPrice)));
        root.appendChild(node);

        node =  doc.createElement("PoNumberType");
        node.appendChild(doc.createTextNode(String.valueOf(mPoNumberType)));
        root.appendChild(node);

        node =  doc.createElement("AccountIdentifierInbound");
        node.appendChild(doc.createTextNode(String.valueOf(mAccountIdentifierInbound)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the TradingPartnerId field is not cloned.
    *
    * @return TradingPartnerData object
    */
    public Object clone(){
        TradingPartnerData myClone = new TradingPartnerData();
        
        myClone.mShortDesc = mShortDesc;
        
        myClone.mTradingTypeCd = mTradingTypeCd;
        
        myClone.mTradingPartnerTypeCd = mTradingPartnerTypeCd;
        
        myClone.mTradingPartnerStatusCd = mTradingPartnerStatusCd;
        
        myClone.mSkuTypeCd = mSkuTypeCd;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        myClone.mUomConversionTypeCd = mUomConversionTypeCd;
        
        myClone.mSiteIdentifierTypeCd = mSiteIdentifierTypeCd;
        
        myClone.mValidateContractPrice = mValidateContractPrice;
        
        myClone.mPoNumberType = mPoNumberType;
        
        myClone.mAccountIdentifierInbound = mAccountIdentifierInbound;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (TradingPartnerDataAccess.TRADING_PARTNER_ID.equals(pFieldName)) {
            return getTradingPartnerId();
        } else if (TradingPartnerDataAccess.SHORT_DESC.equals(pFieldName)) {
            return getShortDesc();
        } else if (TradingPartnerDataAccess.TRADING_TYPE_CD.equals(pFieldName)) {
            return getTradingTypeCd();
        } else if (TradingPartnerDataAccess.TRADING_PARTNER_TYPE_CD.equals(pFieldName)) {
            return getTradingPartnerTypeCd();
        } else if (TradingPartnerDataAccess.TRADING_PARTNER_STATUS_CD.equals(pFieldName)) {
            return getTradingPartnerStatusCd();
        } else if (TradingPartnerDataAccess.SKU_TYPE_CD.equals(pFieldName)) {
            return getSkuTypeCd();
        } else if (TradingPartnerDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (TradingPartnerDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (TradingPartnerDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (TradingPartnerDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (TradingPartnerDataAccess.UOM_CONVERSION_TYPE_CD.equals(pFieldName)) {
            return getUomConversionTypeCd();
        } else if (TradingPartnerDataAccess.SITE_IDENTIFIER_TYPE_CD.equals(pFieldName)) {
            return getSiteIdentifierTypeCd();
        } else if (TradingPartnerDataAccess.VALIDATE_CONTRACT_PRICE.equals(pFieldName)) {
            return getValidateContractPrice();
        } else if (TradingPartnerDataAccess.PO_NUMBER_TYPE.equals(pFieldName)) {
            return getPoNumberType();
        } else if (TradingPartnerDataAccess.ACCOUNT_IDENTIFIER_INBOUND.equals(pFieldName)) {
            return getAccountIdentifierInbound();
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
        return TradingPartnerDataAccess.CLW_TRADING_PARTNER;
    }

    
    /**
     * Sets the TradingPartnerId field. This field is required to be set in the database.
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
     * Sets the TradingTypeCd field. This field is required to be set in the database.
     *
     * @param pTradingTypeCd
     *  String to use to update the field.
     */
    public void setTradingTypeCd(String pTradingTypeCd){
        this.mTradingTypeCd = pTradingTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the TradingTypeCd field.
     *
     * @return
     *  String containing the TradingTypeCd field.
     */
    public String getTradingTypeCd(){
        return mTradingTypeCd;
    }

    /**
     * Sets the TradingPartnerTypeCd field. This field is required to be set in the database.
     *
     * @param pTradingPartnerTypeCd
     *  String to use to update the field.
     */
    public void setTradingPartnerTypeCd(String pTradingPartnerTypeCd){
        this.mTradingPartnerTypeCd = pTradingPartnerTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the TradingPartnerTypeCd field.
     *
     * @return
     *  String containing the TradingPartnerTypeCd field.
     */
    public String getTradingPartnerTypeCd(){
        return mTradingPartnerTypeCd;
    }

    /**
     * Sets the TradingPartnerStatusCd field. This field is required to be set in the database.
     *
     * @param pTradingPartnerStatusCd
     *  String to use to update the field.
     */
    public void setTradingPartnerStatusCd(String pTradingPartnerStatusCd){
        this.mTradingPartnerStatusCd = pTradingPartnerStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the TradingPartnerStatusCd field.
     *
     * @return
     *  String containing the TradingPartnerStatusCd field.
     */
    public String getTradingPartnerStatusCd(){
        return mTradingPartnerStatusCd;
    }

    /**
     * Sets the SkuTypeCd field. This field is required to be set in the database.
     *
     * @param pSkuTypeCd
     *  String to use to update the field.
     */
    public void setSkuTypeCd(String pSkuTypeCd){
        this.mSkuTypeCd = pSkuTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the SkuTypeCd field.
     *
     * @return
     *  String containing the SkuTypeCd field.
     */
    public String getSkuTypeCd(){
        return mSkuTypeCd;
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
     * Sets the UomConversionTypeCd field.
     *
     * @param pUomConversionTypeCd
     *  String to use to update the field.
     */
    public void setUomConversionTypeCd(String pUomConversionTypeCd){
        this.mUomConversionTypeCd = pUomConversionTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the UomConversionTypeCd field.
     *
     * @return
     *  String containing the UomConversionTypeCd field.
     */
    public String getUomConversionTypeCd(){
        return mUomConversionTypeCd;
    }

    /**
     * Sets the SiteIdentifierTypeCd field.
     *
     * @param pSiteIdentifierTypeCd
     *  String to use to update the field.
     */
    public void setSiteIdentifierTypeCd(String pSiteIdentifierTypeCd){
        this.mSiteIdentifierTypeCd = pSiteIdentifierTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the SiteIdentifierTypeCd field.
     *
     * @return
     *  String containing the SiteIdentifierTypeCd field.
     */
    public String getSiteIdentifierTypeCd(){
        return mSiteIdentifierTypeCd;
    }

    /**
     * Sets the ValidateContractPrice field.
     *
     * @param pValidateContractPrice
     *  String to use to update the field.
     */
    public void setValidateContractPrice(String pValidateContractPrice){
        this.mValidateContractPrice = pValidateContractPrice;
        setDirty(true);
    }
    /**
     * Retrieves the ValidateContractPrice field.
     *
     * @return
     *  String containing the ValidateContractPrice field.
     */
    public String getValidateContractPrice(){
        return mValidateContractPrice;
    }

    /**
     * Sets the PoNumberType field.
     *
     * @param pPoNumberType
     *  String to use to update the field.
     */
    public void setPoNumberType(String pPoNumberType){
        this.mPoNumberType = pPoNumberType;
        setDirty(true);
    }
    /**
     * Retrieves the PoNumberType field.
     *
     * @return
     *  String containing the PoNumberType field.
     */
    public String getPoNumberType(){
        return mPoNumberType;
    }

    /**
     * Sets the AccountIdentifierInbound field.
     *
     * @param pAccountIdentifierInbound
     *  String to use to update the field.
     */
    public void setAccountIdentifierInbound(String pAccountIdentifierInbound){
        this.mAccountIdentifierInbound = pAccountIdentifierInbound;
        setDirty(true);
    }
    /**
     * Retrieves the AccountIdentifierInbound field.
     *
     * @return
     *  String containing the AccountIdentifierInbound field.
     */
    public String getAccountIdentifierInbound(){
        return mAccountIdentifierInbound;
    }


}
