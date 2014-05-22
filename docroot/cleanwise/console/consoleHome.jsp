<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<app:setLocaleAndImages/>
<app:checkLogon/>
<bean:define id="Location" value="none" type="java.lang.String" toScope="session"/>
<html:html>
<head>
<link rel="stylesheet" href="../externals/styles.css">
<title>Operations Console Home</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#FFFFFF">
<table border=0 width="769" cellpadding="0" cellspacing="0">
<tr>
  <td>
    <jsp:include flush='true' page="ui/consoleToolbar.jsp"/>
  </td>
</tr>
<tr>
  <td>
    <jsp:include flush='true' page="ui/loginInfo.jsp"/>
  </td>
</tr>
<tr>
  <td align="center" colspan="2"><br><b>Cleanwise Customer Service start page.</b></td>
</tr>
<tr>
  <td colspan="2"><b>Managed Stores</b></td>
</tr>
    <logic:iterate id='store' name='<%=Constants.APP_USER%>' property='stores' type='com.cleanwise.service.api.value.BusEntityData'>
	  <tr>
		<td><a href="consolehome.do?action=changeStore&id=<bean:write name='store' property='busEntityId'/>"><bean:write name='store' property='shortDesc'/></a></td>
	  </tr>
	</logic:iterate>
<tr>
  <td>
    
    <jsp:include flush='true' page="ui/consoleFooter.jsp"/>
  </td>
</tr>
</table>

</body>
</html:html>
