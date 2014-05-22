package com.cleanwise.view.forms;
import com.cleanwise.service.api.value.OrderStatusDescDataVector;
import com.cleanwise.service.api.value.PairViewVector;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;
/**
 * Form bean for the order search page.
 *
 * @author alukovnikov
 */
public final class OrderSearchForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private boolean init=false;

    private OrderStatusDescDataVector _resultList = new OrderStatusDescDataVector();

    private String _mWebOrderConfirmationNum     = new String("");
    private String _mOrderDateRangeBegin = new String("");
    private String _mOrderDateRangeEnd   = new String("");
    private String _mCustPONum           = new String("");
    private String _mSiteCity            = new String("");
    private String _mSiteState           = new String("");
    private String _mSiteZipCode         = new String("");
    private String _mSiteCountry         = new String("");
    private String _mSiteSiteId          = new String("");
    private String _mOrderStatus         = new String("");
    private List<String> _orderStatusList;

    private List _mStateValueList;
    private PairViewVector _mSiteValuePairs;
    private List _mCountryValueList;
    private List _mStatusValueList;
    private boolean mShowLocation;
    private String confirmation = null;
    private Object[] mCountryAndStateLinks;
    private boolean mShowSearchFields = true;
    private String[] selectIds;
    private List<String> _orderPropertyList;
    
    //STJ-4759
    private boolean mFilterByRevisedOrderDate = false;
    
    public String[] getSelectIds() {
        return selectIds;
    }

    public void setSelectIds(String[] selectIds) {
        this.selectIds = selectIds;
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
        if (null == pVal) {
            this._resultList = new OrderStatusDescDataVector();
            return;
        }
        this._resultList = pVal;
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
    	this._mCustPONum= "";
    	this._mWebOrderConfirmationNum="";
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

    /**
     * Holds value of property ordersNotReceivedOnly.
     */
    private String ordersNotReceivedOnly = "n";

    /**
     * Getter for property ordersNotReceivedOnly.
     * @return Value of property ordersNotReceivedOnly.
     */
    public String getOrdersNotReceivedOnly() {

        return this.ordersNotReceivedOnly;
    }

    /**
     * Setter for property ordersNotReceivedOnly.
     * @param ordersNotReceivedOnly New value of property ordersNotReceivedOnly.
     */
    public void setOrdersNotReceivedOnly(String ordersNotReceivedOnly) {

        this.ordersNotReceivedOnly = ordersNotReceivedOnly;
    }

    public void setSiteCountry(String country) {
        this._mSiteCountry = country;
    }

    public String getSiteCountry() {
        return _mSiteCountry;
    }

    public void setStateValueList(List stateValueList) {
        this._mStateValueList = stateValueList;
    }

    public void setSiteValuePairs(PairViewVector siteValuePairs) {
        this._mSiteValuePairs = siteValuePairs;
    }

    public void setCountryValueList(List countryValueList) {
        this._mCountryValueList = countryValueList;
    }

    public List getStateValueList() {
        return _mStateValueList;
    }

    public PairViewVector getSiteValuePairs() {
        return _mSiteValuePairs;
    }

    public List getCountryValueList() {
        return _mCountryValueList;
    }

    public String getSiteSiteId() {
        return _mSiteSiteId;
    }

    public void setSiteSiteId(String siteId) {
        this._mSiteSiteId = siteId;
    }

    public void setStatusValueList(List statusValueList) {
        this._mStatusValueList = statusValueList;
    }

    public List getStatusValueList() {
        return _mStatusValueList;
    }

    public void setOrderStatus(String orderStatus) {
        this._mOrderStatus = orderStatus;
    }

    public String getOrderStatus() {
        return _mOrderStatus;
    }

    /**
	 * @return the orderStatusList
	 */
	public List<String> getOrderStatusList() {
		if (_orderStatusList == null) {
			_orderStatusList = new ArrayList<String>();
		}
		return _orderStatusList;
	}

	/**
	 * @param mOrderStatusList the mOrderStatusList to set
	 */
	public void setOrderStatusList(List<String> orderStatusList) {
		_orderStatusList = orderStatusList;
	}

	/**
	 * @return the orderPropertyList
	 */
	public List<String> getOrderPropertyList() {
		if (_orderPropertyList == null) {
			_orderPropertyList = new ArrayList<String>();
		}
		return _orderPropertyList;
	}

	/**
	 * @param orderPropertyList the orderPropertyList to set
	 */
	public void setOrderPropertyList(List<String> orderPropertyList) {
		_orderPropertyList = orderPropertyList;
	}

	public void setConfirmationMessage(String confirm) {
        this.confirmation = confirm;
    }

    public String getConfirmationMessage() {
        return confirmation;
    }

    public void init() {
        this.init = true;
    }

    public boolean isInit() {
        return init;
    }

    public void setShowLocation(boolean showLocation) {
        this.mShowLocation = showLocation;
    }

    public boolean getShowLocation() {
        return mShowLocation;
    }

    public void setCountryAndStateLinks(Object[] countryAndStateLinks) {
        this.mCountryAndStateLinks = countryAndStateLinks;
    }

    public Object[] getCountryAndStateLinks() {
        return mCountryAndStateLinks;
    }

    public boolean isShowSearchFields() {
        return mShowSearchFields;
    }

    public void setShowSearchFields(boolean pShowSearchFields) {
        this.mShowSearchFields = pShowSearchFields;
    }
    
    public void setResultListAndShowSearchFields(OrderStatusDescDataVector pVal, boolean pShowSearchFields) {
        this.setResultList(pVal);
        setShowSearchFields(pShowSearchFields);
    }

	public boolean isFilterByRevisedOrderDate() {
		return mFilterByRevisedOrderDate;
	}

	public void setFilterByRevisedOrderDate(boolean filterByRevisedOrderDate) {
		mFilterByRevisedOrderDate = filterByRevisedOrderDate;
	}
    
    
}
