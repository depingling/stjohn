<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<app:checkLogon/>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="Location" value="contract" type="java.lang.String" toScope="session"/>
<bean:define id="theForm" name="REPORT_SCHEDULE_FORM" type="com.cleanwise.view.forms.ReportScheduleForm"/>
<html:html>

<head>
<link rel="stylesheet" href="../externals/styles.css">
<title>ReportingConsole</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#FFFFFF">
<center>
<jsp:include flush='true' page="ui/reportingToolbar.jsp"/>
<div class="rptmid">
<%

  String action = (String) request.getAttribute("action");
  if(action==null) request.getParameter("action");
  CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
  String userType = appUser.getUser().getUserTypeCd();
  if(RefCodeNames.USER_TYPE_CD.ADMINISTRATOR.equals(userType) ||
     RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(userType) ||
     RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR.equals(userType) ||
     RefCodeNames.USER_TYPE_CD.REPORTING_USER.equals(userType) ||
     RefCodeNames.USER_TYPE_CD.CRC_MANAGER.equals(userType) ||
     RefCodeNames.USER_TYPE_CD.CUSTOMER_SERVICE.equals(userType)
  ) {
   String changeRuleSet = request.getParameter("scheduleRuleChange");
   if(changeRuleSet==null) changeRuleSet = "";
   if ("init".equals(action)||
       "Set Schedule Filter".equals(action)||
       "Clear Schedule Filter".equals(action)||
       "Delete Selected Schedules".equals(action)||
       "sort".equals(action)
  ){
%>
<jsp:include flush='true' page="reportScheduleBody.jsp"/>
<% } else { %>
<jsp:include flush='true' page="reportScheduleDetail.jsp"/>
<% }} %>

</div>
<jsp:include flush='true' page="/console/ui/consoleFooter.jsp"/>
</center>
</body>

<% String  errorMessage = (String) request.getAttribute("errorMessage");
  if(errorMessage!=null) {

%>
<script language="javascript">
alert("<%=errorMessage%>");
</script>
<%
}
%>

</html:html>




