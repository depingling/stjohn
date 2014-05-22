/**
 * Title: DashboardAction.java
 * Description: This is the Struts Action class handling the ESW dashboard functionality.
 */

package com.espendwise.view.actions.esw;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dto.LocationSearchDto;
import com.cleanwise.service.api.dto.OrderSearchDto;
import com.cleanwise.service.api.session.User;
import com.cleanwise.service.api.util.SessionDataUtil;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.OrderStatusDescData;
import com.cleanwise.service.api.value.OrderStatusDescDataVector;
import com.cleanwise.service.api.value.SiteData;
import com.cleanwise.service.apps.MessageService;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.logic.HandleOrderLogic;
import com.cleanwise.view.logic.esw.SiteLocationBudgetLogic;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.ClwCustomizer;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.SessionTool;
import com.cleanwise.view.utils.ShopTool;
import com.espendwise.view.forms.esw.DashboardForm;
import com.espendwise.view.logic.esw.DashboardLogic;

/**
 * Implementation of <code>Action</code> that handles log on functionality.
 */
public final class DashboardAction extends EswAction {
    private static final Logger log = Logger.getLogger(DashboardAction.class);
    
    //constants to hold the various action mappings that can be returned by this action class.
    private static final String MAPPING_DASHBOARD_SHOW_PENDING_ORDERS = "dashboardShowPendingOrders";
    private static final String MAPPING_DASHBOARD_SHOW_PREVIOUS_ORDERS = "dashboardShowPreviousOrders";
    private static final String MAPPING_DASHBOARD_SHOW_MOST_RECENT_ORDER = "dashboardShowMostRecentOrder";
    private static final String MAPPING_DASHBOARD_SELECT_LOCATION = "dashboardSelectLocation";
    private static final String MAPPING_VIEW_SHOPPING_CART = "viewShoppingCart";

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

    	HttpSession session = request.getSession();
    	CleanwiseUser user = (CleanwiseUser) session.getAttribute(Constants.APP_USER);        
        DashboardForm theForm = (DashboardForm)form;
        SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
        
    	ActionForward returnValue = null;
    	
        //determine what action to perform. If an operation has been specified use it, otherwise 
    	//use the previously executed operation (if any).
    	String operation = theForm.getOperation();
    	if (!Utility.isSet(operation)) {
    		operation = sessionDataUtil.getPreviousDashboardAction();
    	}
    	
    	//If no operation was specified and there is no previous operation, we're arriving here
    	//for the first time so determine where to bring the user.  If the user has pending orders 
    	//then bring them to the pending orders tab.  Otherwise, bring them to the select location
    	//page if they don't have a location selected or bring them to the most recent order page 
    	//if they do have a location selected.
    	if (!Utility.isSet(operation)) {
    		OrderStatusDescDataVector pendingOrders = null;
			OrderSearchDto pendingOrderSearchDto =  new OrderSearchDto(user.getUserId() + "");
			//only retrieve basic data for this search - details will be retrieved later if necessary
			pendingOrderSearchDto.setRetrieveOrderHistory(false);
		    pendingOrderSearchDto.setRetrieveOrderItems(false);
		    pendingOrderSearchDto.setRetrieveOrderAddresses(false);
		    pendingOrderSearchDto.setRetrieveOrderAccount(false);
		    pendingOrderSearchDto.setRetrieveOrderMetaData(false);
		    pendingOrderSearchDto.setRetrieveOrderReceptionData(false);
		    pendingOrderSearchDto.setRetrieveOrderAutoOrderData(false);
		    pendingOrderSearchDto.setRetrieveOrderProperties(false);
			ActionErrors errors = DashboardLogic.performPendingOrdersSearch(request, pendingOrderSearchDto);
			if (errors != null && !errors.isEmpty()) {
    			log.error("Unexpected exception in DashboardAction.performSub ");
                errors = new ActionErrors();
                String errorMess = ClwI18nUtil.getMessage(request, "error.unExpectedError", null);
                errors.add("error", new ActionError("error.simpleGenericError", errorMess));
                saveErrors(request, errors);
            	return mapping.findForward(Constants.GLOBAL_FORWARD_ESW_ERROR); 
			}
			else {
				pendingOrders = (OrderStatusDescDataVector)pendingOrderSearchDto.getMatchingOrders();
			}
    		if (pendingOrders != null && pendingOrders.size() > 0) {
    			operation = Constants.PARAMETER_OPERATION_VALUE_SHOW_PENDING_ORDERS;
    		}
    		else {
    			String mobileClient = (String)request.getSession(false).getAttribute(Constants.MOBILE_CLIENT);
    	        if(mobileClient != null && mobileClient.equals("true")){
    	        //5261
    	        	sessionDataUtil.setSelectedMobileMainTab(Constants.TAB_ORDERS);
    	        	return mapping.findForward(Constants.GLOBAL_FORWARD_ESW_ORDERS); 
    	        }
    			if (user.getSite() == null) {
        			operation = Constants.PARAMETER_OPERATION_VALUE_SELECT_LOCATION_START;
        		}
            	else {
            		operation = Constants.PARAMETER_OPERATION_VALUE_SHOW_MOST_RECENT_ORDER;
            	}
        	}
    	}
    	
    	//trim whitespace from the operation
    	if (Utility.isSet(operation)) {
    		operation = operation.trim();
    	}

    	//perform the operation
    	boolean rememberOperation = true;
        if (Constants.PARAMETER_OPERATION_VALUE_SHOW_PENDING_ORDERS.equalsIgnoreCase(operation)) {
        	returnValue = handleShowPendingOrdersRequest(request, response, theForm, mapping);
            //set the active main tab to be the shopping tab.
        	sessionDataUtil.setSelectedMainTab(Constants.TAB_SHOPPING);
            //set the active sub tab to be the dashboard tab.
            sessionDataUtil.setSelectedSubTab(Constants.TAB_DASHBOARD);
            //set the last visited shopping tab to be the dashboard tab.
            sessionDataUtil.setPreviousShoppingModuleTab(Constants.TAB_DASHBOARD);
        }
        else if (Constants.PARAMETER_OPERATION_VALUE_SHOW_MOST_RECENT_ORDER.equalsIgnoreCase(operation)) {
        	returnValue = handleShowMostRecentOrderRequest(request, response, theForm, mapping, false);
            //set the active main tab to be the shopping tab.
        	sessionDataUtil.setSelectedMainTab(Constants.TAB_SHOPPING);
            //set the active sub tab to be the dashboard tab.
            sessionDataUtil.setSelectedSubTab(Constants.TAB_DASHBOARD);
            //set the last visited shopping tab to be the dashboard tab.
            sessionDataUtil.setPreviousShoppingModuleTab(Constants.TAB_DASHBOARD);
        }
        else if (Constants.PARAMETER_OPERATION_VALUE_REORDER.equalsIgnoreCase(operation)) {
            returnValue = handleShowMostRecentOrderRequest(request, response, theForm, mapping, true);
            rememberOperation = false;
        }
        else if (Constants.PARAMETER_OPERATION_VALUE_SHOW_PREVIOUS_ORDERS.equalsIgnoreCase(operation)) {
        	returnValue = handleShowPreviousOrdersRequest(request, response, theForm, mapping);
            //set the active main tab to be the shopping tab.
        	sessionDataUtil.setSelectedMainTab(Constants.TAB_SHOPPING);
            //set the active sub tab to be the dashboard tab.
            sessionDataUtil.setSelectedSubTab(Constants.TAB_DASHBOARD);
            //set the last visited shopping tab to be the dashboard tab.
            sessionDataUtil.setPreviousShoppingModuleTab(Constants.TAB_DASHBOARD);
        }
        else if (Constants.PARAMETER_OPERATION_VALUE_SELECT_LOCATION_START.equalsIgnoreCase(operation)) {
        	returnValue = handleSelectLocationStartRequest(request, response, theForm, mapping);
        	rememberOperation = false;
        }
        else if (Constants.PARAMETER_OPERATION_VALUE_SEARCH_LOCATIONS.equalsIgnoreCase(operation)) {
        	returnValue = handleSearchLocationsRequest(request, response, theForm, mapping);
        	rememberOperation = false;
        }
        else if (Constants.PARAMETER_OPERATION_VALUE_SORT_LOCATIONS.equalsIgnoreCase(operation)) {
        	returnValue = handleSortLocationsRequest(request, response, theForm, mapping);
        	rememberOperation = false;
        }
        else if (Constants.PARAMETER_OPERATION_VALUE_SELECT_LOCATION.equalsIgnoreCase(operation)) {
        	returnValue = handleSelectLocationRequest(request, response, theForm, mapping);
        	rememberOperation = false;
        }
        else if (Constants.PARAMETER_OPERATION_VALUE_SHOW_CHART.equalsIgnoreCase(operation)) {
        	returnValue = handleShowDashBoardChart(request, response, theForm, mapping);
        	rememberOperation = false;
        }
        else if (Constants.PARAMETER_OPERATION_VALUE_ADD_ORDER_COMMENT.equalsIgnoreCase(operation)) {
        	returnValue = handleAddOrderCommentRequest(request, response, theForm, mapping);
        	rememberOperation = false;
        }
        else if (Constants.PARAMETER_OPERATION_VALUE_APPROVE_ORDERS.equalsIgnoreCase(operation)) {
        	returnValue = handleApprovePendingOrdersRequest(request, response, theForm, mapping);
        	rememberOperation = false;
        }
        else if (Constants.PARAMETER_OPERATION_VALUE_REJECT_ORDERS.equalsIgnoreCase(operation)) {
        	returnValue = handleRejectPendingOrdersRequest(request, response, theForm, mapping);
        	rememberOperation = false;
        }
        else if (Constants.PARAMETER_OPERATION_VALUE_VIEW_WEB_SITE.equalsIgnoreCase(operation)) {
        	returnValue = handleShowWebsiteRequest(request, response, theForm, mapping);
        	rememberOperation = false;
        }
        //STJ-5261 New UI - Mobile Select Locations. 
        else if (Constants.PARAMETER_OPERATION_VALUE_SHOW_TEN_MORE_LOCATIONS.equalsIgnoreCase(operation)) {
        	rememberOperation = false;
        	returnValue = handleViewTenMoreLocationsRequest(request, response, theForm, mapping);
        }
        else if (Constants.PARAMETER_OPERATION_VALUE_VIEW_FULL_WEBSITE_SELECT_LOCATION.equalsIgnoreCase(operation)) {
        	returnValue = handleShowFullWebsiteSelectLocationRequest(request, response, theForm, mapping);
        	rememberOperation = false;
        }
        else {
        	returnValue = handleUnknownOperation(request, response, theForm, mapping);
        	rememberOperation = false;
        }
        
        //if we need to remember the operation, do so
        if (rememberOperation) {
    		sessionDataUtil.setPreviousDashboardAction(operation);
        }
        
        return returnValue;    
    }
    
    /*
     * Private method to determine what action forward should be returned after a show pending orders request.
     */
    private ActionForward handleShowPendingOrdersRequest(HttpServletRequest request, HttpServletResponse response, 
    		DashboardForm form, ActionMapping mapping) {
    	
        OrderSearchDto pendingOrderSearchInfo = form.getPendingOrderSearchInfo();
        OrderSearchDto previousPendingOrderSearchInfo = (OrderSearchDto) request.getSession().getAttribute(Constants.PREVIOUS_PENDING_ORDER_SEARCH_DTO);
        //if no user id was specified in the search criteria, try to retrieve one from any previously
        //saved search.  If that fails then default to the current user
        if (!Utility.isSet(pendingOrderSearchInfo.getUserId())) {
        	if (previousPendingOrderSearchInfo != null &&
        			Utility.isSet(previousPendingOrderSearchInfo.getUserId())) {
        		pendingOrderSearchInfo.setUserId(previousPendingOrderSearchInfo.getUserId());
        	}
        	else {
        		CleanwiseUser user = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);        
        		pendingOrderSearchInfo.setUserId(Integer.toString(user.getUserId()));
        	}
        }
	    
	    //specify the order information that should be retrieved
        pendingOrderSearchInfo.setRetrieveOrderHistory(false);
        pendingOrderSearchInfo.setRetrieveOrderItems(false);
        pendingOrderSearchInfo.setRetrieveOrderAddresses(true);
        pendingOrderSearchInfo.setRetrieveOrderAccount(false);
        pendingOrderSearchInfo.setRetrieveOrderMetaData(true);
        pendingOrderSearchInfo.setRetrieveOrderReceptionData(false);
        pendingOrderSearchInfo.setRetrieveOrderAutoOrderData(false);
        pendingOrderSearchInfo.setRetrieveOrderProperties(true);
        
		//get the pending orders
    	ActionErrors errors = DashboardLogic.performPendingOrdersSearch(request, pendingOrderSearchInfo);

    	//if there were no errors, store the updated search info in the session, and generate an
    	//informational message if no orders were found or the maximum number of orders was found.
        if (errors == null || errors.isEmpty()) {
            request.getSession().setAttribute(Constants.PREVIOUS_PENDING_ORDER_SEARCH_DTO, pendingOrderSearchInfo);
            generatePendingOrderCountMessage(request, pendingOrderSearchInfo);
        }
        //if any errors occurred save them so they can be displayed to the user
        else {
            saveErrors(request, errors);
        }

    	//default the approval date to todays date
    	form.setApprovalDate(ClwI18nUtil.formatDateInp(request, new Date()));
        populateCommonData(request, form);
        
        //set the selected sub-subtab to be the pending orders tab
        Utility.getSessionDataUtil(request).setSelectedSubSubTab(Constants.TAB_PENDING_ORDERS);
        
    	return mapping.findForward(MAPPING_DASHBOARD_SHOW_PENDING_ORDERS);    
    }
    
    /*
     * Private method to determine what action forward should be returned after a show most recent order request.
     */
    private ActionForward handleShowMostRecentOrderRequest(HttpServletRequest request, HttpServletResponse response, 
    		DashboardForm form, ActionMapping mapping, boolean doReorder) {
    	ActionErrors errors = new ActionErrors();
    	SiteData currentLocation = ShopTool.getCurrentSite(request);
    	//if the user has not yet selected a location, return an error
        if (currentLocation == null) {
            String errorMess = ClwI18nUtil.getMessage(request,
                    "error.noLocationSelected", null);
            errors.add("error", new ActionError("error.simpleGenericError",
                    errorMess));
        } 
        else {
            if (doReorder) {
            	ActionErrors copyOrderErrors = HandleOrderLogic.copyOrderToCart(request, Utility.parseInt(form
                        .getSelectedOrderIds()[0]));
                if (copyOrderErrors != null && copyOrderErrors.size() > 0) {
                	errors.add(copyOrderErrors);
                }
                else {
                	return mapping.findForward(MAPPING_VIEW_SHOPPING_CART);
                }
            }
            OrderSearchDto mostRecentOrderSearchInfo = form.getMostRecentOrderSearchInfo();
            CleanwiseUser user = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);

            mostRecentOrderSearchInfo.setLocationId(Integer.toString(currentLocation.getSiteId()));
            mostRecentOrderSearchInfo.setUserId(Integer.toString(user.getUserId()));

            ActionErrors errors2 = DashboardLogic.performMostRecentOrderSearch(request,
                    mostRecentOrderSearchInfo, form);
            if (errors2 != null) {
                errors.add(errors2);
            }
            if (mostRecentOrderSearchInfo.getMatchingOrders() == null
                    || mostRecentOrderSearchInfo.getMatchingOrders().isEmpty()) {
                String infoMsg = ClwI18nUtil.getMessage(request,
                                "mostRecentOrder.search.error.noOrdersPlacedForThisLocation",
                                null);
                ActionMessages messages = new ActionMessages();
                messages.add("message", new ActionMessage(
                        "message.simpleMessage", infoMsg));
                saveMessages(request, messages);
            }
        }

        // if any errors occurred save them so they can be displayed to the
        // user
        if (errors != null && !errors.isEmpty()) {
            saveErrors(request, errors);
        }
        populateCommonData(request, form);
        
        //set the selected sub-subtab to be the most recent order tab
        Utility.getSessionDataUtil(request).setSelectedSubSubTab(Constants.TAB_MOST_RECENT_ORDER);
    	
    	return mapping.findForward(MAPPING_DASHBOARD_SHOW_MOST_RECENT_ORDER);    
    }
    
    /*
     * Private method to determine what action forward should be returned after a show previous orders request.
     */
    private ActionForward handleShowPreviousOrdersRequest(HttpServletRequest request, HttpServletResponse response, 
    		DashboardForm form, ActionMapping mapping) {
    	ActionErrors errors = new ActionErrors();
    	SiteData currentLocation = ShopTool.getCurrentSite(request);
    	//if the user has not yet selected a location, return an error
    	if (currentLocation == null) {
            String errorMess = ClwI18nUtil.getMessage(request, "error.noLocationSelected", null);
            errors.add("error", new ActionError("error.simpleGenericError", errorMess));  		
    	}
    	else {
            OrderSearchDto previousOrderSearchInfo = form.getPreviousOrderSearchInfo();
            OrderSearchDto previousPreviousOrderSearchInfo = (OrderSearchDto) request.getSession().getAttribute(Constants.PREVIOUS_PREVIOUS_ORDER_SEARCH_DTO);
            //if no user id was specified in the search criteria, try to retrieve one from any previously
            //saved search.  If that fails then default to the current user
            if (!Utility.isSet(previousOrderSearchInfo.getUserId())) {
            	if (previousPreviousOrderSearchInfo != null &&
            			Utility.isSet(previousPreviousOrderSearchInfo.getUserId())) {
            		previousOrderSearchInfo.setUserId(previousPreviousOrderSearchInfo.getUserId());
            	}
            	else {
            		CleanwiseUser user = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);        
            		previousOrderSearchInfo.setUserId(Integer.toString(user.getUserId()));
            	}
            }
            //if no date range was specified in the search criteria, try to retrieve one from any previously
            //saved search.  If that fails then default to the last 30 days
            if (!Utility.isSet(previousOrderSearchInfo.getDateRange())) {
            	if (previousPreviousOrderSearchInfo != null &&
            			Utility.isSet(previousPreviousOrderSearchInfo.getDateRange())) {
            		previousOrderSearchInfo.setDateRange(previousPreviousOrderSearchInfo.getDateRange());
            	}
            	else {
            		previousOrderSearchInfo.setDateRange(Constants.PREVIOUS_ORDER_DATE_RANGE_VALUE_LAST_THIRTY_DAYS);
            	}
            }
            //specify the currently selected location for the search criteria.  This is always done regardless of 
            //whether it was specified or available in a previous search, since the location may have changed since
            //the last search was performed and we always want to be using the currently selected location.
    		previousOrderSearchInfo.setLocationId(Integer.toString(currentLocation.getSiteId()));
    		//get the previous orders
            errors = DashboardLogic.performPreviousOrdersSearch(request, previousOrderSearchInfo);
        	//if there were no errors, do the following:
            // - store the updated search info in the session
            // - generate an informational message if no orders were found or the maximum number of 
            //	 orders was found
            // - generate an informational message if a total amount is unable to be determined (i.e. not
            //	 all orders have the same locale - see bug STJ-4322) and the user is allowed to see
            //	 prices.
            if (errors == null || errors.isEmpty()) {
                request.getSession().setAttribute(Constants.PREVIOUS_PREVIOUS_ORDER_SEARCH_DTO, previousOrderSearchInfo);
                generatePreviousOrderCountMessage(request, previousOrderSearchInfo);
                OrderStatusDescDataVector orders = (OrderStatusDescDataVector) previousOrderSearchInfo.getMatchingOrders();
                if (Utility.isSet(orders) && ShopTool.getCurrentUser(request).getShowPrice()) {
                	Iterator<OrderStatusDescData> orderIterator = orders.iterator();
                	Map<String, String> orderLocales = new HashMap<String, String>();
                	while (orderIterator.hasNext()) {
                		OrderStatusDescData order = orderIterator.next();
                		BigDecimal orderTotal = order.getDiscountedTotal();
                		if (orderTotal != null && BigDecimal.ZERO.compareTo(orderTotal) != 0) {
                			orderLocales.put(order.getOrderDetail().getLocaleCd().toLowerCase(),order.getOrderDetail().getLocaleCd().toLowerCase());
                		}
                	}
                	if (orderLocales.size() > 1) {
                    	String message = ClwI18nUtil.getMessage(request,"previousOrders.search.orderTotalUnavailable", null); 
                        //append this message to any existing messages
                    	ActionMessages messages = new ActionMessages();
                        messages.add("message", new ActionMessage("message.simpleMessage", message));
                        messages.add(getMessages(request));
                        saveMessages(request, messages);
                	}
                }
            }
    	}

        //if any errors occurred save them so they can be displayed to the user
        if (errors != null && !errors.isEmpty()) {
            saveErrors(request, errors);
        }
        //retrieve data common to all dash board panels and return
        populateCommonData(request, form);
        
        //set the selected sub-subtab to be the previous orders tab
        Utility.getSessionDataUtil(request).setSelectedSubSubTab(Constants.TAB_PREVIOUS_ORDERS);
        
    	return mapping.findForward(MAPPING_DASHBOARD_SHOW_PREVIOUS_ORDERS);    
    }
    
    /*
     * Private method to determine what action forward should be returned after a select location start request.
     */
    private ActionForward handleSelectLocationStartRequest(HttpServletRequest request, HttpServletResponse response, 
    		DashboardForm form, ActionMapping mapping) {
   	
    	//NOTE - this method will need to be altered to support the usage of location search as a
    	//		 filter for orders, etc.  In that situation, instead of returning the previous
    	//		 search this method should return a new search of locations (unfiltered and sorted
    	//		 by most recent visit in descending order).  If it is important for the most recently
    	//		 visited site to be displayed in that scenario, then the new search will have to set 
    	//		 that information as well.
    	//Return the previous search result as it should be used as the basis for the next search.
    	
        //LocationSearchDto previousSearch = (LocationSearchDto) request.getSession().getAttribute(Constants.PREVIOUS_LOCATION_SEARCH_DTO);
        SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
        //LocationSearchDto previousSearch = sessionDataUtil.getLocationSearchDto();
        LocationSearchDto previousSearch = sessionDataUtil.getLocationSearchDtoMap().get(Constants.CHANGE_LOCATION);
        
        generateLocationCountMessage(request, previousSearch);
        form.setLocationSearchInfo(previousSearch);
        populateCommonData(request, form);
        
        //set the active main tab to be empty, since location selection is applicable
        //to multiple modules.
        sessionDataUtil.setSelectedMainTab(Constants.EMPTY);
    	return mapping.findForward(MAPPING_DASHBOARD_SELECT_LOCATION);    
    }
    
    /*
     * Private method to determine what action forward should be returned after a search locations request.
     */
    private ActionForward handleSearchLocationsRequest(HttpServletRequest request, HttpServletResponse response, 
    		DashboardForm form, ActionMapping mapping) {
    	
    	//NOTE - this method will need to be altered to support the usage of location search as a
    	//		 filter for orders, etc.  In that situation, this method should not store the results 
    	//		 of the search in the session.
        LocationSearchDto locationSearchInfo = form.getLocationSearchInfo();
        ActionErrors errors = DashboardLogic.performLocationSearch(request, locationSearchInfo);
        //if any errors were returned from the search, save them so they can be displayed to the user
        if (errors != null && !errors.isEmpty()) {
            saveErrors(request, errors);
        }
        //otherwise store the updated search info in the session
        else {
           // request.getSession().setAttribute(Constants.PREVIOUS_LOCATION_SEARCH_DTO, locationSearchInfo);
            SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
            //sessionDataUtil.setLocationSearchDto(locationSearchInfo);
            sessionDataUtil.getLocationSearchDtoMap().put(Constants.CHANGE_LOCATION,locationSearchInfo);
            generateLocationCountMessage(request, locationSearchInfo);
        }
        
        //retrieve data common to all dash board panels and return
        populateCommonData(request, form);
    	return mapping.findForward(MAPPING_DASHBOARD_SELECT_LOCATION);    
    }
    
    /*
     * Private method to determine what action forward should be returned after a sort locations request.
     */
    private ActionForward handleSortLocationsRequest(HttpServletRequest request, HttpServletResponse response, 
    		DashboardForm form, ActionMapping mapping) {
    	
    	//NOTE - this method will need to be altered to support the usage of location search as a
    	//		 filter for orders, etc.  In that situation, this method should not store the results of 
    	//		 the sort in the session.
    	LocationSearchDto locationSortInfo = form.getLocationSearchInfo();
    	ActionErrors errors = DashboardLogic.performLocationSearch(request, locationSortInfo);
        //if any errors were returned from the sort, save them so they can be displayed to the user
    	if (errors != null && !errors.isEmpty()) {
            saveErrors(request, errors);
    	}
        //otherwise store the updated sort info in the session
    	else {
    		//request.getSession().setAttribute(Constants.PREVIOUS_LOCATION_SEARCH_DTO, locationSortInfo);
    		
    		//TODO: Need to put all the data that is maintained in the session.
    		SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
            //sessionDataUtil.setLocationSearchDto(locationSortInfo);
    		sessionDataUtil.getLocationSearchDtoMap().put(Constants.CHANGE_LOCATION,locationSortInfo);
            
            generateLocationCountMessage(request, locationSortInfo);
    	}
        //retrieve data common to all dash board panels and return
        populateCommonData(request, form);
    	return mapping.findForward(MAPPING_DASHBOARD_SELECT_LOCATION);    
    }
    
    /*
     * Private method to determine what action forward should be returned after a select location request.
     */
    private ActionForward handleSelectLocationRequest(HttpServletRequest request, HttpServletResponse response, 
    		DashboardForm form, ActionMapping mapping) {
    	
    	//NOTE - this method MIGHT need to be altered to support the usage of location search as a
    	//		 filter for orders, etc.  In that situation, if this method is even called (and hopefully
    	//		 it will not need to be) it should not actually set the location for the user and it
    	//		 should return a different action forward.
        CleanwiseUser user = (CleanwiseUser)request.getSession().getAttribute(Constants.APP_USER);
        BusEntityData location = null;
		if (form.getLocation() != null ) {
            try {
            	User userEjb = APIAccess.getAPIAccess().getUserAPI();
            	if (userEjb.isSiteOfUser(form.getLocation().getSiteId(), user.getUserId())) {
            		location = APIAccess.getAPIAccess().getSiteAPI().getSite(form.getLocation().getSiteId()).getBusEntity();
            	}
            }
            catch (Exception exc) {
            	log.error("Unable to retrieve selected location. Exception: " + exc.getMessage());
            }
		}
        ActionErrors errors = null;
        if (location != null) {
            	errors = DashboardLogic.setUserLocation(request, user, location);
            	//STJ-5479
            	String mobileClient = (String)request.getSession(false).getAttribute(Constants.MOBILE_CLIENT);
                if(Utility.isTrue(mobileClient)){
                	if(errors.isEmpty()) {
                		SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
                		sessionDataUtil.setSpecifiedLocation(null);
                	}
                }
    		if (errors != null && !errors.isEmpty()) {
    			DashboardLogic.initEswDefaultValues(request);
    		}
        }
        else {
            String errorMess = ClwI18nUtil.getMessage(request, "location.search.invalidSiteSpecified", null);
            errors = new ActionErrors();
            errors.add("error", new ActionError("error.simpleGenericError", errorMess));
		}
        //if any errors occurred return the user to the select location page, with the results of the previous search
		if (errors != null && !errors.isEmpty()) {
            saveErrors(request, errors);			
           // LocationSearchDto previousSearch = (LocationSearchDto) request.getSession().getAttribute(Constants.PREVIOUS_LOCATION_SEARCH_DTO);
            
            SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
            //LocationSearchDto previousSearch = sessionDataUtil.getLocationSearchDto();
            LocationSearchDto previousSearch = sessionDataUtil.getLocationSearchDtoMap().get(Constants.CHANGE_LOCATION);
            
            form.setLocationSearchInfo(previousSearch);
            populateCommonData(request, form);
        	return mapping.findForward(MAPPING_DASHBOARD_SELECT_LOCATION);    
		}
		//otherwise return the user to the appropriate landing page.
		else {
			//JEE TODO - is this still correct?  It seems odd to return them
			//anywhere other than the page they were on, although we don't 
			//currently have a way to keep track of how to return them there.
			return mapping.findForward(Constants.GLOBAL_FORWARD_ESW_LANDING_PAGE);
		}
    }
    
    /*
     * Private method to determine what action forward should be returned after an add order
     * comment request.
     */
    private ActionForward handleAddOrderCommentRequest(HttpServletRequest request, HttpServletResponse response, 
    		DashboardForm form, ActionMapping mapping) {
    	
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
    
    /*
     * Private method to determine what action forward should be returned after an approve orders
     * request.
     */
    private ActionForward handleApprovePendingOrdersRequest(HttpServletRequest request, HttpServletResponse response, 
    		DashboardForm form, ActionMapping mapping) {
    	//Check if client is on mobile device  
    	String mobileClient = (String)request.getSession(false).getAttribute(Constants.MOBILE_CLIENT);
        if(mobileClient != null && mobileClient.equals("true") && form.getApprovalDate() == null) //if request is from mobile device
    	form.setApprovalDate(ClwI18nUtil.formatDateInp(request, new Date()));//default the approval date to todays date
    	boolean checkPoNum = false;
    	Map<String,Object> results = DashboardLogic.performApprovePendingOrders(request, form.getSelectedOrderIds(), 
    			form.getApprovalDate(), StringUtils.EMPTY, checkPoNum);
    	ActionErrors errors = (ActionErrors) results.get(ActionErrors.GLOBAL_ERROR);
    	ActionMessages messages = (ActionMessages) results.get(ActionErrors.GLOBAL_MESSAGE);
    	
    	//save the approval date passed in (the call to handleShowPendingOrdersRequest() below resets
    	//the approval date to the current date, which may be different from the approval date passed in).
      	String approvalDate = form.getApprovalDate();
                
    	//call the handleShowPendingOrders method, so the user is returned to a refreshed pending orders page
        ActionForward returnValue = handleShowPendingOrdersRequest(request, response, form, mapping);
        
        //restore the approval date that was passed in
        form.setApprovalDate(approvalDate);
        
        //append any errors that occurred during the show pending orders request to any errors that occurred
        //during the approve orders request
        ActionMessages pendingOrdersErrors = getErrors(request);
        errors.add(pendingOrdersErrors);
        
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
     * Private method to determine what action forward should be returned after a reject orders
     * request.
     */
    private ActionForward handleRejectPendingOrdersRequest(HttpServletRequest request, HttpServletResponse response, 
    		DashboardForm form, ActionMapping mapping) {
    	
    	Map<String,Object> results = DashboardLogic.performRejectPendingOrders(request, form.getSelectedOrderIds());
    	ActionErrors errors = (ActionErrors) results.get(ActionErrors.GLOBAL_ERROR);
    	ActionMessages messages = (ActionMessages) results.get(ActionErrors.GLOBAL_MESSAGE);
        
    	//call the handleShowPendingOrders method, so the user is returned to a refreshed pending orders page
        ActionForward returnValue = handleShowPendingOrdersRequest(request, response, form, mapping);
        
        //append any errors that occurred during the show pending orders request to any errors that occurred
        //during the reject orders request
        ActionMessages pendingOrdersErrors = getErrors(request);
        errors.add(pendingOrdersErrors);
        
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
     * Private method to gather data commonly needed on the dashboard page.
     */
    private void populateCommonData(HttpServletRequest request, DashboardForm form) {
    	ActionErrors errors = new ActionErrors();
    	form.setProductSearchFieldChoices(ClwI18nUtil.getProductSearchFieldChoices(request));
    	form.setPreviousOrderDateRangeChoices(ClwI18nUtil.getPreviousOrderDateRangeChoices(request));
    	//STJ-6084 begin
    	boolean prepareBudgetTotal = false;
    	CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
    	if (appUser != null) {
    		SiteData site = appUser.getSite();
    		prepareBudgetTotal = (site != null && site.getBusEntity() != null && site.getBusEntity().getBusEntityId() > 0);
    	}
    	if (prepareBudgetTotal) {
    		errors = SiteLocationBudgetLogic.prepareLocationBudgetTotal(request, form);
    	}
    	//STJ-6084 end
    	if(errors !=null && errors.size()>0){
    		saveErrors(request, errors);
    	}
		try {
	    	CleanwiseUser user = (CleanwiseUser)request.getSession().getAttribute(Constants.APP_USER);
	    	form.setMessages(MessageService.getMessagesForUser(user.getUserId()));
		}
		catch (Exception e) {
			log.error("Unable to retrieve user messages: ", e);
            String errorMess = ClwI18nUtil.getMessage(request, "message.error.unableToRetrieveMessages", null);
            errors.add("error", new ActionError("error.simpleGenericError", errorMess));
            saveErrors(request, errors);
		}
    }
    
    /*
     * Private method to return the appropriate message if zero or the maximum number of locations
     * was found during a search or sort
     */
    private void generateLocationCountMessage(HttpServletRequest request, LocationSearchDto dto) {
        //if no locations were found, or the maximum number of locations was found, return an informational
        //message
        int locationCount = dto.getMatchingLocations().size();
        String message = null;
        if (locationCount == 0) {
        	message = ClwI18nUtil.getMessage(request,"location.search.noResults", null);
        }
        else if (locationCount >= Constants.LOCATION_SEARCH_RESULTS_MAX_DISPLAY) {
        	Object[] insertionStrings = new Object[1];
        	insertionStrings[0] = Integer.toString(Constants.LOCATION_SEARCH_RESULTS_MAX_DISPLAY);
        	message = ClwI18nUtil.getMessage(request,"location.search.maximumResultsMet", insertionStrings); 
        }
        if (Utility.isSet(message)) {
        	ActionMessages messages = new ActionMessages();
        	messages.add("message", new ActionMessage("message.simpleMessage", message));
        	saveMessages(request, messages);
        }
    }
    
    /*
     * Private method to return the appropriate message if zero or the maximum number of orders
     * was found during a search or sort
     */
    private void generatePreviousOrderCountMessage(HttpServletRequest request, OrderSearchDto dto) {
        //if no previous orders were found, or the maximum number of orders was found, return an informational
        //message
        int orderCount = dto.getMatchingOrders().size();
        String message = null;
        if (orderCount == 0) {
        	message = ClwI18nUtil.getMessage(request,"previousOrder.search.noResults", null);
        }
        else if (orderCount >= Constants.ORDERS_SEARCH_RESULTS_MAX_DISPLAY) {
        	Object[] insertionStrings = new Object[1];
        	insertionStrings[0] = Integer.toString(Constants.ORDERS_SEARCH_RESULTS_MAX_DISPLAY);
        	message = ClwI18nUtil.getMessage(request,"previousOrders.search.maximumResultsMet", insertionStrings); 
        }
        if (Utility.isSet(message)) {
        	ActionMessages messages = new ActionMessages();
        	messages.add("message", new ActionMessage("message.simpleMessage", message));
        	saveMessages(request, messages);
        }
    }
    
    /*
     * Private method to return the appropriate message if zero or the maximum number of orders
     * was found during a search or sort
     */
    private void generatePendingOrderCountMessage(HttpServletRequest request, OrderSearchDto dto) {
        //if no pending orders were found, or the maximum number of orders was found, return an informational
        //message
        int orderCount = dto.getMatchingOrders().size();
        String message = null;
        if (orderCount == 0) {
        	message = ClwI18nUtil.getMessage(request,"pendingOrders.search.noResults", null);
        }
        else if (orderCount >= Constants.ORDERS_SEARCH_RESULTS_MAX_DISPLAY) {
        	Object[] insertionStrings = new Object[1];
        	insertionStrings[0] = Integer.toString(Constants.ORDERS_SEARCH_RESULTS_MAX_DISPLAY);
        	if (ShopTool.getCurrentUser(request).isCanApprovePurchases()) {
        		message = ClwI18nUtil.getMessage(request,"pendingOrders.search.maximumResultsMatchingCriteriaMet", insertionStrings);
        	}
        	else {
        		message = ClwI18nUtil.getMessage(request,"pendingOrders.search.maximumResultsMet", insertionStrings);
        	}
        }
        if (Utility.isSet(message)) {
        	ActionMessages messages = new ActionMessages();
        	messages.add("message", new ActionMessage("message.simpleMessage", message));
        	saveMessages(request, messages);
        }
    }
    
    /*
     * Private method to save state of the chart in session
     */
    private ActionForward handleShowDashBoardChart(HttpServletRequest request, HttpServletResponse response, 
    		DashboardForm form, ActionMapping mapping) {
    	String chartState = (String)request.getParameter("state");
    	SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
    	sessionDataUtil.setShowChart(chartState);
    	//request.getSession().setAttribute(Constants.LOCATION_BUDGET_CHART_STATE,chartState);
		return null;    
    }
    
    /*
     * Private method to determine what action forward should be returned after a reject orders
     * request.
     */
    private ActionForward handleShowWebsiteRequest(HttpServletRequest request, HttpServletResponse response, 
    		DashboardForm form, ActionMapping mapping) {
    	HttpSession session = request.getSession(false);
    	session.setAttribute(Constants.MOBILE_CLIENT,"false");
    	SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
    	sessionDataUtil.setWebUI(true);
    	ClwCustomizer.flushFileMapCache(session);
    	return handleShowPendingOrdersRequest(request, response, form, mapping);
    	
    }
    //STJ-5261 New UI - Mobile Select Location.
    private ActionForward handleViewTenMoreLocationsRequest(HttpServletRequest request, HttpServletResponse response,
			DashboardForm form, ActionMapping mapping) {
    	SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
    	sessionDataUtil.setLocationsLength(sessionDataUtil.getLocationsLength() + 10);
    	return handleSelectLocationStartRequest(request, response, form, mapping);
    }
    /*
     * Private method to determine what action forward should be returned after a view web site
     * request.
     */
    private ActionForward handleShowFullWebsiteSelectLocationRequest(HttpServletRequest request, HttpServletResponse response, 
    		DashboardForm form, ActionMapping mapping) {
    	HttpSession session = request.getSession(false);
    	session.setAttribute(Constants.MOBILE_CLIENT,"false");
    	SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
    	sessionDataUtil.setWebUI(true);
    	ClwCustomizer.flushFileMapCache(session);
    	return handleSelectLocationStartRequest(request, response, form, mapping);
    	
    }
}
