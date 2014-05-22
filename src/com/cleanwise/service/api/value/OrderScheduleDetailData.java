
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        OrderScheduleDetailData
 * Description:  This is a ValueObject class wrapping the database table CLW_ORDER_SCHEDULE_DETAIL.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.OrderScheduleDetailDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>OrderScheduleDetailData</code> is a ValueObject class wrapping of the database table CLW_ORDER_SCHEDULE_DETAIL.
 */
public class OrderScheduleDetailData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 6451910394303312801L;
    private int mOrderScheduleDetailId;// SQL type:NUMBER, not null
    private String mRecordStatusCd;// SQL type:VARCHAR2, not null
    private int mOrderScheduleId;// SQL type:NUMBER, not null
    private String mOrderScheduleDetailCd;// SQL type:VARCHAR2, not null
    private String mDetail;// SQL type:VARCHAR2, not null
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public OrderScheduleDetailData ()
    {
        mRecordStatusCd = "";
        mOrderScheduleDetailCd = "";
        mDetail = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public OrderScheduleDetailData(int parm1, String parm2, int parm3, String parm4, String parm5, Date parm6, String parm7, Date parm8, String parm9)
    {
        mOrderScheduleDetailId = parm1;
        mRecordStatusCd = parm2;
        mOrderScheduleId = parm3;
        mOrderScheduleDetailCd = parm4;
        mDetail = parm5;
        mAddDate = parm6;
        mAddBy = parm7;
        mModDate = parm8;
        mModBy = parm9;
        
    }

    /**
     * Creates a new OrderScheduleDetailData
     *
     * @return
     *  Newly initialized OrderScheduleDetailData object.
     */
    public static OrderScheduleDetailData createValue ()
    {
        OrderScheduleDetailData valueData = new OrderScheduleDetailData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this OrderScheduleDetailData object
     */
    public String toString()
    {
        return "[" + "OrderScheduleDetailId=" + mOrderScheduleDetailId + ", RecordStatusCd=" + mRecordStatusCd + ", OrderScheduleId=" + mOrderScheduleId + ", OrderScheduleDetailCd=" + mOrderScheduleDetailCd + ", Detail=" + mDetail + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("OrderScheduleDetail");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mOrderScheduleDetailId));

        node =  doc.createElement("RecordStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mRecordStatusCd)));
        root.appendChild(node);

        node =  doc.createElement("OrderScheduleId");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderScheduleId)));
        root.appendChild(node);

        node =  doc.createElement("OrderScheduleDetailCd");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderScheduleDetailCd)));
        root.appendChild(node);

        node =  doc.createElement("Detail");
        node.appendChild(doc.createTextNode(String.valueOf(mDetail)));
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
    * creates a clone of this object, the OrderScheduleDetailId field is not cloned.
    *
    * @return OrderScheduleDetailData object
    */
    public Object clone(){
        OrderScheduleDetailData myClone = new OrderScheduleDetailData();
        
        myClone.mRecordStatusCd = mRecordStatusCd;
        
        myClone.mOrderScheduleId = mOrderScheduleId;
        
        myClone.mOrderScheduleDetailCd = mOrderScheduleDetailCd;
        
        myClone.mDetail = mDetail;
        
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

        if (OrderScheduleDetailDataAccess.ORDER_SCHEDULE_DETAIL_ID.equals(pFieldName)) {
            return getOrderScheduleDetailId();
        } else if (OrderScheduleDetailDataAccess.RECORD_STATUS_CD.equals(pFieldName)) {
            return getRecordStatusCd();
        } else if (OrderScheduleDetailDataAccess.ORDER_SCHEDULE_ID.equals(pFieldName)) {
            return getOrderScheduleId();
        } else if (OrderScheduleDetailDataAccess.ORDER_SCHEDULE_DETAIL_CD.equals(pFieldName)) {
            return getOrderScheduleDetailCd();
        } else if (OrderScheduleDetailDataAccess.DETAIL.equals(pFieldName)) {
            return getDetail();
        } else if (OrderScheduleDetailDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (OrderScheduleDetailDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (OrderScheduleDetailDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (OrderScheduleDetailDataAccess.MOD_BY.equals(pFieldName)) {
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
        return OrderScheduleDetailDataAccess.CLW_ORDER_SCHEDULE_DETAIL;
    }

    
    /**
     * Sets the OrderScheduleDetailId field. This field is required to be set in the database.
     *
     * @param pOrderScheduleDetailId
     *  int to use to update the field.
     */
    public void setOrderScheduleDetailId(int pOrderScheduleDetailId){
        this.mOrderScheduleDetailId = pOrderScheduleDetailId;
        setDirty(true);
    }
    /**
     * Retrieves the OrderScheduleDetailId field.
     *
     * @return
     *  int containing the OrderScheduleDetailId field.
     */
    public int getOrderScheduleDetailId(){
        return mOrderScheduleDetailId;
    }

    /**
     * Sets the RecordStatusCd field. This field is required to be set in the database.
     *
     * @param pRecordStatusCd
     *  String to use to update the field.
     */
    public void setRecordStatusCd(String pRecordStatusCd){
        this.mRecordStatusCd = pRecordStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the RecordStatusCd field.
     *
     * @return
     *  String containing the RecordStatusCd field.
     */
    public String getRecordStatusCd(){
        return mRecordStatusCd;
    }

    /**
     * Sets the OrderScheduleId field. This field is required to be set in the database.
     *
     * @param pOrderScheduleId
     *  int to use to update the field.
     */
    public void setOrderScheduleId(int pOrderScheduleId){
        this.mOrderScheduleId = pOrderScheduleId;
        setDirty(true);
    }
    /**
     * Retrieves the OrderScheduleId field.
     *
     * @return
     *  int containing the OrderScheduleId field.
     */
    public int getOrderScheduleId(){
        return mOrderScheduleId;
    }

    /**
     * Sets the OrderScheduleDetailCd field. This field is required to be set in the database.
     *
     * @param pOrderScheduleDetailCd
     *  String to use to update the field.
     */
    public void setOrderScheduleDetailCd(String pOrderScheduleDetailCd){
        this.mOrderScheduleDetailCd = pOrderScheduleDetailCd;
        setDirty(true);
    }
    /**
     * Retrieves the OrderScheduleDetailCd field.
     *
     * @return
     *  String containing the OrderScheduleDetailCd field.
     */
    public String getOrderScheduleDetailCd(){
        return mOrderScheduleDetailCd;
    }

    /**
     * Sets the Detail field. This field is required to be set in the database.
     *
     * @param pDetail
     *  String to use to update the field.
     */
    public void setDetail(String pDetail){
        this.mDetail = pDetail;
        setDirty(true);
    }
    /**
     * Retrieves the Detail field.
     *
     * @return
     *  String containing the Detail field.
     */
    public String getDetail(){
        return mDetail;
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
