package com.cleanwise.service.api.value;

/**
 * Title:        CatalogAssociationData
 * Description:  Value object extension for marshalling catalog association attribute value pairs.
 * Purpose:      Obtains and marshals catalog relationship information among session components.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 */

import com.cleanwise.service.api.framework.*;
import java.lang.*;
import java.util.*;
import java.io.*;

/**
 * <code>CatalogAssociationData</code>
 */
public class CatalogAssociationData extends ValueObject
{
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = -8572943553940480001L;

  // -------------------------------------------------------- Instance Variables
  public int    catalogId;
  public int    busEntityId;
  public String assocTypeCd;

  public CatalogAssociationData() {

  }

  // ---------------------------------------------------------------- Properties

  /**
   * Gets the catalogId (catalog identifier) property
   * return int
   */
  public int getCatalogId() {
    return this.catalogId;
  }

  /**
   * Sets the catalogId (catalog identifier) property
   * @param pValue  the value we want to set
   * return none
   */
  public void setCatalogId(int pValue) {
    this.catalogId = pValue;
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

  /**
   * Gets the assocTypeCd (association type code) property
   * return int
   */
  public String getAssocTypeCd() {
    return this.assocTypeCd;
  }

  /**
   * Sets the assocTypeCd (association type code) property
   * @param pValue  the value we want to set
   * return none
   */
  public void setAssocTypeCd(String pValue) {
    this.assocTypeCd = pValue;
  }


}
