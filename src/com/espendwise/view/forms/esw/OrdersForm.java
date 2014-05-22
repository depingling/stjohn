/**
 * Title: OrdersForm 
 * Description: This is the Struts ActionForm class handling the ESW order functionality.
 *
 */

package com.espendwise.view.forms.esw;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

import com.cleanwise.service.api.dto.OrderSearchDto;
import com.cleanwise.service.api.util.SessionDataUtil;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.DeliveryScheduleViewVector;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.OrderPropertyData;
import com.cleanwise.service.api.value.ProductData;
import com.cleanwise.service.api.value.PropertyDataVector;
import com.cleanwise.service.api.value.OrderPropertyDataVector;
import com.cleanwise.service.api.value.OrderItemDescData;
import com.cleanwise.service.api.value.OrderItemDescDataVector;
import com.cleanwise.view.forms.OrderOpDetailForm;
import com.cleanwise.view.forms.OrderSchedulerForm;
import com.cleanwise.view.forms.UserShopForm;

/**
 * Implementation of <code>ActionForm</code> that handles order functionality.
 */
public final class OrdersForm extends EswForm {

	private static final long serialVersionUID = 8599701115019954266L;
	
	//Orders: Begin
	private List<LabelValueBean> _ordersStatusFieldChoices;
	private List<LabelValueBean> _ordersDateRangeFieldChoices;
	private List<LabelValueBean> _futureOrdersDateRangeFieldChoices;
	private List<LabelValueBean> _ordersLocationFieldChoices;
	private OrderSearchDto _ordersSearchInfo;
	private OrderSearchDto _futureOrdersSearchInfo;

	private String _orderId;
	private OrderPropertyData _orderComment;
	
	// for Order-Detail, Pending-Order-Detail panels(screens): Begin
	private OrderOpDetailForm _orderOpDetailForm;
	private Map<String,OrderItemDescDataVector> distItemMap;
	private Map<Integer, ProductData> _productDataByItemIdMap;
	private IdVector _notesUserApproveIdV;
	private PropertyDataVector _accountMiscProperties;
	private String _approvalDate;
	private UserShopForm _userShopForm;
	private boolean hasFunctionReceiving;
	private boolean _isBillingOrder;
	private boolean _isRebillOrder;
	private OrderPropertyDataVector _allOrderProperties;
	private OrderItemDescDataVector  _mOrderItemDescList;
	// for Order-Detail, Pending-Order-Detail panels(screens): End
	
	private OrderSchedulerForm _orderSchedulerForm;
	private DeliveryScheduleViewVector  _parOrderSchedules;
	
	//Begin: Remote Access
	private boolean _isRemoteAccess;
	//End: Remote Access


	private String _orderNumSearchValue;
	private String _activeTab;

	public String getOrderNumSearchValue() {
		return _orderNumSearchValue;
	}

	public void setOrderNumSearchValue(String v) {
		_orderNumSearchValue = v;
	}

	public String getActiveTab() {
        return _activeTab;
    }
    public void setActiveTab(String v) {
        _activeTab = v;
    }

	/**
	 * 
	 * @return ordersStatusFieldChoices
	 */
	public List<LabelValueBean> getOrdersStatusFieldChoices() {
		return _ordersStatusFieldChoices;
	}

	/**
	 * 
	 * @param ordersStatusFieldChoices - the ordersStatusFieldChoices to set
	 */
	public void setOrdersStatusFieldChoices(
			List<LabelValueBean> ordersStatusFieldChoices) {
		_ordersStatusFieldChoices = ordersStatusFieldChoices;
	}
	
	/**
	 * 
	 * @return ordersDateRangeFieldChoices
	 */
	public List<LabelValueBean> getOrdersDateRangeFieldChoices() {
		return _ordersDateRangeFieldChoices;
	}

	/**
	 * 
	 * @param ordersDateRangeFieldChoices - ordersDateRangeFieldChoices to set
	 */
	public void setOrdersDateRangeFieldChoices(
			List<LabelValueBean> ordersDateRangeFieldChoices) {
		_ordersDateRangeFieldChoices = ordersDateRangeFieldChoices;
	}

	/**
	 * @return the _futureOrdersDateRangeFieldChoices
	 */
	public final List<LabelValueBean> getFutureOrdersDateRangeFieldChoices() {
		return _futureOrdersDateRangeFieldChoices;
	}

	/**
	 * @param futureOrdersDateRangeFieldChoices the _futureOrdersDateRangeFieldChoices to set
	 */
	public final void setFutureOrdersDateRangeFieldChoices(
			List<LabelValueBean> futureOrdersDateRangeFieldChoices) {
		_futureOrdersDateRangeFieldChoices = futureOrdersDateRangeFieldChoices;
	}

	/**
	 * 
	 * @return ordersSearchInfo
	 */
	public OrderSearchDto getOrdersSearchInfo() {
		if(_ordersSearchInfo==null) {
			_ordersSearchInfo = new OrderSearchDto();
		}
		return _ordersSearchInfo;
	}

	/**
	 * 
	 * @param ordersSearchInfo - orderSearchDto Info to set
	 */
	public void setOrdersSearchInfo(OrderSearchDto ordersSearchInfo) {
		_ordersSearchInfo = ordersSearchInfo;
	}
	
	/**
	 * @return the _futureOrdersSearchInfo
	 */
	public final OrderSearchDto getFutureOrdersSearchInfo() {
		if(_futureOrdersSearchInfo==null) {
			_futureOrdersSearchInfo = new OrderSearchDto();
		}
		return _futureOrdersSearchInfo;
	}

	/**
	 * @param futureOrdersSearchInfo the _futureOrdersSearchInfo to set
	 */
	public final void setFutureOrdersSearchInfo(
			OrderSearchDto futureOrdersSearchInfo) {
		_futureOrdersSearchInfo = futureOrdersSearchInfo;
	}

	/**
	 * 
	 * @return the orderId
	 */
	public String getOrderId() {
		if(_orderId==null){
			_orderId=StringUtils.EMPTY;
		}
		return _orderId;
	}

	/**
	 * 
	 * @param orderId - the orderId to set
	 */
	public void setOrderId(String orderId) {
		_orderId = orderId;
	}

	/**
	 * @return the orderSchedulerForm
	 */
	public OrderSchedulerForm getOrderSchedulerForm() {
		if (_orderSchedulerForm == null) {
			_orderSchedulerForm = new OrderSchedulerForm();
		}
		return _orderSchedulerForm;
	}

	/**
	 * @param orderSchedulerForm the orderSchedulerForm to set
	 */
	public void setOrderSchedulerForm(OrderSchedulerForm orderSchedulerForm) {
		_orderSchedulerForm = orderSchedulerForm;
	}

	/**
     * Reset all properties to their default values.
     * @param  mapping  The mapping used to select this instance
     * @param  request  The servlet request we are processing
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
    	super.reset(mapping, request);
    	_ordersSearchInfo = new OrderSearchDto();
    	_orderSchedulerForm = null;
    	_orderNumSearchValue = null;
    	//STJ-5203
    	SessionDataUtil sessionData = Utility.getSessionDataUtil(request);
    	setRemoteAccess(sessionData.isRemoteAccess());
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
    
	/**
	 * @return the OrderOpDetailForm
	 */
	public OrderOpDetailForm getOrderOpDetailForm() {
		if (_orderOpDetailForm==null){
			_orderOpDetailForm = new OrderOpDetailForm();
		}
		return _orderOpDetailForm;
	}

	/**
	 * @param orderOpDetailForm the orderOpDetailForm to set
	 */
	public void setOrderOpDetailForm(OrderOpDetailForm orderOpDetailForm) {
		this._orderOpDetailForm = orderOpDetailForm;				
	}
	
	/**
	 * @return the ordersLocationFieldChoices
	 */
	public List<LabelValueBean> getOrdersLocationFieldChoices() {
		return _ordersLocationFieldChoices;
	}

	/**
	 * @param pOrdersLocationFieldChoices the ordersLocationFieldChoices to set
	 */
	public void setOrdersLocationFieldChoices(
			List<LabelValueBean> pOrdersLocationFieldChoices) {
		_ordersLocationFieldChoices = pOrdersLocationFieldChoices;
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
		
	public void setNotesUserApproveIdV(IdVector notesUserApproveIdV) {
		this._notesUserApproveIdV = notesUserApproveIdV;
	}
	
	public IdVector getNotesUserApproveIdV() {
		return _notesUserApproveIdV;
	}
	
	public void setAccountMiscProperties(PropertyDataVector accountMiscProperties) {
		this._accountMiscProperties = accountMiscProperties;
	}
	
	public PropertyDataVector getAccountMiscProperties() {
		return _accountMiscProperties;
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
	 * @return the UserShopForm
	 */
	public UserShopForm getUserShopForm() {
		if (_userShopForm==null){
			_userShopForm = new UserShopForm();
		}
		return _userShopForm;
	}

	/**
	 * @param userShopForm the UserShopForm to set
	 */
	public void setUserShopForm(UserShopForm userShopForm) {
		this._userShopForm = userShopForm;				
	}

	public boolean getHasFunctionReceiving(){
		return hasFunctionReceiving;
	}
	
	public void setHasFunctionReceiving(boolean pHasFunctionReceiving) {
		this.hasFunctionReceiving = pHasFunctionReceiving;		
	}
	
	public boolean getIsBillingOrder(){
		return _isBillingOrder;
	}
	
	public void setIsBillingOrder(boolean pIsBillingOrder) {
		this._isBillingOrder = pIsBillingOrder;
	}

	public boolean getIsRebillOrder() {
		return _isRebillOrder;
	}
		
	public void setIsRebillOrder(boolean pIsRebillOrder) {
		this._isRebillOrder = pIsRebillOrder;
	}
	
	//All Order Properties
	public OrderPropertyDataVector getAllOrderProperties() {
		return _allOrderProperties;
	}
	
	public void setAllOrderProperties(OrderPropertyDataVector pAllOrderProperties) {
		this._allOrderProperties = pAllOrderProperties;
	}
	
	/**
	 * @return the parOrderSchedules
	 */
	public DeliveryScheduleViewVector getParOrderSchedules() {
		if (_parOrderSchedules == null) {
			_parOrderSchedules = new DeliveryScheduleViewVector();
		}
		return _parOrderSchedules;
	}

	/**
	 * @param parOrderSchedules the parOrderSchedules to set
	 */
	public void setParOrderSchedules(DeliveryScheduleViewVector parOrderSchedules) {
		_parOrderSchedules = parOrderSchedules;
	}

    /**
     * <code>getOrderItemDescList</code> method.
     *
     * @return a <code>OrderItemDescDataVector</code> value
     */
    public OrderItemDescDataVector getOrderItemDescList() {
        if( null == _mOrderItemDescList) {
            _mOrderItemDescList = new OrderItemDescDataVector();
        }
        return (_mOrderItemDescList);
    }

    /**
     * <code>setOrderItemDescList</code> method.
     *
     * @param pVal a <code>OrderItemDescDataVector</code> value
     */
    public void setOrderItemDescList(OrderItemDescDataVector pVal) {
        _mOrderItemDescList = pVal;
    }

	/**
	 * @return the isRemoteAccess
	 */
	public boolean isRemoteAccess() {
		return _isRemoteAccess;
	}

	/**
	 * @param pIsRemoteAccess the isRemoteAccess to set
	 */
	public void setRemoteAccess(boolean pIsRemoteAccess) {
		_isRemoteAccess = pIsRemoteAccess;
	}
	
	/**
	 * @return the distItemMap
	 */
	public final Map<String, OrderItemDescDataVector> getDistItemMap() {
		return distItemMap;
	}

	/**
	 * @param distItemMap the distItemMap to set
	 */
	public final void setDistItemMap(
			Map<String, OrderItemDescDataVector> distItemMap) {
		this.distItemMap = distItemMap;
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

}

