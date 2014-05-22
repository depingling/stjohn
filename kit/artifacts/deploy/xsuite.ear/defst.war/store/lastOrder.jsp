
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

<title><app:storeMessage key="shop.heading.lastOrder"/> </title>
<jsp:include flush='true' page="../userportal/htmlHeaderIncludes.jsp"/>
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
/*
 String storePrefix = (String) session.getAttribute("pages.store.prefix");
 storePrefix = ""; //Overwrite for a while. Use default pages
 String loc = ".";
 String lStoreHeader = loc + storePrefix + "/" + "t_cwHeader.jsp";
 String lLastOrder = loc + "/" + storePrefix + "/" + "lastOrderTemplate.jsp";
 String fs = loc + "/" + storePrefix + "/" + "t_footer.jsp";
 */
String lStoreHeader =
    ClwCustomizer.getStoreFilePath(request, "t_cwHeader.jsp");
String lLastOrder =
  ClwCustomizer.getStoreFilePath(request, "lastOrderTemplate.jsp");
String fs =
  ClwCustomizer.getStoreFilePath(request, "t_footer.jsp");
%>


<bean:define id="shopLoc" value="lastOrder"
  type="java.lang.String" toScope="request"/>
<jsp:include flush='true' page="<%=lStoreHeader%>"/>
<jsp:include flush='true' page="<%=lLastOrder%>"/>
<jsp:include flush='true' page="<%=fs%>"/>
</body>
</html>






