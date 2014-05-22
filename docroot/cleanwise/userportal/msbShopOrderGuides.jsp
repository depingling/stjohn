<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<app:checkLogon/>
<bean:define id="spath" name="pages.store.templates"/>
<% 
String hs = ".." + spath + "/t_cwHeader.jsp"; 
String cs = ".." + spath + "/t_msbShopOGBody.jsp";
String fs = ".." + spath + "/t_footer.jsp";
%>
<template:insert template="cwTemplate.jsp">
  <template:put name="title">
      <app:custom pageElement="pages.title"/>
  </template:put>
  <template:put name="header"  content="<%=hs%>" />
  <template:put name="content" content="<%=cs%>" />
  <template:put name="footer"  content="<%=fs%>" />
</template:insert>


