<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="java.util.Locale" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>
<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:size id="itemListSize" name="INVOICE_OP_DETAIL_FORM" property="orderItemDescList" />
<%int tabIndex=6;%>
<script language="JavaScript1.2">
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

<tr><td colspan="18">&nbsp;</td></tr>
<tr><td colspan="18">
        <table align="center">
                <tr>
                        <td colspan="2" class="mediumheader"><b>Cumulative Summary:</b></td>
                        <td class="resultscolumna"><b>Date</b></td>
                        <td><b>Ordered</b></td>
                        <td class="resultscolumna"><b>Accepted</b></td>
                        <td><b>Shipped</b></td>
                        <td class="resultscolumna"><b>Backordered</b></td>
                        <td><b>Substituted</b></td>
                        <td class="resultscolumna"><b>Invoiced</b></td>
                        <td><b>Returned</b></td>
                </tr>
                <tr>
                        <td colspan="2">&nbsp;</td>
                        <td align="center" class="resultscolumna">
                        <logic:present name="INVOICE_OP_DETAIL_FORM" property="lastDate" >
                                <bean:define id="lastdate" name="INVOICE_OP_DETAIL_FORM" property="lastDate"/>
                                <i18n:formatDate value="<%=lastdate%>" pattern="MM/dd/yyyy" locale="<%=Locale.US%>"/>
                        </logic:present>
                        </td>
                        <td align="center"><bean:write name="INVOICE_OP_DETAIL_FORM" property="orderedNum"/>&nbsp;</td>
                        <td align="center" class="resultscolumna"><bean:write name="INVOICE_OP_DETAIL_FORM" property="acceptedNum"/>&nbsp;</td>
                        <td align="center"><bean:write name="INVOICE_OP_DETAIL_FORM" property="shippedNum"/>&nbsp;</td>
                        <td align="center" class="resultscolumna"><bean:write name="INVOICE_OP_DETAIL_FORM" property="backorderedNum"/>&nbsp;</td>
                        <td align="center"><bean:write name="INVOICE_OP_DETAIL_FORM" property="substitutedNum"/>&nbsp;</td>
                        <td align="center" class="resultscolumna"><bean:write name="INVOICE_OP_DETAIL_FORM" property="invoicedNum"/>&nbsp;</td>
                        <td align="center"><bean:write name="INVOICE_OP_DETAIL_FORM" property="returnedNum"/>&nbsp;</td>
                </tr>
        </table>
</td></tr>

<tr><td colspan="18" class="mainbody">&nbsp;</td></tr>

<tr><td colspan="18">
        <table>
                <tr>
                        <td><b>Invoice Date:</b></td>
                        <td><html:text size="10" maxlength="15" name="INVOICE_OP_DETAIL_FORM" property="invoiceDateS" tabindex="<%=Integer.toString(tabIndex++)%>"/>
                                <span class="reqind" valign="top">*</span>
                        </td>
                        <td><b>Invoice Due Date:</b></td>
                        <td><html:text size="10" maxlength="15" name="INVOICE_OP_DETAIL_FORM" property="invoiceDueDateS"/></td>
                        <td><b>Invoice #:</b></td>
                        <td><html:text size="15" maxlength="20" name="INVOICE_OP_DETAIL_FORM" property="invoiceDist.invoiceNum" tabindex="<%=Integer.toString(tabIndex++)%>"/>
                                <span class="reqind" valign="top">*</span>
                        </td>
                        <td><b>Remit To:</b></td>
                        <td colspan="3">
                            <bean:define id="remitOpt"  name="INVOICE_OP_DETAIL_FORM" property="remitToAddresses"/>
                            <html:select name="INVOICE_OP_DETAIL_FORM" property="remitTo" tabindex="<%=Integer.toString(tabIndex++)%>">
                                <html:option value=""><app:storeMessage  key="admin.select"/></html:option>
                                <html:options  collection="remitOpt" property="name1" labelProperty="name2"/>
                            </html:select>
                       </td>
                        <td><b>Dist Ship From ID:</b></td>
                        <td><html:text size="7" maxlength="20" name="INVOICE_OP_DETAIL_FORM" property="shipFromId" />
                                <bean:define id="distributorId" name="INVOICE_OP_DETAIL_FORM" property="distributorData.busEntity.busEntityId"/>
                                <%
                                        String jsonclick = "popLocateGlobal('../adminportal/distShipFromLocate','getShipFrom&amp;feedField=shipFromId&amp;distributorId="+distributorId+"');";
                                %>
                                <html:button property="action"
                                        onclick="<%=jsonclick%>"
                                        value="Locate Ship From"/>
                                <span class="reqind" valign="top">*</span>
                        </td>
                </tr>
        </table>
</td></tr>

<tr><td colspan="18" class="mainbody">&nbsp;</td></tr>

<tr>
<td><b>Line&nbsp;#</b></td>
<td class="resultscolumna"><b><a href="invoiceOpDetail.do?action=sortitems&sortField=distOrderNum">Dist Order#</a></b></td>
<td><b><a href="invoiceOpDetail.do?action=sortitems&sortField=erpPoNum">
  Outbound PO#</a></b></td>
<td class="resultscolumna"><b><a href="invoiceOpDetail.do?action=sortitems&sortField=cwSKU">CW SKU#</a></b></td>
<td><b><a href="invoiceOpDetail.do?action=sortitems&sortField=distSKU">Dist SKU#</a></b></td>
<td class="resultscolumna"><b>Dist Name</b></td>
<td><b><a href="invoiceOpDetail.do?action=sortitems&sortField=name">Product Name</a></b></td>
<td class="resultscolumna"><b>UOM</b></td>
<td><b>Pack</b></td>
<td class="resultscolumna"><b>Item Size</b></td>
<td><b>Customer Price</b></td>
<td class="resultscolumna"><b>CW Cost</b></td>
<td><b>Qty</b></td>
<td class="resultscolumna"><b>Status</b></td>
<td><b>Item Note</b></td>
<td class="resultscolumna"><b>Add Item Note</b></td>
<td><b>Date</b></td>
<td class="resultscolumna"><b>Line Total</b></td>
</tr>

<logic:iterate id="itemele" indexId="i" name="INVOICE_OP_DETAIL_FORM" property="orderItemDescList" scope="session" type="com.cleanwise.service.api.value.OrderItemDescData">
 <bean:define id="key"  name="itemele" property="orderItem.orderItemId"/>
 <bean:define id="orderedQuantity"  name="itemele" property="orderItem.totalQuantityOrdered" type="java.lang.Integer" />
 <bean:define id="qty" name="itemele" property="orderItem.totalQuantityOrdered" type="Integer"/>
 <%/* get the itemPrice */%>
 <% String linkHref = new String("orderOpItemDetail.do?action=view&id=" + key  + "&fromPage=invoice");
        double itemPrice = 0D;
 %>

 <logic:present name="itemele" property="orderItem.distItemCost" >
        <bean:define id="cwCost"  name="itemele" property="orderItem.distItemCost" type="java.math.BigDecimal" />
         <%/* get the itemPrice */%>
         <% if (null != cwCost) {itemPrice = cwCost.doubleValue();}%>
 </logic:present>

<tr><td colspan="18" class="mainbody"><img src="../<%=ip%>images/cw_descriptionseperator.gif" height="1" width="900"></td></tr>
<tr>
<td><bean:write name="itemele" property="orderItem.erpPoLineNum"/>&nbsp;</td>
<td class="resultscolumna"><bean:write name="itemele" property="orderItem.distOrderNum"/>&nbsp;</td>
<td><bean:write name="itemele" property="orderItem.erpPoNum"/>&nbsp;</td>
<td class="resultscolumna"><bean:write name="itemele" property="orderItem.itemSkuNum"/>&nbsp;</td>
<td><bean:write name="itemele" property="orderItem.distItemSkuNum"/>&nbsp;</td>
<td class="resultscolumna"><bean:write name="itemele" property="distName"/>&nbsp;</td>
<td><a href="<%=linkHref%>"><bean:write name="itemele" property="orderItem.itemShortDesc"/></a>&nbsp;</td>
<td class="resultscolumna"><bean:write name="itemele" property="orderItem.itemUom"/>&nbsp;</td>
<td><bean:write name="itemele" property="orderItem.itemPack"/>&nbsp;</td>
<td class="resultscolumna"><bean:write name="itemele" property="orderItem.itemSize"/>&nbsp;</td>
<td><bean:write name="itemele" property="orderItem.custContractPrice"/>&nbsp;</td>
<td class="resultscolumna"><bean:write name="itemele" property="orderItem.distItemCost"/>&nbsp;</td>
<td><bean:write name="itemele" property="orderItem.totalQuantityOrdered"/>&nbsp;</td>
<td class="resultscolumna">
<logic:present name="itemele" property="orderItem.orderItemStatusCd" >
        <bean:write name="itemele" property="orderItem.orderItemStatusCd"/>&nbsp;
</logic:present>
<logic:notPresent name="itemele" property="orderItem.orderItemStatusCd" >
        Ordered&nbsp;
</logic:notPresent>
</td>
<logic:equal name="itemele" property="hasNote" value="true">
        <td><input type="button" class="smallbutton" onclick="popLinkGlobal('orderOpNote.do?action=view&type=item&itemid=<%=key%>');" value="View"></td>
</logic:equal>
<logic:equal name="itemele" property="hasNote" value="false">
        <td>&nbsp;</td>
</logic:equal>
<td class="resultscolumna"><input type="button" class="smallbutton" onclick="popLinkGlobal('orderOpNote.do?action=add&type=item&itemid=<%=key%>');" value="Add"></td>

<td>&nbsp;</td>
<td class="resultscolumna">&nbsp;</td>
</tr>

<%/*the order item actions*/%>
        <logic:present name="itemele" property="orderItemActionDescList">
        <%
                int shippedQty = 0;
                boolean substitutedFlag = false;
                boolean shippedFlag = false;
        %>

        <bean:size id="detailCount" name="itemele" property="orderItemActionDescList" />
        <logic:greaterThan name="detailCount" value="0">
        <logic:iterate id="detailele" indexId="j" name="itemele" property="orderItemActionDescList" type="com.cleanwise.service.api.value.OrderItemActionDescData">
        <bean:define id="detailkey"  name="detailele" property="orderItemAction.orderItemActionId"/>
        <bean:define id="actionqty" name="detailele" property="orderItemAction.quantity" type="Integer"/>
        <bean:define id="actioncd" name="detailele" property="orderItemAction.actionCd" type="String"/>
        <% String detailLinkHref = new String("orderOpItemUpdate.do?action=edit&id=" + key + "&detailid=" + detailkey);%>
<%/*  get the shipped quantity for this item */%>
        <%
                if ("Substituted".equals(actioncd) || null == actionqty) {
                        substitutedFlag = true;
                }
                else if ("Shipped".equals(actioncd)) {
                        shippedQty += actionqty.intValue();
                        shippedFlag = true;
                }
        %>

        <bean:define id="actionNum" name="detailele" property="orderItemAction.actionCd" type="java.lang.String"/>
        <% 	String itemStatus = new String("");
                itemStatus = actionNum;
        %>

<tr>
<td><!--<bean:write name="itemele" property="orderItem.orderLineNum"/>-->&nbsp;</td>
<td class="resultscolumna">&nbsp;</td>
<td>&nbsp;</td>
<td class="resultscolumna"><bean:write name="detailele" property="orderItemAction.affectedSku"/>&nbsp;</td>
<td><bean:write name="detailele" property="distItemSkuNum"/>&nbsp;</td>
<td class="resultscolumna">&nbsp;</td>
<td><bean:write name="detailele" property="itemShortDesc"/>&nbsp;</td>
<td class="resultscolumna"><bean:write name="detailele" property="itemUom"/>&nbsp;</td>
<td><bean:write name="detailele" property="itemPack"/>&nbsp;</td>
<td class="resultscolumna">&nbsp;</td>
<td>&nbsp;</td>
<td class="resultscolumna"><bean:write name="detailele" property="itemDistCost"/>&nbsp;</td>
<td><bean:write name="detailele" property="orderItemAction.quantity"/>&nbsp;</td>
<td class="resultscolumna"><%=itemStatus%>&nbsp;</td>
<td>&nbsp;</td>
<td class="resultscolumna">&nbsp;</td>

<logic:present name="detailele" property="orderItemAction.actionDate">
<td>
        <bean:define id="date" name="detailele" property="orderItemAction.actionDate"/>
        <i18n:formatDate value="<%=date%>" pattern="MM/dd/yyyy" locale="<%=Locale.US%>"/>
</td>
</logic:present>
<logic:notPresent name="detailele" property="orderItemAction.actionDate">
<td>&nbsp;</td>
</logic:notPresent>

<td class="resultscolumna">&nbsp;</td>

</tr>
        </logic:iterate>
        </logic:greaterThan>

<%  // calc the backordered quantity
        int backorderedQty = 0;
        if (true == shippedFlag && false == substitutedFlag) {
                backorderedQty = qty.intValue() - shippedQty;
                if ( 0 < backorderedQty ) {
%>
<tr>
<td><bean:write name="itemele" property="orderItem.orderLineNum"/>&nbsp;</td>
<td class="resultscolumna">&nbsp;</td>
<td>&nbsp;</td>
<td class="resultscolumna"><bean:write name="itemele" property="orderItem.itemSkuNum"/>&nbsp;</td>
<td>&nbsp;</td>
<td class="resultscolumna">&nbsp;</td>
<td>&nbsp;</td>
<td class="resultscolumna">&nbsp;</td>
<td>&nbsp;</td>
<td class="resultscolumna">&nbsp;</td>
<td>&nbsp;</td>
<td class="resultscolumna">&nbsp;</td>
<td><%=backorderedQty%>&nbsp;</td>
<td class="resultscolumna">Backordered&nbsp;</td>
<td>&nbsp;</td>
<td class="resultscolumna">&nbsp;</td>
<td>&nbsp;</td>
<%     }
}
%>

        </logic:present>
<%/* end of the order item actions*/%>

<%/* To see if the item quantity is fullfilled*/%>
<%
        int totalQuantity = 0;
        if (null != orderedQuantity) {
                totalQuantity = orderedQuantity.intValue();
        }
        int totalInvoicedQuantity = 0;
%>
        <logic:present name="itemele" property="invoiceDistDetailList">
        <logic:iterate id="invoiceele" indexId="m" name="itemele" property="invoiceDistDetailList" type="com.cleanwise.service.api.value.InvoiceDistDetailData">
        <bean:define id="invoiceDetailKey"  name="invoiceele" property="invoiceDistDetailId"/>
        <bean:define id="itemQuantity"  name="invoiceele" property="itemQuantity" type="java.lang.Integer"/>
 <%
        int invoicedQuantity = 0;
        if (null != itemQuantity) {
                invoicedQuantity = itemQuantity.intValue();
        }
        totalInvoicedQuantity += invoicedQuantity;
%>
        </logic:iterate>
        </logic:present>

<% if (totalQuantity > totalInvoicedQuantity ) { %>
<tr>
<td><html:text styleClass="text" size="3" maxlength="6" property='<%= "orderItemDesc[" + i + "].distLineNumS" %>'/>&nbsp;</td>
<td class="resultscolumna">&nbsp;</td>
<td>&nbsp;</td>
<td class="resultscolumna"><html:text styleClass="text" size="5" maxlength="16" property='<%= "orderItemDesc[" + i + "].itemSkuNumS" %>'/>&nbsp;</td>
<td><html:text styleClass="text" size="7" maxlength="16" property='<%= "orderItemDesc[" + i + "].newInvoiceDistDetail.distItemSkuNum" %>' />&nbsp;</td>
<td class="resultscolumna">&nbsp;</td>
<td><html:text styleClass="text" size="15" maxlength="40" property='<%= "orderItemDesc[" + i + "].newInvoiceDistDetail.itemShortDesc" %>'/>&nbsp;</td>
<td class="resultscolumna"><html:text styleClass="text" size="3" maxlength="6" property='<%= "orderItemDesc[" + i + "].newInvoiceDistDetail.itemUom" %>'/>&nbsp;</td>
<td><html:text styleClass="text" size="3" maxlength="6" property='<%= "orderItemDesc[" + i + "].newInvoiceDistDetail.itemPack" %>'/>&nbsp;</td>
<td class="resultscolumna">&nbsp;</td>
<td>&nbsp;</td>
<% String script = "return calcLineTotal('" + i + "');"; %>
<td class="resultscolumna"><html:text styleClass="text" size="5" maxlength="8" property='<%= "orderItemDesc[" + i + "].cwCostS" %>' onchange="<%=script%>" />&nbsp;
        <html:hidden property='<%= "orderItemDesc[" + i + "].itemPriceS" %>' value="<%=String.valueOf(itemPrice)%>" />
</td>
<td><html:text styleClass="text" size="3" maxlength="5" property='<%= "orderItemDesc[" + i + "].itemQuantityS" %>' onchange="<%=script%>" tabindex="<%=Integer.toString(tabIndex++)%>"/>&nbsp;<span class="reqind" valign="top">*</span></td>
<td class="resultscolumna">
        Unapproved Qty:<%=totalQuantity - totalInvoicedQuantity%>&nbsp;
</td>
<td>&nbsp;</td>
<td class="resultscolumna">&nbsp;</td>
<td>&nbsp;</td>
<td class="resultscolumna"><html:text styleClass="textreadonly" size="9" maxlength="9" property='<%= "orderItemDesc[" + i + "].lineTotalS" %>' readonly="true"/>&nbsp;</td>
</tr>
<% } else { %>
<html:hidden property='<%= "orderItemDesc[" + i + "].itemPriceS" %>'/>
<html:hidden property='<%= "orderItemDesc[" + i + "].cwCostS" %>'/>
<html:hidden property='<%= "orderItemDesc[" + i + "].itemQuantityS" %>'/>
<html:hidden property='<%= "orderItemDesc[" + i + "].lineTotalS" %>'/>
<tr>
<td>&nbsp;</td><td class="resultscolumna">&nbsp;</td><td>&nbsp;</td><td class="resultscolumna">&nbsp;</td><td>&nbsp;</td>
<td class="resultscolumna">&nbsp;</td><td>&nbsp;</td><td class="resultscolumna">&nbsp;</td>
<td>&nbsp;</td><td class="resultscolumna">&nbsp;</td><td>&nbsp;</td><td class="resultscolumna">&nbsp;</td><td>&nbsp;</td>
<td class="resultscolumna">
        Unapproved Qty:<%=totalQuantity - totalInvoicedQuantity%>
</td>
<td>&nbsp;</td>
<td class="resultscolumna">&nbsp;</td>
<td>&nbsp;</td>
<td class="resultscolumna">&nbsp;</td>
</tr>
<% }  %>

<%--Display exisintg invoices for this item if any exist--%>
<logic:present name="itemele" property="invoiceDistDataVector">
        <logic:iterate id="distInvoice" name="itemele" property="invoiceDistDataVector" type="com.cleanwise.service.api.value.InvoiceDistData">
                <tr>
                        <td>&nbsp;</td>
                        <td class="resultscolumna">&nbsp;</td>
                        <td>&nbsp;</td>
                        <td class="resultscolumna">&nbsp;</td>
                        <td colspan=2>Existing Invoice:</td>
                        <td colspan=2>
                                <bean:write name="distInvoice" property="invoiceNum"/>&nbsp;
                        </td>
                        <td>&nbsp;</td>
                        <td class="resultscolumna">&nbsp;</td>
                        <td>&nbsp;</td>
                        <td class="resultscolumna">&nbsp;</td>
                        <td>&nbsp;</td>
                        <td class="resultscolumna">&nbsp;</td>
                        <td>&nbsp;</td>
                        <td class="resultscolumna">&nbsp;</td>
                        <td>&nbsp;</td>
                        <td class="resultscolumna">&nbsp;</td>
                </tr>
        </logic:iterate>
</logic:present>
<%--End Display exisintg invoices for this item if any exist--%>
</logic:iterate>
