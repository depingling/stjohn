package com.cleanwise.service.api.value;

/**
 * Title:        ProductMappingData
 * Description:  Value object extension for marshalling product mapping attribute value pairs.
 * Purpose:      Obtains and marshals product mapping information among session components.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 */

import com.cleanwise.service.api.framework.*;
import java.lang.*;
import java.util.*;
import java.io.*;

/**
 * <code>ProductMappingData</code>
 */
public class ProductMappingData extends ValueObject
{
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = -765478803147597637L;

  // -------------------------------------------------------- Instance Variables
  public int    productId;
  public int    productMappingId;
  public String busEntityProductNum;
  public String busEntityProductShortDesc;


  public ProductMappingData() {

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
   * Gets the productMappingId (product Mapping identifier) property
   * return int
   */
  public int getProductMappingId() {
    return this.productMappingId;
  }

  /**
   * Sets the productMappingId (product Mapping identifier) property
   * @param pValue  the value we want to set
   * return none
   */
  public void setProductMappingId(int pValue) {
    this.productMappingId = pValue;
  }

  /**
   * Gets the busEntityProductNum (business entity product number) property
   * return String
   */
  public String getBusEntityProductNum() {
    return this.busEntityProductNum;
  }

  /**
   * Sets the busEntityProductNum (business entity product number) property
   * @param pValue  the value we want to set
   * return none
   */
  public void setBusEntityProductNum(String pValue) {
    this.busEntityProductNum = pValue;
  }

  /**
   * Gets the busEntityProductShortDesc (business entity product short description) property
   * return String
   */
  public String getBusEntityProductShortDesc() {
    return this.busEntityProductShortDesc;
  }

  /**
   * Sets the busEntityProductShortDesc (business entity product short description) property
   * @param pValue  the value we want to set
   * return none
   */
  public void setBusEntityProductShortDesc(String pValue) {
    this.busEntityProductShortDesc = pValue;
  }


}
