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
<bean:define id="theForm" name="SPENDING_ESTIMATOR_FORM" type="com.cleanwise.view.forms.SpendingEstimatorForm"/>
<html:html>

<head>
<link rel="stylesheet" href="../externals/styles.css">
<title>ReportingConsole</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#FFFFFF">
<center>
<div>
<% if(theForm.getReportId()<=0) {%>
<jsp:include flush='true' page="estimatorRepBody.jsp"/>
<% } else { %>
<jsp:include flush='true' page="estimatorRepRequest.jsp"/>
<% } %>
</div>
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




