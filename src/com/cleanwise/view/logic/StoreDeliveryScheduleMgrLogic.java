
package com.cleanwise.view.logic;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.APIServiceAccessException;
import com.cleanwise.service.api.session.Account;
import com.cleanwise.service.api.session.Country;
import com.cleanwise.service.api.session.Distributor;
import com.cleanwise.service.api.util.PhysicalInventoryPeriod;
import com.cleanwise.service.api.util.PhysicalInventoryPeriodArray;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.ScheduleProc;
import com.cleanwise.service.api.util.SearchCriteria;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.BusEntityDataVector;
import com.cleanwise.service.api.value.BusEntitySearchCriteria;
import com.cleanwise.service.api.value.BusEntityTerrView;
import com.cleanwise.service.api.value.BusEntityTerrViewVector;
import com.cleanwise.service.api.value.CountryDataVector;
import com.cleanwise.service.api.value.DeliveryScheduleView;
import com.cleanwise.service.api.value.DeliveryScheduleViewVector;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.ScheduleData;
import com.cleanwise.service.api.value.ScheduleDetailData;
import com.cleanwise.service.api.value.ScheduleDetailDataVector;
import com.cleanwise.service.api.value.ScheduleJoinView;
import com.cleanwise.service.api.value.UserData;
import com.cleanwise.view.forms.StoreDeliveryScheduleMgrForm;
import com.cleanwise.view.forms.StoreDistMgrDetailForm;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;

/**
 * <code>StoreDeliveryScheduleMgrLogic</code> implements the logic needed to
 * place orders scheduled for automatic processing.
 *
 *@author Veronika Denega
 */
public class StoreDeliveryScheduleMgrLogic {

    private static final String className = "StoreDeliveryScheduleMgrLogic";

    public static ActionErrors initSearch(HttpServletRequest request,
                                          StoreDeliveryScheduleMgrForm pForm)
    {
        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        StoreDistMgrDetailForm mainForm = (StoreDistMgrDetailForm) session.getAttribute("STORE_DIST_DETAIL_FORM");
        int distId = mainForm.getIntId();

        APIAccess factory = (APIAccess)request.getSession().getAttribute(Constants.APIACCESS);

        request.getSession().setAttribute(Constants.APIACCESS, factory);
        if (session.getAttribute("country.vector") == null) {
            Country countryBean;
            CountryDataVector countriesv = null;
            try {
                countryBean = factory.getCountryAPI();
                countriesv = countryBean.getAllCountries();
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (APIServiceAccessException e) {
                e.printStackTrace();
            }
            session.setAttribute("country.vector", countriesv);
        }

        pForm.setDistId(distId);

        return ae;
    }

   //***********************************************************************
    public static ActionErrors clearDetail(HttpServletRequest request,
          StoreDeliveryScheduleMgrForm pForm)
    {
      ActionErrors ae = new ActionErrors();
      pForm.setCounties(new BusEntityTerrViewVector());
      pForm.setPostalCodes(new BusEntityTerrViewVector());
      pForm.setAcctBusEntityList(null);
      return ae;
    }
    //***********************************************************************
    public static ActionErrors searchSchedule(HttpServletRequest request,
                                              StoreDeliveryScheduleMgrForm pForm)
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

        //Criteria: "STATE_PROVINCE_CD", "COUNTY_CD", "POSTAL_CD", "SHORT_DESC", "ID", "CITY"
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
        String searchCity =  pForm.getSearchCity();
        if(searchCity!=null && searchCity.length()>0) {
            crit.put("CITY", searchCity);
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
      StoreDeliveryScheduleMgrForm pForm)
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
      StoreDeliveryScheduleMgrForm pForm)
  {
      ActionErrors ae = detail(request, pForm,0);
      return ae;
  }

  public static ActionErrors detail(HttpServletRequest request,
      StoreDeliveryScheduleMgrForm pForm, int scheduleId)
  {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    pForm.setCounties(new BusEntityTerrViewVector());
    pForm.setPostalCodes(new BusEntityTerrViewVector());
    pForm.setAcctBusEntityList(null);
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

    /// Setting of the "USE_PHYSICAL_INVENTORY" property into the form
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    if(appUser == null) {
        String errorMessage = "No " + Constants.APP_USER + " session object found";
        ae.add("error", new ActionError("error.systemError", errorMessage));
        return ae;
    }
    try {
        /*
        PropertyService propertyServiceBean = factory.getPropertyServiceAPI();
        Account acctountBean = factory.getAccountAPI();
        BusEntityData store = (BusEntityData)appUser.getStores().get(0);
        ArrayList accountIds = acctountBean.getAccountsForStore(store.getBusEntityId());
        String usePhysicalInventory = 
            propertyServiceBean.getBusEntityProperty(
                ((Integer)accountIds.get(0)).intValue(), 
                RefCodeNames.PROPERTY_TYPE_CD.USE_PHYSICAL_INVENTORY);
        if (usePhysicalInventory == null) {
            pForm.setUsePhysicalInventory(false);
        } else {
            pForm.setUsePhysicalInventory(Boolean.parseBoolean(usePhysicalInventory));
        }
        */
        pForm.setUsePhysicalInventory(true);
    } catch (Exception ex) {
        String errorMessage = "An error occurred at reading of the " + 
            RefCodeNames.PROPERTY_TYPE_CD.USE_PHYSICAL_INVENTORY + 
            " property. " + ex.getMessage();
        ae.add("error", new ActionError("error.systemError", errorMessage));
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
  private static ActionErrors prepareScheduele(StoreDeliveryScheduleMgrForm pForm)
  {
    ActionErrors ae = new ActionErrors();
    ScheduleJoinView scheduleJoin = pForm.getScheduleJoinData();
    ScheduleData sD = scheduleJoin.getSchedule();
    ScheduleDetailDataVector scheduleDetailDV = scheduleJoin.getScheduleDetail();
    PhysicalInventoryPeriodArray physicalInvPeriods = null;
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
    String invCartAccessInterval="";
    int monthWeekDay = 0;
    if (pForm.getUsePhysicalInventory()) {
        physicalInvPeriods = new PhysicalInventoryPeriodArray();
        physicalInvPeriods.startLoadingItems();
    }
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
        } else if(detType.equals(RefCodeNames.SCHEDULE_DETAIL_CD.INV_CART_ACCESS_INTERVAL)){
            if(invCartAccessInterval.length()>0) {
                ae.add("error.systemError",new ActionError("error.simpleGenericError","Found extra "+value));
            } else {
                invCartAccessInterval = value;
            }
        } else if (detType.equals(RefCodeNames.SCHEDULE_DETAIL_CD.PHYSICAL_INV_START_DATE) || 
                   detType.equals(RefCodeNames.SCHEDULE_DETAIL_CD.PHYSICAL_INV_END_DATE) || 
                   detType.equals(RefCodeNames.SCHEDULE_DETAIL_CD.PHYSICAL_INV_FINAL_DATE)) {
            if (physicalInvPeriods != null) {
                physicalInvPeriods.addItem(detType, value);
            }
        }
    }

    if (physicalInvPeriods != null) {
        if (physicalInvPeriods.finishLoadingItems()) {
            pForm.setPhysicalInventoryPeriods(physicalInvPeriods.toString());
        } else {
            String errorMessage = "The data with the physical inventory period is invalid. ";
            ae.add("error", new ActionError("error.systemError", errorMessage));
        }
    }

    pForm.setInvCartAccessInterval(invCartAccessInterval);
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
    } else if(RefCodeNames.SCHEDULE_RULE_CD.DATE_LIST.equals(rule)){
      //No reqular structure
    }
    return ae;
  }
  //******************************************************************************
  private static ActionErrors prepareSchedule (HttpServletRequest request, StoreDeliveryScheduleMgrForm pForm)
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
  private static ActionErrors showDates(HttpServletRequest request, StoreDeliveryScheduleMgrForm pForm)
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
      //populate the schedule order dates (the dates to show are the calendar delivery dates, not 
      //the order dates
      int daysToAdd = 0;
      try {
    	  daysToAdd = 0 - Integer.valueOf(pForm.getCutoffDay());
      }
      catch (NumberFormatException nfe) {
    	  //nothing to do - the cutoffDate is not a valid integer so assume 0
      }
      List<GregorianCalendar> orderDates = new ArrayList<GregorianCalendar>(); 
      Iterator<GregorianCalendar> datesToShowIterator = datesToShow.iterator();
      while (datesToShowIterator.hasNext()) {
    	  GregorianCalendar dateToShow = datesToShowIterator.next();
    	  GregorianCalendar orderDate = (GregorianCalendar)dateToShow.clone();
    	  orderDate.add(Calendar.DAY_OF_YEAR, daysToAdd);
    	  orderDates.add(orderDate);
      }
      pForm.setCalendarOrderDates(orderDates);
    } else {
      pForm.setNextScheduleRelMonth(-1);
      pForm.setNextScheduleMonthDay(0);
    }

   return ae;
  }
  //-------------------------------------------------------------------------------
  private static void initFields(StoreDeliveryScheduleMgrForm pForm) {
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

    pForm.setUsePhysicalInventory(false);
    pForm.setPhysicalInventoryPeriods("");
  }

   public static ActionErrors addZipCode(HttpServletRequest request,
          StoreDeliveryScheduleMgrForm pForm) throws RemoteException, NamingException, APIServiceAccessException {

       ActionErrors ae = new ActionErrors();
       HttpSession session = request.getSession();

       CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
       UserData userData = appUser.getUser();

       StoreDeliveryScheduleMgrForm mainForm = (StoreDeliveryScheduleMgrForm) session.getAttribute("STORE_DELIVERY_SCHEDULE_FORM");
       String zip = mainForm.getAddZipCode();
       String coutryCd = mainForm.getAddCountryCd();

       if((zip==null)||(zip.trim().equals(""))){
           ae.add("error",new ActionError("error.simpleGenericError", "Zip code is empty, please fill this field."));
           return ae;
       }

       if((coutryCd==null)||(coutryCd.trim().equals(""))){
           ae.add("error",new ActionError("error.simpleGenericError", "Country is empty, please choose this one from list."));
           return ae;
       }
       
       ScheduleDetailData sdD = ScheduleDetailData.createValue();

       sdD.setValue(zip);
       sdD.setCountryCd(coutryCd);

       Distributor distrEjb = APIAccess.getAPIAccess().getDistributorAPI();
       int res = distrEjb.addZipCode(mainForm.getScheduleData().getScheduleId(), sdD, userData.getUserName());
       if(res==0){
           ae.add("error",new ActionError("error.simpleGenericError", "Zip code" + sdD.getValue()
                        + " already exists for this country " + sdD.getCountryCd()));
           return searchTerritoryAll(request,  pForm);
       }else{
           return searchTerritoryAll(request,  pForm);
       }
   }

    //***********************************************************************
   public static ActionErrors save(HttpServletRequest request,
          StoreDeliveryScheduleMgrForm pForm)
   {

    ActionErrors ae = new ActionErrors();
    //Save shcedule
    HttpSession session = request.getSession();
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    if(appUser==null) {
      ae.add("error",new ActionError("error.systemError","No " + Constants.APP_USER+"session object found"));
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

       if (Utility.isSet(pForm.getInvCartAccessInterval())) {
           try {
               Integer.parseInt(pForm.getInvCartAccessInterval());
           }
           catch (NumberFormatException e) {
               ae.add("error.systemError", new ActionError("error.simpleGenericError", "Inventory Interval.Invalid number"));
               return ae;
           }
           ScheduleDetailData sdD = ScheduleDetailData.createValue();
           sdD.setScheduleDetailCd(RefCodeNames.SCHEDULE_DETAIL_CD.INV_CART_ACCESS_INTERVAL);
           sdD.setValue(pForm.getInvCartAccessInterval());
           scheduleDetailDV.add(sdD);
       }

    /// Physical inventory period
    if (pForm.getUsePhysicalInventory()) {
        /// Is used the physical inventory period
        /// The text with dates not should be empty
        if (pForm.getPhysicalInventoryPeriods() != null && pForm.getPhysicalInventoryPeriods().trim().length() > 0) {
            PhysicalInventoryPeriodArray periodsObj = 
                PhysicalInventoryPeriodArray.parse(pForm.getPhysicalInventoryPeriods());
            if (periodsObj == null) {
                String errorMessage = "The text with the physical inventory period is written with errors";
                ae.add("error", new ActionError("error.simpleGenericError", errorMessage));
                return ae;
            }
            ArrayList<PhysicalInventoryPeriod> periods = periodsObj.getPeriods();
            ScheduleDetailData scheduleDetail = null;
            for (int i = 0; i < periods.size(); ++i) {
                PhysicalInventoryPeriod period = periods.get(i);
                if (period == null) {
                    continue;
                }
                scheduleDetail = ScheduleDetailData.createValue();
                scheduleDetail.setScheduleDetailCd(RefCodeNames.SCHEDULE_DETAIL_CD.PHYSICAL_INV_START_DATE);
                scheduleDetail.setValue(period.getStartDateAsString());
                scheduleDetailDV.add(scheduleDetail);

                scheduleDetail = ScheduleDetailData.createValue();
                scheduleDetail.setScheduleDetailCd(RefCodeNames.SCHEDULE_DETAIL_CD.PHYSICAL_INV_END_DATE);
                scheduleDetail.setValue(period.getEndDateAsString());
                scheduleDetailDV.add(scheduleDetail);

                scheduleDetail = ScheduleDetailData.createValue();
                scheduleDetail.setScheduleDetailCd(RefCodeNames.SCHEDULE_DETAIL_CD.PHYSICAL_INV_FINAL_DATE);
                scheduleDetail.setValue(period.getAbsoluteFinishDateAsString());
                scheduleDetailDV.add(scheduleDetail);
            }
        }
    }

    //Cutoff day
    String cutoffDayS = pForm.getCutoffDay();
    int cutoffDay = -1;
    try{
      cutoffDay = Integer.parseInt(cutoffDayS);
    }catch(Exception exp) {}
    if(cutoffDay<0) {
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
    
    checkDates(ae, pForm);
    if (ae.size() > 0) {
        return ae;
    }
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
      schedule.setCycle(1);
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
        // check if weekend  
        Date d = gc.getTime();
        int dayOfWeek = d.getDay() + 1;
        if ( dayOfWeek == gc.SUNDAY || dayOfWeek == gc.SATURDAY) {
            ae.add("error",new ActionError("error.simpleGenericError","Delivery at the weekend not allowed: " + dateS));
            return ae;
        }
        //
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
      StoreDeliveryScheduleMgrForm pForm)
  {
    ActionErrors ae = new ActionErrors();
    pForm.setCounties(new BusEntityTerrViewVector());
    pForm.setPostalCodes(new BusEntityTerrViewVector());
    pForm.setAcctBusEntityList(null);
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
      StoreDeliveryScheduleMgrForm pForm)
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
    pForm.setAcctBusEntityList(null);
    return ae;
  }
 //***********************************************************************
  public static ActionErrors sort(HttpServletRequest request,
      StoreDeliveryScheduleMgrForm pForm)
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

    public static ActionErrors searchTerritory(HttpServletRequest request,
                                               StoreDeliveryScheduleMgrForm pForm)
    {
        return searchTerritory(request, pForm, false);
    }

    public static ActionErrors searchTerritoryAll(HttpServletRequest request,
                                               StoreDeliveryScheduleMgrForm pForm)
    {
        return searchTerritory(request, pForm, true);
    }
    //***********************************************************************
    public static ActionErrors searchTerritory(HttpServletRequest request,
                                               StoreDeliveryScheduleMgrForm pForm, boolean all)
    {
        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        String configType = pForm.getConfigType();
        
        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        if(factory==null) {
            ae.add("error",new ActionError("error.systemError","No Ejb access"));
            return ae;
        }

        Distributor distributorEjb;
        try {
            distributorEjb = factory.getDistributorAPI();
        } catch(com.cleanwise.service.api.APIServiceAccessException exc) {
            exc.printStackTrace();
            ae.add("error",new ActionError("error.systemError","No Distributor Ejb access"));
            return ae;
        }

        //Criteria: "STATE_PROVINCE_CD", "CITY", "COUNTY_CD", "POSTAL_CD", "SHORT_DESC", "ID"
        boolean emptyCritFl = true;
        Hashtable crit = new Hashtable();
        String searchState = pForm.getSearchState();
        if(searchState!=null && searchState.length()>0) {
            crit.put("STATE_PROVINCE_CD", searchState);
            emptyCritFl = false;
        }

        String searchCounty = pForm.getSearchCounty();
        if(searchCounty!=null && searchCounty.length()>0) {
            crit.put("COUNTY_CD",searchCounty);
            emptyCritFl = false;
        }

        String searchZipCode =  pForm.getSearchZipCode();
        if(searchZipCode!=null && searchZipCode.length()>0) {
            if(all){
                searchZipCode = null;
            }else if("Zip Code".equals(configType)) {
                searchZipCode = searchZipCode.trim();
                if(searchZipCode.length()<3) {
                    ae.add("error",new ActionError("error.simpleGenericError", "Zip coode must be at least 3 characters long"));
                    return ae;
                }
                crit.put("POSTAL_CD", searchZipCode);
                emptyCritFl = false;
            }

        }

        String searchCity =  pForm.getSearchCity();
        if(searchCity!=null && searchCity.length()>0) {
            crit.put("CITY", searchCity);
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

        if(all){
            pForm.setSearchCountryCd(null);
            pForm.setSearchZipCode(null);
        }

        if("County".equals(configType)){
            if(emptyCritFl && !servicedOnlyFl) {
                ae.add("error",new ActionError("error.simpleGenericError", "Request is too broad"));
                return ae;
            }
            pForm.setCounties(new BusEntityTerrViewVector());
            try {
                BusEntityTerrViewVector terrVvV = distributorEjb.getDeliveryScheduleCounties(
                        pForm.getScheduleData().getScheduleId(), crit, beginsWithFl, servicedOnlyFl);
                pForm.setCounties(terrVvV);
            } catch (Exception exc) {
                ae.add("error",new ActionError("error.systemError",exc.getMessage()));
                return ae;
            }
        } else if("Zip Code".equals(configType)) {
            if(emptyCritFl && !servicedOnlyFl) {
                ae.add("error",new ActionError("error.simpleGenericError", "Request is too broad"));
                return ae;
            }
            pForm.setPostalCodes(new BusEntityTerrViewVector());
            try {
                BusEntityTerrViewVector terrVvV = distributorEjb.getDeliveryScheduleZipCodes(
                        pForm.getScheduleData().getScheduleId(), crit, beginsWithFl, servicedOnlyFl);
                pForm.setPostalCodes(terrVvV);
            } catch (Exception exc) {
                ae.add("error",new ActionError("error.systemError", exc.getMessage()));
                return ae;
            }
        } else if("Other".equals(configType)) {

            pForm.setPostalCodes(new BusEntityTerrViewVector());
            try {
                BusEntityTerrViewVector terrVvV = distributorEjb.getDeliveryScheduleZipCodes(
                        pForm.getScheduleData().getScheduleId(), pForm.getSearchCountryCd(), pForm.getSearchZipCode());

                pForm.setPostalCodes(terrVvV);



            } catch (Exception exc) {
                ae.add("error", new ActionError("error.systemError", exc.getMessage()));
                return ae;
            }
        }

        ae = showDates(request, pForm);
        if(ae.size()>0){
            return ae;
        }
        pForm.setActConfigType(configType);

        resetSelected(pForm);
        return ae;
    }

    //***********************************************************************
    public static ActionErrors saveConfiguration(HttpServletRequest request,
                                                 StoreDeliveryScheduleMgrForm pForm)
    {
        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        if(appUser==null) {
            ae.add("error",new ActionError("error.systemError","No " + Constants.APP_USER + "session object found"));
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
                    String lCity = betV.getCity();
                    String ss = lCity+"^"+lCounty+"^"+lStateCd+"^"+lCountry;
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
        } else if("Zip Code".equals(actConfigType)){
            try {
                BusEntityTerrViewVector betVV = pForm.getPostalCodes();
                for(int ii=0; ii<betVV.size(); ii++) {
                    BusEntityTerrView betV = (BusEntityTerrView) betVV.get(ii);
                    betV.setCheckedFl(false);
                    String lZip = betV.getPostalCode();
                    String lCountry = betV.getCountryCd();
                    String lCity = betV.getCity();
                    String ss = lZip+"^"+lCountry+"^"+lCity;
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
        }else if("Other".equals(actConfigType)){
            try {

                BusEntityTerrViewVector betVV = pForm.getPostalCodes();
                for(int ii=0; ii<betVV.size(); ii++) {

                    BusEntityTerrView betV = (BusEntityTerrView) betVV.get(ii);
                    betV.setCheckedFl(false);

                    String lZip = betV.getPostalCode();
                    String lCountry = betV.getCountryCd();

                    String ss = lZip + "^" + lCountry;
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
      StoreDeliveryScheduleMgrForm pForm)
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
  private static void resetSelected(StoreDeliveryScheduleMgrForm pForm)
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
                  String lCity = terrVv.getCity();
                  String ss = lCity+"^"+lCounty+"^"+lStateCd+"^"+lCountry;
                  selected[jj++] = ss;
              }
          }
          pForm.setSelected(selected);

      } else if ("Other".equals(actConfigType)){
          
          BusEntityTerrViewVector terrVvV = pForm.getPostalCodes();
          Iterator it = terrVvV.iterator();
          String[] selected = new String[terrVvV.size()];
          int i = 0;
          while(it.hasNext()){
              BusEntityTerrView betV = (BusEntityTerrView) it.next();
              selected[i++] = (betV.getPostalCode() + "^" + betV.getCountryCd());
          }

          pForm.setSelected(selected);

      }else {
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
                  String lCity = terrVv.getCity();
                  String ss = lPostalCode+"^"+lCountry+"^"+lCity;
                  selected[jj++] = ss;
              }
          }
          pForm.setSelected(selected);
      }
  }
  //-------------------------------------------------------------------------------
  public static ActionErrors selectAll(HttpServletRequest request,
      StoreDeliveryScheduleMgrForm pForm)
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
        String lCity = terrVv.getCity();
        String ss = lCity+"^"+lCounty+"^"+lStateCd+"^"+lCountry;
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
        String lCity = terrVv.getCity();
        String ss = lPostalCode+"^"+lCountry+"^"+lCity;
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
      StoreDeliveryScheduleMgrForm pForm)
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
          String lCity = terrVv.getCity();
          String ss = lCity+"^"+lCounty+"^"+lStateCd+"^"+lCountry;
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

    //-------------------------------------------------------------------------------
    public static ActionErrors selectConfigFunc(HttpServletRequest request,
                                                StoreDeliveryScheduleMgrForm pForm)
    {
        ActionErrors ae = new ActionErrors();
        pForm.setCounties(new BusEntityTerrViewVector());
        pForm.setPostalCodes(new BusEntityTerrViewVector());
        pForm.setAcctBusEntityList(null);
        ae = showDates(request, pForm);
        if(ae.size()>0) return ae;
        return ae;
    }

    //-------------------------------------------------------------------------------
    public static ActionErrors selectConfigType(HttpServletRequest request,
                                                StoreDeliveryScheduleMgrForm pForm)
    {
        ActionErrors ae = new ActionErrors();
        pForm.setCounties(new BusEntityTerrViewVector());
        pForm.setPostalCodes(new BusEntityTerrViewVector());
        pForm.setAcctBusEntityList(null);

        HttpSession session = request.getSession();
        StoreDeliveryScheduleMgrForm mainForm = (StoreDeliveryScheduleMgrForm) session.getAttribute("STORE_DELIVERY_SCHEDULE_FORM");
        String configType = mainForm.getConfigType();
        pForm.setConfigType(configType);

        if("Other".equals(configType)){
            searchTerritoryAll(request,  pForm);
        }
        ae = showDates(request, pForm);

        return ae;
    }

  public static ActionErrors accountSearch(HttpServletRequest request, ActionForm form)
     throws Exception
  {
     ActionErrors ae=new ActionErrors();
     HttpSession session = request.getSession();
     CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
     StoreDeliveryScheduleMgrForm sForm = (StoreDeliveryScheduleMgrForm) form;
     APIAccess factory = new APIAccess();
     Account acctountBean = factory.getAccountAPI();
     Distributor distributorBean = factory.getDistributorAPI();
     String fieldValue = sForm.getAcctSearchField();
     String fieldSearchType = sForm.getAcctSearchType();
     String searchGroupId = sForm.getSearchGroupId();
     ScheduleJoinView sjVw = sForm.getScheduleJoinData();
     BusEntitySearchCriteria crit = new BusEntitySearchCriteria();
     //crit.setResultLimit(Constants.MAX_ACCOUNTS_TO_RETURN);
     
     IdVector schedAcctIdV = 
        distributorBean.getDeliveryScheduleAccountIds(sjVw.getSchedule().getScheduleId(),null);

     if(!appUser.isaSystemAdmin() ) {
       crit.setStoreBusEntityIds(appUser.getUserStoreAsIdVector());
     }
     if(fieldValue!=null && fieldValue.trim().length()>0) {
       fieldValue = fieldValue.trim();
       if ("id".equals(fieldSearchType)) {
         crit.setSearchId(fieldValue);
       } else {
         if ("nameBegins".equals(fieldSearchType)) {
           crit.setSearchName(fieldValue);
           crit.setSearchNameType(BusEntitySearchCriteria.NAME_STARTS_WITH);
         } else {
           crit.setSearchName(fieldValue);
           crit.setSearchNameType(BusEntitySearchCriteria.NAME_CONTAINS);
         }
       }
     }
     if(searchGroupId !=null && searchGroupId.trim().length()>0 &&
            Integer.parseInt(searchGroupId) > 0 )             {
       crit.setSearchGroupId(searchGroupId.trim());
     }
     crit.setSearchForInactive(false);
   	 crit.setOrder(BusEntitySearchCriteria.ORDER_BY_NAME);

     if(!sForm.getConfAcctOnlyFl()) {
		   BusEntityDataVector acctBusEntDV = acctountBean.getAccountBusEntByCriteria(crit);        
       sForm.setAcctBusEntityList(acctBusEntDV);
       HashSet schAcctHS = new HashSet();
       for(Iterator iter=schedAcctIdV.iterator(); iter.hasNext();) {
         Integer idI = (Integer) iter.next();
         schAcctHS.add(idI);
       }
       int size = (acctBusEntDV.size()>schAcctHS.size())? schAcctHS.size():acctBusEntDV.size();
       int acctSelected[] = new int[size];
       int ind = 0;
       for(Iterator iter=acctBusEntDV.iterator(); iter.hasNext();) {
         BusEntityData beD = (BusEntityData) iter.next();
         int acctId = beD.getBusEntityId();
         Integer acctIdI = new Integer(acctId);
         if(schAcctHS.contains(acctIdI)) {
           acctSelected[ind++] = acctId;
         }
       }
        int[] acctSelected1 = new int[ind];
        for(int ii=0; ii<ind; ii++) {
          acctSelected1[ii] = acctSelected[ii]; 
        }
        sForm.setAcctSelected(acctSelected1);
     } else {//configured only
       BusEntityDataVector acctBusEntDV = null;
       if(schedAcctIdV.size()>0) {
         crit.setAccountBusEntityIds(schedAcctIdV);
		     acctBusEntDV = acctountBean.getAccountBusEntByCriteria(crit);  
       } else {
         acctBusEntDV = new BusEntityDataVector();
       }
       sForm.setAcctBusEntityList(acctBusEntDV);
       int[] acctSelected = new int[acctBusEntDV.size()];
       int ind = 0;
       for(Iterator iter=acctBusEntDV.iterator(); iter.hasNext();) {
         BusEntityData beD = (BusEntityData) iter.next();
         int acctId = beD.getBusEntityId();
         acctSelected[ind++] = acctId;
       }
       sForm.setAcctSelected(acctSelected);
     }
    ae = showDates(request, sForm);
    if(ae.size()>0) return ae;
     
     return ae;
 }

  public static ActionErrors updateAcctConfigured(HttpServletRequest request, ActionForm form) 
     throws Exception
  {
     ActionErrors ae=new ActionErrors();
     HttpSession session = request.getSession();
     CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
     String userName = appUser.getUser().getUserName();
     StoreDeliveryScheduleMgrForm sForm = (StoreDeliveryScheduleMgrForm) form;
     APIAccess factory = new APIAccess();
     Distributor distributorBean = factory.getDistributorAPI();

     ScheduleJoinView sjVw = sForm.getScheduleJoinData();
     int scheduleId = sjVw.getSchedule().getScheduleId();

     BusEntityDataVector acctBusEntDV = sForm.getAcctBusEntityList();
     HashSet schAcctHS = new HashSet();
     IdVector acctConfig = new IdVector();
     int[] acctSelected = sForm.getAcctSelected();
     for(int ii=0; ii<acctSelected.length; ii++) {
       Integer acctIdI = new Integer(acctSelected[ii]);
       schAcctHS.add(acctIdI);
       acctConfig.add(acctIdI);
     }
     
     IdVector acctNotConfig = new IdVector();
     for(Iterator iter=acctBusEntDV.iterator(); iter.hasNext();) {
       BusEntityData beD = (BusEntityData) iter.next();
       Integer acctIdI = new Integer(beD.getBusEntityId());
       if(!schAcctHS.contains(acctIdI)) {
         acctNotConfig.add(acctIdI);
       }
     }
     distributorBean.updateDeliveryScheduleAccounts(scheduleId, acctConfig, acctNotConfig,userName); 
     ae = accountSearch(request, form);
     ae = showDates(request, sForm);
     if(ae.size()>0) return ae;
     return ae;
 }


    private static void checkDates(ActionErrors ae,
            StoreDeliveryScheduleMgrForm pForm) {
        String sEffDate = pForm.getEffDate();
        String sExpDate = pForm.getExpDate();
        Date dEffDate = null;
        Date dExpDate = null;
        try {
            dEffDate = Utility.parseDate(sEffDate, "MM/dd/yyyy", false);
            dExpDate = Utility.parseDate(sExpDate, "MM/dd/yyyy", false);
        } catch (Exception e) {
            e.printStackTrace();
            ae.add("error", new ActionError("error.simpleGenericError", "Can't parse dates!"));
        }
        if (Utility.isSet(sEffDate) == false) {
            ae.add("error", new ActionError("error.simpleGenericError", "Empty 'Effective Date'!"));
        } else if (dEffDate == null) {
            ae.add("error", new ActionError("error.simpleGenericError", "'Effective Date' must have correct format (MM/DD/YYYY)!"));
        }
        if (Utility.isSet(sExpDate) && dExpDate == null) {
            ae.add("error", new ActionError("error.simpleGenericError", "'Expiration Date' must have correct format (MM/DD/YYYY)!"));
        }
        if (dEffDate != null && dExpDate != null
                && dEffDate.compareTo(dExpDate) >= 0) {
            ae.add("error", new ActionError("error.simpleGenericError", "'Expiration Date' must be greater than 'Effective Date'!"));
        }
    }
}
