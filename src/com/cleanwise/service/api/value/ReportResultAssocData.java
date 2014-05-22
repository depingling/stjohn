
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ReportResultAssocData
 * Description:  This is a ValueObject class wrapping the database table RPT_REPORT_RESULT_ASSOC.
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
 * <code>ReportResultAssocData</code> is a ValueObject class wrapping of the database table RPT_REPORT_RESULT_ASSOC.
 */
public class ReportResultAssocData extends ValueObject
{
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = 2950044248732425909L;
    
    private int mReportResultAssocId;// SQL type:NUMBER, not null
    private int mReportResultId;// SQL type:NUMBER, not null
    private int mAssocRefId;// SQL type:NUMBER, not null
    private String mReportResultAssocCd;// SQL type:VARCHAR2, not null
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2 

    /**
     * Constructor.
     */
    private ReportResultAssocData ()
    {
        mReportResultAssocCd = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor. 
     */
    public ReportResultAssocData(int parm1, int parm2, int parm3, String parm4, Date parm5, String parm6, Date parm7, String parm8)
    {
        mReportResultAssocId = parm1;
        mReportResultId = parm2;
        mAssocRefId = parm3;
        mReportResultAssocCd = parm4;
        mAddDate = parm5;
        mAddBy = parm6;
        mModDate = parm7;
        mModBy = parm8;
        
    }

    /**
     * Creates a new ReportResultAssocData
     *
     * @return
     *  Newly initialized ReportResultAssocData object.
     */
    public static ReportResultAssocData createValue () 
    {
        ReportResultAssocData valueData = new ReportResultAssocData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ReportResultAssocData object
     */
    public String toString()
    {
        return "[" + "ReportResultAssocId=" + mReportResultAssocId + ", ReportResultId=" + mReportResultId + ", AssocRefId=" + mAssocRefId + ", ReportResultAssocCd=" + mReportResultAssocCd + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return ElementNode.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("ReportResultAssoc");
	root.setAttribute("Id", String.valueOf(mReportResultAssocId));

	Element node;

        node = doc.createElement("ReportResultId");
        node.appendChild(doc.createTextNode(String.valueOf(mReportResultId)));
        root.appendChild(node);

        node = doc.createElement("AssocRefId");
        node.appendChild(doc.createTextNode(String.valueOf(mAssocRefId)));
        root.appendChild(node);

        node = doc.createElement("ReportResultAssocCd");
        node.appendChild(doc.createTextNode(String.valueOf(mReportResultAssocCd)));
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
     * Sets the ReportResultAssocId field. This field is required to be set in the database.
     *
     * @param pReportResultAssocId
     *  int to use to update the field.
     */
    public void setReportResultAssocId(int pReportResultAssocId){
        this.mReportResultAssocId = pReportResultAssocId;
        setDirty(true);
    }
    /**
     * Retrieves the ReportResultAssocId field.
     *
     * @return
     *  int containing the ReportResultAssocId field.
     */
    public int getReportResultAssocId(){
        return mReportResultAssocId;
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
     * Sets the AssocRefId field. This field is required to be set in the database.
     *
     * @param pAssocRefId
     *  int to use to update the field.
     */
    public void setAssocRefId(int pAssocRefId){
        this.mAssocRefId = pAssocRefId;
        setDirty(true);
    }
    /**
     * Retrieves the AssocRefId field.
     *
     * @return
     *  int containing the AssocRefId field.
     */
    public int getAssocRefId(){
        return mAssocRefId;
    }

    /**
     * Sets the ReportResultAssocCd field. This field is required to be set in the database.
     *
     * @param pReportResultAssocCd
     *  String to use to update the field.
     */
    public void setReportResultAssocCd(String pReportResultAssocCd){
        this.mReportResultAssocCd = pReportResultAssocCd;
        setDirty(true);
    }
    /**
     * Retrieves the ReportResultAssocCd field.
     *
     * @return
     *  String containing the ReportResultAssocCd field.
     */
    public String getReportResultAssocCd(){
        return mReportResultAssocCd;
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
