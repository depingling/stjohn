/**
 * Title:        OrderOpDetail
 * Description:  This is the Struts ActionForm class for the OrderStatus detail page.
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
 * Form bean for the view OrderStatus page.  This form has the following fields,
 * with default values in square brackets:
 * <ul>
 * <li><b>orderStatusDetail</b> - A OrderData object
 * </ul>
 */
public final class StoreOrderNoteForm extends ActionForm {


  // -------------------------------------------------------- Instance Variables


    private OrderPropertyData  _mOrderPropertyDetail = OrderPropertyData.createValue();
    private OrderPropertyDataVector _mOrderPropertyList = new OrderPropertyDataVector();

    private String _mOrderId = new String("");
    private String _mOrderItemId = new String("");
    private String _mNoteId = new String("");
    private String _mOrderNoteType = new String("");

  // ---------------------------------------------------------------- Properties


    /**
     * Return the orderPropertyData object
     */
    public OrderPropertyData getOrderPropertyDetail() {
        return (this._mOrderPropertyDetail);
    }

    /**
     * Set the orderPropertyData object
     */
    public void setOrderPropertyDetail(OrderPropertyData detail) {
        this._mOrderPropertyDetail = detail;
    }


	/**
     * Return the orderPropertyDataVector object
     */
    public OrderPropertyDataVector getOrderPropertyList() {
        if( null == this._mOrderPropertyList) {
		    this._mOrderPropertyList = new OrderPropertyDataVector();
		}
		return (this._mOrderPropertyList);
    }

    /**
     * Set the orderPropertyDataVector object
     */
    public void setOrderPropertyList(OrderPropertyDataVector detail) {
	    this._mOrderPropertyList = detail;
    }



    /**
     * Return the order Id
     */
    public String getOrderId() {
        return (this._mOrderId);
    }

    /**
     * Set the Order Id
     */
    public void setOrderId(String pVal) {
        this._mOrderId = pVal;
    }

    /**
     * Return the order item Id
     */
    public String getOrderItemId() {
        return (this._mOrderItemId);
    }

    /**
     * Set the Order item Id
     */
    public void setOrderItemId(String pVal) {
        this._mOrderItemId = pVal;
    }


    /**
     * Return the note Id
     */
    public String getNoteId() {
        return (this._mNoteId);
    }

    /**
     * Set the Note Id
     */
    public void setNoteId(String pVal) {
        this._mNoteId = pVal;
    }


    /**
     * Return the order note type
     */
    public String getOrderNoteType() {
        return (this._mOrderNoteType);
    }

    /**
     * Set the Order note type
     */
    public void setOrderNoteType(String pVal) {
        this._mOrderNoteType = pVal;
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
