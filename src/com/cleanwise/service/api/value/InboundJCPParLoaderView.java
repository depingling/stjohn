
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        InboundJCPParLoaderView
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
 * <code>InboundJCPParLoaderView</code> is a ViewObject class for UI.
 */
public class InboundJCPParLoaderView
extends ValueObject
{
   
    private static final long serialVersionUID = -1L;
    private String mSiteName;
    private String mItemSku;
    private String mQuantity;

    /**
     * Constructor.
     */
    public InboundJCPParLoaderView ()
    {
        mSiteName = "";
        mItemSku = "";
        mQuantity = "";
    }

    /**
     * Constructor. 
     */
    public InboundJCPParLoaderView(String parm1, String parm2, String parm3)
    {
        mSiteName = parm1;
        mItemSku = parm2;
        mQuantity = parm3;
        
    }

    /**
     * Creates a new InboundJCPParLoaderView
     *
     * @return
     *  Newly initialized InboundJCPParLoaderView object.
     */
    public static InboundJCPParLoaderView createValue () 
    {
        InboundJCPParLoaderView valueView = new InboundJCPParLoaderView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this InboundJCPParLoaderView object
     */
    public String toString()
    {
        return "[" + "SiteName=" + mSiteName + ", ItemSku=" + mItemSku + ", Quantity=" + mQuantity + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("InboundJCPParLoader");
	root.setAttribute("Id", String.valueOf(mSiteName));

	Element node;

        node = doc.createElement("ItemSku");
        node.appendChild(doc.createTextNode(String.valueOf(mItemSku)));
        root.appendChild(node);

        node = doc.createElement("Quantity");
        node.appendChild(doc.createTextNode(String.valueOf(mQuantity)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public InboundJCPParLoaderView copy()  {
      InboundJCPParLoaderView obj = new InboundJCPParLoaderView();
      obj.setSiteName(mSiteName);
      obj.setItemSku(mItemSku);
      obj.setQuantity(mQuantity);
      
      return obj;
    }

    
    /**
     * Sets the SiteName property.
     *
     * @param pSiteName
     *  String to use to update the property.
     */
    public void setSiteName(String pSiteName){
        this.mSiteName = pSiteName;
    }
    /**
     * Retrieves the SiteName property.
     *
     * @return
     *  String containing the SiteName property.
     */
    public String getSiteName(){
        return mSiteName;
    }


    /**
     * Sets the ItemSku property.
     *
     * @param pItemSku
     *  String to use to update the property.
     */
    public void setItemSku(String pItemSku){
        this.mItemSku = pItemSku;
    }
    /**
     * Retrieves the ItemSku property.
     *
     * @return
     *  String containing the ItemSku property.
     */
    public String getItemSku(){
        return mItemSku;
    }


    /**
     * Sets the Quantity property.
     *
     * @param pQuantity
     *  String to use to update the property.
     */
    public void setQuantity(String pQuantity){
        this.mQuantity = pQuantity;
    }
    /**
     * Retrieves the Quantity property.
     *
     * @return
     *  String containing the Quantity property.
     */
    public String getQuantity(){
        return mQuantity;
    }


    
}
