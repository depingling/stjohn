package com.cleanwise.service.api.value;

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.util.RefCodeNames;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.math.BigDecimal;


/**
 * <code>OutboundEDIRequestData</code> is a ValueObject class
 *  representing an order request.
 */
public class OutboundEDIRequestData extends ValueObject
{
  //Do not remove or modify next line. It would break object DB saving
  private static final long serialVersionUID = 3961270002377437393L;
    private OrderData mOrderD;                // used by all outbound EDI transaction
    private OrderItemDataVector mOrderItemDV; // used by 850 outbound EDI transaction
    private OrderItemActionData mOrderItemActionD; // outbound 856
    private OrderItemActionDataVector mOrderItemActionDV; //outbound 856
    private PurchaseOrderData mPurchaseOrderD; // used by 850 outbound EDI transaction
    private InvoiceAbstractionView mInvoiceAbstractionData;    // used by 856, 810 outbound EDI transaction
    private InvoiceDistData mInvoiceDistD;    // used by 856 outbound EDI transaction
    private List mInvoiceDetailReqDV;         // used by 856, 810 outbound EDI transaction
    private String mOriginalDateOrdered;      // used by 850 outbound EDI transaction
    private String mAddressId;                // used by 850 outbound EDI transaction
    private OrderAddressData mShipAddr;
    private FreightHandlerView mShipVia;

    private OrderAddressData mBillAddr;       // used by 850 application processing outbound EDI transaction
    private OrderAddressData mCustShipAddr;   // used by 856, 810 outbound EDI transaction
    private OrderAddressData mCustBillAddr;   // used by 856, 810 outbound EDI transaction
    private String mFreightChargeType;        // used by CIT 810 outbound EDI transaction
    private OrderPropertyDataVector mOrderPropertyDV;          // used by outbound 855 if quantity changed
    private OrderMetaDataVector mOrderMetaDV;
    private int incomingTradingProfileId;
    private ManifestItemView manifestItemView;    //Used by manifesting
    private String accountName, poAccountName;
    private String storeType;
    private String creditCardNumber;
    private String creditCardExpDate;
    private Map costCenters;
    private OrderFreightDataVector shipMethod;
    private OrderCreditCardData orderCreditCard;
    private OrderFreightData mFreightInfo;
    private TransRebateApprovedViewVector mTransRebateApprovedDV; // used by 850 outbound EDI transaction
    private BigDecimal mDiscount = null;
    
    private BigDecimal mFuelSurcharge = null; // new by SVC 1/14/2010
    private BigDecimal mFreight = null; // new by SVC 1/14/2010
    private BigDecimal mHandling = null;          // new by SVC 1/14/2010
    private BigDecimal mSmallOrderFee = null;          //  02/25/2010
    private Map genericMap = null; // added to hold extra properties for outbound transaction

    /**
     * @return the account id if there is one available.  Otherwise returns 0
     */
    public int getAccountIdIfPresent(){
    	if(mOrderD != null){
    		return mOrderD.getAccountId();
    	}
    	return 0;
    }


    /**
     * This method differs from the @see getDistributor method as it is more robust in its searching.
     * @return the distributor id if there is one available.  Otherwise returns 0
     */
    public int getDistributorIdIfPresent(){
    	if(mInvoiceDistD != null){
    		return mInvoiceDistD.getBusEntityId();
    	}
    	if(mInvoiceAbstractionData != null && mInvoiceAbstractionData.getInvoiceDistData() != null){
    		return mInvoiceAbstractionData.getInvoiceDistData().getBusEntityId();
    	}
    	return getDistributorId();
    }

    /**
     * @return the store id if there is one avaliable.  Otherwise returns 0
     */
    public int getStoreIdIfPresent(){
    	if(mInvoiceDistD != null){
    		return mInvoiceDistD.getStoreId();
    	}
    	if(mInvoiceAbstractionData != null && mInvoiceAbstractionData.getInvoiceDistData() != null){
    		return mInvoiceAbstractionData.getInvoiceDistData().getStoreId();
    	}
    	if(mOrderD != null){
    		return mOrderD.getStoreId();
    	}
    	return 0;
    }


    /**
     * Creates a new OutboundEDIRequestData
     *
     * @return
     *  Newly initialized OutboundEDIRequestData object.
     */
    public static OutboundEDIRequestData createValue ()
    {
        OutboundEDIRequestData valueData = new OutboundEDIRequestData();

        return valueData;
    }
    /**
     * Get the value of OrderD.
     * @return value of OrderD.
     */
    public OrderData getOrderD() {
	    return mOrderD;
    }

    /**
     * Set the value of OrderD.
     * @param v  Value to assign to OrderD.
     */
    public void setOrderD(OrderData v) {
	    mOrderD = v;
    }

    /**
     * Get the value of PurchaseOrderD.
     * @return value of PurchaseOrderD.
     */
    public PurchaseOrderData getPurchaseOrderD() {
	    return mPurchaseOrderD;
    }

    /**
     * Set the value of PurchaseOrderD.
     * @param v  Value to assign to PurchaseOrderD.
     */
    public void setPurchaseOrderD(PurchaseOrderData v) {
	    mPurchaseOrderD = v;
    }

    /**
     * Get the value of InvoiceCustD.
     * @return value of InvoiceCustD.
     * @deprecated use getInvoiceData instead
     */
    public InvoiceAbstractionView getInvoiceCustD() {
	    return mInvoiceAbstractionData;
    }

    public void setInvoiceData(InvoiceCustData v){
        mInvoiceAbstractionData = new InvoiceAbstractionView(v);
    }
    public void setInvoiceData(InvoiceDistData v){
        mInvoiceAbstractionData = new InvoiceAbstractionView(v);
    }

    /**
     * Get the value of InvoiceCustD.
     * @return value of InvoiceCustD.
     */
    public InvoiceAbstractionView getInvoiceData() {
	    return mInvoiceAbstractionData;
    }


    /**
     * Get the value of OrderItemDV.
     * @return value of OrderItemDV.
     */
    public OrderItemDataVector getOrderItemDV() {
	    return mOrderItemDV;
    }

    /**
     * Set the value of OrderItemDV.
     * @param v  Value to assign to OrderItemDV.
     */
    public void setOrderItemDV(OrderItemDataVector  v) {
	    mOrderItemDV = v;
    }

    /**
     * Get the value of OrderItemActionD.
     * @return value of OrderItemActionD.
     */
    public OrderItemActionData getOrderItemActionD() {
	    return mOrderItemActionD;
    }

    /**
     * Set the value of OrderItemActionD.
     * @param v  Value to assign to OrderItemActionD.
     */
    public void setOrderItemActionD(OrderItemActionData v) {
	    mOrderItemActionD = v;
    }
    /**
     * Get the value of OrderItemActionDV.
     * @return value of OrderItemActionDV.
     */
    public OrderItemActionDataVector getOrderItemActionDV() {
	    return mOrderItemActionDV;
    }

    /**
     * Set the value of OrderItemActionDV.
     * @param v  Value to assign to OrderItemActionDV.
     */
    public void setOrderItemActionDV(OrderItemActionDataVector  v) {
	    mOrderItemActionDV = v;
    }

    /**
     * Holds objects of type InvoiceCustDetailRequestData
     */
    public List getInvoiceDetailDV() {
	    return mInvoiceDetailReqDV;
    }

    /**
     * Set the value of InvoiceDetailDV.
     * @param v  Value to assign to InvoiceDetailDV.
     */
    public void setInvoiceDetailDV(List  v) {
	    mInvoiceDetailReqDV = v;
    }

    /**
     * Get the value of OriginalDateOrdered.
     * @return value of OriginalDateOrdered.
     */
    public String getOriginalDateOrdered() {
	    return mOriginalDateOrdered;
    }

    /**
     * Set the value of OriginalDateOrdered.
     * @param v  Value to assign to OriginalDateOrdered.
     */
    public void setOriginalDateOrdered(String  v) {
	    mOriginalDateOrdered = v;
    }

    /**
     * Get the value of ShipAddr.
     * @return value of ShipAddr.
     */
    public OrderAddressData getShipAddr() {
	    return mShipAddr;
    }

    /**
     * Set the value of ShipAddr.
     * @param v  Value to assign to ShipAddr.
     */
    public void setShipAddr(OrderAddressData v) {
	    mShipAddr = v;
    }

    public FreightHandlerView getShipVia() {
	    return mShipVia;
    }

    private    String EdiRoutingCd = "";

    /** Holds value of property accountIdentifier. */
    private String accountIdentifier;

    /** Holds value of property siteIdentifier. */
    private String siteIdentifier;

    /** Holds value of property distributorCompanyCode. */
    private String distributorCompanyCode;

    /** Holds value of property siteProperties. */
    private PropertyDataVector siteProperties;

    /** Holds value of property invoiceCustDetailViewDV. */
    private InvoiceCustDetailViewVector invoiceCustDetailViewDV;

    /** Holds value of property accountProperties. */
    private PropertyDataVector accountProperties;

    /**
     * Holds value of property distributorName.
     */
    private String distributorName;

    /**
     * Holds value of property customerBillingUnit.
     */
    private String customerBillingUnit;

    /**
     * Holds value of property distributorId.
     */
    private int distributorId;

    /**
     * Holds value of property checkOutForm.
     */
    private Object checkOutForm;

    /**
     * Holds value of property faxBackConfirmNumber.
     */
    private PhoneData faxBackConfirmNumber;

    public void setEdiRoutingCd(String EdiRoutingCd) {
        this.EdiRoutingCd = EdiRoutingCd;
    }
    public String getEdiRoutingCd() {
        return EdiRoutingCd;
    }

    public void setShipVia(FreightHandlerView v) {
	    mShipVia = v;
    }

    /**
     * Get the value of BillAddr.
     * @return value of BillAddr.
     */
    public OrderAddressData getBillAddr() {
	    return mBillAddr;
    }

    /**
     * Set the value of BillAddr.
     * @param v  Value to assign to BillAddr.
     */
    public void setBillAddr(OrderAddressData v) {
	    mBillAddr = v;
    }

    /**
     * Get the value of CustShipAddr.
     * @return value of CustShipAddr.
     */
    public OrderAddressData getCustShipAddr() {
	    return mCustShipAddr;
    }

    /**
     * Set the value of CustShipAddr.
     * @param v  Value to assign to CustShipAddr.
     */
    public void setCustShipAddr(OrderAddressData v) {
	    mCustShipAddr = v;
    }


    /**
     * Get the value of CustBillAddr.
     * @return value of CustBillAddr.
     */
    public OrderAddressData getCustBillAddr() {
	    return mCustBillAddr;
    }

    /**
     * Set the value of CustBillAddr.
     * @param v  Value to assign to CustBillAddr.
     */
    public void setCustBillAddr(OrderAddressData v) {
	    mCustBillAddr = v;
    }

    /**
     * Get the value of InvoiceDistD.
     * @return value of InvoiceDistD.
     */
    public InvoiceDistData getInvoiceDistD() {
	    return mInvoiceDistD;
    }

    /**
     * Set the value of InvoiceDistD.
     * @param v  Value to assign to InvoiceDistD.
     */
    public void setInvoiceDistD(InvoiceDistData v) {
	    mInvoiceDistD = v;
    }

    /**
     * Get the value of FreightChargeType.
     * @return value of FreightChargeType.
     */
    public String getFreightChargeType() {
	    return mFreightChargeType;
    }

    /**
     * Set the value of FreightChargeType.
     * @param v  Value to assign to FreightChargeType.
     */
    public void setFreightChargeType(String  v) {
	    this.mFreightChargeType = v;
    }

    /**
     * Get the value of OrderPropertyDV.
     * @return value of OrderPropertyDV.
     */
    public OrderPropertyDataVector getOrderPropertyDV() {
	    return mOrderPropertyDV;
    }

    /**
     * Set the value of OrderPropertyDV.
     * @param v  Value to assign to OrderPropertyDV.
     */
    public void setOrderPropertyDV(OrderPropertyDataVector  v) {
	    this.mOrderPropertyDV = v;
    }

    public OrderMetaDataVector getOrderMetaDV() {
	    return mOrderMetaDV;
    }
    public String getOrderRequestedShipDate() {
        return getOrderMetaDataValue
            (RefCodeNames.PROPERTY_TYPE_CD.DELIVERY_SCHEDULE);
    }

    public String getOrderMetaDataValue(String pMetaName) {

	    if (mOrderMetaDV == null ||
            mOrderMetaDV.size() == 0 ) {
            return "";
        }

	    for ( int i = 0; i < mOrderMetaDV.size(); i++ ) {
            OrderMetaData omd = (OrderMetaData)mOrderMetaDV.get(i);
            if ( omd.getName() == null ) continue;
            if ( pMetaName.equals(omd.getName())) {
                return omd.getValue();
            }
        }

        return "";
    }

    public void setOrderMetaDV(OrderMetaDataVector  v) {
	    this.mOrderMetaDV = v;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this OrderData object
     */
    public String toString()
    {
    return "[" + "Order=" + mOrderD + ", Order Item=" + mOrderItemDV
            + ", Invoice Cust=" + mInvoiceAbstractionData
            + ", Invoice Dist=" + mInvoiceDistD
            + ", Freight Charge Type=" + mFreightChargeType
            + ", Invoice Details=" + mInvoiceDetailReqDV
            + ", Orignal Date Ordered=" + mOriginalDateOrdered
            + ", Address Id=" + mAddressId
            + ", Lawson Shipping=" + mShipAddr
            + ", Customer Shipping=" + mCustShipAddr
            + ", Order Properties=" + mOrderPropertyDV
            + ", Order Meta=" + mOrderMetaDV
            + "]";
    }

    /** Getter for property manifestItemView.
     * @return Value of property manifestItemView.
     *
     */
    public ManifestItemView getManifestItemView() {
        return this.manifestItemView;
    }

    /** Setter for property manifestItemView.
     * @param manifestItemView New value of property manifestItemView.
     *
     */
    public void setManifestItemView(ManifestItemView manifestItemView) {
        this.manifestItemView = manifestItemView;
    }

    /** Getter for property incomingTradingProfileId.
     * @return Value of property incomingTradingProfileId.
     *
     */
    public int getIncomingTradingProfileId() {
        if(this.incomingTradingProfileId == 0 && getOrderD()!= null){
            return getOrderD().getIncomingTradingProfileId();
        }else{
            return this.incomingTradingProfileId;
        }
    }

    /** Setter for property incomingTradingProfileId.
     * @param incomingTradingProfileId New value of property incomingTradingProfileId.
     *
     */
    public void setIncomingTradingProfileId(int incomingTradingProfileId) {
        this.incomingTradingProfileId = incomingTradingProfileId;
    }

    /** Getter for property accountIdentifier.
     * @return Value of property accountIdentifier.
     *
     */
    public String getAccountIdentifier() {
        return this.accountIdentifier;
    }

    /** Setter for property accountIdentifier.
     * @param accountIdentifier New value of property accountIdentifier.
     *
     */
    public void setAccountIdentifier(String accountIdentifier) {
        this.accountIdentifier = accountIdentifier;
    }

    /** Getter for property siteIdentifier.
     * @return Value of property siteIdentifier.
     *
     */
    public String getSiteIdentifier() {
        return this.siteIdentifier;
    }

    /** Setter for property siteIdentifier.
     * @param siteIdentifier New value of property siteIdentifier.
     *
     */
    public void setSiteIdentifier(String siteIdentifier) {
        this.siteIdentifier = siteIdentifier;
    }

    /** Getter for property distributorCompanyCode.
     * @return Value of property distributorCompanyCode.
     *
     */
    public String getDistributorCompanyCode() {
        return this.distributorCompanyCode;
    }

    /** Setter for property distributorCompanyCode.
     * @param distributorCompanyCode New value of property distributorCompanyCode.
     *
     */
    public void setDistributorCompanyCode(String distributorCompanyCode) {
        this.distributorCompanyCode = distributorCompanyCode;
    }

    /** Getter for property siteProperties.
     * @return Value of property siteProperties.
     *
     */
    public PropertyDataVector getSiteProperties() {
        return this.siteProperties;
    }

    /** Setter for property siteProperties.
     * @param siteProperties New value of property siteProperties.
     *
     */
    public void setSiteProperties(PropertyDataVector siteProperties) {
        this.siteProperties = siteProperties;
    }

    /** Getter for property invoiceCustDetailViewDV.
     * @return Value of property invoiceCustDetailViewDV.
     *
     */
    public InvoiceCustDetailViewVector getInvoiceCustDetailViewDV() {
        return this.invoiceCustDetailViewDV;
    }

    /** Setter for property invoiceCustDetailViewDV.
     * @param invoiceCustDetailViewDV New value of property invoiceCustDetailViewDV.
     *
     */
    public void setInvoiceCustDetailViewDV(InvoiceCustDetailViewVector invoiceCustDetailViewDV) {
        this.invoiceCustDetailViewDV = invoiceCustDetailViewDV;
    }

    /** Getter for property accountProperties.
     * @return Value of property accountProperties.
     *
     */
    public PropertyDataVector getAccountProperties() {
        return this.accountProperties;
    }

    /** Setter for property accountProperties.
     * @param accountProperties New value of property accountProperties.
     *
     */
    public void setAccountProperties(PropertyDataVector accountProperties) {
        this.accountProperties = accountProperties;
    }

    /**
     * Getter for property distributorName.
     * @return Value of property distributorName.
     */
    public String getDistributorName() {
        return this.distributorName;
    }

    /**
     * Setter for property distributorName.
     * @param distributorName New value of property distributorName.
     */
    public void setDistributorName(String distributorName) {
        this.distributorName = distributorName;
    }

    /**
     * Getter for property customerBillingUnit.
     * @return Value of property customerBillingUnit.
     */
    public String getCustomerBillingUnit() {
        return this.customerBillingUnit;
    }

    /**
     * Setter for property customerBillingUnit.
     * @param customerBillingUnit New value of property customerBillingUnit.
     */
    public void setCustomerBillingUnit(String customerBillingUnit) {
        this.customerBillingUnit = customerBillingUnit;
    }

    /**
     * Getter for property distId.
     * @return Value of property distId.
     */
    public int getDistributorId() {
        return this.distributorId;
    }

    /**
     * Setter for property distId.
     * @param distId New value of property distId.
     */
    public void setDistributorId(int distributorId) {
        this.distributorId = distributorId;
    }

    /**
     *Sets the accountName property
     */
    public void setAccountName(String pAccountName){
        accountName = pAccountName;
    }
    /**
     *Gets the accountName property
     */
    public String getAccountName(){
        return accountName;
    }

    public void setPoAccountName(String pPoAccountName){
        poAccountName = pPoAccountName;
    }
    public String getPoAccountName(){
        return poAccountName;
    }


    /**
     * This is here for the cXML accounts.  At this point the actual implementation
     * may change to be more server driven.  This is here as a temporary work around untill
     * the real method of implementation is decided upon (I hope)
     * @return Value of property checkOutForm.
     */
    //public com.cleanwise.view.forms.CheckoutForm getCheckOutForm() {
    public Object getCheckOutForm() {
        return this.checkOutForm;
    }

    /**
     * This is here for the cXML accounts.  At this point the actual implementation
     * may change to be more server driven.  This is here as a temporary work around untill
     * the real method of implementation is decided upon (I hope)
     * @param checkOutForm New value of property checkOutForm.
     */
    //public void setCheckOutForm(com.cleanwise.view.forms.CheckoutForm checkOutForm) {
    public void setCheckOutForm(Object checkOutForm) {
        this.checkOutForm = checkOutForm;
    }


    /**
     *Returns the store type of the store that belongs to this request if  it is
     *supported in the EJB layer for this transaction type.
     */
    public String getStoreType(){
        return storeType;
    }

    /**
     *Setter for store type cd property
     */
    public void setStoreTypeCd(String pVal){
        storeType = pVal;
    }

    /**
     * Getter for property faxBackConfirmNumber.
     * @return Value of property faxBackConfirmNumber.
     */
    public PhoneData getFaxBackConfirmNumber() {

        return this.faxBackConfirmNumber;
    }

    /**
     * Setter for property faxBackConfirmNumber.
     * @param faxBackConfirmNumber New value of property faxBackConfirmNumber.
     */
    public void setFaxBackConfirmNumber(PhoneData faxBackConfirmNumber) {

        this.faxBackConfirmNumber = faxBackConfirmNumber;
    }


    private String mDistributorCustomerReferenceCode;

    public String getDistributorCustomerReferenceCode(){
    	return mDistributorCustomerReferenceCode;
    }
    public void setDistributorCustomerReferenceCode(String pVal){
    	mDistributorCustomerReferenceCode = pVal;
    }

    /**
     *Returns the encripted credit card number
     */
    public String getCreditCardNumber(){
        return creditCardNumber;
    }

    /**
     *Setter for encripted credit card number property
     */
    public void setCreditCardNumber(String pVal){
        creditCardNumber = pVal;
    }

    public String getCreditCardExpDate(){
        return creditCardExpDate;
    }

    public void setCreditCardExpDate(String pVal){
        creditCardExpDate = pVal;
    }


    public Map getCostCenters() {
        return costCenters;
    }


    public void setCostCenters(Map costCenters) {
        this.costCenters = costCenters;
    }


    public OrderFreightDataVector getShipMethod() {
        return shipMethod;
    }


    public void setShipMethod(OrderFreightDataVector shipMethod) {
        this.shipMethod = shipMethod;
    }

    public OrderCreditCardData getOrderCreditCard() {
        return orderCreditCard;
    }

    public void setOrderCreditCard(OrderCreditCardData orderCreditCard) {
        this.orderCreditCard = orderCreditCard;
    }

    public OrderFreightData getFreightInfo() {
        return mFreightInfo;
    }

    public void setFreightInfo(OrderFreightData freightInfo) {
        this.mFreightInfo = freightInfo;
    }
    
    /**
     * Get the value of TransRebateApprovedDV.
     * @return value of TransRebateApprovedDV.
     */
    public TransRebateApprovedViewVector getTransRebateApprovedDV() {
	    return mTransRebateApprovedDV;
    }

    /**
     * Set the value of TransRebateApprovedDV.
     * @param v  Value to assign to TransRebateApprovedDV.
     */
    public void setTransRebateApprovedDV(TransRebateApprovedViewVector  v) {
	    mTransRebateApprovedDV = v;
    }

    public BigDecimal getDiscount() {
        return mDiscount;
    }

    public void setDiscount(BigDecimal discount) {
        mDiscount = discount;
    }

    /*** new by SVC 1/14/2010: Start ***/
    
    public BigDecimal getFuelSurcharge() {
        return mFuelSurcharge;
    }
    
    public void setFuelSurcharge(BigDecimal fuelSurcharge) {
        mFuelSurcharge = fuelSurcharge;
    }
    
    public BigDecimal getHandling() {
        return mHandling;
    }

    public void setHandling(BigDecimal handling) {
        mHandling = handling;
    }
    
    public BigDecimal getFreight() {
        return mFreight;
    }
    
    public void setFreight(BigDecimal freight) {
        mFreight = freight;
    }
    /*** new by SVC 1/14/2010: Finish ***/

		/**
		 * @return the mSmallOrderFee
		 */
		public BigDecimal getSmallOrderFee() {
			return mSmallOrderFee;
		}


		/**
		 * @param smallOrderFee the mSmallOrderFee to set
		 */
		public void setSmallOrderFee(BigDecimal smallOrderFee) {
			mSmallOrderFee = smallOrderFee;
		}

		public void setGenericMap(Map genericMap) {
			this.genericMap = genericMap;
		}

		public Map getGenericMap() {
			return genericMap;
		}
    
}
