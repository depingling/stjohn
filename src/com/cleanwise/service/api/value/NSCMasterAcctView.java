
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        NSCMasterAcctView
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
 * <code>NSCMasterAcctView</code> is a ViewObject class for UI.
 */
public class NSCMasterAcctView
extends ValueObject
{
   
    private static final long serialVersionUID = -1L;
    private String mCustMajOfUser;
    private String mUserCode;
    private String mCustMajOfSite;
    private String mSiteRefNumber;

    /**
     * Constructor.
     */
    public NSCMasterAcctView ()
    {
        mCustMajOfUser = "";
        mUserCode = "";
        mCustMajOfSite = "";
        mSiteRefNumber = "";
    }

    /**
     * Constructor. 
     */
    public NSCMasterAcctView(String parm1, String parm2, String parm3, String parm4)
    {
        mCustMajOfUser = parm1;
        mUserCode = parm2;
        mCustMajOfSite = parm3;
        mSiteRefNumber = parm4;
        
    }

    /**
     * Creates a new NSCMasterAcctView
     *
     * @return
     *  Newly initialized NSCMasterAcctView object.
     */
    public static NSCMasterAcctView createValue () 
    {
        NSCMasterAcctView valueView = new NSCMasterAcctView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this NSCMasterAcctView object
     */
    public String toString()
    {
        return "[" + "CustMajOfUser=" + mCustMajOfUser + ", UserCode=" + mUserCode + ", CustMajOfSite=" + mCustMajOfSite + ", SiteRefNumber=" + mSiteRefNumber + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("NSCMasterAcct");
	root.setAttribute("Id", String.valueOf(mCustMajOfUser));

	Element node;

        node = doc.createElement("UserCode");
        node.appendChild(doc.createTextNode(String.valueOf(mUserCode)));
        root.appendChild(node);

        node = doc.createElement("CustMajOfSite");
        node.appendChild(doc.createTextNode(String.valueOf(mCustMajOfSite)));
        root.appendChild(node);

        node = doc.createElement("SiteRefNumber");
        node.appendChild(doc.createTextNode(String.valueOf(mSiteRefNumber)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public NSCMasterAcctView copy()  {
      NSCMasterAcctView obj = new NSCMasterAcctView();
      obj.setCustMajOfUser(mCustMajOfUser);
      obj.setUserCode(mUserCode);
      obj.setCustMajOfSite(mCustMajOfSite);
      obj.setSiteRefNumber(mSiteRefNumber);
      
      return obj;
    }

    
    /**
     * Sets the CustMajOfUser property.
     *
     * @param pCustMajOfUser
     *  String to use to update the property.
     */
    public void setCustMajOfUser(String pCustMajOfUser){
        this.mCustMajOfUser = pCustMajOfUser;
    }
    /**
     * Retrieves the CustMajOfUser property.
     *
     * @return
     *  String containing the CustMajOfUser property.
     */
    public String getCustMajOfUser(){
        return mCustMajOfUser;
    }


    /**
     * Sets the UserCode property.
     *
     * @param pUserCode
     *  String to use to update the property.
     */
    public void setUserCode(String pUserCode){
        this.mUserCode = pUserCode;
    }
    /**
     * Retrieves the UserCode property.
     *
     * @return
     *  String containing the UserCode property.
     */
    public String getUserCode(){
        return mUserCode;
    }


    /**
     * Sets the CustMajOfSite property.
     *
     * @param pCustMajOfSite
     *  String to use to update the property.
     */
    public void setCustMajOfSite(String pCustMajOfSite){
        this.mCustMajOfSite = pCustMajOfSite;
    }
    /**
     * Retrieves the CustMajOfSite property.
     *
     * @return
     *  String containing the CustMajOfSite property.
     */
    public String getCustMajOfSite(){
        return mCustMajOfSite;
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


    
}
