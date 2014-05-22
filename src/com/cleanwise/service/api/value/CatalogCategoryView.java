
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        CatalogCategoryView
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
 * <code>CatalogCategoryView</code> is a ViewObject class for UI.
 */
public class CatalogCategoryView
extends ValueObject
{
   
    private static final long serialVersionUID = -6906637327425296853L;
    private int mCategoryId;
    private String mCategoryName;
    private int mCatalogId;
    private String mCatalogName;
    private int mMajorCategoryId;
    private String mMajorCategoryName;

    /**
     * Constructor.
     */
    public CatalogCategoryView ()
    {
        mCategoryName = "";
        mCatalogName = "";
        mMajorCategoryName = "";
    }

    /**
     * Constructor. 
     */
    public CatalogCategoryView(int parm1, String parm2, int parm3, String parm4, int parm5, String parm6)
    {
        mCategoryId = parm1;
        mCategoryName = parm2;
        mCatalogId = parm3;
        mCatalogName = parm4;
        mMajorCategoryId = parm5;
        mMajorCategoryName = parm6;
        
    }

    /**
     * Creates a new CatalogCategoryView
     *
     * @return
     *  Newly initialized CatalogCategoryView object.
     */
    public static CatalogCategoryView createValue () 
    {
        CatalogCategoryView valueView = new CatalogCategoryView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this CatalogCategoryView object
     */
    public String toString()
    {
        return "[" + "CategoryId=" + mCategoryId + ", CategoryName=" + mCategoryName + ", CatalogId=" + mCatalogId + ", CatalogName=" + mCatalogName + ", MajorCategoryId=" + mMajorCategoryId + ", MajorCategoryName=" + mMajorCategoryName + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("CatalogCategory");
	root.setAttribute("Id", String.valueOf(mCategoryId));

	Element node;

        node = doc.createElement("CategoryName");
        node.appendChild(doc.createTextNode(String.valueOf(mCategoryName)));
        root.appendChild(node);

        node = doc.createElement("CatalogId");
        node.appendChild(doc.createTextNode(String.valueOf(mCatalogId)));
        root.appendChild(node);

        node = doc.createElement("CatalogName");
        node.appendChild(doc.createTextNode(String.valueOf(mCatalogName)));
        root.appendChild(node);

        node = doc.createElement("MajorCategoryId");
        node.appendChild(doc.createTextNode(String.valueOf(mMajorCategoryId)));
        root.appendChild(node);

        node = doc.createElement("MajorCategoryName");
        node.appendChild(doc.createTextNode(String.valueOf(mMajorCategoryName)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public CatalogCategoryView copy()  {
      CatalogCategoryView obj = new CatalogCategoryView();
      obj.setCategoryId(mCategoryId);
      obj.setCategoryName(mCategoryName);
      obj.setCatalogId(mCatalogId);
      obj.setCatalogName(mCatalogName);
      obj.setMajorCategoryId(mMajorCategoryId);
      obj.setMajorCategoryName(mMajorCategoryName);
      
      return obj;
    }

    
    /**
     * Sets the CategoryId property.
     *
     * @param pCategoryId
     *  int to use to update the property.
     */
    public void setCategoryId(int pCategoryId){
        this.mCategoryId = pCategoryId;
    }
    /**
     * Retrieves the CategoryId property.
     *
     * @return
     *  int containing the CategoryId property.
     */
    public int getCategoryId(){
        return mCategoryId;
    }


    /**
     * Sets the CategoryName property.
     *
     * @param pCategoryName
     *  String to use to update the property.
     */
    public void setCategoryName(String pCategoryName){
        this.mCategoryName = pCategoryName;
    }
    /**
     * Retrieves the CategoryName property.
     *
     * @return
     *  String containing the CategoryName property.
     */
    public String getCategoryName(){
        return mCategoryName;
    }


    /**
     * Sets the CatalogId property.
     *
     * @param pCatalogId
     *  int to use to update the property.
     */
    public void setCatalogId(int pCatalogId){
        this.mCatalogId = pCatalogId;
    }
    /**
     * Retrieves the CatalogId property.
     *
     * @return
     *  int containing the CatalogId property.
     */
    public int getCatalogId(){
        return mCatalogId;
    }


    /**
     * Sets the CatalogName property.
     *
     * @param pCatalogName
     *  String to use to update the property.
     */
    public void setCatalogName(String pCatalogName){
        this.mCatalogName = pCatalogName;
    }
    /**
     * Retrieves the CatalogName property.
     *
     * @return
     *  String containing the CatalogName property.
     */
    public String getCatalogName(){
        return mCatalogName;
    }


    /**
     * Sets the MajorCategoryId property.
     *
     * @param pMajorCategoryId
     *  int to use to update the property.
     */
    public void setMajorCategoryId(int pMajorCategoryId){
        this.mMajorCategoryId = pMajorCategoryId;
    }
    /**
     * Retrieves the MajorCategoryId property.
     *
     * @return
     *  int containing the MajorCategoryId property.
     */
    public int getMajorCategoryId(){
        return mMajorCategoryId;
    }


    /**
     * Sets the MajorCategoryName property.
     *
     * @param pMajorCategoryName
     *  String to use to update the property.
     */
    public void setMajorCategoryName(String pMajorCategoryName){
        this.mMajorCategoryName = pMajorCategoryName;
    }
    /**
     * Retrieves the MajorCategoryName property.
     *
     * @return
     *  String containing the MajorCategoryName property.
     */
    public String getMajorCategoryName(){
        return mMajorCategoryName;
    }


    
}
