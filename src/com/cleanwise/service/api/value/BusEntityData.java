
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        BusEntityData
 * Description:  This is a ValueObject class wrapping the database table CLW_BUS_ENTITY.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.BusEntityDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>BusEntityData</code> is a ValueObject class wrapping of the database table CLW_BUS_ENTITY.
 */
public class BusEntityData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 6271147657569362519L;
    private int mBusEntityId;// SQL type:NUMBER, not null
    private String mShortDesc;// SQL type:VARCHAR2, not null
    private String mLongDesc;// SQL type:VARCHAR2
    private Date mEffDate;// SQL type:DATE
    private Date mExpDate;// SQL type:DATE
    private String mBusEntityTypeCd;// SQL type:VARCHAR2, not null
    private String mBusEntityStatusCd;// SQL type:VARCHAR2, not null
    private String mWorkflowRoleCd;// SQL type:VARCHAR2, not null
    private String mLocaleCd;// SQL type:VARCHAR2, not null
    private String mErpNum;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private String mTimeZoneCd;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public BusEntityData ()
    {
        mShortDesc = "";
        mLongDesc = "";
        mBusEntityTypeCd = "";
        mBusEntityStatusCd = "";
        mWorkflowRoleCd = "";
        mLocaleCd = "";
        mErpNum = "";
        mAddBy = "";
        mModBy = "";
        mTimeZoneCd = "";
    }

    /**
     * Constructor.
     */
    public BusEntityData(int parm1, String parm2, String parm3, Date parm4, Date parm5, String parm6, String parm7, String parm8, String parm9, String parm10, Date parm11, String parm12, Date parm13, String parm14, String parm15)
    {
        mBusEntityId = parm1;
        mShortDesc = parm2;
        mLongDesc = parm3;
        mEffDate = parm4;
        mExpDate = parm5;
        mBusEntityTypeCd = parm6;
        mBusEntityStatusCd = parm7;
        mWorkflowRoleCd = parm8;
        mLocaleCd = parm9;
        mErpNum = parm10;
        mAddDate = parm11;
        mAddBy = parm12;
        mModDate = parm13;
        mModBy = parm14;
        mTimeZoneCd = parm15;
        
    }

    /**
     * Creates a new BusEntityData
     *
     * @return
     *  Newly initialized BusEntityData object.
     */
    public static BusEntityData createValue ()
    {
        BusEntityData valueData = new BusEntityData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this BusEntityData object
     */
    public String toString()
    {
        return "[" + "BusEntityId=" + mBusEntityId + ", ShortDesc=" + mShortDesc + ", LongDesc=" + mLongDesc + ", EffDate=" + mEffDate + ", ExpDate=" + mExpDate + ", BusEntityTypeCd=" + mBusEntityTypeCd + ", BusEntityStatusCd=" + mBusEntityStatusCd + ", WorkflowRoleCd=" + mWorkflowRoleCd + ", LocaleCd=" + mLocaleCd + ", ErpNum=" + mErpNum + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", TimeZoneCd=" + mTimeZoneCd + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("BusEntity");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mBusEntityId));

        node =  doc.createElement("ShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mShortDesc)));
        root.appendChild(node);

        node =  doc.createElement("LongDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mLongDesc)));
        root.appendChild(node);

        node =  doc.createElement("EffDate");
        node.appendChild(doc.createTextNode(String.valueOf(mEffDate)));
        root.appendChild(node);

        node =  doc.createElement("ExpDate");
        node.appendChild(doc.createTextNode(String.valueOf(mExpDate)));
        root.appendChild(node);

        node =  doc.createElement("BusEntityTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mBusEntityTypeCd)));
        root.appendChild(node);

        node =  doc.createElement("BusEntityStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mBusEntityStatusCd)));
        root.appendChild(node);

        node =  doc.createElement("WorkflowRoleCd");
        node.appendChild(doc.createTextNode(String.valueOf(mWorkflowRoleCd)));
        root.appendChild(node);

        node =  doc.createElement("LocaleCd");
        node.appendChild(doc.createTextNode(String.valueOf(mLocaleCd)));
        root.appendChild(node);

        node =  doc.createElement("ErpNum");
        node.appendChild(doc.createTextNode(String.valueOf(mErpNum)));
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

        node =  doc.createElement("TimeZoneCd");
        node.appendChild(doc.createTextNode(String.valueOf(mTimeZoneCd)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the BusEntityId field is not cloned.
    *
    * @return BusEntityData object
    */
    public Object clone(){
        BusEntityData myClone = new BusEntityData();
        
        myClone.mShortDesc = mShortDesc;
        
        myClone.mLongDesc = mLongDesc;
        
        if(mEffDate != null){
                myClone.mEffDate = (Date) mEffDate.clone();
        }
        
        if(mExpDate != null){
                myClone.mExpDate = (Date) mExpDate.clone();
        }
        
        myClone.mBusEntityTypeCd = mBusEntityTypeCd;
        
        myClone.mBusEntityStatusCd = mBusEntityStatusCd;
        
        myClone.mWorkflowRoleCd = mWorkflowRoleCd;
        
        myClone.mLocaleCd = mLocaleCd;
        
        myClone.mErpNum = mErpNum;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        myClone.mTimeZoneCd = mTimeZoneCd;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (BusEntityDataAccess.BUS_ENTITY_ID.equals(pFieldName)) {
            return getBusEntityId();
        } else if (BusEntityDataAccess.SHORT_DESC.equals(pFieldName)) {
            return getShortDesc();
        } else if (BusEntityDataAccess.LONG_DESC.equals(pFieldName)) {
            return getLongDesc();
        } else if (BusEntityDataAccess.EFF_DATE.equals(pFieldName)) {
            return getEffDate();
        } else if (BusEntityDataAccess.EXP_DATE.equals(pFieldName)) {
            return getExpDate();
        } else if (BusEntityDataAccess.BUS_ENTITY_TYPE_CD.equals(pFieldName)) {
            return getBusEntityTypeCd();
        } else if (BusEntityDataAccess.BUS_ENTITY_STATUS_CD.equals(pFieldName)) {
            return getBusEntityStatusCd();
        } else if (BusEntityDataAccess.WORKFLOW_ROLE_CD.equals(pFieldName)) {
            return getWorkflowRoleCd();
        } else if (BusEntityDataAccess.LOCALE_CD.equals(pFieldName)) {
            return getLocaleCd();
        } else if (BusEntityDataAccess.ERP_NUM.equals(pFieldName)) {
            return getErpNum();
        } else if (BusEntityDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (BusEntityDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (BusEntityDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (BusEntityDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (BusEntityDataAccess.TIME_ZONE_CD.equals(pFieldName)) {
            return getTimeZoneCd();
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
        return BusEntityDataAccess.CLW_BUS_ENTITY;
    }

    
    /**
     * Sets the BusEntityId field. This field is required to be set in the database.
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
     * Sets the EffDate field.
     *
     * @param pEffDate
     *  Date to use to update the field.
     */
    public void setEffDate(Date pEffDate){
        this.mEffDate = pEffDate;
        setDirty(true);
    }
    /**
     * Retrieves the EffDate field.
     *
     * @return
     *  Date containing the EffDate field.
     */
    public Date getEffDate(){
        return mEffDate;
    }

    /**
     * Sets the ExpDate field.
     *
     * @param pExpDate
     *  Date to use to update the field.
     */
    public void setExpDate(Date pExpDate){
        this.mExpDate = pExpDate;
        setDirty(true);
    }
    /**
     * Retrieves the ExpDate field.
     *
     * @return
     *  Date containing the ExpDate field.
     */
    public Date getExpDate(){
        return mExpDate;
    }

    /**
     * Sets the BusEntityTypeCd field. This field is required to be set in the database.
     *
     * @param pBusEntityTypeCd
     *  String to use to update the field.
     */
    public void setBusEntityTypeCd(String pBusEntityTypeCd){
        this.mBusEntityTypeCd = pBusEntityTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the BusEntityTypeCd field.
     *
     * @return
     *  String containing the BusEntityTypeCd field.
     */
    public String getBusEntityTypeCd(){
        return mBusEntityTypeCd;
    }

    /**
     * Sets the BusEntityStatusCd field. This field is required to be set in the database.
     *
     * @param pBusEntityStatusCd
     *  String to use to update the field.
     */
    public void setBusEntityStatusCd(String pBusEntityStatusCd){
        this.mBusEntityStatusCd = pBusEntityStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the BusEntityStatusCd field.
     *
     * @return
     *  String containing the BusEntityStatusCd field.
     */
    public String getBusEntityStatusCd(){
        return mBusEntityStatusCd;
    }

    /**
     * Sets the WorkflowRoleCd field. This field is required to be set in the database.
     *
     * @param pWorkflowRoleCd
     *  String to use to update the field.
     */
    public void setWorkflowRoleCd(String pWorkflowRoleCd){
        this.mWorkflowRoleCd = pWorkflowRoleCd;
        setDirty(true);
    }
    /**
     * Retrieves the WorkflowRoleCd field.
     *
     * @return
     *  String containing the WorkflowRoleCd field.
     */
    public String getWorkflowRoleCd(){
        return mWorkflowRoleCd;
    }

    /**
     * Sets the LocaleCd field. This field is required to be set in the database.
     *
     * @param pLocaleCd
     *  String to use to update the field.
     */
    public void setLocaleCd(String pLocaleCd){
        this.mLocaleCd = pLocaleCd;
        setDirty(true);
    }
    /**
     * Retrieves the LocaleCd field.
     *
     * @return
     *  String containing the LocaleCd field.
     */
    public String getLocaleCd(){
        return mLocaleCd;
    }

    /**
     * Sets the ErpNum field.
     *
     * @param pErpNum
     *  String to use to update the field.
     */
    public void setErpNum(String pErpNum){
        this.mErpNum = pErpNum;
        setDirty(true);
    }
    /**
     * Retrieves the ErpNum field.
     *
     * @return
     *  String containing the ErpNum field.
     */
    public String getErpNum(){
        return mErpNum;
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
     * Sets the TimeZoneCd field.
     *
     * @param pTimeZoneCd
     *  String to use to update the field.
     */
    public void setTimeZoneCd(String pTimeZoneCd){
        this.mTimeZoneCd = pTimeZoneCd;
        setDirty(true);
    }
    /**
     * Retrieves the TimeZoneCd field.
     *
     * @return
     *  String containing the TimeZoneCd field.
     */
    public String getTimeZoneCd(){
        return mTimeZoneCd;
    }


}
