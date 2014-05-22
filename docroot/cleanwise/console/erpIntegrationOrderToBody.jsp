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
     if(theForm.getApprovedOrders().size()>0) {
  %>
	Orders pending upload: <%=theForm.getApprovedOrders().size()%>
     <html:submit property="action" value="Upload Orders"/>
  <% } else { %>
     No unprocessed approved orders found
  <% } %>
  </td>
  </tr>
  </table>
  <%
  OrderDataVector orderDV = theForm.getProcessedToOrders();
  String[] result = theForm.getOrderToResults();
  if(orderDV!=null) {
  %>
    <table cellspacing="0" border="0" width="769" class="mainbody">
    <tr align="center">
      <td> <b> Order Id </b></td>
      <td> <b> Erp Order Num</b></td>
      <td> <b> Web Order Num</b></td>
      <td> <b> Order Date</b></td>
      <td> <b> Site Name</b></td>
      <td> <b> Status</b></td>
      <td> <b> Message</b></td>
    </tr>
  <%
    for(int ii=0; ii<orderDV.size(); ii++) {
      OrderData oD = (OrderData) orderDV.get(ii);
      int orderId = oD.getOrderId();
      int erpOrderNum = oD.getErpOrderNum();
      String webOrderNum = oD.getOrderNum();
      Date orderDate = oD.getOriginalOrderDate();
      String shipToName = oD.getOrderSiteName();
      String status = oD.getOrderStatusCd();
      String message = result[ii];
  %>
    <tr align="center">
      <td><%=orderId%></td>
      <td><%=erpOrderNum%></td>
      <td><%=webOrderNum%></td>
      <td><%=orderDate%></td>
      <td><%=shipToName%></td>
      <td><%=status%></td>
      <td><%=message%></td>
    </tr>
  <% } %>
  </table>
  <% } %>

</html:form>
</table>
</div>


