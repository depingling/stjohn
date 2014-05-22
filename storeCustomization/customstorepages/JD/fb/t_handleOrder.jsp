<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="com.cleanwise.service.api.value.*"%>
<%@ page import="com.cleanwise.service.api.util.*"%>
<%@ page import="com.cleanwise.service.api.session.Order"%>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>

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
  for ( var i = 0; i < aaal.length; i++ ) {
    var aaa = aaal[i];
    if(aaa.value==actionDef) {
      aaa.value = action;
      aaa.form.submit();
      break;
    }
  }
 return false;
}
//-->
</script>

<%
try {
%>

<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>
<bean:define id="theOrder" type="com.cleanwise.service.api.value.OrderJoinData" name="order"/>
<bean:define id="workflowRole" type="String" name="ApplicationUser" property="user.workflowRoleCd"/>
<bean:define id="orderStatus" type="String" name="order" property="orderStatusCd"/>

<%
 SimpleDateFormat ymdSdf = new SimpleDateFormat("yyyy-MM-dd");
 CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
 String orderLocale = theOrder.getOrder().getLocaleCd();
 ClwI18nUtil clwI18n = ClwI18nUtil.getInstance(request, orderLocale, -1);
 //AccountData accountD = appUser.getUserAccount();
 AccountData accountD = SessionTool.getAccountData(request,theOrder.getOrder().getAccountId());
 if(accountD == null){
     accountD = appUser.getUserAccount();
 }
 SiteData siteD = SessionTool.getSiteData(request,theOrder.getOrder().getSiteId());
 SiteData currSiteD = appUser.getSite();
 if(siteD == null){
     siteD = currSiteD;
 }
 boolean allowPoEntry = true;
 if(RefCodeNames.CUSTOMER_SYSTEM_APPROVAL_CD.PUNCH_OUT_INLIN_NON_E_ORD_ONLY.
   equals(accountD.getCustomerSystemApprovalCd())){
   allowPoEntry = false;
 }

 boolean f_showPO = true;
 if(theOrder.getOrderCC()!=null) f_showPO = false;



 if(!accountD.isCustomerRequestPoAllowed()){
      allowPoEntry=false;
    }

 boolean usingBlanketPo = false;
 if(siteD!=null && siteD.getBlanketPoNum() != null && siteD.getBlanketPoNum().getBlanketPoNumId() != 0){
   usingBlanketPo = true;
   allowPoEntry = false;
 }

 boolean approverFl = (workflowRole.indexOf("APPROVER") >= 0)?true:false;
 int siteAccountId = accountD.getBusEntity().getBusEntityId();
 int orderAccountId = theOrder.getOrder().getAccountId();
 boolean warehouseFl = false;
 int siteId = 0;
 if(siteD != null){
   PropertyData warehousePropD = siteD.
     getMiscProp(RefCodeNames.PROPERTY_TYPE_CD.CONSOLIDATED_ORDER_WAREHOUSE);
   String warehouseVal = null;
   if(warehousePropD!=null) warehouseVal = warehousePropD.getValue();
   if ( null != warehouseVal ) { warehousePropD.getValue(); }
   warehouseFl = (warehouseVal != null && warehouseVal.length()>0 &&
      "T".equalsIgnoreCase(warehouseVal.substring(0, 1)))?true:false;
  siteId = siteD.getBusEntity().getBusEntityId();
 }



 boolean allowOrderConsolidationFl = false;
 String allowOrderConsolidationS =
   accountD.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_ORDER_CONSOLIDATION);
 if (allowOrderConsolidationS != null && allowOrderConsolidationS.length() > 0 &&
      "T".equalsIgnoreCase(allowOrderConsolidationS.substring(0, 1)) &&
      orderAccountId==siteAccountId) {
      allowOrderConsolidationFl = true;
 }

 String tOrderType = theOrder.getOrder().getOrderTypeCd();
 if ( null == tOrderType ) tOrderType = "";

 boolean consolidatedOrderFl =
 RefCodeNames.ORDER_TYPE_CD.CONSOLIDATED.equals(tOrderType);
 boolean toConsolidateOrderFl =
 RefCodeNames.ORDER_TYPE_CD.TO_BE_CONSOLIDATED.equals(tOrderType);

 boolean pendingFl =
    (orderStatus.equals(RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL) ||
     orderStatus.equals(RefCodeNames.ORDER_STATUS_CD.PENDING_CONSOLIDATION) ||
     orderStatus.equals(RefCodeNames.ORDER_STATUS_CD.PENDING_DATE))? true:false;
%>

<script language="JavaScript1.2">

<!--
function viewPrinterFriendly(oid) {
  var loc = "printerFriendlyOrder.do?action=view&orderId=" + oid;
  prtwin = window.open(loc,"printer_friendly",
    "menubar=yes,resizable=yes,scrollbars=yes,toolbar=yes,status=yes,height=500,width=775,left=100,top=165");
  prtwin.focus();
  return false;
}

function approve (oid) {
  var dd = window.document.forms[0].approveDate.value;
  var poNum = window.document.forms[0].requestPoNum.value;
  var loc = "handleOrder.do?action=approve&orderId=" + oid+"&date="+dd+"&requestPoNum="+poNum;
  window.location=loc;
  return false;
}

function approveOn (oid) {
  var dd = window.document.forms[0].approveDate.value;
//alert (date);
  var loc = "handleOrder.do?action=approveOn&orderId=" + oid+"&date="+dd;
  window.location=loc;
  return false;
}
//-->

</script>
<form name="HANDLE_ORDER" action="">
<!--
<div class="text"><font color=red><html:errors/></font></div>
-->

<table align=center CELLSPACING=0 CELLPADDING=10  width="<%=Constants.TABLEWIDTH%>" class="tbstd">
<tr valign="top">
  <td>
    <jsp:include flush='true' page="<%=ClwCustomizer.getStoreFilePath(request,\"f_budgetInfoInc.jsp\")%>">
      <jsp:param name="isPendingOrderBudjet" value="true" />
    </jsp:include>
  </td>
</tr>


<tr class="orderInfoHeader">
<td align="left" class="text"><b>
<% if(consolidatedOrderFl) {%>
 <app:storeMessage key="shop.orderStatus.text.consolidatedToOrder"/>
<% } else { %>
 <app:storeMessage key="shop.orderStatus.text.orderInformation"/>
 <%
   OrderMetaData omD = theOrder.getMetaObject(Order.MODIFICATION_STARTED);
   if(omD!=null) {
 %>
     <br><font color="red">
     <app:storeMessage key="shop.orderStatus.text.ModificationStartedOnBy"
     arg0='<%=(new SimpleDateFormat("yyyy-MM-dd HH:mm")).format(omD.getModDate())%>'
     arg1='<%=omD.getValue()%>'/>
     </font>
 <% } %>

<% } %>
</b></td></tr>

</table>


<logic:present name="order">
<bean:define id="lOrderId"  name="order" property="orderId"/>

<html:hidden  property="orderId" value="<%=lOrderId.toString()%>"/>

<table align=center CELLSPACING=0 CELLPADDING=0
  width="<%=Constants.TABLEWIDTH%>">
<tr>
<td style="border-right: solid 1px black; border-left: solid 1px black;
  font-weight: bold;">

<table align=center CELLSPACING=0 CELLPADDING=0 >

<tr>
<td class="text"><app:storeMessage key="shop.orderStatus.text.orderNumber"/></td>
<td><bean:write name="order" property="orderNum"/></td>
</tr>
<tr>
<td class="text"><app:storeMessage key="shop.orderStatus.text.orderDate"/></td>
<td><bean:write name="order" property="orderDate"/></td>
</tr>
<tr>
<td class="text"><app:storeMessage key="shop.orderStatus.text.placedBy"/></td>
<td>
<logic:present name="order" property="placedByFirstName"><bean:write name="order" property="placedByFirstName"/></logic:present>
<logic:present name="order" property="placedByLastName"> <bean:write name="order" property="placedByLastName"/></logic:present>
(<bean:write name="order" property="addBy"/>)
</td>
</tr>
<%
String orderContactName = theOrder.getOrderData().getOrderContactName();
if (orderContactName != null && orderContactName.length() > 0 &&
    !orderContactName.equalsIgnoreCase("NA NA")) {
      String userFirstName = theOrder.getOrderData().getUserFirstName();
      String userLastName = theOrder.getOrderData().getUserLastName();
      String userFullName = "";
      if (userFirstName != null && userLastName != null) {
        userFullName = userFirstName + " " + userLastName;
      }
      if (!userFullName.equals(orderContactName)) {
%>
        <tr>
<td class="text"><app:storeMessage key="shop.orderStatus.text.requestedBy"/></td>
<td><bean:write name="order" property="orderData.orderContactName"/></td>

        </tr>
<%    } %>
<%} %>

<%
   String poNum = theOrder.getOrder().getRequestPoNum();
   if(poNum==null) poNum="";
   if(allowPoEntry && f_showPO){
%>
  <tr>
  <td class="text"><app:storeMessage key="shop.orderStatus.text.poNumber"/></td>
  <td><input type="text" name='requestPoNum' value = '<%=poNum%>'
     size='30'/><%if(appUser.getPoNumRequired()){%><font color="red">*</font><%}%>
  </td>
  </tr>
 <%}else if(usingBlanketPo){%>
   <tr>
  <td class="text"><app:storeMessage key="shop.orderStatus.text.poNumber"/></td>
  <td>
  <% if(poNum!=null && poNum.trim().length()>0) { %>
    <%=poNum%>
  <% } else { %>
    <%=siteD.getBlanketPoNum().getPoNumber()%>
  <% } %>
  <input type="hidden"  name='requestPoNum' value = '<%=poNum%>' />
  </td>
  </tr>
  <%}else{%>
   <input type="hidden" name='requestPoNum' value = '<%=poNum%>' />
  <% } %>

<% String pendingDate = theOrder.getMetaValue(RefCodeNames.ORDER_PROPERTY_TYPE_CD.PENDING_DATE);
   if(pendingDate!=null && pendingDate.length()>7) {
    pendingDate = pendingDate.substring(6)+"-"+pendingDate.substring(0,2)+"-"+pendingDate.substring(3,5);
   }
%>

<% if(RefCodeNames.ORDER_STATUS_CD.PENDING_DATE.equals(theOrder.getOrderStatusCd())) {%>
<tr>
<td class="text"><app:storeMessage key="shop.orderStatus.text.processOrderOn"/></td>
<td><%=pendingDate%></td>
</tr>
<tr>
<% } %>
<td class="text"><app:storeMessage key="shop.orderStatus.text.shippingComments"/></td>
<td><bean:write name="order" property="comments"/></td>
<td>
</td>
</tr>

<% //Workflow properties
IdVector canApproveIdV = new IdVector();

OrderPropertyDataVector opDV =
  theOrder.getOrderProperties("Workflow Note",
                              RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE,
                              RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
if(opDV.size()>0) {
  canApproveIdV = (IdVector) session.getAttribute("note.approve.ids");
%>
<tr><td colspan="3" class="text"><b><app:storeMessage key="shop.orderStatus.text.notes:"/></b></td><tr>
<% for(int ii=0; ii<opDV.size(); ii++) {
  OrderPropertyData opD = (OrderPropertyData) opDV.get(ii);
  String messKey = opD.getMessageKey();
%>
<tr><td colspan="3">
  <% if(messKey==null){ %>
  <%=opD.getValue()%>
  <% } else { %>
  <app:storeMessage key="<%=messKey%>"
    arg0="<%=opD.getArg0()%>" arg1="<%=opD.getArg1()%>"
    arg2="<%=opD.getArg2()%>" arg3="<%=opD.getArg3()%>" />
  <% } %>
 <% if(opD.getApproveDate()!=null) { %>
 (<app:storeMessage key="shop.orderStatus.text.clearedOnBy"
   arg0='<%=ymdSdf.format(opD.getApproveDate())%>'
    arg1='<%=opD.getModBy()%>'/>)
<% } else {
  Integer opIdI = new Integer(opD.getOrderPropertyId());
  if(canApproveIdV.contains(opIdI)) {
%>
 <input type="checkbox" name='<%="approveNote"+opIdI%>' value='<%=opIdI.toString()%>' checked /></td>
<% } else {
   if(approverFl || !canApproveIdV.isEmpty()) {
%>
 <input type="checkbox" name='<%="wn"+opIdI%>' value='<%=opIdI.toString()%>' disabled /></td>
<% } %>
<% } %>
<% } %>
<% } %>
</td></tr>

<%}%>

<% //cart comment properties
OrderPropertyDataVector ccOpDV =
  theOrder.getOrderProperties(RefCodeNames.ORDER_PROPERTY_TYPE_CD.CUSTOMER_CART_COMMENTS,
                              RefCodeNames.ORDER_PROPERTY_TYPE_CD.CUSTOMER_CART_COMMENTS,
                              RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
pageContext.setAttribute("customerOrderNotes",ccOpDV);
%>
<logic:present name="customerOrderNotes">
<tr><td class="text" colspan="2">
<table>

  <bean:size id="custNotesCt" name="customerOrderNotes"/>
  <logic:greaterThan name="custNotesCt" value="0">
  <tr>
     <td class="text" colspan="2"><b><bean:write name="order" property="billShortDesc"/>
      <app:storeMessage key="shop.orderStatus.text.comments:"/></b></td>
   </tr>
   <logic:iterate name="customerOrderNotes"id="note" type="com.cleanwise.service.api.value.OrderPropertyData">
    <tr>
<td class="text" width="150"
 style="vertical-align:top; padding-left:20; white-space: nowrap;">
<logic:present name="note" property="addDate">
<bean:define id="commentDate" name="note" property="addDate"
  type="java.util.Date" />
<b>
<%=ClwI18nUtil.formatDate(request,commentDate,DateFormat.LONG)%> -
<bean:write name="note" property="addBy"/>
 </b>
 </logic:present>
</td>
<td style="text-align:left;">
<app:writeHTML name="note" property="value"/>
</td>

</logic:iterate>
</logic:greaterThan>
</table>
</td></tr>
</logic:present>
<%-- END cart comment properties --%>

<tr>
<td colspan=3 class="tableoutline"><img src="/images/cw_spacer.gif" height="1"></td>
</tr>
<tr>
<td colspan=3>
<table align=center CELLSPACING=0 CELLPADDING=3 width=750>
<% /* Start - List the items for the order. */ %>

<tr>
<logic:equal name="order" property="orderHasInventoryItem" value="true">
<td class="shopcharthead">&nbsp;</td>
<td class="shopcharthead"><app:storeMessage key="shoppingItems.text.par"/></td>
<td class="shopcharthead"><app:storeMessage key="shoppingItems.text.onHand"/></td>
</logic:equal>
<td class="shopcharthead"><div class="fivemargin"><app:storeMessage key="shoppingItems.text.orderQty"/></td>
<td class="shopcharthead"><div class="fivemargin"><app:storeMessage key="shoppingItems.text.ourSkuNum"/></td>
<td class="shopcharthead"><div class="fivemargin"><app:storeMessage key="shoppingItems.text.name"/></div></td>
<td class="shopcharthead"><div class="fivemargin"><app:storeMessage key="shoppingItems.text.size"/></div></td>
<td class="shopcharthead"><div class="fivemargin"><app:storeMessage key="shoppingItems.text.pack"/></div></td>
<td class="shopcharthead"><div class="fivemargin"><app:storeMessage key="shoppingItems.text.uom"/></div></td>
<td class="shopcharthead"><div class="fivemargin"><app:storeMessage key="shoppingItems.text.color"/></div></td>
<logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">
<td class="shopcharthead"><div class="fivemargin"><app:storeMessage key="shoppingItems.text.price"/></div></td>
</logic:equal>
<td class="shopcharthead"><div class="fivemargin"><app:storeMessage key="shoppingItems.text.status"/></div></td>
<% if (appUser.getUserAccount().isShowSPL()) { %>
<td class="shopcharthead"><div class="fivemargin"><app:storeMessage key="shoppingItems.text.spl"/></div></td>
<%} %>
</tr>

<logic:present name="order">
  <logic:iterate id="item" name="order" property="orderJoinItems"
   offset="0" indexId="oidx"
   type="com.cleanwise.service.api.value.OrderItemJoinData">

 <tr bgcolor="<%=ClwCustomizer.getEvenRowColor(session,(Integer)oidx)%>">

<bean:define id="orderItemData"  name="item" property="orderItem"/>

        <logic:equal name="order" property="orderHasInventoryItem" value="true">

<logic:equal name="item" property="isAnInventoryItem" value="true">
<td  width=15>
<span class="inv_item">
i
<% if (ShopTool.getCurrentSite(request).isAnInventoryAutoOrderItem(
  item.getItemId())) { %>
a
<% } %>
</span>
</td>
<td><bean:write name="orderItemData" property="inventoryParValue"/></td>
<td><bean:write name="orderItemData" property="inventoryQtyOnHand"/></td>
</logic:equal>

<logic:equal name="item" property="isAnInventoryItem" value="false">
<td>&nbsp;</td>
<td>&nbsp;</td>
<td>&nbsp;</td>
</logic:equal>

        </logic:equal>

<td><bean:write name="item" property="qty"/></td>
<td><%=ShopTool.getRuntimeSku(item,request)%></td>
<td><bean:write name="item" property="shortDesc"/></td>
<td><bean:write name="item" property="size"/></td>
<td><bean:write name="item" property="pack"/></td>
<td><bean:write name="item" property="uom"/></td>
<td><bean:write name="item" property="color"/></td>
<logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">
<td align="right">
<logic:present name="item" property="amount">
<bean:define id="l_amt" type="java.math.BigDecimal"name="item" property="amount"/>
<%=clwI18n.priceFormat(l_amt,"&nbsp;")%>
</logic:present>
</td>
</logic:equal>
<td>
<% if(RefCodeNames.ORDER_ITEM_STATUS_CD.CANCELLED.equals(item.getItemStatusCd())) { %>
 Cancelled
<% } else { %>
 &nbsp;
<% } %>
</td>
<% if (appUser.getUserAccount().isShowSPL()) { %>
<td align="center">
  <%if(Utility.isTrue(item.getStandardProductList())){%>
      <app:storeMessage key="shoppingItems.text.y"/>
  <%}else{%>
      <app:storeMessage key="shoppingItems.text.n"/>
  <%}%>
</td>
<% } %>
</tr>
  </logic:iterate>
</logic:present>

</table> <% /* End   - List the items for the order. */ %>

</td>
</tr>


<tr>
<td colspan=3 class="tableoutline"><img src="/images/cw_spacer.gif" height="1"></td>
</tr>
<tr class="checkoutSummaryHeader">
<td align="center" class="text"><b>&raquo; <app:storeMessage key="shop.orderStatus.text.orderSummary"/></b></td>
<td align="center" class="text"><b>&raquo; <app:storeMessage key="shop.orderStatus.text.shipTo"/></b></td>
<td align="center" class="text"><b>&raquo; <app:storeMessage key="shop.orderStatus.text.billTo"/></b></td>
</tr>
<tr>
<td colspan=3 class="tableoutline"><img src="/images/cw_spacer.gif" height="1"></td>
</tr>

<tr valign=top>

<td class="rowb">

<table align=center> <% /* Order summary data. */ %>
<% boolean freightHandleFl = false; %>
<logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">
<tr>
<bean:define id="v_amt" type="java.math.BigDecimal"
  name="order" property="amount"/>
<td class="smalltext"><b><app:storeMessage key="shop.orderStatus.text.amount:"/></b></td>
<td  align=right>
<%=clwI18n.priceFormat(v_amt,"&nbsp;")%>
</td>
</tr>

<bean:define id="v_handlingamt" type="java.math.BigDecimal"
  name="order" property="orderData.totalMiscCost"/>
<%
 if ( null != v_handlingamt && v_handlingamt.intValue() > 0 ) {
  freightHandleFl = true;
%>
<tr>
<td class="smalltext"><b><app:storeMessage key="shop.orderStatus.text.handling:"/></b></td>
<td  align=right>
<% if(!toConsolidateOrderFl) { %>
<%=clwI18n.priceFormat(v_handlingamt,"&nbsp;")%>
<% } else {  %>
*
<% } %>
</td>
</tr>
<% } %>


<logic:present name="order" property="orderData.totalFreightCost">
<bean:define id="v_frtamt" type="java.math.BigDecimal"
  name="order" property="orderData.totalFreightCost"/>
<%
  if ( null != v_frtamt && v_frtamt.intValue() > 0 ) {
  freightHandleFl = true;
%>
<tr>
<td class="smalltext"><b><app:storeMessage key="shop.orderStatus.text.freight:"/></b></td>
<td  align=right>
<% if(!toConsolidateOrderFl) { %>
<%=clwI18n.priceFormat(v_frtamt,"&nbsp;")%>
<% } else {  %>
*
<% } %>
</td>
</tr>
<% } %>
</logic:present>
<%--
<logic:present name="order" property="rushOrderCharge">
<bean:define id="v_rushamt" type="java.math.BigDecimal"
  name="order" property="rushOrderCharge"/>
<tr>
<td class="smalltext"><b><app:storeMessage key="shop.orderStatus.text.rushOrderCharge:"/></b></td>
<td  align=right>
<% if(!toConsolidateOrderFl) { %>
<%=clwI18n.priceFormat(v_rushamt,"&nbsp;")%>
<% } else {  %>
*
<% } %>
</td>
</tr>
</logic:present>
--%>
<tr>
<td class="smalltext"><b><app:storeMessage key="shop.orderStatus.text.total:"/></b></td>
<td  align=right>
<% if(!toConsolidateOrderFl) { %>
<%=clwI18n.priceFormat(theOrder.getSumOfAllOrderCharges(),"&nbsp;")%>
<% } else {  %>
*
<% } %>
</td>
</tr>

</logic:equal> <% /* showPrice */ %>

<tr>
<td class="smalltext"><b><app:storeMessage key="shop.orderStatus.text.orderStatus:"/></b></td>
<td> <%=com.cleanwise.view.utils.ShopTool.xlateStatus(theOrder)%> </td>
</tr>


<%
  if(pendingFl) {
  if (approverFl || !canApproveIdV.isEmpty()) {
  // Show the buttons to approve, reject, and modify
  // the order.
%>
<tr><td colspan=2>
<%
String rejectloc = "javascript:window.location='handleOrder.do?action=reject&orderId=" + lOrderId + "' ";
String approveloc =  "javascript:approve("+lOrderId + ")";
String modifyloc = "javascript:window.location='handleOrder.do?action=modify&orderId=" + lOrderId + "' ";
String approveOn = "javascript:approveOn("+lOrderId + ")";
%>
<html:button styleClass="store_fb" property="action"  onclick='<%=rejectloc%>' >
  <app:storeMessage key="global.action.label.reject" />
</html:button>
<% if(!toConsolidateOrderFl) { %>
<html:button styleClass="store_fb" property="action"
  onclick="actionMultiSubmit('BBBBBBB', 'Approve');">
  <app:storeMessage key="global.action.label.approve" />
</html:button>
<% } %>
<% if(!consolidatedOrderFl) { %>
<html:button styleClass="store_fb" property="action"  onclick='<%=modifyloc%>' >
  <app:storeMessage key="shop.orderStatus.text.modify" />
</html:button>
<% } %>
<% if(!toConsolidateOrderFl) { %>
<br><html:button styleClass="store_fb" property="action"
  onclick="actionMultiSubmit('BBBBBBB', 'ApproveOn');" >
  <app:storeMessage key="shop.orderStatus.text.approveOn" />
</html:button>
<%
   String currDate = ClwI18nUtil.formatDateInp(request,new java.util.Date());
%>
<input type="text" name='approveDate' value = '<%=currDate%>' size='10'/><br>
(<%=ClwI18nUtil.getUIDateFormat(request)%>)<br>
<% } %>
<% if(consolidatedOrderFl) {
  String deconsolidateloc = "javascript:window.location='handleOrder.do?action=deconsolidate&orderId=" + lOrderId + "' ";
%>
<html:button styleClass="store_fb" property="action" onclick='<%=deconsolidateloc%>' >
  <app:storeMessage key="shop.orderStatus.text.undoConsolidation" />
</html:button> 
<% } %>
<% } %>
<% if(toConsolidateOrderFl && warehouseFl) {
String consolidateloc = "javascript:window.location='handleOrder.do?action=consolidate&orderId=" + lOrderId + "' ";
%>
<html:button styleClass="store_fb" property="action"  onclick='<%=consolidateloc%>'>
  <app:storeMessage key="shop.orderStatus.text.consolidate" />
</html:button>
<% } %>
<% if(currSiteD.getSiteId()!=siteId) {
String gotoorderloc = "javascript:window.location='handleOrder.do?action=goToOrderLocation&orderId=" + lOrderId + "' ";
%>
<html:button styleClass="store_fb" property="action"  onclick='<%=gotoorderloc%>'>
  <app:storeMessage key="shop.orderStatus.text.goToOrderLocation" />
</html:button>
<% } %>



</td></tr>
<% } %>

</table> <% /* Order summary data. */ %>

</td>

<td>

<table  align=center> <% /* Ship to data. */ %>
<tr valign=top>
<td  class="smalltext"><b><app:storeMessage key="shop.orderStatus.text.contact:"/></b></td>

<td>
<bean:write name="order" property="order.orderContactName"/>
</td>
</tr>

<tr valign=top>
<td class="smalltext"><b><app:storeMessage key="shop.orderStatus.text.address:"/></b></td>
<td>
<bean:write name="order" property="shipShortDesc"/>
<br>
<bean:write name="order" property="shipAddress1"/>
<logic:present name="order" property="shipAddress2">
  <br>
  <bean:write name="order" property="shipAddress2"/>
</logic:present>
<logic:present name="order" property="shipAddress3">
<br>
<bean:write name="order" property="shipAddress3"/>
</logic:present>
<logic:present name="order" property="shipAddress4">
<br>
<bean:write name="order" property="shipAddress4"/>
</logic:present>
<br>
<bean:write name="order" property="shipCity"/>
<% if (appUser.getUserStore().isStateProvinceRequired()) { %>
<br>
<bean:write name="order" property="shipStateProvinceCd"/>
<% } %>
<br>
<bean:write name="order" property="shipPostalCode"/>
<br>
<bean:write name="order" property="shipCountryCd"/>
</td>
</tr>
</table> <% /* Ship to data. */ %>

</td>

<td >
<table  align=center> <% /* Account bill to information. */ %>
<tr valign=top>
<td class="smalltext"><b><app:storeMessage key="shop.orderStatus.text.address:"/></b></td>
<td>
<bean:write name="order" property="billShortDesc"/>
<br>
<bean:write name="order" property="billAddress1"/>
<logic:present name="order" property="billAddress2">
<br>
<bean:write name="order" property="billAddress2"/>
</logic:present>
<logic:present name="order" property="billAddress3">
<br>
<bean:write name="order" property="billAddress3"/>
</logic:present>
<logic:present name="order" property="billAddress4">
<br>
<bean:write name="order" property="billAddress4"/>
</logic:present>
<br>
<bean:write name="order" property="billCity"/>
<%
String billState = theOrder.getBillStateProvinceCd();
if (billState != null && billState.length() > 0) {
%>
<br>
<bean:write name="order" property="billStateProvinceCd"/>
<% } %>
<br>
<bean:write name="order" property="billPostalCode"/>
<br>
<bean:write name="order" property="billCountryCd"/>
</td>
</tr>
</table>


</td>

</tr>

<tr>
<td colspan=3 class="tableoutline"><img src="/images/cw_spacer.gif" height="1"></td>
</tr>
<% if(freightHandleFl && toConsolidateOrderFl) { %>
<tr>
<td colspan=3>*
  <app:storeMessage key="shop.orderStatus.text.actualHandlingFreightWillBeCalculatedAtTimeOfConsolidatedOrder"/></td>
</tr>
<% } %>
<%
  ReplacedOrderViewVector replacedOrders = theOrder.getReplacedOrders();
  if(replacedOrders!=null && replacedOrders.size()>0) {
%>
<%@ include file="replacedOrders.jsp" %>
<% } %>
</table>
</td></tr>

</table>

</logic:present>

<logic:notPresent name="order">

<table align=center CELLSPACING=0 CELLPADDING=0
  width="<%=Constants.TABLEWIDTH%>">
<tr align=center>
<td style="border-right: solid 1px black; border-left: solid 1px black;
  font-weight: bold;">
<br><br><br><br>
 <app:storeMessage key="shop.orderStatus.text.NoOrderDataAvailable"/>
<br><br><br><br>
</td></tr></table>

</logic:notPresent>
<html:hidden property="action" value="BBBBBBB"/>
<html:hidden property="action" value="CCCCCCC"/>
</form>


<%@ include file="f_table_bottom.jsp" %>

<%
} catch (Exception e) {
  e.printStackTrace();
}
%>


