
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        FiscalCalenderDetailData
 * Description:  This is a ValueObject class wrapping the database table CLW_FISCAL_CALENDER_DETAIL.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.FiscalCalenderDetailDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>FiscalCalenderDetailData</code> is a ValueObject class wrapping of the database table CLW_FISCAL_CALENDER_DETAIL.
 */
public class FiscalCalenderDetailData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -1L;
    private int mFiscalCalenderDetailId;// SQL type:NUMBER, not null
    private int mFiscalCalenderId;// SQL type:NUMBER, not null
    private int mPeriod;// SQL type:NUMBER, not null
    private String mMmdd;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public FiscalCalenderDetailData ()
    {
        mMmdd = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public FiscalCalenderDetailData(int parm1, int parm2, int parm3, String parm4, Date parm5, String parm6, Date parm7, String parm8)
    {
        mFiscalCalenderDetailId = parm1;
        mFiscalCalenderId = parm2;
        mPeriod = parm3;
        mMmdd = parm4;
        mAddDate = parm5;
        mAddBy = parm6;
        mModDate = parm7;
        mModBy = parm8;
        
    }

    /**
     * Creates a new FiscalCalenderDetailData
     *
     * @return
     *  Newly initialized FiscalCalenderDetailData object.
     */
    public static FiscalCalenderDetailData createValue ()
    {
        FiscalCalenderDetailData valueData = new FiscalCalenderDetailData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this FiscalCalenderDetailData object
     */
    public String toString()
    {
        return "[" + "FiscalCalenderDetailId=" + mFiscalCalenderDetailId + ", FiscalCalenderId=" + mFiscalCalenderId + ", Period=" + mPeriod + ", Mmdd=" + mMmdd + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("FiscalCalenderDetail");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mFiscalCalenderDetailId));

        node =  doc.createElement("FiscalCalenderId");
        node.appendChild(doc.createTextNode(String.valueOf(mFiscalCalenderId)));
        root.appendChild(node);

        node =  doc.createElement("Period");
        node.appendChild(doc.createTextNode(String.valueOf(mPeriod)));
        root.appendChild(node);

        node =  doc.createElement("Mmdd");
        node.appendChild(doc.createTextNode(String.valueOf(mMmdd)));
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
    * creates a clone of this object, the FiscalCalenderDetailId field is not cloned.
    *
    * @return FiscalCalenderDetailData object
    */
    public Object clone(){
        FiscalCalenderDetailData myClone = new FiscalCalenderDetailData();
        
        myClone.mFiscalCalenderId = mFiscalCalenderId;
        
        myClone.mPeriod = mPeriod;
        
        myClone.mMmdd = mMmdd;
        
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

        if (FiscalCalenderDetailDataAccess.FISCAL_CALENDER_DETAIL_ID.equals(pFieldName)) {
            return getFiscalCalenderDetailId();
        } else if (FiscalCalenderDetailDataAccess.FISCAL_CALENDER_ID.equals(pFieldName)) {
            return getFiscalCalenderId();
        } else if (FiscalCalenderDetailDataAccess.PERIOD.equals(pFieldName)) {
            return getPeriod();
        } else if (FiscalCalenderDetailDataAccess.MMDD.equals(pFieldName)) {
            return getMmdd();
        } else if (FiscalCalenderDetailDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (FiscalCalenderDetailDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (FiscalCalenderDetailDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (FiscalCalenderDetailDataAccess.MOD_BY.equals(pFieldName)) {
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
        return FiscalCalenderDetailDataAccess.CLW_FISCAL_CALENDER_DETAIL;
    }

    
    /**
     * Sets the FiscalCalenderDetailId field. This field is required to be set in the database.
     *
     * @param pFiscalCalenderDetailId
     *  int to use to update the field.
     */
    public void setFiscalCalenderDetailId(int pFiscalCalenderDetailId){
        this.mFiscalCalenderDetailId = pFiscalCalenderDetailId;
        setDirty(true);
    }
    /**
     * Retrieves the FiscalCalenderDetailId field.
     *
     * @return
     *  int containing the FiscalCalenderDetailId field.
     */
    public int getFiscalCalenderDetailId(){
        return mFiscalCalenderDetailId;
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
     * Sets the Mmdd field.
     *
     * @param pMmdd
     *  String to use to update the field.
     */
    public void setMmdd(String pMmdd){
        this.mMmdd = pMmdd;
        setDirty(true);
    }
    /**
     * Retrieves the Mmdd field.
     *
     * @return
     *  String containing the Mmdd field.
     */
    public String getMmdd(){
        return mMmdd;
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
