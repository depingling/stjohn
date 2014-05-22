<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<script language="JavaScript1.2">
<!--
function popLocate(pLoc, name, pDesc) {
  var loc = pLoc + ".do?feedField=" + name + "&amp;feedDesc=" + pDesc;
  locatewin = window.open(loc,"Locate", "menubar=no,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
  locatewin.focus();

  return false;
}
function popDistLocate(name,name1) {
  var loc = "distlocate.do?feedField=" + name+"&feedDesc="+name1;
  locatewin = window.open(loc,"tickersearch1", "menubar=no,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
  locatewin.focus();

  return false;
}
function setBusEntityType(f,val) {
  f.newBusEntityTypeString.value = val;
}
function delete_assoc(busEntityId) {
	var f = document.getElementById('tradingPartnerForm');
	var actionIdEl = document.getElementById('actionId');
	var busEntityIdEl = document.getElementById('busEntityId');
	if (f && actionIdEl && busEntityIdEl) {
		actionIdEl.value = 'delete_assoc';
		busEntityIdEl.value = busEntityId;
		submit_form();
		f.submit();
	}
}

var commonEl = ["siteIdentifierTypeCd", "accountIdentifierInbound",
                "allow856Flag", "allow856Email"];
function submit_form() {
	for (i in commonEl) {
		var f1_el = document.getElementById("form1_" + commonEl[i]);
	    var f2_el = document.getElementById("form2_" + commonEl[i]);
	    if (f1_el && f2_el) {
		    if (f2_el.tagName == 'SELECT') {
		        f1_el.value = f2_el[f2_el.selectedIndex].value;
		    } else if (f2_el.tagName == 'INPUT' && f2_el.type == 'checkbox') {
		    	f1_el.value = f2_el.checked ? f2_el.value : "";
		    } else {
		        f1_el.value = f2_el.disabled ? "" : f2_el.value;
		    }
	    }
	}
}
//-->
</script>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="theForm" name="TRADING_PARTNER_FORM" type="com.cleanwise.view.forms.TradingPartnerMgrForm"/>

<div class="text">
  <table width="769" cellpadding="4" border="0" class="mainbody">
  <html:form name="TRADING_PARTNER_FORM" styleId="tradingPartnerForm"
    action="adminportal/tradingpartnermgr.do"
    onsubmit="submit_form();"
    type="com.cleanwise.view.forms.TradingPartnerMgrForm">

   <tr>
     <td><b>Partner Id:</b></td>
     <td><b><%=theForm.getTradingPartner().getTradingPartnerId()%></b></td>
     <td><b>&nbsp;</b></td>
     <td><b>&nbsp;</b></td>
  </tr>
   <tr>
     <td><b>Partner Name:</b></td>
     <td><html:text name="TRADING_PARTNER_FORM" property="tradingPartner.shortDesc" size="30" maxlength="30"/></td>
     <td><b>Partner Type:</b></td>
     <td>
       <logic:notEqual name="TRADING_PARTNER_FORM" property="tradingPartner.tradingPartnerId" value="0">
       <logic:equal name="TRADING_PARTNER_FORM" property="tradingPartner.tradingPartnerTypeCd" value="<%=RefCodeNames.TRADING_PARTNER_TYPE_CD.CUSTOMER%>">
         &nbsp;&nbsp;&nbsp<b>Customer</b>
       </logic:equal>
       <logic:equal name="TRADING_PARTNER_FORM" property="tradingPartner.tradingPartnerTypeCd" value="<%=RefCodeNames.TRADING_PARTNER_TYPE_CD.DISTRIBUTOR%>">
         &nbsp;&nbsp;&nbsp<b>Distributor</b>
       </logic:equal>
       <logic:equal name="TRADING_PARTNER_FORM" property="tradingPartner.tradingPartnerTypeCd" value="<%=RefCodeNames.TRADING_PARTNER_TYPE_CD.MANUFACTURER%>">
         &nbsp;&nbsp;&nbsp<b>Manufacturer</b>
       </logic:equal>
       <logic:equal name="TRADING_PARTNER_FORM" property="tradingPartner.tradingPartnerTypeCd" value="<%=RefCodeNames.TRADING_PARTNER_TYPE_CD.STORE%>">
         &nbsp;&nbsp;&nbsp<b>Store</b>
       </logic:equal>
       </logic:notEqual>
       <logic:equal name="TRADING_PARTNER_FORM" property="tradingPartner.tradingPartnerId" value="0">

       <html:hidden property="partnerTypeChange" value="" />
       <html:select name="TRADING_PARTNER_FORM" property="tradingPartner.tradingPartnerTypeCd" onchange="document.forms[0].partnerTypeChange.value='type'; document.forms[0].submit()">
       <html:option value="<%=RefCodeNames.TRADING_PARTNER_TYPE_CD.CUSTOMER%>">Customer</html:option>
       <html:option value="<%=RefCodeNames.TRADING_PARTNER_TYPE_CD.DISTRIBUTOR%>">Distributor</html:option>
       <html:option value="<%=RefCodeNames.TRADING_PARTNER_TYPE_CD.MANUFACTURER%>">Manufacturer</html:option>
       <html:option value="<%=RefCodeNames.TRADING_PARTNER_TYPE_CD.APPLICATION%>">Application</html:option>
       <html:option value="<%=RefCodeNames.TRADING_PARTNER_TYPE_CD.STORE%>">Store</html:option>
       </html:select>
       </logic:equal>
     </td>
  </tr>
<!--  Locate account/distributor -->
<%
boolean isABusEntity = true;
boolean showGroupSender =
  theForm.getTradingPartner().getTradingPartnerTypeCd().equals(RefCodeNames.TRADING_PARTNER_TYPE_CD.CUSTOMER) ||
  theForm.getTradingPartner().getTradingPartnerTypeCd().equals(RefCodeNames.TRADING_PARTNER_TYPE_CD.DISTRIBUTOR);
boolean isADistributor =
  theForm.getTradingPartner().getTradingPartnerTypeCd().equals(RefCodeNames.TRADING_PARTNER_TYPE_CD.DISTRIBUTOR);
boolean isPunchOutType = theForm.getTradingPartner().getTradingTypeCd().equals(RefCodeNames.TRADING_TYPE_CD.PUNCHOUT);
%>
<logic:equal name="TRADING_PARTNER_FORM"
  property="tradingPartner.tradingPartnerTypeCd" value="<%=RefCodeNames.TRADING_PARTNER_TYPE_CD.APPLICATION%>">
        <%isABusEntity = false;%>
</logic:equal>

<%if(isABusEntity){%>
<tr>
<td colspan='4'>
  <table width="600" cellpadding="0" border="2" class="mainbody">
<logic:equal name="TRADING_PARTNER_FORM"
  property="tradingPartner.tradingPartnerTypeCd" value="<%=RefCodeNames.TRADING_PARTNER_TYPE_CD.DISTRIBUTOR%>">
          <%
          boolean distAssoc = false;
          boolean accAssoc = false;
          boolean isFirstAccount = true;
          BusEntityDataVector busEntDV = theForm.getBusEntities();
          TradingPartnerAssocDataVector tpAV = theForm.getTPAssocies();
          for(int ii=0; ii<busEntDV.size();ii++) {
            BusEntityData busEntD = (BusEntityData) busEntDV.get(ii);
            if (busEntD.getBusEntityTypeCd().equals(RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT)) {
              accAssoc = true;
            } else {
              distAssoc = true;
            }
          }
          %>
          <%
            if (distAssoc) {
              boolean isHeader = true;
              for(int ii=0; ii<busEntDV.size();ii++)  {
                BusEntityData busEntD = (BusEntityData) busEntDV.get(ii);
                TradingPartnerAssocData tpAD = (TradingPartnerAssocData) tpAV.get(ii);
                String groupSender = (tpAD.getGroupSenderOverride()!=null)?tpAD.getGroupSenderOverride():"";
                if (!busEntD.getBusEntityTypeCd().equals(RefCodeNames.BUS_ENTITY_TYPE_CD.DISTRIBUTOR)) {
                  continue;
                }
                if (isHeader) { %>
                <tr><td colspan="4"><b>Distributors</b></td></tr>
                <tr>
                  <td><b>Id</b></td>
                  <td><b>Name</b></td>
                  <% if (accAssoc) { %>
                  <td>&nbsp;</td>
                  <% } %>
                  <td>&nbsp;</td>
                </tr>
               <% isHeader = false;
               } %>

               <tr>
                 <td><%=busEntD.getBusEntityId()%></td>
                 <td><%=busEntD.getShortDesc()%></td>
                 <% if(accAssoc){%>
                 <td>&nbsp;</td>
                 <% } %>
                 <td><a href="#" onclick="delete_assoc('<%=busEntD.getBusEntityId()%>');return false;">[Delete]</a></td>
               </tr>
            <%}%>
          <%}%>
          <%
            if (accAssoc) {
              boolean isHeader = true;
              for(int ii=0; ii<busEntDV.size();ii++)  {
                BusEntityData busEntD = (BusEntityData) busEntDV.get(ii);
                TradingPartnerAssocData tpAD = (TradingPartnerAssocData) tpAV.get(ii);
                String groupSender = (tpAD.getGroupSenderOverride()!=null)?tpAD.getGroupSenderOverride():"";
                if (!busEntD.getBusEntityTypeCd().equals(RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT)) {
                  continue;
                }
                if (isHeader) { %>
                <tr><td colspan="4"><b>Accounts</b></td></tr>
                <tr>
                  <td><b>Id</b></td>
                  <td><b>Name</b></td>
                  <td><b>Group Sender</b><span class="reqind"> *</span></td>
                  <td>&nbsp;</td>
                </tr>
               <% isHeader = false;
               } %>

               <tr>
                 <td><%=busEntD.getBusEntityId()%></td>
                 <td><%=busEntD.getShortDesc()%></td>
                 <td><input type="text" name="groupSender[<%=tpAD.getBusEntityId()%>]" value="<%=groupSender%>" size="14"/></td>
                 <td><a href="#" onclick="delete_assoc('<%=busEntD.getBusEntityId()%>');return false;">[Delete]</a></td>
               </tr>
            <%}%>
          <%}%>

           <tr>
            <td>
              <table cellpadding="0" cellspacing="0" border="0">
              <tr>
                <td><html:text styleClass="smalltext" name="TRADING_PARTNER_FORM" property="newBusEntityIdString" size="7"/></td>
                <td>
                  <html:button  styleClass="smalltext"
                    onclick="return popLocate('distlocate', 'newBusEntityIdString', 'newBusEntityDesc');"
                    value="Locate Distributor" property="action" />
                 </td>
               </tr>
               <tr><td>&nbsp;</td>
                <td>
                  <html:button  styleClass="smalltext"
                    onclick="return popLocate('accountlocate', 'newBusEntityIdString', 'newBusEntityDesc');"
                    value="Locate Account" property="action" />
                </td>
              </tr>
            </table>
           </td>
            <td valign="middle">
             <html:text name="TRADING_PARTNER_FORM" property="newBusEntityDesc" readonly="true" styleClass="mainbodylocatename"/>
             <html:submit styleClass="smalltext" value="Add Association" property="action" />
           </td>
            <% if(busEntDV!=null && busEntDV.size()>0){%>
            <td>&nbsp; </td>
            <% if(showGroupSender && accAssoc){%>
            <td>&nbsp; </td>
            <%}%>
            <%}%>
           </tr>
</logic:equal>


<logic:notEqual name="TRADING_PARTNER_FORM"
  property="tradingPartner.tradingPartnerTypeCd" value="<%=RefCodeNames.TRADING_PARTNER_TYPE_CD.DISTRIBUTOR%>">
          <%
          BusEntityDataVector busEntDV = theForm.getBusEntities();
          TradingPartnerAssocDataVector tpAV = theForm.getTPAssocies();
          %>

     <tr>
         <td><b>Id</b></td>
          <td><b>Name</b></td>
          <% if(busEntDV!=null && busEntDV.size()>0){%>
          <% if (showGroupSender) { %>
          <td><b>Group Sender</b></td>
          <% } %>
          <td>&nbsp;</td>
          <% } %>
     </tr>
          <%
          boolean isPunchOutAcctAssoc = false;
          for(int ii=0; ii<busEntDV.size();ii++)  {
             BusEntityData busEntD = (BusEntityData) busEntDV.get(ii);
             if (isPunchOutType && busEntD.getBusEntityTypeCd().equals(RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT)){
            	 isPunchOutAcctAssoc = true;
            	 continue;
             }
             TradingPartnerAssocData tpAD = (TradingPartnerAssocData) tpAV.get(ii);
             String groupSender = (tpAD.getGroupSenderOverride()!=null)?tpAD.getGroupSenderOverride():"";
          %>

           <tr>
             <td><%=busEntD.getBusEntityId()%></td>
             <td><%=busEntD.getShortDesc()%></td>
             <% if(showGroupSender){%>
             <td><input type="text" name="groupSender[<%=tpAD.getBusEntityId()%>]" value="<%=groupSender%>" size="14"/></td>
             <%}%>
             <td><a href="#" onclick="delete_assoc('<%=busEntD.getBusEntityId()%>');return false">[Delete]</a></td>
           </tr>
          <%}%>
		  <% if (isPunchOutAcctAssoc) 
		  	 {
			  	boolean isHeader = true;		  	 
			  	for(int ii=0; ii<busEntDV.size();ii++)  {
		             BusEntityData busEntD = (BusEntityData) busEntDV.get(ii);
		             if (!busEntD.getBusEntityTypeCd().equals(RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT)){
		            	 continue;
		             }
		             if (isHeader) { isHeader = false; %>
		             	<tr>
					         <td colspan="3">&nbsp;</td>
					     </tr>
		            	 <tr>
					         <td><b>Account Id</b></td>
					          <td><b>Account Name</b></td>
					          <td>&nbsp;</td>
					     </tr>
		             <%}%>
				  	<tr>
		             <td><%=busEntD.getBusEntityId()%></td>
		             <td><%=busEntD.getShortDesc()%></td>
		             <td><a href="#" onclick="delete_assoc('<%=busEntD.getBusEntityId()%>');return false">[Delete]</a></td>
		           </tr>
		  	 <%}
		  	 }
		  %>
           <tr>
             <html:hidden property="newBusEntityTypeString" value="" />
            <td>              
            <%
             String locateP=null;
            %>
            <logic:equal name="TRADING_PARTNER_FORM" property="tradingPartner.tradingPartnerTypeCd" value="<%=RefCodeNames.TRADING_PARTNER_TYPE_CD.CUSTOMER%>">
                <%locateP="accountlocate";%>
            </logic:equal>
            <logic:equal name="TRADING_PARTNER_FORM" property="tradingPartner.tradingPartnerTypeCd" value="<%=RefCodeNames.TRADING_PARTNER_TYPE_CD.MANUFACTURER%>">
                <%locateP="manuflocate";%>
            </logic:equal>
            <logic:equal name="TRADING_PARTNER_FORM" property="tradingPartner.tradingPartnerTypeCd" value="<%=RefCodeNames.TRADING_PARTNER_TYPE_CD.STORE%>">
            	<%locateP="storelocate";%>            	     
            </logic:equal>
            <%locateP="return popLocate('"+locateP+"', 'newBusEntityIdString', 'newBusEntityDesc');";%>
            <% if (!isPunchOutType) { %>
            	<html:text styleClass="smalltext" name="TRADING_PARTNER_FORM" property="newBusEntityIdString" size="7"/>
                <html:button  styleClass="smalltext" onclick="<%=locateP%>" value="Locate" property="action" />
            <% } else { %>
              <table cellpadding="0" cellspacing="0" border="0">
	           <tr>
	           	 <td rowspan="2"><html:text styleClass="smalltext" name="TRADING_PARTNER_FORM" property="newBusEntityIdString" size="7"/></td>	           	 
	             <td>
	               <html:button  styleClass="smalltext"
	                 onclick="return popLocate('storelocate', 'newBusEntityIdString', 'newBusEntityDesc');"
	                 value="Locate Store" property="action" />
	              </td>
	            </tr>
	            <tr>
	             <td>
	               <html:button  styleClass="smalltext"
	                 onclick="return popLocate('accountlocate', 'newBusEntityIdString', 'newBusEntityDesc');"
	                 value="Locate Account" property="action" />
	             </td>
	           </tr>
	         </table>
            <% } %>  
            </td>
            <td valign="middle">
             <html:text name="TRADING_PARTNER_FORM" property="newBusEntityDesc" readonly="true" styleClass="mainbodylocatename"/>
             <html:submit styleClass="smalltext" value="Add Association" property="action" />
           </td>
            <% if(busEntDV!=null && busEntDV.size()>0){%>
            <td>&nbsp; </td>
            <% if(showGroupSender){%>
            <td>&nbsp; </td>
            <%}%>
            <%}%>
           </tr>
</logic:notEqual>


         </table>
         </td>
<%}//end isabusentity
%>


<logic:equal name="TRADING_PARTNER_FORM"
  property="tradingPartner.tradingPartnerTypeCd" value="<%=RefCodeNames.TRADING_PARTNER_TYPE_CD.DISTRIBUTOR%>">
<logic:equal name="TRADING_PARTNER_FORM" property="tradingPartner.tradingPartnerId" value="0">
  <tr>
        <td colspan=2><b>Reset Existing Pos to &quot;Distributor_Acknowledged&quot;</b><br>(set to &quot;No&quot; if you want any pos currently in the system to be faxed).</td>
        <td>
            <html:radio name="TRADING_PARTNER_FORM" property="initializeExistingPos" value="true" />
            Yes
            <html:radio name="TRADING_PARTNER_FORM" property="initializeExistingPos" value="false" />
            No
        </td>
        <td>&nbsp;</td>
  </tr>
</logic:equal>
</logic:equal>

<!--   -->
   <tr>
     <td><b>Sku&nbsp;Type:</b></td>
     <td>
       <html:select name="TRADING_PARTNER_FORM" property="tradingPartner.skuTypeCd">
       <html:option value="<%=RefCodeNames.SKU_TYPE_CD.CLW%>">Cleanwise</html:option>
       <html:option value="<%=RefCodeNames.SKU_TYPE_CD.CUSTOMER%>">Customer</html:option>
       <html:option value="<%=RefCodeNames.SKU_TYPE_CD.DISTRIBUTOR%>">Distributor</html:option>
       <html:option value="<%=RefCodeNames.SKU_TYPE_CD.MANUFACTURER%>">Manufacturer</html:option>
       </html:select>
     </td>
     <td><b>Trading&nbsp;Status:</b></td>
     <td>
       <html:select name="TRADING_PARTNER_FORM" property="tradingPartner.tradingPartnerStatusCd">
       <html:option value="<%=RefCodeNames.TRADING_PARTNER_STATUS_CD.ACTIVE%>">Active</html:option>
       <html:option value="<%=RefCodeNames.TRADING_PARTNER_STATUS_CD.INACTIVE%>">Inactive</html:option>
       <html:option value="<%=RefCodeNames.TRADING_PARTNER_STATUS_CD.LIMITED%>">Limited</html:option>
       </html:select>
     </td>
  </tr>
   <tr>
     <td><b>Trading&nbsp;Type:</b></td>
     <td>
       <html:hidden property="tradingTypeChange" value="" />
       <html:select name="TRADING_PARTNER_FORM" property="tradingPartner.tradingTypeCd" onchange="document.forms[0].tradingTypeChange.value='type'; document.forms[0].submit()">
       <html:option value="<%=RefCodeNames.TRADING_TYPE_CD.PAPER%>">Non electronic</html:option>
       <html:option value="<%=RefCodeNames.TRADING_TYPE_CD.EDI%>">EDI</html:option>
       <html:option value="<%=RefCodeNames.TRADING_TYPE_CD.XML%>">XML</html:option>
       <html:option value="<%=RefCodeNames.TRADING_TYPE_CD.FAX%>">FAX</html:option>
       <html:option value="<%=RefCodeNames.TRADING_TYPE_CD.OTHER%>">OTHER</html:option>
       <html:option value="<%=RefCodeNames.TRADING_TYPE_CD.EMAIL%>">EMAIL</html:option>
       <logic:equal name="TRADING_PARTNER_FORM" property="tradingPartner.tradingPartnerTypeCd" value="<%=RefCodeNames.TRADING_PARTNER_TYPE_CD.STORE%>">
       	   <html:option value="<%=RefCodeNames.TRADING_TYPE_CD.PUNCHOUT%>">PUNCHOUT</html:option>
       </logic:equal>
       </html:select>
     </td>
     </td>&nbsp;</tr>
     </td>&nbsp;</tr>
   </tr>
   <tr>
     <logic:equal name="TRADING_PARTNER_FORM" property="tradingPartner.tradingPartnerTypeCd" value="<%=RefCodeNames.TRADING_PARTNER_TYPE_CD.DISTRIBUTOR%>">
       <td><b>Uom Conversion Type:</b></td>
       <td>
         <html:select name="TRADING_PARTNER_FORM" property="tradingPartner.uomConversionTypeCd">
         <html:option value="<%=RefCodeNames.UOM_CONVERSION_TYPE_CD.NONE %>">NONE</html:option>
         <html:option value="<%=RefCodeNames.UOM_CONVERSION_TYPE_CD.CONVERT_UOM_TO_EACH%>">UOM_CONVERSION_TYPE_CD</html:option>
         </html:select>
       </td>
       <td><b>Purchase Order Number Rendering:</b></td>
       <td>
    <html:select name="TRADING_PARTNER_FORM" property="tradingPartner.poNumberType">
         <html:option value="<%=RefCodeNames.PO_NUMBER_RENDERING_TYPE_CD.PLAIN%>">PLAIN</html:option>
         <html:option value="<%=RefCodeNames.PO_NUMBER_RENDERING_TYPE_CD.CONCAT_WITH_ERP_ORDER%>">CONCAT_WITH_ERP_ORDER</html:option>
    </html:select>
       </td>
     </logic:equal>
  </tr>
<tr>

<td valign=top bgcolor=white>
<b>Request Data Checks</b><br>
<html:checkbox  name="TRADING_PARTNER_FORM"  property="checkUOM" />
<b>Check&nbsp;UOM</b>
<br>
<html:checkbox  name="TRADING_PARTNER_FORM"  property="checkAddress" />
<b>Check&nbsp;address</b>
</td>
<td valign=top bgcolor=#ffffcc>
<b>Data Processing Validation</b><br>
<html:checkbox  name="TRADING_PARTNER_FORM"  property="validateRefOrderNum" />
<b>Validate Ref Order Number</b>
<br>
<html:checkbox  name="TRADING_PARTNER_FORM"  property="validateCustomerItemDesc" />
<b>Validate Customer Item Description</b>
<br>
<html:checkbox  name="TRADING_PARTNER_FORM"  property="validateCustomerSkuNum" />
<b>Validate Customer SKU Number</b>
<br>
<html:checkbox  name="TRADING_PARTNER_FORM"  property="validateContractPrice" />
<b>Validate Received Amount</b>
<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(If amount doesn't agree with catalog, order will go into review.)
<br>
<html:checkbox  name="TRADING_PARTNER_FORM"  property="useInboundAmountForCostAndPrice" />
<b>Use inbound 850 amount for order cost and price</b>
<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(DO NOT select ‘Validate Received Amount’ if using this feature.)
</td>
<td colspan="2" valign=top bgcolor=#ffffcc>
<html:checkbox  name="TRADING_PARTNER_FORM"  property="processInvoiceCredit" />&nbsp;<b>Process Invoice Credit</b>
<br>
<html:checkbox  name="TRADING_PARTNER_FORM"  property="usePoLineNumForInvoiceMatch" />
<b>Use Inbound invoice PO Line Num for invoice matching</b>
<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(not DIST SKU)
<br>
<html:checkbox  name="TRADING_PARTNER_FORM"  property="relaxValidateInboundDuplInvoiceNum" />
<b>Relax validation of inbound 810 duplicate invoice numbers</b>
</td>
</td>
</tr>



<logic:equal name="TRADING_PARTNER_FORM" property="tradingPartner.tradingTypeCd" value="<%=RefCodeNames.TRADING_TYPE_CD.FAX%>">
    <html:hidden  name="TRADING_PARTNER_FORM" property="purchaseOrderFreightTerms" value="Prepaid" />
    <html:hidden name="TRADING_PARTNER_FORM" property="purchaseOrderDueDays" value="30" />
    <tr>
        <td><b>Purchase Order Fax Number:</b></td>
        <td><html:text tabindex="21" name="TRADING_PARTNER_FORM" property="purchaseOrderFaxNumber" size="25" maxlength="40" /></td>
    </tr>
    <tr>
        <td><b>Fax To Contact Name:</b></td>
        <td><html:text tabindex="21" name="TRADING_PARTNER_FORM" property="toContactName" maxlength="80" /></td>
    </tr>
    <tr>
        <td><b>Fax From Contact Name:</b></td>
        <td><html:text tabindex="21" name="TRADING_PARTNER_FORM" property="fromContactName" maxlength="40" /></td>
    </tr>
</logic:equal>

<logic:equal name="TRADING_PARTNER_FORM" property="tradingPartner.tradingTypeCd" value="<%=RefCodeNames.TRADING_TYPE_CD.EMAIL%>">
    <html:hidden  name="TRADING_PARTNER_FORM" property="purchaseOrderFreightTerms" value="Prepaid" />
    <html:hidden name="TRADING_PARTNER_FORM" property="purchaseOrderDueDays" value="30" />
    <tr>
        <td><b>Email From:</b></td>
        <td><html:text tabindex="21" name="TRADING_PARTNER_FORM" property="emailFrom" size="50" maxlength="50" /></td>
    </tr>
    <tr>
        <td><b>Email To:</b></td>
        <td><html:text tabindex="21" name="TRADING_PARTNER_FORM" property="emailTo" size="50" maxlength="50" /></td>
    </tr>
    <tr>
        <td><b>Email Subject:</b></td>
        <td><html:text tabindex="21" name="TRADING_PARTNER_FORM" property="emailSubject" size="50" maxlength="150" /></td>
    </tr>
    <tr>
        <td><b>Email Body Template:</b></td>
        <td><html:text tabindex="21" name="TRADING_PARTNER_FORM" property="emailBodyTemplate" size="70" maxlength="300" /></td>
    </tr>    
</logic:equal>


  <tr> <td>&nbsp;</td>
       <td colspan="3">
        <html:submit property="action">
                <app:storeMessage  key="global.action.label.save"/>
        </html:submit>
  <logic:notEqual name="TRADING_PARTNER_FORM" property="tradingPartner.tradingPartnerId" value="0">
        <html:submit property="action">
                <app:storeMessage  key="global.action.label.delete"/>
        </html:submit>
  </logic:notEqual>
    </td>
  </tr>
<html:hidden styleId="actionId" property="action" value="" />
<html:hidden styleId="busEntityId" property="busEntityId" value="" />
<html:hidden name="TRADING_PARTNER_FORM" property="tradingPartner.siteIdentifierTypeCd"     styleId="form1_siteIdentifierTypeCd" />
<html:hidden name="TRADING_PARTNER_FORM" property="tradingPartner.accountIdentifierInbound" styleId="form1_accountIdentifierInbound" />
<html:hidden name="TRADING_PARTNER_FORM" property="allow856Flag"  styleId="form1_allow856Flag" />
<html:hidden name="TRADING_PARTNER_FORM" property="allow856Email" styleId="form1_allow856Email"/>
</html:form>

  <logic:equal name="TRADING_PARTNER_FORM" property="tradingPartner.tradingTypeCd" value="<%=RefCodeNames.TRADING_TYPE_CD.EDI%>">
    <tr>
    <td colspan="4">
    <jsp:include flush='true' page="tradingEdiProfileMgrDetail.jsp"/>
    </td>
    </tr>
  </logic:equal>

  <logic:equal name="TRADING_PARTNER_FORM" property="tradingPartner.tradingTypeCd" value="<%=RefCodeNames.TRADING_TYPE_CD.OTHER%>">
    <tr>
    <td colspan="4">
    <jsp:include flush='true' page="tradingEdiProfileMgrDetail.jsp"/>
    </td>
    </tr>
  </logic:equal>

  <logic:equal name="TRADING_PARTNER_FORM" property="tradingPartner.tradingTypeCd" value="<%=RefCodeNames.TRADING_TYPE_CD.XML%>">
    <tr>
    <td colspan="4">
    <jsp:include flush='true' page="tradingEdiProfileMgrDetail.jsp"/>
    </td>
    </tr>
  </logic:equal>
  
  <% if (theForm.getTradingPartner().getTradingTypeCd().equals(RefCodeNames.TRADING_TYPE_CD.PUNCHOUT)){ %>
	    <tr>
	    <td colspan="4">
	    <jsp:include flush='true' page="tradingEdiProfileMgrDetail.jsp"/>
	    </td>
	    </tr>
  <% }%>
</table>
</div>

