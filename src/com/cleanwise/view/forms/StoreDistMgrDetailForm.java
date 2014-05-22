/**
 *  Title: DistMgrDetailForm Description: This is the Struts ActionForm class
 *  for user management page. Purpose: Strut support to search for distributors.
 *  Copyright: Copyright (c) 2001 Company: CleanWise, Inc.
 *
 *@author     durval
 */

package com.cleanwise.view.forms;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import java.util.ArrayList;
import com.cleanwise.service.api.value.*;


/**
 *  Form bean for the user manager page.
 *
 *@author     durval
 *@created    August 8, 2001
 */
public final class StoreDistMgrDetailForm extends StorePortalBaseForm {
    private String distNumber;
    private ManufacturerDataVector mManufFilter;
    private String[] mSelectedLines = new String[0];

    private String _searchField = "";
    private boolean _containsFlag = false;
    private boolean _showInactiveFlag = false;
    private String _catalogId = null;
    private String _oldCatalogId = null;



    private String poFax;
    private String _doNotAllowInvoiceEdits;
    private String _rejectedInvEmail;
    private String _distLocale;

    public void setPoFax(String pPoFax) {
        poFax = pPoFax;
    }
    public String getPoFax() {
        if ( null == poFax ) {
      return "";
  }
        return poFax;
    }

    private String mCustomerReferenceCode;

    public String getCustomerReferenceCode(){
      return mCustomerReferenceCode;
    }
    public void setCustomerReferenceCode(String pVal){
      mCustomerReferenceCode = pVal;
    }


    /**
     *  Sets the DistNumber attribute of the DistMgrDetailForm object
     *
     *@param  pDistNumber  The new DistNumber value
     */
    public void setDistNumber(String pDistNumber) {
        this.distNumber = pDistNumber;
    }

    /**
     *  Gets the DistNumber attribute of the DistMgrDetailForm object
     *
     *@return    The DistNumber value
     */
    public String getDistNumber() {
  if (  distNumber == null ) {
      return "";
  }
        return distNumber;
    }

    /**
     *  <code>reset</code> method, set the search fiels to null.
     *
     *@param  mapping  an <code>ActionMapping</code> value
     *@param  request  a <code>HttpServletRequest</code> value
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        this._searchField = "";
        this._containsFlag = false;
        this._showInactiveFlag = false;
        this.mSelectIds = new String[0];
        this.mDisplayIds = new String[0];
        mConfShowInactiveFl = false;
        mConfShowConfguredOnlyFl = false;
        mConfMoveSitesFl = false;

    }


    /**
     *  <code>validate</code> method is a stub.
     *
     *@param  mapping  an <code>ActionMapping</code> value
     *@param  request  a <code>HttpServletRequest</code> value
     *@return          an <code>ActionErrors</code> value
     */
    public ActionErrors validate(ActionMapping mapping,
            HttpServletRequest request) {
        // Validation happens in the logic bean.
        return super.validate(mapping, request);
    }

    AddressData ShipFrom;

    /** Holds value of property exceptionOnOverchargedFreight. */
    private String exceptionOnOverchargedFreight,
  ignoreOrderMinimumForFreight;

    private String minimumOrderAmount,
  smallOrderHandlingFee, accountNumbers,
  webInfo ;

    /** Holds value of property invoiceLoadingPriceModel. */
    private String invoiceLoadingPriceModel;

    /** Holds value of property invoiceLoadingPriceExceptionThreshold. */
    private String invoiceAmountPercentAllowanceUpper;
    private String invoiceAmountPercentAllowanceLower;
    private String allowedFreightSurchargeAmount;

    /** Holds value of property allowFreightOnBackorders. */
    private String allowFreightOnBackorders;

    /** Holds value of property purchaseOrderComments. */
    private String purchaseOrderComments;

    /** Holds value of property printCustPhoneOnPurchaseOrder. */
    private String printCustContactOnPurchaseOrder;

    /** Holds value of property allowFreightOnFreightHandlerOrders. */
    private String allowFreightOnFreightHandlerOrders;

    /** Holds value of property distributorsCompanyCode. */
    private String distributorsCompanyCode;

    private ContactViewVector additionalContacts;

    private ContactView wrkContact, wrkBranchContact;
    private String mWrkContactType = "";

    private GroupDataVector distGroups = new GroupDataVector();

    private int manufacturerId = 0;

    private BusEntityDataVector primaryManufacturers = new BusEntityDataVector();

    private AddressData billingAddress = AddressData.createValue();

    private ArrayList mDistBranchesCollection;

    private AddressData wrkAddress = null;

    private ArrayList servedStates = new ArrayList();

    private BusEntityDataVector servedAccounts = new BusEntityDataVector();

    private String manualPOAcknowldgementRequiered;

    /**
     * Holds value of property callHours.
     */
    private String callHours;

    /**
     * Holds value of property runtimeDisplayName.
     */
    private String runtimeDisplayName;

    /**
     * Holds value of property storeId.
     */
    private String storeId;

    /**
     * Get the value of ShipFrom.
     * @return value of ShipFrom.
     */
    public AddressData getShipFrom() {
  if ( null == ShipFrom ) {
      ShipFrom = AddressData.createValue();
  }
  return ShipFrom;
    }

    /**
     * Set the value of ShipFrom.
     * @param v  Value to assign to ShipFrom.
     */
    public void setShipFrom(AddressData  v) {
  this.ShipFrom = v;
    }

    /** Getter for property exceptionOnOverchargedFreight.
     * @return Value of property exceptionOnOverchargedFreight.
     *
     */
    public String getExceptionOnOverchargedFreight() {
        return this.exceptionOnOverchargedFreight;
    }

    /** Setter for property exceptionOnOverchargedFreight.
     * @param exceptionOnOverchargedFreight New value of property exceptionOnOverchargedFreight.
     *
     */
    public void setExceptionOnOverchargedFreight(String exceptionOnOverchargedFreight) {
        this.exceptionOnOverchargedFreight = exceptionOnOverchargedFreight;
    }

    String exceptionOnTaxDifference;
    public String getExceptionOnTaxDifference() {
    return exceptionOnTaxDifference;
  }
  public void setExceptionOnTaxDifference(String exceptionOnTaxDifference) {
    this.exceptionOnTaxDifference = exceptionOnTaxDifference;
  }


    public String getIgnoreOrderMinimumForFreight() {
  if ( null == this.ignoreOrderMinimumForFreight ) {
      return "";
  }
        return this.ignoreOrderMinimumForFreight;
    }

    public void setIgnoreOrderMinimumForFreight(String v) {
        this.ignoreOrderMinimumForFreight = v;
    }

    public String getMinimumOrderAmount() {
        return this.minimumOrderAmount;
    }

    private String mMaxInvoiceFreightAllowed;
    public void setMaxInvoiceFreightAllowed(String v) {
        this.mMaxInvoiceFreightAllowed = v;
    }

    public String getMaxInvoiceFreightAllowed() {
  if ( null == this.mMaxInvoiceFreightAllowed ) {
      return "";
  }
        return this.mMaxInvoiceFreightAllowed;
    }

    public void setMinimumOrderAmount(String minimumOrderAmount) {
        this.minimumOrderAmount = minimumOrderAmount;
    }

    public String getSmallOrderHandlingFee() {
  if ( null == this.smallOrderHandlingFee ) {
      return "";
  }
        return this.smallOrderHandlingFee;
    }

    public void setSmallOrderHandlingFee(String v) {
        this.smallOrderHandlingFee = v;
    }

    public String getWebInfo() {
  if ( null == this.webInfo ) {
      return "";
  }
        return this.webInfo;
    }

    public void setWebInfo(String v) {
        this.webInfo = v;
    }

    public String getAccountNumbers() {
  if ( null == this.accountNumbers ) {
      return "";
  }
        return this.accountNumbers;
    }

    public void setAccountNumbers(String v) {
        this.accountNumbers = v;
    }


    /** Getter for property invoiceLoadingModel.
     * @return Value of property invoiceLoadingModel.
     *
     */
    public String getInvoiceLoadingPriceModel() {
        return this.invoiceLoadingPriceModel;
    }

    /** Setter for property invoiceLoadingModel.
     * @param invoiceLoadingModel New value of property invoiceLoadingModel.
     *
     */
    public void setInvoiceLoadingPriceModel(String invoiceLoadingPriceModel) {
        this.invoiceLoadingPriceModel = invoiceLoadingPriceModel;
    }

    /** Getter for property invoiceLoadingPriceExceptionThreshold.
     * @return Value of property invoiceLoadingPriceExceptionThreshold.
     *
     */
    public String getInvoiceAmountPercentAllowanceUpper() {
        return this.invoiceAmountPercentAllowanceUpper;
    }

    /** Setter for property invoiceLoadingPriceExceptionThreshold.
     * @param invoiceLoadingPriceExceptionThreshold New value of property invoiceLoadingPriceExceptionThreshold.
     *
     */
    public void setInvoiceAmountPercentAllowanceUpper(String invoiceLoadingPriceExceptionThreshold) {
        this.invoiceAmountPercentAllowanceUpper = invoiceLoadingPriceExceptionThreshold;
    }

    /** Getter for property allowFreightOnBackorders.
     * @return Value of property allowFreightOnBackorders.
     *
     */
    public String getAllowFreightOnBackorders() {
        return this.allowFreightOnBackorders;
    }

    /** Setter for property allowFreightOnBackorders.
     * @param allowFreightOnBackorders New value of property allowFreightOnBackorders.
     *
     */
    public void setAllowFreightOnBackorders(String allowFreightOnBackorders) {
        this.allowFreightOnBackorders = allowFreightOnBackorders;
    }

    /** Getter for property purchaseOrderComments.
     * @return Value of property purchaseOrderComments.
     *
     */
    public String getPurchaseOrderComments() {
        return this.purchaseOrderComments;
    }

    /** Setter for property purchaseOrderComments.
     * @param purchaseOrderComments New value of property purchaseOrderComments.
     *
     */
    public void setPurchaseOrderComments(String purchaseOrderComments) {
        this.purchaseOrderComments = purchaseOrderComments;
    }

    /** Getter for property printCustPhoneOnPurchaseOrder.
     * @return Value of property printCustPhoneOnPurchaseOrder.
     *
     */
    public String getPrintCustContactOnPurchaseOrder() {
        return this.printCustContactOnPurchaseOrder;
    }

    /** Setter for property printCustPhoneOnPurchaseOrder.
     * @param printCustPhoneOnPurchaseOrder New value of property printCustPhoneOnPurchaseOrder.
     *
     */
    public void setPrintCustContactOnPurchaseOrder(String printCustContactOnPurchaseOrder) {
        this.printCustContactOnPurchaseOrder = printCustContactOnPurchaseOrder;
    }

    /** Getter for property allowFreightOnFreightHandlerOrders.
     * @return Value of property allowFreightOnFreightHandlerOrders.
     *
     */
    public String getAllowFreightOnFreightHandlerOrders() {
        return this.allowFreightOnFreightHandlerOrders;
    }

    /** Setter for property allowFreightOnFreightHandlerOrders.
     * @param allowFreightOnFreightHandlerOrders New value of property allowFreightOnFreightHandlerOrders.
     *
     */
    public void setAllowFreightOnFreightHandlerOrders(String allowFreightOnFreightHandlerOrders) {
        this.allowFreightOnFreightHandlerOrders = allowFreightOnFreightHandlerOrders;
    }

    /** Getter for property distributorsCompanyCode.
     * @return Value of property distributorsCompanyCode.
     *
     */
    public String getDistributorsCompanyCode() {
        return this.distributorsCompanyCode;
    }

    /** Setter for property distributorsCompanyCode.
     * @param distributorsCompanyCode New value of property distributorsCompanyCode.
     *
     */
    public void setDistributorsCompanyCode(String distributorsCompanyCode) {
        this.distributorsCompanyCode = distributorsCompanyCode;
    }

    /** Getter for property additionalContacts.
     * @return Value of property additionalContacts.
     *
     */
    public ContactViewVector getAdditionalContacts() {
        return this.additionalContacts;
    }

    /** Setter for property additionalContacts.
     * @param distributorsCompanyCode New value of property additionalContacts.
     *
     */
    public void setAdditionalContacts(ContactViewVector additionalContacts) {
        this.additionalContacts = additionalContacts;
    }

    /** Getter for property wrkContact.
     * @return Value of property wrkContact.
     *
     */
    public ContactView getWrkContact() {
        return this.wrkContact;
    }
    public ContactView getWrkBranchContact() {
        return this.wrkBranchContact;
    }
    public ContactView getNewBranchContact() {
        return this.wrkBranchContact;
    }

    /** Setter for property wrkContact.
     * @param distributorsCompanyCode New value of property wrkContact.
     *
     */
    public void setWrkContact(ContactView wrkContact) {
        this.wrkContact = wrkContact;
    }
    public void setWrkBranchContact(ContactView v) {
        this.wrkBranchContact = v;
    }
    public void setNewBranchContact(ContactView v) {
        this.wrkBranchContact = v;
    }

    /** Getter for property distGroups.
     * @return Value of property distGroups.
     *
     */
    public GroupDataVector getDistGroups() {
        return this.distGroups;
    }

    /** Setter for property distGroups.
     * @param distributorsCompanyCode New value of property distGroups.
     *
     */
    public void setDistGroups(GroupDataVector distGroups) {
        this.distGroups = distGroups;
    }

    /** Getter for property manufacturerId.
     * @return Value of property manufacturerId.
     *
     */
    public int getManufacturerId() {
        return this.manufacturerId;
    }

    /** Setter for property manufacturerId.
     * @param distributorsCompanyCode New value of property manufacturerId.
     *
     */
    public void setManufacturerId(int manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    /** Getter for property primaryManufacturers
     * @return Value of property primaryManufacturers.
     *
     */
    public BusEntityDataVector getPrimaryManufacturers() {
        return this.primaryManufacturers;
    }

    /** Setter for property primaryManufacturers.
     * @param distributorsCompanyCode New value of property primaryManufacturers.
     *
     */
    public void setPrimaryManufacturers(BusEntityDataVector primaryManufacturers) {
        this.primaryManufacturers = primaryManufacturers;
    }

    /** Getter for property billingAddress
     * @return Value of property billingAddress.
     *
     */
    public AddressData getBillingAddress() {
        return this.billingAddress;
    }

    /** Setter for property billingAddress.
     * @param distributorsCompanyCode New value of property billingAddress.
     *
     */
    public void setBillingAddress(AddressData billingAddress) {
        this.billingAddress = billingAddress;
    }

    public ArrayList getBranchesCollection() {
        return this.mDistBranchesCollection;
    }

    public void setBranchesCollection(ArrayList v ) {
        this.mDistBranchesCollection = v;
    }

    /** Getter for property wrkAddress
     * @return Value of property wrkAddress.
     *
     */
    public AddressData getWrkAddress() {
        return this.wrkAddress;
    }

    /** Setter for property wrkAddress.
     * @param distributorsCompanyCode New value of property wrkAddress.
     *
     */
    public void setWrkAddress(AddressData wrkAddress) {
        this.wrkAddress = wrkAddress;
    }

    /** Getter for property servedStates
     * @return Value of property servedStates.
     *
     */
    public ArrayList getServedStates() {
        return this.servedStates;
    }

    /** Setter for property servedStates.
     * @param distributorsCompanyCode New value of property servedStates.
     *
     */
    public void setServedStates(ArrayList servedStates) {
        this.servedStates = servedStates;
    }

    /** Getter for property servedAccounts
     * @return Value of property servedAccounts.
     *
     */
    public BusEntityDataVector getServedAccounts() {
        return this.servedAccounts;
    }

    /** Setter for property servedAccounts.
     * @param distributorsCompanyCode New value of property servedAccounts.
     *
     */
    public void setServedAccounts(BusEntityDataVector servedAccounts) {
        this.servedAccounts = servedAccounts;
    }

    /**
     * Getter for property manualPOAcknowldgementRequiered.
     * @return Value of property manualPOAcknowldgementRequiered.
     */
    public String getManualPOAcknowldgementRequiered() {
        return this.manualPOAcknowldgementRequiered;
    }

    /**
     * Setter for property manualPOAcknowldgementRequiered.
     * @param manualPOAcknowldgementRequiered New value of property manualPOAcknowldgementRequiered.
     */
    public void setManualPOAcknowldgementRequiered(String manualPOAcknowldgementRequiered) {
        this.manualPOAcknowldgementRequiered = manualPOAcknowldgementRequiered;
    }

    /**
     * Getter for property callHours.
     * @return Value of property callHours.
     */
    public String getCallHours() {

        return this.callHours;
    }

    /**
     * Setter for property callHours.
     * @param callHours New value of property callHours.
     */
    public void setCallHours(String callHours) {

        this.callHours = callHours;
    }

    /**
     * Getter for property runtimeDisplayName.
     * @return Value of property runtimeDisplayName.
     */
    public String getRuntimeDisplayName() {

        return this.runtimeDisplayName;
    }

    /**
     * Setter for property runtimeDisplayName.
     * @param runtimeDisplayName New value of property runtimeDisplayName.
     */
    public void setRuntimeDisplayName(String runtimeDisplayName) {

        this.runtimeDisplayName = runtimeDisplayName;
    }

    /**
     * Getter for property storeId.
     * @return Value of property storeId.
     */
    public String getStoreId() {

        return this.storeId;
    }

    /**
     * Setter for property storeId.
     * @param storeId New value of property storeId.
     */
    public void setStoreId(String storeId) {

        this.storeId = storeId;
    }

    public String getWrkContactType() {
  return mWrkContactType;
    }

    public void setWrkContactType(String v) {
  mWrkContactType = v;
    }

    /**
     * Holds value of property cancelBackorderedLines.
     */
    private String cancelBackorderedLines;

    /**
     * Getter for property cancelBackorderedLines.
     * @return Value of property cancelBackorderedLines.
     */
    public String getCancelBackorderedLines() {

        return this.cancelBackorderedLines;
    }

    /**
     * Setter for property cancelBackorderedLines.
     * @param cancelBackorderedLines New value of property cancelBackorderedLines.
     */
    public void setCancelBackorderedLines(String cancelBackorderedLines) {

        this.cancelBackorderedLines = cancelBackorderedLines;
    }

    /**
     * Holds value of property holdInvoiceDays.
     */
    private String holdInvoiceDays;

    /**
     * Getter for property holdInvoiceDays.
     * @return Value of property holdInvoiceDays.
     */
    public String getHoldInvoiceDays() {

        return this.holdInvoiceDays;
    }

    /**
     * Setter for property holdInvoiceDays.
     * @param holdInvoiceDays New value of property holdInvoiceDays.
     */
    public void setHoldInvoiceDays(String holdInvoiceDays) {

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

    public ManufacturerDataVector getManufFilter() {return mManufFilter;}
    public void setManufFilter(ManufacturerDataVector pVal) {mManufFilter = pVal;}

    private ManufacturerDataVector mStoreManufacturers = new ManufacturerDataVector();

    public  ManufacturerDataVector getStoreManufacturers() {return mStoreManufacturers;}
    public void setStoreManufacturers( ManufacturerDataVector pVal) {mStoreManufacturers = pVal;}

    public String[] getSelectedLines() {return mSelectedLines;}
    public void setSelectedLines(String[] pVal) {mSelectedLines = pVal;}
  public String getInvoiceAmountPercentAllowanceLower() {
    return invoiceAmountPercentAllowanceLower;
  }
  public void setInvoiceAmountPercentAllowanceLower(
      String invoiceAmountPercentAllowanceLower) {
    this.invoiceAmountPercentAllowanceLower = invoiceAmountPercentAllowanceLower;
  }
  public String getAllowedFreightSurchargeAmount() {
    return allowedFreightSurchargeAmount;
  }
  public void setAllowedFreightSurchargeAmount(
      String allowedFreightSurchargeAmount) {
    this.allowedFreightSurchargeAmount = allowedFreightSurchargeAmount;
  }


  private String exchangeCompanyCode;
  private String exchangeInventoryURL;
  private String exchangeUser;
  private String exchangePassword;

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


    //-- config data
    private CatalogDataVector mConfCatalogFilter = null;
    private String mConfSearchField = "";
    private String mConfSearchType = "nameBegins";
    private String mConfType = "Catalogs";
    private String mConfCity = "";
    private String mConfCounty = "";
    private String mConfState = "";
    private String mConfZipcode = "";
    private boolean mConfShowInactiveFl = false;
    private boolean mConfShowConfguredOnlyFl = false;
    private boolean mConfMoveSitesFl = false;
    private String[] mAssocSiteIds = null;
    private String[] mSelectIds = null;
    private String[] mDisplayIds = null;

    // --  Configuration
    public CatalogDataVector getConfCatalogFilter() {return mConfCatalogFilter;}
    public void setConfCatalogFilter(CatalogDataVector pVal) {mConfCatalogFilter = pVal;}

    public String getConfSearchField() {return mConfSearchField;}
    public void setConfSearchField(String pVal) {mConfSearchField = pVal;}

    public String getConfSearchType() {return mConfSearchType;}
    public void setConfSearchType(String pVal) {mConfSearchType = pVal;}

    public String getConfType() {return mConfType;}
    public void setConfType(String pVal) {mConfType = pVal;}

    public String getConfCity() {return mConfCity;}
    public void setConfCity(String pVal) {mConfCity = pVal;}

    public String getConfCounty() {return mConfCounty;}
    public void setConfCounty(String pVal) {mConfCounty = pVal;}

    public String getConfState() {return mConfState;}
    public void setConfState(String pVal) {mConfState = pVal;}

    public String getConfZipcode() {return mConfZipcode;}
    public void setConfZipcode(String pVal) {mConfZipcode = pVal;}

    public boolean getConfShowInactiveFl() {return mConfShowInactiveFl;}
    public boolean isConfShowInactiveFl() {return mConfShowInactiveFl;}
    public void setConfShowInactiveFl(boolean pVal) {mConfShowInactiveFl = pVal;}

    public boolean getConfShowConfguredOnlyFl() {return mConfShowConfguredOnlyFl;}
    public boolean isConfShowConfguredOnlyFl() {return mConfShowConfguredOnlyFl;}
    public void setConfShowConfguredOnlyFl(boolean pVal) {mConfShowConfguredOnlyFl = pVal;}

    public boolean getConfMoveSitesFl() {return mConfMoveSitesFl;}
    public boolean isConfMoveSitesFl() {return mConfMoveSitesFl;}
    public void setConfMoveSitesFl(boolean pVal) {mConfMoveSitesFl = pVal;}

    public String[] getAssocSiteIds() {return mAssocSiteIds;}
    public void setAssocSiteIds(String[] pVal) {mAssocSiteIds = pVal;}

    public String[] getSelectIds() {return mSelectIds;}
    public void setSelectIds(String[] pVal) {mSelectIds = pVal;}

    public String[] getDisplayIds() {return mDisplayIds;}
    public void setDisplayIds(String[] pVal) {mDisplayIds = pVal;}

    public String getCatalogId() {return (this._catalogId);}
    public void setCatalogId(String pVal) {this._catalogId = pVal;}

    public String getOldCatalogId() {return (this._oldCatalogId);}
    public void setOldCatalogId(String pVal) {this._oldCatalogId = pVal;}

    public String getDoNotAllowInvoiceEdits() {  return this._doNotAllowInvoiceEdits; }
    public void setDoNotAllowInvoiceEdits(String pVal) {  this._doNotAllowInvoiceEdits = pVal; }
		/**
		 * @return the _rejectedInvEmail
		 */
		public String getRejectedInvEmail() {
			return _rejectedInvEmail;
		}
		/**
		 * @param invEmail the _rejectedInvEmail to set
		 */
		public void setRejectedInvEmail(String invEmail) {
			_rejectedInvEmail = invEmail;
		}
		/**
		 * @return the _distLocale
		 */
		public String getDistLocale() {
			return _distLocale;
		}
		/**
		 * @param locale the _distLocale to set
		 */
		public void setDistLocale(String locale) {
			_distLocale = locale;
		}
    
}

