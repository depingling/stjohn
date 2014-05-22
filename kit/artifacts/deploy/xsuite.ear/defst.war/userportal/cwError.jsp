<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<bean:define id="spath" name="pages.store.templates"/>
<% 
String fs = ClwCustomizer.getStoreFilePath(request, "t_footer.jsp");
String cs = ClwCustomizer.getStoreFilePath(request, "t_cwErrorBody.jsp");
%>
<link rel="stylesheet" href='../externals/styles.css'>

<template:insert template="cwTemplate.jsp">
  <template:put name="title">
  Problem Report
  </template:put>
  <template:put name="content" content="<%=cs%>" />
  <template:put name="footer"  content="<%=fs%>" />
</template:insert>

