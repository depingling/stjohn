<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<app:checkLogon/>

<%
/*
Get the locale and the store prefix for this shopping request.
*/

String lStoreHeader = 
  ClwCustomizer.getStoreFilePath(request, "t_cwHeader.jsp");
String lStoreToolbar = 
  ClwCustomizer.getStoreFilePath(request, "t_shopToolbar.jsp");
String lStoreFooter = 
  ClwCustomizer.getStoreFilePath(request, "t_footer.jsp");
%>

<template:insert template="storeTemplate.jsp">
  <template:put name="title"> <app:storeMessage key="shop.heading.serviceNA"/>. </template:put>
  <template:put name="header"  content="<%=lStoreHeader%>" />
  <template:put name="shopToolbar"  content="<%=lStoreToolbar%>" />
  <template:put name="content" >
   Not available.
  </template:put>
  <template:put name="footer"  content="<%=lStoreFooter%>" />
</template:insert>
