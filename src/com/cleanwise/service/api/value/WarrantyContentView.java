
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        WarrantyContentView
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
 * <code>WarrantyContentView</code> is a ViewObject class for UI.
 */
public class WarrantyContentView
extends ValueObject
{
   
    private static final long serialVersionUID = -7290261675886064346L;
    private ContentView mContent;
    private WarrantyContentData mWarrantyContentData;

    /**
     * Constructor.
     */
    public WarrantyContentView ()
    {
    }

    /**
     * Constructor. 
     */
    public WarrantyContentView(ContentView parm1, WarrantyContentData parm2)
    {
        mContent = parm1;
        mWarrantyContentData = parm2;
        
    }

    /**
     * Creates a new WarrantyContentView
     *
     * @return
     *  Newly initialized WarrantyContentView object.
     */
    public static WarrantyContentView createValue () 
    {
        WarrantyContentView valueView = new WarrantyContentView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this WarrantyContentView object
     */
    public String toString()
    {
        return "[" + "Content=" + mContent + ", WarrantyContentData=" + mWarrantyContentData + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("WarrantyContent");
	root.setAttribute("Id", String.valueOf(mContent));

	Element node;

        node = doc.createElement("WarrantyContentData");
        node.appendChild(doc.createTextNode(String.valueOf(mWarrantyContentData)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public WarrantyContentView copy()  {
      WarrantyContentView obj = new WarrantyContentView();
      obj.setContent(mContent);
      obj.setWarrantyContentData(mWarrantyContentData);
      
      return obj;
    }

    
    /**
     * Sets the Content property.
     *
     * @param pContent
     *  ContentView to use to update the property.
     */
    public void setContent(ContentView pContent){
        this.mContent = pContent;
    }
    /**
     * Retrieves the Content property.
     *
     * @return
     *  ContentView containing the Content property.
     */
    public ContentView getContent(){
        return mContent;
    }


    /**
     * Sets the WarrantyContentData property.
     *
     * @param pWarrantyContentData
     *  WarrantyContentData to use to update the property.
     */
    public void setWarrantyContentData(WarrantyContentData pWarrantyContentData){
        this.mWarrantyContentData = pWarrantyContentData;
    }
    /**
     * Retrieves the WarrantyContentData property.
     *
     * @return
     *  WarrantyContentData containing the WarrantyContentData property.
     */
    public WarrantyContentData getWarrantyContentData(){
        return mWarrantyContentData;
    }


    
}
