
<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<html:html>
<head>

<title><app:storeMessage key="shop.heading.editOrderGuide"/></title>
<jsp:include flush='true' 
  page='<%=ClwCustomizer.getStoreFilePath(request, "userportal","htmlHeaderIncludes.jsp")%>'/>
</head>
<body marginheight="0" topmargin="0" marginwidth="0" leftmargin="0">

<%
/*
index.jsp needs to be moved for this to work.
<app:checkLogon/>
*/
%>

<%

 String hs = ClwCustomizer.getStoreFilePath(request, "userportal","cwHeader.jsp");
 String lOrderGuide = ClwCustomizer.getStoreFilePath(request, "editOrderGuideTemplate.jsp");
 String fs = ClwCustomizer.getStoreFilePath(request, "t_footer.jsp");
 
%>
<jsp:include flush='true' page="<%=hs%>"/>
<jsp:include flush='true' page="<%=lOrderGuide%>"/>
<jsp:include flush='true' page="<%=fs%>"/>
</body>

</html:html>






