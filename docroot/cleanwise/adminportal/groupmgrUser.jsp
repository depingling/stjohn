<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<app:checkLogon/>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<html:html>

<head>
<link rel="stylesheet" href="../externals/styles.css">
<title>Application Administrator Home: Groups</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#FFFFFF">

<jsp:include flush='true' page="ui/admToolbar.jsp"/>
<jsp:include flush='true' page="ui/loginInfo.jsp"/>
<jsp:include flush='true' page="ui/admGroupToolbar.jsp"/>


<div class="text">
<font color=red>
<html:errors/>
</font>
<jsp:include flush='true' page="ui/groupInfo.jsp"/>
<table border="0" cellpadding="1" cellspacing="0" width="769" class="mainbody">
<html:form name="GROUP_FORM" action="adminportal/groupmgrUser.do" type="com.cleanwise.view.forms.GroupForm">
        <tr>
                <td colspan="6"><b>Note:</b> Changes do not take effect until the user logs out and logs back in.</td>
        </tr>

        <tr>
                <td colspan="2"><b>Reports Of This Group</b></td>
                <td valign="top" colspan="4">
                        <html:select name="GROUP_FORM" property="reports" multiple="true" size="10">
                                <html:option value=""><app:storeMessage  key="admin.none"/></html:option>
                                <html:options name="Generic.Report.name.vector"/>
                        </html:select>
                </td>
        </tr>
        <tr>
                <td colspan="2"><b>Functions Of This Group</b></td>
                <td valign="top" colspan="4">
<html:select name="GROUP_FORM" property="applicationFunctions" 
  multiple="true" size="10">
<html:option value=""><app:storeMessage  key="admin.none"/></html:option>
<html:options collection="Application.Functions.name.vector" property="value"/>
</html:select>
                </td>
        </tr>

        <tr><td colspan="6">
                <html:hidden property="action" value="user_update"/>
                <html:submit property="action">
                        <app:storeMessage  key="admin.button.submitUpdates"/>
                </html:submit>
        </td></tr>
</html:form>
</table>



<jsp:include flush='true' page="ui/admFooter.jsp"/>

</body>

</html:html>
