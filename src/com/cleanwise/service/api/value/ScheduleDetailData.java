
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ScheduleDetailData
 * Description:  This is a ValueObject class wrapping the database table CLW_SCHEDULE_DETAIL.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.ScheduleDetailDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>ScheduleDetailData</code> is a ValueObject class wrapping of the database table CLW_SCHEDULE_DETAIL.
 */
public class ScheduleDetailData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -1506675232837899543L;
    private int mScheduleDetailId;// SQL type:NUMBER, not null
    private int mScheduleId;// SQL type:NUMBER, not null
    private String mScheduleDetailCd;// SQL type:VARCHAR2, not null
    private String mValue;// SQL type:VARCHAR2, not null
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private String mCountryCd;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public ScheduleDetailData ()
    {
        mScheduleDetailCd = "";
        mValue = "";
        mAddBy = "";
        mModBy = "";
        mCountryCd = "";
    }

    /**
     * Constructor.
     */
    public ScheduleDetailData(int parm1, int parm2, String parm3, String parm4, Date parm5, String parm6, Date parm7, String parm8, String parm9)
    {
        mScheduleDetailId = parm1;
        mScheduleId = parm2;
        mScheduleDetailCd = parm3;
        mValue = parm4;
        mAddDate = parm5;
        mAddBy = parm6;
        mModDate = parm7;
        mModBy = parm8;
        mCountryCd = parm9;
        
    }

    /**
     * Creates a new ScheduleDetailData
     *
     * @return
     *  Newly initialized ScheduleDetailData object.
     */
    public static ScheduleDetailData createValue ()
    {
        ScheduleDetailData valueData = new ScheduleDetailData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ScheduleDetailData object
     */
    public String toString()
    {
        return "[" + "ScheduleDetailId=" + mScheduleDetailId + ", ScheduleId=" + mScheduleId + ", ScheduleDetailCd=" + mScheduleDetailCd + ", Value=" + mValue + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", CountryCd=" + mCountryCd + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("ScheduleDetail");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mScheduleDetailId));

        node =  doc.createElement("ScheduleId");
        node.appendChild(doc.createTextNode(String.valueOf(mScheduleId)));
        root.appendChild(node);

        node =  doc.createElement("ScheduleDetailCd");
        node.appendChild(doc.createTextNode(String.valueOf(mScheduleDetailCd)));
        root.appendChild(node);

        node =  doc.createElement("Value");
        node.appendChild(doc.createTextNode(String.valueOf(mValue)));
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

        node =  doc.createElement("CountryCd");
        node.appendChild(doc.createTextNode(String.valueOf(mCountryCd)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the ScheduleDetailId field is not cloned.
    *
    * @return ScheduleDetailData object
    */
    public Object clone(){
        ScheduleDetailData myClone = new ScheduleDetailData();
        
        myClone.mScheduleId = mScheduleId;
        
        myClone.mScheduleDetailCd = mScheduleDetailCd;
        
        myClone.mValue = mValue;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        myClone.mCountryCd = mCountryCd;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (ScheduleDetailDataAccess.SCHEDULE_DETAIL_ID.equals(pFieldName)) {
            return getScheduleDetailId();
        } else if (ScheduleDetailDataAccess.SCHEDULE_ID.equals(pFieldName)) {
            return getScheduleId();
        } else if (ScheduleDetailDataAccess.SCHEDULE_DETAIL_CD.equals(pFieldName)) {
            return getScheduleDetailCd();
        } else if (ScheduleDetailDataAccess.VALUE.equals(pFieldName)) {
            return getValue();
        } else if (ScheduleDetailDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (ScheduleDetailDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (ScheduleDetailDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (ScheduleDetailDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (ScheduleDetailDataAccess.COUNTRY_CD.equals(pFieldName)) {
            return getCountryCd();
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
        return ScheduleDetailDataAccess.CLW_SCHEDULE_DETAIL;
    }

    
    /**
     * Sets the ScheduleDetailId field. This field is required to be set in the database.
     *
     * @param pScheduleDetailId
     *  int to use to update the field.
     */
    public void setScheduleDetailId(int pScheduleDetailId){
        this.mScheduleDetailId = pScheduleDetailId;
        setDirty(true);
    }
    /**
     * Retrieves the ScheduleDetailId field.
     *
     * @return
     *  int containing the ScheduleDetailId field.
     */
    public int getScheduleDetailId(){
        return mScheduleDetailId;
    }

    /**
     * Sets the ScheduleId field. This field is required to be set in the database.
     *
     * @param pScheduleId
     *  int to use to update the field.
     */
    public void setScheduleId(int pScheduleId){
        this.mScheduleId = pScheduleId;
        setDirty(true);
    }
    /**
     * Retrieves the ScheduleId field.
     *
     * @return
     *  int containing the ScheduleId field.
     */
    public int getScheduleId(){
        return mScheduleId;
    }

    /**
     * Sets the ScheduleDetailCd field. This field is required to be set in the database.
     *
     * @param pScheduleDetailCd
     *  String to use to update the field.
     */
    public void setScheduleDetailCd(String pScheduleDetailCd){
        this.mScheduleDetailCd = pScheduleDetailCd;
        setDirty(true);
    }
    /**
     * Retrieves the ScheduleDetailCd field.
     *
     * @return
     *  String containing the ScheduleDetailCd field.
     */
    public String getScheduleDetailCd(){
        return mScheduleDetailCd;
    }

    /**
     * Sets the Value field. This field is required to be set in the database.
     *
     * @param pValue
     *  String to use to update the field.
     */
    public void setValue(String pValue){
        this.mValue = pValue;
        setDirty(true);
    }
    /**
     * Retrieves the Value field.
     *
     * @return
     *  String containing the Value field.
     */
    public String getValue(){
        return mValue;
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
     * Sets the CountryCd field.
     *
     * @param pCountryCd
     *  String to use to update the field.
     */
    public void setCountryCd(String pCountryCd){
        this.mCountryCd = pCountryCd;
        setDirty(true);
    }
    /**
     * Retrieves the CountryCd field.
     *
     * @return
     *  String containing the CountryCd field.
     */
    public String getCountryCd(){
        return mCountryCd;
    }


}
