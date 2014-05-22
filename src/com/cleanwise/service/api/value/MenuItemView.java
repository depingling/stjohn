
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        MenuItemView
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
 * <code>MenuItemView</code> is a ViewObject class for UI.
 */
public class MenuItemView
extends ValueObject
{
   
   public interface DISPLAY_STATUS {
   		public static final String OPEN="OPEN";
   		public static final String CLOSE="CLOSE";
   		public static final String BLOCK_FOR_CLOSE="BLOCK_FOR_CLOSE";
   		public static final String BLOCK_FOR_OPEN="BLOCK_FOR_OPEN";
   		public static final String UNAVAILABLE="UNAVAILABLE";
   }
   
   public interface ATTR {
   		public static final String ROOT="ROOT";
   }
   
    private static final long serialVersionUID = -1L;
    private String mKey;
    private String mName;
    private String mLink;
    private String mDisplayStatus;
    private MenuItemViewVector mSubItems;

    /**
     * Constructor.
     */
    public MenuItemView ()
    {
        mKey = "";
        mName = "";
        mLink = "";
        mDisplayStatus = "";
    }

    /**
     * Constructor. 
     */
    public MenuItemView(String parm1, String parm2, String parm3, String parm4, MenuItemViewVector parm5)
    {
        mKey = parm1;
        mName = parm2;
        mLink = parm3;
        mDisplayStatus = parm4;
        mSubItems = parm5;
        
    }

    /**
     * Creates a new MenuItemView
     *
     * @return
     *  Newly initialized MenuItemView object.
     */
    public static MenuItemView createValue () 
    {
        MenuItemView valueView = new MenuItemView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this MenuItemView object
     */
    public String toString()
    {
        return "[" + "Key=" + mKey + ", Name=" + mName + ", Link=" + mLink + ", DisplayStatus=" + mDisplayStatus + ", SubItems=" + mSubItems + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("MenuItem");
	root.setAttribute("Id", String.valueOf(mKey));

	Element node;

        node = doc.createElement("Name");
        node.appendChild(doc.createTextNode(String.valueOf(mName)));
        root.appendChild(node);

        node = doc.createElement("Link");
        node.appendChild(doc.createTextNode(String.valueOf(mLink)));
        root.appendChild(node);

        node = doc.createElement("DisplayStatus");
        node.appendChild(doc.createTextNode(String.valueOf(mDisplayStatus)));
        root.appendChild(node);

        node = doc.createElement("SubItems");
        node.appendChild(doc.createTextNode(String.valueOf(mSubItems)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public MenuItemView copy()  {
      MenuItemView obj = new MenuItemView();
      obj.setKey(mKey);
      obj.setName(mName);
      obj.setLink(mLink);
      obj.setDisplayStatus(mDisplayStatus);
      obj.setSubItems(mSubItems);
      
      return obj;
    }

    
    /**
     * Sets the Key property.
     *
     * @param pKey
     *  String to use to update the property.
     */
    public void setKey(String pKey){
        this.mKey = pKey;
    }
    /**
     * Retrieves the Key property.
     *
     * @return
     *  String containing the Key property.
     */
    public String getKey(){
        return mKey;
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


    /**
     * Sets the DisplayStatus property.
     *
     * @param pDisplayStatus
     *  String to use to update the property.
     */
    public void setDisplayStatus(String pDisplayStatus){
        this.mDisplayStatus = pDisplayStatus;
    }
    /**
     * Retrieves the DisplayStatus property.
     *
     * @return
     *  String containing the DisplayStatus property.
     */
    public String getDisplayStatus(){
        return mDisplayStatus;
    }


    /**
     * Sets the SubItems property.
     *
     * @param pSubItems
     *  MenuItemViewVector to use to update the property.
     */
    public void setSubItems(MenuItemViewVector pSubItems){
        this.mSubItems = pSubItems;
    }
    /**
     * Retrieves the SubItems property.
     *
     * @return
     *  MenuItemViewVector containing the SubItems property.
     */
    public MenuItemViewVector getSubItems(){
        return mSubItems;
    }


    
}
