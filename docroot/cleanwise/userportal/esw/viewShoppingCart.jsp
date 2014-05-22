<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer"%>
<%@ page import="com.cleanwise.view.utils.Constants"%>

<%@ taglib uri='/WEB-INF/application.tld' prefix='app' %>
<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='template' %>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%
String header = ClwCustomizer.getStoreFilePath(request, Constants.PORTAL_ESW, "header.jsp");
%>
<%
String content = ClwCustomizer.getStoreFilePath(request, Constants.PORTAL_ESW, "contentViewShoppingCart.jsp");
%>
<%
String footer = ClwCustomizer.getStoreFilePath(request, Constants.PORTAL_ESW, "footer.jsp");
%>

<template:insert template="template.jsp">
  <template:put name="title">
      <%= Constants.COMPANY_NAME %>
  </template:put>
  <template:put name="header" content="<%=header%>" />
  <template:put name="content" content="<%=content%>" />
  <template:put name="footer" content="<%=footer%>" />
</template:insert>