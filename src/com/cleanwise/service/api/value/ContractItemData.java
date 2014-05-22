
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ContractItemData
 * Description:  This is a ValueObject class wrapping the database table CLW_CONTRACT_ITEM.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.ContractItemDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>ContractItemData</code> is a ValueObject class wrapping of the database table CLW_CONTRACT_ITEM.
 */
public class ContractItemData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -2666792811533912578L;
    private int mContractId;// SQL type:NUMBER, not null
    private int mContractItemId;// SQL type:NUMBER, not null
    private int mItemId;// SQL type:NUMBER
    private java.math.BigDecimal mAmount;// SQL type:NUMBER
    private java.math.BigDecimal mDistCost;// SQL type:NUMBER
    private Date mEffDate;// SQL type:DATE
    private Date mExpDate;// SQL type:DATE
    private java.math.BigDecimal mDiscountAmount;// SQL type:NUMBER
    private String mCurrencyCd;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private java.math.BigDecimal mDistBaseCost;// SQL type:NUMBER
    private String mServiceFeeCode;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public ContractItemData ()
    {
        mCurrencyCd = "";
        mAddBy = "";
        mModBy = "";
        mServiceFeeCode = "";
    }

    /**
     * Constructor.
     */
    public ContractItemData(int parm1, int parm2, int parm3, java.math.BigDecimal parm4, java.math.BigDecimal parm5, Date parm6, Date parm7, java.math.BigDecimal parm8, String parm9, Date parm10, String parm11, Date parm12, String parm13, java.math.BigDecimal parm14, String parm15)
    {
        mContractId = parm1;
        mContractItemId = parm2;
        mItemId = parm3;
        mAmount = parm4;
        mDistCost = parm5;
        mEffDate = parm6;
        mExpDate = parm7;
        mDiscountAmount = parm8;
        mCurrencyCd = parm9;
        mAddDate = parm10;
        mAddBy = parm11;
        mModDate = parm12;
        mModBy = parm13;
        mDistBaseCost = parm14;
        mServiceFeeCode = parm15;
        
    }

    /**
     * Creates a new ContractItemData
     *
     * @return
     *  Newly initialized ContractItemData object.
     */
    public static ContractItemData createValue ()
    {
        ContractItemData valueData = new ContractItemData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ContractItemData object
     */
    public String toString()
    {
        return "[" + "ContractId=" + mContractId + ", ContractItemId=" + mContractItemId + ", ItemId=" + mItemId + ", Amount=" + mAmount + ", DistCost=" + mDistCost + ", EffDate=" + mEffDate + ", ExpDate=" + mExpDate + ", DiscountAmount=" + mDiscountAmount + ", CurrencyCd=" + mCurrencyCd + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", DistBaseCost=" + mDistBaseCost + ", ServiceFeeCode=" + mServiceFeeCode + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("ContractItem");
        
        Element node;

        node =  doc.createElement("ContractId");
        node.appendChild(doc.createTextNode(String.valueOf(mContractId)));
        root.appendChild(node);

        root.setAttribute("Id", String.valueOf(mContractItemId));

        node =  doc.createElement("ItemId");
        node.appendChild(doc.createTextNode(String.valueOf(mItemId)));
        root.appendChild(node);

        node =  doc.createElement("Amount");
        node.appendChild(doc.createTextNode(String.valueOf(mAmount)));
        root.appendChild(node);

        node =  doc.createElement("DistCost");
        node.appendChild(doc.createTextNode(String.valueOf(mDistCost)));
        root.appendChild(node);

        node =  doc.createElement("EffDate");
        node.appendChild(doc.createTextNode(String.valueOf(mEffDate)));
        root.appendChild(node);

        node =  doc.createElement("ExpDate");
        node.appendChild(doc.createTextNode(String.valueOf(mExpDate)));
        root.appendChild(node);

        node =  doc.createElement("DiscountAmount");
        node.appendChild(doc.createTextNode(String.valueOf(mDiscountAmount)));
        root.appendChild(node);

        node =  doc.createElement("CurrencyCd");
        node.appendChild(doc.createTextNode(String.valueOf(mCurrencyCd)));
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

        node =  doc.createElement("DistBaseCost");
        node.appendChild(doc.createTextNode(String.valueOf(mDistBaseCost)));
        root.appendChild(node);

        node =  doc.createElement("ServiceFeeCode");
        node.appendChild(doc.createTextNode(String.valueOf(mServiceFeeCode)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the ContractItemId field is not cloned.
    *
    * @return ContractItemData object
    */
    public Object clone(){
        ContractItemData myClone = new ContractItemData();
        
        myClone.mContractId = mContractId;
        
        myClone.mItemId = mItemId;
        
        myClone.mAmount = mAmount;
        
        myClone.mDistCost = mDistCost;
        
        if(mEffDate != null){
                myClone.mEffDate = (Date) mEffDate.clone();
        }
        
        if(mExpDate != null){
                myClone.mExpDate = (Date) mExpDate.clone();
        }
        
        myClone.mDiscountAmount = mDiscountAmount;
        
        myClone.mCurrencyCd = mCurrencyCd;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        myClone.mDistBaseCost = mDistBaseCost;
        
        myClone.mServiceFeeCode = mServiceFeeCode;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (ContractItemDataAccess.CONTRACT_ID.equals(pFieldName)) {
            return getContractId();
        } else if (ContractItemDataAccess.CONTRACT_ITEM_ID.equals(pFieldName)) {
            return getContractItemId();
        } else if (ContractItemDataAccess.ITEM_ID.equals(pFieldName)) {
            return getItemId();
        } else if (ContractItemDataAccess.AMOUNT.equals(pFieldName)) {
            return getAmount();
        } else if (ContractItemDataAccess.DIST_COST.equals(pFieldName)) {
            return getDistCost();
        } else if (ContractItemDataAccess.EFF_DATE.equals(pFieldName)) {
            return getEffDate();
        } else if (ContractItemDataAccess.EXP_DATE.equals(pFieldName)) {
            return getExpDate();
        } else if (ContractItemDataAccess.DISCOUNT_AMOUNT.equals(pFieldName)) {
            return getDiscountAmount();
        } else if (ContractItemDataAccess.CURRENCY_CD.equals(pFieldName)) {
            return getCurrencyCd();
        } else if (ContractItemDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (ContractItemDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (ContractItemDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (ContractItemDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (ContractItemDataAccess.DIST_BASE_COST.equals(pFieldName)) {
            return getDistBaseCost();
        } else if (ContractItemDataAccess.SERVICE_FEE_CODE.equals(pFieldName)) {
            return getServiceFeeCode();
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
        return ContractItemDataAccess.CLW_CONTRACT_ITEM;
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
     * Sets the ContractItemId field. This field is required to be set in the database.
     *
     * @param pContractItemId
     *  int to use to update the field.
     */
    public void setContractItemId(int pContractItemId){
        this.mContractItemId = pContractItemId;
        setDirty(true);
    }
    /**
     * Retrieves the ContractItemId field.
     *
     * @return
     *  int containing the ContractItemId field.
     */
    public int getContractItemId(){
        return mContractItemId;
    }

    /**
     * Sets the ItemId field.
     *
     * @param pItemId
     *  int to use to update the field.
     */
    public void setItemId(int pItemId){
        this.mItemId = pItemId;
        setDirty(true);
    }
    /**
     * Retrieves the ItemId field.
     *
     * @return
     *  int containing the ItemId field.
     */
    public int getItemId(){
        return mItemId;
    }

    /**
     * Sets the Amount field.
     *
     * @param pAmount
     *  java.math.BigDecimal to use to update the field.
     */
    public void setAmount(java.math.BigDecimal pAmount){
        this.mAmount = pAmount;
        setDirty(true);
    }
    /**
     * Retrieves the Amount field.
     *
     * @return
     *  java.math.BigDecimal containing the Amount field.
     */
    public java.math.BigDecimal getAmount(){
        return mAmount;
    }

    /**
     * Sets the DistCost field.
     *
     * @param pDistCost
     *  java.math.BigDecimal to use to update the field.
     */
    public void setDistCost(java.math.BigDecimal pDistCost){
        this.mDistCost = pDistCost;
        setDirty(true);
    }
    /**
     * Retrieves the DistCost field.
     *
     * @return
     *  java.math.BigDecimal containing the DistCost field.
     */
    public java.math.BigDecimal getDistCost(){
        return mDistCost;
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
     * Sets the DiscountAmount field.
     *
     * @param pDiscountAmount
     *  java.math.BigDecimal to use to update the field.
     */
    public void setDiscountAmount(java.math.BigDecimal pDiscountAmount){
        this.mDiscountAmount = pDiscountAmount;
        setDirty(true);
    }
    /**
     * Retrieves the DiscountAmount field.
     *
     * @return
     *  java.math.BigDecimal containing the DiscountAmount field.
     */
    public java.math.BigDecimal getDiscountAmount(){
        return mDiscountAmount;
    }

    /**
     * Sets the CurrencyCd field.
     *
     * @param pCurrencyCd
     *  String to use to update the field.
     */
    public void setCurrencyCd(String pCurrencyCd){
        this.mCurrencyCd = pCurrencyCd;
        setDirty(true);
    }
    /**
     * Retrieves the CurrencyCd field.
     *
     * @return
     *  String containing the CurrencyCd field.
     */
    public String getCurrencyCd(){
        return mCurrencyCd;
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
     * Sets the DistBaseCost field.
     *
     * @param pDistBaseCost
     *  java.math.BigDecimal to use to update the field.
     */
    public void setDistBaseCost(java.math.BigDecimal pDistBaseCost){
        this.mDistBaseCost = pDistBaseCost;
        setDirty(true);
    }
    /**
     * Retrieves the DistBaseCost field.
     *
     * @return
     *  java.math.BigDecimal containing the DistBaseCost field.
     */
    public java.math.BigDecimal getDistBaseCost(){
        return mDistBaseCost;
    }

    /**
     * Sets the ServiceFeeCode field.
     *
     * @param pServiceFeeCode
     *  String to use to update the field.
     */
    public void setServiceFeeCode(String pServiceFeeCode){
        this.mServiceFeeCode = pServiceFeeCode;
        setDirty(true);
    }
    /**
     * Retrieves the ServiceFeeCode field.
     *
     * @return
     *  String containing the ServiceFeeCode field.
     */
    public String getServiceFeeCode(){
        return mServiceFeeCode;
    }


}
