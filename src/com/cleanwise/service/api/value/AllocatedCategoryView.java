
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        AllocatedCategoryView
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

import java.math.BigDecimal;


/**
 * <code>AllocatedCategoryView</code> is a ViewObject class for UI.
 */
public class AllocatedCategoryView
extends ValueObject
{
   
    private static final long serialVersionUID = 2397322246667131014L;
    private AllocatedCategoryData mAllocatedCategory;
    private String mAllocatedPercent;
    private BigDecimal mAmount;
    private BigDecimal mAllLocationsAmount;

    /**
     * Constructor.
     */
    public AllocatedCategoryView ()
    {
        mAllocatedPercent = "";
    }

    /**
     * Constructor. 
     */
    public AllocatedCategoryView(AllocatedCategoryData parm1, String parm2, BigDecimal parm3, BigDecimal parm4)
    {
        mAllocatedCategory = parm1;
        mAllocatedPercent = parm2;
        mAmount = parm3;
        mAllLocationsAmount = parm4;
        
    }

    /**
     * Creates a new AllocatedCategoryView
     *
     * @return
     *  Newly initialized AllocatedCategoryView object.
     */
    public static AllocatedCategoryView createValue () 
    {
        AllocatedCategoryView valueView = new AllocatedCategoryView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this AllocatedCategoryView object
     */
    public String toString()
    {
        return "[" + "AllocatedCategory=" + mAllocatedCategory + ", AllocatedPercent=" + mAllocatedPercent + ", Amount=" + mAmount + ", AllLocationsAmount=" + mAllLocationsAmount + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("AllocatedCategory");
	root.setAttribute("Id", String.valueOf(mAllocatedCategory));

	Element node;

        node = doc.createElement("AllocatedPercent");
        node.appendChild(doc.createTextNode(String.valueOf(mAllocatedPercent)));
        root.appendChild(node);

        node = doc.createElement("Amount");
        node.appendChild(doc.createTextNode(String.valueOf(mAmount)));
        root.appendChild(node);

        node = doc.createElement("AllLocationsAmount");
        node.appendChild(doc.createTextNode(String.valueOf(mAllLocationsAmount)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public AllocatedCategoryView copy()  {
      AllocatedCategoryView obj = new AllocatedCategoryView();
      obj.setAllocatedCategory(mAllocatedCategory);
      obj.setAllocatedPercent(mAllocatedPercent);
      obj.setAmount(mAmount);
      obj.setAllLocationsAmount(mAllLocationsAmount);
      
      return obj;
    }

    
    /**
     * Sets the AllocatedCategory property.
     *
     * @param pAllocatedCategory
     *  AllocatedCategoryData to use to update the property.
     */
    public void setAllocatedCategory(AllocatedCategoryData pAllocatedCategory){
        this.mAllocatedCategory = pAllocatedCategory;
    }
    /**
     * Retrieves the AllocatedCategory property.
     *
     * @return
     *  AllocatedCategoryData containing the AllocatedCategory property.
     */
    public AllocatedCategoryData getAllocatedCategory(){
        return mAllocatedCategory;
    }


    /**
     * Sets the AllocatedPercent property.
     *
     * @param pAllocatedPercent
     *  String to use to update the property.
     */
    public void setAllocatedPercent(String pAllocatedPercent){
        this.mAllocatedPercent = pAllocatedPercent;
    }
    /**
     * Retrieves the AllocatedPercent property.
     *
     * @return
     *  String containing the AllocatedPercent property.
     */
    public String getAllocatedPercent(){
        return mAllocatedPercent;
    }


    /**
     * Sets the Amount property.
     *
     * @param pAmount
     *  BigDecimal to use to update the property.
     */
    public void setAmount(BigDecimal pAmount){
        this.mAmount = pAmount;
    }
    /**
     * Retrieves the Amount property.
     *
     * @return
     *  BigDecimal containing the Amount property.
     */
    public BigDecimal getAmount(){
        return mAmount;
    }


    /**
     * Sets the AllLocationsAmount property.
     *
     * @param pAllLocationsAmount
     *  BigDecimal to use to update the property.
     */
    public void setAllLocationsAmount(BigDecimal pAllLocationsAmount){
        this.mAllLocationsAmount = pAllLocationsAmount;
    }
    /**
     * Retrieves the AllLocationsAmount property.
     *
     * @return
     *  BigDecimal containing the AllLocationsAmount property.
     */
    public BigDecimal getAllLocationsAmount(){
        return mAllLocationsAmount;
    }


    
}
