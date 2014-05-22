
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        XpedxCatalogSiteView
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
 * <code>XpedxCatalogSiteView</code> is a ViewObject class for UI.
 */
public class XpedxCatalogSiteView
extends ValueObject
{
   
    private static final long serialVersionUID = -1L;
    private String mCatalogReference;
    private String mAccountReference;
    private String mSiteReference;

    /**
     * Constructor.
     */
    public XpedxCatalogSiteView ()
    {
        mCatalogReference = "";
        mAccountReference = "";
        mSiteReference = "";
    }

    /**
     * Constructor. 
     */
    public XpedxCatalogSiteView(String parm1, String parm2, String parm3)
    {
        mCatalogReference = parm1;
        mAccountReference = parm2;
        mSiteReference = parm3;
        
    }

    /**
     * Creates a new XpedxCatalogSiteView
     *
     * @return
     *  Newly initialized XpedxCatalogSiteView object.
     */
    public static XpedxCatalogSiteView createValue () 
    {
        XpedxCatalogSiteView valueView = new XpedxCatalogSiteView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this XpedxCatalogSiteView object
     */
    public String toString()
    {
        return "[" + "CatalogReference=" + mCatalogReference + ", AccountReference=" + mAccountReference + ", SiteReference=" + mSiteReference + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("XpedxCatalogSite");
	root.setAttribute("Id", String.valueOf(mCatalogReference));

	Element node;

        node = doc.createElement("AccountReference");
        node.appendChild(doc.createTextNode(String.valueOf(mAccountReference)));
        root.appendChild(node);

        node = doc.createElement("SiteReference");
        node.appendChild(doc.createTextNode(String.valueOf(mSiteReference)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public XpedxCatalogSiteView copy()  {
      XpedxCatalogSiteView obj = new XpedxCatalogSiteView();
      obj.setCatalogReference(mCatalogReference);
      obj.setAccountReference(mAccountReference);
      obj.setSiteReference(mSiteReference);
      
      return obj;
    }

    
    /**
     * Sets the CatalogReference property.
     *
     * @param pCatalogReference
     *  String to use to update the property.
     */
    public void setCatalogReference(String pCatalogReference){
        this.mCatalogReference = pCatalogReference;
    }
    /**
     * Retrieves the CatalogReference property.
     *
     * @return
     *  String containing the CatalogReference property.
     */
    public String getCatalogReference(){
        return mCatalogReference;
    }


    /**
     * Sets the AccountReference property.
     *
     * @param pAccountReference
     *  String to use to update the property.
     */
    public void setAccountReference(String pAccountReference){
        this.mAccountReference = pAccountReference;
    }
    /**
     * Retrieves the AccountReference property.
     *
     * @return
     *  String containing the AccountReference property.
     */
    public String getAccountReference(){
        return mAccountReference;
    }


    /**
     * Sets the SiteReference property.
     *
     * @param pSiteReference
     *  String to use to update the property.
     */
    public void setSiteReference(String pSiteReference){
        this.mSiteReference = pSiteReference;
    }
    /**
     * Retrieves the SiteReference property.
     *
     * @return
     *  String containing the SiteReference property.
     */
    public String getSiteReference(){
        return mSiteReference;
    }


    
}
