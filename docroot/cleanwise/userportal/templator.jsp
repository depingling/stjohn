<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<app:checkLogon/>
<bean:define id="spath" name="pages.store.templates"/>
<%
/* Get request vars  */

String tabPage = (String)request.getParameter("tabs");

if(tabPage != null && !(tabPage.indexOf(".")>0)){tabPage+=".jsp";}
String display = (String)request.getParameter("display");
String suffix = "";
int paramInd = display.indexOf("?");
if(paramInd>0) {
   suffix = display.substring(paramInd);
   display = display.substring(0,paramInd);
}
if(!(display.indexOf(".")>0)){display+=".jsp";}
 
String hs = ClwCustomizer.getStoreFilePath(request,"t_cwHeader.jsp");
String tabs = null;
if(tabPage != null){
    tabs = ClwCustomizer.getStoreFilePath(request,tabPage);
}
String content = ClwCustomizer.getStoreFilePath(request,display);
content += suffix;
String fs = ClwCustomizer.getStoreFilePath(request,"t_footer.jsp");
%>




<template:insert template="cwTemplate.jsp">
  <template:put name="title">
      <app:custom pageElement="pages.title"/>
  </template:put>
  
  <template:put name="simpleContent"  content="true" />
  <template:put name="header"  content="<%=hs%>" />
  <%if(tabs!=null){%>
    <template:put name="tabBar01" content="<%=tabs%>" />
  <%}%>
  <template:put name="content" content="<%=content%>" />
  <template:put name="footer"  content="<%=fs%>" />
</template:insert>


