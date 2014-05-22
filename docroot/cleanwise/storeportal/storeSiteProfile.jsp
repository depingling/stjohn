<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="java.util.Locale" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>


<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<html:html>

<head>
<link rel="stylesheet" href="../externals/styles.css">
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body>

<div class="text">
<table ID=1287 width="769"  class="mainbody">
<tr>
<td><b>Account&nbsp;Id:</b></td>
<td><bean:write name="STORE_ADMIN_SITE_FORM" property="accountId" scope="session"/></td>
<td><b>Account&nbsp;Name:</b></td>
<td><bean:write name="STORE_ADMIN_SITE_FORM" property="accountName" scope="session"/></td>
</tr>
<tr>
<td><b>Site&nbsp;Id:</b> </td>
<td><bean:write name="STORE_ADMIN_SITE_FORM" property="id" scope="session"/></td>
<td><b>Site&nbsp;Name:</b></td>
<td><bean:write name="STORE_ADMIN_SITE_FORM" property="name" scope="session"/></td>
</tr>
</table>
<jsp:include flush='true' page="storeSiteProfileInc.jsp">
    <jsp:param name="detailHref" value="siteprofdet.do" />
</jsp:include>
</div>
</body>

</html:html>
