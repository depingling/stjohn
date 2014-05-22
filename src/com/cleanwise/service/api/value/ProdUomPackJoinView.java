
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ProdUomPackJoinView
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
 * <code>ProdUomPackJoinView</code> is a ViewObject class for UI.
 */
public class ProdUomPackJoinView
extends ValueObject
{
   
    private static final long serialVersionUID = 6649941251861653174L;
    private ProductUomPackData mProductUomPack;
    private OrderGuideItemDescData mOrderGuideItem;
    private String mUnitSizeInp;
    private String mUnitCdInp;
    private String mPackQtyInp;

    /**
     * Constructor.
     */
    public ProdUomPackJoinView ()
    {
        mUnitSizeInp = "";
        mUnitCdInp = "";
        mPackQtyInp = "";
    }

    /**
     * Constructor. 
     */
    public ProdUomPackJoinView(ProductUomPackData parm1, OrderGuideItemDescData parm2, String parm3, String parm4, String parm5)
    {
        mProductUomPack = parm1;
        mOrderGuideItem = parm2;
        mUnitSizeInp = parm3;
        mUnitCdInp = parm4;
        mPackQtyInp = parm5;
        
    }

    /**
     * Creates a new ProdUomPackJoinView
     *
     * @return
     *  Newly initialized ProdUomPackJoinView object.
     */
    public static ProdUomPackJoinView createValue () 
    {
        ProdUomPackJoinView valueView = new ProdUomPackJoinView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ProdUomPackJoinView object
     */
    public String toString()
    {
        return "[" + "ProductUomPack=" + mProductUomPack + ", OrderGuideItem=" + mOrderGuideItem + ", UnitSizeInp=" + mUnitSizeInp + ", UnitCdInp=" + mUnitCdInp + ", PackQtyInp=" + mPackQtyInp + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("ProdUomPackJoin");
	root.setAttribute("Id", String.valueOf(mProductUomPack));

	Element node;

        node = doc.createElement("OrderGuideItem");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderGuideItem)));
        root.appendChild(node);

        node = doc.createElement("UnitSizeInp");
        node.appendChild(doc.createTextNode(String.valueOf(mUnitSizeInp)));
        root.appendChild(node);

        node = doc.createElement("UnitCdInp");
        node.appendChild(doc.createTextNode(String.valueOf(mUnitCdInp)));
        root.appendChild(node);

        node = doc.createElement("PackQtyInp");
        node.appendChild(doc.createTextNode(String.valueOf(mPackQtyInp)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public ProdUomPackJoinView copy()  {
      ProdUomPackJoinView obj = new ProdUomPackJoinView();
      obj.setProductUomPack(mProductUomPack);
      obj.setOrderGuideItem(mOrderGuideItem);
      obj.setUnitSizeInp(mUnitSizeInp);
      obj.setUnitCdInp(mUnitCdInp);
      obj.setPackQtyInp(mPackQtyInp);
      
      return obj;
    }

    
    /**
     * Sets the ProductUomPack property.
     *
     * @param pProductUomPack
     *  ProductUomPackData to use to update the property.
     */
    public void setProductUomPack(ProductUomPackData pProductUomPack){
        this.mProductUomPack = pProductUomPack;
    }
    /**
     * Retrieves the ProductUomPack property.
     *
     * @return
     *  ProductUomPackData containing the ProductUomPack property.
     */
    public ProductUomPackData getProductUomPack(){
        return mProductUomPack;
    }


    /**
     * Sets the OrderGuideItem property.
     *
     * @param pOrderGuideItem
     *  OrderGuideItemDescData to use to update the property.
     */
    public void setOrderGuideItem(OrderGuideItemDescData pOrderGuideItem){
        this.mOrderGuideItem = pOrderGuideItem;
    }
    /**
     * Retrieves the OrderGuideItem property.
     *
     * @return
     *  OrderGuideItemDescData containing the OrderGuideItem property.
     */
    public OrderGuideItemDescData getOrderGuideItem(){
        return mOrderGuideItem;
    }


    /**
     * Sets the UnitSizeInp property.
     *
     * @param pUnitSizeInp
     *  String to use to update the property.
     */
    public void setUnitSizeInp(String pUnitSizeInp){
        this.mUnitSizeInp = pUnitSizeInp;
    }
    /**
     * Retrieves the UnitSizeInp property.
     *
     * @return
     *  String containing the UnitSizeInp property.
     */
    public String getUnitSizeInp(){
        return mUnitSizeInp;
    }


    /**
     * Sets the UnitCdInp property.
     *
     * @param pUnitCdInp
     *  String to use to update the property.
     */
    public void setUnitCdInp(String pUnitCdInp){
        this.mUnitCdInp = pUnitCdInp;
    }
    /**
     * Retrieves the UnitCdInp property.
     *
     * @return
     *  String containing the UnitCdInp property.
     */
    public String getUnitCdInp(){
        return mUnitCdInp;
    }


    /**
     * Sets the PackQtyInp property.
     *
     * @param pPackQtyInp
     *  String to use to update the property.
     */
    public void setPackQtyInp(String pPackQtyInp){
        this.mPackQtyInp = pPackQtyInp;
    }
    /**
     * Retrieves the PackQtyInp property.
     *
     * @return
     *  String containing the PackQtyInp property.
     */
    public String getPackQtyInp(){
        return mPackQtyInp;
    }


    
}
