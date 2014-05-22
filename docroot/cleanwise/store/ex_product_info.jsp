<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<%

String lStoreHeader = 
    ClwCustomizer.getStoreFilePath(request, "t_cwHeader.jsp");
String lStoreToolbar = 
    ClwCustomizer.getStoreFilePath(request, "t_shopToolbar.jsp");
String lStoreFooter = 
    ClwCustomizer.getStoreFilePath(request, "t_footer.jsp");

String lInfo        = 
    ClwCustomizer.getStoreFilePath(request, "t_ex_product_info.jsp");

%>

<template:insert template="storeTemplate.jsp">
  <template:put name="title"> <app:storeMessage key="shop.heading.productInfo"/> </template:put>
  <template:put name="header"  content="<%=lStoreHeader%>" />
  <template:put name="shopToolbar"  content="<%=lStoreToolbar%>" />
  <template:put name="content" content="<%=lInfo%>" />
  <template:put name="footer"  content="<%=lStoreFooter%>" />
</template:insert>




