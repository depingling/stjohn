<%@ page language="java" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<body class="admin2">
<div class="text">
<table cellpadding="2" cellspacing="0" border="0" width="<%=Constants.TABLEWIDTH800%>" class="mainbody">

<html:form styleId="<%=RefCodeNames.UI_PAGE_CD.ACCOUNT_DETAIL%>"
           name="UI_ACCOUNT_MGR_FORM"
           action="/uimanager/uiAccount.do"
           scope="session">



<tr height="0px">
    <td width="150px"></td>
    <td width="250px"></td>
    <td width="150px"></td>
    <td></td>
</tr>
 
<jsp:include flush='true' page="../admin2.0/admin2AccountDetailBody1.jsp">
        <jsp:param name="configMode" value="true"/>
        <jsp:param name="formAction" value="/uimanager/uiAccount.do"/>
        <jsp:param name="formName" value="UI_ACCOUNT_MGR_FORM"/>
</jsp:include>
<jsp:include flush='true' page="../admin2.0/admin2AccountDetailBody2.jsp">
        <jsp:param name="configMode" value="true"/>
        <jsp:param name="formAction" value="/uimanager/uiAccount.do"/>
        <jsp:param name="formName" value="UI_ACCOUNT_MGR_FORM"/>
</jsp:include>
<jsp:include flush='true' page="../admin2.0/admin2AccountDetailBody3.jsp">
        <jsp:param name="configMode" value="true"/>
        <jsp:param name="formAction" value="/uimanager/uiAccount.do"/>
        <jsp:param name="formName" value="UI_ACCOUNT_MGR_FORM"/>
</jsp:include>

<tr>
    <td colspan=4>
        <table cellpadding="2" cellspacing="0" border="0" width="100%" class="mainbody">
            <tr>
                <td colspan="4">&nbsp;</td>
            </tr>
            <tr>
                <td colspan="4">&nbsp;</td>
            </tr>
            <tr>
                <td colspan="4" align="center">
                    <html:submit property="action" value="Save Page Interface"></html:submit>
                </td>
            </tr>
        </table>
    </td>
</tr>
</html:form>
</table>
</div>
</body>