package com.cleanwise.service.api.value;

/**
 * Title:        UserPropertyData
 * Description:  Value object extension for marshalling business entity property attribute value pairs.
 * Purpose:      Obtains and marshals business entity property information among session components.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 */

import com.cleanwise.service.api.framework.*;
import java.lang.*;
import java.util.*;
import java.io.*;

/**
 * <code>UserPropertyData</code>
 */
public class UserPropertyData extends ValueObject
{
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = -7655238442864872254L;

  // -------------------------------------------------------- Instance Variables
  public int    busEntityId;
  public int    propertyId;
  public String propertyShortDesc;
  public String value;
  public String propertyStatusCd;
  public String propertyTypeCd;


  public UserPropertyData() {

  }


  // ---------------------------------------------------------------- Properties

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

  /**
   * Gets the propertyId (property identifier) property
   * return int
   */
  public int getPropertyId() {
    return this.propertyId;
  }

  /**
   * Sets the propertyId (property identifier) property
   * @param pValue  the value we want to set
   * return none
   */
  public void setPropertyId(int pValue) {
    this.propertyId = pValue;
  }

  /**
   * Gets the propertyShortDesc (property short description or name) property
   * return String
   */
  public String getPropertyShortDesc() {
    return this.propertyShortDesc;
  }

  /**
   * Sets the propertyShortDesc (property short description or name) property
   * @param pValue  the value we want to set
   * return none
   */
  public void setPropertyShortDesc(String pValue) {
    this.propertyShortDesc = pValue;
  }

  /**
   * Gets the Value property
   * return String
   */
  public String getValue() {
    return this.value;
  }

  /**
   * Sets the Value property
   * @param pValue  the value we want to set
   * return none
   */
  public void setValue(String pValue) {
    this.value = pValue;
  }

  /**
   * Gets the propertyStatusCd (property status code) property
   * return String
   */
  public String getPropertyStatusCd() {
    return this.propertyStatusCd;
  }

  /**
   * Sets the propertyStatusCd (property status code) property
   * @param pValue  the value we want to set
   * return none
   */
  public void setPropertyStatusCd(String pValue) {
    this.propertyStatusCd = pValue;
  }

  /**
   * Gets the propertyTypeCd (property type code) property
   * return String
   */
  public String getPropertyTypeCd() {
    return this.propertyTypeCd;
  }

  /**
   * Sets the propertyTypeCd (property type code) property
   * @param pValue  the value we want to set
   * return none
   */
  public void setPropertyTypeCd(String pValue) {
    this.propertyTypeCd = pValue;
  }


}
