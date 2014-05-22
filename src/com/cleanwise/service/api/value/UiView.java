
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        UiView
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
 * <code>UiView</code> is a ViewObject class for UI.
 */
public class UiView
extends ValueObject
{
   
    private static final long serialVersionUID = -1L;
    private UiPageViewVector mUiPages;
    private UiData mUiData;
    private UiAssocDataVector mUiAssociations;

    /**
     * Constructor.
     */
    public UiView ()
    {
    }

    /**
     * Constructor. 
     */
    public UiView(UiPageViewVector parm1, UiData parm2, UiAssocDataVector parm3)
    {
        mUiPages = parm1;
        mUiData = parm2;
        mUiAssociations = parm3;
        
    }

    /**
     * Creates a new UiView
     *
     * @return
     *  Newly initialized UiView object.
     */
    public static UiView createValue () 
    {
        UiView valueView = new UiView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this UiView object
     */
    public String toString()
    {
        return "[" + "UiPages=" + mUiPages + ", UiData=" + mUiData + ", UiAssociations=" + mUiAssociations + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("Ui");
	root.setAttribute("Id", String.valueOf(mUiPages));

	Element node;

        node = doc.createElement("UiData");
        node.appendChild(doc.createTextNode(String.valueOf(mUiData)));
        root.appendChild(node);

        node = doc.createElement("UiAssociations");
        node.appendChild(doc.createTextNode(String.valueOf(mUiAssociations)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public UiView copy()  {
      UiView obj = new UiView();
      obj.setUiPages(mUiPages);
      obj.setUiData(mUiData);
      obj.setUiAssociations(mUiAssociations);
      
      return obj;
    }

    
    /**
     * Sets the UiPages property.
     *
     * @param pUiPages
     *  UiPageViewVector to use to update the property.
     */
    public void setUiPages(UiPageViewVector pUiPages){
        this.mUiPages = pUiPages;
    }
    /**
     * Retrieves the UiPages property.
     *
     * @return
     *  UiPageViewVector containing the UiPages property.
     */
    public UiPageViewVector getUiPages(){
        return mUiPages;
    }


    /**
     * Sets the UiData property.
     *
     * @param pUiData
     *  UiData to use to update the property.
     */
    public void setUiData(UiData pUiData){
        this.mUiData = pUiData;
    }
    /**
     * Retrieves the UiData property.
     *
     * @return
     *  UiData containing the UiData property.
     */
    public UiData getUiData(){
        return mUiData;
    }


    /**
     * Sets the UiAssociations property.
     *
     * @param pUiAssociations
     *  UiAssocDataVector to use to update the property.
     */
    public void setUiAssociations(UiAssocDataVector pUiAssociations){
        this.mUiAssociations = pUiAssociations;
    }
    /**
     * Retrieves the UiAssociations property.
     *
     * @return
     *  UiAssocDataVector containing the UiAssociations property.
     */
    public UiAssocDataVector getUiAssociations(){
        return mUiAssociations;
    }


    
}
