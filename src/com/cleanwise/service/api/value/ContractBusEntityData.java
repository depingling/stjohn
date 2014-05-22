package com.cleanwise.service.api.value;

/**
 * Title:        ContractBusEntityData
 * Description:  Value object extension for marshalling contract business entity attribute value pairs.
 * Purpose:      Obtains and marshals contract business entity information among session components.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 */

import com.cleanwise.service.api.framework.*;
import java.lang.*;
import java.util.*;
import java.io.*;

/**
 * <code>ContractBusEntityData</code>
 */
public class ContractBusEntityData extends ValueObject
{
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = -423387891563722578L;

  // -------------------------------------------------------- Instance Variables
  public int    busEntityId;
  public int    contractId;

  public ContractBusEntityData() {

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
   * Gets the contractId (contract identifier) property
   * return int
   */
  public int getContractId() {
    return this.contractId;
  }

  /**
   * Sets the contractId (contract identifier) property
   * @param pValue  the value we want to set
   * return none
   */
  public void setContractId(int pValue) {
    this.contractId = pValue;
  }



}
