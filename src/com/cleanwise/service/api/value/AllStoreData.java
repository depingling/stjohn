
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        AllStoreData
 * Description:  This is a ValueObject class wrapping the database table ESW_ALL_STORE.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.AllStoreDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>AllStoreData</code> is a ValueObject class wrapping of the database table ESW_ALL_STORE.
 */
public class AllStoreData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 8116864918440830884L;
    private int mAllStoreId;// SQL type:NUMBER, not null
    private int mStoreId;// SQL type:NUMBER, not null
    private String mStoreName;// SQL type:VARCHAR2
    private String mDomain;// SQL type:VARCHAR2
    private String mDatasource;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public AllStoreData ()
    {
        mStoreName = "";
        mDomain = "";
        mDatasource = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public AllStoreData(int parm1, int parm2, String parm3, String parm4, String parm5, Date parm6, String parm7, Date parm8, String parm9)
    {
        mAllStoreId = parm1;
        mStoreId = parm2;
        mStoreName = parm3;
        mDomain = parm4;
        mDatasource = parm5;
        mAddDate = parm6;
        mAddBy = parm7;
        mModDate = parm8;
        mModBy = parm9;
        
    }

    /**
     * Creates a new AllStoreData
     *
     * @return
     *  Newly initialized AllStoreData object.
     */
    public static AllStoreData createValue ()
    {
        AllStoreData valueData = new AllStoreData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this AllStoreData object
     */
    public String toString()
    {
        return "[" + "AllStoreId=" + mAllStoreId + ", StoreId=" + mStoreId + ", StoreName=" + mStoreName + ", Domain=" + mDomain + ", Datasource=" + mDatasource + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("AllStore");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mAllStoreId));

        node =  doc.createElement("StoreId");
        node.appendChild(doc.createTextNode(String.valueOf(mStoreId)));
        root.appendChild(node);

        node =  doc.createElement("StoreName");
        node.appendChild(doc.createTextNode(String.valueOf(mStoreName)));
        root.appendChild(node);

        node =  doc.createElement("Domain");
        node.appendChild(doc.createTextNode(String.valueOf(mDomain)));
        root.appendChild(node);

        node =  doc.createElement("Datasource");
        node.appendChild(doc.createTextNode(String.valueOf(mDatasource)));
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
    * creates a clone of this object, the AllStoreId field is not cloned.
    *
    * @return AllStoreData object
    */
    public Object clone(){
        AllStoreData myClone = new AllStoreData();
        
        myClone.mStoreId = mStoreId;
        
        myClone.mStoreName = mStoreName;
        
        myClone.mDomain = mDomain;
        
        myClone.mDatasource = mDatasource;
        
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

        if (AllStoreDataAccess.ALL_STORE_ID.equals(pFieldName)) {
            return getAllStoreId();
        } else if (AllStoreDataAccess.STORE_ID.equals(pFieldName)) {
            return getStoreId();
        } else if (AllStoreDataAccess.STORE_NAME.equals(pFieldName)) {
            return getStoreName();
        } else if (AllStoreDataAccess.DOMAIN.equals(pFieldName)) {
            return getDomain();
        } else if (AllStoreDataAccess.DATASOURCE.equals(pFieldName)) {
            return getDatasource();
        } else if (AllStoreDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (AllStoreDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (AllStoreDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (AllStoreDataAccess.MOD_BY.equals(pFieldName)) {
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
        return AllStoreDataAccess.ESW_ALL_STORE;
    }

    
    /**
     * Sets the AllStoreId field. This field is required to be set in the database.
     *
     * @param pAllStoreId
     *  int to use to update the field.
     */
    public void setAllStoreId(int pAllStoreId){
        this.mAllStoreId = pAllStoreId;
        setDirty(true);
    }
    /**
     * Retrieves the AllStoreId field.
     *
     * @return
     *  int containing the AllStoreId field.
     */
    public int getAllStoreId(){
        return mAllStoreId;
    }

    /**
     * Sets the StoreId field. This field is required to be set in the database.
     *
     * @param pStoreId
     *  int to use to update the field.
     */
    public void setStoreId(int pStoreId){
        this.mStoreId = pStoreId;
        setDirty(true);
    }
    /**
     * Retrieves the StoreId field.
     *
     * @return
     *  int containing the StoreId field.
     */
    public int getStoreId(){
        return mStoreId;
    }

    /**
     * Sets the StoreName field.
     *
     * @param pStoreName
     *  String to use to update the field.
     */
    public void setStoreName(String pStoreName){
        this.mStoreName = pStoreName;
        setDirty(true);
    }
    /**
     * Retrieves the StoreName field.
     *
     * @return
     *  String containing the StoreName field.
     */
    public String getStoreName(){
        return mStoreName;
    }

    /**
     * Sets the Domain field.
     *
     * @param pDomain
     *  String to use to update the field.
     */
    public void setDomain(String pDomain){
        this.mDomain = pDomain;
        setDirty(true);
    }
    /**
     * Retrieves the Domain field.
     *
     * @return
     *  String containing the Domain field.
     */
    public String getDomain(){
        return mDomain;
    }

    /**
     * Sets the Datasource field.
     *
     * @param pDatasource
     *  String to use to update the field.
     */
    public void setDatasource(String pDatasource){
        this.mDatasource = pDatasource;
        setDirty(true);
    }
    /**
     * Retrieves the Datasource field.
     *
     * @return
     *  String containing the Datasource field.
     */
    public String getDatasource(){
        return mDatasource;
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
