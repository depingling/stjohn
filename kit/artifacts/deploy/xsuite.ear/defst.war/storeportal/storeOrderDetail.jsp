<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page import="com.cleanwise.view.i18n.*" %>
<%@ page import="com.cleanwise.view.logic.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<% boolean clwSwitch=ClwCustomizer.getClwSwitch(); %>
<app:checkLogon/>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="theForm" name="STORE_ORDER_DETAIL_FORM" type="com.cleanwise.view.forms.StoreOrderDetailForm"/>
<bean:define id="userType" type="java.lang.String" name="ApplicationUser" property="user.userTypeCd"/>
<bean:define id="userStore" type="java.lang.String" name="ApplicationUser" property="userStore.storeType.value"/>
<%
  OrderStatusDescData orderStatusDetail = theForm.getOrderStatusDetail();
  OrderData orderD = orderStatusDetail.getOrderDetail();

  String orderStatus = orderD.getOrderStatusCd(); if(orderStatus==null) orderStatus="";
  String orderSource = orderD.getOrderSourceCd(); if(orderSource==null) orderSource="";

  boolean consolidatedOrderFl =
   (RefCodeNames.ORDER_TYPE_CD.CONSOLIDATED.equals(orderD.getOrderTypeCd()))?
   true:false;

  ReplacedOrderViewVector replacedOrders = orderStatusDetail.getReplacedOrders();
  if(replacedOrders==null)  replacedOrders = new ReplacedOrderViewVector();

  Integer accountId = new Integer(theForm.getOrderStatusDetail().getAccountId());

  Integer siteId = (theForm.getOrderStatusDetail().getSiteAddress()!=null)?
  new Integer(theForm.getOrderStatusDetail().getSiteAddress().getBusEntityId()):
  new Integer(theForm.getOrderStatusDetail().getOrderDetail().getSiteId());

  //Control Flags
  //boolean  = false;
  boolean adminFlag = true;
  boolean erpInactive = false;
  boolean erpActiveAllowChange = false;
  boolean nonEdiOrder = false;
  boolean ediAllowChange = false;
//  boolean fullControl = ("Full Control".endsWith(request.getParameter("action")))?true:false;
  boolean fullControl = true;

  boolean showCancel = theForm.getShowCancelButton();

//  if ((RefCodeNames.USER_TYPE_CD.ADMINISTRATOR.equals(userType)||
//       RefCodeNames.USER_TYPE_CD.CRC_MANAGER.equals(userType)) &&
//      (null != accountId) && (0 != accountId.intValue())
//  ){adminFlag = true;}

  if (StoreOrderLogic.isOrderStatusValid(orderStatus,fullControl))
   { erpInactive = true;}

  if (
    RefCodeNames.ORDER_STATUS_CD.ERP_RELEASED.equals(orderStatus) ||
    RefCodeNames.ORDER_STATUS_CD.PROCESS_ERP_PO.equals(orderStatus)
  ) { erpActiveAllowChange = true;}

  if (!RefCodeNames.ORDER_SOURCE_CD.EDI_850.equals(orderSource)
     ) { nonEdiOrder = true;}

  ediAllowChange = theForm.getOrderStatusDetail().getAllowModifFl();

  boolean allowWhenNoErp = adminFlag && erpInactive;
  boolean allowWhenNoErpNoEdi = adminFlag && erpInactive && (nonEdiOrder || fullControl);
  boolean allowWhenErpEdi = adminFlag && ((erpActiveAllowChange && ediAllowChange) || fullControl);
%>

<bean:define id="contractId" name="STORE_ORDER_DETAIL_FORM" property="orderStatusDetail.orderDetail.contractId" type="Integer"/>


<script language="JavaScript1.2">
<!--
function popLocate(pLoc, parm) {
  var loc = pLoc + ".jsp?parm=" + parm;
  if(parm != "print"){
    locatewin = window.open(loc,"Locate", "menubar=no,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
  }else{
    locatewin = window.open(loc,"Locate", "menubar=no,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
  }
  locatewin.focus();
  return false;
}

function popLocate2(pLoc, name, pDesc) {

var loc = pLoc + ".do?feedField=" + name + "&feedDesc=" + pDesc;
locatewin = window.open(loc,"Locate", "menubar=no,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
locatewin.focus();

return false;
}

function popPrint(pLoc) {
  var loc = pLoc;
  locatewin = window.open(loc,"Locate", "menubar=yes,resizable=yes,scrollbars=yes,toolbar=yes,status=yes,height=500,width=769,left=100,top=165");
  locatewin.focus();
  return false;
}

function popItemLocate(pLoc, name, pDesc, pSku) {
 var contractId = <%=contractId.toString()%>;
 var siteId = <%=siteId.toString()%>;
 var loc = pLoc + ".do?feedField=" + name + "&feedDesc=" + pDesc + "&feedSku=" + pSku + "&contractid=" + contractId + "&siteid=" + siteId;
 locatewin = window.open(loc,"Locate", "menubar=no,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
 locatewin.focus();

 return false;
}

function popAssetLocate(pLoc, pName, pSiteId, pFeedDesc) {

 var contractId = <%=contractId.toString()%>;
 var loc = pLoc + ".do?feedField=" + pName + "&siteId=" + pSiteId + "&feedDesc=" +  pFeedDesc + "&contractid=" + contractId;
 locatewin = window.open(loc,"Locate", "menubar=no,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
 locatewin.focus();

 return false;
}

function popServiceLocate(pLoc, name,newAssetIdEl,assetId, userId,siteId, pDesc) {
 var contractId = <%=contractId.toString()%>;
 var newAsstIdVal= document.forms[0].elements[newAssetIdEl].value
 if(newAsstIdVal!="")
 {
  assetId=newAsstIdVal;
 }
 var loc = pLoc + ".do?feedField=" + name+ "&amp;siteid=" + siteId + "&amp;assetid=" + assetId + "&amp;custuserid=" + userId+ "&amp;feedDesc=" +  pDesc + "&amp;contractid=" +contractId;
 locatewin = window.open(loc,"Locate", "menubar=no,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
 locatewin.focus();

 return false;
}

function popItemCostLocate(pLoc, name, ind, pSku, pDistErpNum) {
var accountId = <%=accountId.toString()%>;
<% if (allowWhenErpEdi) { %>
var sku = document.forms[0].elements["orderItemDesc[" + ind + "].itemSkuNumS"].value;
if(sku=='' || sku=='undefined') sku = pSku;
var dist = document.forms[0].elements["orderItemDesc[" + ind + "].distIdS"].value;
<% } else { %>
var sku = pSku;
var dist = '';
<% } %>
var loc = pLoc + ".do?feedField=" + name + "&amp;itemSku=" + sku + "&amp;distId=" + dist +"&amp;distErpNum=" + pDistErpNum +"&amp;accountId=" + accountId;
var name = "Locate";
locatewin = window.open(loc,name, "menubar=yes,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
locatewin.focus();

return false;
}


function popLink(pLoc) {
  var loc = pLoc;
  locatewin = window.open(loc,"Note", "menubar=no,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
  locatewin.focus();
  return false;
}

function popUpdate(pLoc, parm) {
  var loc = pLoc + parm;
  locatewin = window.open(loc,"Update", "menubar=no,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
  locatewin.focus();
  return false;
}

function myalert() {
        if ("" == document.forms[0].elements['newContractIdS'].value) {
                return confirm("You didn't input the new contract ID, do you want to use the existing one?");
        }
        else {
                return true;
        }
}


function SetChecked(val) {
 dml=document.forms[0];
 len = dml.elements.length;
 var i=0;
 for( i=0 ; i<len ; i++) {
   if (dml.elements[i].name=='selectItems') {
     dml.elements[i].checked=val;
   }
 }
}

function SetAndSubmit(val){
	var orderItemId = document.getElementById("orderItemIdToView");
	orderItemId.value = val;
	orderItemId.form.submit();
}

//-->
</script>

<html:html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="../externals/styles.css">
</head>

<body bgcolor="#FFFFFF">

<jsp:include flush='true' page="locateStoreSite.jsp">
        <jsp:param name="jspFormAction"  value="/storeportal/storeOrderDetail.do" />
        <jsp:param name="jspFormName"  value="STORE_ORDER_DETAIL_FORM" />
 <jsp:param name="jspSubmitIdent"          value="" />
 <jsp:param name="jspReturnFilterProperty"  value="siteFilter" />
</jsp:include>

<% if ( theForm.getResultMessage() != null &&
        theForm.getResultMessage().length() > 0 ) { %>
<table ID=1060 width="700">
<tr><td class="mediumheader">
<%=theForm.getResultMessage()%>
</td></tr></table>
<%
}

theForm.setResultMessage("");

%>

<html:form styleId="1061" name="STORE_ORDER_DETAIL_FORM" action="/storeportal/storeOrderDetail.do"
        type="com.cleanwise.view.forms.StoreOrderDetailForm">
        
        <html:hidden property="orderItemIdToView" />

<div class="text">

<table ID=1062 border="0" cellpadding="1" cellspacing="0" width="769" class="mainbody"> <%-- BEGIN Header info --%>
<tr><td class="mediumheader">
<% if(consolidatedOrderFl) { %>
  Consolidated Order
<% } else { %>
  Order Header Information
<% } %>
</td></tr>
<tr><td>&nbsp;</td></tr>

<bean:define id="orderId" name="STORE_ORDER_DETAIL_FORM"
  property="orderStatusDetail.orderDetail.orderId" />

<tr valign="top">
 <td>
<table ID=1063 > <%-- BEGIN Order header 1 --%>
<tr valign="top">
<td><b>Web Order#:</b></td>
<td><bean:write name="STORE_ORDER_DETAIL_FORM" property="orderStatusDetail.orderDetail.orderNum" filter="true"/>
</td>
</tr>

        <logic:present name="STORE_ORDER_DETAIL_FORM" property="orderStatusDetail.refOrder">
                <tr>
                        <td><b>Reference Order Num:</b></td>
                        <td><bean:write name="STORE_ORDER_DETAIL_FORM" property="orderStatusDetail.refOrder.orderNum"/></td>
                </tr>
        </logic:present>

<tr>
<td><b>Customer PO#:</b></td>
<td><bean:write name="STORE_ORDER_DETAIL_FORM" property="orderStatusDetail.orderDetail.requestPoNum" filter="true"/>
</td>
</tr>
<logic:present name="STORE_ORDER_DETAIL_FORM" property="orderStatusDetail.orderCreditCardData">
<tr>
<td class="order_status_addr"><b>Customer Used Credit Card</b></td>
<td></td>
</tr>
</logic:present>
<% if(clwSwitch) { %>
<tr>
<td><b>ERP Order#:</b></td>
<td><bean:write name="STORE_ORDER_DETAIL_FORM" property="orderStatusDetail.orderDetail.erpOrderNum" filter="true"/>
</td>
</tr>
<% } %>
<tr>
<td><b>Customer Req/Ref Order#</b></td>
<td><bean:write name="STORE_ORDER_DETAIL_FORM" property="orderStatusDetail.orderDetail.refOrderNum" filter="true"/>
</td>
</tr>
<tr>
<td><b>Method:</b></td>
<td>
<bean:write name="STORE_ORDER_DETAIL_FORM" property="orderStatusDetail.orderDetail.orderSourceCd" filter="true"/>
</td>
</tr>
<tr>
<td><b>Contact Name:</b></td>
<td><bean:write name="STORE_ORDER_DETAIL_FORM" property="orderStatusDetail.orderDetail.orderContactName" filter="true"/>
</td>
</tr>
<tr>
<td><b>Contact Phone#:</b></td>
<td><bean:write name="STORE_ORDER_DETAIL_FORM" property="orderStatusDetail.orderDetail.orderContactPhoneNum" filter="true"/>
</td>
</tr>
<tr>
<td><b>Contact Email:</b></td>
<td><bean:write name="STORE_ORDER_DETAIL_FORM" property="orderStatusDetail.orderDetail.orderContactEmail" filter="true"/>
</td>
</tr>


<tr>
<td><b>Date Ordered:</b></td>
<td>
<logic:present name="STORE_ORDER_DETAIL_FORM" property="orderStatusDetail.orderDetail.revisedOrderDate" >
<bean:define id="orderdate" name="STORE_ORDER_DETAIL_FORM" property="orderStatusDetail.orderDetail.revisedOrderDate" type="java.util.Date"/>
<i18n:formatDate value="<%=orderdate%>" pattern="MM/dd/yyyy" locale="<%=Locale.US%>"/>&nbsp;
<logic:present name="STORE_ORDER_DETAIL_FORM" property="orderStatusDetail.orderDetail.revisedOrderTime" >
<bean:define id="ordertime" name="STORE_ORDER_DETAIL_FORM" property="orderStatusDetail.orderDetail.revisedOrderTime" type="java.util.Date"/>
<i18n:formatDate value="<%=ordertime%>" pattern="hh:mm aaa"/>
</logic:present>
</logic:present>
<logic:notPresent name="STORE_ORDER_DETAIL_FORM" property="orderStatusDetail.orderDetail.revisedOrderDate" >
<logic:present name="STORE_ORDER_DETAIL_FORM" property="orderStatusDetail.orderDetail.originalOrderDate" >
<bean:define id="orderdate" name="STORE_ORDER_DETAIL_FORM" property="orderStatusDetail.orderDetail.originalOrderDate" type="java.util.Date"/>
<i18n:formatDate value="<%=orderdate%>" pattern="MM/dd/yyyy" locale="<%=Locale.US%>"/>&nbsp;
<logic:present name="STORE_ORDER_DETAIL_FORM" property="orderStatusDetail.orderDetail.originalOrderTime" >
<bean:define id="ordertime" name="STORE_ORDER_DETAIL_FORM" property="orderStatusDetail.orderDetail.originalOrderTime" type="java.util.Date"/>
<i18n:formatDate value="<%=ordertime%>" pattern="hh:mm aaa"/>
</logic:present>
</logic:present>
</logic:notPresent>
</td>
</tr>

<tr>
<td><b>New Order Date:</b><br>(mm/dd/yyyy)</td>
<td><html:text name="STORE_ORDER_DETAIL_FORM" property="newOrderDate"
              styleClass="text" size="12" maxlength="10"/>
</td>
</tr>


<tr>
<td><b>Contract ID:</b></td>
<td><bean:write name="STORE_ORDER_DETAIL_FORM" property="orderStatusDetail.orderDetail.contractId" filter="true"/>
</td>
</tr>
<tr>
<td><b>Ship From Name:</b></td>
<td><bean:write name="STORE_ORDER_DETAIL_FORM" property="orderStatusDetail.shipFromName" filter="true"/>
</td>
</tr>
<%  if (orderStatus.equals(RefCodeNames.ORDER_STATUS_CD.PENDING_ORDER_REVIEW)||
        orderStatus.equals(RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW)) { %>
<tr>
<td><b>Site Id:</b></td>
<td><nobr>
<html:text size='10' property="siteId" />
<html:submit style="width:80px;" property="action" value="Locate Site"/></nobr></td>
</tr>
<%} %>
</table> <%-- END Order header 1 --%>
</td>

<td class="order_status_addr">

<table ID=1064> <%-- BEGIN Order header 2 --%>
<tr valign="top">
<td><b>Account Name:</b></td>
<td><bean:write name="STORE_ORDER_DETAIL_FORM" property="orderStatusDetail.accountName" filter="true"/>
</td>
</tr>
<tr>
<td><b>Site Name:</b></td>
<td><bean:write name="STORE_ORDER_DETAIL_FORM" property="orderStatusDetail.orderDetail.orderSiteName" filter="true"/>
</td>
</tr>
<tr>
<td><b>Shipping Address:</b></td>
<td><bean:write name="STORE_ORDER_DETAIL_FORM" property="orderStatusDetail.shipTo.address1" filter="true"/>
</td>
</tr>
<tr>
<td><b>&nbsp;</b></td>
<td><bean:write name="STORE_ORDER_DETAIL_FORM" property="orderStatusDetail.shipTo.address2" filter="true"/>
</td>
</tr>
<tr>
<td><b>&nbsp;</b></td>
<td><bean:write name="STORE_ORDER_DETAIL_FORM" property="orderStatusDetail.shipTo.address3" filter="true"/>
</td>
</tr>
<tr>
<td><b>City:</b></td>
<td><bean:write name="STORE_ORDER_DETAIL_FORM" property="orderStatusDetail.shipTo.city" filter="true"/>
</td>
</tr>
<tr>
<td><b>State:</b></td>
<td><bean:write name="STORE_ORDER_DETAIL_FORM" property="orderStatusDetail.shipTo.stateProvinceCd" filter="true"/>
</td>
</tr>
<tr>
<td><b>ZIP Code:</b></td>
<td><bean:write name="STORE_ORDER_DETAIL_FORM" property="orderStatusDetail.shipTo.postalCode" filter="true"/>
</td>
</tr>

</table> <%-- END Order header 2--%>
</td>


<td>
<table ID=1065> <%-- BEGIN Order header 3--%>
<%--tr>
<td><b>Order Status:</b></td>
<td><bean:write name="STORE_ORDER_DETAIL_FORM" property="orderStatusDetail.orderDetail.orderStatusCd" filter="true"/>
</td>
</tr--%>
<tr>
<td><b>Placed By:</b></td>
<td><bean:write name="STORE_ORDER_DETAIL_FORM" property="orderStatusDetail.orderDetail.addBy" filter="true"/>
</td>
</tr>

<%
String pallowEdits = "false";
if (allowWhenNoErp || allowWhenErpEdi) {
  pallowEdits = "true";
}
%>


<jsp:include flush='true' page="f_storeOrderCharges.jsp">
  <jsp:param name="allowEdits" value="<%=pallowEdits%>" />
  <jsp:param name="taxreadonly" value="<%=Boolean.TRUE%>" />
</jsp:include>


<tr>
<td colspan="2"><hr style="color:#003366;"></td>
</tr>

<tr>
<td><b>Order Status:</b></td>
<td>
  <html:select  name="STORE_ORDER_DETAIL_FORM" property="orderStatus" size="1" styleClass="text" style="width:150px;">
    <html:optionsCollection  name="STORE_ORDER_DETAIL_FORM" property="orderStatusList"/>
  </html:select>
</td>
</tr>

<tr>
<td><b>Workflow Ind:</b></td>
<td>
  <html:select name="STORE_ORDER_DETAIL_FORM" property="workflowInd" size="1" styleClass="text" style="width:150px;">
    <html:optionsCollection  name="STORE_ORDER_DETAIL_FORM" property="workflowIndList"/>
  </html:select>
</td>
</tr>

<tr>
<td colspan="2"><hr style="color:#003366;"></td>
</tr>

<tr>
<td><b>Apply Budget:</b></td>
<td>
<html:checkbox name="STORE_ORDER_DETAIL_FORM" property="applyBudget"></html:checkbox>
</td>
</tr>

<tr>
<td><b>Rebill Order:</b></td>
<td>
<html:checkbox name="STORE_ORDER_DETAIL_FORM" property="reBillOrder"></html:checkbox>
</td>
</tr>

</table> <%-- END Order header 3--%>
</td>
</tr>
<tr>

<logic:present	name="STORE_ORDER_DETAIL_FORM" property="orderStatusDetail.orderMetaData">
<td class="order_status_addr">495
  <%@ include file="f_storeOrderMetaData.jsp" %>
</td>
</logic:present>

<td class="order_status_addr">500
<logic:present	name="STORE_ORDER_DETAIL_FORM" property="orderStatusDetail.customerShipTo">
<jsp:include flush='true' page="f_storeOrderCustShip.jsp"/>
</logic:present>
</td>

</tr>

<tr>
<td><b>Customer Comments:</b></td>
</tr>
<tr>
<td colspan="3">
<logic:present name="STORE_ORDER_DETAIL_FORM" property="orderStatusDetail.orderDetail.comments">
<bean:write name="STORE_ORDER_DETAIL_FORM" property="orderStatusDetail.orderDetail.comments" filter="true"/>
</logic:present>
</td>
</tr>


<tr>
<td><b>Notes:</b></td>
</tr>

<tr>
<td colspan="3"><bean:write name="STORE_ORDER_DETAIL_FORM" property="orderPropertyDetail.value" filter="true"/>
<% 
   OrderPropertyData op = theForm.getOrderPropertyDetail();
   if(op!=null) {
     java.util.Date adddate = op.getAddDate();
     if(adddate!=null) {
%>     
(added by <bean:write name="STORE_ORDER_DETAIL_FORM" property="orderPropertyDetail.addBy" />
 on 
<i18n:formatDate value="<%=adddate%>" pattern="MM/dd/yyyy" locale="<%=Locale.US%>"/>)
<% }} %>
</td>
</tr>

<tr>
<td colspan="3" align="center">
<input type="button" onclick="popLink('storeOrderNote.do?action=view&orderid=<%=orderId%>');" value="View All Order Detail Notes" >
&nbsp;<input type="button" onclick="popLink('storeOrderNote.do?action=add&orderid=<%=orderId%>');" value="Add Order Detail Note" >
&nbsp;<input type="button" onclick="popPrint('storeOrderDetailPrint.do?action=view&amp;id=<%=orderId%>');" value="Print Order Detail">

<%  if (orderStatus.equals(RefCodeNames.ORDER_STATUS_CD.PENDING_ORDER_REVIEW)||
        orderStatus.equals(RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW)||
        orderStatus.equals(RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL)||
        orderStatus.equals(RefCodeNames.ORDER_STATUS_CD.PENDING_CONSOLIDATION)||
        orderStatus.equals(RefCodeNames.ORDER_STATUS_CD.PENDING_DATE)||
        fullControl) { %>
&nbsp;<input type="button" onclick="popPrint('storeOrderTempPo.do?action=view&amp;id=<%=orderId%>');" value="Print Temporary PO">
<% } %>
<%-- &nbsp;<input type="button" onclick="popLink('storeOrderDetail.do?action=siteNotes');" value="Site Notes" />  --%>
</td>
</tr>

</table> <%-- END Header info --%>

<table ID=1066> <%-- BEGIN Order Items --%>
<tr>
<td colspan="2">

<%
int colNum = 20;
if (allowWhenNoErpNoEdi || allowWhenErpEdi) {
  colNum = 30;
}
%>

<logic:notEqual  name="STORE_ORDER_DETAIL_FORM" property="simpleServiceFlag" value="<%=Boolean.TRUE.toString()%>">
<table ID=1067 width="900" border="0" class="results"> <%-- BEGIN Items --%>
<tr>
<td colspan="<%=colNum%>"><span class="mediumheader"><b>Order Item Status:</b></span>
<bean:size id="itemCount" name="STORE_ORDER_DETAIL_FORM" property="orderItemDescList" />
</td>
</tr>

<logic:present name="STORE_ORDER_DETAIL_FORM" property="orderItemDescList">

<tr><td colspan="<%=colNum%>">&nbsp;</td></tr>
<tr><td colspan="<%=colNum%>">
<table ID=1068 align="center">
<tr>
<td colspan="2" class="mediumheader"><b>Cumulative Summary:</b></td>
<td class="resultscolumna"><b>Date</b></td>
<td><b>Ordered</b></td>
<td class="resultscolumna"><b>Accepted</b></td>
<td><b>Shipped</b></td>
<td class="resultscolumna"><b>Backordered</b></td>
<td><b>Substituted</b></td>
<td class="resultscolumna"><b>Invoiced</b></td>
<td><b>Returned</b></td>
</tr>
<tr>
<td colspan="2">&nbsp;</td>
<td align="center" class="resultscolumna">
<logic:present name="STORE_ORDER_DETAIL_FORM" property="lastDate" >
<% 
   op = theForm.getOrderPropertyDetail();
   if(op!=null) {
     java.util.Date adddate = op.getAddDate();
     if(adddate!=null) {
%>     
(added by <bean:write name="STORE_ORDER_DETAIL_FORM" property="orderPropertyDetail.addBy" />
 on 
<i18n:formatDate value="<%=adddate%>" pattern="MM/dd/yyyy" locale="<%=Locale.US%>"/>)
<% }} %>
</logic:present>
</td>
<td align="center"><bean:write name="STORE_ORDER_DETAIL_FORM" property="orderedNum"/>&nbsp;</td>
<td align="center" class="resultscolumna"><bean:write name="STORE_ORDER_DETAIL_FORM" property="acceptedNum"/>&nbsp;</td>
<td align="center"><bean:write name="STORE_ORDER_DETAIL_FORM" property="shippedNum"/>&nbsp;</td>
<td align="center" class="resultscolumna"><bean:write name="STORE_ORDER_DETAIL_FORM" property="backorderedNum"/>&nbsp;</td>
<td align="center"><bean:write name="STORE_ORDER_DETAIL_FORM" property="substitutedNum"/>&nbsp;</td>
<td align="center" class="resultscolumna"><bean:write name="STORE_ORDER_DETAIL_FORM" property="invoicedNum"/>&nbsp;</td>
<td align="center"><bean:write name="STORE_ORDER_DETAIL_FORM" property="returnedNum"/>&nbsp;</td>
</tr>
</table>
</td></tr>
<tr><td colspan="<%=colNum%>" class="mainbody">&nbsp;</td></tr>


<tr>
<% int colInd = 1;
   int qtyColInd = 1;
%>
<% String style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><b>L<br>i<br>n<br>e<br>#</b></td>

<% if(clwSwitch){ %>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><b><a ID=1069 href="storeOrderDetail.do?action=sortitems&sortField=outboundPoNum">Outbound PO#</b></td>
<% } %>
<% if(clwSwitch){ %>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><b><a ID=1070 href="storeOrderDetail.do?action=sortitems&sortField=cwSKU">CW SKU#</b></td>
<% } else { %>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><b><a ID=1071 href="storeOrderDetail.do?action=sortitems&sortField=cwSKU">SKU#</b></td>
<% } %>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><b><a ID=1072 href="storeOrderDetail.do?action=sortitems&sortField=distSKU">Dist SKU#</b></td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><b>Dist Name</b></td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><b><a ID=1073 href="storeOrderDetail.do?action=sortitems&sortField=name">Product Name</b></td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><b>UOM</b></td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><b>Pack</b></td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><b>Item Size</b></td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><b>Customer Price</b></td>
<%if (RefCodeNames.STORE_TYPE_CD.MLA.equals(userStore)) {%>
  <% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
  <td class="<%=style%>"><b>CW Cost</b></td>
<% } %>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><b>Tax Exempt</b></td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><b>Tax Amount</b></td>
<%qtyColInd = colInd;
  style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb";%>
<td class="<%=style%>"><b>Qty</b></td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><b>Status</b></td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><b>PO Status</b></td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><b>Item Note</b></td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><b>Add Item Note<hr/>Resale</b></td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><b>PO and Ship Date</b></td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><b>Target Ship Date</b></td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><b>Open Line Status</b></td>

<%--
<% if (allowWhenNoErpNoEdi || allowWhenErpEdi) { %>

<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>">
<a ID=1074 href="javascript:SetChecked(1)">[Check&nbsp;All]</a>
<br>
<a ID=1075 href="javascript:SetChecked(0)">[&nbsp;Clear]</a>
<br>
<b>Select for cancel</b>
</td>
<% }  --%>
<%
  int nbColumns = colInd;
%>

</tr>
<logic:iterate id="itemele" indexId="i"
  name="STORE_ORDER_DETAIL_FORM" property="orderItemDescList"
  type="com.cleanwise.service.api.value.OrderItemDescData">

 <bean:define id="key"  name="itemele" property="orderItem.orderItemId"/>
 <bean:define id="qty" name="itemele" property="orderItem.totalQuantityOrdered" type="Integer"/>

<% String linkHref = new String("javascript:SetAndSubmit(" +key +")");%>
<tr><td colspan=25><hr></td></tr>
<tr>
<% colInd=1; %>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><bean:write name="itemele" property="orderItem.orderLineNum"/>&nbsp;</td>

<% if(clwSwitch) { %>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<logic:present  name="itemele" property="orderItem.outboundPoNum">
    <td class="<%=style%>"><bean:write name="itemele" property="orderItem.outboundPoNum"/>&nbsp;</td>
</logic:present>
<logic:notPresent  name="itemele" property="orderItem.outboundPoNum">
    <td class="<%=style%>">N/A&nbsp;</td>
</logic:notPresent>
<% } %>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><bean:write name="itemele" property="orderItem.itemSkuNum"/>&nbsp;</td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><bean:write name="itemele" property="orderItem.distItemSkuNum"/>&nbsp;</td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>">
<bean:write name="itemele" property="distName"/>&nbsp;
<%
if (itemele.getOrderItem()!= null &&
    itemele.getOrderItem().getFreightHandlerId() > 0 ) {
%>
<div class="order_shipper">
<%= theForm.getOrderStatusDetail().getShipViaName
      (itemele.getOrderItem().getFreightHandlerId()) %>
</div>

<% } %>
</td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>

<td class="<%=style%>">
<% if (itemele.getOrderItem()!= null &&
       itemele.getOrderItem().getItemShortDesc() != null &&
       itemele.getOrderItem().getItemShortDesc().length() > 0 )
{
%>
<a ID=1076 href="<%=linkHref%>" >
<bean:write name="itemele" property="orderItem.itemShortDesc"/></a>
<%
}
%>
&nbsp; </td>

<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><bean:write name="itemele" property="orderItem.itemUom"/>&nbsp;</td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><bean:write name="itemele" property="orderItem.itemPack"/>&nbsp;</td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><bean:write name="itemele" property="orderItem.itemSize"/>&nbsp;</td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><bean:write name="itemele" property="orderItem.custContractPrice"/>&nbsp;</td>

<%if (RefCodeNames.STORE_TYPE_CD.MLA.equals(userStore)) {%>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><bean:write name="itemele" property="orderItem.distItemCost"/>&nbsp;</td>
<%}%>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>">&nbsp;</td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><bean:write name="itemele" property="orderItem.taxAmount"/></td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><bean:write name="itemele" property="orderItem.totalQuantityOrdered"/>&nbsp;</td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>">
<logic:present name="itemele" property="orderItem.orderItemStatusCd" >
        <bean:write name="itemele" property="orderItem.orderItemStatusCd"/>&nbsp;
</logic:present>
<logic:notPresent name="itemele" property="orderItem.orderItemStatusCd" >
        Ordered&nbsp;
</logic:notPresent>
</td>

<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>">
<logic:present name="itemele" property="purchaseOrderData" >
        <bean:write name="itemele" property="purchaseOrderData.purchaseOrderStatusCd"/>
</logic:present>
&nbsp;
</td>

<logic:equal name="itemele" property="hasNote" value="true">
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><input type="button" class="smallbutton" onclick="popLink('storeOrderNote.do?action=view&type=item&itemid=<%=key%>');" value="View"></td>
</logic:equal>
<logic:equal name="itemele" property="hasNote" value="false">
  <% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
  <td class="<%=style%>">&nbsp;</td>
</logic:equal>

<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>">
        <logic:notEqual name="itemele" property="orderItem.orderItemId" value="0">
                <input type="button" class="smallbutton" onclick="popLink('storeOrderNote.do?action=add&type=item&itemid=<%=key%>');" value="Add">
        </logic:notEqual>
</td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>">
<logic:present name="itemele" property="orderItem.erpPoDate">
        <bean:define id="erppodate" name="itemele" property="orderItem.erpPoDate"/>
        <i18n:formatDate value="<%=erppodate%>" pattern="MM/dd/yyyy" locale="<%=Locale.US%>"/>
</logic:present>
<logic:notPresent name="itemele" property="orderItem.erpPoDate">
&nbsp;
</logic:notPresent>
<% java.util.Date delivery = itemele.getDeliveryDate();
  if(delivery!=null) {
%>


<% java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM/dd/yyyy"); %>
<%=sdf.format(delivery)%>
<% } %>
</td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>">
<logic:present name="itemele" property="orderItem.targetShipDate">
        <bean:define id="dte" name="itemele" property="orderItem.targetShipDate"/>
        <i18n:formatDate value="<%=dte%>" pattern="MM/dd/yyyy" locale="<%=Locale.US%>"/>
</logic:present>
<logic:notPresent name="itemele" property="orderItem.targetShipDate">
        &nbsp;
</logic:notPresent>
</td>

<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><bean:write name="itemele" property="openLineStatusCd"/></td>
<%-- if (allowWhenNoErpNoEdi || allowWhenErpEdi) { %>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>">
        <logic:notEqual name="itemele" property="orderItem.orderItemId" value="0">

<html:multibox property="selectItems">
<bean:write name="itemele" property="orderItem.orderItemId"/>
</html:multibox>

        </logic:notEqual>
</td>
<% } --%>

</tr>

<% if (allowWhenNoErpNoEdi || allowWhenErpEdi) { %>
<!-- the input fields for each item when no integration necessary -->
<!-- and the input fields for each item when erp and may be edi integration is necessary -->
<tr valign="top">
<% colInd=1; %>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>">&nbsp;</td>
<% if(clwSwitch) { %>
  <% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
  <td class="<%=style%>">&nbsp;</td>
<% } %>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<% if (allowWhenErpEdi) { %>
<td class="<%=style%>"><html:text styleClass="textreadonly" size="10" property='<%= "orderItemDesc[" + i + "].itemSkuNumS" %>' readonly="true" />
<html:hidden property='<%= "orderItemDesc[" + i + "].itemIdS" %>'/>
  <br><input type="button"
  onclick="popItemLocate('../adminportal/contractItemLocate', 'orderItemDesc[<%=i%>].itemIdS', '', 'orderItemDesc[<%=i%>].itemSkuNumS');"
  value="Locate Item" class="smallbutton">
        &nbsp;</td>
<%}else{%>
<td class="<%=style%>">&nbsp;</td>
<%}%>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>">&nbsp;</td>
<% if (allowWhenErpEdi) { %>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><html:text styleClass="textreadonly" size="13" property='<%= "orderItemDesc[" + i + "].newDistName" %>' readonly="true" />
<html:hidden property='<%= "orderItemDesc[" + i + "].distIdS" %>'/><br>
 <input type="button"
 onclick="popLocate2('../adminportal/distlocate', 'orderItemDesc[<%=i%>].distIdS', 'orderItemDesc[<%=i%>].newDistName');"
 value="Locate Dist" class="smallbutton">
        &nbsp;</td>
<%}else{%>
<td class="<%=style%>">&nbsp;</td>
<%}%>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>">&nbsp;</td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>">&nbsp;</td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>">&nbsp;</td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>">&nbsp;</td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>">&nbsp;
<%if (!RefCodeNames.STORE_TYPE_CD.MLA.equals(userStore)) {%>
<html:text styleClass="text" size="5" maxlength="16" property='<%= "orderItemDesc[" + i + "].itemPriceS" %>'/>
<%}%>
</td>
<%if (RefCodeNames.STORE_TYPE_CD.MLA.equals(userStore)) {%>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>">
  <html:text styleClass="text" size="5" maxlength="16" property='<%= "orderItemDesc[" + i + "].cwCostS" %>'/>

    <bean:define id="itemSkuNum" name="itemele" property="orderItem.itemSkuNum"/>
    <%
    String distErpNum = "-";
    if (itemele.getOrderItem()!=null && itemele.getOrderItem().getDistErpNum() != null)
    {
    distErpNum = itemele.getOrderItem().getDistErpNum();
    }
    %>

    <input type="button"
    onclick="popItemCostLocate('../adminportal/costlocate',
    'orderItemDesc[<%=i%>].cwCostS',
    '<%=i%>',
    '<%=itemSkuNum%>',
    '<%=distErpNum%>');"
    value="Get Cost" class="smallbutton">
    &nbsp;
</td>
<%}%>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>">
    <html:checkbox value="on" property='<%= "orderItemDesc[" + i + "].taxExempt" %>'/>
</td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>">&nbsp;</td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>">
<% if(!consolidatedOrderFl) { %>
<html:text styleClass="text" size="3" maxlength="5" property='<%= "orderItemDesc[" + i + "].itemQuantityS" %>' />
<% } %>
&nbsp;
</td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>">
<html:select  property='<%= "orderItemDesc[" + i + "].itemStatus" %>' size="1" styleClass="text" style="width:150px;">
  <html:optionsCollection  name="STORE_ORDER_DETAIL_FORM" property="orderItemStatusList"/>
</html:select>
&nbsp;</td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>">
<logic:present name="itemele" property="purchaseOrderData" >
  <html:select  property='<%= "orderItemDesc[" + i + "].poItemStatus" %>' size="1" styleClass="text" style="width:150px;">
    <html:optionsCollection  name="STORE_ORDER_DETAIL_FORM" property="poItemStatusList"/>
  </html:select>
</logic:present>
&nbsp;</td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>">&nbsp;</td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>">
  <html:checkbox value="on" property='<%= "orderItemDesc[" + i + "].reSale" %>'/>
</td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>">&nbsp;</td>
</tr>
<% }  %>

<!-- Consolidated order items. Begin -->
<%
if(consolidatedOrderFl) {
  if(replacedOrders!=null) {
    for(Iterator iter=replacedOrders.iterator(); iter.hasNext();) {
      ReplacedOrderView roVw = (ReplacedOrderView) iter.next();
      ReplacedOrderItemViewVector roiVwV = roVw.getItems();
      if(roiVwV!=null) {
        for(Iterator iter1=roiVwV.iterator(); iter1.hasNext();) {
          ReplacedOrderItemView roiVw = (ReplacedOrderItemView) iter1.next();
          if(roiVw.getItemId()==itemele.getOrderItem().getItemId()) {
%>
<tr valign="top">
<% colInd=1; %>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>">
<html:text styleClass="text" size="3" maxlength="5" name="STORE_ORDER_DETAIL_FORM"
    property='<%= "replacedOrderItem[" + roiVw.getOrderItemId() + "].quantityS" %>' />
</td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>" colspan='<%=nbColumns-qtyColInd-1%>'>
Web Order# <%=roVw.getOrderNum()%> (<%=roVw.getOrderSiteName()%>)</td>
</tr>

<%
            break;
          }
        }
      }
    }
  }
}
%>

<!-- Consolidated order items. End -->


<!-- the order item actions -->
<logic:present name="itemele" property="orderItemActionDescList">
<%
int shippedQty = 0;
boolean substitutedFlag = false;
boolean shippedFlag = false;
%>

<bean:size id="detailCount" name="itemele" property="orderItemActionDescList" />
<logic:greaterThan name="detailCount" value="0">
<logic:iterate id="detailele" indexId="j" name="itemele" property="orderItemActionDescList" type="com.cleanwise.service.api.value.OrderItemActionDescData">
<bean:define id="detailkey"  name="detailele" property="orderItemAction.orderItemActionId"/>
<bean:define id="actionqty" name="detailele" property="orderItemAction.quantity" type="Integer"/>
<bean:define id="actioncd" name="detailele" property="orderItemAction.actionCd" type="String"/>

<% String detailLinkHref = new String("storeOrderItemUpdate.do?action=edit&id=" + key + "&detailid=" + detailkey);%>

<!--  get the shipped quantity for this item -->
<%
if ("Substituted".equals(actioncd) || null == actionqty) {
substitutedFlag = true;
}
else if ("Shipped".equals(actioncd)) {
shippedQty += actionqty.intValue();
shippedFlag = true;
}
%>

<bean:define id="actionNum" name="detailele" property="orderItemAction.actionCd" type="java.lang.String"/>
<% 	String itemStatus = new String("");
itemStatus = actionNum;
%>

<tr>
<% colInd=1; %>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><bean:write name="itemele" property="orderItem.orderLineNum"/>&nbsp;</td>
<% if(clwSwitch) { %>
  <% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
  <td class="<%=style%>">&nbsp;</td>
<% } %>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><bean:write name="detailele" property="orderItemAction.affectedSku"/>&nbsp;</td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><bean:write name="detailele" property="distItemSkuNum"/>&nbsp;</td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>">&nbsp;</td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><bean:write name="detailele" property="itemShortDesc"/>&nbsp;</td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><bean:write name="detailele" property="itemUom"/>&nbsp;</td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><bean:write name="detailele" property="itemPack"/>&nbsp;</td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>">&nbsp;</td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>">&nbsp;</td>
<%if (RefCodeNames.STORE_TYPE_CD.MLA.equals(userStore)) {%>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><bean:write name="detailele" property="itemDistCost"/>&nbsp;</td>
<%}%>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>">&nbsp;</td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>">&nbsp;</td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><bean:write name="detailele" property="orderItemAction.quantity"/>&nbsp;</td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><%=itemStatus%>&nbsp;</td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>">&nbsp;</td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>">&nbsp;</td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>">&nbsp;</td>

<logic:present name="detailele" property="orderItemAction.actionDate">
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>">
        <bean:define id="date" name="detailele" property="orderItemAction.actionDate"/>
        <i18n:formatDate value="<%=date%>" pattern="MM/dd/yyyy" locale="<%=Locale.US%>"/>
</td>
</logic:present>
<logic:notPresent name="detailele" property="orderItemAction.actionDate">
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>">&nbsp;</td>
</logic:notPresent>

<% if (allowWhenNoErpNoEdi || allowWhenErpEdi) { %>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>">&nbsp;</td>
<% }  %>
</tr>
        </logic:iterate>
        </logic:greaterThan>

<%  // calc the backordered quantity
        int backorderedQty = 0;
        if (true == shippedFlag && false == substitutedFlag) {
                backorderedQty = qty.intValue() - shippedQty;
                if ( 0 < backorderedQty ) {
%>
<tr>
<% colInd=1; %>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><bean:write name="itemele" property="orderItem.orderLineNum"/>&nbsp;</td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>">&nbsp;</td>
<% if(clwSwitch) { %>
  <% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
  <td class="<%=style%>">&nbsp;</td>
<% } %>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><bean:write name="itemele" property="orderItem.itemSkuNum"/>&nbsp;</td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>">&nbsp;</td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>">&nbsp;</td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>">&nbsp;</td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>">&nbsp;</td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>">&nbsp;</td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>">&nbsp;</td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>">&nbsp;</td>
<%if (RefCodeNames.STORE_TYPE_CD.MLA.equals(userStore)) {%>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>">&nbsp;</td>
<%}%>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>">&nbsp;</td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>">&nbsp;</td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><%=backorderedQty%>&nbsp;</td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>">Backordered&nbsp;</td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>">&nbsp;</td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>">&nbsp;</td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>">&nbsp;</td>

<% if (allowWhenNoErpNoEdi) { %>

<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>">&nbsp;</td>
<% }

     }
        }
%>

</logic:present>
</logic:iterate>
</logic:present>
</table>
</logic:notEqual>
<logic:equal  name="STORE_ORDER_DETAIL_FORM" property="simpleServiceFlag" value="<%=Boolean.TRUE.toString()%>">
<jsp:include page="storeOrderServiceStatusDetail.jsp" flush='true'>
    <jsp:param name="siteId" value="<%=siteId%>"/>
</jsp:include>
</logic:equal>
</td>
</tr>

<tr>
  <td align="left">
    <html:submit property="action" style="width:120px;">
      <app:storeMessage  key="admin.button.addItem"/>
    </html:submit>
  </td>
  <td align="right">
  </td>
</tr>
<tr>
<td>

<%-- /* BEGIN distributor summary */ --%>
<%try{%>
<% if(theForm != null && theForm.getDistSummary() != null) { %>
<div class="text">
  <table ID=1077 style="border: solid 1px black;">
    <tr><td class="mediumheader">Distribution Information</td></tr>
    <tr class="resultscolumna"><td>Dist name</td><td>Lines</td><td align=right>Total</td>
      <% if (  theForm.getDistSummary().allowChangeToShipping() ) { %>
      <td>Ship VIA</td>
      <% } %>
    </tr>
<%
if(theForm.getDistSummary().getSummaries() != null && theForm.getDistSummary().getSummaries().iterator() != null){
// Summarize the expected po creation by distributor.
java.util.Iterator sumz = theForm.getDistSummary().getSummaries().iterator();
while ( sumz.hasNext()  ) {
%>
<%=( com.cleanwise.view.forms.StoreOrderDetailForm.DistSummaryLine)sumz.next()%>
<%}%>
</table>
</div>
<%}%>
<%}%>
<%}catch(Exception e){}%>
<%-- /* END distributor summary */ --%>

</td>

<td align="left" valign="top">

<% if (null != accountId && 0 != accountId.intValue() ) {  %>
<html:submit style="width:120px;" property="action" onclick="return confirm('You are trying to update this order and/or items. \\nDo you want to continue?');">
  <app:storeMessage  key="admin.button.moveOrder"/>
</html:submit>
<%}%>
<% if (showCancel) { %>
 <html:submit property="action" style="width:120px;" onclick="return confirm('You are trying to cancel this order. \\nDo you want to continue?');">
  <app:storeMessage  key="admin.button.cancelOrder"/>
 </html:submit>
<% } %>

</td>
</tr>
</table>
</div>

</html:form>

</body>
</html:html>


