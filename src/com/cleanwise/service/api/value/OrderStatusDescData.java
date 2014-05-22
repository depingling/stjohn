package com.cleanwise.service.api.value;


import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;

import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;

/**
 *  <code>OrderStatusDescData</code>
 *  describes an order status.
 *
 *@author     liang
 *@created    December 07, 2001
 */
public class OrderStatusDescData extends OrderInfoBase {
  //Do not remove or modify next line. It would break object DB saving
  private static final long serialVersionUID = 2486187129477516943L;

    private OrderData mOrderDetail;
    private OrderItemDataVector mOrderItemList;
    private int    mAccountId   = 0;
    private String mAccountName = new String("");
    private String mErpOrderNum = new String("");
    private AddressData mSiteAddress = AddressData.createValue();
    private OrderAddressData mShipTo = OrderAddressData.createValue();
    private OrderAddressData mBillTo = OrderAddressData.createValue()
    ,mCustomerBillTo = null  // Init to null to check for its presence.
    ;
    private OrderAddressData
      mCustomerShipTo = null;
    private OrderMetaDataVector mOrderMeta = null;

    private String mExceptionTypeCd =  new String("");
    private String mDistName =  new String("");
    private String mShipFromName = new String("");

    private boolean mAllowModifFl = false;

    private OrderPropertyDataVector mOrderPropertyList = new OrderPropertyDataVector();
    private ReplacedOrderViewVector mReplacedOrders = null;
    private OrderData mConsolidatedOrder = null;
    private Boolean received = null;
    private boolean autoOrder = false;
    private boolean ackOnHold = false;
    
    private IdVector mUserApprovableReasonCodeIds = null;
    private String mBudgetYearPeriod;

    /**
     *  Constructor.
     */
    public OrderStatusDescData() { }


    /**
     *  Constructor.
     *
     *@param  parm1  Description of Parameter
     *@param  parm2  Description of Parameter
     *@param  parm3  Description of Parameter
     */
    public OrderStatusDescData(OrderData parm1, OrderItemDataVector parm2) {
        mOrderDetail = parm1;
        mOrderItemList = parm2;
    }

    /**
     *Order Credit Card property
     */
    private OrderCreditCardData mOrderCreditCardData;
    public OrderCreditCardData getOrderCreditCardData(){
        return mOrderCreditCardData;
    }
    public void setOrderCreditCardData
	(OrderCreditCardData pOrderCreditCardData){
        mOrderCreditCardData = pOrderCreditCardData;
    }

    public boolean hasCreditCard() {
	return (mOrderCreditCardData != null);
    }


    /**
     *  Set the mOrderDetail field.
     *
     *@param  v   The new OrderDetail value
     */
    public void setOrderDetail(OrderData v) {
        mOrderDetail = v;
    }


    /**
     *  Set the mOrderItemList field.
     *
     *@param  v   The new OrderItemList value
     */
    public void setOrderItemList(OrderItemDataVector v) {
        mOrderItemList = v;
    }

    /**
     * sets the cached received property.  If it is not set then the orderpropertylist data
     * is used.
     */
    public void setReceived(boolean val){
    	received = new Boolean(val);
    }

    /**
    *Returns true if this order has been reieved against
    */
    public boolean isReceived(){
    	if(received != null){
    		return received.booleanValue();
    	}
        if(mOrderPropertyList == null){
            return false;
        }
        Iterator it = mOrderPropertyList.iterator();
        while(it.hasNext()){
            OrderPropertyData oi = (OrderPropertyData) it.next();
            if(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_RECEIVED.equals(oi.getValue())){
                return true;
            }
        }
        return false;

    }

    /**
     *Returns true if this order contains auto order item
     */
     public boolean isAutoOrder(){
         return autoOrder;
     }

     public void setAutoOrder(boolean autoOrder) {
 		this.autoOrder = autoOrder;
 	 }
    /**
     *  Get the mOrderDetail field.
     *
     *@return    OrderData
     */
    public OrderData getOrderDetail() {
        return mOrderDetail;
    }


    /**
     *  Get the mOrderItemList field.
     *
     *@return    OrderItemDataVector
     */
    public OrderItemDataVector getOrderItemList() {
        return mOrderItemList;
    }


    /**
     *  Get the mAccountId field.
     *
     *@return    int
     */
    public int getAccountId() {
        if(mAccountId == 0 && getOrderDetail() != null){
            mAccountId = getOrderDetail().getAccountId();
        }
        return mAccountId;
    }

    /**
     *  Set the mAccountId field.
     *
     *@param  v   The new AccountId value
     */
    public void setAccountId(int v) {
        mAccountId = v;
    }


    /**
     *  Get the mAccountName field.
     *
     *@return    String
     */
    public String getAccountName() {
        return mAccountName;
    }

    /**
     *  Set the mAccountName field.
     *
     *@param  v   The new AccountName value
     */
    public void setAccountName(String v) {
        mAccountName = v;
    }


    /**
     *  Get the mErpOrderNum field.
     *
     *@return    String
     */
    public String getErpOrderNum() {
        return mErpOrderNum;
    }

    /**
     *  Set the mErpOrderNum field.
     *
     *@param  v   The new ErpOrderNum value
     */
    public void setErpOrderNum(String v) {
        mErpOrderNum = v;
    }


    private SiteData mSiteData = null;

    /**
     * Holds value of property shipped.
     */
    private boolean shipped;

    /**
     * Holds value of property placedBy.
     */
    private UserData placedBy;

    /**
     * Holds value of property refOrder.
     */
    private OrderData refOrder;

    public void setOrderSiteData(SiteData v) {
        mSiteData = v;
    }
    public SiteData getOrderSiteData() {
        return mSiteData;
    }

    /**
     *  Get the mSiteAddress field.
     *
     *@return    AddressData
     */
    public AddressData getSiteAddress() {
    	AddressData siteAddress = null;
    	if(mSiteData!=null) {
    		siteAddress = mSiteData.getSiteAddress();
    	} else if (mSiteAddress!=null){
    		siteAddress = mSiteAddress;
    	}
        return siteAddress;
    }
    
    
    /**
     * Holds the site Address 
     * @param siteAddressData
     */
    public void setSiteAddress(AddressData siteAddressData) {
    	mSiteAddress = siteAddressData;
    }

    /**
     *  Get the mShipTo field.
     *
     *@return    OrderAddressData
     */
    public OrderAddressData getShipTo() {
        return mShipTo;
    }

    /**
     *  Set the mShipTo field.
     *
     *@param  v   The new ShipTo value
     */
    public void setShipTo(OrderAddressData v) {
        mShipTo = v;
    }


    /**
     *  Get the mCustomerShipTo field.
     *
     *@return    OrderAddressData
     */
    public OrderAddressData getCustomerShipTo() {
        return mCustomerShipTo;
    }

    /**
     *  Set the mCustomerShipTo field.
     *
     *@param  v   The new CustomerShipTo value
     */
    public void setCustomerShipTo(OrderAddressData v) {
        mCustomerShipTo = v;
    }



    /**
     *  Get the mBillTo field.
     *
     *@return    OrderAddressData
     */
    public OrderAddressData getBillTo() {
        return mBillTo;
    }

    /**
     *  Set the mBillTo field.
     *
     *@param  v   The new BillTo value
     */
    public void setBillTo(OrderAddressData v) {
        mBillTo = v;
    }

    public OrderAddressData getCustomerBillTo() {
        return mCustomerBillTo;
    }

    public boolean getHasRequestedBillTo() {
        if (getCustomerBillTo() == null ) {
            return false;
        }
        if (Utility.areAddressesEquivalent
        (getCustomerBillTo(),getBillTo()) == true ) {
            // Since the addresses are equivalent, return false.
            return false;
        }

        return true;
    }

    public void setCustomerBillTo(OrderAddressData v) {
        mCustomerBillTo = v;
    }

    /**
     *  Get the mExceptionTypeCd field.
     *
     *@return    String
     */
    public String getExceptionTypeCd() {
        return mExceptionTypeCd;
    }

    /**
     *  Set the mExceptionTypeCd field.
     *
     *@param  v   The new ExceptionTypeCd value
     */
    public void setExceptionTypeCd(String v) {
        mExceptionTypeCd = v;
    }


    /**
     *  Get the mDistName field.
     *
     *@return    String
     */
    public String getDistName() {
        return mDistName;
    }

    /**
     *  Set the mDistName field.
     *
     *@param  v   The new DistName value
     */
    public void setDistName(String v) {
        mDistName = v;
    }


    /**
     *  Get the mShipFromName field.
     *
     *@return    String
     */
    public String getShipFromName() {
        return mShipFromName;
    }

    /**
     *  Set the mShipFromName field.
     *
     *@param  v   The new ShipFromName value
     */
    public void setShipFromName(String v) {
        mShipFromName = v;
    }


    /**
     *  Get the mOrderPropertyList field.
     *
     *@return    OrderPropertyDataVector
     */
    public OrderPropertyDataVector getReferenceNumList() {
        return getOrderPropertyList();
    }

    public OrderPropertyDataVector getOrderPropertyList() {
        return mOrderPropertyList;
    }

    /**
     *  Set the mOrderPropertyList field.
     *
     *@param  v   The new OrderPropertyDataVector value
     */
    public void setReferenceNumList(OrderPropertyDataVector v) {
        setOrderPropertyList(v);
    }
    public void setOrderPropertyList(OrderPropertyDataVector v) {
        mOrderPropertyList = v;
    }

    /**
     *  Gets the mmAllowModifFl. Indicates wheather the order could be modified
     *
     *@return    boolean
     */
    public boolean getAllowModifFl() {
        return mAllowModifFl;
    }

    /**
     *  Sets the mAllowModifFl. Indicates wheather the order could be modified
     *
     *@param  v   The new AllowModifFl value
     */
    public void setAllowModifFl(boolean v) {
        mAllowModifFl = v;
    }

    /**
     *  Returns a String representation of the value object
     *
     *@return    The String representation of this OrderItemStatusDescData object
     */
    public String toString() {

        OrderItemData orderItem = null;
        if(null != mOrderItemList && mOrderItemList.size() > 0) {
            orderItem = (OrderItemData) mOrderItemList.get(0);
        }

        return "[" +  "OrderDetail=" +
                mOrderDetail.toString() + ", OrderItem=" + orderItem.toString() +
                ", ReplacedOrders="+ mReplacedOrders +
                ", ConsolidatedOrder="+ mConsolidatedOrder
                 + "]";
    }


    /**
     *  Creates a new OrderItemStatusDescData
     *
     *@return    Newly initialized OrderItemStatusDescData object.
     */
    public static OrderStatusDescData createValue() {
        OrderStatusDescData valueData = new OrderStatusDescData();
        return valueData;
    }

    public void setOrderMetaData(OrderMetaDataVector v) {
        mOrderMeta = v;
    }

    public OrderMetaDataVector getOrderMetaData() {
        return mOrderMeta;
    }

    public String getPendingDate() {
        return findMetaValue(RefCodeNames.ORDER_PROPERTY_TYPE_CD.PENDING_DATE);
    }
    
    public String getDiscountAmount() {
        return findMetaValue(RefCodeNames.CHARGE_CD.DISCOUNT);
    }
    
    public String getSmallOrderAmount() {
        return findMetaValue(RefCodeNames.CHARGE_CD.SMALL_ORDER_FEE);
    }
    
    public String getFuelSurchargeAmount() {
        return findMetaValue(RefCodeNames.CHARGE_CD.FUEL_SURCHARGE);
    }

    public OrderMetaData getMetaObject(String pName) {
        if (mOrderMeta == null || pName == null) return null;
        Date modDate = null;
        OrderMetaData retObject = null;
        for (Object aMOrderMeta : mOrderMeta) {
            OrderMetaData omD = (OrderMetaData) aMOrderMeta;
            String name = omD.getName();
            if (pName.equals(name)) {
                if (modDate == null) {
                    modDate = mOrderDetail.getModDate();
                    retObject = omD;
                } else {
                    Date mD = mOrderDetail.getModDate();
                    if (mD == null) continue;
                    if (modDate.before(mD)) {
                        modDate = mD;
                        retObject = omD;
                    }
                }
            }
        }
        return retObject;
    }

    private String findMetaValue(String pName) {

        if ( mOrderMeta == null ) return "";

        for ( int ii = 0; ii < mOrderMeta.size(); ii++ ) {
            OrderMetaData omd = (OrderMetaData)mOrderMeta.get(ii);
            if ( omd.getName() != null &&
                 omd.getName().equals
                 (pName)) {
                return omd.getValue()   ;
            }
        }
        return "";
    }

    public String getRequestedShipDate() {
        return this.getRequestedShipDate(mOrderMeta);
    }

    public static String
        getRequestedShipDate(OrderMetaDataVector pOrderMeta) {
        if ( pOrderMeta == null || pOrderMeta.size() == 0 ) {
                return "";
        }

        for ( int i = 0; i < pOrderMeta.size(); i++ ) {
            OrderMetaData omd = (OrderMetaData)pOrderMeta.get(i);
            if ( omd.getName() != null &&
            omd.getName().equals
                 (RefCodeNames.ORDER_PROPERTY_TYPE_CD.REQUESTED_SHIP_DATE)) {
                return omd.getValue()   ;
            }

        }
        return "";
    }


    private FreightHandlerViewVector mShippingForThisOrder = null;
    public void addShipVia(FreightHandlerView pValue) {
	if ( null == mShippingForThisOrder ) {
	    mShippingForThisOrder = new FreightHandlerViewVector();
	}
	mShippingForThisOrder.add(pValue);
    }

    // There may be more than one shipping method per order.
    // Some line items may be shipped via one and other items
    // by another.
    public FreightHandlerView getShipVia(int pFreightHandlerId) {

	if ( null == mShippingForThisOrder ) return null;

	for ( int i = 0; i < mShippingForThisOrder.size() ; i++ ) {
	    FreightHandlerView fhv = (FreightHandlerView)mShippingForThisOrder.get(i);
	    if ( pFreightHandlerId == fhv.getBusEntityData().getBusEntityId() ) {
		return fhv;
	    }
	}
	return null;
    }

    public String getShipViaName(int pFreightHandlerId) {
	FreightHandlerView mShipVia = null;
	mShipVia = getShipVia(pFreightHandlerId);
	if ( null == mShipVia ) return "";
	return mShipVia.getBusEntityData().getShortDesc();
    }


    /**
     * Getter for property prevOrder.
     * @return Value of property prevOrder.
     */
    public OrderData getRefOrder() {
        return this.refOrder;
    }

    /**
     * Setter for property prevOrder.
     * @param prevOrder New value of property prevOrder.
     */
    public void setRefOrder(OrderData refOrder) {
        this.refOrder = refOrder;
    }

    /**
     * Getter for property ReplacedOrders.
     * @return Value of property ReplacedOrders.
     */
    public ReplacedOrderViewVector getReplacedOrders() {
        return mReplacedOrders;
    }

    /**
     * Setter for property ReplacedOrders.
     * @param prevOrder New value of property ReplacedOrders.
     */
    public void setReplacedOrders(ReplacedOrderViewVector v) {
        mReplacedOrders = v;
    }

    /**
     * Getter for property ConsolidatedOrder.
     * @return Value of property ConsolidatedOrder.
     */
    public OrderData getConsolidatedOrder() {
        return mConsolidatedOrder;
    }

    /**
     * Setter for property ConsolidatedOrder.
     * @param prevOrder New value of property ConsolidatedOrder.
     */
    public void setConsolidatedOrder(OrderData v) {
        mConsolidatedOrder = v;
    }

    public BigDecimal getRushOrderCharge() {
        return getOrderDetail().getTotalRushCharge();
    }

    public BigDecimal getSumOfAllOrderCharges() {
        OrderStatusDescData osdd = new OrderStatusDescData();
        return osdd.getSumOfAllOrderCharges(this.getOrderDetail());
    }

    /**
     * Getter for property shipped.
     * @return Value of property shipped.
     */
    public boolean isShipped() {

        return this.shipped;
    }

    /**
     * Setter for property shipped.
     * @param shipped New value of property shipped.
     */
    public void setShipped(boolean shipped) {

        this.shipped = shipped;
    }

    /**
     * Getter for property placedBy.
     * @return Value of property placedBy.
     */
    public UserData getPlacedBy() {

        return this.placedBy;
    }

    /**
     * Setter for property placedBy.
     * @param placedBy New value of property placedBy.
     */
    public void setPlacedBy(UserData placedBy) {

        this.placedBy = placedBy;
    }

    public static BigDecimal getSumOfAllOrderCharges(OrderData pOrderData) {


        BigDecimal osum = new BigDecimal(0);
        if ( pOrderData.getTotalPrice() != null ) {
	    osum = osum.add(pOrderData.getTotalPrice());
	}

        if ( pOrderData.getTotalFreightCost() != null ) {
            osum = osum.add(pOrderData.getTotalFreightCost());
        }
        if ( pOrderData.getTotalMiscCost() != null ) {
            osum = osum.add(pOrderData.getTotalMiscCost());
        }
        if ( pOrderData.getTotalTaxCost() != null ) {
            osum = osum.add(pOrderData.getTotalTaxCost());
        }

        if ( pOrderData.getTotalRushCharge() != null ) {
            osum = osum.add(pOrderData.getTotalRushCharge());
        }

        return osum;
    }

    public java.util.Collection getDistERPNumsInItems() {
	OrderItemDataVector oiv = getOrderItemList();
	java.util.HashMap distfound = new java.util.HashMap();
	for ( int i = 0; null != oiv && i < oiv.size(); i++ ) {
	    OrderItemData oid = (OrderItemData) oiv.get(i);
	    String erpnum = oid.getDistErpNum();
	    if ( null != erpnum && erpnum.length() > 0 ) {
		distfound.put(erpnum, erpnum);
	    }
	}
	return distfound.values();
    }

    // Distributo shipping options if any are available.
    private DistOptionsForShippingViewVector mDistShippingOptions;
    public DistOptionsForShippingViewVector getDistShippingOptions() {
	if ( null == mDistShippingOptions ) {
	    mDistShippingOptions = new DistOptionsForShippingViewVector();
	}
	return mDistShippingOptions;
    }
    public void setDistShippingOptions(DistOptionsForShippingViewVector v) {
	mDistShippingOptions = v;
    }

    public DistOptionsForShippingView getDistShipOption(String pDistErpNum) {
	DistOptionsForShippingViewVector v = getDistShippingOptions();
	for ( int i = 0; i < v.size(); i++ ) {
	    DistOptionsForShippingView opt = (DistOptionsForShippingView)v.get(i);
	    if ( pDistErpNum.equals(opt.getDistBusEntity().getErpNum())) {
		return opt;
	    }
	}
	return null;
    }


	public boolean isAckOnHold() {
		return ackOnHold;
	}


	public void setAckOnHold(boolean ackOnHold) {
		this.ackOnHold = ackOnHold;
	}	
	
	/**
	 * @return the userApprovableReasonCodeIds
	 */
	public IdVector getUserApprovableReasonCodeIds() {
		if (mUserApprovableReasonCodeIds == null) {
			mUserApprovableReasonCodeIds = new IdVector();
		}
		return mUserApprovableReasonCodeIds;
	}

	/**
	 * @param userApprovableReasonCodeIds the userApprovableReasonCodeIds to set
	 */
	public void setUserApprovableReasonCodeIds(IdVector userApprovableReasonCodeIds) {
		this.mUserApprovableReasonCodeIds = userApprovableReasonCodeIds;
	}


	public BigDecimal getDiscountedTotal() {
		BigDecimal orderTotal = getSumOfAllOrderCharges();
		String stringDiscountAmt = getDiscountAmount(); 
		BigDecimal discountAmt; 
		if (Utility.isSet(stringDiscountAmt)) {
			discountAmt = new BigDecimal(stringDiscountAmt);
		}
		else {
			discountAmt = new BigDecimal(0);
		}
		discountAmt = discountAmt.setScale(2, BigDecimal.ROUND_HALF_UP); 
		if (discountAmt.compareTo(new BigDecimal(0))!=0 ) { // it is not 0
			orderTotal = orderTotal.add(discountAmt);
		}
		return orderTotal;
	}
	
	public BigDecimal getEstimatedTotal(){
		BigDecimal estimatedTotal = new BigDecimal(0);
		OrderData orderData = this.getOrderDetail();
		
		if ( orderData.getTotalPrice() != null ) {
			estimatedTotal = estimatedTotal.add(orderData.getTotalPrice());
		}
		if ( orderData.getTotalFreightCost() != null ) {
            estimatedTotal = estimatedTotal.add(orderData.getTotalFreightCost());
        }
		if ( orderData.getTotalMiscCost() != null ) {
            estimatedTotal = estimatedTotal.add(orderData.getTotalMiscCost());
        }
		if(orderData.getTotalRushCharge() != null){
			estimatedTotal = estimatedTotal.add(orderData.getTotalRushCharge());
		}
		
		String discountAmtS = getDiscountAmount(); 
		BigDecimal discountAmt = new BigDecimal(0); 
		if (Utility.isSet(discountAmtS)) {
			discountAmt = new BigDecimal(discountAmtS);
		}
		if (discountAmt.compareTo(new BigDecimal(0))!=0 ) { 
			estimatedTotal = estimatedTotal.add(discountAmt);
		}
		
		String smallOrderAmtS = getSmallOrderAmount(); 
		BigDecimal smallOrderAmt = new BigDecimal(0); 
		if (Utility.isSet(smallOrderAmtS)) {
			smallOrderAmt = new BigDecimal(smallOrderAmtS);
		}
		if (smallOrderAmt.compareTo(new BigDecimal(0))!=0 ) { 
			estimatedTotal = estimatedTotal.add(smallOrderAmt);
		}
		
		String fuelSurchargeAmtS = getFuelSurchargeAmount(); 
		BigDecimal fuelSurchargeAmt = new BigDecimal(0); 
		if (Utility.isSet(fuelSurchargeAmtS)) {
			fuelSurchargeAmt = new BigDecimal(fuelSurchargeAmtS);
		}
		if (fuelSurchargeAmt.compareTo(new BigDecimal(0))!=0 ) { 
			estimatedTotal = estimatedTotal.add(fuelSurchargeAmt);
		}
		
		if(orderData.getTotalTaxCost() != null){
			estimatedTotal = estimatedTotal.add(orderData.getTotalTaxCost());
		}
		
        return estimatedTotal;
	}
	
		/**
	    *Returns budget year and period if order property BUDGET_YEAR_PERIOD_LABEL exists
	    *e.g. Period 10 (10/01/2011)
	    */
	    public String getBudgetYearPeriod(){
	    	if(mBudgetYearPeriod != null){
	    		return mBudgetYearPeriod;
	    	}
	        if(mOrderPropertyList == null){
	            return null;
	        }
	        Iterator it = mOrderPropertyList.iterator();
	        while(it.hasNext()){
	            OrderPropertyData oi = (OrderPropertyData) it.next();
	            if(RefCodeNames.ORDER_PROPERTY_TYPE_CD.BUDGET_YEAR_PERIOD_LABEL.equals(oi.getShortDesc())){
	                return oi.getValue();
	            }
	        }
	        return null;

	    }
}

