package com.cleanwise.service.api.value;

/**
 * Title:        MetaData
 * Description:  Value object extension for marshalling meta attribute value pairs.
 * Purpose:      Obtains and marshals meta information among session components.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 */

import com.cleanwise.service.api.framework.*;
import java.lang.*;
import java.util.*;
import java.io.*;

/**
 * <code>MetaData</code>
 */
public class MetaData extends ValueObject
{
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = 1615152295307112145L;

  // -------------------------------------------------------- Instance Variables
  public int    nameId;
  public int    valueId;
  public String nameValue;
  public String value;


  public MetaData() {

  }

  // ---------------------------------------------------------------- Properties

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
   * Gets the valueId (value identifier) property
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
   * Gets the nameValue (name Value) property
   * return String
   */
  public String getNameValue() {
    return this.nameValue;
  }

  /**
   * Sets the nameValue (name Value) property
   * @param pValue  the value we want to set
   * return none
   */
  public void setNameValue(String pValue) {
    this.nameValue = pValue;
  }

  /**
   * Gets the value property
   * return String
   */
  public String getValue() {
    return this.value;
  }

  /**
   * Sets the value property
   * @param pValue  the value we want to set
   * return none
   */
  public void setValue(String pValue) {
    this.value = pValue;
  }



}
