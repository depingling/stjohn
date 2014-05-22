
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        RealPurchaseOrderNumView
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

import com.cleanwise.service.api.value.PurchaseOrderData;


/**
 * <code>RealPurchaseOrderNumView</code> is a ViewObject class for UI.
 */
public class RealPurchaseOrderNumView
extends ValueObject
{
   
    private static final long serialVersionUID = -3934668628151051577L;
    private int mOrderId;
    private String mDistrPOType;
    private String mPONumValue;
    private String mDistributorName;
    private PurchaseOrderData mPurchaseOrder;

    /**
     * Constructor.
     */
    public RealPurchaseOrderNumView ()
    {
        mDistrPOType = "";
        mPONumValue = "";
        mDistributorName = "";
    }

    /**
     * Constructor. 
     */
    public RealPurchaseOrderNumView(int parm1, String parm2, String parm3, String parm4, PurchaseOrderData parm5)
    {
        mOrderId = parm1;
        mDistrPOType = parm2;
        mPONumValue = parm3;
        mDistributorName = parm4;
        mPurchaseOrder = parm5;
        
    }

    /**
     * Creates a new RealPurchaseOrderNumView
     *
     * @return
     *  Newly initialized RealPurchaseOrderNumView object.
     */
    public static RealPurchaseOrderNumView createValue () 
    {
        RealPurchaseOrderNumView valueView = new RealPurchaseOrderNumView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this RealPurchaseOrderNumView object
     */
    public String toString()
    {
        return "[" + "OrderId=" + mOrderId + ", DistrPOType=" + mDistrPOType + ", PONumValue=" + mPONumValue + ", DistributorName=" + mDistributorName + ", PurchaseOrder=" + mPurchaseOrder + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("RealPurchaseOrderNum");
	root.setAttribute("Id", String.valueOf(mOrderId));

	Element node;

        node = doc.createElement("DistrPOType");
        node.appendChild(doc.createTextNode(String.valueOf(mDistrPOType)));
        root.appendChild(node);

        node = doc.createElement("PONumValue");
        node.appendChild(doc.createTextNode(String.valueOf(mPONumValue)));
        root.appendChild(node);

        node = doc.createElement("DistributorName");
        node.appendChild(doc.createTextNode(String.valueOf(mDistributorName)));
        root.appendChild(node);

        node = doc.createElement("PurchaseOrder");
        node.appendChild(doc.createTextNode(String.valueOf(mPurchaseOrder)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public RealPurchaseOrderNumView copy()  {
      RealPurchaseOrderNumView obj = new RealPurchaseOrderNumView();
      obj.setOrderId(mOrderId);
      obj.setDistrPOType(mDistrPOType);
      obj.setPONumValue(mPONumValue);
      obj.setDistributorName(mDistributorName);
      obj.setPurchaseOrder(mPurchaseOrder);
      
      return obj;
    }

    
    /**
     * Sets the OrderId property.
     *
     * @param pOrderId
     *  int to use to update the property.
     */
    public void setOrderId(int pOrderId){
        this.mOrderId = pOrderId;
    }
    /**
     * Retrieves the OrderId property.
     *
     * @return
     *  int containing the OrderId property.
     */
    public int getOrderId(){
        return mOrderId;
    }


    /**
     * Sets the DistrPOType property.
     *
     * @param pDistrPOType
     *  String to use to update the property.
     */
    public void setDistrPOType(String pDistrPOType){
        this.mDistrPOType = pDistrPOType;
    }
    /**
     * Retrieves the DistrPOType property.
     *
     * @return
     *  String containing the DistrPOType property.
     */
    public String getDistrPOType(){
        return mDistrPOType;
    }


    /**
     * Sets the PONumValue property.
     *
     * @param pPONumValue
     *  String to use to update the property.
     */
    public void setPONumValue(String pPONumValue){
        this.mPONumValue = pPONumValue;
    }
    /**
     * Retrieves the PONumValue property.
     *
     * @return
     *  String containing the PONumValue property.
     */
    public String getPONumValue(){
        return mPONumValue;
    }


    /**
     * Sets the DistributorName property.
     *
     * @param pDistributorName
     *  String to use to update the property.
     */
    public void setDistributorName(String pDistributorName){
        this.mDistributorName = pDistributorName;
    }
    /**
     * Retrieves the DistributorName property.
     *
     * @return
     *  String containing the DistributorName property.
     */
    public String getDistributorName(){
        return mDistributorName;
    }


    /**
     * Sets the PurchaseOrder property.
     *
     * @param pPurchaseOrder
     *  PurchaseOrderData to use to update the property.
     */
    public void setPurchaseOrder(PurchaseOrderData pPurchaseOrder){
        this.mPurchaseOrder = pPurchaseOrder;
    }
    /**
     * Retrieves the PurchaseOrder property.
     *
     * @return
     *  PurchaseOrderData containing the PurchaseOrder property.
     */
    public PurchaseOrderData getPurchaseOrder(){
        return mPurchaseOrder;
    }


    
}
