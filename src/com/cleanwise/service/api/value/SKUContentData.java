package com.cleanwise.service.api.value;

/**
 * Title:        SKUContentData
 * Description:  Value object extension for marshalling Sku content attribute value pairs.
 * Purpose:      Obtains and marshals Sku content information among session components.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 */

import com.cleanwise.service.api.framework.*;
import java.lang.*;
import java.util.*;
import java.io.*;

/**
 * <code>SKUContentData</code>
 */
public class SKUContentData extends ValueObject
{
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = -2947252039448116907L;

  // -------------------------------------------------------- Instance Variables
  public int    skuId;
  public int    contentId;
  public String contentShortDesc;
  public String contentTypeCd;
  public String contentStatusCd;
  public String contentUrl;


  public SKUContentData() {

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
