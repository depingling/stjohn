
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        GenericReportCellView
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
 * <code>GenericReportCellView</code> is a ViewObject class for UI.
 */
public class GenericReportCellView
extends ValueObject
{
   
    private static final long serialVersionUID = -7701654083609843045L;
    private Object mDataValue;
    private String mStyleName;

    /**
     * Constructor.
     */
    public GenericReportCellView ()
    {
        mStyleName = "";
    }

    /**
     * Constructor. 
     */
    public GenericReportCellView(Object parm1, String parm2)
    {
        mDataValue = parm1;
        mStyleName = parm2;
        
    }

    /**
     * Creates a new GenericReportCellView
     *
     * @return
     *  Newly initialized GenericReportCellView object.
     */
    public static GenericReportCellView createValue () 
    {
        GenericReportCellView valueView = new GenericReportCellView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this GenericReportCellView object
     */
    public String toString()
    {
        return "[" + "DataValue=" + mDataValue + ", StyleName=" + mStyleName + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("GenericReportCell");
	root.setAttribute("Id", String.valueOf(mDataValue));

	Element node;

        node = doc.createElement("StyleName");
        node.appendChild(doc.createTextNode(String.valueOf(mStyleName)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public GenericReportCellView copy()  {
      GenericReportCellView obj = new GenericReportCellView();
      obj.setDataValue(mDataValue);
      obj.setStyleName(mStyleName);
      
      return obj;
    }

    
    /**
     * Sets the DataValue property.
     *
     * @param pDataValue
     *  Object to use to update the property.
     */
    public void setDataValue(Object pDataValue){
        this.mDataValue = pDataValue;
    }
    /**
     * Retrieves the DataValue property.
     *
     * @return
     *  Object containing the DataValue property.
     */
    public Object getDataValue(){
        return mDataValue;
    }


    /**
     * Sets the StyleName property.
     *
     * @param pStyleName
     *  String to use to update the property.
     */
    public void setStyleName(String pStyleName){
        this.mStyleName = pStyleName;
    }
    /**
     * Retrieves the StyleName property.
     *
     * @return
     *  String containing the StyleName property.
     */
    public String getStyleName(){
        return mStyleName;
    }


    
}
