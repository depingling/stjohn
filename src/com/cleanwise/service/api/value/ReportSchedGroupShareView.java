
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ReportSchedGroupShareView
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
 * <code>ReportSchedGroupShareView</code> is a ViewObject class for UI.
 */
public class ReportSchedGroupShareView
extends ValueObject
{
   
    private static final long serialVersionUID = -5510749130507296579L;
    private int mReportScheduleId;
    private int mGroupId;
    private String mGroupShortDesc;
    private String mGroupTypeCd;
    private String mGroupStatusCd;
    private boolean mNotifyFl;

    /**
     * Constructor.
     */
    public ReportSchedGroupShareView ()
    {
        mGroupShortDesc = "";
        mGroupTypeCd = "";
        mGroupStatusCd = "";
    }

    /**
     * Constructor. 
     */
    public ReportSchedGroupShareView(int parm1, int parm2, String parm3, String parm4, String parm5, boolean parm6)
    {
        mReportScheduleId = parm1;
        mGroupId = parm2;
        mGroupShortDesc = parm3;
        mGroupTypeCd = parm4;
        mGroupStatusCd = parm5;
        mNotifyFl = parm6;
        
    }

    /**
     * Creates a new ReportSchedGroupShareView
     *
     * @return
     *  Newly initialized ReportSchedGroupShareView object.
     */
    public static ReportSchedGroupShareView createValue () 
    {
        ReportSchedGroupShareView valueView = new ReportSchedGroupShareView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ReportSchedGroupShareView object
     */
    public String toString()
    {
        return "[" + "ReportScheduleId=" + mReportScheduleId + ", GroupId=" + mGroupId + ", GroupShortDesc=" + mGroupShortDesc + ", GroupTypeCd=" + mGroupTypeCd + ", GroupStatusCd=" + mGroupStatusCd + ", NotifyFl=" + mNotifyFl + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("ReportSchedGroupShare");
	root.setAttribute("Id", String.valueOf(mReportScheduleId));

	Element node;

        node = doc.createElement("GroupId");
        node.appendChild(doc.createTextNode(String.valueOf(mGroupId)));
        root.appendChild(node);

        node = doc.createElement("GroupShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mGroupShortDesc)));
        root.appendChild(node);

        node = doc.createElement("GroupTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mGroupTypeCd)));
        root.appendChild(node);

        node = doc.createElement("GroupStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mGroupStatusCd)));
        root.appendChild(node);

        node = doc.createElement("NotifyFl");
        node.appendChild(doc.createTextNode(String.valueOf(mNotifyFl)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public ReportSchedGroupShareView copy()  {
      ReportSchedGroupShareView obj = new ReportSchedGroupShareView();
      obj.setReportScheduleId(mReportScheduleId);
      obj.setGroupId(mGroupId);
      obj.setGroupShortDesc(mGroupShortDesc);
      obj.setGroupTypeCd(mGroupTypeCd);
      obj.setGroupStatusCd(mGroupStatusCd);
      obj.setNotifyFl(mNotifyFl);
      
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
     * Sets the GroupId property.
     *
     * @param pGroupId
     *  int to use to update the property.
     */
    public void setGroupId(int pGroupId){
        this.mGroupId = pGroupId;
    }
    /**
     * Retrieves the GroupId property.
     *
     * @return
     *  int containing the GroupId property.
     */
    public int getGroupId(){
        return mGroupId;
    }


    /**
     * Sets the GroupShortDesc property.
     *
     * @param pGroupShortDesc
     *  String to use to update the property.
     */
    public void setGroupShortDesc(String pGroupShortDesc){
        this.mGroupShortDesc = pGroupShortDesc;
    }
    /**
     * Retrieves the GroupShortDesc property.
     *
     * @return
     *  String containing the GroupShortDesc property.
     */
    public String getGroupShortDesc(){
        return mGroupShortDesc;
    }


    /**
     * Sets the GroupTypeCd property.
     *
     * @param pGroupTypeCd
     *  String to use to update the property.
     */
    public void setGroupTypeCd(String pGroupTypeCd){
        this.mGroupTypeCd = pGroupTypeCd;
    }
    /**
     * Retrieves the GroupTypeCd property.
     *
     * @return
     *  String containing the GroupTypeCd property.
     */
    public String getGroupTypeCd(){
        return mGroupTypeCd;
    }


    /**
     * Sets the GroupStatusCd property.
     *
     * @param pGroupStatusCd
     *  String to use to update the property.
     */
    public void setGroupStatusCd(String pGroupStatusCd){
        this.mGroupStatusCd = pGroupStatusCd;
    }
    /**
     * Retrieves the GroupStatusCd property.
     *
     * @return
     *  String containing the GroupStatusCd property.
     */
    public String getGroupStatusCd(){
        return mGroupStatusCd;
    }


    /**
     * Sets the NotifyFl property.
     *
     * @param pNotifyFl
     *  boolean to use to update the property.
     */
    public void setNotifyFl(boolean pNotifyFl){
        this.mNotifyFl = pNotifyFl;
    }
    /**
     * Retrieves the NotifyFl property.
     *
     * @return
     *  boolean containing the NotifyFl property.
     */
    public boolean getNotifyFl(){
        return mNotifyFl;
    }


    
}
