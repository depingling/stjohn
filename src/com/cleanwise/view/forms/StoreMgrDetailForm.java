/**
 *  Title: StoreMgrDetailForm Description: This is the Struts ActionForm class
 *  for user management page. Purpose: Strut support to search for distributors.
 *  Copyright: Copyright (c) 2001 Company: CleanWise, Inc.
 *
 *@author     tbesser
 */

package com.cleanwise.view.forms;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.service.api.value.BusEntityFieldsData;
import com.cleanwise.service.api.value.MessageResourceData;
import com.cleanwise.service.api.value.MessageResourceDataVector;
import java.util.Properties;

/**
 *  Form bean for the store manager page.
 *
 *@author     tbesser
 *@created    August 23, 2001
 */
public final class StoreMgrDetailForm extends Base2DetailForm {

    private String mPrefix;
    private String mLocale;
    private String mDescription;
    private String mContactEmail;
    private String mCustomerEmail;
    private String storeBusinessName;    
    private String storePrimaryWebAddress;
    private Boolean mSkuWorkflowFlag;
    private boolean mCleanwiseOrderNumFlag=false;
    private String mErpSystem;
    private boolean includeAccountNameInSiteAddress=false;
    private boolean allowPONumberByVendor=false;

    /** Holds value of property busEntityFieldsData. */
    private BusEntityFieldsData busEntityFieldsData;

    /**
     * Holds value of property storeMessageResources.
     */
    private MessageResourceDataVector storeMessageResources;

    /**
     * Holds value of property contactUsType.
     */
    private String contactUsType;

    /**
     * Holds value of property callHours.
     */
    private String callHours;

    /**
     * Holds value of property oddRowColor.
     */
    private String oddRowColor;

    /**
     * Holds value of property evenRowColor.
     */
    private String evenRowColor;

    /**
     * Holds value of property autoSkuAssign.
     */
    private boolean autoSkuAssign;

    /**
     * Holds value of property requireExternalSysLogon.
     */
    private boolean requireExternalSysLogon;
 
    
    /**
     * Holds value of property showDistrNotes.
     */
    private boolean showDistrNotes;

   /**
     * Gets the value of mPrefix
     *
     * @return the value of mPrefix
     */
    public String getPrefix() {
	return this.mPrefix;
    }

    /**
     * Sets the value of mPrefix
     *
     * @param argPrefix Value to assign to this.mPrefix
     */
    public void setPrefix(String argPrefix){
	this.mPrefix = argPrefix;
    }

    /**
     * Gets the value of mLocale
     *
     * @return the value of mLocale
     */
    public String getLocale() {
	return this.mLocale;
    }

    /**
     * Sets the value of mLocale
     *
     * @param argLocale Value to assign to this.mLocale
     */
    public void setLocale(String argLocale){
	this.mLocale = argLocale;
    }

    /**
     * Gets the value of mDescription
     *
     * @return the value of mDescription
     */
    public String getDescription() {
	return this.mDescription;
    }

    /**
     * Sets the value of mDescription
     *
     * @param argDescription Value to assign to this.mDescription
     */
    public void setDescription(String argDescription){
	this.mDescription = argDescription;
    }

    /**
     * Gets the value of mContactEmail
     *
     * @return the value of mContactEmail
     */
    public String getContactEmail() {
	return this.mContactEmail;
    }

    /**
     * Sets the value of mContactEmail
     *
     * @param argContactEmail Value to assign to this.mContactEmail
     */
    public void setContactEmail(String argContactEmail){
	this.mContactEmail = argContactEmail;
    }

    /**
     * Gets the value of mCustomerEmail
     *
     * @return the value of mCustomerEmail
     */
    public String getCustomerEmail() {
	return this.mCustomerEmail;
    }

    /**
     * Sets the value of mCustomerEmail
     *
     * @param argCustomerEmail Value to assign to this.mCustomerEmail
     */
    public void setCustomerEmail(String argCustomerEmail){
	this.mCustomerEmail = argCustomerEmail;
    }

    /**
     *  <code>reset</code> method, set the search fiels to null.
     *
     *@param  mapping  an <code>ActionMapping</code> value
     *@param  request  a <code>HttpServletRequest</code> value
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        if(busEntityFieldsData != null){
            busEntityFieldsData.setF1ShowAdmin(false);
            busEntityFieldsData.setF2ShowAdmin(false);
            busEntityFieldsData.setF3ShowAdmin(false);
            busEntityFieldsData.setF4ShowAdmin(false);
            busEntityFieldsData.setF5ShowAdmin(false);
            busEntityFieldsData.setF1ShowRuntime(false);
            busEntityFieldsData.setF2ShowRuntime(false);
            busEntityFieldsData.setF3ShowRuntime(false);
            busEntityFieldsData.setF4ShowRuntime(false);
            busEntityFieldsData.setF5ShowRuntime(false);
        }
        mCleanwiseOrderNumFlag = false;
        autoSkuAssign = false;
        requireExternalSysLogon = false;
        showDistrNotes = false;
        taxableIndicator = false;
        orderProcessingSplitTaxExemptOrders = false;
        includeAccountNameInSiteAddress = false;
        allowPONumberByVendor=false;
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

    /** Getter for property storeBusinessName.
     * @return Value of property storeBusinessName.
     */
    public String getStoreBusinessName() {
        return this.storeBusinessName;
    }
    
    /** Setter for property storeBusinessName.
     * @param storeBusinessName New value of property storeBusinessName.
     */
    public void setStoreBusinessName(String storeBusinessName) {
        this.storeBusinessName = storeBusinessName;
    }
    
    /** Getter for property storePrimaryWebAddress.
     * @return Value of property storePrimaryWebAddress.
     */
    public String getStorePrimaryWebAddress() {
        return this.storePrimaryWebAddress;
    }
    
    /** Setter for property storePrimaryWebAddress.
     * @param storePrimaryWebAddress New value of property storePrimaryWebAddress.
     */
    public void setStorePrimaryWebAddress(String storePrimaryWebAddress) {
        this.storePrimaryWebAddress = storePrimaryWebAddress;
    }

    public Boolean getSkuWorkflowFlag() {
	return this.mSkuWorkflowFlag;
    }

    public void setSkuWorkflowFlag(Boolean argSkuWorkflowFlag){
	this.mSkuWorkflowFlag = argSkuWorkflowFlag;
    }

    public boolean getCleanwiseOrderNumFlag() {
	return this.mCleanwiseOrderNumFlag;
    }
    public void setCleanwiseOrderNumFlag(boolean argCleanwiseOrderNumFlag){
	this.mCleanwiseOrderNumFlag = argCleanwiseOrderNumFlag;
    }
    public String getErpSystem() {
	return this.mErpSystem;
    }
    public void setErpSystem(String argErpSystem){
	this.mErpSystem = argErpSystem;
    }
    
    /** Getter for property busEntityFieldsData.
     * @return Value of property busEntityFieldsData.
     *
     */
    public BusEntityFieldsData getBusEntityFieldsData() {
        return this.busEntityFieldsData;
    }
    
    /** Setter for property busEntityFieldsData.
     * @param busEntityFieldsData New value of property busEntityFieldsData.
     *
     */
    public void setBusEntityFieldsData(BusEntityFieldsData busEntityFieldsData) {
        this.busEntityFieldsData = busEntityFieldsData;
    }

    /**
     * Getter for property storeMessageResources.
     * @return Value of property storeMessageResources.
     */
    public MessageResourceDataVector getStoreMessageResources() {

        return this.storeMessageResources;
    }

    /**
     * Setter for property storeMessageResources.
     * @param storeMessageResources New value of property storeMessageResources.
     */
    public void setStoreMessageResources(MessageResourceDataVector storeMessageResources) {

        this.storeMessageResources = storeMessageResources;
    }

    /**
     * Getter for property contactUsType.
     * @return Value of property contactUsType.
     */
    public String getContactUsType() {

        return this.contactUsType;
    }

    /**
     * Setter for property contactUsType.
     * @param contactUsType New value of property contactUsType.
     */
    public void setContactUsType(String contactUsType) {

        this.contactUsType = contactUsType;
    }

    /**
     * Getter for property callHours.
     * @return Value of property callHours.
     */
    public String getCallHours() {

        return this.callHours;
    }

    /**
     * Setter for property callHours.
     * @param callHours New value of property callHours.
     */
    public void setCallHours(String callHours) {

        this.callHours = callHours;
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
     * Getter for property autoSkuAsign.
     * @return Value of property autoSkuAsign.
     */
    public boolean isAutoSkuAssign()  {

        return this.autoSkuAssign;
    }

    /**
     * Setter for property autoSkuAsign.
     * @param autoSkuAsign New value of property autoSkuAsign.
     */
    public void setAutoSkuAssign(boolean autoSkuAssign)  {

        this.autoSkuAssign = autoSkuAssign;
    }

    /**
     * Getter for property requireExternalSysLogon.
     * @return Value of property requireExternalSysLogon.
     */
    public boolean isRequireExternalSysLogon() {

        return this.requireExternalSysLogon;
    }

    /**
     * Setter for property requireExternalSysLogon.
     * @param requireExternalSysLogon New value of property requireExternalSysLogon.
     */
    public void setRequireExternalSysLogon(boolean requireExternalSysLogon) {

        this.requireExternalSysLogon = requireExternalSysLogon;
    }
    
    
    /**
     * Getter for property showDistrNotes.
     * @return Value of property showDistrNotes.
     */
    public boolean isShowDistrNotes() {
        return this.showDistrNotes;
    }

    /**
     * Setter for property showDistrNotes.
     * @param showDistrNotes New value of property showDistrNotes.
     */
    public void setShowDistrNotes(boolean showDistrNotes) {
        this.showDistrNotes = showDistrNotes;
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

        return this.taxableIndicator;
    }

    /**
     * Setter for property taxableIndicator.
     * @param taxableIndicator New value of property taxableIndicator.
     */
    public void setTaxableIndicator(boolean taxableIndicator) {

        this.taxableIndicator = taxableIndicator;
    }

    /**
     * Holds value of property orderProcessingSplitTaxExemptOrders.
     */
    private boolean orderProcessingSplitTaxExemptOrders;

    /**
     * Getter for property orderProcessingSplitTaxExemptOrders.
     * @return Value of property orderProcessingSplitTaxExemptOrders.
     */
    public boolean isOrderProcessingSplitTaxExemptOrders() {

        return this.orderProcessingSplitTaxExemptOrders;
    }

    /**
     * Setter for property orderProcessingSplitTaxExemptOrders.
     * @param orderProcessingSplitTaxExemptOrders New value of property orderProcessingSplitTaxExemptOrders.
     */
    public void setOrderProcessingSplitTaxExemptOrders(boolean orderProcessingSplitTaxExemptOrders) {

        this.orderProcessingSplitTaxExemptOrders = orderProcessingSplitTaxExemptOrders;
    }

	public boolean isIncludeAccountNameInSiteAddress() {
		return includeAccountNameInSiteAddress;
	}

	public void setIncludeAccountNameInSiteAddress(boolean includeAccountNameInSiteAddress) {
		this.includeAccountNameInSiteAddress = includeAccountNameInSiteAddress;
	}
	
	public boolean isAllowPONumberByVendors() {
		return allowPONumberByVendor;
	}

	public void setAllowPONumberByVendor(boolean allowPONumberByVendor) {
		this.allowPONumberByVendor = allowPONumberByVendor;
	}

}

