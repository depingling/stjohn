
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        OrderItemData
 * Description:  This is a ValueObject class wrapping the database table CLW_ORDER_ITEM.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.OrderItemDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>OrderItemData</code> is a ValueObject class wrapping of the database table CLW_ORDER_ITEM.
 */
public class OrderItemData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -4770047734411762745L;
    private int mOrderId;// SQL type:NUMBER, not null
    private int mOrderItemId;// SQL type:NUMBER, not null
    private String mOrderItemStatusCd;// SQL type:VARCHAR2
    private int mItemId;// SQL type:NUMBER
    private String mExceptionInd;// SQL type:VARCHAR2
    private int mOrderLineNum;// SQL type:NUMBER
    private int mCustLineNum;// SQL type:NUMBER
    private int mErpPoLineNum;// SQL type:NUMBER
    private int mErpOrderLineNum;// SQL type:NUMBER
    private String mErpOrderNum;// SQL type:VARCHAR2
    private String mErpPoNum;// SQL type:VARCHAR2
    private Date mErpPoDate;// SQL type:DATE
    private Date mErpPoTime;// SQL type:DATE
    private int mItemSkuNum;// SQL type:NUMBER
    private String mItemShortDesc;// SQL type:VARCHAR2
    private String mItemUom;// SQL type:VARCHAR2
    private String mItemPack;// SQL type:VARCHAR2
    private String mItemSize;// SQL type:VARCHAR2
    private java.math.BigDecimal mItemCost;// SQL type:NUMBER
    private String mCustItemSkuNum;// SQL type:VARCHAR2
    private String mCustItemShortDesc;// SQL type:VARCHAR2
    private String mCustItemUom;// SQL type:VARCHAR2
    private String mCustItemPack;// SQL type:VARCHAR2
    private java.math.BigDecimal mCustContractPrice;// SQL type:NUMBER
    private int mCustContractId;// SQL type:NUMBER
    private String mCustContractShortDesc;// SQL type:VARCHAR2
    private String mManuItemSkuNum;// SQL type:VARCHAR2
    private String mManuItemShortDesc;// SQL type:VARCHAR2
    private java.math.BigDecimal mManuItemMsrp;// SQL type:NUMBER
    private String mManuItemUpcNum;// SQL type:VARCHAR2
    private String mManuPackUpcNum;// SQL type:VARCHAR2
    private String mDistItemSkuNum;// SQL type:VARCHAR2
    private String mDistItemShortDesc;// SQL type:VARCHAR2
    private String mDistItemUom;// SQL type:VARCHAR2
    private String mDistItemPack;// SQL type:VARCHAR2
    private java.math.BigDecimal mDistItemCost;// SQL type:NUMBER
    private String mDistOrderNum;// SQL type:VARCHAR2
    private String mDistErpNum;// SQL type:VARCHAR2
    private int mDistItemQuantity;// SQL type:NUMBER
    private int mTotalQuantityOrdered;// SQL type:NUMBER
    private int mTotalQuantityShipped;// SQL type:NUMBER
    private String mComments;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE
    private String mModBy;// SQL type:VARCHAR2
    private String mAckStatusCd;// SQL type:VARCHAR2
    private int mPurchaseOrderId;// SQL type:NUMBER
    private Date mTargetShipDate;// SQL type:DATE
    private int mQuantityConfirmed;// SQL type:NUMBER
    private int mQuantityBackordered;// SQL type:NUMBER
    private int mCostCenterId;// SQL type:NUMBER
    private int mOrderRoutingId;// SQL type:NUMBER
    private int mItemQty855;// SQL type:NUMBER
    private int mFreightHandlerId;// SQL type:NUMBER
    private int mInventoryParValue;// SQL type:NUMBER
    private String mInventoryQtyOnHand;// SQL type:CHAR
    private String mSaleTypeCd;// SQL type:VARCHAR2
    private java.math.BigDecimal mDistBaseCost;// SQL type:NUMBER
    private java.math.BigDecimal mDistUomConvMultiplier;// SQL type:NUMBER
    private java.math.BigDecimal mDistUomConvCost;// SQL type:NUMBER
    private int mTotalQuantityReceived;// SQL type:NUMBER
    private Date mErpPoRefDate;// SQL type:DATE
    private String mErpPoRefNum;// SQL type:VARCHAR2
    private int mErpPoRefLineNum;// SQL type:NUMBER
    private int mAssetId;// SQL type:NUMBER
    private java.math.BigDecimal mTaxRate;// SQL type:NUMBER
    private java.math.BigDecimal mTaxAmount;// SQL type:NUMBER
    private String mTaxExempt;// SQL type:VARCHAR2
    private String mOutboundPoNum;// SQL type:VARCHAR2
    private java.math.BigDecimal mServiceFee;// SQL type:NUMBER

    /**
     * Constructor.
     */
    public OrderItemData ()
    {
        mOrderItemStatusCd = "";
        mExceptionInd = "";
        mErpOrderNum = "";
        mErpPoNum = "";
        mItemShortDesc = "";
        mItemUom = "";
        mItemPack = "";
        mItemSize = "";
        mCustItemSkuNum = "";
        mCustItemShortDesc = "";
        mCustItemUom = "";
        mCustItemPack = "";
        mCustContractShortDesc = "";
        mManuItemSkuNum = "";
        mManuItemShortDesc = "";
        mManuItemUpcNum = "";
        mManuPackUpcNum = "";
        mDistItemSkuNum = "";
        mDistItemShortDesc = "";
        mDistItemUom = "";
        mDistItemPack = "";
        mDistOrderNum = "";
        mDistErpNum = "";
        mComments = "";
        mAddBy = "";
        mModBy = "";
        mAckStatusCd = "";
        mInventoryQtyOnHand = "";
        mSaleTypeCd = "";
        mErpPoRefNum = "";
        mTaxExempt = "";
        mOutboundPoNum = "";
    }

    /**
     * Constructor.
     */
    public OrderItemData(int parm1, int parm2, String parm3, int parm4, String parm5, int parm6, int parm7, int parm8, int parm9, String parm10, String parm11, Date parm12, Date parm13, int parm14, String parm15, String parm16, String parm17, String parm18, java.math.BigDecimal parm19, String parm20, String parm21, String parm22, String parm23, java.math.BigDecimal parm24, int parm25, String parm26, String parm27, String parm28, java.math.BigDecimal parm29, String parm30, String parm31, String parm32, String parm33, String parm34, String parm35, java.math.BigDecimal parm36, String parm37, String parm38, int parm39, int parm40, int parm41, String parm42, Date parm43, String parm44, Date parm45, String parm46, String parm47, int parm48, Date parm49, int parm50, int parm51, int parm52, int parm53, int parm54, int parm55, int parm56, String parm57, String parm58, java.math.BigDecimal parm59, java.math.BigDecimal parm60, java.math.BigDecimal parm61, int parm62, Date parm63, String parm64, int parm65, int parm66, java.math.BigDecimal parm67, java.math.BigDecimal parm68, String parm69, String parm70, java.math.BigDecimal parm71)
    {
        mOrderId = parm1;
        mOrderItemId = parm2;
        mOrderItemStatusCd = parm3;
        mItemId = parm4;
        mExceptionInd = parm5;
        mOrderLineNum = parm6;
        mCustLineNum = parm7;
        mErpPoLineNum = parm8;
        mErpOrderLineNum = parm9;
        mErpOrderNum = parm10;
        mErpPoNum = parm11;
        mErpPoDate = parm12;
        mErpPoTime = parm13;
        mItemSkuNum = parm14;
        mItemShortDesc = parm15;
        mItemUom = parm16;
        mItemPack = parm17;
        mItemSize = parm18;
        mItemCost = parm19;
        mCustItemSkuNum = parm20;
        mCustItemShortDesc = parm21;
        mCustItemUom = parm22;
        mCustItemPack = parm23;
        mCustContractPrice = parm24;
        mCustContractId = parm25;
        mCustContractShortDesc = parm26;
        mManuItemSkuNum = parm27;
        mManuItemShortDesc = parm28;
        mManuItemMsrp = parm29;
        mManuItemUpcNum = parm30;
        mManuPackUpcNum = parm31;
        mDistItemSkuNum = parm32;
        mDistItemShortDesc = parm33;
        mDistItemUom = parm34;
        mDistItemPack = parm35;
        mDistItemCost = parm36;
        mDistOrderNum = parm37;
        mDistErpNum = parm38;
        mDistItemQuantity = parm39;
        mTotalQuantityOrdered = parm40;
        mTotalQuantityShipped = parm41;
        mComments = parm42;
        mAddDate = parm43;
        mAddBy = parm44;
        mModDate = parm45;
        mModBy = parm46;
        mAckStatusCd = parm47;
        mPurchaseOrderId = parm48;
        mTargetShipDate = parm49;
        mQuantityConfirmed = parm50;
        mQuantityBackordered = parm51;
        mCostCenterId = parm52;
        mOrderRoutingId = parm53;
        mItemQty855 = parm54;
        mFreightHandlerId = parm55;
        mInventoryParValue = parm56;
        mInventoryQtyOnHand = parm57;
        mSaleTypeCd = parm58;
        mDistBaseCost = parm59;
        mDistUomConvMultiplier = parm60;
        mDistUomConvCost = parm61;
        mTotalQuantityReceived = parm62;
        mErpPoRefDate = parm63;
        mErpPoRefNum = parm64;
        mErpPoRefLineNum = parm65;
        mAssetId = parm66;
        mTaxRate = parm67;
        mTaxAmount = parm68;
        mTaxExempt = parm69;
        mOutboundPoNum = parm70;
        mServiceFee = parm71;
        
    }

    /**
     * Creates a new OrderItemData
     *
     * @return
     *  Newly initialized OrderItemData object.
     */
    public static OrderItemData createValue ()
    {
        OrderItemData valueData = new OrderItemData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this OrderItemData object
     */
    public String toString()
    {
        return "[" + "OrderId=" + mOrderId + ", OrderItemId=" + mOrderItemId + ", OrderItemStatusCd=" + mOrderItemStatusCd + ", ItemId=" + mItemId + ", ExceptionInd=" + mExceptionInd + ", OrderLineNum=" + mOrderLineNum + ", CustLineNum=" + mCustLineNum + ", ErpPoLineNum=" + mErpPoLineNum + ", ErpOrderLineNum=" + mErpOrderLineNum + ", ErpOrderNum=" + mErpOrderNum + ", ErpPoNum=" + mErpPoNum + ", ErpPoDate=" + mErpPoDate + ", ErpPoTime=" + mErpPoTime + ", ItemSkuNum=" + mItemSkuNum + ", ItemShortDesc=" + mItemShortDesc + ", ItemUom=" + mItemUom + ", ItemPack=" + mItemPack + ", ItemSize=" + mItemSize + ", ItemCost=" + mItemCost + ", CustItemSkuNum=" + mCustItemSkuNum + ", CustItemShortDesc=" + mCustItemShortDesc + ", CustItemUom=" + mCustItemUom + ", CustItemPack=" + mCustItemPack + ", CustContractPrice=" + mCustContractPrice + ", CustContractId=" + mCustContractId + ", CustContractShortDesc=" + mCustContractShortDesc + ", ManuItemSkuNum=" + mManuItemSkuNum + ", ManuItemShortDesc=" + mManuItemShortDesc + ", ManuItemMsrp=" + mManuItemMsrp + ", ManuItemUpcNum=" + mManuItemUpcNum + ", ManuPackUpcNum=" + mManuPackUpcNum + ", DistItemSkuNum=" + mDistItemSkuNum + ", DistItemShortDesc=" + mDistItemShortDesc + ", DistItemUom=" + mDistItemUom + ", DistItemPack=" + mDistItemPack + ", DistItemCost=" + mDistItemCost + ", DistOrderNum=" + mDistOrderNum + ", DistErpNum=" + mDistErpNum + ", DistItemQuantity=" + mDistItemQuantity + ", TotalQuantityOrdered=" + mTotalQuantityOrdered + ", TotalQuantityShipped=" + mTotalQuantityShipped + ", Comments=" + mComments + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", AckStatusCd=" + mAckStatusCd + ", PurchaseOrderId=" + mPurchaseOrderId + ", TargetShipDate=" + mTargetShipDate + ", QuantityConfirmed=" + mQuantityConfirmed + ", QuantityBackordered=" + mQuantityBackordered + ", CostCenterId=" + mCostCenterId + ", OrderRoutingId=" + mOrderRoutingId + ", ItemQty855=" + mItemQty855 + ", FreightHandlerId=" + mFreightHandlerId + ", InventoryParValue=" + mInventoryParValue + ", InventoryQtyOnHand=" + mInventoryQtyOnHand + ", SaleTypeCd=" + mSaleTypeCd + ", DistBaseCost=" + mDistBaseCost + ", DistUomConvMultiplier=" + mDistUomConvMultiplier + ", DistUomConvCost=" + mDistUomConvCost + ", TotalQuantityReceived=" + mTotalQuantityReceived + ", ErpPoRefDate=" + mErpPoRefDate + ", ErpPoRefNum=" + mErpPoRefNum + ", ErpPoRefLineNum=" + mErpPoRefLineNum + ", AssetId=" + mAssetId + ", TaxRate=" + mTaxRate + ", TaxAmount=" + mTaxAmount + ", TaxExempt=" + mTaxExempt + ", OutboundPoNum=" + mOutboundPoNum + ", ServiceFee=" + mServiceFee + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("OrderItem");
        
        Element node;

        node =  doc.createElement("OrderId");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderId)));
        root.appendChild(node);

        root.setAttribute("Id", String.valueOf(mOrderItemId));

        node =  doc.createElement("OrderItemStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderItemStatusCd)));
        root.appendChild(node);

        node =  doc.createElement("ItemId");
        node.appendChild(doc.createTextNode(String.valueOf(mItemId)));
        root.appendChild(node);

        node =  doc.createElement("ExceptionInd");
        node.appendChild(doc.createTextNode(String.valueOf(mExceptionInd)));
        root.appendChild(node);

        node =  doc.createElement("OrderLineNum");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderLineNum)));
        root.appendChild(node);

        node =  doc.createElement("CustLineNum");
        node.appendChild(doc.createTextNode(String.valueOf(mCustLineNum)));
        root.appendChild(node);

        node =  doc.createElement("ErpPoLineNum");
        node.appendChild(doc.createTextNode(String.valueOf(mErpPoLineNum)));
        root.appendChild(node);

        node =  doc.createElement("ErpOrderLineNum");
        node.appendChild(doc.createTextNode(String.valueOf(mErpOrderLineNum)));
        root.appendChild(node);

        node =  doc.createElement("ErpOrderNum");
        node.appendChild(doc.createTextNode(String.valueOf(mErpOrderNum)));
        root.appendChild(node);

        node =  doc.createElement("ErpPoNum");
        node.appendChild(doc.createTextNode(String.valueOf(mErpPoNum)));
        root.appendChild(node);

        node =  doc.createElement("ErpPoDate");
        node.appendChild(doc.createTextNode(String.valueOf(mErpPoDate)));
        root.appendChild(node);

        node =  doc.createElement("ErpPoTime");
        node.appendChild(doc.createTextNode(String.valueOf(mErpPoTime)));
        root.appendChild(node);

        node =  doc.createElement("ItemSkuNum");
        node.appendChild(doc.createTextNode(String.valueOf(mItemSkuNum)));
        root.appendChild(node);

        node =  doc.createElement("ItemShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mItemShortDesc)));
        root.appendChild(node);

        node =  doc.createElement("ItemUom");
        node.appendChild(doc.createTextNode(String.valueOf(mItemUom)));
        root.appendChild(node);

        node =  doc.createElement("ItemPack");
        node.appendChild(doc.createTextNode(String.valueOf(mItemPack)));
        root.appendChild(node);

        node =  doc.createElement("ItemSize");
        node.appendChild(doc.createTextNode(String.valueOf(mItemSize)));
        root.appendChild(node);

        node =  doc.createElement("ItemCost");
        node.appendChild(doc.createTextNode(String.valueOf(mItemCost)));
        root.appendChild(node);

        node =  doc.createElement("CustItemSkuNum");
        node.appendChild(doc.createTextNode(String.valueOf(mCustItemSkuNum)));
        root.appendChild(node);

        node =  doc.createElement("CustItemShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mCustItemShortDesc)));
        root.appendChild(node);

        node =  doc.createElement("CustItemUom");
        node.appendChild(doc.createTextNode(String.valueOf(mCustItemUom)));
        root.appendChild(node);

        node =  doc.createElement("CustItemPack");
        node.appendChild(doc.createTextNode(String.valueOf(mCustItemPack)));
        root.appendChild(node);

        node =  doc.createElement("CustContractPrice");
        node.appendChild(doc.createTextNode(String.valueOf(mCustContractPrice)));
        root.appendChild(node);

        node =  doc.createElement("CustContractId");
        node.appendChild(doc.createTextNode(String.valueOf(mCustContractId)));
        root.appendChild(node);

        node =  doc.createElement("CustContractShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mCustContractShortDesc)));
        root.appendChild(node);

        node =  doc.createElement("ManuItemSkuNum");
        node.appendChild(doc.createTextNode(String.valueOf(mManuItemSkuNum)));
        root.appendChild(node);

        node =  doc.createElement("ManuItemShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mManuItemShortDesc)));
        root.appendChild(node);

        node =  doc.createElement("ManuItemMsrp");
        node.appendChild(doc.createTextNode(String.valueOf(mManuItemMsrp)));
        root.appendChild(node);

        node =  doc.createElement("ManuItemUpcNum");
        node.appendChild(doc.createTextNode(String.valueOf(mManuItemUpcNum)));
        root.appendChild(node);

        node =  doc.createElement("ManuPackUpcNum");
        node.appendChild(doc.createTextNode(String.valueOf(mManuPackUpcNum)));
        root.appendChild(node);

        node =  doc.createElement("DistItemSkuNum");
        node.appendChild(doc.createTextNode(String.valueOf(mDistItemSkuNum)));
        root.appendChild(node);

        node =  doc.createElement("DistItemShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mDistItemShortDesc)));
        root.appendChild(node);

        node =  doc.createElement("DistItemUom");
        node.appendChild(doc.createTextNode(String.valueOf(mDistItemUom)));
        root.appendChild(node);

        node =  doc.createElement("DistItemPack");
        node.appendChild(doc.createTextNode(String.valueOf(mDistItemPack)));
        root.appendChild(node);

        node =  doc.createElement("DistItemCost");
        node.appendChild(doc.createTextNode(String.valueOf(mDistItemCost)));
        root.appendChild(node);

        node =  doc.createElement("DistOrderNum");
        node.appendChild(doc.createTextNode(String.valueOf(mDistOrderNum)));
        root.appendChild(node);

        node =  doc.createElement("DistErpNum");
        node.appendChild(doc.createTextNode(String.valueOf(mDistErpNum)));
        root.appendChild(node);

        node =  doc.createElement("DistItemQuantity");
        node.appendChild(doc.createTextNode(String.valueOf(mDistItemQuantity)));
        root.appendChild(node);

        node =  doc.createElement("TotalQuantityOrdered");
        node.appendChild(doc.createTextNode(String.valueOf(mTotalQuantityOrdered)));
        root.appendChild(node);

        node =  doc.createElement("TotalQuantityShipped");
        node.appendChild(doc.createTextNode(String.valueOf(mTotalQuantityShipped)));
        root.appendChild(node);

        node =  doc.createElement("Comments");
        node.appendChild(doc.createTextNode(String.valueOf(mComments)));
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

        node =  doc.createElement("AckStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mAckStatusCd)));
        root.appendChild(node);

        node =  doc.createElement("PurchaseOrderId");
        node.appendChild(doc.createTextNode(String.valueOf(mPurchaseOrderId)));
        root.appendChild(node);

        node =  doc.createElement("TargetShipDate");
        node.appendChild(doc.createTextNode(String.valueOf(mTargetShipDate)));
        root.appendChild(node);

        node =  doc.createElement("QuantityConfirmed");
        node.appendChild(doc.createTextNode(String.valueOf(mQuantityConfirmed)));
        root.appendChild(node);

        node =  doc.createElement("QuantityBackordered");
        node.appendChild(doc.createTextNode(String.valueOf(mQuantityBackordered)));
        root.appendChild(node);

        node =  doc.createElement("CostCenterId");
        node.appendChild(doc.createTextNode(String.valueOf(mCostCenterId)));
        root.appendChild(node);

        node =  doc.createElement("OrderRoutingId");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderRoutingId)));
        root.appendChild(node);

        node =  doc.createElement("ItemQty855");
        node.appendChild(doc.createTextNode(String.valueOf(mItemQty855)));
        root.appendChild(node);

        node =  doc.createElement("FreightHandlerId");
        node.appendChild(doc.createTextNode(String.valueOf(mFreightHandlerId)));
        root.appendChild(node);

        node =  doc.createElement("InventoryParValue");
        node.appendChild(doc.createTextNode(String.valueOf(mInventoryParValue)));
        root.appendChild(node);

        node =  doc.createElement("InventoryQtyOnHand");
        node.appendChild(doc.createTextNode(String.valueOf(mInventoryQtyOnHand)));
        root.appendChild(node);

        node =  doc.createElement("SaleTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mSaleTypeCd)));
        root.appendChild(node);

        node =  doc.createElement("DistBaseCost");
        node.appendChild(doc.createTextNode(String.valueOf(mDistBaseCost)));
        root.appendChild(node);

        node =  doc.createElement("DistUomConvMultiplier");
        node.appendChild(doc.createTextNode(String.valueOf(mDistUomConvMultiplier)));
        root.appendChild(node);

        node =  doc.createElement("DistUomConvCost");
        node.appendChild(doc.createTextNode(String.valueOf(mDistUomConvCost)));
        root.appendChild(node);

        node =  doc.createElement("TotalQuantityReceived");
        node.appendChild(doc.createTextNode(String.valueOf(mTotalQuantityReceived)));
        root.appendChild(node);

        node =  doc.createElement("ErpPoRefDate");
        node.appendChild(doc.createTextNode(String.valueOf(mErpPoRefDate)));
        root.appendChild(node);

        node =  doc.createElement("ErpPoRefNum");
        node.appendChild(doc.createTextNode(String.valueOf(mErpPoRefNum)));
        root.appendChild(node);

        node =  doc.createElement("ErpPoRefLineNum");
        node.appendChild(doc.createTextNode(String.valueOf(mErpPoRefLineNum)));
        root.appendChild(node);

        node =  doc.createElement("AssetId");
        node.appendChild(doc.createTextNode(String.valueOf(mAssetId)));
        root.appendChild(node);

        node =  doc.createElement("TaxRate");
        node.appendChild(doc.createTextNode(String.valueOf(mTaxRate)));
        root.appendChild(node);

        node =  doc.createElement("TaxAmount");
        node.appendChild(doc.createTextNode(String.valueOf(mTaxAmount)));
        root.appendChild(node);

        node =  doc.createElement("TaxExempt");
        node.appendChild(doc.createTextNode(String.valueOf(mTaxExempt)));
        root.appendChild(node);

        node =  doc.createElement("OutboundPoNum");
        node.appendChild(doc.createTextNode(String.valueOf(mOutboundPoNum)));
        root.appendChild(node);

        node =  doc.createElement("ServiceFee");
        node.appendChild(doc.createTextNode(String.valueOf(mServiceFee)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the OrderItemId field is not cloned.
    *
    * @return OrderItemData object
    */
    public Object clone(){
        OrderItemData myClone = new OrderItemData();
        
        myClone.mOrderId = mOrderId;
        
        myClone.mOrderItemStatusCd = mOrderItemStatusCd;
        
        myClone.mItemId = mItemId;
        
        myClone.mExceptionInd = mExceptionInd;
        
        myClone.mOrderLineNum = mOrderLineNum;
        
        myClone.mCustLineNum = mCustLineNum;
        
        myClone.mErpPoLineNum = mErpPoLineNum;
        
        myClone.mErpOrderLineNum = mErpOrderLineNum;
        
        myClone.mErpOrderNum = mErpOrderNum;
        
        myClone.mErpPoNum = mErpPoNum;
        
        if(mErpPoDate != null){
                myClone.mErpPoDate = (Date) mErpPoDate.clone();
        }
        
        if(mErpPoTime != null){
                myClone.mErpPoTime = (Date) mErpPoTime.clone();
        }
        
        myClone.mItemSkuNum = mItemSkuNum;
        
        myClone.mItemShortDesc = mItemShortDesc;
        
        myClone.mItemUom = mItemUom;
        
        myClone.mItemPack = mItemPack;
        
        myClone.mItemSize = mItemSize;
        
        myClone.mItemCost = mItemCost;
        
        myClone.mCustItemSkuNum = mCustItemSkuNum;
        
        myClone.mCustItemShortDesc = mCustItemShortDesc;
        
        myClone.mCustItemUom = mCustItemUom;
        
        myClone.mCustItemPack = mCustItemPack;
        
        myClone.mCustContractPrice = mCustContractPrice;
        
        myClone.mCustContractId = mCustContractId;
        
        myClone.mCustContractShortDesc = mCustContractShortDesc;
        
        myClone.mManuItemSkuNum = mManuItemSkuNum;
        
        myClone.mManuItemShortDesc = mManuItemShortDesc;
        
        myClone.mManuItemMsrp = mManuItemMsrp;
        
        myClone.mManuItemUpcNum = mManuItemUpcNum;
        
        myClone.mManuPackUpcNum = mManuPackUpcNum;
        
        myClone.mDistItemSkuNum = mDistItemSkuNum;
        
        myClone.mDistItemShortDesc = mDistItemShortDesc;
        
        myClone.mDistItemUom = mDistItemUom;
        
        myClone.mDistItemPack = mDistItemPack;
        
        myClone.mDistItemCost = mDistItemCost;
        
        myClone.mDistOrderNum = mDistOrderNum;
        
        myClone.mDistErpNum = mDistErpNum;
        
        myClone.mDistItemQuantity = mDistItemQuantity;
        
        myClone.mTotalQuantityOrdered = mTotalQuantityOrdered;
        
        myClone.mTotalQuantityShipped = mTotalQuantityShipped;
        
        myClone.mComments = mComments;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        myClone.mAckStatusCd = mAckStatusCd;
        
        myClone.mPurchaseOrderId = mPurchaseOrderId;
        
        if(mTargetShipDate != null){
                myClone.mTargetShipDate = (Date) mTargetShipDate.clone();
        }
        
        myClone.mQuantityConfirmed = mQuantityConfirmed;
        
        myClone.mQuantityBackordered = mQuantityBackordered;
        
        myClone.mCostCenterId = mCostCenterId;
        
        myClone.mOrderRoutingId = mOrderRoutingId;
        
        myClone.mItemQty855 = mItemQty855;
        
        myClone.mFreightHandlerId = mFreightHandlerId;
        
        myClone.mInventoryParValue = mInventoryParValue;
        
        myClone.mInventoryQtyOnHand = mInventoryQtyOnHand;
        
        myClone.mSaleTypeCd = mSaleTypeCd;
        
        myClone.mDistBaseCost = mDistBaseCost;
        
        myClone.mDistUomConvMultiplier = mDistUomConvMultiplier;
        
        myClone.mDistUomConvCost = mDistUomConvCost;
        
        myClone.mTotalQuantityReceived = mTotalQuantityReceived;
        
        if(mErpPoRefDate != null){
                myClone.mErpPoRefDate = (Date) mErpPoRefDate.clone();
        }
        
        myClone.mErpPoRefNum = mErpPoRefNum;
        
        myClone.mErpPoRefLineNum = mErpPoRefLineNum;
        
        myClone.mAssetId = mAssetId;
        
        myClone.mTaxRate = mTaxRate;
        
        myClone.mTaxAmount = mTaxAmount;
        
        myClone.mTaxExempt = mTaxExempt;
        
        myClone.mOutboundPoNum = mOutboundPoNum;
        
        myClone.mServiceFee = mServiceFee;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (OrderItemDataAccess.ORDER_ID.equals(pFieldName)) {
            return getOrderId();
        } else if (OrderItemDataAccess.ORDER_ITEM_ID.equals(pFieldName)) {
            return getOrderItemId();
        } else if (OrderItemDataAccess.ORDER_ITEM_STATUS_CD.equals(pFieldName)) {
            return getOrderItemStatusCd();
        } else if (OrderItemDataAccess.ITEM_ID.equals(pFieldName)) {
            return getItemId();
        } else if (OrderItemDataAccess.EXCEPTION_IND.equals(pFieldName)) {
            return getExceptionInd();
        } else if (OrderItemDataAccess.ORDER_LINE_NUM.equals(pFieldName)) {
            return getOrderLineNum();
        } else if (OrderItemDataAccess.CUST_LINE_NUM.equals(pFieldName)) {
            return getCustLineNum();
        } else if (OrderItemDataAccess.ERP_PO_LINE_NUM.equals(pFieldName)) {
            return getErpPoLineNum();
        } else if (OrderItemDataAccess.ERP_ORDER_LINE_NUM.equals(pFieldName)) {
            return getErpOrderLineNum();
        } else if (OrderItemDataAccess.ERP_ORDER_NUM.equals(pFieldName)) {
            return getErpOrderNum();
        } else if (OrderItemDataAccess.ERP_PO_NUM.equals(pFieldName)) {
            return getErpPoNum();
        } else if (OrderItemDataAccess.ERP_PO_DATE.equals(pFieldName)) {
            return getErpPoDate();
        } else if (OrderItemDataAccess.ERP_PO_TIME.equals(pFieldName)) {
            return getErpPoTime();
        } else if (OrderItemDataAccess.ITEM_SKU_NUM.equals(pFieldName)) {
            return getItemSkuNum();
        } else if (OrderItemDataAccess.ITEM_SHORT_DESC.equals(pFieldName)) {
            return getItemShortDesc();
        } else if (OrderItemDataAccess.ITEM_UOM.equals(pFieldName)) {
            return getItemUom();
        } else if (OrderItemDataAccess.ITEM_PACK.equals(pFieldName)) {
            return getItemPack();
        } else if (OrderItemDataAccess.ITEM_SIZE.equals(pFieldName)) {
            return getItemSize();
        } else if (OrderItemDataAccess.ITEM_COST.equals(pFieldName)) {
            return getItemCost();
        } else if (OrderItemDataAccess.CUST_ITEM_SKU_NUM.equals(pFieldName)) {
            return getCustItemSkuNum();
        } else if (OrderItemDataAccess.CUST_ITEM_SHORT_DESC.equals(pFieldName)) {
            return getCustItemShortDesc();
        } else if (OrderItemDataAccess.CUST_ITEM_UOM.equals(pFieldName)) {
            return getCustItemUom();
        } else if (OrderItemDataAccess.CUST_ITEM_PACK.equals(pFieldName)) {
            return getCustItemPack();
        } else if (OrderItemDataAccess.CUST_CONTRACT_PRICE.equals(pFieldName)) {
            return getCustContractPrice();
        } else if (OrderItemDataAccess.CUST_CONTRACT_ID.equals(pFieldName)) {
            return getCustContractId();
        } else if (OrderItemDataAccess.CUST_CONTRACT_SHORT_DESC.equals(pFieldName)) {
            return getCustContractShortDesc();
        } else if (OrderItemDataAccess.MANU_ITEM_SKU_NUM.equals(pFieldName)) {
            return getManuItemSkuNum();
        } else if (OrderItemDataAccess.MANU_ITEM_SHORT_DESC.equals(pFieldName)) {
            return getManuItemShortDesc();
        } else if (OrderItemDataAccess.MANU_ITEM_MSRP.equals(pFieldName)) {
            return getManuItemMsrp();
        } else if (OrderItemDataAccess.MANU_ITEM_UPC_NUM.equals(pFieldName)) {
            return getManuItemUpcNum();
        } else if (OrderItemDataAccess.MANU_PACK_UPC_NUM.equals(pFieldName)) {
            return getManuPackUpcNum();
        } else if (OrderItemDataAccess.DIST_ITEM_SKU_NUM.equals(pFieldName)) {
            return getDistItemSkuNum();
        } else if (OrderItemDataAccess.DIST_ITEM_SHORT_DESC.equals(pFieldName)) {
            return getDistItemShortDesc();
        } else if (OrderItemDataAccess.DIST_ITEM_UOM.equals(pFieldName)) {
            return getDistItemUom();
        } else if (OrderItemDataAccess.DIST_ITEM_PACK.equals(pFieldName)) {
            return getDistItemPack();
        } else if (OrderItemDataAccess.DIST_ITEM_COST.equals(pFieldName)) {
            return getDistItemCost();
        } else if (OrderItemDataAccess.DIST_ORDER_NUM.equals(pFieldName)) {
            return getDistOrderNum();
        } else if (OrderItemDataAccess.DIST_ERP_NUM.equals(pFieldName)) {
            return getDistErpNum();
        } else if (OrderItemDataAccess.DIST_ITEM_QUANTITY.equals(pFieldName)) {
            return getDistItemQuantity();
        } else if (OrderItemDataAccess.TOTAL_QUANTITY_ORDERED.equals(pFieldName)) {
            return getTotalQuantityOrdered();
        } else if (OrderItemDataAccess.TOTAL_QUANTITY_SHIPPED.equals(pFieldName)) {
            return getTotalQuantityShipped();
        } else if (OrderItemDataAccess.COMMENTS.equals(pFieldName)) {
            return getComments();
        } else if (OrderItemDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (OrderItemDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (OrderItemDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (OrderItemDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (OrderItemDataAccess.ACK_STATUS_CD.equals(pFieldName)) {
            return getAckStatusCd();
        } else if (OrderItemDataAccess.PURCHASE_ORDER_ID.equals(pFieldName)) {
            return getPurchaseOrderId();
        } else if (OrderItemDataAccess.TARGET_SHIP_DATE.equals(pFieldName)) {
            return getTargetShipDate();
        } else if (OrderItemDataAccess.QUANTITY_CONFIRMED.equals(pFieldName)) {
            return getQuantityConfirmed();
        } else if (OrderItemDataAccess.QUANTITY_BACKORDERED.equals(pFieldName)) {
            return getQuantityBackordered();
        } else if (OrderItemDataAccess.COST_CENTER_ID.equals(pFieldName)) {
            return getCostCenterId();
        } else if (OrderItemDataAccess.ORDER_ROUTING_ID.equals(pFieldName)) {
            return getOrderRoutingId();
        } else if (OrderItemDataAccess.ITEM_QTY_855.equals(pFieldName)) {
            return getItemQty855();
        } else if (OrderItemDataAccess.FREIGHT_HANDLER_ID.equals(pFieldName)) {
            return getFreightHandlerId();
        } else if (OrderItemDataAccess.INVENTORY_PAR_VALUE.equals(pFieldName)) {
            return getInventoryParValue();
        } else if (OrderItemDataAccess.INVENTORY_QTY_ON_HAND.equals(pFieldName)) {
            return getInventoryQtyOnHand();
        } else if (OrderItemDataAccess.SALE_TYPE_CD.equals(pFieldName)) {
            return getSaleTypeCd();
        } else if (OrderItemDataAccess.DIST_BASE_COST.equals(pFieldName)) {
            return getDistBaseCost();
        } else if (OrderItemDataAccess.DIST_UOM_CONV_MULTIPLIER.equals(pFieldName)) {
            return getDistUomConvMultiplier();
        } else if (OrderItemDataAccess.DIST_UOM_CONV_COST.equals(pFieldName)) {
            return getDistUomConvCost();
        } else if (OrderItemDataAccess.TOTAL_QUANTITY_RECEIVED.equals(pFieldName)) {
            return getTotalQuantityReceived();
        } else if (OrderItemDataAccess.ERP_PO_REF_DATE.equals(pFieldName)) {
            return getErpPoRefDate();
        } else if (OrderItemDataAccess.ERP_PO_REF_NUM.equals(pFieldName)) {
            return getErpPoRefNum();
        } else if (OrderItemDataAccess.ERP_PO_REF_LINE_NUM.equals(pFieldName)) {
            return getErpPoRefLineNum();
        } else if (OrderItemDataAccess.ASSET_ID.equals(pFieldName)) {
            return getAssetId();
        } else if (OrderItemDataAccess.TAX_RATE.equals(pFieldName)) {
            return getTaxRate();
        } else if (OrderItemDataAccess.TAX_AMOUNT.equals(pFieldName)) {
            return getTaxAmount();
        } else if (OrderItemDataAccess.TAX_EXEMPT.equals(pFieldName)) {
            return getTaxExempt();
        } else if (OrderItemDataAccess.OUTBOUND_PO_NUM.equals(pFieldName)) {
            return getOutboundPoNum();
        } else if (OrderItemDataAccess.SERVICE_FEE.equals(pFieldName)) {
            return getServiceFee();
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
        return OrderItemDataAccess.CLW_ORDER_ITEM;
    }

    
    /**
     * Sets the OrderId field. This field is required to be set in the database.
     *
     * @param pOrderId
     *  int to use to update the field.
     */
    public void setOrderId(int pOrderId){
        this.mOrderId = pOrderId;
        setDirty(true);
    }
    /**
     * Retrieves the OrderId field.
     *
     * @return
     *  int containing the OrderId field.
     */
    public int getOrderId(){
        return mOrderId;
    }

    /**
     * Sets the OrderItemId field. This field is required to be set in the database.
     *
     * @param pOrderItemId
     *  int to use to update the field.
     */
    public void setOrderItemId(int pOrderItemId){
        this.mOrderItemId = pOrderItemId;
        setDirty(true);
    }
    /**
     * Retrieves the OrderItemId field.
     *
     * @return
     *  int containing the OrderItemId field.
     */
    public int getOrderItemId(){
        return mOrderItemId;
    }

    /**
     * Sets the OrderItemStatusCd field.
     *
     * @param pOrderItemStatusCd
     *  String to use to update the field.
     */
    public void setOrderItemStatusCd(String pOrderItemStatusCd){
        this.mOrderItemStatusCd = pOrderItemStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the OrderItemStatusCd field.
     *
     * @return
     *  String containing the OrderItemStatusCd field.
     */
    public String getOrderItemStatusCd(){
        return mOrderItemStatusCd;
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
     * Sets the ExceptionInd field.
     *
     * @param pExceptionInd
     *  String to use to update the field.
     */
    public void setExceptionInd(String pExceptionInd){
        this.mExceptionInd = pExceptionInd;
        setDirty(true);
    }
    /**
     * Retrieves the ExceptionInd field.
     *
     * @return
     *  String containing the ExceptionInd field.
     */
    public String getExceptionInd(){
        return mExceptionInd;
    }

    /**
     * Sets the OrderLineNum field.
     *
     * @param pOrderLineNum
     *  int to use to update the field.
     */
    public void setOrderLineNum(int pOrderLineNum){
        this.mOrderLineNum = pOrderLineNum;
        setDirty(true);
    }
    /**
     * Retrieves the OrderLineNum field.
     *
     * @return
     *  int containing the OrderLineNum field.
     */
    public int getOrderLineNum(){
        return mOrderLineNum;
    }

    /**
     * Sets the CustLineNum field.
     *
     * @param pCustLineNum
     *  int to use to update the field.
     */
    public void setCustLineNum(int pCustLineNum){
        this.mCustLineNum = pCustLineNum;
        setDirty(true);
    }
    /**
     * Retrieves the CustLineNum field.
     *
     * @return
     *  int containing the CustLineNum field.
     */
    public int getCustLineNum(){
        return mCustLineNum;
    }

    /**
     * Sets the ErpPoLineNum field.
     *
     * @param pErpPoLineNum
     *  int to use to update the field.
     */
    public void setErpPoLineNum(int pErpPoLineNum){
        this.mErpPoLineNum = pErpPoLineNum;
        setDirty(true);
    }
    /**
     * Retrieves the ErpPoLineNum field.
     *
     * @return
     *  int containing the ErpPoLineNum field.
     */
    public int getErpPoLineNum(){
        return mErpPoLineNum;
    }

    /**
     * Sets the ErpOrderLineNum field.
     *
     * @param pErpOrderLineNum
     *  int to use to update the field.
     */
    public void setErpOrderLineNum(int pErpOrderLineNum){
        this.mErpOrderLineNum = pErpOrderLineNum;
        setDirty(true);
    }
    /**
     * Retrieves the ErpOrderLineNum field.
     *
     * @return
     *  int containing the ErpOrderLineNum field.
     */
    public int getErpOrderLineNum(){
        return mErpOrderLineNum;
    }

    /**
     * Sets the ErpOrderNum field.
     *
     * @param pErpOrderNum
     *  String to use to update the field.
     */
    public void setErpOrderNum(String pErpOrderNum){
        this.mErpOrderNum = pErpOrderNum;
        setDirty(true);
    }
    /**
     * Retrieves the ErpOrderNum field.
     *
     * @return
     *  String containing the ErpOrderNum field.
     */
    public String getErpOrderNum(){
        return mErpOrderNum;
    }

    /**
     * Sets the ErpPoNum field.
     *
     * @param pErpPoNum
     *  String to use to update the field.
     */
    public void setErpPoNum(String pErpPoNum){
        this.mErpPoNum = pErpPoNum;
        setDirty(true);
    }
    /**
     * Retrieves the ErpPoNum field.
     *
     * @return
     *  String containing the ErpPoNum field.
     */
    public String getErpPoNum(){
        return mErpPoNum;
    }

    /**
     * Sets the ErpPoDate field.
     *
     * @param pErpPoDate
     *  Date to use to update the field.
     */
    public void setErpPoDate(Date pErpPoDate){
        this.mErpPoDate = pErpPoDate;
        setDirty(true);
    }
    /**
     * Retrieves the ErpPoDate field.
     *
     * @return
     *  Date containing the ErpPoDate field.
     */
    public Date getErpPoDate(){
        return mErpPoDate;
    }

    /**
     * Sets the ErpPoTime field.
     *
     * @param pErpPoTime
     *  Date to use to update the field.
     */
    public void setErpPoTime(Date pErpPoTime){
        this.mErpPoTime = pErpPoTime;
        setDirty(true);
    }
    /**
     * Retrieves the ErpPoTime field.
     *
     * @return
     *  Date containing the ErpPoTime field.
     */
    public Date getErpPoTime(){
        return mErpPoTime;
    }

    /**
     * Sets the ItemSkuNum field.
     *
     * @param pItemSkuNum
     *  int to use to update the field.
     */
    public void setItemSkuNum(int pItemSkuNum){
        this.mItemSkuNum = pItemSkuNum;
        setDirty(true);
    }
    /**
     * Retrieves the ItemSkuNum field.
     *
     * @return
     *  int containing the ItemSkuNum field.
     */
    public int getItemSkuNum(){
        return mItemSkuNum;
    }

    /**
     * Sets the ItemShortDesc field.
     *
     * @param pItemShortDesc
     *  String to use to update the field.
     */
    public void setItemShortDesc(String pItemShortDesc){
        this.mItemShortDesc = pItemShortDesc;
        setDirty(true);
    }
    /**
     * Retrieves the ItemShortDesc field.
     *
     * @return
     *  String containing the ItemShortDesc field.
     */
    public String getItemShortDesc(){
        return mItemShortDesc;
    }

    /**
     * Sets the ItemUom field.
     *
     * @param pItemUom
     *  String to use to update the field.
     */
    public void setItemUom(String pItemUom){
        this.mItemUom = pItemUom;
        setDirty(true);
    }
    /**
     * Retrieves the ItemUom field.
     *
     * @return
     *  String containing the ItemUom field.
     */
    public String getItemUom(){
        return mItemUom;
    }

    /**
     * Sets the ItemPack field.
     *
     * @param pItemPack
     *  String to use to update the field.
     */
    public void setItemPack(String pItemPack){
        this.mItemPack = pItemPack;
        setDirty(true);
    }
    /**
     * Retrieves the ItemPack field.
     *
     * @return
     *  String containing the ItemPack field.
     */
    public String getItemPack(){
        return mItemPack;
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
     * Sets the ItemCost field.
     *
     * @param pItemCost
     *  java.math.BigDecimal to use to update the field.
     */
    public void setItemCost(java.math.BigDecimal pItemCost){
        this.mItemCost = pItemCost;
        setDirty(true);
    }
    /**
     * Retrieves the ItemCost field.
     *
     * @return
     *  java.math.BigDecimal containing the ItemCost field.
     */
    public java.math.BigDecimal getItemCost(){
        return mItemCost;
    }

    /**
     * Sets the CustItemSkuNum field.
     *
     * @param pCustItemSkuNum
     *  String to use to update the field.
     */
    public void setCustItemSkuNum(String pCustItemSkuNum){
        this.mCustItemSkuNum = pCustItemSkuNum;
        setDirty(true);
    }
    /**
     * Retrieves the CustItemSkuNum field.
     *
     * @return
     *  String containing the CustItemSkuNum field.
     */
    public String getCustItemSkuNum(){
        return mCustItemSkuNum;
    }

    /**
     * Sets the CustItemShortDesc field.
     *
     * @param pCustItemShortDesc
     *  String to use to update the field.
     */
    public void setCustItemShortDesc(String pCustItemShortDesc){
        this.mCustItemShortDesc = pCustItemShortDesc;
        setDirty(true);
    }
    /**
     * Retrieves the CustItemShortDesc field.
     *
     * @return
     *  String containing the CustItemShortDesc field.
     */
    public String getCustItemShortDesc(){
        return mCustItemShortDesc;
    }

    /**
     * Sets the CustItemUom field.
     *
     * @param pCustItemUom
     *  String to use to update the field.
     */
    public void setCustItemUom(String pCustItemUom){
        this.mCustItemUom = pCustItemUom;
        setDirty(true);
    }
    /**
     * Retrieves the CustItemUom field.
     *
     * @return
     *  String containing the CustItemUom field.
     */
    public String getCustItemUom(){
        return mCustItemUom;
    }

    /**
     * Sets the CustItemPack field.
     *
     * @param pCustItemPack
     *  String to use to update the field.
     */
    public void setCustItemPack(String pCustItemPack){
        this.mCustItemPack = pCustItemPack;
        setDirty(true);
    }
    /**
     * Retrieves the CustItemPack field.
     *
     * @return
     *  String containing the CustItemPack field.
     */
    public String getCustItemPack(){
        return mCustItemPack;
    }

    /**
     * Sets the CustContractPrice field.
     *
     * @param pCustContractPrice
     *  java.math.BigDecimal to use to update the field.
     */
    public void setCustContractPrice(java.math.BigDecimal pCustContractPrice){
        this.mCustContractPrice = pCustContractPrice;
        setDirty(true);
    }
    /**
     * Retrieves the CustContractPrice field.
     *
     * @return
     *  java.math.BigDecimal containing the CustContractPrice field.
     */
    public java.math.BigDecimal getCustContractPrice(){
        return mCustContractPrice;
    }

    /**
     * Sets the CustContractId field.
     *
     * @param pCustContractId
     *  int to use to update the field.
     */
    public void setCustContractId(int pCustContractId){
        this.mCustContractId = pCustContractId;
        setDirty(true);
    }
    /**
     * Retrieves the CustContractId field.
     *
     * @return
     *  int containing the CustContractId field.
     */
    public int getCustContractId(){
        return mCustContractId;
    }

    /**
     * Sets the CustContractShortDesc field.
     *
     * @param pCustContractShortDesc
     *  String to use to update the field.
     */
    public void setCustContractShortDesc(String pCustContractShortDesc){
        this.mCustContractShortDesc = pCustContractShortDesc;
        setDirty(true);
    }
    /**
     * Retrieves the CustContractShortDesc field.
     *
     * @return
     *  String containing the CustContractShortDesc field.
     */
    public String getCustContractShortDesc(){
        return mCustContractShortDesc;
    }

    /**
     * Sets the ManuItemSkuNum field.
     *
     * @param pManuItemSkuNum
     *  String to use to update the field.
     */
    public void setManuItemSkuNum(String pManuItemSkuNum){
        this.mManuItemSkuNum = pManuItemSkuNum;
        setDirty(true);
    }
    /**
     * Retrieves the ManuItemSkuNum field.
     *
     * @return
     *  String containing the ManuItemSkuNum field.
     */
    public String getManuItemSkuNum(){
        return mManuItemSkuNum;
    }

    /**
     * Sets the ManuItemShortDesc field.
     *
     * @param pManuItemShortDesc
     *  String to use to update the field.
     */
    public void setManuItemShortDesc(String pManuItemShortDesc){
        this.mManuItemShortDesc = pManuItemShortDesc;
        setDirty(true);
    }
    /**
     * Retrieves the ManuItemShortDesc field.
     *
     * @return
     *  String containing the ManuItemShortDesc field.
     */
    public String getManuItemShortDesc(){
        return mManuItemShortDesc;
    }

    /**
     * Sets the ManuItemMsrp field.
     *
     * @param pManuItemMsrp
     *  java.math.BigDecimal to use to update the field.
     */
    public void setManuItemMsrp(java.math.BigDecimal pManuItemMsrp){
        this.mManuItemMsrp = pManuItemMsrp;
        setDirty(true);
    }
    /**
     * Retrieves the ManuItemMsrp field.
     *
     * @return
     *  java.math.BigDecimal containing the ManuItemMsrp field.
     */
    public java.math.BigDecimal getManuItemMsrp(){
        return mManuItemMsrp;
    }

    /**
     * Sets the ManuItemUpcNum field.
     *
     * @param pManuItemUpcNum
     *  String to use to update the field.
     */
    public void setManuItemUpcNum(String pManuItemUpcNum){
        this.mManuItemUpcNum = pManuItemUpcNum;
        setDirty(true);
    }
    /**
     * Retrieves the ManuItemUpcNum field.
     *
     * @return
     *  String containing the ManuItemUpcNum field.
     */
    public String getManuItemUpcNum(){
        return mManuItemUpcNum;
    }

    /**
     * Sets the ManuPackUpcNum field.
     *
     * @param pManuPackUpcNum
     *  String to use to update the field.
     */
    public void setManuPackUpcNum(String pManuPackUpcNum){
        this.mManuPackUpcNum = pManuPackUpcNum;
        setDirty(true);
    }
    /**
     * Retrieves the ManuPackUpcNum field.
     *
     * @return
     *  String containing the ManuPackUpcNum field.
     */
    public String getManuPackUpcNum(){
        return mManuPackUpcNum;
    }

    /**
     * Sets the DistItemSkuNum field.
     *
     * @param pDistItemSkuNum
     *  String to use to update the field.
     */
    public void setDistItemSkuNum(String pDistItemSkuNum){
        this.mDistItemSkuNum = pDistItemSkuNum;
        setDirty(true);
    }
    /**
     * Retrieves the DistItemSkuNum field.
     *
     * @return
     *  String containing the DistItemSkuNum field.
     */
    public String getDistItemSkuNum(){
        return mDistItemSkuNum;
    }

    /**
     * Sets the DistItemShortDesc field.
     *
     * @param pDistItemShortDesc
     *  String to use to update the field.
     */
    public void setDistItemShortDesc(String pDistItemShortDesc){
        this.mDistItemShortDesc = pDistItemShortDesc;
        setDirty(true);
    }
    /**
     * Retrieves the DistItemShortDesc field.
     *
     * @return
     *  String containing the DistItemShortDesc field.
     */
    public String getDistItemShortDesc(){
        return mDistItemShortDesc;
    }

    /**
     * Sets the DistItemUom field.
     *
     * @param pDistItemUom
     *  String to use to update the field.
     */
    public void setDistItemUom(String pDistItemUom){
        this.mDistItemUom = pDistItemUom;
        setDirty(true);
    }
    /**
     * Retrieves the DistItemUom field.
     *
     * @return
     *  String containing the DistItemUom field.
     */
    public String getDistItemUom(){
        return mDistItemUom;
    }

    /**
     * Sets the DistItemPack field.
     *
     * @param pDistItemPack
     *  String to use to update the field.
     */
    public void setDistItemPack(String pDistItemPack){
        this.mDistItemPack = pDistItemPack;
        setDirty(true);
    }
    /**
     * Retrieves the DistItemPack field.
     *
     * @return
     *  String containing the DistItemPack field.
     */
    public String getDistItemPack(){
        return mDistItemPack;
    }

    /**
     * Sets the DistItemCost field.
     *
     * @param pDistItemCost
     *  java.math.BigDecimal to use to update the field.
     */
    public void setDistItemCost(java.math.BigDecimal pDistItemCost){
        this.mDistItemCost = pDistItemCost;
        setDirty(true);
    }
    /**
     * Retrieves the DistItemCost field.
     *
     * @return
     *  java.math.BigDecimal containing the DistItemCost field.
     */
    public java.math.BigDecimal getDistItemCost(){
        return mDistItemCost;
    }

    /**
     * Sets the DistOrderNum field.
     *
     * @param pDistOrderNum
     *  String to use to update the field.
     */
    public void setDistOrderNum(String pDistOrderNum){
        this.mDistOrderNum = pDistOrderNum;
        setDirty(true);
    }
    /**
     * Retrieves the DistOrderNum field.
     *
     * @return
     *  String containing the DistOrderNum field.
     */
    public String getDistOrderNum(){
        return mDistOrderNum;
    }

    /**
     * Sets the DistErpNum field.
     *
     * @param pDistErpNum
     *  String to use to update the field.
     */
    public void setDistErpNum(String pDistErpNum){
        this.mDistErpNum = pDistErpNum;
        setDirty(true);
    }
    /**
     * Retrieves the DistErpNum field.
     *
     * @return
     *  String containing the DistErpNum field.
     */
    public String getDistErpNum(){
        return mDistErpNum;
    }

    /**
     * Sets the DistItemQuantity field.
     *
     * @param pDistItemQuantity
     *  int to use to update the field.
     */
    public void setDistItemQuantity(int pDistItemQuantity){
        this.mDistItemQuantity = pDistItemQuantity;
        setDirty(true);
    }
    /**
     * Retrieves the DistItemQuantity field.
     *
     * @return
     *  int containing the DistItemQuantity field.
     */
    public int getDistItemQuantity(){
        return mDistItemQuantity;
    }

    /**
     * Sets the TotalQuantityOrdered field.
     *
     * @param pTotalQuantityOrdered
     *  int to use to update the field.
     */
    public void setTotalQuantityOrdered(int pTotalQuantityOrdered){
        this.mTotalQuantityOrdered = pTotalQuantityOrdered;
        setDirty(true);
    }
    /**
     * Retrieves the TotalQuantityOrdered field.
     *
     * @return
     *  int containing the TotalQuantityOrdered field.
     */
    public int getTotalQuantityOrdered(){
        return mTotalQuantityOrdered;
    }

    /**
     * Sets the TotalQuantityShipped field.
     *
     * @param pTotalQuantityShipped
     *  int to use to update the field.
     */
    public void setTotalQuantityShipped(int pTotalQuantityShipped){
        this.mTotalQuantityShipped = pTotalQuantityShipped;
        setDirty(true);
    }
    /**
     * Retrieves the TotalQuantityShipped field.
     *
     * @return
     *  int containing the TotalQuantityShipped field.
     */
    public int getTotalQuantityShipped(){
        return mTotalQuantityShipped;
    }

    /**
     * Sets the Comments field.
     *
     * @param pComments
     *  String to use to update the field.
     */
    public void setComments(String pComments){
        this.mComments = pComments;
        setDirty(true);
    }
    /**
     * Retrieves the Comments field.
     *
     * @return
     *  String containing the Comments field.
     */
    public String getComments(){
        return mComments;
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

    /**
     * Sets the AckStatusCd field.
     *
     * @param pAckStatusCd
     *  String to use to update the field.
     */
    public void setAckStatusCd(String pAckStatusCd){
        this.mAckStatusCd = pAckStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the AckStatusCd field.
     *
     * @return
     *  String containing the AckStatusCd field.
     */
    public String getAckStatusCd(){
        return mAckStatusCd;
    }

    /**
     * Sets the PurchaseOrderId field.
     *
     * @param pPurchaseOrderId
     *  int to use to update the field.
     */
    public void setPurchaseOrderId(int pPurchaseOrderId){
        this.mPurchaseOrderId = pPurchaseOrderId;
        setDirty(true);
    }
    /**
     * Retrieves the PurchaseOrderId field.
     *
     * @return
     *  int containing the PurchaseOrderId field.
     */
    public int getPurchaseOrderId(){
        return mPurchaseOrderId;
    }

    /**
     * Sets the TargetShipDate field.
     *
     * @param pTargetShipDate
     *  Date to use to update the field.
     */
    public void setTargetShipDate(Date pTargetShipDate){
        this.mTargetShipDate = pTargetShipDate;
        setDirty(true);
    }
    /**
     * Retrieves the TargetShipDate field.
     *
     * @return
     *  Date containing the TargetShipDate field.
     */
    public Date getTargetShipDate(){
        return mTargetShipDate;
    }

    /**
     * Sets the QuantityConfirmed field.
     *
     * @param pQuantityConfirmed
     *  int to use to update the field.
     */
    public void setQuantityConfirmed(int pQuantityConfirmed){
        this.mQuantityConfirmed = pQuantityConfirmed;
        setDirty(true);
    }
    /**
     * Retrieves the QuantityConfirmed field.
     *
     * @return
     *  int containing the QuantityConfirmed field.
     */
    public int getQuantityConfirmed(){
        return mQuantityConfirmed;
    }

    /**
     * Sets the QuantityBackordered field.
     *
     * @param pQuantityBackordered
     *  int to use to update the field.
     */
    public void setQuantityBackordered(int pQuantityBackordered){
        this.mQuantityBackordered = pQuantityBackordered;
        setDirty(true);
    }
    /**
     * Retrieves the QuantityBackordered field.
     *
     * @return
     *  int containing the QuantityBackordered field.
     */
    public int getQuantityBackordered(){
        return mQuantityBackordered;
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
     * Sets the OrderRoutingId field.
     *
     * @param pOrderRoutingId
     *  int to use to update the field.
     */
    public void setOrderRoutingId(int pOrderRoutingId){
        this.mOrderRoutingId = pOrderRoutingId;
        setDirty(true);
    }
    /**
     * Retrieves the OrderRoutingId field.
     *
     * @return
     *  int containing the OrderRoutingId field.
     */
    public int getOrderRoutingId(){
        return mOrderRoutingId;
    }

    /**
     * Sets the ItemQty855 field.
     *
     * @param pItemQty855
     *  int to use to update the field.
     */
    public void setItemQty855(int pItemQty855){
        this.mItemQty855 = pItemQty855;
        setDirty(true);
    }
    /**
     * Retrieves the ItemQty855 field.
     *
     * @return
     *  int containing the ItemQty855 field.
     */
    public int getItemQty855(){
        return mItemQty855;
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
     * Sets the InventoryParValue field.
     *
     * @param pInventoryParValue
     *  int to use to update the field.
     */
    public void setInventoryParValue(int pInventoryParValue){
        this.mInventoryParValue = pInventoryParValue;
        setDirty(true);
    }
    /**
     * Retrieves the InventoryParValue field.
     *
     * @return
     *  int containing the InventoryParValue field.
     */
    public int getInventoryParValue(){
        return mInventoryParValue;
    }

    /**
     * Sets the InventoryQtyOnHand field.
     *
     * @param pInventoryQtyOnHand
     *  String to use to update the field.
     */
    public void setInventoryQtyOnHand(String pInventoryQtyOnHand){
        this.mInventoryQtyOnHand = pInventoryQtyOnHand;
        setDirty(true);
    }
    /**
     * Retrieves the InventoryQtyOnHand field.
     *
     * @return
     *  String containing the InventoryQtyOnHand field.
     */
    public String getInventoryQtyOnHand(){
        return mInventoryQtyOnHand;
    }

    /**
     * Sets the SaleTypeCd field.
     *
     * @param pSaleTypeCd
     *  String to use to update the field.
     */
    public void setSaleTypeCd(String pSaleTypeCd){
        this.mSaleTypeCd = pSaleTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the SaleTypeCd field.
     *
     * @return
     *  String containing the SaleTypeCd field.
     */
    public String getSaleTypeCd(){
        return mSaleTypeCd;
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
     * Sets the DistUomConvMultiplier field.
     *
     * @param pDistUomConvMultiplier
     *  java.math.BigDecimal to use to update the field.
     */
    public void setDistUomConvMultiplier(java.math.BigDecimal pDistUomConvMultiplier){
        this.mDistUomConvMultiplier = pDistUomConvMultiplier;
        setDirty(true);
    }
    /**
     * Retrieves the DistUomConvMultiplier field.
     *
     * @return
     *  java.math.BigDecimal containing the DistUomConvMultiplier field.
     */
    public java.math.BigDecimal getDistUomConvMultiplier(){
        return mDistUomConvMultiplier;
    }

    /**
     * Sets the DistUomConvCost field.
     *
     * @param pDistUomConvCost
     *  java.math.BigDecimal to use to update the field.
     */
    public void setDistUomConvCost(java.math.BigDecimal pDistUomConvCost){
        this.mDistUomConvCost = pDistUomConvCost;
        setDirty(true);
    }
    /**
     * Retrieves the DistUomConvCost field.
     *
     * @return
     *  java.math.BigDecimal containing the DistUomConvCost field.
     */
    public java.math.BigDecimal getDistUomConvCost(){
        return mDistUomConvCost;
    }

    /**
     * Sets the TotalQuantityReceived field.
     *
     * @param pTotalQuantityReceived
     *  int to use to update the field.
     */
    public void setTotalQuantityReceived(int pTotalQuantityReceived){
        this.mTotalQuantityReceived = pTotalQuantityReceived;
        setDirty(true);
    }
    /**
     * Retrieves the TotalQuantityReceived field.
     *
     * @return
     *  int containing the TotalQuantityReceived field.
     */
    public int getTotalQuantityReceived(){
        return mTotalQuantityReceived;
    }

    /**
     * Sets the ErpPoRefDate field.
     *
     * @param pErpPoRefDate
     *  Date to use to update the field.
     */
    public void setErpPoRefDate(Date pErpPoRefDate){
        this.mErpPoRefDate = pErpPoRefDate;
        setDirty(true);
    }
    /**
     * Retrieves the ErpPoRefDate field.
     *
     * @return
     *  Date containing the ErpPoRefDate field.
     */
    public Date getErpPoRefDate(){
        return mErpPoRefDate;
    }

    /**
     * Sets the ErpPoRefNum field.
     *
     * @param pErpPoRefNum
     *  String to use to update the field.
     */
    public void setErpPoRefNum(String pErpPoRefNum){
        this.mErpPoRefNum = pErpPoRefNum;
        setDirty(true);
    }
    /**
     * Retrieves the ErpPoRefNum field.
     *
     * @return
     *  String containing the ErpPoRefNum field.
     */
    public String getErpPoRefNum(){
        return mErpPoRefNum;
    }

    /**
     * Sets the ErpPoRefLineNum field.
     *
     * @param pErpPoRefLineNum
     *  int to use to update the field.
     */
    public void setErpPoRefLineNum(int pErpPoRefLineNum){
        this.mErpPoRefLineNum = pErpPoRefLineNum;
        setDirty(true);
    }
    /**
     * Retrieves the ErpPoRefLineNum field.
     *
     * @return
     *  int containing the ErpPoRefLineNum field.
     */
    public int getErpPoRefLineNum(){
        return mErpPoRefLineNum;
    }

    /**
     * Sets the AssetId field.
     *
     * @param pAssetId
     *  int to use to update the field.
     */
    public void setAssetId(int pAssetId){
        this.mAssetId = pAssetId;
        setDirty(true);
    }
    /**
     * Retrieves the AssetId field.
     *
     * @return
     *  int containing the AssetId field.
     */
    public int getAssetId(){
        return mAssetId;
    }

    /**
     * Sets the TaxRate field.
     *
     * @param pTaxRate
     *  java.math.BigDecimal to use to update the field.
     */
    public void setTaxRate(java.math.BigDecimal pTaxRate){
        this.mTaxRate = pTaxRate;
        setDirty(true);
    }
    /**
     * Retrieves the TaxRate field.
     *
     * @return
     *  java.math.BigDecimal containing the TaxRate field.
     */
    public java.math.BigDecimal getTaxRate(){
        return mTaxRate;
    }

    /**
     * Sets the TaxAmount field.
     *
     * @param pTaxAmount
     *  java.math.BigDecimal to use to update the field.
     */
    public void setTaxAmount(java.math.BigDecimal pTaxAmount){
        this.mTaxAmount = pTaxAmount;
        setDirty(true);
    }
    /**
     * Retrieves the TaxAmount field.
     *
     * @return
     *  java.math.BigDecimal containing the TaxAmount field.
     */
    public java.math.BigDecimal getTaxAmount(){
        return mTaxAmount;
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
     * Sets the OutboundPoNum field.
     *
     * @param pOutboundPoNum
     *  String to use to update the field.
     */
    public void setOutboundPoNum(String pOutboundPoNum){
        this.mOutboundPoNum = pOutboundPoNum;
        setDirty(true);
    }
    /**
     * Retrieves the OutboundPoNum field.
     *
     * @return
     *  String containing the OutboundPoNum field.
     */
    public String getOutboundPoNum(){
        return mOutboundPoNum;
    }

    /**
     * Sets the ServiceFee field.
     *
     * @param pServiceFee
     *  java.math.BigDecimal to use to update the field.
     */
    public void setServiceFee(java.math.BigDecimal pServiceFee){
        this.mServiceFee = pServiceFee;
        setDirty(true);
    }
    /**
     * Retrieves the ServiceFee field.
     *
     * @return
     *  java.math.BigDecimal containing the ServiceFee field.
     */
    public java.math.BigDecimal getServiceFee(){
        return mServiceFee;
    }


}
