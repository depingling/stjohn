
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        TaskData
 * Description:  This is a ValueObject class wrapping the database table CLW_TASK.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.TaskDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>TaskData</code> is a ValueObject class wrapping of the database table CLW_TASK.
 */
public class TaskData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 1964768902105199309L;
    private int mTaskId;// SQL type:NUMBER, not null
    private int mTaskTemplateId;// SQL type:NUMBER
    private int mProcessId;// SQL type:NUMBER
    private String mTaskName;// SQL type:VARCHAR2, not null
    private String mVarClass;// SQL type:VARCHAR2, not null
    private String mMethod;// SQL type:VARCHAR2, not null
    private String mTaskTypeCd;// SQL type:VARCHAR2
    private String mTaskStatusCd;// SQL type:VARCHAR2
    private String mAddBy;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mOperationTypeCd;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public TaskData ()
    {
        mTaskName = "";
        mVarClass = "";
        mMethod = "";
        mTaskTypeCd = "";
        mTaskStatusCd = "";
        mAddBy = "";
        mModBy = "";
        mOperationTypeCd = "";
    }

    /**
     * Constructor.
     */
    public TaskData(int parm1, int parm2, int parm3, String parm4, String parm5, String parm6, String parm7, String parm8, String parm9, Date parm10, String parm11, Date parm12, String parm13)
    {
        mTaskId = parm1;
        mTaskTemplateId = parm2;
        mProcessId = parm3;
        mTaskName = parm4;
        mVarClass = parm5;
        mMethod = parm6;
        mTaskTypeCd = parm7;
        mTaskStatusCd = parm8;
        mAddBy = parm9;
        mAddDate = parm10;
        mModBy = parm11;
        mModDate = parm12;
        mOperationTypeCd = parm13;
        
    }

    /**
     * Creates a new TaskData
     *
     * @return
     *  Newly initialized TaskData object.
     */
    public static TaskData createValue ()
    {
        TaskData valueData = new TaskData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this TaskData object
     */
    public String toString()
    {
        return "[" + "TaskId=" + mTaskId + ", TaskTemplateId=" + mTaskTemplateId + ", ProcessId=" + mProcessId + ", TaskName=" + mTaskName + ", VarClass=" + mVarClass + ", Method=" + mMethod + ", TaskTypeCd=" + mTaskTypeCd + ", TaskStatusCd=" + mTaskStatusCd + ", AddBy=" + mAddBy + ", AddDate=" + mAddDate + ", ModBy=" + mModBy + ", ModDate=" + mModDate + ", OperationTypeCd=" + mOperationTypeCd + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("Task");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mTaskId));

        node =  doc.createElement("TaskTemplateId");
        node.appendChild(doc.createTextNode(String.valueOf(mTaskTemplateId)));
        root.appendChild(node);

        node =  doc.createElement("ProcessId");
        node.appendChild(doc.createTextNode(String.valueOf(mProcessId)));
        root.appendChild(node);

        node =  doc.createElement("TaskName");
        node.appendChild(doc.createTextNode(String.valueOf(mTaskName)));
        root.appendChild(node);

        node =  doc.createElement("VarClass");
        node.appendChild(doc.createTextNode(String.valueOf(mVarClass)));
        root.appendChild(node);

        node =  doc.createElement("Method");
        node.appendChild(doc.createTextNode(String.valueOf(mMethod)));
        root.appendChild(node);

        node =  doc.createElement("TaskTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mTaskTypeCd)));
        root.appendChild(node);

        node =  doc.createElement("TaskStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mTaskStatusCd)));
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

        node =  doc.createElement("OperationTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mOperationTypeCd)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the TaskId field is not cloned.
    *
    * @return TaskData object
    */
    public Object clone(){
        TaskData myClone = new TaskData();
        
        myClone.mTaskTemplateId = mTaskTemplateId;
        
        myClone.mProcessId = mProcessId;
        
        myClone.mTaskName = mTaskName;
        
        myClone.mVarClass = mVarClass;
        
        myClone.mMethod = mMethod;
        
        myClone.mTaskTypeCd = mTaskTypeCd;
        
        myClone.mTaskStatusCd = mTaskStatusCd;
        
        myClone.mAddBy = mAddBy;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mOperationTypeCd = mOperationTypeCd;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (TaskDataAccess.TASK_ID.equals(pFieldName)) {
            return getTaskId();
        } else if (TaskDataAccess.TASK_TEMPLATE_ID.equals(pFieldName)) {
            return getTaskTemplateId();
        } else if (TaskDataAccess.PROCESS_ID.equals(pFieldName)) {
            return getProcessId();
        } else if (TaskDataAccess.TASK_NAME.equals(pFieldName)) {
            return getTaskName();
        } else if (TaskDataAccess.VAR_CLASS.equals(pFieldName)) {
            return getVarClass();
        } else if (TaskDataAccess.METHOD.equals(pFieldName)) {
            return getMethod();
        } else if (TaskDataAccess.TASK_TYPE_CD.equals(pFieldName)) {
            return getTaskTypeCd();
        } else if (TaskDataAccess.TASK_STATUS_CD.equals(pFieldName)) {
            return getTaskStatusCd();
        } else if (TaskDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (TaskDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (TaskDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (TaskDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (TaskDataAccess.OPERATION_TYPE_CD.equals(pFieldName)) {
            return getOperationTypeCd();
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
        return TaskDataAccess.CLW_TASK;
    }

    
    /**
     * Sets the TaskId field. This field is required to be set in the database.
     *
     * @param pTaskId
     *  int to use to update the field.
     */
    public void setTaskId(int pTaskId){
        this.mTaskId = pTaskId;
        setDirty(true);
    }
    /**
     * Retrieves the TaskId field.
     *
     * @return
     *  int containing the TaskId field.
     */
    public int getTaskId(){
        return mTaskId;
    }

    /**
     * Sets the TaskTemplateId field.
     *
     * @param pTaskTemplateId
     *  int to use to update the field.
     */
    public void setTaskTemplateId(int pTaskTemplateId){
        this.mTaskTemplateId = pTaskTemplateId;
        setDirty(true);
    }
    /**
     * Retrieves the TaskTemplateId field.
     *
     * @return
     *  int containing the TaskTemplateId field.
     */
    public int getTaskTemplateId(){
        return mTaskTemplateId;
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

    /**
     * Sets the TaskName field. This field is required to be set in the database.
     *
     * @param pTaskName
     *  String to use to update the field.
     */
    public void setTaskName(String pTaskName){
        this.mTaskName = pTaskName;
        setDirty(true);
    }
    /**
     * Retrieves the TaskName field.
     *
     * @return
     *  String containing the TaskName field.
     */
    public String getTaskName(){
        return mTaskName;
    }

    /**
     * Sets the VarClass field. This field is required to be set in the database.
     *
     * @param pVarClass
     *  String to use to update the field.
     */
    public void setVarClass(String pVarClass){
        this.mVarClass = pVarClass;
        setDirty(true);
    }
    /**
     * Retrieves the VarClass field.
     *
     * @return
     *  String containing the VarClass field.
     */
    public String getVarClass(){
        return mVarClass;
    }

    /**
     * Sets the Method field. This field is required to be set in the database.
     *
     * @param pMethod
     *  String to use to update the field.
     */
    public void setMethod(String pMethod){
        this.mMethod = pMethod;
        setDirty(true);
    }
    /**
     * Retrieves the Method field.
     *
     * @return
     *  String containing the Method field.
     */
    public String getMethod(){
        return mMethod;
    }

    /**
     * Sets the TaskTypeCd field.
     *
     * @param pTaskTypeCd
     *  String to use to update the field.
     */
    public void setTaskTypeCd(String pTaskTypeCd){
        this.mTaskTypeCd = pTaskTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the TaskTypeCd field.
     *
     * @return
     *  String containing the TaskTypeCd field.
     */
    public String getTaskTypeCd(){
        return mTaskTypeCd;
    }

    /**
     * Sets the TaskStatusCd field.
     *
     * @param pTaskStatusCd
     *  String to use to update the field.
     */
    public void setTaskStatusCd(String pTaskStatusCd){
        this.mTaskStatusCd = pTaskStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the TaskStatusCd field.
     *
     * @return
     *  String containing the TaskStatusCd field.
     */
    public String getTaskStatusCd(){
        return mTaskStatusCd;
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
     * Sets the OperationTypeCd field.
     *
     * @param pOperationTypeCd
     *  String to use to update the field.
     */
    public void setOperationTypeCd(String pOperationTypeCd){
        this.mOperationTypeCd = pOperationTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the OperationTypeCd field.
     *
     * @return
     *  String containing the OperationTypeCd field.
     */
    public String getOperationTypeCd(){
        return mOperationTypeCd;
    }


}
