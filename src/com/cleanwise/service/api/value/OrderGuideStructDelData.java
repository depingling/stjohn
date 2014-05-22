
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        OrderGuideStructDelData
 * Description:  This is a ValueObject class wrapping the database table CLW_ORDER_GUIDE_STRUCT_DEL.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.OrderGuideStructDelDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>OrderGuideStructDelData</code> is a ValueObject class wrapping of the database table CLW_ORDER_GUIDE_STRUCT_DEL.
 */
public class OrderGuideStructDelData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 635278877518234768L;
    private int mOrderGuideStructDelId;// SQL type:NUMBER, not null
    private int mOrderGuideId;// SQL type:NUMBER
    private int mItemId;// SQL type:NUMBER
    private int mCategoryItemId;// SQL type:NUMBER
    private int mQuantity;// SQL type:NUMBER
    private String mComments;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public OrderGuideStructDelData ()
    {
        mComments = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public OrderGuideStructDelData(int parm1, int parm2, int parm3, int parm4, int parm5, String parm6, Date parm7, String parm8, Date parm9, String parm10)
    {
        mOrderGuideStructDelId = parm1;
        mOrderGuideId = parm2;
        mItemId = parm3;
        mCategoryItemId = parm4;
        mQuantity = parm5;
        mComments = parm6;
        mAddDate = parm7;
        mAddBy = parm8;
        mModDate = parm9;
        mModBy = parm10;
        
    }

    /**
     * Creates a new OrderGuideStructDelData
     *
     * @return
     *  Newly initialized OrderGuideStructDelData object.
     */
    public static OrderGuideStructDelData createValue ()
    {
        OrderGuideStructDelData valueData = new OrderGuideStructDelData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this OrderGuideStructDelData object
     */
    public String toString()
    {
        return "[" + "OrderGuideStructDelId=" + mOrderGuideStructDelId + ", OrderGuideId=" + mOrderGuideId + ", ItemId=" + mItemId + ", CategoryItemId=" + mCategoryItemId + ", Quantity=" + mQuantity + ", Comments=" + mComments + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("OrderGuideStructDel");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mOrderGuideStructDelId));

        node =  doc.createElement("OrderGuideId");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderGuideId)));
        root.appendChild(node);

        node =  doc.createElement("ItemId");
        node.appendChild(doc.createTextNode(String.valueOf(mItemId)));
        root.appendChild(node);

        node =  doc.createElement("CategoryItemId");
        node.appendChild(doc.createTextNode(String.valueOf(mCategoryItemId)));
        root.appendChild(node);

        node =  doc.createElement("Quantity");
        node.appendChild(doc.createTextNode(String.valueOf(mQuantity)));
        root.appendChild(node);

        node =  doc.createElement("Comments");
        node.appendChild(doc.createTextNode(String.valueOf(mComments)));
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
    * creates a clone of this object, the OrderGuideStructDelId field is not cloned.
    *
    * @return OrderGuideStructDelData object
    */
    public Object clone(){
        OrderGuideStructDelData myClone = new OrderGuideStructDelData();
        
        myClone.mOrderGuideId = mOrderGuideId;
        
        myClone.mItemId = mItemId;
        
        myClone.mCategoryItemId = mCategoryItemId;
        
        myClone.mQuantity = mQuantity;
        
        myClone.mComments = mComments;
        
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

        if (OrderGuideStructDelDataAccess.ORDER_GUIDE_STRUCT_DEL_ID.equals(pFieldName)) {
            return getOrderGuideStructDelId();
        } else if (OrderGuideStructDelDataAccess.ORDER_GUIDE_ID.equals(pFieldName)) {
            return getOrderGuideId();
        } else if (OrderGuideStructDelDataAccess.ITEM_ID.equals(pFieldName)) {
            return getItemId();
        } else if (OrderGuideStructDelDataAccess.CATEGORY_ITEM_ID.equals(pFieldName)) {
            return getCategoryItemId();
        } else if (OrderGuideStructDelDataAccess.QUANTITY.equals(pFieldName)) {
            return getQuantity();
        } else if (OrderGuideStructDelDataAccess.COMMENTS.equals(pFieldName)) {
            return getComments();
        } else if (OrderGuideStructDelDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (OrderGuideStructDelDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (OrderGuideStructDelDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (OrderGuideStructDelDataAccess.MOD_BY.equals(pFieldName)) {
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
        return OrderGuideStructDelDataAccess.CLW_ORDER_GUIDE_STRUCT_DEL;
    }

    
    /**
     * Sets the OrderGuideStructDelId field. This field is required to be set in the database.
     *
     * @param pOrderGuideStructDelId
     *  int to use to update the field.
     */
    public void setOrderGuideStructDelId(int pOrderGuideStructDelId){
        this.mOrderGuideStructDelId = pOrderGuideStructDelId;
        setDirty(true);
    }
    /**
     * Retrieves the OrderGuideStructDelId field.
     *
     * @return
     *  int containing the OrderGuideStructDelId field.
     */
    public int getOrderGuideStructDelId(){
        return mOrderGuideStructDelId;
    }

    /**
     * Sets the OrderGuideId field.
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
     * Sets the Quantity field.
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
     * Sets the Comments field.
     *
     * @param pComments
     *  String to use to update the field.
     */
    public void setComments(String pComments){
        this.mComments = pComments;
        setDirty(true);
    }
    /**
     * Retrieves the Comments field.
     *
     * @return
     *  String containing the Comments field.
     */
    public String getComments(){
        return mComments;
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
