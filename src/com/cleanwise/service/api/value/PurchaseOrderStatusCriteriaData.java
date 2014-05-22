package com.cleanwise.service.api.value;
import com.cleanwise.service.api.framework.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PurchaseOrderStatusCriteriaData extends ValueObject{
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = -3803408720273477522L;
	public static int MAX_SEARCH_RESULTS = 100;
	
	
	
    public static int EXACT_MATCH = 100;
    public static int BEGIN_WITH_IGNORE_CASE = 200;
    public static int EXACT_MATCH_AND_OTHER_COL = 400;
    private IdVector _mDistributorIds       = new IdVector();
    private String _mErpOrderNum         = "";
    private String _mErpPONum            = "";
    private String _mOutboundPoNum       = "";
    private String _mErpPORefNum         = "";
    private String _mWebOrderConfirmationNum     = "";
    private Date   _mPoDateRangeBegin    = null;
    private Date   _mPoDateRangeEnd      = null;
    private String _mSiteId              = "";
    private String _mShipFromId          = "";
    private String _mInvoiceDistNum      = "";
    private List _mPurchaseOrderStatus;
    private String _mInvoiceStatus       = "";
    private List _mPurchOrdManifestStatus;
    private int _mUserId              = 0;
    private String _mUserTypeCd          = "";
    private String _mSiteData            = "";
    private String _mItemStatus          = "";
    private Date   _mItemTargetShipDateBegin = null;
    private Date   _mItemTargetShipDateEnd   = null;
    private String distributorReturnRequestNum = "";
    private String returnRequestRefNum = "";
    private boolean _mPendingManifestOnly = false;
    private boolean _mRoutedOnly = false;
    private Integer targetFacilityRank;
    private IdVector _mAccountIdVector = new IdVector();
    private int _mErpPONumMatchType = EXACT_MATCH;
    private String saleTypeCd; 
    private String orderRequestPoNum;
    private String orderId;
    private String accountId;
    private IdVector storeIdVector;
    private IdVector siteIdVector;
    private boolean invoiceSearch;
    private Date invoiceDistDateRangeBegin;
    private Date invoiceDistDateRangeEnd;
    private List invoiceDistStatusList = null;
    private List invoiceDistExcludeStatusList = new ArrayList();
    
    public PurchaseOrderStatusCriteriaData() {
        this._mDistributorIds           = new IdVector();
        this._mErpOrderNum              = "";
        this._mErpPONum                 = "";
        this._mOutboundPoNum            = "";
        this._mWebOrderConfirmationNum  = "";
        this._mPoDateRangeBegin         = null;
        this._mPoDateRangeEnd           = null;
        this._mSiteId                   = "";
        this._mInvoiceDistNum           = "";
        this._mSiteData                 = "";
        this._mItemStatus               = "";
        this._mItemTargetShipDateBegin  = null;
        this._mItemTargetShipDateEnd    = null;
        distributorReturnRequestNum     = "";
        returnRequestRefNum             = "";
        _mPendingManifestOnly           = false;
        _mRoutedOnly                    = false;
    }

    /**
     * Creates a new OrderStatusCriteriaData
     *
     * @return
     *  Newly initialized OrderStatusCriteriaData object.
     */
    public static PurchaseOrderStatusCriteriaData createValue () 
    {
        return new PurchaseOrderStatusCriteriaData();
    }
    
    
    /** Getter for property SiteData.
     * @return Value of property SiteData.
     */
    public String getSiteData() {
        return _mSiteData;
    }
    
    /** Setter for property SiteData.
     * @param siteData New value of property SiteData.
     */
    public void setSiteData(String siteData) {
        _mSiteData = siteData;
        setDirty(true);
    }
    
     /** Getter for property ItemStatus.
     * @return Value of property ItemStatus.
     */
    public String getItemStatus() {
        return _mItemStatus;
    }
    
    /** Setter for property ItemStatus.
     * @param pItemStatus New value of property ItemStatus.
     */
    public void setItemStatus(String pItemStatus) {
        _mItemStatus = pItemStatus;
        setDirty(true);
    }
    

    /**
     * <code>getDistributorId</code> method.
     *
     * @return a <code>IdVector</code> value
     */
    public IdVector getDistributorIds() {
        return (this._mDistributorIds);
    }

    /**
     * <code>setDistributorId</code> method.
     *
     * @param pVal a <code>IdVector</code> value
     */
    public void setDistributorIds(IdVector pVal) {
        this._mDistributorIds = pVal;
        setDirty(true);
    }
    
    
    /**
     * <code>getErpOrderNum</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getErpOrderNum() {
        return (this._mErpOrderNum);
    }

    /**
     * <code>setErpOrderNum</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setErpOrderNum(String pVal) {
        this._mErpOrderNum = pVal;
        setDirty(true);
    }


    /**
     * <code>getErpPONum</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getErpPONum() {
        return (this._mErpPONum);
    }

    /**
     * <code>setErpPONum</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setErpPONum(String pVal) {
        this._mErpPONum = pVal;
        setDirty(true);
    }
    
    public void setErpPONumMatchType(int pVal) {
       this._mErpPONumMatchType = pVal;
       setDirty(true);
     }
    
    public int getErpPONumMatchType() {
        return _mErpPONumMatchType;
     }

    /**
     * <code>getErpPORefNum</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getErpPORefNum() {
        return (this._mErpPORefNum);
    }

    /**
     * <code>setErpPORefNum</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setErpPORefNum(String pVal) {
        this._mErpPORefNum = pVal;
        setDirty(true);
    }


    
    /**
     * <code>getWebOrderConfirmationNum</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getWebOrderConfirmationNum() {
        return (this._mWebOrderConfirmationNum);
    }

    /**
     * <code>setWebOrderConfirmationNum</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setWebOrderConfirmationNum(String pVal) {
        this._mWebOrderConfirmationNum = pVal;
        setDirty(true);
    }

    
    /**
     * <code>getPoDateRangeBegin</code> method.
     *
     * @return a <code>Date</code> value
     */
    public Date getPoDateRangeBegin() {
        return (this._mPoDateRangeBegin);
    }

    /**
     * <code>setPoDateRangeBegin</code> method.
     *
     * @param pVal a <code>Date</code> value
     */
    public void setPoDateRangeBegin(Date pVal) {
        this._mPoDateRangeBegin = pVal;
        setDirty(true);
    }

    
    /**
     * <code>getPoDateRangeEnd</code> method.
     *
     * @return a <code>Date</code> value
     */
    public Date getPoDateRangeEnd() {
        return (this._mPoDateRangeEnd);
    }

    /**
     * <code>setPoDateRangeEnd</code> method.
     *
     * @param pVal a <code>Date</code> value
     */
    public void setPoDateRangeEnd(Date pVal) {
        this._mPoDateRangeEnd = pVal;
        setDirty(true);
    }

    
    
    /**
     * <code>getSiteId</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getSiteId() {
        return (this._mSiteId);
    }

    /**
     * <code>setSiteId</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setSiteId(String pVal) {
        this._mSiteId = pVal;
        setDirty(true);
    }

 


    /**
     * <code>getInvoiceDistNum</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getInvoiceDistNum() {
        return (this._mInvoiceDistNum);
    }

    /**
     * <code>setInvoiceDistNum</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setInvoiceDistNum(String pVal) {
        this._mInvoiceDistNum = pVal;
        setDirty(true);
    }

    
    /**
     * <code>getPurchaseOrderStatus</code> method.
     *
     * @return a <code>List</code> value
     */
    public List getPurchaseOrderStatus() {
        return (this._mPurchaseOrderStatus);
    }

    /**
     * <code>setPurchaseOrderStatus</code> method.
     *
     * @param pVal a <code>List</code> value
     */
    public void setPurchaseOrderStatus(List pVal) {
        this._mPurchaseOrderStatus = pVal;
        setDirty(true);
    }
    
    /**
     * <code>getPurchOrdManifestStatus</code> method.
     *
     * @return a <code>List</code> value
     */
    public List getPurchOrdManifestStatus() {
        return (this._mPurchOrdManifestStatus);
    }

    /**
     * <code>setPurchOrdManifestStatus</code> method.
     *
     * @param pVal a <code>List</code> value
     */
    public void setPurchOrdManifestStatus(List pVal) {
        this._mPurchOrdManifestStatus = pVal;
        setDirty(true);
    }
    
  

    
     /**
     * <code>getItemTargetShipDateBegin</code> method.
     *
     * @return a <code>Date</code> value
     */
    public Date getItemTargetShipDateBegin() {
        return (this._mItemTargetShipDateBegin);
    }

    /**
     * <code>setItemTargetShipDateBegin</code> method.
     *
     * @param pVal a <code>Date</code> value
     */
    public void setItemTargetShipDateBegin(Date pVal) {
        this._mItemTargetShipDateBegin = pVal;
        setDirty(true);
    }

    /**
     * <code>getItemTargetShipDateEnd</code> method.
     *
     * @return a <code>Date</code> value
     */
    public Date getItemTargetShipDateEnd() {
        return (this._mItemTargetShipDateEnd);
    }

    /**
     * <code>setItemTargetShipDateEnd</code> method.
     *
     * @param pVal a <code>Date</code> value
     */
    public void setItemTargetShipDateEnd(Date pVal) {
        this._mItemTargetShipDateEnd = pVal;
        setDirty(true);
    }

    /** Getter for property distributorReturnRequestNum.
     * @return Value of property distributorReturnRequestNum.
     *
     */
    public String getDistributorReturnRequestNum() {
        return this.distributorReturnRequestNum;
    }
    
    /** Setter for property distributorReturnRequestNum.
     * @param distributorReturnRequestNum New value of property distributorReturnRequestNum.
     *
     */
    public void setDistributorReturnRequestNum(String distributorReturnRequestNum) {
        this.distributorReturnRequestNum = distributorReturnRequestNum;
        setDirty(true);
    }
    
    /** Getter for property returnRequestNum.
     * @return Value of property returnRequestNum.
     *
     */
    public String getReturnRequestRefNum() {
        return this.returnRequestRefNum;
    }
    
    /** Setter for property returnRequestNum.
     * @param returnRequestRefNum New value of property returnRequestNum.
     *
     */
    public void setReturnRequestRefNum(String returnRequestRefNum) {
        this.returnRequestRefNum = returnRequestRefNum;
        setDirty(true);
    }
    
    /** Getter for property pendingManifestOnly.
     * @return Value of property pendingManifestOnly.
     *
     */
    public boolean isPendingManifestOnly() {
        return this._mPendingManifestOnly;
    }
    
    /** Setter for property pendingManifestOnly.
     * @param pPendingManifestOnly New value of property pendingManifestOnly.
     *
     */
    public void setPendingManifestOnly(boolean pPendingManifestOnly) {
        this._mPendingManifestOnly = pPendingManifestOnly;
        setDirty(true);
    }
    
    /** Getter for property routedOnly.
     * @return Value of property routedOnly.
     *
     */
    public boolean isRoutedOnly() {
        return this._mRoutedOnly;
    }
    
    /** Setter for property routedOnly.
     * @param pRoutedOnly New value of property routedOnly.
     *
     */
    public void setRoutedOnly(boolean pRoutedOnly) {
        this._mRoutedOnly = pRoutedOnly;
        setDirty(true);
    }

    /** Getter for property targetFacilityRank.
     * @return Value of property targetFacilityRank.
     *
     */
    public Integer getTargetFacilityRank() {
        return this.targetFacilityRank;
    }
    
    /** Setter for property targetFacilityRank.
     * @param targetFacilityRank New value of property targetFacilityRank.
     *
     */
    public void setTargetFacilityRank(Integer targetFacilityRank) {
        this.targetFacilityRank = targetFacilityRank;
    }

    /**
     * Getter for property orderRequestPoNum.
     * @return Value of property orderRequestPoNum.
     */
    public String getOrderRequestPoNum() {

        return this.orderRequestPoNum;
    }

    /**
     * Setter for property orderRequestPoNum.
     * @param orderRequestPoNum New value of property orderRequestPoNum.
     */
    public void setOrderRequestPoNum(String orderRequestPoNum) {

        this.orderRequestPoNum = orderRequestPoNum;
    }
    
    /**
     * Getter for property orderId.
     * @return Value of property orderId.
     */
    public String getOrderId() {

        return this.orderId;
    }

    /**
     * Setter for property orderId.
     * @param orderId New value of property orderId.
     */
    public void setOrderId(String orderId) {

        this.orderId = orderId;
    }

    /**
     * Getter for property accountId.
     * @return Value of property accountId.
     */
    public String getAccountId() {

        return this.accountId;
    }

    /**
     * Setter for property accountId.
     * @param accountId New value of property accountId.
     */
    public void setAccountId(String accountId) {

        this.accountId = accountId;
    }

    /**
     * Getter for property accountIdVector.
     * @return Value of property accountIdVector.
     */
    public IdVector getAccountIdVector() {

        return this._mAccountIdVector;
    }

    /**
     * Setter for property accountIdVector.
     * @param accountIdVector New value of property accountIdVector.
     */
    public void setAccountIdVector(IdVector accountIdVector) {

        this._mAccountIdVector = accountIdVector;
    }

    /**
     * Getter for property storeIdVector.
     * @return Value of property storeIdVector.
     */
    public IdVector getStoreIdVector() {

        return this.storeIdVector;
    }

    /**
     * Setter for property storeIdVector.
     * @param storeIdVector New value of property storeIdVector.
     */
    public void setStoreIdVector(IdVector storeIdVector) {

        this.storeIdVector = storeIdVector;
    }
    
    
    
    /**
     * Getter for property siteIdVector.
     * @return Value of property siteIdVector.
     */
    public IdVector getSiteIdVector() {

        return this.siteIdVector;
    }

    /**
     * Setter for property siteIdVector.
     * @param siteIdVector New value of property siteIdVector.
     */
    public void setSiteIdVector(IdVector siteIdVector) {

        this.siteIdVector = siteIdVector;
    }

    /**
     * Getter for property invoiceSearch.
     * @return Value of property invoiceSearch.
     */
    public boolean isInvoiceSearch() {

        return this.invoiceSearch;
    }
    

    /**
     * Setter for property invoiceSearch.
     * @param invoiceSearch New value of property invoiceSearch.
     */
    public void setInvoiceSearch(boolean invoiceSearch) {

        this.invoiceSearch = invoiceSearch;
    }

    /**
     * Getter for property distInvoiceDateRangeBegin.
     * @return Value of property distInvoiceDateRangeBegin.
     */
    public Date getInvoiceDistDateRangeBegin()  {

        return this.invoiceDistDateRangeBegin;
    }

    /**
     * Setter for property distInvoiceDateRangeBegin.
     * @param invoiceDistDateRangeBegin New value of property distInvoiceDateRangeBegin.
     */
    public void setInvoiceDistDateRangeBegin(java.util.Date invoiceDistDateRangeBegin)  {

        this.invoiceDistDateRangeBegin = invoiceDistDateRangeBegin;
    }

    /**
     * Getter for property distInvoiceDateRangeEnd.
     * @return Value of property distInvoiceDateRangeEnd.
     */
    public Date getInvoiceDistDateRangeEnd()  {

        return this.invoiceDistDateRangeEnd;
    }

    /**
     * Setter for property distInvoiceDateRangeEnd.
     * @param invoiceDistDateRangeEnd New value of property distInvoiceDateRangeEnd.
     */
    public void setInvoiceDistDateRangeEnd(java.util.Date invoiceDistDateRangeEnd)  {

        this.invoiceDistDateRangeEnd = invoiceDistDateRangeEnd;
    }

    /**
     * Getter for property excludeInvoiceDistStatusList.
     * @return Value of property excludeInvoiceDistStatusList.
     */
    public List getInvoiceDistExcludeStatusList()   {

        return this.invoiceDistExcludeStatusList;
    }

    /**
     * Setter for property excludeInvoiceDistStatusList.
     * @param invoiceDistExcludeStatusList New value of property excludeInvoiceDistStatusList.
     */
    public void setInvoiceDistExcludeStatusList(java.util.List invoiceDistExcludeStatusList)   {

        this.invoiceDistExcludeStatusList = invoiceDistExcludeStatusList;
    }
///////////////////////////
    /**
     * Getter for property invoiceDistStatusList.
     * @return Value of property invoiceDistStatusList.
     */
    public List getInvoiceDistStatusList()   {

        return this.invoiceDistStatusList;
    }

    /**
     * Setter for property invoiceDistStatusList.
     * @param invoiceDistStatusList New value of property invoiceDistStatusList.
     */
    public void setInvoiceDistStatusList(java.util.List invoiceDistStatusList)   {

        this.invoiceDistStatusList = invoiceDistStatusList;
    }
/////////////////////  
    
    /**
     * <code>getInvoiceStatus</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getInvoiceStatus() {
        return (this._mInvoiceStatus);
    }

    /**
     * <code>setInvoiceStatus</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setInvoiceStatus(String pVal) {
        this._mInvoiceStatus = pVal;
        setDirty(true);
    }
    
    /**
     * Holds value of property invoiceDistAddDateRangeBegin.
     */
    private Date invoiceDistAddDateRangeBegin;

    /**
     * Getter for property invoiceDistAddDateRangeBegin.
     * @return Value of property invoiceDistAddDateRangeBegin.
     */
    public Date getInvoiceDistAddDateRangeBegin() {

        return this.invoiceDistAddDateRangeBegin;
    }

    /**
     * Setter for property invoiceDistAddDateRangeBegin.
     * @param invoiceDistAddDateRangeBegin New value of property invoiceDistAddDateRangeBegin.
     */
    public void setInvoiceDistAddDateRangeBegin(Date invoiceDistAddDateRangeBegin) {

        this.invoiceDistAddDateRangeBegin = invoiceDistAddDateRangeBegin;
    }

    /**
     * Holds value of property invoiceDistAddDateRangeEnd.
     */
    private Date invoiceDistAddDateRangeEnd;

    /**
     * Getter for property invoiceDistAddDateRangeEnd.
     * @return Value of property invoiceDistAddDateRangeEnd.
     */
    public Date getInvoiceDistAddDateRangeEnd() {

        return this.invoiceDistAddDateRangeEnd;
    }

    /**
     * Setter for property invoiceDistAddDateRangeEnd.
     * @param invoiceDistAddDateRangeEnd New value of property invoiceDistAddDateRangeEnd.
     */
    public void setInvoiceDistAddDateRangeEnd(Date invoiceDistAddDateRangeEnd) {

        this.invoiceDistAddDateRangeEnd = invoiceDistAddDateRangeEnd;
    }


    /**
     * Get the UserId value.
     * @return the UserId value.
     */
    public int getUserId() {
	return _mUserId;
    }

    /**
     * Set the UserId value.
     * @param newUserId The new UserId value.
     */
    public void setUserId(int newUserId) {
	this._mUserId = newUserId;
    }

	public String getSaleTypeCd() {
		return saleTypeCd;
	}

	public void setSaleTypeCd(String saleTypeCd) {
		this.saleTypeCd = saleTypeCd;
	}


    public String getOutboundPoNum() {
        return _mOutboundPoNum;
    }

    public void setOutboundPoNum(String _mOutboundPoNum) {
        this._mOutboundPoNum = _mOutboundPoNum;
        setDirty(true);
    }
}
