package com.cleanwise.view.logic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.cleanwise.service.api.*;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.value.*;

/**
 *  <code>UserPartsOrderLogic</code> implements the logic 
 *  to manipulate Part Orders.
 *
 *@author     alexxey
 *@created    May 6, 2008
 */
public class UserPartsOrderLogic {

    /**
     *  Prepare OrderJoinObject in session with informaiotn about the current Parts Order.
     *
     *@param  request
     */
    public static void showOrder(HttpServletRequest request, 
				  int orderId) {

	    HttpSession session = request.getSession();
        OrderJoinData ojd = new OrderJoinData();
        
	try {
	    APIAccess factory = new APIAccess();
            Order orderBean = factory.getOrderAPI();
            ojd = orderBean.fetchOrder(orderId);
	} 
	catch (Exception e) {
            e.printStackTrace();
	}
        session.setAttribute("order", ojd);
    return;
    }
}

