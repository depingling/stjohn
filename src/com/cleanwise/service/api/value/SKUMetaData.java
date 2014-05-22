package com.cleanwise.service.api.value;

/**
 * Title:        SKUMetaData
 * Description:  Value object extension for marshalling Sku meta attribute value pairs.
 * Purpose:      Obtains and marshals Sku meta information among session components.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 */

import com.cleanwise.service.api.framework.*;
import java.lang.*;
import java.util.*;
import java.io.*;

/**
 * <code>SKUMetaData</code>
 */
public class SKUMetaData extends MetaData
{
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = 4358179651182488620L;

  // -------------------------------------------------------- Instance Variables
  public int    skuId;


  public SKUMetaData() {

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


}
