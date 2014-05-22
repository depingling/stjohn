<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<%@ page import="java.util.Locale" %>
<%@ page import="java.util.Date" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>


<app:checkLogon/>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="Location" value="contract" type="java.lang.String" toScope="session"/>

<table width="900" border="0" class="results">
<tr>
<td colspan="17"><span class="mediumheader"><b>Order Item Status:</b></span>
<bean:size id="itemCount" name="PO_OP_DETAIL_FORM" property="purchaseOrderItemDescList" />
<!-- <bean:write name="itemCount" />  -->
</td>
</tr>

<logic:present name="PO_OP_DETAIL_FORM" property="purchaseOrderItemDescList">


<tr>
<%int colInd=1;
String style=null;%>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><b><a href="purchaseOrderOpDetail.do?action=sortitems&sortField=erpPoLineNum">Line#</b></td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><b>Dist Order#</b></td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><b><a href="purchaseOrderOpDetail.do?action=sortitems&sortField=cwSKU">CW SKU#</b></td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><b><a href="purchaseOrderOpDetail.do?action=sortitems&sortField=distSKU">Dist SKU#</b></td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><b><a href="purchaseOrderOpDetail.do?action=sortitems&sortField=name">Product Name</b></td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><b>UOM</b></td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><b>Pack</b></td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><b>Item Size</b></td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><b>Customer Price</b></td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><b>CW Cost</b></td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><b>Qty</b></td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><b>PO Cost</b></td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><b>PO Qty</b></td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><b>Status</b></td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><b>Item Note</b></td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><b>Add Item Note</b></td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><b>Qty For Action</b></td>
<logic:equal parameter="caller.function" value="returns">
  <% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
  <td class="<%=style%>"><b>Qty To Return</b></td>
</logic:equal>
<logic:equal parameter="caller.function" value="returnsView">
  <% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
  <td class="<%=style%>"><b>Qty To Return</b></td>
</logic:equal>
<logic:equal parameter="caller.function" value="po">
  <% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
  <td class="<%=style%>"><b>Status</b></td>
  <% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
  <td class="<%=style%>"><b>Target Ship Date</b></td>
</logic:equal>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><b>Open Line Status</b></td>
<%int totalColInd = colInd;%>
</tr>

<logic:iterate id="itemele" indexId="i" name="PO_OP_DETAIL_FORM" property="purchaseOrderItemDescList" scope="session" type="com.cleanwise.service.api.value.OrderItemDescData">
 <bean:define id="key"  name="itemele" property="orderItem.orderItemId"/>
 <bean:define id="qty" name="itemele" property="orderItem.totalQuantityOrdered" type="Integer"/>
 
 <% String linkHref = new String("orderOpItemDetail.do?action=view&id=" + key  + "&fromPage=purchaseOrder");%>

<tr><td colspan="<%=colInd%>" class="mainbody"><img src="../<%=ip%>images/cw_descriptionseperator.gif" height="1" width="900"></td></tr>
<% colInd=1; %>
<tr>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><bean:write name="itemele" property="orderItem.erpPoLineNum"/>&nbsp;</td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><bean:write name="itemele" property="orderItem.distOrderNum"/>&nbsp;</td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><bean:write name="itemele" property="orderItem.itemSkuNum"/>&nbsp;</td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><bean:write name="itemele" property="orderItem.distItemSkuNum"/>&nbsp;</td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><a href="<%=linkHref%>"><bean:write name="itemele" property="orderItem.itemShortDesc"/></a>&nbsp;</td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><bean:write name="itemele" property="orderItem.itemUom"/>&nbsp;</td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><bean:write name="itemele" property="orderItem.itemPack"/>&nbsp;</td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><bean:write name="itemele" property="orderItem.itemSize"/>&nbsp;</td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><bean:write name="itemele" property="orderItem.custContractPrice"/>&nbsp;</td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><bean:write name="itemele" property="orderItem.distItemCost"/>&nbsp;</td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><bean:write name="itemele" property="orderItem.totalQuantityOrdered"/>&nbsp;</td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<logic:present name="itemele" property="orderItem.distUomConvCost">
        <td class="<%=style%>"><bean:write name="itemele" property="orderItem.distUomConvCost"/>&nbsp;</td>
</logic:present>
<logic:notPresent name="itemele" property="orderItem.distUomConvCost">
        <td class="<%=style%>"><bean:write name="itemele" property="orderItem.distItemCost"/>&nbsp;</td>
</logic:notPresent>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><bean:write name="itemele" property="orderItem.distItemQuantity"/>&nbsp;</td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>">
<logic:present name="itemele" property="orderItem.orderItemStatusCd" >
        <bean:write name="itemele" property="orderItem.orderItemStatusCd"/>&nbsp;
</logic:present>
<logic:notPresent name="itemele" property="orderItem.orderItemStatusCd" >
        Ordered&nbsp;
</logic:notPresent>
</td>

<logic:equal name="itemele" property="hasNote" value="true">
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><input type="button" class="smallbutton" onclick="popLink('orderOpNote.do?action=view&type=item&itemid=<%=key%>&src=po');" value="View"></td>
</logic:equal>
<logic:equal name="itemele" property="hasNote" value="false">
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
  <td class="<%=style%>">&nbsp;</td>
</logic:equal>

<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><input type="button" class="smallbutton" onclick="popLink('orderOpNote.do?action=add&type=item&itemid=<%=key%>&src=po');" value="Add"></td>

<logic:equal parameter="caller.function" value="returns">
  <% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
  <td class="<%=style%>">
     <html:text property='<%= "purchaseOrderItemDesc[" + i + "].qtyReturnedString" %>' size='4'/>
  </td>
</logic:equal>
<logic:equal parameter="caller.function" value="returnsView">
  <% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
  <td class="<%=style%>">
     <bean:write name="itemele" property="qtyReturnedString"/>
  </td>
</logic:equal>
<logic:equal parameter="caller.function" value="po">
  <% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
  <td class="<%=style%>">
  	<html:text property='<%= "purchaseOrderItemDesc[" + i + "].newOrderItemActionQtyS" %>' size="3"/>
  </td>
  <% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
  <td class="<%=style%>">
    <html:select property='<%= "purchaseOrderItemDesc[" + i + "].shipStatus" %>'>
                <html:option value=""><app:storeMessage  key="admin.select"/></html:option>
                <html:options collection="OrderProperty.ShipStatus.vector" property="value" />
        </html:select>
  </td>
  <% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
  <td class="<%=style%>">
     <html:text property='<%= "purchaseOrderItemDesc[" + i + "].targetShipDateString" %>'/>
  </td>
  <% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
  <td class="<%=style%>">
  <html:select property='<%= "purchaseOrderItemDesc[" + i + "].openLineStatusCd" %>'>
        <html:option value=""><app:storeMessage  key="admin.select"/></html:option>
        <html:options  collection="Open.line.status.vector" property="value" />
  </html:select>
</logic:equal>
<logic:notEqual parameter="caller.function" value="po">
  <bean:write name="itemele" property="openLineStatusCd"/>
</logic:notEqual>
</td>

<%while(colInd<=totalColInd){%>
<%--Finish empty formatting--%>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>">&nbsp;</td>
<%}%>
</tr>

<%--Returns Additional Item Information--%>
<logic:equal parameter="caller.function" value="returns">
        <tr>
                <%int colIndTet = 1;%>
                <% style = ((colIndTet++)%2)==0?"resultscolumna":"resultscolumnb"; %>
                <td class="<%=style%>">&nbsp;</td>
                <%colIndTet = colIndTet + 2;%>
                <td colspan=2>Received Item Info:</td>
                <% style = ((colIndTet++)%2)==0?"resultscolumna":"resultscolumnb"; %>
                <td class="<%=style%>"><html:text property='<%= "purchaseOrderItemDesc[" + i + "].returnRequestDetailData.recievedDistSku" %>'/></td>
                <% style = ((colIndTet++)%2)==0?"resultscolumna":"resultscolumnb"; %>
                <td class="<%=style%>"></td>
                <% style = ((colIndTet++)%2)==0?"resultscolumna":"resultscolumnb"; %>
                <td class="<%=style%>"><html:text property='<%= "purchaseOrderItemDesc[" + i + "].returnRequestDetailData.recievedDistUom" %>'/></td>
                <% style = ((colIndTet++)%2)==0?"resultscolumna":"resultscolumnb"; %>
                <td class="<%=style%>"><html:text property='<%= "purchaseOrderItemDesc[" + i + "].returnRequestDetailData.recievedDistPack" %>'/></td>
                <% style = ((colIndTet++)%2)==0?"resultscolumna":"resultscolumnb"; %>
                <td class="<%=style%>">&nbsp;</td>
                <% style = ((colIndTet++)%2)==0?"resultscolumna":"resultscolumnb"; %>
                <td class="<%=style%>">&nbsp;</td>
                <% style = ((colIndTet++)%2)==0?"resultscolumna":"resultscolumnb"; %>
                <td class="<%=style%>">&nbsp;</td>
                <% style = ((colIndTet++)%2)==0?"resultscolumna":"resultscolumnb"; %>
                <td class="<%=style%>">&nbsp;</td>
                <% style = ((colIndTet++)%2)==0?"resultscolumna":"resultscolumnb"; %>
                <td class="<%=style%>">&nbsp;</td>
                <% style = ((colIndTet++)%2)==0?"resultscolumna":"resultscolumnb"; %>
                <td class="<%=style%>">&nbsp;</td>
                <% style = ((colIndTet++)%2)==0?"resultscolumna":"resultscolumnb"; %>
                <td class="<%=style%>">&nbsp;</td>
                <% style = ((colIndTet++)%2)==0?"resultscolumna":"resultscolumnb"; %>
                <td class="<%=style%>">&nbsp;</td>
                <% style = ((colIndTet++)%2)==0?"resultscolumna":"resultscolumnb"; %>
                <td class="<%=style%>">&nbsp;</td>
        </tr>
</logic:equal>
<logic:equal parameter="caller.function" value="returnsView">
        <tr>
                <%int colIndTet = 1;%>
                <% style = ((colIndTet++)%2)==0?"resultscolumna":"resultscolumnb"; %>
                <td class="<%=style%>">&nbsp;</td>
                <%colIndTet = colIndTet + 2;%>
                <td colspan=2>Received Item Info:</td>
                <% style = ((colIndTet++)%2)==0?"resultscolumna":"resultscolumnb"; %>
                <td class="<%=style%>"><bean:write name="itemele" property="returnRequestDetailData.recievedDistSku"/></td>
                <% style = ((colIndTet++)%2)==0?"resultscolumna":"resultscolumnb"; %>
                <td class="<%=style%>"></td>
                <% style = ((colIndTet++)%2)==0?"resultscolumna":"resultscolumnb"; %>
                <td class="<%=style%>"><bean:write name="itemele" property="returnRequestDetailData.recievedDistUom"/></td>
                <% style = ((colIndTet++)%2)==0?"resultscolumna":"resultscolumnb"; %>
                <td class="<%=style%>"><bean:write name="itemele" property="returnRequestDetailData.recievedDistPack"/></td>
                <% style = ((colIndTet++)%2)==0?"resultscolumna":"resultscolumnb"; %>
                <td class="<%=style%>">&nbsp;</td>
                <% style = ((colIndTet++)%2)==0?"resultscolumna":"resultscolumnb"; %>
                <td class="<%=style%>">&nbsp;</td>
                <% style = ((colIndTet++)%2)==0?"resultscolumna":"resultscolumnb"; %>
                <td class="<%=style%>">&nbsp;</td>
                <% style = ((colIndTet++)%2)==0?"resultscolumna":"resultscolumnb"; %>
                <td class="<%=style%>">&nbsp;</td>
                <% style = ((colIndTet++)%2)==0?"resultscolumna":"resultscolumnb"; %>
                <td class="<%=style%>">&nbsp;</td>
                <% style = ((colIndTet++)%2)==0?"resultscolumna":"resultscolumnb"; %>
                <td class="<%=style%>">&nbsp;</td>
                <% style = ((colIndTet++)%2)==0?"resultscolumna":"resultscolumnb"; %>
                <td class="<%=style%>">&nbsp;</td>
                <% style = ((colIndTet++)%2)==0?"resultscolumna":"resultscolumnb"; %>
                <td class="<%=style%>">&nbsp;</td>
                <% style = ((colIndTet++)%2)==0?"resultscolumna":"resultscolumnb"; %>
                <td class="<%=style%>">&nbsp;</td>
        </tr>
</logic:equal>
<%--End Returns Additional Item Information--%>

<%int orderItemActionIdx=0;%>

<%--the order item actions--%>
<logic:present name="itemele" property="orderItemActionDescList">

        <bean:size id="detailCount" name="itemele" property="orderItemActionDescList" />
        <logic:iterate id="detailele" indexId="j" name="itemele" property="orderItemActionDescList" type="com.cleanwise.service.api.value.OrderItemActionDescData">
		<bean:define id="detailkey"  name="detailele" property="orderItemAction.orderItemActionId" type="java.lang.Integer"/>

		<tr>
			<%int colInd2 = 1;%>
			<%orderItemActionIdx++;%>
			<% style = ((colInd2++)%2)==0?"resultscolumna":"resultscolumnb"; %>
			<td class="<%=style%>"></td>
			<% style = ((colInd2++)%2)==0?"resultscolumna":"resultscolumnb"; %>
			<td class="<%=style%>">&nbsp;</td>
			<% style = ((colInd2++)%2)==0?"resultscolumna":"resultscolumnb"; %>
			<td class="<%=style%>"><bean:write name="detailele" property="orderItemAction.affectedSku"/>&nbsp;</td>
			<% style = ((colInd2++)%2)==0?"resultscolumna":"resultscolumnb"; %>
			<td class="<%=style%>"><bean:write name="detailele" property="distItemSkuNum"/>&nbsp;</td>
			<% style = ((colInd2++)%2)==0?"resultscolumna":"resultscolumnb"; %>
			<td class="<%=style%>">&nbsp;</td>
			<% style = ((colInd2++)%2)==0?"resultscolumna":"resultscolumnb"; %>
			<td class="<%=style%>"><bean:write name="detailele" property="itemShortDesc"/>&nbsp;</td>
			<% style = ((colInd2++)%2)==0?"resultscolumna":"resultscolumnb"; %>
			<td class="<%=style%>"><bean:write name="detailele" property="itemUom"/>&nbsp;</td>
			<% style = ((colInd2++)%2)==0?"resultscolumna":"resultscolumnb"; %>
			<td class="<%=style%>"><bean:write name="detailele" property="itemPack"/>&nbsp;</td>
			<% style = ((colInd2++)%2)==0?"resultscolumna":"resultscolumnb"; %>
			<td class="<%=style%>">&nbsp;</td>
			<% style = ((colInd2++)%2)==0?"resultscolumna":"resultscolumnb"; %>
			<td class="<%=style%>">&nbsp;</td>
			<% style = ((colInd2++)%2)==0?"resultscolumna":"resultscolumnb"; %>
			<td class="<%=style%>">&nbsp;</td>
			<% style = ((colInd2++)%2)==0?"resultscolumna":"resultscolumnb"; %>
			<td class="<%=style%>">&nbsp;</td>
			<% style = ((colInd2++)%2)==0?"resultscolumna":"resultscolumnb"; %>
			<td class="<%=style%>">&nbsp;</td>
			<% style = ((colInd2++)%2)==0?"resultscolumna":"resultscolumnb"; %>
			<td class="<%=style%>">&nbsp;</td>
			<% style = ((colInd2++)%2)==0?"resultscolumna":"resultscolumnb"; %>
			<td class="<%=style%>">
				<%--only allow users to delete the actions that they added--%>
				<logic:equal name="detailele" property="orderItemAction.addBy" value="<%=(String) session.getAttribute(Constants.USER_NAME)%>">
			            <% String prop = "orderItemActionSelection[" + orderItemActionIdx + "]";%>
				    <html:checkbox name="PO_OP_DETAIL_FORM" property="<%=prop%>" value="<%=detailkey.toString()%>"/>
				</logic:equal>
			</td>
			<% style = ((colInd2++)%2)==0?"resultscolumna":"resultscolumnb"; %>
			<td class="<%=style%>"><bean:write name="detailele" property="orderItemAction.quantity"/>&nbsp;</td>
			<% style = ((colInd2++)%2)==0?"resultscolumna":"resultscolumnb"; %>
			<td class="<%=style%>"><bean:write name="detailele" property="orderItemAction.actionCd"/>&nbsp;</td>
			<logic:present name="detailele" property="orderItemAction.actionDate">
				<% style = ((colInd2++)%2)==0?"resultscolumna":"resultscolumnb"; %>
				<td class="<%=style%>">
					<bean:define id="date" name="detailele" property="orderItemAction.actionDate"/>
					<i18n:formatDate value="<%=date%>" pattern="MM/dd/yyyy" locale="<%=Locale.US%>"/>
				</td>
			</logic:present>
			<logic:notPresent name="detailele" property="orderItemAction.actionDate">
				<% style = ((colInd2++)%2)==0?"resultscolumna":"resultscolumnb"; %>
				<td class="<%=style%>">&nbsp;</td>
			</logic:notPresent>
			<% style = ((colInd2++)%2)==0?"resultscolumna":"resultscolumnb"; %>
			<td class="<%=style%>"><bean:write name="detailele" property="orderItemAction.addBy"/>&nbsp;</td>
			<%--<% style = ((colInd2++)%2)==0?"resultscolumna":"resultscolumnb"; %>
			<logic:present name="detailele" property="orderItemAction.addDate">
				<% style = ((colInd2++)%2)==0?"resultscolumna":"resultscolumnb"; %>
				<td class="<%=style%>">
					<bean:define id="date" name="detailele" property="orderItemAction.addDate"/>
					<i18n:formatDate value="<%=date%>" pattern="MM/dd/yyyy" locale="<%=Locale.US%>"/>
				</td>
			</logic:present>
			<logic:notPresent name="detailele" property="orderItemAction.addDate">
				<% style = ((colInd2++)%2)==0?"resultscolumna":"resultscolumnb"; %>
				<td class="<%=style%>">&nbsp;</td>
			</logic:notPresent>--%>
			<%--fill in the rest of the row with the proper alternating colors--%>
			<%while(colInd>=colInd2){%>
				<% style = ((colInd2++)%2)==0?"resultscolumna":"resultscolumnb"; %>
				<td class="<%=style%>">&nbsp;</td>
			<%}%>
		</tr>
	</logic:iterate>

</logic:present> <%--end display order item actions --%>



</logic:iterate>


</logic:present>
