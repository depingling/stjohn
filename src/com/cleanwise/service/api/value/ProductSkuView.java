
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ProductSkuView
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
 * <code>ProductSkuView</code> is a ViewObject class for UI.
 */
public class ProductSkuView
extends ValueObject
{
   
    private static final long serialVersionUID = -1L;
    private String mCustomerSkuNum;
    private String mPriceListRank1SkuNum;
    private String mPriceListRank2SkuNum;
    private String mManufacturerSkuNum;
    private String mDistributorSkuNum;
    private String mStoreSkuNum;

    /**
     * Constructor.
     */
    public ProductSkuView ()
    {
        mCustomerSkuNum = "";
        mPriceListRank1SkuNum = "";
        mPriceListRank2SkuNum = "";
        mManufacturerSkuNum = "";
        mDistributorSkuNum = "";
        mStoreSkuNum = "";
    }

    /**
     * Constructor. 
     */
    public ProductSkuView(String parm1, String parm2, String parm3, String parm4, String parm5, String parm6)
    {
        mCustomerSkuNum = parm1;
        mPriceListRank1SkuNum = parm2;
        mPriceListRank2SkuNum = parm3;
        mManufacturerSkuNum = parm4;
        mDistributorSkuNum = parm5;
        mStoreSkuNum = parm6;
        
    }

    /**
     * Creates a new ProductSkuView
     *
     * @return
     *  Newly initialized ProductSkuView object.
     */
    public static ProductSkuView createValue () 
    {
        ProductSkuView valueView = new ProductSkuView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ProductSkuView object
     */
    public String toString()
    {
        return "[" + "CustomerSkuNum=" + mCustomerSkuNum + ", PriceListRank1SkuNum=" + mPriceListRank1SkuNum + ", PriceListRank2SkuNum=" + mPriceListRank2SkuNum + ", ManufacturerSkuNum=" + mManufacturerSkuNum + ", DistributorSkuNum=" + mDistributorSkuNum + ", StoreSkuNum=" + mStoreSkuNum + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("ProductSku");
	root.setAttribute("Id", String.valueOf(mCustomerSkuNum));

	Element node;

        node = doc.createElement("PriceListRank1SkuNum");
        node.appendChild(doc.createTextNode(String.valueOf(mPriceListRank1SkuNum)));
        root.appendChild(node);

        node = doc.createElement("PriceListRank2SkuNum");
        node.appendChild(doc.createTextNode(String.valueOf(mPriceListRank2SkuNum)));
        root.appendChild(node);

        node = doc.createElement("ManufacturerSkuNum");
        node.appendChild(doc.createTextNode(String.valueOf(mManufacturerSkuNum)));
        root.appendChild(node);

        node = doc.createElement("DistributorSkuNum");
        node.appendChild(doc.createTextNode(String.valueOf(mDistributorSkuNum)));
        root.appendChild(node);

        node = doc.createElement("StoreSkuNum");
        node.appendChild(doc.createTextNode(String.valueOf(mStoreSkuNum)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public ProductSkuView copy()  {
      ProductSkuView obj = new ProductSkuView();
      obj.setCustomerSkuNum(mCustomerSkuNum);
      obj.setPriceListRank1SkuNum(mPriceListRank1SkuNum);
      obj.setPriceListRank2SkuNum(mPriceListRank2SkuNum);
      obj.setManufacturerSkuNum(mManufacturerSkuNum);
      obj.setDistributorSkuNum(mDistributorSkuNum);
      obj.setStoreSkuNum(mStoreSkuNum);
      
      return obj;
    }

    
    /**
     * Sets the CustomerSkuNum property.
     *
     * @param pCustomerSkuNum
     *  String to use to update the property.
     */
    public void setCustomerSkuNum(String pCustomerSkuNum){
        this.mCustomerSkuNum = pCustomerSkuNum;
    }
    /**
     * Retrieves the CustomerSkuNum property.
     *
     * @return
     *  String containing the CustomerSkuNum property.
     */
    public String getCustomerSkuNum(){
        return mCustomerSkuNum;
    }


    /**
     * Sets the PriceListRank1SkuNum property.
     *
     * @param pPriceListRank1SkuNum
     *  String to use to update the property.
     */
    public void setPriceListRank1SkuNum(String pPriceListRank1SkuNum){
        this.mPriceListRank1SkuNum = pPriceListRank1SkuNum;
    }
    /**
     * Retrieves the PriceListRank1SkuNum property.
     *
     * @return
     *  String containing the PriceListRank1SkuNum property.
     */
    public String getPriceListRank1SkuNum(){
        return mPriceListRank1SkuNum;
    }


    /**
     * Sets the PriceListRank2SkuNum property.
     *
     * @param pPriceListRank2SkuNum
     *  String to use to update the property.
     */
    public void setPriceListRank2SkuNum(String pPriceListRank2SkuNum){
        this.mPriceListRank2SkuNum = pPriceListRank2SkuNum;
    }
    /**
     * Retrieves the PriceListRank2SkuNum property.
     *
     * @return
     *  String containing the PriceListRank2SkuNum property.
     */
    public String getPriceListRank2SkuNum(){
        return mPriceListRank2SkuNum;
    }


    /**
     * Sets the ManufacturerSkuNum property.
     *
     * @param pManufacturerSkuNum
     *  String to use to update the property.
     */
    public void setManufacturerSkuNum(String pManufacturerSkuNum){
        this.mManufacturerSkuNum = pManufacturerSkuNum;
    }
    /**
     * Retrieves the ManufacturerSkuNum property.
     *
     * @return
     *  String containing the ManufacturerSkuNum property.
     */
    public String getManufacturerSkuNum(){
        return mManufacturerSkuNum;
    }


    /**
     * Sets the DistributorSkuNum property.
     *
     * @param pDistributorSkuNum
     *  String to use to update the property.
     */
    public void setDistributorSkuNum(String pDistributorSkuNum){
        this.mDistributorSkuNum = pDistributorSkuNum;
    }
    /**
     * Retrieves the DistributorSkuNum property.
     *
     * @return
     *  String containing the DistributorSkuNum property.
     */
    public String getDistributorSkuNum(){
        return mDistributorSkuNum;
    }


    /**
     * Sets the StoreSkuNum property.
     *
     * @param pStoreSkuNum
     *  String to use to update the property.
     */
    public void setStoreSkuNum(String pStoreSkuNum){
        this.mStoreSkuNum = pStoreSkuNum;
    }
    /**
     * Retrieves the StoreSkuNum property.
     *
     * @return
     *  String containing the StoreSkuNum property.
     */
    public String getStoreSkuNum(){
        return mStoreSkuNum;
    }


    
}
