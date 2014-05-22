<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>


<%@ page import="java.util.Locale" %>
<%@ page import="java.util.Date" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>


<app:checkLogon/>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="Location" value="contract" type="java.lang.String" toScope="session"/>

<Script language="JavaScript1.2">
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
<link rel="stylesheet" href="../externals/styles.css">
<title>Operations Store Admin: Returns</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#FFFFFF">

<bean:define id="mode" value="" />
<%if ("createReturn".equals(request.getParameter("action"))){%>
        <bean:define id="mode" value="create" />
<%}else if ("createFailed".equals(request.getParameter("action"))){%>
        <bean:define id="mode" value="create" />
<%}else{%>
        <bean:define id="mode" value="edit" />
<%}%>

<div class="text">

<table border="0" cellpadding="1" cellspacing="0" width="769" class="mainbody">
<html:form name="PO_OP_DETAIL_FORM" action="/storeportal/storeReturnsOpDetail.do"
        type="com.cleanwise.view.forms.PurchaseOrderOpDetailForm">

        <tr>
        <td colspan="2" class="mediumheader">Order Header Information</td>
                <td colspan="2" class="mediumheader">Shipping Information</td>
                <td colspan="2" class="mediumheader">&nbsp;</td>
        </tr>

        <tr>
                <td colspan="6">&nbsp;
                        <bean:define id="orderId" name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.orderData.orderId" />
                        <bean:define id="poId" name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.purchaseOrderData.purchaseOrderId" />
                        <bean:define id="returnId" name="PO_OP_DETAIL_FORM" property="returnRequestDescData.returnRequestData.returnRequestId" />
                </td>
        </tr>

        <tr valign="top">
                <td colspan="2">
                        <table width="100%">
                                <tr valign="top">
                                <td><b>Web Order#:</b></td>
                                <td><bean:write name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.orderData.orderNum" />
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
<logic:present name="PO_OP_DETAIL_FORM" property="orderStatusDetail.referenceNumList" >
<logic:iterate id="refele" name="PO_OP_DETAIL_FORM" property="orderStatusDetail.referenceNumList" scope="session" type="com.cleanwise.service.api.value.OrderPropertyData">
<tr>
 <td> <b><bean:write name="refele" property="orderPropertyTypeCd" filter="true"/>.<bean:write name="refele" property="shortDesc" filter="true"/></b></td>
 <td><bean:write name="refele" property="value" filter="true"/> </td>
</tr>
</logic:iterate>
</logic:present>
<!-- SiteSpecific properties. -->


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
                                <td><bean:write name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.accountBusEntityData.shortDesc" filter="true"/>
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

                                <tr>
                                <td><b>Ship From Name:</b></td>
                                <td><bean:write name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.shipToAddress.shortDesc" filter="true"/>
                                        </td>
                                </tr>
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
            <input type="button" onclick="popLink('storeOrderOpNote.do?action=view&orderid=<%=orderId%>');" value="View All Order Detail Notes" >
            &nbsp;<input type="button" onclick="popLink('storeOrderOpNote.do?action=add&orderid=<%=orderId%>');" value="Add Order Detail Note" >
            <input type="button" onclick="popPrint('storeOrderOpDetailPrint.do?action=view&amp;id=<%=orderId%>');" value="Print Order Detail">
            &nbsp;<input type="button" onclick="popPrint('storePurchaseOrderMgrOpDetail.do?action=print&amp;id=<%=poId%>');" value="Print PO Detail">
            <logic:equal name="mode" value="edit">
              &nbsp;<input type="button" onclick="popPrint('storeReturnsOpDetail.do?action=printReturn&amp;id=<%=returnId%>');" value="Print Return Detail"></td>
            </logic:equal>
          </td>
     </tr>

        <tr>
        <td colspan="2" class="mediumheader">PO Header Information</td>
                <td colspan="2" class="mediumheader">&nbsp;</td>
                <td colspan="2" class="mediumheader">&nbsp;</td>
        </tr>
    <tr valign="top">
        <td colspan="2">
                        <table width="100%">
                                <tr>
<td><b>Outbound PO#:</b></td>
<td>
<bean:write name="PO_OP_DETAIL_FORM" 
  property="purchaseOrderStatusDescDataView.purchaseOrderData.erpPoNum" 
  filter="true"/>
                                </td>
                                </tr>
                                <tr>
                                <td><b>PO Date:</b></td>
                    <logic:present name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.purchaseOrderData.poDate">
                      <td>
                        <bean:define id="erppodate" name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.purchaseOrderData.poDate"/>
                        <i18n:formatDate value="<%=erppodate%>" pattern="MM/dd/yyyy" locale="<%=Locale.US%>"/>
                      </td>
                    </logic:present>
                    <logic:notPresent name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.purchaseOrderData.poDate">
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
                        <bean:write name="PO_OP_DETAIL_FORM" property="distributorData.purchaseOrderFreightTerms.value" filter="true"/>
                                </td>
                                </tr>
                                <tr>
                                <td><b>Due Days:</b></td>
                                        <td>
                        <bean:write name="PO_OP_DETAIL_FORM" property="distributorData.purchaseOrderDueDays.value" filter="true"/>
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
                                <td><b>Miscelanious:</b></td>
                                        <td>
                        <bean:define id="purchaseOrderMisc"  name="PO_OP_DETAIL_FORM" property="miscellaneousAmount"/>
                                        <i18n:formatCurrency value="<%=purchaseOrderMisc%>" locale="<%=Locale.US%>"/>
                                </td>
                                </tr>
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
    <tr>
        <td colspan="2" class="mediumheader">Return Header Information</td>
        <td colspan="2" class="mediumheader">&nbsp;</td>
        <td colspan="2" class="mediumheader">&nbsp;</td>
    </tr>
    <tr>
        <td><b>Return Request Ref Number:</b></td>
        <td><bean:write name="PO_OP_DETAIL_FORM" property="returnRequestDescData.returnRequestData.returnRequestRefNum" filter="true"/></td>
        <td><b>Sender Contact Name:</b></td>
        <td><html:text name='PO_OP_DETAIL_FORM' property='returnRequestDescData.returnRequestData.senderContactName'/><span class="reqind">*</span></td>
        <td><b>Sender Contact Phone:</b></td>
        <td><html:text name='PO_OP_DETAIL_FORM' property='returnRequestDescData.returnRequestData.senderContactPhone'/><span class="reqind">*</span></td>
    </tr>
    <tr>
        <td><b>Fault:</b></td>
        <td>
                <html:select name="PO_OP_DETAIL_FORM" property='returnRequestDescData.returnRequestData.reason'>
                        <html:option value=""><app:storeMessage  key="admin.select"/></html:option>
                      <%--  <html:option value="Customer choice">Customer choice</html:option> --%>
                      <%--  <html:option value="Damaged">Damaged</html:option> --%>
                      <%--  <html:option value="Defective">Defective</html:option> --%>
                        <html:option value="Our error">Our error</html:option>
                        <html:option value="Customer error">Customer error</html:option>
                        <html:option value="Distributor error">Distributor error</html:option>
                        <html:option value="UOM error">UOM error</html:option>
                      <%--  <html:option value="Duplicate order">Duplicate order</html:option> --%>
                </html:select>
                <span class="reqind">*</span>
        </td>
        <td><b>Distributor Invoice Number:</b></td>
        <td><html:text name='PO_OP_DETAIL_FORM' property='returnRequestDescData.returnRequestData.distributorInvoiceNumber'/></td>
        <td><b>Distributor Reference Number:</b></td>
        <td><html:text name='PO_OP_DETAIL_FORM' property='returnRequestDescData.returnRequestData.distributorRefNum'/></td>
    </tr>
	<tr>
        <td><b>Reason:</b></td>
		<td>
                <html:select name="PO_OP_DETAIL_FORM" property='returnRequestDescData.returnRequestData.problem'>
                        <html:option value=""><app:storeMessage  key="admin.select"/></html:option>
                        <html:option value="Wrong size">Wrong size</html:option> 
						<html:option value="Wrong color">Wrong color</html:option> 
						<html:option value="Pack vs. Each">Pack vs. Each</html:option> 
						<html:option value="Not happy with quality">Not happy with quality</html:option> 
						<html:option value="Wasn't what they expected">Wasn't what was expected</html:option> 
						<html:option value="Other">Other</html:option> 
				</html:select>
                <span class="reqind">*</span>
        </td>	  
	</tr>	
    <tr>
        <td><b>Date Received:</b></td>
        <td><html:text name='PO_OP_DETAIL_FORM' property='returnRequestDateRecievedString'/><span class="reqind">*</span></td>
        <td><b>Return Request Status:</b></td>
        <td><bean:write name="PO_OP_DETAIL_FORM" property="returnRequestDescData.returnRequestData.returnRequestStatus"/></td>
        <td><b>Notes To Distributor:</b></td>
        <td><html:text name='PO_OP_DETAIL_FORM' property='returnRequestDescData.returnRequestData.notesToDistributor'/></td>
    </tr>
    <tr>
        <td><b>Return Pickup Contact:</b></td>
        <td><html:text name="PO_OP_DETAIL_FORM" property="returnRequestDescData.returnRequestData.pickupContactName"/><span class="reqind">*</span></td>
        <td><b>Return Pickup Contact Fax Number:</b></td>
        <td><html:text name="PO_OP_DETAIL_FORM" property="returnRequestDescData.pickupAddress.faxPhoneNum"/></td>
        <td><b>Return Pickup Contact Phone Number:</b></td>
        <td><html:text name="PO_OP_DETAIL_FORM" property="returnRequestDescData.pickupAddress.phoneNum"/><span class="reqind">*</span></td>
    </tr>
    <tr>
        <td><b>Return Method:</b></td>
        <td>
        <html:select name="PO_OP_DETAIL_FORM" property='returnRequestDescData.returnRequestData.returnMethod'>
                <html:option value=""><app:storeMessage  key="admin.select"/></html:option>
                <html:option value="Distributor Pickup">Distributor Pickup</html:option>
                <html:option value="Customer Mailed">Customer Mailed</html:option>
        </html:select>
        </td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
    </tr>
            <tr><td>
            <tr>
                <td valign="top"><b>Return Pickup Address:</b></td>
                <td>
                    <html:text name='PO_OP_DETAIL_FORM' property='returnRequestDescData.pickupAddress.shortDesc'/><span class="reqind">*</span><br>
                    <html:text name='PO_OP_DETAIL_FORM' property='returnRequestDescData.pickupAddress.address1'/><span class="reqind">*</span><br>
                    <html:text name='PO_OP_DETAIL_FORM' property='returnRequestDescData.pickupAddress.address2'/><br>
                    <html:text name='PO_OP_DETAIL_FORM' property='returnRequestDescData.pickupAddress.address3'/><br>
                    <html:text name='PO_OP_DETAIL_FORM' property='returnRequestDescData.pickupAddress.address4'/>
                 </td>
            </tr>
            <tr>
                <td><b>City:</b></td>
                <td><html:text name="PO_OP_DETAIL_FORM" property="returnRequestDescData.pickupAddress.city"/><span class="reqind">*</span></td>
            </tr>
            <tr>
                <td><b>State:</b></td>
                <td><html:text name="PO_OP_DETAIL_FORM" property="returnRequestDescData.pickupAddress.stateProvinceCd"/><span class="reqind">*</span></td>
            </tr>
            <tr>
                <td><b>ZIP Code:</b></td>
                <td><html:text name="PO_OP_DETAIL_FORM" property="returnRequestDescData.pickupAddress.postalCode"/><span class="reqind">*</span></td>
            </tr>
            </td></tr>
        <tr>
                <td colspan="6">&nbsp;</td>
        </tr>

        <tr>
                <td colspan="6">

<%/*</html:form>*/%>

<%/*START ORDER ITEMS*/%>
<logic:equal name="mode" value="create">
        <jsp:include flush='true' page="storePurchaseOrderItemInc.jsp">
                <jsp:param name="caller.function" value="returns" />
        </jsp:include>
</logic:equal>
<logic:equal name="mode" value="edit">
        <jsp:include flush='true' page="storePurchaseOrderItemInc.jsp">
                <jsp:param name="caller.function" value="returnsView" />
        </jsp:include>
</logic:equal>


<%/*END ORDER ITEMS*/%>
</table>

                </td>
        </tr>

    <tr>
        <td colspan="6" align="center">
                <html:submit property="action"><app:storeMessage  key="admin.button.back"/></html:submit>

                <logic:equal name="mode" value="create">
                        <html:submit property="action">
                                <app:storeMessage  key="admin.button.create"/>
                        </html:submit>
                </logic:equal>
                <logic:equal name="mode" value="edit">
                        <html:submit property="action">
                                <app:storeMessage  key="admin.button.submitUpdates"/>
                        </html:submit>
                </logic:equal>
       </td>
    </tr>


</html:form>
</table>
</div>
</body>
</html:html>
