<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<app:checkLogon/>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<html:html>
<head>
<link rel="stylesheet" href="../externals/styles.css">
<title>Account Site Data Configuration Options </title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body>
<jsp:include flush='true' page="ui/admToolbar.jsp"/>
<jsp:include flush='true' page="ui/admAccountToolbar.jsp"/>
<jsp:include flush='true' page="ui/loginInfo.jsp"/>
<jsp:include flush='true' page="ui/accountCtx.jsp"/>

<div class="text">
<html:form action="adminportal/accountSiteData.do" 
name="SD_CONFIG_FORM"
scope="session" 
type="com.cleanwise.view.forms.SiteFieldsConfigForm">

<bean:define id="BusEntityId" type="java.lang.String" name="Account.id"
  toScope="session"/>
  
<html:hidden name="SD_CONFIG_FORM" property="busEntityId"
  value="<%=BusEntityId%>"/>

<table width="769" class="results" border="1">

<tr>
<th>
Required
</th>
<th>
Show in Admin
</th>
<th>
Show in Runtime
<br>Checkout page.
</th>
<th>
Field Id
</th>
<th>
Field name
</th>
</tr>

<%--Field 1--%>
<tr>
<td>
<html:checkbox name="SD_CONFIG_FORM" property="config.f1Required"/>
</td>
<td>
<html:checkbox name="SD_CONFIG_FORM" property="config.f1ShowAdmin"/>
</td>
<td>
<html:checkbox name="SD_CONFIG_FORM" property="config.f1ShowRuntime"/>
</td>
<td>
Field 1
</td>
<td>
<html:text name="SD_CONFIG_FORM" property="config.f1Tag"/>
</td>
</tr>

<%--Field 2--%>
<tr>
<td>
<html:checkbox name="SD_CONFIG_FORM" property="config.f2Required"/>
</td>
<td>
<html:checkbox name="SD_CONFIG_FORM" property="config.f2ShowAdmin"/>
</td>
<td>
<html:checkbox name="SD_CONFIG_FORM" property="config.f2ShowRuntime"/>
</td>
<td>
Field 2
</td>
<td>
<html:text name="SD_CONFIG_FORM" property="config.f2Tag"/>
</td>
</tr>

<%--Field 3--%>
<tr>
<td>
<html:checkbox name="SD_CONFIG_FORM" property="config.f3Required"/>
</td>
<td>
<html:checkbox name="SD_CONFIG_FORM" property="config.f3ShowAdmin"/>
</td>
<td>
<html:checkbox name="SD_CONFIG_FORM" property="config.f3ShowRuntime"/>
</td>
<td>
Field 3
</td>
<td>
<html:text name="SD_CONFIG_FORM" property="config.f3Tag"/>
</td>
</tr>

<%--Field 4--%>
<tr>
<td>
<html:checkbox name="SD_CONFIG_FORM" property="config.f4Required"/>
</td>
<td>
<html:checkbox name="SD_CONFIG_FORM" property="config.f4ShowAdmin"/>
</td>
<td>
<html:checkbox name="SD_CONFIG_FORM" property="config.f4ShowRuntime"/>
</td>
<td>
Field 4
</td>
<td>
<html:text name="SD_CONFIG_FORM" property="config.f4Tag"/>
</td>
</tr>

<%--Field 5--%>
<tr>
<td>
<html:checkbox name="SD_CONFIG_FORM" property="config.f5Required"/>
</td>
<td>
<html:checkbox name="SD_CONFIG_FORM" property="config.f5ShowAdmin"/>
</td>
<td>
<html:checkbox name="SD_CONFIG_FORM" property="config.f5ShowRuntime"/>
</td>
<td>
Field 5
</td>
<td>
<html:text name="SD_CONFIG_FORM" property="config.f5Tag"/>
</td>
</tr>

<%--Field 6--%>
<tr>
<td>
<html:checkbox name="SD_CONFIG_FORM" property="config.f6Required"/>
</td>
<td>
<html:checkbox name="SD_CONFIG_FORM" property="config.f6ShowAdmin"/>
</td>
<td>
<html:checkbox name="SD_CONFIG_FORM" property="config.f6ShowRuntime"/>
</td>
<td>
Field 6
</td>
<td>
<html:text name="SD_CONFIG_FORM" property="config.f6Tag"/>
</td>
</tr>

<%--Field 7--%>
<tr>
<td>
<html:checkbox name="SD_CONFIG_FORM" property="config.f7Required"/>
</td>
<td>
<html:checkbox name="SD_CONFIG_FORM" property="config.f7ShowAdmin"/>
</td>
<td>
<html:checkbox name="SD_CONFIG_FORM" property="config.f7ShowRuntime"/>
</td>
<td>
Field 7
</td>
<td>
<html:text name="SD_CONFIG_FORM" property="config.f7Tag"/>
</td>
</tr>

<%--Field 8--%>
<tr>
<td>
<html:checkbox name="SD_CONFIG_FORM" property="config.f8Required"/>
</td>
<td>
<html:checkbox name="SD_CONFIG_FORM" property="config.f8ShowAdmin"/>
</td>
<td>
<html:checkbox name="SD_CONFIG_FORM" property="config.f8ShowRuntime"/>
</td>
<td>
Field 8
</td>
<td>
<html:text name="SD_CONFIG_FORM" property="config.f8Tag"/>
</td>
</tr>

<%--Field 9--%>
<tr>
<td>
<html:checkbox name="SD_CONFIG_FORM" property="config.f9Required"/>
</td>
<td>
<html:checkbox name="SD_CONFIG_FORM" property="config.f9ShowAdmin"/>
</td>
<td>
<html:checkbox name="SD_CONFIG_FORM" property="config.f9ShowRuntime"/>
</td>
<td>
Field 9
</td>
<td>
<html:text name="SD_CONFIG_FORM" property="config.f9Tag"/>
</td>
</tr>

<%--Field 10--%>
<tr>
<td>
<html:checkbox name="SD_CONFIG_FORM" property="config.f10Required"/>
</td>
<td>
<html:checkbox name="SD_CONFIG_FORM" property="config.f10ShowAdmin"/>
</td>
<td>
<html:checkbox name="SD_CONFIG_FORM" property="config.f10ShowRuntime"/>
</td>
<td>
Field 10
</td>
<td>
<html:text name="SD_CONFIG_FORM" property="config.f10Tag"/>
</td>
</tr>

<tr>
<td colspan=4 align=center>
<html:submit property="action">
<app:storeMessage  key="global.action.label.save"/>
</html:submit>
 Field data
</td>
</tr>

</table>

<table>

<tr>
<td colspan=2>Checkout options where the site field values
are found in the list below.</td>
</tr>

<tr><th>Field Name</th><th>Field Value</th><th>Option Shown</th>
<th>Mod Date</th><th>Mod By</th>
</tr>

<logic:iterate name="ACCOUNT_DETAIL_FORM"
  property="accountData.checkoutOptions"
  type="ShoppingOptionsData"
  id="sod">
<tr>
 <td><bean:write name="sod" property="fieldName"/></td>
 <td><bean:write name="sod" property="fieldValue"/></td>
 <td><bean:write name="sod" property="optionValue"/></td>
 <td><bean:write name="sod" property="modDate"/></td>
 <td><bean:write name="sod" property="modBy"/></td>
</tr>

</logic:iterate>

</table>

</html:form>
  

<jsp:include flush='true' page="ui/admFooter.jsp"/>

</div>
</body>
</html:html>





