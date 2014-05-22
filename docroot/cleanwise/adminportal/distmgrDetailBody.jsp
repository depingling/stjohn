<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<SCRIPT TYPE="text/javascript" SRC="../externals/table-sort.js"
  CHARSET="ISO-8859-1"></SCRIPT>

<script language="JavaScript1.2">
<!--
function popLocate(pLoc, name, pDesc) {
var loc = pLoc + ".do?feedField=" + name + "&amp;feedDesc=" + pDesc + "&amp;submitFl=true";
locatewin = window.open(loc,"Locate", "menubar=yes,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
locatewin.focus();

return false;
}

//-->
</script>
<% 
  String portal = request.getParameter("portal");
  boolean adminPortalFl = ("adminportal".equalsIgnoreCase(portal))?true:false;
  String actionStr = (adminPortalFl)?"adminportal/distmgrDetail.do":"/console/crcdistDetail.do";  
  String upperLink = (adminPortalFl)?"distmgr.do":"crcdist.do";
  String thisLink = (adminPortalFl)?"distmgrDetail.do":"crcdistDetail.do";
  boolean readOnlyFl = (adminPortalFl)?false:true;
  String disabledStr = (readOnlyFl)?"disabled='true'":"";
  
%>  


<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="theForm" name="DIST_DETAIL_FORM" type="com.cleanwise.view.forms.DistMgrDetailForm"/>
<bean:define id="Location" value="dist" type="java.lang.String" toScope="session"/>
<% String action = request.getParameter("action");%>

<link rel="stylesheet" href="../externals/styles.css">


<div class="text">
<center>
<font color=red>
<html:errors/>
</font>
</center>

<table cellspacing="0" border="0" width="<%=Constants.TABLEWIDTH%>"  class="mainbody">
<html:form name="DIST_DETAIL_FORM" scope="session"
        action="<%=actionStr%>"
        type="com.cleanwise.view.forms.DistMgrDetailForm">

<tr>
<td class="largeheader" colspan="4">&nbsp;</td>
</tr>
<tr>
	<td><b>Distributor&nbsp;Id:</b></td>
	<td>
		<bean:write name="DIST_DETAIL_FORM" property="id" />
		<html:hidden property="id"/>
	</td>
	<td colspan="2">&nbsp;</td>
</tr>
<logic:equal name="<%=Constants.APP_USER%>" property="user.userTypeCd" value="<%=RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR%>">
<tr>
	<td><b>Store&nbsp;Id:</b></td>
	<td>
		<html:text tabindex="0" name="DIST_DETAIL_FORM" property="storeId" size="5" readonly='<%=readOnlyFl%>' /><span class="reqind">*</span>
		<html:button property="action" onclick="popLocate('storelocate', 'storeId', 'storeName');" value="Locate Store"/>
	</td>
	<td colspan="2">&nbsp;</td>
</tr>
</logic:equal>
<tr>
	<td><b>Name:</b></td>
	<td>
		<html:text tabindex="1" name="DIST_DETAIL_FORM" property="name" size="25" maxlength="30" readonly='<%=readOnlyFl%>' /><span class="reqind">*</span>
	</td>
	<td><b>Runtime Display Name</b></td><td>
		<html:text tabindex="4" name="DIST_DETAIL_FORM" property="runtimeDisplayName" 
		size="25" maxlength="30"  readonly='<%=readOnlyFl%>' />
	</td>
</tr>
<tr>
<td><b>Distributor&nbsp;Type:</b></td>
<td>
<html:select tabindex="2" name="DIST_DETAIL_FORM" property="typeDesc" disabled='<%=readOnlyFl%>'>
<html:option value="" ><app:storeMessage  key="admin.select"/></html:option>
<html:options  collection="Dist.type.vector" property="value" />
</html:select>
</td>
<td><b>Status:</b></td>
<td>
<html:select tabindex="3" name="DIST_DETAIL_FORM" property="statusCd" disabled='<%=readOnlyFl%>'>
<html:option value="" ><app:storeMessage  key="admin.select"/></html:option>
<html:options  collection="Dist.status.vector" property="value" />
</html:select>
<span class="reqind">*</span>
</td>
</tr>
<tr>
     <td><b>Distributor&nbsp;Number:</b></td>
     <td>
        <html:text tabindex="4" name="DIST_DETAIL_FORM" property="distNumber" 
           size="30" maxlength="30"  readonly='<%=readOnlyFl%>' />
     </td>
     <td><b>Distributor's Company Code:</b></td>
     <td>
        <html:text tabindex="4" name="DIST_DETAIL_FORM" property="distributorsCompanyCode" 
           size="30" maxlength="30"  readonly='<%=readOnlyFl%>' />
     </td>
</tr>
<tr>
        <td><b>Call Center Hours</b></td><td>
		<html:text tabindex="4" name="DIST_DETAIL_FORM" property="callHours" 
           size="30" maxlength="30"  readonly='<%=readOnlyFl%>' />
	</td>
	<td>&nbsp;</td><td>&nbsp;</td>
</tr>
<tr>
        <td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
</tr>
<tr>
<td>
<b>Minimum Order:</b><br> (Monetary Amount)</TD>
<td><html:text tabindex="4" name="DIST_DETAIL_FORM" 
property="minimumOrderAmount"
size="5" maxlength="30"  readonly='<%=readOnlyFl%>' /></td>

<td><b>Small Order Handling Fee:</b><br> 
(Charge applied to orders smaller than the minimum order amount.)</TD>
<td><html:text tabindex="4" name="DIST_DETAIL_FORM" 
  property="smallOrderHandlingFee"
size="5" maxlength="7" readonly='<%=readOnlyFl%>'/>
</td>

</tr>

<tr><td colspan=10 
style="border-top:solid 1px black; background-color: white;" >
<b>Invoice Processing settings</b></td></tr>

<tr>
    <td><b>Exception On Overcharged Freight</b></td>
    <td>
        Yes&nbsp;<html:radio name="DIST_DETAIL_FORM" property="exceptionOnOverchargedFreight" value="Y" disabled='<%=readOnlyFl%>'/>
        &nbsp;&nbsp;&nbsp;
        No&nbsp;<html:radio name="DIST_DETAIL_FORM" property="exceptionOnOverchargedFreight" value="N" disabled='<%=readOnlyFl%>'/>
    </td>

<td><b>Ignore Order Minimum for Freight</b>
<br>YES will allow freight to be charged for orders that are below the
minimum and are being shipped to a freight free territory.
</td>
<td>
Yes&nbsp;<html:radio name="DIST_DETAIL_FORM" 
  property="ignoreOrderMinimumForFreight" value="Y" 
  disabled='<%=readOnlyFl%>'/>
&nbsp;&nbsp;&nbsp;
No&nbsp;<html:radio name="DIST_DETAIL_FORM" 
  property="ignoreOrderMinimumForFreight" value="N" 
  disabled='<%=readOnlyFl%>'/>
</td>

</tr>
<tr>
    <td><b>Invoice Loading Pricing Model:</b></td>
    <td>
      <html:select tabindex="3" name="DIST_DETAIL_FORM" property="invoiceLoadingPriceModel" disabled='<%=readOnlyFl%>'>
      <html:option value="<%=RefCodeNames.INVOICE_LOADING_PRICE_MODEL_CD.EXCEPTION%>" >Exception On Cost Difference</html:option>
      <html:option value="<%=RefCodeNames.INVOICE_LOADING_PRICE_MODEL_CD.DISTRIBUTOR_INVOICE%>" >Use Distributor Invoice Cost</html:option>
      <html:option value="<%=RefCodeNames.INVOICE_LOADING_PRICE_MODEL_CD.PREDETERMINED%>" >Use Our Cost</html:option>
      <html:option value="<%=RefCodeNames.INVOICE_LOADING_PRICE_MODEL_CD.LOWEST%>" >Use Lowest Cost</html:option>
      <html:option value="<%=RefCodeNames.INVOICE_LOADING_PRICE_MODEL_CD.HOLD_ALL%>" >Hold All Invoices For Review</html:option>
      </html:select>
    </td>
    <td><b>Invoice Loading Pricing Model Exception Threshold (%):</b></td>
    <td><html:text tabindex="4" name="DIST_DETAIL_FORM" property="invoiceLoadingPriceExceptionThreshold"
            readonly='<%=readOnlyFl%>'  />%</td>
</tr>
<tr>
    <td>
		<b>Allow Freight On Backorders:</b>
	</td>
    <td>
        Yes&nbsp;<html:radio name="DIST_DETAIL_FORM" property="allowFreightOnBackorders" value="Y" disabled='<%=readOnlyFl%>'/>
        &nbsp;&nbsp;&nbsp;
        No&nbsp;<html:radio name="DIST_DETAIL_FORM" property="allowFreightOnBackorders" value="N" disabled='<%=readOnlyFl%>'/>
    </td>


    <td><b>Maximum freight allowed in an invoice</b>
		<br>If an invoice has freight greater than this value, the
		invoice will go into an exception state.
		<br> Zero or blank means this check is not performed.
	</td>
    <td>
		<html:text name="DIST_DETAIL_FORM" property="maxInvoiceFreightAllowed" disabled='<%=readOnlyFl%>'/>
    </td>
</tr>
<tr>
    <td>
		<b>Cancel Backordered Lines:</b>
		<br>If an invoice is received and there remains items open on it then the remaining items and quantities
		are cancelled.  It is assumed the distributor will not ship these items, and any invoices received against
		the order will not be paid.
	</td>
    <td>
        Yes&nbsp;<html:radio name="DIST_DETAIL_FORM" property="cancelBackorderedLines" value="Y" disabled='<%=readOnlyFl%>'/>
        &nbsp;&nbsp;&nbsp;
        No&nbsp;<html:radio name="DIST_DETAIL_FORM" property="cancelBackorderedLines" value="N" disabled='<%=readOnlyFl%>'/>
    </td>
	
	<td>
		<b>Hold Inbound Invoice:</b>
		<br>Holds the invoice invoices for the conigured number of days.
	</td>
    <td>
        <html:text name="DIST_DETAIL_FORM" property="holdInvoiceDays" disabled='<%=readOnlyFl%>'/>
    </td>
	
</tr>
<tr>
    <td>
		<b>Receiving System Type Code:</b>
		<br>Governs the way that the receiving system works for this distributor.  
		If this property is set to &quote;Requiere Entry&quote; or &quote;Enter Errors Only&quote; 
		then an invoice is flagged as an exception if it is entered with a quantity less then the quantity invoiced,
		or if this property is set to &quote;Requiere Entry&quote; and the order has not been received against.
	</td>
    <td>
	<html:select property="receivingSystemInvoiceCd" disabled='<%=readOnlyFl%>'>
		<html:option value="" ><app:storeMessage  key="admin.select"/></html:option>
		<html:options  collection="RECEIVING_SYSTEM_INVOICE_CD" property="value" />
	</html:select>        
    </td>
	
	<td>&nbsp;</td>
    <td>&nbsp;</td>
<tr><td colspan=10 
style="border-bottom:solid 1px black;" >&nbsp;</td></tr>


<tr>
    <td><b>Print Customer Contact Info on Purchase Order:</b></td>
    <td>
        Yes&nbsp;<html:radio name="DIST_DETAIL_FORM" property="printCustContactOnPurchaseOrder" value="Y" disabled='<%=readOnlyFl%>'/>
        &nbsp;&nbsp;&nbsp;
        No&nbsp;<html:radio name="DIST_DETAIL_FORM" property="printCustContactOnPurchaseOrder" value="N" disabled='<%=readOnlyFl%>'/>
    </td>
    <td><b>Manual PO Acknowledgement Required:</b></td>
    <td>
        Yes&nbsp;<html:radio name="DIST_DETAIL_FORM" property="manualPOAcknowldgementRequiered" value="Y" disabled='<%=readOnlyFl%>'/>
        &nbsp;&nbsp;&nbsp;
        No&nbsp;<html:radio name="DIST_DETAIL_FORM" property="manualPOAcknowldgementRequiered" value="N" disabled='<%=readOnlyFl%>'/>
    </td>
</tr>

<tr valign=top>
<td colspan=3>
<b>Comments To Print On Purchase Order:</b><br>
<html:textarea name="DIST_DETAIL_FORM" 
  property="purchaseOrderComments" 
  rows="4" cols="60" readonly='<%=readOnlyFl%>' />
</td>


    <td><b>Groups</b><ul>
    <% GroupDataVector groupDV = theForm.getDistGroups(); 
       if(groupDV!=null && groupDV.size()>0) {
       for(int ii=0; ii<groupDV.size(); ii++) {
       GroupData groupD = (GroupData) groupDV.get(ii);
       String groupName = groupD.getShortDesc();
    %> 
     <li><%=groupName%></li>
    <% } %>
    <% } else { %>
     &nbsp;
    <% } %>
</ul>

</td>
</tr>

<tr>
<td colspan="4" class="largeheader"><br>Primary Manufacturers</td>
</td>
</tr>
<% 
   BusEntityDataVector mfgDV = theForm.getPrimaryManufacturers();
   if(mfgDV!=null) {
   for(int ii=0; ii<mfgDV.size(); ii++) {
    BusEntityData mfgD = (BusEntityData) mfgDV.get(ii);
    String delMfgHref=thisLink+"?action=DeletePrimaryManufacturer&manufacturerId="+mfgD.getBusEntityId();
%>
<tr><td colspan='4'>  <%=mfgD.getShortDesc()%>
  <a href='<%=delMfgHref%>'>[D]</a></td></tr>
		<% }} %>
<% if(!readOnlyFl) { %>
<tr>
<td colspan="4" styleClass="smalltext">
  <html:hidden name="DIST_DETAIL_FORM" property="manufacturerId" value="0"/>  
  <% String locateMfg = "return popLocate('manuflocate','manufacturerId','');";%>
  <html:button  onclick="<%=locateMfg%>" value="Add Manufacturer" property="action" /> 
</td>
</tr>
<% } %>
<tr>
<td colspan="4" class="largeheader"><br>Contact and Address</td>
</td>
<tr>
<td><b>First Name:</b></td>
<td>
<html:text tabindex="10" name="DIST_DETAIL_FORM" property="name1" maxlength="30" 
  readonly='<%=readOnlyFl%>' />
</td>
<td><b>Phone:</b></td>
<td>

<html:text tabindex="20" name="DIST_DETAIL_FORM" property="phone" maxlength="40"
   readonly='<%=readOnlyFl%>' />
<span class="reqind">*</span>
</td>
</tr>
<tr>
<td><b>Last Name:</b></td>
<td>

<html:text tabindex="11" name="DIST_DETAIL_FORM" property="name2" maxlength="30" 
  readonly='<%=readOnlyFl%>' />
</td>
<td><b>Fax:</b></td>
<td>

<html:text tabindex="21" name="DIST_DETAIL_FORM" property="fax" maxlength="30"
 readonly='<%=readOnlyFl%>' />
</td>

</tr>
<tr>
<td><b>Street Address 1:</b></td>
<td>
<html:text tabindex="12" name="DIST_DETAIL_FORM" property="streetAddr1" maxlength="80"
 readonly='<%=readOnlyFl%>' />
<span class="reqind">*</span>
</td>
<td><b>Email:</b></td>
<td>
<html:text tabindex="22"  name="DIST_DETAIL_FORM" property="emailAddress" maxlength="80" 
 readonly='<%=readOnlyFl%>' />
</td>
</tr>
<tr>
<td><b>Street Address 2:</b></td>
<td>
<html:text tabindex="13" name="DIST_DETAIL_FORM" property="streetAddr2" maxlength="80" 
 readonly='<%=readOnlyFl%>' />
</td>
<td><b>Country:</b></td>
<td>
<html:select tabindex="23" name="DIST_DETAIL_FORM" property="country" disabled='<%=readOnlyFl%>'>
<html:option value=""><app:storeMessage  key="admin.select.country"/></html:option>
<html:options  collection="countries.vector" property="value" />
</html:select>
<span class="reqind">*</span>
</td>
</tr>
<tr>
<td><b>Street Address 3:</b></td>
<td>
<html:text tabindex="14" name="DIST_DETAIL_FORM" property="streetAddr3" maxlength="80" 
 readonly='<%=readOnlyFl%>' />
</td>

<td><b>State/Province:</b></td>
<td>

<html:text tabindex="24" size="20" maxlength="80" name="DIST_DETAIL_FORM"
  property="stateOrProv"  readonly='<%=readOnlyFl%>' />
<span class="reqind">*</span>
</td>
</tr>
<tr>
<td><b>City:</b></td>
<td>
<html:text tabindex="15" name="DIST_DETAIL_FORM" property="city" maxlength="40" 
  readonly='<%=readOnlyFl%>' />
<span class="reqind">*</span>
</td>

<td><b>Zip/Postal Code:</b></td>
<td>
<html:text tabindex="25" name="DIST_DETAIL_FORM" property="postalCode" maxlength="15" 
 readonly='<%=readOnlyFl%>' />
<span class="reqind">*</span>
</td>

</tr>

<tr>
<td colspan="4" class="largeheader"><br>Billing Address</td>
</td>
<tr>
<td><b>Street Address 1:</b></td>
<td>
<html:text tabindex="12" name="DIST_DETAIL_FORM" property="billingAddress.address1" 
  maxlength="80" readonly='<%=readOnlyFl%>' />
</td>
<td colspan='2'><b>&nbsp</b></td>
</tr>
<tr>
<td><b>Street Address 2:</b></td>
<td>
<html:text tabindex="13" name="DIST_DETAIL_FORM" property="billingAddress.address2"
 maxlength="80"  readonly='<%=readOnlyFl%>' />
</td>
<td><b>Country:</b></td>
<td>
<html:select tabindex="23" name="DIST_DETAIL_FORM" property="billingAddress.countryCd" disabled='<%=readOnlyFl%>'>
<html:option value=""><app:storeMessage  key="admin.select.country"/></html:option>
<html:options  collection="countries.vector" property="value" />
</html:select>
</td>
</tr>
<tr>
<td><b>Street Address 3:</b></td>
<td>
<html:text tabindex="14" name="DIST_DETAIL_FORM" property="billingAddress.address3"
 maxlength="80"  readonly='<%=readOnlyFl%>' />
</td>

<td><b>State/Province:</b></td>
<td>

<html:text tabindex="24" size="20" maxlength="80" name="DIST_DETAIL_FORM"  
  property="billingAddress.stateProvinceCd" readonly='<%=readOnlyFl%>' />
</td>
</tr>
<tr>
<td><b>City:</b></td>
<td>
<html:text name="DIST_DETAIL_FORM" property="billingAddress.city" maxlength="40"
  readonly='<%=readOnlyFl%>' />
</td>

<td><b>Zip/Postal Code:</b></td>
<td>
<html:text name="DIST_DETAIL_FORM" property="billingAddress.postalCode" 
  maxlength="15" readonly='<%=readOnlyFl%>' />
</td>
</tr>

<tr>
<td><b>Distributor Web Info:</b></TD>
<td colspan=3><html:text tabindex="4" 
  name="DIST_DETAIL_FORM" property="webInfo"
  size="60" readonly='<%=readOnlyFl%>'/>
</td>
</tr>

<tr>
<td><b>Distributor CW Account Number(s):</b></TD>
<td colspan=3><html:text tabindex="4" 
  name="DIST_DETAIL_FORM" property="accountNumbers"
  size="60" readonly='<%=readOnlyFl%>'/>
</td>
</tr>



<% if(!readOnlyFl) { %>
<tr>
    <td colspan=4 align=center>
      <html:submit property="action">
        <app:storeMessage  key="global.action.label.save"/>
      </html:submit>
      <html:reset>
        <app:storeMessage  key="admin.button.reset"/>
      </html:reset>
    <logic:notEqual name="DIST_DETAIL_FORM" property="id" value="0">
      <html:submit property="action">
        <app:storeMessage  key="global.action.label.delete"/>
      </html:submit>
    </logic:notEqual>
    </td>
</tr>
<% } %>

<tr><td>&nbsp;</td></tr>


<tr><td colspan='4'>
<jsp:include flush='true' page="f_distContacts.jsp"/>
</td></tr>
 
<tr><td colspan='4'>
<jsp:include flush='true' page="f_distBranches.jsp"/>
</td></tr>

<tr>
<td colspan="2" class="largeheader"><b>Served States</b></td>
<td colspan="2" class="largeheader"><b>Served Accounts</b></td>
</tr>
<% 
java.util.ArrayList servedStates = theForm.getServedStates();
BusEntityDataVector servedAccounts = theForm.getServedAccounts();
%>
<tr><td valign='top' colspan='2'>
<%
if(servedStates!=null && servedStates.size()>0) {
for(int ii=0; ii<servedStates.size(); ii++) {
  String ss = (String) servedStates.get(ii);
%>
  <%=ss%><br>
<%}%>
<% } else { %>
  None
<%}%>
</td>
<td colspan='2' valign='top'>
<%
if(servedAccounts!=null && servedAccounts.size()>0) {
for(int ii=0; ii<servedAccounts.size(); ii++) {
  BusEntityData beD = (BusEntityData) servedAccounts.get(ii);
%>
  <%=beD.getShortDesc()%><br>
<%}%>
<% } else { %>
  None
<%}%>

</td>
</tr>

</table>

</div>

<div style="width: <%=Constants.TABLEWIDTH%>;">
<bean:parameter id="editShipFromId" name="ship_from_id" value="0"/>
<logic:equal name="editShipFromId" value="-1">

<div style="background-color: #cc99cc;">
<b>Add a new ship from address.</b><br>
<hr>
<div align="right">

<table>
<tr>
<td><b>Ship From Name:</b></td>
<td>
<html:text
  name="DIST_DETAIL_FORM" property="shipFrom.name1"
  tabindex="30" maxlength="30" readonly='<%=readOnlyFl%>' />
</td>

<td><b>City:</b></td>
<td>
<html:text
  name="DIST_DETAIL_FORM" property="shipFrom.city"
  tabindex="40"maxlength="40" readonly='<%=readOnlyFl%>' />
</td>
</tr>

<tr>
<td><b>Street Address 1:</b></td>
<td>
<html:text
  name="DIST_DETAIL_FORM" property="shipFrom.address1"
  tabindex="31" maxlength="80" readonly='<%=readOnlyFl%>' />
</td>

<td><b>State/Province:</b></td>
<td>
<html:text size="20" maxlength="80"
  name="DIST_DETAIL_FORM" property="shipFrom.stateProvinceCd"
  tabindex="41" readonly='<%=readOnlyFl%>' />
</td>

</tr>

<tr>
<td><b></td>
<td>
</td>

<td><b>Zip/Postal Code:</b></td>
<td>
<html:text
  name="DIST_DETAIL_FORM" property="shipFrom.postalCode"
  tabindex="42" 	maxlength="15" readonly='<%=readOnlyFl%>' />
</td>
</tr>

<tr>
<td>
</td>
<td>
</td>

<td><b>Country:</b></td>
<td>
<html:select name="DIST_DETAIL_FORM" property="shipFrom.countryCd"
  tabindex="43" disabled='<%=readOnlyFl%>'>
<html:option value=""><app:storeMessage  key="admin.select.country"/></html:option>
<html:options  collection="countries.vector" property="value" />
</html:select>
</td>
</tr>

</table>

<html:submit property="action" tabindex="44" >
Add Ship From Address
</html:submit>

</div> <% /* alignment block (right). */ %>

</div> <% /* Color block. */ %>

</logic:equal>

</div>



</html:form>


</div> <% /* End - Ship from locations. */ %>
