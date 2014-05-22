
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        URLResponseDataView
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
 * <code>URLResponseDataView</code> is a ViewObject class for UI.
 */
public class URLResponseDataView
extends ValueObject
{
   
    private static final long serialVersionUID = -1L;
    private String mURL;
    private byte[] mData;

    /**
     * Constructor.
     */
    public URLResponseDataView ()
    {
        mURL = "";
    }

    /**
     * Constructor. 
     */
    public URLResponseDataView(String parm1, byte[] parm2)
    {
        mURL = parm1;
        mData = parm2;
        
    }

    /**
     * Creates a new URLResponseDataView
     *
     * @return
     *  Newly initialized URLResponseDataView object.
     */
    public static URLResponseDataView createValue () 
    {
        URLResponseDataView valueView = new URLResponseDataView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this URLResponseDataView object
     */
    public String toString()
    {
        return "[" + "URL=" + mURL + ", Data=" + mData + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("URLResponseData");
	root.setAttribute("Id", String.valueOf(mURL));

	Element node;

        node = doc.createElement("Data");
        node.appendChild(doc.createTextNode(String.valueOf(mData)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public URLResponseDataView copy()  {
      URLResponseDataView obj = new URLResponseDataView();
      obj.setURL(mURL);
      obj.setData(mData);
      
      return obj;
    }

    
    /**
     * Sets the URL property.
     *
     * @param pURL
     *  String to use to update the property.
     */
    public void setURL(String pURL){
        this.mURL = pURL;
    }
    /**
     * Retrieves the URL property.
     *
     * @return
     *  String containing the URL property.
     */
    public String getURL(){
        return mURL;
    }


    /**
     * Sets the Data property.
     *
     * @param pData
     *  byte[] to use to update the property.
     */
    public void setData(byte[] pData){
        this.mData = pData;
    }
    /**
     * Retrieves the Data property.
     *
     * @return
     *  byte[] containing the Data property.
     */
    public byte[] getData(){
        return mData;
    }


    
}
