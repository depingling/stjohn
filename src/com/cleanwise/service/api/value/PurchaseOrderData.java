
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        PurchaseOrderData
 * Description:  This is a ValueObject class wrapping the database table CLW_PURCHASE_ORDER.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.PurchaseOrderDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>PurchaseOrderData</code> is a ValueObject class wrapping of the database table CLW_PURCHASE_ORDER.
 */
public class PurchaseOrderData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 4886292063091893626L;
    private int mPurchaseOrderId;// SQL type:NUMBER, not null
    private int mOrderId;// SQL type:NUMBER
    private String mErpPoNum;// SQL type:VARCHAR2, not null
    private Date mPoDate;// SQL type:DATE
    private String mDistErpNum;// SQL type:VARCHAR2
    private java.math.BigDecimal mLineItemTotal;// SQL type:NUMBER
    private java.math.BigDecimal mPurchaseOrderTotal;// SQL type:NUMBER, not null
    private String mPurchaseOrderStatusCd;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private String mPurchOrdManifestStatusCd;// SQL type:VARCHAR2
    private int mStoreId;// SQL type:NUMBER
    private String mErpSystemCd;// SQL type:VARCHAR2
    private java.math.BigDecimal mTaxTotal;// SQL type:NUMBER
    private String mErpPoRefNum;// SQL type:VARCHAR2
    private Date mErpPoRefDate;// SQL type:DATE
    private String mOutboundPoNum;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public PurchaseOrderData ()
    {
        mErpPoNum = "";
        mDistErpNum = "";
        mPurchaseOrderStatusCd = "";
        mAddBy = "";
        mModBy = "";
        mPurchOrdManifestStatusCd = "";
        mErpSystemCd = "";
        mErpPoRefNum = "";
        mOutboundPoNum = "";
    }

    /**
     * Constructor.
     */
    public PurchaseOrderData(int parm1, int parm2, String parm3, Date parm4, String parm5, java.math.BigDecimal parm6, java.math.BigDecimal parm7, String parm8, Date parm9, String parm10, Date parm11, String parm12, String parm13, int parm14, String parm15, java.math.BigDecimal parm16, String parm17, Date parm18, String parm19)
    {
        mPurchaseOrderId = parm1;
        mOrderId = parm2;
        mErpPoNum = parm3;
        mPoDate = parm4;
        mDistErpNum = parm5;
        mLineItemTotal = parm6;
        mPurchaseOrderTotal = parm7;
        mPurchaseOrderStatusCd = parm8;
        mAddDate = parm9;
        mAddBy = parm10;
        mModDate = parm11;
        mModBy = parm12;
        mPurchOrdManifestStatusCd = parm13;
        mStoreId = parm14;
        mErpSystemCd = parm15;
        mTaxTotal = parm16;
        mErpPoRefNum = parm17;
        mErpPoRefDate = parm18;
        mOutboundPoNum = parm19;
        
    }

    /**
     * Creates a new PurchaseOrderData
     *
     * @return
     *  Newly initialized PurchaseOrderData object.
     */
    public static PurchaseOrderData createValue ()
    {
        PurchaseOrderData valueData = new PurchaseOrderData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this PurchaseOrderData object
     */
    public String toString()
    {
        return "[" + "PurchaseOrderId=" + mPurchaseOrderId + ", OrderId=" + mOrderId + ", ErpPoNum=" + mErpPoNum + ", PoDate=" + mPoDate + ", DistErpNum=" + mDistErpNum + ", LineItemTotal=" + mLineItemTotal + ", PurchaseOrderTotal=" + mPurchaseOrderTotal + ", PurchaseOrderStatusCd=" + mPurchaseOrderStatusCd + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", PurchOrdManifestStatusCd=" + mPurchOrdManifestStatusCd + ", StoreId=" + mStoreId + ", ErpSystemCd=" + mErpSystemCd + ", TaxTotal=" + mTaxTotal + ", ErpPoRefNum=" + mErpPoRefNum + ", ErpPoRefDate=" + mErpPoRefDate + ", OutboundPoNum=" + mOutboundPoNum + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("PurchaseOrder");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mPurchaseOrderId));

        node =  doc.createElement("OrderId");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderId)));
        root.appendChild(node);

        node =  doc.createElement("ErpPoNum");
        node.appendChild(doc.createTextNode(String.valueOf(mErpPoNum)));
        root.appendChild(node);

        node =  doc.createElement("PoDate");
        node.appendChild(doc.createTextNode(String.valueOf(mPoDate)));
        root.appendChild(node);

        node =  doc.createElement("DistErpNum");
        node.appendChild(doc.createTextNode(String.valueOf(mDistErpNum)));
        root.appendChild(node);

        node =  doc.createElement("LineItemTotal");
        node.appendChild(doc.createTextNode(String.valueOf(mLineItemTotal)));
        root.appendChild(node);

        node =  doc.createElement("PurchaseOrderTotal");
        node.appendChild(doc.createTextNode(String.valueOf(mPurchaseOrderTotal)));
        root.appendChild(node);

        node =  doc.createElement("PurchaseOrderStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mPurchaseOrderStatusCd)));
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

        node =  doc.createElement("PurchOrdManifestStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mPurchOrdManifestStatusCd)));
        root.appendChild(node);

        node =  doc.createElement("StoreId");
        node.appendChild(doc.createTextNode(String.valueOf(mStoreId)));
        root.appendChild(node);

        node =  doc.createElement("ErpSystemCd");
        node.appendChild(doc.createTextNode(String.valueOf(mErpSystemCd)));
        root.appendChild(node);

        node =  doc.createElement("TaxTotal");
        node.appendChild(doc.createTextNode(String.valueOf(mTaxTotal)));
        root.appendChild(node);

        node =  doc.createElement("ErpPoRefNum");
        node.appendChild(doc.createTextNode(String.valueOf(mErpPoRefNum)));
        root.appendChild(node);

        node =  doc.createElement("ErpPoRefDate");
        node.appendChild(doc.createTextNode(String.valueOf(mErpPoRefDate)));
        root.appendChild(node);

        node =  doc.createElement("OutboundPoNum");
        node.appendChild(doc.createTextNode(String.valueOf(mOutboundPoNum)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the PurchaseOrderId field is not cloned.
    *
    * @return PurchaseOrderData object
    */
    public Object clone(){
        PurchaseOrderData myClone = new PurchaseOrderData();
        
        myClone.mOrderId = mOrderId;
        
        myClone.mErpPoNum = mErpPoNum;
        
        if(mPoDate != null){
                myClone.mPoDate = (Date) mPoDate.clone();
        }
        
        myClone.mDistErpNum = mDistErpNum;
        
        myClone.mLineItemTotal = mLineItemTotal;
        
        myClone.mPurchaseOrderTotal = mPurchaseOrderTotal;
        
        myClone.mPurchaseOrderStatusCd = mPurchaseOrderStatusCd;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        myClone.mPurchOrdManifestStatusCd = mPurchOrdManifestStatusCd;
        
        myClone.mStoreId = mStoreId;
        
        myClone.mErpSystemCd = mErpSystemCd;
        
        myClone.mTaxTotal = mTaxTotal;
        
        myClone.mErpPoRefNum = mErpPoRefNum;
        
        if(mErpPoRefDate != null){
                myClone.mErpPoRefDate = (Date) mErpPoRefDate.clone();
        }
        
        myClone.mOutboundPoNum = mOutboundPoNum;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (PurchaseOrderDataAccess.PURCHASE_ORDER_ID.equals(pFieldName)) {
            return getPurchaseOrderId();
        } else if (PurchaseOrderDataAccess.ORDER_ID.equals(pFieldName)) {
            return getOrderId();
        } else if (PurchaseOrderDataAccess.ERP_PO_NUM.equals(pFieldName)) {
            return getErpPoNum();
        } else if (PurchaseOrderDataAccess.PO_DATE.equals(pFieldName)) {
            return getPoDate();
        } else if (PurchaseOrderDataAccess.DIST_ERP_NUM.equals(pFieldName)) {
            return getDistErpNum();
        } else if (PurchaseOrderDataAccess.LINE_ITEM_TOTAL.equals(pFieldName)) {
            return getLineItemTotal();
        } else if (PurchaseOrderDataAccess.PURCHASE_ORDER_TOTAL.equals(pFieldName)) {
            return getPurchaseOrderTotal();
        } else if (PurchaseOrderDataAccess.PURCHASE_ORDER_STATUS_CD.equals(pFieldName)) {
            return getPurchaseOrderStatusCd();
        } else if (PurchaseOrderDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (PurchaseOrderDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (PurchaseOrderDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (PurchaseOrderDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (PurchaseOrderDataAccess.PURCH_ORD_MANIFEST_STATUS_CD.equals(pFieldName)) {
            return getPurchOrdManifestStatusCd();
        } else if (PurchaseOrderDataAccess.STORE_ID.equals(pFieldName)) {
            return getStoreId();
        } else if (PurchaseOrderDataAccess.ERP_SYSTEM_CD.equals(pFieldName)) {
            return getErpSystemCd();
        } else if (PurchaseOrderDataAccess.TAX_TOTAL.equals(pFieldName)) {
            return getTaxTotal();
        } else if (PurchaseOrderDataAccess.ERP_PO_REF_NUM.equals(pFieldName)) {
            return getErpPoRefNum();
        } else if (PurchaseOrderDataAccess.ERP_PO_REF_DATE.equals(pFieldName)) {
            return getErpPoRefDate();
        } else if (PurchaseOrderDataAccess.OUTBOUND_PO_NUM.equals(pFieldName)) {
            return getOutboundPoNum();
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
        return PurchaseOrderDataAccess.CLW_PURCHASE_ORDER;
    }

    
    /**
     * Sets the PurchaseOrderId field. This field is required to be set in the database.
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
     * Sets the OrderId field.
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
     * Sets the ErpPoNum field. This field is required to be set in the database.
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
     * Sets the PoDate field.
     *
     * @param pPoDate
     *  Date to use to update the field.
     */
    public void setPoDate(Date pPoDate){
        this.mPoDate = pPoDate;
        setDirty(true);
    }
    /**
     * Retrieves the PoDate field.
     *
     * @return
     *  Date containing the PoDate field.
     */
    public Date getPoDate(){
        return mPoDate;
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
     * Sets the LineItemTotal field.
     *
     * @param pLineItemTotal
     *  java.math.BigDecimal to use to update the field.
     */
    public void setLineItemTotal(java.math.BigDecimal pLineItemTotal){
        this.mLineItemTotal = pLineItemTotal;
        setDirty(true);
    }
    /**
     * Retrieves the LineItemTotal field.
     *
     * @return
     *  java.math.BigDecimal containing the LineItemTotal field.
     */
    public java.math.BigDecimal getLineItemTotal(){
        return mLineItemTotal;
    }

    /**
     * Sets the PurchaseOrderTotal field. This field is required to be set in the database.
     *
     * @param pPurchaseOrderTotal
     *  java.math.BigDecimal to use to update the field.
     */
    public void setPurchaseOrderTotal(java.math.BigDecimal pPurchaseOrderTotal){
        this.mPurchaseOrderTotal = pPurchaseOrderTotal;
        setDirty(true);
    }
    /**
     * Retrieves the PurchaseOrderTotal field.
     *
     * @return
     *  java.math.BigDecimal containing the PurchaseOrderTotal field.
     */
    public java.math.BigDecimal getPurchaseOrderTotal(){
        return mPurchaseOrderTotal;
    }

    /**
     * Sets the PurchaseOrderStatusCd field.
     *
     * @param pPurchaseOrderStatusCd
     *  String to use to update the field.
     */
    public void setPurchaseOrderStatusCd(String pPurchaseOrderStatusCd){
        this.mPurchaseOrderStatusCd = pPurchaseOrderStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the PurchaseOrderStatusCd field.
     *
     * @return
     *  String containing the PurchaseOrderStatusCd field.
     */
    public String getPurchaseOrderStatusCd(){
        return mPurchaseOrderStatusCd;
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
     * Sets the PurchOrdManifestStatusCd field.
     *
     * @param pPurchOrdManifestStatusCd
     *  String to use to update the field.
     */
    public void setPurchOrdManifestStatusCd(String pPurchOrdManifestStatusCd){
        this.mPurchOrdManifestStatusCd = pPurchOrdManifestStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the PurchOrdManifestStatusCd field.
     *
     * @return
     *  String containing the PurchOrdManifestStatusCd field.
     */
    public String getPurchOrdManifestStatusCd(){
        return mPurchOrdManifestStatusCd;
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
     * Sets the ErpSystemCd field.
     *
     * @param pErpSystemCd
     *  String to use to update the field.
     */
    public void setErpSystemCd(String pErpSystemCd){
        this.mErpSystemCd = pErpSystemCd;
        setDirty(true);
    }
    /**
     * Retrieves the ErpSystemCd field.
     *
     * @return
     *  String containing the ErpSystemCd field.
     */
    public String getErpSystemCd(){
        return mErpSystemCd;
    }

    /**
     * Sets the TaxTotal field.
     *
     * @param pTaxTotal
     *  java.math.BigDecimal to use to update the field.
     */
    public void setTaxTotal(java.math.BigDecimal pTaxTotal){
        this.mTaxTotal = pTaxTotal;
        setDirty(true);
    }
    /**
     * Retrieves the TaxTotal field.
     *
     * @return
     *  java.math.BigDecimal containing the TaxTotal field.
     */
    public java.math.BigDecimal getTaxTotal(){
        return mTaxTotal;
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


}
