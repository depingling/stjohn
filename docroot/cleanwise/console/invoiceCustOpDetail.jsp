<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="java.util.Locale" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<app:checkLogon/>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="Location" value="contract" type="java.lang.String" toScope="session"/>
<html:html>

<head>
<link rel="stylesheet" href="../externals/styles.css">
<title>Operations Console: Invoices</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>
<body bgcolor="#FFFFFF">

<jsp:include flush='true' page="ui/consoleToolbar.jsp"/>
<jsp:include flush='true' page="ui/loginInfo.jsp"/>
<jsp:include flush='true' page="ui/invoiceOpToolbar.jsp"/>


<div class="text">

<font color=red>
<html:errors/>
</font>

<table cellpadding="2" cellspacing="0" border="0" width="769" class="mainbody">
<html:form name="INVOICE_CUST_OP_DETAIL_FORM" action="console/invoiceCustOpDetail.do"
    scope="session" type="com.cleanwise.view.forms.InvoiceCustOpSearchForm">


  <tr> <td><b>Customer Invoice:</b></td>
       <td colspan="4">&nbsp;
       </td>
  </tr>

<tr>
  <td>&nbsp;</td>
  <td><b>Account ID:</b></td>
  <td><bean:write name="INVOICE_CUST_OP_DETAIL_FORM" property="invoiceCustView.invoiceCustData.accountId" /></td>
  <td>&nbsp;</td>
  <td><b>Invoice Status:</b></td>
  <td><bean:write name="INVOICE_CUST_OP_DETAIL_FORM" property="invoiceCustView.invoiceCustData.invoiceStatusCd" /></td>
</tr>

<tr>
  <td>&nbsp;</td>
  <td><b>Invoice Type:</b></td>
  <td><bean:write name="INVOICE_CUST_OP_DETAIL_FORM" property="invoiceCustView.invoiceCustData.invoiceType" /></td>
  <td>&nbsp;</td>
  <td><b>Invoice Num:</b></td>
  <td><bean:write name="INVOICE_CUST_OP_DETAIL_FORM" property="invoiceCustView.invoiceCustData.invoiceNum" /></td>
</tr>

<tr>
  <td>&nbsp;</td>
  <td><b>Invoice Date:</b></td>
  <td>
              <logic:present name="INVOICE_CUST_OP_DETAIL_FORM" property="invoiceCustView.invoiceCustData.invoiceDate" >
                        <bean:define id="date" name="INVOICE_CUST_OP_DETAIL_FORM" property="invoiceCustView.invoiceCustData.invoiceDate" type="java.util.Date"/>
                        <i18n:formatDate value="<%=date%>" pattern="MM/dd/yyyy" locale="<%=Locale.US%>"/>&nbsp;
             </logic:present>
  </td>
</tr>

<tr>
  <td>&nbsp;</td>
  <td><b>Sub Total:</b></td>
  <td><bean:write name="INVOICE_CUST_OP_DETAIL_FORM" property="invoiceCustView.invoiceCustData.subTotal" /></td>
  <td>&nbsp;</td>
  <td><b>Misc Charges:</b></td>
  <td><bean:write name="INVOICE_CUST_OP_DETAIL_FORM" property="invoiceCustView.invoiceCustData.miscCharges" /></td>
</tr>

<tr>
  <td>&nbsp;</td>
  <td><b>Original Invoice Number:</b></td>
  <td><bean:write name="INVOICE_CUST_OP_DETAIL_FORM" property="invoiceCustView.invoiceCustData.originalInvoiceNum" /></td>
  <td>&nbsp;</td>
  <td>&nbsp;</td>
  <td>&nbsp;</td>
</tr>

<tr>
  <td>&nbsp;</td>
  <td><b>Bill To:</b><br>
  <bean:write name="INVOICE_CUST_OP_DETAIL_FORM" property="invoiceCustView.invoiceCustData.billToName" /><br>
  <bean:write name="INVOICE_CUST_OP_DETAIL_FORM" property="invoiceCustView.invoiceCustData.billToAddress1" /><br>
  <bean:write name="INVOICE_CUST_OP_DETAIL_FORM" property="invoiceCustView.invoiceCustData.billToAddress2" /><br>
  <bean:write name="INVOICE_CUST_OP_DETAIL_FORM" property="invoiceCustView.invoiceCustData.billToAddress3" /><br>
  <bean:write name="INVOICE_CUST_OP_DETAIL_FORM" property="invoiceCustView.invoiceCustData.billToAddress4" /><br>
  <bean:write name="INVOICE_CUST_OP_DETAIL_FORM" property="invoiceCustView.invoiceCustData.billToCity" /><br>
  <bean:write name="INVOICE_CUST_OP_DETAIL_FORM" property="invoiceCustView.invoiceCustData.billToState" /><br>
  <bean:write name="INVOICE_CUST_OP_DETAIL_FORM" property="invoiceCustView.invoiceCustData.billToPostalCode" /><br>
  </td>
  <td>&nbsp;</td>
  <td>&nbsp;</td>
  <td><b>Ship To:</b><br>
  <bean:write name="INVOICE_CUST_OP_DETAIL_FORM" property="invoiceCustView.invoiceCustData.shippingName" /><br>
  <bean:write name="INVOICE_CUST_OP_DETAIL_FORM" property="invoiceCustView.invoiceCustData.shippingAddress1" /><br>
  <bean:write name="INVOICE_CUST_OP_DETAIL_FORM" property="invoiceCustView.invoiceCustData.shippingAddress2" /><br>
  <bean:write name="INVOICE_CUST_OP_DETAIL_FORM" property="invoiceCustView.invoiceCustData.shippingAddress3" /><br>
  <bean:write name="INVOICE_CUST_OP_DETAIL_FORM" property="invoiceCustView.invoiceCustData.shippingAddress4" /><br>
  <bean:write name="INVOICE_CUST_OP_DETAIL_FORM" property="invoiceCustView.invoiceCustData.shippingCity" /><br>
  <bean:write name="INVOICE_CUST_OP_DETAIL_FORM" property="invoiceCustView.invoiceCustData.shippingState" /><br>
  <bean:write name="INVOICE_CUST_OP_DETAIL_FORM" property="invoiceCustView.invoiceCustData.shippingPostalCode" /><br>
</tr>


  <tr>
       <td colspan="5" align="center">
       <input type="button" onclick="popPrintGlobal('invoiceCustOpDetail.do?action=print');" value="Print Invoice">
     </td>
  </tr>
  <tr><td colspan="5">&nbsp;</td>
  </tr>


</html:form>
<!--old form end tag-->
</table>


<%--render items--%>
<logic:present name="INVOICE_CUST_OP_DETAIL_FORM" property="invoiceCustView.invoiceCustDetailDataVector">
<bean:size id="rescount" name="INVOICE_CUST_OP_DETAIL_FORM" property="invoiceCustView.invoiceCustDetailDataVector"/>

<logic:greaterThan name="rescount" value="0">
<table cellpadding="2" cellspacing="0" border="0" width="769" class="results">
<tr>
        <td><b>Invoice Detail Status</b></td>
        <td><b>Ship Status</b></td>
        <td><b>Line Number</b></td>
        <td><b>Sku Number</b></td>
        <td><b>Desc</b></td>
        <td><b>UOM</b></td>
        <td><b>Pack</b></td>
        <td><b>Qty</b></td>
        <td><b>Price</b></td>
        <td><b>Line Total</b></td>
<%
/*
Vladimir needs to finish implementing the InvoiceCustInfoView functionality.
durval 1/24/2006
        <td><b>Dist SKU#</b></td>
*/
%>
</tr>

<logic:iterate id="itm" name="INVOICE_CUST_OP_DETAIL_FORM" property="invoiceCustView.invoiceCustDetailDataVector" 
  type="com.cleanwise.service.api.value.InvoiceCustDetailData">

<tr>
  <td><bean:write name="itm" property="invoiceDetailStatusCd"/></td>
  <td class="resultscolumna"><bean:write name="itm" property="shipStatusCd"/></td>
  <td><bean:write name="itm" property="lineNumber"/></td>
  <td class="resultscolumna"><bean:write name="itm" property="itemSkuNum"/></td>
  <td><bean:write name="itm" property="itemShortDesc"/></td>
  <td class="resultscolumna"><bean:write name="itm" property="itemUom"/></td>
  <td><bean:write name="itm" property="itemPack"/></td>
  <td class="resultscolumna"><bean:write name="itm" property="itemQuantity"/></td>
  <td><bean:write name="itm" property="custContractPrice"/></td>
  <td class="resultscolumna"><bean:write name="itm" property="lineTotal"/></td>
<%
/*
Vladimir needs to finish implementing the InvoiceCustInfoView functionality.
durval 1/24/2006
  <td><bean:write name="itm" property="distItemSkuNum"/></td>
*/
%>

</tr>
</logic:iterate>
</table>
</logic:greaterThan>
</logic:present>

<jsp:include flush='true' page="ui/consoleFooter.jsp"/>
</body>

</html:html>




