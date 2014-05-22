
package com.cleanwise.view.logic;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;
import java.util.LinkedList;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import com.cleanwise.view.utils.*;
import com.cleanwise.view.forms.OrderTroubleshootingForm;
import com.cleanwise.service.api.*;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.DataNotFoundException;

/**
 * <code>OrderTroubleshootingLogic</code> implements the logic needed to
 * manipulate order records.
 *
 * @author durval
 */
public class OrderTroubleshootingLogic {

    /**
     * <code>CurrentBackhistearch</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */
    public static void runTroubleshooting(HttpServletRequest request,
			      ActionForm form, String type)
	throws Exception {

	    HttpSession session = request.getSession();
	    OrderTroubleshootingForm sForm = (OrderTroubleshootingForm)form;
	    APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
	    if (null == factory) {
	        throw new Exception("Without APIAccess.");
	    }    
                
	    Order orderEjb = factory.getOrderAPI();
        
		StringBuffer sqlBuf = new StringBuffer("");
		if(type.equals("currentBackorders")){  
		   sqlBuf = new StringBuffer("SELECT CUSTOMER_ACCT, ERP_PO_NUMBER, FRONT_END_ORDER_NUMBER, DATE_ORDERED, CUSTOMER_PO_NUMBER, LOCATION_NAME, SHIPPING_ADDRESS_1, CITY, STATE, ZIP, SHIP_METHOD, status.ORDER_STATUS_ID FROM CLW_ORDER_ITEM_STATUS item, CLW_ORDER_STATUS status, CLW_ERP_PO erp, CLW_SHIPPING_HISTORY ship WHERE item.QUANTITY_ORDERED > item.QUANTITY_SHIPPED AND item.ORDER_ITEM_STATUS_ID = ship.ORDER_ITEM_STATUS_ID AND item.ERP_PO_ID = erp.ERP_PO_ID AND erp.ORDER_STATUS_ID = status.ORDER_STATUS_ID ");
		
		   session.setAttribute("loc", "Current Backorders");
		  
		}
		else if(type.equals("poNotConfirmed")){
		   sqlBuf = new StringBuffer("SELECT CUSTOMER_ACCT, ERP_PO_NUMBER, FRONT_END_ORDER_NUMBER, DATE_ORDERED, CUSTOMER_PO_NUMBER, LOCATION_NAME, SHIPPING_ADDRESS_1, CITY, STATE, ZIP, SHIP_METHOD, status.ORDER_STATUS_ID FROM CLW_ORDER_ITEM_STATUS item, CLW_ORDER_STATUS status, CLW_ERP_PO erp, CLW_SHIPPING_HISTORY ship WHERE (erp.SEND_STATUS = 5 OR erp.SEND_STATUS = 7) AND item.STATUS_855 = 0 AND item.ORDER_ITEM_STATUS_ID = ship.ORDER_ITEM_STATUS_ID AND item.ERP_PO_ID = erp.ERP_PO_ID AND erp.ORDER_STATUS_ID = status.ORDER_STATUS_ID");
		
		   session.setAttribute("loc", "PO Not Confirmed From Distributor");
		
		}   
		else if(type.equals("poAckNotSent")){	
		   sqlBuf = new StringBuffer("SELECT CUSTOMER_ACCT, ERP_PO_NUMBER, FRONT_END_ORDER_NUMBER, DATE_ORDERED, CUSTOMER_PO_NUMBER, LOCATION_NAME, SHIPPING_ADDRESS_1, CITY, STATE, ZIP, SHIP_METHOD, status.ORDER_STATUS_ID FROM CLW_ORDER_ITEM_STATUS item, CLW_ORDER_STATUS status, CLW_ERP_PO erp, CLW_SHIPPING_HISTORY ship WHERE item.STATUS_855 = 1 AND	item.ORDER_ITEM_STATUS_ID = ship.ORDER_ITEM_STATUS_ID AND item.ERP_PO_ID = erp.ERP_PO_ID AND 	erp.ORDER_STATUS_ID = status.ORDER_STATUS_ID");
		
		   session.setAttribute("loc", "PO Acknowledgement Not Sent to Customer");		  
		  
		  
		}
		else if(type.equals("shipNoticeNotSent")){	
		   sqlBuf = new StringBuffer("SELECT CUSTOMER_ACCT, ERP_PO_NUMBER, FRONT_END_ORDER_NUMBER, DATE_ORDERED, CUSTOMER_PO_NUMBER, LOCATION_NAME, SHIPPING_ADDRESS_1, CITY, STATE, ZIP, SHIP_METHOD, status.ORDER_STATUS_ID FROM   CLW_ORDER_ITEM_STATUS item, CLW_ORDER_STATUS status, CLW_ERP_PO erp, CLW_SHIPPING_HISTORY ship WHERE item.STATUS_855 = 2 AND ship.STATUS_856 = 1 AND item.ORDER_ITEM_STATUS_ID = ship.ORDER_ITEM_STATUS_ID AND item.ERP_PO_ID = erp.ERP_PO_ID AND erp.ORDER_STATUS_ID = status.ORDER_STATUS_ID");
		
		   session.setAttribute("loc", "Ship Notice Not Sent to Customer");
		}
		else if(type.equals("poNotReceived")){	
		   sqlBuf = new StringBuffer("SELECT CUSTOMER_ACCT, ERP_PO_NUMBER, FRONT_END_ORDER_NUMBER, DATE_ORDERED, CUSTOMER_PO_NUMBER, LOCATION_NAME, SHIPPING_ADDRESS_1, CITY, STATE, ZIP, SHIP_METHOD, status.ORDER_STATUS_ID FROM CLW_ORDER_ITEM_STATUS item, CLW_ORDER_STATUS status, CLW_ERP_PO erp, CLW_SHIPPING_HISTORY ship WHERE erp.SEND_STATUS = 2 AND	item.ORDER_ITEM_STATUS_ID = ship.ORDER_ITEM_STATUS_ID AND item.ERP_PO_ID = erp.ERP_PO_ID AND 	erp.ORDER_STATUS_ID = status.ORDER_STATUS_ID");
		
		   session.setAttribute("loc", "PO Not Received by Distributor");
		}
		else if(type.equals("poRejected")){	
		   sqlBuf = new StringBuffer("SELECT CUSTOMER_ACCT, ERP_PO_NUMBER, FRONT_END_ORDER_NUMBER, DATE_ORDERED, CUSTOMER_PO_NUMBER, LOCATION_NAME, SHIPPING_ADDRESS_1, CITY, STATE, ZIP, SHIP_METHOD, status.ORDER_STATUS_ID FROM CLW_ORDER_ITEM_STATUS item, CLW_ORDER_STATUS status, CLW_ERP_PO erp, CLW_SHIPPING_HISTORY ship WHERE erp.SEND_STATUS = 6 AND	item.ORDER_ITEM_STATUS_ID = ship.ORDER_ITEM_STATUS_ID AND item.ERP_PO_ID = erp.ERP_PO_ID AND 	erp.ORDER_STATUS_ID = status.ORDER_STATUS_ID");
		
		   session.setAttribute("loc", "PO Rejected from Distributor");
		}
		else if(type.equals("invoiceNotSent")){	
		   sqlBuf = new StringBuffer("SELECT CUSTOMER_ACCT, ERP_PO_NUMBER, FRONT_END_ORDER_NUMBER, DATE_ORDERED, status.CUSTOMER_PO_NUMBER, LOCATION_NAME, status.SHIPPING_ADDRESS_1, CITY, STATE, ZIP, SHIP_METHOD, status.ORDER_STATUS_ID FROM  CLW_ORDER_ITEM_STATUS item, CLW_ORDER_STATUS status, CLW_ERP_PO erp, CLW_SHIPPING_HISTORY ship, CLW_INVOICE invoice WHERE invoice.STATUS_810 = 1 AND item.ORDER_ITEM_STATUS_ID = ship.ORDER_ITEM_STATUS_ID AND item.ERP_PO_ID = erp.ERP_PO_ID AND erp.ORDER_STATUS_ID = status.ORDER_STATUS_ID ");
		
		   session.setAttribute("loc", "Invoice Not Sent to Customer");
		}
		
		if(sqlBuf != null){
		  
		  OrderHistoryDataVector histVec = orderEjb.getTroubledOrders(sqlBuf);        
          DisplayListSort.sort(histVec, "acctname");
	      sForm.setResultList(histVec);			  
		}
		
    }  
  
  
    /**
     *  <code>sort</code>
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static void sort(HttpServletRequest request,
			    ActionForm form)
	throws Exception {

        // Get a reference to the admin facade
        HttpSession session = request.getSession();
	    OrderTroubleshootingForm sForm = (OrderTroubleshootingForm)form;
	    if (sForm == null) {
	        return;
	    }
	    OrderHistoryDataVector hist = (OrderHistoryDataVector)sForm.getResultList();
  	    if (hist == null) {
	        return;
	    }

	    String sortField = request.getParameter("sortField");
	    DisplayListSort.sort(hist, sortField);
    }
	
	
	
	
}  
  
