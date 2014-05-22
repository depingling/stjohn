
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ReportScheduleDetailData
 * Description:  This is a ValueObject class wrapping the database table RPT_REPORT_SCHEDULE_DETAIL.
 * Purpose:      
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;

import org.w3c.dom.*;

/**
 * <code>ReportScheduleDetailData</code> is a ValueObject class wrapping of the database table RPT_REPORT_SCHEDULE_DETAIL.
 */
public class ReportScheduleDetailData extends ValueObject
{
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = 1896231131613069652L;
    
    private int mReportScheduleDetailId;// SQL type:NUMBER, not null
    private int mReportScheduleId;// SQL type:NUMBER, not null
    private String mReportScheduleDetailCd;// SQL type:VARCHAR2, not null
    private String mDetailName;// SQL type:VARCHAR2
    private String mDetailValue;// SQL type:VARCHAR2, not null
    private Date mAddDate;// SQL type:DATE
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE
    private String mModBy;// SQL type:VARCHAR2 

    /**
     * Constructor.
     */
    private ReportScheduleDetailData ()
    {
        mReportScheduleDetailCd = "";
        mDetailName = "";
        mDetailValue = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor. 
     */
    public ReportScheduleDetailData(int parm1, int parm2, String parm3, String parm4, String parm5, Date parm6, String parm7, Date parm8, String parm9)
    {
        mReportScheduleDetailId = parm1;
        mReportScheduleId = parm2;
        mReportScheduleDetailCd = parm3;
        mDetailName = parm4;
        mDetailValue = parm5;
        mAddDate = parm6;
        mAddBy = parm7;
        mModDate = parm8;
        mModBy = parm9;
        
    }

    /**
     * Creates a new ReportScheduleDetailData
     *
     * @return
     *  Newly initialized ReportScheduleDetailData object.
     */
    public static ReportScheduleDetailData createValue () 
    {
        ReportScheduleDetailData valueData = new ReportScheduleDetailData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ReportScheduleDetailData object
     */
    public String toString()
    {
        return "[" + "ReportScheduleDetailId=" + mReportScheduleDetailId + ", ReportScheduleId=" + mReportScheduleId + ", ReportScheduleDetailCd=" + mReportScheduleDetailCd + ", DetailName=" + mDetailName + ", DetailValue=" + mDetailValue + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return ElementNode.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("ReportScheduleDetail");
	root.setAttribute("Id", String.valueOf(mReportScheduleDetailId));

	Element node;

        node = doc.createElement("ReportScheduleId");
        node.appendChild(doc.createTextNode(String.valueOf(mReportScheduleId)));
        root.appendChild(node);

        node = doc.createElement("ReportScheduleDetailCd");
        node.appendChild(doc.createTextNode(String.valueOf(mReportScheduleDetailCd)));
        root.appendChild(node);

        node = doc.createElement("DetailName");
        node.appendChild(doc.createTextNode(String.valueOf(mDetailName)));
        root.appendChild(node);

        node = doc.createElement("DetailValue");
        node.appendChild(doc.createTextNode(String.valueOf(mDetailValue)));
        root.appendChild(node);

        node = doc.createElement("AddDate");
        node.appendChild(doc.createTextNode(String.valueOf(mAddDate)));
        root.appendChild(node);

        node = doc.createElement("AddBy");
        node.appendChild(doc.createTextNode(String.valueOf(mAddBy)));
        root.appendChild(node);

        node = doc.createElement("ModDate");
        node.appendChild(doc.createTextNode(String.valueOf(mModDate)));
        root.appendChild(node);

        node = doc.createElement("ModBy");
        node.appendChild(doc.createTextNode(String.valueOf(mModBy)));
        root.appendChild(node);

        return root;
    }
    
    /**
     * Sets the ReportScheduleDetailId field. This field is required to be set in the database.
     *
     * @param pReportScheduleDetailId
     *  int to use to update the field.
     */
    public void setReportScheduleDetailId(int pReportScheduleDetailId){
        this.mReportScheduleDetailId = pReportScheduleDetailId;
        setDirty(true);
    }
    /**
     * Retrieves the ReportScheduleDetailId field.
     *
     * @return
     *  int containing the ReportScheduleDetailId field.
     */
    public int getReportScheduleDetailId(){
        return mReportScheduleDetailId;
    }

    /**
     * Sets the ReportScheduleId field. This field is required to be set in the database.
     *
     * @param pReportScheduleId
     *  int to use to update the field.
     */
    public void setReportScheduleId(int pReportScheduleId){
        this.mReportScheduleId = pReportScheduleId;
        setDirty(true);
    }
    /**
     * Retrieves the ReportScheduleId field.
     *
     * @return
     *  int containing the ReportScheduleId field.
     */
    public int getReportScheduleId(){
        return mReportScheduleId;
    }

    /**
     * Sets the ReportScheduleDetailCd field. This field is required to be set in the database.
     *
     * @param pReportScheduleDetailCd
     *  String to use to update the field.
     */
    public void setReportScheduleDetailCd(String pReportScheduleDetailCd){
        this.mReportScheduleDetailCd = pReportScheduleDetailCd;
        setDirty(true);
    }
    /**
     * Retrieves the ReportScheduleDetailCd field.
     *
     * @return
     *  String containing the ReportScheduleDetailCd field.
     */
    public String getReportScheduleDetailCd(){
        return mReportScheduleDetailCd;
    }

    /**
     * Sets the DetailName field.
     *
     * @param pDetailName
     *  String to use to update the field.
     */
    public void setDetailName(String pDetailName){
        this.mDetailName = pDetailName;
        setDirty(true);
    }
    /**
     * Retrieves the DetailName field.
     *
     * @return
     *  String containing the DetailName field.
     */
    public String getDetailName(){
        return mDetailName;
    }

    /**
     * Sets the DetailValue field. This field is required to be set in the database.
     *
     * @param pDetailValue
     *  String to use to update the field.
     */
    public void setDetailValue(String pDetailValue){
        this.mDetailValue = pDetailValue;
        setDirty(true);
    }
    /**
     * Retrieves the DetailValue field.
     *
     * @return
     *  String containing the DetailValue field.
     */
    public String getDetailValue(){
        return mDetailValue;
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
