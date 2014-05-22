
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        NscSiteView
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
 * <code>NscSiteView</code> is a ViewObject class for UI.
 */
public class NscSiteView
extends ValueObject
{
   
    private static final long serialVersionUID = -1L;
    private int mSiteId;
    private int mAccountId;
    private String mCustMaj;
    private String mSiteName;
    private String mStatus;
    private String mSiteReferenceNumber;
    private AddressData mAddress;
    private PropertyDataVector mProperties;

    /**
     * Constructor.
     */
    public NscSiteView ()
    {
        mCustMaj = "";
        mSiteName = "";
        mStatus = "";
        mSiteReferenceNumber = "";
    }

    /**
     * Constructor. 
     */
    public NscSiteView(int parm1, int parm2, String parm3, String parm4, String parm5, String parm6, AddressData parm7, PropertyDataVector parm8)
    {
        mSiteId = parm1;
        mAccountId = parm2;
        mCustMaj = parm3;
        mSiteName = parm4;
        mStatus = parm5;
        mSiteReferenceNumber = parm6;
        mAddress = parm7;
        mProperties = parm8;
        
    }

    /**
     * Creates a new NscSiteView
     *
     * @return
     *  Newly initialized NscSiteView object.
     */
    public static NscSiteView createValue () 
    {
        NscSiteView valueView = new NscSiteView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this NscSiteView object
     */
    public String toString()
    {
        return "[" + "SiteId=" + mSiteId + ", AccountId=" + mAccountId + ", CustMaj=" + mCustMaj + ", SiteName=" + mSiteName + ", Status=" + mStatus + ", SiteReferenceNumber=" + mSiteReferenceNumber + ", Address=" + mAddress + ", Properties=" + mProperties + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("NscSite");
	root.setAttribute("Id", String.valueOf(mSiteId));

	Element node;

        node = doc.createElement("AccountId");
        node.appendChild(doc.createTextNode(String.valueOf(mAccountId)));
        root.appendChild(node);

        node = doc.createElement("CustMaj");
        node.appendChild(doc.createTextNode(String.valueOf(mCustMaj)));
        root.appendChild(node);

        node = doc.createElement("SiteName");
        node.appendChild(doc.createTextNode(String.valueOf(mSiteName)));
        root.appendChild(node);

        node = doc.createElement("Status");
        node.appendChild(doc.createTextNode(String.valueOf(mStatus)));
        root.appendChild(node);

        node = doc.createElement("SiteReferenceNumber");
        node.appendChild(doc.createTextNode(String.valueOf(mSiteReferenceNumber)));
        root.appendChild(node);

        node = doc.createElement("Address");
        node.appendChild(doc.createTextNode(String.valueOf(mAddress)));
        root.appendChild(node);

        node = doc.createElement("Properties");
        node.appendChild(doc.createTextNode(String.valueOf(mProperties)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public NscSiteView copy()  {
      NscSiteView obj = new NscSiteView();
      obj.setSiteId(mSiteId);
      obj.setAccountId(mAccountId);
      obj.setCustMaj(mCustMaj);
      obj.setSiteName(mSiteName);
      obj.setStatus(mStatus);
      obj.setSiteReferenceNumber(mSiteReferenceNumber);
      obj.setAddress(mAddress);
      obj.setProperties(mProperties);
      
      return obj;
    }

    
    /**
     * Sets the SiteId property.
     *
     * @param pSiteId
     *  int to use to update the property.
     */
    public void setSiteId(int pSiteId){
        this.mSiteId = pSiteId;
    }
    /**
     * Retrieves the SiteId property.
     *
     * @return
     *  int containing the SiteId property.
     */
    public int getSiteId(){
        return mSiteId;
    }


    /**
     * Sets the AccountId property.
     *
     * @param pAccountId
     *  int to use to update the property.
     */
    public void setAccountId(int pAccountId){
        this.mAccountId = pAccountId;
    }
    /**
     * Retrieves the AccountId property.
     *
     * @return
     *  int containing the AccountId property.
     */
    public int getAccountId(){
        return mAccountId;
    }


    /**
     * Sets the CustMaj property.
     *
     * @param pCustMaj
     *  String to use to update the property.
     */
    public void setCustMaj(String pCustMaj){
        this.mCustMaj = pCustMaj;
    }
    /**
     * Retrieves the CustMaj property.
     *
     * @return
     *  String containing the CustMaj property.
     */
    public String getCustMaj(){
        return mCustMaj;
    }


    /**
     * Sets the SiteName property.
     *
     * @param pSiteName
     *  String to use to update the property.
     */
    public void setSiteName(String pSiteName){
        this.mSiteName = pSiteName;
    }
    /**
     * Retrieves the SiteName property.
     *
     * @return
     *  String containing the SiteName property.
     */
    public String getSiteName(){
        return mSiteName;
    }


    /**
     * Sets the Status property.
     *
     * @param pStatus
     *  String to use to update the property.
     */
    public void setStatus(String pStatus){
        this.mStatus = pStatus;
    }
    /**
     * Retrieves the Status property.
     *
     * @return
     *  String containing the Status property.
     */
    public String getStatus(){
        return mStatus;
    }


    /**
     * Sets the SiteReferenceNumber property.
     *
     * @param pSiteReferenceNumber
     *  String to use to update the property.
     */
    public void setSiteReferenceNumber(String pSiteReferenceNumber){
        this.mSiteReferenceNumber = pSiteReferenceNumber;
    }
    /**
     * Retrieves the SiteReferenceNumber property.
     *
     * @return
     *  String containing the SiteReferenceNumber property.
     */
    public String getSiteReferenceNumber(){
        return mSiteReferenceNumber;
    }


    /**
     * Sets the Address property.
     *
     * @param pAddress
     *  AddressData to use to update the property.
     */
    public void setAddress(AddressData pAddress){
        this.mAddress = pAddress;
    }
    /**
     * Retrieves the Address property.
     *
     * @return
     *  AddressData containing the Address property.
     */
    public AddressData getAddress(){
        return mAddress;
    }


    /**
     * Sets the Properties property.
     *
     * @param pProperties
     *  PropertyDataVector to use to update the property.
     */
    public void setProperties(PropertyDataVector pProperties){
        this.mProperties = pProperties;
    }
    /**
     * Retrieves the Properties property.
     *
     * @return
     *  PropertyDataVector containing the Properties property.
     */
    public PropertyDataVector getProperties(){
        return mProperties;
    }


    
}
