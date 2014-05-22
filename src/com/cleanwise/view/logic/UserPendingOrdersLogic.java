package com.cleanwise.view.logic;

import java.rmi.RemoteException;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.APIServiceAccessException;
import com.cleanwise.service.api.value.OrderSiteSummaryDataVector;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.DisplayListSort;

/**
 *  <code>UserPendingOrdersLogic</code> implements the logic 
 *  to manipulate Orders which require approval or rejection.
 *
 *@author     durval
 *@created    January 7, 2002
 */
public class UserPendingOrdersLogic {
    private static final Logger log = Logger.getLogger(UserPendingOrdersLogic.class);

    /**
     * Retrieve the pending orders for a user.
     * @param pAppUser - the user for whom pending orders are being retrieved
     */
    public static OrderSiteSummaryDataVector listOrders(CleanwiseUser pAppUser)
    throws APIServiceAccessException, NamingException, RemoteException {
        OrderSiteSummaryDataVector returnValue;
		returnValue = new APIAccess().getWorkflowAPI().listPendingOrders(pAppUser.getUser().getUserId());
		if (returnValue == null) {
			returnValue = new OrderSiteSummaryDataVector();
		}
        return returnValue;
    }
    
    /**
     * Retrieve the pending orders for a user, and (optionally) store the list of orders in the session.
     * @param request - the request currently being processed
     * @param pAppUser - the user for whom pending orders are being retrieved
     */
    public static void listOrders(HttpServletRequest request, 
				  CleanwiseUser pAppUser) {
		log.info("UserPendingOrdersLogic.listOrders ");
        OrderSiteSummaryDataVector v;
		try {
		    v = listOrders(pAppUser);
		} 
		catch (Exception e) {
			e.printStackTrace();
	        v = new OrderSiteSummaryDataVector();
		}
		request.getSession().setAttribute("pending.orders.vector", v);
        return;
    }

    
    public static void sort(HttpServletRequest request)
    throws Exception {

        HttpSession session = request.getSession();
        OrderSiteSummaryDataVector v = 
        (OrderSiteSummaryDataVector)
        session.getAttribute("pending.orders.vector" );
        if (v == null) {
            return;
        }
        String sortField = request.getParameter("sortField");
        DisplayListSort.sort(v, sortField);
    }

}

