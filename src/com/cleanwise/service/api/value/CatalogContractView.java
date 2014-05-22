
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        CatalogContractView
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
 * <code>CatalogContractView</code> is a ViewObject class for UI.
 */
public class CatalogContractView
extends ValueObject
{
   
    private static final long serialVersionUID = 6717857350160127031L;
    private CatalogData mCatalog;
    private ContractData mContract;
    private OrderGuideDataVector mOrderGuides;

    /**
     * Constructor.
     */
    public CatalogContractView ()
    {
    }

    /**
     * Constructor. 
     */
    public CatalogContractView(CatalogData parm1, ContractData parm2, OrderGuideDataVector parm3)
    {
        mCatalog = parm1;
        mContract = parm2;
        mOrderGuides = parm3;
        
    }

    /**
     * Creates a new CatalogContractView
     *
     * @return
     *  Newly initialized CatalogContractView object.
     */
    public static CatalogContractView createValue () 
    {
        CatalogContractView valueView = new CatalogContractView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this CatalogContractView object
     */
    public String toString()
    {
        return "[" + "Catalog=" + mCatalog + ", Contract=" + mContract + ", OrderGuides=" + mOrderGuides + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("CatalogContract");
	root.setAttribute("Id", String.valueOf(mCatalog));

	Element node;

        node = doc.createElement("Contract");
        node.appendChild(doc.createTextNode(String.valueOf(mContract)));
        root.appendChild(node);

        node = doc.createElement("OrderGuides");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderGuides)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public CatalogContractView copy()  {
      CatalogContractView obj = new CatalogContractView();
      obj.setCatalog(mCatalog);
      obj.setContract(mContract);
      obj.setOrderGuides(mOrderGuides);
      
      return obj;
    }

    
    /**
     * Sets the Catalog property.
     *
     * @param pCatalog
     *  CatalogData to use to update the property.
     */
    public void setCatalog(CatalogData pCatalog){
        this.mCatalog = pCatalog;
    }
    /**
     * Retrieves the Catalog property.
     *
     * @return
     *  CatalogData containing the Catalog property.
     */
    public CatalogData getCatalog(){
        return mCatalog;
    }


    /**
     * Sets the Contract property.
     *
     * @param pContract
     *  ContractData to use to update the property.
     */
    public void setContract(ContractData pContract){
        this.mContract = pContract;
    }
    /**
     * Retrieves the Contract property.
     *
     * @return
     *  ContractData containing the Contract property.
     */
    public ContractData getContract(){
        return mContract;
    }


    /**
     * Sets the OrderGuides property.
     *
     * @param pOrderGuides
     *  OrderGuideDataVector to use to update the property.
     */
    public void setOrderGuides(OrderGuideDataVector pOrderGuides){
        this.mOrderGuides = pOrderGuides;
    }
    /**
     * Retrieves the OrderGuides property.
     *
     * @return
     *  OrderGuideDataVector containing the OrderGuides property.
     */
    public OrderGuideDataVector getOrderGuides(){
        return mOrderGuides;
    }


    
}
