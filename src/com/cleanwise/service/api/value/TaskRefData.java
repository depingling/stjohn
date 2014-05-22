
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        TaskRefData
 * Description:  This is a ValueObject class wrapping the database table CLW_TASK_REF.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.TaskRefDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>TaskRefData</code> is a ValueObject class wrapping of the database table CLW_TASK_REF.
 */
public class TaskRefData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -472601925074474058L;
    private int mTaskRefId;// SQL type:NUMBER, not null
    private int mTaskId1;// SQL type:NUMBER
    private int mTaskId2;// SQL type:NUMBER
    private int mProcessId;// SQL type:NUMBER, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mTaskRefStatusCd;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public TaskRefData ()
    {
        mAddBy = "";
        mModBy = "";
        mTaskRefStatusCd = "";
    }

    /**
     * Constructor.
     */
    public TaskRefData(int parm1, int parm2, int parm3, int parm4, String parm5, Date parm6, String parm7, Date parm8, String parm9)
    {
        mTaskRefId = parm1;
        mTaskId1 = parm2;
        mTaskId2 = parm3;
        mProcessId = parm4;
        mAddBy = parm5;
        mAddDate = parm6;
        mModBy = parm7;
        mModDate = parm8;
        mTaskRefStatusCd = parm9;
        
    }

    /**
     * Creates a new TaskRefData
     *
     * @return
     *  Newly initialized TaskRefData object.
     */
    public static TaskRefData createValue ()
    {
        TaskRefData valueData = new TaskRefData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this TaskRefData object
     */
    public String toString()
    {
        return "[" + "TaskRefId=" + mTaskRefId + ", TaskId1=" + mTaskId1 + ", TaskId2=" + mTaskId2 + ", ProcessId=" + mProcessId + ", AddBy=" + mAddBy + ", AddDate=" + mAddDate + ", ModBy=" + mModBy + ", ModDate=" + mModDate + ", TaskRefStatusCd=" + mTaskRefStatusCd + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("TaskRef");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mTaskRefId));

        node =  doc.createElement("TaskId1");
        node.appendChild(doc.createTextNode(String.valueOf(mTaskId1)));
        root.appendChild(node);

        node =  doc.createElement("TaskId2");
        node.appendChild(doc.createTextNode(String.valueOf(mTaskId2)));
        root.appendChild(node);

        node =  doc.createElement("ProcessId");
        node.appendChild(doc.createTextNode(String.valueOf(mProcessId)));
        root.appendChild(node);

        node =  doc.createElement("AddBy");
        node.appendChild(doc.createTextNode(String.valueOf(mAddBy)));
        root.appendChild(node);

        node =  doc.createElement("AddDate");
        node.appendChild(doc.createTextNode(String.valueOf(mAddDate)));
        root.appendChild(node);

        node =  doc.createElement("ModBy");
        node.appendChild(doc.createTextNode(String.valueOf(mModBy)));
        root.appendChild(node);

        node =  doc.createElement("ModDate");
        node.appendChild(doc.createTextNode(String.valueOf(mModDate)));
        root.appendChild(node);

        node =  doc.createElement("TaskRefStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mTaskRefStatusCd)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the TaskRefId field is not cloned.
    *
    * @return TaskRefData object
    */
    public Object clone(){
        TaskRefData myClone = new TaskRefData();
        
        myClone.mTaskId1 = mTaskId1;
        
        myClone.mTaskId2 = mTaskId2;
        
        myClone.mProcessId = mProcessId;
        
        myClone.mAddBy = mAddBy;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mTaskRefStatusCd = mTaskRefStatusCd;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (TaskRefDataAccess.TASK_REF_ID.equals(pFieldName)) {
            return getTaskRefId();
        } else if (TaskRefDataAccess.TASK_ID1.equals(pFieldName)) {
            return getTaskId1();
        } else if (TaskRefDataAccess.TASK_ID2.equals(pFieldName)) {
            return getTaskId2();
        } else if (TaskRefDataAccess.PROCESS_ID.equals(pFieldName)) {
            return getProcessId();
        } else if (TaskRefDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (TaskRefDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (TaskRefDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (TaskRefDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (TaskRefDataAccess.TASK_REF_STATUS_CD.equals(pFieldName)) {
            return getTaskRefStatusCd();
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
        return TaskRefDataAccess.CLW_TASK_REF;
    }

    
    /**
     * Sets the TaskRefId field. This field is required to be set in the database.
     *
     * @param pTaskRefId
     *  int to use to update the field.
     */
    public void setTaskRefId(int pTaskRefId){
        this.mTaskRefId = pTaskRefId;
        setDirty(true);
    }
    /**
     * Retrieves the TaskRefId field.
     *
     * @return
     *  int containing the TaskRefId field.
     */
    public int getTaskRefId(){
        return mTaskRefId;
    }

    /**
     * Sets the TaskId1 field.
     *
     * @param pTaskId1
     *  int to use to update the field.
     */
    public void setTaskId1(int pTaskId1){
        this.mTaskId1 = pTaskId1;
        setDirty(true);
    }
    /**
     * Retrieves the TaskId1 field.
     *
     * @return
     *  int containing the TaskId1 field.
     */
    public int getTaskId1(){
        return mTaskId1;
    }

    /**
     * Sets the TaskId2 field.
     *
     * @param pTaskId2
     *  int to use to update the field.
     */
    public void setTaskId2(int pTaskId2){
        this.mTaskId2 = pTaskId2;
        setDirty(true);
    }
    /**
     * Retrieves the TaskId2 field.
     *
     * @return
     *  int containing the TaskId2 field.
     */
    public int getTaskId2(){
        return mTaskId2;
    }

    /**
     * Sets the ProcessId field. This field is required to be set in the database.
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
     * Sets the TaskRefStatusCd field.
     *
     * @param pTaskRefStatusCd
     *  String to use to update the field.
     */
    public void setTaskRefStatusCd(String pTaskRefStatusCd){
        this.mTaskRefStatusCd = pTaskRefStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the TaskRefStatusCd field.
     *
     * @return
     *  String containing the TaskRefStatusCd field.
     */
    public String getTaskRefStatusCd(){
        return mTaskRefStatusCd;
    }


}
