package com.cleanwise.service.api.value;


import com.cleanwise.service.api.framework.*;
import java.lang.*;
import java.util.*;
import java.io.*;


public class ServiceProviderData extends ValueObject {
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = -8015398466835572820L;
    private BusEntityData mBusEntity;
    private AddressData mPrimaryAddress;
    private PhoneData mPrimaryPhone;
    private PhoneData mPrimaryFax;
    private EmailData mPrimaryEmail;
    private PropertyData mServiceProvider;
    private ServiceProviderData mServiceProviderData;
//    private String mUserTypeCd;
    private String[] mServiceProviderTypeId = null;

//    private PropertyData mWomanOwnedBusiness;
//    private PropertyData mMinorityOwnedBusiness;
//    private PropertyData mJWOD;
//    private PropertyData mOtherBusiness;
//    private PropertyDataVector mSpecializations;
//    private PropertyDataVector mMiscProperties;
    /**
     * Holds value of property storeId.
     */
    private int storeId;



    /**
     *  Creates a new <code>ServiceProviderData</code> instance. This should be a
     *  private interface callable only from the ServiceProvider session bean.
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

    public ServiceProviderData(BusEntityData pBusEntity,
            int storeId,
            AddressData pPrimaryAddress,
            PhoneData pPrimaryPhone,
            PhoneData pPrimaryFax,
            EmailData pPrimaryEmail,
            PropertyData pServiceProvider,
            String[] serviceProviderTypeId) {
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
        this.mServiceProvider = (pServiceProvider != null)
        ? pServiceProvider : PropertyData.createValue();
        this.mServiceProviderTypeId = (serviceProviderTypeId != null)
            ? serviceProviderTypeId : new String[0];
    }


    /**
     *  <code>setSpecializations</code> sets the ServiceProvider specialization
     *  properties. Intended use is that the property short desc would be the
     *  specialization (e.g. chemicals, adhesives) and the value would be 1 or 0
     *  (i.e. like a boolean - true or false)
     *
     *@param  pProps  The new Specializations value
     */
//    public void setSpecializations(PropertyDataVector pProps) {
//        mSpecializations = pProps;
//    }
    public void setServiceProvider(PropertyData pProps) {
	    mServiceProvider = pProps;
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
//    public PropertyData getBusinessClass() {
//	return mBusinessClass;
//    }


    /**
     * Describe <code>getWomanOwnedBusiness</code> method here.
     *
     * @return a <code>PropertyData</code> value
     */
//    public PropertyData getWomanOwnedBusiness() {
//	return mWomanOwnedBusiness;
//    }


    /**
     * Describe <code>getMinorityOwnedBusiness</code> method here.
     *
     * @return a <code>PropertyData</code> value
     */
//    public PropertyData getMinorityOwnedBusiness() {
//	return mMinorityOwnedBusiness;
//    }


    /**
     * Describe <code>getJWOD</code> method here.
     *
     * @return a <code>PropertyData</code> value
     */
//    public PropertyData getJWOD() {
//	return mJWOD;
//    }

    public PropertyData getServiceProvider() {
	    return mServiceProvider;
    }
    

    /**
     * Describe <code>getOtherBusiness</code> method here.
     *
     * @return a <code>PropertyData</code> value
     */
//    public PropertyData getOtherBusiness() {
//	return mOtherBusiness;
//    }


    /**
     *  <code>getSpecializations</code> gets the ServiceProvider specialization
     *  properties. Intended use is that the property short desc would be the
     *  specialization (e.g. chemicals, adhesives) and the value would be 1 or 0
     *  (i.e. like a boolean - true or false)
     *
     *@return    an <code>PropertyDataVector</code> value
     */
//    public PropertyDataVector getSpecializations() {
//        return mSpecializations;
//    }


    /**
     *  Describe <code>getMiscProperties</code> method here.
     *
     *@return    an <code>PropertyDataVector</code> value
     */
//    public PropertyDataVector getMiscProperties() {
//        return mMiscProperties;
//    }


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
            ", ServiceProviderType=" + mServiceProviderTypeId +
	    "]";
    }


    /**
     *  Describe <code>createValue</code> method here.
     *
     *@return    a <code>ServiceProviderData</code> value
     */
    public static ServiceProviderData createValue() {
        return new ServiceProviderData(null,0,  null, null, null, null, null, null);
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


	/**
	 * @return Returns the mUserTypeCd.
	 */
//	public String getUserTypeCd() {
//		return mUserTypeCd;
//	}


	/**
	 * @param userTypeCd The mUserTypeCd to set.
	 */
//	public void setUserTypeCd(String userTypeCd) {
//		mUserTypeCd = userTypeCd;
//	}


	/**
	 * @return Returns the mServiceProviderTypeId.
	 */
	public String[] getServiceProviderTypeId() {
		return mServiceProviderTypeId;
	}


	/**
	 * @param serviceProviderTypeId The mServiceProviderTypeId to set.
	 */
	public void setServiceProviderTypeId(String[] serviceProviderTypeId) {
            mServiceProviderTypeId = (serviceProviderTypeId != null)
                                    ? serviceProviderTypeId : new String[0];
	}


	/**
	 * @return Returns the mServiceProviderData.
	 */
	public ServiceProviderData getServiceProviderData() {
		return mServiceProviderData;
	}


	/**
	 * @param serviceProviderData The mServiceProviderData to set.
	 */
	public void setServiceProviderData(ServiceProviderData serviceProviderData) {
		mServiceProviderData = serviceProviderData;
	}
}


