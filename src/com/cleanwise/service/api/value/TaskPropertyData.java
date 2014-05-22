
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        TaskPropertyData
 * Description:  This is a ValueObject class wrapping the database table CLW_TASK_PROPERTY.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.TaskPropertyDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>TaskPropertyData</code> is a ValueObject class wrapping of the database table CLW_TASK_PROPERTY.
 */
public class TaskPropertyData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 4439886578803859941L;
    private int mTaskPropertyId;// SQL type:NUMBER, not null
    private int mTaskId;// SQL type:NUMBER, not null
    private String mPropertyTypeCd;// SQL type:VARCHAR2, not null
    private int mPosition;// SQL type:NUMBER
    private String mVarType;// SQL type:VARCHAR2
    private String mVarName;// SQL type:VARCHAR2
    private String mAddBy;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mTaskPropertyStatusCd;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public TaskPropertyData ()
    {
        mPropertyTypeCd = "";
        mVarType = "";
        mVarName = "";
        mAddBy = "";
        mModBy = "";
        mTaskPropertyStatusCd = "";
    }

    /**
     * Constructor.
     */
    public TaskPropertyData(int parm1, int parm2, String parm3, int parm4, String parm5, String parm6, String parm7, Date parm8, String parm9, Date parm10, String parm11)
    {
        mTaskPropertyId = parm1;
        mTaskId = parm2;
        mPropertyTypeCd = parm3;
        mPosition = parm4;
        mVarType = parm5;
        mVarName = parm6;
        mAddBy = parm7;
        mAddDate = parm8;
        mModBy = parm9;
        mModDate = parm10;
        mTaskPropertyStatusCd = parm11;
        
    }

    /**
     * Creates a new TaskPropertyData
     *
     * @return
     *  Newly initialized TaskPropertyData object.
     */
    public static TaskPropertyData createValue ()
    {
        TaskPropertyData valueData = new TaskPropertyData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this TaskPropertyData object
     */
    public String toString()
    {
        return "[" + "TaskPropertyId=" + mTaskPropertyId + ", TaskId=" + mTaskId + ", PropertyTypeCd=" + mPropertyTypeCd + ", Position=" + mPosition + ", VarType=" + mVarType + ", VarName=" + mVarName + ", AddBy=" + mAddBy + ", AddDate=" + mAddDate + ", ModBy=" + mModBy + ", ModDate=" + mModDate + ", TaskPropertyStatusCd=" + mTaskPropertyStatusCd + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("TaskProperty");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mTaskPropertyId));

        node =  doc.createElement("TaskId");
        node.appendChild(doc.createTextNode(String.valueOf(mTaskId)));
        root.appendChild(node);

        node =  doc.createElement("PropertyTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mPropertyTypeCd)));
        root.appendChild(node);

        node =  doc.createElement("Position");
        node.appendChild(doc.createTextNode(String.valueOf(mPosition)));
        root.appendChild(node);

        node =  doc.createElement("VarType");
        node.appendChild(doc.createTextNode(String.valueOf(mVarType)));
        root.appendChild(node);

        node =  doc.createElement("VarName");
        node.appendChild(doc.createTextNode(String.valueOf(mVarName)));
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

        node =  doc.createElement("TaskPropertyStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mTaskPropertyStatusCd)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the TaskPropertyId field is not cloned.
    *
    * @return TaskPropertyData object
    */
    public Object clone(){
        TaskPropertyData myClone = new TaskPropertyData();
        
        myClone.mTaskId = mTaskId;
        
        myClone.mPropertyTypeCd = mPropertyTypeCd;
        
        myClone.mPosition = mPosition;
        
        myClone.mVarType = mVarType;
        
        myClone.mVarName = mVarName;
        
        myClone.mAddBy = mAddBy;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mTaskPropertyStatusCd = mTaskPropertyStatusCd;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (TaskPropertyDataAccess.TASK_PROPERTY_ID.equals(pFieldName)) {
            return getTaskPropertyId();
        } else if (TaskPropertyDataAccess.TASK_ID.equals(pFieldName)) {
            return getTaskId();
        } else if (TaskPropertyDataAccess.PROPERTY_TYPE_CD.equals(pFieldName)) {
            return getPropertyTypeCd();
        } else if (TaskPropertyDataAccess.POSITION.equals(pFieldName)) {
            return getPosition();
        } else if (TaskPropertyDataAccess.VAR_TYPE.equals(pFieldName)) {
            return getVarType();
        } else if (TaskPropertyDataAccess.VAR_NAME.equals(pFieldName)) {
            return getVarName();
        } else if (TaskPropertyDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (TaskPropertyDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (TaskPropertyDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (TaskPropertyDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (TaskPropertyDataAccess.TASK_PROPERTY_STATUS_CD.equals(pFieldName)) {
            return getTaskPropertyStatusCd();
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
        return TaskPropertyDataAccess.CLW_TASK_PROPERTY;
    }

    
    /**
     * Sets the TaskPropertyId field. This field is required to be set in the database.
     *
     * @param pTaskPropertyId
     *  int to use to update the field.
     */
    public void setTaskPropertyId(int pTaskPropertyId){
        this.mTaskPropertyId = pTaskPropertyId;
        setDirty(true);
    }
    /**
     * Retrieves the TaskPropertyId field.
     *
     * @return
     *  int containing the TaskPropertyId field.
     */
    public int getTaskPropertyId(){
        return mTaskPropertyId;
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
     * Sets the PropertyTypeCd field. This field is required to be set in the database.
     *
     * @param pPropertyTypeCd
     *  String to use to update the field.
     */
    public void setPropertyTypeCd(String pPropertyTypeCd){
        this.mPropertyTypeCd = pPropertyTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the PropertyTypeCd field.
     *
     * @return
     *  String containing the PropertyTypeCd field.
     */
    public String getPropertyTypeCd(){
        return mPropertyTypeCd;
    }

    /**
     * Sets the Position field.
     *
     * @param pPosition
     *  int to use to update the field.
     */
    public void setPosition(int pPosition){
        this.mPosition = pPosition;
        setDirty(true);
    }
    /**
     * Retrieves the Position field.
     *
     * @return
     *  int containing the Position field.
     */
    public int getPosition(){
        return mPosition;
    }

    /**
     * Sets the VarType field.
     *
     * @param pVarType
     *  String to use to update the field.
     */
    public void setVarType(String pVarType){
        this.mVarType = pVarType;
        setDirty(true);
    }
    /**
     * Retrieves the VarType field.
     *
     * @return
     *  String containing the VarType field.
     */
    public String getVarType(){
        return mVarType;
    }

    /**
     * Sets the VarName field.
     *
     * @param pVarName
     *  String to use to update the field.
     */
    public void setVarName(String pVarName){
        this.mVarName = pVarName;
        setDirty(true);
    }
    /**
     * Retrieves the VarName field.
     *
     * @return
     *  String containing the VarName field.
     */
    public String getVarName(){
        return mVarName;
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
     * Sets the TaskPropertyStatusCd field.
     *
     * @param pTaskPropertyStatusCd
     *  String to use to update the field.
     */
    public void setTaskPropertyStatusCd(String pTaskPropertyStatusCd){
        this.mTaskPropertyStatusCd = pTaskPropertyStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the TaskPropertyStatusCd field.
     *
     * @return
     *  String containing the TaskPropertyStatusCd field.
     */
    public String getTaskPropertyStatusCd(){
        return mTaskPropertyStatusCd;
    }


}
