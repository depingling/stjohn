
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        FacilityPropertyData
 * Description:  This is a ValueObject class wrapping the database table CLW_FACILITY_PROPERTY.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.FacilityPropertyDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>FacilityPropertyData</code> is a ValueObject class wrapping of the database table CLW_FACILITY_PROPERTY.
 */
public class FacilityPropertyData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 8779411337682215821L;
    private int mFacilityPropertyId;// SQL type:NUMBER, not null
    private int mEstimatorFacilityId;// SQL type:NUMBER, not null
    private String mPropertyName;// SQL type:VARCHAR2, not null
    private String mPropertyValue;// SQL type:VARCHAR2, not null
    private Date mAddDate;// SQL type:DATE
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public FacilityPropertyData ()
    {
        mPropertyName = "";
        mPropertyValue = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public FacilityPropertyData(int parm1, int parm2, String parm3, String parm4, Date parm5, String parm6, Date parm7, String parm8)
    {
        mFacilityPropertyId = parm1;
        mEstimatorFacilityId = parm2;
        mPropertyName = parm3;
        mPropertyValue = parm4;
        mAddDate = parm5;
        mAddBy = parm6;
        mModDate = parm7;
        mModBy = parm8;
        
    }

    /**
     * Creates a new FacilityPropertyData
     *
     * @return
     *  Newly initialized FacilityPropertyData object.
     */
    public static FacilityPropertyData createValue ()
    {
        FacilityPropertyData valueData = new FacilityPropertyData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this FacilityPropertyData object
     */
    public String toString()
    {
        return "[" + "FacilityPropertyId=" + mFacilityPropertyId + ", EstimatorFacilityId=" + mEstimatorFacilityId + ", PropertyName=" + mPropertyName + ", PropertyValue=" + mPropertyValue + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("FacilityProperty");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mFacilityPropertyId));

        node =  doc.createElement("EstimatorFacilityId");
        node.appendChild(doc.createTextNode(String.valueOf(mEstimatorFacilityId)));
        root.appendChild(node);

        node =  doc.createElement("PropertyName");
        node.appendChild(doc.createTextNode(String.valueOf(mPropertyName)));
        root.appendChild(node);

        node =  doc.createElement("PropertyValue");
        node.appendChild(doc.createTextNode(String.valueOf(mPropertyValue)));
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
    * creates a clone of this object, the FacilityPropertyId field is not cloned.
    *
    * @return FacilityPropertyData object
    */
    public Object clone(){
        FacilityPropertyData myClone = new FacilityPropertyData();
        
        myClone.mEstimatorFacilityId = mEstimatorFacilityId;
        
        myClone.mPropertyName = mPropertyName;
        
        myClone.mPropertyValue = mPropertyValue;
        
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

        if (FacilityPropertyDataAccess.FACILITY_PROPERTY_ID.equals(pFieldName)) {
            return getFacilityPropertyId();
        } else if (FacilityPropertyDataAccess.ESTIMATOR_FACILITY_ID.equals(pFieldName)) {
            return getEstimatorFacilityId();
        } else if (FacilityPropertyDataAccess.PROPERTY_NAME.equals(pFieldName)) {
            return getPropertyName();
        } else if (FacilityPropertyDataAccess.PROPERTY_VALUE.equals(pFieldName)) {
            return getPropertyValue();
        } else if (FacilityPropertyDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (FacilityPropertyDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (FacilityPropertyDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (FacilityPropertyDataAccess.MOD_BY.equals(pFieldName)) {
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
        return FacilityPropertyDataAccess.CLW_FACILITY_PROPERTY;
    }

    
    /**
     * Sets the FacilityPropertyId field. This field is required to be set in the database.
     *
     * @param pFacilityPropertyId
     *  int to use to update the field.
     */
    public void setFacilityPropertyId(int pFacilityPropertyId){
        this.mFacilityPropertyId = pFacilityPropertyId;
        setDirty(true);
    }
    /**
     * Retrieves the FacilityPropertyId field.
     *
     * @return
     *  int containing the FacilityPropertyId field.
     */
    public int getFacilityPropertyId(){
        return mFacilityPropertyId;
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
     * Sets the PropertyName field. This field is required to be set in the database.
     *
     * @param pPropertyName
     *  String to use to update the field.
     */
    public void setPropertyName(String pPropertyName){
        this.mPropertyName = pPropertyName;
        setDirty(true);
    }
    /**
     * Retrieves the PropertyName field.
     *
     * @return
     *  String containing the PropertyName field.
     */
    public String getPropertyName(){
        return mPropertyName;
    }

    /**
     * Sets the PropertyValue field. This field is required to be set in the database.
     *
     * @param pPropertyValue
     *  String to use to update the field.
     */
    public void setPropertyValue(String pPropertyValue){
        this.mPropertyValue = pPropertyValue;
        setDirty(true);
    }
    /**
     * Retrieves the PropertyValue field.
     *
     * @return
     *  String containing the PropertyValue field.
     */
    public String getPropertyValue(){
        return mPropertyValue;
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


}
