package com.cleanwise.service.api.value;

/**
 * Title:        SKUPriceData
 * Description:  Value object extension for marshalling Sku price attribute value pairs.
 * Purpose:      Obtains and marshals Sku price information among session components.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 */

import com.cleanwise.service.api.framework.*;
import java.lang.*;
import java.util.*;
import java.io.*;

/**
 * <code>SKUPriceData</code>
 */
public class SKUPriceData extends ValueObject
{
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = -8576667448076603874L;

  // -------------------------------------------------------- Instance Variables
  public int    skuId;
  public int    priceId;
  public String priceShortDesc;
  public String priceTypeCd;
  public String priceStatusCd;
  public Date   effDate;
  public Date   expDate;
  public String locale;
  public String currencyCd;
  public String baseCurrencyCd;
  public double priceAmt;
  public double discountAmt;
  public int    qty;


  public SKUPriceData() {

  }

  // ---------------------------------------------------------------- Properties

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
   * Gets the priceId (price identifier) property
   * return int
   */
  public int getPriceId() {
    return this.priceId;
  }

  /**
   * Sets the priceId (price identifier) property
   * @param pValue  the value we want to set
   * return none
   */
  public void setPriceId(int pValue) {
    this.priceId = pValue;
  }

  /**
   * Gets the priceShortDesc (price short description) property
   * return String
   */
  public String getPriceShortDesc() {
    return this.priceShortDesc;
  }

  /**
   * Sets the priceShortDesc (price short description) property
   * @param pValue  the value we want to set
   * return none
   */
  public void setPriceShortDesc(String pValue) {
    this.priceShortDesc = pValue;
  }

  /**
   * Gets the priceTypeCd (price type code) property
   * return String
   */
  public String getPriceTypeCd() {
    return this.priceTypeCd;
  }

  /**
   * Sets the priceTypeCd (price type code) property
   * @param pValue  the value we want to set
   * return none
   */
  public void setPriceTypeCd(String pValue) {
    this.priceTypeCd = pValue;
  }

  /**
   * Gets the priceStatusCd (price Status code) property
   * return String
   */
  public String getPriceStatusCd() {
    return this.priceStatusCd;
  }

  /**
   * Sets the priceStatusCd (price Status code) property
   * @param pValue  the value we want to set
   * return none
   */
  public void setPriceStatusCd(String pValue) {
    this.priceStatusCd = pValue;
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
