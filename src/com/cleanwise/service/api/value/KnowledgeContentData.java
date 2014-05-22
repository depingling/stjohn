
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        KnowledgeContentData
 * Description:  This is a ValueObject class wrapping the database table CLW_KNOWLEDGE_CONTENT.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.KnowledgeContentDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>KnowledgeContentData</code> is a ValueObject class wrapping of the database table CLW_KNOWLEDGE_CONTENT.
 */
public class KnowledgeContentData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -4742105858508845747L;
    private int mKnowledgeContentId;// SQL type:NUMBER, not null
    private int mKnowledgeId;// SQL type:NUMBER, not null
    private String mContentUrl;// SQL type:VARCHAR2
    private String mContentFormat;// SQL type:VARCHAR2
    private String mContentSource;// SQL type:VARCHAR2
    private String mLongDesc;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE
    private Date mAddTime;// SQL type:DATE
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public KnowledgeContentData ()
    {
        mContentUrl = "";
        mContentFormat = "";
        mContentSource = "";
        mLongDesc = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public KnowledgeContentData(int parm1, int parm2, String parm3, String parm4, String parm5, String parm6, Date parm7, Date parm8, String parm9, Date parm10, String parm11)
    {
        mKnowledgeContentId = parm1;
        mKnowledgeId = parm2;
        mContentUrl = parm3;
        mContentFormat = parm4;
        mContentSource = parm5;
        mLongDesc = parm6;
        mAddDate = parm7;
        mAddTime = parm8;
        mAddBy = parm9;
        mModDate = parm10;
        mModBy = parm11;
        
    }

    /**
     * Creates a new KnowledgeContentData
     *
     * @return
     *  Newly initialized KnowledgeContentData object.
     */
    public static KnowledgeContentData createValue ()
    {
        KnowledgeContentData valueData = new KnowledgeContentData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this KnowledgeContentData object
     */
    public String toString()
    {
        return "[" + "KnowledgeContentId=" + mKnowledgeContentId + ", KnowledgeId=" + mKnowledgeId + ", ContentUrl=" + mContentUrl + ", ContentFormat=" + mContentFormat + ", ContentSource=" + mContentSource + ", LongDesc=" + mLongDesc + ", AddDate=" + mAddDate + ", AddTime=" + mAddTime + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("KnowledgeContent");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mKnowledgeContentId));

        node =  doc.createElement("KnowledgeId");
        node.appendChild(doc.createTextNode(String.valueOf(mKnowledgeId)));
        root.appendChild(node);

        node =  doc.createElement("ContentUrl");
        node.appendChild(doc.createTextNode(String.valueOf(mContentUrl)));
        root.appendChild(node);

        node =  doc.createElement("ContentFormat");
        node.appendChild(doc.createTextNode(String.valueOf(mContentFormat)));
        root.appendChild(node);

        node =  doc.createElement("ContentSource");
        node.appendChild(doc.createTextNode(String.valueOf(mContentSource)));
        root.appendChild(node);

        node =  doc.createElement("LongDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mLongDesc)));
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

        return root;
    }

    /**
    * creates a clone of this object, the KnowledgeContentId field is not cloned.
    *
    * @return KnowledgeContentData object
    */
    public Object clone(){
        KnowledgeContentData myClone = new KnowledgeContentData();
        
        myClone.mKnowledgeId = mKnowledgeId;
        
        myClone.mContentUrl = mContentUrl;
        
        myClone.mContentFormat = mContentFormat;
        
        myClone.mContentSource = mContentSource;
        
        myClone.mLongDesc = mLongDesc;
        
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
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (KnowledgeContentDataAccess.KNOWLEDGE_CONTENT_ID.equals(pFieldName)) {
            return getKnowledgeContentId();
        } else if (KnowledgeContentDataAccess.KNOWLEDGE_ID.equals(pFieldName)) {
            return getKnowledgeId();
        } else if (KnowledgeContentDataAccess.CONTENT_URL.equals(pFieldName)) {
            return getContentUrl();
        } else if (KnowledgeContentDataAccess.CONTENT_FORMAT.equals(pFieldName)) {
            return getContentFormat();
        } else if (KnowledgeContentDataAccess.CONTENT_SOURCE.equals(pFieldName)) {
            return getContentSource();
        } else if (KnowledgeContentDataAccess.LONG_DESC.equals(pFieldName)) {
            return getLongDesc();
        } else if (KnowledgeContentDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (KnowledgeContentDataAccess.ADD_TIME.equals(pFieldName)) {
            return getAddTime();
        } else if (KnowledgeContentDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (KnowledgeContentDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (KnowledgeContentDataAccess.MOD_BY.equals(pFieldName)) {
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
        return KnowledgeContentDataAccess.CLW_KNOWLEDGE_CONTENT;
    }

    
    /**
     * Sets the KnowledgeContentId field. This field is required to be set in the database.
     *
     * @param pKnowledgeContentId
     *  int to use to update the field.
     */
    public void setKnowledgeContentId(int pKnowledgeContentId){
        this.mKnowledgeContentId = pKnowledgeContentId;
        setDirty(true);
    }
    /**
     * Retrieves the KnowledgeContentId field.
     *
     * @return
     *  int containing the KnowledgeContentId field.
     */
    public int getKnowledgeContentId(){
        return mKnowledgeContentId;
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
     * Sets the ContentUrl field.
     *
     * @param pContentUrl
     *  String to use to update the field.
     */
    public void setContentUrl(String pContentUrl){
        this.mContentUrl = pContentUrl;
        setDirty(true);
    }
    /**
     * Retrieves the ContentUrl field.
     *
     * @return
     *  String containing the ContentUrl field.
     */
    public String getContentUrl(){
        return mContentUrl;
    }

    /**
     * Sets the ContentFormat field.
     *
     * @param pContentFormat
     *  String to use to update the field.
     */
    public void setContentFormat(String pContentFormat){
        this.mContentFormat = pContentFormat;
        setDirty(true);
    }
    /**
     * Retrieves the ContentFormat field.
     *
     * @return
     *  String containing the ContentFormat field.
     */
    public String getContentFormat(){
        return mContentFormat;
    }

    /**
     * Sets the ContentSource field.
     *
     * @param pContentSource
     *  String to use to update the field.
     */
    public void setContentSource(String pContentSource){
        this.mContentSource = pContentSource;
        setDirty(true);
    }
    /**
     * Retrieves the ContentSource field.
     *
     * @return
     *  String containing the ContentSource field.
     */
    public String getContentSource(){
        return mContentSource;
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


}
