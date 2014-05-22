<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="java.util.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="theForm" name="STORE_ADMIN_COST_CENTER_FORM" type="com.cleanwise.view.forms.StoreCostCenterMgrForm"/>
<% 
    CostCenterData costCenterD = theForm.getCostCenterDetail();
    int costCenterId = (costCenterD==null)?0:costCenterD.getCostCenterId();
    String costCenterTypeCd = (costCenterD==null)?"":costCenterD.getCostCenterTypeCd();
%>
<table ID=698 width="<%=Constants.TABLEWIDTH%>">
  <tr bgcolor="#000000">
    <% if(costCenterId>0) { %>
    <app:renderStatefulButton link="costcenterdet.do?action=returnToDetailPage" name="Detail" tabClassOff="tbar" 
				tabClassOn="tbarOn" linkClassOff="tbar" linkClassOn="tbarOn" contains="costcenterdet"/>
      <%if(!RefCodeNames.BUDGET_TYPE_CD.ACCOUNT_WORK_ORDER_BUDGET.equals(costCenterTypeCd) &&
              !RefCodeNames.BUDGET_TYPE_CD.SITE_WORK_ORDER_BUDGET.equals(costCenterTypeCd)) { %>
      <app:renderStatefulButton link="costcenterconfig.do?action=configInit" name="Configuration" tabClassOff="tbar"
                                tabClassOn="tbarOn" linkClassOff="tbar" linkClassOn="tbarOn" contains="costcenterconfig"/>
      <%}%>
      <%if(RefCodeNames.BUDGET_TYPE_CD.ACCOUNT_WORK_ORDER_BUDGET.equals(costCenterTypeCd) ||
              RefCodeNames.BUDGET_TYPE_CD.SITE_WORK_ORDER_BUDGET.equals(costCenterTypeCd)) {  %>
      <app:renderStatefulButton link="costcenterbudget.do?action=init" name="Work Order Budgets" tabClassOff="tbar"
                                tabClassOn="tbarOn" linkClassOff="tbar" linkClassOn="tbarOn" contains="costcenterbudget"/>
      <%}%>
    <% }else{ %>
        <td class="tbartext">&nbsp;</td>
    <% } %>	
	</tr>
</table>


