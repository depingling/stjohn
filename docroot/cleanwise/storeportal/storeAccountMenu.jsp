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

<bean:define id="theForm" name="STORE_ACCOUNT_DETAIL_FORM"
  type="com.cleanwise.view.forms.StoreAccountMgrDetailForm"/>
<%
  int acctId = theForm.getIntId();
%>
<table ID=488 width="<%=Constants.TABLEWIDTH%>">
  <tr bgcolor="#000000">
    <% if(acctId>0) { %>
<td>
<app:renderStatefulButton link="storeAccountDetail.do?action=accountdetail"
 name="Detail" tabClassOff="tbar" tabClassOn="tbarOn"
 linkClassOff="tbar" linkClassOn="tbarOn"
  contains="accountdet"/>

<app:renderStatefulButton link="storeAccountBillTos.do?action=accountBillTos"
 name="BillTos" tabClassOff="tbar" tabClassOn="tbarOn"
 linkClassOff="tbar" linkClassOn="tbarOn"
  contains="storeAccountBillTos"/>

 <app:renderStatefulButton link="storeAccountConfiguration.do"
 name="Configuration" tabClassOff="tbar" tabClassOn="tbarOn"
 linkClassOff="tbar" linkClassOn="tbarOn"
  contains="storeAccountConfiguration"/>

<app:renderStatefulButton link="storeAccountSiteData.do"
 name="Site Data" tabClassOff="tbar" tabClassOn="tbarOn"
 linkClassOff="tbar" linkClassOn="tbarOn"
  contains="storeAccountSiteData"/>


 <app:renderStatefulButton link="storeAccountWorkflow.do"
 name="Workflows" tabClassOff="tbar" tabClassOn="tbarOn"
 linkClassOff="tbar" linkClassOn="tbarOn"
  contains="storeAccountWorkflow"/>


 <app:renderStatefulButton link="storeInventoryMgr.do"
 name="Inventory Items" tabClassOff="tbar" tabClassOn="tbarOn"
 linkClassOff="tbar" linkClassOn="tbarOn"
  contains="storeInventoryMgr"/>


</td></tr>


<tr bgcolor="#000000"><td>



 <app:renderStatefulButton link="storeAccountUIConfig.do"
 name="UI" tabClassOff="tbar" tabClassOn="tbarOn"
 linkClassOff="tbar" linkClassOn="tbarOn"
  contains="storeAccountUIConfig"/>

 <app:renderStatefulButton link="storePipelineMgr.do"
 name="Order Processing" tabClassOff="tbar" tabClassOn="tbarOn"
 linkClassOff="tbar" linkClassOn="tbarOn"
  contains="storePipelineMgr"/>



<app:renderStatefulButton link="storeDeliverySchedules.do?action=deliverySchedInit"
 name="Site Delivery Schedules" tabClassOff="tbar" tabClassOn="tbarOn"
 linkClassOff="tbar" linkClassOn="tbarOn"
  contains="storeDeliverySchedules"/>

<app:renderStatefulButton
 link="accountShoppingRestrictions.do?action=getShoppingRestrictions"
 name="Shopping Control" tabClassOff="tbar" tabClassOn="tbarOn"
  linkClassOff="tbar" linkClassOn="tbarOn"
  contains="accountShoppingRestrictions"/>

<app:renderStatefulButton
 link="accountFiscalCalendar.do?action=list"
 name="Fiscal Calendar" tabClassOff="tbar" tabClassOn="tbarOn"
  linkClassOff="tbar" linkClassOn="tbarOn"
  contains="accountFiscalCalendar"/>

  <app:renderStatefulButton link="accountmgrNote.do?action=init"
  name="Notes" tabClassOff="tbar" tabClassOn="tbarOn"
  linkClassOff="tbar" linkClassOn="tbarOn"
  contains="accountmgrNote"/>
    
</td>
</tr>
<tr bgcolor="#000000"><td colspan="2">
<app:renderStatefulButton link="accountmgrContactUsTopic.do"
  name="Contact Us Topics" tabClassOff="tbar" tabClassOn="tbarOn"
  linkClassOff="tbar" linkClassOn="tbarOn"
  contains="accountmgrContactUsTopic"/>

<app:renderStatefulButton link="accountmgrProductUITemplate.do"
  name="Product UI Template" tabClassOff="tbar" tabClassOn="tbarOn"
  linkClassOff="tbar" linkClassOn="tbarOn"
  contains="accountmgrProductUITemplate"/>

<app:renderStatefulButton link="accountmgrServiceFee.do"
  name="Service Fee" tabClassOff="tbar" tabClassOn="tbarOn"
  linkClassOff="tbar" linkClassOn="tbarOn"
  contains="accountmgrServiceFee"/>
  
<app:renderStatefulButton link="accountMgrCheckoutData.do"
  name="Checkout Data" tabClassOff="tbar" tabClassOn="tbarOn"
  linkClassOff="tbar" linkClassOn="tbarOn"
  contains="accountmgrCheckoutData"/>

</td>
</tr>
    <% }else{ %>

<td class="tbartext">&nbsp;</td>
	<% } %>	
	</tr>

</table>
