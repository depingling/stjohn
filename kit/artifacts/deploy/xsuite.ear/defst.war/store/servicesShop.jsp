<%--

  Title:        servicesShop.jsp
  Description:  builder
  Purpose:      builds jsp for the services shopping
  Copyright:    Copyright (c) 2006
  Company:      CleanWise, Inc.
  Date: 19.01.2007
  Time: 1:27:02
  author        Alexander Chickin, TrinitySoft, Inc.
--%>
<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<app:checkLogon/>
<html language='ru'>
<head>

<title><app:storeMessage key="shop.heading.assetService"/></title>
  <meta http-equiv="Pragma" content="no-cache">
  <META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE">
  <meta http-equiv="Expires" content="-1">
  <jsp:include flush='true' page="/userportal/htmlHeaderIncludes.jsp"/>
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
String hs = ClwCustomizer.getStoreFilePath(request,  "t_cwHeader.jsp");
String fs = ClwCustomizer.getStoreFilePath(request,  "t_footer.jsp");
String cs = ClwCustomizer.getStoreFilePath(request,  "t_userAssetServiceDetail.jsp");
%>


<%

  String action = request.getParameter("action");
  if(action==null ) { action = "init"; }

  if(action.startsWith("asset")) {  %>

  <jsp:include flush='true' page="<%=hs%>"/>
  <jsp:include flush='true' page="<%=cs%>"/>
<% } %>

<jsp:include flush='true' page="<%=fs%>"/>

</body>
</html>