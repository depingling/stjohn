<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<app:checkLogon/>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="Location" value="contract" type="java.lang.String" toScope="session"/>
<bean:define id="theForm" name="GENERIC_REPORT_FORM" type="com.cleanwise.view.forms.GenericReportForm"/>
<html:html>

<head>
<link rel="stylesheet" href="../externals/styles.css">
<title>Generic Report Service</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#cccccc">


<jsp:include flush='true' page="../adminportal/ui/systemToolbar.jsp"/>
<%   
   String action = (String) request.getAttribute("action");
   if(action==null) request.getParameter("action");
%>
<% if(theForm.getReportId()<=0) {%>
<jsp:include flush='true' page="genericRepBody.jsp"/>
<% } else { %>
<jsp:include flush='true' page="genericRepDetail.jsp"/>
<% } %>
<jsp:include flush='true' page="/console/ui/consoleFooter.jsp"/>

</body>


</html:html>




