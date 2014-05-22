
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ReportResultParamData
 * Description:  This is a ValueObject class wrapping the database table RPT_REPORT_RESULT_PARAM.
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
 * <code>ReportResultParamData</code> is a ValueObject class wrapping of the database table RPT_REPORT_RESULT_PARAM.
 */
public class ReportResultParamData extends ValueObject
{
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = 4407592613900884607L;
    
    private int mReportResultParamId;// SQL type:NUMBER, not null
    private int mReportResultId;// SQL type:NUMBER, not null
    private String mParamName;// SQL type:VARCHAR2, not null
    private String mParamValue;// SQL type:VARCHAR2, not null 

    /**
     * Constructor.
     */
    private ReportResultParamData ()
    {
        mParamName = "";
        mParamValue = "";
    }

    /**
     * Constructor. 
     */
    public ReportResultParamData(int parm1, int parm2, String parm3, String parm4)
    {
        mReportResultParamId = parm1;
        mReportResultId = parm2;
        mParamName = parm3;
        mParamValue = parm4;
        
    }

    /**
     * Creates a new ReportResultParamData
     *
     * @return
     *  Newly initialized ReportResultParamData object.
     */
    public static ReportResultParamData createValue () 
    {
        ReportResultParamData valueData = new ReportResultParamData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ReportResultParamData object
     */
    public String toString()
    {
        return "[" + "ReportResultParamId=" + mReportResultParamId + ", ReportResultId=" + mReportResultId + ", ParamName=" + mParamName + ", ParamValue=" + mParamValue + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return ElementNode.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("ReportResultParam");
	root.setAttribute("Id", String.valueOf(mReportResultParamId));

	Element node;

        node = doc.createElement("ReportResultId");
        node.appendChild(doc.createTextNode(String.valueOf(mReportResultId)));
        root.appendChild(node);

        node = doc.createElement("ParamName");
        node.appendChild(doc.createTextNode(String.valueOf(mParamName)));
        root.appendChild(node);

        node = doc.createElement("ParamValue");
        node.appendChild(doc.createTextNode(String.valueOf(mParamValue)));
        root.appendChild(node);

        return root;
    }
    
    /**
     * Sets the ReportResultParamId field. This field is required to be set in the database.
     *
     * @param pReportResultParamId
     *  int to use to update the field.
     */
    public void setReportResultParamId(int pReportResultParamId){
        this.mReportResultParamId = pReportResultParamId;
        setDirty(true);
    }
    /**
     * Retrieves the ReportResultParamId field.
     *
     * @return
     *  int containing the ReportResultParamId field.
     */
    public int getReportResultParamId(){
        return mReportResultParamId;
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
     * Sets the ParamName field. This field is required to be set in the database.
     *
     * @param pParamName
     *  String to use to update the field.
     */
    public void setParamName(String pParamName){
        this.mParamName = pParamName;
        setDirty(true);
    }
    /**
     * Retrieves the ParamName field.
     *
     * @return
     *  String containing the ParamName field.
     */
    public String getParamName(){
        return mParamName;
    }

    /**
     * Sets the ParamValue field. This field is required to be set in the database.
     *
     * @param pParamValue
     *  String to use to update the field.
     */
    public void setParamValue(String pParamValue){
        this.mParamValue = pParamValue;
        setDirty(true);
    }
    /**
     * Retrieves the ParamValue field.
     *
     * @return
     *  String containing the ParamValue field.
     */
    public String getParamValue(){
        return mParamValue;
    }

    
}
