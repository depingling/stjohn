
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        AssetContentData
 * Description:  This is a ValueObject class wrapping the database table CLW_ASSET_CONTENT.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.AssetContentDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>AssetContentData</code> is a ValueObject class wrapping of the database table CLW_ASSET_CONTENT.
 */
public class AssetContentData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -3433951529793068191L;
    private int mAssetContentId;// SQL type:NUMBER, not null
    private int mAssetId;// SQL type:NUMBER, not null
    private int mContentId;// SQL type:NUMBER
    private String mTypeCd;// SQL type:VARCHAR2, not null
    private String mUrl;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public AssetContentData ()
    {
        mTypeCd = "";
        mUrl = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public AssetContentData(int parm1, int parm2, int parm3, String parm4, String parm5, Date parm6, String parm7, Date parm8, String parm9)
    {
        mAssetContentId = parm1;
        mAssetId = parm2;
        mContentId = parm3;
        mTypeCd = parm4;
        mUrl = parm5;
        mAddDate = parm6;
        mAddBy = parm7;
        mModDate = parm8;
        mModBy = parm9;
        
    }

    /**
     * Creates a new AssetContentData
     *
     * @return
     *  Newly initialized AssetContentData object.
     */
    public static AssetContentData createValue ()
    {
        AssetContentData valueData = new AssetContentData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this AssetContentData object
     */
    public String toString()
    {
        return "[" + "AssetContentId=" + mAssetContentId + ", AssetId=" + mAssetId + ", ContentId=" + mContentId + ", TypeCd=" + mTypeCd + ", Url=" + mUrl + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("AssetContent");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mAssetContentId));

        node =  doc.createElement("AssetId");
        node.appendChild(doc.createTextNode(String.valueOf(mAssetId)));
        root.appendChild(node);

        node =  doc.createElement("ContentId");
        node.appendChild(doc.createTextNode(String.valueOf(mContentId)));
        root.appendChild(node);

        node =  doc.createElement("TypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mTypeCd)));
        root.appendChild(node);

        node =  doc.createElement("Url");
        node.appendChild(doc.createTextNode(String.valueOf(mUrl)));
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

        return root;
    }

    /**
    * creates a clone of this object, the AssetContentId field is not cloned.
    *
    * @return AssetContentData object
    */
    public Object clone(){
        AssetContentData myClone = new AssetContentData();
        
        myClone.mAssetId = mAssetId;
        
        myClone.mContentId = mContentId;
        
        myClone.mTypeCd = mTypeCd;
        
        myClone.mUrl = mUrl;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (AssetContentDataAccess.ASSET_CONTENT_ID.equals(pFieldName)) {
            return getAssetContentId();
        } else if (AssetContentDataAccess.ASSET_ID.equals(pFieldName)) {
            return getAssetId();
        } else if (AssetContentDataAccess.CONTENT_ID.equals(pFieldName)) {
            return getContentId();
        } else if (AssetContentDataAccess.TYPE_CD.equals(pFieldName)) {
            return getTypeCd();
        } else if (AssetContentDataAccess.URL.equals(pFieldName)) {
            return getUrl();
        } else if (AssetContentDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (AssetContentDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (AssetContentDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (AssetContentDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
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
        return AssetContentDataAccess.CLW_ASSET_CONTENT;
    }

    
    /**
     * Sets the AssetContentId field. This field is required to be set in the database.
     *
     * @param pAssetContentId
     *  int to use to update the field.
     */
    public void setAssetContentId(int pAssetContentId){
        this.mAssetContentId = pAssetContentId;
        setDirty(true);
    }
    /**
     * Retrieves the AssetContentId field.
     *
     * @return
     *  int containing the AssetContentId field.
     */
    public int getAssetContentId(){
        return mAssetContentId;
    }

    /**
     * Sets the AssetId field. This field is required to be set in the database.
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
     * Sets the ContentId field.
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
     * Sets the TypeCd field. This field is required to be set in the database.
     *
     * @param pTypeCd
     *  String to use to update the field.
     */
    public void setTypeCd(String pTypeCd){
        this.mTypeCd = pTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the TypeCd field.
     *
     * @return
     *  String containing the TypeCd field.
     */
    public String getTypeCd(){
        return mTypeCd;
    }

    /**
     * Sets the Url field.
     *
     * @param pUrl
     *  String to use to update the field.
     */
    public void setUrl(String pUrl){
        this.mUrl = pUrl;
        setDirty(true);
    }
    /**
     * Retrieves the Url field.
     *
     * @return
     *  String containing the Url field.
     */
    public String getUrl(){
        return mUrl;
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


}
