package com.cleanwise.service.api.value;

import com.cleanwise.service.api.framework.*;
import java.lang.*;
import java.util.*;
import java.io.*;

/**
 * <code>CatalogRequestData</code>
 */
public class CatalogRequestData extends ValueObject
{
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = -5051838771380826640L;
  private int _catalogId;

  public CatalogRequestData() {
  }
  public CatalogRequestData(int catalogId) {
    _catalogId=catalogId;
  }
  public int getCatalogId() {
    return _catalogId;
  }
}
