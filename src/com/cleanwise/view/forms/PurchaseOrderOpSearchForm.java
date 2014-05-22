/**
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 */

package com.cleanwise.view.forms;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.service.api.value.*;

/**
 * Form bean for the purchase order sub system
 *
 */
public class PurchaseOrderOpSearchForm extends StorePortalBaseForm {

    private String _searchField = "";
    private String _searchType = "";
      
    private List _resultList = new ArrayList();
    
    //private String _mSearchOrdersType    = "ORDER";
    private String _mDistributorId            = "";
    private String _mErpOrderNum              = "";
    private String _mErpPONum                 = "";
    private String _mOutboundPoNum            = "";    
    private String _mErpPORefNum              = "";
    private String _mWebOrderConfirmationNum  = "";
    private String _mPoDateRangeBegin = "";
    private String _mPoDateRangeEnd   = "";
    private String _mSiteId              = "";
    private String _mInvoiceDistNum      = "";
    private String _mPurchaseOrderStatus      = "";
    private String _mInvoiceStatus      = "";
    private String[] _mSelectorBox      = new String[0];
    private String[] _mQuantityBarcode  = new String[0];
    private String _mSiteData       = "";
    private String _mItemStatus     = "";
    private String _mItemTargetShipDateBeginString = "";
    private String _mItemTargetShipDateEndString = "";
    private String distributorReturnRequestNum = "";
    private String returnRequestRefNum = "";
    private List trackingResultList;
    private String[] openLineStatusUpdates;
    private String targetFacilityRank = "";
    
    /** Holds value of property packagesList. */
    private PurchaseOrderStatusDescDataViewVector packagesList;
    
    /** Holds value of property toManifestList. */
    private PurchaseOrderStatusDescDataViewVector toManifestList;

    /**
     * Holds value of property orderRequestPoNum.
     */
    private String orderRequestPoNum;

    /**
     * Holds value of property accountId.
     */
    private String accountId;
    private String accountIdList = "";

    /**
     * Holds value of property exceptionDistInvoiceOnly.
     */
    private boolean exceptionDistInvoiceOnly;

    /**
     * Holds value of property invoiceDistDateRangeEnd.
     */
    private String invoiceDistDateRangeEnd;

    /**
     * Holds value of property invoiceDistDateRangeBegin.
     */
    private String invoiceDistDateRangeBegin;
    
    /**
     * <code>getSearchField</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getSearchField() {
        return (this._searchField);
    }

    /**
     * <code>setSearchField</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setSearchField(String pVal) {
        this._searchField = pVal;
    }

    /**
     * <code>getSearchType</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getSearchType() {
        return (this._searchType);
    }

    /**
     * <code>setSearchType</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setSearchType(String pVal) {
        this._searchType = pVal;
    }

    
    /**
     * <code>getSearchOrdersType</code> method.
     *
     * @return a <code>String</code> value
     */
    /*public String getSearchOrdersType() {
        return (this._mSearchOrdersType);
    }*/

    /**
     * <code>setSearchOrdersType</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    /*public void setSearchOrdersType(String pVal) {
        this._mSearchOrdersType = pVal;
    }*/
    

    /**
     * <code>getDistributorId</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getDistributorId() {
        return (this._mDistributorId);
    }

    /**
     * <code>setDistributorId</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setDistributorId(String pVal) {
        this._mDistributorId = pVal;
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
    }

    
    /**
     * <code>getPoDateRangeBegin</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getPoDateRangeBegin() {
        return (this._mPoDateRangeBegin);
    }

    /**
     * <code>setPoDateRangeBegin</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setPoDateRangeBegin(String pVal) {
        this._mPoDateRangeBegin = pVal;
    }

    
    /**
     * <code>getPoDateRangeEnd</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getPoDateRangeEnd() {
        return (this._mPoDateRangeEnd);
    }

    /**
     * <code>setPoDateRangeEnd</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setPoDateRangeEnd(String pVal) {
        this._mPoDateRangeEnd = pVal;
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
    }

    
    /**
     * <code>getPurchaseOrderStatus</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getPurchaseOrderStatus() {
        return (this._mPurchaseOrderStatus);
    }

    /**
     * <code>setPurchaseOrderStatus</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setPurchaseOrderStatus(String pVal) {
        this._mPurchaseOrderStatus = pVal;
    }

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
    }
    
     /** Getter for property ItemStatus.
     * @return Value of property ItemStatus.
     */
    public String getItemStatus() {
        return _mItemStatus;
    }
    
    /** Setter for property ItemStatus.
     * @param ItemStatus New value of property ItemStatus.
     */
    public void setItemStatus(String pItemStatus) {
        _mItemStatus = pItemStatus;
    }
    
    
    /**
     * <code>getItemTargetShipDateBeginString</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getItemTargetShipDateBeginString() {
        return (this._mItemTargetShipDateBeginString);
    }

    /**
     * <code>setItemTargetShipDateBeginString</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setItemTargetShipDateBeginString(String pVal) {
        this._mItemTargetShipDateBeginString = pVal;
    }
    
    /**
     * <code>getItemTargetShipDateEndString</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getItemTargetShipDateEndString() {
        return (this._mItemTargetShipDateEndString);
    }

    /**
     * <code>setItemTargetShipDateBeginString</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setItemTargetShipDateEndString(String pVal) {
        this._mItemTargetShipDateEndString = pVal;
    }

        /**
     * <code>getQuantityBarcode</code> method.
     *
     * @return a <code>String[]</code> value
     */
    public String[] getQuantityBarcode() {
        return (this._mQuantityBarcode);
    }

    /**
     * <code>setQuantityBarcode</code> method.
     *
     * @param pVal a <code>String[]</code> value
     */
    public void setQuantityBarcode(String[] pVal) {
        this._mQuantityBarcode = pVal;
    }
    
    /**
     * <code>getSelectorBox</code> method.
     *
     * @return a <code>String[]</code> value
     */
    public String[] getSelectorBox() {
        return (this._mSelectorBox);
    }

    /**
     * <code>setSelectorBox</code> method.
     *
     * @param pVal a <code>String[]</code> value
     */
    public void setSelectorBox(String[] pVal) {
        this._mSelectorBox = pVal;
    }
    
    
    /**
     * <code>getResultList</code> method.
     *
     * @return a <code>OrderStatusDescDataVector</code> value
     */
    public List getResultList() {
        return (this._resultList);
    }

    /**
     * <code>setResultList</code> method.
     *
     * @param pVal a <code>OrderStatusDescDataVector</code> value
     */
    public void setResultList(List pVal) {
        this._resultList = pVal;
    }

    /**
     * <code>getListCount</code> method.
     *
     * @return a <code>int</code> value
     */
    public int getListCount() 
    {
        if (null == this._resultList) {
            this._resultList = new PurchaseOrderStatusDescDataViewVector();
        }
        return (this._resultList.size());
    }

    /**
     * <code>reset</code> method, set the search fiels to null.
     *
     * @param mapping an <code>ActionMapping</code> value
     * @param request a <code>HttpServletRequest</code> value
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        exceptionDistInvoiceOnly = false;
    }


    /**
     * <code>validate</code> method is a stub.
     *
     * @param mapping an <code>ActionMapping</code> value
     * @param request a <code>HttpServletRequest</code> value
     * @return an <code>ActionErrors</code> value
     */
    public ActionErrors validate(ActionMapping mapping,
                               HttpServletRequest request) {
        // No validation necessary.
        return null;
    }

    /** Getter for property SiteData.
     * @return Value of property SiteData.
     */
    public String getSiteData() {
        return _mSiteData;
    }
    
    /** Setter for property SiteData.
     * @param SiteData New value of property SiteData.
     */
    public void setSiteData(String siteData) {
        _mSiteData = siteData;
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
    }
    
    /** Getter for property returnRequestNum.
     * @return Value of property returnRequestNum.
     *
     */
    public String getReturnRequestRefNum() {
        return this.returnRequestRefNum;
    }
    
    /** Setter for property returnRequestNum.
     * @param returnRequestNum New value of property returnRequestNum.
     *
     */
    public void setReturnRequestRefNum(String returnRequestRefNum) {
        this.returnRequestRefNum = returnRequestRefNum;
    }
    
    /** Getter for property trackingResultList.
     * @return Value of property trackingResultList.
     *
     */
    public List getTrackingResultList() {
        return this.trackingResultList;
    }
    
    /** Setter for property trackingResultList.
     * @param trackingResultList New value of property trackingResultList.
     *
     */
    public void setTrackingResultList(List trackingResultList) {
        this.trackingResultList = trackingResultList;
    }
    
    /** Getter for property openLineStatusUpdates.
     * @return Value of property openLineStatusUpdates.
     *
     */
    public String[] getOpenLineStatusUpdates() {
        return this.openLineStatusUpdates;
    }
    
    /** Setter for property openLineStatusUpdates.
     * @param openLineStatusUpdates New value of property openLineStatusUpdates.
     *
     */
    public void setOpenLineStatusUpdates(String[] openLineStatusUpdates) {
        this.openLineStatusUpdates = openLineStatusUpdates;
    }
    
    /** Getter for property packagesList.
     * @return Value of property packagesList.
     *
     */
    public PurchaseOrderStatusDescDataViewVector getPackagesList() {
        return this.packagesList;
    }
    
    /** Setter for property packagesList.
     * @param packagesList New value of property packagesList.
     *
     */
    public void setPackagesList(PurchaseOrderStatusDescDataViewVector packagesList) {
        this.packagesList = packagesList;
    }
    
    /** Getter for property targetFacilityRank.
     * @return Value of property targetFacilityRank.
     *
     */
    public String getTargetFacilityRank() {
        return this.targetFacilityRank;
    }
    
    /** Setter for property targetFacilityRank.
     * @param targetFacilityRank New value of property targetFacilityRank.
     *
     */
    public void setTargetFacilityRank(String targetFacilityRank) {
        this.targetFacilityRank = targetFacilityRank;
    }
    
    /** Getter for property toManifestList.
     * @return Value of property toManifestList.
     *
     */
    public PurchaseOrderStatusDescDataViewVector getToManifestList() {
        return this.toManifestList;
    }
    
    /** Setter for property toManifestList.
     * @param toManifestList New value of property toManifestList.
     *
     */
    public void setToManifestList(PurchaseOrderStatusDescDataViewVector toManifestList) {
        this.toManifestList = toManifestList;
    }
    
    /** indexed list so it is accessable from JSP*/
    public PurchaseOrderStatusDescDataView getToManifestListEle(int pIndex){
        while(pIndex >= toManifestList.size()){
            toManifestList.add(PurchaseOrderStatusDescDataView.createValue());
        }
        return (PurchaseOrderStatusDescDataView) toManifestList.get(pIndex);
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
     * Getter for property accountIdList.
     * @return Value of property accountIdList.
     */
    public String getAccountIdList() {
        return this.accountIdList;
    }

    /**
     * Setter for property accountIdList.
     * @param accountIdList New value of property accountIdList.
     */
    public void setAccountIdList(String accountIdList) {
        this.accountIdList = accountIdList;
    }

    /**
     * Getter for property exceptionDistInvoiceOnly.
     * @return Value of property exceptionDistInvoiceOnly.
     */
    public boolean isExceptionDistInvoiceOnly() {

        return this.exceptionDistInvoiceOnly;
    }

    /**
     * Setter for property exceptionDistInvoiceOnly.
     * @param exceptionDistInvoiceOnly New value of property exceptionDistInvoiceOnly.
     */
    public void setExceptionDistInvoiceOnly(boolean exceptionDistInvoiceOnly) {

        this.exceptionDistInvoiceOnly = exceptionDistInvoiceOnly;
    }

    /**
     * Getter for property invoiceDistDateRangeEnd.
     * @return Value of property invoiceDistDateRangeEnd.
     */
    public String getInvoiceDistDateRangeEnd() {

        return this.invoiceDistDateRangeEnd;
    }

    /**
     * Setter for property invoiceDistDateRangeEnd.
     * @param invoiceDistDateRangeEnd New value of property invoiceDistDateRangeEnd.
     */
    public void setInvoiceDistDateRangeEnd(String invoiceDistDateRangeEnd) {

        this.invoiceDistDateRangeEnd = invoiceDistDateRangeEnd;
    }

    /**
     * Getter for property invoiceDistDateRangeBegin.
     * @return Value of property invoiceDistDateRangeBegin.
     */
    public String getInvoiceDistDateRangeBegin() {

        return this.invoiceDistDateRangeBegin;
    }

    /**
     * Setter for property invoiceDistDateRangeBegin.
     * @param invoiceDistDateRangeBegin New value of property invoiceDistDateRangeBegin.
     */
    public void setInvoiceDistDateRangeBegin(String invoiceDistDateRangeBegin) {

        this.invoiceDistDateRangeBegin = invoiceDistDateRangeBegin;
    }

    /**
     * Holds value of property invoiceDistAddDateRangeBegin.
     */
    private String invoiceDistAddDateRangeBegin;

    /**
     * Getter for property invoiceDistAddDateRangeBegin.
     * @return Value of property invoiceDistAddDateRangeBegin.
     */
    public String getInvoiceDistAddDateRangeBegin() {

        return this.invoiceDistAddDateRangeBegin;
    }

    /**
     * Setter for property invoiceDistAddDateRangeBegin.
     * @param invoiceDistAddDateRangeBegin New value of property invoiceDistAddDateRangeBegin.
     */
    public void setInvoiceDistAddDateRangeBegin(String invoiceDistAddDateRangeBegin) {

        this.invoiceDistAddDateRangeBegin = invoiceDistAddDateRangeBegin;
    }

    /**
     * Holds value of property invoiceDistAddDateRangeEnd.
     */
    private String invoiceDistAddDateRangeEnd;

    /**
     * Getter for property invoiceDistAddDateRangeEnd.
     * @return Value of property invoiceDistAddDateRangeEnd.
     */
    public String getInvoiceDistAddDateRangeEnd() {

        return this.invoiceDistAddDateRangeEnd;
    }

    /**
     * Setter for property invoiceDistAddDateRangeEnd.
     * @param invoiceDistAddDateRangeEnd New value of property invoiceDistAddDateRangeEnd.
     */
    public void setInvoiceDistAddDateRangeEnd(String invoiceDistAddDateRangeEnd) {

        this.invoiceDistAddDateRangeEnd = invoiceDistAddDateRangeEnd;
    }

    public String getOutboundPoNum() {
        return _mOutboundPoNum;
    }

    public void setOutboundPoNum(String _mOutboundPoNum) {
        this._mOutboundPoNum = _mOutboundPoNum;
    }
}
