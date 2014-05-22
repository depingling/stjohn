
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        XpedxUserView
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
 * <code>XpedxUserView</code> is a ViewObject class for UI.
 */
public class XpedxUserView
extends ValueObject
{
   
    private static final long serialVersionUID = 
            -2L
        ;
    private int mStoreId;
    private String mStoreName;
    private int mAccountId;
    private String mAccountName;
    private String mUsername;
    private String mPassword;
    private String mPrefLanguage;
    private String mFirstName;
    private String mLastName;
    private String mAddress1;
    private String mAddress2;
    private String mCity;
    private String mState;
    private String mPostalCode;
    private String mCountry;
    private String mPhone;
    private String mEmail;
    private Boolean mApprover;
    private Boolean mOtherDetailNotification;
    private Boolean mShippingNotification;
    private Boolean mNeedsApproval;
    private Boolean mWasApproved;
    private Boolean mWasRejected;
    private Boolean mWasModified;
    private int mSiteId;
    private String mSiteName;

    /**
     * Constructor.
     */
    public XpedxUserView ()
    {
        mStoreName = "";
        mAccountName = "";
        mUsername = "";
        mPassword = "";
        mPrefLanguage = "";
        mFirstName = "";
        mLastName = "";
        mAddress1 = "";
        mAddress2 = "";
        mCity = "";
        mState = "";
        mPostalCode = "";
        mCountry = "";
        mPhone = "";
        mEmail = "";
        mSiteName = "";
    }

    /**
     * Constructor. 
     */
    public XpedxUserView(int parm1, String parm2, int parm3, String parm4, String parm5, String parm6, String parm7, String parm8, String parm9, String parm10, String parm11, String parm12, String parm13, String parm14, String parm15, String parm16, String parm17, Boolean parm18, Boolean parm19, Boolean parm20, Boolean parm21, Boolean parm22, Boolean parm23, Boolean parm24, int parm25, String parm26)
    {
        mStoreId = parm1;
        mStoreName = parm2;
        mAccountId = parm3;
        mAccountName = parm4;
        mUsername = parm5;
        mPassword = parm6;
        mPrefLanguage = parm7;
        mFirstName = parm8;
        mLastName = parm9;
        mAddress1 = parm10;
        mAddress2 = parm11;
        mCity = parm12;
        mState = parm13;
        mPostalCode = parm14;
        mCountry = parm15;
        mPhone = parm16;
        mEmail = parm17;
        mApprover = parm18;
        mOtherDetailNotification = parm19;
        mShippingNotification = parm20;
        mNeedsApproval = parm21;
        mWasApproved = parm22;
        mWasRejected = parm23;
        mWasModified = parm24;
        mSiteId = parm25;
        mSiteName = parm26;
        
    }

    /**
     * Creates a new XpedxUserView
     *
     * @return
     *  Newly initialized XpedxUserView object.
     */
    public static XpedxUserView createValue () 
    {
        XpedxUserView valueView = new XpedxUserView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this XpedxUserView object
     */
    public String toString()
    {
        return "[" + "StoreId=" + mStoreId + ", StoreName=" + mStoreName + ", AccountId=" + mAccountId + ", AccountName=" + mAccountName + ", Username=" + mUsername + ", Password=" + mPassword + ", PrefLanguage=" + mPrefLanguage + ", FirstName=" + mFirstName + ", LastName=" + mLastName + ", Address1=" + mAddress1 + ", Address2=" + mAddress2 + ", City=" + mCity + ", State=" + mState + ", PostalCode=" + mPostalCode + ", Country=" + mCountry + ", Phone=" + mPhone + ", Email=" + mEmail + ", Approver=" + mApprover + ", OtherDetailNotification=" + mOtherDetailNotification + ", ShippingNotification=" + mShippingNotification + ", NeedsApproval=" + mNeedsApproval + ", WasApproved=" + mWasApproved + ", WasRejected=" + mWasRejected + ", WasModified=" + mWasModified + ", SiteId=" + mSiteId + ", SiteName=" + mSiteName + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("XpedxUser");
	root.setAttribute("Id", String.valueOf(mStoreId));

	Element node;

        node = doc.createElement("StoreName");
        node.appendChild(doc.createTextNode(String.valueOf(mStoreName)));
        root.appendChild(node);

        node = doc.createElement("AccountId");
        node.appendChild(doc.createTextNode(String.valueOf(mAccountId)));
        root.appendChild(node);

        node = doc.createElement("AccountName");
        node.appendChild(doc.createTextNode(String.valueOf(mAccountName)));
        root.appendChild(node);

        node = doc.createElement("Username");
        node.appendChild(doc.createTextNode(String.valueOf(mUsername)));
        root.appendChild(node);

        node = doc.createElement("Password");
        node.appendChild(doc.createTextNode(String.valueOf(mPassword)));
        root.appendChild(node);

        node = doc.createElement("PrefLanguage");
        node.appendChild(doc.createTextNode(String.valueOf(mPrefLanguage)));
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

        node = doc.createElement("Phone");
        node.appendChild(doc.createTextNode(String.valueOf(mPhone)));
        root.appendChild(node);

        node = doc.createElement("Email");
        node.appendChild(doc.createTextNode(String.valueOf(mEmail)));
        root.appendChild(node);

        node = doc.createElement("Approver");
        node.appendChild(doc.createTextNode(String.valueOf(mApprover)));
        root.appendChild(node);

        node = doc.createElement("OtherDetailNotification");
        node.appendChild(doc.createTextNode(String.valueOf(mOtherDetailNotification)));
        root.appendChild(node);

        node = doc.createElement("ShippingNotification");
        node.appendChild(doc.createTextNode(String.valueOf(mShippingNotification)));
        root.appendChild(node);

        node = doc.createElement("NeedsApproval");
        node.appendChild(doc.createTextNode(String.valueOf(mNeedsApproval)));
        root.appendChild(node);

        node = doc.createElement("WasApproved");
        node.appendChild(doc.createTextNode(String.valueOf(mWasApproved)));
        root.appendChild(node);

        node = doc.createElement("WasRejected");
        node.appendChild(doc.createTextNode(String.valueOf(mWasRejected)));
        root.appendChild(node);

        node = doc.createElement("WasModified");
        node.appendChild(doc.createTextNode(String.valueOf(mWasModified)));
        root.appendChild(node);

        node = doc.createElement("SiteId");
        node.appendChild(doc.createTextNode(String.valueOf(mSiteId)));
        root.appendChild(node);

        node = doc.createElement("SiteName");
        node.appendChild(doc.createTextNode(String.valueOf(mSiteName)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public XpedxUserView copy()  {
      XpedxUserView obj = new XpedxUserView();
      obj.setStoreId(mStoreId);
      obj.setStoreName(mStoreName);
      obj.setAccountId(mAccountId);
      obj.setAccountName(mAccountName);
      obj.setUsername(mUsername);
      obj.setPassword(mPassword);
      obj.setPrefLanguage(mPrefLanguage);
      obj.setFirstName(mFirstName);
      obj.setLastName(mLastName);
      obj.setAddress1(mAddress1);
      obj.setAddress2(mAddress2);
      obj.setCity(mCity);
      obj.setState(mState);
      obj.setPostalCode(mPostalCode);
      obj.setCountry(mCountry);
      obj.setPhone(mPhone);
      obj.setEmail(mEmail);
      obj.setApprover(mApprover);
      obj.setOtherDetailNotification(mOtherDetailNotification);
      obj.setShippingNotification(mShippingNotification);
      obj.setNeedsApproval(mNeedsApproval);
      obj.setWasApproved(mWasApproved);
      obj.setWasRejected(mWasRejected);
      obj.setWasModified(mWasModified);
      obj.setSiteId(mSiteId);
      obj.setSiteName(mSiteName);
      
      return obj;
    }

    
    /**
     * Sets the StoreId property.
     *
     * @param pStoreId
     *  int to use to update the property.
     */
    public void setStoreId(int pStoreId){
        this.mStoreId = pStoreId;
    }
    /**
     * Retrieves the StoreId property.
     *
     * @return
     *  int containing the StoreId property.
     */
    public int getStoreId(){
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
     * Sets the Username property.
     *
     * @param pUsername
     *  String to use to update the property.
     */
    public void setUsername(String pUsername){
        this.mUsername = pUsername;
    }
    /**
     * Retrieves the Username property.
     *
     * @return
     *  String containing the Username property.
     */
    public String getUsername(){
        return mUsername;
    }


    /**
     * Sets the Password property.
     *
     * @param pPassword
     *  String to use to update the property.
     */
    public void setPassword(String pPassword){
        this.mPassword = pPassword;
    }
    /**
     * Retrieves the Password property.
     *
     * @return
     *  String containing the Password property.
     */
    public String getPassword(){
        return mPassword;
    }


    /**
     * Sets the PrefLanguage property.
     *
     * @param pPrefLanguage
     *  String to use to update the property.
     */
    public void setPrefLanguage(String pPrefLanguage){
        this.mPrefLanguage = pPrefLanguage;
    }
    /**
     * Retrieves the PrefLanguage property.
     *
     * @return
     *  String containing the PrefLanguage property.
     */
    public String getPrefLanguage(){
        return mPrefLanguage;
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
     * Sets the Phone property.
     *
     * @param pPhone
     *  String to use to update the property.
     */
    public void setPhone(String pPhone){
        this.mPhone = pPhone;
    }
    /**
     * Retrieves the Phone property.
     *
     * @return
     *  String containing the Phone property.
     */
    public String getPhone(){
        return mPhone;
    }


    /**
     * Sets the Email property.
     *
     * @param pEmail
     *  String to use to update the property.
     */
    public void setEmail(String pEmail){
        this.mEmail = pEmail;
    }
    /**
     * Retrieves the Email property.
     *
     * @return
     *  String containing the Email property.
     */
    public String getEmail(){
        return mEmail;
    }


    /**
     * Sets the Approver property.
     *
     * @param pApprover
     *  Boolean to use to update the property.
     */
    public void setApprover(Boolean pApprover){
        this.mApprover = pApprover;
    }
    /**
     * Retrieves the Approver property.
     *
     * @return
     *  Boolean containing the Approver property.
     */
    public Boolean getApprover(){
        return mApprover;
    }


    /**
     * Sets the OtherDetailNotification property.
     *
     * @param pOtherDetailNotification
     *  Boolean to use to update the property.
     */
    public void setOtherDetailNotification(Boolean pOtherDetailNotification){
        this.mOtherDetailNotification = pOtherDetailNotification;
    }
    /**
     * Retrieves the OtherDetailNotification property.
     *
     * @return
     *  Boolean containing the OtherDetailNotification property.
     */
    public Boolean getOtherDetailNotification(){
        return mOtherDetailNotification;
    }


    /**
     * Sets the ShippingNotification property.
     *
     * @param pShippingNotification
     *  Boolean to use to update the property.
     */
    public void setShippingNotification(Boolean pShippingNotification){
        this.mShippingNotification = pShippingNotification;
    }
    /**
     * Retrieves the ShippingNotification property.
     *
     * @return
     *  Boolean containing the ShippingNotification property.
     */
    public Boolean getShippingNotification(){
        return mShippingNotification;
    }


    /**
     * Sets the NeedsApproval property.
     *
     * @param pNeedsApproval
     *  Boolean to use to update the property.
     */
    public void setNeedsApproval(Boolean pNeedsApproval){
        this.mNeedsApproval = pNeedsApproval;
    }
    /**
     * Retrieves the NeedsApproval property.
     *
     * @return
     *  Boolean containing the NeedsApproval property.
     */
    public Boolean getNeedsApproval(){
        return mNeedsApproval;
    }


    /**
     * Sets the WasApproved property.
     *
     * @param pWasApproved
     *  Boolean to use to update the property.
     */
    public void setWasApproved(Boolean pWasApproved){
        this.mWasApproved = pWasApproved;
    }
    /**
     * Retrieves the WasApproved property.
     *
     * @return
     *  Boolean containing the WasApproved property.
     */
    public Boolean getWasApproved(){
        return mWasApproved;
    }


    /**
     * Sets the WasRejected property.
     *
     * @param pWasRejected
     *  Boolean to use to update the property.
     */
    public void setWasRejected(Boolean pWasRejected){
        this.mWasRejected = pWasRejected;
    }
    /**
     * Retrieves the WasRejected property.
     *
     * @return
     *  Boolean containing the WasRejected property.
     */
    public Boolean getWasRejected(){
        return mWasRejected;
    }


    /**
     * Sets the WasModified property.
     *
     * @param pWasModified
     *  Boolean to use to update the property.
     */
    public void setWasModified(Boolean pWasModified){
        this.mWasModified = pWasModified;
    }
    /**
     * Retrieves the WasModified property.
     *
     * @return
     *  Boolean containing the WasModified property.
     */
    public Boolean getWasModified(){
        return mWasModified;
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


    
}
