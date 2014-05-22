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

String lStoreCheckout =
    ClwCustomizer.getStoreFilePath(request,"reviewOrder.jsp");

String lStoreFooter = 
    ClwCustomizer.getStoreFilePath(request,"t_footer.jsp");

%>

<template:insert template="storeTemplate.jsp">
  <template:put name="title"> <app:storeMessage key="shop.heading.reviewOrderItems"/> </template:put>
  <template:put name="header"  content="<%=lStoreHeader%>" />
  <template:put name="content" content="<%=lStoreCheckout%>" />
  <template:put name="footer"  content="<%=lStoreFooter%>" />
</template:insert>



