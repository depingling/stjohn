<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<% String storeDir=ClwCustomizer.getStoreDir(); %>
<bean:define id="theForm" name="STORE_ADMIN_COST_CENTER_FORM" 
                 type="com.cleanwise.view.forms.StoreCostCenterMgrForm"/>
<% 
  CostCenterData costCenterD = theForm.getCostCenterDetail();
  int costCenterId = costCenterD.getCostCenterId();;
  String action = theForm.getAction();
%>



<script language="JavaScript1.2">

</script>

<div  bgcolor="#cccccc">


<table ID=696 border="0"cellpadding="2" cellspacing="1" width="769"  class="mainbody">
<html:form styleId="697" name="STORE_ADMIN_COSTCENTER_FORM"  action="/storeportal/costcenterdet.do"  
         scope="session" type="com.cleanwise.view.forms.StoreCatalogMgrForm">
   <html:hidden property="costCenterId" value="<%= String.valueOf(costCenterId) %>" />
   <tr>
   <td><b>CostCenter ID:</b></td>
   <td><%=costCenterId%></td>
   <td><b>Cost Center Name:</b> </td>
   <td>
     <html:text size="30" maxlength="30" name="STORE_ADMIN_COST_CENTER_FORM" 
         property="costCenterDetail.shortDesc" /><span class="reqind">*</span>
   </td>
   </tr>
   <tr>
   <td><b>Cost Center Type:</b></td>
   <td><html:select name="STORE_ADMIN_COST_CENTER_FORM" 
         property="costCenterDetail.costCenterTypeCd"> 
       <html:option value=""><app:storeMessage  key="admin.select"/></html:option>
       <html:option value="<%=RefCodeNames.BUDGET_TYPE_CD.SITE_BUDGET%>">
                           <%=RefCodeNames.BUDGET_TYPE_CD.SITE_BUDGET%></html:option>
       <html:option value="<%=RefCodeNames.BUDGET_TYPE_CD.ACCOUNT_BUDGET%>">
                           <%=RefCodeNames.BUDGET_TYPE_CD.ACCOUNT_BUDGET%></html:option>
       <html:option value="<%=RefCodeNames.BUDGET_TYPE_CD.ACCOUNT_WORK_ORDER_BUDGET%>">
                           <%=RefCodeNames.BUDGET_TYPE_CD.ACCOUNT_WORK_ORDER_BUDGET%></html:option>
       <html:option value="<%=RefCodeNames.BUDGET_TYPE_CD.SITE_WORK_ORDER_BUDGET%>">
                           <%=RefCodeNames.BUDGET_TYPE_CD.SITE_WORK_ORDER_BUDGET%></html:option>
       </html:select><span class="reqind">*</span>
   </td>
   <td><b>Cost Center Code:</b> </td>
   <td>
     <html:text size="30" maxlength="30" name="STORE_ADMIN_COST_CENTER_FORM" 
         property="costCenterDetail.costCenterCode" />
   </td>
   </tr>       

   <tr>
   <td><b>Cost Center Tax Type:</b></td>
   <td><html:select name="STORE_ADMIN_COST_CENTER_FORM" 
         property="costCenterDetail.costCenterTaxType"> 
       <html:option value=""><app:storeMessage  key="admin.select"/></html:option>
       <html:option value="<%=RefCodeNames.COST_CENTER_TAX_TYPE.DONT_ALLOCATE_SALES_TAX%>">
                           <%=RefCodeNames.COST_CENTER_TAX_TYPE.DONT_ALLOCATE_SALES_TAX%></html:option>
       <html:option value="<%=RefCodeNames.COST_CENTER_TAX_TYPE.MASTER_SALES_TAX_COST_CENTER%>">
                           <%=RefCodeNames.COST_CENTER_TAX_TYPE.MASTER_SALES_TAX_COST_CENTER%></html:option>
       <html:option value="<%=RefCodeNames.COST_CENTER_TAX_TYPE.ALLOCATE_PRODUCT_SALES_TAX%>">
                           <%=RefCodeNames.COST_CENTER_TAX_TYPE.ALLOCATE_PRODUCT_SALES_TAX%></html:option>
       </html:select><span class="reqind">*</span>
   </td>

   <td><b>Cost Center Status:</b></td>
   <td><html:select name="STORE_ADMIN_COST_CENTER_FORM" 
         property="costCenterDetail.costCenterStatusCd"> 
       <html:option value=""><app:storeMessage  key="admin.select"/></html:option>
       <html:option value="<%=RefCodeNames.COST_CENTER_STATUS_CD.ACTIVE%>">
                           <%=RefCodeNames.COST_CENTER_STATUS_CD.ACTIVE%></html:option>
       <html:option value="<%=RefCodeNames.COST_CENTER_STATUS_CD.INACTIVE%>">
                           <%=RefCodeNames.COST_CENTER_STATUS_CD.INACTIVE%></html:option>
       </html:select><span class="reqind">*</span>
   </td>
   </tr>
   <tr>
   <td>&nbsp;</td>
   <td>&nbsp;</td>
   <td><b>Allocate Freight:</b></td>
   <td>
       <html:radio name="STORE_ADMIN_COST_CENTER_FORM" 
              property="costCenterDetail.allocateFreight" value="false"/>No 
     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
       <html:radio name="STORE_ADMIN_COST_CENTER_FORM" 
              property="costCenterDetail.allocateFreight" value="true"/>Yes 
   </td>
   </tr>  
   <tr>
   <td>&nbsp;</td>
   <td>&nbsp;</td>
   <td><b>Allocate Discount:</b></td>
   <td>
       <html:radio name="STORE_ADMIN_COST_CENTER_FORM"
              property="costCenterDetail.allocateDiscount" value="false"/>No
     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
       <html:radio name="STORE_ADMIN_COST_CENTER_FORM"
              property="costCenterDetail.allocateDiscount" value="true"/>Yes 
   </td>
   </tr>
   <tr>
   <td>&nbsp;</td>
   <td>&nbsp;</td>
   <td><b>No Budget:</b></td>
   <td>
       <html:radio name="STORE_ADMIN_COST_CENTER_FORM" 
              property="costCenterDetail.noBudget" value="true"/>True 
     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
       <html:radio name="STORE_ADMIN_COST_CENTER_FORM" 
              property="costCenterDetail.noBudget" value="false"/>False 
   </td>
   </tr>       
     
   <tr>
      <td colspan="4" align="center">
      <html:submit property="action" value="Save"/>
   </tr>


</html:form>  
</div>
