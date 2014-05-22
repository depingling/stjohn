<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>


<% String storeDir=ClwCustomizer.getStoreDir(); %>
<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<html:html>
<head>
<title><template:getAsString name="title" ignore="true"/></title>
  <META HTTP-EQUIV="Content-Type" CONTENT="text/html;charset=utf-8">
  <meta http-equiv="Pragma" content="no-cache">
  <META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE">
  <meta http-equiv="Expires" content="-1">
  <jsp:include flush='true' page="<%=ClwCustomizer.getStoreFilePath(request, \"htmlHeaderIncludes.jsp\")%>"/>
</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" bgcolor="white">

<div>
  <template:useAttribute id="headerA" name="header" ignore="true" classname="java.lang.String"/>
  <template:useAttribute id="headerTxtA" name="headerText" ignore="true" classname="java.lang.String"/>
  <logic:present name="headerA">
    <template:get name="header" flush="true" ignore="true"/>
  </logic:present>
  <logic:present name="headerTxtA">
    <bean:write name="headerTxtA" filter="false"/>
  </logic:present>
</div>

<div>
<table width="100%" border="0" cellspacing="0" cellpadding="0">

<tr> <td width="100%" valign="top" align="center">

        <table align="center" border="0"
          cellpadding="0" cellspacing="0" width="<%=Constants.TABLEWIDTH%>">

        <tr>
                <td class="tableoutline" width="1" bgcolor="black">
                  <img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="1" width="1">
                </td>

                <td>
		  <template:useAttribute id="shopToolbarA" name="shopToolbar" ignore="true" classname="java.lang.String"/>
		  <template:useAttribute id="shopToolbarTxtA" name="shopToolbarText" ignore="true" classname="java.lang.String"/>
		  <logic:present name="shopToolbarA">
		    <template:get name="shopToolbar" flush="true" ignore="true"/>
		  </logic:present>
		  <logic:present name="shopToolbarTxtA">
		    <bean:write name="shopToolbarTxtA" filter="false"/>
		  </logic:present>
                </td>

                <td class="tableoutline" width="1" bgcolor="black">
                  <img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="1" width="1">
                </td></tr>
        </table>
</td> </tr>

<tr> <td width="100%" valign="top" align="center">
  <template:useAttribute id="contentA" name="content" ignore="true" classname="java.lang.String"/>
  <template:useAttribute id="contentTxtA" name="contentText" ignore="true" classname="java.lang.String"/>
  <logic:present name="contentA">
    <template:get name="content" flush="true" ignore="true"/>
  </logic:present>
  <logic:present name="contentTxtA">
    <bean:write name="contentTxtA" filter="false"/>
  </logic:present>
</td> </tr>

</table>
</div>

<div>
<table align="center" border="0"
  cellpadding="0" cellspacing="0" width="<%=Constants.TABLEWIDTH%>"
>
<tr><td>
  <template:useAttribute id="footerA" name="footer" ignore="true" classname="java.lang.String"/>
  <template:useAttribute id="footerTxtA" name="footerText" ignore="true" classname="java.lang.String"/>
  <logic:present name="footerA">
    <template:get name="footer" flush="true" ignore="true"/>
  </logic:present>
  <logic:present name="footerTxtA">
    <bean:write name="footerTxtA" filter="false"/>
  </logic:present>
</td></tr>
</table>
</div>

</body>
</html:html>
