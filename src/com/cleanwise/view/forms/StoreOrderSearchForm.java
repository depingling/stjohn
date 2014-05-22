package com.cleanwise.view.forms;
/**
 * Title:        TrackerSearchForm
 * Description:  This is the Struts ActionForm class for order tracking operation console page.
 * Purpose:      Struts support to search for orders.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       durval
 */
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.LinkedList;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.service.api.value.*;

/**
 * Form bean for the user manager page.
 *
 * @author Liang
 */
public final class StoreOrderSearchForm extends StorePortalBaseForm {

    private String _searchField = "";
    private String _searchType = "";
    private int _storeId = 0;

    private OrderStatusDescDataVector _resultList = new OrderStatusDescDataVector();

    private String _mSearchOrdersType    = new String("ORDER");
    private AccountDataVector mAccountFilter;
    private String _mAccountId           = new String("");
    private String _mAccountIdList       = new String("");
    private DistributorDataVector distributorFilter;
    private String _mDistributorId       = new String("");
    private String _mDistributorIdList       = new String("");
    private String _mErpOrderNum         = new String("");
    private String _mErpPONum            = new String("");
    private String _mWebOrderConfirmationNum     = new String("");
    private String _mOrderDateRangeBegin = new String("");
    private String _mOrderDateRangeEnd   = new String("");
    private String _mCustPONum           = new String("");
    private String _mRefOrderNum           = new String("");
    private String _mSiteId              = new String("");
    private String _mSiteIdList       = new String("");
    private String _mSiteZipCode         = new String("");
    private String _mOrderStatus         = new String("");
    private String _mReferenceCode       = new String("");
    private String _mShipFromId          = new String("");
    private String _mPlacedBy            = new String("");
    private UserDataVector _mUserFilter;
    private String _mMethod              = new String("");
    private String _mInvoiceDistNum      = new String("");
    private String _mInvoiceCustNum      = new String("");
    private String _mWorkflowId          = new String("");
    private SiteViewVector _mSiteFilter;
    private String _mOutboundPoNum       = new String("");


    public int getStoreId() {
        return (this._storeId);
    }
    public void setStoreId(int pVal) {
        this._storeId = pVal;
    }


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
    public String getSearchOrdersType() {
        return (this._mSearchOrdersType);
    }

    /**
     * <code>setSearchOrdersType</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setSearchOrdersType(String pVal) {
        this._mSearchOrdersType = pVal;
    }

    public void setAccountFilter(AccountDataVector pVal) {mAccountFilter = pVal;}
    public AccountDataVector getAccountFilter() {return mAccountFilter;}

    /**
     * <code>getAccountId</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getAccountId() {
      return (this._mAccountId);
    }

    /**
     * <code>setAccountId</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setAccountId(String pVal) {
        this._mAccountId = pVal;
    }

    /**
     * <code>getAccountIdList</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getAccountIdList() {
        return (this._mAccountIdList);
    }

    /**
     * <code>setAccountIdList</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setAccountIdList(String pVal) {
        this._mAccountIdList = pVal;
    }

    /**
     * Getter for property distributorFilter.
     * @return Value of property distributorFilter.
     */
    public DistributorDataVector getDistributorFilter() {

        return this.distributorFilter;
    }

    /**
     * Setter for property distributorFilter.
     * @param distributorFilter New value of property distributorFilter.
     */
    public void setDistributorFilter(DistributorDataVector distributorFilter) {

        this.distributorFilter = distributorFilter;
    }

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
     * <code>getDistributorIdList</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getDistributorIdList() {
        return (this._mDistributorIdList);
    }

    /**
     * <code>setDistributorIdList</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setDistributorIdList(String pVal) {
        this._mDistributorIdList = pVal;
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
     * <code>getOrderDateRangeBegin</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getOrderDateRangeBegin() {
        return (this._mOrderDateRangeBegin);
    }

    /**
     * <code>setOrderDateRangeBegin</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setOrderDateRangeBegin(String pVal) {
        this._mOrderDateRangeBegin = pVal;
    }


    /**
     * <code>getOrderDateRangeEnd</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getOrderDateRangeEnd() {
        return (this._mOrderDateRangeEnd);
    }

    /**
     * <code>setOrderDateRangeEnd</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setOrderDateRangeEnd(String pVal) {
        this._mOrderDateRangeEnd = pVal;
    }


    /**
     * <code>getCustPONum</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getCustPONum() {
        return (this._mCustPONum);
    }

    /**
     * <code>setCustPONum</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setCustPONum(String pVal) {
        this._mCustPONum = pVal;
    }


    /**
     * <code>getRefOrderNum</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getRefOrderNum() {
        return (this._mRefOrderNum);
    }

    /**
     * <code>setRefOrderNum</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setRefOrderNum(String pVal) {
        this._mRefOrderNum = pVal;
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
     * <code>getSiteZipCode</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getSiteZipCode() {
        return (this._mSiteZipCode);
    }

    /**
     * <code>setSiteZipCode</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setSiteZipCode(String pVal) {
        this._mSiteZipCode = pVal;
    }


    /**
     * <code>getOrderStatus</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getOrderStatus() {
        return (this._mOrderStatus);
    }

    /**
     * <code>setOrderStatus</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setOrderStatus(String pVal) {
        this._mOrderStatus = pVal;
    }


    /**
     * <code>getReferenceCode</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getReferenceCode() {
        return (this._mReferenceCode);
    }

    /**
     * <code>setReferenceCode</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setReferenceCode(String pVal) {
        this._mReferenceCode = pVal;
    }


    /**
     * <code>getShipFromId</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getShipFromId() {
        return (this._mShipFromId);
    }

    /**
     * <code>setShipFromId</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setShipFromId(String pVal) {
        this._mShipFromId = pVal;
    }


    /**
     * <code>getPlacedBy</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getPlacedBy() {
        return (this._mPlacedBy);
    }

    /**
     * <code>setPlacedBy</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setPlacedBy(String pVal) {
        this._mPlacedBy = pVal;
    }

    public UserDataVector getUserFilter() {
        return _mUserFilter;
    }
    public void setUserFilter(UserDataVector mUserFilter) {
        this._mUserFilter = mUserFilter;
    }


    /**
     * <code>getMethod</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getMethod() {
        return (this._mMethod);
    }

    /**
     * <code>setMethod</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setMethod(String pVal) {
        this._mMethod = pVal;
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
     * <code>getInvoiceCustNum</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getInvoiceCustNum() {
        return (this._mInvoiceCustNum);
    }

    /**
     * <code>setInvoiceCustNum</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setInvoiceCustNum(String pVal) {
        this._mInvoiceCustNum = pVal;
    }


    /**
     * <code>getWorkflowId</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getWorkflowId() {
        return (this._mWorkflowId);
    }

    /**
     * <code>setWorkflowId</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setWorkflowId(String pVal) {
        this._mWorkflowId = pVal;
    }



    /**
     * <code>getResultList</code> method.
     *
     * @return a <code>OrderStatusDescDataVector</code> value
     */
    public OrderStatusDescDataVector getResultList() {
        return (this._resultList);
    }

    /**
     * <code>setResultList</code> method.
     *
     * @param pVal a <code>OrderStatusDescDataVector</code> value
     */
    public void setResultList(OrderStatusDescDataVector pVal) {
        this._resultList = pVal;
    }

    public SiteViewVector getSiteFilter() { return _mSiteFilter; }
    public void setSiteFilter(SiteViewVector mSiteFilter) {this._mSiteFilter = mSiteFilter;}
    /**
     * <code>getSiteIdList</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getSiteIdList() {
        return (this._mSiteIdList);
    }

    /**
     * <code>setSiteIdList</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setSiteIdList(String pVal) {
        this._mSiteIdList = pVal;
    }



    /**
     * <code>getListCount</code> method.
     *
     * @return a <code>int</code> value
     */
    public int getListCount() {
        if (null == this._resultList) {
            this._resultList = new OrderStatusDescDataVector();
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
        /*
        this._searchField = "";
        this._searchType = "";
        this._mSearchOrdersType    = new String("ORDER");
        this._mAccountId           = new String("");
        this._mDistributorId       = new String("");
        this._mErpOrderNum         = new String("");
        this._mWebOrderConfirmationNum     = new String("");
        this._mOrderDateRangeBegin = new String("");
        this._mOrderDateRangeEnd   = new String("");
        this._mCustPONum           = new String("");
        this._mSiteId              = new String("");
        this._mSiteZipCode         = new String("");
        this._mOrderStatus         = new String("");
        this._mReferenceCode        = new String("");
        this._mShipFromId         = new String("");
        this._mPlacedBy            = new String("");
        this._mMethod              = new String("");
         */
    }

    public String getOutboundPoNum() {
        return _mOutboundPoNum;
    }

    public void setOutboundPoNum(String outboundPoNum) {
        this._mOutboundPoNum = outboundPoNum;
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

}
