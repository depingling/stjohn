/**
 *  Title: SiteMgrDetailForm Description: This is the Struts ActionForm class
 *  for user management page. Purpose: Strut support to search for distributors.
 *  Copyright: Copyright (c) 2001 Company: CleanWise, Inc.
 *
 *@author     durval
 */

package com.cleanwise.view.forms;

import com.cleanwise.service.api.value.BlanketPoNumDataVector;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.view.utils.*;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.SiteData;
import com.cleanwise.service.api.value.PropertyData;
import com.cleanwise.service.api.value.PropertyDataVector;

/**
 *  Form bean for the user manager page.
 *
 *@author     durval
 *@created    August 8, 2001
 */
public final class SiteMgrDetailForm extends Base2DetailForm {

    private String mAccountId;
    private String mAccountName;
    private String mStoreId;
    private String mStoreName;
    private ArrayList mSiteBudgetList;
    private String mSubContractor;
    private String mTaxableIndicator;
    private boolean mInventoryShopping;
    private String mSiteNumber;
    private String mOldSiteNumber;
    private String mShippingMessage;
    private String mEffDate;
    private String mExpDate;
    private IdVector mAvailableShipto = null;
    private boolean ERPEnabled;


    /**
     * Get the value of ShippingMessage.
     * @return value of ShippingMessage.
     */
    public String getShippingMessage() {
        return mShippingMessage;
    }

    /**
     * Set the value of ShippingMessage.
     * @param v  Value to assign to ShippingMessage.
     */
    public void setShippingMessage(String  v) {
        this.mShippingMessage = v;
    }

    // Values for the 3 fields that an account
    // can collect from a site.
    private String mF1Value;
    private String mF2Value;
    private String mF3Value;
    private String mF4Value;
    private String mF5Value;
    private String mF6Value;
    private String mF7Value;
    private String mF8Value;
    private String mF9Value;
    private String mF10Value;
    private String siteReferenceNumber;
    private String distSiteReferenceNumber;
    private String targetFacilityRank;
    private ProfileViewContainer profile;

    /** Holds value of property authorizedReSaleAccount. */
    private boolean authorizedReSaleAccount;


    /** Holds value of property bypassOrderRouting. */
    private boolean bypassOrderRouting;

    /** Holds value of property makeShiptoBillto. */
    private boolean makeShiptoBillto;

    /** Holds value of property consolidatedOrderWarehouse. */
    private boolean consolidatedOrderWarehouse;
    /**
     * Holds value of property blanketPoNumId.
     */
    private Integer blanketPoNumId;



    /**
     *  Sets the StoreName attribute of the SiteMgrDetailForm object
     *
     *@param  mStoreName  The new StoreName value
     */
    public void setStoreName(String mStoreName) {
        this.mStoreName = mStoreName;
    }


    /**
     *  Sets the AccountId attribute of the SiteMgrDetailForm object
     *
     *@param  mAccountId  The new AccountId value
     */
    public void setAccountId(String mAccountId) {
        this.mAccountId = mAccountId;
    }


    /**
     *  Sets the AccountName attribute of the SiteMgrDetailForm object
     *
     *@param  mAccountName  The new AccountName value
     */
    public void setAccountName(String mAccountName) {
        this.mAccountName = mAccountName;
    }


    /**
     *  Sets the StoreId attribute of the SiteMgrDetailForm object
     *
     *@param  mStoreId  The new StoreId value
     */
    public void setStoreId(String mStoreId) {
        this.mStoreId = mStoreId;
    }


    /**
     *  Sets the SiteBudgetList attribute of the SiteMgrDetailForm object
     *
     *@param  pSiteBudgetList  is an ArrayList of SiteBudget objects
     */
    public void setSiteBudgetList(ArrayList pSiteBudgetList) {
        this.mSiteBudgetList = pSiteBudgetList;
    }


    /**
     *  Sets the subContractor attribute of the SiteMgrDetailForm object
     *
     *@param  pSubContractor  The new SubContractor value
     */
    public void setSubContractor(String pSubContractor) {
        this.mSubContractor = pSubContractor;
    }


    /**
     *  Sets the TaxableIndicator attribute of the SiteMgrDetailForm object
     *
     *@param  pTaxableIndicator  The new TaxableIndicator value
     */
    public void setTaxableIndicator(String pTaxableIndicator) {
        this.mTaxableIndicator = pTaxableIndicator;
    }


    /**
     *  Sets the SiteNumber attribute of the SiteMgrDetailForm object
     *
     *@param  pSiteNumber  The new SiteNumber value
     */
    public void setSiteNumber(String pSiteNumber) {
        this.mSiteNumber = pSiteNumber;
    }
    public void setOldSiteNumber(String pOldSiteNumber) {
        this.mOldSiteNumber = pOldSiteNumber;
    }


    public void setF1Value(String v) {
        this.mF1Value = v;
    }
    public void setF2Value(String v) {
        this.mF2Value = v;
    }
    public void setF3Value(String v) {
        this.mF3Value = v;
    }
    public void setF4Value(String v) {
        this.mF4Value = v;
    }
    public void setF5Value(String v) {
        this.mF5Value = v;
    }
    public void setF6Value(String v) {
        this.mF6Value = v;
    }
    public void setF7Value(String v) {
        this.mF7Value = v;
    }
    public void setF8Value(String v) {
        this.mF8Value = v;
    }
    public void setF9Value(String v) {
        this.mF9Value = v;
    }
    public void setF10Value(String v) {
        this.mF10Value = v;
    }
    public void setEffDate(String v) {
        this.mEffDate = v;
    }
    public void setExpDate(String v) {
        this.mExpDate = v;
    }


    /**
     *  Gets the StoreName attribute of the SiteMgrDetailForm object
     *
     *@return    The StoreName value
     */
    public String getStoreName() {
        return mStoreName;
    }


    /**
     *  Gets the AccountId attribute of the SiteMgrDetailForm object
     *
     *@return    The AccountId value
     */
    public String getAccountId() {
        return mAccountId;
    }


    /**
     *  Gets the AccountName attribute of the SiteMgrDetailForm object
     *
     *@return    The AccountName value
     */
    public String getAccountName() {
        return mAccountName;
    }


    /**
     *  Gets the StoreId attribute of the SiteMgrDetailForm object
     *
     *@return    The StoreId value
     */
    public String getStoreId() {
        return mStoreId;
    }


    /**
     *  Gets the specific SiteBudget object requested
     *
     *@param  index  an <code>int</code> value
     *@return        a <code>SiteBudget</code> value
     */
    public SiteBudget getSiteBudget(int index) {
        return (SiteBudget) mSiteBudgetList.get(index);
    }


    /**
     *  Gets the SiteBudgetList attribute of the SiteMgrDetailForm object
     *
     *@return    An ArrayList of SiteBudget objects
     */
    public ArrayList getSiteBudgetList() {
        return mSiteBudgetList;
    }


    /**
     *  Gets the SubContractor attribute of the SiteMgrDetailForm object
     *
     *@return    The SubContractor value
     */
    public String getSubContractor() {
        return mSubContractor;
    }


    /**
     *  Gets the TaxableIndicator attribute of the SiteMgrDetailForm object
     *
     *@return    The TaxableIndicator value
     */
    public String getTaxableIndicator() {
        return mTaxableIndicator;
    }

    /**
     *  Gets the SiteNumber attribute of the SiteMgrDetailForm object
     *
     *@return    The SiteNumber value
     */
    public String getSiteNumber() {
        return mSiteNumber;
    }
    public String getOldSiteNumber() {
        return mOldSiteNumber;
    }

    public String getF1Value() {
        return mF1Value;
    }
    public String getF2Value() {
        return mF2Value;
    }
    public String getF3Value() {
        return mF3Value;
    }
    public String getF4Value() {
        return mF4Value;
    }
    public String getF5Value() {
        return mF5Value;
    }
    public String getF6Value() {
        return mF6Value;
    }
    public String getF7Value() {
        return mF7Value;
    }
    public String getF8Value() {
        return mF8Value;
    }
    public String getF9Value() {
        return mF9Value;
    }
    public String getF10Value() {
        return mF10Value;
    }
    public String getEffDate() {
      return mEffDate;
    }
    public String getExpDate() {
      return mExpDate;
    }


    /**
     * Set the value of AvailableShipto.
     * @param v  Value to assign to AvailableShipto.
     */
    public void setAvailableShipto(IdVector v) {
        this.mAvailableShipto = v;
    }

    /**
     *  Gets the LawsonShipto attribute of the SiteMgrDetailForm object
     *
     *@return    The AvailableShipto value
     */
    public IdVector getAvailableShipto() {
        return mAvailableShipto;
    }

    /**
     *  <code>reset</code> method, set the search fiels to null.
     *
     *@param  mapping  an <code>ActionMapping</code> value
     *@param  request  a <code>HttpServletRequest</code> value
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        this.setBypassOrderRouting(false);
        this.setConsolidatedOrderWarehouse(false);
        this.setInventoryShopping(false);
        return;
    }


    /**
     *  <code>validate</code> method is a stub.
     *
     *@param  mapping  an <code>ActionMapping</code> value
     *@param  request  a <code>HttpServletRequest</code> value
     *@return          an <code>ActionErrors</code> value
     */
    public ActionErrors validate(ActionMapping mapping,
            HttpServletRequest request) {
        // Validation happens in the logic bean.
        return null;
    }

    /** Getter for property siteReferenceNumber.
     * @return Value of property siteReferenceNumber.
     *
     */
    public String getSiteReferenceNumber() {
        return this.siteReferenceNumber;
    }

    /** Setter for property siteReferenceNumber.
     * @param siteReferenceNumber New value of property siteReferenceNumber.
     *
     */
    public void setSiteReferenceNumber(String siteReferenceNumber) {
        this.siteReferenceNumber = siteReferenceNumber;
    }

    /** Getter for property distSiteReferenceNumber.
     * @return Value of property distSiteReferenceNumber.
     *
     */
    public String getDistSiteReferenceNumber() {
        return this.distSiteReferenceNumber;
    }

    /** Setter for property distSiteReferenceNumber.
     * @param distSiteReferenceNumber New value of property distSiteReferenceNumber.
     *
     */
    public void setDistSiteReferenceNumber(String distSiteReferenceNumber) {
        this.distSiteReferenceNumber = distSiteReferenceNumber;
    }
    public void setInventoryShopping(boolean v) {
	this.mInventoryShopping = v;
    }
    public void setInventoryShoppingStr(String v) {
	if ( v == null && v.length() == 0 ) {
	    this.mInventoryShopping = false;
	}
	else {
	    this.mInventoryShopping = v.equals("on");
	}
    }
    public boolean getInventoryShopping() {
	return this.mInventoryShopping;
    }

    /** Getter for property targetFacility.
     * @return Value of property targetFacility.
     *
     */
    public String getTargetFacilityRank() {
        return this.targetFacilityRank;
    }

    /** Setter for property targetFacility.
     * @param targetFacility New value of property targetFacility.
     *
     */
    public void setTargetFacilityRank(String targetFacilityRank) {
        this.targetFacilityRank = targetFacilityRank;
    }

    /** Getter for property profile.
     * @return Value of property profile.
     *
     */
    public ProfileViewContainer getProfile() {
        return this.profile;
    }

    /** Setter for property profile.
     * @param profile New value of property profile.
     *
     */
    public void setProfile(ProfileViewContainer profile) {
        this.profile = profile;
    }

    /** Getter for property authorizedReShipAccount.
     * @return Value of property authorizedReShipAccount.
     *
     */
    public boolean isAuthorizedReSaleAccount() {
        return this.authorizedReSaleAccount;
    }

    /** Setter for property authorizedReShipAccount.
     * @param authorizedReShipAccount New value of property authorizedReShipAccount.
     *
     */
    public void setAuthorizedReSaleAccount(boolean authorizedReSaleAccount) {
        this.authorizedReSaleAccount = authorizedReSaleAccount;
    }


    /** Getter for property bypassOrderRouting.
     * @return Value of property bypassOrderRouting.
     *
     */
    public boolean isBypassOrderRouting() {
        return this.bypassOrderRouting;
    }

    /** Setter for property bypassOrderRouting.
     * @param bypassOrderRouting New value of property bypassOrderRouting.
     *
     */
    public void setBypassOrderRouting(boolean bypassOrderRouting) {
        this.bypassOrderRouting = bypassOrderRouting;
    }

    /**
     * Getter for property makeShiptoBillto.
     * @return Value of property makeShiptoBillto.
     */
    public boolean isMakeShiptoBillto() {
        return this.makeShiptoBillto;
    }

    /**
     * Setter for property makeShiptoBillto.
     * @param makeShiptoBillto New value of property makeShiptoBillto.
     */
    public void setMakeShiptoBillto(boolean makeShiptoBillto) {
        this.makeShiptoBillto = makeShiptoBillto;
    }

    /**
     * Getter for property consolidatedOrderWarehouse.
     * @return Value of property consolidatedOrderWarehouse.
     */
    public boolean isConsolidatedOrderWarehouse() {
        return this.consolidatedOrderWarehouse;
    }

    /**
     * Getter for property consolidatedOrderWarehouse.
     * @return Value of property consolidatedOrderWarehouse.
     */
    public boolean getConsolidatedOrderWarehouse() {
        return this.consolidatedOrderWarehouse;
    }

    /**
     * Setter for property consolidatedOrderWarehouse.
     * @param makeShiptoBillto New value of property consolidatedOrderWarehouse.
     */
    public void setConsolidatedOrderWarehouse(boolean consolidatedOrderWarehouse) {
        this.consolidatedOrderWarehouse = consolidatedOrderWarehouse;
    }

    /**
     * Getter for property blanketPoNumId.
     * @return Value of property blanketPoNumId.
     */
    public Integer getBlanketPoNumId() {

        return this.blanketPoNumId;
    }

    /**
     * Setter for property blanketPoNumId.
     * @param blanketPoNumId New value of property blanketPoNumId.
     */
    public void setBlanketPoNumId(Integer blanketPoNumId) {

        this.blanketPoNumId = blanketPoNumId;
    }


     /**
     * Getter for property ERPEnabled.
     * @return Value of property ERPEnabled.
     */
    public boolean isERPEnabled() {

        return this.ERPEnabled;
    }

    /**
     * Setter for property ERPEnabled.
     * @param ERPEnabled New value of property ERPEnabled.
     */
    public void setERPEnabled(boolean ERPEnabled) {

        this.ERPEnabled = ERPEnabled;
    }

    private SiteData SiteData;

    /**
     * Get the SiteData value.
     * @return the SiteData value.
     */
    public SiteData getSiteData() {
	return SiteData;
    }

    public PropertyData getSiteProp(int propIdx) {
	PropertyDataVector v = getSiteData().getDataFieldProperties();
	if ( null == v ) v = new PropertyDataVector();
	while ( propIdx >= v.size() ) {
	    v.add(PropertyData.createValue());
	}
	return (PropertyData)v.get(propIdx);
    }

    /**
     * Set the SiteData value.
     * @param newSiteData The new SiteData value.
     */
    public void setSiteData(SiteData newSiteData) {
	this.SiteData = newSiteData;
    }
    
}
