package com.cleanwise.service.api.util;

/**
 * This is a utility class for search criteria
 *
 * Copyright:   Copyright (c) 2001
 * Company:     CleanWise, Inc.
 * @author      Yuriy Kupershmidt
 *
 */

import java.util.Vector;
import java.io.Serializable;

public class SearchCriteria extends Object
implements Serializable
{
  //operators
  public static final int EXACT_MATCH = 1000;
  public static final int BEGINS_WITH = 1001;
  public static final int CONTAINS = 1002;
  public static final int EXACT_MATCH_IGNORE_CASE = 1003;
  public static final int BEGINS_WITH_IGNORE_CASE = 1004;
  public static final int CONTAINS_IGNORE_CASE = 1005;

  //oder
  public static final int ORDER_BY_ID = 2000;
  public static final int ORDER_BY_SHORT_DESC = 2001;


  //For product search
  public static final String CLW_SKU_NUMBER="CLW_SKU_NUMBER";
  public static final String CLW_CUST_SKU_NUMBER="CLW_CUST_SKU_NUMBER";
  public static final String CUST_SKU_NUMBER="CUST_SKU_NUMBER";
  public static final String STORE_SKU_NUMBER = "STORE_SKU_NUMBER";
  public static final String CATALOG_SKU_NUMBER = "CATALOG_SKU_NUMBER";
  public static final String SHOPPING_SKU_NUMBER = "SHOPPING_SKU_NUMBER"; //CATALOG_SKU_NUMBER or if null - STORE_SKU_NUMBER 
  public static final String ITEM_ID="ITEM_ID";
  public static final String ITEM_STATUS_CD="ITEM_STATUS_CD";  
  public static final String MANUFACTURER_SKU_NUMBER="MANUFACTURER_SKU_NUMBER";
  public static final String DISTRIBUTOR_SKU_NUMBER="DISTRIBUTOR_SKU_NUMBER";
  public static final String DISTRIBUTOR_SHORT_DESC="DISTRIBUTOR_SHORT_DESC";
  public static final String PRODUCT_SHORT_DESC = "PRODUCT_SHORT_DESC";
  public static final String PRODUCT_LONG_DESC = "PRODUCT_LONG_DESC";
  public static final String MANUFACTURER_SHORT_DESC ="MANUFACTURER_SHORT_DESC";
  public static final String MANUFACTURER_ID ="MANUFACTURER_ID";
  public static final String DISTRIBUTOR_ID ="DISTRIBUTOR_ID";
  public static final String CATALOG_CATEGORY ="CATALOG_CATEGORY";
  public static final String SKU_SIZE = "SKU_SIZE";
  public static final String ITEM_META = "ITEM_META:";
  public static final String CONTRACT_ID = "CONTRACT_ID";
  public static final String STORE_ID = "STORE_ID";
  public static final String CATALOG_ID = "CATALOG_ID";
  public static final String CERTIFIED="CERTIFIED";
  public static final String STAGED_SEARCH_TYPE="STAGED_SEARCH_TYPE";
  public static final String MESSAGE_ID = "MESSAGE_ID";
  public static final String MESSAGE_TITLE = "MESSAGE_TITLE";

    /**
  * Constructor
  */
public SearchCriteria(){}
  public String name;
  public Object value;
  public int operator;

  public SearchCriteria(String name, int operator, Object value)
  {
    this.name = name;
    this.operator = operator;
    this.value = value;
  }
  public String getName() {
    return name;
  }
  public int getOperator() {
    return operator;
  }
  public String getStringValue() {
     return value.toString();
  }
  public Object getObjectValue() {
     return value;
  }

}
