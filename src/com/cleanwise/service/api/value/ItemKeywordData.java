
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ItemKeywordData
 * Description:  This is a ValueObject class wrapping the database table CLW_ITEM_KEYWORD.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.ItemKeywordDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>ItemKeywordData</code> is a ValueObject class wrapping of the database table CLW_ITEM_KEYWORD.
 */
public class ItemKeywordData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 617315211743508973L;
    private int mItemKeywordId;// SQL type:NUMBER, not null
    private int mItemId;// SQL type:NUMBER
    private String mKeyword;// SQL type:VARCHAR2, not null
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public ItemKeywordData ()
    {
        mKeyword = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public ItemKeywordData(int parm1, int parm2, String parm3, Date parm4, String parm5, Date parm6, String parm7)
    {
        mItemKeywordId = parm1;
        mItemId = parm2;
        mKeyword = parm3;
        mAddDate = parm4;
        mAddBy = parm5;
        mModDate = parm6;
        mModBy = parm7;
        
    }

    /**
     * Creates a new ItemKeywordData
     *
     * @return
     *  Newly initialized ItemKeywordData object.
     */
    public static ItemKeywordData createValue ()
    {
        ItemKeywordData valueData = new ItemKeywordData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ItemKeywordData object
     */
    public String toString()
    {
        return "[" + "ItemKeywordId=" + mItemKeywordId + ", ItemId=" + mItemId + ", Keyword=" + mKeyword + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("ItemKeyword");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mItemKeywordId));

        node =  doc.createElement("ItemId");
        node.appendChild(doc.createTextNode(String.valueOf(mItemId)));
        root.appendChild(node);

        node =  doc.createElement("Keyword");
        node.appendChild(doc.createTextNode(String.valueOf(mKeyword)));
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
    * creates a clone of this object, the ItemKeywordId field is not cloned.
    *
    * @return ItemKeywordData object
    */
    public Object clone(){
        ItemKeywordData myClone = new ItemKeywordData();
        
        myClone.mItemId = mItemId;
        
        myClone.mKeyword = mKeyword;
        
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

        if (ItemKeywordDataAccess.ITEM_KEYWORD_ID.equals(pFieldName)) {
            return getItemKeywordId();
        } else if (ItemKeywordDataAccess.ITEM_ID.equals(pFieldName)) {
            return getItemId();
        } else if (ItemKeywordDataAccess.KEYWORD.equals(pFieldName)) {
            return getKeyword();
        } else if (ItemKeywordDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (ItemKeywordDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (ItemKeywordDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (ItemKeywordDataAccess.MOD_BY.equals(pFieldName)) {
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
        return ItemKeywordDataAccess.CLW_ITEM_KEYWORD;
    }

    
    /**
     * Sets the ItemKeywordId field. This field is required to be set in the database.
     *
     * @param pItemKeywordId
     *  int to use to update the field.
     */
    public void setItemKeywordId(int pItemKeywordId){
        this.mItemKeywordId = pItemKeywordId;
        setDirty(true);
    }
    /**
     * Retrieves the ItemKeywordId field.
     *
     * @return
     *  int containing the ItemKeywordId field.
     */
    public int getItemKeywordId(){
        return mItemKeywordId;
    }

    /**
     * Sets the ItemId field.
     *
     * @param pItemId
     *  int to use to update the field.
     */
    public void setItemId(int pItemId){
        this.mItemId = pItemId;
        setDirty(true);
    }
    /**
     * Retrieves the ItemId field.
     *
     * @return
     *  int containing the ItemId field.
     */
    public int getItemId(){
        return mItemId;
    }

    /**
     * Sets the Keyword field. This field is required to be set in the database.
     *
     * @param pKeyword
     *  String to use to update the field.
     */
    public void setKeyword(String pKeyword){
        this.mKeyword = pKeyword;
        setDirty(true);
    }
    /**
     * Retrieves the Keyword field.
     *
     * @return
     *  String containing the Keyword field.
     */
    public String getKeyword(){
        return mKeyword;
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
