
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        WarrantyContentData
 * Description:  This is a ValueObject class wrapping the database table CLW_WARRANTY_CONTENT.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.WarrantyContentDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>WarrantyContentData</code> is a ValueObject class wrapping of the database table CLW_WARRANTY_CONTENT.
 */
public class WarrantyContentData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -2533791569537187385L;
    private int mWarrantyContentId;// SQL type:NUMBER, not null
    private int mWarrantyId;// SQL type:NUMBER, not null
    private int mContentId;// SQL type:NUMBER
    private String mUrl;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public WarrantyContentData ()
    {
        mUrl = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public WarrantyContentData(int parm1, int parm2, int parm3, String parm4, Date parm5, String parm6, Date parm7, String parm8)
    {
        mWarrantyContentId = parm1;
        mWarrantyId = parm2;
        mContentId = parm3;
        mUrl = parm4;
        mAddDate = parm5;
        mAddBy = parm6;
        mModDate = parm7;
        mModBy = parm8;
        
    }

    /**
     * Creates a new WarrantyContentData
     *
     * @return
     *  Newly initialized WarrantyContentData object.
     */
    public static WarrantyContentData createValue ()
    {
        WarrantyContentData valueData = new WarrantyContentData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this WarrantyContentData object
     */
    public String toString()
    {
        return "[" + "WarrantyContentId=" + mWarrantyContentId + ", WarrantyId=" + mWarrantyId + ", ContentId=" + mContentId + ", Url=" + mUrl + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("WarrantyContent");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mWarrantyContentId));

        node =  doc.createElement("WarrantyId");
        node.appendChild(doc.createTextNode(String.valueOf(mWarrantyId)));
        root.appendChild(node);

        node =  doc.createElement("ContentId");
        node.appendChild(doc.createTextNode(String.valueOf(mContentId)));
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
    * creates a clone of this object, the WarrantyContentId field is not cloned.
    *
    * @return WarrantyContentData object
    */
    public Object clone(){
        WarrantyContentData myClone = new WarrantyContentData();
        
        myClone.mWarrantyId = mWarrantyId;
        
        myClone.mContentId = mContentId;
        
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

        if (WarrantyContentDataAccess.WARRANTY_CONTENT_ID.equals(pFieldName)) {
            return getWarrantyContentId();
        } else if (WarrantyContentDataAccess.WARRANTY_ID.equals(pFieldName)) {
            return getWarrantyId();
        } else if (WarrantyContentDataAccess.CONTENT_ID.equals(pFieldName)) {
            return getContentId();
        } else if (WarrantyContentDataAccess.URL.equals(pFieldName)) {
            return getUrl();
        } else if (WarrantyContentDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (WarrantyContentDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (WarrantyContentDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (WarrantyContentDataAccess.MOD_BY.equals(pFieldName)) {
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
        return WarrantyContentDataAccess.CLW_WARRANTY_CONTENT;
    }

    
    /**
     * Sets the WarrantyContentId field. This field is required to be set in the database.
     *
     * @param pWarrantyContentId
     *  int to use to update the field.
     */
    public void setWarrantyContentId(int pWarrantyContentId){
        this.mWarrantyContentId = pWarrantyContentId;
        setDirty(true);
    }
    /**
     * Retrieves the WarrantyContentId field.
     *
     * @return
     *  int containing the WarrantyContentId field.
     */
    public int getWarrantyContentId(){
        return mWarrantyContentId;
    }

    /**
     * Sets the WarrantyId field. This field is required to be set in the database.
     *
     * @param pWarrantyId
     *  int to use to update the field.
     */
    public void setWarrantyId(int pWarrantyId){
        this.mWarrantyId = pWarrantyId;
        setDirty(true);
    }
    /**
     * Retrieves the WarrantyId field.
     *
     * @return
     *  int containing the WarrantyId field.
     */
    public int getWarrantyId(){
        return mWarrantyId;
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
