<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.service.api.util.SearchCriteria" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<app:checkLogon/>

<html:html>
<head>
	<title>Corporate Schedule Search</title>
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	<link rel="stylesheet" href="../externals/styles.css">
</head>
<bean:define id="theForm" name="CORPORATE_SCHEDULE_FORM"
	type="com.cleanwise.view.forms.CorporateScheduleMgrForm" />
<%
	String browser = (String) request.getHeader("User-Agent");
		String isMSIE = "";
		if (browser != null && browser.indexOf("MSIE") >= 0) {
			isMSIE = "Y";
%>
<script language="JavaScript" src="../externals/calendar.js"></script>
<iframe
	style="display: none; position: absolute; z-index: 1; width: 148; height: 194"
	id="CalFrame" marginheight=0 marginwidth=0 noresize frameborder=0
	scrolling=no src="../externals/calendar.html"></iframe>
<%
	} else {
%>
<script language="JavaScript" src="../externals/calendarNS.js"></script>
<%
	}
%>
<body bgcolor="#cccccc">
	<%@include file="/externals/calendar/calendar.jsp"%>
	<div class="text">

		<table ID=266 cellspacing="0" border="0" width="769" class="mainbody">
			<html:form styleId="267" action="storeportal/corporateSchedule.do">
				<html:hidden name="CORPORATE_SCHEDULE_FORM" property="contentPage"
					value="corporateScheduleMgrSearch.jsp" />
				<tr>
					<td>
						<b>Search Schedules:</b>
					</td>
					<td colspan="3">
						<html:text name="CORPORATE_SCHEDULE_FORM" property="searchName" size="30" />
						<html:radio property="searchType" value="id" />
						ID
						<html:radio property="searchType" value="nameBegins" />
						Name(starts with)
						<html:radio property="searchType" value="nameContains" />
						Name(contains)
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>From:</td>
					<td>To:</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>
						<b>Schedule Date:</b>
						<br>
						(mm/dd/yyyy)
					</td>
					<td align="left">
						<nobr>
							<html:text name="CORPORATE_SCHEDULE_FORM"
								styleId="searchDateFrom" property="searchDateFrom"
								maxlength="10" />
							<a href="#"
								onClick="showCalendar('searchDateFrom', event);return false;"
								title="Choose Date"><img name="DATEFROM"
									src="../externals/images/showCalendar.gif" width=19 height=19
									border=0 align="absmiddle" style="position: relative"
									onmouseover="window.status='Choose Date';return true"
									onmouseout="window.status='';return true">
							</a>
						</nobr>
					</td>
					<td align="left">
						<nobr>
							<html:text name="CORPORATE_SCHEDULE_FORM" property="searchDateTo"
								maxlength="10" />
							<%
								if ("Y".equals(isMSIE)) {
							%>
							<a href="#"
								onClick="event.cancelBubble=true; return ShowCalendar(document.forms[0].DATETO, document.forms[0].searchDateTo, null, -7300, 7300, null, false);"
								title="Choose Date"><img name="DATETO"
									src="../externals/images/showCalendar.gif" width=19 height=19
									border=0 align="absmiddle" style="position: relative"
									onmouseover="window.status='Choose Date';return true"
									onmouseout="window.status='';return true">
							</a>
							<%
								} else {
							%>
							<a href="javascript:show_calendar('forms[0].searchDateTo');"
								onmouseover="window.status='Choose Date';return true;"
								onmouseout="window.status='';return true;" title="Choose Date"><img
									src="../externals/images/showCalendar.gif" width=19 height=19
									border=0>
							</a>
							<%
								}
							%>
						</nobr>
					</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td colspan="4">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="4">&nbsp;</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td colspan="3">
						<html:submit property="action" value="Search" />
						<html:submit property="action" value="Create New" />
					</td>
				</tr>
		</table>
		<logic:present name="CORPORATE_SCHEDULE_FORM" property="scheduleList">
			<%
				int size = theForm.getScheduleList().size();
			%>
			Count: <%=size%>
			<%
				if (size > 0) {
			%>
			<table ID=268 cellspacing="0" border="0" width="769">
				<tr align=center>
					<th class="stpTH">
						<a ID=269
							href="corporateSchedule.do?action=sort&sortField=ScheduleId"><b>Schedule Id</b>
						</a>
					</th>
					<th class="stpTH">
						<a ID=270
							href="corporateSchedule.do?action=sort&sortField=ScheduleName"><b>Corporate Schedule Name</b>
						</a>
					</th>
					<th class="stpTH">
						<a ID=272
							href="corporateSchedule.do?action=sort&sortField=ScheduleInfo"><b>Schedule Date</b>
						</a>
					</th>
					<th class="stpTH">
						<a ID=271
							href="corporateSchedule.do?action=sort&sortField=ScheduleStatus"><b>Status</b>
						</a>
					</th>
				</tr>
				<logic:iterate id="arrele" name="CORPORATE_SCHEDULE_FORM"
					property="scheduleList"
					type="com.cleanwise.service.api.value.DeliveryScheduleView"
					indexId="idx">
					<bean:define id="eleid" name="arrele" property="scheduleId" />
					<tr>
						<td class="stpTD">
							<bean:write name="arrele" property="scheduleId" />
						</td>
						<td class="stpTD">
							<a ID=275
								href="corporateScheduleDetail.do?action=detail&scheduleId=<%=eleid%>">
								<bean:write name="arrele" property="scheduleName" /> </a>
						</td>
						<%
							java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM/dd/yyyy");
							String nextDel = (arrele.getNextDelivery() == null) ? "&nbsp;"
										: sdf.format(arrele.getNextDelivery());
						%>
						<td class="stpTD"><%=nextDel%></td>
						<td class="stpTD"> <bean:write name="arrele" property="scheduleStatus" /> </td>
					</tr>
				</logic:iterate>
			</table>
			<%
				}
			%>
		</logic:present>
		</html:form>
	</div>
</body>
</html:html>

