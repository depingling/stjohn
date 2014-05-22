
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        FreightTableCriteriaData
 * Description:  This is a ValueObject class wrapping the database table CLW_FREIGHT_TABLE_CRITERIA.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.FreightTableCriteriaDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>FreightTableCriteriaData</code> is a ValueObject class wrapping of the database table CLW_FREIGHT_TABLE_CRITERIA.
 */
public class FreightTableCriteriaData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -4317518619726289634L;
    private int mFreightTableId;// SQL type:NUMBER, not null
    private int mFreightTableCriteriaId;// SQL type:NUMBER, not null
    private java.math.BigDecimal mLowerAmount;// SQL type:NUMBER
    private java.math.BigDecimal mHigherAmount;// SQL type:NUMBER
    private java.math.BigDecimal mFreightAmount;// SQL type:NUMBER
    private java.math.BigDecimal mHandlingAmount;// SQL type:NUMBER
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private int mFreightHandlerId;// SQL type:NUMBER
    private String mFreightCriteriaTypeCd;// SQL type:VARCHAR2
    private String mRuntimeTypeCd;// SQL type:VARCHAR2
    private String mShortDesc;// SQL type:VARCHAR2
    private int mUiOrder;// SQL type:NUMBER
    private String mChargeCd;// SQL type:VARCHAR2
    private java.math.BigDecimal mDiscount;// SQL type:NUMBER

    /**
     * Constructor.
     */
    public FreightTableCriteriaData ()
    {
        mAddBy = "";
        mModBy = "";
        mFreightCriteriaTypeCd = "";
        mRuntimeTypeCd = "";
        mShortDesc = "";
        mChargeCd = "";
    }

    /**
     * Constructor.
     */
    public FreightTableCriteriaData(int parm1, int parm2, java.math.BigDecimal parm3, java.math.BigDecimal parm4, java.math.BigDecimal parm5, java.math.BigDecimal parm6, Date parm7, String parm8, Date parm9, String parm10, int parm11, String parm12, String parm13, String parm14, int parm15, String parm16, java.math.BigDecimal parm17)
    {
        mFreightTableId = parm1;
        mFreightTableCriteriaId = parm2;
        mLowerAmount = parm3;
        mHigherAmount = parm4;
        mFreightAmount = parm5;
        mHandlingAmount = parm6;
        mAddDate = parm7;
        mAddBy = parm8;
        mModDate = parm9;
        mModBy = parm10;
        mFreightHandlerId = parm11;
        mFreightCriteriaTypeCd = parm12;
        mRuntimeTypeCd = parm13;
        mShortDesc = parm14;
        mUiOrder = parm15;
        mChargeCd = parm16;
        mDiscount = parm17;
        
    }

    /**
     * Creates a new FreightTableCriteriaData
     *
     * @return
     *  Newly initialized FreightTableCriteriaData object.
     */
    public static FreightTableCriteriaData createValue ()
    {
        FreightTableCriteriaData valueData = new FreightTableCriteriaData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this FreightTableCriteriaData object
     */
    public String toString()
    {
        return "[" + "FreightTableId=" + mFreightTableId + ", FreightTableCriteriaId=" + mFreightTableCriteriaId + ", LowerAmount=" + mLowerAmount + ", HigherAmount=" + mHigherAmount + ", FreightAmount=" + mFreightAmount + ", HandlingAmount=" + mHandlingAmount + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", FreightHandlerId=" + mFreightHandlerId + ", FreightCriteriaTypeCd=" + mFreightCriteriaTypeCd + ", RuntimeTypeCd=" + mRuntimeTypeCd + ", ShortDesc=" + mShortDesc + ", UiOrder=" + mUiOrder + ", ChargeCd=" + mChargeCd + ", Discount=" + mDiscount + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("FreightTableCriteria");
        
        Element node;

        node =  doc.createElement("FreightTableId");
        node.appendChild(doc.createTextNode(String.valueOf(mFreightTableId)));
        root.appendChild(node);

        root.setAttribute("Id", String.valueOf(mFreightTableCriteriaId));

        node =  doc.createElement("LowerAmount");
        node.appendChild(doc.createTextNode(String.valueOf(mLowerAmount)));
        root.appendChild(node);

        node =  doc.createElement("HigherAmount");
        node.appendChild(doc.createTextNode(String.valueOf(mHigherAmount)));
        root.appendChild(node);

        node =  doc.createElement("FreightAmount");
        node.appendChild(doc.createTextNode(String.valueOf(mFreightAmount)));
        root.appendChild(node);

        node =  doc.createElement("HandlingAmount");
        node.appendChild(doc.createTextNode(String.valueOf(mHandlingAmount)));
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

        node =  doc.createElement("FreightHandlerId");
        node.appendChild(doc.createTextNode(String.valueOf(mFreightHandlerId)));
        root.appendChild(node);

        node =  doc.createElement("FreightCriteriaTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mFreightCriteriaTypeCd)));
        root.appendChild(node);

        node =  doc.createElement("RuntimeTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mRuntimeTypeCd)));
        root.appendChild(node);

        node =  doc.createElement("ShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mShortDesc)));
        root.appendChild(node);

        node =  doc.createElement("UiOrder");
        node.appendChild(doc.createTextNode(String.valueOf(mUiOrder)));
        root.appendChild(node);

        node =  doc.createElement("ChargeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mChargeCd)));
        root.appendChild(node);

        node =  doc.createElement("Discount");
        node.appendChild(doc.createTextNode(String.valueOf(mDiscount)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the FreightTableCriteriaId field is not cloned.
    *
    * @return FreightTableCriteriaData object
    */
    public Object clone(){
        FreightTableCriteriaData myClone = new FreightTableCriteriaData();
        
        myClone.mFreightTableId = mFreightTableId;
        
        myClone.mLowerAmount = mLowerAmount;
        
        myClone.mHigherAmount = mHigherAmount;
        
        myClone.mFreightAmount = mFreightAmount;
        
        myClone.mHandlingAmount = mHandlingAmount;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        myClone.mFreightHandlerId = mFreightHandlerId;
        
        myClone.mFreightCriteriaTypeCd = mFreightCriteriaTypeCd;
        
        myClone.mRuntimeTypeCd = mRuntimeTypeCd;
        
        myClone.mShortDesc = mShortDesc;
        
        myClone.mUiOrder = mUiOrder;
        
        myClone.mChargeCd = mChargeCd;
        
        myClone.mDiscount = mDiscount;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (FreightTableCriteriaDataAccess.FREIGHT_TABLE_ID.equals(pFieldName)) {
            return getFreightTableId();
        } else if (FreightTableCriteriaDataAccess.FREIGHT_TABLE_CRITERIA_ID.equals(pFieldName)) {
            return getFreightTableCriteriaId();
        } else if (FreightTableCriteriaDataAccess.LOWER_AMOUNT.equals(pFieldName)) {
            return getLowerAmount();
        } else if (FreightTableCriteriaDataAccess.HIGHER_AMOUNT.equals(pFieldName)) {
            return getHigherAmount();
        } else if (FreightTableCriteriaDataAccess.FREIGHT_AMOUNT.equals(pFieldName)) {
            return getFreightAmount();
        } else if (FreightTableCriteriaDataAccess.HANDLING_AMOUNT.equals(pFieldName)) {
            return getHandlingAmount();
        } else if (FreightTableCriteriaDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (FreightTableCriteriaDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (FreightTableCriteriaDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (FreightTableCriteriaDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (FreightTableCriteriaDataAccess.FREIGHT_HANDLER_ID.equals(pFieldName)) {
            return getFreightHandlerId();
        } else if (FreightTableCriteriaDataAccess.FREIGHT_CRITERIA_TYPE_CD.equals(pFieldName)) {
            return getFreightCriteriaTypeCd();
        } else if (FreightTableCriteriaDataAccess.RUNTIME_TYPE_CD.equals(pFieldName)) {
            return getRuntimeTypeCd();
        } else if (FreightTableCriteriaDataAccess.SHORT_DESC.equals(pFieldName)) {
            return getShortDesc();
        } else if (FreightTableCriteriaDataAccess.UI_ORDER.equals(pFieldName)) {
            return getUiOrder();
        } else if (FreightTableCriteriaDataAccess.CHARGE_CD.equals(pFieldName)) {
            return getChargeCd();
        } else if (FreightTableCriteriaDataAccess.DISCOUNT.equals(pFieldName)) {
            return getDiscount();
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
        return FreightTableCriteriaDataAccess.CLW_FREIGHT_TABLE_CRITERIA;
    }

    
    /**
     * Sets the FreightTableId field. This field is required to be set in the database.
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
     * Sets the FreightTableCriteriaId field. This field is required to be set in the database.
     *
     * @param pFreightTableCriteriaId
     *  int to use to update the field.
     */
    public void setFreightTableCriteriaId(int pFreightTableCriteriaId){
        this.mFreightTableCriteriaId = pFreightTableCriteriaId;
        setDirty(true);
    }
    /**
     * Retrieves the FreightTableCriteriaId field.
     *
     * @return
     *  int containing the FreightTableCriteriaId field.
     */
    public int getFreightTableCriteriaId(){
        return mFreightTableCriteriaId;
    }

    /**
     * Sets the LowerAmount field.
     *
     * @param pLowerAmount
     *  java.math.BigDecimal to use to update the field.
     */
    public void setLowerAmount(java.math.BigDecimal pLowerAmount){
        this.mLowerAmount = pLowerAmount;
        setDirty(true);
    }
    /**
     * Retrieves the LowerAmount field.
     *
     * @return
     *  java.math.BigDecimal containing the LowerAmount field.
     */
    public java.math.BigDecimal getLowerAmount(){
        return mLowerAmount;
    }

    /**
     * Sets the HigherAmount field.
     *
     * @param pHigherAmount
     *  java.math.BigDecimal to use to update the field.
     */
    public void setHigherAmount(java.math.BigDecimal pHigherAmount){
        this.mHigherAmount = pHigherAmount;
        setDirty(true);
    }
    /**
     * Retrieves the HigherAmount field.
     *
     * @return
     *  java.math.BigDecimal containing the HigherAmount field.
     */
    public java.math.BigDecimal getHigherAmount(){
        return mHigherAmount;
    }

    /**
     * Sets the FreightAmount field.
     *
     * @param pFreightAmount
     *  java.math.BigDecimal to use to update the field.
     */
    public void setFreightAmount(java.math.BigDecimal pFreightAmount){
        this.mFreightAmount = pFreightAmount;
        setDirty(true);
    }
    /**
     * Retrieves the FreightAmount field.
     *
     * @return
     *  java.math.BigDecimal containing the FreightAmount field.
     */
    public java.math.BigDecimal getFreightAmount(){
        return mFreightAmount;
    }

    /**
     * Sets the HandlingAmount field.
     *
     * @param pHandlingAmount
     *  java.math.BigDecimal to use to update the field.
     */
    public void setHandlingAmount(java.math.BigDecimal pHandlingAmount){
        this.mHandlingAmount = pHandlingAmount;
        setDirty(true);
    }
    /**
     * Retrieves the HandlingAmount field.
     *
     * @return
     *  java.math.BigDecimal containing the HandlingAmount field.
     */
    public java.math.BigDecimal getHandlingAmount(){
        return mHandlingAmount;
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
     * Sets the FreightHandlerId field.
     *
     * @param pFreightHandlerId
     *  int to use to update the field.
     */
    public void setFreightHandlerId(int pFreightHandlerId){
        this.mFreightHandlerId = pFreightHandlerId;
        setDirty(true);
    }
    /**
     * Retrieves the FreightHandlerId field.
     *
     * @return
     *  int containing the FreightHandlerId field.
     */
    public int getFreightHandlerId(){
        return mFreightHandlerId;
    }

    /**
     * Sets the FreightCriteriaTypeCd field.
     *
     * @param pFreightCriteriaTypeCd
     *  String to use to update the field.
     */
    public void setFreightCriteriaTypeCd(String pFreightCriteriaTypeCd){
        this.mFreightCriteriaTypeCd = pFreightCriteriaTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the FreightCriteriaTypeCd field.
     *
     * @return
     *  String containing the FreightCriteriaTypeCd field.
     */
    public String getFreightCriteriaTypeCd(){
        return mFreightCriteriaTypeCd;
    }

    /**
     * Sets the RuntimeTypeCd field.
     *
     * @param pRuntimeTypeCd
     *  String to use to update the field.
     */
    public void setRuntimeTypeCd(String pRuntimeTypeCd){
        this.mRuntimeTypeCd = pRuntimeTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the RuntimeTypeCd field.
     *
     * @return
     *  String containing the RuntimeTypeCd field.
     */
    public String getRuntimeTypeCd(){
        return mRuntimeTypeCd;
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
     * Sets the UiOrder field.
     *
     * @param pUiOrder
     *  int to use to update the field.
     */
    public void setUiOrder(int pUiOrder){
        this.mUiOrder = pUiOrder;
        setDirty(true);
    }
    /**
     * Retrieves the UiOrder field.
     *
     * @return
     *  int containing the UiOrder field.
     */
    public int getUiOrder(){
        return mUiOrder;
    }

    /**
     * Sets the ChargeCd field.
     *
     * @param pChargeCd
     *  String to use to update the field.
     */
    public void setChargeCd(String pChargeCd){
        this.mChargeCd = pChargeCd;
        setDirty(true);
    }
    /**
     * Retrieves the ChargeCd field.
     *
     * @return
     *  String containing the ChargeCd field.
     */
    public String getChargeCd(){
        return mChargeCd;
    }

    /**
     * Sets the Discount field.
     *
     * @param pDiscount
     *  java.math.BigDecimal to use to update the field.
     */
    public void setDiscount(java.math.BigDecimal pDiscount){
        this.mDiscount = pDiscount;
        setDirty(true);
    }
    /**
     * Retrieves the Discount field.
     *
     * @return
     *  java.math.BigDecimal containing the Discount field.
     */
    public java.math.BigDecimal getDiscount(){
        return mDiscount;
    }


}
