<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<app:checkLogon/>

<%
request.setAttribute("homePageFrameJspName", "t_homePageFramePreview.jsp");
String hs = ClwCustomizer.getStoreFilePath(request, "t_cwHeader.jsp");
String cs = ClwCustomizer.getStoreFilePath(request, "t_msbSitesBody.jsp");
String fs = ClwCustomizer.getStoreFilePath(request, "t_footer.jsp");
String ft = "<table align=center CELLSPACING=0 CELLPADDING=2 width=\"800\" border=0>" +
            "<tr><td bgcolor=\"#003399\" width=\"100%\" height=\"1\"></td></tr>" +
            "<tr><td width=\"100%\" align=center bgcolor=#003399> " +
            "<form><input type=\"button\" value=\"Close Preview Window\" onClick=\"window.close()\"></form>" +
            "<tr><td bgcolor=\"#003399\" width=\"100%\" height=\"1\"></td></tr>" +
            "</table>";
%>

<template:insert template="<%=ClwCustomizer.getStoreFilePath(request, \"cwTemplate.jsp\")%>">
  <template:put name="title">
      <app:custom pageElement="pages.title"/>
  </template:put>
  <template:put name="header"  content="<%=hs%>" />
  <template:put name="content" content="<%=cs%>" />
  <template:put name="footer"  content="<%=fs%>" />
  <template:put name="footerText"  content="<%=ft%>" />

</template:insert>

