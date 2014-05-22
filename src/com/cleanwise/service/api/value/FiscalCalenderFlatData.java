
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        FiscalCalenderFlatData
 * Description:  This is a ValueObject class wrapping the database table CLW_FISCAL_CALENDER_FLAT.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.FiscalCalenderFlatDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>FiscalCalenderFlatData</code> is a ValueObject class wrapping of the database table CLW_FISCAL_CALENDER_FLAT.
 */
public class FiscalCalenderFlatData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 6485594084921203249L;
    private int mFiscalCalenderFlatId;// SQL type:NUMBER, not null
    private int mFiscalCalenderId;// SQL type:NUMBER, not null
    private int mBusEntityId;// SQL type:NUMBER, not null
    private Date mStartDate;// SQL type:DATE, not null
    private Date mEndDate;// SQL type:DATE, not null
    private int mPeriod;// SQL type:NUMBER, not null
    private int mFiscalYear;// SQL type:NUMBER, not null

    /**
     * Constructor.
     */
    public FiscalCalenderFlatData ()
    {
    }

    /**
     * Constructor.
     */
    public FiscalCalenderFlatData(int parm1, int parm2, int parm3, Date parm4, Date parm5, int parm6, int parm7)
    {
        mFiscalCalenderFlatId = parm1;
        mFiscalCalenderId = parm2;
        mBusEntityId = parm3;
        mStartDate = parm4;
        mEndDate = parm5;
        mPeriod = parm6;
        mFiscalYear = parm7;
        
    }

    /**
     * Creates a new FiscalCalenderFlatData
     *
     * @return
     *  Newly initialized FiscalCalenderFlatData object.
     */
    public static FiscalCalenderFlatData createValue ()
    {
        FiscalCalenderFlatData valueData = new FiscalCalenderFlatData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this FiscalCalenderFlatData object
     */
    public String toString()
    {
        return "[" + "FiscalCalenderFlatId=" + mFiscalCalenderFlatId + ", FiscalCalenderId=" + mFiscalCalenderId + ", BusEntityId=" + mBusEntityId + ", StartDate=" + mStartDate + ", EndDate=" + mEndDate + ", Period=" + mPeriod + ", FiscalYear=" + mFiscalYear + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("FiscalCalenderFlat");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mFiscalCalenderFlatId));

        node =  doc.createElement("FiscalCalenderId");
        node.appendChild(doc.createTextNode(String.valueOf(mFiscalCalenderId)));
        root.appendChild(node);

        node =  doc.createElement("BusEntityId");
        node.appendChild(doc.createTextNode(String.valueOf(mBusEntityId)));
        root.appendChild(node);

        node =  doc.createElement("StartDate");
        node.appendChild(doc.createTextNode(String.valueOf(mStartDate)));
        root.appendChild(node);

        node =  doc.createElement("EndDate");
        node.appendChild(doc.createTextNode(String.valueOf(mEndDate)));
        root.appendChild(node);

        node =  doc.createElement("Period");
        node.appendChild(doc.createTextNode(String.valueOf(mPeriod)));
        root.appendChild(node);

        node =  doc.createElement("FiscalYear");
        node.appendChild(doc.createTextNode(String.valueOf(mFiscalYear)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the FiscalCalenderFlatId field is not cloned.
    *
    * @return FiscalCalenderFlatData object
    */
    public Object clone(){
        FiscalCalenderFlatData myClone = new FiscalCalenderFlatData();
        
        myClone.mFiscalCalenderId = mFiscalCalenderId;
        
        myClone.mBusEntityId = mBusEntityId;
        
        if(mStartDate != null){
                myClone.mStartDate = (Date) mStartDate.clone();
        }
        
        if(mEndDate != null){
                myClone.mEndDate = (Date) mEndDate.clone();
        }
        
        myClone.mPeriod = mPeriod;
        
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

        if (FiscalCalenderFlatDataAccess.FISCAL_CALENDER_FLAT_ID.equals(pFieldName)) {
            return getFiscalCalenderFlatId();
        } else if (FiscalCalenderFlatDataAccess.FISCAL_CALENDER_ID.equals(pFieldName)) {
            return getFiscalCalenderId();
        } else if (FiscalCalenderFlatDataAccess.BUS_ENTITY_ID.equals(pFieldName)) {
            return getBusEntityId();
        } else if (FiscalCalenderFlatDataAccess.START_DATE.equals(pFieldName)) {
            return getStartDate();
        } else if (FiscalCalenderFlatDataAccess.END_DATE.equals(pFieldName)) {
            return getEndDate();
        } else if (FiscalCalenderFlatDataAccess.PERIOD.equals(pFieldName)) {
            return getPeriod();
        } else if (FiscalCalenderFlatDataAccess.FISCAL_YEAR.equals(pFieldName)) {
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
        return FiscalCalenderFlatDataAccess.CLW_FISCAL_CALENDER_FLAT;
    }

    
    /**
     * Sets the FiscalCalenderFlatId field. This field is required to be set in the database.
     *
     * @param pFiscalCalenderFlatId
     *  int to use to update the field.
     */
    public void setFiscalCalenderFlatId(int pFiscalCalenderFlatId){
        this.mFiscalCalenderFlatId = pFiscalCalenderFlatId;
        setDirty(true);
    }
    /**
     * Retrieves the FiscalCalenderFlatId field.
     *
     * @return
     *  int containing the FiscalCalenderFlatId field.
     */
    public int getFiscalCalenderFlatId(){
        return mFiscalCalenderFlatId;
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
     * Sets the StartDate field. This field is required to be set in the database.
     *
     * @param pStartDate
     *  Date to use to update the field.
     */
    public void setStartDate(Date pStartDate){
        this.mStartDate = pStartDate;
        setDirty(true);
    }
    /**
     * Retrieves the StartDate field.
     *
     * @return
     *  Date containing the StartDate field.
     */
    public Date getStartDate(){
        return mStartDate;
    }

    /**
     * Sets the EndDate field. This field is required to be set in the database.
     *
     * @param pEndDate
     *  Date to use to update the field.
     */
    public void setEndDate(Date pEndDate){
        this.mEndDate = pEndDate;
        setDirty(true);
    }
    /**
     * Retrieves the EndDate field.
     *
     * @return
     *  Date containing the EndDate field.
     */
    public Date getEndDate(){
        return mEndDate;
    }

    /**
     * Sets the Period field. This field is required to be set in the database.
     *
     * @param pPeriod
     *  int to use to update the field.
     */
    public void setPeriod(int pPeriod){
        this.mPeriod = pPeriod;
        setDirty(true);
    }
    /**
     * Retrieves the Period field.
     *
     * @return
     *  int containing the Period field.
     */
    public int getPeriod(){
        return mPeriod;
    }

    /**
     * Sets the FiscalYear field. This field is required to be set in the database.
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
