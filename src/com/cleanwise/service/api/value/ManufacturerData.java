package com.cleanwise.service.api.value;


import com.cleanwise.service.api.framework.*;
import java.lang.*;
import java.util.*;
import java.io.*;

/**
 *  <code>ManufacturerData</code> is a value object that aggregates all the
 *  value objects that make up a Manufacturer.
 *
 *@author     tbesser
 *@created    August 28, 2001
 */
public class ManufacturerData extends ValueObject {
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = -337494664864037425L;
    private BusEntityData mBusEntity;
    private AddressData mPrimaryAddress;
    private PhoneData mPrimaryPhone;
    private PhoneData mPrimaryFax;
    private EmailData mPrimaryEmail;
    private PropertyData mBusinessClass;
    private PropertyData mWomanOwnedBusiness;
    private PropertyData mMinorityOwnedBusiness;
    private PropertyData mJWOD;
    private PropertyData mOtherBusiness;
    private PropertyDataVector mSpecializations;
    private PropertyDataVector mMiscProperties;
    private PropertyData mOtherNames;
    /**
     * Holds value of property storeId.
     */
    private int storeId;



    /**
     *  Creates a new <code>ManufacturerData</code> instance. This should be a
     *  private interface callable only from the Manufacturer session bean.
     *  (Unfortunately I do not know of a way to do that)
     *
     *@param  pBusEntity        Description of Parameter
     *@param  pPrimaryAddress   Description of Parameter
     *@param  pPrimaryPhone     Description of Parameter
     *@param  pPrimaryFax       Description of Parameter
     *@param  pPrimaryEmail     Description of Parameter
     *@param  pBusinessClass
     *@param  pWomanOwnedBusiness
     *@param  pMinorityOwnedBusiness
     *@param  pJWOD
     *@param  pOtherBusiness
     *@param  pSpecializations  Description of Parameter
     *@param  pMiscProperties   Description of Parameter
     */

    public ManufacturerData(BusEntityData pBusEntity,
            int storeId,
            AddressData pPrimaryAddress,
            PhoneData pPrimaryPhone,
            PhoneData pPrimaryFax,
            EmailData pPrimaryEmail,
	    PropertyData pBusinessClass,
	    PropertyData pWomanOwnedBusiness,
	    PropertyData pMinorityOwnedBusiness,
	    PropertyData pJWOD,
	    PropertyData pOtherBusiness,
            PropertyDataVector pSpecializations,
            PropertyDataVector pMiscProperties, 
            PropertyData pOtherNames) {
        this.mBusEntity = (pBusEntity != null)
	    ? pBusEntity : BusEntityData.createValue();
        this.storeId = storeId;
        this.mPrimaryAddress = (pPrimaryAddress != null)
	    ? pPrimaryAddress : AddressData.createValue();
        this.mPrimaryPhone = (pPrimaryPhone != null)
	    ? pPrimaryPhone : PhoneData.createValue();
        this.mPrimaryFax = (pPrimaryFax != null)
	    ? pPrimaryFax : PhoneData.createValue();
        this.mPrimaryEmail = (pPrimaryEmail != null)
	    ? pPrimaryEmail : EmailData.createValue();
	this.mBusinessClass = (pBusinessClass != null)
	    ? pBusinessClass : PropertyData.createValue();
	this.mWomanOwnedBusiness = (pWomanOwnedBusiness != null)
	    ? pWomanOwnedBusiness : PropertyData.createValue();
	this.mMinorityOwnedBusiness = (pMinorityOwnedBusiness != null)
	    ? pMinorityOwnedBusiness : PropertyData.createValue();
	this.mJWOD = (pJWOD != null)
	    ? pJWOD : PropertyData.createValue();
	this.mOtherBusiness = (pOtherBusiness != null)
	    ? pOtherBusiness : PropertyData.createValue();
        this.mSpecializations = (pSpecializations != null)
	    ? pSpecializations : new PropertyDataVector();
        this.mMiscProperties = (pMiscProperties != null)
	    ? pMiscProperties : new PropertyDataVector();
	    this.mOtherNames = (pOtherNames != null)
	    ? pOtherNames : PropertyData.createValue();
    }


    /**
     *  <code>setSpecializations</code> sets the Manufacturer specialization
     *  properties. Intended use is that the property short desc would be the
     *  specialization (e.g. chemicals, adhesives) and the value would be 1 or 0
     *  (i.e. like a boolean - true or false)
     *
     *@param  pProps  The new Specializations value
     */
    public void setSpecializations(PropertyDataVector pProps) {
        mSpecializations = pProps;
    }


    /**
     *  Describe <code>getBusEntity</code> method here.
     *
     *@return    a <code>BusEntityData</code> value
     */
    public BusEntityData getBusEntity() {
        return mBusEntity;
    }


    /**
     *  Describe <code>getPrimaryAddress</code> method here.
     *
     *@return    an <code>AddressData</code> value
     */
    public AddressData getPrimaryAddress() {
        return mPrimaryAddress;
    }


    /**
     *  Describe <code>getPrimaryFax</code> method here.
     *
     *@return    a <code>PhoneData</code> value
     */
    public PhoneData getPrimaryFax() {
        return mPrimaryFax;
    }


    /**
     *  Describe <code>getPrimaryPhone</code> method here.
     *
     *@return    a <code>PhoneData</code> value
     */
    public PhoneData getPrimaryPhone() {
        return mPrimaryPhone;
    }


    /**
     *  Describe <code>getPrimaryEmail</code> method here.
     *
     *@return    an <code>EmailData</code> value
     */
    public EmailData getPrimaryEmail() {
        return mPrimaryEmail;
    }


    /**
     * Describe <code>getBusinessClass</code> method here.
     *
     * @return a <code>PropertyData</code> value
     */
    public PropertyData getBusinessClass() {
	return mBusinessClass;
    }


    /**
     * Describe <code>getWomanOwnedBusiness</code> method here.
     *
     * @return a <code>PropertyData</code> value
     */
    public PropertyData getWomanOwnedBusiness() {
	return mWomanOwnedBusiness;
    }


    /**
     * Describe <code>getMinorityOwnedBusiness</code> method here.
     *
     * @return a <code>PropertyData</code> value
     */
    public PropertyData getMinorityOwnedBusiness() {
	return mMinorityOwnedBusiness;
    }


    /**
     * Describe <code>getJWOD</code> method here.
     *
     * @return a <code>PropertyData</code> value
     */
    public PropertyData getJWOD() {
	return mJWOD;
    }


    /**
     * Describe <code>getOtherBusiness</code> method here.
     *
     * @return a <code>PropertyData</code> value
     */
    public PropertyData getOtherBusiness() {
	return mOtherBusiness;
    }


    /**
     *  <code>getSpecializations</code> gets the Manufacturer specialization
     *  properties. Intended use is that the property short desc would be the
     *  specialization (e.g. chemicals, adhesives) and the value would be 1 or 0
     *  (i.e. like a boolean - true or false)
     *
     *@return    an <code>PropertyDataVector</code> value
     */
    public PropertyDataVector getSpecializations() {
        return mSpecializations;
    }


    /**
     *  Describe <code>getMiscProperties</code> method here.
     *
     *@return    an <code>PropertyDataVector</code> value
     */
    public PropertyDataVector getMiscProperties() {
        return mMiscProperties;
    }


    /**
     *  Returns a String representation of the value object
     *
     *@return    The String representation of this SiteData object
     */
    public String toString() {
        return "[" + 
	    "BusEntity=" + mBusEntity + 
            ", StoreId = " + storeId +
	    ", PrimaryAddress=" + mPrimaryAddress + 
	    ", PrimaryPhone=" + mPrimaryPhone + 
	    ", PrimaryFax=" + mPrimaryFax + 
	    ", PrimaryEmail=" + mPrimaryEmail + 
	    ", BusinessClass=" +  mBusinessClass +
	    ", WomanOwnedBusiness=" +  mWomanOwnedBusiness +
	    ", MinorityOwnedBusiness=" +  mMinorityOwnedBusiness +
	    ", JWOD=" +  mJWOD +
	    ", OtherBusiness=" +  mOtherBusiness +
	    ", Specializations=" + mSpecializations + 
	    ", MiscProperties=" + mMiscProperties + 
	    "]";
    }


    /**
     *  Describe <code>createValue</code> method here.
     *
     *@return    a <code>ManufacturerData</code> value
     */
    public static ManufacturerData createValue() {
        return new ManufacturerData(null,0,  null, null, null, null, null, null,
				    null, null, null, null, null, null);
    }

    /**
     * Getter for property storeId.
     * @return Value of property storeId.
     */
    public int getStoreId() {

        return this.storeId;
    }

    /**
     * Setter for property storeId.
     * @param storeId New value of property storeId.
     */
    public void setStoreId(int storeId) {

        this.storeId = storeId;
    }


	public void setOtherNames(PropertyData mOtherNames) {
		this.mOtherNames = mOtherNames;
	}


	public PropertyData getOtherNames() {
		return mOtherNames;
	}
}


