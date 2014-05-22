
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        InventoryLevelView
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
 * <code>InventoryLevelView</code> is a ViewObject class for UI.
 */
public class InventoryLevelView
extends ValueObject
{
   
    private static final long serialVersionUID = -1L;
    private InventoryLevelData mInventoryLevelData;
    private InventoryLevelDetailDataVector mParValues;

    /**
     * Constructor.
     */
    public InventoryLevelView ()
    {
    }

    /**
     * Constructor. 
     */
    public InventoryLevelView(InventoryLevelData parm1, InventoryLevelDetailDataVector parm2)
    {
        mInventoryLevelData = parm1;
        mParValues = parm2;
        
    }

    /**
     * Creates a new InventoryLevelView
     *
     * @return
     *  Newly initialized InventoryLevelView object.
     */
    public static InventoryLevelView createValue () 
    {
        InventoryLevelView valueView = new InventoryLevelView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this InventoryLevelView object
     */
    public String toString()
    {
        return "[" + "InventoryLevelData=" + mInventoryLevelData + ", ParValues=" + mParValues + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("InventoryLevel");
	root.setAttribute("Id", String.valueOf(mInventoryLevelData));

	Element node;

        node = doc.createElement("ParValues");
        node.appendChild(doc.createTextNode(String.valueOf(mParValues)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public InventoryLevelView copy()  {
      InventoryLevelView obj = new InventoryLevelView();
      obj.setInventoryLevelData(mInventoryLevelData);
      obj.setParValues(mParValues);
      
      return obj;
    }

    
    /**
     * Sets the InventoryLevelData property.
     *
     * @param pInventoryLevelData
     *  InventoryLevelData to use to update the property.
     */
    public void setInventoryLevelData(InventoryLevelData pInventoryLevelData){
        this.mInventoryLevelData = pInventoryLevelData;
    }
    /**
     * Retrieves the InventoryLevelData property.
     *
     * @return
     *  InventoryLevelData containing the InventoryLevelData property.
     */
    public InventoryLevelData getInventoryLevelData(){
        return mInventoryLevelData;
    }


    /**
     * Sets the ParValues property.
     *
     * @param pParValues
     *  InventoryLevelDetailDataVector to use to update the property.
     */
    public void setParValues(InventoryLevelDetailDataVector pParValues){
        this.mParValues = pParValues;
    }
    /**
     * Retrieves the ParValues property.
     *
     * @return
     *  InventoryLevelDetailDataVector containing the ParValues property.
     */
    public InventoryLevelDetailDataVector getParValues(){
        return mParValues;
    }


    
}
