<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer"%>
<%@ page import="com.cleanwise.view.utils.Constants"%>

<%@ taglib uri='/WEB-INF/application.tld' prefix='app' %>
<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='template' %>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%
//override the default body tag
StringBuilder body = new StringBuilder(125);
body.append("<body onload=\"");
//internationalize any date pickers on the screen via the body onload event.
//Note - the internationalizeDatePickers method is in scripts.js.
body.append(Utility.getDatePickerInitializationCalls(ClwI18nUtil.getUserLocale(request), , ClwI18nUtil.getCountryDateFormat(request)));
//initialize the multiple date picker on this page
body.append("initializeDatePicker();");
//body.append("$('#multiInlinePicker').datepick({ multiSelect: 3, monthsToShow: 3, monthsToStep: 3, prevText: 'Prev months', nextText: 'Next months', 'disable', 'show'});");
body.append("\">"); 
%>

<%
String header = ClwCustomizer.getStoreFilePath(request, Constants.PORTAL_ESW, "header.jsp");
%>
<%
String content = ClwCustomizer.getStoreFilePath(request, Constants.PORTAL_ESW, "contentOrdersShowSchedules.jsp");
%>
<%
String footer = ClwCustomizer.getStoreFilePath(request, Constants.PORTAL_ESW, "footer.jsp");
%>

<template:insert template="template.jsp">
  <template:put name="title">
      <%= Constants.COMPANY_NAME %>
  </template:put>
  <template:put name="header" content="<%=header%>" />
  <template:put name="body">
  	<%=body.toString() %>
  </template:put>
  <template:put name="content" content="<%=content%>" />
  <template:put name="footer" content="<%=footer%>" />
</template:insert>