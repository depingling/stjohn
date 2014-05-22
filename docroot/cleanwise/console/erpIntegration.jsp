<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<app:checkLogon/>

<bean:define id="theForm" name="ERP_INTEGRATION_FORM" type="com.cleanwise.view.forms.ErpIntegrationForm"/>
<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="Location" value="contract" type="java.lang.String" toScope="session"/>
<html:html>

<head>
<link rel="stylesheet" href="../externals/styles.css">
<title>Operations Console: Orders</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#FFFFFF">

<jsp:include flush='true' page="ui/consoleToolbar.jsp"/>
<jsp:include flush='true' page="ui/loginInfo.jsp"/>
<%
 int function = theForm.getFunction();
 if(function==com.cleanwise.view.forms.ErpIntegrationForm.CUST_ORDER_TO_ERP) {
%>
 <jsp:include flush='true' page="erpIntegrationOrderToBody.jsp"/>
<%
 } else if(function==com.cleanwise.view.forms.ErpIntegrationForm.DIST_PO_FROM_ERP) {
%>
 <jsp:include flush='true' page="erpIntegrationOrderFromBody.jsp"/>
<%
 } else if(function==com.cleanwise.view.forms.ErpIntegrationForm.DIST_INVOICE_TO_ERP) {
%>
 <jsp:include flush='true' page="erpIntegrationInvoiceToBody.jsp"/>
<%
 } else if(function==com.cleanwise.view.forms.ErpIntegrationForm.CUST_INVOICE_FROM_ERP) {
%>
 <jsp:include flush='true' page="erpIntegrationInvoiceFromBody.jsp"/>
<%
 } else if(function==com.cleanwise.view.forms.ErpIntegrationForm.REMITTANCE_TO_ERP) {
%>
 <jsp:include flush='true' page="erpIntegrationRemittanceToBody.jsp"/>
<%
 }
%>
<jsp:include flush='true' page="ui/consoleFooter.jsp"/>
</body>

</html:html>




