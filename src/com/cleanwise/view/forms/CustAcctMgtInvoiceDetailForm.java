/*
 * StoreVendorInvoiceForm.java
 *
 * Created on July 6, 2005, 2:58 PM
 */

package com.cleanwise.view.forms;

import java.util.*;

import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.RefCodeNames;

/**
 *
 * @author nguschina
 */
public class CustAcctMgtInvoiceDetailForm extends BaseDetailForm{

  private InvoiceDistDetailDataVector invoiceDetail;
  private OrderPropertyDataVector orderNotes;

  /**
     * Holds value of property existingIvoices.
     */
    private InvoiceDistDataVector existingInvoices;

    private int nextInvoiceInList;
    private int prevInvoiceInList;
    private DistributorData distributor;
    private String distributionCenterNo;
    private String distributionCenterName;
    private String accountNumber;
    private String accountName;
    private Date invoiceDate;
    private String invoiceType;
    private String poNumber;
    private String taxAmount;
    private String freightCharges;
    private String invoiceNumber;

    private InvoiceNetworkServiceDataVector resultList;
    private InvoiceDistView invoiceView;
  private String sortField;

  /** Creates a new instance of StoreVendorInvoiceForm */
    public CustAcctMgtInvoiceDetailForm() {
    }


     /**
      * Getter for property distributor.
      * @return Value of property distributor.
      */
     public DistributorData getDistributor() {
         return this.distributor;
     }

     /**
      * Setter for property distributor.
      * @param distributor New value of property distributor.
      */
     public void setDistributor(DistributorData distributor) {

         this.distributor = distributor;
    }

    public int getNextInvoiceInList() {
            return nextInvoiceInList;
    }

    public void setNextInvoiceInList(int nextInvoiceInList) {
            this.nextInvoiceInList = nextInvoiceInList;
    }

    public int getPrevInvoiceInList() {
            return prevInvoiceInList;
    }

    public void setPrevInvoiceInList(int prevInvoiceInList) {
            this.prevInvoiceInList = prevInvoiceInList;
}
  /**
     * Getter for property existingIvoices.
     * @return Value of property existingIvoices.
     */
    public InvoiceDistDataVector getExistingInvoices() {

        return this.existingInvoices;
    }

    /**
     * Setter for property existingIvoices.
     * @param existingIvoices New value of property existingIvoices.
     */
    public void setExistingInvoices(InvoiceDistDataVector existingIvoices) {

        this.existingInvoices = existingIvoices;
    }

    /**
      * Getter for property invoiceDetail.
      * @return Value of property invoiceDetail.
      */
     public InvoiceDistDetailDataVector getInvoiceDetail() {

         return this.invoiceDetail;
     }

  public InvoiceNetworkServiceDataVector getResultList() {

    return resultList;
  }

  public String getDistributionCenterNo() {
    return distributionCenterNo;
  }

  public String getDistributionCenterName() {
    return distributionCenterName;
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public String getAccountName() {
    return accountName;
  }

  public Date getInvoiceDate() {

    return invoiceDate;
  }

  public String getInvoiceType() {
    return invoiceType;
  }

  public String getPoNumber() {
    return poNumber;
  }

  public String getTaxAmount() {
    return taxAmount;
  }

  public String getFreightCharges() {
    return freightCharges;
  }

  public String getInvoiceNumber() {
    return invoiceNumber;
  }

  public InvoiceDistView getInvoiceView() {
    return invoiceView;
  }

  public String getSortField() {
    return sortField;
  }

  /**
      * Setter for property existingIvoices.
      * @param invoiceDetail New value of property invoiceDetail.
      */
     public void setInvoiceDetail(InvoiceDistDetailDataVector invoiceDetail) {

         this.invoiceDetail = invoiceDetail;
     }

  public void setResultList(InvoiceNetworkServiceDataVector resultList) {

    this.resultList = resultList;
  }

  public void setDistributionCenterNo(String distributionCenterNo) {
    this.distributionCenterNo = distributionCenterNo;
  }

  public void setDistributionCenterName(String distributionCenterName) {
    this.distributionCenterName = distributionCenterName;
  }

  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }

  public void setAccountName(String accountName) {
    this.accountName = accountName;
  }

  public void setInvoiceDate(Date invoiceDate) {

    this.invoiceDate = invoiceDate;
  }

  public void setInvoiceType(String invoiceType) {
    this.invoiceType = invoiceType;
  }

  public void setPoNumber(String poNumber) {
    this.poNumber = poNumber;
  }

  public void setTaxAmount(String taxAmount) {
    this.taxAmount = taxAmount;
  }

  public void setFreightCharges(String freightCharges) {
    this.freightCharges = freightCharges;
  }

  public void setInvoiceNumber(String invoiceNumber) {
    this.invoiceNumber = invoiceNumber;
  }

  public void setInvoiceView(InvoiceDistView invoiceView) {
    this.invoiceView = invoiceView;
  }

  public void setSortField(String sortField) {
    this.sortField = sortField;
  }

  public OrderPropertyDataVector getOrderNotes() {
    return this.orderNotes;
  }

  public void setOrderNotes(OrderPropertyDataVector v) {
      this.orderNotes = v;
  }

}
