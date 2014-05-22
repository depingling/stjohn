<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.SessionTool" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.util.Utility"%>
<%@ page import="java.util.Locale" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.view.forms.StoreVendorInvoiceDetailForm" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>
<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="po" name="STORE_VEN_INVOICE_DETAIL_FORM" property="invoice" type="com.cleanwise.service.api.value.PurchaseOrderStatusDescDataView"/>
<bean:size id="itemListSize" name="STORE_VEN_INVOICE_DETAIL_FORM" property="invoiceItems" />
<bean:define id="theForm" name="STORE_VEN_INVOICE_DETAIL_FORM" type="com.cleanwise.view.forms.StoreVendorInvoiceDetailForm"/>
<%
    SessionTool st = new SessionTool(request);
    java.util.Locale locale = st.getUserLocaleCode(request);
    CleanwiseUser user = st.getUserData();
    boolean isDoNotAllowInvoiceEdits = ((StoreVendorInvoiceDetailForm) theForm).isDoNotAllowInvoiceEdits() && !((StoreVendorInvoiceDetailForm) theForm).isPoScreen();
    boolean isUserDoNotAllowInvoiceEdits = !(po != null && po.getInvoiceDist() != null && Utility.isSet(((InvoiceDistData) po.getInvoiceDist()).getModBy()) && ((InvoiceDistData) po.getInvoiceDist()).getModBy().equals(user.getUserName()));

%>
<%int tabIndex=6;%>
<script language="JavaScript1.2">
function deleteLine(lineId){

	document.forms[0].actionOveride.value = "delLine";
	document.forms[0].lineToDelete.value=lineId;
	document.forms[0].submit();
	//return 1;
	//invoicesVendorDetail.do?action=delLine&idx=lineId;
}

function popConfirmFreight(distMinOrd,poTotal) {
  if (document.forms[0].elements["totalFreightCostS"].value != "" && document.forms[0].elements["totalFreightCostS"].value != "0"){
        if((poTotal * 1.0) > (distMinOrd * 1.0)){
                return confirm('The total purchase order amount (' + poTotal + ') is greater than the configured minimum order for this distributor (' + distMinOrd + ').  Are you sure you want to approve this freight charge?');
        }
  }
  return true;
}

function calcTotal() {
  var calcTotal = 0.0;
  var contractTotal = 0.0;
  var invoiceSubTotal = 0.0;
  var contractSubTotal = 0.0;

<% for (int i = 0; i < itemListSize.intValue(); i++) {  %>
  if (document.forms[0].elements["orderItemDesc[<%=i%>].lineTotalS"].value != "") {
        invoiceSubTotal += document.forms[0].elements["orderItemDesc[<%=i%>].lineTotalS"].value * 1.0;
  }
  if (document.forms[0].elements["orderItemDesc[<%=i%>].itemQuantityS"].value != "" &&
          document.forms[0].elements["orderItemDesc[<%=i%>].itemPriceS"].value != "") {
        contractSubTotal += document.forms[0].elements["orderItemDesc[<%=i%>].itemQuantityS"].value * document.forms[0].elements["orderItemDesc[<%=i%>].itemPriceS"].value * 1.0;
  }
<% }  %>

  if ( 0.0 != invoiceSubTotal )  {
        document.forms[0].elements["invoiceSubTotalS"].value = invoiceSubTotal;
  }
  else {
        document.forms[0].elements["invoiceSubTotalS"].value = "";
  }

  if ( 0.0 != contractSubTotal )  {
        document.forms[0].elements["contractSubTotalS"].value = contractSubTotal;
  }
  else {
        document.forms[0].elements["contractSubTotalS"].value = "";
  }

  calcTotal += invoiceSubTotal * 1.0;
  contractTotal += contractSubTotal * 1.0;
  if (document.forms[0].elements["totalFreightCostS"].value != "") {
        calcTotal += document.forms[0].elements["totalFreightCostS"].value * 1.0;
        contractTotal += document.forms[0].elements["totalFreightCostS"].value * 1.0;
  }
  if (document.forms[0].elements["totalMiscChargesS"].value != "") {
        calcTotal += document.forms[0].elements["totalMiscChargesS"].value * 1.0;
        contractTotal += document.forms[0].elements["totalMiscChargesS"].value * 1.0;
  }
  if (document.forms[0].elements["totalTaxCostS"].value != "") {
        calcTotal += document.forms[0].elements["totalTaxCostS"].value * 1.0;
        contractTotal += document.forms[0].elements["totalTaxCostS"].value * 1.0;
  }

  if ( 0.0 != calcTotal )  {
        document.forms[0].elements["invoiceTotalS"].value = calcTotal;
  }
  else {
        document.forms[0].elements["invoiceTotalS"].value = "";
  }

  if ( 0.0 != contractTotal )  {
        document.forms[0].elements["contractTotalS"].value = contractTotal;
  }
  else {
        document.forms[0].elements["contractTotalS"].value = "";
  }

  return true;
}


function calcLineTotal(ind) {
  var itemPrice = 0.0;
  var cwPrice = 0.0;
  var quantity = 0.0;
  var lineTotal = 0.0

  if (document.forms[0].elements["orderItemDesc[" + ind + "].itemPriceS"].value != "") {
        itemPrice = document.forms[0].elements["orderItemDesc[" + ind + "].itemPriceS"].value;
  }
  if (document.forms[0].elements["orderItemDesc[" + ind + "].cwCostS"].value != "") {
        cwPrice = document.forms[0].elements["orderItemDesc[" + ind + "].cwCostS"].value;
  }
  if (document.forms[0].elements["orderItemDesc[" + ind + "].itemQuantityS"].value != "") {
        quantity = document.forms[0].elements["orderItemDesc[" + ind + "].itemQuantityS"].value;
  }

  if ( 0.0 != quantity )  {
        if (0.0 != cwPrice) {
                lineTotal = cwPrice * quantity * 1.0;
        }
        else {
                lineTotal = itemPrice * quantity * 1.0;
        }
        document.forms[0].elements["orderItemDesc[" + ind + "].lineTotalS"].value = lineTotal;
  }
  else {
        document.forms[0].elements["orderItemDesc[" + ind + "].lineTotalS"].value = "";
  }

  calcTotal();

  return true;
}

</script>
<html:hidden property="actionOveride" value=""/>
<html:hidden property="lineToDelete" value=""/>
<table ID=869 width="<%=Constants.TABLEWIDTH%>" border="0" class="results">
<tr><td><b>Original PO Items</b></td></tr>
<tr>
	<td><b>Line</b></td>
	<td><b>Desc</b></td>
	<td><b>Dist Sku</b></td>
	<td><b>Dist Cost</b></td>
	<td><b>Dist Qty</b></td>
	<td><b>Ord Cost</b></td>
	<td><b>Ord Qty</b></td>
	<td><b>Prev Inv Qty</b></td>
	<td><b>Recvd Qty</b></td>
	<td><b>Taxable</b></td>
</tr>
<logic:iterate id="itemele" indexId="i" name="STORE_VEN_INVOICE_DETAIL_FORM" property="invoiceItems" scope="session" type="com.cleanwise.service.api.value.OrderItemDescData">
	<logic:present name="itemele" property="orderItem.orderItemId">
	<tr>
		<td><logic:present name="itemele" property="orderItem">
				<bean:write name="itemele" property="orderItem.erpPoLineNum"/>
			</logic:present></td>
		<td>
			<logic:present name="itemele" property="orderItem.itemShortDesc">
				<%
					//STJ-3621
					try {
						String itemShortDesc = itemele.getOrderItem().getItemShortDesc();
						if(Utility.isSet(itemShortDesc)) {
							itemele.getOrderItem().setItemShortDesc(itemShortDesc.trim()); 
						}
					}catch(Exception e){
					}
					
				%>
				<bean:write name="itemele" property="orderItem.itemShortDesc"/>
			</logic:present>
		</td>
		<td>
			<logic:present name="itemele" property="orderItem.distItemSkuNum">
				<bean:write name="itemele" property="orderItem.distItemSkuNum"/>
			</logic:present>
		</td>
		<td>
			<logic:present name="itemele" property="orderItem.distUomConvCost">
				<bean:define id="distUomConvCost" name="itemele" property="orderItem.distUomConvCost"/>
				<%=ClwI18nUtil.formatInvoiceCurrency(request, distUomConvCost, po)%>	
			</logic:present>
			<logic:notPresent name="itemele" property="orderItem.distUomConvCost">
				<logic:present name="itemele" property="orderItem.distItemCost">
					<bean:define id="distItemCost" name="itemele" property="orderItem.distItemCost"/>
					<%=ClwI18nUtil.formatInvoiceCurrency(request, distItemCost, po)%>
				</logic:present>
			</logic:notPresent>
		</td>
		<td>
			<logic:present name="itemele" property="orderItem.distItemQuantity">
				<bean:write name="itemele" property="orderItem.distItemQuantity"/>
			</logic:present>
		</td>
		<td>
			<logic:present name="itemele" property="orderItem.distItemCost">
				<bean:define id="distItemCost" name="itemele" property="orderItem.distItemCost"/>
				<%=ClwI18nUtil.formatInvoiceCurrency(request, distItemCost, po)%>	
			</logic:present>
		</td>
		<td>
			<logic:present name="itemele" property="orderItem.totalQuantityOrdered">
				<bean:write name="itemele" property="orderItem.totalQuantityOrdered"/>
			</logic:present>
		</td>
		<td>
			<logic:present name="itemele" property="orderItem.totalQuantityShipped">
				<bean:write name="itemele" property="orderItem.totalQuantityShipped"/>
			</logic:present>
		</td>
		<td>
			<logic:present name="itemele" property="orderItem.totalQuantityReceived">
				<bean:write name="itemele" property="orderItem.totalQuantityReceived"/>
			</logic:present>
		</td>
		<td>
            <%
                if(Utility.isTaxableOrderItem(itemele.getOrderItem())){
            %>
            Y
            <%
            }else{
            %>
            N
            <%
                }
            %>
		</td>
	</tr>
	</logic:present>
</logic:iterate>
</table>
<table ID=870 width="<%=Constants.TABLEWIDTH%>" border="0" class="results">
<thead>
<tr>
<th>&nbsp;</th><%--f_sortTable('itemTblBdy',0,false);--%>
<th><a ID=871 href="#pgsort" class="tableheader" onclick="f_sortTable('itemTblBdy',1,false);">Line&nbsp;#<br>match to sku</a></th>
<th><a ID=872 href="#pgsort" class="tableheader" onclick="f_sortTable('itemTblBdy',2,false);">Dist Sku</a></th>
<th><a ID=873 href="#pgsort" class="tableheader" onclick="f_sortTable('itemTblBdy',3,false);">Sys Sku</a></th>
<th><a ID=874 href="#pgsort" class="tableheader" onclick="f_sortTable('itemTblBdy',4,false);">Itm Desc</a></th>
<th><a ID=875 href="#pgsort" class="tableheader" onclick="f_sortTable('itemTblBdy',5,false);">UOM</a></th>
<th><a ID=876 href="#pgsort" class="tableheader" onclick="f_sortTable('itemTblBdy',6,false);">Pack</a></th>
<th><a ID=877 href="#pgsort" class="tableheader" onclick="f_sortTable('itemTblBdy',7,false);">Status</a></th>
<th><a ID=878 href="#pgsort" class="tableheader" onclick="f_sortTable('itemTblBdy',8,false);">ERP Acct</a></th>
<th><a ID=879 href="#pgsort" class="tableheader" onclick="f_sortTable('itemTblBdy',9,false);">Ord Qty</a></th>
<th><a ID=880 href="#pgsort" class="tableheader" onclick="f_sortTable('itemTblBdy',10,false);">Inv Qty</a></th>
<th><a ID=881 href="#pgsort" class="tableheader" onclick="f_sortTable('itemTblBdy',11,false);">Ord Cost</a></th>
<th><a ID=882 href="#pgsort" class="tableheader" onclick="f_sortTable('itemTblBdy',12,false);">Inv Cost</a></th>
<th><a ID=883 href="#pgsort" class="tableheader" onclick="f_sortTable('itemTblBdy',13,false);">Net</a></th>
<th><a ID=884 href="#pgsort" class="tableheader" onclick="f_sortTable('itemTblBdy',13,false);">Calc Tax</a></th>

</tr>
</thead>
<%String prop; %>
<tbody id="itemTblBdy">
<logic:iterate id="itemele" indexId="i" name="STORE_VEN_INVOICE_DETAIL_FORM" property="invoiceItems" scope="session" type="com.cleanwise.service.api.value.OrderItemDescData">
<logic:present name="itemele" property="workingInvoiceDistDetailData">
	<tr>
		<td><a ID=885 href="javascript:deleteLine('<%=i%>')" alt="delete this invoice line">D</a></td>
		<td>
			<%prop = "invoiceItems["+i+"].orderItemIdS"; %>
			<%if(itemele.getOrderItem() == null || itemele.getOrderItem().getOrderItemId() == 0){%>
				<html:select name="STORE_VEN_INVOICE_DETAIL_FORM" property="<%=prop%>" onchange="document.forms[0].actionOveride.value='reassignLine';document.forms[0].submit();">
					<html:option value="">&nbsp;</html:option>
					<html:optionsCollection  name="store.vendor.invoice.detail.validPoItems"/>
				</html:select>
			<%}else{%>
				Line# <bean:write name="itemele" property="orderItem.erpPoLineNum"/>
			<%}%>
		</td>
		<td>
			<%prop = "invoiceItems["+i+"].workingInvoiceDistDetailData.distItemSkuNum"; %>
            <%if(isDoNotAllowInvoiceEdits) {%>
            <logic:present name="STORE_VEN_INVOICE_DETAIL_FORM" property="<%=prop%>">
                <bean:write name="STORE_VEN_INVOICE_DETAIL_FORM" property="<%=prop%>"/>
            </logic:present>
            <%} else {%>
            <html:text name="STORE_VEN_INVOICE_DETAIL_FORM" property="<%=prop%>" size="10"/>
            <%}%>
        </td>
		<td>
			<logic:present name="itemele" property="orderItem">
				<bean:write name="itemele" property="orderItem.itemSkuNum"/>
			</logic:present>
			<logic:notPresent name="itemele" property="orderItem">
				<logic:present name="itemele" property="workingInvoiceDistDetailData.itemSkuNum">
					<bean:write name="itemele" property="workingInvoiceDistDetailData.itemSkuNum"/>
				</logic:present>
			</logic:notPresent>
		</td>
		<td>
			<%prop = "invoiceItems["+i+"].workingInvoiceDistDetailData.distItemShortDesc"; %>            
            <%OrderItemDescData itemDescData =((OrderItemDescData)((StoreVendorInvoiceDetailForm)theForm).getInvoiceItems().get(i));%>
            <%if((isDoNotAllowInvoiceEdits && isUserDoNotAllowInvoiceEdits) || itemDescData.getOrderItem()== null) {%>
            <logic:present name="STORE_VEN_INVOICE_DETAIL_FORM" property="<%=prop%>">
                <bean:write name="STORE_VEN_INVOICE_DETAIL_FORM" property="<%=prop%>"/>
            </logic:present>
            <%} else {%>
			<%
				//STJ-3621
				String itemDesc="";
				try {
					itemDesc = itemDescData.getOrderItem().getItemShortDesc();
					if(Utility.isSet(itemDesc)){
						itemDesc = itemDesc.trim();
					} else {
						itemDesc = "";
					}
				}catch(Exception e){
				}
					
				%>
            <html:text name="STORE_VEN_INVOICE_DETAIL_FORM" property="<%=prop%>" value="<%=itemDesc %>" />
            <%}%>
		</td>
		<td>
			<%prop = "invoiceItems["+i+"].workingInvoiceDistDetailData.distItemUom"; %>
            <%if(isDoNotAllowInvoiceEdits && isUserDoNotAllowInvoiceEdits) {%>
            <logic:present name="STORE_VEN_INVOICE_DETAIL_FORM" property="<%=prop%>">
                <bean:write name="STORE_VEN_INVOICE_DETAIL_FORM" property="<%=prop%>"/>
            </logic:present>
            <%} else {%>
            <html:text name="STORE_VEN_INVOICE_DETAIL_FORM" property="<%=prop%>" size="2"/>
            <%}%>
		</td>
		<td>
			<%prop = "invoiceItems["+i+"].workingInvoiceDistDetailData.distItemPack";%>
            
            <%if(isDoNotAllowInvoiceEdits  && isUserDoNotAllowInvoiceEdits) {%>
            <logic:present name="STORE_VEN_INVOICE_DETAIL_FORM" property="<%=prop%>">
                <bean:write name="STORE_VEN_INVOICE_DETAIL_FORM" property="<%=prop%>"/>
            </logic:present>
            <%} else {%>
            <html:text name="STORE_VEN_INVOICE_DETAIL_FORM" property="<%=prop%>" size="5"/>
            <%}%>
		</td>
		<td>
		<logic:present name="itemele" property="orderItem">
			<logic:present name="itemele" property="orderItem.orderItemStatusCd" >
				<bean:define id="status" name="itemele" property="orderItem.orderItemStatusCd" type="java.lang.String"/>
				<%=SessionTool.xlateAdminStatus(status,request)%>
			</logic:present>
			<logic:notPresent name="itemele" property="orderItem.orderItemStatusCd" >
					Ordered&nbsp;
			</logic:notPresent>
		</logic:present>
		</td>
		<td>
			<%prop = "invoiceItems["+i+"].workingInvoiceDistDetailData.erpAccountCode";%>
			<table>
				<tr>
					<td>
						<html:text name="STORE_VEN_INVOICE_DETAIL_FORM" property="<%=prop%>" size="5"/>
					</td>
					<td>
						<a href="javascript:launchMenuBox('<%=prop%>')">Show List</a>
					</td>
				</tr>
			</table>

		</td>
		<td>
			<logic:present name="itemele" property="orderItem.totalQuantityOrdered">
				<bean:write name="itemele" property="orderItem.totalQuantityOrdered"/>
			</logic:present>
		</td>
		<td>
            <%prop = "invoiceItems["+i+"].itemQuantityS"; %>
            <%if(itemele.getWorkingInvoiceDistDetailData().getItemQuantity() != itemele.getWorkingInvoiceDistDetailData().getDistItemQtyReceived()){%>
                Recvd: <bean:write name="itemele" property="workingInvoiceDistDetailData.distItemQtyReceived"/><br>
            <%}%>
			<logic:present name="STORE_VEN_INVOICE_DETAIL_FORM" property="<%=prop%>">
                <%if(isDoNotAllowInvoiceEdits) {%>
                <logic:present name="STORE_VEN_INVOICE_DETAIL_FORM" property="<%=prop%>">
                    <bean:write name="STORE_VEN_INVOICE_DETAIL_FORM" property="<%=prop%>"/>
                </logic:present>
                <%} else {%>
                <html:text name="STORE_VEN_INVOICE_DETAIL_FORM" property="<%=prop%>"  size="4" tabindex="<%=Integer.toString(tabIndex++)%>"/>
                <%}%>
			</logic:present>
		</td>
		<td>

		<logic:present name="itemele" property="orderItem.distItemCost">
			<bean:define id="distItemCost" name="itemele" property="orderItem.distItemCost"/>
			<%=ClwI18nUtil.formatInvoiceCurrency(request, distItemCost, po)%>	
		</logic:present>
		</td>
		<td>
		<%-- Deal with the UOM Conversion system --%>
		<logic:present name="itemele" property="orderItem.distUomConvMultiplier">
			<logic:notEqual name="itemele" property="orderItem.distUomConvMultiplier" value="1">
				 UOM Multi:<bean:write name="itemele" property="orderItem.distUomConvMultiplier"/><br>
			</logic:notEqual>
		</logic:present>

		<%
		java.math.BigDecimal recCst = itemele.getWorkingInvoiceDistDetailData().getItemReceivedCost();
		java.math.BigDecimal adjCst = itemele.getWorkingInvoiceDistDetailData().getAdjustedCost();
		if(recCst != null && adjCst != null && recCst.compareTo(adjCst) != 0){%>
			<bean:define id="itemReceivedCost" name="itemele" property="workingInvoiceDistDetailData.itemReceivedCost"/>
			Recvd: 
			<%=ClwI18nUtil.formatInvoiceCurrency(request, itemReceivedCost, po)%>	
			<br>
		<%}%>
		<%prop = "invoiceItems["+i+"].cwCostS"; %>
            <%if(isDoNotAllowInvoiceEdits) {%>
            <logic:present name="STORE_VEN_INVOICE_DETAIL_FORM" property="<%=prop%>">
                <bean:write name="STORE_VEN_INVOICE_DETAIL_FORM" property="<%=prop%>"/>
            </logic:present>
            <%} else {%>
            <html:text name="STORE_VEN_INVOICE_DETAIL_FORM" property="<%=prop%>" size="5" tabindex="<%=Integer.toString(tabIndex++)%>"/>
            <%}%>
	</td>
		<td>
			<%java.math.BigDecimal lineTot = itemele.getActualCost();
			lineTot = lineTot.multiply(new java.math.BigDecimal(itemele.getActualQty()));%>
			<%=ClwI18nUtil.formatInvoiceCurrency(request, lineTot, po)%>	
		</td>
        <td>
            <logic:present name="itemele" property="calculatedSalesTax">
                <bean:define id="itmCalculatedSalesTax" name="itemele" property="calculatedSalesTax"/>
                <%=ClwI18nUtil.formatInvoiceCurrency(request, itmCalculatedSalesTax, po)%>	
            </logic:present>
        </td>
	</tr>
</logic:present><%--WorkingInvoiceDistData--%>
</logic:iterate>

<%-- Now go ahead and display some totals --%>
<tr>
    <td colspan="12">&nbsp;</td>
    <td colspan="2">
        <b>Actual Sub Total:</b>
        <logic:present name="po" property="invoiceDist.subTotal">
            <bean:define id="invSubTotal" name="po" property="invoiceDist.subTotal"/>
            <%=ClwI18nUtil.formatInvoiceCurrency(request, invSubTotal, po)%>	
         </logic:present>
    </td>
</tr>
</tbody>
</table>
