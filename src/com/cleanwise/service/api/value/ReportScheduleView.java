
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ReportScheduleView
 * Description:  This is a ViewObject class for UI.
 * Purpose:      
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ViewObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import java.lang.*;
import java.util.*;
import java.io.*;
import org.w3c.dom.*;

import java.util.Date;


/**
 * <code>ReportScheduleView</code> is a ViewObject class for UI.
 */
public class ReportScheduleView
extends ValueObject
{
   
    private static final long serialVersionUID = 3573020625688392242L;
    private int mReportScheduleId;
    private int mGenericReportId;
    private String mReportCategory;
    private String mReportName;
    private String mReportParameters;
    private String mScheduleInfo;
    private Date mLastRunDate;
    private String mScheduleName;

    /**
     * Constructor.
     */
    public ReportScheduleView ()
    {
        mReportCategory = "";
        mReportName = "";
        mReportParameters = "";
        mScheduleInfo = "";
        mScheduleName = "";
    }

    /**
     * Constructor. 
     */
    public ReportScheduleView(int parm1, int parm2, String parm3, String parm4, String parm5, String parm6, Date parm7, String parm8)
    {
        mReportScheduleId = parm1;
        mGenericReportId = parm2;
        mReportCategory = parm3;
        mReportName = parm4;
        mReportParameters = parm5;
        mScheduleInfo = parm6;
        mLastRunDate = parm7;
        mScheduleName = parm8;
        
    }

    /**
     * Creates a new ReportScheduleView
     *
     * @return
     *  Newly initialized ReportScheduleView object.
     */
    public static ReportScheduleView createValue () 
    {
        ReportScheduleView valueView = new ReportScheduleView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ReportScheduleView object
     */
    public String toString()
    {
        return "[" + "ReportScheduleId=" + mReportScheduleId + ", GenericReportId=" + mGenericReportId + ", ReportCategory=" + mReportCategory + ", ReportName=" + mReportName + ", ReportParameters=" + mReportParameters + ", ScheduleInfo=" + mScheduleInfo + ", LastRunDate=" + mLastRunDate + ", ScheduleName=" + mScheduleName + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("ReportSchedule");
	root.setAttribute("Id", String.valueOf(mReportScheduleId));

	Element node;

        node = doc.createElement("GenericReportId");
        node.appendChild(doc.createTextNode(String.valueOf(mGenericReportId)));
        root.appendChild(node);

        node = doc.createElement("ReportCategory");
        node.appendChild(doc.createTextNode(String.valueOf(mReportCategory)));
        root.appendChild(node);

        node = doc.createElement("ReportName");
        node.appendChild(doc.createTextNode(String.valueOf(mReportName)));
        root.appendChild(node);

        node = doc.createElement("ReportParameters");
        node.appendChild(doc.createTextNode(String.valueOf(mReportParameters)));
        root.appendChild(node);

        node = doc.createElement("ScheduleInfo");
        node.appendChild(doc.createTextNode(String.valueOf(mScheduleInfo)));
        root.appendChild(node);

        node = doc.createElement("LastRunDate");
        node.appendChild(doc.createTextNode(String.valueOf(mLastRunDate)));
        root.appendChild(node);

        node = doc.createElement("ScheduleName");
        node.appendChild(doc.createTextNode(String.valueOf(mScheduleName)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public ReportScheduleView copy()  {
      ReportScheduleView obj = new ReportScheduleView();
      obj.setReportScheduleId(mReportScheduleId);
      obj.setGenericReportId(mGenericReportId);
      obj.setReportCategory(mReportCategory);
      obj.setReportName(mReportName);
      obj.setReportParameters(mReportParameters);
      obj.setScheduleInfo(mScheduleInfo);
      obj.setLastRunDate(mLastRunDate);
      obj.setScheduleName(mScheduleName);
      
      return obj;
    }

    
    /**
     * Sets the ReportScheduleId property.
     *
     * @param pReportScheduleId
     *  int to use to update the property.
     */
    public void setReportScheduleId(int pReportScheduleId){
        this.mReportScheduleId = pReportScheduleId;
    }
    /**
     * Retrieves the ReportScheduleId property.
     *
     * @return
     *  int containing the ReportScheduleId property.
     */
    public int getReportScheduleId(){
        return mReportScheduleId;
    }


    /**
     * Sets the GenericReportId property.
     *
     * @param pGenericReportId
     *  int to use to update the property.
     */
    public void setGenericReportId(int pGenericReportId){
        this.mGenericReportId = pGenericReportId;
    }
    /**
     * Retrieves the GenericReportId property.
     *
     * @return
     *  int containing the GenericReportId property.
     */
    public int getGenericReportId(){
        return mGenericReportId;
    }


    /**
     * Sets the ReportCategory property.
     *
     * @param pReportCategory
     *  String to use to update the property.
     */
    public void setReportCategory(String pReportCategory){
        this.mReportCategory = pReportCategory;
    }
    /**
     * Retrieves the ReportCategory property.
     *
     * @return
     *  String containing the ReportCategory property.
     */
    public String getReportCategory(){
        return mReportCategory;
    }


    /**
     * Sets the ReportName property.
     *
     * @param pReportName
     *  String to use to update the property.
     */
    public void setReportName(String pReportName){
        this.mReportName = pReportName;
    }
    /**
     * Retrieves the ReportName property.
     *
     * @return
     *  String containing the ReportName property.
     */
    public String getReportName(){
        return mReportName;
    }


    /**
     * Sets the ReportParameters property.
     *
     * @param pReportParameters
     *  String to use to update the property.
     */
    public void setReportParameters(String pReportParameters){
        this.mReportParameters = pReportParameters;
    }
    /**
     * Retrieves the ReportParameters property.
     *
     * @return
     *  String containing the ReportParameters property.
     */
    public String getReportParameters(){
        return mReportParameters;
    }


    /**
     * Sets the ScheduleInfo property.
     *
     * @param pScheduleInfo
     *  String to use to update the property.
     */
    public void setScheduleInfo(String pScheduleInfo){
        this.mScheduleInfo = pScheduleInfo;
    }
    /**
     * Retrieves the ScheduleInfo property.
     *
     * @return
     *  String containing the ScheduleInfo property.
     */
    public String getScheduleInfo(){
        return mScheduleInfo;
    }


    /**
     * Sets the LastRunDate property.
     *
     * @param pLastRunDate
     *  Date to use to update the property.
     */
    public void setLastRunDate(Date pLastRunDate){
        this.mLastRunDate = pLastRunDate;
    }
    /**
     * Retrieves the LastRunDate property.
     *
     * @return
     *  Date containing the LastRunDate property.
     */
    public Date getLastRunDate(){
        return mLastRunDate;
    }


    /**
     * Sets the ScheduleName property.
     *
     * @param pScheduleName
     *  String to use to update the property.
     */
    public void setScheduleName(String pScheduleName){
        this.mScheduleName = pScheduleName;
    }
    /**
     * Retrieves the ScheduleName property.
     *
     * @return
     *  String containing the ScheduleName property.
     */
    public String getScheduleName(){
        return mScheduleName;
    }


    
}
