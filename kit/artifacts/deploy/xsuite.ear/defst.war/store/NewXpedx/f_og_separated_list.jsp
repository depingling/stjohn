<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.value.*"%>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Locale" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>
<!--PAGESRCID=f_og_seperated_list.jsp -->
<style type="text/css">
.shopcharthead {
    color: #ffffff;
    font-weight: bold;
    background-color: #333333 ;
}

    font-weight: bold;
    color: #333333;
    background-color: #DFDAFA ;
}



</style>

<script language="JavaScript1.2">
<!--
function f_SetChecked(obj,prop) {
 dml=document.SHOP_FORM;
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

function actionMultiSubmit(actionDef, action) {
 var aaa = document.getElementsByName('action');

 for(ii=aaa.length-1; ii>=0; ii--) {
   var actionObj = aaa[ii];
   if(actionObj.value==actionDef) {
     actionObj.value=action;
     actionObj.form.submit();
     break;
   }
 }
 return false;
 }

function setAndSubmit(fid, vv, value) {
  var aaa = document.forms[fid].elements[vv];
  aaa.value=value;
  aaa.form.submit();
  return false;

}

<%
String isGotoAnchor = (String)request.getAttribute("gotoAnchor");
if("true".equals(isGotoAnchor)){
%>

function getAnchorPosition (anchorName) {
  if (document.layers) {
    var anchor = document.anchors[anchorName];
    return { x: anchor.x, y: anchor.y };
  }
  else if (document.getElementById) {

    var anchor = document.getElementById(anchorName);
	if(anchor==null){
        anchor = document.anchors[anchorName];
	}
    var coords = {x: 0, y: 0 };
    while (anchor) {
      coords.x += anchor.offsetLeft;
      coords.y += anchor.offsetTop;
      anchor = anchor.offsetParent;
    }
    return coords;
  }
}

/* for Internet Explorer */
/*@cc_on @*/
/*@if (@_win32)
  document.write("<script id=__ie_onload defer><\/script>");
  var script = document.getElementById("__ie_onload");
  script.onreadystatechange = function() {
    if (this.readyState == "complete") {
      pageScroll(); // call the onload handler
    }
  };
/*@end @*/

function pageScroll() {
  // quit if this function has already been called
  if (arguments.callee.done) return;

  // flag this function so we don't do the same thing twice
  arguments.callee.done = true;

  // kill the timer
  if (_timer) clearInterval(_timer);

	//alert(getAnchorPosition("buttonSection").y);
    window.scrollTo(100,parseInt(getAnchorPosition("buttonSection").y));
};

/* for Mozilla/Opera9 */
if (document.addEventListener) {
  document.addEventListener("DOMContentLoaded", pageScroll, false);
}



/* for Safari */
if (/WebKit/i.test(navigator.userAgent)) { // sniff
  var _timer = setInterval(function() {
    if (/loaded|complete/.test(document.readyState)) {
      pageScroll(); // call the onload handler
    }
  }, 10);
}

<%}%>
//-->
</script>

<html:form name="SHOP_FORM" action="/store/shop.do">
<%
//String isGotoAnchor = (String)request.getAttribute("gotoAnchor");
if("true".equals(isGotoAnchor)){ %>
	<body onload="pageScroll()">
<%}else{%>
<body>
<%}%>
<bean:define id="theForm" name="SHOP_FORM" type="com.cleanwise.view.forms.UserShopForm"/>
<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>
<%
CleanwiseUser appUser = ShopTool.getCurrentUser(request);
String storeDir=ClwCustomizer.getStoreDir();
    String numberOfIventoryItemsStr = request.getParameter("numberOfIventoryItems");
    int numberOfIventoryItems = Integer.parseInt(numberOfIventoryItemsStr);

    String numberOfRegularItemsStr = request.getParameter("numberOfRegularItems");
    int numberOfRegularItems = Integer.parseInt(numberOfRegularItemsStr);


    String TABIDX_STR = request.getParameter("TABIDX");
    int TABIDX = Integer.parseInt(TABIDX_STR);

    ShoppingCartData shoppingCart = (ShoppingCartData) session.getAttribute(Constants.SHOPPING_CART);
    boolean inventoryShopping = ShopTool.isInventoryShoppingOn(request);

    String recalc_img = IMGPath + "/b_recalculate.gif";
    String upd_inv_img = IMGPath + "/b_updateinventory.gif";

    String prevCategory = "", thisItemCategory = "";
    boolean firstrow = true;
    int rowIdx = 0;

    String itemsString =ClwI18nUtil.getShoppingItemsString(request,shoppingCart);

    int COL_COUNT=0;
        boolean isHideForNewXpdex = true;
        boolean isUser=theForm.isUserOrderGuide(theForm.getOrderGuideId());
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

%>
<% if (inventoryShopping && numberOfIventoryItems > 0) { %>

<table border="0" align=center CELLSPACING=0 CELLPADDING=0
        width="<%=Constants.TABLEWIDTH%>"
        style="{border-bottom: black 2px solid;}" >

<tr>

<logic:equal name="SHOP_FORM" property="allowPurchase" value="true">
            <td class="shopcharthead" align="center">
            <app:storeMessage key="shop.og.table.header.orderQty"/>
             <%COL_COUNT++;%>
            </td>
        </logic:equal>

        <logic:equal name="SHOP_FORM" property="allowPurchase" value="false">
            <td class="shopcharthead"><%COL_COUNT++;%>&nbsp;</td>
        </logic:equal>

                <td class="shopcharthead"><%COL_COUNT++;%>&nbsp;</td>
                <td class="shopcharthead" align="center">
        <app:storeMessage key="shop.og.table.header.uom"/>
        <%COL_COUNT++;%>
        </td>

        <td class="shopcharthead" align="center">
        <app:storeMessage key="shoppingItems.text.item#"/>
        <%COL_COUNT++;%>
        </td>

        <td class="shopcharthead">
            <app:storeMessage key="shop.og.table.header.productName"/>
         <%COL_COUNT++;%>
         </td>
                 <%--
        <td class="shopcharthead" align="center">
        <app:storeMessage key="shop.og.table.header.size"/>
            <%COL_COUNT++;%>
        </td>
        <td class="shopcharthead" align="center">
        <app:storeMessage key="shop.og.table.header.pack"/>
        <%COL_COUNT++;%>
        </td>
                --%>

        <logic:equal name="SHOP_FORM" property="showPrice" value="true">
            <td class="shopcharthead" align="right">
            <app:storeMessage key="shop.og.table.header.price"/>&nbsp;
            </td>
                        <%--
            <td class="shopcharthead" align="center">
            <app:storeMessage key="shop.og.table.header.amount"/>
                <%COL_COUNT++;%>
            </td>
                        --%>
        </logic:equal>

        <logic:equal name="SHOP_FORM" property="showPrice" value="false">
            <td class="shopcharthead" align="center"><%COL_COUNT++;%>
            &nbsp;</td>
        </logic:equal>

                <td class="shopcharthead" align="right"><%COL_COUNT++;%>
                        <app:storeMessage key="shoppingItems.text.total"/>&nbsp;
        </td>

                <% if (isHideForNewXpdex) {%>
        <% if (isUser) { %>
    <td>
     <table cellpadding="0" cellspacing="0" width="100%">
       <tr>
        <td align="center" valign="middle" class="shopcharthead"><div class="fivemargin"><app:storeMessage key="global.action.label.delete"/></div></td>
        <td align="center" valign="top" class="shopcharthead">
          <div class="fivemargin">
            <b><app:storeMessage key="shoppingItems.text.all"/></b><br>
            <input type="checkbox" name="deleteAllFl" value="<%=Boolean.toString(false)%>"  onclick="f_SetChecked(this,'selectBox')"/></td>
          </div>
        </td>
       </tr>
     </table>
         </td>
  <% }else{ %><td class="shopcharthead">&nbsp;</td><% } %>
<% } else { %>
  <% if (isUser) { %>
    <td class="shopcharthead"><div class="fivemargin"><app:storeMessage key="global.action.label.delete"/></div></td>

    <td class="shopcharthead"><div class="fivemargin">
      <a href="javascript:f_SetChecked(1,'orderSelectBox')"><app:storeMessage key="shoppingItems.text.checkAll"/></a>
      <a href="javascript:f_SetChecked(0,'orderSelectBox')"><app:storeMessage key="shoppingItems.text.clear"/></a>
    </td>
	<td class="shopcharthead">&nbsp;</td>
  <% }else{ %>
    <td class="shopcharthead">&nbsp;</td>
  <% } %>

<% } %>

  </tr>

<logic:iterate id="item" name="SHOP_FORM" property="cartItems"
   offset="0" indexId="kidx"
   type="com.cleanwise.service.api.value.ShoppingCartItemData">

     <bean:define id="itemId" name="item" property="product.productId"
       type="java.lang.Integer" />
	   <bean:define id="orderNumber" name="item" property="orderNumber"/>
     <bean:define id="quantityEl" value="<%=\"quantityElement[\"+kidx+\"]\"%>"/>
     <bean:define id="onhandEl" value="<%=\"onhandElement[\"+kidx+\"]\"%>"/>
     <bean:define id="itemIdsEl" value="<%=\"itemIdsElement[\"+kidx+\"]\"%>"/>

<% if (inventoryShopping && item.getIsaInventoryItem()) { %>

<% if(theForm.getOrderBy()==Constants.ORDER_BY_CATEGORY) {

thisItemCategory = Utility.isSet(item.getCustomCategoryName()) ? item.getCustomCategoryName() : item.getCategoryPath();
if ( null == thisItemCategory ) {
  thisItemCategory = "";
}
%>
 <% if (firstrow) { %>
<tr>
<td colspan="<%=COL_COUNT%>" class="shopcategory" width="<%=Constants.TABLEWIDTH%>" height="20px">
                <bean:write name="item" property="categoryPath"/></td>
            <% prevCategory = thisItemCategory; %>
                        <td class="shopcategory">&nbsp;</td>
                        <td class="shopcategory">&nbsp;</td>

</tr>
 <%
firstrow = false;
} else if(! prevCategory.equals(thisItemCategory)) { %>
<% prevCategory = thisItemCategory;%>
<tr>
<td colspan="<%=COL_COUNT%>" class="shopcategory" width="<%=Constants.TABLEWIDTH%>" height="20px">
                <bean:write name="item" property="categoryPath"/></td>
                                <td class="shopcategory">&nbsp;</td>
                                <td class="shopcategory">&nbsp;</td>

</tr>
  <% } %>

<% } %>

<%@ include file="f_oitem.jsp" %>

</tr>

<% } %>
</logic:iterate>
</table>



<% } /* End of inventory items. */ %>

<% if (numberOfRegularItems > 0) { %>

<%COL_COUNT = 0;%>
<table border="0" align=center CELLSPACING=0 CELLPADDING=0
        width="<%=Constants.TABLEWIDTH%>"
        style="{border-bottom: black 2px solid;}" >
<tr>

<logic:equal name="SHOP_FORM" property="allowPurchase" value="true">
            <td class="shopcharthead" align="center">
            <app:storeMessage key="shop.og.table.header.orderQty"/>
             <%COL_COUNT++;%>
            </td>
        </logic:equal>

        <logic:equal name="SHOP_FORM" property="allowPurchase" value="false">
            <td class="shopcharthead"><%COL_COUNT++;%>&nbsp;</td>
        </logic:equal>

                <td class="shopcharthead"><%COL_COUNT++;%>&nbsp;</td>
                <td class="shopcharthead" align="center">
        <app:storeMessage key="shop.og.table.header.uom"/>
        <%COL_COUNT++;%>
        </td>

        <td class="shopcharthead" align="center">
        <app:storeMessage key="shoppingItems.text.item#"/>
        <%COL_COUNT++;%>
        </td>

        <td class="shopcharthead">
            <app:storeMessage key="shop.og.table.header.productName"/>
         <%COL_COUNT++;%>
         </td>
                 <%--
        <td class="shopcharthead" align="center">
        <app:storeMessage key="shop.og.table.header.size"/>
            <%COL_COUNT++;%>
        </td>
        <td class="shopcharthead" align="center">
        <app:storeMessage key="shop.og.table.header.pack"/>
        <%COL_COUNT++;%>
        </td>
                --%>

        <logic:equal name="SHOP_FORM" property="showPrice" value="true">
            <td class="shopcharthead" align="right">
            <app:storeMessage key="shop.og.table.header.price"/>&nbsp;
            </td>
                        <%--
            <td class="shopcharthead" align="center">
            <app:storeMessage key="shop.og.table.header.amount"/>
                <%COL_COUNT++;%>
            </td>
                        --%>
        </logic:equal>

        <logic:equal name="SHOP_FORM" property="showPrice" value="false">
            <td class="shopcharthead" align="center"><%COL_COUNT++;%>
            &nbsp;</td>
        </logic:equal>

                <td class="shopcharthead" align="right"><%COL_COUNT++;%>
                        <app:storeMessage key="shoppingItems.text.total"/>&nbsp;
        </td>

                <% if (isHideForNewXpdex) {%>
        <% if (isUser) { %>
    <td>
     <table cellpadding="0" cellspacing="0" width="100%">
       <tr>
        <td align="center" valign="middle" class="shopcharthead"><div class="fivemargin"><app:storeMessage key="global.action.label.delete"/></div></td>
        <td align="center" valign="top" class="shopcharthead"><%COL_COUNT++;%>
          <div class="fivemargin">
            <b><app:storeMessage key="shoppingItems.text.all"/></b><br>
            <input type="checkbox" name="deleteAllFl" value="<%=Boolean.toString(false)%>"  onclick="f_SetChecked(this,'selectBox')"/></td>
          </div>
        </td>
       </tr>
     </table>
         </td>
  <% }else{ %><td class="shopcharthead"><%COL_COUNT++;%>&nbsp;</td><% } %>
<% } else { %>
  <% if (isUser) { %>
    <td class="shopcharthead"><div class="fivemargin"><app:storeMessage key="global.action.label.delete"/><%COL_COUNT++;%></div></td>

    <td class="shopcharthead"><div class="fivemargin"><%COL_COUNT++;%>
      <a href="javascript:f_SetChecked(1,'orderSelectBox')"><app:storeMessage key="shoppingItems.text.checkAll"/></a>
      <a href="javascript:f_SetChecked(0,'orderSelectBox')"><app:storeMessage key="shoppingItems.text.clear"/></a>
    </td>
	<td class="shopcharthead">&nbsp;</td>
  <% }else{ %>
    <td class="shopcharthead">&nbsp;</td>
  <% } %>

<% } %>

  </tr>

<%
/* List non-inventory items. */
 firstrow = true;
 rowIdx = 0;
 prevCategory = "";
%>


<logic:iterate id="item" name="SHOP_FORM" property="cartItems"
   offset="0" indexId="kidx"
   type="com.cleanwise.service.api.value.ShoppingCartItemData">

<%
int itemId = item.getItemId();
SiteInventoryInfoView inventoryItemInfo = ShopTool.getInventoryItem(request, itemId);
   boolean modernInvShopping = ShopTool.isModernInventoryShopping(request);
   %>
<% int colIdx=0; %>
<% if (item.getIsaInventoryItem() == false) { %>

<% if(theForm.getOrderBy()==Constants.ORDER_BY_CATEGORY) { %>
<%
thisItemCategory = Utility.isSet(item.getCustomCategoryName()) ? item.getCustomCategoryName() : item.getCategoryPath();
if ( null == thisItemCategory ) {
  thisItemCategory = "";
}
%>
  <% if (firstrow) { %>
<tr>
<td colspan="<%=COL_COUNT%>" class="shopcategory" width="<%=Constants.TABLEWIDTH%>" height="20px">
                <bean:write name="item" property="categoryPath"/></td>
            <% prevCategory = thisItemCategory; %>
                        <td class="shopcategory">&nbsp;</td>

</tr>
 <%
firstrow = false;
} else if(! prevCategory.equals(thisItemCategory)) { %>
<% prevCategory = thisItemCategory;%>
<tr>
<td colspan="<%=COL_COUNT%>" class="shopcategory" width="<%=Constants.TABLEWIDTH%>" height="20px">
                <bean:write name="item" property="categoryPath"/></td>
                                <td class="shopcategory">&nbsp;</td>

</tr>
  <% } %>
<% } %>



<!-- START: item display <%=kidx%> -->


<%String styleClass = (((rowIdx++) %2 )==0) ?  "evenRowColor" : "oddRowColor";%>
<tr class="<%=styleClass%>">
<%String itemLink = "shop.do?action=msItem&source=t_itemDetail.jsp&itemId="
   +itemId+"&qty="+item.getQuantity();%>
	<app:displayProd viewOptionEditCartItems="<%=editCartItems%>" viewOptionQuickOrderView="<%=quickOrderView%>" viewOptionAddToCartList="false"  viewOptionOrderGuide="true"
	name="item" link="<%=itemLink%>" index="<%=kidx%>" inputNameOnHand="<%=\"cartLine[\" + kidx + \"].inventoryQtyOnHandString\"%>" inputNameQuantity="<%= \"cartLine[\" + kidx + \"].quantityString\"%>"/>


</tr>

<%--
<td class="text" align="center"><div >
<bean:define id="orderNumber" name="item" property="orderNumber"/>
  <% if(isUser) { %>
 <% if (editCartItems ) { %>
    <% if ( inventoryItemInfo != null && inventoryItemInfo.getItemId() > 0 && !modernInvShopping ) { %>
    &nbsp;
    <% } else { %>
    <html:multibox name="SHOP_FORM" property="selectBox"
      value="<%=\"\"+(((Integer)itemId).longValue()*10000+((Integer)orderNumber).longValue())%>"
         tabindex='<%=String.valueOf(kidx.intValue() + 2000)%>'
    />
    <% } %>

        <% } else if ( appUser.canMakePurchases() && quickOrderView == false &&
                    ShopTool.isInventoryShoppingOn(request)&& !modernInvShopping) { %>
    <html:multibox name="SHOP_FORM" property="orderSelectBox"
      value="<%=String.valueOf(item.getProduct().getProductId())%>"
      tabindex='<%=String.valueOf(kidx.intValue() + 3000)%>'
    />
 <% } %>
 <% } %>
</div></td>
--%>
     </tr> <% /* End of line item */ %>



<!-- END: item display <%=kidx%> -->

<% } %>

   </logic:iterate>
</table>
<br>
<% } %>
<table   cellpadding="0" cellspacing="0" align="center"  width="<%=Constants.TABLEWIDTH%>" >
  <tr >
  <a name="buttonSection"></a>
    <td colspan="3" align="right">
      <table align = "right" border="0" cellpadding="0" cellspacing="0" >
                <%-- delete selected --%>
                <% if(isUser){ %>
                <td align="right" valign="middle"><img src='<%=ClwCustomizer.getSIP(request,"buttonLeft.png")%>' border="0"></td>
       <td align="center" valign="middle" nowrap class="xpdexGradientButton">
           <a class="xpdexGradientButton" href="#" onclick="actionMultiSubmit('BBBBBBB','removeSelected');"><app:storeMessage key="shoppingCart.text.deleteSelected"/></a>
       </td>
       <td align="left" valign="middle"><img src='<%=ClwCustomizer.getSIP(request,"buttonRight.png")%>' border="0"></td>
       <td>&nbsp;</td>
           <% } %>
                <%-- update list --%>
                <% if(isUser){ %>
                <td align="right" valign="middle"><img src='<%=ClwCustomizer.getSIP(request,"buttonLeft.png")%>' border="0"></td>
     <td align="center" valign="middle" nowrap class="xpdexGradientButton">
           <a class="xpdexGradientButton" href="#" onclick="actionMultiSubmit('BBBBBBB','updateList');"><app:storeMessage key="shoppingCart.text.updateList"/></a>
     </td>
     <td align="left" valign="middle"><img src='<%=ClwCustomizer.getSIP(request,"buttonRight.png")%>' border="0"></td>
     <td>&nbsp;</td>
                <% } %>

                <%-- add to cart --%>
                <td align="right" valign="middle"><img src='<%=ClwCustomizer.getSIP(request,"buttonLeft.png")%>' border="0"></td>
     <td align="center" valign="middle" nowrap class="xpdexGradientButton">
                <% if(ShopTool.isModernInventoryCartAvailable(request) && !(ShopTool.hasDiscretionaryCartAccessOpen(request))){ %>
           <a class="xpdexGradientButton" href="#" onclick="actionMultiSubmit('BBBBBBB', 'og_addToNewXpedxInvCart');">Add to Cart</a>
                <% } else { %>
                        <a class="xpdexGradientButton" href="#" onclick="actionMultiSubmit('BBBBBBB', 'og_addToCart');">Add to Cart</a>
                <% } %>
     </td>
     <td align="left" valign="middle"><img src='<%=ClwCustomizer.getSIP(request,"buttonRight.png")%>' border="0"></td>
     <td>&nbsp;</td>

                <%-- checkout --%>

                <td align="right" valign="middle"><img src='<%=ClwCustomizer.getSIP(request,"buttonLeft.png")%>' border="0"></td>
         <td align="center" valign="middle" nowrap class="xpdexGradientButton">
               <%--<a class="xpdexGradientButton" href="../store/checkout.do"><app:storeMessage key="global.action.label.checkout"/></a>--%>
			   <a class="xpdexGradientButton" href="#" onclick="actionMultiSubmit('BBBBBBB', 'og_addToCartViewCart');"><app:storeMessage key="global.action.label.checkout"/></a>
         </td>
         <td align="left" valign="middle"><img src='<%=ClwCustomizer.getSIP(request,"buttonRight.png")%>' border="0"></td>

      </table>
   </td>

  </tr>

</table>
<html:hidden property="action" value="BBBBBBB"/>
<html:hidden property="action" value="CCCCCCC"/>
</body>
</html:form>
