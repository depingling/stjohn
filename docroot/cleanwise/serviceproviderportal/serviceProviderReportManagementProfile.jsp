<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<app:checkLogon/>

<bean:define id="spath" name="pages.store.templates"/>
<%

String cs = "t_serviceProviderReportingMgtProf.jsp";

%>

<bean:define id="toolBarTab" type="java.lang.String" value="profile" toScope="request"/>

<template:insert template="serviceProviderTemplate.jsp">
    <template:put name="header"  content="t_cwHeader.jsp" />
    <template:put name="tabBar01"  content="f_serviceProviderReportingMgtToolbar.jsp" />
    <template:put name="content" content="<%=cs%>"/>
    <template:put name="footer"  content="../store/t_footer.jsp" />     
</template:insert>




