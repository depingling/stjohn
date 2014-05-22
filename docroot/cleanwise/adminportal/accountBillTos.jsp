<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.forms.*" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<app:checkLogon/>

<SCRIPT TYPE="text/javascript" SRC="../externals/table-sort.js" 
  CHARSET="ISO-8859-1"></SCRIPT>

<html:html>

<head>
<link rel="stylesheet" href="../externals/styles.css">
<title>Application Administrator Home: Bill to information</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<style>
.af { font-weight: bold; }
.bttab { background-color: #cccccc; }
</style>
</head>

<% 
String aid = (String)session.getAttribute("Account.id"); 
String addloc = "adminportal/accountBillTos.do?action=addBillTo&accountId=" + aid;
String action = request.getParameter("action");
if ( null == action ) {
  action = "";
}

String editbilltoId = request.getParameter("billtoId");
if ( null == editbilltoId ) {
  editbilltoId = "";
}

String updloc = "adminportal/accountBillTos.do?action=updateBillTo&accountId=" + aid
 + "&billtoId=" + editbilltoId;
%>
  
<jsp:include flush='true' page="ui/admToolbar.jsp"/>
<jsp:include flush='true' page="ui/admAccountToolbar.jsp"/>
<jsp:include flush='true' page="ui/loginInfo.jsp"/>
<jsp:include flush='true' page="ui/accountCtx.jsp"/>
  
  
<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<div class="text">

<% if ( action.equals("billtodetail") || action.equals("updateBillTo") ) { %>

<bean:define id="theForm"
  name="ACCOUNT_BILLTO_FORM"  type="AccountBillToForm" />

<b>Account bill to detail:</b> 
<br>
<html:form action="<%=updloc%>">

<table class="bttab" >

<tr>
<td class="af">Id:</td><td>
<bean:write name="ACCOUNT_BILLTO_FORM" property="billTo.billToId"/>
</td>
<td class="af">Mod by:</td><td>
<bean:write name="ACCOUNT_BILLTO_FORM" property="billTo.busEntity.modBy"/>
</td>
<td class="af">Mod date:</td><td>
<bean:write name="ACCOUNT_BILLTO_FORM" property="billTo.busEntity.modDate"/>
</td>
</tr>
<tr><td class="af">Bill to name:</td><td>
<html:text name="ACCOUNT_BILLTO_FORM" property="billTo.busEntity.shortDesc"/>
</td>
<td class="af">Status:</td><td>
<html:select name="ACCOUNT_BILLTO_FORM" property="billTo.busEntity.busEntityStatusCd">
<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
<html:options  collection="Account.status.vector" property="value" />
</html:select>

</td>
<td class="af">ERP number:</td><td>
<html:text name="ACCOUNT_BILLTO_FORM" property="billTo.busEntity.erpNum" maxlength="4"/>
</td>
</tr>
</table>

<table>
<tr><td>

<% /* bill to address */ %>
<b>Billing address information</b>
<tr><td class="af">Address1:</td>
<td><html:text name="ACCOUNT_BILLTO_FORM" property="billTo.billToAddress.address1"/></td>
</tr> 
<tr><td class="af">Address2:</td>
<td><html:text name="ACCOUNT_BILLTO_FORM" property="billTo.billToAddress.address2"/></td>
</tr> 
<tr><td class="af">Address3:</td>
<td><html:text name="ACCOUNT_BILLTO_FORM" property="billTo.billToAddress.address3"/></td>
</tr> 
<tr><td class="af">Address4:</td>
<td><html:text name="ACCOUNT_BILLTO_FORM" property="billTo.billToAddress.address4"/></td>
</tr> 
<tr><td class="af">City:</td>
<td><html:text name="ACCOUNT_BILLTO_FORM" property="billTo.billToAddress.city"/></td>
</tr> 
<tr><td class="af">State:</td>
<td><html:text name="ACCOUNT_BILLTO_FORM" property="billTo.billToAddress.stateProvinceCd"/></td>
</tr> 
<tr><td class="af">Zip:</td>
<td>
<html:text name="ACCOUNT_BILLTO_FORM" property="billTo.billToAddress.postalCode"/>
</td>
<tr><td class="af">Country:</td>
<td>
<html:select name="ACCOUNT_BILLTO_FORM" property="billTo.billToAddress.countryCd">
<html:option value=""><app:storeMessage  key="admin.select.country"/></html:option>
<html:options  collection="countries.vector" property="value" />
</html:select>
</td>
<td><html:submit value="Update Bill To"/></td>
</tr>
</table>


<html:hidden name="ACCOUNT_BILLTO_FORM" 
  property="billTo.busEntity.busEntityTypeCd"
  value="<%=RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT_BILLTO%>"/>
<html:hidden name="ACCOUNT_BILLTO_FORM" 
  property="billTo.busEntity.workflowRoleCd"
  value="<%=RefCodeNames.WORKFLOW_ROLE_CD.UNKNOWN%>" />
<html:hidden name="ACCOUNT_BILLTO_FORM" 
  property="billTo.busEntity.localeCd"
  value="en_US" />

  
<html:hidden name="ACCOUNT_BILLTO_FORM" property="billTo.billToAddress.shortDesc"
  value="<%=RefCodeNames.ADDRESS_TYPE_CD.ACCOUNT_BILLTO%>"/>
<html:hidden name="ACCOUNT_BILLTO_FORM" property="billTo.billToAddress.addressTypeCd"
  value="<%=RefCodeNames.ADDRESS_TYPE_CD.ACCOUNT_BILLTO%>"/>
<html:hidden name="ACCOUNT_BILLTO_FORM" property="billTo.billToAddress.addressStatusCd"
  value="<%=RefCodeNames.ADDRESS_STATUS_CD.ACTIVE%>"/>
  
</html:form>

<% } else { %>
<b>Account billing addresses defined:</b> 
<br>
<logic:present name="account.billtos.vector">
<bean:size id="rescount"  name="account.billtos.vector"/>
 Billtos count:  <bean:write name="rescount" />

<table cellspacing="0" border="0" width="769"  class="results">
<thead>
<tr align=left>
<th  class="tt"><a href="#pgsort" onclick="this.blur(); return f_sortTable('resTblBdy', 0, false);" title="-- Sort by Id">Id</a></th>
<th  class="tt"><a href="#pgsort" onclick="this.blur(); return f_sortTable('resTblBdy', 1, false);" title="-- Sort by ERP Num">ERP Num</a></th>
<th  class="tt"><a href="#pgsort" onclick="this.blur(); return f_sortTable('resTblBdy', 2, false);" title="-- Sort by Name">Name</a></th>
<th  class="tt"><a href="#pgsort" onclick="this.blur(); return f_sortTable('resTblBdy', 3, false);" title="-- Sort by Address1">Address1</a></th>
<th  class="tt"><a href="#pgsort" onclick="this.blur(); return f_sortTable('resTblBdy', 4, false);" title="-- Sort by Address2">Address2</a></th>
<th  class="tt"><a href="#pgsort" onclick="this.blur(); return f_sortTable('resTblBdy', 5, false);" title="-- Sort by City">City</a></th>
<th  class="tt"><a href="#pgsort" onclick="this.blur(); return f_sortTable('resTblBdy', 6, false);" title="-- Sort by State">State</a></th>
<th  class="tt"><a href="#pgsort" onclick="this.blur(); return f_sortTable('resTblBdy', 7, false);" title="-- Sort by Zip">Zip</a></th>
<th  class="tt"><a href="#pgsort" onclick="this.blur(); return f_sortTable('resTblBdy', 8, false);" title="-- Sort by Status">Status</a></th>
</tr>
</thead>

<tbody id="resTblBdy">
<logic:iterate id="arrele" name="account.billtos.vector"
  type="com.cleanwise.service.api.value.BillToData">

<tr>
<td><bean:write name="arrele" property="busEntity.busEntityId"/></td>
<td><bean:write name="arrele" property="busEntity.erpNum"/></td>
<td>
<a href="accountBillTos.do?action=billtodetail&billtoId=<%=arrele.getBusEntity().getBusEntityId()%>">
<bean:write name="arrele" property="busEntity.shortDesc"/>
</a>
</td>
<td><bean:write name="arrele" property="billToAddress.address1"/></td>
<td><bean:write name="arrele" property="billToAddress.address2"/></td>
<td><bean:write name="arrele" property="billToAddress.city"/></td>
<td><bean:write name="arrele" property="billToAddress.stateProvinceCd"/></td>
<td><bean:write name="arrele" property="billToAddress.postalCode"/></td>
<td><bean:write name="arrele" property="busEntity.busEntityStatusCd"/></td>

</tr>

</logic:iterate>
</tbody>
</table>

</logic:present>

<br>
<b>Create a new bill to for this account.</b>
<html:form action="<%=addloc%>">

<table class="bttab">

<tr><td class="af">Bill to name:</td><td>
<html:text name="ACCOUNT_BILLTO_FORM" property="billTo.busEntity.shortDesc"/>
</td>
<td class="af">Status:</td><td>
<html:select name="ACCOUNT_BILLTO_FORM" property="billTo.busEntity.busEntityStatusCd">
<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
<html:options  collection="Account.status.vector" property="value" />
</html:select>

</td>
<td class="af">ERP number:</td><td>
<html:text name="ACCOUNT_BILLTO_FORM" property="billTo.busEntity.erpNum"/>
</td>
</tr>

<tr>
<td> <% /* bill to address */ %>
<b>Billing address information</b>
<tr><td class="af">Address1:</td>
<td><html:text name="ACCOUNT_BILLTO_FORM" property="billTo.billToAddress.address1"/></td>
</tr> 
<tr><td class="af">Address2:</td>
<td><html:text name="ACCOUNT_BILLTO_FORM" property="billTo.billToAddress.address2"/></td>
</tr> 
<tr><td class="af">Address3:</td>
<td><html:text name="ACCOUNT_BILLTO_FORM" property="billTo.billToAddress.address3"/></td>
</tr> 
<tr><td class="af">Address4:</td>
<td><html:text name="ACCOUNT_BILLTO_FORM" property="billTo.billToAddress.address4"/></td>
</tr> 
<tr><td class="af">City:</td>
<td><html:text name="ACCOUNT_BILLTO_FORM" property="billTo.billToAddress.city"/></td>
</tr> 
<tr><td class="af">State:</td>
<td><html:text name="ACCOUNT_BILLTO_FORM" property="billTo.billToAddress.stateProvinceCd"/></td>
</tr> 
<tr><td class="af">Zip:</td>
<td>
<html:text name="ACCOUNT_BILLTO_FORM" property="billTo.billToAddress.postalCode"/>
</td>
<tr><td class="af">Country:</td>
<td>
<html:select name="ACCOUNT_BILLTO_FORM" property="billTo.billToAddress.countryCd">
<html:option value=""><app:storeMessage  key="admin.select.country"/></html:option>
<html:options  collection="countries.vector" property="value" />
</html:select>
</td>
<td><html:submit value="Create Bill To"/></td>
</tr>

<html:hidden name="ACCOUNT_BILLTO_FORM" 
  property="billTo.busEntity.busEntityId" value="0"/>

<html:hidden name="ACCOUNT_BILLTO_FORM" 
  property="billTo.busEntity.busEntityTypeCd"
  value="<%=RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT_BILLTO%>"/>
<html:hidden name="ACCOUNT_BILLTO_FORM" 
  property="billTo.busEntity.workflowRoleCd"
  value="<%=RefCodeNames.WORKFLOW_ROLE_CD.UNKNOWN%>" />
<html:hidden name="ACCOUNT_BILLTO_FORM" 
  property="billTo.busEntity.localeCd"
  value="en_US" />

  
<html:hidden name="ACCOUNT_BILLTO_FORM" property="billTo.billToAddress.shortDesc"
  value="<%=RefCodeNames.ADDRESS_TYPE_CD.ACCOUNT_BILLTO%>"/>
<html:hidden name="ACCOUNT_BILLTO_FORM" property="billTo.billToAddress.addressTypeCd"
  value="<%=RefCodeNames.ADDRESS_TYPE_CD.ACCOUNT_BILLTO%>"/>
<html:hidden name="ACCOUNT_BILLTO_FORM" property="billTo.billToAddress.addressStatusCd"
  value="<%=RefCodeNames.ADDRESS_STATUS_CD.ACTIVE%>"/>
  
  </table>

</html:form>
<% } %>

  </div>
<jsp:include flush='true' page="ui/admFooter.jsp"/>

</html:html>

