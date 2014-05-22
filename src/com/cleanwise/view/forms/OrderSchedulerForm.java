/**
 * Title:        UserShopForm
 * Description:  This is the Struts ActionForm class for the shopping page.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Yuriy Kupershmidt
 */

package com.cleanwise.view.forms;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.OrderGuideData;
import com.cleanwise.service.api.value.OrderGuideDataVector;
import com.cleanwise.service.api.value.OrderScheduleView;
import com.cleanwise.service.api.value.OrderScheduleViewVector;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.utils.Constants;

/**
 * Form bean for the shopping page. This form has the following fields,
 */

public final class OrderSchedulerForm extends ActionForm {

	// -------------------------------------------------------- Instance
	// Variables
	private String _scheduleType = "week";

	private OrderScheduleViewVector _orderSchedules = new OrderScheduleViewVector();

	private OrderGuideDataVector _userOrderGuides = null;

	private OrderGuideDataVector _templateOrderGuides = null;

	private int _orderScheduleId = 0;

	private int _orderGuideId = 0;

	private String _startDate = null;

	private String _endDate = null;

	private String _scheduleAction = RefCodeNames.ORDER_SCHEDULE_CD.NOTIFY;

	private String _weekCycle = "1";

	private int[] _weekDays = new int[0]; // Sunday - 1, ... Saturday - 6

	private String _monthDayCycle = "1";

	private int[] _monthDays = new int[0]; // 1 ... 28

	private String _monthWeekCycle = "1";

	private int _monthWeekDay = 2; // Sunday - 1, ... Saturday - 7, day - 8

	private int _monthWeeks = 1; // first - 1, second - 2, third - 3, forth
									// -4, last - 5

	private int[] _monthMonths = new int[0]; // January - 1, ... December -
												// 12

	private String _alsoDates = "";

	private String _excludeDates = "";

	private String _ccEmail = "";

	private GregorianCalendar _calendar = null;

	private int _nextOrderIndex = 0;

	private int _nextOrderRelMonth = 0;

	private int _nextOrderMonthDay = 0;

	private List _calendarDatesWithOrders = new ArrayList();

	private int _nextExcludedIndex = 0;

	private int _nextExcludedRelMonth = 0;

	private int _nextExcludedMonthDay = 0;

	private List _calendarExcludedDates = new ArrayList();

	// crc info
	private String _contactName = "";

	private String _contactPhone = "";

	private String _contactEmail = "";

	// -------------------------------------------------------- Interface
	// Methods
	public String getScheduleType() {
		return _scheduleType;
	}

	public void setScheduleType(String pValue) {
		_scheduleType = pValue;
	}

	public OrderScheduleViewVector getOrderSchedules() {
		return _orderSchedules;
	}

	public void setOrderSchedules(OrderScheduleViewVector pValue) {
		_orderSchedules = pValue;
	}

	public String getOrderScheduleName(HttpServletRequest request, int ind, String nameDateSeperator) {
		if (ind < 0 || ind >= _orderSchedules.size())
			return "";
		if (!Utility.isSet(nameDateSeperator)) {
			nameDateSeperator = " ";
		}
		OrderScheduleView osv = (OrderScheduleView) _orderSchedules.get(ind);
		return ClwI18nUtil.formatOrderScheduleName(request, osv, nameDateSeperator);
	}

	public String getOrderScheduleName(HttpServletRequest request, int ind) {
		return getOrderScheduleName(request, ind, "   ");
	}
	
	public String getOrderScheduleName(HttpServletRequest request, String nameDateSeperator) {
		String returnValue = StringUtils.EMPTY;
		int orderScheduleId = getOrderScheduleId();
		if (orderScheduleId > 0 && Utility.isSet(_orderSchedules)) {
			for (int i=0; i<_orderSchedules.size(); i++) {
				OrderScheduleView osv = (OrderScheduleView) _orderSchedules.get(i);
				if (osv.getOrderScheduleId() == orderScheduleId) {
					returnValue = getOrderScheduleName(request, i, nameDateSeperator);
					break;
				}
			}
		}
		return returnValue;
	}

	public String getOrderScheduleName(int ind) {
		if (ind < 0 || ind >= _orderSchedules.size())
			return "";
		OrderScheduleView osv = (OrderScheduleView) _orderSchedules.get(ind);
		StringBuilder scheduleName = new StringBuilder(50);
		scheduleName.append(osv.getOrderGuideName());
		Date startDate = osv.getEffDate();
		Date endDate = osv.getExpDate();
		if (startDate != null || endDate != null) {
			scheduleName.append(" (");
			if (startDate != null) {
				scheduleName.append(Constants.date2string(startDate));
			}
			else {
				scheduleName.append("...");
			}
			scheduleName.append("  -  ");
			if (endDate != null) {
				scheduleName.append(Constants.date2string(endDate));
			}
			else {
				scheduleName.append("...");
			}
			scheduleName.append(") ");
		}
		return scheduleName.toString();
	}

	public String getOrderScheduleId(int ind) {
		if (ind < 0 || ind >= _orderSchedules.size())
			return "";
		OrderScheduleView osv = (OrderScheduleView) _orderSchedules.get(ind);
		String ss = "" + osv.getOrderScheduleId();
		return ss;
	}

	public int getNextOrderIndex() {
		return _nextOrderIndex;
	}

	public void setNextOrderIndex(int pValue) {
		_nextOrderIndex = pValue;
	}

	public int getNextExcludedIndex() {
		return _nextExcludedIndex;
	}

	public void setNextExcludedIndex(int pValue) {
		_nextExcludedIndex = pValue;
	}

	// Calendar
	public int getOrderScheduleId() {
		return _orderScheduleId;
	}

	public void setOrderScheduleId(int pValue) {
		_orderScheduleId = pValue;
	}

	public int getNextOrderRelMonth() {
		return _nextOrderRelMonth;
	}

	public void setNextOrderRelMonth(int pValue) {
		_nextOrderRelMonth = pValue;
	}

	public int getNextOrderMonthDay() {
		return _nextOrderMonthDay;
	}

	public void setNextOrderMonthDay(int pValue) {
		_nextOrderMonthDay = pValue;
	}

	public List getCalendarDatesWithOrders() {
		return _calendarDatesWithOrders;
	}

	public void setCalendarDatesWithOrders(List pValue) {
		_calendarDatesWithOrders = pValue;
	}

	public int getNextExcludedRelMonth() {
		return _nextExcludedRelMonth;
	}

	public void setNextExcludedRelMonth(int pValue) {
		_nextExcludedRelMonth = pValue;
	}

	public int getNextExcludedMonthDay() {
		return _nextExcludedMonthDay;
	}

	public void setNextExcludedMonthDay(int pValue) {
		_nextExcludedMonthDay = pValue;
	}

	public List getCalendarExcludedDates() {
		return _calendarExcludedDates;
	}

	public void setCalendarExcludedDates(List pValue) {
		_calendarExcludedDates = pValue;
	}

	public GregorianCalendar getCalendar() {
		return _calendar;
	}

	private int mCurrentYear;

	public void advanceCalendar() {

		if (mCurrentYear > 0) {
			mCurrentYear++;
		} else {
			mCurrentYear = _calendar.get(GregorianCalendar.YEAR) + 1;
		}
		_calendar.set(GregorianCalendar.YEAR, mCurrentYear);

	}

	public void rewindCalendar() {

		if (mCurrentYear > 0) {
			mCurrentYear--;
		} else {
			mCurrentYear = _calendar.get(GregorianCalendar.YEAR) - 1;
		}

		if (mCurrentYear < 2000) {
			mCurrentYear = 2000;
		}
		_calendar.set(GregorianCalendar.YEAR, mCurrentYear);
	}

	public void setCalendar(GregorianCalendar pValue) {
		GregorianCalendar cal = new GregorianCalendar(pValue
				.get(GregorianCalendar.YEAR), 0, 1);
		_calendar = cal;
	}

	public int[] getDaysInMonth(int pMonth) {

		int[] daysInMonth = new int[43];
		for (int i = 0; i < daysInMonth.length; i++) {
			daysInMonth[i] = -1;
		}

		GregorianCalendar cal = new GregorianCalendar(_calendar
				.get(GregorianCalendar.YEAR), pMonth, 1);

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

			for (int withIdx = 0; withIdx < _calendarDatesWithOrders.size(); withIdx++) {
				GregorianCalendar gc = (GregorianCalendar) _calendarDatesWithOrders
						.get(withIdx);
				if (cal.getTimeInMillis() == gc.getTimeInMillis()) {
					res *= 100;
					break;
				}
			}

			for (int xclIdx = 0; xclIdx < _calendarExcludedDates.size(); xclIdx++) {
				GregorianCalendar gc = (GregorianCalendar) _calendarExcludedDates
						.get(xclIdx);
				if (cal.getTimeInMillis() == gc.getTimeInMillis()) {
					res *= 10000;
					break;
				}
			}
			daysInMonth[i] = res;
			cal.add(GregorianCalendar.DAY_OF_MONTH, 1);
		}

		return daysInMonth;
	}

	// Compute whether there is any action on the day
	// provided.

	public String getMonth(int relativeMonth) {
		int month = _calendar.get(GregorianCalendar.MONTH);
		month += relativeMonth;
		month %= 12;
		String name = "";
		switch (month) {
		case 0:
			name = "January";
			break;
		case 1:
			name = "February";
			break;
		case 2:
			name = "March";
			break;
		case 3:
			name = "April";
			break;
		case 4:
			name = "May";
			break;
		case 5:
			name = "June";
			break;
		case 6:
			name = "July";
			break;
		case 7:
			name = "August";
			break;
		case 8:
			name = "September";
			break;
		case 9:
			name = "October";
			break;
		case 10:
			name = "November";
			break;
		case 11:
			name = "December";
			break;
		}
		return name;
	}

	public int getScheduleYear() {
		// the whole year is displayed at a time now
		return _calendar.get(GregorianCalendar.YEAR);
	}

	public void resetScheduleYear() {
		mCurrentYear = 0;
		_calendar = new GregorianCalendar();
	}

	// Start date , end date
	public String getStartDate() {
		return _startDate;
	}

	public void setStartDate(String pValue) {
		_startDate = pValue;
	}

	public String getEndDate() {
		return _endDate;
	}

	public void setEndDate(String pValue) {
		_endDate = pValue;
	}

	public String getScheduleAction() {
		return _scheduleAction;
	}

	public void setScheduleAction(String pValue) {
		_scheduleAction = pValue;
	}

	// Week schdule
	public String getWeekCycle() {
		return _weekCycle;
	}

	public void setWeekCycle(String pValue) {
		_weekCycle = pValue;
	}

	public int[] getWeekDays() {
		return _weekDays;
	}

	public void setWeekDays(int[] pValue) {
		_weekDays = pValue;
	}

	// Month day schdule
	public String getMonthDayCycle() {
		return _monthDayCycle;
	}

	public void setMonthDayCycle(String pValue) {
		_monthDayCycle = pValue;
	}

	public int[] getMonthDays() {
		return _monthDays;
	}

	public void setMonthDays(int[] pValue) {
		_monthDays = pValue;
	}

	// Month week schdule
	public String getMonthWeekCycle() {
		return _monthWeekCycle;
	}

	public void setMonthWeekCycle(String pValue) {
		_monthWeekCycle = pValue;
	}

	public int getMonthWeekDay() {
		return _monthWeekDay;
	}

	public void setMonthWeekDay(int pValue) {
		_monthWeekDay = pValue;
	}

	public int getMonthWeeks() {
		return _monthWeeks;
	}

	public void setMonthWeeks(int pValue) {
		_monthWeeks = pValue;
	}

	public int[] getMonthMonths() {
		return _monthMonths;
	}

	public void setMonthMonths(int[] pValue) {
		_monthMonths = pValue;
	}

	// Excepitons
	public String getAlsoDates() {
		return _alsoDates;
	}

	public void setAlsoDates(String pValue) {
		_alsoDates = pValue;
	}

	public String getExcludeDates() {
		return _excludeDates;
	}

	public void setExcludeDates(String pValue) {
		_excludeDates = pValue;
	}

	// CC Email
	public String getCcEmail() {
		return _ccEmail;
	}

	public void setCcEmail(String pValue) {
		_ccEmail = pValue;
	}

	// Order guides
	public int getOrderGuideId() {
		return _orderGuideId;
	}

	public void setOrderGuideId(int pValue) {
		_orderGuideId = pValue;
	}

	// User order quides
	public OrderGuideDataVector getUserOrderGuides() {
		return _userOrderGuides;
	}

	public void setUserOrderGuides(OrderGuideDataVector pValue) {
		_userOrderGuides = pValue;
	}

	public ArrayList getUserOrderGuideIds() {
		ArrayList al = new ArrayList();
		if (_userOrderGuides == null)
			return al;
		for (int ii = 0; ii < _userOrderGuides.size(); ii++) {
			OrderGuideData ogD = (OrderGuideData) _userOrderGuides.get(ii);
			al.add("" + ogD.getOrderGuideId());
		}
		return al;
	}

	public ArrayList getUserOrderGuideNames() {
		ArrayList al = new ArrayList();
		if (_userOrderGuides == null)
			return al;
		for (int ii = 0; ii < _userOrderGuides.size(); ii++) {
			OrderGuideData ogD = (OrderGuideData) _userOrderGuides.get(ii);
			al.add(ogD.getShortDesc());
		}
		return al;
	}

	public int getUserOrderGuideNumber() {
		if (_userOrderGuides == null)
			return 0;
		return _userOrderGuides.size();
	}

	// Template orde guides
	public OrderGuideDataVector getTemplateOrderGuides() {
		return _templateOrderGuides;
	}

	public void setTemplateOrderGuides(OrderGuideDataVector pValue) {
		_templateOrderGuides = pValue;
	}

	public ArrayList getTemplateOrderGuideIds() {
		ArrayList al = new ArrayList();
		if (_templateOrderGuides == null)
			return al;
		for (int ii = 0; ii < _templateOrderGuides.size(); ii++) {
			OrderGuideData ogD = (OrderGuideData) _templateOrderGuides.get(ii);
			al.add("" + ogD.getOrderGuideId());
		}
		return al;
	}

	public ArrayList getTemplateOrderGuideNames() {
		ArrayList al = new ArrayList();
		if (_templateOrderGuides == null)
			return al;
		for (int ii = 0; ii < _templateOrderGuides.size(); ii++) {
			OrderGuideData ogD = (OrderGuideData) _templateOrderGuides.get(ii);
			al.add(ogD.getShortDesc());
		}
		return al;
	}

	public int getTemplateOrderGuideNumber() {
		if (_templateOrderGuides == null)
			return 0;
		return _templateOrderGuides.size();
	}

	// Contact Name
	public String getContactName() {
		return _contactName;
	}

	public void setContactName(String pValue) {
		_contactName = pValue;
	}

	// Contact Phone Number
	public String getContactPhone() {
		return _contactPhone;
	}

	public void setContactPhone(String pValue) {
		_contactPhone = pValue;
	}

	// Contact Email
	public String getContactEmail() {
		return _contactEmail;
	}

	public void setContactEmail(String pValue) {
		_contactEmail = pValue;
	}

	/**
	 * Reset all properties to their default values.
	 * 
	 * @param mapping
	 *            The mapping used to select this instance
	 * @param request
	 *            The servlet request we are processing
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {

		_calendarDatesWithOrders = new ArrayList();
		_calendarExcludedDates = new ArrayList();
		_calendar = new GregorianCalendar();

	}

	/**
	 * Validate the properties that have been set from this HTTP request, and
	 * return an <code>ActionErrors</code> object that encapsulates any
	 * validation errors that have been found. If no errors are found, return
	 * <code>null</code> or an <code>ActionErrors</code> object with no
	 * recorded error messages.
	 * 
	 * @param mapping
	 *            The mapping used to select this instance
	 * @param request
	 *            The servlet request we are processing
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {

		ActionErrors errors = new ActionErrors();
		return errors;
	}

}
