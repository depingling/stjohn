<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.session.Event" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

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
function jobSearch() {
  document.getElementsByName("userAction")[0].value = "jobSearch";
  document.forms[0].action += "jobSearch";
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
                Job Name:
            </td>
            <td align="left">
                <html:text name="QUARTZ_ADM_CONFIG_FORM" property="searchJobName"/>
            </td>
            <td align="left">
                Job Group:
            </td>
            <td align="left">
                <html:text name="QUARTZ_ADM_CONFIG_FORM" property="searchJobGroup"/>
            </td>
        </tr>

        <tr>
            <td align="left">
                Job Class:
            </td>
            <td align="left">
                <html:select name="QUARTZ_ADM_CONFIG_FORM" property="searchJobClassSelect">
                	<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
                	<html:options collection="jobClassNames" property="value" labelProperty="label"/>
          		</html:select>
            </td>
            <td>Manual Job Class:</td>
            <td align="left">
                <html:text name="QUARTZ_ADM_CONFIG_FORM" property="searchJobClass"/>
            </td>
        </tr>
<%--
        <tr>
            <td align="left">
                Description:
            </td>
            <td align="left" colspan="3">
                <html:text name="QUARTZ_ADM_CONFIG_FORM" property="searchJobDesc" size="84"/>
            </td>
        </tr>
--%>
        <tr>
            <td align="left">
                Parameter Name:
            </td>
            <td align="left">
                <html:text name="QUARTZ_ADM_CONFIG_FORM" property="searchJobParName"/>
            </td>
            <td align="left">
                Parameter Value:
            </td>
            <td align="left">
                <html:text name="QUARTZ_ADM_CONFIG_FORM" property="searchJobParValue"/>
            </td>
        </tr>

        <tr>
            <td>
              <html:button value="Search" onclick="jobSearch();" property="buttonSearchJob" style="width:90px;"/>
            </td>
            <td>

            </td>
        </tr>
    </html:form>

</table>

<table bgcolor="#cccccc" width="769">
	<tr>
        <td colspan="7">Result Count: <%=theForm.getSearchJobs().size()%></td>
    </tr>
    <tr>
        <td></td>
    </tr>
    <tr>
        <td/>
        <td/>
        <td nowrap="nowrap"><a href="quartzAdm.do?action=sort&sortField=getName">Name</a></td>
        <td nowrap="nowrap">Group</td>
        <td nowrap="nowrap"><a href="quartzAdm.do?action=sort&sortField=getJobClass-getName">Class</a></td>
        <td>Description</td>
        <%if (theForm.getSearchJobParName() != null && !"".equals(theForm.getSearchJobParName()) ||
              theForm.getSearchJobParValue() != null && !"".equals(theForm.getSearchJobParValue()) ) {%>
        <td>Parameters</td>
        <%}%>
        <td colspan="2"/>
    </tr>

    <logic:iterate id="job" name="QUARTZ_ADM_CONFIG_FORM" property="searchJobs" indexId="i">
        <tr>
            <td>
                <a href="quartzAdm.do?action=jobSearch&func=start&fullName=<bean:write name="job" property="fullName"/>">Execute</a>
            </td>
            <td>
                <%if ("Active".equals((String)(theForm.getSearchJobStates().get(i.intValue())))) {%>
                <a href="quartzAdm.do?action=jobSearch&func=pause&fullName=<bean:write name="job" property="fullName"/>">Pause</a>
                <%} else if ("Paused".equals((String)(theForm.getSearchJobStates().get(i.intValue())))) {%>
                <a href="quartzAdm.do?action=jobSearch&func=unpause&fullName=<bean:write name="job" property="fullName"/>">Resume</a>
                <%}%>
            </td>
            <td nowrap="nowrap"><bean:write name="job" property="name"/></td>
            <td nowrap="nowrap"><bean:write name="job" property="group"/></td>
            <td nowrap="nowrap"><bean:write name="job" property="jobClass.name"/></td>
            <td><bean:write name="job" property="description"/></td>
            <%if (theForm.getSearchJobParName() != null && !"".equals(theForm.getSearchJobParName()) ||
                  theForm.getSearchJobParValue() != null && !"".equals(theForm.getSearchJobParValue()) ) {%>
            <td>
                <%=theForm.getSearchJobPars().get(i.intValue())%>
            </td>
            <%}%>
            <td>
                <a href="quartzAdm.do?action=jobAddEdit&func=editinit&fullName=<bean:write name="job" property="fullName"/>">Edit</a>
            </td>
        </tr>
    </logic:iterate>
</table>
    <jsp:include flush='true' page="ui/admFooter.jsp"/>
</body>
</html:html>
