
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        EdiInp856ItemView
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
 * <code>EdiInp856ItemView</code> is a ViewObject class for UI.
 */
public class EdiInp856ItemView
extends ValueObject
{
   
    private static final long serialVersionUID = -3233587239925774055L;
    private int mPurchOrderLineNum;
    private String mDistSkuNum;
    private int mShippedQty;
    private String mUom;
    private int mOrderItemId;
    private String mSkuNum;
    private List mTrackingNumList;
    private String mDeliveryReference;

    /**
     * Constructor.
     */
    public EdiInp856ItemView ()
    {
        mDistSkuNum = "";
        mUom = "";
        mSkuNum = "";
    }

    /**
     * Constructor. 
     */
    public EdiInp856ItemView(int parm1, String parm2, int parm3, String parm4, int parm5, String parm6, List parm7)
    {
        mPurchOrderLineNum = parm1;
        mDistSkuNum = parm2;
        mShippedQty = parm3;
        mUom = parm4;
        mOrderItemId = parm5;
        mSkuNum = parm6;
        mTrackingNumList = parm7;
        
    }

    /**
     * Creates a new EdiInp856ItemView
     *
     * @return
     *  Newly initialized EdiInp856ItemView object.
     */
    public static EdiInp856ItemView createValue () 
    {
        EdiInp856ItemView valueView = new EdiInp856ItemView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this EdiInp856ItemView object
     */
    public String toString()
    {
        return "[" + "PurchOrderLineNum=" + mPurchOrderLineNum + ", DistSkuNum=" + mDistSkuNum + ", ShippedQty=" + mShippedQty + ", Uom=" + mUom + ", OrderItemId=" + mOrderItemId + ", SkuNum=" + mSkuNum + ", TrackingNumList=" + mTrackingNumList + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("EdiInp856Item");
	root.setAttribute("Id", String.valueOf(mPurchOrderLineNum));

	Element node;

        node = doc.createElement("DistSkuNum");
        node.appendChild(doc.createTextNode(String.valueOf(mDistSkuNum)));
        root.appendChild(node);

        node = doc.createElement("ShippedQty");
        node.appendChild(doc.createTextNode(String.valueOf(mShippedQty)));
        root.appendChild(node);

        node = doc.createElement("Uom");
        node.appendChild(doc.createTextNode(String.valueOf(mUom)));
        root.appendChild(node);

        node = doc.createElement("OrderItemId");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderItemId)));
        root.appendChild(node);

        node = doc.createElement("SkuNum");
        node.appendChild(doc.createTextNode(String.valueOf(mSkuNum)));
        root.appendChild(node);

        node = doc.createElement("TrackingNumList");
        node.appendChild(doc.createTextNode(String.valueOf(mTrackingNumList)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public EdiInp856ItemView copy()  {
      EdiInp856ItemView obj = new EdiInp856ItemView();
      obj.setPurchOrderLineNum(mPurchOrderLineNum);
      obj.setDistSkuNum(mDistSkuNum);
      obj.setShippedQty(mShippedQty);
      obj.setUom(mUom);
      obj.setOrderItemId(mOrderItemId);
      obj.setSkuNum(mSkuNum);
      obj.setTrackingNumList(mTrackingNumList);
      
      return obj;
    }

    
    /**
     * Sets the PurchOrderLineNum property.
     *
     * @param pPurchOrderLineNum
     *  int to use to update the property.
     */
    public void setPurchOrderLineNum(int pPurchOrderLineNum){
        this.mPurchOrderLineNum = pPurchOrderLineNum;
    }
    /**
     * Retrieves the PurchOrderLineNum property.
     *
     * @return
     *  int containing the PurchOrderLineNum property.
     */
    public int getPurchOrderLineNum(){
        return mPurchOrderLineNum;
    }


    /**
     * Sets the DistSkuNum property.
     *
     * @param pDistSkuNum
     *  String to use to update the property.
     */
    public void setDistSkuNum(String pDistSkuNum){
        this.mDistSkuNum = pDistSkuNum;
    }
    /**
     * Retrieves the DistSkuNum property.
     *
     * @return
     *  String containing the DistSkuNum property.
     */
    public String getDistSkuNum(){
        return mDistSkuNum;
    }


    /**
     * Sets the ShippedQty property.
     *
     * @param pShippedQty
     *  int to use to update the property.
     */
    public void setShippedQty(int pShippedQty){
        this.mShippedQty = pShippedQty;
    }
    /**
     * Retrieves the ShippedQty property.
     *
     * @return
     *  int containing the ShippedQty property.
     */
    public int getShippedQty(){
        return mShippedQty;
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
     * Sets the OrderItemId property.
     *
     * @param pOrderItemId
     *  int to use to update the property.
     */
    public void setOrderItemId(int pOrderItemId){
        this.mOrderItemId = pOrderItemId;
    }
    /**
     * Retrieves the OrderItemId property.
     *
     * @return
     *  int containing the OrderItemId property.
     */
    public int getOrderItemId(){
        return mOrderItemId;
    }


    /**
     * Sets the SkuNum property.
     *
     * @param pSkuNum
     *  String to use to update the property.
     */
    public void setSkuNum(String pSkuNum){
        this.mSkuNum = pSkuNum;
    }
    /**
     * Retrieves the SkuNum property.
     *
     * @return
     *  String containing the SkuNum property.
     */
    public String getSkuNum(){
        return mSkuNum;
    }


    /**
     * Sets the TrackingNumList property.
     *
     * @param pTrackingNumList
     *  List to use to update the property.
     */
    public void setTrackingNumList(List pTrackingNumList){
        this.mTrackingNumList = pTrackingNumList;
    }
    /**
     * Retrieves the TrackingNumList property.
     *
     * @return
     *  List containing the TrackingNumList property.
     */
    public List getTrackingNumList(){
        return mTrackingNumList;
    }

		/**
		 * @return the mDeliveryReference
		 */
		public String getDeliveryReference() {
			return mDeliveryReference;
		}

		/**
		 * @param deliveryReference the mDeliveryReference to set
		 */
		public void setDeliveryReference(String deliveryReference) {
			mDeliveryReference = deliveryReference;
		}

    
    
}
