/**
 * Title: OrdersAction 
 * Description: This is the Struts Action class handling the ESW orders functionality.
 */

package com.espendwise.view.actions.esw;


import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.LabelValueBean;

import com.cleanwise.service.api.dto.LocationSearchDto;
import com.cleanwise.service.api.dto.OrderSearchDto;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.SessionDataUtil;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.DeliveryScheduleView;
import com.cleanwise.service.api.value.DeliveryScheduleViewVector;
import com.cleanwise.service.api.value.OrderScheduleViewVector;
import com.cleanwise.service.api.value.OrderStatusDescDataVector;
import com.cleanwise.service.api.value.OrderStatusDescData;
import com.cleanwise.service.api.value.SiteData;
import com.cleanwise.service.api.value.OrderItemDescData;
import com.cleanwise.view.forms.OrderSchedulerForm;
import com.cleanwise.view.forms.StoreDeliveryScheduleMgrForm;
import com.cleanwise.view.forms.StoreSiteMgrForm;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.logic.OrderOpLogic;
import com.cleanwise.view.logic.OrderSchedulerLogic;
import com.cleanwise.view.logic.StoreDeliveryScheduleMgrLogic;
import com.cleanwise.view.logic.StoreSiteMgrLogic;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.ClwCustomizer;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.SessionTool;
import com.cleanwise.view.utils.ShopTool;
import com.espendwise.view.forms.esw.OrdersForm;
import com.espendwise.view.logic.esw.DashboardLogic;
import com.espendwise.view.logic.esw.OrdersLogic;

/**
 * Implementation of <code>Action</code> that handles log on functionality.
 */
public final class OrdersAction extends EswAction {
    private static final Logger log = Logger.getLogger(OrdersAction.class);
    
    //constants to hold the various action mappings that can be returned by this action class.
    private static final String MAPPING_ORDERS_SHOW_ALL_ORDERS = "ordersShowAllOrder";
    private static final String MAPPING_ORDERS_SHOW_PRODUCT_LIMITS = "ordersShowProductLimits";
    private static final String MAPPING_ORDERS_SHOW_ORDER_SCHEDULES = "ordersShowOrderSchedules";
    private static final String MAPPING_ORDERS_SHOW_FUTURE_ORDERS = "ordersShowFutureOrders";
    private static final String MAPPING_VIEW_ORDER_DETAIL = "viewOrderDetail";    //for Order-Detail Panel (screen)
    private static final String MAPPING_VIEW_SHOPPING_CART = "viewShoppingCart";  //for Order-Detail Panel (screen)
    private static final String MAPPING_VIEW_DASHBOARD = "showDashboard";
    
    /**
     * Process the specified HTTP request, and create the corresponding HTTP
     * response (or forward to another web component that will create it).
     * Return an <code>ActionForward</code> instance describing where and how
     * control should be forwarded, or <code>null</code> if the response has
     * already been completed.
     * @param  mapping      the ActionMapping used to select this instance.
     * @param  form         the ActionForm containing the data.
     * @param  request      the HTTP request we are processing.
     * @param  response     the HTTP response we are creating.
     * @return              an ActionForward describing the component that should receive control.
     */
    public ActionForward performAction(ActionMapping mapping, ActionForm form,
            						HttpServletRequest request, HttpServletResponse response) {

        //If there isn't a currently logged on user then go to the login page
        if (!new SessionTool(request).checkSession()) {
            return mapping.findForward(Constants.GLOBAL_FORWARD_ESW_LOGON);
        }

    	ActionForward returnValue = null;
    	
        OrdersForm theForm = (OrdersForm)form;
        SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
        
        //set the active sub tab to be the orders tab.
        sessionDataUtil.setSelectedSubTab(Constants.TAB_ORDERS);
        //set the last visited shopping tab to be the orders tab.
        sessionDataUtil.setPreviousShoppingModuleTab(Constants.TAB_ORDERS);
        
    	//determine what action to perform
        //If an operation has been specified use it.
    	String operation = theForm.getOperation();
    	
        //If no operation was specified but there is a previously executed operation use it.
    	if (!Utility.isSet(operation)) {
    		operation = sessionDataUtil.getPreviousOrdersAction();
    	}
    	
    	//If no operation was specified and there is no previous operation, default to show all orders
    	if (!Utility.isSet(operation)) {
    		operation = Constants.PARAMETER_OPERATION_VALUE_SHOW_ALL_ORDERS;
    	}
    	
    	//trim whitespace if an operation has been specified
    	if (Utility.isSet(operation)) {
    		operation = operation.trim();
    	}
    	//now that we've determined what action to take, take it
    	
    	boolean rememberOperation = true;
    	
    	if (Constants.PARAMETER_OPERATION_VALUE_SHOW_ORDER.equalsIgnoreCase(operation)) {
    		rememberOperation = false;
        	returnValue = handleShowOrdersRequest(request, response, theForm, mapping);
        } 
        else if(Constants.PARAMETER_OPERATION_VALUE_SHOW_ALL_ORDERS.equalsIgnoreCase(operation)) {
        	returnValue = handleShowAllOrdersRequest(request, response, theForm, mapping);
        } 
        else if (Constants.PARAMETER_OPERATION_VALUE_FILTER_ALL_ORDERS.equalsIgnoreCase(operation)){
        	rememberOperation = false;
        	returnValue = handleFilterAllOrdersRequest(request, response, theForm, mapping);
        }
        else if (Constants.PARAMETER_OPERATION_VALUE_SORT_ORDERS.equalsIgnoreCase(operation)) {
        	rememberOperation = false;
        	returnValue = handleSortAllOrdersRequest(request, response, theForm, mapping);
        }
        else if (Constants.PARAMETER_OPERATION_VALUE_SHOW_PRODUCT_LIMITS.equalsIgnoreCase(operation)) {
        	returnValue = handleShowProductLimitsRequest(request, response, theForm, mapping);
        }
        else if(Constants.PARAMETER_OPERATION_VALUE_SHOW_FUTURE_ORDERS.equals(operation)) {
        	returnValue = handleShowFutureOrdersRequest(request,response,theForm,mapping);
        } 
        else if (Constants.PARAMETER_OPERATION_VALUE_UPDATE_RECEIVE.equals(operation)) {
        	rememberOperation = false;
        	returnValue = handleSaveOrderDetailInfoRequest(request,response,theForm,mapping);
        }
        else if (Constants.PARAMETER_OPERATION_VALUE_UPDATE_ORDER.equals(operation)) {
        	rememberOperation = false;
        	returnValue = handleUpdateOrderRequest(request,response,theForm,mapping);
        }
        else if (Constants.PARAMETER_OPERATION_VALUE_ADD_ORDER_COMMENT.equalsIgnoreCase(operation)) {
        	returnValue = handleAddOrderCommentRequest(request, response, theForm, mapping);
        	rememberOperation = false;
        }
        else if (Constants.PARAMETER_OPERATION_VALUE_REORDER.equalsIgnoreCase(operation)) {
        	rememberOperation = false;
        	returnValue = handleReorderRequest(request, response, theForm, mapping);
        }
        else if (Constants.PARAMETER_OPERATION_VALUE_APPROVE_ORDER.equalsIgnoreCase(operation)) {
        	rememberOperation = false;
        	returnValue = handleApprovePendingOrderRequest(request, response, theForm, mapping);
        }
        else if (Constants.PARAMETER_OPERATION_VALUE_REJECT_ORDER.equalsIgnoreCase(operation)) {
        	rememberOperation = false;
        	returnValue = handleRejectPendingOrderRequest(request, response, theForm, mapping);
        }
        else if (Constants.PARAMETER_OPERATION_VALUE_MODIFY_ORDER.equalsIgnoreCase(operation)) {
        	rememberOperation = false;        	
        	returnValue = handleModifyOrderRequest(request, response, theForm, mapping);
        }
        else if(Constants.PARAMETER_OPERATION_VALUE_SHOW_ORDER_SCHEDULES.equals(operation)) {
        	returnValue = handleShowOrderSchedulesRequest(request,response,theForm,mapping);
        } 
        else if (Constants.PARAMETER_OPERATION_VALUE_SHOW_CORPORATE_ORDER_SCHEDULE.equalsIgnoreCase(operation)) {
        	rememberOperation = false;
        	returnValue = handleShowParOrderScheduleRequest(request, response, theForm, mapping);
        }
        else if (Constants.PARAMETER_OPERATION_VALUE_SHOW_ORDER_SCHEDULE.equalsIgnoreCase(operation)) {
        	rememberOperation = false;
        	returnValue = handleShowOrderScheduleRequest(request, response, theForm, mapping);
        }
        else if (Constants.PARAMETER_OPERATION_VALUE_START_NEW_ORDER_SCHEDULE.equalsIgnoreCase(operation)) {
        	rememberOperation = false;
        	returnValue = handleStartNewOrderScheduleRequest(request, response, theForm, mapping);
        }
        else if (Constants.PARAMETER_OPERATION_VALUE_SAVE_ORDER_SCHEDULE.equalsIgnoreCase(operation)) {
        	rememberOperation = false;
        	returnValue = handleSaveOrderScheduleRequest(request, response, theForm, mapping);
        }
        else if (Constants.PARAMETER_OPERATION_VALUE_DELETE_ORDER_SCHEDULE.equalsIgnoreCase(operation)) {
        	rememberOperation = false;
        	returnValue = handleDeleteOrderScheduleRequest(request, response, theForm, mapping);
        }
        else if (Constants.PARAMETER_OPERATION_VALUE_VIEW_WEB_ORDER_DETAIL.equalsIgnoreCase(operation)) {
        	rememberOperation = false;
        	returnValue = handleViewWebisteOrderDetailRequest(request, response, theForm, mapping);
        }
    	//STJ-5261 New UI - Mobile All Orders. 
        else if (Constants.PARAMETER_OPERATION_VALUE_SHOW_TEN_MORE_ORDERS.equalsIgnoreCase(operation)) {
        	rememberOperation = false;
        	returnValue = handleViewTenMoreOrdersRequest(request, response, theForm, mapping);
        }
        else if (Constants.PARAMETER_OPERATION_VALUE_VIEW_WEB_SITE.equalsIgnoreCase(operation)) {
        	rememberOperation = false;
        	returnValue = handleShowWebsiteRequest(request, response, theForm, mapping);
        }
        else if (Constants.PARAMETER_OPERATION_VALUE_SEARCH_ORDERS.equalsIgnoreCase(operation)) {
            rememberOperation = false;
            returnValue = handleSearchOrderNum(request, response, theForm, mapping);
        }
        else {
        	rememberOperation = false;
        	returnValue = handleUnknownOperation(request, response, theForm, mapping);
        }
        
    	if(rememberOperation) {
    		sessionDataUtil.setPreviousOrdersAction(operation);
    	}
    	
    	return returnValue;   
    }

    /*
     * Private method to determine what action forward should be returned after a show orders request.
     */
	private ActionForward handleShowOrdersRequest(HttpServletRequest request,
			HttpServletResponse response, OrdersForm theForm,
			ActionMapping mapping) {
		
		ActionForward actionForward = null;
		String orderId = theForm.getOrderId();
		String orderNum = request.getParameter("orderNum");
		if ( orderNum!= null){
			try {
				orderId = OrderOpLogic.getOrderIdByWebOrderNum(request, orderNum, 0);
				theForm.setOrderId(orderId);
			}catch (Exception e) {
	            ActionErrors errors = new ActionErrors();
	            String errorMess = ClwI18nUtil.getMessage(request, "error.unExpectedError", null);
	            errors.add("error", new ActionError("error.simpleGenericError", errorMess));
	            saveErrors(request, errors);
	        	return mapping.findForward(Constants.GLOBAL_FORWARD_ESW_ERROR);    
	    	}
		}
		
		//If Order Id is not set from the OrdersForm, Check if the order id is set in session Or not
		//NOTE: Order id would be set in session when StJohn is accessed from Orca or Neptune.
		if(!Utility.isSet(orderId)) {
			SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
			orderId = sessionDataUtil.getOrderId();
		}
		
		if(Utility.isSet(orderId)){ //If orderId is passed as a parameter.
			actionForward = handleShowOrdersRequestByOrderId(request,response,theForm,mapping);
		} else {		
			actionForward = handleShowAllOrdersRequest(request,response,theForm,mapping);  	
		}
		return actionForward;
	}
	
	/*
     * Private method to determine what action forward should be returned after a show orders request.
     */
	private ActionForward handleShowAllOrdersRequest(HttpServletRequest request,
			HttpServletResponse response, OrdersForm theForm,
			ActionMapping mapping) {
		SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
		OrderSearchDto ordersSearchInfo = sessionDataUtil.getOrderSearchDto();
 		if(ordersSearchInfo!=null) {
		//Use existing search criteria to get the latest results for all orders.
			theForm.setOrdersSearchInfo(ordersSearchInfo);
		}

		return handleFilterAllOrdersRequest(request,response,theForm,mapping);
	}
	
	/*
     * Private method to determine what action forward should be returned after a show orders request.
     */
	@SuppressWarnings("deprecation")
	private ActionForward handleFilterAllOrdersRequest(HttpServletRequest request,
			HttpServletResponse response, OrdersForm theForm,
			ActionMapping mapping) {
		
		OrderSearchDto ordersSearchInfo = theForm.getOrdersSearchInfo();
		
		CleanwiseUser user = (CleanwiseUser)request.getSession().getAttribute(Constants.APP_USER);
		ordersSearchInfo.setUserId(String.valueOf(user.getUserId()));
		
		//Initialize Default values, if they were not set.
		OrdersLogic.initOrdersCriteria(request,ordersSearchInfo);
		
		populateCommonData(request,theForm);
		//Get search results
		ActionErrors errors = OrdersLogic.performAllOrdersSearch(request, ordersSearchInfo);
		
		if(errors != null && !errors.isEmpty()) {
			saveErrors(request, errors);
		} else {
			//Orders Search form should be restored.
			SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
	    	sessionDataUtil.setOrderSearchDto(ordersSearchInfo);
	    	if(Constants.ORDERS_CURRENT_LOCATION.equals(sessionDataUtil.getMobileLocationSelected())) {
	    		sessionDataUtil.setMobileLocationSelected(StringUtils.EMPTY);
	    	}
		}
		
    	//if no all orders were found, return an informational message to the user.
		if (errors.isEmpty() && !Utility.isSet(ordersSearchInfo.getMatchingOrders())) {
			ActionMessages messages = new ActionMessages();
        	String message = ClwI18nUtil.getMessage(request,"orders.search.noResults", null);
        	messages.add("message", new ActionMessage("message.simpleMessage", message));
        	saveMessages(request, messages);
		}
    	return mapping.findForward(MAPPING_ORDERS_SHOW_ALL_ORDERS);
	}
	
	/*
     * Private method to determine what action forward should be returned after a sort orders request.
     */
	private ActionForward handleSortAllOrdersRequest(HttpServletRequest request,
			HttpServletResponse response, OrdersForm theForm,
			ActionMapping mapping) {
    	
    	ActionForward actionForward = null;
    	
    	//Get OrdersFilterSearchDto from the session.
    	SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
    	OrderSearchDto ordersSearchInfo = sessionDataUtil.getOrderSearchDto();
    	
    	//If search results are not null then just call out the sort method in OrdersLogic.java
    	if(ordersSearchInfo!=null && ordersSearchInfo.getMatchingOrders()!=null) {
    		//Set form data values to orderFilterSearchDto data values
    		ordersSearchInfo.setSortField(theForm.getOrdersSearchInfo().getSortField());
    		ordersSearchInfo.setSortOrder(theForm.getOrdersSearchInfo().getSortOrder());
    		
    		if(ordersSearchInfo.getSortField()==null || ordersSearchInfo.getSortOrder()==null) {
    			OrdersLogic.initOrdersCriteria(request,ordersSearchInfo);
    		} 
    		populateCommonData(request,theForm);
    		
    		//if no orders were found, return an informational message to the user.
    		if (!Utility.isSet(ordersSearchInfo.getMatchingOrders())) {
            	ActionMessages messages = new ActionMessages();
            	String message = ClwI18nUtil.getMessage(request,"orders.search.noResults", null);
            	messages.add("message", new ActionMessage("message.simpleMessage", message));
            	saveMessages(request, messages);
    		} else {
    			//Sort the results
    			//STJ-5421
        		OrdersLogic.sortAllOrdersResults(request, (OrderStatusDescDataVector) ordersSearchInfo.getMatchingOrders(), ordersSearchInfo.getSortField(), ordersSearchInfo.getSortOrder());
        		theForm.setOrdersSearchInfo(ordersSearchInfo);
    		}
    		
    		actionForward = mapping.findForward(MAPPING_ORDERS_SHOW_ALL_ORDERS);
    	} else {
    		handleShowAllOrdersRequest(request,response,theForm,mapping);
    	}
    	return actionForward;
    }
	
	/*
     * Private method to determine what action forward should be returned after a show orders by order id request.
     */
	private ActionForward handleShowOrdersRequestByOrderId(HttpServletRequest request,
			HttpServletResponse response, OrdersForm theForm,
			ActionMapping mapping) {

		populateCommonData(request, theForm);
		
    	ActionForward actionForward = null;

		actionForward = mapping.findForward(MAPPING_VIEW_ORDER_DETAIL);
		
		//find the Order by order Id
        ActionErrors errors = new ActionErrors();
        ActionMessages messages = new ActionMessages();
        
        try {
            //errors = OrdersLogic.getOrderDetail(request, theForm);
        	Map<String,Object> saveResults = OrdersLogic.getOrderDetail(request, theForm);
        	errors = (ActionErrors) saveResults.get(ActionErrors.GLOBAL_ERROR);
    	    messages = (ActionMessages) saveResults.get(ActionErrors.GLOBAL_MESSAGE);
            if (errors.size() > 0) {
                saveErrors(request, errors);
            }
            if (messages.size() > 0) {
        		saveMessages(request, messages);
        	}
        } catch (Exception e) {
            log.error("Unexpected exception in OrdersAction.handleShowOrdersRequestByOrderId(): ",
                            e);
            String errorMess = ClwI18nUtil.getMessage(request,
                    "error.unExpectedError", null);
            errors.add("error", new ActionError("error.simpleGenericError",
                    errorMess));
            saveErrors(request, errors);
            return mapping.findForward(Constants.GLOBAL_FORWARD_ESW_ERROR);
        }

		for (int j1=0; j1 < theForm.getOrderOpDetailForm().getOrderItemDescList().size(); j1++) {
			   OrderItemDescData orderItemDescData1 = (OrderItemDescData) theForm.getOrderOpDetailForm().getOrderItemDescList().get(j1);
			   String strReceivedOrdersAction1 = orderItemDescData1.getItemQuantityRecvdS().trim();
               log.info("strReceivedOrdersAction1 = " + strReceivedOrdersAction1);
		}
		SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
		sessionDataUtil.setOrderItemDescList(theForm.getOrderOpDetailForm().getOrderItemDescList());
		
        theForm.setOrderItemDescList(theForm.getOrderOpDetailForm().getOrderItemDescList());
		for (int j2=0; j2 < theForm.getOrderItemDescList().size(); j2++) {
			   OrderItemDescData orderItemDescData2 = (OrderItemDescData) theForm.getOrderItemDescList().get(j2);
			   String strReceivedOrdersAction2 = orderItemDescData2.getItemQuantityRecvdS().trim();
               log.info("strReceivedOrdersAction2 = " + strReceivedOrdersAction2);
		}
    	return actionForward;
	}
	
	private ActionForward handleUpdateOrderRequest(HttpServletRequest request,
			HttpServletResponse response, OrdersForm theForm,
			ActionMapping mapping) {
		ActionForward actionForward = mapping.findForward(MAPPING_VIEW_ORDER_DETAIL);
		
		ActionErrors errors = new ActionErrors();
        ActionMessages messages = new ActionMessages();
        
        try {
        	
            Map<String,Object> saveResults = OrdersLogic.updatePO(request, theForm);
        	errors = (ActionErrors) saveResults.get(ActionErrors.GLOBAL_ERROR);
    	    messages = (ActionMessages) saveResults.get(ActionErrors.GLOBAL_MESSAGE);
    	    if (messages.size() > 0) {
        		saveMessages(request, messages);
        	}
            if (errors.size() > 0) {
                saveErrors(request, errors);
            }else{
            	actionForward = handleShowOrdersRequestByOrderId(request,response,theForm,mapping);
            }
            
        } catch (Exception e) { 
            log.error("Unexpected exception in handleUpdateOrderRequest(): ",
                            e);
            String errorMess = ClwI18nUtil.getMessage(request,
                    "error.unExpectedError", null);
            errors.add("error", new ActionError("error.simpleGenericError",
                    errorMess));
            saveErrors(request, errors);
            return mapping.findForward(Constants.GLOBAL_FORWARD_ESW_ERROR);
        }
        
    	return actionForward;	
	}
	
	/*
     * Private method to determine:
     * 1) what action forward should be returned after handle Update Order Detail Info Request
     * 2) save info. from Order-Detail screen (panel)
     */
	private ActionForward handleSaveOrderDetailInfoRequest(HttpServletRequest request,
			HttpServletResponse response, OrdersForm theForm,
			ActionMapping mapping) {

		populateCommonData(request, theForm);
		
    	ActionForward actionForward = null;

		actionForward = mapping.findForward(MAPPING_VIEW_ORDER_DETAIL);
		
		//save the Order-Detail info. passed from the NEW UI Order-Detail/Order-Detail-Pending panels (screens) 
        ActionErrors errors = new ActionErrors();
        ActionMessages messages = new ActionMessages();
        
        try {
        	boolean receivedFl = theForm.getHasFunctionReceiving();
        	log.info("receivedFl = " + receivedFl);
        	boolean rejectFl = false;

            Map<String,Object> saveResults = OrdersLogic.saveOrderDetail(request, theForm, receivedFl, rejectFl);
        	errors = (ActionErrors) saveResults.get(ActionErrors.GLOBAL_ERROR);
    	    messages = (ActionMessages) saveResults.get(ActionErrors.GLOBAL_MESSAGE);
    	    if (messages.size() > 0) {
        		saveMessages(request, messages);
        	}
            if (errors.size() > 0) {
                saveErrors(request, errors);
            }else{
            	actionForward = handleShowOrdersRequestByOrderId(request,response,theForm,mapping);
            }
            
        } catch (Exception e) { 
            log.error("Unexpected exception in handleUpdateOrderDetailInfoRequest(): ",
                            e);
            String errorMess = ClwI18nUtil.getMessage(request,
                    "error.unExpectedError", null);
            errors.add("error", new ActionError("error.simpleGenericError",
                    errorMess));
            saveErrors(request, errors);
            return mapping.findForward(Constants.GLOBAL_FORWARD_ESW_ERROR);
        }
        
    	return actionForward;	
        	
        }
	/*
     * Private method to determine what action forward should be returned after a product limits request.
     */
	private ActionForward handleShowProductLimitsRequest(HttpServletRequest request,
			HttpServletResponse response, OrdersForm theForm,
			ActionMapping mapping) {
		
		//TODO:
		return handleUnknownOperation(request, response, theForm, mapping);
	}
	
	/*
     * Private method to determine what action forward should be returned after a show 
     * order schedules request.  This method doesn't do any work itself, but rather passes
     * through to other methods to handle the request.
     */
	private ActionForward handleShowOrderSchedulesRequest(HttpServletRequest request,
			HttpServletResponse response, OrdersForm theForm,
			ActionMapping mapping) {
        
		ActionForward returnValue = null;
    	String previousOrderScheduleId = Utility.getSessionDataUtil(request).getLastViewedOrderScheduleId();
    	if (Utility.isSet(previousOrderScheduleId)) {
    		theForm.getOrderSchedulerForm().setOrderScheduleId(Integer.parseInt(previousOrderScheduleId));
        	String previousOrderScheduleType = Utility.getSessionDataUtil(request).getLastViewedOrderScheduleType();
    		if (RefCodeNames.FUTURE_ORDER_TYPE.CORPORATE_ORDER_SCHEDULE.equalsIgnoreCase(previousOrderScheduleType)) {
    			returnValue = handleShowParOrderScheduleRequest(request, response, theForm, mapping);
    		}
    		else {
    			returnValue = handleShowOrderScheduleRequest(request, response, theForm, mapping);
    		}
    	}
    	else {
            returnValue = handleStartNewOrderScheduleRequest(request, response, theForm, mapping);
    	}

    	return returnValue;
	}
	
	/*
     * Private method to determine what action forward should be returned after a start new 
     * order schedule request.
     */
	private ActionForward handleStartNewOrderScheduleRequest(HttpServletRequest request,
			HttpServletResponse response, OrdersForm theForm,
			ActionMapping mapping) {
        
    	ActionErrors errors = new ActionErrors();
    	ActionMessages messages = new ActionMessages();
    	SiteData currentLocation = ShopTool.getCurrentSite(request);
    	
    	//clear any previously stored order schedule info from the session
    	Utility.getSessionDataUtil(request).setLastViewedOrderScheduleId(null);
    	
    	//if the user has not yet selected a location, return an error
        if (currentLocation == null) {
            String errorMess = ClwI18nUtil.getMessage(request, "error.noLocationSelected");
            errors.add("error", new ActionError("error.simpleGenericError",
                    errorMess));
        }
    	//otherwise retrieve the data common to the order schedule functionality
        else {
        	populateCommonScheduleData(request, theForm, true, errors, messages);
        }
        
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
		if (!messages.isEmpty()) {
			saveMessages(request, messages);
		}
    	return mapping.findForward(MAPPING_ORDERS_SHOW_ORDER_SCHEDULES);
	}
	
	/*
     * Private method to determine what action forward should be returned after a 
     * show par order schedule request.
     */
	private ActionForward handleShowParOrderScheduleRequest(HttpServletRequest request,
			HttpServletResponse response, OrdersForm theForm,
			ActionMapping mapping) {
        
        ActionErrors errors = new ActionErrors();
    	ActionMessages messages = new ActionMessages();
    	SiteData currentLocation = ShopTool.getCurrentSite(request);
    	
    	//clear any previously stored order schedule info from the session
    	Utility.getSessionDataUtil(request).setLastViewedParOrderScheduleId(null);
    	
    	//if the user has not yet selected a location, return an error
        if (currentLocation == null) {
            String errorMess = ClwI18nUtil.getMessage(request, "error.noLocationSelected");
            errors.add("error", new ActionError("error.simpleGenericError",
                    errorMess));
        }
    	//otherwise try to retrieve the order schedule information
        else {
			int scheduleId = theForm.getOrderSchedulerForm().getOrderScheduleId();
			populateCommonScheduleData(request, theForm, true, errors, messages);
			if (scheduleId > 0) {
				ActionErrors parOrderErrors = new ActionErrors();
				DeliveryScheduleViewVector parOrderSchedules = getParOrderSchedules(request, ShopTool.getCurrentSite(request).getSiteId(), parOrderErrors);
				if (parOrderErrors.isEmpty()) {
					Iterator<DeliveryScheduleView> parOrderScheduleIterator = parOrderSchedules.iterator();
					boolean foundParOrderSchedule = false;
					while (parOrderScheduleIterator.hasNext() && !foundParOrderSchedule) {
						foundParOrderSchedule = (scheduleId == parOrderScheduleIterator.next().getScheduleId());
					}
					if (foundParOrderSchedule) {
						StoreDeliveryScheduleMgrForm deliveryScheduleForm = new StoreDeliveryScheduleMgrForm();
						errors.add(StoreDeliveryScheduleMgrLogic.detail(request, deliveryScheduleForm, scheduleId));
						theForm.getOrderSchedulerForm().setOrderScheduleId(scheduleId);
						theForm.getOrderSchedulerForm().setCalendarDatesWithOrders(deliveryScheduleForm.getCalendarOrderDates());
					}
					else {
					    String errorMess = ClwI18nUtil.getMessage(request, "orders.schedule.error.invalidSchedule");
					    errors.add("error", new ActionError("error.simpleGenericError", errorMess));
					}
				}
				else {
					errors.add(parOrderErrors);
				}
			}
			else {
			    String errorMess = ClwI18nUtil.getMessage(request, "error.unExpectedError");
			    errors.add("error", new ActionError("error.simpleGenericError", errorMess));
			}
        }
        
        //store the info for a successfully retrieved order schedule in the session
		if (theForm.getOrderSchedulerForm().getOrderScheduleId() > 0 && errors.isEmpty()) {
			String scheduleId = Integer.toString(theForm.getOrderSchedulerForm().getOrderScheduleId());
	    	Utility.getSessionDataUtil(request).setLastViewedParOrderScheduleId(scheduleId);
		}
		
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
		if (!messages.isEmpty()) {
			saveMessages(request, messages);
		}
    	return mapping.findForward(MAPPING_ORDERS_SHOW_ORDER_SCHEDULES);
	}
	
	/*
     * Private method to determine what action forward should be returned after a 
     * show order schedule request.
     */
	private ActionForward handleShowOrderScheduleRequest(HttpServletRequest request,
			HttpServletResponse response, OrdersForm theForm,
			ActionMapping mapping) {
        
        ActionErrors errors = new ActionErrors();
        ActionMessages messages = new ActionMessages();
    	SiteData currentLocation = ShopTool.getCurrentSite(request);
    	
    	//clear any previously stored order schedule id from the session
    	Utility.getSessionDataUtil(request).setLastViewedOrderScheduleId(null);
    	
    	//if the user has not yet selected a location, return an error
        if (currentLocation == null) {
            String errorMess = ClwI18nUtil.getMessage(request, "error.noLocationSelected");
            errors.add("error", new ActionError("error.simpleGenericError",
                    errorMess));
        } 
        //otherwise try to retrieve the schedule information
        else {
			int scheduleId = theForm.getOrderSchedulerForm().getOrderScheduleId();
			populateCommonScheduleData(request, theForm, true, errors, messages);
			if (scheduleId > 0) {
				OrderSchedulerForm orderSchedulerForm = theForm.getOrderSchedulerForm();
				orderSchedulerForm.setOrderScheduleId(scheduleId);
				errors.add(OrderSchedulerLogic.prepareSchedule(request, orderSchedulerForm));
				//convert any existing schedule with a type of WEEK_MONTH to instead have a type of
				//DATE_LIST  The old UI supports two flavors of custom (DATE_LIST and WEEK_MONTH), but
				//the new UI supports only one (DATE_LIST), so to avoid having to deal with data
				//conversion via a script or some other mechanism we do it here on the fly.
				if (RefCodeNames.ORDER_SCHEDULE_RULE_CD.WEEK_MONTH.equals(orderSchedulerForm.getScheduleType())) {
					orderSchedulerForm.setScheduleType(RefCodeNames.ORDER_SCHEDULE_RULE_CD.DATE_LIST);
					List<GregorianCalendar> orderDates = orderSchedulerForm.getCalendarDatesWithOrders();
					Iterator<GregorianCalendar> orderDateIterator = orderDates.iterator();
					StringBuilder orderDatesString = new StringBuilder(50);
					boolean appendSeperator = false;
					while (orderDateIterator.hasNext()) {
						if (appendSeperator) {
							orderDatesString.append(Constants.MULTIPLE_DATE_SEPARATOR);
						}
						orderDatesString.append(ClwI18nUtil.formatDateInp(request, orderDateIterator.next().getTime()));
						appendSeperator = true;
					}
					orderSchedulerForm.setAlsoDates(orderDatesString.toString());
				}
			}
			else {
			    String errorMess = ClwI18nUtil.getMessage(request, "error.unExpectedError");
			    errors.add("error", new ActionError("error.simpleGenericError", errorMess));
			}
        	//Return an error and clear the schedule data if the order schedule exists and was either created 
			//from one of the template shopping lists or was created from a deleted shopping list.  We are not 
			//including template shopping lists in the shopping list drop-down in the UI, so that control 
			//would not be able to display the correct data for an order schedule created from a template 
			//shopping list.  Additionally, even though we should be preventing the deletion of a user shopping
			//list if a schedule has been creating using it, there could be existing order guides that have that
			//situation so we need to handle it as well.
			scheduleId = theForm.getOrderSchedulerForm().getOrderScheduleId();
			String scheduleShoppingListId = Integer.toString(theForm.getOrderSchedulerForm().getOrderGuideId());
			boolean templateShoppingListUsed = (scheduleId != 0 &&
					theForm.getOrderSchedulerForm().getTemplateOrderGuideIds().contains(scheduleShoppingListId));
			boolean deletedShoppingListUsed = (scheduleId != 0 && !templateShoppingListUsed && 
					!theForm.getOrderSchedulerForm().getUserOrderGuideIds().contains(scheduleShoppingListId));
			if (templateShoppingListUsed) {
	        	Object[] insertionStrings = new Object[1];
	        	insertionStrings[0] = theForm.getOrderSchedulerForm().getOrderScheduleName(request, " ");
			    String errorMess = ClwI18nUtil.getMessage(request, "orders.schedule.error.scheduleCreatedFromTemplateShoppingList", insertionStrings);
			    errors.add("error", new ActionError("error.simpleGenericError", errorMess));
				populateCommonScheduleData(request, theForm, true, errors, messages);
			}
			if (deletedShoppingListUsed) {
	        	Object[] insertionStrings = new Object[1];
	        	insertionStrings[0] = theForm.getOrderSchedulerForm().getOrderScheduleName(request, " ");
			    String errorMess = ClwI18nUtil.getMessage(request, "orders.schedule.error.scheduleCreatedFromDeletedShoppingList", insertionStrings);
			    errors.add("error", new ActionError("error.simpleGenericError", errorMess));
				populateCommonScheduleData(request, theForm, true, errors, messages);
			}
        }
        
        //store the info for a successfully retrieved order schedule in the session
		if (theForm.getOrderSchedulerForm().getOrderScheduleId() > 0 && errors.isEmpty()) {
			String scheduleId = Integer.toString(theForm.getOrderSchedulerForm().getOrderScheduleId());
	    	Utility.getSessionDataUtil(request).setLastViewedOrderScheduleId(scheduleId);
		}
		
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
		if (!messages.isEmpty()) {
			saveMessages(request, messages);
		}
    	return mapping.findForward(MAPPING_ORDERS_SHOW_ORDER_SCHEDULES);
	}
	
	/*
     * Private method to determine what action forward should be returned after a
     * save order schedule request.
     */
	private ActionForward handleSaveOrderScheduleRequest(HttpServletRequest request,
			HttpServletResponse response, OrdersForm theForm,
			ActionMapping mapping) {
        
    	ActionErrors errors = new ActionErrors();
    	ActionMessages messages = new ActionMessages();
    	SiteData currentLocation = ShopTool.getCurrentSite(request);
    	
    	//if the user has not yet selected a location, return an error
        if (currentLocation == null) {
            String errorMess = ClwI18nUtil.getMessage(request, "error.noLocationSelected");
            errors.add("error", new ActionError("error.simpleGenericError",
                    errorMess));
        }
        //otherwise try to save the schedule
        else {
        	//trim all string data
        	OrderSchedulerForm orderSchedulerForm = theForm.getOrderSchedulerForm();
        	orderSchedulerForm.setAlsoDates(Utility.safeTrim(orderSchedulerForm.getAlsoDates()));
        	orderSchedulerForm.setCcEmail(Utility.safeTrim(orderSchedulerForm.getCcEmail()));
        	orderSchedulerForm.setContactEmail(Utility.safeTrim(orderSchedulerForm.getContactEmail()));
        	orderSchedulerForm.setContactName(Utility.safeTrim(orderSchedulerForm.getContactName()));
        	orderSchedulerForm.setContactPhone(Utility.safeTrim(orderSchedulerForm.getContactPhone()));
        	orderSchedulerForm.setEndDate(Utility.safeTrim(orderSchedulerForm.getEndDate()));
        	orderSchedulerForm.setExcludeDates(Utility.safeTrim(orderSchedulerForm.getExcludeDates()));
        	orderSchedulerForm.setMonthDayCycle(Utility.safeTrim(orderSchedulerForm.getMonthDayCycle()));
        	orderSchedulerForm.setMonthWeekCycle(Utility.safeTrim(orderSchedulerForm.getMonthWeekCycle()));
        	orderSchedulerForm.setScheduleAction(Utility.safeTrim(orderSchedulerForm.getScheduleAction()));
        	orderSchedulerForm.setScheduleType(Utility.safeTrim(orderSchedulerForm.getScheduleType()));
        	orderSchedulerForm.setStartDate(Utility.safeTrim(orderSchedulerForm.getStartDate()));
        	orderSchedulerForm.setWeekCycle(Utility.safeTrim(orderSchedulerForm.getWeekCycle()));
        	errors.add(OrderSchedulerLogic.validateSaveSchedule(request, orderSchedulerForm));
        	if (errors.isEmpty()) {
        		errors.add(OrderSchedulerLogic.saveSchedule(request, orderSchedulerForm, false));
        	}
        }
        
        ActionForward returnValue = null;
        //if no errors occurred then call out to the showOrderSchedule method to handle the response and
        //include a message to the user to let them know the order schedule was saved
        if (errors.isEmpty()) {
            returnValue = handleShowOrderScheduleRequest(request, response, theForm, mapping);
            //retrieve any existing messages
            messages = getMessages(request);
            //add our new message to them
        	Object[] insertionStrings = new Object[1];
        	insertionStrings[0] = theForm.getOrderSchedulerForm().getOrderScheduleName(request, " ");
            String successMessage = ClwI18nUtil.getMessage(request, "orders.schedule.message.scheduleSaved", insertionStrings);
        	messages.add("message", new ActionMessage("message.simpleMessage", successMessage));
        	saveMessages(request, messages);
        }
        //otherwise return the user to the input page after gathering required data
        else {
            populateCommonScheduleData(request, theForm, false, errors, messages);
    		if (!errors.isEmpty()) {
    			saveErrors(request, errors);
    		}
    		if (!messages.isEmpty()) {
    			saveMessages(request, messages);
    		}
	    	returnValue = mapping.findForward(MAPPING_ORDERS_SHOW_ORDER_SCHEDULES);
        }
        
        return returnValue;
	}
	
	/*
     * Private method to determine what action forward should be returned after a 
     * delete order schedule request.
     */
	private ActionForward handleDeleteOrderScheduleRequest(HttpServletRequest request,
			HttpServletResponse response, OrdersForm theForm,
			ActionMapping mapping) {
		
		String orderScheduleName = null;
        
    	ActionErrors errors = new ActionErrors();
    	ActionMessages messages = new ActionMessages();
    	SiteData currentLocation = ShopTool.getCurrentSite(request);
    	
    	//if the user has not yet selected a location, return an error
        if (currentLocation == null) {
            String errorMess = ClwI18nUtil.getMessage(request, "error.noLocationSelected");
            errors.add("error", new ActionError("error.simpleGenericError",
                    errorMess));
        }
        //otherwise try to delete the schedule
        else {
			int scheduleId = theForm.getOrderSchedulerForm().getOrderScheduleId();
			populateCommonScheduleData(request, theForm, false, errors, messages);
			if (scheduleId > 0) {
	        	OrderSchedulerForm orderSchedulerForm = theForm.getOrderSchedulerForm();
	        	orderSchedulerForm.setOrderScheduleId(scheduleId);
	        	ActionErrors deleteErrors = OrderSchedulerLogic.validateDeleteSchedule(request, orderSchedulerForm);
	        	if (deleteErrors.isEmpty()) {
	        		orderScheduleName = theForm.getOrderSchedulerForm().getOrderScheduleName(request, " ");
	        		errors.add(OrderSchedulerLogic.deleteSchedule(request, orderSchedulerForm));
	        	}
	        	else {
	        		errors.add(deleteErrors);
	        	}
			}
			else {
			    String errorMess = ClwI18nUtil.getMessage(request, "error.unExpectedError");
			    errors.add("error", new ActionError("error.simpleGenericError", errorMess));
			}
        }
        
        ActionForward returnValue = null;
        //if no errors occurred then call out to the showOrderSchedules method to handle the response and
        //include a message to the user to let them know the order schedule was deleted
        if (errors.isEmpty()) {
        	//clear the stored order schedule info from the session
        	Utility.getSessionDataUtil(request).setLastViewedOrderScheduleId(null);
        	returnValue = handleShowOrderSchedulesRequest(request, response, theForm, mapping);
            //retrieve any existing messages
            messages = getMessages(request);
            //add our new message to them
        	Object[] insertionStrings = new Object[1];
        	insertionStrings[0] = orderScheduleName;
            String successMessage = ClwI18nUtil.getMessage(request, "orders.schedule.message.scheduleDeleted", insertionStrings);
            messages.add("message", new ActionMessage("message.simpleMessage", successMessage));
        	saveMessages(request, messages);
        }
        //otherwise return the user to the input page after gathering required data
        else {
    		if (!errors.isEmpty()) {
    			saveErrors(request, errors);
    		}
    		if (!messages.isEmpty()) {
    			saveMessages(request, messages);
    		}
	    	returnValue = mapping.findForward(MAPPING_ORDERS_SHOW_ORDER_SCHEDULES);
        }
        
        return returnValue;
	}

	private ActionForward handleSearchOrderNum(HttpServletRequest request,
			HttpServletResponse response, OrdersForm theForm,
			ActionMapping mapping) {


        ActionForward actionForward = mapping.findForward(MAPPING_VIEW_ORDER_DETAIL);
        String activeTab = theForm.getActiveTab();
        String mapToReturnWhenError = MAPPING_VIEW_DASHBOARD ;
        if (activeTab.equals(Constants.TAB_ORDERS)) {
         //  mapToReturnWhenError = MAPPING_ORDERS_SHOW_ALL_ORDERS;
         mapToReturnWhenError = "showAllOrders";
        }

		OrderSearchDto ordersSearchInfo = theForm.getOrdersSearchInfo();
		String orderNum = theForm.getOrderNumSearchValue();


 		//Get search results
		ActionErrors errors = OrdersLogic.performOrderSearchByOrderNum(request, orderNum, ordersSearchInfo);

		if(errors != null && !errors.isEmpty()) {
			saveErrors(request, errors);
			
			return mapping.findForward(mapToReturnWhenError);
		} else {
			//Orders Search form should be restored.
			SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
	    	sessionDataUtil.setOrderSearchDto(ordersSearchInfo);
	    	if(Constants.ORDERS_CURRENT_LOCATION.equals(sessionDataUtil.getMobileLocationSelected())) {
	    		sessionDataUtil.setMobileLocationSelected(StringUtils.EMPTY);
	    	}
		}
		ArrayList orders =  (ArrayList)ordersSearchInfo.getMatchingOrders();
		if (!Utility.isSet(orders) || orders.size() == 0) {
        	ActionMessages messages = new ActionMessages();
        	Object[] insertionStrings = new Object[1];
        	insertionStrings[0] = orderNum;
        	String message = ClwI18nUtil.getMessage(request,"orders.search.orderNumberNotFound", insertionStrings);
        	messages.add("message", new ActionMessage("message.simpleMessage", message));
        	saveMessages(request, messages);
            actionForward = mapping.findForward(mapToReturnWhenError);
		    //SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
		    //sessionDataUtil.setOrderSearchDto(null);
		} else if (orders.size()>1){
			populateCommonData(request,theForm);
			actionForward = mapping.findForward(MAPPING_ORDERS_SHOW_ALL_ORDERS);
		} else {
            OrderStatusDescData order = (OrderStatusDescData)orders.get(0);
            theForm.setOrderId(order.getOrderDetail().getOrderId()+"");
            //actionForward = handleShowOrdersRequestByOrderId(request, response, theForm, mapping);

            try {
                Map<String,Object> saveResults = OrdersLogic.getOrderDetail(request, theForm);
                errors = (ActionErrors) saveResults.get(ActionErrors.GLOBAL_ERROR);
                ActionMessages messages = (ActionMessages) saveResults.get(ActionErrors.GLOBAL_MESSAGE);
                if (errors.size() > 0) {
                    saveErrors(request, errors);
                }
                if (messages.size() > 0) {
                    saveMessages(request, messages);
                }
            } catch (Exception e) {
                log.error("Unexpected exception in OrdersAction.handleShowOrdersRequestByOrderId(): ",
                                e);
                String errorMess = ClwI18nUtil.getMessage(request,
                        "error.unExpectedError", null);
                errors.add("error", new ActionError("error.simpleGenericError",
                        errorMess));
                saveErrors(request, errors);
                e.printStackTrace();
                return mapping.findForward(Constants.GLOBAL_FORWARD_ESW_ERROR);
            }
            // STJ-5558
            String status =  order.getOrderDetail().getOrderStatusCd();
            if (status.equals(RefCodeNames.ORDER_STATUS_CD.RECEIVED) ||
                status.equals(RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW) ||
                status.equals(RefCodeNames.ORDER_STATUS_CD.PENDING_ORDER_REVIEW) ||
                status.equals(RefCodeNames.ORDER_STATUS_CD.ORDERED_PROCESSING)) {
                ActionMessages messages = new ActionMessages();
                Object[] insertionStrings = new Object[1];
                insertionStrings[0] = orderNum;
                String message = ClwI18nUtil.getMessage(request,"orders.search.orderIsNotProcessedYet", insertionStrings);
                messages.add("message", new ActionMessage("message.simpleMessage", message));
                saveMessages(request, messages);
                actionForward = mapping.findForward(mapToReturnWhenError);
                return actionForward;
            }

            for (int j1=0; j1 < theForm.getOrderOpDetailForm().getOrderItemDescList().size(); j1++) {
                   OrderItemDescData orderItemDescData1 = (OrderItemDescData) theForm.getOrderOpDetailForm().getOrderItemDescList().get(j1);
                   String strReceivedOrdersAction1 = orderItemDescData1.getItemQuantityRecvdS().trim();
                   log.info("strReceivedOrdersAction1 = " + strReceivedOrdersAction1);
            }
            SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
            sessionDataUtil.setOrderItemDescList(theForm.getOrderOpDetailForm().getOrderItemDescList());

            theForm.setOrderItemDescList(theForm.getOrderOpDetailForm().getOrderItemDescList());
            for (int j2=0; j2 < theForm.getOrderItemDescList().size(); j2++) {
                   OrderItemDescData orderItemDescData2 = (OrderItemDescData) theForm.getOrderItemDescList().get(j2);
                   String strReceivedOrdersAction2 = orderItemDescData2.getItemQuantityRecvdS().trim();
                   log.info("strReceivedOrdersAction2 = " + strReceivedOrdersAction2);
            }
            request.setAttribute("operation", "showOrder");
            request.setAttribute("orderId", order.getOrderDetail().getOrderId());
        }

    	return actionForward;
	}



	private void populateCommonScheduleData(HttpServletRequest request, OrdersForm theForm,
			boolean resetScheduleData, ActionErrors errors, ActionMessages messages) {
    	
    	//if the user has selected a location, retrieve the data common to the order 
    	//schedule functionality
        if (ShopTool.getCurrentSite(request) != null) {
        	OrderSchedulerForm orderSchedulerForm = theForm.getOrderSchedulerForm();
        	OrderSchedulerLogic.init(request, orderSchedulerForm, resetScheduleData, true);
        	//make any new-UI required changes to the initialized values
        	if (resetScheduleData) {
        		orderSchedulerForm.setStartDate(ClwI18nUtil.getUIDateFormat(request).toLowerCase());
        		orderSchedulerForm.setEndDate(ClwI18nUtil.getUIDateFormat(request).toLowerCase());
        		orderSchedulerForm.setAlsoDates(ClwI18nUtil.getUIDateFormat(request).toLowerCase());
        		orderSchedulerForm.setExcludeDates(ClwI18nUtil.getUIDateFormat(request).toLowerCase());
        		//STJ-4598: Default recurrence to monthly
        		orderSchedulerForm.setScheduleType(RefCodeNames.ORDER_SCHEDULE_RULE_CD.DAY_MONTH);
        	}
        	theForm.setParOrderSchedules(getParOrderSchedules(request, ShopTool.getCurrentSite(request).getSiteId(), errors));
        	OrderScheduleViewVector existingOrderSchedules = orderSchedulerForm.getOrderSchedules();
        	boolean maxSchedulesExceeded = existingOrderSchedules.size() > Constants.ORDER_SCHEDULES_SEARCH_RESULTS_MAX_DISPLAY;
        	if (maxSchedulesExceeded) {
        		OrderScheduleViewVector trimmedOrderSchedules = new OrderScheduleViewVector();
        		for (int i=0; i<Constants.ORDER_SCHEDULES_SEARCH_RESULTS_MAX_DISPLAY; i++) {
        			trimmedOrderSchedules.add(existingOrderSchedules.get(i));
        		}
        		orderSchedulerForm.setOrderSchedules(trimmedOrderSchedules);
            	Object[] insertionStrings = new Object[1];
            	insertionStrings[0] = Constants.ORDER_SCHEDULES_SEARCH_RESULTS_MAX_DISPLAY;
                String successMessage = ClwI18nUtil.getMessage(request, "orders.schedule.message.maximumResultsMet", insertionStrings);
            	messages.add("message", new ActionMessage("message.simpleMessage", successMessage));
        	}
        	//if the user is a browse only user and there are no schedules to show, return a message
        	if (ShopTool.getCurrentUser(request).isBrowseOnly() && 
        			orderSchedulerForm.getOrderSchedules().size() <= 0 && 
        			theForm.getParOrderSchedules().size() <= 0) {
        		String message = ClwI18nUtil.getMessage(request, "orders.schedule.message.noResults");
            	messages.add("message", new ActionMessage("message.simpleMessage", message));
        	}
        }
		
	}
	
	/*
     * Private method to determine what action forward should be returned after a future orders request.
     */
	private ActionForward handleShowFutureOrdersRequest(HttpServletRequest request,
			HttpServletResponse response, OrdersForm theForm,
			ActionMapping mapping) {

		ActionErrors errors = new ActionErrors();
		SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
		OrderSearchDto previousFutureOrderSearchInfo = sessionDataUtil.getFutureOrderSearchDto();
		
    	SiteData currentLocation = ShopTool.getCurrentSite(request);
    	//if the user has not yet selected a location, return an error
    	if (currentLocation == null) {
            String errorMess = ClwI18nUtil.getMessage(request, "error.noLocationSelected", null);
            errors.add("error", new ActionError("error.simpleGenericError", errorMess));  		
    	}else{
    		OrderSearchDto futureOrderSearchInfo = theForm.getFutureOrdersSearchInfo();

    		futureOrderSearchInfo.setLocationId(Integer.toString(currentLocation.getSiteId()));

    		if (!Utility.isSet(futureOrderSearchInfo.getDateRange())) {
            	if (previousFutureOrderSearchInfo != null &&
            			Utility.isSet(previousFutureOrderSearchInfo.getDateRange())) {
            		futureOrderSearchInfo.setDateRange(previousFutureOrderSearchInfo.getDateRange());
            	}
            	else {//if date range is not set default to next 90 days
            		futureOrderSearchInfo.setDateRange(Constants.FUTURE_ORDER_DATE_RANGE_VALUE_NEXT_NINETY_DAYS);
            	}
            }
    		
            populateCommonData(request,theForm);
            DeliveryScheduleViewVector parOrderSchedules = getParOrderSchedules(request, ShopTool.getCurrentSite(request).getSiteId(), errors);
            errors = OrdersLogic.performFutureOrdersSearch(request, futureOrderSearchInfo, parOrderSchedules);
        	
            if (errors == null || errors.isEmpty()) {
                sessionDataUtil.setFutureOrderSearchDto(futureOrderSearchInfo);
            }
            //if no all orders were found, return an informational message to the user.
    		if (errors.isEmpty() && !Utility.isSet(futureOrderSearchInfo.getMatchingOrders())) {
            	ActionMessages messages = new ActionMessages();
            	String message = ClwI18nUtil.getMessage(request,"orders.search.noResults", null);
            	messages.add("message", new ActionMessage("message.simpleMessage", message));
            	saveMessages(request, messages);
    		}
            
    	}
		
    	//if any errors occurred save them so they can be displayed to the user
        if (errors != null && !errors.isEmpty()) {
            saveErrors(request, errors);
        }
        
        return mapping.findForward(MAPPING_ORDERS_SHOW_FUTURE_ORDERS);
	}
		
    /*
     * Private method to determine what action forward should be returned after a Reorder request.
     */
    private ActionForward handleReorderRequest(HttpServletRequest request, HttpServletResponse response,
			OrdersForm form, ActionMapping mapping) {
		
    	log.info("before calling OrdersLogic.reorder");
        ActionErrors errors = new ActionErrors();
    	try {
    	    CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
            if(appUser.isBrowseOnly()){
                String message = ClwI18nUtil.getMessage(request,"orders.error.browseOnlyCannotReorder", null);
                errors.add("error", new ActionMessage("message.simpleMessage", message));
                saveErrors(request, errors);
                return mapping.findForward(MAPPING_VIEW_ORDER_DETAIL);    
            }
            errors = OrdersLogic.reorder(request, form);
            if (errors.size() > 0 && errors!=null) {
                saveErrors(request, errors);
            }
        } catch (Exception e) {
            log.error("Unexpected exception in handleReorderRequest() method: ",
                            e);
            String errorMess = ClwI18nUtil.getMessage(request,
                    "error.unExpectedError", null);
            errors.add("error", new ActionError("error.simpleGenericError",
                    errorMess));
            saveErrors(request, errors);
            return mapping.findForward(Constants.GLOBAL_FORWARD_ESW_ERROR);
        }
        
    	/***
		ActionErrors errors = UserShopLogic.addToCart(request, form.getUserShopForm());		
		if(errors!=null && errors.size()>0) { 
			saveErrors(request, errors);
		}		    	
		***/
    	log.info("after calling OrdersLogic.reorder");
    	
    	//No need to select any tab for shopping cart view.
		//form.setSelectedMainTab(Constants.EMPTY); ???
        //form.setSelectedSubTab(Constants.EMPTY); ???
		
		return mapping.findForward(MAPPING_VIEW_SHOPPING_CART);
	}
	
    /*
     * Private method to determine what action forward should be returned after a Modify request.
     */
    private ActionForward handleModifyOrderRequest(HttpServletRequest request, HttpServletResponse response,
			OrdersForm form, ActionMapping mapping) {
		
    	ActionForward returnValue = null;
    	ActionErrors errors = new ActionErrors();
    	ActionMessages messages = new ActionMessages();
    	
    	/*** Modify Order: Begin ***/
        
        log.info("before calling OrdersLogic.modify()");
 
    	try {
    	    CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
            if(appUser.isBrowseOnly()){
                String message = ClwI18nUtil.getMessage(request,"orders.error.browseOnlyCannotPlaceOrder", null);
                errors.add("error", new ActionMessage("message.simpleMessage", message));
                saveErrors(request, errors);
                return mapping.findForward(MAPPING_VIEW_ORDER_DETAIL);    
            }
            errors = OrdersLogic.modify(request, form);
            if (errors.size() > 0 && errors!=null) {
                saveErrors(request, errors);
            }
        } catch (Exception e) {
            log.error("Unexpected exception in handleModifyOrderRequest() method while executing OrdersLogic.modify(request, form)",
                            e);
            String errorMess = ClwI18nUtil.getMessage(request,
                    "error.unExpectedError", null);
            errors.add("error", new ActionError("error.simpleGenericError",
                    errorMess));
            saveErrors(request, errors);
            return mapping.findForward(Constants.GLOBAL_FORWARD_ESW_ERROR);
        }
        
    	/***
		ActionErrors errors = UserShopLogic.addToCart(request, form.getUserShopForm());		
		if(errors!=null && errors.size()>0) { 
			saveErrors(request, errors);
		}		    	
		***/
        
    	log.info("after calling OrdersLogic.modify()");
    	
        if (!errors.isEmpty())  {  
        	 returnValue = mapping.findForward(MAPPING_VIEW_ORDER_DETAIL);
        	 return returnValue;            	 
        }
    	
    	/*** Modify Order: End ***/
    	
    	
   	    /*** Save data from the screen in the Database: Begin ***/
   	    try {
        	boolean receivedFl = form.getHasFunctionReceiving();
        	log.info("receivedFl = " + receivedFl);
        	boolean rejectFl = false;
            //errors = OrdersLogic.saveOrderDetail(request, form, receivedFl);
        	Map<String,Object> saveResults = OrdersLogic.saveOrderDetail(request, form, receivedFl, rejectFl);
        	ActionErrors modifyOrderErrors = (ActionErrors) saveResults.get(ActionErrors.GLOBAL_ERROR);
    	    messages = (ActionMessages) saveResults.get(ActionErrors.GLOBAL_MESSAGE);
            if (messages.size() > 0) {
        		saveMessages(request, messages);
        	}
            if (modifyOrderErrors.size() > 0) {
            	errors.add(modifyOrderErrors);
                saveErrors(request, errors);
            }
        	
        } catch (Exception e) { 
            log.error("Unexpected exception in handleModifyOrderRequest() while saving Order Detail data",
                            e);
            String errorMess = ClwI18nUtil.getMessage(request,
                    "error.unExpectedError", null);
            errors.add("error", new ActionError("error.simpleGenericError",
                    errorMess));
            saveErrors(request, errors);
            return mapping.findForward(Constants.GLOBAL_FORWARD_ESW_ERROR);
        }
        if (!errors.isEmpty())  {  
       	 returnValue = mapping.findForward(MAPPING_VIEW_ORDER_DETAIL);
       	 return returnValue;            	 
        }
        /*** Save data from the screen in the Database: End ***/
    	
    	//No need to select any tab for shopping cart view.
		//form.setSelectedMainTab(Constants.EMPTY); 
        //form.setSelectedSubTab(Constants.EMPTY); 
		
		return mapping.findForward(MAPPING_VIEW_SHOPPING_CART);
	}
    
    /*
     * Private method to determine what action forward should be returned after a reject order
     * request.
     */
    private ActionForward handleRejectPendingOrderRequest(HttpServletRequest request, HttpServletResponse response, 
    		OrdersForm form, ActionMapping mapping) {
    	
    	ActionForward returnValue = null;
    	ActionErrors errors = new ActionErrors();
    	ActionMessages messages = new ActionMessages();
    	
    	String orderIdM = form.getOrderId();
		
		log.info("handleRejectPendingOrderRequest().orderIdM_1 = " + orderIdM);
		
		SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
		
		if(orderIdM.equals(StringUtils.EMPTY)){ //orderId is NOT set in the OrdersForm
		   
		   orderIdM = sessionDataUtil.getOrderId();
		}
		else {
			sessionDataUtil.setOrderId(orderIdM);
		}
				
		log.info("handleRejectPendingOrderRequest.orderIdM_2 = " + orderIdM);		
		
		
    	String[] pOrderIds2 = new String[1]; 
    	pOrderIds2[0] = orderIdM;
    	
    	log.info("pOrderIds2[0] = " + pOrderIds2[0]);
    	
         /*** Reject Pending Orders: Begin ***/
        
    	Map<String,Object> results = DashboardLogic.performRejectPendingOrders(request, pOrderIds2);
    	errors = (ActionErrors) results.get(ActionErrors.GLOBAL_ERROR);
    	ActionMessages rejectOrderMessages = (ActionMessages) results.get(ActionErrors.GLOBAL_MESSAGE);
            	    	        
        //append any errors that occurred during the show pending order request to any errors that occurred
        //during the reject order request
        ActionMessages pendingOrdersErrors = getErrors(request);
        errors.add(pendingOrdersErrors);        
    	
		//returnValue = mapping.findForward(MAPPING_VIEW_ORDER_DETAIL);
		
        //save any errors that occurred
    	if (errors.size() > 0) {
    		saveErrors(request, errors);
    	}
    	//save any messages that we need to return
    	if (rejectOrderMessages.size() > 0) {
    		messages.add(rejectOrderMessages);
    		saveMessages(request, messages);
    	}
    	
        if (!errors.isEmpty())  {  
         	 returnValue = mapping.findForward(MAPPING_VIEW_ORDER_DETAIL);
         	 return returnValue;            	 
        }
        
    	/*** Reject Pending Orders: End ***/
    	
   	    /*** Save data from the screen in the Database: Begin ***/
   	    try {
        	boolean receivedFl = form.getHasFunctionReceiving();
        	log.info("receivedFl = " + receivedFl);
        	boolean rejectFl = true;
            //errors = OrdersLogic.saveOrderDetail(request, form, receivedFl);
        	Map<String,Object> saveResults = OrdersLogic.saveOrderDetail(request, form, receivedFl, rejectFl);
        	errors = (ActionErrors) saveResults.get(ActionErrors.GLOBAL_ERROR);        	
        	ActionMessages saveMessages = (ActionMessages) saveResults.get(ActionErrors.GLOBAL_MESSAGE);
            if (errors.size() > 0) {
                saveErrors(request, errors);
            }
        	if (saveMessages.size() > 0) {
        		messages.add(saveMessages);
        		saveMessages(request, messages);
        	}        	    	    
        } catch (Exception e) { 
            log.error("Unexpected exception in handleRejectPendingOrderRequest() while saving Customer Comments",
                            e);
            String errorMess = ClwI18nUtil.getMessage(request,
                    "error.unExpectedError", null);
            errors.add("error", new ActionError("error.simpleGenericError",
                    errorMess));
            saveErrors(request, errors);
            return mapping.findForward(Constants.GLOBAL_FORWARD_ESW_ERROR);
        }
        if (!errors.isEmpty())  {  
       	 returnValue = mapping.findForward(MAPPING_VIEW_ORDER_DETAIL);
       	 return returnValue;            	 
        }
        /*** Save data from the screen in the Database: End ***/
    	            	
        //Do we need to refresh Pending-Order-Detail page and show the Order as "Rejected" after it was rejected ? Yes    	
    	//call the handleShowOrdersRequestByOrderId() method, so the user is returned to a refreshed pending order page
        returnValue = handleShowOrdersRequestByOrderId(request, response, form, mapping);
        
    	//call the populateCommonData() method so that the location budget information is refreshed.
    	populateCommonData(request, form);
        
        return returnValue;
    }
    
    /*
     * Private method to determine what action forward should be returned after an approve order
     * request.
     */
    private ActionForward handleApprovePendingOrderRequest(HttpServletRequest request, HttpServletResponse response, 
    		OrdersForm form, ActionMapping mapping) {
    	    	
    	String[] pOrderIds1 = new String[1]; 
    	pOrderIds1[0] = form.getOrderId();
    	
    	log.info("pOrderIds1[0] = " + pOrderIds1[0]);
    	log.info("form.getApprovalDate() = " + form.getApprovalDate());
    	
    	ActionForward returnValue = null;
    	ActionErrors errors = new ActionErrors();
    	ActionMessages messages = new ActionMessages();
    	    	
        	/*** Approve Pending Orders: Begin ***/   
    	
    	    boolean checkPoNum = true;
    	    log.info("form.getOrderOpDetailForm().getRequestPoNum() = " + form.getOrderOpDetailForm().getRequestPoNum());
    	    Map<String,Object> results = DashboardLogic.performApprovePendingOrders(request, pOrderIds1, 
        			form.getApprovalDate(), form.getOrderOpDetailForm().getRequestPoNum(), checkPoNum);
    	    errors = (ActionErrors) results.get(ActionErrors.GLOBAL_ERROR);
    	    ActionMessages approvalMessages = (ActionMessages) results.get(ActionErrors.GLOBAL_MESSAGE);
    	    
    	    //save the approval date passed in (the call to handleShowOrdersRequestByOrderId() below resets
    	    //the approval date to the current date, which may be different from the approval date passed in).
      	    String approvalDate = form.getApprovalDate();                    	
            
            //restore the approval date that was passed in
            form.setApprovalDate(approvalDate);
        
            //append any errors that occurred during the show pending order request to any errors that occurred
            //during the approve order request
            ActionMessages pendingOrdersErrors = getErrors(request);
            errors.add(pendingOrdersErrors);
            
            
            //save any errors that occurred
        	if (errors.size() > 0) {
        		saveErrors(request, errors);
        	}
        	//save any messages that we need to return
        	if (approvalMessages.size() > 0) {
        		messages.add(approvalMessages);
        		saveMessages(request, messages);
        	}
        	
            if (!errors.isEmpty())  {  
              	 returnValue = mapping.findForward(MAPPING_VIEW_ORDER_DETAIL);
              	 return returnValue;            	 
            }
            
            /*** Approve Pending Orders: End ***/
            
            /*** Save data from the screen in the Database: Begin ***/
       	    try {
            	boolean receivedFl = form.getHasFunctionReceiving();
            	log.info("receivedFl = " + receivedFl);
            	boolean rejectFl = false;
            	Map<String,Object> saveResults = OrdersLogic.saveOrderDetail(request, form, receivedFl, rejectFl);
            	errors = (ActionErrors) saveResults.get(ActionErrors.GLOBAL_ERROR);
            	ActionMessages saveMessages = (ActionMessages) saveResults.get(ActionErrors.GLOBAL_MESSAGE);
                if (errors.size() > 0) {
                    saveErrors(request, errors);
                }
            	if (saveMessages.size() > 0) {
            		messages.add(saveMessages);
            		saveMessages(request, messages);
            	}
            } catch (Exception e) { 
                log.error("Unexpected exception in handleApprovePendingOrderRequest() while saving Customer Comments ",
                                e);
                String errorMess = ClwI18nUtil.getMessage(request,
                        "error.unExpectedError", null);
                errors.add("error", new ActionError("error.simpleGenericError",
                        errorMess));
                saveErrors(request, errors);
                return mapping.findForward(Constants.GLOBAL_FORWARD_ESW_ERROR);
            }
            
      	    //call the handleShowOrdersRequestByOrderId() method, so the user is returned to a refreshed pending order page
            returnValue = handleShowOrdersRequestByOrderId(request,response, form, mapping);
            
            if (!errors.isEmpty())  {  
            	saveErrors(request, errors);	
           	    returnValue = mapping.findForward(MAPPING_VIEW_ORDER_DETAIL);
           	    return returnValue;            	 
            }            
       	   
            /*** Save data from the screen in the Database: End ***/
            	
		
        //save any errors that occurred
    	if (errors.size() > 0) {
    		saveErrors(request, errors);
    	}
    	//save any messages that we need to return
    	if (messages.size() > 0) {
    		saveMessages(request, messages);
    	}
    	
    	//call the populateCommonData() method so that the location budget information is refreshed.
    	populateCommonData(request, form);
        
        return returnValue;
    }
    /*
     * Private method to gather data commonly needed on the Orders/Order-Detail-Pending web pages
     */
    private void populateCommonData(HttpServletRequest request, OrdersForm form) {
    	//Status Drop down
    	form.setOrdersStatusFieldChoices(ClwI18nUtil.getOrdersStatusFieldChoices(request));
    	//Orders Date Range
    	form.setOrdersDateRangeFieldChoices(ClwI18nUtil.getOrdersDateRangeFieldChoices(request));

    	form.setFutureOrdersDateRangeFieldChoices(ClwI18nUtil.getFutureOrdersDateRangeChoices(request));
    	
    	//Pending Order Approval => today's date: Begin
    	form.setApprovalDate(ClwI18nUtil.getTodaysDate(request));    	
    	//Pending Order Approval => today's date: End
    	
    	//Begin: Location Drop down
    	
    	SiteData currentLocation = ShopTool.getCurrentSite(request);
		List<LabelValueBean> locationChoices = null;
		
		if(currentLocation!=null) {
			locationChoices = ClwI18nUtil.getLocationChoices(request);
		} else {
			locationChoices = new ArrayList<LabelValueBean>(); 
		}
    	
    	try {
    		SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
    		//STJ-5677:
	        //LocationSearchDto locationSearchDto = sessionDataUtil.getLocationSearchDto();
    		LocationSearchDto locationSearchDto = sessionDataUtil.getLocationSearchDtoMap().get(Constants.SPECIFY_LOCATIONS_ALL_ORDERS);
    		if(locationSearchDto!=null && Utility.isSet(locationSearchDto.getMatchingLocations()) && locationSearchDto.getMatchingLocations().size()>1) {
    			OrderSearchDto orderSearchDto = sessionDataUtil.getOrderSearchDto();
    			if(orderSearchDto==null) {
    				orderSearchDto = form.getOrdersSearchInfo();
    			}
        		//Need to add All Location(Number of Locations) as the first element in the list. If there are matching locations.
            	locationChoices.add(0,new LabelValueBean(orderSearchDto.getLocations(), Constants.ORDERS_ALL_LOCATIONS));
        	}
    	}finally{    		
    	}
    	form.setOrdersLocationFieldChoices(locationChoices);
    	//End: Location Drop down
    	
    }
    
    private DeliveryScheduleViewVector getParOrderSchedules(HttpServletRequest request, int locationId, ActionErrors errors) {
    	DeliveryScheduleViewVector returnValue = null;
    	//save the current list of schedules
        Object currentSchedules = request.getSession().getAttribute("Related.site.distschedules.vector");
        StoreSiteMgrForm siteForm = new StoreSiteMgrForm();
        siteForm.setId(locationId);
        try {
        	//STJ-5757.
        	//StoreSiteMgrLogic.searchSiteSchedules(request, siteForm);
        	StoreSiteMgrLogic.searchCorporateSchedules(request, siteForm);
        	returnValue = (DeliveryScheduleViewVector) request.getSession().getAttribute("Related.site.corpschedules.vector");
        }
        catch (Exception exc) {
        	String errorMess = ClwI18nUtil.getMessage(request, "orders.schedule.error.unexpectedParSchedulesRetrievalError");
        	errors.add("error", new ActionError("error.simpleGenericError", errorMess));
        }
        finally {
        	//restore the original list of schedules
        	request.getSession().setAttribute("Related.site.corpschedules.vector", currentSchedules);
        }
		return returnValue;
    }
    
    private ActionForward handleViewWebisteOrderDetailRequest(HttpServletRequest request, HttpServletResponse response,
			OrdersForm form, ActionMapping mapping) {
    	HttpSession session = request.getSession(false);
    	session.setAttribute(Constants.MOBILE_CLIENT,Constants.FALSE);
    	SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
    	sessionDataUtil.setWebUI(true);
    	ClwCustomizer.flushFileMapCache(session);
    	return handleShowOrdersRequestByOrderId(request, response, form, mapping);
    }
    //STJ-5261 New UI - Mobile All Orders.
    private ActionForward handleViewTenMoreOrdersRequest(HttpServletRequest request, HttpServletResponse response,
			OrdersForm form, ActionMapping mapping) {
    	SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
    	sessionDataUtil.setOrdersLength(sessionDataUtil.getOrdersLength() + 10);
    	return handleShowAllOrdersRequest(request, response, form, mapping);
    }
    
    /*
     * Private method to determine what action forward should be returned after a view web site
     * request.STJ-5261 New UI - Mobile All Orders.
     */
    private ActionForward handleShowWebsiteRequest(HttpServletRequest request, HttpServletResponse response, 
    		OrdersForm form, ActionMapping mapping) {
    	HttpSession session = request.getSession(false);
    	session.setAttribute(Constants.MOBILE_CLIENT,"false");
    	SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
    	sessionDataUtil.setWebUI(true);
    	ClwCustomizer.flushFileMapCache(session);
    	return handleShowAllOrdersRequest(request, response, form, mapping);
    	
    }
    
    /*
     * Private method to determine what action forward should be returned after an add order
     * comment request.
     */
    private ActionForward handleAddOrderCommentRequest(HttpServletRequest request, HttpServletResponse response, 
    		OrdersForm form, ActionMapping mapping) {
    	
    	ActionForward returnValue = null;
    	String jsonResponse = DashboardLogic.performAddOrderCommentResponseJson(request, response, form.getOrderComment());
    	try {
    		response.setContentType(Constants.CONTENT_TYPE_JSON);
    		response.setHeader("Cache-Control", "no-cache");
    		response.setCharacterEncoding(Constants.UTF_8);
    		response.getWriter().write(jsonResponse);
    	}
    	catch (Exception e) {
            ActionErrors errors = new ActionErrors();
            String errorMess = ClwI18nUtil.getMessage(request, "error.unExpectedError", null);
            errors.add("error", new ActionError("error.simpleGenericError", errorMess));
            saveErrors(request, errors);
        	return mapping.findForward(Constants.GLOBAL_FORWARD_ESW_ERROR);    
    	}
    	return returnValue;
    }
}

