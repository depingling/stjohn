
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ReportResultData
 * Description:  This is a ValueObject class wrapping the database table RPT_REPORT_RESULT.
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
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>ReportResultData</code> is a ValueObject class wrapping of the database table RPT_REPORT_RESULT.
 */
public class ReportResultData extends ValueObject
{
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = 1632673471480798694L;
    
    private int mGenericReportId;// SQL type:NUMBER
    private int mReportResultId;// SQL type:NUMBER, not null
    private int mReportScheduleId;// SQL type:NUMBER
    private int mUserId;// SQL type:NUMBER, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mProtectedFl;// SQL type:VARCHAR2
    private String mReportCategory;// SQL type:VARCHAR2, not null
    private String mReportName;// SQL type:VARCHAR2, not null
    private String mReportResultStatusCd;// SQL type:VARCHAR2 

    /**
     * Constructor.
     */
    private ReportResultData ()
    {
        mAddBy = "";
        mModBy = "";
        mProtectedFl = "";
        mReportCategory = "";
        mReportName = "";
        mReportResultStatusCd = "";
    }

    /**
     * Constructor. 
     */
    public ReportResultData(int parm1, int parm2, int parm3, int parm4, String parm5, Date parm6, String parm7, Date parm8, String parm9, String parm10, String parm11, String parm12)
    {
        mGenericReportId = parm1;
        mReportResultId = parm2;
        mReportScheduleId = parm3;
        mUserId = parm4;
        mAddBy = parm5;
        mAddDate = parm6;
        mModBy = parm7;
        mModDate = parm8;
        mProtectedFl = parm9;
        mReportCategory = parm10;
        mReportName = parm11;
        mReportResultStatusCd = parm12;
        
    }

    /**
     * Creates a new ReportResultData
     *
     * @return
     *  Newly initialized ReportResultData object.
     */
    public static ReportResultData createValue () 
    {
        ReportResultData valueData = new ReportResultData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ReportResultData object
     */
    public String toString()
    {
        return "[" + "GenericReportId=" + mGenericReportId + ", ReportResultId=" + mReportResultId + ", ReportScheduleId=" + mReportScheduleId + ", UserId=" + mUserId + ", AddBy=" + mAddBy + ", AddDate=" + mAddDate + ", ModBy=" + mModBy + ", ModDate=" + mModDate + ", ProtectedFl=" + mProtectedFl + ", ReportCategory=" + mReportCategory + ", ReportName=" + mReportName + ", ReportResultStatusCd=" + mReportResultStatusCd + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return ElementNode.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("ReportResult");
	root.setAttribute("Id", String.valueOf(mGenericReportId));

	Element node;

        node = doc.createElement("ReportResultId");
        node.appendChild(doc.createTextNode(String.valueOf(mReportResultId)));
        root.appendChild(node);

        node = doc.createElement("ReportScheduleId");
        node.appendChild(doc.createTextNode(String.valueOf(mReportScheduleId)));
        root.appendChild(node);

        node = doc.createElement("UserId");
        node.appendChild(doc.createTextNode(String.valueOf(mUserId)));
        root.appendChild(node);

        node = doc.createElement("AddBy");
        node.appendChild(doc.createTextNode(String.valueOf(mAddBy)));
        root.appendChild(node);

        node = doc.createElement("AddDate");
        node.appendChild(doc.createTextNode(String.valueOf(mAddDate)));
        root.appendChild(node);

        node = doc.createElement("ModBy");
        node.appendChild(doc.createTextNode(String.valueOf(mModBy)));
        root.appendChild(node);

        node = doc.createElement("ModDate");
        node.appendChild(doc.createTextNode(String.valueOf(mModDate)));
        root.appendChild(node);

        node = doc.createElement("ProtectedFl");
        node.appendChild(doc.createTextNode(String.valueOf(mProtectedFl)));
        root.appendChild(node);

        node = doc.createElement("ReportCategory");
        node.appendChild(doc.createTextNode(String.valueOf(mReportCategory)));
        root.appendChild(node);

        node = doc.createElement("ReportName");
        node.appendChild(doc.createTextNode(String.valueOf(mReportName)));
        root.appendChild(node);

        node = doc.createElement("ReportResultStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mReportResultStatusCd)));
        root.appendChild(node);

        return root;
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
     * Sets the ReportResultId field. This field is required to be set in the database.
     *
     * @param pReportResultId
     *  int to use to update the field.
     */
    public void setReportResultId(int pReportResultId){
        this.mReportResultId = pReportResultId;
        setDirty(true);
    }
    /**
     * Retrieves the ReportResultId field.
     *
     * @return
     *  int containing the ReportResultId field.
     */
    public int getReportResultId(){
        return mReportResultId;
    }

    /**
     * Sets the ReportScheduleId field.
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
     * Sets the UserId field. This field is required to be set in the database.
     *
     * @param pUserId
     *  int to use to update the field.
     */
    public void setUserId(int pUserId){
        this.mUserId = pUserId;
        setDirty(true);
    }
    /**
     * Retrieves the UserId field.
     *
     * @return
     *  int containing the UserId field.
     */
    public int getUserId(){
        return mUserId;
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
     * Sets the ProtectedFl field.
     *
     * @param pProtectedFl
     *  String to use to update the field.
     */
    public void setProtectedFl(String pProtectedFl){
        this.mProtectedFl = pProtectedFl;
        setDirty(true);
    }
    /**
     * Retrieves the ProtectedFl field.
     *
     * @return
     *  String containing the ProtectedFl field.
     */
    public String getProtectedFl(){
        return mProtectedFl;
    }

    /**
     * Sets the ReportCategory field. This field is required to be set in the database.
     *
     * @param pReportCategory
     *  String to use to update the field.
     */
    public void setReportCategory(String pReportCategory){
        this.mReportCategory = pReportCategory;
        setDirty(true);
    }
    /**
     * Retrieves the ReportCategory field.
     *
     * @return
     *  String containing the ReportCategory field.
     */
    public String getReportCategory(){
        return mReportCategory;
    }

    /**
     * Sets the ReportName field. This field is required to be set in the database.
     *
     * @param pReportName
     *  String to use to update the field.
     */
    public void setReportName(String pReportName){
        this.mReportName = pReportName;
        setDirty(true);
    }
    /**
     * Retrieves the ReportName field.
     *
     * @return
     *  String containing the ReportName field.
     */
    public String getReportName(){
        return mReportName;
    }

    /**
     * Sets the ReportResultStatusCd field.
     *
     * @param pReportResultStatusCd
     *  String to use to update the field.
     */
    public void setReportResultStatusCd(String pReportResultStatusCd){
        this.mReportResultStatusCd = pReportResultStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the ReportResultStatusCd field.
     *
     * @return
     *  String containing the ReportResultStatusCd field.
     */
    public String getReportResultStatusCd(){
        return mReportResultStatusCd;
    }

    
}
