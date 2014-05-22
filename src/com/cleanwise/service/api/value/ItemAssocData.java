
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ItemAssocData
 * Description:  This is a ValueObject class wrapping the database table CLW_ITEM_ASSOC.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.ItemAssocDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>ItemAssocData</code> is a ValueObject class wrapping of the database table CLW_ITEM_ASSOC.
 */
public class ItemAssocData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -365985741185830827L;
    private int mItemAssocId;// SQL type:NUMBER, not null
    private int mItem1Id;// SQL type:NUMBER, not null
    private int mItem2Id;// SQL type:NUMBER, not null
    private int mCatalogId;// SQL type:NUMBER
    private String mItemAssocCd;// SQL type:VARCHAR2, not null
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public ItemAssocData ()
    {
        mItemAssocCd = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public ItemAssocData(int parm1, int parm2, int parm3, int parm4, String parm5, Date parm6, String parm7, Date parm8, String parm9)
    {
        mItemAssocId = parm1;
        mItem1Id = parm2;
        mItem2Id = parm3;
        mCatalogId = parm4;
        mItemAssocCd = parm5;
        mAddDate = parm6;
        mAddBy = parm7;
        mModDate = parm8;
        mModBy = parm9;
        
    }

    /**
     * Creates a new ItemAssocData
     *
     * @return
     *  Newly initialized ItemAssocData object.
     */
    public static ItemAssocData createValue ()
    {
        ItemAssocData valueData = new ItemAssocData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ItemAssocData object
     */
    public String toString()
    {
        return "[" + "ItemAssocId=" + mItemAssocId + ", Item1Id=" + mItem1Id + ", Item2Id=" + mItem2Id + ", CatalogId=" + mCatalogId + ", ItemAssocCd=" + mItemAssocCd + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("ItemAssoc");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mItemAssocId));

        node =  doc.createElement("Item1Id");
        node.appendChild(doc.createTextNode(String.valueOf(mItem1Id)));
        root.appendChild(node);

        node =  doc.createElement("Item2Id");
        node.appendChild(doc.createTextNode(String.valueOf(mItem2Id)));
        root.appendChild(node);

        node =  doc.createElement("CatalogId");
        node.appendChild(doc.createTextNode(String.valueOf(mCatalogId)));
        root.appendChild(node);

        node =  doc.createElement("ItemAssocCd");
        node.appendChild(doc.createTextNode(String.valueOf(mItemAssocCd)));
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
    * creates a clone of this object, the ItemAssocId field is not cloned.
    *
    * @return ItemAssocData object
    */
    public Object clone(){
        ItemAssocData myClone = new ItemAssocData();
        
        myClone.mItem1Id = mItem1Id;
        
        myClone.mItem2Id = mItem2Id;
        
        myClone.mCatalogId = mCatalogId;
        
        myClone.mItemAssocCd = mItemAssocCd;
        
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

        if (ItemAssocDataAccess.ITEM_ASSOC_ID.equals(pFieldName)) {
            return getItemAssocId();
        } else if (ItemAssocDataAccess.ITEM1_ID.equals(pFieldName)) {
            return getItem1Id();
        } else if (ItemAssocDataAccess.ITEM2_ID.equals(pFieldName)) {
            return getItem2Id();
        } else if (ItemAssocDataAccess.CATALOG_ID.equals(pFieldName)) {
            return getCatalogId();
        } else if (ItemAssocDataAccess.ITEM_ASSOC_CD.equals(pFieldName)) {
            return getItemAssocCd();
        } else if (ItemAssocDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (ItemAssocDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (ItemAssocDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (ItemAssocDataAccess.MOD_BY.equals(pFieldName)) {
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
        return ItemAssocDataAccess.CLW_ITEM_ASSOC;
    }

    
    /**
     * Sets the ItemAssocId field. This field is required to be set in the database.
     *
     * @param pItemAssocId
     *  int to use to update the field.
     */
    public void setItemAssocId(int pItemAssocId){
        this.mItemAssocId = pItemAssocId;
        setDirty(true);
    }
    /**
     * Retrieves the ItemAssocId field.
     *
     * @return
     *  int containing the ItemAssocId field.
     */
    public int getItemAssocId(){
        return mItemAssocId;
    }

    /**
     * Sets the Item1Id field. This field is required to be set in the database.
     *
     * @param pItem1Id
     *  int to use to update the field.
     */
    public void setItem1Id(int pItem1Id){
        this.mItem1Id = pItem1Id;
        setDirty(true);
    }
    /**
     * Retrieves the Item1Id field.
     *
     * @return
     *  int containing the Item1Id field.
     */
    public int getItem1Id(){
        return mItem1Id;
    }

    /**
     * Sets the Item2Id field. This field is required to be set in the database.
     *
     * @param pItem2Id
     *  int to use to update the field.
     */
    public void setItem2Id(int pItem2Id){
        this.mItem2Id = pItem2Id;
        setDirty(true);
    }
    /**
     * Retrieves the Item2Id field.
     *
     * @return
     *  int containing the Item2Id field.
     */
    public int getItem2Id(){
        return mItem2Id;
    }

    /**
     * Sets the CatalogId field.
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
     * Sets the ItemAssocCd field. This field is required to be set in the database.
     *
     * @param pItemAssocCd
     *  String to use to update the field.
     */
    public void setItemAssocCd(String pItemAssocCd){
        this.mItemAssocCd = pItemAssocCd;
        setDirty(true);
    }
    /**
     * Retrieves the ItemAssocCd field.
     *
     * @return
     *  String containing the ItemAssocCd field.
     */
    public String getItemAssocCd(){
        return mItemAssocCd;
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
