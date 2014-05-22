
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        CatalogData
 * Description:  This is a ValueObject class wrapping the database table CLW_CATALOG.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.CatalogDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>CatalogData</code> is a ValueObject class wrapping of the database table CLW_CATALOG.
 */
public class CatalogData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -1L;
    private int mCatalogId;// SQL type:NUMBER, not null
    private String mShortDesc;// SQL type:VARCHAR2, not null
    private String mShippingMessage;// SQL type:VARCHAR2
    private String mCatalogStatusCd;// SQL type:VARCHAR2, not null
    private String mCatalogTypeCd;// SQL type:VARCHAR2, not null
    private Date mEffDate;// SQL type:DATE
    private Date mExpDate;// SQL type:DATE
    private int mRankWeight;// SQL type:NUMBER
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private String mLoaderField;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public CatalogData ()
    {
        mShortDesc = "";
        mShippingMessage = "";
        mCatalogStatusCd = "";
        mCatalogTypeCd = "";
        mAddBy = "";
        mModBy = "";
        mLoaderField = "";
    }

    /**
     * Constructor.
     */
    public CatalogData(int parm1, String parm2, String parm3, String parm4, String parm5, Date parm6, Date parm7, int parm8, Date parm9, String parm10, Date parm11, String parm12, String parm13)
    {
        mCatalogId = parm1;
        mShortDesc = parm2;
        mShippingMessage = parm3;
        mCatalogStatusCd = parm4;
        mCatalogTypeCd = parm5;
        mEffDate = parm6;
        mExpDate = parm7;
        mRankWeight = parm8;
        mAddDate = parm9;
        mAddBy = parm10;
        mModDate = parm11;
        mModBy = parm12;
        mLoaderField = parm13;
        
    }

    /**
     * Creates a new CatalogData
     *
     * @return
     *  Newly initialized CatalogData object.
     */
    public static CatalogData createValue ()
    {
        CatalogData valueData = new CatalogData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this CatalogData object
     */
    public String toString()
    {
        return "[" + "CatalogId=" + mCatalogId + ", ShortDesc=" + mShortDesc + ", ShippingMessage=" + mShippingMessage + ", CatalogStatusCd=" + mCatalogStatusCd + ", CatalogTypeCd=" + mCatalogTypeCd + ", EffDate=" + mEffDate + ", ExpDate=" + mExpDate + ", RankWeight=" + mRankWeight + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", LoaderField=" + mLoaderField + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("Catalog");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mCatalogId));

        node =  doc.createElement("ShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mShortDesc)));
        root.appendChild(node);

        node =  doc.createElement("ShippingMessage");
        node.appendChild(doc.createTextNode(String.valueOf(mShippingMessage)));
        root.appendChild(node);

        node =  doc.createElement("CatalogStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mCatalogStatusCd)));
        root.appendChild(node);

        node =  doc.createElement("CatalogTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mCatalogTypeCd)));
        root.appendChild(node);

        node =  doc.createElement("EffDate");
        node.appendChild(doc.createTextNode(String.valueOf(mEffDate)));
        root.appendChild(node);

        node =  doc.createElement("ExpDate");
        node.appendChild(doc.createTextNode(String.valueOf(mExpDate)));
        root.appendChild(node);

        node =  doc.createElement("RankWeight");
        node.appendChild(doc.createTextNode(String.valueOf(mRankWeight)));
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

        node =  doc.createElement("LoaderField");
        node.appendChild(doc.createTextNode(String.valueOf(mLoaderField)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the CatalogId field is not cloned.
    *
    * @return CatalogData object
    */
    public Object clone(){
        CatalogData myClone = new CatalogData();
        
        myClone.mShortDesc = mShortDesc;
        
        myClone.mShippingMessage = mShippingMessage;
        
        myClone.mCatalogStatusCd = mCatalogStatusCd;
        
        myClone.mCatalogTypeCd = mCatalogTypeCd;
        
        if(mEffDate != null){
                myClone.mEffDate = (Date) mEffDate.clone();
        }
        
        if(mExpDate != null){
                myClone.mExpDate = (Date) mExpDate.clone();
        }
        
        myClone.mRankWeight = mRankWeight;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        myClone.mLoaderField = mLoaderField;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (CatalogDataAccess.CATALOG_ID.equals(pFieldName)) {
            return getCatalogId();
        } else if (CatalogDataAccess.SHORT_DESC.equals(pFieldName)) {
            return getShortDesc();
        } else if (CatalogDataAccess.SHIPPING_MESSAGE.equals(pFieldName)) {
            return getShippingMessage();
        } else if (CatalogDataAccess.CATALOG_STATUS_CD.equals(pFieldName)) {
            return getCatalogStatusCd();
        } else if (CatalogDataAccess.CATALOG_TYPE_CD.equals(pFieldName)) {
            return getCatalogTypeCd();
        } else if (CatalogDataAccess.EFF_DATE.equals(pFieldName)) {
            return getEffDate();
        } else if (CatalogDataAccess.EXP_DATE.equals(pFieldName)) {
            return getExpDate();
        } else if (CatalogDataAccess.RANK_WEIGHT.equals(pFieldName)) {
            return getRankWeight();
        } else if (CatalogDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (CatalogDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (CatalogDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (CatalogDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (CatalogDataAccess.LOADER_FIELD.equals(pFieldName)) {
            return getLoaderField();
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
        return CatalogDataAccess.CLW_CATALOG;
    }

    
    /**
     * Sets the CatalogId field. This field is required to be set in the database.
     *
     * @param pCatalogId
     *  int to use to update the field.
     */
    public void setCatalogId(int pCatalogId){
        this.mCatalogId = pCatalogId;
        setDirty(true);
    }
    /**
     * Retrieves the CatalogId field.
     *
     * @return
     *  int containing the CatalogId field.
     */
    public int getCatalogId(){
        return mCatalogId;
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
     * Sets the ShippingMessage field.
     *
     * @param pShippingMessage
     *  String to use to update the field.
     */
    public void setShippingMessage(String pShippingMessage){
        this.mShippingMessage = pShippingMessage;
        setDirty(true);
    }
    /**
     * Retrieves the ShippingMessage field.
     *
     * @return
     *  String containing the ShippingMessage field.
     */
    public String getShippingMessage(){
        return mShippingMessage;
    }

    /**
     * Sets the CatalogStatusCd field. This field is required to be set in the database.
     *
     * @param pCatalogStatusCd
     *  String to use to update the field.
     */
    public void setCatalogStatusCd(String pCatalogStatusCd){
        this.mCatalogStatusCd = pCatalogStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the CatalogStatusCd field.
     *
     * @return
     *  String containing the CatalogStatusCd field.
     */
    public String getCatalogStatusCd(){
        return mCatalogStatusCd;
    }

    /**
     * Sets the CatalogTypeCd field. This field is required to be set in the database.
     *
     * @param pCatalogTypeCd
     *  String to use to update the field.
     */
    public void setCatalogTypeCd(String pCatalogTypeCd){
        this.mCatalogTypeCd = pCatalogTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the CatalogTypeCd field.
     *
     * @return
     *  String containing the CatalogTypeCd field.
     */
    public String getCatalogTypeCd(){
        return mCatalogTypeCd;
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
     * Sets the RankWeight field.
     *
     * @param pRankWeight
     *  int to use to update the field.
     */
    public void setRankWeight(int pRankWeight){
        this.mRankWeight = pRankWeight;
        setDirty(true);
    }
    /**
     * Retrieves the RankWeight field.
     *
     * @return
     *  int containing the RankWeight field.
     */
    public int getRankWeight(){
        return mRankWeight;
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
     * Sets the LoaderField field.
     *
     * @param pLoaderField
     *  String to use to update the field.
     */
    public void setLoaderField(String pLoaderField){
        this.mLoaderField = pLoaderField;
        setDirty(true);
    }
    /**
     * Retrieves the LoaderField field.
     *
     * @return
     *  String containing the LoaderField field.
     */
    public String getLoaderField(){
        return mLoaderField;
    }


}
