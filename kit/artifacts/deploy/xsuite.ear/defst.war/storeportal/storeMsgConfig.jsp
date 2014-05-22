<%@ page language="java"%>
<%@ page import="com.cleanwise.view.utils.Constants"%>
<%@ page import="com.cleanwise.service.api.value.*"%>
<%@ page import="com.cleanwise.service.api.dao.*"%>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames"%>
<%@ page import="com.cleanwise.view.forms.*"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app"%>
<script language="JavaScript1.2">
 <!--
function sortSubmit(val) {
 var actions;
 actions=document.all["action"];
 for(ii=actions.length-1; ii>=0; ii--) {
   if(actions[ii].value=='BBBBBBB') {
     actions[ii].value="sort";
     break;
   }
 }
 sortField=document.all["sortField"];
 sortField.value = val;

 document.forms[1].submit();
 return false;
 }

  function f_check_search()
  {
  var x=document.getElementsByName("searchType");
  for (var i = 0; i < x.length ; i++)
  {
  if (x[i].value == "nameBegins")
  {
  x[i].checked = true;
  }
  }                   
  }

  function f_check_boxes()
  {
  f_set_vals(1);
  }

  function f_uncheck_boxes()
  {
  f_set_vals(0);
  }

  function f_set_vals(pVal)
  {
  var x=document.getElementsByName("configAccountsSelectedIds");
  //alert('x.length='+x.length + ' pVal=' + pVal);
  for (var i = 0; i < x.length ; i++)
  {
  if (x[i].name == "configAccountsSelectedIds")
  {
  if ( pVal == 1 )
  {
  x[i].checked = true;
  }
  else
  {
  x[i].checked = false;
  }
  }
  }

  }
  function submitForm() {
  document.forms[0].submit();
  }
  -->
</script>

<style>
.cfg {
	border-top: solid 1px black;
	border-right: solid 1px black;
	background-color: #cccccc;
	font-weight: bold;
	text-align: center;
}

.cfg_on {
	border-top: solid 1px black;
	border-right: solid 1px black;
	background-color: white;
	font-weight: bold;
	text-align: center;
}
</style>
<bean:define id="theForm" name="STORE_ADMIN_MSG_FORM"
	type="com.cleanwise.view.forms.StoreMsgMgrForm" />
<table class="results"><tr><td>
<table border="0" cellpadding="5" cellspacing="5" >
	<tr>
		<td><b><app:storeMessage key="storemessages.text.messageId" />:</b></td>
		<td><bean:write name="STORE_ADMIN_MSG_FORM"
			property="messageDetail.storeMessageId" filter="true" /></td>
		<td><b><app:storeMessage key="storemessages.text.messageTitle" />:</b></td>
		<td><bean:write name="STORE_ADMIN_MSG_FORM"
			property="messageDetail.messageTitle" filter="true" /></td>
	</tr>
</table>
<table>
	<tr>
		<td><b><app:storeMessage key="storemessages.text.configureAccounts" /></b></td>
	</tr>
</table>
<div id="acctConfig"><html:form 
	name="STORE_ADMIN_MSG_FORM" action="/storeportal/msgconfig.do"
	scope="session" type="com.cleanwise.view.forms.StoreMsgMgrForm">
	<table ID=1382 width="769">
		<tr>
			<td>&nbsp;</td>
			<td><html:text name="STORE_ADMIN_MSG_FORM"
				property="confSearchField" />
			<html:radio name="STORE_ADMIN_MSG_FORM" property="confSearchType"
				value="<%=Constants.ID%>" /><app:storeMessage key="storemessages.text.id" /><html:radio name="STORE_ADMIN_MSG_FORM"
				property="confSearchType" value="<%=Constants.NAME_BEGINS%>" /><app:storeMessage key="storemessages.text.nameStartsWith" /><html:radio
				name="STORE_ADMIN_MSG_FORM" property="confSearchType"
				value="<%=Constants.NAME_CONTAINS%>" /><app:storeMessage key="storemessages.text.nameContains" /></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td><html:submit property="action">
				<app:storeMessage  key="global.action.label.search" />
			</html:submit> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<app:storeMessage key="storemessages.text.showConfiguredOnly" />:<html:checkbox name='STORE_ADMIN_MSG_FORM'
				property='configuratedOnlyFl' value='true' />
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<app:storeMessage key="storemessages.text.showInactive" />:<html:checkbox
				name='STORE_ADMIN_MSG_FORM' property='confShowInactiveFl'
				value='true' /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <html:submit
				property="action"><app:storeMessage key="storemessages.text.configureAllAccounts" /></html:submit></td>
		</tr>
	</table>
	<logic:present name="STORE_ADMIN_MSG_FORM" property="configAccounts">
		<bean:size id="itemCount" name="STORE_ADMIN_MSG_FORM" property="configAccounts"/>
      <app:storeMessage key="storemessages.text.searchResultCount" />: <bean:write name="itemCount" />
		<logic:greaterThan name="itemCount" value="0">
			<table>
				<tr align=left>
					<td><a ID=1384 class="tableheader" href="#pgsort"
						onclick="sortSubmit('id');"><app:storeMessage key="storemessages.text.accountId" /></td>
					<td><a ID=1385 class="tableheader" href="#pgsort"
						onclick="sortSubmit('name');"><app:storeMessage key="storemessages.text.name" /></td>
					<td><a ID=1386 class="tableheader" href="#pgsort"
						onclick="sortSubmit('status');"><app:storeMessage key="storemessages.text.status" /></td>
					<td class="tableheader"><a href="javascript:f_check_boxes();">[ <app:storeMessage key="storemessages.text.selectAll" /> ]<br>
					</a><a href="javascript:f_uncheck_boxes();">[ <app:storeMessage key="storemessages.text.clearAll" /> ]</a>
					</td>
				</tr>
				<logic:iterate id="item" name="STORE_ADMIN_MSG_FORM" property="configAccounts"
					type="com.cleanwise.service.api.value.BusEntityData">
					<tr>
						<td><bean:write name="item" property="busEntityId" /></td>
						<td><bean:write name="item" property="shortDesc" /></td>
						<td><bean:write name="item" property="busEntityStatusCd" /></td>
						<td><html:multibox name="STORE_ADMIN_MSG_FORM"
							property="configAccountsSelectedIds" value="<%=String.valueOf(item.getBusEntityId())%>" /></td>
					</tr>
				</logic:iterate>
				<tr>
					<td colspan="3"></td>
					<td><html:submit property="action">
						<app:storeMessage  key="global.action.label.save" />
					</html:submit></td>
				</tr>
			</table>
		</logic:greaterThan>
	</logic:present>
	<html:hidden property="action" value="BBBBBBB" />
	<html:hidden property="sortField" value="BBBBBBB" />
</html:form></div>
</td></tr></table>