package com.cleanwise.service.api.value;

/**
 * Title:        ProductContentData
 * Description:  Value object extension for marshalling product content attribute value pairs.
 * Purpose:      Obtains and marshals product content information among session components.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 */

import com.cleanwise.service.api.framework.*;
import java.lang.*;
import java.util.*;
import java.io.*;

/**
 * <code>ProductContentData</code>
 */
public class ProductContentData extends ValueObject
{
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = -8401008243450945954L;

  // -------------------------------------------------------- Instance Variables
  public int    productId;
  public int    contentId;
  public String contentShortDesc;
  public String contentTypeCd;
  public String contentStatusCd;
  public String contentUrl;

  public ProductContentData() {

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
   * Gets the contentId (content identifier) property
   * return int
   */
  public int getContentId() {
    return this.contentId;
  }

  /**
   * Sets the contentId (content identifier) property
   * @param pValue  the value we want to set
   * return none
   */
  public void setContentId(int pValue) {
    this.contentId = pValue;
  }

  /**
   * Gets the contentShortDesc (content short description) property
   * return String
   */
  public String getContentShortDesc() {
    return this.contentShortDesc;
  }

  /**
   * Sets the contentShortDesc (content short description) property
   * @param pValue  the value we want to set
   * return none
   */
  public void setContentShortDesc(String pValue) {
    this.contentShortDesc = pValue;
  }

  /**
   * Gets the contentTypeCd (content type code) property
   * return String
   */
  public String getContentTypeCd() {
    return this.contentTypeCd;
  }

  /**
   * Sets the contentTypeCd (content type code) property
   * @param pValue  the value we want to set
   * return none
   */
  public void setContentTypeCd(String pValue) {
    this.contentTypeCd = pValue;
  }

  /**
   * Gets the contentStatusCd (content Status code) property
   * return String
   */
  public String getContentStatusCd() {
    return this.contentStatusCd;
  }

  /**
   * Sets the contentStatusCd (content Status code) property
   * @param pValue  the value we want to set
   * return none
   */
  public void setContentStatusCd(String pValue) {
    this.contentStatusCd = pValue;
  }

  /**
   * Gets the contentUrl (content Url) property
   * return String
   */
  public String getContentUrl() {
    return this.contentUrl;
  }

  /**
   * Sets the contentUrl (content Url) property
   * @param pValue  the value we want to set
   * return none
   */
  public void setContentUrl(String pValue) {
    this.contentUrl = pValue;
  }


}
