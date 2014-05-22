
<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.OrderGuideData" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<bean:define id="theForm" name="CUST_ACCT_MGT_INVOICE_DETAIL_FORM" type="com.cleanwise.view.forms.CustAcctMgtInvoiceDetailForm"/>

<html:html>
<head>

<title><%=theForm.getInvoiceNumber()%></title>
<jsp:include flush='true' page="../userportal/htmlHeaderIncludes.jsp"/>
</head>
<body marginheight="0" topmargin="0" marginwidth="0" leftmargin="0">

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<%
/*
index.jsp needs to be moved for this to work.
<app:checkLogon/>
*/
%>

<%

 String lStoreOrderGuide =
    ClwCustomizer.getFilePath(session,
    "printerFriendlyInvoicesDetailTemplate.jsp");

%>

    <jsp:include flush='true' page="<%= lStoreOrderGuide %>"/>


</body>
</html:html>






