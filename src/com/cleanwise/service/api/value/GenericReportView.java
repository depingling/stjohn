
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        GenericReportView
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
 * <code>GenericReportView</code> is a ViewObject class for UI.
 */
public class GenericReportView
extends ValueObject
{
   
    private static final long serialVersionUID = 7143291115242319066L;
    private int mGenericReportId;
    private String mReportCategory;
    private String mReportName;
    private String mLongDesc;
    private String mDBName;
    private String mReportClass;

    /**
     * Constructor.
     */
    public GenericReportView ()
    {
        mReportCategory = "";
        mReportName = "";
        mLongDesc = "";
        mDBName = "";
        mReportClass = "";
    }

    /**
     * Constructor. 
     */
    public GenericReportView(int parm1, String parm2, String parm3, String parm4, String parm5, String parm6)
    {
        mGenericReportId = parm1;
        mReportCategory = parm2;
        mReportName = parm3;
        mLongDesc = parm4;
        mDBName = parm5;
        mReportClass = parm6;
        
    }

    /**
     * Creates a new GenericReportView
     *
     * @return
     *  Newly initialized GenericReportView object.
     */
    public static GenericReportView createValue () 
    {
        GenericReportView valueView = new GenericReportView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this GenericReportView object
     */
    public String toString()
    {
        return "[" + "GenericReportId=" + mGenericReportId + ", ReportCategory=" + mReportCategory + ", ReportName=" + mReportName + ", LongDesc=" + mLongDesc + ", DBName=" + mDBName + ", ReportClass=" + mReportClass + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("GenericReport");
	root.setAttribute("Id", String.valueOf(mGenericReportId));

	Element node;

        node = doc.createElement("ReportCategory");
        node.appendChild(doc.createTextNode(String.valueOf(mReportCategory)));
        root.appendChild(node);

        node = doc.createElement("ReportName");
        node.appendChild(doc.createTextNode(String.valueOf(mReportName)));
        root.appendChild(node);

        node = doc.createElement("LongDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mLongDesc)));
        root.appendChild(node);

        node = doc.createElement("DBName");
        node.appendChild(doc.createTextNode(String.valueOf(mDBName)));
        root.appendChild(node);

        node = doc.createElement("ReportClass");
        node.appendChild(doc.createTextNode(String.valueOf(mReportClass)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public GenericReportView copy()  {
      GenericReportView obj = new GenericReportView();
      obj.setGenericReportId(mGenericReportId);
      obj.setReportCategory(mReportCategory);
      obj.setReportName(mReportName);
      obj.setLongDesc(mLongDesc);
      obj.setDBName(mDBName);
      obj.setReportClass(mReportClass);
      
      return obj;
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
     * Sets the LongDesc property.
     *
     * @param pLongDesc
     *  String to use to update the property.
     */
    public void setLongDesc(String pLongDesc){
        this.mLongDesc = pLongDesc;
    }
    /**
     * Retrieves the LongDesc property.
     *
     * @return
     *  String containing the LongDesc property.
     */
    public String getLongDesc(){
        return mLongDesc;
    }


    /**
     * Sets the DBName property.
     *
     * @param pDBName
     *  String to use to update the property.
     */
    public void setDBName(String pDBName){
        this.mDBName = pDBName;
    }
    /**
     * Retrieves the DBName property.
     *
     * @return
     *  String containing the DBName property.
     */
    public String getDBName(){
        return mDBName;
    }


    /**
     * Sets the ReportClass property.
     *
     * @param pReportClass
     *  String to use to update the property.
     */
    public void setReportClass(String pReportClass){
        this.mReportClass = pReportClass;
    }
    /**
     * Retrieves the ReportClass property.
     *
     * @return
     *  String containing the ReportClass property.
     */
    public String getReportClass(){
        return mReportClass;
    }


    
}
