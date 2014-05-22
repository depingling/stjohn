<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.cleanwise.service.api.value.*"%>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>


<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<html:html>
<head>
	<title>Corporate Schedule Search</title>
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	<link rel="stylesheet" href="../externals/styles.css">
</head>

<bean:define id="theForm" name="CORPORATE_SCHEDULE_FORM"
	type="com.cleanwise.view.forms.CorporateScheduleMgrForm" />
<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session" />
<%
    String browser = (String)  request.getHeader("User-Agent");
    String isMSIE = "";
    if(browser!=null && browser.indexOf("MSIE")>=0) {
        isMSIE = "Y";
%>
<script language="JavaScript" src="../externals/calendar.js"></script>
<iframe
	style="display: none; position: absolute; z-index: 1; width: 148; height: 194"
	id="CalFrame" marginheight=0 marginwidth=0 noresize frameborder=0
	scrolling=no src="../externals/calendar.html"></iframe>
<% } else {  %>
<script language="JavaScript" src="../externals/calendarNS.js"></script>
<% }  %>

<div class="text">
	<table ID=220 border="0" cellpadding="0" cellspacing="0" width="769"
		class="mainbody">
		<tr>
			<td height="5"></td>
		</tr>
		<tr>
			<td>
				<b>Schedule Id:</b>
				<bean:write name="CORPORATE_SCHEDULE_FORM"
					property="scheduleData.scheduleId" />
			</td>
			<td colspan="2">&nbsp;</td>
			<td>
				<b>Schedule Name:</b>
				<bean:write name="CORPORATE_SCHEDULE_FORM"
					property="scheduleData.shortDesc" />
			</td>
			<td colspan="2">&nbsp;</td>
			<td>
				<b>Schedule Status:</b>
				<bean:write name="CORPORATE_SCHEDULE_FORM"
					property="scheduleData.scheduleStatusCd" />
			</td>
			<td colspan="2">&nbsp;</td>
		</tr>
		<tr>
			<td height="5"></td>
		</tr>
	</table>

	<table ID=526 width="769">
		<tr>
			<td align="center"> </td>
		</tr>
	</table>
	<table ID=219 width="769" cellspacing="0" border="0" class="mainbody">
		<html:form styleId="dlsmd"
			action="storeportal/corporateScheduleDetail.do">
			<html:hidden name="CORPORATE_SCHEDULE_FORM" property="contentPage"
				value="dlScheduleMgrDetail.jsp" />
			<tr>
				<td class="smalltext">
					<!-- Schdule -->
					<table ID=221 class="smalltext" border="0" cellpadding="0"
						cellspacing="0" width="100%">
						<tr>
							<td width="15%">
								<b>Schedule Name:</b>
							</td>
							<td width="32%">
								<html:text name="CORPORATE_SCHEDULE_FORM"
									property="scheduleData.shortDesc" maxlength="30" size="30" />
								<font color="red">*</font>
							</td>
							<td width="18%">
								<b>Interval Hours:</b>
							</td>
							<td>
								<html:text name="CORPORATE_SCHEDULE_FORM"
									property="invCartAccessInterval" maxlength="3" size="1" />
								<font color="red">*</font>
							</td>
						</tr>
						<tr>
							<td>
								<b>Status:</b>
							</td>
							<td>
								<html:select name="CORPORATE_SCHEDULE_FORM"
									property="scheduleData.scheduleStatusCd">
									<html:option
										value="<%=RefCodeNames.SCHEDULE_STATUS_CD.ACTIVE%>"><%=RefCodeNames.SCHEDULE_STATUS_CD.ACTIVE%></html:option>
									<html:option
										value="<%=RefCodeNames.SCHEDULE_STATUS_CD.INACTIVE%>"><%=RefCodeNames.SCHEDULE_STATUS_CD.INACTIVE%></html:option>
									<html:option
										value="<%=RefCodeNames.SCHEDULE_STATUS_CD.LIMITED%>"><%=RefCodeNames.SCHEDULE_STATUS_CD.LIMITED%></html:option>
								</html:select>
								<font color="red">*</font>
							</td>
							<td>
								<b>Cutoff Time:</b>
								<br>
								(24-Hour Military Time)
							</td>
							<td>
								<html:text name="CORPORATE_SCHEDULE_FORM" property="cutoffTime"
									maxlength="5" size="8" />
								<font color="red">*</font>
							</td>
						</tr>
						<tr>
							<td colspan="4" height="15"></td>
						</tr>
						<tr>
							<td colspan="4">
								<table border="0" cellpadding="0" cellspacing="0" width="100%">
									<tr>
										<td>
											<b>Schedule Dates:</b>
										</td>
									</tr>
									<tr>
										<td>
											<font size="-2">(mm/dd/yy, ...)</font>
										</td>
									</tr>
									<tr>
										<td>
											<html:textarea name="CORPORATE_SCHEDULE_FORM"
												property="alsoDates" cols="85" rows="3" />
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td colspan="4" height="15"></td>
						</tr>
						<tr>
							<td colspan="4">
								<table border="0" cellpadding="0" cellspacing="0" width="100%">
									<tr>
										<td>
											<b>Physical Inventory Dates:</b>
										</td>
									</tr>
									<tr>
										<td>
											<font size="-2">(mm/dd/yyyy, mm/dd/yyyy, mm/dd/yyyy)...</font>
										</td>
									</tr>
									<tr>
										<td>
											<html:textarea name="CORPORATE_SCHEDULE_FORM"
												property="physicalInventoryPeriods" cols="85" rows="3" />
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="4" height="15"></td>
			</tr>
			<tr>
				<td align="center" class="smalltext">
					<html:submit property="action" value="Save" />
					<% if(theForm.getScheduleData().getScheduleId()>0) {  %>
					<html:submit property="action" value="Delete" />
					<% } %>
				</td>
			</tr>
			<tr>
				<td colspan="4" height="15"></td>
			</tr>
	</table>
	<input type="hidden" name="action" value="unknown" />
	<input type="hidden" name="command" value="unknown" />
	</html:form>
</div>
</html:html>




