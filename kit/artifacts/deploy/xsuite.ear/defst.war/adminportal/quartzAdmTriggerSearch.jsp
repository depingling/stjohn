<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="java.util.Locale" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<app:setLocaleAndImages/>
<app:checkLogon/>

<bean:define id="Location" value="none" type="java.lang.String" toScope="session"/>
<bean:define id="theForm" name="QUARTZ_ADM_CONFIG_FORM" type="com.cleanwise.view.forms.QuartzAdmMgrForm"/>

<html:html>
<head>
    <link rel="stylesheet" href="../externals/styles.css">
    <style>
        .tt {
            color: white;
            background-color: black;
        }

        .tt1 {
            border-right: solid 1px black;
        }

    </style>
    <title>Quartz Administrator</title>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<script language="JavaScript1.2" >
function triggerSearch() {
  document.getElementsByName("userAction")[0].value = "triggerSearch";
  document.forms[0].action += "triggerSearch";
  document.forms[0].submit();
  return false;
}
</script>

</head>

<body>
<%
    String action = request.getParameter("action");
    if (action == null) {
        action = "init";
    }
%>

<table border=0 width="769" cellpadding="0" cellspacing="0">
    <tr>
        <td>
            <jsp:include flush='true' page="ui/systemToolbar.jsp"/>
        </td>
    </tr>
    <tr>
        <td>
            <jsp:include flush='true' page="ui/loginInfo.jsp"/>
        </td>
    </tr>
    <tr>
        <td>
            <jsp:include flush='true' page="ui/admQuartzToolbar.jsp"/>
        </td>
    </tr>
</table>

<table bgcolor="#cccccc" width="769">

    <html:form
            action="/adminportal/quartzAdm.do?action="
            enctype="multipart/form-data">
<html:hidden property="userAction"/>

        <tr>
            <td align="left">
                Trigger Name:
            </td>
            <td align="left">
                <html:text name="QUARTZ_ADM_CONFIG_FORM" property="searchTriggerName"/>
            </td>
            <td align="left">
                Trigger Group:
            </td>
            <td align="left">
                <html:text name="QUARTZ_ADM_CONFIG_FORM" property="searchTriggerGroup"/>
            </td>
        </tr>

        <tr>
            <td align="left">
                Job Name:
            </td>
            <td align="left">
                <html:text name="QUARTZ_ADM_CONFIG_FORM" property="searchTriggerJobName"/>
            </td>
            <td align="left">
                Job Group:
            </td>
            <td align="left">
                <html:text name="QUARTZ_ADM_CONFIG_FORM" property="searchTriggerJobGroup"/>
            </td>
        </tr>
<%--
        <tr>
            <td align="left">
                Description:
            </td>
            <td align="left" colspan="3">
                <html:text name="QUARTZ_ADM_CONFIG_FORM" property="searchTriggerDesc" size="84"/>
            </td>
        </tr>
--%>
        <tr>
            <td>
              <html:button value="Search" onclick="triggerSearch();" property="buttonSearchTrigger" style="width:90px;"/>
            </td>
            <td>

            </td>
        </tr>
    </html:form>

</table>

<table bgcolor="#cccccc" width="769">

    <tr>
        <td>Name</td>
        <td>Group</td>
        <td>Job Name</td>
        <td>Job Group</td>
        <td>Last Fire</td>
        <td>Fires Next</td>
        <td>Description</td>
        <td colspan="2"/>
    </tr>

    <logic:iterate id="trigger" name="QUARTZ_ADM_CONFIG_FORM" property="searchTriggers" indexId="i">
        <tr>
            <td><bean:write name="trigger" property="name"/></td>
            <td><bean:write name="trigger" property="group"/></td>
            <td><bean:write name="trigger" property="jobName"/></td>
            <td><bean:write name="trigger" property="jobGroup"/></td>
            <td>
              <logic:present name="trigger" property="previousFireTime">
                <bean:define id="prev" name="trigger" property="previousFireTime"/>
                <i18n:formatDate value="<%=prev%>" pattern="MM/dd/yyyy HH:mm:ss" locale="<%=Locale.US%>"/>
              </logic:present>
            </td>
            <td>
              <logic:present name="trigger" property="nextFireTime">
                <bean:define id="next" name="trigger" property="nextFireTime"/>
                <i18n:formatDate value="<%=next%>" pattern="MM/dd/yyyy HH:mm:ss" locale="<%=Locale.US%>"/>
              </logic:present>
            </td>
            <td><bean:write name="trigger" property="description"/></td>
            <td>
                <a href="quartzAdm.do?action=cronTriggerAddEdit&fullName=<bean:write name="trigger" property="fullName"/>">Edit</a>
            </td>

            <td>
                <a href="quartzAdm.do?action=triggerSearch&func=delete&fullName=<bean:write name="trigger" property="fullName"/>">Delete</a>
            </td>
        </tr>
    </logic:iterate>
</table>
    <jsp:include flush='true' page="ui/admFooter.jsp"/>
</body>
</html:html>
