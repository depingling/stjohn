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

//-->
</script>


<% CleanwiseUser appUser = (CleanwiseUser)
     session.getAttribute(Constants.APP_USER); %>



<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>
<bean:define id="theForm" name="ORDER_OP_DETAIL_FORM" type="com.cleanwise.view.forms.OrderOpDetailForm"/>
<bean:define id="workflowRole" type="java.lang.String" name="ApplicationUser" property="user.workflowRoleCd"/>
<bean:define id="orderStatus" name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.orderDetail.orderStatusCd" type="String"/>
<bean:define id="lOrderId"
  name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.orderDetail.orderId"/>

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






<ul>
<li class="warning">
  <html:errors/>
</li>
<%
    if (!theForm.getUserMessages().isEmpty()) {
%>
                <%
                for (int i = 0; i < theForm.getUserMessages().size(); i++) {
                    String message = (String) theForm.getUserMessages().get(i); %>
                    <li class="warning"><%=message%></li>
                <% } %>
<%
    }
%>
<html:form name="ORDER_OP_DETAIL_FORM" action="/store/orderDetail.do"
        type="com.cleanwise.view.forms.OrderOpDetailForm">
<html:hidden property="action" value="BBBBBBB"/>


<logic:present name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail">

<bean:define id="lOrderId"
  name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.orderDetail.orderId"/>
<li>
<span class="textLabel"><app:storeMessage key="shop.catalog.text.orderNumber"/></span>
<bean:write name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.orderDetail.orderNum"/>
</li>


  <% if(consolidatedToOrderD!=null) {%>
	<li><span class="textLabel"><app:storeMessage key="shop.catalog.text.consolidatedToOrder"/></span>
        <%=consolidatedToOrderD.getOrderNum()%></li>
  <% } %>

<logic:present name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.refOrder">
    
	<li><span class="textLabel"><app:storeMessage key="shop.catalog.text.referenceOrderNum"/></span>
	<bean:write name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.refOrder.orderNum"/></li>
</logic:present>


<li><span class="textLabel"><app:storeMessage key="shop.orderStatus.text.orderDate"/></span>
<%
   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
   sdf.setTimeZone(timeZone);
   Date date = orderStatusDetail.getOrderDetail().getOriginalOrderDate();
   Date time = orderStatusDetail.getOrderDetail().getOriginalOrderTime();
   Date orderDate = Utility.getDateTime(date, time);
   String formattedODate = ClwI18nUtil.formatDateInp(request, orderDate, timeZone.getID() );
%>
<%=formattedODate%>
</li>


<li><span class="textLabel"><app:storeMessage key="shop.orderStatus.text.requestedShipDate"/></span>
<bean:write
 name="ORDER_OP_DETAIL_FORM"
 property="orderStatusDetail.requestedShipDate"/>
</li>

</logic:present>


<li><span class="textLabel"><app:storeMessage key="shop.orderStatus.text.poNumber"/></span>
<%
  String thisPoNum = orderStatusDetail.getOrderDetail().getRequestPoNum();
  if ( null == thisPoNum ) thisPoNum = "";
  if (
    orderStatus.equals(RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL)
    && appUser.canMakePurchases() && allowPoEntry
     ) { %>



<html:text name="ORDER_OP_DETAIL_FORM" property="requestPoNum"
 value="<%=thisPoNum%>" 
 />
<% } else { %>

<bean:write name="ORDER_OP_DETAIL_FORM"
 property="orderStatusDetail.orderDetail.requestPoNum"/>

<% } %>
</li>

<% if(RefCodeNames.ORDER_STATUS_CD.PENDING_DATE.equals(orderStatus)) {%>
<li><span class="textLabel"><app:storeMessage key="shop.orderStatus.text.processOrderOn"/></span>
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
</li>
<% } %>



<% if (appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.WORK_ORDER_APPROVER) ||
         appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.ASSET_USER) ||
         appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR)) {%>
<li>
   <span class="textLabel"><app:storeMessage key="userWorkOrder.text.workOrderNumber"/>
   <html:text name="ORDER_OP_DETAIL_FORM" property="workOrderNumber" size="20" maxlength="22" />
            <html:button property="action" styleClass="store_fb"
                onclick="actionSubmit('BBBBBBB', 'button.bindToWorkOrder');" >
                <app:storeMessage key="global.action.label.save"/>
            </html:button>
</li>
<%}%>
   
<li>
  <span class="textLabel"><app:storeMessage key="shop.orderStatus.text.comments:"/></span>
  <bean:write name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.orderDetail.comments"/>
</li>

<bean:define id="opDV " type="com.cleanwise.service.api.value.OrderPropertyDataVector"
   name="ORDER_OP_DETAIL_FORM" property="orderPropertyList"/>
<% //Workflow properties
IdVector canApproveIdV = new IdVector();

if(opDV.size()>0) {
  canApproveIdV = (IdVector) session.getAttribute("note.approve.ids");
%>

<li><span class="textLabel"><app:storeMessage key="shop.orderStatus.text.notes:"/></span>
<%

for(int ii=0; ii<opDV.size(); ii++) {
  OrderPropertyData opD = (OrderPropertyData) opDV.get(ii);
  if("Workflow Note".equals(opD.getShortDesc())){
%>

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
<%//END Workflow properties%>
</li>
<%

String accountName = "";

/* Handle orders that have been canceled. */

if ( theForm.getOrderStatusDetail().getBillTo() != null &&
     theForm.getOrderStatusDetail().getBillTo().getShortDesc() != null ) {
  accountName = theForm.getOrderStatusDetail().getBillTo().getShortDesc();
}

if ( null == accountName ) accountName = "";

%>
<li>
<logic:present name="ORDER_OP_DETAIL_FORM" property="customerOrderNotes">

          <bean:size id="custNotesCt" name="ORDER_OP_DETAIL_FORM" property="customerOrderNotes"/>
          <logic:greaterThan name="custNotesCt" value="0">
          <app:storeMessage key="label.display.customer.order.notes"arg0="<%=accountName%>"/>:
          
        <logic:iterate name="ORDER_OP_DETAIL_FORM" property="customerOrderNotes" id="note" type="com.cleanwise.service.api.value.OrderPropertyData">
          
<bean:write name="note" property="addBy"/>
<app:writeHTML name="note" property="value"/>

        </logic:iterate>
        </logic:greaterThan>
</logic:present>
</li>
</ul>

<% /* Start - List the items for the order. */ %>
<%int colCount=0;%>


<%colCount++;%>

<ul>
<li class="listResultsHeader"><app:storeMessage key="shoppingCart.text.itemsString"/></li>
<%String lastErpPoNum = null;%>
<logic:iterate id="item" name="ORDER_OP_DETAIL_FORM" property="orderItemDescList"
            offset="0" indexId="oidx" type="com.cleanwise.service.api.value.OrderItemDescData">
<bean:define id="qty" name="item" property="orderItem.totalQuantityOrdered" type="Integer"/>
<%java.math.BigDecimal custItemPrice=new BigDecimal(0.00);%>
<logic:present  name="item" property="orderItem.custContractPrice">
  <%custItemPrice=item.getOrderItem().getCustContractPrice();%>
</logic:present>


<%if(!useCustPo){%>
  <logic:equal name="<%=Constants.APP_USER%>" property="userStore.storeType.value" value="<%=RefCodeNames.STORE_TYPE_CD.DISTRIBUTOR%>">
  <%
  if(item.getOrderItem().getErpPoNum()!= null && !item.getOrderItem().getErpPoNum().equals(lastErpPoNum)){ //display po header text
    lastErpPoNum = item.getOrderItem().getErpPoNum();
  %>
<li>
<dl>
  <dt><app:storeMessage key="shop.orderStatus.text.distributor"/></dt>
  <dd><bean:write name="item" property="distRuntimeDisplayName"/><dd>
  <dt><app:storeMessage key="shop.orderStatus.text.purchaseOrder"/><dt>
  <dd><bean:write name="item" property="orderItem.erpPoNum"/></dd>
      

			   <logic:present name="item" property="orderFreight.shortDesc">
			    <app:storeMessage key="shop.orderStatus.text.freightOption"/>
				<dt><bean:write name="item" property="orderFreight.shortDesc"/></dt>
			   </logic:present>
			   
			   <logic:present name="item" property="orderFreight.amount">
				<dd><bean:define id="amount"  name="item" property="orderFreight.amount"/>
				<%=clwI18n.priceFormat(amount,"&nbsp;")%></dd>
			  </logic:present>

			<logic:present name="item" property="orderDiscount">
			
				<dt><bean:write name="item" property="orderDiscount.shortDesc"/></dt>
				<dd><bean:define id="amount"  name="item" property="orderDiscount.amount"/>
				<%=clwI18n.priceFormat(amount,"&nbsp;")%></dd>
			

			    <logic:present name="item" property="orderFuelSurcharge.shortDesc">
                                <dt><bean:write name="item" property="orderFuelSurcharge.shortDesc"/></dt>
                </logic:present> 
                <logic:present name="item" property="orderFuelSurcharge.amount"> 
				  <dd><bean:define id="amount"  name="item" property="orderFuelSurcharge.amount"/>
				  <%=clwI18n.priceFormat(amount,"&nbsp;")%></dd>
			    </logic:present>  
			
			</logic:present>

			<logic:present name="item" property="orderSmallOrderFee">
			
			   <logic:present name="item" property="orderSmallOrderFee.shortDesc">
				<dt><bean:write name="item" property="orderSmallOrderFee.shortDesc"/></dt>
			   </logic:present>
			   <logic:present name="item" property="orderSmallOrderFee.amount">
				<dd><bean:define id="amount"  name="item" property="orderSmallOrderFee.amount"/>
				<%=clwI18n.priceFormat(amount,"&nbsp;")%></dd>
			   </logic:present>
			
			</logic:present>
					
	
  <%}//end deisplay po header text%>
  </dl>
</li>
  </logic:equal>
<%}%>
<%--Display the distributor and the purchase order number--%>
<li class='<%=((oidx+1) % 2==0)?"even" : "odd"%>'>

<%--//sku customization--%>
<ul>
<li class="listResults"><%=ShopTool.getRuntimeSku(item.getOrderItem(),request)%></li>
<li class="listResults"><bean:write name="item" property="orderItem.itemShortDesc"/></li>
<li class="listResults"><span class="textLabel"><app:storeMessage key="shoppingItems.text.qty"/></span> <bean:write name="item" property="orderItem.totalQuantityOrdered"/></li>
<logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">
  <li class="listResults"><span class="textLabel"><app:storeMessage key="shoppingItems.text.price"/></span> <%=clwI18n.priceFormat(custItemPrice,"&nbsp;")%></li>
</logic:equal>
   <% if(RefCodeNames.ORDER_ITEM_STATUS_CD.CANCELLED.
                         equals(item.getOrderItem().getOrderItemStatusCd())) { %>
    <li class="listResults">
      <app:storeMessage key="shoppingItems.text.cancelled"/>
    </li>
   <% } %>
</ul>
</li>
</logic:iterate>
</ul>
<% /* End   - List the items for the order. */ %>


<% /* Order summary data. */ %>
<dl>
<% boolean freightHandFl = false; %>
<logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">
<dt>
  <app:storeMessage key="shop.orderStatus.text.subtotal:"/>
</dt>
<dd>
  <bean:define id="subtotal"  name="ORDER_OP_DETAIL_FORM" property="subTotal"/>
  <%=clwI18n.priceFormat(subtotal,"&nbsp;")%>
</dd>

<!-- Added for Discount: begin  -->
<!-- cartDiscount -->
<% BigDecimal discountAmt = theForm.getDiscountAmt();// I have to get the discount %>
<% if (discountAmt != null && discountAmt.compareTo(new BigDecimal(0))!=0 ) { %>

<% if (discountAmt.compareTo(new BigDecimal(0)) >0 ) {    	   
	  discountAmt = discountAmt.negate(); 
}
discountAmt = discountAmt.setScale(2, BigDecimal.ROUND_HALF_UP); 
%>
<dt>Discount:</dt>
<dd><%=ClwI18nUtil.getPriceShopping(request,discountAmt,"&nbsp;")%></dd>
<% }%>

<!-- Discount: End  -->
<bean:define id="freightcost"  name="ORDER_OP_DETAIL_FORM"
  property="totalFreightCost" type="BigDecimal"/>
<% if (freightcost!=null && 0.001 < java.lang.Math.abs(freightcost.doubleValue()) &&
       consolidatedToOrderD==null) {
  freightHandFl = true;
%>
<dt><app:storeMessage key="shop.orderStatus.text.freight:"/></dt>
<% if(!toBeConsolidatedFl) { %>
  <dd><%=clwI18n.priceFormat(freightcost,"&nbsp;")%></dd>
<% } else { %>
  <dd>*</dd>
<% } %>

<% }  %>

<%--
<% if ( theForm.getRushOrderCharge() != null ) { %>
<bean:define id="rushCharge"  name="ORDER_OP_DETAIL_FORM"
  property="rushOrderCharge" type="BigDecimal"/>
<% if (rushCharge!=null && consolidatedToOrderD==null) { %>
<dt><app:storeMessage key="shop.orderStatus.text.rushOrderCharge:"/></dt>
<% if(!toBeConsolidatedFl) { %>
  <dd><%=clwI18n.priceFormat(rushCharge,"&nbsp;")%></dd>
<% } else { %>
  <dd>*</dd>
<% } %>

<% }  %>
<% }  /* rush order charge block */ %>--%>

<!-- the misc cost-->
<% if ( theForm.getTotalMiscCost() != null ) { %>
  <bean:define id="misc"  name="ORDER_OP_DETAIL_FORM" property="totalMiscCost" type="BigDecimal"/>
  <% if (misc!=null  && 0.001 < java.lang.Math.abs(misc.doubleValue()) &&
         consolidatedToOrderD==null) {
    freightHandFl = true;
  %>
  <dt><app:storeMessage key="shop.orderStatus.text.handling:"/></dt>
  <dd>
  <% if(!toBeConsolidatedFl) { %>
    <%=clwI18n.priceFormat(misc,"&nbsp;")%>
  <% } else { %>
    *
  <% } %>
  <% }  %>
<% }  %>

<logic:present name="ORDER_OP_DETAIL_FORM" property="smallOrderFeeAmt">
    <bean:define id="smallOrderFee" name="ORDER_OP_DETAIL_FORM" property="smallOrderFeeAmt"
                 type="java.math.BigDecimal"/>
    <% if (smallOrderFee != null && 0.001 < java.lang.Math.abs(smallOrderFee.doubleValue())) { %>
    
            <dt><app:storeMessage key="shop.orderStatus.text.smallOrderFee:"/></dt>
       
            <dd><%=clwI18n.priceFormat(smallOrderFee, "&nbsp;")%></dd>
  
    <% } %>
</logic:present>


<logic:present name="ORDER_OP_DETAIL_FORM" property="fuelSurchargeAmt">
    <bean:define id="fuelSurcharge" name="ORDER_OP_DETAIL_FORM" property="fuelSurchargeAmt"
                 type="java.math.BigDecimal"/>
    <% if (fuelSurcharge != null && 0.001 < java.lang.Math.abs(fuelSurcharge.doubleValue())) { %>

            <dt><app:storeMessage key="shop.orderStatus.text.fuelSurcharge:"/></dt>

            <dd><%=clwI18n.priceFormat(fuelSurcharge, "&nbsp;")%></dd>

    <% } %>
</logic:present>

<!-- the tax cost-->
<logic:present name="ORDER_OP_DETAIL_FORM" property="totalTaxCost">
  <bean:define id="tax"  name="ORDER_OP_DETAIL_FORM" property="totalTaxCost" type="Double"/>
  <% if (tax!=null && 0.001 < java.lang.Math.abs(tax.doubleValue())) { %>
<dt><app:storeMessage key="shop.orderStatus.text.tax:"/></dt>
    <dd class="text"><%=clwI18n.priceFormat(tax,"&nbsp;")%></dd>
  <% }  %>
</logic:present>
<% if(consolidatedToOrderD==null && (!toBeConsolidatedFl || !freightHandFl)) { %>
<dt><app:storeMessage key="shop.orderStatus.text.total:"/></dt>
<bean:define id="total"  name="ORDER_OP_DETAIL_FORM" property="totalAmount"/>
   <dd><%=clwI18n.priceFormat(total,"&nbsp;")%></dd>

<% }  %>
</logic:equal>

<dt><app:storeMessage key="shop.orderStatus.text.orderStatus:"/></dt>
<dd>
<%if(RefCodeNames.ORDER_STATUS_CD.CANCELLED.equals(orderStatus) &&
                orderStatusDetail.getConsolidatedOrder()!=null) { %>
 <app:storeMessage key="shop.orderStatus.text.consolidated"/>
<% }else{ %>
 <%=com.cleanwise.view.utils.ShopTool.xlateStatus(orderStatusDetail)%>
<% } %>
</dd>

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

<dt>
<%
String rejectloc = "javascript:window.location='orderDetail.do?action=reject&orderId=" + lOrderId + "' ";
String approveloc = "javascript:window.location='orderDetail.do?action=approve&orderId=" + lOrderId + "' ";
String poNumStr = "";
  if(useCustPo) {
    poNumStr = "&requestPoNum='+encodeURIComponent('"+requestPoNum+"')+'";
  }
%>
<html:button styleClass="store_fb" property="action" onclick='<%=rejectloc%>'>
  <app:storeMessage key="global.action.label.reject" />
</html:button>
<% if(!toBeConsolidatedFl) { %>
<html:button styleClass="store_fb" property="action" onclick="actionMultiSubmit('BBBBBBB', 'Approve');">
  <app:storeMessage key="global.action.label.approve" />
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
<html:button styleClass="store_fb" property="action"  onclick='<%=deconsolidateloc%>' >
  <app:storeMessage key="shop.orderStatus.text.undoConsolidation" />
</html:button>
<% } %>
<% } %>
<% if(toBeConsolidatedFl && warehouseFl) {
String consolidateloc = "javascript:window.location='orderDetail.do?action=consolidate&orderId=" + lOrderId + "' ";
%>
<html:button styleClass="store_fb" property="action"  onclick='<%=consolidateloc%>' >
  <app:storeMessage key="shop.orderStatus.text.consolidate" />
</html:button>
<% } %>
</dt>

<% } %>

<% /* Ship to data. */ %>

<dt><app:storeMessage key="shop.orderStatus.text.address:"/></dt>
<dd>
<ul>
<li><bean:write name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.shipTo.shortDesc"/></li>
<li><bean:write name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.shipTo.address1"/></li>
<li><bean:write name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.shipTo.address2"/></li>
<li><bean:write name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.shipTo.address3"/></li>
<li><bean:write name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.shipTo.address4"/></li>
<li><bean:write name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.shipTo.city"/></li>
<% if (appUser.getUserStore().isStateProvinceRequired()) { %>
  <li><bean:write name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.shipTo.stateProvinceCd"/></li>
<%} %>
<li><bean:write name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.shipTo.postalCode"/></li>
<li><bean:write name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.shipTo.countryCd"/></li>
</ul>
</dd>
</ul>
 <% /* Ship to data. */ %>

    <jsp:include flush='true' page="<%=ClwCustomizer.getStoreFilePath(request,\"f_budgetInfoInc.jsp\")%>">
      <jsp:param name="isPendingOrderBudjet" value="true" />
    </jsp:include>

<% if(consolidatedToOrderD==null && toBeConsolidatedFl && freightHandFl) { %>
* <app:storeMessage key="shop.orderStatus.text.actualHandlingFreightWillBeCalculatedAtTimeOfConsolidatedOrder"/>
<% } %>



</html:form>
</table>
 
