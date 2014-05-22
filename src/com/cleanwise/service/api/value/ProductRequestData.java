package com.cleanwise.service.api.value;

import com.cleanwise.service.api.framework.*;
import java.lang.*;
import java.util.*;
import java.io.*;

/**
 * <code>ProductRequestData</code>
 */
public class ProductRequestData extends ValueObject
{
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = 7981075540373715815L;
  int _productId;
  public ProductRequestData() {

  }
  public ProductRequestData(int productId) {
    _productId=productId;
  }

}
