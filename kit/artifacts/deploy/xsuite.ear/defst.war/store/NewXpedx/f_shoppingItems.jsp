<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.view.utils.ShopTool" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.APIAccess" %>
<%@ page import="com.cleanwise.service.api.*" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.session.*" %>
<%@ page import="com.cleanwise.service.api.util.Utility"%>

<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.math.BigDecimal" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>
<!--PAGESRCID=f_shoppingItems.jsp -->
<script language="JavaScript1.2">
<!--
function f_SetChecked(obj,prop) {
 dml=document.SHOPPING_CART_FORM;
 len = dml.elements.length;
 var val = 0;
 if (obj.checked){
   val=1;
 }
 var i=0;
 for( i=0 ; i<len ; i++) {
   if (dml.elements[i].name==prop && dml.elements[i].disabled!=true) {
     dml.elements[i].checked=val;
   }
 }
}

//-->
</script>

<style type="text/css">
.shopcharthead {
    color: #ffffff;
    font-weight: bold;
    background-color: #333333 ;
}
.shopcategory {

    font-weight: bold;
    color: #333333;
    background-color: #CCCCFF ;
}



</style>


<bean:define id="theForm" name="SHOPPING_CART_FORM"
      type="com.cleanwise.view.forms.ShoppingCartForm" scope="session"/>



<%

CleanwiseUser appUser = ShopTool.getCurrentUser(request);
SiteData thisSite = appUser.getSite();

String prevGroup = "";
boolean isHideForNewXpdex = true;
boolean withBudget = false;  //sciD.getProduct().getCostCenterId() > 0
boolean isPendingOrderBudjet = Utility.isTrue(request.getParameter("isPendingOrderBudjet"));

java.math.BigDecimal allocatedTotal = new BigDecimal(0.00);
java.math.BigDecimal spentTotal = new BigDecimal(0.00);
java.math.BigDecimal totWCartTotal = new BigDecimal(0.00);

//------ determine the type of Shopping Cart : with budget or not ------------
CostCenterDataVector costCenters = ShopTool.getSiteCostCenters(request);
//withBudget = (costCenters != null && costCenters.size() > 1);
withBudget = thisSite.hasBudgets();

//----------------------------------------------------------------------------
boolean
  quickOrderView = false,
  shoppingCartView = false,
  checkoutView = false,
  editCartItems = true ,
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
else shoppingCartView = true;

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

  String showDistInventory = ShopTool.getShowDistInventoryCode(request);


  int rowCount = 0;
%>

<app:defineProdColumnCount id="colCountInt" viewOptionEditCartItems="<%=editCartItems%>" viewOptionQuickOrderView="<%=quickOrderView%>" viewOptionAddToCartList="false" viewOptionOrderGuide="false" viewOptionShoppingCart="<%=shoppingCartView%>" viewOptionCheckout="<%=checkoutView%>"/>
<%int COL_COUNT = ((Integer)pageContext.getAttribute("colCountInt")).intValue();%>

<table cellpadding="0" cellspacing="0" align="center" width="100%"  border="0">

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




 <%--------------------------------- HEADER -----------------------------------------%>
<tr>
   <app:displayProdHeader viewOptionEditCartItems="<%=editCartItems%>"
   viewOptionQuickOrderView="<%=quickOrderView%>" viewOptionAddToCartList="false" viewOptionOrderGuide="false" viewOptionShoppingCart="<%=shoppingCartView%>" viewOptionCheckout="<%=checkoutView%>"
   onClickDeleteAllCheckBox="f_SetChecked(this,'selectBox')"/>
</tr>
 <%-----------------------------------------------------------------------------------------%>

  <tr><td colspan="<%=COL_COUNT%>" class="tableoutline"><img src="/images/cw_spacer.gif" height="1"></td></tr>

  <bean:define id="SCARTITEMS" name="SHOPPING_CART_FORM" property="cartItems" type="java.util.List"/>

  <%
  if ( withBudget ) {
    shoppingCart.orderByCostCenter(SCARTITEMS);
  }else{
    shoppingCart.orderByCategory(SCARTITEMS);
  }
%>


<%
    boolean isaMLAStore = RefCodeNames.STORE_TYPE_CD.MLA.equals(appUser.getUserStore().getStoreType().getValue());
    editCartItems = editCartItems && !(checkoutView && ! isaMLAStore);
  // for checkoutView on store type other than MLA, we need display distributor info
  // (which items belong to which distributor)
  if (true) {
%>
<%--  if (checkoutView && ! isaMLAStore) {
%>
   <%@ include file="f_sitemDistr.jsp" %>
<%
  } else {
--%>
<% CostCenterData wrk = null;
    int centerId = 0;
    String centerName = "";
    CostCenterData costCenterData = null;
%>
<% if (!withBudget) {%>
<%-----------------------------CART ITEMS  loop ----------------------------------%>
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

SiteInventoryInfoView inventoryItemInfo = ShopTool.getInventoryItem(request, itemId.intValue());

%>
  <% if( !prevGroup.equals(sciD.getCategoryPath()) ) {
     rowCount=0;
 %>
     <tr> <td class="shopcategory" colspan="<%=COL_COUNT%>">
       <table cellpadding="0" cellspacing="0" align="center"   width="100%">
         <tr><td class="shopcategory">&nbsp;<bean:write name="sciD" property="categoryPath"/> </td>
           <%--
           <td class="shopcategory" align="right">
             <% if ( sciD.getProduct().getCostCenterId() > 0 ) { %>
             <%= sciD.getProduct().getCostCenterName() %>
             <% } %>
           </td>
           --%>
         </tr>
       </table>
     </td></tr>
    <%
    }
    prevGroup = sciD.getCategoryPath();
    %>
  <%@ include file="f_sitem.jsp" %>
  <% if (!isHideForNewXpdex) {%>
     <%@ include file="f_sitem_audit.jsp" %>
  <% } %>
</logic:iterate>
<%} // end if (!withBudget) both for checkout or not view %>
<%-----------------------end of SCARTITEM without budget ----------------------------------%>
<% if (withBudget) {%>
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
  %>
  <%
  SiteInventoryInfoView inventoryItemInfo = ShopTool.getInventoryItem(request, itemId.intValue());

  boolean isNewGroupHeader =(sciD.getProduct().getCostCenterId() == 0 )
                     ? (!prevGroup.equals(sciD.getCategoryPath()))
                     : (!prevGroup.equals(sciD.getProduct().getCostCenterName()));

  if( isNewGroupHeader ) {

    int itemCenterId = sciD.getProduct().getCostCenterId();
    String itemCenterName = sciD.getProduct().getCostCenterName();
    if (itemCenterId != 0){
      Iterator iter = costCenters.iterator();
      while (iter.hasNext()) {
        //   calculation cost center attributes
        costCenterData = (CostCenterData) iter.next();
        centerId   = costCenterData.getCostCenterId();
        centerName = costCenterData.getShortDesc();

        if(itemCenterName.compareTo(centerName) == 0) {
          //display cost center group title for current shopping cart item
          wrk = costCenterData;
          break;
        }
        if(itemCenterName.compareTo(centerName) > 0) {
          //No Items in the shopping cart for this cost center
          if ( (wrk == null) ||((wrk != null) && (centerName.compareTo(wrk.getShortDesc()) > 0))){
            %>
<%rowCount = 0;%>
            <%@ include file="f_groupTitleInc.jsp"  %>
            <%
            String styleClass = (((rowCount++) %2 )==0) ?  "evenRowColor" : "oddRowColor";
            %>
            <tr class="<%=styleClass%>" >
              <td  style="padding-left:20px" colspan="<%=COL_COUNT%>"><app:storeMessage key="shoppingItems.text.noItemsForCostCenter"/></td>
            </tr>
            <%
            }
          continue;
        }
        break;
      }
    } %>
<%rowCount = 0;%>
     <%@ include file="f_groupTitleInc.jsp"  %>
  <% }%>


<%@ include file="f_sitem.jsp" %>
<% if (!isHideForNewXpdex) {%>
   <%@ include file="f_sitem_audit.jsp" %>
<% } %>

</logic:iterate>
<%
String maxVal = (costCenters == null || costCenters.isEmpty()) ? "" : ((CostCenterData)costCenters.get(costCenters.size()-1)).getShortDesc();
ShoppingCartItemData sciD = null;
   if ((wrk != null) && (maxVal.compareTo( wrk.getShortDesc() )>0)) {
     for (int i = 0; costCenters != null && i < costCenters.size(); i++) {
       costCenterData = (CostCenterData)costCenters.get(i);
       centerId = costCenterData.getCostCenterId();
       centerName = costCenterData.getShortDesc();
       if (centerName.compareTo(wrk.getShortDesc()) > 0) { %>
<%rowCount = 0;%>
       <%@ include file="f_groupTitleInc.jsp"  %>
       <% String styleClass = (((rowCount++) %2 )==0) ?  "evenRowColor" : "oddRowColor"; %>
       <tr class="<%=styleClass%>" >
         <td  style="padding-left:20px" colspan="<%=COL_COUNT%>"><app:storeMessage key="shoppingItems.text.noItemsForCostCenter"/></td>
       </tr>
       <% }
       }
     }
 %>

<% } // end if (withBudget)%>
<% } %>

<script type="text/javascript" language="JavaScript">
<!--
  var ix = document.getElementById("IDX_0");
  if (ix != null) {
  ix.focus();
  }
  // -->
</script>

</table>




