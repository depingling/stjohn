
<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.value.*"%>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Locale" %>


<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>


<%
String requestNumber =  String.valueOf(System.currentTimeMillis());
int TABIDX = 0;
%>


<bean:define id="theForm" name="SHOP_FORM" type="com.cleanwise.view.forms.UserShopForm"/>
<%
   String requestToken =  ""+theForm.getRequestToken();
   ShoppingCartData shoppingCart = (ShoppingCartData) session.getAttribute(Constants.SHOPPING_CART);
   String itemsString =ClwI18nUtil.getShoppingItemsString(request,shoppingCart);
   boolean inventoryShopping = ShopTool.isInventoryShoppingOn(request);
%>

<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>


<script language="JavaScript1.2">
<!--

fmtprt= new Image();
fmtprt.src="<%=IMGPath%>/printerfriendly.gif";

fmtexc= new Image();
fmtexc.src="<%=IMGPath%>/excelformat.gif";

function viewPrinterFriendly() {
  var loc = "printerFriendlyOrderGuide.do?action=pdfPrint";
  if ( document.Personalized.elements[0].checked == true ||
       document.Personalized.elements[0].value == "showInfo" ) {
     loc = "printerFriendlyOrderGuidePersonalized.do?action=pdfPrintPers";
  }
  prtwin = window.open(loc,"printer_friendly",
    "menubar=yes,resizable=yes,scrollbars=yes,toolbar=yes,status=yes,height=500, width=775,left=100,top=165");
  prtwin.focus();

  return false;
}

function viewExcelFormat() {
  var loc = "printerFriendlyOrderGuide.do?action=excelPrint";
  if ( document.Personalized.elements[0].checked == true ||
       document.Personalized.elements[0].value == "showInfo" ) {
     loc = "printerFriendlyOrderGuidePersonalized.do?action=excelPrintPers";
  }
  <% if (theForm.getAppUser().getUserAccount().isHideItemMfg()) { %>
    loc = loc + "&showMfg=n&showMfgSku=n";
  <% } %>
  prtwin = window.open(loc,"excel_format",
    "menubar=yes,resizable=yes,scrollbars=yes,toolbar=yes,status=yes,height=500, width=775,left=100,top=165");
  prtwin.focus();

  return false;
}

function actionMultiSubmit(actionDef, action) {
	var aaal = document.getElementsByName('action');
	if(clicked > 0){
	  return false;
	}
	var aaa = aaal[0];
	aaa.value = action;
	aaa.form.submit();
	
	clicked++;
	return false;
}

//-->

</script>

<table cellpadding="0" cellspacing="0" align="center"
  class="tbstd" width="<%=Constants.TABLEWIDTH%>" >
<tr>
<td>  <jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"t_shopToolbar.jsp")%>'/></td>
</tr>
</table>

<logic:equal name="SHOP_FORM" property="userOrderGuideNumber" value="0">
<logic:equal name="SHOP_FORM" property="templateOrderGuideNumber" value="0">
<table cellpadding="0" cellspacing="0" align="center"
  class="tbstd" width="<%=Constants.TABLEWIDTH%>" >
<tr>
<td align="center">
<b><app:storeMessage key="shop.og.text.noOrderGuideAvailable"/></b>
</td>
</tr>
</table>
</logic:equal>
</logic:equal>



<%
boolean firstrow = true;
int rowIdx = 0;
String prevCategory = "", thisItemCategory = "";
 %>


<!-- Order guide select section -->
<% if(theForm.getUserOrderGuideNumber()>0 ||
      theForm.getTemplateOrderGuideNumber()>0) {%>

<table cellpadding="0" cellspacing="0" align="center"
  class="tbstd" width="<%=Constants.TABLEWIDTH%>" >

  <html:form  action="/store/shop.do?action=orderGuideSelect">
<tr>
<td width="57px">&nbsp;</td>
  <td>
<br>
  <html:select name="SHOP_FORM" property="templateOrderGuideId"
          onchange="javascript:  document.forms[0].submit();"  >
  <html:option value="-1"><app:storeMessage key="shop.og.text.templateOrderGuides"/></html:option>
  <html:options  name="SHOP_FORM" property="templateOrderGuideIds"
   labelName="SHOP_FORM" labelProperty="templateOrderGuideNames"/>
  <html:option value="-2"><app:storeMessage key="shop.og.text.myOrderGuides"/></html:option>
  <html:options  name="SHOP_FORM" property="userOrderGuideIds"
   labelName="SHOP_FORM" labelProperty="userOrderGuideNames"/>
  </html:select>

  <html:hidden property="orderBy"/>
  </html:form>
</td>
<td align=center width="300px">

<logic:greaterThan name="SHOP_FORM" property="orderGuideId" value="0">
<logic:greaterThan name="SHOP_FORM" property="cartItemsSize" value="0">

<% int orderByFormIndex = 1; %>

<form name="Personalized">
<br>&nbsp;
<app:storeMessage key="shop.og.text.includeLocationInformation"/>
<input type="radio" name="pers" value="yes" Checked><app:storeMessage key="shop.og.text.yes"/>
<input type="radio" name="pers" value="no"><app:storeMessage key="shop.og.text.no"/>
<br>

 <% String b_print = IMGPath + "/b_print.gif"; %>
  <a href="#" class="linkButton" onclick="viewPrinterFriendly();"
  ><img src="<%=b_print%>" border="0"/><app:storeMessage key="global.label.printerFriendly"/></a>
&nbsp;&nbsp;&nbsp;&nbsp;
 <% String b_excel = IMGPath + "/b_excel.gif"; %>
  <a href="#" class="linkButton" onclick="viewExcelFormat();"
  ><img src="<%=b_excel%>" border="0"/><app:storeMessage key="global.label.excelFormat"/></a>

</form>

<%
orderByFormIndex = 2;
%>

</td>

<td>
<br>
      <html:form action="/store/shop.do?action=orderGuideSelect">
  <b><app:storeMessage key="shop.og.text.orderBy"/></b>
<%
String formSubmitString =
  "javascript: { document.forms[" + orderByFormIndex + "].submit();}";
%>
<html:select name="SHOP_FORM" property="orderBy"
 onchange="<%=formSubmitString%>" >
<html:option value="<%=\"\"+Constants.ORDER_BY_CATEGORY%>">
   <app:storeMessage key="shop.og.text.category"/>
</html:option>
<html:option value="<%=\"\"+Constants.ORDER_BY_CUST_SKU%>">
   <app:storeMessage key="shop.og.text.ourSkuNum"/>
</html:option>
<html:option value="<%=\"\"+Constants.ORDER_BY_NAME%>">
   <app:storeMessage key="shop.og.text.productName"/>
</html:option>
        </html:select>
      </html:form>
</td>
</logic:greaterThan> <% /* Items are available for display. */ %>

</logic:greaterThan> <% /* Only display the printer
      friendly order guide button if
      an order guide is selected. */ %>


</tr>
</table>
<% } %>

 <jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_shopping_msgs.jsp")%>'/>


<logic:greaterThan name="SHOP_FORM" property="orderGuideId" value="0">
<logic:equal name="SHOP_FORM" property="cartItemsSize" value="0">

<table cellpadding="0" cellspacing="0" align="center"
  class="tbstd" width="<%=Constants.TABLEWIDTH%>" >

<tr>
<td class="text" align="center">
  <b><app:storeMessage key="shop.og.text.noItemsInOrderGuide"/></b>
</td>
  </tr>
</table>
</logic:equal>

<logic:greaterThan name="SHOP_FORM" property="cartItemsSize" value="0">

<table cellpadding="0" cellspacing="0" align="center"
  class="tbstd" width="<%=Constants.TABLEWIDTH%>" >
  <tr>  <td class="tableoutline"><img src="<%=IMGPath%>/cw_spacer.gif" height="3"></td>  </tr>
</table>

<%
   String recalc_img = IMGPath + "/b_recalculate.gif";
   String upd_inv_img = IMGPath + "/b_updateinventory.gif";
   int numberOfIventoryItems = 0;
   int numberOfRegularItems = 0;
%>

<logic:iterate id="tmpitem" name="SHOP_FORM" property="cartItems"
   offset="0" indexId="tidx"
   type="com.cleanwise.service.api.value.ShoppingCartItemData">

<%
if (tmpitem.getIsaInventoryItem()) {
++numberOfIventoryItems;
} else {
++numberOfRegularItems;
}
%>
</logic:iterate>


<html:form name="SHOP_FORM" action="/store/shop.do">
<html:hidden  property="requestTokenSubmit" value="<%=requestToken%>"/>
<% if (inventoryShopping && numberOfIventoryItems > 0) { %>

<table cellpadding="0" cellspacing="0" align="center"
  class="tbstd" width="<%=Constants.TABLEWIDTH%>" >
<tr>
<td >
</td>
<td>
<% if (theForm.getAppUser().getAllowPurchase() ) { %>
<a href="#" id="updinv" class="linkButton" onclick="actionMultiSubmit('BBBBBBB', 'update_inventory');"
  ><img src="<%=upd_inv_img%>" border="0"/><app:storeMessage key="global.label.updateInventory"/></a>
<% } %>

</td>
<td>
  <a href="../store/shoppingcart.do" class="linkButton"><img src="<%=IMGPath%>/b_viewcart.gif"
    border="0"><app:storeMessage key="global.label.shoppingCart"/></a>
</td>
<td valign="middle">
<%=itemsString%>
</td>
<td >
  <logic:notEqual name="shoppingCart" property="newItemsQty" value="0">
  <img src="<%=IMGPath%>/new.gif"/>&nbsp;<%=ClwI18nUtil.getNewShoppingItemsString(request,shoppingCart)%>
<%  shoppingCart.clearNewItems(); %>
  </logic:notEqual>


  <logic:equal name="SHOP_FORM" property="allowPurchase" value="true">
    <a href="../store/checkout.do" class="linkButton"><img src=<%=IMGPath%>/b_checkout.gif
      border=0><app:storeMessage key="global.action.label.checkout"/></a>
  </logic:equal>

</td>
</tr>
</table>

<% int colCount = 0; %>

<table cellpadding="1" cellspacing="0" align="center"
  class="tbstd" width="<%=Constants.TABLEWIDTH%>" >
<tr>

<td class="shopcharthead"><br><%colCount++;%></td>
<td class="shopcharthead" align="center"><app:storeMessage key="shop.og.table.header.par"/><%colCount++;%></td>
<td class="shopcharthead" align="center"><app:storeMessage key="shop.og.table.header.onHand"/><%colCount++;%></td>

<td class="shopcharthead" align="center"><app:storeMessage key="shoppingItems.text.maxOrderQty"/><%colCount++;%></td>

<logic:equal name="SHOP_FORM" property="allowPurchase" value="true">
<td class="shopcharthead" align="center"><app:storeMessage key="shop.og.table.header.orderQty"/><%colCount++;%></td>
</logic:equal>

<logic:equal name="SHOP_FORM" property="allowPurchase" value="false">
<td class="shopcharthead">    &nbsp;<%colCount++;%></td>
</logic:equal>

  <td class="shopcharthead" align="center"><app:storeMessage key="shop.og.table.header.ourSkuNum"/><%colCount++;%></td>
  <td class="shopcharthead"><center><app:storeMessage key="shop.og.table.header.productName"/></center><%colCount++;%></td>
  <td class="shopcharthead" align="center"><app:storeMessage key="shop.og.table.header.size"/><%colCount++;%></td>
  <td class="shopcharthead" align="center"><app:storeMessage key="shop.og.table.header.pack"/><%colCount++;%></td>
  <td class="shopcharthead" align="center"><app:storeMessage key="shop.og.table.header.uom"/><%colCount++;%></td>
  <%  if (!theForm.getAppUser().getUserAccount().isHideItemMfg()) { %>
  <td class="shopcharthead" align="center"><app:storeMessage key="shop.og.table.header.manuf"/><%colCount++;%></td>
  <td class="shopcharthead" align="center"><app:storeMessage key="shop.og.table.header.manufSkuNum"/><%colCount++;%></td>
  <% } %>  
  <logic:equal name="SHOP_FORM" property="showPrice" value="true">
     <td class="shopcharthead" align="center"><app:storeMessage key="shop.og.table.header.price"/><%colCount++;%></td>
     <td class="shopcharthead" align="center"><app:storeMessage key="shop.og.table.header.amount"/><%colCount++;%></td>
  </logic:equal>

  <logic:equal name="SHOP_FORM" property="showPrice" value="false">
     <td class="shopcharthead" align="center">&nbsp;<%colCount++;%></td>
  </logic:equal>

  </tr>

<logic:iterate id="item" name="SHOP_FORM" property="cartItems"
   offset="0" indexId="kidx"
   type="com.cleanwise.service.api.value.ShoppingCartItemData">

     <bean:define id="itemId" name="item" property="product.productId"
       type="java.lang.Integer" />
     <bean:define id="quantityEl" value="<%=\"quantityElement[\"+kidx+\"]\"%>"/>
     <bean:define id="onhandEl" value="<%=\"onhandElement[\"+kidx+\"]\"%>"/>
     <bean:define id="itemIdsEl" value="<%=\"itemIdsElement[\"+kidx+\"]\"%>"/>

<% if (inventoryShopping && item.getIsaInventoryItem()) { %>

<% if(theForm.getOrderBy()==Constants.ORDER_BY_CATEGORY) {

thisItemCategory = item.getCategoryPath();
if ( null == thisItemCategory ) {
  thisItemCategory = "";
}
%>
 <% if (firstrow) { %>
<tr>
<td colspan="<%=colCount%>" class="shopcategory">&nbsp;&nbsp;&nbsp;&nbsp;
<bean:write name="item" property="categoryPath"/></td>
<% prevCategory = thisItemCategory; %>
</tr>
 <%
firstrow = false;
} else if(! prevCategory.equals(thisItemCategory)) { %>
<% prevCategory = thisItemCategory;%>
<tr>
<td colspan="<%=colCount%>" class="shopcategory">&nbsp;&nbsp;&nbsp;&nbsp;
<bean:write name="item" property="categoryPath"/></td>
</tr>
  <% } %>

<% } %>

<%-- jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_oitem.jsp")%>'/ --%>

     <!-- START: item display <%=kidx%> -->
     <tr bgcolor="<%=ClwCustomizer.getEvenRowColor(request.getSession(),rowIdx++)%>">
     <% 
     Date curDate = Constants.getCurrentDate();
     Date effDate = item.getProduct().getEffDate();
     Date expDate = item.getProduct().getExpDate();
     if(effDate != null && effDate.compareTo(curDate)<=0 &&
        (expDate==null || expDate.compareTo(curDate)>0)) {
     %>

     <% if (inventoryShopping && item.getIsaInventoryItem() ) { %>
     <td>
     <span class="inv_item">
     i
     <% if (ShopTool.getCurrentSite(request).isAnInventoryAutoOrderItem
       (item.getProduct().getProductId())) { %>
     a
     <% } %>
     </span>
     </td>

     <td align="center">
        <bean:write name="SHOP_FORM" property='<%= "cartLine[" + kidx + "].inventoryParValue" %>'/>
     </td>
     <td align="center">
     <% if ( item.getInventoryParValue() <= 0 &&
             item.getInventoryQtyOnHand() <= 0 ) { %>
     <html:text name="SHOP_FORM"
      property='<%= "cartLine[" + kidx + "].inventoryQtyOnHandString" %>'
      value="0" size="3"
      tabindex='<%=String.valueOf(TABIDX + 10)%>'
      styleId='<%="IDX_" + TABIDX%>'
     />
     <% } else { %>
     <html:text name="SHOP_FORM"
      property='<%= "cartLine[" + kidx + "].inventoryQtyOnHandString" %>'
       size="3"
      tabindex='<%=String.valueOf(TABIDX + 10)%>'
      styleId='<%="IDX_" + TABIDX%>'
     />
     <% } %>
     </td>

     <td class="text" align="center">
      <logic:greaterThan name="item" property="maxOrderQty" value="-1">
        <bean:write name="item" property="maxOrderQty"/>
      </logic:greaterThan>
     </td>

     <% if ( item.getInventoryParValue() > 0 ) { %>
       <td align="center">
           <% if ( item.getInventoryQtyIsSet() ) {
             int newOnHandVal = 0;
             try { newOnHandVal = Integer.parseInt(item.getInventoryQtyOnHandString()); }
             catch (Exception e) { newOnHandVal = 0; }
             item.setInventoryQtyOnHand(newOnHandVal);
           %>
             <bean:write name="SHOP_FORM" property='<%= "cartLine[" + kidx + "].inventoryOrderQty" %>' />
            <% } %>
       </td>
     <% } else { %>
        <td align="center">&nbsp;</td>
     <% } %>

     <% } else { %>
         <logic:equal name="SHOP_FORM" property="allowPurchase" value="true">
         <td>
             <html:text name="SHOP_FORM"
             property='<%= "cartLine[" + kidx + "].quantityString" %>'
              size="3"
              tabindex='<%=String.valueOf(TABIDX + 10)%>'
              styleId='<%="IDX_" + TABIDX%>'
             />

         </td>
         </logic:equal>
         <logic:equal name="SHOP_FORM" property="allowPurchase" value="false">
         <td>    &nbsp;</td>
         </logic:equal>
        <td>    &nbsp;</td>
        <td>    &nbsp;</td>
     <% } %>
     <% } else { %>
         <td>
            N/A
          </td>
         <td>    &nbsp;</td>
          <td>    &nbsp;</td>
          <% } %>

          <td class="text" align="right"><bean:write name="item" property="actualSkuNum"/>&nbsp;</td>
          <td class="text" align="left">
          <%String itemLink = "shop.do?action=item&source=orderGuide&itemId="
            +itemId+"&qty="+item.getQuantity();
          %>
          <html:link href="<%=itemLink%>">
            <bean:write name="item" property="product.catalogProductShortDesc"/>&nbsp;
          </html:link>
          </td>
          <td class="text" align="center"><bean:write name="item" property="product.size"/>&nbsp;</td>
          <td class="text" align="center"><bean:write name="item" property="product.pack"/>&nbsp;</td>
          <td class="text" align="center"><bean:write name="item" property="product.uom"/>&nbsp;</td>
          <%  if (!theForm.getAppUser().getUserAccount().isHideItemMfg()) {  %>
                     <td class="text" align="center"><bean:write name="item" property="product.manufacturerName"/>&nbsp;</td>
                  <td class="text" align="center">
                     <bean:write name="item" property="product.manufacturerSku"/>&nbsp;
                 </td>
          <% } %>
          <logic:equal name="SHOP_FORM" property="showPrice" value="true">
            <td class="text" ><div class="fivemargin">
              <bean:define id="price"  name="item" property="price"/>
              <%=ClwI18nUtil.getPriceShopping(request,price,"<br>")%>
              <logic:equal name="item" property="contractFlag" value="true">
                <logic:equal name="SHOP_FORM" property="showWholeCatalog" value="true">*</logic:equal>
              </logic:equal>
            </td>
     <%
     java.math.BigDecimal finalLineAmount = null;
     if ( item.getIsaInventoryItem() ) {
       finalLineAmount = new java.math.BigDecimal ( item.getPrice() * item.getInventoryOrderQty());
     } else {
       finalLineAmount =  new java.math.BigDecimal(item.getAmount());
     }
     %>
     <td class="text" align="center">
     <bean:define id="amount"  name="item" property="amount"/>
                <%=ClwI18nUtil.getPriceShopping(request,finalLineAmount,"<br>")%>
     </td>
     </logic:equal>
     <logic:equal name="SHOP_FORM" property="showPrice" value="false">
     <td class="text" align="center">&nbsp;</td>
     </logic:equal>
          </tr> <% /* End of line item */ %>
     <% TABIDX++ ; %>
     <!-- END: item display <%=kidx%> -->


<% } %>
</logic:iterate>
</table>

<table cellpadding="1" cellspacing="0" align="center"
  class="tbstd" width="<%=Constants.TABLEWIDTH%>" >
    <tr>  <td>
<% if (theForm.getAppUser().getAllowPurchase() ) { %>
<a href="#" id="upd2" class="linkButton" onclick="actionMultiSubmit('BBBBBBB', 'update_inventory');"
  ><img src="<%=upd_inv_img%>" border="0"/><app:storeMessage key="global.label.updateInventory"/></a>
<% } %>
  </td>  </tr>
  <tr>  <td class="tableoutline"><img src="<%=IMGPath%>/cw_spacer.gif"
           height="3"></td>  </tr>
</table>


<% } /* End of inventory items. */ %>

<% if (numberOfRegularItems > 0) { %>

<table cellpadding="1" cellspacing="0" align="center"
  class="tbstd" width="<%=Constants.TABLEWIDTH%>" >

<tr>
<td >
  <logic:equal name="SHOP_FORM" property="allowPurchase" value="true">
    <% String add_img = IMGPath + "/b_addtocart.gif"; %>
      <a href="#" class="linkButton" onclick="actionMultiSubmit('BBBBBBB', 'og_addToCart');"
      ><img src="<%=add_img%>" border="0"/><app:storeMessage key="global.label.addToCart"/>
      </a>
  </logic:equal>

</td>
<td>
<a href="shop.do?action=clear" class="linkButton">
<img src="<%=IMGPath%>/b_clearqty.gif" border="0"/><app:storeMessage key="global.label.clearQuantities"/></a>
</td>
<td>
  <a href="#" class="linkButton" onclick="actionMultiSubmit('BBBBBBB', 'og_recalc');"
  ><img src="<%=recalc_img%>" border="0"/><app:storeMessage key="global.label.recalculate"/></a>
</td>
<td>
  <a href="../store/shoppingcart.do" class="linkButton"><img src="<%=IMGPath%>/b_viewcart.gif"
    border="0"><app:storeMessage key="global.label.shoppingCart"/></a>
</td>
<td valign="top"><%=itemsString%>
  <logic:notEqual name="shoppingCart" property="newItemsQty" value="0">
<br>
<img src="<%=IMGPath%>/new.gif"/>&nbsp;<%=ClwI18nUtil.getNewShoppingItemsString(request,shoppingCart)%>
<%
shoppingCart.clearNewItems();
%>
  </logic:notEqual>
</td>


  <logic:equal name="SHOP_FORM" property="allowPurchase" value="true">
    <a href="../store/checkout.do" class="linkButton"><img src=<%=IMGPath%>/b_checkout.gif
      border=0><app:storeMessage key="global.action.label.checkout"/></a>
  </logic:equal>

</td>
</tr>
<tr><td colspan=6>&nbsp;</td></tr>
</table>

<% int colCount = 0; %>

<table cellpadding="1" cellspacing="0" align="center"
  class="tbstd" width="<%=Constants.TABLEWIDTH%>" >

<tr>

<td class="shopcharthead"><%colCount++;%>&nbsp;</td>
  <td class="shopcharthead" align="center"><%colCount++;%>
  <app:storeMessage key="shoppingItems.text.maxOrderQty"/>
</td>


<logic:equal name="SHOP_FORM" property="allowPurchase" value="true">
<td class="shopcharthead" align="center"><%colCount++;%><app:storeMessage key="shop.og.table.header.orderQty"/></td>
</logic:equal>

<logic:equal name="SHOP_FORM" property="allowPurchase" value="false">
<%colCount++;%><td class="shopcharthead">    &nbsp;</td>
</logic:equal>

<td class="shopcharthead" align="center"><app:storeMessage key="shop.og.table.header.ourSkuNum"/><%colCount++;%></td>
  <td class="shopcharthead"><center><app:storeMessage key="shop.og.table.header.productName"/></center><%colCount++;%></td>
  <td class="shopcharthead" align="center"><app:storeMessage key="shop.og.table.header.size"/><%colCount++;%></td>
  <td class="shopcharthead" align="center"><app:storeMessage key="shop.og.table.header.pack"/><%colCount++;%></td>
  <td class="shopcharthead" align="center"><app:storeMessage key="shop.og.table.header.uom"/><%colCount++;%></td>
  <% if (!theForm.getAppUser().getUserAccount().isHideItemMfg()) { %>
    <td class="shopcharthead" align="center"><app:storeMessage key="shop.og.table.header.manuf"/><%colCount++;%></td>
    <td class="shopcharthead" align="center"><app:storeMessage key="shop.og.table.header.manufSkuNum"/><%colCount++;%></td>
  <% } %>
  <logic:equal name="SHOP_FORM" property="showPrice" value="true">
     <td class="shopcharthead" align="center"><app:storeMessage key="shop.og.table.header.price"/><%colCount++;%></td>
     <td class="shopcharthead" align="center"><app:storeMessage key="shop.og.table.header.amount"/><%colCount++;%></td>
  </logic:equal>

  <logic:equal name="SHOP_FORM" property="showPrice" value="false">
     <td class="shopcharthead" align="center">&nbsp;<%colCount++;%></td>
  </logic:equal>

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

     <bean:define id="itemId" name="item" property="product.productId"
       type="java.lang.Integer" />
     <bean:define id="quantityEl" value="<%=\"quantityElement[\"+kidx+\"]\"%>"/>
     <bean:define id="onhandEl" value="<%=\"onhandElement[\"+kidx+\"]\"%>"/>
     <bean:define id="itemIdsEl" value="<%=\"itemIdsElement[\"+kidx+\"]\"%>"/>
<!--
     <html:hidden name="SHOP_FORM" property="<%=itemIdsEl%>" value="<%=\"\"+itemId%>"/>
-->
<% if (item.getIsaInventoryItem() == false) { %>

<% if(theForm.getOrderBy()==Constants.ORDER_BY_CATEGORY) { %>
<%
thisItemCategory = item.getCategoryPath();
if ( null == thisItemCategory ) {
  thisItemCategory = "";
}
%>
 <% if (firstrow) { %>
<tr>
<td colspan="<%=colCount%>" class="shopcategory">&nbsp;&nbsp;&nbsp;&nbsp;
<bean:write name="item" property="categoryPath"/></td>
<% prevCategory = thisItemCategory; %>

</tr>
 <%
firstrow = false;
} else if(! prevCategory.equals(thisItemCategory)) { %>
<% prevCategory = thisItemCategory; %>
<tr>
<td colspan="<%=colCount%>" class="shopcategory">&nbsp;&nbsp;&nbsp;&nbsp;
<bean:write name="item" property="categoryPath"/></td>
</tr>
  <% } %>
<% } %>

<!--  include file="f_oitem.jsp"  -->

<!-- START: item display <%=kidx%> -->

<tr bgcolor="<%=ClwCustomizer.getEvenRowColor(request.getSession(),rowIdx++)%>">

<td>
      <% if (ShopTool.isInventoryShoppingOn(request) && item.getIsaInventoryItem() ) { %>
<%--      <span class="inv_item"> --%>
        i
        <% if (ShopTool.getCurrentSite(request).isAnInventoryAutoOrderItem(item.getProduct().getProductId())) { %>
        a
        <% } %>
  <%--    </span> --%>
    <%} else { %>
       &nbsp;
    <%} %>
    </td>     


     <td class="text" align="center">
      <logic:greaterThan name="item" property="maxOrderQty" value="-1">        
        <bean:write name="item" property="maxOrderQty"/>
      </logic:greaterThan>
     </td>

<logic:equal name="SHOP_FORM" property="allowPurchase" value="true">
<td>
<html:text name="SHOP_FORM"
property='<%= "cartLine[" + kidx + "].quantityString" %>' size="3"
 tabindex='<%=String.valueOf(TABIDX + 10)%>'
 styleId='<%="IDX_" + TABIDX%>'
/>

<% TABIDX++ ; %>

</td>
</logic:equal>
<logic:equal name="SHOP_FORM" property="allowPurchase" value="false">
<td>    &nbsp;</td>
</logic:equal>

     <td class="text" align="right">
       <bean:write name="item" property="actualSkuNum"/>&nbsp;
     </td>
     <td class="text" align="left">
     <%String itemLink = "shop.do?action=item&source=orderGuide&itemId="
       +itemId+"&qty="+item.getQuantity();
     %>
     <html:link href="<%=itemLink%>">
       <bean:write name="item" property="product.catalogProductShortDesc"/>&nbsp;
     </html:link>
     </td>
     <td class="text" align="center">
       <bean:write name="item" property="product.size"/>&nbsp;
     </td>
     <td class="text" align="center">
       <bean:write name="item" property="product.pack"/>&nbsp;
     </td>
     <td class="text" align="center">
       <bean:write name="item" property="product.uom"/>&nbsp;
     </td>
     <% if (!theForm.getAppUser().getUserAccount().isHideItemMfg()) { %>
     <td class="text" align="center">
     <bean:write name="item" property="product.manufacturerName"/>&nbsp;
     </td>
     <td class="text" align="center">
       <bean:write name="item" property="product.manufacturerSku"/>&nbsp;
     </td>
     <% } %>
     <logic:equal name="SHOP_FORM" property="showPrice" value="true">
       <td class="text" ><div class="fivemargin">
         <bean:define id="price"  name="item" property="price"/>
           <%=ClwI18nUtil.getPriceShopping(request,price,"<br>")%>
       <logic:equal name="item" property="contractFlag" value="true">
       <logic:equal name="SHOP_FORM" property="showWholeCatalog" value="true">
       *
       </logic:equal>
</logic:equal>
</td>

<%
java.math.BigDecimal finalLineAmount = null;

if ( item.getIsaInventoryItem() ) {
  finalLineAmount = new java.math.BigDecimal
    ( item.getPrice() * item.getInventoryOrderQty());
}
else {
  finalLineAmount =
    new java.math.BigDecimal(item.getAmount());
}
%>

<td class="text" align="center">
<bean:define id="amount"  name="item" property="amount"/>
    <%=ClwI18nUtil.getPriceShopping(request,finalLineAmount,"<br>")%>
</td>
</logic:equal>

<logic:equal name="SHOP_FORM" property="showPrice" value="false">
<td class="text" align="center">&nbsp;</td>
</logic:equal>


     </tr> <% /* End of line item */ %>



<!-- END: item display <%=kidx%> -->

<% } %>

   </logic:iterate>
</table>

<table cellpadding="1" cellspacing="0" align="center"
  class="tbstd" width="<%=Constants.TABLEWIDTH%>" >

  <tr>
  <td class="tableoutline"><img src="<%=IMGPath%>/cw_spacer.gif" height="3"></td>
  </tr>
</table>

<table cellpadding="1" cellspacing="0" align="center"
  class="tbstd" width="<%=Constants.TABLEWIDTH%>" >

<tr>
  <td>
  <logic:equal name="SHOP_FORM" property="allowPurchase" value="true">
    <% String add_img = IMGPath + "/b_addtocart.gif"; %>
      <a href="#" class="linkButton" onclick="actionMultiSubmit('BBBBBBB', 'og_addToCart');">
      <img src="<%=add_img%>" border="0"/><app:storeMessage key="global.label.addToCart"/>
      </a>
  </logic:equal>
  &nbsp;&nbsp;
  <a href="shop.do?action=clear" class="linkButton">
    <img src="<%=IMGPath%>/b_clearqty.gif" border="0"/><app:storeMessage key="global.label.clearQuantities"/></a>
  &nbsp;&nbsp;
  <a href="#" class="linkButton" onclick="actionMultiSubmit('BBBBBBB', 'og_recalc');"
    ><img src="<%=recalc_img%>" border="0"/><app:storeMessage key="global.label.recalculate"/></a>
  &nbsp;&nbsp;
  <% String refreshLink = "shop.do?action=refreshGuide&templateOrderGuideId="+theForm.getOrderGuideId(); %>
  <a href="<%=refreshLink%>" class="linkButton">
    <img src="<%=IMGPath%>/b_refresh.gif" border="0"/><app:storeMessage key="global.label.refresh"/></a>
  </td>
  <logic:equal name="SHOP_FORM" property="showPrice" value="true">
  <td class="text" align="rigth"><div class="fivemargin">
  <b><app:storeMessage key="shop.og.text.total"/><b>
     </div></td>
     <td class="text"><div class="fivemargin">
       <bean:define id="itemsAmt"  name="SHOP_FORM" property="itemsAmt"/>
       <%=ClwI18nUtil.getPriceShopping(request,itemsAmt,"<br>")%>
     </div></td>
     </logic:equal>
     <logic:notEqual name="SHOP_FORM" property="showPrice" value="true">
     <td class="text" colspan=2><div class="fivemargin">&nbsp;</div></td>
     </logic:notEqual>
  </tr>
</table>
<% } %>
<html:hidden property="action" value="BBBBBBB"/>
<html:hidden property="action" value="CCCCCCC"/>
</html:form>
</logic:greaterThan>
</logic:greaterThan>



<script type="text/javascript" language="JavaScript">
<!--
  var ix = document.getElementById("IDX_0");
  if (ix != null) {
  ix.focus();
  }
  var clicked = 0;
  // -->
</script>





