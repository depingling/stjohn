
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        InvoiceCustReengData
 * Description:  This is a ValueObject class wrapping the database table CLW_INVOICE_CUST_REENG.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.InvoiceCustReengDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>InvoiceCustReengData</code> is a ValueObject class wrapping of the database table CLW_INVOICE_CUST_REENG.
 */
public class InvoiceCustReengData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 2855743738663681954L;
    private int mInvoiceCustReengId;// SQL type:NUMBER, not null
    private String mFileName;// SQL type:VARCHAR2
    private String mSender;// SQL type:VARCHAR2
    private String mReceiver;// SQL type:VARCHAR2
    private String mSetNum;// SQL type:VARCHAR2
    private String mInvoiceNum;// SQL type:VARCHAR2
    private String mRefInvoiceNum;// SQL type:VARCHAR2
    private Date mInvoiceDate;// SQL type:DATE
    private int mLineNum;// SQL type:NUMBER
    private int mCwSkuNum;// SQL type:NUMBER
    private String mCustSkuNum;// SQL type:VARCHAR2
    private String mItemUom;// SQL type:VARCHAR2
    private int mItemQty;// SQL type:NUMBER
    private java.math.BigDecimal mItemPrice;// SQL type:NUMBER
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private String mChargeType;// SQL type:VARCHAR2
    private String mInvoiceType;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public InvoiceCustReengData ()
    {
        mFileName = "";
        mSender = "";
        mReceiver = "";
        mSetNum = "";
        mInvoiceNum = "";
        mRefInvoiceNum = "";
        mCustSkuNum = "";
        mItemUom = "";
        mAddBy = "";
        mModBy = "";
        mChargeType = "";
        mInvoiceType = "";
    }

    /**
     * Constructor.
     */
    public InvoiceCustReengData(int parm1, String parm2, String parm3, String parm4, String parm5, String parm6, String parm7, Date parm8, int parm9, int parm10, String parm11, String parm12, int parm13, java.math.BigDecimal parm14, Date parm15, String parm16, Date parm17, String parm18, String parm19, String parm20)
    {
        mInvoiceCustReengId = parm1;
        mFileName = parm2;
        mSender = parm3;
        mReceiver = parm4;
        mSetNum = parm5;
        mInvoiceNum = parm6;
        mRefInvoiceNum = parm7;
        mInvoiceDate = parm8;
        mLineNum = parm9;
        mCwSkuNum = parm10;
        mCustSkuNum = parm11;
        mItemUom = parm12;
        mItemQty = parm13;
        mItemPrice = parm14;
        mAddDate = parm15;
        mAddBy = parm16;
        mModDate = parm17;
        mModBy = parm18;
        mChargeType = parm19;
        mInvoiceType = parm20;
        
    }

    /**
     * Creates a new InvoiceCustReengData
     *
     * @return
     *  Newly initialized InvoiceCustReengData object.
     */
    public static InvoiceCustReengData createValue ()
    {
        InvoiceCustReengData valueData = new InvoiceCustReengData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this InvoiceCustReengData object
     */
    public String toString()
    {
        return "[" + "InvoiceCustReengId=" + mInvoiceCustReengId + ", FileName=" + mFileName + ", Sender=" + mSender + ", Receiver=" + mReceiver + ", SetNum=" + mSetNum + ", InvoiceNum=" + mInvoiceNum + ", RefInvoiceNum=" + mRefInvoiceNum + ", InvoiceDate=" + mInvoiceDate + ", LineNum=" + mLineNum + ", CwSkuNum=" + mCwSkuNum + ", CustSkuNum=" + mCustSkuNum + ", ItemUom=" + mItemUom + ", ItemQty=" + mItemQty + ", ItemPrice=" + mItemPrice + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", ChargeType=" + mChargeType + ", InvoiceType=" + mInvoiceType + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("InvoiceCustReeng");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mInvoiceCustReengId));

        node =  doc.createElement("FileName");
        node.appendChild(doc.createTextNode(String.valueOf(mFileName)));
        root.appendChild(node);

        node =  doc.createElement("Sender");
        node.appendChild(doc.createTextNode(String.valueOf(mSender)));
        root.appendChild(node);

        node =  doc.createElement("Receiver");
        node.appendChild(doc.createTextNode(String.valueOf(mReceiver)));
        root.appendChild(node);

        node =  doc.createElement("SetNum");
        node.appendChild(doc.createTextNode(String.valueOf(mSetNum)));
        root.appendChild(node);

        node =  doc.createElement("InvoiceNum");
        node.appendChild(doc.createTextNode(String.valueOf(mInvoiceNum)));
        root.appendChild(node);

        node =  doc.createElement("RefInvoiceNum");
        node.appendChild(doc.createTextNode(String.valueOf(mRefInvoiceNum)));
        root.appendChild(node);

        node =  doc.createElement("InvoiceDate");
        node.appendChild(doc.createTextNode(String.valueOf(mInvoiceDate)));
        root.appendChild(node);

        node =  doc.createElement("LineNum");
        node.appendChild(doc.createTextNode(String.valueOf(mLineNum)));
        root.appendChild(node);

        node =  doc.createElement("CwSkuNum");
        node.appendChild(doc.createTextNode(String.valueOf(mCwSkuNum)));
        root.appendChild(node);

        node =  doc.createElement("CustSkuNum");
        node.appendChild(doc.createTextNode(String.valueOf(mCustSkuNum)));
        root.appendChild(node);

        node =  doc.createElement("ItemUom");
        node.appendChild(doc.createTextNode(String.valueOf(mItemUom)));
        root.appendChild(node);

        node =  doc.createElement("ItemQty");
        node.appendChild(doc.createTextNode(String.valueOf(mItemQty)));
        root.appendChild(node);

        node =  doc.createElement("ItemPrice");
        node.appendChild(doc.createTextNode(String.valueOf(mItemPrice)));
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

        node =  doc.createElement("ChargeType");
        node.appendChild(doc.createTextNode(String.valueOf(mChargeType)));
        root.appendChild(node);

        node =  doc.createElement("InvoiceType");
        node.appendChild(doc.createTextNode(String.valueOf(mInvoiceType)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the InvoiceCustReengId field is not cloned.
    *
    * @return InvoiceCustReengData object
    */
    public Object clone(){
        InvoiceCustReengData myClone = new InvoiceCustReengData();
        
        myClone.mFileName = mFileName;
        
        myClone.mSender = mSender;
        
        myClone.mReceiver = mReceiver;
        
        myClone.mSetNum = mSetNum;
        
        myClone.mInvoiceNum = mInvoiceNum;
        
        myClone.mRefInvoiceNum = mRefInvoiceNum;
        
        if(mInvoiceDate != null){
                myClone.mInvoiceDate = (Date) mInvoiceDate.clone();
        }
        
        myClone.mLineNum = mLineNum;
        
        myClone.mCwSkuNum = mCwSkuNum;
        
        myClone.mCustSkuNum = mCustSkuNum;
        
        myClone.mItemUom = mItemUom;
        
        myClone.mItemQty = mItemQty;
        
        myClone.mItemPrice = mItemPrice;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        myClone.mChargeType = mChargeType;
        
        myClone.mInvoiceType = mInvoiceType;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (InvoiceCustReengDataAccess.INVOICE_CUST_REENG_ID.equals(pFieldName)) {
            return getInvoiceCustReengId();
        } else if (InvoiceCustReengDataAccess.FILE_NAME.equals(pFieldName)) {
            return getFileName();
        } else if (InvoiceCustReengDataAccess.SENDER.equals(pFieldName)) {
            return getSender();
        } else if (InvoiceCustReengDataAccess.RECEIVER.equals(pFieldName)) {
            return getReceiver();
        } else if (InvoiceCustReengDataAccess.SET_NUM.equals(pFieldName)) {
            return getSetNum();
        } else if (InvoiceCustReengDataAccess.INVOICE_NUM.equals(pFieldName)) {
            return getInvoiceNum();
        } else if (InvoiceCustReengDataAccess.REF_INVOICE_NUM.equals(pFieldName)) {
            return getRefInvoiceNum();
        } else if (InvoiceCustReengDataAccess.INVOICE_DATE.equals(pFieldName)) {
            return getInvoiceDate();
        } else if (InvoiceCustReengDataAccess.LINE_NUM.equals(pFieldName)) {
            return getLineNum();
        } else if (InvoiceCustReengDataAccess.CW_SKU_NUM.equals(pFieldName)) {
            return getCwSkuNum();
        } else if (InvoiceCustReengDataAccess.CUST_SKU_NUM.equals(pFieldName)) {
            return getCustSkuNum();
        } else if (InvoiceCustReengDataAccess.ITEM_UOM.equals(pFieldName)) {
            return getItemUom();
        } else if (InvoiceCustReengDataAccess.ITEM_QTY.equals(pFieldName)) {
            return getItemQty();
        } else if (InvoiceCustReengDataAccess.ITEM_PRICE.equals(pFieldName)) {
            return getItemPrice();
        } else if (InvoiceCustReengDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (InvoiceCustReengDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (InvoiceCustReengDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (InvoiceCustReengDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (InvoiceCustReengDataAccess.CHARGE_TYPE.equals(pFieldName)) {
            return getChargeType();
        } else if (InvoiceCustReengDataAccess.INVOICE_TYPE.equals(pFieldName)) {
            return getInvoiceType();
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
        return InvoiceCustReengDataAccess.CLW_INVOICE_CUST_REENG;
    }

    
    /**
     * Sets the InvoiceCustReengId field. This field is required to be set in the database.
     *
     * @param pInvoiceCustReengId
     *  int to use to update the field.
     */
    public void setInvoiceCustReengId(int pInvoiceCustReengId){
        this.mInvoiceCustReengId = pInvoiceCustReengId;
        setDirty(true);
    }
    /**
     * Retrieves the InvoiceCustReengId field.
     *
     * @return
     *  int containing the InvoiceCustReengId field.
     */
    public int getInvoiceCustReengId(){
        return mInvoiceCustReengId;
    }

    /**
     * Sets the FileName field.
     *
     * @param pFileName
     *  String to use to update the field.
     */
    public void setFileName(String pFileName){
        this.mFileName = pFileName;
        setDirty(true);
    }
    /**
     * Retrieves the FileName field.
     *
     * @return
     *  String containing the FileName field.
     */
    public String getFileName(){
        return mFileName;
    }

    /**
     * Sets the Sender field.
     *
     * @param pSender
     *  String to use to update the field.
     */
    public void setSender(String pSender){
        this.mSender = pSender;
        setDirty(true);
    }
    /**
     * Retrieves the Sender field.
     *
     * @return
     *  String containing the Sender field.
     */
    public String getSender(){
        return mSender;
    }

    /**
     * Sets the Receiver field.
     *
     * @param pReceiver
     *  String to use to update the field.
     */
    public void setReceiver(String pReceiver){
        this.mReceiver = pReceiver;
        setDirty(true);
    }
    /**
     * Retrieves the Receiver field.
     *
     * @return
     *  String containing the Receiver field.
     */
    public String getReceiver(){
        return mReceiver;
    }

    /**
     * Sets the SetNum field.
     *
     * @param pSetNum
     *  String to use to update the field.
     */
    public void setSetNum(String pSetNum){
        this.mSetNum = pSetNum;
        setDirty(true);
    }
    /**
     * Retrieves the SetNum field.
     *
     * @return
     *  String containing the SetNum field.
     */
    public String getSetNum(){
        return mSetNum;
    }

    /**
     * Sets the InvoiceNum field.
     *
     * @param pInvoiceNum
     *  String to use to update the field.
     */
    public void setInvoiceNum(String pInvoiceNum){
        this.mInvoiceNum = pInvoiceNum;
        setDirty(true);
    }
    /**
     * Retrieves the InvoiceNum field.
     *
     * @return
     *  String containing the InvoiceNum field.
     */
    public String getInvoiceNum(){
        return mInvoiceNum;
    }

    /**
     * Sets the RefInvoiceNum field.
     *
     * @param pRefInvoiceNum
     *  String to use to update the field.
     */
    public void setRefInvoiceNum(String pRefInvoiceNum){
        this.mRefInvoiceNum = pRefInvoiceNum;
        setDirty(true);
    }
    /**
     * Retrieves the RefInvoiceNum field.
     *
     * @return
     *  String containing the RefInvoiceNum field.
     */
    public String getRefInvoiceNum(){
        return mRefInvoiceNum;
    }

    /**
     * Sets the InvoiceDate field.
     *
     * @param pInvoiceDate
     *  Date to use to update the field.
     */
    public void setInvoiceDate(Date pInvoiceDate){
        this.mInvoiceDate = pInvoiceDate;
        setDirty(true);
    }
    /**
     * Retrieves the InvoiceDate field.
     *
     * @return
     *  Date containing the InvoiceDate field.
     */
    public Date getInvoiceDate(){
        return mInvoiceDate;
    }

    /**
     * Sets the LineNum field.
     *
     * @param pLineNum
     *  int to use to update the field.
     */
    public void setLineNum(int pLineNum){
        this.mLineNum = pLineNum;
        setDirty(true);
    }
    /**
     * Retrieves the LineNum field.
     *
     * @return
     *  int containing the LineNum field.
     */
    public int getLineNum(){
        return mLineNum;
    }

    /**
     * Sets the CwSkuNum field.
     *
     * @param pCwSkuNum
     *  int to use to update the field.
     */
    public void setCwSkuNum(int pCwSkuNum){
        this.mCwSkuNum = pCwSkuNum;
        setDirty(true);
    }
    /**
     * Retrieves the CwSkuNum field.
     *
     * @return
     *  int containing the CwSkuNum field.
     */
    public int getCwSkuNum(){
        return mCwSkuNum;
    }

    /**
     * Sets the CustSkuNum field.
     *
     * @param pCustSkuNum
     *  String to use to update the field.
     */
    public void setCustSkuNum(String pCustSkuNum){
        this.mCustSkuNum = pCustSkuNum;
        setDirty(true);
    }
    /**
     * Retrieves the CustSkuNum field.
     *
     * @return
     *  String containing the CustSkuNum field.
     */
    public String getCustSkuNum(){
        return mCustSkuNum;
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
     * Sets the ItemQty field.
     *
     * @param pItemQty
     *  int to use to update the field.
     */
    public void setItemQty(int pItemQty){
        this.mItemQty = pItemQty;
        setDirty(true);
    }
    /**
     * Retrieves the ItemQty field.
     *
     * @return
     *  int containing the ItemQty field.
     */
    public int getItemQty(){
        return mItemQty;
    }

    /**
     * Sets the ItemPrice field.
     *
     * @param pItemPrice
     *  java.math.BigDecimal to use to update the field.
     */
    public void setItemPrice(java.math.BigDecimal pItemPrice){
        this.mItemPrice = pItemPrice;
        setDirty(true);
    }
    /**
     * Retrieves the ItemPrice field.
     *
     * @return
     *  java.math.BigDecimal containing the ItemPrice field.
     */
    public java.math.BigDecimal getItemPrice(){
        return mItemPrice;
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
     * Sets the ChargeType field.
     *
     * @param pChargeType
     *  String to use to update the field.
     */
    public void setChargeType(String pChargeType){
        this.mChargeType = pChargeType;
        setDirty(true);
    }
    /**
     * Retrieves the ChargeType field.
     *
     * @return
     *  String containing the ChargeType field.
     */
    public String getChargeType(){
        return mChargeType;
    }

    /**
     * Sets the InvoiceType field.
     *
     * @param pInvoiceType
     *  String to use to update the field.
     */
    public void setInvoiceType(String pInvoiceType){
        this.mInvoiceType = pInvoiceType;
        setDirty(true);
    }
    /**
     * Retrieves the InvoiceType field.
     *
     * @return
     *  String containing the InvoiceType field.
     */
    public String getInvoiceType(){
        return mInvoiceType;
    }


}
