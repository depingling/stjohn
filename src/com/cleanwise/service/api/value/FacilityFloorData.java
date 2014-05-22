
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        FacilityFloorData
 * Description:  This is a ValueObject class wrapping the database table CLW_FACILITY_FLOOR.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.FacilityFloorDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>FacilityFloorData</code> is a ValueObject class wrapping of the database table CLW_FACILITY_FLOOR.
 */
public class FacilityFloorData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 5640213316385222117L;
    private int mFacilityFloorId;// SQL type:NUMBER, not null
    private int mEstimatorFacilityId;// SQL type:NUMBER, not null
    private String mFloorTypeCd;// SQL type:VARCHAR2, not null
    private java.math.BigDecimal mCleanablePercent;// SQL type:NUMBER
    private java.math.BigDecimal mCleanablePercentHt;// SQL type:NUMBER
    private java.math.BigDecimal mCleanablePercentMt;// SQL type:NUMBER
    private java.math.BigDecimal mCleanablePercentLt;// SQL type:NUMBER
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public FacilityFloorData ()
    {
        mFloorTypeCd = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public FacilityFloorData(int parm1, int parm2, String parm3, java.math.BigDecimal parm4, java.math.BigDecimal parm5, java.math.BigDecimal parm6, java.math.BigDecimal parm7, Date parm8, String parm9, Date parm10, String parm11)
    {
        mFacilityFloorId = parm1;
        mEstimatorFacilityId = parm2;
        mFloorTypeCd = parm3;
        mCleanablePercent = parm4;
        mCleanablePercentHt = parm5;
        mCleanablePercentMt = parm6;
        mCleanablePercentLt = parm7;
        mAddDate = parm8;
        mAddBy = parm9;
        mModDate = parm10;
        mModBy = parm11;
        
    }

    /**
     * Creates a new FacilityFloorData
     *
     * @return
     *  Newly initialized FacilityFloorData object.
     */
    public static FacilityFloorData createValue ()
    {
        FacilityFloorData valueData = new FacilityFloorData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this FacilityFloorData object
     */
    public String toString()
    {
        return "[" + "FacilityFloorId=" + mFacilityFloorId + ", EstimatorFacilityId=" + mEstimatorFacilityId + ", FloorTypeCd=" + mFloorTypeCd + ", CleanablePercent=" + mCleanablePercent + ", CleanablePercentHt=" + mCleanablePercentHt + ", CleanablePercentMt=" + mCleanablePercentMt + ", CleanablePercentLt=" + mCleanablePercentLt + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("FacilityFloor");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mFacilityFloorId));

        node =  doc.createElement("EstimatorFacilityId");
        node.appendChild(doc.createTextNode(String.valueOf(mEstimatorFacilityId)));
        root.appendChild(node);

        node =  doc.createElement("FloorTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mFloorTypeCd)));
        root.appendChild(node);

        node =  doc.createElement("CleanablePercent");
        node.appendChild(doc.createTextNode(String.valueOf(mCleanablePercent)));
        root.appendChild(node);

        node =  doc.createElement("CleanablePercentHt");
        node.appendChild(doc.createTextNode(String.valueOf(mCleanablePercentHt)));
        root.appendChild(node);

        node =  doc.createElement("CleanablePercentMt");
        node.appendChild(doc.createTextNode(String.valueOf(mCleanablePercentMt)));
        root.appendChild(node);

        node =  doc.createElement("CleanablePercentLt");
        node.appendChild(doc.createTextNode(String.valueOf(mCleanablePercentLt)));
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
    * creates a clone of this object, the FacilityFloorId field is not cloned.
    *
    * @return FacilityFloorData object
    */
    public Object clone(){
        FacilityFloorData myClone = new FacilityFloorData();
        
        myClone.mEstimatorFacilityId = mEstimatorFacilityId;
        
        myClone.mFloorTypeCd = mFloorTypeCd;
        
        myClone.mCleanablePercent = mCleanablePercent;
        
        myClone.mCleanablePercentHt = mCleanablePercentHt;
        
        myClone.mCleanablePercentMt = mCleanablePercentMt;
        
        myClone.mCleanablePercentLt = mCleanablePercentLt;
        
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

        if (FacilityFloorDataAccess.FACILITY_FLOOR_ID.equals(pFieldName)) {
            return getFacilityFloorId();
        } else if (FacilityFloorDataAccess.ESTIMATOR_FACILITY_ID.equals(pFieldName)) {
            return getEstimatorFacilityId();
        } else if (FacilityFloorDataAccess.FLOOR_TYPE_CD.equals(pFieldName)) {
            return getFloorTypeCd();
        } else if (FacilityFloorDataAccess.CLEANABLE_PERCENT.equals(pFieldName)) {
            return getCleanablePercent();
        } else if (FacilityFloorDataAccess.CLEANABLE_PERCENT_HT.equals(pFieldName)) {
            return getCleanablePercentHt();
        } else if (FacilityFloorDataAccess.CLEANABLE_PERCENT_MT.equals(pFieldName)) {
            return getCleanablePercentMt();
        } else if (FacilityFloorDataAccess.CLEANABLE_PERCENT_LT.equals(pFieldName)) {
            return getCleanablePercentLt();
        } else if (FacilityFloorDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (FacilityFloorDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (FacilityFloorDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (FacilityFloorDataAccess.MOD_BY.equals(pFieldName)) {
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
        return FacilityFloorDataAccess.CLW_FACILITY_FLOOR;
    }

    
    /**
     * Sets the FacilityFloorId field. This field is required to be set in the database.
     *
     * @param pFacilityFloorId
     *  int to use to update the field.
     */
    public void setFacilityFloorId(int pFacilityFloorId){
        this.mFacilityFloorId = pFacilityFloorId;
        setDirty(true);
    }
    /**
     * Retrieves the FacilityFloorId field.
     *
     * @return
     *  int containing the FacilityFloorId field.
     */
    public int getFacilityFloorId(){
        return mFacilityFloorId;
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
     * Sets the FloorTypeCd field. This field is required to be set in the database.
     *
     * @param pFloorTypeCd
     *  String to use to update the field.
     */
    public void setFloorTypeCd(String pFloorTypeCd){
        this.mFloorTypeCd = pFloorTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the FloorTypeCd field.
     *
     * @return
     *  String containing the FloorTypeCd field.
     */
    public String getFloorTypeCd(){
        return mFloorTypeCd;
    }

    /**
     * Sets the CleanablePercent field.
     *
     * @param pCleanablePercent
     *  java.math.BigDecimal to use to update the field.
     */
    public void setCleanablePercent(java.math.BigDecimal pCleanablePercent){
        this.mCleanablePercent = pCleanablePercent;
        setDirty(true);
    }
    /**
     * Retrieves the CleanablePercent field.
     *
     * @return
     *  java.math.BigDecimal containing the CleanablePercent field.
     */
    public java.math.BigDecimal getCleanablePercent(){
        return mCleanablePercent;
    }

    /**
     * Sets the CleanablePercentHt field.
     *
     * @param pCleanablePercentHt
     *  java.math.BigDecimal to use to update the field.
     */
    public void setCleanablePercentHt(java.math.BigDecimal pCleanablePercentHt){
        this.mCleanablePercentHt = pCleanablePercentHt;
        setDirty(true);
    }
    /**
     * Retrieves the CleanablePercentHt field.
     *
     * @return
     *  java.math.BigDecimal containing the CleanablePercentHt field.
     */
    public java.math.BigDecimal getCleanablePercentHt(){
        return mCleanablePercentHt;
    }

    /**
     * Sets the CleanablePercentMt field.
     *
     * @param pCleanablePercentMt
     *  java.math.BigDecimal to use to update the field.
     */
    public void setCleanablePercentMt(java.math.BigDecimal pCleanablePercentMt){
        this.mCleanablePercentMt = pCleanablePercentMt;
        setDirty(true);
    }
    /**
     * Retrieves the CleanablePercentMt field.
     *
     * @return
     *  java.math.BigDecimal containing the CleanablePercentMt field.
     */
    public java.math.BigDecimal getCleanablePercentMt(){
        return mCleanablePercentMt;
    }

    /**
     * Sets the CleanablePercentLt field.
     *
     * @param pCleanablePercentLt
     *  java.math.BigDecimal to use to update the field.
     */
    public void setCleanablePercentLt(java.math.BigDecimal pCleanablePercentLt){
        this.mCleanablePercentLt = pCleanablePercentLt;
        setDirty(true);
    }
    /**
     * Retrieves the CleanablePercentLt field.
     *
     * @return
     *  java.math.BigDecimal containing the CleanablePercentLt field.
     */
    public java.math.BigDecimal getCleanablePercentLt(){
        return mCleanablePercentLt;
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
