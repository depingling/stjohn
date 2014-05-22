/**
 * Title:        OrderTroubleshootingForm
 * Description:  This is the Struts ActionForm class for the OrderTroubleshooting area.
 * Purpose:      
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       
 */

package com.cleanwise.view.forms;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import com.cleanwise.service.api.value.*;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.CurrencyFormat;
import javax.servlet.http.HttpSession;


/**
 * Form bean for the view OrderTroubleshooting page.  This form has the following fields,
 * with default values in square brackets:
 * <ul>
 * <li><b>orderTroubleshooting</b>
 * </ul>
 */
public final class OrderTroubleshootingForm extends ActionForm {


  // -------------------------------------------------------- Instance Variables
  private OrderHistoryDataVector _resultList = new OrderHistoryDataVector();
  
  private String _mOrderDateRangeBegin       = new String("");
  private String _mOrderDateRangeEnd         = new String("");

   

    
  // ---------------------------------------------------------------- Properties

  /**
     * <code>getOrderDateRangeBegin</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getOrderDateRangeBegin() {
        return (this._mOrderDateRangeBegin);
    }

    /**
     * <code>setOrderDateRangeBegin</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setOrderDateRangeBegin(String pVal) {
        this._mOrderDateRangeBegin = pVal;
    }

    
    /**
     * <code>getOrderDateRangeEnd</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getOrderDateRangeEnd() {
        return (this._mOrderDateRangeEnd);
    }

    /**
     * <code>setOrderDateRangeEnd</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setOrderDateRangeEnd(String pVal) {
        this._mOrderDateRangeEnd = pVal;
    }
	
	/**
     * <code>getResultList</code> method.
     *
     * @return a <code>OrderStatusDescDataVector</code> value
     */
    public OrderHistoryDataVector getResultList() {
        return (this._resultList);
    }

    /**
     * <code>setResultList</code> method.
     *
     * @param pVal a <code>OrderStatusDescDataVector</code> value
     */
    public void setResultList(OrderHistoryDataVector pVal) {
        this._resultList = pVal;
    }

    /**
     * <code>getListCount</code> method.
     *
     * @return a <code>int</code> value
     */
    public int getListCount() {
        return (this._resultList.size());
    }
	
	/**
     * <code>reset</code> method, set the search fiels to null.
     *
     * @param mapping an <code>ActionMapping</code> value
     * @param request a <code>HttpServletRequest</code> value
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        /*
        
        this._mOrderDateRangeBegin    = new String("");
        this._mOrderDateRangeEnd     = new String("");          
         */
    }


    /**
     * <code>validate</code> method is a stub.
     *
     * @param mapping an <code>ActionMapping</code> value
     * @param request a <code>HttpServletRequest</code> value
     * @return an <code>ActionErrors</code> value
     */
    public ActionErrors validate(ActionMapping mapping,
                               HttpServletRequest request) {
        // No validation necessary.
        return null;
    }
  
  
}
