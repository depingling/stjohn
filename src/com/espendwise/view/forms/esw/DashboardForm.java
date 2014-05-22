/**
 * Title: DashboardForm 
 * Description: This is the Struts ActionForm class handling the ESW UI Dash board functionality.
 *
 */

package com.espendwise.view.forms.esw;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

import com.cleanwise.service.api.dto.LocationSearchDto;
import com.cleanwise.service.api.dto.OrderSearchDto;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.OrderPropertyData;
import com.cleanwise.service.api.value.OrderStatusDescData;
import com.cleanwise.service.api.value.ProductData;
import com.cleanwise.service.api.value.SiteData;

/**
 * Implementation of <code>ActionForm</code> that handles dash board functionality.
 */
public final class DashboardForm extends EswForm {
	private static final long serialVersionUID = 1329530174652249289L;
	
	private List<LabelValueBean> _previousOrderDateRangeChoices;
	private LocationSearchDto _locationSearchInfo;
	private SiteData _location;
	private OrderSearchDto _previousOrderSearchInfo;
	private OrderSearchDto _mostRecentOrderSearchInfo;
	private OrderSearchDto _pendingOrderSearchInfo;
	private OrderPropertyData _orderComment;
	private String[] _selectedOrderIds;
	private String _approvalDate;
	private Map<Integer, ProductData> _productDataByItemIdMap;

	/**
	 * @return the previousOrderDateRangeChoices
	 */
	public List<LabelValueBean> getPreviousOrderDateRangeChoices() {
		return _previousOrderDateRangeChoices;
	}

	/**
	 * @param previousOrderDateRangeChoices the previousOrderDateRangeChoices to set
	 */
	public void setPreviousOrderDateRangeChoices(
			List<LabelValueBean> previousOrderDateRangeChoices) {
		_previousOrderDateRangeChoices = previousOrderDateRangeChoices;
	}

	/**
	 * @return the locationSearchInfo
	 */
	public LocationSearchDto getLocationSearchInfo() {
		if (_locationSearchInfo == null) {
			_locationSearchInfo = new LocationSearchDto();
		}
		return _locationSearchInfo;
	}

	/**
	 * @param locationSearchInfo the locationSearchInfo to set
	 */
	public void setLocationSearchInfo(LocationSearchDto locationSearchInfo) {
		_locationSearchInfo = locationSearchInfo;
	}

	/**
	 * @return the location
	 */
	public SiteData getLocation() {
		if (_location == null) {
			_location = SiteData.createValue();
			_location.setBusEntity(BusEntityData.createValue());
		}
		return _location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(SiteData location) {
		_location = location;
	}

	/**
	 * @return the previousOrderSearchInfo
	 */
	public OrderSearchDto getPreviousOrderSearchInfo() {
		if (_previousOrderSearchInfo == null) {
			_previousOrderSearchInfo = new OrderSearchDto();
		}
		return _previousOrderSearchInfo;
	}

	/**
	 * @param previousOrderSearchInfo the previousOrderSearchInfo to set
	 */
	public void setPreviousOrderSearchInfo(OrderSearchDto previousOrderSearchInfo) {
		_previousOrderSearchInfo = previousOrderSearchInfo;
	}

	/**
	 * @return the mostRecentOrderSearchInfo
	 */
	public OrderSearchDto getMostRecentOrderSearchInfo() {
		if (_mostRecentOrderSearchInfo == null) {
			_mostRecentOrderSearchInfo = new OrderSearchDto();
		}
		return _mostRecentOrderSearchInfo;
	}

	/**
	 * @param mostRecentOrderSearchInfo the mostRecentOrderSearchInfo to set
	 */
	public void setMostRecentOrderSearchInfo(OrderSearchDto mostRecentOrderSearchInfo) {
		_mostRecentOrderSearchInfo = mostRecentOrderSearchInfo;
	}

	/**
	 * @return the pendingOrderSearchInfo
	 */
	public OrderSearchDto getPendingOrderSearchInfo() {
		if (_pendingOrderSearchInfo == null) {
			_pendingOrderSearchInfo = new OrderSearchDto();
		}
		return _pendingOrderSearchInfo;
	}

	/**
	 * @param pendingOrderSearchInfo the pendingOrderSearchInfo to set
	 */
	public void setPendingOrderSearchInfo(OrderSearchDto pendingOrderSearchInfo) {
		_pendingOrderSearchInfo = pendingOrderSearchInfo;
	}

	/**
	 * @return the orderComment
	 */
	public OrderPropertyData getOrderComment() {
		if (_orderComment == null) {
			_orderComment = OrderPropertyData.createValue();
		}
		return _orderComment;
	}

	/**
	 * @param orderComment the orderComment to set
	 */
	public void setOrderComment(OrderPropertyData orderComment) {
		_orderComment = orderComment;
	}

	/**
	 * @return the selectedOrderIds
	 */
	public String[] getSelectedOrderIds() {
		if (_selectedOrderIds == null) {
			_selectedOrderIds = new String[0];
		}
		return _selectedOrderIds;
	}

	/**
	 * @param selectedOrderIds the selectedOrderIds to set
	 */
	public void setSelectedOrderIds(String[] selectedOrderIds) {
		_selectedOrderIds = selectedOrderIds;
	}

	/**
	 * @return the approvalDate
	 */
	public String getApprovalDate() {
		return _approvalDate;
	}

	/**
	 * @param approvalDate the approvalDate to set
	 */
	public void setApprovalDate(String approvalDate) {
		_approvalDate = approvalDate;
	}

    /**
     * 
     * @return the productDataByItemIdMap
     */
    public Map<Integer, ProductData> getProductDataByItemIdMap() {
        return _productDataByItemIdMap;
    }

    /**
     * 
     * @param productDataByItemIdMap
     *            the productDataByItemIdMap to set
     */
    public void setProductDataByItemIdMap(
            Map<Integer, ProductData> productDataByItemIdMap) {
        this._productDataByItemIdMap = productDataByItemIdMap;
    }
    
    /**
     * Method to determine if the current list of pending orders contains any orders the
     * current user is allowed to approve.
     * @return true if the current list of pending orders contains at least one order the
     * current user can approve, false otherwise.
     */
    public boolean isFoundApprovablePendingOrder() {
    	boolean returnValue = false;
    	ArrayList pendingOrders = (ArrayList)getPendingOrderSearchInfo().getMatchingOrders();
    	if (Utility.isSet(pendingOrders)) {
    		Iterator<OrderStatusDescData> pendingOrderIterator = pendingOrders.iterator();
    		while (pendingOrderIterator.hasNext() && !returnValue) {
    			OrderStatusDescData pendingOrder = pendingOrderIterator.next();
    			returnValue = Utility.isSet(pendingOrder.getUserApprovableReasonCodeIds());
    		}
    	}
    	return returnValue;
    }

    /**
     * Reset all properties to their default values.
     * @param  mapping  The mapping used to select this instance
     * @param  request  The servlet request we are processing
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
    	super.reset(mapping, request);
    	_previousOrderDateRangeChoices = null;
    	_locationSearchInfo = null;
    	_location = null;
    	_previousOrderSearchInfo = null;
    	_mostRecentOrderSearchInfo = null;
    	_pendingOrderSearchInfo = null;
    	_orderComment = null;
    	_selectedOrderIds = null;
    	_approvalDate = null;
    }

    /**
     * Validate the properties that have been set from this HTTP request, and
     * return an <code>ActionErrors</code> object that encapsulates any
     * validation errors that have been found. If no errors are found, return
     * <code>null</code> or an <code>ActionErrors</code> object with no
     * recorded error messages.
     *@param  mapping  The mapping used to select this instance
     *@param  request  The servlet request we are processing
     *@return          Description of the Returned Value
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
    	//no validation is performed at the form level, so return null.
    	ActionErrors returnValue = null;
        return returnValue;
    }

}

