package com.cleanwise.view.logic;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.util.MessageResources;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dto.OrderSearchDto;
import com.cleanwise.service.api.session.Account;
import com.cleanwise.service.api.session.Country;
import com.cleanwise.service.api.session.Order;
import com.cleanwise.service.api.session.Site;
import com.cleanwise.service.api.util.QueryRequest;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.AccountData;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.OrderJoinData;
import com.cleanwise.service.api.value.OrderPropertyData;
import com.cleanwise.service.api.value.OrderPropertyDataVector;
import com.cleanwise.service.api.value.OrderStatusCriteriaData;
import com.cleanwise.service.api.value.OrderStatusDescData;
import com.cleanwise.service.api.value.OrderStatusDescDataVector;
import com.cleanwise.service.api.value.PairView;
import com.cleanwise.service.api.value.PairViewVector;
import com.cleanwise.service.api.value.SiteData;
import com.cleanwise.service.api.value.SiteViewVector;
import com.cleanwise.view.forms.OrderSearchForm;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.DisplayListSort;
import com.cleanwise.view.utils.ShopTool;
import com.espendwise.view.logic.esw.OrdersLogic;


public class OrderSearchLogic
{

    private static final String EMPTY = "";
    public static final String ORDER_SEARCH_FORM = "ORDER_SEARCH_FORM";

    /**
     *  Pick out the information from the form to send an email.
     *
     *@param  request
     */
    public static void listOrders(HttpServletRequest request, ActionForm form)
                           throws Exception
    {

        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        TimeZone timeZoneInp = null;
        if (appUser != null){
          timeZoneInp = Utility.getTimeZoneFromUserData(appUser.getSite());

        }

        //OrderSearchForm sForm = (OrderSearchForm)form;
        OrderSearchForm sForm = new OrderSearchForm();
        APIAccess factory = (APIAccess)session.getAttribute(
                                    Constants.APIACCESS);

        if (null == factory)
        {
            throw new Exception("Without APIAccess.");
        }

        Order orderEjb = factory.getOrderAPI();
        OrderStatusCriteriaData searchCriteria = OrderStatusCriteriaData.createValue();
        IdVector storeIds = appUser.getUserStoreAsIdVector();
        searchCriteria.getExcludeOrderStatusList().add(RefCodeNames.ORDER_STATUS_CD.REFERENCE_ONLY);
        Date currentDate = new Date();
        Calendar gCalendar = Calendar.getInstance();
        gCalendar.setTime(currentDate);

        // By default, list the orders for the last 3 months.
        int smonth = gCalendar.get(Calendar.MONTH) - 3;
        gCalendar.set(Calendar.MONTH, smonth);

        Date orderBeginDate = gCalendar.getTime();
        SimpleDateFormat sdfDT = new SimpleDateFormat(Constants.SIMPLE_DATE_PATTERN+ " " +Constants.SIMPLE_TIME24_PATTERN);

        sForm.setOrderDateRangeBegin(ClwI18nUtil.formatDateInp(request, orderBeginDate, timeZoneInp.getID() ));
        sForm.setOrderDateRangeEnd(ClwI18nUtil.formatDateInp(request, currentDate, timeZoneInp.getID()));

        searchCriteria.setOrderDateRangeBegin(sdfDT.format(orderBeginDate));
        searchCriteria.setOrderDateRangeEnd(sdfDT.format(currentDate));

        // search order must have userId or/and userTypeCd
        String userId = (String)session.getAttribute(Constants.USER_ID);
        String userType = (String)session.getAttribute(Constants.USER_TYPE);

        if (null == userId)
            userId = new String("");

        if (null == userType)
            userType = new String("");

        searchCriteria.setUserId(userId);
        searchCriteria.setUserTypeCd(userType);

            int siteId = appUser.getSite().getBusEntity().getBusEntityId();
            Integer sId = new Integer(siteId);
            searchCriteria.setSiteId(sId.toString());

            //get the account the site is part of
            Account accountBean = factory.getAccountAPI();
            AccountData ad = accountBean.getAccountForSite(siteId);
            Integer acctId = new Integer(ad.getBusEntity().getBusEntityId());
            searchCriteria.setAccountId(acctId.toString());

        // fetch the orders in the past year
        OrderStatusDescDataVector orderStatus = new OrderStatusDescDataVector();
        orderStatus = orderEjb.getOrderStatusDescCollection(searchCriteria,
                storeIds);
        sForm.setResultListAndShowSearchFields(orderStatus, true);
        session.setAttribute(ORDER_SEARCH_FORM, sForm);

        return;
    }

    public static void listPendingOrders(HttpServletRequest request,
            ActionForm form) throws Exception {
        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute("ApplicationUser");
        OrderSearchForm sForm = new OrderSearchForm();
        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        if (null == factory) {
            throw new Exception("Without APIAccess.");
        }
        Order orderEjb = factory.getOrderAPI();
        OrderStatusCriteriaData searchCriteria = OrderStatusCriteriaData.createValue();
        List<String> statusValueList = null;
        List<String> orderPropertyList = null;
        if (form instanceof OrderSearchForm) {
        	statusValueList = ((OrderSearchForm)form).getStatusValueList();
        	orderPropertyList = ((OrderSearchForm)form).getOrderPropertyList();
        }
        if (Utility.isSet(statusValueList)) {
        	Iterator statusValueIterator = statusValueList.iterator();
        	while (statusValueIterator.hasNext()) {
        		searchCriteria.getOrderStatusList().add(statusValueIterator.next());
        	}
        }
        else {
        	searchCriteria.getOrderStatusList().add(RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL);
        	searchCriteria.getOrderStatusList().add(RefCodeNames.ORDER_STATUS_CD.PENDING_CONSOLIDATION);
        	searchCriteria.getOrderStatusList().add(RefCodeNames.ORDER_STATUS_CD.PENDING_DATE);
        }
        IdVector storeIds = appUser.getUserStoreAsIdVector();

        // search order must have userId or/and userTypeCd
        String userId = (String)session.getAttribute(Constants.USER_ID);
        String userType = (String)session.getAttribute(Constants.USER_TYPE);
        if (null == userId) {
            userId = new String("");
        }
        if (null == userType) {
            userType = new String("");
        }
        searchCriteria.setUserId(userId);
        searchCriteria.setUserTypeCd(userType);

        OrderStatusDescDataVector orderStatus = new OrderStatusDescDataVector();
        orderStatus = orderEjb.getOrderStatusDescCollection(searchCriteria,
                storeIds);
        sForm.setResultListAndShowSearchFields(orderStatus, false);
        for (int i = 0; orderStatus != null && i < orderStatus.size(); i++) {
            OrderStatusDescData orderStatusDescD = (OrderStatusDescData)orderStatus.get(i);
            if (Utility.isSet(orderPropertyList)) {
            	OrderPropertyDataVector properties = new OrderPropertyDataVector();
            	Iterator<String> orderPropertyIterator = orderPropertyList.iterator();
            	while (orderPropertyIterator.hasNext()) {
            		String orderProperty = orderPropertyIterator.next();
            		properties.addAll(orderEjb.getOrderPropertyCollection(orderStatusDescD.getOrderDetail().getOrderId(), orderProperty));
            	}
            	orderStatusDescD.setOrderPropertyList(properties);
            }
            else {
            	orderStatusDescD.setOrderPropertyList(orderEjb
                    .getOrderPropertyVec(orderStatusDescD.getOrderDetail().getOrderId()));
            }
        }
        session.setAttribute(ORDER_SEARCH_FORM, sForm);
    }

    public static OrderStatusDescDataVector listPendingOrders(HttpServletRequest request,
    		OrderSearchDto orderSearchDto) throws Exception {
        OrderStatusCriteriaData searchCriteria = OrderStatusCriteriaData.createValue();
        searchCriteria.setRetrieveOrderAccount(orderSearchDto.isRetrieveOrderAccount());
        searchCriteria.setRetrieveOrderAddresses(orderSearchDto.isRetrieveOrderAddresses());
        searchCriteria.setRetrieveOrderHistory(orderSearchDto.isRetrieveOrderHistory());
        searchCriteria.setRetrieveOrderItems(orderSearchDto.isRetrieveOrderItems());
        searchCriteria.setRetrieveOrderMetaData(orderSearchDto.isRetrieveOrderMetaData());
        searchCriteria.setRetrieveOrderReceptionData(orderSearchDto.isRetrieveOrderReceptionData());
        searchCriteria.setRetrieveOrderAutoOrderData(orderSearchDto.isRetrieveOrderAutoOrderData());
        searchCriteria.setRetrieveOrderProperties(orderSearchDto.isRetrieveOrderProperties());
        if (Utility.isSet(orderSearchDto.getOrderStatuses())) {
        	searchCriteria.getOrderStatusList().addAll(orderSearchDto.getOrderStatuses());
        }
        else {
        	searchCriteria.getOrderStatusList().add(RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL);
        	searchCriteria.getOrderStatusList().add(RefCodeNames.ORDER_STATUS_CD.PENDING_CONSOLIDATION);
        	searchCriteria.getOrderStatusList().add(RefCodeNames.ORDER_STATUS_CD.PENDING_DATE);
        }
        searchCriteria.setOrderPropertyList(orderSearchDto.getOrderProperties());
        searchCriteria.setStoreIdVector(ShopTool.getCurrentUser(request).getUserStoreAsIdVector());

        // search order must have userId or/and userTypeCd
        HttpSession session = request.getSession();
        searchCriteria.setUserId(Utility.strNN((String)session.getAttribute(Constants.USER_ID)));
        searchCriteria.setUserTypeCd(Utility.strNN((String)session.getAttribute(Constants.USER_TYPE)));

        OrderStatusDescDataVector orderStatus = APIAccess.getAPIAccess().getOrderAPI().
        	getOrderStatusDescCollection(searchCriteria);
        return orderStatus;
    }
    
    public static void sort(HttpServletRequest request, ActionForm form)
            throws Exception
    {

        OrderSearchForm sForm = (OrderSearchForm)form;

        if (sForm == null)
        {

            return;
        }

        OrderStatusDescDataVector orders = (OrderStatusDescDataVector)sForm.getResultList();

        if (orders == null)
        {

            return;
        }

        String sortField = request.getParameter("sortField");
        boolean desc = false;
        if(sortField != null && sortField.length() > 4 && sortField.endsWith("Desc")){
        	sortField = sortField.substring(0,sortField.length()-4);
        	desc = true;
        }
        DisplayListSort.sort(request, orders, sortField);
        if(desc){
           Collections.reverse(orders);
        }
    }
    
    //STJ-5775
    /**
     * <code>search</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */
    public static ActionErrors search(HttpServletRequest request,
                                      ActionForm form)
                               throws Exception
    {
    	return search(request,form,null);
    }
    
    //STJ-5775
    /**
     * <code>search</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */
    public static ActionErrors search(HttpServletRequest request,
                                      ActionForm form,String previousOrdersSearch)
                               throws Exception
    {

        ActionErrors lUpdateErrors = new ActionErrors();
        HttpSession session = request.getSession();
        OrderSearchForm sForm = (OrderSearchForm)form;
        APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        TimeZone timeZoneInp = null;
        if (appUser != null) {
          timeZoneInp = Utility.getTimeZoneFromUserData(appUser.getSite());
        }

        String reqAction = request.getParameter("action");
        if (null == reqAction) {
          reqAction = "--none--";
        }

        if (null == factory)
        {
            throw new Exception("Without APIAccess.");
        }

        Order orderEjb = factory.getOrderAPI();
        OrderStatusCriteriaData searchCriteria = OrderStatusCriteriaData.createValue();
        IdVector storeIds = appUser.getUserStoreAsIdVector();
        searchCriteria.getExcludeOrderStatusList().add(RefCodeNames.ORDER_STATUS_CD.REFERENCE_ONLY);
        
        if(!Utility.isSet(previousOrdersSearch)) {  //STJ-5775
	        if (sForm.getOrderDateRangeBegin().trim().length() < 1 &&
	            sForm.getOrderDateRangeEnd().trim().length() < 1)
	        {
	            String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.fieldsOrderDateRangeAreEmpty",null);
	            lUpdateErrors.add("ordersearch",new ActionError("error.simpleGenericError",errorMess));
	            return lUpdateErrors;
	        }
        }

        if (sForm.getWebOrderConfirmationNum().trim().length() > 0)
        {
            searchCriteria.setWebOrderConfirmationNum(sForm.getWebOrderConfirmationNum().trim());
        }
        
        SimpleDateFormat sdfDT = new SimpleDateFormat(Constants.SIMPLE_DATE_PATTERN+ " " +Constants.SIMPLE_TIME24_PATTERN);

        if (sForm.getOrderDateRangeBegin().trim().length() > 0)
        {
            String dateBegSInp = sForm.getOrderDateRangeBegin().trim();
            Date dateBeg = null;
            try {
               dateBeg = ClwI18nUtil.parseDateTimeInp(request,dateBegSInp, "00:00:00", timeZoneInp.getID() );

            } catch (Exception exc) {
               Object[] params = new Object[1];
               params[0] = dateBegSInp;
               String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.wrongBeginDate",params);
               lUpdateErrors.add("begDate",new ActionError("error.simpleGenericError",errorMess));
               exc.printStackTrace();
               return(lUpdateErrors);
            }
            String dateBegS = sdfDT.format(dateBeg);
            searchCriteria.setOrderDateRangeBegin(dateBegS);
        }

        if (sForm.getOrderDateRangeEnd().trim().length() > 0)
        {
            String dateEndSInp = sForm.getOrderDateRangeEnd().trim();
            Date dateEnd = null;
            try {
               dateEnd = ClwI18nUtil.parseDateTimeInp(request,dateEndSInp,"23:59:59", timeZoneInp.getID());
             } catch (Exception exc) {
               Object[] params = new Object[1];
               params[0] = dateEndSInp;
               String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.wrongEndDate",params);
               lUpdateErrors.add("endDate",new ActionError("error.simpleGenericError",errorMess));
               exc.printStackTrace();
               return(lUpdateErrors);
            }
            String dateEndS = sdfDT.format(dateEnd);
            searchCriteria.setOrderDateRangeEnd(dateEndS);
        }
        if (sForm.getCustPONum().trim().length() > 0)
        {
            searchCriteria.setCustPONum(sForm.getCustPONum().trim());
        }

        if (sForm.getSiteZipCode().trim().length() > 0)
        {
            searchCriteria.setSiteZipCode(sForm.getSiteZipCode().trim());
        }

        if (sForm.getSiteCity().trim().length() > 0)
        {
            searchCriteria.setSiteCity(sForm.getSiteCity().trim());
        }

        if (sForm.getSiteState().trim().length() > 0)
        {
            searchCriteria.setSiteState(sForm.getSiteState().trim());
        }

        if (Utility.isTrue(sForm.getOrdersNotReceivedOnly())){
            searchCriteria.setOrdersNotReceivedOnly();
        }

        // search order must have userId or/and userTypeCd
        String userId = (String)session.getAttribute(Constants.USER_ID);
        String userType = (String)session.getAttribute(Constants.USER_TYPE);

        if (null == userId){
          String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.noUserId",null);
          lUpdateErrors.add("userId",new ActionError("error.simpleGenericError",errorMess));
          return(lUpdateErrors);
        }

        if (null == userType)
            userType = new String("");

        searchCriteria.setUserId(userId);
        searchCriteria.setUserTypeCd(userType);

        int siteId = appUser.getSite().getBusEntity().getBusEntityId();
     if (!reqAction.equals("search_all_sites") ) {

        Integer sId = new Integer(siteId);
        searchCriteria.setSiteId(sId.toString());
    }

        //get the account the site is part of and add it if the uesr is a crc or admin
        //as otherwise they will be getting data that has no context for them.
        if(appUser.isaAdmin() || appUser.isaCustServiceRep()){
            Account accountBean = factory.getAccountAPI();
            AccountData ad = accountBean.getAccountForSite(siteId);
            Integer acctId = new Integer(ad.getBusEntity().getBusEntityId());
            searchCriteria.setAccountId(acctId.toString());
        }
        
        //STJ-4759
        searchCriteria.setFilterByRevisedOrderDate(sForm.isFilterByRevisedOrderDate());

        OrderStatusDescDataVector orderStatus = orderEjb.getOrderStatusDescCollection(searchCriteria,
                storeIds);

        if(Utility.isSet(previousOrdersSearch)) {//STJ-5775
	        for(int i = 0; i < orderStatus.size(); i++) {
	     		OrderStatusDescData orderStatusDescData = (OrderStatusDescData)orderStatus.get(i);
	     		if(orderStatusDescData.getOrderDetail().getRevisedOrderDate() != null) {
		        		orderStatusDescData.getOrderDetail().setOriginalOrderDate(orderStatusDescData.getOrderDetail().getRevisedOrderDate());
		        		orderStatusDescData.getOrderDetail().setOriginalOrderTime(orderStatusDescData.getOrderDetail().getRevisedOrderTime());
	     		}
	     	}
	        DisplayListSort.sort(request, orderStatus, Constants.ORDERS_SORT_FIELD_ORDER_DATE,Constants.ORDERS_SORT_ORDER_DESCENDING);
	        sForm.setResultListAndShowSearchFields(orderStatus, true);
    	} else {
	        //put greatest order id first
	        DisplayListSort.sort(request, orderStatus, "orderid");
	
	        OrderStatusDescDataVector temp = new OrderStatusDescDataVector();
	
	        for (int j = orderStatus.size() - 1; j >= 0; j--)
	        {
	            temp.add(orderStatus.get(j));
	        }
	
	        orderStatus = temp;
	        sForm.setResultListAndShowSearchFields(orderStatus, true);
    	}

        return lUpdateErrors;
    }

    /**
     * <code>search</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form    an <code>ActionForm</code> value
     * @return errors
     * @throws Exception if an error occurs
     */
    public static ActionErrors searchRejectOrders(HttpServletRequest request, ActionForm form) throws Exception {

        HttpSession session = request.getSession();
        OrderSearchForm sForm = (OrderSearchForm) form;

        if (sForm == null || !sForm.isInit()) {
            ActionErrors ae = initXPEDX(request, sForm);
            if (ae.size() > 0) {
                return ae;
            }
        }

        sForm = (OrderSearchForm) session.getAttribute(ORDER_SEARCH_FORM);
        ActionErrors ae = checkRequest(request, sForm);
        if (ae.size() > 0) {
            return ae;
        }

        if (sForm != null) {
            sForm.setOrderStatus(RefCodeNames.ORDER_STATUS_CD.REJECTED);
        }

        return searchXPEDXOrders(request, sForm);

    }


    /**
     * <code>search</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form    an <code>ActionForm</code> value
     * @return errors
     * @throws Exception if an error occurs
     */
    public static ActionErrors searchApproveOrders(HttpServletRequest request, ActionForm form) throws Exception {

        HttpSession session = request.getSession();
        OrderSearchForm sForm = (OrderSearchForm) form;
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        TimeZone timeZoneInp = Utility.getTimeZoneFromUserData(appUser.getSite());

        if (sForm == null || !sForm.isInit()) {
            ActionErrors ae = initXPEDX(request, sForm);
            if (ae.size() > 0) {
                return ae;
            }
        }

        sForm = (OrderSearchForm) session.getAttribute(ORDER_SEARCH_FORM);
        ActionErrors ae = checkRequest(request, sForm);
        if (ae.size() > 0) {
            return ae;
        }

        if (sForm != null) {
            sForm.setOrderStatus(RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL);
            sForm.setSiteSiteId(EMPTY);

            Date currentDate = new Date();
            Calendar gCalendar = Calendar.getInstance();
            gCalendar.setTime(currentDate);

            // By default, list the orders for the last 3 months.
            int smonth = gCalendar.get(Calendar.MONTH) - 3;
            gCalendar.set(Calendar.MONTH, smonth);

            Date orderBeginDate = gCalendar.getTime();

            sForm.setOrderDateRangeBegin(ClwI18nUtil.formatDateInp(request, orderBeginDate, timeZoneInp.getID()));
            sForm.setOrderDateRangeEnd(ClwI18nUtil.formatDateInp(request, currentDate, timeZoneInp.getID()));
        }

        return searchXPEDXOrders(request, sForm);

    }
    /**
     * <code>search</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form    an <code>ActionForm</code> value
     * @return errors
     * @throws Exception if an error occurs
     */
    public static ActionErrors searchXPEDXOrders(HttpServletRequest request, ActionForm form) throws Exception {

        ActionErrors ae = checkRequest(request, form);
        if (ae.size() > 0) {
            return ae;
        }

        HttpSession session = request.getSession();
        OrderSearchForm sForm = (OrderSearchForm) form;
        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        TimeZone timeZoneInp = Utility.getTimeZoneFromUserData(appUser.getSite());

        if (!Utility.isSet(sForm.getOrderDateRangeEnd())) {
            Date currentDate = new Date();
            sForm.setOrderDateRangeEnd(ClwI18nUtil.formatDateInp(request, currentDate, timeZoneInp.getID()));
        }


        ae = checkFormAttribute(request, sForm);
        if (ae.size() > 0) {
            sForm.setResultListAndShowSearchFields(new OrderStatusDescDataVector(), true);
            return ae;
        }
        //first request
        if("true".equals(request.getParameter("bkdte"))){
        	sForm.setOrderDateRangeBegin(null);
        	sForm.setOrderDateRangeEnd(null);
        }

        Order orderEjb = factory.getOrderAPI();

        sForm.setConfirmationMessage(EMPTY);
        OrderStatusCriteriaData searchCriteria = OrderStatusCriteriaData.createValue();
        searchCriteria.getExcludeOrderStatusList().add(RefCodeNames.ORDER_STATUS_CD.REFERENCE_ONLY);
        searchCriteria.setNewXpedex();
        
        SimpleDateFormat sdfDT = new SimpleDateFormat(Constants.SIMPLE_DATE_PATTERN + " " + Constants.SIMPLE_TIME24_PATTERN);

        if (Utility.isSet(sForm.getOrderDateRangeBegin())) {
            String dateBegSInp = sForm.getOrderDateRangeBegin().trim();
            Date dateBeg = ClwI18nUtil.parseDateTimeInp(request, dateBegSInp, "23:59:59", timeZoneInp.getID());
            String dateBegS = sdfDT.format(dateBeg);
            searchCriteria.setOrderDateRangeBegin(dateBegS);
        }else{
        	String begD = "01/01/1900";
        	Date dateBegin = ClwI18nUtil.parseDateTimeInp(request, begD, "23:59:59", timeZoneInp.getID());

            String dateBegS = sdfDT.format(dateBegin);
            searchCriteria.setOrderDateRangeBegin(dateBegS);
        }

        if (Utility.isSet(sForm.getOrderDateRangeEnd())) {
            String dateEndSInp = sForm.getOrderDateRangeEnd().trim();
            Date dateEnd = ClwI18nUtil.parseDateTimeInp(request, dateEndSInp, "23:59:59", timeZoneInp.getID());
            String dateEndS = sdfDT.format(dateEnd);
            searchCriteria.setOrderDateRangeEnd(dateEndS);
        }

        if (sForm.getWebOrderConfirmationNum().trim().length() > 0) {
            searchCriteria.setWebOrderConfirmationNum(sForm.getWebOrderConfirmationNum().trim());
        }

        if (sForm.getCustPONum().trim().length() > 0) {
            searchCriteria.setCustPONum(sForm.getCustPONum().trim());
        }

        if (sForm.getSiteZipCode().trim().length() > 0) {
            searchCriteria.setSiteZipCode(sForm.getSiteZipCode().trim());
        }

        if (sForm.getSiteCity().trim().length() > 0) {
            searchCriteria.setSiteCity(sForm.getSiteCity().trim());
        }

        if (sForm.getSiteState().trim().length() > 0) {
            searchCriteria.setSiteState(sForm.getSiteState().trim());
        }

        if (Utility.isTrue(sForm.getOrdersNotReceivedOnly())) {
            searchCriteria.setOrdersNotReceivedOnly();
        }

        if (Utility.isSet(sForm.getSiteCountry())) {
            searchCriteria.setSiteCountry(sForm.getSiteCountry());
        }

        if (Utility.isSet(sForm.getOrderStatus())) {

            ArrayList statuslist = new ArrayList();
            if (Constants.ORDER_STATUS_ALL.equals(sForm.getOrderStatus()) && sForm.getStatusValueList() != null) {
                /*Iterator it = sForm.getStatusValueList().iterator();
                while (it.hasNext()) {
                    statuslist.addAll(ShopTool.rxlateOrderStatusXpedx((String) it.next()));
                }*/
            } else {
                statuslist.addAll(ShopTool.rxlateOrderStatusXpedx(sForm.getOrderStatus()));
            }
            searchCriteria.getOrderStatusList().addAll(statuslist);
        }

        // search order must have userId or/and userTypeCd
        String userId = String.valueOf(appUser.getUser().getUserId());
        String userType = Utility.strNN((String) session.getAttribute(Constants.USER_TYPE));

        searchCriteria.setUserId(userId);
        searchCriteria.setUserTypeCd(userType);

        int siteId = appUser.getSite().getBusEntity().getBusEntityId();
        searchCriteria.setSiteId(sForm.getSiteSiteId());

        //get the account the site is part of and add it if the uesr is a crc or admin
        //as otherwise they will be getting data that has no context for them.
        if (appUser.isaAdmin() || appUser.isaCustServiceRep()) {
            Account accountBean = factory.getAccountAPI();
            AccountData ad = accountBean.getAccountForSite(siteId);
            Integer acctId = new Integer(ad.getBusEntity().getBusEntityId());
            searchCriteria.setAccountId(acctId.toString());
        }

        OrderStatusDescDataVector orderStatus;
        //orderStatus = orderEjb.getOrderStatusDescCollection(searchCriteria);
        //get a light weight object
        orderStatus = orderEjb.getLightOrderStatusDescCollection(searchCriteria);

        //put greatest order id first
        DisplayListSort.sort(request, orderStatus, "orderid");

        OrderStatusDescDataVector temp = new OrderStatusDescDataVector();

        //reverse the list
        for (int j = orderStatus.size() - 1; j >= 0; j--) {
        	OrderStatusDescData desc = (OrderStatusDescData) orderStatus.get(j);
        	if(RefCodeNames.ORDER_STATUS_CD.SHIPPED.equals(sForm.getOrderStatus())){
        		if(desc.isShipped() || RefCodeNames.ORDER_STATUS_CD.INVOICED.equals(desc.getOrderDetail().getOrderStatusCd())){
        			temp.add(desc);
        		}
        	}else if(RefCodeNames.ORDER_STATUS_CD.ORDERED_PROCESSING.equals(sForm.getOrderStatus())){
        		if(!desc.isShipped()){
        			temp.add(desc);
        		}
        	}else{
        		temp.add(desc);
        	}
        }

        orderStatus = temp;

        if (orderStatus.isEmpty()) {
            String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.noResultsFound", null);
            //ae.add("begDate", new ActionError("error.simpleGenericError", errorMess));
            sForm.setConfirmationMessage(errorMess);
            sForm.setResultListAndShowSearchFields(orderStatus, true);
            return ae;
        }

        //sort to have latest order first
        DisplayListSort.sort(request, orderStatus, "orderdateDesc");
        sForm.setResultListAndShowSearchFields(orderStatus, true);

        session.setAttribute(ORDER_SEARCH_FORM, sForm);

        return ae;
    }

    private static ActionErrors checkFormAttribute(HttpServletRequest request, OrderSearchForm sForm) {

        ActionErrors errors = new ActionErrors();
        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        TimeZone timeZoneInp = Utility.getTimeZoneFromUserData(appUser.getSite());

        Date dateBeg = null;
        Date dateEnd = null;
        
        if (Utility.isSet(sForm.getOrderDateRangeBegin())) {
            String dateBegSInp = sForm.getOrderDateRangeBegin().trim();
            try {
                dateBeg = ClwI18nUtil.parseDateTimeInp(request, dateBegSInp, "00:00:00", timeZoneInp.getID()); 
            } catch (Exception exc) {
            	sForm.setOrderDateRangeBegin(dateBegSInp);
                String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.wrongBeginDate", new Object[]{dateBegSInp});
                if(errors.size()==0){
                	String errorM = ClwI18nUtil.getMessage(request,"shop.errors.invalidSearchCriteriaStack",null);
            		errors.add("invalidSearchCriteria", new ActionError("error.simpleGenericError", errorM));
                }
                errors.add("invalidSearchCriteria", new ActionError("error.simpleGenericError", errorMess));

            }
        }
        
        if (Utility.isSet(sForm.getOrderDateRangeEnd())) {
            String dateEndSInp = sForm.getOrderDateRangeEnd().trim();
            try {
                dateEnd = ClwI18nUtil.parseDateTimeInp(request, dateEndSInp, "23:59:59", timeZoneInp.getID());                
            } catch (Exception exc) {
            	sForm.setOrderDateRangeEnd(dateEndSInp);
                String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.wrongEndDate", new Object[]{dateEndSInp});
                if(errors.size()==0){
                	String errorM = ClwI18nUtil.getMessage(request,"shop.errors.invalidSearchCriteriaStack",null);
            		errors.add("invalidSearchCriteria", new ActionError("error.simpleGenericError", errorM));
                }
                errors.add("invalidSearchCriteria", new ActionError("error.simpleGenericError", errorMess));

            }
        }
        if (dateBeg != null){
        	Calendar curentCal = Calendar.getInstance();
            Calendar begCal = Calendar.getInstance();

            begCal.setTime(dateBeg);
            curentCal.setTime(new Date());

            if (begCal.after(curentCal)) {
                String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.startDateGreaterThanCurrentDate", null);
                if(errors.size()==0){
                	String errorM = ClwI18nUtil.getMessage(request,"shop.errors.invalidSearchCriteriaStack",null);
            		errors.add("invalidSearchCriteria", new ActionError("error.simpleGenericError", errorM));
                }
                errors.add("invalidSearchCriteria", new ActionError("error.simpleGenericError", errorMess));
            }
            if (dateEnd!=null){
            	Calendar endCal = Calendar.getInstance();

                begCal.setTime(dateBeg);
                endCal.setTime(dateEnd);

                if (endCal.before(begCal)) {
                    String errorMess  = ClwI18nUtil.getMessage(request, "shop.errors.startDateGreaterThanEndDate", null);
                    if(errors.size()==0){
                    	String errorM = ClwI18nUtil.getMessage(request,"shop.errors.invalidSearchCriteriaStack",null);
                		errors.add("invalidSearchCriteria", new ActionError("error.simpleGenericError", errorM));
                    }
                    errors.add("invalidSearchCriteria", new ActionError("error.simpleGenericError", errorMess));
                }
            }
        }

        return errors;
    }

    public static ActionErrors initXPEDX(HttpServletRequest request, ActionForm form) throws Exception {
        HttpSession session = request.getSession();
        OrderSearchForm sForm = new OrderSearchForm();

        ActionErrors ae = checkRequest(request, form);
        if (ae.size() > 0) {
            return ae;
        }

        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        TimeZone timeZoneInp = Utility.getTimeZoneFromUserData(appUser.getSite());

        Country countryEjb = factory.getCountryAPI();
        Site siteEjb = factory.getSiteAPI();

        int siteId = appUser.getSite().getSiteId();
        int userId = appUser.getUserId();

        sForm.init();
        List statusValueList = Arrays.asList(Constants.xpedxShoppingOrderStatusList);

        Object[] countryAndStateLinks = countryEjb.getCountryAndValidStateLinks(userId);

        List countryValList = Arrays.asList(((HashMap)countryAndStateLinks[0]).keySet().toArray());
        Collections.sort(countryValList);

        List stateValList = Arrays.asList(((HashMap)countryAndStateLinks[1]).keySet().toArray());
        Collections.sort(stateValList);

        sForm.setSiteCity(EMPTY);
        sForm.setSiteState(EMPTY);
        sForm.setSiteZipCode(EMPTY);
        sForm.setCustPONum(EMPTY);
        sForm.setWebOrderConfirmationNum(EMPTY);
        sForm.setSiteCountry(EMPTY);
        sForm.setSiteSiteId(EMPTY);
        sForm.setOrderStatus(EMPTY);
        sForm.setCountryAndStateLinks(countryAndStateLinks);

        sForm.setStateValueList(stateValList);
        sForm.setSiteValuePairs(new PairViewVector());
        sForm.setCountryValueList(countryValList);
        sForm.setStatusValueList(statusValueList);

        sForm.setSiteSiteId(String.valueOf(siteId));

        PairViewVector siteValPairs = siteEjb.getSiteIdAndName(userId,0,null,null,RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE,2);
        IdVector siteIds = getObject1AsId(siteValPairs);
        if (siteIds == null ||
                siteIds.isEmpty() ||
                (siteIds.size() == 1 && ((Integer) siteIds.get(0)).intValue() == siteId)) {
            sForm.setShowLocation(false);
        } else {
            sForm.setShowLocation(true);
        }

        OrderStatusCriteriaData searchCriteria = OrderStatusCriteriaData.createValue();
        searchCriteria.getExcludeOrderStatusList().add(RefCodeNames.ORDER_STATUS_CD.REFERENCE_ONLY);

        Date currentDate = new Date();
        Calendar gCalendar = Calendar.getInstance();
        gCalendar.setTime(currentDate);

        // By default, list the orders for the last 3 months.
        int smonth = gCalendar.get(Calendar.MONTH) - 3;
        gCalendar.set(Calendar.MONTH, smonth);

        Date orderBeginDate = gCalendar.getTime();

        sForm.setOrderDateRangeBegin(ClwI18nUtil.formatDateInp(request, orderBeginDate, timeZoneInp.getID()));
        sForm.setOrderDateRangeEnd(ClwI18nUtil.formatDateInp(request, currentDate, timeZoneInp.getID()));

        session.setAttribute(ORDER_SEARCH_FORM, sForm);

        return ae;
    }

    public static ActionErrors initXPEDXListOrders(HttpServletRequest request, ActionForm form) throws Exception {
        HttpSession session = request.getSession();
        OrderSearchForm sForm = (OrderSearchForm) form;

        ActionErrors ae = initXPEDX(request, sForm);
        if (ae.size() > 0) {
            return ae;
        }

        String orderStatusVal = request.getParameter("orderStatus");

        sForm = (OrderSearchForm) session.getAttribute(ORDER_SEARCH_FORM);
        sForm.setOrderStatus(Utility.strNN(orderStatusVal));
        if (RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL.equals(orderStatusVal)) {
            sForm.setSiteSiteId(EMPTY);
        }

        ae = searchXPEDXOrders(request, sForm);

        return ae;
    }


    private static ActionErrors checkRequest(HttpServletRequest request, ActionForm form) throws Exception {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        APIAccess factory = (APIAccess) request.getSession().getAttribute(Constants.APIACCESS);

        if (form == null) {
            ae.add("error", new ActionError("error.systemError", "Form not initialized"));
            return ae;
        }

        if (factory == null) {
            ae.add("error", new ActionError("error.systemError", "No Ejb access"));
            return ae;
        }

        if (appUser == null) {
            ae.add("error", new ActionError("error.systemError", "No clw user info"));
            return ae;
        }

        if (appUser.getUser() == null) {
            ae.add("error", new ActionError("error.systemError", "No user info"));
            return ae;
        }

        if (appUser.getUserStore() == null) {
            ae.add("error", new ActionError("error.systemError", "No store info"));
            return ae;
        }

        if (appUser.getUserAccount() == null) {
            ae.add("error", new ActionError("error.systemError", "No account info"));
            return ae;
        }

        if (appUser.getSite() == null) {
            ae.add("error", new ActionError("error.systemError", "No site info"));
            return ae;
        }

        return ae;

    }

    private static IdVector getObject1AsId(PairViewVector sites) {
        IdVector ids = new IdVector();
        if (sites != null) {
            for (Object oSite : sites) {
                PairView site = (PairView) oSite;
                ids.add(site.getObject1());
            }
        }
        return ids;
    }

    /**
     * Gets sites for criteria of search of the order
     *
     * @param storeId   store identifier
     * @param accountId account identifier
     * @param userId    user identifier
     * @param siteId    site identifier(optional)
     * @param country   country(optional)
     * @param state     state(optional)
     * @return SiteView objects
     * @throws Exception if an exception
     */
    public static SiteViewVector getSiteList(int storeId,
                                             int accountId,
                                             int userId,
                                             int siteId,
                                             String country,
                                             String state) throws Exception {


        Site siteEjb = APIAccess.getAPIAccess().getSiteAPI();

        QueryRequest req = new QueryRequest();

        req.filterByUserId(userId);
        req.filterBySiteStatusCd(RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);
        req.orderBySiteName(true);
        req.filterByStoreIds(Utility.toIdVector(storeId));
        req.filterByAccountId(accountId);

        if (siteId > 0) {
            req.filterBySiteId(siteId);
        }

        if (Utility.isSet(country)) {
            req.filterByCountry(country, QueryRequest.BEGINS_IGNORE_CASE);
        }

        if (Utility.isSet(state)) {
            req.filterByState(state, QueryRequest.BEGINS_IGNORE_CASE);
        }

        return siteEjb.getSiteCollection(req);

    }

    public static ActionErrors changeCountry(HttpServletRequest request, ActionForm form, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        OrderSearchForm sForm = (OrderSearchForm) form;

        ActionErrors ae = checkRequest(request, form);
        if (ae.size() > 0) {
            return ae;
        }

        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        Site siteEjb = factory.getSiteAPI();

        int userId    = appUser.getUser().getUserId();

        String newCountry = request.getParameter("newCountry");

        List stateValueList;
        if (Utility.isSet(newCountry)) {
            stateValueList = Arrays.asList(((HashSet) ((HashMap) sForm.getCountryAndStateLinks()[0]).get(newCountry)).toArray());
        } else {
            stateValueList = Arrays.asList(((HashMap) sForm.getCountryAndStateLinks()[1]).keySet().toArray());
        }
        Collections.sort(stateValueList);

         if (Utility.isSet(sForm.getSiteState()) && !stateValueList.contains(sForm.getSiteState())) {
            sForm.setSiteState(EMPTY);
            sForm.setSiteSiteId(EMPTY);
        }

        PairViewVector siteValPairs;
        if (Utility.isSet(sForm.getSiteState()) || stateValueList.size() == 0) {
            siteValPairs = siteEjb.getSiteIdAndName(userId, 0, newCountry, sForm.getSiteState(), RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE, 0);
        } else {
            siteValPairs = new PairViewVector();
        }

        IdVector siteIds = getObject1AsId(siteValPairs);
        if (Utility.isSet(sForm.getSiteSiteId()) && !siteIds.contains(new Integer(sForm.getSiteSiteId()))) {
            sForm.setSiteSiteId(EMPTY);
        }

        sForm.setSiteCountry(newCountry);
        sForm.setStateValueList(stateValueList);
        sForm.setSiteValuePairs(siteValPairs);

        String responseStr = prepareLocationCriteriaResJson(sForm,false,true,true);

        response.setContentType("json-comment-filtered");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(responseStr);

        session.setAttribute(ORDER_SEARCH_FORM, sForm);

        return ae;
    }

    public static ActionErrors changeState(HttpServletRequest request, ActionForm form, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        OrderSearchForm sForm = (OrderSearchForm) form;

        ActionErrors ae = checkRequest(request, form);
        if (ae.size() > 0) {
            return ae;
        }

        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        Site siteEjb = factory.getSiteAPI();

        int userId    = appUser.getUser().getUserId();

        String newState = request.getParameter("newState");

        PairViewVector siteValPairs;
        if (Utility.isSet(newState) || sForm.getStateValueList().size() == 0) {
            siteValPairs = siteEjb.getSiteIdAndName(userId, 0, sForm.getSiteCountry(), newState, RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE, 0);
        } else {
            siteValPairs = new PairViewVector();
        }

        IdVector siteIds = getObject1AsId(siteValPairs);
        if (Utility.isSet(sForm.getSiteSiteId()) && !siteIds.contains(new Integer(sForm.getSiteSiteId()))) {
            sForm.setSiteSiteId(EMPTY);
        }

        sForm.setSiteState(newState);
        sForm.setSiteValuePairs(siteValPairs);

        String responseStr = prepareLocationCriteriaResJson(sForm,false,false,true);

        response.setContentType("json-comment-filtered");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(responseStr);

        session.setAttribute(ORDER_SEARCH_FORM, sForm);

        return ae;
    }


    public static ActionErrors changeSite(HttpServletRequest request, ActionForm form, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        OrderSearchForm sForm = (OrderSearchForm) form;

        ActionErrors ae = checkRequest(request, form);
        if (ae.size() > 0) {
            return ae;
        }

        String newSite = request.getParameter("newSite");

        if (Utility.isSet(newSite)) {
            try {
                Integer.parseInt(newSite);
                sForm.setSiteSiteId(newSite);
            } catch (NumberFormatException e) {
                sForm.setSiteSiteId(EMPTY);
                e.printStackTrace();
            }
        } else {
            sForm.setSiteSiteId(EMPTY);
        }

        String responseStr = prepareLocationCriteriaResJson(sForm,false,false,false);

        response.setContentType("json-comment-filtered");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(responseStr);

        session.setAttribute(ORDER_SEARCH_FORM, sForm);

        return ae;
    }

    private static String prepareLocationCriteriaResJson(OrderSearchForm sForm,
                                                         boolean redrawCountries,
                                                         boolean redrawStates,
                                                         boolean redrawSites) {

        StringBuffer responseJson = new StringBuffer();

        responseJson.append("{\"response\":{");

        {
            appendSiteCriteria(responseJson, sForm.getSiteValuePairs(), sForm.getSiteSiteId(), redrawSites);

            responseJson.append(",");
            appendStateCriteria(responseJson, sForm.getStateValueList(), sForm.getSiteState(), redrawStates);

            responseJson.append(",");
            appendCountryCriteria(responseJson, sForm.getCountryValueList(), sForm.getSiteCountry(), redrawCountries);
        }

        responseJson.append("}}");

        return responseJson.toString();

    }

    private static void appendSiteCriteria(StringBuffer responseJson, PairViewVector siteValuePairs, String currentValue, boolean redraw) {

        StringBuffer sb = new StringBuffer();

        sb.append("\"sites\":");

        sb.append("{\"redraw\":");
        sb.append(Boolean.toString(redraw));

        if (redraw && siteValuePairs != null && !siteValuePairs.isEmpty()) {

            sb.append(",\"selected\":");
            sb.append("\"");
            sb.append(Utility.strNN(currentValue));
            sb.append("\"");
            sb.append(",");

            sb.append("\"list\":[");
            int i = 0;
            for (Object siteValuePair : siteValuePairs) {
                PairView psiteVal = (PairView) siteValuePair;
                if (i > 0) {
                    sb.append(",");
                }
                sb.append("{\"id\":");
                sb.append(psiteVal.getObject1().toString());
                sb.append(",\"val\":\"");
                sb.append(psiteVal.getObject2().toString());
                sb.append("\"}");
                i++;
            }
            sb.append("]");
        }

        sb.append("}");

        responseJson.append(sb);
    }


    private static void appendStateCriteria(StringBuffer responseJson, List stateValueList, String currentValue, boolean redraw) {

        StringBuffer sb = new StringBuffer();

        sb.append("\"states\":");

        sb.append("{\"redraw\":");
        sb.append(Boolean.toString(redraw));

        if (redraw && stateValueList != null && !stateValueList.isEmpty()) {

            sb.append(",\"selected\":");
            sb.append("\"");
            sb.append(Utility.strNN(currentValue));
            sb.append("\"");
            sb.append(",");

            sb.append("\"list\":[");
            int i = 0;
            for (Object oState : stateValueList) {
                String state = (String) oState;
                if (i > 0) {
                    sb.append(",");
                }
                sb.append("{\"val\":");
                sb.append("\"");
                sb.append(state);
                sb.append("\"}");
                i++;
            }
            sb.append("]");

        }

        sb.append("}");

        responseJson.append(sb);
    }

    private static void appendCountryCriteria(StringBuffer responseJson,
                                              List countryValueList,
                                              String currentValue,
                                              boolean redraw) {

        StringBuffer sb = new StringBuffer();

        sb.append("\"countries\":");

        sb.append("{\"redraw\":");
        sb.append(Boolean.toString(redraw));

        if (redraw && countryValueList != null && !countryValueList.isEmpty()) {

            sb.append(",\"selected\":");
            sb.append("\"");
            sb.append(Utility.strNN(currentValue));
            sb.append("\"");
            sb.append(",");

            sb.append("\"list\":[");
            int i = 0;
            for (Object oCountry : countryValueList) {
                String country = (String) oCountry;
                if (i > 0) {
                    sb.append(",");
                }
                sb.append("{\"val\":");
                sb.append("\"");
                sb.append(country);
                sb.append("\"}");
                i++;
            }
            sb.append("]");
        }

        sb.append("}");

        responseJson.append(sb);
    }
    
    private final static String REJECT = "Reject";
    private final static String APPROVE = "Approve";
    private final static String APPROVE_ON = "ApproveOn";

    public static ActionErrors checkSubAction(HttpServletRequest request,
            ActionForm form, MessageResources mr) {
        String subAction = request.getParameter("subAction");
        ActionErrors ae = new ActionErrors();
        if (REJECT.equals(subAction) || APPROVE.equals(subAction)
                || APPROVE_ON.equals(subAction)) {
            OrderSearchForm sForm = (OrderSearchForm) form;
            if (sForm.getSelectIds() == null
                    || sForm.getSelectIds().length == 0) {
                String errorMess = ClwI18nUtil.getMessage(request,
                        "shop.orderStatus.text.noOrdersSelected", null);
                ae.add("error", new ActionError("error.simpleGenericError",
                        errorMess));
            }
            if (ae.size() == 0) {
                try {
                    if (REJECT.equals(subAction) == true) {
                        ae = rejectOrders(request, sForm, mr);
                    } else if (APPROVE.equals(subAction)) {
                        ae = approveOrders(request, sForm, new Date());
                    } else if (APPROVE_ON.equals(subAction)) {
                        Date processDate = parseApproveDate(request, ae);
                        if (ae.size() == 0) {
                            ae = approveOrders(request, sForm, processDate);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return ae;
    }

    private static Date parseApproveDate(HttpServletRequest request,
            ActionErrors ae) {
        String pattern = ClwI18nUtil.getDatePattern(request);
        SimpleDateFormat sdfInp = new SimpleDateFormat(pattern);
        String dateS = request.getParameter("approveDate");
        Date processDate = null;
        if (Utility.isSet(dateS)) {
            try {
                processDate = sdfInp.parse(dateS);
            } catch (Exception exc) {
            }
            if ((processDate == null)
                    || (!sdfInp.format(processDate).equals(dateS))) {
                Object[] params = new Object[1];
                params[0] = dateS;
                String errorMess = ClwI18nUtil.getMessage(request,
                        "shop.errors.wrongDateFormat", params);
                ae.add("error", new ActionError("error.simpleGenericError",
                        errorMess));
                return null;
            }
            SimpleDateFormat sdfUs = new SimpleDateFormat("MM/dd/yyyy");
            Date currDate = new Date();
            try {
                currDate = sdfUs.parse(sdfUs.format(currDate));
            } catch (Exception e) {
                e.printStackTrace();
            }
            int compare = processDate.compareTo(currDate);
            if (compare < 0) {
                String errorMess = ClwI18nUtil.getMessage(request,
                        "shop.errors.dateCan'tBeBeforeCurrentDate", null);
                ae.add("error", new ActionError("error.simpleGenericError",
                        errorMess));
                return null;
            } else {
                return processDate;
            }
        } else {
            String errorMess = ClwI18nUtil.getMessage(request,
                    "shop.errors.enterValidDate", null);
            ae.add("error", new ActionError("error.simpleGenericError",
                    errorMess));
            return null;
        }
    }

    public static ActionErrors rejectOrders(HttpServletRequest request,
            ActionForm form, MessageResources mr) {
        OrderSearchForm searchForm = (OrderSearchForm) form;
        ActionErrors ae = new ActionErrors();
        IdVector wrongOrderIds = new IdVector();
        for (int i = 0; searchForm.getSelectIds() != null
                && i < searchForm.getSelectIds().length; i++) {
            int orderId = Utility.parseInt(searchForm.getSelectIds()[i]);
            try {
                ae.add(HandleOrderLogic.rejectOrder(request, orderId, mr));
            } catch (Exception e) {
                e.printStackTrace();
                wrongOrderIds.add(orderId);
            }
        }
        if (wrongOrderIds.size() > 0) {
            putWrongOrdersMessage(request, ae, wrongOrderIds, searchForm
                    .getResultList());
        }
        return ae;
    }

    public static ActionErrors approveOrders(HttpServletRequest request,
            ActionForm form, Date processDate) throws Exception {
        OrderSearchForm searchForm = (OrderSearchForm) form;
        ActionErrors ae = new ActionErrors();
        IdVector wrongOrderIds = new IdVector();
        for (int i = 0; searchForm.getSelectIds() != null
                && i < searchForm.getSelectIds().length; i++) {
            int orderId = Utility.parseInt(searchForm.getSelectIds()[i]);
            try {
                ActionErrors errors = approveOrder(request, orderId, processDate);
                if (errors != null && !errors.isEmpty()) {
                	ae.add(errors);
                }
            } catch (Exception e) {
                e.printStackTrace();
                wrongOrderIds.add(orderId);
            }
        }
        if (wrongOrderIds.size() > 0) {
            putWrongOrdersMessage(request, ae, wrongOrderIds, searchForm
                    .getResultList());
        }
        return ae;
    }

    private static void putWrongOrdersMessage(HttpServletRequest request,
            ActionErrors ae, IdVector wrongOrderIds,
            OrderStatusDescDataVector orders) {
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; orders != null && i < orders.size(); i++) {
            OrderStatusDescData osdd = (OrderStatusDescData) orders.get(i);
            if (wrongOrderIds.contains(osdd.getOrderDetail().getOrderId())) {
                if (buffer.length() > 0) {
                    buffer.append(",");
                }
                buffer.append(osdd.getOrderDetail().getOrderNum());
            }
        }
        String errorMess = ClwI18nUtil.getMessage(request,
                "shop.errors.errorProcessingOrders", new String[] { buffer
                        .toString() });
        ae.add("orderApproveError", new ActionError("error.simpleGenericError",
                errorMess));
    }

    private static ActionErrors approveOrder(HttpServletRequest request, int pOrderId,
            Date processDate) throws Exception {
    	ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        APIAccess factory = new APIAccess();
        Order orderBean = factory.getOrderAPI();
        OrderJoinData orderJD = orderBean.fetchOrder(pOrderId);
        LogOnLogic.siteShop(request, orderJD.getOrder().getSiteId());
        CleanwiseUser appUser = (CleanwiseUser) session
                .getAttribute(Constants.APP_USER);
        //determine if there are any reasons for the order being in a pending approval state
        //that the user is not authorized to approve
        IdVector notesUserApproveIdV = orderBean.getPropertiesUserCanApprove(
        		appUser.getUser(), orderJD.getOrderId());
        OrderPropertyDataVector opDV = orderJD.getOrderProperties(
                "Workflow Note",
                RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE,
                RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
        IdVector opIdV = new IdVector();
        OrderPropertyDataVector notApprovedNotes = new OrderPropertyDataVector();
        for (Iterator iter = opDV.iterator(); iter.hasNext();) {
            OrderPropertyData opD = (OrderPropertyData) iter.next();
            Integer opIdI = new Integer(opD.getOrderPropertyId());
            if (notesUserApproveIdV.contains(opIdI)) {
            	opIdV.add(opIdI);
            } 
            else {
            	if (opD.getApproveDate()==null) {
            		notApprovedNotes.add(opD);
            	}
            }
        }
        //if there are reasons the user cannot approve return an error
        if (notApprovedNotes.size()>0) {
            String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.someNotesAreNotCleared",null);
            ae.add("error",new ActionError("error.simpleGenericError",errorMess));
            int ind=0;
            for(Iterator iter=notApprovedNotes.iterator(); iter.hasNext();ind++) {
                   OrderPropertyData opD = (OrderPropertyData) iter.next();
               Object[] params = new Object[1];
               params[0] = opD.getValue();
               errorMess = ClwI18nUtil.getMessage(request,"shop.errors.noteIsNotCleared",params);
               ae.add("error"+ind,new ActionError("error.simpleGenericError",errorMess));
            }
        }
        //otherwise approve the order
        else {
        	AccountData accountD = appUser.getUserAccount();
        	SiteData siteD = appUser.getSite();
        	String poNum = orderJD.getOrder().getRequestPoNum();
        	orderJD = orderBean.approveOrder(pOrderId, opIdV, processDate, poNum,
        			appUser.getUser().getUserId(), appUser.getUser().getUserName());
        	factory.getIntegrationServicesAPI().updateJanitorsCloset(orderJD, true);
        	factory.getSiteAPI().updateBudgetSpendingInfo(siteD);
        }
        
        return ae;
    }
}
