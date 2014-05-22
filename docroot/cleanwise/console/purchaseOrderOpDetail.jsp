<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
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

<app:checkLogon/>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="orderStatus" type="java.lang.String"
                                name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.orderData.orderStatusCd"/>
<bean:define id="purchaseOrderStatusDescDataView" type="com.cleanwise.service.api.value.PurchaseOrderStatusDescDataView"
                                name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView"/>


<script language="JavaScript1.2">
<!--
function popLocate(pLoc, parm) {
  var loc = pLoc + ".jsp?parm=" + parm;
  if(parm != "print"){
    locatewin = window.open(loc,"Locate", "menubar=no,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
  }else{
    locatewin = window.open(loc,"Locate", "menubar=yes,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
  }
  locatewin.focus();
  return false;
}

function popLocate2(pLoc, name, pDesc) {
    var loc = pLoc + ".do?feedField=" + name + "&amp;feedDesc=" + pDesc;
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


function popOrder(pLoc) {
var loc = pLoc;
orderwin = window.open(loc,"Order", "menubar=yes,resizable=yes,scrollbars=yes,toolbar=yes,status=yes,height=500,width=780,left=50,top=50");
orderwin.focus();

return false;
}

//-->
</script>

<html:html>

<head>
<title>Operations Console Home: PurchaseOrders</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="../externals/styles.css">
</head>

<body bgcolor="#FFFFFF">

<jsp:include flush='true' page="ui/consoleToolbar.jsp"/>
<jsp:include flush='true' page="ui/loginInfo.jsp"/>
<jsp:include flush='true' page="ui/purchaseOrderOpToolbar.jsp"/>

<div class="text">
<font color=red>
<html:errors/>
</font>

<table border="0" cellpadding="1" cellspacing="0" width="769" class="mainbody">
<html:form name="PO_OP_DETAIL_FORM" action="/console/purchaseOrderOpDetail.do"
        type="com.cleanwise.view.forms.PurchaseOrderOpDetailForm">

        <tr>
        <td colspan="2" class="mediumheader">Order Header Information</td>
                <td colspan="2" class="mediumheader">Shipping Information</td>
                <td colspan="2" class="mediumheader">&nbsp;</td>
        </tr>

        <tr>
                <td colspan="6">&nbsp;
                        <bean:define id="orderId" name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.orderData.orderId" />
                </td>
        </tr>

        <tr valign="top">
                <td colspan="2">
                        <table width="100%">
<tr valign="top">
<td><b>Web Order#:</b></td>

<td>
<% String orderLinkHref = "orderOpDetail.do?action=view&id=" + orderId;%>
<a href="<%=orderLinkHref%>" target="new">
  <bean:write name="PO_OP_DETAIL_FORM" 
    property="purchaseOrderStatusDescDataView.orderData.orderNum" />
</a>
</td>

</tr>
                                <tr>
                                <td><b>Customer PO#:</b></td>
                                <td><bean:write name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.orderData.requestPoNum" filter="true"/>
                                        </td>
                                </tr>
                                <tr>
                                <td><b>ERP Order#:</b></td>
                                <td><bean:write name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.orderData.erpOrderNum" filter="true"/>
                                        </td>
                                </tr>
                                <tr>
                                <td><b>Customer Requisition#:</b></td>
                                <td><bean:write name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.orderData.refOrderNum" filter="true"/>
                                        </td>
                                </tr>
                                <tr>
                                <td><b>Method:</b></td>
                                <td><bean:write name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.orderData.orderSourceCd" filter="true"/>
                                </td>
                                </tr>
                                <tr>
                                <td><b>Contact Name:</b></td>
                                <td><bean:write name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.orderData.orderContactName" filter="true"/>
                                </td>
                                </tr>
                                <tr>
                                <td><b>Contact Phone#:</b></td>
                                <td><bean:write name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.orderData.orderContactPhoneNum" filter="true"/>
                                </td>
                                </tr>
                                <tr>
                                <td><b>Contact Email:</b></td>
                                <td><bean:write name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.orderData.orderContactEmail" filter="true"/>
                                </td>
                                </tr>
<!-- SiteSpecific properties. -->
<!--
<logic:present name="PO_OP_DETAIL_FORM" property="orderStatusDetail.referenceNumList" >
<logic:iterate id="refele" name="PO_OP_DETAIL_FORM" property="orderStatusDetail.referenceNumList" scope="session" type="com.cleanwise.service.api.value.OrderPropertyData">
<tr>
 <td> <b><bean:write name="refele" property="orderPropertyTypeCd" filter="true"/>.<bean:write name="refele" property="shortDesc" filter="true"/></b></td>
 <td><bean:write name="refele" property="value" filter="true"/> </td>
</tr>
</logic:iterate>
</logic:present>
-->
<!-- SiteSpecific properties. -->
<bean:define id="orderStatusDetail" type="OrderStatusDescData"
                                name="PO_OP_DETAIL_FORM" property="orderStatusDescData"/>
<tr>
<td colspan='2' class="order_status_addr"> 
  <%@ include file="f_orderMetaData.jsp" %>
</td>
</tr>


                                <tr>
                                <td><b>Date Ordered:</b></td>
                                <td>
                                        <logic:present name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.orderData.originalOrderDate" >
                                                <bean:define id="orderdate" name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.orderData.originalOrderDate" type="java.util.Date"/>
                                        <i18n:formatDate value="<%=orderdate%>" pattern="MM/dd/yyyy" locale="<%=Locale.US%>"/>&nbsp;
                                          <logic:present name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.orderData.originalOrderTime" >
                                                <bean:define id="ordertime" name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.orderData.originalOrderTime" type="java.util.Date"/>
                                        <i18n:formatDate value="<%=ordertime%>" pattern="hh:mm aaa" locale="<%=Locale.US%>"/>
                                          </logic:present>
                                        </logic:present>
                                        </td>
                                </tr>


                                <tr>
                                <td><b>Contract ID:</b></td>
                                <td><bean:write name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.orderData.contractId" filter="true"/>
                                </td>
                                </tr>


                        </table>
                </td>

                <td colspan="2">
                        <table width="100%">
                                <tr valign="top">
                                <td><b>Account Name:</b></td>
                                <td><bean:write name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.poAccountName" filter="true"/>
                                </td>
                                </tr>
                                <tr>
                                <td><b>Site Name:</b></td>
                                <td><bean:write name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.orderData.orderSiteName" filter="true"/>
                                        </td>
                                </tr>
                                <tr>
                                <td><b>Shipping Address:</b></td>
                                <td><bean:write name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.shipToAddress.address1" filter="true"/>
                                        </td>
                                </tr>
                                <tr>
                                <td><b>&nbsp;</b></td>
                                <td><bean:write name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.shipToAddress.address2" filter="true"/>
                                        </td>
                                </tr>
                                <tr>
                                <td><b>&nbsp;</b></td>
                                <td><bean:write name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.shipToAddress.address3" filter="true"/>
                                        </td>
                                </tr>
                                <tr>
                                <td><b>City:</b></td>
                                <td><bean:write name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.shipToAddress.city" filter="true"/>
                                        </td>
                                </tr>
                                <tr>
                                <td><b>State:</b></td>
                                <td><bean:write name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.shipToAddress.stateProvinceCd" filter="true"/>
                                        </td>
                                </tr>
                                <tr>
                                <td><b>ZIP Code:</b></td>
                                <td><bean:write name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.shipToAddress.postalCode" filter="true"/>
                                        </td>
                                </tr>

                                <logic:present	name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.orderData.customerShipTo">
                                <tr>
                                <td><b>Customer Shipping Name:</b></td>
                                <td><bean:write name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.orderData.customerShipTo.shortDesc" filter="true"/>
                                        </td>
                                </tr>
                                <tr>
                                <td><b>Customer Shipping Address:</b></td>
                                <td><bean:write name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.orderData.customerShipTo.address1" filter="true"/>
                                        </td>
                                </tr>
                                <tr>
                                <td><b>&nbsp;</b></td>
                                <td><bean:write name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.orderData.customerShipTo.address2" filter="true"/>
                                        </td>
                                </tr>
                                <tr>
                                <td><b>&nbsp;</b></td>
                                <td><bean:write name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.orderData.customerShipTo.address3" filter="true"/>
                                        </td>
                                </tr>
                                <tr>
                                <td><b>&nbsp;</b></td>
                                <td><bean:write name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.orderData.customerShipTo.address4" filter="true"/>
                                        </td>
                                </tr>
                                <tr>
                                <td><b>City:</b></td>
                                <td><bean:write name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.orderData.customerShipTo.city" filter="true"/>
                                        </td>
                                </tr>
                                <tr>
                                <td><b>State:</b></td>
                                <td><bean:write name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.orderData.customerShipTo.stateProvinceCd" filter="true"/>
                                        </td>
                                </tr>
                                <tr>
                                <td><b>ZIP Code:</b></td>
                                <td><bean:write name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.orderData.customerShipTo.postalCode" filter="true"/>
                                        </td>
                                </tr>
                                </logic:present>

                        </table>
                </td>

                <td colspan="2">
                        <table width="100%">
                                <tr valign="top">
                                <td><b>Order Status:</b></td>
                                <td><bean:write name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.orderData.orderStatusCd" filter="true"/>
                                </td>
                            </tr>
                                <tr>
                                        <td><b>Placed By:</b></td>
                                <td><bean:write name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.orderData.addBy" filter="true"/>
                                        </td>
                                </tr>
                                <tr>
                                <td><b>Sub-Total:</b></td>
                                        <td>
                        <logic:present name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.orderData.totalPrice">
                          <bean:define id="subtotal"  name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.orderData.totalPrice"/>
                                          <i18n:formatCurrency value="<%=subtotal%>" locale="<%=Locale.US%>"/>
                        </logic:present>
                                </td>
                                </tr>
                                <tr>
                                <td><b>Freight:</b></td>
                                        <td>
                        <logic:present name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.orderData.totalFreightCost">
                            <bean:define id="freightcost"  name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.orderData.totalFreightCost"/>
                                        <i18n:formatCurrency value="<%=freightcost%>" locale="<%=Locale.US%>"/>
                        </logic:present>
                                </td>
                                </tr>
                                <tr>
                                <td><b>Tax:</b></td>
                                        <td>
                        <logic:present name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.orderData.totalTaxCost">
                            <bean:define id="tax"  name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.orderData.totalTaxCost"/>
                                            <i18n:formatCurrency value="<%=tax%>" locale="<%=Locale.US%>"/>
                         </logic:present>
                            </td>
                                </tr>
<tr>
<td><b>Handling:</b></td>
<td>
<logic:present name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.orderData.totalMiscCost">
<bean:define id="handling"  name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.orderData.totalMiscCost"/>
<i18n:formatCurrency value="<%=handling%>" locale="<%=Locale.US%>"/>
</logic:present>
</td>
</tr>

<logic:present name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.orderData.totalRushCharge">
<tr>
<td><b>Rush Charge:</b></td>
<td>
<bean:define id="rushc"  name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.orderData.totalRushCharge"/>
<i18n:formatCurrency value="<%=rushc%>" locale="<%=Locale.US%>"/>
</td>
</tr>
</logic:present>

<tr>
<td><b>Total:</b></td>
<td>
<logic:present name="PO_OP_DETAIL_FORM" property="orderTotalAmount">
<bean:define id="orderTotal"  name="PO_OP_DETAIL_FORM" property="orderTotalAmount"/>
<i18n:formatCurrency value="<%=orderTotal%>" locale="<%=Locale.US%>"/>
</logic:present>
</td>
</tr>

                        </table>
                </td>
        </tr>

        <tr><td colspan="6">&nbsp;</td></tr>
        <tr>
          <td><b>Notes:</b></td>
          <td colspan="5">&nbsp;</td>
    </tr>

        <tr>
          <td colspan="6"><bean:write name="PO_OP_DETAIL_FORM" property="orderPropertyDetail.value" filter="true"/></td>
    </tr>

        <tr><td colspan="6">&nbsp;</td></tr>


    <tr>
          <td colspan="6" align="center">
            <input type="button" onclick="popLink('orderOpNote.do?action=view&orderid=<%=orderId%>&src=po');" value="View All Order Detail Notes" >
            &nbsp;<input type="button" onclick="popLink('orderOpNote.do?action=add&orderid=<%=orderId%>');" value="Add Order Detail Note" >
        &nbsp;<input type="button" onclick="popPrint('orderOpDetailPrint.do?action=view&amp;id=<%=orderId%>');" value="Print Order Detail">
        &nbsp;<input type="button" onclick="popPrint('purchaseOrderOpDetail.do?action=print');" value="Print PO Detail"></td>
          </td>
        </tr>
        </table>
<table border="0" cellpadding="1" cellspacing="0" width="769" class="mainbody">

<tr>
        <td colspan="2" class="mediumheader">
<br><br>
Outbound PO Header Information</td>
                <td colspan="2" >&nbsp;</td>
                <td colspan="2" >&nbsp;</td>
        </tr>
    <tr valign="top">
        <td colspan="2">
                        <table width="100%">

<tr>
<td><b>Outbound PO #:</b></td>
<td>
<%--<bean:write name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.purchaseOrderData.erpPoNum" filter="true"/>--%>
<bean:write name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.purchaseOrderData.outboundPoNum" filter="true"/>
</td>
</tr>
<tr>
<td><b>Outbound PO Date:</b></td>

<logic:present name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.purchaseOrderData.poDate">
<td>

<bean:define id="erppodate" name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.purchaseOrderData.poDate"/>
<i18n:formatDate value="<%=erppodate%>" pattern="MM/dd/yyyy" locale="<%=Locale.US%>"/>
</td>
</logic:present>
</tr>


<tr>

<td><b>ERP PO #:</b></td>
<td>
<logic:present name="PO_OP_DETAIL_FORM" 
  property="purchaseOrderStatusDescDataView.purchaseOrderData.erpPoRefNum">
<bean:write name="PO_OP_DETAIL_FORM" 
  property="purchaseOrderStatusDescDataView.purchaseOrderData.erpPoRefNum"/>
</logic:present>
</td>
</tr>

<tr>
<td><b>ERP PO Date:</b></td>

<td>
<logic:present name="PO_OP_DETAIL_FORM" 
  property="purchaseOrderStatusDescDataView.purchaseOrderData.erpPoRefDate">
<bean:define id="erpporefdate" name="PO_OP_DETAIL_FORM" 
  property="purchaseOrderStatusDescDataView.purchaseOrderData.erpPoRefDate"/>

<i18n:formatDate value="<%=erpporefdate%>" 
  pattern="MM/dd/yyyy" locale="<%=Locale.US%>"/>
</logic:present>

</td>
</tr>


<logic:notPresent name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.purchaseOrderData.poDate">
<td>&nbsp;</td>
</logic:notPresent>
</tr>



<bean:size id="omld"
        name="PO_OP_DETAIL_FORM" property="orderMetaList"/>

<% if ( omld.intValue() > 0 ) { %>

<logic:present name="PO_OP_DETAIL_FORM" property="orderMetaList">

<logic:iterate id="OMD1" name="PO_OP_DETAIL_FORM" property="orderMetaList"
        type="com.cleanwise.service.api.value.OrderMetaData">
<tr>
<td><b> <bean:write name="OMD1" property="name"/>: </b></td>
<td> <bean:write name="OMD1" property="value"/> </td>
</tr>
</logic:iterate>

</logic:present>

<% } %>

<tr>
<td><b>Distributor Delivery Date:</b></td>
<logic:present name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.deliveryDate"><td>
<bean:define id="erppodate" name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.deliveryDate"/>
<i18n:formatDate value="<%=erppodate%>" pattern="MM/dd/yyyy" locale="<%=Locale.US%>"/>
</td>
</logic:present>
<logic:notPresent name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.deliveryDate">
<td>&nbsp;</td>
</logic:notPresent>
</tr>
</table>

                </td>
        <td colspan="2">
                        <table width="100%">
                                <tr>
                                <td><b>Vendor Name:</b></td>
                                        <td>
                        <bean:write name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.distributorBusEntityData.shortDesc" filter="true"/>
                                </td>
                                <tr>
                                <td><b>Vendor Phone:</b></td>
                                        <td>
                        <bean:write name="PO_OP_DETAIL_FORM" property="distributorData.primaryPhone.phoneNum" filter="true"/>
                                </td>
                </tr>
                <tr>
                    <td><b>Vendor Address:</b></td>
                                        <td>
                        <bean:write name="PO_OP_DETAIL_FORM" property="distributorData.primaryAddress.address1" filter="true"/>
                                </td>
                                </tr>
                <tr>
                    <td><b>&nbsp;</b></td>
                                        <td>
                        <bean:write name="PO_OP_DETAIL_FORM" property="distributorData.primaryAddress.address2" filter="true"/>
                                </td>
                                </tr>
                <tr>
                    <td><b>&nbsp;</b></td>
                                        <td>
                        <bean:write name="PO_OP_DETAIL_FORM" property="distributorData.primaryAddress.address3" filter="true"/>
                                </td>
                                </tr>
                <tr>
                    <td><b>&nbsp;</b></td>
                                        <td>
                        <bean:write name="PO_OP_DETAIL_FORM" property="distributorData.primaryAddress.address4" filter="true"/>
                                </td>
                                </tr>
                <tr>
                    <td><b>Vendor City:</b></td>
                                        <td>
                        <bean:write name="PO_OP_DETAIL_FORM" property="distributorData.primaryAddress.city" filter="true"/>
                                </td>
                                </tr>
                <tr>
                    <td><b>Vendor State:</b></td>
                                        <td>
                        <bean:write name="PO_OP_DETAIL_FORM" property="distributorData.primaryAddress.stateProvinceCd" filter="true"/>
                                </td>
                                </tr>
                <tr>
                    <td><b>Vendor Zip Code:</b></td>
                                        <td>
                        <bean:write name="PO_OP_DETAIL_FORM" property="distributorData.primaryAddress.postalCode" filter="true"/>
                                </td>
                                </tr>
                        </table>
                </td>
        <td colspan="2">
                        <table width="100%">
                                <tr>
                    <logic:present name="PO_OP_DETAIL_FORM" property="purchaseOrderStatus">
                        <logic:equal name="PO_OP_DETAIL_FORM" property="purchaseOrderStatus" value="<%=RefCodeNames.PURCHASE_ORDER_STATUS_CD.SENT_TO_DISTRIBUTOR%>">
                            <td><html:submit property="action" value="Acknowledge Po"/></td>
                            <td>&nbsp;</td>
                        </logic:equal>
                        <logic:notEqual name="PO_OP_DETAIL_FORM" property="purchaseOrderStatus" value="<%=RefCodeNames.PURCHASE_ORDER_STATUS_CD.SENT_TO_DISTRIBUTOR%>">
                            <td><b>Status:</b></td>
                            <td><bean:write name="PO_OP_DETAIL_FORM" property="purchaseOrderStatus" filter="true"/></td>
                        </logic:notEqual>
                    </logic:present>
                    <logic:notPresent name="PO_OP_DETAIL_FORM" property="purchaseOrderStatus">
                        ERROR
                    </logic:notPresent>
                </tr>
                                <tr>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                                </tr>
                                <tr>
                                <td><b>Freight Terms:</b></td>
                                        <td>
										<logic:present name="PO_OP_DETAIL_FORM" property="distributorData.purchaseOrderFreightTerms.value">
                        <bean:write name="PO_OP_DETAIL_FORM" property="distributorData.purchaseOrderFreightTerms.value" filter="true"/>
										</logic:present>
                                </td>
                                </tr>
                                <tr>
                                <td><b>Due Days:</b></td>
                                        <td>
										<logic:present name="PO_OP_DETAIL_FORM" property="distributorData.purchaseOrderDueDays.value">
                        <bean:write name="PO_OP_DETAIL_FORM" property="distributorData.purchaseOrderDueDays.value" filter="true"/>
										</logic:present>
                                </td>
                                </tr>
                                <tr>
                                <td><b>Subtotal:</b></td>
                                        <td>
                        <bean:define id="purchaseOrderLnTotal"  name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.purchaseOrderData.lineItemTotal"/>
                                        <i18n:formatCurrency value="<%=purchaseOrderLnTotal%>" locale="<%=Locale.US%>"/>
                                </td>
                                </tr>
                                <tr>
                                <td><b>Misc:</b></td>
                                        <td>
                        <bean:define id="purchaseOrderMisc"  name="PO_OP_DETAIL_FORM" property="miscellaneousAmount"/>
                                        <i18n:formatCurrency value="<%=purchaseOrderMisc%>" locale="<%=Locale.US%>"/>
                                </td>
                                </tr>
								
								<logic:present name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.purchaseOrderData.taxTotal">
                                <tr>
                                <td><b>Tax:</b></td>
                                        <td>
                        <bean:define id="taxTotal"  name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.purchaseOrderData.taxTotal"/>
                                        <i18n:formatCurrency value="<%=purchaseOrderMisc%>" locale="<%=Locale.US%>"/>
                                </td>
                                </tr>
								</logic:present>
                <tr>
                                <td><b>Total:</b></td>
                                        <td>
                        <bean:define id="purchaseOrderTotal"  name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.purchaseOrderData.purchaseOrderTotal"/>
                                        <i18n:formatCurrency value="<%=purchaseOrderTotal%>" locale="<%=Locale.US%>"/>
                                </td>
                                </tr>
                        </table>
                </td>
    </tr>


    <logic:present name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.manifestDataItems">
        <logic:iterate id="man" name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.manifestDataItems"
                type="com.cleanwise.service.api.value.ManifestItemData">
                <tr>

                        <td><b>Package Id:</b></td>
                        <td>
                                <bean:write name="man" property="packageId" filter="true"/>
                        </td>
                        <td>&nbsp;</td>
                </tr>
        </logic:iterate>
    </logic:present>

    <tr>

        <td colspan="3">
            <table width="100%">
                <tr>
                    <td><html:submit property="action" value="Set Shipping Status For All"/></td>
                    <td>
                        <html:select name="PO_OP_DETAIL_FORM" property="shippingStatus">
                                        <html:option value=""><app:storeMessage  key="admin.select"/></html:option>
                                        <html:options  collection="OrderProperty.ShipStatus.vector" property="value" />
                                </html:select>
                    </td>
                 </tr>
                 <tr>
                    <td><html:submit property="action" value="Set Target Ship Date For All"/></td>
                    <td><html:text name="PO_OP_DETAIL_FORM" property="targetShipDate"/></td>
                </tr>
                <tr>
                    <td><html:submit property="action" value="Set Open Line Status For All"/></td>
                    <td>
                        <html:select name="PO_OP_DETAIL_FORM" property="openLineStatusCd">
                          <html:option value=""><app:storeMessage  key="admin.select"/></html:option>
                          <html:options  collection="Open.line.status.vector" property="value" />
                        </html:select>
                    </td>
                 </tr>
            </table>
        </td>
        <td colspan="3">
            <table width="100%">
                <tr>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
            </table>
        </td>
        </tr>


        <tr>
                <td colspan="6" align="center">
			<html:submit property="action">
			   <app:storeMessage  key="global.action.label.save"/>
			 </html:submit>
			 
			 <html:submit property="action">
			   <app:storeMessage  key="button.delete.order.item.actions"/>
			 </html:submit>
	        </td>
        </tr>

                </table>
<!-- start of items list -->
<table border="0" cellpadding="1" cellspacing="0" width="769" class="mainbody">
        <tr>
                <td colspan="6">
<jsp:include flush='true' page="purchaseOrderItemInc.jsp">
        <jsp:param name="caller.function" value="po" />
</jsp:include>
</table>
<!-- end of items list -->
                </td>
        </tr>

    <tr>
        <td colspan="6" align="center">
                        <html:submit property="action"><app:storeMessage  key="admin.button.back"/></html:submit>
                        <html:submit property="action">
			   <app:storeMessage  key="global.action.label.save"/>
			 </html:submit>
			 
			 <html:submit property="action">
			   <app:storeMessage  key="button.delete.order.item.actions"/>
			 </html:submit>
       </td>
    </tr>


</html:form>
</table>

</div>

<jsp:include flush='true' page="ui/consoleFooter.jsp"/>

</body>
</html:html>

