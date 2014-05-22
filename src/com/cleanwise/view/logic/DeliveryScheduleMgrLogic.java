
package com.cleanwise.view.logic;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Date;
import java.util.Hashtable;
import java.util.GregorianCalendar;
import com.cleanwise.service.api.util.PasswordUtil;
import com.cleanwise.service.api.util.ScheduleProc;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.view.utils.*;
import com.cleanwise.view.forms.DeliveryScheduleMgrForm;
import com.cleanwise.view.forms.DistMgrDetailForm;
import com.cleanwise.service.api.*;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.DuplicateNameException;
import com.cleanwise.service.api.util.RefCodeNames;
import java.rmi.RemoteException;
import com.cleanwise.service.api.util.SearchCriteria;
import java.text.SimpleDateFormat;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

/**
 * <code>TRadintPartnerMgrLogic</code> implements the logic needed to
 * place orders scheduled for automatic processing.
 *
 *@author Yuriy Kupershmidt
 */
public class DeliveryScheduleMgrLogic {

    public static ActionErrors initSearch(HttpServletRequest request,
			    DeliveryScheduleMgrForm pForm)
    {
    ActionErrors ae = new ActionErrors();
      HttpSession session = request.getSession();
      DistMgrDetailForm mainForm = (DistMgrDetailForm) session.getAttribute("DIST_DETAIL_FORM");
      int distId = mainForm.getIntId();
      pForm.setDistId(distId);
      return ae;
    }
   //***********************************************************************
    public static ActionErrors clearDetail(HttpServletRequest request,
			    DeliveryScheduleMgrForm pForm)
    {
      ActionErrors ae = new ActionErrors();
      pForm.setCounties(new BusEntityTerrViewVector());
      pForm.setPostalCodes(new BusEntityTerrViewVector());
      return ae;
    }
   //***********************************************************************
  public static ActionErrors searchSchedule(HttpServletRequest request,
	    DeliveryScheduleMgrForm pForm)
  {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();

    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    if(factory==null) {
      ae.add("error",new ActionError("error.systemError","No Ejb access"));
      return ae;
    }
    Distributor distributorEjb = null;
    try {
      distributorEjb = factory.getDistributorAPI();
    } catch(com.cleanwise.service.api.APIServiceAccessException exc) {
      exc.printStackTrace();
      ae.add("error",new ActionError("error.systemError","No Distributor Ejb access"));
      return ae;
    }

    //Criteria: "STATE_PROVINCE_CD", "COUNTY_CD", "POSTAL_CD", "SHORT_DESC", "ID"
     Hashtable crit = new Hashtable();
     String searchState = pForm.getSearchState();
     if(searchState!=null && searchState.length()>0) {
       crit.put("STATE_PROVINCE_CD", searchState);
     }
     String searchName = pForm.getSearchName();
     if(searchName!=null && searchName.length()>0) {
       crit.put("SHORT_DESC", searchName);
     }
     String searchId = pForm.getSearchId();
     if(searchId!=null && searchId.length()>0) {
        crit.put("ID", searchId);
     }
     String searchCounty =  pForm.getSearchCounty();
     if(searchCounty!=null && searchCounty.length()>0) {
        crit.put("COUNTY_CD",searchCounty);
     }
     String searchZipCode =  pForm.getSearchZipCode();
     if(searchZipCode!=null && searchZipCode.length()>0) {
        crit.put("POSTAL_CD", searchZipCode);
     }
    
    int searchType = pForm.getSearchType();
    boolean beginsWithFl = false;
    if(searchType==SearchCriteria.BEGINS_WITH_IGNORE_CASE) beginsWithFl = true; 

    pForm.setScheduleList(null);
    try {
      DeliveryScheduleViewVector dsVv = distributorEjb.getDeliverySchedules(
        pForm.getDistId(), crit, beginsWithFl);
      pForm.setScheduleList(dsVv);
    } catch (Exception exc) {
      ae.add("error",new ActionError("error.systemError",exc.getMessage()));
      return ae;
    }
    
    return ae;
  }
   //***********************************************************************
  public static ActionErrors viewAll(HttpServletRequest request,
	    DeliveryScheduleMgrForm pForm)
  {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();

    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    if(factory==null) {
      ae.add("error",new ActionError("error.systemError","No Ejb access"));
      return ae;
    }
    Distributor distributorEjb = null;
    try {
      distributorEjb = factory.getDistributorAPI();
    } catch(com.cleanwise.service.api.APIServiceAccessException exc) {
      exc.printStackTrace();
      ae.add("error",new ActionError("error.systemError","No Distributor Ejb access"));
      return ae;
    }

     pForm.setScheduleList(null);
     try {
       DeliveryScheduleViewVector dsVv = distributorEjb.getDeliverySchedules(
          pForm.getDistId(), new Hashtable(), true);
      pForm.setScheduleList(dsVv);
    } catch (Exception exc) {
      ae.add("error",new ActionError("error.systemError",exc.getMessage()));
      return ae;
    }
    
    return ae;
  }
   //***********************************************************************
  public static ActionErrors detail(HttpServletRequest request,
	    DeliveryScheduleMgrForm pForm)
  {
      ActionErrors ae = detail(request, pForm,0);
      return ae;
  }    

  public static ActionErrors detail(HttpServletRequest request,
	    DeliveryScheduleMgrForm pForm, int scheduleId)
  {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    pForm.setCounties(new BusEntityTerrViewVector());
    pForm.setPostalCodes(new BusEntityTerrViewVector());
    if(scheduleId==0) {
      String scheduleIdS = request.getParameter("scheduleId");
      try {
        scheduleId = Integer.parseInt(scheduleIdS);
      } catch(NumberFormatException exc) {}
      if(scheduleId<=0) {
        ae.add("error",new ActionError("error.systemError","Wrong scheduleId format: "+scheduleIdS));
        return ae;
      }
    }
    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    if(factory==null) {
      ae.add("error",new ActionError("error.systemError","No Ejb access"));
      return ae;
    }
    Distributor distributorEjb = null;
    try {
      distributorEjb = factory.getDistributorAPI();
    } catch(com.cleanwise.service.api.APIServiceAccessException exc) {
      exc.printStackTrace();
      ae.add("error",new ActionError("error.systemError","No distributor Ejb access"));
      return ae;
    }
    ScheduleJoinView scheduleJoin = null;
    try {
       scheduleJoin = distributorEjb.getDeliveryScheduleById(scheduleId);
    } catch (Exception exc) {
      ae.add("error",new ActionError("error.systemError",exc.getMessage()));
      return ae;
    }
    pForm.setScheduleJoinData(scheduleJoin);
    ae = prepareScheduele(pForm);
    if(ae.size()>0) return ae;
    
    ae = showDates(request, pForm);
    if(ae.size()>0) return ae;
    
    return ae;
  }
//--------------------------------------------------------------------------------
  private static ActionErrors prepareScheduele(DeliveryScheduleMgrForm pForm) 
  {
    ActionErrors ae = new ActionErrors();
    ScheduleJoinView scheduleJoin = pForm.getScheduleJoinData();
    ScheduleData sD = scheduleJoin.getSchedule();
    ScheduleDetailDataVector scheduleDetailDV = scheduleJoin.getScheduleDetail();
    String weekCycle = "";
    String monthCycle = "";
    String monthWeekCycle = "";
    String alsoDates = "";
    String excludeDates = "";
    String cutOffDay = "";
    String cutOffTime = "";
    ArrayList weekDays = new ArrayList();
    ArrayList monthDays = new ArrayList();
    ArrayList monthWeeks = new ArrayList();
    int monthWeekDay = 0;
    for(int ii=0; ii<scheduleDetailDV.size(); ii++) {
      ScheduleDetailData sdD = (ScheduleDetailData) scheduleDetailDV.get(ii);
      String detType = sdD.getScheduleDetailCd();
      String value = sdD.getValue();
        if(detType.equals(RefCodeNames.SCHEDULE_DETAIL_CD.ALSO_DATE)){
          if(alsoDates.length()>0) alsoDates += ", ";
          alsoDates += value;
        } else if(detType.equals(RefCodeNames.SCHEDULE_DETAIL_CD.EXCEPT_DATE)){
          if(excludeDates.length()>0) excludeDates += ", ";
          excludeDates += value;
        } else if(detType.equals(RefCodeNames.SCHEDULE_DETAIL_CD.CUTOFF_DAY)){
          if(cutOffDay.length()>0) {
            ae.add("error",new ActionError("error.simpleGenericError","Found extra cut off day: "+value));
          } else {
            cutOffDay = value;
          }
        } else if(detType.equals(RefCodeNames.SCHEDULE_DETAIL_CD.CUTOFF_TIME)){
          if(cutOffTime.length()>0) {
            ae.add("error",new ActionError("error.simpleGenericError","Found extra cut off time: "+value));
          } else {
            cutOffTime = value;
          }
        } else if(detType.equals(RefCodeNames.SCHEDULE_DETAIL_CD.MONTH_DAY)){
           monthDays.add(value);
        } else if(detType.equals(RefCodeNames.SCHEDULE_DETAIL_CD.MONTH_WEEK)){
           monthWeeks.add(value);
        } else if(detType.equals(RefCodeNames.SCHEDULE_DETAIL_CD.WEEK_DAY)){
           weekDays.add(value);
        }
    }
    pForm.setCutoffDay(cutOffDay);
    pForm.setCutoffTime(cutOffTime);
    pForm.setAlsoDates(alsoDates);
    pForm.setExcludeDates(excludeDates);
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    Date effDate = sD.getEffDate();
    if(effDate!=null) {
       pForm.setEffDate(sdf.format(effDate));
    } else {
       pForm.setEffDate(null);
    }
    Date expDate = sD.getExpDate();
    if(expDate!=null) {
       pForm.setExpDate(sdf.format(expDate));
    } else {
       pForm.setExpDate(null);
    }

    
    pForm.setWeekDays(null);
    pForm.setMonthDays(null);
    pForm.setMonthWeeks(null);
    pForm.setMonthWeekDay(0);
    
    String rule = sD.getScheduleRuleCd();
    if(RefCodeNames.SCHEDULE_RULE_CD.WEEK.equals(rule)){
      weekCycle += sD.getCycle();
      pForm.setWeekCycle(String.valueOf(sD.getCycle()));
      int size = weekDays.size();
      int[] weekDayA = new int[size];
      for(int ii=0; ii<size; ii++) {
        int wd = 0;
        try{
          wd = Integer.parseInt((String) weekDays.get(ii));
        }catch (Exception exc) {}
        weekDayA[ii] = wd;
      }
      pForm.setWeekDays(weekDayA);
    } else if(RefCodeNames.SCHEDULE_RULE_CD.DAY_MONTH.equals(rule)){
      monthCycle += sD.getCycle();
      int size = monthDays.size();
      int[] monthDayA = new int[size];
      for(int ii=0; ii<size; ii++) {
        int md = 0;
        try{
          md = Integer.parseInt((String) monthDays.get(ii));
        }catch (Exception exc) {}
        monthDayA[ii] = md ;
      }
      pForm.setMonthDays(monthDayA);
    } else if(RefCodeNames.SCHEDULE_RULE_CD.WEEK_MONTH.equals(rule)){
      monthWeekCycle += sD.getCycle();
      int size = monthWeeks.size();
      int[] monthWeekA = new int[size];
      for(int ii=0; ii<size; ii++) {
        int mw = 0;
        try{
          mw = Integer.parseInt((String) monthWeeks.get(ii));
        }catch (Exception exc) {}
        monthWeekA[ii] = mw;
      }
      pForm.setMonthWeeks(monthWeekA);
      monthWeekDay = 0;
      if(weekDays.size()>0) {
        try{
          String wdS = (String) weekDays.get(0);
          monthWeekDay = Integer.parseInt(wdS);
        } catch (Exception exc) {}
      }
      pForm.setMonthWeekDay(monthWeekDay);
    }
    return ae;
  }
  //******************************************************************************
  private static ActionErrors prepareSchedule (HttpServletRequest request, DeliveryScheduleMgrForm pForm)
  {
    ActionErrors ae = new ActionErrors();
/*
    OrderScheduleJoin  odrderSchedule = pForm.getOrderSchedule();
    pForm.setStartDate(Constants.date2string(odrderSchedule.getEffDate()));
    Date endDate = odrderSchedule.getExpDate();
    if(endDate==null) {
      pForm.setEndDate("");
    } else {
      pForm.setEndDate(Constants.date2string(odrderSchedule.getExpDate()));
    }
    pForm.setCcEmail(odrderSchedule.getCcEmail());
//    pForm.setScheduleType(odrderSchedule.getOrderScheduleRuleCd());
    if(RefCodeNames.ORDER_SCHEDULE_RULE_CD.WEEK.equals(odrderSchedule.getOrderScheduleRuleCd())){
      pForm.setWeekCycle(""+odrderSchedule.getCycle());
      pForm.setWeekDays(odrderSchedule.getElements());
    } else {
      pForm.setWeekCycle("1");
      pForm.setWeekDays(new int[0]);
    }
    if(RefCodeNames.ORDER_SCHEDULE_RULE_CD.DAY_MONTH.equals(odrderSchedule.getOrderScheduleRuleCd())){
      pForm.setMonthDayCycle(""+odrderSchedule.getCycle());
      pForm.setMonthDays(odrderSchedule.getElements());
    } else {
      pForm.setMonthDayCycle("1");
      pForm.setMonthDays(new int[0]);
    }
    if(RefCodeNames.ORDER_SCHEDULE_RULE_CD.WEEK_MONTH.equals(odrderSchedule.getOrderScheduleRuleCd())){
      pForm.setMonthWeekCycle(""+odrderSchedule.getCycle());
      pForm.setMonthWeekDay(odrderSchedule.getMonthWeekDay());
      pForm.setMonthWeeks(odrderSchedule.getElements());
    } else {
      pForm.setMonthWeekCycle("1");
      pForm.setMonthWeekDay(2);
      pForm.setMonthWeeks(new int[0]);
    }
    String alsoS = "";
    Date[] alsoDates = odrderSchedule.getAlsoDates();
    for(int jj=0; jj<alsoDates.length; jj++) {
      Date dd = alsoDates[jj];
      if(dd!=null) {
        if(alsoS.length()>0) {
          alsoS += ", ";
        }
        alsoS += Constants.date2string(dd);
      }
    }
    pForm.setAlsoDates(alsoS);
    String exceptS = "";
    Date[] exceptDates = odrderSchedule.getExceptDates();
    for(int jj=0; jj<exceptDates.length; jj++) {
      Date dd = exceptDates[jj];
      if(dd!=null) {
        if(exceptS.length()>0) {
          exceptS += ", ";
        }
        exceptS += Constants.date2string(dd);
      }
    }
    pForm.setExcludeDates(exceptS);
    // show on calendar
    ae = showDates(request,pForm);
    if(ae.size()>0) {
      return ae;
    }
 */
    return ae;
  }
  //--------------------------------------------------------------------------------------
  private static ActionErrors showDates(HttpServletRequest request, DeliveryScheduleMgrForm pForm)
  {
    ActionErrors ae = new ActionErrors();
    ScheduleData schedule = pForm.getScheduleData();
    Date curDate = Constants.getCurrentDate();
    GregorianCalendar calendar = new GregorianCalendar();
    calendar.setTime(curDate);
    calendar.add(GregorianCalendar.MONTH,-1);
    calendar.set(GregorianCalendar.DAY_OF_MONTH,1);
    pForm.setCalendar(calendar);
    GregorianCalendar endCalendar = (GregorianCalendar) calendar.clone();
    endCalendar.add(GregorianCalendar.MONTH,6);
    GregorianCalendar scheduleStart = Constants.createCalendar(pForm.getEffDate());
    if(scheduleStart==null) {
      ae.add("error",new ActionError("error.simpleGenericError","Wrong start date format: "+pForm.getEffDate()));
      return ae;
    }
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    pForm.setEffDate(sdf.format(scheduleStart.getTime()));
    
    GregorianCalendar scheduleEnd = null;
    String endDate = pForm.getExpDate();
    if(endDate == null || endDate.trim().length()==0) {
      scheduleEnd = (GregorianCalendar) scheduleStart.clone();
      scheduleEnd.add(GregorianCalendar.YEAR,100);
    } else {
      scheduleEnd = Constants.createCalendar(endDate);
      if(scheduleEnd==null) {
        ae.add("error",new ActionError("error.simpleGenericError","Wrong end date format: "+pForm.getExpDate()));
        return ae;
      }
      pForm.setExpDate(sdf.format(scheduleEnd.getTime()));
    }
    long calendarMills = calendar.getTime().getTime();
    List datesToShow = new ArrayList();
    String scheduleRule = schedule.getScheduleRuleCd();
    ScheduleJoinView sjVw = pForm.getScheduleJoinData();
    Date startDate = calendar.getTime();
    Date effDate = sjVw.getSchedule().getEffDate();
    if(effDate!=null && effDate.after(startDate)) startDate = effDate;
    //Weekly schedule
    if(RefCodeNames.SCHEDULE_RULE_CD.WEEK.equals(scheduleRule)) {
      ScheduleProc scheduleProc = new ScheduleProc(pForm.getScheduleJoinData(),null);
      try{
        scheduleProc.initSchedule();
        GregorianCalendar curDeliveryCal = 
            scheduleProc.getFirstDeliveryDate(startDate, endCalendar.getTime());
        while(curDeliveryCal!=null) {
          datesToShow.add(curDeliveryCal);
          curDeliveryCal = scheduleProc.getNextDeliveryDate();  
        }
      } catch (Exception exc) {
        exc.printStackTrace();
        ae.add("error",new ActionError("error.simpleGenericError",exc.getMessage()));
        return ae;
      }
      /*
      GregorianCalendar startWeek = (GregorianCalendar) scheduleStart.clone();
      int dayOfWeek = startWeek.get(GregorianCalendar.DAY_OF_WEEK);
      startWeek.add(GregorianCalendar.DATE,-dayOfWeek+1);
      long startMills = startWeek.getTime().getTime();
      long diff = calendarMills - startMills;
      String weekCycleS = pForm.getWeekCycle();
      int weekCycle = 0;
      try{
        weekCycle = Integer.parseInt(weekCycleS);
        if(weekCycle<=0) {
          ae.add("error",new ActionError("error.simpleGenericError","Weeks number format: "+weekCycleS));
          return ae;
        }
      } catch (NumberFormatException exc) {
        ae.add("error",new ActionError("error.simpleGenericError","Weeks number format: "+weekCycleS));
        return ae;
      }
      if(diff>0) {
        int nbWeeks = (int) (diff/(7*24*3600*1000));
        int nbCycles = nbWeeks/weekCycle;
        startWeek.add(GregorianCalendar.DATE,nbCycles*weekCycle*7);
      }
      //Generate dates
      GregorianCalendar wrkCalendar = (GregorianCalendar) startWeek.clone();
      int[] weekDays = pForm.getWeekDays();
      if(weekDays.length>0) {
        boolean finishFlag = false;
        boolean startFlag = false;
        while(!finishFlag) {
          for(int ii=0; ii<weekDays.length; ii++) {
            int wd = wrkCalendar.get(GregorianCalendar.DAY_OF_WEEK);
            wrkCalendar.add(GregorianCalendar.DATE,weekDays[ii]-wd);
            if(!startFlag) {
              if(!wrkCalendar.before(scheduleStart) && !wrkCalendar.before(calendar)) {
                startFlag=true;
              } else {
                continue;
              }
            }
            if(startFlag){
              if(!wrkCalendar.after(scheduleEnd) && !wrkCalendar.after(endCalendar)) {
                datesToShow.add(wrkCalendar.clone());
              } else {
                finishFlag=true;
              }
            }
          }
          wrkCalendar.add(GregorianCalendar.DATE,weekCycle*7);
        }
      }
      */
    } else {
      pForm.setWeekDays(new int[0]);
    }
    //Month day schedule
    if(RefCodeNames.SCHEDULE_RULE_CD.DAY_MONTH.equals(scheduleRule)) {
      int[] monthDays = pForm.getMonthDays();
      if(monthDays.length>0) {
        if(datesToShow.size()>0) {
          ae.add("error",new ActionError("error.simpleGenericError","Only one schedule is allowed" ));
          return ae;
        }
        GregorianCalendar startMonth = (GregorianCalendar) scheduleStart.clone();
        startMonth.set(GregorianCalendar.DAY_OF_MONTH,1);
        String monthDayCycleS = pForm.getMonthDayCycle();
        int monthDayCycle = 0;
        try{
          monthDayCycle = Integer.parseInt(monthDayCycleS);
          if(monthDayCycle<=0) {
            ae.add("error",new ActionError("error.simpleGenericError","Month number format: "+monthDayCycleS));
            return ae;
          }
        } catch (NumberFormatException exc) {
          ae.add("error",new ActionError("error.simpleGenericError","Month number format: "+monthDayCycleS));
          return ae;
        }
        //Determin date to start
        GregorianCalendar start = (GregorianCalendar) scheduleStart.clone();
        if(calendar.after(scheduleStart)) {
          start = (GregorianCalendar) calendar.clone();
          int mmStart = scheduleStart.get(GregorianCalendar.YEAR)*12+scheduleStart.get(GregorianCalendar.MONTH);
          int mmCalendar = calendar.get(GregorianCalendar.YEAR)*12+calendar.get(GregorianCalendar.MONTH);
          int diff1 = mmCalendar - mmStart;
          int nbCycles = diff1/monthDayCycle;
          int nbMonths = nbCycles*monthDayCycle;
          startMonth.add(GregorianCalendar.MONTH,nbMonths);
        }
        //End date
        GregorianCalendar end = (GregorianCalendar) scheduleEnd.clone();
        if(endCalendar.before(end)) {
          end = (GregorianCalendar) endCalendar.clone();
        }
        //Dates
        GregorianCalendar gc = (GregorianCalendar) startMonth.clone();
        boolean startFlag = false;
        boolean finishFlag = false;
        while(!finishFlag) {
          for(int ii=0; ii<monthDays.length; ii++) {
            if(monthDays[ii]<=28) {
              gc.set(GregorianCalendar.DAY_OF_MONTH,monthDays[ii]);
            } else {
              gc.set(GregorianCalendar.DAY_OF_MONTH,1);
              gc.add(GregorianCalendar.MONTH,1);
              gc.add(GregorianCalendar.DAY_OF_MONTH,-1);
            }
            if(!startFlag) {
              if(!start.after(gc)) {
                startFlag = true;
              }
            }
            if(startFlag) {
              if(!gc.after(end)) {
                datesToShow.add(gc.clone());
              } else {
                finishFlag = true;
                break;
              }
            }
          }
          gc.set(GregorianCalendar.DAY_OF_MONTH,1);
          gc.add(GregorianCalendar.MONTH,monthDayCycle);
        }
      }
    } else {
      pForm.setMonthDays(new int[0]);
    }
    //Month week schedule
    if(RefCodeNames.SCHEDULE_RULE_CD.WEEK_MONTH.equals(scheduleRule)) {
      ScheduleProc scheduleProc = new ScheduleProc(pForm.getScheduleJoinData(),null);
      try{
        scheduleProc.initSchedule();
        GregorianCalendar curDeliveryCal = 
            scheduleProc.getFirstDeliveryDate(startDate, endCalendar.getTime());
        while(curDeliveryCal!=null) {
          datesToShow.add(curDeliveryCal);
          curDeliveryCal = scheduleProc.getNextDeliveryDate();  
        }
      } catch (Exception exc) {
        exc.printStackTrace();
        ae.add("error",new ActionError("error.simpleGenericError",exc.getMessage()));
        return ae;
      }
/*
      int[] monthWeeks = pForm.getMonthWeeks();
      if(monthWeeks.length>0) {
        if(datesToShow.size()>0) {
          ae.add("error",new ActionError("error.simpleGenericError","Only one schedule is  allowed" ));
          return ae;
        }
        GregorianCalendar startMonth = (GregorianCalendar) scheduleStart.clone();
        startMonth.set(GregorianCalendar.DAY_OF_MONTH,1);
        String monthWeekCycleS = pForm.getMonthWeekCycle();
        int monthWeekCycle = 0;
        try{
          monthWeekCycle = Integer.parseInt(monthWeekCycleS);
          if(monthWeekCycle<=0) {
            ae.add("error",new ActionError("error.simpleGenericError","Month number format: "+monthWeekCycleS));
            return ae;
          }
        } catch (NumberFormatException exc) {
          ae.add("error",new ActionError("error.simpleGenericError","Month number format: "+monthWeekCycleS));
          return ae;
        }
        //Determin date to start
        GregorianCalendar start = (GregorianCalendar) scheduleStart.clone();
        if(calendar.after(scheduleStart)) {
          start = (GregorianCalendar) calendar.clone();
          int mmStart = scheduleStart.get(GregorianCalendar.YEAR)*12+scheduleStart.get(GregorianCalendar.MONTH);
          int mmCalendar = calendar.get(GregorianCalendar.YEAR)*12+calendar.get(GregorianCalendar.MONTH);
          int diff1 = mmCalendar - mmStart;
          int nbCycles = diff1/monthWeekCycle;
          int nbMonths = nbCycles*monthWeekCycle;
          startMonth.add(GregorianCalendar.MONTH,nbMonths);
        }
        //End date
        GregorianCalendar end = (GregorianCalendar) scheduleEnd.clone();
        if(endCalendar.before(end)) {
          end = (GregorianCalendar) endCalendar.clone();
        }
        //Dates
        int monthWeekDay = pForm.getMonthWeekDay();
        GregorianCalendar gc = (GregorianCalendar) startMonth.clone();
        boolean startFlag = false;
        boolean finishFlag = false;
        while(!finishFlag) {
          int ww = gc.get(GregorianCalendar.DAY_OF_WEEK);
          if(ww>monthWeekDay) {
            gc.add(GregorianCalendar.DAY_OF_MONTH,7-ww+monthWeekDay);
          } else {
            gc.add(GregorianCalendar.DAY_OF_MONTH,monthWeekDay-ww);
          }
          for(int ii=0; ii<monthWeeks.length; ii++) {
            GregorianCalendar gc1 = (GregorianCalendar) gc.clone();
            gc1.add(GregorianCalendar.DAY_OF_MONTH,(monthWeeks[ii]-1)*7);
            if(monthWeeks[ii]>4) {//last week
              if(gc1.get(GregorianCalendar.MONTH)!=gc.get(GregorianCalendar.MONTH)) {
                 gc1.add(GregorianCalendar.DAY_OF_MONTH,-7);
              }
            }
            if(!startFlag) {
              if(!start.after(gc1)) {
                startFlag = true;
              }
            }
            if(startFlag) {
              if(!gc1.after(end)) {
                datesToShow.add(gc1.clone());
              } else {
                finishFlag = true;
                break;
              }
            }
          }
          gc.set(GregorianCalendar.DAY_OF_MONTH,1);
          gc.add(GregorianCalendar.MONTH,monthWeekCycle);
        }
      }
      */
    } else {
      pForm.setMonthWeeks(new int[0]);
    }
    int datesInd = datesToShow.size();
    //Additional days
    String alsoDates = pForm.getAlsoDates();
    if(alsoDates!=null && alsoDates.trim().length()>0) {
      int commaInd = 0;
      while(commaInd>=0) {
        int nextCommaInd = alsoDates.indexOf(',',commaInd);
        String dateS = null;
        if(nextCommaInd>0){
          dateS = alsoDates.substring(commaInd,nextCommaInd).trim();
          commaInd = nextCommaInd+1;
        } else {
          dateS = alsoDates.substring(commaInd).trim();
          commaInd = -1;
        }
        GregorianCalendar gc = Constants.createCalendar(dateS);
        if(gc==null) {
          ae.add("error",new ActionError("error.simpleGenericError","Wrong also place order on date format: "+dateS));
          return ae;
        }
        if(!(scheduleStart.after(gc)||scheduleEnd.before(gc))) {
          datesToShow.add(gc);
        }
      }
    }
    //Order dates
    Object[] datesArray = datesToShow.toArray();
    int size = datesArray.length;
    for(int ii=0; ii<size-1; ii++) {
      boolean finishFlag = true;
      for(int jj=1; jj<size-ii; jj++) {
        GregorianCalendar date1 = (GregorianCalendar) datesArray[jj-1];
        GregorianCalendar date2 = (GregorianCalendar) datesArray[jj];
        if(date1.after(date2)) {
          finishFlag = false;
          datesArray[jj-1] = date2;
          datesArray[jj] = date1;
        }
      }
      if(finishFlag) {
        break;
      }
    }
    //Exclude dates
    String excludeDates = pForm.getExcludeDates();
    List excludeDatesL = new ArrayList();
    if(excludeDates!=null && excludeDates.trim().length()>0) {
      int commaInd = 0;
      while(commaInd>=0) {
        int nextCommaInd = excludeDates.indexOf(',',commaInd);
        String dateS = null;
        if(nextCommaInd>0){
          dateS = excludeDates.substring(commaInd,nextCommaInd).trim();
          commaInd = nextCommaInd+1;
        } else {
          dateS = excludeDates.substring(commaInd).trim();
          commaInd = -1;
        }
        GregorianCalendar gc = Constants.createCalendar(dateS);
        if(gc==null) {
          ae.add("error",new ActionError("error.simpleGenericError","Wrong do not place order on date format: "+dateS));
          return ae;
        }
        int ii=0;
        for(; ii<size; ii++) {
          GregorianCalendar dd = (GregorianCalendar) datesArray[ii];
          if(gc.equals(dd)){
            excludeDatesL.add(dd.getTime());
            datesArray[ii] = null;
            break;
          }
        }
        for(ii++; ii<size; ii++) {
          GregorianCalendar dd = (GregorianCalendar) datesArray[ii];
          if(!gc.equals(dd)){
            break;
          }
          datesArray[ii] = null;
        }
      }
    }

    //Remove duplicates and nulls
    datesToShow = new ArrayList();
    GregorianCalendar prevDate = null;
    for(int ii=0; ii<size; ii++) {
      GregorianCalendar dd = (GregorianCalendar) datesArray[ii];
      if(dd==null || dd.equals(prevDate)){
        continue;
      }
      datesToShow.add(dd);
      prevDate = dd;
    }

    pForm.setCalendarScheduleDates(datesToShow);
    pForm.setNextScheduleIndex(0);
    if(datesToShow.size()>0) {
      GregorianCalendar gc = (GregorianCalendar) datesToShow.get(0);
      pForm.setNextScheduleRelMonth(
          gc.get(GregorianCalendar.YEAR)*12+gc.get(GregorianCalendar.MONTH)-
          (calendar.get(GregorianCalendar.YEAR)*12+calendar.get(GregorianCalendar.MONTH)));
      pForm.setNextScheduleMonthDay(gc.get(GregorianCalendar.DAY_OF_MONTH));
    } else {
      pForm.setNextScheduleRelMonth(-1);
      pForm.setNextScheduleMonthDay(0);
    }
 
   return ae;
  }
  //-------------------------------------------------------------------------------
  private static void initFields(DeliveryScheduleMgrForm pForm) {
    pForm.setCutoffDay("2");
    pForm.setCutoffTime("12:00 pm");
    pForm.setNextCutOffDate("");
    pForm.setNextDeliveryDate("");


    pForm.setEffDate(Constants.date2string(Constants.getCurrentDate()));
    pForm.setExpDate("");
    pForm.setWeekCycle("1");
    pForm.setWeekDays(new int[0]);
    pForm.setMonthDayCycle("1");
    pForm.setMonthDays(new int[0]);

    pForm.setMonthWeekCycle("1");
    pForm.setMonthWeekDay(2);
    pForm.setMonthWeeks(new int[0]);
    pForm.setAlsoDates("");
    pForm.setExcludeDates("");


    Date curDate = Constants.getCurrentDate();
    GregorianCalendar calendar = new GregorianCalendar();
    calendar.setTime(curDate);
    calendar.add(GregorianCalendar.MONTH,-1);
    calendar.set(GregorianCalendar.DAY_OF_MONTH,1);
    pForm.setCalendar(calendar);
    
    pForm.setLastReqMonth(-1);
    pForm.setLastReqMonthDay(-1);
    pForm.setLastReqFirstWeekDay(-1);
    pForm.setNextScheduleIndex(0);
    pForm.setNextScheduleRelMonth(0);
    pForm.setNextScheduleMonthDay(0);
    pForm.setCalendarScheduleDates(new ArrayList());
  }
   //***********************************************************************
   public static ActionErrors save(HttpServletRequest request,
			    DeliveryScheduleMgrForm pForm)
   {
       
    ActionErrors ae = new ActionErrors();
    //Save shcedule
    HttpSession session = request.getSession();
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    if(appUser==null) {
      ae.add("error",new ActionError("error.systemError","No "+Constants.APP_USER+"session object found"));
      return ae;
    }
    UserData user = appUser.getUser();

    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    if(factory==null) {
      ae.add("error",new ActionError("error.systemError","No Ejb access"));
      return ae;
    }
    Distributor distributorEjb = null;
    try {
      distributorEjb = factory.getDistributorAPI();
    } catch(com.cleanwise.service.api.APIServiceAccessException exc) {
      exc.printStackTrace();
      ae.add("error",new ActionError("error.systemError","No Distributor Ejb access"));
      return ae;
    }
    ScheduleData schedule = pForm.getScheduleData();
    //Name
    String name = schedule.getShortDesc();
    if(name==null || name.trim().length()==0) {
      ae.add("error",new ActionError("error.simpleGenericError","Empty schedule name"));
      return ae;
    }
    ScheduleDetailDataVector scheduleDetailDV = new ScheduleDetailDataVector();
    //Cutoff day
    String cutoffDayS = pForm.getCutoffDay();
    int cutoffDay = 0;
    try{
      cutoffDay = Integer.parseInt(cutoffDayS);
    }catch(Exception exp) {}
    if(cutoffDay<=0) {
      ae.add("error",new ActionError("error.simpleGenericError","Wrong Cutoff Day format: "+cutoffDayS));
      return ae;
    }
    ScheduleDetailData sdD = ScheduleDetailData.createValue();
    sdD.setScheduleDetailCd(RefCodeNames.SCHEDULE_DETAIL_CD.CUTOFF_DAY);
    sdD.setValue(""+cutoffDayS);
    scheduleDetailDV.add(sdD);
    //Cutoff time
    String cutoffTimeS = pForm.getCutoffTime();
    SimpleDateFormat sdfTime = new SimpleDateFormat("hh:mm a");
    Date cutoffTime = null;
    try{
      cutoffTime = sdfTime.parse(cutoffTimeS);
    }catch(Exception exc){}
    if(cutoffTime==null) {
      ae.add("error",new ActionError("error.simpleGenericError","Wrong Cutoff Time format: "+cutoffTimeS));
      return ae;
    }
    cutoffTimeS = sdfTime.format(cutoffTime);
    pForm.setCutoffTime(cutoffTimeS);
    sdD = ScheduleDetailData.createValue();
    sdD.setScheduleDetailCd(RefCodeNames.SCHEDULE_DETAIL_CD.CUTOFF_TIME);
    sdD.setValue(""+cutoffTimeS);
    scheduleDetailDV.add(sdD);
    
    
    //EffDate
    GregorianCalendar gc = Constants.createCalendar(pForm.getEffDate());
    schedule.setEffDate(gc.getTime());
    //ExpDate
    String endDate = pForm.getExpDate();
    if(endDate!=null &&  endDate.trim().length()>0) {
      gc = Constants.createCalendar(endDate);
      schedule.setExpDate(gc.getTime());
    }
    if(pForm.getWeekDays().length>0) {
      schedule.setScheduleRuleCd(RefCodeNames.SCHEDULE_RULE_CD.WEEK);
      schedule.setCycle(Integer.parseInt(pForm.getWeekCycle()));
      int[] weekDays = pForm.getWeekDays();
      for(int ii=0; ii<weekDays.length; ii++) {
        sdD = ScheduleDetailData.createValue();
        sdD.setScheduleDetailCd(RefCodeNames.SCHEDULE_DETAIL_CD.WEEK_DAY);
        sdD.setValue(""+weekDays[ii]);
        scheduleDetailDV.add(sdD);
      }
    }
    else if(pForm.getMonthDays().length>0) {
      schedule.setScheduleRuleCd(RefCodeNames.SCHEDULE_RULE_CD.DAY_MONTH);
      schedule.setCycle(Integer.parseInt(pForm.getMonthDayCycle()));
      int[] monthDays = pForm.getMonthDays();
      for(int ii=0; ii<monthDays.length; ii++) {
        sdD = ScheduleDetailData.createValue();
        sdD.setScheduleDetailCd(RefCodeNames.SCHEDULE_DETAIL_CD.MONTH_DAY);
        sdD.setValue(""+monthDays[ii]);
        scheduleDetailDV.add(sdD);
      }
    }
    else if(pForm.getMonthWeeks().length>0) {
      schedule.setScheduleRuleCd(RefCodeNames.SCHEDULE_RULE_CD.WEEK_MONTH);
      schedule.setCycle(Integer.parseInt(pForm.getMonthWeekCycle()));
      int[] monthWeeks = pForm.getMonthWeeks();
      for(int ii=0; ii<monthWeeks.length; ii++) {
        sdD = ScheduleDetailData.createValue();
        sdD.setScheduleDetailCd(RefCodeNames.SCHEDULE_DETAIL_CD.MONTH_WEEK);
        sdD.setValue(""+monthWeeks[ii]);
        scheduleDetailDV.add(sdD);
      }
      sdD = ScheduleDetailData.createValue();
      sdD.setScheduleDetailCd(RefCodeNames.SCHEDULE_DETAIL_CD.WEEK_DAY);
      sdD.setValue(""+pForm.getMonthWeekDay());
      scheduleDetailDV.add(sdD);
    }
    else {
      schedule.setScheduleRuleCd(RefCodeNames.SCHEDULE_RULE_CD.DATE_LIST);
    }
    //Additional days
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    String alsoDates = pForm.getAlsoDates();
    if(alsoDates!=null && alsoDates.trim().length()>0) {
      int commaInd = 0;
      while(commaInd>=0) {
        int nextCommaInd = alsoDates.indexOf(',',commaInd);
        String dateS = null;
        if(nextCommaInd>0){
          dateS = alsoDates.substring(commaInd,nextCommaInd).trim();
          commaInd = nextCommaInd+1;
        } else {
          dateS = alsoDates.substring(commaInd).trim();
          commaInd = -1;
        }
        gc = Constants.createCalendar(dateS);
        if(gc==null) {
          ae.add("error",new ActionError("error.simpleGenericError","Wrong also place order on date format: "+dateS));
          return ae;
        }
        sdD = ScheduleDetailData.createValue();
        sdD.setScheduleDetailCd(RefCodeNames.SCHEDULE_DETAIL_CD.ALSO_DATE);
        sdD.setValue(sdf.format(gc.getTime()));
        scheduleDetailDV.add(sdD);
      }
    }
    //Exclude dates
    String excludeDates = pForm.getExcludeDates();
    List excludeDatesL = new ArrayList();
    if(excludeDates!=null && excludeDates.trim().length()>0) {
      int commaInd = 0;
      while(commaInd>=0) {
        int nextCommaInd = excludeDates.indexOf(',',commaInd);
        String dateS = null;
        if(nextCommaInd>0){
          dateS = excludeDates.substring(commaInd,nextCommaInd).trim();
          commaInd = nextCommaInd+1;
        } else {
          dateS = excludeDates.substring(commaInd).trim();
          commaInd = -1;
        }
        gc = Constants.createCalendar(dateS);
        if(gc==null) {
          ae.add("error",new ActionError("error.simpleGenericError","Wrong do not place order on date format: "+dateS));
          return ae;
        }
        sdD = ScheduleDetailData.createValue();
        sdD.setScheduleDetailCd(RefCodeNames.SCHEDULE_DETAIL_CD.EXCEPT_DATE);
        sdD.setValue(sdf.format(gc.getTime()));
        scheduleDetailDV.add(sdD);
      }
    }
    try {
      ScheduleJoinView scheduleJ = distributorEjb.saveDeliverySchedule(schedule,scheduleDetailDV,user.getUserName());
      pForm.setScheduleJoinData(scheduleJ);
      schedule = scheduleJ.getSchedule();
    } catch (RemoteException exc) {
      ae.add("error",new ActionError("error.simpleGenericError",exc.getMessage()));
      return ae;
    }
    ae = detail(request,pForm,schedule.getScheduleId());
    if(ae.size()>0) return ae;
    ae = showDates(request,pForm);
    if(ae.size()>0) {
      return ae;
    }
   return ae;
  }
 //***********************************************************************
  public static ActionErrors createNew(HttpServletRequest request,
	    DeliveryScheduleMgrForm pForm)
  {
    ActionErrors ae = new ActionErrors();
    pForm.setCounties(new BusEntityTerrViewVector());
    pForm.setPostalCodes(new BusEntityTerrViewVector());
    pForm.setScheduleJoinData(null);
    HttpSession session = request.getSession();
    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    if(factory==null) {
      ae.add("error",new ActionError("error.systemError","No Ejb access"));
      return ae;
    }
    initFields(pForm);
    ScheduleData schedule = ScheduleData.createValue();
    schedule.setBusEntityId(pForm.getDistId());
    schedule.setScheduleTypeCd(RefCodeNames.SCHEDULE_TYPE_CD.DELIVERY);
    schedule.setScheduleRuleCd(RefCodeNames.SCHEDULE_RULE_CD.DAY_MONTH);
    schedule.setScheduleStatusCd(RefCodeNames.SCHEDULE_STATUS_CD.LIMITED);
    ScheduleJoinView scheduleJ = ScheduleJoinView.createValue();
    scheduleJ.setSchedule(schedule);
    pForm.setScheduleJoinData(scheduleJ);
    

    

 return ae;
  }
 //***********************************************************************
  public static ActionErrors delete(HttpServletRequest request,
	    DeliveryScheduleMgrForm pForm)
  {
    ActionErrors ae = new ActionErrors();
    //Save shcedule
    HttpSession session = request.getSession();

    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    if(factory==null) {
      ae.add("error",new ActionError("error.systemError","No Ejb access"));
      return ae;
    }
    Distributor distributorEjb = null;
    try {
      distributorEjb = factory.getDistributorAPI();
    } catch(com.cleanwise.service.api.APIServiceAccessException exc) {
      exc.printStackTrace();
      ae.add("error",new ActionError("error.systemError","No Distributor Ejb access"));
      return ae;
    }
    ScheduleData schedule = pForm.getScheduleData();
    int scheduleId = schedule.getScheduleId();
    if(scheduleId >0 )
      try {
        distributorEjb.deleteDeliverySchedule(scheduleId);
        pForm.setScheduleJoinData(null);
        DeliveryScheduleViewVector dsVV = pForm.getScheduleList();
        for(int ii=0; ii<dsVV.size(); ii++) {
          DeliveryScheduleView dsV = (DeliveryScheduleView) dsVV.get(ii);
          if(dsV.getScheduleId()==scheduleId){
            dsVV.remove(dsV);
            break;
          }
        }
        pForm.setScheduleList(dsVV);
      } catch (RemoteException exc) {
        ae.add("error",new ActionError("error.simpleGenericError",exc.getMessage()));
        return ae;
      }
    pForm.setCounties(new BusEntityTerrViewVector());
    pForm.setPostalCodes(new BusEntityTerrViewVector());
    return ae;
  }
 //***********************************************************************
  public static ActionErrors sort(HttpServletRequest request,
	    DeliveryScheduleMgrForm pForm)
  {
    ActionErrors ae = new ActionErrors();
    String fieldName = (String) request.getParameter("sortField");
    if(fieldName==null) {
      ae.add("error",new ActionError("error.systemError","No sort field name found"));
      return ae;
    }
    DeliveryScheduleViewVector dsVV = pForm.getScheduleList();
    dsVV.sort(fieldName);
    pForm.setScheduleList(dsVV);

 return ae;
  }
   //***********************************************************************
  public static ActionErrors searchTerritory(HttpServletRequest request,
	    DeliveryScheduleMgrForm pForm)
  {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();

    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    if(factory==null) {
      ae.add("error",new ActionError("error.systemError","No Ejb access"));
      return ae;
    }
    Distributor distributorEjb = null;
    try {
      distributorEjb = factory.getDistributorAPI();
    } catch(com.cleanwise.service.api.APIServiceAccessException exc) {
      exc.printStackTrace();
      ae.add("error",new ActionError("error.systemError","No Distributor Ejb access"));
      return ae;
    }

    //Criteria: "STATE_PROVINCE_CD", "COUNTY_CD", "POSTAL_CD", "SHORT_DESC", "ID"
     boolean emptyCritFl = true;
     Hashtable crit = new Hashtable();
     String searchState = pForm.getSearchState();
     if(searchState!=null && searchState.length()>0) {
       crit.put("STATE_PROVINCE_CD", searchState);
       emptyCritFl = false;
     }

     String searchCounty =  pForm.getSearchCounty();
     if(searchCounty!=null && searchCounty.length()>0) {
       crit.put("COUNTY_CD",searchCounty);
       emptyCritFl = false;
     }
     String searchZipCode =  pForm.getSearchZipCode();
     if(searchZipCode!=null && searchZipCode.length()>0) {
       searchZipCode = searchZipCode.trim();
       if(searchZipCode.length()<3) {
         ae.add("error",new ActionError("error.simpleGenericError","Zip coode must be at least 3 characters long"));
         return ae;
       }
       crit.put("POSTAL_CD", searchZipCode);
       emptyCritFl = false;
     }
    
    int searchType = pForm.getSearchType();
    boolean beginsWithFl = false;
    if(searchType==SearchCriteria.BEGINS_WITH_IGNORE_CASE) beginsWithFl = true; 

    String servicedOnlyS = pForm.getServicedOnly();
    pForm.setServicedOnlySave(servicedOnlyS);
    boolean servicedOnlyFl = false;
    if("true".equals(servicedOnlyS)) {
      servicedOnlyFl = true;
    }
    if(emptyCritFl && !servicedOnlyFl) {
      ae.add("error",new ActionError("error.simpleGenericError","Request is too broad"));
      return ae;
    }
    String configType = pForm.getConfigType();
    if("County".equals(configType)) {
    pForm.setCounties(new BusEntityTerrViewVector());
      try {
        BusEntityTerrViewVector terrVvV = distributorEjb.getDeliveryScheduleCounties(
            pForm.getScheduleData().getScheduleId(), crit, beginsWithFl,servicedOnlyFl);
        pForm.setCounties(terrVvV);
      } catch (Exception exc) {
        ae.add("error",new ActionError("error.systemError",exc.getMessage()));
        return ae;
      }
    } else {
      pForm.setPostalCodes(new BusEntityTerrViewVector());
      try {
        BusEntityTerrViewVector terrVvV = distributorEjb.getDeliveryScheduleZipCodes(
            pForm.getScheduleData().getScheduleId(), crit, beginsWithFl,servicedOnlyFl);
        pForm.setPostalCodes(terrVvV);
      } catch (Exception exc) {
        ae.add("error",new ActionError("error.systemError",exc.getMessage()));
        return ae;
      }
    }
    ae = showDates(request, pForm);
    if(ae.size()>0) return ae;
    pForm.setActConfigType(configType);
    resetSelected(pForm);
    return ae;
  }
  
   //***********************************************************************
  public static ActionErrors saveConfiguration(HttpServletRequest request,
	    DeliveryScheduleMgrForm pForm)
  {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();

    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    if(appUser==null) {
      ae.add("error",new ActionError("error.systemError","No "+Constants.APP_USER+"session object found"));
      return ae;
    }
    UserData user = appUser.getUser();

    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    if(factory==null) {
      ae.add("error",new ActionError("error.systemError","No Ejb access"));
      return ae;
    }
    Distributor distributorEjb = null;
    try {
      distributorEjb = factory.getDistributorAPI();
    } catch(com.cleanwise.service.api.APIServiceAccessException exc) {
      exc.printStackTrace();
      ae.add("error",new ActionError("error.systemError","No Distributor Ejb access"));
      return ae;
    }

    String actConfigType = pForm.getActConfigType();
    if("County".equals(actConfigType)) {
      try {
        BusEntityTerrViewVector betVV = pForm.getCounties();  
        for(int ii=0; ii<betVV.size(); ii++) {
          BusEntityTerrView betV = (BusEntityTerrView) betVV.get(ii);
          betV.setCheckedFl(false);
          String lCounty = betV.getCountyCd();
          String lStateCd = betV.getStateProvinceCd();
          String lCountry = betV.getCountryCd();
          String ss = lCounty+"^"+lStateCd+"^"+lCountry;    
          String[] selected = pForm.getSelected();
          for(int jj=0; jj<selected.length; jj++) {
              if(selected[jj].equals(ss)) {
              betV.setCheckedFl(true);
              break;
            }
          }
          
        }
        distributorEjb.updateDeliveryScheduleCounties(
                               pForm.getScheduleData().getScheduleId(), 
                               betVV,
                               user.getUserName());
      } catch (Exception exc) {
        ae.add("error",new ActionError("error.systemError",exc.getMessage()));
        return ae;
      }
    } else {
      try {
        BusEntityTerrViewVector betVV = pForm.getPostalCodes();  
        for(int ii=0; ii<betVV.size(); ii++) {
          BusEntityTerrView betV = (BusEntityTerrView) betVV.get(ii);
          betV.setCheckedFl(false);
          String lZip = betV.getPostalCode();
          String lCountry = betV.getCountryCd();
          String ss = lZip+"^"+lCountry;    
          String[] selected = pForm.getSelected();
          for(int jj=0; jj<selected.length; jj++) {
              if(selected[jj].equals(ss)) {
              betV.setCheckedFl(true);
              break;
            }
          }
        }
        distributorEjb.updateDeliveryScheduleZipCodes(
                               pForm.getScheduleData().getScheduleId(), 
                               betVV,
                               user.getUserName());
      } catch (Exception exc) {
        ae.add("error",new ActionError("error.systemError",exc.getMessage()));
        return ae;
      }
    }
    ae = showDates(request, pForm);
    if(ae.size()>0) return ae;
    pForm.setConfigType(actConfigType);

    ae = searchTerritory(request,pForm);
    if(ae.size()>0) return ae;

    return ae;
  }
 //***********************************************************************
  public static ActionErrors sortTerr(HttpServletRequest request,
	    DeliveryScheduleMgrForm pForm)
  {
    ActionErrors ae = new ActionErrors();
    String fieldName = (String) request.getParameter("sortField");
    if(fieldName==null) {
      ae.add("error",new ActionError("error.systemError","No sort field name found"));
      return ae;
    }
    String actConfigType = pForm.getActConfigType();
    if("County".equals(actConfigType)) {
       BusEntityTerrViewVector betVV = pForm.getCounties();  
       betVV.sort(fieldName);
       pForm.setCounties(betVV);
    }else {
       BusEntityTerrViewVector betVV = pForm.getPostalCodes();  
       betVV.sort(fieldName);
       pForm.setPostalCodes(betVV);
    }
   ae = prepareScheduele(pForm); 
   if(ae.size()>0) return ae;
   ae = showDates(request, pForm);
   if(ae.size()>0) return ae;
   resetSelected(pForm);
   pForm.setServicedOnly(pForm.getServicedOnlySave());

   return ae;
  }
  //---------------------------------------------------------------------------
  private static void resetSelected(DeliveryScheduleMgrForm pForm) 
  {
  String actConfigType = pForm.getActConfigType();
    if("County".equals(actConfigType)) {
      BusEntityTerrViewVector terrVvV = pForm.getCounties();
      int count = 0;
      for(int ii=0; ii<terrVvV.size(); ii++) {
         BusEntityTerrView terrVv = (BusEntityTerrView) terrVvV.get(ii);
         if(terrVv.getCheckedFl()) count++;
      }
      String[] selected = new String[count];
      for(int ii=0,jj=0; ii<terrVvV.size(); ii++) {
        BusEntityTerrView terrVv = (BusEntityTerrView) terrVvV.get(ii);
        if(terrVv.getCheckedFl()){
          String lCounty = terrVv.getCountyCd();
          String lStateCd = terrVv.getStateProvinceCd();
          String lCountry = terrVv.getCountryCd();
          String ss = lCounty+"^"+lStateCd+"^"+lCountry;    
          selected[jj++] = ss;
        }
      }
      pForm.setSelected(selected);
    } else {
      BusEntityTerrViewVector terrVvV = pForm.getPostalCodes();
      int count = 0;
      for(int ii=0; ii<terrVvV.size(); ii++) {
         BusEntityTerrView terrVv = (BusEntityTerrView) terrVvV.get(ii);
         if(terrVv.getCheckedFl()) count++;
      }
      String[] selected = new String[count];
      for(int ii=0,jj=0; ii<terrVvV.size(); ii++) {
        BusEntityTerrView terrVv = (BusEntityTerrView) terrVvV.get(ii);
        if(terrVv.getCheckedFl()){
          String lPostalCode = terrVv.getPostalCode();
          String lCountry = terrVv.getCountryCd();
          String ss = lPostalCode+"^"+lCountry;    
          selected[jj++] = ss;
        }
      }
      pForm.setSelected(selected);
    }
  }
  //-------------------------------------------------------------------------------
  public static ActionErrors selectAll(HttpServletRequest request,
	    DeliveryScheduleMgrForm pForm)
  {
    ActionErrors ae = new ActionErrors();
    String actConfigType = pForm.getActConfigType();
    if("County".equals(actConfigType)) {
      BusEntityTerrViewVector terrVvV = pForm.getCounties();
      String[] selected = new String[terrVvV.size()];
      for(int ii=0,jj=0; ii<terrVvV.size(); ii++) {
        BusEntityTerrView terrVv = (BusEntityTerrView) terrVvV.get(ii);
        String lCounty = terrVv.getCountyCd();
        String lStateCd = terrVv.getStateProvinceCd();
        String lCountry = terrVv.getCountryCd();
        String ss = lCounty+"^"+lStateCd+"^"+lCountry;    
        selected[jj++] = ss;
      }
      pForm.setSelected(selected);
    } else {
      BusEntityTerrViewVector terrVvV = pForm.getPostalCodes();
      String[] selected = new String[terrVvV.size()];
      for(int ii=0,jj=0; ii<terrVvV.size(); ii++) {
        BusEntityTerrView terrVv = (BusEntityTerrView) terrVvV.get(ii);
        String lPostalCode = terrVv.getPostalCode();
        String lCountry = terrVv.getCountryCd();
        String ss = lPostalCode+"^"+lCountry;    
        selected[jj++] = ss;
      }
      pForm.setSelected(selected);
    }
//    ae = prepareScheduele(pForm); 
//    if(ae.size()>0) return ae;
    ae = showDates(request, pForm);
    if(ae.size()>0) return ae;
   return ae;
  }
  //-------------------------------------------------------------------------------
  public static ActionErrors clearSelected(HttpServletRequest request,
	    DeliveryScheduleMgrForm pForm)
  {
    ActionErrors ae = new ActionErrors();
    String actConfigType = pForm.getActConfigType();
    if("County".equals(actConfigType)) {
      BusEntityTerrViewVector terrVvV = pForm.getCounties();
      int count = 0;
      for(int ii=0,jj=0; ii<terrVvV.size(); ii++) {
        BusEntityTerrView terrVv = (BusEntityTerrView) terrVvV.get(ii);
        boolean checkedFl = terrVv.getCheckedFl();
        boolean noModifyFl = terrVv.getNoModifiyFl();
        if(checkedFl && noModifyFl) count++;
      }
      String[] selected = new String[count];
      for(int ii=0,jj=0; ii<terrVvV.size(); ii++) {
        BusEntityTerrView terrVv = (BusEntityTerrView) terrVvV.get(ii);
        boolean checkedFl = terrVv.getCheckedFl();
        boolean noModifyFl = terrVv.getNoModifiyFl();
        if(checkedFl && noModifyFl) {
          String lCounty = terrVv.getCountyCd();
          String lStateCd = terrVv.getStateProvinceCd();
          String lCountry = terrVv.getCountryCd();
          String ss = lCounty+"^"+lStateCd+"^"+lCountry;    
          selected[jj++] = ss;
        }
      }
      pForm.setSelected(selected);
    } else {
      pForm.setSelected(new String[0]);
    }
    ae = showDates(request, pForm);
    if(ae.size()>0) return ae;
    return ae;
  }
  
}





