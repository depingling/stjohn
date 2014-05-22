
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        AssetContentDetailView
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
 * <code>AssetContentDetailView</code> is a ViewObject class for UI.
 */
public class AssetContentDetailView
extends ValueObject
{
   
    private static final long serialVersionUID = -2422202942137462903L;
    private ContentDetailView mContent;
    private AssetContentData mAssetContentData;

    /**
     * Constructor.
     */
    public AssetContentDetailView ()
    {
    }

    /**
     * Constructor. 
     */
    public AssetContentDetailView(ContentDetailView parm1, AssetContentData parm2)
    {
        mContent = parm1;
        mAssetContentData = parm2;
        
    }

    /**
     * Creates a new AssetContentDetailView
     *
     * @return
     *  Newly initialized AssetContentDetailView object.
     */
    public static AssetContentDetailView createValue () 
    {
        AssetContentDetailView valueView = new AssetContentDetailView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this AssetContentDetailView object
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
        Element root = doc.createElement("AssetContentDetail");
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
    public AssetContentDetailView copy()  {
      AssetContentDetailView obj = new AssetContentDetailView();
      obj.setContent(mContent);
      obj.setAssetContentData(mAssetContentData);
      
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
