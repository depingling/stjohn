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
<bean:define id="theForm" name="EVENT_ADM_CONFIG_FORM" type="com.cleanwise.view.forms.EventAdmMgrForm"/>

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
        <title>Event Administrator</title>
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
                <jsp:include flush='true' page="ui/admEventToolbar.jsp"/>
            </td>
        </tr>
    </table>
    <%
        if("init".equals(action)){
    %>
    <table bgcolor="#cccccc" width="769">
        <html:form
                action="/adminportal/eventAdm.do?action=init"
                enctype="multipart/form-data">

            <tr>
                <td>

                    <b>Current statistic:</b>
                    <table bgcolor="#cccccc" width="100%">
                        <tr><td></td>
                            <td>FAILED</td>
                            <td>IN_PROGRESS</td>
                            <td>PROCESSED</td>
                            <td>READY</td>
                            <td>REJECTED</td>
                            <td>ATTEMPT=0</td>
                        </tr>
                        <tr><td>EMAIL</td>
                            <td bgcolor="red"><a href="eventAdm.do?action=search&type=eventEmailFailed">
                                <bean:write name="EVENT_ADM_CONFIG_FORM" property="eventEmailCountFailed"/></a></td>
                            <td bgcolor="blue"><a href="eventAdm.do?action=search&type=eventEmailInProgress">
                                <bean:write name="EVENT_ADM_CONFIG_FORM" property="eventEmailCountInProgress"/></a></td>
                            <td bgcolor="cyan"><a href="eventAdm.do?action=search&type=eventEmailProcessed">
                                <bean:write name="EVENT_ADM_CONFIG_FORM" property="eventEmailCountProcessed"/></a></td>
                            <td bgcolor="yellow"><a href="eventAdm.do?action=search&type=eventEmailReady">
                                <bean:write name="EVENT_ADM_CONFIG_FORM" property="eventEmailCountReady"/></a></td>
                            <td bgcolor="red"><a href="eventAdm.do?action=search&type=eventEmailRejected">
                                <bean:write name="EVENT_ADM_CONFIG_FORM" property="eventEmailCountRejected"/></a></td>
                            <td><a href="eventAdm.do?action=search&type=eventEmailUnActive">
                                <bean:write name="EVENT_ADM_CONFIG_FORM" property="eventEmailCountUnActive"/></a></td>
                        </tr>
                        <tr><td>PROCESS</td>
                            <td bgcolor="red"><a href="eventAdm.do?action=search&type=eventProcessFailed">
                                <bean:write name="EVENT_ADM_CONFIG_FORM" property="eventProcessCountFailed"/></a></td>
                            <td bgcolor="blue"><a href="eventAdm.do?action=search&type=eventProcessInProgress">
                                <bean:write name="EVENT_ADM_CONFIG_FORM" property="eventProcessCountInProgress"/></a></td>
                            <td bgcolor="cyan"><a href="eventAdm.do?action=search&type=eventProcessProcessed">
                                <bean:write name="EVENT_ADM_CONFIG_FORM" property="eventProcessCountProcessed"/></a></td>
                            <td bgcolor="yellow"><a href="eventAdm.do?action=search&type=eventProcessReady">
                                <bean:write name="EVENT_ADM_CONFIG_FORM" property="eventProcessCountReady"/></a></td>
                            <td bgcolor="red"><a href="eventAdm.do?action=search&type=eventProcessRejected">
                                <bean:write name="EVENT_ADM_CONFIG_FORM" property="eventProcessCountRejected"/></a></td>
                            <td><a href="eventAdm.do?action=search&type=eventProcessUnActive">
                                <bean:write name="EVENT_ADM_CONFIG_FORM" property="eventProcessCountUnActive"/></a></td>
                        </tr>
                    </table>
                </td>
            </tr>

            <tr>
                <td>
                    <html:hidden name="EVENT_ADM_CONFIG_FORM" property="searchDateFrom" value=""/>
                    <html:hidden name="EVENT_ADM_CONFIG_FORM" property="searchDateTo" value=""/>
                    <input type="submit" name="action" value="Refresh Statistic">
                </td>
            </tr>
        </html:form>
    </table>
    <%
        }
    %>
    <div class="admres">

        Event(s) total count in system:<bean:write name="EVENT_ADM_CONFIG_FORM" property="eventCount"/>

    </div>

    </body>
</html:html>
