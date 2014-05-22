<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<app:checkLogon/>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<%
	String feedField =  (String)request.getParameter("feedField");
	if(null == feedField) {
		feedField = new String("");
	}
	String feedDesc =  (String)request.getParameter("feedDesc");
	if(null == feedDesc) {
		feedDesc = new String("");
	}
	String feedSku =  (String)request.getParameter("feedSku");
	if(null == feedSku) {
		feedSku = new String("");
	}	
%>	

<script language="JavaScript1.2">
<!--

function passIdAndName(id, name, sku) {
  var feedBackFieldName = document.forms[0].feedField.value;
  var feedDesc = document.forms[0].feedDesc.value;
  var feedSku = document.forms[0].feedSku.value;

  if(feedBackFieldName && ""!= feedBackFieldName) {
    window.opener.document.forms[0].elements[feedBackFieldName].value = id;
  }
  if(feedDesc && ""!= feedDesc) {
    window.opener.document.forms[0].elements[feedDesc].value = unescape(name.replace(/\+/g, ' '));
  }
  if(feedSku && ""!= feedSku) {
    window.opener.document.forms[0].elements[feedSku].value = unescape(sku.replace(/\+/g, ' '));
  }
  
  self.close();
}

//-->
</script>


<html:html>

<head>
<title>Operations Console Home: Order Items</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="../externals/styles.css">
</head>

<body bgcolor="#FFFFFF">

<jsp:include flush='true' page="ui/loginInfo.jsp"/>
	
<div class="text">
<font color=red>
<html:errors/>
</font>

<table border="0" cellpadding="1" cellspacing="0" width="769" class="mainbody">
<html:form name="ORDER_OP_ITEM_SEARCH_FORM" action="/console/orderItemLocate.do"
	type="com.cleanwise.view.forms.OrderOpItemSearchForm">

  	<tr> 
    	<td colspan="2" class="mediumheader">Order Header Information</td>
		<td colspan="2" class="mediumheader">&nbsp;</td>
	</tr>		
		
	
	<tr>
		<td colspan="4">&nbsp;
			<bean:define id="orderId" name="ORDER_OP_ITEM_SEARCH_FORM" property="orderDesc.orderDetail.orderId" />	
			<bean:define id="erpPoNum" name="ORDER_OP_ITEM_SEARCH_FORM" property="erpPoNum" />	
			<html:hidden name="ORDER_OP_ITEM_SEARCH_FORM" property="orderId" />	
			<html:hidden name="ORDER_OP_ITEM_SEARCH_FORM" property="erpPoNum" />	
			<input type="hidden" name="feedField" value="<%=feedField%>">
			<input type="hidden" name="feedDesc" value="<%=feedDesc%>">
			<input type="hidden" name="feedSku" value="<%=feedSku%>">			
		</td>
	</tr>	

	<tr valign="top">
		<td colspan="2">
			<table width="100%">
				<tr valign="top">
			    	<td><b>Web Order#:</b></td>
			        <td><bean:write name="ORDER_OP_ITEM_SEARCH_FORM" property="orderDesc.orderDetail.orderNum" filter="true"/>
					</td>
				</tr>				
				<tr>
			    	<td><b>Customer PO#:</b></td>
			        <td><bean:write name="ORDER_OP_ITEM_SEARCH_FORM" property="orderDesc.orderDetail.requestPoNum" filter="true"/>
					</td>				
				</tr>
				<tr>
			    	<td><b>ERP Order#:</b></td>
			        <td><bean:write name="ORDER_OP_ITEM_SEARCH_FORM" property="orderDesc.orderDetail.erpOrderNum" filter="true"/>
					</td>
				</tr>
				<tr>
			        <td><b>Method:</b></td>
			        <td><bean:write name="ORDER_OP_ITEM_SEARCH_FORM" property="orderDesc.orderDetail.orderSourceCd" filter="true"/> 
			        </td>		
				</tr>

				<tr>
			    	<td><b>Date Ordered:</b></td>
			        <td>
					<logic:present name="ORDER_OP_ITEM_SEARCH_FORM" property="orderDesc.orderDetail.originalOrderDate" >
						<bean:define id="orderdate" name="ORDER_OP_ITEM_SEARCH_FORM" property="orderDesc.orderDetail.originalOrderDate" type="java.util.Date"/>
			    		<i18n:formatDate value="<%=orderdate%>" pattern="MM/dd/yyyy" locale="<%=Locale.US%>"/>&nbsp;  		
					  <logic:present name="ORDER_OP_ITEM_SEARCH_FORM" property="orderDesc.orderDetail.originalOrderTime" >
						<bean:define id="ordertime" name="ORDER_OP_ITEM_SEARCH_FORM" property="orderDesc.orderDetail.originalOrderTime" type="java.util.Date"/>
			    		<i18n:formatDate value="<%=ordertime%>" pattern="hh:mm aaa" locale="<%=Locale.US%>"/>  								
					  </logic:present>							
					</logic:present>	
					</td>
				</tr>
			</table>		
		</td>
	
		<td colspan="2">
			<table width="100%">
				<tr valign="top">
			        <td><b>Account Name:</b></td>
			        <td><bean:write name="ORDER_OP_ITEM_SEARCH_FORM" property="orderDesc.accountName" filter="true"/> 
			        </td>		
				</tr>
				<tr>
			        <td><b>Site Name:</b></td>
			        <td><bean:write name="ORDER_OP_ITEM_SEARCH_FORM" property="orderDesc.orderDetail.orderSiteName" filter="true"/> 
					</td>
				</tr>	
				<tr>
			    	<td><b>Ship From Name:</b></td>
			        <td><bean:write name="ORDER_OP_ITEM_SEARCH_FORM" property="orderDesc.shipFromName" filter="true"/> 
					</td>
				</tr>
				<tr>
			        <td><b>Order Status:</b></td>
			        <td><bean:write name="ORDER_OP_ITEM_SEARCH_FORM" property="orderDesc.orderDetail.orderStatusCd" filter="true"/> 
			        </td>		
			    </tr>
				<tr>
					<td><b>Placed By:</b></td>
			        <td><bean:write name="ORDER_OP_ITEM_SEARCH_FORM" property="orderDesc.orderDetail.addBy" filter="true"/> 
					</td>		
				</tr>				
			</table>		
		</td>
				
	</tr>
				
	<tr><td colspan="4">&nbsp;</td></tr>	
		
	<tr>
		<td colspan="4">


<table width="769" border="0" class="results">
<tr>
<td colspan="14"><span class="mediumheader"><b>Order Item List:</b></span>
<bean:size id="itemCount" name="ORDER_OP_ITEM_SEARCH_FORM" property="orderItemDescList" />
<!-- <bean:write name="itemCount" />  -->
</td>
</tr>

<logic:present name="ORDER_OP_ITEM_SEARCH_FORM" property="orderItemDescList">

<tr><td colspan="14" class="mainbody">&nbsp;</td></tr>

<tr>
<td><b>Line&nbsp;#</b></td>
<td class="resultscolumna"><b><a href="orderItemLocate.do?action=sortitems&sortField=distOrderNum&orderId=<%=orderId%>&erpPoNum=<%=erpPoNum%>&feedField=<%=feedField%>&feedDesc=<%=feedDesc%>&feedSku=<%=feedSku%>">Dist Order#</b></td>
<td><b><a href="orderItemLocate.do?action=sortitems&sortField=erpPoNum&orderId=<%=orderId%>&erpPoNum=<%=erpPoNum%>&feedField=<%=feedField%>&feedDesc=<%=feedDesc%>&feedSku=<%=feedSku%>">Outbound PO#</b></td>
<td class="resultscolumna"><b><a href="orderItemLocate.do?action=sortitems&sortField=cwSKU&orderId=<%=orderId%>&erpPoNum=<%=erpPoNum%>&feedField=<%=feedField%>&feedDesc=<%=feedDesc%>&feedSku=<%=feedSku%>">CW SKU#</b></td>
<td><b><a href="orderItemLocate.do?action=sortitems&sortField=distSKU&orderId=<%=orderId%>&erpPoNum=<%=erpPoNum%>&feedField=<%=feedField%>&feedDesc=<%=feedDesc%>&feedSku=<%=feedSku%>">Dist SKU#</b></td>
<td class="resultscolumna"><b>Dist Name</b></td>
<td><b><a href="orderItemLocate.do?action=sortitems&sortField=name&orderId=<%=orderId%>&erpPoNum=<%=erpPoNum%>&feedField=<%=feedField%>&feedDesc=<%=feedDesc%>&feedSku=<%=feedSku%>">Product Name</b></td>
<td class="resultscolumna"><b>UOM</b></td>
<td><b>Pack</b></td>
<td class="resultscolumna"><b>Item Size</b></td>
<td><b>Customer Price</b></td>
<td class="resultscolumna"><b>CW Cost</b></td>
<td><b>Qty</b></td>
<td class="resultscolumna"><b>Date</b></td>
</tr>

<logic:iterate id="itemele" indexId="i" name="ORDER_OP_ITEM_SEARCH_FORM" property="orderItemDescList" scope="session" type="com.cleanwise.service.api.value.OrderItemDescData">
 <bean:define id="key"  name="itemele" property="orderItem.orderItemId"/>
 <bean:define id="sku" name="itemele" property="orderItem.itemSkuNum" type="java.lang.Integer"/>
 <bean:define id="name" name="itemele" property="orderItem.itemShortDesc" type="java.lang.String"/>

<tr><td colspan="14" ><hr></td></tr>

<tr>
<td><bean:write name="itemele" property="orderItem.orderLineNum"/>&nbsp;</td>
<td class="resultscolumna"><bean:write name="itemele" property="orderItem.distOrderNum"/>&nbsp;</td>
<td><bean:write name="itemele" property="orderItem.erpPoNum"/>&nbsp;</td>
<td class="resultscolumna"><bean:write name="itemele" property="orderItem.itemSkuNum"/>&nbsp;</td>
<td><bean:write name="itemele" property="orderItem.distItemSkuNum"/>&nbsp;</td>
<td class="resultscolumna"><bean:write name="itemele" property="distName"/>&nbsp;</td>
<td>
    <% String onClick = new String("return passIdAndName('"+key+"', '"+ java.net.URLEncoder.encode(name) +"', '"+ java.net.URLEncoder.encode(sku.toString()) +"');");%>    <a href="javascript:void(0);" onclick="<%=onClick%>">
	<bean:write name="itemele" property="orderItem.itemShortDesc"/>
	</a>&nbsp;
</td>
<td class="resultscolumna"><bean:write name="itemele" property="orderItem.itemUom"/>&nbsp;</td>
<td><bean:write name="itemele" property="orderItem.itemPack"/>&nbsp;</td>
<td class="resultscolumna"><bean:write name="itemele" property="orderItem.itemSize"/>&nbsp;</td>
<td><bean:write name="itemele" property="orderItem.custContractPrice"/>&nbsp;</td>
<td class="resultscolumna"><bean:write name="itemele" property="orderItem.distItemCost"/>&nbsp;</td>
<td><bean:write name="itemele" property="orderItem.totalQuantityOrdered"/>&nbsp;</td>
<td class="resultscolumna">
<logic:present name="itemele" property="orderItem.erpPoDate">
	<bean:define id="erppodate" name="itemele" property="orderItem.erpPoDate"/>
   	<i18n:formatDate value="<%=erppodate%>" pattern="MM/dd/yyyy" locale="<%=Locale.US%>"/>  		
</logic:present>	
</td>		
</tr>		


		
</logic:iterate>

</logic:present>

</table>

		</td>
	</tr>

	
</html:form>	
</table>

</div>

<jsp:include flush='true' page="ui/consoleFooter.jsp"/>

</body>
</html:html>
