
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        WoiStatusHistData
 * Description:  This is a ValueObject class wrapping the database table CLW_WOI_STATUS_HIST.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.WoiStatusHistDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>WoiStatusHistData</code> is a ValueObject class wrapping of the database table CLW_WOI_STATUS_HIST.
 */
public class WoiStatusHistData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -2573653022855225980L;
    private int mWoiStatusHistId;// SQL type:NUMBER, not null
    private int mWorkOrderItemId;// SQL type:NUMBER, not null
    private String mStatusCd;// SQL type:VARCHAR2, not null
    private Date mStatusDate;// SQL type:DATE, not null
    private String mTypeCd;// SQL type:VARCHAR2, not null
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public WoiStatusHistData ()
    {
        mStatusCd = "";
        mTypeCd = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public WoiStatusHistData(int parm1, int parm2, String parm3, Date parm4, String parm5, Date parm6, String parm7, Date parm8, String parm9)
    {
        mWoiStatusHistId = parm1;
        mWorkOrderItemId = parm2;
        mStatusCd = parm3;
        mStatusDate = parm4;
        mTypeCd = parm5;
        mAddDate = parm6;
        mAddBy = parm7;
        mModDate = parm8;
        mModBy = parm9;
        
    }

    /**
     * Creates a new WoiStatusHistData
     *
     * @return
     *  Newly initialized WoiStatusHistData object.
     */
    public static WoiStatusHistData createValue ()
    {
        WoiStatusHistData valueData = new WoiStatusHistData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this WoiStatusHistData object
     */
    public String toString()
    {
        return "[" + "WoiStatusHistId=" + mWoiStatusHistId + ", WorkOrderItemId=" + mWorkOrderItemId + ", StatusCd=" + mStatusCd + ", StatusDate=" + mStatusDate + ", TypeCd=" + mTypeCd + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("WoiStatusHist");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mWoiStatusHistId));

        node =  doc.createElement("WorkOrderItemId");
        node.appendChild(doc.createTextNode(String.valueOf(mWorkOrderItemId)));
        root.appendChild(node);

        node =  doc.createElement("StatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mStatusCd)));
        root.appendChild(node);

        node =  doc.createElement("StatusDate");
        node.appendChild(doc.createTextNode(String.valueOf(mStatusDate)));
        root.appendChild(node);

        node =  doc.createElement("TypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mTypeCd)));
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
    * creates a clone of this object, the WoiStatusHistId field is not cloned.
    *
    * @return WoiStatusHistData object
    */
    public Object clone(){
        WoiStatusHistData myClone = new WoiStatusHistData();
        
        myClone.mWorkOrderItemId = mWorkOrderItemId;
        
        myClone.mStatusCd = mStatusCd;
        
        if(mStatusDate != null){
                myClone.mStatusDate = (Date) mStatusDate.clone();
        }
        
        myClone.mTypeCd = mTypeCd;
        
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

        if (WoiStatusHistDataAccess.WOI_STATUS_HIST_ID.equals(pFieldName)) {
            return getWoiStatusHistId();
        } else if (WoiStatusHistDataAccess.WORK_ORDER_ITEM_ID.equals(pFieldName)) {
            return getWorkOrderItemId();
        } else if (WoiStatusHistDataAccess.STATUS_CD.equals(pFieldName)) {
            return getStatusCd();
        } else if (WoiStatusHistDataAccess.STATUS_DATE.equals(pFieldName)) {
            return getStatusDate();
        } else if (WoiStatusHistDataAccess.TYPE_CD.equals(pFieldName)) {
            return getTypeCd();
        } else if (WoiStatusHistDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (WoiStatusHistDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (WoiStatusHistDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (WoiStatusHistDataAccess.MOD_BY.equals(pFieldName)) {
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
        return WoiStatusHistDataAccess.CLW_WOI_STATUS_HIST;
    }

    
    /**
     * Sets the WoiStatusHistId field. This field is required to be set in the database.
     *
     * @param pWoiStatusHistId
     *  int to use to update the field.
     */
    public void setWoiStatusHistId(int pWoiStatusHistId){
        this.mWoiStatusHistId = pWoiStatusHistId;
        setDirty(true);
    }
    /**
     * Retrieves the WoiStatusHistId field.
     *
     * @return
     *  int containing the WoiStatusHistId field.
     */
    public int getWoiStatusHistId(){
        return mWoiStatusHistId;
    }

    /**
     * Sets the WorkOrderItemId field. This field is required to be set in the database.
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
     * Sets the StatusCd field. This field is required to be set in the database.
     *
     * @param pStatusCd
     *  String to use to update the field.
     */
    public void setStatusCd(String pStatusCd){
        this.mStatusCd = pStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the StatusCd field.
     *
     * @return
     *  String containing the StatusCd field.
     */
    public String getStatusCd(){
        return mStatusCd;
    }

    /**
     * Sets the StatusDate field. This field is required to be set in the database.
     *
     * @param pStatusDate
     *  Date to use to update the field.
     */
    public void setStatusDate(Date pStatusDate){
        this.mStatusDate = pStatusDate;
        setDirty(true);
    }
    /**
     * Retrieves the StatusDate field.
     *
     * @return
     *  Date containing the StatusDate field.
     */
    public Date getStatusDate(){
        return mStatusDate;
    }

    /**
     * Sets the TypeCd field. This field is required to be set in the database.
     *
     * @param pTypeCd
     *  String to use to update the field.
     */
    public void setTypeCd(String pTypeCd){
        this.mTypeCd = pTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the TypeCd field.
     *
     * @return
     *  String containing the TypeCd field.
     */
    public String getTypeCd(){
        return mTypeCd;
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
