<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>

<title><app:storeMessage key="shop.heading.shoppingCart"/></title>
<jsp:include flush='true' page="<%=ClwCustomizer.getStoreFilePath(request, \"../userportal/htmlHeaderIncludes.jsp\")%>"/>
<jsp:include flush='true' page="../userportal/htmlHeaderIncludes.jsp"/>
</head>
<body marginheight="0" topmargin="0" marginwidth="0" leftmargin="0">
<table id="PageTable" align="center" border="0" cellpadding="0" cellspacing="0" height="100%">
<tr valign="top">
<td width="20" background='<%=ClwCustomizer.getSIP(request,"leftBorder.gif")%>'>&nbsp;</td>
<td bgcolor="white">&nbsp;&nbsp;&nbsp;</td>
<td bgcolor="white"> 

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<%
/*
index.jsp needs to be moved for this to work.
<app:checkLogon/>
*/
%>
<table border="0" cellpadding="0" cellspacing="0" ><tr valign="top"><td>
<%

String hs = ClwCustomizer.getStoreFilePath(request,
  "t_cwHeader.jsp"); 
String fs = ClwCustomizer.getStoreFilePath(request, "t_footer.jsp");

String lscart = ClwCustomizer.getStoreFilePath(request,
  "shoppingCartTemplate.jsp");
%>

<jsp:include flush='true' page="<%=hs%>"/>
<jsp:include flush='true' page="<%=lscart%>"/>
<jsp:include flush='true' page="<%=fs%>"/>
</td></tr></table>
</td> 
<td bgcolor="white">&nbsp;&nbsp;&nbsp;</td>
<td width="20" background='<%=ClwCustomizer.getSIP(request,"rightBorder.gif")%>'>&nbsp;</td>
</tr>
</table>

</body>
</html>






