
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        LoggingData
 * Description:  This is a ValueObject class wrapping the database table CLW_LOGGING.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.LoggingDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>LoggingData</code> is a ValueObject class wrapping of the database table CLW_LOGGING.
 */
public class LoggingData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 1L;
    private int mLoggingId;// SQL type:NUMBER, not null
    private int mParentId;// SQL type:NUMBER
    private String mPrio;// SQL type:VARCHAR2
    private int mIprio;// SQL type:NUMBER
    private String mCategory;// SQL type:VARCHAR2
    private String mThread;// SQL type:VARCHAR2
    private String mMessage;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE
    private String mModBy;// SQL type:VARCHAR2
    private String mThrowable;// SQL type:CLOB
    private int mProcessId;// SQL type:NUMBER

    /**
     * Constructor.
     */
    public LoggingData ()
    {
        mPrio = "";
        mCategory = "";
        mThread = "";
        mMessage = "";
        mAddBy = "";
        mModBy = "";
        mThrowable = "";
    }

    /**
     * Constructor.
     */
    public LoggingData(int parm1, int parm2, String parm3, int parm4, String parm5, String parm6, String parm7, Date parm8, String parm9, Date parm10, String parm11, String parm12, int parm13)
    {
        mLoggingId = parm1;
        mParentId = parm2;
        mPrio = parm3;
        mIprio = parm4;
        mCategory = parm5;
        mThread = parm6;
        mMessage = parm7;
        mAddDate = parm8;
        mAddBy = parm9;
        mModDate = parm10;
        mModBy = parm11;
        mThrowable = parm12;
        mProcessId = parm13;
        
    }

    /**
     * Creates a new LoggingData
     *
     * @return
     *  Newly initialized LoggingData object.
     */
    public static LoggingData createValue ()
    {
        LoggingData valueData = new LoggingData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this LoggingData object
     */
    public String toString()
    {
        return "[" + "LoggingId=" + mLoggingId + ", ParentId=" + mParentId + ", Prio=" + mPrio + ", Iprio=" + mIprio + ", Category=" + mCategory + ", Thread=" + mThread + ", Message=" + mMessage + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", Throwable=" + mThrowable + ", ProcessId=" + mProcessId + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("Logging");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mLoggingId));

        node =  doc.createElement("ParentId");
        node.appendChild(doc.createTextNode(String.valueOf(mParentId)));
        root.appendChild(node);

        node =  doc.createElement("Prio");
        node.appendChild(doc.createTextNode(String.valueOf(mPrio)));
        root.appendChild(node);

        node =  doc.createElement("Iprio");
        node.appendChild(doc.createTextNode(String.valueOf(mIprio)));
        root.appendChild(node);

        node =  doc.createElement("Category");
        node.appendChild(doc.createTextNode(String.valueOf(mCategory)));
        root.appendChild(node);

        node =  doc.createElement("Thread");
        node.appendChild(doc.createTextNode(String.valueOf(mThread)));
        root.appendChild(node);

        node =  doc.createElement("Message");
        node.appendChild(doc.createTextNode(String.valueOf(mMessage)));
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

        node =  doc.createElement("Throwable");
        node.appendChild(doc.createTextNode(String.valueOf(mThrowable)));
        root.appendChild(node);

        node =  doc.createElement("ProcessId");
        node.appendChild(doc.createTextNode(String.valueOf(mProcessId)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the LoggingId field is not cloned.
    *
    * @return LoggingData object
    */
    public Object clone(){
        LoggingData myClone = new LoggingData();
        
        myClone.mParentId = mParentId;
        
        myClone.mPrio = mPrio;
        
        myClone.mIprio = mIprio;
        
        myClone.mCategory = mCategory;
        
        myClone.mThread = mThread;
        
        myClone.mMessage = mMessage;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        myClone.mThrowable = mThrowable;
        
        myClone.mProcessId = mProcessId;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (LoggingDataAccess.LOGGING_ID.equals(pFieldName)) {
            return getLoggingId();
        } else if (LoggingDataAccess.PARENT_ID.equals(pFieldName)) {
            return getParentId();
        } else if (LoggingDataAccess.PRIO.equals(pFieldName)) {
            return getPrio();
        } else if (LoggingDataAccess.IPRIO.equals(pFieldName)) {
            return getIprio();
        } else if (LoggingDataAccess.CATEGORY.equals(pFieldName)) {
            return getCategory();
        } else if (LoggingDataAccess.THREAD.equals(pFieldName)) {
            return getThread();
        } else if (LoggingDataAccess.MESSAGE.equals(pFieldName)) {
            return getMessage();
        } else if (LoggingDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (LoggingDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (LoggingDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (LoggingDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (LoggingDataAccess.THROWABLE.equals(pFieldName)) {
            return getThrowable();
        } else if (LoggingDataAccess.PROCESS_ID.equals(pFieldName)) {
            return getProcessId();
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
        return LoggingDataAccess.CLW_LOGGING;
    }

    
    /**
     * Sets the LoggingId field. This field is required to be set in the database.
     *
     * @param pLoggingId
     *  int to use to update the field.
     */
    public void setLoggingId(int pLoggingId){
        this.mLoggingId = pLoggingId;
        setDirty(true);
    }
    /**
     * Retrieves the LoggingId field.
     *
     * @return
     *  int containing the LoggingId field.
     */
    public int getLoggingId(){
        return mLoggingId;
    }

    /**
     * Sets the ParentId field.
     *
     * @param pParentId
     *  int to use to update the field.
     */
    public void setParentId(int pParentId){
        this.mParentId = pParentId;
        setDirty(true);
    }
    /**
     * Retrieves the ParentId field.
     *
     * @return
     *  int containing the ParentId field.
     */
    public int getParentId(){
        return mParentId;
    }

    /**
     * Sets the Prio field.
     *
     * @param pPrio
     *  String to use to update the field.
     */
    public void setPrio(String pPrio){
        this.mPrio = pPrio;
        setDirty(true);
    }
    /**
     * Retrieves the Prio field.
     *
     * @return
     *  String containing the Prio field.
     */
    public String getPrio(){
        return mPrio;
    }

    /**
     * Sets the Iprio field.
     *
     * @param pIprio
     *  int to use to update the field.
     */
    public void setIprio(int pIprio){
        this.mIprio = pIprio;
        setDirty(true);
    }
    /**
     * Retrieves the Iprio field.
     *
     * @return
     *  int containing the Iprio field.
     */
    public int getIprio(){
        return mIprio;
    }

    /**
     * Sets the Category field.
     *
     * @param pCategory
     *  String to use to update the field.
     */
    public void setCategory(String pCategory){
        this.mCategory = pCategory;
        setDirty(true);
    }
    /**
     * Retrieves the Category field.
     *
     * @return
     *  String containing the Category field.
     */
    public String getCategory(){
        return mCategory;
    }

    /**
     * Sets the Thread field.
     *
     * @param pThread
     *  String to use to update the field.
     */
    public void setThread(String pThread){
        this.mThread = pThread;
        setDirty(true);
    }
    /**
     * Retrieves the Thread field.
     *
     * @return
     *  String containing the Thread field.
     */
    public String getThread(){
        return mThread;
    }

    /**
     * Sets the Message field.
     *
     * @param pMessage
     *  String to use to update the field.
     */
    public void setMessage(String pMessage){
        this.mMessage = pMessage;
        setDirty(true);
    }
    /**
     * Retrieves the Message field.
     *
     * @return
     *  String containing the Message field.
     */
    public String getMessage(){
        return mMessage;
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
     * Sets the Throwable field.
     *
     * @param pThrowable
     *  String to use to update the field.
     */
    public void setThrowable(String pThrowable){
        this.mThrowable = pThrowable;
        setDirty(true);
    }
    /**
     * Retrieves the Throwable field.
     *
     * @return
     *  String containing the Throwable field.
     */
    public String getThrowable(){
        return mThrowable;
    }

    /**
     * Sets the ProcessId field.
     *
     * @param pProcessId
     *  int to use to update the field.
     */
    public void setProcessId(int pProcessId){
        this.mProcessId = pProcessId;
        setDirty(true);
    }
    /**
     * Retrieves the ProcessId field.
     *
     * @return
     *  int containing the ProcessId field.
     */
    public int getProcessId(){
        return mProcessId;
    }


}
