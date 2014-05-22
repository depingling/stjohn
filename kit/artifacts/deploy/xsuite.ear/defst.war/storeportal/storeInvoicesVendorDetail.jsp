<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="java.util.Locale" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.view.forms.StoreVendorInvoiceDetailForm" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<bean:define id="theForm" name="STORE_VEN_INVOICE_DETAIL_FORM" type="com.cleanwise.view.forms.StoreVendorInvoiceDetailForm"/>

<link rel="stylesheet" href="../externals/menuBox.css">
<script language="JavaScript" src="../externals/menuBox.js"></script>


<script language="JavaScript1.2">

function initializeMenuBoxForErpAcctSelection() {

	var title = "Select Cost Center"
	var columnHeaders = ["Cost Center Id", "Name", "Status"]
	var selectableColumn = 1
	var evenRowColor = '<%=ClwCustomizer.getEvenRowColor(request.getSession())%>'
	var oddRowColor = '<%=ClwCustomizer.getOddRowColor(request.getSession())%>'
	var rows = new Array()
<%
    int i = 0;
if (theForm.getCostCenterDataVector() != null) {
	for(java.util.Iterator iter=theForm.getCostCenterDataVector().iterator(); iter.hasNext();) {
		CostCenterData costCenterData = (CostCenterData) iter.next();
%>        
		row = [<%=costCenterData.getCostCenterId()%>,'<%=costCenterData.getShortDesc()%>','<%=costCenterData.getCostCenterStatusCd()%>']
		rows[<%=i%>] = row
<%		
		i = i + 1;
	}
}
%>    
	menuBox.initialize(title, columnHeaders, selectableColumn, evenRowColor, oddRowColor, rows);
}	

initializeMenuBoxForErpAcctSelection()

function discountValidate(){
	var discount = document.getElementById('totalDiscountS').value;
	if(discount > 0){
		alert("Invalid Discount, should be a negative value ");
		return false;
	}
	return true;
}

function userConfirm(){
	var rejectedInvEmail = '<%=theForm.getRejectedInvEmail()%>';
	var msg = "";
	if(rejectedInvEmail == null || rejectedInvEmail == "" || rejectedInvEmail.length < 1 ){
		msg = "You are about to reject this invoice.  Vendor should be informed. Click ok to proceed.";
	}else{
		msg = "You are about to reject this invoice."
			+ " Vendor will be informed."
			+	" Click ok to proceed.";
	}
	return confirm(msg);
}

</script>


<table ID=850>

<tr><td>
<html:form styleId="851" action="/storeportal/invoicesVendorDetail.do">

<table ID=852 width="<%=Constants.TABLEWIDTH%>" class="mainbody">
<bean:define id="po" name="STORE_VEN_INVOICE_DETAIL_FORM" property="invoice" />
<%java.math.BigDecimal zero=new java.math.BigDecimal(0);%>
<%
    int tabIndex = 1;
    SessionTool st = new SessionTool(request);
    java.util.Locale locale = st.getUserLocaleCode(request);
    CleanwiseUser user = st.getUserData();
    String totalReadOnly = user.getUserProperties().getProperty(RefCodeNames.PROPERTY_TYPE_CD.TOTAL_FIELD_READONLY, "");
    boolean isDoNotAllowInvoiceEdits = ((StoreVendorInvoiceDetailForm) theForm).isDoNotAllowInvoiceEdits() && !((StoreVendorInvoiceDetailForm) theForm).isPoScreen();
%>

<tr valign="top">
    <td>
        <table ID=853 class="mainbody" width="100%">
            <tr>
                <td align="left">
                    <logic:notEqual name="STORE_VEN_INVOICE_DETAIL_FORM" property="prevInvoiceInList" value="0">
                        <html:submit property="action">
                            <app:storeMessage  key="global.action.label.previous"/>
                        </html:submit>
                    </logic:notEqual>
                </td>

                <td colspan="4" align="center">
                    <logic:notEqual name="STORE_VEN_INVOICE_DETAIL_FORM" property="invoice.invoiceDist.invoiceDistId" value="0">
                        <html:submit property="action">
                            <app:storeMessage  key="button.recalculate"/>
                        </html:submit>
                    </logic:notEqual>
                </td>

                <td align="right">
                    <logic:notEqual name="STORE_VEN_INVOICE_DETAIL_FORM" property="nextInvoiceInList" value="0">
                        <html:submit property="action">
                            <app:storeMessage  key="global.action.label.next"/>
                        </html:submit>
                    </logic:notEqual>
                </td>
            </tr>
            <tr>
                <td align="left">
                </td>

                <td colspan="4" align="center">
                    <html:submit property="action" onclick="return confirm('You are about to release this invoice, click ok to proceed.');">
                        <app:storeMessage  key="admin.button.submitUpdates"/>
                    </html:submit>
                    <button type="Button" onClick="location='invoicesVendor.do';">
                        <app:storeMessage  key="admin.button.back"/>
                    </button>
                    <logic:notEqual name="STORE_VEN_INVOICE_DETAIL_FORM" property="invoice.invoiceDist.invoiceDistId" value="0">
                        <button type="Button" onClick="window.open('invoicesVendorDetail.do?action=print');">
                            <app:storeMessage  key="global.action.label.print"/>
                        </button>
                    </logic:notEqual>

                    <logic:equal name="STORE_VEN_INVOICE_DETAIL_FORM" property="updateableInvoiceStatus" value="true">
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <html:submit property="action" onclick="return userConfirm();">
                            <app:storeMessage  key="invoice.button.reject"/>
                        </html:submit>
                        <html:submit property="action" onclick="return confirm('This invoice will NOT be sent to the erp system, click ok to proceed.');">
                            <app:storeMessage  key="invoice.button.manuallyResolved"/>
                        </html:submit>
                    </logic:equal>
                    <logic:equal name="STORE_VEN_INVOICE_DETAIL_FORM" property="invoice.invoiceDist.invoiceStatusCd" value="<%=RefCodeNames.INVOICE_STATUS_CD.REJECTED%>">
                    <html:submit property="action" onclick="return confirm('This will re-set this invoice to be reprocessed...it may still go into a pending status, click ok to proceed.');">
                            <app:storeMessage  key="invoice.button.resetStatus"/>
                    </html:submit>
                    </logic:equal>
                    
                </td>

                <td align="right">
                </td>
            </tr>
        </table>
    </td>
</tr>

<tr valign="top">
    <td colspan="6" class="mediumheader">Invoice Information</td>
</tr>

<tr valign="top">
    <td>
        <table ID=854 width="100%">
            <td><b>Invoice Date:</b></td>

            <td>
                <%if(isDoNotAllowInvoiceEdits) {%>
                <logic:present name="STORE_VEN_INVOICE_DETAIL_FORM" property="invoiceDateS">
        		  <%=ClwI18nUtil.formatDateInp(request, theForm.getInvoice().getInvoiceDist().getInvoiceDate())%>
                </logic:present>
                <%} else {%>
                <html:text styleClass="text" size="10" maxlength="15" property="invoiceDateS" tabindex="<%=Integer.toString(tabIndex++)%>"/>
                <span class="reqind" valign="top">*</span>
                <%}%>
            </td>

            <td><b>Invoice #:</b></td>

            <td>
                <%if(isDoNotAllowInvoiceEdits) {%>
                <logic:present name="STORE_VEN_INVOICE_DETAIL_FORM" property="invoice.invoiceDist.invoiceNum">
                <bean:write name="STORE_VEN_INVOICE_DETAIL_FORM" property="invoice.invoiceDist.invoiceNum"/>
                </logic:present>
                <%} else {%>
                <html:text styleClass="text" size="15" maxlength="20" property="invoice.invoiceDist.invoiceNum" tabindex="<%=Integer.toString(tabIndex++)%>"/>
                <span class="reqind" valign="top">*</span>
                <%}%>
            </td>
            <td align="right" width="40%">&nbsp;
            <logic:equal name="STORE_VEN_INVOICE_DETAIL_FORM" property="invoice.invoiceApproved" value="true">
              <b>Invoice approved by
              <bean:write name="STORE_VEN_INVOICE_DETAIL_FORM" property="invoice.invoiceApprovedBy" filter="true"/>
              at
       		  <%=ClwI18nUtil.formatDateInp(request, theForm.getInvoice().getInvoiceApprovedDate())%>
              </b>
            </logic:equal>
            </td>
        </table>
    </td>
</tr>

<tr valign="top">
<td>
<table ID=855 width="100%">
<td colspan="2">
    <table ID=856 align="left" width="100%">
        <tr>
            <td><b>Total:</b></td>
            <% if (totalReadOnly.equalsIgnoreCase("true") || totalReadOnly.equalsIgnoreCase("on")) { %>
            <td colspan="2">
                <logic:present name="STORE_VEN_INVOICE_DETAIL_FORM" property="calculatedTotal">
                    <bean:define id="calculatedTotal" name="STORE_VEN_INVOICE_DETAIL_FORM" property="calculatedTotal"/>
                    <%=ClwI18nUtil.formatInvoiceCurrency(request, calculatedTotal, po)%>    
                    <input type="hidden" name="totalAmountS" value="<%=calculatedTotal%>"/>
                </logic:present>
                <br>
            </td>
            <%} else { %>
            <td><html:text styleClass="text" size="11" maxlength="13" name="STORE_VEN_INVOICE_DETAIL_FORM" property="totalAmountS" tabindex="<%=Integer.toString(tabIndex++)%>"/><br></td>
            <td><span class="reqind" valign="top">*</span><br></td>
            <%} %>
        </tr>
        <tr>
            <td><b>Freight:</b></td>
            <td>
                <%if(isDoNotAllowInvoiceEdits) {%>
                <logic:present  name="STORE_VEN_INVOICE_DETAIL_FORM" property="totalFreightCostS">
                <bean:write name="STORE_VEN_INVOICE_DETAIL_FORM" property="totalFreightCostS"/>
                </logic:present>
                <%} else {%>
                <html:text styleClass="text" size="11" maxlength="13" name="STORE_VEN_INVOICE_DETAIL_FORM" property="totalFreightCostS" onchange="return calcTotal();" tabindex="<%=Integer.toString(tabIndex++)%>"/>
                <%}%>
                <br>
            </td>
        </tr>
        <tr>
            <td><b>Requested Freight:</b></td>
            <td>
                <logic:present name="po" property="vendorRequestedFreight">
                    <bean:define id="vendorRequestedFreight" name="po" 
                    property="vendorRequestedFreight"/>
                    <%=ClwI18nUtil.formatInvoiceCurrency(request, vendorRequestedFreight, po)%>
                </logic:present>
                <logic:notPresent name="po" property="vendorRequestedFreight">
                    <%=ClwI18nUtil.formatInvoiceCurrency(request, zero, po)%>
                </logic:notPresent>
            </td>
        </tr>
        <tr>
            <td><b>Misc Charges:</b></td>
            <td>
                <%if(isDoNotAllowInvoiceEdits) {%>
                <logic:present name="STORE_VEN_INVOICE_DETAIL_FORM" property="totalMiscChargesS">
                <bean:write name="STORE_VEN_INVOICE_DETAIL_FORM" property="totalMiscChargesS"/>
                </logic:present>
                <%} else {%>
                <html:text styleClass="text" size="11" maxlength="13" name="STORE_VEN_INVOICE_DETAIL_FORM" property="totalMiscChargesS" onchange="return calcTotal();" tabindex="<%=Integer.toString(tabIndex++)%>"/>
                <%}%>
                <br></td>
        </tr>
        <tr>
            <td><b>Requested Misc Charges:</b></td>
            <td>
                <logic:present name="po" property="vendorRequestedMiscCharges">
                    <bean:define id="vendorRequestedMiscCharges" name="po" property="vendorRequestedMiscCharges"/>
                    <%=ClwI18nUtil.formatInvoiceCurrency(request, vendorRequestedMiscCharges, po)%>         
                </logic:present>
                <logic:notPresent name="po" property="vendorRequestedMiscCharges">
                    <%=ClwI18nUtil.formatInvoiceCurrency(request, zero, po)%>   
                </logic:notPresent>
            </td>
        </tr>
        <tr>
            <td><b>Tax:</b></td>
            <td>
                <%if(isDoNotAllowInvoiceEdits) {%>
                <logic:present name="STORE_VEN_INVOICE_DETAIL_FORM" property="totalTaxCostS">
                    <bean:write name="STORE_VEN_INVOICE_DETAIL_FORM" property="totalTaxCostS"/>
                </logic:present>
                <%} else {%>
                <html:text styleClass="text" size="11" maxlength="13" name="STORE_VEN_INVOICE_DETAIL_FORM" property="totalTaxCostS" onchange="return calcTotal();" tabindex="<%=Integer.toString(tabIndex++)%>"/>
                <%}%>
            </td>
        </tr>
        <tr>
            <td><b>Calculated Tax:</b></td>
            <td>
                <logic:present name="STORE_VEN_INVOICE_DETAIL_FORM" property="calculatedSalesTax">
                    <bean:define id="calculatedSalesTax" name="STORE_VEN_INVOICE_DETAIL_FORM" property="calculatedSalesTax"/>
                    <%=ClwI18nUtil.formatInvoiceCurrency(request, calculatedSalesTax, po)%> 
                </logic:present>
            </td>
        </tr>
        <tr>
            <td><b>Requested Tax:</b></td>
            <td>
                <logic:present name="po" property="vendorRequestedTax">
                    <bean:define id="vendorRequestedTax" name="po" property="vendorRequestedTax"/>
                   <%=ClwI18nUtil.formatInvoiceCurrency(request, vendorRequestedTax, po)%>  
                </logic:present>
                <logic:notPresent name="po" property="vendorRequestedTax">
                   <%=ClwI18nUtil.formatInvoiceCurrency(request, zero, po)%>    
                </logic:notPresent>
            </td>
        </tr>
        <tr>
          <td><b>Discount:</b></td>
          <td>
              <%if(isDoNotAllowInvoiceEdits) {%>
              <logic:present name="STORE_VEN_INVOICE_DETAIL_FORM" property="totalDiscountS">
              <bean:write name="STORE_VEN_INVOICE_DETAIL_FORM" property="totalDiscountS"/>
              </logic:present>
              <%} else {%>
              <html:text styleClass="text" size="11" maxlength="13" styleId="totalDiscountS" 
              name="STORE_VEN_INVOICE_DETAIL_FORM" property="totalDiscountS" 
              onchange=" return discountValidate(); return calcTotal();" 
              tabindex="<%=Integer.toString(tabIndex++)%>"/>
              <%}%>
              <br>
          </td>
        </tr>
        
        <tr>
	 		  	<td><b>Calculated Discount:</b></td>
          <td>
              <logic:present name="po" property="orderAddOnChargeDataVector">
              	<logic:iterate id="orderAddOnChargeData" name="po" property="orderAddOnChargeDataVector">
              		<logic:equal name="orderAddOnChargeData" property="distFeeChargeCd" value="<%=RefCodeNames.CHARGE_CD.DISCOUNT%>">
                  	<bean:define id="discountAmnt" name="orderAddOnChargeData" property="amount"/>
                 		<%=ClwI18nUtil.formatInvoiceCurrency(request, discountAmnt, po)%>  
                 	</logic:equal>
                </logic:iterate>
              </logic:present>
              <logic:notPresent name="discountAmnt" >
                 <%=ClwI18nUtil.formatInvoiceCurrency(request, zero, po)%>    
              </logic:notPresent>
          </td>
       </tr>

        <tr>
          <td><b>Requested Discount:</b></td>
          <td>
              <logic:present name="po" property="vendorRequestedDiscount">
                  <bean:define id="vendorRequestedDiscount" name="po" 
                  property="vendorRequestedDiscount"/>
                 <%=ClwI18nUtil.formatInvoiceCurrency(request, vendorRequestedDiscount, po)%>  
              </logic:present>
              <logic:notPresent name="po" property="vendorRequestedDiscount">
                 <%=ClwI18nUtil.formatInvoiceCurrency(request, zero, po)%>    
              </logic:notPresent>
          </td>
        </tr>
                
        <tr>
            <td><b>Actual Sub-Total:</b></td>
            <td>
                <logic:present name="po" property="invoiceDist.subTotal">
                    <bean:define id="invSubTotal" name="po" 
                    property="invoiceDist.subTotal"/>
                        <%=ClwI18nUtil.formatInvoiceCurrency(request, invSubTotal, po)%>    
                </logic:present>
            </td>
        </tr>

        <tr>
            <td><b>Received Total:</b></td>
            <td>
                <logic:present name="STORE_VEN_INVOICE_DETAIL_FORM" property="totalReceivedCost">
                    <bean:define id="totalReceivedCost" name="STORE_VEN_INVOICE_DETAIL_FORM" property="totalReceivedCost"/>
                    <%=ClwI18nUtil.formatInvoiceCurrency(request, totalReceivedCost, po)%>  
                </logic:present>
            </td>
        </tr>
    </table>
</td>
<td colspan="2">
    <table ID=857 width="100%">
        <tr>
            <td><b>Shipping Address:</b><BR></td>
            <td><bean:write name="po" property="invoiceDist.shipToAddress1" filter="true"/><BR></td>
        </tr>
        <tr>
            <td>&nbsp;</td><td><bean:write name="po" property="invoiceDist.shipToAddress2" filter="true"/><BR></td>
        </tr>
        <tr>
            <td>&nbsp;</td><td><bean:write name="po" property="invoiceDist.shipToAddress3" filter="true"/><BR></td>
        </tr>
        <tr>
            <td><b>City:</b></td>
            <td><bean:write name="po" property="invoiceDist.shipToCity" filter="true"/><BR></td>
        </tr>
        <tr>
            <td><b>State:</b></td>
            <td><bean:write name="po" property="invoiceDist.shipToState" filter="true"/><BR></td>
        </tr>
        <tr>
            <td><b>ZIP Code:</b></td>
            <td><bean:write name="po" property="invoiceDist.shipToPostalCode" filter="true"/><BR></td>
        </tr>
    </table>
</td>
<td colspan="2">
    <table ID=858 width="100%">
        <tr>
            <td>
                <%if(theForm.getRemitToAddresses() != null){%>
                <b>Remit To:</b>
                <bean:define id="remitOpt" name="STORE_VEN_INVOICE_DETAIL_FORM" property="remitToAddresses"/>
                <html:select property="invoice.invoiceDist.remitTo" tabindex="<%=Integer.toString(tabIndex++)%>">
                    <html:option value=""><app:storeMessage  key="admin.select"/></html:option>
                    <html:options collection="remitOpt" property="name1" labelProperty="name2"/>
                </html:select>
                <BR>
                <%}%>
                <b>Invoice Status Code:</b>
                <bean:write name="po" property="invoiceDist.invoiceStatusCd"/>
                <BR>
                <logic:notEqual name="po" property="invoiceDist.invoiceDistId" value="0">
                    <html:submit property="action"><app:storeMessage  key="admin.button.addNote"/></html:submit><br>
                </logic:notEqual>
                <logic:equal name="po" property="invoiceDist.invoiceDistId" value="0">
                    <b>Note:</b><br>
                </logic:equal>
                <html:textarea property="newNote" rows="4" cols="30"/>

                <br>
                <logic:equal name="po" property="orderReceived" value="true">
                    Order has been received
                </logic:equal>
                <logic:equal name="po" property="orderReceived" value="false">
                    <%-- if receiving system is disabled (specifically or property not present) don't display anything --%>
                    <logic:present name="STORE_VEN_INVOICE_DETAIL_FORM" property="receivingSystemInvoiceCd" >
                        <logic:notEqual name="STORE_VEN_INVOICE_DETAIL_FORM" property="receivingSystemInvoiceCd" value="<%=RefCodeNames.RECEIVING_SYSTEM_INVOICE_CD.DISABLED%>">
                            <%-- if this distributor is configered for requiered entry emphasize the text --%>
                            <logic:equal name="STORE_VEN_INVOICE_DETAIL_FORM" property="receivingSystemInvoiceCd" value="<%=RefCodeNames.RECEIVING_SYSTEM_INVOICE_CD.REQUIERE_ENTRY_FIRST_ONLY%>">
                                <font color="red">Order NOT Received</font>
                            </logic:equal>
                            <%-- otherwise just indicate that order has not been recieved --%>
                            <logic:notEqual name="STORE_VEN_INVOICE_DETAIL_FORM" property="receivingSystemInvoiceCd" value="<%=RefCodeNames.RECEIVING_SYSTEM_INVOICE_CD.REQUIERE_ENTRY_FIRST_ONLY%>">
                                Order NOT Received
                            </logic:notEqual>
                        </logic:notEqual>
                    </logic:present>
                </logic:equal>
            </td>
        </tr>
    </table>
</td>
</table>
</td>
</tr>

<logic:present name="STORE_VEN_INVOICE_DETAIL_FORM" property="notes">
    <bean:size id="noteCount" name="STORE_VEN_INVOICE_DETAIL_FORM" property="notes" />
    <logic:greaterThan name="noteCount" value="0">
        <tr>
            <td colspan="6" class="mediumheader">Notes</td>
        </tr>
        <logic:iterate id="aNote" name="STORE_VEN_INVOICE_DETAIL_FORM" property="notes">
            <tr>
                <td>
                    <table ID=859>
                        <td>&nbsp;</td>
                        <td>
                            <logic:present name="aNote" property="addDate">
                                <bean:define id="noteAddDate" type="java.util.Date" name="aNote" property="addDate"/>
       		                    <%=ClwI18nUtil.formatDateInp(request, noteAddDate)%>
                            </logic:present>
                        </td>
                        <td colspan="4">
                            <bean:write name="aNote" property="value"/>
                            <br><hr width="90%">
                        </td>
                    </table>
                </td>
            </tr>
        </logic:iterate>
    </logic:greaterThan>
</logic:present>

<tr><td colspan="6">&nbsp;</td></tr>


<%/* po header infomration -->*/%>

<tr>
    <td colspan="4">
        <table ID=860 width="100%">
            <tr>
                <td align="right">
                    PO Number: <html:text styleClass="text" name="STORE_VEN_INVOICE_DETAIL_FORM" property="newErpPoNum" size="11"/>
                        <html:submit property="action" onclick="return confirm('You will loose any changes are you sure you want to proceed?');"><app:storeMessage  key="invoice.button.lookupPo"/></html:submit>
                    <html:submit property="action" onclick="return confirm('You will loose any changes are you sure you want to proceed?');"><app:storeMessage  key="invoice.button.assignNewPo"/></html:submit>
                </td>
            </tr>
        </table>
    </td>
</tr>

<tr>
    <td colspan="6" class="mediumheader">PO Header Information</td>
</tr>

<tr valign="top">
<td>
<table ID=861>
<td colspan="2">
    <table ID=862 width="100%">
        <tr>
            <td><b>Outbound PO#:</b></td>
            <td>
                <logic:present name="po" property="purchaseOrderData">
                    <bean:write name="po" property="purchaseOrderData.erpPoNum" filter="true"/>
                </logic:present>
                <logic:notPresent name="po" property="purchaseOrderData">
                    <bean:write name="po" property="invoiceDist.erpPoNum" filter="true"/>
                </logic:notPresent>
            </td>
        </tr>
        <tr>
            <td><b>PO Date:</b></td>
            <logic:present name="po" property="purchaseOrderData.poDate">
                <td>
                    <bean:define id="erppodate" type="java.util.Date" name="po" property="purchaseOrderData.poDate"/>
       		        <%=ClwI18nUtil.formatDateInp(request, erppodate)%>
                </td>
            </logic:present>
            <logic:notPresent name="po" property="purchaseOrderData.poDate">
                <td>&nbsp;</td>
            </logic:notPresent>
        </tr>
        <logic:present name="po" property="orderFreightDataVector">
            <tr>
                <td><b>Freight Info:</b></td>
            </tr>
            <logic:iterate id="frt" name="po" property="orderFreightDataVector">
                <tr>
                    <td>&nbsp;</td>
                    <td><b><bean:write name="frt" property="shortDesc"/></b></td>
                    <td>
                        <logic:equal name="frt" property="freightTypeCd" value="<%=RefCodeNames.FREIGHT_CRITERIA_TYPE_CD.ESTIMATE%>">
                            unknown
                        </logic:equal>
                        <logic:notEqual name="frt" property="freightTypeCd" value="<%=RefCodeNames.FREIGHT_CRITERIA_TYPE_CD.ESTIMATE%>">
                            <logic:present name="frt" property="amount">
                              <bean:define id="freightAmt" name="frt" property="amount" type="java.math.BigDecimal"/>
                             <%=ClwI18nUtil.formatInvoiceCurrency(request, freightAmt, po)%>    
                            </logic:present>  
                        </logic:notEqual>
                    </td>
                </tr>
            </logic:iterate>
        </logic:present>
    </table>
</td>
<td colspan="2">
    <table ID=863 width="100%">
        <tr>
            <td><b>Vendor Name:</b></td>
            <td>
                <logic:present name="po" property="distributorBusEntityData">
                    <bean:write name="po" property="distributorBusEntityData.shortDesc" filter="true"/>
                </logic:present>
            </td>
        </tr>
        <tr>
            <td><b>Vendor Order Minimum:</b></td>
            <td>
                <logic:present name="STORE_VEN_INVOICE_DETAIL_FORM" property="vendorMinimumOrder">
                    <bean:define id="vendorMinimumOrder" name="STORE_VEN_INVOICE_DETAIL_FORM" property="vendorMinimumOrder"/>
                   <%=ClwI18nUtil.formatInvoiceCurrency(request, vendorMinimumOrder, po)%>  
                </logic:present>
            </td>
        </tr>
    </table>
</td>
<td colspan="2">
    <table ID=864 width="100%">
        <tr>
            <td><b>Status:</b></td>
            <td>
                <logic:present name="po" property="purchaseOrderData">
                    <bean:write name="po" property="purchaseOrderData.purchaseOrderStatusCd" filter="true"/>
                </logic:present>
            </td>
        </tr>
        <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
        </tr>
        <tr>
            <td><b>Subtotal:</b></td>
            <td>
                <logic:present name="po" property="purchaseOrderData.lineItemTotal">
                    <bean:define id="purchaseOrderLnTotal"  name="po" property="purchaseOrderData.lineItemTotal"/>
                  <%=ClwI18nUtil.formatInvoiceCurrency(request, purchaseOrderLnTotal, po)%> 
                </logic:present>
            </td>
        </tr>
        <tr>
        </tr>
        <tr>
            <td><b>Total:</b></td>
            <td>
                <logic:present name="po" property="purchaseOrderData.purchaseOrderTotal">
                    <bean:define id="purchaseOrderTotal"  name="po" property="purchaseOrderData.purchaseOrderTotal"/>
                    <%=ClwI18nUtil.formatInvoiceCurrency(request, purchaseOrderTotal, po)%> 
                </logic:present>
            </td>
        </tr>
    </table>
</td>
</table>
</td>
</tr>

    <%--  order header information --%>
<tr>
    <td colspan="6" class="mediumheader">Order Header Information</td>
</tr>

<tr valign="top">
<td>
<table ID=865>
<td colspan="4">
    <table ID=866 width="100%">
        <tr valign="top">
            <td><b>Web Order#:</b></td>
            <td>
                <logic:present name="po" property="orderData">
                    <bean:write name="po" property="orderData.orderNum" filter="true"/>
                </logic:present>
            </td>
        </tr>
        <tr>
            <td><b>Entered PO#:</b></td>
            <td>
                <logic:present name="po" property="orderData">
                    <bean:write name="po" property="orderData.requestPoNum" filter="true"/>
                </logic:present>
            </td>
        </tr>
        <tr>
            <td><b>ERP Order#:</b></td>
            <td>
                <logic:present name="po" property="orderData">
                    <bean:write name="po" property="orderData.erpOrderNum" filter="true"/>
                </logic:present>
            </td>
        </tr>
        <tr>
            <td><b>Entered Requisition#:</b></td>
            <td>
                <logic:present name="po" property="orderData">
                    <bean:write name="po" property="orderData.refOrderNum" filter="true"/>
                </logic:present>
            </td>
        </tr>

        <tr>
            <td><b>Date Ordered:</b></td>
            <td>
                <logic:present name="po" property="orderData">
                    <logic:present name="po" property="orderData.originalOrderDate" >
                        <bean:define id="orderdate" name="po" property="orderData.originalOrderDate" type="java.util.Date"/>
       		            <%=ClwI18nUtil.formatDateInp(request, orderdate)%>   
                    </logic:present>
                </logic:present>
            </td>
        </tr>
        <tr>
            <td><b>Order Status:</b></td>
            <td>
                <logic:present name="po" property="orderData">
                    <bean:write name="po" property="orderData.orderStatusCd" filter="true"/>
                </logic:present>
            </td>
        </tr>
    </table>
</td>
<td colspan="2">
    <table ID=867 width="100%">
        <tr valign="top">
            <td><b>Account Name:</b></td>
            <td>
                <logic:present name="po" property="accountBusEntityData">
                    <bean:write name="po" property="accountBusEntityData.shortDesc" filter="true"/>
                </logic:present>
            </td>
        </tr>
        <tr>
            <td><b>Site Name:</b></td>
            <td>
                <logic:present name="po" property="orderData">
                    <bean:write name="po" property="orderData.orderSiteName" filter="true"/>
                </logic:present>
                <logic:notPresent name="po" property="orderData">
                    <bean:write name="po" property="invoiceDist.shipToName" filter="true"/>
                </logic:notPresent>
            </td>
        </tr>
        <tr>
            <td><b>Shipping Address:</b></td>
            <td>
                <logic:present name="po.shipToAddress">
                    <bean:write name="po" property="shipToAddress.address1" filter="true"/>
                </logic:present>
            </td>
        </tr>
        <tr>
            <td><b>&nbsp;</b></td>
            <td>
                <logic:present name="po.shipToAddress">
                    <bean:write name="po" property="shipToAddress.address2" filter="true"/>
                </logic:present>
            </td>
        </tr>
        <tr>
            <td><b>&nbsp;</b></td>
            <td>
                <logic:present name="po.shipToAddress">
                    <bean:write name="po" property="shipToAddress.address3" filter="true"/>
                </logic:present>
            </td>
        </tr>
        <tr>
            <td><b>City:</b></td>
            <td>
                <logic:present name="po.shipToAddress">
                    <bean:write name="po" property="shipToAddress.city" filter="true"/>
                </logic:present>
            </td>
        </tr>
        <tr>
            <td><b>State:</b></td>
            <td>
                <logic:present name="po.shipToAddress">
                    <bean:write name="po" property="shipToAddress.stateProvinceCd" filter="true"/>
                </logic:present>
            </td>
        </tr>
        <tr>
            <td><b>ZIP Code:</b></td>
            <td>
                <logic:present name="po.shipToAddress">
                    <bean:write name="po" property="shipToAddress.postalCode" filter="true"/>
                </logic:present>
            </td>
        </tr>
        <tr>
        </tr>
    </table>
</td>
</table>
</td>
</tr>

<%/* end order header infomration -->*/%>

<tr><td colspan="6">&nbsp;</td></tr>
</table>

<logic:present name="STORE_VEN_INVOICE_DETAIL_FORM" property="invoiceItems">
    <jsp:include flush='true' page="storeInvoicesVendorDetailItemInc.jsp"/>
</logic:present>

<table ID=868 width="<%=Constants.TABLEWIDTH%>">
    <tr>
        <td align="center">
            <html:submit property="action" onclick="return confirm('You are about to release this invoice, click ok to proceed.');">
                <app:storeMessage  key="admin.button.submitUpdates"/>
            </html:submit>
            <button type="Button" onClick="location='invoicesVendor.do';">
                <app:storeMessage  key="admin.button.back"/>
            </button>
            <logic:notEqual name="STORE_VEN_INVOICE_DETAIL_FORM" property="invoice.invoiceDist.invoiceDistId" value="0">
                <html:submit property="action">
                    <app:storeMessage  key="global.action.label.print"/>
                </html:submit>
            </logic:notEqual>
            <logic:equal name="STORE_VEN_INVOICE_DETAIL_FORM" property="updateableInvoiceStatus" value="true">
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                
                <%--<html:submit property="action" onclick="return confirm('You are about to reject this invoice.  Vendor should be informed. click ok to proceed.');"> --%>
                <html:submit property="action" 
                    onclick="return userConfirm();">
                    <app:storeMessage  key="invoice.button.reject"/>
                </html:submit>
                <html:submit property="action" onclick="return confirm('This invoice will NOT be sent to the erp system, click ok to proceed.');">
                    <app:storeMessage  key="invoice.button.manuallyResolved"/>
                </html:submit>
            </logic:equal>
            <html:submit property="action" onclick="return confirm('You are about to add a line item to this invoice that was not on the original po, this is not recommended, click ok to proceed.');">
                <app:storeMessage  key="invoice.button.addLine"/>
            </html:submit>
            <logic:notEqual name="STORE_VEN_INVOICE_DETAIL_FORM" property="invoice.invoiceDist.invoiceDistId" value="0">
                <html:submit property="action">
                    <app:storeMessage  key="button.recalculate"/>
                </html:submit>
            </logic:notEqual>
        </td>
    </tr>
</table>

</html:form>
</td>

</tr>

</table>
