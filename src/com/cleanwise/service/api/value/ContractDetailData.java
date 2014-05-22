package com.cleanwise.service.api.value;

/**
 * Title:        ContractDetailData
 * Description:  Value object extension for marshalling contract attribute value pairs.
 * Purpose:      Obtains and marshals contract information among session components.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 */

import com.cleanwise.service.api.framework.*;
import java.lang.*;
import java.util.*;
import java.io.*;

/**
 * <code>ContractDetailData</code>
 */
public class ContractDetailData extends ValueObject
{
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = 7151818766244758323L;

  // -------------------------------------------------------- Instance Variables
  public int    contractId;
  public int    contractItemId;
  public int    contractPriceId;
  public int    productId;
  public int    skuId;
  public Date   effDate;
  public Date   expDate;
  public String locale;
  public String currencyCd;
  public String baseCurrencyCd;
  public double priceAmt;
  public double discountAmt;
  public int    qty;


  public ContractDetailData() {

  }

  // ---------------------------------------------------------------- Properties

  /**
   * Gets the contractId (contract identifier) property
   * return int
   */
  public int getContractId() {
    return this.contractId;
  }

  /**
   * Sets the contractId (contract identifier) property
   * @param pValue  the value we want to set
   * return none
   */
  public void setContractId(int pValue) {
    this.contractId = pValue;
  }

  /**
   * Gets the contractItemId (contract Item identifier) property
   * return int
   */
  public int getContractItemId() {
    return this.contractItemId;
  }

  /**
   * Sets the contractItemId (contract Item identifier) property
   * @param pValue  the value we want to set
   * return none
   */
  public void setContractItemId(int pValue) {
    this.contractItemId = pValue;
  }

  /**
   * Gets the contractPriceId (contract Price identifier) property
   * return int
   */
  public int getContractPriceId() {
    return this.contractPriceId;
  }

  /**
   * Sets the contractPriceId (contract Price identifier) property
   * @param pValue  the value we want to set
   * return none
   */
  public void setContractPriceId(int pValue) {
    this.contractPriceId = pValue;
  }

  /**
   * Gets the productId (product identifier) property
   * return int
   */
  public int getProductId() {
    return this.productId;
  }

  /**
   * Sets the productId (product identifier) property
   * @param pValue  the value we want to set
   * return none
   */
  public void setProductId(int pValue) {
    this.productId = pValue;
  }

  /**
   * Gets the skuId (sku identifier) property
   * return int
   */
  public int getSkuId() {
    return this.skuId;
  }

  /**
   * Sets the skuId (sku identifier) property
   * @param pValue  the value we want to set
   * return none
   */
  public void setSkuId(int pValue) {
    this.skuId = pValue;
  }

  /**
   * Gets the EffDate (effective date) property
   * return Date
   */
  public Date getEffDate() {
    return this.effDate;
  }

  /**
   * Sets the EffDate (effective date) property
   * @param pValue  the value we want to set
   * return none
   */
  public void setEffDate(Date pValue) {
    this.effDate = pValue;
  }

  /**
   * Gets the ExpDate (expiration date) property
   * return Date
   */
  public Date getExpDate() {
    return this.expDate;
  }

  /**
   * Sets the ExpDate (expiration date) property
   * @param pValue  the value we want to set
   * return none
   */
  public void setExpDate(Date pValue) {
    this.expDate = pValue;
  }

  /**
   * Gets the locale property
   * return String
   */
  public String getLocale() {
    return this.locale;
  }

  /**
   * Sets the locale property
   * @param pValue  the value we want to set
   * return none
   */
  public void setLocale(String pValue) {
    this.locale = pValue;
  }

  /**
   * Gets the currencyCd (currency code) property
   * return String
   */
  public String getCurrencyCd() {
    return this.currencyCd;
  }

  /**
   * Sets the currencyCd (currency code) property
   * @param pValue  the value we want to set
   * return none
   */
  public void setCurrencyCd(String pValue) {
    this.currencyCd = pValue;
  }

  /**
   * Gets the baseCurrencyCd (base currency code) property
   * return String
   */
  public String getBaseCurrencyCd() {
    return this.baseCurrencyCd;
  }

  /**
   * Sets the baseCurrencyCd (base currency code) property
   * @param pValue  the value we want to set
   * return none
   */
  public void setBaseCurrencyCd(String pValue) {
    this.baseCurrencyCd = pValue;
  }

  /**
   * Gets the priceAmt (sku list price) property
   * return int
   */
  public double getPriceAmt() {
    return this.priceAmt;
  }

  /**
   * Sets the priceAmt (sku list price) property
   * @param pValue  the value we want to set
   * return none
   */
  public void setPriceAmt(double pValue) {
    this.priceAmt = pValue;
  }

  /**
   * Gets the discountAmt (sku discount price) property
   * return int
   */
  public double getDiscountAmt() {
    return this.discountAmt;
  }

  /**
   * Sets the discountAmt (sku discount price) property
   * @param pValue  the value we want to set
   * return none
   */
  public void setDiscountAmt(double pValue) {
    this.discountAmt = pValue;
  }

  /**
   * Gets the qty (sku quantity) property
   * return int
   */
  public int getQty() {
    return this.qty;
  }

  /**
   * Sets the qty (sku quantity) property
   * @param pValue  the value we want to set
   * return none
   */
  public void setQty(int pValue) {
    this.qty = pValue;
  }


}
