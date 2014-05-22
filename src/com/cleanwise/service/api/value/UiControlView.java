
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        UiControlView
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
 * <code>UiControlView</code> is a ViewObject class for UI.
 */
public class UiControlView
extends ValueObject
{
   
    private static final long serialVersionUID = -1L;
    private UiControlData mUiControlData;
    private UiControlElementDataVector mUiControlElements;

    /**
     * Constructor.
     */
    public UiControlView ()
    {
    }

    /**
     * Constructor. 
     */
    public UiControlView(UiControlData parm1, UiControlElementDataVector parm2)
    {
        mUiControlData = parm1;
        mUiControlElements = parm2;
        
    }

    /**
     * Creates a new UiControlView
     *
     * @return
     *  Newly initialized UiControlView object.
     */
    public static UiControlView createValue () 
    {
        UiControlView valueView = new UiControlView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this UiControlView object
     */
    public String toString()
    {
        return "[" + "UiControlData=" + mUiControlData + ", UiControlElements=" + mUiControlElements + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("UiControl");
	root.setAttribute("Id", String.valueOf(mUiControlData));

	Element node;

        node = doc.createElement("UiControlElements");
        node.appendChild(doc.createTextNode(String.valueOf(mUiControlElements)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public UiControlView copy()  {
      UiControlView obj = new UiControlView();
      obj.setUiControlData(mUiControlData);
      obj.setUiControlElements(mUiControlElements);
      
      return obj;
    }

    
    /**
     * Sets the UiControlData property.
     *
     * @param pUiControlData
     *  UiControlData to use to update the property.
     */
    public void setUiControlData(UiControlData pUiControlData){
        this.mUiControlData = pUiControlData;
    }
    /**
     * Retrieves the UiControlData property.
     *
     * @return
     *  UiControlData containing the UiControlData property.
     */
    public UiControlData getUiControlData(){
        return mUiControlData;
    }


    /**
     * Sets the UiControlElements property.
     *
     * @param pUiControlElements
     *  UiControlElementDataVector to use to update the property.
     */
    public void setUiControlElements(UiControlElementDataVector pUiControlElements){
        this.mUiControlElements = pUiControlElements;
    }
    /**
     * Retrieves the UiControlElements property.
     *
     * @return
     *  UiControlElementDataVector containing the UiControlElements property.
     */
    public UiControlElementDataVector getUiControlElements(){
        return mUiControlElements;
    }


    
}
