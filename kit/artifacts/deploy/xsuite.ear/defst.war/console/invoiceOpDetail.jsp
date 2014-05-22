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
<app:checkLogon/>
<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="theForm" name="INVOICE_OP_DETAIL_FORM" type="com.cleanwise.view.forms.InvoiceOpDetailForm"/>
<bean:size id="itemListSize" name="INVOICE_OP_DETAIL_FORM" property="orderItemDescList" />

<script language="JavaScript1.2" src="../externals/lib.js">
</script>

<html:html>
<head>
<title>Operations Console Home: Invoice</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="../externals/styles.css">

</head>
<body bgcolor="#FFFFFF">
<% int tabIndex=1; %>
<jsp:include flush='true' page="ui/consoleToolbar.jsp"/>
<jsp:include flush='true' page="ui/loginInfo.jsp"/>
<jsp:include flush='true' page="ui/invoiceOpToolbar.jsp"/>

<div class="text">
<font color=red>
<html:errors/>
</font>

<table border="0" cellpadding="1" cellspacing="0" width="769" class="mainbody">
<html:form name="INVOICE_OP_DETAIL_FORM" action="/console/invoiceOpDetail.do"
        type="com.cleanwise.view.forms.InvoiceOpDetailForm">
        <bean:define id="po" name="INVOICE_OP_DETAIL_FORM" property="purchaseOrderStatusDesc" />
        <tr>
        <td colspan="2" class="mediumheader">Order Header Information</td>
                <td colspan="2" class="mediumheader">Shipping Information</td>
                <td colspan="2" class="mediumheader">&nbsp;</td>
        </tr>
        <tr>
                <td colspan="6"><bean:define id="orderId" name="po" property="orderData.orderId" /></td>
        </tr>
        <tr valign="top">
                <td colspan="2">
                        <table width="100%">
                                <tr valign="top">
                                <td><b>Web Order#:</b></td>
                                <td><bean:write name="po" property="orderData.orderNum" filter="true"/>
                                        </td>
                                </tr>
                                <tr>
                                <td><b>Customer PO#:</b></td>
                                <td><bean:write name="po" property="orderData.requestPoNum" filter="true"/>
                                        </td>
                                </tr>
                                <tr>
                                <td><b>ERP Order#:</b></td>
                                <td><bean:write name="po" property="orderData.erpOrderNum" filter="true"/></td>
                                </tr>
                                <tr>
                                <td><b>Customer Requisition#:</b></td>
                                <td><bean:write name="po" property="orderData.refOrderNum" filter="true"/></td>
                                </tr>
                                <tr>
                                <td><b>Method:</b></td>
                                <td><bean:write name="po" property="orderData.orderSourceCd" filter="true"/></td>
                                </tr><tr></tr>
                                <tr>
                                <td><b>Date Ordered:</b></td>
                                <td>
                                        <logic:present name="PO" property="orderData.originalOrderDate" >
                                                <bean:define id="orderdate" name="po" property="orderData.originalOrderDate" type="java.util.Date"/>
                                        <i18n:formatDate value="<%=orderdate%>" pattern="MM/dd/yyyy" locale="<%=Locale.US%>"/>&nbsp;
                                        </logic:present>
                                        </td>
                                </tr>
                                <tr>
                                <td><b>Order Status:</b></td>
                                <td><bean:write name="po" property="orderData.orderStatusCd" filter="true"/>
                                        </td>
                                </tr>
                        </table>
                </td>

                <td colspan="2">
                        <table width="100%">
                                <tr valign="top">
                                <td><b>Account Name:</b></td>
                                <td><bean:write name="po" property="accountBusEntityData.shortDesc" filter="true"/>
                                </td>
                                </tr>
                                <tr>
                                <td><b>Site Name:</b></td>
                                <td><bean:write name="po" property="orderData.orderSiteName" filter="true"/>
                                        </td>
                                </tr>
                                <tr>
                                <td><b>Shipping Address:</b></td>
                                <td><bean:write name="po" property="shipToAddress.address1" filter="true"/>
                                        </td>
                                </tr>
                                <tr>
                                <td><b>&nbsp;</b></td>
                                <td><bean:write name="po" property="shipToAddress.address2" filter="true"/>
                                        </td>
                                </tr>
                                <tr>
                                <td><b>&nbsp;</b></td>
                                <td><bean:write name="po" property="shipToAddress.address3" filter="true"/>
                                        </td>
                                </tr>
                                <tr>
                                <td><b>City:</b></td>
                                <td><bean:write name="po" property="shipToAddress.city" filter="true"/>
                                        </td>
                                </tr>
                                <tr>
                                <td><b>State:</b></td>
                                <td><bean:write name="po" property="shipToAddress.stateProvinceCd" filter="true"/>
                                        </td>
                                </tr>
                                <tr>
                                <td><b>ZIP Code:</b></td>
                                <td><bean:write name="po" property="shipToAddress.postalCode" filter="true"/>
                                        </td>
                                </tr>
                                <tr>
                                </tr>
                        </table>
                </td>

                <td colspan="2">
                        <table width="100%">
                                <tr>
                                <td><html:submit property="action"><app:storeMessage  key="invoice.button.lookupPo"/></html:submit></td>
                                <td><html:text name="INVOICE_OP_DETAIL_FORM" property="newErpPoNum" size="11"/></td>
                                <tr>
                                <td><b>Voucher Number:</b></td>
                                        <td><bean:write name="INVOICE_OP_DETAIL_FORM" property="voucher" filter="true"/></td>
                                </tr>
                                <tr>
                                <td><b>Vendor Erp Num:</b></td>
                                        <td>
                                                <html:text styleClass="text" size="11" maxlength="13" name="INVOICE_OP_DETAIL_FORM" property="distErpNumCheck"  tabindex="<%=Integer.toString(tabIndex++)%>"/>
                                                <span class="reqind" valign="top">*</span>
                                        </td>
                                </tr>
                                <tr> <% if(theForm.getErpSystemCd()==null) { %>
                    
                                   <td>
                                       <b>Batch Number:</b></td>
                                        <td>
                                                <html:select name="INVOICE_OP_DETAIL_FORM" property="batchNumS" tabindex="<%=Integer.toString(tabIndex++)%>">
                                                        <html:option value=""><app:storeMessage  key="admin.select"/></html:option>
                                                        <html:options  name="ERP.Dist.Unreleased.Batches"/>
                                                </html:select>
                                                <span class="reqind" valign="top">*</span>
                                        </td>
                                     <% } else { %>
                                       <td colspan="2">&nbsp;</TD>
                                     <% } %>
                                </tr>
                                <tr valign="top">
                                <td><b>Invoiced Total:</b></td>
                                        <td><html:text styleClass="text" size="11" maxlength="13" name="INVOICE_OP_DETAIL_FORM" property="totalAmountS" tabindex="<%=Integer.toString(tabIndex++)%>"/>
                                                <span class="reqind" valign="top">*</span>
                                        </td>
                                </tr>
                                <tr>
                                <td><b>Freight:</b></td>
                                        <td><html:text styleClass="text" size="11" maxlength="13" name="INVOICE_OP_DETAIL_FORM" property="totalFreightCostS" onchange="return calcTotal();" tabindex="<%=Integer.toString(tabIndex++)%>"/>
                                </td>
                                </tr>
                                <tr>
                                <td><b>Tax:</b></td>
                                        <td><html:text styleClass="text" size="11" maxlength="13" name="INVOICE_OP_DETAIL_FORM" property="totalTaxCostS" onchange="return calcTotal();" tabindex="<%=Integer.toString(tabIndex++)%>"/>
                                </td>
                                </tr>
                                <%--<tr>
                                <td><b>Handling:</b></td>
                                        <td><html:text styleClass="text" size="11" maxlength="13"  name="INVOICE_OP_DETAIL_FORM" property="totalMiscCostS" onchange="return calcTotal();"/>
                                </td>
                                </tr>
                                <tr>
                                <td><b>Discount:</b></td>
                                        <td><html:text styleClass="text" size="11" maxlength="13" name="INVOICE_OP_DETAIL_FORM" property="discountS" onchange="return calcTotal();"/>
                                </td>
                                </tr>--%>
                                <tr>
                                <td><b>Invoiced Sub-Total:</b></td>
                                        <td><html:text styleClass="textreadonly" size="11" maxlength="13" name="INVOICE_OP_DETAIL_FORM" property="invoiceSubTotalS" readonly="true"/>
                                </td>
                                </tr>
                                <tr>
                                <td><b>Contract Sub-Total:</b></td>
                                        <td><html:text styleClass="textreadonly" size="11" maxlength="13" name="INVOICE_OP_DETAIL_FORM" property="contractSubTotalS" readonly="true"/>
                                </td>
                                </tr>
                                <tr>
                                        <td colspan="2"><hr></td>
                                </tr>
                                <tr>
                                <td><b>Calculated Total:</b></td>
                                        <td><html:text styleClass="textreadonly" size="11" maxlength="13" name="INVOICE_OP_DETAIL_FORM" property="invoiceTotalS" readonly="true"/>
                                </td>
                                </tr>
                                <tr>
                                <td><b>Contract Total:</b></td>
                                        <td><html:text styleClass="textreadonly" size="11" maxlength="13" name="INVOICE_OP_DETAIL_FORM" property="contractTotalS" readonly="true"/>
                                </td>
                                </tr>
                        </table>
                </td>
        </tr>

        <tr><td colspan="6">&nbsp;</td></tr>
        <tr>
          <td><b>Notes:</b></td>
          <td colspan="5">&nbsp;</td>
    </tr>
        <tr><td colspan="6"><bean:write name="INVOICE_OP_DETAIL_FORM" property="orderPropertyDetail.value" filter="true"/></td></tr>
        <tr><td colspan="6">&nbsp;</td></tr>
        <tr>
          <td colspan="6" align="center">
            <input type="button" onclick="popLinkGlobal('orderOpNote.do?action=view&orderid=<%=orderId%>');" value="View All Order Detail Notes" >
            &nbsp;<input type="button" onclick="popLinkGlobal('orderOpNote.do?action=add&orderid=<%=orderId%>');" value="Add Order Detail Note" >
            &nbsp;<input type="button" onclick="popPrintGlobal('orderOpDetailPrint.do?action=view&amp;id=<%=orderId%>');" value="Print Order Detail">
            &nbsp;<input type="button" onclick="popPrintGlobal('invoiceOpDetail.do?action=print&amp;id=<%=orderId%>');" value="Print PO Detail">
          </td>
        </tr>

    <%/* po header infomration -->*/%>
    <tr>
        <td colspan="2" class="mediumheader">PO Header Information</td>
                <td colspan="2" class="mediumheader">&nbsp;</td>
                <td colspan="2" class="mediumheader">&nbsp;</td>
        </tr>
    <tr valign="top">
        <td colspan="2">
                        <table width="100%">
                                <tr>
<td><b>Outbound PO#:</b></td>
<td>
<bean:write name="po" property="purchaseOrderData.erpPoNum" filter="true"/>
                                </td>
                                </tr>
                                <tr>
                                <td><b>PO Date:</b></td>
                    <logic:present name="po" property="purchaseOrderData.poDate">
                      <td>
                        <bean:define id="erppodate" name="po" property="purchaseOrderData.poDate"/>
                        <i18n:formatDate value="<%=erppodate%>" pattern="MM/dd/yyyy" locale="<%=Locale.US%>"/>
                      </td>
                    </logic:present>
                    <logic:notPresent name="po" property="purchaseOrderData.poDate">
                      <td>&nbsp;</td>
                    </logic:notPresent>
                                </tr>


                        </table>
                </td>
                <td colspan="2">
                <table width="100%">
                <tr>
                        <td><b>Vendor Name:</b></td>
                        <td>
                                <bean:write name="po" property="distributorBusEntityData.shortDesc" filter="true"/>
                        </td>
                </tr>
                <tr>
                        <td><b>Vendor Id:</b></td>
                        <td>
                                <bean:write name="po" property="distributorBusEntityData.busEntityId" filter="true"/>
                        </td>
                </tr>
                <tr>
                        <td><b>Vendor Erp Num:</b></td>
                        <td>
                                <bean:write name="po" property="distributorBusEntityData.erpNum" filter="true"/>
                        </td>
                </tr>
                <tr>
                        <td><b>Vendor Order Minimum:</b></td>
                        <td>
                                <bean:write name="INVOICE_OP_DETAIL_FORM" property="distributorData.minimumOrderAmount" filter="true"/>
                        </td>
                </tr>
                </table>
                </td>
        <td colspan="2">
                        <table width="100%">
                                <tr>
                            <td><b>Status:</b></td>
                            <td><bean:write name="po" property="purchaseOrderData.purchaseOrderStatusCd" filter="true"/></td>
                </tr>
                                <tr>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                                </tr>
                <tr>
                                <td><b>Subtotal:</b></td>
                                <td>
                                        <bean:define id="purchaseOrderLnTotal"  name="po" property="purchaseOrderData.lineItemTotal"/>
                                        <i18n:formatCurrency value="<%=purchaseOrderLnTotal%>" locale="<%=Locale.US%>"/>
                                </td>
                </tr>
                <tr>
                </tr>
                <tr>
                                <td><b>Total:</b></td>
                                <td>
                                        <bean:define id="purchaseOrderTotal"  name="po" property="purchaseOrderData.purchaseOrderTotal"/>
                                        <i18n:formatCurrency value="<%=purchaseOrderTotal%>" locale="<%=Locale.US%>"/>
                                </td>
                        </tr>
                        </table>
                </td>
    </tr>
    <tr>
        <td colspan="6" align="center">
                <html:submit property="action" value="Submit Updates" onclick="return confirm('You are about to send this invoice to the ERP System for processing, click ok to proceed.');"/>
                <html:submit property="action"><app:storeMessage  key="admin.button.back"/></html:submit>
       </td>
    </tr>
    <%/* end po header infomration -->*/%>

        <tr><td colspan="6">&nbsp;</td></tr>

        <tr>
                <td colspan="6">
<table width="900" border="0" class="results">
<tr>
<td colspan="18"><span class="mediumheader"><b>Order Item Status:</b></span>
<bean:size id="itemCount" name="INVOICE_OP_DETAIL_FORM" property="orderItemDescList" />
<!-- <bean:write name="itemCount" />  -->
</td>
</tr>
<logic:present name="INVOICE_OP_DETAIL_FORM" property="orderItemDescList">
<jsp:include flush='true' page="invoiceOpDetailItemInc.jsp"/>
</logic:present>
</table>

</td>
</tr>
   <tr>
        <td colspan="6" align="center">
                <html:submit property="action" value="Submit Updates" onclick="return confirm('You are about to send this invoice to the ERP System for processing, click ok to proceed.');"/>
                <html:submit property="action"><app:storeMessage  key="admin.button.back"/></html:submit>
       </td>
    </tr>
</html:form>
</table>
</div>
<jsp:include flush='true' page="ui/consoleFooter.jsp"/>
</body>
</html:html>
