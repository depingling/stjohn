package com.cleanwise.service.api.value;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cleanwise.service.api.dao.OrderDataAccess;
import com.cleanwise.service.api.framework.ValueObject;
import com.cleanwise.view.utils.Constants;

public class OrderStatusCriteriaData extends ValueObject{
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = 8629244444804030946L;

    private boolean mNotRecvdOnly = false;
    private String _mAccountId           = "";
    private IdVector _mAccountIdVector   = new IdVector();
    private String _mDistributorId       = "";
    private IdVector _mDistributorIdVector   = new IdVector();
    private String _mErpOrderNum         = "";
    private String _mErpPONum            = "";
    private String _mWebOrderConfirmationNum     = "";
    private String _mOrderDateRangeBegin = "";
    private String _mOrderDateRangeEnd   = "";
    private String _mCustPONum           = "";
    private String _mOutboundPONum       = "";
    private String _mRefOrderNum         = "";
    private String _mSiteId              = "";
    private IdVector _mSiteIdVector = new IdVector();
    private String _mSiteZipCode         = "";
    private String _mSiteCity            = "";
    private String _mSiteState           = "";
    private String _mOrderStatus         = "";
    private String _mReferenceCode       = "";
    private String _mShipFromId          = "";
    private String _mPlacedBy            = "";
    private String _mMethod              = "";
    private String _mInvoiceDistNum      = "";
    private String _mInvoiceCustNum      = "";
    private String _mUserId              = "";
    private String _mUserTypeCd          = "";
    private String _mExceptionTypeCd     = "";
    private String _mWorkflowId          = "";
    private String _mSiteCountry         = "";
    private boolean _mNewXpedex         = false;

    private ArrayList _mExcludeOrderStatus = new ArrayList();
    private ArrayList _mOrderStatusList = new ArrayList();
    private List<String> _orderPropertyList = new ArrayList<String>();
    private int _mMaxRows = 500; // default to 500
    
    private boolean _retrieveOrderHistory = true;
    private boolean _retrieveOrderItems = true;
    private boolean _retrieveOrderAddresses = true;
    private boolean _retrieveOrderAccount = true;
    private boolean _retrieveOrderMetaData = true;
    private boolean _retrieveOrderReceptionData = true;
    private boolean _retrieveOrderAutoOrderData = true;
    private boolean _retrieveOrderProperties = true;
    
    //STJ-4560
    private String _orderBudgetTypeCd;
    
    //STJ-4759 - allow filtering/ordering by revised order
    private boolean _filterByRevisedOrderDate = false;
    private String _orderBy = null;
    private String _orderDirection = Constants.DB_SORT_ORDER_ASCENDING;
    
    private boolean _includeRelatedOrder = false;

    /**
     * Holds value of property storeIds.
     */
    private IdVector storeIds;

    /**
     * Holds value of property exactOrderDateRangeEnd.
     */
    private Date exactOrderDateRangeEnd;

    public OrderStatusCriteriaData() {
        this._mAccountId           = "";
        this._mAccountIdVector     = new IdVector();
        this._mDistributorIdVector = new IdVector();
        this._mDistributorId       = "";
        this._mErpOrderNum         = "";
        this._mErpPONum            = "";
        this._mWebOrderConfirmationNum     = "";
        this._mOrderDateRangeBegin = "";
        this._mOrderDateRangeEnd   = "";
        this._mCustPONum           = "";
        this._mRefOrderNum           = "";
        this._mSiteId              = "";
        this._mSiteIdVector = new IdVector();
        this._mSiteZipCode         = "";
        this._mSiteCity            = "";
        this._mSiteState           = "";
        this._mOrderStatus         = "";
        this._mReferenceCode       = "";
        this._mShipFromId          = "";
        this._mPlacedBy            = "";
        this._mMethod              = "";
        this._mInvoiceDistNum      = "";
        this._mInvoiceCustNum      = "";
        this._mExceptionTypeCd     = "";
        this._mWorkflowId          = "";
        this._mOutboundPONum       = "";
        this._mSiteCountry         = "";

        this._mExcludeOrderStatus = new ArrayList();
        this._mOrderStatusList = new ArrayList();
        this._orderPropertyList = new ArrayList();
        this._mNewXpedex = false;

        this._retrieveOrderHistory = true;
        this._retrieveOrderItems = true;
        this._retrieveOrderAddresses = true;
        this._retrieveOrderAccount = true;
        this._retrieveOrderMetaData = true;
        this._retrieveOrderReceptionData = true;
        this._retrieveOrderAutoOrderData = true;
        this._retrieveOrderProperties = true;
    }

    /**
     * Creates a new OrderStatusCriteriaData
     *
     * @return
     *  Newly initialized OrderStatusCriteriaData object.
     */
    public static OrderStatusCriteriaData createValue ()
    {
        OrderStatusCriteriaData valueData = new OrderStatusCriteriaData();

        return valueData;
    }


    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this OrderStatusCriteriaData object
     */
    public String toString()
    {
        return "[" + "AccountId=" + _mAccountId + "AccountIdVector=" + _mAccountIdVector +", DistributorId=" + _mDistributorId +
            ", ErpOrderNum=" + _mErpOrderNum + ", ErpPONum=" + _mErpPONum +",OutboundPONum="+_mOutboundPONum + ", WebOrderConfirmationNum=" + _mWebOrderConfirmationNum +
            ", OrderDateRangeBegin=" + _mOrderDateRangeBegin + ", OrderDateRangeEnd=" + _mOrderDateRangeEnd +
            ", CustPONum=" + _mCustPONum + ", RefOrderNum=" + _mRefOrderNum + ", SiteId=" + _mSiteId + ", SiteIdVector=" + _mSiteIdVector + ", SiteZipCode=" + _mSiteZipCode +
            ", SiteCity=" + _mSiteCity + ", SiteState=" + _mSiteState +", SiteContry=" + _mSiteCountry +
            ", OrderStatus=" + _mOrderStatus + ", ReferenceCode=" + _mReferenceCode +
            ", ShipFromId=" + _mShipFromId + ", PlacedBy=" + _mPlacedBy + ", Method=" + _mMethod +
            ", InvoiceDistNum=" + _mInvoiceDistNum + ", InvoiceCustNum=" + _mInvoiceCustNum +
            ", UserId=" + _mUserId + ", UserTypeCd=" + _mUserTypeCd + ", ExceptionTypeCd=" + _mExceptionTypeCd +
            ", WorkflowId=" + _mWorkflowId + ", DistriutorIdVector=" + _mDistributorIdVector +
            ", NewXpedex=" + _mNewXpedex + ", _mMaxRows=" + _mMaxRows +
            ", orderPropertyList" + _orderPropertyList + 
            ", retrieveOrderHistory" + _retrieveOrderHistory + 
            ", retrieveOrderItems" + _retrieveOrderItems + 
            ", retrieveOrderAddresses" + _retrieveOrderAddresses + 
            ", retrieveOrderAccount" + _retrieveOrderAccount + 
            ", retrieveOrderMetaData" + _retrieveOrderMetaData + 
            ", retrieveOrderReceptionData" + _retrieveOrderReceptionData + 
            ", retrieveOrderAutoOrderData" + _retrieveOrderAutoOrderData + 
            ", retrieveOrderProperties" + _retrieveOrderProperties + 
            "]";
    }

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
        setDirty(true);
    }

    /**
     * <code>getAccountIdVector</code> method.
     *
     * @return a <code>IdVector</code> value
     */
    public IdVector getAccountIdVector() {
        return (this._mAccountIdVector);
    }

    /**
     * <code>setAccountIdVector</code> method.
     *
     * @param pVal a <code>IdVector</code> value
     */
    public void setAccountIdVector(IdVector pVal) {
        this._mAccountIdVector = pVal;
        setDirty(true);
    }

    /**
     * <code>getDistributorIdVector</code> method.
     *
     * @return a <code>IdVector</code> value
     */
    public IdVector getDistributorIdVector() {
        return (this._mDistributorIdVector);
    }

    /**
     * <code>setDistributorIdVector</code> method.
     *
     * @param pVal a <code>IdVector</code> value
     */
    public void setDistributorIdVector(IdVector pVal) {
        this._mDistributorIdVector = pVal;
        setDirty(true);
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
        setDirty(true);
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
        setDirty(true);
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
        setDirty(true);
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

    public IdVector getSiteIdVector() {
        return _mSiteIdVector;
    }

    public void setSiteIdVector(IdVector pVal) {
        this._mSiteIdVector = pVal;
        setDirty(true);
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
        setDirty(true);
    }


    /**
     * <code>getSiteCity</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getSiteCity() {
        return (this._mSiteCity);
    }

    /**
     * <code>setSiteCity</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setSiteCity(String pVal) {
        this._mSiteCity = pVal;
        setDirty(true);
    }


    /**
     * <code>getSiteState</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getSiteState() {
        return (this._mSiteState);
    }

    /**
     * <code>setSiteState</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setSiteState(String pVal) {
        this._mSiteState = pVal;
        setDirty(true);
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
        setDirty(true);
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
        setDirty(true);
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
        setDirty(true);
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
        setDirty(true);
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
        setDirty(true);
    }


    /**
     * <code>getUserId</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getUserId() {
        return (this._mUserId);
    }

    /**
     * <code>setUserId</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setUserId(String pVal) {
        this._mUserId = pVal;
        setDirty(true);
    }

    /**
     * <code>getUserTypeCd</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getUserTypeCd() {
        return (this._mUserTypeCd);
    }

    /**
     * <code>setUserTypeCd</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setUserTypeCd(String pVal) {
        this._mUserTypeCd = pVal;
        setDirty(true);
    }


    /**
     * <code>getExceptionTypeCd</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getExceptionTypeCd() {
        return (this._mExceptionTypeCd);
    }

    /**
     * <code>setExceptionTypeCd</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setExceptionTypeCd(String pVal) {
        this._mExceptionTypeCd = pVal;
        setDirty(true);
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
        setDirty(true);
    }

    public ArrayList getExcludeOrderStatusList(){
        return _mExcludeOrderStatus;
    }

    public ArrayList getOrderStatusList(){
        return _mOrderStatusList;
    }

    public void addOrderStatus(String pVal){
        _mOrderStatusList.add(pVal);
    }


    /**
     * Getter for property storeIds.
     * @return Value of property storeIds.
     */
    public IdVector getStoreIdVector() {

        return this.storeIds;
    }

    /**
     * Setter for property storeIds.
     * @param storeIds New value of property storeIds.
     */
    public void setStoreIdVector(IdVector storeIds) {

        this.storeIds = storeIds;
    }

    /**
     * Getter for property exactOrderDateRangeEnd.
     * @return Value of property exactOrderDateRangeEnd.
     */
    public Date getExactOrderDateRangeEnd() {

        return this.exactOrderDateRangeEnd;
    }

    /**
     * Setter for property exactOrderDateRangeEnd.
     * @param exactOrderDateRangeEnd New value of property exactOrderDateRangeEnd.
     */
    public void setExactOrderDateRangeEnd(Date exactOrderDateRangeEnd) {

        this.exactOrderDateRangeEnd = exactOrderDateRangeEnd;
    }

    /**
     *Only fetch orders taht have not been received
     */
    public void setOrdersNotReceivedOnly(){
        mNotRecvdOnly = true;
    }

    public boolean getNewXpedex(){
        return _mNewXpedex;
    }

    public void setNewXpedex(){
        this._mNewXpedex = true;
    }

    /**
     *Only fetch orders taht have not been received
     */
    public boolean getOrdersNotReceivedOnly(){
        return mNotRecvdOnly;
    }
    public String getOutboundPONum() {
        return _mOutboundPONum;
    }

    public void setOutboundPONum(String outboundPONum) {
        this._mOutboundPONum = outboundPONum;
    }

    public void setSiteCountry(String siteCountry) {
        this._mSiteCountry = siteCountry;
    }

    public String getSiteCountry() {
        return _mSiteCountry;
    }
    
    /**
     * <code>getMaxRows</code> method.
     *
     * @return a <code>int</code> value
     */
    public int getMaxRows() {
        return (this._mMaxRows);
    }

    /**
     * <code>setMaxRows</code> method.
     *
     * @param pVal a <code>int</code> value
     */
    public void setMaxRows(int pVal) {
        this._mMaxRows = pVal;
    }

	/**
	 * @return the orderPropertyList
	 */
	public List<String> getOrderPropertyList() {
		return _orderPropertyList;
	}

	/**
	 * @param orderPropertyList the orderPropertyList to set
	 */
	public void setOrderPropertyList(List<String> orderPropertyList) {
		_orderPropertyList = orderPropertyList;
	}

	/**
	 * @return the retrieveOrderHistory
	 */
	public boolean isRetrieveOrderHistory() {
		return _retrieveOrderHistory;
	}

	/**
	 * @param retrieveOrderHistory the retrieveOrderHistory to set
	 */
	public void setRetrieveOrderHistory(boolean retrieveOrderHistory) {
		_retrieveOrderHistory = retrieveOrderHistory;
	}

	/**
	 * @return the retrieveOrderItems
	 */
	public boolean isRetrieveOrderItems() {
		return _retrieveOrderItems;
	}

	/**
	 * @param retrieveOrderItems the retrieveOrderItems to set
	 */
	public void setRetrieveOrderItems(boolean retrieveOrderItems) {
		_retrieveOrderItems = retrieveOrderItems;
	}

	/**
	 * @return the retrieveOrderAddresses
	 */
	public boolean isRetrieveOrderAddresses() {
		return _retrieveOrderAddresses;
	}

	/**
	 * @param retrieveOrderAddresses the retrieveOrderAddresses to set
	 */
	public void setRetrieveOrderAddresses(boolean retrieveOrderAddresses) {
		_retrieveOrderAddresses = retrieveOrderAddresses;
	}

	/**
	 * @return the retrieveOrderAccount
	 */
	public boolean isRetrieveOrderAccount() {
		return _retrieveOrderAccount;
	}

	/**
	 * @param retrieveOrderAccount the retrieveOrderAccount to set
	 */
	public void setRetrieveOrderAccount(boolean retrieveOrderAccount) {
		_retrieveOrderAccount = retrieveOrderAccount;
	}

	/**
	 * @return the retrieveOrderMetaData
	 */
	public boolean isRetrieveOrderMetaData() {
		return _retrieveOrderMetaData;
	}

	/**
	 * @param retrieveOrderMetaData the retrieveOrderMetaData to set
	 */
	public void setRetrieveOrderMetaData(boolean retrieveOrderMetaData) {
		_retrieveOrderMetaData = retrieveOrderMetaData;
	}

	/**
	 * @return the retrieveOrderReceptionData
	 */
	public boolean isRetrieveOrderReceptionData() {
		return _retrieveOrderReceptionData;
	}

	/**
	 * @param retrieveOrderReceptionData the retrieveOrderReceptionData to set
	 */
	public void setRetrieveOrderReceptionData(boolean retrieveOrderReceptionData) {
		_retrieveOrderReceptionData = retrieveOrderReceptionData;
	}

	/**
	 * @return the retrieveOrderAutoOrderData
	 */
	public boolean isRetrieveOrderAutoOrderData() {
		return _retrieveOrderAutoOrderData;
	}

	/**
	 * @param retrieveOrderAutoOrderData the retrieveOrderAutoOrderData to set
	 */
	public void setRetrieveOrderAutoOrderData(boolean retrieveOrderAutoOrderData) {
		_retrieveOrderAutoOrderData = retrieveOrderAutoOrderData;
	}

	/**
	 * @return the retrieveOrderProperties
	 */
	public boolean isRetrieveOrderProperties() {
		return _retrieveOrderProperties;
	}

	/**
	 * @param retrieveOrderProperties the retrieveOrderProperties to set
	 */
	public void setRetrieveOrderProperties(boolean retrieveOrderProperties) {
		_retrieveOrderProperties = retrieveOrderProperties;
	}

	public String getOrderBudgetTypeCd() {
		return _orderBudgetTypeCd;
	}

	public void setOrderBudgetTypeCd(String pOrderBudgetTypeCd) {
		_orderBudgetTypeCd = pOrderBudgetTypeCd;
	}

	public boolean isFilterByRevisedOrderDate() {
		return _filterByRevisedOrderDate;
	}

	public void setFilterByRevisedOrderDate(boolean filterByRevisedOrderDate) {
		_filterByRevisedOrderDate = filterByRevisedOrderDate;
	}

	/**
	 * @return the orderBy
	 */
	public String getOrderBy() {
		String returnValue = null;
		if (OrderDataAccess.REVISED_ORDER_DATE.equals(_orderBy)) {
			returnValue = "(TO_DATE (TO_CHAR(NVL(REVISED_ORDER_DATE,ORIGINAL_ORDER_DATE),'MM/DD/YYYY')||TO_CHAR(NVL(REVISED_ORDER_TIME,ORIGINAL_ORDER_TIME),'hh24:mi:ss'), 'MM/DD/YYYY hh24:mi:ss'))";
		}
		else {
			returnValue = _orderBy;
		}
		return returnValue;
	}

	/**
	 * @param orderBy the orderBy to set
	 */
	public void setOrderBy(String orderBy) {
		_orderBy = orderBy;
	}

	/**
	 * @return the orderDirection
	 */
	public String getOrderDirection() {
		return _orderDirection;
	}

	/**
	 * @param orderDirection the orderDirection to set
	 */
	public void setOrderDirection(String orderDirection) {
		_orderDirection = orderDirection;
	}

	public void setIncludeRelatedOrder(boolean includeRelatedOrder) {
		this._includeRelatedOrder = includeRelatedOrder;
	}

	public boolean isIncludeRelatedOrder() {
		return _includeRelatedOrder;
	}

}
