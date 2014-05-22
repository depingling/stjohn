
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        InventoryOrderLogData
 * Description:  This is a ValueObject class wrapping the database table CLW_INVENTORY_ORDER_LOG.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.InventoryOrderLogDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>InventoryOrderLogData</code> is a ValueObject class wrapping of the database table CLW_INVENTORY_ORDER_LOG.
 */
public class InventoryOrderLogData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -7273214897748424836L;
    private int mInventoryOrderLogId;// SQL type:NUMBER, not null
    private int mSiteId;// SQL type:NUMBER, not null
    private Date mOrderCutoffDate;// SQL type:DATE, not null
    private int mOrderId;// SQL type:NUMBER
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private Date mOrderDeliveryDate;// SQL type:DATE

    /**
     * Constructor.
     */
    public InventoryOrderLogData ()
    {
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public InventoryOrderLogData(int parm1, int parm2, Date parm3, int parm4, Date parm5, String parm6, Date parm7, String parm8, Date parm9)
    {
        mInventoryOrderLogId = parm1;
        mSiteId = parm2;
        mOrderCutoffDate = parm3;
        mOrderId = parm4;
        mAddDate = parm5;
        mAddBy = parm6;
        mModDate = parm7;
        mModBy = parm8;
        mOrderDeliveryDate = parm9;
        
    }

    /**
     * Creates a new InventoryOrderLogData
     *
     * @return
     *  Newly initialized InventoryOrderLogData object.
     */
    public static InventoryOrderLogData createValue ()
    {
        InventoryOrderLogData valueData = new InventoryOrderLogData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this InventoryOrderLogData object
     */
    public String toString()
    {
        return "[" + "InventoryOrderLogId=" + mInventoryOrderLogId + ", SiteId=" + mSiteId + ", OrderCutoffDate=" + mOrderCutoffDate + ", OrderId=" + mOrderId + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", OrderDeliveryDate=" + mOrderDeliveryDate + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("InventoryOrderLog");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mInventoryOrderLogId));

        node =  doc.createElement("SiteId");
        node.appendChild(doc.createTextNode(String.valueOf(mSiteId)));
        root.appendChild(node);

        node =  doc.createElement("OrderCutoffDate");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderCutoffDate)));
        root.appendChild(node);

        node =  doc.createElement("OrderId");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderId)));
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

        node =  doc.createElement("OrderDeliveryDate");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderDeliveryDate)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the InventoryOrderLogId field is not cloned.
    *
    * @return InventoryOrderLogData object
    */
    public Object clone(){
        InventoryOrderLogData myClone = new InventoryOrderLogData();
        
        myClone.mSiteId = mSiteId;
        
        if(mOrderCutoffDate != null){
                myClone.mOrderCutoffDate = (Date) mOrderCutoffDate.clone();
        }
        
        myClone.mOrderId = mOrderId;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        if(mOrderDeliveryDate != null){
                myClone.mOrderDeliveryDate = (Date) mOrderDeliveryDate.clone();
        }
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (InventoryOrderLogDataAccess.INVENTORY_ORDER_LOG_ID.equals(pFieldName)) {
            return getInventoryOrderLogId();
        } else if (InventoryOrderLogDataAccess.SITE_ID.equals(pFieldName)) {
            return getSiteId();
        } else if (InventoryOrderLogDataAccess.ORDER_CUTOFF_DATE.equals(pFieldName)) {
            return getOrderCutoffDate();
        } else if (InventoryOrderLogDataAccess.ORDER_ID.equals(pFieldName)) {
            return getOrderId();
        } else if (InventoryOrderLogDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (InventoryOrderLogDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (InventoryOrderLogDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (InventoryOrderLogDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (InventoryOrderLogDataAccess.ORDER_DELIVERY_DATE.equals(pFieldName)) {
            return getOrderDeliveryDate();
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
        return InventoryOrderLogDataAccess.CLW_INVENTORY_ORDER_LOG;
    }

    
    /**
     * Sets the InventoryOrderLogId field. This field is required to be set in the database.
     *
     * @param pInventoryOrderLogId
     *  int to use to update the field.
     */
    public void setInventoryOrderLogId(int pInventoryOrderLogId){
        this.mInventoryOrderLogId = pInventoryOrderLogId;
        setDirty(true);
    }
    /**
     * Retrieves the InventoryOrderLogId field.
     *
     * @return
     *  int containing the InventoryOrderLogId field.
     */
    public int getInventoryOrderLogId(){
        return mInventoryOrderLogId;
    }

    /**
     * Sets the SiteId field. This field is required to be set in the database.
     *
     * @param pSiteId
     *  int to use to update the field.
     */
    public void setSiteId(int pSiteId){
        this.mSiteId = pSiteId;
        setDirty(true);
    }
    /**
     * Retrieves the SiteId field.
     *
     * @return
     *  int containing the SiteId field.
     */
    public int getSiteId(){
        return mSiteId;
    }

    /**
     * Sets the OrderCutoffDate field. This field is required to be set in the database.
     *
     * @param pOrderCutoffDate
     *  Date to use to update the field.
     */
    public void setOrderCutoffDate(Date pOrderCutoffDate){
        this.mOrderCutoffDate = pOrderCutoffDate;
        setDirty(true);
    }
    /**
     * Retrieves the OrderCutoffDate field.
     *
     * @return
     *  Date containing the OrderCutoffDate field.
     */
    public Date getOrderCutoffDate(){
        return mOrderCutoffDate;
    }

    /**
     * Sets the OrderId field.
     *
     * @param pOrderId
     *  int to use to update the field.
     */
    public void setOrderId(int pOrderId){
        this.mOrderId = pOrderId;
        setDirty(true);
    }
    /**
     * Retrieves the OrderId field.
     *
     * @return
     *  int containing the OrderId field.
     */
    public int getOrderId(){
        return mOrderId;
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
     * Sets the OrderDeliveryDate field.
     *
     * @param pOrderDeliveryDate
     *  Date to use to update the field.
     */
    public void setOrderDeliveryDate(Date pOrderDeliveryDate){
        this.mOrderDeliveryDate = pOrderDeliveryDate;
        setDirty(true);
    }
    /**
     * Retrieves the OrderDeliveryDate field.
     *
     * @return
     *  Date containing the OrderDeliveryDate field.
     */
    public Date getOrderDeliveryDate(){
        return mOrderDeliveryDate;
    }


}
