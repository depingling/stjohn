
package com.cleanwise.view.logic;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Date;
import java.util.GregorianCalendar;
import com.cleanwise.service.api.util.PasswordUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.view.utils.*;
import com.cleanwise.view.forms.OrderScheduleMgrForm;
import com.cleanwise.service.api.*;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.DuplicateNameException;
import com.cleanwise.service.api.util.RefCodeNames;
import java.rmi.RemoteException;
import com.cleanwise.service.api.util.SearchCriteria;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

/**
 * <code>TRadintPartnerMgrLogic</code> implements the logic needed to
 * place orders scheduled for automatic processing.
 *
 *@author Yuriy Kupershmidt
 */
public class OrderScheduleMgrLogic {

    /**
     * <code>init</code> method.
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */
    public static ActionErrors init(HttpServletRequest request,
			    OrderScheduleMgrForm pForm)
    {
    ActionErrors ae = new ActionErrors();
  	return ae;
    }
   //***********************************************************************
    public static ActionErrors clearDetail(HttpServletRequest request,
			    OrderScheduleMgrForm pForm)
    {
    ActionErrors ae = new ActionErrors();
  	return ae;
    }
   //***********************************************************************
  public static ActionErrors search(HttpServletRequest request,
	    OrderScheduleMgrForm pForm)
  {
    ActionErrors ae = new ActionErrors();
    clearDetail(request,pForm);
    HttpSession session = request.getSession();
    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    if(factory==null) {
      ae.add("error",new ActionError("error.systemError","No Ejb access"));
      return ae;
    }
    AutoOrder autoOrderEjb = null;
    try {
      autoOrderEjb = factory.getAutoOrderAPI();
    } catch(com.cleanwise.service.api.APIServiceAccessException exc) {
      exc.printStackTrace();
      ae.add("error",new ActionError("error.systemError","No auto order Ejb pointer"));
      return ae;
    }
    int searchType = pForm.getSearchType();
    String siteIdS = pForm.getSearchSiteId();
    String siteName = pForm.getSearchSiteName();
    String accountIdS = pForm.getSearchAccountId();
    String orderGuideName = pForm.getSearchOrderGuideName();
    String orderScheduleType = pForm.getSearchOrderScheduleType();
    String orderScheduleRuleType = pForm.getSearchOrderScheduleRuleType();
    String dateForS = pForm.getSearchDateFor();
    int siteId = 0;
    if(siteIdS!=null && siteIdS.trim().length()>0) {
      try {
        siteId = Integer.parseInt(siteIdS);
      } catch (NumberFormatException exc) {
        ae.add("error",new ActionError("error.simpleGenericError","Wrong site id format: "+siteIdS));
        return ae;
      }
      if(siteId<0){
        ae.add("error",new ActionError("error.simpleGenericError","Wrong site id format: "+siteIdS));
        return ae;
      }
    }
    int accountId = 0;
    if(accountIdS!=null && accountIdS.trim().length()>0) {
      try {
        accountId = Integer.parseInt(accountIdS);
      } catch (NumberFormatException exc) {
        ae.add("error",new ActionError("error.simpleGenericError","Wrong account id format: "+accountIdS));
        return ae;
      }
      if(siteId<0){
        ae.add("error",new ActionError("error.simpleGenericError","Wrong account id format: "+accountIdS));
        return ae;
      }
    }
    Date dateFor = null;
    if(dateForS!=null && dateForS.trim().length()>0) {
      GregorianCalendar gc = Constants.createCalendar(dateForS);
      dateFor = gc.getTime();
      if(gc==null) {
        ae.add("error",new ActionError("error.simpleGenericError","Wrong date for format: "+dateForS));
        return ae;
      }
    }

    OrderScheduleViewVector orderScheduleVV = null;
    try {
       orderScheduleVV = autoOrderEjb.getOrderSchedules(
                           accountId,
                           siteId,
                           siteName,
                           orderGuideName,
                           orderScheduleType,
                           orderScheduleRuleType,
                           dateFor,
                           searchType);
    } catch (RemoteException exc) {
      ae.add("error",new ActionError("error.systemError",exc.getMessage()));
      return ae;
    }
    pForm.setOrderSchedules(orderScheduleVV);

  	return ae;
  }
   //***********************************************************************
  public static ActionErrors detail(HttpServletRequest request,
	    OrderScheduleMgrForm pForm)
  {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    String scheduleIdS = request.getParameter("scheduleId");
    int scheduleId = 0;
    try {
      scheduleId = Integer.parseInt(scheduleIdS);
    } catch(NumberFormatException exc) {}
    if(scheduleId<=0) {
      ae.add("error",new ActionError("error.systemError","Wrong scheduleId format: "+scheduleIdS));
      return ae;
    }

    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    if(factory==null) {
      ae.add("error",new ActionError("error.systemError","No Ejb access"));
      return ae;
    }
    AutoOrder autoOrderEjb = null;
    try {
      autoOrderEjb = factory.getAutoOrderAPI();
    } catch(com.cleanwise.service.api.APIServiceAccessException exc) {
      exc.printStackTrace();
      ae.add("error",new ActionError("error.systemError","No auto order Ejb pointer"));
      return ae;
    }
    OrderScheduleJoin orderSchedule = null;
    try {
       orderSchedule = autoOrderEjb.getOrderSchedule(scheduleId);
       pForm.setOrderSchedule(orderSchedule);
    } catch (Exception exc) {
      ae.add("error",new ActionError("error.systemError",exc.getMessage()));
      return ae;
    }
    CatalogInformation catalogInfEjb = null;
    try {
      catalogInfEjb = factory.getCatalogInformationAPI();
    } catch(com.cleanwise.service.api.APIServiceAccessException exc) {
      exc.printStackTrace();
      ae.add("error",new ActionError("error.systemError","No catalog information Ejb pointer"));
      return ae;
    }
    CatalogData catalog = null;
    try {
       catalog = catalogInfEjb.getSiteCatalog(orderSchedule.getSiteId());
    } catch (Exception exc) {
      ae.add("error",new ActionError("error.systemError",exc.getMessage()));
      return ae;
    }
    ShoppingServices shoppingServicesEjb = null;
    try {
      shoppingServicesEjb = factory.getShoppingServicesAPI();
    } catch(com.cleanwise.service.api.APIServiceAccessException exc) {
      exc.printStackTrace();
      ae.add("error",new ActionError("error.systemError","No shopping services Ejb pointer"));
      return ae;
    }
    OrderGuideDataVector templateOrderGuides = null;
    OrderGuideDataVector userOrderGuides = null;
    int catalogId = catalog.getCatalogId();
    pForm.setCatalogId(catalogId);
    int userId = orderSchedule.getUserId();
    int siteId = orderSchedule.getSiteId();
    try {
       templateOrderGuides = shoppingServicesEjb.getTemplateOrderGuides(catalogId, siteId);
       userOrderGuides = shoppingServicesEjb.getUserOrderGuides(userId,catalogId,siteId);
    } catch (Exception exc) {
      ae.add("error",new ActionError("error.systemError",exc.getMessage()));
      return ae;
    }
    pForm.setTemplateOrderGuides(templateOrderGuides);
    pForm.setUserOrderGuides(userOrderGuides);
    ae = prepareSchedule(request, pForm);
    if(ae.size()>0) {
      return ae;
    }
    pForm.setUserId(orderSchedule.getUserId());
    User userEjb = null;
    try {
      userEjb = factory.getUserAPI();
    } catch(com.cleanwise.service.api.APIServiceAccessException exc) {
      exc.printStackTrace();
      ae.add("error",new ActionError("error.systemError","No users Ejb pointer"));
      return ae;
    }
    UserDataVector users = null;
    try {
       users = userEjb.getUsersCollectionByBusEntity(orderSchedule.getSiteId(),null);
       pForm.setUsers(users);
    } catch (Exception exc) {
      ae.add("error",new ActionError("error.systemError",exc.getMessage()));
      return ae;
    }

  	return ae;
  }
   //***********************************************************************
  public static ActionErrors changeUser(HttpServletRequest request,
	    OrderScheduleMgrForm pForm)
  {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    OrderScheduleJoin orderSchedule = pForm.getOrderSchedule();
    int userId = pForm.getUserId();

    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    if(factory==null) {
      ae.add("error",new ActionError("error.systemError","No Ejb access"));
      return ae;
    }
    User userEjb = null;
    try {
      userEjb = factory.getUserAPI();
    } catch(com.cleanwise.service.api.APIServiceAccessException exc) {
      exc.printStackTrace();
      ae.add("error",new ActionError("error.systemError","No auto order Ejb pointer"));
      return ae;
    }
    UserData user = null;
    try {
       user = userEjb.getUser(userId);
    } catch (Exception exc) {
      ae.add("error",new ActionError("error.systemError",exc.getMessage()));
      return ae;
    }

    AutoOrder autoOrderEjb = null;
    try {
      autoOrderEjb = factory.getAutoOrderAPI();
    } catch(com.cleanwise.service.api.APIServiceAccessException exc) {
      exc.printStackTrace();
      ae.add("error",new ActionError("error.systemError","No auto order Ejb pointer"));
      return ae;
    }
    ShoppingServices shoppingServicesEjb = null;
    try {
      shoppingServicesEjb = factory.getShoppingServicesAPI();
    } catch(com.cleanwise.service.api.APIServiceAccessException exc) {
      exc.printStackTrace();
      ae.add("error",new ActionError("error.systemError","No shopping services Ejb pointer"));
      return ae;
    }
    OrderGuideDataVector userOrderGuides = null;
    int catalogId = pForm.getCatalogId();
    int siteId = orderSchedule.getSiteId();
    try {
       userOrderGuides = shoppingServicesEjb.getUserOrderGuides(userId,catalogId,siteId);
    } catch (Exception exc) {
      ae.add("error",new ActionError("error.systemError",exc.getMessage()));
      return ae;
    }
    pForm.setUserOrderGuides(userOrderGuides);
    boolean changeOrderGuideFlag = true;
    int orderGuideId = orderSchedule.getOrderGuideId();
    for(int ii=0; ii<userOrderGuides.size(); ii++) {
      OrderGuideData ogD = (OrderGuideData) userOrderGuides.get(ii);
      if(ogD.getOrderGuideId()==orderGuideId) {
        changeOrderGuideFlag = false;
        break;
      }
    }
    if(changeOrderGuideFlag) {
      OrderGuideDataVector templateOrderGuides = pForm.getTemplateOrderGuides();
      for(int ii=0; ii<templateOrderGuides.size(); ii++) {
        OrderGuideData ogD = (OrderGuideData) templateOrderGuides.get(ii);
        if(ogD.getOrderGuideId()==orderGuideId) {
          changeOrderGuideFlag = false;
          break;
        }
      }
    }
    if(changeOrderGuideFlag) {
      orderSchedule.setOrderGuideId(0);
    }
    orderSchedule.setUserId(userId);
    orderSchedule.setUserFirstName(user.getFirstName());
    orderSchedule.setUserLastName(user.getLastName());
  	return ae;
  }
  //******************************************************************************
  private static ActionErrors prepareSchedule (HttpServletRequest request, OrderScheduleMgrForm pForm)
  {
    ActionErrors ae = new ActionErrors();
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
    return ae;
  }
  //--------------------------------------------------------------------------------------
  private static ActionErrors showDates(HttpServletRequest request, OrderScheduleMgrForm pForm)
  {
    ActionErrors ae = new ActionErrors();
    OrderScheduleJoin orderSchedule = pForm.getOrderSchedule();
    Date curDate = Constants.getCurrentDate();
    GregorianCalendar calendar = new GregorianCalendar();
    calendar.setTime(curDate);
    calendar.add(GregorianCalendar.MONTH,-1);
    calendar.set(GregorianCalendar.DAY_OF_MONTH,1);
    pForm.setCalendar(calendar);
    if(orderSchedule.getOrderScheduleId()<=0 && orderSchedule.getOrderGuideId()<=0) {
      return ae;
    }

    GregorianCalendar endCalendar = (GregorianCalendar) calendar.clone();
    endCalendar.add(GregorianCalendar.MONTH,6);
    GregorianCalendar scheduleStart = Constants.createCalendar(pForm.getStartDate());
    if(scheduleStart==null) {
      ae.add("error",new ActionError("error.simpleGenericError","Wrong start date format: "+pForm.getStartDate()));
      return ae;
    }
    GregorianCalendar scheduleEnd = null;
    String endDate = pForm.getEndDate();
    if(endDate == null || endDate.trim().length()==0) {
      scheduleEnd = (GregorianCalendar) scheduleStart.clone();
      scheduleEnd.add(GregorianCalendar.YEAR,100);
    } else {
      scheduleEnd = Constants.createCalendar(endDate);
      if(scheduleEnd==null) {
        ae.add("error",new ActionError("error.simpleGenericError","Wrong end date format: "+pForm.getEndDate()));
        return ae;
      }
    }
    long calendarMills = calendar.getTime().getTime();

    //init OrderScheduleJoin object
    int orderGuideId = orderSchedule.getOrderGuideId();
    if(orderGuideId<=0) {
      ae.add("error",new ActionError("error.simpleGenericError","No order guide selected"));
      return ae;
    }

    List datesToShow = new ArrayList();
    String scheduleType = orderSchedule.getOrderScheduleRuleCd();
    //Weekly schedule
    if(RefCodeNames.ORDER_SCHEDULE_RULE_CD.WEEK.equals(scheduleType)) {
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
    } else {
      pForm.setWeekDays(new int[0]);
    }
    //Month day schedule
    if(RefCodeNames.ORDER_SCHEDULE_RULE_CD.DAY_MONTH.equals(scheduleType)) {
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
    if(RefCodeNames.ORDER_SCHEDULE_RULE_CD.WEEK_MONTH.equals(scheduleType)) {
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
      if(dd.before(calendar)) {
        continue;
      }
      datesToShow.add(dd);
      prevDate = dd;
    }

    pForm.setCalendarOrderDates(datesToShow);
    pForm.setNextOrderIndex(0);
    if(datesToShow.size()>0) {
      GregorianCalendar gc = (GregorianCalendar) datesToShow.get(0);
      pForm.setNextOrderRelMonth(
          gc.get(GregorianCalendar.YEAR)*12+gc.get(GregorianCalendar.MONTH)-
          (calendar.get(GregorianCalendar.YEAR)*12+calendar.get(GregorianCalendar.MONTH)));
      pForm.setNextOrderMonthDay(gc.get(GregorianCalendar.DAY_OF_MONTH));
    } else {
      pForm.setNextOrderRelMonth(-1);
      pForm.setNextOrderMonthDay(0);
    }
    return ae;
  }
  //-------------------------------------------------------------------------------
  private static void initFields(OrderScheduleMgrForm pForm) {
    pForm.setUsers(null);
//    pForm.setScheduleType(RefCodeNames.ORDER_SCHEDULE_RULE_CD.WEEK);
//    pForm.setOrderScheduleId(0);
//    pForm.setOrderGuideId(0);
    pForm.setStartDate(Constants.date2string(Constants.getCurrentDate()));
    pForm.setEndDate("");
//    pForm.setScheduleAction(RefCodeNames.ORDER_SCHEDULE_CD.NOTIFY);
    pForm.setCcEmail("");
    pForm.setWeekCycle("1");
    pForm.setWeekDays(new int[0]);
    pForm.setMonthDayCycle("1");
    pForm.setMonthDays(new int[0]);
    pForm.setMonthWeekCycle("1");
    pForm.setMonthWeekDay(2);
    pForm.setMonthWeeks(new int[0]);
    pForm.setAlsoDates("");
    pForm.setExcludeDates("");
  }
   //***********************************************************************
   public static ActionErrors save(HttpServletRequest request,
			    OrderScheduleMgrForm pForm)
   {
    ActionErrors ae = new ActionErrors();
    ae = showDates(request,pForm);
    if(ae.size()>0) {
      return ae;
    }
    //Save shcedule
    HttpSession session = request.getSession();
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    if(appUser==null) {
      ae.add("error",new ActionError("error.systemError","No "+Constants.APP_USER+"session object found"));
      return ae;
    }
    UserData user = appUser.getUser();
    OrderScheduleJoin orderSchedule = pForm.getOrderSchedule();
    int siteId = orderSchedule.getSiteId();

    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    if(factory==null) {
      ae.add("error",new ActionError("error.systemError","No Ejb access"));
      return ae;
    }
    AutoOrder autoOrderEjb = null;
    try {
      autoOrderEjb = factory.getAutoOrderAPI();
    } catch(com.cleanwise.service.api.APIServiceAccessException exc) {
      exc.printStackTrace();
      ae.add("error",new ActionError("error.systemError","No auto order Ejb pointer"));
      return ae;
    }
//    orderSchedule.setOrderScheduleId(pForm.getOrderScheduleId());
    GregorianCalendar gc = Constants.createCalendar(pForm.getStartDate());
    orderSchedule.setEffDate(gc.getTime());
    String endDate = pForm.getEndDate();
    if(endDate!=null &&  endDate.trim().length()>0) {
      gc = Constants.createCalendar(endDate);
      orderSchedule.setExpDate(gc.getTime());
    }
//    orderScheduleViewD.setOrderGuideId(pForm.getOrderGuideId());
    orderSchedule.setCcEmail(pForm.getCcEmail());
//    orderScheduleViewD.setOrderScheduleCd(pForm.getScheduleAction());
    if(pForm.getWeekDays().length>0) {
      orderSchedule.setOrderScheduleRuleCd(RefCodeNames.ORDER_SCHEDULE_RULE_CD.WEEK);
      orderSchedule.setCycle(Integer.parseInt(pForm.getWeekCycle()));
      orderSchedule.setElements(pForm.getWeekDays());
    }
    else if(pForm.getMonthDays().length>0) {
      orderSchedule.setOrderScheduleRuleCd(RefCodeNames.ORDER_SCHEDULE_RULE_CD.DAY_MONTH);
      orderSchedule.setCycle(Integer.parseInt(pForm.getMonthDayCycle()));
      orderSchedule.setElements(pForm.getMonthDays());
    }
    else if(pForm.getMonthWeeks().length>0) {
      orderSchedule.setOrderScheduleRuleCd(RefCodeNames.ORDER_SCHEDULE_RULE_CD.WEEK_MONTH);
      orderSchedule.setCycle(Integer.parseInt(pForm.getMonthWeekCycle()));
      orderSchedule.setElements(pForm.getMonthWeeks());
      orderSchedule.setMonthWeekDay(pForm.getMonthWeekDay());
    }
    else {
      orderSchedule.setOrderScheduleRuleCd(RefCodeNames.ORDER_SCHEDULE_RULE_CD.DATE_LIST);
    }
    //Additional days
    String alsoDates = pForm.getAlsoDates();
    List alsoDateL = new ArrayList();
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
        alsoDateL.add(gc);
      }
    }
    Date[] alsoDatesA = new Date[alsoDateL.size()];
    for(int ii=0; ii<alsoDateL.size(); ii++) {
      gc = (GregorianCalendar) alsoDateL.get(ii);
      alsoDatesA[ii]=gc.getTime();
    }
    orderSchedule.setAlsoDates(alsoDatesA);

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
        excludeDatesL.add(gc.getTime());
      }
    }
    Date[] excludeDatesA = new Date[excludeDatesL.size()];
    for(int ii=0; ii<excludeDatesA.length; ii++) {
      Date excludeDate = (Date) excludeDatesL.get(ii);
      excludeDatesA[ii] = excludeDate;
    }
    orderSchedule.setExceptDates(excludeDatesA);
    int oldId = orderSchedule.getOrderScheduleId();
    int orderGuideId = orderSchedule.getOrderGuideId();
    //check crossings
    Date effDate = orderSchedule.getEffDate();
    long effMills = effDate.getTime();
    long expMills = 0;
    Date expDate = orderSchedule.getExpDate();
    if(expDate!=null) {
        expMills = expDate.getTime();
    }

    OrderScheduleViewVector schedules = null;
    try {
      schedules = autoOrderEjb.getOrderSchedules(orderSchedule.getSiteId(),pForm.getUserId());
    } catch (RemoteException exc) {
      ae.add("error",new ActionError("error.simpleGenericError",exc.getMessage()));
      return ae;
    }

    for(int ii=0; ii<schedules.size(); ii++) {
      OrderScheduleView osv = (OrderScheduleView) schedules.get(ii);
      if(oldId!=osv.getOrderScheduleId() && orderGuideId==osv.getOrderGuideId()){
        Date effD = osv.getEffDate();
        long effM = effD.getTime();
        long expM = 0;
        Date expD = osv.getExpDate();
        if(expD!=null) {
          expM = expD.getTime();
        }
        if((expM==0 || expM>=effMills) &&
           (effM<=expMills || expMills==0)) {
          String name = osv.getOrderGuideName()+" ("+Constants.date2string(effD)+" - ";
          if(expD!=null) {
            name += Constants.date2string(expD)+" )";
          } else {
            name += "... )";
          }
          String mess = "The schedule intersects with existing schedule: "+name;
          ae.add("error",new ActionError("error.simpleGenericError",mess));
          return ae;
        }
      }
    }
    try {
      int id = autoOrderEjb.saveOrderSchedule(orderSchedule,user.getUserName());
      orderSchedule.setOrderScheduleId(id);
      pForm.setOrderSchedule(orderSchedule);
    } catch (RemoteException exc) {
      ae.add("error",new ActionError("error.simpleGenericError",exc.getMessage()));
      return ae;
    }

    List orderGuides = pForm.getTemplateOrderGuides();
    int ii=0;
    for(; ii<orderGuides.size(); ii++) {
      OrderGuideData ogD = (OrderGuideData) orderGuides.get(ii);
      if(orderGuideId==ogD.getOrderGuideId()) {
        orderSchedule.setOrderGuideName(ogD.getShortDesc());
        break;
      }
    }
    if(ii==orderGuides.size()) {
      orderGuides = pForm.getUserOrderGuides();
      for(int jj=0; jj<orderGuides.size(); jj++) {
        OrderGuideData ogD = (OrderGuideData) orderGuides.get(jj);
        if(orderGuideId==ogD.getOrderGuideId()) {
          orderSchedule.setOrderGuideName(ogD.getShortDesc());
          break;
        }
      }
    }

    //Update in list
    OrderScheduleViewVector orderSchedules = pForm.getOrderSchedules();
    ii=0;
    if(orderSchedules!=null) {
      for(; ii<orderSchedules.size(); ii++) {
        OrderScheduleView osV = (OrderScheduleView) orderSchedules.get(ii);
        if(osV.getOrderScheduleId()==oldId) {
          osV.setOrderScheduleRuleCd(orderSchedule.getOrderScheduleRuleCd());
          osV.setOrderScheduleId(orderSchedule.getOrderScheduleId());
          osV.setOrderScheduleCd(orderSchedule.getOrderScheduleCd());
          osV.setOrderGuideName(orderSchedule.getOrderGuideName());
          osV.setOrderGuideId(orderSchedule.getOrderGuideId());
          osV.setEffDate(orderSchedule.getEffDate());
          osV.setExpDate(orderSchedule.getExpDate());
          break;
        }
      }
    }
/*
    if(ii==orderSchedules.size()) {
      orderSchedules.add(orderScheduleViewD);
    }
*/
    pForm.setOrderSchedules(orderSchedules);
    return ae;
  }
 //***********************************************************************
  public static ActionErrors createNew(HttpServletRequest request,
	    OrderScheduleMgrForm pForm)
  {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    if(factory==null) {
      ae.add("error",new ActionError("error.systemError","No Ejb access"));
      return ae;
    }
    initFields(pForm);
    OrderScheduleJoin orderSchedule = OrderScheduleJoin.createValue();
    String siteIdS = pForm.getSearchSiteId();
    int siteId = 0;
    if(siteIdS!=null && siteIdS.trim().length()>0) {
      try {
        siteId = Integer.parseInt(siteIdS);
      } catch (NumberFormatException exc) {
        ae.add("error",new ActionError("error.simpleGenericError","Wrong site id format: "+siteIdS));
        return ae;
      }
    }
    if(siteId<=0) {
      ae.add("error",new ActionError("error.simpleGenericError","Site id is requred to create order schedule"));
      return ae;
    }
    Site siteEjb = null;
    try {
      siteEjb = factory.getSiteAPI();
    } catch(com.cleanwise.service.api.APIServiceAccessException exc) {
      exc.printStackTrace();
      ae.add("error",new ActionError("error.systemError","No site Ejb pointer"));
      return ae;
    }
    SiteData site = null;
    try {
       site = siteEjb.getSite(siteId, 0, false, SessionTool.getCategoryToCostCenterView(session, siteId));
    } catch (Exception exc) {
      ae.add("error",new ActionError("error.systemError",exc.getMessage()));
      return ae;
    }
    orderSchedule.setSiteName(site.getBusEntity().getShortDesc());
    BusEntityData accountBusEntity = site.getAccountBusEntity();
    orderSchedule.setAccountName(accountBusEntity.getShortDesc());
    orderSchedule.setAccountId(accountBusEntity.getBusEntityId());
    CatalogInformation catalogInfEjb = null;
    try {
      catalogInfEjb = factory.getCatalogInformationAPI();
    } catch(com.cleanwise.service.api.APIServiceAccessException exc) {
      exc.printStackTrace();
      ae.add("error",new ActionError("error.systemError","No catalog information Ejb pointer"));
      return ae;
    }
    CatalogData catalog = null;
    try {
       catalog = catalogInfEjb.getSiteCatalog(siteId);
    } catch (Exception exc) {
      ae.add("error",new ActionError("error.systemError",exc.getMessage()));
      return ae;
    }
    User userEjb = null;
    try {
      userEjb = factory.getUserAPI();
    } catch(com.cleanwise.service.api.APIServiceAccessException exc) {
      exc.printStackTrace();
      ae.add("error",new ActionError("error.systemError","No users Ejb pointer"));
      return ae;
    }
    UserDataVector users = null;
    try {
       users = userEjb.getUsersCollectionByBusEntity(siteId,null);
    } catch (Exception exc) {
      ae.add("error",new ActionError("error.systemError",exc.getMessage()));
      return ae;
    }
    if(users.size()==0) {
      ae.add("error",new ActionError("error.systemError","Selected site has no users"));
      return ae;
    }
    pForm.setUsers(users);
    UserData user = (UserData) users.get(0);
    int userId = user.getUserId();
    ShoppingServices shoppingServicesEjb = null;
    try {
      shoppingServicesEjb = factory.getShoppingServicesAPI();
    } catch(com.cleanwise.service.api.APIServiceAccessException exc) {
      exc.printStackTrace();
      ae.add("error",new ActionError("error.systemError","No shopping services Ejb pointer"));
      return ae;
    }
    OrderGuideDataVector templateOrderGuides = null;
    OrderGuideDataVector userOrderGuides = null;
    int catalogId = catalog.getCatalogId();
    pForm.setCatalogId(catalogId);
    try {
       templateOrderGuides = shoppingServicesEjb.getTemplateOrderGuides(catalogId, siteId);
       userOrderGuides = shoppingServicesEjb.getUserOrderGuides(userId, catalogId, siteId);
    } catch (Exception exc) {
      ae.add("error",new ActionError("error.systemError",exc.getMessage()));
      return ae;
    }
    pForm.setTemplateOrderGuides(templateOrderGuides);
    pForm.setUserOrderGuides(userOrderGuides);

    orderSchedule.setSiteId(siteId);
    orderSchedule.setUserId(userId);
    orderSchedule.setUserFirstName(user.getFirstName());
    orderSchedule.setUserLastName(user.getLastName());
    pForm.setUserId(userId);

    String orderScheduleType = pForm.getSearchOrderScheduleType();
    if(orderScheduleType==null || orderScheduleType.trim().length()==00) {
      orderScheduleType = RefCodeNames.ORDER_SCHEDULE_CD.NOTIFY;
    }
    orderSchedule.setOrderScheduleCd(orderScheduleType);
    String orderScheduleRuleType = pForm.getSearchOrderScheduleRuleType();
    if(orderScheduleRuleType==null || orderScheduleRuleType.trim().length()==0) {
      orderScheduleRuleType = RefCodeNames.ORDER_SCHEDULE_RULE_CD.WEEK;
    }
    orderSchedule.setOrderScheduleRuleCd(orderScheduleRuleType);
    pForm.setOrderSchedule(orderSchedule);
    ae = showDates(request,pForm);
    if(ae.size()>0) {
      return ae;
    }
    return ae;
  }
 //***********************************************************************
  public static ActionErrors delete(HttpServletRequest request,
	    OrderScheduleMgrForm pForm)
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
    AutoOrder autoOrderEjb = null;
    try {
      autoOrderEjb = factory.getAutoOrderAPI();
    } catch(com.cleanwise.service.api.APIServiceAccessException exc) {
      exc.printStackTrace();
      ae.add("error",new ActionError("error.systemError","No order Ejb pointer"));
      return ae;
    }
    OrderScheduleJoin orderSchedule = pForm.getOrderSchedule();
    if(orderSchedule==null) {
      ae.add("error",new ActionError("error.systemError","Nothing to delete"));
      return ae;
    }
    int scheduleId = orderSchedule.getOrderScheduleId();
    if(scheduleId<=0 ){
      ae.add("error",new ActionError("error.systemError","Nothing to delete"));
      return ae;
    }
    try {
      autoOrderEjb.deleteOrderSchedule(scheduleId, user.getUserName());
    } catch(RemoteException exc) {
      exc.printStackTrace();
      ae.add("error",new ActionError("error.systemError","exc.getMessage()"));
      return ae;
    }
    //Update in list
    OrderScheduleViewVector orderSchedules = pForm.getOrderSchedules();
    for(int ii=0; ii<orderSchedules.size(); ii++) {
      OrderScheduleView osv = (OrderScheduleView) orderSchedules.get(ii);
      if(osv.getOrderScheduleId()==scheduleId) {
        orderSchedules.remove(ii);
        break;
      }
    }
    initFields(pForm);
    return ae;
  }
 //***********************************************************************
  public static ActionErrors sort(HttpServletRequest request,
	    OrderScheduleMgrForm pForm)
  {
    ActionErrors ae = new ActionErrors();
    String fieldName = (String) request.getParameter("sortField");
    if(fieldName==null) {
      ae.add("error",new ActionError("error.systemError","No sort field name found"));
      return ae;
    }
    OrderScheduleViewVector orderScheduleVV = pForm.getOrderSchedules();
    orderScheduleVV.sort(fieldName);
    pForm.setOrderSchedules(orderScheduleVV);
  	return ae;
  }
}





