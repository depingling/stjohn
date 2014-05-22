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
    <title>Event Administrator - Create Event</title>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
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
            <jsp:include flush='true' page="ui/admEventToolbar.jsp"/>
        </td>
    </tr>
</table>
<%
    if ("creating".equals(action)) {
%>
<html:form
           action="/adminportal/eventAdm.do?action=edit&func=create">
<table bgcolor="#cccccc" width="769">
<tr>
    <td>
        <table bgcolor="#cccccc" width="100%">
            <tr>
                <td align="left">
                    Attempt:
                </td>
                <td align="left">
                    <html:text name="EVENT_ADM_CONFIG_FORM" property="createEventData.attempt"/>
                </td>
            </tr>

            <tr>
                <td align="left">
                    Type:
                </td>
                <td align="left">
                    <html:select name="EVENT_ADM_CONFIG_FORM" property="createEventData.type">
                        <html:option value=""></html:option>
                        <html:option value="<%=Event.TYPE_EMAIL%>"><%=Event.TYPE_EMAIL%>
                        </html:option>
                        <html:option value="<%=Event.TYPE_PROCESS%>"><%=Event.TYPE_PROCESS%>
                        </html:option>
                    </html:select>
                </td>
                <td align="left">
                    Status:
                </td>
                <td align="left">
                    <html:select name="EVENT_ADM_CONFIG_FORM" property="createEventData.status">
                        <html:option value=""></html:option>
                        <html:option value="<%=Event.STATUS_FAILED%>"><%=Event.STATUS_FAILED%></html:option>
                        <html:option value="<%=Event.STATUS_HOLD%>"><%=Event.STATUS_HOLD%></html:option>
                        <html:option value="<%=Event.STATUS_IGNORE%>"><%=Event.STATUS_IGNORE%></html:option>
                        <html:option value="<%=Event.STATUS_IN_PROGRESS%>"><%=Event.STATUS_IN_PROGRESS%></html:option>
                        <html:option value="<%=Event.STATUS_LIMITED%>"><%=Event.STATUS_LIMITED%></html:option>
                        <html:option value="<%=Event.STATUS_PROC_ERROR%>"><%=Event.STATUS_PROC_ERROR%></html:option>
                        <html:option value="<%=Event.STATUS_PROCESSED%>"><%=Event.STATUS_PROCESSED%></html:option>
                        <html:option value="<%=Event.STATUS_READY%>"><%=Event.STATUS_READY%></html:option>
                        <html:option value="<%=Event.STATUS_REJECTED%>"><%=Event.STATUS_REJECTED%></html:option>
                    </html:select>
                </td>
            </tr>

            <tr>
                <td>

                </td>
                <td>

                </td>
            </tr>

        </table>
    </td>
</tr>

<tr>
    <td>
        <input type="submit" name="action" value="Save">
    </td>
</tr>

</table>
</html:form>
<%
    }
%>
<div class="admres">

    Event(s) total count in system:<bean:write name="EVENT_ADM_CONFIG_FORM" property="eventCount"/>

</div>

</body>
</html:html>
