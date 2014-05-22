/**
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 */

package com.cleanwise.view.forms;

import java.util.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;
import com.cleanwise.service.api.value.*;

/**
 * Form bean for the purchase order sub system
 *
 */
public class CustAcctMgtInvoiceSearchForm extends ActionForm {

    private List _invoiceStatusList = new ArrayList();
    private String _searchField = "";
    private String _searchType = "";
    private String _searchStatus = "";

    private List _resultList = new ArrayList();

    /**
     * Holds value of property invoiceDistNum.
     */
    private String _invoiceDistNum      = "";
    /**
     * Holds value of property invoiceDistDateRangeEnd.
     */
    private String _invoiceDistDateRangeEnd;

    /**
     * Holds value of property invoiceDistDateRangeBegin.
     */
    private String _invoiceDistDateRangeBegin;

    /**
     * Holds value of property outboundPoNum.
     */

    private String _outboundPoNum            = "";


      /** Holds value of property packagesList. */
    private PurchaseOrderStatusDescDataViewVector packagesList;

    /** Holds value of property toManifestList. */
   private PurchaseOrderStatusDescDataViewVector toManifestList;

   private String _invoiceDistAddDateRangeBegin;
   private String _invoiceDistAddDateRangeEnd;
   private String _erpPONum;
   private String _statusToSet = "";

  /**
     * <code>getSearchField</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getSearchField() {
        return (this._searchField);
    }

    /**
     * <code>setSearchField</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setSearchField(String pVal) {
        this._searchField = pVal;
    }

    /**
     * <code>getSearchType</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getSearchType() {
        return (this._searchType);
    }

    /**
     * <code>setSearchType</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setSearchType(String pVal) {
        this._searchType = pVal;
    }

     /**
     * <code>getInvoiceDistNum</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getInvoiceDistNum() {
        return (this._invoiceDistNum);
    }

    /**
     * <code>setInvoiceDistNum</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setInvoiceDistNum(String pVal) {
        this._invoiceDistNum = pVal;
    }


    /**
     * <code>getResultList</code> method.
     *
     * @return a <code>OrderStatusDescDataVector</code> value
     */
    public List getResultList() {
        return (this._resultList);
    }

    /**
     * <code>setResultList</code> method.
     *
     * @param pVal a <code>OrderStatusDescDataVector</code> value
     */
    public void setResultList(List pVal) {
        this._resultList = pVal;
    }

    /**
     * <code>getListCount</code> method.
     *
     * @return a <code>int</code> value
     */
    public int getListCount()
    {
        if (null == this._resultList) {
            this._resultList = new PurchaseOrderStatusDescDataViewVector();
        }
        return (this._resultList.size());
    }

    /**
     * <code>reset</code> method, set the search fiels to null.
     *
     * @param mapping an <code>ActionMapping</code> value
     * @param request a <code>HttpServletRequest</code> value
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
       // exceptionDistInvoiceOnly = false;
    }


    /**
     * <code>validate</code> method is a stub.
     *
     * @param mapping an <code>ActionMapping</code> value
     * @param request a <code>HttpServletRequest</code> value
     * @return an <code>ActionErrors</code> value
     */
    public ActionErrors validate(ActionMapping mapping,
                               HttpServletRequest request) {
        // No validation necessary.
        return null;
    }



    /**
     * Getter for property invoiceDistDateRangeEnd.
     * @return Value of property invoiceDistDateRangeEnd.
     */
    public String getInvoiceDistDateRangeEnd() {

        return this._invoiceDistDateRangeEnd;
    }

    /**
     * Setter for property invoiceDistDateRangeEnd.
     * @param invoiceDistDateRangeEnd New value of property invoiceDistDateRangeEnd.
     */
    public void setInvoiceDistDateRangeEnd(String invoiceDistDateRangeEnd) {

        this._invoiceDistDateRangeEnd = invoiceDistDateRangeEnd;
    }

    /**
     * Getter for property invoiceDistDateRangeBegin.
     * @return Value of property invoiceDistDateRangeBegin.
     */
    public String getInvoiceDistDateRangeBegin() {

        return this._invoiceDistDateRangeBegin;
    }

    /**
     * Setter for property invoiceDistDateRangeBegin.
     * @param invoiceDistDateRangeBegin New value of property invoiceDistDateRangeBegin.
     */
    public void setInvoiceDistDateRangeBegin(String invoiceDistDateRangeBegin) {

        this._invoiceDistDateRangeBegin = invoiceDistDateRangeBegin;
    }


    public String getOutboundPoNum() {
        return _outboundPoNum;
    }

    public void setOutboundPoNum(String outboundPoNum) {
        this._outboundPoNum = outboundPoNum;
    }

  public void setInvoiceDistAddDateRangeBegin(String
                                               invoiceDistAddDateRangeBegin) {
    this._invoiceDistAddDateRangeBegin = invoiceDistAddDateRangeBegin;
  }

  public void setInvoiceDistAddDateRangeEnd(String invoiceDistAddDateRangeEnd) {
    this._invoiceDistAddDateRangeEnd = invoiceDistAddDateRangeEnd;
  }

  public String getInvoiceDistAddDateRangeBegin() {
    return this._invoiceDistAddDateRangeBegin;
  }

  public String getInvoiceDistAddDateRangeEnd() {
    return this._invoiceDistAddDateRangeEnd;
  }

  public void setErpPONum(String erpPONum) {
    this._erpPONum = erpPONum;
  }

  public String getErpPONum() {
    return _erpPONum;
  }
  
  
  
  public void setInvoiceStatusList(List invoiceStatusList) {
    this._invoiceStatusList = invoiceStatusList;
  }

  public List getInvoiceStatusList() {
    return _invoiceStatusList;
  }

  public void setSearchStatus(String searchStatus) {
    this._searchStatus = searchStatus;
  }

  public String getSearchStatus() {
    return _searchStatus;
  }

  public void setStatusToSet(String statusToSet) {
    this._statusToSet = statusToSet;
  }

  public String getStatusToSet() {
    return _statusToSet;
  }
  
}
