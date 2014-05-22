<%@ page language="java" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<app:setLocaleAndImages/>
<app:checkLogon/>
<bean:define id="Location" value="none" type="java.lang.String" toScope="session"/>
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

        <title>Process Administrator</title>

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
                <jsp:include flush='true' page="ui/admProcessToolbar.jsp"/>
            </td>
        </tr>
    </table>

    <table bgcolor="#cccccc" width="769">
        <html:form
                action="/adminportal/processAdm.do?action=init"
                enctype="multipart/form-data">

            <tr>
                <td>

                    <b>Current statistic:</b>

                </td>
            </tr>

            <tr>
                <td>

                    <input type="submit" name="action" value="Refresh Statistic">

                </td>
            </tr>
        </html:form>
    </table>

    <div class="admres">

        Process(s) current total active in system:

    </div>

    </body>
</html:html>
