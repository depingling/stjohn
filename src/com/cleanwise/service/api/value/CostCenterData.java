
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        CostCenterData
 * Description:  This is a ValueObject class wrapping the database table CLW_COST_CENTER.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.CostCenterDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>CostCenterData</code> is a ValueObject class wrapping of the database table CLW_COST_CENTER.
 */
public class CostCenterData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 5375487497675175807L;
    private int mCostCenterId;// SQL type:NUMBER, not null
    private String mShortDesc;// SQL type:VARCHAR2, not null
    private String mCostCenterStatusCd;// SQL type:VARCHAR2, not null
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private String mCostCenterTypeCd;// SQL type:VARCHAR2
    private String mAllocateFreight;// SQL type:VARCHAR2
    private String mAllocateDiscount;// SQL type:VARCHAR2
    private String mCostCenterTaxType;// SQL type:VARCHAR2
    private int mStoreId;// SQL type:NUMBER
    private String mCostCenterCode;// SQL type:VARCHAR2
    private String mNoBudget;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public CostCenterData ()
    {
        mShortDesc = "";
        mCostCenterStatusCd = "";
        mAddBy = "";
        mModBy = "";
        mCostCenterTypeCd = "";
        mAllocateFreight = "";
        mAllocateDiscount = "";
        mCostCenterTaxType = "";
        mCostCenterCode = "";
        mNoBudget = "";
    }

    /**
     * Constructor.
     */
    public CostCenterData(int parm1, String parm2, String parm3, Date parm4, String parm5, Date parm6, String parm7, String parm8, String parm9, String parm10, String parm11, int parm12, String parm13, String parm14)
    {
        mCostCenterId = parm1;
        mShortDesc = parm2;
        mCostCenterStatusCd = parm3;
        mAddDate = parm4;
        mAddBy = parm5;
        mModDate = parm6;
        mModBy = parm7;
        mCostCenterTypeCd = parm8;
        mAllocateFreight = parm9;
        mAllocateDiscount = parm10;
        mCostCenterTaxType = parm11;
        mStoreId = parm12;
        mCostCenterCode = parm13;
        mNoBudget = parm14;
        
    }

    /**
     * Creates a new CostCenterData
     *
     * @return
     *  Newly initialized CostCenterData object.
     */
    public static CostCenterData createValue ()
    {
        CostCenterData valueData = new CostCenterData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this CostCenterData object
     */
    public String toString()
    {
        return "[" + "CostCenterId=" + mCostCenterId + ", ShortDesc=" + mShortDesc + ", CostCenterStatusCd=" + mCostCenterStatusCd + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", CostCenterTypeCd=" + mCostCenterTypeCd + ", AllocateFreight=" + mAllocateFreight + ", AllocateDiscount=" + mAllocateDiscount + ", CostCenterTaxType=" + mCostCenterTaxType + ", StoreId=" + mStoreId + ", CostCenterCode=" + mCostCenterCode + ", NoBudget=" + mNoBudget + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("CostCenter");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mCostCenterId));

        node =  doc.createElement("ShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mShortDesc)));
        root.appendChild(node);

        node =  doc.createElement("CostCenterStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mCostCenterStatusCd)));
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

        node =  doc.createElement("CostCenterTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mCostCenterTypeCd)));
        root.appendChild(node);

        node =  doc.createElement("AllocateFreight");
        node.appendChild(doc.createTextNode(String.valueOf(mAllocateFreight)));
        root.appendChild(node);

        node =  doc.createElement("AllocateDiscount");
        node.appendChild(doc.createTextNode(String.valueOf(mAllocateDiscount)));
        root.appendChild(node);

        node =  doc.createElement("CostCenterTaxType");
        node.appendChild(doc.createTextNode(String.valueOf(mCostCenterTaxType)));
        root.appendChild(node);

        node =  doc.createElement("StoreId");
        node.appendChild(doc.createTextNode(String.valueOf(mStoreId)));
        root.appendChild(node);

        node =  doc.createElement("CostCenterCode");
        node.appendChild(doc.createTextNode(String.valueOf(mCostCenterCode)));
        root.appendChild(node);

        node =  doc.createElement("NoBudget");
        node.appendChild(doc.createTextNode(String.valueOf(mNoBudget)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the CostCenterId field is not cloned.
    *
    * @return CostCenterData object
    */
    public Object clone(){
        CostCenterData myClone = new CostCenterData();
        
        myClone.mShortDesc = mShortDesc;
        
        myClone.mCostCenterStatusCd = mCostCenterStatusCd;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        myClone.mCostCenterTypeCd = mCostCenterTypeCd;
        
        myClone.mAllocateFreight = mAllocateFreight;
        
        myClone.mAllocateDiscount = mAllocateDiscount;
        
        myClone.mCostCenterTaxType = mCostCenterTaxType;
        
        myClone.mStoreId = mStoreId;
        
        myClone.mCostCenterCode = mCostCenterCode;
        
        myClone.mNoBudget = mNoBudget;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (CostCenterDataAccess.COST_CENTER_ID.equals(pFieldName)) {
            return getCostCenterId();
        } else if (CostCenterDataAccess.SHORT_DESC.equals(pFieldName)) {
            return getShortDesc();
        } else if (CostCenterDataAccess.COST_CENTER_STATUS_CD.equals(pFieldName)) {
            return getCostCenterStatusCd();
        } else if (CostCenterDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (CostCenterDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (CostCenterDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (CostCenterDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (CostCenterDataAccess.COST_CENTER_TYPE_CD.equals(pFieldName)) {
            return getCostCenterTypeCd();
        } else if (CostCenterDataAccess.ALLOCATE_FREIGHT.equals(pFieldName)) {
            return getAllocateFreight();
        } else if (CostCenterDataAccess.ALLOCATE_DISCOUNT.equals(pFieldName)) {
            return getAllocateDiscount();
        } else if (CostCenterDataAccess.COST_CENTER_TAX_TYPE.equals(pFieldName)) {
            return getCostCenterTaxType();
        } else if (CostCenterDataAccess.STORE_ID.equals(pFieldName)) {
            return getStoreId();
        } else if (CostCenterDataAccess.COST_CENTER_CODE.equals(pFieldName)) {
            return getCostCenterCode();
        } else if (CostCenterDataAccess.NO_BUDGET.equals(pFieldName)) {
            return getNoBudget();
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
        return CostCenterDataAccess.CLW_COST_CENTER;
    }

    
    /**
     * Sets the CostCenterId field. This field is required to be set in the database.
     *
     * @param pCostCenterId
     *  int to use to update the field.
     */
    public void setCostCenterId(int pCostCenterId){
        this.mCostCenterId = pCostCenterId;
        setDirty(true);
    }
    /**
     * Retrieves the CostCenterId field.
     *
     * @return
     *  int containing the CostCenterId field.
     */
    public int getCostCenterId(){
        return mCostCenterId;
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
     * Sets the CostCenterStatusCd field. This field is required to be set in the database.
     *
     * @param pCostCenterStatusCd
     *  String to use to update the field.
     */
    public void setCostCenterStatusCd(String pCostCenterStatusCd){
        this.mCostCenterStatusCd = pCostCenterStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the CostCenterStatusCd field.
     *
     * @return
     *  String containing the CostCenterStatusCd field.
     */
    public String getCostCenterStatusCd(){
        return mCostCenterStatusCd;
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
     * Sets the CostCenterTypeCd field.
     *
     * @param pCostCenterTypeCd
     *  String to use to update the field.
     */
    public void setCostCenterTypeCd(String pCostCenterTypeCd){
        this.mCostCenterTypeCd = pCostCenterTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the CostCenterTypeCd field.
     *
     * @return
     *  String containing the CostCenterTypeCd field.
     */
    public String getCostCenterTypeCd(){
        return mCostCenterTypeCd;
    }

    /**
     * Sets the AllocateFreight field.
     *
     * @param pAllocateFreight
     *  String to use to update the field.
     */
    public void setAllocateFreight(String pAllocateFreight){
        this.mAllocateFreight = pAllocateFreight;
        setDirty(true);
    }
    /**
     * Retrieves the AllocateFreight field.
     *
     * @return
     *  String containing the AllocateFreight field.
     */
    public String getAllocateFreight(){
        return mAllocateFreight;
    }

    /**
     * Sets the AllocateDiscount field.
     *
     * @param pAllocateDiscount
     *  String to use to update the field.
     */
    public void setAllocateDiscount(String pAllocateDiscount){
        this.mAllocateDiscount = pAllocateDiscount;
        setDirty(true);
    }
    /**
     * Retrieves the AllocateDiscount field.
     *
     * @return
     *  String containing the AllocateDiscount field.
     */
    public String getAllocateDiscount(){
        return mAllocateDiscount;
    }

    /**
     * Sets the CostCenterTaxType field.
     *
     * @param pCostCenterTaxType
     *  String to use to update the field.
     */
    public void setCostCenterTaxType(String pCostCenterTaxType){
        this.mCostCenterTaxType = pCostCenterTaxType;
        setDirty(true);
    }
    /**
     * Retrieves the CostCenterTaxType field.
     *
     * @return
     *  String containing the CostCenterTaxType field.
     */
    public String getCostCenterTaxType(){
        return mCostCenterTaxType;
    }

    /**
     * Sets the StoreId field.
     *
     * @param pStoreId
     *  int to use to update the field.
     */
    public void setStoreId(int pStoreId){
        this.mStoreId = pStoreId;
        setDirty(true);
    }
    /**
     * Retrieves the StoreId field.
     *
     * @return
     *  int containing the StoreId field.
     */
    public int getStoreId(){
        return mStoreId;
    }

    /**
     * Sets the CostCenterCode field.
     *
     * @param pCostCenterCode
     *  String to use to update the field.
     */
    public void setCostCenterCode(String pCostCenterCode){
        this.mCostCenterCode = pCostCenterCode;
        setDirty(true);
    }
    /**
     * Retrieves the CostCenterCode field.
     *
     * @return
     *  String containing the CostCenterCode field.
     */
    public String getCostCenterCode(){
        return mCostCenterCode;
    }

    /**
     * Sets the NoBudget field.
     *
     * @param pNoBudget
     *  String to use to update the field.
     */
    public void setNoBudget(String pNoBudget){
        this.mNoBudget = pNoBudget;
        setDirty(true);
    }
    /**
     * Retrieves the NoBudget field.
     *
     * @return
     *  String containing the NoBudget field.
     */
    public String getNoBudget(){
        return mNoBudget;
    }


}
