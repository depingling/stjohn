package com.cleanwise.service.api.value;

import java.math.*;

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.util.*;

/**
 *  <code>DistributorData</code> is a value object that aggregates all the value
 *  objects that make up a Distributor.
 *
 *@author     tbesser
 *@created    August 1, 2001
 */
public class DistributorData extends ValueObject {
  //Do not remove or modify next line. It would break object DB saving
  private static final long serialVersionUID = -6108369132283679058L;

    private BusEntityData mBusEntity;
    private AddressData mPrimaryAddress;
    private PhoneData mPrimaryPhone;
    private PhoneData mPrimaryFax;
    private EmailData mPrimaryEmail;
    private PropertyData mDistributorTypeProp;
    private PhoneData mPoFax;
    private PropertyData mPurchaseOrderFreightTerms;
    private PropertyData mPurchaseOrderDueDays;
    private AddressData mBillingAddress;
    private FreightTableDescData freightTable;
    private String mCustomerReferenceCode;
    private BigDecimal allowedFreightSurchargeAmount; //just for reference
    private Boolean mDoNotAllowInvoiceEdits;
    private EmailData mRejectedInvEmail;

    public String getCustomerReferenceCode(){
    	return mCustomerReferenceCode;
    }
    public void setCustomerReferenceCode(String pVal){
    	mCustomerReferenceCode = pVal;
    }





    /**
     *  Holds value of property exceptionOnOverchargedFreight.
     */
    private Boolean exceptionOnOverchargedFreight;

    private String mMaxInvoiceFreightAllowed;
    /**
     *  Holds value of property minimumOrderAmount.
     */
    private BigDecimal minimumOrderAmount;

    /**
     *  Holds value of property invoiceLoadingPriceModel.
     */
    private String invoiceLoadingPriceModel;

    /**
     *  Holds value of property invoiceLoadingPriceExceptionThreshold.
     */
    private BigDecimal invoiceAmountPercentAllowanceUpper;

    /**
     *  Holds value of property allowFreightOnBackorders.
     */
    private Boolean allowFreightOnBackorders;


    private String exceptionOnTaxDifference=RefCodeNames.EXCEPTION_ON_TAX_VALUE.YES;

    /**
     *  Holds value of property purchaseOrderComments.
     */
    private String purchaseOrderComments;

    /**
     *  Holds value of property printCustPhoneOnPurchaseOrder.
     */
    private Boolean printCustPhoneOnPurchaseOrder;

    /**
     *  Holds value of property allowFreightOnFreightHandlerOrders.
     */
    private Boolean allowFreightOnFreightHandlerOrders;

    /**
     *  Holds value of property distributorsCompanyCode.
     */
    private String distributorsCompanyCode;

    /**
     *  Holds value of property manualPOAckReq.
     */
    private Boolean manualPOAckReq;

    /**
     *  Holds value of property callHours.
     */
    private String callHours;

    /**
     *  Holds value of property runtimeDisplayName.
     */
    private String runtimeDisplayName;

    /**
     *  Holds value of property storeId.
     */
    private int storeId;

    /**
    * Various distributor attributes dreamed up by paula and frank m.
    * These are informational data items mostly of use to CRC users.
    */
    private String    smallOrderHandlingFee, accountNumbers, webInfo;


    /**
     *  Creates a new <code>DistributorData</code> instance with minimal
     *  information
     *
     *@param  pBusEntity       Description of the Parameter
     *@param  pPrimaryAddress  Description of the Parameter
     */
    public DistributorData(BusEntityData pBusEntity,
            AddressData pPrimaryAddress) {
        init(pBusEntity, pPrimaryAddress, null, null, null, null
                , null, null, null, 0,null);
    }


    /**
     *  Creates a new <code>DistributorData</code> instance. This should be a
     *  private interface callable only from the Distributor session bean.
     *  (Unfortunately I do not know of a way to do that)
     *
     *@param  pBusEntity           Description of Parameter
     *@param  pPrimaryAddress      Description of Parameter
     *@param  pPrimaryPhone        Description of Parameter
     *@param  pPrimaryFax          Description of Parameter
     *@param  pPrimaryEmail        Description of Parameter
     *@param  pDistTypeProp        Description of Parameter
     *@param  pPoFax               Description of the Parameter
     *@param  pDistTypePropVector  Description of the Parameter
     *@param  pBillingAddress      Description of the Parameter
     *@param  pStoreId             Description of the Parameter
     */
    public DistributorData(BusEntityData pBusEntity,
            AddressData pPrimaryAddress,
            PhoneData pPrimaryPhone,
            PhoneData pPrimaryFax,
            PhoneData pPoFax,
            EmailData pPrimaryEmail,
            PropertyData pDistTypeProp,
            PropertyDataVector pDistTypePropVector,
            AddressData pBillingAddress,
            int pStoreId,
            FreightTableDescData freightTableDesc
            ) {

        init(pBusEntity, pPrimaryAddress, pPrimaryPhone,
                pPrimaryFax, pPoFax, pPrimaryEmail, pDistTypeProp,
                pDistTypePropVector,
                pBillingAddress,
                pStoreId,freightTableDesc);
    }


    /**
     *  Called when the object is initialized
     *
     *@param  pBusEntity           Description of the Parameter
     *@param  pPrimaryAddress      Description of the Parameter
     *@param  pPrimaryPhone        Description of the Parameter
     *@param  pPrimaryFax          Description of the Parameter
     *@param  pPoFax               Description of the Parameter
     *@param  pPrimaryEmail        Description of the Parameter
     *@param  pDistTypeProp        Description of the Parameter
     *@param  pDistTypePropVector  Description of the Parameter
     *@param  pBillingAddress      Description of the Parameter
     *@param  pStoreId             Description of the Parameter
     */
    private void init(BusEntityData pBusEntity,
            AddressData pPrimaryAddress,
            PhoneData pPrimaryPhone,
            PhoneData pPrimaryFax,
            PhoneData pPoFax,
            EmailData pPrimaryEmail,
            PropertyData pDistTypeProp,
            PropertyDataVector pDistTypePropVector,
            AddressData pBillingAddress,
            int pStoreId,
            FreightTableDescData freightTableDesc) {
        this.mBusEntity = (pBusEntity != null)
                 ? pBusEntity : BusEntityData.createValue();
        this.mPrimaryAddress = (pPrimaryAddress != null)
                 ? pPrimaryAddress : AddressData.createValue();
        this.mPrimaryPhone = (pPrimaryPhone != null)
                 ? pPrimaryPhone : PhoneData.createValue();
        this.mPrimaryFax = (pPrimaryFax != null)
                 ? pPrimaryFax : PhoneData.createValue();
        this.mPoFax = (pPoFax != null)
                 ? pPoFax : PhoneData.createValue();
        this.mPrimaryEmail = (pPrimaryEmail != null)
                 ? pPrimaryEmail : EmailData.createValue();
        this.mDistributorTypeProp = (pDistTypeProp != null)
                 ? pDistTypeProp : PropertyData.createValue();

        this.mBillingAddress = (pBillingAddress != null)
                 ? pBillingAddress : AddressData.createValue();

        this.storeId = pStoreId;

        this.freightTable = freightTableDesc;

        initOtherProperties(pDistTypePropVector);
    }


    /**
     *  Description of the Method
     *
     *@param  pV  Description of the Parameter
     */
    private void initOtherProperties
            (PropertyDataVector pV) {
        for (int i = 0; null != pV &&
                i < pV.size(); i++) {
	        initOtherProperty((PropertyData) pV.get(i));
        }
    }


    /**
     *  Description of the Method
     *
     *@param  pProp  Description of the Parameter
     */
    private void initOtherProperty
            (PropertyData pProp) {

        if (null == pProp) {
            return;
        }

        PropertyData lProp = pProp;

        if (lProp.getPropertyTypeCd().equalsIgnoreCase(RefCodeNames.PROPERTY_TYPE_CD.PURCH_ORDER_DUE_DAYS)) {
            mPurchaseOrderDueDays = lProp;
        } else if (lProp.getPropertyTypeCd().equalsIgnoreCase(RefCodeNames.PROPERTY_TYPE_CD.PURCH_ORDER_FREIGHT_TERMS)) {
            mPurchaseOrderFreightTerms = lProp;
        } else if (lProp.getPropertyTypeCd().equals(RefCodeNames.PROPERTY_TYPE_CD.CALL_HOURS)) {
            callHours = lProp.getValue();
        } else if (lProp.getPropertyTypeCd().equals(RefCodeNames.PROPERTY_TYPE_CD.RUNTIME_DISPLAY_NAME)) {
            runtimeDisplayName = lProp.getValue();
        } else if (lProp.getPropertyTypeCd().equalsIgnoreCase(RefCodeNames.PROPERTY_TYPE_CD.MINIMUM_ORDER_AMOUNT)) {
            minimumOrderAmount = Utility.parseBigDecimal(lProp.getValue());
        } else if (lProp.getPropertyTypeCd().equalsIgnoreCase(RefCodeNames.PROPERTY_TYPE_CD.ERROR_ON_OVERCHARGED_FREIGHT)) {
            if ("true".equalsIgnoreCase(lProp.getValue())) {
                exceptionOnOverchargedFreight = Boolean.TRUE;
            } else if ("false".equalsIgnoreCase(lProp.getValue())) {
                exceptionOnOverchargedFreight = Boolean.FALSE;
            }
        } else if (lProp.getPropertyTypeCd().equalsIgnoreCase(RefCodeNames.PROPERTY_TYPE_CD.IGNORE_ORDER_MIN_FOR_FREIGHT)) {
            if ("true".equalsIgnoreCase(lProp.getValue())) {
                ignoreOrderMinimumForFreight = Boolean.TRUE;
            } else if ("false".equalsIgnoreCase(lProp.getValue())) {
                ignoreOrderMinimumForFreight = Boolean.FALSE;
            }
        } else if (lProp.getPropertyTypeCd().equalsIgnoreCase(RefCodeNames.PROPERTY_TYPE_CD.INVOICE_AMT_PERCNT_ALLOW_UPPER)) {
            invoiceAmountPercentAllowanceUpper = Utility.parseBigDecimal(lProp.getValue());
        } else if (lProp.getPropertyTypeCd().equalsIgnoreCase(RefCodeNames.PROPERTY_TYPE_CD.INVOICE_AMT_PERCNT_ALLOW_LOWER)) {
            invoiceAmountPercentAllowanceLower = Utility.parseBigDecimal(lProp.getValue());
        } else if (lProp.getPropertyTypeCd().equalsIgnoreCase(RefCodeNames.PROPERTY_TYPE_CD.ALLOWED_FRT_SURCHARGE_AMOUNT)) {
            allowedFreightSurchargeAmount = Utility.parseBigDecimal(lProp.getValue());
        } else if (lProp.getPropertyTypeCd().equalsIgnoreCase(RefCodeNames.PROPERTY_TYPE_CD.INVOICE_LOADING_PRICE_MODEL_CD)) {
            invoiceLoadingPriceModel = lProp.getValue();
        } else if (lProp.getPropertyTypeCd().equalsIgnoreCase(RefCodeNames.PROPERTY_TYPE_CD.RECEIVING_SYSTEM_INVOICE_CD)){
            receivingSystemInvoiceCd = lProp.getValue();
        } else if (lProp.getPropertyTypeCd().equalsIgnoreCase(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_FREIGHT_ON_BACKORDERS)) {
            if ("true".equalsIgnoreCase(lProp.getValue())) {
                allowFreightOnBackorders = Boolean.TRUE;
            } else if ("false".equalsIgnoreCase(lProp.getValue())) {
                allowFreightOnBackorders = Boolean.FALSE;
            }
        } else if (lProp.getPropertyTypeCd().equals(RefCodeNames.PROPERTY_TYPE_CD.EXCEPTION_ON_TAX_DIFFERENCE)) {
               exceptionOnTaxDifference = lProp.getValue();
        } else if (
                lProp.getPropertyTypeCd().equalsIgnoreCase(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_FREIGHT_ON_FH_ORDERS)) {
            if ("true".equalsIgnoreCase(lProp.getValue())) {
                allowFreightOnFreightHandlerOrders = Boolean.TRUE;
            } else if ("false".equalsIgnoreCase(lProp.getValue())) {
                allowFreightOnFreightHandlerOrders = Boolean.FALSE;
            }
        } else if (lProp.getPropertyTypeCd().equalsIgnoreCase(RefCodeNames.PROPERTY_TYPE_CD.PURCHASE_ORDER_COMMENTS)) {
            purchaseOrderComments = lProp.getValue();
        } else if (lProp.getPropertyTypeCd().equalsIgnoreCase(RefCodeNames.PROPERTY_TYPE_CD.DISTRIBUTORS_COMPANY_CODE)) {
            distributorsCompanyCode = lProp.getValue();
        } else if (lProp.getPropertyTypeCd().equalsIgnoreCase(RefCodeNames.PROPERTY_TYPE_CD.PRINT_CUST_CONTACT_ON_PO)) {
            if ("true".equalsIgnoreCase(lProp.getValue())) {
                printCustPhoneOnPurchaseOrder = Boolean.TRUE;
            } else if ("false".equalsIgnoreCase(lProp.getValue())) {
                printCustPhoneOnPurchaseOrder = Boolean.FALSE;
            }
        } else if (lProp.getPropertyTypeCd().equals(RefCodeNames.PROPERTY_TYPE_CD.MAN_PO_ACK_REQUIERED)) {
            if ("true".equalsIgnoreCase(lProp.getValue())) {
                manualPOAckReq = Boolean.TRUE;
            } else if ("false".equalsIgnoreCase(lProp.getValue())) {
                manualPOAckReq = Boolean.FALSE;
            }
        } else if (lProp.getPropertyTypeCd().equals
                (RefCodeNames.PROPERTY_TYPE_CD.DIST_SMALL_ORDER_FEE)) {
            this.smallOrderHandlingFee = lProp.getValue();
        } else if (lProp.getPropertyTypeCd().equals
                (RefCodeNames.PROPERTY_TYPE_CD.DIST_ACCOUNT_NUMBERS)) {
            this.accountNumbers = lProp.getValue();
        } else if (lProp.getPropertyTypeCd().equals
                (RefCodeNames.PROPERTY_TYPE_CD.DIST_WEB_INFO)) {
            this.webInfo = lProp.getValue();
        } else if (lProp.getPropertyTypeCd().equals
                (RefCodeNames.PROPERTY_TYPE_CD.DIST_MAX_INVOICE_FREIGHT)) {
            this.mMaxInvoiceFreightAllowed = lProp.getValue();
        } else if (lProp.getPropertyTypeCd().equals
                (RefCodeNames.PROPERTY_TYPE_CD.CANCEL_BACKORDERED_LINES)) {
            setCancelBackorderedLines(new Boolean(Utility.isTrue(lProp.getValue())));
        } else if (lProp.getPropertyTypeCd().equals
                (RefCodeNames.PROPERTY_TYPE_CD.CUSTOMER_REFERENCE_CODE)) {
            setCustomerReferenceCode(lProp.getValue());
        } else if (lProp.getPropertyTypeCd().equals(RefCodeNames.PROPERTY_TYPE_CD.HOLD_INVOICE)) {
            try{
                if(Utility.isSet(lProp.getValue())){
                    setHoldInvoiceDays(Integer.parseInt(lProp.getValue()));
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        } else if (lProp.getPropertyTypeCd().equals(RefCodeNames.DIST_EXCHANGE_PROPERTY.EXCHANGE_COMPANY_CODE)) {
        	setExchangeCompanyCode(lProp.getValue());
        } else if (lProp.getPropertyTypeCd().equals(RefCodeNames.DIST_EXCHANGE_PROPERTY.EXCHANGE_INVENTORY_URL)) {
        	setExchangeInventoryURL(lProp.getValue());
        } else if (lProp.getPropertyTypeCd().equals(RefCodeNames.DIST_EXCHANGE_PROPERTY.EXCHANGE_USER)) {
        	setExchangeUser(lProp.getValue());
        } else if (lProp.getPropertyTypeCd().equals(RefCodeNames.DIST_EXCHANGE_PROPERTY.EXCHANGE_PASSWORD)) {
        	setExchangePassword(lProp.getValue());
        } else if (lProp.getPropertyTypeCd().equalsIgnoreCase(RefCodeNames.PROPERTY_TYPE_CD.DO_NOT_ALLOW_INVOICE_EDITS)) {
            if(Utility.isTrue(lProp.getValue())){
                setDoNotAllowInvoiceEdits(Boolean.TRUE);
            } else{
                setDoNotAllowInvoiceEdits(Boolean.FALSE);
            }
        }
    }

    private Boolean cancelBackorderedLines;
    public Boolean getCancelBackorderedLines(){
        return cancelBackorderedLines;
    }
    public void setCancelBackorderedLines(Boolean val){
        cancelBackorderedLines = val;
    }


    /**
     *  Sets the DistributorType attribute of the DistributorData object Known
     *  values are National and Regional.
     *
     *@param  pDistributorType  The new DistributorType value
     */
    public void setDistributorTypeProp(PropertyData pDistributorType) {
        this.mDistributorTypeProp = pDistributorType;
    }


    /**
     *  Sets the BillingAddress attribute of the DistributorData object
     *
     *@param  pBillingAddress  The new BillingAddress value
     */
    public void setBillingAddress(AddressData pBillingAddress) {
        this.mBillingAddress = pBillingAddress;
    }


    /**
     *  Gets the DistributorType attribute of the DistributorData object
     *
     *@return    The DistributorType value
     */
    public PropertyData getDistributorTypeProp() {
        return mDistributorTypeProp;
    }


    /**
     *  Describe <code>getBusEntity</code> method here.
     *
     *@return    a <code>BusEntityData</code> value
     */
    public BusEntityData getBusEntity() {
        return mBusEntity;
    }


    /**
     *  Describe <code>getPrimaryAddress</code> method here.
     *
     *@return    an <code>AddressData</code> value
     */
    public AddressData getPrimaryAddress() {
        return mPrimaryAddress;
    }


    /**
     *  Describe <code>getPrimaryFax</code> method here.
     *
     *@return    a <code>PhoneData</code> value
     */
    public PhoneData getPrimaryFax() {
        return mPrimaryFax;
    }


    /**
     *  Describe <code>getPrimaryPhone</code> method here.
     *
     *@return    a <code>PhoneData</code> value
     */
    public PhoneData getPrimaryPhone() {
        return mPrimaryPhone;
    }


    /**
     *  Describe <code>getPrimaryEmail</code> method here.
     *
     *@return    an <code>EmailData</code> value
     */
    public EmailData getPrimaryEmail() {
        return mPrimaryEmail;
    }


    /**
     *  Getter for property poFax.
     *
     *@return    Value of property poFax.
     */
    public PhoneData getPoFax() {
        return mPoFax;
    }


    public String getExceptionOnTaxDifference() {
		return exceptionOnTaxDifference;
	}

    public void setExceptionOnTaxDifference(String pVal) {
		exceptionOnTaxDifference=pVal;
	}

	/**
     *  Getter for property purchaseOrderFreightTerms.
     *
     *@return    Value of property purchaseOrderFreightTerms.
     */
    public PropertyData getPurchaseOrderFreightTerms() {
        return mPurchaseOrderFreightTerms;
    }


    /**
     *  Getter for property purchaseOrderDueDays.
     *
     *@return    Value of property purchaseOrderDueDays.
     */
    public PropertyData getPurchaseOrderDueDays() {
        return mPurchaseOrderDueDays;
    }


    /**
     *  Describe <code>getBillingAddress</code> method here.
     *
     *@return    an <code>AddressData</code> value
     */
    public AddressData getBillingAddress() {
        return mBillingAddress;
    }


    /**
     *  Returns a String representation of the value object
     *
     *@return    The String representation of this SiteData object
     */
    public String toString() {
        return "[" + "BusEntity=" + mBusEntity + ", PrimaryAddress=" + mPrimaryAddress + ", PrimaryPhone=" + mPrimaryPhone + ", PrimaryFax=" + mPrimaryFax + ", PrimaryEmail=" + mPrimaryEmail + ", DistributorTypeProperty=" + mDistributorTypeProp + ", BillingAddress=" + mBillingAddress + "]";
    }


    /**
     *  Describe <code>createValue</code> method here.
     *
     *@return    a <code>DistributorData</code> value
     */
    public static DistributorData createValue() {
        return new DistributorData(null, null);
    }


    private Boolean ignoreOrderMinimumForFreight;
    public Boolean getIgnoreOrderMinimumForFreight() {
	if (null == ignoreOrderMinimumForFreight ) {
	    ignoreOrderMinimumForFreight = new Boolean(false);
	}
        return this.ignoreOrderMinimumForFreight;
    }

    public void setIgnoreOrderMinimumForFreight(Boolean v) {
        this.ignoreOrderMinimumForFreight = v;
    }

    /**
     *  Getter for property exceptionOnOverchargedFreight.
     *
     *@return    Value of property exceptionOnOverchargedFreight.
     */
    public Boolean getExceptionOnOverchargedFreight() {
        return this.exceptionOnOverchargedFreight;
    }

    public String getMaxInvoiceFreightAllowed() {
	return mMaxInvoiceFreightAllowed;
    }
    public void setMaxInvoiceFreightAllowed(String v) {
	mMaxInvoiceFreightAllowed = v;
    }

    /**
     *  Setter for property exceptionOnOverchargedFreight.
     *
     *@param  exceptionOnOverchargedFreight  New value of property
     *      exceptionOnOverchargedFreight.
     */
    public void setExceptionOnOverchargedFreight(Boolean exceptionOnOverchargedFreight) {
        this.exceptionOnOverchargedFreight = exceptionOnOverchargedFreight;
    }


    /**
     *  Getter for property minimumOrderAmount.
     *
     *@return    Value of property minimumOrderAmount.
     */
    public BigDecimal getMinimumOrderAmount() {
        return this.minimumOrderAmount;
    }


    /**
     *  Setter for property minimumOrderAmount.
     *
     *@param  minimumOrderAmount  New value of property minimumOrderAmount.
     */
    public void setMinimumOrderAmount(BigDecimal minimumOrderAmount) {
        this.minimumOrderAmount = minimumOrderAmount;
    }


    /**
     *  Getter for property invoiceLoadingModel.
     *
     *@return    Value of property invoiceLoadingModel.
     */
    public String getInvoiceLoadingPriceModel() {
        return this.invoiceLoadingPriceModel;
    }


    /**
     *  Setter for property invoiceLoadingModel.
     *
     *@param  invoiceLoadingPriceModel  The new invoiceLoadingPriceModel value
     */
    public void setInvoiceLoadingPriceModel(String invoiceLoadingPriceModel) {
        this.invoiceLoadingPriceModel = invoiceLoadingPriceModel;
    }


    /**
     *  Getter for property invoiceLoadingPriceExceptionThreshold.
     *
     *@return    Value of property invoiceLoadingPriceExceptionThreshold.
     */
    public BigDecimal getInvoiceAmountPercentAllowanceUpper() {
        return this.invoiceAmountPercentAllowanceUpper;
    }


    /**
     *  Setter for property invoiceLoadingPriceExceptionThreshold.
     *
     *@param  invoiceLoadingPriceExceptionThreshold  New value of property
     *      invoiceLoadingPriceExceptionThreshold.
     */
    public void setInvoiceAmountPercentAllowanceUpper(BigDecimal invoiceLoadingPriceExceptionThreshold) {
        this.invoiceAmountPercentAllowanceUpper = invoiceLoadingPriceExceptionThreshold;
    }


    BigDecimal invoiceAmountPercentAllowanceLower;


    public BigDecimal getInvoiceAmountPercentAllowanceLower() {
		return invoiceAmountPercentAllowanceLower;
	}
	public void setInvoiceAmountPercentAllowanceLower(
			BigDecimal invoiceAmountPercentAllowanceLower) {
		this.invoiceAmountPercentAllowanceLower = invoiceAmountPercentAllowanceLower;
	}
	public BigDecimal getAllowedFreightSurchargeAmount() {
		return allowedFreightSurchargeAmount;
	}
	public void setAllowedFreightSurchargeAmount(BigDecimal freightSurchargeAmount) {
		allowedFreightSurchargeAmount = freightSurchargeAmount;
	}
	/**
     *  Getter for property allowFreightOnBackorders.
     *
     *@return    Value of property allowFreightOnBackorders.
     */
    public Boolean getAllowFreightOnBackorders() {
        return this.allowFreightOnBackorders;
    }


    /**
     *  Setter for property allowFreightOnBackorders.
     *
     *@param  allowFreightOnBackorders  New value of property
     *      allowFreightOnBackorders.
     */
    public void setAllowFreightOnBackorders(Boolean allowFreightOnBackorders) {
        this.allowFreightOnBackorders = allowFreightOnBackorders;
    }


    /**
     *  Getter for property purchaseOrderComments.
     *
     *@return    Value of property purchaseOrderComments.
     */
    public String getPurchaseOrderComments() {
        return this.purchaseOrderComments;
    }


    /**
     *  Setter for property purchaseOrderComments.
     *
     *@param  purchaseOrderComments  New value of property
     *      purchaseOrderComments.
     */
    public void setPurchaseOrderComments(String purchaseOrderComments) {
        this.purchaseOrderComments = purchaseOrderComments;
    }


    /**
     *  Getter for property printCustPhoneOnPurchaseOrder.
     *
     *@return    Value of property printCustPhoneOnPurchaseOrder.
     */
    public Boolean getPrintCustContactOnPurchaseOrder() {
        return this.printCustPhoneOnPurchaseOrder;
    }


    /**
     *  Setter for property printCustPhoneOnPurchaseOrder.
     *
     *@param  printCustPhoneOnPurchaseOrder  New value of property
     *      printCustPhoneOnPurchaseOrder.
     */
    public void setPrintCustContactOnPurchaseOrder(Boolean printCustPhoneOnPurchaseOrder) {
        this.printCustPhoneOnPurchaseOrder = printCustPhoneOnPurchaseOrder;
    }


    /**
     *  Getter for property allowFreightOnFreightHandlerOrders.
     *
     *@return    Value of property allowFreightOnFreightHandlerOrders.
     */
    public Boolean getAllowFreightOnFreightHandlerOrders() {
        return this.allowFreightOnFreightHandlerOrders;
    }


    /**
     *  Setter for property allowFreightOnFreightHandlerOrders.
     *
     *@param  allowFreightOnFreightHandlerOrders  New value of property
     *      allowFreightOnFreightHandlerOrders.
     */
    public void setAllowFreightOnFreightHandlerOrders(Boolean allowFreightOnFreightHandlerOrders) {
        this.allowFreightOnFreightHandlerOrders = allowFreightOnFreightHandlerOrders;
    }


    /**
     *  Getter for property distributorsCompanyCode.
     *
     *@return    Value of property distributorsCompanyCode.
     */
    public String getDistributorsCompanyCode() {
        return this.distributorsCompanyCode;
    }


    /**
     *  Setter for property distributorsCompanyCode.
     *
     *@param  distributorsCompanyCode  New value of property
     *      distributorsCompanyCode.
     */
    public void setDistributorsCompanyCode(String distributorsCompanyCode) {
        this.distributorsCompanyCode = distributorsCompanyCode;
    }


    /**
     *  Getter for property manualPOAckReq.
     *
     *@return    Value of property manualPOAckReq.
     */
    public Boolean getManualPOAcknowldgementRequiered() {
        return this.manualPOAckReq;
    }


    /**
     *  Setter for property manualPOAckReq.
     *
     *@param  v               The new manualPOAcknowldgementRequiered value
     */
    public void setManualPOAcknowldgementRequiered(Boolean v) {
        this.manualPOAckReq = v;
    }


    /**
     *  Getter for property callHours.
     *
     *@return    Value of property callHours.
     */
    public String getCallHours() {

        return this.callHours;
    }


    /**
     *  Setter for property callHours.
     *
     *@param  callHours  New value of property callHours.
     */
    public void setCallHours(String callHours) {

        this.callHours = callHours;
    }


    /**
     *  Getter for property runtimeDisplayName.
     *
     *@return    Value of property runtimeDisplayName.
     */
    public String getRuntimeDisplayName() {

        return this.runtimeDisplayName;
    }


    /**
     *  Setter for property runtimeDisplayName.
     *
     *@param  runtimeDisplayName  New value of property runtimeDisplayName.
     */
    public void setRuntimeDisplayName(String runtimeDisplayName) {

        this.runtimeDisplayName = runtimeDisplayName;
    }


    /**
     *  Getter for property storeId.
     *
     *@return    Value of property storeId.
     */
    public int getStoreId() {

        return this.storeId;
    }


    /**
     *  Setter for property storeId.
     *
     *@param  storeId  New value of property storeId.
     */
    public void setStoreId(int storeId) {

        this.storeId = storeId;
    }


    /**
     *  Gets the smallOrderHandlingFee attribute of the DistributorData object
     *
     *@return    The smallOrderHandlingFee value
     */
    public String getSmallOrderHandlingFee() {
        return this.smallOrderHandlingFee;
    }


    /**
     *  Sets the smallOrderHandlingFee attribute of the DistributorData object
     *
     *@param  v  The new smallOrderHandlingFee value
     */
    public void setSmallOrderHandlingFee(String v) {
        this.smallOrderHandlingFee = v;
    }


    /**
     *  Gets the webInfo attribute of the DistributorData object
     *
     *@return    The webInfo value
     */
    public String getWebInfo() {
        return this.webInfo;
    }


    /**
     *  Sets the webInfo attribute of the DistributorData object
     *
     *@param  v  The new webInfo value
     */
    public void setWebInfo(String v) {
        this.webInfo = v;
    }


    /**
     *  Gets the accountNumbers attribute of the DistributorData object
     *
     *@return    The accountNumbers value
     */
    public String getAccountNumbers() {
        return this.accountNumbers;
    }


    /**
     *  Sets the accountNumbers attribute of the DistributorData object
     *
     *@param  v  The new accountNumbers value
     */
    public void setAccountNumbers(String v) {
        this.accountNumbers = v;
    }



    /**
     *  Gets the propertyCollection attribute of the DistributorData object
     *
     *@return    The propertyCollection value
     */
    public PropertyDataVector getPropertyCollection() {
        int distId = getBusEntity().getBusEntityId();
        PropertyDataVector distProps = new PropertyDataVector();
        if (getInvoiceAmountPercentAllowanceUpper() != null) {
            String propNm = RefCodeNames.PROPERTY_TYPE_CD.INVOICE_AMT_PERCNT_ALLOW_UPPER;
            distProps.add
                    (PropertyUtil.toPropertyData
                    (0, distId, propNm, propNm,
                    getInvoiceAmountPercentAllowanceUpper().toString()
                    ));
        }
        if (getInvoiceAmountPercentAllowanceLower() != null) {
            String propNm = RefCodeNames.PROPERTY_TYPE_CD.INVOICE_AMT_PERCNT_ALLOW_LOWER;
            distProps.add
                    (PropertyUtil.toPropertyData
                    (0, distId, propNm, propNm,
                    getInvoiceAmountPercentAllowanceLower().toString()
                    ));
        }
        if (getAllowedFreightSurchargeAmount() != null) {
            String propNm = RefCodeNames.PROPERTY_TYPE_CD.ALLOWED_FRT_SURCHARGE_AMOUNT;
            distProps.add
                    (PropertyUtil.toPropertyData
                    (0, distId, propNm, propNm,
                    getAllowedFreightSurchargeAmount().toString()
                    ));
        }


        if (getInvoiceLoadingPriceModel() != null) {
            String propNm = RefCodeNames.PROPERTY_TYPE_CD.INVOICE_LOADING_PRICE_MODEL_CD;
            distProps.add
                    (PropertyUtil.toPropertyData
                    (0, distId, propNm, propNm,
                    getInvoiceLoadingPriceModel()
                    ));
        }

        if (getReceivingSystemInvoiceCd() != null) {
            String propNm = RefCodeNames.PROPERTY_TYPE_CD.RECEIVING_SYSTEM_INVOICE_CD;
            distProps.add
                    (PropertyUtil.toPropertyData
                    (0, distId, propNm, propNm,
                    getReceivingSystemInvoiceCd()
                    ));
        }


        if (getMinimumOrderAmount() != null) {
            String propNm = RefCodeNames.PROPERTY_TYPE_CD.MINIMUM_ORDER_AMOUNT;
            distProps.add
                    (PropertyUtil.toPropertyData
                    (0, distId, propNm, propNm,
                    getMinimumOrderAmount().toString()));
        }

        if (getCallHours() != null) {
            String propNm = RefCodeNames.PROPERTY_TYPE_CD.CALL_HOURS;
            distProps.add
                    (PropertyUtil.toPropertyData
                    (0, distId, propNm, propNm, getCallHours()));
        }

        if (getSmallOrderHandlingFee() != null) {
            String propNm = RefCodeNames.PROPERTY_TYPE_CD.DIST_SMALL_ORDER_FEE;
            distProps.add
                    (PropertyUtil.toPropertyData
                    (0, distId, propNm, propNm,
                    getSmallOrderHandlingFee()));
        }

        if (getWebInfo() != null) {
            String propNm = RefCodeNames.PROPERTY_TYPE_CD.DIST_WEB_INFO;
            distProps.add
                    (PropertyUtil.toPropertyData
                    (0, distId, propNm, propNm,
                    getWebInfo()));
        }
        if (getMaxInvoiceFreightAllowed() != null) {
            String propNm = RefCodeNames.PROPERTY_TYPE_CD.DIST_MAX_INVOICE_FREIGHT;
            distProps.add
                    (PropertyUtil.toPropertyData
                    (0, distId, propNm, propNm,
                    getMaxInvoiceFreightAllowed()));
        }

        if (getAccountNumbers() != null) {
            String propNm = RefCodeNames.PROPERTY_TYPE_CD.DIST_ACCOUNT_NUMBERS;
            distProps.add
                    (PropertyUtil.toPropertyData
                    (0, distId, propNm, propNm,
                    getAccountNumbers()));
        }

        if (getRuntimeDisplayName() != null) {
            String propNm = RefCodeNames.PROPERTY_TYPE_CD.RUNTIME_DISPLAY_NAME;
            distProps.add
                    (PropertyUtil.toPropertyData
                    (0, distId, propNm, propNm, getRuntimeDisplayName()));
        }

        if (getExceptionOnOverchargedFreight() != null) {
            String propNm = RefCodeNames.PROPERTY_TYPE_CD.ERROR_ON_OVERCHARGED_FREIGHT;
            String moprop;
            if (getExceptionOnOverchargedFreight().equals(Boolean.TRUE)) {
                moprop = "true";
            } else {
                moprop = "false";
            }
            distProps.add
                    (PropertyUtil.toPropertyData
                    (0, distId, propNm, propNm, moprop));
        }

        if (getExceptionOnTaxDifference() != null) {
            String propNm = RefCodeNames.PROPERTY_TYPE_CD.EXCEPTION_ON_TAX_DIFFERENCE;
            String moprop;
            moprop=getExceptionOnTaxDifference();
            distProps.add
                    (PropertyUtil.toPropertyData
                    (0, distId, propNm, propNm, moprop));
        }

        if (getIgnoreOrderMinimumForFreight() != null) {
            String propNm = RefCodeNames.PROPERTY_TYPE_CD.IGNORE_ORDER_MIN_FOR_FREIGHT;
            String moprop;
            if (getIgnoreOrderMinimumForFreight().equals(Boolean.TRUE)) {
                moprop = "true";
            } else {
                moprop = "false";
            }
            distProps.add
                    (PropertyUtil.toPropertyData
                    (0, distId, propNm, propNm, moprop));
        }

        if (getAllowFreightOnBackorders() != null) {
            String propNm = RefCodeNames.PROPERTY_TYPE_CD.ALLOW_FREIGHT_ON_BACKORDERS;
            String moprop;
            if (getAllowFreightOnBackorders().equals(Boolean.TRUE)) {
                moprop = "true";
            } else {
                moprop = "false";
            }
            distProps.add
                    (PropertyUtil.toPropertyData
                    (0, distId, propNm, propNm, moprop));
        }

        if (getAllowFreightOnFreightHandlerOrders() != null) {
            String propNm = RefCodeNames.PROPERTY_TYPE_CD.ALLOW_FREIGHT_ON_FH_ORDERS;
            String moprop;
            if (getAllowFreightOnFreightHandlerOrders().equals(Boolean.TRUE)) {
                moprop = "true";
            } else {
                moprop = "false";
            }
            distProps.add
                    (PropertyUtil.toPropertyData
                    (0, distId, propNm, propNm, moprop));
        }

        if (getPurchaseOrderComments() != null) {
            String propNm = RefCodeNames.PROPERTY_TYPE_CD.PURCHASE_ORDER_COMMENTS;
            distProps.add
                    (PropertyUtil.toPropertyData
                    (0, distId, propNm, propNm,
                    getPurchaseOrderComments().toString()));
        }

        {
            String propNm = RefCodeNames.PROPERTY_TYPE_CD.DISTRIBUTORS_COMPANY_CODE;
            String value = getDistributorsCompanyCode();
            if(value == null){
            	value = "";
            }
            distProps.add
                    (PropertyUtil.toPropertyData
                    (0, distId, propNm, propNm,
                    value));
        }

        if (getPrintCustContactOnPurchaseOrder() != null) {
            String propNm = RefCodeNames.PROPERTY_TYPE_CD.PRINT_CUST_CONTACT_ON_PO;
            String moprop;
            if (getPrintCustContactOnPurchaseOrder().equals(Boolean.TRUE)) {
                moprop = "true";
            } else {
                moprop = "false";
            }
            distProps.add
                    (PropertyUtil.toPropertyData
                    (0, distId, propNm, propNm, moprop));
        }

        if (getManualPOAcknowldgementRequiered() != null) {
            String propNm = RefCodeNames.PROPERTY_TYPE_CD.MAN_PO_ACK_REQUIERED;
            String moprop;
            if (getManualPOAcknowldgementRequiered().equals(Boolean.TRUE)) {
                moprop = "true";
            } else {
                moprop = "false";
            }
            distProps.add
                    (PropertyUtil.toPropertyData
                    (0, distId, propNm, propNm, moprop));
        }
        if(getCancelBackorderedLines() != null){
            String propNm = RefCodeNames.PROPERTY_TYPE_CD.CANCEL_BACKORDERED_LINES;
            String moprop;
            moprop = getCancelBackorderedLines().toString();
            distProps.add
                    (PropertyUtil.toPropertyData
                    (0, distId, propNm, propNm, moprop));
        }
        {
            String propNm = RefCodeNames.PROPERTY_TYPE_CD.HOLD_INVOICE;
            String moprop;
            moprop = Integer.toString(getHoldInvoiceDays());
            distProps.add
                    (PropertyUtil.toPropertyData
                    (0, distId, propNm, propNm, moprop));
        }
        {
        	String propNm = RefCodeNames.PROPERTY_TYPE_CD.CUSTOMER_REFERENCE_CODE;
            String moprop;
            moprop = getCustomerReferenceCode();
            distProps.add
                    (PropertyUtil.toPropertyData
                    (0, distId, propNm, propNm, moprop));
        }

        if (Utility.isSet(getExchangeCompanyCode())) {
            String propNm = RefCodeNames.DIST_EXCHANGE_PROPERTY.EXCHANGE_COMPANY_CODE;
            distProps.add(PropertyUtil.toPropertyData
                    (0, distId, propNm, propNm, getExchangeCompanyCode()));
        }
        if (Utility.isSet(getExchangeInventoryURL())) {
            String propNm = RefCodeNames.DIST_EXCHANGE_PROPERTY.EXCHANGE_INVENTORY_URL;
            distProps.add(PropertyUtil.toPropertyData
                    (0, distId, propNm, propNm, getExchangeInventoryURL()));
        }
        if (Utility.isSet(getExchangeUser())) {
            String propNm = RefCodeNames.DIST_EXCHANGE_PROPERTY.EXCHANGE_USER;
            distProps.add(PropertyUtil.toPropertyData
                    (0, distId, propNm, propNm, getExchangeUser()));
        }
        if (Utility.isSet(getExchangePassword())) {
            String propNm = RefCodeNames.DIST_EXCHANGE_PROPERTY.EXCHANGE_PASSWORD;
            distProps.add(PropertyUtil.toPropertyData
            			(0, distId, propNm, propNm, getExchangePassword()));
        }
        if (getDoNotAllowInvoiceEdits() != null) {
            String propNm = RefCodeNames.PROPERTY_TYPE_CD.DO_NOT_ALLOW_INVOICE_EDITS;
            distProps.add(PropertyUtil.toPropertyData (0, distId, propNm, propNm,getDoNotAllowInvoiceEdits().toString()));
        }

        return distProps;
    }

    /**
     * Holds value of property holdInvoiceDays.
     */
    private int holdInvoiceDays;

    /**
     * Getter for property holdInvoiceDays.
     * @return Value of property holdInvoiceDays.
     */
    public int getHoldInvoiceDays() {

        return this.holdInvoiceDays;
    }

    /**
     * Setter for property holdInvoiceDays.
     * @param holdInvoiceDays New value of property holdInvoiceDays.
     */
    public void setHoldInvoiceDays(int holdInvoiceDays) {

        this.holdInvoiceDays = holdInvoiceDays;
    }

    /**
     * Holds value of property receivingSystemInvoiceCd.
     */
    private String receivingSystemInvoiceCd;

    /**
     * Getter for property receivingSystemInvoiceCd.
     * @return Value of property receivingSystemInvoiceCd.
     */
    public String getReceivingSystemInvoiceCd() {

        return this.receivingSystemInvoiceCd;
    }

    /**
     * Setter for property receivingSystemInvoiceCd.
     * @param receivingSystemInvoiceCd New value of property receivingSystemInvoiceCd.
     */
    public void setReceivingSystemInvoiceCd(String receivingSystemInvoiceCd) {

        this.receivingSystemInvoiceCd = receivingSystemInvoiceCd;
    }


	public FreightTableDescData getFreightTable() {
		return freightTable;
	}


	public void setFreightTable(FreightTableDescData freightTable) {
		this.freightTable = freightTable;
	}

	private String exchangeCompanyCode;
	private String exchangeInventoryURL;
	private String exchangeUser;
	private String exchangePassword;
  private PhoneData primaryPhone;
  private AddressData primaryAddress;
  private BusEntityData busEntity;

  public String getExchangeCompanyCode() {
		return exchangeCompanyCode;
	}
	public void setExchangeCompanyCode(String exchangeCompanyCode) {
		this.exchangeCompanyCode = exchangeCompanyCode;
	}
	public String getExchangeInventoryURL() {
		return exchangeInventoryURL;
	}
	public void setExchangeInventoryURL(String exchangeInventoryURL) {
		this.exchangeInventoryURL = exchangeInventoryURL;
	}
	public String getExchangePassword() {
		return exchangePassword;
	}
	public void setExchangePassword(String exchangePassword) {
		this.exchangePassword = exchangePassword;
	}
	public String getExchangeUser() {
		return exchangeUser;
	}
	public void setExchangeUser(String exchangeUser) {
		this.exchangeUser = exchangeUser;
	}

  public void setPrimaryPhone(PhoneData primaryPhone) {
    this.mPrimaryPhone = primaryPhone;
  }

  public void setPrimaryAddress(AddressData primaryAddress) {
    this.mPrimaryAddress = primaryAddress;
  }

  public void setBusEntity(BusEntityData busEntity) {
    this.mBusEntity = busEntity;
  }

    public Boolean getDoNotAllowInvoiceEdits() {
        return this.mDoNotAllowInvoiceEdits;
    }

    public void setDoNotAllowInvoiceEdits(Boolean mDoNotAllowInvoiceEdits) {
        this.mDoNotAllowInvoiceEdits = mDoNotAllowInvoiceEdits;
    }
		/**
		 * @return the mRejectedInvEmail
		 */
		public EmailData getRejectedInvEmail() {
			return mRejectedInvEmail;
		}
		/**
		 * @param rejectedInvEmail the RejectedInvEmail to set
		 */
		public void setRejectedInvEmail(EmailData rejectedInvEmail) {
			mRejectedInvEmail = rejectedInvEmail;
		}
    
}


