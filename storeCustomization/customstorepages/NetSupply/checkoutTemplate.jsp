<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.GregorianCalendar" %>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.*" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.util.Locale" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<script language="JavaScript1.2">
<!--

function setAndSubmit(fid, vv, value) {
 var aaa = document.forms[fid].elements[vv];
 aaa.value=value;
 aaa.form.submit();
 return false;
 }

//-->

</script>

<bean:define id="theForm" name="CHECKOUT_FORM"
 type="com.cleanwise.view.forms.CheckoutForm"/>
<%
 ShoppingCartData shoppingCart = (ShoppingCartData) session.getAttribute(Constants.SHOPPING_CART);
 CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
 boolean crcManagerFl = appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.CRC_MANAGER);

 AccountData account = appUser.getUserAccount();
 String allowOrderConsolidationS =
   account.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_ORDER_CONSOLIDATION);
 boolean consolidatedOrderFl =(shoppingCart instanceof ConsolidatedCartView)? true:false;

 boolean allowOrderConsolidationFl =
   (allowOrderConsolidationS != null && allowOrderConsolidationS.length() > 0 &&
      "T".equalsIgnoreCase(allowOrderConsolidationS.substring(0, 1)))? true:false;
 if(consolidatedOrderFl) allowOrderConsolidationFl = false;

String userType = appUser.getUser().getUserTypeCd();

boolean showPlaceOrderFields = false,
        showPlaceOrderButton = true,
           showOrderSelected = false;

java.util.ArrayList pmtopts = new java.util.ArrayList();
//
// Doug requested that the CC option be listed
// first (default) if the user is set up for CC purchases.
// durval 2006-9-13
//
if (appUser.getCreditCardFlag()) {
  pmtopts.add("CC");
}
if (appUser.getOnAccount()) {
  pmtopts.add("PO");
}
if (appUser.getOtherPaymentFlag()) {
  pmtopts.add("Other");
}
String reqpmt = "";
boolean f_showPO = false, f_showCC = false, f_showOther = false;
if ( appUser.canMakePurchases() ) {
 showPlaceOrderFields = true;
 reqpmt = theForm.getReqPmt();
 if (null == reqpmt || "0".equals(reqpmt) ) {
   if ( appUser.getCreditCardFlag() )
   { reqpmt = "CC";}
   else { reqpmt = "PO"; }
 }
 if (reqpmt.equals("PO")) {
   f_showPO = true;
 }
 if (reqpmt.equals("CC")) {
   f_showCC = true;
   if(appUser.getUserStore().getStoreId() == 173243){f_showPO = true;}
 }

 if (reqpmt.equals("Other")) {
   f_showOther = true;
 }

 if (      appUser.getCreditCardFlag()
  && appUser.getOtherPaymentFlag() == false
  && appUser.getOnAccount() == false	) {
  // The user is allowed to use credit cards and no other option has
  // been choosen.  By default, we show the PO.
  f_showCC = true;
  f_showPO = false;
  if(appUser.getUserStore().getStoreId() == 173243){f_showPO = true;}
 }

}


boolean allowPoEntry = true, allowShippingCommentsEntry = true, allowAccountComments = true;
String siteName = appUser.getSite().getBusEntity().getShortDesc();
String accountName = account.getBusEntity().getShortDesc();
String accountNumber = account.getBusEntity().getErpNum();
String siteErp = appUser.getSite().getBusEntity().getErpNum();
String contractName = (String)session.getAttribute("ContractName");
if(RefCodeNames.CUSTOMER_SYSTEM_APPROVAL_CD.PUNCH_OUT_INLIN_NON_E_ORD_ONLY.
                                  equals(account.getCustomerSystemApprovalCd())){
    allowPoEntry = false;
   allowShippingCommentsEntry = false;
   allowAccountComments = false;
}
if(appUser.getUserStore().getStoreId() == 173243){allowPoEntry = true;}

boolean allowPoByVender = false;
if (f_showPO && allowPoEntry){
	allowPoByVender = Utility.isTrue(PropertyUtil.getPropertyValue(appUser.getUserStore().getMiscProperties(),
    		 		RefCodeNames.PROPERTY_TYPE_CD.ALLOW_PO_NUM_BY_VENDER));
}
//BigDecimal rushOrderCharge = new BigDecimal(0);
boolean f_paymetrics = "true".equals(session.getAttribute(Constants.PAYMETRICS))?true:false;
boolean f_paymetricsCC = "true".equals(session.getAttribute(Constants.PAYMETRICS_CC))?true:false;

%>

<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>
<bean:define id="clwWorkflowRole" type="java.lang.String" name="ApplicationUser" property="user.workflowRoleCd"/>

<%
/*
Do not use the html:form tag as struts then tries to interpret
the value for the action as a mapping in the struts config
file.  Since we don't know for sure what the checkout url will
be because of ecounterline integration, use the general form tag.

durval
*/
if ( null != ShopTool.getCheckoutFormUrl(request) ) {
%>
<form action="<%=ShopTool.getCheckoutFormUrl(request)%>"
  target="<%=ShopTool.getCheckoutFormTarget(request)%>"
  name="CHECKOUT_FORM" method="POST" >

<table class="tbstd" width="<%=Constants.TABLEWIDTH%>"
  cellpadding=0 cellspacing=0>
<tr>
<td>

  <jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_shoppingItems.jsp")%>'>
        <jsp:param name="allowEdits" value="false"/>
        <jsp:param name="showSelectCol" value="true"/>
        <jsp:param name="view" value="checkout"/>
        <jsp:param name="orderBy" value="<%=theForm.getSortBy()%>"/>
        <jsp:param name="formId" value="CHECKOUT_FORM"/>
  </jsp:include>

</td>
</tr>
</table>

<table class="tbstd" cellpadding=0 cellspacing=0>

<% if ( null != theForm.getHandlingAmt()) {
%>
<tr>
  <td class="smalltext" align="right">  <b><app:storeMessage key="shop.checkout.text.handling"/></b> </td>
  <td align="right">
    <!-- theForm.getHandlingAmt()-->"
    <%=ClwI18nUtil.getPriceShopping(request,theForm.getHandlingAmt(),"&nbsp;")%></td>
</tr>
<% } %>

<% if ( null != theForm.getMiscAmt()) { %>
<tr><td class="smalltext" align="right"> <b><app:storeMessage key="shop.checkout.text.miscCharges"/></b></td>
<td align="right">
   <!-- theForm.getMiscAmt()-->
   <%=ClwI18nUtil.getPriceShopping(request,theForm.getMiscAmt(),"&nbsp;")%></td>
</tr>
<% } %>

<% if ( null != theForm.getSalesTax()) { %>
<tr valign=top><td class="smalltext" align="right"> <b><app:storeMessage key="shop.checkout.text.tax"/></b></td>
<td align="right">
  <!-- theForm.getSalesTax()-->
  <%=ClwI18nUtil.getPriceShopping(request,theForm.getSalesTax(),"&nbsp;")%></td>
</tr>
<% } %>

<tr><td class="smalltext" align="right"> <b><app:storeMessage key="shop.checkout.text.total"/></b></td>
<% BigDecimal lTotal = theForm.getCartAmt(request).add(theForm.getFreightAmt());
   lTotal = lTotal.add(theForm.getHandlingAmt() );
   lTotal = lTotal.add(theForm.getSalesTax() );
   lTotal = lTotal.add(theForm.getDiscountAmt()); // SVC: new stmt
   %>
 <td align="right">
   <!--lTotal -->
   <%=ClwI18nUtil.getPriceShopping(request,lTotal,"&nbsp;")%>
 </td>
</tr>

<tr>
  <td width="400">&nbsp;</td>
    <td align="right">
    <a href="#" class="linkButton" onclick="setAndSubmit('CHECKOUT_FORM','command','placeOrder');"
    ><img src='<%=IMGPath + "/b_placeorder.gif"%>' border="0"/><app:storeMessage key="global.label.placeOrder"/></a>
    <a href="#" class="linkButton" onclick="setAndSubmit('CHECKOUT_FORM','command','recalc');"
    ><img src='<%=IMGPath + "/b_recalculate.gif"%>' border="0"/><app:storeMessage key="global.label.recalculate"/></a>
</td>
</tr>
</table>
  <html:hidden property="action" value="submit"/>
  <html:hidden property="command" value="placeOrder"/>

  </form>

<jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_table_bottom1.jsp")%>'/>
<% } else { %>
<html:form action="/store/checkout.do?action=submit"
  name="CHECKOUT_FORM" method="POST" >

<%@ include file="f_checkout_msgs.jsp" %>
<table class="tbstd" width="<%=Constants.TABLEWIDTH%>"
  cellpadding=0 cellspacing=0>
<% if(request.getAttribute("org.apache.struts.action.ERROR")!=null) { %>
<tr><td class="genericerror" align="center"><html:errors/></td></tr>
<% } %>
<tr >
<td class="checkoutSubHeader" valign="top">
 <% if(consolidatedOrderFl) {%>
  <app:storeMessage key="shop.checkout.text.consolidatedShoppingCart"/>
 <% } else { %>
  <app:storeMessage key="shop.checkout.text.shoppingCart"/>
 <% } %>
</td>
</tr>
</table>

<table class="tbstd" width="<%=Constants.TABLEWIDTH%>"
  cellpadding=0 cellspacing=0>
<logic:equal name="CHECKOUT_FORM" property="itemsSize" value="0">
<!-- No items in the shopping cart -->
<% if(request.getAttribute("org.apache.struts.action.ERROR")==null) { %>
<tr class="orderInfoHeader">
<td class="text" align="center">
  <br><b><app:storeMessage key="shop.checkout.text.shoppiningCartIsEmpty"/></b><br>&nbsp;
</td>
</tr>
<tr>



<td class="tableoutline">
<img src="<%=IMGPath%>/cw_spacer.gif" height="3">
</td>

</tr>
<%}%>
</logic:equal>
</table>

<table class="tbstd" width="<%=Constants.TABLEWIDTH%>"
  cellpadding=0 cellspacing=0>

<logic:greaterThan name="CHECKOUT_FORM" property="itemsSize" value="0">
<html:hidden name="CHECKOUT_FORM" property="siteId" value="<%=\"\"+theForm.getSite().getBusEntity().getBusEntityId()%>" />
<html:hidden name="CHECKOUT_FORM" property="userId" value="<%=\"\"+appUser.getUser().getUserId()%>" />
<html:hidden name="CHECKOUT_FORM" property="cartAmtString" value="<%=theForm.getCartAmtString()%>" />
<%

if ( ShopTool.isInventoryShoppingOn(request) == true ) {

 showPlaceOrderFields = true;
 if ( appUser.canMakePurchases() ) {
  showOrderSelected = true;
 }
    if (ShopTool.isModernInventoryShopping(request)) {
        showPlaceOrderButton = true;
        showPlaceOrderFields = true;
        if(showOrderSelected) showOrderSelected = false;
    } else {
        showPlaceOrderButton = false;
    }
}

OrderData prevOrder = shoppingCart.getPrevOrderData();
if ( prevOrder != null ) { %>
<tr><td rowspan=4><app:storeMessage key="shop.checkout.text.thisOrderWasCreatedFrom"/> </td></tr>
<tr><td><app:storeMessage key="shop.checkout.text.orderNumber"/></td>
    <td> <%= prevOrder.getOrderNum() %></td>
</tr>
<tr><td><app:storeMessage key="shop.checkout.text.orderedBy"/></td>
<td> <%= prevOrder.getUserFirstName() %> <%= prevOrder.getUserLastName() %> ( <%= prevOrder.getAddBy() %> )</td>
</tr>
<tr><td><app:storeMessage key="shop.checkout.text.originalOrderDate"/></td>
<td> <%= prevOrder.getOriginalOrderDate() %></td>
</tr>
<% } %>
</table>

<table class="tbstd" width="<%=Constants.TABLEWIDTH%>"
  cellpadding=0 cellspacing=0>
<tr class="categoryline">
<%
  if ( null != pmtopts && pmtopts.size() > 1 ) {
  %>
   <td align="right"><b><app:storeMessage key="shop.checkout.text.payment"/></b>
   <select name="reqPmt" onchange="javascript: { document.forms[0].command.value = 'select_pmt_option'; document.forms[0].submit();}">
   <% for ( int pmti = 0; pmti < pmtopts.size(); pmti++ ) {
   String thispmt = (String)pmtopts.get(pmti);
   %>
  <% if ( thispmt.equals("PO") && !f_paymetricsCC) { %>
   <option value="PO"    <% if (reqpmt.equals(thispmt) ) { %>   selected="selected"   <% } %>
   ><app:storeMessage key="shop.checkout.text.purchaseOrder"/></option>
   <% } %>
   <% if ( thispmt.equals("CC")) { %>
   <option value="CC"    <% if (reqpmt.equals(thispmt) ) { %>   selected="selected"   <% } %>
   ><app:storeMessage key="shop.checkout.text.creditCard"/></option>
   <% } %>
   <% if ( thispmt.equals("Other") && !f_paymetricsCC) { %>
   <option value="Other" <% if (reqpmt.equals(thispmt) ) { %>   selected="selected"   <% } %>
   ><app:storeMessage key="shop.checkout.text.recordOfCall"/></option>
   <% } %>

 <% } %>
 </select>
 </td>
  <%
  }else if(reqpmt != null){%>
  <html:hidden property="reqPmt" value="<%=reqpmt%>"/>
  <%}
%>


<td align="right">
  <b><app:storeMessage key="shop.checkout.text.orderBy"/></b>
 <html:select name="CHECKOUT_FORM" property="sortBy" onchange="javascript: { document.forms[0].command.value = 'select_sort'; document.forms[0].submit();}">
 <% if (!f_paymetricsCC || f_paymetricsCC && Constants.ORDER_BY_CATEGORY == theForm.getSortBy()) { %>
 <html:option value="<%=\"\"+Constants.ORDER_BY_CATEGORY%>">
   <app:storeMessage key="shop.checkout.text.category"/>
 </html:option>
 <% }
 if (!f_paymetricsCC || f_paymetricsCC && Constants.ORDER_BY_CUST_SKU == theForm.getSortBy()) { %>
 <html:option value="<%=\"\"+Constants.ORDER_BY_CUST_SKU%>">
   <app:storeMessage key="shop.checkout.text.ourSkuNum"/>
 </html:option>
 <% }
 if (!f_paymetricsCC || f_paymetricsCC && Constants.ORDER_BY_NAME == theForm.getSortBy()) { %>
 <html:option value="<%=\"\"+Constants.ORDER_BY_NAME%>">
   <app:storeMessage key="shop.checkout.text.productName"/>
 </html:option>
 <%}%>
 </html:select>
</td>
</tr>
</table>

<% if (account.isShowSPL()) {
    boolean isNonSPLExists = false;
    java.util.List items = ShopTool.getCurrentShoppingCart(request).getItems();
    if (items != null) {
      for (int i=0; i<items.size(); i++) {
        ShoppingCartItemData item = (ShoppingCartItemData)items.get(i);
        ItemMappingData catDistMapping = item.getProduct().getCatalogDistrMapping();
        if (catDistMapping==null ||
            !com.cleanwise.service.api.util.Utility.isTrue(catDistMapping.getStandardProductList(),false)) {
          isNonSPLExists = true;
          break;
        }
      }
    }
    if (isNonSPLExists) {
%>
<table class="tbstd" width="<%=Constants.TABLEWIDTH%>" cellpadding=0 cellspacing=0>
<tr><td align="center" style="padding-top: 2em; padding-bottom: 2em;"><b><app:storeMessage key="shop.checkout.text.nonSPLItemsWarning"/></b></td></tr>
</table>
<%}} %>

<%
  String showDistInventory = ShopTool.getShowDistInventoryCode(request);
  if(RefCodeNames.DIST_INVENTORY_DISPLAY.SHOW_FLAG.equals(showDistInventory) ||
     RefCodeNames.DIST_INVENTORY_DISPLAY.SHOW_QUANTITIES.equals(showDistInventory)) {
    int noInventory = 0;
    java.util.List items = ShopTool.getCurrentShoppingCart(request).getItems();
    if (items != null) {
      for (int i=0; i<items.size(); i++) {
        ShoppingCartItemData item = (ShoppingCartItemData)items.get(i);
        int invQty = item.getDistInventoryQty();
        if(invQty==0) {
            noInventory = 2;
            break;
        }
        if(invQty<0) noInventory = 1;
      }
    }
    if (noInventory>0) {
%>
<table class="tbstd" width="<%=Constants.TABLEWIDTH%>" cellpadding=0 cellspacing=0>
<tr><td align="center" style="padding-top: 2em; padding-bottom: 2em;">
  <% if(noInventory==1) { %>
  <b><font color='red'><app:storeMessage key="shop.checkout.text.noInvInfoWarning"/></font></b>
  <% } else { %>
  <b><font color='red'><app:storeMessage key="shop.checkout.text.noInventoryWarning"/></font></b>
  <% } %>
</td></tr>
</table>
<%}} %>

<%if(appUser.getUserStore().getStoreId() == 173243){f_showPO=true;}%>
<%    if ( showPlaceOrderFields ) {   %>
  <jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_placeOrderFields.jsp")%>'>
     <jsp:param name="showPlaceOrderFields" value='<%=String.valueOf(showPlaceOrderFields)%>'/>
     <jsp:param name="showPlaceOrderButton" value='<%=String.valueOf(showPlaceOrderButton)%>'/>
     <jsp:param name="f_showCC" value='<%=String.valueOf(f_showCC)%>'/>
     <jsp:param name="f_showOther" value='<%=String.valueOf(f_showOther)%>'/>
     <jsp:param name="f_showPO" value='<%=String.valueOf(f_showPO)%>'/>
     <jsp:param name="allowPoEntry" value='<%=String.valueOf(allowPoEntry)%>'/>
     <jsp:param name="allowShippingCommentsEntry" value='<%=String.valueOf(allowShippingCommentsEntry)%>'/>
     <jsp:param name="allowAccountComments" value='<%=String.valueOf(allowAccountComments)%>'/>
     <jsp:param name="allowOrderConsolidationFl" value='<%=String.valueOf(allowOrderConsolidationFl)%>'/>
     <jsp:param name="formId" value="CHECKOUT_FORM"/>
     <jsp:param name="allowPoByVender" value='<%=String.valueOf(allowPoByVender)%>'/>
     <jsp:param name="f_paymetrics" value='<%=String.valueOf(f_paymetrics)%>'/>
     <jsp:param name="f_paymetricsCC" value='<%=String.valueOf(f_paymetricsCC)%>'/>
  </jsp:include>
<% } /* End of the place order fields. */ %>

<table class="tbstd" width="<%=Constants.TABLEWIDTH%>"
  cellpadding=0 cellspacing=0>
<tr>
<td>

  <jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_shoppingItems.jsp")%>'>
        <jsp:param name="allowEdits" value="false"/>
        <jsp:param name="showSelectCol" value="true"/>
        <jsp:param name="view" value="checkout"/>
        <jsp:param name="orderBy" value="<%=theForm.getSortBy()%>"/>
        <jsp:param name="formId" value="CHECKOUT_FORM"/>
        <jsp:param name="allowPoByVender" value='<%=String.valueOf(allowPoByVender)%>'/>
  </jsp:include>

</td>
</tr>
</table>



<table class="tbstd" width="<%=Constants.TABLEWIDTH%>"
  cellpadding=0 cellspacing=0 >
     <tr valign=top>
     <td align="center">
<table border="0" align="center" cellspacing="0" cellpadding="0">
<tr valign=top>
        <td valign=top>      <!-- Table order summary beg -->
     <table border="0" cellspacing="0" cellpadding="0" align="center">
          <%--  <bean:define id="cartAmt" name="CHECKOUT_FORM" property="cartAmt"/> --%>
		  <%BigDecimal cartAmt = (theForm.getCartAmt(request)); %>
        <tr>
<td colspan=3 class="checkoutSummaryHeader" WIDTH="243" HEIGHT="21">
  <app:storeMessage key="shop.checkout.header.summary"/></td>
</tr>
<tr>
<td class="smalltext"><b><app:storeMessage key="shop.checkout.text.subtotal"/></b></td>
<!-- cartAmt -->
<td align="right"><%=ClwI18nUtil.getPriceShopping(request,cartAmt,"&nbsp;")%></td>
<td> &nbsp;&nbsp;</td>
                </tr>

<!-- Added by SVC: Discount: begin  -->
<tr>
<!-- cartDiscount -->
<% BigDecimal discountAmt = theForm.getDiscountAmt();// I have to calculate discount here %>
<% if (discountAmt != null && discountAmt.compareTo(new BigDecimal(0))!=0 ) { %>
	   <td class="smalltext"><b><html>Discount:</html></b></td>
       <td align="right"><%=ClwI18nUtil.getPriceShopping(request,discountAmt,"&nbsp;")%></td>
       <td> &nbsp;&nbsp;</td>
<% } %>
</tr>
<!-- Discount: End  -->

  <bean:define id="freightAmt" name="CHECKOUT_FORM" property="freightAmt"/>
  <% if (!f_paymetricsCC && (appUser.isaCustServiceRep() || crcManagerFl)) { %>
    <tr><td class="smalltext"> <b><app:storeMessage key="shop.checkout.text.freight"/></b></td>
      <td align="right"><html:text name="CHECKOUT_FORM" property="freightAmtString" size="8"/> </td>
      <td> &nbsp;&nbsp;</td>
    </tr>
  <% } else { %>
  <html:hidden name="CHECKOUT_FORM" property="freightAmtString" value="<%=theForm.getFreightAmtString()%>" />
  <logic:greaterThan name="CHECKOUT_FORM" property="freightAmt" value="0">
    <tr><td class="smalltext" > <b><app:storeMessage key="shop.checkout.text.freight"/></b> </td>
      <!-- freightAmt -->
      <td align="right"><%=ClwI18nUtil.getPriceShopping(request,freightAmt,"&nbsp;")%><bean:write name="CHECKOUT_FORM" property="estimateFreightString" /></td>
    <td> &nbsp;&nbsp;</td>
    </tr>
    </logic:greaterThan>
  <% } %>



<bean:define id="handlingAmt" name="CHECKOUT_FORM"
  property="handlingAmt" type="java.math.BigDecimal"/>
<%
if ( prevOrder != null && prevOrder.getTotalMiscCost() != null &&
        prevOrder.getTotalMiscCost().intValue() > 0 ) {
  if ( handlingAmt == null || handlingAmt.intValue() <= 0 ) {
     String p = prevOrder.getOrderSourceCd();
        if ( null == p ) p = "";
        if ( ! p.equals("Inventory") ) {
          // Don't carry over freight charges on inventory orders.
          // Let the system re-compute them.
          handlingAmt = prevOrder.getTotalMiscCost();
          theForm.setHandlingAmt(handlingAmt);
        }
  }
}
%><%--
<!-- Rush order charge (begin) -->
<% if(Utility.isSet(ShopTool.getRushOrderCharge(request))) {
     String rushOrderChargeS = theForm.getRushChargeAmtString();
     if(rushOrderChargeS==null) rushOrderChargeS = "";
     try {
	    rushOrderCharge = new BigDecimal(rushOrderChargeS);
     } catch (Exception exc) {}
%>
<% if (!f_paymetricsCC && (appUser.isaCustServiceRep() || crcManagerFl)) { %>
  <tr><td class="smalltext">  <b><app:storeMessage key="shop.checkout.text.rushOrderCharge"/></b></td>
  <td align="right"> <html:text name="CHECKOUT_FORM" property="rushChargeAmtString" size="8"/></td>
  <td> &nbsp;&nbsp;</td>
  </tr>
<% } else { %>
  <html:hidden name="CHECKOUT_FORM" property="rushChargeAmtString" value="<%=rushOrderChargeS%>" />
  <% if(rushOrderCharge.doubleValue()>0.01) { %>
  <tr><td class="smalltext" >  <b><app:storeMessage key="shop.checkout.text.rushOrderCharge"/></b> </td>
  <td align="right"><%=ClwI18nUtil.getPriceShopping(request,rushOrderCharge,"&nbsp;")%></td>
  <td> &nbsp;&nbsp;</td>
  </tr>
<% } %>
<% } %>
<% } %>
<!--Rush order charge (end) -->
--%>
<%
String applyHandlingCharge = null;
try{
applyHandlingCharge = theForm.getSite().getSiteFieldValue
  ("Apply contract handling charges");
  }catch(Exception e){e.printStackTrace();}

if (applyHandlingCharge != null &&
  applyHandlingCharge.toLowerCase().equals("n")) {
  handlingAmt = new java.math.BigDecimal("0");
  theForm.setHandlingAmt(handlingAmt);
}
%>
<% if (!f_paymetricsCC && (appUser.isaCustServiceRep() || crcManagerFl)) { %>
  <tr><td class="smalltext">  <b><app:storeMessage key="shop.checkout.text.handling"/></b></td>
  <td align="right"> <html:text name="CHECKOUT_FORM" property="handlingAmtString" size="8"/> </td>
  <td> &nbsp;&nbsp;</td>
  </tr>
<% } else { %>
<html:hidden name="CHECKOUT_FORM" property="handlingAmtString" value="<%=theForm.getHandlingAmtString()%>" />
            <logic:greaterThan name="CHECKOUT_FORM" property="handlingAmt" value="0">
  <tr><td class="smalltext" >  <b><app:storeMessage key="shop.checkout.text.handling"/></b> </td>
  <!-- handlingAmt -->
  <td align="right"><%=ClwI18nUtil.getPriceShopping(request,handlingAmt,"&nbsp;")%></td>
<td> &nbsp;&nbsp;</td>
  </tr>
            </logic:greaterThan>
<% } %>

          <logic:greaterThan name="CHECKOUT_FORM" property="smallOrderFeeAmt" value="0">
              <tr valign=top>
                  <bean:define id="smallOrderFeeAmt" name="CHECKOUT_FORM" property="smallOrderFeeAmt"/>
                  <!-- smallOrderFee -->
                  <td class="smalltext"><b> <app:storeMessage key="shop.checkout.text.smallOrderFee:"/> </b></td>
                  <td align="right"><%=ClwI18nUtil.getPriceShopping(request, smallOrderFeeAmt, "&nbsp;")%> </td>
                  <td> &nbsp;&nbsp;</td>
              </tr>
          </logic:greaterThan>
          <%--<logic:greaterThan name="CHECKOUT_FORM" property="fuelSurchargeAmt" value="0">--%>
		  <% if (appUser.isaCustServiceRep() || crcManagerFl) { %>
		    <% BigDecimal fuelSurchargeAmt = theForm.getFuelSurchargeAmt();%>
            <% if (fuelSurchargeAmt != null && fuelSurchargeAmt.compareTo(new BigDecimal(0))!=0 ) { %>
			<tr><td class="smalltext">  <b><app:storeMessage key="shop.checkout.text.fuelSurcharge:"/></b></td>
				<td align="right"> <html:text name="CHECKOUT_FORM" property="fuelSurchargeAmt" size="8"/></td>
				<td> &nbsp;&nbsp;</td>
			</tr>
			<% } %>
		  <% } else { %>
              <tr valign=top>

                <bean:define id="fuelSurchargeAmt" name="CHECKOUT_FORM" property="fuelSurchargeAmt"/>
                <!-- fuelSurchargeAmt -->
                <% BigDecimal fuelSurchargeAmt2 = theForm.getFuelSurchargeAmt();%>
                <% if (fuelSurchargeAmt2 != null && fuelSurchargeAmt2.compareTo(new BigDecimal(0))!=0 ) { %>
                  <td class="smalltext"><b><app:storeMessage key="shop.checkout.text.fuelSurcharge:"/></b></td>
                  <td align="right"><%=ClwI18nUtil.getPriceShopping(request, fuelSurchargeAmt, "&nbsp;")%></td>
                  <td> &nbsp;&nbsp;</td>
                <% } %>
              </tr>
		   <%}%>
          <%--</logic:greaterThan>--%>

          <bean:define id="miscAmt"  name="CHECKOUT_FORM" property="miscAmt"/>
            <logic:greaterThan name="CHECKOUT_FORM" property="miscAmt" value="0">
<tr><td class="smalltext"> <b><app:storeMessage key="shop.checkout.text.miscCharges"/></b></td>
<!-- miscAmt -->
<td align="right"><%=ClwI18nUtil.getPriceShopping(request,miscAmt,"&nbsp;")%></td>
<td> &nbsp;&nbsp;</td>
</tr>
            </logic:greaterThan>

<tr><td class="smalltext"> <b><app:storeMessage key="shop.checkout.text.total"/></b></td>
<%	BigDecimal lTotal = theForm.getCartAmt(request);
  lTotal = lTotal.add(theForm.getFreightAmt());
   lTotal = lTotal.add(theForm.getFuelSurchargeAmt()!=null?theForm.getFuelSurchargeAmt():new BigDecimal(0));
   lTotal = lTotal.add(theForm.getSmallOrderFeeAmt()!=null?theForm.getSmallOrderFeeAmt():new BigDecimal(0));
   lTotal = lTotal.add(handlingAmt);
//   lTotal = lTotal.add(rushOrderCharge);
   lTotal = lTotal.add(theForm.getDiscountAmt()); // SVC: new stmt
   lTotal = lTotal.add(theForm.getSalesTax()); %>
 <td align="right">
   <!-- lTotal -->
   <%=ClwI18nUtil.getPriceShopping(request,lTotal,"&nbsp;")%><bean:write name="CHECKOUT_FORM" property="estimateFreightString" />
 </td>
<td> &nbsp;&nbsp;</td>
</tr>

<tr>
<td colspan="2">&nbsp;&nbsp;</td>
</tr>
<tr>
<td class="smalltext" colspan="2" align="center"><b>Applicable tax calculated on invoice</b></td>
</tr>

     </table><!--Table order summary end -->
</td>
<td valign=top>     <!-- Table billto beg -->
     <table border="0" cellspacing="0" cellpadding="0" align="center">
        <tr>
        <td  class="checkoutSummaryHeader" WIDTH="243" HEIGHT="21">
       <app:storeMessage key="shop.checkout.header.billingInformation"/>
     </td>
</tr><tr>
        <td class="smalltext"><div class="fivemargin">
        <b><app:storeMessage key="shop.checkout.text.accountName"/></b>&nbsp;<%=accountName%><br>
</div>
</td>
</tr>

<tr><td>
<% if (appUser.canEditBillTo() && f_showCC == false ) { %>
    <table>
<tr><td>  <b><app:storeMessage key="shop.checkout.text.address1"/></b></td>
    <td>  <html:text name="CHECKOUT_FORM"
          property="requestedBillToAddress.address1"/>
    </td>
</tr>
<tr><td>  <b><app:storeMessage key="shop.checkout.text.address2"/></b></td>
    <td>  <html:text name="CHECKOUT_FORM"
          property="requestedBillToAddress.address2"/>
    </td>
</tr>
<tr><td>  <b><app:storeMessage key="shop.checkout.text.address3"/></b></td>
    <td>  <html:text name="CHECKOUT_FORM"
          property="requestedBillToAddress.address3"/>
    </td>
</tr>

<tr><td> <b><app:storeMessage key="shop.checkout.text.city:"/></b></td>
    <td> <html:text name="CHECKOUT_FORM"
          property="requestedBillToAddress.city"/>
    </td>
</tr>
<% if (appUser.getUserStore().isStateProvinceRequired()) { %>
<tr><td> <b><app:storeMessage key="shop.checkout.text.state:"/></b></td>
    <td> <html:text name="CHECKOUT_FORM" size="2" maxlength="2"
          property="requestedBillToAddress.stateProvinceCd"/>
    </td>
</tr>
<%} %>
<tr><td> <b><app:storeMessage key="shop.checkout.text.zip:"/></b></td>
    <td> <html:text name="CHECKOUT_FORM"
          property="requestedBillToAddress.postalCode"/>
    </td>
</tr>
<tr><td> <b><app:storeMessage key="shop.checkout.text.country:"/></b></td>
    <td> <html:text name="CHECKOUT_FORM"
          property="requestedBillToAddress.countryCd"/>
    </td>
</tr>

    </table>

<% } else if (f_showCC == false) { %>

    <table>
<tr><td> <b><app:storeMessage key="shop.checkout.text.address1"/></b></td>
<td><bean:write name="CHECKOUT_FORM" property="account.billingAddress.address1"/></td>
</tr>
        <% String address2 = theForm.getAccount().getBillingAddress().getAddress2(); %>
        <% if(address2!=null && address2.trim().length()>0) { %>
<tr><td> <b><app:storeMessage key="shop.checkout.text.address2"/></b></td>
<td><bean:write name="CHECKOUT_FORM" property="account.billingAddress.address2"/></td>
</tr>
        <% } %>
        <% String address3 = theForm.getAccount().getBillingAddress().getAddress3(); %>
        <% if(address3!=null && address3.trim().length()>0) { %>
<tr><td> <b><app:storeMessage key="shop.checkout.text.address3"/></b></td>
<td><bean:write name="CHECKOUT_FORM" property="account.billingAddress.address3"/></td>
</tr>
        <% } %>
        <% String address4 = theForm.getAccount().getBillingAddress().getAddress4(); %>
        <% if(address4!=null && address4.trim().length()>0) { %>
<tr><td> <b><app:storeMessage key="shop.checkout.text.address4"/></b></td>
<td><bean:write name="CHECKOUT_FORM" property="account.billingAddress.address4"/></td>
</tr>
        <% } %>

<tr><td> <b><app:storeMessage key="shop.checkout.text.city:"/></b></td>
<td><bean:write name="CHECKOUT_FORM" property="account.billingAddress.city"/></td>
</tr>

<%
if (appUser.getUserStore().isStateProvinceRequired()) {
%>
<tr><td> <b><app:storeMessage key="shop.checkout.text.state:"/></b></td>
  <td><bean:write name="CHECKOUT_FORM" property="account.billingAddress.stateProvinceCd"/></td>
</tr>
<% } %>

<tr><td> <b><app:storeMessage key="shop.checkout.text.zip:"/></b></td>
<td><bean:write name="CHECKOUT_FORM" property="account.billingAddress.postalCode"/></td>
</tr>
<tr><td> <b><app:storeMessage key="shop.checkout.text.country:"/></b></td>
<td><bean:write name="CHECKOUT_FORM" property="account.billingAddress.countryCd"/></td>
</tr>
</table>

<% } %>
</td></tr>

     </table>
     </td>
     <td  valign=top>
     <table border="0" cellspacing="0" cellpadding="0" align="center">
        <tr>
  <td colspan="2"  class="checkoutSummaryHeader" WIDTH="243" HEIGHT="21">
   <app:storeMessage key="shop.checkout.header.shippingInformation"/>
  </td>
</tr>


<tr valign=top><td>

<% if ( !ShopTool.isAnOCISession(request.getSession()) ) { %>


            <table>
<tr><td>  <b><app:storeMessage key="shop.checkout.text.address1"/></b></td>
    <td>  <bean:write name="CHECKOUT_FORM"
          property="site.siteAddress.address1"/>
    </td>
</tr>
<tr><td>  <b><app:storeMessage key="shop.checkout.text.address2"/></b></td>
    <td>  <bean:write name="CHECKOUT_FORM"
          property="site.siteAddress.address2"/>
    </td>
</tr>
<tr><td>  <b><app:storeMessage key="shop.checkout.text.address3"/></b></td>
    <td>  <bean:write name="CHECKOUT_FORM"
          property="site.siteAddress.address3"/>
    </td>
</tr>

<tr><td> <b><app:storeMessage key="shop.checkout.text.city:"/></b></td>
    <td> <bean:write name="CHECKOUT_FORM"
          property="site.siteAddress.city"/>
    </td>
</tr>
<% if (appUser.getUserStore().isStateProvinceRequired()) {%>
<tr><td> <b><app:storeMessage key="shop.checkout.text.state:"/></b></td>
  <td> <bean:write name="CHECKOUT_FORM"
    property="site.siteAddress.stateProvinceCd"/>
  </td>
</tr>
<% } %>
<tr><td> <b><app:storeMessage key="shop.checkout.text.zip:"/></b></td>
    <td> <bean:write name="CHECKOUT_FORM"
          property="site.siteAddress.postalCode"/>
    </td>
</tr>
<tr><td> <b><app:storeMessage key="shop.checkout.text.country:"/></b></td>
    <td> <bean:write name="CHECKOUT_FORM"
          property="site.siteAddress.countryCd"/>
    </td>
</tr>

          </table>
<% } %>

</td></tr>


     </table><!--Table shipto end -->
     </td>

</tr>
</table>

  <jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_placeOrderButtons.jsp")%>'>
     <jsp:param name="showPlaceOrderFields" value='<%=String.valueOf(showPlaceOrderFields)%>'/>
     <jsp:param name="showPlaceOrderButton" value='<%=String.valueOf(showPlaceOrderButton)%>'/>
     <jsp:param name="showOrderSelected" value='<%=String.valueOf(showOrderSelected)%>'/>
     <jsp:param name="f_paymetrics" value='<%=String.valueOf(f_paymetrics)%>'/>
     <jsp:param name="f_paymetricsCC" value='<%=String.valueOf(f_paymetricsCC)%>'/>
     <jsp:param name="f_showCC" value='<%=String.valueOf(f_showCC)%>'/>
  </jsp:include>


</logic:greaterThan>

</td></tr>
</table>

<% if(consolidatedOrderFl) { %>
<jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"assocOrder.jsp")%>'/>
<% } %>

<jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_table_bottom1.jsp")%>'/>

  <html:hidden property="action" value="submit"/>
  <html:hidden property="command" value="placeOrder"/>

</html:form>
<% } %>


<!-- CRC INFO -->
<%
  if(userType.equals(RefCodeNames.USER_TYPE_CD.CUSTOMER_SERVICE) ||
     userType.equals(RefCodeNames.USER_TYPE_CD.ADMINISTRATOR) ||
     userType.equals(RefCodeNames.USER_TYPE_CD.CRC_MANAGER) ||
	 crcManagerFl){
%>

<table align="center" border=0" cellspacing="0"
  cellpadding="0" class="textreadonly">
<tr><td>
<b><app:storeMessage key="shop.checkout.text.accountName"/></b>  <%=accountName%> </td></tr>
<tr><td>
<b><app:storeMessage key="shop.checkout.text.accountErp"/></b> <%=accountNumber%>  </td></tr>
<tr><td><b><app:storeMessage key="shop.checkout.text.siteName"/></b> <%=siteName %> </td></tr>
<tr><td><b><app:storeMessage key="shop.checkout.text.siteErp"/></b><%=siteErp%> </td></tr>
<tr><td><b><app:storeMessage key="shop.checkout.text.contractName"/></b> <%=contractName %> </td></tr>
</table>

<% } %>



