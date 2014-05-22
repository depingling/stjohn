<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page import="com.cleanwise.view.forms.StoreAccountMgrDetailForm" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Locale" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<app:checkLogon/>
<bean:define id="theForm" name="ACCOUNT_MGR_SERVICE_FEE_FORM"
type="com.cleanwise.view.forms.AccountMgrServiceFeeForm"/>

<html:html>
<body>
<jsp:include flush='true' page="/storeportal/storeAdminNonTilesLayout.jsp"/>

<table ID=489 class="stpTable">
<tr>
<td class=stpLabel>Account&nbsp;Id</td>
<td><bean:write name="STORE_ACCOUNT_DETAIL_FORM"
   property="accountData.busEntity.busEntityId"
   scope="session"/>
</td>

<td class=stpLabel>Account&nbsp;Name</td>
<td><bean:write name="STORE_ACCOUNT_DETAIL_FORM"
   property="accountData.busEntity.shortDesc"
   scope="session"/>
</td>

</tr>
</table>

<html:form
 styleId="490"  name="ACCOUNT_MGR_SERVICE_FEE_FORM"
  action="/storeportal/accountmgrServiceFee?action=update"
  type="com.cleanwise.view.forms.AccountMgrServiceFeeForm">



<bean:define id="thisAccountId" name="STORE_ACCOUNT_DETAIL_FORM"
   property="accountData.accountId"
   type="java.lang.Integer" />

<html:hidden name="ACCOUNT_MGR_SERVICE_FEE_FORM" property="accountId"
  value="<%=thisAccountId.toString()%>"/>



<table class="stpTable_sortable" id="ts1">

<thead>
<tr>
<th class="stpTH">Service Fee Code</th>
<th class="stpTH">Service Fee Item Number</th>
<th class="stpTH">Service Fee Amount</th>
</tr>
</thead>

<tbody>

<logic:iterate id="priceRuleDescV" name="ACCOUNT_MGR_SERVICE_FEE_FORM" indexId="i" property="priceRuleDescV" type="PriceRuleDescView">
<tr>
    <td class=stpTD align="center"><html:text property="<%= \"serviceFeeCode[\" + i + \"]\" %>" size='20'/></td>
    <td class=stpTD align="center"><html:text property="<%= \"serviceFeeItemNum[\" + i + \"]\" %>" size='20'/></td>
    <td class=stpTD align="center"><html:text property="<%= \"serviceFeeAmount[\" + i + \"]\" %>" size='20'/></td>
</tr>
</logic:iterate>
</tbody>

</table>

<table ID=491>
<tr><td width=600 align=right><html:submit value="Submit"/>
</td></tr>
</table>

</html:form>

<jsp:include flush='true' page="ui/admFooter.jsp"/>
</body>
</html:html>
