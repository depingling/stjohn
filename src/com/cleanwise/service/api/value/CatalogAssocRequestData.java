package com.cleanwise.service.api.value;

/**
 * Title:        CatalogAssocRequestData
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 */

import com.cleanwise.service.api.framework.*;
import java.lang.*;
import java.util.*;
import java.io.*;

/**
 * <code>CatalogAssocRequestData</code>
 */
public class CatalogAssocRequestData extends ValueObject
{
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = 6959002299485221076L;

  int _catalogAssocRD;
  // -------------------------------------------------------- Instance Variables

  public CatalogAssocRequestData() {
  }
  public CatalogAssocRequestData(int pCatalogAssocRD) {
    _catalogAssocRD = pCatalogAssocRD;
  }

  // ---------------------------------------------------------------- Properties
  /**
  * Gets id of CatalogAssocData object associated with this request
  * @return int
  */
  public int getCatalogAssocId() {
    return _catalogAssocRD;
  }



}
