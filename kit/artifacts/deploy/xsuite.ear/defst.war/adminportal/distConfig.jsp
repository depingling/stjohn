<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<app:checkLogon/>
<% String storeDir=ClwCustomizer.getStoreDir(); %>

<html:html>
<head>
<title>Distributor Config</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="../externals/styles.css">
</head>
<bean:define id="mainForm" name="DIST_DETAIL_FORM" type="com.cleanwise.view.forms.DistMgrDetailForm"/>
<bean:define id="theForm" name="DIST_CONFIG_FORM" type="com.cleanwise.view.forms.DistMgrConfigForm"/>
<body bgcolor="#cccccc">
<jsp:include flush='true' page="ui/admToolbar.jsp"/>
<jsp:include flush='true' page="ui/admDistToolbar.jsp"/>
<jsp:include flush='true' page="ui/loginInfo.jsp"/>
<jsp:include flush='true' page="distConfigBody.jsp">
   <jsp:param name="portal" 	value="adminportal" /> 
</jsp:include>
<jsp:include flush='true' page="ui/admFooter.jsp"/>
</body>
</html:html>

