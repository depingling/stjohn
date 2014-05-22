
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        UiPageView
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
 * <code>UiPageView</code> is a ViewObject class for UI.
 */
public class UiPageView
extends ValueObject
{
   
    private static final long serialVersionUID = -1L;
    private UiData mUiData;
    private UiPageData mUiPage;
    private UiControlViewVector mUiControls;

    /**
     * Constructor.
     */
    public UiPageView ()
    {
    }

    /**
     * Constructor. 
     */
    public UiPageView(UiData parm1, UiPageData parm2, UiControlViewVector parm3)
    {
        mUiData = parm1;
        mUiPage = parm2;
        mUiControls = parm3;
        
    }

    /**
     * Creates a new UiPageView
     *
     * @return
     *  Newly initialized UiPageView object.
     */
    public static UiPageView createValue () 
    {
        UiPageView valueView = new UiPageView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this UiPageView object
     */
    public String toString()
    {
        return "[" + "UiData=" + mUiData + ", UiPage=" + mUiPage + ", UiControls=" + mUiControls + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("UiPage");
	root.setAttribute("Id", String.valueOf(mUiData));

	Element node;

        node = doc.createElement("UiPage");
        node.appendChild(doc.createTextNode(String.valueOf(mUiPage)));
        root.appendChild(node);

        node = doc.createElement("UiControls");
        node.appendChild(doc.createTextNode(String.valueOf(mUiControls)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public UiPageView copy()  {
      UiPageView obj = new UiPageView();
      obj.setUiData(mUiData);
      obj.setUiPage(mUiPage);
      obj.setUiControls(mUiControls);
      
      return obj;
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
     * Sets the UiPage property.
     *
     * @param pUiPage
     *  UiPageData to use to update the property.
     */
    public void setUiPage(UiPageData pUiPage){
        this.mUiPage = pUiPage;
    }
    /**
     * Retrieves the UiPage property.
     *
     * @return
     *  UiPageData containing the UiPage property.
     */
    public UiPageData getUiPage(){
        return mUiPage;
    }


    /**
     * Sets the UiControls property.
     *
     * @param pUiControls
     *  UiControlViewVector to use to update the property.
     */
    public void setUiControls(UiControlViewVector pUiControls){
        this.mUiControls = pUiControls;
    }
    /**
     * Retrieves the UiControls property.
     *
     * @return
     *  UiControlViewVector containing the UiControls property.
     */
    public UiControlViewVector getUiControls(){
        return mUiControls;
    }


    
}
