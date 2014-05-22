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

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>
<!--PAGESRCID=f_og_common_list.jsp -->
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
    int COL_COUNT=0;

    String itemsString =ClwI18nUtil.getShoppingItemsString(request,shoppingCart);
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
int orderby = 0;
if ( tmp_itemorder != null ) {
  orderby = Integer.parseInt(tmp_itemorder);
}


%>

<html:form name="SHOP_FORM" action="/store/shop.do">
<%
//String isGotoAnchor = (String)request.getAttribute("gotoAnchor");
if("true".equals(isGotoAnchor)){ %>
	<body onload="pageScroll()">
<%}else{%>
<body>
<%}%>

<table border="0" align=center CELLSPACING=0 CELLPADDING=0
        width="<%=Constants.TABLEWIDTH%>"
        style="{border-bottom: black 2px solid;}" >

<tr>
    <%if (inventoryShopping) {%>
    <td class="shopcharthead" align="center">
        <%COL_COUNT++;%>&nbsp;
    </td>
    <%}%>

    <logic:equal name="SHOP_FORM" property="allowPurchase" value="true">
        <td class="shopcharthead" align="center">
            <%COL_COUNT++;%><app:storeMessage key="shop.og.table.header.orderQty"/>
        </td>
    </logic:equal>

    <logic:equal name="SHOP_FORM" property="allowPurchase" value="false">
        <td class="shopcharthead"><%COL_COUNT++;%>&nbsp;</td>
    </logic:equal>

        <td class="shopcharthead" align="center">
        <%COL_COUNT++;%><app:storeMessage key="shop.og.table.header.uom"/>
    </td>

        <td class="shopcharthead" align="center"><%COL_COUNT++;%>&nbsp;</td>

    <td class="shopcharthead" align="center">
        <app:storeMessage key="shoppingItems.text.item#"/>
        <%COL_COUNT++;%>
        </td>

    <td class="shopcharthead">
        <%COL_COUNT++;%><center>
            <app:storeMessage key="shop.og.table.header.productName"/>
        </center>
    </td>

        <%--
    <td class="shopcharthead" align="center">
        <%COL_COUNT++;%><app:storeMessage key="shop.og.table.header.size"/>
    </td>

    <td class="shopcharthead" align="center">
        <%COL_COUNT++;%><app:storeMessage key="shop.og.table.header.pack"/>
    </td>



    <% if (!theForm.getAppUser().getUserAccount().isHideItemMfg()) {
         if (RefCodeNames.STORE_TYPE_CD.MANUFACTURER.equals(theForm.getAppUser().getUserStore().getStoreType().getValue())) { %>
           <td class="shopcharthead" align="center"><%COL_COUNT++;%>&nbsp;</td>
        <% } else { %>
           <td class="shopcharthead" align="center"><%COL_COUNT++;%><app:storeMessage key="shop.og.table.header.manuf"/></td>
        <% } %>

    <td class="shopcharthead" align="center">
        <%COL_COUNT++;%><app:storeMessage key="shop.og.table.header.manufSkuNum"/>
    </td>
    <% } %>
        --%>

    <logic:equal name="SHOP_FORM" property="showPrice" value="true">
        <td class="shopcharthead" align="center">
            <%COL_COUNT++;%><app:storeMessage key="shop.og.table.header.price"/>
        </td>
                <%--
        <td class="shopcharthead" align="center">
            <%COL_COUNT++;%><app:storeMessage key="shop.og.table.header.amount"/>
        </td>
                --%>
    </logic:equal>

    <logic:equal name="SHOP_FORM" property="showPrice" value="false">
        <%COL_COUNT++;%><td class="shopcharthead" align="center">&nbsp;</td>
    </logic:equal>

        <td class="shopcharthead" align="center">
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
  <% } else if (inventoryShopping && quickOrderView == false && f_showSelectCol == true) {  %>
    <td class="shopcharthead"><div class="fivemargin">
      <a href="javascript:f_SetChecked(1,'orderSelectBox')"><app:storeMessage key="shoppingItems.text.checkAll"/></a>
      <a href="javascript:f_SetChecked(0,'orderSelectBox')"><app:storeMessage key="shoppingItems.text.clear"/></a>
    </td>
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


<logic:iterate id="item" name="SHOP_FORM"  property="cartItems"
               offset="0" indexId="kidx"
               type="com.cleanwise.service.api.value.ShoppingCartItemData">


<% if (theForm.getOrderBy() == Constants.ORDER_BY_CATEGORY) { %>

<%
    thisItemCategory = item.getCategoryPath();
    if (null == thisItemCategory) {
        thisItemCategory = "";
    }
%>
<%if (firstrow) { %>
<tr>
    <td colspan="<%=COL_COUNT%>" class="shopcategory">
        &nbsp;&nbsp;&nbsp;&nbsp;
        <bean:write name="item" property="categoryPath"/>
    </td>
    <% prevCategory = thisItemCategory; %>
</tr>

<%
    firstrow = false;
} else if (!prevCategory.equals(thisItemCategory)) { %>
<% prevCategory = thisItemCategory; %>
<tr>
    <td colspan="<%=COL_COUNT%>" class="shopcategory">&nbsp;&nbsp;&nbsp;&nbsp;
        <bean:write name="item" property="categoryPath"/>
    </td>
</tr>
<% } %>
<% } %>

<!-- include file="f_oitem.jsp" -->

<!-- START: item display <%=kidx%> -->

<%String itemLink = "shop.do?action=msItem&source=t_itemDetail.jsp&itemId="
   +itemId+"&qty="+item.getQuantity();%>
String styleClass = (((IDX) %2 )==0) ?  "evenRowColor" : "oddRowColor";%>
<tr class="<%=styleClass%>">

<app:displayProd viewOptionEditCartItems="<%=editCartItems%>" viewOptionQuickOrderView="<%=quickOrderView%>" viewOptionAddToCartList="false" viewOptionOrderGuide="false"
	name="sciD" link="<%=itemLink%>" index="<%=IDX%>" inputNameOnHand="<%=\"cartLine[\" + IDX + \"].inventoryQtyOnHandString\"%>" inputNameQuantity="<%= \"cartLine[\" + kidx + \"].quantityString\"%>"/>


</tr>




<!-- END: item display <%=kidx%> -->
</logic:iterate>
</table>

<table   cellpadding="0" cellspacing="0" align="center"  width="<%=Constants.TABLEWIDTH%>" >

  <tr >
  <a name="buttonSection"></a>
    <td colspan="3" align="right">
      <table align = "right" border="0" cellpadding="0" cellspacing="0" >
        <tr>
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

               <% if (!ShopTool.isPhysicalCartAvailable(request)) { %>
                <%-- add to cart --%>

                <td align="right" valign="middle"><img src='<%=ClwCustomizer.getSIP(request,"buttonLeft.png")%>' border="0"></td>
     <td align="center" valign="middle" nowrap class="xpdexGradientButton">
                <% if(ShopTool.isModernInventoryCartAvailable(request) && !(ShopTool.hasDiscretionaryCartAccessOpen(request))){ %>
           <a class="xpdexGradientButton" href="#" onclick="actionMultiSubmit('BBBBBBB', 'og_addToNewXpedxInvCart');"><app:storeMessage key="shoppingCart.text.addToCart"/></a>
                <% } else { %>
                        <a class="xpdexGradientButton" href="#" onclick="actionMultiSubmit('BBBBBBB', 'og_addToCart');"><app:storeMessage key="shoppingCart.text.addToCart"/></a>
                <% } %>
     </td>
     <td align="left" valign="middle"><img src='<%=ClwCustomizer.getSIP(request,"buttonRight.png")%>' border="0"></td>


     <td>&nbsp;</td>

                <%-- checkout --%>

                <td align="right" valign="middle"><img src='<%=ClwCustomizer.getSIP(request,"buttonLeft.png")%>' border="0"></td>
         <td align="center" valign="middle" nowrap class="xpdexGradientButton">
               <a class="xpdexGradientButton" href="#" onclick="actionMultiSubmit('BBBBBBB', 'og_addToCartViewCart');"><app:storeMessage key="global.action.label.checkout"/></a>
         </td>
         <td align="left" valign="middle"><img src='<%=ClwCustomizer.getSIP(request,"buttonRight.png")%>' border="0"></td>
              <% } %>

		</tr>
      </table>
   </td>

  </tr>

</table>
<html:hidden property="action" value="BBBBBBB"/>
<html:hidden property="action" value="CCCCCCC"/>
</body>
</html:form>
