package com.cleanwise.service.api.value;

/**
 * Title:        CustomCategoryData
 * Description:  Value object extension for marshalling category custom attribute value pairs.
 * Purpose:      Obtains and marshals category custom information among session components.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 */

import com.cleanwise.service.api.framework.*;
import java.lang.*;
import java.util.*;
import java.io.*;

/**
 * <code>CustomCategoryData</code>
 */
public class CustomCategoryData extends ValueObject
{
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = -5012124712272668442L;

  // -------------------------------------------------------- Instance Variables
  public int    categoryId;
  public int    busEntityId;

  public CustomCategoryData() {

  }

  // ---------------------------------------------------------------- Properties

  /**
   * Gets the categoryId (category identifier) property
   * return int
   */
  public int getCategoryId() {
    return this.categoryId;
  }

  /**
   * Sets the categoryId (category identifier) property
   * @param pValue  the value we want to set
   * return none
   */
  public void setCategoryId(int pValue) {
    this.categoryId = pValue;
  }

  /**
   * Gets the BusEntityId (business entity identifier) property
   * return int
   */
  public int getBusEntityId() {
    return this.busEntityId;
  }

  /**
   * Sets the BusEntityId (business entity identifier) property
   * @param pValue  the value we want to set
   * return none
   */
  public void setBusEntityId(int pValue) {
    this.busEntityId = pValue;
  }


}
