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
public final class PurchaseOrderOpDetailForm extends ActionForm {


  // -------------------------------------------------------- Instance Variables


    private PurchaseOrderStatusDescDataView    _mPurchaseOrderStatusDescDataView = PurchaseOrderStatusDescDataView.createValue();
    private OrderPropertyData  _mOrderPropertyDetail = OrderPropertyData.createValue();
    private OrderPropertyDataVector _mOrderPropertyList = new OrderPropertyDataVector();
    private OrderMetaDataVector _mOrderMetaList = new OrderMetaDataVector();
    private OrderItemDescDataVector  _mPurchaseOrderItemDescList = new OrderItemDescDataVector();
    private DistributorData _mDistributorData = DistributorData.createValue();
    private OrderStatusDescData _mOrderStatusDescData = OrderStatusDescData.createValue();
    
    private int _mOrderedNum = 0;
    private int _mAcceptedNum = 0;
    private int _mRejectedNum = 0;
    private int _mShippedNum = 0;
    private int _mBackorderedNum = 0;
    private int _mCanceledNum = 0;
    private int _mSubstitutedNum = 0;
    private int _mInvoicedNum = 0;
    //private Date _mLastDate = null;
    

    private BigDecimal _mOrderTotalAmount = new BigDecimal(0);
    private BigDecimal _mMiscellaneousAmount = new BigDecimal(0);

    
    private String _mPurchaseOrderStatus = "";
    private String _mTargetShipDate = "";
    private String _mShippingStatus = "";
    private ReturnRequestDescDataView returnRequestDescData 
        = new ReturnRequestDescDataView(ReturnRequestData.createValue(),new OrderItemDescDataVector(),OrderAddressData.createValue());
    private String returnRequestDateRecievedString = "";
    
    /** Holds value of property openLineStatusCd. */
    private String openLineStatusCd;

    /**
     * Holds value of property orderItemActionSelection.
     */
    private Integer[] orderItemActionSelection;
    
    private int mDistNoteLine = -1;
  // ---------------------------------------------------------------- Properties

    
    /**
     * <code>getTargetShipDate</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getTargetShipDate() {
        return (this._mTargetShipDate);
    }

    /**
     * <code>setTargetShipDate</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setTargetShipDate(String pVal) {
        this._mTargetShipDate = pVal;
    }
    
    /**
     * <code>getShippingStatus</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getShippingStatus() {
        return (this._mShippingStatus);
    }

    /**
     * <code>setShippingStatus</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setShippingStatus(String pVal) {
        this._mShippingStatus = pVal;
    }

    
    /**
     * Return the orderStatus detail object
     */
    public PurchaseOrderStatusDescDataView getPurchaseOrderStatusDescDataView() {
        return (this._mPurchaseOrderStatusDescDataView);
    }

    /**
     * Set the orderStatus detail object
     */
    public void setPurchaseOrderStatusDescDataView(PurchaseOrderStatusDescDataView detail) {
        this._mPurchaseOrderStatusDescDataView = detail;
    }

    public DistributorData getDistributorData(){
        return this._mDistributorData;
    }
    public void setDistributorData(DistributorData pVal){
        this._mDistributorData = pVal;
    }

    public OrderStatusDescData getOrderStatusDescData(){
        return this._mOrderStatusDescData;
    }
    public void setOrderStatusDescData(OrderStatusDescData pVal){
        this._mOrderStatusDescData = pVal;
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
     * <code>getPurchaseOrderItemDescList</code> method.
     *
     * @return a <code>OrderItemDescDataVector</code> value
     */
    public OrderItemDescDataVector getPurchaseOrderItemDescList() {
        if( null == this._mPurchaseOrderItemDescList) {
            this._mPurchaseOrderItemDescList = new OrderItemDescDataVector();
        }
        return (this._mPurchaseOrderItemDescList);
    }

    /**
     * <code>setPurchaseOrderItemDescList</code> method.
     *
     * @param pVal a <code>OrderItemDescDataVector</code> value
     */
    public void setPurchaseOrderItemDescList(OrderItemDescDataVector pVal) {
        this._mPurchaseOrderItemDescList = pVal;
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
    /*public Date getLastDate() {
        return (this._mLastDate);
    }*/

    /**
     * Set the last action date
     */
    /*public void setLastDate(Date pVal) {
        this._mLastDate = pVal;
    }*/

        
    /**
     * Return the OrderTotalAmount of the order
     */
    public BigDecimal getOrderTotalAmount() {
	return _mOrderStatusDescData.getSumOfAllOrderCharges();
    }

    /**
     * Set the OrderTotalAmount of the order
     */
    public void setOrderTotalAmount(BigDecimal pVal) {
        this._mOrderTotalAmount = pVal;
    }
    
    /**
     * Return the MiscellaneousAmount of the order
     */
    public BigDecimal getMiscellaneousAmount() {
        return (this._mMiscellaneousAmount);
    }

    /**
     * Set the MiscellaneousAmount of the order
     */
    public void setMiscellaneousAmount(BigDecimal pVal) {
        this._mMiscellaneousAmount = pVal;
    }


    /**
     * Return the PurchaseOrderStatus of the order
     */
    public String getPurchaseOrderStatus() {
        return (this._mPurchaseOrderStatus);
    }

    /**
     * Set the PurchaseOrderStatus of the order
     */
    public void setPurchaseOrderStatus(String pVal) {
        this._mPurchaseOrderStatus = pVal;
    }
    
    
    
  // ------------------------------------------------------------ Public Methods
    public OrderItemDescData getPurchaseOrderItemDesc(int idx) {

        if (_mPurchaseOrderItemDescList == null) {
            _mPurchaseOrderItemDescList = new OrderItemDescDataVector();
        }
        while (idx >= _mPurchaseOrderItemDescList.size()) {
            _mPurchaseOrderItemDescList.add(OrderItemDescData.createValue());
        }    
        
        return (OrderItemDescData) _mPurchaseOrderItemDescList.get(idx);
    }
    
    private IdVector mOrderItemActionSelection = new IdVector();
     public Integer getOrderItemActionSelection(int idx) {
        while (idx >= mOrderItemActionSelection.size()) {
            mOrderItemActionSelection.add(null);
        }    
        
        return (Integer) mOrderItemActionSelection.get(idx);
    }
     
     public void setOrderItemActionSelection(int idx,Integer value) {
        while (idx >= mOrderItemActionSelection.size()) {
            mOrderItemActionSelection.add(null);
        }    
        
        mOrderItemActionSelection.add(idx,value);
    }
     
     public List getOrderItemActionSelectionList(){
         return mOrderItemActionSelection;
     }

    /**
     * Reset all properties to their default values.
     *
     * @param mapping The mapping used to select this instance
     * @param request The servlet request we are processing
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        mOrderItemActionSelection.clear();
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

    /** Getter for property returnRequestDescData.
     * @return Value of property returnRequestDescData.
     *
     */
    public ReturnRequestDescDataView getReturnRequestDescData() {
        return this.returnRequestDescData;
    }    

    /** Setter for property returnRequestDescData.
     * @param returnRequestDescData New value of property returnRequestDescData.
     *
     */
    public void setReturnRequestDescData(ReturnRequestDescDataView returnRequestDescData) {
        this.returnRequestDescData = returnRequestDescData;
    }
    
    /** Getter for property returnRequestDateRecievedString.
     * @return Value of property returnRequestDateRecievedString.
     *
     */
    public String getReturnRequestDateRecievedString() {
        return this.returnRequestDateRecievedString;
    }
    
    /** Setter for property returnRequestDateRecievedString.
     * @param returnRequestDateRecievedString New value of property returnRequestDateRecievedString.
     *
     */
    public void setReturnRequestDateRecievedString(String returnRequestDateRecievedString) {
        this.returnRequestDateRecievedString = returnRequestDateRecievedString;
    }
    
    /** Getter for property openLineStatusCd.
     * @return Value of property openLineStatusCd.
     *
     */
    public String getOpenLineStatusCd() {
        return this.openLineStatusCd;
    }
    
    /** Setter for property openLineStatusCd.
     * @param openLineStatusCd New value of property openLineStatusCd.
     *
     */
    public void setOpenLineStatusCd(String openLineStatusCd) {
        this.openLineStatusCd = openLineStatusCd;
    }

    public OrderMetaDataVector getOrderMetaList() {
        if( null == this._mOrderMetaList) {
		    this._mOrderMetaList = new OrderMetaDataVector();
		}
		return (this._mOrderMetaList);
    }

    public void setOrderMetaList(OrderMetaDataVector v) {
	    this._mOrderMetaList = v;
    }

    public int getDistNoteLine(){return mDistNoteLine;}
    public void setDistNoteLine(int pVal){mDistNoteLine = pVal;}

    
}
