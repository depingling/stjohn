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


<html:html>

<head>
<title>Operations Console Home: Order Status</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="../externals/styles.css">
</head>

<body bgcolor="#FFFFFF">

<div class="text">
<font color=red>
<html:errors/>
</font>


<table ID=1078 border="0" cellpadding="1" cellspacing="0" width="769" class="mainbody">
<html:form styleId="1079" name="STORE_ORDER_DETAIL_FORM" action="/storeportal/storeOrderDetailPrint.do"
        type="com.cleanwise.view.forms.StoreOrderDetailForm">

        <tr>
        <td colspan="2" class="mediumheader">Order Header Information</td>
                <td colspan="2" class="mediumheader">Shipping Information</td>
                <td colspan="2" class="mediumheader">&nbsp;</td>
        </tr>

        <tr>
                <td colspan="6">&nbsp;</td>
        </tr>

        <tr>
                <td colspan="6">&nbsp;
                        <bean:define id="orderId" name="STORE_ORDER_DETAIL_FORM" property="orderStatusDetail.orderDetail.orderId" />
                </td>
        </tr>

        <tr valign="top">
                <td colspan="2">
                        <table ID=1080 width="100%">
                                <tr valign="top">
                                <td><b>Web Order#:</b></td>
                                <td><bean:write name="STORE_ORDER_DETAIL_FORM" property="orderStatusDetail.orderDetail.orderNum" filter="true"/>
                                        </td>
                                </tr>
                                <tr>
                                <td><b>Customer PO#:</b></td>
                                <td><bean:write name="STORE_ORDER_DETAIL_FORM" property="orderStatusDetail.orderDetail.requestPoNum" filter="true"/>
                                        </td>
                                </tr>
                                <tr>
                                <td><b>ERP Order#:</b></td>
                                <td><bean:write name="STORE_ORDER_DETAIL_FORM" property="orderStatusDetail.orderDetail.erpOrderNum" filter="true"/>
                                        </td>
                                </tr>
                                <tr>
                                <td><b>Customer Requisition#:</b></td>
                                <td><bean:write name="STORE_ORDER_DETAIL_FORM" property="orderStatusDetail.orderDetail.refOrderNum" filter="true"/>
                                        </td>
                                </tr>
                                <tr>
                                <td><b>Method:</b></td>
                                <td><bean:write name="STORE_ORDER_DETAIL_FORM" property="orderStatusDetail.orderDetail.orderSourceCd" filter="true"/>
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

<!-- SiteSpecific properties. -->
<logic:present name="STORE_ORDER_DETAIL_FORM" property="orderStatusDetail.referenceNumList" >
<logic:iterate id="refele" name="STORE_ORDER_DETAIL_FORM" property="orderStatusDetail.referenceNumList" scope="session" type="com.cleanwise.service.api.value.OrderPropertyData">
<tr>
 <td> <b><bean:write name="refele" property="orderPropertyTypeCd" filter="true"/>.<bean:write name="refele" property="shortDesc" filter="true"/></b></td>
 <td colspan=2><bean:write name="refele" property="value" filter="true"/> </td>
</tr>
</logic:iterate>
</logic:present>
<!-- SiteSpecific properties. -->

                                <tr>
                                <td><b>Date Ordered:</b></td>
                                <td>
                                        <logic:present name="STORE_ORDER_DETAIL_FORM" property="orderStatusDetail.orderDetail.originalOrderDate" >
                                                <bean:define id="orderdate" name="STORE_ORDER_DETAIL_FORM" property="orderStatusDetail.orderDetail.originalOrderDate" type="java.util.Date"/>
                                        <i18n:formatDate value="<%=orderdate%>" pattern="MM/dd/yyyy" locale="<%=Locale.US%>"/>&nbsp;
                                          <logic:present name="STORE_ORDER_DETAIL_FORM" property="orderStatusDetail.orderDetail.originalOrderTime" >
                                                <bean:define id="ordertime" name="STORE_ORDER_DETAIL_FORM" property="orderStatusDetail.orderDetail.originalOrderTime" type="java.util.Date"/>
                                        <i18n:formatTime  value="<%=ordertime%>" style="short" locale="<%=Locale.US%>"/>
                                          </logic:present>
                                        </logic:present>
                                        </td>
                                </tr>
                        </table>
                </td>

                <td colspan="2">
                        <table ID=1081 width="100%">
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

                                <logic:present	name="STORE_ORDER_DETAIL_FORM" property="orderStatusDetail.customerShipTo">
                                <tr>
                                <td><b>Customer Shipping Name:</b></td>
                                <td><bean:write name="STORE_ORDER_DETAIL_FORM" property="orderStatusDetail.customerShipTo.shortDesc" filter="true"/>
                                        </td>
                                </tr>
                                <tr>
                                <td><b>Customer Shipping Address:</b></td>
                                <td><bean:write name="STORE_ORDER_DETAIL_FORM" property="orderStatusDetail.customerShipTo.address1" filter="true"/>
                                        </td>
                                </tr>
                                <tr>
                                <td><b>&nbsp;</b></td>
                                <td><bean:write name="STORE_ORDER_DETAIL_FORM" property="orderStatusDetail.customerShipTo.address2" filter="true"/>
                                        </td>
                                </tr>
                                <tr>
                                <td><b>&nbsp;</b></td>
                                <td><bean:write name="STORE_ORDER_DETAIL_FORM" property="orderStatusDetail.customerShipTo.address3" filter="true"/>
                                        </td>
                                </tr>
                                <tr>
                                <td><b>&nbsp;</b></td>
                                <td><bean:write name="STORE_ORDER_DETAIL_FORM" property="orderStatusDetail.customerShipTo.address4" filter="true"/>
                                        </td>
                                </tr>
                                <tr>
                                <td><b>City:</b></td>
                                <td><bean:write name="STORE_ORDER_DETAIL_FORM" property="orderStatusDetail.customerShipTo.city" filter="true"/>
                                        </td>
                                </tr>
                                <tr>
                                <td><b>State:</b></td>
                                <td><bean:write name="STORE_ORDER_DETAIL_FORM" property="orderStatusDetail.customerShipTo.stateProvinceCd" filter="true"/>
                                        </td>
                                </tr>
                                <tr>
                                <td><b>ZIP Code:</b></td>
                                <td><bean:write name="STORE_ORDER_DETAIL_FORM" property="orderStatusDetail.customerShipTo.postalCode" filter="true"/>
                                        </td>
                                </tr>
                                </logic:present>
                                <!--                             <tr>
                                <td><b>Ship From Name:</b></td>
                                <td><bean:write name="STORE_ORDER_DETAIL_FORM" property="orderStatusDetail.shipFromName" filter="true"/>
                                        </td>
                                </tr> -->
                                </table>
                </td>

                <td colspan="2">
                        <table ID=1082 width="100%">
                                <tr valign="top">
                                <td><b>Order Status:</b></td>
                                <td><bean:write name="STORE_ORDER_DETAIL_FORM" property="orderStatusDetail.orderDetail.orderStatusCd" filter="true"/>
                                </td>
                            </tr>
                                <tr>
                                        <td><b>Placed By:</b></td>
                                <td><bean:write name="STORE_ORDER_DETAIL_FORM" property="orderStatusDetail.orderDetail.addBy" filter="true"/>
                                        </td>
                                </tr>

<jsp:include flush='true' page="f_storeOrderCharges.jsp">
  <jsp:param name="allowEdits" value="false" />
  <jsp:param name="taxreadonly" value="<%=Boolean.TRUE%>" />
</jsp:include>


                        </table>
                </td>
        </tr>

        <tr>
          <td colspan="6">&nbsp;</b></td>
        </tr>
        <tr>
      <td colspan="6">
                <%int index = 1;%>
        <logic:iterate id="note" indexId="i" name="STORE_ORDER_DETAIL_FORM" property="orderPropertyList" scope="session" type="com.cleanwise.service.api.value.OrderPropertyData">
          <bean:define id="date" name="note" property="addDate"/>
          <table ID=1083>
            <tr><td><b>Note:</b> <%=index%></td></tr>
                <tr>
                  <td><b>Added By</b>: <bean:write name="note" property="addBy"/></td>
                </tr>
                <tr>
<td><b>Added Date:</b> <i18n:formatDate value="<%=date%>"
pattern="MM/dd/yyyy hh:mm a" locale="<%=Locale.US%>"/></td>
                </tr>
                <tr>
                  <td><bean:write name="note" property="value"/></td>
                </tr>
          </table>
          <hr>
          <%index++;%>
        </logic:iterate>

          </td>
        </tr>

        <tr>
                <td colspan="6">&nbsp;</td>
        </tr>

        <tr>
                <td colspan="6">

<logic:notEqual  name="STORE_ORDER_DETAIL_FORM" property="simpleServiceFlag" value="<%=Boolean.TRUE.toString()%>">
<table ID=1084 width="900" border="0" class="results">
<tr>
<td colspan="18"><span class="mediumheader"><b>Order Item Status:</b></span>
<bean:size id="itemCount" name="STORE_ORDER_DETAIL_FORM" property="orderItemDescList" />
<!-- <bean:write name="itemCount" />  -->
</td>
</tr>

<logic:present name="STORE_ORDER_DETAIL_FORM" property="orderItemDescList">

<tr><td colspan="18">&nbsp;</td></tr>
<tr><td colspan="18">
        <table ID=1085 align="center">
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
                                <bean:define id="lastdate" name="STORE_ORDER_DETAIL_FORM" property="lastDate"/>
<i18n:formatDate value="<%=lastdate%>"
pattern="MM/dd/yyyy  hh:mm a" locale="<%=Locale.US%>"/>
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
<tr><td colspan="18" class="mainbody">&nbsp;</td></tr>


<tr>
<td><b>Line&nbsp;#</b></td>
<td class="resultscolumna"><b><a ID=1086 href="storeOrderDetail.do?action=sortitems&sortField=distOrderNum">Dist Order#</b></td>
<td><b><a ID=1087 href="orderOpDetail.do?action=sortitems&sortField=outboundPoNum">
Outbound PO#</b></td>
<td class="resultscolumna"><b><a ID=1088 href="orderOpDetail.do?action=sortitems&sortField=cwSKU">CW SKU#</b></td>
<td><b><a ID=1089 href="storeOrderDetail.do?action=sortitems&sortField=distSKU">Dist SKU#</b></td>
<td class="resultscolumna"><b>Dist Name</b></td>
<td><b><a ID=1090 href="storeOrderDetail.do?action=sortitems&sortField=name">Product Name</b></td>
<td class="resultscolumna"><b>UOM</b></td>
<td><b>Pack</b></td>
<td class="resultscolumna"><b>Item Size</b></td>
<td><b>Customer Price</b></td>
<td class="resultscolumna"><b>CW Cost</b></td>
<td><b>Qty</b></td>
<td class="resultscolumna"><b>Status</b></td>
<td><b>PO Status</b></td>
<td class="resultscolumna"><b>Date</b></td>
<td><b>Target Ship Date</b></td>
<td class="resultscolumna"><b>Open Line Status</b></td>
</tr>

<logic:iterate id="itemele" indexId="i" name="STORE_ORDER_DETAIL_FORM" property="orderItemDescList" scope="session" type="com.cleanwise.service.api.value.OrderItemDescData">

 <bean:define id="key"  name="itemele" property="orderItem.orderItemId"/>
 <% String linkHref = new String("storeOrderItemDetail.do?action=view&id=" + key);%>

<tr><td colspan="18" class="mainbody"><img ID=1091 src="../<%=ip%>images/cw_descriptionseperator.gif" height="1" width="900"></td></tr>

<tr>
<td><bean:write name="itemele" property="orderItem.orderLineNum"/>&nbsp;</td>
<td class="resultscolumna"><bean:write name="itemele" property="orderItem.distOrderNum"/>&nbsp;</td>
<td>
        <logic:present  name="itemele" property="orderItem.outboundPoNum">
            <bean:write name="itemele" property="orderItem.outboundPoNum"/>
        </logic:present>
        <logic:notPresent  name="itemele" property="orderItem.outboundPoNum">
            N/A
        </logic:notPresent>
</td>
<td class="resultscolumna"><bean:write name="itemele" property="orderItem.itemSkuNum"/>&nbsp;</td>
<td><bean:write name="itemele" property="orderItem.distItemSkuNum"/>&nbsp;</td>
<td class="resultscolumna"><bean:write name="itemele" property="distName"/>&nbsp;</td>
<td><a ID=1092 href="<%=linkHref%>"><bean:write name="itemele" property="orderItem.itemShortDesc"/></a>&nbsp;</td>
<td class="resultscolumna"><bean:write name="itemele" property="orderItem.itemUom"/>&nbsp;</td>
<td><bean:write name="itemele" property="orderItem.itemPack"/>&nbsp;</td>
<td class="resultscolumna"><bean:write name="itemele" property="orderItem.itemSize"/>&nbsp;</td>
<td><bean:write name="itemele" property="orderItem.custContractPrice"/>&nbsp;</td>
<td class="resultscolumna"><bean:write name="itemele" property="orderItem.distItemCost"/>&nbsp;</td>
<td><bean:write name="itemele" property="orderItem.totalQuantityOrdered"/>&nbsp;</td>
<td class="resultscolumna">
<logic:present name="itemele" property="orderItem.orderItemStatusCd" >
        <bean:write name="itemele" property="orderItem.orderItemStatusCd"/>&nbsp;
</logic:present>
<logic:notPresent name="itemele" property="orderItem.orderItemStatusCd" >
        Ordered&nbsp;
</logic:notPresent>
</td>

<td>
<logic:present name="itemele" property="purchaseOrderData" >
        <bean:write name="itemele" property="purchaseOrderData.purchaseOrderStatusCd"/>
</logic:present>
&nbsp;
</td>

<td class="resultscolumna">
<logic:present name="itemele" property="orderItem.erpPoDate">
        <bean:define id="erppodate" name="itemele" property="orderItem.erpPoDate"/>
        <i18n:formatDate value="<%=erppodate%>"
pattern="MM/dd/yyyy" locale="<%=Locale.US%>"/>
</logic:present>
<logic:notPresent name="itemele" property="orderItem.erpPoDate">
        &nbsp;
</logic:notPresent>
</td>

<td>
<logic:present name="itemele" property="orderItem.targetShipDate">
        <bean:define id="dte" name="itemele" property="orderItem.targetShipDate"/>
        <i18n:formatDate value="<%=dte%>" pattern="MM/dd/yyyy" locale="<%=Locale.US%>"/>
</logic:present>
<logic:notPresent name="itemele" property="orderItem.targetShipDate">
        &nbsp;
</logic:notPresent>
</td>

<td class="resultscolumna"><bean:write name="itemele" property="openLineStatusCd"/></td>
</tr>


 <!-- the order item actions -->
        <logic:present name="itemele" property="orderItemActionList">

        <bean:size id="detailCount" name="itemele" property="orderItemActionList" />
        <logic:greaterThan name="detailCount" value="0">
        <logic:iterate id="detailele_a"
        indexId="j"
        name="itemele"
        property="orderItemActionList"
        type="com.cleanwise.service.api.value.OrderItemActionData">

        <bean:define id="detailkey"  name="detailele_a" property="orderItemActionId"/>
        <% String detailLinkHref = new String("orderOpItemUpdate.do?action=edit&id=" + key + "&detailid=" + detailkey);%>

        <bean:define id="actionNum" name="detailele_a" property="actionCd" type="java.lang.String"/>
        <% 	String itemStatus = new String("");
                itemStatus = actionNum;
        %>



<tr>
<td><bean:write name="itemele" property="orderItem.orderLineNum"/>&nbsp;</td>
<td class="resultscolumna">&nbsp;</td>
<td>&nbsp;</td>
<td class="resultscolumna"><bean:write name="detailele_a" property="affectedSku"/>&nbsp;</td>
<td>&nbsp;</td>
<td class="resultscolumna">&nbsp;</td>
<td>&nbsp;</td>
<td class="resultscolumna">&nbsp;</td>
<td>&nbsp;</td>
<td class="resultscolumna">&nbsp;</td>
<td>&nbsp;</td>
<td class="resultscolumna">&nbsp;</td>
<td><bean:write name="detailele_a" property="quantity"/>&nbsp;</td>
<td class="resultscolumna"><%=itemStatus%>&nbsp;</td>
<td>&nbsp;</td>

<logic:present name="detailele_a" property="actionDate">
<td class="resultscolumna">
        <bean:define id="date" name="detailele_a" property="actionDate"/>
        <i18n:formatDate value="<%=date%>" pattern="MM/dd/yyyy" locale="<%=Locale.US%>"/>
</td>
</logic:present>
<logic:notPresent name="detailele_a" property="actionDate">
<td class="resultscolumna">&nbsp;</td>
</logic:notPresent>
<td>&nbsp;</td>
<td class="resultscolumna">&nbsp;</td>
</tr>
        </logic:iterate>

        </logic:greaterThan>

        </logic:present>
<!--end order item actions-->
<!-- the order item notes -->
        <logic:present name="itemele" property="orderItemNotes">

        <bean:size id="detailCount" name="itemele" property="orderItemNotes" />
        <logic:greaterThan name="detailCount" value="0">
        <logic:iterate id="detailele" indexId="j" name="itemele" property="orderItemNotes" type="com.cleanwise.service.api.value.OrderPropertyData">
        <bean:define id="note" name="detailele" property="value" type="java.lang.String"/>

        <tr>
        <td><bean:write name="itemele" property="orderItem.orderLineNum"/>&nbsp;</td>
        <td class="resultscolumna">&nbsp;</td>
        <td>&nbsp;</td>
        <td class="resultscolumna"><bean:write name="itemele" property="orderItem.itemSkuNum"/>&nbsp;</td>
        <td>&nbsp;</td>
        <td colspan=9><bean:write name="detailele" property="value"/></td>
        <td>&nbsp;</td>
        <logic:present name="detailele" property="addDate">
        <td class="resultscolumna">
                <bean:define id="date" name="detailele" property="addDate"/>
                <i18n:formatDate value="<%=date%>" pattern="MM/dd/yyyy hh:mm a" locale="<%=Locale.US%>"/>
        </td>
        </logic:present>
        <logic:notPresent name="detailele" property="addDate">
        <td class="resultscolumna">&nbsp;</td>
        </logic:notPresent>
        </tr>

        </logic:iterate>
        </logic:greaterThan>
        </logic:present>
<!-- end order item notes -->


</logic:iterate>

</logic:present>

</table>
</logic:notEqual>
<logic:equal  name="STORE_ORDER_DETAIL_FORM" property="simpleServiceFlag" value="<%=Boolean.TRUE.toString()%>">
 <div class="results">
  <jsp:include flush='true' page="storeOrderServicePrintStatusDetail.jsp"/>
</div>
</logic:equal>
                </td>
        </tr>

</html:form>
</table>
<br>
<table ID=1093 border="2" width="100%">
  <tr>
    <td><b>Note:</b>  In order to print and view all fields, you must set your browser to "Landscape".  <br><br>
<b>If you have Internet Explorer:</b><br>
Go to File:  Page Setup.  Under Orientation, Click "Landscape", then "OK".
<br>To Print, just go to File: Print. <br><br>
<b>If you have Netscape:</b><br>
Go to File: Print.  Click on the "Properties" button.  <br>
Select the "Layout" tab.  Under Orientation, Click "Landscape", then "OK".
</td>
  </tr>
</table>
</div>
</body>
</html:html>
