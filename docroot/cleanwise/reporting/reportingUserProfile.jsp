<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<app:checkLogon/>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="Location" value="contract" type="java.lang.String" toScope="session"/>
<bean:define id="theForm" name="ANALYTIC_REPORT_FORM" type="com.cleanwise.view.forms.AnalyticReportForm"/>
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
<jsp:include flush='true' page="reportingUserProfE.jsp"/>
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







