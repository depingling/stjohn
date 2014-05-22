<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ page import="com.cleanwise.service.api.value.DeliveryScheduleView" %>
<%@ page import="com.cleanwise.service.api.value.DeliveryScheduleViewVector" %>
<%@ page import="com.cleanwise.view.forms.OrderSchedulerForm"%>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser"%>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer"%>
<%@ page import="com.cleanwise.view.utils.Constants"%>
<%@ page import="com.cleanwise.view.utils.ShopTool"%>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.GregorianCalendar" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.Map" %>

<%@ taglib uri='/WEB-INF/application.tld' prefix='app' %>
<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='template' %>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<bean:define id="myForm" name="esw.OrdersForm"  type="com.espendwise.view.forms.esw.OrdersForm"/>

<%
	CleanwiseUser user = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
	String orderScheduleLink = "userportal/esw/orders.do";
	boolean siteSelected = (ShopTool.getCurrentSite(request) != null);
	//build a map of weekday numbers to weekday names
	Map<Integer,String> weekdayNumbersToNames = new HashMap<Integer,String>();
	weekdayNumbersToNames.put(Calendar.SUNDAY, ClwI18nUtil.getMessage(request,"global.text.month.sunday"));
	weekdayNumbersToNames.put(Calendar.MONDAY, ClwI18nUtil.getMessage(request,"global.text.month.monday"));
	weekdayNumbersToNames.put(Calendar.TUESDAY, ClwI18nUtil.getMessage(request,"global.text.month.tuesday"));
	weekdayNumbersToNames.put(Calendar.WEDNESDAY, ClwI18nUtil.getMessage(request,"global.text.month.wednesday"));
	weekdayNumbersToNames.put(Calendar.THURSDAY, ClwI18nUtil.getMessage(request,"global.text.month.thursday"));
	weekdayNumbersToNames.put(Calendar.FRIDAY, ClwI18nUtil.getMessage(request,"global.text.month.friday"));
	weekdayNumbersToNames.put(Calendar.SATURDAY, ClwI18nUtil.getMessage(request,"global.text.month.saturday"));
	String recurrenceColSpan="15";
	
	//determine if we are showing a PAR order schedule or not
	List<Integer> parOrderScheduleIds = new ArrayList<Integer>();
	if (Utility.isSet(myForm.getParOrderSchedules())) {
		DeliveryScheduleViewVector parOrderSchedules = myForm.getParOrderSchedules();
		Iterator<DeliveryScheduleView> parOrderScheduleIterator = parOrderSchedules.iterator();
		while (parOrderScheduleIterator.hasNext()) {
			parOrderScheduleIds.add(new Integer(parOrderScheduleIterator.next().getScheduleId()));
		}
	}
	List<Integer> orderScheduleIds = new ArrayList<Integer>();
	if (Utility.isSet(myForm.getOrderSchedulerForm().getOrderSchedules())) {
		int orderScheduleCount = myForm.getOrderSchedulerForm().getOrderSchedules().size();
		for (int count=0; count<orderScheduleCount; count++) {
			orderScheduleIds.add(new Integer(myForm.getOrderSchedulerForm().getOrderScheduleId(count)));
		}
	}
	boolean showingParOrderSchedule = parOrderScheduleIds.contains(myForm.getOrderSchedulerForm().getOrderScheduleId()) &&
										!orderScheduleIds.contains(myForm.getOrderSchedulerForm().getOrderScheduleId());
	
	boolean showingNewOrderSchedule = myForm.getOrderSchedulerForm().getOrderScheduleId() <= 0;
	
	//create a string array of order dates to be used when rendering the calendar at the bottom of the page
	List<GregorianCalendar> orderDateDates = myForm.getOrderSchedulerForm().getCalendarDatesWithOrders();
	StringBuilder orderDatesString = new StringBuilder(50);
	boolean includeSeperator = false;
	if (Utility.isSet(orderDateDates)) {
		Iterator<GregorianCalendar> orderDateDateIterator = orderDateDates.iterator();
		while (orderDateDateIterator.hasNext()) {
			if (includeSeperator) {
				orderDatesString.append(Constants.MULTIPLE_DATE_SEPARATOR);
			}
			//the javascript from Bridgeline that is used to populate the calendar at the bottom of the page is expecting
			//dates to be formatted as MM/dd/yyyy, so use that format
        				
			//STJ-5772: 
			String pattern = ClwI18nUtil.getDatePattern(request);
			//SimpleDateFormat sdf = new SimpleDateFormat(Constants.SIMPLE_DATE_PATTERN);
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			orderDatesString.append(sdf.format(orderDateDateIterator.next().getTime()));
			includeSeperator = true;
		}
	}
	
	String defaultDatePattern = ClwI18nUtil.getUIDateFormat(request).toLowerCase();
	
	//default the also dates field to the default date pattern if it is empty.
	if (!Utility.isSet(myForm.getOrderSchedulerForm().getAlsoDates())) {
		myForm.getOrderSchedulerForm().setAlsoDates(defaultDatePattern);
	}
	//default the exclude dates field to the default date pattern if it is empty.
	if (!Utility.isSet(myForm.getOrderSchedulerForm().getExcludeDates())) {
		myForm.getOrderSchedulerForm().setExcludeDates(defaultDatePattern);
	}

	int alsoDatesSize = 75;
	int excludeDatesSize = 75;
	int ccEmailSize = 75;
%>
<script type="text/javascript">

function synchronizeCalendarControlColors() {
	var active_color = '#575757'; // Color of user provided text
	var inactive_color = '#C2C2C2'; // Color of default text
	if ($.trim($("#alsoDates").val()) == '<%=defaultDatePattern%>') {
		$("#alsoDates").css('color', inactive_color);
	}
	else {
		$("#alsoDates").css('color', active_color);
	}
	if ($.trim($("#excludeDates").val()) == '<%=defaultDatePattern%>') {
		$("#excludeDates").css('color', inactive_color);
	}
	else {
		$("#excludeDates").css('color', active_color);
	}	
}

function newOrderSchedule() {
	$("input[name=operation]").val('<%=Constants.PARAMETER_OPERATION_VALUE_START_NEW_ORDER_SCHEDULE%>');
	submitForm('orderForm');
}

function saveOrderSchedule() {
	$("input[name=operation]").val('<%=Constants.PARAMETER_OPERATION_VALUE_SAVE_ORDER_SCHEDULE%>');
	submitForm('orderForm');
}

function showParOrderSchedule(scheduleId) {
	$('#scheduleId').val(scheduleId);
	$('#operation').val('<%=Constants.PARAMETER_OPERATION_VALUE_SHOW_CORPORATE_ORDER_SCHEDULE%>');
	submitForm('orderForm');
}

function showSchedule(scheduleId) {
	$('#scheduleId').val(scheduleId);
	$('#operation').val('<%=Constants.PARAMETER_OPERATION_VALUE_SHOW_ORDER_SCHEDULE%>');
	submitForm('orderForm');
}

function deleteSchedule() {
	$("input[name=operation]").val('<%=Constants.PARAMETER_OPERATION_VALUE_DELETE_ORDER_SCHEDULE%>');	
	submitForm('orderForm');
}

function showAdditionalDaysCalendar() {

	//if we're not already showing the additional days calendar, show it
	if ( $("div.orderScheduleAdditionalDays:visible").length === 0) {
		//if the current value is the default date format, blank it out
		if ($.trim($("#alsoDates").val()) == '<%=defaultDatePattern%>') {
			$("#alsoDates").val("");
		}
		synchronizeCalendarControlColors();
		//for some reason, without the next line the calendar initially appears using Japanese characters (although
    	//as soon as the user clicks on the forward/back buttons it displays correctly.  All the next line is doing
    	//is setting the dayNamesMin property to itself, but it fixes the problem.
    	$('div.orderScheduleAdditionalDays').datepicker("option", "dayNamesMin", $('div.orderScheduleAdditionalDays').datepicker("option", "dayNamesMin"));
		$('div.orderScheduleAdditionalDays').show();
		$('div.orderScheduleAdditionalDaysButtons').show();
	}
	//nothing to do as the calendar is already open
	else {
	}
}

function refreshAdditionalDaysCalendar() {
	//if we're not showing the additional days calendar, do nothing
	if ( $("div.orderScheduleAdditionalDays:visible").length === 0) {
	}
	//otherwise refresh the calendar
	else {
		synchronizeCalendarControlColors();
    	$('div.orderScheduleAdditionalDays').datepicker("hide");
    	$('div.orderScheduleAdditionalDays').datepicker("show");
	}
}

function hideAdditionalDaysCalendar() {
	//nothing to do as the calendar is already open
	if ( $("div.orderScheduleAdditionalDays:visible").length === 0) {
	}
	//if we're showing the additional days calendar, hide it
	else {
		//if the current value is the blank, set it to the default date format
		if ($.trim($("#alsoDates").val()) == '') {
			$("#alsoDates").val("<%=defaultDatePattern%>");
		}
		synchronizeCalendarControlColors();
		$('div.orderScheduleAdditionalDays').hide();
		$('div.orderScheduleAdditionalDaysButtons').hide();
	}
}

function showExcludedDaysCalendar() {
	//if we're not already showing the excluded days calendar, show it
	if ( $("div.orderScheduleExcludedDays:visible").length === 0) {
		//if the current value is the default date format, blank it out
		if ($.trim($("#excludeDates").val()) == '<%=defaultDatePattern%>') {
			$("#excludeDates").val("");
		}
		synchronizeCalendarControlColors();
		//for some reason, without the next line the calendar initially appears using Japanese characters (although
    	//as soon as the user clicks on the forward/back buttons it displays correctly.  All the next line is doing
    	//is setting the dayNamesMin property to itself, but it fixes the problem.
    	$('div.orderScheduleExcludedDays').datepicker("option", "dayNamesMin", $('div.orderScheduleExcludedDays').datepicker("option", "dayNamesMin"));
		$('div.orderScheduleExcludedDays').show();
		$('div.orderScheduleExcludedDaysButtons').show();
	}
	//nothing to do as the calendar is already open
	else {
	}
}

function refreshExcludedDaysCalendar() {
	//if we're not showing the excluded days calendar, do nothing
	if ( $("div.orderScheduleExcludedDays:visible").length === 0) {
	}
	//otherwise refresh the calendar
	else {
		synchronizeCalendarControlColors();
		$('div.orderScheduleExcludedDays').datepicker("hide");
    	$('div.orderScheduleExcludedDays').datepicker("show");
	}
}

function hideExcludedDaysCalendar() {
	//nothing to do as the calendar is already open
	if ( $("div.orderScheduleExcludedDays:visible").length === 0) {
	}
	//if we're showing the excluded days calendar, hide it
	else {
		//if the current value is the blank, set it to the default date format
		if ($.trim($("#excludeDates").val()) == '') {
			$("#excludeDates").val("<%=defaultDatePattern%>");
		}
		synchronizeCalendarControlColors();
		$('div.orderScheduleExcludedDays').hide();
		$('div.orderScheduleExcludedDaysButtons').hide();
	}
}

</script>

<app:setLocaleAndImages/>
		<%
			String errorsAndMessagesPage = ClwCustomizer.getStoreFilePath(request, Constants.PORTAL_ESW, "errorsAndMessages.jsp");
		%>
        <jsp:include page="<%=errorsAndMessagesPage %>"/>
        <!-- Begin: Shopping Sub Tabs -->
       	<%
			String shoppingTabPage = ClwCustomizer.getStoreFilePath(request, Constants.PORTAL_ESW, "shoppingTabs.jsp");
		%>
     	<jsp:include page="<%=shoppingTabPage%>"/>
   		<!-- End: Shopping Sub Tabs -->
        <div class="singleColumn clearfix" id="contentWrapper">
            <div id="content">
                <!-- Start Box -->
                <div class="boxWrapper">
                    <div class="content">
                    	<%
                    		if (siteSelected) {
                    	%>
                        <div class="left scheduleWidget clearfix">
                            <div class="rightColumn">
                                <div class="rightColumnIndent clearfix">
                                	<%
                                    	if (!user.isBrowseOnly() || !showingNewOrderSchedule) {
                                	%>
				                    		<h2>
				                    			<%
				                    				String scheduleLabel = null;	
				                    				if (showingParOrderSchedule) {
				                    					scheduleLabel = ClwI18nUtil.getMessage(request,"futureOrder.corporateOrder.schedule");
				                    				}
				                    				else {
					                    				scheduleLabel = Utility.encodeForHTML(myForm.getOrderSchedulerForm().getOrderScheduleName(request, ""));
				                    				}
				                    				if (!Utility.isSet(scheduleLabel)) {
				                    					scheduleLabel = ClwI18nUtil.getMessage(request,"orders.schedules.label.newSchedule");
				                    				}
				                    			%>
				                    			<%=scheduleLabel%>
				                    		</h2>
			                    	<%
			                    		if (!showingParOrderSchedule) {
			                    	%>
	                                    	<p class="required topPadding right">
												*&nbsp;<app:storeMessage key="global.text.required" />
	                                    	</p>
	                                <%
			                    		}
	                                %>
		                                    <hr/>
		                            <%
                                    	}
		                            %>
                               		<html:form styleId="orderForm" action="<%=orderScheduleLink%>">
                                 		<html:hidden styleId="operation" property="<%=Constants.PARAMETER_OPERATION%>" value=""/>
                                 		<html:hidden styleId="scheduleId" property="<%=Constants.PARAMETER_ORDER_SCHEDULE_ID %>"/>
		                    	<%
		                    		if (!showingParOrderSchedule && (!user.isBrowseOnly() || !showingNewOrderSchedule)) {
		                    	%>
			                            <table class="noBorder">
	                                        <colgroup>
	                                            <col class="leftColumn" />
	                                            <col />
	                                        </colgroup>
                                        	<tr>
                                            	<td>
	                                                <app:storeMessage key="orders.schedules.label.shoppingList" /><span class="required">&nbsp;*</span>
                                            	</td>
                                            	<td>
                                                	<html:select property="orderSchedulerForm.orderGuideId">
                                                		<html:option value="">
                                                			-- <app:storeMessage key="global.action.label.select"/> --
                                                		</html:option>
                                                		<%
	    	                                            	//create a set of options from the available user shopping lists.  Template shopping lists
	    	                                            	//are intentionally excluded from the dropdown.
	    	                                            	if (Utility.isSet(myForm.getOrderSchedulerForm().getUserOrderGuides())) {
                                                		%>
                                                				<html:optionsCollection 
                                                					property="orderSchedulerForm.userOrderGuides"
                                                					label="shortDesc" value="orderGuideId"/>
                                                		<%
	    	                                            	}
                                                		%>
                                                	</html:select>
                                            	</td>
                                        	</tr>
	                                        <tr>
	                                            <td>
	                                                <app:storeMessage key="orders.schedules.label.startDate" /><span class="required">&nbsp;*</span>
	                                            </td>
	                                            <td>
	                                                <html:text property="orderSchedulerForm.startDate" styleId="startDate" styleClass="default-value standardCal" />
	                                            </td>
	                                        </tr>
											<tr>
	                                            <td>
	                                                <app:storeMessage key="orders.schedules.label.endDate" /><span class="required">&nbsp;*</span>
                                            	</td>
                                            	<td>
	                                                <html:text property="orderSchedulerForm.endDate" styleId="endDate" styleClass="default-value standardCal" />
	                                            </td>
	                                        </tr>
                                        	<tr>
                                            	<td>
	                                                <app:storeMessage key="global.label.type" /><span class="required">&nbsp;*</span>
                                            	</td>
                                            	<td>
                                                	<p class="inputRow noMargin">
                                                		<html:radio property="orderSchedulerForm.scheduleAction" 
                                                			value="<%=RefCodeNames.ORDER_SCHEDULE_CD.NOTIFY %>"/>&nbsp;<label><app:storeMessage key="orders.schedules.label.remindMeToPlaceMyOrder" /></label>
                                                		<html:radio property="orderSchedulerForm.scheduleAction"
                                                			value="<%=RefCodeNames.ORDER_SCHEDULE_CD.PLACE_ORDER %>"/>&nbsp;<label><app:storeMessage key="orders.schedules.label.automaticallyPlaceMyOrder" /></label>
                                                	</p>
                                            	</td>
                                        	</tr>
											<tr>
	                                            <td>
	                                                <app:storeMessage key="orders.schedules.label.ccEmail" />
                                            	</td>
                                            	<td>
	                                                <html:text property="orderSchedulerForm.ccEmail" size="<%=Integer.toString(ccEmailSize)%>"/>
	                                            </td>
	                                        </tr>
                                    	</table>
                                    	<hr/>
                                    	<table class="noBorder">
                                    		<tbody>
	                                        	<tr>
	                                            	<td class="recurrence" colspan="<%=recurrenceColSpan%>" align="left">
		                                                <app:storeMessage key="orders.schedules.label.recurrence" /><span class="required">&nbsp;*</span>
	                                                	<html:select property="orderSchedulerForm.scheduleType" styleClass="recurrence">
											            	<html:option value="<%=RefCodeNames.ORDER_SCHEDULE_RULE_CD.WEEK%>">
	                                                			<app:storeMessage key="orders.schedules.label.recurrence.weekly" />
											            	</html:option>
											            	<html:option value="<%=RefCodeNames.ORDER_SCHEDULE_RULE_CD.DAY_MONTH%>">
	                                                			<app:storeMessage key="orders.schedules.label.recurrence.monthly" />
											                </html:option>
											              	<html:option value="<%=RefCodeNames.ORDER_SCHEDULE_RULE_CD.DATE_LIST%>">
	                                                			<app:storeMessage key="orders.schedules.label.recurrence.custom" />
											                </html:option>
	                                                	</html:select>
	                                                	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	                                                	<div class="weekly">
	                                                		<app:storeMessage key="orders.schedules.label.recurrence.weekly.begin" />
	                                                		&nbsp;<html:text property="orderSchedulerForm.weekCycle" styleClass="qtyInput"/> 
	                                                		<app:storeMessage key="orders.schedules.label.recurrence.weekly.end" />:
	                                                	</div>
	                                                	<div class="monthly">
	                                                		<app:storeMessage key="orders.schedules.label.recurrence.monthly.begin" />
	                                                		&nbsp;<html:text property="orderSchedulerForm.monthDayCycle" styleClass="qtyInput"/> 
	                                                		<app:storeMessage key="orders.schedules.label.recurrence.monthly.end" />:
	                                                	</div>
	                                                	<div class="custom">
	                                                	</div>
	                                            	</td>
	                                        	</tr>
											</tbody>
											<tbody id="weeklyScheduleControls">
												<tr align="left">
													<td colspan="<%=recurrenceColSpan%>">
														&nbsp;
													</td>
												</tr>
												<tr align="left">
			                                    		<%
			                                    			int dayOfWeek = Calendar.getInstance(ClwI18nUtil.getUserLocale(request)).getFirstDayOfWeek();
			                                    			for (int count=Calendar.SUNDAY; count<=Calendar.SATURDAY;  count++) {
			                                    		%>
													<td colspan="2">
			          											<html:multibox property="orderSchedulerForm.weekDays" value="<%=Integer.toString(dayOfWeek)%>"/>&nbsp;<label><%=weekdayNumbersToNames.get(dayOfWeek)%></label>
			                                    		<%
			                                    				dayOfWeek = dayOfWeek + 1;
			                                    				if (dayOfWeek > Calendar.SATURDAY) {
			                                    					dayOfWeek = Calendar.SUNDAY;
			                                    				}
			                                    		%>
													</td>
			                                    		<%
			                                    			}
			                                    		%>
			                                    	<td>
			                                    		&nbsp;
			                                    	</td>
												</tr>
											</tbody>
											<tbody id="monthlyScheduleControls">
												<tr align="center">
													<td colspan="<%=recurrenceColSpan%>">
														&nbsp;
													</td>
												</tr>
							    				<tr align="center">
												    <% 
												    	for (int i=Constants.MONTH_DAY_MIN; i<Integer.valueOf(recurrenceColSpan); i++) { 
												    %>
													        <td>
													        	<%=Integer.toString(i)%>
													        </td>
												    <% 
												    	} 
												    %>
												    <td>
												    	&nbsp;
												    </td>
												</tr>
												<tr align="center">
												    <% 
												    	for (int i=Constants.MONTH_DAY_MIN; i<Integer.valueOf(recurrenceColSpan); i++) { %>
													        <td>
																<html:multibox property="orderSchedulerForm.monthDays" value="<%=Integer.toString(i)%>"/>
															</td>
												    <% 
												    	} 
												    %>
												    <td>
												    	&nbsp;
												    </td>
												</tr>
							    				<tr align="center">
												    <% 
												    	for (int i=Integer.valueOf(recurrenceColSpan); i<=Constants.MONTH_DAY_MAX; i++) { 
												    %>
													        <td>
													        	<%=Integer.toString(i)%>
													        </td>
												    <% 
												    	} 
												    %>
												    <td>
														<app:storeMessage key="orders.schedules.label.lastDay" />
												    </td>
												</tr>
												<tr align="center">
												    <% 
												    	for (int i=Integer.valueOf(recurrenceColSpan); i<=Constants.MONTH_DAY_MAX; i++) { %>
													        <td>
																<html:multibox property="orderSchedulerForm.monthDays" value="<%=Integer.toString(i)%>"/>
															</td>
												    <% 
												    	} 
												    %>
												    <td>
														<html:multibox property="orderSchedulerForm.monthDays" value="<%=Integer.toString(Constants.MONTH_DAY_LAST)%>" />
												    </td>
												</tr>
											</tbody>
											<tbody id="customScheduleControls">
											</tbody>
                                    	</table>
			                            <table class="noBorder">
	                                        <colgroup>
	                                            <col class="leftColumn" />
	                                            <col />
	                                        </colgroup>
											<tr align="left">
                                            	<td>
                                                	<app:storeMessage key="orders.schedules.label.additionalDays" />
                                           		</td>
                                           		<td>
                                                	<html:text property="orderSchedulerForm.alsoDates" size="<%=Integer.toString(alsoDatesSize)%>"
                                                		styleClass="calendarTextBox" styleId="alsoDates"
                                                		onclick="showAdditionalDaysCalendar();" onkeypress="refreshAdditionalDaysCalendar();"/>
                                            	</td>
											</tr>
											<tr align="left">
												<td colspan="2">
                                                	<div style="display: none" class="orderScheduleAdditionalDays">
                                                	</div>
                                                	</br>
                                                	<div style="display:none" class="orderScheduleAdditionalDaysButtons">
				    	                                <html:link href="javascript:hideAdditionalDaysCalendar();" styleClass="smallBtn">
				    	                                	<span>
					                                        	<app:storeMessage key="global.action.label.close" />
				    	                                	</span>
		    	                                		</html:link>
                                                	</div>
												</td>
											</tr>
											<tr align="left">
                                            	<td>
                                                	<app:storeMessage key="orders.schedules.label.exceptionDays" />
                                           		</td>
                                           		<td>
                                                	<html:text property="orderSchedulerForm.excludeDates" size="<%=Integer.toString(excludeDatesSize)%>"
                                                		styleClass="calendarTextBox" styleId="excludeDates"
                                                		onclick="showExcludedDaysCalendar();" onkeypress="refreshExcludedDaysCalendar();"/>
                                            	</td>
											</tr>
											<tr align="left">
												<td colspan="2">
                                                	<div style="display: none" class="orderScheduleExcludedDays">
                                                	</div>
                                                	</br>
                                                	<div style="display:none" class="orderScheduleExcludedDaysButtons">
				    	                                <html:link href="javascript:hideExcludedDaysCalendar();" styleClass="smallBtn">
				    	                                	<span>
					                                        	<app:storeMessage key="global.action.label.close" />
				    	                                	</span>
		    	                                		</html:link>
                                                	</div>
												</td>
											</tr>
                                    	</table>
                                    	<br/>
		                                <%
		                                	if (!user.isBrowseOnly()) {
		                                %>
			                                    <html:link href="javascript:saveOrderSchedule()" styleClass="blueBtnLarge">
			                                    	<span>
			                                        	<app:storeMessage key="global.action.label.save" />
			                                    	</span>
			                                    </html:link>
			                                    <%
			                                    	StringBuilder href = new StringBuilder();
			                                    	if (myForm.getOrderSchedulerForm().getOrderScheduleId() > 0) {
				                                    	href.append("javascript:showSchedule(");
				                                    	href.append(Integer.toString(myForm.getOrderSchedulerForm().getOrderScheduleId()));
				                                    	href.append(")");
			                                    	}
			                                    	else {
			                                    		href.append("javascript:newOrderSchedule()");
			                                    	}
			                                    %>								
		    	                                <html:link href="<%=href.toString()%>" styleClass="blueBtnLarge">
		    	                                	<span>
			                                        	<app:storeMessage key="global.label.revert" />
		    	                                	</span>
		    	                                </html:link>
			                                    <%
			                                    	href = new StringBuilder(50); 
			                                    	StringBuilder styleClass = new StringBuilder(50);
			                                    	if (myForm.getOrderSchedulerForm().getOrderScheduleId() <= 0) {
			                                    		styleClass.append("greyBtnLarge");
				                                    	href.append("javascript:void()");
			                                    	}
			                                    	else {
			                                    		styleClass.append("blueBtnLarge");
			                                    		href.append("javascript:deleteSchedule()");
			                                    	}
			                                    %>								
												<html:link href="<%=href.toString() %>" styleClass="<%=styleClass.toString() %>">
													<span>
			                                        	<app:storeMessage key="global.action.label.delete" />
													</span>
												</html:link>
					                            <table class="noBorder">
			                                        <colgroup>
			                                            <col class="leftColumn" />
			                                        </colgroup>
													<tr align="left">
		                                            	<td>
		                                                	&nbsp;
		                                           		</td>
													</tr>
		                                    	</table>
										<%
		                                	}
										%>
										<hr/>
		                    	<%
		                    		}
		                    	%>
									</html:form>
								<%
									if (!user.isBrowseOnly() || !showingNewOrderSchedule) {
								%>
	                                    <input class="selectedDays" type="hidden" value="<%=orderDatesString.toString()%>" />
	                                    <div class="threeColCalendar">
	                                    </div>
	                            <%
									}
	                            %>
                                </div>
                            </div>
                            <div class="leftColumn">
                                <h3><app:storeMessage key="orders.schedules.label.orderSchedules" /></h3>
                                <ul>
                                <%
                                	String liClassString;
                                	int parOrderScheduleCount = 0;
                                	if (Utility.isSet(myForm.getParOrderSchedules())) {
                                		parOrderScheduleCount = myForm.getParOrderSchedules().size();
                                	}
                                	for (int count=0; count<parOrderScheduleCount; count++) {
                                		String scheduleName = ClwI18nUtil.getMessage(request,"futureOrder.corporateOrder.schedule");
                                		int scheduleId = ((DeliveryScheduleView)myForm.getParOrderSchedules().get(count)).getScheduleId();
                                    	StringBuilder href = new StringBuilder();
                                    	href.append("javascript:showParOrderSchedule(");
                                    	href.append(scheduleId);
                                    	href.append(")");
                                    	if (showingParOrderSchedule && 
                                    			scheduleId == myForm.getOrderSchedulerForm().getOrderScheduleId()) {
                                    		liClassString = "class=\"selected\"";
                                    	}
                                    	else {
                                    		liClassString = StringUtils.EMPTY;
                                    	}
                                %>
                                    <li <%=liClassString%>>
                                    	<html:link href="<%=href.toString()%>">
                                    		<%=scheduleName%>
                                    	</html:link>
                                    </li>
                                <%
                                	}
                                	OrderSchedulerForm orderSchedulerForm = myForm.getOrderSchedulerForm();
                                	int orderScheduleCount = 0;
                                	if (Utility.isSet(orderSchedulerForm.getOrderSchedules())) {
                                		orderScheduleCount = orderSchedulerForm.getOrderSchedules().size();
                                	}
                                	for (int count=0; count<orderScheduleCount; count++) {
                                		String scheduleName = Utility.encodeForHTML(orderSchedulerForm.getOrderScheduleName(request, count, ""));
                                		int scheduleId = Integer.parseInt(orderSchedulerForm.getOrderScheduleId(count));
                                    	StringBuilder href = new StringBuilder();
                                    	href.append("javascript:showSchedule(");
                                    	href.append(orderSchedulerForm.getOrderScheduleId(count));
                                    	href.append(")");
                                    	if (!showingParOrderSchedule && 
                                    			scheduleId == myForm.getOrderSchedulerForm().getOrderScheduleId()) {
                                    		liClassString = "class=\"selected\"";
                                    	}
                                    	else {
                                    		liClassString = StringUtils.EMPTY;
                                    	}
                                %>
                                    <li <%=liClassString%>>
                                    	<html:link href="<%=href.toString()%>">
                                    		<%=scheduleName%>
                                    	</html:link>
                                    </li>
                                <%
                                	}
                                %>
                                </ul>
                                <%
                                	if (!user.isBrowseOnly()) {
                                %>
		                                <html:link href="javascript:newOrderSchedule()" styleClass="btn leftBtn topMargin">
		                                	<span>
		                                		<app:storeMessage key="global.action.label.create" />
		                                	</span>
		                                </html:link>
		                        <%
                                	}
		                        %>
                        	</div>
                   		</div>
                   		<%
                    		}
                   		%>
                   	</div>
                    <div class="bottom clearfix"><span class="left">&nbsp;</span><span class="center">&nbsp;</span><span class="right">&nbsp;</span></div>
                </div>
                <!-- End Box -->
            </div>
        </div>
        <script>
        	synchronizeCalendarControlColors();
        </script>
                