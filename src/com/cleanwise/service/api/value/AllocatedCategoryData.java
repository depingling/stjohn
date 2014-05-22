
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        AllocatedCategoryData
 * Description:  This is a ValueObject class wrapping the database table CLW_ALLOCATED_CATEGORY.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.AllocatedCategoryDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>AllocatedCategoryData</code> is a ValueObject class wrapping of the database table CLW_ALLOCATED_CATEGORY.
 */
public class AllocatedCategoryData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -7241018835739137831L;
    private int mAllocatedCategoryId;// SQL type:NUMBER, not null
    private int mEstimatorFacilityId;// SQL type:NUMBER, not null
    private String mName;// SQL type:VARCHAR2, not null
    private java.math.BigDecimal mPercent;// SQL type:NUMBER
    private int mSeqNum;// SQL type:NUMBER
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public AllocatedCategoryData ()
    {
        mName = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public AllocatedCategoryData(int parm1, int parm2, String parm3, java.math.BigDecimal parm4, int parm5, Date parm6, String parm7, Date parm8, String parm9)
    {
        mAllocatedCategoryId = parm1;
        mEstimatorFacilityId = parm2;
        mName = parm3;
        mPercent = parm4;
        mSeqNum = parm5;
        mAddDate = parm6;
        mAddBy = parm7;
        mModDate = parm8;
        mModBy = parm9;
        
    }

    /**
     * Creates a new AllocatedCategoryData
     *
     * @return
     *  Newly initialized AllocatedCategoryData object.
     */
    public static AllocatedCategoryData createValue ()
    {
        AllocatedCategoryData valueData = new AllocatedCategoryData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this AllocatedCategoryData object
     */
    public String toString()
    {
        return "[" + "AllocatedCategoryId=" + mAllocatedCategoryId + ", EstimatorFacilityId=" + mEstimatorFacilityId + ", Name=" + mName + ", Percent=" + mPercent + ", SeqNum=" + mSeqNum + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("AllocatedCategory");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mAllocatedCategoryId));

        node =  doc.createElement("EstimatorFacilityId");
        node.appendChild(doc.createTextNode(String.valueOf(mEstimatorFacilityId)));
        root.appendChild(node);

        node =  doc.createElement("Name");
        node.appendChild(doc.createTextNode(String.valueOf(mName)));
        root.appendChild(node);

        node =  doc.createElement("Percent");
        node.appendChild(doc.createTextNode(String.valueOf(mPercent)));
        root.appendChild(node);

        node =  doc.createElement("SeqNum");
        node.appendChild(doc.createTextNode(String.valueOf(mSeqNum)));
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
    * creates a clone of this object, the AllocatedCategoryId field is not cloned.
    *
    * @return AllocatedCategoryData object
    */
    public Object clone(){
        AllocatedCategoryData myClone = new AllocatedCategoryData();
        
        myClone.mEstimatorFacilityId = mEstimatorFacilityId;
        
        myClone.mName = mName;
        
        myClone.mPercent = mPercent;
        
        myClone.mSeqNum = mSeqNum;
        
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

        if (AllocatedCategoryDataAccess.ALLOCATED_CATEGORY_ID.equals(pFieldName)) {
            return getAllocatedCategoryId();
        } else if (AllocatedCategoryDataAccess.ESTIMATOR_FACILITY_ID.equals(pFieldName)) {
            return getEstimatorFacilityId();
        } else if (AllocatedCategoryDataAccess.NAME.equals(pFieldName)) {
            return getName();
        } else if (AllocatedCategoryDataAccess.PERCENT.equals(pFieldName)) {
            return getPercent();
        } else if (AllocatedCategoryDataAccess.SEQ_NUM.equals(pFieldName)) {
            return getSeqNum();
        } else if (AllocatedCategoryDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (AllocatedCategoryDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (AllocatedCategoryDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (AllocatedCategoryDataAccess.MOD_BY.equals(pFieldName)) {
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
        return AllocatedCategoryDataAccess.CLW_ALLOCATED_CATEGORY;
    }

    
    /**
     * Sets the AllocatedCategoryId field. This field is required to be set in the database.
     *
     * @param pAllocatedCategoryId
     *  int to use to update the field.
     */
    public void setAllocatedCategoryId(int pAllocatedCategoryId){
        this.mAllocatedCategoryId = pAllocatedCategoryId;
        setDirty(true);
    }
    /**
     * Retrieves the AllocatedCategoryId field.
     *
     * @return
     *  int containing the AllocatedCategoryId field.
     */
    public int getAllocatedCategoryId(){
        return mAllocatedCategoryId;
    }

    /**
     * Sets the EstimatorFacilityId field. This field is required to be set in the database.
     *
     * @param pEstimatorFacilityId
     *  int to use to update the field.
     */
    public void setEstimatorFacilityId(int pEstimatorFacilityId){
        this.mEstimatorFacilityId = pEstimatorFacilityId;
        setDirty(true);
    }
    /**
     * Retrieves the EstimatorFacilityId field.
     *
     * @return
     *  int containing the EstimatorFacilityId field.
     */
    public int getEstimatorFacilityId(){
        return mEstimatorFacilityId;
    }

    /**
     * Sets the Name field. This field is required to be set in the database.
     *
     * @param pName
     *  String to use to update the field.
     */
    public void setName(String pName){
        this.mName = pName;
        setDirty(true);
    }
    /**
     * Retrieves the Name field.
     *
     * @return
     *  String containing the Name field.
     */
    public String getName(){
        return mName;
    }

    /**
     * Sets the Percent field.
     *
     * @param pPercent
     *  java.math.BigDecimal to use to update the field.
     */
    public void setPercent(java.math.BigDecimal pPercent){
        this.mPercent = pPercent;
        setDirty(true);
    }
    /**
     * Retrieves the Percent field.
     *
     * @return
     *  java.math.BigDecimal containing the Percent field.
     */
    public java.math.BigDecimal getPercent(){
        return mPercent;
    }

    /**
     * Sets the SeqNum field.
     *
     * @param pSeqNum
     *  int to use to update the field.
     */
    public void setSeqNum(int pSeqNum){
        this.mSeqNum = pSeqNum;
        setDirty(true);
    }
    /**
     * Retrieves the SeqNum field.
     *
     * @return
     *  int containing the SeqNum field.
     */
    public int getSeqNum(){
        return mSeqNum;
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
