
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ReturnRequestDetailData
 * Description:  This is a ValueObject class wrapping the database table CLW_RETURN_REQUEST_DETAIL.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.ReturnRequestDetailDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>ReturnRequestDetailData</code> is a ValueObject class wrapping of the database table CLW_RETURN_REQUEST_DETAIL.
 */
public class ReturnRequestDetailData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -4952060016634924854L;
    private int mReturnRequestDetailId;// SQL type:NUMBER, not null
    private int mReturnRequestId;// SQL type:NUMBER
    private int mOrderItemId;// SQL type:NUMBER
    private int mQuantityReturned;// SQL type:NUMBER
    private Date mPickupDate;// SQL type:DATE
    private String mAddBy;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE
    private String mModBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE
    private String mRecievedDistSku;// SQL type:VARCHAR2
    private String mRecievedDistUom;// SQL type:VARCHAR2
    private String mRecievedDistPack;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public ReturnRequestDetailData ()
    {
        mAddBy = "";
        mModBy = "";
        mRecievedDistSku = "";
        mRecievedDistUom = "";
        mRecievedDistPack = "";
    }

    /**
     * Constructor.
     */
    public ReturnRequestDetailData(int parm1, int parm2, int parm3, int parm4, Date parm5, String parm6, Date parm7, String parm8, Date parm9, String parm10, String parm11, String parm12)
    {
        mReturnRequestDetailId = parm1;
        mReturnRequestId = parm2;
        mOrderItemId = parm3;
        mQuantityReturned = parm4;
        mPickupDate = parm5;
        mAddBy = parm6;
        mAddDate = parm7;
        mModBy = parm8;
        mModDate = parm9;
        mRecievedDistSku = parm10;
        mRecievedDistUom = parm11;
        mRecievedDistPack = parm12;
        
    }

    /**
     * Creates a new ReturnRequestDetailData
     *
     * @return
     *  Newly initialized ReturnRequestDetailData object.
     */
    public static ReturnRequestDetailData createValue ()
    {
        ReturnRequestDetailData valueData = new ReturnRequestDetailData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ReturnRequestDetailData object
     */
    public String toString()
    {
        return "[" + "ReturnRequestDetailId=" + mReturnRequestDetailId + ", ReturnRequestId=" + mReturnRequestId + ", OrderItemId=" + mOrderItemId + ", QuantityReturned=" + mQuantityReturned + ", PickupDate=" + mPickupDate + ", AddBy=" + mAddBy + ", AddDate=" + mAddDate + ", ModBy=" + mModBy + ", ModDate=" + mModDate + ", RecievedDistSku=" + mRecievedDistSku + ", RecievedDistUom=" + mRecievedDistUom + ", RecievedDistPack=" + mRecievedDistPack + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("ReturnRequestDetail");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mReturnRequestDetailId));

        node =  doc.createElement("ReturnRequestId");
        node.appendChild(doc.createTextNode(String.valueOf(mReturnRequestId)));
        root.appendChild(node);

        node =  doc.createElement("OrderItemId");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderItemId)));
        root.appendChild(node);

        node =  doc.createElement("QuantityReturned");
        node.appendChild(doc.createTextNode(String.valueOf(mQuantityReturned)));
        root.appendChild(node);

        node =  doc.createElement("PickupDate");
        node.appendChild(doc.createTextNode(String.valueOf(mPickupDate)));
        root.appendChild(node);

        node =  doc.createElement("AddBy");
        node.appendChild(doc.createTextNode(String.valueOf(mAddBy)));
        root.appendChild(node);

        node =  doc.createElement("AddDate");
        node.appendChild(doc.createTextNode(String.valueOf(mAddDate)));
        root.appendChild(node);

        node =  doc.createElement("ModBy");
        node.appendChild(doc.createTextNode(String.valueOf(mModBy)));
        root.appendChild(node);

        node =  doc.createElement("ModDate");
        node.appendChild(doc.createTextNode(String.valueOf(mModDate)));
        root.appendChild(node);

        node =  doc.createElement("RecievedDistSku");
        node.appendChild(doc.createTextNode(String.valueOf(mRecievedDistSku)));
        root.appendChild(node);

        node =  doc.createElement("RecievedDistUom");
        node.appendChild(doc.createTextNode(String.valueOf(mRecievedDistUom)));
        root.appendChild(node);

        node =  doc.createElement("RecievedDistPack");
        node.appendChild(doc.createTextNode(String.valueOf(mRecievedDistPack)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the ReturnRequestDetailId field is not cloned.
    *
    * @return ReturnRequestDetailData object
    */
    public Object clone(){
        ReturnRequestDetailData myClone = new ReturnRequestDetailData();
        
        myClone.mReturnRequestId = mReturnRequestId;
        
        myClone.mOrderItemId = mOrderItemId;
        
        myClone.mQuantityReturned = mQuantityReturned;
        
        if(mPickupDate != null){
                myClone.mPickupDate = (Date) mPickupDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mRecievedDistSku = mRecievedDistSku;
        
        myClone.mRecievedDistUom = mRecievedDistUom;
        
        myClone.mRecievedDistPack = mRecievedDistPack;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (ReturnRequestDetailDataAccess.RETURN_REQUEST_DETAIL_ID.equals(pFieldName)) {
            return getReturnRequestDetailId();
        } else if (ReturnRequestDetailDataAccess.RETURN_REQUEST_ID.equals(pFieldName)) {
            return getReturnRequestId();
        } else if (ReturnRequestDetailDataAccess.ORDER_ITEM_ID.equals(pFieldName)) {
            return getOrderItemId();
        } else if (ReturnRequestDetailDataAccess.QUANTITY_RETURNED.equals(pFieldName)) {
            return getQuantityReturned();
        } else if (ReturnRequestDetailDataAccess.PICKUP_DATE.equals(pFieldName)) {
            return getPickupDate();
        } else if (ReturnRequestDetailDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (ReturnRequestDetailDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (ReturnRequestDetailDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (ReturnRequestDetailDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (ReturnRequestDetailDataAccess.RECIEVED_DIST_SKU.equals(pFieldName)) {
            return getRecievedDistSku();
        } else if (ReturnRequestDetailDataAccess.RECIEVED_DIST_UOM.equals(pFieldName)) {
            return getRecievedDistUom();
        } else if (ReturnRequestDetailDataAccess.RECIEVED_DIST_PACK.equals(pFieldName)) {
            return getRecievedDistPack();
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
        return ReturnRequestDetailDataAccess.CLW_RETURN_REQUEST_DETAIL;
    }

    
    /**
     * Sets the ReturnRequestDetailId field. This field is required to be set in the database.
     *
     * @param pReturnRequestDetailId
     *  int to use to update the field.
     */
    public void setReturnRequestDetailId(int pReturnRequestDetailId){
        this.mReturnRequestDetailId = pReturnRequestDetailId;
        setDirty(true);
    }
    /**
     * Retrieves the ReturnRequestDetailId field.
     *
     * @return
     *  int containing the ReturnRequestDetailId field.
     */
    public int getReturnRequestDetailId(){
        return mReturnRequestDetailId;
    }

    /**
     * Sets the ReturnRequestId field.
     *
     * @param pReturnRequestId
     *  int to use to update the field.
     */
    public void setReturnRequestId(int pReturnRequestId){
        this.mReturnRequestId = pReturnRequestId;
        setDirty(true);
    }
    /**
     * Retrieves the ReturnRequestId field.
     *
     * @return
     *  int containing the ReturnRequestId field.
     */
    public int getReturnRequestId(){
        return mReturnRequestId;
    }

    /**
     * Sets the OrderItemId field.
     *
     * @param pOrderItemId
     *  int to use to update the field.
     */
    public void setOrderItemId(int pOrderItemId){
        this.mOrderItemId = pOrderItemId;
        setDirty(true);
    }
    /**
     * Retrieves the OrderItemId field.
     *
     * @return
     *  int containing the OrderItemId field.
     */
    public int getOrderItemId(){
        return mOrderItemId;
    }

    /**
     * Sets the QuantityReturned field.
     *
     * @param pQuantityReturned
     *  int to use to update the field.
     */
    public void setQuantityReturned(int pQuantityReturned){
        this.mQuantityReturned = pQuantityReturned;
        setDirty(true);
    }
    /**
     * Retrieves the QuantityReturned field.
     *
     * @return
     *  int containing the QuantityReturned field.
     */
    public int getQuantityReturned(){
        return mQuantityReturned;
    }

    /**
     * Sets the PickupDate field.
     *
     * @param pPickupDate
     *  Date to use to update the field.
     */
    public void setPickupDate(Date pPickupDate){
        this.mPickupDate = pPickupDate;
        setDirty(true);
    }
    /**
     * Retrieves the PickupDate field.
     *
     * @return
     *  Date containing the PickupDate field.
     */
    public Date getPickupDate(){
        return mPickupDate;
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
     * Sets the RecievedDistSku field.
     *
     * @param pRecievedDistSku
     *  String to use to update the field.
     */
    public void setRecievedDistSku(String pRecievedDistSku){
        this.mRecievedDistSku = pRecievedDistSku;
        setDirty(true);
    }
    /**
     * Retrieves the RecievedDistSku field.
     *
     * @return
     *  String containing the RecievedDistSku field.
     */
    public String getRecievedDistSku(){
        return mRecievedDistSku;
    }

    /**
     * Sets the RecievedDistUom field.
     *
     * @param pRecievedDistUom
     *  String to use to update the field.
     */
    public void setRecievedDistUom(String pRecievedDistUom){
        this.mRecievedDistUom = pRecievedDistUom;
        setDirty(true);
    }
    /**
     * Retrieves the RecievedDistUom field.
     *
     * @return
     *  String containing the RecievedDistUom field.
     */
    public String getRecievedDistUom(){
        return mRecievedDistUom;
    }

    /**
     * Sets the RecievedDistPack field.
     *
     * @param pRecievedDistPack
     *  String to use to update the field.
     */
    public void setRecievedDistPack(String pRecievedDistPack){
        this.mRecievedDistPack = pRecievedDistPack;
        setDirty(true);
    }
    /**
     * Retrieves the RecievedDistPack field.
     *
     * @return
     *  String containing the RecievedDistPack field.
     */
    public String getRecievedDistPack(){
        return mRecievedDistPack;
    }


}
