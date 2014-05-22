/**
 * Title:        InvoiceOpDetail
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
import com.cleanwise.service.api.util.RefCodeNames;
import javax.servlet.http.HttpSession;


/**
 * Form bean for the view OrderStatus page.  This form has the following fields,
 * with default values in square brackets:
 * <ul>
 * <li><b>orderStatusDetail</b> - A OrderData object
 * </ul>
 */
public final class InvoiceOpDetailForm extends ActionForm {


  // -------------------------------------------------------- Instance Variables


    private OrderStatusDescData    _mOrderStatusDetail;
    private OrderPropertyData  _mOrderPropertyDetail;
    private OrderPropertyDataVector _mOrderPropertyList;
    private OrderItemDescDataVector  _mOrderItemDescList;
    private InvoiceDistDescDataVector  _mInvoiceDistDescList;
    private InvoiceDistData  _mInvoiceDist;
    private InvoiceDistDetailDataVector  _mInvoiceDistDetailList;
    
    
    private int _mOrderedNum;
    private int _mAcceptedNum;
    private int _mRejectedNum;
    private int _mShippedNum;
    private int _mBackorderedNum;
    private int _mCanceledNum;
    private int _mSubstitutedNum;
    private int _mInvoicedNum;
    private Date _mLastDate;

    private String _mTotalAmountS;
    private String _mSubTotalS;
    private String _mTotalFreightCostS;
    private String _mTotalTaxCostS;
    private String _mTotalMiscCostS;
    private String _mDiscountS;
    private String _mInvoiceSubTotalS;
    private String _mAdjustedTotalS;
    private String _mInvoiceTotalS;
    private String _mContractTotalS;
    private String _mContractSubTotalS;
    
    private String _mDistSku;
    private String _mInvoiceDateS;
    private String _mInvoiceDueDateS;
    private String _mShipFromId;
    private PurchaseOrderStatusDescDataView purchaseOrderStatusDesc;
    private DistributorData distributorData;
    private String batchNumS;
    private String voucher;
    private String remitTo;
    private AddressDataVector remitToAddresses;
    private String distErpNumCheck;
    private int orderRoutedWarningCount;
    private int minimumOrderWarningCount;
    private int frieghtOnBackorderedWarningCount;
    private int returnedNum;
    private int frieghtOnFreeTerritoryWarningCount;    
    private String _mErpSystemCd;
    /** Holds value of property newErpPoNum. */
    private String newErpPoNum;
    
    public void init(){
        _mOrderStatusDetail = OrderStatusDescData.createValue();
        _mOrderPropertyDetail = OrderPropertyData.createValue();
        _mOrderPropertyList = new OrderPropertyDataVector();
        _mOrderItemDescList = new OrderItemDescDataVector();
        _mInvoiceDistDescList = new InvoiceDistDescDataVector();
        _mInvoiceDist = InvoiceDistData.createValue();
        _mInvoiceDistDetailList = new InvoiceDistDetailDataVector();
        
        orderRoutedWarningCount = 0;
        minimumOrderWarningCount = 0;
        frieghtOnBackorderedWarningCount = 0;
        frieghtOnFreeTerritoryWarningCount = 0;
        
        _mOrderedNum = 0;
        _mAcceptedNum = 0;
        _mRejectedNum = 0;
        _mShippedNum = 0;
        _mBackorderedNum = 0;
        _mCanceledNum = 0;
        _mSubstitutedNum = 0;
        _mInvoicedNum = 0;
        _mLastDate = null;
        
        _mTotalAmountS = "";
        _mSubTotalS = "";
        _mTotalFreightCostS = "";
        _mTotalTaxCostS = "";
        _mTotalMiscCostS = "";
        _mDiscountS = "";
        _mInvoiceSubTotalS = "";
        _mAdjustedTotalS = "";
        _mInvoiceTotalS = "";
        _mContractTotalS = "";
        _mContractSubTotalS = "";
        
        _mDistSku = "";
        //cache the date as it makes entry easier
        _mInvoiceDueDateS = "";
        _mShipFromId = "";
        purchaseOrderStatusDesc = null;
        distributorData = null;
        batchNumS = "";
        voucher = null;
        remitTo = null;
        remitToAddresses = null;
        
        distErpNumCheck = "";
        _mErpSystemCd = "";
    }

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
     * <code>getInvoiceDistDescList</code> method.
     *
     * @return a <code>InvoiceDistDescDataVector</code> value
     */
    public InvoiceDistDescDataVector getInvoiceDistDescList() {
        if( null == this._mInvoiceDistDescList) {
            this._mInvoiceDistDescList = new InvoiceDistDescDataVector();
        }
        return (this._mInvoiceDistDescList);
    }

    /**
     * <code>setInvoiceDistDescList</code> method.
     *
     * @param pVal a <code>InvoiceDistDescDataVector</code> value
     */
    public void setInvoiceDistDescList(InvoiceDistDescDataVector pVal) {
        this._mInvoiceDistDescList = pVal;
    }


    /**
     * Return the InvoiceDist object
     */
    public InvoiceDistData getInvoiceDist() {
        return (this._mInvoiceDist);
    }

    /**
     * Set the InvoiceDist object
     */
    public void setInvoiceDist(InvoiceDistData detail) {
        this._mInvoiceDist = detail;
    }
	
	
    /**
     * <code>getInvoiceDistDetailList</code> method.
     *
     * @return a <code>InvoiceDistDetailDataVector</code> value
     */
    public InvoiceDistDetailDataVector getInvoiceDistDetailList() {
        if( null == this._mInvoiceDistDetailList) {
            this._mInvoiceDistDetailList = new InvoiceDistDetailDataVector();
        }
        return (this._mInvoiceDistDetailList);
    }

    /**
     * <code>setInvoiceDistDetailList</code> method.
     *
     * @param pVal a <code>InvoiceDistDetailDataVector</code> value
     */
    public void setInvoiceDistDetailList(InvoiceDistDetailDataVector pVal) {
        this._mInvoiceDistDetailList = pVal;
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
    public String getTotalAmountS() {
        return (this._mTotalAmountS);
    }

    /**
     * Set the TotalAmount of the order
     */
    public void setTotalAmountS(String pVal) {
        this._mTotalAmountS = pVal;
    }


    /**
     * Return the SubTotal of the order
     */
    public String getSubTotalS() {
        return (this._mSubTotalS);
    }

    /**
     * Set the SubTotal of the order
     */
    public void setSubTotalS(String pVal) {
        this._mSubTotalS = pVal;
    }

    
    /**
     * Return the TotalFreightCost of the order
     */
    public String getTotalFreightCostS() {
        return (this._mTotalFreightCostS);
    }

    /**
     * Set the TotalFreightCost of the order
     */
    public void setTotalFreightCostS(String pVal) {
        this._mTotalFreightCostS = pVal;
    }


    /**
     * Return the TotalTaxCost of the order
     */
    public String getTotalTaxCostS() {
        return (this._mTotalTaxCostS);
    }

    /**
     * Set the TotalTaxCost of the order
     */
    public void setTotalTaxCostS(String pVal) {
        this._mTotalTaxCostS = pVal;
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
     * Return the Discount of the order
     */
    public String getDiscountS() {
        return (this._mDiscountS);
    }

    /**
     * Set the Discount of the order
     */
    public void setDiscountS(String pVal) {
        this._mDiscountS = pVal;
    }
    
    
    /**
     * Return the InvoiceSubTotal of the order
     */
    public String getInvoiceSubTotalS() {
        return (this._mInvoiceSubTotalS);
    }

    /**
     * Set the InvoiceSubTotal of the order
     */
    public void setInvoiceSubTotalS(String pVal) {
        this._mInvoiceSubTotalS = pVal;
    }

    
    /**
     * Return the AdjustedTotal of the order
     */
    public String getAdjustedTotalS() {
        return (this._mAdjustedTotalS);
    }

    /**
     * Set the AdjustedTotal of the order
     */
    public void setAdjustedTotalS(String pVal) {
        this._mAdjustedTotalS = pVal;
    }

    
    /**
     * Return the InvoiceTotalS of the order
     */
    public String getInvoiceTotalS() {
        return (this._mInvoiceTotalS);
    }

    /**
     * Set the InvoiceTotalS of the order
     */
    public void setInvoiceTotalS(String pVal) {
        this._mInvoiceTotalS = pVal;
    }


    /**
     * Return the ContractTotalS of the order
     */
    public String getContractTotalS() {
        return (this._mContractTotalS);
    }

    /**
     * Set the ContractTotalS of the order
     */
    public void setContractTotalS(String pVal) {
        this._mContractTotalS = pVal;
    }


    /**
     * Return the ContractSubTotalS of the order
     */
    public String getContractSubTotalS() {
        return (this._mContractSubTotalS);
    }

    /**
     * Set the ContractSubTotalS of the order
     */
    public void setContractSubTotalS(String pVal) {
        this._mContractSubTotalS = pVal;
    }

    
    
    
    /**
     * Return the DistSku of the order
     */
    public String getDistSku() {
        return (this._mDistSku);
    }

    /**
     * Set the DistSku of the order
     */
    public void setDistSku(String pVal) {
        this._mDistSku = pVal;
    }

    /**
     * Return the InvoiceDateS of the order
     */
    public String getInvoiceDateS() {
        return (this._mInvoiceDateS);
    }

    /**
     * Set the InvoiceDateS of the order
     */
    public void setInvoiceDateS(String pVal) {
        this._mInvoiceDateS = pVal;
    }

    
    /**
     * Return the InvoiceDueDateS of the order
     */
    public String getInvoiceDueDateS() {
        return (this._mInvoiceDueDateS);
    }

    /**
     * Set the InvoiceDueDateS of the order
     */
    public void setInvoiceDueDateS(String pVal) {
        this._mInvoiceDueDateS = pVal;
    }
    
    /**
     * Return the ShipFromId of the order
     */
    public String getShipFromId() {
        return (this._mShipFromId);
    }

    /**
     * Set the ShipFromId of the order
     */
    public void setShipFromId(String pVal) {
        this._mShipFromId = pVal;
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

    /** Getter for property purchaseOrderStatusDesc.
     * @return Value of property purchaseOrderStatusDesc.
     *
     */
    public PurchaseOrderStatusDescDataView getPurchaseOrderStatusDesc() {
        return this.purchaseOrderStatusDesc;
    }    

    /** Setter for property purchaseOrderStatusDesc.
     * @param purchaseOrderStatusDesc New value of property purchaseOrderStatusDesc.
     *
     */
    public void setPurchaseOrderStatusDesc(PurchaseOrderStatusDescDataView purchaseOrderStatusDesc) {
        this.purchaseOrderStatusDesc = purchaseOrderStatusDesc;
    }
    
    /** Getter for property distributorData.
     * @return Value of property distributorData.
     *
     */
    public DistributorData getDistributorData() {
        return this.distributorData;
    }
    
    /** Setter for property distributorData.
     * @param distributorData New value of property distributorData.
     *
     */
    public void setDistributorData(DistributorData distributorData) {
        this.distributorData = distributorData;
    }
    
    /** Getter for property batchNumS.
     * @return Value of property batchNumS.
     *
     */
    public String getBatchNumS() {
        return this.batchNumS;
    }
    
    /** Setter for property batchNumS.
     * @param batchNumS New value of property batchNumS.
     *
     */
    public void setBatchNumS(String batchNumS) {
        this.batchNumS = batchNumS;
    }
    
    /** Getter for property voucher.
     * @return Value of property voucher.
     *
     */
    public String getVoucher() {
        return this.voucher;
    }
    
    /** Setter for property voucher.
     * @param voucher New value of property voucher.
     *
     */
    public void setVoucher(String voucher) {
        this.voucher = voucher;
    }
    
    /** Getter for property remitTo.
     * @return Value of property remitTo.
     *
     */
    public String getRemitTo() {
        return this.remitTo;
    }
    
    /** Setter for property remitTo.
     * @param remitTo New value of property remitTo.
     *
     */
    public void setRemitTo(String remitTo) {
        this.remitTo = remitTo;
    }
    
    /** Getter for property remitToAddresses.
     * @return Value of property remitToAddresses.
     *
     */
    public AddressDataVector getRemitToAddresses() {
        return this.remitToAddresses;
    }
    
    /** Setter for property remitToAddresses.
     * @param remitToAddresses New value of property remitToAddresses.
     *
     */
    public void setRemitToAddresses(AddressDataVector remitToAddresses) {
        this.remitToAddresses = remitToAddresses;
    }
    
    /** Getter for property distErpNumCheck.
     * @return Value of property distErpNumCheck.
     *
     */
    public String getDistErpNumCheck() {
        return this.distErpNumCheck;
    }
    
    /** Setter for property distErpNumCheck.
     * @param distErpNumCheck New value of property distErpNumCheck.
     *
     */
    public void setDistErpNumCheck(String distErpNumCheck) {
        this.distErpNumCheck = distErpNumCheck;
    }
    
    /** Getter for property warningCount.
     * @return Value of property warningCount.
     *
     */
    public int getOrderRoutedWarningCount() {
        return this.orderRoutedWarningCount;
    }
    
    /** Setter for property warningCount.
     * @param warningCount New value of property warningCount.
     *
     */
    public void setOrderRoutedWarningCount(int orderRoutedWarningCount) {
        this.orderRoutedWarningCount = orderRoutedWarningCount;
    }
    
    /** Getter for property minimumOrderWarningCount.
     * @return Value of property minimumOrderWarningCount.
     *
     */
    public int getMinimumOrderWarningCount() {
        return this.minimumOrderWarningCount;
    }
    
    /** Setter for property minimumOrderWarningCount.
     * @param minimumOrderWarningCount New value of property minimumOrderWarningCount.
     *
     */
    public void setMinimumOrderWarningCount(int minimumOrderWarningCount) {
        this.minimumOrderWarningCount = minimumOrderWarningCount;
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
    
    /** Getter for property frieghtOnBackorderedWarningCount.
     * @return Value of property frieghtOnBackorderedWarningCount.
     *
     */
    public int getFrieghtOnBackorderedWarningCount() {
        return this.frieghtOnBackorderedWarningCount;
    }
    
    /** Setter for property frieghtOnBackorderedWarningCount.
     * @param frieghtOnBackorderedWarningCount New value of property frieghtOnBackorderedWarningCount.
     *
     */
    public void setFrieghtOnBackorderedWarningCount(int frieghtOnBackorderedWarningCount) {
        this.frieghtOnBackorderedWarningCount = frieghtOnBackorderedWarningCount;
    }
    
    /** Getter for property frieghtOnFreeTerritoryWarningCount.
     * @return Value of property frieghtOnFreeTerritoryWarningCount.
     *
     */
    public int getFrieghtOnFreeTerritoryWarningCount() {
        return this.frieghtOnFreeTerritoryWarningCount;
    }
    
    /** Setter for property frieghtOnFreeTerritoryWarningCount.
     * @param frieghtOnFreeTerritoryWarningCount New value of property frieghtOnFreeTerritoryWarningCount.
     *
     */
    public void setFrieghtOnFreeTerritoryWarningCount(int frieghtOnFreeTerritoryWarningCount) {
        this.frieghtOnFreeTerritoryWarningCount = frieghtOnFreeTerritoryWarningCount;
    }
    
    /** Getter for property newErpPoNum.
     * @return Value of property newErpPoNum.
     *
     */
    public String getNewErpPoNum() {
        return this.newErpPoNum;
    }
    
    /** Setter for property newErpPoNum.
     * @param newErpPoNum New value of property newErpPoNum.
     *
     */
    public void setNewErpPoNum(String newErpPoNum) {
        this.newErpPoNum = newErpPoNum;
    }
    
    /** Getter for property erpSystemCd.
     * @return Value of property erpSystemCd.
     *
     */
    public String getErpSystemCd() {
        return this._mErpSystemCd;
    }
    
    /** Setter for property erpSystemCd.
     * @param newErpPoNum New value of property erpSystemCd.
     *
     */
    public void setErpSystemCd(String erpSystemCd) {
        this._mErpSystemCd = erpSystemCd;
    }
}
