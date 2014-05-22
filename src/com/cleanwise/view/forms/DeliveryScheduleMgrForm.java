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
 *  Form bean for the Delivery scheduler manager configuration page.
 *
 *@author     Yuriy Kupershmidt
 *@created    October 14, 2001
 */
public final class DeliveryScheduleMgrForm extends ActionForm {
   //Page control
   private String _contentPage = "";
   public void setContentPage(String pValue) {_contentPage = pValue;}
   public String getContentPage(){return _contentPage;}

   //Main page fields
   private int _distId = 0;
   public void setDistId(int pValue) {_distId = pValue;}
   public int getDistId(){return _distId;}

   //Search parameters
   private String _searchId = "";
   public void setSearchId(String pValue) {_searchId = pValue;}
   public String getSearchId(){return _searchId;}

   private String _searchName = "";
   public void setSearchName(String pValue) {_searchName = pValue;}
   public String getSearchName(){return _searchName;}

   private int _searchType = SearchCriteria.BEGINS_WITH_IGNORE_CASE;
   public void setSearchType(int pValue) {_searchType = pValue;}
   public int getSearchType(){return _searchType;}
   
   private String _searchZipCode = "";
   public void setSearchZipCode(String pValue) {_searchZipCode = pValue;}
   public String getSearchZipCode(){return _searchZipCode;}
   
   private String _searchState = "";
   public void setSearchState(String pValue) {_searchState = pValue;}
   public String getSearchState(){return _searchState;}
   
   private String _searchCounty = "";
   public void setSearchCounty(String pValue) {_searchCounty = pValue;}
   public String getSearchCounty(){return _searchCounty;}

   //Search result
   private DeliveryScheduleViewVector _scheduleList = null;
   public void setScheduleList(DeliveryScheduleViewVector pValue) {_scheduleList = pValue;}
   public DeliveryScheduleViewVector getScheduleList(){return _scheduleList;}

   
   //Detail data
   private ScheduleJoinView _scheduleJoinData = null;
   public void setScheduleJoinData(ScheduleJoinView pValue) {_scheduleJoinData = pValue;}
   public ScheduleJoinView getScheduleJoinData(){return _scheduleJoinData;}

   //private ScheduleData _scheduleData = null;
   public ScheduleData getScheduleData(){
     if(_scheduleJoinData==null) return null;
     return _scheduleJoinData.getSchedule();
   }
 
   private String _cutoffTime="12:00 pm";
   public void setCutoffTime(String pValue) {_cutoffTime = pValue;}
   public String getCutoffTime(){return _cutoffTime;}
   
   
   private String _cutoffDay = "2"; //Number of days
   public void setCutoffDay(String pValue) {_cutoffDay = pValue;}
   public String getCutoffDay(){return _cutoffDay;}
   
   private String _nextDeliveryDate = "";
   public void setNextDeliveryDate(String pValue) {_nextDeliveryDate = pValue;}
   public String getNextDeliveryDate(){return _nextDeliveryDate;}

   private String _nextCutOffDate = "";
   public void setNextCutOffDate(String pValue) {_nextCutOffDate = pValue;}
   public String getNextCutOffDate(){return _nextCutOffDate;}
   
   // -------------------------------------------------------- Instance Variables
   //private CityPostalCodeDataVector _postalCodes = new CityPostalCodeDataVector();

   private String _effDate = null;
   public String getEffDate() {return _effDate;}
   public void setEffDate(String pValue) {_effDate = pValue;}

   private String _expDate = null;
   public String getExpDate() {return _expDate;}
   public void setExpDate(String pValue) {_expDate = pValue;}

  

   private String _weekCycle = "1";
   private int[] _weekDays = new int[0];  // Sunday - 1, ... Saturday - 6

   private String _monthDayCycle = "1";
   private int[] _monthDays = new int[0]; //1 ... 28

   private String _monthWeekCycle = "1";
   private int _monthWeekDay = 2; // Sunday - 1, ... Saturday - 7, day - 8
   private int[] _monthWeeks = new int[0]; //first -1, second -2, third -3, forth -4, last -5

   private String _alsoDates = "";
   private String _excludeDates = "";

  //private int _monthWeekFrequency = 0;

  private GregorianCalendar _calendar = null;
  private int _lastReqMonth = -1;
  private int _lastReqMonthDay = -1;
  private int _lastReqFirstWeekDay = -1;
  private int _nextScheduleIndex = 0;
  private int _nextScheduleRelMonth = 0;
  private int _nextScheduleMonthDay = 0;
  private List _calendarScheduleDates = new ArrayList();

  // -------------------------------------------------------- Interface Methods
/*
  public String getOrderScheduleId(int ind) {
    if(ind <0 || ind>=_orderSchedules.size()) return "";
    OrderScheduleView osv = (OrderScheduleView) _orderSchedules.get(ind);
    String ss = ""+osv.getOrderScheduleId();
    return ss;
  }
*/
 
  public int getNextScheduleIndex() {return _nextScheduleIndex;}
  public void setNextScheduleIndex(int pValue) {_nextScheduleIndex = pValue;}

  public int getNextScheduleRelMonth() {return _nextScheduleRelMonth;}
  public void setNextScheduleRelMonth(int pValue) {_nextScheduleRelMonth = pValue;}

  public int getNextScheduleMonthDay() {return _nextScheduleMonthDay;}
  public void setNextScheduleMonthDay(int pValue) {_nextScheduleMonthDay = pValue;}

  public List getCalendarScheduleDates() {return _calendarScheduleDates;}
  public void setCalendarScheduleDates(List pValue) {_calendarScheduleDates = pValue;}

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

    if(relativeMonth==_nextScheduleRelMonth && res==_nextScheduleMonthDay) {
      res *= 100;
      _nextScheduleIndex++;
      if(_nextScheduleIndex<_calendarScheduleDates.size()) {
      GregorianCalendar gc = (GregorianCalendar) _calendarScheduleDates.get(_nextScheduleIndex);
        _nextScheduleRelMonth =
          gc.get(GregorianCalendar.YEAR)*12+gc.get(GregorianCalendar.MONTH)-
          (_calendar.get(GregorianCalendar.YEAR)*12+_calendar.get(GregorianCalendar.MONTH));
        _nextScheduleMonthDay = gc.get(GregorianCalendar.DAY_OF_MONTH);
      } else {
        _nextScheduleRelMonth = -1;
        _nextScheduleMonthDay = 0;
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

  //private int _lastReqMonth = -1;
  public int getLastReqMonth() {return _lastReqMonth;}
  public void setLastReqMonth(int pValue) {_lastReqMonth = pValue;}

  //private int _lastReqMonthDay = -1;
  public int getLastReqMonthDay() {return _lastReqMonthDay;}
  public void setLastReqMonthDay(int pValue) {_lastReqMonthDay = pValue;}

  //private int _lastReqFirstWeekDay = -1;
  public int getLastReqFirstWeekDay() {return _lastReqFirstWeekDay;}
  public void setLastReqFirstWeekDay(int pValue) {_lastReqFirstWeekDay = pValue;}

   //--------------------------------------------------------- Configuration
   private String _actConfigType = "";
   public String getActConfigType() {return _actConfigType;}
   public void setActConfigType(String pValue) {_actConfigType = pValue; }
   
   private String _configType = "County";
   public String getConfigType() {return _configType;}
   public void setConfigType(String pValue) {_configType = pValue; }
   
   private String _servicedOnly = "false";
   public String getServicedOnly() {return _servicedOnly;}
   public void setServicedOnly(String pValue) {_servicedOnly = pValue; }

   private String _servicedOnlySave = "false";
   public String getServicedOnlySave() {return _servicedOnlySave;}
   public void setServicedOnlySave(String pValue) {_servicedOnlySave = pValue; }

   private BusEntityTerrViewVector _counties = new BusEntityTerrViewVector();
   public void setCounties (BusEntityTerrViewVector pVal) {_counties = pVal;}
   public BusEntityTerrViewVector getCounties() {return _counties;}

   private BusEntityTerrViewVector _postalCodes = new BusEntityTerrViewVector();
   public void setPostalCodes (BusEntityTerrViewVector pVal) {_postalCodes = pVal;}
   public BusEntityTerrViewVector getPostalCodes() {return _postalCodes;}

   private String[] _selected = new String[0];
   public void setSelected(String[] pVal) {_selected = pVal;}
   public String[] getSelected() {return _selected;}



    /**
     *  <code>reset</code> method, set the search fiels to null.
     *
     *@param  mapping  an <code>ActionMapping</code> value
     *@param  request  a <code>HttpServletRequest</code> value
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        setServicedOnly("false");
        setSelected(new String[0]);
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

