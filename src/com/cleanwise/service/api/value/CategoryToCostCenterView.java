
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        CategoryToCostCenterView
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
 * <code>CategoryToCostCenterView</code> is a ViewObject class for UI.
 */
public class CategoryToCostCenterView
extends ValueObject
{
   
    private static final long serialVersionUID = -8739572085033774740L;
    private String mCategoryName;
    private int mCostCenterId;

    /**
     * Constructor.
     */
    public CategoryToCostCenterView ()
    {
        mCategoryName = "";
    }

    /**
     * Constructor. 
     */
    public CategoryToCostCenterView(String parm1, int parm2)
    {
        mCategoryName = parm1;
        mCostCenterId = parm2;
        
    }

    /**
     * Creates a new CategoryToCostCenterView
     *
     * @return
     *  Newly initialized CategoryToCostCenterView object.
     */
    public static CategoryToCostCenterView createValue () 
    {
        CategoryToCostCenterView valueView = new CategoryToCostCenterView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this CategoryToCostCenterView object
     */
    public String toString()
    {
        return "[" + "CategoryName=" + mCategoryName + ", CostCenterId=" + mCostCenterId + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("CategoryToCostCenter");
	root.setAttribute("Id", String.valueOf(mCategoryName));

	Element node;

        node = doc.createElement("CostCenterId");
        node.appendChild(doc.createTextNode(String.valueOf(mCostCenterId)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public CategoryToCostCenterView copy()  {
      CategoryToCostCenterView obj = new CategoryToCostCenterView();
      obj.setCategoryName(mCategoryName);
      obj.setCostCenterId(mCostCenterId);
      
      return obj;
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
     * Sets the CostCenterId property.
     *
     * @param pCostCenterId
     *  int to use to update the property.
     */
    public void setCostCenterId(int pCostCenterId){
        this.mCostCenterId = pCostCenterId;
    }
    /**
     * Retrieves the CostCenterId property.
     *
     * @return
     *  int containing the CostCenterId property.
     */
    public int getCostCenterId(){
        return mCostCenterId;
    }


    
}
