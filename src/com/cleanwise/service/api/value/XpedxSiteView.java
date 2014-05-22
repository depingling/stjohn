
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        XpedxSiteView
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
 * <code>XpedxSiteView</code> is a ViewObject class for UI.
 */
public class XpedxSiteView
extends ValueObject
{
   
    private static final long serialVersionUID = 12345678L;
    private int mAccountId;
    private String mAccountName;
    private String mSiteName;
    private String mSiteBudgetRefNumber;
    private Boolean mTaxeble;
    private Boolean mEnableInentoryShopping;
    private Boolean mShareBuyerOrderGuides;
    private String mFirstName;
    private String mLastName;
    private String mAddress1;
    private String mAddress2;
    private String mAddress3;
    private String mAddress4;
    private String mCity;
    private String mState;
    private String mPostalCode;
    private String mCountry;
    private String mShippingMessage;

    /**
     * Constructor.
     */
    public XpedxSiteView ()
    {
        mAccountName = "";
        mSiteName = "";
        mSiteBudgetRefNumber = "";
        mFirstName = "";
        mLastName = "";
        mAddress1 = "";
        mAddress2 = "";
        mAddress3 = "";
        mAddress4 = "";
        mCity = "";
        mState = "";
        mPostalCode = "";
        mCountry = "";
        mShippingMessage = "";
    }

    /**
     * Constructor. 
     */
    public XpedxSiteView(int parm1, String parm2, String parm3, String parm4, Boolean parm5, Boolean parm6, Boolean parm7, String parm8, String parm9, String parm10, String parm11, String parm12, String parm13, String parm14, String parm15, String parm16, String parm17, String parm18)
    {
        mAccountId = parm1;
        mAccountName = parm2;
        mSiteName = parm3;
        mSiteBudgetRefNumber = parm4;
        mTaxeble = parm5;
        mEnableInentoryShopping = parm6;
        mShareBuyerOrderGuides = parm7;
        mFirstName = parm8;
        mLastName = parm9;
        mAddress1 = parm10;
        mAddress2 = parm11;
        mAddress3 = parm12;
        mAddress4 = parm13;
        mCity = parm14;
        mState = parm15;
        mPostalCode = parm16;
        mCountry = parm17;
        mShippingMessage = parm18;
        
    }

    /**
     * Creates a new XpedxSiteView
     *
     * @return
     *  Newly initialized XpedxSiteView object.
     */
    public static XpedxSiteView createValue () 
    {
        XpedxSiteView valueView = new XpedxSiteView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this XpedxSiteView object
     */
    public String toString()
    {
        return "[" + "AccountId=" + mAccountId + ", AccountName=" + mAccountName + ", SiteName=" + mSiteName + ", SiteBudgetRefNumber=" + mSiteBudgetRefNumber + ", Taxeble=" + mTaxeble + ", EnableInentoryShopping=" + mEnableInentoryShopping + ", ShareBuyerOrderGuides=" + mShareBuyerOrderGuides + ", FirstName=" + mFirstName + ", LastName=" + mLastName + ", Address1=" + mAddress1 + ", Address2=" + mAddress2 + ", Address3=" + mAddress3 + ", Address4=" + mAddress4 + ", City=" + mCity + ", State=" + mState + ", PostalCode=" + mPostalCode + ", Country=" + mCountry + ", ShippingMessage=" + mShippingMessage + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("XpedxSite");
	root.setAttribute("Id", String.valueOf(mAccountId));

	Element node;

        node = doc.createElement("AccountName");
        node.appendChild(doc.createTextNode(String.valueOf(mAccountName)));
        root.appendChild(node);

        node = doc.createElement("SiteName");
        node.appendChild(doc.createTextNode(String.valueOf(mSiteName)));
        root.appendChild(node);

        node = doc.createElement("SiteBudgetRefNumber");
        node.appendChild(doc.createTextNode(String.valueOf(mSiteBudgetRefNumber)));
        root.appendChild(node);

        node = doc.createElement("Taxeble");
        node.appendChild(doc.createTextNode(String.valueOf(mTaxeble)));
        root.appendChild(node);

        node = doc.createElement("EnableInentoryShopping");
        node.appendChild(doc.createTextNode(String.valueOf(mEnableInentoryShopping)));
        root.appendChild(node);

        node = doc.createElement("ShareBuyerOrderGuides");
        node.appendChild(doc.createTextNode(String.valueOf(mShareBuyerOrderGuides)));
        root.appendChild(node);

        node = doc.createElement("FirstName");
        node.appendChild(doc.createTextNode(String.valueOf(mFirstName)));
        root.appendChild(node);

        node = doc.createElement("LastName");
        node.appendChild(doc.createTextNode(String.valueOf(mLastName)));
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

        node = doc.createElement("Address4");
        node.appendChild(doc.createTextNode(String.valueOf(mAddress4)));
        root.appendChild(node);

        node = doc.createElement("City");
        node.appendChild(doc.createTextNode(String.valueOf(mCity)));
        root.appendChild(node);

        node = doc.createElement("State");
        node.appendChild(doc.createTextNode(String.valueOf(mState)));
        root.appendChild(node);

        node = doc.createElement("PostalCode");
        node.appendChild(doc.createTextNode(String.valueOf(mPostalCode)));
        root.appendChild(node);

        node = doc.createElement("Country");
        node.appendChild(doc.createTextNode(String.valueOf(mCountry)));
        root.appendChild(node);

        node = doc.createElement("ShippingMessage");
        node.appendChild(doc.createTextNode(String.valueOf(mShippingMessage)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public XpedxSiteView copy()  {
      XpedxSiteView obj = new XpedxSiteView();
      obj.setAccountId(mAccountId);
      obj.setAccountName(mAccountName);
      obj.setSiteName(mSiteName);
      obj.setSiteBudgetRefNumber(mSiteBudgetRefNumber);
      obj.setTaxeble(mTaxeble);
      obj.setEnableInentoryShopping(mEnableInentoryShopping);
      obj.setShareBuyerOrderGuides(mShareBuyerOrderGuides);
      obj.setFirstName(mFirstName);
      obj.setLastName(mLastName);
      obj.setAddress1(mAddress1);
      obj.setAddress2(mAddress2);
      obj.setAddress3(mAddress3);
      obj.setAddress4(mAddress4);
      obj.setCity(mCity);
      obj.setState(mState);
      obj.setPostalCode(mPostalCode);
      obj.setCountry(mCountry);
      obj.setShippingMessage(mShippingMessage);
      
      return obj;
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
     * Sets the AccountName property.
     *
     * @param pAccountName
     *  String to use to update the property.
     */
    public void setAccountName(String pAccountName){
        this.mAccountName = pAccountName;
    }
    /**
     * Retrieves the AccountName property.
     *
     * @return
     *  String containing the AccountName property.
     */
    public String getAccountName(){
        return mAccountName;
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
     * Sets the Taxeble property.
     *
     * @param pTaxeble
     *  Boolean to use to update the property.
     */
    public void setTaxeble(Boolean pTaxeble){
        this.mTaxeble = pTaxeble;
    }
    /**
     * Retrieves the Taxeble property.
     *
     * @return
     *  Boolean containing the Taxeble property.
     */
    public Boolean getTaxeble(){
        return mTaxeble;
    }


    /**
     * Sets the EnableInentoryShopping property.
     *
     * @param pEnableInentoryShopping
     *  Boolean to use to update the property.
     */
    public void setEnableInentoryShopping(Boolean pEnableInentoryShopping){
        this.mEnableInentoryShopping = pEnableInentoryShopping;
    }
    /**
     * Retrieves the EnableInentoryShopping property.
     *
     * @return
     *  Boolean containing the EnableInentoryShopping property.
     */
    public Boolean getEnableInentoryShopping(){
        return mEnableInentoryShopping;
    }


    /**
     * Sets the ShareBuyerOrderGuides property.
     *
     * @param pShareBuyerOrderGuides
     *  Boolean to use to update the property.
     */
    public void setShareBuyerOrderGuides(Boolean pShareBuyerOrderGuides){
        this.mShareBuyerOrderGuides = pShareBuyerOrderGuides;
    }
    /**
     * Retrieves the ShareBuyerOrderGuides property.
     *
     * @return
     *  Boolean containing the ShareBuyerOrderGuides property.
     */
    public Boolean getShareBuyerOrderGuides(){
        return mShareBuyerOrderGuides;
    }


    /**
     * Sets the FirstName property.
     *
     * @param pFirstName
     *  String to use to update the property.
     */
    public void setFirstName(String pFirstName){
        this.mFirstName = pFirstName;
    }
    /**
     * Retrieves the FirstName property.
     *
     * @return
     *  String containing the FirstName property.
     */
    public String getFirstName(){
        return mFirstName;
    }


    /**
     * Sets the LastName property.
     *
     * @param pLastName
     *  String to use to update the property.
     */
    public void setLastName(String pLastName){
        this.mLastName = pLastName;
    }
    /**
     * Retrieves the LastName property.
     *
     * @return
     *  String containing the LastName property.
     */
    public String getLastName(){
        return mLastName;
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
     * Sets the Address4 property.
     *
     * @param pAddress4
     *  String to use to update the property.
     */
    public void setAddress4(String pAddress4){
        this.mAddress4 = pAddress4;
    }
    /**
     * Retrieves the Address4 property.
     *
     * @return
     *  String containing the Address4 property.
     */
    public String getAddress4(){
        return mAddress4;
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
     * Sets the State property.
     *
     * @param pState
     *  String to use to update the property.
     */
    public void setState(String pState){
        this.mState = pState;
    }
    /**
     * Retrieves the State property.
     *
     * @return
     *  String containing the State property.
     */
    public String getState(){
        return mState;
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
     * Sets the Country property.
     *
     * @param pCountry
     *  String to use to update the property.
     */
    public void setCountry(String pCountry){
        this.mCountry = pCountry;
    }
    /**
     * Retrieves the Country property.
     *
     * @return
     *  String containing the Country property.
     */
    public String getCountry(){
        return mCountry;
    }


    /**
     * Sets the ShippingMessage property.
     *
     * @param pShippingMessage
     *  String to use to update the property.
     */
    public void setShippingMessage(String pShippingMessage){
        this.mShippingMessage = pShippingMessage;
    }
    /**
     * Retrieves the ShippingMessage property.
     *
     * @return
     *  String containing the ShippingMessage property.
     */
    public String getShippingMessage(){
        return mShippingMessage;
    }


    
}
