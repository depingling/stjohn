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
  <tr><td class="genericerror" align="center"><html:errors/></td></tr>
  <%
  }
  %>
  <tr>
  <td align="center">
   <jsp:include flush='true' page="erpIntegrationFunctionSelect.jsp"/>
  </td>
  </tr>

  <tr>
  <td align="center">
  <%
     if(theForm.getPendingToRemittances().size()>0) {
  %>
     <html:text name="theForm" property="batchNum" />
     <html:submit property="action" value="Upload Remittances"/>
  <% } else { %>
     No pending remittances found
  <% } %>
  </td>
  </tr>
  </table>
  <%
  RemittanceDataVector rDV = theForm.getProcessedToRemittances();
  String[] result = theForm.getRemittanceToResults();
  if(rDV !=null) {
  %>
    <table cellspacing="0" border="0" width="769" class="mainbody">
    <tr align="center">
      <td> <b> Remittance Id </b></td>
      <td> <b> Payment Ref Number</b></td>
      <td> <b> Payee Erp Account Number</b></td>
      <td> <b> Transaction Date</b></td>
      <td> <b> Message</b></td>
    </tr>
  <%
    for(int ii=0; ii<rDV.size(); ii++) {
      RemittanceData rD = (RemittanceData) rDV.get(ii);
      int rId = rD.getRemittanceId();
      String paymentRefNum = rD.getPaymentReferenceNumber();
      String payeeErpAccount= rD.getPayeeErpAccount();
      Date transactionDate = rD.getTransactionDate();
      String message = result[ii];
  %>
    <tr align="center">
      <td><%=rId%></td>
      <td><%=paymentRefNum %></td>
      <td><%=payeeErpAccount%></td>
      <td><%=transactionDate %></td>
      <td><%=message %></td>
    </tr>
  <% } %>
  </table>
  <% } %>

</html:form>
</table>
</div>


