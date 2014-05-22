
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ItemMasterAssocData
 * Description:  This is a ValueObject class wrapping the database table CLW_ITEM_MASTER_ASSOC.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.ItemMasterAssocDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>ItemMasterAssocData</code> is a ValueObject class wrapping of the database table CLW_ITEM_MASTER_ASSOC.
 */
public class ItemMasterAssocData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 1L;
    private int mItemMasterAssocId;// SQL type:NUMBER, not null
    private int mParentMasterItemId;// SQL type:NUMBER
    private int mChildMasterItemId;// SQL type:NUMBER
    private String mItemMasterAssocStatusCd;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public ItemMasterAssocData ()
    {
        mItemMasterAssocStatusCd = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public ItemMasterAssocData(int parm1, int parm2, int parm3, String parm4, Date parm5, String parm6, Date parm7, String parm8)
    {
        mItemMasterAssocId = parm1;
        mParentMasterItemId = parm2;
        mChildMasterItemId = parm3;
        mItemMasterAssocStatusCd = parm4;
        mAddDate = parm5;
        mAddBy = parm6;
        mModDate = parm7;
        mModBy = parm8;
        
    }

    /**
     * Creates a new ItemMasterAssocData
     *
     * @return
     *  Newly initialized ItemMasterAssocData object.
     */
    public static ItemMasterAssocData createValue ()
    {
        ItemMasterAssocData valueData = new ItemMasterAssocData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ItemMasterAssocData object
     */
    public String toString()
    {
        return "[" + "ItemMasterAssocId=" + mItemMasterAssocId + ", ParentMasterItemId=" + mParentMasterItemId + ", ChildMasterItemId=" + mChildMasterItemId + ", ItemMasterAssocStatusCd=" + mItemMasterAssocStatusCd + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("ItemMasterAssoc");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mItemMasterAssocId));

        node =  doc.createElement("ParentMasterItemId");
        node.appendChild(doc.createTextNode(String.valueOf(mParentMasterItemId)));
        root.appendChild(node);

        node =  doc.createElement("ChildMasterItemId");
        node.appendChild(doc.createTextNode(String.valueOf(mChildMasterItemId)));
        root.appendChild(node);

        node =  doc.createElement("ItemMasterAssocStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mItemMasterAssocStatusCd)));
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
    * creates a clone of this object, the ItemMasterAssocId field is not cloned.
    *
    * @return ItemMasterAssocData object
    */
    public Object clone(){
        ItemMasterAssocData myClone = new ItemMasterAssocData();
        
        myClone.mParentMasterItemId = mParentMasterItemId;
        
        myClone.mChildMasterItemId = mChildMasterItemId;
        
        myClone.mItemMasterAssocStatusCd = mItemMasterAssocStatusCd;
        
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

        if (ItemMasterAssocDataAccess.ITEM_MASTER_ASSOC_ID.equals(pFieldName)) {
            return getItemMasterAssocId();
        } else if (ItemMasterAssocDataAccess.PARENT_MASTER_ITEM_ID.equals(pFieldName)) {
            return getParentMasterItemId();
        } else if (ItemMasterAssocDataAccess.CHILD_MASTER_ITEM_ID.equals(pFieldName)) {
            return getChildMasterItemId();
        } else if (ItemMasterAssocDataAccess.ITEM_MASTER_ASSOC_STATUS_CD.equals(pFieldName)) {
            return getItemMasterAssocStatusCd();
        } else if (ItemMasterAssocDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (ItemMasterAssocDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (ItemMasterAssocDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (ItemMasterAssocDataAccess.MOD_BY.equals(pFieldName)) {
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
        return ItemMasterAssocDataAccess.CLW_ITEM_MASTER_ASSOC;
    }

    
    /**
     * Sets the ItemMasterAssocId field. This field is required to be set in the database.
     *
     * @param pItemMasterAssocId
     *  int to use to update the field.
     */
    public void setItemMasterAssocId(int pItemMasterAssocId){
        this.mItemMasterAssocId = pItemMasterAssocId;
        setDirty(true);
    }
    /**
     * Retrieves the ItemMasterAssocId field.
     *
     * @return
     *  int containing the ItemMasterAssocId field.
     */
    public int getItemMasterAssocId(){
        return mItemMasterAssocId;
    }

    /**
     * Sets the ParentMasterItemId field.
     *
     * @param pParentMasterItemId
     *  int to use to update the field.
     */
    public void setParentMasterItemId(int pParentMasterItemId){
        this.mParentMasterItemId = pParentMasterItemId;
        setDirty(true);
    }
    /**
     * Retrieves the ParentMasterItemId field.
     *
     * @return
     *  int containing the ParentMasterItemId field.
     */
    public int getParentMasterItemId(){
        return mParentMasterItemId;
    }

    /**
     * Sets the ChildMasterItemId field.
     *
     * @param pChildMasterItemId
     *  int to use to update the field.
     */
    public void setChildMasterItemId(int pChildMasterItemId){
        this.mChildMasterItemId = pChildMasterItemId;
        setDirty(true);
    }
    /**
     * Retrieves the ChildMasterItemId field.
     *
     * @return
     *  int containing the ChildMasterItemId field.
     */
    public int getChildMasterItemId(){
        return mChildMasterItemId;
    }

    /**
     * Sets the ItemMasterAssocStatusCd field.
     *
     * @param pItemMasterAssocStatusCd
     *  String to use to update the field.
     */
    public void setItemMasterAssocStatusCd(String pItemMasterAssocStatusCd){
        this.mItemMasterAssocStatusCd = pItemMasterAssocStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the ItemMasterAssocStatusCd field.
     *
     * @return
     *  String containing the ItemMasterAssocStatusCd field.
     */
    public String getItemMasterAssocStatusCd(){
        return mItemMasterAssocStatusCd;
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
