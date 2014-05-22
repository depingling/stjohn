package com.cleanwise.service.api.value;

/**
 * Title:        UserInfoData
 * Description:  Value object extension for user contact information.
 * Purpose:      Contains user contact information
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Kevin Hickey, CleanWise, Inc.
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.RefCodeNames;
import java.lang.*;
import java.util.*;
import java.io.*;

/**
 * <code>UserInfoData</code> is a value object that aggregates all the value
 * objects that make up a User
 */
public class UserInfoData extends ValueObject
{
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = -770170432966071932L;
    public static String
        USER_GETS_EMAIL_ORDER_NEEDS_APPROVAL = "ONAe",
        USER_GETS_EMAIL_ORDER_WAS_REJECTED = "ORe",
        USER_GETS_EMAIL_ORDER_WAS_MODIFIED = "OMe",
        USER_GETS_EMAIL_ORDER_WAS_APPROVED = "OAe",
        USER_GETS_EMAIL_ORDER_DETAIL_APPROVED = "ODAe",
        USER_GETS_EMAIL_ORDER_SHIPPED="OSe",
        USER_GETS_EMAIL_WORK_ORDER_COMPLETED="WOCe",
        USER_GETS_EMAIL_WORK_ORDER_ACCEPTED="WOABPe",
        USER_GETS_EMAIL_WORK_ORDER_REJECTED="WORBPe",
        USER_GETS_EMAIL_CUTOFF_TIME_REMINDER="CTRe",
        USER_GETS_EMAIL_PHYSICAL_INV_NON_COMPL_SITE_LISTING="PINCSLe",
        USER_GETS_EMAIL_PHYSICAL_INV_COUNTS_PAST_DUE="PICPDe";

    private UserData mUserData;
	private EmailData mEmailData;
	private AddressData mAddressData;
	private PhoneData mPhoneData;
	private PhoneData mFaxPhoneData;
	private PhoneData mMobilePhoneData;
	private ArrayList mMemberOfGroups;
	private CountryData mCountryData;
	private LanguageData mLanguageData;

	/**
	 * <code>getCountryData</code> method.
	 * @return mCountryData a <code>CountryData</code> value
	 */
    public CountryData getCountryData() {
		return mCountryData;
	}

    /**
	 * <code>setCountryData</code> method.
	 * @param mCountryData a <code>CountryData</code> value
	 */
	public void setCountryData(CountryData countryData) {
		this.mCountryData = countryData;
	}


	/**
	 * <code>getCountryData</code> method.
	 * @return mlanguageData a <code>LanguageData</code> value
	 */
	public LanguageData getLanguageData() {
		return mLanguageData;
	}

	/**
	 * <code>setLanguageData</code> method.
	 * @param languageData a <code>LanguageData</code> value
	 */
	public void setLanguageData(LanguageData languageData) {
		this.mLanguageData = languageData;
	}


	/**
     * Holds value of property userCanShopDefault.
     */
    private String userCanShopDefault;

    /**
     * Holds value of property userHasReportingDefault.
     */
    private String userHasReportingDefault;

    /**
     * Holds value of property userApprovalLevelDefault.
     */
    private String userApprovalLevelDefault;
    private UserAccountRightsViewVector mUserAccountRights;

    public String getRightsForAccount(int pAccountId) {
        for ( int i = 0; null != mUserAccountRights &&
                  i < mUserAccountRights.size();
              i++) {
            UserAccountRightsView uar = (UserAccountRightsView)
                mUserAccountRights.get(i);
            BusEntityData aD = uar.getAccountData();
            if ( pAccountId == aD.getBusEntityId() ) {
                PropertyData pd = uar.getUserSettings();
                if ( pd.getValue() != null &&
                     pd.getValue().length() > 0 ) {
                    return pd.getValue();
                }
            }
        }
        return null;
    }

    
    public UserAccountRightsViewVector getUserAccountRights()
    {
        if ( mUserAccountRights == null )
            mUserAccountRights = new UserAccountRightsViewVector();
        return mUserAccountRights;
    }
	public void setUserAccountRights(UserAccountRightsViewVector v )
    {
        mUserAccountRights = v;
    }
    
	/**
     * Creates a new <code>UserInfoData</code> instance.  
     */
	 public UserInfoData(UserData pUserData,
	                     EmailData pEmailData,
						 AddressData pAddressData,
						 PhoneData pPhoneData,
						 PhoneData pFaxPhoneData,
						 PhoneData pMobilePhoneData,
						 CountryData pCountryData,
						 LanguageData pLanguageData)
	 {
	   this.mUserData = (pUserData != null) 
	    ? pUserData : UserData.createValue();
	   this.mEmailData = (pEmailData != null)
	    ? pEmailData : EmailData.createValue();
	   this.mAddressData = (pAddressData != null)
	    ? pAddressData : AddressData.createValue();
	   this.mPhoneData = (pPhoneData != null)
	    ? pPhoneData : PhoneData.createValue();
	   this.mFaxPhoneData = (pFaxPhoneData != null)
	    ? pFaxPhoneData : PhoneData.createValue();
		this.mMobilePhoneData = (pMobilePhoneData != null)
	    ? pMobilePhoneData : PhoneData.createValue();
            mMemberOfGroups = new ArrayList();
        this.mCountryData = (pCountryData != null) 
	    ? pCountryData : CountryData.createValue();
        this.mLanguageData = (pLanguageData != null) 
	    ? pLanguageData : LanguageData.createValue();
	 }
	
	
	/**
     * Creates a new UserInfoData
     *
     * @return
     *  Newly initialized UserInfoData object.
     */
    public static UserInfoData createValue () {
	return new UserInfoData(null,null,null,null,null,null,null,null);
    }
	
    public UserData getUserData() {
	return mUserData;
    }
	
    public void setUserData(UserData pUserData){
        this.mUserData = pUserData;
    }
    
    /**
     * Sets the underlinging password of the userData object, this is a convinience method.
     */
    public void setPasswordHash(String pPasswordHash){
        this.mUserData.setPassword(pPasswordHash);
    }
    
    /**
     *Sets the userName of the underlying userData property
     */
    public void setUserName(String value){
        if(this.mUserData == null){
            mUserData = UserData.createValue();
        }
        mUserData.setUserName(value);
    }
    
    /**
     *Sets the firstName of the underlying userData property
     */
    public void setFirstName(String value){
        if(this.mUserData == null){
            mUserData = UserData.createValue();
        }
        mUserData.setFirstName(value);
    }
    
    /**
     *Sets the firstName of the underlying userData property
     */
    public void setLastName(String value){
        if(this.mUserData == null){
            mUserData = UserData.createValue();
        }
        mUserData.setLastName(value);
    }
    
    /**
     *Sets the firstName of the underlying userData property
     */
    public void setUserTypeCd(String value){
        if(this.mUserData == null){
            mUserData = UserData.createValue();
        }
        mUserData.setUserTypeCd(value);
    }
		
    public AddressData getAddressData() {
	return mAddressData;
    }
	
    public void setAddressData(AddressData pAddressData){
        this.mAddressData = pAddressData;
    }
	
    public EmailData getEmailData() {
	return mEmailData;
    }
    
    public void setEmailData(EmailData pEmailData){
        this.mEmailData = pEmailData;
    }
    
    /**
     *Sets the email address of the underlying emailAddressData property
     */
    public void setEmailAddress(String email){
        if(this.mEmailData == null){
            mEmailData = EmailData.createValue();
        }
        mEmailData.setEmailAddress(email);
    }
    
    
    /**
     *Sets the address of the underlying addressData property
     */
    public void setAddress1(String address){
        if(this.mAddressData == null){
            mAddressData = AddressData.createValue();
        }
        mAddressData.setAddress1(address);
    }
    
    /**
     *Sets the address of the underlying addressData property
     */
    public void setAddress2(String address){
        if(this.mAddressData == null){
            mAddressData = AddressData.createValue();
        }
        mAddressData.setAddress2(address);
    }
    
    /**
     *Sets the address of the underlying addressData property
     */
    public void setAddress3(String address){
        if(this.mAddressData == null){
            mAddressData = AddressData.createValue();
        }
        mAddressData.setAddress3(address);
    }
    
    /**
     *Sets the address of the underlying addressData property
     */
    public void setAddress4(String address){
        if(this.mAddressData == null){
            mAddressData = AddressData.createValue();
        }
        mAddressData.setAddress4(address);
    }
    
    /**
     *Sets the address of the underlying addressData property
     */
    public void setCity(String city){
        if(this.mAddressData == null){
            mAddressData = AddressData.createValue();
        }
        mAddressData.setCity(city);
    }
    
    /**
     *Sets the address of the underlying addressData property
     */
    public void setStateProvinceCd(String value){
        if(this.mAddressData == null){
            mAddressData = AddressData.createValue();
        }
        mAddressData.setStateProvinceCd(value);
    }
    
    /**
     *Sets the address of the underlying addressData property
     */
    public void setPostalCode(String value){
        if(this.mAddressData == null){
            mAddressData = AddressData.createValue();
        }
        mAddressData.setPostalCode(value);
    }
    
	
    
	
    public PhoneData getPhone() {
	return mPhoneData;
    }
	
    public void setPhone(PhoneData pPhoneData){
        this.mPhoneData = pPhoneData;
    }
    
    /**
     *Sets the phone number of the underlying phoneData property
     */
    public void setPhoneNumber(String value){
        if(this.mPhoneData == null){
            mPhoneData = PhoneData.createValue();
        }
        mPhoneData.setPhoneNum(value);
    }
	
    public PhoneData getFax() {
	return mFaxPhoneData;
    }
	
    public void setFax(PhoneData pFaxPhoneData){
        this.mFaxPhoneData = pFaxPhoneData;
    }
    
    /**
     *Sets the phone number of the underlying fax property
     */
    public void setFaxNumber(String value){
        if(this.mFaxPhoneData == null){
            mFaxPhoneData = PhoneData.createValue();
        }
        mFaxPhoneData.setPhoneNum(value);
    }
	
    public PhoneData getMobile() {
	return mMobilePhoneData;
    }
	
    public void setMobile(PhoneData pMobilePhoneData){
        this.mMobilePhoneData = pMobilePhoneData;
    }
    
    /**
     *Sets the phone number of the underlying mobile property
     */
    public void setMobileNumber(String value){
        if(this.mMobilePhoneData == null){
            mMobilePhoneData = PhoneData.createValue();
        }
        mMobilePhoneData.setPhoneNum(value);
    }
	
    public ArrayList getMemberOfGroups() {
	return mMemberOfGroups;
    }
	
    public void setMemberOfGroups(ArrayList pMemberOfGroups){
        setDirty(true);
        mMemberOfGroups = pMemberOfGroups;
    }
	
	
    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this UserInfoData object
     */
    public String toString()
    {
	return "[" + "UserData=" + mUserData + 
	             ", AddressData=" + mAddressData + 
	             ", EmailData=" + mEmailData + 
				 ", PhoneData=" + mPhoneData + 
				 ", FaxPhoneData=" + mFaxPhoneData + 
				 ", MobilePhoneData=" + mMobilePhoneData + 
				 "]";
    }

    public boolean subscribesTo(String pSubscriptionCd, int pBusEntityId) {
        if ( null == mUserData )
            return false;

        String rights = getRightsForAccount(pBusEntityId);
        if (rights == null) 
            rights = mUserData.getUserRoleCd();
        
        if ( null == rights || rights.length() == 0 )
            return false;
        
        return (rights.indexOf(pSubscriptionCd) >= 0 );
    }

    

    /**
     * Getter for property userCanShopDefault.
     * @return Value of property userCanShopDefault.
     */
    public String getUserCanShopDefault() {

        return this.userCanShopDefault;
    }

    /**
     * Setter for property userCanShopDefault.
     * @param userCanShopDefault New value of property userCanShopDefault.
     */
    public void setUserCanShopDefault(String userCanShopDefault) {

        this.userCanShopDefault = userCanShopDefault;
    }

    /**
     * Getter for property userHasReportingDefault.
     * @return Value of property userHasReportingDefault.
     */
    public String getUserHasReportingDefault() {

        return this.userHasReportingDefault;
    }

    /**
     * Setter for property userHasReportingDefault.
     * @param userHasReportingDefault New value of property userHasReportingDefault.
     */
    public void setUserHasReportingDefault(String userHasReportingDefault) {

        this.userHasReportingDefault = userHasReportingDefault;
    }

    /**
     * Getter for property userApprovalLevelDefault.
     * @return Value of property userApprovalLevelDefault.
     */
    public String getUserApprovalLevelDefault() {

        return this.userApprovalLevelDefault;
    }

    /**
     * Setter for property userApprovalLevelDefault.
     * @param userApprovalLevelDefault New value of property userApprovalLevelDefault.
     */
    public void setUserApprovalLevelDefault(String userApprovalLevelDefault) {

        this.userApprovalLevelDefault = userApprovalLevelDefault;
    }

    public static boolean isUserActive(UserData pUserData) {
	if ( null == pUserData ) {
	    return false;
	}
	
	if ( pUserData.getUserStatusCd() != null &&
	     pUserData.getUserStatusCd().equals
	     (RefCodeNames.USER_STATUS_CD.ACTIVE) ) {
	    return true;
	}

	return false;
    }
    
    
    private String _customerSystemKey;
    /**
     * Returns a persmissions key that is used by 3rd party systems to identify the 
     * permissions of this user
     */
    public String getCustomerSystemKey(){
    	return _customerSystemKey;
    }
    /**
     * Sets a persmissions key that is used by 3rd party systems to identify the 
     * permissions of this user
     */
    public void setCustomerSystemKey(String pVal){
        _customerSystemKey = pVal;
    }
}



