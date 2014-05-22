
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        StoreMessageDetailData
 * Description:  This is a ValueObject class wrapping the database table CLW_STORE_MESSAGE_DETAIL.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.StoreMessageDetailDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>StoreMessageDetailData</code> is a ValueObject class wrapping of the database table CLW_STORE_MESSAGE_DETAIL.
 */
public class StoreMessageDetailData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 1L;
    private int mStoreMessageDetailId;// SQL type:NUMBER, not null
    private int mStoreMessageId;// SQL type:NUMBER, not null
    private String mMessageDetailTypeCd;// SQL type:VARCHAR2
    private String mMessageTitle;// SQL type:VARCHAR2, not null
    private String mLanguageCd;// SQL type:VARCHAR2, not null
    private String mCountry;// SQL type:VARCHAR2
    private String mMessageAuthor;// SQL type:VARCHAR2, not null
    private String mMessageAbstract;// SQL type:VARCHAR2, not null
    private String mMessageBody;// SQL type:VARCHAR2
    private String mAddBy;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null

    /**
     * Constructor.
     */
    public StoreMessageDetailData ()
    {
        mMessageDetailTypeCd = "";
        mMessageTitle = "";
        mLanguageCd = "";
        mCountry = "";
        mMessageAuthor = "";
        mMessageAbstract = "";
        mMessageBody = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public StoreMessageDetailData(int parm1, int parm2, String parm3, String parm4, String parm5, String parm6, String parm7, String parm8, String parm9, String parm10, Date parm11, String parm12, Date parm13)
    {
        mStoreMessageDetailId = parm1;
        mStoreMessageId = parm2;
        mMessageDetailTypeCd = parm3;
        mMessageTitle = parm4;
        mLanguageCd = parm5;
        mCountry = parm6;
        mMessageAuthor = parm7;
        mMessageAbstract = parm8;
        mMessageBody = parm9;
        mAddBy = parm10;
        mAddDate = parm11;
        mModBy = parm12;
        mModDate = parm13;
        
    }

    /**
     * Creates a new StoreMessageDetailData
     *
     * @return
     *  Newly initialized StoreMessageDetailData object.
     */
    public static StoreMessageDetailData createValue ()
    {
        StoreMessageDetailData valueData = new StoreMessageDetailData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this StoreMessageDetailData object
     */
    public String toString()
    {
        return "[" + "StoreMessageDetailId=" + mStoreMessageDetailId + ", StoreMessageId=" + mStoreMessageId + ", MessageDetailTypeCd=" + mMessageDetailTypeCd + ", MessageTitle=" + mMessageTitle + ", LanguageCd=" + mLanguageCd + ", Country=" + mCountry + ", MessageAuthor=" + mMessageAuthor + ", MessageAbstract=" + mMessageAbstract + ", MessageBody=" + mMessageBody + ", AddBy=" + mAddBy + ", AddDate=" + mAddDate + ", ModBy=" + mModBy + ", ModDate=" + mModDate + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("StoreMessageDetail");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mStoreMessageDetailId));

        node =  doc.createElement("StoreMessageId");
        node.appendChild(doc.createTextNode(String.valueOf(mStoreMessageId)));
        root.appendChild(node);

        node =  doc.createElement("MessageDetailTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mMessageDetailTypeCd)));
        root.appendChild(node);

        node =  doc.createElement("MessageTitle");
        node.appendChild(doc.createTextNode(String.valueOf(mMessageTitle)));
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
    * creates a clone of this object, the StoreMessageDetailId field is not cloned.
    *
    * @return StoreMessageDetailData object
    */
    public Object clone(){
        StoreMessageDetailData myClone = new StoreMessageDetailData();
        
        myClone.mStoreMessageId = mStoreMessageId;
        
        myClone.mMessageDetailTypeCd = mMessageDetailTypeCd;
        
        myClone.mMessageTitle = mMessageTitle;
        
        myClone.mLanguageCd = mLanguageCd;
        
        myClone.mCountry = mCountry;
        
        myClone.mMessageAuthor = mMessageAuthor;
        
        myClone.mMessageAbstract = mMessageAbstract;
        
        myClone.mMessageBody = mMessageBody;
        
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

        if (StoreMessageDetailDataAccess.STORE_MESSAGE_DETAIL_ID.equals(pFieldName)) {
            return getStoreMessageDetailId();
        } else if (StoreMessageDetailDataAccess.STORE_MESSAGE_ID.equals(pFieldName)) {
            return getStoreMessageId();
        } else if (StoreMessageDetailDataAccess.MESSAGE_DETAIL_TYPE_CD.equals(pFieldName)) {
            return getMessageDetailTypeCd();
        } else if (StoreMessageDetailDataAccess.MESSAGE_TITLE.equals(pFieldName)) {
            return getMessageTitle();
        } else if (StoreMessageDetailDataAccess.LANGUAGE_CD.equals(pFieldName)) {
            return getLanguageCd();
        } else if (StoreMessageDetailDataAccess.COUNTRY.equals(pFieldName)) {
            return getCountry();
        } else if (StoreMessageDetailDataAccess.MESSAGE_AUTHOR.equals(pFieldName)) {
            return getMessageAuthor();
        } else if (StoreMessageDetailDataAccess.MESSAGE_ABSTRACT.equals(pFieldName)) {
            return getMessageAbstract();
        } else if (StoreMessageDetailDataAccess.MESSAGE_BODY.equals(pFieldName)) {
            return getMessageBody();
        } else if (StoreMessageDetailDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (StoreMessageDetailDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (StoreMessageDetailDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (StoreMessageDetailDataAccess.MOD_DATE.equals(pFieldName)) {
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
        return StoreMessageDetailDataAccess.CLW_STORE_MESSAGE_DETAIL;
    }

    
    /**
     * Sets the StoreMessageDetailId field. This field is required to be set in the database.
     *
     * @param pStoreMessageDetailId
     *  int to use to update the field.
     */
    public void setStoreMessageDetailId(int pStoreMessageDetailId){
        this.mStoreMessageDetailId = pStoreMessageDetailId;
        setDirty(true);
    }
    /**
     * Retrieves the StoreMessageDetailId field.
     *
     * @return
     *  int containing the StoreMessageDetailId field.
     */
    public int getStoreMessageDetailId(){
        return mStoreMessageDetailId;
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
     * Sets the MessageDetailTypeCd field.
     *
     * @param pMessageDetailTypeCd
     *  String to use to update the field.
     */
    public void setMessageDetailTypeCd(String pMessageDetailTypeCd){
        this.mMessageDetailTypeCd = pMessageDetailTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the MessageDetailTypeCd field.
     *
     * @return
     *  String containing the MessageDetailTypeCd field.
     */
    public String getMessageDetailTypeCd(){
        return mMessageDetailTypeCd;
    }

    /**
     * Sets the MessageTitle field. This field is required to be set in the database.
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
     * Sets the LanguageCd field. This field is required to be set in the database.
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
     * Sets the MessageAuthor field. This field is required to be set in the database.
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
     * Sets the MessageAbstract field. This field is required to be set in the database.
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
