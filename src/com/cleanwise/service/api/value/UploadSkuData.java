
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        UploadSkuData
 * Description:  This is a ValueObject class wrapping the database table CLW_UPLOAD_SKU.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.UploadSkuDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;
/**
 * <code>UploadSkuData</code> is a ValueObject class wrapping of the database table CLW_UPLOAD_SKU.
 */
public class UploadSkuData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 4493003839272231669L;
    private int mUploadSkuId;// SQL type:NUMBER, not null
    private int mItemId;// SQL type:NUMBER
    private int mUploadId;// SQL type:NUMBER, not null
    private int mRowNum;// SQL type:NUMBER, not null
    private String mShortDesc;// SQL type:VARCHAR2
    private String mLongDesc;// SQL type:VARCHAR2
    private String mCategory;// SQL type:VARCHAR2
    private String mSkuSize;// SQL type:VARCHAR2
    private String mSkuPack;// SQL type:VARCHAR2
    private String mSkuUom;// SQL type:VARCHAR2
    private String mSkuColor;// SQL type:VARCHAR2
    private int mManufId;// SQL type:NUMBER
    private String mManufName;// SQL type:VARCHAR2
    private String mManufSku;// SQL type:VARCHAR2
    private String mManufPack;// SQL type:VARCHAR2
    private String mManufUom;// SQL type:VARCHAR2
    private int mDistId;// SQL type:NUMBER
    private String mDistName;// SQL type:VARCHAR2
    private String mDistSku;// SQL type:VARCHAR2
    private String mDistUom;// SQL type:VARCHAR2
    private String mDistPack;// SQL type:VARCHAR2
    private String mListPrice;// SQL type:VARCHAR2
    private String mDistCost;// SQL type:VARCHAR2
    private String mSpl;// SQL type:VARCHAR2
    private String mCatalogPrice;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2, not null
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2, not null
    private String mSkuNum;// SQL type:VARCHAR2
    private String mUnspscCode;// SQL type:VARCHAR2
    private String mOtherDesc;// SQL type:VARCHAR2
    private String mNsn;// SQL type:VARCHAR2
    private String mMfcp;// SQL type:VARCHAR2
    private String mPsn;// SQL type:VARCHAR2
    private String mBaseCost;// SQL type:VARCHAR2
    private int mGenManufId;// SQL type:NUMBER
    private String mGenManufName;// SQL type:VARCHAR2
    private String mGenManufSku;// SQL type:VARCHAR2
    private String mDistUomMult;// SQL type:VARCHAR2
    private String mTaxExempt;// SQL type:VARCHAR2
    private String mSpecialPermission;// SQL type:VARCHAR2
    private String mImageUrl;// SQL type:VARCHAR2
    private String mMsdsUrl;// SQL type:VARCHAR2
    private String mDedUrl;// SQL type:VARCHAR2
    private String mProdSpecUrl;// SQL type:VARCHAR2
    private String mGreenCertified;// SQL type:VARCHAR2
    private String mCustomerSkuNum;// SQL type:VARCHAR2
    private String mShipWeight;// SQL type:VARCHAR2
    private String mWeightUnit;// SQL type:VARCHAR2
    private String mCustomerDesc;// SQL type:VARCHAR2
    private String mAdminCategory;// SQL type:VARCHAR2
    private String mThumbnailUrl;// SQL type:VARCHAR2
    private String mServiceFeeCode;// SQL type:VARCHAR2
    
    //bug # 4793: Added to hold the status of the distributor.
    private String mDistStatus;

    /**
     * Constructor.
     */
    public UploadSkuData ()
    {
        mShortDesc = "";
        mLongDesc = "";
        mCategory = "";
        mSkuSize = "";
        mSkuPack = "";
        mSkuUom = "";
        mSkuColor = "";
        mManufName = "";
        mManufSku = "";
        mManufPack = "";
        mManufUom = "";
        mDistName = "";
        mDistSku = "";
        mDistUom = "";
        mDistPack = "";
        mListPrice = "";
        mDistCost = "";
        mSpl = "";
        mCatalogPrice = "";
        mAddBy = "";
        mModBy = "";
        mSkuNum = "";
        mUnspscCode = "";
        mOtherDesc = "";
        mNsn = "";
        mMfcp = "";
        mPsn = "";
        mBaseCost = "";
        mGenManufName = "";
        mGenManufSku = "";
        mDistUomMult = "";
        mTaxExempt = "";
        mSpecialPermission = "";
        mImageUrl = "";
        mMsdsUrl = "";
        mDedUrl = "";
        mProdSpecUrl = "";
        mGreenCertified = "";
        mCustomerSkuNum = "";
        mShipWeight = "";
        mWeightUnit = "";
        mCustomerDesc = "";
        mAdminCategory = "";
        mThumbnailUrl = "";
        mServiceFeeCode = "";
    }

    /**
     * Constructor.
     */
    public UploadSkuData(int parm1, int parm2, int parm3, int parm4, String parm5, String parm6, String parm7, String parm8, String parm9, String parm10, String parm11, int parm12, String parm13, String parm14, String parm15, String parm16, int parm17, String parm18, String parm19, String parm20, String parm21, String parm22, String parm23, String parm24, String parm25, Date parm26, String parm27, Date parm28, String parm29, String parm30, String parm31, String parm32, String parm33, String parm34, String parm35, String parm36, int parm37, String parm38, String parm39, String parm40, String parm41, String parm42, String parm43, String parm44, String parm45, String parm46, String parm47, String parm48, String parm49, String parm50, String parm51, String parm52, String parm53, String parm54)
    {
        mUploadSkuId = parm1;
        mItemId = parm2;
        mUploadId = parm3;
        mRowNum = parm4;
        mShortDesc = parm5;
        mLongDesc = parm6;
        mCategory = parm7;
        mSkuSize = parm8;
        mSkuPack = parm9;
        mSkuUom = parm10;
        mSkuColor = parm11;
        mManufId = parm12;
        mManufName = parm13;
        mManufSku = parm14;
        mManufPack = parm15;
        mManufUom = parm16;
        mDistId = parm17;
        mDistName = parm18;
        mDistSku = parm19;
        mDistUom = parm20;
        mDistPack = parm21;
        mListPrice = parm22;
        mDistCost = parm23;
        mSpl = parm24;
        mCatalogPrice = parm25;
        mAddDate = parm26;
        mAddBy = parm27;
        mModDate = parm28;
        mModBy = parm29;
        mSkuNum = parm30;
        mUnspscCode = parm31;
        mOtherDesc = parm32;
        mNsn = parm33;
        mMfcp = parm34;
        mPsn = parm35;
        mBaseCost = parm36;
        mGenManufId = parm37;
        mGenManufName = parm38;
        mGenManufSku = parm39;
        mDistUomMult = parm40;
        mTaxExempt = parm41;
        mSpecialPermission = parm42;
        mImageUrl = parm43;
        mMsdsUrl = parm44;
        mDedUrl = parm45;
        mProdSpecUrl = parm46;
        mGreenCertified = parm47;
        mCustomerSkuNum = parm48;
        mShipWeight = parm49;
        mWeightUnit = parm50;
        mCustomerDesc = parm51;
        mAdminCategory = parm52;
        mThumbnailUrl = parm53;
        mServiceFeeCode = parm54;
        
    }

    /**
     * Creates a new UploadSkuData
     *
     * @return
     *  Newly initialized UploadSkuData object.
     */
    public static UploadSkuData createValue ()
    {
        UploadSkuData valueData = new UploadSkuData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this UploadSkuData object
     */
    public String toString()
    {
        return "[" + "UploadSkuId=" + mUploadSkuId + ", ItemId=" + mItemId + ", UploadId=" + mUploadId + ", RowNum=" + mRowNum + ", ShortDesc=" + mShortDesc + ", LongDesc=" + mLongDesc + ", Category=" + mCategory + ", SkuSize=" + mSkuSize + ", SkuPack=" + mSkuPack + ", SkuUom=" + mSkuUom + ", SkuColor=" + mSkuColor + ", ManufId=" + mManufId + ", ManufName=" + mManufName + ", ManufSku=" + mManufSku + ", ManufPack=" + mManufPack + ", ManufUom=" + mManufUom + ", DistId=" + mDistId + ", DistName=" + mDistName + ", DistSku=" + mDistSku + ", DistUom=" + mDistUom + ", DistPack=" + mDistPack + ", ListPrice=" + mListPrice + ", DistCost=" + mDistCost + ", Spl=" + mSpl + ", CatalogPrice=" + mCatalogPrice + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", SkuNum=" + mSkuNum + ", UnspscCode=" + mUnspscCode + ", OtherDesc=" + mOtherDesc + ", Nsn=" + mNsn + ", Mfcp=" + mMfcp + ", Psn=" + mPsn + ", BaseCost=" + mBaseCost + ", GenManufId=" + mGenManufId + ", GenManufName=" + mGenManufName + ", GenManufSku=" + mGenManufSku + ", DistUomMult=" + mDistUomMult + ", TaxExempt=" + mTaxExempt + ", SpecialPermission=" + mSpecialPermission + ", ImageUrl=" + mImageUrl + ", MsdsUrl=" + mMsdsUrl + ", DedUrl=" + mDedUrl + ", ProdSpecUrl=" + mProdSpecUrl + ", GreenCertified=" + mGreenCertified + ", CustomerSkuNum=" + mCustomerSkuNum + ", ShipWeight=" + mShipWeight + ", WeightUnit=" + mWeightUnit + ", CustomerDesc=" + mCustomerDesc + ", AdminCategory=" + mAdminCategory + ", ThumbnailUrl=" + mThumbnailUrl + ", ServiceFeeCode=" + mServiceFeeCode + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("UploadSku");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mUploadSkuId));

        node = doc.createElement("ItemId");
        node.appendChild(doc.createTextNode(String.valueOf(mItemId)));
        root.appendChild(node);

        node = doc.createElement("UploadId");
        node.appendChild(doc.createTextNode(String.valueOf(mUploadId)));
        root.appendChild(node);

        node = doc.createElement("RowNum");
        node.appendChild(doc.createTextNode(String.valueOf(mRowNum)));
        root.appendChild(node);

        node = doc.createElement("ShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mShortDesc)));
        root.appendChild(node);

        node = doc.createElement("LongDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mLongDesc)));
        root.appendChild(node);

        node = doc.createElement("Category");
        node.appendChild(doc.createTextNode(String.valueOf(mCategory)));
        root.appendChild(node);

        node = doc.createElement("SkuSize");
        node.appendChild(doc.createTextNode(String.valueOf(mSkuSize)));
        root.appendChild(node);

        node = doc.createElement("SkuPack");
        node.appendChild(doc.createTextNode(String.valueOf(mSkuPack)));
        root.appendChild(node);

        node = doc.createElement("SkuUom");
        node.appendChild(doc.createTextNode(String.valueOf(mSkuUom)));
        root.appendChild(node);

        node = doc.createElement("SkuColor");
        node.appendChild(doc.createTextNode(String.valueOf(mSkuColor)));
        root.appendChild(node);

        node = doc.createElement("ManufId");
        node.appendChild(doc.createTextNode(String.valueOf(mManufId)));
        root.appendChild(node);

        node = doc.createElement("ManufName");
        node.appendChild(doc.createTextNode(String.valueOf(mManufName)));
        root.appendChild(node);

        node = doc.createElement("ManufSku");
        node.appendChild(doc.createTextNode(String.valueOf(mManufSku)));
        root.appendChild(node);

        node = doc.createElement("ManufPack");
        node.appendChild(doc.createTextNode(String.valueOf(mManufPack)));
        root.appendChild(node);

        node = doc.createElement("ManufUom");
        node.appendChild(doc.createTextNode(String.valueOf(mManufUom)));
        root.appendChild(node);

        node = doc.createElement("DistId");
        node.appendChild(doc.createTextNode(String.valueOf(mDistId)));
        root.appendChild(node);

        node = doc.createElement("DistName");
        node.appendChild(doc.createTextNode(String.valueOf(mDistName)));
        root.appendChild(node);

        node = doc.createElement("DistSku");
        node.appendChild(doc.createTextNode(String.valueOf(mDistSku)));
        root.appendChild(node);

        node = doc.createElement("DistUom");
        node.appendChild(doc.createTextNode(String.valueOf(mDistUom)));
        root.appendChild(node);

        node = doc.createElement("DistPack");
        node.appendChild(doc.createTextNode(String.valueOf(mDistPack)));
        root.appendChild(node);

        node = doc.createElement("ListPrice");
        node.appendChild(doc.createTextNode(String.valueOf(mListPrice)));
        root.appendChild(node);

        node = doc.createElement("DistCost");
        node.appendChild(doc.createTextNode(String.valueOf(mDistCost)));
        root.appendChild(node);

        node = doc.createElement("Spl");
        node.appendChild(doc.createTextNode(String.valueOf(mSpl)));
        root.appendChild(node);

        node = doc.createElement("CatalogPrice");
        node.appendChild(doc.createTextNode(String.valueOf(mCatalogPrice)));
        root.appendChild(node);

        node = doc.createElement("AddDate");
        node.appendChild(doc.createTextNode(String.valueOf(mAddDate)));
        root.appendChild(node);

        node = doc.createElement("AddBy");
        node.appendChild(doc.createTextNode(String.valueOf(mAddBy)));
        root.appendChild(node);

        node = doc.createElement("ModDate");
        node.appendChild(doc.createTextNode(String.valueOf(mModDate)));
        root.appendChild(node);

        node = doc.createElement("ModBy");
        node.appendChild(doc.createTextNode(String.valueOf(mModBy)));
        root.appendChild(node);

        node = doc.createElement("SkuNum");
        node.appendChild(doc.createTextNode(String.valueOf(mSkuNum)));
        root.appendChild(node);

        node = doc.createElement("UnspscCode");
        node.appendChild(doc.createTextNode(String.valueOf(mUnspscCode)));
        root.appendChild(node);

        node = doc.createElement("OtherDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mOtherDesc)));
        root.appendChild(node);

        node = doc.createElement("Nsn");
        node.appendChild(doc.createTextNode(String.valueOf(mNsn)));
        root.appendChild(node);

        node = doc.createElement("Mfcp");
        node.appendChild(doc.createTextNode(String.valueOf(mMfcp)));
        root.appendChild(node);

        node = doc.createElement("Psn");
        node.appendChild(doc.createTextNode(String.valueOf(mPsn)));
        root.appendChild(node);

        node = doc.createElement("BaseCost");
        node.appendChild(doc.createTextNode(String.valueOf(mBaseCost)));
        root.appendChild(node);

        node = doc.createElement("GenManufId");
        node.appendChild(doc.createTextNode(String.valueOf(mGenManufId)));
        root.appendChild(node);

        node = doc.createElement("GenManufName");
        node.appendChild(doc.createTextNode(String.valueOf(mGenManufName)));
        root.appendChild(node);

        node = doc.createElement("GenManufSku");
        node.appendChild(doc.createTextNode(String.valueOf(mGenManufSku)));
        root.appendChild(node);

        node = doc.createElement("DistUomMult");
        node.appendChild(doc.createTextNode(String.valueOf(mDistUomMult)));
        root.appendChild(node);

        node = doc.createElement("TaxExempt");
        node.appendChild(doc.createTextNode(String.valueOf(mTaxExempt)));
        root.appendChild(node);

        node = doc.createElement("SpecialPermission");
        node.appendChild(doc.createTextNode(String.valueOf(mSpecialPermission)));
        root.appendChild(node);

        node = doc.createElement("ImageUrl");
        node.appendChild(doc.createTextNode(String.valueOf(mImageUrl)));
        root.appendChild(node);

        node = doc.createElement("MsdsUrl");
        node.appendChild(doc.createTextNode(String.valueOf(mMsdsUrl)));
        root.appendChild(node);

        node = doc.createElement("DedUrl");
        node.appendChild(doc.createTextNode(String.valueOf(mDedUrl)));
        root.appendChild(node);

        node = doc.createElement("ProdSpecUrl");
        node.appendChild(doc.createTextNode(String.valueOf(mProdSpecUrl)));
        root.appendChild(node);

        node = doc.createElement("GreenCertified");
        node.appendChild(doc.createTextNode(String.valueOf(mGreenCertified)));
        root.appendChild(node);

        node = doc.createElement("CustomerSkuNum");
        node.appendChild(doc.createTextNode(String.valueOf(mCustomerSkuNum)));
        root.appendChild(node);

        node = doc.createElement("ShipWeight");
        node.appendChild(doc.createTextNode(String.valueOf(mShipWeight)));
        root.appendChild(node);

        node = doc.createElement("WeightUnit");
        node.appendChild(doc.createTextNode(String.valueOf(mWeightUnit)));
        root.appendChild(node);

        node = doc.createElement("CustomerDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mCustomerDesc)));
        root.appendChild(node);

        node = doc.createElement("AdminCategory");
        node.appendChild(doc.createTextNode(String.valueOf(mAdminCategory)));
        root.appendChild(node);

        node = doc.createElement("ThumbnailUrl");
        node.appendChild(doc.createTextNode(String.valueOf(mThumbnailUrl)));
        root.appendChild(node);

        node = doc.createElement("ServiceFeeCode");
        node.appendChild(doc.createTextNode(String.valueOf(mServiceFeeCode)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the UploadSkuId field is not cloned.
    *
    * @return UploadSkuData object
    */
    public Object clone(){
        UploadSkuData myClone = new UploadSkuData();
        
        myClone.mItemId = mItemId;
        
        myClone.mUploadId = mUploadId;
        
        myClone.mRowNum = mRowNum;
        
        myClone.mShortDesc = mShortDesc;
        
        myClone.mLongDesc = mLongDesc;
        
        myClone.mCategory = mCategory;
        
        myClone.mSkuSize = mSkuSize;
        
        myClone.mSkuPack = mSkuPack;
        
        myClone.mSkuUom = mSkuUom;
        
        myClone.mSkuColor = mSkuColor;
        
        myClone.mManufId = mManufId;
        
        myClone.mManufName = mManufName;
        
        myClone.mManufSku = mManufSku;
        
        myClone.mManufPack = mManufPack;
        
        myClone.mManufUom = mManufUom;
        
        myClone.mDistId = mDistId;
        
        myClone.mDistName = mDistName;
        
        myClone.mDistSku = mDistSku;
        
        myClone.mDistUom = mDistUom;
        
        myClone.mDistPack = mDistPack;
        
        myClone.mListPrice = mListPrice;
        
        myClone.mDistCost = mDistCost;
        
        myClone.mSpl = mSpl;
        
        myClone.mCatalogPrice = mCatalogPrice;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        myClone.mSkuNum = mSkuNum;
        
        myClone.mUnspscCode = mUnspscCode;
        
        myClone.mOtherDesc = mOtherDesc;
        
        myClone.mNsn = mNsn;
        
        myClone.mMfcp = mMfcp;
        
        myClone.mPsn = mPsn;
        
        myClone.mBaseCost = mBaseCost;
        
        myClone.mGenManufId = mGenManufId;
        
        myClone.mGenManufName = mGenManufName;
        
        myClone.mGenManufSku = mGenManufSku;
        
        myClone.mDistUomMult = mDistUomMult;
        
        myClone.mTaxExempt = mTaxExempt;
        
        myClone.mSpecialPermission = mSpecialPermission;
        
        myClone.mImageUrl = mImageUrl;
        
        myClone.mMsdsUrl = mMsdsUrl;
        
        myClone.mDedUrl = mDedUrl;
        
        myClone.mProdSpecUrl = mProdSpecUrl;
        
        myClone.mGreenCertified = mGreenCertified;
        
        myClone.mCustomerSkuNum = mCustomerSkuNum;
        
        myClone.mShipWeight = mShipWeight;
        
        myClone.mWeightUnit = mWeightUnit;
        
        myClone.mCustomerDesc = mCustomerDesc;
        
        myClone.mAdminCategory = mAdminCategory;
        
        myClone.mThumbnailUrl = mThumbnailUrl;
        
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

        if (UploadSkuDataAccess.UPLOAD_SKU_ID.equals(pFieldName)) {
            return getUploadSkuId();
        } else if (UploadSkuDataAccess.ITEM_ID.equals(pFieldName)) {
            return getItemId();
        } else if (UploadSkuDataAccess.UPLOAD_ID.equals(pFieldName)) {
            return getUploadId();
        } else if (UploadSkuDataAccess.ROW_NUM.equals(pFieldName)) {
            return getRowNum();
        } else if (UploadSkuDataAccess.SHORT_DESC.equals(pFieldName)) {
            return getShortDesc();
        } else if (UploadSkuDataAccess.LONG_DESC.equals(pFieldName)) {
            return getLongDesc();
        } else if (UploadSkuDataAccess.CATEGORY.equals(pFieldName)) {
            return getCategory();
        } else if (UploadSkuDataAccess.SKU_SIZE.equals(pFieldName)) {
            return getSkuSize();
        } else if (UploadSkuDataAccess.SKU_PACK.equals(pFieldName)) {
            return getSkuPack();
        } else if (UploadSkuDataAccess.SKU_UOM.equals(pFieldName)) {
            return getSkuUom();
        } else if (UploadSkuDataAccess.SKU_COLOR.equals(pFieldName)) {
            return getSkuColor();
        } else if (UploadSkuDataAccess.MANUF_ID.equals(pFieldName)) {
            return getManufId();
        } else if (UploadSkuDataAccess.MANUF_NAME.equals(pFieldName)) {
            return getManufName();
        } else if (UploadSkuDataAccess.MANUF_SKU.equals(pFieldName)) {
            return getManufSku();
        } else if (UploadSkuDataAccess.MANUF_PACK.equals(pFieldName)) {
            return getManufPack();
        } else if (UploadSkuDataAccess.MANUF_UOM.equals(pFieldName)) {
            return getManufUom();
        } else if (UploadSkuDataAccess.DIST_ID.equals(pFieldName)) {
            return getDistId();
        } else if (UploadSkuDataAccess.DIST_NAME.equals(pFieldName)) {
            return getDistName();
        } else if (UploadSkuDataAccess.DIST_SKU.equals(pFieldName)) {
            return getDistSku();
        } else if (UploadSkuDataAccess.DIST_UOM.equals(pFieldName)) {
            return getDistUom();
        } else if (UploadSkuDataAccess.DIST_PACK.equals(pFieldName)) {
            return getDistPack();
        } else if (UploadSkuDataAccess.LIST_PRICE.equals(pFieldName)) {
            return getListPrice();
        } else if (UploadSkuDataAccess.DIST_COST.equals(pFieldName)) {
            return getDistCost();
        } else if (UploadSkuDataAccess.SPL.equals(pFieldName)) {
            return getSpl();
        } else if (UploadSkuDataAccess.CATALOG_PRICE.equals(pFieldName)) {
            return getCatalogPrice();
        } else if (UploadSkuDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (UploadSkuDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (UploadSkuDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (UploadSkuDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (UploadSkuDataAccess.SKU_NUM.equals(pFieldName)) {
            return getSkuNum();
        } else if (UploadSkuDataAccess.UNSPSC_CODE.equals(pFieldName)) {
            return getUnspscCode();
        } else if (UploadSkuDataAccess.OTHER_DESC.equals(pFieldName)) {
            return getOtherDesc();
        } else if (UploadSkuDataAccess.NSN.equals(pFieldName)) {
            return getNsn();
        } else if (UploadSkuDataAccess.MFCP.equals(pFieldName)) {
            return getMfcp();
        } else if (UploadSkuDataAccess.PSN.equals(pFieldName)) {
            return getPsn();
        } else if (UploadSkuDataAccess.BASE_COST.equals(pFieldName)) {
            return getBaseCost();
        } else if (UploadSkuDataAccess.GEN_MANUF_ID.equals(pFieldName)) {
            return getGenManufId();
        } else if (UploadSkuDataAccess.GEN_MANUF_NAME.equals(pFieldName)) {
            return getGenManufName();
        } else if (UploadSkuDataAccess.GEN_MANUF_SKU.equals(pFieldName)) {
            return getGenManufSku();
        } else if (UploadSkuDataAccess.DIST_UOM_MULT.equals(pFieldName)) {
            return getDistUomMult();
        } else if (UploadSkuDataAccess.TAX_EXEMPT.equals(pFieldName)) {
            return getTaxExempt();
        } else if (UploadSkuDataAccess.SPECIAL_PERMISSION.equals(pFieldName)) {
            return getSpecialPermission();
        } else if (UploadSkuDataAccess.IMAGE_URL.equals(pFieldName)) {
            return getImageUrl();
        } else if (UploadSkuDataAccess.MSDS_URL.equals(pFieldName)) {
            return getMsdsUrl();
        } else if (UploadSkuDataAccess.DED_URL.equals(pFieldName)) {
            return getDedUrl();
        } else if (UploadSkuDataAccess.PROD_SPEC_URL.equals(pFieldName)) {
            return getProdSpecUrl();
        } else if (UploadSkuDataAccess.GREEN_CERTIFIED.equals(pFieldName)) {
            return getGreenCertified();
        } else if (UploadSkuDataAccess.CUSTOMER_SKU_NUM.equals(pFieldName)) {
            return getCustomerSkuNum();
        } else if (UploadSkuDataAccess.SHIP_WEIGHT.equals(pFieldName)) {
            return getShipWeight();
        } else if (UploadSkuDataAccess.WEIGHT_UNIT.equals(pFieldName)) {
            return getWeightUnit();
        } else if (UploadSkuDataAccess.CUSTOMER_DESC.equals(pFieldName)) {
            return getCustomerDesc();
        } else if (UploadSkuDataAccess.ADMIN_CATEGORY.equals(pFieldName)) {
            return getAdminCategory();
        } else if (UploadSkuDataAccess.THUMBNAIL_URL.equals(pFieldName)) {
            return getThumbnailUrl();
        } else if (UploadSkuDataAccess.SERVICE_FEE_CODE.equals(pFieldName)) {
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
        return UploadSkuDataAccess.CLW_UPLOAD_SKU;
    }

    
    /**
     * Sets the UploadSkuId field. This field is required to be set in the database.
     *
     * @param pUploadSkuId
     *  int to use to update the field.
     */
    public void setUploadSkuId(int pUploadSkuId){
        this.mUploadSkuId = pUploadSkuId;
        setDirty(true);
    }
    /**
     * Retrieves the UploadSkuId field.
     *
     * @return
     *  int containing the UploadSkuId field.
     */
    public int getUploadSkuId(){
        return mUploadSkuId;
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
     * Sets the UploadId field. This field is required to be set in the database.
     *
     * @param pUploadId
     *  int to use to update the field.
     */
    public void setUploadId(int pUploadId){
        this.mUploadId = pUploadId;
        setDirty(true);
    }
    /**
     * Retrieves the UploadId field.
     *
     * @return
     *  int containing the UploadId field.
     */
    public int getUploadId(){
        return mUploadId;
    }

    /**
     * Sets the RowNum field. This field is required to be set in the database.
     *
     * @param pRowNum
     *  int to use to update the field.
     */
    public void setRowNum(int pRowNum){
        this.mRowNum = pRowNum;
        setDirty(true);
    }
    /**
     * Retrieves the RowNum field.
     *
     * @return
     *  int containing the RowNum field.
     */
    public int getRowNum(){
        return mRowNum;
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
     * Sets the Category field.
     *
     * @param pCategory
     *  String to use to update the field.
     */
    public void setCategory(String pCategory){
        this.mCategory = pCategory;
        setDirty(true);
    }
    /**
     * Retrieves the Category field.
     *
     * @return
     *  String containing the Category field.
     */
    public String getCategory(){
        return mCategory;
    }

    /**
     * Sets the SkuSize field.
     *
     * @param pSkuSize
     *  String to use to update the field.
     */
    public void setSkuSize(String pSkuSize){
        this.mSkuSize = pSkuSize;
        setDirty(true);
    }
    /**
     * Retrieves the SkuSize field.
     *
     * @return
     *  String containing the SkuSize field.
     */
    public String getSkuSize(){
        return mSkuSize;
    }

    /**
     * Sets the SkuPack field.
     *
     * @param pSkuPack
     *  String to use to update the field.
     */
    public void setSkuPack(String pSkuPack){
        this.mSkuPack = pSkuPack;
        setDirty(true);
    }
    /**
     * Retrieves the SkuPack field.
     *
     * @return
     *  String containing the SkuPack field.
     */
    public String getSkuPack(){
        return mSkuPack;
    }

    /**
     * Sets the SkuUom field.
     *
     * @param pSkuUom
     *  String to use to update the field.
     */
    public void setSkuUom(String pSkuUom){
        this.mSkuUom = pSkuUom;
        setDirty(true);
    }
    /**
     * Retrieves the SkuUom field.
     *
     * @return
     *  String containing the SkuUom field.
     */
    public String getSkuUom(){
        return mSkuUom;
    }

    /**
     * Sets the SkuColor field.
     *
     * @param pSkuColor
     *  String to use to update the field.
     */
    public void setSkuColor(String pSkuColor){
        this.mSkuColor = pSkuColor;
        setDirty(true);
    }
    /**
     * Retrieves the SkuColor field.
     *
     * @return
     *  String containing the SkuColor field.
     */
    public String getSkuColor(){
        return mSkuColor;
    }

    /**
     * Sets the ManufId field.
     *
     * @param pManufId
     *  int to use to update the field.
     */
    public void setManufId(int pManufId){
        this.mManufId = pManufId;
        setDirty(true);
    }
    /**
     * Retrieves the ManufId field.
     *
     * @return
     *  int containing the ManufId field.
     */
    public int getManufId(){
        return mManufId;
    }

    /**
     * Sets the ManufName field.
     *
     * @param pManufName
     *  String to use to update the field.
     */
    public void setManufName(String pManufName){
        this.mManufName = pManufName;
        setDirty(true);
    }
    /**
     * Retrieves the ManufName field.
     *
     * @return
     *  String containing the ManufName field.
     */
    public String getManufName(){
        return mManufName;
    }

    /**
     * Sets the ManufSku field.
     *
     * @param pManufSku
     *  String to use to update the field.
     */
    public void setManufSku(String pManufSku){
        this.mManufSku = pManufSku;
        setDirty(true);
    }
    /**
     * Retrieves the ManufSku field.
     *
     * @return
     *  String containing the ManufSku field.
     */
    public String getManufSku(){
        return mManufSku;
    }

    /**
     * Sets the ManufPack field.
     *
     * @param pManufPack
     *  String to use to update the field.
     */
    public void setManufPack(String pManufPack){
        this.mManufPack = pManufPack;
        setDirty(true);
    }
    /**
     * Retrieves the ManufPack field.
     *
     * @return
     *  String containing the ManufPack field.
     */
    public String getManufPack(){
        return mManufPack;
    }

    /**
     * Sets the ManufUom field.
     *
     * @param pManufUom
     *  String to use to update the field.
     */
    public void setManufUom(String pManufUom){
        this.mManufUom = pManufUom;
        setDirty(true);
    }
    /**
     * Retrieves the ManufUom field.
     *
     * @return
     *  String containing the ManufUom field.
     */
    public String getManufUom(){
        return mManufUom;
    }

    /**
     * Sets the DistId field.
     *
     * @param pDistId
     *  int to use to update the field.
     */
    public void setDistId(int pDistId){
        this.mDistId = pDistId;
        setDirty(true);
    }
    /**
     * Retrieves the DistId field.
     *
     * @return
     *  int containing the DistId field.
     */
    public int getDistId(){
        return mDistId;
    }

    /**
     * Sets the DistName field.
     *
     * @param pDistName
     *  String to use to update the field.
     */
    public void setDistName(String pDistName){
        this.mDistName = pDistName;
        setDirty(true);
    }
    /**
     * Retrieves the DistName field.
     *
     * @return
     *  String containing the DistName field.
     */
    public String getDistName(){
        return mDistName;
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
     * Sets the DistUom field.
     *
     * @param pDistUom
     *  String to use to update the field.
     */
    public void setDistUom(String pDistUom){
        this.mDistUom = pDistUom;
        setDirty(true);
    }
    /**
     * Retrieves the DistUom field.
     *
     * @return
     *  String containing the DistUom field.
     */
    public String getDistUom(){
        return mDistUom;
    }

    /**
     * Sets the DistPack field.
     *
     * @param pDistPack
     *  String to use to update the field.
     */
    public void setDistPack(String pDistPack){
        this.mDistPack = pDistPack;
        setDirty(true);
    }
    /**
     * Retrieves the DistPack field.
     *
     * @return
     *  String containing the DistPack field.
     */
    public String getDistPack(){
        return mDistPack;
    }

    /**
     * Sets the ListPrice field.
     *
     * @param pListPrice
     *  String to use to update the field.
     */
    public void setListPrice(String pListPrice){
        this.mListPrice = pListPrice;
        setDirty(true);
    }
    /**
     * Retrieves the ListPrice field.
     *
     * @return
     *  String containing the ListPrice field.
     */
    public String getListPrice(){
        return mListPrice;
    }

    /**
     * Sets the DistCost field.
     *
     * @param pDistCost
     *  String to use to update the field.
     */
    public void setDistCost(String pDistCost){
        this.mDistCost = pDistCost;
        setDirty(true);
    }
    /**
     * Retrieves the DistCost field.
     *
     * @return
     *  String containing the DistCost field.
     */
    public String getDistCost(){
        return mDistCost;
    }

    /**
     * Sets the Spl field.
     *
     * @param pSpl
     *  String to use to update the field.
     */
    public void setSpl(String pSpl){
        this.mSpl = pSpl;
        setDirty(true);
    }
    /**
     * Retrieves the Spl field.
     *
     * @return
     *  String containing the Spl field.
     */
    public String getSpl(){
        return mSpl;
    }

    /**
     * Sets the CatalogPrice field.
     *
     * @param pCatalogPrice
     *  String to use to update the field.
     */
    public void setCatalogPrice(String pCatalogPrice){
        this.mCatalogPrice = pCatalogPrice;
        setDirty(true);
    }
    /**
     * Retrieves the CatalogPrice field.
     *
     * @return
     *  String containing the CatalogPrice field.
     */
    public String getCatalogPrice(){
        return mCatalogPrice;
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
     * Sets the AddBy field. This field is required to be set in the database.
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
     * Sets the ModBy field. This field is required to be set in the database.
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
     * Sets the SkuNum field.
     *
     * @param pSkuNum
     *  String to use to update the field.
     */
    public void setSkuNum(String pSkuNum){
        this.mSkuNum = pSkuNum;
        setDirty(true);
    }
    /**
     * Retrieves the SkuNum field.
     *
     * @return
     *  String containing the SkuNum field.
     */
    public String getSkuNum(){
        return mSkuNum;
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
     * Sets the OtherDesc field.
     *
     * @param pOtherDesc
     *  String to use to update the field.
     */
    public void setOtherDesc(String pOtherDesc){
        this.mOtherDesc = pOtherDesc;
        setDirty(true);
    }
    /**
     * Retrieves the OtherDesc field.
     *
     * @return
     *  String containing the OtherDesc field.
     */
    public String getOtherDesc(){
        return mOtherDesc;
    }

    /**
     * Sets the Nsn field.
     *
     * @param pNsn
     *  String to use to update the field.
     */
    public void setNsn(String pNsn){
        this.mNsn = pNsn;
        setDirty(true);
    }
    /**
     * Retrieves the Nsn field.
     *
     * @return
     *  String containing the Nsn field.
     */
    public String getNsn(){
        return mNsn;
    }

    /**
     * Sets the Mfcp field.
     *
     * @param pMfcp
     *  String to use to update the field.
     */
    public void setMfcp(String pMfcp){
        this.mMfcp = pMfcp;
        setDirty(true);
    }
    /**
     * Retrieves the Mfcp field.
     *
     * @return
     *  String containing the Mfcp field.
     */
    public String getMfcp(){
        return mMfcp;
    }

    /**
     * Sets the Psn field.
     *
     * @param pPsn
     *  String to use to update the field.
     */
    public void setPsn(String pPsn){
        this.mPsn = pPsn;
        setDirty(true);
    }
    /**
     * Retrieves the Psn field.
     *
     * @return
     *  String containing the Psn field.
     */
    public String getPsn(){
        return mPsn;
    }

    /**
     * Sets the BaseCost field.
     *
     * @param pBaseCost
     *  String to use to update the field.
     */
    public void setBaseCost(String pBaseCost){
        this.mBaseCost = pBaseCost;
        setDirty(true);
    }
    /**
     * Retrieves the BaseCost field.
     *
     * @return
     *  String containing the BaseCost field.
     */
    public String getBaseCost(){
        return mBaseCost;
    }

    /**
     * Sets the GenManufId field.
     *
     * @param pGenManufId
     *  int to use to update the field.
     */
    public void setGenManufId(int pGenManufId){
        this.mGenManufId = pGenManufId;
        setDirty(true);
    }
    /**
     * Retrieves the GenManufId field.
     *
     * @return
     *  int containing the GenManufId field.
     */
    public int getGenManufId(){
        return mGenManufId;
    }

    /**
     * Sets the GenManufName field.
     *
     * @param pGenManufName
     *  String to use to update the field.
     */
    public void setGenManufName(String pGenManufName){
        this.mGenManufName = pGenManufName;
        setDirty(true);
    }
    /**
     * Retrieves the GenManufName field.
     *
     * @return
     *  String containing the GenManufName field.
     */
    public String getGenManufName(){
        return mGenManufName;
    }

    /**
     * Sets the GenManufSku field.
     *
     * @param pGenManufSku
     *  String to use to update the field.
     */
    public void setGenManufSku(String pGenManufSku){
        this.mGenManufSku = pGenManufSku;
        setDirty(true);
    }
    /**
     * Retrieves the GenManufSku field.
     *
     * @return
     *  String containing the GenManufSku field.
     */
    public String getGenManufSku(){
        return mGenManufSku;
    }

    /**
     * Sets the DistUomMult field.
     *
     * @param pDistUomMult
     *  String to use to update the field.
     */
    public void setDistUomMult(String pDistUomMult){
        this.mDistUomMult = pDistUomMult;
        setDirty(true);
    }
    /**
     * Retrieves the DistUomMult field.
     *
     * @return
     *  String containing the DistUomMult field.
     */
    public String getDistUomMult(){
        return mDistUomMult;
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
     * Sets the ImageUrl field.
     *
     * @param pImageUrl
     *  String to use to update the field.
     */
    public void setImageUrl(String pImageUrl){
        this.mImageUrl = pImageUrl;
        setDirty(true);
    }
    /**
     * Retrieves the ImageUrl field.
     *
     * @return
     *  String containing the ImageUrl field.
     */
    public String getImageUrl(){
        return mImageUrl;
    }

    /**
     * Sets the MsdsUrl field.
     *
     * @param pMsdsUrl
     *  String to use to update the field.
     */
    public void setMsdsUrl(String pMsdsUrl){
        this.mMsdsUrl = pMsdsUrl;
        setDirty(true);
    }
    /**
     * Retrieves the MsdsUrl field.
     *
     * @return
     *  String containing the MsdsUrl field.
     */
    public String getMsdsUrl(){
        return mMsdsUrl;
    }

    /**
     * Sets the DedUrl field.
     *
     * @param pDedUrl
     *  String to use to update the field.
     */
    public void setDedUrl(String pDedUrl){
        this.mDedUrl = pDedUrl;
        setDirty(true);
    }
    /**
     * Retrieves the DedUrl field.
     *
     * @return
     *  String containing the DedUrl field.
     */
    public String getDedUrl(){
        return mDedUrl;
    }

    /**
     * Sets the ProdSpecUrl field.
     *
     * @param pProdSpecUrl
     *  String to use to update the field.
     */
    public void setProdSpecUrl(String pProdSpecUrl){
        this.mProdSpecUrl = pProdSpecUrl;
        setDirty(true);
    }
    /**
     * Retrieves the ProdSpecUrl field.
     *
     * @return
     *  String containing the ProdSpecUrl field.
     */
    public String getProdSpecUrl(){
        return mProdSpecUrl;
    }

    /**
     * Sets the GreenCertified field.
     *
     * @param pGreenCertified
     *  String to use to update the field.
     */
    public void setGreenCertified(String pGreenCertified){
        this.mGreenCertified = pGreenCertified;
        setDirty(true);
    }
    /**
     * Retrieves the GreenCertified field.
     *
     * @return
     *  String containing the GreenCertified field.
     */
    public String getGreenCertified(){
        return mGreenCertified;
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
     * Sets the ShipWeight field.
     *
     * @param pShipWeight
     *  String to use to update the field.
     */
    public void setShipWeight(String pShipWeight){
        this.mShipWeight = pShipWeight;
        setDirty(true);
    }
    /**
     * Retrieves the ShipWeight field.
     *
     * @return
     *  String containing the ShipWeight field.
     */
    public String getShipWeight(){
        return mShipWeight;
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
     * Sets the CustomerDesc field.
     *
     * @param pCustomerDesc
     *  String to use to update the field.
     */
    public void setCustomerDesc(String pCustomerDesc){
        this.mCustomerDesc = pCustomerDesc;
        setDirty(true);
    }
    /**
     * Retrieves the CustomerDesc field.
     *
     * @return
     *  String containing the CustomerDesc field.
     */
    public String getCustomerDesc(){
        return mCustomerDesc;
    }

    /**
     * Sets the AdminCategory field.
     *
     * @param pAdminCategory
     *  String to use to update the field.
     */
    public void setAdminCategory(String pAdminCategory){
        this.mAdminCategory = pAdminCategory;
        setDirty(true);
    }
    /**
     * Retrieves the AdminCategory field.
     *
     * @return
     *  String containing the AdminCategory field.
     */
    public String getAdminCategory(){
        return mAdminCategory;
    }

    /**
     * Sets the ThumbnailUrl field.
     *
     * @param pThumbnailUrl
     *  String to use to update the field.
     */
    public void setThumbnailUrl(String pThumbnailUrl){
        this.mThumbnailUrl = pThumbnailUrl;
        setDirty(true);
    }
    /**
     * Retrieves the ThumbnailUrl field.
     *
     * @return
     *  String containing the ThumbnailUrl field.
     */
    public String getThumbnailUrl(){
        return mThumbnailUrl;
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
    
    /**
     * Sets the distributor status.
     * @param pDistStatus
     */
    public void setDistStatus(String pDistStatus){
    	mDistStatus = pDistStatus;
    }
    
    /**
     * Gets the Distributor status.
     * @return mDistStatus
     */
    public String getDistStatus() {
    	return mDistStatus;
    }

}
