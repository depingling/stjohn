
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        CatalogStructureData
 * Description:  This is a ValueObject class wrapping the database table CLW_CATALOG_STRUCTURE.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.CatalogStructureDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>CatalogStructureData</code> is a ValueObject class wrapping of the database table CLW_CATALOG_STRUCTURE.
 */
public class CatalogStructureData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 2661433747826098296L;
    private int mCatalogStructureId;// SQL type:NUMBER, not null
    private int mCatalogId;// SQL type:NUMBER, not null
    private int mBusEntityId;// SQL type:NUMBER
    private String mCatalogStructureCd;// SQL type:VARCHAR2, not null
    private int mItemId;// SQL type:NUMBER, not null
    private String mCustomerSkuNum;// SQL type:VARCHAR2
    private String mShortDesc;// SQL type:VARCHAR2
    private Date mEffDate;// SQL type:DATE
    private Date mExpDate;// SQL type:DATE
    private String mStatusCd;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private int mCostCenterId;// SQL type:NUMBER
    private String mTaxExempt;// SQL type:VARCHAR2
    private int mItemGroupId;// SQL type:NUMBER
    private String mSpecialPermission;// SQL type:VARCHAR2
    private int mSortOrder;// SQL type:NUMBER

    /**
     * Constructor.
     */
    public CatalogStructureData ()
    {
        mCatalogStructureCd = "";
        mCustomerSkuNum = "";
        mShortDesc = "";
        mStatusCd = "";
        mAddBy = "";
        mModBy = "";
        mTaxExempt = "";
        mSpecialPermission = "";
    }

    /**
     * Constructor.
     */
    public CatalogStructureData(int parm1, int parm2, int parm3, String parm4, int parm5, String parm6, String parm7, Date parm8, Date parm9, String parm10, Date parm11, String parm12, Date parm13, String parm14, int parm15, String parm16, int parm17, String parm18, int parm19)
    {
        mCatalogStructureId = parm1;
        mCatalogId = parm2;
        mBusEntityId = parm3;
        mCatalogStructureCd = parm4;
        mItemId = parm5;
        mCustomerSkuNum = parm6;
        mShortDesc = parm7;
        mEffDate = parm8;
        mExpDate = parm9;
        mStatusCd = parm10;
        mAddDate = parm11;
        mAddBy = parm12;
        mModDate = parm13;
        mModBy = parm14;
        mCostCenterId = parm15;
        mTaxExempt = parm16;
        mItemGroupId = parm17;
        mSpecialPermission = parm18;
        mSortOrder = parm19;
        
    }

    /**
     * Creates a new CatalogStructureData
     *
     * @return
     *  Newly initialized CatalogStructureData object.
     */
    public static CatalogStructureData createValue ()
    {
        CatalogStructureData valueData = new CatalogStructureData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this CatalogStructureData object
     */
    public String toString()
    {
        return "[" + "CatalogStructureId=" + mCatalogStructureId + ", CatalogId=" + mCatalogId + ", BusEntityId=" + mBusEntityId + ", CatalogStructureCd=" + mCatalogStructureCd + ", ItemId=" + mItemId + ", CustomerSkuNum=" + mCustomerSkuNum + ", ShortDesc=" + mShortDesc + ", EffDate=" + mEffDate + ", ExpDate=" + mExpDate + ", StatusCd=" + mStatusCd + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", CostCenterId=" + mCostCenterId + ", TaxExempt=" + mTaxExempt + ", ItemGroupId=" + mItemGroupId + ", SpecialPermission=" + mSpecialPermission + ", SortOrder=" + mSortOrder + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("CatalogStructure");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mCatalogStructureId));

        node =  doc.createElement("CatalogId");
        node.appendChild(doc.createTextNode(String.valueOf(mCatalogId)));
        root.appendChild(node);

        node =  doc.createElement("BusEntityId");
        node.appendChild(doc.createTextNode(String.valueOf(mBusEntityId)));
        root.appendChild(node);

        node =  doc.createElement("CatalogStructureCd");
        node.appendChild(doc.createTextNode(String.valueOf(mCatalogStructureCd)));
        root.appendChild(node);

        node =  doc.createElement("ItemId");
        node.appendChild(doc.createTextNode(String.valueOf(mItemId)));
        root.appendChild(node);

        node =  doc.createElement("CustomerSkuNum");
        node.appendChild(doc.createTextNode(String.valueOf(mCustomerSkuNum)));
        root.appendChild(node);

        node =  doc.createElement("ShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mShortDesc)));
        root.appendChild(node);

        node =  doc.createElement("EffDate");
        node.appendChild(doc.createTextNode(String.valueOf(mEffDate)));
        root.appendChild(node);

        node =  doc.createElement("ExpDate");
        node.appendChild(doc.createTextNode(String.valueOf(mExpDate)));
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

        node =  doc.createElement("CostCenterId");
        node.appendChild(doc.createTextNode(String.valueOf(mCostCenterId)));
        root.appendChild(node);

        node =  doc.createElement("TaxExempt");
        node.appendChild(doc.createTextNode(String.valueOf(mTaxExempt)));
        root.appendChild(node);

        node =  doc.createElement("ItemGroupId");
        node.appendChild(doc.createTextNode(String.valueOf(mItemGroupId)));
        root.appendChild(node);

        node =  doc.createElement("SpecialPermission");
        node.appendChild(doc.createTextNode(String.valueOf(mSpecialPermission)));
        root.appendChild(node);

        node =  doc.createElement("SortOrder");
        node.appendChild(doc.createTextNode(String.valueOf(mSortOrder)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the CatalogStructureId field is not cloned.
    *
    * @return CatalogStructureData object
    */
    public Object clone(){
        CatalogStructureData myClone = new CatalogStructureData();
        
        myClone.mCatalogId = mCatalogId;
        
        myClone.mBusEntityId = mBusEntityId;
        
        myClone.mCatalogStructureCd = mCatalogStructureCd;
        
        myClone.mItemId = mItemId;
        
        myClone.mCustomerSkuNum = mCustomerSkuNum;
        
        myClone.mShortDesc = mShortDesc;
        
        if(mEffDate != null){
                myClone.mEffDate = (Date) mEffDate.clone();
        }
        
        if(mExpDate != null){
                myClone.mExpDate = (Date) mExpDate.clone();
        }
        
        myClone.mStatusCd = mStatusCd;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        myClone.mCostCenterId = mCostCenterId;
        
        myClone.mTaxExempt = mTaxExempt;
        
        myClone.mItemGroupId = mItemGroupId;
        
        myClone.mSpecialPermission = mSpecialPermission;
        
        myClone.mSortOrder = mSortOrder;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (CatalogStructureDataAccess.CATALOG_STRUCTURE_ID.equals(pFieldName)) {
            return getCatalogStructureId();
        } else if (CatalogStructureDataAccess.CATALOG_ID.equals(pFieldName)) {
            return getCatalogId();
        } else if (CatalogStructureDataAccess.BUS_ENTITY_ID.equals(pFieldName)) {
            return getBusEntityId();
        } else if (CatalogStructureDataAccess.CATALOG_STRUCTURE_CD.equals(pFieldName)) {
            return getCatalogStructureCd();
        } else if (CatalogStructureDataAccess.ITEM_ID.equals(pFieldName)) {
            return getItemId();
        } else if (CatalogStructureDataAccess.CUSTOMER_SKU_NUM.equals(pFieldName)) {
            return getCustomerSkuNum();
        } else if (CatalogStructureDataAccess.SHORT_DESC.equals(pFieldName)) {
            return getShortDesc();
        } else if (CatalogStructureDataAccess.EFF_DATE.equals(pFieldName)) {
            return getEffDate();
        } else if (CatalogStructureDataAccess.EXP_DATE.equals(pFieldName)) {
            return getExpDate();
        } else if (CatalogStructureDataAccess.STATUS_CD.equals(pFieldName)) {
            return getStatusCd();
        } else if (CatalogStructureDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (CatalogStructureDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (CatalogStructureDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (CatalogStructureDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (CatalogStructureDataAccess.COST_CENTER_ID.equals(pFieldName)) {
            return getCostCenterId();
        } else if (CatalogStructureDataAccess.TAX_EXEMPT.equals(pFieldName)) {
            return getTaxExempt();
        } else if (CatalogStructureDataAccess.ITEM_GROUP_ID.equals(pFieldName)) {
            return getItemGroupId();
        } else if (CatalogStructureDataAccess.SPECIAL_PERMISSION.equals(pFieldName)) {
            return getSpecialPermission();
        } else if (CatalogStructureDataAccess.SORT_ORDER.equals(pFieldName)) {
            return getSortOrder();
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
        return CatalogStructureDataAccess.CLW_CATALOG_STRUCTURE;
    }

    
    /**
     * Sets the CatalogStructureId field. This field is required to be set in the database.
     *
     * @param pCatalogStructureId
     *  int to use to update the field.
     */
    public void setCatalogStructureId(int pCatalogStructureId){
        this.mCatalogStructureId = pCatalogStructureId;
        setDirty(true);
    }
    /**
     * Retrieves the CatalogStructureId field.
     *
     * @return
     *  int containing the CatalogStructureId field.
     */
    public int getCatalogStructureId(){
        return mCatalogStructureId;
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
     * Sets the CatalogStructureCd field. This field is required to be set in the database.
     *
     * @param pCatalogStructureCd
     *  String to use to update the field.
     */
    public void setCatalogStructureCd(String pCatalogStructureCd){
        this.mCatalogStructureCd = pCatalogStructureCd;
        setDirty(true);
    }
    /**
     * Retrieves the CatalogStructureCd field.
     *
     * @return
     *  String containing the CatalogStructureCd field.
     */
    public String getCatalogStructureCd(){
        return mCatalogStructureCd;
    }

    /**
     * Sets the ItemId field. This field is required to be set in the database.
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
     * Sets the CustomerSkuNum field.
     *
     * @param pCustomerSkuNum
     *  String to use to update the field.
     */
    public void setCustomerSkuNum(String pCustomerSkuNum){
        this.mCustomerSkuNum = pCustomerSkuNum;
        setDirty(true);
    }
    /**
     * Retrieves the CustomerSkuNum field.
     *
     * @return
     *  String containing the CustomerSkuNum field.
     */
    public String getCustomerSkuNum(){
        return mCustomerSkuNum;
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
     * Sets the CostCenterId field.
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
     * Sets the TaxExempt field.
     *
     * @param pTaxExempt
     *  String to use to update the field.
     */
    public void setTaxExempt(String pTaxExempt){
        this.mTaxExempt = pTaxExempt;
        setDirty(true);
    }
    /**
     * Retrieves the TaxExempt field.
     *
     * @return
     *  String containing the TaxExempt field.
     */
    public String getTaxExempt(){
        return mTaxExempt;
    }

    /**
     * Sets the ItemGroupId field.
     *
     * @param pItemGroupId
     *  int to use to update the field.
     */
    public void setItemGroupId(int pItemGroupId){
        this.mItemGroupId = pItemGroupId;
        setDirty(true);
    }
    /**
     * Retrieves the ItemGroupId field.
     *
     * @return
     *  int containing the ItemGroupId field.
     */
    public int getItemGroupId(){
        return mItemGroupId;
    }

    /**
     * Sets the SpecialPermission field.
     *
     * @param pSpecialPermission
     *  String to use to update the field.
     */
    public void setSpecialPermission(String pSpecialPermission){
        this.mSpecialPermission = pSpecialPermission;
        setDirty(true);
    }
    /**
     * Retrieves the SpecialPermission field.
     *
     * @return
     *  String containing the SpecialPermission field.
     */
    public String getSpecialPermission(){
        return mSpecialPermission;
    }

    /**
     * Sets the SortOrder field.
     *
     * @param pSortOrder
     *  int to use to update the field.
     */
    public void setSortOrder(int pSortOrder){
        this.mSortOrder = pSortOrder;
        setDirty(true);
    }
    /**
     * Retrieves the SortOrder field.
     *
     * @return
     *  int containing the SortOrder field.
     */
    public int getSortOrder(){
        return mSortOrder;
    }


}
