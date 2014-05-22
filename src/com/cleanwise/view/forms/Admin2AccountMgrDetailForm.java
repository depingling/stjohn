package com.cleanwise.view.forms;

import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.wrapper.UiPageViewWrapper;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


public class  Admin2AccountMgrDetailForm  extends  Base2DetailForm  {


    private AccountData mAccountData;

    private boolean mInit = false;
    private boolean isClone=false;
    private boolean isFirstUpdate=false;

    /*********************  Instance Variables ****************************/
    private OrderRoutingDescViewVector mAccountOrderRoutingList = null;
    public static final String ORDER_PROCESSING_PAGE ="order_processing" ;
    private FreightHandlerViewVector mfhFilter;
    private String _freightHandlerId="";
    private DistributorDataVector mDistFilter=null;
    private String mDistributorId="";
    private CatalogDataVector mCatalogFilter;
    private String mCatalogId="";
    private String mContractId="";

    private int cloneAccoundId=0;
    private ArrayList cloneFiscalCalendar=null;
    private String mFiscalEffDate;
    private String mFiscalYearString="";
    private String mIsSaveFiscalCal="";


    private String erpSystemCode;
    private String controlAccountData="YES";
    private Integer mNumberOfBudgetPeriods;

    public Admin2AccountMgrDetailForm(){}

    private String mStoreId;
    private String mStoreName;

    private String mAccountNumber;
    private String mOrderMinimum;
    private String mCreditLimit;
    private String mCreditRating;
    private String mBillingAddress1;
    private String mBillingAddress2;
    private String mBillingAddress3;
    private String mBillingCity;
    private String mBillingState;
    private String mBillingPostalCode;
    private String mBillingCountry;
    private String mOrderPhone = "";
    private String mOrderFax = "";
    private String mComments = "", mOrderGuideNote = "", mSkuTag = "";
    private String mFreightChargeType;
    private String mShowDistInventory;

    private String mRebatePersent = "";
    private String mRebateEffDate = "";
    private String mScheduleCutoffDays = "";

    private String mWeightThreshold = "";
    private String mPriceThreshold = "";
    private String mContractThreshold = "";

    private String mAccountFolder = "";
    private String mAccountFolderNew = "";
    private String mTimeZoneCd = "";

    private String mCustomerEmail = "";
    private String mContactUsCCEmail = "";
    private String mDefaultEmail = "";

    private LocateForm locateForm=null;
    private String _cartReminderInterval="";

    //inventory properties
    private String _invPOSuffix="";
    private String _invOGListUI="";
    private String _invMissingNotification="";
    private String _invCheckPlacedOrder="";

    private String mShopUIType="";
    private String mContactInformationType = "";

    private UiPageViewWrapper uiPage;

    public UiPageViewWrapper getUiPage() {
        return uiPage;
    }

    public void setUiPage(UiPageViewWrapper uiPage) {
        this.uiPage = uiPage;
    }

    public LocateForm getLocateForm() {
          return locateForm;
      }

      public void setLocateForm(LocateForm locateForm) {
          this.locateForm = locateForm;
      }

    private OrderRoutingData mNewOrderRoutingData = OrderRoutingData.createValue();
    private OrderRoutingDescView mOrderRoutingDescTestResult = null;
    private AccountOrderPipeline orderPipeline = new AccountOrderPipeline(null, 0);
    private String orderRouteTestOrderZip = "";
    private String mOrderManagerEmails = "";
    private String mInvReminderEmailSub = "";
    private String mInvReminderEmailMsg = "";
    private String mNotifyOrderEmailGenerator = "";
    private String mConfirmOrderEmailGenerator = "";
    private String mRejectOrderEmailGenerator = "";
    private String mPendingApprovEmailGenerator = "";
    private String mOrderConfirmationEmailTemplate = "";
    private String mShippingNotificationEmailTemplate = "";
    private String mPendingApprovalEmailTemplate = "";
    private String mRejectedOrderEmailTemplate = "";
    private String mModifiedOrderEmailTemplate = "";

    /** Holds value of property maxItemWeight. */
    private String maxItemWeight;

    /** Holds value of property maxItemCubicSize. */
    private String maxItemCubicSize;

    /** Holds value of property reSaleAccountErpNumber. */
    private String reSaleAccountErpNumber;


    /** Holds value of property EDI_SHIP_TO_PREFIX. */
    private String mEdiShipToPrefix;

    /** Holds value of property targetMarginStr. */
    private String targetMarginStr;

    /** Holds value of property dataFieldProperties. */
    private List dataFieldProperties;

    /**
     * Holds value of property customerSystemApprovalCd.
     */
    private String customerSystemApprovalCd;
    /**
     * Holds value of property DISTR_PO_TYPE:
     */
    private String distrPoType;

    private String pdfOrderStatusClass;
    private String pdfOrderClass;

    private Boolean allowCreditCard;
    private Boolean mAllowSetWorkOrderPoNumber;
    private Boolean mWorkOrderPoNumberIsRequired;
    private Boolean mAllowBuyWorkOrderParts;
    private Boolean mUserAssignedAssetNumber;
    private Boolean _invLedgerSwitch;

    private Boolean mCrcShop;
    private Boolean mUsePhysicalInventory;
    private Boolean mShowScheduledDelivery;
    private Boolean mAllowOrderConsolidation;
    private Boolean mShowDistSkuNum;
    private Boolean mShowDistDeliveryDate;
    private Boolean mAllowModernShopping;
    private Boolean mAllowSiteLLC;
    private Boolean mAllowOrderInvItems;
    private Boolean mShowInvCartTotal;
    private Boolean mShowMyShoppingLists;
    private Boolean mShowExpressOrder;
    private Boolean mShowSPL;
    private Boolean mAddServiceFee;
    private Boolean mHoldPO;
    private Boolean mAllowChangePassword;

    private Boolean taxableIndicator;
    private Boolean authorizedForResale;
    private Boolean mMakeShipToBillTo;
    private Boolean customerRequestPoAllowed;

    private Boolean mAllowReorder;
    private Boolean connectionCustomer;

    private Boolean mModifyQtyBy855;
    private Boolean createOrderItemsBy855;
    private Boolean createOrderBy855;

    public void setOrderRoutingDescTestResult(OrderRoutingDescView pOrderRoutingDescTestResult){
        mOrderRoutingDescTestResult = pOrderRoutingDescTestResult;
    }

    public OrderRoutingDescView getOrderRoutingDescTestResult(){
      return mOrderRoutingDescTestResult;
    }

    public void setNewOrderRoutingData(OrderRoutingData pNewOrderRoutingData){
        mNewOrderRoutingData = pNewOrderRoutingData;
    }

    public OrderRoutingData getNewOrderRoutingData(){
        return mNewOrderRoutingData;
    }

    public void setAccountOrderRoutingList(OrderRoutingDescViewVector pAccountOrderRouting) {
        mAccountOrderRoutingList = pAccountOrderRouting;
    }

    public OrderRoutingDescViewVector getAccountOrderRoutingList() {
        if ( mAccountOrderRoutingList == null ) {
            mAccountOrderRoutingList = new OrderRoutingDescViewVector();
        }
        return mAccountOrderRoutingList;
    }

    public OrderRoutingDescView getAccountOrderRouting(int idx) {
        if (mAccountOrderRoutingList == null) {
            mAccountOrderRoutingList = new OrderRoutingDescViewVector();
        }
        while (idx >= mAccountOrderRoutingList.size()) {
            mAccountOrderRoutingList.add(OrderRoutingDescView.createValue());
        }
        return (OrderRoutingDescView) mAccountOrderRoutingList.get(idx);
    }

    public AccountOrderPipeline getOrderPipeline() {
        return orderPipeline;
    }
    public void setOrderPipeline(AccountOrderPipeline data) {
        orderPipeline = data;
    }

    /**
     *  Sets the StoreName attribute of the StoreAccountMgrDetailForm object
     *
     *@param  pStoreName  The new StoreName value
     */
    public void setStoreName(String pStoreName) {
        this.mStoreName = pStoreName;
    }


    /**
     *  Sets the StoreId attribute of the StoreAccountMgrDetailForm object
     *
     *@param  pStoreId  The new StoreId value
     */
    public void setStoreId(String pStoreId) {
        this.mStoreId = pStoreId;
    }


    /**
     *  Sets the AccountNumber attribute of the StoreAccountMgrDetailForm object
     *
     *@param  pAccountNumber  The new AccountNumber value
     */
    public void setAccountNumber(String pAccountNumber) {
        this.mAccountNumber = pAccountNumber;
    }


    /**
     *  Sets the OrderMinimum attribute of the StoreAccountMgrDetailForm object
     *
     *@param  pOrderMinimum  The new OrderMinimum value
     */
    public void setOrderMinimum(String pOrderMinimum) {
        this.mOrderMinimum = pOrderMinimum;
    }

    /**
     *  Sets the CreditLimit attribute of the StoreAccountMgrDetailForm object
     *
     *@param  pCreditLimit  The new CreditLimit value
     */
    public void setCreditLimit(String pCreditLimit) {
        this.mCreditLimit = pCreditLimit;
    }

    /**
     *  Sets the CreditRating attribute of the StoreAccountMgrDetailForm object
     *
     *@param  pCreditRating  The new CreditRating value
     */
    public void setCreditRating(String pCreditRating) {
        this.mCreditRating = pCreditRating;
    }


    /**
     *  Sets the Billing Address1 attribute of the StoreAccountMgrDetailForm object
     *
     *@param  pBillingAddress1  The new Billing Address1 value
     */
    public void setBillingAddress1(String pBillingAddress1) {
        this.mBillingAddress1 = pBillingAddress1;
    }


    /**
     *  Sets the Billing Address2 attribute of the StoreAccountMgrDetailForm object
     *
     *@param  pBillingAddress2  The new Billing Address2 value
     */
    public void setBillingAddress2(String pBillingAddress2) {
        this.mBillingAddress2 = pBillingAddress2;
    }


    /**
     *  Sets the Billing Address3 attribute of the StoreAccountMgrDetailForm object
     *
     *@param  pBillingAddress3  The new Billing Address3 value
     */
    public void setBillingAddress3(String pBillingAddress3) {
        this.mBillingAddress3 = pBillingAddress3;
    }


    /**
     *  Sets the Billing City attribute of the StoreAccountMgrDetailForm object
     *
     *@param  pBillingCity  The new Billing City value
     */
    public void setBillingCity(String pBillingCity) {
        this.mBillingCity = pBillingCity;
    }


    /**
     *  Sets the Billing State attribute of the StoreAccountMgrDetailForm object
     *
     *@param  pBillingState  The new Billing State value
     */
    public void setBillingState(String pBillingState) {
        this.mBillingState = pBillingState;
    }


    /**
     *  Sets the Billing PostalCode attribute of the StoreAccountMgrDetailForm object
     *
     *@param  pBillingPostalCode  The new Billing PostalCode value
     */
    public void setBillingPostalCode(String pBillingPostalCode) {
        this.mBillingPostalCode = pBillingPostalCode;
    }


    /**
     *  Sets the Billing Country attribute of the StoreAccountMgrDetailForm object
     *
     *@param  pBillingCountry  The new Billing Country value
     */
    public void setBillingCountry(String pBillingCountry) {
        this.mBillingCountry = pBillingCountry;
    }


    /**sets the current value for the freight type code */
    public void setFreightChargeType(String pFreightChargeType){
        this.mFreightChargeType = pFreightChargeType;
    }

    /**sets the current value for the ShowDistInventory */
    public void setShowDistInventory(String pShowDistInventory){
        this.mShowDistInventory = pShowDistInventory;
    }

    /**
     *  Gets the StoreName attribute of the StoreAccountMgrDetailForm object
     *
     *@return    The StoreName value
     */
    public String getStoreName() {
        return mStoreName;
    }


    /**
     *  Gets the StoreId attribute of the StoreAccountMgrDetailForm object
     *
     *@return    The StoreId value
     */
    public String getStoreId() {
        return mStoreId;
    }


    /**
     *  Gets the AccountNumber attribute of the StoreAccountMgrDetailForm object
     *
     *@return    The AccountNumber value
     */
    public String getAccountNumber() {
        return mAccountNumber;
    }


    /**
     *  Gets the OrderMinimum attribute of the StoreAccountMgrDetailForm object
     *
     *@return    The OrderMinimum value
     */
    public String getOrderMinimum() {
        return mOrderMinimum;
    }

    /**
     *  Gets the CreditRating attribute of the StoreAccountMgrDetailForm object
     *
     *@return    The CreditRating value
     */
    public String getCreditRating() {
        return mCreditRating;
    }


    /**
     *  Gets the CreditLimit attribute of the StoreAccountMgrDetailForm object
     *
     *@return    The CreditLimit value
     */
    public String getCreditLimit() {
        return mCreditLimit;
    }


    /**
     *  Gets the Billing Address1 attribute of the StoreAccountMgrDetailForm object
     *
     *@return    The BillingAddress1 value
     */
    public String getBillingAddress1() {
        return mBillingAddress1;
    }


    /**
     *  Gets the Billing Address2 attribute of the StoreAccountMgrDetailForm object
     *
     *@return    The BillingAddress2 value
     */
    public String getBillingAddress2() {
        return mBillingAddress2;
    }


    /**
     *  Gets the Billing Address3 attribute of the StoreAccountMgrDetailForm object
     *
     *@return    The BillingAddress3 value
     */
    public String getBillingAddress3() {
        return mBillingAddress3;
    }


    /**
     *  Gets the Billing City attribute of the StoreAccountMgrDetailForm object
     *
     *@return    The BillingCity value
     */
    public String getBillingCity() {
        return mBillingCity;
    }


    /**
     *  Gets the Billing State attribute of the StoreAccountMgrDetailForm object
     *
     *@return    The BillingState value
     */
    public String getBillingState() {
        return mBillingState;
    }


    /**
     *  Gets the Billing PostalCode attribute of the StoreAccountMgrDetailForm object
     *
     *@return    The BillingPostalCode value
     */
    public String getBillingPostalCode() {
        return mBillingPostalCode;
    }


    /**
     *  Gets the Billing Country attribute of the StoreAccountMgrDetailForm object
     *
     *@return    The BillingCountry value
     */
    public String getBillingCountry() {
        return mBillingCountry;
    }

    /**gets the current value freight charge type code */
    public String getFreightChargeType(){
        return mFreightChargeType;
    }

    /**gets the current value ShowDistInventory */
    public String getShowDistInventory(){
        return mShowDistInventory;
    }

    /**sets the current value for the shipto counter, only has meaning if create
     erpShipto is set to true*/
    public void setMakeShipToBillTo(Boolean pMakeShipToBillTo){
        this.mMakeShipToBillTo = pMakeShipToBillTo;
    }
    public void setShowScheduledDelivery(Boolean v){
        this.mShowScheduledDelivery = v;
    }
    public Boolean getShowScheduledDelivery(){
        return this.mShowScheduledDelivery;
    }
    public void setAllowOrderConsolidation(Boolean v){
        this.mAllowOrderConsolidation = v;
    }
    public Boolean getAllowOrderConsolidation(){
        return this.mAllowOrderConsolidation;
    }

    public void setShowDistSkuNum(Boolean v){
        this.mShowDistSkuNum = v;
    }
    public Boolean getShowDistSkuNum(){
        return this.mShowDistSkuNum;
    }

    public void setShowDistDeliveryDate(Boolean v){
        this.mShowDistDeliveryDate = v;
    }
    public Boolean getShowDistDeliveryDate(){
        return this.mShowDistDeliveryDate;
    }

    public void setRebatePersent(String v){
        this.mRebatePersent = v;
    }
    public String getRebatePersent(){
        return this.mRebatePersent;
    }

    public void setRebateEffDate(String v){
        this.mRebateEffDate = v;
    }
    public String getRebateEffDate(){
        return this.mRebateEffDate;
    }

    public void setScheduleCutoffDays(String v){
        this.mScheduleCutoffDays = v;
    }
    public String getScheduleCutoffDays(){
        return this.mScheduleCutoffDays;
    }

    public void setAccountFolder(String v) {
      this.mAccountFolder = v;
    }
    public String getAccountFolder() {
      return this.mAccountFolder;
    }

    public void setAccountFolderNew(String v) {
      this.mAccountFolderNew = v;
    }
    public String getAccountFolderNew() {
      return this.mAccountFolderNew;
    }

    public void setTimeZoneCd(String v) {
      this.mTimeZoneCd = v;
    }
    public String getTimeZoneCd() {
      return this.mTimeZoneCd;
    }
    /*
    public void setWorkflowEmail(String v) {
      this.mWorkflowEmail = v;
    }
    public String getWorkflowEmail() {
      return this.mWorkflowEmail;
    }
    */

    public void setCustomerEmail(String v) {
      this.mCustomerEmail = v;
    }
    public String getCustomerEmail() {
      return this.mCustomerEmail;
    }

    public void setContactUsCCEmail(String v) {
        this.mContactUsCCEmail = v;
      }
      public String getContactUsCCEmail() {
        return this.mContactUsCCEmail;
      }

    public void setDefaultEmail(String v) {
    	this.mDefaultEmail = v;
    }
     
    public String getDefaultEmail() {
    	return this.mDefaultEmail;
    }
    public void setShowSPL(Boolean v) {
      this.mShowSPL = v;
    }
    public Boolean getShowSPL(){
      return this.mShowSPL;
    }

    public void setAddServiceFee(Boolean v) {
    	this.mAddServiceFee = v;
    }
    public Boolean getAddServiceFee(){
    	return this.mAddServiceFee;
    }

    public void setHoldPO(Boolean v) {
      this.mHoldPO = v;
    }
    public Boolean getHoldPO(){
      return this.mHoldPO;
    }

    /**
     *  <code>reset</code> method, set the search fiels to null.
     *
     *@param  mapping  an <code>ActionMapping</code> value
     *@param  request  a <code>HttpServletRequest</code> value
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {

        authorizedForResale = null;
        customerRequestPoAllowed = null;
        mItemsToAddToInventory = new String[0];
        mSelectedInventoryItems= new String[0];
        mCrcShop= null;
        mMakeShipToBillTo= null;
        taxableIndicator = null;
        mModifyQtyBy855= null;
        createOrderItemsBy855= null;
        createOrderBy855= null;
        mAllowModernShopping=false;
        mAllowSiteLLC= null;
        mShowExpressOrder= null;
        mAllowOrderInvItems= null;
        mAllowReorder= null;
        mShowInvCartTotal= null;
        mShowMyShoppingLists= null;
        mAllowChangePassword= null;
        mShowScheduledDelivery= null;
        mAllowOrderConsolidation= null;
        mShowDistSkuNum= null;
        mShowDistDeliveryDate= null;
        mAccountFolder = "";
        mAccountFolderNew = "";
        mTimeZoneCd = "";
        mCustomerEmail = "";
        mShowSPL= null;
        mAddServiceFee= null;
        mHoldPO= null;
        _invPOSuffix="";
        _invLedgerSwitch=false;
        mAllowSetWorkOrderPoNumber= null;
        mWorkOrderPoNumberIsRequired= null;
        mAllowBuyWorkOrderParts = null;
        mUserAssignedAssetNumber= null;
        mContactInformationType = "";
        allowCreditCard= null;
        mUsePhysicalInventory= null;
        connectionCustomer= null;
        mNumberOfBudgetPeriods = null;
        mFiscalCalenderData = getFiscalCalUpdate();
        mOrderConfirmationEmailTemplate = "";
        mShippingNotificationEmailTemplate = "";
        mPendingApprovalEmailTemplate = "";
        mRejectedOrderEmailTemplate = "";
        mModifiedOrderEmailTemplate = "";
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

    /**
     *  Sets the OrderFax attribute
     *
     *@param  mOrderFax  The new OrderFax value
     */
    public void setOrderFax(String mOrderFax) {
        this.mOrderFax = mOrderFax;
    }


    /**
     *  Sets the OrderPhone attribute
     *
     *@param  mOrderPhone  The new Phone value
     */
    public void setOrderPhone(String mOrderPhone) {
        this.mOrderPhone = mOrderPhone;
    }

    /**
     *  Gets the OrderFax attribute
     *
     *@return    The OrderFax value
     */
    public String getOrderFax() {
        if ( null == mOrderFax ) {
            return "";
        }
        return mOrderFax;
    }


    /**
     *  Gets the OrderPhone attribute
     *
     *@return    The OrderPhone value
     */
    public String getOrderPhone() {
        if ( null == mOrderPhone ) {
            return "";
        }
        return mOrderPhone;
    }

    /**
     *  Sets the Comments attribute
     *
     *@param  mComments  The new Phone value
     */
    public void setComments(String mComments) {
        this.mComments = mComments;
    }

    /**
     *  Gets the Comments attribute
     *
     *@return    The Comments value
     */
    public String getComments() {
        if ( null == mComments ) {
            return "";
        }
        return mComments;
    }

    public void setOrderGuideNote(String pVal) {
        this.mOrderGuideNote = pVal;
    }

    public String getOrderGuideNote() {
        if ( null == mOrderGuideNote ) {
            return "";
        }
        return mOrderGuideNote;
    }

    public void setSkuTag(String pVal) {
        this.mSkuTag = pVal;
    }

    public String getSkuTag() {
        if ( null == mSkuTag ) {
            return "";
        }
        return mSkuTag;
    }


    /**
     *  Sets the CRC Shopping attribute of the StoreAccountMgrDetailForm object
     *
     *@param  pCrcShop  The new CRC Shop value
     */
    public void setCrcShop(Boolean pCrcShop) {
        this.mCrcShop = pCrcShop;
    }

    /**
     *  Gets the the flag whether to let the crc user shop for the account
     *
     *@return    The CrcShop value
     */
    public Boolean getCrcShop() {
        return mCrcShop;
    }

    /**gets the current value for the shipto counter, only has meaning if create
     erpShipto is set to true*/
    public Boolean getMakeShipToBillTo(){
        return mMakeShipToBillTo;
    }
    //Jd begin
//    private String mWeightThreshold = "";
    /**
     *  Sets the WeightThreshold attribute
     *@param  mVal  The new WeightThreshold value
     */
    public void setWeightThreshold(String mVal) { this.mWeightThreshold = mVal; }
    /**
     *  Gets the WeightThreshold attribute
     *@return    The WeightThreshold value
     */
    public String getWeightThreshold() {
        if ( null == mWeightThreshold ) {
            return "";
        }
        return mWeightThreshold;
    }
//    private String mPriceThreshold = "";
    /**
     *  Sets the PriceThreshold attribute
     *@param  mVal  The new PriceThreshold value
     */
    public void setPriceThreshold(String mVal) { this.mPriceThreshold = mVal; }
    /**
     *  Gets the PriceThreshold attribute
     *@return    The PriceThreshold value
     */
    public String getPriceThreshold() {
        if ( null == mPriceThreshold ) return "";
        return mPriceThreshold;
    }
//    private String mContractThreshold = "";
    /**
     *  Sets the ContractThreshold attribute
     *@param  mVal  The new ContractThreshold value
     */
    public void setContractThreshold(String mVal) { this.mContractThreshold = mVal; }
    /**
     *  Gets the ContractThreshold attribute
     *@return    The ContractThreshold value
     */
    public String getContractThreshold() {
        if ( null == mContractThreshold ) return "";
        return mContractThreshold;
    }

    //Jd end


    /** Getter for property orderRouteTestOrderZip.
     * @return Value of property orderRouteTestOrderZip.
     *
     */
    public String getOrderRouteTestOrderZip() {
        return this.orderRouteTestOrderZip;
    }

    /** Setter for property orderRouteTestOrderZip.
     * @param orderRouteTestOrderZip New value of property orderRouteTestOrderZip.
     *
     */
    public void setOrderRouteTestOrderZip(String orderRouteTestOrderZip) {
        this.orderRouteTestOrderZip = orderRouteTestOrderZip;
    }

    /** Getter for property maxItemWeight.
     * @return Value of property maxItemWeight.
     *
     */
    public String getMaxItemWeight() {
        return this.maxItemWeight;
    }

    /** Setter for property maxItemWeight.
     * @param maxItemWeight New value of property maxItemWeight.
     *
     */
    public void setMaxItemWeight(String maxItemWeight) {
        this.maxItemWeight = maxItemWeight;
    }

    /** Getter for property maxItemCubicSize.
     * @return Value of property maxItemCubicSize.
     *
     */
    public String getMaxItemCubicSize() {
        return this.maxItemCubicSize;
    }

    /** Setter for property maxItemCubicSize.
     * @param maxItemCubicSize New value of property maxItemCubicSize.
     *
     */
    public void setMaxItemCubicSize(String maxItemCubicSize) {
        this.maxItemCubicSize = maxItemCubicSize;
    }

    /** Getter for property mOrderManagerEmails.
     * @return Value of property mOrderManagerEmails.
     *
     */
    public String getOrderManagerEmails() {
        return this.mOrderManagerEmails;
    }

    /** Setter for property mOrderManagerEmails.
     * @param orderManagerEmails New value of property mOrderManagerEmails.
     *
     */
    public void setOrderManagerEmails(String orderManagerEmails) {
        this.mOrderManagerEmails = orderManagerEmails;
    }

    /*
     * Getter and setter properties for Subject and Message
     * of auto-generate emails for the inventory/scheduled cart
     * to remind users to enter on-hand qtys
     */
    public String getInvReminderEmailSub(){
    	return this.mInvReminderEmailSub;
    }

    public void setInvReminderEmailSub(String invReminderEmailSub){
    	this.mInvReminderEmailSub = invReminderEmailSub;
    }

    public String getInvReminderEmailMsg(){
    	return this.mInvReminderEmailMsg;
    }

    public void setInvReminderEmailMsg(String invReminderEmailMsg){
    	this.mInvReminderEmailMsg = invReminderEmailMsg;
    }

    public String getNotifyOrderEmailGenerator() {
        return this.mNotifyOrderEmailGenerator;
    }

    public void setNotifyOrderEmailGenerator(String notifyOrderEmailGenerator) {
        this.mNotifyOrderEmailGenerator = notifyOrderEmailGenerator;
    }

    public String getConfirmOrderEmailGenerator() {
        return this.mConfirmOrderEmailGenerator;
    }

    public void setConfirmOrderEmailGenerator(String confirmOrderEmailGenerator) {
        this.mConfirmOrderEmailGenerator = confirmOrderEmailGenerator;
    }

    public String getRejectOrderEmailGenerator() {
        return this.mRejectOrderEmailGenerator;
    }

    public void setRejectOrderEmailGenerator(String rejectOrderEmailGenerator) {
        this.mRejectOrderEmailGenerator = rejectOrderEmailGenerator;
    }

    public String getPendingApprovEmailGenerator() {
        return this.mPendingApprovEmailGenerator;
    }

    public void setPendingApprovEmailGenerator(String pendingApprovEmailGenerator) {
        this.mPendingApprovEmailGenerator = pendingApprovEmailGenerator;
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

    /** Getter for property reSaleAccountErpNumber.
     * @return Value of property reSaleAccountErpNumber.
     *
     */
    public String getReSaleAccountErpNumber() {
        return this.reSaleAccountErpNumber;
    }

    /** Setter for property reSaleAccountErpNumber.
     * @param reSaleAccountErpNumber New value of property reSaleAccountErpNumber.
     *
     */
    public void setReSaleAccountErpNumber(String reSaleAccountErpNumber) {
        this.reSaleAccountErpNumber = reSaleAccountErpNumber;
    }

    /** Getter for property authorizedForResale.
     * @return Value of property authorizedForResale.
     *
     */
    public Boolean getAuthorizedForResale() {
        return this.authorizedForResale;
    }

    /** Setter for property authorizedForResale.
     * @param authorizedForResale New value of property authorizedForResale.
     *
     */
    public void setAuthorizedForResale(Boolean authorizedForResale) {
        this.authorizedForResale = authorizedForResale;
    }

    /** Getter for property EdiShipToPrefix.
     * @return Value of property EdiShipToPrefix.
     *
     */
    public String getEdiShipToPrefix() {
        return this.mEdiShipToPrefix;
    }

    public void setEdiShipToPrefix(String val) {
        this.mEdiShipToPrefix = val;
    }

    /** Getter for property targetMarginStr.
     * @return Value of property targetMarginStr.
     *
     */
    public String getTargetMarginStr() {
        return this.targetMarginStr;
    }

    /** Setter for property targetMarginStr.
     * @param targetMarginStr New value of property targetMarginStr.
     *
     */
    public void setTargetMarginStr(String targetMarginStr) {
        this.targetMarginStr = targetMarginStr;
    }

    /** Getter for property dataFieldProperties.
     * @return Value of property dataFieldProperties.
     *
     */
    public List getDataFieldProperties() {
        return this.dataFieldProperties;
    }

    /** Setter for property dataFieldProperties.
     * @param dataFieldProperties New value of property dataFieldProperties.
     *
     */
    public void setDataFieldProperties(List dataFieldProperties) {
        this.dataFieldProperties = dataFieldProperties;
    }

    public void setDataFieldProperty(int indx, BusEntityFieldDataElement dataField) {
        int len = dataFieldProperties.size();
        while(len <= indx){
            dataFieldProperties.add(null);
        }
        dataFieldProperties.add(indx,dataField);
    }


    public BusEntityFieldDataElement getDataFieldProperty(int indx) {
        if(indx > dataFieldProperties.size()){
            return null;
        }else{
            return (BusEntityFieldDataElement) dataFieldProperties.get(indx);
        }
    }

    /**
     * Getter for property customerSystemApprovalCd.
     * @return Value of property customerSystemApprovalCd.
     */
    public String getCustomerSystemApprovalCd() {
        return this.customerSystemApprovalCd;
    }

    /**
     * Setter for property customerSystemApprovalCd.
     * @param customerSystemApprovalCd New value of property customerSystemApprovalCd.
     */
    public void setCustomerSystemApprovalCd(String customerSystemApprovalCd) {
        this.customerSystemApprovalCd = customerSystemApprovalCd;
    }


    public void setAccountData(AccountData v) {
        mAccountData = v;
    }

    public AccountData getAccountData() {
        return mAccountData;
    }

    public void setInventoryItemsAvailable(ArrayList v) {
        mInventoryItemsAvailable = v;
    }
    ArrayList    mInventoryItemsAvailable = null;
    public ArrayList getInventoryItemsAvailable() {
        if (null == mInventoryItemsAvailable) {
            mInventoryItemsAvailable = new ArrayList();
        }
        return mInventoryItemsAvailable;
    }

    private String[] mItemsToAddToInventory = null;
    public void setItemsToAddToInventory(String[] pValue) {
        mItemsToAddToInventory = pValue;
    }
    public String[] getItemsToAddToInventory() {
        return mItemsToAddToInventory;
    }

    private String[] mSelectedInventoryItems= null;
    public void setSelectedInventoryItems(String[] pValue) {
        mSelectedInventoryItems = pValue;
    }
    public String[] getSelectedInventoryItems() {
        return mSelectedInventoryItems;
    }

    /**
     * Holds value of property runtimeDisplayOrderItemActionTypes.
     */
    private String[] runtimeDisplayOrderItemActionTypes;
    /**
     * Holds value of property purchaseOrderAccountName.
     */
    private String purchaseOrderAccountName;

    /**
     * Holds value of property budgetTypeCd.
     */
    private String budgetTypeCd;

    /**
     * Holds value of property glTransformationType.
     */
    private String glTransformationType;

    /**
     * Holds value of property distributorAccountRefNum.
     */
    private String distributorAccountRefNum;

    /**
     * Holds value of property faxBackConfirm.
     */
    private String faxBackConfirm;

    private String mRushOrderCharge = "0";


    public String getRushOrderCharge() {
        return mRushOrderCharge;
    }

    private String mAutoOrderFactor = "0.500001";

    public String getAutoOrderFactor() {
        return mAutoOrderFactor;
    }

    /**
     * Getter for property runtimeDisplayOrderItemActionTypes.
     * @return Value of property runtimeDisplayOrderItemActionTypes.
     */
    public String[] getRuntimeDisplayOrderItemActionTypes() {
        return this.runtimeDisplayOrderItemActionTypes;
    }

    /**
     * Setter for property runtimeDisplayOrderItemActionTypes.
     * @param runtimeDisplayOrderItemActionTypes New value of property runtimeDisplayOrderItemActionTypes.
     */
    public void setRuntimeDisplayOrderItemActionTypes(String[] runtimeDisplayOrderItemActionTypes) {
        this.runtimeDisplayOrderItemActionTypes = runtimeDisplayOrderItemActionTypes;
    }

    /**
     * Getter for property purchaseOrderAccountName.
     * @return Value of property purchaseOrderAccountName.
     */
    public String getPurchaseOrderAccountName() {
        return this.purchaseOrderAccountName;
    }

    /**
     * Setter for property purchaseOrderAccountName.
     * @param purchaseOrderAccountName New value of property purchaseOrderAccountName.
     */
    public void setPurchaseOrderAccountName(String purchaseOrderAccountName) {
        this.purchaseOrderAccountName = purchaseOrderAccountName;
    }

    /**
     * Getter for property budgetTypeCd.
     * @return Value of property budgetTypeCd.
     */
    public String getBudgetTypeCd() {
        return this.budgetTypeCd;
    }

    /**
     * Setter for property budgetTypeCd.
     * @param budgetTypeCd New value of property budgetTypeCd.
     */
    public void setBudgetTypeCd(String budgetTypeCd) {
        this.budgetTypeCd = budgetTypeCd;
    }

    /**
     * Getter for property glTransformationType.
     * @return Value of property glTransformationType.
     */
    public String getGlTransformationType() {
        return this.glTransformationType;
    }

    /**
     * Setter for property glTransformationType.
     * @param glTransformationType New value of property glTransformationType.
     */
    public void setGlTransformationType(String glTransformationType) {
        this.glTransformationType = glTransformationType;
    }

    /**
     * Getter for property distributorAccountRefNum.
     * @return Value of property distributorAccountRefNum.
     */
    public String getDistributorAccountRefNum() {
        return this.distributorAccountRefNum;
    }

    /**
     * Setter for property glTransformationType.
     * @param distributorAccountRefNum New value of property glTransformationType.
     */
    public void setDistributorAccountRefNum(String distributorAccountRefNum) {
        this.distributorAccountRefNum = distributorAccountRefNum;
    }


    /**
     * Getter for property customerRequestPoAllowed.
     * @return Value of property customerRequestPoAllowed.
     */
    public Boolean getCustomerRequestPoAllowed() {
        return this.customerRequestPoAllowed;
    }

    /**
     * Setter for property customerRequestPoAllowed.
     * @param customerRequestPoAllowed New value of property customerRequestPoAllowed.
     */
    public void setCustomerRequestPoAllowed(Boolean customerRequestPoAllowed) {
        this.customerRequestPoAllowed = customerRequestPoAllowed;
    }

    /**
     * Getter for property faxBackConfirm.
     * @return Value of property faxBackConfirm.
     */
    public String getFaxBackConfirm() {

        return this.faxBackConfirm;
    }

    /**
     * Setter for property faxBackConfirm.
     * @param faxBackConfirm New value of property faxBackConfirm.
     */
    public void setFaxBackConfirm(String faxBackConfirm) {

        this.faxBackConfirm = faxBackConfirm;
    }
    public void setRushOrderCharge(String v) {
        if ( v == null ) v = "0";
        mRushOrderCharge = v;
    }

    public void setAutoOrderFactor(String v) {   	
    	if(v==null){
    		v="0.500001";
    	}
        mAutoOrderFactor = v;
    }

      /**
     * Getter for property taxableIndicator.
     * @return Value of property taxableIndicator.
     */
    public Boolean getTaxableIndicator() {
        return this.taxableIndicator;
    }

    /**
     * Setter for property taxableIndicator.
     * @param taxableIndicator New value of property taxableIndicator.
     */
    public void setTaxableIndicator(Boolean taxableIndicator) {
        this.taxableIndicator = taxableIndicator;
    }

   /**
     * Getter for property mModifyQtyBy855.
     * @return Value of property mModifyQtyBy855.
     */
    public Boolean getModifyQtyBy855() {

        return this.mModifyQtyBy855;
    }

    /**
     * Setter for property mModifyQtyBy855.
     * @param pModifyQtyBy855 New value of property mModifyQtyBy855.
     */
    public void setModifyQtyBy855(Boolean pModifyQtyBy855) {
        this.mModifyQtyBy855 = pModifyQtyBy855;
    }


    public Boolean getAllowChangePassword() {
        return mAllowChangePassword;
    }
    public void setAllowChangePassword(Boolean pVal) {
        mAllowChangePassword = pVal;
    }

    private List shoppingOptions;
  private String faqLink;
  // shopping options for this account
    public List getShoppingOptions() {
        return this.shoppingOptions;
    }

    public void setShoppingOptions(List shoppingOptions) {
        this.shoppingOptions = shoppingOptions;
    }

    public void setShoppingOption
            (int indx,ShoppingOptionsData shoppingOption) {
        int len = shoppingOptions.size();
        while(len <= indx){
            shoppingOptions.add(null);
        }
        shoppingOptions.add(indx,shoppingOption);
    }


    public ShoppingOptionsData getShoppingOption(int indx) {
        if(indx > shoppingOptions.size()){
            return null;
        }else{
            return (ShoppingOptionsData) shoppingOptions.get(indx);
        }
    }


    public LocateForm.Locate getLocateForm(String page) throws Exception {
      Hashtable form = locateForm.getForm();
      LocateForm.Locate loc= (LocateForm.Locate) form.get(page);
        if(loc!=null)  return loc;
        else throw new Exception("Locate does not exist");
    }
   // this block hold   methods setter and getter value id  selcted in LocateStoreForms
    public void setFreightHandlerId(String fhId) {
        this._freightHandlerId=fhId;
    }
    public String getFreightHandlerId() {
        return _freightHandlerId;
    }
    //Selected value from LocateSToreFhForm
    public FreightHandlerViewVector getFhFilter() {
        return mfhFilter;
    }
    public void setFhFilter(FreightHandlerViewVector fhFilter) {
        this.mfhFilter = fhFilter;
    }
    //Selected value from LocateSToreDistributorForm
    public DistributorDataVector getDistFilter() {
        return mDistFilter;
    }
    public void setDistFilter(DistributorDataVector mDistFilter) {
        this.mDistFilter = mDistFilter;
    }
    // this block hold   methods setter and getter value id  selcted in LocateStoreForms
    public void setDistributorId(String distId) {
        this.mDistributorId=distId;

    }
    public String getDistributorId() { return mDistributorId;   }
    //Selected value from LocateSToreCatalogForm
    public CatalogDataVector getCatalogFilter() {
        return mCatalogFilter;
    }
    public void setCatalogFilter(CatalogDataVector mCatalogFilter) {
        this.mCatalogFilter = mCatalogFilter;
    }
   // this block hold   methods setter and getter value id  selcted in LocateStoreForms
    public void setCatalogId(String catalogId) {
        this.mCatalogId = catalogId;
    }
    public String getCatalogId() {return mCatalogId;
    }
     public String getContractId() { return mContractId; }

    public Boolean isClone() {
        return isClone;
    }
    public void  setClone(Boolean status){
        this.isClone=status;
    }

    public int getCloneAccoundId() {
        return cloneAccoundId;
    }

    public void setCloneAccoundId(int cloneAccoundId) {
        this.cloneAccoundId = cloneAccoundId;
    }

    public void setCloneFiscalCalendar(ArrayList cloneFiscalCalendar) {
        this.cloneFiscalCalendar = cloneFiscalCalendar;
    }

    public ArrayList getCloneFiscalCalendar() {
        return cloneFiscalCalendar;
    }

    public Boolean isFirstUpdate() {
        return isFirstUpdate;
    }

    public void setFirstUpdate(Boolean firstUpdateClone) {
        this.isFirstUpdate = firstUpdateClone;
    }

    public void setErpSystemCode(String erpSystemCd) {
        this.erpSystemCode = erpSystemCd;
    }

    public String getErpSystemCode() {
        return erpSystemCode;
    }

    public String getControlAccountData() {
        return controlAccountData;
    }

    public void setControlAccountData(String controlAccountData) {
        this.controlAccountData = controlAccountData;
    }

    public String getInvPOSuffix() {
        return _invPOSuffix;
    }

    public void setInvPOSuffix(String invPOSuffix) {
        this._invPOSuffix = invPOSuffix;
    }

    public Boolean getInvLedgerSwitch() {
        return _invLedgerSwitch;
    }

    public void setInvLedgerSwitch(Boolean invLedgerSwitch) {
        this._invLedgerSwitch = invLedgerSwitch;
    }

   public String getInvOGListUI() {
        return _invOGListUI;
    }

    public void setInvOGListUI(String str) {
        this._invOGListUI = str;
    }

   public String getInvCheckPlacedOrder() {
        return _invCheckPlacedOrder;
    }

    public void setInvCheckPlacedOrder(String invCheckPlacedOrder) {
        this._invCheckPlacedOrder = invCheckPlacedOrder;
    }

    public String getInvMissingNotification() {
        return _invMissingNotification;
    }

    public void setInvMissingNotification(String invMissingNotification) {
        this._invMissingNotification = invMissingNotification;
    }

    public String getCartReminderInterval() {
        return _cartReminderInterval;
    }

    public void setCartReminderInterval(String cartReminderInterval) {
        this._cartReminderInterval = cartReminderInterval;
    }

    public String getDistrPoType() {
        return distrPoType;
    }

    public void setDistrPoType(String distrPoType) {
        this.distrPoType = distrPoType;
    }

	public Boolean getCreateOrderBy855() {
		return createOrderBy855;
	}

	public void setCreateOrderBy855(Boolean createOrderBy855) {
		this.createOrderBy855 = createOrderBy855;
	}

	public Boolean getCreateOrderItemsBy855() {
		return createOrderItemsBy855;
	}

  public String getFaqLink() {
    return faqLink;
  }

  public void setCreateOrderItemsBy855(Boolean createOrderItemsBy855) {
		this.createOrderItemsBy855 = createOrderItemsBy855;
	}

  public void setFaqLink(String faqLink) {
    this.faqLink = faqLink;
  }

  public Boolean getAllowModernShopping() {
        return mAllowModernShopping;
    }

    public void setAllowModernShopping(Boolean allowModernShopping) {
        this.mAllowModernShopping = allowModernShopping;
    }

    public Boolean getAllowSiteLLC() {
        return mAllowSiteLLC;
    }

    public void setAllowSiteLLC(Boolean allowSiteLLC) {
        this.mAllowSiteLLC = allowSiteLLC;
    }

    public Boolean getShowExpressOrder() {
        return mShowExpressOrder;
    }

    public void setShowExpressOrder(Boolean showExpressOrder) {
        this.mShowExpressOrder = showExpressOrder;
    }

    public Boolean getAllowOrderInvItems() {
        return mAllowOrderInvItems;
    }

    public void setAllowOrderInvItems(Boolean allowOrderInvItems) {
        this.mAllowOrderInvItems = allowOrderInvItems;
    }

    public Boolean getShowInvCartTotal() {
        return mShowInvCartTotal;
    }

    public void setShowInvCartTotal(Boolean showInvCartTotal) {
        this.mShowInvCartTotal = showInvCartTotal;
    }

    public Boolean getShowMyShoppingLists() {
        return mShowMyShoppingLists;
    }

    public void setShowMyShoppingLists(Boolean showMyShoppingLists) {
        this.mShowMyShoppingLists = showMyShoppingLists;
    }

  public Boolean getAllowReorder() {
        return mAllowReorder;
    }

  public void setAllowReorder(Boolean pVal) {
        this.mAllowReorder = pVal;
    }

    /**
    * sets the current value for the <code>mAllowSetWorkOrderPoNumber</code> property
    * @param pAllowSetWorkOrderPoNumber
    */
    public void setAllowSetWorkOrderPoNumber(Boolean pAllowSetWorkOrderPoNumber){
        this.mAllowSetWorkOrderPoNumber = pAllowSetWorkOrderPoNumber;
    }

    /**
    * returns the current value of the <code>mAllowSetWorkOrderPoNumber</code> property
    * @return
    */
    public Boolean getAllowSetWorkOrderPoNumber(){
        return this.mAllowSetWorkOrderPoNumber;
    }

    /**
    * sets the current value for the <code>mWorkOrderPoNumberIsRequired</code> property
    * @param mWorkOrderPoNumberIsRequired
    */
    public void setWorkOrderPoNumberIsRequired(Boolean mWorkOrderPoNumberIsRequired){
        this.mWorkOrderPoNumberIsRequired = mWorkOrderPoNumberIsRequired;
    }

    /**
    * returns the current value of the <code>mmWorkOrderPoNumberIsRequired</code> property
    * @return
    */
    public Boolean getWorkOrderPoNumberIsRequired(){
        return this.mWorkOrderPoNumberIsRequired;
    }

    public void setShopUIType(String shopUIType) {
        this.mShopUIType = shopUIType;
    }


    public String getShopUIType() {
        return mShopUIType;
    }

    /**
    * sets the current value for the <code>mAllowBuyWorkOrderParts</code> property
    * @param pAllowBuyWorkOrderParts
    */
    public void setAllowBuyWorkOrderParts(Boolean pAllowBuyWorkOrderParts){
        this.mAllowBuyWorkOrderParts = pAllowBuyWorkOrderParts;
    }

    /**
    * returns the current value of the <code>mAllowBuyWorkOrderParts</code> property
    * @return
    */
    public Boolean getAllowBuyWorkOrderParts(){
        return this.mAllowBuyWorkOrderParts;
    }

    public Boolean getUserAssignedAssetNumber() {
        return mUserAssignedAssetNumber;
    }

    public void setUserAssignedAssetNumber(Boolean userAssignedAssetNumber) {
        this.mUserAssignedAssetNumber = userAssignedAssetNumber;
    }
    
    public String getPdfOrderStatusClass() {
        return pdfOrderStatusClass;
    }

    public void setPdfOrderStatusClass(String v) {
        pdfOrderStatusClass = v;
    }

    public String getPdfOrderClass() {
        return pdfOrderClass;
    }

    public void setPdfOrderClass(String v) {
        pdfOrderClass = v;
    }

    public void setContactInformationType(String pContactInformationType){
        mContactInformationType = pContactInformationType;
    }

    public String getContactInformationType(){
        return mContactInformationType;
    }

    public void setAllowCreditCard(Boolean v) {
      this.allowCreditCard = v;
    }

  public void setConnectionCustomer(Boolean connectionCustomer) {
    this.connectionCustomer = connectionCustomer;
  }

  public Boolean getAllowCreditCard(){
      return this.allowCreditCard;
    }

  public Boolean getConnectionCustomer() {
    return connectionCustomer;
  }

  public Boolean getUsePhysicalInventory() {
        return mUsePhysicalInventory;
    }

    public void setUsePhysicalInventory(Boolean usePhysicalInventory) {
        mUsePhysicalInventory = usePhysicalInventory;
    }

    public Integer getNumberOfBudgetPeriods() {
        return mNumberOfBudgetPeriods;
    }

    public void setNumberOfBudgetPeriods(Integer numberOfBudgetPeriods) {
        this.mNumberOfBudgetPeriods = numberOfBudgetPeriods;
    }
    
    public Integer getAccountId(){
    	return Integer.valueOf(mAccountData.getAccountId());
    }

    public void init() {
        mInit = true;
    }

    public boolean isInit() {
        return mInit;
    }

	public String getFiscalEffDate() {
        if (mFiscalEffDate != null) {
            return mFiscalEffDate;
        }
        java.util.Date d = mFiscalCalenderData.getFiscalCalender().getEffDate();
        String dstring = "";
        if ( null != d  ) {
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(d);
            dstring = dstring +
                (calendar.get(Calendar.MONTH) + 1)  + "/" +
                calendar.get(Calendar.DAY_OF_MONTH) + "/" +
                calendar.get(Calendar.YEAR );
         }
        return dstring;
	}

	public void setFiscalEffDate(String mFiscalEffDate) {
		this.mFiscalEffDate = mFiscalEffDate;
        java.text.DateFormat formatter = new java.text.SimpleDateFormat("MM/dd/yyyy");
        try {
            formatter.setLenient(false);
            Date date = (Date)formatter.parse(mFiscalEffDate);
            mFiscalCalenderData.getFiscalCalender().setEffDate(date);
        } catch (Exception e) {
            mFiscalCalenderData.getFiscalCalender().setEffDate(null);
        }
	}

	public String getFiscalYearString() {
        if ( null == mFiscalCalenderData ) {
            mFiscalCalenderData = getFiscalCalUpdate();
        }
//        return mFiscalCalenderData.getFiscalCalender().getFiscalYear() >= 0 ? String
//                .valueOf(mFiscalCalenderData.getFiscalCalender().getFiscalYear()) : "";

        return this.mFiscalYearString;
	}

	public void setFiscalYearString(String mFiscalYearString) {
		this.mFiscalYearString = mFiscalYearString;
	}

	public String getIsSaveFiscalCal() {
		return mIsSaveFiscalCal;
	}

	public void setIsSaveFiscalCal(String mIsSaveFiscalCal) {
		this.mIsSaveFiscalCal = mIsSaveFiscalCal;
	}

    private FiscalCalenderView mFiscalCalenderData;
    public FiscalCalenderView getFiscalCalUpdate() {
        if ( null == mFiscalCalenderData ) {
            FiscalCalenderView fcd = FiscalCalenderView.createValue();
            FiscalCalenderData fiscalCalender = FiscalCalenderData.createValue();
            fiscalCalender.setFiscalCalenderId(-1);
            fiscalCalender.setPeriodCd(RefCodeNames.BUDGET_PERIOD_CD.MONTHLY);
            fiscalCalender.setShortDesc("Admin Default Cal");
            //GregorianCalendar cal = new GregorianCalendar();
//            fcd.setFiscalYear(cal.get(Calendar.YEAR));
//            fcd.setEffDate(new java.util.Date());
            FiscalCalenderDetailDataVector defaultCalDetails = new FiscalCalenderDetailDataVector();

            if (mNumberOfBudgetPeriods == null || mNumberOfBudgetPeriods == 0) {
                defaultCalDetails.add(new FiscalCalenderDetailData(0, -1, 1, "1/1", new Date(), "Admin Default Cal", new Date(), "Admin Default Cal"));
                defaultCalDetails.add(new FiscalCalenderDetailData(0, -1, 2, "2/1", new Date(), "Admin Default Cal", new Date(), "Admin Default Cal"));
                defaultCalDetails.add(new FiscalCalenderDetailData(0, -1, 3, "3/1", new Date(), "Admin Default Cal", new Date(), "Admin Default Cal"));
                defaultCalDetails.add(new FiscalCalenderDetailData(0, -1, 4, "4/1", new Date(), "Admin Default Cal", new Date(), "Admin Default Cal"));
                defaultCalDetails.add(new FiscalCalenderDetailData(0, -1, 5, "5/1", new Date(), "Admin Default Cal", new Date(), "Admin Default Cal"));
                defaultCalDetails.add(new FiscalCalenderDetailData(0, -1, 6, "6/1", new Date(), "Admin Default Cal", new Date(), "Admin Default Cal"));
                defaultCalDetails.add(new FiscalCalenderDetailData(0, -1, 7, "7/1", new Date(), "Admin Default Cal", new Date(), "Admin Default Cal"));
                defaultCalDetails.add(new FiscalCalenderDetailData(0, -1, 8, "8/1", new Date(), "Admin Default Cal", new Date(), "Admin Default Cal"));
                defaultCalDetails.add(new FiscalCalenderDetailData(0, -1, 9, "9/1", new Date(), "Admin Default Cal", new Date(), "Admin Default Cal"));
                defaultCalDetails.add(new FiscalCalenderDetailData(0, -1, 10, "10/1", new Date(), "Admin Default Cal", new Date(), "Admin Default Cal"));
                defaultCalDetails.add(new FiscalCalenderDetailData(0, -1, 11, "11/1", new Date(), "Admin Default Cal", new Date(), "Admin Default Cal"));
                defaultCalDetails.add(new FiscalCalenderDetailData(0, -1, 12, "12/1", new Date(), "Admin Default Cal", new Date(), "Admin Default Cal"));
                defaultCalDetails.add(new FiscalCalenderDetailData(0, -1, 13, null, new Date(), "Admin Default Cal", new Date(), "Admin Default Cal"));
            } else {
                for (int i = 0; i < mNumberOfBudgetPeriods; i++) {
                    defaultCalDetails.add(new FiscalCalenderDetailData(0, -1, (i + 1), null, new Date(), "Admin Default Cal", new Date(), "Admin Default Cal"));
                }
            }
            fcd.setFiscalCalender(fiscalCalender);
            fcd.setFiscalCalenderDetails(defaultCalDetails);

            mFiscalCalenderData = fcd;
        }
        return mFiscalCalenderData;
    }

    public void setFiscalCalUpdate(FiscalCalenderView v) {
        mFiscalCalenderData = v;
    }

    public void setFiscalBusEntityId(String pBusEntityId) {
        mFiscalCalenderData.getFiscalCalender().setBusEntityId(Integer.parseInt(pBusEntityId));
    }
    public String getFiscalBusEntityId() {
        return String.valueOf(mFiscalCalenderData.getFiscalCalender().getBusEntityId());
    }
    
}
