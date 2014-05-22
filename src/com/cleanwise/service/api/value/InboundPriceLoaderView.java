
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        InboundPriceLoaderView
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
 * <code>InboundPriceLoaderView</code> is a ViewObject class for UI.
 */
public class InboundPriceLoaderView
extends ValueObject
{
   
    private static final long serialVersionUID = -1L;
    private String mVersionNum;
    private String mStoreId;
    private String mStoreName;
    private String mCatalogKey;
    private String mDistSku;
    private String mDistributor;
    private String mUom;
    private String mCost;
    private String mPrice;

    /**
     * Constructor.
     */
    public InboundPriceLoaderView ()
    {
        mVersionNum = "";
        mStoreId = "";
        mStoreName = "";
        mCatalogKey = "";
        mDistSku = "";
        mDistributor = "";
        mUom = "";
        mCost = "";
        mPrice = "";
    }

    /**
     * Constructor. 
     */
    public InboundPriceLoaderView(String parm1, String parm2, String parm3, String parm4, String parm5, String parm6, String parm7, String parm8, String parm9)
    {
        mVersionNum = parm1;
        mStoreId = parm2;
        mStoreName = parm3;
        mCatalogKey = parm4;
        mDistSku = parm5;
        mDistributor = parm6;
        mUom = parm7;
        mCost = parm8;
        mPrice = parm9;
        
    }

    /**
     * Creates a new InboundPriceLoaderView
     *
     * @return
     *  Newly initialized InboundPriceLoaderView object.
     */
    public static InboundPriceLoaderView createValue () 
    {
        InboundPriceLoaderView valueView = new InboundPriceLoaderView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this InboundPriceLoaderView object
     */
    public String toString()
    {
        return "[" + "VersionNum=" + mVersionNum + ", StoreId=" + mStoreId + ", StoreName=" + mStoreName + ", CatalogKey=" + mCatalogKey + ", DistSku=" + mDistSku + ", Distributor=" + mDistributor + ", Uom=" + mUom + ", Cost=" + mCost + ", Price=" + mPrice + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("InboundPriceLoader");
	root.setAttribute("Id", String.valueOf(mVersionNum));

	Element node;

        node = doc.createElement("StoreId");
        node.appendChild(doc.createTextNode(String.valueOf(mStoreId)));
        root.appendChild(node);

        node = doc.createElement("StoreName");
        node.appendChild(doc.createTextNode(String.valueOf(mStoreName)));
        root.appendChild(node);

        node = doc.createElement("CatalogKey");
        node.appendChild(doc.createTextNode(String.valueOf(mCatalogKey)));
        root.appendChild(node);

        node = doc.createElement("DistSku");
        node.appendChild(doc.createTextNode(String.valueOf(mDistSku)));
        root.appendChild(node);

        node = doc.createElement("Distributor");
        node.appendChild(doc.createTextNode(String.valueOf(mDistributor)));
        root.appendChild(node);

        node = doc.createElement("Uom");
        node.appendChild(doc.createTextNode(String.valueOf(mUom)));
        root.appendChild(node);

        node = doc.createElement("Cost");
        node.appendChild(doc.createTextNode(String.valueOf(mCost)));
        root.appendChild(node);

        node = doc.createElement("Price");
        node.appendChild(doc.createTextNode(String.valueOf(mPrice)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public InboundPriceLoaderView copy()  {
      InboundPriceLoaderView obj = new InboundPriceLoaderView();
      obj.setVersionNum(mVersionNum);
      obj.setStoreId(mStoreId);
      obj.setStoreName(mStoreName);
      obj.setCatalogKey(mCatalogKey);
      obj.setDistSku(mDistSku);
      obj.setDistributor(mDistributor);
      obj.setUom(mUom);
      obj.setCost(mCost);
      obj.setPrice(mPrice);
      
      return obj;
    }

    
    /**
     * Sets the VersionNum property.
     *
     * @param pVersionNum
     *  String to use to update the property.
     */
    public void setVersionNum(String pVersionNum){
        this.mVersionNum = pVersionNum;
    }
    /**
     * Retrieves the VersionNum property.
     *
     * @return
     *  String containing the VersionNum property.
     */
    public String getVersionNum(){
        return mVersionNum;
    }


    /**
     * Sets the StoreId property.
     *
     * @param pStoreId
     *  String to use to update the property.
     */
    public void setStoreId(String pStoreId){
        this.mStoreId = pStoreId;
    }
    /**
     * Retrieves the StoreId property.
     *
     * @return
     *  String containing the StoreId property.
     */
    public String getStoreId(){
        return mStoreId;
    }


    /**
     * Sets the StoreName property.
     *
     * @param pStoreName
     *  String to use to update the property.
     */
    public void setStoreName(String pStoreName){
        this.mStoreName = pStoreName;
    }
    /**
     * Retrieves the StoreName property.
     *
     * @return
     *  String containing the StoreName property.
     */
    public String getStoreName(){
        return mStoreName;
    }


    /**
     * Sets the CatalogKey property.
     *
     * @param pCatalogKey
     *  String to use to update the property.
     */
    public void setCatalogKey(String pCatalogKey){
        this.mCatalogKey = pCatalogKey;
    }
    /**
     * Retrieves the CatalogKey property.
     *
     * @return
     *  String containing the CatalogKey property.
     */
    public String getCatalogKey(){
        return mCatalogKey;
    }


    /**
     * Sets the DistSku property.
     *
     * @param pDistSku
     *  String to use to update the property.
     */
    public void setDistSku(String pDistSku){
        this.mDistSku = pDistSku;
    }
    /**
     * Retrieves the DistSku property.
     *
     * @return
     *  String containing the DistSku property.
     */
    public String getDistSku(){
        return mDistSku;
    }


    /**
     * Sets the Distributor property.
     *
     * @param pDistributor
     *  String to use to update the property.
     */
    public void setDistributor(String pDistributor){
        this.mDistributor = pDistributor;
    }
    /**
     * Retrieves the Distributor property.
     *
     * @return
     *  String containing the Distributor property.
     */
    public String getDistributor(){
        return mDistributor;
    }


    /**
     * Sets the Uom property.
     *
     * @param pUom
     *  String to use to update the property.
     */
    public void setUom(String pUom){
        this.mUom = pUom;
    }
    /**
     * Retrieves the Uom property.
     *
     * @return
     *  String containing the Uom property.
     */
    public String getUom(){
        return mUom;
    }


    /**
     * Sets the Cost property.
     *
     * @param pCost
     *  String to use to update the property.
     */
    public void setCost(String pCost){
        this.mCost = pCost;
    }
    /**
     * Retrieves the Cost property.
     *
     * @return
     *  String containing the Cost property.
     */
    public String getCost(){
        return mCost;
    }


    /**
     * Sets the Price property.
     *
     * @param pPrice
     *  String to use to update the property.
     */
    public void setPrice(String pPrice){
        this.mPrice = pPrice;
    }
    /**
     * Retrieves the Price property.
     *
     * @return
     *  String containing the Price property.
     */
    public String getPrice(){
        return mPrice;
    }


    
}
