<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.Date" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="java.math.BigDecimal" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="theForm" name="ORDER_BACKFILL_FORM" type="com.cleanwise.view.forms.OrderBackfillForm"/>
<% 
String portal = request.getParameter("portal");
if ( null == portal ) portal = "console";
String action = "/" + portal + "/orderBackfill.do";
%>

<div class="text">
<font color=red>
<html:errors/>
</font>
<table border="0" cellpadding="1" cellspacing="0" width="769" class="mainbody">
<html:form name="ORDER_BACKFILL_FORM" action="<%=action%>"
	type="com.cleanwise.view.forms.OrderBackfillForm">
  	<tr>
    <td colspan="6">
    <b>Web Order#:</b>
    <html:text name="ORDER_BACKFILL_FORM" property="stringOrderNumber" size="10" maxlength="10"/>
    <b>&nbsp;&nbsp;&nbsp;&nbsp;Erp Order#:</b>
    <html:text name="ORDER_BACKFILL_FORM" property="stringErpOrderNum" size="10" maxlength="10"/>
   	<html:submit property="action" value="Select"/>
    </td>
	</tr>
  	<tr>
    	<td colspan="2" class="mediumheader">Order Header Information</td>
		<td colspan="4" class="mediumheader">&nbsp;</td>
	</tr>

	<tr>
		<td colspan="6">&nbsp;
			<bean:define id="orderId" name="ORDER_BACKFILL_FORM" property="orderStatusDetail.orderDetail.orderId" />
		</td>
	</tr>
<%
 OrderStatusDescData orderStatusDesc = theForm.getOrderStatusDetail();
 OrderData order = orderStatusDesc.getOrderDetail();
 String orderNum=order.getOrderNum();
 if(orderNum==null) orderNum="";
 double subTotal = theForm.getSubTotal();
 int erpOrderNum = order.getErpOrderNum();
 double totalFreightCost = theForm.getTotalFreightCost();
 String requestPoNum = order.getRequestPoNum();
 if(requestPoNum==null) requestPoNum="";
 double totalTaxCost = theForm.getTotalTaxCost();
 String refOrderNum = order.getRefOrderNum();
 if(refOrderNum==null) refOrderNum="";
 double totalMiscCost = theForm.getTotalMiscCost();
 String accountErpNum = order.getAccountErpNum();
 if(accountErpNum==null) accountErpNum="";
 double totalAmount = theForm.getTotalAmount();
 String orderSiteName = order.getOrderSiteName();
 if(orderSiteName==null) orderSiteName="";
 String orderStatusCd = order.getOrderStatusCd();
 if(orderStatusCd==null) orderStatusCd="";
 String orderSourceCd = order.getOrderSourceCd();
 if(orderSourceCd==null) orderSourceCd="";
 String siteErpNum = order.getSiteErpNum();
 if(siteErpNum==null) siteErpNum="";
 String addBy = order.getAddBy();
 if(addBy==null) addBy="";
 Date originalOrderDate = order.getOriginalOrderDate();
 Date originalOrderTime = order.getOriginalOrderTime();
 String orderContactName = order.getOrderContactName();
 if(orderContactName==null) orderContactName="";
 String orderContactPhoneNum = order.getOrderContactPhoneNum();
 if(orderContactPhoneNum==null) orderContactPhoneNum="";
 String orderContactEmail = order.getOrderContactEmail();
 if(orderContactEmail==null) orderContactEmail="";
 int contractId = order.getContractId();
 String contractShortDesc = order.getContractShortDesc();
 if(contractShortDesc==null) contractShortDesc="";
 //------------------------------------------------------
 OrderStatusDescData orderStatusDescAfter = theForm.getOrderStatusDetailAfter();
 OrderData orderAfter = orderStatusDescAfter.getOrderDetail();
 String orderNumAfter=orderAfter.getOrderNum();
 if(orderNumAfter==null) orderNumAfter="";
 double subTotalAfter = theForm.getSubTotalAfter();
 int erpOrderNumAfter = orderAfter.getErpOrderNum();
 double totalFreightCostAfter = theForm.getTotalFreightCostAfter();
 String requestPoNumAfter = orderAfter.getRequestPoNum();
 if(requestPoNumAfter==null) requestPoNumAfter="";
 double totalTaxCostAfter = theForm.getTotalTaxCostAfter();
 String refOrderNumAfter = orderAfter.getRefOrderNum();
 if(refOrderNumAfter==null) refOrderNumAfter="";
 double totalMiscCostAfter = theForm.getTotalMiscCostAfter();
 String accountErpNumAfter = orderAfter.getAccountErpNum();
 if(accountErpNumAfter==null) accountErpNumAfter="";
 double totalAmountAfter = theForm.getTotalAmountAfter();
 String orderSiteNameAfter = orderAfter.getOrderSiteName();
 if(orderSiteNameAfter==null) orderSiteNameAfter="";
 String orderStatusCdAfter = orderAfter.getOrderStatusCd();
 if(orderStatusCdAfter==null) orderStatusCdAfter="";
 String orderSourceCdAfter = orderAfter.getOrderSourceCd();
 if(orderSourceCdAfter==null) orderSourceCdAfter="";
 String siteErpNumAfter = orderAfter.getSiteErpNum();
 if(siteErpNumAfter==null) siteErpNumAfter="";
 String addByAfter = orderAfter.getAddBy();
 if(addByAfter==null) addByAfter="";
 Date originalOrderDateAfter = orderAfter.getOriginalOrderDate();
 Date originalOrderTimeAfter = orderAfter.getOriginalOrderTime();
 String orderContactNameAfter = orderAfter.getOrderContactName();
 if(orderContactNameAfter==null) orderContactNameAfter="";
 String orderContactPhoneNumAfter = orderAfter.getOrderContactPhoneNum();
 if(orderContactPhoneNumAfter==null) orderContactPhoneNumAfter="";
 String orderContactEmailAfter = orderAfter.getOrderContactEmail();
 if(orderContactEmailAfter==null) orderContactEmailAfter="";
 int contractIdAfter = orderAfter.getContractId();
 String contractShortDescAfter = orderAfter.getContractShortDesc();
 if(contractShortDescAfter==null) contractShortDescAfter="";

 String regColor = "black";
 String errColor = "red";
 String color = regColor;
 double dif = 0;
%>

	<tr>
	  <td colspan="6">
      <table border="0" cellpadding="1" cellspacing="0" width="900" class="mainbody">
      <tr>
      <td><b>&nbsp;</b></td>
      <td><b>Before</b></td>
      <td><b>After</b></td>
      <td><b>&nbsp;</b></td>
      <td><b>Before</b></td>
      <td><b>After</b></td>
      </tr>
      <tr>
      <td><b>Web Order#:</b></td>
<% color = (orderNum.equals(orderNumAfter))?regColor:errColor; %>
      <td><font color="<%=color%>"><%=orderNum%></font></td>
      <td><font color="<%=color%>"><%=orderNumAfter%></font></td>
      <td><b>Sub-Total:</b></td>
<% dif= subTotal-subTotalAfter; color = (dif>-0.0001 && dif<0.0001)?regColor:errColor; %>
      <td><font color="<%=color%>">
		   	<i18n:formatCurrency value="<%=new BigDecimal(subTotal)%>" locale="<%=Locale.US%>"/>
      </font></td>
      <td><font color="<%=color%>">
		   	<i18n:formatCurrency value="<%=new BigDecimal(subTotalAfter)%>" locale="<%=Locale.US%>"/>
      </font></td>
	  <tr>
      <td><b>ERP Order#:</b></td>
<% color = (erpOrderNum==erpOrderNumAfter)?regColor:errColor; %>
      <td><font color="<%=color%>"><%=erpOrderNum%></font></td>
      <td><font color="<%=color%>"><%=erpOrderNumAfter%></font></td>
      <td><b>Freight:</b></td>
<% dif= totalFreightCost-totalFreightCostAfter; color = (dif>-0.0001 && dif<0.0001)?regColor:errColor; %>
      <td><font color="<%=color%>">
		   	<i18n:formatCurrency value="<%=new BigDecimal(totalFreightCost)%>" locale="<%=Locale.US%>"/>
      </font></td>
      <td><font color="<%=color%>">
		   	<i18n:formatCurrency value="<%=new BigDecimal(totalFreightCostAfter)%>" locale="<%=Locale.US%>"/>
      </font></td>
      </tr>
	  <tr>
      <td><b>Customer PO#:</b></td>
<% color = (requestPoNum.equals(requestPoNumAfter))?regColor:errColor; %>
      <td><font color="<%=color%>"><%=requestPoNum%></font></td>
      <td><font color="<%=color%>"><%=requestPoNumAfter%></font></td>
      <td><b>Tax:</b></td>
<% dif=totalTaxCost-totalTaxCostAfter; color = (dif>-0.0001 && dif<0.0001)?regColor:errColor; %>
      <td><font color="<%=color%>">
        	<i18n:formatCurrency value="<%=new BigDecimal(totalTaxCost)%>" locale="<%=Locale.US%>"/>
      </font></td>
      <td><font color="<%=color%>">
        	<i18n:formatCurrency value="<%=new BigDecimal(totalTaxCostAfter)%>" locale="<%=Locale.US%>"/>
      </font></td>
      </tr>
	  <tr>
      <td><b>Customer Requisition#:</b></td>
<% color = (refOrderNum.equals(refOrderNumAfter))?regColor:errColor; %>
      <td><font color="<%=color%>"><%=refOrderNum%></font></td>
      <td><font color="<%=color%>"><%=refOrderNumAfter%></font></td>
      <td><b>Handling:</b></td>
<% dif=totalMiscCost-totalMiscCostAfter; color = (dif>-0.0001 && dif<0.0001)?regColor:errColor; %>
      <td><font color="<%=color%>">
        	<i18n:formatCurrency value="<%=new BigDecimal(totalMiscCost)%>" locale="<%=Locale.US%>"/>
      </font></td>
      <td><font color="<%=color%>">
        	<i18n:formatCurrency value="<%=new BigDecimal(totalMiscCostAfter)%>" locale="<%=Locale.US%>"/>
      </font></td>
	  <tr>
      <td><b>Account Erp#:</b></td>
<% color = (accountErpNum.equals(accountErpNumAfter))?regColor:errColor; %>
      <td><font color="<%=color%>"><%=accountErpNum%></font></td>
      <td><font color="<%=color%>"><%=accountErpNum%></font></td>
      <td><b>Total</b></td>
<% dif=totalAmount-totalAmountAfter; color = (dif>-0.0001 && dif<0.0001)?regColor:errColor; %>
      <td><font color="<%=color%>">
        	<i18n:formatCurrency value="<%=new BigDecimal(totalAmount)%>" locale="<%=Locale.US%>"/>
      </font></td>
      <td><font color="<%=color%>">
        	<i18n:formatCurrency value="<%=new BigDecimal(totalAmountAfter)%>" locale="<%=Locale.US%>"/>
      </font></td>
      </tr>
	  <tr>
      <td><b>Site Name:</b></td>
<% color = (orderSiteName.equals(orderSiteNameAfter))?regColor:errColor; %>
      <td><font color="<%=color%>"><%=orderSiteName%></font></td>
      <td><font color="<%=color%>"><%=orderSiteNameAfter%></font></td>
      <td><b>Order Status</b></td>
<% color = (orderStatusCd.equals(orderStatusCdAfter))?regColor:errColor; %>
      <td><font color="<%=color%>"><%=orderStatusCd%></font></td>
      <td>
      	<html:select name="ORDER_BACKFILL_FORM" property="orderStatusDetailAfter.orderDetail.orderStatusCd" styleClass="text">
        <html:option value="<%=RefCodeNames.ORDER_STATUS_CD.CANCELLED%>"><%=RefCodeNames.ORDER_STATUS_CD.CANCELLED%></html:option>
        <html:option value="<%=RefCodeNames.ORDER_STATUS_CD.DUPLICATED%>"><%=RefCodeNames.ORDER_STATUS_CD.DUPLICATED%></html:option>
        <html:option value="<%=RefCodeNames.ORDER_STATUS_CD.PENDING_CONSOLIDATION%>"><%=RefCodeNames.ORDER_STATUS_CD.PENDING_CONSOLIDATION%></html:option>
        <html:option value="<%=RefCodeNames.ORDER_STATUS_CD.PENDING_DATE%>"><%=RefCodeNames.ORDER_STATUS_CD.PENDING_DATE%></html:option>
        <html:option value="<%=RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL%>"><%=RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL%></html:option>
        <html:option value="<%=RefCodeNames.ORDER_STATUS_CD.REJECTED%>"><%=RefCodeNames.ORDER_STATUS_CD.REJECTED%></html:option>
        <html:option value="<%=RefCodeNames.ORDER_STATUS_CD.ORDERED%>"><%=RefCodeNames.ORDER_STATUS_CD.ORDERED%></html:option>
        <html:option value="<%=RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW%>"><%=RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW%></html:option>
        <html:option value="<%=RefCodeNames.ORDER_STATUS_CD.INVOICED%>"><%=RefCodeNames.ORDER_STATUS_CD.INVOICED%></html:option>
        <html:option value="<%=RefCodeNames.ORDER_STATUS_CD.PROCESS_ERP_PO%>"><%=RefCodeNames.ORDER_STATUS_CD.PROCESS_ERP_PO%></html:option>
        <html:option value="<%=RefCodeNames.ORDER_STATUS_CD.ERP_REJECTED%>"><%=RefCodeNames.ORDER_STATUS_CD.ERP_REJECTED%></html:option>
        <html:option value="<%=RefCodeNames.ORDER_STATUS_CD.ERP_RELEASED%>"><%=RefCodeNames.ORDER_STATUS_CD.ERP_RELEASED%></html:option>
        <html:option value="<%=RefCodeNames.ORDER_STATUS_CD.ERP_RELEASED_PO_ERROR%>"><%=RefCodeNames.ORDER_STATUS_CD.ERP_RELEASED_PO_ERROR%></html:option>
        <html:option value="<%=RefCodeNames.ORDER_STATUS_CD.ERP_CANCELLED%>"><%=RefCodeNames.ORDER_STATUS_CD.ERP_CANCELLED%></html:option>
        </html:select>
      </td>
      </tr>
	  <tr>
      <td><b>Site Erp#:</b></td>
<% color = (siteErpNum.equals(siteErpNumAfter))?regColor:errColor; %>
      <td><font color="<%=color%>"><%=siteErpNum%></font></td>
      <td><font color="<%=color%>"><%=siteErpNumAfter%></font></td>
      <td><b>Method</b></td>
<% color = (orderSourceCd.equals(orderSourceCdAfter))?regColor:errColor; %>
      <td><font color="<%=color%>"><%=orderSourceCd%></font></td>
      <% if(theForm.getOrderStatusDetailAfter().getOrderDetail().getOrderId()>0) {%>
        <td><font color="<%=color%>"><%=orderSourceCdAfter%></font></td>
      <% } else { %>
        <td>
      	<html:select name="ORDER_BACKFILL_FORM" property="orderStatusDetailAfter.orderDetail.orderSourceCd" styleClass="text">
        <html:option value="<%=RefCodeNames.ORDER_SOURCE_CD.EDI_850%>"><%=RefCodeNames.ORDER_SOURCE_CD.EDI_850%></html:option>
        <html:option value="<%=RefCodeNames.ORDER_SOURCE_CD.FAX%>"><%=RefCodeNames.ORDER_SOURCE_CD.FAX%></html:option>
        <html:option value="<%=RefCodeNames.ORDER_SOURCE_CD.MAIL%>"><%=RefCodeNames.ORDER_SOURCE_CD.MAIL%></html:option>
        <html:option value="<%=RefCodeNames.ORDER_SOURCE_CD.TELEPHONE%>"><%=RefCodeNames.ORDER_SOURCE_CD.TELEPHONE%></html:option>
        <html:option value="<%=RefCodeNames.ORDER_SOURCE_CD.WEB%>"><%=RefCodeNames.ORDER_SOURCE_CD.WEB%></html:option>
        <html:option value="<%=RefCodeNames.ORDER_SOURCE_CD.LAW%>"><%=RefCodeNames.ORDER_SOURCE_CD.LAW%></html:option>
        <html:option value="<%=RefCodeNames.ORDER_SOURCE_CD.OTHER%>"><%=RefCodeNames.ORDER_SOURCE_CD.OTHER%></html:option>
        </html:select>
        </td>
      <% } %>
      </tr>
	  <tr>
      <td><b>Placed By:</b></td>
<% color = (addBy.equals(addByAfter))?regColor:errColor; %>
      <td><font color="<%=color%>"><%=addBy%></font></td>
      <td><font color="<%=color%>"><%=addByAfter%></font></td>
      <td><b>Date Ordered:</b></td>
<%
  color=errColor;
  if(originalOrderDate!= null && originalOrderDate.equals(originalOrderDateAfter)) 
     color = regColor;
%>
<% if(originalOrderDate!= null) { %>
      <td><font color="<%=color%>">
     		<i18n:formatDate value="<%=originalOrderDate%>" pattern="MM/dd/yyyy" locale="<%=Locale.US%>"/>&nbsp;
		    <i18n:formatDate value="<%=originalOrderTime%>" pattern="hh:mm aaa" locale="<%=Locale.US%>"/>
      </font></td>
<% } else { %>
      <td>&nbsp;</td>
<% } %>
<% if(originalOrderDateAfter!= null){ %>
      <td><font color="<%=color%>">
    		<i18n:formatDate value="<%=originalOrderDateAfter%>" pattern="MM/dd/yyyy" locale="<%=Locale.US%>"/>&nbsp;
		    <i18n:formatDate value="<%=originalOrderTimeAfter%>" pattern="hh:mm aaa" locale="<%=Locale.US%>"/>
      </font></td>
<% } else { %>
      <td>&nbsp;</td>
<% } %>
      </tr>
	  <tr>
      <td><b>Contact Name:</b></td>
<% color = (orderContactName.equals(orderContactNameAfter))?regColor:errColor; %>
      <td><font color="<%=color%>"><%=orderContactName%></font></td>
      <td><font color="<%=color%>"><%=orderContactNameAfter%></font></td>
      </tr>
	  <tr>
      <td><b>Contact Phone:</b></td>
<% color = (orderContactPhoneNum.equals(orderContactPhoneNumAfter))?regColor:errColor; %>
      <td><font color="<%=color%>"><%=orderContactPhoneNum%></font></td>
      <td><font color="<%=color%>"><%=orderContactPhoneNumAfter%></font></td>
      </tr>
	  <tr>
      <td><b>Contact Email:</b></td>
<% color = (orderContactEmail.equals(orderContactEmailAfter))?regColor:errColor; %>
      <td><font color="<%=color%>"><%=orderContactEmail%></font></td>
      <td><font color="<%=color%>"><%=orderContactEmailAfter%></font></td>
      </tr>
	  <tr>
      <td><b>Contract Id:</b></td>
<% color = (contractId==contractIdAfter)?regColor:errColor; %>
      <td><font color="<%=color%>"><%=contractId%></font></td>
      <td><font color="<%=color%>"><%=contractIdAfter%></font></td>
      </tr>
	  <tr>
      <td><b>Contract Name:</b></td>
<% color = (contractShortDesc.equals(contractShortDescAfter))?regColor:errColor; %>
      <td><font color="<%=color%>"><%=contractShortDesc%></font></td>
      <td><font color="<%=color%>"><%=contractShortDescAfter%></font></td>
      </tr>
      </table>
      </td>
    </tr>
    <jsp:include flush='true' page="orderBackfillAddress.jsp"/>
	<tr><td colspan="6">&nbsp;</td></tr>
    <!-- Consolidated Item Status -->
	<tr>
    <td colspan="6" align="center">
    <html:select name="ORDER_BACKFILL_FORM" property="allItemsStatus" styleClass="text"
    onchange="document.forms[0].submit();">
    <html:option value="">--    All Items Status    --</html:option>
    <html:option value="<%=RefCodeNames.ORDER_ITEM_STATUS_CD.CANCELLED%>"><%=RefCodeNames.ORDER_ITEM_STATUS_CD.CANCELLED%></html:option>
    <html:option value="<%=RefCodeNames.ORDER_ITEM_STATUS_CD.PENDING_ERP_PO%>"><%=RefCodeNames.ORDER_ITEM_STATUS_CD.PENDING_ERP_PO%></html:option>
    <html:option value="<%=RefCodeNames.ORDER_ITEM_STATUS_CD.PENDING_REVIEW%>"><%=RefCodeNames.ORDER_ITEM_STATUS_CD.PENDING_REVIEW%></html:option>
    <html:option value="<%=RefCodeNames.ORDER_ITEM_STATUS_CD.PENDING_FULFILLMENT%>"><%=RefCodeNames.ORDER_ITEM_STATUS_CD.PENDING_FULFILLMENT%></html:option>
    <html:option value="<%=RefCodeNames.ORDER_ITEM_STATUS_CD.SENT_TO_DISTRIBUTOR%>"><%=RefCodeNames.ORDER_ITEM_STATUS_CD.SENT_TO_DISTRIBUTOR%></html:option>
    <html:option value="<%=RefCodeNames.ORDER_ITEM_STATUS_CD.SENT_TO_DISTRIBUTOR_FAILED%>"><%=RefCodeNames.ORDER_ITEM_STATUS_CD.SENT_TO_DISTRIBUTOR_FAILED%></html:option>
    <html:option value="<%=RefCodeNames.ORDER_ITEM_STATUS_CD.INVOICED%>"><%=RefCodeNames.ORDER_ITEM_STATUS_CD.INVOICED%></html:option>
    </html:select>
    </td>
    </tr>
	<tr>
    <td colspan="6"><jsp:include flush='true' page="orderBackfillItems.jsp"/></td>
    </tr>
	<tr>
    <% int orderIdAfter = theForm.getOrderStatusDetailAfter().getOrderDetail().getOrderId();%>
    <html:hidden name="ORDER_BACKFILL_FORM" property="orderId" value="<%=\"\"+orderIdAfter%>"/>
    <%if(erpOrderNumAfter>0) { %>
    <%if(orderIdAfter>0) { %>
    <td colspan="6" align="center"><html:submit property="action" value="Update"/></td>
    <% } else { %>
    <td colspan="6" align="center"><html:submit property="action" value="Create"/></td>
    <% } %>
    <% } %>
    </tr>

</html:form>
</table>

</div>
