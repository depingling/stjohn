<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="java.util.Date" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="theForm" name="ERP_INTEGRATION_FORM" type="com.cleanwise.view.forms.ErpIntegrationForm"/>
<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<div class="text">
  <table cellspacing="0" border="0" width="769" class="mainbody">
  <html:form action="/console/erpIntegration.do">
  <% Object errors =request.getAttribute("org.apache.struts.action.ERROR");
     if(errors!=null) {
  %>
  <tr><td class="genericerror" align="center"><html:errors/></td></tr>
  <%
  }
  %>
  <tr>
  <td colspan="2" align="center">
   <jsp:include flush='true' page="erpIntegrationFunctionSelect.jsp"/>
  </td>
  </tr>

  <tr>
  <td colspan="2" align="center">
  <%
     if(theForm.getCustErpInvoiceIds()!=null && theForm.getCustErpInvoiceIds().size()>0) {
  %>
     <html:submit property="action" value="Pickup Invoices"/>
  <% } else { %>
     No unprocessed released invoices found
  <% } %>
  </td>
  </tr>
  <tr align="center">
  <td width="20%">
     <html:text size="8" name="ERP_INTEGRATION_FORM" property="invoiceToDownload"/>
     <html:submit property="action" value="Reload Invoice"/>
  </td>
  <td width="20%">
     <b>Credit Num:</b><html:text size="8" name="ERP_INTEGRATION_FORM" property="creditInvoiceToDownload"/>
     <b>Original Invoice Num (opt):</b><html:text size="8" name="ERP_INTEGRATION_FORM" property="origInvoiceNum"/>
     <html:submit property="action" value="Reload Credit Invoice"/>
  </td>
  </tr>
  </table>
  <%
  InvoiceCustDataVector invoiceDV = theForm.getCustInvoices();
  String[] result = theForm.getDownloadCustInvoiceResults();
  if(invoiceDV!=null) {
  %>
    <table cellspacing="0" border="0" width="769" class="mainbody">
    <tr align="center">
      <td> <b> Invoice Cust Id </b></td>
      <td> <b> Erp Invoice Num</b></td>
      <td> <b> Invoice Date</b></td>
      <td> <b> Order Id</b></td>
      <td> <b> Site Id</b></td>
      <td> <b> Shipping Name</b></td>
      <td> <b> Status</b></td>
      <td> <b> Message</b></td>
    </tr>
  <%
    for(int ii=0; ii<invoiceDV.size(); ii++) {
      InvoiceCustData icD = (InvoiceCustData) invoiceDV.get(ii);
      int invoiceCustId = icD.getInvoiceCustId();
      String erpInvoiceNum = icD.getInvoiceNum();
      String erpInvoiceType = icD.getInvoiceType();
      if (RefCodeNames.INVOICE_TYPE_CD.CR.equals(erpInvoiceType)) {
        erpInvoiceNum += erpInvoiceType;
      }
      Date invoiceDate = icD.getInvoiceDate();
      int orderId = icD.getOrderId();
      int siteId = icD.getSiteId();
      String shippingName = icD.getShippingName();
      String status = icD.getInvoiceStatusCd();
      String message = "";
      if(result.length>ii) message = result[ii];
  %>
    <tr align="center">
      <td><%=invoiceCustId%></td>
      <td><%=erpInvoiceNum%></td>
      <td><%=invoiceDate%></td>
      <td><%=orderId%></td>
      <td><%=siteId%></td>
      <td><%=shippingName%></td>
      <td><%=status%></td>
      <td><%=message%></td>
    </tr>
  <% } %>
  </table>
  <% } %>
</html:form>
</table>
</div>


