
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        AccCategoryToCostCenterView
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
 * <code>AccCategoryToCostCenterView</code> is a ViewObject class for UI.
 */
public class AccCategoryToCostCenterView
extends ValueObject
{
   
    private static final long serialVersionUID = -8739572085033774740L;
    private int mLastReqSiteId;
    private int mLastReqSiteCatalogId;
    private int mAccountId;
    private int mAccCatalogId;
    private HashMap mCategoryToCostCenterMap;

    /**
     * Constructor.
     */
    public AccCategoryToCostCenterView ()
    {
    }

    /**
     * Constructor. 
     */
    public AccCategoryToCostCenterView(int parm1, int parm2, int parm3, int parm4, HashMap parm5)
    {
        mLastReqSiteId = parm1;
        mLastReqSiteCatalogId = parm2;
        mAccountId = parm3;
        mAccCatalogId = parm4;
        mCategoryToCostCenterMap = parm5;
        
    }

    /**
     * Creates a new AccCategoryToCostCenterView
     *
     * @return
     *  Newly initialized AccCategoryToCostCenterView object.
     */
    public static AccCategoryToCostCenterView createValue () 
    {
        AccCategoryToCostCenterView valueView = new AccCategoryToCostCenterView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this AccCategoryToCostCenterView object
     */
    public String toString()
    {
        return "[" + "LastReqSiteId=" + mLastReqSiteId + ", LastReqSiteCatalogId=" + mLastReqSiteCatalogId + ", AccountId=" + mAccountId + ", AccCatalogId=" + mAccCatalogId + ", CategoryToCostCenterMap=" + mCategoryToCostCenterMap + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("AccCategoryToCostCenter");
	root.setAttribute("Id", String.valueOf(mLastReqSiteId));

	Element node;

        node = doc.createElement("LastReqSiteCatalogId");
        node.appendChild(doc.createTextNode(String.valueOf(mLastReqSiteCatalogId)));
        root.appendChild(node);

        node = doc.createElement("AccountId");
        node.appendChild(doc.createTextNode(String.valueOf(mAccountId)));
        root.appendChild(node);

        node = doc.createElement("AccCatalogId");
        node.appendChild(doc.createTextNode(String.valueOf(mAccCatalogId)));
        root.appendChild(node);

        node = doc.createElement("CategoryToCostCenterMap");
        node.appendChild(doc.createTextNode(String.valueOf(mCategoryToCostCenterMap)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public AccCategoryToCostCenterView copy()  {
      AccCategoryToCostCenterView obj = new AccCategoryToCostCenterView();
      obj.setLastReqSiteId(mLastReqSiteId);
      obj.setLastReqSiteCatalogId(mLastReqSiteCatalogId);
      obj.setAccountId(mAccountId);
      obj.setAccCatalogId(mAccCatalogId);
      obj.setCategoryToCostCenterMap(mCategoryToCostCenterMap);
      
      return obj;
    }

    
    /**
     * Sets the LastReqSiteId property.
     *
     * @param pLastReqSiteId
     *  int to use to update the property.
     */
    public void setLastReqSiteId(int pLastReqSiteId){
        this.mLastReqSiteId = pLastReqSiteId;
    }
    /**
     * Retrieves the LastReqSiteId property.
     *
     * @return
     *  int containing the LastReqSiteId property.
     */
    public int getLastReqSiteId(){
        return mLastReqSiteId;
    }


    /**
     * Sets the LastReqSiteCatalogId property.
     *
     * @param pLastReqSiteCatalogId
     *  int to use to update the property.
     */
    public void setLastReqSiteCatalogId(int pLastReqSiteCatalogId){
        this.mLastReqSiteCatalogId = pLastReqSiteCatalogId;
    }
    /**
     * Retrieves the LastReqSiteCatalogId property.
     *
     * @return
     *  int containing the LastReqSiteCatalogId property.
     */
    public int getLastReqSiteCatalogId(){
        return mLastReqSiteCatalogId;
    }


    /**
     * Sets the AccountId property.
     *
     * @param pAccountId
     *  int to use to update the property.
     */
    public void setAccountId(int pAccountId){
        this.mAccountId = pAccountId;
    }
    /**
     * Retrieves the AccountId property.
     *
     * @return
     *  int containing the AccountId property.
     */
    public int getAccountId(){
        return mAccountId;
    }


    /**
     * Sets the AccCatalogId property.
     *
     * @param pAccCatalogId
     *  int to use to update the property.
     */
    public void setAccCatalogId(int pAccCatalogId){
        this.mAccCatalogId = pAccCatalogId;
    }
    /**
     * Retrieves the AccCatalogId property.
     *
     * @return
     *  int containing the AccCatalogId property.
     */
    public int getAccCatalogId(){
        return mAccCatalogId;
    }


    /**
     * Sets the CategoryToCostCenterMap property.
     *
     * @param pCategoryToCostCenterMap
     *  HashMap to use to update the property.
     */
    public void setCategoryToCostCenterMap(HashMap pCategoryToCostCenterMap){
        this.mCategoryToCostCenterMap = pCategoryToCostCenterMap;
    }
    /**
     * Retrieves the CategoryToCostCenterMap property.
     *
     * @return
     *  HashMap containing the CategoryToCostCenterMap property.
     */
    public HashMap getCategoryToCostCenterMap(){
        return mCategoryToCostCenterMap;
    }


    
}
