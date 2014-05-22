
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ItemMappingAssocData
 * Description:  This is a ValueObject class wrapping the database table CLW_ITEM_MAPPING_ASSOC.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.ItemMappingAssocDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>ItemMappingAssocData</code> is a ValueObject class wrapping of the database table CLW_ITEM_MAPPING_ASSOC.
 */
public class ItemMappingAssocData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -6705698374437434435L;
    private int mItemMappingAssocId;// SQL type:NUMBER, not null
    private int mItemMapping1Id;// SQL type:NUMBER
    private int mItemMapping2Id;// SQL type:NUMBER
    private String mItemMappingAssocCd;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public ItemMappingAssocData ()
    {
        mItemMappingAssocCd = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public ItemMappingAssocData(int parm1, int parm2, int parm3, String parm4, Date parm5, String parm6, Date parm7, String parm8)
    {
        mItemMappingAssocId = parm1;
        mItemMapping1Id = parm2;
        mItemMapping2Id = parm3;
        mItemMappingAssocCd = parm4;
        mAddDate = parm5;
        mAddBy = parm6;
        mModDate = parm7;
        mModBy = parm8;
        
    }

    /**
     * Creates a new ItemMappingAssocData
     *
     * @return
     *  Newly initialized ItemMappingAssocData object.
     */
    public static ItemMappingAssocData createValue ()
    {
        ItemMappingAssocData valueData = new ItemMappingAssocData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ItemMappingAssocData object
     */
    public String toString()
    {
        return "[" + "ItemMappingAssocId=" + mItemMappingAssocId + ", ItemMapping1Id=" + mItemMapping1Id + ", ItemMapping2Id=" + mItemMapping2Id + ", ItemMappingAssocCd=" + mItemMappingAssocCd + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("ItemMappingAssoc");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mItemMappingAssocId));

        node =  doc.createElement("ItemMapping1Id");
        node.appendChild(doc.createTextNode(String.valueOf(mItemMapping1Id)));
        root.appendChild(node);

        node =  doc.createElement("ItemMapping2Id");
        node.appendChild(doc.createTextNode(String.valueOf(mItemMapping2Id)));
        root.appendChild(node);

        node =  doc.createElement("ItemMappingAssocCd");
        node.appendChild(doc.createTextNode(String.valueOf(mItemMappingAssocCd)));
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
    * creates a clone of this object, the ItemMappingAssocId field is not cloned.
    *
    * @return ItemMappingAssocData object
    */
    public Object clone(){
        ItemMappingAssocData myClone = new ItemMappingAssocData();
        
        myClone.mItemMapping1Id = mItemMapping1Id;
        
        myClone.mItemMapping2Id = mItemMapping2Id;
        
        myClone.mItemMappingAssocCd = mItemMappingAssocCd;
        
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

        if (ItemMappingAssocDataAccess.ITEM_MAPPING_ASSOC_ID.equals(pFieldName)) {
            return getItemMappingAssocId();
        } else if (ItemMappingAssocDataAccess.ITEM_MAPPING1_ID.equals(pFieldName)) {
            return getItemMapping1Id();
        } else if (ItemMappingAssocDataAccess.ITEM_MAPPING2_ID.equals(pFieldName)) {
            return getItemMapping2Id();
        } else if (ItemMappingAssocDataAccess.ITEM_MAPPING_ASSOC_CD.equals(pFieldName)) {
            return getItemMappingAssocCd();
        } else if (ItemMappingAssocDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (ItemMappingAssocDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (ItemMappingAssocDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (ItemMappingAssocDataAccess.MOD_BY.equals(pFieldName)) {
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
        return ItemMappingAssocDataAccess.CLW_ITEM_MAPPING_ASSOC;
    }

    
    /**
     * Sets the ItemMappingAssocId field. This field is required to be set in the database.
     *
     * @param pItemMappingAssocId
     *  int to use to update the field.
     */
    public void setItemMappingAssocId(int pItemMappingAssocId){
        this.mItemMappingAssocId = pItemMappingAssocId;
        setDirty(true);
    }
    /**
     * Retrieves the ItemMappingAssocId field.
     *
     * @return
     *  int containing the ItemMappingAssocId field.
     */
    public int getItemMappingAssocId(){
        return mItemMappingAssocId;
    }

    /**
     * Sets the ItemMapping1Id field.
     *
     * @param pItemMapping1Id
     *  int to use to update the field.
     */
    public void setItemMapping1Id(int pItemMapping1Id){
        this.mItemMapping1Id = pItemMapping1Id;
        setDirty(true);
    }
    /**
     * Retrieves the ItemMapping1Id field.
     *
     * @return
     *  int containing the ItemMapping1Id field.
     */
    public int getItemMapping1Id(){
        return mItemMapping1Id;
    }

    /**
     * Sets the ItemMapping2Id field.
     *
     * @param pItemMapping2Id
     *  int to use to update the field.
     */
    public void setItemMapping2Id(int pItemMapping2Id){
        this.mItemMapping2Id = pItemMapping2Id;
        setDirty(true);
    }
    /**
     * Retrieves the ItemMapping2Id field.
     *
     * @return
     *  int containing the ItemMapping2Id field.
     */
    public int getItemMapping2Id(){
        return mItemMapping2Id;
    }

    /**
     * Sets the ItemMappingAssocCd field.
     *
     * @param pItemMappingAssocCd
     *  String to use to update the field.
     */
    public void setItemMappingAssocCd(String pItemMappingAssocCd){
        this.mItemMappingAssocCd = pItemMappingAssocCd;
        setDirty(true);
    }
    /**
     * Retrieves the ItemMappingAssocCd field.
     *
     * @return
     *  String containing the ItemMappingAssocCd field.
     */
    public String getItemMappingAssocCd(){
        return mItemMappingAssocCd;
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
