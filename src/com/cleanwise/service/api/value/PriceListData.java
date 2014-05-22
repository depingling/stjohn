
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        PriceListData
 * Description:  This is a ValueObject class wrapping the database table CLW_PRICE_LIST.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.PriceListDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>PriceListData</code> is a ValueObject class wrapping of the database table CLW_PRICE_LIST.
 */
public class PriceListData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -1L;
    private int mPriceListId;// SQL type:NUMBER, not null
    private String mShortDesc;// SQL type:VARCHAR2, not null
    private int mRank;// SQL type:NUMBER
    private String mPriceListStatusCd;// SQL type:VARCHAR2, not null
    private String mPriceListTypeCd;// SQL type:VARCHAR2, not null
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public PriceListData ()
    {
        mShortDesc = "";
        mPriceListStatusCd = "";
        mPriceListTypeCd = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public PriceListData(int parm1, String parm2, int parm3, String parm4, String parm5, Date parm6, String parm7, Date parm8, String parm9)
    {
        mPriceListId = parm1;
        mShortDesc = parm2;
        mRank = parm3;
        mPriceListStatusCd = parm4;
        mPriceListTypeCd = parm5;
        mAddDate = parm6;
        mAddBy = parm7;
        mModDate = parm8;
        mModBy = parm9;
        
    }

    /**
     * Creates a new PriceListData
     *
     * @return
     *  Newly initialized PriceListData object.
     */
    public static PriceListData createValue ()
    {
        PriceListData valueData = new PriceListData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this PriceListData object
     */
    public String toString()
    {
        return "[" + "PriceListId=" + mPriceListId + ", ShortDesc=" + mShortDesc + ", Rank=" + mRank + ", PriceListStatusCd=" + mPriceListStatusCd + ", PriceListTypeCd=" + mPriceListTypeCd + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("PriceList");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mPriceListId));

        node =  doc.createElement("ShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mShortDesc)));
        root.appendChild(node);

        node =  doc.createElement("Rank");
        node.appendChild(doc.createTextNode(String.valueOf(mRank)));
        root.appendChild(node);

        node =  doc.createElement("PriceListStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mPriceListStatusCd)));
        root.appendChild(node);

        node =  doc.createElement("PriceListTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mPriceListTypeCd)));
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
    * creates a clone of this object, the PriceListId field is not cloned.
    *
    * @return PriceListData object
    */
    public Object clone(){
        PriceListData myClone = new PriceListData();
        
        myClone.mShortDesc = mShortDesc;
        
        myClone.mRank = mRank;
        
        myClone.mPriceListStatusCd = mPriceListStatusCd;
        
        myClone.mPriceListTypeCd = mPriceListTypeCd;
        
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

        if (PriceListDataAccess.PRICE_LIST_ID.equals(pFieldName)) {
            return getPriceListId();
        } else if (PriceListDataAccess.SHORT_DESC.equals(pFieldName)) {
            return getShortDesc();
        } else if (PriceListDataAccess.RANK.equals(pFieldName)) {
            return getRank();
        } else if (PriceListDataAccess.PRICE_LIST_STATUS_CD.equals(pFieldName)) {
            return getPriceListStatusCd();
        } else if (PriceListDataAccess.PRICE_LIST_TYPE_CD.equals(pFieldName)) {
            return getPriceListTypeCd();
        } else if (PriceListDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (PriceListDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (PriceListDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (PriceListDataAccess.MOD_BY.equals(pFieldName)) {
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
        return PriceListDataAccess.CLW_PRICE_LIST;
    }

    
    /**
     * Sets the PriceListId field. This field is required to be set in the database.
     *
     * @param pPriceListId
     *  int to use to update the field.
     */
    public void setPriceListId(int pPriceListId){
        this.mPriceListId = pPriceListId;
        setDirty(true);
    }
    /**
     * Retrieves the PriceListId field.
     *
     * @return
     *  int containing the PriceListId field.
     */
    public int getPriceListId(){
        return mPriceListId;
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
     * Sets the Rank field.
     *
     * @param pRank
     *  int to use to update the field.
     */
    public void setRank(int pRank){
        this.mRank = pRank;
        setDirty(true);
    }
    /**
     * Retrieves the Rank field.
     *
     * @return
     *  int containing the Rank field.
     */
    public int getRank(){
        return mRank;
    }

    /**
     * Sets the PriceListStatusCd field. This field is required to be set in the database.
     *
     * @param pPriceListStatusCd
     *  String to use to update the field.
     */
    public void setPriceListStatusCd(String pPriceListStatusCd){
        this.mPriceListStatusCd = pPriceListStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the PriceListStatusCd field.
     *
     * @return
     *  String containing the PriceListStatusCd field.
     */
    public String getPriceListStatusCd(){
        return mPriceListStatusCd;
    }

    /**
     * Sets the PriceListTypeCd field. This field is required to be set in the database.
     *
     * @param pPriceListTypeCd
     *  String to use to update the field.
     */
    public void setPriceListTypeCd(String pPriceListTypeCd){
        this.mPriceListTypeCd = pPriceListTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the PriceListTypeCd field.
     *
     * @return
     *  String containing the PriceListTypeCd field.
     */
    public String getPriceListTypeCd(){
        return mPriceListTypeCd;
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
