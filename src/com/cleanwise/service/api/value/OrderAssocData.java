
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        OrderAssocData
 * Description:  This is a ValueObject class wrapping the database table CLW_ORDER_ASSOC.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.OrderAssocDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>OrderAssocData</code> is a ValueObject class wrapping of the database table CLW_ORDER_ASSOC.
 */
public class OrderAssocData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -9187654978840304643L;
    private int mOrderAssocId;// SQL type:NUMBER, not null
    private int mOrder1Id;// SQL type:NUMBER
    private int mOrder2Id;// SQL type:NUMBER, not null
    private String mOrderAssocCd;// SQL type:VARCHAR2
    private String mOrderAssocStatusCd;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private int mWorkOrderItemId;// SQL type:NUMBER
    private int mServiceTicketId;// SQL type:NUMBER

    /**
     * Constructor.
     */
    public OrderAssocData ()
    {
        mOrderAssocCd = "";
        mOrderAssocStatusCd = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public OrderAssocData(int parm1, int parm2, int parm3, String parm4, String parm5, Date parm6, String parm7, Date parm8, String parm9, int parm10, int parm11)
    {
        mOrderAssocId = parm1;
        mOrder1Id = parm2;
        mOrder2Id = parm3;
        mOrderAssocCd = parm4;
        mOrderAssocStatusCd = parm5;
        mAddDate = parm6;
        mAddBy = parm7;
        mModDate = parm8;
        mModBy = parm9;
        mWorkOrderItemId = parm10;
        mServiceTicketId = parm11;
        
    }

    /**
     * Creates a new OrderAssocData
     *
     * @return
     *  Newly initialized OrderAssocData object.
     */
    public static OrderAssocData createValue ()
    {
        OrderAssocData valueData = new OrderAssocData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this OrderAssocData object
     */
    public String toString()
    {
        return "[" + "OrderAssocId=" + mOrderAssocId + ", Order1Id=" + mOrder1Id + ", Order2Id=" + mOrder2Id + ", OrderAssocCd=" + mOrderAssocCd + ", OrderAssocStatusCd=" + mOrderAssocStatusCd + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", WorkOrderItemId=" + mWorkOrderItemId + ", ServiceTicketId=" + mServiceTicketId + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("OrderAssoc");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mOrderAssocId));

        node =  doc.createElement("Order1Id");
        node.appendChild(doc.createTextNode(String.valueOf(mOrder1Id)));
        root.appendChild(node);

        node =  doc.createElement("Order2Id");
        node.appendChild(doc.createTextNode(String.valueOf(mOrder2Id)));
        root.appendChild(node);

        node =  doc.createElement("OrderAssocCd");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderAssocCd)));
        root.appendChild(node);

        node =  doc.createElement("OrderAssocStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderAssocStatusCd)));
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

        node =  doc.createElement("WorkOrderItemId");
        node.appendChild(doc.createTextNode(String.valueOf(mWorkOrderItemId)));
        root.appendChild(node);

        node = doc.createElement("ServiceTicketId");
        node.appendChild(doc.createTextNode(String.valueOf(mServiceTicketId)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the OrderAssocId field is not cloned.
    *
    * @return OrderAssocData object
    */
    public Object clone(){
        OrderAssocData myClone = new OrderAssocData();
        
        myClone.mOrder1Id = mOrder1Id;
        
        myClone.mOrder2Id = mOrder2Id;
        
        myClone.mOrderAssocCd = mOrderAssocCd;
        
        myClone.mOrderAssocStatusCd = mOrderAssocStatusCd;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        myClone.mWorkOrderItemId = mWorkOrderItemId;
        
        myClone.mServiceTicketId = mServiceTicketId;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (OrderAssocDataAccess.ORDER_ASSOC_ID.equals(pFieldName)) {
            return getOrderAssocId();
        } else if (OrderAssocDataAccess.ORDER1_ID.equals(pFieldName)) {
            return getOrder1Id();
        } else if (OrderAssocDataAccess.ORDER2_ID.equals(pFieldName)) {
            return getOrder2Id();
        } else if (OrderAssocDataAccess.ORDER_ASSOC_CD.equals(pFieldName)) {
            return getOrderAssocCd();
        } else if (OrderAssocDataAccess.ORDER_ASSOC_STATUS_CD.equals(pFieldName)) {
            return getOrderAssocStatusCd();
        } else if (OrderAssocDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (OrderAssocDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (OrderAssocDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (OrderAssocDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (OrderAssocDataAccess.WORK_ORDER_ITEM_ID.equals(pFieldName)) {
            return getWorkOrderItemId();
        } else if (OrderAssocDataAccess.SERVICE_TICKET_ID.equals(pFieldName)) {
            return getServiceTicketId();
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
        return OrderAssocDataAccess.CLW_ORDER_ASSOC;
    }

    
    /**
     * Sets the OrderAssocId field. This field is required to be set in the database.
     *
     * @param pOrderAssocId
     *  int to use to update the field.
     */
    public void setOrderAssocId(int pOrderAssocId){
        this.mOrderAssocId = pOrderAssocId;
        setDirty(true);
    }
    /**
     * Retrieves the OrderAssocId field.
     *
     * @return
     *  int containing the OrderAssocId field.
     */
    public int getOrderAssocId(){
        return mOrderAssocId;
    }

    /**
     * Sets the Order1Id field.
     *
     * @param pOrder1Id
     *  int to use to update the field.
     */
    public void setOrder1Id(int pOrder1Id){
        this.mOrder1Id = pOrder1Id;
        setDirty(true);
    }
    /**
     * Retrieves the Order1Id field.
     *
     * @return
     *  int containing the Order1Id field.
     */
    public int getOrder1Id(){
        return mOrder1Id;
    }

    /**
     * Sets the Order2Id field. This field is required to be set in the database.
     *
     * @param pOrder2Id
     *  int to use to update the field.
     */
    public void setOrder2Id(int pOrder2Id){
        this.mOrder2Id = pOrder2Id;
        setDirty(true);
    }
    /**
     * Retrieves the Order2Id field.
     *
     * @return
     *  int containing the Order2Id field.
     */
    public int getOrder2Id(){
        return mOrder2Id;
    }

    /**
     * Sets the OrderAssocCd field.
     *
     * @param pOrderAssocCd
     *  String to use to update the field.
     */
    public void setOrderAssocCd(String pOrderAssocCd){
        this.mOrderAssocCd = pOrderAssocCd;
        setDirty(true);
    }
    /**
     * Retrieves the OrderAssocCd field.
     *
     * @return
     *  String containing the OrderAssocCd field.
     */
    public String getOrderAssocCd(){
        return mOrderAssocCd;
    }

    /**
     * Sets the OrderAssocStatusCd field.
     *
     * @param pOrderAssocStatusCd
     *  String to use to update the field.
     */
    public void setOrderAssocStatusCd(String pOrderAssocStatusCd){
        this.mOrderAssocStatusCd = pOrderAssocStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the OrderAssocStatusCd field.
     *
     * @return
     *  String containing the OrderAssocStatusCd field.
     */
    public String getOrderAssocStatusCd(){
        return mOrderAssocStatusCd;
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
     * Sets the WorkOrderItemId field.
     *
     * @param pWorkOrderItemId
     *  int to use to update the field.
     */
    public void setWorkOrderItemId(int pWorkOrderItemId){
        this.mWorkOrderItemId = pWorkOrderItemId;
        setDirty(true);
    }
    /**
     * Retrieves the WorkOrderItemId field.
     *
     * @return
     *  int containing the WorkOrderItemId field.
     */
    public int getWorkOrderItemId(){
        return mWorkOrderItemId;
    }

    /**
     * Sets the ServiceTicketId field.
     *
     * @param pServiceTicketId
     *  int to use to update the field.
     */
    public void setServiceTicketId(int pServiceTicketId){
        this.mServiceTicketId = pServiceTicketId;
        setDirty(true);
    }
    /**
     * Retrieves the ServiceTicketId field.
     *
     * @return
     *  int containing the ServiceTicketId field.
     */
    public int getServiceTicketId(){
        return mServiceTicketId;
    }


}
