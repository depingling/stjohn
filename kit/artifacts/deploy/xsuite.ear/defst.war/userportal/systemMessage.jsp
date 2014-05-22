<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<app:checkLogon/>
<bean:define id="spath" name="pages.store.templates"/>

<%
String cs = ".." + spath + "/t_systemMessage.jsp";
String fs = ".." + spath + "/t_footer.jsp";
%>
<template:insert template="cwTemplate.jsp">
  <template:put name="title">
      <app:custom pageElement="pages.title"/>
  </template:put>
  <template:put name="content" content="<%=cs%>" />
  <template:put name="footer"  content="<%=fs%>" />
</template:insert>
<font size="1"><center><a href="../userportal/logoff.do">Cancel and Logoff</a></center></font>

