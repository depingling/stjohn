<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

   <b>Erp Integration Function</b>
   <html:hidden property="funcitonChange" value="" />
   <html:select name="ERP_INTEGRATION_FORM" property="function"  onchange="document.forms[0].funcitonChange.value='change'; document.forms[0].submit()">
   <html:option value="<%=\"\"+com.cleanwise.view.forms.ErpIntegrationForm.CUST_ORDER_TO_ERP%>">
       Send Customer Order To Erp</html:option>
   <html:option value="<%=\"\"+com.cleanwise.view.forms.ErpIntegrationForm.DIST_PO_FROM_ERP%>">
       Pickup Released Purchase Orders </html:option>
   <html:option value="<%=\"\"+com.cleanwise.view.forms.ErpIntegrationForm.DIST_INVOICE_TO_ERP%>">
       Send Distributor Invoices To Erp</html:option>
   <html:option value="<%=\"\"+com.cleanwise.view.forms.ErpIntegrationForm.CUST_INVOICE_FROM_ERP%>">
       Pickup Customer Invoices From Erp</html:option>
   <html:option value="<%=\"\"+com.cleanwise.view.forms.ErpIntegrationForm.REMITTANCE_TO_ERP%>">
       Send Remittance Data To Erp</html:option>
   </html:select>
