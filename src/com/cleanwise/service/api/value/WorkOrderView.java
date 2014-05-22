
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        WorkOrderView
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
 * <code>WorkOrderView</code> is a ViewObject class for UI.
 */
public class WorkOrderView
extends ValueObject
{
   
    private static final long serialVersionUID = -1L;
    private int mWorkOrderId;
    private String mName;
    private String mType;
    private String mStatus;
    private String mPriority;
    private Date mStartDate;
    private Date mExptDate;

    /**
     * Constructor.
     */
    public WorkOrderView ()
    {
        mName = "";
        mType = "";
        mStatus = "";
        mPriority = "";
    }

    /**
     * Constructor. 
     */
    public WorkOrderView(int parm1, String parm2, String parm3, String parm4, String parm5, Date parm6, Date parm7)
    {
        mWorkOrderId = parm1;
        mName = parm2;
        mType = parm3;
        mStatus = parm4;
        mPriority = parm5;
        mStartDate = parm6;
        mExptDate = parm7;
        
    }

    /**
     * Creates a new WorkOrderView
     *
     * @return
     *  Newly initialized WorkOrderView object.
     */
    public static WorkOrderView createValue () 
    {
        WorkOrderView valueView = new WorkOrderView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this WorkOrderView object
     */
    public String toString()
    {
        return "[" + "WorkOrderId=" + mWorkOrderId + ", Name=" + mName + ", Type=" + mType + ", Status=" + mStatus + ", Priority=" + mPriority + ", StartDate=" + mStartDate + ", ExptDate=" + mExptDate + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("WorkOrder");
	root.setAttribute("Id", String.valueOf(mWorkOrderId));

	Element node;

        node = doc.createElement("Name");
        node.appendChild(doc.createTextNode(String.valueOf(mName)));
        root.appendChild(node);

        node = doc.createElement("Type");
        node.appendChild(doc.createTextNode(String.valueOf(mType)));
        root.appendChild(node);

        node = doc.createElement("Status");
        node.appendChild(doc.createTextNode(String.valueOf(mStatus)));
        root.appendChild(node);

        node = doc.createElement("Priority");
        node.appendChild(doc.createTextNode(String.valueOf(mPriority)));
        root.appendChild(node);

        node = doc.createElement("StartDate");
        node.appendChild(doc.createTextNode(String.valueOf(mStartDate)));
        root.appendChild(node);

        node = doc.createElement("ExptDate");
        node.appendChild(doc.createTextNode(String.valueOf(mExptDate)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public WorkOrderView copy()  {
      WorkOrderView obj = new WorkOrderView();
      obj.setWorkOrderId(mWorkOrderId);
      obj.setName(mName);
      obj.setType(mType);
      obj.setStatus(mStatus);
      obj.setPriority(mPriority);
      obj.setStartDate(mStartDate);
      obj.setExptDate(mExptDate);
      
      return obj;
    }

    
    /**
     * Sets the WorkOrderId property.
     *
     * @param pWorkOrderId
     *  int to use to update the property.
     */
    public void setWorkOrderId(int pWorkOrderId){
        this.mWorkOrderId = pWorkOrderId;
    }
    /**
     * Retrieves the WorkOrderId property.
     *
     * @return
     *  int containing the WorkOrderId property.
     */
    public int getWorkOrderId(){
        return mWorkOrderId;
    }


    /**
     * Sets the Name property.
     *
     * @param pName
     *  String to use to update the property.
     */
    public void setName(String pName){
        this.mName = pName;
    }
    /**
     * Retrieves the Name property.
     *
     * @return
     *  String containing the Name property.
     */
    public String getName(){
        return mName;
    }


    /**
     * Sets the Type property.
     *
     * @param pType
     *  String to use to update the property.
     */
    public void setType(String pType){
        this.mType = pType;
    }
    /**
     * Retrieves the Type property.
     *
     * @return
     *  String containing the Type property.
     */
    public String getType(){
        return mType;
    }


    /**
     * Sets the Status property.
     *
     * @param pStatus
     *  String to use to update the property.
     */
    public void setStatus(String pStatus){
        this.mStatus = pStatus;
    }
    /**
     * Retrieves the Status property.
     *
     * @return
     *  String containing the Status property.
     */
    public String getStatus(){
        return mStatus;
    }


    /**
     * Sets the Priority property.
     *
     * @param pPriority
     *  String to use to update the property.
     */
    public void setPriority(String pPriority){
        this.mPriority = pPriority;
    }
    /**
     * Retrieves the Priority property.
     *
     * @return
     *  String containing the Priority property.
     */
    public String getPriority(){
        return mPriority;
    }


    /**
     * Sets the StartDate property.
     *
     * @param pStartDate
     *  Date to use to update the property.
     */
    public void setStartDate(Date pStartDate){
        this.mStartDate = pStartDate;
    }
    /**
     * Retrieves the StartDate property.
     *
     * @return
     *  Date containing the StartDate property.
     */
    public Date getStartDate(){
        return mStartDate;
    }


    /**
     * Sets the ExptDate property.
     *
     * @param pExptDate
     *  Date to use to update the property.
     */
    public void setExptDate(Date pExptDate){
        this.mExptDate = pExptDate;
    }
    /**
     * Retrieves the ExptDate property.
     *
     * @return
     *  Date containing the ExptDate property.
     */
    public Date getExptDate(){
        return mExptDate;
    }


    
}
