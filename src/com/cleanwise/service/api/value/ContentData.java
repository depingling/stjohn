
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ContentData
 * Description:  This is a ValueObject class wrapping the database table CLW_CONTENT.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.ContentDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>ContentData</code> is a ValueObject class wrapping of the database table CLW_CONTENT.
 */
public class ContentData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -3781003057741152702L;
    private int mContentId;// SQL type:NUMBER, not null
    private String mShortDesc;// SQL type:VARCHAR2, not null
    private String mLongDesc;// SQL type:VARCHAR2
    private int mVersion;// SQL type:NUMBER
    private String mContentTypeCd;// SQL type:VARCHAR2, not null
    private String mContentStatusCd;// SQL type:VARCHAR2, not null
    private String mSourceCd;// SQL type:VARCHAR2, not null
    private String mLocaleCd;// SQL type:VARCHAR2, not null
    private String mLanguageCd;// SQL type:VARCHAR2, not null
    private int mItemId;// SQL type:NUMBER
    private int mBusEntityId;// SQL type:NUMBER
    private String mPath;// SQL type:VARCHAR2
    private String mContentUsageCd;// SQL type:VARCHAR2, not null
    private Date mEffDate;// SQL type:DATE
    private Date mExpDate;// SQL type:DATE
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private byte[] mBinaryData;// SQL type:BLOB
    private String mContentServer;// SQL type:VARCHAR2
    private String mContentSystemRef;// SQL type:VARCHAR2
    private String mStorageTypeCd;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public ContentData ()
    {
        mShortDesc = "";
        mLongDesc = "";
        mContentTypeCd = "";
        mContentStatusCd = "";
        mSourceCd = "";
        mLocaleCd = "";
        mLanguageCd = "";
        mPath = "";
        mContentUsageCd = "";
        mAddBy = "";
        mModBy = "";
        mContentServer = "";
        mContentSystemRef = "";
        mStorageTypeCd = "";
    }

    /**
     * Constructor.
     */
    public ContentData(int parm1, String parm2, String parm3, int parm4, String parm5, String parm6, String parm7, String parm8, String parm9, int parm10, int parm11, String parm12, String parm13, Date parm14, Date parm15, Date parm16, String parm17, Date parm18, String parm19, byte[] parm20, String parm21, String parm22, String parm23)
    {
        mContentId = parm1;
        mShortDesc = parm2;
        mLongDesc = parm3;
        mVersion = parm4;
        mContentTypeCd = parm5;
        mContentStatusCd = parm6;
        mSourceCd = parm7;
        mLocaleCd = parm8;
        mLanguageCd = parm9;
        mItemId = parm10;
        mBusEntityId = parm11;
        mPath = parm12;
        mContentUsageCd = parm13;
        mEffDate = parm14;
        mExpDate = parm15;
        mAddDate = parm16;
        mAddBy = parm17;
        mModDate = parm18;
        mModBy = parm19;
        mBinaryData = parm20;
        mContentServer = parm21;
        mContentSystemRef = parm22;
        mStorageTypeCd = parm23;
        
    }

    /**
     * Creates a new ContentData
     *
     * @return
     *  Newly initialized ContentData object.
     */
    public static ContentData createValue ()
    {
        ContentData valueData = new ContentData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ContentData object
     */
    public String toString()
    {
        return "[" + "ContentId=" + mContentId + ", ShortDesc=" + mShortDesc + ", LongDesc=" + mLongDesc + ", Version=" + mVersion + ", ContentTypeCd=" + mContentTypeCd + ", ContentStatusCd=" + mContentStatusCd + ", SourceCd=" + mSourceCd + ", LocaleCd=" + mLocaleCd + ", LanguageCd=" + mLanguageCd + ", ItemId=" + mItemId + ", BusEntityId=" + mBusEntityId + ", Path=" + mPath + ", ContentUsageCd=" + mContentUsageCd + ", EffDate=" + mEffDate + ", ExpDate=" + mExpDate + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", BinaryData=" + mBinaryData + ", ContentServer=" + mContentServer + ", ContentSystemRef=" + mContentSystemRef + ", StorageTypeCd=" + mStorageTypeCd + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("Content");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mContentId));

        node =  doc.createElement("ShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mShortDesc)));
        root.appendChild(node);

        node =  doc.createElement("LongDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mLongDesc)));
        root.appendChild(node);

        node =  doc.createElement("Version");
        node.appendChild(doc.createTextNode(String.valueOf(mVersion)));
        root.appendChild(node);

        node =  doc.createElement("ContentTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mContentTypeCd)));
        root.appendChild(node);

        node =  doc.createElement("ContentStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mContentStatusCd)));
        root.appendChild(node);

        node =  doc.createElement("SourceCd");
        node.appendChild(doc.createTextNode(String.valueOf(mSourceCd)));
        root.appendChild(node);

        node =  doc.createElement("LocaleCd");
        node.appendChild(doc.createTextNode(String.valueOf(mLocaleCd)));
        root.appendChild(node);

        node =  doc.createElement("LanguageCd");
        node.appendChild(doc.createTextNode(String.valueOf(mLanguageCd)));
        root.appendChild(node);

        node =  doc.createElement("ItemId");
        node.appendChild(doc.createTextNode(String.valueOf(mItemId)));
        root.appendChild(node);

        node =  doc.createElement("BusEntityId");
        node.appendChild(doc.createTextNode(String.valueOf(mBusEntityId)));
        root.appendChild(node);

        node =  doc.createElement("Path");
        node.appendChild(doc.createTextNode(String.valueOf(mPath)));
        root.appendChild(node);

        node =  doc.createElement("ContentUsageCd");
        node.appendChild(doc.createTextNode(String.valueOf(mContentUsageCd)));
        root.appendChild(node);

        node =  doc.createElement("EffDate");
        node.appendChild(doc.createTextNode(String.valueOf(mEffDate)));
        root.appendChild(node);

        node =  doc.createElement("ExpDate");
        node.appendChild(doc.createTextNode(String.valueOf(mExpDate)));
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

        node =  doc.createElement("BinaryData");
        node.appendChild(doc.createTextNode(String.valueOf(mBinaryData)));
        root.appendChild(node);

        node =  doc.createElement("ContentServer");
        node.appendChild(doc.createTextNode(String.valueOf(mContentServer)));
        root.appendChild(node);

        node =  doc.createElement("ContentSystemRef");
        node.appendChild(doc.createTextNode(String.valueOf(mContentSystemRef)));
        root.appendChild(node);

        node =  doc.createElement("StorageTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mStorageTypeCd)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the ContentId field is not cloned.
    *
    * @return ContentData object
    */
    public Object clone(){
        ContentData myClone = new ContentData();
        
        myClone.mShortDesc = mShortDesc;
        
        myClone.mLongDesc = mLongDesc;
        
        myClone.mVersion = mVersion;
        
        myClone.mContentTypeCd = mContentTypeCd;
        
        myClone.mContentStatusCd = mContentStatusCd;
        
        myClone.mSourceCd = mSourceCd;
        
        myClone.mLocaleCd = mLocaleCd;
        
        myClone.mLanguageCd = mLanguageCd;
        
        myClone.mItemId = mItemId;
        
        myClone.mBusEntityId = mBusEntityId;
        
        myClone.mPath = mPath;
        
        myClone.mContentUsageCd = mContentUsageCd;
        
        if(mEffDate != null){
                myClone.mEffDate = (Date) mEffDate.clone();
        }
        
        if(mExpDate != null){
                myClone.mExpDate = (Date) mExpDate.clone();
        }
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        myClone.mBinaryData = mBinaryData;
        
        myClone.mContentServer = mContentServer;
        
        myClone.mContentSystemRef = mContentSystemRef;
        
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

        if (ContentDataAccess.CONTENT_ID.equals(pFieldName)) {
            return getContentId();
        } else if (ContentDataAccess.SHORT_DESC.equals(pFieldName)) {
            return getShortDesc();
        } else if (ContentDataAccess.LONG_DESC.equals(pFieldName)) {
            return getLongDesc();
        } else if (ContentDataAccess.VERSION.equals(pFieldName)) {
            return getVersion();
        } else if (ContentDataAccess.CONTENT_TYPE_CD.equals(pFieldName)) {
            return getContentTypeCd();
        } else if (ContentDataAccess.CONTENT_STATUS_CD.equals(pFieldName)) {
            return getContentStatusCd();
        } else if (ContentDataAccess.SOURCE_CD.equals(pFieldName)) {
            return getSourceCd();
        } else if (ContentDataAccess.LOCALE_CD.equals(pFieldName)) {
            return getLocaleCd();
        } else if (ContentDataAccess.LANGUAGE_CD.equals(pFieldName)) {
            return getLanguageCd();
        } else if (ContentDataAccess.ITEM_ID.equals(pFieldName)) {
            return getItemId();
        } else if (ContentDataAccess.BUS_ENTITY_ID.equals(pFieldName)) {
            return getBusEntityId();
        } else if (ContentDataAccess.PATH.equals(pFieldName)) {
            return getPath();
        } else if (ContentDataAccess.CONTENT_USAGE_CD.equals(pFieldName)) {
            return getContentUsageCd();
        } else if (ContentDataAccess.EFF_DATE.equals(pFieldName)) {
            return getEffDate();
        } else if (ContentDataAccess.EXP_DATE.equals(pFieldName)) {
            return getExpDate();
        } else if (ContentDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (ContentDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (ContentDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (ContentDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (ContentDataAccess.BINARY_DATA.equals(pFieldName)) {
            return getBinaryData();
        } else if (ContentDataAccess.CONTENT_SERVER.equals(pFieldName)) {
            return getContentServer();
        } else if (ContentDataAccess.CONTENT_SYSTEM_REF.equals(pFieldName)) {
            return getContentSystemRef();
        } else if (ContentDataAccess.STORAGE_TYPE_CD.equals(pFieldName)) {
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
        return ContentDataAccess.CLW_CONTENT;
    }

    
    /**
     * Sets the ContentId field. This field is required to be set in the database.
     *
     * @param pContentId
     *  int to use to update the field.
     */
    public void setContentId(int pContentId){
        this.mContentId = pContentId;
        setDirty(true);
    }
    /**
     * Retrieves the ContentId field.
     *
     * @return
     *  int containing the ContentId field.
     */
    public int getContentId(){
        return mContentId;
    }

    /**
     * Sets the ShortDesc field. This field is required to be set in the database.
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
     * Sets the Version field.
     *
     * @param pVersion
     *  int to use to update the field.
     */
    public void setVersion(int pVersion){
        this.mVersion = pVersion;
        setDirty(true);
    }
    /**
     * Retrieves the Version field.
     *
     * @return
     *  int containing the Version field.
     */
    public int getVersion(){
        return mVersion;
    }

    /**
     * Sets the ContentTypeCd field. This field is required to be set in the database.
     *
     * @param pContentTypeCd
     *  String to use to update the field.
     */
    public void setContentTypeCd(String pContentTypeCd){
        this.mContentTypeCd = pContentTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the ContentTypeCd field.
     *
     * @return
     *  String containing the ContentTypeCd field.
     */
    public String getContentTypeCd(){
        return mContentTypeCd;
    }

    /**
     * Sets the ContentStatusCd field. This field is required to be set in the database.
     *
     * @param pContentStatusCd
     *  String to use to update the field.
     */
    public void setContentStatusCd(String pContentStatusCd){
        this.mContentStatusCd = pContentStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the ContentStatusCd field.
     *
     * @return
     *  String containing the ContentStatusCd field.
     */
    public String getContentStatusCd(){
        return mContentStatusCd;
    }

    /**
     * Sets the SourceCd field. This field is required to be set in the database.
     *
     * @param pSourceCd
     *  String to use to update the field.
     */
    public void setSourceCd(String pSourceCd){
        this.mSourceCd = pSourceCd;
        setDirty(true);
    }
    /**
     * Retrieves the SourceCd field.
     *
     * @return
     *  String containing the SourceCd field.
     */
    public String getSourceCd(){
        return mSourceCd;
    }

    /**
     * Sets the LocaleCd field. This field is required to be set in the database.
     *
     * @param pLocaleCd
     *  String to use to update the field.
     */
    public void setLocaleCd(String pLocaleCd){
        this.mLocaleCd = pLocaleCd;
        setDirty(true);
    }
    /**
     * Retrieves the LocaleCd field.
     *
     * @return
     *  String containing the LocaleCd field.
     */
    public String getLocaleCd(){
        return mLocaleCd;
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
     * Sets the Path field.
     *
     * @param pPath
     *  String to use to update the field.
     */
    public void setPath(String pPath){
        this.mPath = pPath;
        setDirty(true);
    }
    /**
     * Retrieves the Path field.
     *
     * @return
     *  String containing the Path field.
     */
    public String getPath(){
        return mPath;
    }

    /**
     * Sets the ContentUsageCd field. This field is required to be set in the database.
     *
     * @param pContentUsageCd
     *  String to use to update the field.
     */
    public void setContentUsageCd(String pContentUsageCd){
        this.mContentUsageCd = pContentUsageCd;
        setDirty(true);
    }
    /**
     * Retrieves the ContentUsageCd field.
     *
     * @return
     *  String containing the ContentUsageCd field.
     */
    public String getContentUsageCd(){
        return mContentUsageCd;
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
     * Sets the BinaryData field.
     *
     * @param pBinaryData
     *  byte[] to use to update the field.
     */
    public void setBinaryData(byte[] pBinaryData){
        this.mBinaryData = pBinaryData;
        setDirty(true);
    }
    /**
     * Retrieves the BinaryData field.
     *
     * @return
     *  byte[] containing the BinaryData field.
     */
    public byte[] getBinaryData(){
        return mBinaryData;
    }

    /**
     * Sets the ContentServer field.
     *
     * @param pContentServer
     *  String to use to update the field.
     */
    public void setContentServer(String pContentServer){
        this.mContentServer = pContentServer;
        setDirty(true);
    }
    /**
     * Retrieves the ContentServer field.
     *
     * @return
     *  String containing the ContentServer field.
     */
    public String getContentServer(){
        return mContentServer;
    }

    /**
     * Sets the ContentSystemRef field.
     *
     * @param pContentSystemRef
     *  String to use to update the field.
     */
    public void setContentSystemRef(String pContentSystemRef){
        this.mContentSystemRef = pContentSystemRef;
        setDirty(true);
    }
    /**
     * Retrieves the ContentSystemRef field.
     *
     * @return
     *  String containing the ContentSystemRef field.
     */
    public String getContentSystemRef(){
        return mContentSystemRef;
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
