/**
 * Title:        OrderOpItemDetail
 * Description:  This is the Struts ActionForm class for the OrderItem detail page.
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
 * Form bean for the view OrderItem page.  This form has the following fields,
 * with default values in square brackets:
 * <ul>
 * <li><b>OrderItemDetail</b> - A OrderItemData object
 * </ul>
 */
public final class OrderOpItemDetailForm extends ActionForm {


  // -------------------------------------------------------- Instance Variables


    private OrderItemDescData    _mOrderItemDesc = OrderItemDescData.createValue();
    private InvoiceDistDataVector    _mInvoiceDistList = new InvoiceDistDataVector();
    
    private int _mAcceptedNum = 0;
    private int _mRejectedNum = 0;
    private int _mShippedNum = 0;
    private int _mBackorderedNum = 0;
    private int _mInvoicedNum = 0;
    private int _mSubstitutedNum = 0;
    private Date _mLastDate = null;
    
  // ---------------------------------------------------------------- Properties


    /**
     * Return the OrderItemDesc object
     */
    public OrderItemDescData getOrderItemDesc() {
        return (this._mOrderItemDesc);
    }

    /**
     * Set the OrderItemDesc object
     */
    public void setOrderItemDesc(OrderItemDescData detail) {
        this._mOrderItemDesc = detail;
    }


    /**
     * Return the InvoiceDistList object
     */
    public InvoiceDistDataVector getInvoiceDistList() {
        return (this._mInvoiceDistList);
    }

    /**
     * Set the InvoiceDistList object
     */
    public void setInvoiceDistList(InvoiceDistDataVector detail) {
        this._mInvoiceDistList = detail;
    }

  
    
    /**
     * Return the number of accepted items
     */
    public int getAcceptedNum() {
        return (this._mAcceptedNum);
    }

    /**
     * Set the number of accepted items
     */
    public void setAcceptedNum(int pVal) {
        this._mAcceptedNum = pVal;
    }

  
    /**
     * Return the number of Rejected items
     */
    public int getRejectedNum() {
        return (this._mRejectedNum);
    }

    /**
     * Set the number of Rejected items
     */
    public void setRejectedNum(int pVal) {
        this._mRejectedNum = pVal;
    }

    
    /**
     * Return the number of Shipped items
     */
    public int getShippedNum() {
        return (this._mShippedNum);
    }

    /**
     * Set the number of Shipped items
     */
    public void setShippedNum(int pVal) {
        this._mShippedNum = pVal;
    }

    
    /**
     * Return the number of Backordered items
     */
    public int getBackorderedNum() {
        return (this._mBackorderedNum);
    }

    /**
     * Set the number of Backordered items
     */
    public void setBackorderedNum(int pVal) {
        this._mBackorderedNum = pVal;
    }


    /**
     * Return the number of Invoiced items
     */
    public int getInvoicedNum() {
        return (this._mInvoicedNum);
    }

    /**
     * Set the number of Invoiced items
     */
    public void setInvoicedNum(int pVal) {
        this._mInvoicedNum = pVal;
    }

    
    /**
     * Return the number of Substituted items
     */
    public int getSubstitutedNum() {
        return (this._mSubstitutedNum);
    }

    /**
     * Set the number of Substituted  items
     */
    public void setSubstitutedNum(int pVal) {
        this._mSubstitutedNum = pVal;
    }

    
    /**
     * Return the Date of last action
     */
    public Date getLastDate() {
        return (this._mLastDate);
    }

    /**
     * Set the last action date
     */
    public void setLastDate(Date pVal) {
        this._mLastDate = pVal;
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
