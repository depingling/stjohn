
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ProductView
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
 * <code>ProductView</code> is a ViewObject class for UI.
 */
public class ProductView
extends ValueObject
{
   
    private static final long serialVersionUID = -1L;
    private int mProductId;
    private ProductSkuView mProductSku;
    private ProductPriceView mProductPrice;

    /**
     * Constructor.
     */
    public ProductView ()
    {
    }

    /**
     * Constructor. 
     */
    public ProductView(int parm1, ProductSkuView parm2, ProductPriceView parm3)
    {
        mProductId = parm1;
        mProductSku = parm2;
        mProductPrice = parm3;
        
    }

    /**
     * Creates a new ProductView
     *
     * @return
     *  Newly initialized ProductView object.
     */
    public static ProductView createValue () 
    {
        ProductView valueView = new ProductView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ProductView object
     */
    public String toString()
    {
        return "[" + "ProductId=" + mProductId + ", ProductSku=" + mProductSku + ", ProductPrice=" + mProductPrice + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("Product");
	root.setAttribute("Id", String.valueOf(mProductId));

	Element node;

        node = doc.createElement("ProductSku");
        node.appendChild(doc.createTextNode(String.valueOf(mProductSku)));
        root.appendChild(node);

        node = doc.createElement("ProductPrice");
        node.appendChild(doc.createTextNode(String.valueOf(mProductPrice)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public ProductView copy()  {
      ProductView obj = new ProductView();
      obj.setProductId(mProductId);
      obj.setProductSku(mProductSku);
      obj.setProductPrice(mProductPrice);
      
      return obj;
    }

    
    /**
     * Sets the ProductId property.
     *
     * @param pProductId
     *  int to use to update the property.
     */
    public void setProductId(int pProductId){
        this.mProductId = pProductId;
    }
    /**
     * Retrieves the ProductId property.
     *
     * @return
     *  int containing the ProductId property.
     */
    public int getProductId(){
        return mProductId;
    }


    /**
     * Sets the ProductSku property.
     *
     * @param pProductSku
     *  ProductSkuView to use to update the property.
     */
    public void setProductSku(ProductSkuView pProductSku){
        this.mProductSku = pProductSku;
    }
    /**
     * Retrieves the ProductSku property.
     *
     * @return
     *  ProductSkuView containing the ProductSku property.
     */
    public ProductSkuView getProductSku(){
        return mProductSku;
    }


    /**
     * Sets the ProductPrice property.
     *
     * @param pProductPrice
     *  ProductPriceView to use to update the property.
     */
    public void setProductPrice(ProductPriceView pProductPrice){
        this.mProductPrice = pProductPrice;
    }
    /**
     * Retrieves the ProductPrice property.
     *
     * @return
     *  ProductPriceView containing the ProductPrice property.
     */
    public ProductPriceView getProductPrice(){
        return mProductPrice;
    }


    
}
