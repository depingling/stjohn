
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ProductUomPackData
 * Description:  This is a ValueObject class wrapping the database table CLW_PRODUCT_UOM_PACK.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.ProductUomPackDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>ProductUomPackData</code> is a ValueObject class wrapping of the database table CLW_PRODUCT_UOM_PACK.
 */
public class ProductUomPackData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 186812340952173096L;
    private int mProductUomPackId;// SQL type:NUMBER, not null
    private int mItemId;// SQL type:NUMBER
    private String mEstimatorProductCd;// SQL type:VARCHAR2
    private java.math.BigDecimal mUnitSize;// SQL type:NUMBER
    private String mUnitCd;// SQL type:VARCHAR2
    private int mPackQty;// SQL type:NUMBER
    private String mChemicalUsageModelCd;// SQL type:VARCHAR2
    private String mLinerSizeCd;// SQL type:VARCHAR2
    private String mAppearanceStandardCd;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public ProductUomPackData ()
    {
        mEstimatorProductCd = "";
        mUnitCd = "";
        mChemicalUsageModelCd = "";
        mLinerSizeCd = "";
        mAppearanceStandardCd = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public ProductUomPackData(int parm1, int parm2, String parm3, java.math.BigDecimal parm4, String parm5, int parm6, String parm7, String parm8, String parm9, Date parm10, String parm11, Date parm12, String parm13)
    {
        mProductUomPackId = parm1;
        mItemId = parm2;
        mEstimatorProductCd = parm3;
        mUnitSize = parm4;
        mUnitCd = parm5;
        mPackQty = parm6;
        mChemicalUsageModelCd = parm7;
        mLinerSizeCd = parm8;
        mAppearanceStandardCd = parm9;
        mAddDate = parm10;
        mAddBy = parm11;
        mModDate = parm12;
        mModBy = parm13;
        
    }

    /**
     * Creates a new ProductUomPackData
     *
     * @return
     *  Newly initialized ProductUomPackData object.
     */
    public static ProductUomPackData createValue ()
    {
        ProductUomPackData valueData = new ProductUomPackData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ProductUomPackData object
     */
    public String toString()
    {
        return "[" + "ProductUomPackId=" + mProductUomPackId + ", ItemId=" + mItemId + ", EstimatorProductCd=" + mEstimatorProductCd + ", UnitSize=" + mUnitSize + ", UnitCd=" + mUnitCd + ", PackQty=" + mPackQty + ", ChemicalUsageModelCd=" + mChemicalUsageModelCd + ", LinerSizeCd=" + mLinerSizeCd + ", AppearanceStandardCd=" + mAppearanceStandardCd + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("ProductUomPack");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mProductUomPackId));

        node =  doc.createElement("ItemId");
        node.appendChild(doc.createTextNode(String.valueOf(mItemId)));
        root.appendChild(node);

        node =  doc.createElement("EstimatorProductCd");
        node.appendChild(doc.createTextNode(String.valueOf(mEstimatorProductCd)));
        root.appendChild(node);

        node =  doc.createElement("UnitSize");
        node.appendChild(doc.createTextNode(String.valueOf(mUnitSize)));
        root.appendChild(node);

        node =  doc.createElement("UnitCd");
        node.appendChild(doc.createTextNode(String.valueOf(mUnitCd)));
        root.appendChild(node);

        node =  doc.createElement("PackQty");
        node.appendChild(doc.createTextNode(String.valueOf(mPackQty)));
        root.appendChild(node);

        node =  doc.createElement("ChemicalUsageModelCd");
        node.appendChild(doc.createTextNode(String.valueOf(mChemicalUsageModelCd)));
        root.appendChild(node);

        node =  doc.createElement("LinerSizeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mLinerSizeCd)));
        root.appendChild(node);

        node =  doc.createElement("AppearanceStandardCd");
        node.appendChild(doc.createTextNode(String.valueOf(mAppearanceStandardCd)));
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
    * creates a clone of this object, the ProductUomPackId field is not cloned.
    *
    * @return ProductUomPackData object
    */
    public Object clone(){
        ProductUomPackData myClone = new ProductUomPackData();
        
        myClone.mItemId = mItemId;
        
        myClone.mEstimatorProductCd = mEstimatorProductCd;
        
        myClone.mUnitSize = mUnitSize;
        
        myClone.mUnitCd = mUnitCd;
        
        myClone.mPackQty = mPackQty;
        
        myClone.mChemicalUsageModelCd = mChemicalUsageModelCd;
        
        myClone.mLinerSizeCd = mLinerSizeCd;
        
        myClone.mAppearanceStandardCd = mAppearanceStandardCd;
        
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

        if (ProductUomPackDataAccess.PRODUCT_UOM_PACK_ID.equals(pFieldName)) {
            return getProductUomPackId();
        } else if (ProductUomPackDataAccess.ITEM_ID.equals(pFieldName)) {
            return getItemId();
        } else if (ProductUomPackDataAccess.ESTIMATOR_PRODUCT_CD.equals(pFieldName)) {
            return getEstimatorProductCd();
        } else if (ProductUomPackDataAccess.UNIT_SIZE.equals(pFieldName)) {
            return getUnitSize();
        } else if (ProductUomPackDataAccess.UNIT_CD.equals(pFieldName)) {
            return getUnitCd();
        } else if (ProductUomPackDataAccess.PACK_QTY.equals(pFieldName)) {
            return getPackQty();
        } else if (ProductUomPackDataAccess.CHEMICAL_USAGE_MODEL_CD.equals(pFieldName)) {
            return getChemicalUsageModelCd();
        } else if (ProductUomPackDataAccess.LINER_SIZE_CD.equals(pFieldName)) {
            return getLinerSizeCd();
        } else if (ProductUomPackDataAccess.APPEARANCE_STANDARD_CD.equals(pFieldName)) {
            return getAppearanceStandardCd();
        } else if (ProductUomPackDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (ProductUomPackDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (ProductUomPackDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (ProductUomPackDataAccess.MOD_BY.equals(pFieldName)) {
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
        return ProductUomPackDataAccess.CLW_PRODUCT_UOM_PACK;
    }

    
    /**
     * Sets the ProductUomPackId field. This field is required to be set in the database.
     *
     * @param pProductUomPackId
     *  int to use to update the field.
     */
    public void setProductUomPackId(int pProductUomPackId){
        this.mProductUomPackId = pProductUomPackId;
        setDirty(true);
    }
    /**
     * Retrieves the ProductUomPackId field.
     *
     * @return
     *  int containing the ProductUomPackId field.
     */
    public int getProductUomPackId(){
        return mProductUomPackId;
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
     * Sets the EstimatorProductCd field.
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
     * Sets the UnitSize field.
     *
     * @param pUnitSize
     *  java.math.BigDecimal to use to update the field.
     */
    public void setUnitSize(java.math.BigDecimal pUnitSize){
        this.mUnitSize = pUnitSize;
        setDirty(true);
    }
    /**
     * Retrieves the UnitSize field.
     *
     * @return
     *  java.math.BigDecimal containing the UnitSize field.
     */
    public java.math.BigDecimal getUnitSize(){
        return mUnitSize;
    }

    /**
     * Sets the UnitCd field.
     *
     * @param pUnitCd
     *  String to use to update the field.
     */
    public void setUnitCd(String pUnitCd){
        this.mUnitCd = pUnitCd;
        setDirty(true);
    }
    /**
     * Retrieves the UnitCd field.
     *
     * @return
     *  String containing the UnitCd field.
     */
    public String getUnitCd(){
        return mUnitCd;
    }

    /**
     * Sets the PackQty field.
     *
     * @param pPackQty
     *  int to use to update the field.
     */
    public void setPackQty(int pPackQty){
        this.mPackQty = pPackQty;
        setDirty(true);
    }
    /**
     * Retrieves the PackQty field.
     *
     * @return
     *  int containing the PackQty field.
     */
    public int getPackQty(){
        return mPackQty;
    }

    /**
     * Sets the ChemicalUsageModelCd field.
     *
     * @param pChemicalUsageModelCd
     *  String to use to update the field.
     */
    public void setChemicalUsageModelCd(String pChemicalUsageModelCd){
        this.mChemicalUsageModelCd = pChemicalUsageModelCd;
        setDirty(true);
    }
    /**
     * Retrieves the ChemicalUsageModelCd field.
     *
     * @return
     *  String containing the ChemicalUsageModelCd field.
     */
    public String getChemicalUsageModelCd(){
        return mChemicalUsageModelCd;
    }

    /**
     * Sets the LinerSizeCd field.
     *
     * @param pLinerSizeCd
     *  String to use to update the field.
     */
    public void setLinerSizeCd(String pLinerSizeCd){
        this.mLinerSizeCd = pLinerSizeCd;
        setDirty(true);
    }
    /**
     * Retrieves the LinerSizeCd field.
     *
     * @return
     *  String containing the LinerSizeCd field.
     */
    public String getLinerSizeCd(){
        return mLinerSizeCd;
    }

    /**
     * Sets the AppearanceStandardCd field.
     *
     * @param pAppearanceStandardCd
     *  String to use to update the field.
     */
    public void setAppearanceStandardCd(String pAppearanceStandardCd){
        this.mAppearanceStandardCd = pAppearanceStandardCd;
        setDirty(true);
    }
    /**
     * Retrieves the AppearanceStandardCd field.
     *
     * @return
     *  String containing the AppearanceStandardCd field.
     */
    public String getAppearanceStandardCd(){
        return mAppearanceStandardCd;
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
