
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        GroupSearchCriteriaView
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




/**
 * <code>GroupSearchCriteriaView</code> is a ViewObject class for UI.
 */
public class GroupSearchCriteriaView
extends ValueObject
{
   
    private static final long serialVersionUID = 2436718742271252942L;
    private String mGroupName;
    private String mGroupType;
    private String mGroupStatus;
    private String mReportName;
    private String mUserName;
    private int mGroupId;
    private int mStoreId;

    /**
     * Constructor.
     */
    public GroupSearchCriteriaView ()
    {
        mGroupName = "";
        mGroupType = "";
        mGroupStatus = "";
        mReportName = "";
        mUserName = "";
    }

    /**
     * Constructor. 
     */
    public GroupSearchCriteriaView(String parm1, String parm2, String parm3, String parm4, String parm5, int parm6, int parm7)
    {
        mGroupName = parm1;
        mGroupType = parm2;
        mGroupStatus = parm3;
        mReportName = parm4;
        mUserName = parm5;
        mGroupId = parm6;
        mStoreId = parm7;
        
    }

    /**
     * Creates a new GroupSearchCriteriaView
     *
     * @return
     *  Newly initialized GroupSearchCriteriaView object.
     */
    public static GroupSearchCriteriaView createValue () 
    {
        GroupSearchCriteriaView valueView = new GroupSearchCriteriaView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this GroupSearchCriteriaView object
     */
    public String toString()
    {
        return "[" + "GroupName=" + mGroupName + ", GroupType=" + mGroupType + ", GroupStatus=" + mGroupStatus + ", ReportName=" + mReportName + ", UserName=" + mUserName + ", GroupId=" + mGroupId + ", StoreId=" + mStoreId + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("GroupSearchCriteria");
	root.setAttribute("Id", String.valueOf(mGroupName));

	Element node;

        node = doc.createElement("GroupType");
        node.appendChild(doc.createTextNode(String.valueOf(mGroupType)));
        root.appendChild(node);

        node = doc.createElement("GroupStatus");
        node.appendChild(doc.createTextNode(String.valueOf(mGroupStatus)));
        root.appendChild(node);

        node = doc.createElement("ReportName");
        node.appendChild(doc.createTextNode(String.valueOf(mReportName)));
        root.appendChild(node);

        node = doc.createElement("UserName");
        node.appendChild(doc.createTextNode(String.valueOf(mUserName)));
        root.appendChild(node);

        node = doc.createElement("GroupId");
        node.appendChild(doc.createTextNode(String.valueOf(mGroupId)));
        root.appendChild(node);

        node = doc.createElement("StoreId");
        node.appendChild(doc.createTextNode(String.valueOf(mStoreId)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public GroupSearchCriteriaView copy()  {
      GroupSearchCriteriaView obj = new GroupSearchCriteriaView();
      obj.setGroupName(mGroupName);
      obj.setGroupType(mGroupType);
      obj.setGroupStatus(mGroupStatus);
      obj.setReportName(mReportName);
      obj.setUserName(mUserName);
      obj.setGroupId(mGroupId);
      obj.setStoreId(mStoreId);
      
      return obj;
    }

    
    /**
     * Sets the GroupName property.
     *
     * @param pGroupName
     *  String to use to update the property.
     */
    public void setGroupName(String pGroupName){
        this.mGroupName = pGroupName;
    }
    /**
     * Retrieves the GroupName property.
     *
     * @return
     *  String containing the GroupName property.
     */
    public String getGroupName(){
        return mGroupName;
    }


    /**
     * Sets the GroupType property.
     *
     * @param pGroupType
     *  String to use to update the property.
     */
    public void setGroupType(String pGroupType){
        this.mGroupType = pGroupType;
    }
    /**
     * Retrieves the GroupType property.
     *
     * @return
     *  String containing the GroupType property.
     */
    public String getGroupType(){
        return mGroupType;
    }


    /**
     * Sets the GroupStatus property.
     *
     * @param pGroupStatus
     *  String to use to update the property.
     */
    public void setGroupStatus(String pGroupStatus){
        this.mGroupStatus = pGroupStatus;
    }
    /**
     * Retrieves the GroupStatus property.
     *
     * @return
     *  String containing the GroupStatus property.
     */
    public String getGroupStatus(){
        return mGroupStatus;
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
     * Sets the UserName property.
     *
     * @param pUserName
     *  String to use to update the property.
     */
    public void setUserName(String pUserName){
        this.mUserName = pUserName;
    }
    /**
     * Retrieves the UserName property.
     *
     * @return
     *  String containing the UserName property.
     */
    public String getUserName(){
        return mUserName;
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
     * Sets the StoreId property.
     *
     * @param pStoreId
     *  int to use to update the property.
     */
    public void setStoreId(int pStoreId){
        this.mStoreId = pStoreId;
    }
    /**
     * Retrieves the StoreId property.
     *
     * @return
     *  int containing the StoreId property.
     */
    public int getStoreId(){
        return mStoreId;
    }


    
}
