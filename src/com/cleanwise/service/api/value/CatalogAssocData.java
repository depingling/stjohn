
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        CatalogAssocData
 * Description:  This is a ValueObject class wrapping the database table CLW_CATALOG_ASSOC.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.CatalogAssocDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>CatalogAssocData</code> is a ValueObject class wrapping of the database table CLW_CATALOG_ASSOC.
 */
public class CatalogAssocData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 1184170467873775072L;
    private int mCatalogAssocId;// SQL type:NUMBER, not null
    private int mCatalogId;// SQL type:NUMBER, not null
    private int mBusEntityId;// SQL type:NUMBER, not null
    private String mCatalogAssocCd;// SQL type:VARCHAR2, not null
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private int mNewCatalogId;// SQL type:NUMBER

    /**
     * Constructor.
     */
    public CatalogAssocData ()
    {
        mCatalogAssocCd = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public CatalogAssocData(int parm1, int parm2, int parm3, String parm4, Date parm5, String parm6, Date parm7, String parm8, int parm9)
    {
        mCatalogAssocId = parm1;
        mCatalogId = parm2;
        mBusEntityId = parm3;
        mCatalogAssocCd = parm4;
        mAddDate = parm5;
        mAddBy = parm6;
        mModDate = parm7;
        mModBy = parm8;
        mNewCatalogId = parm9;
        
    }

    /**
     * Creates a new CatalogAssocData
     *
     * @return
     *  Newly initialized CatalogAssocData object.
     */
    public static CatalogAssocData createValue ()
    {
        CatalogAssocData valueData = new CatalogAssocData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this CatalogAssocData object
     */
    public String toString()
    {
        return "[" + "CatalogAssocId=" + mCatalogAssocId + ", CatalogId=" + mCatalogId + ", BusEntityId=" + mBusEntityId + ", CatalogAssocCd=" + mCatalogAssocCd + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", NewCatalogId=" + mNewCatalogId + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("CatalogAssoc");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mCatalogAssocId));

        node =  doc.createElement("CatalogId");
        node.appendChild(doc.createTextNode(String.valueOf(mCatalogId)));
        root.appendChild(node);

        node =  doc.createElement("BusEntityId");
        node.appendChild(doc.createTextNode(String.valueOf(mBusEntityId)));
        root.appendChild(node);

        node =  doc.createElement("CatalogAssocCd");
        node.appendChild(doc.createTextNode(String.valueOf(mCatalogAssocCd)));
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

        node =  doc.createElement("NewCatalogId");
        node.appendChild(doc.createTextNode(String.valueOf(mNewCatalogId)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the CatalogAssocId field is not cloned.
    *
    * @return CatalogAssocData object
    */
    public Object clone(){
        CatalogAssocData myClone = new CatalogAssocData();
        
        myClone.mCatalogId = mCatalogId;
        
        myClone.mBusEntityId = mBusEntityId;
        
        myClone.mCatalogAssocCd = mCatalogAssocCd;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        myClone.mNewCatalogId = mNewCatalogId;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (CatalogAssocDataAccess.CATALOG_ASSOC_ID.equals(pFieldName)) {
            return getCatalogAssocId();
        } else if (CatalogAssocDataAccess.CATALOG_ID.equals(pFieldName)) {
            return getCatalogId();
        } else if (CatalogAssocDataAccess.BUS_ENTITY_ID.equals(pFieldName)) {
            return getBusEntityId();
        } else if (CatalogAssocDataAccess.CATALOG_ASSOC_CD.equals(pFieldName)) {
            return getCatalogAssocCd();
        } else if (CatalogAssocDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (CatalogAssocDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (CatalogAssocDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (CatalogAssocDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (CatalogAssocDataAccess.NEW_CATALOG_ID.equals(pFieldName)) {
            return getNewCatalogId();
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
        return CatalogAssocDataAccess.CLW_CATALOG_ASSOC;
    }

    
    /**
     * Sets the CatalogAssocId field. This field is required to be set in the database.
     *
     * @param pCatalogAssocId
     *  int to use to update the field.
     */
    public void setCatalogAssocId(int pCatalogAssocId){
        this.mCatalogAssocId = pCatalogAssocId;
        setDirty(true);
    }
    /**
     * Retrieves the CatalogAssocId field.
     *
     * @return
     *  int containing the CatalogAssocId field.
     */
    public int getCatalogAssocId(){
        return mCatalogAssocId;
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
     * Sets the CatalogAssocCd field. This field is required to be set in the database.
     *
     * @param pCatalogAssocCd
     *  String to use to update the field.
     */
    public void setCatalogAssocCd(String pCatalogAssocCd){
        this.mCatalogAssocCd = pCatalogAssocCd;
        setDirty(true);
    }
    /**
     * Retrieves the CatalogAssocCd field.
     *
     * @return
     *  String containing the CatalogAssocCd field.
     */
    public String getCatalogAssocCd(){
        return mCatalogAssocCd;
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
     * Sets the NewCatalogId field.
     *
     * @param pNewCatalogId
     *  int to use to update the field.
     */
    public void setNewCatalogId(int pNewCatalogId){
        this.mNewCatalogId = pNewCatalogId;
        setDirty(true);
    }
    /**
     * Retrieves the NewCatalogId field.
     *
     * @return
     *  int containing the NewCatalogId field.
     */
    public int getNewCatalogId(){
        return mNewCatalogId;
    }


}
