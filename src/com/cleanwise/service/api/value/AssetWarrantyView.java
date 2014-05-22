
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        AssetWarrantyView
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
 * <code>AssetWarrantyView</code> is a ViewObject class for UI.
 */
public class AssetWarrantyView
extends ValueObject
{
   
    private static final long serialVersionUID = 6968855950583764063L;
    private AssetWarrantyData mAssetWarrantyData;
    private AssetView mAssetView;
    private WarrantyNoteDataVector mAssetWarrantyNotes;

    /**
     * Constructor.
     */
    public AssetWarrantyView ()
    {
    }

    /**
     * Constructor. 
     */
    public AssetWarrantyView(AssetWarrantyData parm1, AssetView parm2, WarrantyNoteDataVector parm3)
    {
        mAssetWarrantyData = parm1;
        mAssetView = parm2;
        mAssetWarrantyNotes = parm3;
        
    }

    /**
     * Creates a new AssetWarrantyView
     *
     * @return
     *  Newly initialized AssetWarrantyView object.
     */
    public static AssetWarrantyView createValue () 
    {
        AssetWarrantyView valueView = new AssetWarrantyView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this AssetWarrantyView object
     */
    public String toString()
    {
        return "[" + "AssetWarrantyData=" + mAssetWarrantyData + ", AssetView=" + mAssetView + ", AssetWarrantyNotes=" + mAssetWarrantyNotes + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("AssetWarranty");
	root.setAttribute("Id", String.valueOf(mAssetWarrantyData));

	Element node;

        node = doc.createElement("AssetView");
        node.appendChild(doc.createTextNode(String.valueOf(mAssetView)));
        root.appendChild(node);

        node = doc.createElement("AssetWarrantyNotes");
        node.appendChild(doc.createTextNode(String.valueOf(mAssetWarrantyNotes)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public AssetWarrantyView copy()  {
      AssetWarrantyView obj = new AssetWarrantyView();
      obj.setAssetWarrantyData(mAssetWarrantyData);
      obj.setAssetView(mAssetView);
      obj.setAssetWarrantyNotes(mAssetWarrantyNotes);
      
      return obj;
    }

    
    /**
     * Sets the AssetWarrantyData property.
     *
     * @param pAssetWarrantyData
     *  AssetWarrantyData to use to update the property.
     */
    public void setAssetWarrantyData(AssetWarrantyData pAssetWarrantyData){
        this.mAssetWarrantyData = pAssetWarrantyData;
    }
    /**
     * Retrieves the AssetWarrantyData property.
     *
     * @return
     *  AssetWarrantyData containing the AssetWarrantyData property.
     */
    public AssetWarrantyData getAssetWarrantyData(){
        return mAssetWarrantyData;
    }


    /**
     * Sets the AssetView property.
     *
     * @param pAssetView
     *  AssetView to use to update the property.
     */
    public void setAssetView(AssetView pAssetView){
        this.mAssetView = pAssetView;
    }
    /**
     * Retrieves the AssetView property.
     *
     * @return
     *  AssetView containing the AssetView property.
     */
    public AssetView getAssetView(){
        return mAssetView;
    }


    /**
     * Sets the AssetWarrantyNotes property.
     *
     * @param pAssetWarrantyNotes
     *  WarrantyNoteDataVector to use to update the property.
     */
    public void setAssetWarrantyNotes(WarrantyNoteDataVector pAssetWarrantyNotes){
        this.mAssetWarrantyNotes = pAssetWarrantyNotes;
    }
    /**
     * Retrieves the AssetWarrantyNotes property.
     *
     * @return
     *  WarrantyNoteDataVector containing the AssetWarrantyNotes property.
     */
    public WarrantyNoteDataVector getAssetWarrantyNotes(){
        return mAssetWarrantyNotes;
    }


    
}
