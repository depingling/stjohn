
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        PriceListAssocData
 * Description:  This is a ValueObject class wrapping the database table CLW_PRICE_LIST_ASSOC.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.PriceListAssocDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>PriceListAssocData</code> is a ValueObject class wrapping of the database table CLW_PRICE_LIST_ASSOC.
 */
public class PriceListAssocData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -1L;
    private int mPriceListAssocId;// SQL type:NUMBER, not null
    private int mPriceListId;// SQL type:NUMBER, not null
    private int mBusEntityId;// SQL type:NUMBER, not null
    private String mPriceListAssocCd;// SQL type:VARCHAR2, not null
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public PriceListAssocData ()
    {
        mPriceListAssocCd = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public PriceListAssocData(int parm1, int parm2, int parm3, String parm4, Date parm5, String parm6, Date parm7, String parm8)
    {
        mPriceListAssocId = parm1;
        mPriceListId = parm2;
        mBusEntityId = parm3;
        mPriceListAssocCd = parm4;
        mAddDate = parm5;
        mAddBy = parm6;
        mModDate = parm7;
        mModBy = parm8;
        
    }

    /**
     * Creates a new PriceListAssocData
     *
     * @return
     *  Newly initialized PriceListAssocData object.
     */
    public static PriceListAssocData createValue ()
    {
        PriceListAssocData valueData = new PriceListAssocData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this PriceListAssocData object
     */
    public String toString()
    {
        return "[" + "PriceListAssocId=" + mPriceListAssocId + ", PriceListId=" + mPriceListId + ", BusEntityId=" + mBusEntityId + ", PriceListAssocCd=" + mPriceListAssocCd + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("PriceListAssoc");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mPriceListAssocId));

        node =  doc.createElement("PriceListId");
        node.appendChild(doc.createTextNode(String.valueOf(mPriceListId)));
        root.appendChild(node);

        node =  doc.createElement("BusEntityId");
        node.appendChild(doc.createTextNode(String.valueOf(mBusEntityId)));
        root.appendChild(node);

        node =  doc.createElement("PriceListAssocCd");
        node.appendChild(doc.createTextNode(String.valueOf(mPriceListAssocCd)));
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
    * creates a clone of this object, the PriceListAssocId field is not cloned.
    *
    * @return PriceListAssocData object
    */
    public Object clone(){
        PriceListAssocData myClone = new PriceListAssocData();
        
        myClone.mPriceListId = mPriceListId;
        
        myClone.mBusEntityId = mBusEntityId;
        
        myClone.mPriceListAssocCd = mPriceListAssocCd;
        
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

        if (PriceListAssocDataAccess.PRICE_LIST_ASSOC_ID.equals(pFieldName)) {
            return getPriceListAssocId();
        } else if (PriceListAssocDataAccess.PRICE_LIST_ID.equals(pFieldName)) {
            return getPriceListId();
        } else if (PriceListAssocDataAccess.BUS_ENTITY_ID.equals(pFieldName)) {
            return getBusEntityId();
        } else if (PriceListAssocDataAccess.PRICE_LIST_ASSOC_CD.equals(pFieldName)) {
            return getPriceListAssocCd();
        } else if (PriceListAssocDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (PriceListAssocDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (PriceListAssocDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (PriceListAssocDataAccess.MOD_BY.equals(pFieldName)) {
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
        return PriceListAssocDataAccess.CLW_PRICE_LIST_ASSOC;
    }

    
    /**
     * Sets the PriceListAssocId field. This field is required to be set in the database.
     *
     * @param pPriceListAssocId
     *  int to use to update the field.
     */
    public void setPriceListAssocId(int pPriceListAssocId){
        this.mPriceListAssocId = pPriceListAssocId;
        setDirty(true);
    }
    /**
     * Retrieves the PriceListAssocId field.
     *
     * @return
     *  int containing the PriceListAssocId field.
     */
    public int getPriceListAssocId(){
        return mPriceListAssocId;
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
     * Sets the BusEntityId field. This field is required to be set in the database.
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
     * Sets the PriceListAssocCd field. This field is required to be set in the database.
     *
     * @param pPriceListAssocCd
     *  String to use to update the field.
     */
    public void setPriceListAssocCd(String pPriceListAssocCd){
        this.mPriceListAssocCd = pPriceListAssocCd;
        setDirty(true);
    }
    /**
     * Retrieves the PriceListAssocCd field.
     *
     * @return
     *  String containing the PriceListAssocCd field.
     */
    public String getPriceListAssocCd(){
        return mPriceListAssocCd;
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
