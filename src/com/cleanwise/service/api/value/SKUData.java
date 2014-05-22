package com.cleanwise.service.api.value;

/**
 * Title:        SKUData
 * Description:  Value object extension for marshalling SKU attribute value pairs.
 * Purpose:      Obtains and marshals SKU information among session components.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 */

import com.cleanwise.service.api.framework.*;
import java.lang.*;
import java.util.*;
import java.io.*;

/**
 * <code>SKUData</code>
 */
public class SKUData extends ValueObject
{
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = 3429840536541278226L;

  // -------------------------------------------------------- Instance Variables
  public int    skuId;
  public String skuShortDesc;
  public String skuLongDesc;
  public String skuTypeCd;
  public String skuStatusCd;
  public String skuFulfillCd;
  public String imageShortDesc;
  public String imagePath;
  public Date   effDate;
  public Date   expDate;
  public String inCatalogInd;
  public String skuUpc;
  public String skuPkgUpc;
  public String packCd;
  public String sizeShortDesc;
  public double weight;


  public SKUData() {

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
   * Gets the skuShortDesc (sku short description) property
   * return String
   */
  public String getSkuShortDesc() {
    return this.skuShortDesc;
  }

  /**
   * Sets the skuShortDesc (sku short description) property
   * @param pValue  the value we want to set
   * return none
   */
  public void setSkuShortDesc(String pValue) {
    this.skuShortDesc = pValue;
  }

  /**
   * Gets the skuLongDesc (sku Long description) property
   * return String
   */
  public String getSkuLongDesc() {
    return this.skuLongDesc;
  }

  /**
   * Sets the skuLongDesc (sku Long description) property
   * @param pValue  the value we want to set
   * return none
   */
  public void setSkuLongDesc(String pValue) {
    this.skuLongDesc = pValue;
  }

  /**
   * Gets the skuTypeCd (sku type code) property
   * return String
   */
  public String getSkuTypeCd() {
    return this.skuTypeCd;
  }

  /**
   * Sets the skuTypeCd (sku type code) property
   * @param pValue  the value we want to set
   * return none
   */
  public void setSkuTypeCd(String pValue) {
    this.skuTypeCd = pValue;
  }

  /**
   * Gets the skuStatusCd (sku Status code) property
   * return String
   */
  public String getSkuStatusCd() {
    return this.skuStatusCd;
  }

  /**
   * Sets the skuStatusCd (sku Status code) property
   * @param pValue  the value we want to set
   * return none
   */
  public void setSkuStatusCd(String pValue) {
    this.skuStatusCd = pValue;
  }

  /**
   * Gets the skuFulfillCd (sku fulfillment code) property
   * return String
   */
  public String getSkuFulfillCd() {
    return this.skuFulfillCd;
  }

  /**
   * Sets the skuFulfillCd (sku fulfillment code) property
   * @param pValue  the value we want to set
   * return none
   */
  public void setSkuFulfillCd(String pValue) {
    this.skuFulfillCd = pValue;
  }

  /**
   * Gets the imageShortDesc (image short description) property
   * return String
   */
  public String getImageShortDesc() {
    return this.imageShortDesc;
  }

  /**
   * Sets the imageShortDesc (image short description) property
   * @param pValue  the value we want to set
   * return none
   */
  public void setImageShortDesc(String pValue) {
    this.imageShortDesc = pValue;
  }

  /**
   * Gets the imagePath (image Path) property
   * return String
   */
  public String getImagePath() {
    return this.imagePath;
  }

  /**
   * Sets the imagePath (image Path) property
   * @param pValue  the value we want to set
   * return none
   */
  public void setImagePath(String pValue) {
    this.imagePath = pValue;
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
   * Gets the inCatalogInd (in catalog indicator) property
   * return String
   */
  public String getInCatalogInd() {
    return this.inCatalogInd;
  }

  /**
   * Sets the inCatalogInd (in catalog indicator) property
   * @param pValue  the value we want to set
   * return none
   */
  public void setInCatalogInd(String pValue) {
    this.inCatalogInd = pValue;
  }

  /**
   * Gets the skuUpc (sku Upc code) property
   * return String
   */
  public String getSkuUpc() {
    return this.skuUpc;
  }

  /**
   * Sets the skuUpc (sku Upc code) property
   * @param pValue  the value we want to set
   * return none
   */
  public void setSkuUpc(String pValue) {
    this.skuUpc = pValue;
  }

  /**
   * Gets the skuPkgUpc (sku package Upc code) property
   * return String
   */
  public String getSkuPkgUpc() {
    return this.skuPkgUpc;
  }

  /**
   * Sets the skuPkgUpc (sku package Upc code) property
   * @param pValue  the value we want to set
   * return none
   */
  public void setSkuPkgUpc(String pValue) {
    this.skuPkgUpc = pValue;
  }

  /**
   * Gets the PackCd (sku package code) property
   * return String
   */
  public String getPackCd() {
    return this.packCd;
  }

  /**
   * Sets the packCd (sku package code) property
   * @param pValue  the value we want to set
   * return none
   */
  public void setPackCd(String pValue) {
    this.packCd = pValue;
  }

  /**
   * Gets the sizeShortDesc (sku size short description) property
   * return String
   */
  public String getSizeShortDesc() {
    return this.sizeShortDesc;
  }

  /**
   * Sets the sizeShortDesc (sku sizeshort description) property
   * @param pValue  the value we want to set
   * return none
   */
  public void setSizeShortDesc(String pValue) {
    this.sizeShortDesc = pValue;
  }

  /**
   * Gets the weight (sku weight) property
   * return double
   */
  public double getWeight() {
    return this.weight;
  }

  /**
   * Sets the weight (sku weight) property
   * @param pValue  the value we want to set
   * return none
   */
  public void setWeight(double pValue) {
    this.weight = pValue;
  }



}
