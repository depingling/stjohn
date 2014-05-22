
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        SitePriceListView
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
 * <code>SitePriceListView</code> is a ViewObject class for UI.
 */
public class SitePriceListView
extends ValueObject
{
   
    private static final long serialVersionUID = -1L;
    private PriceListData mPriceList1;
    private PriceListData mPriceList2;
    private PriceListDataVector mProprietaryLists;

    /**
     * Constructor.
     */
    public SitePriceListView ()
    {
    }

    /**
     * Constructor. 
     */
    public SitePriceListView(PriceListData parm1, PriceListData parm2, PriceListDataVector parm3)
    {
        mPriceList1 = parm1;
        mPriceList2 = parm2;
        mProprietaryLists = parm3;
        
    }

    /**
     * Creates a new SitePriceListView
     *
     * @return
     *  Newly initialized SitePriceListView object.
     */
    public static SitePriceListView createValue () 
    {
        SitePriceListView valueView = new SitePriceListView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this SitePriceListView object
     */
    public String toString()
    {
        return "[" + "PriceList1=" + mPriceList1 + ", PriceList2=" + mPriceList2 + ", ProprietaryLists=" + mProprietaryLists + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("SitePriceList");
	root.setAttribute("Id", String.valueOf(mPriceList1));

	Element node;

        node = doc.createElement("PriceList2");
        node.appendChild(doc.createTextNode(String.valueOf(mPriceList2)));
        root.appendChild(node);

        node = doc.createElement("ProprietaryLists");
        node.appendChild(doc.createTextNode(String.valueOf(mProprietaryLists)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public SitePriceListView copy()  {
      SitePriceListView obj = new SitePriceListView();
      obj.setPriceList1(mPriceList1);
      obj.setPriceList2(mPriceList2);
      obj.setProprietaryLists(mProprietaryLists);
      
      return obj;
    }

    
    /**
     * Sets the PriceList1 property.
     *
     * @param pPriceList1
     *  PriceListData to use to update the property.
     */
    public void setPriceList1(PriceListData pPriceList1){
        this.mPriceList1 = pPriceList1;
    }
    /**
     * Retrieves the PriceList1 property.
     *
     * @return
     *  PriceListData containing the PriceList1 property.
     */
    public PriceListData getPriceList1(){
        return mPriceList1;
    }


    /**
     * Sets the PriceList2 property.
     *
     * @param pPriceList2
     *  PriceListData to use to update the property.
     */
    public void setPriceList2(PriceListData pPriceList2){
        this.mPriceList2 = pPriceList2;
    }
    /**
     * Retrieves the PriceList2 property.
     *
     * @return
     *  PriceListData containing the PriceList2 property.
     */
    public PriceListData getPriceList2(){
        return mPriceList2;
    }


    /**
     * Sets the ProprietaryLists property.
     *
     * @param pProprietaryLists
     *  PriceListDataVector to use to update the property.
     */
    public void setProprietaryLists(PriceListDataVector pProprietaryLists){
        this.mProprietaryLists = pProprietaryLists;
    }
    /**
     * Retrieves the ProprietaryLists property.
     *
     * @return
     *  PriceListDataVector containing the ProprietaryLists property.
     */
    public PriceListDataVector getProprietaryLists(){
        return mProprietaryLists;
    }


    
}
