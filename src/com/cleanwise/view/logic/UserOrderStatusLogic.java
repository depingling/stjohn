package com.cleanwise.view.logic;

import com.cleanwise.service.api.*;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.util.*;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.service.api.value.*;

import com.cleanwise.view.forms.UserOrderStatusForm;
import com.cleanwise.view.utils.*;

import java.text.SimpleDateFormat;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.w3c.dom.*;
import javax.xml.parsers.*;


/**
 *  <code>UserPendingOrdersLogic</code> implements the logic
 *  to manipulate Orders which require approval or rejection.
 *
 *@author     durval
 *@created    January 7, 2002
 */
public class UserOrderStatusLogic
{
    private static String className = "UserOrderStatusLogic";

    private static final String EMPTY = "";
    private static final String USER_ORDER_STATUS_FORM = "USER_ORDER_STATUS_FORM";

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

        //UserOrderStatusForm sForm = (UserOrderStatusForm)form;
        UserOrderStatusForm sForm = new UserOrderStatusForm();
        APIAccess factory = (APIAccess)session.getAttribute(
                                    Constants.APIACCESS);

        if (null == factory)
        {
            throw new Exception("Without APIAccess.");
        }

        Order orderEjb = factory.getOrderAPI();
        User userBean = factory.getUserAPI();
        IdVector storeIds = appUser.getUserStoreAsIdVector();
        OrderStatusCriteriaData searchCriteria = OrderStatusCriteriaData.createValue();
        searchCriteria.getExcludeOrderStatusList().add(RefCodeNames.ORDER_STATUS_CD.REFERENCE_ONLY);
        Date currentDate = new Date();
        Calendar gCalendar = Calendar.getInstance();
        gCalendar.setTime(currentDate);

        // By default, list the orders for the last 3 months.
        int smonth = gCalendar.get(Calendar.MONTH) - 3;
        gCalendar.set(Calendar.MONTH, smonth);

        Date orderBeginDate = gCalendar.getTime();
//        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
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

        //if user is a customer and an approver, disregard the site id
        // we don't want to limit thier access to approving orders
        UserData ud = userBean.getUser(Integer.parseInt(userId));
        String code = ud.getWorkflowRoleCd();

//            CleanwiseUser appUser = (CleanwiseUser)session.getAttribute( "ApplicationUser");
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
        orderStatus = orderEjb.getOrderStatusDescCollection(searchCriteria, storeIds);
        sForm.setResultList(orderStatus);

        session.setAttribute(USER_ORDER_STATUS_FORM, sForm);

        return;
    }

    public static void sort(HttpServletRequest request, ActionForm form)
            throws Exception
    {

        HttpSession session = request.getSession();
        UserOrderStatusForm sForm = (UserOrderStatusForm)form;

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

        ActionErrors lUpdateErrors = new ActionErrors();
        HttpSession session = request.getSession();
        UserOrderStatusForm sForm = (UserOrderStatusForm)form;
        APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
        CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);
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
        User userBean = factory.getUserAPI();
        IdVector storeIds = appUser.getUserStoreAsIdVector();
        OrderStatusCriteriaData searchCriteria = OrderStatusCriteriaData.createValue();
        searchCriteria.getExcludeOrderStatusList().add(RefCodeNames.ORDER_STATUS_CD.REFERENCE_ONLY);
        if (sForm.getOrderDateRangeBegin().trim().length() < 1 &&
            sForm.getOrderDateRangeEnd().trim().length() < 1)
        {
            String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.fieldsOrderDateRangeAreEmpty",null);
            lUpdateErrors.add("ordersearch",new ActionError("error.simpleGenericError",errorMess));
            return lUpdateErrors;
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

        //if user is a customer and an approver, disregard the site id
        // we don't want to limit thier access to approving orders
        UserData ud = userBean.getUser(Integer.parseInt(userId));
        String code = ud.getWorkflowRoleCd();

        //limit results to the site they have logged in to.
//        CleanwiseUser appUser = (CleanwiseUser)session.getAttribute("ApplicationUser");
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

        OrderStatusDescDataVector orderStatus = new OrderStatusDescDataVector();
        orderStatus = orderEjb.getOrderStatusDescCollection(searchCriteria, storeIds);

        //put greatest order id first
        DisplayListSort.sort(request, orderStatus, "orderid");

        OrderStatusDescDataVector temp = new OrderStatusDescDataVector();

        for (int j = orderStatus.size() - 1; j >= 0; j--)
        {
            temp.add(orderStatus.get(j));
        }

        orderStatus = temp;
        sForm.setResultList(orderStatus);

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
        UserOrderStatusForm sForm = (UserOrderStatusForm) form;

        if (sForm == null || !sForm.isInit()) {
            ActionErrors ae = initXPEDX(request, sForm);
            if (ae.size() > 0) {
                return ae;
            }
        }

        sForm = (UserOrderStatusForm) session.getAttribute(USER_ORDER_STATUS_FORM);
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
        UserOrderStatusForm sForm = (UserOrderStatusForm) form;
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        TimeZone timeZoneInp = Utility.getTimeZoneFromUserData(appUser.getSite());

        if (sForm == null || !sForm.isInit()) {
            ActionErrors ae = initXPEDX(request, sForm);
            if (ae.size() > 0) {
                return ae;
            }
        }

        sForm = (UserOrderStatusForm) session.getAttribute(USER_ORDER_STATUS_FORM);
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
        UserOrderStatusForm sForm = (UserOrderStatusForm) form;
        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        TimeZone timeZoneInp = Utility.getTimeZoneFromUserData(appUser.getSite());

        /*if (!Utility.isSet(sForm.getOrderDateRangeBegin())) {
        	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
            java.util.Date orderDate = new java.util.Date();
            orderDate = simpleDateFormat.parse("1/1/1900");
            sForm.setOrderDateRangeBegin(ClwI18nUtil.formatDateInp(request, orderDate, timeZoneInp.getID()));
        }*/
        if (!Utility.isSet(sForm.getOrderDateRangeEnd())) {
            Date currentDate = new Date();
            sForm.setOrderDateRangeEnd(ClwI18nUtil.formatDateInp(request, currentDate, timeZoneInp.getID()));
        }


        ae = checkFormAttribute(request, sForm);
        if (ae.size() > 0) {
            sForm.setResultList(new OrderStatusDescDataVector());
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
        
        //STJ-3986
        if (Utility.isSet(sForm.getOrderDateRangeBegin())) {
            String dateBegSInp = sForm.getOrderDateRangeBegin().trim();
            Date dateBeg = ClwI18nUtil.parseDateTimeInp(request, dateBegSInp, "00:00:00", timeZoneInp.getID());
            String dateBegS = sdfDT.format(dateBeg);
            searchCriteria.setOrderDateRangeBegin(dateBegS);
        }else{
        	String begD = "01/01/1900";
        	Date dateBegin = ClwI18nUtil.parseDateTimeInp(request, begD, "00:00:00", timeZoneInp.getID());

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
        if(Utility.isSet(sForm.getWebOrderConfirmationNum()) || Utility.isSet(sForm.getCustPONum())) {
        	if (Utility.isEqual(sForm.getOrderStatus(), Constants.ORDER_STATUS_ALL))
        		searchCriteria.setIncludeRelatedOrder(true);
        }
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
            sForm.setResultList(orderStatus);
            return ae;
        }

        //sort to have latest order first
        DisplayListSort.sort(request, orderStatus, "orderdateDesc");
        sForm.setResultList(orderStatus);

        session.setAttribute(USER_ORDER_STATUS_FORM, sForm);

        return ae;
    }

    private static ActionErrors checkFormAttribute(HttpServletRequest request, UserOrderStatusForm sForm) {

        ActionErrors errors = new ActionErrors();
        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        TimeZone timeZoneInp = Utility.getTimeZoneFromUserData(appUser.getSite());

        /*if (!Utility.isSet(sForm.getOrderDateRangeBegin())
                && !Utility.isSet(sForm.getOrderDateRangeEnd().trim())) {
            String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.fieldsOrderDateRangeAreEmpty", null);
            errors.add("invalidSearchCriteria", new ActionError("error.simpleGenericError", errorMess));
            return errors;
        }*/

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
        UserOrderStatusForm sForm = new UserOrderStatusForm();

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

        session.setAttribute(USER_ORDER_STATUS_FORM, sForm);

        return ae;
    }

    public static ActionErrors initXPEDXListOrders(HttpServletRequest request, ActionForm form) throws Exception {
        HttpSession session = request.getSession();
        UserOrderStatusForm sForm = (UserOrderStatusForm) form;

        ActionErrors ae = initXPEDX(request, sForm);
        if (ae.size() > 0) {
            return ae;
        }

        String orderStatusVal = request.getParameter("orderStatus");

        sForm = (UserOrderStatusForm) session.getAttribute(USER_ORDER_STATUS_FORM);
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
        UserOrderStatusForm sForm = (UserOrderStatusForm) form;

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

        /* PairViewVector siteValPairs;
        if (!Utility.isSet(sForm.getSiteState())) {
            siteValPairs = siteEjb.getSiteIdAndName(userId, 0, newCountry, sForm.getSiteState(), RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE, Constants.MAX_DYNAMIC_LIST_SITES + 1);
            if (siteValPairs.size() > Constants.MAX_DYNAMIC_LIST_SITES) {
                siteValPairs.clear();
            }
        } else {
            siteValPairs = siteEjb.getSiteIdAndName(userId, 0, newCountry, sForm.getSiteState(), RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE, 0);
        }*/

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

        session.setAttribute(USER_ORDER_STATUS_FORM, sForm);

        return ae;
    }

    public static ActionErrors changeState(HttpServletRequest request, ActionForm form, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        UserOrderStatusForm sForm = (UserOrderStatusForm) form;

        ActionErrors ae = checkRequest(request, form);
        if (ae.size() > 0) {
            return ae;
        }

        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        Site siteEjb = factory.getSiteAPI();

        int userId    = appUser.getUser().getUserId();

        String newState = request.getParameter("newState");

        /*  PairViewVector siteValPairs;
        if (!Utility.isSet(newState)) {
            siteValPairs = siteEjb.getSiteIdAndName(userId, 0, sForm.getSiteCountry(), newState, RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE, Constants.MAX_DYNAMIC_LIST_SITES + 1);
            if (siteValPairs.size() > Constants.MAX_DYNAMIC_LIST_SITES) {
                siteValPairs.clear();
            }
        } else {
            siteValPairs = siteEjb.getSiteIdAndName(userId, 0, sForm.getSiteCountry(), newState, RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE, 0);
        }*/

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

        session.setAttribute(USER_ORDER_STATUS_FORM, sForm);

        return ae;
    }


    public static ActionErrors changeSite(HttpServletRequest request, ActionForm form, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        UserOrderStatusForm sForm = (UserOrderStatusForm) form;

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

        session.setAttribute(USER_ORDER_STATUS_FORM, sForm);

        return ae;
    }

    /**
     * @deprecated  was replaced on prepareLocationCriteriaResJson  (29.10.2008)
     */
    private static Element prepareLocationCriteriaResXml(UserOrderStatusForm sForm) {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder docBuilder = null;

        try {
        	docBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
        }
        Document responseXml = docBuilder.getDOMImplementation().createDocument("", "LocationCriteria", null);

        Element root = responseXml.getDocumentElement();
        root.setAttribute("time", String.valueOf(System.currentTimeMillis()));

        appendSiteCriteria(responseXml,root,sForm.getSiteValuePairs(),sForm.getSiteSiteId());
        appendCountryCriteria(responseXml,root,sForm.getCountryValueList(),sForm.getSiteCountry());
        appendStateCriteria(responseXml,root,sForm.getStateValueList(),sForm.getSiteState());

        return root;

    }

    private static String prepareLocationCriteriaResJson(UserOrderStatusForm sForm,
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

    /**
     * @deprecated should be used appendStateCriteria which uses JSON format (29.10.2008)
     */
    private static void appendStateCriteria(Document responseXml,
                                            Element root,
                                            List stateValueList,
                                            String currentValue) {

        if (stateValueList!=null && !stateValueList.isEmpty()) {

            Element stateNode = responseXml.createElement("StateValueList");
            stateNode.setAttribute("selected",Utility.strNN(currentValue));

            Element node;
            Iterator it = stateValueList.iterator();
            while (it.hasNext()) {
                String stateVal = (String) it.next();

                node = responseXml.createElement("Value");
                node.setAttribute("Code",stateVal);

                node.appendChild(responseXml.createTextNode(stateVal));

                stateNode.appendChild(node);
            }
            root.appendChild(stateNode);
        }
    }

    /**
     * @deprecated should be used appendCountryCriteria which uses JSON format (29.10.2008)
     */
    private static void appendCountryCriteria(Document responseXml,
                                              Element root,
                                              List countryValueList,
                                              String currentValue) {

        if (countryValueList!=null && !countryValueList.isEmpty()) {

            Element countryNode = responseXml.createElement("CountryValueList");
            countryNode.setAttribute("selected",Utility.strNN(currentValue));

            Element node;
            Iterator it = countryValueList.iterator();
            while (it.hasNext()) {
                String countryVal = (String) it.next();

                node = responseXml.createElement("Value");
                node.setAttribute("Code",countryVal);

                node.appendChild(responseXml.createTextNode(countryVal));

                countryNode.appendChild(node);
            }
            root.appendChild(countryNode);
        }
    }

    /**
     * @deprecated should be used appendSiteCriteria which uses JSON format (29.10.2008)
     */
    private static void appendSiteCriteria(Document responseXml,
                                           Element root,
                                           PairViewVector siteValuePairs,
                                           String currentValue) {

        if (siteValuePairs!=null && !siteValuePairs.isEmpty()) {

            Element siteNode = responseXml.createElement("SiteValueList");
            siteNode.setAttribute("selected",Utility.strNN(currentValue));

            Element node;
            Iterator it = siteValuePairs.iterator();
            while (it.hasNext()) {
                PairView psiteVal = (PairView) it.next();

                node = responseXml.createElement("Value");
                node.setAttribute("Code", psiteVal.getObject1().toString());
                node.appendChild(responseXml.createTextNode(psiteVal.getObject2().toString()));

                siteNode.appendChild(node);
            }
            root.appendChild(siteNode);
        }
    }

}
