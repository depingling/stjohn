
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        FiscalCalenderData
 * Description:  This is a ValueObject class wrapping the database table CLW_FISCAL_CALENDER.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.FiscalCalenderDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>FiscalCalenderData</code> is a ValueObject class wrapping of the database table CLW_FISCAL_CALENDER.
 */
public class FiscalCalenderData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 4607962068270997240L;
    private int mFiscalCalenderId;// SQL type:NUMBER, not null
    private int mBusEntityId;// SQL type:NUMBER, not null
    private String mShortDesc;// SQL type:VARCHAR2, not null
    private String mPeriodCd;// SQL type:VARCHAR2, not null
    private Date mEffDate;// SQL type:DATE, not null
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private int mFiscalYear;// SQL type:NUMBER

    /**
     * Constructor.
     */
    public FiscalCalenderData ()
    {
        mShortDesc = "";
        mPeriodCd = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public FiscalCalenderData(int parm1, int parm2, String parm3, String parm4, Date parm5, Date parm6, String parm7, Date parm8, String parm9, int parm10)
    {
        mFiscalCalenderId = parm1;
        mBusEntityId = parm2;
        mShortDesc = parm3;
        mPeriodCd = parm4;
        mEffDate = parm5;
        mAddDate = parm6;
        mAddBy = parm7;
        mModDate = parm8;
        mModBy = parm9;
        mFiscalYear = parm10;
        
    }

    /**
     * Creates a new FiscalCalenderData
     *
     * @return
     *  Newly initialized FiscalCalenderData object.
     */
    public static FiscalCalenderData createValue ()
    {
        FiscalCalenderData valueData = new FiscalCalenderData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this FiscalCalenderData object
     */
    public String toString()
    {
        return "[" + "FiscalCalenderId=" + mFiscalCalenderId + ", BusEntityId=" + mBusEntityId + ", ShortDesc=" + mShortDesc + ", PeriodCd=" + mPeriodCd + ", EffDate=" + mEffDate + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", FiscalYear=" + mFiscalYear + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("FiscalCalender");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mFiscalCalenderId));

        node =  doc.createElement("BusEntityId");
        node.appendChild(doc.createTextNode(String.valueOf(mBusEntityId)));
        root.appendChild(node);

        node =  doc.createElement("ShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mShortDesc)));
        root.appendChild(node);

        node =  doc.createElement("PeriodCd");
        node.appendChild(doc.createTextNode(String.valueOf(mPeriodCd)));
        root.appendChild(node);

        node =  doc.createElement("EffDate");
        node.appendChild(doc.createTextNode(String.valueOf(mEffDate)));
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

        node =  doc.createElement("FiscalYear");
        node.appendChild(doc.createTextNode(String.valueOf(mFiscalYear)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the FiscalCalenderId field is not cloned.
    *
    * @return FiscalCalenderData object
    */
    public Object clone(){
        FiscalCalenderData myClone = new FiscalCalenderData();
        
        myClone.mBusEntityId = mBusEntityId;
        
        myClone.mShortDesc = mShortDesc;
        
        myClone.mPeriodCd = mPeriodCd;
        
        if(mEffDate != null){
                myClone.mEffDate = (Date) mEffDate.clone();
        }
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        myClone.mFiscalYear = mFiscalYear;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (FiscalCalenderDataAccess.FISCAL_CALENDER_ID.equals(pFieldName)) {
            return getFiscalCalenderId();
        } else if (FiscalCalenderDataAccess.BUS_ENTITY_ID.equals(pFieldName)) {
            return getBusEntityId();
        } else if (FiscalCalenderDataAccess.SHORT_DESC.equals(pFieldName)) {
            return getShortDesc();
        } else if (FiscalCalenderDataAccess.PERIOD_CD.equals(pFieldName)) {
            return getPeriodCd();
        } else if (FiscalCalenderDataAccess.EFF_DATE.equals(pFieldName)) {
            return getEffDate();
        } else if (FiscalCalenderDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (FiscalCalenderDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (FiscalCalenderDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (FiscalCalenderDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (FiscalCalenderDataAccess.FISCAL_YEAR.equals(pFieldName)) {
            return getFiscalYear();
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
        return FiscalCalenderDataAccess.CLW_FISCAL_CALENDER;
    }

    
    /**
     * Sets the FiscalCalenderId field. This field is required to be set in the database.
     *
     * @param pFiscalCalenderId
     *  int to use to update the field.
     */
    public void setFiscalCalenderId(int pFiscalCalenderId){
        this.mFiscalCalenderId = pFiscalCalenderId;
        setDirty(true);
    }
    /**
     * Retrieves the FiscalCalenderId field.
     *
     * @return
     *  int containing the FiscalCalenderId field.
     */
    public int getFiscalCalenderId(){
        return mFiscalCalenderId;
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
     * Sets the PeriodCd field. This field is required to be set in the database.
     *
     * @param pPeriodCd
     *  String to use to update the field.
     */
    public void setPeriodCd(String pPeriodCd){
        this.mPeriodCd = pPeriodCd;
        setDirty(true);
    }
    /**
     * Retrieves the PeriodCd field.
     *
     * @return
     *  String containing the PeriodCd field.
     */
    public String getPeriodCd(){
        return mPeriodCd;
    }

    /**
     * Sets the EffDate field. This field is required to be set in the database.
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
     * Sets the FiscalYear field.
     *
     * @param pFiscalYear
     *  int to use to update the field.
     */
    public void setFiscalYear(int pFiscalYear){
        this.mFiscalYear = pFiscalYear;
        setDirty(true);
    }
    /**
     * Retrieves the FiscalYear field.
     *
     * @return
     *  int containing the FiscalYear field.
     */
    public int getFiscalYear(){
        return mFiscalYear;
    }


}
