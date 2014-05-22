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
<jsp:include flush='true' page="ui/groupInfo.jsp"/>
<table border="0" cellpadding="1" cellspacing="0" width="769" class="mainbody">
<html:form name="GROUP_FORM" action="adminportal/groupmgrConfig.do" type="com.cleanwise.view.forms.GroupForm">
        <tr>
                <td><b>Find:</b></td>
                <td>
                        <logic:equal name="GROUP_FORM" property="groupData.groupTypeCd" value="<%=RefCodeNames.GROUP_TYPE_CD.USER%>">
                                <html:select name="GROUP_FORM" property="configType">
                                        <html:option value="<%=RefCodeNames.GROUP_TYPE_CD.USER%>"/>
                                </html:select>
                        </logic:equal>
                        <logic:equal name="GROUP_FORM" property="groupData.groupTypeCd" value="<%=RefCodeNames.GROUP_TYPE_CD.ACCOUNT%>">
                                <html:select name="GROUP_FORM" property="configType">
                                        <html:option value="<%=RefCodeNames.GROUP_TYPE_CD.ACCOUNT%>"/>
                                </html:select>
                        </logic:equal>
                        <%--<logic:equal name="GROUP_FORM" property="groupData.groupTypeCd" value="<%=RefCodeNames.GROUP_TYPE_CD.SITE%>">
                                <html:select name="GROUP_FORM" property="configType">
                                        <html:option value="<%=RefCodeNames.GROUP_TYPE_CD.SITE%>"/>
                                </html:select>
                        </logic:equal>--%>
                        <logic:equal name="GROUP_FORM" property="groupData.groupTypeCd" value="<%=RefCodeNames.GROUP_TYPE_CD.DISTRIBUTOR%>">
                                <html:select name="GROUP_FORM" property="configType">
                                        <html:option value="<%=RefCodeNames.GROUP_TYPE_CD.DISTRIBUTOR%>"/>
                                </html:select>
                        </logic:equal>
						<logic:equal name="GROUP_FORM" property="groupData.groupTypeCd" value="<%=RefCodeNames.GROUP_TYPE_CD.MANUFACTURER%>">
                                <html:select name="GROUP_FORM" property="configType">
                                        <html:option value="<%=RefCodeNames.GROUP_TYPE_CD.MANUFACTURER%>"/>
                                </html:select>
                        </logic:equal>
						<logic:equal name="GROUP_FORM" property="groupData.groupTypeCd" value="<%=RefCodeNames.GROUP_TYPE_CD.STORE%>">
                                <html:select name="GROUP_FORM" property="configType">
                                        <html:option value="<%=RefCodeNames.GROUP_TYPE_CD.STORE%>"/>
                                </html:select>
                        </logic:equal>
                </td>
                <td>
                        <html:text name="GROUP_FORM" property="configSearchField"/>
                </td>
        </tr>
		<logic:equal name="<%=Constants.APP_USER%>" property="user.userTypeCd" value="<%=RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR%>">
		<tr>
		<td><b>Store&nbsp;Id:</b></td>
		<td>
			<html:text name="GROUP_FORM" property="searchStoreId" size="5"/>
			<html:button property="action" onclick="popLocateGlobal('storelocate', 'searchStoreId', 'storeName');" value="Locate Store"/>
		</td>
		</logic:equal>
        <tr>
                <td><b>Search By:</b></td>
                <td colspan="3">
                        <html:radio name="GROUP_FORM" property="configSearchType" value="nameBegins" />
                        Name(starts with)
                        <html:radio name="GROUP_FORM" property="configSearchType" value="nameContains" />
                        Name(contains)
                </td>
        </tr>
        <tr>
                <td></td>
                <td colspan="3">
                        <html:submit property="action">
                                <app:storeMessage  key="global.action.label.search"/>
                        </html:submit>
                        <html:submit property="action">
                                <app:storeMessage  key="admin.button.submitUpdates"/>
                        </html:submit>
                </td>
        </tr>
</table>


<logic:present name="GROUP_FORM" property="configResults">
<table>
<tr>
<%--Render headers based off type we are configuring--%>
<logic:equal name="GROUP_FORM" property="configResultsType" value="<%=RefCodeNames.GROUP_TYPE_CD.USER%>">
        <td>User Id</td>
        <td>Login Name</td>
        <td>First Name</td>
        <td>Last Name</td>
        <td>User Type</td>
        <td>Status</td>
</logic:equal>
<%--Only the user has a custom header, the rest we will just use the same heading--%>
<logic:notEqual name="GROUP_FORM" property="configResultsType" value="<%=RefCodeNames.GROUP_TYPE_CD.USER%>">
        <td>Id</td>
        <td>Name</td>
        <td>Address</td>
        <td>City</td>
        <td>State</td>
        <td>Zip Code</td>
        <td>Status</td>
</logic:notEqual>
<td>
        Select<br>
        <a class="tableheader" href="javascript:SetCheckedGlobal(1,'configResults.selected')">[Check&nbsp;All]</a>
        <br>
        <a class="tableheader" href="javascript:SetCheckedGlobal(0,'configResults.selected')">[&nbsp;Clear]</a>
</td>
</tr>
<logic:iterate id="arrele" name="GROUP_FORM" property="configResults.values" indexId="i">
<tr>
<%--Render detail rows based off type we are configuring--%>
<logic:equal name="GROUP_FORM" property="configResultsType" value="<%=RefCodeNames.GROUP_TYPE_CD.USER%>">
        <td><bean:write name="arrele" property="userId"/></td>
        <td><bean:write name="arrele" property="userName"/></td>
        <td><bean:write name="arrele" property="firstName"/></td>
        <td><bean:write name="arrele" property="lastName"/></td>
        <td><bean:write name="arrele" property="userTypeCd"/></td>
        <td><bean:write name="arrele" property="userStatusCd"/></td>
</logic:equal>
<logic:equal name="GROUP_FORM" property="configResultsType" value="<%=RefCodeNames.GROUP_TYPE_CD.ACCOUNT%>">
        <td><bean:write name="arrele" property="busEntity.busEntityId"/></td>
        <td><bean:write name="arrele" property="busEntity.shortDesc"/></td>
        <td><bean:write name="arrele" property="billingAddress.address1"/></td>
        <td><bean:write name="arrele" property="billingAddress.city"/></td>
        <td><bean:write name="arrele" property="billingAddress.stateProvinceCd"/></td>
        <td><bean:write name="arrele" property="billingAddress.postalCode"/></td>
        <td><bean:write name="arrele" property="busEntity.busEntityStatusCd"/></td>
</logic:equal>
<logic:equal name="GROUP_FORM" property="configResultsType" value="<%=RefCodeNames.GROUP_TYPE_CD.DISTRIBUTOR%>">
        <td><bean:write name="arrele" property="busEntity.busEntityId"/></td>
        <td><bean:write name="arrele" property="busEntity.shortDesc"/></td>
        <td><bean:write name="arrele" property="primaryAddress.address1"/></td>
        <td><bean:write name="arrele" property="primaryAddress.city"/></td>
        <td><bean:write name="arrele" property="primaryAddress.stateProvinceCd"/></td>
        <td><bean:write name="arrele" property="primaryAddress.postalCode"/></td>
        <td><bean:write name="arrele" property="busEntity.busEntityStatusCd"/></td>
</logic:equal>
<%--<logic:equal name="GROUP_FORM" property="configResultsType" value="<%=RefCodeNames.GROUP_TYPE_CD.SITE%>">
        <td><bean:write name="arrele" property="busEntity.busEntityId"/></td>
        <td><bean:write name="arrele" property="busEntity.shortDesc"/></td>
        <td><bean:write name="arrele" property="siteAddress.address1"/></td>
        <td><bean:write name="arrele" property="siteAddress.city"/></td>
        <td><bean:write name="arrele" property="siteAddress.stateProvinceCd"/></td>
        <td><bean:write name="arrele" property="siteAddress.postalCode"/></td>
        <td><bean:write name="arrele" property="busEntity.busEntityStatusCd"/></td>
</logic:equal>--%>
<logic:equal name="GROUP_FORM" property="configResultsType" value="<%=RefCodeNames.GROUP_TYPE_CD.STORE%>">
        <td><bean:write name="arrele" property="busEntity.busEntityId"/></td>
        <td><bean:write name="arrele" property="busEntity.shortDesc"/></td>
        <td><bean:write name="arrele" property="primaryAddress.address1"/></td>
        <td><bean:write name="arrele" property="primaryAddress.city"/></td>
        <td><bean:write name="arrele" property="primaryAddress.stateProvinceCd"/></td>
        <td><bean:write name="arrele" property="primaryAddress.postalCode"/></td>
        <td><bean:write name="arrele" property="busEntity.busEntityStatusCd"/></td>
</logic:equal>
<logic:equal name="GROUP_FORM" property="configResultsType" value="<%=RefCodeNames.GROUP_TYPE_CD.MANUFACTURER%>">
        <td><bean:write name="arrele" property="busEntity.busEntityId"/></td>
        <td><bean:write name="arrele" property="busEntity.shortDesc"/></td>
        <td><bean:write name="arrele" property="primaryAddress.address1"/></td>
        <td><bean:write name="arrele" property="primaryAddress.city"/></td>
        <td><bean:write name="arrele" property="primaryAddress.stateProvinceCd"/></td>
        <td><bean:write name="arrele" property="primaryAddress.postalCode"/></td>
        <td><bean:write name="arrele" property="busEntity.busEntityStatusCd"/></td>
</logic:equal>
<%String selectedStr = "configResults.selected["+i+"]";%>
<td><html:multibox name="GROUP_FORM" property="<%=selectedStr%>" value="true"/></td>
</tr>
</logic:iterate>
</table>
</logic:present>


</html:form>
<jsp:include flush='true' page="ui/admFooter.jsp"/>
</body>
</html:html>
