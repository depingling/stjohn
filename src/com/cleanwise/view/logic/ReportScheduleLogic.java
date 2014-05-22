

package com.cleanwise.view.logic;

import java.io.OutputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Iterator;
import java.util.Vector;
import java.util.HashMap;
import java.util.Map;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;
import java.math.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import com.cleanwise.view.utils.*;
import com.cleanwise.service.api.*;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.*;
import com.cleanwise.view.forms.ReportScheduleForm;
import com.cleanwise.view.logic.AnalyticReportLogic;
//import jxl.*;
//import jxl.write.*;
import java.text.SimpleDateFormat;
import java.util.*;
import com.cleanwise.view.i18n.ClwI18nUtil;


/**
 * <code>ReportScheduleLogic</code> implements the logic needed to
 * manipulate order schedules.
 *
 * @author Yuriy Kupershmidt
 */
public class ReportScheduleLogic {

	 private static final Logger log = Logger.getLogger(ReportScheduleLogic.class);
	 private static final int SHOW_FILTER_NUM = 50;

    /**
     * <code>init</code> method.
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */
    public static void init(HttpServletRequest request,
    ReportScheduleForm pForm)
    throws Exception {
      try {
        APIAccess factory = new APIAccess();
        Report reportEjb = factory.getReportAPI();
        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        int userId = appUser.getUser().getUserId();
        Map filter = new HashMap();
        String categoryFilter = pForm.getCategoryFilter();
        if(Utility.isSet(categoryFilter)){
          filter.put("REPORT_CATEGORY",categoryFilter);
        }
        String nameFilter = pForm.getReportFilter();
        if(Utility.isSet(nameFilter)){
          filter.put("REPORT_NAME",nameFilter);
        }
        ReportScheduleViewVector schedules = reportEjb.getReportSchedules( filter, appUser.getUser());
//        ReportScheduleViewVector schedules = reportEjb.getReportSchedules(filter);
        schedules = sortByCategoryName(schedules);
        pForm.setReportSchedules(schedules);
      }catch (Exception exc) {
       exc.printStackTrace();
       throw exc;
      }
    }

   //--------------------------------------------------------------------------
    public static void sort(HttpServletRequest request,
    ReportScheduleForm pForm)
    throws Exception {
      String sortField = request.getParameter("field");
      ReportScheduleViewVector schedules = pForm.getReportSchedules();
      if("category".equals(sortField)) {
        schedules = sortByCategoryName(schedules);
      } else if("id".equals(sortField)) {
        schedules.sort("ReportScheduleId");
      } else if("report".equals(sortField)) {
        schedules.sort("ReportName");
      } else if("schedule".equals(sortField)) {
        schedules.sort("ScheduleName");
      }
      pForm.setReportSchedules(schedules);
      return;
    }


    private static ReportScheduleViewVector
                     sortByCategoryName(ReportScheduleViewVector schedules) {
      if(schedules.size()<=1) return  schedules;
      Object[] reportA = schedules.toArray();

      for(int ii=0; ii<reportA.length-1; ii++) {
        boolean exitFl = true;
        for(int jj=0; jj<reportA.length-ii-1; jj++) {
           ReportScheduleView grVw1 = (ReportScheduleView) reportA[jj];
           ReportScheduleView grVw2 = (ReportScheduleView) reportA[jj+1];
           String cat1 = grVw1.getReportCategory();
           String cat2 = grVw2.getReportCategory();
           if(cat1==null) cat1="";
           if(cat2==null) cat2="";
           int comp = cat1.compareTo(cat2);
           if(comp>0) {
             reportA[jj] = grVw2;
             reportA[jj+1] = grVw1;
             exitFl = false;
             continue;
           }
           if(comp==0) {
             String name1 = grVw1.getReportName();
             String name2 = grVw2.getReportName();
             if(name1==null) name1="";
             if(name2==null) name2="";
             comp = name1.compareTo(name2);
             if(comp>0) {
               reportA[jj] = grVw2;
               reportA[jj+1] = grVw1;
               exitFl = false;
               continue;
             }
           }
        }
        if(exitFl) break;
      }
      schedules = new ReportScheduleViewVector();
      for(int ii=0; ii<reportA.length; ii++) {
        schedules.add((ReportScheduleView) reportA[ii]);
      }
      return schedules;
    }



    //---------------------------------------------------------------------------------------
    public static void clearFilter(HttpServletRequest request,
                                            ReportScheduleForm pForm)
    throws Exception {
      pForm.setCategoryFilter("");
      pForm.setReportFilter("");
      init(request,pForm);
    }

    //---------------------------------------------------------------------------------------
    public static ActionErrors getSchedule(HttpServletRequest request, ReportScheduleForm pForm)
    throws Exception
    {
      try {
      ActionErrors errors = new ActionErrors();
      CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
      APIAccess factory = new APIAccess();
      Report reportEjb = factory.getReportAPI();
      String scheduleIdS = request.getParameter("id");
      int scheduleId = 0;
      try {
        scheduleId = Integer.parseInt(scheduleIdS);
      }catch (Exception exc) {}
      if(scheduleId<=0) {
         String errorMess = "Wrong report schedule id format: "+scheduleIdS;
         errors.add("error",
                         new ActionError("error.simpleGenericError",errorMess));
         return errors;
      }
      ReportScheduleJoinView rsjVw = reportEjb.getReportSchedule(scheduleId);
      //-----init Generic Controls from Schedule---------
      GenericReportControlViewVector genericReportControls = rsjVw.getReportControls();
      populateControlFilterMap(request, genericReportControls);
      populateLocateFilters(reportEjb, pForm, genericReportControls);
      //-------------------------------
      pForm.setReportScheduleJoin(rsjVw);
      errors = scatterSchedule(request,pForm);
      if(errors.size()>0) {
        return errors;
      }
      errors = showDates(request, pForm);
      if(errors.size()>0) {
        return errors;
      }
      ReportSchedUserShareViewVector userVwV = pForm.getUsers();
      userVwV.sort("UserId");
      ArrayList userNotifIds = new ArrayList();
      for(int ii=0; ii<userVwV.size(); ii++) {
        ReportSchedUserShareView rsusVw = (ReportSchedUserShareView) userVwV.get(ii);
        int id = rsusVw.getUserId();
        if(rsusVw.getNotifyFl()) {
          userNotifIds.add(""+id);
        }
        if (rsusVw.getReportOwnerFl()) {
          pForm.setReportOwner(rsusVw.getUserId());
        }
      }
      String[] userA = new String[userNotifIds.size()];
      for(int ii=0; ii < userNotifIds.size(); ii++) {
        userA[ii]=(String) userNotifIds.get(ii);
      }
      pForm.setUserToNotify(userA);

      ReportSchedGroupShareViewVector groupVwV = pForm.getGroups();
      ArrayList groupNotifIds = new ArrayList();
      for(int ii=0; ii<groupVwV.size(); ii++) {
        ReportSchedGroupShareView rsgsVw = (ReportSchedGroupShareView) groupVwV.get(ii);
        int id = rsgsVw.getGroupId();
        if(rsgsVw.getNotifyFl()) {
          groupNotifIds.add(""+id);
        }
      }
      String[] groupA = new String[groupNotifIds.size()];
      for(int ii=0; ii < groupNotifIds.size(); ii++) {
        groupA[ii]=(String) groupNotifIds.get(ii);
      }
      pForm.setGroupToNotify(groupA);

      return errors;
      } catch (Exception exc) {
        exc.printStackTrace();
        throw exc;
      }

    }

    public static void populateControlFilterMap (HttpServletRequest request,  GenericReportControlViewVector genericReportControls)  throws Exception {
      HttpSession session = request.getSession();
      if (session.getAttribute("REPORT_CONTROL_FILTER_MAP") == null) {
        session.setAttribute("REPORT_CONTROL_FILTER_MAP", new HashMap());
      }
      HashMap controlFiltersMap = (HashMap) session.getAttribute("REPORT_CONTROL_FILTER_MAP");
      if (controlFiltersMap == null){
        controlFiltersMap = new HashMap();
      }
      for(int ii=0; ii<genericReportControls.size(); ii++) {
        GenericReportControlView grcVw = (GenericReportControlView) genericReportControls.get(ii);
        if (grcVw != null){
          String controlName = grcVw.getName();
          Object controlValue = grcVw.getValue();
          controlFiltersMap.put(controlName, controlValue);
        }
      }

    }

    public static void populateLocateFilters (Report reportEjb, ReportScheduleForm pForm, GenericReportControlViewVector genericReportControls)  throws Exception {
      for(int ii=0; ii<genericReportControls.size(); ii++) {
        GenericReportControlView grcVw = (GenericReportControlView) genericReportControls.get(ii);
        if (grcVw != null) {
          String controlName = grcVw.getName();
          Object controlValue = grcVw.getValue();
          Object filter = null;
          if (Utility.isSet(controlName)){
           if (controlName.contains("LOCATE_ACCOUNT_MULTI")){
             filter = reportEjb.getLocateFilterByIds(controlName, (String)controlValue );
             AccountUIViewVector accountFilter =(AccountUIViewVector) filter;
             pForm.setAccountFilter(accountFilter);
           }else if (controlName.contains("LOCATE_SITE_MULTI")){
             filter = reportEjb.getLocateFilterByIds(controlName, (String)controlValue );
             SiteViewVector siteFilter =(SiteViewVector) filter;
             pForm.setSiteFilter(siteFilter);
           }else if (controlName.contains("LOCATE_ITEM")){
             filter = reportEjb.getLocateFilterByIds(controlName, (String)controlValue );
             ItemViewVector itemFilter = (ItemViewVector) filter;
             pForm.setItemFilter(itemFilter);
           }else if (controlName.contains("LOCATE_DISTRIBUTOR")){
             filter = reportEjb.getLocateFilterByIds(controlName, (String)controlValue );
             DistributorDataVector distFilter = (DistributorDataVector) filter;
             pForm.setDistFilter(distFilter);
           }else if (controlName.contains("LOCATE_DISTRIBUTOR")){
             filter = reportEjb.getLocateFilterByIds(controlName, (String)controlValue );
             ManufacturerDataVector manufFilter = (ManufacturerDataVector) filter;
             pForm.setManufFilter(manufFilter);
           }
           if (controlName.startsWith("ACCOUNT_MULTI")){
             String[] runForAccounts = (Utility.isSet((String)controlValue)) ? ((String)controlValue).split(","):null;
             pForm.setRunForAccounts(runForAccounts);
           }

         }
       }
      }

    }


//------------------------------------------------------------
   private String mWeekCycle = "1";
   private int[] mWeekDays = new int[0];  // Sunday - 1, ... Saturday - 6

   private String mMonthDayCycle = "1";
   private int[] mMonthDays = new int[0]; //1 ... 28

   private String mMonthWeekCycle = "1";
   private int mMonthWeekDay = 2; // Sunday - 1, ... Saturday - 7, day - 8
   private int[] mMonthWeeks = new int[0]; //first -1, second -2, third -3, forth -4, last -5

   private String mAlsoDates = "";
   private String mExcludeDates = "";

  //private int mMonthWeekFrequency = 0;

  private GregorianCalendar mCalendar = null;
  private int mLastReqMonth = -1;
  private int mLastReqMonthDay = -1;
  private int mLastReqFirstWeekDay = -1;
  private int mNextScheduleIndex = 0;
  private int mNextScheduleRelMonth = 0;
  private int mNextScheduleMonthDay = 0;
  private List mCalendarScheduleDates = new ArrayList();

   //---------------------------------------------------------------------------------------
    public static ActionErrors delSchedules(HttpServletRequest request, ReportScheduleForm pForm)
    throws Exception
    {
      try {
      ActionErrors errors = new ActionErrors();
      CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
      APIAccess factory = new APIAccess();
      Report reportEjb = factory.getReportAPI();
      String[] schedulesToDelA = pForm.getScheduleSelected();
      if(schedulesToDelA==null || schedulesToDelA.length==0) {
         String errorMess = "No schedule selected";
         errors.add("error",
                         new ActionError("error.simpleGenericError",errorMess));
         return errors;
      }
      IdVector schedulesToDelIdV = new IdVector();
      for(int ii=0; ii<schedulesToDelA.length; ii++) {
        String scheduleIdS = schedulesToDelA[ii];
        int scheduleId = Integer.parseInt(scheduleIdS);
        schedulesToDelIdV.add(new Integer(scheduleId));
      }
      reportEjb.deleteReportSchedules(schedulesToDelIdV);
      init(request,pForm);
      return errors;
      } catch (Exception exc) {
        exc.printStackTrace();
        throw exc;
      }
    }

    //---------------------------------------------------------------------------------------
    public static ActionErrors addSchedule(HttpServletRequest request, ReportScheduleForm pForm)
    throws Exception
    {
      try {
      ActionErrors errors = new ActionErrors();
      CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
      if (Utility.isSet(request.getParameter("reportId"))){
        int repId = Integer.parseInt(request.getParameter("reportId"));
        pForm.setNewScheduleReportId(repId);
      }
      int reportId = pForm.getNewScheduleReportId();
      APIAccess factory = new APIAccess();
      Report reportEjb = factory.getReportAPI();
      GenericReportData grD = null;
      GenericReportControlViewVector grcVwV = null;
      if (reportId > 0){
        // init controls
        grD = reportEjb.getGenericReport(reportId);
        grcVwV = reportEjb.getGenericReportControls(reportId);
         //try to populate Generic Controls values from session Filter
         initControlsFromSessionFilters(request,  grcVwV );
      }

      ReportScheduleJoinView reportScheduleJoinVw = ReportScheduleJoinView.createValue();
      reportScheduleJoinVw.setReport(grD);
      reportScheduleJoinVw.setReportControls(grcVwV);
      ReportScheduleData rsD = ReportScheduleData.createValue();
      if (grD != null){
        rsD.setGenericReportId(grD.getGenericReportId());
      }
      rsD.setReportScheduleStatusCd(RefCodeNames.REPORT_SCHEDULE_STATUS_CD.ACTIVE);
      rsD.setReportScheduleRuleCd(RefCodeNames.REPORT_SCHEDULE_RULE_CD.WEEK);
      rsD.setCycle(1);
      reportScheduleJoinVw.setSchedule(rsD);
      reportScheduleJoinVw.setScheduleDetails(new ReportScheduleDetailDataVector());

      ReportSchedGroupShareViewVector groupV= new ReportSchedGroupShareViewVector();
      ReportSchedUserShareViewVector userV = new ReportSchedUserShareViewVector();
      String userType = appUser.getUser().getUserTypeCd();
      int appUserId = appUser.getUser().getUserId();
      if( RefCodeNames.USER_TYPE_CD.REPORTING_USER.equals(userType) ) {
         UserData uD = appUser.getUser();
         ReportSchedUserShareView user = ReportSchedUserShareView.createValue();
         user.setUserId(uD.getUserId());
         user.setUserFirstName(uD.getFirstName());
         user.setUserLastName(uD.getLastName());
         user.setUserLoginName(uD.getUserName());
         user.setUserTypeCd(uD.getUserTypeCd());
         user.setUserStatusCd(uD.getUserStatusCd());
         user.setNotifyFl(false);
         user.setReportOwnerFl(true);
         pForm.setReportOwner(appUserId);
         userV.add(user) ;
      }
      reportScheduleJoinVw.setGroups(groupV);
      reportScheduleJoinVw.setUsers(userV);

      pForm.setReportScheduleJoin(reportScheduleJoinVw);
      // Locate Filters
      pForm.setAccountFilter(null);
      pForm.setSiteFilter(null);
      pForm.setItemFilter(null);
      pForm.setManufFilter(null);
      pForm.setDistFilter(null);
      /////// Calendar
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


      return errors;
      } catch (Exception exc) {
        exc.printStackTrace();
        throw exc;
      }
    }
    //-------------------------------------------------------------------------------
    private static void initControlsFromSessionFilters(HttpServletRequest request, GenericReportControlViewVector grcVwV ) {
        HashMap controlFiltersMap = getReportControlFilterMap (request);
       //try to populate Generic Controls values from session Filter
        if (grcVwV != null) {
          for (int j = 0; j < grcVwV.size(); j++) {
            GenericReportControlView grc = (GenericReportControlView) grcVwV.get(j);
            String controlName = grc.getName();
            Object controlVal = controlFiltersMap.get(controlName);
            if (controlVal != null && controlVal instanceof String) {
              grc.setValue( (String) controlVal);
            }
          }
        }
    }

    public static IdVector getUserSelectedFilter (HttpServletRequest request)  throws Exception {
       return getUserSelectedFilter ( request, Constants.USER_FILTER_NAME);
     }
     public static IdVector getUserSelectedFilter (HttpServletRequest request, String filterName)  throws Exception {
        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        UserData selectedUser = null;
        Object obj = getControlFilter(request, filterName);

        if (obj != null && obj instanceof UserData) {
          selectedUser = (UserData) obj;
        }
        if (selectedUser == null) {
          selectedUser = appUser.getUser();
        }
        if (selectedUser.getUserTypeCd().equals(RefCodeNames.USER_TYPE_CD.ACCOUNT_ADMINISTRATOR)||
            selectedUser.getUserTypeCd().equals(RefCodeNames.USER_TYPE_CD.ADMINISTRATOR)||
            selectedUser.getUserTypeCd().equals(RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR)||
            selectedUser.getUserTypeCd().equals(RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR) ||
            (selectedUser.getUserTypeCd().equals(RefCodeNames.USER_TYPE_CD.REPORTING_USER) &&
              selectedUser.getUserRoleCd().contains("RepOA^")) ){
          return null;
        }
        Integer selectedUserId = new Integer(selectedUser.getUserId());

        IdVector ids = null;
        ids = new IdVector();
        ids.add(selectedUserId);
        return null;  // temporary !!! Only for DEMO !
//       return ids;

     }

    private static HashMap getReportControlFilterMap(HttpServletRequest request) {
         HttpSession session = request.getSession();
         if (session.getAttribute("REPORT_CONTROL_FILTER_MAP") == null) {
           session.setAttribute("REPORT_CONTROL_FILTER_MAP", new HashMap());
         }
         HashMap controlFiltersMap =(HashMap)session.getAttribute("REPORT_CONTROL_FILTER_MAP");
         return controlFiltersMap;
      }

   public static Object getControlFilter (HttpServletRequest request,  String controlName)  throws Exception {
           HashMap controlFiltersMap = getReportControlFilterMap (request);
           Object controlVal = null;
           if ( controlFiltersMap != null){
             controlVal = controlFiltersMap.get(controlName);
           }

           return controlVal;
    }
    //---------------------------------------------------------------------------------------
    public static ActionErrors addUser(HttpServletRequest request, ReportScheduleForm pForm)
    throws Exception
    {
      try {
      ActionErrors errors = new ActionErrors();
      errors = assembleSchedule(request,pForm);
      CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
      APIAccess factory = new APIAccess();
      User userEjb = factory.getUserAPI();
      int userId = pForm.getUserToAdd();
      ReportSchedUserShareViewVector uVwV = pForm.getUsers();
      boolean foundFl = false;
      for(int ii=0; ii<uVwV.size(); ii++) {
        ReportSchedUserShareView uVw = (ReportSchedUserShareView) uVwV.get(ii);
        int id = uVw.getUserId();
        if(id==userId) {
          foundFl = true;
          break;
        }
      }
      if(!foundFl) {
        UserData uD = userEjb.getUser(userId);
         ReportSchedUserShareView rsusVw = ReportSchedUserShareView.createValue();
         rsusVw.setReportScheduleId(0);
         rsusVw.setUserId(uD.getUserId());
         rsusVw.setUserFirstName(uD.getFirstName());
         rsusVw.setUserLastName(uD.getLastName());
         rsusVw.setUserLoginName(uD.getUserName());
         rsusVw.setUserTypeCd(uD.getUserTypeCd());
         rsusVw.setUserStatusCd(uD.getUserStatusCd());
         rsusVw.setNotifyFl(false);
         rsusVw.setReportOwnerFl(false);
        uVwV.add(0,rsusVw);
      }
      pForm.setUsers(uVwV);
      return errors;
      } catch (Exception exc) {
        exc.printStackTrace();
        throw exc;
      }
    }

    public static ActionErrors addUsers(HttpServletRequest request,  ActionForm form)
     throws Exception
     {
       ActionErrors errors = new ActionErrors();
       ReportScheduleForm pForm = (ReportScheduleForm)form;
       IdVector ids = Utility.toIdVector(pForm.getUserFilter());
       try {
//         errors = assembleSchedule(request,pForm);
         CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
         APIAccess factory = new APIAccess();
         User userEjb = factory.getUserAPI();
         ReportSchedUserShareViewVector uVwV = pForm.getUsers();

         for (Iterator iter = ids.iterator(); iter.hasNext(); ) {
           Integer it = (Integer) iter.next();
           int userId = it.intValue();
           boolean foundFl = false;
           for (int ii = 0; ii < uVwV.size(); ii++) {
             ReportSchedUserShareView uVw = (ReportSchedUserShareView) uVwV.get(ii);
             int id = uVw.getUserId();
             if (id == userId) {
               foundFl = true;
               break;
             }
           }
           if (!foundFl) {
             UserData uD = userEjb.getUser(userId);
             ReportSchedUserShareView rsusVw = ReportSchedUserShareView.createValue();
             rsusVw.setReportScheduleId(0);
             rsusVw.setUserId(uD.getUserId());
             rsusVw.setUserFirstName(uD.getFirstName());
             rsusVw.setUserLastName(uD.getLastName());
             rsusVw.setUserLoginName(uD.getUserName());
             rsusVw.setUserTypeCd(uD.getUserTypeCd());
             rsusVw.setUserStatusCd(uD.getUserStatusCd());
             rsusVw.setNotifyFl(false);
             rsusVw.setReportOwnerFl(false);
             uVwV.add(0, rsusVw);
           }
         }
         pForm.setUsers(uVwV);
         return errors;
         } catch (Exception exc) {
           exc.printStackTrace();
           throw exc;
         }
       }

       public static ActionErrors addGroups(HttpServletRequest request, ActionForm form)
       throws Exception
       {
         ActionErrors errors = new ActionErrors();
         ReportScheduleForm pForm = (ReportScheduleForm)form;
         IdVector ids = Utility.toIdVector(pForm.getGroupFilter());
         try {
 //          errors = assembleSchedule(request,pForm);
           CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
           APIAccess factory = new APIAccess();
           Group groupEjb = factory.getGroupAPI();
//           int groupId = pForm.getGroupToAdd();
           ReportSchedGroupShareViewVector gVwV = pForm.getGroups();

           for (Iterator iter = ids.iterator(); iter.hasNext(); ) {
             Integer it = (Integer) iter.next();
             int groupId = it.intValue();
             boolean foundFl = false;
             for (int ii = 0; ii < gVwV.size(); ii++) {
               ReportSchedGroupShareView gVw = (ReportSchedGroupShareView) gVwV.get(ii);
               int id = gVw.getGroupId();
               if (id == groupId) {
                 foundFl = true;
                 break;
               }
             }
             if (!foundFl) {
               GroupData gD = groupEjb.getGroupDetail(groupId);
               ReportSchedGroupShareView rsgsVw = ReportSchedGroupShareView.createValue();
               rsgsVw.setReportScheduleId(0);
               rsgsVw.setGroupId(gD.getGroupId());
               rsgsVw.setGroupShortDesc(gD.getShortDesc());
               rsgsVw.setGroupTypeCd(gD.getGroupTypeCd());
               rsgsVw.setGroupStatusCd(gD.getGroupStatusCd());
               rsgsVw.setNotifyFl(false);
               gVwV.add(0, rsgsVw);
             }
           }
           pForm.setGroups(gVwV);
           return errors;
         } catch (Exception exc) {
           exc.printStackTrace();
           throw exc;
         }
    }
    //---------------------------------------------------------------------------------------
    public static ActionErrors addGroup(HttpServletRequest request, ReportScheduleForm pForm)
    throws Exception
    {
      try {
      ActionErrors errors = new ActionErrors();
      errors = assembleSchedule(request,pForm);
      CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
      APIAccess factory = new APIAccess();
      Group groupEjb = factory.getGroupAPI();
      int groupId = pForm.getGroupToAdd();
      ReportSchedGroupShareViewVector gVwV = pForm.getGroups();
      boolean foundFl = false;
      for(int ii=0; ii<gVwV.size(); ii++) {
        ReportSchedGroupShareView gVw = (ReportSchedGroupShareView) gVwV.get(ii);
        int id = gVw.getGroupId();
        if(id==groupId) {
          foundFl = true;
          break;
        }
      }
      if(!foundFl) {
        GroupData gD = groupEjb.getGroupDetail(groupId);
         ReportSchedGroupShareView rsgsVw = ReportSchedGroupShareView.createValue();
         rsgsVw.setReportScheduleId(0);
         rsgsVw.setGroupId(gD.getGroupId());
         rsgsVw.setGroupShortDesc(gD.getShortDesc());
         rsgsVw.setGroupTypeCd(gD.getGroupTypeCd());
         rsgsVw.setGroupStatusCd(gD.getGroupStatusCd());
         rsgsVw.setNotifyFl(false);
        gVwV.add(0,rsgsVw);
      }
      pForm.setGroups(gVwV);
      return errors;
      } catch (Exception exc) {
        exc.printStackTrace();
        throw exc;
      }
    }

    //---------------------------------------------------------------------------------------
    public static ActionErrors delUsers(HttpServletRequest request, ReportScheduleForm pForm)
    throws Exception
    {
      try {
      ActionErrors errors = new ActionErrors();
      errors = assembleSchedule(request,pForm);
      CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
      String[] userA = pForm.getUserSelected();
      if(userA==null || userA.length==0) {
         String errorMess = "No user selected";
         errors.add("error",
                         new ActionError("error.simpleGenericError",errorMess));
         return errors;
      }
      ReportSchedUserShareViewVector uVwV = pForm.getUsers();
      for(int ii=0; ii<userA.length; ii++) {
        String userIdS = userA[ii];
        int userId = Integer.parseInt(userIdS);
        for(int jj=0; jj<uVwV.size(); jj++) {
          ReportSchedUserShareView uVw = (ReportSchedUserShareView) uVwV.get(jj);
          int id = uVw.getUserId();
          if(id==userId) {
            uVwV.remove(jj);
            break;
          }
        }
      }
      pForm.setUsers(uVwV);
      return errors;
      } catch (Exception exc) {
        exc.printStackTrace();
        throw exc;
      }
    }

    //---------------------------------------------------------------------------------------
    public static ActionErrors delGroups(HttpServletRequest request, ReportScheduleForm pForm)
    throws Exception
    {
      try {
      ActionErrors errors = new ActionErrors();
      errors = assembleSchedule(request,pForm);
      CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
      String[] groupA = pForm.getGroupSelected();
      if(groupA==null || groupA.length==0) {
         String errorMess = "No group selected";
         errors.add("error",
                         new ActionError("error.simpleGenericError",errorMess));
         return errors;
      }
      ReportSchedGroupShareViewVector gVwV = pForm.getGroups();
      for(int ii=0; ii<groupA.length; ii++) {
        String groupIdS = groupA[ii];
        int groupId = Integer.parseInt(groupIdS);
        for(int jj=0; jj<gVwV.size(); jj++) {
          ReportSchedGroupShareView gVw = (ReportSchedGroupShareView) gVwV.get(jj);
          int id = gVw.getGroupId();
          if(id==groupId) {
            gVwV.remove(jj);
            break;
          }
        }
      }
      pForm.setGroups(gVwV);
      return errors;
      } catch (Exception exc) {
        exc.printStackTrace();
        throw exc;
      }
    }

    // method getDateFmt finds the value of the DATE_FMT parameter
    private static String getDateFmt(GenericReportControlViewVector controlViewVector) {
    	//String stringDateFMT = "";
    	String value = "";
    	for(int j=0; j<controlViewVector.size(); j++) {
        	GenericReportControlView genericControls =
                (GenericReportControlView) controlViewVector.get(j);
        	String genericControlName = genericControls.getName().trim();
        	if (genericControlName.equals("DATE_FMT")) {
                    value = genericControls.getValue();
        		//stringDateFMT = genericControlName;
        		break;
        	}
        }
        //String value = grc.getValue();
    	//return stringDateFMT;
        if (!Utility.isSet(value)) {
          value = Constants.SIMPLE_DATE_PATTERN;
        }
    	log.info("Passed Date Format = " + value);
    	return value;
    }

    private static ActionErrors checkDateFmt(String pName, String pDateFmt, ActionErrors errors) {

    	log.info("************SVC: pDateFmt = " + pDateFmt);

    	String cleanDateFmt = pDateFmt.toLowerCase().trim();
    	log.info("cleanDateFmt = " + cleanDateFmt);
    	if (cleanDateFmt.equals("mm/dd/yyyy")) {

    	} else if (cleanDateFmt.equals("dd/mm/yyyy")) {

    	} else {
    		String mesg = "Date Format " + pDateFmt + " is wrong. Two Date Formats are currently available: MM/dd/yyyy and dd/MM/yyyy.";
            errors.add(pName, new ActionError("error.simpleGenericError", mesg));
            log.info("***************SVC: error message = " + mesg);
    	}
        return errors;
    }

    //---------------------------------------------------------------------------------------
    public static ActionErrors saveSchedule(HttpServletRequest request, ReportScheduleForm pForm)
    throws Exception
    {
      try {
      ActionErrors errors = new ActionErrors();
      //Check parameters
      CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
      String userName = appUser.getUser().getUserName();
      int userId = appUser.getUser().getUserId();
      APIAccess factory = new APIAccess();
      Report reportEjb = factory.getReportAPI();
      GenericReportControlViewVector grcVV = pForm.getReportScheduleJoin().getReportControls();
      HashMap controlValues = new HashMap();
      Date beginDate = null;
	  Date endDate = null;
	  String dateFmt = getDateFmt(grcVV); // get date format
	  // check the correctness of the date format(DATE_FMT), passed from the screen as a parameter
      if (Utility.isSet(dateFmt) ){
    	  errors = checkDateFmt("DATE_FMT", dateFmt, errors);
    	  if (errors.size() > 0) {
    		  return errors;
          }
      }
      SimpleDateFormat df = new SimpleDateFormat(dateFmt);

      for(int ii=0; ii<grcVV.size(); ii++) {
        GenericReportControlView grc = (GenericReportControlView) grcVV.get(ii);
        String name = grc.getName();
        String label = grc.getLabel();
        if(label==null && label.trim().length()==0) label = name;
        String mf = grc.getMandatoryFl();
        if(mf!=null) mf = mf.toUpperCase();
         boolean mandatoryFl = true;
         if("N".equals(mf) || "NO".equals(mf) ||"0".equals(mf) ||"F".equals(mf) ||"FALSE".equals(mf)) {
            mandatoryFl = false;
         }
         if(name!=null && name.trim().toUpperCase().endsWith("_OPT")) {
           mandatoryFl = false;
         }
         String value = grc.getValue();
         if("CUSTOMER".equalsIgnoreCase(name)) {
              mandatoryFl = false;
              if(value==null ||value.trim().length()==0) {
              value = ""+userId;
              grc.setValue(value);
           }
        }
        if(name.endsWith("_MULTI") || name.endsWith("_MULTI_OPT") ){
              value = (value==null) ? "" : value;
        }

        //--------------------------------------------------
        if (name.contains("LOCATE_ACCOUNT_MULTI")) {
          AccountUIViewVector accounts = pForm.getAccountFilter();
          String strAccountIds = "";
          value = "";
          if (  accounts != null && accounts.size() > 0) {
            for (int accountIndex = 0; accountIndex < accounts.size(); ++accountIndex) {
              AccountUIView account = (AccountUIView) accounts.get(accountIndex);
              String strAccountId ="";
              if (account.getAccountDimIds() != null && account.getBusEntity().getBusEntityId() < 0) {
                strAccountId = account.getAccountDimIds(); // For DataWerehouse search
              } else{
                strAccountId = Integer.toString(account.getBusEntity().getBusEntityId());
              }
            //don't need to check that selected account associated with user's store
            //checkParamSecurity("ACCOUNT", errors, strAccountId, factory, appUser, grc);
            if (accountIndex > 0) {
                strAccountIds += ",";
              }
              strAccountIds += strAccountId;
            }
            value = strAccountIds;
          }
          grc.setValue(strAccountIds);

        }
        //-----
        if (name.contains("LOCATE_MANUFACTURER")){
            ManufacturerDataVector manufV = pForm.getManufFilter();
            String strManufIds = "";
            value = "";
            if (manufV != null && manufV.size() > 0) {
              for (int indexM = 0; indexM < manufV.size(); indexM++) {
                ManufacturerData manuf = (ManufacturerData) manufV.get(indexM);
                String strId = Integer.toString(manuf.getBusEntity().getBusEntityId());
                if (indexM > 0) {
                  strManufIds += ",";
                }
                strManufIds += strId;
              }
              value = strManufIds;
            }
            grc.setValue(value);
        }

        if (name.contains("DW_LOCATE_DISTRIBUTOR")){
            DistributorDataVector distV = pForm.getDistFilter();
            String strDistIds = "";
            value = "";
            if (distV != null && distV.size() > 0) {
              for (int indexD = 0; indexD < distV.size(); indexD++) {
                DistributorData dist = (DistributorData) distV.get(indexD);
                String strId = Integer.toString(dist.getBusEntity().getBusEntityId());
                if (indexD > 0) {
                  strDistIds += ",";
                }
                strDistIds += strId;
              }
              value = strDistIds;
            }
            grc.setValue(value);
        }
        if (name.contains("LOCATE_ITEM")){
         ItemViewVector itemV = pForm.getItemFilter();
         String strItemIds = "";
         value = "";
         if (itemV != null && itemV.size() > 0) {
           for (int indexI = 0; indexI < itemV.size(); indexI++) {
             ItemView item = (ItemView) itemV.get(indexI);
             String strId = Integer.toString(item.getItemId());
             if (indexI > 0) {
               strItemIds += ",";
             }
             strItemIds += strId;
           }
           value = strItemIds;
         }
         grc.setValue(value);
       }
       if (name.contains("LOCATE_SITE_MULTI")) {
         SiteViewVector sites = pForm.getSiteFilter();
         String strSiteIds = "";
         value = "";
         if (  sites != null && sites.size() > 0) {
           for (int siteIndex = 0; siteIndex < sites.size(); ++siteIndex) {
             SiteView site = (SiteView) sites.get(siteIndex);
             String strSiteId = Integer.toString(site.getId());
             //don't need to check that selected site associated with user's store
             //checkParamSecurity("SITE", errors, strSiteId, factory, appUser, grc);
             if (siteIndex > 0) {
               strSiteIds += ",";
             }
             strSiteIds += strSiteId;
            }
            value = strSiteIds;
          }
          grc.setValue(strSiteIds);
        }

    //----
 //      if ("DW_STORE_SELECT".equalsIgnoreCase(name)){
 //              DWOperation dwBean = factory.getDWOperationAPI();
 //              int dwStoreDimId = dwBean.getStoreDimId(value);
 //              value =Integer.toString( dwStoreDimId);
 //             // don't store this Values as Generic Report Control value!
 //       }
 //       if ("DW_USER_SELECT".equalsIgnoreCase(name)){
 //        IdVector userIds = getUserSelectedFilter(request);
 //         value ="";
 //         if (userIds != null){
 //           Integer userIdFilter = (Integer)userIds.get(0);
 //           DWOperation dwBean = factory.getDWOperationAPI();
 //           value = dwBean.getUserFilterForAccounts(userIdFilter.intValue()) ;
 //         }
 //         mandatoryFl = false;
 //       }
        if ("DW_USER_SELECT".equalsIgnoreCase(name)){
          mandatoryFl = false;
        }
        if (name.contains("DW_CONNECT_CUST")){
                 value = (Utility.isSet(value) ? value : "No");
                 grc.setValue(value);
        }
        if (name.startsWith("ACCOUNT_MULTI") && appUser.getUser().getUserTypeCd().equals(RefCodeNames.USER_TYPE_CD.REPORTING_USER)){
          String[] runForAccounts = pForm.getRunForAccounts();
          if (null != runForAccounts && runForAccounts.length > 0) {
            String[] values = (Utility.isSet((String)runForAccounts[0])) ? ((String)runForAccounts[0]).split(","):null;
            value = (String)runForAccounts[0];
  //          value = "";
  //          for (int i = 0; i < values.length; i++) {
  //            if (value.length() > 0)
  //              value += ", ";
  //            value += runForAccounts[i];
  //          }
            grc.setValue(value);
            pForm.setRunForAccounts(values);
//             params.put("runForAccounts", value);
//             pstr += "\nrunForAccounts=" + s;
          }
        }
        //--------------------------------------------------------

        if(mandatoryFl && (value==null || value.trim().length()==0)) {
          errors.add(name, new ActionError("variable.empty.error",name));
        }
        String type = grc.getType();
        if(type!=null && value!=null && value.trim().length()>0) {
          if("DATE".equalsIgnoreCase(type) ||
             "BEG_DATE".equalsIgnoreCase(name) || "END_DATE".equalsIgnoreCase(name) ||
             name.startsWith("DW_BEGIN_DATE") || name.startsWith("DW_END_DATE") ) {
            if(!isDate(value)) {
               String ext =  ": Unknown Date Constant (modification action) is used or ";
               label = (Utility.isSet(label)) ? label : name;
               errors.add(name, new ActionError("error.badDateFormat", "Incorrect " + label + ext));
            }
        	value = Utility.customizeDateParam(grc.getName(),value);
            try{
            	if("BEG_DATE".equalsIgnoreCase(name)){
            		beginDate = df.parse(value);
            	}else if("END_DATE".equalsIgnoreCase(name)){
            		endDate = df.parse(value);
            	}
            }catch(Exception e ){
            	String errorMess ="Invalid date";
            	errors.add("Error", new ActionError("error.simpleGenericError", errorMess));
            	return errors;
            }
          } else if("INT".equalsIgnoreCase(type) ||
            "ACCOUNT".equalsIgnoreCase(name) || "ACCOUNT_OPT" .equalsIgnoreCase(name) ||
            "CONTRACT".equalsIgnoreCase(name) || "DISTRIBUTOR".equalsIgnoreCase(name) ||
            "MANUFACTURER".equalsIgnoreCase(name) ||"ITEM".equalsIgnoreCase(name) ||
            "CATALOG".equalsIgnoreCase(name) ||"ITEM_OPT".equalsIgnoreCase(name) ||
            "STORE".equalsIgnoreCase(name) ||"STORE_OPT".equalsIgnoreCase(name)||
            "CUSTOMER".equalsIgnoreCase(name))
          {
            if(!isInt(value)) {
               errors.add(name, new ActionError("variable.integer.format.error", label));
            }
          } else if("NUMBER".equalsIgnoreCase(type)) {
            if(!isNumber(value)) {
               errors.add(name, new ActionError("error.invalidNumberAmount", label));
            }
          }
        }

      }
      if(errors.size()>0) {
         return errors;
      }
      if(endDate != null && beginDate != null){
    	  if(endDate.before(beginDate)){
        	String errorMess =ClwI18nUtil.getMessage(request, "report.text.beginDateGreaterEndDate", null);
        	errors.add("Error", new ActionError("error.simpleGenericError", errorMess));
    	  }
    	  if(errors.size()>0) {
  			return errors;
  		  }
      }

      //Check schedule
      errors = assembleSchedule(request,pForm);
      ReportScheduleData schedule = pForm.getReportScheduleJoin().getSchedule();
      String name = schedule.getShortDesc();
      if(name==null || name.trim().length()==0) {
        errors.add("error",new ActionError("error.simpleGenericError","Empty schedule name"));
      }
      // check user's e-mail
      ArrayList errUsers = verifyUsersEmails(request,pForm);
      if (errUsers != null && errUsers.size() >0){
        errors.add("error",new ActionError("error.simpleGenericError","Can not be configured because email is not provided for the user(s): "+ errUsers.toString().replace("[","").replace("]","")));
      }

      if(errors.size()>0) {
         return errors;
      }

      //Set nofification flags // removed
      /*
      ReportSchedUserShareViewVector uVwV = pForm.getUsers();
      String[]  assocUserA = pForm.getUserToNotify();
      for(int ii=0; ii<uVwV.size(); ii++) {
        ReportSchedUserShareView uVw = (ReportSchedUserShareView) uVwV.get(ii);
        int assocUserId = uVw.getUserId();
        boolean foundFl = false;
        for(int jj=0; ii<assocUserA.length; ii++) {
          String idS = assocUserA[ii];
          int id = Integer.parseInt(idS);
          if(id==assocUserId) {
            foundFl = true;
            uVw.setNotifyFl(true);
            break;
          }
        }
        if(!foundFl) {
          uVw.setNotifyFl(false);
        }
      }


      ReportSchedGroupShareViewVector gVwV = pForm.getGroups();
      String[]  assocGroupA = pForm.getGroupToNotify();
      for(int ii=0; ii<gVwV.size(); ii++) {
        ReportSchedGroupShareView gVw = (ReportSchedGroupShareView) gVwV.get(ii);
        int assocGroupId = gVw.getGroupId();
        boolean foundFl = false;
        for(int jj=0; ii<assocGroupA.length; ii++) {
          String idS = assocGroupA[ii];
          int id = Integer.parseInt(idS);
          if(id==assocGroupId) {
            foundFl = true;
            gVw.setNotifyFl(true);
            break;
          }
        }
        if(!foundFl) {
          gVw.setNotifyFl(false);
        }
      } */


      // Set Report Owner flag
      ReportSchedUserShareViewVector uVwV = pForm.getUsers();
      int  reportOwner = pForm.getReportOwner();
      for(int ii=0; ii<uVwV.size(); ii++) {
        ReportSchedUserShareView uVw = (ReportSchedUserShareView) uVwV.get(ii);
        int uId = uVw.getUserId();
        if(uId == reportOwner) {
            uVw.setReportOwnerFl(true);
        } else {
        	uVw.setReportOwnerFl(false);
        }
      }


      //Save now
      ReportScheduleJoinView rsjVw = pForm.getReportScheduleJoin();
      rsjVw = reportEjb.saveReportSchedule(rsjVw, userName);
      pForm.setReportScheduleJoin(rsjVw);

      return errors;
      } catch (Exception exc) {
        exc.printStackTrace();
        throw exc;
      }
    }

  //--------------------------------------------------------------------------------------
  public static ArrayList  verifyUsersEmails(HttpServletRequest request, ReportScheduleForm pForm) throws Exception {
    boolean ok = true;
    APIAccess factory = APIAccess.getAPIAccess();
    User userEjb = factory.getUserAPI();
    ReportSchedUserShareViewVector users = pForm.getUsers();
    ArrayList errUsers = new ArrayList();
    if(users != null) {
      for (int ii = 0; ii < users.size(); ii++) {
        ReportSchedUserShareView userView = (ReportSchedUserShareView)users.get(ii);
        if (userView != null) {
          UserInfoData uiD = userEjb.getUserContact(userView.getUserId());
          if (uiD != null) {
            String userStatusCd = uiD.getUserData().getUserStatusCd();
            if (RefCodeNames.USER_STATUS_CD.ACTIVE.equals(userStatusCd)) {
              EmailData emailD = uiD.getEmailData();
              if (emailD == null || !Utility.isSet(emailD.getEmailAddress())) {
                ok = false;
                errUsers.add(userView.getUserLoginName());
 //             break;
              }
            }
          }
        }
      }
    }
    return errUsers;
  }

  public static ActionErrors assembleSchedule(HttpServletRequest request, ReportScheduleForm pForm)
  throws Exception
  {
    ActionErrors ae = new ActionErrors();

    ReportScheduleData schedule = pForm.getReportScheduleJoin().getSchedule();
    ReportScheduleDetailDataVector rsdDV = new ReportScheduleDetailDataVector();


    if(pForm.getWeekDays().length>0) {
      schedule.setReportScheduleRuleCd(RefCodeNames.REPORT_SCHEDULE_RULE_CD.WEEK);
      schedule.setCycle(Integer.parseInt(pForm.getWeekCycle()));
      int[] weekDays = pForm.getWeekDays();
      for(int ii=0; ii<weekDays.length; ii++) {
        ReportScheduleDetailData rsdD = ReportScheduleDetailData.createValue();
        rsdD.setReportScheduleDetailCd(RefCodeNames.REPORT_SCHEDULE_DETAIL_CD.WEEK_DAY);
        rsdD.setDetailValue(""+weekDays[ii]);
        rsdDV.add(rsdD);
      }
    }
    else if(pForm.getMonthDays().length>0) {
      schedule.setReportScheduleRuleCd(RefCodeNames.REPORT_SCHEDULE_RULE_CD.DAY_MONTH);
      schedule.setCycle(Integer.parseInt(pForm.getMonthDayCycle()));
      int[] monthDays = pForm.getMonthDays();
      for(int ii=0; ii<monthDays.length; ii++) {
        ReportScheduleDetailData rsdD = ReportScheduleDetailData.createValue();
        rsdD.setReportScheduleDetailCd(RefCodeNames.REPORT_SCHEDULE_DETAIL_CD.MONTH_DAY);
        rsdD.setDetailValue(""+monthDays[ii]);
        rsdDV.add(rsdD);
      }
    }
    else if(pForm.getMonthWeeks().length>0) {
      schedule.setReportScheduleRuleCd(RefCodeNames.REPORT_SCHEDULE_RULE_CD.WEEK_MONTH);
      schedule.setCycle(Integer.parseInt(pForm.getMonthWeekCycle()));
      int[] monthWeeks = pForm.getMonthWeeks();
      for(int ii=0; ii<monthWeeks.length; ii++) {
        ReportScheduleDetailData rsdD = ReportScheduleDetailData.createValue();
        rsdD.setReportScheduleDetailCd(RefCodeNames.REPORT_SCHEDULE_DETAIL_CD.MONTH_WEEK);
        rsdD.setDetailValue(""+monthWeeks[ii]);
        rsdDV.add(rsdD);
      }
      ReportScheduleDetailData rsdD = ReportScheduleDetailData.createValue();
      rsdD.setReportScheduleDetailCd(RefCodeNames.REPORT_SCHEDULE_DETAIL_CD.WEEK_DAY);
      rsdD.setDetailValue(""+pForm.getMonthWeekDay());
      rsdDV.add(rsdD);
    }
    else {
      schedule.setReportScheduleRuleCd(RefCodeNames.REPORT_SCHEDULE_RULE_CD.DATE_LIST);
    }
    //Additional days
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    String alsoDates = pForm.getAlsoDates();
    if(alsoDates!=null && alsoDates.trim().length()>0) {
      int commaInd = 0;
      for(int counter=0; commaInd>=0 && counter<200; counter++) {
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
          ae.add("error",new ActionError("error.simpleGenericError","Wrong Also Date format: "+dateS));
          return ae;
        }
        ReportScheduleDetailData rsdD = ReportScheduleDetailData.createValue();
        rsdD.setReportScheduleDetailCd(RefCodeNames.REPORT_SCHEDULE_DETAIL_CD.ALSO_DATE);
        rsdD.setDetailValue(sdf.format(gc.getTime()));
        rsdDV.add(rsdD);
      }
    }
    //Exclude dates
    String excludeDates = pForm.getExcludeDates();
    List excludeDatesL = new ArrayList();
    if(excludeDates!=null && excludeDates.trim().length()>0) {
      int commaInd = 0;
      for(int counter=0; commaInd>=0 && counter<200; counter++) {
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
          ae.add("error",new ActionError("error.simpleGenericError","Wrong  Except Date format: "+dateS));
          return ae;
        }
        ReportScheduleDetailData rsdD = ReportScheduleDetailData.createValue();
        rsdD.setReportScheduleDetailCd(RefCodeNames.REPORT_SCHEDULE_DETAIL_CD.EXCEPT_DATE);
        rsdD.setDetailValue(sdf.format(gc.getTime()));
        rsdDV.add(rsdD);
      }
    }
    String emailFlag = pForm.getEmailFlag();
    if (Utility.isSet(emailFlag) && emailFlag.equals("Yes")) {
      ReportScheduleDetailData rsdD = ReportScheduleDetailData.createValue();
      rsdD.setReportScheduleDetailCd(RefCodeNames.REPORT_SCHEDULE_DETAIL_CD.EMAIL_FLAG);
      rsdD.setDetailValue(emailFlag);
      rsdDV.add(rsdD);
    }
    pForm.getReportScheduleJoin().setScheduleDetails(rsdDV);
    ae = showDates(request,pForm);
    if(ae.size()>0) {
      return ae;
    }

   return ae;
  }
  //--------------------------------------------------------------------------------------
  public static ActionErrors scatterSchedule(HttpServletRequest request, ReportScheduleForm pForm)
  throws Exception
  {
    ActionErrors ae = new ActionErrors();

    ReportScheduleData schedule = pForm.getReportScheduleJoin().getSchedule();
    ReportScheduleDetailDataVector rsdDV = pForm.getReportScheduleJoin().getScheduleDetails();

    LinkedList weekDayAL = new LinkedList();
    LinkedList monthDayAL = new LinkedList();
    LinkedList monthWeekAL = new LinkedList();
    String alsoDates = "";
    String exceptDates = "";
    String emailFlag ="No";
    for(int ii=0; ii<rsdDV.size(); ii++) {
      ReportScheduleDetailData rsdD = (ReportScheduleDetailData) rsdDV.get(ii);
      String detailCd = rsdD.getReportScheduleDetailCd();
      String value = rsdD.getDetailValue();
      if(RefCodeNames.REPORT_SCHEDULE_DETAIL_CD.WEEK_DAY.equals(detailCd)) {
        weekDayAL.add(value);
      }else if(RefCodeNames.REPORT_SCHEDULE_DETAIL_CD.MONTH_DAY.equals(detailCd)) {
        monthDayAL.add(value);
      }else if(RefCodeNames.REPORT_SCHEDULE_DETAIL_CD.MONTH_WEEK.equals(detailCd)) {
        monthWeekAL.add(value);
      }else if(RefCodeNames.REPORT_SCHEDULE_DETAIL_CD.ALSO_DATE.equals(detailCd)) {
        if(alsoDates.length()>0) alsoDates += ", ";
        alsoDates += value;
      }else if(RefCodeNames.REPORT_SCHEDULE_DETAIL_CD.EXCEPT_DATE.equals(detailCd)) {
        if(exceptDates.length()>0) exceptDates += ", ";
        exceptDates += value;
      }else if(RefCodeNames.REPORT_SCHEDULE_DETAIL_CD.EMAIL_FLAG.equals(detailCd)) {
       emailFlag=value;
      }
    }
    pForm.setEmailFlag(emailFlag);
    pForm.setAlsoDates(alsoDates);
    pForm.setExcludeDates(exceptDates);

    pForm.setWeekDays(new int[0]);
    pForm.setWeekCycle("1");
    pForm.setMonthDayCycle("1");
    pForm.setMonthDays(new int[0]);
    pForm.setMonthWeekCycle("1");
    pForm.setMonthWeekDay(1);
    pForm.setMonthWeeks(new int[0]);

    String ruleCd = schedule.getReportScheduleRuleCd();
    int cycle = schedule.getCycle();

    if(RefCodeNames.REPORT_SCHEDULE_RULE_CD.WEEK.equals(ruleCd)) {
      pForm.setWeekCycle(""+cycle);
      int[] weekDayA = listToIntArray(weekDayAL);
      pForm.setWeekDays(weekDayA);
    } else if(RefCodeNames.REPORT_SCHEDULE_RULE_CD.DAY_MONTH.equals(ruleCd)) {
      pForm.setMonthDayCycle(""+cycle);
      int[] monthDayA = listToIntArray(monthDayAL);
      pForm.setMonthDays(monthDayA);
    } else if(RefCodeNames.REPORT_SCHEDULE_RULE_CD.WEEK_MONTH.equals(ruleCd)) {
      pForm.setMonthWeekCycle(""+cycle);
      int[] weekDayA = listToIntArray(weekDayAL);
      if(weekDayA.length>0)
      pForm.setMonthWeekDay(weekDayA[0]);
      int[] monthWeekA = listToIntArray(monthWeekAL);
      pForm.setMonthWeeks(monthWeekA);
    }
   return ae;
  }
  //--------------------------------------------------------------------------------------
  private static int[] listToIntArray(List pList) {
    int[] intA = new int[pList.size()];
    Iterator iter = pList.iterator();
    for(int  ii=0; iter.hasNext(); ii++) {
      String ss = (String) iter.next();
      try{
        int vv = Integer.parseInt(ss);
        intA[ii] = vv;
      }catch(Exception exc) {
        intA[ii] = 0;
      }
    }
    if(intA.length<=1) {
      return intA;
    }

    for(int ii=0; ii<intA.length-1; ii++) {
      boolean exitFl = true;
      for(int jj=0; jj<intA.length-ii-1; jj++) {
        if(intA[jj]>intA[jj+1]) {
          int vv = intA[jj];
          intA[jj] = intA[jj+1];
          intA[jj+1] = vv;
          exitFl = false;
        }
      }
      if(exitFl) break;
    }
    return intA;
  }

  //--------------------------------------------------------------------------------------
  public static ActionErrors showDates(HttpServletRequest request, ReportScheduleForm pForm)
  throws Exception
  {
    ActionErrors ae = new ActionErrors();
    ReportScheduleData schedule = pForm.getReportScheduleJoin().getSchedule();
    Date curDate = Constants.getCurrentDate();
    GregorianCalendar calendar = new GregorianCalendar();
    calendar.setTime(curDate);
    calendar.add(GregorianCalendar.MONTH,-1);
    calendar.set(GregorianCalendar.DAY_OF_MONTH,1);
    pForm.setCalendar(calendar);
    GregorianCalendar endCalendar = (GregorianCalendar) calendar.clone();
    endCalendar.add(GregorianCalendar.MONTH,6);
    Date addDate = schedule.getAddDate();
    if(addDate==null) {
      addDate = curDate;
    }else {
      SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
      addDate = sdf.parse(sdf.format(addDate));
    }
    GregorianCalendar scheduleStart = new GregorianCalendar();
    scheduleStart.setTime(addDate);

    GregorianCalendar scheduleEnd =  new GregorianCalendar();
    scheduleEnd = (GregorianCalendar) scheduleStart.clone();
    scheduleEnd.add(GregorianCalendar.YEAR,100);

    long calendarMills = calendar.getTime().getTime();
    List datesToShow = new ArrayList();
    String scheduleRule = schedule.getReportScheduleRuleCd();
    //Weekly schedule
    if(RefCodeNames.REPORT_SCHEDULE_RULE_CD.WEEK.equals(scheduleRule)) {
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

        for(int counter=0; !finishFlag && counter<200; counter++) {
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
    if(RefCodeNames.REPORT_SCHEDULE_RULE_CD.DAY_MONTH.equals(scheduleRule)) {
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
        for(int counter=0; !finishFlag && counter<200; counter++) {
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
    if(RefCodeNames.REPORT_SCHEDULE_RULE_CD.WEEK_MONTH.equals(scheduleRule)) {
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
        for(int counter=0; !finishFlag && counter<200; counter++) {
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
      for(int counter=0; commaInd>=0 && counter<200; counter++) {
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
      for(int counter=0; commaInd>=0 && counter<200; counter++) {
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

    //---------------------------------------------------------------------------------------
    public static ActionErrors saveAndRun(HttpServletRequest request,
                                          HttpServletResponse res,
                                          ReportScheduleForm pForm)
    throws Exception
    {
        ActionErrors errors = new ActionErrors();
        try {
          errors = saveSchedule(request,pForm);
          if(errors.size()>0) {
            return errors;
          }
          CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
          String userName = appUser.getUser().getUserName();
          int userId = appUser.getUser().getUserId();
          APIAccess factory = new APIAccess();
          Report reportEjb = factory.getReportAPI();
          ReportScheduleJoinView rsjVw = pForm.getReportScheduleJoin();
          GenericReportData reportD = rsjVw.getReport();
          String rType = reportD.getCategory();
          String rName = reportD.getName();
            //check if they are authorized,  this report should not be displayed to user,
            //so this really shouldn't happen
           if(!(appUser.isAuthorizedForReport(rName))){
                errors.add(rName, new ActionError("error.systemError", "NOT AUTHORIZED"));
                return errors;
           }
           int reportScheduleId = rsjVw.getSchedule().getReportScheduleId();
           ReportResultData reportResultD =
                      reportEjb.processScheduledReport(reportScheduleId,userId, userName);
           int reportResultId = reportResultD.getReportResultId();
           String resultName = rName;
           Date date = reportResultD.getModDate();
           SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
           resultName += sdf.format(date);

           errors = downloadPreparedReport(request, res, reportResultId, resultName);

        }catch (Exception exc) {
          String errorMess = exc.getMessage();
          String repMarker = "^clwKey^";
          if (errorMess != null) {
            int pos = errorMess.indexOf(repMarker);
            if (pos >= 0) {
              String err = ClwI18nUtil.formatEjbError(request, errorMess);
              errors.add("Error", new ActionError("error.simpleGenericError", err));
              return errors;
            }
          }
            exc.printStackTrace();
            throw exc;
        }
        return errors;
    }


    //---------------------------------------------------------------------------------------
    public static ActionErrors downloadPreparedReport(HttpServletRequest request,
                                         HttpServletResponse res,
                                         int pReportResultId,
                                         String pResultName)
    throws Exception
    {
        ActionErrors errors = new ActionErrors();
        try {
          CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
          String userName = appUser.getUser().getUserName();
          int userId = appUser.getUser().getUserId();
          APIAccess factory = new APIAccess();
          Report reportEjb = factory.getReportAPI();

/*
          GenericReportResultViewVector results =
                       reportEjb.readArchiveReport(pReportResultId);
          //Create report result object
          GenericReportResultViewVector results = new GenericReportResultViewVector();
          GenericReportResultView repRes = null;
          ArrayList table = null;
          for(int ii=0; ii<reportLines.size(); ii++) {
            ReportResultLineData rrlD = (ReportResultLineData) reportLines.get(ii);
              String lineS = rrlD.getLineValue();
              String lineS1 = rrlD.getLineValue1();
              if(lineS1!=null) lineS += lineS1;
            if(RefCodeNames.REPORT_RESULT_LINE_CD.HEADER.
                                           equals(rrlD.getReportResultLineCd())){
              GenericReportColumnViewVector header =
                        (GenericReportColumnViewVector) stringToObject(lineS);
              repRes = GenericReportResultView.createValue();
              results.add(repRes);
              table = new ArrayList();
              repRes.setHeader(header);
              repRes.setColumnCount(header.size());
              repRes.setTable(table);

            } else if (RefCodeNames.REPORT_RESULT_LINE_CD.REPORT_LINE.
                                           equals(rrlD.getReportResultLineCd())){
              Object line =  stringToObject(lineS);
              table.add(line);
            }
          }
 */
          //Create report result object
          GenericReportResultViewVector results =
                                     reportEjb.readArchiveReport(pReportResultId);

          boolean hasRawData = false;
          for (int i = 0; results != null && i < results.size(); i++) {
             GenericReportResultView reportResult = (GenericReportResultView) results.get(i);
             if(reportResult.getRawOutput() != null && reportResult.getRawOutput().length > 0){
                 hasRawData = true;
             }
          }
          String fileName = pResultName;
          OutputStream output = res.getOutputStream();
          res.reset();
          if (hasRawData) {
            fileName+=".data";
            res.setContentType("application/octet-stream");
            res.setHeader("extension", "data");
            res.setHeader("Content-disposition", "attachment; filename="+fileName);
            GenericReportResultView result = (GenericReportResultView) results.get(0);
            res.getOutputStream().write(result.getRawOutput());
            res.flushBuffer();
          } else {
           //GetReportName
           //String fileName = pResultName;

        	  fileName+=".xls";
        	  fileName = fileName.replaceAll(" ", "-");
        	  fileName = fileName.replaceAll("/", "-"); //problem with IE6
        	  res.setContentType("application/x-excel");
        	  String browser = (String)  request.getHeader("User-Agent");
        	  boolean isMSIE6 = browser!=null && browser.indexOf("MSIE 6")>=0;
        	  if (!isMSIE6){
        		  res.setHeader("extension", "xls");
        	  }

        	  if(isMSIE6){
        		  res.setHeader("Pragma", "public");
        		  res.setHeader("Expires", "0");
        		  res.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        		  res.setHeader("Cache-Control", "public");

        	  }
        	  res.setHeader("Content-disposition", "attachment; filename="+fileName);
        	  ReportWritter.writeExcelReportMulti(results, output);
        	  res.flushBuffer();
          }
          try {
            reportEjb.markReportAsRead(pReportResultId,userId);
          }catch(Exception exc) { //Dump the exception (doesn't affect download)
            exc.printStackTrace();
          }
        }catch (Exception exc) {
            exc.printStackTrace();
            throw exc;
            //errors.add("system error", new ActionError("error.systemError", exc.getMessage()));
        }
        return errors;
    }

    private static Object stringToObject (String pStr) {
      Object obj = null;
      char[] charArr = pStr.toCharArray();
      byte[] byteArr = new byte[charArr.length];
      for(int ii=0; ii<byteArr.length; ii++) {
        byteArr[ii] = (byte) charArr[ii];
      }
      java.io.ByteArrayInputStream iStream = new java.io.ByteArrayInputStream(byteArr);
      try {
        java.io.ObjectInputStream is = new java.io.ObjectInputStream(iStream);
        obj =  is.readObject();
        is.close();
        iStream.close();
      } catch(Exception exc) {
        exc.printStackTrace();
      }
      return obj;
    }


   public static boolean isDate(String pDate) {
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        pDate = pDate.trim().toLowerCase().replaceAll(" ", "");
        if (pDate.equals(Constants.REP_DATE_CONST.LAST_MONTH_YEAR_BEGIN) ||
            pDate.equals(Constants.REP_DATE_CONST.LAST_MONTH_END) ||
            pDate.equals(Constants.REP_DATE_CONST.THIS_YEAR_BEGIN) ||
            pDate.equals(Constants.REP_DATE_CONST.TODAY) ||
            pDate.equals(Constants.REP_DATE_CONST.LAST_WEEKDAY)){
         return true;
        }
        if (pDate.startsWith(Constants.REP_DATE_CONST.TODAY)||
            pDate.startsWith(Constants.REP_DATE_CONST.THIS_YEAR_BEGIN)||
            pDate.startsWith(Constants.REP_DATE_CONST.LAST_MONTH_END)||
            pDate.startsWith(Constants.REP_DATE_CONST.LAST_MONTH_YEAR_BEGIN)||
            pDate.startsWith(Constants.REP_DATE_CONST.LAST_WEEKDAY)) {
          try {
            GregorianCalendar gc = new GregorianCalendar();
            Date theDate = new Date();
            String repConst = Constants.REP_DATE_CONST.TODAY;
            if (Utility.isValidDateConstant(pDate,Constants.REP_DATE_CONST.LAST_WEEKDAY)){
              theDate = Utility.evaluateLastWeekday();
              repConst = Constants.REP_DATE_CONST.LAST_WEEKDAY;
            } else if (Utility.isValidDateConstant(pDate,Constants.REP_DATE_CONST.LAST_MONTH_YEAR_BEGIN)) {
              gc.add(GregorianCalendar.MONTH, -1);
              theDate = df.parse("01/01/" + gc.get(GregorianCalendar.YEAR));
              repConst = Constants.REP_DATE_CONST.LAST_MONTH_YEAR_BEGIN;
            } else if (Utility.isValidDateConstant(pDate,Constants.REP_DATE_CONST.THIS_YEAR_BEGIN)) {
              theDate = df.parse("01/01/" + gc.get(GregorianCalendar.YEAR));
              repConst = Constants.REP_DATE_CONST.THIS_YEAR_BEGIN;
            } else if (Utility.isValidDateConstant(pDate,Constants.REP_DATE_CONST.LAST_MONTH_END)) {
              gc.set(GregorianCalendar.DAY_OF_MONTH, 1);
              gc.add(GregorianCalendar.DAY_OF_MONTH, -1);
              theDate = df.parse((gc.get(GregorianCalendar.MONTH) + 1) + "/" +
                                   gc.get(GregorianCalendar.DAY_OF_MONTH) + "/" +
                                   gc.get(GregorianCalendar.YEAR));
              repConst = Constants.REP_DATE_CONST.LAST_MONTH_END;
            }

            Utility.doDateMods(pDate.substring(repConst.length()),theDate);
            return true;
          }
          catch (Exception e) {
              return false;
          }
        }
        try {
            df.parse(pDate);
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }


    public static boolean isInt(String pInt) {
        try {
            Integer.parseInt(pInt);
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }


    public static boolean isNumber(String pDouble) {
        try {
            Double.parseDouble(pDouble);
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }
}




