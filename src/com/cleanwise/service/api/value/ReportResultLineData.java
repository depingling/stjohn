
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ReportResultLineData
 * Description:  This is a ValueObject class wrapping the database table RPT_REPORT_RESULT_LINE.
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
 * <code>ReportResultLineData</code> is a ValueObject class wrapping of the database table RPT_REPORT_RESULT_LINE.
 */
public class ReportResultLineData extends ValueObject
{
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = -8785646205518601559L;
    
    private int mReportResultId;// SQL type:NUMBER, not null
    private int mReportResultLineId;// SQL type:NUMBER, not null
    private String mLineValue;// SQL type:VARCHAR2
    private String mLineValue1;// SQL type:VARCHAR2
    //private Blob mLineValueBlob;// SQL type:BLOB
    private byte[] mLineValueBlob;// SQL type:BLOB
    private String mPageName;// SQL type:VARCHAR2
    private String mReportResultLineCd;// SQL type:VARCHAR2, not null 

    /**
     * Constructor.
     */
    private ReportResultLineData ()
    {
        mLineValue = "";
        mLineValue1 = "";
        mPageName = "";
        mReportResultLineCd = "";
    }

    /**
     * Constructor. 
     */
    public ReportResultLineData(int parm1, int parm2, String parm3, String parm4, byte[] parm5, String parm6, String parm7)
    {
        mReportResultId = parm1;
        mReportResultLineId = parm2;
        mLineValue = parm3;
        mLineValue1 = parm4;
        mLineValueBlob = parm5;
        mPageName = parm6;
        mReportResultLineCd = parm7;
        
    }

    /**
     * Creates a new ReportResultLineData
     *
     * @return
     *  Newly initialized ReportResultLineData object.
     */
    public static ReportResultLineData createValue () 
    {
        ReportResultLineData valueData = new ReportResultLineData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ReportResultLineData object
     */
    public String toString()
    {
        return "[" + "ReportResultId=" + mReportResultId + ", ReportResultLineId=" + mReportResultLineId + ", LineValue=" + mLineValue + ", LineValue1=" + mLineValue1 + ", LineValueBlob=" + mLineValueBlob + ", PageName=" + mPageName + ", ReportResultLineCd=" + mReportResultLineCd + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return ElementNode.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("ReportResultLine");
	root.setAttribute("Id", String.valueOf(mReportResultId));

	Element node;

        node = doc.createElement("ReportResultLineId");
        node.appendChild(doc.createTextNode(String.valueOf(mReportResultLineId)));
        root.appendChild(node);

        node = doc.createElement("LineValue");
        node.appendChild(doc.createTextNode(String.valueOf(mLineValue)));
        root.appendChild(node);

        node = doc.createElement("LineValue1");
        node.appendChild(doc.createTextNode(String.valueOf(mLineValue1)));
        root.appendChild(node);

        node = doc.createElement("LineValueBlob");
        node.appendChild(doc.createTextNode(String.valueOf(mLineValueBlob)));
        root.appendChild(node);

        node = doc.createElement("PageName");
        node.appendChild(doc.createTextNode(String.valueOf(mPageName)));
        root.appendChild(node);

        node = doc.createElement("ReportResultLineCd");
        node.appendChild(doc.createTextNode(String.valueOf(mReportResultLineCd)));
        root.appendChild(node);

        return root;
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
     * Sets the ReportResultLineId field. This field is required to be set in the database.
     *
     * @param pReportResultLineId
     *  int to use to update the field.
     */
    public void setReportResultLineId(int pReportResultLineId){
        this.mReportResultLineId = pReportResultLineId;
        setDirty(true);
    }
    /**
     * Retrieves the ReportResultLineId field.
     *
     * @return
     *  int containing the ReportResultLineId field.
     */
    public int getReportResultLineId(){
        return mReportResultLineId;
    }

    /**
     * Sets the LineValue field.
     *
     * @param pLineValue
     *  String to use to update the field.
     */
    public void setLineValue(String pLineValue){
        this.mLineValue = pLineValue;
        setDirty(true);
    }
    /**
     * Retrieves the LineValue field.
     *
     * @return
     *  String containing the LineValue field.
     */
    public String getLineValue(){
        return mLineValue;
    }

    /**
     * Sets the LineValue1 field.
     *
     * @param pLineValue1
     *  String to use to update the field.
     */
    public void setLineValue1(String pLineValue1){
        this.mLineValue1 = pLineValue1;
        setDirty(true);
    }
    /**
     * Retrieves the LineValue1 field.
     *
     * @return
     *  String containing the LineValue1 field.
     */
    public String getLineValue1(){
        return mLineValue1;
    }

    /**
     * Sets the LineValueBlob field.
     *
     * @param pLineValueBlob
     *  Blob to use to update the field.
     */
    public void setLineValueBlob(byte[] pLineValueBlob){
        this.mLineValueBlob = pLineValueBlob;
        setDirty(true);
    }
    /**
     * Retrieves the LineValueBlob field.
     *
     * @return
     *  Blob containing the LineValueBlob field.
     */
    public byte[] getLineValueBlob(){
        return mLineValueBlob;
    }

    /**
     * Sets the PageName field.
     *
     * @param pPageName
     *  String to use to update the field.
     */
    public void setPageName(String pPageName){
        this.mPageName = pPageName;
        setDirty(true);
    }
    /**
     * Retrieves the PageName field.
     *
     * @return
     *  String containing the PageName field.
     */
    public String getPageName(){
        return mPageName;
    }

    /**
     * Sets the ReportResultLineCd field. This field is required to be set in the database.
     *
     * @param pReportResultLineCd
     *  String to use to update the field.
     */
    public void setReportResultLineCd(String pReportResultLineCd){
        this.mReportResultLineCd = pReportResultLineCd;
        setDirty(true);
    }
    /**
     * Retrieves the ReportResultLineCd field.
     *
     * @return
     *  String containing the ReportResultLineCd field.
     */
    public String getReportResultLineCd(){
        return mReportResultLineCd;
    }

    
}
