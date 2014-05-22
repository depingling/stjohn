
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        SiteDeliveryData
 * Description:  This is a ValueObject class wrapping the database table CLW_SITE_DELIVERY.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.SiteDeliveryDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>SiteDeliveryData</code> is a ValueObject class wrapping of the database table CLW_SITE_DELIVERY.
 */
public class SiteDeliveryData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 1L;
    private int mSiteDeliveryId;// SQL type:NUMBER, not null
    private int mBusEntityId;// SQL type:NUMBER
    private String mCustMaj;// SQL type:VARCHAR2, not null
    private String mSiteRefNum;// SQL type:VARCHAR2, not null
    private int mYear;// SQL type:NUMBER, not null
    private int mWeek;// SQL type:NUMBER, not null
    private int mDeliveryDay;// SQL type:NUMBER, not null
    private String mStatusCd;// SQL type:VARCHAR2, not null
    private boolean mDeliveryFlag;// SQL type:NUMBER, not null
    private int mCutoffDay;// SQL type:NUMBER, not null
    private Date mCutoffSystemTime;// SQL type:DATE
    private Date mCutoffSiteTime;// SQL type:DATE
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private Date mDeliveryDate;// SQL type:DATE
    private String mCutoffSystemTimeSource;// SQL type:VARCHAR2
    private String mCutoffSiteTimeSource;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public SiteDeliveryData ()
    {
        mCustMaj = "";
        mSiteRefNum = "";
        mStatusCd = "";
        mAddBy = "";
        mModBy = "";
        mCutoffSystemTimeSource = "";
        mCutoffSiteTimeSource = "";
    }

    /**
     * Constructor.
     */
    public SiteDeliveryData(int parm1, int parm2, String parm3, String parm4, int parm5, int parm6, int parm7, String parm8, boolean parm9, int parm10, Date parm11, Date parm12, Date parm13, String parm14, Date parm15, String parm16, Date parm17, String parm18, String parm19)
    {
        mSiteDeliveryId = parm1;
        mBusEntityId = parm2;
        mCustMaj = parm3;
        mSiteRefNum = parm4;
        mYear = parm5;
        mWeek = parm6;
        mDeliveryDay = parm7;
        mStatusCd = parm8;
        mDeliveryFlag = parm9;
        mCutoffDay = parm10;
        mCutoffSystemTime = parm11;
        mCutoffSiteTime = parm12;
        mAddDate = parm13;
        mAddBy = parm14;
        mModDate = parm15;
        mModBy = parm16;
        mDeliveryDate = parm17;
        mCutoffSystemTimeSource = parm18;
        mCutoffSiteTimeSource = parm19;
        
    }

    /**
     * Creates a new SiteDeliveryData
     *
     * @return
     *  Newly initialized SiteDeliveryData object.
     */
    public static SiteDeliveryData createValue ()
    {
        SiteDeliveryData valueData = new SiteDeliveryData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this SiteDeliveryData object
     */
    public String toString()
    {
        return "[" + "SiteDeliveryId=" + mSiteDeliveryId + ", BusEntityId=" + mBusEntityId + ", CustMaj=" + mCustMaj + ", SiteRefNum=" + mSiteRefNum + ", Year=" + mYear + ", Week=" + mWeek + ", DeliveryDay=" + mDeliveryDay + ", StatusCd=" + mStatusCd + ", DeliveryFlag=" + mDeliveryFlag + ", CutoffDay=" + mCutoffDay + ", CutoffSystemTime=" + mCutoffSystemTime + ", CutoffSiteTime=" + mCutoffSiteTime + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", DeliveryDate=" + mDeliveryDate + ", CutoffSystemTimeSource=" + mCutoffSystemTimeSource + ", CutoffSiteTimeSource=" + mCutoffSiteTimeSource + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("SiteDelivery");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mSiteDeliveryId));

        node =  doc.createElement("BusEntityId");
        node.appendChild(doc.createTextNode(String.valueOf(mBusEntityId)));
        root.appendChild(node);

        node =  doc.createElement("CustMaj");
        node.appendChild(doc.createTextNode(String.valueOf(mCustMaj)));
        root.appendChild(node);

        node =  doc.createElement("SiteRefNum");
        node.appendChild(doc.createTextNode(String.valueOf(mSiteRefNum)));
        root.appendChild(node);

        node =  doc.createElement("Year");
        node.appendChild(doc.createTextNode(String.valueOf(mYear)));
        root.appendChild(node);

        node =  doc.createElement("Week");
        node.appendChild(doc.createTextNode(String.valueOf(mWeek)));
        root.appendChild(node);

        node =  doc.createElement("DeliveryDay");
        node.appendChild(doc.createTextNode(String.valueOf(mDeliveryDay)));
        root.appendChild(node);

        node =  doc.createElement("StatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mStatusCd)));
        root.appendChild(node);

        node =  doc.createElement("DeliveryFlag");
        node.appendChild(doc.createTextNode(String.valueOf(mDeliveryFlag)));
        root.appendChild(node);

        node =  doc.createElement("CutoffDay");
        node.appendChild(doc.createTextNode(String.valueOf(mCutoffDay)));
        root.appendChild(node);

        node =  doc.createElement("CutoffSystemTime");
        node.appendChild(doc.createTextNode(String.valueOf(mCutoffSystemTime)));
        root.appendChild(node);

        node =  doc.createElement("CutoffSiteTime");
        node.appendChild(doc.createTextNode(String.valueOf(mCutoffSiteTime)));
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

        node =  doc.createElement("DeliveryDate");
        node.appendChild(doc.createTextNode(String.valueOf(mDeliveryDate)));
        root.appendChild(node);

        node =  doc.createElement("CutoffSystemTimeSource");
        node.appendChild(doc.createTextNode(String.valueOf(mCutoffSystemTimeSource)));
        root.appendChild(node);

        node =  doc.createElement("CutoffSiteTimeSource");
        node.appendChild(doc.createTextNode(String.valueOf(mCutoffSiteTimeSource)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the SiteDeliveryId field is not cloned.
    *
    * @return SiteDeliveryData object
    */
    public Object clone(){
        SiteDeliveryData myClone = new SiteDeliveryData();
        
        myClone.mBusEntityId = mBusEntityId;
        
        myClone.mCustMaj = mCustMaj;
        
        myClone.mSiteRefNum = mSiteRefNum;
        
        myClone.mYear = mYear;
        
        myClone.mWeek = mWeek;
        
        myClone.mDeliveryDay = mDeliveryDay;
        
        myClone.mStatusCd = mStatusCd;
        
        myClone.mDeliveryFlag = mDeliveryFlag;
        
        myClone.mCutoffDay = mCutoffDay;
        
        if(mCutoffSystemTime != null){
                myClone.mCutoffSystemTime = (Date) mCutoffSystemTime.clone();
        }
        
        if(mCutoffSiteTime != null){
                myClone.mCutoffSiteTime = (Date) mCutoffSiteTime.clone();
        }
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        if(mDeliveryDate != null){
                myClone.mDeliveryDate = (Date) mDeliveryDate.clone();
        }
        
        myClone.mCutoffSystemTimeSource = mCutoffSystemTimeSource;
        
        myClone.mCutoffSiteTimeSource = mCutoffSiteTimeSource;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (SiteDeliveryDataAccess.SITE_DELIVERY_ID.equals(pFieldName)) {
            return getSiteDeliveryId();
        } else if (SiteDeliveryDataAccess.BUS_ENTITY_ID.equals(pFieldName)) {
            return getBusEntityId();
        } else if (SiteDeliveryDataAccess.CUST_MAJ.equals(pFieldName)) {
            return getCustMaj();
        } else if (SiteDeliveryDataAccess.SITE_REF_NUM.equals(pFieldName)) {
            return getSiteRefNum();
        } else if (SiteDeliveryDataAccess.YEAR.equals(pFieldName)) {
            return getYear();
        } else if (SiteDeliveryDataAccess.WEEK.equals(pFieldName)) {
            return getWeek();
        } else if (SiteDeliveryDataAccess.DELIVERY_DAY.equals(pFieldName)) {
            return getDeliveryDay();
        } else if (SiteDeliveryDataAccess.STATUS_CD.equals(pFieldName)) {
            return getStatusCd();
        } else if (SiteDeliveryDataAccess.DELIVERY_FLAG.equals(pFieldName)) {
            return getDeliveryFlag();
        } else if (SiteDeliveryDataAccess.CUTOFF_DAY.equals(pFieldName)) {
            return getCutoffDay();
        } else if (SiteDeliveryDataAccess.CUTOFF_SYSTEM_TIME.equals(pFieldName)) {
            return getCutoffSystemTime();
        } else if (SiteDeliveryDataAccess.CUTOFF_SITE_TIME.equals(pFieldName)) {
            return getCutoffSiteTime();
        } else if (SiteDeliveryDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (SiteDeliveryDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (SiteDeliveryDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (SiteDeliveryDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (SiteDeliveryDataAccess.DELIVERY_DATE.equals(pFieldName)) {
            return getDeliveryDate();
        } else if (SiteDeliveryDataAccess.CUTOFF_SYSTEM_TIME_SOURCE.equals(pFieldName)) {
            return getCutoffSystemTimeSource();
        } else if (SiteDeliveryDataAccess.CUTOFF_SITE_TIME_SOURCE.equals(pFieldName)) {
            return getCutoffSiteTimeSource();
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
        return SiteDeliveryDataAccess.CLW_SITE_DELIVERY;
    }

    
    /**
     * Sets the SiteDeliveryId field. This field is required to be set in the database.
     *
     * @param pSiteDeliveryId
     *  int to use to update the field.
     */
    public void setSiteDeliveryId(int pSiteDeliveryId){
        this.mSiteDeliveryId = pSiteDeliveryId;
        setDirty(true);
    }
    /**
     * Retrieves the SiteDeliveryId field.
     *
     * @return
     *  int containing the SiteDeliveryId field.
     */
    public int getSiteDeliveryId(){
        return mSiteDeliveryId;
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
     * Sets the CustMaj field. This field is required to be set in the database.
     *
     * @param pCustMaj
     *  String to use to update the field.
     */
    public void setCustMaj(String pCustMaj){
        this.mCustMaj = pCustMaj;
        setDirty(true);
    }
    /**
     * Retrieves the CustMaj field.
     *
     * @return
     *  String containing the CustMaj field.
     */
    public String getCustMaj(){
        return mCustMaj;
    }

    /**
     * Sets the SiteRefNum field. This field is required to be set in the database.
     *
     * @param pSiteRefNum
     *  String to use to update the field.
     */
    public void setSiteRefNum(String pSiteRefNum){
        this.mSiteRefNum = pSiteRefNum;
        setDirty(true);
    }
    /**
     * Retrieves the SiteRefNum field.
     *
     * @return
     *  String containing the SiteRefNum field.
     */
    public String getSiteRefNum(){
        return mSiteRefNum;
    }

    /**
     * Sets the Year field. This field is required to be set in the database.
     *
     * @param pYear
     *  int to use to update the field.
     */
    public void setYear(int pYear){
        this.mYear = pYear;
        setDirty(true);
    }
    /**
     * Retrieves the Year field.
     *
     * @return
     *  int containing the Year field.
     */
    public int getYear(){
        return mYear;
    }

    /**
     * Sets the Week field. This field is required to be set in the database.
     *
     * @param pWeek
     *  int to use to update the field.
     */
    public void setWeek(int pWeek){
        this.mWeek = pWeek;
        setDirty(true);
    }
    /**
     * Retrieves the Week field.
     *
     * @return
     *  int containing the Week field.
     */
    public int getWeek(){
        return mWeek;
    }

    /**
     * Sets the DeliveryDay field. This field is required to be set in the database.
     *
     * @param pDeliveryDay
     *  int to use to update the field.
     */
    public void setDeliveryDay(int pDeliveryDay){
        this.mDeliveryDay = pDeliveryDay;
        setDirty(true);
    }
    /**
     * Retrieves the DeliveryDay field.
     *
     * @return
     *  int containing the DeliveryDay field.
     */
    public int getDeliveryDay(){
        return mDeliveryDay;
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
     * Sets the DeliveryFlag field. This field is required to be set in the database.
     *
     * @param pDeliveryFlag
     *  boolean to use to update the field.
     */
    public void setDeliveryFlag(boolean pDeliveryFlag){
        this.mDeliveryFlag = pDeliveryFlag;
        setDirty(true);
    }
    /**
     * Retrieves the DeliveryFlag field.
     *
     * @return
     *  boolean containing the DeliveryFlag field.
     */
    public boolean getDeliveryFlag(){
        return mDeliveryFlag;
    }

    /**
     * Sets the CutoffDay field. This field is required to be set in the database.
     *
     * @param pCutoffDay
     *  int to use to update the field.
     */
    public void setCutoffDay(int pCutoffDay){
        this.mCutoffDay = pCutoffDay;
        setDirty(true);
    }
    /**
     * Retrieves the CutoffDay field.
     *
     * @return
     *  int containing the CutoffDay field.
     */
    public int getCutoffDay(){
        return mCutoffDay;
    }

    /**
     * Sets the CutoffSystemTime field.
     *
     * @param pCutoffSystemTime
     *  Date to use to update the field.
     */
    public void setCutoffSystemTime(Date pCutoffSystemTime){
        this.mCutoffSystemTime = pCutoffSystemTime;
        setDirty(true);
    }
    /**
     * Retrieves the CutoffSystemTime field.
     *
     * @return
     *  Date containing the CutoffSystemTime field.
     */
    public Date getCutoffSystemTime(){
        return mCutoffSystemTime;
    }

    /**
     * Sets the CutoffSiteTime field.
     *
     * @param pCutoffSiteTime
     *  Date to use to update the field.
     */
    public void setCutoffSiteTime(Date pCutoffSiteTime){
        this.mCutoffSiteTime = pCutoffSiteTime;
        setDirty(true);
    }
    /**
     * Retrieves the CutoffSiteTime field.
     *
     * @return
     *  Date containing the CutoffSiteTime field.
     */
    public Date getCutoffSiteTime(){
        return mCutoffSiteTime;
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
     * Sets the DeliveryDate field.
     *
     * @param pDeliveryDate
     *  Date to use to update the field.
     */
    public void setDeliveryDate(Date pDeliveryDate){
        this.mDeliveryDate = pDeliveryDate;
        setDirty(true);
    }
    /**
     * Retrieves the DeliveryDate field.
     *
     * @return
     *  Date containing the DeliveryDate field.
     */
    public Date getDeliveryDate(){
        return mDeliveryDate;
    }

    /**
     * Sets the CutoffSystemTimeSource field.
     *
     * @param pCutoffSystemTimeSource
     *  String to use to update the field.
     */
    public void setCutoffSystemTimeSource(String pCutoffSystemTimeSource){
        this.mCutoffSystemTimeSource = pCutoffSystemTimeSource;
        setDirty(true);
    }
    /**
     * Retrieves the CutoffSystemTimeSource field.
     *
     * @return
     *  String containing the CutoffSystemTimeSource field.
     */
    public String getCutoffSystemTimeSource(){
        return mCutoffSystemTimeSource;
    }

    /**
     * Sets the CutoffSiteTimeSource field.
     *
     * @param pCutoffSiteTimeSource
     *  String to use to update the field.
     */
    public void setCutoffSiteTimeSource(String pCutoffSiteTimeSource){
        this.mCutoffSiteTimeSource = pCutoffSiteTimeSource;
        setDirty(true);
    }
    /**
     * Retrieves the CutoffSiteTimeSource field.
     *
     * @return
     *  String containing the CutoffSiteTimeSource field.
     */
    public String getCutoffSiteTimeSource(){
        return mCutoffSiteTimeSource;
    }


}
