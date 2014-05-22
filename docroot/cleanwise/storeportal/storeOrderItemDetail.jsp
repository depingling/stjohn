<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="java.util.Locale" %>
<%@ page import="com.cleanwise.service.api.value.InvoiceDistDetailData" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<app:checkLogon/>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<html:html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="../externals/styles.css">
</head>

<body bgcolor="#FFFFFF">

<div class="text">
<font color=red>
<html:errors/>
</font>

<table ID=1094 border="0" cellpadding="1" cellspacing="0" width="769" class="mainbody">
<html:form styleId="1095" name="STORE_ORDER_ITEM_DETAIL_FORM" action="/storeportal/storeOrderItemDetail.do"
	type="com.cleanwise.view.forms.StoreOrderItemDetailForm">


	<tr>
		<td colspan="6">
<!-- the orderItem information -->
<table ID=1096 width="769" border="0" class="results">
  	<tr>
    	<td colspan="6" class="mediumheader">Order Item Information</td>
	</tr>
	<tr>
		<td><b>Line #:</b></td>
		<td><bean:write name="STORE_ORDER_ITEM_DETAIL_FORM" property="orderItemDesc.orderItem.orderLineNum" filter="true"/></td>
		<td><b>CW SKU #:</b></td>
		<td><bean:write name="STORE_ORDER_ITEM_DETAIL_FORM" property="orderItemDesc.orderItem.itemSkuNum" filter="true"/></td>
		<td><b>Distributor Sku #:</b></td>
		<td><bean:write name="STORE_ORDER_ITEM_DETAIL_FORM" property="orderItemDesc.orderItem.distItemSkuNum" filter="true"/></td>
		<td><b>Manufacturer Sku #:</b></td>
		<td><bean:write name="STORE_ORDER_ITEM_DETAIL_FORM" property="orderItemDesc.orderItem.manuItemSkuNum" filter="true"/></td>
	</tr>
	<tr>
		<td><b>Dist Order #:</b></td>
		<td><bean:write name="STORE_ORDER_ITEM_DETAIL_FORM" property="orderItemDesc.orderItem.distOrderNum" filter="true"/></td>
		<td><b>Product Name:</b></td>
		<td><bean:write name="STORE_ORDER_ITEM_DETAIL_FORM" property="orderItemDesc.orderItem.itemShortDesc" filter="true"/></td>
		<td><b>Customer Price:</b></td>
		<td><bean:write name="STORE_ORDER_ITEM_DETAIL_FORM" property="orderItemDesc.orderItem.custContractPrice" filter="true"/></td>
		<td><b>Manufacturer:</b></td>
		<td><bean:write name="STORE_ORDER_ITEM_DETAIL_FORM" property="orderItemDesc.orderItem.manuItemShortDesc" filter="true"/></td>
	</tr>
	<tr>
		<td><b>ERP Order #:</b></td>
		<td><bean:write name="STORE_ORDER_ITEM_DETAIL_FORM" property="orderItemDesc.orderItem.erpOrderNum" filter="true"/></td>
		<td><b>Item Size:</b></td>
		<td><bean:write name="STORE_ORDER_ITEM_DETAIL_FORM" property="orderItemDesc.orderItem.itemSize" filter="true"/></td>
		<td><b>CW Cost:</b></td>
		<td><bean:write name="STORE_ORDER_ITEM_DETAIL_FORM" property="orderItemDesc.orderItem.distItemCost" filter="true"/></td>
	</tr>
	<tr>
		<td><b>Outbound PO #:</b></td>
		<td>
            <logic:present  name="STORE_ORDER_ITEM_DETAIL_FORM" property="orderItemDesc.orderItem.outboundPoNum">
                <bean:write  name="STORE_ORDER_ITEM_DETAIL_FORM" property="orderItemDesc.orderItem.outboundPoNum" filter="true"/>
            </logic:present>
            <logic:notPresent   name="STORE_ORDER_ITEM_DETAIL_FORM" property="orderItemDesc.orderItem.outboundPoNum">
                N/A
            </logic:notPresent>
        </td>
		<td><b>UOM:</b></td>
		<td><bean:write name="STORE_ORDER_ITEM_DETAIL_FORM" property="orderItemDesc.orderItem.itemUom" filter="true"/></td>
		<td><b>Quantity:</b></td>
		<td><bean:write name="STORE_ORDER_ITEM_DETAIL_FORM" property="orderItemDesc.orderItem.totalQuantityOrdered" filter="true"/></td>
	</tr>
	<tr>
		<td><b>Distributor Name:</b></td>
		<td><bean:write name="STORE_ORDER_ITEM_DETAIL_FORM" property="orderItemDesc.distName" filter="true"/></td>
		<td><b>Pack:</b></td>
		<td><bean:write name="STORE_ORDER_ITEM_DETAIL_FORM" property="orderItemDesc.orderItem.itemPack" filter="true"/></td>
		<td colspan="2">&nbsp;</td>
	</tr>
</table>
		</td>
	</tr>

	<tr>
		<td colspan="6">&nbsp;</td>
	</tr>

	<tr>
		<td colspan="6">
<!-- the OrderItemAction Info -->
<table ID=1097 width="769" border="0" class="results">
<tr>
<td colspan="8"><span class="mediumheader"><b>Order Item Actions:</b></span>
<bean:size id="itemCount" name="STORE_ORDER_ITEM_DETAIL_FORM" property="orderItemDesc.orderItemActionList" />
<bean:write name="itemCount" />
</td>
</tr>

<logic:present name="STORE_ORDER_ITEM_DETAIL_FORM" property="orderItemDesc.orderItemActionList">
<tr>
<td><b>Action ID</b></td>
<td class="resultscolumna"><b>Affected Line #</b></td>
<td><b>Affected Order #</b></td>
<td class="resultscolumna"><b>Affected SKU #</b></td>
<td><b>Amount</b></td>
<td class="resultscolumna"><b>Quantity</b></td>
<td><b>Action CD</b></td>
<td class="resultscolumna"><a ID=1098 href="storeOrderItemDetail.do?action=sortitemdetails&sortField=date"><b>Action Date</b></a></td>
</tr>

<logic:iterate id="itemele" indexId="i" name="STORE_ORDER_ITEM_DETAIL_FORM" property="orderItemDesc.orderItemActionList" scope="session" type="com.cleanwise.service.api.value.OrderItemActionData">
<tr>
<td><bean:write name="itemele" property="orderItemActionId"/>&nbsp;</td>
<td class="resultscolumna"><bean:write name="itemele" property="affectedLineItem"/>&nbsp;</td>
<td><bean:write name="itemele" property="affectedOrderNum"/>&nbsp;</td>
<td class="resultscolumna"><bean:write name="itemele" property="affectedSku"/>&nbsp;</td>
<td><bean:write name="itemele" property="amount"/>&nbsp;</td>
<td class="resultscolumna"><bean:write name="itemele" property="quantity"/>&nbsp;</td>
<td><bean:write name="itemele" property="actionCd"/>&nbsp;</td>
<logic:present name="itemele" property="actionDate">
<td class="resultscolumna">
	<bean:define id="actiondate"  name="itemele" property="actionDate"/>
    <i18n:formatDate value="<%=actiondate%>" pattern="MM/dd/yyyy" locale="<%=Locale.US%>"/>&nbsp;
</td>
</logic:present>
<logic:notPresent name="itemele" property="actionDate">
<td class="resultscolumna">&nbsp;</td>
</logic:notPresent>
</tr>
</logic:iterate>

</logic:present>
</table>
		</td>
	</tr>

	<tr>
		<td colspan="6">&nbsp;</td>
	</tr>

	<tr>
		<td colspan="6">
<!-- the OrderItemSubstitution Info -->
<table ID=1099 width="769" border="0" class="results">
<tr>
<td colspan="9"><span class="mediumheader"><b>Order Item Substitutions:</b></span>
<bean:size id="subCount" name="STORE_ORDER_ITEM_DETAIL_FORM" property="orderItemDesc.orderItemSubstitutionList" />
<bean:write name="subCount" />
</td>
</tr>

<logic:present name="STORE_ORDER_ITEM_DETAIL_FORM" property="orderItemDesc.orderItemSubstitutionList">
<tr>
<td><b>Item Sub. ID</b></td>
<td class="resultscolumna"><b>SKU #</b></td>
<td><b>Distributor SKU #</b></td>
<td class="resultscolumna"><b>Manufactuer SKU #</b></td>
<td><b>Product Name</b></td>
<td class="resultscolumna"><b>UOM</b></td>
<td><b>Pack</b></td>
<td class="resultscolumna"><b>Quantity</b></td>
<td><b>Distributor Cost</b></td>
</tr>

<logic:iterate id="subele" indexId="j" name="STORE_ORDER_ITEM_DETAIL_FORM" property="orderItemDesc.orderItemSubstitutionList" scope="session" type="com.cleanwise.service.api.value.ItemSubstitutionData">
<tr>
<td><bean:write name="subele" property="itemSubstitutionId"/>&nbsp;</td>
<td class="resultscolumna"><bean:write name="subele" property="itemSkuNum"/>&nbsp;</td>
<td><bean:write name="subele" property="distItemSkuNum"/>&nbsp;</td>
<td class="resultscolumna"><bean:write name="subele" property="manuItemSkuNum"/>&nbsp;</td>
<td><bean:write name="subele" property="itemShortDesc"/>&nbsp;</td>
<td class="resultscolumna"><bean:write name="subele" property="itemUom"/>&nbsp;</td>
<td><bean:write name="subele" property="itemPack"/>&nbsp;</td>
<td class="resultscolumna"><bean:write name="subele" property="itemQuantity"/></td>
<td><bean:write name="subele" property="itemDistCost"/>&nbsp;</td>
</tr>
</logic:iterate>

</logic:present>
</table>
		</td>
	</tr>


	<tr>
		<td colspan="6">&nbsp;</td>
	</tr>

	<tr>
		<td colspan="6">
<!-- the shippingHistory Info -->
<table ID=1100 width="769" border="0" class="results">
<tr>
<td colspan="6"><span class="mediumheader"><b>Shipping History:</b></span>
</td>
</tr>

<logic:present name="STORE_ORDER_ITEM_DETAIL_FORM" property="invoiceDistList">
<bean:define id="invDistDetailList" name="STORE_ORDER_ITEM_DETAIL_FORM" property="orderItemDesc.invoiceDistDetailList" type="com.cleanwise.service.api.value.InvoiceDistDetailDataVector" />
<logic:iterate id="invdistele" indexId="k" name="STORE_ORDER_ITEM_DETAIL_FORM" property="invoiceDistList" scope="session" type="com.cleanwise.service.api.value.InvoiceDistData">
	<bean:define id="invDistId" name="invdistele" property="invoiceDistId" type="java.lang.Integer" />

    <%  String itemSku = new String("");
		String distItemSku = new String("");
		String itemName = new String("");
		String itemUom = new String("");
		String itemPack = new String("");
		String itemQuantity = new String("");
		String lineTotal = new String("");
   		if ( null != invDistDetailList ) {
	   		for (int i = 0; i < invDistDetailList.size(); i++) {
				InvoiceDistDetailData invDistDetailD = (InvoiceDistDetailData)invDistDetailList.get(i);
				if(	invDistDetailD.getInvoiceDistId() == invDistId.intValue()) {
					itemSku = String.valueOf(invDistDetailD.getItemSkuNum());
					distItemSku = invDistDetailD.getDistItemSkuNum();
					itemName = invDistDetailD.getItemShortDesc();
					itemUom = invDistDetailD.getItemUom();
					itemPack = invDistDetailD.getItemPack();
					itemQuantity = String.valueOf(invDistDetailD.getItemQuantity());
					if ( null != invDistDetailD.getLineTotal() ) {
						lineTotal = invDistDetailD.getLineTotal().toString();
					}
					break;
				}
			}
		}
	%>

	<tr><td colspan="6"><hr></td></tr>

	<tr>
		<td><b>Invoice #:</b></td>
		<td><bean:write name="invdistele" property="invoiceNum" filter="true"/></td>
		<td><b>Invoice Date:</b></td>
			<logic:present name="invdistele" property="invoiceDate">
			<td>
			<bean:define id="date" name="invdistele" property="invoiceDate"/>
   			<i18n:formatDate value="<%=date%>" pattern="MM/dd/yyyy" locale="<%=Locale.US%>"/>
			</td>
			</logic:present>
			<logic:notPresent name="invdistele" property="invoiceDate">
			<td>&nbsp;</td>
			</logic:notPresent>
		<td><b>Ship From Address:</b></td>
		<td><bean:write name="invdistele" property="shipFromAddress1" filter="true"/></td>
	</tr>

	<tr>
		<td><b>Dist Order #:</b></td>
		<td><bean:write name="invdistele" property="distOrderNum" filter="true"/></td>
		<td><b>Carrier:</b></td>
		<td><bean:write name="invdistele" property="carrier" filter="true"/></td>
		<td>&nbsp;</td>
		<td><bean:write name="invdistele" property="shipFromAddress2" filter="true"/></td>
	</tr>

	<tr>
		<td><b>Tracking Type:</b></td>
		<td><bean:write name="invdistele" property="trackingType" filter="true"/></td>
		<td><b>Tracking #:</b></td>
		<td><bean:write name="invdistele" property="trackingNum" filter="true"/></td>
		<td>&nbsp;</td>
		<td><bean:write name="invdistele" property="shipFromAddress3" filter="true"/></td>
	</tr>

	<tr>
		<td><b>Item Sku:</b></td>
		<td><%=itemSku%></td>
		<td><b>Dist Item Sku:</b></td>
		<td><%=distItemSku%></td>
		<td>&nbsp;</td>
		<td><bean:write name="invdistele" property="shipFromAddress4" filter="true"/></td>
	</tr>

	<tr>
		<td><b>Product Name:</b></td>
		<td><%=itemName%></td>
		<td><b>Item UOM:</b></td>
		<td><%=itemUom%></td>
		<td><b>Ship From City:</b></td>
		<td><bean:write name="invdistele" property="shipFromCity" filter="true"/></td>
	</tr>

	<tr>
		<td colspan="2">&nbsp;</td>
		<td><b>Item Pack:</b></td>
		<td><%=itemPack%></td>
		<td><b>Ship From State:</b></td>
		<td><bean:write name="invdistele" property="shipFromState" filter="true"/></td>
	</tr>

	<tr>
		<td><b>Shipped Quantity:</b></td>
		<td><%=itemQuantity%></td>
		<td><b>Line Total:</b></td>
		<td><%=lineTotal%></td>
		<td><b>Ship From Postal Code:</b></td>
		<td><bean:write name="invdistele" property="shipFromPostalCode" filter="true"/></td>
	</tr>

</logic:iterate>
</logic:present>
</table>

	<tr>
		<td colspan="6">&nbsp;</td>
	</tr>

	<tr>
		<td colspan="6">
			<table ID=1101 width="769" border="0" class="mainbody">
				<tr>
					<td colspan="6"><span class="mediumheader"><b>Cumulative Summary:</b></span>
					</td>
				</tr>
				<tr>
					<td><b>Date</b></td>
					<td><b>Accepted</b></td>
					<td><b>Substituted</b></td>
					<td><b>Shipped</b></td>
					<td><b>Backordered</b></td>
					<td><b>Invoiced</b></td>
				</tr>
				<tr>
					<td>
					</td>
					<td><bean:write name="STORE_ORDER_ITEM_DETAIL_FORM" property="acceptedNum"/>&nbsp;</td>
					<td><bean:write name="STORE_ORDER_ITEM_DETAIL_FORM" property="substitutedNum"/>&nbsp;</td>
					<td><bean:write name="STORE_ORDER_ITEM_DETAIL_FORM" property="shippedNum"/>&nbsp;</td>
					<td><bean:write name="STORE_ORDER_ITEM_DETAIL_FORM" property="backorderedNum"/>&nbsp;</td>
					<td><bean:write name="STORE_ORDER_ITEM_DETAIL_FORM" property="invoicedNum"/>&nbsp;</td>
				</tr>
			</table>
		</td>
	</tr>


    <tr>
    	<td colspan="6" align="center">
          <html:button onclick="document.forms[0].submit();" property="buttonBack">
			<app:storeMessage  key="admin.button.back"/>
          </html:button>
       </td>
    </tr>


</html:form>
</table>

</div>

</body>
</html:html>
