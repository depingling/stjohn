
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ProdApplData
 * Description:  This is a ValueObject class wrapping the database table CLW_PROD_APPL.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.ProdApplDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>ProdApplData</code> is a ValueObject class wrapping of the database table CLW_PROD_APPL.
 */
public class ProdApplData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -4464321728281385610L;
    private int mProdApplId;// SQL type:NUMBER, not null
    private int mItemId;// SQL type:NUMBER
    private int mCleaningProcId;// SQL type:NUMBER
    private String mEstimatorProductCd;// SQL type:VARCHAR2, not null
    private int mEstimatorFacilityId;// SQL type:NUMBER
    private int mCleaningSchedStructId;// SQL type:NUMBER
    private java.math.BigDecimal mUsageRate;// SQL type:NUMBER
    private String mUnitCdNumerator;// SQL type:VARCHAR2
    private String mUnitCdDenominator;// SQL type:VARCHAR2
    private java.math.BigDecimal mSharingPercent;// SQL type:NUMBER
    private int mFloorCareId;// SQL type:NUMBER
    private String mFloorTypeCd;// SQL type:VARCHAR2
    private java.math.BigDecimal mDilutionRate;// SQL type:NUMBER
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private java.math.BigDecimal mUiFilter;// SQL type:NUMBER
    private String mUnitCdDenominator1;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public ProdApplData ()
    {
        mEstimatorProductCd = "";
        mUnitCdNumerator = "";
        mUnitCdDenominator = "";
        mFloorTypeCd = "";
        mAddBy = "";
        mModBy = "";
        mUnitCdDenominator1 = "";
    }

    /**
     * Constructor.
     */
    public ProdApplData(int parm1, int parm2, int parm3, String parm4, int parm5, int parm6, java.math.BigDecimal parm7, String parm8, String parm9, java.math.BigDecimal parm10, int parm11, String parm12, java.math.BigDecimal parm13, Date parm14, String parm15, Date parm16, String parm17, java.math.BigDecimal parm18, String parm19)
    {
        mProdApplId = parm1;
        mItemId = parm2;
        mCleaningProcId = parm3;
        mEstimatorProductCd = parm4;
        mEstimatorFacilityId = parm5;
        mCleaningSchedStructId = parm6;
        mUsageRate = parm7;
        mUnitCdNumerator = parm8;
        mUnitCdDenominator = parm9;
        mSharingPercent = parm10;
        mFloorCareId = parm11;
        mFloorTypeCd = parm12;
        mDilutionRate = parm13;
        mAddDate = parm14;
        mAddBy = parm15;
        mModDate = parm16;
        mModBy = parm17;
        mUiFilter = parm18;
        mUnitCdDenominator1 = parm19;
        
    }

    /**
     * Creates a new ProdApplData
     *
     * @return
     *  Newly initialized ProdApplData object.
     */
    public static ProdApplData createValue ()
    {
        ProdApplData valueData = new ProdApplData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ProdApplData object
     */
    public String toString()
    {
        return "[" + "ProdApplId=" + mProdApplId + ", ItemId=" + mItemId + ", CleaningProcId=" + mCleaningProcId + ", EstimatorProductCd=" + mEstimatorProductCd + ", EstimatorFacilityId=" + mEstimatorFacilityId + ", CleaningSchedStructId=" + mCleaningSchedStructId + ", UsageRate=" + mUsageRate + ", UnitCdNumerator=" + mUnitCdNumerator + ", UnitCdDenominator=" + mUnitCdDenominator + ", SharingPercent=" + mSharingPercent + ", FloorCareId=" + mFloorCareId + ", FloorTypeCd=" + mFloorTypeCd + ", DilutionRate=" + mDilutionRate + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", UiFilter=" + mUiFilter + ", UnitCdDenominator1=" + mUnitCdDenominator1 + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("ProdAppl");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mProdApplId));

        node =  doc.createElement("ItemId");
        node.appendChild(doc.createTextNode(String.valueOf(mItemId)));
        root.appendChild(node);

        node =  doc.createElement("CleaningProcId");
        node.appendChild(doc.createTextNode(String.valueOf(mCleaningProcId)));
        root.appendChild(node);

        node =  doc.createElement("EstimatorProductCd");
        node.appendChild(doc.createTextNode(String.valueOf(mEstimatorProductCd)));
        root.appendChild(node);

        node =  doc.createElement("EstimatorFacilityId");
        node.appendChild(doc.createTextNode(String.valueOf(mEstimatorFacilityId)));
        root.appendChild(node);

        node =  doc.createElement("CleaningSchedStructId");
        node.appendChild(doc.createTextNode(String.valueOf(mCleaningSchedStructId)));
        root.appendChild(node);

        node =  doc.createElement("UsageRate");
        node.appendChild(doc.createTextNode(String.valueOf(mUsageRate)));
        root.appendChild(node);

        node =  doc.createElement("UnitCdNumerator");
        node.appendChild(doc.createTextNode(String.valueOf(mUnitCdNumerator)));
        root.appendChild(node);

        node =  doc.createElement("UnitCdDenominator");
        node.appendChild(doc.createTextNode(String.valueOf(mUnitCdDenominator)));
        root.appendChild(node);

        node =  doc.createElement("SharingPercent");
        node.appendChild(doc.createTextNode(String.valueOf(mSharingPercent)));
        root.appendChild(node);

        node =  doc.createElement("FloorCareId");
        node.appendChild(doc.createTextNode(String.valueOf(mFloorCareId)));
        root.appendChild(node);

        node =  doc.createElement("FloorTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mFloorTypeCd)));
        root.appendChild(node);

        node =  doc.createElement("DilutionRate");
        node.appendChild(doc.createTextNode(String.valueOf(mDilutionRate)));
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

        node =  doc.createElement("UiFilter");
        node.appendChild(doc.createTextNode(String.valueOf(mUiFilter)));
        root.appendChild(node);

        node =  doc.createElement("UnitCdDenominator1");
        node.appendChild(doc.createTextNode(String.valueOf(mUnitCdDenominator1)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the ProdApplId field is not cloned.
    *
    * @return ProdApplData object
    */
    public Object clone(){
        ProdApplData myClone = new ProdApplData();
        
        myClone.mItemId = mItemId;
        
        myClone.mCleaningProcId = mCleaningProcId;
        
        myClone.mEstimatorProductCd = mEstimatorProductCd;
        
        myClone.mEstimatorFacilityId = mEstimatorFacilityId;
        
        myClone.mCleaningSchedStructId = mCleaningSchedStructId;
        
        myClone.mUsageRate = mUsageRate;
        
        myClone.mUnitCdNumerator = mUnitCdNumerator;
        
        myClone.mUnitCdDenominator = mUnitCdDenominator;
        
        myClone.mSharingPercent = mSharingPercent;
        
        myClone.mFloorCareId = mFloorCareId;
        
        myClone.mFloorTypeCd = mFloorTypeCd;
        
        myClone.mDilutionRate = mDilutionRate;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        myClone.mUiFilter = mUiFilter;
        
        myClone.mUnitCdDenominator1 = mUnitCdDenominator1;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (ProdApplDataAccess.PROD_APPL_ID.equals(pFieldName)) {
            return getProdApplId();
        } else if (ProdApplDataAccess.ITEM_ID.equals(pFieldName)) {
            return getItemId();
        } else if (ProdApplDataAccess.CLEANING_PROC_ID.equals(pFieldName)) {
            return getCleaningProcId();
        } else if (ProdApplDataAccess.ESTIMATOR_PRODUCT_CD.equals(pFieldName)) {
            return getEstimatorProductCd();
        } else if (ProdApplDataAccess.ESTIMATOR_FACILITY_ID.equals(pFieldName)) {
            return getEstimatorFacilityId();
        } else if (ProdApplDataAccess.CLEANING_SCHED_STRUCT_ID.equals(pFieldName)) {
            return getCleaningSchedStructId();
        } else if (ProdApplDataAccess.USAGE_RATE.equals(pFieldName)) {
            return getUsageRate();
        } else if (ProdApplDataAccess.UNIT_CD_NUMERATOR.equals(pFieldName)) {
            return getUnitCdNumerator();
        } else if (ProdApplDataAccess.UNIT_CD_DENOMINATOR.equals(pFieldName)) {
            return getUnitCdDenominator();
        } else if (ProdApplDataAccess.SHARING_PERCENT.equals(pFieldName)) {
            return getSharingPercent();
        } else if (ProdApplDataAccess.FLOOR_CARE_ID.equals(pFieldName)) {
            return getFloorCareId();
        } else if (ProdApplDataAccess.FLOOR_TYPE_CD.equals(pFieldName)) {
            return getFloorTypeCd();
        } else if (ProdApplDataAccess.DILUTION_RATE.equals(pFieldName)) {
            return getDilutionRate();
        } else if (ProdApplDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (ProdApplDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (ProdApplDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (ProdApplDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (ProdApplDataAccess.UI_FILTER.equals(pFieldName)) {
            return getUiFilter();
        } else if (ProdApplDataAccess.UNIT_CD_DENOMINATOR1.equals(pFieldName)) {
            return getUnitCdDenominator1();
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
        return ProdApplDataAccess.CLW_PROD_APPL;
    }

    
    /**
     * Sets the ProdApplId field. This field is required to be set in the database.
     *
     * @param pProdApplId
     *  int to use to update the field.
     */
    public void setProdApplId(int pProdApplId){
        this.mProdApplId = pProdApplId;
        setDirty(true);
    }
    /**
     * Retrieves the ProdApplId field.
     *
     * @return
     *  int containing the ProdApplId field.
     */
    public int getProdApplId(){
        return mProdApplId;
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
     * Sets the CleaningProcId field.
     *
     * @param pCleaningProcId
     *  int to use to update the field.
     */
    public void setCleaningProcId(int pCleaningProcId){
        this.mCleaningProcId = pCleaningProcId;
        setDirty(true);
    }
    /**
     * Retrieves the CleaningProcId field.
     *
     * @return
     *  int containing the CleaningProcId field.
     */
    public int getCleaningProcId(){
        return mCleaningProcId;
    }

    /**
     * Sets the EstimatorProductCd field. This field is required to be set in the database.
     *
     * @param pEstimatorProductCd
     *  String to use to update the field.
     */
    public void setEstimatorProductCd(String pEstimatorProductCd){
        this.mEstimatorProductCd = pEstimatorProductCd;
        setDirty(true);
    }
    /**
     * Retrieves the EstimatorProductCd field.
     *
     * @return
     *  String containing the EstimatorProductCd field.
     */
    public String getEstimatorProductCd(){
        return mEstimatorProductCd;
    }

    /**
     * Sets the EstimatorFacilityId field.
     *
     * @param pEstimatorFacilityId
     *  int to use to update the field.
     */
    public void setEstimatorFacilityId(int pEstimatorFacilityId){
        this.mEstimatorFacilityId = pEstimatorFacilityId;
        setDirty(true);
    }
    /**
     * Retrieves the EstimatorFacilityId field.
     *
     * @return
     *  int containing the EstimatorFacilityId field.
     */
    public int getEstimatorFacilityId(){
        return mEstimatorFacilityId;
    }

    /**
     * Sets the CleaningSchedStructId field.
     *
     * @param pCleaningSchedStructId
     *  int to use to update the field.
     */
    public void setCleaningSchedStructId(int pCleaningSchedStructId){
        this.mCleaningSchedStructId = pCleaningSchedStructId;
        setDirty(true);
    }
    /**
     * Retrieves the CleaningSchedStructId field.
     *
     * @return
     *  int containing the CleaningSchedStructId field.
     */
    public int getCleaningSchedStructId(){
        return mCleaningSchedStructId;
    }

    /**
     * Sets the UsageRate field.
     *
     * @param pUsageRate
     *  java.math.BigDecimal to use to update the field.
     */
    public void setUsageRate(java.math.BigDecimal pUsageRate){
        this.mUsageRate = pUsageRate;
        setDirty(true);
    }
    /**
     * Retrieves the UsageRate field.
     *
     * @return
     *  java.math.BigDecimal containing the UsageRate field.
     */
    public java.math.BigDecimal getUsageRate(){
        return mUsageRate;
    }

    /**
     * Sets the UnitCdNumerator field.
     *
     * @param pUnitCdNumerator
     *  String to use to update the field.
     */
    public void setUnitCdNumerator(String pUnitCdNumerator){
        this.mUnitCdNumerator = pUnitCdNumerator;
        setDirty(true);
    }
    /**
     * Retrieves the UnitCdNumerator field.
     *
     * @return
     *  String containing the UnitCdNumerator field.
     */
    public String getUnitCdNumerator(){
        return mUnitCdNumerator;
    }

    /**
     * Sets the UnitCdDenominator field.
     *
     * @param pUnitCdDenominator
     *  String to use to update the field.
     */
    public void setUnitCdDenominator(String pUnitCdDenominator){
        this.mUnitCdDenominator = pUnitCdDenominator;
        setDirty(true);
    }
    /**
     * Retrieves the UnitCdDenominator field.
     *
     * @return
     *  String containing the UnitCdDenominator field.
     */
    public String getUnitCdDenominator(){
        return mUnitCdDenominator;
    }

    /**
     * Sets the SharingPercent field.
     *
     * @param pSharingPercent
     *  java.math.BigDecimal to use to update the field.
     */
    public void setSharingPercent(java.math.BigDecimal pSharingPercent){
        this.mSharingPercent = pSharingPercent;
        setDirty(true);
    }
    /**
     * Retrieves the SharingPercent field.
     *
     * @return
     *  java.math.BigDecimal containing the SharingPercent field.
     */
    public java.math.BigDecimal getSharingPercent(){
        return mSharingPercent;
    }

    /**
     * Sets the FloorCareId field.
     *
     * @param pFloorCareId
     *  int to use to update the field.
     */
    public void setFloorCareId(int pFloorCareId){
        this.mFloorCareId = pFloorCareId;
        setDirty(true);
    }
    /**
     * Retrieves the FloorCareId field.
     *
     * @return
     *  int containing the FloorCareId field.
     */
    public int getFloorCareId(){
        return mFloorCareId;
    }

    /**
     * Sets the FloorTypeCd field.
     *
     * @param pFloorTypeCd
     *  String to use to update the field.
     */
    public void setFloorTypeCd(String pFloorTypeCd){
        this.mFloorTypeCd = pFloorTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the FloorTypeCd field.
     *
     * @return
     *  String containing the FloorTypeCd field.
     */
    public String getFloorTypeCd(){
        return mFloorTypeCd;
    }

    /**
     * Sets the DilutionRate field.
     *
     * @param pDilutionRate
     *  java.math.BigDecimal to use to update the field.
     */
    public void setDilutionRate(java.math.BigDecimal pDilutionRate){
        this.mDilutionRate = pDilutionRate;
        setDirty(true);
    }
    /**
     * Retrieves the DilutionRate field.
     *
     * @return
     *  java.math.BigDecimal containing the DilutionRate field.
     */
    public java.math.BigDecimal getDilutionRate(){
        return mDilutionRate;
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
     * Sets the UiFilter field.
     *
     * @param pUiFilter
     *  java.math.BigDecimal to use to update the field.
     */
    public void setUiFilter(java.math.BigDecimal pUiFilter){
        this.mUiFilter = pUiFilter;
        setDirty(true);
    }
    /**
     * Retrieves the UiFilter field.
     *
     * @return
     *  java.math.BigDecimal containing the UiFilter field.
     */
    public java.math.BigDecimal getUiFilter(){
        return mUiFilter;
    }

    /**
     * Sets the UnitCdDenominator1 field.
     *
     * @param pUnitCdDenominator1
     *  String to use to update the field.
     */
    public void setUnitCdDenominator1(String pUnitCdDenominator1){
        this.mUnitCdDenominator1 = pUnitCdDenominator1;
        setDirty(true);
    }
    /**
     * Retrieves the UnitCdDenominator1 field.
     *
     * @return
     *  String containing the UnitCdDenominator1 field.
     */
    public String getUnitCdDenominator1(){
        return mUnitCdDenominator1;
    }


}
