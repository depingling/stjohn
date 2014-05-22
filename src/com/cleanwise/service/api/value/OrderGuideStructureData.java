
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        OrderGuideStructureData
 * Description:  This is a ValueObject class wrapping the database table CLW_ORDER_GUIDE_STRUCTURE.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.OrderGuideStructureDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>OrderGuideStructureData</code> is a ValueObject class wrapping of the database table CLW_ORDER_GUIDE_STRUCTURE.
 */
public class OrderGuideStructureData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 8214146834639927019L;
    private int mOrderGuideStructureId;// SQL type:NUMBER, not null
    private int mOrderGuideId;// SQL type:NUMBER, not null
    private int mItemId;// SQL type:NUMBER, not null
    private int mCategoryItemId;// SQL type:NUMBER
    private String mCustCategory;// SQL type:VARCHAR2
    private int mQuantity;// SQL type:NUMBER, not null
    private int mSortOrder;// SQL type:NUMBER
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public OrderGuideStructureData ()
    {
        mCustCategory = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public OrderGuideStructureData(int parm1, int parm2, int parm3, int parm4, String parm5, int parm6, int parm7, Date parm8, String parm9, Date parm10, String parm11)
    {
        mOrderGuideStructureId = parm1;
        mOrderGuideId = parm2;
        mItemId = parm3;
        mCategoryItemId = parm4;
        mCustCategory = parm5;
        mQuantity = parm6;
        mSortOrder = parm7;
        mAddDate = parm8;
        mAddBy = parm9;
        mModDate = parm10;
        mModBy = parm11;
        
    }

    /**
     * Creates a new OrderGuideStructureData
     *
     * @return
     *  Newly initialized OrderGuideStructureData object.
     */
    public static OrderGuideStructureData createValue ()
    {
        OrderGuideStructureData valueData = new OrderGuideStructureData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this OrderGuideStructureData object
     */
    public String toString()
    {
        return "[" + "OrderGuideStructureId=" + mOrderGuideStructureId + ", OrderGuideId=" + mOrderGuideId + ", ItemId=" + mItemId + ", CategoryItemId=" + mCategoryItemId + ", CustCategory=" + mCustCategory + ", Quantity=" + mQuantity + ", SortOrder=" + mSortOrder + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("OrderGuideStructure");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mOrderGuideStructureId));

        node =  doc.createElement("OrderGuideId");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderGuideId)));
        root.appendChild(node);

        node =  doc.createElement("ItemId");
        node.appendChild(doc.createTextNode(String.valueOf(mItemId)));
        root.appendChild(node);

        node =  doc.createElement("CategoryItemId");
        node.appendChild(doc.createTextNode(String.valueOf(mCategoryItemId)));
        root.appendChild(node);

        node =  doc.createElement("CustCategory");
        node.appendChild(doc.createTextNode(String.valueOf(mCustCategory)));
        root.appendChild(node);

        node =  doc.createElement("Quantity");
        node.appendChild(doc.createTextNode(String.valueOf(mQuantity)));
        root.appendChild(node);

        node =  doc.createElement("SortOrder");
        node.appendChild(doc.createTextNode(String.valueOf(mSortOrder)));
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
    * creates a clone of this object, the OrderGuideStructureId field is not cloned.
    *
    * @return OrderGuideStructureData object
    */
    public Object clone(){
        OrderGuideStructureData myClone = new OrderGuideStructureData();
        
        myClone.mOrderGuideId = mOrderGuideId;
        
        myClone.mItemId = mItemId;
        
        myClone.mCategoryItemId = mCategoryItemId;
        
        myClone.mCustCategory = mCustCategory;
        
        myClone.mQuantity = mQuantity;
        
        myClone.mSortOrder = mSortOrder;
        
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

        if (OrderGuideStructureDataAccess.ORDER_GUIDE_STRUCTURE_ID.equals(pFieldName)) {
            return getOrderGuideStructureId();
        } else if (OrderGuideStructureDataAccess.ORDER_GUIDE_ID.equals(pFieldName)) {
            return getOrderGuideId();
        } else if (OrderGuideStructureDataAccess.ITEM_ID.equals(pFieldName)) {
            return getItemId();
        } else if (OrderGuideStructureDataAccess.CATEGORY_ITEM_ID.equals(pFieldName)) {
            return getCategoryItemId();
        } else if (OrderGuideStructureDataAccess.CUST_CATEGORY.equals(pFieldName)) {
            return getCustCategory();
        } else if (OrderGuideStructureDataAccess.QUANTITY.equals(pFieldName)) {
            return getQuantity();
        } else if (OrderGuideStructureDataAccess.SORT_ORDER.equals(pFieldName)) {
            return getSortOrder();
        } else if (OrderGuideStructureDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (OrderGuideStructureDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (OrderGuideStructureDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (OrderGuideStructureDataAccess.MOD_BY.equals(pFieldName)) {
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
        return OrderGuideStructureDataAccess.CLW_ORDER_GUIDE_STRUCTURE;
    }

    
    /**
     * Sets the OrderGuideStructureId field. This field is required to be set in the database.
     *
     * @param pOrderGuideStructureId
     *  int to use to update the field.
     */
    public void setOrderGuideStructureId(int pOrderGuideStructureId){
        this.mOrderGuideStructureId = pOrderGuideStructureId;
        setDirty(true);
    }
    /**
     * Retrieves the OrderGuideStructureId field.
     *
     * @return
     *  int containing the OrderGuideStructureId field.
     */
    public int getOrderGuideStructureId(){
        return mOrderGuideStructureId;
    }

    /**
     * Sets the OrderGuideId field. This field is required to be set in the database.
     *
     * @param pOrderGuideId
     *  int to use to update the field.
     */
    public void setOrderGuideId(int pOrderGuideId){
        this.mOrderGuideId = pOrderGuideId;
        setDirty(true);
    }
    /**
     * Retrieves the OrderGuideId field.
     *
     * @return
     *  int containing the OrderGuideId field.
     */
    public int getOrderGuideId(){
        return mOrderGuideId;
    }

    /**
     * Sets the ItemId field. This field is required to be set in the database.
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
     * Sets the CategoryItemId field.
     *
     * @param pCategoryItemId
     *  int to use to update the field.
     */
    public void setCategoryItemId(int pCategoryItemId){
        this.mCategoryItemId = pCategoryItemId;
        setDirty(true);
    }
    /**
     * Retrieves the CategoryItemId field.
     *
     * @return
     *  int containing the CategoryItemId field.
     */
    public int getCategoryItemId(){
        return mCategoryItemId;
    }

    /**
     * Sets the CustCategory field.
     *
     * @param pCustCategory
     *  String to use to update the field.
     */
    public void setCustCategory(String pCustCategory){
        this.mCustCategory = pCustCategory;
        setDirty(true);
    }
    /**
     * Retrieves the CustCategory field.
     *
     * @return
     *  String containing the CustCategory field.
     */
    public String getCustCategory(){
        return mCustCategory;
    }

    /**
     * Sets the Quantity field. This field is required to be set in the database.
     *
     * @param pQuantity
     *  int to use to update the field.
     */
    public void setQuantity(int pQuantity){
        this.mQuantity = pQuantity;
        setDirty(true);
    }
    /**
     * Retrieves the Quantity field.
     *
     * @return
     *  int containing the Quantity field.
     */
    public int getQuantity(){
        return mQuantity;
    }

    /**
     * Sets the SortOrder field.
     *
     * @param pSortOrder
     *  int to use to update the field.
     */
    public void setSortOrder(int pSortOrder){
        this.mSortOrder = pSortOrder;
        setDirty(true);
    }
    /**
     * Retrieves the SortOrder field.
     *
     * @return
     *  int containing the SortOrder field.
     */
    public int getSortOrder(){
        return mSortOrder;
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
