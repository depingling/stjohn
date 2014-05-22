
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        DMSISiteView
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
 * <code>DMSISiteView</code> is a ViewObject class for UI.
 */
public class DMSISiteView
extends ValueObject
{
   
    private static final long serialVersionUID = 12345L;
    private String mSiteBudgetRefNumber;
    private String mSiteDescription;
    private String mAddress1;
    private String mAddress2;
    private String mAddress3;
    private String mCity;
    private String mStateProvinceCd;
    private String mPostalCode;
    private String mTaxeble;

    /**
     * Constructor.
     */
    public DMSISiteView ()
    {
        mSiteBudgetRefNumber = "";
        mSiteDescription = "";
        mAddress1 = "";
        mAddress2 = "";
        mAddress3 = "";
        mCity = "";
        mStateProvinceCd = "";
        mPostalCode = "";
        mTaxeble = "";
    }

    /**
     * Constructor. 
     */
    public DMSISiteView(String parm1, String parm2, String parm3, String parm4, String parm5, String parm6, String parm7, String parm8, String parm9)
    {
        mSiteBudgetRefNumber = parm1;
        mSiteDescription = parm2;
        mAddress1 = parm3;
        mAddress2 = parm4;
        mAddress3 = parm5;
        mCity = parm6;
        mStateProvinceCd = parm7;
        mPostalCode = parm8;
        mTaxeble = parm9;
        
    }

    /**
     * Creates a new DMSISiteView
     *
     * @return
     *  Newly initialized DMSISiteView object.
     */
    public static DMSISiteView createValue () 
    {
        DMSISiteView valueView = new DMSISiteView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this DMSISiteView object
     */
    public String toString()
    {
        return "[" + "SiteBudgetRefNumber=" + mSiteBudgetRefNumber + ", SiteDescription=" + mSiteDescription + ", Address1=" + mAddress1 + ", Address2=" + mAddress2 + ", Address3=" + mAddress3 + ", City=" + mCity + ", StateProvinceCd=" + mStateProvinceCd + ", PostalCode=" + mPostalCode + ", Taxeble=" + mTaxeble + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("DMSISite");
	root.setAttribute("Id", String.valueOf(mSiteBudgetRefNumber));

	Element node;

        node = doc.createElement("SiteDescription");
        node.appendChild(doc.createTextNode(String.valueOf(mSiteDescription)));
        root.appendChild(node);

        node = doc.createElement("Address1");
        node.appendChild(doc.createTextNode(String.valueOf(mAddress1)));
        root.appendChild(node);

        node = doc.createElement("Address2");
        node.appendChild(doc.createTextNode(String.valueOf(mAddress2)));
        root.appendChild(node);

        node = doc.createElement("Address3");
        node.appendChild(doc.createTextNode(String.valueOf(mAddress3)));
        root.appendChild(node);

        node = doc.createElement("City");
        node.appendChild(doc.createTextNode(String.valueOf(mCity)));
        root.appendChild(node);

        node = doc.createElement("StateProvinceCd");
        node.appendChild(doc.createTextNode(String.valueOf(mStateProvinceCd)));
        root.appendChild(node);

        node = doc.createElement("PostalCode");
        node.appendChild(doc.createTextNode(String.valueOf(mPostalCode)));
        root.appendChild(node);

        node = doc.createElement("Taxeble");
        node.appendChild(doc.createTextNode(String.valueOf(mTaxeble)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public DMSISiteView copy()  {
      DMSISiteView obj = new DMSISiteView();
      obj.setSiteBudgetRefNumber(mSiteBudgetRefNumber);
      obj.setSiteDescription(mSiteDescription);
      obj.setAddress1(mAddress1);
      obj.setAddress2(mAddress2);
      obj.setAddress3(mAddress3);
      obj.setCity(mCity);
      obj.setStateProvinceCd(mStateProvinceCd);
      obj.setPostalCode(mPostalCode);
      obj.setTaxeble(mTaxeble);
      
      return obj;
    }

    
    /**
     * Sets the SiteBudgetRefNumber property.
     *
     * @param pSiteBudgetRefNumber
     *  String to use to update the property.
     */
    public void setSiteBudgetRefNumber(String pSiteBudgetRefNumber){
        this.mSiteBudgetRefNumber = pSiteBudgetRefNumber;
    }
    /**
     * Retrieves the SiteBudgetRefNumber property.
     *
     * @return
     *  String containing the SiteBudgetRefNumber property.
     */
    public String getSiteBudgetRefNumber(){
        return mSiteBudgetRefNumber;
    }


    /**
     * Sets the SiteDescription property.
     *
     * @param pSiteDescription
     *  String to use to update the property.
     */
    public void setSiteDescription(String pSiteDescription){
        this.mSiteDescription = pSiteDescription;
    }
    /**
     * Retrieves the SiteDescription property.
     *
     * @return
     *  String containing the SiteDescription property.
     */
    public String getSiteDescription(){
        return mSiteDescription;
    }


    /**
     * Sets the Address1 property.
     *
     * @param pAddress1
     *  String to use to update the property.
     */
    public void setAddress1(String pAddress1){
        this.mAddress1 = pAddress1;
    }
    /**
     * Retrieves the Address1 property.
     *
     * @return
     *  String containing the Address1 property.
     */
    public String getAddress1(){
        return mAddress1;
    }


    /**
     * Sets the Address2 property.
     *
     * @param pAddress2
     *  String to use to update the property.
     */
    public void setAddress2(String pAddress2){
        this.mAddress2 = pAddress2;
    }
    /**
     * Retrieves the Address2 property.
     *
     * @return
     *  String containing the Address2 property.
     */
    public String getAddress2(){
        return mAddress2;
    }


    /**
     * Sets the Address3 property.
     *
     * @param pAddress3
     *  String to use to update the property.
     */
    public void setAddress3(String pAddress3){
        this.mAddress3 = pAddress3;
    }
    /**
     * Retrieves the Address3 property.
     *
     * @return
     *  String containing the Address3 property.
     */
    public String getAddress3(){
        return mAddress3;
    }


    /**
     * Sets the City property.
     *
     * @param pCity
     *  String to use to update the property.
     */
    public void setCity(String pCity){
        this.mCity = pCity;
    }
    /**
     * Retrieves the City property.
     *
     * @return
     *  String containing the City property.
     */
    public String getCity(){
        return mCity;
    }


    /**
     * Sets the StateProvinceCd property.
     *
     * @param pStateProvinceCd
     *  String to use to update the property.
     */
    public void setStateProvinceCd(String pStateProvinceCd){
        this.mStateProvinceCd = pStateProvinceCd;
    }
    /**
     * Retrieves the StateProvinceCd property.
     *
     * @return
     *  String containing the StateProvinceCd property.
     */
    public String getStateProvinceCd(){
        return mStateProvinceCd;
    }


    /**
     * Sets the PostalCode property.
     *
     * @param pPostalCode
     *  String to use to update the property.
     */
    public void setPostalCode(String pPostalCode){
        this.mPostalCode = pPostalCode;
    }
    /**
     * Retrieves the PostalCode property.
     *
     * @return
     *  String containing the PostalCode property.
     */
    public String getPostalCode(){
        return mPostalCode;
    }


    /**
     * Sets the Taxeble property.
     *
     * @param pTaxeble
     *  String to use to update the property.
     */
    public void setTaxeble(String pTaxeble){
        this.mTaxeble = pTaxeble;
    }
    /**
     * Retrieves the Taxeble property.
     *
     * @return
     *  String containing the Taxeble property.
     */
    public String getTaxeble(){
        return mTaxeble;
    }


    
}
