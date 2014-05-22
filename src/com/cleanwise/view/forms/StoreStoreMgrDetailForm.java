package com.cleanwise.view.forms;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.cleanwise.service.api.dto.StoreProfileDto;
import com.cleanwise.service.api.value.BusEntityDataVector;
import com.cleanwise.service.api.value.BusEntityFieldsData;
import com.cleanwise.service.api.value.MessageResourceDataVector;
import com.cleanwise.service.api.value.RefCdDataVector;
import com.cleanwise.service.api.value.StoreProfileDataVector;

/**
 * @author Alexander Chikin
 * Date: 28.09.2006
 * Time: 5:22:19
 * Form bean for the store manager page.
 * Changed by scher 5/19/2009
 */
public class StoreStoreMgrDetailForm  extends Base2DetailForm {

    private String mPrefix;
    private String mPrefixNew;
    private String mLocale;
    private String mDescription;
    private String mContactEmail;
    private String mCustomerEmail;
    private String mDefaultEmail;
    private String storeBusinessName;
    private String storePrimaryWebAddress;
    private Boolean mSkuWorkflowFlag;
    private boolean mCleanwiseOrderNumFlag=false;
    private String mErpSystem;
    private boolean includeAccountNameInSiteAddress=false;
    private boolean allowPONumberByVendor=false;
    private String workOrderEmailAddress;
    private boolean budgetThresholdFl;
    private boolean allowSpecPermissionItemsMgt;
    private BusEntityDataVector childStores;
    private String googleAnalyticsId;
    private String userNameMaxSize;
    private boolean allowMixedCategoryAndItemUnderSameParent;

    /** Holds value of property busEntityFieldsData. */
    private BusEntityFieldsData busEntityFieldsData;


    private BusEntityFieldsData masterItemFieldsData = BusEntityFieldsData.createValue();


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
    private String selectedResourceLocale="";
    private String resMessageShowType="All";
    private String nameSearchMessage="";
    private Hashtable messageResourcesDefaultValue=new Hashtable();
    private boolean allowAssetManagement;
    private boolean orderGuideNotReqd;

    private boolean equalCostAndPrice;
    private String pendingOrderNotification;

    /**
     * Holds value of property requireErpAccountNumber
     */
    private boolean requireErpAccountNumber; // SVC: new

    private boolean displayDistributorAccountReferenceNumber;
    private boolean displayDistributorSiteReferenceNumber;
    private boolean parentStoreFlag = false;
    private String parentStoreId = "";
    private boolean stageUnmatched = false;

    private boolean useXiPay;
    
    //email template properties
    private String mOrderConfirmationEmailTemplate = "";
    private String mShippingNotificationEmailTemplate = "";
    private String mPendingApprovalEmailTemplate = "";
    private String mRejectedOrderEmailTemplate = "";
    private String mModifiedOrderEmailTemplate = "";
    private RefCdDataVector mEmailTemplateChoices = null;
    
    //alternate UI information
    private String mAlternateUI = "";
    
    private StoreProfileDto storeProfile;
    
    //Store Datasource name
    private String storeDatasource = "";

    /**
	 * @return the storeProfile
	 */
	public final StoreProfileDto getStoreProfile() {
		return storeProfile;
	}

	/**
	 * @param storeProfile the storeProfile to set
	 */
	public final void setStoreProfile(StoreProfileDto storeProfile) {
		this.storeProfile = storeProfile;
	}

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
      * Gets the value of mPrefixNew
      *
      * @return the value of mPrefixNew
      */
     public String getPrefixNew() {
     return this.mPrefixNew;
     }

    /**
     * Sets the value of mPrefixNew
     *
     * @param argPrefixNew Value to assign to this.mPrefixNew
     */
    public void setPrefixNew(String argPrefixNew){
    this.mPrefixNew = argPrefixNew;
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
    	if(argContactEmail!=null) {
    this.mContactEmail = argContactEmail.trim();
    	}
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
    	if(argCustomerEmail!=null) {
    this.mCustomerEmail = argCustomerEmail.trim();
    	}
    }


    public String getDefaultEmail() {
    	return this.mDefaultEmail;
    }

    public void setDefaultEmail(String pDefaultEmail) {
    	if(pDefaultEmail!=null) {
    	this.mDefaultEmail = pDefaultEmail.trim();
    	}
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
        if (masterItemFieldsData != null) {
            masterItemFieldsData.setF1ShowAdmin(false);
            masterItemFieldsData.setF2ShowAdmin(false);
            masterItemFieldsData.setF3ShowAdmin(false);
            masterItemFieldsData.setF4ShowAdmin(false);
            masterItemFieldsData.setF5ShowAdmin(false);
            masterItemFieldsData.setF6ShowAdmin(false);
            masterItemFieldsData.setF7ShowAdmin(false);
            masterItemFieldsData.setF8ShowAdmin(false);
            masterItemFieldsData.setF9ShowAdmin(false);
            masterItemFieldsData.setF10ShowAdmin(false);
            masterItemFieldsData.setF11ShowAdmin(false);
            masterItemFieldsData.setF12ShowAdmin(false);
            masterItemFieldsData.setF13ShowAdmin(false);
            masterItemFieldsData.setF14ShowAdmin(false);
            masterItemFieldsData.setF15ShowAdmin(false);

            masterItemFieldsData.setF1ShowRuntime(false);
            masterItemFieldsData.setF2ShowRuntime(false);
            masterItemFieldsData.setF3ShowRuntime(false);
            masterItemFieldsData.setF4ShowRuntime(false);
            masterItemFieldsData.setF5ShowRuntime(false);
            masterItemFieldsData.setF6ShowRuntime(false);
            masterItemFieldsData.setF7ShowRuntime(false);
            masterItemFieldsData.setF8ShowRuntime(false);
            masterItemFieldsData.setF9ShowRuntime(false);
            masterItemFieldsData.setF10ShowRuntime(false);
            masterItemFieldsData.setF11ShowRuntime(false);
            masterItemFieldsData.setF12ShowRuntime(false);
            masterItemFieldsData.setF13ShowRuntime(false);
            masterItemFieldsData.setF14ShowRuntime(false);
            masterItemFieldsData.setF1ShowRuntime(false);

        }
        mCleanwiseOrderNumFlag = false;
        autoSkuAssign = false;
        requireExternalSysLogon = false;
        showDistrNotes = false;
        taxableIndicator = false;
        orderProcessingSplitTaxExemptOrders = false;
        includeAccountNameInSiteAddress = false;
        allowAssetManagement = false;
        orderGuideNotReqd = false;
        allowPONumberByVendor = false;
        equalCostAndPrice = false;

        requireErpAccountNumber = false;

        displayDistributorAccountReferenceNumber = false;
        displayDistributorSiteReferenceNumber = false;
        budgetThresholdFl = false;
        allowSpecPermissionItemsMgt = false;
        parentStoreFlag = false;
        parentStoreId = "";
        stageUnmatched = false;
        childStores = null;

        useXiPay = false;
        mOrderConfirmationEmailTemplate = "";
        mShippingNotificationEmailTemplate = "";
        mPendingApprovalEmailTemplate = "";
        mRejectedOrderEmailTemplate = "";
        mModifiedOrderEmailTemplate = "";
        mEmailTemplateChoices = null;
        mAlternateUI = "";
        storeDatasource = "";
        allowMixedCategoryAndItemUnderSameParent = false;
        
        storeProfile = new StoreProfileDto();
        storeProfile.setProfileNameDisplay(false);
        storeProfile.setProfileNameEdit(false);
        storeProfile.setLanguageDisplay(false);
        storeProfile.setLanguageEdit(false);
        storeProfile.setCountryDisplay(false);
        storeProfile.setCountryEdit(false);
        storeProfile.setContactAddressDisplay(false);
        storeProfile.setContactAddressEdit(false);
        storeProfile.setPhoneDisplay(false);
        storeProfile.setPhoneEdit(false);
        storeProfile.setMobileDisplay(false);
        storeProfile.setMobileEdit(false);
        storeProfile.setFaxDisplay(false);
        storeProfile.setFaxEdit(false);
        storeProfile.setEmailDisplay(false);
        storeProfile.setEmailEdit(false);
        storeProfile.setChangePassword(false);
        storeProfile.setStoreLanguages(new String[0]);
        
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


    public BusEntityFieldsData getMasterItemFieldsData() {
        return this.masterItemFieldsData;
    }

    public void setMasterItemFieldsData(BusEntityFieldsData v) {
        this.masterItemFieldsData = v;
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
     * @param autoSkuAssign New value of property autoSkuAsign.
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

    public String getSelectedResourceLocale() {
        return selectedResourceLocale;
    }
    public void setSelectedResourceLocale(String resourceLocale) {
        this.selectedResourceLocale = resourceLocale;
    }

    public String getResMessageShowType() {
        return resMessageShowType;
    }

    public void setResMessageShowType(String resMessageShowType) {
        this.resMessageShowType= resMessageShowType;
    }

    public void setNameSearchMessage(String nameSearchMessage) {
        this.nameSearchMessage = nameSearchMessage;
    }

    public String getNameSearchMessage() {
        return nameSearchMessage;

    }

    public void setMessageResourcesDefaultValue(Hashtable messageResourcesDefaultValue) {
        this.messageResourcesDefaultValue = messageResourcesDefaultValue;
    }

    public Hashtable getMessageResourcesDefaultValue() {
        return messageResourcesDefaultValue;
    }

    public boolean isAllowAssetManagement() {
        return allowAssetManagement;
    }

    public void setAllowAssetManagement(boolean allowAssetManagement) {
        this.allowAssetManagement = allowAssetManagement;
    }

    public boolean isRequireErpAccountNumber() {

        return requireErpAccountNumber;
    }

    public void setRequireErpAccountNumber(boolean requireErpAccountNumber) {

        this.requireErpAccountNumber = requireErpAccountNumber;
    }

    public boolean isOrderGuideNotReqd() {
        return orderGuideNotReqd;
    }

    public void setOrderGuideNotReqd(boolean orderGuideNotReqd) {
        this.orderGuideNotReqd = orderGuideNotReqd;
    }

    public boolean isAllowPONumberByVendor() {
        return allowPONumberByVendor;
    }

    public void setAllowPONumberByVendor(boolean allowPONumberByVendor) {
        this.allowPONumberByVendor = allowPONumberByVendor;
    }

    public boolean isEqualCostAndPrice() {
        return equalCostAndPrice;
    }

    public void setEqualCostAndPrice(boolean equalCostAndPrice) {
        this.equalCostAndPrice = equalCostAndPrice;
    }

    public String getWorkOrderEmailAddress() {
        return workOrderEmailAddress;
    }

    public void setWorkOrderEmailAddress(String workOrderEmailAddress) {
        this.workOrderEmailAddress = workOrderEmailAddress;
    }

    public String getPendingOrderNotification() {
        return pendingOrderNotification;
    }

    public void setPendingOrderNotification(String pendingOrderNotification) {
        this.pendingOrderNotification = pendingOrderNotification;
    }

	public boolean isDisplayDistributorAccountReferenceNumber() {
    	return displayDistributorAccountReferenceNumber;
    }

	public void setDisplayDistributorAccountReferenceNumber(
            boolean displayDistributorAccountReferenceNumber) {
    	this.displayDistributorAccountReferenceNumber = displayDistributorAccountReferenceNumber;
    }

	public boolean isDisplayDistributorSiteReferenceNumber() {
    	return displayDistributorSiteReferenceNumber;
    }

	public void setDisplayDistributorSiteReferenceNumber(
            boolean displayDistributorSiteReferenceNumber) {
    	this.displayDistributorSiteReferenceNumber = displayDistributorSiteReferenceNumber;
    }

    public boolean isBudgetThresholdFl() {
        return budgetThresholdFl;
    }

    public void setBudgetThresholdFl(boolean budgetThresholdFl) {
        this.budgetThresholdFl = budgetThresholdFl;
    }

    public void setAllowSpecPermissionItemsMgt(boolean pAllowSpecPermissionItemsMgt) {
        this.allowSpecPermissionItemsMgt = pAllowSpecPermissionItemsMgt;
    }

    public boolean getAllowSpecPermissionItemsMgt() {
        return allowSpecPermissionItemsMgt;
    }

    public boolean isParentStore() {
        return parentStoreFlag;
    }

    public void setParentStore(boolean parentStoreFlag) {
        this.parentStoreFlag = parentStoreFlag;
    }

    public String getParentStoreId() {
        return parentStoreId;
    }

    public void setParentStoreId(String parentStoreId) {
        this.parentStoreId = parentStoreId;
    }

    public boolean isStageUnmatched() {
        return stageUnmatched;
    }

    public void setStageUnmatched(boolean stageUnmatched) {
        this.stageUnmatched = stageUnmatched;
    }

    public BusEntityDataVector getChildStores() {
        return childStores;
    }

    public void setChildStores(BusEntityDataVector childStores) {
        this.childStores = childStores;
    }

    public boolean isUseXiPay() {
        return useXiPay;
    }

    public void setUseXiPay(boolean useXiPay) {
        this.useXiPay = useXiPay;
    }

    public String getGoogleAnalyticsId() {
        return googleAnalyticsId;
    }

    public void setGoogleAnalyticsId(String googleAnalyticsId) {
        this.googleAnalyticsId = googleAnalyticsId;
    }

	public String getOrderConfirmationEmailTemplate() {
		return mOrderConfirmationEmailTemplate;
	}

	public void setOrderConfirmationEmailTemplate(String orderConfirmationEmailTemplate) {
		mOrderConfirmationEmailTemplate = orderConfirmationEmailTemplate;
	}

	public String getShippingNotificationEmailTemplate() {
		return mShippingNotificationEmailTemplate;
	}

	public void setShippingNotificationEmailTemplate(String shippingNotificationEmailTemplate) {
		mShippingNotificationEmailTemplate = shippingNotificationEmailTemplate;
	}

	public String getPendingApprovalEmailTemplate() {
		return mPendingApprovalEmailTemplate;
	}

	public void setPendingApprovalEmailTemplate(String pendingApprovalEmailTemplate) {
		mPendingApprovalEmailTemplate = pendingApprovalEmailTemplate;
	}

	public String getRejectedOrderEmailTemplate() {
		return mRejectedOrderEmailTemplate;
	}

	public void setRejectedOrderEmailTemplate(String rejectedOrderEmailTemplate) {
		mRejectedOrderEmailTemplate = rejectedOrderEmailTemplate;
	}

	public String getModifiedOrderEmailTemplate() {
		return mModifiedOrderEmailTemplate;
	}

	public void setModifiedOrderEmailTemplate(String modifiedOrderEmailTemplate) {
		mModifiedOrderEmailTemplate = modifiedOrderEmailTemplate;
	}

	public RefCdDataVector getEmailTemplateChoices() {
		if (mEmailTemplateChoices == null) {
			mEmailTemplateChoices = new RefCdDataVector();
		}
		return mEmailTemplateChoices;
	}

	public void setEmailTemplateChoices(RefCdDataVector emailTemplateChoices) {
		mEmailTemplateChoices = emailTemplateChoices;
	}

	public String getAlternateUI() {
		return mAlternateUI;
	}

	public void setAlternateUI(String alternateUI) {
		this.mAlternateUI = alternateUI;
	}

	public void setUserNameMaxSize(String userNameMaxSize) {
		this.userNameMaxSize = userNameMaxSize;
	}

	public String getUserNameMaxSize() {
		return userNameMaxSize;
	}
	
	public String getStoreDatasource() {
		return storeDatasource;
	}

	public void setStoreDatasource(String storeDatasource) {
		this.storeDatasource = storeDatasource;
	}
	
	public void setAllowMixedCategoryAndItemUnderSameParent(boolean pAllowMixedCategoryAndItemUnderSameParent) {
        this.allowMixedCategoryAndItemUnderSameParent = pAllowMixedCategoryAndItemUnderSameParent;
    }

    public boolean getAllowMixedCategoryAndItemUnderSameParent() {
        return allowMixedCategoryAndItemUnderSameParent;
    }
}
