<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>

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
    </head>

    <body>
    <%
        String action = request.getParameter("action");
        if (action==null) {
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
    <%
        if("init".equals(action)){
    %>
    <table bgcolor="#cccccc" width="769">
        <html:form
                action="/adminportal/quartzAdm.do"
                enctype="multipart/form-data">

        <tr>
            <td align="left" colspan="2">
                Name:
            </td>
            <td align="left" colspan="2">
                <bean:write name="QUARTZ_ADM_CONFIG_FORM" property="schedulerName"/>
            </td>
        </tr>
        <tr>
            <td align="left" colspan="2">
                State:
            </td>
            <td align="left" colspan="2">
                <bean:write name="QUARTZ_ADM_CONFIG_FORM" property="schedulerState"/>
            </td>
        </tr>
        <tr>
            <td align="left" colspan="2">
                Num of jobs executed:
            </td>
            <td align="left" colspan="2">
                <bean:write name="QUARTZ_ADM_CONFIG_FORM" property="numJobsExecuted"/>
            </td>
        </tr>
        <tr>
            <td align="left" colspan="2">
                Running since:
            </td>
            <td align="left" colspan="2">
                <bean:write name="QUARTZ_ADM_CONFIG_FORM" property="runningSince"/>
            </td>
        </tr>
        <tr>
            <td align="left" colspan="2">
                Threadpool size:
            </td>
            <td align="left" colspan="2">
                <bean:write name="QUARTZ_ADM_CONFIG_FORM" property="threadPoolSize"/>
            </td>
        </tr>
        <tr>
            <td align="left" colspan="2">
                Version:
            </td>
            <td align="left" colspan="2">
                <bean:write name="QUARTZ_ADM_CONFIG_FORM" property="version"/>
            </td>
        </tr>
        <tr>
            <td>
              <input type="submit" name="action" value="Start" style="width:70px;">
            </td>
            <td>
              <input type="submit" name="action" value="Pause" style="width:70px;">
            </td>
            <td>
              <input type="submit" name="action" value="Hard Stop" style="width:70px;">
            </td>
            <td>
              <input type="submit" name="action" value="Soft Stop" style="width:70px;">
              &nbsp;&nbsp;(wait for jobs to complete)
            </td>
        </tr>

        </html:form>
    </table>
    <%
        }
    %>
    <jsp:include flush='true' page="ui/admFooter.jsp"/>
    </body>
</html:html>
