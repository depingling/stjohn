<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<app:checkLogon/>

<template:insert template="serviceProviderTemplate.jsp">
  <template:put name="header"  content="t_cwHeader.jsp" />
  <template:put name="footer"  content="../store/t_footer.jsp" />
</template:insert>
