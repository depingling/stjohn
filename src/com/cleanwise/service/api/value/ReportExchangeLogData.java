
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ReportExchangeLogData
 * Description:  This is a ValueObject class wrapping the database table CLW_REPORT_EXCHANGE_LOG.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.ReportExchangeLogDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>ReportExchangeLogData</code> is a ValueObject class wrapping of the database table CLW_REPORT_EXCHANGE_LOG.
 */
public class ReportExchangeLogData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 7946439439441154473L;
    private int mReportExchangeLogId;// SQL type:NUMBER, not null
    private int mGenericReportId;// SQL type:NUMBER
    private String mRecordKey;// SQL type:VARCHAR2
    private String mValue;// SQL type:VARCHAR2
    private Date mSentDate;// SQL type:DATE
    private Date mAddDate;// SQL type:DATE
    private Date mModDate;// SQL type:DATE

    /**
     * Constructor.
     */
    public ReportExchangeLogData ()
    {
        mRecordKey = "";
        mValue = "";
    }

    /**
     * Constructor.
     */
    public ReportExchangeLogData(int parm1, int parm2, String parm3, String parm4, Date parm5, Date parm6, Date parm7)
    {
        mReportExchangeLogId = parm1;
        mGenericReportId = parm2;
        mRecordKey = parm3;
        mValue = parm4;
        mSentDate = parm5;
        mAddDate = parm6;
        mModDate = parm7;
        
    }

    /**
     * Creates a new ReportExchangeLogData
     *
     * @return
     *  Newly initialized ReportExchangeLogData object.
     */
    public static ReportExchangeLogData createValue ()
    {
        ReportExchangeLogData valueData = new ReportExchangeLogData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ReportExchangeLogData object
     */
    public String toString()
    {
        return "[" + "ReportExchangeLogId=" + mReportExchangeLogId + ", GenericReportId=" + mGenericReportId + ", RecordKey=" + mRecordKey + ", Value=" + mValue + ", SentDate=" + mSentDate + ", AddDate=" + mAddDate + ", ModDate=" + mModDate + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("ReportExchangeLog");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mReportExchangeLogId));

        node =  doc.createElement("GenericReportId");
        node.appendChild(doc.createTextNode(String.valueOf(mGenericReportId)));
        root.appendChild(node);

        node =  doc.createElement("RecordKey");
        node.appendChild(doc.createTextNode(String.valueOf(mRecordKey)));
        root.appendChild(node);

        node =  doc.createElement("Value");
        node.appendChild(doc.createTextNode(String.valueOf(mValue)));
        root.appendChild(node);

        node =  doc.createElement("SentDate");
        node.appendChild(doc.createTextNode(String.valueOf(mSentDate)));
        root.appendChild(node);

        node =  doc.createElement("AddDate");
        node.appendChild(doc.createTextNode(String.valueOf(mAddDate)));
        root.appendChild(node);

        node =  doc.createElement("ModDate");
        node.appendChild(doc.createTextNode(String.valueOf(mModDate)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the ReportExchangeLogId field is not cloned.
    *
    * @return ReportExchangeLogData object
    */
    public Object clone(){
        ReportExchangeLogData myClone = new ReportExchangeLogData();
        
        myClone.mGenericReportId = mGenericReportId;
        
        myClone.mRecordKey = mRecordKey;
        
        myClone.mValue = mValue;
        
        if(mSentDate != null){
                myClone.mSentDate = (Date) mSentDate.clone();
        }
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (ReportExchangeLogDataAccess.REPORT_EXCHANGE_LOG_ID.equals(pFieldName)) {
            return getReportExchangeLogId();
        } else if (ReportExchangeLogDataAccess.GENERIC_REPORT_ID.equals(pFieldName)) {
            return getGenericReportId();
        } else if (ReportExchangeLogDataAccess.RECORD_KEY.equals(pFieldName)) {
            return getRecordKey();
        } else if (ReportExchangeLogDataAccess.CLW_VALUE.equals(pFieldName)) {
            return getValue();
        } else if (ReportExchangeLogDataAccess.SENT_DATE.equals(pFieldName)) {
            return getSentDate();
        } else if (ReportExchangeLogDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (ReportExchangeLogDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
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
        return ReportExchangeLogDataAccess.CLW_REPORT_EXCHANGE_LOG;
    }

    
    /**
     * Sets the ReportExchangeLogId field. This field is required to be set in the database.
     *
     * @param pReportExchangeLogId
     *  int to use to update the field.
     */
    public void setReportExchangeLogId(int pReportExchangeLogId){
        this.mReportExchangeLogId = pReportExchangeLogId;
        setDirty(true);
    }
    /**
     * Retrieves the ReportExchangeLogId field.
     *
     * @return
     *  int containing the ReportExchangeLogId field.
     */
    public int getReportExchangeLogId(){
        return mReportExchangeLogId;
    }

    /**
     * Sets the GenericReportId field.
     *
     * @param pGenericReportId
     *  int to use to update the field.
     */
    public void setGenericReportId(int pGenericReportId){
        this.mGenericReportId = pGenericReportId;
        setDirty(true);
    }
    /**
     * Retrieves the GenericReportId field.
     *
     * @return
     *  int containing the GenericReportId field.
     */
    public int getGenericReportId(){
        return mGenericReportId;
    }

    /**
     * Sets the RecordKey field.
     *
     * @param pRecordKey
     *  String to use to update the field.
     */
    public void setRecordKey(String pRecordKey){
        this.mRecordKey = pRecordKey;
        setDirty(true);
    }
    /**
     * Retrieves the RecordKey field.
     *
     * @return
     *  String containing the RecordKey field.
     */
    public String getRecordKey(){
        return mRecordKey;
    }

    /**
     * Sets the Value field.
     *
     * @param pValue
     *  String to use to update the field.
     */
    public void setValue(String pValue){
        this.mValue = pValue;
        setDirty(true);
    }
    /**
     * Retrieves the Value field.
     *
     * @return
     *  String containing the Value field.
     */
    public String getValue(){
        return mValue;
    }

    /**
     * Sets the SentDate field.
     *
     * @param pSentDate
     *  Date to use to update the field.
     */
    public void setSentDate(Date pSentDate){
        this.mSentDate = pSentDate;
        setDirty(true);
    }
    /**
     * Retrieves the SentDate field.
     *
     * @return
     *  Date containing the SentDate field.
     */
    public Date getSentDate(){
        return mSentDate;
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


}
