/**
 * Title:        OrderOpItemSearch
 * Description:  This is the Struts ActionForm class for the Order Items search page.
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
 * Form bean for the view Order Items page.  This form has the following fields,
 * with default values in square brackets:
 * <ul>
 * <li><b>orderStatusDetail</b> - A OrderData object
 * </ul>
 */
public final class StoreOrderItemSearchForm extends ActionForm {


  // -------------------------------------------------------- Instance Variables


    private OrderStatusDescData    _mOrderDesc = OrderStatusDescData.createValue();
    private OrderItemDescDataVector  _mOrderItemDescList = new OrderItemDescDataVector();
    private String _mOrderId = new String("");
    private String _mErpPoNum = new String("");

  // ---------------------------------------------------------------- Properties


    /**
     * Return the orderDesc object
     */
    public OrderStatusDescData getOrderDesc() {
        return (this._mOrderDesc);
    }

    /**
     * Set the orderDesc object
     */
    public void setOrderDesc(OrderStatusDescData detail) {
        this._mOrderDesc = detail;
    }


    /**
     * <code>getOrderItemDescList</code> method.
     *
     * @return a <code>OrderItemDescDataVector</code> value
     */
    public OrderItemDescDataVector getOrderItemDescList() {
        if( null == this._mOrderItemDescList) {
            this._mOrderItemDescList = new OrderItemDescDataVector();
        }
        return (this._mOrderItemDescList);
    }

    /**
     * <code>setOrderItemDescList</code> method.
     *
     * @param pVal a <code>OrderItemDescDataVector</code> value
     */
    public void setOrderItemDescList(OrderItemDescDataVector pVal) {
        this._mOrderItemDescList = pVal;
    }


    /**
     * Return the OrderId
     */
    public String getOrderId() {
        return (this._mOrderId);
    }

    /**
     * Set the OrderId
     */
    public void setOrderId(String pVal) {
        this._mOrderId = pVal;
    }


    /**
     * Return the ErpPoNum
     */
    public String getErpPoNum() {
        return (this._mErpPoNum);
    }

    /**
     * Set the ErpPoNum
     */
    public void setErpPoNum(String pVal) {
        this._mErpPoNum = pVal;
    }




  // ------------------------------------------------------------ Public Methods


    /**
     * Reset all properties to their default values.
     *
     * @param mapping The mapping used to select this instance
     * @param request The servlet request we are processing
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
    }


    /**
     * Validate the properties that have been set from this HTTP request,
     * and return an <code>ActionErrors</code> object that encapsulates any
     * validation errors that have been found.  If no errors are found, return
     * <code>null</code> or an <code>ActionErrors</code> object with no
     * recorded error messages.
     *
     * Required fields are:
     *   freightTable name
     *   tax ID
     *   contact first and last names
     *   contact email address
     *
     * Additionally, a freightTable's name is checked for uniqueness.
     *
     * @param mapping The mapping used to select this instance
     * @param request The servlet request we are processing
     */
    public ActionErrors validate(ActionMapping mapping,
                               HttpServletRequest request) {

        return null;
    }


}
