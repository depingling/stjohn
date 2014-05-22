<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.view.utils.ShopTool" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.math.BigDecimal" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<script language="JavaScript1.2">
<!--
function f_SetChecked(val,prop) {
 dml=document.CHECKOUT_FORM;
 len = dml.elements.length;
 var i=0;
 for( i=0 ; i<len ; i++) {
   if (dml.elements[i].name==prop && dml.elements[i].disabled!=true) {
     dml.elements[i].checked=val;
   }
 }
}

//-->
</script>

<bean:define id="theForm" name="SHOPPING_CART_FORM"
      type="com.cleanwise.view.forms.ShoppingCartForm" scope="session"/>

<%

CleanwiseUser appUser = ShopTool.getCurrentUser(request);

String prevCategory = "";

boolean
  quickOrderView = false,
  checkoutView = false,
  editCartItems = true,
  f_showSelectCol = false;

String
 allowEdits = request.getParameter("allowEdits"),
 thisView = request.getParameter("view"),
 showSelectCol = request.getParameter("showSelectCol"),
 tmp_itemorder = request.getParameter("orderBy")
;

if ( null == thisView ) thisView = "";

if (      thisView.equals("quickOrder") ) { quickOrderView = true; }
else if ( thisView.equals("checkout") )   { checkoutView = true; }

if ( allowEdits != null && allowEdits.equals("false")) {
  editCartItems = false;
}
if ( showSelectCol != null && showSelectCol.equals("true")) {
  f_showSelectCol = true;
  if ( appUser.canMakePurchases() == false )
  {
   f_showSelectCol = false;
  }
}
int orderby = 0;
if ( tmp_itemorder != null ) {
  orderby = Integer.parseInt(tmp_itemorder);
}

String
  storeDir=ClwCustomizer.getStoreDir(),
  requestNumber =  (String) session.getAttribute(Constants.REQUEST_NUM),
  IPTH = (String) session.getAttribute("pages.store.images");

  ShoppingCartData shoppingCart = ShopTool.getCurrentShoppingCart(request);
  theForm.setCartItems(shoppingCart.getItems());

  int  COL_COUNT = 14;

  int rowCount = 0;
%>

<table cellpadding="0" cellspacing="0" align="center"
 width="<%=Constants.TABLEWIDTH_m2%>" >

 <tr>

<% boolean inventoryShopping = ShopTool.isInventoryShoppingOn(request); %>
<% boolean modernInvShopping = ShopTool.isModernInventoryShopping(request); %>
<%
boolean resaleItemsAllowed = ShopTool.isResaleItemsAllowed(request);
boolean resaleItemsAllowedViewOnly=true;
%>
<app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ADD_RE_SALE_ITEMS%>">
<%resaleItemsAllowedViewOnly=false;%>
</app:authorizedForFunction>

<%
if(resaleItemsAllowed){
  COL_COUNT++;
}
if (appUser.getUserAccount().isShowSPL()) {
  COL_COUNT++;
}
%>

<% if (inventoryShopping) {
  COL_COUNT = COL_COUNT + 3;
 %>


  <td width=30 align="left" class="shopcharthead">&nbsp;</td>
  <td align="center" class="shopcharthead">&nbsp;<!-- app:storeMessage key="shoppingItems.text.onHand"/ --></td>
<% }

/* Remove the select column */
if (f_showSelectCol == false ||
    editCartItems == false ||
    quickOrderView ) { COL_COUNT = COL_COUNT - 1; }

%>
  <td class="shopcharthead" align="center">
    <app:storeMessage key="shoppingItems.text.maxOrderQty"/>
  </td>


  <td width=30 align="center" class="shopcharthead"><app:storeMessage key="shoppingItems.text.orderQty"/></td>
  <%
  String showDistInventory = ShopTool.getShowDistInventoryCode(request);
  if(RefCodeNames.DIST_INVENTORY_DISPLAY.SHOW_FLAG.equals(showDistInventory) ||
     RefCodeNames.DIST_INVENTORY_DISPLAY.SHOW_QUANTITIES.equals(showDistInventory)) {
    COL_COUNT++;
  %>
  <td class="shopcharthead"><div class="fivemargin"><app:storeMessage key="shoppingItems.text.distInv"/></div></td>
  <% } %>

  <td class="shopcharthead"><div class="fivemargin"><app:storeMessage key="shoppingItems.text.ourSkuNum"/></div></td>
  <td class="shopcharthead"><div class="fivemargin"><app:storeMessage key="shoppingItems.text.productName"/></div></td>
  <td class="shopcharthead"><div class="fivemargin"><app:storeMessage key="shoppingItems.text.size"/></div></td>
  <td class="shopcharthead"><div class="fivemargin"><app:storeMessage key="shoppingItems.text.pack"/></div></td>
  <td class="shopcharthead"><div class="fivemargin"><app:storeMessage key="shoppingItems.text.uom"/></div></td>
  <td class="shopcharthead"><div class="fivemargin"><app:storeMessage key="shoppingItems.text.color"/></div></td>
  <logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">
    <td class="shopcharthead"><div class="fivemargin"><app:storeMessage key="shoppingItems.text.price"/></div></td>
    <td class="shopcharthead"><div class="fivemargin"><app:storeMessage key="shoppingItems.text.amount"/></div></td>
  </logic:equal>
  <logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="false">
     <td class="shopcharthead" align="center" colspan="2">&nbsp;</td>
  </logic:equal>
  <% if (appUser.getUserAccount().isShowSPL()) { %>
    <td class="shopcharthead"><div class="fivemargin"><app:storeMessage key="shoppingItems.text.spl"/></div></td>
  <%} %>

<% if (editCartItems) { %>
  <td class="shopcharthead"><div class="fivemargin"><app:storeMessage key="global.action.label.select"/></div></td>
<% } else if (
  inventoryShopping && quickOrderView == false && f_showSelectCol == true) {  %>
  <td class="shopcharthead"><div class="fivemargin">
<a href="javascript:f_SetChecked(1,'orderSelectBox')"><app:storeMessage key="shoppingItems.text.checkAll"/></a>
<a href="javascript:f_SetChecked(0,'orderSelectBox')"><app:storeMessage key="shoppingItems.text.clear"/></a>
</td>
<% }else{ %>
  <td class="shopcharthead">&nbsp;</td>
<% } %>

<% if(resaleItemsAllowed && !editCartItems && !quickOrderView) { %>
  <td class="shopcharthead"><div class="fivemargin">
        <font color="red"><app:storeMessage key="shoppingItems.text.reSaleItem"/></font>
    <%if(!resaleItemsAllowedViewOnly){%>
    <br>
        <a href="javascript:f_SetChecked(1,'reSaleSelectBox')"><app:storeMessage key="shoppingItems.text.checkAll"/></a>
        <a href="javascript:f_SetChecked(0,'reSaleSelectBox')"><app:storeMessage key="shoppingItems.text.clear"/></a>
    <%}%>
  </div></td>
<% } %>

  </tr>
  <tr><td colspan="<%=COL_COUNT%>" class="tableoutline"><img src="/images/cw_spacer.gif" height="1"></td></tr>

  <bean:define id="SCARTITEMS" name="SHOPPING_CART_FORM" property="cartItems" type="java.util.List"/>

  <%
  if(orderby==Constants.ORDER_BY_CATEGORY)
  {
    shoppingCart.orderByCategory(SCARTITEMS);
  }
%>


<%
    boolean isaMLAStore = RefCodeNames.STORE_TYPE_CD.MLA.equals
      (appUser.getUserStore().getStoreType().getValue());
  // for checkoutView on store type other than MLA, we need display distributor info
  // (which items belong to which distributor)
  if (checkoutView && ! isaMLAStore) {
%>

    <bean:define id="SCartDistV" name="CHECKOUT_FORM" property="cartDistributors" type="ShoppingCartDistDataVector"/>

    <logic:iterate id="scdistD" name="SCartDistV"
       offset="0" indexId="DISTIDX"
       type="com.cleanwise.service.api.value.ShoppingCartDistData">

    <bean:define id="distName" name="scdistD" property="distributorName"/>
    <bean:define id="distItems" name="scdistD" property="shoppingCartItems"
      type="java.util.List"/>

    <tr>
      <td class="shopcharthead" colspan="<%=COL_COUNT%>">
        &nbsp;<bean:write name="scdistD" property="distributorName"/>
      </td>
    </tr>

      <logic:iterate id="sciD" name="distItems"
         offset="0" indexId="IDX"
         type="com.cleanwise.service.api.value.ShoppingCartItemData">

         <bean:define id="orderNumber" name="sciD" property="orderNumber"/>
         <bean:define id="itemId" name="sciD" property="product.productId" type="java.lang.Integer" />

    <%
      String
        quantityEl =     "quantityElement["+IDX+"]",
        itemIdsEl =      "itemIdsElement["+IDX+"]",
        orderNumbersEl = "orderNumbersElement["+IDX+"]"
      ;

      SiteInventoryInfoView inventoryItemInfo =
        ShopTool.getInventoryItem(request, itemId.intValue());
    %>

    <%
      if (checkoutView) {
          if ( sciD.getQuantity() > 0) {
          /* Only include items in the checkout screen if
             the item is being ordered.
          */
      %>

    <%                  if(orderby==Constants.ORDER_BY_CATEGORY) { %>
    <%                          if ( !prevCategory.equals(sciD.getCategoryPath()) ) { %>
      <tr> <td class="shopcategory" colspan="<%=COL_COUNT%>">

        <table cellpadding="0" cellspacing="0" align="center"
             width=<%=Constants.TABLEWIDTH_m4%>
          <tr><td class="shopcategory">&nbsp;&nbsp;&nbsp;<bean:write name="sciD" property="categoryPath"/></td>
            <td class="shopcategory" align="right">
    <%                                  if ( sciD.getProduct().getCostCenterId() > 0 ) { %>
            <%= sciD.getProduct().getCostCenterName() %>
    <%                                  }       %>
        </td></tr></table>
      </td></tr>
    <%                          }
          prevCategory = sciD.getCategoryPath();
          } %>
    <%@ include file="f_sitem.jsp" %>
    <%          }
      }
    %>

    </logic:iterate>

                <%      if(null != scdistD.getDistFreightImplied() && 0 < scdistD.getDistFreightImplied().size()) {
    %>
    <bean:define id="freightImplied" name="scdistD" property="distFreightImplied"
      type="java.util.List"/>

      <logic:iterate id="impliedD" name="freightImplied"
         offset="0" indexId="impliedIDX"
         type="com.cleanwise.service.api.value.FreightTableCriteriaData">
    <tr>

      <td class="freighttext">&nbsp;</td>
      <td class="freighttext" colspan="2" align="left">
      <bean:write name="impliedD" property="shortDesc" />:
      </td>
      <td class="freighttext">
        <logic:present name="impliedD" property="freightAmount">
        <bean:define id="frAmt" name="impliedD" property="freightAmount" />
        <%=ClwI18nUtil.getPriceShopping(request,frAmt,"&nbsp;")%>
        </logic:present>
        <logic:notPresent name="impliedD" property="freightAmount">
        <%=ClwI18nUtil.getPriceShopping(request,new java.math.BigDecimal(0),"&nbsp;")%>
        </logic:notPresent>
      </td>
      <td colspan="<%=COL_COUNT-4%>" class="freighttext">&nbsp;</td>
    </tr>
    </logic:iterate>
    <%  }

      if(null != scdistD.getDistFreightOptions() && 0 < scdistD.getDistFreightOptions().size()) {
    %>
    <bean:define id="freightOptions" name="scdistD" property="distFreightOptions"
      type="java.util.List"/>

    <tr>
      <td class="freighttext">&nbsp;</td>
      <td class="freighttext"><app:storeMessage key="shoppingItems.text.shippingMethod"/> </td>
      <td class="freighttext" align="left" colspan="<%=COL_COUNT-3%>">
        <html:select name="CHECKOUT_FORM" property="distFreightVendor">
          <html:options collection="freightOptions" property="freightTableCriteriaId" labelProperty="shortDesc"  />
        </html:select>
        <bean:define id="selectedFrMsg" name="scdistD" property="distSelectableFreightMsg" type="String"/>
        <bean:define id="selectedFrAmt" name="scdistD" property="distSelectableFreightCost" />
    <%          if (null != selectedFrMsg && selectedFrMsg.trim().length() > 0) {  %>
        <bean:write name="scdistD" property="distSelectableFreightMsg" />
    <%          } else {  %>
        <%=ClwI18nUtil.getPriceShopping(request,selectedFrAmt,"&nbsp;")%>
    <%          }  %>
      </td>
      <td class="freighttext"  colspan="<%=COL_COUNT-6%>">&nbsp;</td>
    </tr>
    <%  } else {  %>
      <html:hidden name="CHECKOUT_FORM" property="distFreightVendor" value=""/>
    <%  }  %>
    <%  if(false){  %>
    <tr>
      <td class="freighttext">&nbsp;</td>
      <td class="freighttext" colspan="2" align="left"><app:storeMessage key="shoppingItems.text.tax"/></td>
      <td class="freighttext">
              <logic:present name="scdistD" property="salesTax">
              <bean:define id="salesTax" name="scdistD" property="salesTax" />
              <%=ClwI18nUtil.getPriceShopping(request,salesTax,"&nbsp;")%>
              </logic:present>
              <logic:notPresent name="scdistD" property="salesTax">
              <%=ClwI18nUtil.getPriceShopping(request,new java.math.BigDecimal(0),"&nbsp;")%>
              </logic:notPresent>
      </td>
      <td colspan="<%=COL_COUNT-4%>" class="freighttext">&nbsp;</td>
    </tr>
    <%  }  %>
  </logic:iterate>

<%
  } else {
%>

  <logic:iterate id="sciD" name="SCARTITEMS"
   offset="0" indexId="IDX"
   type="com.cleanwise.service.api.value.ShoppingCartItemData">

     <bean:define id="orderNumber" name="sciD" property="orderNumber"/>
     <bean:define id="itemId" name="sciD" property="product.productId" type="java.lang.Integer" />
     <bean:define id="distName" name="sciD" property="product.catalogDistributorName" />

<% String
quantityEl =     "quantityElement["+IDX+"]",
itemIdsEl =      "itemIdsElement["+IDX+"]",
orderNumbersEl = "orderNumbersElement["+IDX+"]"
;

SiteInventoryInfoView inventoryItemInfo =
  ShopTool.getInventoryItem(request, itemId.intValue());
%>

<% if (checkoutView) {
    if ( sciD.getQuantity() > 0) {
      /* Only include items in the checkout screen if
         the item is being ordered.
      */
    %>

<% if(orderby==Constants.ORDER_BY_CATEGORY) { %>
<% if ( !prevCategory.equals(sciD.getCategoryPath()) ) { %>
<tr> <td class="shopcategory" colspan="<%=COL_COUNT%>">

<table cellpadding="0" cellspacing="0" align="center"
   width="<%=Constants.TABLEWIDTH_m4%>">
<tr><td class="shopcategory"><bean:write name="sciD" property="categoryPath"/></td>
<td class="shopcategory" align="right">
<% if ( sciD.getProduct().getCostCenterId() > 0 ) { %>
<%= sciD.getProduct().getCostCenterName() %>
<% } %>
</td></tr></table>
</td></tr>
<% }
prevCategory = sciD.getCategoryPath();
} %>
<%@ include file="f_sitem.jsp" %>
        <%-- if ( ! checkoutView ) { %>
        <%@ include file="f_sitem_audit.jsp" %>
        <%} --%>
<%} %>

<%
} else {
%>

<% if(orderby==Constants.ORDER_BY_CATEGORY) { %>
<% if( !prevCategory.equals(sciD.getCategoryPath()) ) { %>
<tr> <td class="shopcategory" colspan="<%=COL_COUNT%>">

<table cellpadding="0" cellspacing="0" align="center"
   width=<%=Constants.TABLEWIDTH_m4%>
<tr><td class="shopcategory"><bean:write name="sciD" property="categoryPath"/> </td>
<td class="shopcategory" align="right">
<% if ( sciD.getProduct().getCostCenterId() > 0 ) { %>
<%= sciD.getProduct().getCostCenterName() %>
<% } %>
</td></tr></table>
</td></tr>
<%
 }
   prevCategory = sciD.getCategoryPath();
} %>

<%@ include file="f_sitem.jsp" %>
<%-- @ include file="f_sitem_audit.jsp" --%>

<%} %>

</logic:iterate>

<%
  } // end if(checkoutView) else

%>

<script type="text/javascript" language="JavaScript">
<!--
  var ix = document.getElementById("IDX_0");
  if (ix != null) {
  ix.focus();
  }
  // -->
</script>

</table>




