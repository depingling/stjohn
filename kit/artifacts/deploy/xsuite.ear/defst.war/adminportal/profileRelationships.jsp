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
<title>Application Administrator Home: Profiling</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#FFFFFF">

<jsp:include flush='true' page="ui/admToolbar.jsp"/>
<jsp:include flush='true' page="ui/admProfilingToolbar.jsp"/>
<jsp:include flush='true' page="ui/loginInfo.jsp"/>


<div class="text">
<font color=red>
<html:errors/>
</font>

<table> <tr>
<html:form name="RELATED_FORM" action="adminportal/related.do"
    method="POST"
    scope="session" type="com.cleanwise.view.forms.RelatedForm">
<td><b>Find Relationships:</b></td>
<td>
<html:text name="RELATED_FORM" property="searchForName" size="10"/>
<html:select name="RELATED_FORM" property="searchForType">
<html:options collection="Related.options.vector"
property="label"
labelProperty="value"
/>
</html:select>
</td>
</tr>
<%--
(Not implemented)
<tr>
        <td><b>Search By:</b></td>
        <td colspan="3">
          <html:radio name="RELATED_FORM" property="searchType" value="nameBegins" />
          Name(starts with)
          <html:radio name="RELATED_FORM" property="searchType" value="nameContains" />
          Name(contains)
        </td>
</tr>
--%>

<html:hidden property="action" value="profiling"/>

<tr>
<td></td>
<td colspan="3">
<html:submit property="viewAll">
<app:storeMessage  key="global.action.label.search"/>
</html:submit>
<html:submit property="viewAll">
<app:storeMessage  key="admin.button.viewall"/>
</html:submit>
</td>
</tr>

</html:form>

</table>

<%/****************Display Found Accounts****************/%>
<logic:present name="Related.profileing.accounts.vector">
<bean:size id="rescount"  name="Related.profileing.accounts.vector"/>
Search result count:  <bean:write name="rescount" />
<logic:greaterThan name="rescount" value="0">
<table width="769"  class="results">
<tr align=left>
        <td>Account Id</td>
        <td>Account Name</td>
        <td>Account Status</td>
        <td>Account Erp Num</td>
</tr>

<logic:iterate id="arrele" name="Related.profileing.accounts.vector">
        <tr>
                <td><bean:write name="arrele" property="busEntityId"/></td>
                <td><bean:write name="arrele" property="shortDesc"/></td>
                <td><bean:write name="arrele" property="busEntityStatusCd"/></td>
                <td><bean:write name="arrele" property="erpNum"/></td>
        </tr>
</logic:iterate>
</table>

</logic:greaterThan>
</logic:present>
<%/****************End Display Found Accounts****************/%>


<jsp:include flush='true' page="ui/admFooter.jsp"/>

</body>

</html:html>
