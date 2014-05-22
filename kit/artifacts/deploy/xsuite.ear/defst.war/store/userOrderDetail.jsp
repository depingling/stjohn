<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.forms.OrderOpDetailForm" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<app:checkLogon/>
 <bean:define id="theForm" name="ORDER_OP_DETAIL_FORM"
    type="com.cleanwise.view.forms.OrderOpDetailForm"/>
<%
    String lStoreHeader =
      ClwCustomizer.getStoreFilePath(request, "t_cwHeader.jsp");
    String lStoreToolbar =
        ClwCustomizer.getStoreFilePath(request, "t_shopToolbar.jsp");
    String lStoreFooter =
        ClwCustomizer.getStoreFilePath(request, "t_footer.jsp");
    String lhandleOrder;
    if (theForm != null && ((OrderOpDetailForm) theForm).getSimpleServiceOrderFl())
    {
       lhandleOrder  = ClwCustomizer.getStoreFilePath(request, "t_userServiceOrderDetail.jsp");
    } else {
    lhandleOrder  =    ClwCustomizer.getStoreFilePath(request, "t_userOrderDetail.jsp");;
    }
%>

<template:insert template="<%=ClwCustomizer.getStoreFilePath(request, \"storeTemplate.jsp\")%>">
  <template:put name="title"> <app:storeMessage key="shop.heading.shopOrderStatus"/> </template:put>
  <template:put name="header"  content="<%=lStoreHeader%>" />
  <template:put name="shopToolbar"  content="<%=lStoreToolbar%>" />
  <template:put name="content" content="<%=lhandleOrder%>" />
  <template:put name="footer"  content="<%=lStoreFooter%>" />
</template:insert>
