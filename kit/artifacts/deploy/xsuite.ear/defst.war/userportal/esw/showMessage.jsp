<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer"%>
<%@ page import="com.cleanwise.view.utils.Constants"%>

<%@ taglib uri='/WEB-INF/application.tld' prefix='app' %>
<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='template' %>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%
//no header for the message window;
%>

<link href="../../externals/esw/message_preview.css" rel="Stylesheet" type="text/css" media="all" />

<%
//override the default <body> tag
StringBuilder body = new StringBuilder(25);
body.append("<body class=");
body.append("\"popUpMedium\"");
body.append(">"); 
%>
<%
String content = ClwCustomizer.getStoreFilePath(request, Constants.PORTAL_ESW, "contentShowMessage.jsp");
%>
<%
//no footer for the message window
%>

<template:insert template="template.jsp">
  <template:put name="title">
      <%= Constants.COMPANY_NAME %>
  </template:put>
  <template:put name="body">
  	<%=body.toString() %>
  </template:put>
  <template:put name="content" content="<%=content%>" />
</template:insert>