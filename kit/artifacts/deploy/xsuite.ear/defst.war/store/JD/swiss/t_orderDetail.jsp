<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="com.cleanwise.service.api.value.*"%>
<%@ page import="com.cleanwise.service.api.util.*"%>
<%@ page import="java.math.BigDecimal"%>
<%@ page import="java.util.Locale" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.TimeZone" %>
<%@ page import="java.util.Date" %>

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
  for ( var i = 0; i < 1; i++ ) {
    var aaa = aaal[i];
    aaa.value = action;
    aaa.form.submit();
  }
 return false;
}

 function actionSubmit(actionDef, action) {
    var actions = document.getElementsByName('action');
    //alert("actionDef: " + actionDef + "     actions.length:  " + actions.length);
    for(ii = 0; ii < actions.length; ii++) {
        //alert("value: " + actions[ii].value);
        if (actions[ii].value == actionDef) {
            //alert("action: " + action);
            actions[ii].value = action;
            actions[ii].form.submit();
        break;
        }
    }
    return false;
 }

//-->

</script>




<% CleanwiseUser appUser = (CleanwiseUser)
     session.getAttribute(Constants.APP_USER); %>



<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>
<bean:define id="theForm" name="ORDER_OP_DETAIL_FORM" type="com.cleanwise.view.forms.OrderOpDetailForm"/>
<bean:define id="workflowRole" type="java.lang.String" name="ApplicationUser" property="user.workflowRoleCd"/>
<bean:define id="orderStatus" name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.orderDetail.orderStatusCd" type="String"/>

<%
OrderStatusDescData orderStatusDetail = theForm.getOrderStatusDetail();
  String orderLocale = orderStatusDetail.getOrderDetail().getLocaleCd();
  ClwI18nUtil clwI18n = ClwI18nUtil.getInstance(request,orderLocale,-1);
  SimpleDateFormat sdfInter = new SimpleDateFormat("yyyy-MM-dd");
  TimeZone timeZone =  Utility.getTimeZoneFromUserData(appUser.getSite());
  SimpleDateFormat sdfEntry = new SimpleDateFormat("yyyy-MM-dd" +" " + Constants.SIMPLE_TIME24_PATTERN + ".S");
  sdfEntry.setTimeZone(timeZone);
  SimpleDateFormat sdfComment = new SimpleDateFormat("yyyy-MM-dd k:mm");
  sdfComment.setTimeZone(timeZone);
  boolean approverFl = (workflowRole.indexOf("APPROVER") >= 0) ? true : false;
  boolean consolidatedOrderFl =
   RefCodeNames.ORDER_TYPE_CD.CONSOLIDATED.equals(orderStatusDetail.getOrderDetail().getOrderTypeCd());

  boolean toBeConsolidatedFl =
   RefCodeNames.ORDER_TYPE_CD.TO_BE_CONSOLIDATED.equals(orderStatusDetail.getOrderDetail().getOrderTypeCd());

  OrderData consolidatedToOrderD = orderStatusDetail.getConsolidatedOrder();

  boolean useCustPo = true;
  String requestPoNum = orderStatusDetail.getOrderDetail().getRequestPoNum();

  if(requestPoNum == null || requestPoNum.equals("") ||
  requestPoNum.equalsIgnoreCase("na") ||	requestPoNum.equalsIgnoreCase("n/a")){
    useCustPo = false;
  }
  %>
<script language="JavaScript1.2">

<!--
function viewPrinterFriendly(oid) {
  var loc = "orderDetail.do?action=printOrderStatus&id=" + oid;
  <% if (appUser.getUserAccount().isHideItemMfg()) { %>
      loc = loc + "&showMfg=n&showMfgSku=n";
  <% } %>
  prtwin = window.open(loc,"printer_friendly",
    "menubar=yes,resizable=yes,scrollbars=yes,toolbar=yes,status=yes,height=500,width=775,left=100,top=165");
  prtwin.focus();

  return false;
}
//-->

</script>

<!--<div class="text"><font color=red><html:errors/></font></div>-->
<%
    if (!theForm.getUserMessages().isEmpty()) {
%>
        <div class="text">
            <font color="blue">
                <%
                for (int i = 0; i < theForm.getUserMessages().size(); i++) {
                    String message = (String) theForm.getUserMessages().get(i); %>
                    <%=message%><br>
                <% } %>
            </font>
        </div>
<%
    }
%>

<table align=center CELLSPACING=0 CELLPADDING=10
  width="<%=Constants.TABLEWIDTH%>" class="tbstd">
<tr valign="top">
  <td>
    <jsp:include flush='true' page="<%=ClwCustomizer.getStoreFilePath(request,\"f_budgetInfoInc.jsp\")%>">
      <jsp:param name="isPendingOrderBudjet" value="true" />
    </jsp:include>
  </td>
</tr>
<tr class="orderInfoHeader">
<td align="left" class="text"><b>
<%if(consolidatedOrderFl){ %>
  <app:storeMessage key="shop.catalog.text.consolidateOrder"/>
<% } else { %>
  <app:storeMessage key="shop.catalog.text.orderInformation"/>
<% } %>
</b></td>
<td align="right" class="text">
  <a href="#" class="linkButton" onclick="viewPrinterFriendly('<%=orderStatusDetail.getOrderDetail().getOrderId()%>');"
  ><img src='<%=IMGPath + "/b_print.gif"%>' border="0"/><app:storeMessage key="global.label.printerFriendly"/></a>
</td>
</tr>

</table>


<table align=center CELLSPACING=0 CELLPADDING=0
  width="<%=Constants.TABLEWIDTH%>">

<html:form name="ORDER_OP_DETAIL_FORM" action="/store/orderDetail.do"
        type="com.cleanwise.view.forms.OrderOpDetailForm">
<html:hidden property="action" value="BBBBBBB"/>

<logic:present name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail">

<bean:define id="lOrderId"
  name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.orderDetail.orderId"/>


<tr>
<td style="border-right: solid 1px black; border-left: solid 1px black;
  font-weight: bold;">

<table cellpadding=0 cellspacing=0 align=center>

<tr>
<td colspan="3">

<table width=750 cellpadding=2 cellspacing=0>
<tr>
<td>
  <table  width=355>
  <tr>
  <td class="text"><b><app:storeMessage key="shop.catalog.text.orderNumber"/></b></td>
  <td><bean:write name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.orderDetail.orderNum"/></td>
  </tr>
  <% if(consolidatedToOrderD!=null) {%>
  <tr>
  <td class="text"><b><app:storeMessage key="shop.catalog.text.consolidatedToOrder"/></b></td>
  <td><%=consolidatedToOrderD.getOrderNum()%></td>
  </tr>
  <% } %>

  <logic:present name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.refOrder">
    <tr>
      <td class="text"><b><app:storeMessage key="shop.catalog.text.referenceOrderNum"/></b></td>
      <td><bean:write name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.refOrder.orderNum"/></td>
    </tr>
  </logic:present>

  <tr>
<td class="text"><b><app:storeMessage key="shop.orderStatus.text.orderDate"/></b></td>
<%
   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
   sdf.setTimeZone(timeZone);
   Date date = orderStatusDetail.getOrderDetail().getOriginalOrderDate();
   Date time = orderStatusDetail.getOrderDetail().getOriginalOrderTime();
   Date orderDate = Utility.getDateTime(date, time);
   String formattedODate = ClwI18nUtil.formatDateInp(request, orderDate, timeZone.getID() );
%>
<td><%=formattedODate%></td>
<%--
<td>
<bean:define id="ordd" type="java.util.Date"
 name="ORDER_OP_DETAIL_FORM"
 property="orderStatusDetail.orderDetail.originalOrderDate"/>
<i18n:formatDate value="<%=ordd%>" pattern="yyyy-MM-dd"/>
</td>
--%>

</tr>

<logic:present name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.requestedShipDate">
<bean:define id="ordreqd" type="java.lang.String"
 name="ORDER_OP_DETAIL_FORM"
 property="orderStatusDetail.requestedShipDate"/>
 <% if ( ordreqd != null && ordreqd.length() > 0) { %>
        <tr>
<td class="text"><b><app:storeMessage key="shop.orderStatus.text.requestedShipDate"/></b></td>
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
AccountData accountD = SessionTool.getAccountData(request,orderStatusDetail.getOrderDetail().getAccountId());
 if(accountD == null){
     accountD = appUser.getUserAccount();
 }
SiteData siteD = SessionTool.getSiteData(request,orderStatusDetail.getOrderDetail().getSiteId());
SiteData currSiteD = appUser.getSite();
if(siteD == null){
    siteD = currSiteD;
}
boolean allowPoEntry = true;
 if(RefCodeNames.CUSTOMER_SYSTEM_APPROVAL_CD.PUNCH_OUT_INLIN_NON_E_ORD_ONLY.
   equals(accountD.getCustomerSystemApprovalCd())){
   allowPoEntry = false;
 }
 if(!accountD.isCustomerRequestPoAllowed()){
      allowPoEntry=false;
    }

 boolean usingBlanketPo = false;
 if(siteD!=null && siteD.getBlanketPoNum() != null && siteD.getBlanketPoNum().getBlanketPoNumId() != 0){
   usingBlanketPo = true;
   allowPoEntry = false;
 }
 int siteAccountId = accountD.getBusEntity().getBusEntityId();
 boolean warehouseFl = false;
 int siteId = 0;
 if(siteD != null){
    siteId = siteD.getBusEntity().getBusEntityId();
 }
 if(currSiteD != null){
   PropertyData warehousePropD = currSiteD.
     getMiscProp(RefCodeNames.PROPERTY_TYPE_CD.CONSOLIDATED_ORDER_WAREHOUSE);
   String warehouseVal = null;
   if(warehousePropD!=null) warehouseVal = warehousePropD.getValue();
   if ( null != warehouseVal ) { warehousePropD.getValue(); }
   warehouseFl = (warehouseVal != null && warehouseVal.length()>0 &&
      "T".equalsIgnoreCase(warehouseVal.substring(0, 1)))?true:false;
 }
%>

<td class="text"><b><app:storeMessage key="shop.orderStatus.text.poNumber"/></b></td>
<td>
<%
  String thisPoNum = orderStatusDetail.getOrderDetail().getRequestPoNum();
  if ( null == thisPoNum ) thisPoNum = "";
  if (
    orderStatus.equals(RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL)
    && appUser.canMakePurchases() && allowPoEntry
     ) { %>



<html:text name="ORDER_OP_DETAIL_FORM" property="requestPoNum"
 value="<%=thisPoNum%>" size="30"
 />
<% } else { %>
<bean:write name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.orderDetail.requestPoNum"/>
<html:hidden name="ORDER_OP_DETAIL_FORM" property="requestPoNum" value="<%=thisPoNum%>"/>
<% } %>
</td>
        </tr>
        <tr>
<td class="text"><b><app:storeMessage key="shop.orderStatus.text.method"/></b></td>
<td><bean:write name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.orderDetail.orderSourceCd"/></td>
        </tr>
<% if(RefCodeNames.ORDER_STATUS_CD.PENDING_DATE.equals(orderStatus)) {%>
<tr>
<td class="text"><b><app:storeMessage key="shop.orderStatus.text.processOrderOn"/></b></td>
<td>
<%
// --date convertation should be reviewed later (YK)
 String processOnDateS = theForm.getOrderStatusDetail().getPendingDate();
SimpleDateFormat defaultFormat = new SimpleDateFormat("MM/dd/yyyy");
Date defaultDate = defaultFormat.parse(processOnDateS);

String pattern = ClwI18nUtil.getDatePattern(request);
SimpleDateFormat sdfm = new SimpleDateFormat(pattern);

 java.util.Date processOnDate = null;
 if(processOnDateS!=null && processOnDateS.trim().length()>0) {

   processOnDate = sdfm.parse(sdfm.format(defaultDate));
   
   String formattedProcessOnDate = ClwI18nUtil.formatDateInp(request, processOnDate, timeZone.getID() );
%>
 <%=formattedProcessOnDate%>
<% } %>
</td>
</tr>
<% } %>
</table>

</td><td>

                <table align=left>
                    <tr>
                        <td class="text">
                            <b><app:storeMessage key="shop.orderStatus.text.contactName"/></b>
                        </td>
                        <td colspan="2">
                            <bean:write name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.orderDetail.orderContactName"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="text">
                            <b><app:storeMessage key="shop.orderStatus.text.contactPhone"/></b>
                        </td>
                        <td colspan="2">
                            <bean:write name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.orderDetail.orderContactPhoneNum"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="text">
                            <b><app:storeMessage key="shop.orderStatus.text.contactEmail"/></b>
                        </td>
                        <td  colspan="2">
                            <bean:write name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.orderDetail.orderContactEmail"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="text">
                            <b><app:storeMessage key="shop.orderStatus.text.placedBy"/></b>
                        </td>
                        <td  colspan="2">
                            <bean:write name="ORDER_OP_DETAIL_FORM" property="orderPlacedBy"/>
                            (<bean:write name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.orderDetail.addBy"/>)
                        <td>
                    </tr>
<%
String orderContactName = theForm.getOrderStatusDetail().getOrderDetail().getOrderContactName();
if (orderContactName != null && orderContactName.length() > 0 &&
    !orderContactName.equalsIgnoreCase("NA NA")) {
      String userFirstName = theForm.getOrderStatusDetail().getOrderDetail().getUserFirstName();
      String userLastName = theForm.getOrderStatusDetail().getOrderDetail().getUserLastName();
      String userFullName = "";
      if (userFirstName != null && userLastName != null) {
        userFullName = userFirstName + " " + userLastName;
      }
      if (!userFullName.equals(orderContactName)) {
%>
        <tr>
            <td class="text">
                <b><app:storeMessage key="shop.orderStatus.text.requestedBy"/></b>
            </td>
            <td  colspan="2">
                <bean:write name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.orderDetail.orderContactName"/>
            </td>
        </tr>
<%    } %>
<%} %>

<% if (appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.WORK_ORDER_APPROVER) ||
         appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.ASSET_USER) ||
         appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR)) {%>
    <tr>
        <td class="text">
            <b><app:storeMessage key="userWorkOrder.text.workOrderNumber"/></b>
        </td>
        <td>
            <html:text name="ORDER_OP_DETAIL_FORM" property="workOrderNumber" size="20" maxlength="22" />
        <td>
        <td>
            <html:button property="action" styleClass="store_fb"
                onclick="actionSubmit('BBBBBBB', 'button.bindToWorkOrder');" >
                <app:storeMessage key="global.action.label.save"/>
            </html:button>
        </td>
    </tr>
<%}%>
                    
</table>

</td>
</tr>


<tr>
<td class="text" colspan=2><b><app:storeMessage key="shop.orderStatus.text.comments:"/> </b>
<bean:write name="ORDER_OP_DETAIL_FORM"
 property="orderStatusDetail.orderDetail.comments"/></td>
</tr>

<bean:define id="opDV " type="com.cleanwise.service.api.value.OrderPropertyDataVector"
   name="ORDER_OP_DETAIL_FORM" property="orderPropertyList"/>
<% //Workflow properties
IdVector canApproveIdV = new IdVector();

if(opDV.size()>0) {
  canApproveIdV = (IdVector) session.getAttribute("note.approve.ids");
%>
<tr><td colspan="3" class="text"><b><app:storeMessage key="shop.orderStatus.text.notes:"/></b></td><tr>
<%

for(int ii=0; ii<opDV.size(); ii++) {
  OrderPropertyData opD = (OrderPropertyData) opDV.get(ii);
  if("Workflow Note".equals(opD.getShortDesc())){
%>
<tr><td colspan="3">
<%   String messKey = opD.getMessageKey();
     if(messKey==null){ %>
       <%=opD.getValue()%>
 <%  } else { %>
      <app:storeMessage key="<%=messKey%>"
          arg0="<%=opD.getArg0()%>"
          arg0TypeCd="<%=opD.getArg0TypeCd()%>"
          arg1="<%=opD.getArg1()%>"
          arg1TypeCd="<%=opD.getArg1TypeCd()%>"
          arg2="<%=opD.getArg2()%>"
          arg2TypeCd="<%=opD.getArg2TypeCd()%>"
          arg3="<%=opD.getArg3()%>"
          arg3TypeCd="<%=opD.getArg3TypeCd()%>"
          />
  <% } %>
  <%
    if(opD.getApproveDate()!=null) {
        Date appDate = opD.getApproveDate();
        String formattedAppDate = ClwI18nUtil.formatDateInp(request, appDate, timeZone.getID() );
  %>
    (<app:storeMessage key="shop.orderStatus.text.clearedOnBy"
     arg0="<%=formattedAppDate%>"
     arg1="<%=opD.getModBy()%>"/>)
<% } else {
        Integer opIdI = new Integer(opD.getOrderPropertyId());
        if(canApproveIdV.contains(opIdI)) {%>
<input type="checkbox" name='<%="approveNote"+opIdI%>' value='<%=opIdI.toString()%>' checked />
<%      } else {
            if(approverFl || !canApproveIdV.isEmpty()) {%>
<input type="checkbox" name='<%="wn"+opIdI%>' value='<%=opIdI.toString()%>' disabled />
<%          }
        }
   }
}
}
} %>
</td></tr>
<%//END Workflow properties%>

<%

String accountName = "";

/* Handle orders that have been canceled. */

if ( theForm.getOrderStatusDetail().getBillTo() != null &&
     theForm.getOrderStatusDetail().getBillTo().getShortDesc() != null ) {
  accountName = theForm.getOrderStatusDetail().getBillTo().getShortDesc();
}

if ( null == accountName ) accountName = "";

%>

<logic:present name="ORDER_OP_DETAIL_FORM" property="customerOrderNotes">
<tr><td class="text" colspan="2">
<table>

          <bean:size id="custNotesCt" name="ORDER_OP_DETAIL_FORM" property="customerOrderNotes"/>
          <logic:greaterThan name="custNotesCt" value="0">
          <tr>
             <td class="text" colspan="2"><b><app:storeMessage key="label.display.customer.order.notes"
             arg0="<%=accountName%>"/>:</b></td>
           </tr>
        <logic:iterate name="ORDER_OP_DETAIL_FORM" property="customerOrderNotes" id="note" type="com.cleanwise.service.api.value.OrderPropertyData">
          <tr>
<td class="text" width="150"
 style="vertical-align:top; padding-left:20; white-space: nowrap;">

<bean:define id="commentDate" name="note" property="addDate"
  type="java.util.Date" />
<b>
<%-- <i18n:formatDate value="<%=commentDate%>"  pattern="yyyy-MM-dd k:mm"/>--%>
<%
	Date comDate = commentDate;
	String formattedComDate = ClwI18nUtil.formatDateInp(request, comDate, timeZone.getID() );
%>	
<%=formattedComDate%> -
<bean:write name="note" property="addBy"/>
 </b>
</td>
<td style="text-align:left;">
<app:writeHTML name="note" property="value"/>
</td>

        </logic:iterate>
        </logic:greaterThan>
</table>
</td></tr>
</logic:present>

<%if ( appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.ADD_CUSTOMER_ORDER_NOTES)){%>
  <tr>
    <td colspan="2" class="text"><b><app:storeMessage key="label.customer.order.notes" arg0="<%=accountName%>" />:</b><br>
      <html:textarea name="ORDER_OP_DETAIL_FORM" property="customerComment" rows="3" cols="55"/>
      <html:button property="action" styleClass="store_fb"
          onclick="actionSubmit('BBBBBBB', 'button.addCustomerComment');" >
          <app:storeMessage key="button.addCustomerComment"/>
      </html:button>
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

<tr>
<%int colCount=0;%>
<logic:equal name="ORDER_OP_DETAIL_FORM" property="orderHasInventoryItem"
  value="true">
<td class="shopcharthead">&nbsp;</td><%colCount++;%>
<td class="shopcharthead"><app:storeMessage key="shoppingItems.text.par"/></td><%colCount++;%>
<td class="shopcharthead"><app:storeMessage key="shoppingItems.text.onHand"/></td><%colCount++;%>
</logic:equal>
<td class="shopcharthead"><div><app:storeMessage key="shoppingItems.text.ourSkuNum"/></td><%colCount++;%>
<td class="shopcharthead"><div><app:storeMessage key="shoppingItems.text.productName"/></div></td><%colCount++;%>
<td class="shopcharthead"><div><app:storeMessage key="shoppingItems.text.size"/></div></td><%colCount++;%>
<td class="shopcharthead"><div><app:storeMessage key="shoppingItems.text.pack"/></div></td><%colCount++;%>
<td class="shopcharthead"><div><app:storeMessage key="shoppingItems.text.uom"/></div></td><%colCount++;%>
<% if (!appUser.getUserAccount().isHideItemMfg()) { %>
<td class="shopcharthead"><div><app:storeMessage key="shoppingItems.text.manufacturer"/></div></td><%colCount++;%>
<td class="shopcharthead"><div><app:storeMessage key="shoppingItems.text.mfgSkuNum"/></div></td><%colCount++;%>
<% } %>
<logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">
  <td class="shopcharthead"><div><app:storeMessage key="shoppingItems.text.price"/></div></td><%colCount++;%>
</logic:equal>
<td class="shopcharthead"><div><app:storeMessage key="shoppingItems.text.qty"/></td><%colCount++;%>
<logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">
  <td class="shopcharthead"><div><app:storeMessage key="shoppingItems.text.extendedPrice"/></td><%colCount++;%>
</logic:equal>
<td class="shopcharthead"><div><app:storeMessage key="shoppingItems.text.status"/></div></td><%colCount++;%>

<%
boolean resaleItemsAllowed = ShopTool.isResaleItemsAllowed(request);
if(resaleItemsAllowed){%>
<td class="shopcharthead"><div><app:storeMessage key="label.display.item.tax.exempt"/></td><%colCount++;%>
<%}%>
<app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.RECEIVING%>">
<td class="shopcharthead"><div><app:storeMessage key="shoppingItems.text.received"/></div></td><%colCount++;%>
</app:authorizedForFunction>
<% if (appUser.getUserAccount().isShowSPL())  {%>
<td class="shopcharthead"><div><app:storeMessage key="shoppingItems.text.spl"/></div></td><%colCount++;%>
<% } %>
</tr>
<%String lastErpPoNum = null;%>
<logic:iterate id="item" name="ORDER_OP_DETAIL_FORM" property="orderItemDescList"
            offset="0" indexId="oidx" type="com.cleanwise.service.api.value.OrderItemDescData">
<bean:define id="qty" name="item" property="orderItem.totalQuantityOrdered" type="Integer"/>

<%java.math.BigDecimal custItemPrice=new BigDecimal(0.00);%>
<logic:present  name="item" property="orderItem.custContractPrice">
  <%custItemPrice=item.getOrderItem().getCustContractPrice();%>
</logic:present>

<%--Display the distributor and the purchase order number if the user did not enter a po number for this order, it is a distributor store type,
and the po number has been created--%>

<%if(!useCustPo){%>
  <logic:equal name="<%=Constants.APP_USER%>" property="userStore.storeType.value" value="<%=RefCodeNames.STORE_TYPE_CD.DISTRIBUTOR%>">
  <%
  if(item.getOrderItem().getErpPoNum()!= null && !item.getOrderItem().getErpPoNum().equals(lastErpPoNum)){ //display po header text
    lastErpPoNum = item.getOrderItem().getErpPoNum();
  %>
     <tr bgcolor="<%=ClwCustomizer.getEvenRowColor(session,(Integer)oidx)%>">
      <td colspan="4">
        <b><app:storeMessage key="shop.orderStatus.text.distributor"/></b>
           <bean:write name="item" property="distRuntimeDisplayName"/>
      </td>
      <td colspan="4">
        <b><app:storeMessage key="shop.orderStatus.text.purchaseOrder"/></b>
           <bean:write name="item" property="orderItem.erpPoNum"/>
      </td>
      <td colspan="<%=colCount-8%>">
	  
	  <table>
			<logic:present name="item" property="orderFreight">
			<tr>
			   <logic:present name="item" property="orderFreight.shortDesc">
			    <b><app:storeMessage key="shop.orderStatus.text.freightOption"/>:</b>
				<bean:write name="item" property="orderFreight.shortDesc"/>
			   </logic:present>
			   <logic:present name="item" property="orderFreight.amount">
				<bean:define id="amount"  name="item" property="orderFreight.amount"/>
				<td><%=clwI18n.priceFormat(amount,"&nbsp;")%></td>
			   </logic:present>
			</tr>
			</logic:present>
			<logic:present name="item" property="orderDiscount">
			<tr>
			    <td><b><bean:write name="item" property="orderDiscount.shortDesc"/>:</b></td>
				<bean:define id="amount"  name="item" property="orderDiscount.amount"/>
				<td><%=clwI18n.priceFormat(amount,"&nbsp;")%></td>
			</tr>
			</logic:present>
        
			<logic:present name="item" property="orderFuelSurcharge">
			<tr>
			    <logic:present name="item" property="orderFuelSurcharge.shortDesc">
                  <td><b><bean:write name="item" property="orderFuelSurcharge.shortDesc"/>:</b></td>
                </logic:present> 
                <logic:present name="item" property="orderFuelSurcharge.amount"> 
				  <bean:define id="amount"  name="item" property="orderFuelSurcharge.amount"/>
				  <td><%=clwI18n.priceFormat(amount,"&nbsp;")%></td>
			    </logic:present>  
			</tr>
			</logic:present>

			<logic:present name="item" property="orderSmallOrderFee">
			<tr>
			   <logic:present name="item" property="orderSmallOrderFee.shortDesc">
				<td><b><bean:write name="item" property="orderSmallOrderFee.shortDesc"/>:</b></td>
			   </logic:present>
			   <logic:present name="item" property="orderSmallOrderFee.amount">
				<bean:define id="amount"  name="item" property="orderSmallOrderFee.amount"/>
				<td><%=clwI18n.priceFormat(amount,"&nbsp;")%></td>
			   </logic:present>
			</tr>
			</logic:present>
					
		</table>
      </td>
    </tr>
  <%}//end deisplay po header text%>
  </logic:equal>
<%}%>
<%--Display the distributor and the purchase order number--%>

<tr bgcolor="<%=ClwCustomizer.getEvenRowColor(session,(Integer)oidx)%>">
<% int colqty = 0; %>
<logic:equal name="ORDER_OP_DETAIL_FORM" property="orderHasInventoryItem" value="true">
<logic:equal name="item" property="isAnInventoryItem" value="true">
<td >
<% colqty++; %>
<span class="inv_item">
i
<% if (ShopTool.getCurrentSite(request).isAnInventoryAutoOrderItem
  (item.getOrderItem().getItemId())) { %>
a
<% } %>
</span>
</td>
<% colqty++; %>
<td><bean:write name="item" property="orderItem.inventoryParValue"/></td>
<% colqty++; %>
<td><bean:write name="item" property="orderItem.inventoryQtyOnHand"/></td>
</logic:equal>

<logic:equal name="item" property="isAnInventoryItem" value="false">
<% colqty++; %>
<td>&nbsp;</td>
<% colqty++; %>
<td>&nbsp;</td>
<% colqty++; %>
<td>&nbsp;</td>
</logic:equal>

</logic:equal>


<%--//sku customization--%>
<% colqty++; %>
        <td><%=ShopTool.getRuntimeSku(item.getOrderItem(),request)%></td>
<% colqty++; %>
        <td><bean:write name="item" property="orderItem.itemShortDesc"/></td>
<% colqty++; %>
        <td><bean:write name="item" property="orderItem.itemSize"/></td>
<% colqty++; %>
        <td><bean:write name="item" property="orderItem.itemPack"/></td>
<% colqty++; %>
        <td><bean:write name="item" property="orderItem.itemUom"/></td>
<% if (!appUser.getUserAccount().isHideItemMfg()) { %>
<% colqty++; %>
        <td><bean:write name="item" property="orderItem.manuItemShortDesc"/></td>
<% colqty++; %>
        <td><bean:write name="item" property="orderItem.manuItemSkuNum"/></td>
<% } %>
<logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">
<% colqty++; %>
<td align="right">
  <%=clwI18n.priceFormat(custItemPrice,"&nbsp;")%>
</td>
</logic:equal>
<% colqty++; %>
<td  align="center">
  <bean:write name="item" property="orderItem.totalQuantityOrdered"/>
</td>
<logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">
<% colqty++; %>
<td align="right">
  <%=clwI18n.priceFormat(custItemPrice.multiply(new BigDecimal(qty.doubleValue())),"&nbsp;")%>
</td>
</logic:equal>
<% colqty++; %>
      <td>
   <% if(RefCodeNames.ORDER_ITEM_STATUS_CD.CANCELLED.
                         equals(item.getOrderItem().getOrderItemStatusCd())) { %>
    <app:storeMessage key="shoppingItems.text.cancelled"/>
   <% } else { %>
       &nbsp;
   <% } %>
     </td>

 <%if(resaleItemsAllowed){%>
  <% colqty++; %>
    <td>
      <logic:equal name="item" property="orderItem.saleTypeCd" value="<%=RefCodeNames.ITEM_SALE_TYPE_CD.RE_SALE%>">
        &nbsp;<app:storeMessage key="shoppingItems.text.y"/>
      </logic:equal>
      <logic:notEqual name="item" property="orderItem.saleTypeCd" value="<%=RefCodeNames.ITEM_SALE_TYPE_CD.RE_SALE%>">
        &nbsp;<app:storeMessage key="shoppingItems.text.n"/>
      </logic:notEqual>
    </td>
 <%}%>
 <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.RECEIVING%>">
     <%String prop = "orderItemDescList["+oidx+"].itemQuantityRecvdS"; %>
   <td><html:text name="ORDER_OP_DETAIL_FORM" size="3" property="<%=prop%>"/></td>
 </app:authorizedForFunction>

<% if (appUser.getUserAccount().isShowSPL())  {%>
<% colqty++; %>
<td align="center">
  <%if(Utility.isTrue(item.getStandardProductList())){%>
      <app:storeMessage key="shoppingItems.text.y"/>
  <%}else{%>
      <app:storeMessage key="shoppingItems.text.n"/>
  <%}%>
</td>
<% } %>
</tr>
<%
  if(theForm.getShowDistNoteFl()) {
  OrderPropertyData distNote = item.getDistPoNote();
  if(distNote!=null && Utility.isSet(distNote.getValue())) {
%>
<tr bgcolor="<%=ClwCustomizer.getEvenRowColor(session,(Integer)oidx)%>">
 <td colspan='<%=colqty%>'><i>Dist. Note:</i>&nbsp;<%=distNote.getValue()%> </td>
</tr>
<% }} %>
        <logic:present name="item" property="orderItemActionList">
        <%
          int shippedQty = 0;
          boolean substitutedFlag = false;
          boolean shippedFlag = false;
        %>

        <logic:iterate id="action" name="item" property="orderItemActionList"
                offset="0" indexId="aidx"
                type="com.cleanwise.service.api.value.OrderItemActionData">

        <%
          Integer actionqty = new Integer(action.getQuantity());
    String actioncd = action.getActionCd();
 //   java.util.Date actiondate = action.getActionDate();
    java.util.Date actiondate = Utility.getDateTime(action.getActionDate(), action.getActionTime()) ;

    if(actiondate == null){
      actiondate = action.getAddDate();
    }
                if (null == actioncd) {
                        actioncd = new String("");
                }
                if (RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.SUBSTITUTED.equals(actioncd) || null == actionqty) {
                        substitutedFlag = true;
                }
                else if (RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.CUST_SHIPPED.equals(actioncd)) {
                        shippedQty += actionqty.intValue();
                        shippedFlag = true;
                }
        %>
<app:shouldDisplayOrderItemAction name="<%=actioncd%>">
<logic:greaterThan value="0" name="action" property="quantity">
  <%if (RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.CUST_INVOICED.equals(actioncd)) {
             actioncd = "Invoiced";
        }%>
 <tr bgcolor="<%=ClwCustomizer.getEvenRowColor(session,(Integer)oidx)%>">
  <logic:equal name="ORDER_OP_DETAIL_FORM" property="orderHasInventoryItem" value="true">
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </logic:equal>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">
        <td>&nbsp;</td>
        </logic:equal>
        <td align='center'><bean:write name="action" property="quantity"/></td>
        <logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">
        <td>&nbsp;</td>
        </logic:equal>
        <td><%=actioncd%>
        <%if(actiondate != null){
		
		String formattedActionDate = ClwI18nUtil.formatDateInp(request, actiondate, timeZone.getID() );
		%>
                (<%= formattedActionDate%>)
        <%}%>
        </td>
    <%if(resaleItemsAllowed){%><td></td><%}%>
    <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.RECEIVING%>"><td>&nbsp;</td>
    </app:authorizedForFunction>
    <% if (appUser.getUserAccount().isShowSPL())  {%><td>&nbsp;</td><%}%>
</tr>
</logic:greaterThan>
</app:shouldDisplayOrderItemAction>
        </logic:iterate>

<%
        int backorderedQty = 0;
        if (true == shippedFlag && false == substitutedFlag) {
                backorderedQty = qty.intValue() - shippedQty;
                if ( 0 < backorderedQty ) {
%>
 <tr bgcolor="<%=ClwCustomizer.getEvenRowColor(session,(Integer)oidx)%>">
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">
        <td>&nbsp;</td>
        </logic:equal>
        <td><%=backorderedQty%></td>
        <logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">
        <td>&nbsp;</td>
        </logic:equal>
        <td><app:storeMessage key="shoppingItems.text.backordered"/></td>
    <%if(resaleItemsAllowed){%><td></td><%}%>
    <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.RECEIVING%>"><td>&nbsp;</td>
    </app:authorizedForFunction>
    <% if (appUser.getUserAccount().isShowSPL())  {%><td>&nbsp;</td><%}%>
</tr>
<%     }
        }
%>
        </logic:present>
<%
int colspan1 = colqty - 4;  
if (appUser.getUserAccount().isShowSPL() == true) {
    colspan1--;
}
if (appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.RECEIVING) == true) {
    colspan1--;
}
%>
      <logic:iterate id="shoppingEntry" name="item" property="shoppingHistory"
   type="com.cleanwise.service.api.value.ShoppingInfoData">
   <tr>
   <td class="rep_line">&nbsp;</td>
   <td colspan="<%=colspan1%>" class="rep_line">
  <%
  String messKey = shoppingEntry.getMessageKey();
  if(messKey==null){ %>
   <bean:write name="shoppingEntry" property="value"/>
  <% } else { %>
   <app:storeMessage key="<%=messKey%>"
    arg0="<%=shoppingEntry.getArg0()%>" arg1="<%=shoppingEntry.getArg1()%>"
    arg2="<%=shoppingEntry.getArg2()%>" arg3="<%=shoppingEntry.getArg3()%>" />

  <% } %>
  </td>
 <td colspan="3" class="rep_line"><%
	String formattedAddDate = ClwI18nUtil.formatDateInp(request, shoppingEntry.getAddDate(), timeZone.getID() );
 %><%=formattedAddDate%></td>
   <td class="rep_line"><bean:write name="shoppingEntry" property="addBy"/></td>
   <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.RECEIVING%>"><td class="rep_line">&nbsp;</td>
   </app:authorizedForFunction>
   <% if (appUser.getUserAccount().isShowSPL())  {%><td class="rep_line">&nbsp;</td><%}%>
   </tr>
    </logic:iterate>

  </logic:iterate>

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
<table align=center>
<% /* Order summary data. */ %>
<% boolean freightHandFl = false; %>
<logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">
<tr>
<td class="smalltext"><b><app:storeMessage key="shop.orderStatus.text.subtotal:"/></b></td>
<td align=right>
<bean:define id="subtotal"  name="ORDER_OP_DETAIL_FORM" property="subTotal"/>
<%=clwI18n.priceFormat(subtotal,"&nbsp;")%>
</td>
</tr>
<!-- Added for Discount: begin  -->
<!-- cartDiscount -->
<% BigDecimal discountAmt = theForm.getDiscountAmt();// I have to get the discount %>
<% if (discountAmt != null && discountAmt.compareTo(new BigDecimal(0))!=0 ) { %>
<tr>
<% if (discountAmt.compareTo(new BigDecimal(0)) >0 ) {    	   
	  discountAmt = discountAmt.negate(); 
} 
discountAmt = discountAmt.setScale(2, BigDecimal.ROUND_HALF_UP);
%>
<td class="smalltext"><b><html>Discount:</html></b></td>
<td align="right"><%=ClwI18nUtil.getPriceShopping(request,discountAmt,"&nbsp;")%></td>
<td> &nbsp;&nbsp;</td>
</tr>
<% } %> 
<!-- Discount: End  -->
<bean:define id="freightcost"  name="ORDER_OP_DETAIL_FORM"
  property="totalFreightCost" type="BigDecimal"/>
<% if (freightcost!=null && 0.001 < java.lang.Math.abs(freightcost.doubleValue()) &&
       consolidatedToOrderD==null) {
  freightHandFl = true;
%>
<tr>
<td class="smalltext"><b><app:storeMessage key="shop.orderStatus.text.freight:"/></b></td>
<td  align=right>
<% if(!toBeConsolidatedFl) { %>
  <%=clwI18n.priceFormat(freightcost,"&nbsp;")%>
<% } else { %>
  *
<% } %>
</td>
</tr>
<% }  %>

<%--
<% if ( theForm.getRushOrderCharge() != null ) { %>
<bean:define id="rushCharge"  name="ORDER_OP_DETAIL_FORM"
  property="rushOrderCharge" type="BigDecimal"/>
<% if (rushCharge!=null && consolidatedToOrderD==null) { %>
<tr>
<td class="smalltext"><b><app:storeMessage key="shop.orderStatus.text.rushOrderCharge:"/></b></td>
<td  align=right>
<% if(!toBeConsolidatedFl) { %>
  <%=clwI18n.priceFormat(rushCharge,"&nbsp;")%>
<% } else { %>
  *
<% } %>
</td>
</tr>
<% }  %>
<% }  /* rush order charge block */ %>--%>

<!-- the misc cost-->
<% if ( theForm.getTotalMiscCost() != null ) { %>
  <bean:define id="misc"  name="ORDER_OP_DETAIL_FORM" property="totalMiscCost" type="BigDecimal"/>
  <% if (misc!=null  && 0.001 < java.lang.Math.abs(misc.doubleValue()) &&
         consolidatedToOrderD==null) {
    freightHandFl = true;
  %>
  <tr>
  <td class="text"><b><app:storeMessage key="shop.orderStatus.text.handling:"/><b></td>
  <td align=right>
  <% if(!toBeConsolidatedFl) { %>
    <%=clwI18n.priceFormat(misc,"&nbsp;")%>
  <% } else { %>
    *
  <% } %>
  </td>
  </tr>
  <% }  %>
<% }  %>

<logic:present name="ORDER_OP_DETAIL_FORM" property="smallOrderFeeAmt">
    <bean:define id="smallOrderFee" name="ORDER_OP_DETAIL_FORM" property="smallOrderFeeAmt"
                 type="java.math.BigDecimal"/>
    <% if (smallOrderFee != null && 0.001 < java.lang.Math.abs(smallOrderFee.doubleValue())) { %>
    <tr>
        <td class="smalltext"><b>
            <app:storeMessage key="shop.orderStatus.text.smallOrderFee:"/>
        </b></td>
        <td align=right>
            <%=clwI18n.priceFormat(smallOrderFee, "&nbsp;")%>
        </td>
    </tr>
    <% } %>
</logic:present>


<logic:present name="ORDER_OP_DETAIL_FORM" property="fuelSurchargeAmt">
    <bean:define id="fuelSurcharge" name="ORDER_OP_DETAIL_FORM" property="fuelSurchargeAmt"
                 type="java.math.BigDecimal"/>
    <% if (fuelSurcharge != null && 0.001 < java.lang.Math.abs(fuelSurcharge.doubleValue())) { %>
    <tr>
        <td class="smalltext"><b>
            <app:storeMessage key="shop.orderStatus.text.fuelSurcharge:"/>
        </b></td>
        <td align=right>
            <%=clwI18n.priceFormat(fuelSurcharge, "&nbsp;")%>
        </td>
    </tr>
    <% } %>
</logic:present>

<!-- the tax cost-->
<logic:present name="ORDER_OP_DETAIL_FORM" property="totalTaxCost">
  <bean:define id="tax"  name="ORDER_OP_DETAIL_FORM" property="totalTaxCost" type="Double"/>
  <% if (tax!=null && 0.001 < java.lang.Math.abs(tax.doubleValue())) { %>
  <tr>
  <td class="smalltext"><b><app:storeMessage key="shop.orderStatus.text.tax:"/></b></td>
  <td  align=right>
    <%=clwI18n.priceFormat(tax,"&nbsp;")%>
  </td>
  </tr>
  <% }  %>
</logic:present>
<% if(consolidatedToOrderD==null && (!toBeConsolidatedFl || !freightHandFl)) { %>
<tr>
<td class="smalltext"><b><app:storeMessage key="shop.orderStatus.text.totalExcludingVOC:"/></b></td>
<td  align=right>
<bean:define id="total"  name="ORDER_OP_DETAIL_FORM" property="totalAmount"/>
   <%=clwI18n.priceFormat(total,"&nbsp;")%>
</td>
</tr>
<% }  %>
</logic:equal>
<tr>
<td class="smalltext"><b><app:storeMessage key="shop.orderStatus.text.orderStatus:"/></b></td>
<td> <%=com.cleanwise.view.utils.ShopTool.xlateStatus(orderStatusDetail)%>
<%if(RefCodeNames.ORDER_STATUS_CD.CANCELLED.equals(orderStatus) &&
                orderStatusDetail.getConsolidatedOrder()!=null) { %>
 <app:storeMessage key="shop.orderStatus.text.consolidated"/>
<% } %>
</td>
</tr>
<%
  boolean pendingFl =
      (orderStatus.equals(RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL) ||
              orderStatus.equals(RefCodeNames.ORDER_STATUS_CD.PENDING_CONSOLIDATION) ||
              orderStatus.equals(RefCodeNames.ORDER_STATUS_CD.PENDING_DATE)) ? true : false;
  if (pendingFl) {
      if (approverFl || canApproveIdV.isEmpty() == false) { 
  // Show the buttons to approve, reject, and modify
  // the order.
%>
<tr><td colspan=2>
<%
String rejectloc = "javascript:window.location='orderDetail.do?action=reject&orderId=" + lOrderId + "' ";
String poNumStr = "";
  if(useCustPo) {
    poNumStr = "&requestPoNum='+encodeURIComponent('"+requestPoNum+"')+'";
  }
String approveloc = "javascript:window.location='orderDetail.do?action=Approve&orderId=" + lOrderId +
        poNumStr +"&approveAll=true'";
String modifyloc = "javascript:window.location='orderDetail.do?action=modify&orderId=" + lOrderId + "' ";
%>
<html:button styleClass="store_fb" property="action" onclick='<%=rejectloc%>'>
  <app:storeMessage key="global.action.label.reject" />
</html:button>
<% if(!toBeConsolidatedFl) { %>
<html:button styleClass="store_fb" property="action" onclick="actionMultiSubmit('BBBBBBB', 'Approve');" >
	<app:storeMessage key="global.action.label.approve" />
</html:button>
<% } %>
<%if(!consolidatedOrderFl){ %>
<html:button styleClass="store_fb" property="action" onclick='<%=modifyloc%>'>
  <app:storeMessage key="shop.orderStatus.text.modify" />
</html:button>
<% } %>
<% if(!toBeConsolidatedFl) {
    //String currDate = ClwI18nUtil.formatDateInp(request,new java.util.Date());
    String currDate = ClwI18nUtil.formatDateInp(request, new java.util.Date(), timeZone.getID() );
%>
<br><html:button styleClass="store_fb" property="action"
  onclick="actionMultiSubmit('BBBBBBB', 'ApproveOn');" >
<app:storeMessage key="shop.orderStatus.text.approveOn" />
</html:button>&nbsp;<input type="text" name='approveDate' value = '<%=currDate%>' size='10'/><br>
(<%=ClwI18nUtil.getUIDateFormat(request)%>)<br>
<% } %>
<% if(consolidatedOrderFl) {
  String deconsolidateloc = "javascript:window.location='orderDetail.do?action=deconsolidate&orderId=" + lOrderId + "' ";
%>
<html:button styleClass="store_fb" property="action"  onclick='<%=deconsolidateloc%>'>
  <app:storeMessage key="shop.orderStatus.text.undoConsolidation" />
</html:button>
<% } %>
<% } %>
<% if(toBeConsolidatedFl && warehouseFl) {
String consolidateloc = "javascript:window.location='orderDetail.do?action=consolidate&orderId=" + lOrderId + "' ";
%>
<html:button styleClass="store_fb" property="action"  onclick='<%=consolidateloc%>'>
  <app:storeMessage key="shop.orderStatus.text.consolidate" />
</html:button>
<% } %>
<% if(currSiteD.getSiteId()!=siteId) {
String gotoorderloc = "javascript:window.location='orderDetail.do?action=goToOrderLocation&orderId=" + lOrderId + "' ";
%>
<html:button styleClass="store_fb" property="action"  onclick='<%=gotoorderloc%>'>
  <app:storeMessage key="shop.orderStatus.text.goToOrderLocation" />
</html:button>
<% } %>
</td></tr>
<% } %>

<logic:equal name="ApplicationUser" property="allowPurchase" value="true" >

<%
  if (
    orderStatus.equals(RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL) == false
    && !consolidatedOrderFl
  ) {
  // Show the buttons reorder button.
  String reorderloc = "javascript:window.location='orderDetail.do?action=reorder&orderId=" + lOrderId + "' ";
%>
<tr><td colspan=2>
<html:button styleClass="store_fb" property="action" onclick='<%=reorderloc%>'>
   <app:storeMessage key="global.action.label.reorder" />
</html:button>
<app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.RECEIVING%>">
  <html:button styleClass="store_fb" property="action"
  onclick="actionSubmit('BBBBBBB', 'button.update.receiving');" >
  <app:storeMessage key="button.update.receiving"/>
  </html:button>
</app:authorizedForFunction>
</td></tr>
<% } %>
</logic:equal>

</table> <% /* Order summary data. */ %>

</td>

<td>

<table align=center> <% /* Ship to data. */ %>

<tr valign=top>
<td class="smalltext"><b><app:storeMessage key="shop.orderStatus.text.address:"/></b></td>
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
<% if (appUser.getUserStore().isStateProvinceRequired()) { %>
<br>
<bean:write name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.shipTo.stateProvinceCd"/>
<%} %>
<br>
<bean:write name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.shipTo.postalCode"/>
<br>
<bean:write name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.shipTo.countryCd"/>
</td>
</tr>
</table> <% /* Ship to data. */ %>

</td>

<td >
<table  align=center> <% /* Account bill to information. */ %>
<% if (theForm.getOrderStatusDetail().hasCreditCard()) { %>

<tr>
 <td class="text" align="left"> <b><app:storeMessage key="shop.orderStatus.text.cartHolder:"/></b>  </td>
<td>
<bean:write name="ORDER_OP_DETAIL_FORM"
property="orderStatusDetail.orderCreditCardData.name"/>
</td>
</tr><tr>
  <td class="text" align="left">
  <b><app:storeMessage key="shop.orderStatus.text.cardNumber:"/></b>
 <br><app:storeMessage key="shop.orderStatus.text.last4digits"/></td>
<td>
<bean:write name="ORDER_OP_DETAIL_FORM"
property="orderStatusDetail.orderCreditCardData.creditCardNumberDisplay"/>
</td>
</tr><tr>
<td class="text"> <b><app:storeMessage key="shop.orderStatus.text.cardType:"/></b>      </td>
<td>
<bean:write name="ORDER_OP_DETAIL_FORM"
property="orderStatusDetail.orderCreditCardData.creditCardType"/>
</td>
</tr><tr>
<td rowspan=7 valign=top class="text">
<b><app:storeMessage key="shop.orderStatus.text.cardAddress:"/></b></td>
<td>
<bean:write name="ORDER_OP_DETAIL_FORM"
property="orderStatusDetail.orderCreditCardData.address1"/>
</td>
</tr><tr>
<td>
<bean:write name="ORDER_OP_DETAIL_FORM"
property="orderStatusDetail.orderCreditCardData.address2"/>
</td>
</tr><tr>
<td>
<bean:write name="ORDER_OP_DETAIL_FORM"
property="orderStatusDetail.orderCreditCardData.address3"/>
</td>
</tr><tr>
<td>
<bean:write name="ORDER_OP_DETAIL_FORM"
property="orderStatusDetail.orderCreditCardData.address4"/>
</td>
</tr><tr>
<td>
<bean:write name="ORDER_OP_DETAIL_FORM"
property="orderStatusDetail.orderCreditCardData.city"/>
</td>
</tr>

<% if (appUser.getUserStore().isStateProvinceRequired()) { %>
<tr>
<td>
<bean:write name="ORDER_OP_DETAIL_FORM"
property="orderStatusDetail.orderCreditCardData.stateProvinceCd"/>
</td>
</tr>
<%} %>

<tr>
<td>
<bean:write name="ORDER_OP_DETAIL_FORM"
property="orderStatusDetail.orderCreditCardData.postalCode"/>
</td>
</tr><tr>
<td>
<bean:write name="ORDER_OP_DETAIL_FORM"
property="orderStatusDetail.orderCreditCardData.countryCd"/>
</td>
</tr>

<% } else { %>
<tr valign=top>
<td class="smalltext"><b><app:storeMessage key="shop.orderStatus.text.address:"/></b></td>
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
<% if (appUser.getUserStore().isStateProvinceRequired()) { %>
<br>
<bean:write name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.billTo.stateProvinceCd"/>
<%} %>
<br>
<bean:write name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.billTo.postalCode"/>
<br>
<bean:write name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.billTo.countryCd"/>
</td>
</tr>
<% } %>
</table>

</td>
</tr>

<tr>
<td colspan=3 class="redwarning"><br>&nbsp;<app:storeMessage key="shop.message.vocNotIncluded"/><br>&nbsp;</td>
</tr>

<tr>
<td colspan=3 class="tableoutline"><img src="/images/cw_spacer.gif" height="1"></td>
</tr>


<% if(consolidatedToOrderD==null && toBeConsolidatedFl && freightHandFl) { %>
<tr>
<td colspan=3>*
 <app:storeMessage key="shop.orderStatus.text.actualHandlingFreightWillBeCalculatedAtTimeOfConsolidatedOrder"/></td>
</tr>
<% } %>
<tr><td colspan=3>
<%
if(orderStatusDetail!=null) {
  ReplacedOrderViewVector replacedOrders = orderStatusDetail.getReplacedOrders();
  if(replacedOrders!=null && replacedOrders.size()>0) {
%>
<jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"replacedOrders.jsp")%>' />
<% }} %>
</td></tr>
</table>
</td></tr>
</logic:present>

<logic:notPresent name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail">

<tr align=center>
<td style="border-right: solid 1px black; border-left: solid 1px black;
  font-weight: bold;">
<br><br><br><br>
<app:storeMessage key="shop.orderStatus.text.NoOrderDataAvailable"/>
<br><br><br><br>
</td></tr>

</logic:notPresent>
</html:form>


</table>
<jsp:include flush='true' page="t_orderDetailInvoice.jsp"/>

<jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_table_bottom1.jsp")%>' />



