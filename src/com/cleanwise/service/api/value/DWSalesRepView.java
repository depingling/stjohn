
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        DWSalesRepView
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
 * <code>DWSalesRepView</code> is a ViewObject class for UI.
 */
public class DWSalesRepView
extends ValueObject
{
   
    private static final long serialVersionUID = -1L;
    private int mStoreDimId;
    private int mSalesRepDimId;
    private int mLwId;
    private String mRepName;
    private String mRepCode;

    /**
     * Constructor.
     */
    public DWSalesRepView ()
    {
        mRepName = "";
        mRepCode = "";
    }

    /**
     * Constructor. 
     */
    public DWSalesRepView(int parm1, int parm2, int parm3, String parm4, String parm5)
    {
        mStoreDimId = parm1;
        mSalesRepDimId = parm2;
        mLwId = parm3;
        mRepName = parm4;
        mRepCode = parm5;
        
    }

    /**
     * Creates a new DWSalesRepView
     *
     * @return
     *  Newly initialized DWSalesRepView object.
     */
    public static DWSalesRepView createValue () 
    {
        DWSalesRepView valueView = new DWSalesRepView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this DWSalesRepView object
     */
    public String toString()
    {
        return "[" + "StoreDimId=" + mStoreDimId + ", SalesRepDimId=" + mSalesRepDimId + ", LwId=" + mLwId + ", RepName=" + mRepName + ", RepCode=" + mRepCode + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("DWSalesRep");
	root.setAttribute("Id", String.valueOf(mStoreDimId));

	Element node;

        node = doc.createElement("SalesRepDimId");
        node.appendChild(doc.createTextNode(String.valueOf(mSalesRepDimId)));
        root.appendChild(node);

        node = doc.createElement("LwId");
        node.appendChild(doc.createTextNode(String.valueOf(mLwId)));
        root.appendChild(node);

        node = doc.createElement("RepName");
        node.appendChild(doc.createTextNode(String.valueOf(mRepName)));
        root.appendChild(node);

        node = doc.createElement("RepCode");
        node.appendChild(doc.createTextNode(String.valueOf(mRepCode)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public DWSalesRepView copy()  {
      DWSalesRepView obj = new DWSalesRepView();
      obj.setStoreDimId(mStoreDimId);
      obj.setSalesRepDimId(mSalesRepDimId);
      obj.setLwId(mLwId);
      obj.setRepName(mRepName);
      obj.setRepCode(mRepCode);
      
      return obj;
    }

    
    /**
     * Sets the StoreDimId property.
     *
     * @param pStoreDimId
     *  int to use to update the property.
     */
    public void setStoreDimId(int pStoreDimId){
        this.mStoreDimId = pStoreDimId;
    }
    /**
     * Retrieves the StoreDimId property.
     *
     * @return
     *  int containing the StoreDimId property.
     */
    public int getStoreDimId(){
        return mStoreDimId;
    }


    /**
     * Sets the SalesRepDimId property.
     *
     * @param pSalesRepDimId
     *  int to use to update the property.
     */
    public void setSalesRepDimId(int pSalesRepDimId){
        this.mSalesRepDimId = pSalesRepDimId;
    }
    /**
     * Retrieves the SalesRepDimId property.
     *
     * @return
     *  int containing the SalesRepDimId property.
     */
    public int getSalesRepDimId(){
        return mSalesRepDimId;
    }


    /**
     * Sets the LwId property.
     *
     * @param pLwId
     *  int to use to update the property.
     */
    public void setLwId(int pLwId){
        this.mLwId = pLwId;
    }
    /**
     * Retrieves the LwId property.
     *
     * @return
     *  int containing the LwId property.
     */
    public int getLwId(){
        return mLwId;
    }


    /**
     * Sets the RepName property.
     *
     * @param pRepName
     *  String to use to update the property.
     */
    public void setRepName(String pRepName){
        this.mRepName = pRepName;
    }
    /**
     * Retrieves the RepName property.
     *
     * @return
     *  String containing the RepName property.
     */
    public String getRepName(){
        return mRepName;
    }


    /**
     * Sets the RepCode property.
     *
     * @param pRepCode
     *  String to use to update the property.
     */
    public void setRepCode(String pRepCode){
        this.mRepCode = pRepCode;
    }
    /**
     * Retrieves the RepCode property.
     *
     * @return
     *  String containing the RepCode property.
     */
    public String getRepCode(){
        return mRepCode;
    }


    
}
