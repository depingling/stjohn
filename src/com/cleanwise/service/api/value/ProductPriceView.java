
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ProductPriceView
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
 * <code>ProductPriceView</code> is a ViewObject class for UI.
 */
public class ProductPriceView
extends ValueObject
{
   
    private static final long serialVersionUID = -1L;
    private BigDecimal mListPrice;
    private BigDecimal mContractPrice;
    private BigDecimal mDistCost;
    private BigDecimal mDistBaseCost;
    private BigDecimal mDiscount;
    private BigDecimal mPriceList1;
    private BigDecimal mPriceList2;

    /**
     * Constructor.
     */
    public ProductPriceView ()
    {
    }

    /**
     * Constructor. 
     */
    public ProductPriceView(BigDecimal parm1, BigDecimal parm2, BigDecimal parm3, BigDecimal parm4, BigDecimal parm5, BigDecimal parm6, BigDecimal parm7)
    {
        mListPrice = parm1;
        mContractPrice = parm2;
        mDistCost = parm3;
        mDistBaseCost = parm4;
        mDiscount = parm5;
        mPriceList1 = parm6;
        mPriceList2 = parm7;
        
    }

    /**
     * Creates a new ProductPriceView
     *
     * @return
     *  Newly initialized ProductPriceView object.
     */
    public static ProductPriceView createValue () 
    {
        ProductPriceView valueView = new ProductPriceView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ProductPriceView object
     */
    public String toString()
    {
        return "[" + "ListPrice=" + mListPrice + ", ContractPrice=" + mContractPrice + ", DistCost=" + mDistCost + ", DistBaseCost=" + mDistBaseCost + ", Discount=" + mDiscount + ", PriceList1=" + mPriceList1 + ", PriceList2=" + mPriceList2 + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("ProductPrice");
	root.setAttribute("Id", String.valueOf(mListPrice));

	Element node;

        node = doc.createElement("ContractPrice");
        node.appendChild(doc.createTextNode(String.valueOf(mContractPrice)));
        root.appendChild(node);

        node = doc.createElement("DistCost");
        node.appendChild(doc.createTextNode(String.valueOf(mDistCost)));
        root.appendChild(node);

        node = doc.createElement("DistBaseCost");
        node.appendChild(doc.createTextNode(String.valueOf(mDistBaseCost)));
        root.appendChild(node);

        node = doc.createElement("Discount");
        node.appendChild(doc.createTextNode(String.valueOf(mDiscount)));
        root.appendChild(node);

        node = doc.createElement("PriceList1");
        node.appendChild(doc.createTextNode(String.valueOf(mPriceList1)));
        root.appendChild(node);

        node = doc.createElement("PriceList2");
        node.appendChild(doc.createTextNode(String.valueOf(mPriceList2)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public ProductPriceView copy()  {
      ProductPriceView obj = new ProductPriceView();
      obj.setListPrice(mListPrice);
      obj.setContractPrice(mContractPrice);
      obj.setDistCost(mDistCost);
      obj.setDistBaseCost(mDistBaseCost);
      obj.setDiscount(mDiscount);
      obj.setPriceList1(mPriceList1);
      obj.setPriceList2(mPriceList2);
      
      return obj;
    }

    
    /**
     * Sets the ListPrice property.
     *
     * @param pListPrice
     *  BigDecimal to use to update the property.
     */
    public void setListPrice(BigDecimal pListPrice){
        this.mListPrice = pListPrice;
    }
    /**
     * Retrieves the ListPrice property.
     *
     * @return
     *  BigDecimal containing the ListPrice property.
     */
    public BigDecimal getListPrice(){
        return mListPrice;
    }


    /**
     * Sets the ContractPrice property.
     *
     * @param pContractPrice
     *  BigDecimal to use to update the property.
     */
    public void setContractPrice(BigDecimal pContractPrice){
        this.mContractPrice = pContractPrice;
    }
    /**
     * Retrieves the ContractPrice property.
     *
     * @return
     *  BigDecimal containing the ContractPrice property.
     */
    public BigDecimal getContractPrice(){
        return mContractPrice;
    }


    /**
     * Sets the DistCost property.
     *
     * @param pDistCost
     *  BigDecimal to use to update the property.
     */
    public void setDistCost(BigDecimal pDistCost){
        this.mDistCost = pDistCost;
    }
    /**
     * Retrieves the DistCost property.
     *
     * @return
     *  BigDecimal containing the DistCost property.
     */
    public BigDecimal getDistCost(){
        return mDistCost;
    }


    /**
     * Sets the DistBaseCost property.
     *
     * @param pDistBaseCost
     *  BigDecimal to use to update the property.
     */
    public void setDistBaseCost(BigDecimal pDistBaseCost){
        this.mDistBaseCost = pDistBaseCost;
    }
    /**
     * Retrieves the DistBaseCost property.
     *
     * @return
     *  BigDecimal containing the DistBaseCost property.
     */
    public BigDecimal getDistBaseCost(){
        return mDistBaseCost;
    }


    /**
     * Sets the Discount property.
     *
     * @param pDiscount
     *  BigDecimal to use to update the property.
     */
    public void setDiscount(BigDecimal pDiscount){
        this.mDiscount = pDiscount;
    }
    /**
     * Retrieves the Discount property.
     *
     * @return
     *  BigDecimal containing the Discount property.
     */
    public BigDecimal getDiscount(){
        return mDiscount;
    }


    /**
     * Sets the PriceList1 property.
     *
     * @param pPriceList1
     *  BigDecimal to use to update the property.
     */
    public void setPriceList1(BigDecimal pPriceList1){
        this.mPriceList1 = pPriceList1;
    }
    /**
     * Retrieves the PriceList1 property.
     *
     * @return
     *  BigDecimal containing the PriceList1 property.
     */
    public BigDecimal getPriceList1(){
        return mPriceList1;
    }


    /**
     * Sets the PriceList2 property.
     *
     * @param pPriceList2
     *  BigDecimal to use to update the property.
     */
    public void setPriceList2(BigDecimal pPriceList2){
        this.mPriceList2 = pPriceList2;
    }
    /**
     * Retrieves the PriceList2 property.
     *
     * @return
     *  BigDecimal containing the PriceList2 property.
     */
    public BigDecimal getPriceList2(){
        return mPriceList2;
    }


    
}
