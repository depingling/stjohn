<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<SCRIPT TYPE="text/javascript" SRC="../externals/table-sort.js" CHARSET="ISO-8859-1"></SCRIPT>

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

<script language="JavaScript1.2">
<!--
function SetChecked(val) {
 dml=document.STORE_DIST_DETAIL_FORM;
 len = dml.elements.length;
 var i=0;
 for( i=0 ; i<len ; i++) {
   if (dml.elements[i].name=='selectIds') {
     dml.elements[i].checked=val;
   }
 }
}
function clearRadioButton() {
 dml=document.STORE_DIST_DETAIL_FORM;
 len = dml.elements.length;
 var i=0;
 for( i=0 ; i<len ; i++) {
   if (dml.elements[i].name=='catalogId') {
     dml.elements[i].checked=0;
   }
 }
}
//-->

</script>



<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="theForm" name="STORE_DIST_DETAIL_FORM" type="com.cleanwise.view.forms.StoreDistMgrDetailForm"/>
<% String action = request.getParameter("action");%>

<link rel="stylesheet" href="../externals/styles.css"/>


<div class="text">
<%--
<center>
<font color="red">
<html:errors/>
</font>
</center>
 --%>


<%-- distributor header --%>
<table ID=730 cellspacing="0" border="0" width="<%=Constants.TABLEWIDTH%>"  class="mainbody">
<html:form styleId="731" name="STORE_DIST_DETAIL_FORM" scope="session"
        action="storeportal/distconf.do"
        type="com.cleanwise.view.forms.StoreDistMgrDetailForm">

<tr>
    <td class="largeheader" colspan="4">&nbsp;</td>
</tr>

<tr>
  <td><b>Distributor&nbsp;Id:</b></td>
  <td><bean:write name="STORE_DIST_DETAIL_FORM" property="id"/></td>
  <td><b>Name:</b></td>
  <td>
    <bean:write name="STORE_DIST_DETAIL_FORM" property="name"/>
  </td>
</tr>
</table>

<div class="text">

<logic:equal name="STORE_DIST_DETAIL_FORM" property="typeDesc" value="SUB_DISTRIBUTOR">
<table ID=550>
  <tr> <td><b>Find</b></td>
       <td>
          <html:select name="STORE_DIST_DETAIL_FORM" property="confType" onchange="document.forms[0].submit();">
             <html:option value="Catalogs">Catalogs</html:option>
             <html:option value="Sites">Sites</html:option>
             <html:option value="Configuration">Configuration</html:option>
          </html:select>
       </td>
  </tr>
<% if( !Utility.isSet(theForm.getConfType()) ||
       "Catalogs".equals(theForm.getConfType())) { %>
  <tr>
      <td>&nbsp;</td>
       <td colspan="4">
          <html:text name="STORE_DIST_DETAIL_FORM" property="confSearchField"/>
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          <html:radio name="STORE_DIST_DETAIL_FORM" property="confSearchType" value="nameBegins" />
          Name(starts with)
          &nbsp;&nbsp;
          <html:radio name="STORE_DIST_DETAIL_FORM" property="confSearchType" value="nameContains" />
          Name(contains)
         </td>
  </tr>

  <tr> <td colspan="1">&nbsp;</td>
       <td colspan="4">
        <html:submit property="action">
                <app:storeMessage  key="global.action.label.search"/>
        </html:submit>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        Show Configured Only<html:checkbox name="STORE_DIST_DETAIL_FORM"  property="confShowConfguredOnlyFl"/>

        <logic:equal name="STORE_DIST_DETAIL_FORM" property="confType" value="Distributors">
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        Show Inactive<html:checkbox name="STORE_DIST_DETAIL_FORM" property="confShowInactiveFl"/>
        </logic:equal>
     </td>
  </tr>
<% } %>


<logic:equal name="STORE_DIST_DETAIL_FORM" property="confType" value="Sites">
<tr>
<td align="right"><b>Find Site</b></td>
<td>
<html:text name="STORE_DIST_DETAIL_FORM" property="confSearchField"/>
<html:radio name="STORE_DIST_DETAIL_FORM" property="confSearchType"
    value="id" />  ID
<html:radio name="STORE_DIST_DETAIL_FORM" property="confSearchType"
    value="nameBegins" />  Name(starts with)
<html:radio name="STORE_DIST_DETAIL_FORM" property="confSearchType"
    value="nameContains" />  Name(contains)
</td>
</tr>

<tr>
<td align="right"><b>City</b></td>
<td colspan="3"><html:text name="STORE_DIST_DETAIL_FORM" property="confCity"/>
&nbsp;&nbsp;&nbsp;&nbsp;<b>County</b>&nbsp;&nbsp;
<html:text name="STORE_DIST_DETAIL_FORM" property="confCounty"/>
</td>

</tr>

<tr>
<td align="right"><b>State</b></td>
<td colspan="3"><html:text name="STORE_DIST_DETAIL_FORM" property="confState"/>
&nbsp;&nbsp;&nbsp;&nbsp;<b>Zip Code</b>
<html:text name="STORE_DIST_DETAIL_FORM" property="confZipcode"/>
</td>
</tr>

<tr>
       <td colspan="4">
        <html:submit property="action">
                <app:storeMessage  key="global.action.label.search"/>
        </html:submit>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        Show Configured Only<html:checkbox name="STORE_DIST_DETAIL_FORM"  property="confShowConfguredOnlyFl"/>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        Move Sites When Assign<html:checkbox name="STORE_DIST_DETAIL_FORM"  property="confMoveSitesFl"/>
     </td>
  </tr>
</logic:equal>


  <tr><td colspan="4">&nbsp;</td>
  </tr>
  </table>
 </logic:equal>
<%-- end of config page select for sub-distributors --%>



<%-- dist config--%>
<% if( !theForm.getTypeDesc().equals("SUB_DISTRIBUTOR") ||
       (Utility.isSet(theForm.getConfType()) && "Configuration".equals(theForm.getConfType())) ) { %>

<table cellspacing="0" border="0" width="<%=Constants.TABLEWIDTH%>" class="mainbody">
<tr>
    <td colspan="10" style="border-top:solid 1px black; background-color: white;">
        <b>Invoice Processing Settings</b>
    </td>
</tr>

<tr>
    <td><b>Perform Sales Tax Check:</b></td>
    <td>
        Yes&nbsp;<html:radio name="STORE_DIST_DETAIL_FORM" property="exceptionOnTaxDifference" value="<%=RefCodeNames.EXCEPTION_ON_TAX_VALUE.YES%>"/>
        &nbsp;
        No&nbsp;<html:radio name="STORE_DIST_DETAIL_FORM" property="exceptionOnTaxDifference" value="<%=RefCodeNames.EXCEPTION_ON_TAX_VALUE.NO%>"/>
        &nbsp;
        Yes For Resale&nbsp;<html:radio name="STORE_DIST_DETAIL_FORM" property="exceptionOnTaxDifference" value="<%=RefCodeNames.EXCEPTION_ON_TAX_VALUE.YES_FOR_RESALE%>"/>
    </td>
    <td></td>
    <td></td>
</tr>


<tr>
    <td>
        <b>Exception On Overcharged Freight:</b>
    </td>
    <td>
        Yes&nbsp;<html:radio name="STORE_DIST_DETAIL_FORM" property="exceptionOnOverchargedFreight" value="Y"/>
        &nbsp;&nbsp;&nbsp;
        No&nbsp;<html:radio name="STORE_DIST_DETAIL_FORM" property="exceptionOnOverchargedFreight" value="N"/>
    </td>

    <td>
        <b>Ignore Order Minimum for Freight:</b>
        <br>YES will allow freight to be charged for orders that are below the
        minimum and are being shipped to a freight free territory.
    </td>
    <td>
        Yes&nbsp;<html:radio name="STORE_DIST_DETAIL_FORM"
                             property="ignoreOrderMinimumForFreight" value="Y"/>
        &nbsp;&nbsp;&nbsp;
        No&nbsp;<html:radio name="STORE_DIST_DETAIL_FORM"
                            property="ignoreOrderMinimumForFreight" value="N"/>
    </td>

</tr>

<tr>
    <td><b>Invoice Loading Pricing Model:</b></td>
    <td>
      <html:select tabindex="3" name="STORE_DIST_DETAIL_FORM" property="invoiceLoadingPriceModel">
      <html:option value="<%=RefCodeNames.INVOICE_LOADING_PRICE_MODEL_CD.EXCEPTION%>" >Exception On Cost Difference</html:option>
      <html:option value="<%=RefCodeNames.INVOICE_LOADING_PRICE_MODEL_CD.DISTRIBUTOR_INVOICE%>" >Use Distributor Invoice Cost</html:option>
      <html:option value="<%=RefCodeNames.INVOICE_LOADING_PRICE_MODEL_CD.PREDETERMINED%>" >Use Our Cost</html:option>
      <html:option value="<%=RefCodeNames.INVOICE_LOADING_PRICE_MODEL_CD.LOWEST%>" >Use Lowest Cost</html:option>
      <html:option value="<%=RefCodeNames.INVOICE_LOADING_PRICE_MODEL_CD.HOLD_ALL%>" >Hold All Invoices For Review</html:option>
      </html:select>
    </td>
    <td>
        <b>Invoice Amount Percent Allowance (Undercharge & Overcharge):</b>
        This controls the percentage difference that is allowed in a price during the invoice loading.  There is an upper limit for overcharges and a lower limit for under charges.
    </td>
    <td>
        <table ID=732>
        <tr>
        <td><b>Undercharge</b><br>
        <html:text tabindex="4" name="STORE_DIST_DETAIL_FORM" property="invoiceAmountPercentAllowanceLower" size="2"/>%</td>
        <td><b>Overcharge</b><br>
        <html:text tabindex="4" name="STORE_DIST_DETAIL_FORM" property="invoiceAmountPercentAllowanceUpper" size="2"/>%</td>
        </tr>
        </table>
    </td>
</tr>

<tr>
    <td>
		<b>Allow Freight On Backorders:</b>
	</td>
    <td>
        Yes&nbsp;<html:radio name="STORE_DIST_DETAIL_FORM" property="allowFreightOnBackorders" value="Y"/>
        &nbsp;&nbsp;&nbsp;
        No&nbsp;<html:radio name="STORE_DIST_DETAIL_FORM" property="allowFreightOnBackorders" value="N"/>
    </td>


    <td><b>Maximum freight allowed in an invoice:</b>
		<br>If an invoice has freight greater than this value, the
		invoice will go into an exception state.
		<br> Zero or blank means this check is not performed.
	</td>
    <td>
		<html:text name="STORE_DIST_DETAIL_FORM" property="maxInvoiceFreightAllowed"/>
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
        Yes&nbsp;<html:radio name="STORE_DIST_DETAIL_FORM" property="cancelBackorderedLines" value="Y"/>
        &nbsp;&nbsp;&nbsp;
        No&nbsp;<html:radio name="STORE_DIST_DETAIL_FORM" property="cancelBackorderedLines" value="N"/>
    </td>

	<td>
		<b>Hold Inbound Invoice:</b>
		<br>Holds the inbound invoices for the configured number of days.
	</td>
    <td>
        <html:text name="STORE_DIST_DETAIL_FORM" property="holdInvoiceDays"/>
    </td>

</tr>
<tr>
    <td>
        <b>Do Not Allow Invoice Edits:</b>
     </td>
    <td>
        Yes&nbsp;<html:radio name="STORE_DIST_DETAIL_FORM" property="doNotAllowInvoiceEdits" value="Y"/>
        &nbsp;&nbsp;&nbsp;
        No&nbsp; <html:radio name="STORE_DIST_DETAIL_FORM" property="doNotAllowInvoiceEdits" value="N"/>
    </td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
</tr>
<tr>
    <td>
		<b>Receiving System Type Code:</b>
		<br>Governs the way that the receiving system works for this distributor.
		If this property is set to &quot;Require Entry&quot; or &quot;Enter Errors Only&quot;
		then an invoice is flagged as an exception if it is entered with a quantity less then the quantity invoiced,
		or if this property is set to &quot;Require Entry&quot; and the order has not been received against.
	</td>
    <td>
	<html:select name="STORE_DIST_DETAIL_FORM" property="receivingSystemInvoiceCd">
		<html:option value="" ><app:storeMessage  key="admin.select"/></html:option>
		<html:options  collection="RECEIVING_SYSTEM_INVOICE_CD" property="value" />
	</html:select>
    </td>

	<td>&nbsp;</td>
    <td>&nbsp;</td>
</tr>

<tr>
  <td>
		<b>Rejected Invoice Email Notification:</b>
		<br>Comma separated list of emails.
	</td>
  <td>
		<html:text name="STORE_DIST_DETAIL_FORM" property="rejectedInvEmail" size="25"/>
  </td>
	<td>&nbsp;</td>
	<td>&nbsp;</td>
</tr>

<tr><td colspan=10
style="border-bottom:solid 1px black;" >&nbsp;</td></tr>


<tr>
    <td><b>Print Customer Contact Info on Purchase Order:</b></td>
    <td>
        Yes&nbsp;<html:radio name="STORE_DIST_DETAIL_FORM" property="printCustContactOnPurchaseOrder" value="Y"/>
        &nbsp;&nbsp;&nbsp;
        No&nbsp;<html:radio name="STORE_DIST_DETAIL_FORM" property="printCustContactOnPurchaseOrder" value="N"/>
    </td>
    <td><b>Manual PO Acknowledgement Required:</b></td>
    <td>
        Yes&nbsp;<html:radio name="STORE_DIST_DETAIL_FORM" property="manualPOAcknowldgementRequiered" value="Y"/>
        &nbsp;&nbsp;&nbsp;
        No&nbsp;<html:radio name="STORE_DIST_DETAIL_FORM" property="manualPOAcknowldgementRequiered" value="N"/>
    </td>
</tr>

<tr valign=top>
<td colspan=3>
<b>Comments To Print On Purchase Order:</b><br>
<html:textarea name="STORE_DIST_DETAIL_FORM"
  property="purchaseOrderComments"
  rows="4" cols="60"/>
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

<tr><td colspan="4" class="largeheader"><br>Primary Manufacturers</td></tr>
<tr><td colspan='4'>
<jsp:include flush='true' page="locateStoreManufacturer.jsp">
   <jsp:param name="jspFormAction" 	value="/storeportal/distconf.do" />
   <jsp:param name="jspFormName" 	value="STORE_DIST_DETAIL_FORM" />
   <jsp:param name="jspSubmitIdent" 	value="" />
   <jsp:param name="jspReturnFilterProperty" value="manufFilter"/>
</jsp:include>
</td>
</tr>
    
<%
   BusEntityDataVector mfgDV = theForm.getPrimaryManufacturers();
   if(mfgDV!=null) {
     for(int ii=0; ii<mfgDV.size(); ii++) {
       BusEntityData mfgD = (BusEntityData) mfgDV.get(ii);
       String delMfgHref="distconf.do?action=DeletePrimaryManufacturer&manufacturerId="+mfgD.getBusEntityId();
%>
<tr>
    <td colspan='4'>
        <%=mfgD.getShortDesc()%><a ID=733 href='<%=delMfgHref%>'>[D]</a>
    </td>
</tr>
<%   }
   }
%>

<tr>
<td colspan="4" styleClass="smalltext">
  <html:hidden name="STORE_DIST_DETAIL_FORM" property="manufacturerId" value="0"/>
  <html:submit property="action" value="Locate Manufacturer" styleClass='text' />
</td>
</tr>

<tr>
    <td colspan=4 align=center>
      <html:submit property="action">
        <app:storeMessage  key="admin.button.saveStoreDistConfig"/>
      </html:submit>
      <html:reset>
        <app:storeMessage  key="admin.button.reset"/>
      </html:reset>
    </td>
</tr>

</table>
  <%}%>

<%-- site list --%>
<logic:present name="distributor.sites.vector">
  <bean:size id="rescount"  name="distributor.sites.vector"/>
  Search result count:  <bean:write name="rescount" />

  <logic:greaterThan name="rescount" value="0">
  <div style="width: 769; text-align: right;">
  <a href="javascript:SetChecked(1)">[Check&nbsp;All]</a>
  <a href="javascript:SetChecked(0)">[&nbsp;Clear]</a> <br>
  </div>

  <table width="<%=Constants.TABLEWIDTH%>" border="0"   class="stpTable_sortable"  id="ts2" >
  <thead>
  <tr align=left>
  <th class="stpTH">Site Id</th>
  <th class="stpTH">Account Name</th>
  <th class="stpTH">Site Name</th>
  <th class="stpTH">Street Address</th>
  <th class="stpTH">City</th>
  <th class="stpTH">State</th>
  <th class="stpTH">County</th>
  <th class="stpTH">Zip Code</th>
  <th class="stpTH">Status</th>
  <th class="stpTH">Select</th>
  </tr>
  </thead>
  <tr>
  <logic:iterate id="arrele" name="distributor.sites.vector">
      <bean:define id="eleid" name="arrele" property="id"/>
      <td><bean:write name="arrele" property="id"/></td>
      <td><bean:write name="arrele" property="accountName"/></td>
      <td><bean:write name="arrele" property="name"/></td>
      <td><bean:write name="arrele" property="address"/></td>
      <td><bean:write name="arrele" property="city"/></td>
      <td><bean:write name="arrele" property="state"/></td>
      <td><bean:write name="arrele" property="county"/></td>
      <td><bean:write name="arrele" property="postalCode"/></td>
      <td><bean:write name="arrele" property="status"/></td>
  <td><html:multibox name="STORE_DIST_DETAIL_FORM" property="selectIds" value="<%=(\"\"+eleid)%>" /></td>
  <html:hidden name="STORE_DIST_DETAIL_FORM" property="displayIds" value="<%=(\"\"+eleid)%>" />
  </tr>
  </logic:iterate>
  </table>

  <table ID=1204>
  <tr>
  <td>
  <html:submit property="action">
  <app:storeMessage  key="global.action.label.save"/>
  </html:submit>
  </td>
  </tr>
  </table>

  </logic:greaterThan>
  </logic:present>


<%-- catalog list --%>
<logic:present name="distributor.catalogs.vector">
  <bean:size id="rescount"  name="distributor.catalogs.vector"/>
  Search result count:  <bean:write name="rescount" />
  <logic:greaterThan name="rescount" value="0">

  <div style="width: 769; text-align: right;">
   <a href="javascript:SetChecked(1)">[Check&nbsp;All]</a>
   <a href="javascript:SetChecked(0)">[&nbsp;Clear]</a> <br>
  </div>


  <table width="<%=Constants.TABLEWIDTH%>"   class="stpTable_sortable" id="ts2">
  <thead>
      <tr align=left>
          <th class="stpTH">Catalog Id</th>
          <th class="stpTH">Name</th>
          <th class="stpTH">Status</th>
          <th class="stpTH">Type</th>
          <th class="stpTH">Select</th>
      </tr>
  </thead>

  <logic:iterate id="arrele" name="distributor.catalogs.vector">
  <tr>
  <td><bean:write name="arrele" property="catalogId"/></td>
  <td>
  <bean:define id="eleid" name="arrele" property="catalogId"/>
  <a ID=1203 href="storecatalogdet.do?action=edit&id=<%=eleid%>">
  <bean:write name="arrele" property="shortDesc"/>
  </a>
  </td>
  <td><bean:write name="arrele" property="catalogStatusCd"/></td>
  <td><bean:write name="arrele" property="catalogTypeCd"/></td>

      <td><html:multibox name="STORE_DIST_DETAIL_FORM" property="selectIds" value="<%=(\"\"+eleid)%>" /></td>
      <html:hidden name="STORE_DIST_DETAIL_FORM" property="displayIds" value="<%=(\"\"+eleid)%>" />
  </tr>

  </logic:iterate>
  </table>

  <table ID=1204>
  <tr>
  <td>
  <html:submit property="action">
  <app:storeMessage  key="global.action.label.save"/>
  </html:submit>
  </td>
  </tr>
  </table>
  </logic:greaterThan>
  </logic:present>

</div>
</html:form>


