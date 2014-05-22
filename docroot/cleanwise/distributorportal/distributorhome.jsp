<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<app:checkLogon/>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="Location" value="contract" type="java.lang.String" toScope="session"/>
<html:html>

<head>

<link rel="stylesheet" href="../externals/styles.css">
<title>Distributor Portal</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#FFFFFF">
<div class="text">

<jsp:include flush='true' page="ui/distributorToolbar.jsp"/>
<jsp:include flush='true' page="ui/loginInfo.jsp"/>

<table>
<tr><td><b>Managed Distributors:</b></td></tr>
<logic:iterate id='dist' name='<%=Constants.APP_USER%>' property='distributors' type='com.cleanwise.service.api.value.BusEntityData'>
  <tr>
        <td>&nbsp;</td>
        <td><bean:write name='dist' property='shortDesc'/></td>
  </tr>
</logic:iterate>
</table>

</div>
<jsp:include flush='true' page="ui/distributorFooter.jsp"/>
</body>

</html:html>