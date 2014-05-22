
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        DeliveryScheduleView
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
 * <code>DeliveryScheduleView</code> is a ViewObject class for UI.
 */
public class DeliveryScheduleView
extends ValueObject
{
   
    private static final long serialVersionUID = 1334948797629506202L;
    private int mBusEntityId;
    private String mBusEntityShortDesc;
    private String mBusEntityErpNum;
    private int mScheduleId;
    private String mScheduleName;
    private String mScheduleStatus;
    private String mScheduleInfo;
    private String mCutoffInfo;
    private String mExceptions;
    private java.util.Date mNextDelivery;
    private java.util.Date mLastProcessedDt;

    /**
     * Constructor.
     */
    public DeliveryScheduleView ()
    {
        mBusEntityShortDesc = "";
        mBusEntityErpNum = "";
        mScheduleName = "";
        mScheduleStatus = "";
        mScheduleInfo = "";
        mCutoffInfo = "";
        mExceptions = "";
    }

    /**
     * Constructor. 
     */
    public DeliveryScheduleView(int parm1, String parm2, String parm3, int parm4, String parm5, String parm6, String parm7, String parm8, String parm9, java.util.Date parm10, java.util.Date parm11)
    {
        mBusEntityId = parm1;
        mBusEntityShortDesc = parm2;
        mBusEntityErpNum = parm3;
        mScheduleId = parm4;
        mScheduleName = parm5;
        mScheduleStatus = parm6;
        mScheduleInfo = parm7;
        mCutoffInfo = parm8;
        mExceptions = parm9;
        mNextDelivery = parm10;
        mLastProcessedDt = parm11;
        
    }

    /**
     * Creates a new DeliveryScheduleView
     *
     * @return
     *  Newly initialized DeliveryScheduleView object.
     */
    public static DeliveryScheduleView createValue () 
    {
        DeliveryScheduleView valueView = new DeliveryScheduleView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this DeliveryScheduleView object
     */
    public String toString()
    {
        return "[" + "BusEntityId=" + mBusEntityId + ", BusEntityShortDesc=" + mBusEntityShortDesc + ", BusEntityErpNum=" + mBusEntityErpNum + ", ScheduleId=" + mScheduleId + ", ScheduleName=" + mScheduleName + ", ScheduleStatus=" + mScheduleStatus + ", ScheduleInfo=" + mScheduleInfo + ", CutoffInfo=" + mCutoffInfo + ", Exceptions=" + mExceptions + ", NextDelivery=" + mNextDelivery + ", LastProcessedDt=" + mLastProcessedDt + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("DeliverySchedule");
	root.setAttribute("Id", String.valueOf(mBusEntityId));

	Element node;

        node = doc.createElement("BusEntityShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mBusEntityShortDesc)));
        root.appendChild(node);

        node = doc.createElement("BusEntityErpNum");
        node.appendChild(doc.createTextNode(String.valueOf(mBusEntityErpNum)));
        root.appendChild(node);

        node = doc.createElement("ScheduleId");
        node.appendChild(doc.createTextNode(String.valueOf(mScheduleId)));
        root.appendChild(node);

        node = doc.createElement("ScheduleName");
        node.appendChild(doc.createTextNode(String.valueOf(mScheduleName)));
        root.appendChild(node);

        node = doc.createElement("ScheduleStatus");
        node.appendChild(doc.createTextNode(String.valueOf(mScheduleStatus)));
        root.appendChild(node);

        node = doc.createElement("ScheduleInfo");
        node.appendChild(doc.createTextNode(String.valueOf(mScheduleInfo)));
        root.appendChild(node);

        node = doc.createElement("CutoffInfo");
        node.appendChild(doc.createTextNode(String.valueOf(mCutoffInfo)));
        root.appendChild(node);

        node = doc.createElement("Exceptions");
        node.appendChild(doc.createTextNode(String.valueOf(mExceptions)));
        root.appendChild(node);

        node = doc.createElement("NextDelivery");
        node.appendChild(doc.createTextNode(String.valueOf(mNextDelivery)));
        root.appendChild(node);

        node = doc.createElement("LastProcessedDt");
        node.appendChild(doc.createTextNode(String.valueOf(mLastProcessedDt)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public DeliveryScheduleView copy()  {
      DeliveryScheduleView obj = new DeliveryScheduleView();
      obj.setBusEntityId(mBusEntityId);
      obj.setBusEntityShortDesc(mBusEntityShortDesc);
      obj.setBusEntityErpNum(mBusEntityErpNum);
      obj.setScheduleId(mScheduleId);
      obj.setScheduleName(mScheduleName);
      obj.setScheduleStatus(mScheduleStatus);
      obj.setScheduleInfo(mScheduleInfo);
      obj.setCutoffInfo(mCutoffInfo);
      obj.setExceptions(mExceptions);
      obj.setNextDelivery(mNextDelivery);
      obj.setLastProcessedDt(mLastProcessedDt);
      
      return obj;
    }

    
    /**
     * Sets the BusEntityId property.
     *
     * @param pBusEntityId
     *  int to use to update the property.
     */
    public void setBusEntityId(int pBusEntityId){
        this.mBusEntityId = pBusEntityId;
    }
    /**
     * Retrieves the BusEntityId property.
     *
     * @return
     *  int containing the BusEntityId property.
     */
    public int getBusEntityId(){
        return mBusEntityId;
    }


    /**
     * Sets the BusEntityShortDesc property.
     *
     * @param pBusEntityShortDesc
     *  String to use to update the property.
     */
    public void setBusEntityShortDesc(String pBusEntityShortDesc){
        this.mBusEntityShortDesc = pBusEntityShortDesc;
    }
    /**
     * Retrieves the BusEntityShortDesc property.
     *
     * @return
     *  String containing the BusEntityShortDesc property.
     */
    public String getBusEntityShortDesc(){
        return mBusEntityShortDesc;
    }


    /**
     * Sets the BusEntityErpNum property.
     *
     * @param pBusEntityErpNum
     *  String to use to update the property.
     */
    public void setBusEntityErpNum(String pBusEntityErpNum){
        this.mBusEntityErpNum = pBusEntityErpNum;
    }
    /**
     * Retrieves the BusEntityErpNum property.
     *
     * @return
     *  String containing the BusEntityErpNum property.
     */
    public String getBusEntityErpNum(){
        return mBusEntityErpNum;
    }


    /**
     * Sets the ScheduleId property.
     *
     * @param pScheduleId
     *  int to use to update the property.
     */
    public void setScheduleId(int pScheduleId){
        this.mScheduleId = pScheduleId;
    }
    /**
     * Retrieves the ScheduleId property.
     *
     * @return
     *  int containing the ScheduleId property.
     */
    public int getScheduleId(){
        return mScheduleId;
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


    /**
     * Sets the ScheduleStatus property.
     *
     * @param pScheduleStatus
     *  String to use to update the property.
     */
    public void setScheduleStatus(String pScheduleStatus){
        this.mScheduleStatus = pScheduleStatus;
    }
    /**
     * Retrieves the ScheduleStatus property.
     *
     * @return
     *  String containing the ScheduleStatus property.
     */
    public String getScheduleStatus(){
        return mScheduleStatus;
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
     * Sets the CutoffInfo property.
     *
     * @param pCutoffInfo
     *  String to use to update the property.
     */
    public void setCutoffInfo(String pCutoffInfo){
        this.mCutoffInfo = pCutoffInfo;
    }
    /**
     * Retrieves the CutoffInfo property.
     *
     * @return
     *  String containing the CutoffInfo property.
     */
    public String getCutoffInfo(){
        return mCutoffInfo;
    }


    /**
     * Sets the Exceptions property.
     *
     * @param pExceptions
     *  String to use to update the property.
     */
    public void setExceptions(String pExceptions){
        this.mExceptions = pExceptions;
    }
    /**
     * Retrieves the Exceptions property.
     *
     * @return
     *  String containing the Exceptions property.
     */
    public String getExceptions(){
        return mExceptions;
    }


    /**
     * Sets the NextDelivery property.
     *
     * @param pNextDelivery
     *  java.util.Date to use to update the property.
     */
    public void setNextDelivery(java.util.Date pNextDelivery){
        this.mNextDelivery = pNextDelivery;
    }
    /**
     * Retrieves the NextDelivery property.
     *
     * @return
     *  java.util.Date containing the NextDelivery property.
     */
    public java.util.Date getNextDelivery(){
        return mNextDelivery;
    }


    /**
     * Sets the LastProcessedDt property.
     *
     * @param pLastProcessedDt
     *  java.util.Date to use to update the property.
     */
    public void setLastProcessedDt(java.util.Date pLastProcessedDt){
        this.mLastProcessedDt = pLastProcessedDt;
    }
    /**
     * Retrieves the LastProcessedDt property.
     *
     * @return
     *  java.util.Date containing the LastProcessedDt property.
     */
    public java.util.Date getLastProcessedDt(){
        return mLastProcessedDt;
    }


    
}
