<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="java.util.Locale" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.view.logic.StoreOrderLogic" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>
<%int colNum = 15; %>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="theForm" name="STORE_ORDER_DETAIL_FORM" type="com.cleanwise.view.forms.StoreOrderDetailForm"/>
<bean:define id="userType" type="java.lang.String" name="ApplicationUser" property="user.userTypeCd"/>
<bean:define id="userStore" type="java.lang.String" name="ApplicationUser" property="userStore.storeType.value"/>

<%
    OrderStatusDescData orderStatusDetail = theForm.getOrderStatusDetail();
    OrderData orderD = orderStatusDetail.getOrderDetail();

    String orderStatus = orderD.getOrderStatusCd();
    if (orderStatus == null) orderStatus = "";
    String orderSource = orderD.getOrderSourceCd();
    if (orderSource == null) orderSource = "";

    String siteId = request.getParameter("siteId");


    //Control Flags
    //boolean  = false;
    boolean adminFlag = true;
    boolean erpInactive = false;
    boolean erpActiveAllowChange = false;
    boolean nonEdiOrder = false;
    boolean ediAllowChange = false;
    boolean fullControl = true;
    String userId = String.valueOf(orderD.getUserId());

    if (StoreOrderLogic.isOrderStatusValid(orderStatus, fullControl)) {
        erpInactive = true;
    }

    if (RefCodeNames.ORDER_STATUS_CD.ERP_RELEASED.equals(orderStatus) ||
                    RefCodeNames.ORDER_STATUS_CD.PROCESS_ERP_PO.equals(orderStatus)) {
        erpActiveAllowChange = true;
    }

    nonEdiOrder = true;
    ediAllowChange = theForm.getOrderStatusDetail().getAllowModifFl();
    boolean allowWhenNoErpNoEdi = adminFlag && erpInactive && (nonEdiOrder || fullControl);
    boolean allowWhenErpEdi = adminFlag && ((erpActiveAllowChange && ediAllowChange) || fullControl);
    if (allowWhenNoErpNoEdi || allowWhenErpEdi)  colNum++;
    if (RefCodeNames.STORE_TYPE_CD.MLA.equals(userStore)) colNum++;
%>

<table ID=1116 width="900" border="0" class="results">
<%-- BEGIN Items --%>
<tr>
    <td colspan="<%=colNum%>"><span class="mediumheader"><b>Order Service Status:</b></span>
        <bean:size id="itemCount" name="STORE_ORDER_DETAIL_FORM" property="orderItemDescList"/>
    </td>
</tr>
<logic:present name="STORE_ORDER_DETAIL_FORM" property="orderItemDescList">
<tr>
    <td colspan="<%=colNum%>">&nbsp;</td>
</tr>
<!-- Cumulative Summary  -->
<tr>
    <td colspan="<%=colNum%>">
        <table ID=1117 align="center">
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
                    <logic:present name="STORE_ORDER_DETAIL_FORM" property="lastDate">
                        <%
                            OrderPropertyData op = theForm.getOrderPropertyDetail();
                            if (op != null) {
                                java.util.Date adddate = op.getAddDate();
                                if (adddate != null) {
                        %>
                        (added by
                        <bean:write name="STORE_ORDER_DETAIL_FORM" property="orderPropertyDetail.addBy"/>
                        on
                        <i18n:formatDate value="<%=adddate%>" pattern="MM/dd/yyyy" locale="<%=Locale.US%>"/>
                        )
                        <% }
                        } %>
                    </logic:present>
                </td>
                <td align="center">
                    <bean:write name="STORE_ORDER_DETAIL_FORM" property="orderedNum"/>
                    &nbsp;</td>
                <td align="center" class="resultscolumna">
                    <bean:write name="STORE_ORDER_DETAIL_FORM" property="acceptedNum"/>
                    &nbsp;</td>
                <td align="center">
                    <bean:write name="STORE_ORDER_DETAIL_FORM" property="shippedNum"/>
                    &nbsp;</td>
                <td align="center" class="resultscolumna">
                    <bean:write name="STORE_ORDER_DETAIL_FORM" property="backorderedNum"/>
                    &nbsp;</td>
                <td align="center">
                    <bean:write name="STORE_ORDER_DETAIL_FORM" property="substitutedNum"/>
                    &nbsp;</td>
                <td align="center" class="resultscolumna">
                    <bean:write name="STORE_ORDER_DETAIL_FORM" property="invoicedNum"/>
                    &nbsp;</td>
                <td align="center">
                    <bean:write name="STORE_ORDER_DETAIL_FORM" property="returnedNum"/>
                    &nbsp;</td>
            </tr>
        </table>
    </td>
</tr>
 <!--  End Cumulative Summary  -->
<tr>
    <td colspan="<%=colNum%>" class="mainbody">&nbsp;</td>
</tr>

<tr>
    <%
       int colInd = 1;
       int qtyColInd = 1;
    %>
    <% String style = ((colInd++) % 2) == 0 ? "resultscolumna" : "resultscolumnb"; %>
    <td class="<%=style%>"><b>L<br>i<br>n<br>e<br>#</b></td>

    <% style = ((colInd++) % 2) == 0 ? "resultscolumna" : "resultscolumnb"; %>
    <td class="<%=style%>"><a ID=1118 href="storeOrderDetail.do?action=sortitems&sortField=erpPoNum"><b>Outbound  PO#</b></a></td>

    <% style = ((colInd++) % 2) == 0 ? "resultscolumna" : "resultscolumnb"; %>
    <td class="<%=style%>"><a ID=1119 href="storeOrderDetail.do?action=sortitems&sortField=asset"><b>Asset Name</b></a></td>

    <% style = ((colInd++) % 2) == 0 ? "resultscolumna" : "resultscolumnb"; %>
    <td class="<%=style%>"><b>Dist Name</b></td>

    <% style = ((colInd++) % 2) == 0 ? "resultscolumna" : "resultscolumnb"; %>
    <td class="<%=style%>"><a ID=1120 href="storeOrderDetail.do?action=sortitems&sortField=name"><b>Service Name</b></a></td>

    <% style = ((colInd++) % 2) == 0 ? "resultscolumna" : "resultscolumnb"; %>
    <td class="<%=style%>"><b>Customer Price</b></td>

    <%if (RefCodeNames.STORE_TYPE_CD.MLA.equals(userStore)) {%>
    <% style = ((colInd++) % 2) == 0 ? "resultscolumna" : "resultscolumnb"; %>
    <td class="<%=style%>"><b>CW Cost</b></td>
    <% } %>

    <% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
    <td class="<%=style%>"><b>Tax Rate<hr/>Tax Exempt</b></td>

    <% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
    <td class="<%=style%>"><b>Tax Amount</b></td>

    <%
        qtyColInd = colInd;
        style = ((colInd++) % 2) == 0 ? "resultscolumna" : "resultscolumnb";
    %>
    <td class="<%=style%>"><b>Qty</b></td>

    <% style = ((colInd++) % 2) == 0 ? "resultscolumna" : "resultscolumnb"; %>
    <td class="<%=style%>"><b>Status</b></td>

    <% style = ((colInd++) % 2) == 0 ? "resultscolumna" : "resultscolumnb"; %>
    <td class="<%=style%>"><b>PO Status</b></td>

    <% style = ((colInd++) % 2) == 0 ? "resultscolumna" : "resultscolumnb"; %>
    <td class="<%=style%>"><b>Item Note</b></td>

    <% style = ((colInd++) % 2) == 0 ? "resultscolumna" : "resultscolumnb"; %>
    <td class="<%=style%>"><b>PO and Ship Date</b></td>

    <% style = ((colInd++) % 2) == 0 ? "resultscolumna" : "resultscolumnb"; %>
    <td class="<%=style%>"><b>Target Ship Date</b></td>

    <% style = ((colInd++) % 2) == 0 ? "resultscolumna" : "resultscolumnb"; %>
    <td class="<%=style%>"><b>Open Line Status</b></td>

    <% if (allowWhenNoErpNoEdi || allowWhenErpEdi) { %>
    <% style = ((colInd++) % 2) == 0 ? "resultscolumna" : "resultscolumnb"; %>
    <td class="<%=style%>"><a ID=1121 href="javascript:SetChecked(1)">[Check&nbsp;All]</a>
        <a ID=1122 href="javascript:SetChecked(0)">[&nbsp;Clear]</a> <br> <b>Select for cancel</b></td>
    <% }%>

</tr>

<logic:iterate id="itemele" indexId="i"
               name="STORE_ORDER_DETAIL_FORM" property="orderItemDescList"
               type="com.cleanwise.service.api.value.OrderItemDescData">

<bean:define id="key" name="itemele" property="orderItem.orderItemId"/>
<bean:define id="qty" name="itemele" property="orderItem.totalQuantityOrdered" type="Integer"/>
<% String linkHref = new String("storeOrderItemDetail.do?action=view&id=" + key);%>

<tr>
    <td colspan="<%=colNum%>">
        <hr>
    </td>
</tr>

<tr>
    <% colInd = 1; %>

    <% style = ((colInd++) % 2) == 0 ? "resultscolumna" : "resultscolumnb"; %>
    <td class="<%=style%>"><bean:write name="itemele" property="orderItem.orderLineNum"/>&nbsp;</td>

    <% style = ((colInd++) % 2) == 0 ? "resultscolumna" : "resultscolumnb"; %>
    <td class="<%=style%>"><bean:write name="itemele" property="orderItem.erpPoNum"/>&nbsp;</td>

    <% style = ((colInd++) % 2) == 0 ? "resultscolumna" : "resultscolumnb"; %>
    <td>
        <% if (((OrderItemDescData) itemele).getAssetInfo() != null
                &&((OrderItemDescData) itemele).getAssetInfo().getShortDesc() != null
                &&((OrderItemDescData) itemele).getAssetInfo().getShortDesc().length() > 0) {
        %>
        <bean:write name="itemele" property="assetInfo.shortDesc"/>
        <%
            }   else {   %> &nbsp; <%}%>
    </td>

    <% style = ((colInd++) % 2) == 0 ? "resultscolumna" : "resultscolumnb"; %>
    <td class="<%=style%>">
        <bean:write name="itemele" property="distName"/>
        &nbsp;
        <%
            if (itemele.getOrderItem() != null
                    && itemele.getOrderItem().getFreightHandlerId() > 0) {
        %>
        <div class="order_shipper">
            <%= theForm.getOrderStatusDetail().getShipViaName(itemele.getOrderItem().getFreightHandlerId()) %>
        </div>
        <% } %>

    </td>

    <% style = ((colInd++) % 2) == 0 ? "resultscolumna" : "resultscolumnb"; %>
    <td class="<%=style%>">
        <% if (itemele.getOrderItem() != null
                && itemele.getOrderItem().getItemShortDesc() != null
                && itemele.getOrderItem().getItemShortDesc().length() > 0) {
        %>
        <a ID=1123 href="<%=linkHref%>"> <bean:write name="itemele" property="orderItem.itemShortDesc"/> </a>

        <%}%> &nbsp;
    </td>

    <% style = ((colInd++) % 2) == 0 ? "resultscolumna" : "resultscolumnb"; %>
    <td class="<%=style%>"><bean:write name="itemele" property="orderItem.custContractPrice"/> &nbsp;</td>

    <%if (RefCodeNames.STORE_TYPE_CD.MLA.equals(userStore)) {%>
    <% style = ((colInd++) % 2) == 0 ? "resultscolumna" : "resultscolumnb"; %>
    <td class="<%=style%>"><bean:write name="itemele" property="orderItem.distItemCost"/>&nbsp;</td>
    <%}%>

    <% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
    <td class="<%=style%>"><bean:write name="itemele" property="orderItem.taxRate"/></td>

    <% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
    <td class="<%=style%>"><bean:write name="itemele" property="orderItem.taxAmount"/></td>

    <% style = ((colInd++) % 2) == 0 ? "resultscolumna" : "resultscolumnb"; %>
    <td class="<%=style%>"><bean:write name="itemele" property="orderItem.totalQuantityOrdered"/>&nbsp;</td>

    <% style = ((colInd++) % 2) == 0 ? "resultscolumna" : "resultscolumnb"; %>
    <td class="<%=style%>">
        <logic:present name="itemele" property="orderItem.orderItemStatusCd">
            <bean:write name="itemele" property="orderItem.orderItemStatusCd"/>
            &nbsp;
        </logic:present>
        <logic:notPresent name="itemele" property="orderItem.orderItemStatusCd">
            Ordered&nbsp;
        </logic:notPresent>
    </td>

    <% style = ((colInd++) % 2) == 0 ? "resultscolumna" : "resultscolumnb"; %>
    <td class="<%=style%>">
        <logic:present name="itemele" property="purchaseOrderData">
            <bean:write name="itemele" property="purchaseOrderData.purchaseOrderStatusCd"/>
        </logic:present>
        &nbsp;
    </td>

    <% style = ((colInd++) % 2) == 0 ? "resultscolumna" : "resultscolumnb"; %>
    <td class="<%=style%>">
        <logic:equal name="itemele" property="hasNote" value="true">
            <input name="button" type="button" class="smallbutton"
                   onClick="popLink('storeOrderNote.do?action=view&type=item&itemid=<%=key%>');" value="View">
        </logic:equal>
        <logic:notEqual name="itemele" property="orderItem.orderItemId" value="0">
            <input name="button2" type="button" class="smallbutton"
                   onClick="popLink('storeOrderNote.do?action=add&type=item&itemid=<%=key%>');" value="Add">
        </logic:notEqual>
    </td>

    <% style = ((colInd++) % 2) == 0 ? "resultscolumna" : "resultscolumnb"; %>
    <td class="<%=style%>">
        <logic:present name="itemele" property="orderItem.erpPoDate">
            <bean:define id="erppodate" name="itemele" property="orderItem.erpPoDate"/>
            <i18n:formatDate value="<%=erppodate%>" pattern="MM/dd/yyyy" locale="<%=Locale.US%>"/>
        </logic:present>
        <logic:notPresent name="itemele" property="orderItem.erpPoDate">
            &nbsp;
        </logic:notPresent>
        <% java.util.Date delivery = itemele.getDeliveryDate();
            if (delivery != null) {
        %>
        <% java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM/dd/yyyy"); %>
        <%=sdf.format(delivery)%>
        <% } %>
    </td>

    <% style = ((colInd++) % 2) == 0 ? "resultscolumna" : "resultscolumnb"; %>
    <td class="<%=style%>">
        <logic:present name="itemele" property="orderItem.targetShipDate">
            <bean:define id="dte" name="itemele" property="orderItem.targetShipDate"/>
            <i18n:formatDate value="<%=dte%>" pattern="MM/dd/yyyy" locale="<%=Locale.US%>"/>
        </logic:present>
        <logic:notPresent name="itemele" property="orderItem.targetShipDate">
            &nbsp;
        </logic:notPresent>
    </td>

    <% style = ((colInd++) % 2) == 0 ? "resultscolumna" : "resultscolumnb"; %>
    <td class="<%=style%>">
        <bean:write name="itemele" property="openLineStatusCd"/>
    </td>

    <%if (allowWhenNoErpNoEdi || allowWhenErpEdi) { %>
    <% style = ((colInd++) % 2) == 0 ? "resultscolumna" : "resultscolumnb"; %>
    <td class="<%=style%>">
        <logic:notEqual name="itemele" property="orderItem.orderItemId" value="0">
            <html:multibox property="selectItems">
                <bean:write name="itemele" property="orderItem.orderItemId"/>
            </html:multibox>
        </logic:notEqual>
    </td>
    <% }%>
</tr>

<% if (allowWhenNoErpNoEdi || allowWhenErpEdi) { %>
<!-- the input fields for each item when no integration necessary -->
<!-- and the input fields for each item when erp and may be edi integration is necessary -->
<tr valign="top">
    <% colInd = 1; %>

    <% style = ((colInd++) % 2) == 0 ? "resultscolumna" : "resultscolumnb"; %>
    <td class="<%=style%>">&nbsp;</td>

    <% style = ((colInd++) % 2) == 0 ? "resultscolumna" : "resultscolumnb"; %>
    <td class="<%=style%>">&nbsp;</td>

    <% style = ((colInd++) % 2) == 0 ? "resultscolumna" : "resultscolumnb"; %>
    <td  class="<%=style%>">
    <% if (((OrderItemDescData) itemele).getAssetInfo() != null) {%>
        <html:text styleClass="textreadonly" size="15" property='<%= "orderItemDesc[" + i + "].newAssetName" %>' readonly="true"/>
        <html:hidden property='<%= "orderItemDesc[" + i + "].assetIdS" %>'/>
        <div align ="center">
        <input name="button2" type="button" class="smallbutton"
               onClick="popAssetLocate('../adminportal/assetLocate', 'orderItemDesc[<%=i%>].assetIdS','<%=siteId%>','orderItemDesc[<%=i%>].newAssetName');"
               value="Locate Asset">
       </div>
    <%} else { %> &nbsp;<%}%>
    </td>

    <% style = ((colInd++) % 2) == 0 ? "resultscolumna" : "resultscolumnb"; %>
    <% if (allowWhenErpEdi) { %>
    <td class="<%=style%>">
        <html:text styleClass="textreadonly" size="12" property='<%= "orderItemDesc[" + i + "].newDistName" %>'  readonly="true"/>
        <html:hidden property='<%= "orderItemDesc[" + i + "].distIdS" %>'/>
        <div align ="center">
        <input name="button2" type="button" class="smallbutton"
               onClick="popLocate2('../adminportal/distlocate', 'orderItemDesc[<%=i%>].distIdS', 'orderItemDesc[<%=i%>].newDistName');"
               value="Locate Dist">
        </div>
     </td>
    <%} else {%>
    <td class="<%=style%>">&nbsp;</td>
    <%}%>
    
    <% style = ((colInd++) % 2) == 0 ? "resultscolumna" : "resultscolumnb"; %>
    <% if (allowWhenErpEdi) { %>
    <td class="<%=style%>">
        <html:text styleClass="textreadonly" size="17" property='<%= "orderItemDesc[" + i + "].newServiceName" %>'  readonly="true"/>
        <html:hidden property='<%= "orderItemDesc[" + i + "].itemIdS" %>'/>
        <%
            String assetId="";
           if (((OrderItemDescData) theForm.getOrderItemDesc(i.intValue())).getAssetInfo() != null) {
                    assetId = String.valueOf(((OrderItemDescData) theForm.getOrderItemDesc(i.intValue())).getAssetInfo().getAssetId());
                }
        %>
       <div align ="center">
       <input type="button"
                   onclick="popServiceLocate('../adminportal/contractServiceLocate', 'orderItemDesc[<%=i%>].itemIdS','orderItemDesc[<%=i%>].assetIdS','<%=assetId%>','<%=userId%>', '<%=siteId%>','orderItemDesc[<%=i%>].newServiceName');"
                   value="Locate Service" class="smallbutton">
       </div>
    </td>
    <%} else {%>
    <td class="<%=style%>">&nbsp;</td>
    <%}%>

    <% style = ((colInd++) % 2) == 0 ? "resultscolumna" : "resultscolumnb"; %>
    <td class="<%=style%>">
        <%if (!RefCodeNames.STORE_TYPE_CD.MLA.equals(userStore)) {%>
        <div align ="center">
        <html:text styleClass="text" size="5" maxlength="16" property='<%= "orderItemDesc[" + i + "].itemPriceS" %>'/>
        </div>
                <%} else {%>&nbsp;<%}%>
    </td>

    <%if (RefCodeNames.STORE_TYPE_CD.MLA.equals(userStore)) {%>
    <% style = ((colInd++) % 2) == 0 ? "resultscolumna" : "resultscolumnb"; %>
    <td class="<%=style%>">
        <div align ="center">
        <html:text styleClass="text" size="5" maxlength="16" property='<%= "orderItemDesc[" + i + "].cwCostS" %>'/>
        <bean:define id="itemSkuNum" name="itemele" property="orderItem.itemSkuNum"/>
        <%
            String distErpNum = "-";
            if (itemele.getOrderItem() != null && itemele.getOrderItem().getDistErpNum() != null) {
                distErpNum = itemele.getOrderItem().getDistErpNum();
            }
        %>
        </div>
    </td>
    <%}%>

    <% style = ((colInd++) % 2) == 0 ? "resultscolumna" : "resultscolumnb"; %>
    <td class="<%=style%>">
    <html:checkbox value="on" property='<%= "orderItemDesc[" + i + "].taxExempt" %>'/>
    </td>

    <% style = ((colInd++) % 2) == 0 ? "resultscolumna" : "resultscolumnb"; %>
    <td class="<%=style%>">&nbsp;</td>

    <% style = ((colInd++) % 2) == 0 ? "resultscolumna" : "resultscolumnb"; %>
    <td class="<%=style%>"> &nbsp; </td>

    <% style = ((colInd++) % 2) == 0 ? "resultscolumna" : "resultscolumnb"; %>
    <td class="<%=style%>">
        <html:select property='<%= "orderItemDesc[" + i + "].itemStatus" %>' size="1" styleClass="text"
                     style="width:150px;">
            <html:optionsCollection name="STORE_ORDER_DETAIL_FORM" property="orderItemStatusList"/>
        </html:select>
        &nbsp;</td>

    <% style = ((colInd++) % 2) == 0 ? "resultscolumna" : "resultscolumnb"; %>
    <td class="<%=style%>">
        <logic:present name="itemele" property="purchaseOrderData">
            <html:select property='<%= "orderItemDesc[" + i + "].poItemStatus" %>' size="1" styleClass="text"
                         style="width:150px;">
                <html:optionsCollection name="STORE_ORDER_DETAIL_FORM" property="poItemStatusList"/>
            </html:select>
        </logic:present>
        &nbsp;</td>

    <% style = ((colInd++) % 2) == 0 ? "resultscolumna" : "resultscolumnb"; %>
    <td class="<%=style%>">&nbsp;</td>

    <% style = ((colInd++) % 2) == 0 ? "resultscolumna" : "resultscolumnb"; %>
    <td class="<%=style%>">&nbsp;</td>

    <% style = ((colInd++) % 2) == 0 ? "resultscolumna" : "resultscolumnb"; %>
    <td class="<%=style%>">&nbsp;</td>

    <% style = ((colInd++) % 2) == 0 ? "resultscolumna" : "resultscolumnb"; %>
    <td class="<%=style%>">&nbsp;</td>

    <% style = ((colInd++) % 2) == 0 ? "resultscolumna" : "resultscolumnb"; %>
    <td class="<%=style%>">&nbsp;</td>

</tr>
<% } %>
</logic:iterate>
</logic:present>
</table>
