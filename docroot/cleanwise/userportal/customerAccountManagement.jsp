<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<app:checkLogon/>

<%

String hs = ClwCustomizer.getStoreFilePath(request, "t_cwHeader.jsp");
String fs = ClwCustomizer.getStoreFilePath(request, "t_footer.jsp");
String tabs = ClwCustomizer.getStoreFilePath(request, "f_custAcctMgtToolbar.jsp");
String cs = null;

CleanwiseUser appUser = (CleanwiseUser)
  request.getSession().getAttribute(Constants.APP_USER);

String userType = appUser.getUser().getUserTypeCd();

if(appUser.isNoReporting()){
  cs = ClwCustomizer.getStoreFilePath(request, "t_custAcctMgtNoReport.jsp");
}
else{
  cs = ClwCustomizer.getStoreFilePath(request, "t_custAcctMgtBody.jsp");
}


%>
<bean:define id="toolBarTab" type="java.lang.String"  value="default" toScope="request"/>

<template:insert template="<%=ClwCustomizer.getStoreFilePath(request, \"cwTemplate.jsp\")%>">
  <template:put name="title">
      <app:custom pageElement="pages.title"/>
  </template:put>
  <template:put name="simpleContent"  content="true" />
  <template:put name="header"  content="<%=hs%>" />
  <template:put name="tabBar01" content="<%=tabs%>" />
  <template:put name="content" content="<%=cs%>" />
  <template:put name="footer"  content="<%=fs%>" />
</template:insert>

