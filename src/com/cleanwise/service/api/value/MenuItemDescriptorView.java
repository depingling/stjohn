
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        MenuItemDescriptorView
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
 * <code>MenuItemDescriptorView</code> is a ViewObject class for UI.
 */
public class MenuItemDescriptorView
extends ValueObject
{
   
    private static final long serialVersionUID = 
            -1L
        ;
    private java.util.HashSet mParentKeys;
    private java.util.HashSet mChildKeys;
    private int mTreeLevel;
    private java.util.HashSet mKeyPath;

    /**
     * Constructor.
     */
    public MenuItemDescriptorView ()
    {
    }

    /**
     * Constructor. 
     */
    public MenuItemDescriptorView(java.util.HashSet parm1, java.util.HashSet parm2, int parm3, java.util.HashSet parm4)
    {
        mParentKeys = parm1;
        mChildKeys = parm2;
        mTreeLevel = parm3;
        mKeyPath = parm4;
        
    }

    /**
     * Creates a new MenuItemDescriptorView
     *
     * @return
     *  Newly initialized MenuItemDescriptorView object.
     */
    public static MenuItemDescriptorView createValue () 
    {
        MenuItemDescriptorView valueView = new MenuItemDescriptorView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this MenuItemDescriptorView object
     */
    public String toString()
    {
        return "[" + "ParentKeys=" + mParentKeys + ", ChildKeys=" + mChildKeys + ", TreeLevel=" + mTreeLevel + ", KeyPath=" + mKeyPath + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return ElementNode.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("MenuItemDescriptor");
	root.setAttribute("Id", String.valueOf(mParentKeys));

	Element node;

        node = doc.createElement("ChildKeys");
        node.appendChild(doc.createTextNode(String.valueOf(mChildKeys)));
        root.appendChild(node);

        node = doc.createElement("TreeLevel");
        node.appendChild(doc.createTextNode(String.valueOf(mTreeLevel)));
        root.appendChild(node);

        node = doc.createElement("KeyPath");
        node.appendChild(doc.createTextNode(String.valueOf(mKeyPath)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public MenuItemDescriptorView copy()  {
      MenuItemDescriptorView obj = new MenuItemDescriptorView();
      obj.setParentKeys(mParentKeys);
      obj.setChildKeys(mChildKeys);
      obj.setTreeLevel(mTreeLevel);
      obj.setKeyPath(mKeyPath);
      
      return obj;
    }

    
    /**
     * Sets the ParentKeys property.
     *
     * @param pParentKeys
     *  java.util.HashSet to use to update the property.
     */
    public void setParentKeys(java.util.HashSet pParentKeys){
        this.mParentKeys = pParentKeys;
    }
    /**
     * Retrieves the ParentKeys property.
     *
     * @return
     *  java.util.HashSet containing the ParentKeys property.
     */
    public java.util.HashSet getParentKeys(){
        return mParentKeys;
    }


    /**
     * Sets the ChildKeys property.
     *
     * @param pChildKeys
     *  java.util.HashSet to use to update the property.
     */
    public void setChildKeys(java.util.HashSet pChildKeys){
        this.mChildKeys = pChildKeys;
    }
    /**
     * Retrieves the ChildKeys property.
     *
     * @return
     *  java.util.HashSet containing the ChildKeys property.
     */
    public java.util.HashSet getChildKeys(){
        return mChildKeys;
    }


    /**
     * Sets the TreeLevel property.
     *
     * @param pTreeLevel
     *  int to use to update the property.
     */
    public void setTreeLevel(int pTreeLevel){
        this.mTreeLevel = pTreeLevel;
    }
    /**
     * Retrieves the TreeLevel property.
     *
     * @return
     *  int containing the TreeLevel property.
     */
    public int getTreeLevel(){
        return mTreeLevel;
    }


    /**
     * Sets the KeyPath property.
     *
     * @param pKeyPath
     *  java.util.HashSet to use to update the property.
     */
    public void setKeyPath(java.util.HashSet pKeyPath){
        this.mKeyPath = pKeyPath;
    }
    /**
     * Retrieves the KeyPath property.
     *
     * @return
     *  java.util.HashSet containing the KeyPath property.
     */
    public java.util.HashSet getKeyPath(){
        return mKeyPath;
    }


    
}
