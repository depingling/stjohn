
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        CleaningSchedFilterData
 * Description:  This is a ValueObject class wrapping the database table CLW_CLEANING_SCHED_FILTER.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.CleaningSchedFilterDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>CleaningSchedFilterData</code> is a ValueObject class wrapping of the database table CLW_CLEANING_SCHED_FILTER.
 */
public class CleaningSchedFilterData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 6998925894514808426L;
    private int mCleaningSchedFilterId;// SQL type:NUMBER, not null
    private int mCleaningScheduleId;// SQL type:NUMBER, not null
    private String mPropertyName;// SQL type:VARCHAR2, not null
    private String mFilterOperatorCd;// SQL type:VARCHAR2, not null
    private String mPropertyValue;// SQL type:VARCHAR2, not null
    private int mFilterGroupNum;// SQL type:NUMBER, not null
    private Date mAddDate;// SQL type:DATE
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public CleaningSchedFilterData ()
    {
        mPropertyName = "";
        mFilterOperatorCd = "";
        mPropertyValue = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public CleaningSchedFilterData(int parm1, int parm2, String parm3, String parm4, String parm5, int parm6, Date parm7, String parm8, Date parm9, String parm10)
    {
        mCleaningSchedFilterId = parm1;
        mCleaningScheduleId = parm2;
        mPropertyName = parm3;
        mFilterOperatorCd = parm4;
        mPropertyValue = parm5;
        mFilterGroupNum = parm6;
        mAddDate = parm7;
        mAddBy = parm8;
        mModDate = parm9;
        mModBy = parm10;
        
    }

    /**
     * Creates a new CleaningSchedFilterData
     *
     * @return
     *  Newly initialized CleaningSchedFilterData object.
     */
    public static CleaningSchedFilterData createValue ()
    {
        CleaningSchedFilterData valueData = new CleaningSchedFilterData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this CleaningSchedFilterData object
     */
    public String toString()
    {
        return "[" + "CleaningSchedFilterId=" + mCleaningSchedFilterId + ", CleaningScheduleId=" + mCleaningScheduleId + ", PropertyName=" + mPropertyName + ", FilterOperatorCd=" + mFilterOperatorCd + ", PropertyValue=" + mPropertyValue + ", FilterGroupNum=" + mFilterGroupNum + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("CleaningSchedFilter");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mCleaningSchedFilterId));

        node =  doc.createElement("CleaningScheduleId");
        node.appendChild(doc.createTextNode(String.valueOf(mCleaningScheduleId)));
        root.appendChild(node);

        node =  doc.createElement("PropertyName");
        node.appendChild(doc.createTextNode(String.valueOf(mPropertyName)));
        root.appendChild(node);

        node =  doc.createElement("FilterOperatorCd");
        node.appendChild(doc.createTextNode(String.valueOf(mFilterOperatorCd)));
        root.appendChild(node);

        node =  doc.createElement("PropertyValue");
        node.appendChild(doc.createTextNode(String.valueOf(mPropertyValue)));
        root.appendChild(node);

        node =  doc.createElement("FilterGroupNum");
        node.appendChild(doc.createTextNode(String.valueOf(mFilterGroupNum)));
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
    * creates a clone of this object, the CleaningSchedFilterId field is not cloned.
    *
    * @return CleaningSchedFilterData object
    */
    public Object clone(){
        CleaningSchedFilterData myClone = new CleaningSchedFilterData();
        
        myClone.mCleaningScheduleId = mCleaningScheduleId;
        
        myClone.mPropertyName = mPropertyName;
        
        myClone.mFilterOperatorCd = mFilterOperatorCd;
        
        myClone.mPropertyValue = mPropertyValue;
        
        myClone.mFilterGroupNum = mFilterGroupNum;
        
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

        if (CleaningSchedFilterDataAccess.CLEANING_SCHED_FILTER_ID.equals(pFieldName)) {
            return getCleaningSchedFilterId();
        } else if (CleaningSchedFilterDataAccess.CLEANING_SCHEDULE_ID.equals(pFieldName)) {
            return getCleaningScheduleId();
        } else if (CleaningSchedFilterDataAccess.PROPERTY_NAME.equals(pFieldName)) {
            return getPropertyName();
        } else if (CleaningSchedFilterDataAccess.FILTER_OPERATOR_CD.equals(pFieldName)) {
            return getFilterOperatorCd();
        } else if (CleaningSchedFilterDataAccess.PROPERTY_VALUE.equals(pFieldName)) {
            return getPropertyValue();
        } else if (CleaningSchedFilterDataAccess.FILTER_GROUP_NUM.equals(pFieldName)) {
            return getFilterGroupNum();
        } else if (CleaningSchedFilterDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (CleaningSchedFilterDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (CleaningSchedFilterDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (CleaningSchedFilterDataAccess.MOD_BY.equals(pFieldName)) {
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
        return CleaningSchedFilterDataAccess.CLW_CLEANING_SCHED_FILTER;
    }

    
    /**
     * Sets the CleaningSchedFilterId field. This field is required to be set in the database.
     *
     * @param pCleaningSchedFilterId
     *  int to use to update the field.
     */
    public void setCleaningSchedFilterId(int pCleaningSchedFilterId){
        this.mCleaningSchedFilterId = pCleaningSchedFilterId;
        setDirty(true);
    }
    /**
     * Retrieves the CleaningSchedFilterId field.
     *
     * @return
     *  int containing the CleaningSchedFilterId field.
     */
    public int getCleaningSchedFilterId(){
        return mCleaningSchedFilterId;
    }

    /**
     * Sets the CleaningScheduleId field. This field is required to be set in the database.
     *
     * @param pCleaningScheduleId
     *  int to use to update the field.
     */
    public void setCleaningScheduleId(int pCleaningScheduleId){
        this.mCleaningScheduleId = pCleaningScheduleId;
        setDirty(true);
    }
    /**
     * Retrieves the CleaningScheduleId field.
     *
     * @return
     *  int containing the CleaningScheduleId field.
     */
    public int getCleaningScheduleId(){
        return mCleaningScheduleId;
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
     * Sets the FilterOperatorCd field. This field is required to be set in the database.
     *
     * @param pFilterOperatorCd
     *  String to use to update the field.
     */
    public void setFilterOperatorCd(String pFilterOperatorCd){
        this.mFilterOperatorCd = pFilterOperatorCd;
        setDirty(true);
    }
    /**
     * Retrieves the FilterOperatorCd field.
     *
     * @return
     *  String containing the FilterOperatorCd field.
     */
    public String getFilterOperatorCd(){
        return mFilterOperatorCd;
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
     * Sets the FilterGroupNum field. This field is required to be set in the database.
     *
     * @param pFilterGroupNum
     *  int to use to update the field.
     */
    public void setFilterGroupNum(int pFilterGroupNum){
        this.mFilterGroupNum = pFilterGroupNum;
        setDirty(true);
    }
    /**
     * Retrieves the FilterGroupNum field.
     *
     * @return
     *  int containing the FilterGroupNum field.
     */
    public int getFilterGroupNum(){
        return mFilterGroupNum;
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
