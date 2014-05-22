<%@ page import="java.util.Locale" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<table ID=1108 class="result">
<tr>
<td colspan="18"><span class="mediumheader"><b>Order Service Status:</b></span>
<bean:size id="itemCount" name="STORE_ORDER_DETAIL_FORM" property="orderItemDescList" />
</td>
</tr>

<logic:present name="STORE_ORDER_DETAIL_FORM" property="orderItemDescList">

<tr><td colspan="18">&nbsp;</td></tr>
<tr><td colspan="18">
        <table ID=1109 align="center">
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
<td class="resultscolumna"><b><a ID=1110 href="storeOrderDetail.do?action=sortitems&sortField=distOrderNum">Dist Order#</b></td>
<td><a ID=1111 href="storeOrderDetail.do?action=sortitems&sortField=asset"><b>Asset Name</b></a></td>
<td class="resultscolumna"><b><a ID=1112 href="storeOrderDetail.do?action=sortitems&sortField=distSKU">Dist SKU#</b></td>
<td><b>Dist Name</b></td>
<td class="resultscolumna"><a ID=1113 href="storeOrderDetail.do?action=sortitems&sortField=name"><b>Service Name</b></a></td>
<td><b>Customer Price</b></td>
<td class="resultscolumna"><b>CW Cost</b></td>
<td><b>Status</b></td>
<td class="resultscolumna"><b>PO Status</b></td>
<td><b>Date</b></td>
<td class="resultscolumna"><b>Target Ship Date</b></td>
<td><b>Open Line Status</b></td>
</tr>

<logic:iterate id="itemele" indexId="i" name="STORE_ORDER_DETAIL_FORM" property="orderItemDescList" scope="session" type="com.cleanwise.service.api.value.OrderItemDescData">

 <bean:define id="key"  name="itemele" property="orderItem.orderItemId"/>
 <% String linkHref = new String("storeOrderItemDetail.do?action=view&id=" + key);%>

<tr><td colspan="18" class="mainbody"><img ID=1114 src="../<%=ip%>images/cw_descriptionseperator.gif" height="1" width="900"></td></tr>

<tr>
    <td>
        <bean:write name="itemele" property="orderItem.orderLineNum"/>
        &nbsp;
    </td>
    <td   class="resultscolumna">
        <bean:write name="itemele" property="orderItem.distOrderNum"/>
        &nbsp;
    </td>
    <td>
        <logic:present name="itemele" property="assetInfo">
            <bean:write name="itemele" property="assetInfo.shortDesc"/>
        </logic:present>

    </td>
    <td   class="resultscolumna"><bean:write name="itemele" property="orderItem.distItemSkuNum"/>&nbsp;</td>

<td><bean:write name="itemele" property="distName"/>&nbsp;</td>
<td class="resultscolumna"><a ID=1115 href="<%=linkHref%>"><bean:write name="itemele" property="orderItem.itemShortDesc"/></a>&nbsp;</td>
<td><bean:write name="itemele" property="orderItem.custContractPrice"/>&nbsp;</td>
<td class="resultscolumna"><bean:write name="itemele" property="orderItem.distItemCost"/>&nbsp;</td>
<td>
<logic:present name="itemele" property="orderItem.orderItemStatusCd" >
        <bean:write name="itemele" property="orderItem.orderItemStatusCd"/>&nbsp;
</logic:present>
<logic:notPresent name="itemele" property="orderItem.orderItemStatusCd" >
        Ordered&nbsp;
</logic:notPresent>
</td>

<td  class="resultscolumna">
<logic:present name="itemele" property="purchaseOrderData" >
        <bean:write name="itemele" property="purchaseOrderData.purchaseOrderStatusCd"/>
</logic:present>
&nbsp;
</td>

<td>
<logic:present name="itemele" property="orderItem.erpPoDate">
        <bean:define id="erppodate" name="itemele" property="orderItem.erpPoDate"/>
        <i18n:formatDate value="<%=erppodate%>"
pattern="MM/dd/yyyy" locale="<%=Locale.US%>"/>
</logic:present>
<logic:notPresent name="itemele" property="orderItem.erpPoDate">
        &nbsp;
</logic:notPresent>
</td>

<td  class="resultscolumna">
<logic:present name="itemele" property="orderItem.targetShipDate">
        <bean:define id="dte" name="itemele" property="orderItem.targetShipDate"/>
        <i18n:formatDate value="<%=dte%>" pattern="MM/dd/yyyy" locale="<%=Locale.US%>"/>
</logic:present>
<logic:notPresent name="itemele" property="orderItem.targetShipDate">
        &nbsp;
</logic:notPresent>
</td>

<td><bean:write name="itemele" property="openLineStatusCd"/></td>
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
        <% 	String itemStatus = new String("");    itemStatus = actionNum;%>



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
<td class="resultscolumna"><%=itemStatus%>&nbsp;</td>

<logic:present name="detailele_a" property="actionDate">
<td>
        <bean:define id="date" name="detailele_a" property="actionDate"/>
        <i18n:formatDate value="<%=date%>" pattern="MM/dd/yyyy" locale="<%=Locale.US%>"/>
</td>
</logic:present>
<logic:notPresent name="detailele_a" property="actionDate">
<td>&nbsp;</td>
</logic:notPresent>
<td class="resultscolumna">&nbsp;</td>
<td>&nbsp;</td>
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
        <td class="resultscolumna">&nbsp;</td>
        <td colspan=5><bean:write name="detailele" property="value"/></td>
        <td class="resultscolumna">&nbsp;</td>
         <logic:present name="detailele" property="addDate">
        <td>
                <bean:define id="date" name="detailele" property="addDate"/>
                <i18n:formatDate value="<%=date%>" pattern="MM/dd/yyyy hh:mm a" locale="<%=Locale.US%>"/>
        </td>
        </logic:present>
        <logic:notPresent name="detailele" property="addDate">
        <td class="resultscolumna">&nbsp;</td>
        </logic:notPresent>
        <td class="resultscolumna">&nbsp;</td>
        <td>&nbsp;</td>
        </tr>

        </logic:iterate>
        </logic:greaterThan>
        </logic:present>
<!-- end order item notes -->


</logic:iterate>

</logic:present>

</table>