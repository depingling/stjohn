
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        PipelineData
 * Description:  This is a ValueObject class wrapping the database table CLW_PIPELINE.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.PipelineDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>PipelineData</code> is a ValueObject class wrapping of the database table CLW_PIPELINE.
 */
public class PipelineData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -4503793631602320094L;
    private int mPipelineId;// SQL type:NUMBER, not null
    private String mShortDesc;// SQL type:VARCHAR2, not null
    private String mPipelineStatusCd;// SQL type:VARCHAR2, not null
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private int mBusEntityId;// SQL type:NUMBER
    private String mPipelineTypeCd;// SQL type:VARCHAR2
    private String mClassname;// SQL type:VARCHAR2
    private int mPipelineOrder;// SQL type:NUMBER
    private int mOptional;// SQL type:NUMBER

    /**
     * Constructor.
     */
    public PipelineData ()
    {
        mShortDesc = "";
        mPipelineStatusCd = "";
        mAddBy = "";
        mModBy = "";
        mPipelineTypeCd = "";
        mClassname = "";
    }

    /**
     * Constructor.
     */
    public PipelineData(int parm1, String parm2, String parm3, Date parm4, String parm5, Date parm6, String parm7, int parm8, String parm9, String parm10, int parm11, int parm12)
    {
        mPipelineId = parm1;
        mShortDesc = parm2;
        mPipelineStatusCd = parm3;
        mAddDate = parm4;
        mAddBy = parm5;
        mModDate = parm6;
        mModBy = parm7;
        mBusEntityId = parm8;
        mPipelineTypeCd = parm9;
        mClassname = parm10;
        mPipelineOrder = parm11;
        mOptional = parm12;
        
    }

    /**
     * Creates a new PipelineData
     *
     * @return
     *  Newly initialized PipelineData object.
     */
    public static PipelineData createValue ()
    {
        PipelineData valueData = new PipelineData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this PipelineData object
     */
    public String toString()
    {
        return "[" + "PipelineId=" + mPipelineId + ", ShortDesc=" + mShortDesc + ", PipelineStatusCd=" + mPipelineStatusCd + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", BusEntityId=" + mBusEntityId + ", PipelineTypeCd=" + mPipelineTypeCd + ", Classname=" + mClassname + ", PipelineOrder=" + mPipelineOrder + ", Optional=" + mOptional + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("Pipeline");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mPipelineId));

        node =  doc.createElement("ShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mShortDesc)));
        root.appendChild(node);

        node =  doc.createElement("PipelineStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mPipelineStatusCd)));
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

        node =  doc.createElement("BusEntityId");
        node.appendChild(doc.createTextNode(String.valueOf(mBusEntityId)));
        root.appendChild(node);

        node =  doc.createElement("PipelineTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mPipelineTypeCd)));
        root.appendChild(node);

        node =  doc.createElement("Classname");
        node.appendChild(doc.createTextNode(String.valueOf(mClassname)));
        root.appendChild(node);

        node =  doc.createElement("PipelineOrder");
        node.appendChild(doc.createTextNode(String.valueOf(mPipelineOrder)));
        root.appendChild(node);

        node =  doc.createElement("Optional");
        node.appendChild(doc.createTextNode(String.valueOf(mOptional)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the PipelineId field is not cloned.
    *
    * @return PipelineData object
    */
    public Object clone(){
        PipelineData myClone = new PipelineData();
        
        myClone.mShortDesc = mShortDesc;
        
        myClone.mPipelineStatusCd = mPipelineStatusCd;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        myClone.mBusEntityId = mBusEntityId;
        
        myClone.mPipelineTypeCd = mPipelineTypeCd;
        
        myClone.mClassname = mClassname;
        
        myClone.mPipelineOrder = mPipelineOrder;
        
        myClone.mOptional = mOptional;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (PipelineDataAccess.PIPELINE_ID.equals(pFieldName)) {
            return getPipelineId();
        } else if (PipelineDataAccess.SHORT_DESC.equals(pFieldName)) {
            return getShortDesc();
        } else if (PipelineDataAccess.PIPELINE_STATUS_CD.equals(pFieldName)) {
            return getPipelineStatusCd();
        } else if (PipelineDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (PipelineDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (PipelineDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (PipelineDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (PipelineDataAccess.BUS_ENTITY_ID.equals(pFieldName)) {
            return getBusEntityId();
        } else if (PipelineDataAccess.PIPELINE_TYPE_CD.equals(pFieldName)) {
            return getPipelineTypeCd();
        } else if (PipelineDataAccess.CLASSNAME.equals(pFieldName)) {
            return getClassname();
        } else if (PipelineDataAccess.PIPELINE_ORDER.equals(pFieldName)) {
            return getPipelineOrder();
        } else if (PipelineDataAccess.OPTIONAL.equals(pFieldName)) {
            return getOptional();
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
        return PipelineDataAccess.CLW_PIPELINE;
    }

    
    /**
     * Sets the PipelineId field. This field is required to be set in the database.
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
     * Sets the PipelineStatusCd field. This field is required to be set in the database.
     *
     * @param pPipelineStatusCd
     *  String to use to update the field.
     */
    public void setPipelineStatusCd(String pPipelineStatusCd){
        this.mPipelineStatusCd = pPipelineStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the PipelineStatusCd field.
     *
     * @return
     *  String containing the PipelineStatusCd field.
     */
    public String getPipelineStatusCd(){
        return mPipelineStatusCd;
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
     * Sets the BusEntityId field.
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
     * Sets the PipelineTypeCd field.
     *
     * @param pPipelineTypeCd
     *  String to use to update the field.
     */
    public void setPipelineTypeCd(String pPipelineTypeCd){
        this.mPipelineTypeCd = pPipelineTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the PipelineTypeCd field.
     *
     * @return
     *  String containing the PipelineTypeCd field.
     */
    public String getPipelineTypeCd(){
        return mPipelineTypeCd;
    }

    /**
     * Sets the Classname field.
     *
     * @param pClassname
     *  String to use to update the field.
     */
    public void setClassname(String pClassname){
        this.mClassname = pClassname;
        setDirty(true);
    }
    /**
     * Retrieves the Classname field.
     *
     * @return
     *  String containing the Classname field.
     */
    public String getClassname(){
        return mClassname;
    }

    /**
     * Sets the PipelineOrder field.
     *
     * @param pPipelineOrder
     *  int to use to update the field.
     */
    public void setPipelineOrder(int pPipelineOrder){
        this.mPipelineOrder = pPipelineOrder;
        setDirty(true);
    }
    /**
     * Retrieves the PipelineOrder field.
     *
     * @return
     *  int containing the PipelineOrder field.
     */
    public int getPipelineOrder(){
        return mPipelineOrder;
    }

    /**
     * Sets the Optional field.
     *
     * @param pOptional
     *  int to use to update the field.
     */
    public void setOptional(int pOptional){
        this.mOptional = pOptional;
        setDirty(true);
    }
    /**
     * Retrieves the Optional field.
     *
     * @return
     *  int containing the Optional field.
     */
    public int getOptional(){
        return mOptional;
    }


}
