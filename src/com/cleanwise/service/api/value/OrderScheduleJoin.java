/**
 * Title:        OrderScheduleJoin
 * Description:  This is a ValueObject class to display order schedule
 * Purpose:      Conviniet usage of order item properties
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Yuriy Kupershmidt
 */

package com.cleanwise.service.api.value;
import com.cleanwise.service.api.framework.*;
import java.util.Date;

public class OrderScheduleJoin  extends ValueObject{
  private int _userId = 0;
  private String _userFirstName = "";
  private String _userLastName = "";
  private int _siteId = 0;
  private String _siteName = "";
  private int _accountId = 0;
  private String _accountName = "";

  private int _orderScheduleId = 0;
  private int _orderGuideId =0;
  private int _workOrderId = 0;
  private String _orderGuideName = "";
  private Date _effDate = null;
  private Date _expDate = null;
  private int _cycle = 0;
  private int[] _elements = new int[0];
  private int _monthWeekDay = 0;
  private int _monthWeeks = 0;
  private String _orderScheduleCd = "";
  private String _orderScheduleRuleCd = "";
  private String _ccEmail = "";
  private Date[] _alsoDates = new Date[0];
  private Date[] _exceptDates = new Date[0];

  //crc info
  private String _contactName = "";
  private String _contactPhone = "";
  private String _contactEmail = "";

  public OrderScheduleJoin() {
  }

  /**
   * Creates a new OrderScheduleJoin
   * @return
   *  Newly initialized OrderScheduleJoin object.
   */
  public static OrderScheduleJoin createValue ()
  {
      OrderScheduleJoin valueData = new OrderScheduleJoin();
      return valueData;
  }

  public void setUserId(int pValue) {_userId = pValue;}
  public int getUserId() {return _userId;}

  public void setUserFirstName(String pValue) {_userFirstName = pValue;}
  public String getUserFirstName() {return _userFirstName;}

  public void setUserLastName(String pValue) {_userLastName = pValue;}
  public String getUserLastName() {return _userLastName;}

  public void setSiteId(int pValue) {_siteId = pValue;}
  public int getSiteId() {return _siteId;}

  public void setSiteName(String pValue) {_siteName = pValue;}
  public String getSiteName() {return _siteName;}

  public void setAccountId(int pValue) {_accountId = pValue;}
  public int getAccountId() {return _accountId;}

  public void setAccountName(String pValue) {_accountName = pValue;}
  public String getAccountName() {return _accountName;}

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

  public void setCycle(int pValue) {_cycle = pValue;}
  public int getCycle() {return _cycle;}

  public void setElements(int[] pValue) {_elements = pValue;}
  public int[] getElements() {return _elements;}

  public void setMonthWeekDay(int pValue) {_monthWeekDay = pValue;}
  public int getMonthWeekDay() {return _monthWeekDay;}

  public void setMonthWeeks(int pValue) {_monthWeeks = pValue;}
  public int getMonthWeeks() {return _monthWeeks;}

  public void setOrderScheduleCd(String pValue) {_orderScheduleCd = pValue;}
  public String getOrderScheduleCd() {return _orderScheduleCd;}

  public void setOrderScheduleRuleCd(String pValue) {_orderScheduleRuleCd = pValue;}
  public String getOrderScheduleRuleCd() {return _orderScheduleRuleCd;}

  public void setCcEmail(String pValue) {_ccEmail = pValue;}
  public String getCcEmail() {return _ccEmail;}

  public void setAlsoDates(Date[] pValue) {_alsoDates = pValue;}
  public Date[] getAlsoDates() {return _alsoDates;}

  public void setExceptDates(Date[] pValue) {_exceptDates = pValue;}
  public Date[] getExceptDates() {return _exceptDates;}

  public void setContactName(String pValue) {_contactName = pValue;}
  public String getContactName() {return _contactName;}

  public void setContactPhone(String pValue) {_contactPhone = pValue;}
  public String getContactPhone() {return _contactPhone;}

  public void setContactEmail(String pValue) {_contactEmail = pValue;}
  public String getContactEmail() {return _contactEmail;}

  public int getWorkOrderId() { return _workOrderId; }
  public void setWorkOrderId(int pValue) { this._workOrderId = pValue;}

/*
  public void set..(** pValue) {_.. = pValue;}
  public ** get..() {return _..;}
*/

}
