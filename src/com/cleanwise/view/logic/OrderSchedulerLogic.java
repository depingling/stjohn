/**
 * Title:        UserShopLogic
 * Description:  This is the business logic class for the UserShopAction and UserShopForm.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Yuriy Kupershmidt
 */

package com.cleanwise.view.logic;

import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.AutoOrder;
import com.cleanwise.service.api.session.OrderGuide;
import com.cleanwise.service.api.session.ShoppingServices;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.EmailData;
import com.cleanwise.service.api.value.OrderGuideData;
import com.cleanwise.service.api.value.OrderGuideDataVector;
import com.cleanwise.service.api.value.OrderGuideStructureData;
import com.cleanwise.service.api.value.OrderGuideStructureDataVector;
import com.cleanwise.service.api.value.OrderScheduleJoin;
import com.cleanwise.service.api.value.OrderScheduleView;
import com.cleanwise.service.api.value.OrderScheduleViewVector;
import com.cleanwise.service.api.value.SiteData;
import com.cleanwise.service.api.value.UserData;
import com.cleanwise.view.forms.OrderSchedulerForm;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.ShopTool;
import com.cleanwise.view.utils.validators.EmailValidator;
import com.espendwise.view.forms.esw.UserProfileForm;
import com.espendwise.view.logic.esw.UserProfileLogic;

/**
 * Class description.
 *
 */
public class OrderSchedulerLogic {

    private static final Logger log = Logger.getLogger(OrderSchedulerLogic.class);

	public static ActionErrors init(HttpServletRequest request,
			OrderSchedulerForm pForm, boolean resetScheduleData) {
		ActionErrors ae = new ActionErrors();
		HttpSession session = request.getSession();
		APIAccess factory = (APIAccess) session
				.getAttribute(Constants.APIACCESS);
		if (factory == null) {
			ae.add("error", new ActionError("error.systemError",
					"No Ejb access"));
			return ae;
		}

		ShoppingServices shoppingServEjb = null;
		try {
			shoppingServEjb = factory.getShoppingServicesAPI();
		} catch (com.cleanwise.service.api.APIServiceAccessException exc) {
			ae.add("error", new ActionError("error.systemError",
					"No shopping services Ejb pointer"));
			return ae;
		}
		
		CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
		if (appUser == null) {
			ae.add("error", new ActionError("error.systemError", "No "
					+ Constants.APP_USER + "session object found"));
			return ae;
		}
		UserData user = appUser.getUser();
		int siteId = appUser.getSite().getBusEntity().getBusEntityId();
		//Prepare list of schedules
		int userid = user.getUserId();
		if (appUser.getSite().isSetupForSharedOrderGuides()) {
			// This will force the system to get all available order
			// schedules and order guides.
			userid = 0;
		}

		try {
			OrderScheduleViewVector orderSchedules = APIAccess.getAPIAccess().getAutoOrderAPI()
					.getOrderSchedules(siteId, userid);
			pForm.setOrderSchedules(orderSchedules);
		} catch (Exception exc) {
			ae.add("error", new ActionError("error.systemError", exc
					.getMessage()));
			return ae;
		}

		Integer catalogIdI = (Integer) session
				.getAttribute(Constants.CATALOG_ID);
		if (catalogIdI == null) {
			ae.add("error", new ActionError("error.systemError", "No "
					+ Constants.CATALOG_ID + " session object found"));
			return ae;
		}
		int catalogId = catalogIdI.intValue();
		//initialize order guide list
		OrderGuideDataVector userOrderGuideDV = null;
		OrderGuideDataVector templateOrderGuideDV = null;
		try {
			userOrderGuideDV = shoppingServEjb.getUserOrderGuides(userid,
					catalogId, siteId);
			templateOrderGuideDV = shoppingServEjb.getTemplateOrderGuides(
					catalogId, siteId);
		} catch (RemoteException exc) {
			exc.printStackTrace();
			ae.add("error", new ActionError("error.systemError",
					"Can't pick up order guides"));
			return ae;
		}
		pForm.setUserOrderGuides(userOrderGuideDV);
		pForm.setTemplateOrderGuides(templateOrderGuideDV);

		if (resetScheduleData) {
			initFields(request, pForm);
		}
		initializeFormCalendar(pForm);

		orderSchedules(pForm);

		return ae;
	}

	public static ActionErrors init(HttpServletRequest request,
			OrderSchedulerForm pForm, boolean resetScheduleData, 
			boolean removeSchedulesFromUnsharedLists) {
		ActionErrors ae = init(request, pForm, resetScheduleData);
		CleanwiseUser user = ShopTool.getCurrentUser(request);
		//if we initialized without errors and we are to remove schedules created 
		//from unshared shopping lists, do so now
		if ((ae == null || ae.isEmpty()) && 
				Utility.isSet(pForm.getOrderSchedules()) &&
				removeSchedulesFromUnsharedLists && 
				!user.getSite().isSetupForSharedOrderGuides()) {
			ShoppingServices shoppingServEjb = null;
			try {
				shoppingServEjb = APIAccess.getAPIAccess().getShoppingServicesAPI();
				int catalogId = ((Integer) request.getSession().getAttribute(Constants.CATALOG_ID)).intValue();
				int siteId = user.getSite().getBusEntity().getBusEntityId();
				//get all shopping lists available for the site
				OrderGuideDataVector unsharedShoppingLists = shoppingServEjb.getUserOrderGuides(0, catalogId, siteId);
				//get the shopping lists accessible by the user
				OrderGuideDataVector userShoppingLists = pForm.getUserOrderGuides();
				//remove the shopping lists accessible by the user from the lists available for the
				//site to get a list of unshared shopping lists
				Iterator<OrderGuideData> userShoppingListIterator = userShoppingLists.iterator();
				while (userShoppingListIterator.hasNext()) {
					OrderGuideData userShoppingList = userShoppingListIterator.next();
					Iterator<OrderGuideData> unsharedShoppingListIterator = unsharedShoppingLists.iterator();
					while (unsharedShoppingListIterator.hasNext()) {
						OrderGuideData unsharedShoppingList = unsharedShoppingListIterator.next();
						if (userShoppingList.getOrderGuideId() == unsharedShoppingList.getOrderGuideId()) {
							unsharedShoppingListIterator.remove();
						}
					}
				}
				//build a list of unshared shopping list ids
				if (Utility.isSet(unsharedShoppingLists)) {
					List<Integer> unsharedShoppingListIds = new ArrayList<Integer>();
					Iterator<OrderGuideData> unsharedShoppingListIterator = unsharedShoppingLists.iterator();
					while (unsharedShoppingListIterator.hasNext()) {
						unsharedShoppingListIds.add(unsharedShoppingListIterator.next().getOrderGuideId());
					}
					//iterate over the order schedules, removing any based on unshared shopping lists
					Iterator<OrderScheduleView> orderScheduleIterator = pForm.getOrderSchedules().iterator();
					while (orderScheduleIterator.hasNext()) {
						OrderScheduleView orderSchedule = orderScheduleIterator.next();
						if (unsharedShoppingListIds.contains(orderSchedule.getOrderGuideId())) {
							orderScheduleIterator.remove();
						}
					}
				}
			} catch (Exception exc) {
                String errorMess = ClwI18nUtil.getMessage(request, "error.unExpectedError");
                ae.add("error", new ActionError("error.simpleGenericError", errorMess));
				return ae;
			}
		}
		return ae;
	}
	
	public static void initializeFormCalendar(OrderSchedulerForm pForm) {
		//Calendar start date
		Date curDate = Constants.getCurrentDate();
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(curDate);
		calendar.set(GregorianCalendar.DAY_OF_YEAR, 1);
		calendar.set(GregorianCalendar.DAY_OF_MONTH, 1);
		pForm.setCalendar(calendar);
	}

    /**
     * Validate the data for the creation/modification of an order schedule.
     * Note - this method was created for the new UI, which has slightly different validation
     * 	requirements than the existing UI.  Ideally, the saveSchedule() method would be
     * 	modified to remove all of the validation checks that it currently performs and instead
     * 	call this method first, but that cannot be done while the validation requirements are
     * 	different.
     * @param   request - the <code>HttpServletRequest</code> currently being handled.
     * @param   pForm - an <code>OrderSchedulerForm</code> containing the order schedule data.
     * @return  An <code>ActionErrors</code> object containing any errors.
     */
	public static ActionErrors validateSaveSchedule(HttpServletRequest request, OrderSchedulerForm pForm) {

		ActionErrors errors = new ActionErrors();
		
		//if the form hasn't been initialized with a calendar, do that now.  This field needs
		//to have been initialized for some of the validation performed below.
		if (pForm.getCalendar() == null) {
			initializeFormCalendar(pForm);
		}
		
    	//if the user has the browse only privilege set, return an error without doing any
		//additional error checking
    	CleanwiseUser currentUser = ShopTool.getCurrentUser(request);
    	if (currentUser.isBrowseOnly()) {
            String errorMess = ClwI18nUtil.getMessage(request, "orders.schedule.error.notAuthorizedForSave", null);
            errors.add("error", new ActionError("error.simpleGenericError", errorMess));
            return errors;
    	}
		
		//check that a valid shopping list has been specified
		int shoppingListId = pForm.getOrderGuideId();
		if (shoppingListId <= 0) {
			String errorMess = ClwI18nUtil.getMessage(request,"orders.schedule.error.noShoppingList");
			errors.add("error", new ActionError("error.simpleGenericError",	errorMess));
		}
		//check that the specified shopping list contains at least one item with a quantity > 0
		else {
		    OrderGuideStructureDataVector orderGuideItems = null;
		    try {
		    	orderGuideItems = APIAccess.getAPIAccess().getOrderGuideAPI().getOrderGuideStructure(shoppingListId);
		    } catch (Exception exc) {
	            String errorMess = ClwI18nUtil.getMessage(request, "orders.schedule.error.unexpectedShoppingListRetrievalError");
	            errors.add("error", new ActionError("error.simpleGenericError", errorMess));
	            return errors;
		    }
		    if (orderGuideItems == null) {
		    	orderGuideItems = new OrderGuideStructureDataVector();
		    }
		    Iterator<OrderGuideStructureData> orderGuideItemIterator = orderGuideItems.iterator();
		    boolean emptyShoppingList = true;
		    while (orderGuideItemIterator.hasNext() && emptyShoppingList) {
		    	OrderGuideStructureData item = orderGuideItemIterator.next();
		    	emptyShoppingList = (item.getQuantity() <= 0);
		    }
		    if (emptyShoppingList) {
				String errorMess = ClwI18nUtil.getMessage(request,"orders.schedule.error.noItemsWithQuantitySet");
				errors.add("error", new ActionError("error.simpleGenericError",	errorMess));
		    }
		}
		
		//check that a valid start date has been specified
		String startDateValue = pForm.getStartDate();
		Date startDate = null;
		if (!Utility.isSet(startDateValue)) {
			String errorMess = ClwI18nUtil.getMessage(request,"orders.schedule.error.noStartDate");
			errors.add("error", new ActionError("error.simpleGenericError",	errorMess));
		}
		else {
			try {
				startDate = ClwI18nUtil.parseDateInp(request, startDateValue.trim());
			}
			catch (ParseException pe) {
				String errorMess = ClwI18nUtil.getMessage(request,"orders.schedule.error.invalidStartDate");
				errors.add("error", new ActionError("error.simpleGenericError",	errorMess));
			}
		}
		
		//check that a valid end date has been specified
		String endDateValue = pForm.getEndDate();
		Date endDate = null;
		if (!Utility.isSet(endDateValue)) {
			String errorMess = ClwI18nUtil.getMessage(request,"orders.schedule.error.noEndDate");
			errors.add("error", new ActionError("error.simpleGenericError",	errorMess));
		}
		else {
			try {
				endDate = ClwI18nUtil.parseDateInp(request, endDateValue.trim());
			}
			catch (ParseException pe) {
				String errorMess = ClwI18nUtil.getMessage(request,"orders.schedule.error.invalidEndDate");
				errors.add("error", new ActionError("error.simpleGenericError",	errorMess));
			}
		}
		
		//check that a valid notification value has been specified
		String scheduleAction = pForm.getScheduleAction();
		if (!Utility.isSet(scheduleAction)) {
			String errorMess = ClwI18nUtil.getMessage(request,"orders.schedule.error.noType");
			errors.add("error", new ActionError("error.simpleGenericError",	errorMess));
		}
		else {
			if (!RefCodeNames.ORDER_SCHEDULE_CD.NOTIFY.equals(scheduleAction) &&
					!RefCodeNames.ORDER_SCHEDULE_CD.PLACE_ORDER.equals(scheduleAction)) {
				String errorMess = ClwI18nUtil.getMessage(request,"orders.schedule.error.invalidType");
				errors.add("error", new ActionError("error.simpleGenericError",	errorMess));
			}
			else {
				if (RefCodeNames.ORDER_SCHEDULE_CD.NOTIFY.equals(scheduleAction)) {
					UserProfileForm userForm = new UserProfileForm();
					ActionErrors userProfileErrors = UserProfileLogic.getUserDetailById(request, 
							ShopTool.getCurrentUser(request).getUserId(), userForm);
					if (userProfileErrors != null && !userProfileErrors.isEmpty()) {
						String errorMess = ClwI18nUtil.getMessage(request,"orders.schedule.error.cannotRetrieveUserInfo");
						errors.add("error", new ActionError("error.simpleGenericError",	errorMess));
					}
					else {
						EmailData emailData = userForm.getUserInfo().getEmailData();
						if (emailData == null || 
								!RefCodeNames.EMAIL_STATUS_CD.ACTIVE.equalsIgnoreCase(emailData.getEmailStatusCd()) ||
								!Utility.isValidEmailAddress(emailData.getEmailAddress())) {
							String errorMess = ClwI18nUtil.getMessage(request,"orders.schedule.error.invalidEmailAddress");
							errors.add("error", new ActionError("error.simpleGenericError",	errorMess));
						}
					}
				}
			}
		}
		
		//if CC Email address information has been specified, make sure it is valid
		String ccEmail = Utility.safeTrim(Utility.strNN(pForm.getCcEmail()));
		if (Utility.isSet(ccEmail)) {
			//convert any commas to semicolons so that either commas or semicolons can be used
			//as a separator.
			ccEmail = ccEmail.replaceAll(Constants.COMMA, Constants.SEMICOLON);
			StringBuilder trimmedEmailAddresses = new StringBuilder(Constants.MAX_LENGTH_ORDER_SCHEDULE_CC_EMAIL_VALUE);
		    String[] emailAddresses = ccEmail.split(Constants.SEMICOLON);
		    for (int index=0; index<emailAddresses.length; index++) {
		    	String emailAddress = Utility.safeTrim(emailAddresses[index]);
		        EmailValidator.validateEmail(request, errors, ClwI18nUtil.getMessage(request,"orders.schedules.label.ccEmail"), emailAddress);
		        trimmedEmailAddresses.append(emailAddress);
		        if (index != (emailAddresses.length - 1)) {
		        	trimmedEmailAddresses.append(Constants.SEMICOLON);
		        }
		    }
		    //ensure the length of the cc email field is under the maximum
		    ccEmail = trimmedEmailAddresses.toString();
		    if (ccEmail.length() > Constants.MAX_LENGTH_ORDER_SCHEDULE_CC_EMAIL_VALUE) {
	        	Object[] insertionStrings = new Object[2];
	        	insertionStrings[0] = ClwI18nUtil.getMessage(request,"orders.schedules.label.ccEmail");
	        	insertionStrings[1] = Integer.toString(Constants.MAX_LENGTH_ORDER_SCHEDULE_CC_EMAIL_VALUE);
	        	String errorMess = ClwI18nUtil.getMessage(request,"global.error.maximumLengthExceeded", insertionStrings); 
				errors.add("error", new ActionError("error.simpleGenericError",	errorMess));
		    }
		}
	    pForm.setCcEmail(ccEmail);
		
		//check that valid recurrence information has been specified
		String recurrence = pForm.getScheduleType();
		if (!Utility.isSet(recurrence)) {
			String errorMess = ClwI18nUtil.getMessage(request,"orders.schedule.error.noRecurrence");
			errors.add("error", new ActionError("error.simpleGenericError",	errorMess));
		}
		else {
			if (RefCodeNames.ORDER_SCHEDULE_RULE_CD.WEEK.equals(recurrence)) {
				String interval = pForm.getWeekCycle();
				try {
					Integer weekInterval = Integer.valueOf(interval);
					if (weekInterval.intValue() <= 0) {
						String errorMess = ClwI18nUtil.getMessage(request,"orders.schedule.error.invalidRecurrenceInterval");
						errors.add("error", new ActionError("error.simpleGenericError",	errorMess));
					}
				}
				catch (Exception e) {
					String errorMess = ClwI18nUtil.getMessage(request,"orders.schedule.error.invalidRecurrenceInterval");
					errors.add("error", new ActionError("error.simpleGenericError",	errorMess));
				}
				int[]days = pForm.getWeekDays();
				boolean invalidDay = false;
				for (int i=0; i<days.length && !invalidDay; i++) {
					invalidDay = (days[i] < Calendar.SUNDAY || days[i] > Calendar.SATURDAY);
				}
				if (invalidDay) {
					String errorMess = ClwI18nUtil.getMessage(request,"orders.schedule.error.invalidWeekdays");
					errors.add("error", new ActionError("error.simpleGenericError",	errorMess));
				}
			}
			else if (RefCodeNames.ORDER_SCHEDULE_RULE_CD.DAY_MONTH.equals(recurrence)) {
				String interval = pForm.getMonthDayCycle();
				try {
					Integer monthInterval = Integer.valueOf(interval);
					if (monthInterval.intValue() <= 0) {
						String errorMess = ClwI18nUtil.getMessage(request,"orders.schedule.error.invalidRecurrenceInterval");
						errors.add("error", new ActionError("error.simpleGenericError",	errorMess));
					}
				}
				catch (Exception e) {
					String errorMess = ClwI18nUtil.getMessage(request,"orders.schedule.error.invalidRecurrenceInterval");
					errors.add("error", new ActionError("error.simpleGenericError",	errorMess));
				}
				int[]days = pForm.getMonthDays();
				boolean invalidDay = false;
				for (int i=0; i<days.length && !invalidDay; i++) {
					invalidDay = (days[i] < Constants.MONTH_DAY_MIN || 
							(days[i] > Constants.MONTH_DAY_MAX && days[i] != Constants.MONTH_DAY_LAST));
				}
				if (invalidDay) {
					String errorMess = ClwI18nUtil.getMessage(request,"orders.schedule.error.invalidMonthdays");
					errors.add("error", new ActionError("error.simpleGenericError",	errorMess));
				}
			}
			else if (RefCodeNames.ORDER_SCHEDULE_RULE_CD.DATE_LIST.equals(recurrence)) {
				//nothing to check here
			}
			else {
				String errorMess = ClwI18nUtil.getMessage(request,"orders.schedule.error.invalidRecurrence");
				errors.add("error", new ActionError("error.simpleGenericError",	errorMess));
			}
		}
		
		//check that additional days information, if specified, is valid
		//if the current value equals the default value, blank it out
		if (ClwI18nUtil.getUIDateFormat(request).toLowerCase().equals(pForm.getAlsoDates())) {
			pForm.setAlsoDates(StringUtils.EMPTY);
		}
		String alsoDates = pForm.getAlsoDates();
		String[] alsoDatesArray = null;
		if (Utility.isSet(alsoDates)) {
			StringBuilder alsoDatesBuilder = new StringBuilder();
			boolean includeSeparator = false;
			boolean badDateFound = false;
			alsoDatesArray = alsoDates.split(Constants.MULTIPLE_DATE_SEPARATOR);
			for (int i=0; i<alsoDatesArray.length; i++) {
				String date = Utility.safeTrim(alsoDatesArray[i]);
				if (Utility.isSet(date)) {
					if (createCalendar(request, date) == null) {
						Object[] params = new Object[1];
						params[0] = date;
						String errorMess = ClwI18nUtil.getMessage(request,"orders.schedule.error.invalidAlsoDate", params);
						errors.add("error", new ActionError("error.simpleGenericError",	errorMess));
						badDateFound = true;
					}
					else {
						if (includeSeparator) {
							alsoDatesBuilder.append(Constants.MULTIPLE_DATE_SEPARATOR);
						}
						includeSeparator = true;
						alsoDatesBuilder.append(date);
					}
				}
			}
			if (!badDateFound) {
				pForm.setAlsoDates(alsoDatesBuilder.toString());
			}
		}

		//check that deletion days information, if specified, is valid
		//if the current value equals the default value, blank it out

		if (ClwI18nUtil.getUIDateFormat(request).toLowerCase().equals(pForm.getExcludeDates())) {
			pForm.setExcludeDates(StringUtils.EMPTY);
		}
		String exclusionDates = pForm.getExcludeDates();
		String[] exclusionDatesArray = null;
		if (Utility.isSet(exclusionDates)) {
			StringBuilder exclusionDatesBuilder = new StringBuilder();
			boolean includeSeparator = false;
			boolean badDateFound = false;
			exclusionDatesArray = exclusionDates.split(Constants.MULTIPLE_DATE_SEPARATOR);
			for (int i=0; i<exclusionDatesArray.length; i++) {
				String date = Utility.safeTrim(exclusionDatesArray[i]);
				if (Utility.isSet(date)) {
					if (createCalendar(request, date) == null) {
						Object[] params = new Object[1];
						params[0] = date;
						String errorMess = ClwI18nUtil.getMessage(request,"orders.schedule.error.invalidDeletionDate", params);
						errors.add("error", new ActionError("error.simpleGenericError",	errorMess));
						badDateFound = true;
					}
					else {
						if (includeSeparator) {
							exclusionDatesBuilder.append(Constants.MULTIPLE_DATE_SEPARATOR);
						}
						includeSeparator = true;
						exclusionDatesBuilder.append(date);
					}
				}
			}
			if (!badDateFound) {
				pForm.setExcludeDates(exclusionDatesBuilder.toString());
			}
		}
		
		//if we've encountered any errors, return them since additional error checking relies
		//on the checks above succeeding
		if (!errors.isEmpty()) {
			return errors;
		}
		
		//if the schedule will not result in any orders, return an error
		errors.add(showDates(request, pForm));
		if (!Utility.isSet(pForm.getCalendarDatesWithOrders())) {
			String errorMess = ClwI18nUtil.getMessage(request,"orders.schedule.error.noOrdersGenerated");
			errors.add("error", new ActionError("error.simpleGenericError",	errorMess));
		}
	    
	    //if the end date of the schedule is before the start date, return an error
	    GregorianCalendar gcStartDate = new GregorianCalendar();
	    gcStartDate.setTime(startDate);
	    GregorianCalendar gcEndDate = new GregorianCalendar();
	    gcEndDate.setTime(endDate);
		if (gcEndDate.before(gcStartDate)) {
            Object[] params = new Object[2];
            params[0] = pForm.getEndDate();
            params[1] = pForm.getStartDate();
            String errorMess = ClwI18nUtil.getMessage(request,"orders.schedule.error.endDateBeforeStartDate",params);
			errors.add("error", new ActionError("error.simpleGenericError",	errorMess));
		}
		
		//if any of the additional days are outside the range of start date to end date, return an error
		if (Utility.isSet(alsoDatesArray)) {
			StringBuilder invalidDates = new StringBuilder(50);
			boolean includeComma = false;
			for (int i=0; i<alsoDatesArray.length; i++) {
				String dateString = Utility.strNN(alsoDatesArray[i]).trim();
				if (Utility.isSet(dateString)) {
					GregorianCalendar alsoDate = createCalendar(request, dateString);
					if (alsoDate.before(gcStartDate) || alsoDate.after(gcEndDate)) {
						if (includeComma) {
							invalidDates.append(", ");
						}
						invalidDates.append(alsoDatesArray[i]);
						includeComma = true;
					}
				}
			}
			if (invalidDates.length() > 0) {
                Object[] params = new Object[3];
                params[0] = pForm.getStartDate();
                params[1] = pForm.getEndDate();
                params[2] = invalidDates.toString();
                String errorMess = ClwI18nUtil.getMessage(request,"orders.schedule.error.additionalOrderDatesOutOfRange", params);
                errors.add("error", new ActionError("error.simpleGenericError", errorMess));
			}
		}
		
		//if any of the exclusion days are outside the range of start date to end date, return an error
		if (Utility.isSet(exclusionDatesArray)) {
			StringBuilder invalidDates = new StringBuilder(50);
			boolean includeComma = false;
			for (int i=0; i<exclusionDatesArray.length; i++) {
				String dateString = Utility.strNN(exclusionDatesArray[i]).trim();
				if (Utility.isSet(dateString)) {
					GregorianCalendar exclusionDate = createCalendar(request, dateString);
					if (exclusionDate.before(gcStartDate) || exclusionDate.after(gcEndDate)) {
						if (includeComma) {
							invalidDates.append(", ");
						}
						invalidDates.append(exclusionDatesArray[i]);
						includeComma = true;
					}
				}
			}
			if (invalidDates.length() > 0) {
                Object[] params = new Object[3];
                params[0] = pForm.getStartDate();
                params[1] = pForm.getEndDate();
                params[2] = invalidDates.toString();
                String errorMess = ClwI18nUtil.getMessage(request,"orders.schedule.error.excludeOrderDatesOutOfRange", params);
                errors.add("error", new ActionError("error.simpleGenericError", errorMess));
			}
		}

		//for certain user types, ensure contact information has been specified
		UserData user = ShopTool.getCurrentUser(request).getUser();
		if (RefCodeNames.USER_TYPE_CD.CUSTOMER_SERVICE.equals(user.getUserTypeCd())	|| 
				RefCodeNames.USER_TYPE_CD.CRC_MANAGER.equals(user.getUserTypeCd())) {
			if (!Utility.isSet(pForm.getContactName())) {
				String errorMess = ClwI18nUtil.getMessage(request, "orders.schedule.error.contactNameRequired");
				errors.add("error", new ActionError("error.simpleGenericError",	errorMess));
			}

			if (!Utility.isSet(pForm.getContactPhone())) {
				String errorMess = ClwI18nUtil.getMessage(request, "orders.schedule.error.contactPhoneRequired");
				errors.add("error", new ActionError("error.simpleGenericError",	errorMess));
			}
		}
		
		//ensure this schedule does not overlap with any other schedules
		OrderScheduleViewVector existingSchedules = null;
	    try {
			int siteId = ShopTool.getCurrentUser(request).getSite().getBusEntity().getBusEntityId();
			int userId = ShopTool.getCurrentUser(request).getUserId();
	    	existingSchedules = APIAccess.getAPIAccess().getAutoOrderAPI().getOrderSchedules(siteId, userId);
	    } catch (Exception exc) {
            String errorMess = ClwI18nUtil.getMessage(request, "orders.schedule.error.unexpectedExistingSchedulesRetrievalError");
            errors.add("error", new ActionError("error.simpleGenericError", errorMess));
            return errors;
		}
		long thisScheduleStartTime = gcStartDate.getTime().getTime();
		long thisScheduleEndTime = 0;
		if (gcEndDate.getTime() != null) {
			thisScheduleEndTime = gcEndDate.getTime().getTime();
		}
		for (int ii = 0; ii < existingSchedules.size(); ii++) {
			OrderScheduleView existingSchedule = (OrderScheduleView) existingSchedules.get(ii);
			if (pForm.getOrderScheduleId() != existingSchedule.getOrderScheduleId()
					&& pForm.getOrderGuideId() == existingSchedule.getOrderGuideId()) {
				long existingScheduleStartTime = existingSchedule.getEffDate().getTime();
				long existingScheduleEndTime = 0;
				if (existingSchedule.getExpDate() != null) {
					existingScheduleEndTime = existingSchedule.getExpDate().getTime();
				}
				if ((existingScheduleEndTime == 0 || existingScheduleEndTime >= thisScheduleStartTime)
						&& (existingScheduleStartTime <= thisScheduleEndTime || thisScheduleEndTime == 0)) {
					Object[] params = new Object[1];
					params[0] = ClwI18nUtil.formatOrderScheduleName(request, existingSchedule, " ");
					String errorMess = ClwI18nUtil.getMessage(request, "orders.schedule.error.scheduleOverlapsExistingSchedule", params);
					errors.add("error", new ActionError("error.simpleGenericError", errorMess));
				}
			}
		}

		return errors;	
	}
	
	public static ActionErrors saveSchedule(HttpServletRequest request,
			OrderSchedulerForm pForm, boolean determineOrderScheduleRuleCd) {
		ActionErrors ae = new ActionErrors();
		ae = showDates(request, pForm);
		if (ae.size() > 0) {
			return ae;
		}
		//Save schedule
		HttpSession session = request.getSession();
		CleanwiseUser appUser = (CleanwiseUser) session
				.getAttribute(Constants.APP_USER);
		if (appUser == null) {
			ae.add("error", new ActionError("error.systemError", "No "
					+ Constants.APP_USER + "session object found"));
			return ae;
		}
		UserData user = appUser.getUser();
		SiteData site = appUser.getSite();
		int siteId = site.getBusEntity().getBusEntityId();

		APIAccess factory = (APIAccess) session
				.getAttribute(Constants.APIACCESS);
		if (factory == null) {
			ae.add("error", new ActionError("error.systemError",
					"No Ejb access"));
			return ae;
		}
		AutoOrder autoOrderEjb = null;
		try {
			autoOrderEjb = factory.getAutoOrderAPI();
		} catch (com.cleanwise.service.api.APIServiceAccessException exc) {
			exc.printStackTrace();
			ae.add("error", new ActionError("error.systemError",
					"No order Ejb pointer"));
			return ae;
		}
		OrderScheduleJoin orderScheduleJ = OrderScheduleJoin.createValue();
		orderScheduleJ.setOrderScheduleId(pForm.getOrderScheduleId());

		GregorianCalendar gc = null;
		GregorianCalendar gCStartDate = null;
		String startDateS = pForm.getStartDate();
		try {
			Date sd = ClwI18nUtil.parseDateInp(request, startDateS);
			gc = new GregorianCalendar();
			gc.setTime(sd);
			
			gCStartDate = new GregorianCalendar();
			gCStartDate.setTime(sd);			
		} catch (Exception exc) {
		}
		if (gc == null) {
			Object[] params = new Object[1];
			params[0] = startDateS;
			String errorMess = ClwI18nUtil.getMessage(request,
					"shop.errors.wrongStartDateFormat", params);
			ae.add("error", new ActionError("error.simpleGenericError",
					errorMess));
			return ae;
		}

		orderScheduleJ.setEffDate(gc.getTime());
		GregorianCalendar gCEndDate = null;
		String endDateS = pForm.getEndDate();
		if (endDateS != null && endDateS.trim().length() > 0) {
			gc = createCalendar(request, endDateS);
			gCEndDate = new GregorianCalendar();
			Date ed = null;
			try {
				ed = ClwI18nUtil.parseDateInp(request, endDateS);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			gCEndDate.setTime(ed);
			if (gc == null) {
				Object[] params = new Object[1];
				params[0] = endDateS;
				String errorMess = ClwI18nUtil.getMessage(request,
						"shop.errors.wrongEndDateFormat", params);
				ae.add("error", new ActionError("error.simpleGenericError",
						errorMess));
				return ae;
			}
			
			if (gc.before(gCStartDate)) {
                Object[] params = new Object[2];
                params[0] = "" + endDateS;
                params[1] = "" + startDateS;
                String errorMess =
                    ClwI18nUtil.getMessage(request,"shop.errors.endDateBeforeStartDate",params);
                ae.add("error",new ActionError("error.simpleGenericError",errorMess));
                log.info(errorMess);
                return ae;				
			}
			
			orderScheduleJ.setExpDate(gc.getTime());
		}

	    OrderGuide orderGuideEjb = null;
	    String emsg = "";
	    try {
	         emsg = "No OrderGuide API Access";
	         orderGuideEjb = factory.getOrderGuideAPI();
	    } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
	         ae.add("error", new ActionError("error.systemError", emsg));
	         return ae;
	    }	    
		
	    OrderGuideStructureDataVector orderGuideItems = null;
	    try {
			orderGuideItems = orderGuideEjb.getOrderGuideStructure(pForm.getOrderGuideId());
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (DataNotFoundException e) {
			e.printStackTrace();
		}
	    Iterator itr = orderGuideItems.iterator();
	    boolean emptyOG = true;
	    while (itr.hasNext()) {
	    	OrderGuideStructureData item = (OrderGuideStructureData) itr.next();
	        if (item.getQuantity() > 0) {
	        	emptyOG = false; 
                break;
	        }
	    }
		
		orderScheduleJ.setOrderGuideId(pForm.getOrderGuideId());
		orderScheduleJ.setCcEmail(pForm.getCcEmail());
		orderScheduleJ.setOrderScheduleCd(pForm.getScheduleAction());
		
		//set/determine the schedule rule (weekly, monthly, etc).
		if (determineOrderScheduleRuleCd || !Utility.isSet(pForm.getScheduleType())) {
			if (pForm.getWeekDays().length > 0) {
				orderScheduleJ.setOrderScheduleRuleCd(RefCodeNames.ORDER_SCHEDULE_RULE_CD.WEEK);
			} else if (pForm.getMonthDays().length > 0) {
				orderScheduleJ.setOrderScheduleRuleCd(RefCodeNames.ORDER_SCHEDULE_RULE_CD.DAY_MONTH);
			} else if (pForm.getMonthMonths().length > 0) {
				orderScheduleJ.setOrderScheduleRuleCd(RefCodeNames.ORDER_SCHEDULE_RULE_CD.WEEK_MONTH);
			} else {
				orderScheduleJ.setOrderScheduleRuleCd(RefCodeNames.ORDER_SCHEDULE_RULE_CD.DATE_LIST);
			}
		}
		else {
			orderScheduleJ.setOrderScheduleRuleCd(pForm.getScheduleType());
		}

		//depending upon the schedule rule, set the additional data that is required for that rule
		if (RefCodeNames.ORDER_SCHEDULE_RULE_CD.WEEK.equals(orderScheduleJ.getOrderScheduleRuleCd())) {
			orderScheduleJ.setCycle(Integer.parseInt(pForm.getWeekCycle()));
			orderScheduleJ.setElements(pForm.getWeekDays());
		} else if (RefCodeNames.ORDER_SCHEDULE_RULE_CD.DAY_MONTH.equals(orderScheduleJ.getOrderScheduleRuleCd())) {
			orderScheduleJ.setCycle(Integer.parseInt(pForm.getMonthDayCycle()));
			orderScheduleJ.setElements(pForm.getMonthDays());
		} else if (RefCodeNames.ORDER_SCHEDULE_RULE_CD.WEEK_MONTH.equals(orderScheduleJ.getOrderScheduleRuleCd())) {
			orderScheduleJ.setElements(pForm.getMonthMonths());
			orderScheduleJ.setMonthWeeks(pForm.getMonthWeeks());
			orderScheduleJ.setMonthWeekDay(pForm.getMonthWeekDay());
		}

		//Additional days
		//if the current value equals the default value, blank it out
		if (ClwI18nUtil.getUIDateFormat(request).toLowerCase().equals(pForm.getAlsoDates())) {
			pForm.setAlsoDates(StringUtils.EMPTY);
		}
		String alsoDates = pForm.getAlsoDates();
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
					String errorMess = ClwI18nUtil.getMessage(request,
							"shop.errors.wrongAlsoDateFormat", params);
					ae.add("error", new ActionError("error.simpleGenericError",
							errorMess));
					return ae;
				}
				if (gc.before(gCStartDate) || gc.after(gCEndDate)) {
	                Object[] params = new Object[2];
	                params[0] = "" + startDateS;
	                params[1] = "" + endDateS;
	                String errorMess =
	                    ClwI18nUtil.getMessage(request,"shop.errors.additionalOrderDateOutOfRange", params);
	                ae.add("error", new ActionError("error.simpleGenericError", errorMess));
	                log.info(errorMess);
	                return ae;				
				}
			    if (emptyOG) {
					String errorMess = ClwI18nUtil
					.getMessage(
						request,
						"shop.errors.noItemsWithQuantitySet",
						null);
			        ae.add("error", new ActionError("error.simpleGenericError",
					    errorMess));
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
		//if the current value equals the default value, blank it out
		if (ClwI18nUtil.getUIDateFormat(request).toLowerCase().equals(pForm.getExcludeDates())) {
			pForm.setExcludeDates(StringUtils.EMPTY);
		}
		String excludeDates = pForm.getExcludeDates();
		List excludeDatesL = new ArrayList();
		if (excludeDates != null && excludeDates.trim().length() > 0) {
			int commaInd = 0;
			while (commaInd >= 0) {
				int nextCommaInd = excludeDates.indexOf(',', commaInd);
				String dateS = null;
				if (nextCommaInd > 0) {
					dateS = excludeDates.substring(commaInd, nextCommaInd)
							.trim();
					commaInd = nextCommaInd + 1;
				} else {
					dateS = excludeDates.substring(commaInd).trim();
					commaInd = -1;
				}
				gc = createCalendar(request, dateS);
				if (gc == null) {
					Object[] params = new Object[1];
					params[0] = dateS;
					String errorMess = ClwI18nUtil.getMessage(request,
							"shop.errors.wrongNotPlaceDateFormat", params);
					ae.add("error", new ActionError("error.simpleGenericError",
							errorMess));
					return ae;
				}
				if (gc.before(gCStartDate) || gc.after(gCEndDate)) {
	                Object[] params = new Object[2];
	                params[0] = "" + startDateS;
	                params[1] = "" + endDateS;
	                String errorMess =
	                    ClwI18nUtil.getMessage(request,"shop.errors.deleteOrderDateOutOfRange", params);
	                ae.add("error", new ActionError("error.simpleGenericError", errorMess));
	                log.info(errorMess);
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
		if (user.getUserTypeCd().equals(
				RefCodeNames.USER_TYPE_CD.CUSTOMER_SERVICE)
				|| user.getUserTypeCd().equals(
						RefCodeNames.USER_TYPE_CD.CRC_MANAGER)) {
			if (pForm.getContactName() == null
					|| pForm.getContactName().trim().length() == 0) {
				String errorMess = ClwI18nUtil
						.getMessage(
								request,
								"shop.errors.fieldContactNameRequiresInformation",
								null);
				ae.add("error", new ActionError("error.simpleGenericError",
						errorMess));
				return ae;
			} else {
				orderScheduleJ.setContactName(pForm.getContactName());
			}

			if (pForm.getContactPhone() == null
					|| pForm.getContactPhone().trim().length() == 0) {
				String errorMess = ClwI18nUtil.getMessage(request,
						"shop.errors.fieldContactPhoneRequiresInformation",
						null);
				ae.add("error", new ActionError("error.simpleGenericError",
						errorMess));
				return ae;
			} else {
				orderScheduleJ.setContactPhone(pForm.getContactPhone());
			}

			orderScheduleJ.setContactEmail(pForm.getContactEmail());

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
		List schedules = pForm.getOrderSchedules();
		for (int ii = 0; ii < schedules.size(); ii++) {
			OrderScheduleView osv = (OrderScheduleView) schedules.get(ii);
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
					params[0] = pForm.getOrderScheduleName(ii);
					String errorMess = ClwI18nUtil
						.getMessage(
						    request,
							    "shop.errors.scheduleItersectsWithExistingSchedule",
								params);
					ae.add("error", new ActionError("error.simpleGenericError",
					    errorMess));
					return ae;
				}
			}
		}
		orderScheduleJ.setSiteId(siteId);
		orderScheduleJ.setSiteName(site.getBusEntity().getShortDesc());
		orderScheduleJ.setUserId(user.getUserId());

		try {
			int id = autoOrderEjb.saveOrderSchedule(orderScheduleJ, user
					.getUserName());
			orderScheduleJ.setOrderScheduleId(id);
			pForm.setOrderScheduleId(id);
		} catch (RemoteException exc) {
			String errorMess = ClwI18nUtil.formatEjbError(request, exc
					.getMessage());
			if (errorMess != null && errorMess.trim().length() > 0) {
				ae.add("error", new ActionError("error.simpleGenericError",
						errorMess));
			} else {
				ae.add("error", new ActionError("error.systemError", exc
						.getMessage()));
			}
			return ae;
		}

		List orderGuides = pForm.getTemplateOrderGuides();
		if (orderGuides == null) {
			orderGuides = new OrderGuideDataVector();
		}
		int ii = 0;
		for (; ii < orderGuides.size(); ii++) {
			OrderGuideData ogD = (OrderGuideData) orderGuides.get(ii);
			if (orderGuideId == ogD.getOrderGuideId()) {
				orderScheduleJ.setOrderGuideName(ogD.getShortDesc());
				break;
			}
		}
		if (ii == orderGuides.size()) {
			orderGuides = pForm.getUserOrderGuides();
			if (orderGuides == null) {
				orderGuides = new OrderGuideDataVector();
			}
			for (int jj = 0; jj < orderGuides.size(); jj++) {
				OrderGuideData ogD = (OrderGuideData) orderGuides.get(jj);
				if (orderGuideId == ogD.getOrderGuideId()) {
					orderScheduleJ.setOrderGuideName(ogD.getShortDesc());
					break;
				}
			}
		}

		//Update in list
		OrderScheduleView orderScheduleV = OrderScheduleView.createValue();
		orderScheduleV.setOrderScheduleId(orderScheduleJ.getOrderScheduleId());
		orderScheduleV.setOrderGuideId(orderScheduleJ.getOrderGuideId());
		orderScheduleV.setOrderScheduleRuleCd(orderScheduleJ
				.getOrderScheduleRuleCd());
		orderScheduleV.setOrderScheduleCd(orderScheduleJ.getOrderScheduleCd());
		orderScheduleV.setOrderGuideName(orderScheduleJ.getOrderGuideName());
		orderScheduleV.setExpDate(orderScheduleJ.getExpDate());
		orderScheduleV.setEffDate(orderScheduleJ.getEffDate());
		orderScheduleV.setSiteId(orderScheduleJ.getSiteId());
		orderScheduleV.setSiteName(orderScheduleJ.getSiteName());
		orderScheduleV.setUserId(orderScheduleJ.getUserId());
		OrderScheduleViewVector orderSchedules = pForm.getOrderSchedules();
		if (orderSchedules == null) {
			orderSchedules = new OrderScheduleViewVector();
		}
		ii = 0;
		for (; ii < orderSchedules.size(); ii++) {
			OrderScheduleView osv = (OrderScheduleView) orderSchedules.get(ii);
			if (osv.getOrderScheduleId() == oldId) {
				orderSchedules.remove(ii);
				orderSchedules.add(ii, orderScheduleV);
				break;
			}
		}
		if (ii == orderSchedules.size()) {
			orderSchedules.add(orderScheduleV);
		}
		pForm.setOrderSchedules(orderSchedules);
		orderSchedules(pForm);
		pForm.resetScheduleYear();
		return ae;
	}
	
	public static ActionErrors prepareSchedule(HttpServletRequest request,
			OrderSchedulerForm pForm) {
		ActionErrors ae = new ActionErrors();
		int scheduleId = pForm.getOrderScheduleId();
		if (scheduleId != 0) {
			OrderScheduleViewVector schedules = pForm.getOrderSchedules();
			int ii = 0;
			OrderScheduleView osv = null;
			for (; ii < schedules.size(); ii++) {
				osv = (OrderScheduleView) schedules.get(ii);
				if (osv.getOrderScheduleId() == scheduleId) {
					break;
				}
			}
			if (ii == schedules.size()) {
				String errorMess = ClwI18nUtil.getMessage(request,
						"shop.errors.scheduleNotFoundProbablyOldPageWasUsed",
						null);
				ae.add("error", new ActionError("error.simpleGenericError",
						errorMess));
				initFields(request, pForm);
				return ae;
			}
			pForm.setOrderGuideId(osv.getOrderGuideId());
			pForm.setStartDate(ClwI18nUtil.formatDateInp(request, osv
					.getEffDate()));
			Date endDate = osv.getExpDate();
			if (endDate == null) {
				pForm.setEndDate("");
			} else {
				pForm.setEndDate(ClwI18nUtil.formatDateInp(request, osv
						.getExpDate()));
			}
			pForm.setScheduleAction(osv.getOrderScheduleCd());
			HttpSession session = request.getSession();
			APIAccess factory = (APIAccess) session
					.getAttribute(Constants.APIACCESS);
			if (factory == null) {
				ae.add("error", new ActionError("error.systemError",
						"No Ejb access"));
				return ae;
			}
			AutoOrder autoOrderEjb = null;
			try {
				autoOrderEjb = factory.getAutoOrderAPI();
			} catch (com.cleanwise.service.api.APIServiceAccessException exc) {
				exc.printStackTrace();
				ae.add("error", new ActionError("error.systemError",
						"No auto order Ejb pointer"));
				return ae;
			}
			OrderScheduleJoin orderScheduleJ = null;
			try {
				orderScheduleJ = autoOrderEjb.getOrderSchedule(osv
						.getOrderScheduleId());
			} catch (Exception exc) {
				exc.printStackTrace();
				ae.add("error", new ActionError("error.systemError", exc
						.getMessage()));
				return ae;
			}
			pForm.setCcEmail(orderScheduleJ.getCcEmail());
			pForm.setScheduleType(orderScheduleJ.getOrderScheduleRuleCd());
			if (RefCodeNames.ORDER_SCHEDULE_RULE_CD.WEEK.equals(orderScheduleJ
					.getOrderScheduleRuleCd())) {
				pForm.setWeekCycle("" + orderScheduleJ.getCycle());
				pForm.setWeekDays(orderScheduleJ.getElements());
			} else {
				pForm.setWeekCycle("1");
				pForm.setWeekDays(new int[0]);
			}
			if (RefCodeNames.ORDER_SCHEDULE_RULE_CD.DAY_MONTH
					.equals(orderScheduleJ.getOrderScheduleRuleCd())) {
				pForm.setMonthDayCycle("" + orderScheduleJ.getCycle());
				pForm.setMonthDays(orderScheduleJ.getElements());
			} else {
				pForm.setMonthDayCycle("1");
				pForm.setMonthDays(new int[0]);
			}
			if (RefCodeNames.ORDER_SCHEDULE_RULE_CD.WEEK_MONTH
					.equals(orderScheduleJ.getOrderScheduleRuleCd())) {
				pForm.setMonthWeekDay(orderScheduleJ.getMonthWeekDay());
				pForm.setMonthMonths(orderScheduleJ.getElements());
				pForm.setMonthWeeks(orderScheduleJ.getMonthWeeks());

			} else {
				pForm.setMonthWeekDay(2);
				pForm.setMonthMonths(new int[0]);
				pForm.setMonthWeeks(1);

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
			pForm.setAlsoDates(alsoS);
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
			pForm.setExcludeDates(exceptS);

			String contactName = orderScheduleJ.getContactName();
			String contactPhone = orderScheduleJ.getContactPhone();
			String contactEmail = orderScheduleJ.getContactEmail();

			if (contactName != null) {
				pForm.setContactName(contactName);
			}

			if (contactPhone != null) {
				pForm.setContactPhone(contactPhone);
			}

			if (contactEmail != null) {
				pForm.setContactEmail(contactEmail);
			}

		} else { //No schedule selected
			initFields(request, pForm);
		}

		// show on calendar
		ae = showDates(request, pForm);
		if (ae.size() > 0) {
			return ae;
		}
		orderSchedules(pForm);
		return ae;
	}

	//--------------------------------------------------------------------------------------
	private static void orderSchedules(OrderSchedulerForm pForm) {
		OrderScheduleViewVector scheduleL = pForm.getOrderSchedules();
		Object[] schedules = scheduleL.toArray();
		for (int ii = 0; ii < schedules.length - 1; ii++) {
			boolean exitFlag = true;
			for (int jj = 1; jj < schedules.length - ii; jj++) {
				OrderScheduleView sched1 = (OrderScheduleView) schedules[jj - 1];
				OrderScheduleView sched2 = (OrderScheduleView) schedules[jj];
				String ss1 = sched1.getOrderGuideName();
				String ss2 = sched2.getOrderGuideName();
				int comp = ss1.compareToIgnoreCase(ss2);
				if (comp > 0) {
					schedules[jj - 1] = sched2;
					schedules[jj] = sched1;
					exitFlag = false;
				} else if (comp == 0) {
					Date start1 = sched1.getEffDate();
					Date start2 = sched2.getEffDate();
					if (start1.after(start2)) {
						schedules[jj - 1] = sched2;
						schedules[jj] = sched1;
						exitFlag = false;
					}
				}
			}
		}
		scheduleL = new OrderScheduleViewVector();
		for (int ii = 0; ii < schedules.length; ii++) {
			scheduleL.add(schedules[ii]);
		}
		pForm.setOrderSchedules(scheduleL);
	}

	/**
	 * Method to calculate the order cut dates for a week-based order schedule.
	 * @param scheduleStart - a <code>GregorianCalendar</code> representing the schedule start date
	 * @param weekDays - an integer array containing the selected days of the week
	 * @param cycle - an integer containing the number of weeks in the order cycle
	 * @return a <code>List</code> of <code>GregorianCalendar</code> objects containing the
	 * 		dates on which orders will be cut for the schedule.
	 */
	public static List<GregorianCalendar> calculateWeeklyOrders(GregorianCalendar scheduleStart,
			int[] weekDays, int cycle) {

		ArrayList<GregorianCalendar> datesToShow = new ArrayList<GregorianCalendar>();
		GregorianCalendar thisCal = (GregorianCalendar) scheduleStart.clone();
		//determine order dates one year out from the schedule start date
		GregorianCalendar endCal = (GregorianCalendar) scheduleStart.clone();
		endCal.add(GregorianCalendar.YEAR, 1);
		Date calculationEndDate = endCal.getTime();
		//determine the order dates
		while (!thisCal.getTime().after(calculationEndDate)) {
			//iterate over the next 7 days (current week)
			for (int count=GregorianCalendar.SUNDAY; count<=GregorianCalendar.SATURDAY; count++) {
				//if the current day of the week is one of the selected days add it to the list
				for (int i=0; i<weekDays.length; i++) {
					int selectedDay = weekDays[i];
					if (selectedDay == (thisCal.get(GregorianCalendar.DAY_OF_WEEK))) {
						datesToShow.add((GregorianCalendar)thisCal.clone());
					}
				}
				thisCal.add(GregorianCalendar.DAY_OF_YEAR, 1);
			}
			//now that we've gone through the current week, increment the calendar to reflect
			//the number of weeks in the cycle.  For example, a cycle of one week means no 
			//increment is required (since we'll just start on the next week), a cycle of two 
			//weeks means a one week increment (so we skip the upcoming week and start again in 
			//the following week), etc.
			int cycleIncrement = 7*(cycle-1);
			thisCal.add(GregorianCalendar.DAY_OF_YEAR, cycleIncrement);
		}

		return datesToShow;
	}

	/**
	 * Method to calculate the order cut dates for a month-based order schedule.
	 * @param scheduleStart - a <code>GregorianCalendar</code> representing the schedule start date
	 * @param monthDays - an integer array containing the selected days of the month
	 * @param cycle - an integer containing the number of months in the order cycle
	 * @return a <code>List</code> of <code>GregorianCalendar</code> objects containing the
	 * 		dates on which orders will be cut for the schedule.
	 */
	public static List<GregorianCalendar> calculateMonthlyOrders(GregorianCalendar scheduleStart,
			int[] monthDays, int cycle) {

		ArrayList<GregorianCalendar> datesToShow = new ArrayList<GregorianCalendar>();
		GregorianCalendar thisCal = (GregorianCalendar) scheduleStart.clone();
		//determine order dates one year out from the schedule start date
		GregorianCalendar endCal = (GregorianCalendar) scheduleStart.clone();
		endCal.add(GregorianCalendar.YEAR, 1);
		Date calculationEndDate = endCal.getTime();
		//determine the order dates
		while (!thisCal.getTime().after(calculationEndDate)) {
			//iterate over the days in the current month
			int currentDayOfMonth = thisCal.get(GregorianCalendar.DAY_OF_MONTH);
			int maxDaysInMonth = thisCal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
			for (int count=currentDayOfMonth; count<=maxDaysInMonth; count++) {
				for (int i=0; i<monthDays.length; i++) {
					int selectedDay = monthDays[i];
					if ((selectedDay == Constants.MONTH_DAY_LAST) &&
							(thisCal.get(GregorianCalendar.DAY_OF_MONTH) ==
								thisCal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH))) { 
						// add the last day of the month.
						datesToShow.add((GregorianCalendar)thisCal.clone());
					} else if (selectedDay == thisCal.get(GregorianCalendar.DAY_OF_MONTH)) {
						datesToShow.add((GregorianCalendar)thisCal.clone());
					}
				}
				thisCal.add(GregorianCalendar.DAY_OF_YEAR, 1);
			}
			//now that we've reached the end of the current month, increment the calendar to 
			//reflect the number of months in the cycle.  For example, a cycle of one month means 
			//no increment is required (since we'll just start on the next month), a cycle of two 
			//months means a one month increment (so we skip the upcoming month and start again in 
			//the following month), etc.
			int cycleIncrement = cycle-1;
			thisCal.add(GregorianCalendar.MONTH, cycleIncrement);
			//add a day so we start the next loop on the first day of the next month
			thisCal.add(GregorianCalendar.DAY_OF_YEAR, 1);
		}

		return datesToShow;
	}

	public static List calculateCustomOrders(GregorianCalendar scheduleStart,
			int monthWeekDay, int monthWeek, int[] monthMonths) {

		ArrayList datesToShow = new ArrayList();
		//STJ - 3865
		int[] noOfDays = new int[]{31,28,31,30,31,30,31,31,30,31,30,31};
		
		for (int i = 0; i < monthMonths.length; i++) {
			int thisMonth = monthMonths[i];

			GregorianCalendar thiscal = new GregorianCalendar(scheduleStart
					.get(GregorianCalendar.YEAR), thisMonth, 1);

			GregorianCalendar lastdayMatch = null;
			boolean matchfound = false;

			int dayidx = 0;
			
			int leafYear = thiscal.get(GregorianCalendar.YEAR)%4;
			int daysInMonth = noOfDays[thisMonth];
			if(thisMonth==1 && leafYear==0){
				daysInMonth = 29;
			}
			
			while (dayidx++ < daysInMonth) {

				int thisdayofweek = thiscal.get(GregorianCalendar.DAY_OF_WEEK);
				int dayOfWeekInMonth = thiscal.get(GregorianCalendar.DAY_OF_WEEK_IN_MONTH);
				
				if (dayOfWeekInMonth == monthWeek && thisdayofweek == monthWeekDay) {

					datesToShow.add(thiscal.clone());
					// done with this one
					matchfound = true;
					break;
				}

				if (dayOfWeekInMonth > monthWeek && thisdayofweek == monthWeekDay) {
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
			if (matchfound == false && null != lastdayMatch) {
				datesToShow.add(lastdayMatch.clone());
			}
		}

		return datesToShow;

	}

	private static ActionErrors showDates(HttpServletRequest request,
			OrderSchedulerForm pForm) {
		ActionErrors ae = new ActionErrors();

		GregorianCalendar origcalendar = (GregorianCalendar) pForm
				.getCalendar().clone();

		GregorianCalendar calendar = new GregorianCalendar(origcalendar
				.get(GregorianCalendar.YEAR), 0, 1);

		GregorianCalendar endCalendar = (GregorianCalendar) calendar.clone();
		endCalendar.add(GregorianCalendar.MONTH, 12);

		GregorianCalendar scheduleStart = createCalendar(request, pForm
				.getStartDate());
		if (scheduleStart == null) {
			Object[] params = new Object[1];
			params[0] = pForm.getStartDate();
			String errorMess = ClwI18nUtil.getMessage(request,
					"shop.errors.wrongStartDateFormat", params);
			ae.add("error", new ActionError("error.simpleGenericError",
					errorMess));
			return ae;
		}

		GregorianCalendar scheduleEnd = null;
		String endDateS = pForm.getEndDate();
		if (endDateS == null || endDateS.trim().length() == 0) {
			scheduleEnd = (GregorianCalendar) scheduleStart.clone();
			scheduleEnd.add(GregorianCalendar.YEAR, 100);
		} else {
			scheduleEnd = createCalendar(request, endDateS);
			if (scheduleEnd == null) {
				Object[] params = new Object[1];
				params[0] = endDateS;
				String errorMess = ClwI18nUtil.getMessage(request,
						"shop.errors.wrongEndDateFormat", params);
				ae.add("error", new ActionError("error.simpleGenericError",
						errorMess));
				return ae;
			}
		}

		long calendarMills = calendar.getTime().getTime();

		// init OrderScheduleJoin object
		int orderGuideId = pForm.getOrderGuideId();
		if (orderGuideId <= 0) {
			String errorMess = ClwI18nUtil.getMessage(request,
					"shop.errors.noOrderGuideSelected", null);
			ae.add("error", new ActionError("error.simpleGenericError",
					errorMess));
			return ae;
		}

		List datesToShow = new ArrayList();
		String scheduleType = pForm.getScheduleType();

		//Weekly schedule
		if (RefCodeNames.ORDER_SCHEDULE_RULE_CD.WEEK.equals(scheduleType)) {
			datesToShow = calculateWeeklyOrders(scheduleStart, pForm.getWeekDays(), Integer.parseInt(pForm.getWeekCycle()));

		}
		//Month day schedule
		else if (RefCodeNames.ORDER_SCHEDULE_RULE_CD.DAY_MONTH
				.equals(scheduleType)) {

			datesToShow = calculateMonthlyOrders(scheduleStart, pForm.getMonthDays(), Integer.parseInt(pForm.getMonthDayCycle()));
		}
		//Month week (custom) schedule
		else if (RefCodeNames.ORDER_SCHEDULE_RULE_CD.WEEK_MONTH
				.equals(scheduleType)) {
			int monthWeeks = pForm.getMonthWeeks();
			int[] monthMonths = pForm.getMonthMonths();

			datesToShow = calculateCustomOrders(calendar, pForm
					.getMonthWeekDay(), monthWeeks, monthMonths);
		}

		int datesInd = datesToShow.size();
		//Additional days
		String alsoDates = pForm.getAlsoDates();
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
					Object[] params = new Object[1];
					params[0] = dateS;
					String errorMess = ClwI18nUtil.getMessage(request,
							"shop.errors.wrongAlsoDateFormat", params);
					ae.add("error", new ActionError("error.simpleGenericError",
							errorMess));
					return ae;
				}
				if (!(scheduleStart.after(gc) || scheduleEnd.before(gc))) {
					datesToShow.add(gc);
				}
			}
		}

		//Exclude dates
		String excludeDates = pForm.getExcludeDates();
		List excludeDatesL = new ArrayList();
		List datesToNotShow = new ArrayList();
		if (excludeDates != null && excludeDates.trim().length() > 0) {
			int commaInd = 0;
			while (commaInd >= 0) {
				int nextCommaInd = excludeDates.indexOf(',', commaInd);
				String dateS = null;
				if (nextCommaInd > 0) {
					dateS = excludeDates.substring(commaInd, nextCommaInd)
							.trim();
					commaInd = nextCommaInd + 1;
				} else {
					dateS = excludeDates.substring(commaInd).trim();
					commaInd = -1;
				}
				GregorianCalendar gc = createCalendar(request, dateS);
				if (gc == null) {
					Object[] params = new Object[1];
					params[0] = dateS;
					String errorMess = ClwI18nUtil.getMessage(request,
							"shop.errors.wrongNotPlaceDateFormat", params);
					ae.add("error", new ActionError("error.simpleGenericError",
							errorMess));
					return ae;
				}

				for (int ii = 0; ii < datesToShow.size(); ii++) {
					GregorianCalendar dd = (GregorianCalendar) datesToShow
							.get(ii);
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
		ArrayList finaldatesToShow = new ArrayList();
		for (int ii = 0; ii < datesToShow.size(); ii++) {
			GregorianCalendar dd = (GregorianCalendar) datesToShow.get(ii);
			if (dd == null) {
				continue;
			}
			if (!scheduleStart.after(dd) && !dd.after(scheduleEnd)) {
				// this date is not before the start and
				// not after the end of the schedule time span.
				finaldatesToShow.add(dd);

			}
		}

		pForm.setCalendarDatesWithOrders(finaldatesToShow);
		pForm.setNextOrderIndex(0);
		if (datesToShow.size() > 0) {
			pForm.setNextOrderRelMonth(0);
			pForm.setNextOrderMonthDay(0);
		} else {
			pForm.setNextOrderRelMonth(-1);
			pForm.setNextOrderMonthDay(0);
		}

		pForm.setCalendarExcludedDates(datesToNotShow);
		pForm.setNextExcludedIndex(0);
		if (datesToNotShow.size() > 0) {
			GregorianCalendar gc = (GregorianCalendar) datesToNotShow.get(0);
			pForm.setNextExcludedRelMonth(gc.get(GregorianCalendar.YEAR)
					* 12
					+ gc.get(GregorianCalendar.MONTH)
					- (calendar.get(GregorianCalendar.YEAR) * 12 + calendar
							.get(GregorianCalendar.MONTH)));
			pForm.setNextExcludedMonthDay(gc
					.get(GregorianCalendar.DAY_OF_MONTH));
		} else {
			pForm.setNextExcludedRelMonth(-1);
			pForm.setNextExcludedMonthDay(0);
		}

		return ae;
	}

	private static void initFields(HttpServletRequest request,
			OrderSchedulerForm pForm) {
		pForm.setScheduleType(RefCodeNames.ORDER_SCHEDULE_RULE_CD.WEEK);
		pForm.setOrderScheduleId(0);
		pForm.setOrderGuideId(0);
		pForm.setStartDate(ClwI18nUtil.formatDateInp(request, Constants
				.getCurrentDate()));
		pForm.setEndDate("");
		pForm.setScheduleAction(RefCodeNames.ORDER_SCHEDULE_CD.NOTIFY);
		pForm.setCcEmail("");
		pForm.setWeekCycle("1");
		pForm.setWeekDays(new int[0]);
		pForm.setMonthDayCycle("1");
		pForm.setMonthDays(new int[0]);
		pForm.setMonthWeekCycle("1");
		pForm.setMonthWeekDay(2);
		pForm.setMonthWeeks(1);
		pForm.setMonthMonths(new int[0]);
		pForm.setAlsoDates("");
		pForm.setExcludeDates("");
		pForm.setContactName("");
		pForm.setContactPhone("");
		pForm.setContactEmail("");
	}

	public static GregorianCalendar createCalendar(HttpServletRequest request,
			String pDateS) {
		GregorianCalendar gc = null;
		try {
			Date date = ClwI18nUtil.parseDateInp(request, pDateS);
			gc = new GregorianCalendar();
			gc.set(GregorianCalendar.DAY_OF_YEAR, 1);
			gc.set(GregorianCalendar.DAY_OF_MONTH, 1);
			gc.setTime(date);

		} catch (Exception exc) {
		}
		return gc;
	}

	private static GregorianCalendar createCalendarEnUs(String pDateS) {

		if (pDateS == null)
			return null;
		char[] dateA = pDateS.toCharArray();
		int ii = 0;
		for (; ii < dateA.length; ii++) {
			char aa = dateA[ii];
			if (aa == '/')
				break;
			if (aa < '0' || aa > '9')
				return null;
		}
		if (ii == 0 || ii == dateA.length)
			return null;
		String monthS = new String(dateA, 0, ii);
		ii++;
		int ii1 = ii;
		for (; ii < dateA.length; ii++) {
			char aa = dateA[ii];
			if (aa == '/')
				break;
			if (aa < '0' || aa > '9')
				return null;
		}
		if (ii == ii1 || ii == dateA.length)
			return null;
		String dayS = new String(dateA, ii1, ii - ii1);
		ii++;
		ii1 = ii;
		for (; ii < dateA.length; ii++) {
			char aa = dateA[ii];
			if (aa < '0' || aa > '9')
				return null;
		}
		if (ii == ii1)
			return null;
		String yearS = new String(dateA, ii1, ii - ii1);
		int month = 0;
		int day = 0;
		int year = 0;
		try {
			month = Integer.parseInt(monthS);
			month--;
			day = Integer.parseInt(dayS);
			year = Integer.parseInt(yearS);
			if (year < 100)
				year += 2000;
		} catch (NumberFormatException exc) {
			return null;
		}
		GregorianCalendar calendar = null;
		try {
			calendar = new GregorianCalendar(year, month, day);
			calendar.set(GregorianCalendar.DAY_OF_YEAR, 1);
			calendar.set(GregorianCalendar.DAY_OF_MONTH, 1);
		} catch (Exception exc) {
			return null;
		}
		return calendar;
	}

    /**
     * Validate the data for the deletion of an order schedule.
     * @param   request - the <code>HttpServletRequest</code> currently being handled.
     * @param   pForm - an <code>OrderSchedulerForm</code> containing the order schedule data.
     * @return  An <code>ActionErrors</code> object containing any errors.
     */
	public static ActionErrors validateDeleteSchedule(HttpServletRequest request, OrderSchedulerForm pForm) {

		ActionErrors errors = new ActionErrors();
		
    	//if the user has the browse only privilege set, return an error without doing any
		//additional error checking
    	CleanwiseUser currentUser = ShopTool.getCurrentUser(request);
    	if (currentUser.isBrowseOnly()) {
            String errorMess = ClwI18nUtil.getMessage(request, "orders.schedule.error.notAuthorizedForDelete", null);
            errors.add("error", new ActionError("error.simpleGenericError", errorMess));
            return errors;
    	}
		
		//ensure the specified schedule exists
		OrderScheduleViewVector existingSchedules = null;
	    try {
			int siteId = ShopTool.getCurrentUser(request).getSite().getBusEntity().getBusEntityId();
			int userId = ShopTool.getCurrentUser(request).getUserId();
	    	existingSchedules = APIAccess.getAPIAccess().getAutoOrderAPI().getOrderSchedules(siteId, userId);
	    } catch (Exception exc) {
            String errorMess = ClwI18nUtil.getMessage(request, "orders.schedule.error.unexpectedExistingSchedulesRetrievalError");
            errors.add("error", new ActionError("error.simpleGenericError", errorMess));
            return errors;
		}
	    boolean scheduleFound = false;
		for (int i = 0; i < existingSchedules.size() && !scheduleFound; i++) {
			OrderScheduleView existingSchedule = (OrderScheduleView) existingSchedules.get(i);
			scheduleFound =  (pForm.getOrderScheduleId() == existingSchedule.getOrderScheduleId());
		}
		if (!scheduleFound) {
            String errorMess = ClwI18nUtil.getMessage(request, "orders.schedule.error.invalidSchedule");
            errors.add("error", new ActionError("error.simpleGenericError", errorMess));
		}

		return errors;	
	}
	
	public static ActionErrors deleteSchedule(HttpServletRequest request,
			OrderSchedulerForm pForm) {
		ActionErrors ae = new ActionErrors();
		HttpSession session = request.getSession();
		CleanwiseUser appUser = (CleanwiseUser) session
				.getAttribute(Constants.APP_USER);
		if (appUser == null) {
			ae.add("error", new ActionError("error.systemError", "No "
					+ Constants.APP_USER + "session object found"));
			return ae;
		}
		UserData user = appUser.getUser();
		APIAccess factory = (APIAccess) session
				.getAttribute(Constants.APIACCESS);
		if (factory == null) {
			ae.add("error", new ActionError("error.systemError",
					"No Ejb access"));
			return ae;
		}
		AutoOrder autoOrderEjb = null;
		try {
			autoOrderEjb = factory.getAutoOrderAPI();
		} catch (com.cleanwise.service.api.APIServiceAccessException exc) {
			exc.printStackTrace();
			ae.add("error", new ActionError("error.systemError",
					"No order Ejb pointer"));
			return ae;
		}
		int scheduleId = pForm.getOrderScheduleId();
		if (scheduleId <= 0) {
			ae.add("error", new ActionError("error.systemError",
					"Nothing to delete"));
			return ae;
		}
		try {
			autoOrderEjb.deleteOrderSchedule(scheduleId, user.getUserName());
		} catch (RemoteException exc) {
			exc.printStackTrace();
			ae.add("error", new ActionError("error.systemError",
					"exc.getMessage()"));
			return ae;
		}
		//Update in list
		OrderScheduleViewVector orderSchedules = pForm.getOrderSchedules();
		if (orderSchedules == null) {
			orderSchedules = new OrderScheduleViewVector();
		}
		for (int ii = 0; ii < orderSchedules.size(); ii++) {
			OrderScheduleView osv = (OrderScheduleView) orderSchedules.get(ii);
			if (osv.getOrderScheduleId() == scheduleId) {
				orderSchedules.remove(ii);
				break;
			}
		}
		initFields(request, pForm);
		return ae;
	}

	public static ActionErrors showCalDates(HttpServletRequest request,
			OrderSchedulerForm pForm, String variant) {
		ActionErrors ae = new ActionErrors();

		GregorianCalendar calendar = pForm.getCalendar();
		GregorianCalendar endCalendar = (GregorianCalendar) calendar.clone();

		if (calendar == null) {
			Object[] params = new Object[1];
			params[0] = pForm.getCalendar();
			String errorMess = ClwI18nUtil.getMessage(request,
					"shop.errors.badCalendar", params);
			ae.add("error", new ActionError("error.simpleGenericError",
					errorMess));
			return ae;
		}

		if (variant.equals("forward")) {
			pForm.advanceCalendar();
		} else if (variant.equals("backward")) {
			pForm.rewindCalendar();
		}

		ae = showDates(request, pForm);
		return ae;
	}

	public static ActionErrors select(HttpServletRequest request,
			OrderSchedulerForm pForm) {
		ActionErrors ae = new ActionErrors();

		return ae;
	}
}
