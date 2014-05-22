<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="java.util.Locale" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<div class="text">
  <table width="769" border="0" cellspacing="0" cellpadding="0" 
   class="mainbody">
  <tr>
  <td><b>Store&nbsp;Id:</b></td><td><bean:write name="Store.id"/></td>
  <td><b>Store&nbsp;Name:</b></td><td><bean:write name="Store.name"/></td>
  </tr>
  <tr class="results"> <td><b>SKU Workflow:</b></td>
       <td colspan="2">
<span class="wkrule">
If SKU is empty, or SKU starts with (CW)
<br>
Then
<br>
Forward the order for approval.
</span>
       </td>
<td>
<form method=GET action="storeWorkflow.do">
<input type="hidden" name="bus_entity_id" value=<bean:write name="Store.id"/> >
<input type="hidden" name="action" value="update_cw_sku_setting">
<b>Enabled: </b> 
<bean:define id="wf" name="STORE_DETAIL_FORM" 
  property="skuWorkflowFlag" scope="session" type="java.lang.Boolean"/>

<logic:equal name="wf" value="true">
<input type=checkbox name="cw_sku_workflow_flag" value="on" checked >
</logic:equal>

<logic:equal name="wf" value="false">
<input type=checkbox name="cw_sku_workflow_flag" value="on" >
</logic:equal>

<input type="submit" value="Update">
</form>
</td>
  </tr>
</table>

</div>


