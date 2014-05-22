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
<title>Store Account Data Configuration Options </title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body>
<html:form action="adminportal/storeAccountData.do" name="STORE_DETAIL_FORM"
scope="session" type="com.cleanwise.view.forms.StoreMgrDetailForm">
<jsp:include flush='true' page="ui/admToolbar.jsp"/>
<jsp:include flush='true' page="ui/admStoreToolbar.jsp"/>
<jsp:include flush='true' page="ui/loginInfo.jsp"/>
<jsp:include flush='true' page="ui/storeInfo.jsp"/>

<div class="text">


<table width="769" class="results" border="0">

<tr>
<td>
Show in Admin
</td>
<td>

</td>
<td>
Field Id
</td>
<td>
Field name
</td>
</tr>

<tr>
<td>
<html:checkbox name="STORE_DETAIL_FORM" property="busEntityFieldsData.f1ShowAdmin"/>
</td>
<td>
<%--<html:checkbox name="STORE_DETAIL_FORM" property="busEntityFieldsData.f1ShowRuntime"/>--%>
</td>
<td>
Field 1
</td>
<td>
<html:text name="STORE_DETAIL_FORM" property="busEntityFieldsData.f1Tag"/>
</td>
</tr>

<tr>
<td>
<html:checkbox name="STORE_DETAIL_FORM" property="busEntityFieldsData.f2ShowAdmin"/>
</td>
<td>
<%--<html:checkbox name="STORE_DETAIL_FORM" property="busEntityFieldsData.f2ShowRuntime"/>--%>
</td>
<td>
Field 2
</td>
<td>
<html:text name="STORE_DETAIL_FORM" property="busEntityFieldsData.f2Tag"/>
</td>
</tr>

<tr>
<td>
<html:checkbox name="STORE_DETAIL_FORM" property="busEntityFieldsData.f3ShowAdmin"/>
</td>
<td>
<%--<html:checkbox name="STORE_DETAIL_FORM" property="busEntityFieldsData.f3ShowRuntime"/>--%>
</td>
<td>
Field 3
</td>
<td>
<html:text name="STORE_DETAIL_FORM" property="busEntityFieldsData.f3Tag"/>
</td>
</tr>

<tr>
<td>
<html:checkbox name="STORE_DETAIL_FORM" property="busEntityFieldsData.f4ShowAdmin"/>
</td>
<td>
<%--<html:checkbox name="STORE_DETAIL_FORM" property="busEntityFieldsData.f4ShowRuntime"/>--%>
</td>
<td>
Field 4
</td>
<td>
<html:text name="STORE_DETAIL_FORM" property="busEntityFieldsData.f4Tag"/>
</td>
</tr>

<tr>
<td>
<html:checkbox name="STORE_DETAIL_FORM" property="busEntityFieldsData.f5ShowAdmin"/>
</td>
<td>
<%--<html:checkbox name="STORE_DETAIL_FORM" property="busEntityFieldsData.f5ShowRuntime"/>--%>
</td>
<td>
Field 5
</td>
<td>
<html:text name="STORE_DETAIL_FORM" property="busEntityFieldsData.f5Tag"/>
</td>
</tr>

<tr>
<td colspan=4 align=center>
<html:submit property="action">
<app:storeMessage  key="global.action.label.save"/>
</html:submit>
</td>
</tr>

</table>

<jsp:include flush='true' page="ui/admFooter.jsp"/>

</div>
</html:form>
</body>
</html:html>





