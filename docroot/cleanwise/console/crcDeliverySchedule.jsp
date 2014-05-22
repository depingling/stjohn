<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<bean:define id="theForm" name="DELIVERY_SCHEDULE_FORM" type="com.cleanwise.view.forms.DeliveryScheduleMgrForm"/>

<app:checkLogon/>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<html:html>
<head>
<link rel="stylesheet" href="../externals/styles.css">
<title>System Administrator: Delivery Schedules</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>
<% String contentPage = "../adminportal/"+theForm.getContentPage(); %>
<body>
<jsp:include flush='true' page="ui/consoleToolbar.jsp"/>
<jsp:include flush='true' page="ui/crcDistToolbar.jsp"/>
<jsp:include flush='true' page="ui/loginInfo.jsp"/>
<jsp:include flush='true' page="<%=contentPage%>">
   <jsp:param name="portal" 	value="console" /> 
</jsp:include>
<jsp:include flush='true' page="ui/consoleFooter.jsp"/>
</body>

</html:html>




