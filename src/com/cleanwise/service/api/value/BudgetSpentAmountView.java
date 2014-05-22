
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        BudgetSpentAmountView
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
 * <code>BudgetSpentAmountView</code> is a ViewObject class for UI.
 */
public class BudgetSpentAmountView
extends ValueObject
{
   
    private static final long serialVersionUID = 
            -1L
        ;
    private BigDecimal mAmountSpent;
    private BigDecimal mAmountAllocated;
    private BigDecimal mAmountDifference;
    private BigDecimal mAmount;
    private BigDecimal mAmountTotal;

    /**
     * Constructor.
     */
    public BudgetSpentAmountView ()
    {
    }

    /**
     * Constructor. 
     */
    public BudgetSpentAmountView(BigDecimal parm1, BigDecimal parm2, BigDecimal parm3, BigDecimal parm4, BigDecimal parm5)
    {
        mAmountSpent = parm1;
        mAmountAllocated = parm2;
        mAmountDifference = parm3;
        mAmount = parm4;
        mAmountTotal = parm5;
        
    }

    /**
     * Creates a new BudgetSpentAmountView
     *
     * @return
     *  Newly initialized BudgetSpentAmountView object.
     */
    public static BudgetSpentAmountView createValue () 
    {
        BudgetSpentAmountView valueView = new BudgetSpentAmountView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this BudgetSpentAmountView object
     */
    public String toString()
    {
        return "[" + "AmountSpent=" + mAmountSpent + ", AmountAllocated=" + mAmountAllocated + ", AmountDifference=" + mAmountDifference + ", Amount=" + mAmount + ", AmountTotal=" + mAmountTotal + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("BudgetSpentAmount");
	root.setAttribute("Id", String.valueOf(mAmountSpent));

	Element node;

        node = doc.createElement("AmountAllocated");
        node.appendChild(doc.createTextNode(String.valueOf(mAmountAllocated)));
        root.appendChild(node);

        node = doc.createElement("AmountDifference");
        node.appendChild(doc.createTextNode(String.valueOf(mAmountDifference)));
        root.appendChild(node);

        node = doc.createElement("Amount");
        node.appendChild(doc.createTextNode(String.valueOf(mAmount)));
        root.appendChild(node);

        node = doc.createElement("AmountTotal");
        node.appendChild(doc.createTextNode(String.valueOf(mAmountTotal)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public BudgetSpentAmountView copy()  {
      BudgetSpentAmountView obj = new BudgetSpentAmountView();
      obj.setAmountSpent(mAmountSpent);
      obj.setAmountAllocated(mAmountAllocated);
      obj.setAmountDifference(mAmountDifference);
      obj.setAmount(mAmount);
      obj.setAmountTotal(mAmountTotal);
      
      return obj;
    }

    
    /**
     * Sets the AmountSpent property.
     *
     * @param pAmountSpent
     *  BigDecimal to use to update the property.
     */
    public void setAmountSpent(BigDecimal pAmountSpent){
        this.mAmountSpent = pAmountSpent;
    }
    /**
     * Retrieves the AmountSpent property.
     *
     * @return
     *  BigDecimal containing the AmountSpent property.
     */
    public BigDecimal getAmountSpent(){
        return mAmountSpent;
    }


    /**
     * Sets the AmountAllocated property.
     *
     * @param pAmountAllocated
     *  BigDecimal to use to update the property.
     */
    public void setAmountAllocated(BigDecimal pAmountAllocated){
        this.mAmountAllocated = pAmountAllocated;
    }
    /**
     * Retrieves the AmountAllocated property.
     *
     * @return
     *  BigDecimal containing the AmountAllocated property.
     */
    public BigDecimal getAmountAllocated(){
        return mAmountAllocated;
    }


    /**
     * Sets the AmountDifference property.
     *
     * @param pAmountDifference
     *  BigDecimal to use to update the property.
     */
    public void setAmountDifference(BigDecimal pAmountDifference){
        this.mAmountDifference = pAmountDifference;
    }
    /**
     * Retrieves the AmountDifference property.
     *
     * @return
     *  BigDecimal containing the AmountDifference property.
     */
    public BigDecimal getAmountDifference(){
        return mAmountDifference;
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
     * Sets the AmountTotal property.
     *
     * @param pAmountTotal
     *  BigDecimal to use to update the property.
     */
    public void setAmountTotal(BigDecimal pAmountTotal){
        this.mAmountTotal = pAmountTotal;
    }
    /**
     * Retrieves the AmountTotal property.
     *
     * @return
     *  BigDecimal containing the AmountTotal property.
     */
    public BigDecimal getAmountTotal(){
        return mAmountTotal;
    }


    
}
