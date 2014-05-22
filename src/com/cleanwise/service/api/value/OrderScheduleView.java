/**
 * Title:        OrderScheduleView
 * Description:  This is a ValueObject class to display brief order schedule info
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Yuriy Kupershmidt
 */

package com.cleanwise.service.api.value;
import com.cleanwise.service.api.framework.*;
import java.util.Date;

public class OrderScheduleView  extends ValueObject{
  private int _orderScheduleId = 0;
  private int _orderGuideId =0;
  private String _orderGuideName = "";
  private Date _effDate = null;
  private Date _expDate = null;
  private String _orderScheduleCd = "";
  private String _orderScheduleRuleCd = "";
  private int _siteId = 0;
  private String _siteName = "";
  private int _accountId = 0;
  private String _accountName = "";
  private int _userId = 0;

  private OrderScheduleView() {
  }
  /**
   * Creates a new OrderScheduleView
   * @return
   *  Newly initialized OrderScheduleView object.
   */
  public static OrderScheduleView createValue ()
  {
      OrderScheduleView valueData = new OrderScheduleView();
      return valueData;
  }

  public void setOrderScheduleId(int pValue) {_orderScheduleId = pValue;}
  public int getOrderScheduleId() {return _orderScheduleId;}

  public void setOrderGuideId(int pValue) {_orderGuideId = pValue;}
  public int getOrderGuideId() {return _orderGuideId;}

  public void setOrderGuideName(String pValue) {_orderGuideName = pValue;}
  public String getOrderGuideName() {return _orderGuideName;}

  public void setEffDate(Date pValue) {_effDate = pValue;}
  public Date getEffDate() {return _effDate;}

  public void setExpDate(Date pValue) {_expDate = pValue;}
  public Date getExpDate() {return _expDate;}

  public void setOrderScheduleCd(String pValue) {_orderScheduleCd = pValue;}
  public String getOrderScheduleCd() {return _orderScheduleCd;}

  public void setOrderScheduleRuleCd(String pValue) {_orderScheduleRuleCd = pValue;}
  public String getOrderScheduleRuleCd() {return _orderScheduleRuleCd;}

  public void setSiteId(int pValue) {_siteId = pValue;}
  public int getSiteId() {return _siteId;}

  public void setSiteName(String  pValue) {_siteName = pValue;}
  public String  getSiteName() {return _siteName;}

  public void setAccountId(int pValue) {_accountId = pValue;}
  public int getAccountId() {return _accountId;}

  public void setAccountName(String  pValue) {_accountName = pValue;}
  public String  getAccountName() {return _accountName;}

  public void setUserId(int pValue) {_userId = pValue;}
  public int getUserId() {return _userId;}

/*
  public void set..(** pValue) {_.. = pValue;}
  public ** get..() {return _..;}

*/

}
