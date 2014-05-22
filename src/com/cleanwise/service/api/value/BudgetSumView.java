
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        BudgetSumView
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
 * <code>BudgetSumView</code> is a ViewObject class for UI.
 */
public class BudgetSumView
extends ValueObject
{
   
    private static final long serialVersionUID = 838327702968535549L;
    private BigDecimal mYtdAmountSpent;
    private BigDecimal mYtdAmountAllocated;
    private BigDecimal mYtdDifference;
    private BigDecimal mAnnualAmountSpent;
    private BigDecimal mAnnualAmountAllocated;
    private BigDecimal mAnnualDifference;
    private java.util.List mPerCostCenterVector;
    private java.util.List mSpendDetailVector;

    /**
     * Constructor.
     */
    public BudgetSumView ()
    {
    }

    /**
     * Constructor. 
     */
    public BudgetSumView(BigDecimal parm1, BigDecimal parm2, BigDecimal parm3, BigDecimal parm4, BigDecimal parm5, BigDecimal parm6, java.util.List parm7, java.util.List parm8)
    {
        mYtdAmountSpent = parm1;
        mYtdAmountAllocated = parm2;
        mYtdDifference = parm3;
        mAnnualAmountSpent = parm4;
        mAnnualAmountAllocated = parm5;
        mAnnualDifference = parm6;
        mPerCostCenterVector = parm7;
        mSpendDetailVector = parm8;
        
    }

    /**
     * Creates a new BudgetSumView
     *
     * @return
     *  Newly initialized BudgetSumView object.
     */
    public static BudgetSumView createValue () 
    {
        BudgetSumView valueView = new BudgetSumView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this BudgetSumView object
     */
    public String toString()
    {
        return "[" + "YtdAmountSpent=" + mYtdAmountSpent + ", YtdAmountAllocated=" + mYtdAmountAllocated + ", YtdDifference=" + mYtdDifference + ", AnnualAmountSpent=" + mAnnualAmountSpent + ", AnnualAmountAllocated=" + mAnnualAmountAllocated + ", AnnualDifference=" + mAnnualDifference + ", PerCostCenterVector=" + mPerCostCenterVector + ", SpendDetailVector=" + mSpendDetailVector + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("BudgetSum");
	root.setAttribute("Id", String.valueOf(mYtdAmountSpent));

	Element node;

        node = doc.createElement("YtdAmountAllocated");
        node.appendChild(doc.createTextNode(String.valueOf(mYtdAmountAllocated)));
        root.appendChild(node);

        node = doc.createElement("YtdDifference");
        node.appendChild(doc.createTextNode(String.valueOf(mYtdDifference)));
        root.appendChild(node);

        node = doc.createElement("AnnualAmountSpent");
        node.appendChild(doc.createTextNode(String.valueOf(mAnnualAmountSpent)));
        root.appendChild(node);

        node = doc.createElement("AnnualAmountAllocated");
        node.appendChild(doc.createTextNode(String.valueOf(mAnnualAmountAllocated)));
        root.appendChild(node);

        node = doc.createElement("AnnualDifference");
        node.appendChild(doc.createTextNode(String.valueOf(mAnnualDifference)));
        root.appendChild(node);

        node = doc.createElement("PerCostCenterVector");
        node.appendChild(doc.createTextNode(String.valueOf(mPerCostCenterVector)));
        root.appendChild(node);

        node = doc.createElement("SpendDetailVector");
        node.appendChild(doc.createTextNode(String.valueOf(mSpendDetailVector)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public BudgetSumView copy()  {
      BudgetSumView obj = new BudgetSumView();
      obj.setYtdAmountSpent(mYtdAmountSpent);
      obj.setYtdAmountAllocated(mYtdAmountAllocated);
      obj.setYtdDifference(mYtdDifference);
      obj.setAnnualAmountSpent(mAnnualAmountSpent);
      obj.setAnnualAmountAllocated(mAnnualAmountAllocated);
      obj.setAnnualDifference(mAnnualDifference);
      obj.setPerCostCenterVector(mPerCostCenterVector);
      obj.setSpendDetailVector(mSpendDetailVector);
      
      return obj;
    }

    
    /**
     * Sets the YtdAmountSpent property.
     *
     * @param pYtdAmountSpent
     *  BigDecimal to use to update the property.
     */
    public void setYtdAmountSpent(BigDecimal pYtdAmountSpent){
        this.mYtdAmountSpent = pYtdAmountSpent;
    }
    /**
     * Retrieves the YtdAmountSpent property.
     *
     * @return
     *  BigDecimal containing the YtdAmountSpent property.
     */
    public BigDecimal getYtdAmountSpent(){
        return mYtdAmountSpent;
    }


    /**
     * Sets the YtdAmountAllocated property.
     *
     * @param pYtdAmountAllocated
     *  BigDecimal to use to update the property.
     */
    public void setYtdAmountAllocated(BigDecimal pYtdAmountAllocated){
        this.mYtdAmountAllocated = pYtdAmountAllocated;
    }
    /**
     * Retrieves the YtdAmountAllocated property.
     *
     * @return
     *  BigDecimal containing the YtdAmountAllocated property.
     */
    public BigDecimal getYtdAmountAllocated(){
        return mYtdAmountAllocated;
    }


    /**
     * Sets the YtdDifference property.
     *
     * @param pYtdDifference
     *  BigDecimal to use to update the property.
     */
    public void setYtdDifference(BigDecimal pYtdDifference){
        this.mYtdDifference = pYtdDifference;
    }
    /**
     * Retrieves the YtdDifference property.
     *
     * @return
     *  BigDecimal containing the YtdDifference property.
     */
    public BigDecimal getYtdDifference(){
        return mYtdDifference;
    }


    /**
     * Sets the AnnualAmountSpent property.
     *
     * @param pAnnualAmountSpent
     *  BigDecimal to use to update the property.
     */
    public void setAnnualAmountSpent(BigDecimal pAnnualAmountSpent){
        this.mAnnualAmountSpent = pAnnualAmountSpent;
    }
    /**
     * Retrieves the AnnualAmountSpent property.
     *
     * @return
     *  BigDecimal containing the AnnualAmountSpent property.
     */
    public BigDecimal getAnnualAmountSpent(){
        return mAnnualAmountSpent;
    }


    /**
     * Sets the AnnualAmountAllocated property.
     *
     * @param pAnnualAmountAllocated
     *  BigDecimal to use to update the property.
     */
    public void setAnnualAmountAllocated(BigDecimal pAnnualAmountAllocated){
        this.mAnnualAmountAllocated = pAnnualAmountAllocated;
    }
    /**
     * Retrieves the AnnualAmountAllocated property.
     *
     * @return
     *  BigDecimal containing the AnnualAmountAllocated property.
     */
    public BigDecimal getAnnualAmountAllocated(){
        return mAnnualAmountAllocated;
    }


    /**
     * Sets the AnnualDifference property.
     *
     * @param pAnnualDifference
     *  BigDecimal to use to update the property.
     */
    public void setAnnualDifference(BigDecimal pAnnualDifference){
        this.mAnnualDifference = pAnnualDifference;
    }
    /**
     * Retrieves the AnnualDifference property.
     *
     * @return
     *  BigDecimal containing the AnnualDifference property.
     */
    public BigDecimal getAnnualDifference(){
        return mAnnualDifference;
    }


    /**
     * Sets the PerCostCenterVector property.
     *
     * @param pPerCostCenterVector
     *  java.util.List to use to update the property.
     */
    public void setPerCostCenterVector(java.util.List pPerCostCenterVector){
        this.mPerCostCenterVector = pPerCostCenterVector;
    }
    /**
     * Retrieves the PerCostCenterVector property.
     *
     * @return
     *  java.util.List containing the PerCostCenterVector property.
     */
    public java.util.List getPerCostCenterVector(){
        return mPerCostCenterVector;
    }


    /**
     * Sets the SpendDetailVector property.
     *
     * @param pSpendDetailVector
     *  java.util.List to use to update the property.
     */
    public void setSpendDetailVector(java.util.List pSpendDetailVector){
        this.mSpendDetailVector = pSpendDetailVector;
    }
    /**
     * Retrieves the SpendDetailVector property.
     *
     * @return
     *  java.util.List containing the SpendDetailVector property.
     */
    public java.util.List getSpendDetailVector(){
        return mSpendDetailVector;
    }


    
}
