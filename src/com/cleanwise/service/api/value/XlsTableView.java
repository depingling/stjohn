
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        XlsTableView
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
 * <code>XlsTableView</code> is a ViewObject class for UI.
 */
public class XlsTableView
extends ValueObject
{
   
    private static final long serialVersionUID = -3968322480163250118L;
    private UploadData mHeader;
    private UploadSkuView[] mContent;

    /**
     * Constructor.
     */
    public XlsTableView ()
    {
    }

    /**
     * Constructor. 
     */
    public XlsTableView(UploadData parm1, UploadSkuView[] parm2)
    {
        mHeader = parm1;
        mContent = parm2;
        
    }

    /**
     * Creates a new XlsTableView
     *
     * @return
     *  Newly initialized XlsTableView object.
     */
    public static XlsTableView createValue () 
    {
        XlsTableView valueView = new XlsTableView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this XlsTableView object
     */
    public String toString()
    {
        return "[" + "Header=" + mHeader + ", Content=" + mContent + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("XlsTable");
	root.setAttribute("Id", String.valueOf(mHeader));

	Element node;

        node = doc.createElement("Content");
        node.appendChild(doc.createTextNode(String.valueOf(mContent)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public XlsTableView copy()  {
      XlsTableView obj = new XlsTableView();
      obj.setHeader(mHeader);
      obj.setContent(mContent);
      
      return obj;
    }

    
    /**
     * Sets the Header property.
     *
     * @param pHeader
     *  UploadData to use to update the property.
     */
    public void setHeader(UploadData pHeader){
        this.mHeader = pHeader;
    }
    /**
     * Retrieves the Header property.
     *
     * @return
     *  UploadData containing the Header property.
     */
    public UploadData getHeader(){
        return mHeader;
    }


    /**
     * Sets the Content property.
     *
     * @param pContent
     *  UploadSkuView[] to use to update the property.
     */
    public void setContent(UploadSkuView[] pContent){
        this.mContent = pContent;
    }
    /**
     * Retrieves the Content property.
     *
     * @return
     *  UploadSkuView[] containing the Content property.
     */
    public UploadSkuView[] getContent(){
        return mContent;
    }


    
}
