
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        CategoryInfoView
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
 * <code>CategoryInfoView</code> is a ViewObject class for UI.
 */
public class CategoryInfoView
extends ValueObject
{
   
    private static final long serialVersionUID = -1L;
    private String mCategoryKey;
    private String mName;
    private String mLink;

    /**
     * Constructor.
     */
    public CategoryInfoView ()
    {
        mCategoryKey = "";
        mName = "";
        mLink = "";
    }

    /**
     * Constructor. 
     */
    public CategoryInfoView(String parm1, String parm2, String parm3)
    {
        mCategoryKey = parm1;
        mName = parm2;
        mLink = parm3;
        
    }

    /**
     * Creates a new CategoryInfoView
     *
     * @return
     *  Newly initialized CategoryInfoView object.
     */
    public static CategoryInfoView createValue () 
    {
        CategoryInfoView valueView = new CategoryInfoView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this CategoryInfoView object
     */
    public String toString()
    {
        return "[" + "CategoryKey=" + mCategoryKey + ", Name=" + mName + ", Link=" + mLink + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("CategoryInfo");
	root.setAttribute("Id", String.valueOf(mCategoryKey));

	Element node;

        node = doc.createElement("Name");
        node.appendChild(doc.createTextNode(String.valueOf(mName)));
        root.appendChild(node);

        node = doc.createElement("Link");
        node.appendChild(doc.createTextNode(String.valueOf(mLink)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public CategoryInfoView copy()  {
      CategoryInfoView obj = new CategoryInfoView();
      obj.setCategoryKey(mCategoryKey);
      obj.setName(mName);
      obj.setLink(mLink);
      
      return obj;
    }

    
    /**
     * Sets the CategoryKey property.
     *
     * @param pCategoryKey
     *  String to use to update the property.
     */
    public void setCategoryKey(String pCategoryKey){
        this.mCategoryKey = pCategoryKey;
    }
    /**
     * Retrieves the CategoryKey property.
     *
     * @return
     *  String containing the CategoryKey property.
     */
    public String getCategoryKey(){
        return mCategoryKey;
    }


    /**
     * Sets the Name property.
     *
     * @param pName
     *  String to use to update the property.
     */
    public void setName(String pName){
        this.mName = pName;
    }
    /**
     * Retrieves the Name property.
     *
     * @return
     *  String containing the Name property.
     */
    public String getName(){
        return mName;
    }


    /**
     * Sets the Link property.
     *
     * @param pLink
     *  String to use to update the property.
     */
    public void setLink(String pLink){
        this.mLink = pLink;
    }
    /**
     * Retrieves the Link property.
     *
     * @return
     *  String containing the Link property.
     */
    public String getLink(){
        return mLink;
    }


    
}
