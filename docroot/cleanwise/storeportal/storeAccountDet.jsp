<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.forms.AccountMgrDetailForm" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<% 
boolean clwSwitch = ClwCustomizer.getClwSwitch(); 
String storeDir = ClwCustomizer.getStoreDir(); 
SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
AccountMgrDetailForm theForm = (AccountMgrDetailForm) 
  session.getAttribute("ACCOUNT_DETAIL_FORM");
%>

<app:checkLogon/>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="Location" value="account" type="java.lang.String" toScope="session"/>
<script language="JavaScript1.2">
<!--
function popLocate(pLoc, name, pDesc) {
  var loc = pLoc + ".do?feedField=" + name + "&amp;feedDesc=" + pDesc;
  locatewin = window.open(loc,"Locate", "menubar=no,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
  locatewin.focus();
  return false;
}

//-->
</script>

<html:html>

<head>
<link rel="stylesheet" href="../externals/styles.css">
<title>Application Administrator Home: Accounts</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<html:form styleId="477" name="ACCOUNT_DETAIL_FORM"
  action="/storeportal/accountdet"
  scope="session"
  type="com.cleanwise.view.forms.AccountMgrDetailForm">

<div class="text">
<center>
<font color=red>
<html:errors/>
</font>
</center>

<table ID=478 class="stpTable">

<logic:equal name="ACCOUNT_DETAIL_FORM" property="id" value="0">
<logic:equal name="<%=Constants.APP_USER%>" property="user.userTypeCd" value="<%=RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR%>">

<tr>
<td class=stpLabel>Store Id</td>
<td>
    <html:text name="ACCOUNT_DETAIL_FORM" property="storeId" size="5"/>
    <span class="reqind">*</span>
    <html:button property="action" onclick="popLocate('storelocate', 'storeId', 'storeName');" value="Locate Store"/>
</td>
<td class=stpLabel>Store Name</td>
<td>
    <html:text name="ACCOUNT_DETAIL_FORM" property="storeName" size="30" readonly="true" styleClass="mainbodylocatename"/>
</td>
</tr>

</logic:equal>
</logic:equal>

<logic:notEqual name="ACCOUNT_DETAIL_FORM" property="id" value="0">
<tr>
<td class=stpLabel>Store&nbsp;Id</td>
<td>
<bean:write name="ACCOUNT_DETAIL_FORM" property="storeId"/>
<html:hidden name="ACCOUNT_DETAIL_FORM" property="storeId"/>
</td>
<td class=stpLabel>Store Name</td>
<td>
<bean:write name="ACCOUNT_DETAIL_FORM" property="storeName"/>
<html:hidden name="ACCOUNT_DETAIL_FORM" property="storeName"/>
</td>
</tr>
</logic:notEqual>

</table>




<table ID=479 cellspacing="0" border="0" width="769"  class="mainbody">


<bean:define id="BusEntityId" type="java.lang.String"
  name="ACCOUNT_DETAIL_FORM" property="id"
  toScope="session" />

<tr>
<td class="largeheader" colspan="4">Account Detail</td>
</tr>
<tr>
<td><b>Account&nbsp;Id:</b></td>
<td>&nbsp;
<logic:notEqual name="ACCOUNT_DETAIL_FORM" property="id" value="0">
<bean:write name="ACCOUNT_DETAIL_FORM" property="id"/>
</logic:notEqual>
<html:hidden property="id"/>
</td>
<td><b>Name:</b></td>
<td>
<html:text name="ACCOUNT_DETAIL_FORM" property="name" size="30" maxlength="30"/>
<span class="reqind">*</span>
</td>
</tr>
<tr>
<td><b>Account&nbsp;Type</b></td>
<td>
<html:select name="ACCOUNT_DETAIL_FORM" property="typeDesc">
<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
<html:options  collection="Account.type.vector" property="value" />
</html:select>
<span class="reqind">*</span>
</td>
<td><b>Status:</b></td>
<td>
<html:select name="ACCOUNT_DETAIL_FORM" property="statusCd">
<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
<html:options  collection="Account.status.vector" property="value" />
</html:select>
<span class="reqind">*</span>
</td>
</tr>
<tr>
<% /*if(clwSwitch) {*/ %>
<td><b>Account&nbsp;Number:</b></td>
<td>
<html:text name="ACCOUNT_DETAIL_FORM" property="accountNumber" size="30" maxlength="30"/>
<span class="reqind">*</span>
</td>
<% /*} else {*/ %>
<!--
<td colspan="2">&nbsp;</td>
<html:hidden name="ACCOUNT_DETAIL_FORM" property="accountNumber" value="NA"/>
-->
<% /*}*/ %>
<% if(clwSwitch) { %>
<td><b>Order&nbsp;Minimum:</b></td>
<td>
<html:text name="ACCOUNT_DETAIL_FORM" property="orderMinimum" size="10" maxlength="15"/>
</td>
</tr>
<tr>
  <td><b>Credit&nbsp;Limit:</b></td>
  <td>
    <html:text name="ACCOUNT_DETAIL_FORM" property="creditLimit" size="30" maxlength="30"/>
  </td>
  <td><b>Credit&nbsp;Rating:</b></td>
  <td>
    <html:text name="ACCOUNT_DETAIL_FORM" property="creditRating" size="30" maxlength="30"/>
</td>
</tr>
<tr>
  <td><b>Budget&nbsp;Type:</b></td>
  <td>
        <html:select name="ACCOUNT_DETAIL_FORM" property="budgetTypeCd">
           <html:option value=""><app:storeMessage  key="admin.select"/></html:option>
           <html:options  collection="BUDGET_ACCRUAL_TYPE_CD" property="value" />
        </html:select>
  </td>
  <td>&nbsp;</td>
  <td>&nbsp;</td>
</tr>

<tr>
        <logic:present name="ACCOUNT_DETAIL_FORM" property="dataFieldProperties">
        <logic:iterate name="ACCOUNT_DETAIL_FORM" property="dataFieldProperties" id="dataF" indexId="i">
                <%String prop = "dataFieldProperty["+i+"].value";%>
                <logic:equal name="dataF" property="showAdmin" value="true">
                        <td><b><bean:write name="dataF" property="tag"/></b></td>
                        <td><b><html:text name="ACCOUNT_DETAIL_FORM" property="<%=prop%>"/></b></td>
                </logic:equal>
                <logic:notEqual name="dataF" property="showAdmin" value="true">
                        <td>&nbsp;</td><td>&nbsp;</td>
                </logic:notEqual>
                <bean:define id="modResult" value="<%=Integer.toString((i.intValue() + 1)%2)%>"/>
                <logic:equal name="modResult" value="0">
                        </tr>
                        <tr>
                </logic:equal>
        </logic:iterate>
        </logic:present>
        </tr>
</tr>

<% } else if("jd".equals(storeDir)){ %>
<!-- //Jd begin -->
<td><b>Weight&nbsp;Threshold:</b></td>
<td>
<html:text name="ACCOUNT_DETAIL_FORM" property="weightThreshold" size="10" maxlength="15"/>
</td>
</tr>
<tr>
  <td><b>Contract&nbsp;Threshold:</b></td>
  <td>
    <html:text name="ACCOUNT_DETAIL_FORM" property="contractThreshold" size="10" maxlength="15"/>
  </td>
  <td><b>Price&nbsp;Threshold:</b></td>
  <td>
    <html:text name="ACCOUNT_DETAIL_FORM" property="priceThreshold" size="10" maxlength="15"/>
</td>
</tr>
<!-- //Jd end -->
<% } %>
<tr>
<td colspan="4" class="largeheader"><br>Primary Contact</td>
</td>
<tr>
<td><b>First Name:</b></td>
<td>
<html:text name="ACCOUNT_DETAIL_FORM" property="name1" maxlength="40" size="40"/>
<span class="reqind">*</span>
</td>
<td><b>Phone:</b></td>
<td>
<html:text name="ACCOUNT_DETAIL_FORM" property="phone" maxlength="15"/>
<span class="reqind">*</span>
</td>
</tr>
<tr>
<td><b>Last Name:</b></td>
<td>
<html:text name="ACCOUNT_DETAIL_FORM" property="name2" maxlength="40" size="40" />
<span class="reqind">*</span>
</td>
<td><b>Fax:</b></td>
<td>
<html:text name="ACCOUNT_DETAIL_FORM" property="fax" maxlength="15"/>
<span class="reqind">*</span>
</td>
</tr>
<tr>
<td>&nbsp;</td>
<td>&nbsp;</td>
<td><b>Confirm Fax Back:</b></td>
<td>
<html:text name="ACCOUNT_DETAIL_FORM" property="faxBackConfirm" maxlength="15"/>
</td>
</tr>
<tr>
<td><b>Street&nbsp;Address&nbsp;1:</b></td>
<td>
<html:text name="ACCOUNT_DETAIL_FORM" property="streetAddr1" maxlength="80" size="40"/>
<span class="reqind">*</span>
</td>
<td><b>Email:</b></td>
<td>
<html:text name="ACCOUNT_DETAIL_FORM" property="emailAddress" maxlength="40"/>
<span class="reqind">*</span>
</td>
</tr>
<tr>
<td><b>Street&nbsp;Address&nbsp;2:</b></td>
<td>
<html:text name="ACCOUNT_DETAIL_FORM" property="streetAddr2" maxlength="80" size="40"/>
</td>
<td><b>Country:</b></td>
<td>
<html:select name="ACCOUNT_DETAIL_FORM" property="country">
<html:option value=""><app:storeMessage  key="admin.select.country"/></html:option>
<html:options  collection="countries.vector" property="value" />
</html:select>
<span class="reqind">*</span>
</td>
</tr>
<tr>
<td><b>Street&nbsp;Address&nbsp;3:</b></td>
<td>
<html:text name="ACCOUNT_DETAIL_FORM" property="streetAddr3" maxlength="80" size="40"/>
</td>

<td><b>State/Province:</b></td>
<td>
<html:text size="20" maxlength="80" name="ACCOUNT_DETAIL_FORM"
  property="stateOrProv" />
<span class="reqind">*</span>
</td>
</tr>
<tr>
<td><b>City:</b></td>
<td>
<html:text name="ACCOUNT_DETAIL_FORM" property="city" maxlength="40"/>
<span class="reqind">*</span>
</td>

<td><b>Zip/Postal Code:</b></td>
<td>
<html:text name="ACCOUNT_DETAIL_FORM" property="postalCode" maxlength="15"/>
<span class="reqind">*</span>
</td>
</tr>

<td colspan="4" class="largeheader"><br>Billing Address</td>
</td>
<tr>
<td><b>Street Address 1:</b></td>
<td>
<html:text name="ACCOUNT_DETAIL_FORM" property="billingAddress1" maxlength="80" size="40"/>
<span class="reqind">*</span>
</td>
<td><b>Country:</b></td>
<td>
<html:select name="ACCOUNT_DETAIL_FORM" property="billingCountry">
<html:option value=""><app:storeMessage  key="admin.select.country"/></html:option>
<html:options  collection="countries.vector" property="value" />
</html:select>
<span class="reqind">*</span>
</td>
</tr>
<tr>
<td><b>Street Address 2:</b></td>
<td>
<html:text name="ACCOUNT_DETAIL_FORM" property="billingAddress2" maxlength="80" size="40"/>
</td>
<td><b>State/Province:</b></td>
<td>
<html:text size="20" maxlength="80" name="ACCOUNT_DETAIL_FORM"
  property="billingState" />
<span class="reqind">*</span>
</td>
</tr>
<tr>
<td><b>Street Address 3:</b></td>
<td>
<html:text name="ACCOUNT_DETAIL_FORM" property="billingAddress3" maxlength="80" size="40"/>
</td>
<td><b>Zip/Postal Code:</b></td>
<td>
<html:text name="ACCOUNT_DETAIL_FORM" property="billingPostalCode" maxlength="15"/>
<span class="reqind">*</span>
</td>
</tr>
<tr>
<td><b>City:</b></td>
<td>
<html:text name="ACCOUNT_DETAIL_FORM" property="billingCity" maxlength="40"/>
<span class="reqind">*</span>
</td>
</tr>
<tr>
<td colspan="2"><b>Purchase Order Account Name:</b></td></td>
<td><html:text name="ACCOUNT_DETAIL_FORM" property="purchaseOrderAccountName" maxlength="40"/></td></td>
</tr>

<td colspan="2" class="largeheader"><br>Order Contact Information</td>
</td>
<tr>
<td><b>Order Phone:</b></td>
<td>
<html:text name="ACCOUNT_DETAIL_FORM" property="orderPhone" maxlength="15"/>
<span class="reqind">*</span>
</td>
</tr>
<tr>
<td><b>Order Fax:</b></td>
<td>
<html:text name="ACCOUNT_DETAIL_FORM" property="orderFax" maxlength="15"/>
<span class="reqind">*</span>
</td>
</tr>
<tr>
<td><b>Order Guide Comments:</b></td>
<td colspan=4>
<html:textarea name="ACCOUNT_DETAIL_FORM" property="comments" rows="4" 
  cols="60"/>
</td>
</tr>
<tr>
<td><b>Order Guide Note:</b>
<br>Appears on the second page of the PDF
version of an order guide.
</td>
<td colspan=4>
<html:textarea name="ACCOUNT_DETAIL_FORM" property="orderGuideNote" rows="4" 
  cols="60"/>
</td>
</tr>
<tr>
<td><b>Order Guide SKU Heading:</b>
</td>
<td colspan=4>
<html:text name="ACCOUNT_DETAIL_FORM" property="skuTag"/>
</td>
</tr>

<tr>
   <td colspan="2"><b>Allow The Following Order Item Actions To Display In the Runtime</b></td>
   <td colspan="2">&nbsp;</td>
</tr>
<tr>
   <td colspan="2">
   	<html:select name="ACCOUNT_DETAIL_FORM" property="runtimeDisplayOrderItemActionTypes" multiple="true" size="10">
		<html:option value=""><app:storeMessage  key="admin.none"/></html:option>
		<html:options  collection="ORDER_ITEM_DETAIL_ACTION_CD" property="value" />
	</html:select>
   </td>
   <td colspan="2">&nbsp;</td>
</tr>

<tr>
<td colspan="2">
<html:checkbox name="STORE_ACCOUNT_DETAIL_FORM" property="taxableIndicator"/>
<b>Taxable.</b>
</td>
<td colspan="2">
<html:checkbox name="STORE_ACCOUNT_DETAIL_FORM" property="allowChangePassword"/>
<b>Allow User to Change Password.</b>
</tr>

<tr>
<td colspan="2">
<html:checkbox name="ACCOUNT_DETAIL_FORM" property="crcShop"/>
<b>Do not allow CRC to shop for sites in this account.</b>
</td>
<td colspan="2"><b>Freight&nbsp;Charge&nbsp;Type:</b><html:select name="ACCOUNT_DETAIL_FORM" property="freightChargeType">
       <html:option value="<%=RefCodeNames.FREIGHT_CHARGE_CD.CC%>">Collect</html:option>
       <html:option value="<%=RefCodeNames.FREIGHT_CHARGE_CD.CF%>">Collect - Freight Credited to Payment Customer</html:option>
       <html:option value="<%=RefCodeNames.FREIGHT_CHARGE_CD.DF%>">Defined by Buyer and Seller</html:option>
       <html:option value="<%=RefCodeNames.FREIGHT_CHARGE_CD.PA%>">Advance Payment</html:option>
       <html:option value="<%=RefCodeNames.FREIGHT_CHARGE_CD.PB%>">Customer Pick Up/ Backhaul</html:option>
       <html:option value="<%=RefCodeNames.FREIGHT_CHARGE_CD.PC%>">Prepaid But Charged to customer</html:option>
       <html:option value="<%=RefCodeNames.FREIGHT_CHARGE_CD.PO%>">Prepaid Only</html:option>
       <html:option value="<%=RefCodeNames.FREIGHT_CHARGE_CD.PP%>">Prepaid by seller</html:option>
</html:select>
</td>
</tr>
<% if(clwSwitch) { %>
<tr>
<td colspan="2">
<html:checkbox name="ACCOUNT_DETAIL_FORM" property="makeShipToBillTo"/>
<b>Make ship to bill to.</b>
</td>
<td colspan="2"><b>Substitution Manager eMails:</b>
<html:text name="ACCOUNT_DETAIL_FORM" property="orderManagerEmails" size="60"/>
</td>
</tr>

<tr>
<td colspan="2">
<html:checkbox name="ACCOUNT_DETAIL_FORM" property="showScheduledDelivery"/>
<b>Enable inventory order processing.</b>
</td>
<td><b>Edi Ship To Prefix:</b></td>
<td><html:text name="ACCOUNT_DETAIL_FORM" property="ediShipToPrefix" size="10"/></td>
</tr>

<tr>
<td colspan="2">
<html:checkbox name="ACCOUNT_DETAIL_FORM" property="customerRequestPoAllowed"/>
<b>Allow customer to enter po number.</b>
</td>
<td colspan="2">
<%--<b>Rush order charge</b>
<html:text name="ACCOUNT_DETAIL_FORM" property="rushOrderCharge"/>--%>
</td>
</tr>

<tr>
  <td colspan="2"><html:checkbox name="ACCOUNT_DETAIL_FORM" property="authorizedForResale"/><b>Authorized For Re-Selling Items</b></td>
  <td><b>Re-Sale ERP Account Number</b></td>
  <td><html:text name="ACCOUNT_DETAIL_FORM" property="reSaleAccountErpNumber" size="30" maxlength="30"/></td>
</tr>
<tr>
  <td colspan="2"><html:checkbox name="ACCOUNT_DETAIL_FORM" property="allowOrderConsolidation"/><b>Allow Order Consolidation</b></td>
  <td><b>Delivery Cutoff Days</b></td>
  <td><html:text name="ACCOUNT_DETAIL_FORM" property="scheduleCutoffDays" size="5" /></td>
</tr>
<tr>
  <td colspan="2"><html:checkbox name="ACCOUNT_DETAIL_FORM" property="showDistSkuNum"/><b>Show Distributor SKU #</b></td>
  <td><b>&nbsp;</b></td>
  <td>&nbsp;</td>
</tr>
<tr>
  <td colspan='2'>
  <% AccountData accountD = theForm.getAccountData();
  if(accountD!=null) {
    BusEntityParameterDataVector rebateHistDV = 
                          accountD.getAccountParameterHistory("Rebate Persent");
    if(rebateHistDV!=null) {
    for(Iterator iter=rebateHistDV.iterator(); iter.hasNext();) {
      BusEntityParameterData rebateHistD = (BusEntityParameterData) iter.next();
      Date expDate = rebateHistD.getExpDate();
      if(expDate==null) break;
      Date effDate = rebateHistD.getEffDate();
      String effDateS =(effDate==null)?"":sdf.format(effDate);
      String expDateS = sdf.format(expDate);
  %>
  <b>Rebate Percent</b><%=rebateHistD.getValue()%>
  <b>Eff Date</b><%=effDateS%>
  <b>Exp Date</b><%=expDateS%>
  <%
  }}}
  %>
  <b>Rebate Percent</b> <html:text name="ACCOUNT_DETAIL_FORM" property="rebatePersent" size="5"/>
  <b>Effecitve Date</b> <html:text name="ACCOUNT_DETAIL_FORM" property="rebateEffDate" size="10"/>
  </td>
  <td colspan='2'>&nbsp;</td>
</tr>
<% } else { %>
<html:hidden name="ACCOUNT_DETAIL_FORM" property="makeShipToBillTo" value="false"/>
<html:hidden name="ACCOUNT_DETAIL_FORM" property="showScheduledDelivery" value="false"/>
<html:hidden name="ACCOUNT_DETAIL_FORM" property="allowOrderConsolidation" value="false"/>
<% } %>
<tr>
  <td><b>Target Margin</b></td>
  <td><html:text name="ACCOUNT_DETAIL_FORM" property="targetMarginStr" size="10" maxlength="10"/></td>

  <td><b>Customer System Approval Code</b></td>
  <td>
        <html:select name="ACCOUNT_DETAIL_FORM" property="customerSystemApprovalCd">
        <html:option value=""><app:storeMessage  key="admin.select"/></html:option>
        <html:options  collection="CUSTOMER_SYSTEM_APPROVAL_CD" property="value" />
        </html:select>
  </td>

</tr>
<tr>
<td colspan=4 align=center>
<html:submit property="action">
<app:storeMessage  key="global.action.label.save"/>
</html:submit>
<html:reset>
<app:storeMessage  key="admin.button.reset"/>
</html:reset>
<logic:notEqual name="ACCOUNT_DETAIL_FORM" property="id" value="0">
<html:submit property="action">
<app:storeMessage  key="global.action.label.delete"/>
</html:submit>
</logic:notEqual>
</td>
</tr>

</table>
</html:form>

</div>

</html:html>


