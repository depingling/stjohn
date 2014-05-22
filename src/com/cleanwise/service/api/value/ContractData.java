
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ContractData
 * Description:  This is a ValueObject class wrapping the database table CLW_CONTRACT.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.ContractDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>ContractData</code> is a ValueObject class wrapping of the database table CLW_CONTRACT.
 */
public class ContractData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -8132757524672175518L;
    private int mContractId;// SQL type:NUMBER, not null
    private String mRefContractNum;// SQL type:VARCHAR2, not null
    private String mShortDesc;// SQL type:VARCHAR2, not null
    private String mLongDesc;// SQL type:VARCHAR2
    private int mCatalogId;// SQL type:NUMBER, not null
    private String mContractStatusCd;// SQL type:VARCHAR2, not null
    private String mContractTypeCd;// SQL type:VARCHAR2, not null
    private Date mEffDate;// SQL type:DATE
    private Date mExpDate;// SQL type:DATE
    private Date mAcceptanceDate;// SQL type:DATE
    private Date mQuoteExpDate;// SQL type:DATE
    private boolean mContractItemsOnlyInd;// SQL type:NUMBER
    private boolean mHidePricingInd;// SQL type:NUMBER
    private String mLocaleCd;// SQL type:VARCHAR2, not null
    private int mRankWeight;// SQL type:NUMBER
    private int mFreightTableId;// SQL type:NUMBER
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private int mDiscountTableId;// SQL type:NUMBER

    /**
     * Constructor.
     */
    public ContractData ()
    {
        mRefContractNum = "";
        mShortDesc = "";
        mLongDesc = "";
        mContractStatusCd = "";
        mContractTypeCd = "";
        mLocaleCd = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public ContractData(int parm1, String parm2, String parm3, String parm4, int parm5, String parm6, String parm7, Date parm8, Date parm9, Date parm10, Date parm11, boolean parm12, boolean parm13, String parm14, int parm15, int parm16, Date parm17, String parm18, Date parm19, String parm20, int parm21)
    {
        mContractId = parm1;
        mRefContractNum = parm2;
        mShortDesc = parm3;
        mLongDesc = parm4;
        mCatalogId = parm5;
        mContractStatusCd = parm6;
        mContractTypeCd = parm7;
        mEffDate = parm8;
        mExpDate = parm9;
        mAcceptanceDate = parm10;
        mQuoteExpDate = parm11;
        mContractItemsOnlyInd = parm12;
        mHidePricingInd = parm13;
        mLocaleCd = parm14;
        mRankWeight = parm15;
        mFreightTableId = parm16;
        mAddDate = parm17;
        mAddBy = parm18;
        mModDate = parm19;
        mModBy = parm20;
        mDiscountTableId = parm21;
        
    }

    /**
     * Creates a new ContractData
     *
     * @return
     *  Newly initialized ContractData object.
     */
    public static ContractData createValue ()
    {
        ContractData valueData = new ContractData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ContractData object
     */
    public String toString()
    {
        return "[" + "ContractId=" + mContractId + ", RefContractNum=" + mRefContractNum + ", ShortDesc=" + mShortDesc + ", LongDesc=" + mLongDesc + ", CatalogId=" + mCatalogId + ", ContractStatusCd=" + mContractStatusCd + ", ContractTypeCd=" + mContractTypeCd + ", EffDate=" + mEffDate + ", ExpDate=" + mExpDate + ", AcceptanceDate=" + mAcceptanceDate + ", QuoteExpDate=" + mQuoteExpDate + ", ContractItemsOnlyInd=" + mContractItemsOnlyInd + ", HidePricingInd=" + mHidePricingInd + ", LocaleCd=" + mLocaleCd + ", RankWeight=" + mRankWeight + ", FreightTableId=" + mFreightTableId + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", DiscountTableId=" + mDiscountTableId + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("Contract");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mContractId));

        node =  doc.createElement("RefContractNum");
        node.appendChild(doc.createTextNode(String.valueOf(mRefContractNum)));
        root.appendChild(node);

        node =  doc.createElement("ShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mShortDesc)));
        root.appendChild(node);

        node =  doc.createElement("LongDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mLongDesc)));
        root.appendChild(node);

        node =  doc.createElement("CatalogId");
        node.appendChild(doc.createTextNode(String.valueOf(mCatalogId)));
        root.appendChild(node);

        node =  doc.createElement("ContractStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mContractStatusCd)));
        root.appendChild(node);

        node =  doc.createElement("ContractTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mContractTypeCd)));
        root.appendChild(node);

        node =  doc.createElement("EffDate");
        node.appendChild(doc.createTextNode(String.valueOf(mEffDate)));
        root.appendChild(node);

        node =  doc.createElement("ExpDate");
        node.appendChild(doc.createTextNode(String.valueOf(mExpDate)));
        root.appendChild(node);

        node =  doc.createElement("AcceptanceDate");
        node.appendChild(doc.createTextNode(String.valueOf(mAcceptanceDate)));
        root.appendChild(node);

        node =  doc.createElement("QuoteExpDate");
        node.appendChild(doc.createTextNode(String.valueOf(mQuoteExpDate)));
        root.appendChild(node);

        node =  doc.createElement("ContractItemsOnlyInd");
        node.appendChild(doc.createTextNode(String.valueOf(mContractItemsOnlyInd)));
        root.appendChild(node);

        node =  doc.createElement("HidePricingInd");
        node.appendChild(doc.createTextNode(String.valueOf(mHidePricingInd)));
        root.appendChild(node);

        node =  doc.createElement("LocaleCd");
        node.appendChild(doc.createTextNode(String.valueOf(mLocaleCd)));
        root.appendChild(node);

        node =  doc.createElement("RankWeight");
        node.appendChild(doc.createTextNode(String.valueOf(mRankWeight)));
        root.appendChild(node);

        node =  doc.createElement("FreightTableId");
        node.appendChild(doc.createTextNode(String.valueOf(mFreightTableId)));
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

        node =  doc.createElement("DiscountTableId");
        node.appendChild(doc.createTextNode(String.valueOf(mDiscountTableId)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the ContractId field is not cloned.
    *
    * @return ContractData object
    */
    public Object clone(){
        ContractData myClone = new ContractData();
        
        myClone.mRefContractNum = mRefContractNum;
        
        myClone.mShortDesc = mShortDesc;
        
        myClone.mLongDesc = mLongDesc;
        
        myClone.mCatalogId = mCatalogId;
        
        myClone.mContractStatusCd = mContractStatusCd;
        
        myClone.mContractTypeCd = mContractTypeCd;
        
        if(mEffDate != null){
                myClone.mEffDate = (Date) mEffDate.clone();
        }
        
        if(mExpDate != null){
                myClone.mExpDate = (Date) mExpDate.clone();
        }
        
        if(mAcceptanceDate != null){
                myClone.mAcceptanceDate = (Date) mAcceptanceDate.clone();
        }
        
        if(mQuoteExpDate != null){
                myClone.mQuoteExpDate = (Date) mQuoteExpDate.clone();
        }
        
        myClone.mContractItemsOnlyInd = mContractItemsOnlyInd;
        
        myClone.mHidePricingInd = mHidePricingInd;
        
        myClone.mLocaleCd = mLocaleCd;
        
        myClone.mRankWeight = mRankWeight;
        
        myClone.mFreightTableId = mFreightTableId;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        myClone.mDiscountTableId = mDiscountTableId;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (ContractDataAccess.CONTRACT_ID.equals(pFieldName)) {
            return getContractId();
        } else if (ContractDataAccess.REF_CONTRACT_NUM.equals(pFieldName)) {
            return getRefContractNum();
        } else if (ContractDataAccess.SHORT_DESC.equals(pFieldName)) {
            return getShortDesc();
        } else if (ContractDataAccess.LONG_DESC.equals(pFieldName)) {
            return getLongDesc();
        } else if (ContractDataAccess.CATALOG_ID.equals(pFieldName)) {
            return getCatalogId();
        } else if (ContractDataAccess.CONTRACT_STATUS_CD.equals(pFieldName)) {
            return getContractStatusCd();
        } else if (ContractDataAccess.CONTRACT_TYPE_CD.equals(pFieldName)) {
            return getContractTypeCd();
        } else if (ContractDataAccess.EFF_DATE.equals(pFieldName)) {
            return getEffDate();
        } else if (ContractDataAccess.EXP_DATE.equals(pFieldName)) {
            return getExpDate();
        } else if (ContractDataAccess.ACCEPTANCE_DATE.equals(pFieldName)) {
            return getAcceptanceDate();
        } else if (ContractDataAccess.QUOTE_EXP_DATE.equals(pFieldName)) {
            return getQuoteExpDate();
        } else if (ContractDataAccess.CONTRACT_ITEMS_ONLY_IND.equals(pFieldName)) {
            return getContractItemsOnlyInd();
        } else if (ContractDataAccess.HIDE_PRICING_IND.equals(pFieldName)) {
            return getHidePricingInd();
        } else if (ContractDataAccess.LOCALE_CD.equals(pFieldName)) {
            return getLocaleCd();
        } else if (ContractDataAccess.RANK_WEIGHT.equals(pFieldName)) {
            return getRankWeight();
        } else if (ContractDataAccess.FREIGHT_TABLE_ID.equals(pFieldName)) {
            return getFreightTableId();
        } else if (ContractDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (ContractDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (ContractDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (ContractDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (ContractDataAccess.DISCOUNT_TABLE_ID.equals(pFieldName)) {
            return getDiscountTableId();
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
        return ContractDataAccess.CLW_CONTRACT;
    }

    
    /**
     * Sets the ContractId field. This field is required to be set in the database.
     *
     * @param pContractId
     *  int to use to update the field.
     */
    public void setContractId(int pContractId){
        this.mContractId = pContractId;
        setDirty(true);
    }
    /**
     * Retrieves the ContractId field.
     *
     * @return
     *  int containing the ContractId field.
     */
    public int getContractId(){
        return mContractId;
    }

    /**
     * Sets the RefContractNum field. This field is required to be set in the database.
     *
     * @param pRefContractNum
     *  String to use to update the field.
     */
    public void setRefContractNum(String pRefContractNum){
        this.mRefContractNum = pRefContractNum;
        setDirty(true);
    }
    /**
     * Retrieves the RefContractNum field.
     *
     * @return
     *  String containing the RefContractNum field.
     */
    public String getRefContractNum(){
        return mRefContractNum;
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
     * Sets the CatalogId field. This field is required to be set in the database.
     *
     * @param pCatalogId
     *  int to use to update the field.
     */
    public void setCatalogId(int pCatalogId){
        this.mCatalogId = pCatalogId;
        setDirty(true);
    }
    /**
     * Retrieves the CatalogId field.
     *
     * @return
     *  int containing the CatalogId field.
     */
    public int getCatalogId(){
        return mCatalogId;
    }

    /**
     * Sets the ContractStatusCd field. This field is required to be set in the database.
     *
     * @param pContractStatusCd
     *  String to use to update the field.
     */
    public void setContractStatusCd(String pContractStatusCd){
        this.mContractStatusCd = pContractStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the ContractStatusCd field.
     *
     * @return
     *  String containing the ContractStatusCd field.
     */
    public String getContractStatusCd(){
        return mContractStatusCd;
    }

    /**
     * Sets the ContractTypeCd field. This field is required to be set in the database.
     *
     * @param pContractTypeCd
     *  String to use to update the field.
     */
    public void setContractTypeCd(String pContractTypeCd){
        this.mContractTypeCd = pContractTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the ContractTypeCd field.
     *
     * @return
     *  String containing the ContractTypeCd field.
     */
    public String getContractTypeCd(){
        return mContractTypeCd;
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
     * Sets the AcceptanceDate field.
     *
     * @param pAcceptanceDate
     *  Date to use to update the field.
     */
    public void setAcceptanceDate(Date pAcceptanceDate){
        this.mAcceptanceDate = pAcceptanceDate;
        setDirty(true);
    }
    /**
     * Retrieves the AcceptanceDate field.
     *
     * @return
     *  Date containing the AcceptanceDate field.
     */
    public Date getAcceptanceDate(){
        return mAcceptanceDate;
    }

    /**
     * Sets the QuoteExpDate field.
     *
     * @param pQuoteExpDate
     *  Date to use to update the field.
     */
    public void setQuoteExpDate(Date pQuoteExpDate){
        this.mQuoteExpDate = pQuoteExpDate;
        setDirty(true);
    }
    /**
     * Retrieves the QuoteExpDate field.
     *
     * @return
     *  Date containing the QuoteExpDate field.
     */
    public Date getQuoteExpDate(){
        return mQuoteExpDate;
    }

    /**
     * Sets the ContractItemsOnlyInd field.
     *
     * @param pContractItemsOnlyInd
     *  boolean to use to update the field.
     */
    public void setContractItemsOnlyInd(boolean pContractItemsOnlyInd){
        this.mContractItemsOnlyInd = pContractItemsOnlyInd;
        setDirty(true);
    }
    /**
     * Retrieves the ContractItemsOnlyInd field.
     *
     * @return
     *  boolean containing the ContractItemsOnlyInd field.
     */
    public boolean getContractItemsOnlyInd(){
        return mContractItemsOnlyInd;
    }

    /**
     * Sets the HidePricingInd field.
     *
     * @param pHidePricingInd
     *  boolean to use to update the field.
     */
    public void setHidePricingInd(boolean pHidePricingInd){
        this.mHidePricingInd = pHidePricingInd;
        setDirty(true);
    }
    /**
     * Retrieves the HidePricingInd field.
     *
     * @return
     *  boolean containing the HidePricingInd field.
     */
    public boolean getHidePricingInd(){
        return mHidePricingInd;
    }

    /**
     * Sets the LocaleCd field. This field is required to be set in the database.
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
     * Sets the RankWeight field.
     *
     * @param pRankWeight
     *  int to use to update the field.
     */
    public void setRankWeight(int pRankWeight){
        this.mRankWeight = pRankWeight;
        setDirty(true);
    }
    /**
     * Retrieves the RankWeight field.
     *
     * @return
     *  int containing the RankWeight field.
     */
    public int getRankWeight(){
        return mRankWeight;
    }

    /**
     * Sets the FreightTableId field.
     *
     * @param pFreightTableId
     *  int to use to update the field.
     */
    public void setFreightTableId(int pFreightTableId){
        this.mFreightTableId = pFreightTableId;
        setDirty(true);
    }
    /**
     * Retrieves the FreightTableId field.
     *
     * @return
     *  int containing the FreightTableId field.
     */
    public int getFreightTableId(){
        return mFreightTableId;
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
     * Sets the DiscountTableId field.
     *
     * @param pDiscountTableId
     *  int to use to update the field.
     */
    public void setDiscountTableId(int pDiscountTableId){
        this.mDiscountTableId = pDiscountTableId;
        setDirty(true);
    }
    /**
     * Retrieves the DiscountTableId field.
     *
     * @return
     *  int containing the DiscountTableId field.
     */
    public int getDiscountTableId(){
        return mDiscountTableId;
    }


}
