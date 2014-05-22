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
<%@ page import="java.util.HashMap" %>

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

  int  COL_COUNT = 13;

  int rowCount = 0;
  ShoppingCartData shoppingCartD = ShopTool.getCurrentShoppingCart(request);
  int storeId = appUser.getUserStore().getStoreId();
  ShoppingCartDistDataVector cartDistributorV =
      new ShoppingCartDistDataVector(shoppingCartD.getItems(), storeId, appUser.getSite().getContractData());
  BigDecimal totalDiscountCost = new BigDecimal(0);
  totalDiscountCost = totalDiscountCost.setScale(2, BigDecimal.ROUND_HALF_UP);
  totalDiscountCost = totalDiscountCost.add(cartDistributorV.getTotalDiscountCost());
  HashMap discountPerDistHM = cartDistributorV.getDiscountCostPerDist();
  //Create counter for the discountPerDist on the Request Level (f_shoppingItems.jsp can be called several times)
  Integer ct = (Integer)request.getAttribute("discountCounter");
  if (ct == null) {
      ct = new Integer(0);
  }
  request.setAttribute("discountCounter", ct); // new code

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


  <td width=15 align="left" class="shopcharthead">&nbsp;</td>
  <!-- td width=30 align="center" class="shopcharthead"><app:storeMessage key="shoppingItems.text.par"/></td-->
  <td class="shopcharthead">&nbsp;</td>
  <td width=30 align="center" class="shopcharthead"><app:storeMessage key="shoppingItems.text.onHand"/></td>
<% }

/* Remove the select column */
if (f_showSelectCol == false ||
    editCartItems == false ||
    quickOrderView ) { COL_COUNT = COL_COUNT - 1; }

%>
  <td align="center" class="shopcharthead"><app:storeMessage key="shoppingItems.text.orderQty"/></td>
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
  <td class="shopcharthead"><div class="fivemargin"><app:storeMessage key="shoppingItems.text.manufacturer"/></div></td>

  <td class="shopcharthead"><div class="fivemargin"><app:storeMessage key="shoppingItems.text.mfgSkuNum"/></div></td>
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
        <bean:define id="checkoutForm" name="CHECKOUT_FORM" type="com.cleanwise.view.forms.CheckoutForm"/>
    <bean:define id="SCartDistV" name="CHECKOUT_FORM" property="cartDistributors" type="ShoppingCartDistDataVector"/>

    <logic:iterate id="scdistD" name="SCartDistV"
       offset="0" indexId="DISTIDX"
       type="com.cleanwise.service.api.value.ShoppingCartDistData">

    <bean:define id="distName" name="scdistD" property="distributorName"/>
    <bean:define id="distItems" name="scdistD" property="shoppingCartItems"
      type="java.util.List"/>

    <tr>
    <%
      boolean allowPoByVender = ("true".equals(request.getParameter("allowPoByVender")))?true:false;      
      if(!allowPoByVender){%>
        <td class="shopcharthead" colspan="<%=COL_COUNT%>">
                &nbsp;<bean:write name="scdistD" property="distributorName"/>
        </td>
    <% } else { %>
      <td class="shopcharthead" colspan="<%=COL_COUNT-10%>">
        &nbsp;<bean:write name="scdistD" property="distributorName"/>
      </td>
      <td class="shopcharthead" colspan="6"> 
            <b><app:storeMessage key="shop.checkout.text.poNumber"/>  </b><br>
            <html:text name="CHECKOUT_FORM" property='<%="cartDistributors[" + DISTIDX + "].poNumber"%>' size="20" maxlength="22" />
            <%if(appUser.getPoNumRequired()){%>
                    <font color="red">*</font>
                <%}%>
                <br>
      </td>
      <td class="shopcharthead" colspan="4">
        &nbsp;
      </td>
    <% } %>
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
         type="com.cleanwise.service.api.value.FreightTableCriteriaData"><%
         boolean toBeDeterminate = RefCodeNames.FREIGHT_CRITERIA_TYPE_CD.ESTIMATE.equals(impliedD.getFreightCriteriaTypeCd());
         BigDecimal freightAmount = impliedD.getFreightAmount();
         BigDecimal handlingAmount = impliedD.getHandlingAmount();
         if (handlingAmount == null) {
             handlingAmount = new BigDecimal(0);
         } else {
             handlingAmount = handlingAmount.setScale(2,BigDecimal.ROUND_HALF_UP);
         }
         if (freightAmount != null) {
             handlingAmount = handlingAmount.add(freightAmount.setScale(2,BigDecimal.ROUND_HALF_UP));    
         }
         if (toBeDeterminate || handlingAmount.compareTo(BigDecimal.ZERO) != 0) {
%>
    <tr>
      <td class="freighttext">&nbsp;</td>
      <td class="freighttext" colspan="2" align="left">
      <bean:write name="impliedD" property="shortDesc" />:
      </td>
      <td class="freighttext">
            <logic:equal name="impliedD" property="freightCriteriaTypeCd" value="<%=RefCodeNames.FREIGHT_CRITERIA_TYPE_CD.ESTIMATE%>">
                <%--should be i18n, but this is hard coded in the ShoppingCartDistData object as well.  Do both at same time to be consistent--%>
                        To Be Determined
                </logic:equal>
                <logic:notEqual name="impliedD" property="freightCriteriaTypeCd" value="<%=RefCodeNames.FREIGHT_CRITERIA_TYPE_CD.ESTIMATE%>"><%
                %><%=ClwI18nUtil.getPriceShopping(request,handlingAmount,"&nbsp;")%>
                </logic:notEqual>
      </td>
      <td colspan="<%=COL_COUNT-4%>" class="freighttext">&nbsp;</td>
    </tr><%} %>
    </logic:iterate>
    <%  }
    if (null != scdistD.getDistFuelSurchargeList() && 0 < scdistD.getDistFuelSurchargeList().size()) {%>
        <bean:define id="fuelSurchargeList" name="scdistD" property="distFuelSurchargeList" type="java.util.List"/>
            <logic:iterate id="fuelSurchargeCriteria" name="fuelSurchargeList" offset="0" indexId="impliedIDX" type="com.cleanwise.service.api.value.FreightTableCriteriaData"><%
        boolean toBeDetermined = RefCodeNames.FREIGHT_CRITERIA_TYPE_CD.ESTIMATE.equals(fuelSurchargeCriteria.getFreightCriteriaTypeCd());
        BigDecimal fuelSurchargeAmt = fuelSurchargeCriteria.getHandlingAmount();
        if (toBeDetermined || (fuelSurchargeAmt != null && fuelSurchargeAmt.compareTo(BigDecimal.ZERO) != 0)) {
%>        <tr>
        <td class="freighttext">&nbsp;</td>
            <td class="freighttext" colspan="2" align="left">
                <bean:write name="fuelSurchargeCriteria" property="shortDesc" />:
            </td>
            <td class="freighttext">
                <logic:equal name="fuelSurchargeCriteria" property="freightCriteriaTypeCd" value="<%=RefCodeNames.FREIGHT_CRITERIA_TYPE_CD.ESTIMATE%>">
                    <%--should be i18n, but this is hard coded in the ShoppingCartDistData object as well.  Do both at same time to be consistent--%>
To Be Determined
                </logic:equal>
                <logic:notEqual name="fuelSurchargeCriteria" property="freightCriteriaTypeCd" value="<%=RefCodeNames.FREIGHT_CRITERIA_TYPE_CD.ESTIMATE%>">
<%=ClwI18nUtil.getPriceShopping(request, fuelSurchargeAmt, "&nbsp;")%>
                </logic:notEqual>
            </td>
            <td colspan="<%=COL_COUNT-4%>" class="freighttext">&nbsp;</td>
        </tr><%} %>
        </logic:iterate>
    <%
    }
    if (null != scdistD.getDistSmallOrderFeeList() && 0 < scdistD.getDistSmallOrderFeeList().size()) {
    %>
        <bean:define id="smallOrderFeeList" name="scdistD" property="distSmallOrderFeeList" type="java.util.List"/>
        <logic:iterate id="smallOrderFeeCriteria" name="smallOrderFeeList" offset="0" indexId="impliedIDX" type="com.cleanwise.service.api.value.FreightTableCriteriaData"><%
        boolean toBeDetermined = RefCodeNames.FREIGHT_CRITERIA_TYPE_CD.ESTIMATE.equals(smallOrderFeeCriteria.getFreightCriteriaTypeCd());
        BigDecimal smallOrderFeeAmt = smallOrderFeeCriteria.getHandlingAmount();
        if (toBeDetermined || (smallOrderFeeAmt != null && smallOrderFeeAmt.compareTo(BigDecimal.ZERO) != 0)) { 
%>        <tr>
            <td class="freighttext">&nbsp;</td>
            <td class="freighttext" colspan="2" align="left">
                <bean:write name="smallOrderFeeCriteria" property="shortDesc" />:
            </td>
            <td class="freighttext">
                <logic:equal name="smallOrderFeeCriteria" property="freightCriteriaTypeCd" value="<%=RefCodeNames.FREIGHT_CRITERIA_TYPE_CD.ESTIMATE%>">
                    <%--should be i18n, but this is hard coded in the ShoppingCartDistData object as well.  Do both at same time to be consistent--%>
To Be Determined
                </logic:equal>
                <logic:notEqual name="smallOrderFeeCriteria" property="freightCriteriaTypeCd" value="<%=RefCodeNames.FREIGHT_CRITERIA_TYPE_CD.ESTIMATE%>">
<%=ClwI18nUtil.getPriceShopping(request, smallOrderFeeAmt, "&nbsp;")%>
                </logic:notEqual>
            </td>
            <td colspan="<%=COL_COUNT-4%>" class="freighttext">&nbsp;</td>
        </tr><%} %>
        </logic:iterate>
    <%
    }
    %>

<!-- Added for Discount: Begin -->
   <%if(null != scdistD.getDistDiscountImplied() && 0 < scdistD.getDistDiscountImplied().size()) {
        int frTblId = scdistD.getFrTblId();
        int distId = scdistD.getDistId(frTblId); 
        Integer distIdInt = new Integer( distId );
        BigDecimal distIdBD = new BigDecimal(0);
        distIdBD = (BigDecimal)discountPerDistHM.get(distIdInt);
   %>
   <%  if (totalDiscountCost != null) {// && totalDiscountCost.compareTo(new BigDecimal(0))!=0 ) { %>
    <bean:define id="chtForm" name="CHECKOUT_FORM" type="com.cleanwise.view.forms.CheckoutForm"/>
    <% BigDecimal zeroValue = new BigDecimal(0); 
     if ( totalDiscountCost.compareTo(zeroValue)>0 ) {         
        totalDiscountCost = totalDiscountCost.negate(); 
     } 
     totalDiscountCost = totalDiscountCost.setScale(2, BigDecimal.ROUND_HALF_UP);
     chtForm.setDiscountAmt(totalDiscountCost);
     chtForm.setDiscountAmtPerDist(discountPerDistHM); 
     %>
     <bean:define id="discountImplied" name="scdistD" property="distDiscountImplied"
      type="java.util.List"/>

      <logic:iterate id="impliedDisc" name="discountImplied"
         offset="0" indexId="impliedIDX"
         type="com.cleanwise.service.api.value.FreightTableCriteriaData"><%
         if (distIdBD != null && distIdBD.compareTo(BigDecimal.ZERO) != 0) {
%>    <tr>
      <td class="freighttext">&nbsp;</td>
      <td class="freighttext" colspan="2" align="left">
      <% 
           ct = (Integer)request.getAttribute("discountCounter");
           int cti = ct.intValue(); //convert ct Integer Onject into primitive int
      %>
      <% //BigDecimal dAmt2 = (BigDecimal) discountPerDist.get(cti); %>
      <% //BigDecimal zeroValue1 = new BigDecimal(0);  %>
      <bean:write name="impliedDisc" property="shortDesc" />: 
      <% 
         ct = new Integer(cti+1); //increment int cti and convert it into Integer Object ct
         request.setAttribute("discountCounter", ct);
      %>
      </td>
      <td class="freighttext" colspan="8">
                <%--logic:equal name="impliedDisc" property="freightCriteriaTypeCd" value="<%=RefCodeNames.FREIGHT_CRITERIA_TYPE_CD.DOLLARS%>"--%>
                <logic:present name="impliedDisc" property="discount">
                <bean:define id="disAmt" name="impliedDisc" property="discount" />
                                
                <%--
                 String dAmt1 = disAmt.toString(); // old code
                 BigDecimal dAmt2 = new BigDecimal(dAmt1); 
                 BigDecimal zeroValue1 = new BigDecimal(0); 
                 if ( dAmt2.compareTo(zeroValue1)>0 ) {        
                    dAmt2 = dAmt2.negate(); 
                 } 
                 //dAmt2 = dAmt2.setScale(2, BigDecimal.ROUND_HALF_UP); // old code
                 --%>            
                 <%--//=ClwI18nUtil.getPriceShopping(request,dAmt2,"&nbsp;")--%>
                 <% /*** new code for Discount in DOLLARS and PERCENTAGE-DOLLARS ***/ %>
                 <% //BigDecimal dAmt2 = (BigDecimal) discountPerDist.get(cti); %>
                 <% BigDecimal dAmt2 = distIdBD; %>
                 <% if (distIdBD != null && distIdBD.compareTo(BigDecimal.ZERO) != 0) { %>
                   <% BigDecimal zeroValue1 = new BigDecimal(0); 
                   if ( dAmt2.compareTo(zeroValue1)>0 ) {          
                     dAmt2 = dAmt2.negate(); 
                    } 
                    dAmt2 = dAmt2.setScale(2, BigDecimal.ROUND_HALF_UP);
                    %>            
                    <%=ClwI18nUtil.getPriceShopping(request,dAmt2,"&nbsp;")%>
                <% } %> 
                </logic:present>
                <logic:notPresent name="impliedDisc" property="discount">
                <% //=ClwI18nUtil.getPriceShopping(request,new java.math.BigDecimal(0),"&nbsp;")%>
                </logic:notPresent>
                <%--/logic:equal--%>
      </td>
      <td colspan="<%=COL_COUNT-4%>" class="freighttext">&nbsp;</td>
    </tr><%} %>
    </logic:iterate>
    <%  } %>    
   <%  } %>
    <!-- Added for Discount: End -->
<%
      if(null != scdistD.getDistFreightOptions() && 0 < scdistD.getDistFreightOptions().size()) {
    %>
    <bean:define id="freightOptions" name="scdistD" property="distFreightOptions"
      type="java.util.List"/>

    <tr>
      <td class="freighttext">&nbsp;</td>
      <td class="freighttext"><app:storeMessage key="shoppingItems.text.shippingCost"/> </td>
      <td class="freighttext" align="left" colspan="<%=COL_COUNT-3%>">
        <html:select name="CHECKOUT_FORM" property="distFreightVendor">
          <html:option value="" />
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
        <% if ( ! checkoutView ) { %>
        <%@ include file="f_sitem_audit.jsp" %>
        <%} %>
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
<%@ include file="f_sitem_audit.jsp" %>

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




