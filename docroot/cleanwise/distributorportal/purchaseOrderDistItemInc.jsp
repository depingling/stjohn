<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>


<%@ page import="java.util.Locale" %>
<%@ page import="java.util.Date" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>

<script language="JavaScript1.2">
<!--
function submitAddNote(val) {
 dml=document.forms;
 mmm:
 for(i=0; i<dml.length; i++) {
  ellen = dml[i].elements.length;
  actionFl = false;
  fieldFl = false;
  //alert('next_form='+ellen);
  for(j=0; j<ellen; j++) {
    if (dml[i].elements[j].name=='action' && 
        dml[i].elements[j].value=='BBBBBBB') {
      //alert(dml[i].elements[j].name+'='+dml[i].elements[j].value);
          dml[i].elements[j].value="note";
          actionFl = true;
        }
    if (dml[i].elements[j].name=='index') {  
      //alert(dml[i].elements[j].name+'='+dml[i].elements[j].value);
      dml[i].elements[j].value=val;
      fieldFl = true;
    }
    if(actionFl && fieldFl) {
      //alert('Submit');
      dml[i].submit();
      break mmm;
    }
  }
 }
}
-->
</script>

<app:checkLogon/>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="theForm" name="PO_OP_DETAIL_FORM" type="com.cleanwise.view.forms.PurchaseOrderOpDetailForm"/>
<bean:define id="Location" value="contract" type="java.lang.String" toScope="session"/>

<table width="100%" border="0" class="results">
<tr>
<td colspan="9"><span class="mediumheader"><b>Items:</b></span>
<bean:size id="itemCount" name="PO_OP_DETAIL_FORM" property="purchaseOrderItemDescList" />
<!-- <bean:write name="itemCount" />  -->
</td>
</tr>

<logic:present name="PO_OP_DETAIL_FORM" property="purchaseOrderItemDescList">


<tr>
<%int colInd=1;
String style=null;%>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><b><a href="purchaseOrderDistDetail.do?action=sortitems&sortField=erpPoLineNum">Line#</b></td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><b><a href="purchaseOrderDistDetail.do?action=sortitems&sortField=cwSKU">CW SKU#</b></td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><b><a href="purchaseOrderDistDetail.do?action=sortitems&sortField=distSKU">Dist SKU#</b></td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><b><a href="purchaseOrderDistDetail.do?action=sortitems&sortField=name">Product Name</b></td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><b>UOM</b></td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><b>Pack</b></td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><b>Item Size</b></td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><b>Cost</b></td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><b>Qty</b></td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><b>Qty For Action</b></td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><b>Status</b></td>
<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
<td class="<%=style%>"><b>Target Ship Date</b></td>
</tr>

<logic:iterate id="itemele" indexId="i" name="PO_OP_DETAIL_FORM" property="purchaseOrderItemDescList" scope="session" type="com.cleanwise.service.api.value.OrderItemDescData">
 <bean:define id="key"  name="itemele" property="orderItem.orderItemId"/>
 <bean:define id="qty" name="itemele" property="orderItem.totalQuantityOrdered" type="Integer"/>
 <% String linkHref = new String("orderOpItemDetail.do?action=view&id=" + key  + "&fromPage=purchaseOrder");
   OrderPropertyData distNote = itemele.getDistPoNote(); 
%>

<tr><td colspan="<%=colInd%>" class="mainbody"><img src="../<%=ip%>images/cw_descriptionseperator.gif" height="1" width="750"></td></tr>
 <% colInd=1; %>
 <%int orderItemActionIdx = 0;%>
<tr>
	<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
	<td class="<%=style%>"><bean:write name="itemele" property="orderItem.erpPoLineNum"/>&nbsp;</td>
	<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
	<td class="<%=style%>"><bean:write name="itemele" property="orderItem.itemSkuNum"/>&nbsp;</td>
	<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
	<td class="<%=style%>"><bean:write name="itemele" property="orderItem.distItemSkuNum"/>&nbsp;</td>
	<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
	<td class="<%=style%>"><bean:write name="itemele" property="orderItem.itemShortDesc"/>&nbsp;</td>
	<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
	<td class="<%=style%>"><bean:write name="itemele" property="orderItem.itemUom"/>&nbsp;</td>
	<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
	<td class="<%=style%>"><bean:write name="itemele" property="orderItem.itemPack"/>&nbsp;</td>
	<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
	<td class="<%=style%>"><bean:write name="itemele" property="orderItem.itemSize"/>&nbsp;</td>
	<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
	<td class="<%=style%>"><bean:write name="itemele" property="orderItem.distItemCost"/>&nbsp;</td>
	<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
	<td class="<%=style%>"><bean:write name="itemele" property="orderItem.totalQuantityOrdered"/>&nbsp;</td>
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
		<html:text property='<%= "purchaseOrderItemDesc[" + i + "].targetShipDateString" %>' size="10"/>
     	</td>
	<% style = ((colInd++)%2)==0?"resultscolumna":"resultscolumnb"; %>
	<td class="<%=style%>">
   <% String addNote="submitAddNote("+i+");";%>
   <logic:notPresent name="itemele" property="distPoNote">
   <a href='#pgsort' onclick="<%=addNote%>">Add Note</a></th>
   </logic:notPresent>&nbsp;
	</td>
  	
</tr>

<%--distributor po note--%>
<% 
   if(distNote!=null) {
%>
		<tr>
			<% int colInd2=1;
         style = "resultscolumnb"; 
      %>
		  <td colspan='1' class="<%=style%>">Note:</td>
			<td colspan='11' class="<%=style%>">
			<% colInd2+=11; %>
  		<html:text styleClass='text' property='<%= "purchaseOrderItemDesc[" + i + "].distPoNote.value" %>' size="130"/>
  		</td>
			<% colInd2++; %>
			<td class="<%=style%>"><bean:write name="itemele" property="distPoNote.modBy"/>&nbsp;</td>
			<%--fill in the rest of the row with the proper alternating colors--%>
			<%while((colInd-1)>colInd2++){%>
				<% style = "resultscolumna"; %>
				<td class="<%=style%>">&nbsp;</td>
			<%}%>
		</tr>

<% } %>
<%--the order item actions--%>
<logic:present name="itemele" property="orderItemActionDescList">
	
        <bean:size id="detailCount" name="itemele" property="orderItemActionDescList" />
        <logic:iterate id="detailele" indexId="j" name="itemele" property="orderItemActionDescList" type="com.cleanwise.service.api.value.OrderItemActionDescData">
		<logic:greaterThan value="0" name="detailele" property="orderItemAction.quantity">
		<bean:define id="detailkey"  name="detailele" property="orderItemAction.orderItemActionId" type="java.lang.Integer"/>

		<tr>
			<%int colInd2 = 1;%>
			<%orderItemActionIdx++;%>
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
			<%while(colInd>colInd2){%>
				<% style = ((colInd2++)%2)==0?"resultscolumna":"resultscolumnb"; %>
				<td class="<%=style%>">&nbsp;</td>
			<%}%>
		</tr>
		</logic:greaterThan>
	</logic:iterate>

</logic:present> <%--end display order item actions --%>


</logic:iterate>

  <html:hidden  property="action" value="BBBBBBB"/>
  <html:hidden  property="index" value="BBBBBBB"/>

</logic:present>
