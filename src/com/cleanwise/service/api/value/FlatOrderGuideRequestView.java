
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        FlatOrderGuideRequestView
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
 * <code>FlatOrderGuideRequestView</code> is a ViewObject class for UI.
 */
public class FlatOrderGuideRequestView
extends ValueObject
{
   
    private static final long serialVersionUID = 2721260216016884901L;
    private String mSiteRefNumber;
    private String mDistSkuNumber;
    private int mQuantity;
    private java.util.Date mReleasedDate;
    private String mUserName;
    private String mOrderGuideName;

    /**
     * Constructor.
     */
    public FlatOrderGuideRequestView ()
    {
        mSiteRefNumber = "";
        mDistSkuNumber = "";
        mUserName = "";
        mOrderGuideName = "";
    }

    /**
     * Constructor. 
     */
    public FlatOrderGuideRequestView(String parm1, String parm2, int parm3, java.util.Date parm4, String parm5, String parm6)
    {
        mSiteRefNumber = parm1;
        mDistSkuNumber = parm2;
        mQuantity = parm3;
        mReleasedDate = parm4;
        mUserName = parm5;
        mOrderGuideName = parm6;
        
    }

    /**
     * Creates a new FlatOrderGuideRequestView
     *
     * @return
     *  Newly initialized FlatOrderGuideRequestView object.
     */
    public static FlatOrderGuideRequestView createValue () 
    {
        FlatOrderGuideRequestView valueView = new FlatOrderGuideRequestView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this FlatOrderGuideRequestView object
     */
    public String toString()
    {
        return "[" + "SiteRefNumber=" + mSiteRefNumber + ", DistSkuNumber=" + mDistSkuNumber + ", Quantity=" + mQuantity + ", ReleasedDate=" + mReleasedDate + ", UserName=" + mUserName + ", OrderGuideName=" + mOrderGuideName + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("FlatOrderGuideRequest");
	root.setAttribute("Id", String.valueOf(mSiteRefNumber));

	Element node;

        node = doc.createElement("DistSkuNumber");
        node.appendChild(doc.createTextNode(String.valueOf(mDistSkuNumber)));
        root.appendChild(node);

        node = doc.createElement("Quantity");
        node.appendChild(doc.createTextNode(String.valueOf(mQuantity)));
        root.appendChild(node);

        node = doc.createElement("ReleasedDate");
        node.appendChild(doc.createTextNode(String.valueOf(mReleasedDate)));
        root.appendChild(node);

        node = doc.createElement("UserName");
        node.appendChild(doc.createTextNode(String.valueOf(mUserName)));
        root.appendChild(node);

        node = doc.createElement("OrderGuideName");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderGuideName)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public FlatOrderGuideRequestView copy()  {
      FlatOrderGuideRequestView obj = new FlatOrderGuideRequestView();
      obj.setSiteRefNumber(mSiteRefNumber);
      obj.setDistSkuNumber(mDistSkuNumber);
      obj.setQuantity(mQuantity);
      obj.setReleasedDate(mReleasedDate);
      obj.setUserName(mUserName);
      obj.setOrderGuideName(mOrderGuideName);
      
      return obj;
    }

    
    /**
     * Sets the SiteRefNumber property.
     *
     * @param pSiteRefNumber
     *  String to use to update the property.
     */
    public void setSiteRefNumber(String pSiteRefNumber){
        this.mSiteRefNumber = pSiteRefNumber;
    }
    /**
     * Retrieves the SiteRefNumber property.
     *
     * @return
     *  String containing the SiteRefNumber property.
     */
    public String getSiteRefNumber(){
        return mSiteRefNumber;
    }


    /**
     * Sets the DistSkuNumber property.
     *
     * @param pDistSkuNumber
     *  String to use to update the property.
     */
    public void setDistSkuNumber(String pDistSkuNumber){
        this.mDistSkuNumber = pDistSkuNumber;
    }
    /**
     * Retrieves the DistSkuNumber property.
     *
     * @return
     *  String containing the DistSkuNumber property.
     */
    public String getDistSkuNumber(){
        return mDistSkuNumber;
    }


    /**
     * Sets the Quantity property.
     *
     * @param pQuantity
     *  int to use to update the property.
     */
    public void setQuantity(int pQuantity){
        this.mQuantity = pQuantity;
    }
    /**
     * Retrieves the Quantity property.
     *
     * @return
     *  int containing the Quantity property.
     */
    public int getQuantity(){
        return mQuantity;
    }


    /**
     * Sets the ReleasedDate property.
     *
     * @param pReleasedDate
     *  java.util.Date to use to update the property.
     */
    public void setReleasedDate(java.util.Date pReleasedDate){
        this.mReleasedDate = pReleasedDate;
    }
    /**
     * Retrieves the ReleasedDate property.
     *
     * @return
     *  java.util.Date containing the ReleasedDate property.
     */
    public java.util.Date getReleasedDate(){
        return mReleasedDate;
    }


    /**
     * Sets the UserName property.
     *
     * @param pUserName
     *  String to use to update the property.
     */
    public void setUserName(String pUserName){
        this.mUserName = pUserName;
    }
    /**
     * Retrieves the UserName property.
     *
     * @return
     *  String containing the UserName property.
     */
    public String getUserName(){
        return mUserName;
    }


    /**
     * Sets the OrderGuideName property.
     *
     * @param pOrderGuideName
     *  String to use to update the property.
     */
    public void setOrderGuideName(String pOrderGuideName){
        this.mOrderGuideName = pOrderGuideName;
    }
    /**
     * Retrieves the OrderGuideName property.
     *
     * @return
     *  String containing the OrderGuideName property.
     */
    public String getOrderGuideName(){
        return mOrderGuideName;
    }


    
}
