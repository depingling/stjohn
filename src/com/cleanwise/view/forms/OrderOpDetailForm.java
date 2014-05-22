/**
 * Title:        OrderOpDetail
 * Description:  This is the Struts ActionForm class for the OrderStatus detail page.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author
 */

package com.cleanwise.view.forms;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.*;
import java.util.ArrayList;

/**
 * Form bean for the view OrderStatus page.  This form has the following fields,
 * with default values in square brackets:
 * <ul>
 * <li><b>orderStatusDetail</b> - A OrderData object
 * </ul>
 */
public final class OrderOpDetailForm extends ActionForm {


    // -------------------------------------------------------- Instance Variables

    private String _lastAction;
    private OrderStatusDescData    _mOrderStatusDetail = OrderStatusDescData.createValue();
    private OrderPropertyData  _mOrderPropertyDetail = OrderPropertyData.createValue();
    private OrderPropertyDataVector _mOrderPropertyList = new OrderPropertyDataVector();
    private OrderItemDescDataVector  _mOrderItemDescList = new OrderItemDescDataVector();
    private boolean _mSimpleServiceOrderFl =false;

    public boolean getOrderHasInventoryItem()    {

        for ( int i = 0; null != _mOrderItemDescList &&
                  i < _mOrderItemDescList.size(); i++ )	    {
	    OrderItemDescData oid = (OrderItemDescData)_mOrderItemDescList.get(i);
	    if ( oid.getIsAnInventoryItem() )    {
		return true;
	    }
	}
        return false;
    }

    private int _mOrderedNum = 0;
    private int _mAcceptedNum = 0;
    private int _mRejectedNum = 0;
    private int _mShippedNum = 0;
    private int _mBackorderedNum = 0;
    private int _mCanceledNum = 0;
    private int _mSubstitutedNum = 0;
    private int _mInvoicedNum = 0;
    private Date _mLastDate = null;
    private String _mApproveDate = null;
    private String _mRequestPoNum = null;

    private double _mTotalAmount = 0D;
    private double _mSubTotal = 0D;
    private BigDecimal _mTotalFreightCost = new BigDecimal(0),
	_mTotalMiscCost = new BigDecimal(0),
	_mRushOrderCharge = new BigDecimal(0),
    _smallOrderFeeAmt = new BigDecimal(0),
    _fuelSurchargeAmt = new BigDecimal(0),
    _discountAmt = new BigDecimal(0),
    _serviceFeeAmt = new BigDecimal(0);
    private double _mTotalTaxCost = 0D;

    private String _discountStr = new String("");
    private String _mTotalFreightCostS = new String("");
    private String _mTotalMiscCostS = new String("");
    private int _mHandlingChoise = 2;
    private boolean _mHandlingChangedFlag = false,
	_bypassOrderRouting = false;
    private String _mFuelSurcharge = new String("");

    private String _mNewContractIdS = new String("");
    private String[] _mSelectItems = new String[0];

    private boolean _mFullControlFl = false;

    /** Holds value of property returnedNum. */
    private int returnedNum;

    /** Holds value of property customerOrderNotes. */
    private OrderPropertyDataVector customerOrderNotes;

    /** Holds value of property customerComment. */
    private String customerComment;

    /**
     * Holds value of property orderPlacedBy.
     */
    private String orderPlacedBy;

    private NoteJoinViewVector _siteNotes = null;

    /**
     * Holds value of property processCustomerWorkflow.
     */
    private boolean processCustomerWorkflow;
    private boolean showDistNoteFl = false;
    private String _workOrderNumber = "";
    private WorkOrderData _associatedWorkOrder = null;

    private IdVector _associatedServiceTickets;
    private IdVector _unavailableServiceTickets;

    private ArrayList<String> _userMessages = new ArrayList();
    private boolean hidePack = false;
    private boolean hideUom = false;
    private boolean hideManufName = false;
    private boolean hideManufSku = false;

    // ----------------------------------------------------- Properties

    /**
     * Return the lastAction object
     */
    public String getLastAction() {
        return (this._lastAction);
    }

    /**
     * Set the lastAction  object
     */
    public void setLastAction(String pVal) {
        this._lastAction = pVal;
    }

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
     * Based on the current totals for this order
     * calculate the total amount.  This include all product
     * charges and various freight, handling, rush, and tax charges.
     */
    public void calculateTotalAmount() {

        BigDecimal recalct = new BigDecimal(0);
        if ( _mTotalFreightCost != null ) {
            recalct = recalct.add(_mTotalFreightCost);
        }
        if ( _mTotalMiscCost != null ) {
            recalct = recalct.add(_mTotalMiscCost);
        }
        if ( _mRushOrderCharge != null ) {
            recalct = recalct.add(_mRushOrderCharge);
        }

        if ( _fuelSurchargeAmt != null ) {
            recalct = recalct.add(_fuelSurchargeAmt);
        }

        if (_smallOrderFeeAmt != null ) {
            recalct = recalct.add(_smallOrderFeeAmt);
        }

        if (_discountAmt != null ) {
            if (_discountAmt.doubleValue() > 0) {
                recalct = recalct.add(new BigDecimal(-_discountAmt.doubleValue()));
            } else {
                recalct = recalct.add(_discountAmt);
            }
        }
        
        _mTotalAmount = recalct.doubleValue() + _mTotalTaxCost
	    + _mSubTotal;
        _mTotalAmount=new BigDecimal(_mTotalAmount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
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
        calculateTotalAmount();
    }


    /**
     * Return the TotalFreightCost of the order
     */
    public BigDecimal getTotalFreightCost() {
        return (this._mTotalFreightCost);
    }
    /**
     * Set the TotalFreightCost of the order
     */
    public void setTotalFreightCost(BigDecimal pVal) {
        this._mTotalFreightCost = pVal;
        calculateTotalAmount();
    }

    public BigDecimal getRushOrderCharge() {
        return (this._mRushOrderCharge);
    }
    public void setRushOrderCharge(BigDecimal pVal) {
        this._mRushOrderCharge = pVal;
        calculateTotalAmount();
    }

    public void setRushOrderChargeS(String pVal) {
        BigDecimal rc = null;
        if ( null == pVal || pVal.length() == 0 ) {
            rc = new BigDecimal(0);
        }
        else {
            rc = new BigDecimal(pVal);
        }
        setRushOrderCharge(rc);
    }

    public String getRushOrderChargeS() {
        if ( null == this._mRushOrderCharge ) {
            return "0";
        }

        return (this._mRushOrderCharge.toString());
    }


    /**
     * Return the TotalMiscCost of the order
     */
    public BigDecimal getTotalMiscCost() {
        return (this._mTotalMiscCost);
    }
    /**
     * Set the TotalMiscCost of the order
     */
    public void setTotalMiscCost(BigDecimal pVal) {
        this._mTotalMiscCost = pVal;
        calculateTotalAmount();
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
        calculateTotalAmount();
    }



    /**
     * Return the HandlingFlag
     */
    public boolean getHandlingChangedFlag() {
        return (this._mHandlingChangedFlag);
    }
    /**
     * Set the HandlingFlag
     */
    public void setHandlingChangedFlag(boolean pVal) {
        this._mHandlingChangedFlag = pVal;
    }

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


    public OrderItemDescData getOrderItemDesc(int idx) {

        if (_mOrderItemDescList == null) {
            _mOrderItemDescList = new OrderItemDescDataVector();
        }
        while (idx >= _mOrderItemDescList.size()) {
            _mOrderItemDescList.add(OrderItemDescData.createValue());
        }

        return (OrderItemDescData) _mOrderItemDescList.get(idx);
    }

    public ReplacedOrderItemView getReplacedOrderItem(int pOrderItemId) {
	ReplacedOrderViewVector replacedOrders = _mOrderStatusDetail.getReplacedOrders();
	if (replacedOrders == null || replacedOrders.size()==0) {
	    return null;
	}
	for(Iterator iter=replacedOrders.iterator(); iter.hasNext();) {
	    ReplacedOrderView roVw = (ReplacedOrderView) iter.next();
	    ReplacedOrderItemViewVector roiVwV = roVw.getItems();
	    if(roiVwV==null) {
		return null;
	    }
	    for(Iterator iter1=roiVwV.iterator(); iter1.hasNext();) {
		ReplacedOrderItemView roiVw = (ReplacedOrderItemView) iter1.next();
		if(roiVw.getOrderItemId()==pOrderItemId) {
		    return roiVw;
		}
	    }
	}
	return null;
    }

    public String [] getSelectItems() {
        return _mSelectItems;
    }

    public void setSelectItems(String[] pItemIds) {
        _mSelectItems = pItemIds;
    }

    /**
     * Return the TotalFreightCost of the order
     */
    public String getTotalFreightCostS() {
        return (this._mTotalFreightCostS);
    }

    /**
     * Set the FuelSurcharge of the order
     */
    public void setFuelSurcharge(String pVal) {
        this._mFuelSurcharge = pVal;
    }

    /**
     * Return the FuelSurcharge of the order
     */
    public String getFuelSurcharge() {
        return (this._mFuelSurcharge);
    }

    /**
     * Set the TotalFreightCost of the order
     */
    public void setTotalFreightCostS(String pVal) {
        this._mTotalFreightCostS = pVal;
    }

    /**
     * Return the TotalMiscCost of the order
     */
    public String getTotalMiscCostS() {
        return (this._mTotalMiscCostS);
    }

    /**
     * Set the TotalMiscCost of the order
     */
    public void setTotalMiscCostS(String pVal) {
        this._mTotalMiscCostS = pVal;
    }

    /**
     * Return the HandlingChoise switch
     */
    public int getHandlingChoise() {
        return (this._mHandlingChoise);
    }
    /**
     * Set the HandlingChoise switch
     */
    public void setHandlingChoise(int pVal) {
        this._mHandlingChoise = pVal;
    }

    /**
     * Return the Site Notes
     */
    public NoteJoinViewVector getSiteNotes() {
        return (this._siteNotes);
    }
    /**
     * Set the Site Notes
     */
    public void setSiteNotes(NoteJoinViewVector pVal) {
        this._siteNotes = pVal;
    }

    private int mStoreId;
    public void setStoreId(int v) {
        mStoreId = v;
    }
    public int getStoreId() {
        return mStoreId;
    }

    private ShoppingCartItemDataVector _items = new ShoppingCartItemDataVector();
    public void setItems(ShoppingCartItemDataVector pValue) {
        _items = pValue;
    }
    public ShoppingCartItemDataVector getItems() {
        return _items;
    }

    private SiteData mSite;
    public void setSite(SiteData siteD) {
        mSite = siteD;
    }
    public SiteData getSite() {
        return mSite;
    }

    private ShoppingCartDistDataVector mCartDistributors;
    public ShoppingCartDistDataVector getCartDistributors() {
        ShoppingCartDistDataVector cartDistV;
        if (null != mCartDistributors) {
            cartDistV = mCartDistributors;
        }
        else {
        	ContractData contr = null;
        	if(this.getSite() != null){

        		contr = this.getSite().getContractData();
        	}
            cartDistV= new ShoppingCartDistDataVector(this.getItems(), this.getStoreId(), contr);
            mCartDistributors = cartDistV;
        }
        return cartDistV;
    }
    public void setCartDistributors(ShoppingCartDistDataVector distV) {
        mCartDistributors = distV;
        //if (!isFreightByContract())
        //	setFreightAmt(mCartDistributors.getTotalFreightCost());
    }

    public ShoppingCartDistData getCartDistributors(int i) {
    	ShoppingCartDistData distD = (ShoppingCartDistData) mCartDistributors.get(i);
        return distD;
    }


    /**
     * Reset all properties to their default values.
     *
     * @param mapping The mapping used to select this instance
     * @param request The servlet request we are processing
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
	this._bypassOrderRouting = false;
        //sthis.processCustomerWorkflow = false;
	this._mSelectItems = new String[0];
        this._workOrderNumber = "";
        this._userMessages.clear(); 
        hidePack = false;
        hideUom = false;
        hideManufName = false;
        hideManufSku = false;
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

    /** Getter for property returnedNum.
     * @return Value of property returnedNum.
     *
     */
    public int getReturnedNum() {
        return this.returnedNum;
    }

    /** Setter for property returnedNum.
     * @param returnedNum New value of property returnedNum.
     *
     */
    public void setReturnedNum(int returnedNum) {
        this.returnedNum = returnedNum;
    }

    public boolean isBypassOrderRouting() {
	return this._bypassOrderRouting;
    }

    public void setBypassOrderRouting(boolean v) {
	this._bypassOrderRouting = v;
    }

    public boolean getFullControlFl() {
	return this._mFullControlFl;
    }

    public void setFullControlFl (boolean v) {
	this._mFullControlFl  = v;
    }

    /** Getter for property customerOrderNotes.
     * @return Value of property customerOrderNotes.
     *
     */
    public OrderPropertyDataVector getCustomerOrderNotes() {
        return this.customerOrderNotes;
    }

    /** Setter for property customerOrderNotes.
     * @param customerOrderNotes New value of property customerOrderNotes.
     *
     */
    public void setCustomerOrderNotes(OrderPropertyDataVector customerOrderNotes) {
        this.customerOrderNotes = customerOrderNotes;
    }

    /** Getter for property customerComment.
     * @return Value of property customerComment.
     *
     */
    public String getCustomerComment() {
        return this.customerComment;
    }

    /** Setter for property customerComment.
     * @param customerComment New value of property customerComment.
     *
     */
    public void setCustomerComment(String customerComment) {
        this.customerComment = customerComment;
    }

    /**
     * Getter for property orderPlacedBy.
     * @return Value of property orderPlacedBy.
     */
    public String getOrderPlacedBy() {

        return this.orderPlacedBy;
    }

    /**
     * Setter for property orderPlacedBy.
     * @param orderPlacedBy New value of property orderPlacedBy.
     */
    public void setOrderPlacedBy(String orderPlacedBy) {

        this.orderPlacedBy = orderPlacedBy;
    }

    /**
     * Getter for property processCustomerWorkflow.
     * @return Value of property processCustomerWorkflow.
     */
    public boolean isProcessCustomerWorkflow() {
        return this.processCustomerWorkflow;
    }

    /**
     * Setter for property processCustomerWorkflow.
     * @param processCustomerWorkflow New value of property processCustomerWorkflow.
     */
    public void setProcessCustomerWorkflow(boolean processCustomerWorkflow) {
        this.processCustomerWorkflow = processCustomerWorkflow;
    }

    private InvoiceCustViewVector mInvoices;
    public InvoiceCustViewVector getInvoiceList() {
	if ( null == mInvoices ) {
	    mInvoices = new InvoiceCustViewVector();
	}
	return mInvoices;
    }
    public void setInvoiceList(InvoiceCustViewVector v) {
	mInvoices = v;
    }

    public boolean getShowDistNoteFl() {return showDistNoteFl;}
    public boolean isShowDistNoteFl() {return showDistNoteFl;}
    public void setShowDistNoteFl(boolean pVal) {showDistNoteFl = pVal;}

    public void setSimpleServiceOrderFl(boolean simpleServiceOrderFl) {
        this._mSimpleServiceOrderFl = simpleServiceOrderFl;
    }


    public boolean getSimpleServiceOrderFl() {
        return _mSimpleServiceOrderFl;
    }

    // Distributor information.
    public class DistSummaryLine {
        public boolean mAllowChangeToShipping = false;
        public String mDistName = "";
        public String mDistErpNum = "";
        public int mLineItemCount = 0, mOrderId;
        public java.math.BigDecimal mDistTotal = null;
	public DistOptionsForShippingView mDistShipOpt = null;
        public String toString() {
	    String s = "<tr> <td class='results'> " + mDistName + "</td>" +
		" <td>" + mLineItemCount + "</td>" +
		" <td align=right>" + mDistTotal + "</td>"
		;

	    if ( mDistName.length() > 1 && mAllowChangeToShipping ) {
		// get the shipping option for this distributor.

		if ( null != mDistShipOpt ) {
		    s+= "<td class='order_shipper'>";
		    s+= " <a href=orderOpDetail.do?action=update_shipping&id="
			+  mOrderId + "&disterpnum=" + mDistErpNum
			+ "&freightHandler="
			+ mDistShipOpt.getFreightHandlerBusEntity().getBusEntityId() + ">"
			+ mDistShipOpt.getFreightHandlerBusEntity().getShortDesc()
			+ "</a>";
		    s+= "</td> ";
		    s+= "<td class='order_shipper'>";
		    s+= " <a href=orderOpDetail.do?action=update_shipping&id="
			+  mOrderId + "&disterpnum=" + mDistErpNum + "&freightHandler=0" + ">"
			+ "Clear</a>";
		    s+= "</td> ";
		}
	    }
	    s+= "</tr>";
	    return s;
        }
    }

    public class DistSummary {

	OrderStatusDescData m_orderStatusDetail;

	String mOrderStatusCd;
	int mSummaryOrderId = 0;

	public DistSummary( OrderStatusDescData orderStatusDetail) {
	    m_orderStatusDetail = orderStatusDetail;
	    OrderData oD = orderStatusDetail.getOrderDetail();
	    mSummaryOrderId = oD.getOrderId();

	    if ( null == oD ) {
		mOrderStatusCd = "";
	    } else {
		mOrderStatusCd = oD.getOrderStatusCd();
	    }
	}

	public boolean allowChangeToShipping() {
	    return
		RefCodeNames.ORDER_STATUS_CD.PENDING_ORDER_REVIEW.equals
		(mOrderStatusCd) ||
		RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW.equals
		(mOrderStatusCd)
		;
	}

	java.util.HashMap mDistMap = new java.util.HashMap();

	public void addItem(OrderItemDescData pOid ) {
	    String t = pOid.getDistName();
	    if ( null == t ) t = "-";
	    String t2 =	 pOid.getOrderItem().getDistErpNum();
	    if ( null == t2 ) t2 = "-";
	    DistOptionsForShippingView distShipOpt =
		m_orderStatusDetail.getDistShipOption(t2);

	    DistSummaryLine dline = (DistSummaryLine)mDistMap.get(t);
	    if (dline == null) {
		dline = new DistSummaryLine();
		dline.mAllowChangeToShipping = allowChangeToShipping();
		dline.mDistName = t;
		dline.mDistErpNum = t2;
		dline.mDistShipOpt = distShipOpt;
		dline.mOrderId = mSummaryOrderId;
		dline.mDistTotal =  new java.math.BigDecimal(0);
	    }
	    dline.mLineItemCount++;

	    java.math.BigDecimal dcost = pOid.getOrderItem().getDistItemCost()
		, qty = new java.math.BigDecimal
		(pOid.getOrderItem().getTotalQuantityOrdered());

	    if ( null != dcost ) {
		dcost =dcost.multiply(qty);
		dline.mDistTotal = dline.mDistTotal.add(dcost);
	    }
	    mDistMap.put(t, dline);

	}

	public java.util.Collection getSummaries() {
	    return mDistMap.values();
	}

    }

    public DistSummary getDistSummary() {

	DistSummary dSummary = new DistSummary(getOrderStatusDetail());
	OrderItemDescDataVector oiList = getOrderItemDescList();
	for ( int i = 0; null != oiList && i < oiList.size(); i++ )   {
	    OrderItemDescData oid = (OrderItemDescData)oiList.get(i);
	    dSummary.addItem(oid);
	}
	return dSummary;
    }

    private String mResultMsg = "";
    public String getResultMessage() {
	return mResultMsg;
    }
    public void setResultMessage(String m) {
	mResultMsg = m;
    }

    public String getApproveDate() {
        return _mApproveDate;
    }

    public void setApproveDate(String approveDate) {
        this._mApproveDate = approveDate;
    }

    public String getRequestPoNum() {
        return _mRequestPoNum;
    }

    public void setRequestPoNum(String _mRequestPoNum) {
        this._mRequestPoNum = _mRequestPoNum;
    }

        public void setSmallOrderFeeAmt(BigDecimal smallOrderFeeAmt) {
        this._smallOrderFeeAmt = smallOrderFeeAmt;
    }


    public BigDecimal getSmallOrderFeeAmt() {
        return _smallOrderFeeAmt;
    }

    public void setFuelSurchargeAmt(BigDecimal fuelSurchargeAmt) {
        this._fuelSurchargeAmt = fuelSurchargeAmt;
    }

    public BigDecimal getFuelSurchargeAmt() {
        return _fuelSurchargeAmt;
    }
    
    public void setServiceFeeAmt(BigDecimal serviceFeeAmt) {
        this._serviceFeeAmt = serviceFeeAmt;
    }

    public BigDecimal getServiceFeeAmt() {
        return _serviceFeeAmt;
    }

    public void setDiscountAmt(BigDecimal discountAmt) {
        _discountAmt = discountAmt;
    }

    public BigDecimal getDiscountAmt() {
        return _discountAmt;
    }

    public String getDiscountStr() {
        return _discountStr;
    }

    public void setDiscountStr(String discountStr) {
        _discountStr = discountStr;
    }
    
    public String getWorkOrderNumber() {
        return _workOrderNumber;
    }

    public void setWorkOrderNumber(String workOrderNumber) {
        this._workOrderNumber = workOrderNumber;
    }

    public ArrayList<String> getUserMessages() {
        return _userMessages;
    }

    public void setUserMessages(ArrayList<String> userMessages) {
        this._userMessages = userMessages;
    }

    public WorkOrderData getAssociatedWorkOrder() {
        return _associatedWorkOrder;
    }

    public void setAssociatedWorkOrder(WorkOrderData associatedWorkOrder) {
        this._associatedWorkOrder = associatedWorkOrder;
    }

    public void setAssociatedServiceTickets(IdVector pAssociatedServiceTickets) {
        _associatedServiceTickets = pAssociatedServiceTickets;
    }

    public IdVector getAssociatedServiceTickets() {
        return _associatedServiceTickets;
    }

    public void setUnavailableServiceTickets(IdVector pUnavailableServiceTickets) {
        _unavailableServiceTickets = pUnavailableServiceTickets;
    }

    public IdVector getUnavailableServiceTickets() {
        return _unavailableServiceTickets;
    }

	public void setHidePack(boolean hidePack) {
		this.hidePack = hidePack;
	}

	public boolean isHidePack() {
		return hidePack;
	}

	public void setHideUom(boolean hideUom) {
		this.hideUom = hideUom;
	}

	public boolean isHideUom() {
		return hideUom;
	}

	public void setHideManufName(boolean hideManufName) {
		this.hideManufName = hideManufName;
	}

	public boolean isHideManufName() {
		return hideManufName;
	}

	public void setHideManufSku(boolean hideManufSku) {
		this.hideManufSku = hideManufSku;
	}

	public boolean isHideManufSku() {
		return hideManufSku;
	}
};
