<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="java.util.Locale" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<script language="JavaScript1.2">
<!--
function popLocate(pLoc, name, pDesc) {
  var loc = pLoc + ".do?feedField=" + name + "&amp;feedDesc=" + pDesc;
  locatewin = window.open(loc,"Locate", "menubar=no,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
  locatewin.focus();
  return false;
}

function popLink(pLoc) {
  var loc = pLoc;
  locatewin = window.open(loc,"Note", "menubar=no,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
  locatewin.focus();
  return false;
}

//-->
</script>
<%
String currentURI = "purchaseOrderLineTrackerOp.do";
String fullCurrentURI = "console/purchaseOrderLineTrackerOp.do";
%>

<div class="text">

<font color=red>
<html:errors/>
</font>


<table cellpadding="2" cellspacing="0" border="0" width="769" class="mainbody">
<html:form name="PO_OP_SEARCH_FORM" action="<%= fullCurrentURI %>" focus="distributorId" scope="session" type="com.cleanwise.view.forms.PurchseOrderOpSearchForm">

  <tr> <td><b>Search:</b></td>
       <td colspan="4">&nbsp;
       </td>
  </tr>


  <tr> <td>&nbsp;</td>
       <td><b>Distributor ID:</b></td>
           <td colspan="3">
                        <html:text name="PO_OP_SEARCH_FORM" property="distributorId" />
                        <html:button property="action"
                                onclick="popLocate('../adminportal/distlocate', 'distributorId', '');"
                                value="Locate Distributor"/>
       </td>
  </tr>

  <tr> <td>&nbsp;</td>
   <td><b>Account(s)</b></td>
       <td colspan='3'>
       <% String onKeyPress="return submitenter(this,event,'Submit');"; %>
       <% String onClick = "popLocate('../adminportal/accountLocateMulti', 'accountIdList', '');";%>
       <html:text size='50' onkeypress="<%=onKeyPress%>" name="PO_OP_SEARCH_FORM"   property="accountIdList" />
       <html:button property="locateAccount"
                    onclick="<%=onClick%>"
                    value="Locate Account(s)"/>
        </td>
  
  </tr>

  <tr> <td>&nbsp;</td>
       <td><b>Purchase Order Date:</b><br>(mm/dd/yyyy)</td>
           <td colspan="3">
                        Begin Date Range
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        End Date Range<br>
                        <html:text name="PO_OP_SEARCH_FORM" property="poDateRangeBegin" />

                        <html:text name="PO_OP_SEARCH_FORM" property="poDateRangeEnd" />
           </td>
  </tr>


  <tr> <td>&nbsp;</td>
       <td><b>ERP Order #:</b></td>
           <td>
                        <html:text name="PO_OP_SEARCH_FORM" property="erpOrderNum" />
       </td>
       <td>&nbsp;</td>
       <td>&nbsp;</td>
  </tr>

  <tr> <td>&nbsp;</td>
       <td><b>Outbound PO #:</b></td>
           <td>
                        <html:text name="PO_OP_SEARCH_FORM" property="erpPONum" />
       </td>
       <td>&nbsp;</td>
       <td>&nbsp;</td>
  </tr>

  <tr>
      <td colspan="5" align="center">
        <html:submit property="action">
            <app:storeMessage  key="global.action.label.search"/>
        </html:submit>
        <html:submit property="action">
            <app:storeMessage  key="global.action.label.print"/>
        </html:submit>
     </td>
  </tr>
  <tr><td colspan="5">&nbsp;</td>
  </tr>


</html:form>
</table>

<logic:present name="PO_OP_SEARCH_FORM" property="trackingResultList">
        <bean:size id="listCount" name="PO_OP_SEARCH_FORM" property="trackingResultList" />

        Search results count:&nbsp;<bean:write name="listCount"/>
        <logic:greaterThan name="listCount" value="0">

<table cellpadding="2" cellspacing="0" border="0" width="769" class="results">
<tr align=left>
        <td><a href="<%= currentURI %>?action=sort&sortField=DistributorName"><b>Dist Name</b></a></td>
        <td><a href="<%= currentURI %>?action=sort&sortField=AccountName"><b>Acct Name</b></a></td>
        <td><a href="<%= currentURI %>?action=sort&sortField=ErpPoNum"><b>Outbound PO Num&nbsp;#</b></a></td>
        <td><a href="<%= currentURI %>?action=sort&sortField=ErpOrderNum"><b>ERP Order&nbsp;#</b></a></td>
        <td><a href="<%= currentURI %>?action=sort&sortField=ErpPoDate"><b>PO Date</b></a></td>
        <td><b>PO Status</b></td>
        <td><b>Target Shipping Date</b></td>
        <td><a href="<%= currentURI %>?action=sort&sortField=OpenLineStatusCd"><b>Open Line Status</b></a></td>
        <td><a href="<%= currentURI %>?action=sort&sortField=ErpSkuNum"><b>Sku</b></a></td>
        <td><b>Unit Cost</b></td>
        <td><b>Ordered Qty</b></td>
        <td><b>Open Qty</b></td>
        <td><b>Open Amount</b></td>
        <td><b>Item Description</b></td>
        <td><b>View Item Note</b></td>
        <td><b>Add Item Note</b></td>
        <td><b>View Order Note</b></td>
        <td><b>Add Order Note</b></td>
</tr>

 <bean:define id="pagesize" name="listCount"/>



<html:form name="PO_OP_SEARCH_FORM" action="<%= fullCurrentURI %>" scope="session" type="com.cleanwise.view.forms.PurchseOrderOpSearchForm">

<logic:iterate id="po" indexId="i" name="PO_OP_SEARCH_FORM" property="trackingResultList" type="com.cleanwise.service.api.value.ERPPurchaseOrderLineDescView">
 <%String linkHref = null;%>
 <bean:define id="orderId"  value="0" type="java.lang.String"/>
 <bean:define id="orderItemId"  value="0" type="java.lang.String"/>
 <logic:present name="po" property="purchaseOrderData">
        <bean:define id="poId"  name="po" property="purchaseOrderData.purchaseOrderId"/>
        <%
        orderId = Integer.toString(po.getPurchaseOrderData().getOrderId());
        pageContext.setAttribute("orderId",orderId);
        linkHref = "purchaseOrderOpDetail.do?action=view&id=" + poId;
        %>
 </logic:present>
 <logic:present name="po" property="orderItemData">
        <%
        orderItemId = Integer.toString(po.getOrderItemData().getOrderItemId());
        pageContext.setAttribute("orderItemId",orderItemId);
        %>
 </logic:present>


 <tr>
  <td><bean:write name="po" property="distributorName"/></td>
  <td><bean:write name="po" property="accountName"/></td>
  <bean:define id="poDate"  name="po" property="erpPoDate"/>

  <logic:present name="po" property="purchaseOrderData">
        <td><a href="<%=linkHref%>"><bean:write name="po" property="erpPoNum"/></a></td>
  </logic:present>
  <logic:notPresent name="po" property="purchaseOrderData">
        <td><bean:write name="po" property="erpPoNum"/></td>
  </logic:notPresent>

  <td><bean:write name="po" property="erpOrderNum"/></td>
  <td>
        <logic:present name="poDate">
          <i18n:formatDate value="<%=poDate%>" pattern="MM/dd/yyyy" locale="<%=Locale.US%>"/>
        </logic:present>
  </td>

  <%--po status cd--%>
  <logic:present name="po" property="purchaseOrderData">
        <td><bean:write name="po" property="purchaseOrderData.purchaseOrderStatusCd"/></td>
  </logic:present>
  <logic:notPresent name="po" property="purchaseOrderData">
        <td>&nbsp;</td>
  </logic:notPresent>

  <%--target ship date--%>
  <td>
 <logic:present name="po" property="orderItemData">
        <logic:present name="po" property="orderItemData.targetShipDate">
                <bean:define id="targetShipDate"  name="po" property="orderItemData.targetShipDate"/>
                <logic:present name="targetShipDate">
                          <i18n:formatDate value="<%=targetShipDate%>" pattern="MM/dd/yyyy" locale="<%=Locale.US%>"/>
                </logic:present>
        </logic:present>
  </logic:present>
  </td>

  <%--open line status cd--%>
  <td>
  <bean:define id='oplCd' name="po" property='openLineStatusCd' type='java.lang.String'/>
  <html:select name="PO_OP_SEARCH_FORM" property="openLineStatusUpdates" value='<%=oplCd%>'>
        <html:option value=""><app:storeMessage  key="admin.select"/></html:option>
        <html:options  collection="Open.line.status.vector" property="value" />
  </html:select>
  </td>

  <td><bean:write name="po" property="erpSkuNum"/></td>

  <%--line unit amount--%>
  <td>
    <logic:present name="po" property="erpUnitCost">
      <bean:define id="erpUnitCost"  name="po" property="erpUnitCost"/>
                  <i18n:formatCurrency value="<%=erpUnitCost%>" locale="<%=Locale.US%>"/>
    </logic:present>
  </td>

  <td><bean:write name="po" property="erpOrderedQty"/></td>
  <td><bean:write name="po" property="erpOpenQty"/></td>

  <%--open line amount--%>
  <td>
    <logic:present name="po" property="erpOpenAmount">
      <bean:define id="erpOpenAmount"  name="po" property="erpOpenAmount"/>
                  <i18n:formatCurrency value="<%=erpOpenAmount%>" locale="<%=Locale.US%>"/>
    </logic:present>
  </td>
  <td><bean:write name="po" property="erpItemDescription"/></td>

  <logic:present name="orderItemId">
        <logic:equal name="orderItemId" value="0">
                <td>&nbsp;</td><td>&nbsp;</td>
        </logic:equal>
        <logic:notEqual name="orderItemId" value="0">
                <logic:equal name="po" property="hasNote" value="true">
                        <td><input type="button" class="smallbutton" onclick="popLink('orderOpNote.do?action=view&type=item&itemid=<%=orderItemId%>');" value="VIN"></td>
                </logic:equal>
                <logic:notEqual name="po" property="hasNote" value="true">
                        <td>&nbsp;</td>
                </logic:notEqual>
                <td><input type="button" class="smallbutton" onclick="popLink('orderOpNote.do?action=add&type=item&itemid=<%=orderItemId%>');" value="AIN"></td>
        </logic:notEqual>
  </logic:present>
  <logic:notPresent name="orderItemId">
        <td>&nbsp;</td><td>&nbsp;</td>
  </logic:notPresent>
  <logic:present name="orderId">
        <logic:equal name="orderId" value="0">
                <td>&nbsp;</td><td>&nbsp;</td>
        </logic:equal>
        <logic:notEqual name="orderId" value="0">
                <td><input type="button" class="smallbutton" onclick="popLink('orderOpNote.do?action=view&orderid=<%=orderId%>');" value="VON" ></td>
                <td><input type="button" class="smallbutton" onclick="popLink('orderOpNote.do?action=add&orderid=<%=orderId%>');" value="AON" ></td>
        </logic:notEqual>
  </logic:present>
  <logic:notPresent name="orderId">
        <td>&nbsp;</td><td>&nbsp;</td>
  </logic:notPresent>
 </tr>
 </logic:iterate>

<tr><td>
        <html:submit property="action">
           <app:storeMessage  key="global.action.label.save"/>
        </html:submit>
</html:form>
</td></tr>
</table>
</logic:greaterThan>
</logic:present>
</div>
