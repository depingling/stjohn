/**
 * Title: OrdersLogic 
 * Description: This is the business logic class handling the ESW orders functionality.
 */
package com.espendwise.view.logic.esw;

import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dto.FutureOrderDto;
import com.cleanwise.service.api.dto.LocationBudgetChartDto;
import com.cleanwise.service.api.dto.LocationSearchDto;
import com.cleanwise.service.api.dto.OrderSearchDto;
import com.cleanwise.service.api.session.Account;
import com.cleanwise.service.api.session.AutoOrder;
import com.cleanwise.service.api.session.Order;
import com.cleanwise.service.api.session.OrderGuide;
import com.cleanwise.service.api.session.ProductInformation;
import com.cleanwise.service.api.session.PropertyService;
import com.cleanwise.service.api.session.Schedule;
import com.cleanwise.service.api.session.ShoppingServices;
import com.cleanwise.service.api.session.Site;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.SessionDataUtil;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.AccountData;
import com.cleanwise.service.api.value.DeliveryScheduleView;
import com.cleanwise.service.api.value.DeliveryScheduleViewVector;
import com.cleanwise.service.api.value.FiscalCalendarInfo;
import com.cleanwise.service.api.value.FiscalCalendarInfo.BudgetPeriodInfo;
import com.cleanwise.service.api.value.FiscalCalenderData;
import com.cleanwise.service.api.value.FiscalCalenderView;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.OrderData;
import com.cleanwise.service.api.value.OrderGuideInfoData;
import com.cleanwise.service.api.value.OrderInfoBase;
import com.cleanwise.service.api.value.OrderItemData;
import com.cleanwise.service.api.value.OrderItemDescData;
import com.cleanwise.service.api.value.OrderItemDescDataVector;
import com.cleanwise.service.api.value.OrderItemJoinData;
import com.cleanwise.service.api.value.OrderItemJoinDataVector;
import com.cleanwise.service.api.value.OrderJoinData;
import com.cleanwise.service.api.value.OrderPropertyData;
import com.cleanwise.service.api.value.OrderPropertyDataVector;
import com.cleanwise.service.api.value.OrderScheduleJoin;
import com.cleanwise.service.api.value.OrderStatusCriteriaData;
import com.cleanwise.service.api.value.OrderStatusDescData;
import com.cleanwise.service.api.value.OrderStatusDescDataVector;
import com.cleanwise.service.api.value.ProductData;
import com.cleanwise.service.api.value.ProductViewDefData;
import com.cleanwise.service.api.value.ProductViewDefDataVector;
import com.cleanwise.service.api.value.PropertyData;
import com.cleanwise.service.api.value.PropertyDataVector;
import com.cleanwise.service.api.value.ScheduleOrderDates;
import com.cleanwise.service.api.value.ShoppingCartData;
import com.cleanwise.service.api.value.ShoppingCartItemData;
import com.cleanwise.service.api.value.ShoppingCartItemDataVector;
import com.cleanwise.service.api.value.SiteData;
import com.cleanwise.service.api.value.UserData;
import com.cleanwise.view.forms.UserShopForm;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.logic.HandleOrderLogic;
import com.cleanwise.view.logic.OrderDetailLogic;
import com.cleanwise.view.logic.OrderOpLogic;
import com.cleanwise.view.logic.OrderSchedulerLogic;
import com.cleanwise.view.logic.UserShopLogic;
import com.cleanwise.view.logic.esw.SiteLocationBudgetLogic;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.ClwCustomizer;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.DisplayListSort;
import com.cleanwise.view.utils.RemoteWebClient;
import com.cleanwise.view.utils.SessionTool;
import com.cleanwise.view.utils.ShopTool;
import com.espendwise.view.forms.esw.OrdersForm;

public class OrdersLogic {

	private static final Logger log = Logger.getLogger(OrdersLogic.class);
	
	/**
	 * Find the orders matching a set of criteria.
	 * @param ordersSearchInfo - a <code>OrderSearchDto</code> containing the search criteria.
	 * @return An <code>ActionErrors</code> object containing any errors.
	 */
	@SuppressWarnings({"unchecked", "deprecation"})
	public static ActionErrors performAllOrdersSearch(HttpServletRequest request, OrderSearchDto ordersSearchInfo) {

		ActionErrors errors = new ActionErrors();
		
		errors = validateOrdersCriteria(request,ordersSearchInfo);
		if(errors.size()>0) {
			return errors;
		}
		
		try {
	        CleanwiseUser appUser = ShopTool.getCurrentUser(request);
	        SiteData currentLocation = appUser.getSite();
	        	
	        //Build the Search criteria
	        OrderStatusCriteriaData searchCriteria = OrderStatusCriteriaData.createValue();
	        searchCriteria.getExcludeOrderStatusList().add(RefCodeNames.ORDER_STATUS_CD.REFERENCE_ONLY);
	        
	        //STJ-4759
	        searchCriteria.setFilterByRevisedOrderDate(true);
	        
	        // Search order must have userId or/and userTypeCd
	        String userId = String.valueOf(appUser.getUser().getUserId());
	        String userType = Utility.strNN(appUser.getUser().getUserTypeCd());
	
	        searchCriteria.setUserId(userId);
	        searchCriteria.setUserTypeCd(userType);
	        
	        //Location Data
	        if(ordersSearchInfo.getLocationSelected().trim().startsWith(Constants.ORDERS_ALL_LOCATIONS)) {
	        	//Do not set SiteId in search criteria for all sites configured to the user
		        searchCriteria.setSiteId("");
	        } else if (ordersSearchInfo.getLocationSelected().trim().startsWith(Constants.ORDERS_CURRENT_LOCATION)) {//STJ-5261
	        	int siteId = 0;
	        	String mobileClient = (String)request.getSession(false).getAttribute(Constants.MOBILE_CLIENT);
    	        if(Utility.isTrue(mobileClient)) {
    	        	SessionDataUtil sessionData = Utility.getSessionDataUtil(request);
    	        	if(sessionData.getSpecifiedLocation() != null) {
    	        		siteId = sessionData.getSpecifiedLocation().getSiteId();
    	        	}
    	        	else {
    	        		if(appUser.getSite() != null) {
    	        			siteId = appUser.getSite().getBusEntity().getBusEntityId();
    	        		} else {
    	        			String errorMess = ClwI18nUtil.getMessage(request, "mobile.order.search.error.noLocationSpecified", null);
		    	            errors.add("error", new ActionError("error.simpleGenericError", errorMess));
		    	            return errors;	
    	        		}
    	        	}
    	        } else {
    	        	siteId = appUser.getSite().getBusEntity().getBusEntityId();
    	        }
	 	        searchCriteria.setSiteId(String.valueOf(siteId));
	        } else if (ordersSearchInfo.getLocationSelected().trim().startsWith(Constants.ORDERS_SPECIFIED_LOCATIONS)) {
                IdVector siteIds = new IdVector();
                int[] siteId = ordersSearchInfo.getSiteId();
                for (int i = 0; siteId != null && i < siteId.length; i++) {
                    siteIds.add(siteId[i]);
                }
                searchCriteria.setSiteIdVector(siteIds);
	        }
	        
	        //Order Status
	        @SuppressWarnings("rawtypes")
			List statusList = getOrderStatusList(ordersSearchInfo.getOrderStatus());
	        searchCriteria.getOrderStatusList().addAll(statusList);
	      
	        // Date Range
	        Date beginDate = null;
	        Date endDate = null;
	        
	        if(ordersSearchInfo.getDateRange().trim().equals(Constants.DATE_RANGE_LAST_PERIOD)) {
	    		int currentPeriod = currentLocation.getCurrentBudgetPeriod();
	    		if (currentPeriod > 1) {
	    			int lastPeriod = currentPeriod - 1;
		    		FiscalCalendarInfo.BudgetPeriodInfo budgetPeriod = (BudgetPeriodInfo) currentLocation.getBudgetPeriods().get(new Integer(lastPeriod));
		    		beginDate = budgetPeriod.getStartDate();
		    		endDate = budgetPeriod.getEndDate();
	    		}
	    		//if we're in the first period of the current fiscal year, we need to get the last period
	    		//of the previous fiscal year.
	    		else {
	    			try {
	    				Account accountBean = APIAccess.getAPIAccess().getAccountAPI();
						FiscalCalenderData currentFiscalCalendar = accountBean.getCurrentFiscalCalender(currentLocation.getAccountId());
						int currentFiscalYear = currentFiscalCalendar.getFiscalYear();
						int previousFiscalYear = currentFiscalYear - 1;
						FiscalCalenderView fcv = accountBean.getFiscalCalenderVForYear(currentLocation.getAccountId(), previousFiscalYear);
						//if there is no fiscal calendar for the previous year, return an error
						if (fcv == null) {
		    	            String errorMess = ClwI18nUtil.getMessage(request, "previousOrder.search.error.noLastPeriod", null);
		    	            errors.add("error", new ActionError("error.simpleGenericError", errorMess));
		    	            return errors;						
						}
						FiscalCalendarInfo fci = new FiscalCalendarInfo(fcv);
						FiscalCalendarInfo.BudgetPeriodInfo budgetPeriod = fci.getBudgetPeriod(fci.getNumberOfBudgetPeriods());
			    		beginDate = budgetPeriod.getStartDate();
			    		endDate = budgetPeriod.getEndDate();
	    			}
	    			catch (Exception e) {
	    	        	log.error("Exception occurred when searching for previous orders...");
	    	            String errorMess = ClwI18nUtil.getMessage(request, "previousOrder.search.error.problemRetrievingOrders", null);
	    	            errors.add("error", new ActionError("error.simpleGenericError", errorMess));
	    	            return errors;
	    			}
	    		}
	        }
	        else if(ordersSearchInfo.getDateRange().trim().equals(Constants.DATE_RANGE_CURRENT_PERIOD)) {
	    		int currentPeriod = currentLocation.getCurrentBudgetPeriod();
	    		FiscalCalendarInfo.BudgetPeriodInfo budgetPeriod = (BudgetPeriodInfo) currentLocation.getBudgetPeriods().get(new Integer(currentPeriod));
	    		beginDate = budgetPeriod.getStartDate();
	    		endDate = budgetPeriod.getEndDate();
	        }
	        else if (ordersSearchInfo.getDateRange().trim().equals(Constants.DATE_RANGE_CUSTOM_RANGE)) {
	        	String userLocaleDateFormat = ClwI18nUtil.getDatePattern(request);
	        	
	        	//From date
	        	String fromDate = "";
	        	if(Utility.isSet(ordersSearchInfo.getFrom()) && !userLocaleDateFormat.equals(ordersSearchInfo.getFrom().trim())) {
	        		fromDate = ordersSearchInfo.getFrom();
	        	} else {
	        		fromDate = Constants.DEFAULT_BEGIN_DATE;
	        	}
	        	beginDate = ClwI18nUtil.parseDateInp(request, fromDate);
	        	
	        	//To date
	        	String toDate = "";
	        	if(Utility.isSet(ordersSearchInfo.getTo()) && !userLocaleDateFormat.equals(ordersSearchInfo.getTo().trim())) {
	        		toDate = ordersSearchInfo.getTo();
	        	} else {
	        		toDate = ClwI18nUtil.formatDateInp(request, new Date());
        			ordersSearchInfo.setTo(toDate);
	        	}
	        	endDate = ClwI18nUtil.parseDateInp(request, toDate);		        	
	        }
	        else {
	        	String mobileClient = (String)request.getSession(false).getAttribute(Constants.MOBILE_CLIENT);
	            if(Utility.isTrue(mobileClient)){
	        		endDate = new Date();
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(endDate);
					calendar.add(Calendar.DATE, -30);
					beginDate = new Date(calendar.getTime().getTime());

					ordersSearchInfo.setFrom(ClwI18nUtil.formatDateInp(request,beginDate));
					ordersSearchInfo.setTo(ClwI18nUtil.formatDateInp(request,endDate));

	            } else {
		        	endDate = new Date();
		    		Calendar calendar = Calendar.getInstance();
		    		calendar.setTime(endDate);
		        	if (ordersSearchInfo.getDateRange().trim().equals(Constants.DATE_RANGE_30_DAYS)) {
		        		calendar.add(Calendar.DATE, -30);
			        }
			        else if (ordersSearchInfo.getDateRange().trim().equals(Constants.DATE_RANGE_60_DAYS)) {
			        	calendar.add(Calendar.DATE, -60);
			        }
			        else if (ordersSearchInfo.getDateRange().trim().equals(Constants.DATE_RANGE_90_DAYS)) {
			        	calendar.add(Calendar.DATE, -90);
			        }
		        	beginDate = new Date(calendar.getTime().getTime());
	            }
	        }
	        
	        //Convert date format to DB(MM/dd/yyyy) date format
	        SimpleDateFormat sdf = new SimpleDateFormat(Constants.SIMPLE_DATE_PATTERN);
	        searchCriteria.setOrderDateRangeBegin(sdf.format(beginDate));
	        searchCriteria.setOrderDateRangeEnd(sdf.format(endDate));
	        
	        //Order Number
	        if(Utility.isSet(ordersSearchInfo.getOrderNumber())) {
	        	searchCriteria.setWebOrderConfirmationNum(ordersSearchInfo.getOrderNumber().trim());
	        }
	        
	        //PO Number
	        if(Utility.isSet(ordersSearchInfo.getPurchaseOrderNumber())) {
	        	searchCriteria.setCustPONum(ordersSearchInfo.getPurchaseOrderNumber().trim());
	        	if (Utility.isEqual(ordersSearchInfo.getOrderStatus(), Constants.ORDER_STATUS_ALL))
	        		searchCriteria.setIncludeRelatedOrder(true);
	        }
	        
	        //Orders Not Received
	        if(ordersSearchInfo.isOrdersNotReceived()) {
	        	searchCriteria.setOrdersNotReceivedOnly();
	        }
	        
	        IdVector storeIds = appUser.getUserStoreAsIdVector();
	        //Get All orders Data.
	        Order orderBean = APIAccess.getAPIAccess().getOrderAPI();
	        
	       // OrderStatusDescDataVector orderStatusList = orderEjb.getLightOrderStatusDescCollection(searchCriteria);
	        OrderStatusDescDataVector orderStatusList = orderBean.getOrderStatusDescCollection(searchCriteria,storeIds);
	        
	        //If an order has 'ERP Released' status, then it comes under either 'Ordered' category 
        	//or 'Shipment Received' category. So, Filter the orders based on the selected status .
	        if(RefCodeNames.ORDER_STATUS_CD.ORDERED.equals(ordersSearchInfo.getOrderStatus()) || 
	        		RefCodeNames.ORDER_STATUS_CD.SHIPMENT_RECEIVED.equals(ordersSearchInfo.getOrderStatus())) {
	        	orderStatusList = filterOrderStatuses(orderStatusList,ordersSearchInfo.getOrderStatus());
	        }
	        
	        
	        //STJ-5421 STJ-5699
	        for(int i = 0; i < orderStatusList.size(); i++) {
         		OrderStatusDescData orderStatusDescData = (OrderStatusDescData)orderStatusList.get(i);
         		if(orderStatusDescData.getOrderDetail().getRevisedOrderDate() != null) {
 	        		orderStatusDescData.getOrderDetail().setOriginalOrderDate(orderStatusDescData.getOrderDetail().getRevisedOrderDate());
 	        		orderStatusDescData.getOrderDetail().setOriginalOrderTime(orderStatusDescData.getOrderDetail().getRevisedOrderTime());
         		}
         	}
	        
	        //STJ-5261 New UI - Mobile All Orders. 
	        String mobileClient = (String)request.getSession(false).getAttribute(Constants.MOBILE_CLIENT);
	        
	        //Call sort method
	        if(Utility.isTrue(mobileClient)) {// STJ-5486-sort orders by revised dates with the most recent order displayed first.
	    		//STJ-5421
	        	sortAllOrdersResults(request, orderStatusList, Constants.ORDERS_SORT_FIELD_ORDER_DATE,Constants.ORDERS_SORT_ORDER_DESCENDING);
	        	
	        } else { 
	    		//STJ-5421
	        	sortAllOrdersResults(request, orderStatusList, ordersSearchInfo.getSortField(),ordersSearchInfo.getSortOrder());
	        }
	        
	        if(Utility.isTrue(mobileClient)){
	        	Site siteEjb = APIAccess.getAPIAccess().getSiteAPI();
	        	SessionDataUtil session = Utility.getSessionDataUtil(request);
	        	session.setSelectedMobileMainTab(Constants.TAB_ORDERS);
	    		Iterator<OrderStatusDescData> orderIterator = orderStatusList.iterator();
	    		CleanwiseUser user = ShopTool.getCurrentUser(request);
	    		
	    		Map<Integer,UserData> userIdToApprovingUserMap = new HashMap<Integer,UserData>();
	    		while (orderIterator.hasNext()) {
	    			OrderStatusDescData order = orderIterator.next();
	    			/*Get only the notes from the vector for the selected order
	    	        getOrderPropertyVec takes care of making sure we only get notes,
	    	        and only the notes for the proper order
	    	        */
	    			
	    			IdVector userApprovableReasonCodeIds = orderBean.getPropertiesUserCanApprove(user.getUser(), 
    	            		order.getOrderDetail().getOrderId());
        	        order.setUserApprovableReasonCodeIds(userApprovableReasonCodeIds);
	    			
	    			
	    	        OrderPropertyDataVector orderPropertyDetailVec =
	    	        		orderBean.getOrderPropertyVec(order.getOrderDetail().getOrderId());
	    	        order.setOrderPropertyList(orderPropertyDetailVec);
	    			
	    			
	    			if (Utility.isSet(order.getOrderPropertyList())) {
						Iterator<OrderPropertyData> propertyIterator = order.getOrderPropertyList().iterator();
						while (propertyIterator.hasNext()) {
							OrderPropertyData property = propertyIterator.next();
							if (property.getApproveDate() != null) {
								String approvingUserName = StringUtils.EMPTY;
								int userIdI = property.getApproveUserId();
								if (userIdI > 0) {
									UserData approvingUser = userIdToApprovingUserMap.get(userIdI);
									if (approvingUser == null) {
										try {
											approvingUser = APIAccess.getAPIAccess().getUserAPI().getUser(property.getApproveUserId());
											if (approvingUser != null) {
												userIdToApprovingUserMap.put(userIdI, approvingUser);
											}
										}
										catch (Exception e) {
											//nothing to do here - a blank value will be used.
										}
									}
									if (approvingUser != null) {
										approvingUserName = approvingUser.getUserName();
									}
								}
								property.setModBy(approvingUserName);
							}
						}
						DashboardLogic.sortProperties(order.getOrderPropertyList());
					}
	    			
	    			//STJ-5261 site data is needed for mobile All-Orders for displaying unspent budget for 
	    			//orders in pending approval status.
	    			if(order.getOrderDetail().getOrderStatusCd().equals(RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL)) {
		    			SiteData siteData = orderBean.getSiteData(order.getOrderDetail());
		    			siteEjb.updateBudgetSpendingInfo(siteData);
		    			order.setOrderSiteData(siteData);
	    			}
	    		}
    		}
	        
	        ordersSearchInfo.setMatchingOrders(orderStatusList);
	        
		}catch (Exception e){
			log.error("Unable to retrieve Orders for the logged in user.");
        	String errorMess = ClwI18nUtil.getMessage(request, "orders.search.problemRetrievingOrders", null);
        	errors.add("error", new ActionError("error.simpleGenericError", errorMess));    	
		}
        
		return errors;
	}

	public static ActionErrors performOrderSearchByOrderNum(
	        HttpServletRequest request, String pOrderNum, OrderSearchDto ordersSearchInfo) {

		ActionErrors errors = new ActionErrors();

		//errors = validateOrdersCriteria(request,ordersSearchInfo, false);
		if (!Utility.isSet(pOrderNum)) {
        	log.error("Missing orders number search criteria encountered.");
            String errorMess = ClwI18nUtil.getMessage(request, "orders.search.noOrderNumber", null);
            errors.add("error", new ActionError("error.simpleGenericError", errorMess));
            return errors;
        }

		try {
	        CleanwiseUser appUser = ShopTool.getCurrentUser(request);
	        SiteData currentLocation = appUser.getSite();

	        //Build the Search criteria
	        OrderStatusCriteriaData searchCriteria = OrderStatusCriteriaData.createValue();
	        searchCriteria.getExcludeOrderStatusList().add(RefCodeNames.ORDER_STATUS_CD.REFERENCE_ONLY);
	        searchCriteria.setIncludeRelatedOrder(true);

	        // Search order must have userId or/and userTypeCd
	        String userId = String.valueOf(appUser.getUser().getUserId());
	        String userType = Utility.strNN(appUser.getUser().getUserTypeCd());

	        searchCriteria.setUserId(userId);
	        searchCriteria.setUserTypeCd(userType);

	        //Location Data
	        if(ordersSearchInfo.getLocationSelected().trim().startsWith(Constants.ORDERS_ALL_LOCATIONS)) {
	        	//Do not set SiteId in search criteria for all sites configured to the user
		        searchCriteria.setSiteId("");
	        } else if (ordersSearchInfo.getLocationSelected().trim().startsWith(Constants.ORDERS_CURRENT_LOCATION)) {//STJ-5261
	        	int siteId = 0;
	        	String mobileClient = (String)request.getSession(false).getAttribute(Constants.MOBILE_CLIENT);
    	        if(Utility.isTrue(mobileClient)) {
    	        	SessionDataUtil sessionData = Utility.getSessionDataUtil(request);
    	        	if(sessionData.getSpecifiedLocation() != null) {
    	        		siteId = sessionData.getSpecifiedLocation().getSiteId();
    	        	}
    	        	else {
    	        		if(appUser.getSite() != null) {
    	        			siteId = appUser.getSite().getBusEntity().getBusEntityId();
    	        		} else {
    	        			String errorMess = ClwI18nUtil.getMessage(request, "mobile.order.search.error.noLocationSpecified", null);
		    	            errors.add("error", new ActionError("error.simpleGenericError", errorMess));
		    	            return errors;
    	        		}
    	        	}
    	        } else {
    	        	siteId = appUser.getSite().getBusEntity().getBusEntityId();
    	        }
	 	        searchCriteria.setSiteId(String.valueOf(siteId));
	        } else if (ordersSearchInfo.getLocationSelected().trim().startsWith(Constants.ORDERS_SPECIFIED_LOCATIONS)) {
                IdVector siteIds = new IdVector();
                int[] siteId = ordersSearchInfo.getSiteId();
                for (int i = 0; siteId != null && i < siteId.length; i++) {
                    siteIds.add(siteId[i]);
                }
                searchCriteria.setSiteIdVector(siteIds);
	        }

        	searchCriteria.setWebOrderConfirmationNum(pOrderNum.trim());

	        IdVector storeIds = appUser.getUserStoreAsIdVector();
	        //Get All orders Data.
	        Order orderEjb = APIAccess.getAPIAccess().getOrderAPI();

	        OrderStatusDescDataVector orderStatusList = orderEjb.getOrderStatusDescCollection(searchCriteria,storeIds);

	        //STJ-5261 New UI - Mobile All Orders.
	        String mobileClient = (String)request.getSession(false).getAttribute(Constants.MOBILE_CLIENT);

	        if(Utility.isTrue(mobileClient)){
	        	Site siteEjb = APIAccess.getAPIAccess().getSiteAPI();
	        	SessionDataUtil session = Utility.getSessionDataUtil(request);
	        	session.setSelectedMobileMainTab(Constants.TAB_ORDERS);
	    		Iterator<OrderStatusDescData> orderIterator = orderStatusList.iterator();
	    		while (orderIterator.hasNext()) {
	    			OrderStatusDescData order = orderIterator.next();
	    			/*Get only the notes from the vector for the selected order
	    	        getOrderPropertyVec takes care of making sure we only get notes,
	    	        and only the notes for the proper order
	    	        */
	    	        OrderPropertyDataVector orderPropertyDetailVec =
	    	        	orderEjb.getOrderPropertyVec(order.getOrderDetail().getOrderId());
	    	        IdVector userApprovableReasonCodeIds = new IdVector();
	    	        if(orderPropertyDetailVec.size() > 0)
	    	        	userApprovableReasonCodeIds.add(((OrderPropertyData)orderPropertyDetailVec.get(0)).getApproveUserId());
	    			order.setUserApprovableReasonCodeIds(userApprovableReasonCodeIds);
	    			order.setOrderPropertyList(orderPropertyDetailVec);

	    			//STJ-5261 site data is needed for mobile All-Orders for displaying unspent budget for
	    			//orders in pending approval status.
	    			if(order.getOrderDetail().getOrderStatusCd().equals(RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL)) {
		    			SiteData siteData = orderEjb.getSiteData(order.getOrderDetail());
		    			siteEjb.updateBudgetSpendingInfo(siteData);
		    			order.setOrderSiteData(siteData);
	    			}
	    		}
    		}

	        ordersSearchInfo.setMatchingOrders(orderStatusList);

		}catch (Exception e){
			log.error("Unable to retrieve Orders for the logged in user.");
        	String errorMess = ClwI18nUtil.getMessage(request, "orders.search.problemRetrievingOrders", null);
        	errors.add("error", new ActionError("error.simpleGenericError", errorMess));
		}

		return errors;
	}



	/**
	 * Returns Sorted OrderStatusDescDataVector, sorting would be performed based on specified sort field and order.
	 * @param orderStatusList - List that needs to be sorted.
	 * @param sortByField - Sorting field.
	 * @param sortByOrder - Sorting Order.
	 */
	public static void sortAllOrdersResults(HttpServletRequest request, OrderStatusDescDataVector orderStatusList,String sortByField, String sortByOrder) {
		//STJ-5421
		DisplayListSort.sort(request, orderStatusList, sortByField,sortByOrder);
	}

	
	/**
	 * Initializes OrdersCritia with default values if they are not set.
	 * @param request
	 * @param ordersSearchDto
	 */
	public static void initOrdersCriteria(HttpServletRequest request, OrderSearchDto ordersSearchDto) {
		//STJ-5261
		String mobileClient = (String)request.getSession(false).getAttribute(Constants.MOBILE_CLIENT);
        if(mobileClient != null && mobileClient.equals("true")){
        	SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
        	CleanwiseUser user = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
			SiteData location = user.getSite();
			if(Constants.ORDERS_CURRENT_LOCATION.equals(sessionDataUtil.getMobileLocationSelected())) {
				ordersSearchDto.setLocationSelected(sessionDataUtil.getMobileLocationSelected());	
			}
			
			if(!Utility.isSet(ordersSearchDto.getLocationSelected())) {
				if(location == null) {
					ordersSearchDto.setLocationSelected(Constants.ORDERS_ALL_LOCATIONS);
				}
				else {
					ordersSearchDto.setLocationSelected(Constants.ORDERS_CURRENT_LOCATION);
					sessionDataUtil.setSpecifiedLocation(location);
				}
			}
        }
        else {
			if(!Utility.isSet(ordersSearchDto.getLocationSelected())) {
				ordersSearchDto.setLocationSelected(Constants.ORDERS_ALL_LOCATIONS);
			}	
        }
		
		if(!Utility.isSet(ordersSearchDto.getLocations())) {
			SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
			//STJ-5677:
	        //LocationSearchDto locationSearchDto = sessionDataUtil.getLocationSearchDto();
			LocationSearchDto locationSearchDto = sessionDataUtil.getLocationSearchDtoMap().get(Constants.SPECIFY_LOCATIONS_ALL_ORDERS);
	        Object[] params = new Object[1];
 	        params[0] = 0;
	        if(locationSearchDto==null) {
	        	ordersSearchDto.setLocationSelected(Constants.ORDERS_CURRENT_LOCATION);
	        } else {
	 	        params[0] = locationSearchDto.getMatchingLocations().size();
	        }
	        ordersSearchDto.setLocations(ClwI18nUtil.getMessage(request, "orders.filterPane.label.allLocations", params));
		}
		
    	//Initialize the sort field if it is not specified
    	if (!Utility.isSet(ordersSearchDto.getSortField())) {
    		ordersSearchDto.setSortField(Constants.ORDERS_SORT_FIELD_ORDER_NUMBER);
    	}
    	
    	//Initialize the sort order if it is not specified
    	if (!Utility.isSet(ordersSearchDto.getSortOrder())) {
    		ordersSearchDto.setSortOrder(Constants.ORDERS_SORT_ORDER_ASCENDING);
    	}
    	
    	//Default value for Order Status
        if(!Utility.isSet(ordersSearchDto.getOrderStatus())) {
        	ordersSearchDto.setOrderStatus(Constants.ORDER_STATUS_ALL);
        }
      //STJ-5261
        if(mobileClient != null && mobileClient.equals("true")){
	    	if(Constants.ORDERS_DATE_RANGE_FORMAT.equals(ordersSearchDto.getFrom()) && 
	    			Constants.ORDERS_DATE_RANGE_FORMAT.equals(ordersSearchDto.getTo())) {
	    		ordersSearchDto.setDateRange(Constants.DATE_RANGE_30_DAYS);
	    		String userLocaleDateFormat = ClwI18nUtil.getUIDateFormat(request);
	        	if(Utility.isSet(userLocaleDateFormat)) {
	        		userLocaleDateFormat = userLocaleDateFormat.toLowerCase();
	        	}
	        	ordersSearchDto.setFrom(userLocaleDateFormat);
	        	ordersSearchDto.setTo(userLocaleDateFormat);
	    		
	    	} else {
	    		ordersSearchDto.setDateRange(Constants.DATE_RANGE_CUSTOM_RANGE);
	    	}
	    }else {
	       //Default value for Order Date 
	        if(!Utility.isSet(ordersSearchDto.getDateRange())) {
	        	ordersSearchDto.setDateRange(Constants.DATE_RANGE_30_DAYS);
	        }
	        
	        //If Custom Date range is not set then Initialize From & To date values with 
	        //Logged in User's locale date format.
	        if(!ordersSearchDto.getDateRange().trim().equals(Constants.DATE_RANGE_CUSTOM_RANGE)) {
	        	String userLocaleDateFormat = ClwI18nUtil.getUIDateFormat(request);
	        	if(Utility.isSet(userLocaleDateFormat)) {
	        		userLocaleDateFormat = userLocaleDateFormat.toLowerCase();
	        	}
	        	ordersSearchDto.setFrom(userLocaleDateFormat);
	        	ordersSearchDto.setTo(userLocaleDateFormat);
	        }
        }
	}
	
	/*
     * Private method to validate any order search/sort criteria is valid
     */
    private static ActionErrors validateOrdersCriteria(HttpServletRequest request,
        OrderSearchDto ordersSearchDto) {
        CleanwiseUser user = (CleanwiseUser)request.getSession().getAttribute(Constants.APP_USER);
    	ActionErrors errors = new ActionErrors();
    	if (ordersSearchDto == null) {
        	log.error("Missing orders filter search criteria encountered.");
            String errorMess = ClwI18nUtil.getMessage(request, "orders.search.problemRetrievingOrders", null);
            errors.add("error", new ActionError("error.simpleGenericError", errorMess));    		
    	}
    	else {
    		//make sure the user id is specified and valid
    		if (!Utility.isSet(ordersSearchDto.getUserId()) ||
    			Integer.valueOf(ordersSearchDto.getUserId().trim()) != user.getUserId()) {
            	log.error("Invalid user id search criteria encountered.");
                String errorMess = ClwI18nUtil.getMessage(request, "orders.search.problemRetrievingOrders", null);
                errors.add("error", new ActionError("error.simpleGenericError", errorMess));    		
    		}
    		
    		//If User's location is not set
    		if(!Utility.isSet(ordersSearchDto.getLocations())) {
    			SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
    			//STJ-5677
    	        //LocationSearchDto locationSearchDto = sessionDataUtil.getLocationSearchDto();
    			LocationSearchDto locationSearchDto = sessionDataUtil.getLocationSearchDtoMap().get(Constants.SPECIFY_LOCATIONS_ALL_ORDERS);
    	        if(locationSearchDto==null) {
    	        	errors = SecurityLogic.initializeUserLocationInformation(request);
    	        	if(errors.size()>0) {
    	    			return errors;
    	    		}
    	        	//STJ-5677
    	        	//locationSearchDto = sessionDataUtil.getLocationSearchDto();
    	        	 locationSearchDto = sessionDataUtil.getLocationSearchDtoMap().get(Constants.SPECIFY_LOCATIONS_ALL_ORDERS);
    	        }
    	        
    	        Object[] params = new Object[1];
                params[0] = locationSearchDto.getMatchingLocations().size();
                ordersSearchDto.setLocations(ClwI18nUtil.getMessage(request, "orders.filterPane.label.allLocations", params));
    		}
	        
    		// Validate date fields.
	        if(ordersSearchDto.getDateRange().trim().equals(Constants.DATE_RANGE_CUSTOM_RANGE)) {
	        	String fromDate = ordersSearchDto.getFrom();
	        	String toDate = ordersSearchDto.getTo();
	        	Date from = null;
	        	Date to = null;
	        	
	        	String userLocaleDateFormat = ClwI18nUtil.getDatePattern(request);
	        	if(fromDate==null || fromDate.equals(StringUtils.EMPTY) || fromDate.equalsIgnoreCase(userLocaleDateFormat)) {
	        		ordersSearchDto.setFrom(userLocaleDateFormat);
	        	} else {
	        		try {
						from = ClwI18nUtil.parseDateInp(request, fromDate);
					} catch (ParseException e) {
						log.error("Invalid begin date entered.");
		                String errorMess = ClwI18nUtil.getMessage(request, "orders.search.invalidBeginDate", null);
		                errors.add("error", new ActionError("error.simpleGenericError", errorMess));
					}
	        	}
	        	
	        	if(toDate==null  || toDate.equals(StringUtils.EMPTY) || toDate.equalsIgnoreCase(userLocaleDateFormat)) {
	        		ordersSearchDto.setTo(userLocaleDateFormat);
	        	} else {
	        		try {
						to = ClwI18nUtil.parseDateInp(request, toDate);
					} catch (ParseException e) {
						log.error("Invalid end date entered.");
		                String errorMess = ClwI18nUtil.getMessage(request, "orders.search.invalidEndDate", null);
		                errors.add("error", new ActionError("error.simpleGenericError", errorMess));
					}
	        	}
	        	
	        	if(errors.isEmpty()) {
		        	if(from==null && to==null) {
		        		log.error("Order date range fields are empty");
		        		String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.fieldsOrderDateRangeAreEmpty",null);
		        		errors.add("ordersearch",new ActionError("error.simpleGenericError",errorMess));
		        	}
		        	else if(from!=null && (from.compareTo(new Date())>0)) {
		        		log.error("Invalid date range, future date should not be selected as begin date.");
		                String errorMess = ClwI18nUtil.getMessage(request, "orders.search.invalidBeginDateRange", null);
		                errors.add("error", new ActionError("error.simpleGenericError", errorMess));
		        	} 
		        	else if(to!=null && (to.compareTo(new Date())>0)) {
		        		log.error("Invalid date range, future date should not be selected as end date.");
		                String errorMess = ClwI18nUtil.getMessage(request, "orders.search.invalidEndDateRange", null);
		                errors.add("error", new ActionError("error.simpleGenericError", errorMess));
		        	}
		        	else if(from!=null && to!=null && (from.compareTo(to)>0)) {
		        		log.error("Invalid date range, end date must be before or equals to the beginning date.");
		                String errorMess = ClwI18nUtil.getMessage(request, "orders.search.invalidDateRange", null);
		                errors.add("error", new ActionError("error.simpleGenericError", errorMess));
		        	}
	        	}
	        	
	        }
        	
    	}
    	return errors;
    }



	@SuppressWarnings({"unchecked", "rawtypes" })
	private static List getOrderStatusList(String status) {
        List list = new ArrayList();

        if(RefCodeNames.ORDER_STATUS_CD.ORDERED_PROCESSING.equals(status)) {
    		list.add(RefCodeNames.ORDER_STATUS_CD.RECEIVED);
        	list.add(RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW);
        	list.add(RefCodeNames.ORDER_STATUS_CD.PENDING_ORDER_REVIEW);
        }
        else if (RefCodeNames.ORDER_STATUS_CD.REJECTED.equals(status)){
        	list.add(RefCodeNames.ORDER_STATUS_CD.REJECTED);
        	list.add(RefCodeNames.ORDER_STATUS_CD.ERP_REJECTED);
        }
        else if (RefCodeNames.ORDER_STATUS_CD.CANCELLED.equals(status)) {
            list.add(RefCodeNames.ORDER_STATUS_CD.ERP_CANCELLED);
            list.add(RefCodeNames.ORDER_STATUS_CD.CANCELLED);
        }
        else if(RefCodeNames.ORDER_STATUS_CD.ORDERED.equals(status)){
        	list.add(RefCodeNames.ORDER_STATUS_CD.ERP_RELEASED);
            list.add(RefCodeNames.ORDER_STATUS_CD.ORDERED);
        }
        
        else if(RefCodeNames.ORDER_STATUS_CD.SHIPMENT_RECEIVED.equals(status)) {
        	list.add(RefCodeNames.ORDER_STATUS_CD.SHIPMENT_RECEIVED);
        	list.add(RefCodeNames.ORDER_STATUS_CD.ERP_RELEASED);
        }
        
        else if(Constants.ORDER_STATUS_ALL.equals(status)) {
        	list.add(RefCodeNames.ORDER_STATUS_CD.RECEIVED);
        	list.add(RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW);
        	list.add(RefCodeNames.ORDER_STATUS_CD.PENDING_ORDER_REVIEW);
        	list.add(RefCodeNames.ORDER_STATUS_CD.REJECTED);
        	list.add(RefCodeNames.ORDER_STATUS_CD.ERP_REJECTED);
        	list.add(RefCodeNames.ORDER_STATUS_CD.ERP_RELEASED);
        	list.add(RefCodeNames.ORDER_STATUS_CD.ORDERED);
        	list.add(RefCodeNames.ORDER_STATUS_CD.SHIPMENT_RECEIVED);
        	list.add(RefCodeNames.ORDER_STATUS_CD.ERP_CANCELLED);
            list.add(RefCodeNames.ORDER_STATUS_CD.CANCELLED);
            list.add(RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL);
            list.add(RefCodeNames.ORDER_STATUS_CD.PENDING_DATE);
            list.add(RefCodeNames.ORDER_STATUS_CD.PENDING_CONSOLIDATION);
            list.add(RefCodeNames.ORDER_STATUS_CD.SHIPPED);
            list.add(RefCodeNames.ORDER_STATUS_CD.INVOICED);
            list.add(RefCodeNames.ORDER_STATUS_CD.ON_HOLD);
        }
        else {
        	list.add(status);
        }
        
        return list;
    }
	
	 /*
     * Filters all orders for 'Ordered' status.
     */
	@SuppressWarnings("unchecked")
	private static OrderStatusDescDataVector filterOrderStatuses(OrderStatusDescDataVector orders, String selectedStatus) {
		OrderStatusDescDataVector filteredOrders = new OrderStatusDescDataVector();
		String statusCode = "";
		for(int index = 0; index< orders.size(); index++) {
			OrderStatusDescData data = (OrderStatusDescData) orders.get(index);
        	statusCode = data.getOrderDetail().getOrderStatusCd().trim();
        	//If the flag received is set then that order comes into
        	//'Shipment Received' category otherwise it comes into 'Ordered' Category.
        	if(RefCodeNames.ORDER_STATUS_CD.ERP_RELEASED.equals(statusCode)) {
        		if(RefCodeNames.ORDER_STATUS_CD.ORDERED.equals(selectedStatus) && !data.isReceived()) {
        			filteredOrders.add(data);
        		} else if(RefCodeNames.ORDER_STATUS_CD.SHIPMENT_RECEIVED.equals(selectedStatus) && data.isReceived()) {
        			filteredOrders.add(data);
        		}
        	} else {
        		filteredOrders.add(data);
        	}
		}
		return filteredOrders;
	}

	/**
     * Find the future orders matching a set of criteria.
     * @param   request - the <code>HttpServletRequest</code> currently being handled.
     * @param   futureOrderSearchDto - an <code>OrderSearchDto</code> containing the search criteria.
     * @return  An <code>ActionErrors</code> object containing any errors.
     */
    public static ActionErrors performFutureOrdersSearch(HttpServletRequest request, 
    		OrderSearchDto futureOrderSearchDto, DeliveryScheduleViewVector parOrderSchedules) {
    	ActionErrors ae = new ActionErrors();
    	OrderStatusCriteriaData searchCriteria = OrderStatusCriteriaData.createValue();
    	CleanwiseUser user = (CleanwiseUser)request.getSession().getAttribute(Constants.APP_USER);
    	String localeCd = user.getUser().getPrefLocaleCd();
    	SiteData currentLocation = ShopTool.getCurrentSite(request);
    	
    	//filter by location
    	if(Utility.isSet(futureOrderSearchDto.getLocationId())){
    		searchCriteria.setSiteId(futureOrderSearchDto.getLocationId());
    	}
    	
    	Date beginDate = null;
    	Date endDate = null;
    	//STJ-5757.
    	Date corpOrderBeginDate = null;
    	Date corpOrderEndDate = null;
    	String dateRange = futureOrderSearchDto.getDateRange();
    	if (Constants.FUTURE_ORDER_DATE_RANGE_VALUE_CURRENT_PERIOD.equalsIgnoreCase(dateRange)) {
    		int currentPeriod = currentLocation.getCurrentBudgetPeriod();
    		FiscalCalendarInfo.BudgetPeriodInfo budgetPeriod = (BudgetPeriodInfo) currentLocation.getBudgetPeriods().get(new Integer(currentPeriod));
    		//beginDate = budgetPeriod.getStartDate();
    		beginDate = new Date();
    		endDate = budgetPeriod.getEndDate();
    		
    	}else if (Constants.FUTURE_ORDER_DATE_RANGE_VALUE_NEXT_PERIOD.equalsIgnoreCase(dateRange)) {
    		int currentPeriod = currentLocation.getCurrentBudgetPeriod();
    		int periodCount = currentLocation.getBudgetPeriods().size();
    		if (currentPeriod < periodCount  ) {
    			int nextPeriod = currentPeriod + 1;
	    		FiscalCalendarInfo.BudgetPeriodInfo budgetPeriod = (BudgetPeriodInfo) currentLocation.getBudgetPeriods().get(new Integer(nextPeriod));
	    		beginDate = budgetPeriod.getStartDate();
	    		endDate = budgetPeriod.getEndDate();
            } else {
	    			try {
	    				Account accountBean = APIAccess.getAPIAccess().getAccountAPI();
						FiscalCalenderData currentFiscalCalendar = accountBean.getCurrentFiscalCalender(currentLocation.getAccountId());
						int currentFiscalYear = currentFiscalCalendar.getFiscalYear();
						int previousFiscalYear = currentFiscalYear + 1;
						FiscalCalenderView fcv = accountBean.getFiscalCalenderVForYear(currentLocation.getAccountId(), previousFiscalYear);
						//if there is no fiscal calendar for the next year, return an error
						if (fcv == null) {
		    	            String errorMess = ClwI18nUtil.getMessage(request, "futureOrder.search.error.noFiscalCalendarFound", null);
		    	            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
		    	            return ae;
						}
						FiscalCalendarInfo fci = new FiscalCalendarInfo(fcv);
						FiscalCalendarInfo.BudgetPeriodInfo budgetPeriod = fci.getBudgetPeriod(fci.getNumberOfBudgetPeriods());
						if ( budgetPeriod == null) {
		    	            String errorMess = ClwI18nUtil.getMessage(request, "futureOrder.search.error.noFiscalCalendarFound", null);
		    	            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
		    	            return ae;
                        }
			    		beginDate = budgetPeriod.getStartDate();
			    		endDate = budgetPeriod.getEndDate();
	    			}
	    			catch (Exception e) {
	    	        	log.error("Exception occurred when getting next budget period...");
	    	            String errorMess = ClwI18nUtil.getMessage(request, "futureOrder.search.error.problemRetrievingOrders", null);
	    	            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
	    	            return ae;
	    			}
            }
    		
    	} else {
    		beginDate = new Date();
    		Calendar calendar = Calendar.getInstance();
    		calendar.setTime(beginDate);
    		if (Constants.FUTURE_ORDER_DATE_RANGE_VALUE_NEXT_THIRTY_DAYS.equalsIgnoreCase(dateRange)) {
    			calendar.add(Calendar.DATE, +30);
    		}
    		else if (Constants.FUTURE_ORDER_DATE_RANGE_VALUE_NEXT_SIXTY_DAYS.equalsIgnoreCase(dateRange)) {
    			calendar.add(Calendar.DATE, +60);
    		}
    		else if (Constants.FUTURE_ORDER_DATE_RANGE_VALUE_NEXT_NINETY_DAYS.equalsIgnoreCase(dateRange)) {
    			calendar.add(Calendar.DATE, +90);
    		}
    		endDate = new Date(calendar.getTime().getTime());
    	}
    	//STJ-5757.
    	 SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
    	 try{
    		 corpOrderBeginDate = sdf1.parse(sdf1.format(beginDate));
    		 corpOrderEndDate = sdf1.parse(sdf1.format(endDate));
    	 } catch(Exception e) {
    		 
    	 }
    	
    	SimpleDateFormat sdf = new SimpleDateFormat(ClwI18nUtil.getDatePattern(request));
    	futureOrderSearchDto.setFrom(sdf.format(beginDate));
    	futureOrderSearchDto.setTo(sdf.format(endDate));
    	
    	
    	try {
	        
	        Collection<OrderInfoBase> futureOrders = new ArrayList<OrderInfoBase>();

	        //Pending date orders
	        
	        Collection<OrderInfoBase> pendingDateOrders = getPendingDateOrders(request, searchCriteria, 
	        		localeCd, beginDate, endDate);
	        if(pendingDateOrders!=null && pendingDateOrders.size()>0){  	
	        	futureOrders.addAll(pendingDateOrders);
	        }
	        
	        
	        //Par orders (scheduled orders)
	        int pAcctId = currentLocation.getAccountId();
	      
	        HttpSession session = request.getSession();
	        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
	        PropertyService propEjb = factory.getPropertyServiceAPI();
	    		
	        String invShopping = propEjb.getBusEntityProperty(pAcctId, RefCodeNames.PROPERTY_TYPE_CD.SHOW_SCHED_DELIVERY);
	    		
	        if(Utility.isSet(invShopping) && invShopping.equals("true")){
	        	if(currentLocation!=null && currentLocation.hasInventoryShoppingOn()){
	        		//STJ-5757.
	        		//Collection parOrders = getParOrders(request, localeCd, currentLocation, beginDate, endDate, parOrderSchedules);
	        		Collection corporateOrders = getCorporateOrders(request, localeCd, currentLocation, corpOrderBeginDate, corpOrderEndDate, parOrderSchedules);
	    	        
	        		if(corporateOrders != null && corporateOrders.size()>0){
	        			futureOrders.addAll(corporateOrders);
	        		}
 	       	}
	        }	       
	        
	        //shopping list schedule orders
	        
	        Collection<OrderInfoBase> shoppingScheduleOrders = getShoppingScheduleOrders(request, localeCd, currentLocation,
	        		beginDate, endDate);
	        if(shoppingScheduleOrders!=null && shoppingScheduleOrders.size()>0){
	        	futureOrders.addAll(shoppingScheduleOrders);
	        }

	        HashMap hMap = new HashMap();
	    	Iterator it = futureOrders.iterator();
	    	while(it.hasNext()){
	    		FutureOrderDto order = (FutureOrderDto)it.next();
	    		String key = order.getReleaseDate()+order.getOrderType()+order.getContentId();
	    		hMap.put(key, order);
	    	}
	    	TreeMap foMap = new TreeMap(hMap);
	    	
	    	futureOrders = foMap.values();
	        futureOrderSearchDto.setMatchingOrders(futureOrders);
	        
    	}catch (Exception exc) {
        	exc.printStackTrace();
            String errorMess = ClwI18nUtil.getMessage(request, "futureOrder.search.error.problemRetrievingOrders", null);
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
    	}
    	
		return ae;
    }
    
    public static Collection<OrderInfoBase> getShoppingScheduleOrders(HttpServletRequest request,String pLocaleCd, 
    		SiteData pSite, Date pBegDate, Date pEndDate) throws Exception{
    	Collection<OrderInfoBase> schedulesList = new ArrayList<OrderInfoBase>();
    	FutureOrderDto thisOrder = null;
    	try{
    		HttpSession session = request.getSession();
    		APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    		CleanwiseUser appUser = ShopTool.getCurrentUser(request);
    		
    		AutoOrder autoOrderEjb = factory.getAutoOrderAPI();
	        OrderGuide ogEjb = factory.getOrderGuideAPI();
	        PropertyService propEjb = factory.getPropertyServiceAPI();
	        
	        String isShared = null;
	        try{ 
	        	isShared = propEjb.getBusEntityProperty(pSite.getSiteId(), RefCodeNames.PROPERTY_TYPE_CD.SHARE_ORDER_GUIDES);
	        }catch(DataNotFoundException exc){
	        	log.info(exc.getMessage()+" "+RefCodeNames.PROPERTY_TYPE_CD.SHARE_ORDER_GUIDES);
	        }
	        
	        SimpleDateFormat sdf = new SimpleDateFormat(ClwI18nUtil.getDatePattern(request));
	        
    		ArrayList<OrderScheduleJoin> orderSchedulesList = autoOrderEjb.getOrderSchedulesList(
	        		pSite.getSiteId(), pBegDate, pEndDate, RefCodeNames.ORDER_SCHEDULE_CD.PLACE_ORDER);
	       
	        Iterator it = orderSchedulesList.iterator();
	        while(it.hasNext()){
	        	OrderScheduleJoin orderSchedule = (OrderScheduleJoin)it.next();
	        	OrderGuideInfoData oguide = ogEjb.getOrderGuideInfoWithEstimatedTotal(
	        			orderSchedule.getOrderGuideId(),pSite.getContractData().getContractId());
	        	
	        	boolean userSharedList = false;
	        	if(appUser.getUserId()==oguide.getOrderGuideData().getUserId() || 
	        			(Utility.isSet(isShared) && isShared.equalsIgnoreCase("true"))){
	        		userSharedList = true;
	        	}
	        	
	        	Date effDate = orderSchedule.getEffDate();
	        	Date expDate = orderSchedule.getExpDate();
	        	
	        	List<String> dates = getOrderScheduleDatesInDateRange(request, orderSchedule, pBegDate, pEndDate);

	        	for(int i=0; i<dates.size(); i++){
	        		thisOrder = new FutureOrderDto();
	        		thisOrder.setUserSharedList(userSharedList);
	        		thisOrder.setReleaseDate(dates.get(i));
	        		thisOrder.setOrderType(RefCodeNames.FUTURE_ORDER_TYPE.SHOPPING_LIST_SCHEDULE);
	        		thisOrder.setSchedule(orderSchedule.getOrderGuideName());
	        		thisOrder.setScheduleBeginDate(sdf.format(effDate));
	        		thisOrder.setScheduleEndDate(sdf.format(expDate));
	        		thisOrder.setOrderContent(oguide.getOrderGuideData().getOrderGuideId()+"-"+orderSchedule.getOrderGuideName());
	        		thisOrder.setContentId(orderSchedule.getOrderScheduleId());
			        
	        		String total = oguide.getEstimatedTotal().toString();
	        		
	        		thisOrder.setOrderTotal(total);
			        	
	        		schedulesList.add(thisOrder);
	        		
	        	}
	        }	
    	}catch (Exception exc) {
        	exc.printStackTrace();
            throw exc;
    	}
    	return schedulesList;
    }
    
    public static Collection<OrderInfoBase> getParOrders(HttpServletRequest request,String pLocaleCd, SiteData pSite,
    		Date pBegDate, Date pEndDate, DeliveryScheduleViewVector parOrderSchedules) throws Exception{
    	
    	DateFormat df = DateFormat.getDateInstance();
    	SimpleDateFormat sdf = new SimpleDateFormat(ClwI18nUtil.getDatePattern(request));
    	
    	Collection<OrderInfoBase> parOrdersList = new ArrayList<OrderInfoBase>();
    	FutureOrderDto thisOrder = null;
    	try{
    		HttpSession session = request.getSession();
    		APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    		
    		Site siteEjb = factory.getSiteAPI();
    		ShoppingCartData inventoryCart =(ShoppingCartData) session.getAttribute(Constants.INVENTORY_SHOPPING_CART);
    		String inventoryCartTotal = null;
    		
    		if(inventoryCart!=null){
    			
    			inventoryCartTotal = new Double(inventoryCart.getItemsCost()).toString();
    		}
    		PropertyData cutOffDateProp = pSite.getMiscProp(RefCodeNames.PROPERTY_TYPE_CD.NEXT_ORDER_CUTOFF_DATE);
    		PropertyData cutOffTimeProp = pSite.getMiscProp(RefCodeNames.PROPERTY_TYPE_CD.NEXT_ORDER_CUTOFF_TIME);
    		
    		//next order cut-off date
    		Date nextCutOffDate = null;
    		
    		if(cutOffDateProp!=null){   		
    			nextCutOffDate = df.parse(cutOffDateProp.getValue());      	
    		}
    		
    		//next order cut-off time
    		String nextCutOffTime = null;
    		
    		if(cutOffTimeProp!=null){   		
    			nextCutOffTime = cutOffTimeProp.getValue();  	
    		}
    		ArrayList siteSchedules = null;
    		try{
    			siteSchedules = siteEjb.getOrderScheduleDatesForMainDistributor(pSite.getSiteId(), pSite.getAccountId(), pBegDate, pEndDate);
    		}catch(RemoteException exc){
    			//exc.printStackTrace();
    			log.info(exc.getMessage());
    		}
    		
    		if(siteSchedules != null && siteSchedules.size()>0){
    			
    			Object thisSched = siteSchedules.get(0);

                if(thisSched instanceof String) {

                  //throw new Exception ((String)oo);
                	log.info((String)thisSched);

                }else{
    		
		    		String intervalStr = pSite.getInventoryCartAccessInterval();
		    		GregorianCalendar cal = null;
		    		String scheduleBeginDateS = null;
		        	String scheduleEndDateS = null;   
		        	
		    		Iterator it = siteSchedules.iterator();
		    		while(it.hasNext()){
		    			
		    			ScheduleOrderDates sods = (ScheduleOrderDates)it.next();
		    			
		    			thisOrder = new FutureOrderDto();
		    			Date openDate = null;
		    			Date cutOffDate = sods.getNextOrderCutoffDate();
		    			
		    			if(cutOffDate.after(pEndDate)){
		    				continue;
		    			}
		    			
		    			String cutOffDateS = sdf.format(cutOffDate);
		    			
		    			if(cutOffDate.equals(nextCutOffDate)){ //check for immediate next cut off
		    				thisOrder.setOrderTotal(inventoryCartTotal);
		    			}
		    			
		    			if(Utility.isSet(intervalStr)){
		    				int accessInterval = Integer.parseInt(intervalStr);
		    				cal = new GregorianCalendar();
		    				cal.setTime(cutOffDate);
		    				boolean allowCorpSchedOrder = pSite.isAllowCorpSchedOrder();
		                    if (allowCorpSchedOrder)
		                    	cal.add(Calendar.HOUR, (-1) * accessInterval);
		                    else
		                    	cal.add(Calendar.DATE, (-1) * accessInterval);
		    				openDate = cal.getTime();
		    				
		    				scheduleBeginDateS = sdf.format(openDate)+" "+Constants.PAR_ORDER_OPEN_TIME;
		
		        			scheduleEndDateS = cutOffDateS+" "+nextCutOffTime;
		        			
		        			//show begin/end date only when interval days is set
		    	        	thisOrder.setScheduleBeginDate(scheduleBeginDateS);
		    	        	thisOrder.setScheduleEndDate(scheduleEndDateS); 
		    			}
		
			        	thisOrder.setReleaseDate(cutOffDateS);
		
			        	thisOrder.setOrderType(RefCodeNames.FUTURE_ORDER_TYPE.CORPORATE_ORDER_SCHEDULE);
			        	thisOrder.setSchedule(RefCodeNames.FUTURE_ORDER_TYPE.CORPORATE_ORDER_SCHEDULE);
			        	
			        	thisOrder.setOrderContent(RefCodeNames.FUTURE_ORDER_CONTENT_TYPE.CORPORATE_ORDER);
			        	
			        	//STJ-4479 - set the PAR order schedule id.  Note - assumption is that there
			        	//is only one PAR order schedule per site (per functional spec)
			        	if (Utility.isSet(parOrderSchedules) && parOrderSchedules.size() == 1) {
				        	DeliveryScheduleView parOrderSchedule = (DeliveryScheduleView) parOrderSchedules.get(0);
			        		thisOrder.setContentId(parOrderSchedule.getScheduleId());
			        	}
			        	else {
			        		throw new DataNotFoundException("Unable to determine id of the PAR order schedule");
			        	}
			        	        	
			        	parOrdersList.add(thisOrder);
		    			
		    		}
                }
    		}
        	
    	}catch (Exception exc) {
        	exc.printStackTrace();
            throw exc;
    	}
    	return parOrdersList;
    }
    
	public static Collection<OrderInfoBase> getPendingDateOrders(HttpServletRequest request, OrderStatusCriteriaData pSearchCrit, 
			String pLocaleCd, Date pBegDate, Date pEndDate) throws Exception{
		
		Collection<OrderInfoBase> pendingDateOrdersList = new ArrayList<OrderInfoBase>();
		FutureOrderDto thisOrder = null;
		
		try{
			SimpleDateFormat sdf = new SimpleDateFormat(ClwI18nUtil.getDatePattern(request));
			HttpSession session = request.getSession();
			APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        
			Order orderEjb = factory.getOrderAPI();
        
			pSearchCrit.setOrderStatus(RefCodeNames.ORDER_STATUS_CD.PENDING_DATE);
        
			OrderStatusDescDataVector pendingDateOrders = orderEjb.getOrderStatusDescCollection_OLD(pSearchCrit);
			if(pendingDateOrders != null && pendingDateOrders.size()>0){
        	
				for(int i=0; i<pendingDateOrders.size(); i++){
					OrderStatusDescData statusD = (OrderStatusDescData)pendingDateOrders.get(i);
        		
					String pendingDateS = statusD.getPendingDate();
					Date pendingDate = Utility.convertDBStringToDate(pendingDateS, false);
        			
					if(pendingDate.compareTo(pBegDate)>=0 && pendingDate.compareTo(pEndDate)<=0){
        			
						thisOrder = new FutureOrderDto();
						thisOrder.setOrderContent(statusD.getOrderDetail().getOrderNum());
						thisOrder.setContentId(statusD.getOrderDetail().getOrderId());
						
						String total = statusD.getEstimatedTotal().toString();
						thisOrder.setOrderTotal(total);
    	        	
						thisOrder.setOrderType(RefCodeNames.FUTURE_ORDER_TYPE.PENDING_DATE_ORDER);
						thisOrder.setSchedule(RefCodeNames.FUTURE_ORDER_TYPE.PENDING_DATE_ORDER);
        			
						thisOrder.setReleaseDate(sdf.format(pendingDate));
        			
						pendingDateOrdersList.add(thisOrder);
					}		
				}
			}
			
		}catch (Exception exc) {
        	exc.printStackTrace();
            throw exc;
    	}
        return pendingDateOrdersList;
	}
	
	/**
	   * Get order schedule dates
	   */
	  public static List<String> getOrderScheduleDatesInDateRange(HttpServletRequest request,OrderScheduleJoin orderSchedule,
			  Date pBegDate, Date pEndDate) throws Exception{
		  
		  	SimpleDateFormat userSdf = new SimpleDateFormat(ClwI18nUtil.getDatePattern(request));
			List<String> scheduleDates = new ArrayList<String>();
			List<GregorianCalendar> dateList = new ArrayList<GregorianCalendar>();

			GregorianCalendar scheduleStart = new GregorianCalendar();
			scheduleStart.set(GregorianCalendar.DAY_OF_YEAR, 1);
			scheduleStart.set(GregorianCalendar.DAY_OF_MONTH, 1);
			scheduleStart.setTime(pBegDate);
			
			String ruleCd = orderSchedule.getOrderScheduleRuleCd();
			Date expDate = orderSchedule.getExpDate();
			
			if(ruleCd.equals(RefCodeNames.ORDER_SCHEDULE_RULE_CD.DAY_MONTH)){
				
				dateList = OrderSchedulerLogic.calculateMonthlyOrders(scheduleStart, orderSchedule.getElements(), orderSchedule.getCycle());
				
			}else if(ruleCd.equals(RefCodeNames.ORDER_SCHEDULE_RULE_CD.WEEK)){
				
				dateList = OrderSchedulerLogic.calculateWeeklyOrders(scheduleStart, orderSchedule.getElements(), orderSchedule.getCycle());
				
			}else if(ruleCd.equals(RefCodeNames.ORDER_SCHEDULE_RULE_CD.WEEK_MONTH)){
				
				int monthWeeks = orderSchedule.getMonthWeeks();
				int[] monthMonths = orderSchedule.getElements();
				int monthWeekDay = orderSchedule.getMonthWeekDay();
				dateList = OrderSchedulerLogic.calculateCustomOrders(scheduleStart, monthWeekDay, monthWeeks, monthMonths);
				
			}
			
			for(int i=0; i<dateList.size(); i++){
				GregorianCalendar cal = (GregorianCalendar)dateList.get(i);
				Date scheduleDate = cal.getTime();
				
				if(!scheduleDate.after(expDate)){ //check that calculated date is within schedule date range
					if((pBegDate.before(scheduleDate) || pBegDate.equals(scheduleDate)) 
							&& (pEndDate.after(scheduleDate) || pEndDate.equals(scheduleDate))){
	
						scheduleDates.add(userSdf.format(cal.getTime()));
					}
				}	
			}
			
			Date[] alsoDates = orderSchedule.getAlsoDates();
			Date[] exceptDates = orderSchedule.getExceptDates();
			
			//Also dates
			for(int i=0; i<alsoDates.length; i++){
				Date alsoDate = alsoDates[i];
				
				if(!pBegDate.after(alsoDate) || pEndDate.before(alsoDate)){
					
					scheduleDates.add(userSdf.format(alsoDate));
				}
			}
			
			//Except dates
			for(int i=0; i<exceptDates.length; i++){
				Date exceptDate = exceptDates[i];
		
				for(int j=0; j<scheduleDates.size(); j++){
					
					Date sdDate = userSdf.parse(scheduleDates.get(j));			
					
					if(sdDate.equals(exceptDate)){
						scheduleDates.remove(j);
					}
				}
			}
			

			for (int ii = 0; ii < scheduleDates.size(); ii++) {
				
				Date sdDate = userSdf.parse(scheduleDates.get(ii));
				
				if(pBegDate.after(sdDate) && !(pEndDate.after(sdDate))){
					scheduleDates.remove(ii);
				}

			}
			return scheduleDates;
		}
	
	public static Map<String,Object> getOrderDetail(HttpServletRequest request, OrdersForm form) throws Exception {
		
		Map<String,Object> returnValue = new HashMap<String,Object>();
    	ActionErrors errors = new ActionErrors();
    	ActionMessages messages = new ActionMessages();
    	returnValue.put(ActionErrors.GLOBAL_ERROR, errors);
    	returnValue.put(ActionErrors.GLOBAL_MESSAGE, messages);
		
		String orderId = form.getOrderId();
		log.info("getOrderDetail() orderId = " + orderId);
		SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
		
		if(orderId.equals(StringUtils.EMPTY)){ //orderId is NOT set in the OrdersForm
		   
		   orderId = sessionDataUtil.getOrderId();
		}
		else {
			sessionDataUtil.setOrderId(orderId);
		}
		
		//ActionErrors errors = new ActionErrors();
		
		// Get the existing OrderOpDetailForm and pass it to the existing Logic method.
		log.info("orderId = " + orderId);		
		
		errors = OrderOpLogic.getOrderStatusDetail(request, form.getOrderOpDetailForm(), orderId);
		//ActionErrors errors = OrderDetailLogic.getOrderStatusDetail(request, form.getOrderOpDetailForm(), orderId); //we could also use this method to populate the form 
		
		//OrderPropertyDataVector orderPropertyList = form.getOrderOpDetailForm().getOrderPropertyList();		
		//log.info("getOrderDetail(): orderPropertyList = " + orderPropertyList);
		
		if(errors.size()>0) {
			 log.error("Exception occurred while executing OrderOpLogic.getOrderStatusDetail() method");
	         String errorMess = ClwI18nUtil.getMessage(request, "shop.orderdetail.error.problemFindingOrderDetailInfo", null);
	         errors.add("error", new ActionError("error.simpleGenericError", errorMess));
	         return returnValue;
		}
		APIAccess factory = new APIAccess();
        Order orderBean = factory.getOrderAPI(); 

        CleanwiseUser appUser = (CleanwiseUser)request.getSession().getAttribute(Constants.APP_USER);
        
        //Find out if the Order is in "Pending Approval" status 
        int intOrderId = Integer.parseInt(orderId);
		OrderData orderData = orderBean.getOrderStatus(intOrderId);
		if (orderData.getOrderStatusCd().equals(RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL)){		  			  
			  //update locationBudgetChartDto for the Order site with the Budget info. 
			  SiteData orderSiteData = form.getOrderOpDetailForm().getSite();
			  log.debug("orderSiteData = " + orderSiteData);
			  if (orderSiteData != null) {
			     errors = SiteLocationBudgetLogic.updateLocationBudgetChart(request, orderSiteData, appUser);	
			     if(errors.size()>0) {
			    	 log.error("Exception occurred while updating Location Budget Chart...");
			         String errorMess = ClwI18nUtil.getMessage(request, "shop.pendingorderdetail.error.problemUpdatingLocationBudgetChart", null);
			         errors.add("error", new ActionError("error.simpleGenericError", errorMess));
			         return returnValue;
			     }
			  } 
			  //if selected Location(Site) is different from the Order Location(Site) 
			  //AND Order Location supports budgeting
			  //show info. message on the screen
			  if (appUser.getSite() != null && orderSiteData != null 
					  && appUser.getSite().getSiteId() != orderSiteData.getSiteId()) {
        		  LocationBudgetChartDto chartDto = sessionDataUtil.getLocationBudgetChartDto();
        		  if(chartDto!=null && chartDto.isAccountHasBudget()){
				     Object[] insertionStringsLB = new Object[2];
				     //find Order's Site name (Order's Location name)
	                 insertionStringsLB[0] = form.getOrderOpDetailForm().getOrderStatusDetail().getShipTo().getShortDesc().trim();
				     //find User's Site name (Site Location name)
	                 String userSiteName = appUser.getSite().getBusEntity().getShortDesc().trim();
	                 insertionStringsLB[1] = userSiteName;
				     String success2Message = ClwI18nUtil.getMessage(request, "shop.pendingorderdetail.message.locationBudgetForOrderLocation", insertionStringsLB);
		       	     messages.add("message", new ActionMessage("message.simpleMessage", success2Message));
				     log.info("SVC_messages = " + messages);
        		  }
			  }
		        
		}		                
		
		ProductInformation productInfoEjb = factory.getProductInformationAPI();
        
		// Code below creates IdVector of the Product Items,
		// which belong to one Order: we need it to show "Green" items on the Order-Detail screen (panel)
        IdVector orderItemIds = new IdVector();	
        OrderItemDescDataVector itemStatusDescV = null;
        
        try {
           itemStatusDescV =
               orderBean.getOrderItemDescCollection(Integer.parseInt(orderId));
    	}
    	catch (Exception e) {
        	log.error("Exception occurred when searching for product items...", e);
            String errorMess = ClwI18nUtil.getMessage(request, "orders.search.error.problemRetrievingOrderItems", null);
            errors.add("error", new ActionError("error.simpleGenericError", errorMess));
            return returnValue;
    	}
        if (itemStatusDescV != null && itemStatusDescV.size() > 0) {
           Iterator it = itemStatusDescV.iterator();
           while (it.hasNext()) {
               OrderItemData orderItemData = ((OrderItemDescData) (it.next())).getOrderItem();
               if (orderItemData != null) {
            	   int itemId = orderItemData.getItemId();
            	   orderItemIds.add(itemId);
               }
           }
        }
		
        // retrieve Product Data with all properties, INCLUDING "Green" certificates;
        // it will be used for showing "Green" certificates for the Product Items 
        // on the Order-Detail/Pending-Order-Detail screens 
        try {
            Map<Integer, ProductData> productDataByItemIdMap = 
        	   DashboardLogic.getProductDataByItemIdMap(productInfoEjb, orderItemIds);
            form.setProductDataByItemIdMap(productDataByItemIdMap);
        } catch (Exception e) {
        	log.error("Exception occurred when creating productDataByItemIdMap...", e);
            String errorMess = ClwI18nUtil.getMessage(request, "orders.search.error.problemCreatingProductDataByItemIdMap", null);
            errors.add("error", new ActionError("error.simpleGenericError", errorMess));
            return returnValue;
    	}
        
        //retrieve SPL information as necessary
        if (appUser.getUserAccount().isShowSPL()) {
        	try {
        		ShoppingServices shoppingServEjb = factory.getShoppingServicesAPI();
                int catalogId = ((Integer)request.getSession(false).getAttribute(Constants.CATALOG_ID)).intValue();
                int contractId = 0;
                Integer contractIdI = (Integer) request.getSession(false).getAttribute(Constants.CONTRACT_ID);
                if (contractIdI != null) {
                    contractId = contractIdI.intValue();
                }
                ShoppingCartItemDataVector itemV = null;
                if (appUser.getSite() == null) {
                    itemV = shoppingServEjb.prepareShoppingItems(appUser.getUserStore().getStoreType().getValue(),
                            SiteData.createValue(), catalogId, contractId, orderItemIds, null);
                }
                else {
                	itemV = shoppingServEjb.prepareShoppingItems(appUser.getUserStore().getStoreType().getValue(),
                        appUser.getSite(), catalogId, contractId, orderItemIds,
                        SessionTool.getCategoryToCostCenterView(request.getSession(), appUser.getSite().getSiteId()));
                }
                Map<Integer, ProductData> productDataByItemIdMap = form.getProductDataByItemIdMap();
                if (Utility.isSet(productDataByItemIdMap)) {
                	Iterator<ShoppingCartItemData> itemIterator = itemV.iterator();
                	while (itemIterator.hasNext()) {
                		ShoppingCartItemData item = itemIterator.next();
                		ProductData product = productDataByItemIdMap.get(new Integer(item.getItemId()));
                		if (product != null) {
                			product.setCatalogDistrMapping(item.getProduct().getCatalogDistrMapping());
                		}
                	}
                }
        	}
        	catch (Exception e) {
            	log.error("Exception occurred when getting SPL information...", e);
                String errorMess = ClwI18nUtil.getMessage(request, "orders.search.error.problemRetrievingProductSPLData", null);
                errors.add("error", new ActionError("error.simpleGenericError", errorMess));
                return returnValue;
        	}
        }
        //end retrieve SPL information
		///////////////////////////////////////////////////////////////////
		
        
		// Chunk of code below is taken from the HandleOrderLogic.java class;
		// It retrieves a list of reason codes the user 
		// is allowed to approve and stores it in the OrdersForm;
		// These reason codes will be later used in one of the Order-Detail SubPanels 
		// to populate the subsequent JSP
		//////////////////////////////////////////////////////////////////
        IdVector notesUserApproveIdV = null;
        try {
           notesUserApproveIdV =
               orderBean.getPropertiesUserCanApprove(appUser.getUser(), Integer.parseInt(orderId));
        } catch (Exception e) {
        	log.error("Exception occurred when creating notesUserApproveIdV...", e);
            String errorMess = ClwI18nUtil.getMessage(request, "orders.search.error.problemCreatingNotesUserApproveIdV", null);
            errors.add("error", new ActionError("error.simpleGenericError", errorMess));
            return returnValue;
    	}
        log.info("SVC notesUserApproveIdV = " + notesUserApproveIdV);
        
        form.setNotesUserApproveIdV(notesUserApproveIdV);
        
        ///////////////////////////////////////////////////////////        
        // Retrieve accountMiscProperties to try to populate "Rebill Order" field
        AccountData accountD = appUser.getUserAccount();
        PropertyDataVector accountMiscProperties = null;
        
        try {
             accountMiscProperties = accountD.getMiscProperties();
        } catch (Exception e) {
            	log.error("Exception occurred when creating accountMiscProperties...", e);
                String errorMess = ClwI18nUtil.getMessage(request, "orders.search.error.problemCreatingAccountMiscProperties", null);
                errors.add("error", new ActionError("error.simpleGenericError", errorMess));
                return returnValue;
        }
                		
        form.setAccountMiscProperties(accountMiscProperties);
        ////////////////////////////////////////////////////////////   
        
        //Retrieve "Billing" Properties in order to populate "Confirmation Only Order" field on the screen
        OrderPropertyDataVector billingProperties = null;
        boolean billingFl = false;
        
        try {
            billingProperties = orderBean.getOrderPropertyCollection(Integer.parseInt(orderId), RefCodeNames.ORDER_PROPERTY_TYPE_CD.BILLING_ORDER);
        } catch (Exception e) {
           	log.error("Exception occurred when creating billingProperties...", e);
               String errorMess = ClwI18nUtil.getMessage(request, "orders.search.error.problemCreatingBillingProperties", null);
               errors.add("error", new ActionError("error.simpleGenericError", errorMess));
               return returnValue;
        }
           
        if (billingProperties.size() > 0) {
    	   billingFl = true;
        }
        form.setIsBillingOrder(billingFl);
        
        //Retrieve "Rebill Order" Properties in order to populate "Rebill Order" field on the screen
        OrderPropertyDataVector rebillProperties = null;
        boolean rebillFl = false;
        
        try {
        	rebillProperties = orderBean.getOrderPropertyCollection(Integer.parseInt(orderId), RefCodeNames.ORDER_PROPERTY_TYPE_CD.REBILL_ORDER);
        } catch (Exception e) {
           	log.error("Exception occurred when creating rebillProperties...", e);
               String errorMess = ClwI18nUtil.getMessage(request, "orders.search.error.problemCreatingRebillProperties", null);
               errors.add("error", new ActionError("error.simpleGenericError", errorMess));
               return returnValue;
        }
           
        if (rebillProperties.size() > 0) {
        	rebillFl = true;
        }
        form.setIsRebillOrder(rebillFl);
        
        //Retrieve ALL Order Properties in order to populate the Action Form
        OrderPropertyDataVector allOrderProperties = null;
        
        try {
        	allOrderProperties = orderBean.getOrderPropertyCollectionByOrderId(Integer.parseInt(orderId));
        } catch (Exception e) {
           	log.error("Exception occurred when creating allOrderProperties...", e);
               String errorMess = ClwI18nUtil.getMessage(request, "orders.search.error.problemCreatingAllOrderProperties", null);
               errors.add("error", new ActionError("error.simpleGenericError", errorMess));
               return returnValue;
        }
           
        if (allOrderProperties.size() > 0) {
        	form.setAllOrderProperties(allOrderProperties);
        }   
        
        //Set "Received" property in the OrdersForm.OrderOpDetailForm.OrderStatusDetail form using setReceived(val) method
        
        OrderStatusDescData orderStatusDetailForm = form.getOrderOpDetailForm().getOrderStatusDetail();
        
        Iterator itAOP = allOrderProperties.iterator();
        while(itAOP.hasNext()){
            OrderPropertyData oi = (OrderPropertyData) itAOP.next();
            if(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_RECEIVED.equals(oi.getOrderPropertyTypeCd().trim())){
            	log.debug("getOrderDetail() method: found ORDER_RECEIVED property for the Order");
            	orderStatusDetailForm.setReceived(true);
            	break;
            }
        }
        
        //STJ-5265: Set service tickets info, if StJohn is accessed from Orca.
        SessionDataUtil sessionData = Utility.getSessionDataUtil(request);
        if (sessionData.isRemoteAccess()) {

        	form.getOrderOpDetailForm().setAssociatedServiceTickets(null);
        	form.getOrderOpDetailForm().setUnavailableServiceTickets(null);

        	IdVector seviceTicketNumbers = orderBean.getAssociatedServiceTickets(intOrderId);

        	if (Utility.isSet(seviceTicketNumbers)) {
        										
        		RemoteWebClient orcaWebClient = sessionData.getRemoteWebClient();
        		String sessionId = request.getSession().getId();
        		IdVector unavailableNumbers = null;
        		if(orcaWebClient!=null) {
        			unavailableNumbers = orcaWebClient.determineUnAvailableStNumbers(sessionId,
        									appUser, seviceTicketNumbers, new Date(),false);
        		}
        		
        		form.getOrderOpDetailForm().setAssociatedServiceTickets(seviceTicketNumbers);
        		form.getOrderOpDetailForm().setUnavailableServiceTickets(unavailableNumbers);
        	}
        }  
              
        // decide if column Pack, UOM, Manufacturer Name and Manufacturer Sku should not be displayed 
        // based on SHOP_UI_DEFAULT property status
        Account accountBean = APIAccess.getAPIAccess().getAccountAPI();
        form.getOrderOpDetailForm().setHidePack(false);
		form.getOrderOpDetailForm().setHideUom(false);
		form.getOrderOpDetailForm().setHideManufName(false);
		form.getOrderOpDetailForm().setHideManufSku(false);
        ProductViewDefDataVector pvdDV = accountBean.getProductViewDefData(orderStatusDetailForm.getAccountId());
        for (int i = 0; i < pvdDV.size(); i++){
			ProductViewDefData pvdD = (ProductViewDefData) pvdDV.get(i); 
			if (!pvdD.getProductViewCd().equals(RefCodeNames.SHOP_UI_PRODUCT_VIEW_CD.SHOP_UI_DEFAULT))
				continue;
			if (pvdD.getAttributename().equals("PACK")){
				form.getOrderOpDetailForm().setHidePack(Utility.isEqual(pvdD.getStatusCd(), "INACTIVE"));
			} else if (pvdD.getAttributename().equals("UOM")){
				form.getOrderOpDetailForm().setHideUom(Utility.isEqual(pvdD.getStatusCd(), "INACTIVE"));
			} else if (pvdD.getAttributename().equals("Manufacturer Name")){
				form.getOrderOpDetailForm().setHideManufName(Utility.isEqual(pvdD.getStatusCd(), "INACTIVE"));
			} else if (pvdD.getAttributename().equals("Manufacturer Sku")){
				form.getOrderOpDetailForm().setHideManufSku(Utility.isEqual(pvdD.getStatusCd(), "INACTIVE"));
			}
		}

		//Populate distItemMap
		Map distItemMap = new TreeMap();
		Iterator it = form.getOrderOpDetailForm().getOrderItemDescList().iterator();
		while(it.hasNext()){
			OrderItemDescData oiDD = (OrderItemDescData)it.next();
			int distId = oiDD.getDistId();
			
			if(!distItemMap.containsKey(distId)){
				OrderItemDescDataVector oiDDV = new OrderItemDescDataVector();
				oiDDV.add(oiDD);
				distItemMap.put(distId, oiDDV);
			}else{
				OrderItemDescDataVector oiDDV = (OrderItemDescDataVector)distItemMap.get(distId);
				oiDDV.add(oiDD);
			}
		}
    	DashboardLogic.sortProperties(form.getOrderOpDetailForm().getOrderPropertyList());

		form.setDistItemMap(distItemMap);
		return returnValue;
	}
 	
	public static Map<String,Object> updatePO(HttpServletRequest request, OrdersForm form) throws Exception {
		Map<String,Object> returnValue = new HashMap<String,Object>();
		ActionErrors errors = new ActionErrors();
    	ActionMessages messages = new ActionMessages();
    	returnValue.put(ActionErrors.GLOBAL_ERROR, errors);
    	returnValue.put(ActionErrors.GLOBAL_MESSAGE, messages);
    	
    	CleanwiseUser appUser = ShopTool.getCurrentUser(request);
    	//STJ-4601 if the user has the browse only privilege set, they are not allowed to modify the order
    	if (appUser.isBrowseOnly()) {
			String errorMess = ClwI18nUtil.getMessage(request,"shop.pendingorderdetail.error.notAuthorizedToModifyOrder");
			errors.add("error",	new ActionError("error.simpleGenericError", errorMess));
			return returnValue;
    	}
    	String orderId1 = form.getOrderId();	
    	String orderStatusItemsPanel = form.getOrderOpDetailForm().getOrderStatusDetail().getOrderDetail().getOrderStatusCd(); 
    	
    	// For Pending Approval Orders: update customer's PO# in the Database
    	if(orderStatusItemsPanel.equals(RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL)) {
    		log.info("form.getOrderOpDetailForm().getRequestPoNum() = " + form.getOrderOpDetailForm().getRequestPoNum());
    		String poNumber = form.getOrderOpDetailForm().getRequestPoNum();  
    		String orderNum = form.getOrderOpDetailForm().getOrderStatusDetail().getOrderDetail().getOrderNum();
    		try {			
  	        
    			Order orderBean = APIAccess.getAPIAccess().getOrderAPI();
    			OrderJoinData orderJD = orderBean.fetchOrder(Integer.parseInt(orderId1));

    			//validate purchase order information.  
    			boolean purchaseOrderIssueOccurred = false;
    			boolean allowPoEntry = true;
    			if (RefCodeNames.CUSTOMER_SYSTEM_APPROVAL_CD.PUNCH_OUT_INLIN_NON_E_ORD_ONLY.equals(appUser.getUserAccount().getCustomerSystemApprovalCd())){
    				allowPoEntry = false;
    			}
    			boolean f_showPO = true;
    			if (orderJD.getOrderCC() != null) {
    				f_showPO = false;
    			}
    			SiteData site = SessionTool.getSiteDataNoCache(request,  orderJD.getOrder().getSiteId());

    			if (site.getBlanketPoNum() != null && 
    					site.getBlanketPoNum().getBlanketPoNumId() != 0){
    				allowPoEntry = false;
    			}
    			String poNum = orderJD.getOrder().getRequestPoNum();
    			log.info("poNum1 = " + poNum);
    			if (allowPoEntry && f_showPO){		            
    				if (!Utility.isSet(poNumber) && appUser.getPoNumRequired()) {
    					Object[] insertionStringsI = new Object[1];
    					insertionStringsI[0] = orderNum;
    					String errorMess = ClwI18nUtil.getMessage(request,"shop.pendingorderdetail.error.poNumber",insertionStringsI);
    					errors.add("error",	new ActionError("error.simpleGenericError", errorMess));
    					purchaseOrderIssueOccurred = true;
    				}
    				else {
    					poNum = poNumber;
    				}
    			}
    			log.info("poNum2 = " + poNum);
    			if(poNum != null && poNum.trim().length()>Constants.MAX_SIZE_OF_PO_NUM){
    				log.error("PO# should not exceed 255 characters.");
    				Object[] insertionStrings = new Object[1];
    				insertionStrings[0] = orderNum;
    				String errorMess = ClwI18nUtil.getMessage(request, "shop.orderdetail.error.poNumber", insertionStrings);
    				errors.add("error", new ActionError("error.simpleGenericError", errorMess));
    			} else {
    				HandleOrderLogic handleOrderLogic =
    					(HandleOrderLogic) ClwCustomizer.getJavaObject(request,"com.cleanwise.view.logic.HandleOrderLogic");
    				ActionErrors poErrors = handleOrderLogic.validatePoNum(request, poNum);
    				if (poErrors != null && !poErrors.isEmpty()) {
    					errors.add(poErrors);
    					purchaseOrderIssueOccurred = true;
    				}
    				//if no purchase order error occurred, save the order
    				if (!purchaseOrderIssueOccurred) {
    					// fetch the Order
    					OrderData orderData = orderBean.getOrderStatus(Integer.parseInt(orderId1));
    					orderData.setRequestPoNum(poNum);
    					// update Order in the Database
    					orderBean.updateOrder(orderData);
	        	
    					// update OrdersForm with a new value of poNum
    					OrderData orderDetailForm = form.getOrderOpDetailForm().getOrderStatusDetail().getOrderDetail();
    					orderDetailForm.setRequestPoNum(poNum);
    				}
    			} //endif
    		} //try
    		catch (Exception e) {
    			Object[] insertionStrings = new Object[1];
    			if (poNumber != null && (!poNumber.equals(""))) {
    				insertionStrings[0] = poNumber; 
    			}
    			else {
    				insertionStrings[0] = orderId1;
    			}
    			String errorMess = ClwI18nUtil.getMessage(request, "pendingOrders.error.unexpectedSavePoNumPendingOrderError", insertionStrings);
    			errors.add("error", new ActionError("error.simpleGenericError", errorMess));
    		}
    	}	//endif        
    	return returnValue;
	}
	
	public static Map<String,Object> saveOrderDetail(HttpServletRequest request, OrdersForm form, boolean receivedFl, boolean rejectFl) throws Exception {
		
		Map<String,Object> returnValue = new HashMap<String,Object>();
    	ActionErrors errors = new ActionErrors();
    	ActionMessages messages = new ActionMessages();
    	returnValue.put(ActionErrors.GLOBAL_ERROR, errors);
    	returnValue.put(ActionErrors.GLOBAL_MESSAGE, messages);
    	
		
        CleanwiseUser appUser = ShopTool.getCurrentUser(request);
    	//STJ-4601 if the user has the browse only privilege set, they are not allowed to modify the order
    	if (appUser.isBrowseOnly()) {
			String errorMess = ClwI18nUtil.getMessage(request,"shop.pendingorderdetail.error.notAuthorizedToModifyOrder");
			errors.add("error",	new ActionError("error.simpleGenericError", errorMess));
			return returnValue;
    	}
    	
    	String orderStatusItemsPanel = form.getOrderOpDetailForm().getOrderStatusDetail().getOrderDetail().getOrderStatusCd(); 
		log.info("saveOrderDetail().orderStatusItemsPanel = " + orderStatusItemsPanel);
		
        String orderId1 = form.getOrderId();		
		
		log.info("orderId1 = " + orderId1);
						
		if(orderId1.equals(StringUtils.EMPTY)){ //orderId is NOT set in the OrdersForm
		   SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
		   orderId1 = sessionDataUtil.getOrderId();
		}
		
		SessionDataUtil sessionDataUtil0 = Utility.getSessionDataUtil(request);
		
		if (!orderStatusItemsPanel.equals(RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL) &&
        		!orderStatusItemsPanel.equals(RefCodeNames.ORDER_STATUS_CD.PENDING_CONSOLIDATION) &&
        		!orderStatusItemsPanel.equals(RefCodeNames.ORDER_STATUS_CD.PENDING_DATE)) { 
			
		   for (int i1 = 0; i1 < form.getOrderOpDetailForm().getOrderItemDescList().size(); i1++) {
			   OrderItemDescData orderItemDescData = (OrderItemDescData) form.getOrderOpDetailForm().getOrderItemDescList().get(i1);
			   String received1 = orderItemDescData.getItemQuantityRecvdS();
			   log.info("received1 " + i1 + " = " + received1);
			   
			   OrderItemDescData orderItemDescData22 = (OrderItemDescData) form.getOrderItemDescList().get(i1);
			   String str22 = orderItemDescData22.getItemQuantityRecvdS().trim();
			   log.info("str22 " + i1 + " = " + str22);
			   
			   OrderItemDescData orderItemDescData23 = (OrderItemDescData) sessionDataUtil0.getOrderItemDescList().get(i1);
			   String str23 = orderItemDescData23.getItemQuantityRecvdS().trim();
			   log.info("str23 " + i1 + " = " + str23);
		   }
				   
		   // if "Received" column is shown in the "Items" section of the Order-Detail screen, update "Received" quantities 
		   if (receivedFl) {
				   log.info("calling OrderOpLogic.updateReceivedItems() method...");
				   for (int i2 = 0; i2 < form.getOrderOpDetailForm().getOrderItemDescList().size(); i2++) {
					   OrderItemDescData orderItemDescData2 = (OrderItemDescData) form.getOrderOpDetailForm().getOrderItemDescList().get(i2);
					   String received2 = orderItemDescData2.getItemQuantityRecvdS();
					   log.info("received2 " + i2 + " = " + received2);
					   int received2Int = 0;
					   int intFl = 0;
					   String errorMess = new String();
					   try {
					       received2Int = Integer.parseInt(received2);
					   } catch (NumberFormatException e) {
						   intFl = 1;
						   Object[] insertionStrings = new Object[1];
						   insertionStrings[0] = received2;
						   log.error("Exception parsing <Received> quantity",
		                            e);
		                   errorMess = ClwI18nUtil.getMessage(request,
		                    "shop.orderdetail.error.receivedValueIsWrong", insertionStrings);
		                   errors.add("error", new ActionError("error.simpleGenericError",
		                                errorMess));
		            
					   } 
					   if (intFl == 0 && received2Int < 0) {
						   Object[] insStrings = new Object[1];
						   insStrings[0] = received2;
						   log.info("Negative <Received> quantity " + received2Int);
		                   errorMess = ClwI18nUtil.getMessage(request,
		                    "shop.orderdetail.error.receivedValueIsWrong", insStrings);
		                   errors.add("error", new ActionError("error.simpleGenericError",
		                                errorMess));
					   }
				   } //for
					   
				   if(errors.size()>0) {
					    return returnValue;
				   }  
				   
				   // were the "Received" quantities changed: Begin
				   /*boolean flRecQtyChanged = false; 
				   
				   Order orderBean = APIAccess.getAPIAccess().getOrderAPI();
			       OrderJoinData orderJoinData = orderBean.fetchOrder(Integer.parseInt(orderId1));
				   OrderItemJoinDataVector orderItemJoinData2Vector = (OrderItemJoinDataVector) orderJoinData.getOrderJoinItems();
				   
				   for (int j1=0; j1 < form.getOrderOpDetailForm().getOrderItemDescList().size(); j1++) {
					   OrderItemDescData orderItemDescData1 = (OrderItemDescData) form.getOrderOpDetailForm().getOrderItemDescList().get(j1);
					   String str1 = orderItemDescData1.getItemQuantityRecvdS().trim();
				       for (int j2 = 0; j2 < orderItemJoinData2Vector.size(); j2++){
					       OrderItemJoinData orderItemJoinData2 = (OrderItemJoinData) orderJoinData.getOrderJoinItems().get(j2);
					       OrderItemData orderItemData = (OrderItemData) orderItemJoinData2.getOrderItem();
					   				   
					       String str2 = Integer.toString(orderItemData.getTotalQuantityReceived());					       

					       if (orderItemDescData1.getOrderItem().getOrderItemId() == orderItemData.getOrderItemId()) {
                               log.info("orderItemId1 = " + orderItemDescData1.getOrderItem().getOrderItemId() + "orderItemId2 = " + orderItemData.getOrderItemId());
					    	   log.info("str1 = " + str1 + " str2 = " + str2);					    	   
					    	   if (str1.equals(str2)) {
						          break;				   
					          } else {
						          flRecQtyChanged = true;
						          break;
					          }					      
					       }
				       }
				   }			*/	   				   
				   //were the "Received" quantities changed: End
				   
				   ActionErrors updateReceivedItemsErrors = OrderOpLogic.updateReceivedItems(request,form.getOrderOpDetailForm());
			       if(updateReceivedItemsErrors.size()>0) {
			    	    errors.add(updateReceivedItemsErrors);
						return returnValue;
				   }
				   //if (flRecQtyChanged) {   
			          String success1Message = ClwI18nUtil.getMessage(request, "shop.orderdetail.message.receivedValueUpdated", null);
		       	      messages.add("message", new ActionMessage("message.simpleMessage", success1Message));
				   //}
		   } //if (receivedFl) {
		} //if (!orderStatusItemsPanel.equals(RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL) && 
  		
	  return returnValue;
	} 
	
	public static ActionErrors reorder(HttpServletRequest request, OrdersForm form) throws Exception {
		
		String orderId1 = form.getOrderId();
		
		log.info("reorder()");
		log.info("reorder.orderId1_1 = " + orderId1);
		
		SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
		
		if(orderId1.equals(StringUtils.EMPTY)){ //orderId is NOT set in the OrdersForm
		   
		   orderId1 = sessionDataUtil.getOrderId();
		}
		else {
			sessionDataUtil.setOrderId(orderId1);
		}
				
		log.info("reorder.orderId2 = " + orderId1);
		int pOrderId = Integer.parseInt(orderId1);
		
		ActionErrors errors = new ActionErrors();
		try {
		   errors = OrderDetailLogic.reorderNewUi(request, pOrderId);
        } catch (Exception e) {
        	log.error("Exception occurred when doing reorder...", e);
            String errorMess = ClwI18nUtil.getMessage(request, "orders.search.error.problemReorderingProductItems", null);
            errors.add("error", new ActionError("error.simpleGenericError", errorMess));
            return errors;
        }
        
        return errors;
	}

	public static ActionErrors modify(HttpServletRequest request, OrdersForm form) throws Exception {
		
		String orderIdM = form.getOrderId();
		
		log.info("modify()");
		log.info("modify.orderIdM_1 = " + orderIdM);
		
		SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
		
		if(orderIdM.equals(StringUtils.EMPTY)){ //orderId is NOT set in the OrdersForm
		   
		   orderIdM = sessionDataUtil.getOrderId();
		}
		else {
			sessionDataUtil.setOrderId(orderIdM);
		}
				
		log.info("modify.orderIdM_2 = " + orderIdM);
		
		ActionErrors errors = new ActionErrors();
		try {
		   errors = OrderOpLogic.modifyOrder(request, form.getOrderOpDetailForm());
		   //STJ-5747.
		   if(errors != null && errors.isEmpty()) {
			   UserShopForm userShopForm = new UserShopForm();
	           SessionDataUtil sessionData = Utility.getSessionDataUtil(request);
	           userShopForm.setShopMethod(Constants.SHOP_BY_CATEGORY);
	           errors = UserShopLogic.init(request, userShopForm);
	           //populate the form with a map of categories to items, so we can display the count of
	           //items per category in the menu
	           errors.add(UserShopLogic.populateCategoryToItemMap(request, userShopForm));
	           sessionData.setUserShopForm(userShopForm);
		   }
        } catch (Exception e) {
        	log.error("Exception occurred when doing modifyOrder...", e);
            String errorMess = ClwI18nUtil.getMessage(request, "orders.search.error.problemModifyingProductItems", null);
            errors.add("error", new ActionError("error.simpleGenericError", errorMess));
            return errors;
        }
        
        return errors;
	}
	
	/** 
	 * This will calculate corporate order open dates based on release date, interval hours, and cut off time.
	 * @param request
	 * @param pLocaleCd
	 * @param pSite
	 * @param pBegDate
	 * @param pEndDate
	 * @param parOrderSchedules
	 * @return
	 * @throws Exception
	 */
	private static Collection<OrderInfoBase> getCorporateOrders(HttpServletRequest request,String pLocaleCd, SiteData pSite,
    		Date pBegDate, Date pEndDate, DeliveryScheduleViewVector corpOrderSchedules) throws Exception{
    	
    	DateFormat df = DateFormat.getDateInstance();
    	SimpleDateFormat sdf = new SimpleDateFormat(ClwI18nUtil.getDatePattern(request));
    	SimpleDateFormat stf = new SimpleDateFormat(ClwI18nUtil.getTimePattern(request));
    	
    	Collection<OrderInfoBase> corpOrdersList = new ArrayList<OrderInfoBase>();
    	FutureOrderDto thisOrder = null;
    	try{
    		HttpSession session = request.getSession();
    		APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    		Schedule schedule = factory.getScheduleAPI();
    		
    		ShoppingCartData inventoryCart =(ShoppingCartData) session.getAttribute(Constants.INVENTORY_SHOPPING_CART);
    		String inventoryCartTotal = null;
    		
    		if(inventoryCart != null){
    			inventoryCartTotal = new Double(inventoryCart.getItemsCost()).toString();
    		}
    		
    		try{
    			Iterator corpOrderSchIterator = corpOrderSchedules.iterator();
    			DeliveryScheduleView deliveryScheduleView = null;
	        	
    			//Here we iterate corporate schedules and calculate corporate order open dates.
    			while(corpOrderSchIterator.hasNext()) {
    				deliveryScheduleView = (DeliveryScheduleView)corpOrderSchIterator.next();
    				int interval = Integer.parseInt(schedule.getCorpScheduleIntervalValue(deliveryScheduleView.getScheduleId()));
    				String releaseDatesS = deliveryScheduleView.getScheduleInfo();
    				StringTokenizer st = new StringTokenizer(releaseDatesS,":,,");
    				ArrayList<String> releaseDatesList = new ArrayList<String>();
    				while(st.hasMoreTokens()) {
    					releaseDatesList.add(st.nextToken());
    				}
    				// Below we get Hrs and mins from cut off time.
    				String cutOffS = deliveryScheduleView.getCutoffInfo();
    				String[] hrsAndMins = cutOffS.split(":");
    				int hrs = Integer.parseInt(hrsAndMins[0]);
    				int mins = Integer.parseInt(hrsAndMins[1]);
    			
    			
	    			for(int i = 1; i < releaseDatesList.size(); i++) {
	    				thisOrder = new FutureOrderDto();
	    				String releaseDateS = releaseDatesList.get(i);
	    				if (!Utility.isSet(releaseDateS)) {
                            continue;
                        }
	    				SimpleDateFormat sdp = new SimpleDateFormat("MM/dd/yyyy");
	    				Date releaseDate = sdp.parse(releaseDateS.trim());
	    				if(!((releaseDate.after(pBegDate) || releaseDate.equals(pBegDate)) 
	    						&& (releaseDate.before(pEndDate) || releaseDate.equals(pEndDate)))) {
	    					continue;
	    				}
	    				st = new StringTokenizer(releaseDateS.trim(),"/");
	    				int month = 0;
	    				int day = 0;
	    				int year = 0;
	    				for (int j = 0; st.hasMoreTokens() && j < 3; j++) {
    			        	if (j == 0) {
    			        		month = Integer.parseInt(st.nextToken());
    			        	} else if (j == 1) {
    			        		day = Integer.parseInt(st.nextToken());
    			        	} else if(j == 2) {
    			        		year = Integer.parseInt(st.nextToken());
    			        	}
	    			    }
	    				GregorianCalendar endDate = new GregorianCalendar(year, month - 1, day, hrs, mins);
	    				GregorianCalendar openingDate = new GregorianCalendar(year, month - 1, day, hrs, mins);
	    				df = DateFormat.getDateTimeInstance(DateFormat.SHORT,
	    					        DateFormat.SHORT);
	    				
	    				openingDate.add(Calendar.HOUR, -interval);
	    			    
		    			if(releaseDate.equals(deliveryScheduleView.getNextDelivery())){ //check for immediate next cut off
		    				thisOrder.setOrderTotal(inventoryCartTotal);
		    			}
		    			
		    			String scheduleBeginDateS = sdf.format(openingDate.getTime())+ " "+stf.format(openingDate.getTime());
		    			
	        			String scheduleEndDateS = sdf.format(endDate.getTime())+ " "+stf.format(endDate.getTime());
	        			
	    	        	thisOrder.setScheduleBeginDate(scheduleBeginDateS);
	    	        	thisOrder.setScheduleEndDate(scheduleEndDateS); 
	    	        	thisOrder.setReleaseDate(sdf.format(sdf.parse(releaseDateS.trim())));
	    	        	
	    	        	thisOrder.setOrderType(RefCodeNames.FUTURE_ORDER_TYPE.CORPORATE_ORDER_SCHEDULE);
			        	thisOrder.setSchedule(RefCodeNames.FUTURE_ORDER_TYPE.CORPORATE_ORDER_SCHEDULE);
			        	
			        	thisOrder.setOrderContent(RefCodeNames.FUTURE_ORDER_CONTENT_TYPE.CORPORATE_ORDER);
			        	
			        	if (Utility.isSet(corpOrderSchedules)) {
			        		thisOrder.setContentId(deliveryScheduleView.getScheduleId());
			        	}
			        	corpOrdersList.add(thisOrder);
	    			}
    			}
    			
    		}catch(RemoteException exc){
    			log.info(exc.getMessage());
    		}
        	
    	}catch (Exception exc) {
        	exc.printStackTrace();
            throw exc;
    	}
    	return corpOrdersList;
    }

}
