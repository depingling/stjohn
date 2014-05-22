package com.cleanwise.service.api.value;

/**
 * Title:        CatalogRelationshipData
 * Description:  Value object extension for marshalling catalog association attribute value pairs.
 * Purpose:      Obtains and marshals catalog relationship information among session components.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 */

import com.cleanwise.service.api.framework.*;
import java.lang.*;
import java.util.*;
import java.io.*;

/**
 * <code>CatalogRelationshipData</code>
 */
public class CatalogRelationshipData extends ValueObject
{
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = -7389738142041428511L;

  // -------------------------------------------------------- Instance Variables
  public int    catalogId;
  public int    catalogRelId;
  public int    categoryId;
  public int    productId;
  public int    skuId;
  public String catalogRelCd;

  public CatalogRelationshipData() {

  }

  // ---------------------------------------------------------------- Properties

  /**
   * Gets the catalogId (catalog identifier) property
   * return int
   */
  public int getCatalogId() {
    return this.catalogId;
  }

  /**
   * Sets the catalogId (catalog identifier) property
   * @param pValue  the value we want to set
   * return none
   */
  public void setCatalogId(int pValue) {
    this.catalogId = pValue;
  }

  /**
   * Sets the catalogRelId (catalog relationship identifier) property
   * @param pValue  the value we want to set
   * return none
   */
  public void setCatalogRelId(int pValue) {
    this.catalogRelId = pValue;
  }

  /**
   * Gets the catalogRelId (catalog relationship identifier) property
   * return int
   */
  public int getCatalogRelId() {
    return this.catalogRelId;
  }

  /**
   * Gets the CategoryId (Category identifier) property
   * return int
   */
  public int getCategoryId() {
    return this.categoryId;
  }

  /**
   * Sets the CategoryId (Category identifier) property
   * @param pValue  the value we want to set
   * return none
   */
  public void setCategoryId(int pValue) {
    this.categoryId = pValue;
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
   * Gets the catalogRelCd (catalog relationship code) property
   * return int
   */
  public String getCatalogRelCd() {
    return this.catalogRelCd;
  }

  /**
   * Sets the catalogRelCd (catalog relationship code) property
   * @param pValue  the value we want to set
   * return none
   */
  public void setCatalogRelCd(String pValue) {
    this.catalogRelCd = pValue;
  }


}
