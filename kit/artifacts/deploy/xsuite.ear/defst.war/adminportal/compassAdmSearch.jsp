<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<app:setLocaleAndImages/>
<app:checkLogon/>

<bean:define id="Location" value="none" type="java.lang.String" toScope="session"/>
<bean:define id="theForm" name="COMPASS_ADM_CONFIG_FORM" type="com.cleanwise.view.forms.CompassAdmMgrForm"/>

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
        <title>Compass Administrator</title>
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
                <jsp:include flush='true' page="ui/admCompassToolbar.jsp"/>
            </td>
        </tr>
    </table>
    <table bgcolor="#cccccc" width="769">
        <html:form
                action="/adminportal/compassAdm.do?action=Search"
                enctype="multipart/form-data">
        <tr>
            <td align="left">
                Aliase:
            </td>
            <td align="left">
                <html:text name="COMPASS_ADM_CONFIG_FORM" property="queryAliase" maxlength="10"/>
            </td>
        </tr>
        <tr>
            <td align="left">
                Id name:
            </td>
            <td align="left">
                <html:text name="COMPASS_ADM_CONFIG_FORM" property="queryId" maxlength="10"/>
            </td>
        </tr>
        <tr>
            <td align="left">
                Query:
            </td>
            <td align="left">
              <html:textarea name="COMPASS_ADM_CONFIG_FORM" property="queryStr" rows="5" cols="60"/>
            </td>
        </tr>
        <tr>
            <td></td>
            <td align="left"><input type="submit" name="action" value="Search" style="width:120px;"></td>
        </tr>
        </html:form>
    </table>

<logic:notEmpty name="COMPASS_ADM_CONFIG_FORM" property="resultIds">
<table  class="stpTable_sortable" id="ts1" bgcolor="#cccccc" width="769" border="0">
<thead>
    <tr class="stpTH">
        <th class=stpTH align="left"><bean:write name="COMPASS_ADM_CONFIG_FORM" property="queryId"/></th>
    </tr>
</thead>
<tbody>
    <logic:iterate id="row" name="COMPASS_ADM_CONFIG_FORM" property="resultIds" indexId="i">
        <tr class="stpTD">
            <td class="stpTD" align="left"><%=row%></td>
    </logic:iterate>
</tbody>
</table>
</logic:notEmpty>


    <jsp:include flush='true' page="ui/admFooter.jsp"/>
    </body>
</html:html>
