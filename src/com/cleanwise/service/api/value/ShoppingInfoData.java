
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ShoppingInfoData
 * Description:  This is a ValueObject class wrapping the database table CLW_SHOPPING_INFO.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.ShoppingInfoDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>ShoppingInfoData</code> is a ValueObject class wrapping of the database table CLW_SHOPPING_INFO.
 */
public class ShoppingInfoData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -6618613378993146379L;
    private int mShoppingInfoId;// SQL type:NUMBER, not null
    private int mOrderGuideId;// SQL type:NUMBER
    private int mItemId;// SQL type:NUMBER
    private String mShortDesc;// SQL type:VARCHAR2
    private String mValue;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private int mSiteId;// SQL type:NUMBER
    private int mOrderId;// SQL type:NUMBER
    private String mMessageKey;// SQL type:VARCHAR2
    private String mArg0;// SQL type:VARCHAR2
    private String mArg0TypeCd;// SQL type:VARCHAR2
    private String mArg1;// SQL type:VARCHAR2
    private String mArg1TypeCd;// SQL type:VARCHAR2
    private String mArg2;// SQL type:VARCHAR2
    private String mArg2TypeCd;// SQL type:VARCHAR2
    private String mArg3;// SQL type:VARCHAR2
    private String mArg3TypeCd;// SQL type:VARCHAR2
    private String mShoppingInfoStatusCd;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public ShoppingInfoData ()
    {
        mShortDesc = "";
        mValue = "";
        mAddBy = "";
        mModBy = "";
        mMessageKey = "";
        mArg0 = "";
        mArg0TypeCd = "";
        mArg1 = "";
        mArg1TypeCd = "";
        mArg2 = "";
        mArg2TypeCd = "";
        mArg3 = "";
        mArg3TypeCd = "";
        mShoppingInfoStatusCd = "";
    }

    /**
     * Constructor.
     */
    public ShoppingInfoData(int parm1, int parm2, int parm3, String parm4, String parm5, Date parm6, String parm7, Date parm8, String parm9, int parm10, int parm11, String parm12, String parm13, String parm14, String parm15, String parm16, String parm17, String parm18, String parm19, String parm20, String parm21)
    {
        mShoppingInfoId = parm1;
        mOrderGuideId = parm2;
        mItemId = parm3;
        mShortDesc = parm4;
        mValue = parm5;
        mAddDate = parm6;
        mAddBy = parm7;
        mModDate = parm8;
        mModBy = parm9;
        mSiteId = parm10;
        mOrderId = parm11;
        mMessageKey = parm12;
        mArg0 = parm13;
        mArg0TypeCd = parm14;
        mArg1 = parm15;
        mArg1TypeCd = parm16;
        mArg2 = parm17;
        mArg2TypeCd = parm18;
        mArg3 = parm19;
        mArg3TypeCd = parm20;
        mShoppingInfoStatusCd = parm21;
        
    }

    /**
     * Creates a new ShoppingInfoData
     *
     * @return
     *  Newly initialized ShoppingInfoData object.
     */
    public static ShoppingInfoData createValue ()
    {
        ShoppingInfoData valueData = new ShoppingInfoData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ShoppingInfoData object
     */
    public String toString()
    {
        return "[" + "ShoppingInfoId=" + mShoppingInfoId + ", OrderGuideId=" + mOrderGuideId + ", ItemId=" + mItemId + ", ShortDesc=" + mShortDesc + ", Value=" + mValue + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", SiteId=" + mSiteId + ", OrderId=" + mOrderId + ", MessageKey=" + mMessageKey + ", Arg0=" + mArg0 + ", Arg0TypeCd=" + mArg0TypeCd + ", Arg1=" + mArg1 + ", Arg1TypeCd=" + mArg1TypeCd + ", Arg2=" + mArg2 + ", Arg2TypeCd=" + mArg2TypeCd + ", Arg3=" + mArg3 + ", Arg3TypeCd=" + mArg3TypeCd + ", ShoppingInfoStatusCd=" + mShoppingInfoStatusCd + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("ShoppingInfo");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mShoppingInfoId));

        node =  doc.createElement("OrderGuideId");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderGuideId)));
        root.appendChild(node);

        node =  doc.createElement("ItemId");
        node.appendChild(doc.createTextNode(String.valueOf(mItemId)));
        root.appendChild(node);

        node =  doc.createElement("ShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mShortDesc)));
        root.appendChild(node);

        node =  doc.createElement("Value");
        node.appendChild(doc.createTextNode(String.valueOf(mValue)));
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

        node =  doc.createElement("SiteId");
        node.appendChild(doc.createTextNode(String.valueOf(mSiteId)));
        root.appendChild(node);

        node =  doc.createElement("OrderId");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderId)));
        root.appendChild(node);

        node =  doc.createElement("MessageKey");
        node.appendChild(doc.createTextNode(String.valueOf(mMessageKey)));
        root.appendChild(node);

        node =  doc.createElement("Arg0");
        node.appendChild(doc.createTextNode(String.valueOf(mArg0)));
        root.appendChild(node);

        node =  doc.createElement("Arg0TypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mArg0TypeCd)));
        root.appendChild(node);

        node =  doc.createElement("Arg1");
        node.appendChild(doc.createTextNode(String.valueOf(mArg1)));
        root.appendChild(node);

        node =  doc.createElement("Arg1TypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mArg1TypeCd)));
        root.appendChild(node);

        node =  doc.createElement("Arg2");
        node.appendChild(doc.createTextNode(String.valueOf(mArg2)));
        root.appendChild(node);

        node =  doc.createElement("Arg2TypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mArg2TypeCd)));
        root.appendChild(node);

        node =  doc.createElement("Arg3");
        node.appendChild(doc.createTextNode(String.valueOf(mArg3)));
        root.appendChild(node);

        node =  doc.createElement("Arg3TypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mArg3TypeCd)));
        root.appendChild(node);

        node =  doc.createElement("ShoppingInfoStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mShoppingInfoStatusCd)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the ShoppingInfoId field is not cloned.
    *
    * @return ShoppingInfoData object
    */
    public Object clone(){
        ShoppingInfoData myClone = new ShoppingInfoData();
        
        myClone.mOrderGuideId = mOrderGuideId;
        
        myClone.mItemId = mItemId;
        
        myClone.mShortDesc = mShortDesc;
        
        myClone.mValue = mValue;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        myClone.mSiteId = mSiteId;
        
        myClone.mOrderId = mOrderId;
        
        myClone.mMessageKey = mMessageKey;
        
        myClone.mArg0 = mArg0;
        
        myClone.mArg0TypeCd = mArg0TypeCd;
        
        myClone.mArg1 = mArg1;
        
        myClone.mArg1TypeCd = mArg1TypeCd;
        
        myClone.mArg2 = mArg2;
        
        myClone.mArg2TypeCd = mArg2TypeCd;
        
        myClone.mArg3 = mArg3;
        
        myClone.mArg3TypeCd = mArg3TypeCd;
        
        myClone.mShoppingInfoStatusCd = mShoppingInfoStatusCd;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (ShoppingInfoDataAccess.SHOPPING_INFO_ID.equals(pFieldName)) {
            return getShoppingInfoId();
        } else if (ShoppingInfoDataAccess.ORDER_GUIDE_ID.equals(pFieldName)) {
            return getOrderGuideId();
        } else if (ShoppingInfoDataAccess.ITEM_ID.equals(pFieldName)) {
            return getItemId();
        } else if (ShoppingInfoDataAccess.SHORT_DESC.equals(pFieldName)) {
            return getShortDesc();
        } else if (ShoppingInfoDataAccess.CLW_VALUE.equals(pFieldName)) {
            return getValue();
        } else if (ShoppingInfoDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (ShoppingInfoDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (ShoppingInfoDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (ShoppingInfoDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (ShoppingInfoDataAccess.SITE_ID.equals(pFieldName)) {
            return getSiteId();
        } else if (ShoppingInfoDataAccess.ORDER_ID.equals(pFieldName)) {
            return getOrderId();
        } else if (ShoppingInfoDataAccess.MESSAGE_KEY.equals(pFieldName)) {
            return getMessageKey();
        } else if (ShoppingInfoDataAccess.ARG0.equals(pFieldName)) {
            return getArg0();
        } else if (ShoppingInfoDataAccess.ARG0_TYPE_CD.equals(pFieldName)) {
            return getArg0TypeCd();
        } else if (ShoppingInfoDataAccess.ARG1.equals(pFieldName)) {
            return getArg1();
        } else if (ShoppingInfoDataAccess.ARG1_TYPE_CD.equals(pFieldName)) {
            return getArg1TypeCd();
        } else if (ShoppingInfoDataAccess.ARG2.equals(pFieldName)) {
            return getArg2();
        } else if (ShoppingInfoDataAccess.ARG2_TYPE_CD.equals(pFieldName)) {
            return getArg2TypeCd();
        } else if (ShoppingInfoDataAccess.ARG3.equals(pFieldName)) {
            return getArg3();
        } else if (ShoppingInfoDataAccess.ARG3_TYPE_CD.equals(pFieldName)) {
            return getArg3TypeCd();
        } else if (ShoppingInfoDataAccess.SHOPPING_INFO_STATUS_CD.equals(pFieldName)) {
            return getShoppingInfoStatusCd();
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
        return ShoppingInfoDataAccess.CLW_SHOPPING_INFO;
    }

    
    /**
     * Sets the ShoppingInfoId field. This field is required to be set in the database.
     *
     * @param pShoppingInfoId
     *  int to use to update the field.
     */
    public void setShoppingInfoId(int pShoppingInfoId){
        this.mShoppingInfoId = pShoppingInfoId;
        setDirty(true);
    }
    /**
     * Retrieves the ShoppingInfoId field.
     *
     * @return
     *  int containing the ShoppingInfoId field.
     */
    public int getShoppingInfoId(){
        return mShoppingInfoId;
    }

    /**
     * Sets the OrderGuideId field.
     *
     * @param pOrderGuideId
     *  int to use to update the field.
     */
    public void setOrderGuideId(int pOrderGuideId){
        this.mOrderGuideId = pOrderGuideId;
        setDirty(true);
    }
    /**
     * Retrieves the OrderGuideId field.
     *
     * @return
     *  int containing the OrderGuideId field.
     */
    public int getOrderGuideId(){
        return mOrderGuideId;
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
     * Sets the Value field.
     *
     * @param pValue
     *  String to use to update the field.
     */
    public void setValue(String pValue){
        this.mValue = pValue;
        setDirty(true);
    }
    /**
     * Retrieves the Value field.
     *
     * @return
     *  String containing the Value field.
     */
    public String getValue(){
        return mValue;
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
     * Sets the SiteId field.
     *
     * @param pSiteId
     *  int to use to update the field.
     */
    public void setSiteId(int pSiteId){
        this.mSiteId = pSiteId;
        setDirty(true);
    }
    /**
     * Retrieves the SiteId field.
     *
     * @return
     *  int containing the SiteId field.
     */
    public int getSiteId(){
        return mSiteId;
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
     * Sets the MessageKey field.
     *
     * @param pMessageKey
     *  String to use to update the field.
     */
    public void setMessageKey(String pMessageKey){
        this.mMessageKey = pMessageKey;
        setDirty(true);
    }
    /**
     * Retrieves the MessageKey field.
     *
     * @return
     *  String containing the MessageKey field.
     */
    public String getMessageKey(){
        return mMessageKey;
    }

    /**
     * Sets the Arg0 field.
     *
     * @param pArg0
     *  String to use to update the field.
     */
    public void setArg0(String pArg0){
        this.mArg0 = pArg0;
        setDirty(true);
    }
    /**
     * Retrieves the Arg0 field.
     *
     * @return
     *  String containing the Arg0 field.
     */
    public String getArg0(){
        return mArg0;
    }

    /**
     * Sets the Arg0TypeCd field.
     *
     * @param pArg0TypeCd
     *  String to use to update the field.
     */
    public void setArg0TypeCd(String pArg0TypeCd){
        this.mArg0TypeCd = pArg0TypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the Arg0TypeCd field.
     *
     * @return
     *  String containing the Arg0TypeCd field.
     */
    public String getArg0TypeCd(){
        return mArg0TypeCd;
    }

    /**
     * Sets the Arg1 field.
     *
     * @param pArg1
     *  String to use to update the field.
     */
    public void setArg1(String pArg1){
        this.mArg1 = pArg1;
        setDirty(true);
    }
    /**
     * Retrieves the Arg1 field.
     *
     * @return
     *  String containing the Arg1 field.
     */
    public String getArg1(){
        return mArg1;
    }

    /**
     * Sets the Arg1TypeCd field.
     *
     * @param pArg1TypeCd
     *  String to use to update the field.
     */
    public void setArg1TypeCd(String pArg1TypeCd){
        this.mArg1TypeCd = pArg1TypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the Arg1TypeCd field.
     *
     * @return
     *  String containing the Arg1TypeCd field.
     */
    public String getArg1TypeCd(){
        return mArg1TypeCd;
    }

    /**
     * Sets the Arg2 field.
     *
     * @param pArg2
     *  String to use to update the field.
     */
    public void setArg2(String pArg2){
        this.mArg2 = pArg2;
        setDirty(true);
    }
    /**
     * Retrieves the Arg2 field.
     *
     * @return
     *  String containing the Arg2 field.
     */
    public String getArg2(){
        return mArg2;
    }

    /**
     * Sets the Arg2TypeCd field.
     *
     * @param pArg2TypeCd
     *  String to use to update the field.
     */
    public void setArg2TypeCd(String pArg2TypeCd){
        this.mArg2TypeCd = pArg2TypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the Arg2TypeCd field.
     *
     * @return
     *  String containing the Arg2TypeCd field.
     */
    public String getArg2TypeCd(){
        return mArg2TypeCd;
    }

    /**
     * Sets the Arg3 field.
     *
     * @param pArg3
     *  String to use to update the field.
     */
    public void setArg3(String pArg3){
        this.mArg3 = pArg3;
        setDirty(true);
    }
    /**
     * Retrieves the Arg3 field.
     *
     * @return
     *  String containing the Arg3 field.
     */
    public String getArg3(){
        return mArg3;
    }

    /**
     * Sets the Arg3TypeCd field.
     *
     * @param pArg3TypeCd
     *  String to use to update the field.
     */
    public void setArg3TypeCd(String pArg3TypeCd){
        this.mArg3TypeCd = pArg3TypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the Arg3TypeCd field.
     *
     * @return
     *  String containing the Arg3TypeCd field.
     */
    public String getArg3TypeCd(){
        return mArg3TypeCd;
    }

    /**
     * Sets the ShoppingInfoStatusCd field.
     *
     * @param pShoppingInfoStatusCd
     *  String to use to update the field.
     */
    public void setShoppingInfoStatusCd(String pShoppingInfoStatusCd){
        this.mShoppingInfoStatusCd = pShoppingInfoStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the ShoppingInfoStatusCd field.
     *
     * @return
     *  String containing the ShoppingInfoStatusCd field.
     */
    public String getShoppingInfoStatusCd(){
        return mShoppingInfoStatusCd;
    }


}
