<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<app:checkLogon/>

<%

String lStoreHeader = 
   ClwCustomizer.getStoreFilePath(request, "t_cwHeader.jsp");
String lStoreToolbar = 
    ClwCustomizer.getStoreFilePath(request, "t_shopToolbar.jsp");
String lStoreFooter = 
    ClwCustomizer.getStoreFilePath(request, "t_footer.jsp");

String lhandleOrder         = 
    ClwCustomizer.getStoreFilePath(request, "t_orderDetail.jsp");

%>

<template:insert template="<%=ClwCustomizer.getStoreFilePath(request, \"storeTemplate.jsp\")%>">
  <template:put name="title"> <app:storeMessage key="shop.heading.shopOrderStatus"/> </template:put>
  <template:put name="header"  content="<%=lStoreHeader%>" />
  <template:put name="shopToolbar"  content="<%=lStoreToolbar%>" />
  <template:put name="content" content="<%=lhandleOrder%>" />
  <template:put name="footer"  content="<%=lStoreFooter%>" />
</template:insert>
