
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ReportScheduleData
 * Description:  This is a ValueObject class wrapping the database table RPT_REPORT_SCHEDULE.
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
 * <code>ReportScheduleData</code> is a ValueObject class wrapping of the database table RPT_REPORT_SCHEDULE.
 */
public class ReportScheduleData extends ValueObject
{
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = -6736608680550497444L;
    
    private int mReportScheduleId;// SQL type:NUMBER, not null
    private String mShortDesc;// SQL type:VARCHAR2
    private int mGenericReportId;// SQL type:NUMBER
    private String mReportScheduleStatusCd;// SQL type:VARCHAR2
    private String mReportScheduleRuleCd;// SQL type:VARCHAR2
    private int mCycle;// SQL type:NUMBER
    private Date mLastRunDate;// SQL type:DATE
    private Date mAddDate;// SQL type:DATE
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE
    private String mModBy;// SQL type:VARCHAR2 

    /**
     * Constructor.
     */
    private ReportScheduleData ()
    {
        mShortDesc = "";
        mReportScheduleStatusCd = "";
        mReportScheduleRuleCd = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor. 
     */
    public ReportScheduleData(int parm1, String parm2, int parm3, String parm4, String parm5, int parm6, Date parm7, Date parm8, String parm9, Date parm10, String parm11)
    {
        mReportScheduleId = parm1;
        mShortDesc = parm2;
        mGenericReportId = parm3;
        mReportScheduleStatusCd = parm4;
        mReportScheduleRuleCd = parm5;
        mCycle = parm6;
        mLastRunDate = parm7;
        mAddDate = parm8;
        mAddBy = parm9;
        mModDate = parm10;
        mModBy = parm11;
        
    }

    /**
     * Creates a new ReportScheduleData
     *
     * @return
     *  Newly initialized ReportScheduleData object.
     */
    public static ReportScheduleData createValue () 
    {
        ReportScheduleData valueData = new ReportScheduleData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ReportScheduleData object
     */
    public String toString()
    {
        return "[" + "ReportScheduleId=" + mReportScheduleId + ", ShortDesc=" + mShortDesc + ", GenericReportId=" + mGenericReportId + ", ReportScheduleStatusCd=" + mReportScheduleStatusCd + ", ReportScheduleRuleCd=" + mReportScheduleRuleCd + ", Cycle=" + mCycle + ", LastRunDate=" + mLastRunDate + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return ElementNode.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("ReportSchedule");
	root.setAttribute("Id", String.valueOf(mReportScheduleId));

	Element node;

        node = doc.createElement("ShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mShortDesc)));
        root.appendChild(node);

        node = doc.createElement("GenericReportId");
        node.appendChild(doc.createTextNode(String.valueOf(mGenericReportId)));
        root.appendChild(node);

        node = doc.createElement("ReportScheduleStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mReportScheduleStatusCd)));
        root.appendChild(node);

        node = doc.createElement("ReportScheduleRuleCd");
        node.appendChild(doc.createTextNode(String.valueOf(mReportScheduleRuleCd)));
        root.appendChild(node);

        node = doc.createElement("Cycle");
        node.appendChild(doc.createTextNode(String.valueOf(mCycle)));
        root.appendChild(node);

        node = doc.createElement("LastRunDate");
        node.appendChild(doc.createTextNode(String.valueOf(mLastRunDate)));
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
     * Sets the ShortDesc field.
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
     * Sets the GenericReportId field.
     *
     * @param pGenericReportId
     *  int to use to update the field.
     */
    public void setGenericReportId(int pGenericReportId){
        this.mGenericReportId = pGenericReportId;
        setDirty(true);
    }
    /**
     * Retrieves the GenericReportId field.
     *
     * @return
     *  int containing the GenericReportId field.
     */
    public int getGenericReportId(){
        return mGenericReportId;
    }

    /**
     * Sets the ReportScheduleStatusCd field.
     *
     * @param pReportScheduleStatusCd
     *  String to use to update the field.
     */
    public void setReportScheduleStatusCd(String pReportScheduleStatusCd){
        this.mReportScheduleStatusCd = pReportScheduleStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the ReportScheduleStatusCd field.
     *
     * @return
     *  String containing the ReportScheduleStatusCd field.
     */
    public String getReportScheduleStatusCd(){
        return mReportScheduleStatusCd;
    }

    /**
     * Sets the ReportScheduleRuleCd field.
     *
     * @param pReportScheduleRuleCd
     *  String to use to update the field.
     */
    public void setReportScheduleRuleCd(String pReportScheduleRuleCd){
        this.mReportScheduleRuleCd = pReportScheduleRuleCd;
        setDirty(true);
    }
    /**
     * Retrieves the ReportScheduleRuleCd field.
     *
     * @return
     *  String containing the ReportScheduleRuleCd field.
     */
    public String getReportScheduleRuleCd(){
        return mReportScheduleRuleCd;
    }

    /**
     * Sets the Cycle field.
     *
     * @param pCycle
     *  int to use to update the field.
     */
    public void setCycle(int pCycle){
        this.mCycle = pCycle;
        setDirty(true);
    }
    /**
     * Retrieves the Cycle field.
     *
     * @return
     *  int containing the Cycle field.
     */
    public int getCycle(){
        return mCycle;
    }

    /**
     * Sets the LastRunDate field.
     *
     * @param pLastRunDate
     *  Date to use to update the field.
     */
    public void setLastRunDate(Date pLastRunDate){
        this.mLastRunDate = pLastRunDate;
        setDirty(true);
    }
    /**
     * Retrieves the LastRunDate field.
     *
     * @return
     *  Date containing the LastRunDate field.
     */
    public Date getLastRunDate(){
        return mLastRunDate;
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
