<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<app:checkLogon/>

<%
String cs = null;

CleanwiseUser appUser = (CleanwiseUser)
  request.getSession().getAttribute(Constants.APP_USER);

String userType = appUser.getUser().getUserTypeCd();

if(appUser.isNoReporting()){
  cs = "t_serviceProviderReportingMgtNoReport.jsp";
}
else{
  cs = "t_serviceProviderReportingMgtBody.jsp";
}

%>

<bean:define id="toolBarTab" type="java.lang.String"  value="default" toScope="request"/>

<template:insert template="serviceProviderTemplate.jsp">
    <template:put name="header"  content="t_cwHeader.jsp" />
    <template:put name="tabBar01"  content="f_serviceProviderReportingMgtToolbar.jsp" />
    <template:put name="content" content="<%=cs%>"/>
    <template:put name="footer"  content="../store/t_footer.jsp" />     
</template:insert>

