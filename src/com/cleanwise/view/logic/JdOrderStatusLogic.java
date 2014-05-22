package com.cleanwise.view.logic;

import com.cleanwise.service.api.*;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;

import com.cleanwise.view.forms.JdOrderStatusForm;
import com.cleanwise.view.utils.*;

import java.rmi.*;

import java.text.SimpleDateFormat;

import java.util.*;
import java.util.Calendar;
import java.util.Date;

import javax.ejb.*;

import javax.naming.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;


/**
 *  <code>UserPendingOrdersLogic</code> implements the logic 
 *  to manipulate Orders which require approval or rejection.
 *
 *@author     durval
 *@created    January 7, 2002
 */
public class JdOrderStatusLogic
{

    /**
     *  Pick out the information from the form to send an email.
     *
     *@param  request
     */
    public static void listOrders(HttpServletRequest request, ActionForm form)
                           throws Exception
    {

        HttpSession session = request.getSession();

        //JdOrderStatusForm sForm = new JdOrderStatusForm();
        JdOrderStatusForm sForm = (JdOrderStatusForm) form;
        APIAccess factory = (APIAccess)session.getAttribute(
                                    Constants.APIACCESS);

        if (null == factory)
        {
            throw new Exception("Without APIAccess.");
        }

        Order orderEjb = factory.getOrderAPI();
        User userBean = factory.getUserAPI();
        OrderStatusCriteriaData searchCriteria = OrderStatusCriteriaData.createValue();
        Date currentDate = new Date();
        Calendar gCalendar = Calendar.getInstance();
        gCalendar.setTime(currentDate);

        // By default, list the orders for the last 3 months.
        int smonth = gCalendar.get(Calendar.MONTH) - 3;
        gCalendar.set(Calendar.MONTH, smonth);

        Date orderBeginDate = gCalendar.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String orderBeginDateS = simpleDateFormat.format(orderBeginDate);
        String orderEndDateS = simpleDateFormat.format(currentDate);
        sForm.setOrderDateRangeBegin(orderBeginDateS);
        sForm.setOrderDateRangeEnd(orderEndDateS);
        searchCriteria.setOrderDateRangeBegin(orderBeginDateS);
        searchCriteria.setOrderDateRangeEnd(orderEndDateS);

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

        CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(
                                            "ApplicationUser");
        int accountId = appUser.getSite().getAccountBusEntity().getBusEntityId();
        searchCriteria.setAccountId(accountId+"");


        // fetch the orders in the past year
        JdOrderStatusViewVector orderStatusVV = new JdOrderStatusViewVector();
        orderStatusVV = orderEjb.getJdOrderStatusDescCollection(searchCriteria);
        if(orderStatusVV.size()>1000) {
           orderStatusVV.remove(1000);
           sForm.setOverflowFl(true);
        } else {
           sForm.setOverflowFl(false);
        }
        sForm.setResultList(orderStatusVV);
        //session.setAttribute("USER_ORDER_STATUS_FORM", sForm);
        return;
    }

    public static void sort(HttpServletRequest request, ActionForm form)
                     throws Exception
    {

        HttpSession session = request.getSession();
        JdOrderStatusForm sForm = (JdOrderStatusForm)form;

        if (sForm == null)
        {

            return;
        }

        JdOrderStatusViewVector orders = sForm.getResultList();

        if (orders == null)
        {

            return;
        }

        String sortField = request.getParameter("sortField");   
        orders.sort(sortField);
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
        JdOrderStatusForm sForm = (JdOrderStatusForm)form;
        APIAccess factory = (APIAccess)session.getAttribute(
                                    Constants.APIACCESS);

        if (null == factory)
        {
            throw new Exception("Without APIAccess.");
        }

        Order orderEjb = factory.getOrderAPI();
        User userBean = factory.getUserAPI();
        OrderStatusCriteriaData searchCriteria = OrderStatusCriteriaData.createValue();

        if (sForm.getOrderDateRangeBegin().trim().length() < 1 && 
            sForm.getOrderDateRangeEnd().trim().length() < 1)
        {
            lUpdateErrors.add("ordersearch", 
                              new ActionError("variable.empty.error", 
                                              "Order Date Range"));

            return lUpdateErrors;
        }

        if (sForm.getWebOrderConfirmationNum().trim().length() > 0)
        {
            searchCriteria.setWebOrderConfirmationNum(sForm.getWebOrderConfirmationNum().trim());
        }

        if (sForm.getOrderDateRangeBegin().trim().length() > 0)
        {
            searchCriteria.setOrderDateRangeBegin(sForm.getOrderDateRangeBegin().trim());
        }

        if (sForm.getOrderDateRangeEnd().trim().length() > 0)
        {
            searchCriteria.setOrderDateRangeEnd(sForm.getOrderDateRangeEnd().trim());
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

        //limit results to the site they have logged in to.
        CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(
                                        "ApplicationUser");
        int accountId = appUser.getSite().getAccountBusEntity().getBusEntityId();
        searchCriteria.setAccountId(accountId+"");

        JdOrderStatusViewVector orderStatusVV = new JdOrderStatusViewVector();
        orderStatusVV = orderEjb.getJdOrderStatusDescCollection(searchCriteria);
        if(orderStatusVV.size()>1000) {
           orderStatusVV.remove(1000);
           sForm.setOverflowFl(true);
        } else {
           sForm.setOverflowFl(false);
        }

        //put greatest order id first
        orderStatusVV.sort("OrderNum");

        sForm.setResultList(orderStatusVV);

        return lUpdateErrors;
    }
}
