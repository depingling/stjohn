
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        DispatchData
 * Description:  This is a ValueObject class wrapping the database table CLW_DISPATCH.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.DispatchDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>DispatchData</code> is a ValueObject class wrapping of the database table CLW_DISPATCH.
 */
public class DispatchData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 1255430723756469358L;
    private int mDispatchId;// SQL type:NUMBER, not null
    private int mBusEntityId;// SQL type:NUMBER
    private String mShortDesc;// SQL type:VARCHAR2, not null
    private String mLongDesc;// SQL type:VARCHAR2
    private String mTypeCd;// SQL type:VARCHAR2, not null
    private String mStatusCd;// SQL type:VARCHAR2, not null
    private String mSourceCd;// SQL type:VARCHAR2
    private String mPriority;// SQL type:VARCHAR2, not null
    private Date mNotifyDate;// SQL type:DATE
    private Date mCreateDate;// SQL type:DATE
    private Date mCompleteDate;// SQL type:DATE
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public DispatchData ()
    {
        mShortDesc = "";
        mLongDesc = "";
        mTypeCd = "";
        mStatusCd = "";
        mSourceCd = "";
        mPriority = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public DispatchData(int parm1, int parm2, String parm3, String parm4, String parm5, String parm6, String parm7, String parm8, Date parm9, Date parm10, Date parm11, Date parm12, String parm13, Date parm14, String parm15)
    {
        mDispatchId = parm1;
        mBusEntityId = parm2;
        mShortDesc = parm3;
        mLongDesc = parm4;
        mTypeCd = parm5;
        mStatusCd = parm6;
        mSourceCd = parm7;
        mPriority = parm8;
        mNotifyDate = parm9;
        mCreateDate = parm10;
        mCompleteDate = parm11;
        mAddDate = parm12;
        mAddBy = parm13;
        mModDate = parm14;
        mModBy = parm15;
        
    }

    /**
     * Creates a new DispatchData
     *
     * @return
     *  Newly initialized DispatchData object.
     */
    public static DispatchData createValue ()
    {
        DispatchData valueData = new DispatchData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this DispatchData object
     */
    public String toString()
    {
        return "[" + "DispatchId=" + mDispatchId + ", BusEntityId=" + mBusEntityId + ", ShortDesc=" + mShortDesc + ", LongDesc=" + mLongDesc + ", TypeCd=" + mTypeCd + ", StatusCd=" + mStatusCd + ", SourceCd=" + mSourceCd + ", Priority=" + mPriority + ", NotifyDate=" + mNotifyDate + ", CreateDate=" + mCreateDate + ", CompleteDate=" + mCompleteDate + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("Dispatch");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mDispatchId));

        node =  doc.createElement("BusEntityId");
        node.appendChild(doc.createTextNode(String.valueOf(mBusEntityId)));
        root.appendChild(node);

        node =  doc.createElement("ShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mShortDesc)));
        root.appendChild(node);

        node =  doc.createElement("LongDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mLongDesc)));
        root.appendChild(node);

        node =  doc.createElement("TypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mTypeCd)));
        root.appendChild(node);

        node =  doc.createElement("StatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mStatusCd)));
        root.appendChild(node);

        node =  doc.createElement("SourceCd");
        node.appendChild(doc.createTextNode(String.valueOf(mSourceCd)));
        root.appendChild(node);

        node =  doc.createElement("Priority");
        node.appendChild(doc.createTextNode(String.valueOf(mPriority)));
        root.appendChild(node);

        node =  doc.createElement("NotifyDate");
        node.appendChild(doc.createTextNode(String.valueOf(mNotifyDate)));
        root.appendChild(node);

        node =  doc.createElement("CreateDate");
        node.appendChild(doc.createTextNode(String.valueOf(mCreateDate)));
        root.appendChild(node);

        node =  doc.createElement("CompleteDate");
        node.appendChild(doc.createTextNode(String.valueOf(mCompleteDate)));
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
    * creates a clone of this object, the DispatchId field is not cloned.
    *
    * @return DispatchData object
    */
    public Object clone(){
        DispatchData myClone = new DispatchData();
        
        myClone.mBusEntityId = mBusEntityId;
        
        myClone.mShortDesc = mShortDesc;
        
        myClone.mLongDesc = mLongDesc;
        
        myClone.mTypeCd = mTypeCd;
        
        myClone.mStatusCd = mStatusCd;
        
        myClone.mSourceCd = mSourceCd;
        
        myClone.mPriority = mPriority;
        
        if(mNotifyDate != null){
                myClone.mNotifyDate = (Date) mNotifyDate.clone();
        }
        
        if(mCreateDate != null){
                myClone.mCreateDate = (Date) mCreateDate.clone();
        }
        
        if(mCompleteDate != null){
                myClone.mCompleteDate = (Date) mCompleteDate.clone();
        }
        
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

        if (DispatchDataAccess.DISPATCH_ID.equals(pFieldName)) {
            return getDispatchId();
        } else if (DispatchDataAccess.BUS_ENTITY_ID.equals(pFieldName)) {
            return getBusEntityId();
        } else if (DispatchDataAccess.SHORT_DESC.equals(pFieldName)) {
            return getShortDesc();
        } else if (DispatchDataAccess.LONG_DESC.equals(pFieldName)) {
            return getLongDesc();
        } else if (DispatchDataAccess.TYPE_CD.equals(pFieldName)) {
            return getTypeCd();
        } else if (DispatchDataAccess.STATUS_CD.equals(pFieldName)) {
            return getStatusCd();
        } else if (DispatchDataAccess.SOURCE_CD.equals(pFieldName)) {
            return getSourceCd();
        } else if (DispatchDataAccess.PRIORITY.equals(pFieldName)) {
            return getPriority();
        } else if (DispatchDataAccess.NOTIFY_DATE.equals(pFieldName)) {
            return getNotifyDate();
        } else if (DispatchDataAccess.CREATE_DATE.equals(pFieldName)) {
            return getCreateDate();
        } else if (DispatchDataAccess.COMPLETE_DATE.equals(pFieldName)) {
            return getCompleteDate();
        } else if (DispatchDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (DispatchDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (DispatchDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (DispatchDataAccess.MOD_BY.equals(pFieldName)) {
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
        return DispatchDataAccess.CLW_DISPATCH;
    }

    
    /**
     * Sets the DispatchId field. This field is required to be set in the database.
     *
     * @param pDispatchId
     *  int to use to update the field.
     */
    public void setDispatchId(int pDispatchId){
        this.mDispatchId = pDispatchId;
        setDirty(true);
    }
    /**
     * Retrieves the DispatchId field.
     *
     * @return
     *  int containing the DispatchId field.
     */
    public int getDispatchId(){
        return mDispatchId;
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
     * Sets the LongDesc field.
     *
     * @param pLongDesc
     *  String to use to update the field.
     */
    public void setLongDesc(String pLongDesc){
        this.mLongDesc = pLongDesc;
        setDirty(true);
    }
    /**
     * Retrieves the LongDesc field.
     *
     * @return
     *  String containing the LongDesc field.
     */
    public String getLongDesc(){
        return mLongDesc;
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
     * Sets the SourceCd field.
     *
     * @param pSourceCd
     *  String to use to update the field.
     */
    public void setSourceCd(String pSourceCd){
        this.mSourceCd = pSourceCd;
        setDirty(true);
    }
    /**
     * Retrieves the SourceCd field.
     *
     * @return
     *  String containing the SourceCd field.
     */
    public String getSourceCd(){
        return mSourceCd;
    }

    /**
     * Sets the Priority field. This field is required to be set in the database.
     *
     * @param pPriority
     *  String to use to update the field.
     */
    public void setPriority(String pPriority){
        this.mPriority = pPriority;
        setDirty(true);
    }
    /**
     * Retrieves the Priority field.
     *
     * @return
     *  String containing the Priority field.
     */
    public String getPriority(){
        return mPriority;
    }

    /**
     * Sets the NotifyDate field.
     *
     * @param pNotifyDate
     *  Date to use to update the field.
     */
    public void setNotifyDate(Date pNotifyDate){
        this.mNotifyDate = pNotifyDate;
        setDirty(true);
    }
    /**
     * Retrieves the NotifyDate field.
     *
     * @return
     *  Date containing the NotifyDate field.
     */
    public Date getNotifyDate(){
        return mNotifyDate;
    }

    /**
     * Sets the CreateDate field.
     *
     * @param pCreateDate
     *  Date to use to update the field.
     */
    public void setCreateDate(Date pCreateDate){
        this.mCreateDate = pCreateDate;
        setDirty(true);
    }
    /**
     * Retrieves the CreateDate field.
     *
     * @return
     *  Date containing the CreateDate field.
     */
    public Date getCreateDate(){
        return mCreateDate;
    }

    /**
     * Sets the CompleteDate field.
     *
     * @param pCompleteDate
     *  Date to use to update the field.
     */
    public void setCompleteDate(Date pCompleteDate){
        this.mCompleteDate = pCompleteDate;
        setDirty(true);
    }
    /**
     * Retrieves the CompleteDate field.
     *
     * @return
     *  Date containing the CompleteDate field.
     */
    public Date getCompleteDate(){
        return mCompleteDate;
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
