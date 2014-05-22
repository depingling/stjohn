
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        OrderHandlingDetailView
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
 * <code>OrderHandlingDetailView</code> is a ViewObject class for UI.
 */
public class OrderHandlingDetailView
extends ValueObject
{
   
    private static final long serialVersionUID = -3945228001116105895L;
    private int mPriceRuleId;
    private String mRuleShortDesc;
    private String mChargeTypeCd;
    private BigDecimal mAmount;
    private ArrayList mItemIdVector;

    /**
     * Constructor.
     */
    public OrderHandlingDetailView ()
    {
        mRuleShortDesc = "";
        mChargeTypeCd = "";
    }

    /**
     * Constructor. 
     */
    public OrderHandlingDetailView(int parm1, String parm2, String parm3, BigDecimal parm4, ArrayList parm5)
    {
        mPriceRuleId = parm1;
        mRuleShortDesc = parm2;
        mChargeTypeCd = parm3;
        mAmount = parm4;
        mItemIdVector = parm5;
        
    }

    /**
     * Creates a new OrderHandlingDetailView
     *
     * @return
     *  Newly initialized OrderHandlingDetailView object.
     */
    public static OrderHandlingDetailView createValue () 
    {
        OrderHandlingDetailView valueView = new OrderHandlingDetailView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this OrderHandlingDetailView object
     */
    public String toString()
    {
        return "[" + "PriceRuleId=" + mPriceRuleId + ", RuleShortDesc=" + mRuleShortDesc + ", ChargeTypeCd=" + mChargeTypeCd + ", Amount=" + mAmount + ", ItemIdVector=" + mItemIdVector + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("OrderHandlingDetail");
	root.setAttribute("Id", String.valueOf(mPriceRuleId));

	Element node;

        node = doc.createElement("RuleShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mRuleShortDesc)));
        root.appendChild(node);

        node = doc.createElement("ChargeTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mChargeTypeCd)));
        root.appendChild(node);

        node = doc.createElement("Amount");
        node.appendChild(doc.createTextNode(String.valueOf(mAmount)));
        root.appendChild(node);

        node = doc.createElement("ItemIdVector");
        node.appendChild(doc.createTextNode(String.valueOf(mItemIdVector)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public OrderHandlingDetailView copy()  {
      OrderHandlingDetailView obj = new OrderHandlingDetailView();
      obj.setPriceRuleId(mPriceRuleId);
      obj.setRuleShortDesc(mRuleShortDesc);
      obj.setChargeTypeCd(mChargeTypeCd);
      obj.setAmount(mAmount);
      obj.setItemIdVector(mItemIdVector);
      
      return obj;
    }

    
    /**
     * Sets the PriceRuleId property.
     *
     * @param pPriceRuleId
     *  int to use to update the property.
     */
    public void setPriceRuleId(int pPriceRuleId){
        this.mPriceRuleId = pPriceRuleId;
    }
    /**
     * Retrieves the PriceRuleId property.
     *
     * @return
     *  int containing the PriceRuleId property.
     */
    public int getPriceRuleId(){
        return mPriceRuleId;
    }


    /**
     * Sets the RuleShortDesc property.
     *
     * @param pRuleShortDesc
     *  String to use to update the property.
     */
    public void setRuleShortDesc(String pRuleShortDesc){
        this.mRuleShortDesc = pRuleShortDesc;
    }
    /**
     * Retrieves the RuleShortDesc property.
     *
     * @return
     *  String containing the RuleShortDesc property.
     */
    public String getRuleShortDesc(){
        return mRuleShortDesc;
    }


    /**
     * Sets the ChargeTypeCd property.
     *
     * @param pChargeTypeCd
     *  String to use to update the property.
     */
    public void setChargeTypeCd(String pChargeTypeCd){
        this.mChargeTypeCd = pChargeTypeCd;
    }
    /**
     * Retrieves the ChargeTypeCd property.
     *
     * @return
     *  String containing the ChargeTypeCd property.
     */
    public String getChargeTypeCd(){
        return mChargeTypeCd;
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
     * Sets the ItemIdVector property.
     *
     * @param pItemIdVector
     *  ArrayList to use to update the property.
     */
    public void setItemIdVector(ArrayList pItemIdVector){
        this.mItemIdVector = pItemIdVector;
    }
    /**
     * Retrieves the ItemIdVector property.
     *
     * @return
     *  ArrayList containing the ItemIdVector property.
     */
    public ArrayList getItemIdVector(){
        return mItemIdVector;
    }


    
}
