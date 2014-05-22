
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        StagedItemData
 * Description:  This is a ValueObject class wrapping the database table CLW_STAGED_ITEM.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.StagedItemDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>StagedItemData</code> is a ValueObject class wrapping of the database table CLW_STAGED_ITEM.
 */
public class StagedItemData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 1L;
    private int mStagedItemId;// SQL type:NUMBER, not null
    private int mVersionNumber;// SQL type:NUMBER
    private String mAction;// SQL type:VARCHAR2
    private String mAsset;// SQL type:VARCHAR2
    private int mStoreId;// SQL type:NUMBER
    private String mStoreName;// SQL type:VARCHAR2
    private String mDistSku;// SQL type:VARCHAR2
    private String mMfgSku;// SQL type:VARCHAR2
    private String mManufacturer;// SQL type:VARCHAR2
    private String mDistributor;// SQL type:VARCHAR2
    private String mPack;// SQL type:VARCHAR2
    private String mUom;// SQL type:VARCHAR2
    private String mCategoryName;// SQL type:VARCHAR2
    private String mSubCat1;// SQL type:VARCHAR2
    private String mSubCat2;// SQL type:VARCHAR2
    private String mSubCat3;// SQL type:VARCHAR2
    private String mMultiProductName;// SQL type:VARCHAR2
    private String mItemSize;// SQL type:VARCHAR2
    private String mLongDescription;// SQL type:VARCHAR2
    private String mShortDescription;// SQL type:VARCHAR2
    private String mProductUpc;// SQL type:VARCHAR2
    private String mPackUpc;// SQL type:VARCHAR2
    private String mUnspscCode;// SQL type:VARCHAR2
    private String mColor;// SQL type:VARCHAR2
    private int mShippingWeight;// SQL type:NUMBER
    private String mWeightUnit;// SQL type:VARCHAR2
    private int mNsn;// SQL type:NUMBER
    private int mShippingCubicSize;// SQL type:NUMBER
    private String mHazmat;// SQL type:VARCHAR2
    private String mCertifiedCompanies;// SQL type:VARCHAR2
    private String mImage;// SQL type:VARCHAR2
    private byte[] mImageBlob;// SQL type:BLOB
    private String mMsds;// SQL type:VARCHAR2
    private byte[] mMsdsBlob;// SQL type:BLOB
    private String mSpecification;// SQL type:VARCHAR2
    private byte[] mSpecificationBlob;// SQL type:BLOB
    private String mItemName;// SQL type:VARCHAR2
    private String mModelNumber;// SQL type:VARCHAR2
    private String mAssocDoc1;// SQL type:VARCHAR2
    private byte[] mAssocDoc1Blob;// SQL type:BLOB
    private String mAssocDoc2;// SQL type:VARCHAR2
    private byte[] mAssocDoc2Blob;// SQL type:BLOB
    private String mAssocDoc3;// SQL type:VARCHAR2
    private byte[] mAssocDoc3Blob;// SQL type:BLOB
    private int mMatchedItemId;// SQL type:NUMBER

    /**
     * Constructor.
     */
    public StagedItemData ()
    {
        mAction = "";
        mAsset = "";
        mStoreName = "";
        mDistSku = "";
        mMfgSku = "";
        mManufacturer = "";
        mDistributor = "";
        mPack = "";
        mUom = "";
        mCategoryName = "";
        mSubCat1 = "";
        mSubCat2 = "";
        mSubCat3 = "";
        mMultiProductName = "";
        mItemSize = "";
        mLongDescription = "";
        mShortDescription = "";
        mProductUpc = "";
        mPackUpc = "";
        mUnspscCode = "";
        mColor = "";
        mWeightUnit = "";
        mHazmat = "";
        mCertifiedCompanies = "";
        mImage = "";
        mMsds = "";
        mSpecification = "";
        mItemName = "";
        mModelNumber = "";
        mAssocDoc1 = "";
        mAssocDoc2 = "";
        mAssocDoc3 = "";
    }

    /**
     * Constructor.
     */
    public StagedItemData(int parm1, int parm2, String parm3, String parm4, int parm5, String parm6, String parm7, String parm8, String parm9, String parm10, String parm11, String parm12, String parm13, String parm14, String parm15, String parm16, String parm17, String parm18, String parm19, String parm20, String parm21, String parm22, String parm23, String parm24, int parm25, String parm26, int parm27, int parm28, String parm29, String parm30, String parm31, byte[] parm32, String parm33, byte[] parm34, String parm35, byte[] parm36, String parm37, String parm38, String parm39, byte[] parm40, String parm41, byte[] parm42, String parm43, byte[] parm44, int parm45)
    {
        mStagedItemId = parm1;
        mVersionNumber = parm2;
        mAction = parm3;
        mAsset = parm4;
        mStoreId = parm5;
        mStoreName = parm6;
        mDistSku = parm7;
        mMfgSku = parm8;
        mManufacturer = parm9;
        mDistributor = parm10;
        mPack = parm11;
        mUom = parm12;
        mCategoryName = parm13;
        mSubCat1 = parm14;
        mSubCat2 = parm15;
        mSubCat3 = parm16;
        mMultiProductName = parm17;
        mItemSize = parm18;
        mLongDescription = parm19;
        mShortDescription = parm20;
        mProductUpc = parm21;
        mPackUpc = parm22;
        mUnspscCode = parm23;
        mColor = parm24;
        mShippingWeight = parm25;
        mWeightUnit = parm26;
        mNsn = parm27;
        mShippingCubicSize = parm28;
        mHazmat = parm29;
        mCertifiedCompanies = parm30;
        mImage = parm31;
        mImageBlob = parm32;
        mMsds = parm33;
        mMsdsBlob = parm34;
        mSpecification = parm35;
        mSpecificationBlob = parm36;
        mItemName = parm37;
        mModelNumber = parm38;
        mAssocDoc1 = parm39;
        mAssocDoc1Blob = parm40;
        mAssocDoc2 = parm41;
        mAssocDoc2Blob = parm42;
        mAssocDoc3 = parm43;
        mAssocDoc3Blob = parm44;
        mMatchedItemId = parm45;

    }

    /**
     * Creates a new StagedItemData
     *
     * @return
     *  Newly initialized StagedItemData object.
     */
    public static StagedItemData createValue ()
    {
        StagedItemData valueData = new StagedItemData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this StagedItemData object
     */
    public String toString()
    {
        return "[" + "StagedItemId=" + mStagedItemId + ", VersionNumber=" + mVersionNumber + ", Action=" + mAction + ", Asset=" + mAsset + ", StoreId=" + mStoreId + ", StoreName=" + mStoreName + ", DistSku=" + mDistSku + ", MfgSku=" + mMfgSku + ", Manufacturer=" + mManufacturer + ", Distributor=" + mDistributor + ", Pack=" + mPack + ", Uom=" + mUom + ", CategoryName=" + mCategoryName + ", SubCat1=" + mSubCat1 + ", SubCat2=" + mSubCat2 + ", SubCat3=" + mSubCat3 + ", MultiProductName=" + mMultiProductName + ", ItemSize=" + mItemSize + ", LongDescription=" + mLongDescription + ", ShortDescription=" + mShortDescription + ", ProductUpc=" + mProductUpc + ", PackUpc=" + mPackUpc + ", UnspscCode=" + mUnspscCode + ", Color=" + mColor + ", ShippingWeight=" + mShippingWeight + ", WeightUnit=" + mWeightUnit + ", Nsn=" + mNsn + ", ShippingCubicSize=" + mShippingCubicSize + ", Hazmat=" + mHazmat + ", CertifiedCompanies=" + mCertifiedCompanies + ", Image=" + mImage + ", ImageBlob=" + mImageBlob + ", Msds=" + mMsds + ", MsdsBlob=" + mMsdsBlob + ", Specification=" + mSpecification + ", SpecificationBlob=" + mSpecificationBlob + ", ItemName=" + mItemName + ", ModelNumber=" + mModelNumber + ", AssocDoc1=" + mAssocDoc1 + ", AssocDoc1Blob=" + mAssocDoc1Blob + ", AssocDoc2=" + mAssocDoc2 + ", AssocDoc2Blob=" + mAssocDoc2Blob + ", AssocDoc3=" + mAssocDoc3 + ", AssocDoc3Blob=" + mAssocDoc3Blob + ", MatchedItemId=" + mMatchedItemId  + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("StagedItem");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mStagedItemId));

        node =  doc.createElement("VersionNumber");
        node.appendChild(doc.createTextNode(String.valueOf(mVersionNumber)));
        root.appendChild(node);

        node =  doc.createElement("Action");
        node.appendChild(doc.createTextNode(String.valueOf(mAction)));
        root.appendChild(node);

        node =  doc.createElement("Asset");
        node.appendChild(doc.createTextNode(String.valueOf(mAsset)));
        root.appendChild(node);

        node =  doc.createElement("StoreId");
        node.appendChild(doc.createTextNode(String.valueOf(mStoreId)));
        root.appendChild(node);

        node =  doc.createElement("StoreName");
        node.appendChild(doc.createTextNode(String.valueOf(mStoreName)));
        root.appendChild(node);

        node =  doc.createElement("DistSku");
        node.appendChild(doc.createTextNode(String.valueOf(mDistSku)));
        root.appendChild(node);

        node =  doc.createElement("MfgSku");
        node.appendChild(doc.createTextNode(String.valueOf(mMfgSku)));
        root.appendChild(node);

        node =  doc.createElement("Manufacturer");
        node.appendChild(doc.createTextNode(String.valueOf(mManufacturer)));
        root.appendChild(node);

        node =  doc.createElement("Distributor");
        node.appendChild(doc.createTextNode(String.valueOf(mDistributor)));
        root.appendChild(node);

        node =  doc.createElement("Pack");
        node.appendChild(doc.createTextNode(String.valueOf(mPack)));
        root.appendChild(node);

        node =  doc.createElement("Uom");
        node.appendChild(doc.createTextNode(String.valueOf(mUom)));
        root.appendChild(node);

        node =  doc.createElement("CategoryName");
        node.appendChild(doc.createTextNode(String.valueOf(mCategoryName)));
        root.appendChild(node);

        node =  doc.createElement("SubCat1");
        node.appendChild(doc.createTextNode(String.valueOf(mSubCat1)));
        root.appendChild(node);

        node =  doc.createElement("SubCat2");
        node.appendChild(doc.createTextNode(String.valueOf(mSubCat2)));
        root.appendChild(node);

        node =  doc.createElement("SubCat3");
        node.appendChild(doc.createTextNode(String.valueOf(mSubCat3)));
        root.appendChild(node);

        node =  doc.createElement("MultiProductName");
        node.appendChild(doc.createTextNode(String.valueOf(mMultiProductName)));
        root.appendChild(node);

        node =  doc.createElement("ItemSize");
        node.appendChild(doc.createTextNode(String.valueOf(mItemSize)));
        root.appendChild(node);

        node =  doc.createElement("LongDescription");
        node.appendChild(doc.createTextNode(String.valueOf(mLongDescription)));
        root.appendChild(node);

        node =  doc.createElement("ShortDescription");
        node.appendChild(doc.createTextNode(String.valueOf(mShortDescription)));
        root.appendChild(node);

        node =  doc.createElement("ProductUpc");
        node.appendChild(doc.createTextNode(String.valueOf(mProductUpc)));
        root.appendChild(node);

        node =  doc.createElement("PackUpc");
        node.appendChild(doc.createTextNode(String.valueOf(mPackUpc)));
        root.appendChild(node);

        node =  doc.createElement("UnspscCode");
        node.appendChild(doc.createTextNode(String.valueOf(mUnspscCode)));
        root.appendChild(node);

        node =  doc.createElement("Color");
        node.appendChild(doc.createTextNode(String.valueOf(mColor)));
        root.appendChild(node);

        node =  doc.createElement("ShippingWeight");
        node.appendChild(doc.createTextNode(String.valueOf(mShippingWeight)));
        root.appendChild(node);

        node =  doc.createElement("WeightUnit");
        node.appendChild(doc.createTextNode(String.valueOf(mWeightUnit)));
        root.appendChild(node);

        node =  doc.createElement("Nsn");
        node.appendChild(doc.createTextNode(String.valueOf(mNsn)));
        root.appendChild(node);

        node =  doc.createElement("ShippingCubicSize");
        node.appendChild(doc.createTextNode(String.valueOf(mShippingCubicSize)));
        root.appendChild(node);

        node =  doc.createElement("Hazmat");
        node.appendChild(doc.createTextNode(String.valueOf(mHazmat)));
        root.appendChild(node);

        node =  doc.createElement("CertifiedCompanies");
        node.appendChild(doc.createTextNode(String.valueOf(mCertifiedCompanies)));
        root.appendChild(node);

        node =  doc.createElement("Image");
        node.appendChild(doc.createTextNode(String.valueOf(mImage)));
        root.appendChild(node);

        node =  doc.createElement("ImageBlob");
        node.appendChild(doc.createTextNode(String.valueOf(mImageBlob)));
        root.appendChild(node);

        node =  doc.createElement("Msds");
        node.appendChild(doc.createTextNode(String.valueOf(mMsds)));
        root.appendChild(node);

        node =  doc.createElement("MsdsBlob");
        node.appendChild(doc.createTextNode(String.valueOf(mMsdsBlob)));
        root.appendChild(node);

        node =  doc.createElement("Specification");
        node.appendChild(doc.createTextNode(String.valueOf(mSpecification)));
        root.appendChild(node);

        node =  doc.createElement("SpecificationBlob");
        node.appendChild(doc.createTextNode(String.valueOf(mSpecificationBlob)));
        root.appendChild(node);

        node =  doc.createElement("ItemName");
        node.appendChild(doc.createTextNode(String.valueOf(mItemName)));
        root.appendChild(node);

        node =  doc.createElement("ModelNumber");
        node.appendChild(doc.createTextNode(String.valueOf(mModelNumber)));
        root.appendChild(node);

        node =  doc.createElement("AssocDoc1");
        node.appendChild(doc.createTextNode(String.valueOf(mAssocDoc1)));
        root.appendChild(node);

        node =  doc.createElement("AssocDoc1Blob");
        node.appendChild(doc.createTextNode(String.valueOf(mAssocDoc1Blob)));
        root.appendChild(node);

        node =  doc.createElement("AssocDoc2");
        node.appendChild(doc.createTextNode(String.valueOf(mAssocDoc2)));
        root.appendChild(node);

        node =  doc.createElement("AssocDoc2Blob");
        node.appendChild(doc.createTextNode(String.valueOf(mAssocDoc2Blob)));
        root.appendChild(node);

        node =  doc.createElement("AssocDoc3");
        node.appendChild(doc.createTextNode(String.valueOf(mAssocDoc3)));
        root.appendChild(node);

        node =  doc.createElement("AssocDoc3Blob");
        node.appendChild(doc.createTextNode(String.valueOf(mAssocDoc3Blob)));
        root.appendChild(node);

        node =  doc.createElement("MatchedItemId");
        node.appendChild(doc.createTextNode(String.valueOf(mMatchedItemId)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the StagedItemId field is not cloned.
    *
    * @return StagedItemData object
    */
    public Object clone(){
        StagedItemData myClone = new StagedItemData();
        
        myClone.mVersionNumber = mVersionNumber;
        
        myClone.mAction = mAction;
        
        myClone.mAsset = mAsset;
        
        myClone.mStoreId = mStoreId;
        
        myClone.mStoreName = mStoreName;
        
        myClone.mDistSku = mDistSku;
        
        myClone.mMfgSku = mMfgSku;
        
        myClone.mManufacturer = mManufacturer;
        
        myClone.mDistributor = mDistributor;
        
        myClone.mPack = mPack;
        
        myClone.mUom = mUom;
        
        myClone.mCategoryName = mCategoryName;
        
        myClone.mSubCat1 = mSubCat1;
        
        myClone.mSubCat2 = mSubCat2;
        
        myClone.mSubCat3 = mSubCat3;
        
        myClone.mMultiProductName = mMultiProductName;
        
        myClone.mItemSize = mItemSize;
        
        myClone.mLongDescription = mLongDescription;
        
        myClone.mShortDescription = mShortDescription;
        
        myClone.mProductUpc = mProductUpc;
        
        myClone.mPackUpc = mPackUpc;
        
        myClone.mUnspscCode = mUnspscCode;
        
        myClone.mColor = mColor;
        
        myClone.mShippingWeight = mShippingWeight;
        
        myClone.mWeightUnit = mWeightUnit;
        
        myClone.mNsn = mNsn;
        
        myClone.mShippingCubicSize = mShippingCubicSize;
        
        myClone.mHazmat = mHazmat;
        
        myClone.mCertifiedCompanies = mCertifiedCompanies;
        
        myClone.mImage = mImage;
        
        myClone.mImageBlob = mImageBlob;
        
        myClone.mMsds = mMsds;
        
        myClone.mMsdsBlob = mMsdsBlob;
        
        myClone.mSpecification = mSpecification;
        
        myClone.mSpecificationBlob = mSpecificationBlob;
        
        myClone.mItemName = mItemName;
        
        myClone.mModelNumber = mModelNumber;
        
        myClone.mAssocDoc1 = mAssocDoc1;
        
        myClone.mAssocDoc1Blob = mAssocDoc1Blob;
        
        myClone.mAssocDoc2 = mAssocDoc2;
        
        myClone.mAssocDoc2Blob = mAssocDoc2Blob;
        
        myClone.mAssocDoc3 = mAssocDoc3;
        
        myClone.mAssocDoc3Blob = mAssocDoc3Blob;
        
        myClone.mMatchedItemId = mMatchedItemId;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (StagedItemDataAccess.STAGED_ITEM_ID.equals(pFieldName)) {
            return getStagedItemId();
        } else if (StagedItemDataAccess.VERSION_NUMBER.equals(pFieldName)) {
            return getVersionNumber();
        } else if (StagedItemDataAccess.ACTION.equals(pFieldName)) {
            return getAction();
        } else if (StagedItemDataAccess.ASSET.equals(pFieldName)) {
            return getAsset();
        } else if (StagedItemDataAccess.STORE_ID.equals(pFieldName)) {
            return getStoreId();
        } else if (StagedItemDataAccess.STORE_NAME.equals(pFieldName)) {
            return getStoreName();
        } else if (StagedItemDataAccess.DIST_SKU.equals(pFieldName)) {
            return getDistSku();
        } else if (StagedItemDataAccess.MFG_SKU.equals(pFieldName)) {
            return getMfgSku();
        } else if (StagedItemDataAccess.MANUFACTURER.equals(pFieldName)) {
            return getManufacturer();
        } else if (StagedItemDataAccess.DISTRIBUTOR.equals(pFieldName)) {
            return getDistributor();
        } else if (StagedItemDataAccess.PACK.equals(pFieldName)) {
            return getPack();
        } else if (StagedItemDataAccess.UOM.equals(pFieldName)) {
            return getUom();
        } else if (StagedItemDataAccess.CATEGORY_NAME.equals(pFieldName)) {
            return getCategoryName();
        } else if (StagedItemDataAccess.SUB_CAT_1.equals(pFieldName)) {
            return getSubCat1();
        } else if (StagedItemDataAccess.SUB_CAT_2.equals(pFieldName)) {
            return getSubCat2();
        } else if (StagedItemDataAccess.SUB_CAT_3.equals(pFieldName)) {
            return getSubCat3();
        } else if (StagedItemDataAccess.MULTI_PRODUCT_NAME.equals(pFieldName)) {
            return getMultiProductName();
        } else if (StagedItemDataAccess.ITEM_SIZE.equals(pFieldName)) {
            return getItemSize();
        } else if (StagedItemDataAccess.LONG_DESCRIPTION.equals(pFieldName)) {
            return getLongDescription();
        } else if (StagedItemDataAccess.SHORT_DESCRIPTION.equals(pFieldName)) {
            return getShortDescription();
        } else if (StagedItemDataAccess.PRODUCT_UPC.equals(pFieldName)) {
            return getProductUpc();
        } else if (StagedItemDataAccess.PACK_UPC.equals(pFieldName)) {
            return getPackUpc();
        } else if (StagedItemDataAccess.UNSPSC_CODE.equals(pFieldName)) {
            return getUnspscCode();
        } else if (StagedItemDataAccess.COLOR.equals(pFieldName)) {
            return getColor();
        } else if (StagedItemDataAccess.SHIPPING_WEIGHT.equals(pFieldName)) {
            return getShippingWeight();
        } else if (StagedItemDataAccess.WEIGHT_UNIT.equals(pFieldName)) {
            return getWeightUnit();
        } else if (StagedItemDataAccess.NSN.equals(pFieldName)) {
            return getNsn();
        } else if (StagedItemDataAccess.SHIPPING_CUBIC_SIZE.equals(pFieldName)) {
            return getShippingCubicSize();
        } else if (StagedItemDataAccess.HAZMAT.equals(pFieldName)) {
            return getHazmat();
        } else if (StagedItemDataAccess.CERTIFIED_COMPANIES.equals(pFieldName)) {
            return getCertifiedCompanies();
        } else if (StagedItemDataAccess.IMAGE.equals(pFieldName)) {
            return getImage();
        } else if (StagedItemDataAccess.IMAGE_BLOB.equals(pFieldName)) {
            return getImageBlob();
        } else if (StagedItemDataAccess.MSDS.equals(pFieldName)) {
            return getMsds();
        } else if (StagedItemDataAccess.MSDS_BLOB.equals(pFieldName)) {
            return getMsdsBlob();
        } else if (StagedItemDataAccess.SPECIFICATION.equals(pFieldName)) {
            return getSpecification();
        } else if (StagedItemDataAccess.SPECIFICATION_BLOB.equals(pFieldName)) {
            return getSpecificationBlob();
        } else if (StagedItemDataAccess.ITEM_NAME.equals(pFieldName)) {
            return getItemName();
        } else if (StagedItemDataAccess.MODEL_NUMBER.equals(pFieldName)) {
            return getModelNumber();
        } else if (StagedItemDataAccess.ASSOC_DOC_1.equals(pFieldName)) {
            return getAssocDoc1();
        } else if (StagedItemDataAccess.ASSOC_DOC_1_BLOB.equals(pFieldName)) {
            return getAssocDoc1Blob();
        } else if (StagedItemDataAccess.ASSOC_DOC_2.equals(pFieldName)) {
            return getAssocDoc2();
        } else if (StagedItemDataAccess.ASSOC_DOC_2_BLOB.equals(pFieldName)) {
            return getAssocDoc2Blob();
        } else if (StagedItemDataAccess.ASSOC_DOC_3.equals(pFieldName)) {
            return getAssocDoc3();
        } else if (StagedItemDataAccess.ASSOC_DOC_3_BLOB.equals(pFieldName)) {
            return getAssocDoc3Blob();
        } else if (StagedItemDataAccess.MATCHED_ITEM_ID.equals(pFieldName)) {
            return getMatchedItemId();
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
        return StagedItemDataAccess.CLW_STAGED_ITEM;
    }

    
    /**
     * Sets the StagedItemId field. This field is required to be set in the database.
     *
     * @param pStagedItemId
     *  int to use to update the field.
     */
    public void setStagedItemId(int pStagedItemId){
        this.mStagedItemId = pStagedItemId;
        setDirty(true);
    }
    /**
     * Retrieves the StagedItemId field.
     *
     * @return
     *  int containing the StagedItemId field.
     */
    public int getStagedItemId(){
        return mStagedItemId;
    }

    /**
     * Sets the VersionNumber field.
     *
     * @param pVersionNumber
     *  int to use to update the field.
     */
    public void setVersionNumber(int pVersionNumber){
        this.mVersionNumber = pVersionNumber;
        setDirty(true);
    }
    /**
     * Retrieves the VersionNumber field.
     *
     * @return
     *  int containing the VersionNumber field.
     */
    public int getVersionNumber(){
        return mVersionNumber;
    }

    /**
     * Sets the Action field.
     *
     * @param pAction
     *  String to use to update the field.
     */
    public void setAction(String pAction){
        this.mAction = pAction;
        setDirty(true);
    }
    /**
     * Retrieves the Action field.
     *
     * @return
     *  String containing the Action field.
     */
    public String getAction(){
        return mAction;
    }

    /**
     * Sets the Asset field.
     *
     * @param pAsset
     *  String to use to update the field.
     */
    public void setAsset(String pAsset){
        this.mAsset = pAsset;
        setDirty(true);
    }
    /**
     * Retrieves the Asset field.
     *
     * @return
     *  String containing the Asset field.
     */
    public String getAsset(){
        return mAsset;
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
     * Sets the StoreName field.
     *
     * @param pStoreName
     *  String to use to update the field.
     */
    public void setStoreName(String pStoreName){
        this.mStoreName = pStoreName;
        setDirty(true);
    }
    /**
     * Retrieves the StoreName field.
     *
     * @return
     *  String containing the StoreName field.
     */
    public String getStoreName(){
        return mStoreName;
    }

    /**
     * Sets the DistSku field.
     *
     * @param pDistSku
     *  String to use to update the field.
     */
    public void setDistSku(String pDistSku){
        this.mDistSku = pDistSku;
        setDirty(true);
    }
    /**
     * Retrieves the DistSku field.
     *
     * @return
     *  String containing the DistSku field.
     */
    public String getDistSku(){
        return mDistSku;
    }

    /**
     * Sets the MfgSku field.
     *
     * @param pMfgSku
     *  String to use to update the field.
     */
    public void setMfgSku(String pMfgSku){
        this.mMfgSku = pMfgSku;
        setDirty(true);
    }
    /**
     * Retrieves the MfgSku field.
     *
     * @return
     *  String containing the MfgSku field.
     */
    public String getMfgSku(){
        return mMfgSku;
    }

    /**
     * Sets the Manufacturer field.
     *
     * @param pManufacturer
     *  String to use to update the field.
     */
    public void setManufacturer(String pManufacturer){
        this.mManufacturer = pManufacturer;
        setDirty(true);
    }
    /**
     * Retrieves the Manufacturer field.
     *
     * @return
     *  String containing the Manufacturer field.
     */
    public String getManufacturer(){
        return mManufacturer;
    }

    /**
     * Sets the Distributor field.
     *
     * @param pDistributor
     *  String to use to update the field.
     */
    public void setDistributor(String pDistributor){
        this.mDistributor = pDistributor;
        setDirty(true);
    }
    /**
     * Retrieves the Distributor field.
     *
     * @return
     *  String containing the Distributor field.
     */
    public String getDistributor(){
        return mDistributor;
    }

    /**
     * Sets the Pack field.
     *
     * @param pPack
     *  String to use to update the field.
     */
    public void setPack(String pPack){
        this.mPack = pPack;
        setDirty(true);
    }
    /**
     * Retrieves the Pack field.
     *
     * @return
     *  String containing the Pack field.
     */
    public String getPack(){
        return mPack;
    }

    /**
     * Sets the Uom field.
     *
     * @param pUom
     *  String to use to update the field.
     */
    public void setUom(String pUom){
        this.mUom = pUom;
        setDirty(true);
    }
    /**
     * Retrieves the Uom field.
     *
     * @return
     *  String containing the Uom field.
     */
    public String getUom(){
        return mUom;
    }

    /**
     * Sets the CategoryName field.
     *
     * @param pCategoryName
     *  String to use to update the field.
     */
    public void setCategoryName(String pCategoryName){
        this.mCategoryName = pCategoryName;
        setDirty(true);
    }
    /**
     * Retrieves the CategoryName field.
     *
     * @return
     *  String containing the CategoryName field.
     */
    public String getCategoryName(){
        return mCategoryName;
    }

    /**
     * Sets the SubCat1 field.
     *
     * @param pSubCat1
     *  String to use to update the field.
     */
    public void setSubCat1(String pSubCat1){
        this.mSubCat1 = pSubCat1;
        setDirty(true);
    }
    /**
     * Retrieves the SubCat1 field.
     *
     * @return
     *  String containing the SubCat1 field.
     */
    public String getSubCat1(){
        return mSubCat1;
    }

    /**
     * Sets the SubCat2 field.
     *
     * @param pSubCat2
     *  String to use to update the field.
     */
    public void setSubCat2(String pSubCat2){
        this.mSubCat2 = pSubCat2;
        setDirty(true);
    }
    /**
     * Retrieves the SubCat2 field.
     *
     * @return
     *  String containing the SubCat2 field.
     */
    public String getSubCat2(){
        return mSubCat2;
    }

    /**
     * Sets the SubCat3 field.
     *
     * @param pSubCat3
     *  String to use to update the field.
     */
    public void setSubCat3(String pSubCat3){
        this.mSubCat3 = pSubCat3;
        setDirty(true);
    }
    /**
     * Retrieves the SubCat3 field.
     *
     * @return
     *  String containing the SubCat3 field.
     */
    public String getSubCat3(){
        return mSubCat3;
    }

    /**
     * Sets the MultiProductName field.
     *
     * @param pMultiProductName
     *  String to use to update the field.
     */
    public void setMultiProductName(String pMultiProductName){
        this.mMultiProductName = pMultiProductName;
        setDirty(true);
    }
    /**
     * Retrieves the MultiProductName field.
     *
     * @return
     *  String containing the MultiProductName field.
     */
    public String getMultiProductName(){
        return mMultiProductName;
    }

    /**
     * Sets the ItemSize field.
     *
     * @param pItemSize
     *  String to use to update the field.
     */
    public void setItemSize(String pItemSize){
        this.mItemSize = pItemSize;
        setDirty(true);
    }
    /**
     * Retrieves the ItemSize field.
     *
     * @return
     *  String containing the ItemSize field.
     */
    public String getItemSize(){
        return mItemSize;
    }

    /**
     * Sets the LongDescription field.
     *
     * @param pLongDescription
     *  String to use to update the field.
     */
    public void setLongDescription(String pLongDescription){
        this.mLongDescription = pLongDescription;
        setDirty(true);
    }
    /**
     * Retrieves the LongDescription field.
     *
     * @return
     *  String containing the LongDescription field.
     */
    public String getLongDescription(){
        return mLongDescription;
    }

    /**
     * Sets the ShortDescription field.
     *
     * @param pShortDescription
     *  String to use to update the field.
     */
    public void setShortDescription(String pShortDescription){
        this.mShortDescription = pShortDescription;
        setDirty(true);
    }
    /**
     * Retrieves the ShortDescription field.
     *
     * @return
     *  String containing the ShortDescription field.
     */
    public String getShortDescription(){
        return mShortDescription;
    }

    /**
     * Sets the ProductUpc field.
     *
     * @param pProductUpc
     *  String to use to update the field.
     */
    public void setProductUpc(String pProductUpc){
        this.mProductUpc = pProductUpc;
        setDirty(true);
    }
    /**
     * Retrieves the ProductUpc field.
     *
     * @return
     *  String containing the ProductUpc field.
     */
    public String getProductUpc(){
        return mProductUpc;
    }

    /**
     * Sets the PackUpc field.
     *
     * @param pPackUpc
     *  String to use to update the field.
     */
    public void setPackUpc(String pPackUpc){
        this.mPackUpc = pPackUpc;
        setDirty(true);
    }
    /**
     * Retrieves the PackUpc field.
     *
     * @return
     *  String containing the PackUpc field.
     */
    public String getPackUpc(){
        return mPackUpc;
    }

    /**
     * Sets the UnspscCode field.
     *
     * @param pUnspscCode
     *  String to use to update the field.
     */
    public void setUnspscCode(String pUnspscCode){
        this.mUnspscCode = pUnspscCode;
        setDirty(true);
    }
    /**
     * Retrieves the UnspscCode field.
     *
     * @return
     *  String containing the UnspscCode field.
     */
    public String getUnspscCode(){
        return mUnspscCode;
    }

    /**
     * Sets the Color field.
     *
     * @param pColor
     *  String to use to update the field.
     */
    public void setColor(String pColor){
        this.mColor = pColor;
        setDirty(true);
    }
    /**
     * Retrieves the Color field.
     *
     * @return
     *  String containing the Color field.
     */
    public String getColor(){
        return mColor;
    }

    /**
     * Sets the ShippingWeight field.
     *
     * @param pShippingWeight
     *  java.math.BigDecimal to use to update the field.
     */
    public void setShippingWeight(int pShippingWeight){
        this.mShippingWeight = pShippingWeight;
        setDirty(true);
    }
    /**
     * Retrieves the ShippingWeight field.
     *
     * @return
     *  java.math.BigDecimal containing the ShippingWeight field.
     */
    public int getShippingWeight(){
        return mShippingWeight;
    }

    /**
     * Sets the WeightUnit field.
     *
     * @param pWeightUnit
     *  String to use to update the field.
     */
    public void setWeightUnit(String pWeightUnit){
        this.mWeightUnit = pWeightUnit;
        setDirty(true);
    }
    /**
     * Retrieves the WeightUnit field.
     *
     * @return
     *  String containing the WeightUnit field.
     */
    public String getWeightUnit(){
        return mWeightUnit;
    }

    /**
     * Sets the Nsn field.
     *
     * @param pNsn
     *  int to use to update the field.
     */
    public void setNsn(int pNsn){
        this.mNsn = pNsn;
        setDirty(true);
    }
    /**
     * Retrieves the Nsn field.
     *
     * @return
     *  int containing the Nsn field.
     */
    public int getNsn(){
        return mNsn;
    }

    /**
     * Sets the ShippingCubicSize field.
     *
     * @param pShippingCubicSize
     *  int to use to update the field
     */
    public void setShippingCubicSize(int pShippingCubicSize){
        this.mShippingCubicSize = pShippingCubicSize;
        setDirty(true);
    }
    /**
     * Retrieves the ShippingCubicSize field.
     *
     * @return
     *   int containing the ShippingCubicSize field.
     */
    public int getShippingCubicSize(){
        return mShippingCubicSize;
    }

    /**
     * Sets the Hazmat field.
     *
     * @param pHazmat
     *  String to use to update the field.
     */
    public void setHazmat(String pHazmat){
        this.mHazmat = pHazmat;
        setDirty(true);
    }
    /**
     * Retrieves the Hazmat field.
     *
     * @return
     *  String containing the Hazmat field.
     */
    public String getHazmat(){
        return mHazmat;
    }

    /**
     * Sets the CertifiedCompanies field.
     *
     * @param pCertifiedCompanies
     *  String to use to update the field.
     */
    public void setCertifiedCompanies(String pCertifiedCompanies){
        this.mCertifiedCompanies = pCertifiedCompanies;
        setDirty(true);
    }
    /**
     * Retrieves the CertifiedCompanies field.
     *
     * @return
     *  String containing the CertifiedCompanies field.
     */
    public String getCertifiedCompanies(){
        return mCertifiedCompanies;
    }

    /**
     * Sets the Image field.
     *
     * @param pImage
     *  String to use to update the field.
     */
    public void setImage(String pImage){
        this.mImage = pImage;
        setDirty(true);
    }
    /**
     * Retrieves the Image field.
     *
     * @return
     *  String containing the Image field.
     */
    public String getImage(){
        return mImage;
    }

    /**
     * Sets the ImageBlob field.
     *
     * @param pImageBlob
     *  byte[] to use to update the field.
     */
    public void setImageBlob(byte[] pImageBlob){
        this.mImageBlob = pImageBlob;
        setDirty(true);
    }
    /**
     * Retrieves the ImageBlob field.
     *
     * @return
     *  byte[] containing the ImageBlob field.
     */
    public byte[] getImageBlob(){
        return mImageBlob;
    }

    /**
     * Sets the Msds field.
     *
     * @param pMsds
     *  String to use to update the field.
     */
    public void setMsds(String pMsds){
        this.mMsds = pMsds;
        setDirty(true);
    }
    /**
     * Retrieves the Msds field.
     *
     * @return
     *  String containing the Msds field.
     */
    public String getMsds(){
        return mMsds;
    }

    /**
     * Sets the MsdsBlob field.
     *
     * @param pMsdsBlob
     *  byte[] to use to update the field.
     */
    public void setMsdsBlob(byte[] pMsdsBlob){
        this.mMsdsBlob = pMsdsBlob;
        setDirty(true);
    }
    /**
     * Retrieves the MsdsBlob field.
     *
     * @return
     *  byte[] containing the MsdsBlob field.
     */
    public byte[] getMsdsBlob(){
        return mMsdsBlob;
    }

    /**
     * Sets the Specification field.
     *
     * @param pSpecification
     *  String to use to update the field.
     */
    public void setSpecification(String pSpecification){
        this.mSpecification = pSpecification;
        setDirty(true);
    }
    /**
     * Retrieves the Specification field.
     *
     * @return
     *  String containing the Specification field.
     */
    public String getSpecification(){
        return mSpecification;
    }

    /**
     * Sets the SpecificationBlob field.
     *
     * @param pSpecificationBlob
     *  byte[] to use to update the field.
     */
    public void setSpecificationBlob(byte[] pSpecificationBlob){
        this.mSpecificationBlob = pSpecificationBlob;
        setDirty(true);
    }
    /**
     * Retrieves the SpecificationBlob field.
     *
     * @return
     *  byte[] containing the SpecificationBlob field.
     */
    public byte[] getSpecificationBlob(){
        return mSpecificationBlob;
    }

    /**
     * Sets the ItemName field.
     *
     * @param pItemName
     *  String to use to update the field.
     */
    public void setItemName(String pItemName){
        this.mItemName = pItemName;
        setDirty(true);
    }
    /**
     * Retrieves the ItemName field.
     *
     * @return
     *  String containing the ItemName field.
     */
    public String getItemName(){
        return mItemName;
    }

    /**
     * Sets the ModelNumber field.
     *
     * @param pModelNumber
     *  String to use to update the field.
     */
    public void setModelNumber(String pModelNumber){
        this.mModelNumber = pModelNumber;
        setDirty(true);
    }
    /**
     * Retrieves the ModelNumber field.
     *
     * @return
     *  String containing the ModelNumber field.
     */
    public String getModelNumber(){
        return mModelNumber;
    }

    /**
     * Sets the AssocDoc1 field.
     *
     * @param pAssocDoc1
     *  String to use to update the field.
     */
    public void setAssocDoc1(String pAssocDoc1){
        this.mAssocDoc1 = pAssocDoc1;
        setDirty(true);
    }
    /**
     * Retrieves the AssocDoc1 field.
     *
     * @return
     *  String containing the AssocDoc1 field.
     */
    public String getAssocDoc1(){
        return mAssocDoc1;
    }

    /**
     * Sets the AssocDoc1Blob field.
     *
     * @param pAssocDoc1Blob
     *  byte[] to use to update the field.
     */
    public void setAssocDoc1Blob(byte[] pAssocDoc1Blob){
        this.mAssocDoc1Blob = pAssocDoc1Blob;
        setDirty(true);
    }
    /**
     * Retrieves the AssocDoc1Blob field.
     *
     * @return
     *  byte[] containing the AssocDoc1Blob field.
     */
    public byte[] getAssocDoc1Blob(){
        return mAssocDoc1Blob;
    }

    /**
     * Sets the AssocDoc2 field.
     *
     * @param pAssocDoc2
     *  String to use to update the field.
     */
    public void setAssocDoc2(String pAssocDoc2){
        this.mAssocDoc2 = pAssocDoc2;
        setDirty(true);
    }
    /**
     * Retrieves the AssocDoc2 field.
     *
     * @return
     *  String containing the AssocDoc2 field.
     */
    public String getAssocDoc2(){
        return mAssocDoc2;
    }

    /**
     * Sets the AssocDoc2Blob field.
     *
     * @param pAssocDoc2Blob
     *  byte[] to use to update the field.
     */
    public void setAssocDoc2Blob(byte[] pAssocDoc2Blob){
        this.mAssocDoc2Blob = pAssocDoc2Blob;
        setDirty(true);
    }
    /**
     * Retrieves the AssocDoc2Blob field.
     *
     * @return
     *  byte[] containing the AssocDoc2Blob field.
     */
    public byte[] getAssocDoc2Blob(){
        return mAssocDoc2Blob;
    }

    /**
     * Sets the AssocDoc3 field.
     *
     * @param pAssocDoc3
     *  String to use to update the field.
     */
    public void setAssocDoc3(String pAssocDoc3){
        this.mAssocDoc3 = pAssocDoc3;
        setDirty(true);
    }
    /**
     * Retrieves the AssocDoc3 field.
     *
     * @return
     *  String containing the AssocDoc3 field.
     */
    public String getAssocDoc3(){
        return mAssocDoc3;
    }

    /**
     * Sets the AssocDoc3Blob field.
     *
     * @param pAssocDoc3Blob
     *  byte[] to use to update the field.
     */
    public void setAssocDoc3Blob(byte[] pAssocDoc3Blob){
        this.mAssocDoc3Blob = pAssocDoc3Blob;
        setDirty(true);
    }
    /**
     * Retrieves the AssocDoc3Blob field.
     *
     * @return
     *  byte[] containing the AssocDoc3Blob field.
     */
    public byte[] getAssocDoc3Blob(){
        return mAssocDoc3Blob;
    }

    /**
     * Sets the MatchedItemId field.
     *
     * @param pMatchedItemId
     *  int to use to update the field.
     */
    public void setMatchedItemId(int pMatchedItemId){
        this.mMatchedItemId = pMatchedItemId;
        setDirty(true);
    }
    /**
     * Retrieves the MatchedItemId field.
     *
     * @return
     *  int containing the MatchedItemId field.
     */
    public int getMatchedItemId(){
        return mMatchedItemId;
    }
    
}
