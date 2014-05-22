
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

<title><app:storeMessage key="shop.heading.orderScheduler"/></title>
<jsp:include flush='true' 
  page='<%=ClwCustomizer.getStoreFilePath(request,"userportal","htmlHeaderIncludes.jsp")%>'/>
</head>
<body marginheight="0" topmargin="0" marginwidth="0" leftmargin="0">

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<%
/*
index.jsp needs to be moved for this to work.
<app:checkLogon/>
*/
%>

<%
/*
Get the locale and the store prefix for this shopping request.
*/

 String storePrefix = (String) session.getAttribute("pages.store.prefix");
 String lOrderSch = ClwCustomizer.getStoreFilePath(request,"orderSchedulerTemplate.jsp");
%>
<jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"userportal","cwHeader.jsp")%>'/>
<jsp:include flush='true' page="<%= lOrderSch %>"/>
<jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"t_footer.jsp")%>'/>
</body>
</html:html>







