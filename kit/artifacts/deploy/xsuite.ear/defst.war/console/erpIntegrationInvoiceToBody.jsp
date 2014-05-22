<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="java.util.Date" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

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
  <tr><td colspan=3 class="genericerror" align="center"><html:errors/></td></tr>
  <%
  }
  %>
  <tr>
  <td colspan=3 align="center">
   <jsp:include flush='true' page="erpIntegrationFunctionSelect.jsp"/>
  </td>
  </tr>

  <tr>
    <td>
    <b>Select Batch Number:</b>
    <% IdVector batchs = theForm.getUnreleasedBatches();
       if(batchs.size()>0) {
    %>
      <html:select name="ERP_INTEGRATION_FORM" property="batchNum">
    <% for(int ii=0; ii<batchs.size(); ii++) {
         int batchNum = ((Integer)batchs.get(ii)).intValue();
    %>
      <html:option value="<%=\"\"+batchNum%>"><%=batchNum%></html:option>
    <% } %>
    </html:select>
    <%   } else { %>
    No unreleased batches found
    <% } %>
    </td>
    <td>
    <b>Select Account:</b>
    <% BusEntityDataVector accounts = theForm.getAccounts();
       if(accounts.size()>0) {
    %>
      <html:select name="ERP_INTEGRATION_FORM" property="accountErpNum">
    <% for(int ii=0; ii<accounts.size(); ii++) {
         BusEntityData beD = (BusEntityData) accounts.get(ii);
         String accountErpNum = beD.getErpNum();
         String shortDesc = beD.getShortDesc();
    %>
      <html:option value="<%=accountErpNum%>"><%=shortDesc%>(<%=accountErpNum%>)</html:option>
    <% } %>
    </html:select>
    <%   } else { %>
    No unprocessed invoices found
    <% } %>
    <td>
    <%
       if(batchs.size()>0 && accounts.size()>0 ) {
    %>
     <html:submit property="action" value="Upload"/>
    <% } %>
    </td>
    </tr>
    </table>
    </td>
  </tr>
  <%
  InvoiceDistDataVector invoiceDistDV = theForm.getInvoices();
  String[] result = theForm.getUploadResults();
  if(invoiceDistDV!=null) {
  %>
  <tr>
    <table cellspacing="0" border="0" width="769" class="mainbody">
    <tr align="center">
      <td> <b> Erp PO</b></td>
      <td> <b> Invoice Number</b></td>
      <td> <b> Invoice Data</b></td>
      <td> <b> Ship To Name</b></td>
      <td> <b> Ship From Name</b></td>
      <td> <b> Status</b></td>
      <td> <b> Message</b></td>
    </tr>
  <%
    for(int ii=0; ii<invoiceDistDV.size(); ii++) {
      InvoiceDistData idD = (InvoiceDistData) invoiceDistDV.get(ii);
      String poNum = idD.getErpPoNum();
      String invNum = idD.getInvoiceNum();
      Date invDate = idD.getInvoiceDate();
      String shipToName = idD.getShipToName();
      String shipFromName = idD.getShipFromName();
      String status = idD.getInvoiceStatusCd();
      String message = result[ii];
  %>
    <tr align="center">
      <td><%=poNum%></td>
      <td><%=invNum%></td>
      <td><%=invDate%></td>
      <td><%=shipToName%></td>
      <td><%=shipFromName%></td>
      <td><%=status%></td>
      <td><%=message%></td>
    </tr>
  <% } %>
  </table>
  </tr>
  <% } %>

</html:form>
</table>
</div>


