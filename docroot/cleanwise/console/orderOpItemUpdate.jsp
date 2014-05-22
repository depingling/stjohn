<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<app:checkLogon/>


<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="detailaction" name="ORDER_OP_ITEM_UPDATE_FORM" property="itemAction" />

<%
	String action = new String("");
	action = request.getParameter("action");
	if(null == action) {
		action = new String("");
	}

%>

<script language="JavaScript1.2">
<!--
function initpage() {
	<% if( "close".equals(action)) {  %>
		window.opener.location.reload();
		window.close();
	<% } %>	 	
 	 	return false;
}

//-->
</script>

<html:html>

<head>
<title>Operations Console Home: Update Order Item Detail</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="../externals/styles.css">
</head>

<body bgcolor="#FFFFFF" onload="return initpage();">

<div class="text">
<font color=red>
<html:errors/>
</font>

<table border="0" cellpadding="1" cellspacing="0" width="769" class="mainbody">
<html:form name="ORDER_OP_ITEM_UPDATE_FORM" action="/console/orderOpItemUpdate.do" focus='elements[0]'
	type="com.cleanwise.view.forms.OrderOpItemUpdateForm">

  	<tr> 
    	<td colspan="8" class="largeheader">Update Order Item Detail</td>
	</tr>		

	<tr>		
		<td><b>Line&nbsp;#</b></td>
		<td><b>Dist Order#</b></td>
		<td><b>ERP PO#</b></td>
		<td><b>CW SKU#</b></td>
		<td><b>Product Name</b></td>
		<td><b>Dist SKU#</b></td>
		<td><b>Dist Name</b></td>
		<td><b>Item Size</b></td>
	</tr>
	<tr>
		<td><bean:write name="ORDER_OP_ITEM_UPDATE_FORM" property="orderItemStatusDesc.orderItemStatus.poLineNumber"/>&nbsp;</td>
		<td><bean:write name="ORDER_OP_ITEM_UPDATE_FORM" property="distOrderNum"/>&nbsp;</td>		
		<td><bean:write name="ORDER_OP_ITEM_UPDATE_FORM" property="orderItemStatusDesc.erpPoDetail.erpPoNumber"/>&nbsp;</td>
		<td><bean:write name="ORDER_OP_ITEM_UPDATE_FORM" property="orderItemStatusDesc.orderItemStatus.cwSkuNumber"/>&nbsp;</td>
		<td><bean:write name="ORDER_OP_ITEM_UPDATE_FORM" property="orderItemStatusDesc.orderItemStatus.productName"/>&nbsp;</td>
		<td><bean:write name="ORDER_OP_ITEM_UPDATE_FORM" property="orderItemStatusDesc.orderItemStatus.vendorSku"/>&nbsp;</td>
		<td><bean:write name="ORDER_OP_ITEM_UPDATE_FORM" property="orderItemStatusDesc.erpPoDetail.vendorId"/>&nbsp;</td>
		<td><bean:write name="ORDER_OP_ITEM_UPDATE_FORM" property="orderItemStatusDesc.orderItemStatus.itemSize"/>&nbsp;</td>		
	</tr>

	<tr><td colspan="8">&nbsp;</td></tr>				
	<tr>
		<td>&nbsp;</td>	
		<td><b>UOM</b></td>
		<td><b>Pack</b></td>
		<td><b>CW Cost</b></td>
		<td><b>Quantity</b></td>
		<td colspan="2"><b>Action</b></td>
		<td><b>Date</b></td>
	</tr>	
	<tr>
		<td>&nbsp;</td>
	<% if (RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.ACK_ACCEPTED_CHANGES_MADE.equals(detailaction)) { %>			
		<td><html:text name="ORDER_OP_ITEM_UPDATE_FORM" property="uom" size="3" maxlength="10"/>&nbsp;</td>		
		<td><html:text name="ORDER_OP_ITEM_UPDATE_FORM" property="pack" size="5" maxlength="15"/>&nbsp;</td>		
		<td><html:text name="ORDER_OP_ITEM_UPDATE_FORM" property="cwCost" size="7" maxlength="20"/>&nbsp;</td>		
	<% } else {  %>
		<td><bean:write name="ORDER_OP_ITEM_UPDATE_FORM" property="orderItemStatusDesc.orderItemStatus.uom"/>&nbsp;</td>
		<td><bean:write name="ORDER_OP_ITEM_UPDATE_FORM" property="orderItemStatusDesc.orderItemStatus.pack"/>&nbsp;</td>
		<td><html:text name="ORDER_OP_ITEM_UPDATE_FORM" property="quantity" size="7" maxlength="15"/>&nbsp;</td>		
		<td colspan="2">
			<html:hidden property="change" value="" />
			<html:select name="ORDER_OP_ITEM_UPDATE_FORM" property="itemAction" onchange="document.forms[0].change.value='type'; document.forms[0].submit();">
				<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
				<html:options  collection="ItemDetail.action.vector" property="value" />
				<html:option value="Shipped">Shipped</html:option>
				<html:option value="Invoiced">Invoiced</html:option>
			</html:select>
		</td>		
		<td><html:text name="ORDER_OP_ITEM_UPDATE_FORM" property="actionDateS" size="10" maxlength="15"/>&nbsp;</td>
	</tr>
		
	<tr><td colspan="8">&nbsp;</td></tr>		

	<% if (! "Shipped".equals(detailaction) && ! "Invoiced".equals(detailaction)) { %>				
  	<tr> 
    	<td colspan="8" class="largeheader">Add Item Action Note</td>
	</tr>		

<!--	
	<tr>
		<td class="mediumheader">Name:</td>
		<td colspan="7">
			<html:text name="ORDER_OP_ITEM_UPDATE_FORM" property="itemActionNote.addBy" value=""/>
		</td>
	</tr>	
-->
	
	<tr>
		<td class="mediumheader">Note:</td>
		<td colspan="7">
			<html:textarea name="ORDER_OP_ITEM_UPDATE_FORM" property="itemActionNote.value" rows="10" cols="30" />	
		</td>
	</tr>
	
	<% } else if ("Shipped".equals(detailaction)) {  %>
	
  	<tr> 
    	<td colspan="8" class="largeheader">Add Shipping Information</td>
	</tr>		

	<tr>
		<td class="mediumheader" colspan="2">Carrier:</td>
		<td colspan="2">
			<html:text name="ORDER_OP_ITEM_UPDATE_FORM" property="shippingCarrier" />	
		</td>
		<td class="mediumheader" colspan="2">Ship Method:</td>
		<td colspan="2">
			<html:text name="ORDER_OP_ITEM_UPDATE_FORM" property="shippingMethod" />	
		</td>		
	</tr>
	
	<tr>
		<td class="mediumheader" colspan="2">Ship From Name:</td>
		<td colspan="2">
			<html:text name="ORDER_OP_ITEM_UPDATE_FORM" property="shippingFromName" />	
		</td>
		<td class="mediumheader" colspan="2">Vendor Shipment Id:</td>
		<td colspan="2">
			<html:text name="ORDER_OP_ITEM_UPDATE_FORM" property="vendorShipmentId" />	
		</td>		
	</tr>
	
	<tr>
		<td class="mediumheader" colspan="2">Ship From City:</td>
		<td colspan="2">
			<html:text name="ORDER_OP_ITEM_UPDATE_FORM" property="shippingFromCity" />	
		</td>
		<td class="mediumheader" colspan="2">Tracking Type:</td>
		<td colspan="2">
			<html:text name="ORDER_OP_ITEM_UPDATE_FORM" property="trackingType" />	
		</td>		
	</tr>
	
	<tr>
		<td class="mediumheader" colspan="2">Ship From State:</td>
		<td colspan="2">
			<html:text name="ORDER_OP_ITEM_UPDATE_FORM" property="shippingFromState" />	
		</td>
		<td class="mediumheader" colspan="2">Tracking Number:</td>
		<td colspan="2">
			<html:text name="ORDER_OP_ITEM_UPDATE_FORM" property="trackingNumber" />	
		</td>		
	</tr>
	
	<tr>
		<td class="mediumheader" colspan="2">Ship From Zip:</td>
		<td colspan="2">
			<html:text name="ORDER_OP_ITEM_UPDATE_FORM" property="shippingFromZip" />	
		</td>
		<td class="mediumheader" colspan="2">&nbsp;</td>
		<td colspan="2">&nbsp;</td>		
	</tr>		
	
	<% } else if ("Invoiced".equals(detailaction)) {  %>

  	<tr> 
    	<td colspan="8" class="largeheader">Invoice Information</td>
	</tr>		
	
	<tr>
		<td class="mediumheader" colspan="2">Invoice Number:</td>
		<td colspan="2">
			<bean:define id="invoicelist" name="ORDER_OP_ITEM_UPDATE_FORM" property="orderItemStatusDesc.invoiceList" />
			<html:hidden property="changeInvoice" value="" />
			<html:select name="ORDER_OP_ITEM_UPDATE_FORM" property="invoiceId" onchange="document.forms[0].changeInvoice.value='invoice';">
				<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
				<html:option value="0">Create New</html:option>
				<html:options collection="invoicelist" property="invoiceId" labelProperty="invoiceNumber"/>
			</html:select>
		</td>
		<td class="mediumheader" colspan="4">&nbsp;</td>
	</tr>		
	
	
	<% }  %>
		
		
    <tr> 
    	<td colspan="8" align="center"> 
			<html:reset>
        		<app:storeMessage  key="admin.button.reset"/>
      		</html:reset>
			<html:submit property="action"><app:storeMessage  key="global.action.label.save"/></html:submit>
       </td>
    </tr>
	
	
</html:form>	
</table>

</div>

<jsp:include flush='true' page="ui/consoleFooter.jsp"/>

</body>
</html:html>
