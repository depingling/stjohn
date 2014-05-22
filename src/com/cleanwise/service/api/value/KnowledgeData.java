
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        KnowledgeData
 * Description:  This is a ValueObject class wrapping the database table CLW_KNOWLEDGE.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.KnowledgeDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>KnowledgeData</code> is a ValueObject class wrapping of the database table CLW_KNOWLEDGE.
 */
public class KnowledgeData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -5859643489808433898L;
    private int mKnowledgeId;// SQL type:NUMBER, not null
    private int mBusEntityId;// SQL type:NUMBER
    private int mUserId;// SQL type:NUMBER
    private int mItemId;// SQL type:NUMBER
    private String mShortDesc;// SQL type:VARCHAR2
    private String mLongDesc;// SQL type:VARCHAR2
    private String mKnowledgeCategoryCd;// SQL type:VARCHAR2
    private Date mEffDate;// SQL type:DATE
    private Date mExpDate;// SQL type:DATE
    private String mComments;// SQL type:VARCHAR2
    private String mKnowledgeStatusCd;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE
    private Date mAddTime;// SQL type:DATE
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE
    private String mModBy;// SQL type:VARCHAR2
    private int mStoreId;// SQL type:NUMBER

    /**
     * Constructor.
     */
    public KnowledgeData ()
    {
        mShortDesc = "";
        mLongDesc = "";
        mKnowledgeCategoryCd = "";
        mComments = "";
        mKnowledgeStatusCd = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public KnowledgeData(int parm1, int parm2, int parm3, int parm4, String parm5, String parm6, String parm7, Date parm8, Date parm9, String parm10, String parm11, Date parm12, Date parm13, String parm14, Date parm15, String parm16, int parm17)
    {
        mKnowledgeId = parm1;
        mBusEntityId = parm2;
        mUserId = parm3;
        mItemId = parm4;
        mShortDesc = parm5;
        mLongDesc = parm6;
        mKnowledgeCategoryCd = parm7;
        mEffDate = parm8;
        mExpDate = parm9;
        mComments = parm10;
        mKnowledgeStatusCd = parm11;
        mAddDate = parm12;
        mAddTime = parm13;
        mAddBy = parm14;
        mModDate = parm15;
        mModBy = parm16;
        mStoreId = parm17;
        
    }

    /**
     * Creates a new KnowledgeData
     *
     * @return
     *  Newly initialized KnowledgeData object.
     */
    public static KnowledgeData createValue ()
    {
        KnowledgeData valueData = new KnowledgeData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this KnowledgeData object
     */
    public String toString()
    {
        return "[" + "KnowledgeId=" + mKnowledgeId + ", BusEntityId=" + mBusEntityId + ", UserId=" + mUserId + ", ItemId=" + mItemId + ", ShortDesc=" + mShortDesc + ", LongDesc=" + mLongDesc + ", KnowledgeCategoryCd=" + mKnowledgeCategoryCd + ", EffDate=" + mEffDate + ", ExpDate=" + mExpDate + ", Comments=" + mComments + ", KnowledgeStatusCd=" + mKnowledgeStatusCd + ", AddDate=" + mAddDate + ", AddTime=" + mAddTime + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", StoreId=" + mStoreId + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("Knowledge");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mKnowledgeId));

        node =  doc.createElement("BusEntityId");
        node.appendChild(doc.createTextNode(String.valueOf(mBusEntityId)));
        root.appendChild(node);

        node =  doc.createElement("UserId");
        node.appendChild(doc.createTextNode(String.valueOf(mUserId)));
        root.appendChild(node);

        node =  doc.createElement("ItemId");
        node.appendChild(doc.createTextNode(String.valueOf(mItemId)));
        root.appendChild(node);

        node =  doc.createElement("ShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mShortDesc)));
        root.appendChild(node);

        node =  doc.createElement("LongDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mLongDesc)));
        root.appendChild(node);

        node =  doc.createElement("KnowledgeCategoryCd");
        node.appendChild(doc.createTextNode(String.valueOf(mKnowledgeCategoryCd)));
        root.appendChild(node);

        node =  doc.createElement("EffDate");
        node.appendChild(doc.createTextNode(String.valueOf(mEffDate)));
        root.appendChild(node);

        node =  doc.createElement("ExpDate");
        node.appendChild(doc.createTextNode(String.valueOf(mExpDate)));
        root.appendChild(node);

        node =  doc.createElement("Comments");
        node.appendChild(doc.createTextNode(String.valueOf(mComments)));
        root.appendChild(node);

        node =  doc.createElement("KnowledgeStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mKnowledgeStatusCd)));
        root.appendChild(node);

        node =  doc.createElement("AddDate");
        node.appendChild(doc.createTextNode(String.valueOf(mAddDate)));
        root.appendChild(node);

        node =  doc.createElement("AddTime");
        node.appendChild(doc.createTextNode(String.valueOf(mAddTime)));
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

        node =  doc.createElement("StoreId");
        node.appendChild(doc.createTextNode(String.valueOf(mStoreId)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the KnowledgeId field is not cloned.
    *
    * @return KnowledgeData object
    */
    public Object clone(){
        KnowledgeData myClone = new KnowledgeData();
        
        myClone.mBusEntityId = mBusEntityId;
        
        myClone.mUserId = mUserId;
        
        myClone.mItemId = mItemId;
        
        myClone.mShortDesc = mShortDesc;
        
        myClone.mLongDesc = mLongDesc;
        
        myClone.mKnowledgeCategoryCd = mKnowledgeCategoryCd;
        
        if(mEffDate != null){
                myClone.mEffDate = (Date) mEffDate.clone();
        }
        
        if(mExpDate != null){
                myClone.mExpDate = (Date) mExpDate.clone();
        }
        
        myClone.mComments = mComments;
        
        myClone.mKnowledgeStatusCd = mKnowledgeStatusCd;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        if(mAddTime != null){
                myClone.mAddTime = (Date) mAddTime.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        myClone.mStoreId = mStoreId;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (KnowledgeDataAccess.KNOWLEDGE_ID.equals(pFieldName)) {
            return getKnowledgeId();
        } else if (KnowledgeDataAccess.BUS_ENTITY_ID.equals(pFieldName)) {
            return getBusEntityId();
        } else if (KnowledgeDataAccess.USER_ID.equals(pFieldName)) {
            return getUserId();
        } else if (KnowledgeDataAccess.ITEM_ID.equals(pFieldName)) {
            return getItemId();
        } else if (KnowledgeDataAccess.SHORT_DESC.equals(pFieldName)) {
            return getShortDesc();
        } else if (KnowledgeDataAccess.LONG_DESC.equals(pFieldName)) {
            return getLongDesc();
        } else if (KnowledgeDataAccess.KNOWLEDGE_CATEGORY_CD.equals(pFieldName)) {
            return getKnowledgeCategoryCd();
        } else if (KnowledgeDataAccess.EFF_DATE.equals(pFieldName)) {
            return getEffDate();
        } else if (KnowledgeDataAccess.EXP_DATE.equals(pFieldName)) {
            return getExpDate();
        } else if (KnowledgeDataAccess.COMMENTS.equals(pFieldName)) {
            return getComments();
        } else if (KnowledgeDataAccess.KNOWLEDGE_STATUS_CD.equals(pFieldName)) {
            return getKnowledgeStatusCd();
        } else if (KnowledgeDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (KnowledgeDataAccess.ADD_TIME.equals(pFieldName)) {
            return getAddTime();
        } else if (KnowledgeDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (KnowledgeDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (KnowledgeDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (KnowledgeDataAccess.STORE_ID.equals(pFieldName)) {
            return getStoreId();
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
        return KnowledgeDataAccess.CLW_KNOWLEDGE;
    }

    
    /**
     * Sets the KnowledgeId field. This field is required to be set in the database.
     *
     * @param pKnowledgeId
     *  int to use to update the field.
     */
    public void setKnowledgeId(int pKnowledgeId){
        this.mKnowledgeId = pKnowledgeId;
        setDirty(true);
    }
    /**
     * Retrieves the KnowledgeId field.
     *
     * @return
     *  int containing the KnowledgeId field.
     */
    public int getKnowledgeId(){
        return mKnowledgeId;
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
     * Sets the UserId field.
     *
     * @param pUserId
     *  int to use to update the field.
     */
    public void setUserId(int pUserId){
        this.mUserId = pUserId;
        setDirty(true);
    }
    /**
     * Retrieves the UserId field.
     *
     * @return
     *  int containing the UserId field.
     */
    public int getUserId(){
        return mUserId;
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
     * Sets the KnowledgeCategoryCd field.
     *
     * @param pKnowledgeCategoryCd
     *  String to use to update the field.
     */
    public void setKnowledgeCategoryCd(String pKnowledgeCategoryCd){
        this.mKnowledgeCategoryCd = pKnowledgeCategoryCd;
        setDirty(true);
    }
    /**
     * Retrieves the KnowledgeCategoryCd field.
     *
     * @return
     *  String containing the KnowledgeCategoryCd field.
     */
    public String getKnowledgeCategoryCd(){
        return mKnowledgeCategoryCd;
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
     * Sets the KnowledgeStatusCd field.
     *
     * @param pKnowledgeStatusCd
     *  String to use to update the field.
     */
    public void setKnowledgeStatusCd(String pKnowledgeStatusCd){
        this.mKnowledgeStatusCd = pKnowledgeStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the KnowledgeStatusCd field.
     *
     * @return
     *  String containing the KnowledgeStatusCd field.
     */
    public String getKnowledgeStatusCd(){
        return mKnowledgeStatusCd;
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
     * Sets the AddTime field.
     *
     * @param pAddTime
     *  Date to use to update the field.
     */
    public void setAddTime(Date pAddTime){
        this.mAddTime = pAddTime;
        setDirty(true);
    }
    /**
     * Retrieves the AddTime field.
     *
     * @return
     *  Date containing the AddTime field.
     */
    public Date getAddTime(){
        return mAddTime;
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


}
