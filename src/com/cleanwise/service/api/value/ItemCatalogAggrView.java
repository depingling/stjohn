
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ItemCatalogAggrView
 * Description:  This is a ViewObject class for UI.
 * Purpose:      
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ViewObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import java.lang.*;
import java.util.*;
import java.io.*;
import org.w3c.dom.*;

import java.math.BigDecimal;


/**
 * <code>ItemCatalogAggrView</code> is a ViewObject class for UI.
 */
public class ItemCatalogAggrView
extends ValueObject
{
   
    private static final long serialVersionUID = -5920456356903929902L;
    private boolean mSelectFl;
    private int mAccountId;
    private int mCatalogId;
    private int mContractId;
    private int mOrderGuideId;
    private String mOrderGuideName;
    private String mCatalogName;
    private String mCatalogType;
    private String mCatalogStatus;
    private int mItemId;
    private String mItemName;
    private String mSkuNum;
    private String mSkuSize;
    private String mSkuUom;
    private String mSkuPack;
    private int mManufId;
    private String mManufName;
    private String mManufSku;
    private int mCategoryId;
    private String mCategoryIdInp;
    private String mCategoryName;
    private int mCostCenterId;
    private String mCostCenterIdInp;
    private String mCostCenterName;
    private int mDistId;
    private String mDistIdInp;
    private String mDistName;
    private BigDecimal mCost;
    private String mCostInp;
    private BigDecimal mPrice;
    private String mPriceInp;
    private BigDecimal mBaseCost;
    private String mBaseCostInp;
    private String mCatalogSkuNum;
    private String mCatalogSkuNumInp;
    private String mDistSkuNum;
    private String mDistSkuNumInp;
    private String mDistSkuPack;
    private String mDistSkuPackInp;
    private String mDistSkuUom;
    private String mDistSkuUomInp;
    private BigDecimal mDistConvers;
    private String mDistConversInp;
    private int mGenManufId;
    private String mGenManufIdInp;
    private String mGenManufName;
    private String mGenManufSkuNum;
    private String mGenManufSkuNumInp;
    private boolean mCatalogFl;
    private boolean mCatalogFlInp;
    private boolean mContractFl;
    private boolean mContractFlInp;
    private boolean mOrderGuideFl;
    private boolean mOrderGuideFlInp;
    private boolean mDistSPLFl;
    private boolean mDistSPLFlInp;
    private boolean mTaxExemptFl;
    private boolean mTaxExemptFlInp;
    private boolean mSpecialPermissionFl;
    private boolean mSpecialPermissionFlInp;
    private String mDistErpSkuNum;
    private String mDistErpUom;
    private String mDistErpNum;
    private int mMultiproductId;
    private String mMultiproductName;
    private String mMultiproductIdInp;
    private String mServiceFeeCode;
    private String mServiceFeeCodeInp;
    private String mItemStatusCd;
    private String mDistStatus;

    /**
     * Constructor.
     */
    public ItemCatalogAggrView ()
    {
        mOrderGuideName = "";
        mCatalogName = "";
        mCatalogType = "";
        mCatalogStatus = "";
        mItemName = "";
        mSkuNum = "";
        mSkuSize = "";
        mSkuUom = "";
        mSkuPack = "";
        mManufName = "";
        mManufSku = "";
        mCategoryIdInp = "";
        mCategoryName = "";
        mCostCenterIdInp = "";
        mCostCenterName = "";
        mDistIdInp = "";
        mDistName = "";
        mCostInp = "";
        mPriceInp = "";
        mBaseCostInp = "";
        mCatalogSkuNum = "";
        mCatalogSkuNumInp = "";
        mDistSkuNum = "";
        mDistSkuNumInp = "";
        mDistSkuPack = "";
        mDistSkuPackInp = "";
        mDistSkuUom = "";
        mDistSkuUomInp = "";
        mDistConversInp = "";
        mGenManufIdInp = "";
        mGenManufName = "";
        mGenManufSkuNum = "";
        mGenManufSkuNumInp = "";
        mDistErpSkuNum = "";
        mDistErpUom = "";
        mDistErpNum = "";
        mMultiproductName = "";
        mMultiproductIdInp = "";
        mServiceFeeCode = "";
        mServiceFeeCodeInp = "";
        mItemStatusCd = "";
        mDistStatus = "";
    }

    /**
     * Constructor. 
     */
    public ItemCatalogAggrView(boolean parm1, int parm2, int parm3, int parm4, int parm5, String parm6, String parm7, String parm8, String parm9, int parm10, String parm11, String parm12, String parm13, String parm14, String parm15, int parm16, String parm17, String parm18, int parm19, String parm20, String parm21, int parm22, String parm23, String parm24, int parm25, String parm26, String parm27, BigDecimal parm28, String parm29, BigDecimal parm30, String parm31, BigDecimal parm32, String parm33, String parm34, String parm35, String parm36, String parm37, String parm38, String parm39, String parm40, String parm41, BigDecimal parm42, String parm43, int parm44, String parm45, String parm46, String parm47, String parm48, boolean parm49, boolean parm50, boolean parm51, boolean parm52, boolean parm53, boolean parm54, boolean parm55, boolean parm56, boolean parm57, boolean parm58, boolean parm59, boolean parm60, String parm61, String parm62, String parm63, int parm64, String parm65, String parm66, String parm67, String parm68, String parm69, String parm70)
    {
        mSelectFl = parm1;
        mAccountId = parm2;
        mCatalogId = parm3;
        mContractId = parm4;
        mOrderGuideId = parm5;
        mOrderGuideName = parm6;
        mCatalogName = parm7;
        mCatalogType = parm8;
        mCatalogStatus = parm9;
        mItemId = parm10;
        mItemName = parm11;
        mSkuNum = parm12;
        mSkuSize = parm13;
        mSkuUom = parm14;
        mSkuPack = parm15;
        mManufId = parm16;
        mManufName = parm17;
        mManufSku = parm18;
        mCategoryId = parm19;
        mCategoryIdInp = parm20;
        mCategoryName = parm21;
        mCostCenterId = parm22;
        mCostCenterIdInp = parm23;
        mCostCenterName = parm24;
        mDistId = parm25;
        mDistIdInp = parm26;
        mDistName = parm27;
        mCost = parm28;
        mCostInp = parm29;
        mPrice = parm30;
        mPriceInp = parm31;
        mBaseCost = parm32;
        mBaseCostInp = parm33;
        mCatalogSkuNum = parm34;
        mCatalogSkuNumInp = parm35;
        mDistSkuNum = parm36;
        mDistSkuNumInp = parm37;
        mDistSkuPack = parm38;
        mDistSkuPackInp = parm39;
        mDistSkuUom = parm40;
        mDistSkuUomInp = parm41;
        mDistConvers = parm42;
        mDistConversInp = parm43;
        mGenManufId = parm44;
        mGenManufIdInp = parm45;
        mGenManufName = parm46;
        mGenManufSkuNum = parm47;
        mGenManufSkuNumInp = parm48;
        mCatalogFl = parm49;
        mCatalogFlInp = parm50;
        mContractFl = parm51;
        mContractFlInp = parm52;
        mOrderGuideFl = parm53;
        mOrderGuideFlInp = parm54;
        mDistSPLFl = parm55;
        mDistSPLFlInp = parm56;
        mTaxExemptFl = parm57;
        mTaxExemptFlInp = parm58;
        mSpecialPermissionFl = parm59;
        mSpecialPermissionFlInp = parm60;
        mDistErpSkuNum = parm61;
        mDistErpUom = parm62;
        mDistErpNum = parm63;
        mMultiproductId = parm64;
        mMultiproductName = parm65;
        mMultiproductIdInp = parm66;
        mServiceFeeCode = parm67;
        mServiceFeeCodeInp = parm68;
        mItemStatusCd = parm69;
        mDistStatus = parm70;
        
    }

    /**
     * Creates a new ItemCatalogAggrView
     *
     * @return
     *  Newly initialized ItemCatalogAggrView object.
     */
    public static ItemCatalogAggrView createValue () 
    {
        ItemCatalogAggrView valueView = new ItemCatalogAggrView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ItemCatalogAggrView object
     */
    public String toString()
    {
        return "[" + "SelectFl=" + mSelectFl + ", AccountId=" + mAccountId + ", CatalogId=" + mCatalogId + ", ContractId=" + mContractId + ", OrderGuideId=" + mOrderGuideId + ", OrderGuideName=" + mOrderGuideName + ", CatalogName=" + mCatalogName + ", CatalogType=" + mCatalogType + ", CatalogStatus=" + mCatalogStatus + ", ItemId=" + mItemId + ", ItemName=" + mItemName + ", SkuNum=" + mSkuNum + ", SkuSize=" + mSkuSize + ", SkuUom=" + mSkuUom + ", SkuPack=" + mSkuPack + ", ManufId=" + mManufId + ", ManufName=" + mManufName + ", ManufSku=" + mManufSku + ", CategoryId=" + mCategoryId + ", CategoryIdInp=" + mCategoryIdInp + ", CategoryName=" + mCategoryName + ", CostCenterId=" + mCostCenterId + ", CostCenterIdInp=" + mCostCenterIdInp + ", CostCenterName=" + mCostCenterName + ", DistId=" + mDistId + ", DistIdInp=" + mDistIdInp + ", DistName=" + mDistName + ", Cost=" + mCost + ", CostInp=" + mCostInp + ", Price=" + mPrice + ", PriceInp=" + mPriceInp + ", BaseCost=" + mBaseCost + ", BaseCostInp=" + mBaseCostInp + ", CatalogSkuNum=" + mCatalogSkuNum + ", CatalogSkuNumInp=" + mCatalogSkuNumInp + ", DistSkuNum=" + mDistSkuNum + ", DistSkuNumInp=" + mDistSkuNumInp + ", DistSkuPack=" + mDistSkuPack + ", DistSkuPackInp=" + mDistSkuPackInp + ", DistSkuUom=" + mDistSkuUom + ", DistSkuUomInp=" + mDistSkuUomInp + ", DistConvers=" + mDistConvers + ", DistConversInp=" + mDistConversInp + ", GenManufId=" + mGenManufId + ", GenManufIdInp=" + mGenManufIdInp + ", GenManufName=" + mGenManufName + ", GenManufSkuNum=" + mGenManufSkuNum + ", GenManufSkuNumInp=" + mGenManufSkuNumInp + ", CatalogFl=" + mCatalogFl + ", CatalogFlInp=" + mCatalogFlInp + ", ContractFl=" + mContractFl + ", ContractFlInp=" + mContractFlInp + ", OrderGuideFl=" + mOrderGuideFl + ", OrderGuideFlInp=" + mOrderGuideFlInp + ", DistSPLFl=" + mDistSPLFl + ", DistSPLFlInp=" + mDistSPLFlInp + ", TaxExemptFl=" + mTaxExemptFl + ", TaxExemptFlInp=" + mTaxExemptFlInp + ", SpecialPermissionFl=" + mSpecialPermissionFl + ", SpecialPermissionFlInp=" + mSpecialPermissionFlInp + ", DistErpSkuNum=" + mDistErpSkuNum + ", DistErpUom=" + mDistErpUom + ", DistErpNum=" + mDistErpNum + ", MultiproductId=" + mMultiproductId + ", MultiproductName=" + mMultiproductName + ", MultiproductIdInp=" + mMultiproductIdInp + ", ServiceFeeCode=" + mServiceFeeCode + ", ServiceFeeCodeInp=" + mServiceFeeCodeInp + ", ItemStatusCd=" + mItemStatusCd + ", DistStatus=" + mDistStatus + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("ItemCatalogAggr");
	root.setAttribute("Id", String.valueOf(mSelectFl));

	Element node;

        node = doc.createElement("AccountId");
        node.appendChild(doc.createTextNode(String.valueOf(mAccountId)));
        root.appendChild(node);

        node = doc.createElement("CatalogId");
        node.appendChild(doc.createTextNode(String.valueOf(mCatalogId)));
        root.appendChild(node);

        node = doc.createElement("ContractId");
        node.appendChild(doc.createTextNode(String.valueOf(mContractId)));
        root.appendChild(node);

        node = doc.createElement("OrderGuideId");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderGuideId)));
        root.appendChild(node);

        node = doc.createElement("OrderGuideName");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderGuideName)));
        root.appendChild(node);

        node = doc.createElement("CatalogName");
        node.appendChild(doc.createTextNode(String.valueOf(mCatalogName)));
        root.appendChild(node);

        node = doc.createElement("CatalogType");
        node.appendChild(doc.createTextNode(String.valueOf(mCatalogType)));
        root.appendChild(node);

        node = doc.createElement("CatalogStatus");
        node.appendChild(doc.createTextNode(String.valueOf(mCatalogStatus)));
        root.appendChild(node);

        node = doc.createElement("ItemId");
        node.appendChild(doc.createTextNode(String.valueOf(mItemId)));
        root.appendChild(node);

        node = doc.createElement("ItemName");
        node.appendChild(doc.createTextNode(String.valueOf(mItemName)));
        root.appendChild(node);

        node = doc.createElement("SkuNum");
        node.appendChild(doc.createTextNode(String.valueOf(mSkuNum)));
        root.appendChild(node);

        node = doc.createElement("SkuSize");
        node.appendChild(doc.createTextNode(String.valueOf(mSkuSize)));
        root.appendChild(node);

        node = doc.createElement("SkuUom");
        node.appendChild(doc.createTextNode(String.valueOf(mSkuUom)));
        root.appendChild(node);

        node = doc.createElement("SkuPack");
        node.appendChild(doc.createTextNode(String.valueOf(mSkuPack)));
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

        node = doc.createElement("CategoryId");
        node.appendChild(doc.createTextNode(String.valueOf(mCategoryId)));
        root.appendChild(node);

        node = doc.createElement("CategoryIdInp");
        node.appendChild(doc.createTextNode(String.valueOf(mCategoryIdInp)));
        root.appendChild(node);

        node = doc.createElement("CategoryName");
        node.appendChild(doc.createTextNode(String.valueOf(mCategoryName)));
        root.appendChild(node);

        node = doc.createElement("CostCenterId");
        node.appendChild(doc.createTextNode(String.valueOf(mCostCenterId)));
        root.appendChild(node);

        node = doc.createElement("CostCenterIdInp");
        node.appendChild(doc.createTextNode(String.valueOf(mCostCenterIdInp)));
        root.appendChild(node);

        node = doc.createElement("CostCenterName");
        node.appendChild(doc.createTextNode(String.valueOf(mCostCenterName)));
        root.appendChild(node);

        node = doc.createElement("DistId");
        node.appendChild(doc.createTextNode(String.valueOf(mDistId)));
        root.appendChild(node);

        node = doc.createElement("DistIdInp");
        node.appendChild(doc.createTextNode(String.valueOf(mDistIdInp)));
        root.appendChild(node);

        node = doc.createElement("DistName");
        node.appendChild(doc.createTextNode(String.valueOf(mDistName)));
        root.appendChild(node);

        node = doc.createElement("Cost");
        node.appendChild(doc.createTextNode(String.valueOf(mCost)));
        root.appendChild(node);

        node = doc.createElement("CostInp");
        node.appendChild(doc.createTextNode(String.valueOf(mCostInp)));
        root.appendChild(node);

        node = doc.createElement("Price");
        node.appendChild(doc.createTextNode(String.valueOf(mPrice)));
        root.appendChild(node);

        node = doc.createElement("PriceInp");
        node.appendChild(doc.createTextNode(String.valueOf(mPriceInp)));
        root.appendChild(node);

        node = doc.createElement("BaseCost");
        node.appendChild(doc.createTextNode(String.valueOf(mBaseCost)));
        root.appendChild(node);

        node = doc.createElement("BaseCostInp");
        node.appendChild(doc.createTextNode(String.valueOf(mBaseCostInp)));
        root.appendChild(node);

        node = doc.createElement("CatalogSkuNum");
        node.appendChild(doc.createTextNode(String.valueOf(mCatalogSkuNum)));
        root.appendChild(node);

        node = doc.createElement("CatalogSkuNumInp");
        node.appendChild(doc.createTextNode(String.valueOf(mCatalogSkuNumInp)));
        root.appendChild(node);

        node = doc.createElement("DistSkuNum");
        node.appendChild(doc.createTextNode(String.valueOf(mDistSkuNum)));
        root.appendChild(node);

        node = doc.createElement("DistSkuNumInp");
        node.appendChild(doc.createTextNode(String.valueOf(mDistSkuNumInp)));
        root.appendChild(node);

        node = doc.createElement("DistSkuPack");
        node.appendChild(doc.createTextNode(String.valueOf(mDistSkuPack)));
        root.appendChild(node);

        node = doc.createElement("DistSkuPackInp");
        node.appendChild(doc.createTextNode(String.valueOf(mDistSkuPackInp)));
        root.appendChild(node);

        node = doc.createElement("DistSkuUom");
        node.appendChild(doc.createTextNode(String.valueOf(mDistSkuUom)));
        root.appendChild(node);

        node = doc.createElement("DistSkuUomInp");
        node.appendChild(doc.createTextNode(String.valueOf(mDistSkuUomInp)));
        root.appendChild(node);

        node = doc.createElement("DistConvers");
        node.appendChild(doc.createTextNode(String.valueOf(mDistConvers)));
        root.appendChild(node);

        node = doc.createElement("DistConversInp");
        node.appendChild(doc.createTextNode(String.valueOf(mDistConversInp)));
        root.appendChild(node);

        node = doc.createElement("GenManufId");
        node.appendChild(doc.createTextNode(String.valueOf(mGenManufId)));
        root.appendChild(node);

        node = doc.createElement("GenManufIdInp");
        node.appendChild(doc.createTextNode(String.valueOf(mGenManufIdInp)));
        root.appendChild(node);

        node = doc.createElement("GenManufName");
        node.appendChild(doc.createTextNode(String.valueOf(mGenManufName)));
        root.appendChild(node);

        node = doc.createElement("GenManufSkuNum");
        node.appendChild(doc.createTextNode(String.valueOf(mGenManufSkuNum)));
        root.appendChild(node);

        node = doc.createElement("GenManufSkuNumInp");
        node.appendChild(doc.createTextNode(String.valueOf(mGenManufSkuNumInp)));
        root.appendChild(node);

        node = doc.createElement("CatalogFl");
        node.appendChild(doc.createTextNode(String.valueOf(mCatalogFl)));
        root.appendChild(node);

        node = doc.createElement("CatalogFlInp");
        node.appendChild(doc.createTextNode(String.valueOf(mCatalogFlInp)));
        root.appendChild(node);

        node = doc.createElement("ContractFl");
        node.appendChild(doc.createTextNode(String.valueOf(mContractFl)));
        root.appendChild(node);

        node = doc.createElement("ContractFlInp");
        node.appendChild(doc.createTextNode(String.valueOf(mContractFlInp)));
        root.appendChild(node);

        node = doc.createElement("OrderGuideFl");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderGuideFl)));
        root.appendChild(node);

        node = doc.createElement("OrderGuideFlInp");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderGuideFlInp)));
        root.appendChild(node);

        node = doc.createElement("DistSPLFl");
        node.appendChild(doc.createTextNode(String.valueOf(mDistSPLFl)));
        root.appendChild(node);

        node = doc.createElement("DistSPLFlInp");
        node.appendChild(doc.createTextNode(String.valueOf(mDistSPLFlInp)));
        root.appendChild(node);

        node = doc.createElement("TaxExemptFl");
        node.appendChild(doc.createTextNode(String.valueOf(mTaxExemptFl)));
        root.appendChild(node);

        node = doc.createElement("TaxExemptFlInp");
        node.appendChild(doc.createTextNode(String.valueOf(mTaxExemptFlInp)));
        root.appendChild(node);

        node = doc.createElement("SpecialPermissionFl");
        node.appendChild(doc.createTextNode(String.valueOf(mSpecialPermissionFl)));
        root.appendChild(node);

        node = doc.createElement("SpecialPermissionFlInp");
        node.appendChild(doc.createTextNode(String.valueOf(mSpecialPermissionFlInp)));
        root.appendChild(node);

        node = doc.createElement("DistErpSkuNum");
        node.appendChild(doc.createTextNode(String.valueOf(mDistErpSkuNum)));
        root.appendChild(node);

        node = doc.createElement("DistErpUom");
        node.appendChild(doc.createTextNode(String.valueOf(mDistErpUom)));
        root.appendChild(node);

        node = doc.createElement("DistErpNum");
        node.appendChild(doc.createTextNode(String.valueOf(mDistErpNum)));
        root.appendChild(node);

        node = doc.createElement("MultiproductId");
        node.appendChild(doc.createTextNode(String.valueOf(mMultiproductId)));
        root.appendChild(node);

        node = doc.createElement("MultiproductName");
        node.appendChild(doc.createTextNode(String.valueOf(mMultiproductName)));
        root.appendChild(node);

        node = doc.createElement("MultiproductIdInp");
        node.appendChild(doc.createTextNode(String.valueOf(mMultiproductIdInp)));
        root.appendChild(node);

        node = doc.createElement("ServiceFeeCode");
        node.appendChild(doc.createTextNode(String.valueOf(mServiceFeeCode)));
        root.appendChild(node);

        node = doc.createElement("ServiceFeeCodeInp");
        node.appendChild(doc.createTextNode(String.valueOf(mServiceFeeCodeInp)));
        root.appendChild(node);

        node = doc.createElement("ItemStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mItemStatusCd)));
        root.appendChild(node);

        node = doc.createElement("DistStatus");
        node.appendChild(doc.createTextNode(String.valueOf(mDistStatus)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public ItemCatalogAggrView copy()  {
      ItemCatalogAggrView obj = new ItemCatalogAggrView();
      obj.setSelectFl(mSelectFl);
      obj.setAccountId(mAccountId);
      obj.setCatalogId(mCatalogId);
      obj.setContractId(mContractId);
      obj.setOrderGuideId(mOrderGuideId);
      obj.setOrderGuideName(mOrderGuideName);
      obj.setCatalogName(mCatalogName);
      obj.setCatalogType(mCatalogType);
      obj.setCatalogStatus(mCatalogStatus);
      obj.setItemId(mItemId);
      obj.setItemName(mItemName);
      obj.setSkuNum(mSkuNum);
      obj.setSkuSize(mSkuSize);
      obj.setSkuUom(mSkuUom);
      obj.setSkuPack(mSkuPack);
      obj.setManufId(mManufId);
      obj.setManufName(mManufName);
      obj.setManufSku(mManufSku);
      obj.setCategoryId(mCategoryId);
      obj.setCategoryIdInp(mCategoryIdInp);
      obj.setCategoryName(mCategoryName);
      obj.setCostCenterId(mCostCenterId);
      obj.setCostCenterIdInp(mCostCenterIdInp);
      obj.setCostCenterName(mCostCenterName);
      obj.setDistId(mDistId);
      obj.setDistIdInp(mDistIdInp);
      obj.setDistName(mDistName);
      obj.setCost(mCost);
      obj.setCostInp(mCostInp);
      obj.setPrice(mPrice);
      obj.setPriceInp(mPriceInp);
      obj.setBaseCost(mBaseCost);
      obj.setBaseCostInp(mBaseCostInp);
      obj.setCatalogSkuNum(mCatalogSkuNum);
      obj.setCatalogSkuNumInp(mCatalogSkuNumInp);
      obj.setDistSkuNum(mDistSkuNum);
      obj.setDistSkuNumInp(mDistSkuNumInp);
      obj.setDistSkuPack(mDistSkuPack);
      obj.setDistSkuPackInp(mDistSkuPackInp);
      obj.setDistSkuUom(mDistSkuUom);
      obj.setDistSkuUomInp(mDistSkuUomInp);
      obj.setDistConvers(mDistConvers);
      obj.setDistConversInp(mDistConversInp);
      obj.setGenManufId(mGenManufId);
      obj.setGenManufIdInp(mGenManufIdInp);
      obj.setGenManufName(mGenManufName);
      obj.setGenManufSkuNum(mGenManufSkuNum);
      obj.setGenManufSkuNumInp(mGenManufSkuNumInp);
      obj.setCatalogFl(mCatalogFl);
      obj.setCatalogFlInp(mCatalogFlInp);
      obj.setContractFl(mContractFl);
      obj.setContractFlInp(mContractFlInp);
      obj.setOrderGuideFl(mOrderGuideFl);
      obj.setOrderGuideFlInp(mOrderGuideFlInp);
      obj.setDistSPLFl(mDistSPLFl);
      obj.setDistSPLFlInp(mDistSPLFlInp);
      obj.setTaxExemptFl(mTaxExemptFl);
      obj.setTaxExemptFlInp(mTaxExemptFlInp);
      obj.setSpecialPermissionFl(mSpecialPermissionFl);
      obj.setSpecialPermissionFlInp(mSpecialPermissionFlInp);
      obj.setDistErpSkuNum(mDistErpSkuNum);
      obj.setDistErpUom(mDistErpUom);
      obj.setDistErpNum(mDistErpNum);
      obj.setMultiproductId(mMultiproductId);
      obj.setMultiproductName(mMultiproductName);
      obj.setMultiproductIdInp(mMultiproductIdInp);
      obj.setServiceFeeCode(mServiceFeeCode);
      obj.setServiceFeeCodeInp(mServiceFeeCodeInp);
      obj.setItemStatusCd(mItemStatusCd);
      obj.setDistStatus(mDistStatus);
      
      return obj;
    }

    
    /**
     * Sets the SelectFl property.
     *
     * @param pSelectFl
     *  boolean to use to update the property.
     */
    public void setSelectFl(boolean pSelectFl){
        this.mSelectFl = pSelectFl;
    }
    /**
     * Retrieves the SelectFl property.
     *
     * @return
     *  boolean containing the SelectFl property.
     */
    public boolean getSelectFl(){
        return mSelectFl;
    }


    /**
     * Sets the AccountId property.
     *
     * @param pAccountId
     *  int to use to update the property.
     */
    public void setAccountId(int pAccountId){
        this.mAccountId = pAccountId;
    }
    /**
     * Retrieves the AccountId property.
     *
     * @return
     *  int containing the AccountId property.
     */
    public int getAccountId(){
        return mAccountId;
    }


    /**
     * Sets the CatalogId property.
     *
     * @param pCatalogId
     *  int to use to update the property.
     */
    public void setCatalogId(int pCatalogId){
        this.mCatalogId = pCatalogId;
    }
    /**
     * Retrieves the CatalogId property.
     *
     * @return
     *  int containing the CatalogId property.
     */
    public int getCatalogId(){
        return mCatalogId;
    }


    /**
     * Sets the ContractId property.
     *
     * @param pContractId
     *  int to use to update the property.
     */
    public void setContractId(int pContractId){
        this.mContractId = pContractId;
    }
    /**
     * Retrieves the ContractId property.
     *
     * @return
     *  int containing the ContractId property.
     */
    public int getContractId(){
        return mContractId;
    }


    /**
     * Sets the OrderGuideId property.
     *
     * @param pOrderGuideId
     *  int to use to update the property.
     */
    public void setOrderGuideId(int pOrderGuideId){
        this.mOrderGuideId = pOrderGuideId;
    }
    /**
     * Retrieves the OrderGuideId property.
     *
     * @return
     *  int containing the OrderGuideId property.
     */
    public int getOrderGuideId(){
        return mOrderGuideId;
    }


    /**
     * Sets the OrderGuideName property.
     *
     * @param pOrderGuideName
     *  String to use to update the property.
     */
    public void setOrderGuideName(String pOrderGuideName){
        this.mOrderGuideName = pOrderGuideName;
    }
    /**
     * Retrieves the OrderGuideName property.
     *
     * @return
     *  String containing the OrderGuideName property.
     */
    public String getOrderGuideName(){
        return mOrderGuideName;
    }


    /**
     * Sets the CatalogName property.
     *
     * @param pCatalogName
     *  String to use to update the property.
     */
    public void setCatalogName(String pCatalogName){
        this.mCatalogName = pCatalogName;
    }
    /**
     * Retrieves the CatalogName property.
     *
     * @return
     *  String containing the CatalogName property.
     */
    public String getCatalogName(){
        return mCatalogName;
    }


    /**
     * Sets the CatalogType property.
     *
     * @param pCatalogType
     *  String to use to update the property.
     */
    public void setCatalogType(String pCatalogType){
        this.mCatalogType = pCatalogType;
    }
    /**
     * Retrieves the CatalogType property.
     *
     * @return
     *  String containing the CatalogType property.
     */
    public String getCatalogType(){
        return mCatalogType;
    }


    /**
     * Sets the CatalogStatus property.
     *
     * @param pCatalogStatus
     *  String to use to update the property.
     */
    public void setCatalogStatus(String pCatalogStatus){
        this.mCatalogStatus = pCatalogStatus;
    }
    /**
     * Retrieves the CatalogStatus property.
     *
     * @return
     *  String containing the CatalogStatus property.
     */
    public String getCatalogStatus(){
        return mCatalogStatus;
    }


    /**
     * Sets the ItemId property.
     *
     * @param pItemId
     *  int to use to update the property.
     */
    public void setItemId(int pItemId){
        this.mItemId = pItemId;
    }
    /**
     * Retrieves the ItemId property.
     *
     * @return
     *  int containing the ItemId property.
     */
    public int getItemId(){
        return mItemId;
    }


    /**
     * Sets the ItemName property.
     *
     * @param pItemName
     *  String to use to update the property.
     */
    public void setItemName(String pItemName){
        this.mItemName = pItemName;
    }
    /**
     * Retrieves the ItemName property.
     *
     * @return
     *  String containing the ItemName property.
     */
    public String getItemName(){
        return mItemName;
    }


    /**
     * Sets the SkuNum property.
     *
     * @param pSkuNum
     *  String to use to update the property.
     */
    public void setSkuNum(String pSkuNum){
        this.mSkuNum = pSkuNum;
    }
    /**
     * Retrieves the SkuNum property.
     *
     * @return
     *  String containing the SkuNum property.
     */
    public String getSkuNum(){
        return mSkuNum;
    }


    /**
     * Sets the SkuSize property.
     *
     * @param pSkuSize
     *  String to use to update the property.
     */
    public void setSkuSize(String pSkuSize){
        this.mSkuSize = pSkuSize;
    }
    /**
     * Retrieves the SkuSize property.
     *
     * @return
     *  String containing the SkuSize property.
     */
    public String getSkuSize(){
        return mSkuSize;
    }


    /**
     * Sets the SkuUom property.
     *
     * @param pSkuUom
     *  String to use to update the property.
     */
    public void setSkuUom(String pSkuUom){
        this.mSkuUom = pSkuUom;
    }
    /**
     * Retrieves the SkuUom property.
     *
     * @return
     *  String containing the SkuUom property.
     */
    public String getSkuUom(){
        return mSkuUom;
    }


    /**
     * Sets the SkuPack property.
     *
     * @param pSkuPack
     *  String to use to update the property.
     */
    public void setSkuPack(String pSkuPack){
        this.mSkuPack = pSkuPack;
    }
    /**
     * Retrieves the SkuPack property.
     *
     * @return
     *  String containing the SkuPack property.
     */
    public String getSkuPack(){
        return mSkuPack;
    }


    /**
     * Sets the ManufId property.
     *
     * @param pManufId
     *  int to use to update the property.
     */
    public void setManufId(int pManufId){
        this.mManufId = pManufId;
    }
    /**
     * Retrieves the ManufId property.
     *
     * @return
     *  int containing the ManufId property.
     */
    public int getManufId(){
        return mManufId;
    }


    /**
     * Sets the ManufName property.
     *
     * @param pManufName
     *  String to use to update the property.
     */
    public void setManufName(String pManufName){
        this.mManufName = pManufName;
    }
    /**
     * Retrieves the ManufName property.
     *
     * @return
     *  String containing the ManufName property.
     */
    public String getManufName(){
        return mManufName;
    }


    /**
     * Sets the ManufSku property.
     *
     * @param pManufSku
     *  String to use to update the property.
     */
    public void setManufSku(String pManufSku){
        this.mManufSku = pManufSku;
    }
    /**
     * Retrieves the ManufSku property.
     *
     * @return
     *  String containing the ManufSku property.
     */
    public String getManufSku(){
        return mManufSku;
    }


    /**
     * Sets the CategoryId property.
     *
     * @param pCategoryId
     *  int to use to update the property.
     */
    public void setCategoryId(int pCategoryId){
        this.mCategoryId = pCategoryId;
    }
    /**
     * Retrieves the CategoryId property.
     *
     * @return
     *  int containing the CategoryId property.
     */
    public int getCategoryId(){
        return mCategoryId;
    }


    /**
     * Sets the CategoryIdInp property.
     *
     * @param pCategoryIdInp
     *  String to use to update the property.
     */
    public void setCategoryIdInp(String pCategoryIdInp){
        this.mCategoryIdInp = pCategoryIdInp;
    }
    /**
     * Retrieves the CategoryIdInp property.
     *
     * @return
     *  String containing the CategoryIdInp property.
     */
    public String getCategoryIdInp(){
        return mCategoryIdInp;
    }


    /**
     * Sets the CategoryName property.
     *
     * @param pCategoryName
     *  String to use to update the property.
     */
    public void setCategoryName(String pCategoryName){
        this.mCategoryName = pCategoryName;
    }
    /**
     * Retrieves the CategoryName property.
     *
     * @return
     *  String containing the CategoryName property.
     */
    public String getCategoryName(){
        return mCategoryName;
    }


    /**
     * Sets the CostCenterId property.
     *
     * @param pCostCenterId
     *  int to use to update the property.
     */
    public void setCostCenterId(int pCostCenterId){
        this.mCostCenterId = pCostCenterId;
    }
    /**
     * Retrieves the CostCenterId property.
     *
     * @return
     *  int containing the CostCenterId property.
     */
    public int getCostCenterId(){
        return mCostCenterId;
    }


    /**
     * Sets the CostCenterIdInp property.
     *
     * @param pCostCenterIdInp
     *  String to use to update the property.
     */
    public void setCostCenterIdInp(String pCostCenterIdInp){
        this.mCostCenterIdInp = pCostCenterIdInp;
    }
    /**
     * Retrieves the CostCenterIdInp property.
     *
     * @return
     *  String containing the CostCenterIdInp property.
     */
    public String getCostCenterIdInp(){
        return mCostCenterIdInp;
    }


    /**
     * Sets the CostCenterName property.
     *
     * @param pCostCenterName
     *  String to use to update the property.
     */
    public void setCostCenterName(String pCostCenterName){
        this.mCostCenterName = pCostCenterName;
    }
    /**
     * Retrieves the CostCenterName property.
     *
     * @return
     *  String containing the CostCenterName property.
     */
    public String getCostCenterName(){
        return mCostCenterName;
    }


    /**
     * Sets the DistId property.
     *
     * @param pDistId
     *  int to use to update the property.
     */
    public void setDistId(int pDistId){
        this.mDistId = pDistId;
    }
    /**
     * Retrieves the DistId property.
     *
     * @return
     *  int containing the DistId property.
     */
    public int getDistId(){
        return mDistId;
    }


    /**
     * Sets the DistIdInp property.
     *
     * @param pDistIdInp
     *  String to use to update the property.
     */
    public void setDistIdInp(String pDistIdInp){
        this.mDistIdInp = pDistIdInp;
    }
    /**
     * Retrieves the DistIdInp property.
     *
     * @return
     *  String containing the DistIdInp property.
     */
    public String getDistIdInp(){
        return mDistIdInp;
    }


    /**
     * Sets the DistName property.
     *
     * @param pDistName
     *  String to use to update the property.
     */
    public void setDistName(String pDistName){
        this.mDistName = pDistName;
    }
    /**
     * Retrieves the DistName property.
     *
     * @return
     *  String containing the DistName property.
     */
    public String getDistName(){
        return mDistName;
    }


    /**
     * Sets the Cost property.
     *
     * @param pCost
     *  BigDecimal to use to update the property.
     */
    public void setCost(BigDecimal pCost){
        this.mCost = pCost;
    }
    /**
     * Retrieves the Cost property.
     *
     * @return
     *  BigDecimal containing the Cost property.
     */
    public BigDecimal getCost(){
        return mCost;
    }


    /**
     * Sets the CostInp property.
     *
     * @param pCostInp
     *  String to use to update the property.
     */
    public void setCostInp(String pCostInp){
        this.mCostInp = pCostInp;
    }
    /**
     * Retrieves the CostInp property.
     *
     * @return
     *  String containing the CostInp property.
     */
    public String getCostInp(){
        return mCostInp;
    }


    /**
     * Sets the Price property.
     *
     * @param pPrice
     *  BigDecimal to use to update the property.
     */
    public void setPrice(BigDecimal pPrice){
        this.mPrice = pPrice;
    }
    /**
     * Retrieves the Price property.
     *
     * @return
     *  BigDecimal containing the Price property.
     */
    public BigDecimal getPrice(){
        return mPrice;
    }


    /**
     * Sets the PriceInp property.
     *
     * @param pPriceInp
     *  String to use to update the property.
     */
    public void setPriceInp(String pPriceInp){
        this.mPriceInp = pPriceInp;
    }
    /**
     * Retrieves the PriceInp property.
     *
     * @return
     *  String containing the PriceInp property.
     */
    public String getPriceInp(){
        return mPriceInp;
    }


    /**
     * Sets the BaseCost property.
     *
     * @param pBaseCost
     *  BigDecimal to use to update the property.
     */
    public void setBaseCost(BigDecimal pBaseCost){
        this.mBaseCost = pBaseCost;
    }
    /**
     * Retrieves the BaseCost property.
     *
     * @return
     *  BigDecimal containing the BaseCost property.
     */
    public BigDecimal getBaseCost(){
        return mBaseCost;
    }


    /**
     * Sets the BaseCostInp property.
     *
     * @param pBaseCostInp
     *  String to use to update the property.
     */
    public void setBaseCostInp(String pBaseCostInp){
        this.mBaseCostInp = pBaseCostInp;
    }
    /**
     * Retrieves the BaseCostInp property.
     *
     * @return
     *  String containing the BaseCostInp property.
     */
    public String getBaseCostInp(){
        return mBaseCostInp;
    }


    /**
     * Sets the CatalogSkuNum property.
     *
     * @param pCatalogSkuNum
     *  String to use to update the property.
     */
    public void setCatalogSkuNum(String pCatalogSkuNum){
        this.mCatalogSkuNum = pCatalogSkuNum;
    }
    /**
     * Retrieves the CatalogSkuNum property.
     *
     * @return
     *  String containing the CatalogSkuNum property.
     */
    public String getCatalogSkuNum(){
        return mCatalogSkuNum;
    }


    /**
     * Sets the CatalogSkuNumInp property.
     *
     * @param pCatalogSkuNumInp
     *  String to use to update the property.
     */
    public void setCatalogSkuNumInp(String pCatalogSkuNumInp){
        this.mCatalogSkuNumInp = pCatalogSkuNumInp;
    }
    /**
     * Retrieves the CatalogSkuNumInp property.
     *
     * @return
     *  String containing the CatalogSkuNumInp property.
     */
    public String getCatalogSkuNumInp(){
        return mCatalogSkuNumInp;
    }


    /**
     * Sets the DistSkuNum property.
     *
     * @param pDistSkuNum
     *  String to use to update the property.
     */
    public void setDistSkuNum(String pDistSkuNum){
        this.mDistSkuNum = pDistSkuNum;
    }
    /**
     * Retrieves the DistSkuNum property.
     *
     * @return
     *  String containing the DistSkuNum property.
     */
    public String getDistSkuNum(){
        return mDistSkuNum;
    }


    /**
     * Sets the DistSkuNumInp property.
     *
     * @param pDistSkuNumInp
     *  String to use to update the property.
     */
    public void setDistSkuNumInp(String pDistSkuNumInp){
        this.mDistSkuNumInp = pDistSkuNumInp;
    }
    /**
     * Retrieves the DistSkuNumInp property.
     *
     * @return
     *  String containing the DistSkuNumInp property.
     */
    public String getDistSkuNumInp(){
        return mDistSkuNumInp;
    }


    /**
     * Sets the DistSkuPack property.
     *
     * @param pDistSkuPack
     *  String to use to update the property.
     */
    public void setDistSkuPack(String pDistSkuPack){
        this.mDistSkuPack = pDistSkuPack;
    }
    /**
     * Retrieves the DistSkuPack property.
     *
     * @return
     *  String containing the DistSkuPack property.
     */
    public String getDistSkuPack(){
        return mDistSkuPack;
    }


    /**
     * Sets the DistSkuPackInp property.
     *
     * @param pDistSkuPackInp
     *  String to use to update the property.
     */
    public void setDistSkuPackInp(String pDistSkuPackInp){
        this.mDistSkuPackInp = pDistSkuPackInp;
    }
    /**
     * Retrieves the DistSkuPackInp property.
     *
     * @return
     *  String containing the DistSkuPackInp property.
     */
    public String getDistSkuPackInp(){
        return mDistSkuPackInp;
    }


    /**
     * Sets the DistSkuUom property.
     *
     * @param pDistSkuUom
     *  String to use to update the property.
     */
    public void setDistSkuUom(String pDistSkuUom){
        this.mDistSkuUom = pDistSkuUom;
    }
    /**
     * Retrieves the DistSkuUom property.
     *
     * @return
     *  String containing the DistSkuUom property.
     */
    public String getDistSkuUom(){
        return mDistSkuUom;
    }


    /**
     * Sets the DistSkuUomInp property.
     *
     * @param pDistSkuUomInp
     *  String to use to update the property.
     */
    public void setDistSkuUomInp(String pDistSkuUomInp){
        this.mDistSkuUomInp = pDistSkuUomInp;
    }
    /**
     * Retrieves the DistSkuUomInp property.
     *
     * @return
     *  String containing the DistSkuUomInp property.
     */
    public String getDistSkuUomInp(){
        return mDistSkuUomInp;
    }


    /**
     * Sets the DistConvers property.
     *
     * @param pDistConvers
     *  BigDecimal to use to update the property.
     */
    public void setDistConvers(BigDecimal pDistConvers){
        this.mDistConvers = pDistConvers;
    }
    /**
     * Retrieves the DistConvers property.
     *
     * @return
     *  BigDecimal containing the DistConvers property.
     */
    public BigDecimal getDistConvers(){
        return mDistConvers;
    }


    /**
     * Sets the DistConversInp property.
     *
     * @param pDistConversInp
     *  String to use to update the property.
     */
    public void setDistConversInp(String pDistConversInp){
        this.mDistConversInp = pDistConversInp;
    }
    /**
     * Retrieves the DistConversInp property.
     *
     * @return
     *  String containing the DistConversInp property.
     */
    public String getDistConversInp(){
        return mDistConversInp;
    }


    /**
     * Sets the GenManufId property.
     *
     * @param pGenManufId
     *  int to use to update the property.
     */
    public void setGenManufId(int pGenManufId){
        this.mGenManufId = pGenManufId;
    }
    /**
     * Retrieves the GenManufId property.
     *
     * @return
     *  int containing the GenManufId property.
     */
    public int getGenManufId(){
        return mGenManufId;
    }


    /**
     * Sets the GenManufIdInp property.
     *
     * @param pGenManufIdInp
     *  String to use to update the property.
     */
    public void setGenManufIdInp(String pGenManufIdInp){
        this.mGenManufIdInp = pGenManufIdInp;
    }
    /**
     * Retrieves the GenManufIdInp property.
     *
     * @return
     *  String containing the GenManufIdInp property.
     */
    public String getGenManufIdInp(){
        return mGenManufIdInp;
    }


    /**
     * Sets the GenManufName property.
     *
     * @param pGenManufName
     *  String to use to update the property.
     */
    public void setGenManufName(String pGenManufName){
        this.mGenManufName = pGenManufName;
    }
    /**
     * Retrieves the GenManufName property.
     *
     * @return
     *  String containing the GenManufName property.
     */
    public String getGenManufName(){
        return mGenManufName;
    }


    /**
     * Sets the GenManufSkuNum property.
     *
     * @param pGenManufSkuNum
     *  String to use to update the property.
     */
    public void setGenManufSkuNum(String pGenManufSkuNum){
        this.mGenManufSkuNum = pGenManufSkuNum;
    }
    /**
     * Retrieves the GenManufSkuNum property.
     *
     * @return
     *  String containing the GenManufSkuNum property.
     */
    public String getGenManufSkuNum(){
        return mGenManufSkuNum;
    }


    /**
     * Sets the GenManufSkuNumInp property.
     *
     * @param pGenManufSkuNumInp
     *  String to use to update the property.
     */
    public void setGenManufSkuNumInp(String pGenManufSkuNumInp){
        this.mGenManufSkuNumInp = pGenManufSkuNumInp;
    }
    /**
     * Retrieves the GenManufSkuNumInp property.
     *
     * @return
     *  String containing the GenManufSkuNumInp property.
     */
    public String getGenManufSkuNumInp(){
        return mGenManufSkuNumInp;
    }


    /**
     * Sets the CatalogFl property.
     *
     * @param pCatalogFl
     *  boolean to use to update the property.
     */
    public void setCatalogFl(boolean pCatalogFl){
        this.mCatalogFl = pCatalogFl;
    }
    /**
     * Retrieves the CatalogFl property.
     *
     * @return
     *  boolean containing the CatalogFl property.
     */
    public boolean getCatalogFl(){
        return mCatalogFl;
    }


    /**
     * Sets the CatalogFlInp property.
     *
     * @param pCatalogFlInp
     *  boolean to use to update the property.
     */
    public void setCatalogFlInp(boolean pCatalogFlInp){
        this.mCatalogFlInp = pCatalogFlInp;
    }
    /**
     * Retrieves the CatalogFlInp property.
     *
     * @return
     *  boolean containing the CatalogFlInp property.
     */
    public boolean getCatalogFlInp(){
        return mCatalogFlInp;
    }


    /**
     * Sets the ContractFl property.
     *
     * @param pContractFl
     *  boolean to use to update the property.
     */
    public void setContractFl(boolean pContractFl){
        this.mContractFl = pContractFl;
    }
    /**
     * Retrieves the ContractFl property.
     *
     * @return
     *  boolean containing the ContractFl property.
     */
    public boolean getContractFl(){
        return mContractFl;
    }


    /**
     * Sets the ContractFlInp property.
     *
     * @param pContractFlInp
     *  boolean to use to update the property.
     */
    public void setContractFlInp(boolean pContractFlInp){
        this.mContractFlInp = pContractFlInp;
    }
    /**
     * Retrieves the ContractFlInp property.
     *
     * @return
     *  boolean containing the ContractFlInp property.
     */
    public boolean getContractFlInp(){
        return mContractFlInp;
    }


    /**
     * Sets the OrderGuideFl property.
     *
     * @param pOrderGuideFl
     *  boolean to use to update the property.
     */
    public void setOrderGuideFl(boolean pOrderGuideFl){
        this.mOrderGuideFl = pOrderGuideFl;
    }
    /**
     * Retrieves the OrderGuideFl property.
     *
     * @return
     *  boolean containing the OrderGuideFl property.
     */
    public boolean getOrderGuideFl(){
        return mOrderGuideFl;
    }


    /**
     * Sets the OrderGuideFlInp property.
     *
     * @param pOrderGuideFlInp
     *  boolean to use to update the property.
     */
    public void setOrderGuideFlInp(boolean pOrderGuideFlInp){
        this.mOrderGuideFlInp = pOrderGuideFlInp;
    }
    /**
     * Retrieves the OrderGuideFlInp property.
     *
     * @return
     *  boolean containing the OrderGuideFlInp property.
     */
    public boolean getOrderGuideFlInp(){
        return mOrderGuideFlInp;
    }


    /**
     * Sets the DistSPLFl property.
     *
     * @param pDistSPLFl
     *  boolean to use to update the property.
     */
    public void setDistSPLFl(boolean pDistSPLFl){
        this.mDistSPLFl = pDistSPLFl;
    }
    /**
     * Retrieves the DistSPLFl property.
     *
     * @return
     *  boolean containing the DistSPLFl property.
     */
    public boolean getDistSPLFl(){
        return mDistSPLFl;
    }


    /**
     * Sets the DistSPLFlInp property.
     *
     * @param pDistSPLFlInp
     *  boolean to use to update the property.
     */
    public void setDistSPLFlInp(boolean pDistSPLFlInp){
        this.mDistSPLFlInp = pDistSPLFlInp;
    }
    /**
     * Retrieves the DistSPLFlInp property.
     *
     * @return
     *  boolean containing the DistSPLFlInp property.
     */
    public boolean getDistSPLFlInp(){
        return mDistSPLFlInp;
    }


    /**
     * Sets the TaxExemptFl property.
     *
     * @param pTaxExemptFl
     *  boolean to use to update the property.
     */
    public void setTaxExemptFl(boolean pTaxExemptFl){
        this.mTaxExemptFl = pTaxExemptFl;
    }
    /**
     * Retrieves the TaxExemptFl property.
     *
     * @return
     *  boolean containing the TaxExemptFl property.
     */
    public boolean getTaxExemptFl(){
        return mTaxExemptFl;
    }


    /**
     * Sets the TaxExemptFlInp property.
     *
     * @param pTaxExemptFlInp
     *  boolean to use to update the property.
     */
    public void setTaxExemptFlInp(boolean pTaxExemptFlInp){
        this.mTaxExemptFlInp = pTaxExemptFlInp;
    }
    /**
     * Retrieves the TaxExemptFlInp property.
     *
     * @return
     *  boolean containing the TaxExemptFlInp property.
     */
    public boolean getTaxExemptFlInp(){
        return mTaxExemptFlInp;
    }


    /**
     * Sets the SpecialPermissionFl property.
     *
     * @param pSpecialPermissionFl
     *  boolean to use to update the property.
     */
    public void setSpecialPermissionFl(boolean pSpecialPermissionFl){
        this.mSpecialPermissionFl = pSpecialPermissionFl;
    }
    /**
     * Retrieves the SpecialPermissionFl property.
     *
     * @return
     *  boolean containing the SpecialPermissionFl property.
     */
    public boolean getSpecialPermissionFl(){
        return mSpecialPermissionFl;
    }


    /**
     * Sets the SpecialPermissionFlInp property.
     *
     * @param pSpecialPermissionFlInp
     *  boolean to use to update the property.
     */
    public void setSpecialPermissionFlInp(boolean pSpecialPermissionFlInp){
        this.mSpecialPermissionFlInp = pSpecialPermissionFlInp;
    }
    /**
     * Retrieves the SpecialPermissionFlInp property.
     *
     * @return
     *  boolean containing the SpecialPermissionFlInp property.
     */
    public boolean getSpecialPermissionFlInp(){
        return mSpecialPermissionFlInp;
    }


    /**
     * Sets the DistErpSkuNum property.
     *
     * @param pDistErpSkuNum
     *  String to use to update the property.
     */
    public void setDistErpSkuNum(String pDistErpSkuNum){
        this.mDistErpSkuNum = pDistErpSkuNum;
    }
    /**
     * Retrieves the DistErpSkuNum property.
     *
     * @return
     *  String containing the DistErpSkuNum property.
     */
    public String getDistErpSkuNum(){
        return mDistErpSkuNum;
    }


    /**
     * Sets the DistErpUom property.
     *
     * @param pDistErpUom
     *  String to use to update the property.
     */
    public void setDistErpUom(String pDistErpUom){
        this.mDistErpUom = pDistErpUom;
    }
    /**
     * Retrieves the DistErpUom property.
     *
     * @return
     *  String containing the DistErpUom property.
     */
    public String getDistErpUom(){
        return mDistErpUom;
    }


    /**
     * Sets the DistErpNum property.
     *
     * @param pDistErpNum
     *  String to use to update the property.
     */
    public void setDistErpNum(String pDistErpNum){
        this.mDistErpNum = pDistErpNum;
    }
    /**
     * Retrieves the DistErpNum property.
     *
     * @return
     *  String containing the DistErpNum property.
     */
    public String getDistErpNum(){
        return mDistErpNum;
    }


    /**
     * Sets the MultiproductId property.
     *
     * @param pMultiproductId
     *  int to use to update the property.
     */
    public void setMultiproductId(int pMultiproductId){
        this.mMultiproductId = pMultiproductId;
    }
    /**
     * Retrieves the MultiproductId property.
     *
     * @return
     *  int containing the MultiproductId property.
     */
    public int getMultiproductId(){
        return mMultiproductId;
    }


    /**
     * Sets the MultiproductName property.
     *
     * @param pMultiproductName
     *  String to use to update the property.
     */
    public void setMultiproductName(String pMultiproductName){
        this.mMultiproductName = pMultiproductName;
    }
    /**
     * Retrieves the MultiproductName property.
     *
     * @return
     *  String containing the MultiproductName property.
     */
    public String getMultiproductName(){
        return mMultiproductName;
    }


    /**
     * Sets the MultiproductIdInp property.
     *
     * @param pMultiproductIdInp
     *  String to use to update the property.
     */
    public void setMultiproductIdInp(String pMultiproductIdInp){
        this.mMultiproductIdInp = pMultiproductIdInp;
    }
    /**
     * Retrieves the MultiproductIdInp property.
     *
     * @return
     *  String containing the MultiproductIdInp property.
     */
    public String getMultiproductIdInp(){
        return mMultiproductIdInp;
    }


    /**
     * Sets the ServiceFeeCode property.
     *
     * @param pServiceFeeCode
     *  String to use to update the property.
     */
    public void setServiceFeeCode(String pServiceFeeCode){
        this.mServiceFeeCode = pServiceFeeCode;
    }
    /**
     * Retrieves the ServiceFeeCode property.
     *
     * @return
     *  String containing the ServiceFeeCode property.
     */
    public String getServiceFeeCode(){
        return mServiceFeeCode;
    }


    /**
     * Sets the ServiceFeeCodeInp property.
     *
     * @param pServiceFeeCodeInp
     *  String to use to update the property.
     */
    public void setServiceFeeCodeInp(String pServiceFeeCodeInp){
        this.mServiceFeeCodeInp = pServiceFeeCodeInp;
    }
    /**
     * Retrieves the ServiceFeeCodeInp property.
     *
     * @return
     *  String containing the ServiceFeeCodeInp property.
     */
    public String getServiceFeeCodeInp(){
        return mServiceFeeCodeInp;
    }


    /**
     * Sets the ItemStatusCd property.
     *
     * @param pItemStatusCd
     *  String to use to update the property.
     */
    public void setItemStatusCd(String pItemStatusCd){
        this.mItemStatusCd = pItemStatusCd;
    }
    /**
     * Retrieves the ItemStatusCd property.
     *
     * @return
     *  String containing the ItemStatusCd property.
     */
    public String getItemStatusCd(){
        return mItemStatusCd;
    }


    /**
     * Sets the DistStatus property.
     *
     * @param pDistStatus
     *  String to use to update the property.
     */
    public void setDistStatus(String pDistStatus){
        this.mDistStatus = pDistStatus;
    }
    /**
     * Retrieves the DistStatus property.
     *
     * @return
     *  String containing the DistStatus property.
     */
    public String getDistStatus(){
        return mDistStatus;
    }


    
}
