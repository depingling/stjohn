package com.cleanwise.service.api.value;

/**
 * Title:        StoreData
 * Description:  Value object extension for marshalling store data.
 * Purpose:      Obtains and marshals store information among session components.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Tim Besser, CleanWise, Inc.
 */

import com.cleanwise.service.api.framework.*;

import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;

/**
 * <code>StoreData</code> is a value object that aggregates all the value
 * objects that make up a Store.
 */
public class StoreData extends ValueObject
{                                                       
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = 4548065248744794976L;
    private BusEntityData mBusEntity;
    private PropertyData mPrefix;
//    private PropertyData mPrefixNew;
    private PropertyData mStoreType;
    private EmailData mCustomerServiceEmail;
    private EmailData mContactUsEmail;
    private EmailData mDefaultEmail;
    private AddressData mPrimaryAddress;
    private PhoneData mPrimaryPhone;
    private PhoneData mPrimaryFax;
    private EmailData mPrimaryEmail;
    private PropertyData mStorePrimaryWebAddress;
    private PropertyData mStoreBusinessName;
    private PropertyDataVector mMiscProperties;
    private PropertyData mCallHours;
    private CountryPropertyDataVector mCountryProperties;

    /**
     * Holds value of property oddRowColor.
     */
    private String oddRowColor;

    /**
     * Holds value of property evenRowColor.
     */
    private String evenRowColor;
    
    private String pendingOrderNotification;


    /**
     * Creates a new <code>StoreData</code> instance.  This should be
     * a private interface callable only from the Store session bean.
     * (Unfortunately I do not know of a way to do that)
     */

    public StoreData(BusEntityData pBusEntity,
         PropertyData pPrefix,
         PropertyData pStoreType,
         EmailData pCustomerServiceEmail,
         EmailData pContactUsEmail,
         EmailData pDefaultEmail,
         AddressData pPrimaryAddress,
         PhoneData pPrimaryPhone,
         PhoneData pPrimaryFax,
         EmailData pPrimaryEmail,
                     PropertyData pStorePrimaryWebAddress,
                     PropertyData pStoreBusinessName,
                     PropertyData pCallHours,
         PropertyDataVector pMiscProperties,
                     String pEvenRowColor,
                     String pOddRowColor,
                     CountryPropertyDataVector pCountryProperties,
                     String pPendingOrderNotification) {
  this.mBusEntity = (pBusEntity != null)
      ? pBusEntity : BusEntityData.createValue();
  this.mPrefix = (pPrefix != null)
      ? pPrefix : PropertyData.createValue();
  this.mStoreType = (pStoreType != null)
      ? pStoreType : PropertyData.createValue();
  this.mCustomerServiceEmail = (pCustomerServiceEmail != null)
      ? pCustomerServiceEmail : EmailData.createValue();
  this.mContactUsEmail = (pContactUsEmail != null)
      ? pContactUsEmail : EmailData.createValue();
  this.mDefaultEmail = (pDefaultEmail != null)
  ? pDefaultEmail : EmailData.createValue();
  this.mPrimaryAddress = (pPrimaryAddress != null)
      ? pPrimaryAddress : AddressData.createValue();
  this.mPrimaryPhone = (pPrimaryPhone != null)
      ? pPrimaryPhone : PhoneData.createValue();
  this.mPrimaryFax = (pPrimaryFax != null)
      ? pPrimaryFax : PhoneData.createValue();
  this.mPrimaryEmail = (pPrimaryEmail != null)
      ? pPrimaryEmail : EmailData.createValue();
        this.mStoreBusinessName = (pStoreBusinessName != null)
      ? pStoreBusinessName : PropertyData.createValue();
        this.mCallHours = (pCallHours != null)
      ? pCallHours : PropertyData.createValue();
        this.mStorePrimaryWebAddress = (pStorePrimaryWebAddress != null)
      ? pStorePrimaryWebAddress : PropertyData.createValue();
  this.mMiscProperties = (pMiscProperties != null)
      ? pMiscProperties : new PropertyDataVector();
        this.evenRowColor = pEvenRowColor;
        this.oddRowColor = pOddRowColor;
  this.mCountryProperties = (pCountryProperties != null)
      ? pCountryProperties : new CountryPropertyDataVector();
//  this.mPrefixNew = PropertyData.createValue();
  this.pendingOrderNotification = pPendingOrderNotification;
    }

    /**
     * Describe <code>createValue</code> method here.
     *
     * @return a <code>StoreData</code> value
     */
    public static StoreData createValue ()
    {
  return new StoreData(null,null,null,null,null,null,
           null,null,null,null,null,null,null,null,null,null,null, null);
    }

    /**
     * Describe <code>getBusEntity</code> method here.
     *
     * @return a <code>BusEntityData</code> value
     */
    public BusEntityData getBusEntity() {
  return mBusEntity;
    }

    public int getStoreId() {
  return mBusEntity.getBusEntityId();
    }

    /**
     * Describe <code>getPrefix</code> method here.
     *
     * @return a <code>String</code> value
     */
    public PropertyData getPrefix() {
  return mPrefix;
    }

    /**
     * Describe <code>getPrefixNew</code> method here.
     *
     * @return a <code>String</code> value
     */
//    public PropertyData getPrefixNew() {
//  return mPrefixNew;
//    }

    /**
     * Describe <code>setPrefixNew</code> method here.
     *
     */
//    public void setPrefixNew(PropertyData pVal) {
//    mPrefixNew = pVal;
//    }

    /**
     * Describe <code>getStoreType</code> method here.
     *
     * @return a <code>String</code> value
     */
    public PropertyData getStoreType() {
  return mStoreType;
    }

    /**
     * Describe <code>getPrimaryAddress</code> method here.
     *
     * @return an <code>AddressData</code> value
     */
    public AddressData getPrimaryAddress() {
  return mPrimaryAddress;
    }

    /**
     * Describe <code>getPrimaryFax</code> method here.
     *
     * @return a <code>PhoneData</code> value
     */
    public PhoneData getPrimaryFax() {
  return mPrimaryFax;
    }

    /**
     * Describe <code>getPrimaryPhone</code> method here.
     *
     * @return a <code>PhoneData</code> value
     */
    public PhoneData getPrimaryPhone() {
  return mPrimaryPhone;
    }

    /**
     * Describe <code>getPrimaryEmail</code> method here.
     *
     * @return an <code>EmailData</code> value
     */
    public EmailData getPrimaryEmail() {
  return mPrimaryEmail;
    }

    /**
     * Describe <code>getContactUsEmail</code> method here.
     *
     * @return an <code>EmailData</code> value
     */
    public EmailData getContactUsEmail() {
  return mContactUsEmail;
    }

    /**
     * Describe <code>getCustomerServiceEmail</code> method here.
     *    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this StoreData object
     */
    public EmailData getCustomerServiceEmail() {
  return mCustomerServiceEmail;
    }

    public EmailData getDefaultEmail() {
    	return mDefaultEmail;
    }
    /**
     * Describe <code>getStorePrimaryWebAddress</code> method here.
     *
     * @return an <code>PropertyData</code> value
     */
    public PropertyData getStorePrimaryWebAddress() {
  return mStorePrimaryWebAddress;
    }

    /**
     * Describe <code>getStoreBusinessName</code> method here.
     *
     * @return an <code>PropertyData</code> value
     */
    public PropertyData getStoreBusinessName() {
  return mStoreBusinessName;
    }

    /**
     * Describe <code>getMiscProperties</code> method here.
     *
     * @return an <code>PropertyDataVector</code> value
     */
    public PropertyDataVector getMiscProperties() {
  return mMiscProperties;
    }

    public CountryPropertyDataVector getCountryProperties() {
      return mCountryProperties;
    }

    /**
     *Convinience method instead of looping through the misc properties
     */
    public String getErpSystemCode(){
        return Utility.getPropertyValue(getMiscProperties(),RefCodeNames.PROPERTY_TYPE_CD.ERP_SYSTEM);
    }

    /**
     *Convinience method instead of looping through the misc properties
     */
    public boolean isAccountNameInSiteAddress(){
      String str = Utility.getPropertyValue(getMiscProperties(),RefCodeNames.PROPERTY_TYPE_CD.ACCOUNT_NAME_IN_SITE_ADDRESS);
      return Utility.isTrue(str);
    }

    public boolean isStateProvinceRequired() {
      String str = Utility.getCountryPropertyValue(getCountryProperties(), RefCodeNames.COUNTRY_PROPERTY.USES_STATE);
      return Utility.isTrue(str);
    }

     public boolean isAllowAssetManagement() {
     String str = Utility.getPropertyValue(getMiscProperties(),RefCodeNames.PROPERTY_TYPE_CD.ASSET_MANAGEMENT);
     return Utility.isTrue(str);
    }

    public boolean isAllowBudgetThreshold() {
        String str = Utility.getPropertyValue(getMiscProperties(), RefCodeNames.PROPERTY_TYPE_CD.BUDGET_THRESHOLD_FL);
        return Utility.isTrue(str);
    }

    public boolean isAllowSpecialPermission() {
        String str = Utility.getPropertyValue(getMiscProperties(), RefCodeNames.PROPERTY_TYPE_CD.ALLOW_SPECIAL_PRTMISSION_ITEMS);
        return Utility.isTrue(str);
    }

    public boolean isRequireErpAccountNumber() {
         String str = Utility.getPropertyValue(getMiscProperties(),RefCodeNames.PROPERTY_TYPE_CD.ERP_ACCOUNT_NUMBER);
         return Utility.isTrue(str);
     }
     
     public boolean isSetCostAndPriceEqual() {
         String str = Utility.getPropertyValue(getMiscProperties(),RefCodeNames.PROPERTY_TYPE_CD.EQUAL_COST_AND_PRICE);
         return Utility.isTrue(str);
     }
    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this SiteData object
     */
    public String toString()
    {
  return "[" + "BusEntity=" + mBusEntity + ", Prefix=" + mPrefix + /*", PrefixNew=" + mPrefixNew +*/ ", StoreType=" + mStoreType + ", CustomerServiceEmail=" + mCustomerServiceEmail + ", DefaultEmail=" + mDefaultEmail + ", ContactUsEmail=" + mContactUsEmail + ", PrimaryAddress=" + mPrimaryAddress + ", PrimaryPhone=" + mPrimaryPhone + ", PrimaryFax=" + mPrimaryFax + ", PrimaryEmail=" + mPrimaryEmail + ", MiscProperties=" + mMiscProperties + "]";
    }

    /**
     * Getter for property callHours.
     * @return Value of property callHours.
     */
    public PropertyData getCallHours() {

        return mCallHours;
    }

    /**
     * Setter for property callHours.
     * @param callHours New value of property callHours.
     */
    public void setCallHours(PropertyData pCallHours) {

        mCallHours = pCallHours;
    }

    /**
     * Getter for property oddRowColor.
     * @return Value of property oddRowColor.
     */
    public String getOddRowColor() {

        return this.oddRowColor;
    }

    /**
     * Setter for property oddRowColor.
     * @param oddRowColor New value of property oddRowColor.
     */
    public void setOddRowColor(String oddRowColor) {

        this.oddRowColor = oddRowColor;
    }

    /**
     * Getter for property evenRowColor.
     * @return Value of property evenRowColor.
     */
    public String getEvenRowColor() {

        return this.evenRowColor;
    }

    /**
     * Setter for property evenRowColor.
     * @param evenRowColor New value of property evenRowColor.
     */
    public void setEvenRowColor(String evenRowColor) {

        this.evenRowColor = evenRowColor;
    }

    /**
     * Holds value of property taxableIndicator.
     */
    private boolean taxableIndicator;

    /**
     * Getter for property taxableIndicator.
     * @return Value of property taxableIndicator.
     */
    public boolean isTaxableIndicator() {
        return Utility.isTrue(Utility.getPropertyValue(getMiscProperties(),RefCodeNames.PROPERTY_TYPE_CD.TAXABLE_INDICATOR));
    }

    public String getPendingOrderNotification() {
        return pendingOrderNotification;
    }

    public void setPendingOrderNotification(String pendingOrderNotification) {
        this.pendingOrderNotification = pendingOrderNotification;
    }
}
