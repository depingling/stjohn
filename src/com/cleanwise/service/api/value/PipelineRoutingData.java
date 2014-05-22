
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        PipelineRoutingData
 * Description:  This is a ValueObject class wrapping the database table CLW_PIPELINE_ROUTING.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.PipelineRoutingDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>PipelineRoutingData</code> is a ValueObject class wrapping of the database table CLW_PIPELINE_ROUTING.
 */
public class PipelineRoutingData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -2128300385522741204L;
    private int mPipelineRoutingId;// SQL type:NUMBER, not null
    private int mPipelineId;// SQL type:NUMBER
    private int mSequenceNum;// SQL type:NUMBER
    private String mProgramCd;// SQL type:VARCHAR2, not null
    private String mExceptionCd;// SQL type:VARCHAR2, not null
    private String mActionCd;// SQL type:VARCHAR2, not null
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public PipelineRoutingData ()
    {
        mProgramCd = "";
        mExceptionCd = "";
        mActionCd = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public PipelineRoutingData(int parm1, int parm2, int parm3, String parm4, String parm5, String parm6, Date parm7, String parm8, Date parm9, String parm10)
    {
        mPipelineRoutingId = parm1;
        mPipelineId = parm2;
        mSequenceNum = parm3;
        mProgramCd = parm4;
        mExceptionCd = parm5;
        mActionCd = parm6;
        mAddDate = parm7;
        mAddBy = parm8;
        mModDate = parm9;
        mModBy = parm10;
        
    }

    /**
     * Creates a new PipelineRoutingData
     *
     * @return
     *  Newly initialized PipelineRoutingData object.
     */
    public static PipelineRoutingData createValue ()
    {
        PipelineRoutingData valueData = new PipelineRoutingData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this PipelineRoutingData object
     */
    public String toString()
    {
        return "[" + "PipelineRoutingId=" + mPipelineRoutingId + ", PipelineId=" + mPipelineId + ", SequenceNum=" + mSequenceNum + ", ProgramCd=" + mProgramCd + ", ExceptionCd=" + mExceptionCd + ", ActionCd=" + mActionCd + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("PipelineRouting");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mPipelineRoutingId));

        node =  doc.createElement("PipelineId");
        node.appendChild(doc.createTextNode(String.valueOf(mPipelineId)));
        root.appendChild(node);

        node =  doc.createElement("SequenceNum");
        node.appendChild(doc.createTextNode(String.valueOf(mSequenceNum)));
        root.appendChild(node);

        node =  doc.createElement("ProgramCd");
        node.appendChild(doc.createTextNode(String.valueOf(mProgramCd)));
        root.appendChild(node);

        node =  doc.createElement("ExceptionCd");
        node.appendChild(doc.createTextNode(String.valueOf(mExceptionCd)));
        root.appendChild(node);

        node =  doc.createElement("ActionCd");
        node.appendChild(doc.createTextNode(String.valueOf(mActionCd)));
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
    * creates a clone of this object, the PipelineRoutingId field is not cloned.
    *
    * @return PipelineRoutingData object
    */
    public Object clone(){
        PipelineRoutingData myClone = new PipelineRoutingData();
        
        myClone.mPipelineId = mPipelineId;
        
        myClone.mSequenceNum = mSequenceNum;
        
        myClone.mProgramCd = mProgramCd;
        
        myClone.mExceptionCd = mExceptionCd;
        
        myClone.mActionCd = mActionCd;
        
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

        if (PipelineRoutingDataAccess.PIPELINE_ROUTING_ID.equals(pFieldName)) {
            return getPipelineRoutingId();
        } else if (PipelineRoutingDataAccess.PIPELINE_ID.equals(pFieldName)) {
            return getPipelineId();
        } else if (PipelineRoutingDataAccess.SEQUENCE_NUM.equals(pFieldName)) {
            return getSequenceNum();
        } else if (PipelineRoutingDataAccess.PROGRAM_CD.equals(pFieldName)) {
            return getProgramCd();
        } else if (PipelineRoutingDataAccess.EXCEPTION_CD.equals(pFieldName)) {
            return getExceptionCd();
        } else if (PipelineRoutingDataAccess.ACTION_CD.equals(pFieldName)) {
            return getActionCd();
        } else if (PipelineRoutingDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (PipelineRoutingDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (PipelineRoutingDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (PipelineRoutingDataAccess.MOD_BY.equals(pFieldName)) {
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
        return PipelineRoutingDataAccess.CLW_PIPELINE_ROUTING;
    }

    
    /**
     * Sets the PipelineRoutingId field. This field is required to be set in the database.
     *
     * @param pPipelineRoutingId
     *  int to use to update the field.
     */
    public void setPipelineRoutingId(int pPipelineRoutingId){
        this.mPipelineRoutingId = pPipelineRoutingId;
        setDirty(true);
    }
    /**
     * Retrieves the PipelineRoutingId field.
     *
     * @return
     *  int containing the PipelineRoutingId field.
     */
    public int getPipelineRoutingId(){
        return mPipelineRoutingId;
    }

    /**
     * Sets the PipelineId field.
     *
     * @param pPipelineId
     *  int to use to update the field.
     */
    public void setPipelineId(int pPipelineId){
        this.mPipelineId = pPipelineId;
        setDirty(true);
    }
    /**
     * Retrieves the PipelineId field.
     *
     * @return
     *  int containing the PipelineId field.
     */
    public int getPipelineId(){
        return mPipelineId;
    }

    /**
     * Sets the SequenceNum field.
     *
     * @param pSequenceNum
     *  int to use to update the field.
     */
    public void setSequenceNum(int pSequenceNum){
        this.mSequenceNum = pSequenceNum;
        setDirty(true);
    }
    /**
     * Retrieves the SequenceNum field.
     *
     * @return
     *  int containing the SequenceNum field.
     */
    public int getSequenceNum(){
        return mSequenceNum;
    }

    /**
     * Sets the ProgramCd field. This field is required to be set in the database.
     *
     * @param pProgramCd
     *  String to use to update the field.
     */
    public void setProgramCd(String pProgramCd){
        this.mProgramCd = pProgramCd;
        setDirty(true);
    }
    /**
     * Retrieves the ProgramCd field.
     *
     * @return
     *  String containing the ProgramCd field.
     */
    public String getProgramCd(){
        return mProgramCd;
    }

    /**
     * Sets the ExceptionCd field. This field is required to be set in the database.
     *
     * @param pExceptionCd
     *  String to use to update the field.
     */
    public void setExceptionCd(String pExceptionCd){
        this.mExceptionCd = pExceptionCd;
        setDirty(true);
    }
    /**
     * Retrieves the ExceptionCd field.
     *
     * @return
     *  String containing the ExceptionCd field.
     */
    public String getExceptionCd(){
        return mExceptionCd;
    }

    /**
     * Sets the ActionCd field. This field is required to be set in the database.
     *
     * @param pActionCd
     *  String to use to update the field.
     */
    public void setActionCd(String pActionCd){
        this.mActionCd = pActionCd;
        setDirty(true);
    }
    /**
     * Retrieves the ActionCd field.
     *
     * @return
     *  String containing the ActionCd field.
     */
    public String getActionCd(){
        return mActionCd;
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
