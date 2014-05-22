/**
 * Title:        OrderBackfill
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
public final class OrderBackfillForm extends ActionForm {


  // -------------------------------------------------------- Instance Variables


    private String mStringOrderNumber = null;
    private String mStringErpOrderNum = null;
    private int mOrderId = 0;

    private OrderStatusDescData    _mOrderStatusDetail = OrderStatusDescData.createValue();
    private OrderStatusDescData    _mOrderStatusDetailAfter = OrderStatusDescData.createValue();


    private OrderPropertyData  _mOrderPropertyDetail = OrderPropertyData.createValue();
    private OrderPropertyDataVector _mOrderPropertyList = new OrderPropertyDataVector();
    private OrderItemDescDataVector  _mOrderItemDescList = new OrderItemDescDataVector();


    private int _mOrderedNum = 0;
    private int _mAcceptedNum = 0;
    private int _mRejectedNum = 0;
    private int _mShippedNum = 0;
    private int _mBackorderedNum = 0;
    private int _mCanceledNum = 0;
    private int _mSubstitutedNum = 0;
    private int _mInvoicedNum = 0;
    private Date _mLastDate = null;

    private double _mTotalAmount = 0D;
    private double _mSubTotal = 0D;
    private double _mTotalFreightCost = 0D;
    private double _mTotalTaxCost = 0D;
    private double _mTotalMiscCost = 0D;

    private double _mTotalAmountAfter = 0D;
    private double _mSubTotalAfter = 0D;
    private double _mTotalFreightCostAfter = 0D;
    private double _mTotalTaxCostAfter = 0D;
    private double _mTotalMiscCostAfter = 0D;

    private String _mAllItemsStatus = null;

    private String _mNewContractIdS = new String("");
  // ---------------------------------------------------------------- Properties

    public String getStringOrderNumber() {return mStringOrderNumber;}
    public void setStringOrderNumber(String pValue){mStringOrderNumber=pValue;}
    public String getStringErpOrderNum() {return mStringErpOrderNum;}
    public void setStringErpOrderNum(String pValue){mStringErpOrderNum=pValue;}
    public int getOrderId() {return mOrderId;}
    public void setOrderId(int pValue){mOrderId=pValue;}

  public void setOrderItemStatus(int pIndex, String pValue){
    if (_mOrderStatusDetailAfter==null) return;
    OrderItemDataVector oiDV = _mOrderStatusDetailAfter.getOrderItemList();
    if(oiDV==null) return;
    if(pIndex<0) return;
    if(pIndex<oiDV.size()) {
      OrderItemData oiD = (OrderItemData) oiDV.get(pIndex);
      if(oiD==null) return;
      oiD.setOrderItemStatusCd(pValue);
    }
  }

  public String getOrderItemStatus(int pIndex){
    if (_mOrderStatusDetailAfter==null) return "";
    OrderItemDataVector oiDV = _mOrderStatusDetailAfter.getOrderItemList();
    if(oiDV==null) return "";
    if(pIndex<0 ||pIndex>=oiDV.size()) return "";
    OrderItemData oiD = (OrderItemData) oiDV.get(pIndex);
    if(oiD==null) return "";
    return  oiD.getOrderItemStatusCd();
  }

  public void setAckStatus(int pIndex, String pValue){
    if (_mOrderStatusDetailAfter==null) return;
    OrderItemDataVector oiDV = _mOrderStatusDetailAfter.getOrderItemList();
    if(oiDV==null) return;
    if(pIndex<0) return;
    if(pIndex<oiDV.size()) {
      OrderItemData oiD = (OrderItemData) oiDV.get(pIndex);
      if(oiD==null) return;
      oiD.setAckStatusCd(pValue);
    }
  }

  public String getAckStatus(int pIndex){
    if (_mOrderStatusDetailAfter==null) return "";
    OrderItemDataVector oiDV = _mOrderStatusDetailAfter.getOrderItemList();
    if(oiDV==null) return "";
    if(pIndex<0 ||pIndex>=oiDV.size()) return "";
    OrderItemData oiD = (OrderItemData) oiDV.get(pIndex);
    if(oiD==null) return "";
    return  oiD.getAckStatusCd();
  }

    /**
     * Return the orderStatus detail object
     */
    public OrderStatusDescData getOrderStatusDetail() {
        return (this._mOrderStatusDetail);
    }
    /**
     * Return the orderStatus detail object
     */
    public OrderStatusDescData getOrderStatusDetailAfter() {
        return (this._mOrderStatusDetailAfter);
    }

    /**
     * Set the orderStatus detail object
     */
    public void setOrderStatusDetail(OrderStatusDescData detail) {
        this._mOrderStatusDetail = detail;
    }
    /**
     * Set the orderStatus detail object
     */
    public void setOrderStatusDetailAfter(OrderStatusDescData detail) {
        this._mOrderStatusDetailAfter = detail;
    }

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
     * Return the number of Ordered items
     */
    public int getOrderedNum() {
        return (this._mOrderedNum);
    }

    /**
     * Set the number of Ordered items
     */
    public void setOrderedNum(int pVal) {
        this._mOrderedNum = pVal;
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
     * Return the number of Canceled items
     */
    public int getCanceledNum() {
        return (this._mCanceledNum);
    }

    /**
     * Set the number of Canceled items
     */
    public void setCanceledNum(int pVal) {
        this._mCanceledNum = pVal;
    }


    /**
     * Return the number of Substituted items
     */
    public int getSubstitutedNum() {
        return (this._mSubstitutedNum);
    }

    /**
     * Set the number of Substituted items
     */
    public void setSubstitutedNum(int pVal) {
        this._mSubstitutedNum = pVal;
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


    /**
     * Return the TotalAmount of the order
     */
    public double getTotalAmount() {
        return (this._mTotalAmount);
    }

    /**
     * Set the TotalAmount of the order
     */
    public void setTotalAmount(double pVal) {
        this._mTotalAmount = pVal;
    }


    /**
     * Return the SubTotal of the order
     */
    public double getSubTotal() {
        return (this._mSubTotal);
    }

    /**
     * Set the SubTotal of the order
     */
    public void setSubTotal(double pVal) {
        this._mSubTotal = pVal;
    }


    /**
     * Return the TotalFreightCost of the order
     */
    public double getTotalFreightCost() {
        return (this._mTotalFreightCost);
    }

    /**
     * Set the TotalFreightCost of the order
     */
    public void setTotalFreightCost(double pVal) {
        this._mTotalFreightCost = pVal;
    }


    /**
     * Return the TotalTaxCost of the order
     */
    public double getTotalTaxCost() {
        return (this._mTotalTaxCost);
    }

    /**
     * Set the TotalTaxCost of the order
     */
    public void setTotalTaxCost(double pVal) {
        this._mTotalTaxCost = pVal;
    }


    /**
     * Return the TotalMiscCost of the order
     */
    public double getTotalMiscCost() {
        return (this._mTotalMiscCost);
    }

    /**
     * Set the TotalMiscCost of the order
     */
    public void setTotalMiscCost(double pVal) {
        this._mTotalMiscCost = pVal;
    }

///////////////////////////////
    /**
     * Return the TotalAmount of the order
     */
    public double getTotalAmountAfter() {
        return (this._mTotalAmountAfter);
    }

    /**
     * Set the TotalAmount of the order
     */
    public void setTotalAmountAfter(double pVal) {
        this._mTotalAmountAfter = pVal;
    }


    /**
     * Return the SubTotal of the order
     */
    public double getSubTotalAfter() {
        return (this._mSubTotalAfter);
    }

    /**
     * Set the SubTotal of the order
     */
    public void setSubTotalAfter(double pVal) {
        this._mSubTotalAfter = pVal;
    }


    /**
     * Return the TotalFreightCost of the order
     */
    public double getTotalFreightCostAfter() {
        return (this._mTotalFreightCostAfter);
    }

    /**
     * Set the TotalFreightCost of the order
     */
    public void setTotalFreightCostAfter(double pVal) {
        this._mTotalFreightCostAfter = pVal;
    }


    /**
     * Return the TotalTaxCost of the order
     */
    public double getTotalTaxCostAfter() {
        return (this._mTotalTaxCostAfter);
    }

    /**
     * Set the TotalTaxCost of the order
     */
    public void setTotalTaxCostAfter(double pVal) {
        this._mTotalTaxCostAfter = pVal;
    }


    /**
     * Return the TotalMiscCost of the order
     */
    public double getTotalMiscCostAfter() {
        return (this._mTotalMiscCostAfter);
    }

    /**
     * Set the TotalMiscCost of the order
     */
    public void setTotalMiscCostAfter(double pVal) {
        this._mTotalMiscCostAfter = pVal;
    }
////////////////////////////////
    /**
     * Return the NewContractIdS of the order
     */
    public String getNewContractIdS() {
        return (this._mNewContractIdS);
    }

    /**
     * Set the NewContractIdS of the order
     */
    public void setNewContractIdS(String pVal) {
        this._mNewContractIdS = pVal;
    }

    /**
     * Return the AllItemsStatus of the order
     */
    public String getAllItemsStatus() {
        return (this._mAllItemsStatus);
    }

    /**
     * Set the AllItemsStatus of the order
     */
    public void setAllItemsStatus(String pVal) {
        this._mAllItemsStatus = pVal;
    }


  // ------------------------------------------------------------ Public Methods


    /**
     * Reset all properties to their default values.
     *
     * @param mapping The mapping used to select this instance
     * @param request The servlet request we are processing
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
      _mAllItemsStatus = null;
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
