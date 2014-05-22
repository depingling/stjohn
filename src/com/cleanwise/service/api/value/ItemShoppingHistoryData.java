package com.cleanwise.service.api.value;

import com.cleanwise.service.api.framework.*;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.math.*;
import com.cleanwise.view.utils.Constants;
/**
 * <code>ShoppingCartData</code>
 */
public class ItemShoppingHistoryData extends ValueObject
{
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = 3814721218112878870L;
  //---------------------------------------------------------------------------
  int _lastOrderId = 0;
  String _lastOrderNumber = "";
  double _lastPrice = 0;
  int _lastQty = 0;
  Date _lastDate = new Date(0);
  int _y2dQty = 0;
  double _y2dAmt = 0;

  //---------------------------------------------------------------------------
  public ItemShoppingHistoryData() {
  }

/*
  public void set. (String pValue) {_. = pValue;}
  public String get.() {return _.;}

*/
  public void setLastOrderId (int pValue) {_lastOrderId = pValue;}
  public int getLastOrderId() {return _lastOrderId;}

  public void setLastOrderNumber (String pValue) {_lastOrderNumber = pValue;}
  public String getLastOrderNumber() {return _lastOrderNumber;}

  public void setLastPrice (double pValue) {_lastPrice = pValue;}
  public double getLastPrice() {return _lastPrice;}

  public double getLastAmt() {
     BigDecimal priceBD = new BigDecimal((getLastPrice()+.005)*100);
     long price = priceBD.longValue();
     long cost = getLastQty()*price;
     double costD = cost;
     return costD/100;
  }

  public void setLastQty (int pValue) {_lastQty = pValue;}
  public int getLastQty() {return _lastQty;}

  public void setLastDate (Date pValue) {_lastDate = pValue;}
  public Date getLastDate() {return _lastDate;}

  public void setY2dQty (int pValue) {_y2dQty = pValue;}
  public int getY2dQty() {return _y2dQty;}

  public void setY2dAmt (double pValue) {_y2dAmt = pValue;}
  public double getY2dAmt() {return _y2dAmt;}

}
