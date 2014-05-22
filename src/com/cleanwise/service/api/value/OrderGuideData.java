
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        OrderGuideData
 * Description:  This is a ValueObject class wrapping the database table CLW_ORDER_GUIDE.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.OrderGuideDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>OrderGuideData</code> is a ValueObject class wrapping of the database table CLW_ORDER_GUIDE.
 */
public class OrderGuideData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 8407169807722085703L;
    private int mOrderGuideId;// SQL type:NUMBER, not null
    private String mShortDesc;// SQL type:VARCHAR2, not null
    private int mCatalogId;// SQL type:NUMBER
    private int mBusEntityId;// SQL type:NUMBER
    private int mUserId;// SQL type:NUMBER
    private String mOrderGuideTypeCd;// SQL type:VARCHAR2, not null
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private String mOrderBudgetTypeCd;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public OrderGuideData ()
    {
        mShortDesc = "";
        mOrderGuideTypeCd = "";
        mAddBy = "";
        mModBy = "";
        mOrderBudgetTypeCd = "";
    }

    /**
     * Constructor.
     */
    public OrderGuideData(int parm1, String parm2, int parm3, int parm4, int parm5, String parm6, Date parm7, String parm8, Date parm9, String parm10, String parm11)
    {
        mOrderGuideId = parm1;
        mShortDesc = parm2;
        mCatalogId = parm3;
        mBusEntityId = parm4;
        mUserId = parm5;
        mOrderGuideTypeCd = parm6;
        mAddDate = parm7;
        mAddBy = parm8;
        mModDate = parm9;
        mModBy = parm10;
        mOrderBudgetTypeCd = parm11;
        
    }

    /**
     * Creates a new OrderGuideData
     *
     * @return
     *  Newly initialized OrderGuideData object.
     */
    public static OrderGuideData createValue ()
    {
        OrderGuideData valueData = new OrderGuideData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this OrderGuideData object
     */
    public String toString()
    {
        return "[" + "OrderGuideId=" + mOrderGuideId + ", ShortDesc=" + mShortDesc + ", CatalogId=" + mCatalogId + ", BusEntityId=" + mBusEntityId + ", UserId=" + mUserId + ", OrderGuideTypeCd=" + mOrderGuideTypeCd + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", OrderBudgetTypeCd=" + mOrderBudgetTypeCd + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("OrderGuide");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mOrderGuideId));

        node =  doc.createElement("ShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mShortDesc)));
        root.appendChild(node);

        node =  doc.createElement("CatalogId");
        node.appendChild(doc.createTextNode(String.valueOf(mCatalogId)));
        root.appendChild(node);

        node =  doc.createElement("BusEntityId");
        node.appendChild(doc.createTextNode(String.valueOf(mBusEntityId)));
        root.appendChild(node);

        node =  doc.createElement("UserId");
        node.appendChild(doc.createTextNode(String.valueOf(mUserId)));
        root.appendChild(node);

        node =  doc.createElement("OrderGuideTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderGuideTypeCd)));
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

        node =  doc.createElement("OrderBudgetTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderBudgetTypeCd)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the OrderGuideId field is not cloned.
    *
    * @return OrderGuideData object
    */
    public Object clone(){
        OrderGuideData myClone = new OrderGuideData();
        
        myClone.mShortDesc = mShortDesc;
        
        myClone.mCatalogId = mCatalogId;
        
        myClone.mBusEntityId = mBusEntityId;
        
        myClone.mUserId = mUserId;
        
        myClone.mOrderGuideTypeCd = mOrderGuideTypeCd;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        myClone.mOrderBudgetTypeCd = mOrderBudgetTypeCd;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (OrderGuideDataAccess.ORDER_GUIDE_ID.equals(pFieldName)) {
            return getOrderGuideId();
        } else if (OrderGuideDataAccess.SHORT_DESC.equals(pFieldName)) {
            return getShortDesc();
        } else if (OrderGuideDataAccess.CATALOG_ID.equals(pFieldName)) {
            return getCatalogId();
        } else if (OrderGuideDataAccess.BUS_ENTITY_ID.equals(pFieldName)) {
            return getBusEntityId();
        } else if (OrderGuideDataAccess.USER_ID.equals(pFieldName)) {
            return getUserId();
        } else if (OrderGuideDataAccess.ORDER_GUIDE_TYPE_CD.equals(pFieldName)) {
            return getOrderGuideTypeCd();
        } else if (OrderGuideDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (OrderGuideDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (OrderGuideDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (OrderGuideDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (OrderGuideDataAccess.ORDER_BUDGET_TYPE_CD.equals(pFieldName)) {
            return getOrderBudgetTypeCd();
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
        return OrderGuideDataAccess.CLW_ORDER_GUIDE;
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
     * Sets the BusEntityId field.
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
     * Sets the UserId field.
     *
     * @param pUserId
     *  int to use to update the field.
     */
    public void setUserId(int pUserId){
        this.mUserId = pUserId;
        setDirty(true);
    }
    /**
     * Retrieves the UserId field.
     *
     * @return
     *  int containing the UserId field.
     */
    public int getUserId(){
        return mUserId;
    }

    /**
     * Sets the OrderGuideTypeCd field. This field is required to be set in the database.
     *
     * @param pOrderGuideTypeCd
     *  String to use to update the field.
     */
    public void setOrderGuideTypeCd(String pOrderGuideTypeCd){
        this.mOrderGuideTypeCd = pOrderGuideTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the OrderGuideTypeCd field.
     *
     * @return
     *  String containing the OrderGuideTypeCd field.
     */
    public String getOrderGuideTypeCd(){
        return mOrderGuideTypeCd;
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
     * Sets the OrderBudgetTypeCd field.
     *
     * @param pOrderBudgetTypeCd
     *  String to use to update the field.
     */
    public void setOrderBudgetTypeCd(String pOrderBudgetTypeCd){
        this.mOrderBudgetTypeCd = pOrderBudgetTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the OrderBudgetTypeCd field.
     *
     * @return
     *  String containing the OrderBudgetTypeCd field.
     */
    public String getOrderBudgetTypeCd(){
        return mOrderBudgetTypeCd;
    }


}
