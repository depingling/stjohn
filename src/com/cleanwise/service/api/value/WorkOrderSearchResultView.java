
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        WorkOrderSearchResultView
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
 * <code>WorkOrderSearchResultView</code> is a ViewObject class for UI.
 */
public class WorkOrderSearchResultView
extends ValueObject
{
   
    private static final long serialVersionUID = 3842273823255019289L;
    private String mSiteName;
    private String mDistributorShipToNumber;
    private int mWorkOrderId;
    private String mWorkOrderNum;
    private String mPoNumber;
    private String mShortDesc;
    private String mTypeCd;
    private String mPriority;
    private String mStatusCd;
    private java.util.Date mActualStartDate;
    private java.util.Date mActualFinishDate;

    /**
     * Constructor.
     */
    public WorkOrderSearchResultView ()
    {
        mSiteName = "";
        mDistributorShipToNumber = "";
        mWorkOrderNum = "";
        mPoNumber = "";
        mShortDesc = "";
        mTypeCd = "";
        mPriority = "";
        mStatusCd = "";
    }

    /**
     * Constructor. 
     */
    public WorkOrderSearchResultView(String parm1, String parm2, int parm3, String parm4, String parm5, String parm6, String parm7, String parm8, String parm9, java.util.Date parm10, java.util.Date parm11)
    {
        mSiteName = parm1;
        mDistributorShipToNumber = parm2;
        mWorkOrderId = parm3;
        mWorkOrderNum = parm4;
        mPoNumber = parm5;
        mShortDesc = parm6;
        mTypeCd = parm7;
        mPriority = parm8;
        mStatusCd = parm9;
        mActualStartDate = parm10;
        mActualFinishDate = parm11;
        
    }

    /**
     * Creates a new WorkOrderSearchResultView
     *
     * @return
     *  Newly initialized WorkOrderSearchResultView object.
     */
    public static WorkOrderSearchResultView createValue () 
    {
        WorkOrderSearchResultView valueView = new WorkOrderSearchResultView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this WorkOrderSearchResultView object
     */
    public String toString()
    {
        return "[" + "SiteName=" + mSiteName + ", DistributorShipToNumber=" + mDistributorShipToNumber + ", WorkOrderId=" + mWorkOrderId + ", WorkOrderNum=" + mWorkOrderNum + ", PoNumber=" + mPoNumber + ", ShortDesc=" + mShortDesc + ", TypeCd=" + mTypeCd + ", Priority=" + mPriority + ", StatusCd=" + mStatusCd + ", ActualStartDate=" + mActualStartDate + ", ActualFinishDate=" + mActualFinishDate + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("WorkOrderSearchResult");
	root.setAttribute("Id", String.valueOf(mSiteName));

	Element node;

        node = doc.createElement("DistributorShipToNumber");
        node.appendChild(doc.createTextNode(String.valueOf(mDistributorShipToNumber)));
        root.appendChild(node);

        node = doc.createElement("WorkOrderId");
        node.appendChild(doc.createTextNode(String.valueOf(mWorkOrderId)));
        root.appendChild(node);

        node = doc.createElement("WorkOrderNum");
        node.appendChild(doc.createTextNode(String.valueOf(mWorkOrderNum)));
        root.appendChild(node);

        node = doc.createElement("PoNumber");
        node.appendChild(doc.createTextNode(String.valueOf(mPoNumber)));
        root.appendChild(node);

        node = doc.createElement("ShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mShortDesc)));
        root.appendChild(node);

        node = doc.createElement("TypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mTypeCd)));
        root.appendChild(node);

        node = doc.createElement("Priority");
        node.appendChild(doc.createTextNode(String.valueOf(mPriority)));
        root.appendChild(node);

        node = doc.createElement("StatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mStatusCd)));
        root.appendChild(node);

        node = doc.createElement("ActualStartDate");
        node.appendChild(doc.createTextNode(String.valueOf(mActualStartDate)));
        root.appendChild(node);

        node = doc.createElement("ActualFinishDate");
        node.appendChild(doc.createTextNode(String.valueOf(mActualFinishDate)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public WorkOrderSearchResultView copy()  {
      WorkOrderSearchResultView obj = new WorkOrderSearchResultView();
      obj.setSiteName(mSiteName);
      obj.setDistributorShipToNumber(mDistributorShipToNumber);
      obj.setWorkOrderId(mWorkOrderId);
      obj.setWorkOrderNum(mWorkOrderNum);
      obj.setPoNumber(mPoNumber);
      obj.setShortDesc(mShortDesc);
      obj.setTypeCd(mTypeCd);
      obj.setPriority(mPriority);
      obj.setStatusCd(mStatusCd);
      obj.setActualStartDate(mActualStartDate);
      obj.setActualFinishDate(mActualFinishDate);
      
      return obj;
    }

    
    /**
     * Sets the SiteName property.
     *
     * @param pSiteName
     *  String to use to update the property.
     */
    public void setSiteName(String pSiteName){
        this.mSiteName = pSiteName;
    }
    /**
     * Retrieves the SiteName property.
     *
     * @return
     *  String containing the SiteName property.
     */
    public String getSiteName(){
        return mSiteName;
    }


    /**
     * Sets the DistributorShipToNumber property.
     *
     * @param pDistributorShipToNumber
     *  String to use to update the property.
     */
    public void setDistributorShipToNumber(String pDistributorShipToNumber){
        this.mDistributorShipToNumber = pDistributorShipToNumber;
    }
    /**
     * Retrieves the DistributorShipToNumber property.
     *
     * @return
     *  String containing the DistributorShipToNumber property.
     */
    public String getDistributorShipToNumber(){
        return mDistributorShipToNumber;
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
     * Sets the WorkOrderNum property.
     *
     * @param pWorkOrderNum
     *  String to use to update the property.
     */
    public void setWorkOrderNum(String pWorkOrderNum){
        this.mWorkOrderNum = pWorkOrderNum;
    }
    /**
     * Retrieves the WorkOrderNum property.
     *
     * @return
     *  String containing the WorkOrderNum property.
     */
    public String getWorkOrderNum(){
        return mWorkOrderNum;
    }


    /**
     * Sets the PoNumber property.
     *
     * @param pPoNumber
     *  String to use to update the property.
     */
    public void setPoNumber(String pPoNumber){
        this.mPoNumber = pPoNumber;
    }
    /**
     * Retrieves the PoNumber property.
     *
     * @return
     *  String containing the PoNumber property.
     */
    public String getPoNumber(){
        return mPoNumber;
    }


    /**
     * Sets the ShortDesc property.
     *
     * @param pShortDesc
     *  String to use to update the property.
     */
    public void setShortDesc(String pShortDesc){
        this.mShortDesc = pShortDesc;
    }
    /**
     * Retrieves the ShortDesc property.
     *
     * @return
     *  String containing the ShortDesc property.
     */
    public String getShortDesc(){
        return mShortDesc;
    }


    /**
     * Sets the TypeCd property.
     *
     * @param pTypeCd
     *  String to use to update the property.
     */
    public void setTypeCd(String pTypeCd){
        this.mTypeCd = pTypeCd;
    }
    /**
     * Retrieves the TypeCd property.
     *
     * @return
     *  String containing the TypeCd property.
     */
    public String getTypeCd(){
        return mTypeCd;
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
     * Sets the StatusCd property.
     *
     * @param pStatusCd
     *  String to use to update the property.
     */
    public void setStatusCd(String pStatusCd){
        this.mStatusCd = pStatusCd;
    }
    /**
     * Retrieves the StatusCd property.
     *
     * @return
     *  String containing the StatusCd property.
     */
    public String getStatusCd(){
        return mStatusCd;
    }


    /**
     * Sets the ActualStartDate property.
     *
     * @param pActualStartDate
     *  java.util.Date to use to update the property.
     */
    public void setActualStartDate(java.util.Date pActualStartDate){
        this.mActualStartDate = pActualStartDate;
    }
    /**
     * Retrieves the ActualStartDate property.
     *
     * @return
     *  java.util.Date containing the ActualStartDate property.
     */
    public java.util.Date getActualStartDate(){
        return mActualStartDate;
    }


    /**
     * Sets the ActualFinishDate property.
     *
     * @param pActualFinishDate
     *  java.util.Date to use to update the property.
     */
    public void setActualFinishDate(java.util.Date pActualFinishDate){
        this.mActualFinishDate = pActualFinishDate;
    }
    /**
     * Retrieves the ActualFinishDate property.
     *
     * @return
     *  java.util.Date containing the ActualFinishDate property.
     */
    public java.util.Date getActualFinishDate(){
        return mActualFinishDate;
    }


    
}
