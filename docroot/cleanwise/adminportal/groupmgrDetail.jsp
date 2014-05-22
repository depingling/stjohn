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

<table border="0" cellpadding="1" cellspacing="0" width="769" class="mainbody">
<html:form name="GROUP_FORM" action="adminportal/groupmgrDetail.do" type="com.cleanwise.view.forms.GroupForm">
        <tr>
                <td><b>Group Id:</b></td>
                <td><bean:write name="GROUP_FORM" property="groupData.groupId"/></td>
                <td>&nbsp;</td><td>&nbsp;</td>
        </tr>
        <tr>
                <td><b>Group Type:</b></td>
                <td>
                        <html:select name="GROUP_FORM" property="groupData.groupTypeCd" onchange="document.forms[0].submit();">
                                <html:option value=""><app:storeMessage  key="admin.select"/></html:option>
                                <html:options collection="group.type.vector" property="value"/>
                        </html:select>
                </td>
                <td><b>Group Status:</b></td>
                <td>
                        <html:select name="GROUP_FORM" property="groupData.groupStatusCd">
                                <html:option value=""><app:storeMessage  key="admin.select"/></html:option>
                                <html:options collection="group.status.vector" property="value"/>
                        </html:select>
                </td>
        </tr>
        <tr>
                <td><b>Group Name:</b></td>
                <td colspan="3">
                        <html:text name="GROUP_FORM" property="groupData.shortDesc" size="30"/>
                        <logic:equal name="GROUP_FORM" property="groupData.groupTypeCd" value="<%=RefCodeNames.GROUP_TYPE_CD.USER%>">
                                Or select name from the list:
                                <html:select name="GROUP_FORM" property="groupNameSelect">
                                        <html:option value=""><app:storeMessage  key="admin.select"/></html:option>
                                        <html:options collection="group.intrinsic.name.vector" property="value"/>
                                </html:select>
                        </logic:equal>
                </td>
        </tr>
        <tr><td>
                <html:submit property="action">
                        <app:storeMessage  key="admin.button.submitUpdates"/>
                </html:submit>
        </td></tr>
</html:form>
</table>



<jsp:include flush='true' page="ui/admFooter.jsp"/>

</body>

</html:html>
