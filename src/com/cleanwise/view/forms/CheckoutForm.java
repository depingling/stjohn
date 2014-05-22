/**
 * Title:        UserShopForm
 * Description:  This is the Struts ActionForm class for the shopping page.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Yuriy Kupershmidt
 */

package com.cleanwise.view.forms;
import java.math.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;
import org.apache.struts.util.LabelValueBean;

import com.cleanwise.service.api.session.ShoppingServicesBean.ServiceFeeDetail;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.view.utils.*;


/**
 * Form bean for the shopping page.  This form has the following fields,
 */

public class CheckoutForm extends ShoppingForm implements UpdaterAfterStoreChange {

    // ----------------------------------------- Instance Variables
    private boolean _confirmationFlag = false;
    private boolean _orderServiceFlag =false;
    private boolean _earlyReleaseFlag =false;
    private ProcessOrderResultData _orderResult = null;
    private int _userId = -1;
    private SiteData _site = null;
    private int _siteId = -1;
    private AccountData _account = null;
    private ShoppingCartItemDataVector _items = new ShoppingCartItemDataVector();
    private ShoppingCartServiceDataVector _services=new  ShoppingCartServiceDataVector();
    private List _itemIdQty = new ArrayList();
    private String _comments = "";
    private String _poNumber = "";
    private String _workOrderNumber = "";
    private String _otherPaymentInfo = "";
    private String _processOrderDate = "";
    private BigDecimal _cartAmt = new BigDecimal(0);
    private BigDecimal _cartServiceAmt=new BigDecimal(0);
    private BigDecimal _freightAmt = new BigDecimal(0);
    private BigDecimal _handlingAmt = new BigDecimal(0);
    private BigDecimal _serviceFeeAmt = new BigDecimal(0);
    private BigDecimal _smallOrderFeeAmt = new BigDecimal(0);
    private BigDecimal _fuelSurchargeAmt = new BigDecimal(0);
    private BigDecimal _discountAmt = new BigDecimal(0); //SVC: new
    private HashMap _discountAmtPerDist = new HashMap<Integer,BigDecimal>(); //SVC: new

    private HashMap serviceFeeDetailObj;
    private BigDecimal _miscAmt = new BigDecimal(0);
    private int _costCenterId = 0;
    private String _costCenterName = "";
    private String _ccType = "";
    private String _ccNumber = "";
    private int _ccExpMonth = -1;
    private int _ccExpYear = -1;
    private String _ccPersonName = "";
    private String _ccStreet1 = "";
    private String _ccStreet2 = "";
    private String _ccCity = "";
    private String _ccState = "";
    private String _ccZipCode = "";

    private int _siteAddressId = -1;
    private String _siteName1 = "";
    private String _siteName2 = "";
    private String _siteShortDesc = "";
    private String _siteAddress1 = "";
    private String _siteAddress2 = "";
    private String _siteAddress3 = "";
    private String _siteAddress4 = "";
    private String _siteCity = "";
    private String _siteStateProvinceCd = "";
    private String _siteCountryCd = "";
    private String _sitePostalCode = "";
    
    private String _alternateAddress1 = "";
    private String _alternateAddress2 = "";
    private String _alternateAddress3 = "";
    private String _alternateCity = "";
    private String _alternateStateProvince = "";
    private String _alternateCountry = "";
    private String _alternatePostalCode = "";
    private String _alternatePhoneNum = "";

    private int _accountAddressId = -1;
    private String _accountNumber = "";
    private String _accountShortDesc = "";
    private String _accountAddress1 = "";
    private String _accountAddress2 = "";
    private String _accountAddress3 = "";
    private String _accountAddress4 = "";
    private String _accountCity = "";
    private String _accountStateProvinceCd = "";
    private String _accountCountryCd = "";
    private String _accountPostalCode = "";

    private String _rebillOrder = "";

    private CostCenterDataVector _costCenters = new CostCenterDataVector();
    private int _sortBy = 0;
    private int _sortServiceBy=0;

    private String orderContactName = "";
    private String orderContactPhoneNum = "";
    private String orderContactEmail = "";
    private String orderOriginationMethod = "";
    private CustomerOrderRequestData _orderRequest = null;
    private boolean _allowAlternateShipTo = false;

    private String _reqPmt = "0";

    public String getReqPmt() { return _reqPmt; }
    public void setReqPmt(String v) { _reqPmt = v; }
    public boolean isaCcReq() {
      if ( null == _reqPmt ) _reqPmt = "";
      return "CC".equals(_reqPmt);
    }

    private long[] _orderSelectBox = new long[0];
    private String[] _orderServiceSelectBox=new String[0];
    private String[] _reSaleSelectBox = new String[0];

    boolean _pendingConsolidation = false;
    private ReplacedOrderViewVector _replacedOrders;

    private String requestedShipDate;
    private List<BusEntityFieldDataElement> mCheckoutFieldProperties = new ArrayList<BusEntityFieldDataElement>();

    private String _xiPayCCToken = "";
    private int _xiPayCCType = -1;
    private int _xiPayBinRangeType = -1;
    private String _budgetYearPeriod;
    private String _budgetYearPeriodLabel;
    private List<LabelValueBean> _budgetPeriodChoices;


    private String _orderServiceTicketNumbers; //used only if shopping has been initiated from orca

    public String getRequestedShipDate() {
    return requestedShipDate;
  }
  public void setRequestedShipDate(String requestedShipDate) {
    this.requestedShipDate = requestedShipDate;
  }

  private boolean _isStateProvinceRequired = true;

  public void setIsStateProvinceRequired(boolean v) {
    _isStateProvinceRequired = v;
  }
  public boolean isStateProvinceRequired() {
    return _isStateProvinceRequired;
  }

  public void init() {
        _confirmationFlag = false;
        _orderServiceFlag=false;
        _earlyReleaseFlag=false;
        _orderResult = null;
        _userId = -1;
        _site = null;
        _siteId = -1;
        _account = null;
        _items = new ShoppingCartItemDataVector();
        _services=new ShoppingCartServiceDataVector();
        _itemIdQty = new ArrayList();
        _comments = "";
        _poNumber = "";
        _workOrderNumber = "";
        _otherPaymentInfo = "";
        _processOrderDate = "";
        _cartAmt = new BigDecimal(0);
        _freightAmt = new BigDecimal(0);
        _discountAmt = new BigDecimal(0); //SVC: new
        _discountAmtPerDist = new HashMap<Integer,BigDecimal>(); //SVC: new
        _handlingAmt = new BigDecimal(0);
        _serviceFeeAmt = new BigDecimal(0);
        serviceFeeDetailObj = new HashMap();
        _miscAmt = new BigDecimal(0);
        _costCenterId = 0;
        _costCenterName = "";
        _ccType = "";
        _ccNumber = "";
        _ccExpMonth = -1;
        _ccExpYear = -1;
        _ccPersonName = "";
        _ccStreet1 = "";
        _ccStreet2 = "";
        _ccCity = "";
        _ccState = "";
        _ccZipCode = "";
        _siteAddressId = -1;
        _siteName1 = "";
        _siteName2 = "";
        _siteShortDesc = "";
        _siteAddress1 = "";
        _siteAddress2 = "";
        _siteAddress3 = "";
        _siteAddress4 = "";
        _siteCity = "";
        _siteStateProvinceCd = "";
        _siteCountryCd = "";
        _sitePostalCode = "";
        _accountAddressId = -1;
        _accountNumber = "";
        _accountShortDesc = "";
        _accountAddress1 = "";
        _accountAddress2 = "";
        _accountAddress3 = "";
        _accountAddress4 = "";
        _accountCity = "";
        _accountStateProvinceCd = "";
        _accountCountryCd = "";
        _accountPostalCode = "";
        _costCenters = new CostCenterDataVector();
        _sortBy = 0;
        _sortServiceBy=0;
        orderContactName = "";
        orderContactPhoneNum = "";
        orderContactEmail = "";
        orderOriginationMethod = "";
        billingOrder = false;
        orderNote = null;
        billingDistributorInvoice = "";
        billingOriginalPurchaseOrder = "";
        customerRequestedReshipOrderNum = "";
        customerComment = "";
        _replacedOrders = null;
        _pendingConsolidation = false;
        _orderRequest = null;
        _orderServiceSelectBox=new String[0];
        _reSaleSelectBox = new String[0];
        requestedShipDate = "";
        allowPONumberByVendor = false;
        setDistFreightVendor(new String[0]);
        allowPONumberByVendor = false;
        _allowAlternateShipTo = false;
        _alternateAddress1 = "";
        _alternateAddress2 = "";
        _alternateAddress3 = "";
        _alternateCity = "";
        _alternateStateProvince = "";
        _alternateCountry = "";
        _alternatePostalCode = "";
        _alternatePhoneNum = "";
        _orderServiceTicketNumbers = "";        
    }

    // ------------------------------------------------------- Properties
    public void setConfirmationFlag(boolean pValue) {
        _confirmationFlag = pValue;
  if ( pValue == true ) {
      // Set the billing address used to place
      // the order.
      if (hasRequestedBillToAddress()) {
    setConfirmBillToAddress(getRequestedBillToAddress());
      }
      else {
    setConfirmBillToAddress(_account.getBillingAddress());
      }
  }
    }

    public boolean getConfirmationFlag() {
        return _confirmationFlag;
    }

    public void setOrderResult(ProcessOrderResultData pValue) {
        _orderResult = pValue;
    }
    public ProcessOrderResultData getOrderResult() {
        return _orderResult;
    }

    public void setUserId(int pValue) {
        _userId = pValue;
    }
    public int getUserId() {
        return _userId;
    }

    public void setSite(SiteData pValue) {
        _site = pValue;
        setSiteId(pValue.getSiteId());
        _siteShortDesc = _site.getBusEntity().getShortDesc();
        _siteAddress1 = _site.getSiteAddress().getAddress1();
        _siteAddress2 = _site.getSiteAddress().getAddress2();
        _siteAddress3 = _site.getSiteAddress().getAddress3();
        _siteAddress4 = _site.getSiteAddress().getAddress4();
        _siteName1 = _site.getSiteAddress().getName1();
        _siteName2 = _site.getSiteAddress().getName2();
        _siteCity = _site.getSiteAddress().getCity();
        _siteStateProvinceCd = _site.getSiteAddress().
      getStateProvinceCd();
        _siteCountryCd = _site.getSiteAddress().getCountryCd();
        _sitePostalCode = _site.getSiteAddress().getPostalCode();

    }

    public SiteData getSite() {
        return _site;
    }

    public void setSiteId(int pValue) {
        _siteId = pValue;
    }
    public int getSiteId() {
        return _siteId;
    }
    private String _shippingMessage;

    /** Holds value of property orderNote. */
    private String orderNote;

    /** Holds value of property billingOrder. */
    private boolean billingOrder,
  _bypassOrderRouting = false;

    /** Holds value of property billingDistributorInvoice. */
    private String billingDistributorInvoice="";

    /** Holds value of property billingOriginalPurchaseOrder. */
    private String billingOriginalPurchaseOrder="";

    /** Holds value of property customerRequestedReshipOrderNum. */
    private String customerRequestedReshipOrderNum;

    /** Holds value of property customerComment. */
    private String customerComment;

    /**
     * Holds value of property customerSystemId.
     */
    private String customerSystemId;

    /**
     * Holds value of property inlinePostData.
     */
    private String inlinePostData;

    /**
     * Holds value of property inlinePostFieldName.
     */
    private String inlinePostFieldName;

    /**
     * Holds value of property bypassCustomerWorkflow.
     */
    private boolean bypassCustomerWorkflow;

    /**
     * Get the value of ShippingMessage.
     * @return value of ShippingMessage.
     */
    public String getShippingMessage() {
        if ( null == _shippingMessage )
      {
    _shippingMessage = "";
      }
        return _shippingMessage;
    }

    /**
     * Set the value of ShippingMessage.
     * @param v  Value to assign to ShippingMessage.
     */
    public void setShippingMessage(String  v) {
        this._shippingMessage = v;
    }

    public void setAccount(AccountData pValue) {
        _account = pValue;
        _accountNumber = _account.getBusEntity().getErpNum();
        _accountShortDesc = _account.getBusEntity().getShortDesc();
        _accountAddress1 = _account.getBillingAddress().getAddress1();
        _accountAddress2 = _account.getBillingAddress().getAddress2();
        _accountAddress3 = _account.getBillingAddress().getAddress3();
        _accountAddress4 = _account.getBillingAddress().getAddress4();
        _accountCity = _account.getBillingAddress().getCity();
        _accountStateProvinceCd = _account.getBillingAddress().
      getStateProvinceCd();
        _accountCountryCd = _account.getBillingAddress().getCountryCd();
        _accountPostalCode = _account.getBillingAddress().getPostalCode();

  // set the default billing address.
  // this used to be the billing address for the
  // account, but that was not useful for DHHS.
  // durval 1/25/2006
  setRequestedBillToAddress(AddressData.createValue());

    }
    public AccountData getAccount() {
        return _account;
    }
    /*
      public void set.(String pValue) {_ = pValue;}
      public String get.() {return _;}

    */
    public void setComments(String pValue) {
        _comments = pValue;
    }
    public String getComments() {
        return _comments;
    }

    public void setPoNumber(String pValue) {
        _poNumber = pValue;
    }
    public String getPoNumber() {
        return _poNumber;
    }

    public void setOtherPaymentInfo(String pValue) {
        _otherPaymentInfo = pValue;
    }
    public String getOtherPaymentInfo() {
        return _otherPaymentInfo;
    }


    public void setProcessOrderDate(String pValue) {
        _processOrderDate = pValue;
    }
    public String getProcessOrderDate() {
        return _processOrderDate;
    }

    public void setCartAmtString(String pValue) {
        _cartAmt = new BigDecimal(I18nUtil.getDecimalStr(pValue));
    }
    public String getCartAmtString() {
        return ""+_cartAmt;
    }
    public void setCartAmt(BigDecimal pValue) {
        _cartAmt = pValue;
    }
    public BigDecimal getCartAmt(HttpServletRequest request) {
    	_cartAmt = getCartItemsAmt(request);
      //  _cartAmt = getCartItemsAmt();
        return _cartAmt;
    }

    /**@deprecated*/
    public BigDecimal getCartAmt() {
    	//**This should never be called*/
    	_cartAmt = getCartItemsAmt(null);
      //  _cartAmt = getCartItemsAmt();
        return _cartAmt;
    }





    public void setFreightAmtString(String pValue) {
        try
      {
    _freightAmt = new BigDecimal(I18nUtil.getDecimalStr(pValue));
      }
        catch (Exception e)
      {
    // In case there were any parsing errors.
    _freightAmt = new BigDecimal(0);
      }
    }
    public String getFreightAmtString() {
        return ""+_freightAmt;
    }
    public void setFreightAmt(BigDecimal pValue) {
        _freightAmt = pValue;
    }
    public BigDecimal getFreightAmt() {
        if (null == _freightAmt)
      {
    _freightAmt = new BigDecimal(0);
      }
        return _freightAmt;
    }


    public void setHandlingAmtString(String pValue) {
        try
      {
    _handlingAmt = new BigDecimal(I18nUtil.getDecimalStr(pValue));
      }
        catch (Exception e)
      {
    // In case there were any parsing errors.
    _handlingAmt = new BigDecimal(0);
      }
    }
    public String getHandlingAmtString() {
        return ""+_handlingAmt;
    }
    public void setHandlingAmt(BigDecimal pValue) {
        _handlingAmt = pValue;
    }
    public BigDecimal getHandlingAmt() {
        if (null == _handlingAmt)
      {
    _handlingAmt = new BigDecimal(0);
      }
        return _handlingAmt;
    }

    public void setServiceFeeAmtString(String pValue) {
        _serviceFeeAmt = new BigDecimal(I18nUtil.getDecimalStr(pValue));
    }
    public String getServiceFeeAmtString() {
        return ""+_serviceFeeAmt;
    }
    public void setServiceFeeAmt(BigDecimal pValue) {
        _serviceFeeAmt = pValue;
    }
    public BigDecimal getServiceFeeAmt() {
        return _serviceFeeAmt;
    }

    public void setServiceFeeDetail(HashMap pValue) {
        serviceFeeDetailObj = pValue;
    }
    public HashMap getServiceFeeDetail() {
        return serviceFeeDetailObj;
    }

    public void setMiscAmtString(String pValue) {
        _miscAmt = new BigDecimal(I18nUtil.getDecimalStr(pValue));
    }
    public String getMiscAmtString() {
        return ""+_miscAmt;
    }
    public void setMiscAmt(BigDecimal pValue) {
        _miscAmt = pValue;
    }
    public BigDecimal getMiscAmt() {
        return _miscAmt;
    }

    public void setCostCenterId(int pValue) {
        _costCenterId = pValue;
    }
    public int getCostCenterId() {
        return _costCenterId;
    }

    public void setCostCenterName(String pValue) {
        _costCenterName = pValue;
    }
    public String getCostCenterName() {
        return _costCenterName;
    }

    public void setCcType(String pValue) {
        _ccType = pValue;
    }
    public String getCcType() {
        return _ccType;
    }

    public void setCcNumber(String pValue) {
        _ccNumber = pValue;
    }
    public String getCcNumber() {
        return _ccNumber;
    }
    public String getCcNumberForDisplay(){
        if(_ccNumber == null){
            return null;
        }
        if(_ccNumber.length() > 4){
            return _ccNumber.substring(_ccNumber.length()-4,_ccNumber.length());
        }else{
            return null;
        }
    }

    public void setCcExpMonth(int pValue) {
        _ccExpMonth = pValue;
    }
    public int getCcExpMonth() {
        return _ccExpMonth;
    }

    public void setCcExpYear(int pValue) {
        _ccExpYear = pValue;
    }
    public int getCcExpYear() {
        return _ccExpYear;
    }

    public void setCcPersonName(String pValue) {
        _ccPersonName = pValue;
    }
    public String getCcPersonName() {
        return _ccPersonName;
    }

    public void setCcStreet1(String pValue) {
        _ccStreet1 = pValue;
    }
    public String getCcStreet1() {
        return _ccStreet1;
    }

    public void setCcStreet2(String pValue) {
        _ccStreet2 = pValue;
    }
    public String getCcStreet2() {
        return _ccStreet2;
    }

    public void setCcCity(String pValue) {
        _ccCity = pValue;
    }
    public String getCcCity() {
        return _ccCity;
    }

    public void setCcState(String pValue) {
        _ccState = pValue;
    }
    public String getCcState() {
        return _ccState;
    }

    public void setCcZipCode(String pValue) {
        _ccZipCode = pValue;
    }
    public String getCcZipCode() {
        return _ccZipCode;
    }


    public void setItems(ShoppingCartItemDataVector pValue) {
        _items = pValue;
    }
    public ShoppingCartItemDataVector getItems() {
        return _items;
    }

    public void setServices(ShoppingCartServiceDataVector pValue) {
        _services = pValue;
    }
    public ShoppingCartServiceDataVector getServices() {
        return _services;
    }

    public int getSortBy() {
        return _sortBy;
    }
    public void setSortBy(int pValue) {
        _sortBy = pValue;
    }

    public int getSortServiceBy() {
        return _sortServiceBy;
    }

    public void setSortServiceBy(int pValue) {
        _sortServiceBy = pValue;
    }

    public void setItemIdQtyElement(int pIndex, String pValue){
        _itemIdQty.add(pValue);
    }
    public List getItemIdQty() {
        return _itemIdQty;
    }
    public void setItemIdQty(List pValue) {
        _itemIdQty = pValue;
    }

    public ShoppingCartItemData getItemElement(int pIndex) {

        if(pIndex<_items.size() && pIndex>=0)	    {
      return (ShoppingCartItemData) _items.get(pIndex);
  }
  return null;
    }

    public int getItemsSize() {
        if(_items==null) {
            return 0;
  }
        return _items.size();
    }

    public int getServicesSize() {
        if(_services==null) {
            return 0;
  }
        return _services.size();
    }

    public boolean isCategoryChanged(int ind) {
        if(ind>=_items.size())
            return false;
        if(ind==0)
            return true;
        ShoppingCartItemData item1 = (ShoppingCartItemData) _items.get(ind-1);
        ShoppingCartItemData item2 = (ShoppingCartItemData) _items.get(ind);
        if(!item1.getCategoryName().equals(item2.getCategoryName()))
            return true;
        return false;
    }

    public void setCostCenters(CostCenterDataVector pValue) {
        _costCenters = pValue;
    }

    public CostCenterDataVector getCostCenters(){
        CostCenterDataVector tmpCostCenters = new CostCenterDataVector();

        for( int i = 0 ; i < _costCenters.size(); i++ )    {
      CostCenterData tmpCostCenterData = (CostCenterData)_costCenters.get(i);
      String costCenterStatus = tmpCostCenterData.getCostCenterStatusCd();
      if( !costCenterStatus.equals("INACTIVE") )  {
    tmpCostCenters.add(tmpCostCenterData);
      }
  }
        return tmpCostCenters;
    }

    //
    public void setSiteAddressId(int pValue) {
        _siteAddressId = pValue;
    }
    public int getSiteAddressId() {
        return _siteAddressId;
    }

    public void setSiteName1(String pValue) {
        _siteName1 = pValue;
    }
    public String getSiteName1() {
        return _siteName1;
    }

    public void setSiteName2(String pValue) {
        _siteName2 = pValue;
    }
    public String getSiteName2() {
        return _siteName2;
    }

    public void setSiteShortDesc(String pValue) {
        _siteShortDesc = pValue;
    }
    public String getSiteShortDesc() {
        return _siteShortDesc;
    }

    public void setSiteAddress1(String pValue) {
        _siteAddress1 = pValue;
    }
    public String getSiteAddress1() {
        return _siteAddress1;
    }

    public void setSiteAddress2(String pValue) {
        _siteAddress2 = pValue;
    }
    public String getSiteAddress2() {
        return _siteAddress2;
    }

    public void setSiteAddress3(String pValue) {
        _siteAddress3 = pValue;
    }
    public String getSiteAddress3() {
        return _siteAddress3;
    }

    public void setSiteAddress4(String pValue) {
        _siteAddress4 = pValue;
    }
    public String getSiteAddress4() {
        return _siteAddress4;
    }

    public void setSiteCity(String pValue) {
        _siteCity = pValue;
    }
    public String getSiteCity() {
        return _siteCity;
    }

    public void setSiteStateProvinceCd(String pValue) {
        _siteStateProvinceCd = pValue;
    }
    public String getSiteStateProvinceCd() {
        return _siteStateProvinceCd;
    }

    public void setSiteCountryCd(String pValue) {
        _siteCountryCd = pValue;
    }
    public String getSiteCountryCd() {
        return _siteCountryCd;
    }

    public void setSitePostalCode(String pValue) {
        _sitePostalCode = pValue;
    }
    public String getSitePostalCode() {
        return _sitePostalCode;
    }

    public void setAccountNumber(String pValue) {
        _accountNumber = pValue;
    }
    public String getAccountNumber() {
        return _accountNumber;
    }

    public void setAccountShortDesc(String pValue) {
        _accountShortDesc = pValue;
    }
    public String getAccountShortDesc() {
        return _accountShortDesc;
    }

    public void setAccountAddressId(int pValue) {
        _accountAddressId = pValue;
    }
    public int getAccountAddressId() {
        return _accountAddressId;
    }

    public void setAccountAddress1(String pValue) {
        _accountAddress1 = pValue;
    }
    public String getAccountAddress1() {
        return _accountAddress1;
    }

    public void setAccountAddress2(String pValue) {
        _accountAddress2 = pValue;
    }
    public String getAccountAddress2() {
        return _accountAddress2;
    }

    public void setAccountAddress3(String pValue) {
        _accountAddress3 = pValue;
    }
    public String getAccountAddress3() {
        return _accountAddress3;
    }

    public void setAccountAddress4(String pValue) {
        _accountAddress4 = pValue;
    }
    public String getAccountAddress4() {
        return _accountAddress4;
    }

    public void setAccountCity(String pValue) {
        _accountCity = pValue;
    }
    public String getAccountCity() {
        return _accountCity;
    }

    public void setAccountStateProvinceCd(String pValue) {
        _accountStateProvinceCd = pValue;
    }
    public String getAccountStateProvinceCd() {
        return _accountStateProvinceCd;
    }

    public void setAccountCountryCd(String pValue) {
        _accountCountryCd = pValue;
    }
    public String getAccountCountryCd() {
        return _accountCountryCd;
    }

    public void setAccountPostalCode(String pValue) {
        _accountPostalCode = pValue;
    }
    public String getAccountPostalCode() {
        return _accountPostalCode;
    }

    public void setReplacedOrders(ReplacedOrderViewVector pValue) {
        _replacedOrders = pValue;
    }
    public ReplacedOrderViewVector getReplacedOrders() {
        return _replacedOrders;
    }

    public void setPendingConsolidation(boolean pValue) {
        _pendingConsolidation = pValue;
    }
    public boolean getPendingConsolidation() {
        return _pendingConsolidation;
    }
    // ------------------------------------------------------------ Public Methods


    /**
     * Reset all properties to their default values.
     *
     * @param mapping The mapping used to select this instance
     * @param request The servlet request we are processing
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        reset();
    }

    /**
     * Reset all properties to their default values.
     *
     * @param mapping The mapping used to select this instance
     * @param request The servlet request we are processing
     */
    public void reset(ActionMapping mapping, ServletRequest request){
        reset();
    }

    /**
     * Reset all properties to their default values.
     */
    private void reset(){

         if(_reSaleSelectBox != null){
             for(int i=0;i<_reSaleSelectBox.length;i++){
                 _reSaleSelectBox[i] = "";
             }
         }
        _orderServiceSelectBox=new String[0];
        bypassCustomerWorkflow = false;
        _pendingConsolidation = false;
        _bypassOrderRouting = false;
        bypassBudget = false;
        _rebillOrder = "false";
        setBudgetYearPeriod(null);
    }

    /**
     * Validate the properties that have been set from this HTTP request,
     * and return an <code>ActionErrors</code> object that encapsulates any
     * validation errors that have been found.  If no errors are found, return
     * <code>null</code> or an <code>ActionErrors</code> object with no
     * recorded error messages.
     */


    /**
     * Get the value of orderContactName.
     * @return value of orderContactName.
     */
    public String getOrderContactName() {
        return orderContactName;
    }

    /**
     * Set the value of orderContactName.
     * @param v  Value to assign to orderContactName.
     */
    public void setOrderContactName(String  v) {
        this.orderContactName = v;
    }


    /**
     * Get the value of orderContactPhoneNum.
     * @return value of orderContactPhoneNum.
     */
    public String getOrderContactPhoneNum() {
        return orderContactPhoneNum;
    }

    /**
     * Set the value of orderContactPhoneNum.
     * @param v  Value to assign to orderContactPhoneNum.
     */
    public void setOrderContactPhoneNum(String  v) {
        this.orderContactPhoneNum = v;
    }


    /**
     * Get the value of orderContactEmail.
     * @return value of orderContactEmail.
     */
    public String getOrderContactEmail() {
        return orderContactEmail;
    }

    /**
     * Set the value of orderContactEmail.
     * @param v  Value to assign to orderContactEmail.
     */
    public void setOrderContactEmail(String  v) {
        this.orderContactEmail = v;
    }


    /**
     * Get the value of orderOriginationMethod.
     * @return value of orderOriginationMethod.
     */
    public String getOrderOriginationMethod() {
        return orderOriginationMethod;
    }

    /**
     * Set the value of orderOriginationMethod.
     * @param v  Value to assign to orderOriginationMethod.
     */
    public void setOrderOriginationMethod(String  v) {
        this.orderOriginationMethod = v;
    }

    /*
     * @param mapping The mapping used to select this instance
     * @param request The servlet request we are processing
     */
    public ActionErrors validate(ActionMapping mapping,
                                 HttpServletRequest request) {

        ActionErrors errors = new ActionErrors();
        return errors;
    }

    /** Getter for property orderNote.
     * @return Value of property orderNote.
     *
     */
    public String getOrderNote() {
        return this.orderNote;
    }

    /** Setter for property orderNote.
     * @param orderNote New value of property orderNote.
     *
     */
    public void setOrderNote(String orderNote) {
        this.orderNote = orderNote;
    }

    /** Getter for property billingOrder.
     * @return Value of property billingOrder.
     *
     */
    public boolean isBillingOrder() {
        return this.billingOrder;
    }

    /** Setter for property billingOrder.
     * @param billingOrder New value of property billingOrder.
     *
     * @throws PropertyVetoException
     *
     */
    public void setBillingOrder(boolean billingOrder)
  throws java.beans.PropertyVetoException {
        this.billingOrder = billingOrder;
    }


    public boolean isBypassOrderRouting() {
  return this._bypassOrderRouting;
    }

    public void setBypassOrderRouting(boolean v) {
  this._bypassOrderRouting = v;
    }

    /** Getter for property billingOnlyDistributorInvoice.
     * @return Value of property billingOnlyDistributorInvoice.
     *
     */
    public String getBillingDistributorInvoice() {
        return this.billingDistributorInvoice;
    }

    /** Setter for property billingOnlyDistributorInvoice.
     * @param billingOnlyDistributorInvoice New value of property billingOnlyDistributorInvoice.
     *
     */
    public void setBillingDistributorInvoice(String billingDistributorInvoice) {
        this.billingDistributorInvoice = billingDistributorInvoice;
    }

    /** Getter for property billingOnlyOriginalPurchaseOrder.
     * @return Value of property billingOnlyOriginalPurchaseOrder.
     *
     */
    public String getBillingOriginalPurchaseOrder() {
        return this.billingOriginalPurchaseOrder;
    }

    /** Setter for property billingOnlyOriginalPurchaseOrder.
     * @param billingOnlyOriginalPurchaseOrder New value of property billingOnlyOriginalPurchaseOrder.
     *
     */
    public void setBillingOriginalPurchaseOrder(String billingOriginalPurchaseOrder) {
        this.billingOriginalPurchaseOrder = billingOriginalPurchaseOrder;
    }

    /** Getter for property customerRequestedReshipOrderNum.
     * @return Value of property customerRequestedReshipOrderNum.
     *
     */
    public String getCustomerRequestedReshipOrderNum() {
        return this.customerRequestedReshipOrderNum;
    }

    /** Setter for property customerRequestedReshipOrderNum.
     * @param customerRequestedReshipOrderNum New value of property customerRequestedReshipOrderNum.
     *
     */
    public void setCustomerRequestedReshipOrderNum(String customerRequestedReshipOrderNum) {
        this.customerRequestedReshipOrderNum = customerRequestedReshipOrderNum;
    }

    public void setOrderSelectBox(long[] pValue) {_orderSelectBox = pValue;}
    public long[] getOrderSelectBox() {return _orderSelectBox;}


    public void setOrderServiceSelectBox(String[] pValue) {_orderServiceSelectBox = pValue;}
    public String[] getOrderServiceSelectBox() {return _orderServiceSelectBox;}


    public void setReSaleSelectBox(String[] pValue) {
  _reSaleSelectBox = pValue;}

    public String[] getReSaleSelectBox() {

  if ( _account.isAuthorizedForResale() ) {
      return _reSaleSelectBox;
  }

  // If the account is not authorized for resale, send back
  // empty check boxes.
  int nitems = 0;
        if(null != _items ) nitems = _items.size();
  _reSaleSelectBox = new String[nitems];
  return _reSaleSelectBox;
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
     * Getter for property customerSystemId.
     * @return Value of property customerSystemId.
     */
    public String getCustomerSystemId() {
        return this.customerSystemId;
    }

    /**
     * Setter for property customerSystemId.
     * @param customerSystemId New value of property customerSystemId.
     */
    public void setCustomerSystemId(String customerSystemId) {
        this.customerSystemId = customerSystemId;
    }

    /**
     * Getter for property inlinePostData.
     * @return Value of property inlinePostData.
     */
    public String getInlinePostData() {
        return this.inlinePostData;
    }

    /**
     * Setter for property inlinePostData.
     * @param inlinePostData New value of property inlinePostData.
     */
    public void setInlinePostData(String inlinePostData) {
        this.inlinePostData = inlinePostData;
    }

    /**
     * Getter for property inlinePostName.
     * @return Value of property inlinePostName.
     */
    public String getInlinePostFieldName() {
        return this.inlinePostFieldName;
    }

    /**
     * Setter for property inlinePostName.
     * @param inlinePostName New value of property inlinePostName.
     */
    public void setInlinePostFieldName(String inlinePostFieldName) {
        this.inlinePostFieldName = inlinePostFieldName;
    }

    /**
     * Getter for property bypassCustomerWorkflow.
     * @return Value of property bypassCustomerWorkflow.
     */
    public boolean isBypassCustomerWorkflow() {
        return this.bypassCustomerWorkflow;
    }

    /**
     * Setter for property bypassCustomerWorkflow.
     * @param bypassCustomerWorkflow New value of property bypassCustomerWorkflow.
     */
    public void setBypassCustomerWorkflow(boolean bypassCustomerWorkflow) {
        this.bypassCustomerWorkflow = bypassCustomerWorkflow;
    }

    /**
     * Getter for property orderRequest.
     * @return Value of property orderRequest.
     */
    public CustomerOrderRequestData getOrderRequest() {
        return _orderRequest;
    }

    /**
     * Setter for property orderRequest.
     * @param bypassCustomerWorkflow New value of property orderRequest.
     */
    public void setOrderRequest(CustomerOrderRequestData val) {
        _orderRequest = val;
    }

    private AddressData _reqBillToAddress,
  _confirmBillToAddress;

    public AddressData getRequestedBillToAddress() {
        if ( null == _reqBillToAddress ) {
            _reqBillToAddress = AddressData.createValue();
      _reqBillToAddress.setCountryCd
    (RefCodeNames.ADDRESS_COUNTRY_CD.UNITED_STATES);
        }
        return _reqBillToAddress;
    }

    public void setRequestedBillToAddress( AddressData  v ) {
  _reqBillToAddress = v;
    }

    public AddressData getConfirmBillToAddress() {
        return _confirmBillToAddress;
    }

    public void setConfirmBillToAddress( AddressData  v ) {
  _confirmBillToAddress = v;
    }

    /**
     * Holds value of property bypassBudget.
     */
    private boolean bypassBudget;

    public boolean hasRequestedBillToAddress() {
        if ( null == _reqBillToAddress ) {
      return false;
        }

  if ( getRequestBillToCheckMessage() != null ) {
      return false;
  }

  return true;
    }

    public String getRequestBillToCheckMessage() {

  return ShopTool.doBasicAddressCheck( _reqBillToAddress, _isStateProvinceRequired);

    }

    /**
     * Getter for property bypassBudget.
     * @return Value of property bypassBudget.
     */
    public boolean isBypassBudget() {

        return this.bypassBudget;
    }
    /**
     * Setter for property bypassBudget.
     * @param bypassBudget New value of property bypassBudget.
     */
    public void setBypassBudget(boolean bypassBudget) {

        this.bypassBudget = bypassBudget;
    }

    private String mRushOrderCharge;
    public void setRushChargeAmtString(String v) {
        mRushOrderCharge = v;
    }
    public String getRushChargeAmtString() {
        if ( null == mRushOrderCharge ) mRushOrderCharge = "";
        return mRushOrderCharge;
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
        if (!isFreightByContract())
        	setFreightAmt(mCartDistributors.getTotalFreightCost());
    }

    public ShoppingCartDistData getCartDistributors(int i) {
    	ShoppingCartDistData distD = (ShoppingCartDistData) mCartDistributors.get(i);
        return distD;
    }

    private int mStoreId;
    public void setStoreId(int v) {
        mStoreId = v;
    }
    public int getStoreId() {
        return mStoreId;
    }

    private String[] mDistFreightVendor;
    public void setDistFreightVendor(String[] values) {
        mDistFreightVendor = values;
        if(mCartDistributors!=null) {
            mCartDistributors.setDistFreightVendor(values);
        }
    }
    public String[] getDistFreightVendor() {
        return mDistFreightVendor;
    }

    private String mEstimateFreightString = new String("");
    public void setEstimateFreightString(String value) {
        mEstimateFreightString = value;
    }
    public String getEstimateFreightString() {

        mEstimateFreightString = "";
        try{
        if (mCartDistributors!= null && mCartDistributors.hasEstimatedFreight()) {
            mEstimateFreightString = "+estimate";
        }
        }catch(Exception e){
            e.printStackTrace();
        }
        return mEstimateFreightString;
    }

    public boolean getEstimateFreightFlag() {
        boolean flag = false;
        try{
        if (mCartDistributors!= null && mCartDistributors.hasEstimatedFreight()) {
            flag = true;
        }
        }catch(Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    private BigDecimal mDistFreightAmt;
    public void setDistFreightAmt(BigDecimal value) {
        mDistFreightAmt = value;
    }
    public BigDecimal getDistFreightAmt() {
        mDistFreightAmt = mCartDistributors.getTotalFreightCost();
        return mDistFreightAmt;
    }

    /**
     * Holds value of property salesTax.
     */
    private BigDecimal salesTax;

    /**
     * Getter for property salesTax.
     * @return Value of property salesTax.
     */
    public BigDecimal getSalesTax() {
        if(this.salesTax == null){
            this.salesTax = new BigDecimal(0.00);
        }
        return this.salesTax;
    }

    /**
     * Setter for property salesTax.
     * @param salesTax New value of property salesTax.
     */
    public void setSalesTax(BigDecimal salesTax) {
        this.salesTax = salesTax;
    }

    // Checkout options.
    private HashMap checkoutOptionsMap = new HashMap();

    public Iterator getCheckoutOptionsMapKeys() {
  return checkoutOptionsMap.keySet().iterator();
    }

    public Object getCheckoutOption(String key) {
        return checkoutOptionsMap.get(key);
    }

    public void setCheckoutOption(String key, Object value) {
        checkoutOptionsMap.put(key, value);
    }


    private int mCheckoutBillToId = 0;
    public int getCheckoutBillToId() {
  return mCheckoutBillToId;
    }
    public void setCheckoutBillToId(int pV) {
  mCheckoutBillToId = pV;
    }

    public void setOrderServiceFlag(boolean orderServiceFlag) {
        this._orderServiceFlag = orderServiceFlag;
    }

    public boolean getOrderServiceFlag() {
        return _orderServiceFlag;
    }

    public void setCartServiceAmt(BigDecimal cartServiceAmt) {
        this._cartServiceAmt = cartServiceAmt;
    }

    public BigDecimal getCartServiceAmt() {
       return  this._cartServiceAmt;
    }

    public void setEarlyReleaseFlag(boolean earlyReleaseFlag) {
        this._earlyReleaseFlag = earlyReleaseFlag;
    }

    public boolean getEarlyReleaseFlag() {
        return _earlyReleaseFlag;
    }

    boolean allowPONumberByVendor = false;
    public void setAllowPONumberByVendor(boolean allowPONumberByVendor) {
        this.allowPONumberByVendor = allowPONumberByVendor;
    }

    public boolean isAllowPONumberByVendor() {
        return allowPONumberByVendor;
    }

    boolean freightByContract = false;
    public void setFreightByContract(boolean freightByContract) {
        this.freightByContract = freightByContract;
    }

    public boolean isFreightByContract(){
    	return freightByContract;
    }

    private List warningMessages = null;
  private String deliveryDate;
  private SiteDeliveryDataVector deliveryDataVector;
  private String confirmMessage;
  public void setWarningMessages(List warningMessages) {
		this.warningMessages = warningMessages;
	}

  public void setDeliveryDate(String deliveryDate) {

    this.deliveryDate = deliveryDate;
  }

  public void setDeliveryDataVector(SiteDeliveryDataVector deliveryDataVector) {
    this.deliveryDataVector = deliveryDataVector;
  }

  public String deliveryDateList;
 
/**
 * @return the deliveryDateList
 */
public final String getDeliveryDateList() {
	return deliveryDateList;
}
/**
 * @param deliveryDateList the deliveryDateList to set
 */
public final void setDeliveryDateList(String deliveryDateList) {
	this.deliveryDateList = deliveryDateList;
}

public void setConfirmMessage(String confirmMessage) {
    this.confirmMessage = confirmMessage;
  }

  public List getWarningMessages() {
		return warningMessages;
	}

  public String getDeliveryDate() {

    return deliveryDate;
  }

  public SiteDeliveryDataVector getDeliveryDataVector() {
    return deliveryDataVector;
  }

  public String getConfirmMessage() {
    return confirmMessage;
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

    // Added by SVC : Begin
    public void setDiscountAmtString(String pValue) {
      try
      {
    _discountAmt = new BigDecimal(I18nUtil.getDecimalStr(pValue));
      }
        catch (Exception e)
      {
         // In case there were any parsing errors.
         _discountAmt = new BigDecimal(0);
      }
    }
    public String getDiscountAmtString() {
        return ""+_discountAmt;
    }
    public void setDiscountAmt(BigDecimal pValue) {
        _discountAmt = pValue;
    }
    public BigDecimal getDiscountAmt() {
      if (null == _discountAmt)
      {
        _discountAmt = new BigDecimal(0);
      }
        return _discountAmt;
    }
    public void addDiscountAmt(BigDecimal pValue) {
        if (null == _discountAmt)
        {
          _discountAmt = new BigDecimal(0);
        }
    	_discountAmt = _discountAmt.add(pValue);
    }
    public void setDiscountAmtPerDist(HashMap<Integer,BigDecimal> pValue) {
    	_discountAmtPerDist = pValue;
    }
    public HashMap<Integer,BigDecimal> getDiscountAmtPerDist() {
    	return _discountAmtPerDist;
    }

    public String getWorkOrderNumber() {
        return _workOrderNumber;
    }

    public void setWorkOrderNumber(String workOrderNumber) {
        this._workOrderNumber = workOrderNumber;
    }
    // Added by SVC : End

    public List<BusEntityFieldDataElement> getCheckoutFieldProperties() {
        return mCheckoutFieldProperties;
    }

    public void setCheckoutFieldProperties(List<BusEntityFieldDataElement> checkoutFields) {
        mCheckoutFieldProperties = checkoutFields;
    }

    public String getRebillOrder() {
        return _rebillOrder;
    }

    public void setRebillOrder(String rebillOrder) {
        _rebillOrder = rebillOrder;
    }

    /// 'UpdaterAfterStoreChange' interface
    public void updateDataAfterStoreChange() {
        _rebillOrder = "";
    }

    public String getXiPayCCToken() {
        return _xiPayCCToken;
    }

    public void setXiPayCCToken(String xiPayCCToken) {
        _xiPayCCToken = xiPayCCToken;
    }

    public int getXiPayCCType() {
        return _xiPayCCType;
    }

    public void setXiPayCCType(int xiPayCCType) {
        _xiPayCCType = xiPayCCType;
    }

    public int getXiPayBinRangeType() {
        return _xiPayBinRangeType;
    }

    public void setXiPayBinRangeType(int xiPayBinRangeType) {
        _xiPayBinRangeType = xiPayBinRangeType;
    }

    public boolean getAllowAlternateShipTo() {
        return _allowAlternateShipTo;
    }

    public void setAllowAlternateShipTo(boolean allowAlternateShipTo) {
        this._allowAlternateShipTo = allowAlternateShipTo;
    }

    public String getAlternateAddress1() {
        return _alternateAddress1;
    }

    public void setAlternateAddress1(String alternateAddress1) {
        this._alternateAddress1 = alternateAddress1;
    }

    public String getAlternateAddress2() {
        return _alternateAddress2;
    }

    public void setAlternateAddress2(String alternateAddress2) {
        this._alternateAddress2 = alternateAddress2;
    }

    public String getAlternateAddress3() {
        return _alternateAddress3;
    }

    public void setAlternateAddress3(String alternateAddress3) {
        this._alternateAddress3 = alternateAddress3;
    }

    public String getAlternateCity() {
        return _alternateCity;
    }

    public void setAlternateCity(String alternateCity) {
        this._alternateCity = alternateCity;
    }

    public String getAlternateCountry() {
        return _alternateCountry;
    }

    public void setAlternateCountry(String alternateCountry) {
        this._alternateCountry = alternateCountry;
    }

    public String getAlternatePostalCode() {
        return _alternatePostalCode;
    }

    public void setAlternatePostalCode(String alternatePostalCode) {
        this._alternatePostalCode = alternatePostalCode;
    }

    public String getAlternateStateProvince() {
        return _alternateStateProvince;
    }

    public void setAlternateStateProvince(String alternateStateProvince) {
        this._alternateStateProvince = alternateStateProvince;
    }
    
    public String getAlternatePhoneNum() {
        return _alternatePhoneNum;
    }

    public void setAlternatePhoneNum(String alternatePhoneNum) {
        this._alternatePhoneNum = alternatePhoneNum;
    }
    
	public void setBudgetYearPeriod(String _budgetYearPeriod) {
		this._budgetYearPeriod = _budgetYearPeriod;
		if (_budgetPeriodChoices != null && Utility.isSet(_budgetYearPeriod)){
			for (LabelValueBean lvBean : _budgetPeriodChoices){
				if (_budgetYearPeriod.equals(lvBean.getValue())){
					setBudgetYearPeriodLabel(lvBean.getLabel());
					break;
				}
			}
		}
	}
	public String getBudgetYearPeriod() {
		return _budgetYearPeriod;
	}
	public void setBudgetPeriodChoices(List<LabelValueBean> _budgetPeriodChoices) {
		this._budgetPeriodChoices = _budgetPeriodChoices;
	}

	public List<LabelValueBean> getBudgetPeriodChoices() {
		return _budgetPeriodChoices;
	}
	public void setBudgetYearPeriodLabel(String _budgetYearPeriodLabel) {
		this._budgetYearPeriodLabel = _budgetYearPeriodLabel;
	}
	public String getBudgetYearPeriodLabel() {
		return _budgetYearPeriodLabel;
	}

   public String getOrderServiceTicketNumbers() {
        return _orderServiceTicketNumbers;
    }

    public void setOrderServiceTicketNumbers(String pOrderServiceTicketNumbers) {
        _orderServiceTicketNumbers = pOrderServiceTicketNumbers;
    }
}
