package com.cleanwise.service.api.value;

/**
 * Title:        ProductSKUData
 * Description:  Value object extension for marshalling product SKU attribute value pairs.
 * Purpose:      Obtains and marshals product SKU information among session components.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 */

import com.cleanwise.service.api.framework.*;
import java.lang.*;
import java.util.*;
import java.io.*;

/**
 * <code>ProductSKUData</code>
 */
public class ProductSKUData extends ValueObject
{
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = -8071570004696377865L;

  // -------------------------------------------------------- Instance Variables
  public int    productId;
  public int    skuId;

  public ProductSKUData() {

  }

  // ---------------------------------------------------------------- Properties

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


}
