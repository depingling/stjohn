
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        WarrantyContentDetailView
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
 * <code>WarrantyContentDetailView</code> is a ViewObject class for UI.
 */
public class WarrantyContentDetailView
extends ValueObject
{
   
    private static final long serialVersionUID = 7353540570186813672L;
    private ContentDetailView mContent;
    private WarrantyContentData mWarrantyContentData;

    /**
     * Constructor.
     */
    public WarrantyContentDetailView ()
    {
    }

    /**
     * Constructor. 
     */
    public WarrantyContentDetailView(ContentDetailView parm1, WarrantyContentData parm2)
    {
        mContent = parm1;
        mWarrantyContentData = parm2;
        
    }

    /**
     * Creates a new WarrantyContentDetailView
     *
     * @return
     *  Newly initialized WarrantyContentDetailView object.
     */
    public static WarrantyContentDetailView createValue () 
    {
        WarrantyContentDetailView valueView = new WarrantyContentDetailView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this WarrantyContentDetailView object
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
        Element root = doc.createElement("WarrantyContentDetail");
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
    public WarrantyContentDetailView copy()  {
      WarrantyContentDetailView obj = new WarrantyContentDetailView();
      obj.setContent(mContent);
      obj.setWarrantyContentData(mWarrantyContentData);
      
      return obj;
    }

    
    /**
     * Sets the Content property.
     *
     * @param pContent
     *  ContentDetailView to use to update the property.
     */
    public void setContent(ContentDetailView pContent){
        this.mContent = pContent;
    }
    /**
     * Retrieves the Content property.
     *
     * @return
     *  ContentDetailView containing the Content property.
     */
    public ContentDetailView getContent(){
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
