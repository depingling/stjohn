<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.forms.StoreAccountMgrDetailForm" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.view.utils.ShopTool"%>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser"%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<%
boolean clwSwitch = ClwCustomizer.getClwSwitch();
String storeDir = ClwCustomizer.getStoreDir();
SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
StoreAccountMgrDetailForm theForm = (StoreAccountMgrDetailForm)session.getAttribute("STORE_ACCOUNT_DETAIL_FORM");
boolean isAuthorizedForResale=false;
String controlAccountData="YES";
CleanwiseUser cwu = ShopTool.getCurrentUser(request);
String erpSystemCode = cwu.getUserStore().getErpSystemCode();
if(theForm!=null)
   {

   controlAccountData=theForm.isFirstUpdate()==true?"NO":"YES";
   isAuthorizedForResale=theForm.isAuthorizedForResale();
   }

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

function enableCheckBox(self, boxName) {
    if (self.checked) {
        self.form.elements[boxName].disabled = false;
    } else {
        self.form.elements[boxName].checked = false;
        self.form.elements[boxName].disabled = true;
    }
}

function checkDependencies(formName) {
    var boxToCheck = document.forms[formName].elements["allowSetWorkOrderPoNumber"];
    var boxToSet = document.forms[formName].elements["workOrderPoNumberIsRequired"];

    if (boxToCheck.checked) {
        boxToSet.disabled = false;
    } else {
        boxToSet.disabled = true;
    }
}

//-->
</script>

<html:html>

<head>
<link rel="stylesheet" href="../externals/styles.css">
<title>Application Administrator Home: Accounts</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body onLoad = "javascript: checkDependencies('STORE_ACCOUNT_DETAIL_FORM');">
<html:form styleId="480" name="STORE_ACCOUNT_DETAIL_FORM"
  action="/storeportal/storeAccountDetail"
  scope="session"
  type="com.cleanwise.view.forms.StoreAccountMgrDetailForm">

<html:hidden name="STORE_ACCOUNT_DETAIL_FORM" property="controlAccountData" value="<%=controlAccountData%>"/>

<div class="text">


<table ID=481 class="stpTable">

<logic:equal name="STORE_ACCOUNT_DETAIL_FORM" property="id" value="0">
<logic:equal name="<%=Constants.APP_USER%>" property="user.userTypeCd" value="<%=RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR%>">

<tr>
<td class=stpLabel>Store Id</td>
<td>
    <html:text name="STORE_ACCOUNT_DETAIL_FORM" property="storeId" size="5"/>
    <span class="reqind">*</span>
    <html:button property="action" onclick="popLocate('storelocate', 'storeId', 'storeName');" value="Locate Store"/>
</td>
<td class=stpLabel>Store Name</td>
<td>
    <html:text name="STORE_ACCOUNT_DETAIL_FORM" property="storeName" size="30" readonly="true" styleClass="mainbodylocatename"/>
</td>
</tr>

</logic:equal>
</logic:equal>

<logic:notEqual name="STORE_ACCOUNT_DETAIL_FORM" property="id" value="0">
<tr>
<td class=stpLabel>Store&nbsp;Id</td>
<td>
<bean:write name="STORE_ACCOUNT_DETAIL_FORM" property="storeId"/>
<html:hidden name="STORE_ACCOUNT_DETAIL_FORM" property="storeId"/>
</td>
<td class=stpLabel>Store Name</td>
<td>
<bean:write name="STORE_ACCOUNT_DETAIL_FORM" property="storeName"/>
<html:hidden name="STORE_ACCOUNT_DETAIL_FORM" property="storeName"/>
</td>
</tr>
</logic:notEqual>

</table>




<table ID=482 cellspacing="0" border="0" width="<%=Constants.TABLEWIDTH%>"  class="mainbody">


<bean:define id="BusEntityId" type="java.lang.String"
  name="STORE_ACCOUNT_DETAIL_FORM" property="id"
  toScope="session" />

<tr>
<td class="largeheader" colspan="4">Account Detail</td>
</tr>
<tr>
<td><b>Account&nbsp;Id:</b></td>
<td>&nbsp;
<logic:notEqual name="STORE_ACCOUNT_DETAIL_FORM" property="id" value="0">
<bean:write name="STORE_ACCOUNT_DETAIL_FORM" property="id"/>
</logic:notEqual>
<html:hidden property="id"/>
</td>
<td><b>Name:</b></td>
<td>
<html:text name="STORE_ACCOUNT_DETAIL_FORM" property="name" size="30" maxlength="30"/>
<span class="reqind">*</span>
</td>
</tr>
<tr>
<td><b>Account&nbsp;Type</b></td>
<td>
<html:select name="STORE_ACCOUNT_DETAIL_FORM" property="typeDesc">
<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
<html:options  collection="Account.type.vector" property="value" />
</html:select>
<span class="reqind">*</span>
</td>
<td><b>Status:</b></td>
<td>
<html:select name="STORE_ACCOUNT_DETAIL_FORM" property="statusCd">
<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
<html:options  collection="Account.status.vector" property="value" />
</html:select>
<span class="reqind">*</span>
</td>
</tr>
<tr>
<% /*if(clwSwitch) {*/
%>
<td><b>Account&nbsp;Number:</b></td>

   <td>
&nbsp;&nbsp;<bean:write name="STORE_ACCOUNT_DETAIL_FORM" property="accountNumber"/>
</td>
<% /*} else {*/ %>
<!--
<td colspan="2">&nbsp;</td>
<html:hidden name="STORE_ACCOUNT_DETAIL_FORM" property="accountNumber" value="NA"/>
-->
<% /*}*/ %>
<% if(clwSwitch) { %>
<td><b>Order&nbsp;Minimum:</b></td>
<td>
<html:text name="STORE_ACCOUNT_DETAIL_FORM" property="orderMinimum" size="10" maxlength="15"/>
</td>
</tr>
<tr>
  <td><b>Credit&nbsp;Limit:</b></td>
  <td>
    <html:text name="STORE_ACCOUNT_DETAIL_FORM" property="creditLimit" size="30" maxlength="30"/>
  </td>
  <td><b>Credit&nbsp;Rating:</b></td>
  <td>
    <html:text name="STORE_ACCOUNT_DETAIL_FORM" property="creditRating" size="30" maxlength="30"/>
</td>
</tr>
<tr>
  <td><b>Budget&nbsp;Type:</b></td>
  <td>
        <html:select name="STORE_ACCOUNT_DETAIL_FORM" property="budgetTypeCd">
           <html:option value=""><app:storeMessage  key="admin.select"/></html:option>
           <html:options  collection="BUDGET_ACCRUAL_TYPE_CD" property="value" />
        </html:select>
<span class="reqind">*</span>
  </td>
  <%--<td>&nbsp;</td>
  <td>&nbsp;</td>--%>
  <td><b>GL&nbsp;Code&nbsp;Transformation</b></td>
  <td>
		<html:select name="STORE_ACCOUNT_DETAIL_FORM" property="glTransformationType">
           <html:option value=""><app:storeMessage  key="admin.select"/></html:option>
           <html:options  collection="GL_TRANSFORMATION_TYPE" property="value" />
        </html:select>
  </td>
</tr>

<tr>
  <td><b>Time Zone:</b></td>
  <td>
		<html:select name="STORE_ACCOUNT_DETAIL_FORM" property="timeZoneCd">
           <html:option value=""><app:storeMessage  key="admin.select"/></html:option>
		   <html:options  collection="TimeZone.vector" property="value" />
        </html:select>
  </td>
<%--
  <td>&nbsp;</td>
  <td>&nbsp;</td>
--%>
  <td colspan="2"><html:checkbox name="STORE_ACCOUNT_DETAIL_FORM" property="allowSiteLLC"/>
  <b>Allow Site LLC Override</b>&nbsp;&nbsp;&nbsp;&nbsp;
</tr>


<tr>
  <td><b>Customer&nbsp;Service&nbsp;Email</b></td>
  <td>
    <html:text name="STORE_ACCOUNT_DETAIL_FORM" property="customerEmail" size="30" maxlength="80"/>
  </td>

  <td><b>Distributor&nbsp;Account&nbsp;Reference&nbsp;Number</b></td>
  <td>
	<html:text name="STORE_ACCOUNT_DETAIL_FORM" property="distributorAccountRefNum" size="30" maxlength="30"/>
  </td>
</tr>

<tr>
  <td><b>Contact&nbsp;Us&nbsp;CC&nbsp;Email</b></td>
  <td>
    <html:text name="STORE_ACCOUNT_DETAIL_FORM" property="contactUsCCEmail" size="30" maxlength="80"/>
  </td>
</tr>

<tr>
  <td><b>Default&nbsp;Email</b></td>
  <td>
    <html:text name="STORE_ACCOUNT_DETAIL_FORM" property="defaultEmail" size="30" maxlength="80"/>&nbsp; Ex:&nbsp;James&lt;default@espendwise.com&gt;
  </td>
</tr>
<tr>
        <logic:present name="STORE_ACCOUNT_DETAIL_FORM" property="dataFieldProperties">
        <logic:iterate name="STORE_ACCOUNT_DETAIL_FORM" property="dataFieldProperties" id="dataF" indexId="i">
                <%String prop = "dataFieldProperty["+i+"].value";%>
                <logic:equal name="dataF" property="showAdmin" value="true">
                        <td><b><bean:write name="dataF" property="tag"/></b></td>
                        <td><b><html:text name="STORE_ACCOUNT_DETAIL_FORM" property="<%=prop%>"/></b></td>
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
<html:text name="STORE_ACCOUNT_DETAIL_FORM" property="weightThreshold" size="10" maxlength="15"/>
</td>
</tr>
<tr>
  <td><b>Contract&nbsp;Threshold:</b></td>
  <td>
    <html:text name="STORE_ACCOUNT_DETAIL_FORM" property="contractThreshold" size="10" maxlength="15"/>
  </td>
  <td><b>Price&nbsp;Threshold:</b></td>
  <td>
    <html:text name="STORE_ACCOUNT_DETAIL_FORM" property="priceThreshold" size="10" maxlength="15"/>
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
<html:text name="STORE_ACCOUNT_DETAIL_FORM" property="name1" maxlength="40" size="40"/>
</td>
<td><b>Phone:</b></td>
<td>
<html:text name="STORE_ACCOUNT_DETAIL_FORM" property="phone" maxlength="15"/>
</td>
</tr>
<tr>
<td><b>Last Name:</b></td>
<td>
<html:text name="STORE_ACCOUNT_DETAIL_FORM" property="name2" maxlength="40" size="40" />
</td>
<td><b>Fax:</b></td>
<td>
<html:text name="STORE_ACCOUNT_DETAIL_FORM" property="fax" maxlength="15"/>
</td>
</tr>
<tr>
<td>&nbsp;</td>
<td>&nbsp;</td>
<td><b>Confirm Fax Back:</b></td>
<td>
<html:text name="STORE_ACCOUNT_DETAIL_FORM" property="faxBackConfirm" maxlength="15"/>
</td>
</tr>
<tr>
<td><b>Street&nbsp;Address&nbsp;1:</b></td>
<td>
<html:text name="STORE_ACCOUNT_DETAIL_FORM" property="streetAddr1" maxlength="80" size="40"/>
</td>
<td><b>Email:</b></td>
<td>
<html:text name="STORE_ACCOUNT_DETAIL_FORM" property="emailAddress" maxlength="80"/>
</td>
</tr>
<tr>
<td><b>Street&nbsp;Address&nbsp;2:</b></td>
<td>
<html:text name="STORE_ACCOUNT_DETAIL_FORM" property="streetAddr2" maxlength="80" size="40"/>
</td>
<td><b>Country:</b></td>
<td>
<html:select name="STORE_ACCOUNT_DETAIL_FORM" property="country">
<html:option value=""><app:storeMessage  key="admin.select.country"/></html:option>
<html:options  collection="country.vector" labelProperty="uiName" property="shortDesc" />
</html:select>
</td>
</tr>
<tr>
<td><b>Street&nbsp;Address&nbsp;3:</b></td>
<td>
<html:text name="STORE_ACCOUNT_DETAIL_FORM" property="streetAddr3" maxlength="80" size="40"/>
</td>

<td><b>State/Province:</b></td>
<td>
<html:text size="20" maxlength="80" name="STORE_ACCOUNT_DETAIL_FORM" property="stateOrProv" />
</td>
</tr>
<tr>
<td><b>City:</b></td>
<td>
<html:text name="STORE_ACCOUNT_DETAIL_FORM" property="city" maxlength="40"/>
</td>

<td><b>Zip/Postal Code:</b></td>
<td>
<html:text name="STORE_ACCOUNT_DETAIL_FORM" property="postalCode" maxlength="15"/>
</td>
</tr>

<td colspan="4" class="largeheader"><br>Billing Address</td>
</td>
<tr>
<td><b>Street Address 1:</b></td>
<td>
<html:text name="STORE_ACCOUNT_DETAIL_FORM" property="billingAddress1" maxlength="80" size="40"/>
<span class="reqind">*</span>
</td>
<td><b>Country:</b></td>
<td>
<html:select name="STORE_ACCOUNT_DETAIL_FORM" property="billingCountry">
<html:option value=""><app:storeMessage  key="admin.select.country"/></html:option>
<html:options  collection="country.vector" labelProperty="uiName" property="shortDesc" />
</html:select>
<span class="reqind">*</span>
</td>
</tr>
<tr>
<td><b>Street Address 2:</b></td>
<td>
<html:text name="STORE_ACCOUNT_DETAIL_FORM" property="billingAddress2" maxlength="80" size="40"/>
</td>
<td><b>State/Province:</b></td>
<td>
<html:text size="20" maxlength="80" name="STORE_ACCOUNT_DETAIL_FORM"
  property="billingState" />
<span class="reqind">*</span>
</td>
</tr>
<tr>
<td><b>Street Address 3:</b></td>
<td>
<html:text name="STORE_ACCOUNT_DETAIL_FORM" property="billingAddress3" maxlength="80" size="40"/>
</td>
<td><b>Zip/Postal Code:</b></td>
<td>
<html:text name="STORE_ACCOUNT_DETAIL_FORM" property="billingPostalCode" maxlength="15"/>
<span class="reqind">*</span>
</td>
</tr>
<tr>
<td><b>City:</b></td>
<td>
<html:text name="STORE_ACCOUNT_DETAIL_FORM" property="billingCity" maxlength="40"/>
<span class="reqind">*</span>
</td>
</tr>
<tr>
<td colspan="2"><b>Purchase Order Account Name:</b>&nbsp;&nbsp;
<html:text name="STORE_ACCOUNT_DETAIL_FORM" property="purchaseOrderAccountName" maxlength="40"/></td>
<td colspan="2">&nbsp;</td>
</tr>

<td colspan="2" class="largeheader"><br>Order Contact Information</td>
</td>
<tr>
<td><b>Order Phone:</b></td>
<td>
<html:text name="STORE_ACCOUNT_DETAIL_FORM" property="orderPhone" maxlength="15"/>
</td>
</tr>
<tr>
<td><b>Order Fax:</b></td>
<td>
<html:text name="STORE_ACCOUNT_DETAIL_FORM" property="orderFax" maxlength="15"/>
</td>
</tr>
<tr>
<td><b>Order Guide Comments:</b></td>
<td colspan=4>
<html:textarea name="STORE_ACCOUNT_DETAIL_FORM" property="comments" rows="4"
  cols="60"/>
</td>
</tr>
<tr>
<td><b>Order Guide Note:</b>
<br>Appears on the second page of the PDF
version of an order guide.
</td>
<td colspan=4>
<html:textarea name="STORE_ACCOUNT_DETAIL_FORM" property="orderGuideNote" rows="4"
  cols="60"/>
</td>
</tr>
<tr>
<td><b>Order Guide SKU Heading:</b>
</td>
<td colspan=4>
<html:text name="STORE_ACCOUNT_DETAIL_FORM" property="skuTag"/>
</td>
</tr>

<tr>
   <td colspan="2"><b>Allow The Following Order Item Actions To Display In the Runtime</b></td>
   <td colspan="2">&nbsp;</td>
</tr>
<tr>
   <td colspan="2">
     <html:select name="STORE_ACCOUNT_DETAIL_FORM" property="runtimeDisplayOrderItemActionTypes" multiple="true" size="10">
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
<html:checkbox name="STORE_ACCOUNT_DETAIL_FORM" property="crcShop"/>
<b>Do not allow CRC to shop for sites in this account.</b>
</td>
<td colspan="2"><b>Freight&nbsp;Charge&nbsp;Type:</b><html:select name="STORE_ACCOUNT_DETAIL_FORM" property="freightChargeType">
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
<html:checkbox name="STORE_ACCOUNT_DETAIL_FORM" property="makeShipToBillTo"/>
<b>Make ship to bill to.</b>
</td>
<td colspan="2"><b>Substitution Manager eMails:</b>
<html:text name="STORE_ACCOUNT_DETAIL_FORM" property="orderManagerEmails" size="80"/>
</td>
</tr>

<tr>
<td colspan="2">
<html:checkbox name="STORE_ACCOUNT_DETAIL_FORM" property="showScheduledDelivery"/>
<b>Enable inventory order processing.</b>
</td>
<td><b>Edi Ship To Prefix:</b></td>
<td>
&nbsp;&nbsp;<bean:write name="STORE_ACCOUNT_DETAIL_FORM" property="ediShipToPrefix"/>
</td>

</tr>

<tr>
<td colspan="2">
<html:checkbox name="STORE_ACCOUNT_DETAIL_FORM" property="customerRequestPoAllowed"/>
<b>Allow customer to enter po number.</b>
</td>
<td colspan="2">
<%--<b>Rush order charge</b>
<html:text name="STORE_ACCOUNT_DETAIL_FORM" property="rushOrderCharge"/> --%>
</td>
</tr>

<tr>
  <td colspan="2"><html:checkbox name="STORE_ACCOUNT_DETAIL_FORM" property="authorizedForResale"/><b>Authorized For Re-Selling Items</b></td>
  <td><b>Re-Sale ERP Account Number</b></td>
  <td>
    <% if(isAuthorizedForResale) {%>
    &nbsp;&nbsp;<bean:write name="STORE_ACCOUNT_DETAIL_FORM" property="reSaleAccountErpNumber"/>
    <%} else{%>
        <html:hidden name="STORE_ACCOUNT_DETAIL_FORM"  property="reSaleAccountErpNumber" value=""/>
    <%} %>
  </td>
</tr>
<tr>
  <td colspan="2"><html:checkbox name="STORE_ACCOUNT_DETAIL_FORM" property="allowOrderConsolidation"/><b>Allow Order Consolidation</b></td>
  <td><b>Delivery Cutoff Days</b></td>
  <td><html:text name="STORE_ACCOUNT_DETAIL_FORM" property="scheduleCutoffDays" size="5" /></td>
</tr>
<tr>
  <td colspan="2"><html:checkbox name="STORE_ACCOUNT_DETAIL_FORM" property="showDistSkuNum"/><b>Show Distributor SKU #</b></td>
  <td><b>Account Folder</b></td>
  <td><html:text name="STORE_ACCOUNT_DETAIL_FORM" property="accountFolder" size="30" /></td>
</tr>
<tr>
  <td colspan="2"><html:checkbox name="STORE_ACCOUNT_DETAIL_FORM" property="showDistDeliveryDate"/><b>Show Dist. Delivery Date</b></td>
  <td colspan="2"><html:checkbox name="STORE_ACCOUNT_DETAIL_FORM" property="showSPL"/><b>Show SPL flag</b></td>
</tr>
<tr>
  <td colspan="2"><html:checkbox name="STORE_ACCOUNT_DETAIL_FORM" property="holdPO"/><b>Set PO on Hold</b></td>
  <td colspan="2"><b>Auto Order Qty Factor:</b>&nbsp;
      <html:text name="STORE_ACCOUNT_DETAIL_FORM" property="autoOrderFactor" size="5"/><span class="reqind">*</span></td>
</tr>

<tr>
  <td colspan="2"><html:checkbox name="STORE_ACCOUNT_DETAIL_FORM" property="modifyQtyBy855"/><b>Modify Order Quantity and cost (price too if store has "Set Cost and Price Equal" enabled) by Order Acknowledgment(855)</b></td>
  <td colspan="2"><html:checkbox name="STORE_ACCOUNT_DETAIL_FORM" property="allowOrderInvItems"/><b>Allow Order Inventory Items in Scheduled cart period</b></td>
</tr>
<tr>
    <td colspan="2"><html:checkbox name="STORE_ACCOUNT_DETAIL_FORM" property="allowReorder"/><b>Allow Reorder</b></td>
    <td colspan="2"><html:checkbox name="STORE_ACCOUNT_DETAIL_FORM" property="usePhysicalInventory"/><b>Use Xpedx Physical Inventory</b></td>
</tr>
<tr>
  <td colspan="2"><html:checkbox name="STORE_ACCOUNT_DETAIL_FORM" property="createOrderBy855"/><b>Create Order by Order Acknowledgment(855)</b></td>
  <td colspan="2"> <b>Shop UI Type</b>
      <html:select name="STORE_ACCOUNT_DETAIL_FORM" property="shopUIType">
          <html:option value="<%=RefCodeNames.SHOP_UI_TYPE.B2C%>">
              <%=RefCodeNames.SHOP_UI_TYPE.B2C%>
          </html:option>
          <html:option value="<%=RefCodeNames.SHOP_UI_TYPE.B2B%>">
              <%=RefCodeNames.SHOP_UI_TYPE.B2B%>
          </html:option>
      </html:select>
    </td>
</tr>
<!--<tr>
  <td colspan="2"><html:checkbox name="STORE_ACCOUNT_DETAIL_FORM" property="createOrderItemsBy855"/><b>Create New Order Lines by Order Acknowledgment(855)</b></td>
  <td colspan="2">&nbsp;</td>
</tr>-->
<tr>
  <td colspan="2"><html:checkbox name="STORE_ACCOUNT_DETAIL_FORM" property="allowModernShopping"/>
  <b>Allow Modern Shopping</b>&nbsp;&nbsp;&nbsp;&nbsp;
  <b>Folder:</b>
  <html:text name="STORE_ACCOUNT_DETAIL_FORM" property="accountFolderNew" size="30" />
  </td>
   <td colspan="2">
     <b>FAQ Link:</b>&nbsp;&nbsp;&nbsp;&nbsp;
     <html:text name="STORE_ACCOUNT_DETAIL_FORM" property="faqLink" size="30" />
   </td></tr>
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
  <b>Rebate Percent</b> <html:text name="STORE_ACCOUNT_DETAIL_FORM" property="rebatePersent" size="5"/>
  <b>Effecitve Date</b> <html:text name="STORE_ACCOUNT_DETAIL_FORM" property="rebateEffDate" size="10"/>
  </td>
  <td colspan="2"><b>Dist Inventory Status Display:</b>
     <html:select name="STORE_ACCOUNT_DETAIL_FORM" property="showDistInventory">
       <html:option value="<%=RefCodeNames.DIST_INVENTORY_DISPLAY.DO_NOT_SHOW%>">
        <%=RefCodeNames.DIST_INVENTORY_DISPLAY.DO_NOT_SHOW%></html:option>
       <html:option value="<%=RefCodeNames.DIST_INVENTORY_DISPLAY.SHOW_FLAG%>">
       <%=RefCodeNames.DIST_INVENTORY_DISPLAY.SHOW_FLAG%></html:option>
       <html:option value="<%=RefCodeNames.DIST_INVENTORY_DISPLAY.SHOW_QUANTITIES%>">
       <%=RefCodeNames.DIST_INVENTORY_DISPLAY.SHOW_QUANTITIES%></html:option>
    </html:select>
  </td>
</tr>
<% } else { %>
<html:hidden name="STORE_ACCOUNT_DETAIL_FORM" property="makeShipToBillTo" value="false"/>
<html:hidden name="STORE_ACCOUNT_DETAIL_FORM" property="showScheduledDelivery" value="false"/>
<html:hidden name="STORE_ACCOUNT_DETAIL_FORM" property="allowOrderConsolidation" value="false"/>
<% } %>
<tr>
  <td><b>Target Margin</b></td>
  <td><html:text name="STORE_ACCOUNT_DETAIL_FORM" property="targetMarginStr" size="10" maxlength="10"/></td>

  <td><b>Customer System Approval Code</b></td>
  <td>
        <html:select name="STORE_ACCOUNT_DETAIL_FORM" property="customerSystemApprovalCd">
        <html:option value=""><app:storeMessage  key="admin.select"/></html:option>
        <html:options  collection="CUSTOMER_SYSTEM_APPROVAL_CD" property="value" />
        </html:select>
  </td>

</tr>
<tr>

  <td colspan="2">
	<html:checkbox name="STORE_ACCOUNT_DETAIL_FORM" property="showInvCartTotal"/><b>Show inventory items price in cart total</b>
  </td>

  <td><b>Cart Reminder Interval(days)</b></td>
  <td><html:text name="STORE_ACCOUNT_DETAIL_FORM" property="cartReminderInterval" size="10" maxlength="10"/></td>

</tr>

<tr>
	<td colspan="2">
		<html:checkbox name="STORE_ACCOUNT_DETAIL_FORM" property="showMyShoppingLists"/><b>Display My Shopping Lists</b>
	</td>

    <td colspan="2"><html:checkbox name="STORE_ACCOUNT_DETAIL_FORM" property="allowCreditCard"/>&nbsp;<b>Allow pay by Credit Card</b></td>
</tr>

<tr>
	<td colspan="2">
		<html:checkbox name="STORE_ACCOUNT_DETAIL_FORM" property="showExpressOrder"/><b>Express Order</b>
	</td>
	<td colspan="2"><html:checkbox name="STORE_ACCOUNT_DETAIL_FORM" property="addServiceFee"/><b>Add Service Fee</b></td>
</tr>

<tr>
	<td colspan="2">
      <html:checkbox name="STORE_ACCOUNT_DETAIL_FORM" property="connectionCustomer"/><b>Connection Customer</b>
	</td>
	<td colspan="2"><html:checkbox name="STORE_ACCOUNT_DETAIL_FORM" property="showReBillOrder"/><b>Display Rebill Order in Checkout</b></td>
</tr>

<tr>
	<td colspan="2">
      <html:checkbox name="STORE_ACCOUNT_DETAIL_FORM" property="modifyCustPoNumBy855"/><b>Modify customer po # by Order Acknowledgment(855)</b>
	</td>
	<td colspan="2">
      <html:checkbox name="STORE_ACCOUNT_DETAIL_FORM" property="allowAlternateShipTo"/><b>Alternate Ship to Override</b>
	</td>	
</tr>

<tr>
	<td colspan="2">
      <html:checkbox name="STORE_ACCOUNT_DETAIL_FORM" property="supportsBudget"/><b>Supports Budget</b>
	</td>
	<td colspan="2">
      &nbsp;
	</td>
</tr>

<%if (cwu.getUserStore().isAllowBudgetThreshold()) {%>
<tr>
	<td colspan="2">&nbsp;</td>
    <td><b>Budget threshold type</b>:</td>
    <td>
        <html:select name="STORE_ACCOUNT_DETAIL_FORM" property="budgetThresholdType">
            <html:option value="<%=RefCodeNames.BUDGET_THRESHOLD_TYPE.NONE%>"><app:storeMessage  key="admin.none"/></html:option>
            <html:option value="<%=RefCodeNames.BUDGET_THRESHOLD_TYPE.ACCOUNT_BUDGET_THRESHOLD%>"><%=RefCodeNames.BUDGET_THRESHOLD_TYPE.ACCOUNT_BUDGET_THRESHOLD%></html:option>
            <html:option value="<%=RefCodeNames.BUDGET_THRESHOLD_TYPE.SITE_BUDGET_THRESHOLD%>"><%=RefCodeNames.BUDGET_THRESHOLD_TYPE.SITE_BUDGET_THRESHOLD%></html:option>
        </html:select>
    </td>
</tr>
<%}%>

<tr>
<td colspan="4">&nbsp;</td>
</tr>
<tr>
<td colspan="4">
<b>Pdf Order Class Name:</b><br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<html:text name="STORE_ACCOUNT_DETAIL_FORM" property="pdfOrderClass" size="70"/>
</td>
</tr>
<tr>
<td colspan="4">
<b>Pdf Order Status Class Name:</b><br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<html:text name="STORE_ACCOUNT_DETAIL_FORM" property="pdfOrderStatusClass" size="70"/>
</td>
</tr>
		<tr>
		<td colspan="4">&nbsp;</td>
		</tr>
			<tr>
				<td colspan="4">
				<b>Reminder Email for users to update scheduled carts before order cutoff time:</b></td>
			</tr>
            <tr>
                <td><b>Email Subject:</b></td>
				<td colspan="3"><html:textarea name="STORE_ACCOUNT_DETAIL_FORM" property="invReminderEmailSub" rows="1" cols="60"/></td>
            </tr>
			<tr>
				<td><b>Email Message:</b></td>
				<td colspan="3">
				<html:textarea name="STORE_ACCOUNT_DETAIL_FORM" property="invReminderEmailMsg" rows="4"
					cols="60"/>
				</td>
			</tr>
            <tr>
                <td><b>Order Confirmation Email Generator:</b></td>
                <td colspan="3"><html:text name="STORE_ACCOUNT_DETAIL_FORM" property="confirmOrderEmailGenerator" size="100"/></td>
            </tr>
            <tr>
                <td><b>Order Notification Email Generator:</b></td>
                <td colspan="3"><html:text name="STORE_ACCOUNT_DETAIL_FORM" property="notifyOrderEmailGenerator" size="100"/></td>
            </tr>
            <tr>
                <td><b>Order Rejected Email Generator:</b></td>
                <td colspan="3"><html:text name="STORE_ACCOUNT_DETAIL_FORM" property="rejectOrderEmailGenerator" size="100"/></td>
            </tr>
            <tr>
                <td><b>Pending Approval Email Generator:</b></td>
                <td colspan="3"><html:text name="STORE_ACCOUNT_DETAIL_FORM" property="pendingApprovEmailGenerator" size="100"/></td>
            </tr>
            <tr>
                <td>
                	<b>Order Confirmation Email Template:</b>
                </td>
                <td colspan="3">
                	<html:select name="STORE_ACCOUNT_DETAIL_FORM" property="orderConfirmationEmailTemplate">
		           		<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
				   		<html:optionsCollection property="emailTemplateChoices" label="value"/>
		        	</html:select>
		        </td>
            </tr>
            <tr>
                <td>
                	<b>Shipping Notification Email Template:</b>
                </td>
                <td colspan="3">
                	<html:select name="STORE_ACCOUNT_DETAIL_FORM" property="shippingNotificationEmailTemplate">
		           		<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
				   		<html:optionsCollection property="emailTemplateChoices" label="value"/>
		        	</html:select>
		        </td>
            </tr>
            <tr>
                <td>
                	<b>Pending Approval Email Template:</b>
                </td>
                <td colspan="3">
                	<html:select name="STORE_ACCOUNT_DETAIL_FORM" property="pendingApprovalEmailTemplate">
		           		<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
				   		<html:optionsCollection property="emailTemplateChoices" label="value"/>
		        	</html:select>
		        </td>
            </tr>
            <tr>
                <td>
                	<b>Rejected Order Email Template:</b>
                </td>
                <td colspan="3">
                	<html:select name="STORE_ACCOUNT_DETAIL_FORM" property="rejectedOrderEmailTemplate">
		           		<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
				   		<html:optionsCollection property="emailTemplateChoices" label="value"/>
		        	</html:select>
		        </td>
            </tr>
            <tr>
                <td>
                	<b>Modified Order Email Template:</b>
                </td>
                <td colspan="3">
                	<html:select name="STORE_ACCOUNT_DETAIL_FORM" property="modifiedOrderEmailTemplate">
		           		<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
				   		<html:optionsCollection property="emailTemplateChoices" label="value"/>
		        	</html:select>
		        </td>
            </tr>

            <tr><td class="largeheader" align=left colspan="4">Inventory properties</td></tr>

            <tr>
                 <td colspan="2">
                    <html:checkbox name="STORE_ACCOUNT_DETAIL_FORM" property="invLedgerSwitch"/><b>Budget(Off)</b>
                </td>
                <td><b>PO Suffix</b></td>
                <td>
                    <html:text name="STORE_ACCOUNT_DETAIL_FORM" property="invPOSuffix"  size="3"/>
                </td>
            </tr>

             <tr>
                 <td colspan="2">&nbsp;</td>
                 <td><b>O.G. Inventory UI</b></td>
                 <td>
                     <html:select name="STORE_ACCOUNT_DETAIL_FORM" property="invOGListUI">
                         <html:option value=""><app:storeMessage  key="admin.select"/></html:option>
                         <html:options  collection="INVENTORY_OG_LIST_UI" property="value" />
                     </html:select>
                 </td>
            </tr>
            <tr>
                <td colspan="2">&nbsp;</td>
                <td><b>Send notification if auto order applies (hours)</b></td>
                 <td>
                    <html:text name="STORE_ACCOUNT_DETAIL_FORM" property="invMissingNotification"  size="3"/>
                 </td>
            </tr>
            <tr>
                <td colspan="2">&nbsp;</td>
                <td><b>Do not place inv. order if site order exists (days)</b></td>
                <td>
                    <html:text name="STORE_ACCOUNT_DETAIL_FORM" property="invCheckPlacedOrder"  size="3"/>
                </td>
            </tr>
            <tr>
                <td colspan="2">&nbsp;</td>
                <td><b>Distributor PO Type</b></td>
                <td>
                    <html:select name="STORE_ACCOUNT_DETAIL_FORM" property="distrPoType">
                    <html:option value="<%=RefCodeNames.DISTR_PO_TYPE.SYSTEM%>"><%=RefCodeNames.DISTR_PO_TYPE.SYSTEM%></html:option>
                    <html:option value="<%=RefCodeNames.DISTR_PO_TYPE.REQUEST%>"><%=RefCodeNames.DISTR_PO_TYPE.REQUEST%></html:option>
                    <html:option value="<%=RefCodeNames.DISTR_PO_TYPE.CUSTOMER%>"><%=RefCodeNames.DISTR_PO_TYPE.CUSTOMER%></html:option>
                    </html:select>
                </td>
            </tr>

			<tr>
			    <td colspan="4">&nbsp;</td>
			</tr>

			<tr><td class="largeheader" align=left colspan="4">Asset Management</td></tr>

			<tr>
                <td colspan="2">
                    <table width="100%" border="0" cellspacing="0" cellpadding="2">
                        <tr>
                            <td align="left">
                                <html:checkbox name="STORE_ACCOUNT_DETAIL_FORM" property="allowSetWorkOrderPoNumber" onclick="javascript:enableCheckBox(this, 'workOrderPoNumberIsRequired')"/>
                            </td>
                            <td valign="middle">
                                &nbsp;<b>Allow to set Work&nbsp;Order PO&nbsp;Number</b>
                            </td>
                            <td align="center">
                                <html:checkbox name="STORE_ACCOUNT_DETAIL_FORM" property="workOrderPoNumberIsRequired"/>
                            </td>
                            <td valign="middle">
                                &nbsp;<b>Work&nbsp;Order PO&nbsp;Number is required</b>
                            </td>
                        </tr>
                    </table>
                </td>
                <td colspan="2">
                </td>
            </tr>
            
            <tr>
                <td colspan="2">
                    <html:checkbox name="STORE_ACCOUNT_DETAIL_FORM" property="userAssignedAssetNumber"/>
                    &nbsp;<b>User assigned Asset&nbsp;Number</b>
                </td>
                <td colspan="2">
                </td>
            </tr>
            
            <tr>
                <td colspan="2">
                    <html:checkbox name="STORE_ACCOUNT_DETAIL_FORM" property="allowBuyWorkOrderParts"/>
                    &nbsp;<b>Allow to Order Parts for Work&nbsp;Order</b>
                </td>
                <td colspan="2">
                </td>
            </tr>

			<tr>
                <td colspan="2">
                    <table width="100%" border="0" cellspacing="0" cellpadding="2">
                        <tr>
                            <td align="left">
                                <b>Populate Contact Information&nbsp;</b>
                            </td>
                            <td>
								<html:radio name="STORE_ACCOUNT_DETAIL_FORM" property="contactInformationType" value="user" />&nbsp;<b>User</b>
                            </td>
                            <td>
                                <html:radio name="STORE_ACCOUNT_DETAIL_FORM" property="contactInformationType" value="site" />&nbsp;<b>Site</b>
                            </td>
                            <td>
                                <html:radio name="STORE_ACCOUNT_DETAIL_FORM" property="contactInformationType" value="none" />&nbsp;<b>None</b>
                            </td>
                        </tr>
                    </table>
                </td>
                <td colspan="2">
                </td>
            </tr>

			<tr>
			    <td colspan="4">&nbsp;</td>
			</tr>
<tr>
<td colspan=4 align=center>
<html:submit property="action">
<app:storeMessage  key="global.action.label.save"/>
</html:submit>
<html:reset>
<app:storeMessage  key="admin.button.reset"/>
</html:reset>
<logic:notEqual name="STORE_ACCOUNT_DETAIL_FORM" property="id" value="0">
<html:submit property="action">
<app:storeMessage  key="global.action.label.delete"/>
</html:submit>
</logic:notEqual>
<logic:notEqual name="STORE_ACCOUNT_DETAIL_FORM" property="id" value="0">
<html:submit property="action" value="Clone">
</html:submit>
</logic:notEqual>
</td>
</tr>
</table>
</html:form>

</div>

<body>
</html:html>


