
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ReportScheduleJoinView
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

import com.cleanwise.service.api.value.*;


/**
 * <code>ReportScheduleJoinView</code> is a ViewObject class for UI.
 */
public class ReportScheduleJoinView
extends ValueObject
{
   
    private static final long serialVersionUID = -7296117156736762752L;
    private GenericReportData mReport;
    private ReportScheduleData mSchedule;
    private ReportScheduleDetailDataVector mScheduleDetails;
    private GenericReportControlViewVector mReportControls;
    private ReportSchedGroupShareViewVector mGroups;
    private ReportSchedUserShareViewVector mUsers;

    /**
     * Constructor.
     */
    public ReportScheduleJoinView ()
    {
    }

    /**
     * Constructor. 
     */
    public ReportScheduleJoinView(GenericReportData parm1, ReportScheduleData parm2, ReportScheduleDetailDataVector parm3, GenericReportControlViewVector parm4, ReportSchedGroupShareViewVector parm5, ReportSchedUserShareViewVector parm6)
    {
        mReport = parm1;
        mSchedule = parm2;
        mScheduleDetails = parm3;
        mReportControls = parm4;
        mGroups = parm5;
        mUsers = parm6;
        
    }

    /**
     * Creates a new ReportScheduleJoinView
     *
     * @return
     *  Newly initialized ReportScheduleJoinView object.
     */
    public static ReportScheduleJoinView createValue () 
    {
        ReportScheduleJoinView valueView = new ReportScheduleJoinView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ReportScheduleJoinView object
     */
    public String toString()
    {
        return "[" + "Report=" + mReport + ", Schedule=" + mSchedule + ", ScheduleDetails=" + mScheduleDetails + ", ReportControls=" + mReportControls + ", Groups=" + mGroups + ", Users=" + mUsers + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("ReportScheduleJoin");
	root.setAttribute("Id", String.valueOf(mReport));

	Element node;

        node = doc.createElement("Schedule");
        node.appendChild(doc.createTextNode(String.valueOf(mSchedule)));
        root.appendChild(node);

        node = doc.createElement("ScheduleDetails");
        node.appendChild(doc.createTextNode(String.valueOf(mScheduleDetails)));
        root.appendChild(node);

        node = doc.createElement("ReportControls");
        node.appendChild(doc.createTextNode(String.valueOf(mReportControls)));
        root.appendChild(node);

        node = doc.createElement("Groups");
        node.appendChild(doc.createTextNode(String.valueOf(mGroups)));
        root.appendChild(node);

        node = doc.createElement("Users");
        node.appendChild(doc.createTextNode(String.valueOf(mUsers)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public ReportScheduleJoinView copy()  {
      ReportScheduleJoinView obj = new ReportScheduleJoinView();
      obj.setReport(mReport);
      obj.setSchedule(mSchedule);
      obj.setScheduleDetails(mScheduleDetails);
      obj.setReportControls(mReportControls);
      obj.setGroups(mGroups);
      obj.setUsers(mUsers);
      
      return obj;
    }

    
    /**
     * Sets the Report property.
     *
     * @param pReport
     *  GenericReportData to use to update the property.
     */
    public void setReport(GenericReportData pReport){
        this.mReport = pReport;
    }
    /**
     * Retrieves the Report property.
     *
     * @return
     *  GenericReportData containing the Report property.
     */
    public GenericReportData getReport(){
        return mReport;
    }


    /**
     * Sets the Schedule property.
     *
     * @param pSchedule
     *  ReportScheduleData to use to update the property.
     */
    public void setSchedule(ReportScheduleData pSchedule){
        this.mSchedule = pSchedule;
    }
    /**
     * Retrieves the Schedule property.
     *
     * @return
     *  ReportScheduleData containing the Schedule property.
     */
    public ReportScheduleData getSchedule(){
        return mSchedule;
    }


    /**
     * Sets the ScheduleDetails property.
     *
     * @param pScheduleDetails
     *  ReportScheduleDetailDataVector to use to update the property.
     */
    public void setScheduleDetails(ReportScheduleDetailDataVector pScheduleDetails){
        this.mScheduleDetails = pScheduleDetails;
    }
    /**
     * Retrieves the ScheduleDetails property.
     *
     * @return
     *  ReportScheduleDetailDataVector containing the ScheduleDetails property.
     */
    public ReportScheduleDetailDataVector getScheduleDetails(){
        return mScheduleDetails;
    }


    /**
     * Sets the ReportControls property.
     *
     * @param pReportControls
     *  GenericReportControlViewVector to use to update the property.
     */
    public void setReportControls(GenericReportControlViewVector pReportControls){
        this.mReportControls = pReportControls;
    }
    /**
     * Retrieves the ReportControls property.
     *
     * @return
     *  GenericReportControlViewVector containing the ReportControls property.
     */
    public GenericReportControlViewVector getReportControls(){
        return mReportControls;
    }


    /**
     * Sets the Groups property.
     *
     * @param pGroups
     *  ReportSchedGroupShareViewVector to use to update the property.
     */
    public void setGroups(ReportSchedGroupShareViewVector pGroups){
        this.mGroups = pGroups;
    }
    /**
     * Retrieves the Groups property.
     *
     * @return
     *  ReportSchedGroupShareViewVector containing the Groups property.
     */
    public ReportSchedGroupShareViewVector getGroups(){
        return mGroups;
    }


    /**
     * Sets the Users property.
     *
     * @param pUsers
     *  ReportSchedUserShareViewVector to use to update the property.
     */
    public void setUsers(ReportSchedUserShareViewVector pUsers){
        this.mUsers = pUsers;
    }
    /**
     * Retrieves the Users property.
     *
     * @return
     *  ReportSchedUserShareViewVector containing the Users property.
     */
    public ReportSchedUserShareViewVector getUsers(){
        return mUsers;
    }


    
}
