package com.cleanwise.service.api.value;

/**
 * Title:        BusEntityProfileMetaData
 * Description:  Value object extension for marshalling business entity meta profile attribute value pairs.
 * Purpose:      Obtains and marshals business entity meta profile information among session components.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 *
 */

import com.cleanwise.service.api.framework.*;
import java.lang.*;
import java.util.*;
import java.io.*;

/**
 * <code>BusEntityProfileMetaData</code>
 */
public class BusEntityProfileMetaData extends ValueObject
{
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = -1647218942150003100L;

  // -------------------------------------------------------- Instance Variables
  public int    busEntityId;
  public int    nameId;
  public int    valueId;
  public String nameValue;
  public String value;


  public BusEntityProfileMetaData() {

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
   * Gets the nameId (name identifier) property
   * return int
   */
  public int getNameId() {
    return this.nameId;
  }

  /**
   * Sets the nameId (name identifier) property
   * @param pValue  the value we want to set
   * return none
   */
  public void setNameId(int pValue) {
    this.nameId = pValue;
  }

  /**
   * Gets the valueId (Value identifier) property
   * return int
   */
  public int getValueId() {
    return this.valueId;
  }

  /**
   * Sets the valueId (value identifier) property
   * @param pValue  the value we want to set
   * return none
   */
  public void setValueId(int pValue) {
    this.valueId = pValue;
  }

  /**
   * Gets the nameValue (name value) property
   * return String
   */
  public String getNameValue() {
    return this.nameValue;
  }

  /**
   * Sets the nameValue (name value) property
   * @param pValue  the value we want to set
   * return none
   */
  public void setNameValue(String pValue) {
    this.nameValue = pValue;
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


}
