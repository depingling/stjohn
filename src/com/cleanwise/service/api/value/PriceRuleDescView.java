
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        PriceRuleDescView
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
 * <code>PriceRuleDescView</code> is a ViewObject class for UI.
 */
public class PriceRuleDescView
extends ValueObject
{
   
    private static final long serialVersionUID = -1L;
    private PriceRuleData mPriceRule;
    private PriceRuleDetailDataVector mPriceRuleDetails;

    /**
     * Constructor.
     */
    public PriceRuleDescView ()
    {
    }

    /**
     * Constructor. 
     */
    public PriceRuleDescView(PriceRuleData parm1, PriceRuleDetailDataVector parm2)
    {
        mPriceRule = parm1;
        mPriceRuleDetails = parm2;
        
    }

    /**
     * Creates a new PriceRuleDescView
     *
     * @return
     *  Newly initialized PriceRuleDescView object.
     */
    public static PriceRuleDescView createValue () 
    {
        PriceRuleDescView valueView = new PriceRuleDescView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this PriceRuleDescView object
     */
    public String toString()
    {
        return "[" + "PriceRule=" + mPriceRule + ", PriceRuleDetails=" + mPriceRuleDetails + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("PriceRuleDesc");
	root.setAttribute("Id", String.valueOf(mPriceRule));

	Element node;

        node = doc.createElement("PriceRuleDetails");
        node.appendChild(doc.createTextNode(String.valueOf(mPriceRuleDetails)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public PriceRuleDescView copy()  {
      PriceRuleDescView obj = new PriceRuleDescView();
      obj.setPriceRule(mPriceRule);
      obj.setPriceRuleDetails(mPriceRuleDetails);
      
      return obj;
    }

    
    /**
     * Sets the PriceRule property.
     *
     * @param pPriceRule
     *  PriceRuleData to use to update the property.
     */
    public void setPriceRule(PriceRuleData pPriceRule){
        this.mPriceRule = pPriceRule;
    }
    /**
     * Retrieves the PriceRule property.
     *
     * @return
     *  PriceRuleData containing the PriceRule property.
     */
    public PriceRuleData getPriceRule(){
        return mPriceRule;
    }


    /**
     * Sets the PriceRuleDetails property.
     *
     * @param pPriceRuleDetails
     *  PriceRuleDetailDataVector to use to update the property.
     */
    public void setPriceRuleDetails(PriceRuleDetailDataVector pPriceRuleDetails){
        this.mPriceRuleDetails = pPriceRuleDetails;
    }
    /**
     * Retrieves the PriceRuleDetails property.
     *
     * @return
     *  PriceRuleDetailDataVector containing the PriceRuleDetails property.
     */
    public PriceRuleDetailDataVector getPriceRuleDetails(){
        return mPriceRuleDetails;
    }


    
}
