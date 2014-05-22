
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        NscUsView
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
 * <code>NscUsView</code> is a ViewObject class for UI.
 */
public class NscUsView
extends ValueObject
{
   
    private static final long serialVersionUID = -1L;
    private String mUserName;
    private String mPassword;
    private String mCustomerNumber;
    private String mContactName;
    private String mEmailAddress;
    private String mCatalogName;
    private String mLocationNumber;
    private String mMemberNumber;
    private String mFirstName;
    private String mLastName;
    private int mUserId;
    private String mUserAction;
    private int mStoreId;
    private int mStoreAssocId;
    private String mStoreAssocAction;
    private int mAccountId;
    private int mAccountAssocId;
    private String mAccountAssocAction;
    private int mSiteId;
    private int mSiteAssocId;
    private String mSiteAssocAction;
    private int mEmailId;
    private String mEmailAction;
    private int mMemberId;
    private int mCatalogId;

    /**
     * Constructor.
     */
    public NscUsView ()
    {
        mUserName = "";
        mPassword = "";
        mCustomerNumber = "";
        mContactName = "";
        mEmailAddress = "";
        mCatalogName = "";
        mLocationNumber = "";
        mMemberNumber = "";
        mFirstName = "";
        mLastName = "";
        mUserAction = "";
        mStoreAssocAction = "";
        mAccountAssocAction = "";
        mSiteAssocAction = "";
        mEmailAction = "";
    }

    /**
     * Constructor. 
     */
    public NscUsView(String parm1, String parm2, String parm3, String parm4, String parm5, String parm6, String parm7, String parm8, String parm9, String parm10, int parm11, String parm12, int parm13, int parm14, String parm15, int parm16, int parm17, String parm18, int parm19, int parm20, String parm21, int parm22, String parm23, int parm24, int parm25)
    {
        mUserName = parm1;
        mPassword = parm2;
        mCustomerNumber = parm3;
        mContactName = parm4;
        mEmailAddress = parm5;
        mCatalogName = parm6;
        mLocationNumber = parm7;
        mMemberNumber = parm8;
        mFirstName = parm9;
        mLastName = parm10;
        mUserId = parm11;
        mUserAction = parm12;
        mStoreId = parm13;
        mStoreAssocId = parm14;
        mStoreAssocAction = parm15;
        mAccountId = parm16;
        mAccountAssocId = parm17;
        mAccountAssocAction = parm18;
        mSiteId = parm19;
        mSiteAssocId = parm20;
        mSiteAssocAction = parm21;
        mEmailId = parm22;
        mEmailAction = parm23;
        mMemberId = parm24;
        mCatalogId = parm25;
        
    }

    /**
     * Creates a new NscUsView
     *
     * @return
     *  Newly initialized NscUsView object.
     */
    public static NscUsView createValue () 
    {
        NscUsView valueView = new NscUsView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this NscUsView object
     */
    public String toString()
    {
        return "[" + "UserName=" + mUserName + ", Password=" + mPassword + ", CustomerNumber=" + mCustomerNumber + ", ContactName=" + mContactName + ", EmailAddress=" + mEmailAddress + ", CatalogName=" + mCatalogName + ", LocationNumber=" + mLocationNumber + ", MemberNumber=" + mMemberNumber + ", FirstName=" + mFirstName + ", LastName=" + mLastName + ", UserId=" + mUserId + ", UserAction=" + mUserAction + ", StoreId=" + mStoreId + ", StoreAssocId=" + mStoreAssocId + ", StoreAssocAction=" + mStoreAssocAction + ", AccountId=" + mAccountId + ", AccountAssocId=" + mAccountAssocId + ", AccountAssocAction=" + mAccountAssocAction + ", SiteId=" + mSiteId + ", SiteAssocId=" + mSiteAssocId + ", SiteAssocAction=" + mSiteAssocAction + ", EmailId=" + mEmailId + ", EmailAction=" + mEmailAction + ", MemberId=" + mMemberId + ", CatalogId=" + mCatalogId + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("NscUs");
	root.setAttribute("Id", String.valueOf(mUserName));

	Element node;

        node = doc.createElement("Password");
        node.appendChild(doc.createTextNode(String.valueOf(mPassword)));
        root.appendChild(node);

        node = doc.createElement("CustomerNumber");
        node.appendChild(doc.createTextNode(String.valueOf(mCustomerNumber)));
        root.appendChild(node);

        node = doc.createElement("ContactName");
        node.appendChild(doc.createTextNode(String.valueOf(mContactName)));
        root.appendChild(node);

        node = doc.createElement("EmailAddress");
        node.appendChild(doc.createTextNode(String.valueOf(mEmailAddress)));
        root.appendChild(node);

        node = doc.createElement("CatalogName");
        node.appendChild(doc.createTextNode(String.valueOf(mCatalogName)));
        root.appendChild(node);

        node = doc.createElement("LocationNumber");
        node.appendChild(doc.createTextNode(String.valueOf(mLocationNumber)));
        root.appendChild(node);

        node = doc.createElement("MemberNumber");
        node.appendChild(doc.createTextNode(String.valueOf(mMemberNumber)));
        root.appendChild(node);

        node = doc.createElement("FirstName");
        node.appendChild(doc.createTextNode(String.valueOf(mFirstName)));
        root.appendChild(node);

        node = doc.createElement("LastName");
        node.appendChild(doc.createTextNode(String.valueOf(mLastName)));
        root.appendChild(node);

        node = doc.createElement("UserId");
        node.appendChild(doc.createTextNode(String.valueOf(mUserId)));
        root.appendChild(node);

        node = doc.createElement("UserAction");
        node.appendChild(doc.createTextNode(String.valueOf(mUserAction)));
        root.appendChild(node);

        node = doc.createElement("StoreId");
        node.appendChild(doc.createTextNode(String.valueOf(mStoreId)));
        root.appendChild(node);

        node = doc.createElement("StoreAssocId");
        node.appendChild(doc.createTextNode(String.valueOf(mStoreAssocId)));
        root.appendChild(node);

        node = doc.createElement("StoreAssocAction");
        node.appendChild(doc.createTextNode(String.valueOf(mStoreAssocAction)));
        root.appendChild(node);

        node = doc.createElement("AccountId");
        node.appendChild(doc.createTextNode(String.valueOf(mAccountId)));
        root.appendChild(node);

        node = doc.createElement("AccountAssocId");
        node.appendChild(doc.createTextNode(String.valueOf(mAccountAssocId)));
        root.appendChild(node);

        node = doc.createElement("AccountAssocAction");
        node.appendChild(doc.createTextNode(String.valueOf(mAccountAssocAction)));
        root.appendChild(node);

        node = doc.createElement("SiteId");
        node.appendChild(doc.createTextNode(String.valueOf(mSiteId)));
        root.appendChild(node);

        node = doc.createElement("SiteAssocId");
        node.appendChild(doc.createTextNode(String.valueOf(mSiteAssocId)));
        root.appendChild(node);

        node = doc.createElement("SiteAssocAction");
        node.appendChild(doc.createTextNode(String.valueOf(mSiteAssocAction)));
        root.appendChild(node);

        node = doc.createElement("EmailId");
        node.appendChild(doc.createTextNode(String.valueOf(mEmailId)));
        root.appendChild(node);

        node = doc.createElement("EmailAction");
        node.appendChild(doc.createTextNode(String.valueOf(mEmailAction)));
        root.appendChild(node);

        node = doc.createElement("MemberId");
        node.appendChild(doc.createTextNode(String.valueOf(mMemberId)));
        root.appendChild(node);

        node = doc.createElement("CatalogId");
        node.appendChild(doc.createTextNode(String.valueOf(mCatalogId)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public NscUsView copy()  {
      NscUsView obj = new NscUsView();
      obj.setUserName(mUserName);
      obj.setPassword(mPassword);
      obj.setCustomerNumber(mCustomerNumber);
      obj.setContactName(mContactName);
      obj.setEmailAddress(mEmailAddress);
      obj.setCatalogName(mCatalogName);
      obj.setLocationNumber(mLocationNumber);
      obj.setMemberNumber(mMemberNumber);
      obj.setFirstName(mFirstName);
      obj.setLastName(mLastName);
      obj.setUserId(mUserId);
      obj.setUserAction(mUserAction);
      obj.setStoreId(mStoreId);
      obj.setStoreAssocId(mStoreAssocId);
      obj.setStoreAssocAction(mStoreAssocAction);
      obj.setAccountId(mAccountId);
      obj.setAccountAssocId(mAccountAssocId);
      obj.setAccountAssocAction(mAccountAssocAction);
      obj.setSiteId(mSiteId);
      obj.setSiteAssocId(mSiteAssocId);
      obj.setSiteAssocAction(mSiteAssocAction);
      obj.setEmailId(mEmailId);
      obj.setEmailAction(mEmailAction);
      obj.setMemberId(mMemberId);
      obj.setCatalogId(mCatalogId);
      
      return obj;
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
     * Sets the CustomerNumber property.
     *
     * @param pCustomerNumber
     *  String to use to update the property.
     */
    public void setCustomerNumber(String pCustomerNumber){
        this.mCustomerNumber = pCustomerNumber;
    }
    /**
     * Retrieves the CustomerNumber property.
     *
     * @return
     *  String containing the CustomerNumber property.
     */
    public String getCustomerNumber(){
        return mCustomerNumber;
    }


    /**
     * Sets the ContactName property.
     *
     * @param pContactName
     *  String to use to update the property.
     */
    public void setContactName(String pContactName){
        this.mContactName = pContactName;
    }
    /**
     * Retrieves the ContactName property.
     *
     * @return
     *  String containing the ContactName property.
     */
    public String getContactName(){
        return mContactName;
    }


    /**
     * Sets the EmailAddress property.
     *
     * @param pEmailAddress
     *  String to use to update the property.
     */
    public void setEmailAddress(String pEmailAddress){
        this.mEmailAddress = pEmailAddress;
    }
    /**
     * Retrieves the EmailAddress property.
     *
     * @return
     *  String containing the EmailAddress property.
     */
    public String getEmailAddress(){
        return mEmailAddress;
    }


    /**
     * Sets the CatalogName property.
     *
     * @param pCatalogName
     *  String to use to update the property.
     */
    public void setCatalogName(String pCatalogName){
        this.mCatalogName = pCatalogName;
    }
    /**
     * Retrieves the CatalogName property.
     *
     * @return
     *  String containing the CatalogName property.
     */
    public String getCatalogName(){
        return mCatalogName;
    }


    /**
     * Sets the LocationNumber property.
     *
     * @param pLocationNumber
     *  String to use to update the property.
     */
    public void setLocationNumber(String pLocationNumber){
        this.mLocationNumber = pLocationNumber;
    }
    /**
     * Retrieves the LocationNumber property.
     *
     * @return
     *  String containing the LocationNumber property.
     */
    public String getLocationNumber(){
        return mLocationNumber;
    }


    /**
     * Sets the MemberNumber property.
     *
     * @param pMemberNumber
     *  String to use to update the property.
     */
    public void setMemberNumber(String pMemberNumber){
        this.mMemberNumber = pMemberNumber;
    }
    /**
     * Retrieves the MemberNumber property.
     *
     * @return
     *  String containing the MemberNumber property.
     */
    public String getMemberNumber(){
        return mMemberNumber;
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
     * Sets the UserId property.
     *
     * @param pUserId
     *  int to use to update the property.
     */
    public void setUserId(int pUserId){
        this.mUserId = pUserId;
    }
    /**
     * Retrieves the UserId property.
     *
     * @return
     *  int containing the UserId property.
     */
    public int getUserId(){
        return mUserId;
    }


    /**
     * Sets the UserAction property.
     *
     * @param pUserAction
     *  String to use to update the property.
     */
    public void setUserAction(String pUserAction){
        this.mUserAction = pUserAction;
    }
    /**
     * Retrieves the UserAction property.
     *
     * @return
     *  String containing the UserAction property.
     */
    public String getUserAction(){
        return mUserAction;
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
     * Sets the StoreAssocId property.
     *
     * @param pStoreAssocId
     *  int to use to update the property.
     */
    public void setStoreAssocId(int pStoreAssocId){
        this.mStoreAssocId = pStoreAssocId;
    }
    /**
     * Retrieves the StoreAssocId property.
     *
     * @return
     *  int containing the StoreAssocId property.
     */
    public int getStoreAssocId(){
        return mStoreAssocId;
    }


    /**
     * Sets the StoreAssocAction property.
     *
     * @param pStoreAssocAction
     *  String to use to update the property.
     */
    public void setStoreAssocAction(String pStoreAssocAction){
        this.mStoreAssocAction = pStoreAssocAction;
    }
    /**
     * Retrieves the StoreAssocAction property.
     *
     * @return
     *  String containing the StoreAssocAction property.
     */
    public String getStoreAssocAction(){
        return mStoreAssocAction;
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
     * Sets the AccountAssocId property.
     *
     * @param pAccountAssocId
     *  int to use to update the property.
     */
    public void setAccountAssocId(int pAccountAssocId){
        this.mAccountAssocId = pAccountAssocId;
    }
    /**
     * Retrieves the AccountAssocId property.
     *
     * @return
     *  int containing the AccountAssocId property.
     */
    public int getAccountAssocId(){
        return mAccountAssocId;
    }


    /**
     * Sets the AccountAssocAction property.
     *
     * @param pAccountAssocAction
     *  String to use to update the property.
     */
    public void setAccountAssocAction(String pAccountAssocAction){
        this.mAccountAssocAction = pAccountAssocAction;
    }
    /**
     * Retrieves the AccountAssocAction property.
     *
     * @return
     *  String containing the AccountAssocAction property.
     */
    public String getAccountAssocAction(){
        return mAccountAssocAction;
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
     * Sets the SiteAssocId property.
     *
     * @param pSiteAssocId
     *  int to use to update the property.
     */
    public void setSiteAssocId(int pSiteAssocId){
        this.mSiteAssocId = pSiteAssocId;
    }
    /**
     * Retrieves the SiteAssocId property.
     *
     * @return
     *  int containing the SiteAssocId property.
     */
    public int getSiteAssocId(){
        return mSiteAssocId;
    }


    /**
     * Sets the SiteAssocAction property.
     *
     * @param pSiteAssocAction
     *  String to use to update the property.
     */
    public void setSiteAssocAction(String pSiteAssocAction){
        this.mSiteAssocAction = pSiteAssocAction;
    }
    /**
     * Retrieves the SiteAssocAction property.
     *
     * @return
     *  String containing the SiteAssocAction property.
     */
    public String getSiteAssocAction(){
        return mSiteAssocAction;
    }


    /**
     * Sets the EmailId property.
     *
     * @param pEmailId
     *  int to use to update the property.
     */
    public void setEmailId(int pEmailId){
        this.mEmailId = pEmailId;
    }
    /**
     * Retrieves the EmailId property.
     *
     * @return
     *  int containing the EmailId property.
     */
    public int getEmailId(){
        return mEmailId;
    }


    /**
     * Sets the EmailAction property.
     *
     * @param pEmailAction
     *  String to use to update the property.
     */
    public void setEmailAction(String pEmailAction){
        this.mEmailAction = pEmailAction;
    }
    /**
     * Retrieves the EmailAction property.
     *
     * @return
     *  String containing the EmailAction property.
     */
    public String getEmailAction(){
        return mEmailAction;
    }


    /**
     * Sets the MemberId property.
     *
     * @param pMemberId
     *  int to use to update the property.
     */
    public void setMemberId(int pMemberId){
        this.mMemberId = pMemberId;
    }
    /**
     * Retrieves the MemberId property.
     *
     * @return
     *  int containing the MemberId property.
     */
    public int getMemberId(){
        return mMemberId;
    }


    /**
     * Sets the CatalogId property.
     *
     * @param pCatalogId
     *  int to use to update the property.
     */
    public void setCatalogId(int pCatalogId){
        this.mCatalogId = pCatalogId;
    }
    /**
     * Retrieves the CatalogId property.
     *
     * @return
     *  int containing the CatalogId property.
     */
    public int getCatalogId(){
        return mCatalogId;
    }


    
}
