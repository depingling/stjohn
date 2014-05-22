/**
 * Title: OrderSearchDto 
 * Description: This is a data transfer object holding order search criteria and results.
 */

package com.cleanwise.service.api.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.cleanwise.service.api.dto.template.OrderDto;
import com.cleanwise.service.api.value.OrderInfoBase;
import com.cleanwise.view.utils.Constants;

public class OrderSearchDto extends OrderDto implements Serializable {
	
	private static final long serialVersionUID = 8214769818345853737L;
	private String _userId;
	private String _locationId;
	private Collection<OrderInfoBase> _matchingOrders;

	private String _locations;
	private String _locationSelected;
	private String _orderStatus;
	private String _dateRange;
	private String _from;
	private String _to;
	private String _sortField;
	private String _sortOrder;
	private boolean _ordersNotReceived;
	private List<String> _orderStatuses;
	private List<String> _orderProperties;
	private boolean _retrieveOrderHistory;
	private boolean _retrieveOrderItems;
	private boolean _retrieveOrderAddresses;
	private boolean _retrieveOrderAccount;
	private boolean _retrieveOrderMetaData;
	private boolean _retrieveOrderReceptionData;
	private boolean _retrieveOrderAutoOrderData;
	private boolean _retrieveOrderProperties;
	private int[] _siteId;
	private boolean _ordersAccountsSupportBudget;
	
    /**
     * Create a completely empty new OrderSearchDto
     */
    public OrderSearchDto() {
    	super();
    	setUserId(StringUtils.EMPTY);
    	setLocationId(StringUtils.EMPTY);
    	setMatchingOrders(null);

    	setLocations(StringUtils.EMPTY);
    	setOrderStatus(StringUtils.EMPTY);
    	setDateRange(StringUtils.EMPTY);
    	setFrom(Constants.ORDERS_DATE_RANGE_FORMAT);
    	setTo(Constants.ORDERS_DATE_RANGE_FORMAT);
    	setOrderNumber(StringUtils.EMPTY);
    	setPurchaseOrderNumber(StringUtils.EMPTY);
    	setSortOrder(StringUtils.EMPTY);
    	setLocationSelected(StringUtils.EMPTY);
    	setOrdersNotReceived(false);
    	setOrderStatuses(null);
    	setOrderProperties(null);
    	setRetrieveOrderHistory(true);
    	setRetrieveOrderItems(true);
    	setRetrieveOrderAddresses(true);
    	setRetrieveOrderAccount(true);
    	setRetrieveOrderMetaData(true);
    	setRetrieveOrderReceptionData(true);
    	setRetrieveOrderAutoOrderData(true);
    	setRetrieveOrderProperties(true);
    }
    
    /**
     * Create a completely new OrderSearchDto initialized with a user id.
     * @param  userId  a <code>String</code> containing the id of the user for whom orders
     * 	are being searched.
     */
    public OrderSearchDto(String userId) {
    	this();
    	setUserId(userId);
    }
    
    /**
     * Create a completely new OrderSearchDto initialized with a user id and location id.
     * @param  userId  a <code>String</code> containing the id of the user for whom orders
     * 	are being searched.
     * @param  locationId  a <code>String</code> containing the id of the location for which 
     * 	orders are being searched.
     */
    public OrderSearchDto(String userId, String locationId) {
    	this();
    	setUserId(userId);
    	setLocationId(locationId);
    }

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return _userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		_userId = userId;
	}

	/**
	 * @return the locationId
	 */
	public String getLocationId() {
		return _locationId;
	}

	/**
	 * @param locationId the locationId to set
	 */
	public void setLocationId(String locationId) {
		_locationId = locationId;
	}

	/**
	 * @return the matchingOrders
	 */
	public Collection<OrderInfoBase> getMatchingOrders() {
		if (_matchingOrders == null) {
			_matchingOrders = new ArrayList<OrderInfoBase>();
		}
		return _matchingOrders;
	}

	/**
	 * @param matchingOrders the matchingOrders to set
	 */
	public void setMatchingOrders(Collection<OrderInfoBase> matchingOrders) {
		_matchingOrders = matchingOrders;
	}

	
	/**
	 * 
	 * @return locations
	 */
	public String getLocations() {
		return _locations;
	}
	
	/**
	 * 
	 * @param locations - locations to set
	 */
	public void setLocations(String locations) {
		_locations = locations;
	}
	
	/**
	 * 
	 * @return orderStatus
	 */
	public String getOrderStatus() {
		return _orderStatus;
	}
	
	/**
	 * 
	 * @param orderStatus
	 */
	public void setOrderStatus(String orderStatus) {
		_orderStatus = orderStatus;
	}

	/**
	 * 
	 * @return dateRange.
	 */
	public String getDateRange() {
		return _dateRange;
	}
	
	/**
	 * 
	 * @param dateRange
	 */
	public void setDateRange(String dateRange) {
		_dateRange = dateRange;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getFrom() {
		return _from;
	}
	
	/**
	 * 
	 * @param from - custom from Date
	 */
	public void setFrom(String from) {
		_from = from;
	}
	
	/**
	 * 
	 * @return to
	 */
	public String getTo() {
		return _to;
	}
	
	/**
	 * 
	 * @param to - custom to date.
	 */
	public void setTo(String to) {
		_to = to;
	}
	
	/**
     * <code>getListCount</code> method.
     *
     * @return a <code>int</code> value
     */
    public int getListCount() {
        return (getMatchingOrders().size());
    }

	public String getSortOrder() {
		return _sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		_sortOrder = sortOrder;
	}
	
	/**
	 * @return the sortField
	 */
	public String getSortField() {
		return _sortField;
	}
	
	/**
	 * @param sortField the sortField to set
	 */
	public void setSortField(String sortField) {
		_sortField = sortField;
	}

	/**
	 * 
	 * @return
	 */
	public String getLocationSelected() {
		return _locationSelected;
	}

	/**
	 * 
	 * @param locationSelected
	 */
	public void setLocationSelected(String locationSelected) {
		_locationSelected = locationSelected;
	}

	/**
	 * @return the ordersReceived
	 */
	public boolean isOrdersNotReceived() {
		return _ordersNotReceived;
	}

	/**
	 * @param pOrdersReceived the ordersReceived to set
	 */
	public void setOrdersNotReceived(boolean pOrdersNotReceived) {
		_ordersNotReceived = pOrdersNotReceived;
	}

	/**
	 * @return the orderStatuses
	 */
	public List<String> getOrderStatuses() {
		return _orderStatuses;
	}

	/**
	 * @param orderStatuses the orderStatuses to set
	 */
	public void setOrderStatuses(List<String> orderStatuses) {
		_orderStatuses = orderStatuses;
	}

	/**
	 * @return the orderProperties
	 */
	public List<String> getOrderProperties() {
		return _orderProperties;
	}

	/**
	 * @param orderProperties the orderProperties to set
	 */
	public void setOrderProperties(List<String> orderProperties) {
		_orderProperties = orderProperties;
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

    public int[] getSiteId() {
        return _siteId;
    }

    public void setSiteId(int[] siteId) {
        this._siteId = siteId;
    }

    /**
	 * Method to determine if only basic order information is to be retrieved.
	 * @return  true if only basic information is to be retrieved, false otherwise.
	 */
	public boolean isRetrieveBasicInfoOnly() {
		boolean retrievingDetails = isRetrieveOrderAccount() || isRetrieveOrderAddresses() ||
									isRetrieveOrderAutoOrderData() || isRetrieveOrderHistory() ||
									isRetrieveOrderItems() || isRetrieveOrderMetaData() ||
									isRetrieveOrderProperties() || isRetrieveOrderReceptionData();
		return !retrievingDetails;
	}
	
	/**
	 * @return the _ordersAccountsSupportBudget
	 */
	public boolean isOrdersAccountsSupportBudget() {
		return _ordersAccountsSupportBudget;
	}

	/**
	 * @param _ordersAccountsSupportBudget the _ordersAccountsSupportBudget to set
	 */
	public void setOrdersAccountsSupportBudget(boolean pOrdersAccountsSupportBudget) {
		_ordersAccountsSupportBudget = pOrdersAccountsSupportBudget;
	}

}
