
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        EventEmailData
 * Description:  This is a ValueObject class wrapping the database table CLW_EVENT_EMAIL.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.EventEmailDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>EventEmailData</code> is a ValueObject class wrapping of the database table CLW_EVENT_EMAIL.
 */
public class EventEmailData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 8359772579020135652L;
    private int mEventEmailId;// SQL type:NUMBER, not null
    private int mEventId;// SQL type:NUMBER
    private String mToAddress;// SQL type:VARCHAR2, not null
    private String mCcAddress;// SQL type:VARCHAR2
    private String mSubject;// SQL type:VARCHAR2
    private String mText;// SQL type:VARCHAR2
    private String mImportance;// SQL type:VARCHAR2
    private String mEmailStatusCd;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private byte[] mAttachments;// SQL type:BLOB
    private String mFromAddress;// SQL type:VARCHAR2
    private byte[] mLongText;// SQL type:BLOB
    private String mBinaryDataServer;// SQL type:VARCHAR2
    private String mAttachmentsSystemRef;// SQL type:VARCHAR2
    private String mLongTextSystemRef;// SQL type:VARCHAR2
    private String mStorageTypeCd;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public EventEmailData ()
    {
        mToAddress = "";
        mCcAddress = "";
        mSubject = "";
        mText = "";
        mImportance = "";
        mEmailStatusCd = "";
        mAddBy = "";
        mModBy = "";
        mFromAddress = "";
        mBinaryDataServer = "";
        mAttachmentsSystemRef = "";
        mLongTextSystemRef = "";
        mStorageTypeCd = "";
    }

    /**
     * Constructor.
     */
    public EventEmailData(int parm1, int parm2, String parm3, String parm4, String parm5, String parm6, String parm7, String parm8, Date parm9, String parm10, Date parm11, String parm12, byte[] parm13, String parm14, byte[] parm15, String parm16, String parm17, String parm18, String parm19)
    {
        mEventEmailId = parm1;
        mEventId = parm2;
        mToAddress = parm3;
        mCcAddress = parm4;
        mSubject = parm5;
        mText = parm6;
        mImportance = parm7;
        mEmailStatusCd = parm8;
        mAddDate = parm9;
        mAddBy = parm10;
        mModDate = parm11;
        mModBy = parm12;
        mAttachments = parm13;
        mFromAddress = parm14;
        mLongText = parm15;
        mBinaryDataServer = parm16;
        mAttachmentsSystemRef = parm17;
        mLongTextSystemRef = parm18;
        mStorageTypeCd = parm19;
        
    }

    /**
     * Creates a new EventEmailData
     *
     * @return
     *  Newly initialized EventEmailData object.
     */
    public static EventEmailData createValue ()
    {
        EventEmailData valueData = new EventEmailData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this EventEmailData object
     */
    public String toString()
    {
        return "[" + "EventEmailId=" + mEventEmailId + ", EventId=" + mEventId + ", ToAddress=" + mToAddress + ", CcAddress=" + mCcAddress + ", Subject=" + mSubject + ", Text=" + mText + ", Importance=" + mImportance + ", EmailStatusCd=" + mEmailStatusCd + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", Attachments=" + mAttachments + ", FromAddress=" + mFromAddress + ", LongText=" + mLongText + ", BinaryDataServer=" + mBinaryDataServer + ", AttachmentsSystemRef=" + mAttachmentsSystemRef + ", LongTextSystemRef=" + mLongTextSystemRef + ", StorageTypeCd=" + mStorageTypeCd + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("EventEmail");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mEventEmailId));

        node =  doc.createElement("EventId");
        node.appendChild(doc.createTextNode(String.valueOf(mEventId)));
        root.appendChild(node);

        node =  doc.createElement("ToAddress");
        node.appendChild(doc.createTextNode(String.valueOf(mToAddress)));
        root.appendChild(node);

        node =  doc.createElement("CcAddress");
        node.appendChild(doc.createTextNode(String.valueOf(mCcAddress)));
        root.appendChild(node);

        node =  doc.createElement("Subject");
        node.appendChild(doc.createTextNode(String.valueOf(mSubject)));
        root.appendChild(node);

        node =  doc.createElement("Text");
        node.appendChild(doc.createTextNode(String.valueOf(mText)));
        root.appendChild(node);

        node =  doc.createElement("Importance");
        node.appendChild(doc.createTextNode(String.valueOf(mImportance)));
        root.appendChild(node);

        node =  doc.createElement("EmailStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mEmailStatusCd)));
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

        node =  doc.createElement("Attachments");
        node.appendChild(doc.createTextNode(String.valueOf(mAttachments)));
        root.appendChild(node);

        node =  doc.createElement("FromAddress");
        node.appendChild(doc.createTextNode(String.valueOf(mFromAddress)));
        root.appendChild(node);

        node =  doc.createElement("LongText");
        node.appendChild(doc.createTextNode(String.valueOf(mLongText)));
        root.appendChild(node);

        node =  doc.createElement("BinaryDataServer");
        node.appendChild(doc.createTextNode(String.valueOf(mBinaryDataServer)));
        root.appendChild(node);

        node =  doc.createElement("AttachmentsSystemRef");
        node.appendChild(doc.createTextNode(String.valueOf(mAttachmentsSystemRef)));
        root.appendChild(node);

        node =  doc.createElement("LongTextSystemRef");
        node.appendChild(doc.createTextNode(String.valueOf(mLongTextSystemRef)));
        root.appendChild(node);

        node =  doc.createElement("StorageTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mStorageTypeCd)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the EventEmailId field is not cloned.
    *
    * @return EventEmailData object
    */
    public Object clone(){
        EventEmailData myClone = new EventEmailData();
        
        myClone.mEventId = mEventId;
        
        myClone.mToAddress = mToAddress;
        
        myClone.mCcAddress = mCcAddress;
        
        myClone.mSubject = mSubject;
        
        myClone.mText = mText;
        
        myClone.mImportance = mImportance;
        
        myClone.mEmailStatusCd = mEmailStatusCd;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        myClone.mAttachments = mAttachments;
        
        myClone.mFromAddress = mFromAddress;
        
        myClone.mLongText = mLongText;
        
        myClone.mBinaryDataServer = mBinaryDataServer;
        
        myClone.mAttachmentsSystemRef = mAttachmentsSystemRef;
        
        myClone.mLongTextSystemRef = mLongTextSystemRef;
        
        myClone.mStorageTypeCd = mStorageTypeCd;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (EventEmailDataAccess.EVENT_EMAIL_ID.equals(pFieldName)) {
            return getEventEmailId();
        } else if (EventEmailDataAccess.EVENT_ID.equals(pFieldName)) {
            return getEventId();
        } else if (EventEmailDataAccess.TO_ADDRESS.equals(pFieldName)) {
            return getToAddress();
        } else if (EventEmailDataAccess.CC_ADDRESS.equals(pFieldName)) {
            return getCcAddress();
        } else if (EventEmailDataAccess.SUBJECT.equals(pFieldName)) {
            return getSubject();
        } else if (EventEmailDataAccess.TEXT.equals(pFieldName)) {
            return getText();
        } else if (EventEmailDataAccess.IMPORTANCE.equals(pFieldName)) {
            return getImportance();
        } else if (EventEmailDataAccess.EMAIL_STATUS_CD.equals(pFieldName)) {
            return getEmailStatusCd();
        } else if (EventEmailDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (EventEmailDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (EventEmailDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (EventEmailDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (EventEmailDataAccess.ATTACHMENTS.equals(pFieldName)) {
            return getAttachments();
        } else if (EventEmailDataAccess.FROM_ADDRESS.equals(pFieldName)) {
            return getFromAddress();
        } else if (EventEmailDataAccess.LONG_TEXT.equals(pFieldName)) {
            return getLongText();
        } else if (EventEmailDataAccess.BINARY_DATA_SERVER.equals(pFieldName)) {
            return getBinaryDataServer();
        } else if (EventEmailDataAccess.ATTACHMENTS_SYSTEM_REF.equals(pFieldName)) {
            return getAttachmentsSystemRef();
        } else if (EventEmailDataAccess.LONG_TEXT_SYSTEM_REF.equals(pFieldName)) {
            return getLongTextSystemRef();
        } else if (EventEmailDataAccess.STORAGE_TYPE_CD.equals(pFieldName)) {
            return getStorageTypeCd();
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
        return EventEmailDataAccess.CLW_EVENT_EMAIL;
    }

    
    /**
     * Sets the EventEmailId field. This field is required to be set in the database.
     *
     * @param pEventEmailId
     *  int to use to update the field.
     */
    public void setEventEmailId(int pEventEmailId){
        this.mEventEmailId = pEventEmailId;
        setDirty(true);
    }
    /**
     * Retrieves the EventEmailId field.
     *
     * @return
     *  int containing the EventEmailId field.
     */
    public int getEventEmailId(){
        return mEventEmailId;
    }

    /**
     * Sets the EventId field.
     *
     * @param pEventId
     *  int to use to update the field.
     */
    public void setEventId(int pEventId){
        this.mEventId = pEventId;
        setDirty(true);
    }
    /**
     * Retrieves the EventId field.
     *
     * @return
     *  int containing the EventId field.
     */
    public int getEventId(){
        return mEventId;
    }

    /**
     * Sets the ToAddress field. This field is required to be set in the database.
     *
     * @param pToAddress
     *  String to use to update the field.
     */
    public void setToAddress(String pToAddress){
        this.mToAddress = pToAddress;
        setDirty(true);
    }
    /**
     * Retrieves the ToAddress field.
     *
     * @return
     *  String containing the ToAddress field.
     */
    public String getToAddress(){
        return mToAddress;
    }

    /**
     * Sets the CcAddress field.
     *
     * @param pCcAddress
     *  String to use to update the field.
     */
    public void setCcAddress(String pCcAddress){
        this.mCcAddress = pCcAddress;
        setDirty(true);
    }
    /**
     * Retrieves the CcAddress field.
     *
     * @return
     *  String containing the CcAddress field.
     */
    public String getCcAddress(){
        return mCcAddress;
    }

    /**
     * Sets the Subject field.
     *
     * @param pSubject
     *  String to use to update the field.
     */
    public void setSubject(String pSubject){
        this.mSubject = pSubject;
        setDirty(true);
    }
    /**
     * Retrieves the Subject field.
     *
     * @return
     *  String containing the Subject field.
     */
    public String getSubject(){
        return mSubject;
    }

    /**
     * Sets the Text field.
     *
     * @param pText
     *  String to use to update the field.
     */
    public void setText(String pText){
        this.mText = pText;
        setDirty(true);
    }
    /**
     * Retrieves the Text field.
     *
     * @return
     *  String containing the Text field.
     */
    public String getText(){
        return mText;
    }

    /**
     * Sets the Importance field.
     *
     * @param pImportance
     *  String to use to update the field.
     */
    public void setImportance(String pImportance){
        this.mImportance = pImportance;
        setDirty(true);
    }
    /**
     * Retrieves the Importance field.
     *
     * @return
     *  String containing the Importance field.
     */
    public String getImportance(){
        return mImportance;
    }

    /**
     * Sets the EmailStatusCd field.
     *
     * @param pEmailStatusCd
     *  String to use to update the field.
     */
    public void setEmailStatusCd(String pEmailStatusCd){
        this.mEmailStatusCd = pEmailStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the EmailStatusCd field.
     *
     * @return
     *  String containing the EmailStatusCd field.
     */
    public String getEmailStatusCd(){
        return mEmailStatusCd;
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
     * Sets the Attachments field.
     *
     * @param pAttachments
     *  byte[] to use to update the field.
     */
    public void setAttachments(byte[] pAttachments){
        this.mAttachments = pAttachments;
        setDirty(true);
    }
    /**
     * Retrieves the Attachments field.
     *
     * @return
     *  byte[] containing the Attachments field.
     */
    public byte[] getAttachments(){
        return mAttachments;
    }

    /**
     * Sets the FromAddress field.
     *
     * @param pFromAddress
     *  String to use to update the field.
     */
    public void setFromAddress(String pFromAddress){
        this.mFromAddress = pFromAddress;
        setDirty(true);
    }
    /**
     * Retrieves the FromAddress field.
     *
     * @return
     *  String containing the FromAddress field.
     */
    public String getFromAddress(){
        return mFromAddress;
    }

    /**
     * Sets the LongText field.
     *
     * @param pLongText
     *  byte[] to use to update the field.
     */
    public void setLongText(byte[] pLongText){
        this.mLongText = pLongText;
        setDirty(true);
    }
    /**
     * Retrieves the LongText field.
     *
     * @return
     *  byte[] containing the LongText field.
     */
    public byte[] getLongText(){
        return mLongText;
    }

    /**
     * Sets the BinaryDataServer field.
     *
     * @param pBinaryDataServer
     *  String to use to update the field.
     */
    public void setBinaryDataServer(String pBinaryDataServer){
        this.mBinaryDataServer = pBinaryDataServer;
        setDirty(true);
    }
    /**
     * Retrieves the BinaryDataServer field.
     *
     * @return
     *  String containing the BinaryDataServer field.
     */
    public String getBinaryDataServer(){
        return mBinaryDataServer;
    }

    /**
     * Sets the AttachmentsSystemRef field.
     *
     * @param pAttachmentsSystemRef
     *  String to use to update the field.
     */
    public void setAttachmentsSystemRef(String pAttachmentsSystemRef){
        this.mAttachmentsSystemRef = pAttachmentsSystemRef;
        setDirty(true);
    }
    /**
     * Retrieves the AttachmentsSystemRef field.
     *
     * @return
     *  String containing the AttachmentsSystemRef field.
     */
    public String getAttachmentsSystemRef(){
        return mAttachmentsSystemRef;
    }

    /**
     * Sets the LongTextSystemRef field.
     *
     * @param pLongTextSystemRef
     *  String to use to update the field.
     */
    public void setLongTextSystemRef(String pLongTextSystemRef){
        this.mLongTextSystemRef = pLongTextSystemRef;
        setDirty(true);
    }
    /**
     * Retrieves the LongTextSystemRef field.
     *
     * @return
     *  String containing the LongTextSystemRef field.
     */
    public String getLongTextSystemRef(){
        return mLongTextSystemRef;
    }

    /**
     * Sets the StorageTypeCd field.
     *
     * @param pStorageTypeCd
     *  String to use to update the field.
     */
    public void setStorageTypeCd(String pStorageTypeCd){
        this.mStorageTypeCd = pStorageTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the StorageTypeCd field.
     *
     * @return
     *  String containing the StorageTypeCd field.
     */
    public String getStorageTypeCd(){
        return mStorageTypeCd;
    }


}
