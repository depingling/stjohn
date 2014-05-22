<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<%

 String lprtOrder = 
    ClwCustomizer.getFilePath(session, "t_printerFriendlyOrder.jsp");
 String lStoreFooter = 
    ClwCustomizer.getFilePath(session, "t_footer.jsp");

%>

<template:insert template="storeTemplate.jsp">
  <template:put name="title"> 
    Order Information (Printer version)
  </template:put>
  <template:put name="content" content="<%=lprtOrder%>" />
  <template:put name="footer"  content="<%=lStoreFooter%>" />
</template:insert>
