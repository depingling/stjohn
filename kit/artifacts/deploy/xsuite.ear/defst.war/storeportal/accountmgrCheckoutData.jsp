<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<app:checkLogon/>
<bean:define id="theForm" name="ACCOUNT_MGR_CHECKOUT_DATA_FORM"
type="com.cleanwise.view.forms.AccountMgrCheckoutDataForm"/>

<html:html>
<body>
<jsp:include flush='true' page="/storeportal/storeAdminNonTilesLayout.jsp"/>
<br>


<html:form
 styleId="490"  name="ACCOUNT_MGR_CHECKOUT_DATA_FORM"
  action="/storeportal/accountMgrCheckoutData?action=Save"
  type="com.cleanwise.view.forms.AccountMgrCheckoutDataForm">



<bean:define id="thisAccountId" name="STORE_ACCOUNT_DETAIL_FORM"
   property="accountData.accountId"
   type="java.lang.Integer" />

<html:hidden name="ACCOUNT_MGR_CHECKOUT_DATA_FORM" property="accountId"
  value="<%=thisAccountId.toString()%>"/>


<jsp:include flush='true' page="storeAccountCtx.jsp"/>
<table ID=493 width="<%=Constants.TABLEWIDTH%>" class="results" border="1">

<tr>
<th>
Required
</th>
<th>
Show in Runtime
<br>Checkout page.
</th>
<th>
Field Id
</th>
<th>
Field name
</th>
</tr>

<%--Field 1--%>
<tr>
<td>
<html:checkbox name="ACCOUNT_MGR_CHECKOUT_DATA_FORM" property="config.f1Required"/>
</td>
<td>
<html:checkbox name="ACCOUNT_MGR_CHECKOUT_DATA_FORM" property="config.f1ShowRuntime"/>
</td>
<td>
Field 1
</td>
<td>
<html:text name="ACCOUNT_MGR_CHECKOUT_DATA_FORM" property="config.f1Tag"/>
</td>
</tr>

<%--Field 2--%>
<tr>
<td>

<html:checkbox name="ACCOUNT_MGR_CHECKOUT_DATA_FORM"  property="config.f2Required"/>
</td>
<td>
<html:checkbox name="ACCOUNT_MGR_CHECKOUT_DATA_FORM" property="config.f2ShowRuntime"/>
</td>
<td>
Field 2
</td>
<td>
<html:text name="ACCOUNT_MGR_CHECKOUT_DATA_FORM" property="config.f2Tag"/>
</td>
</tr>

<%--Field 3--%>
<tr>
<td>
<html:checkbox name="ACCOUNT_MGR_CHECKOUT_DATA_FORM" property="config.f3Required"/>
</td>
<td>
<html:checkbox name="ACCOUNT_MGR_CHECKOUT_DATA_FORM" property="config.f3ShowRuntime"/>
</td>
<td>
Field 3
</td>
<td>
<html:text name="ACCOUNT_MGR_CHECKOUT_DATA_FORM" property="config.f3Tag"/>
</td>
</tr>

<%--Field 4--%>
<tr>
<td>
<html:checkbox name="ACCOUNT_MGR_CHECKOUT_DATA_FORM" property="config.f4Required"/>
</td>
<td>
<html:checkbox name="ACCOUNT_MGR_CHECKOUT_DATA_FORM" property="config.f4ShowRuntime"/>
</td>
<td>
Field 4
</td>
<td>
<html:text name="ACCOUNT_MGR_CHECKOUT_DATA_FORM" property="config.f4Tag"/>
</td>
</tr>

<%--Field 5--%>
<tr>
<td>
<html:checkbox name="ACCOUNT_MGR_CHECKOUT_DATA_FORM" property="config.f5Required"/>
</td>
<td>
<html:checkbox name="ACCOUNT_MGR_CHECKOUT_DATA_FORM" property="config.f5ShowRuntime"/>
</td>
<td>
Field 5
</td>
<td>
<html:text name="ACCOUNT_MGR_CHECKOUT_DATA_FORM" property="config.f5Tag"/>
</td>
</tr>

<%--Field 6--%>
		<tr>
			<td><html:checkbox name="ACCOUNT_MGR_CHECKOUT_DATA_FORM"
				property="config.f6Required" /></td>
			<td><html:checkbox name="ACCOUNT_MGR_CHECKOUT_DATA_FORM"
				property="config.f6ShowRuntime" /></td>
			<td>Field 6</td>
			<td><html:text name="ACCOUNT_MGR_CHECKOUT_DATA_FORM"
				property="config.f6Tag" /></td>
		</tr>

		<%--Field 7--%>
		<tr>
			<td><html:checkbox name="ACCOUNT_MGR_CHECKOUT_DATA_FORM"
				property="config.f7Required" /></td>
			<td><html:checkbox name="ACCOUNT_MGR_CHECKOUT_DATA_FORM"
				property="config.f7ShowRuntime" /></td>
			<td>Field 7</td>
			<td><html:text name="ACCOUNT_MGR_CHECKOUT_DATA_FORM"
				property="config.f7Tag" /></td>
		</tr>

		<%--Field 8--%>
		<tr>
			<td><html:checkbox name="ACCOUNT_MGR_CHECKOUT_DATA_FORM"
				property="config.f8Required" /></td>
			<td><html:checkbox name="ACCOUNT_MGR_CHECKOUT_DATA_FORM"
				property="config.f8ShowRuntime" /></td>
			<td>Field 8</td>
			<td><html:text name="ACCOUNT_MGR_CHECKOUT_DATA_FORM"
				property="config.f8Tag" /></td>
		</tr>

		<%--Field 9--%>
		<tr>
			<td><html:checkbox name="ACCOUNT_MGR_CHECKOUT_DATA_FORM"
				property="config.f9Required" /></td>
			<td><html:checkbox name="ACCOUNT_MGR_CHECKOUT_DATA_FORM"
				property="config.f9ShowRuntime" /></td>
			<td>Field 9</td>
			<td><html:text name="ACCOUNT_MGR_CHECKOUT_DATA_FORM"
				property="config.f9Tag" /></td>
		</tr>

		<%--Field 10--%>
		<tr>
			<td><html:checkbox name="ACCOUNT_MGR_CHECKOUT_DATA_FORM"
				property="config.f10Required" /></td>
			<td><html:checkbox name="ACCOUNT_MGR_CHECKOUT_DATA_FORM"
				property="config.f10ShowRuntime" /></td>
			<td>Field 10</td>
			<td><html:text name="ACCOUNT_MGR_CHECKOUT_DATA_FORM"
				property="config.f10Tag" /></td>
		</tr>



<tr>
<td colspan=4 align=center>
<html:submit property="action">
<app:storeMessage  key="global.action.label.save"/>
</html:submit>
</td>
</tr>

</table>

</html:form>

<jsp:include flush='true' page="ui/admFooter.jsp"/>
</body>
</html:html>
