
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        BudgetSpentShortView
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
 * <code>BudgetSpentShortView</code> is a ViewObject class for UI.
 */
public class BudgetSpentShortView
extends ValueObject
{
   
    private static final long serialVersionUID = 
            -1L
        ;
    private int mBusEntityId;
    private int mCostCenterId;
    private String mBudgetTypeCd;
    private int mBudgetYear;
    private int mBudgetPeriod;
    private BigDecimal mAmountSpent;
    private BigDecimal mAmountAllocated;

    /**
     * Constructor.
     */
    public BudgetSpentShortView ()
    {
        mBudgetTypeCd = "";
    }

    /**
     * Constructor. 
     */
    public BudgetSpentShortView(int parm1, int parm2, String parm3, int parm4, int parm5, BigDecimal parm6, BigDecimal parm7)
    {
        mBusEntityId = parm1;
        mCostCenterId = parm2;
        mBudgetTypeCd = parm3;
        mBudgetYear = parm4;
        mBudgetPeriod = parm5;
        mAmountSpent = parm6;
        mAmountAllocated = parm7;
        
    }

    /**
     * Creates a new BudgetSpentShortView
     *
     * @return
     *  Newly initialized BudgetSpentShortView object.
     */
    public static BudgetSpentShortView createValue () 
    {
        BudgetSpentShortView valueView = new BudgetSpentShortView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this BudgetSpentShortView object
     */
    public String toString()
    {
        return "[" + "BusEntityId=" + mBusEntityId + ", CostCenterId=" + mCostCenterId + ", BudgetTypeCd=" + mBudgetTypeCd + ", BudgetYear=" + mBudgetYear + ", BudgetPeriod=" + mBudgetPeriod + ", AmountSpent=" + mAmountSpent + ", AmountAllocated=" + mAmountAllocated + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("BudgetSpentShort");
	root.setAttribute("Id", String.valueOf(mBusEntityId));

	Element node;

        node = doc.createElement("CostCenterId");
        node.appendChild(doc.createTextNode(String.valueOf(mCostCenterId)));
        root.appendChild(node);

        node = doc.createElement("BudgetTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mBudgetTypeCd)));
        root.appendChild(node);

        node = doc.createElement("BudgetYear");
        node.appendChild(doc.createTextNode(String.valueOf(mBudgetYear)));
        root.appendChild(node);

        node = doc.createElement("BudgetPeriod");
        node.appendChild(doc.createTextNode(String.valueOf(mBudgetPeriod)));
        root.appendChild(node);

        node = doc.createElement("AmountSpent");
        node.appendChild(doc.createTextNode(String.valueOf(mAmountSpent)));
        root.appendChild(node);

        node = doc.createElement("AmountAllocated");
        node.appendChild(doc.createTextNode(String.valueOf(mAmountAllocated)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public BudgetSpentShortView copy()  {
      BudgetSpentShortView obj = new BudgetSpentShortView();
      obj.setBusEntityId(mBusEntityId);
      obj.setCostCenterId(mCostCenterId);
      obj.setBudgetTypeCd(mBudgetTypeCd);
      obj.setBudgetYear(mBudgetYear);
      obj.setBudgetPeriod(mBudgetPeriod);
      obj.setAmountSpent(mAmountSpent);
      obj.setAmountAllocated(mAmountAllocated);
      
      return obj;
    }

    
    /**
     * Sets the BusEntityId property.
     *
     * @param pBusEntityId
     *  int to use to update the property.
     */
    public void setBusEntityId(int pBusEntityId){
        this.mBusEntityId = pBusEntityId;
    }
    /**
     * Retrieves the BusEntityId property.
     *
     * @return
     *  int containing the BusEntityId property.
     */
    public int getBusEntityId(){
        return mBusEntityId;
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


    /**
     * Sets the BudgetTypeCd property.
     *
     * @param pBudgetTypeCd
     *  String to use to update the property.
     */
    public void setBudgetTypeCd(String pBudgetTypeCd){
        this.mBudgetTypeCd = pBudgetTypeCd;
    }
    /**
     * Retrieves the BudgetTypeCd property.
     *
     * @return
     *  String containing the BudgetTypeCd property.
     */
    public String getBudgetTypeCd(){
        return mBudgetTypeCd;
    }


    /**
     * Sets the BudgetYear property.
     *
     * @param pBudgetYear
     *  int to use to update the property.
     */
    public void setBudgetYear(int pBudgetYear){
        this.mBudgetYear = pBudgetYear;
    }
    /**
     * Retrieves the BudgetYear property.
     *
     * @return
     *  int containing the BudgetYear property.
     */
    public int getBudgetYear(){
        return mBudgetYear;
    }


    /**
     * Sets the BudgetPeriod property.
     *
     * @param pBudgetPeriod
     *  int to use to update the property.
     */
    public void setBudgetPeriod(int pBudgetPeriod){
        this.mBudgetPeriod = pBudgetPeriod;
    }
    /**
     * Retrieves the BudgetPeriod property.
     *
     * @return
     *  int containing the BudgetPeriod property.
     */
    public int getBudgetPeriod(){
        return mBudgetPeriod;
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


    
}
