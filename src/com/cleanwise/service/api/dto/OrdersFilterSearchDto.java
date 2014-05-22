/**
 * Title: OrdersFilterSearchDto 
 * Description: This is a data transfer object holding all orders filters search criteria.

 */
package com.cleanwise.service.api.dto;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import com.cleanwise.service.api.dto.template.OrderDto;
import com.cleanwise.service.api.value.OrderStatusDescDataVector;
import com.cleanwise.view.utils.Constants;

public class OrdersFilterSearchDto extends OrderDto implements Serializable {
	
	
	private static final long serialVersionUID = 6278486712959045179L;
	private String _userId;
	private String _locations;
	private String _locationSelected;
	private String _orderStatus;
	private String _dateRange;
	private String _from;
	private String _to;
	private OrderStatusDescDataVector _resultList;
	private String _sortField;
	private String _sortOrder;
	
	/**
     * Create a completely empty new LocationSearchDto
     */
    public OrdersFilterSearchDto() {
    	super();
    	setUserId(StringUtils.EMPTY);
    	setLocations(StringUtils.EMPTY);
    	setOrderStatus(StringUtils.EMPTY);
    	setDateRange(StringUtils.EMPTY);
    	setFrom(Constants.ORDERS_DATE_RANGE_FORMAT);
    	setTo(Constants.ORDERS_DATE_RANGE_FORMAT);
    	setOrderNumber(StringUtils.EMPTY);
    	setPurchaseOrderNumber(StringUtils.EMPTY);
    	_resultList = new OrderStatusDescDataVector(); 
    	setSortOrder(StringUtils.EMPTY);
    	setLocationSelected(StringUtils.EMPTY);
    }
    
    /**
     * Create a completely new OrdersFilterSearchDto initialized with a user id.
     * @param  userId  a <code>String</code> containing the user id of the user for whom orders
     * 	are being searched.
     */
    public OrdersFilterSearchDto(String userId) {
    	this();
    	setUserId(userId);
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
	 * 
	 * @return
	 */
	public OrderStatusDescDataVector getResultList() {
		return _resultList;
	}

	/**
	 * 
	 * @param resultList
	 */
	public void setResultList(OrderStatusDescDataVector resultList) {
		if (resultList==null) {
           _resultList = new OrderStatusDescDataVector();
        } else {
        	_resultList = resultList;
        }
	}
	
	/**
     * <code>getListCount</code> method.
     *
     * @return a <code>int</code> value
     */
    public int getListCount() {
        if (_resultList==null) {
            this._resultList = new OrderStatusDescDataVector();
        }
        return (_resultList.size());
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
	
	
}

