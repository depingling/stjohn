
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ContentDetailData
 * Description:  This is a ValueObject class wrapping the database table CLW_CONTENT_DETAIL.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.ContentDetailDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>ContentDetailData</code> is a ValueObject class wrapping of the database table CLW_CONTENT_DETAIL.
 */
public class ContentDetailData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 9196087044146092466L;
    private int mContentId;// SQL type:NUMBER, not null
    private int mContentDetailId;// SQL type:NUMBER, not null
    private String mShortDesc;// SQL type:VARCHAR2, not null
    private int mSequenceNum;// SQL type:NUMBER
    private String mValue;// SQL type:VARCHAR2, not null
    private String mContentDetailTypeCd;// SQL type:VARCHAR2, not null
    private String mContentDetailStatusCd;// SQL type:VARCHAR2, not null
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public ContentDetailData ()
    {
        mShortDesc = "";
        mValue = "";
        mContentDetailTypeCd = "";
        mContentDetailStatusCd = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public ContentDetailData(int parm1, int parm2, String parm3, int parm4, String parm5, String parm6, String parm7, Date parm8, String parm9, Date parm10, String parm11)
    {
        mContentId = parm1;
        mContentDetailId = parm2;
        mShortDesc = parm3;
        mSequenceNum = parm4;
        mValue = parm5;
        mContentDetailTypeCd = parm6;
        mContentDetailStatusCd = parm7;
        mAddDate = parm8;
        mAddBy = parm9;
        mModDate = parm10;
        mModBy = parm11;
        
    }

    /**
     * Creates a new ContentDetailData
     *
     * @return
     *  Newly initialized ContentDetailData object.
     */
    public static ContentDetailData createValue ()
    {
        ContentDetailData valueData = new ContentDetailData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ContentDetailData object
     */
    public String toString()
    {
        return "[" + "ContentId=" + mContentId + ", ContentDetailId=" + mContentDetailId + ", ShortDesc=" + mShortDesc + ", SequenceNum=" + mSequenceNum + ", Value=" + mValue + ", ContentDetailTypeCd=" + mContentDetailTypeCd + ", ContentDetailStatusCd=" + mContentDetailStatusCd + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("ContentDetail");
        
        Element node;

        node =  doc.createElement("ContentId");
        node.appendChild(doc.createTextNode(String.valueOf(mContentId)));
        root.appendChild(node);

        root.setAttribute("Id", String.valueOf(mContentDetailId));

        node =  doc.createElement("ShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mShortDesc)));
        root.appendChild(node);

        node =  doc.createElement("SequenceNum");
        node.appendChild(doc.createTextNode(String.valueOf(mSequenceNum)));
        root.appendChild(node);

        node =  doc.createElement("Value");
        node.appendChild(doc.createTextNode(String.valueOf(mValue)));
        root.appendChild(node);

        node =  doc.createElement("ContentDetailTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mContentDetailTypeCd)));
        root.appendChild(node);

        node =  doc.createElement("ContentDetailStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mContentDetailStatusCd)));
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
    * creates a clone of this object, the ContentDetailId field is not cloned.
    *
    * @return ContentDetailData object
    */
    public Object clone(){
        ContentDetailData myClone = new ContentDetailData();
        
        myClone.mContentId = mContentId;
        
        myClone.mShortDesc = mShortDesc;
        
        myClone.mSequenceNum = mSequenceNum;
        
        myClone.mValue = mValue;
        
        myClone.mContentDetailTypeCd = mContentDetailTypeCd;
        
        myClone.mContentDetailStatusCd = mContentDetailStatusCd;
        
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

        if (ContentDetailDataAccess.CONTENT_ID.equals(pFieldName)) {
            return getContentId();
        } else if (ContentDetailDataAccess.CONTENT_DETAIL_ID.equals(pFieldName)) {
            return getContentDetailId();
        } else if (ContentDetailDataAccess.SHORT_DESC.equals(pFieldName)) {
            return getShortDesc();
        } else if (ContentDetailDataAccess.SEQUENCE_NUM.equals(pFieldName)) {
            return getSequenceNum();
        } else if (ContentDetailDataAccess.CLW_VALUE.equals(pFieldName)) {
            return getValue();
        } else if (ContentDetailDataAccess.CONTENT_DETAIL_TYPE_CD.equals(pFieldName)) {
            return getContentDetailTypeCd();
        } else if (ContentDetailDataAccess.CONTENT_DETAIL_STATUS_CD.equals(pFieldName)) {
            return getContentDetailStatusCd();
        } else if (ContentDetailDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (ContentDetailDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (ContentDetailDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (ContentDetailDataAccess.MOD_BY.equals(pFieldName)) {
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
        return ContentDetailDataAccess.CLW_CONTENT_DETAIL;
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
     * Sets the ContentDetailId field. This field is required to be set in the database.
     *
     * @param pContentDetailId
     *  int to use to update the field.
     */
    public void setContentDetailId(int pContentDetailId){
        this.mContentDetailId = pContentDetailId;
        setDirty(true);
    }
    /**
     * Retrieves the ContentDetailId field.
     *
     * @return
     *  int containing the ContentDetailId field.
     */
    public int getContentDetailId(){
        return mContentDetailId;
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
     * Sets the SequenceNum field.
     *
     * @param pSequenceNum
     *  int to use to update the field.
     */
    public void setSequenceNum(int pSequenceNum){
        this.mSequenceNum = pSequenceNum;
        setDirty(true);
    }
    /**
     * Retrieves the SequenceNum field.
     *
     * @return
     *  int containing the SequenceNum field.
     */
    public int getSequenceNum(){
        return mSequenceNum;
    }

    /**
     * Sets the Value field. This field is required to be set in the database.
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
     * Sets the ContentDetailTypeCd field. This field is required to be set in the database.
     *
     * @param pContentDetailTypeCd
     *  String to use to update the field.
     */
    public void setContentDetailTypeCd(String pContentDetailTypeCd){
        this.mContentDetailTypeCd = pContentDetailTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the ContentDetailTypeCd field.
     *
     * @return
     *  String containing the ContentDetailTypeCd field.
     */
    public String getContentDetailTypeCd(){
        return mContentDetailTypeCd;
    }

    /**
     * Sets the ContentDetailStatusCd field. This field is required to be set in the database.
     *
     * @param pContentDetailStatusCd
     *  String to use to update the field.
     */
    public void setContentDetailStatusCd(String pContentDetailStatusCd){
        this.mContentDetailStatusCd = pContentDetailStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the ContentDetailStatusCd field.
     *
     * @return
     *  String containing the ContentDetailStatusCd field.
     */
    public String getContentDetailStatusCd(){
        return mContentDetailStatusCd;
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
