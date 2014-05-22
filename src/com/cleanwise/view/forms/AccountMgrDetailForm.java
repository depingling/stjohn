/**
 *  Title: AccountMgrDetailForm Description: This is the Struts ActionForm class
 *  for user management page. Purpose: Strut support to search for distributors.
 *  Copyright: Copyright (c) 2001 Company: CleanWise, Inc.
 *
 *@author     tbesser
 */

package com.cleanwise.view.forms;

import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.value.*;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 *  Form bean for the account manager page.
 *
 *@author     tbesser
 *@created    August 23, 2001
 */
public final class AccountMgrDetailForm extends Base2DetailForm {

    private OrderRoutingDescViewVector mAccountOrderRoutingList = null;
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
    private boolean mCrcShop = false;
    private String mFreightChargeType;
    private boolean mMakeShipToBillTo = false;
    private boolean mShowScheduledDelivery = false;
    private boolean mAllowOrderConsolidation = false;
    private boolean mShowDistSkuNum = false;
    private String mRebatePersent = "";
    private String mRebateEffDate = "";
    private String mScheduleCutoffDays = "";
    //Jd begin
    private String mWeightThreshold = "";
    private String mPriceThreshold = "";
    private String mContractThreshold = "";
    //Jd end
    
    private OrderRoutingData mNewOrderRoutingData = OrderRoutingData.createValue();
    private OrderRoutingDescView mOrderRoutingDescTestResult = null;
    private AccountOrderPipeline orderPipeline = new AccountOrderPipeline(null, 0);
    private String orderRouteTestOrderZip = "";
    private String mOrderManagerEmails = "";
    
    /** Holds value of property maxItemWeight. */
    private String maxItemWeight;
    
    /** Holds value of property maxItemCubicSize. */
    private String maxItemCubicSize;
    
    /** Holds value of property reSaleAccountErpNumber. */
    private String reSaleAccountErpNumber;
    
    /** Holds value of property authorizedForResale. */
    private boolean authorizedForResale;

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
     *  Sets the StoreName attribute of the AccountMgrDetailForm object
     *
     *@param  pStoreName  The new StoreName value
     */
    public void setStoreName(String pStoreName) {
        this.mStoreName = pStoreName;
    }


    /**
     *  Sets the StoreId attribute of the AccountMgrDetailForm object
     *
     *@param  pStoreId  The new StoreId value
     */
    public void setStoreId(String pStoreId) {
        this.mStoreId = pStoreId;
    }


    /**
     *  Sets the AccountNumber attribute of the AccountMgrDetailForm object
     *
     *@param  pAccountNumber  The new AccountNumber value
     */
    public void setAccountNumber(String pAccountNumber) {
        this.mAccountNumber = pAccountNumber;
    }


    /**
     *  Sets the OrderMinimum attribute of the AccountMgrDetailForm object
     *
     *@param  pOrderMinimum  The new OrderMinimum value
     */
    public void setOrderMinimum(String pOrderMinimum) {
        this.mOrderMinimum = pOrderMinimum;
    }

    /**
     *  Sets the CreditLimit attribute of the AccountMgrDetailForm object
     *
     *@param  pCreditLimit  The new CreditLimit value
     */
    public void setCreditLimit(String pCreditLimit) {
        this.mCreditLimit = pCreditLimit;
    }

    /**
     *  Sets the CreditRating attribute of the AccountMgrDetailForm object
     *
     *@param  pCreditRating  The new CreditRating value
     */
    public void setCreditRating(String pCreditRating) {
        this.mCreditRating = pCreditRating;
    }


    /**
     *  Sets the Billing Address1 attribute of the AccountMgrDetailForm object
     *
     *@param  pBillingAddress1  The new Billing Address1 value
     */
    public void setBillingAddress1(String pBillingAddress1) {
        this.mBillingAddress1 = pBillingAddress1;
    }


    /**
     *  Sets the Billing Address2 attribute of the AccountMgrDetailForm object
     *
     *@param  pBillingAddress2  The new Billing Address2 value
     */
    public void setBillingAddress2(String pBillingAddress2) {
        this.mBillingAddress2 = pBillingAddress2;
    }


    /**
     *  Sets the Billing Address3 attribute of the AccountMgrDetailForm object
     *
     *@param  pBillingAddress3  The new Billing Address3 value
     */
    public void setBillingAddress3(String pBillingAddress3) {
        this.mBillingAddress3 = pBillingAddress3;
    }


    /**
     *  Sets the Billing City attribute of the AccountMgrDetailForm object
     *
     *@param  pBillingCity  The new Billing City value
     */
    public void setBillingCity(String pBillingCity) {
        this.mBillingCity = pBillingCity;
    }


    /**
     *  Sets the Billing State attribute of the AccountMgrDetailForm object
     *
     *@param  pBillingState  The new Billing State value
     */
    public void setBillingState(String pBillingState) {
        this.mBillingState = pBillingState;
    }


    /**
     *  Sets the Billing PostalCode attribute of the AccountMgrDetailForm object
     *
     *@param  pBillingPostalCode  The new Billing PostalCode value
     */
    public void setBillingPostalCode(String pBillingPostalCode) {
        this.mBillingPostalCode = pBillingPostalCode;
    }


    /**
     *  Sets the Billing Country attribute of the AccountMgrDetailForm object
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

    /**
     *  Gets the StoreName attribute of the AccountMgrDetailForm object
     *
     *@return    The StoreName value
     */
    public String getStoreName() {
        return mStoreName;
    }


    /**
     *  Gets the StoreId attribute of the AccountMgrDetailForm object
     *
     *@return    The StoreId value
     */
    public String getStoreId() {
        return mStoreId;
    }


    /**
     *  Gets the AccountNumber attribute of the AccountMgrDetailForm object
     *
     *@return    The AccountNumber value
     */
    public String getAccountNumber() {
        return mAccountNumber;
    }


    /**
     *  Gets the OrderMinimum attribute of the AccountMgrDetailForm object
     *
     *@return    The OrderMinimum value
     */
    public String getOrderMinimum() {
        return mOrderMinimum;
    }

    /**
     *  Gets the CreditRating attribute of the AccountMgrDetailForm object
     *
     *@return    The CreditRating value
     */
    public String getCreditRating() {
        return mCreditRating;
    }


    /**
     *  Gets the CreditLimit attribute of the AccountMgrDetailForm object
     *
     *@return    The CreditLimit value
     */
    public String getCreditLimit() {
        return mCreditLimit;
    }


    /**
     *  Gets the Billing Address1 attribute of the AccountMgrDetailForm object
     *
     *@return    The BillingAddress1 value
     */
    public String getBillingAddress1() {
        return mBillingAddress1;
    }


    /**
     *  Gets the Billing Address2 attribute of the AccountMgrDetailForm object
     *
     *@return    The BillingAddress2 value
     */
    public String getBillingAddress2() {
        return mBillingAddress2;
    }


    /**
     *  Gets the Billing Address3 attribute of the AccountMgrDetailForm object
     *
     *@return    The BillingAddress3 value
     */
    public String getBillingAddress3() {
        return mBillingAddress3;
    }


    /**
     *  Gets the Billing City attribute of the AccountMgrDetailForm object
     *
     *@return    The BillingCity value
     */
    public String getBillingCity() {
        return mBillingCity;
    }


    /**
     *  Gets the Billing State attribute of the AccountMgrDetailForm object
     *
     *@return    The BillingState value
     */
    public String getBillingState() {
        return mBillingState;
    }


    /**
     *  Gets the Billing PostalCode attribute of the AccountMgrDetailForm object
     *
     *@return    The BillingPostalCode value
     */
    public String getBillingPostalCode() {
        return mBillingPostalCode;
    }


    /**
     *  Gets the Billing Country attribute of the AccountMgrDetailForm object
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

    /**sets the current value for the shipto counter, only has meaning if create
     erpShipto is set to true*/
    public void setMakeShipToBillTo(boolean pMakeShipToBillTo){
        this.mMakeShipToBillTo = pMakeShipToBillTo;
    }
    public void setShowScheduledDelivery(boolean v){
        this.mShowScheduledDelivery = v;
    }
    public boolean getShowScheduledDelivery(){
        return this.mShowScheduledDelivery;
    }
    public void setAllowOrderConsolidation(boolean v){
        this.mAllowOrderConsolidation = v;
    }    
    public boolean getAllowOrderConsolidation(){
        return this.mAllowOrderConsolidation;
    }

    public void setShowDistSkuNum(boolean v){
        this.mShowDistSkuNum = v;
    }    
    public boolean getShowDistSkuNum(){
        return this.mShowDistSkuNum;
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
    
    /**
     *  <code>reset</code> method, set the search fiels to null.
     *
     *@param  mapping  an <code>ActionMapping</code> value
     *@param  request  a <code>HttpServletRequest</code> value
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {

        customerRequestPoAllowed = false;
        mItemsToAddToInventory = new String[0];
        mSelectedInventoryItems= new String[0];
	mCrcShop = false;
	mMakeShipToBillTo = false;	
        taxableIndicator = false;
        mShowScheduledDelivery = false;
        mAllowOrderConsolidation = false;
        mShowDistSkuNum = false;
	// reset to default fiscal calendar values
	mFiscalCalenderData = getFiscalCalUpdate();
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
     *  Sets the CRC Shopping attribute of the AccountMgrDetailForm object
     *
     *@param  pCrcShop  The new CRC Shop value
     */
    public void setCrcShop(boolean pCrcShop) {
        this.mCrcShop = pCrcShop;
    }

    /**
     *  Gets the the flag whether to let the crc user shop for the account
     *
     *@return    The CrcShop value
     */
    public boolean getCrcShop() {
        return mCrcShop;
    }

        /**gets the current value for the shipto counter, only has meaning if create
     erpShipto is set to true*/
    public boolean getMakeShipToBillTo(){
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
    public boolean isAuthorizedForResale() {
        return this.authorizedForResale;
    }    
    
    /** Setter for property authorizedForResale.
     * @param authorizedForResale New value of property authorizedForResale.
     *
     */
    public void setAuthorizedForResale(boolean authorizedForResale) {
        this.authorizedForResale = authorizedForResale;
    }    
    
    /** Getter for property EdiShipToPrefix.
     * @return Value of property EdiShipToPrefix.
     *
     */
    public String getEdiShipToPrefix() {
        return this.mEdiShipToPrefix;
    }    
    
    /** Setter for property EdiShipToPrefix.
     * @param val New value of property EdiShipToPrefix.
     *
     */
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
    
    public void setDataFieldProperty(int indx,BusEntityFieldDataElement dataField) {
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


    private FiscalCalenderData mFiscalCalenderData;
    public FiscalCalenderData getFiscalCalUpdate() {
	if ( null == mFiscalCalenderData ) {
	    FiscalCalenderData fcd = FiscalCalenderData.createValue();
	    GregorianCalendar cal = new GregorianCalendar();
	    fcd.setFiscalYear(cal.get(Calendar.YEAR));
	    fcd.setEffDate(new java.util.Date());
            fcd.setPeriodCd(RefCodeNames.BUDGET_PERIOD_CD.MONTHLY);
            /*fcd.setMmdd1("1/1");
            fcd.setMmdd2("2/1");
            fcd.setMmdd3("3/1");
            fcd.setMmdd4("4/1");
            fcd.setMmdd5("5/1");
            fcd.setMmdd6("6/1");
            fcd.setMmdd7("7/1");
            fcd.setMmdd8("8/1");
            fcd.setMmdd9("9/1");
            fcd.setMmdd10("10/1");
            fcd.setMmdd11("11/1");
            fcd.setMmdd12("12/1");*/
            fcd.setShortDesc("Admin Default Cal");
	    mFiscalCalenderData = fcd;

	}
	return mFiscalCalenderData;
    }

    public void setFiscalCalUpdate(FiscalCalenderData v) {
	mFiscalCalenderData = v;
    }

    public void setFiscalYear(String pYear) {
	mFiscalCalenderData.setFiscalYear(Integer.parseInt(pYear));
    }

    public String getFiscalYear() {
	if ( null == mFiscalCalenderData ) {
	    mFiscalCalenderData = getFiscalCalUpdate();
	}
	return String.valueOf(mFiscalCalenderData.getFiscalYear());
    }

    public void setFiscalEffDate(String pDateStr) {
	java.text.DateFormat formatter = new java.text.SimpleDateFormat("MM/dd/yyyy");
	try {
	    Date date = (Date)formatter.parse(pDateStr);
	    mFiscalCalenderData.setEffDate(date);
	} catch (Exception e) {
	    try {
		Date date = (Date)formatter.parse("1/1/3000");
		mFiscalCalenderData.setEffDate(date);	    
	    } catch (Exception e2) {}
	}
    }
    public String getFiscalEffDate() {
	java.util.Date d = mFiscalCalenderData.getEffDate();
	if ( null == d  ) {
	    d = new java.util.Date();
	}
	Calendar calendar = new GregorianCalendar();
	calendar.setTime(d);	
	String dstring = "" + 
	    (calendar.get(Calendar.MONTH) + 1)  + "/" +
	    calendar.get(Calendar.DAY_OF_MONTH) + "/" +
	    calendar.get(Calendar.YEAR );
	    
	return dstring;
    }

    public void setFiscalBusEntityId(String pBusEntityId) {
	mFiscalCalenderData.setBusEntityId(Integer.parseInt(pBusEntityId));
    }
    public String getFiscalBusEntityId() {
	return String.valueOf(mFiscalCalenderData.getBusEntityId());
    }
    

    private AccountData mAccountData;
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
     * Holds value of property customerRequestPoAllowed.
     */
    private boolean customerRequestPoAllowed = true;

    /**
     * Holds value of property faxBackConfirm.
     */
    private String faxBackConfirm;
    
    private String mRushOrderCharge = "0";
    public String getRushOrderCharge() {
        return mRushOrderCharge;
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
     * Getter for property customerRequestPoAllowed.
     * @return Value of property customerRequestPoAllowed.
     */
    public boolean isCustomerRequestPoAllowed() {

        return this.customerRequestPoAllowed;
    }

    /**
     * Setter for property customerRequestPoAllowed.
     * @param customerRequestPoAllowed New value of property customerRequestPoAllowed.
     */
    public void setCustomerRequestPoAllowed(boolean customerRequestPoAllowed) {

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


    private List shoppingOptions;
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

}

