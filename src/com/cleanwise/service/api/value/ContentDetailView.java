
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ContentDetailView
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




/**
 * <code>ContentDetailView</code> is a ViewObject class for UI.
 */
public class ContentDetailView
extends ValueObject
{
   
    private static final long serialVersionUID = -7264403190402072237L;
    private int mBusEntityId;
    private int mContentId;
    private int mItemId;
    private String mAddBy;
    private Date mAddDate;
    private String mContentStatusCd;
    private String mContentTypeCd;
    private String mContentUsageCd;
    private Date mEffDate;
    private Date mExpDate;
    private String mLanguageCd;
    private String mLocaleCd;
    private String mLongDesc;
    private String mModBy;
    private Date mModDate;
    private String mPath;
    private String mShortDesc;
    private String mSourceCd;
    private int mVersion;
    private byte[] mData;

    /**
     * Constructor.
     */
    public ContentDetailView ()
    {
        mAddBy = "";
        mContentStatusCd = "";
        mContentTypeCd = "";
        mContentUsageCd = "";
        mLanguageCd = "";
        mLocaleCd = "";
        mLongDesc = "";
        mModBy = "";
        mPath = "";
        mShortDesc = "";
        mSourceCd = "";
    }

    /**
     * Constructor. 
     */
    public ContentDetailView(int parm1, int parm2, int parm3, String parm4, Date parm5, String parm6, String parm7, String parm8, Date parm9, Date parm10, String parm11, String parm12, String parm13, String parm14, Date parm15, String parm16, String parm17, String parm18, int parm19, byte[] parm20)
    {
        mBusEntityId = parm1;
        mContentId = parm2;
        mItemId = parm3;
        mAddBy = parm4;
        mAddDate = parm5;
        mContentStatusCd = parm6;
        mContentTypeCd = parm7;
        mContentUsageCd = parm8;
        mEffDate = parm9;
        mExpDate = parm10;
        mLanguageCd = parm11;
        mLocaleCd = parm12;
        mLongDesc = parm13;
        mModBy = parm14;
        mModDate = parm15;
        mPath = parm16;
        mShortDesc = parm17;
        mSourceCd = parm18;
        mVersion = parm19;
        mData = parm20;
        
    }

    /**
     * Creates a new ContentDetailView
     *
     * @return
     *  Newly initialized ContentDetailView object.
     */
    public static ContentDetailView createValue () 
    {
        ContentDetailView valueView = new ContentDetailView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ContentDetailView object
     */
    public String toString()
    {
        return "[" + "BusEntityId=" + mBusEntityId + ", ContentId=" + mContentId + ", ItemId=" + mItemId + ", AddBy=" + mAddBy + ", AddDate=" + mAddDate + ", ContentStatusCd=" + mContentStatusCd + ", ContentTypeCd=" + mContentTypeCd + ", ContentUsageCd=" + mContentUsageCd + ", EffDate=" + mEffDate + ", ExpDate=" + mExpDate + ", LanguageCd=" + mLanguageCd + ", LocaleCd=" + mLocaleCd + ", LongDesc=" + mLongDesc + ", ModBy=" + mModBy + ", ModDate=" + mModDate + ", Path=" + mPath + ", ShortDesc=" + mShortDesc + ", SourceCd=" + mSourceCd + ", Version=" + mVersion + ", Data=" + mData + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("ContentDetail");
	root.setAttribute("Id", String.valueOf(mBusEntityId));

	Element node;

        node = doc.createElement("ContentId");
        node.appendChild(doc.createTextNode(String.valueOf(mContentId)));
        root.appendChild(node);

        node = doc.createElement("ItemId");
        node.appendChild(doc.createTextNode(String.valueOf(mItemId)));
        root.appendChild(node);

        node = doc.createElement("AddBy");
        node.appendChild(doc.createTextNode(String.valueOf(mAddBy)));
        root.appendChild(node);

        node = doc.createElement("AddDate");
        node.appendChild(doc.createTextNode(String.valueOf(mAddDate)));
        root.appendChild(node);

        node = doc.createElement("ContentStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mContentStatusCd)));
        root.appendChild(node);

        node = doc.createElement("ContentTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mContentTypeCd)));
        root.appendChild(node);

        node = doc.createElement("ContentUsageCd");
        node.appendChild(doc.createTextNode(String.valueOf(mContentUsageCd)));
        root.appendChild(node);

        node = doc.createElement("EffDate");
        node.appendChild(doc.createTextNode(String.valueOf(mEffDate)));
        root.appendChild(node);

        node = doc.createElement("ExpDate");
        node.appendChild(doc.createTextNode(String.valueOf(mExpDate)));
        root.appendChild(node);

        node = doc.createElement("LanguageCd");
        node.appendChild(doc.createTextNode(String.valueOf(mLanguageCd)));
        root.appendChild(node);

        node = doc.createElement("LocaleCd");
        node.appendChild(doc.createTextNode(String.valueOf(mLocaleCd)));
        root.appendChild(node);

        node = doc.createElement("LongDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mLongDesc)));
        root.appendChild(node);

        node = doc.createElement("ModBy");
        node.appendChild(doc.createTextNode(String.valueOf(mModBy)));
        root.appendChild(node);

        node = doc.createElement("ModDate");
        node.appendChild(doc.createTextNode(String.valueOf(mModDate)));
        root.appendChild(node);

        node = doc.createElement("Path");
        node.appendChild(doc.createTextNode(String.valueOf(mPath)));
        root.appendChild(node);

        node = doc.createElement("ShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mShortDesc)));
        root.appendChild(node);

        node = doc.createElement("SourceCd");
        node.appendChild(doc.createTextNode(String.valueOf(mSourceCd)));
        root.appendChild(node);

        node = doc.createElement("Version");
        node.appendChild(doc.createTextNode(String.valueOf(mVersion)));
        root.appendChild(node);

        node = doc.createElement("Data");
        node.appendChild(doc.createTextNode(String.valueOf(mData)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public ContentDetailView copy()  {
      ContentDetailView obj = new ContentDetailView();
      obj.setBusEntityId(mBusEntityId);
      obj.setContentId(mContentId);
      obj.setItemId(mItemId);
      obj.setAddBy(mAddBy);
      obj.setAddDate(mAddDate);
      obj.setContentStatusCd(mContentStatusCd);
      obj.setContentTypeCd(mContentTypeCd);
      obj.setContentUsageCd(mContentUsageCd);
      obj.setEffDate(mEffDate);
      obj.setExpDate(mExpDate);
      obj.setLanguageCd(mLanguageCd);
      obj.setLocaleCd(mLocaleCd);
      obj.setLongDesc(mLongDesc);
      obj.setModBy(mModBy);
      obj.setModDate(mModDate);
      obj.setPath(mPath);
      obj.setShortDesc(mShortDesc);
      obj.setSourceCd(mSourceCd);
      obj.setVersion(mVersion);
      obj.setData(mData);
      
      return obj;
    }

    
    /**
     * Sets the BusEntityId property.
     *
     * @param pBusEntityId
     *  int to use to update the property.
     */
    public void setBusEntityId(int pBusEntityId){
        this.mBusEntityId = pBusEntityId;
    }
    /**
     * Retrieves the BusEntityId property.
     *
     * @return
     *  int containing the BusEntityId property.
     */
    public int getBusEntityId(){
        return mBusEntityId;
    }


    /**
     * Sets the ContentId property.
     *
     * @param pContentId
     *  int to use to update the property.
     */
    public void setContentId(int pContentId){
        this.mContentId = pContentId;
    }
    /**
     * Retrieves the ContentId property.
     *
     * @return
     *  int containing the ContentId property.
     */
    public int getContentId(){
        return mContentId;
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
     * Sets the AddBy property.
     *
     * @param pAddBy
     *  String to use to update the property.
     */
    public void setAddBy(String pAddBy){
        this.mAddBy = pAddBy;
    }
    /**
     * Retrieves the AddBy property.
     *
     * @return
     *  String containing the AddBy property.
     */
    public String getAddBy(){
        return mAddBy;
    }


    /**
     * Sets the AddDate property.
     *
     * @param pAddDate
     *  Date to use to update the property.
     */
    public void setAddDate(Date pAddDate){
        this.mAddDate = pAddDate;
    }
    /**
     * Retrieves the AddDate property.
     *
     * @return
     *  Date containing the AddDate property.
     */
    public Date getAddDate(){
        return mAddDate;
    }


    /**
     * Sets the ContentStatusCd property.
     *
     * @param pContentStatusCd
     *  String to use to update the property.
     */
    public void setContentStatusCd(String pContentStatusCd){
        this.mContentStatusCd = pContentStatusCd;
    }
    /**
     * Retrieves the ContentStatusCd property.
     *
     * @return
     *  String containing the ContentStatusCd property.
     */
    public String getContentStatusCd(){
        return mContentStatusCd;
    }


    /**
     * Sets the ContentTypeCd property.
     *
     * @param pContentTypeCd
     *  String to use to update the property.
     */
    public void setContentTypeCd(String pContentTypeCd){
        this.mContentTypeCd = pContentTypeCd;
    }
    /**
     * Retrieves the ContentTypeCd property.
     *
     * @return
     *  String containing the ContentTypeCd property.
     */
    public String getContentTypeCd(){
        return mContentTypeCd;
    }


    /**
     * Sets the ContentUsageCd property.
     *
     * @param pContentUsageCd
     *  String to use to update the property.
     */
    public void setContentUsageCd(String pContentUsageCd){
        this.mContentUsageCd = pContentUsageCd;
    }
    /**
     * Retrieves the ContentUsageCd property.
     *
     * @return
     *  String containing the ContentUsageCd property.
     */
    public String getContentUsageCd(){
        return mContentUsageCd;
    }


    /**
     * Sets the EffDate property.
     *
     * @param pEffDate
     *  Date to use to update the property.
     */
    public void setEffDate(Date pEffDate){
        this.mEffDate = pEffDate;
    }
    /**
     * Retrieves the EffDate property.
     *
     * @return
     *  Date containing the EffDate property.
     */
    public Date getEffDate(){
        return mEffDate;
    }


    /**
     * Sets the ExpDate property.
     *
     * @param pExpDate
     *  Date to use to update the property.
     */
    public void setExpDate(Date pExpDate){
        this.mExpDate = pExpDate;
    }
    /**
     * Retrieves the ExpDate property.
     *
     * @return
     *  Date containing the ExpDate property.
     */
    public Date getExpDate(){
        return mExpDate;
    }


    /**
     * Sets the LanguageCd property.
     *
     * @param pLanguageCd
     *  String to use to update the property.
     */
    public void setLanguageCd(String pLanguageCd){
        this.mLanguageCd = pLanguageCd;
    }
    /**
     * Retrieves the LanguageCd property.
     *
     * @return
     *  String containing the LanguageCd property.
     */
    public String getLanguageCd(){
        return mLanguageCd;
    }


    /**
     * Sets the LocaleCd property.
     *
     * @param pLocaleCd
     *  String to use to update the property.
     */
    public void setLocaleCd(String pLocaleCd){
        this.mLocaleCd = pLocaleCd;
    }
    /**
     * Retrieves the LocaleCd property.
     *
     * @return
     *  String containing the LocaleCd property.
     */
    public String getLocaleCd(){
        return mLocaleCd;
    }


    /**
     * Sets the LongDesc property.
     *
     * @param pLongDesc
     *  String to use to update the property.
     */
    public void setLongDesc(String pLongDesc){
        this.mLongDesc = pLongDesc;
    }
    /**
     * Retrieves the LongDesc property.
     *
     * @return
     *  String containing the LongDesc property.
     */
    public String getLongDesc(){
        return mLongDesc;
    }


    /**
     * Sets the ModBy property.
     *
     * @param pModBy
     *  String to use to update the property.
     */
    public void setModBy(String pModBy){
        this.mModBy = pModBy;
    }
    /**
     * Retrieves the ModBy property.
     *
     * @return
     *  String containing the ModBy property.
     */
    public String getModBy(){
        return mModBy;
    }


    /**
     * Sets the ModDate property.
     *
     * @param pModDate
     *  Date to use to update the property.
     */
    public void setModDate(Date pModDate){
        this.mModDate = pModDate;
    }
    /**
     * Retrieves the ModDate property.
     *
     * @return
     *  Date containing the ModDate property.
     */
    public Date getModDate(){
        return mModDate;
    }


    /**
     * Sets the Path property.
     *
     * @param pPath
     *  String to use to update the property.
     */
    public void setPath(String pPath){
        this.mPath = pPath;
    }
    /**
     * Retrieves the Path property.
     *
     * @return
     *  String containing the Path property.
     */
    public String getPath(){
        return mPath;
    }


    /**
     * Sets the ShortDesc property.
     *
     * @param pShortDesc
     *  String to use to update the property.
     */
    public void setShortDesc(String pShortDesc){
        this.mShortDesc = pShortDesc;
    }
    /**
     * Retrieves the ShortDesc property.
     *
     * @return
     *  String containing the ShortDesc property.
     */
    public String getShortDesc(){
        return mShortDesc;
    }


    /**
     * Sets the SourceCd property.
     *
     * @param pSourceCd
     *  String to use to update the property.
     */
    public void setSourceCd(String pSourceCd){
        this.mSourceCd = pSourceCd;
    }
    /**
     * Retrieves the SourceCd property.
     *
     * @return
     *  String containing the SourceCd property.
     */
    public String getSourceCd(){
        return mSourceCd;
    }


    /**
     * Sets the Version property.
     *
     * @param pVersion
     *  int to use to update the property.
     */
    public void setVersion(int pVersion){
        this.mVersion = pVersion;
    }
    /**
     * Retrieves the Version property.
     *
     * @return
     *  int containing the Version property.
     */
    public int getVersion(){
        return mVersion;
    }


    /**
     * Sets the Data property.
     *
     * @param pData
     *  byte[] to use to update the property.
     */
    public void setData(byte[] pData){
        this.mData = pData;
    }
    /**
     * Retrieves the Data property.
     *
     * @return
     *  byte[] containing the Data property.
     */
    public byte[] getData(){
        return mData;
    }


    
}
