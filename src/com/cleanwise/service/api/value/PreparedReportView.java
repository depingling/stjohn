
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        PreparedReportView
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
 * <code>PreparedReportView</code> is a ViewObject class for UI.
 */
public class PreparedReportView
extends ValueObject
{
   
    private static final long serialVersionUID = -5607781451384432062L;
    private int mReportResultId;
    private int mGenericReportId;
    private String mReportCategory;
    private String mReportName;
    private Date mReportDate;
    private String mReportParameters;
    private boolean mReadFl;
    private int mRequesterId;
    private String mRequesterName;
    private String mReportResultStatusCd;
    private String mProtectedFl;

    /**
     * Constructor.
     */
    public PreparedReportView ()
    {
        mReportCategory = "";
        mReportName = "";
        mReportParameters = "";
        mRequesterName = "";
        mReportResultStatusCd = "";
        mProtectedFl = "";
    }

    /**
     * Constructor. 
     */
    public PreparedReportView(int parm1, int parm2, String parm3, String parm4, Date parm5, String parm6, boolean parm7, int parm8, String parm9, String parm10, String parm11)
    {
        mReportResultId = parm1;
        mGenericReportId = parm2;
        mReportCategory = parm3;
        mReportName = parm4;
        mReportDate = parm5;
        mReportParameters = parm6;
        mReadFl = parm7;
        mRequesterId = parm8;
        mRequesterName = parm9;
        mReportResultStatusCd = parm10;
        mProtectedFl = parm11;
        
    }

    /**
     * Creates a new PreparedReportView
     *
     * @return
     *  Newly initialized PreparedReportView object.
     */
    public static PreparedReportView createValue () 
    {
        PreparedReportView valueView = new PreparedReportView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this PreparedReportView object
     */
    public String toString()
    {
        return "[" + "ReportResultId=" + mReportResultId + ", GenericReportId=" + mGenericReportId + ", ReportCategory=" + mReportCategory + ", ReportName=" + mReportName + ", ReportDate=" + mReportDate + ", ReportParameters=" + mReportParameters + ", ReadFl=" + mReadFl + ", RequesterId=" + mRequesterId + ", RequesterName=" + mRequesterName + ", ReportResultStatusCd=" + mReportResultStatusCd + ", ProtectedFl=" + mProtectedFl + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("PreparedReport");
	root.setAttribute("Id", String.valueOf(mReportResultId));

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

        node = doc.createElement("ReportDate");
        node.appendChild(doc.createTextNode(String.valueOf(mReportDate)));
        root.appendChild(node);

        node = doc.createElement("ReportParameters");
        node.appendChild(doc.createTextNode(String.valueOf(mReportParameters)));
        root.appendChild(node);

        node = doc.createElement("ReadFl");
        node.appendChild(doc.createTextNode(String.valueOf(mReadFl)));
        root.appendChild(node);

        node = doc.createElement("RequesterId");
        node.appendChild(doc.createTextNode(String.valueOf(mRequesterId)));
        root.appendChild(node);

        node = doc.createElement("RequesterName");
        node.appendChild(doc.createTextNode(String.valueOf(mRequesterName)));
        root.appendChild(node);

        node = doc.createElement("ReportResultStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mReportResultStatusCd)));
        root.appendChild(node);

        node = doc.createElement("ProtectedFl");
        node.appendChild(doc.createTextNode(String.valueOf(mProtectedFl)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public PreparedReportView copy()  {
      PreparedReportView obj = new PreparedReportView();
      obj.setReportResultId(mReportResultId);
      obj.setGenericReportId(mGenericReportId);
      obj.setReportCategory(mReportCategory);
      obj.setReportName(mReportName);
      obj.setReportDate(mReportDate);
      obj.setReportParameters(mReportParameters);
      obj.setReadFl(mReadFl);
      obj.setRequesterId(mRequesterId);
      obj.setRequesterName(mRequesterName);
      obj.setReportResultStatusCd(mReportResultStatusCd);
      obj.setProtectedFl(mProtectedFl);
      
      return obj;
    }

    
    /**
     * Sets the ReportResultId property.
     *
     * @param pReportResultId
     *  int to use to update the property.
     */
    public void setReportResultId(int pReportResultId){
        this.mReportResultId = pReportResultId;
    }
    /**
     * Retrieves the ReportResultId property.
     *
     * @return
     *  int containing the ReportResultId property.
     */
    public int getReportResultId(){
        return mReportResultId;
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
     * Sets the ReportDate property.
     *
     * @param pReportDate
     *  Date to use to update the property.
     */
    public void setReportDate(Date pReportDate){
        this.mReportDate = pReportDate;
    }
    /**
     * Retrieves the ReportDate property.
     *
     * @return
     *  Date containing the ReportDate property.
     */
    public Date getReportDate(){
        return mReportDate;
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
     * Sets the ReadFl property.
     *
     * @param pReadFl
     *  boolean to use to update the property.
     */
    public void setReadFl(boolean pReadFl){
        this.mReadFl = pReadFl;
    }
    /**
     * Retrieves the ReadFl property.
     *
     * @return
     *  boolean containing the ReadFl property.
     */
    public boolean getReadFl(){
        return mReadFl;
    }


    /**
     * Sets the RequesterId property.
     *
     * @param pRequesterId
     *  int to use to update the property.
     */
    public void setRequesterId(int pRequesterId){
        this.mRequesterId = pRequesterId;
    }
    /**
     * Retrieves the RequesterId property.
     *
     * @return
     *  int containing the RequesterId property.
     */
    public int getRequesterId(){
        return mRequesterId;
    }


    /**
     * Sets the RequesterName property.
     *
     * @param pRequesterName
     *  String to use to update the property.
     */
    public void setRequesterName(String pRequesterName){
        this.mRequesterName = pRequesterName;
    }
    /**
     * Retrieves the RequesterName property.
     *
     * @return
     *  String containing the RequesterName property.
     */
    public String getRequesterName(){
        return mRequesterName;
    }


    /**
     * Sets the ReportResultStatusCd property.
     *
     * @param pReportResultStatusCd
     *  String to use to update the property.
     */
    public void setReportResultStatusCd(String pReportResultStatusCd){
        this.mReportResultStatusCd = pReportResultStatusCd;
    }
    /**
     * Retrieves the ReportResultStatusCd property.
     *
     * @return
     *  String containing the ReportResultStatusCd property.
     */
    public String getReportResultStatusCd(){
        return mReportResultStatusCd;
    }


    /**
     * Sets the ProtectedFl property.
     *
     * @param pProtectedFl
     *  String to use to update the property.
     */
    public void setProtectedFl(String pProtectedFl){
        this.mProtectedFl = pProtectedFl;
    }
    /**
     * Retrieves the ProtectedFl property.
     *
     * @return
     *  String containing the ProtectedFl property.
     */
    public String getProtectedFl(){
        return mProtectedFl;
    }


    
}
