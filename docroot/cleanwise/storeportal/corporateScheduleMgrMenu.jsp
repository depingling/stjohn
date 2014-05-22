<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="theForm" name="CORPORATE_SCHEDULE_FORM" type="com.cleanwise.view.forms.CorporateScheduleMgrForm"/>

<%
	int scheduleId = theForm.getScheduleData().getScheduleId();
%>
<table ID=1432 width="<%=Constants.TABLEWIDTH%>">
	<tr bgcolor="#000000">
		<%
			if (scheduleId > 0) {
		%>
		<app:renderStatefulButton
			link="corporateScheduleDetail.do?action=detail" name="Detail"
			tabClassOff="tbar" tabClassOn="tbarOn" linkClassOff="tbar"
			linkClassOn="tbarOn" contains="corporateScheduleDetail" />

		<app:renderStatefulButton
			link="corporateScheduleConfig.do?initconfig=true"
			name="Configuration" tabClassOff="tbar" tabClassOn="tbarOn"
			linkClassOff="tbar" linkClassOn="tbarOn"
			contains="corporateScheduleConfig" />
		<%
			} else {
		%>
		<td class="tbartext">
			&nbsp;
		</td>
		<%
			}
		%>
	</tr>
</table>



