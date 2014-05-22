
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        BudgetView
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
 * <code>BudgetView</code> is a ViewObject class for UI.
 */
public class BudgetView
extends ValueObject
{
   
    private static final long serialVersionUID = -1L;
    private BudgetData mBudgetData;
    private BudgetDetailDataVector mDetails;

    /**
     * Constructor.
     */
    public BudgetView ()
    {
    }

    /**
     * Constructor. 
     */
    public BudgetView(BudgetData parm1, BudgetDetailDataVector parm2)
    {
        mBudgetData = parm1;
        mDetails = parm2;
        
    }

    /**
     * Creates a new BudgetView
     *
     * @return
     *  Newly initialized BudgetView object.
     */
    public static BudgetView createValue () 
    {
        BudgetView valueView = new BudgetView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this BudgetView object
     */
    public String toString()
    {
        return "[" + "BudgetData=" + mBudgetData + ", Details=" + mDetails + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("Budget");
	root.setAttribute("Id", String.valueOf(mBudgetData));

	Element node;

        node = doc.createElement("Details");
        node.appendChild(doc.createTextNode(String.valueOf(mDetails)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public BudgetView copy()  {
      BudgetView obj = new BudgetView();
      obj.setBudgetData(mBudgetData);
      obj.setDetails(mDetails);
      
      return obj;
    }

    
    /**
     * Sets the BudgetData property.
     *
     * @param pBudgetData
     *  BudgetData to use to update the property.
     */
    public void setBudgetData(BudgetData pBudgetData){
        this.mBudgetData = pBudgetData;
    }
    /**
     * Retrieves the BudgetData property.
     *
     * @return
     *  BudgetData containing the BudgetData property.
     */
    public BudgetData getBudgetData(){
        return mBudgetData;
    }


    /**
     * Sets the Details property.
     *
     * @param pDetails
     *  BudgetDetailDataVector to use to update the property.
     */
    public void setDetails(BudgetDetailDataVector pDetails){
        this.mDetails = pDetails;
    }
    /**
     * Retrieves the Details property.
     *
     * @return
     *  BudgetDetailDataVector containing the Details property.
     */
    public BudgetDetailDataVector getDetails(){
        return mDetails;
    }


    
}
