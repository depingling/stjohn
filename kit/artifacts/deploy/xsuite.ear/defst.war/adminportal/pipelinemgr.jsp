<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<% boolean clwSwitch = ClwCustomizer.getClwSwitch(); %>
<% String storeDir = ClwCustomizer.getStoreDir(); %>
<app:checkLogon/>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="Location" value="account" 
  type="java.lang.String" toScope="session"/>

<style>
.orth { background-color: lightblue;
  font-weight: bold;
}
</style>

<script language="JavaScript1.2">
<!--
function popLocate(pLoc, parm) {
  var loc = pLoc + ".jsp?parm=" + parm;
  if(parm != "print"){
    locatewin = window.open(loc,"Locate", "menubar=no,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
  }else{
    locatewin = window.open(loc,"Locate", "menubar=yes,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
  }
  locatewin.focus();
  return false;
}
function popLocate2(pLoc, name, pDesc) {
var loc = pLoc + ".do?feedField=" + name + "&amp;feedDesc=" + pDesc;
locatewin = window.open(loc,"Locate", "menubar=no,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
locatewin.focus();

return false;
}

function f_showZip(e,o) {

	// e = event object in W3C DOM compatible browsers (Ex. Mozilla)
	// o = div object clicked by user

	// Parsing all children of the clicked div
	for (i=0;i<o.childNodes.length;i++) {
		// Find all div having the CSS class zitem
		if (o.childNodes[i].className=="zitem") {
			// If the div is hidden, make it visible
			if (o.childNodes[i].style.display=="none") {
				o.childNodes[i].style.display="block"
			} else if (o.childNodes[i].style.display=="block") {
				// If the div is visible, hide it
				o.childNodes[i].style.display="none"
			}
			
		}
	}

	// Stop click event propagation in upper div hierarchy	
	if (document.all) {
		// Code for IE browsers
		window.event.cancelBubble=true
	} else if (!document.all && document.getElementById) {
		// Code for Mozilla browsers
		e.stopPropagation()
	}
}


-->
</script>

<html:html>

<head>
<link rel="stylesheet" href="../externals/styles.css">
<title>Application Administrator Home: Order Pipelines</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body>

<jsp:include flush='true' page="ui/admToolbar.jsp"/>
<logic:notEqual name="ACCOUNT_DETAIL_FORM" property="id" value="0">
<jsp:include flush='true' page="ui/admAccountToolbar.jsp"/>
</logic:notEqual>
<jsp:include flush='true' page="ui/loginInfo.jsp"/>
<jsp:include flush='true' page="ui/accountCtx.jsp"/>


<div class="text">
<center>
<font color=red>
<html:errors/>
</font>
</center>

<html:form name="ACCOUNT_DETAIL_FORM" action="adminportal/pipelinemgr.do" scope="session" type="com.cleanwise.view.forms.AccountMgrDetailForm">

<table cellspacing="0" border="0" width="769"  class="mainbody">
<tr>
  <td><input type="text" name="contractIdDummy" size="5"/>
        <html:button property="action" onclick="popLocate2('../adminportal/contractlocate', 'contractIdDummy', '');" value="Locate Contract"/>
  </td>
  <td>
     <input type="text" name="freightHandlerIdDummy" size="5"/>
     <html:button property="action" onclick="popLocate2('../adminportal/freighthandlerlocate', 'freightHandlerIdDummy', '');" value="Locate Freight Handler"/>
  </td>
  <td>
     <input type="text" name="distributorIdDummy" size="5"/>
     <html:button property="action" onclick="popLocate2('../adminportal/distlocate', 'distributorIdDummy', '');" value="Locate Distributor"/>
  </td>
</tr>
<tr>
  <td colspan='3'><br><b>Order processing configuration</b></td>
</tr>
<tr>
  <td>
    <b>Account small order routing:</b>
  </td>
  <td>
    <html:select name="ACCOUNT_DETAIL_FORM" property="orderPipeline.pipelineData.pipelineStatusCd">
    <html:option value="<%=RefCodeNames.PIPELINE_STATUS_CD.ACTIVE%>" />
    <html:option value="<%=RefCodeNames.PIPELINE_STATUS_CD.INACTIVE%>" />
    </html:select>
  </td>
</tr>
<tr>
  <td>
    <b>Max item weight (lbs.):</b> </td>
  <td><html:text name="ACCOUNT_DETAIL_FORM" property="maxItemWeight" size="4" maxlength="6"/>
  </td>
  <td>
    <b>Max item cubic size (feet):</b> </td>
  <td><html:text name="ACCOUNT_DETAIL_FORM" property="maxItemCubicSize" size="4" maxlength="6"/>
  </td>
</tr>
<tr>
  <td colspan='20' align=center>
  <table cellspacing="2" border="1">
  <tr>
    <td valign=center><html:submit property="action"><app:storeMessage  key="admin.button.addOrderRoute"/></html:submit></td>
    <td><b>Zip</b><br><html:text name='ACCOUNT_DETAIL_FORM' property='newOrderRoutingData.zip' size='5'/></td>
    <td><b>Distributor Id</b><br><html:text name='ACCOUNT_DETAIL_FORM' property='newOrderRoutingData.distributorId' size='4'/></td>
    <td><b>Contract Id</b><br><html:text name='ACCOUNT_DETAIL_FORM' property='newOrderRoutingData.contractId' size='4'/></td>
    <td><b>Freight Handler Id</b><br><html:text name='ACCOUNT_DETAIL_FORM' property='newOrderRoutingData.freightHandlerId' size='4'/></td>
  </tr>
  <tr>
  <td></td><td></td>
    <td><b>Final Distributor Id</b><br><html:text name='ACCOUNT_DETAIL_FORM' property='newOrderRoutingData.finalDistributorId' size='4'/></td>
    <td><b>Final Contract Id</b><br><html:text name='ACCOUNT_DETAIL_FORM' property='newOrderRoutingData.finalContractId' size='4'/></td>
    <td><b>Final Freight Handler Id</b><br><html:text name='ACCOUNT_DETAIL_FORM' property='newOrderRoutingData.finalFreightHandlerId' size='4'/></td>
    <td><b>Ltl Freight Handler Id</b><br><html:text name='ACCOUNT_DETAIL_FORM' property='newOrderRoutingData.ltlFreightHandlerId' size='4'/></td>
  </tr>
  </table>
  </td>
</tr>
<logic:present name='ACCOUNT_DETAIL_FORM' property='orderRoutingDescTestResult'>
<tr>
  <td colspan='10'>
        <table cellspacing="2" border="1" width="769">
        <tr><td colspan='10'><b>Test Results:</b></td></tr>
        <tr>
                <td><b>Zip</b> </td>
                <td><bean:write name='ACCOUNT_DETAIL_FORM' property='orderRoutingDescTestResult.orderRoutingData.zip'/></td>
                </tr><tr>
                <td><b>Dist</b> </td>
                <logic:present name='ACCOUNT_DETAIL_FORM' property='orderRoutingDescTestResult.distributor'>
                <td>        <bean:write name='ACCOUNT_DETAIL_FORM' property='orderRoutingDescTestResult.distributor.shortDesc'/></td>
                <td><bean:write name='ACCOUNT_DETAIL_FORM' property='orderRoutingDescTestResult.orderRoutingData.distributorId'/></td>
                </logic:present>
                 </tr><tr>
                <td><b>Contract</b> </td>
                <logic:present name='ACCOUNT_DETAIL_FORM' property='orderRoutingDescTestResult.contract'>
                <td>        <bean:write name='ACCOUNT_DETAIL_FORM' property='orderRoutingDescTestResult.contract.shortDesc'/></td>
                <td><bean:write name='ACCOUNT_DETAIL_FORM' property='orderRoutingDescTestResult.orderRoutingData.contractId'/></td>
                </logic:present>
                 </tr><tr>
                <td><b>Freight Handler</b> </td>
                <logic:present name='ACCOUNT_DETAIL_FORM' property='orderRoutingDescTestResult.freightHandler'>
                <td>        <bean:write name='ACCOUNT_DETAIL_FORM' property='orderRoutingDescTestResult.freightHandler.shortDesc'/></td>
                <td><bean:write name='ACCOUNT_DETAIL_FORM' property='orderRoutingDescTestResult.orderRoutingData.freightHandlerId'/></td>
                </logic:present>
 </tr><tr>
                <td><b>Final Dist</b> </td>
                <logic:present name='ACCOUNT_DETAIL_FORM' property='orderRoutingDescTestResult.finalDistributor'>
                <td>        <bean:write name='ACCOUNT_DETAIL_FORM' property='orderRoutingDescTestResult.finalDistributor.shortDesc'/></td>
                <td><bean:write name='ACCOUNT_DETAIL_FORM' property='orderRoutingDescTestResult.orderRoutingData.finalDistributorId'/></td>
                </logic:present>
                 </tr><tr>
                <td><b>Final Contract</b> </td>

                <logic:present name='ACCOUNT_DETAIL_FORM' property='orderRoutingDescTestResult.finalContract'>
                <td>        <bean:write name='ACCOUNT_DETAIL_FORM' property='orderRoutingDescTestResult.finalContract.shortDesc'/></td>
                <td><bean:write name='ACCOUNT_DETAIL_FORM' property='orderRoutingDescTestResult.orderRoutingData.finalContractId'/></td>
                </logic:present>
                 </tr><tr>
                <td><b>Final Freight Handler</b> </td>
                <logic:present name='ACCOUNT_DETAIL_FORM' property='orderRoutingDescTestResult.finalFreightHandler'>
                <td>        <bean:write name='ACCOUNT_DETAIL_FORM' property='orderRoutingDescTestResult.finalFreightHandler.shortDesc'/></td>
                <td><bean:write name='ACCOUNT_DETAIL_FORM' property='orderRoutingDescTestResult.orderRoutingData.finalFreightHandlerId'/></td>
                </logic:present>
                 </tr><tr>
                <td><b>LTL Freight Handler</b> </td>
                <logic:present name='ACCOUNT_DETAIL_FORM' property='orderRoutingDescTestResult.ltlFreightHandler'>
                <td>        <bean:write name='ACCOUNT_DETAIL_FORM' property='orderRoutingDescTestResult.ltlFreightHandler.shortDesc'/></td>
                <td><bean:write name='ACCOUNT_DETAIL_FORM' property='orderRoutingDescTestResult.orderRoutingData.ltlFreightHandlerId'/></td>
                </logic:present>
        </tr>
        </table>
  </td>
</tr>
</logic:present>

<tr align=center>
  <td colspan=20>
    <b>Postal Code:</b><html:text name='ACCOUNT_DETAIL_FORM' 
  property='orderRouteTestOrderZip'/>
    <html:submit property="action"><app:storeMessage  key="admin.button.checkOrderRoute"/></html:submit>
  </td>
</tr>

<tr>
<td colspan=20 align=center>
  <html:submit property="action"><app:storeMessage  key="admin.button.saveAllPiplineData"/></html:submit>
  <html:reset><app:storeMessage  key="admin.button.reset"/></html:reset>
</td>
</tr>

</table>
</html:form>
</div>

<%
String reqzip = request.getParameter("orderRouteTestOrderZip");

if ( reqzip != null ) {

java.util.HashMap
  v_contracts = new java.util.HashMap(),
  v_distributors = new java.util.HashMap(),
  v_freightHandlers = new java.util.HashMap()
;
%>

<logic:iterate id="ord" 
  name="ACCOUNT_DETAIL_FORM" property="accountOrderRoutingList" 
  scope="session" type="com.cleanwise.service.api.value.OrderRoutingDescView" >
<% if ( ord.getContract() != null ) { 
	v_contracts.put( 
		new Integer(ord.getContract().getContractId()),
		ord.getContract());
 } %>

<% if ( ord.getDistributor() != null ) {
	v_distributors.put( 
		new Integer(ord.getDistributor().getBusEntityId()),
		ord.getDistributor());
 } %>

<% if ( ord.getFreightHandler() != null ) { 
	v_freightHandlers.put( 
		new Integer(ord.getFreightHandler().getBusEntityId()),
		ord.getFreightHandler());
 } %>

<% if ( ord.getFinalContract() != null ) { 
	v_contracts.put( 
		new Integer(ord.getFinalContract().getContractId()),
		ord.getFinalContract());
 } %>

<% if ( ord.getFinalDistributor() != null ) { 
	v_distributors.put( 
		new Integer(ord.getFinalDistributor().getBusEntityId()),
		ord.getFinalDistributor());
 } %>

<% if ( ord.getFinalFreightHandler() != null ) { 
	v_freightHandlers.put( 
		new Integer(ord.getFinalFreightHandler().getBusEntityId()),
		ord.getFinalFreightHandler());
 } %>

<% if ( ord.getLtlFreightHandler() != null ) { 
	v_freightHandlers.put( 
		new Integer(ord.getLtlFreightHandler().getBusEntityId()),
		ord.getLtlFreightHandler());
 } %>

</logic:iterate>

<%--Display the individual order routing entries--%>
<table with=769 class="results"> <tr>
<td width=50 class="tableheader">Zip</td>
<td width=200 class="tableheader">Contract</td>
<td class="tableheader">Freight Handler</td>
</tr></table>
<logic:iterate id="arrele" indexId="idx" 
  name="ACCOUNT_DETAIL_FORM" property="accountOrderRoutingList" 
  scope="session" type="com.cleanwise.service.api.value.OrderRoutingDescView" >

<bean:define id="zipstr" name='arrele' property='orderRoutingData.zip'
  type="java.lang.String" />
<bean:define id="account_id" name='arrele' property='orderRoutingData.accountId'
  type="java.lang.Integer" />

<% if (zipstr.startsWith(reqzip)) { %>  

<table with=769 class="results"> 
	<% if ((idx.intValue() % 2) == 0 ) { %>
<tr class="rowa">
	<% } else { %>
<tr class="rowb">
	<% } %>

<td width=50><%=zipstr%></td>
<td width=200>

		<div class="zitem" 
  style="display:block" 
  onclick="f_showZip(event,this)">
  
<% if ( arrele.getContract() != null ) { %>
<%=arrele.getContract().getShortDesc()%>
<% } else { %>
-
<% } %>
	<div class="zitem" 
 style="margin-left:2em;background-color: #cccccc;display:none; width:600;" 
 onclick="f_showZip(event,this)">

<form name="ZF<%=zipstr%>" action="pipelinemgr.do">
<input type="hidden" name="account_id" value="<%=account_id%>"/>
<input type="hidden" name="zip" value="<%=zipstr%>"/>
<input type="hidden" name="action" value="update_zip_entry"/>


                            <table>
<tr><td class="orth">Zip: </td>
<td >
<% if ( arrele.getOrderRoutingData() != null ) { %>
<%=arrele.getOrderRoutingData().getZip()%>
<% } %>

</td>
<td>
<input name="form_action" type=submit value="Update"/>
<input name="form_action" type=submit value="Delete"/>
</td>
</tr>

<tr>
<td class="orth">Contract: </td>
<td><% if ( arrele.getContract() != null ) { %>
<%=arrele.getContract().getShortDesc()%>
<% } %>

</td>
<td>
<select name="newContract">
<% if (arrele.getContract() != null) { %> 
<!-- curr contract = <%=arrele.getContract().getContractId()%> -->
<% } %>

<option value="0">--None--</option>
<% 
java.util.Iterator contracts = v_contracts.values().iterator();
while (contracts.hasNext() ) {
 ContractData tcon = (ContractData)contracts.next();
%>

<option 
<%
if (arrele.getContract() != null &&
  tcon.getContractId() == arrele.getContract().getContractId() ) { %>
 SELECTED=y
<% } %>
value="<%=tcon.getContractId()%>">
<%=tcon.getShortDesc()%> 
</option>
<% } %>

</select>

</td>
</tr><tr><td class="orth">Distributor: </td><td>
<% if ( arrele.getDistributor() != null ) { %>
	<%=arrele.getDistributor().getShortDesc()%>
<% } %>

</td>
<td>
<select name="newDistributor">
<option value="0">--None--</option>
<% 
java.util.Iterator dist = v_distributors.values().iterator();
while (dist.hasNext() ) {
 BusEntityData be = (BusEntityData)dist.next();
%>
<option
<% if (arrele.getDistributor() != null &&
  be.getBusEntityId() == arrele.getDistributor().getBusEntityId() ) {%>
 SELECTED=y
<% } %> 
value="<%=be.getBusEntityId()%>">
<%=be.getShortDesc()%>
</option>
<% } %>

</select>

</td>

</tr>
<tr><td class="orth">Freight Handler: </td><td>
<% if ( arrele.getFreightHandler() != null ) { %>
	<%=arrele.getFreightHandler().getShortDesc()%>
<% } %>

</td>
<td>
<select name="newFreightHandler">
<option value="0">--None--</option>
<% 
java.util.Iterator fhs = v_freightHandlers.values().iterator();
while (fhs.hasNext() ) {
 BusEntityData be = (BusEntityData)fhs.next();
%>
<option 
<%
if (arrele.getFreightHandler() != null &&
  be.getBusEntityId() == arrele.getFreightHandler().getBusEntityId() ) { %>
 SELECTED=y
<% } %>
value="<%=be.getBusEntityId()%>">
<%=be.getShortDesc()%>
</option>
<% } %>

</select>

</td>

</tr>

<tr><td class="orth">Final Contract: </td><td>
<% if ( arrele.getFinalContract() != null ) { %>
<%=arrele.getFinalContract().getShortDesc()%>
<% } %>

</td>
<td>
<select name="newFinalContract">
<option value="0">--None--</option>
<% 
 contracts = v_contracts.values().iterator();
while (contracts.hasNext() ) {
 ContractData tcon = (ContractData)contracts.next();
%>
<option 
<%
if (arrele.getFinalContract() != null &&
  tcon.getContractId() == arrele.getFinalContract().getContractId() ) { %>
 SELECTED=y
<% } %>

value="<%=tcon.getContractId()%>">
<%=tcon.getShortDesc()%>
</option>
<% } %>

</select>

</td>

</tr><tr><td class="orth">Final Distributor: </td><td>
<% if ( arrele.getFinalDistributor() != null ) { %>
	<%=arrele.getFinalDistributor().getShortDesc()%>
<% } %>

</td>
<td>
<select name="newFinalDistributor">
<option value="0">--None--</option>
<% 
 dist = v_distributors.values().iterator();
while (dist.hasNext() ) {
 BusEntityData be = (BusEntityData)dist.next();
%>
<option
<% if (arrele.getFinalDistributor() != null &&
  be.getBusEntityId() == arrele.getFinalDistributor().getBusEntityId() ) {%>
 SELECTED=y
<% } %> 
value="<%=be.getBusEntityId()%>">
<%=be.getShortDesc()%>
</option>
<% } %>

</select>

</td>

</tr>

<tr><td class="orth">Final Freight Handler: </td><td>
<% if ( arrele.getFinalFreightHandler() != null ) { %>
	<%=arrele.getFinalFreightHandler().getShortDesc()%>
<% } %>

</td>
<td>
<select name="newFinalFreightHandler">
<option value="0">--None--</option>
<% 
 fhs = v_freightHandlers.values().iterator();
while (fhs.hasNext() ) {
 BusEntityData be = (BusEntityData)fhs.next();
%>
<option 
<%
if (arrele.getFinalFreightHandler() != null &&
  be.getBusEntityId() == arrele.getFinalFreightHandler().getBusEntityId() ) { %>
 SELECTED=y
<% } %>
value="<%=be.getBusEntityId()%>">
<%=be.getShortDesc()%>
</option>
<% } %>

</select>

</td>

</tr>
<tr><td class="orth">LTL Freight Handler: </td><td>
<% if ( arrele.getLtlFreightHandler() != null ) { %>
	<%=arrele.getLtlFreightHandler().getShortDesc()%>
<% } %>


</td>
<td>
<select name="newLtlFreightHandler">
<option value="0">--None--</option>
<% 
 fhs = v_freightHandlers.values().iterator();
while (fhs.hasNext() ) {
 BusEntityData be = (BusEntityData)fhs.next();
%>
<option 
<% if (arrele.getLtlFreightHandler() != null &&
  be.getBusEntityId() == arrele.getLtlFreightHandler().getBusEntityId() ) {%>
SELECTED=y
<% } %>
value="<%=be.getBusEntityId()%>"
>
<%=be.getShortDesc()%>
</option>
<% } %>

</select>

</td>
</tr>
</table>


</form>

	</div>
		</div>

        </td>
<td>
<% if ( arrele.getFreightHandler() == null ||
        arrele.getFreightHandler().getShortDesc() == null ) { %>
-
<% } else { %>
<%=arrele.getFreightHandler().getShortDesc()%>
<% } %>
</td>
</tr></table>

        <% } /* end of if on zipstr starts with. */ %>
</logic:iterate>

<% } %>

<p>
<jsp:include flush='true' page="ui/admFooter.jsp"/>
</body>

</html:html>
