/**
 * Title:        OrderOpTemporaryPo
 * Description:  This is the Struts ActionForm class for the Order temporary PO detail page.
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
 * Form bean for the view and print temporary PO page.  This form has the following fields,
 * with default values in square brackets:
 * <ul>
 * <li><b>orderStatusDetail</b> - A OrderData object
 * </ul>
 */
public final class OrderOpTemporaryPoForm extends ActionForm {


  // -------------------------------------------------------- Instance Variables


    private OrderStatusDescData    _mOrderStatusDetail = OrderStatusDescData.createValue();
    private OrderItemDescDataVector  _mOrderItemDescList = new OrderItemDescDataVector();
    private DistributorDataVector _mDistList = new DistributorDataVector();   
    private String  _mDistIdS = new String("");
    private DistributorData _mDistributorData = null;
    private OrderItemDataVector  _mDistOrderItemList = new OrderItemDataVector();
    
    
    
  // ---------------------------------------------------------------- Properties


    /**
     * Return the orderStatus detail object
     */
    public OrderStatusDescData getOrderStatusDetail() {
        return (this._mOrderStatusDetail);
    }

    /**
     * Set the orderStatus detail object
     */
    public void setOrderStatusDetail(OrderStatusDescData detail) {
        this._mOrderStatusDetail = detail;
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
     * <code>getDistList</code> method.
     *
     * @return a <code>DistributorDataVector</code> value
     */
    public DistributorDataVector getDistList() {
        if( null == this._mDistList) {
            this._mDistList = new DistributorDataVector();
        }
        return (this._mDistList);
    }

    /**
     * <code>setDistList</code> method.
     *
     * @param pVal a <code>DistributorDataVector</code> value
     */
    public void setDistList(DistributorDataVector pVal) {
        this._mDistList = pVal;
    }
    

    /**
     * <code>getDistributorData</code> method.
     *
     * @return a <code>DistributorData</code> value
     */
    public DistributorData getDistributorData() {
        return (this._mDistributorData);
    }

    /**
     * <code>setDistributorData</code> method.
     *
     * @param pVal a <code>DistributorData</code> value
     */
    public void setDistributorData(DistributorData pVal) {
        this._mDistributorData = pVal;
    }
    
    
    
    /**
     * Return distIdS
     */
    public String getDistIdS() {
        return (this._mDistIdS);
    }

    /**
     * Set the distIdS
     */
    public void setDistIdS(String pVal) {
        this._mDistIdS = pVal;
    }
    
    
    /**
     * <code>getDistOrderItemList</code> method.
     *
     * @return a <code>OrderItemDataVector</code> value
     */
    public OrderItemDataVector getDistOrderItemList() {
        if( null == this._mDistOrderItemList) {
            this._mDistOrderItemList = new OrderItemDataVector();
        }
        return (this._mDistOrderItemList);
    }

    /**
     * <code>setDistOrderItemList</code> method.
     *
     * @param pVal a <code>OrderItemDataVector</code> value
     */
    public void setDistOrderItemList(OrderItemDataVector pVal) {
        this._mDistOrderItemList = pVal;
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
