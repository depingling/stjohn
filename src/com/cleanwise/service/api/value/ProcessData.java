
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ProcessData
 * Description:  This is a ValueObject class wrapping the database table CLW_PROCESS.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.ProcessDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>ProcessData</code> is a ValueObject class wrapping of the database table CLW_PROCESS.
 */
public class ProcessData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 2507804954185744258L;
    private int mProcessId;// SQL type:NUMBER, not null
    private int mProcessTemplateId;// SQL type:NUMBER
    private String mProcessName;// SQL type:VARCHAR2, not null
    private String mProcessTypeCd;// SQL type:VARCHAR2, not null
    private String mProcessStatusCd;// SQL type:VARCHAR2, not null
    private String mProcessHostname;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private int mProcessPriority;// SQL type:NUMBER

    /**
     * Constructor.
     */
    public ProcessData ()
    {
        mProcessName = "";
        mProcessTypeCd = "";
        mProcessStatusCd = "";
        mProcessHostname = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public ProcessData(int parm1, int parm2, String parm3, String parm4, String parm5, String parm6, Date parm7, String parm8, Date parm9, String parm10, int parm11)
    {
        mProcessId = parm1;
        mProcessTemplateId = parm2;
        mProcessName = parm3;
        mProcessTypeCd = parm4;
        mProcessStatusCd = parm5;
        mProcessHostname = parm6;
        mAddDate = parm7;
        mAddBy = parm8;
        mModDate = parm9;
        mModBy = parm10;
        mProcessPriority = parm11;
        
    }

    /**
     * Creates a new ProcessData
     *
     * @return
     *  Newly initialized ProcessData object.
     */
    public static ProcessData createValue ()
    {
        ProcessData valueData = new ProcessData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ProcessData object
     */
    public String toString()
    {
        return "[" + "ProcessId=" + mProcessId + ", ProcessTemplateId=" + mProcessTemplateId + ", ProcessName=" + mProcessName + ", ProcessTypeCd=" + mProcessTypeCd + ", ProcessStatusCd=" + mProcessStatusCd + ", ProcessHostname=" + mProcessHostname + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", ProcessPriority=" + mProcessPriority + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("Process");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mProcessId));

        node =  doc.createElement("ProcessTemplateId");
        node.appendChild(doc.createTextNode(String.valueOf(mProcessTemplateId)));
        root.appendChild(node);

        node =  doc.createElement("ProcessName");
        node.appendChild(doc.createTextNode(String.valueOf(mProcessName)));
        root.appendChild(node);

        node =  doc.createElement("ProcessTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mProcessTypeCd)));
        root.appendChild(node);

        node =  doc.createElement("ProcessStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mProcessStatusCd)));
        root.appendChild(node);

        node =  doc.createElement("ProcessHostname");
        node.appendChild(doc.createTextNode(String.valueOf(mProcessHostname)));
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

        node =  doc.createElement("ProcessPriority");
        node.appendChild(doc.createTextNode(String.valueOf(mProcessPriority)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the ProcessId field is not cloned.
    *
    * @return ProcessData object
    */
    public Object clone(){
        ProcessData myClone = new ProcessData();
        
        myClone.mProcessTemplateId = mProcessTemplateId;
        
        myClone.mProcessName = mProcessName;
        
        myClone.mProcessTypeCd = mProcessTypeCd;
        
        myClone.mProcessStatusCd = mProcessStatusCd;
        
        myClone.mProcessHostname = mProcessHostname;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        myClone.mProcessPriority = mProcessPriority;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (ProcessDataAccess.PROCESS_ID.equals(pFieldName)) {
            return getProcessId();
        } else if (ProcessDataAccess.PROCESS_TEMPLATE_ID.equals(pFieldName)) {
            return getProcessTemplateId();
        } else if (ProcessDataAccess.PROCESS_NAME.equals(pFieldName)) {
            return getProcessName();
        } else if (ProcessDataAccess.PROCESS_TYPE_CD.equals(pFieldName)) {
            return getProcessTypeCd();
        } else if (ProcessDataAccess.PROCESS_STATUS_CD.equals(pFieldName)) {
            return getProcessStatusCd();
        } else if (ProcessDataAccess.PROCESS_HOSTNAME.equals(pFieldName)) {
            return getProcessHostname();
        } else if (ProcessDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (ProcessDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (ProcessDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (ProcessDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (ProcessDataAccess.PROCESS_PRIORITY.equals(pFieldName)) {
            return getProcessPriority();
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
        return ProcessDataAccess.CLW_PROCESS;
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
     * Sets the ProcessTemplateId field.
     *
     * @param pProcessTemplateId
     *  int to use to update the field.
     */
    public void setProcessTemplateId(int pProcessTemplateId){
        this.mProcessTemplateId = pProcessTemplateId;
        setDirty(true);
    }
    /**
     * Retrieves the ProcessTemplateId field.
     *
     * @return
     *  int containing the ProcessTemplateId field.
     */
    public int getProcessTemplateId(){
        return mProcessTemplateId;
    }

    /**
     * Sets the ProcessName field. This field is required to be set in the database.
     *
     * @param pProcessName
     *  String to use to update the field.
     */
    public void setProcessName(String pProcessName){
        this.mProcessName = pProcessName;
        setDirty(true);
    }
    /**
     * Retrieves the ProcessName field.
     *
     * @return
     *  String containing the ProcessName field.
     */
    public String getProcessName(){
        return mProcessName;
    }

    /**
     * Sets the ProcessTypeCd field. This field is required to be set in the database.
     *
     * @param pProcessTypeCd
     *  String to use to update the field.
     */
    public void setProcessTypeCd(String pProcessTypeCd){
        this.mProcessTypeCd = pProcessTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the ProcessTypeCd field.
     *
     * @return
     *  String containing the ProcessTypeCd field.
     */
    public String getProcessTypeCd(){
        return mProcessTypeCd;
    }

    /**
     * Sets the ProcessStatusCd field. This field is required to be set in the database.
     *
     * @param pProcessStatusCd
     *  String to use to update the field.
     */
    public void setProcessStatusCd(String pProcessStatusCd){
        this.mProcessStatusCd = pProcessStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the ProcessStatusCd field.
     *
     * @return
     *  String containing the ProcessStatusCd field.
     */
    public String getProcessStatusCd(){
        return mProcessStatusCd;
    }

    /**
     * Sets the ProcessHostname field.
     *
     * @param pProcessHostname
     *  String to use to update the field.
     */
    public void setProcessHostname(String pProcessHostname){
        this.mProcessHostname = pProcessHostname;
        setDirty(true);
    }
    /**
     * Retrieves the ProcessHostname field.
     *
     * @return
     *  String containing the ProcessHostname field.
     */
    public String getProcessHostname(){
        return mProcessHostname;
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
     * Sets the ProcessPriority field.
     *
     * @param pProcessPriority
     *  int to use to update the field.
     */
    public void setProcessPriority(int pProcessPriority){
        this.mProcessPriority = pProcessPriority;
        setDirty(true);
    }
    /**
     * Retrieves the ProcessPriority field.
     *
     * @return
     *  int containing the ProcessPriority field.
     */
    public int getProcessPriority(){
        return mProcessPriority;
    }


}
