<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.*" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Iterator" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>
<script language="JavaScript1.2">
    <!--

    function actionMultiSubmit(actionDef, action) {
        var aaal = document.getElementsByName('action');
        for (var i = 0; i < 1; i++) {
            var aaa = aaal[i];
            aaa.value = action;
            aaa.form.submit();
        }
        return false;
    }

    //-->

</script>


<% CleanwiseUser appUser = (CleanwiseUser)
        session.getAttribute(Constants.APP_USER); %>


<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>
<bean:define id="theForm" name="ORDER_OP_DETAIL_FORM" type="com.cleanwise.view.forms.OrderOpDetailForm"/>
<%
    OrderStatusDescData orderStatusDetail = theForm.getOrderStatusDetail();
    String orderLocale = orderStatusDetail.getOrderDetail().getLocaleCd();
    ClwI18nUtil clwI18n = ClwI18nUtil.getInstance(request, orderLocale, -1);
    SimpleDateFormat sdfInter = new SimpleDateFormat("yyyy-MM-dd");


    boolean useCustPo = true;
    String requestPoNum = orderStatusDetail.getOrderDetail().getRequestPoNum();

    if (requestPoNum == null || requestPoNum.equals("") ||
            requestPoNum.equalsIgnoreCase("na") || requestPoNum.equalsIgnoreCase("n/a")) {
        useCustPo = false;
    }
%>


<bean:define id="clwWorkflowRole" type="java.lang.String" name="ApplicationUser" property="user.workflowRoleCd"/>

<bean:define id="orderStatus" name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.orderDetail.orderStatusCd"
             type="String"/>


<script language="JavaScript1.2">

    <!--
    function viewPrinterFriendly(oid) {
        var loc = "printerFriendlyOrder.do?action=orderStatus&orderId=" + oid;
        prtwin = window.open(loc, "printer_friendly",
                "menubar=yes,resizable=yes,scrollbars=yes,toolbar=yes,status=yes,height=500,width=775,left=100,top=165");
        prtwin.focus();

        return false;
    }
    //-->

</script>

<div class="text"><font color=red>
    <html:errors/>
</font></div>


<table align=center CELLSPACING=0 CELLPADDING=10 width="<%=Constants.TABLEWIDTH%>" class="tbstd">
    <tr class="orderInfoHeader">
        <td align="left" class="text"><b>
            <app:storeMessage key="shop.catalog.text.orderInformation"/>
        </b></td>
    </tr>

</table>


<table align=center CELLSPACING=0 CELLPADDING=0 width="<%=Constants.TABLEWIDTH%>">

<html:form name="ORDER_OP_DETAIL_FORM" action="/store/userOrderDetail.do"
           type="com.cleanwise.view.forms.OrderOpDetailForm">

<logic:present name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail">

<bean:define id="lOrderId" name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.orderDetail.orderId"/>


<tr>
<td style="border-right: solid 1px black; border-left: solid 1px black; font-weight: bold;">

<table cellpadding=0 cellspacing=0 align=center>

<tr>
<td colspan="3">

<table width=750 cellpadding=2 cellspacing=0>
<tr>
<td>
<table width=355>
<tr>
    <td class="text"><b>
        <app:storeMessage key="shop.catalog.text.orderNumber"/>
    </b></td>
    <td>
        <bean:write name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.orderDetail.orderNum"/>
    </td>
</tr>
<logic:present name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.refOrder">
    <tr>
        <td class="text"><b>
            <app:storeMessage key="shop.catalog.text.referenceOrderNum"/>
        </b></td>
        <td>
            <bean:write name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.refOrder.orderNum"/>
        </td>
    </tr>
</logic:present>

<tr>
    <td class="text"><b>
        <app:storeMessage key="shop.orderStatus.text.orderDate"/>
    </b></td>
    <td>
        <bean:define id="ordd" type="java.util.Date"
                     name="ORDER_OP_DETAIL_FORM"
                     property="orderStatusDetail.orderDetail.originalOrderDate"/>
        <i18n:formatDate value="<%=ordd%>" pattern="yyyy-MM-dd"/>
    </td>
</tr>

<logic:present name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.requestedShipDate">
    <bean:define id="ordreqd" type="java.lang.String"
                 name="ORDER_OP_DETAIL_FORM"
                 property="orderStatusDetail.requestedShipDate"/>
    <% if (ordreqd != null && ordreqd.length() > 0) { %>
    <tr>
        <td class="text"><b>
            <app:storeMessage key="shop.orderStatus.text.requestedShipDate"/>
        </b></td>
        <td>
            <bean:write
                    name="ORDER_OP_DETAIL_FORM"
                    property="orderStatusDetail.requestedShipDate"/>
        </td>
    </tr>
    <% } %>
</logic:present>
<tr>


    <%
        AccountData accountD = SessionTool.getAccountData(request, orderStatusDetail.getOrderDetail().getAccountId());
        if (accountD == null) {
            accountD = appUser.getUserAccount();
        }
        SiteData siteD = SessionTool.getSiteData(request, orderStatusDetail.getOrderDetail().getSiteId());
        if (siteD == null) {
            siteD = appUser.getSite();
        }
        boolean allowPoEntry = true;
        if (RefCodeNames.CUSTOMER_SYSTEM_APPROVAL_CD.PUNCH_OUT_INLIN_NON_E_ORD_ONLY.
                equals(accountD.getCustomerSystemApprovalCd())) {
            allowPoEntry = false;
        }
        if (!accountD.isCustomerRequestPoAllowed()) {
            allowPoEntry = false;
        }

        boolean usingBlanketPo = false;
        if (siteD != null && siteD.getBlanketPoNum() != null && siteD.getBlanketPoNum().getBlanketPoNumId() != 0) {
            usingBlanketPo = true;
            allowPoEntry = false;
        }
    %>

    <td class="text"><b>
        <app:storeMessage key="shop.orderStatus.text.poNumber"/>
    </b></td>
    <td>
        <%
            String thisPoNum = orderStatusDetail.getOrderDetail().getRequestPoNum();
            if (null == thisPoNum) thisPoNum = "";
            if (
                    orderStatus.equals(RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL)
                            && appUser.canMakePurchases() && allowPoEntry
                    ) { %>


        <html:text name="ORDER_OP_DETAIL_FORM" property="requestPoNum"
                   value="<%=thisPoNum%>" size="30"
                />
        <% } else { %>

        <bean:write name="ORDER_OP_DETAIL_FORM"
                    property="orderStatusDetail.orderDetail.requestPoNum"/>

        <% } %>
    </td>
</tr>
<tr>
    <td class="text"><b>
        <app:storeMessage key="shop.orderStatus.text.method"/>
    </b></td>
    <td>
        <bean:write name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.orderDetail.orderSourceCd"/>
    </td>
</tr>
<% if (RefCodeNames.ORDER_STATUS_CD.PENDING_DATE.equals(orderStatus)) {%>
<tr>
    <td class="text"><b>
        <app:storeMessage key="shop.orderStatus.text.processOrderOn"/>
    </b></td>
    <td>
        <%
            String processOnDateS = theForm.getOrderStatusDetail().getPendingDate();
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            java.util.Date processOnDate = null;
            if (processOnDateS != null && processOnDateS.trim().length() > 0) {
                processOnDate = sdf.parse(processOnDateS);
        %>
        <%=(new SimpleDateFormat("yyyy-MM-dd")).format(processOnDate)%>
        <% } %>
    </td>
</tr>
<% } %>
</table>

</td>
<td>

    <table align=left>
        <tr>
            <td class="text"><b>
                <app:storeMessage key="shop.orderStatus.text.contactName"/>
            </b></td>
            <td>
                <bean:write name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.orderDetail.orderContactName"/>
            </td>
        </tr>
        <tr>
            <td class="text"><b>
                <app:storeMessage key="shop.orderStatus.text.contactPhone"/>
            </b></td>
            <td>
                <bean:write name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.orderDetail.orderContactPhoneNum"/>
            </td>
        </tr>
        <tr>
            <td class="text"><b>
                <app:storeMessage key="shop.orderStatus.text.contactEmail"/>
            </b></td>
            <td>
                <bean:write name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.orderDetail.orderContactEmail"/>
            </td>
        </tr>
        <tr>
            <td class="text"><b>
                <app:storeMessage key="shop.orderStatus.text.placedBy"/>
            </b></td>
            <td>
                    <bean:write name="ORDER_OP_DETAIL_FORM" property="orderPlacedBy"/>
                (<bean:write name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.orderDetail.addBy"/>)
        </tr>
    </table>

</td>
</tr>


<tr>
    <td class="text" colspan=3><b>
        <app:storeMessage key="shop.orderStatus.text.comments:"/>
    </b>
        <bean:write name="ORDER_OP_DETAIL_FORM"
                    property="orderStatusDetail.orderDetail.comments"/>
    </td>
</tr>

<bean:define id="opDV " type="com.cleanwise.service.api.value.OrderPropertyDataVector"
             name="ORDER_OP_DETAIL_FORM" property="orderPropertyList"/>
<% //Workflow properties
    IdVector canApproveIdV = new IdVector();

    if (opDV.size() > 0) {
        canApproveIdV = (IdVector) session.getAttribute("note.approve.ids");
%>
<tr>
    <td colspan="3" class="text"><b>
        <app:storeMessage key="shop.orderStatus.text.notes:"/>
    </b></td>
<tr>
        <%

        for(int ii=0; ii<opDV.size(); ii++) {
          OrderPropertyData opD = (OrderPropertyData) opDV.get(ii);
          if("Workflow Note".equals(opD.getShortDesc())){


        %>
<tr>
    <td colspan="3">
        <% if (opD.getApproveDate() != null) { %>
        <%=opD.getValue()%>

        (
        <app:storeMessage key="shop.orderStatus.text.clearedOnBy"
                          arg0="<%=sdfInter.format(opD.getApproveDate())%>"
                          arg1="<%=opD.getModBy()%>"/>
        )
        <% } %>
        <% } %>
        <% } %>
        <% } %>
    </td>
</tr>
<%//END Workflow properties%>

<%

    String accountName = "";

/* Handle orders that have been canceled. */

    if (theForm.getOrderStatusDetail().getBillTo() != null &&
            theForm.getOrderStatusDetail().getBillTo().getShortDesc() != null) {
        accountName = theForm.getOrderStatusDetail().getBillTo().getShortDesc();
    }

    if (null == accountName) accountName = "";

%>

<logic:present name="ORDER_OP_DETAIL_FORM" property="customerOrderNotes">
    <tr>
        <td class="text" colspan="2">
            <table>

                <bean:size id="custNotesCt" name="ORDER_OP_DETAIL_FORM" property="customerOrderNotes"/>
                <logic:greaterThan name="custNotesCt" value="0">
                <tr>
                    <td class="text" colspan="2"><b>
                        <app:storeMessage key="label.display.customer.order.notes"
                                          arg0="<%=accountName%>"/>
                        :</b></td>
                </tr>
                <logic:iterate name="ORDER_OP_DETAIL_FORM" property="customerOrderNotes" id="note"
                               type="com.cleanwise.service.api.value.OrderPropertyData">
                <tr>
                    <td class="text" width="150"
                        style="vertical-align:top; padding-left:20; white-space: nowrap;">

                        <bean:define id="commentDate" name="note" property="addDate"
                                     type="java.util.Date"/>
                        <b>
                            <i18n:formatDate value="<%=commentDate%>" pattern="yyyy-MM-dd k:mm"/>
                            -
                            <bean:write name="note" property="addBy"/>
                        </b>
                    </td>
                    <td style="text-align:left;">
                        <app:writeHTML name="note" property="value"/>
                    </td>

                    </logic:iterate>
                    </logic:greaterThan>
            </table>
        </td>
    </tr>
</logic:present>

<%if (appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.ADD_CUSTOMER_ORDER_NOTES)) {%>
<tr>
    <td colspan="2" class="text"><b>
        <app:storeMessage key="label.customer.order.notes" arg0="<%=accountName%>"/>
        :</b><br>
        <html:textarea name="ORDER_OP_DETAIL_FORM" property="customerComment" rows="3" cols="55"/>
        <html:submit property="action" styleClass="store_fb"
                     onclick="actionMultiSubmit('BBBBBBB', 'button.addCustomerComment');">
            <app:storeMessage key="button.addCustomerComment"/>
        </html:submit>
    </td>
</tr>
<%}%> <%--is authorized for ADD_CUSTOMER_ORDER_NOTES check--%>

</table>


</td>
</tr>

<tr>
    <td colspan=3 class="tableoutline"><img src="/images/cw_spacer.gif" height="1"></td>
</tr>
<tr>
<td colspan=3>
<table align=center width="100%" cellpadding=0 cellspacing=0>
<% /* Start - List the items for the order. */ %>
<%String lastErpPoNum = null;%>
<tr>
    <td class="shopcharthead">
        <div>
            <app:storeMessage key="userAssets.text.assetName"/>
        </div>
    </td>
    <td class="shopcharthead">
        <div>
            <app:storeMessage key="userAssets.text.assetNumber"/>
        </div>
    </td>
    <td class="shopcharthead">
        <div>
            <app:storeMessage key="userAssets.text.assetSerial"/>
        </div>
    </td>
    <td class="shopcharthead">
        <div>
            <app:storeMessage key="shoppingServices.text.ourServiceName"/>
        </div>
    </td>
    <td class="shopcharthead">
        <div>
            <app:storeMessage key="shoppingItems.text.price"/>
        </div>
    </td>

    <td class="shopcharthead">
        <div>
            <app:storeMessage key="shoppingItems.text.status"/>
        </div>
    </td>
</tr>
<logic:iterate id="item" name="ORDER_OP_DETAIL_FORM" property="orderItemDescList"
               offset="0" indexId="oidx" type="com.cleanwise.service.api.value.OrderItemDescData">
<bean:define id="qty" name="item" property="orderItem.totalQuantityOrdered" type="Integer"/>

<%java.math.BigDecimal custItemPrice = new BigDecimal(0.00);%>
<logic:present name="item" property="orderItem.custContractPrice">
    <%custItemPrice = item.getOrderItem().getCustContractPrice();%>
</logic:present>

<%--Display the distributor and the purchase order number if the user did not enter a po number for this order, it is a distributor store type,
and the po number has been created--%>

<%if (!useCustPo) {%>
<logic:equal name="<%=Constants.APP_USER%>" property="userStore.storeType.value"
             value="<%=RefCodeNames.STORE_TYPE_CD.DISTRIBUTOR%>">
    <%
        if (item.getOrderItem().getErpPoNum() != null && !item.getOrderItem().getErpPoNum().equals(lastErpPoNum)) { //display po header text
            lastErpPoNum = item.getOrderItem().getErpPoNum();
    %>
    <tr bgcolor="<%=ClwCustomizer.getEvenRowColor(session,(Integer)oidx)%>">
        <td colspan="4">
            <b>
                <app:storeMessage key="shop.orderStatus.text.distributor"/>
            </b>
            <bean:write name="item" property="distRuntimeDisplayName"/>
        </td>
        <td colspan="4">
            <b>
                <app:storeMessage key="shop.orderStatus.text.purchaseOrder"/>
            </b>
            <bean:write name="item" property="orderItem.erpPoNum"/>
        </td>
        <td>
            <logic:present name="item" property="orderFreight">
                <b>
                    <app:storeMessage key="shop.orderStatus.text.freightOption:"/>
                </b>
                <bean:write name="item" property="orderFreight.shortDesc"/>
                <logic:present name="item" property="orderFreight.amount">
                    <bean:define id="amount" name="item" property="orderFreight.amount"/>
                    <br>
                    <b>
                        <app:storeMessage key="shop.orderStatus.text.amount:"/>
                    </b>
                    <%=clwI18n.priceFormat(amount, "&nbsp;")%>
                </logic:present>
            </logic:present>
        </td>
    </tr>
    <%}//end deisplay po header text%>
</logic:equal>
<%}%>
<%--Display the distributor and the purchase order number--%>

<tr bgcolor="<%=ClwCustomizer.getEvenRowColor(session,(Integer)oidx)%>">
    <bean:define id="servId" name="item" property="itemInfo.itemId"/>
    <td>
        <logic:present name="item" property="assetInfo">
            <bean:write name="item" property="assetInfo.shortDesc"/>
        </logic:present>
    </td>
    <td>
        <logic:present name="item" property="assetInfo">
            <bean:write name="item" property="assetInfo.assetNum"/>
        </logic:present>
    </td>
    <td>
        <logic:present name="item" property="assetInfo">
            <bean:write name="item" property="assetInfo.serialNum"/>
        </logic:present>
    </td>
    <td>
        <bean:write name="item" property="itemInfo.shortDesc"/>
    </td>

    <td>
        <%=clwI18n.priceFormat(custItemPrice, "&nbsp;")%>

    </td>

    <td>
        <% if (RefCodeNames.ORDER_ITEM_STATUS_CD.CANCELLED.
                equals(item.getOrderItem().getOrderItemStatusCd())) { %>
        <app:storeMessage key="shoppingItems.text.cancelled"/>
        <% } else { %>
        &nbsp;
        <% } %>
    </td>
</tr>
<%
    if (theForm.getShowDistNoteFl()) {
        OrderPropertyData distNote = item.getDistPoNote();
        if (distNote != null && Utility.isSet(distNote.getValue())) {
%>
<tr bgcolor="<%=ClwCustomizer.getEvenRowColor(session,(Integer)oidx)%>">
    <td colspan='8'><i>Dist. Note:</i>&nbsp;<%=distNote.getValue()%>
    </td>
</tr>
<% }
} %>
</logic:iterate>

</table>
<% /* End   - List the items for the order. */ %>

</td>
</tr>


<tr>
    <td colspan=3 class="tableoutline"><img src="/images/cw_spacer.gif" height="1"></td>
</tr>
<tr class="checkoutSummaryHeader">
    <td align="center" class="text"><b>&raquo;
        <app:storeMessage key="shop.orderStatus.text.orderSummary"/>
    </b></td>
    <td align="center" class="text"><b>&raquo;
        <app:storeMessage key="shop.orderStatus.text.shipTo"/>
    </b></td>
    <td align="center" class="text"><b>&raquo;
        <app:storeMessage key="shop.orderStatus.text.billTo"/>
    </b></td>
</tr>
<tr>
    <td colspan=3 class="tableoutline"><img src="/images/cw_spacer.gif" height="1"></td>
</tr>

<tr valign=top>

<td class="rowb">
<table align=center>
<% /* Order summary data. */ %>
<tr>
    <td class="smalltext"><b>
        <app:storeMessage key="shop.orderStatus.text.subtotal:"/>
    </b></td>
    <td align=right>
        <bean:define id="subtotal" name="ORDER_OP_DETAIL_FORM" property="subTotal"/>
        <%=clwI18n.priceFormat(subtotal, "&nbsp;")%>
    </td>
</tr>
<% boolean freightHandFl = false; %>
<bean:define id="freightcost" name="ORDER_OP_DETAIL_FORM"
             property="totalFreightCost" type="BigDecimal"/>
<% if (freightcost != null && 0.001 < java.lang.Math.abs(freightcost.doubleValue())) {
    freightHandFl = true;
%>
<tr>
    <td class="smalltext"><b>
        <app:storeMessage key="shop.orderStatus.text.freight:"/>
    </b></td>
    <td align=right>

        <%=clwI18n.priceFormat(freightcost, "&nbsp;")%>

    </td>
</tr>
<%}%>

<%--
<% if (theForm.getRushOrderCharge() != null) { %>
<bean:define id="rushCharge" name="ORDER_OP_DETAIL_FORM"
             property="rushOrderCharge" type="BigDecimal"/>
<% if (rushCharge != null) { %>
<tr>
    <td class="smalltext"><b>
        <app:storeMessage key="shop.orderStatus.text.rushOrderCharge:"/>
    </b></td>
    <td align=right>
        <%=clwI18n.priceFormat(rushCharge, "&nbsp;")%>

    </td>
</tr>

<% }
} %>--%>

<!-- the misc cost-->
<% if (theForm.getTotalMiscCost() != null) { %>
<bean:define id="misc" name="ORDER_OP_DETAIL_FORM" property="totalMiscCost" type="BigDecimal"/>
<% if (misc != null && 0.001 < java.lang.Math.abs(misc.doubleValue())) {
    freightHandFl = true;
%>
<tr>
    <td class="text"><b>
            <app:storeMessage key="shop.orderStatus.text.handling:"/><b></td>
    <td align=right>

        <%=clwI18n.priceFormat(misc, "&nbsp;")%>

    </td>
</tr>
<% }
} %>

<!-- the tax cost-->
<logic:present name="ORDER_OP_DETAIL_FORM" property="totalTaxCost">
    <bean:define id="tax" name="ORDER_OP_DETAIL_FORM" property="totalTaxCost" type="Double"/>
    <% if (tax != null && 0.001 < java.lang.Math.abs(tax.doubleValue())) { %>
    <tr>
        <td class="smalltext"><b>
            <app:storeMessage key="shop.orderStatus.text.tax:"/>
        </b></td>
        <td align=right>
            <%=clwI18n.priceFormat(tax, "&nbsp;")%>
        </td>
    </tr>
    <% } %>
</logic:present>

<tr>
    <td class="smalltext"><b>
        <app:storeMessage key="shop.orderStatus.text.totalExcludingVOC:"/>
    </b></td>
    <td align=right>
        <bean:define id="total" name="ORDER_OP_DETAIL_FORM" property="totalAmount"/>
        <%=clwI18n.priceFormat(total, "&nbsp;")%>
    </td>
</tr>


<tr>
    <td class="smalltext"><b>
        <app:storeMessage key="shop.orderStatus.text.orderStatus:"/>
    </b></td>
    <td><%=com.cleanwise.view.utils.ShopTool.xlateStatus(orderStatusDetail)%>
    </td>
</tr>

</table>
<% /* Order summary data. */ %>

</td>

<td>

    <table align=center><% /* Ship to data. */ %>

        <tr valign=top>
            <td class="smalltext"><b>
                <app:storeMessage key="shop.orderStatus.text.address:"/>
            </b></td>
            <td>
                <bean:write name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.shipTo.shortDesc"/>
                <br>
                <bean:write name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.shipTo.address1"/>
                <br>
                <bean:write name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.shipTo.address2"/>
                <br>
                <bean:write name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.shipTo.address3"/>
                <br>
                <bean:write name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.shipTo.address4"/>
                <br>
                <bean:write name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.shipTo.city"/>
                <br>
                <bean:write name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.shipTo.postalCode"/>
            </td>
        </tr>
    </table>
    <% /* Ship to data. */ %>

</td>

<td>
<table align=center> <% /* Account bill to information. */ %>
    <td class="smalltext"><b>
        <app:storeMessage key="shop.orderStatus.text.address:"/>
    </b></td>
    <td>
        <bean:write name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.billTo.shortDesc"/>
        <br>
        <bean:write name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.billTo.address1"/>
        <br>
        <bean:write name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.billTo.address2"/>
        <br>
        <bean:write name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.billTo.address3"/>
        <br>
        <bean:write name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.billTo.address4"/>
        <br>
        <bean:write name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.billTo.city"/>
        <br>
        <bean:write name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.billTo.postalCode"/>
    </td>

</tr>
</table>


</td>

</tr>

<tr>
    <td colspan=3 class="tableoutline"><img src="/images/cw_spacer.gif" height="1"></td>
</tr>

<tr>

</tr>
</table>
</td>

</tr>
</logic:present>

<logic:notPresent name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail">

    <tr align=center>
        <td style="border-right: solid 1px black; border-left: solid 1px black;
  font-weight: bold;">
            <br><br><br><br>
            <app:storeMessage key="shop.orderStatus.text.NoOrderDataAvailable"/>
            <br><br><br><br>
        </td>
    </tr>

</logic:notPresent>
<html:hidden property="action" value="BBBBBBB"/>
<html:hidden property="action" value="CCCCCCC"/>
</html:form>


</table>
<jsp:include flush='true' page="t_userOrderDetailInvoice.jsp"/>

<jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_table_bottom1.jsp")%>' />



