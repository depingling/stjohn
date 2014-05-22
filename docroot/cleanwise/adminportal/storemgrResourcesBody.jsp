<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<app:checkLogon/>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="Location" value="store" type="java.lang.String" toScope="session"/>

<html:form name="STORE_DETAIL_FORM" action="adminportal/storeResources.do"
	scope="session" type="com.cleanwise.view.forms.StoreMgrDetailForm">
	<table class="mainbody">
	<tr>
		<td colspan="6" align="center">
			<html:submit property="action">
				<app:storeMessage  key="global.action.label.save"/>
			</html:submit>
		</td>
	</tr>
	<tr>
		<td><b>Name</b></td>
		<td><b>Value</b></td>
		<td><b>Locale</b></td>
		<td><b>Current Localized Value</b></td>
	</tr>
	<logic:iterate id="resource" name="STORE_DETAIL_FORM" property="storeMessageResources" indexId="i">
		<bean:define id="messName" name="resource" property="name" type="java.lang.String"/>
		<tr>
			<td width="180"><bean:write name="resource" property="name"/></td>
			<%String prop = "storeMessageResources["+i+"].value";%>
			<td width="180"><html:text name="STORE_DETAIL_FORM" property="<%=prop%>"/><a href='javascript:void(0);' onClick='javaScript:popLocateGlobal("../general/textEdit","<%=prop%>")'>(e)</a></td>
			<%prop = "storeMessageResources["+i+"].locale";%>
			<td width="100"><html:text name="STORE_DETAIL_FORM" property="<%=prop%>" size="5"/></td>
			<td width="237"><app:storeMessage key="<%=messName%>"/></td>
		</tr>
	</logic:iterate>
	<tr>
		<td colspan="6" align="center">
			<html:submit property="action">
				<app:storeMessage  key="global.action.label.save"/>
			</html:submit>
		</td>
	</tr>
	</table>
</html:form>
