<%@ page language="java"%>
<%@ page import="com.cleanwise.view.utils.Constants"%>
<%@ page import="com.cleanwise.service.api.value.*"%>
<%@ page import="java.util.Locale"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app"%>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n"%>

<app:checkLogon />

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session" />
<bean:define id="Location" value="contract" type="java.lang.String"
	toScope="session" />
<html:html>

<head>
<link rel="stylesheet" href="../externals/styles.css">
<title>Operations Console: Invoices</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#FFFFFF">

<%--<jsp:include flush='true' page="ui/consoleToolbar.jsp"/>
<%--<jsp:include flush='true' page="ui/loginInfo.jsp"/>
<jsp:include flush='true' page="ui/invoiceOpToolbar.jsp"/>--%>


<div class="text">
 
<logic:present
	name="INVOICE_CUST_OP_SEARCH_FORM" property="resultList">
	<bean:size id="rescount" name="INVOICE_CUST_OP_SEARCH_FORM"
		property="resultList" />
</logic:present>

<table cellpadding="2" cellspacing="0" border="0" width="769"
	class="mainbody">
	<html:form name="INVOICE_CUST_OP_SEARCH_FORM"
		action="storeportal/invoiceCustOp.do" scope="session"
		type="com.cleanwise.view.forms.InvoiceCustOpSearchForm">


		<tr>
			<td><b>Search:</b></td>
			<td colspan="4">&nbsp;</td>
		</tr>


		<tr>
			<td>&nbsp;</td>
			<td><b>Account ID:</b></td>
			<td colspan="3"><html:text name="INVOICE_CUST_OP_SEARCH_FORM"
				property="accountId" /> <html:button property="action"
				onclick="popLocateFeedGlobal('../adminportal/accountlocate', 'accountId','');"
				value="Locate Account" /></td>
		</tr>

		<tr>
			<td>&nbsp;</td>
			<td><b>Web order # / Confirmation #:</b></td>
			<td colspan="3"><html:text name="INVOICE_CUST_OP_SEARCH_FORM"
				property="webOrderNum" /></td>
		</tr>


		<tr>
			<td>&nbsp;</td>
			<td><b>Invoice Num:</b></td>
			<td colspan="3"><html:text name="INVOICE_CUST_OP_SEARCH_FORM"
				property="invoiceNumRangeBegin" /> <html:text
				name="INVOICE_CUST_OP_SEARCH_FORM" property="invoiceNumRangeEnd" />
			</td>
		</tr>

		<tr>
			<td>&nbsp;</td>
			<td><b>Invoice Date:</b><br>
			(mm/dd/yyyy)</td>
			<td colspan="3">Begin Date Range
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			End Date Range<br>
			<html:text name="INVOICE_CUST_OP_SEARCH_FORM"
				property="invoiceDateRangeBegin" /> <html:text
				name="INVOICE_CUST_OP_SEARCH_FORM" property="invoiceDateRangeEnd" />
			</td>
		</tr>


		<tr>
			<td colspan="5" align="center"><html:submit property="action">
				<app:storeMessage  key="global.action.label.search" />
			</html:submit> <logic:present name="INVOICE_CUST_OP_SEARCH_FORM"
				property="resultList">
				<logic:greaterThan name="rescount" value="0">
					<input type="button"
						onclick="popPrintGlobal('invoiceCustOp.do?action=printAll');"
						value="Print Invoices">
				</logic:greaterThan>
			</logic:present></td>
		</tr>
		<tr>
			<td colspan="5">&nbsp;</td>
		</tr>


	</html:form>
	<!--old form end tag-->
</table>


<%--render results--%> <logic:present name="INVOICE_CUST_OP_SEARCH_FORM"
	property="resultList">
Search results count:&nbsp;<bean:write name="rescount" />

	<logic:greaterThan name="rescount" value="0">
		<table cellpadding="2" cellspacing="0" border="0" width="769"
			class="results">
			<tr>
				<td><b>Account Id</b></td>
				<td><b>Invoice Type</b></td>
				<td><b>Invoice Num</b></td>
				<td><b>Invoice Date</b></td>
				<td><b>Invoice Status</b></td>
				<td><b>Bill To Name</b></td>
				<td><b>Ship To Name</b></td>
				<td><b>Sub Total</b></td>
				<td><b>Misc Charges</b></td>
				<td><b>Original Invoice num</b></td>
			</tr>

			<logic:iterate id="inv" name="INVOICE_CUST_OP_SEARCH_FORM"
				property="resultList"
				type="com.cleanwise.service.api.value.InvoiceCustView">
				<bean:define id="key" name="inv"
					property="invoiceCustData.invoiceCustId" />
				<bean:define id="orderkey" name="inv"
					property="invoiceCustData.orderId" />

				<%
					String linkHref = "invoiceCustOpDetail.do?action=view&id="
											+ key;
				%>

				<tr>
					<td><bean:write name="inv"
						property="invoiceCustData.accountId" /></td>
					<td><bean:write name="inv"
						property="invoiceCustData.invoiceType" /></td>
					<td class="resultscolumna"><a href="<%=linkHref%>"><bean:write
						name="inv" property="invoiceCustData.invoiceNum" /></a></td>
					<td><bean:define id="invoiceDate" name="inv"
						property="invoiceCustData.invoiceDate" /> <logic:present
						name="invoiceDate">
						<i18n:formatDate value="<%=invoiceDate%>" pattern="MM/dd/yyyy"
							locale="<%=Locale.US%>" />
					</logic:present></td>
					<td class="resultscolumna"><bean:write name="inv"
						property="invoiceCustData.invoiceStatusCd" /></td>
					<td><bean:write name="inv"
						property="invoiceCustData.billToName" /></td>
					<td class="resultscolumna"><bean:write name="inv"
						property="invoiceCustData.shippingName" /></td>
					<td><bean:write name="inv" property="invoiceCustData.subTotal" /></td>
					<td class="resultscolumna"><bean:write name="inv"
						property="invoiceCustData.miscCharges" /></td>
					<td><bean:write name="inv"
						property="invoiceCustData.originalInvoiceNum" /></td>
				</tr>
			</logic:iterate>
		</table>
	</logic:greaterThan>
</logic:present>
</body>

</html:html>




