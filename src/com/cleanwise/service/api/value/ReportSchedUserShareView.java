
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ReportSchedUserShareView
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
 * <code>ReportSchedUserShareView</code> is a ViewObject class for UI.
 */
public class ReportSchedUserShareView
extends ValueObject
{
   
    private static final long serialVersionUID = -1969676691256772449L;
    private int mReportScheduleId;
    private int mUserId;
    private String mUserFirstName;
    private String mUserLastName;
    private String mUserLoginName;
    private String mUserTypeCd;
    private String mUserStatusCd;
    private boolean mNotifyFl;
    private boolean mReportOwnerFl;

    /**
     * Constructor.
     */
    public ReportSchedUserShareView ()
    {
        mUserFirstName = "";
        mUserLastName = "";
        mUserLoginName = "";
        mUserTypeCd = "";
        mUserStatusCd = "";
    }

    /**
     * Constructor. 
     */
    public ReportSchedUserShareView(int parm1, int parm2, String parm3, String parm4, String parm5, String parm6, String parm7, boolean parm8, boolean parm9)
    {
        mReportScheduleId = parm1;
        mUserId = parm2;
        mUserFirstName = parm3;
        mUserLastName = parm4;
        mUserLoginName = parm5;
        mUserTypeCd = parm6;
        mUserStatusCd = parm7;
        mNotifyFl = parm8;
        mReportOwnerFl = parm9;
        
    }

    /**
     * Creates a new ReportSchedUserShareView
     *
     * @return
     *  Newly initialized ReportSchedUserShareView object.
     */
    public static ReportSchedUserShareView createValue () 
    {
        ReportSchedUserShareView valueView = new ReportSchedUserShareView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ReportSchedUserShareView object
     */
    public String toString()
    {
        return "[" + "ReportScheduleId=" + mReportScheduleId + ", UserId=" + mUserId + ", UserFirstName=" + mUserFirstName + ", UserLastName=" + mUserLastName + ", UserLoginName=" + mUserLoginName + ", UserTypeCd=" + mUserTypeCd + ", UserStatusCd=" + mUserStatusCd + ", NotifyFl=" + mNotifyFl + ", ReportOwnerFl=" + mReportOwnerFl + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("ReportSchedUserShare");
	root.setAttribute("Id", String.valueOf(mReportScheduleId));

	Element node;

        node = doc.createElement("UserId");
        node.appendChild(doc.createTextNode(String.valueOf(mUserId)));
        root.appendChild(node);

        node = doc.createElement("UserFirstName");
        node.appendChild(doc.createTextNode(String.valueOf(mUserFirstName)));
        root.appendChild(node);

        node = doc.createElement("UserLastName");
        node.appendChild(doc.createTextNode(String.valueOf(mUserLastName)));
        root.appendChild(node);

        node = doc.createElement("UserLoginName");
        node.appendChild(doc.createTextNode(String.valueOf(mUserLoginName)));
        root.appendChild(node);

        node = doc.createElement("UserTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mUserTypeCd)));
        root.appendChild(node);

        node = doc.createElement("UserStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mUserStatusCd)));
        root.appendChild(node);

        node = doc.createElement("NotifyFl");
        node.appendChild(doc.createTextNode(String.valueOf(mNotifyFl)));
        root.appendChild(node);

        node = doc.createElement("ReportOwnerFl");
        node.appendChild(doc.createTextNode(String.valueOf(mReportOwnerFl)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public ReportSchedUserShareView copy()  {
      ReportSchedUserShareView obj = new ReportSchedUserShareView();
      obj.setReportScheduleId(mReportScheduleId);
      obj.setUserId(mUserId);
      obj.setUserFirstName(mUserFirstName);
      obj.setUserLastName(mUserLastName);
      obj.setUserLoginName(mUserLoginName);
      obj.setUserTypeCd(mUserTypeCd);
      obj.setUserStatusCd(mUserStatusCd);
      obj.setNotifyFl(mNotifyFl);
      obj.setReportOwnerFl(mReportOwnerFl);
      
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
     * Sets the UserId property.
     *
     * @param pUserId
     *  int to use to update the property.
     */
    public void setUserId(int pUserId){
        this.mUserId = pUserId;
    }
    /**
     * Retrieves the UserId property.
     *
     * @return
     *  int containing the UserId property.
     */
    public int getUserId(){
        return mUserId;
    }


    /**
     * Sets the UserFirstName property.
     *
     * @param pUserFirstName
     *  String to use to update the property.
     */
    public void setUserFirstName(String pUserFirstName){
        this.mUserFirstName = pUserFirstName;
    }
    /**
     * Retrieves the UserFirstName property.
     *
     * @return
     *  String containing the UserFirstName property.
     */
    public String getUserFirstName(){
        return mUserFirstName;
    }


    /**
     * Sets the UserLastName property.
     *
     * @param pUserLastName
     *  String to use to update the property.
     */
    public void setUserLastName(String pUserLastName){
        this.mUserLastName = pUserLastName;
    }
    /**
     * Retrieves the UserLastName property.
     *
     * @return
     *  String containing the UserLastName property.
     */
    public String getUserLastName(){
        return mUserLastName;
    }


    /**
     * Sets the UserLoginName property.
     *
     * @param pUserLoginName
     *  String to use to update the property.
     */
    public void setUserLoginName(String pUserLoginName){
        this.mUserLoginName = pUserLoginName;
    }
    /**
     * Retrieves the UserLoginName property.
     *
     * @return
     *  String containing the UserLoginName property.
     */
    public String getUserLoginName(){
        return mUserLoginName;
    }


    /**
     * Sets the UserTypeCd property.
     *
     * @param pUserTypeCd
     *  String to use to update the property.
     */
    public void setUserTypeCd(String pUserTypeCd){
        this.mUserTypeCd = pUserTypeCd;
    }
    /**
     * Retrieves the UserTypeCd property.
     *
     * @return
     *  String containing the UserTypeCd property.
     */
    public String getUserTypeCd(){
        return mUserTypeCd;
    }


    /**
     * Sets the UserStatusCd property.
     *
     * @param pUserStatusCd
     *  String to use to update the property.
     */
    public void setUserStatusCd(String pUserStatusCd){
        this.mUserStatusCd = pUserStatusCd;
    }
    /**
     * Retrieves the UserStatusCd property.
     *
     * @return
     *  String containing the UserStatusCd property.
     */
    public String getUserStatusCd(){
        return mUserStatusCd;
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


    /**
     * Sets the ReportOwnerFl property.
     *
     * @param pReportOwnerFl
     *  boolean to use to update the property.
     */
    public void setReportOwnerFl(boolean pReportOwnerFl){
        this.mReportOwnerFl = pReportOwnerFl;
    }
    /**
     * Retrieves the ReportOwnerFl property.
     *
     * @return
     *  boolean containing the ReportOwnerFl property.
     */
    public boolean getReportOwnerFl(){
        return mReportOwnerFl;
    }


    
}
