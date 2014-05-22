
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        AssetContentView
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
 * <code>AssetContentView</code> is a ViewObject class for UI.
 */
public class AssetContentView
extends ValueObject
{
   
    private static final long serialVersionUID = 2815379614485828046L;
    private ContentView mContent;
    private AssetContentData mAssetContentData;

    /**
     * Constructor.
     */
    public AssetContentView ()
    {
    }

    /**
     * Constructor. 
     */
    public AssetContentView(ContentView parm1, AssetContentData parm2)
    {
        mContent = parm1;
        mAssetContentData = parm2;
        
    }

    /**
     * Creates a new AssetContentView
     *
     * @return
     *  Newly initialized AssetContentView object.
     */
    public static AssetContentView createValue () 
    {
        AssetContentView valueView = new AssetContentView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this AssetContentView object
     */
    public String toString()
    {
        return "[" + "Content=" + mContent + ", AssetContentData=" + mAssetContentData + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("AssetContent");
	root.setAttribute("Id", String.valueOf(mContent));

	Element node;

        node = doc.createElement("AssetContentData");
        node.appendChild(doc.createTextNode(String.valueOf(mAssetContentData)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public AssetContentView copy()  {
      AssetContentView obj = new AssetContentView();
      obj.setContent(mContent);
      obj.setAssetContentData(mAssetContentData);
      
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
     * Sets the AssetContentData property.
     *
     * @param pAssetContentData
     *  AssetContentData to use to update the property.
     */
    public void setAssetContentData(AssetContentData pAssetContentData){
        this.mAssetContentData = pAssetContentData;
    }
    /**
     * Retrieves the AssetContentData property.
     *
     * @return
     *  AssetContentData containing the AssetContentData property.
     */
    public AssetContentData getAssetContentData(){
        return mAssetContentData;
    }


    
}
