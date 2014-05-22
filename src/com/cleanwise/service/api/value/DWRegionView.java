
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        DWRegionView
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
 * <code>DWRegionView</code> is a ViewObject class for UI.
 */
public class DWRegionView
extends ValueObject
{
   
    private static final long serialVersionUID = -1L;
    private int mStoreDimId;
    private int mRegionDimId;
    private String mRegionName;
    private String mRegionCode;

    /**
     * Constructor.
     */
    public DWRegionView ()
    {
        mRegionName = "";
        mRegionCode = "";
    }

    /**
     * Constructor. 
     */
    public DWRegionView(int parm1, int parm2, String parm3, String parm4)
    {
        mStoreDimId = parm1;
        mRegionDimId = parm2;
        mRegionName = parm3;
        mRegionCode = parm4;
        
    }

    /**
     * Creates a new DWRegionView
     *
     * @return
     *  Newly initialized DWRegionView object.
     */
    public static DWRegionView createValue () 
    {
        DWRegionView valueView = new DWRegionView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this DWRegionView object
     */
    public String toString()
    {
        return "[" + "StoreDimId=" + mStoreDimId + ", RegionDimId=" + mRegionDimId + ", RegionName=" + mRegionName + ", RegionCode=" + mRegionCode + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("DWRegion");
	root.setAttribute("Id", String.valueOf(mStoreDimId));

	Element node;

        node = doc.createElement("RegionDimId");
        node.appendChild(doc.createTextNode(String.valueOf(mRegionDimId)));
        root.appendChild(node);

        node = doc.createElement("RegionName");
        node.appendChild(doc.createTextNode(String.valueOf(mRegionName)));
        root.appendChild(node);

        node = doc.createElement("RegionCode");
        node.appendChild(doc.createTextNode(String.valueOf(mRegionCode)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public DWRegionView copy()  {
      DWRegionView obj = new DWRegionView();
      obj.setStoreDimId(mStoreDimId);
      obj.setRegionDimId(mRegionDimId);
      obj.setRegionName(mRegionName);
      obj.setRegionCode(mRegionCode);
      
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
     * Sets the RegionDimId property.
     *
     * @param pRegionDimId
     *  int to use to update the property.
     */
    public void setRegionDimId(int pRegionDimId){
        this.mRegionDimId = pRegionDimId;
    }
    /**
     * Retrieves the RegionDimId property.
     *
     * @return
     *  int containing the RegionDimId property.
     */
    public int getRegionDimId(){
        return mRegionDimId;
    }


    /**
     * Sets the RegionName property.
     *
     * @param pRegionName
     *  String to use to update the property.
     */
    public void setRegionName(String pRegionName){
        this.mRegionName = pRegionName;
    }
    /**
     * Retrieves the RegionName property.
     *
     * @return
     *  String containing the RegionName property.
     */
    public String getRegionName(){
        return mRegionName;
    }


    /**
     * Sets the RegionCode property.
     *
     * @param pRegionCode
     *  String to use to update the property.
     */
    public void setRegionCode(String pRegionCode){
        this.mRegionCode = pRegionCode;
    }
    /**
     * Retrieves the RegionCode property.
     *
     * @return
     *  String containing the RegionCode property.
     */
    public String getRegionCode(){
        return mRegionCode;
    }


    
}
