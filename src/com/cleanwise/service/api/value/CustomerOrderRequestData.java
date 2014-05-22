package com.cleanwise.service.api.value;

import java.lang.*;
import java.util.*;
import java.io.*;


/**
 * <code>CustomerOrderRequestData</code> is a OrderRequestData class 
 *  representing an order request.
 * <p>
 *
 *     An OrderRequestData object <b>may</b> specify:
 *
 *        4) the user id, this represents the user placing
 *           the order, this field is optional, it the field
 *           is null, then this order is assumed to have been
 *           generated rather than placed through the Web interface.
 * 	    
 * </p>
 * @author <a href="mailto:dvieira@DVIEIRA"></a>
 */
public class CustomerOrderRequestData extends OrderRequestData
{
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = -7023044916998038539L;
    private int mUserId = -1;
    private String mUserName = null;
    private int mCostCenterId;
    private int mContractId = -1;
    private double mFreightCharge = 0;
    private double mHandlingCharge = 0;
    private double mRushOrderCharge = 0;

    private OrderFreightDataVector mOrderFreightList;
    private Collection mInventoryOrderQtyLog;
    
    private OrderAddOnChargeDataVector mOrderDiscountList;
	private OrderAddOnChargeDataVector mOrderAddOnChargeList;

    private ServiceTicketOrderRequestView mServiceTicketOrderRequest;


    /**
    * Allow the buyer the ability to specify the freight
    * for this order.
    */
    public void setFreightCharge(double pFreightCharge) {
        this.mFreightCharge = pFreightCharge;
    }

    /**
    * Get the freight specified by the buyer for this order.
    */
    public double getFreightCharge() {
        return mFreightCharge;
    }

    /**
    * Allow the buyer the ability to specify the handling
    * for this order.
    */
    public void setHandlingCharge(double pHandlingCharge) {
        this.mHandlingCharge = pHandlingCharge;
    }

    /**
    * Get the handling specified by the buyer for this order.
    */
    public double getHandlingCharge() {
        return mHandlingCharge;
    }
    
    public void setRushOrderCharge(double pCharge) {
        this.mRushOrderCharge = pCharge;
    }

    public double getRushOrderCharge() {
        return mRushOrderCharge;
    }
    
    /**
     * Get the value of ContractId.
     * @return value of ContractId.
     */
    public int getContractId() {
	return mContractId;
    }
    
    /**
     * Set the value of ContractId.
     * @param v  Value to assign to ContractId.
     */
    public void setContractId(int  v) {
	this.mContractId = v;
    }
    
    /**
     * Get the value of UserName.
     * @return value of UserName.
     */
    public String getUserName() {
	if ( null == mUserName ) {
	    return "---";
	}
	return mUserName;
    }
    
    /**
     * Set the value of UserName.
     * @param v  Value to assign to UserName.
     */
    public void setUserName(String  v) {
	this.mUserName = v;
    }
    
    
    /**
     * Get the value of CostCenterId.
     * @return value of CostCenterId.
     */
    public int getCostCenterId() {
	return mCostCenterId;
    }
    
    /**
     * Set the value of CostCenterId.
     * @param v  Value to assign to CostCenterId.
     */
    public void setCostCenterId(int  v) {
	this.mCostCenterId = v;
    }
    

    /**
     * Get the value of UserId.
     * @return value of UserId.
     */
    public int getUserId() {
	return mUserId;
    }
    
    /**
     * Set the value of UserId.
     * @param v  Value to assign to UserId.
     */
    public void setUserId(int  v) {
	this.mUserId = v;
    }
    

    /**
     * Creates a new <code>CustomerOrderRequestData</code> instance.
     *
     * @param pSiteId an <code>int</code> value
     * @param pAccountId an <code>int</code> value
     * @param pUserId an <code>int</code> value
     */
    public CustomerOrderRequestData (int pSiteId, int pAccountId, int pUserId) {
    super(pSiteId, pAccountId);
	mUserId = pUserId;
    }
    
    public CustomerOrderRequestData () {
    super(-1, -1);
	mUserId = -1;
    }


    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this CustomerOrderRequestData object
     */
    public String toString() {
        return "[ UserId=" + mUserId +
	    ", CostCenterId=" + mCostCenterId +
	    ", ContractId=" + mContractId +
        " Items [ " + super.toString() + " ] ]";
    }

    private AddressData mRequestedBillingAddress;
    private AddressData mAlternateShipToAddress;

    public AddressData getRequestedBillingAddress() {
        return mRequestedBillingAddress;
    }
    public void setRequestedBillingAddress(AddressData v) {
        mRequestedBillingAddress = v;
    }
    
    /**
    * Allow the buyer the ability to specify the freight cost list
    * for this order according to distributors.
    */
    public void setOrderFreightList(OrderFreightDataVector pList) {
        this.mOrderFreightList = pList;
    }
 
     
    /**
    * Get the freight cost list according to distributors specified by the buyer for 
     * this order.
    */
    public OrderFreightDataVector getOrderFreightList() {
        return mOrderFreightList;
    }

    /**
     * Allow the buyer to specify the Discount cost list
     * for this order per distributors.
     */
     public void setOrderDiscountList(OrderAddOnChargeDataVector pList) {
        this.mOrderDiscountList = pList;
     }
     
    /**
     * Get the Discount cost list according to distributors specified by the buyer for 
      * this order.
     */
     public OrderAddOnChargeDataVector getOrderDiscountList() {
         return mOrderDiscountList;
     }
     
    /**
     * Allow the buyer to specify the Additional charges cost list
     * for this order per distributors.
     */
     public void setOrderAddOnChargeList(OrderAddOnChargeDataVector pList) {
        this.mOrderAddOnChargeList = pList;
     }
     
    /**
     * Get the Additional charges cost list according to distributors specified by the buyer for 
      * this order.
     */
     public OrderAddOnChargeDataVector getOrderAddOnChargeList() {
         return mOrderAddOnChargeList;
     }
    public void setInventotyOrderQtyLog(Collection collection) {
        this.mInventoryOrderQtyLog = collection;
    }

    public Collection getInventoryOrderQtyLog() {
        return mInventoryOrderQtyLog;
    }

    public AddressData getAlternateShipToAddress() {
        return mAlternateShipToAddress;
    }

    public void setAlternateShipToAddress(AddressData alternateShipToAddress) {
        this.mAlternateShipToAddress = alternateShipToAddress;
    }

    public void setServiceTicketOrderRequest(ServiceTicketOrderRequestView pServiceTicketOrderRequest) {
        mServiceTicketOrderRequest = pServiceTicketOrderRequest;
    }

    public ServiceTicketOrderRequestView getServiceTicketOrderRequest() {
        return mServiceTicketOrderRequest;
    }
}
