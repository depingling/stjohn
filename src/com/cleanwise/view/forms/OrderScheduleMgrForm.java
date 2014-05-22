/**
 *  Title: TradingPartnerMgrForm Description: This is the Struts ActionForm
 *  class for tranding partner UI service
 *  Copyright: Copyright (c) 2001 Company: CleanWise, Inc.
 *
 *@author     Yuriy Kupershmidt
 */

package com.cleanwise.view.forms;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.SearchCriteria;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ArrayList;

/**
 *  Form bean for the order scheduler manager configuration page.
 *
 *@author     Yuriy Kupershmidt
 *@created    October 14, 2001
 */
public final class OrderScheduleMgrForm extends ActionForm {
   //Page control
   private String _contentPage = "";
   //Search parameters
   private int _searchType = SearchCriteria.BEGINS_WITH_IGNORE_CASE;
   private String _searchAccountId = "";
   private String _searchAccountName = "";
   private String _searchSiteId = "";
   private String _searchSiteName = "";
   private String _searchOrderGuideName = "";
   private String _searchOrderScheduleType = "";
   private String _searchOrderScheduleRuleType = "";
   private String _searchDateFor = "";
   private OrderScheduleViewVector _orderSchedules = null;
   private OrderScheduleJoin _orderSchedule = null;



   //Page control
//   private String _contentPage = "";
   public void setContentPage(String pValue) {_contentPage = pValue;}
   public String getContentPage(){return _contentPage;}

   //Search parameters
//   private int _searchType = SearchCriteria.BEGINS_WITH_IGNORE_CASE;
   public void setSearchType(int pValue) {_searchType = pValue;}
   public int getSearchType(){return _searchType;}

//   private String _searchAccountId = "";
   public void setSearchAccountId(String pValue) {_searchAccountId = pValue;}
   public String getSearchAccountId(){return _searchAccountId;}

//   private String _searchAccountName = "";
   public void setSearchAccountName(String pValue) {_searchAccountName = pValue;}
   public String getSearchAccountName(){return _searchAccountName;}

//   private String _searchSiteId = "";
   public void setSearchSiteId(String pValue) {_searchSiteId = pValue;}
   public String getSearchSiteId(){return _searchSiteId;}

//   private String _searchSiteName = "";
   public void setSearchSiteName(String pValue) {_searchSiteName = pValue;}
   public String getSearchSiteName(){return _searchSiteName;}

//   private String _searchOrderGuideName = "";
   public void setSearchOrderGuideName(String pValue) {_searchOrderGuideName = pValue;}
   public String getSearchOrderGuideName(){return _searchOrderGuideName;}

//   private String _searchOrderScheduleType = "";
   public void setSearchOrderScheduleType(String pValue) {_searchOrderScheduleType = pValue;}
   public String getSearchOrderScheduleType(){return _searchOrderScheduleType;}

//   private String _searchOrderScheduleRuleType = "";
   public void setSearchOrderScheduleRuleType(String pValue) {_searchOrderScheduleRuleType = pValue;}
   public String getSearchOrderScheduleRuleType(){return _searchOrderScheduleRuleType;}

   public void setSearchDateFor(String pValue) {_searchDateFor = pValue;}
   public String getSearchDateFor(){return _searchDateFor;}

//   private OrderScheduleViewVector _orderSchedules = null;
   public void setOrderSchedules(OrderScheduleViewVector pValue) {_orderSchedules = pValue;}
   public OrderScheduleViewVector getOrderSchedules(){return _orderSchedules;}

   public void setOrderSchedule(OrderScheduleJoin pValue) {_orderSchedule = pValue;}
   public OrderScheduleJoin getOrderSchedule(){return _orderSchedule;}


/*
   public void set..(** pValue) {_.. = pValue;}
   public ** get..(){return _..;}

*/
/////////////////////////////////////////
  // -------------------------------------------------------- Instance Variables
  private UserDataVector _users = null;
  private int _catalogId;
//  private String _siteName;
  private int _userId;
//  private String _accountName;
//  private String _scheduleType = "week";
  private OrderGuideDataVector _userOrderGuides = null;
  private OrderGuideDataVector _templateOrderGuides = null;
//  private int _orderScheduleId = 0;
//  private int _orderGuideId = 0;

  private String _startDate = null;
  private String _endDate = null;
//  private String _scheduleAction = RefCodeNames.ORDER_SCHEDULE_CD.NOTIFY;

  private String _weekCycle = "1";
  private int[] _weekDays = new int[0];  // Sunday - 1, ... Saturday - 6

  private String _monthDayCycle = "1";
  private int[] _monthDays = new int[0]; //1 ... 28

  private String _monthWeekCycle = "1";
  private int _monthWeekDay = 2; // Sunday - 1, ... Saturday - 7, day - 8
  private int[] _monthWeeks = new int[0]; //first -1, second -2, third -3, forth -4, last -5

  private String _alsoDates = "";
  private String _excludeDates = "";

  private int _monthWeekFrequency = 0;

  private String _ccEmail = "";

  private GregorianCalendar _calendar = null;
  private int _lastReqMonth = -1;
  private int _lastReqMonthDay = -1;
  private int _lastReqFirstWeekDay = -1;
  private int _nextOrderIndex = 0;
  private int _nextOrderRelMonth = 0;
  private int _nextOrderMonthDay = 0;
  private List _calendarOrderDates = new ArrayList();
  // -------------------------------------------------------- Interface Methods
//  public String getScheduleType() {return _scheduleType;}
//  public void setScheduleType(String pValue) {_scheduleType = pValue;}

  public int getUserId() {return _userId;}
  public void setUserId(int pValue) {_userId = pValue;}

  public int getCatalogId() {return _catalogId;}
  public void setCatalogId(int pValue) {_catalogId = pValue;}

//  public String getAccountName() {return _accountName;}
//  public void setAccountName(String pValue) {_accountName = pValue;}

//  public int getSiteId() {return _siteId;}
//  public void setSiteId(int pValue) {_siteId = pValue;}

//  public String getSiteName() {return _siteName;}
//  public void setSiteName(String pValue) {_siteName = pValue;}

  public UserDataVector getUsers() {return _users;}
  public void setUsers(UserDataVector pValue) {_users = pValue;}

/*
  public String getOrderScheduleName(int ind) {
    if(ind <0 || ind>=_orderSchedules.size()) return "";
    OrderScheduleView osv = (OrderScheduleView) _orderSchedules.get(ind);
    String ss = "";
    ss += osv.getOrderGuideName();
    ss+="   ( ";
    Date dd = osv.getEffDate();
    ss+=Constants.date2string(dd);
    ss+="    -   ";
    dd = osv.getExpDate();
    if(dd==null){
      ss+=" ... ";
    } else {
      ss+=Constants.date2string(dd);
    }
    ss += " )";
    return ss;
  }
*/
  public String getOrderScheduleId(int ind) {
    if(ind <0 || ind>=_orderSchedules.size()) return "";
    OrderScheduleView osv = (OrderScheduleView) _orderSchedules.get(ind);
    String ss = ""+osv.getOrderScheduleId();
    return ss;
  }

  public int getNextOrderIndex() {return _nextOrderIndex;}
  public void setNextOrderIndex(int pValue) {_nextOrderIndex = pValue;}


  //Calendar
//  public int getOrderScheduleId() {return _orderScheduleId;}
//  public void setOrderScheduleId(int pValue) {_orderScheduleId = pValue;}

  public int getNextOrderRelMonth() {return _nextOrderRelMonth;}
  public void setNextOrderRelMonth(int pValue) {_nextOrderRelMonth = pValue;}

  public int getNextOrderMonthDay() {return _nextOrderMonthDay;}
  public void setNextOrderMonthDay(int pValue) {_nextOrderMonthDay = pValue;}

  public List getCalendarOrderDates() {return _calendarOrderDates;}
  public void setCalendarOrderDates(List pValue) {_calendarOrderDates = pValue;}

  public GregorianCalendar getCalendar() {return _calendar;}
  public void setCalendar(GregorianCalendar pValue) {_calendar = pValue;}

  public int getDay(int relativeMonth, int weekNum, int weekDay) {
    GregorianCalendar calendar = (GregorianCalendar) _calendar.clone();
    if(relativeMonth!=_lastReqMonth) {
      calendar.add(GregorianCalendar.MONTH,relativeMonth);
      _lastReqFirstWeekDay = calendar.get(GregorianCalendar.DAY_OF_WEEK);
      calendar.add(GregorianCalendar.MONTH,1);
      calendar.add(GregorianCalendar.DAY_OF_MONTH,-1);
      _lastReqMonthDay = calendar.get(GregorianCalendar.DAY_OF_MONTH);
    }
    int req = weekNum*7+weekDay;
    int res = req-_lastReqFirstWeekDay+1;
    if(res>_lastReqMonthDay) res = -1;

    if(relativeMonth==_nextOrderRelMonth && res==_nextOrderMonthDay) {
      res *= 100;
      _nextOrderIndex++;
      if(_nextOrderIndex<_calendarOrderDates.size()) {
      GregorianCalendar gc = (GregorianCalendar) _calendarOrderDates.get(_nextOrderIndex);
        _nextOrderRelMonth =
          gc.get(GregorianCalendar.YEAR)*12+gc.get(GregorianCalendar.MONTH)-
          (_calendar.get(GregorianCalendar.YEAR)*12+_calendar.get(GregorianCalendar.MONTH));
        _nextOrderMonthDay = gc.get(GregorianCalendar.DAY_OF_MONTH);
      } else {
        _nextOrderRelMonth = -1;
        _nextOrderMonthDay = 0;
      }
    }
    return res;
  }
  public String getMonth(int relativeMonth){
    int month = _calendar.get(GregorianCalendar.MONTH);
    month += relativeMonth;
    month %= 12;
    String name = "";
    switch(month) {
      case 0: name = "January"; break;
      case 1: name = "February"; break;
      case 2: name = "March"; break;
      case 3: name = "April"; break;
      case 4: name = "May"; break;
      case 5: name = "June"; break;
      case 6: name = "July"; break;
      case 7: name = "August"; break;
      case 8: name = "September"; break;
      case 9: name = "October"; break;
      case 10: name = "November"; break;
      case 11: name = "December"; break;
    }
    return name;
  }


  //Start date , end date
  public String getStartDate() {return _startDate;}
  public void setStartDate(String pValue) {_startDate = pValue;}

  public String getEndDate() {return _endDate;}
  public void setEndDate(String pValue) {_endDate = pValue;}

//  public String getScheduleAction() {return _scheduleAction;}
//  public void setScheduleAction(String pValue) {_scheduleAction = pValue;}

  //Week schdule
  public String getWeekCycle() {return _weekCycle;}
  public void setWeekCycle(String pValue) {_weekCycle = pValue;}

  public int[] getWeekDays() {return _weekDays;}
  public void setWeekDays(int[] pValue) {_weekDays = pValue;}

  //Month day schdule
  public String getMonthDayCycle() {return _monthDayCycle;}
  public void setMonthDayCycle(String pValue) {_monthDayCycle = pValue;}

  public int[] getMonthDays() {return _monthDays;}
  public void setMonthDays(int[] pValue) {_monthDays = pValue;}

  //Month week schdule
  public String getMonthWeekCycle() {return _monthWeekCycle;}
  public void setMonthWeekCycle(String pValue) {_monthWeekCycle = pValue;}

  public int getMonthWeekDay() {return _monthWeekDay;}
  public void setMonthWeekDay(int pValue) {_monthWeekDay = pValue;}

  public int[] getMonthWeeks() {return _monthWeeks;}
  public void setMonthWeeks(int[] pValue) {_monthWeeks = pValue;}


  //Excepitons
  public String getAlsoDates() {return _alsoDates;}
  public void setAlsoDates(String pValue) {_alsoDates = pValue;}

  public String getExcludeDates() {return _excludeDates;}
  public void setExcludeDates(String pValue) {_excludeDates = pValue;}

  //CC Email
  public String getCcEmail() {return _ccEmail;}
  public void setCcEmail(String pValue) {_ccEmail = pValue;}

  //Order guides
//  public int getOrderGuideId(){return _orderGuideId;}
//  public void setOrderGuideId(int pValue){_orderGuideId = pValue;}

  //User order quides
  public OrderGuideDataVector getUserOrderGuides() {return _userOrderGuides;}
  public void setUserOrderGuides(OrderGuideDataVector pValue) {_userOrderGuides = pValue;}

  public ArrayList getUserOrderGuideIds() {
    ArrayList al = new ArrayList();
    al.add("-1");
    if(_userOrderGuides == null) return al;
    for(int ii=0; ii<_userOrderGuides.size(); ii++) {
      OrderGuideData ogD = (OrderGuideData) _userOrderGuides.get(ii);
      al.add(""+ogD.getOrderGuideId());
    }
    return al;
  }
  public ArrayList getUserOrderGuideNames() {
    ArrayList al = new ArrayList();
    al.add("-- User's Order Guides --");
    if(_userOrderGuides == null) return al;
    for(int ii=0; ii<_userOrderGuides.size(); ii++) {
      OrderGuideData ogD = (OrderGuideData) _userOrderGuides.get(ii);
      al.add(ogD.getShortDesc());
    }
    return al;
  }

  public int getUserOrderGuideNumber() {
    if(_userOrderGuides == null) return 0;
    return _userOrderGuides.size();
  }

  //Template order guides
  public OrderGuideDataVector getTemplateOrderGuides() {return _templateOrderGuides;}
  public void setTemplateOrderGuides(OrderGuideDataVector pValue) {_templateOrderGuides = pValue;}
  public ArrayList getTemplateOrderGuideIds() {
    ArrayList al = new ArrayList();
    al.add("-1");
    if(_templateOrderGuides == null) return al;
    for(int ii=0; ii<_templateOrderGuides.size(); ii++) {
      OrderGuideData ogD = (OrderGuideData) _templateOrderGuides.get(ii);
      al.add(""+ogD.getOrderGuideId());
    }
    return al;
  }

  public ArrayList getTemplateOrderGuideNames() {
    ArrayList al = new ArrayList();
    al.add("-- Template Order Guides --");
    if(_templateOrderGuides == null) return al;
    for(int ii=0; ii<_templateOrderGuides.size(); ii++) {
      OrderGuideData ogD = (OrderGuideData) _templateOrderGuides.get(ii);
      al.add(ogD.getShortDesc());
    }
    return al;
  }
  public int getTemplateOrderGuideNumber() {
    if(_templateOrderGuides == null) return 0;
    return _templateOrderGuides.size();
  }
////////////////////////////////////////

    /**
     *  <code>reset</code> method, set the search fiels to null.
     *
     *@param  mapping  an <code>ActionMapping</code> value
     *@param  request  a <code>HttpServletRequest</code> value
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
    }


    /**
     *  <code>validate</code> method is a stub.
     *
     *@param  mapping  an <code>ActionMapping</code> value
     *@param  request  a <code>HttpServletRequest</code> value
     *@return          an <code>ActionErrors</code> value
     */
    public ActionErrors validate(ActionMapping mapping,
            HttpServletRequest request) {
        // No validation necessary.
        return null;
    }

}

