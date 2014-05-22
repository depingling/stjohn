package com.cleanwise.view.logic;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.AutoOrder;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.*;
import com.cleanwise.view.forms.UserWorkOrderDetailMgrForm;
import com.cleanwise.view.forms.UserWorkOrderSchedulerForm;
import com.cleanwise.view.forms.UserWorkOrderContentMgrForm;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Title:        UserWorkOrderSchedulerLogic
 * Description:  logic manager for creation of the schedule of orders
 * Purpose:      executes operation for creation of the schedule of orders
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * Date:         25.05.2008
 * Time:         17:38:05
 *
 * @author Alexander Chickin,TrinitySoft, Inc.
 */
public class UserWorkOrderSchedulerLogic {

    public static final String className = "UserWorkOrderSchedulerLogic";
    private static final String USER_WORK_ORDER_SCHEDULER_FORM = "USER_WORK_ORDER_SCHEDULER_FORM";

    public static ActionErrors init(HttpServletRequest request,
                                    UserWorkOrderSchedulerForm mgrForm) throws Exception {

        HttpSession session = request.getSession();
        ActionErrors ae;

        mgrForm = new UserWorkOrderSchedulerForm();
        mgrForm.init();         

        UserWorkOrderDetailMgrForm detForm = (UserWorkOrderDetailMgrForm) session.getAttribute(UserWorkOrderMgrLogic.USER_WORK_ORDER_DETAIL_MGR_FORM);
        if (detForm != null) {
            mgrForm.setWorkOrder(detForm.getWorkOrderDetail().getWorkOrder());
        }

        ae = checkRequest(request, mgrForm);
        if (ae.size() > 0) {
            return ae;
        }

        initFields(request, mgrForm);

        session.setAttribute(USER_WORK_ORDER_SCHEDULER_FORM, mgrForm);

        return ae;

    }

    private static void initFields(HttpServletRequest request, UserWorkOrderSchedulerForm mgrForm) {
        mgrForm.setScheduleType(RefCodeNames.ORDER_SCHEDULE_RULE_CD.WEEK);
        mgrForm.setOrderScheduleId(0);
        mgrForm.setStartDate(ClwI18nUtil.formatDateInp(request, Constants.getCurrentDate()));
        mgrForm.setEndDate("");
        mgrForm.setScheduleAction(RefCodeNames.ORDER_SCHEDULE_CD.NOTIFY);
        mgrForm.setCcEmail("");
        mgrForm.setWeekCycle("1");
        mgrForm.setWeekDays(new int[0]);
        mgrForm.setMonthDayCycle("1");
        mgrForm.setMonthDays(new int[0]);
        mgrForm.setMonthWeekCycle("1");
        mgrForm.setMonthWeekDay(2);
        mgrForm.setMonthWeeks(1);
        mgrForm.setMonthMonths(new int[0]);
        mgrForm.setAlsoDates("");
        mgrForm.setExcludeDates("");
        mgrForm.setContactName("");
        mgrForm.setContactPhone("");
        mgrForm.setContactEmail("");
        mgrForm.setCalendarExcludedDates(new ArrayList());
        mgrForm.setCalendarDatesWithOrders(new ArrayList());
        mgrForm.setOrderSchedules(new OrderScheduleDataVector());
        //Calendar start date
        Date curDate = Constants.getCurrentDate();
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(curDate);
        calendar.set(GregorianCalendar.DAY_OF_YEAR, 1);
        calendar.set(GregorianCalendar.DAY_OF_MONTH, 1);
        mgrForm.setCalendar(calendar);
    }

    public static ActionErrors saveSchedule(HttpServletRequest request, UserWorkOrderSchedulerForm mgrForm) throws Exception {

        HttpSession session = request.getSession();
        ActionErrors ae = new ActionErrors();

        ae.add(checkRequest(request, mgrForm));
        if (ae.size() > 0) {
            return ae;
        }

        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        ae = showDates(request, mgrForm);
        if (ae.size() > 0) {
            return ae;
        }

        UserData user = appUser.getUser();
        SiteData site = appUser.getSite();
        int siteId = site.getBusEntity().getBusEntityId();

        AutoOrder autoOrderEjb = factory.getAutoOrderAPI();

        OrderScheduleJoin orderScheduleJ = OrderScheduleJoin.createValue();
        orderScheduleJ.setOrderScheduleId(mgrForm.getOrderScheduleId());
        orderScheduleJ.setWorkOrderId(mgrForm.getWorkOrder().getWorkOrderId());
        orderScheduleJ.setUserId(appUser.getUser().getUserId());
        orderScheduleJ.setSiteId(siteId);
        orderScheduleJ.setSiteName(site.getBusEntity().getShortDesc());

        GregorianCalendar gc = null;
        GregorianCalendar gcStartDate = null;
        String startDateS = mgrForm.getStartDate();
        try {
            Date sd = ClwI18nUtil.parseDateInp(request, startDateS);
            gc = new GregorianCalendar();
            gc.setTime(sd);
            gcStartDate = new GregorianCalendar();
            gcStartDate.setTime(sd);
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        if (gc == null) {
            Object[] params = new Object[1];
            params[0] = startDateS;
            String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.wrongEndDateFormat", params);
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }

        orderScheduleJ.setEffDate(gc.getTime());
        GregorianCalendar gcEndDate = null;
        String endDateS = mgrForm.getEndDate();
        if (endDateS != null && endDateS.trim().length() > 0) {
            gc = createCalendar(request, endDateS);
            gcEndDate = new GregorianCalendar();
            Date ed = null;
            try {
                ed = ClwI18nUtil.parseDateInp(request, endDateS);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            gcEndDate.setTime(ed);

            if (gc == null) {
                Object[] params = new Object[1];
                params[0] = endDateS;
                String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.wrongEndDateFormat", params);
                ae.add("error", new ActionError("error.simpleGenericError", errorMess));
                return ae;
            }

            if (gc.before(gcStartDate)) {
                Object[] params = new Object[2];
                params[0] = "" + endDateS;
                params[1] = "" + startDateS;
                String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.endDateBeforeStartDate", params);
                ae.add("error", new ActionError("error.simpleGenericError", errorMess));
                return ae;
            }

            orderScheduleJ.setExpDate(gc.getTime());
        }

        //orderScheduleJ.setWorkOrderId();
        orderScheduleJ.setCcEmail(mgrForm.getCcEmail());
        orderScheduleJ.setOrderScheduleCd(mgrForm.getScheduleAction());

        if (mgrForm.getWeekDays().length > 0) {
            orderScheduleJ.setOrderScheduleRuleCd(RefCodeNames.ORDER_SCHEDULE_RULE_CD.WEEK);
            orderScheduleJ.setCycle(Integer.parseInt(mgrForm.getWeekCycle()));
            orderScheduleJ.setElements(mgrForm.getWeekDays());
        } else if (mgrForm.getMonthDays().length > 0) {
            orderScheduleJ.setOrderScheduleRuleCd(RefCodeNames.ORDER_SCHEDULE_RULE_CD.DAY_MONTH);
            orderScheduleJ.setCycle(Integer.parseInt(mgrForm.getMonthDayCycle()));
            orderScheduleJ.setElements(mgrForm.getMonthDays());
        } else if (mgrForm.getMonthMonths().length > 0) {
            orderScheduleJ.setOrderScheduleRuleCd(RefCodeNames.ORDER_SCHEDULE_RULE_CD.WEEK_MONTH);
            orderScheduleJ.setElements(mgrForm.getMonthMonths());
            orderScheduleJ.setMonthWeeks(mgrForm.getMonthWeeks());
            orderScheduleJ.setMonthWeekDay(mgrForm.getMonthWeekDay());
        } else {
            orderScheduleJ.setOrderScheduleRuleCd(RefCodeNames.ORDER_SCHEDULE_RULE_CD.DATE_LIST);
        }

        //Additional days
        String alsoDates = mgrForm.getAlsoDates();
        List alsoDateL = new ArrayList();
        if (alsoDates != null && alsoDates.trim().length() > 0) {
            int commaInd = 0;
            while (commaInd >= 0) {
                int nextCommaInd = alsoDates.indexOf(',', commaInd);
                String dateS = null;
                if (nextCommaInd > 0) {
                    dateS = alsoDates.substring(commaInd, nextCommaInd).trim();
                    commaInd = nextCommaInd + 1;
                } else {
                    dateS = alsoDates.substring(commaInd).trim();
                    commaInd = -1;
                }

                gc = createCalendar(request, dateS);

                if (gc == null) {
                    Object[] params = new Object[1];
                    params[0] = dateS;
                    String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.wrongAlsoDateFormat", params);
                    ae.add("error", new ActionError("error.simpleGenericError", errorMess));
                    return ae;
                }

                if (gc.before(gcStartDate) || gc.after(gcEndDate)) {
                    Object[] params = new Object[2];
                    params[0] = "" + startDateS;
                    params[1] = "" + endDateS;
                    String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.additionalOrderDateOutOfRange", params);
                    ae.add("error", new ActionError("error.simpleGenericError", errorMess));

                    return ae;
                }

                alsoDateL.add(gc);
            }
        }

        Date[] alsoDatesA = new Date[alsoDateL.size()];
        for (int ii = 0; ii < alsoDateL.size(); ii++) {
            gc = (GregorianCalendar) alsoDateL.get(ii);
            alsoDatesA[ii] = gc.getTime();
        }

        orderScheduleJ.setAlsoDates(alsoDatesA);

        //Exclude dates
        String excludeDates = mgrForm.getExcludeDates();
        List excludeDatesL = new ArrayList();
        if (excludeDates != null && excludeDates.trim().length() > 0) {
            int commaInd = 0;
            while (commaInd >= 0) {
                int nextCommaInd = excludeDates.indexOf(',', commaInd);
                String dateS = null;
                if (nextCommaInd > 0) {
                    dateS = excludeDates.substring(commaInd, nextCommaInd).trim();
                    commaInd = nextCommaInd + 1;
                } else {
                    dateS = excludeDates.substring(commaInd).trim();
                    commaInd = -1;
                }

                gc = createCalendar(request, dateS);

                if (gc == null) {
                    Object[] params = new Object[1];
                    params[0] = dateS;
                    String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.wrongNotPlaceDateFormat", params);
                    ae.add("error", new ActionError("error.simpleGenericError", errorMess));
                    return ae;
                }

                if (gc.before(gcStartDate) || gc.after(gcEndDate)) {
                    Object[] params = new Object[2];
                    params[0] = "" + startDateS;
                    params[1] = "" + endDateS;
                    String errorMess =
                            ClwI18nUtil.getMessage(request, "shop.errors.deleteOrderDateOutOfRange", params);
                    ae.add("error", new ActionError("error.simpleGenericError", errorMess));
                    return ae;
                }
                excludeDatesL.add(gc.getTime());
            }
        }

        Date[] excludeDatesA = new Date[excludeDatesL.size()];
        for (int ii = 0; ii < excludeDatesA.length; ii++) {
            Date excludeDate = (Date) excludeDatesL.get(ii);
            excludeDatesA[ii] = excludeDate;
        }
        orderScheduleJ.setExceptDates(excludeDatesA);

        //for crc's only
        if (user.getUserTypeCd().equals(RefCodeNames.USER_TYPE_CD.CUSTOMER_SERVICE)
                || user.getUserTypeCd().equals(RefCodeNames.USER_TYPE_CD.CRC_MANAGER)) {
            if (mgrForm.getContactName() == null
                    || mgrForm.getContactName().trim().length() == 0) {
                String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.fieldContactNameRequiresInformation", null);
                ae.add("error", new ActionError("error.simpleGenericError", errorMess));
                return ae;
            } else {
                orderScheduleJ.setContactName(mgrForm.getContactName());
            }

            if (mgrForm.getContactPhone() == null || mgrForm.getContactPhone().trim().length() == 0) {
                String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.fieldContactPhoneRequiresInformation", null);
                ae.add("error", new ActionError("error.simpleGenericError", errorMess));
                return ae;
            } else {
                orderScheduleJ.setContactPhone(mgrForm.getContactPhone());
            }
            orderScheduleJ.setContactEmail(mgrForm.getContactEmail());

        }

        int oldId = orderScheduleJ.getOrderScheduleId();
        int orderGuideId = orderScheduleJ.getOrderGuideId();
        //check crossings
        Date effDate = orderScheduleJ.getEffDate();
        long effMills = effDate.getTime();
        long expMills = 0;
        Date expDate = orderScheduleJ.getExpDate();
        if (expDate != null) {
            expMills = expDate.getTime();
        }
        List schedules = mgrForm.getOrderSchedules();
        for (int ii = 0; ii < schedules.size(); ii++) {
            OrderScheduleData osv = (OrderScheduleData) schedules.get(ii);
            if (oldId != osv.getOrderScheduleId()
                    && orderGuideId == osv.getOrderGuideId()) {
                Date effD = osv.getEffDate();
                long effM = effD.getTime();
                long expM = 0;
                Date expD = osv.getExpDate();
                if (expD != null) {
                    expM = expD.getTime();
                }
                if ((expM == 0 || expM >= effMills)
                        && (effM <= expMills || expMills == 0)) {
                    Object[] params = new Object[1];
                    params[0] = mgrForm.getOrderScheduleName(ii);
                    String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.scheduleItersectsWithExistingSchedule", params);
                    ae.add("error", new ActionError("error.simpleGenericError", errorMess));
                    return ae;
                }
            }
        }

        try {
            int id = autoOrderEjb.saveOrderSchedule(orderScheduleJ, user.getUserName());
            orderScheduleJ.setOrderScheduleId(id);
            mgrForm.setOrderScheduleId(id);
        } catch (RemoteException exc) {
            String errorMess = ClwI18nUtil.formatEjbError(request, exc.getMessage());
            if (errorMess != null && errorMess.trim().length() > 0) {
                ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            } else {
                ae.add("error", new ActionError("error.systemError", exc.getMessage()));
            }
            return ae;
        }

        //Update in list
        OrderScheduleData orderSchedule = OrderScheduleData.createValue();
        orderSchedule.setOrderScheduleId(orderScheduleJ.getOrderScheduleId());
        orderSchedule.setOrderGuideId(orderScheduleJ.getOrderGuideId());
        orderSchedule.setOrderScheduleRuleCd(orderScheduleJ.getOrderScheduleRuleCd());
        orderSchedule.setOrderScheduleCd(orderScheduleJ.getOrderScheduleCd());
        orderSchedule.setExpDate(orderScheduleJ.getExpDate());
        orderSchedule.setEffDate(orderScheduleJ.getEffDate());
        orderSchedule.setBusEntityId(orderScheduleJ.getSiteId());
        orderSchedule.setUserId(orderScheduleJ.getUserId());
        orderSchedule.setWorkOrderId(orderScheduleJ.getWorkOrderId());
        OrderScheduleDataVector orderSchedules = mgrForm.getOrderSchedules();
        int ii = 0;
        for (; ii < orderSchedules.size(); ii++) {
            OrderScheduleData osv = (OrderScheduleData) orderSchedules.get(ii);
            if (osv.getOrderScheduleId() == oldId) {
                orderSchedules.remove(ii);
                orderSchedules.add(ii, orderSchedule);
                break;
            }
        }
        if (ii == orderSchedules.size()) {
            orderSchedules.add(orderSchedule);
        }

        if(schedules.size()>1){
            ae.add("error", new ActionError("error.systemError", "Multiple schedules for workOrderId: "+mgrForm.getWorkOrder().getWorkOrderId()));
            return ae;
        }

        mgrForm.setOrderSchedules(orderSchedules);
        mgrForm.resetScheduleYear();

        return ae;
    }


    private static ActionErrors showDates(HttpServletRequest request, UserWorkOrderSchedulerForm mgrForm) {

        ActionErrors ae = new ActionErrors();

        GregorianCalendar origCalendar = (GregorianCalendar) mgrForm.getCalendar().clone();
        GregorianCalendar calendar = new GregorianCalendar(origCalendar.get(GregorianCalendar.YEAR), 0, 1);
        GregorianCalendar endCalendar = (GregorianCalendar) calendar.clone();
        endCalendar.add(GregorianCalendar.MONTH, 12);

        GregorianCalendar scheduleStart = createCalendar(request, mgrForm.getStartDate());
        if (scheduleStart == null) {
            String property = ClwI18nUtil.getMessageOrNull(request, "shop.orderSchedule.text.startDate");
            if (property == null) {
                property = "Start Date";
            }
            String mess = ClwI18nUtil.getMessage(request, "error.wrongDateFormat", new Object[]{property, mgrForm.getStartDate()}, true);
            ae.add("startDate", new ActionError("error.simpleError", mess));
        }
        
        GregorianCalendar scheduleEnd;
        String endDateS = mgrForm.getEndDate();
        if (endDateS == null || endDateS.trim().length() == 0) {
            scheduleEnd = (GregorianCalendar) scheduleStart.clone();
            scheduleEnd.add(GregorianCalendar.YEAR, 100);
        } else {
            scheduleEnd = createCalendar(request, endDateS);
            if (scheduleEnd == null) {
                String property = ClwI18nUtil.getMessageOrNull(request, "shop.orderSchedule.text.endDate");
                if (property == null) {
                    property = "End Date";
                }
                String mess = ClwI18nUtil.getMessage(request, "error.wrongDateFormat", new Object[]{property, endDateS}, true);
                ae.add("endDate", new ActionError("error.simpleError", mess));
            }
        }

        long calendarMills = calendar.getTime().getTime();

        List datesToShow = new ArrayList();
        String scheduleType = mgrForm.getScheduleType();

        if (RefCodeNames.ORDER_SCHEDULE_RULE_CD.WEEK.equals(scheduleType)) {  //Weekly schedule
            datesToShow = calculateWeeklyOrders(calendar, mgrForm.getWeekDays());
        } else if (RefCodeNames.ORDER_SCHEDULE_RULE_CD.DAY_MONTH.equals(scheduleType)) {  //Month day schedule
            datesToShow = calculateMonthlyOrders(calendar, mgrForm.getMonthDays());
        } else
        if (RefCodeNames.ORDER_SCHEDULE_RULE_CD.WEEK_MONTH.equals(scheduleType)) {  //Month week (custom) schedule
            int monthWeeks = mgrForm.getMonthWeeks();
            int[] monthMonths = mgrForm.getMonthMonths();
            datesToShow = calculateCustomOrders(calendar, mgrForm.getMonthWeekDay(), monthWeeks, monthMonths);
        }

        //Additional days
        String alsoDates = mgrForm.getAlsoDates();
        if (alsoDates != null && alsoDates.trim().length() > 0) {
            int commaInd = 0;
            while (commaInd >= 0) {
                int nextCommaInd = alsoDates.indexOf(',', commaInd);
                String dateS = null;
                if (nextCommaInd > 0) {
                    dateS = alsoDates.substring(commaInd, nextCommaInd).trim();
                    commaInd = nextCommaInd + 1;
                } else {
                    dateS = alsoDates.substring(commaInd).trim();
                    commaInd = -1;
                }
                GregorianCalendar gc = createCalendar(request, dateS);
                if (gc == null) { 
                    String property = ClwI18nUtil.getMessageOrNull(request, "shop.orderSchedule.text.placeAdditionalOrdersOn");
                    if (property == null) {
                        property = "Place additional order(s) on";
                    }
                    String mess = ClwI18nUtil.getMessage(request, "error.wrongDateFormat", new Object[]{property, dateS}, true);
                    ae.add("alsoDates", new ActionError("error.simpleError", mess));
                }
                if (!(scheduleStart.after(gc) || scheduleEnd.before(gc))) {
                    datesToShow.add(gc);
                }
            }
        }

        //Exclude dates
        String excludeDates = mgrForm.getExcludeDates();
        List excludeDatesL = new ArrayList();
        List datesToNotShow = new ArrayList();
        if (excludeDates != null && excludeDates.trim().length() > 0) {
            int commaInd = 0;
            while (commaInd >= 0) {
                int nextCommaInd = excludeDates.indexOf(',', commaInd);
                String dateS;
                if (nextCommaInd > 0) {
                    dateS = excludeDates.substring(commaInd, nextCommaInd).trim();
                    commaInd = nextCommaInd + 1;
                } else {
                    dateS = excludeDates.substring(commaInd).trim();
                    commaInd = -1;
                }
                GregorianCalendar gc = createCalendar(request, dateS);
                if (gc == null) {
                    String property = ClwI18nUtil.getMessageOrNull(request, "shop.orderSchedule.text.deleteAdditionalOrdersOn");
                    if (property == null) {
                        property = "Delete order(s) on";
                    }
                    String mess = ClwI18nUtil.getMessage(request, "error.wrongDateFormat", new Object[]{property, dateS}, true);
                    ae.add("excludeDates", new ActionError("error.simpleError", mess));
                }
                for (int ii = 0; ii < datesToShow.size(); ii++) {
                    GregorianCalendar dd = (GregorianCalendar) datesToShow.get(ii);
                    if (gc.equals(dd)) {
                        datesToNotShow.add(gc);
                        excludeDatesL.add(dd.getTime());
                        datesToShow.set(ii, null);
                        break;
                    }
                }

            }
        }

        //Remove duplicates and nulls
        // and any dates outside the active date range for this schedule.
        ArrayList finalDatesToShow = new ArrayList();
        for (int ii = 0; ii < datesToShow.size(); ii++) {
            GregorianCalendar dd = (GregorianCalendar) datesToShow.get(ii);
            if (dd == null) {
                continue;
            }
            if (!scheduleStart.after(dd) && scheduleEnd.after(dd)) {
                // this date is not before the start and
                // not after the end of the schedule time span.
                finalDatesToShow.add(dd);
            }
        }

        mgrForm.setCalendarDatesWithOrders(finalDatesToShow);
        mgrForm.setNextOrderIndex(0);
        if (datesToShow.size() > 0) {
            mgrForm.setNextOrderRelMonth(0);
            mgrForm.setNextOrderMonthDay(0);
        } else {
            mgrForm.setNextOrderRelMonth(-1);
            mgrForm.setNextOrderMonthDay(0);
        }

        mgrForm.setCalendarExcludedDates(datesToNotShow);
        mgrForm.setNextExcludedIndex(0);
        if (datesToNotShow.size() > 0) {
            GregorianCalendar gc = (GregorianCalendar) datesToNotShow.get(0);
            mgrForm.setNextExcludedRelMonth(gc.get(GregorianCalendar.YEAR)
                    * 12
                    + gc.get(GregorianCalendar.MONTH)
                    - (calendar.get(GregorianCalendar.YEAR) * 12 + calendar.get(GregorianCalendar.MONTH)));
            mgrForm.setNextExcludedMonthDay(gc.get(GregorianCalendar.DAY_OF_MONTH));
        } else {
            mgrForm.setNextExcludedRelMonth(-1);
            mgrForm.setNextExcludedMonthDay(0);
        }

        return ae;
    }

    private static GregorianCalendar createCalendar(HttpServletRequest request, String pDateStr) {
        GregorianCalendar gc = null;
        try {
            Date date = ClwI18nUtil.parseDateInp(request, pDateStr);
            gc = new GregorianCalendar();
            gc.set(GregorianCalendar.DAY_OF_YEAR, 1);
            gc.set(GregorianCalendar.DAY_OF_MONTH, 1);
            gc.setTime(date);
        } catch (Exception exc) {
            error(exc.getMessage(), exc);
        }
        return gc;
    }

    private static List calculateWeeklyOrders(GregorianCalendar scheduleStart, int[] weekDays) {
        ArrayList datesToShow = new ArrayList();
        GregorianCalendar thiscal = (GregorianCalendar) scheduleStart.clone();
        // increment the calendar for 1 year.
        for (int day = 0; day <= 366; day++) {
            for (int i = 0; i < weekDays.length; i++) {

                int thisWeekDay = weekDays[i];

                if (thisWeekDay == (thiscal.get(GregorianCalendar.DAY_OF_WEEK))) {
                    datesToShow.add(thiscal.clone());
                }
            }
            thiscal.add(GregorianCalendar.DAY_OF_YEAR, 1);
        }
        return datesToShow;
    }

    private static List calculateMonthlyOrders(GregorianCalendar scheduleStart, int[] monthDays) {

        ArrayList datesToShow = new ArrayList();
        GregorianCalendar thiscal = (GregorianCalendar) scheduleStart.clone();
        // increment the calendar for 1 year.
        for (int day = 0; day <= 366; day++) {
            for (int i = 0; i < monthDays.length; i++) {

                int thisMonthDay = monthDays[i];

                if (thisMonthDay == 32 &&
                        thiscal.get(GregorianCalendar.DAY_OF_MONTH) ==
                                thiscal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH)) {
                    // add the last day of the month.
                    datesToShow.add(thiscal.clone());
                } else if (thisMonthDay == thiscal.get(GregorianCalendar.DAY_OF_MONTH)) {
                    datesToShow.add(thiscal.clone());
                }
            }
            thiscal.add(GregorianCalendar.DAY_OF_YEAR, 1);
        }

        return datesToShow;
    }

    private static List calculateCustomOrders(GregorianCalendar scheduleStart,
                                              int monthWeekDay,
                                              int monthWeek,
                                              int[] monthMonths) {

        ArrayList datesToShow = new ArrayList();

        for (int i = 0; i < monthMonths.length; i++) {
            int thisMonth = monthMonths[i];

            GregorianCalendar thiscal = new GregorianCalendar(scheduleStart.get(GregorianCalendar.YEAR), thisMonth, 1);

            GregorianCalendar lastdayMatch = null;
            boolean matchfound = false;

            int dayidx = 0;
            while (dayidx++ < 32) {

                int thisweekofm = thiscal.get(GregorianCalendar.WEEK_OF_MONTH);
                int thisdayofweek = thiscal.get(GregorianCalendar.DAY_OF_WEEK);

                if (thisweekofm == monthWeek && thisdayofweek == monthWeekDay) {

                    datesToShow.add(thiscal.clone());
                    // done with this one
                    matchfound = true;
                    break;
                }

                if (thisweekofm > monthWeek && thisdayofweek == monthWeekDay) {
                    datesToShow.add(thiscal.clone());
                    // done with this one
                    matchfound = true;
                    break;
                }
                if (thisdayofweek == monthWeekDay) {
                    lastdayMatch = (GregorianCalendar) thiscal.clone();
                }
                thiscal.add(GregorianCalendar.DAY_OF_MONTH, 1);

            }
            if (!matchfound && null != lastdayMatch) {
                datesToShow.add(lastdayMatch.clone());
            }
        }

        return datesToShow;

    }


    private static ActionErrors checkRequest(HttpServletRequest request, UserWorkOrderSchedulerForm mgrForm) throws Exception {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        APIAccess factory = (APIAccess) request.getSession().getAttribute(Constants.APIACCESS);
        UserWorkOrderDetailMgrForm detForm = (UserWorkOrderDetailMgrForm) session.getAttribute(UserWorkOrderMgrLogic.USER_WORK_ORDER_DETAIL_MGR_FORM);

        if (mgrForm == null) {
            throw new Exception("Form not initialized");
        }

        if (detForm == null) {
            throw new Exception("Detail form not initialized");
        }

        if (detForm.getWorkOrderDetail() == null) {
            throw new Exception("Parent form not contains detail data");
        }

        if (detForm.getWorkOrderDetail().getWorkOrder() == null) {
            throw new Exception("Parent form not contains work order data");
        }

        if (mgrForm.getWorkOrder() == null) {
            throw new Exception("Mgr.Form not contains work order data");
        }

        if (factory == null) {
            ae.add("error", new ActionError("error.systemError", "No Ejb access"));
            return ae;
        }

        if (appUser == null) {
            ae.add("error", new ActionError("error.systemError", "No user info"));
            return ae;
        }

        if (!detForm.getWorkOrderDetail().getWorkOrder().equals(mgrForm.getWorkOrder())) {
            ae.add("error", new ActionError("error.systemError", "shop.errors.pageExpired"));
            return ae;
        }

        if (!appUser.getUserStore().isAllowAssetManagement()) {
            ae.add("error", new ActionError("error.simpleGenericError", "Unauthorized access"));
            throw new Exception("Unauthorized access");
        }

        return ae;
    }

    private static int getIdFromRequest(HttpServletRequest request, String idName) {
        String IdStr = request.getParameter(idName);
        int IdInt = 0;
        if (Utility.isSet(IdStr)) {
            try {
                IdInt = Integer.parseInt(IdStr);
            } catch (NumberFormatException e) {
            }
        }
        return IdInt;
    }

    private static int getScheduleId(HttpServletRequest request, UserWorkOrderSchedulerForm mgrForm) {

        int id = getIdFromRequest(request, "scheduleId");

        if (id <= 0 && mgrForm != null && mgrForm.getOrderScheduleId() > 0) {
            return mgrForm.getOrderScheduleId();
        } else {
            return id;
        }
    }

    //******************************************************************************
    public static ActionErrors getDetail(HttpServletRequest request, UserWorkOrderSchedulerForm mgrForm) throws Exception {

        HttpSession session = request.getSession();
        ActionErrors ae;

        int scheduleId = getScheduleId(request, mgrForm);
        ae = init(request, mgrForm);
        if (ae.size() > 0) {
            return ae;
        }

        mgrForm = (UserWorkOrderSchedulerForm) session.getAttribute(USER_WORK_ORDER_SCHEDULER_FORM);

        if (scheduleId != 0) {

            APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
            CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

            AutoOrder autoOrderEjb;
            try {
                autoOrderEjb = factory.getAutoOrderAPI();
            } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
                exc.printStackTrace();
                ae.add("error", new ActionError("error.systemError", "No auto order Ejb pointer"));
                return ae;
            }

            OrderScheduleDataVector schedules;
            try {
                int siteId = appUser.getSite().getBusEntity().getBusEntityId();
                IdVector workOrderIds = new IdVector();
                workOrderIds.add(new Integer(mgrForm.getWorkOrder().getWorkOrderId()));
                schedules = autoOrderEjb.getWorkOrderSchedules(siteId, workOrderIds);
                if(schedules.size()>1){
                    ae.add("error", new ActionError("error.systemError", "Multiple schedules for workOrderId: "+workOrderIds));
                    return ae;
                }
                mgrForm.setOrderSchedules(schedules);
            } catch (RemoteException exc) {
                exc.printStackTrace();
                ae.add("error", new ActionError("error.systemError", exc.getMessage()));
                return ae;
            }

            int ii = 0;
            OrderScheduleData osv = null;
            for (; ii < schedules.size(); ii++) {
                osv = (OrderScheduleData) schedules.get(ii);
                if (osv.getOrderScheduleId() == scheduleId) {
                    break;
                }
            }
            if(osv==null){
              ae.add("error", new ActionError("error.systemError", "Schedule not found"));
              return ae;
            }

            mgrForm.setStartDate(ClwI18nUtil.formatDateInp(request, osv.getEffDate()));
            Date endDate = osv.getExpDate();
            if (endDate == null) {
                mgrForm.setEndDate("");
            } else {
                mgrForm.setEndDate(ClwI18nUtil.formatDateInp(request, osv.getExpDate()));
            }
            mgrForm.setScheduleAction(osv.getOrderScheduleCd());


            OrderScheduleJoin orderScheduleJ;
            try {
                orderScheduleJ = autoOrderEjb.getWorkOrderSchedule(osv.getOrderScheduleId());
            } catch (Exception exc) {
                exc.printStackTrace();
                ae.add("error", new ActionError("error.systemError", exc.getMessage()));
                return ae;
            }

            mgrForm.setCcEmail(orderScheduleJ.getCcEmail());
            mgrForm.setScheduleType(orderScheduleJ.getOrderScheduleRuleCd());
            if (RefCodeNames.ORDER_SCHEDULE_RULE_CD.WEEK.equals(orderScheduleJ.getOrderScheduleRuleCd())) {
                mgrForm.setWeekCycle("" + orderScheduleJ.getCycle());
                mgrForm.setWeekDays(orderScheduleJ.getElements());
            } else {
                mgrForm.setWeekCycle("1");
                mgrForm.setWeekDays(new int[0]);
            }

            if (RefCodeNames.ORDER_SCHEDULE_RULE_CD.DAY_MONTH.equals(orderScheduleJ.getOrderScheduleRuleCd())) {
                mgrForm.setMonthDayCycle("" + orderScheduleJ.getCycle());
                mgrForm.setMonthDays(orderScheduleJ.getElements());
            } else {
                mgrForm.setMonthDayCycle("1");
                mgrForm.setMonthDays(new int[0]);
            }

            if (RefCodeNames.ORDER_SCHEDULE_RULE_CD.WEEK_MONTH.equals(orderScheduleJ.getOrderScheduleRuleCd())) {
                mgrForm.setMonthWeekDay(orderScheduleJ.getMonthWeekDay());
                mgrForm.setMonthMonths(orderScheduleJ.getElements());
                mgrForm.setMonthWeeks(orderScheduleJ.getMonthWeeks());
            } else {
                mgrForm.setMonthWeekDay(2);
                mgrForm.setMonthMonths(new int[0]);
                mgrForm.setMonthWeeks(1);
            }

            String alsoS = "";
            Date[] alsoDates = orderScheduleJ.getAlsoDates();
            for (int jj = 0; jj < alsoDates.length; jj++) {
                Date dd = alsoDates[jj];
                if (dd != null) {
                    if (alsoS.length() > 0) {
                        alsoS += ", ";
                    }
                    alsoS += ClwI18nUtil.formatDateInp(request, dd);
                }
            }

            mgrForm.setAlsoDates(alsoS);
            String exceptS = "";
            Date[] exceptDates = orderScheduleJ.getExceptDates();
            for (int jj = 0; jj < exceptDates.length; jj++) {
                Date dd = exceptDates[jj];
                if (dd != null) {
                    if (exceptS.length() > 0) {
                        exceptS += ", ";
                    }
                    exceptS += ClwI18nUtil.formatDateInp(request, dd);
                }
            }

            mgrForm.setExcludeDates(exceptS);

            String contactName  = orderScheduleJ.getContactName();
            String contactPhone = orderScheduleJ.getContactPhone();
            String contactEmail = orderScheduleJ.getContactEmail();

            mgrForm.setContactName(Utility.strNN(contactName));
            mgrForm.setContactPhone(Utility.strNN(contactPhone));
            mgrForm.setContactEmail(Utility.strNN(contactEmail));

        }
        mgrForm.setOrderScheduleId(scheduleId);
        // show on calendar
        ae = showDates(request, mgrForm);
        if (ae.size() > 0) {
            return ae;
        }

        session.setAttribute(USER_WORK_ORDER_SCHEDULER_FORM, mgrForm);

        return ae;
    }


    public static ActionErrors deleteSchedule(HttpServletRequest request, UserWorkOrderSchedulerForm mgrForm) throws Exception {

        ActionErrors ae;
        HttpSession session = request.getSession();

        ae = checkRequest(request, mgrForm);
        if (ae.size() > 0) {
            return ae;
        }

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        UserData user = appUser.getUser();
        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);


        AutoOrder autoOrderEjb;
        try {
            autoOrderEjb = factory.getAutoOrderAPI();
        } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
            exc.printStackTrace();
            ae.add("error", new ActionError("error.systemError", "No order Ejb pointer"));
            return ae;
        }

        int scheduleId = mgrForm.getOrderScheduleId();
        if (scheduleId <= 0) {
            ae.add("error", new ActionError("error.systemError", "Nothing to delete"));
            return ae;
        }

        try {
            autoOrderEjb.deleteOrderSchedule(scheduleId, user.getUserName());
        } catch (RemoteException exc) {
            exc.printStackTrace();
            ae.add("error", new ActionError("error.systemError", exc.getMessage()));
            return ae;
        }

        //Update in list
        OrderScheduleDataVector orderSchedules = mgrForm.getOrderSchedules();
        for (int ii = 0; ii < orderSchedules.size(); ii++) {
            OrderScheduleData osv = (OrderScheduleData) orderSchedules.get(ii);
            if (osv.getOrderScheduleId() == scheduleId) {
                orderSchedules.remove(ii);
                break;
            }
        }

        ae = init(request, mgrForm);
        return ae;
    }

    /**
     * Error logging
     *
     * @param message - message which will be pasted to log
     * @param e       - Excepiton
     */
    private static void error(String message, Exception e) {
        StringWriter wr = new StringWriter();
        PrintWriter prW = new PrintWriter(wr);
        e.printStackTrace(prW);

    }

    public static ActionErrors navigateForward(HttpServletRequest request, UserWorkOrderSchedulerForm mgrForm) throws Exception {
        ActionErrors ae;
        ae = checkRequest(request, mgrForm);
        if (ae.size() > 0) {
            return ae;
        }

        mgrForm.advanceCalendar();

        ae = showDates(request, mgrForm);
        return ae;
    }

    public static ActionErrors navigateBackward(HttpServletRequest request, UserWorkOrderSchedulerForm mgrForm) throws Exception {
        ActionErrors ae;
        ae = checkRequest(request, mgrForm);
        if (ae.size() > 0) {
            return ae;
        }

        mgrForm.rewindCalendar();

        ae = showDates(request, mgrForm);
        return ae;
    }
    
    public static ActionErrors scheduleTypeChange(HttpServletRequest request, UserWorkOrderSchedulerForm mgrForm) throws Exception {
        ActionErrors ae;
        ae = checkRequest(request, mgrForm);
        if (ae.size() > 0) {
            return ae;
        }
        
        String schedulerType = mgrForm.getScheduleType();
        if (RefCodeNames.ORDER_SCHEDULE_RULE_CD.WEEK.equals(schedulerType)) {
            mgrForm.setMonthDays(new int[0]);
            mgrForm.setMonthMonths(new int[0]);
        } else if (RefCodeNames.ORDER_SCHEDULE_RULE_CD.DAY_MONTH.equals(schedulerType)) {
            mgrForm.setWeekDays(new int[0]);
            mgrForm.setMonthMonths(new int[0]);
        } else if (RefCodeNames.ORDER_SCHEDULE_RULE_CD.WEEK_MONTH.equals(schedulerType)) {
            mgrForm.setWeekDays(new int[0]);
            mgrForm.setMonthMonths(new int[0]);
        }
        return ae;
    }
}
