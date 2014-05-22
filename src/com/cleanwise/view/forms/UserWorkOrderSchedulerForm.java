package com.cleanwise.view.forms;

import com.cleanwise.service.api.value.OrderScheduleDataVector;
import com.cleanwise.service.api.value.OrderScheduleView;
import com.cleanwise.service.api.value.WorkOrderData;
import com.cleanwise.service.api.value.OrderScheduleData;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.utils.Constants;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Title:        UserWorkOrderSchedulerForm
 * Description:  Form bean for the work order page.
 * Purpose:      Holds data for creation a schedule of orders
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * Date:         25.05.2008
 * Time:         16:56:42
 *
 * @author Alexander Chickin TrinitySoft, Inc.
 */
public class UserWorkOrderSchedulerForm extends ActionForm {

    public boolean init = false;

    private OrderScheduleDataVector orderSchedules;
    private String scheduleType = "week";
    private int orderScheduleId = 0;
    private String startDate;
    private String endDate;
    private String scheduleAction;
    private String ccEmail;
    private String weekCycle;
    private int[] weekDays;
    private String monthDayCycle;
    private int[] monthDays;
    private String monthWeekCycle;
    private int monthWeekDay;
    private int monthWeeks;
    private int[] monthMonths;
    private String alsoDates;
    private String excludeDates;
    private String contactName;
    private String contactPhone;
    private String contactEmail;
    private GregorianCalendar calendar;
    private List calendarDatesWithOrders;
    private int nextOrderIndex;
    private int nextOrderRelMonth;
    private int nextOrderMonthDay;
    private List calendarExcludedDates;
    private int nextExcludedIndex;
    private int nextExcludedRelMonth;
    private int nextExcludedMonthDay;
    private int currentYear;
    private WorkOrderData workOrder;

    public void init() {
        init = true;
    }

    public void setOrderSchedules(OrderScheduleDataVector orderSchedules) {
        this.orderSchedules = orderSchedules;
    }

    public OrderScheduleDataVector getOrderSchedules() {
        return orderSchedules;
    }

    public String getScheduleType() {
        return scheduleType;
    }

    public void setScheduleType(String scheduleType) {
        this.scheduleType = scheduleType;
    }

    public int getOrderScheduleId() {
        return orderScheduleId;
    }

    public void setOrderScheduleId(int orderScheduleId) {
        this.orderScheduleId = orderScheduleId;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setScheduleAction(String scheduleAction) {
        this.scheduleAction = scheduleAction;
    }

    public void setCcEmail(String ccEmail) {
        this.ccEmail = ccEmail;
    }

    public void setWeekCycle(String weekCycle) {
        this.weekCycle = weekCycle;
    }

    public void setWeekDays(int[] weekDays) {
        this.weekDays = weekDays;
    }

    public void setMonthDayCycle(String monthDayCycle) {
        this.monthDayCycle = monthDayCycle;
    }

    public void setMonthDays(int[] monthDays) {
        this.monthDays = monthDays;
    }

    public void setMonthWeekCycle(String monthWeekCycle) {
        this.monthWeekCycle = monthWeekCycle;
    }

    public void setMonthWeekDay(int monthWeekDay) {
        this.monthWeekDay = monthWeekDay;
    }

    public void setMonthWeeks(int monthWeeks) {
        this.monthWeeks = monthWeeks;
    }

    public void setMonthMonths(int[] monthMonths) {
        this.monthMonths = monthMonths;
    }

    public void setAlsoDates(String alsoDates) {
        this.alsoDates = alsoDates;
    }

    public void setExcludeDates(String excludeDates) {
        this.excludeDates = excludeDates;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }


    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getScheduleAction() {
        return scheduleAction;
    }

    public String getCcEmail() {
        return ccEmail;
    }

    public String getWeekCycle() {
        return weekCycle;
    }

    public int[] getWeekDays() {
        return weekDays;
    }

    public String getMonthDayCycle() {
        return monthDayCycle;
    }

    public int[] getMonthDays() {
        return monthDays;
    }

    public String getMonthWeekCycle() {
        return monthWeekCycle;
    }

    public int getMonthWeeks() {
        return monthWeeks;
    }

    public int getMonthWeekDay() {
        return monthWeekDay;
    }

    public int[] getMonthMonths() {
        return monthMonths;
    }

    public String getAlsoDates() {
        return alsoDates;
    }

    public String getExcludeDates() {
        return excludeDates;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public String getContactName() {
        return contactName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public GregorianCalendar getCalendar() {
        return calendar;
    }

    public void setCalendarDatesWithOrders(List calendarDatesWithOrders) {
        this.calendarDatesWithOrders = calendarDatesWithOrders;
    }

    public List getCalendarDatesWithOrders() {
        return calendarDatesWithOrders;
    }

    public void setNextOrderIndex(int nextOrderIndex) {
        this.nextOrderIndex = nextOrderIndex;
    }

    public int getNextOrderIndex() {
        return nextOrderIndex;
    }

    public void setNextOrderRelMonth(int nextOrderRelMonth) {
        this.nextOrderRelMonth = nextOrderRelMonth;
    }

    public int getNextOrderRelMonth() {
        return nextOrderRelMonth;
    }

    public void setNextOrderMonthDay(int nextOrderMonthDay) {
        this.nextOrderMonthDay = nextOrderMonthDay;
    }


    public int getNextOrderMonthDay() {
        return nextOrderMonthDay;
    }

    public void setCalendarExcludedDates(List calendarExcludedDates) {
        this.calendarExcludedDates = calendarExcludedDates;
    }

    public List getCalendarExcludedDates() {
        return calendarExcludedDates;
    }

    public void setNextExcludedIndex(int nextExcludedIndex) {
        this.nextExcludedIndex = nextExcludedIndex;
    }

    public int getNextExcludedIndex() {
        return nextExcludedIndex;
    }

    public void setNextExcludedRelMonth(int nextExcludedRelMonth) {
        this.nextExcludedRelMonth = nextExcludedRelMonth;
    }

    public int getNextExcludedRelMonth() {
        return nextExcludedRelMonth;
    }

    public void setNextExcludedMonthDay(int nextExcludedMonthDay) {
        this.nextExcludedMonthDay = nextExcludedMonthDay;
    }

    public int getNextExcludedMonthDay() {
        return nextExcludedMonthDay;
    }


    public int getCurrentYear() {
        return currentYear;
    }

    public void setCurrentYear(int currentYear) {
        this.currentYear = currentYear;
    }

    public void resetScheduleYear() {
        currentYear = 0;
        calendar = new GregorianCalendar();
    }

    public int getScheduleYear() {
        // the whole year is displayed at a time now
        return calendar.get(GregorianCalendar.YEAR);
    }
    /**
     * Reset all properties to their default values.
     *
     * @param mapping The mapping used to select this instance
     * @param request The servlet request we are processing
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {

        calendarDatesWithOrders = new ArrayList();
        calendarExcludedDates = new ArrayList();
        calendar = new GregorianCalendar();

    }

    public String getOrderScheduleName(HttpServletRequest request, int ind) {
        if (ind < 0 || ind >= orderSchedules.size()){
            return "";
        }
        OrderScheduleData osv = (OrderScheduleData) orderSchedules.get(ind);
        String ss = "";
        ss += "( ";
        Date dd = osv.getEffDate();
        ss += ClwI18nUtil.formatDateInp(request, dd);
        ss += "    -   ";
        dd = osv.getExpDate();
        if (dd == null) {
            ss += " ... ";
        } else {
            ss += ClwI18nUtil.formatDateInp(request, dd);
        }
        ss += " )";
        return ss;
    }

    public String getOrderScheduleName(int ind) {
        if (ind < 0 || ind >= orderSchedules.size()) {
            return "";
        }
        OrderScheduleData osv = (OrderScheduleData) orderSchedules.get(ind);
        String ss = "";
        ss += "( ";
        Date dd = osv.getEffDate();
        ss += Constants.date2string(dd);
        ss += "    -   ";
        dd = osv.getExpDate();
        if (dd == null) {
            ss += " ... ";
        } else {
            ss += Constants.date2string(dd);
        }
        ss += " )";
        return ss;
    }

    public String getOrderScheduleId(int ind) {
        if (ind < 0 || ind >= orderSchedules.size()) {
            return "";
        }
        OrderScheduleData osv = (OrderScheduleData) orderSchedules.get(ind);
        return "" + osv.getOrderScheduleId();
    }


    public void advanceCalendar() {
        if (currentYear > 0) {
            currentYear++;
        } else {
            currentYear = calendar.get(GregorianCalendar.YEAR) + 1;
        }
        calendar.set(GregorianCalendar.YEAR, currentYear);
    }

    public void rewindCalendar() {
        if (currentYear > 0) {
            currentYear--;
        } else {
            currentYear = calendar.get(GregorianCalendar.YEAR) - 1;
        }
        if (currentYear < 2000) {
            currentYear = 2000;
        }
        calendar.set(GregorianCalendar.YEAR, currentYear);
    }

	public void setCalendar(GregorianCalendar pValue) {
		calendar = new GregorianCalendar(pValue.get(GregorianCalendar.YEAR), 0, 1);
	}

	public int[] getDaysInMonth(int pMonth) {

        int[] daysInMonth = new int[0];
        try {
            daysInMonth = new int[43];
            for (int i = 0; i < daysInMonth.length; i++) {
                daysInMonth[i] = -1;
            }

            GregorianCalendar cal = new GregorianCalendar(calendar.get(GregorianCalendar.YEAR), pMonth, 1);

            // get the first day of the month
            int firstDay = cal.get(GregorianCalendar.DAY_OF_WEEK) -1;
            // get the last day of the month
            int lastDay = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);

            int d = 0;
            for (int i = firstDay; i < (lastDay + firstDay); i++) {
                daysInMonth[i] = ++d;
            }

            for (int i = 0; i < daysInMonth.length; i++) {
                int res = daysInMonth[i];
                if (res < 0)
                    continue;

                for (int withIdx = 0; withIdx < calendarDatesWithOrders.size(); withIdx++) {
                    GregorianCalendar gc = (GregorianCalendar) calendarDatesWithOrders.get(withIdx);
                    if (cal.getTimeInMillis() == gc.getTimeInMillis()) {
                        res *= 100;
                        break;
                    }
                }

                for (int xclIdx = 0; xclIdx < calendarExcludedDates.size(); xclIdx++) {
                    GregorianCalendar gc = (GregorianCalendar) calendarExcludedDates.get(xclIdx);
                    if (cal.getTimeInMillis() == gc.getTimeInMillis()) {
                        res *= 10000;
                        break;
                    }
                }
                daysInMonth[i] = res;
                cal.add(GregorianCalendar.DAY_OF_MONTH, 1);
            }
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return daysInMonth;
	}

	// Compute whether there is any action on the day
	// provided.

    public String getMonth(int relativeMonth) {
        int month = calendar.get(GregorianCalendar.MONTH);
        month += relativeMonth;
        month %= 12;
        String name = "";
        switch (month) {
            case 0:	name = "January";   break;
            case 1:	name = "February";  break;
            case 2:	name = "March"; 	break;
            case 3:	name = "April"; 	break;
            case 4:	name = "May";       break;
            case 5:	name = "June";      break;
            case 6:	name = "July";  	break;
            case 7:	name = "August";    break;
            case 8:	name = "September";	break;
            case 9:	name = "October";	break;
            case 10:name = "November";	break;
            case 11:name = "December";	break;
        }
        return name;
    }

    public void setWorkOrder(WorkOrderData workOrder) {
        this.workOrder = workOrder;
    }

    public WorkOrderData getWorkOrder() {
        return workOrder;
    }
}
