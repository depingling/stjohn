<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.cachecos.Cachecos" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<app:setLocaleAndImages/>
<app:checkLogon/>
<bean:define id="Location" value="none" type="java.lang.String" toScope="session"/>
<bean:define id="theForm" name="ADM_CACHECOS_MGR_FORM" type="com.cleanwise.view.forms.AdmCachecosMgrForm"/>

<html:html>
    <head>
        <link rel="stylesheet" href="../externals/styles.css">
        <style>
            .tt {color: white;background-color: black; }
            .tt1 {border-right: solid 1px black;}
        </style>
        <title>Cachecos Administrator/Memory Information</title>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    </head>

    <body>

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
                <jsp:include flush='true' page="ui/admCachecosToolbar.jsp"/>
            </td>
        </tr>
    </table>

    <table bgcolor="#cccccc" width="769">
        <font size="4"> <%=Cachecos.getCachecosManager().info()%></font>
    </table>

    <jsp:include flush='true' page="ui/admFooter.jsp"/>
    </body>
</html:html>
