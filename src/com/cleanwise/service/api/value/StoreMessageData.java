
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        StoreMessageData
 * Description:  This is a ValueObject class wrapping the database table CLW_STORE_MESSAGE.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.StoreMessageDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>StoreMessageData</code> is a ValueObject class wrapping of the database table CLW_STORE_MESSAGE.
 */
public class StoreMessageData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 1251004386957082525L;
    private int mStoreMessageId;// SQL type:NUMBER, not null
    private String mMessageTitle;// SQL type:VARCHAR2
    private String mShortDesc;// SQL type:VARCHAR2
    private String mMessageType;// SQL type:VARCHAR2
    private Date mPostedDate;// SQL type:DATE
    private Date mEndDate;// SQL type:DATE
    private String mForcedRead;// SQL type:VARCHAR2
    private int mHowManyTimes;// SQL type:NUMBER
    private int mForcedReadCount;// SQL type:NUMBER
    private String mPublished;// SQL type:VARCHAR2
    private int mDisplayOrder;// SQL type:NUMBER
    private String mLanguageCd;// SQL type:VARCHAR2
    private String mCountry;// SQL type:VARCHAR2
    private String mMessageAuthor;// SQL type:VARCHAR2
    private String mMessageAbstract;// SQL type:VARCHAR2
    private String mMessageBody;// SQL type:VARCHAR2
    private String mStoreMessageStatusCd;// SQL type:VARCHAR2, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null

    /**
     * Constructor.
     */
    public StoreMessageData ()
    {
        mMessageTitle = "";
        mShortDesc = "";
        mMessageType = "";
        mForcedRead = "";
        mPublished = "";
        mLanguageCd = "";
        mCountry = "";
        mMessageAuthor = "";
        mMessageAbstract = "";
        mMessageBody = "";
        mStoreMessageStatusCd = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public StoreMessageData(int parm1, String parm2, String parm3, String parm4, Date parm5, Date parm6, String parm7, int parm8, int parm9, String parm10, int parm11, String parm12, String parm13, String parm14, String parm15, String parm16, String parm17, String parm18, Date parm19, String parm20, Date parm21)
    {
        mStoreMessageId = parm1;
        mMessageTitle = parm2;
        mShortDesc = parm3;
        mMessageType = parm4;
        mPostedDate = parm5;
        mEndDate = parm6;
        mForcedRead = parm7;
        mHowManyTimes = parm8;
        mForcedReadCount = parm9;
        mPublished = parm10;
        mDisplayOrder = parm11;
        mLanguageCd = parm12;
        mCountry = parm13;
        mMessageAuthor = parm14;
        mMessageAbstract = parm15;
        mMessageBody = parm16;
        mStoreMessageStatusCd = parm17;
        mAddBy = parm18;
        mAddDate = parm19;
        mModBy = parm20;
        mModDate = parm21;
        
    }

    /**
     * Creates a new StoreMessageData
     *
     * @return
     *  Newly initialized StoreMessageData object.
     */
    public static StoreMessageData createValue ()
    {
        StoreMessageData valueData = new StoreMessageData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this StoreMessageData object
     */
    public String toString()
    {
        return "[" + "StoreMessageId=" + mStoreMessageId + ", MessageTitle=" + mMessageTitle + ", ShortDesc=" + mShortDesc + ", MessageType=" + mMessageType + ", PostedDate=" + mPostedDate + ", EndDate=" + mEndDate + ", ForcedRead=" + mForcedRead + ", HowManyTimes=" + mHowManyTimes + ", ForcedReadCount=" + mForcedReadCount + ", Published=" + mPublished + ", DisplayOrder=" + mDisplayOrder + ", LanguageCd=" + mLanguageCd + ", Country=" + mCountry + ", MessageAuthor=" + mMessageAuthor + ", MessageAbstract=" + mMessageAbstract + ", MessageBody=" + mMessageBody + ", StoreMessageStatusCd=" + mStoreMessageStatusCd + ", AddBy=" + mAddBy + ", AddDate=" + mAddDate + ", ModBy=" + mModBy + ", ModDate=" + mModDate + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("StoreMessage");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mStoreMessageId));

        node =  doc.createElement("MessageTitle");
        node.appendChild(doc.createTextNode(String.valueOf(mMessageTitle)));
        root.appendChild(node);

        node =  doc.createElement("ShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mShortDesc)));
        root.appendChild(node);

        node =  doc.createElement("MessageType");
        node.appendChild(doc.createTextNode(String.valueOf(mMessageType)));
        root.appendChild(node);

        node =  doc.createElement("PostedDate");
        node.appendChild(doc.createTextNode(String.valueOf(mPostedDate)));
        root.appendChild(node);

        node =  doc.createElement("EndDate");
        node.appendChild(doc.createTextNode(String.valueOf(mEndDate)));
        root.appendChild(node);

        node =  doc.createElement("ForcedRead");
        node.appendChild(doc.createTextNode(String.valueOf(mForcedRead)));
        root.appendChild(node);

        node =  doc.createElement("HowManyTimes");
        node.appendChild(doc.createTextNode(String.valueOf(mHowManyTimes)));
        root.appendChild(node);

        node =  doc.createElement("ForcedReadCount");
        node.appendChild(doc.createTextNode(String.valueOf(mForcedReadCount)));
        root.appendChild(node);

        node =  doc.createElement("Published");
        node.appendChild(doc.createTextNode(String.valueOf(mPublished)));
        root.appendChild(node);

        node =  doc.createElement("DisplayOrder");
        node.appendChild(doc.createTextNode(String.valueOf(mDisplayOrder)));
        root.appendChild(node);

        node =  doc.createElement("LanguageCd");
        node.appendChild(doc.createTextNode(String.valueOf(mLanguageCd)));
        root.appendChild(node);

        node =  doc.createElement("Country");
        node.appendChild(doc.createTextNode(String.valueOf(mCountry)));
        root.appendChild(node);

        node =  doc.createElement("MessageAuthor");
        node.appendChild(doc.createTextNode(String.valueOf(mMessageAuthor)));
        root.appendChild(node);

        node =  doc.createElement("MessageAbstract");
        node.appendChild(doc.createTextNode(String.valueOf(mMessageAbstract)));
        root.appendChild(node);

        node =  doc.createElement("MessageBody");
        node.appendChild(doc.createTextNode(String.valueOf(mMessageBody)));
        root.appendChild(node);

        node =  doc.createElement("StoreMessageStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mStoreMessageStatusCd)));
        root.appendChild(node);

        node =  doc.createElement("AddBy");
        node.appendChild(doc.createTextNode(String.valueOf(mAddBy)));
        root.appendChild(node);

        node =  doc.createElement("AddDate");
        node.appendChild(doc.createTextNode(String.valueOf(mAddDate)));
        root.appendChild(node);

        node =  doc.createElement("ModBy");
        node.appendChild(doc.createTextNode(String.valueOf(mModBy)));
        root.appendChild(node);

        node =  doc.createElement("ModDate");
        node.appendChild(doc.createTextNode(String.valueOf(mModDate)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the StoreMessageId field is not cloned.
    *
    * @return StoreMessageData object
    */
    public Object clone(){
        StoreMessageData myClone = new StoreMessageData();
        
        myClone.mMessageTitle = mMessageTitle;
        
        myClone.mShortDesc = mShortDesc;
        
        myClone.mMessageType = mMessageType;
        
        if(mPostedDate != null){
                myClone.mPostedDate = (Date) mPostedDate.clone();
        }
        
        if(mEndDate != null){
                myClone.mEndDate = (Date) mEndDate.clone();
        }
        
        myClone.mForcedRead = mForcedRead;
        
        myClone.mHowManyTimes = mHowManyTimes;
        
        myClone.mForcedReadCount = mForcedReadCount;
        
        myClone.mPublished = mPublished;
        
        myClone.mDisplayOrder = mDisplayOrder;
        
        myClone.mLanguageCd = mLanguageCd;
        
        myClone.mCountry = mCountry;
        
        myClone.mMessageAuthor = mMessageAuthor;
        
        myClone.mMessageAbstract = mMessageAbstract;
        
        myClone.mMessageBody = mMessageBody;
        
        myClone.mStoreMessageStatusCd = mStoreMessageStatusCd;
        
        myClone.mAddBy = mAddBy;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (StoreMessageDataAccess.STORE_MESSAGE_ID.equals(pFieldName)) {
            return getStoreMessageId();
        } else if (StoreMessageDataAccess.MESSAGE_TITLE.equals(pFieldName)) {
            return getMessageTitle();
        } else if (StoreMessageDataAccess.SHORT_DESC.equals(pFieldName)) {
            return getShortDesc();
        } else if (StoreMessageDataAccess.MESSAGE_TYPE.equals(pFieldName)) {
            return getMessageType();
        } else if (StoreMessageDataAccess.POSTED_DATE.equals(pFieldName)) {
            return getPostedDate();
        } else if (StoreMessageDataAccess.END_DATE.equals(pFieldName)) {
            return getEndDate();
        } else if (StoreMessageDataAccess.FORCED_READ.equals(pFieldName)) {
            return getForcedRead();
        } else if (StoreMessageDataAccess.HOW_MANY_TIMES.equals(pFieldName)) {
            return getHowManyTimes();
        } else if (StoreMessageDataAccess.FORCED_READ_COUNT.equals(pFieldName)) {
            return getForcedReadCount();
        } else if (StoreMessageDataAccess.PUBLISHED.equals(pFieldName)) {
            return getPublished();
        } else if (StoreMessageDataAccess.DISPLAY_ORDER.equals(pFieldName)) {
            return getDisplayOrder();
        } else if (StoreMessageDataAccess.LANGUAGE_CD.equals(pFieldName)) {
            return getLanguageCd();
        } else if (StoreMessageDataAccess.COUNTRY.equals(pFieldName)) {
            return getCountry();
        } else if (StoreMessageDataAccess.MESSAGE_AUTHOR.equals(pFieldName)) {
            return getMessageAuthor();
        } else if (StoreMessageDataAccess.MESSAGE_ABSTRACT.equals(pFieldName)) {
            return getMessageAbstract();
        } else if (StoreMessageDataAccess.MESSAGE_BODY.equals(pFieldName)) {
            return getMessageBody();
        } else if (StoreMessageDataAccess.STORE_MESSAGE_STATUS_CD.equals(pFieldName)) {
            return getStoreMessageStatusCd();
        } else if (StoreMessageDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (StoreMessageDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (StoreMessageDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (StoreMessageDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
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
        return StoreMessageDataAccess.CLW_STORE_MESSAGE;
    }

    
    /**
     * Sets the StoreMessageId field. This field is required to be set in the database.
     *
     * @param pStoreMessageId
     *  int to use to update the field.
     */
    public void setStoreMessageId(int pStoreMessageId){
        this.mStoreMessageId = pStoreMessageId;
        setDirty(true);
    }
    /**
     * Retrieves the StoreMessageId field.
     *
     * @return
     *  int containing the StoreMessageId field.
     */
    public int getStoreMessageId(){
        return mStoreMessageId;
    }

    /**
     * Sets the MessageTitle field.
     *
     * @param pMessageTitle
     *  String to use to update the field.
     */
    public void setMessageTitle(String pMessageTitle){
        this.mMessageTitle = pMessageTitle;
        setDirty(true);
    }
    /**
     * Retrieves the MessageTitle field.
     *
     * @return
     *  String containing the MessageTitle field.
     */
    public String getMessageTitle(){
        return mMessageTitle;
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
     * Sets the MessageType field.
     *
     * @param pMessageType
     *  String to use to update the field.
     */
    public void setMessageType(String pMessageType){
        this.mMessageType = pMessageType;
        setDirty(true);
    }
    /**
     * Retrieves the MessageType field.
     *
     * @return
     *  String containing the MessageType field.
     */
    public String getMessageType(){
        return mMessageType;
    }

    /**
     * Sets the PostedDate field.
     *
     * @param pPostedDate
     *  Date to use to update the field.
     */
    public void setPostedDate(Date pPostedDate){
        this.mPostedDate = pPostedDate;
        setDirty(true);
    }
    /**
     * Retrieves the PostedDate field.
     *
     * @return
     *  Date containing the PostedDate field.
     */
    public Date getPostedDate(){
        return mPostedDate;
    }

    /**
     * Sets the EndDate field.
     *
     * @param pEndDate
     *  Date to use to update the field.
     */
    public void setEndDate(Date pEndDate){
        this.mEndDate = pEndDate;
        setDirty(true);
    }
    /**
     * Retrieves the EndDate field.
     *
     * @return
     *  Date containing the EndDate field.
     */
    public Date getEndDate(){
        return mEndDate;
    }

    /**
     * Sets the ForcedRead field.
     *
     * @param pForcedRead
     *  String to use to update the field.
     */
    public void setForcedRead(String pForcedRead){
        this.mForcedRead = pForcedRead;
        setDirty(true);
    }
    /**
     * Retrieves the ForcedRead field.
     *
     * @return
     *  String containing the ForcedRead field.
     */
    public String getForcedRead(){
        return mForcedRead;
    }

    /**
     * Sets the HowManyTimes field.
     *
     * @param pHowManyTimes
     *  int to use to update the field.
     */
    public void setHowManyTimes(int pHowManyTimes){
        this.mHowManyTimes = pHowManyTimes;
        setDirty(true);
    }
    /**
     * Retrieves the HowManyTimes field.
     *
     * @return
     *  int containing the HowManyTimes field.
     */
    public int getHowManyTimes(){
        return mHowManyTimes;
    }

    /**
     * Sets the ForcedReadCount field.
     *
     * @param pForcedReadCount
     *  int to use to update the field.
     */
    public void setForcedReadCount(int pForcedReadCount){
        this.mForcedReadCount = pForcedReadCount;
        setDirty(true);
    }
    /**
     * Retrieves the ForcedReadCount field.
     *
     * @return
     *  int containing the ForcedReadCount field.
     */
    public int getForcedReadCount(){
        return mForcedReadCount;
    }

    /**
     * Sets the Published field.
     *
     * @param pPublished
     *  String to use to update the field.
     */
    public void setPublished(String pPublished){
        this.mPublished = pPublished;
        setDirty(true);
    }
    /**
     * Retrieves the Published field.
     *
     * @return
     *  String containing the Published field.
     */
    public String getPublished(){
        return mPublished;
    }

    /**
     * Sets the DisplayOrder field.
     *
     * @param pDisplayOrder
     *  int to use to update the field.
     */
    public void setDisplayOrder(int pDisplayOrder){
        this.mDisplayOrder = pDisplayOrder;
        setDirty(true);
    }
    /**
     * Retrieves the DisplayOrder field.
     *
     * @return
     *  int containing the DisplayOrder field.
     */
    public int getDisplayOrder(){
        return mDisplayOrder;
    }

    /**
     * Sets the LanguageCd field.
     *
     * @param pLanguageCd
     *  String to use to update the field.
     */
    public void setLanguageCd(String pLanguageCd){
        this.mLanguageCd = pLanguageCd;
        setDirty(true);
    }
    /**
     * Retrieves the LanguageCd field.
     *
     * @return
     *  String containing the LanguageCd field.
     */
    public String getLanguageCd(){
        return mLanguageCd;
    }

    /**
     * Sets the Country field.
     *
     * @param pCountry
     *  String to use to update the field.
     */
    public void setCountry(String pCountry){
        this.mCountry = pCountry;
        setDirty(true);
    }
    /**
     * Retrieves the Country field.
     *
     * @return
     *  String containing the Country field.
     */
    public String getCountry(){
        return mCountry;
    }

    /**
     * Sets the MessageAuthor field.
     *
     * @param pMessageAuthor
     *  String to use to update the field.
     */
    public void setMessageAuthor(String pMessageAuthor){
        this.mMessageAuthor = pMessageAuthor;
        setDirty(true);
    }
    /**
     * Retrieves the MessageAuthor field.
     *
     * @return
     *  String containing the MessageAuthor field.
     */
    public String getMessageAuthor(){
        return mMessageAuthor;
    }

    /**
     * Sets the MessageAbstract field.
     *
     * @param pMessageAbstract
     *  String to use to update the field.
     */
    public void setMessageAbstract(String pMessageAbstract){
        this.mMessageAbstract = pMessageAbstract;
        setDirty(true);
    }
    /**
     * Retrieves the MessageAbstract field.
     *
     * @return
     *  String containing the MessageAbstract field.
     */
    public String getMessageAbstract(){
        return mMessageAbstract;
    }

    /**
     * Sets the MessageBody field.
     *
     * @param pMessageBody
     *  String to use to update the field.
     */
    public void setMessageBody(String pMessageBody){
        this.mMessageBody = pMessageBody;
        setDirty(true);
    }
    /**
     * Retrieves the MessageBody field.
     *
     * @return
     *  String containing the MessageBody field.
     */
    public String getMessageBody(){
        return mMessageBody;
    }

    /**
     * Sets the StoreMessageStatusCd field. This field is required to be set in the database.
     *
     * @param pStoreMessageStatusCd
     *  String to use to update the field.
     */
    public void setStoreMessageStatusCd(String pStoreMessageStatusCd){
        this.mStoreMessageStatusCd = pStoreMessageStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the StoreMessageStatusCd field.
     *
     * @return
     *  String containing the StoreMessageStatusCd field.
     */
    public String getStoreMessageStatusCd(){
        return mStoreMessageStatusCd;
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


}
