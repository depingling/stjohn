
<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>

<%@ page import="java.util.Date" %>
<%@ page import="com.cleanwise.service.api.value.*"%>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.List" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>
<bean:define id="theForm" name="SHOP_FORM" type="com.cleanwise.view.forms.UserShopForm"/>

<script language="JavaScript">
  <!--
function viewPrinterFriendly2() {
  var loc = "printerFriendlyOrderGuide.do?action=pdfPrintCatalog";
  prtwin = window.open(loc,"printer_friendly",
    "menubar=yes,resizable=yes,scrollbars=yes,toolbar=yes,status=yes,height=500, width=775,left=100,top=165");
  prtwin.focus();

  return false;
}

function viewPrinterFriendly() {
  var loc = "printerFriendlyOrderGuide.do?action=pdfPrintCatalog";
  prtwin = window.open(loc,"cat_print",
    "menubar=yes,resizable=yes,scrollbars=yes,toolbar=yes,status=yes,height=500, width=775,left=100,top=165");
  prtwin.focus();
  return false;
}

function viewExcelFormat() {
  var loc = "printerFriendlyOrderGuide.do?action=excelPrintCatalog";
<% if (theForm.getAppUser().getUserAccount().isHideItemMfg()) { %>
  loc = loc + "&showMfg=n&showMfgSku=n";
<% } %>
  loc = loc + "&showPack=n&showUom=n";
  prtwin = window.open(loc,"excel_format",
    "menubar=yes,resizable=yes,scrollbars=yes,toolbar=yes,status=yes,height=500, width=775,left=100,top=165");
  prtwin.focus();

  return false;
}

fmtprt= new Image();
fmtprt.src="<%=IMGPath%>/printerfriendly.gif";

fmtexc= new Image();
fmtexc.src="<%=IMGPath%>/excelformat.gif";


if(document.forms[0] && document.forms[0].search){
  document.forms[0].search.focus();
}

function f_cf() {
  if(document.forms[0] && document.forms[0].search){
    document.forms[0].search.focus();
  }
}

function actionMultiSubmit(actionDef, action) {
  var aaal = document.getElementsByName('action');
  for ( var i = 0; i < 1; i++ ) {
    var aaa = aaal[i];
    aaa.value = action;
    aaa.form.submit();
  }

 return false;
}

function setAndSubmit(fid, vv, value) {
  var aaa = document.forms[fid].elements[vv];
  aaa.value=value;
  aaa.form.submit();
  return false;

}
-->
</script>


<%
    boolean
      quickOrderView = false,
      shoppingCartView = false,
      checkoutView = false,
      editCartItems = true ,
      f_showSelectCol = false,
      addToCartListView = true;

%>

<app:commonDefineProdColumnCount
    id="colCountInt"
    viewOptionEditCartItems="<%=editCartItems%>"
    viewOptionQuickOrderView="<%=quickOrderView%>"
    viewOptionAddToCartList="<%=addToCartListView%>"
    viewOptionOrderGuide="false"
    viewOptionShoppingCart="<%=shoppingCartView%>"
    viewOptionCheckout="<%=checkoutView%>"
    viewOptionCatalog="true"
    />

<%int numCols = ((Integer)pageContext.getAttribute("colCountInt")).intValue();%>

<%
ShoppingCartData shoppingCart = (ShoppingCartData) session.getAttribute(Constants.SHOPPING_CART);
String itemsString = ClwI18nUtil.getShoppingItemsString(request,shoppingCart);
CleanwiseUser appUser = theForm.getAppUser();
%>
<%
    SiteData site = null;
    boolean viewAddInvCart = false;
    if (shoppingCart != null) {
        site = shoppingCart.getSite();
        if (site != null) {
            viewAddInvCart
                    = site.hasModernInventoryShopping() && site.hasInventoryShoppingOn();
        }
    }
%>

<table align="center" class="tbstd" cellpadding="0" cellspacing="0"
  width="<%=Constants.TABLEWIDTH%>">
<tr>
<td>
<jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"t_shopToolbar.jsp")%>'/>

</td>
</tr>
</table>

<jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_shopping_msgs.jsp")%>'>
    <jsp:param name="shoppingCartName" value="<%=Constants.SHOPPING_CART%>"/>
</jsp:include>
<jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_shopping_msgs.jsp")%>'>
    <jsp:param name="shoppingCartName" value="<%=Constants.INVENTORY_SHOPPING_CART%>"/>
</jsp:include>

<table class="tbstd" align="center"
  cellpadding="0" cellspacing="0" width="<%=Constants.TABLEWIDTH%>">

<!-- content, shopping catalog -->
<tr>
<td height=200 valign=top>
<table align="center" border="0" cellpadding="1" cellspacing="0">
<html:form styleId="sel" action="/store/shop.do?action=control">

<html:hidden name="SHOP_FORM" property="navigateCategoryId" value="-1"/>
<tr><td class="smalltext" valign="up" width="40%">
<table width="100%">
<tr>
    <td colspan="2" height="30px" valign="center">
        <logic:equal name="SHOP_FORM" property="categoryPathSize" value="0">
            <logic:equal name="SHOP_FORM" property="navigationFlag" value="true">
                <a href="#" class="linkButton" onclick="setAndSubmit('sel','command','listAll');" >
                    <app:storeMessage key="shop.catalog.text.allMyProducts"/></a>
            </logic:equal>
        </logic:equal>
        <logic:equal name="SHOP_FORM" property="navigationFlag" value="true">
            <logic:greaterThan name="SHOP_FORM" property="shopNavigatorInfo" value="0">
                <jsp:include flush="true" page="<%=ClwCustomizer.getStoreFilePath(request,\"t_msbSiteCategoryNavigator.jsp\")%>"/>
            </logic:greaterThan>
        </logic:equal>
         <logic:equal name="SHOP_FORM" property="navigationFlag" value="false">
              <a href="../store/shop.do?action=catalog" class="linkButton" >
                    <app:storeMessage key="shop.catalog.text.shopByCategory"/>
              </a>
         </logic:equal>
    </td>
</tr>


        <tr>
            <td class="smalltext" width="53%" valign="top">

           <logic:equal name="SHOP_FORM" property="navigationFlag" value="true">
                <table>                 
                    <tr>
                        <td width="6px">&nbsp;</td>
                        <td>
                            <jsp:include page='<%=ClwCustomizer.getStoreFilePath(request,"popupFacade.jsp")%>'/>                            
                         </td>
                    </tr>           
                </table>
            </td>
           </logic:equal>

            <!-- search -->
            <td class="smalltext" width="47%" valign="top">
                <jsp:include flush='true' page='t_searchCatalogItemForm.jsp'/>
            </td>
        </tr>

    
<logic:greaterThan name="SHOP_FORM" property="productListSize" value="0">
<tr>

    <td colspan="2" align="right">
        <a href="#" class="linkButton" onclick="viewExcelFormat();">
            <img src='<%=IMGPath + "/b_excel.gif"%>' border="0"/><app:storeMessage key="global.label.excelFormat"/>
        </a>&nbsp;
        <b><app:storeMessage key="shop.catalog.text.orderBy"/></b>&nbsp;
    <html:select name="SHOP_FORM" property="orderBy" onchange="javascript:document.forms[0].submit();">
          <html:option value="<%=\"\"+Constants.ORDER_BY_CATEGORY%>">
            <app:storeMessage key="shop.catalog.text.category"/>
          </html:option>
          <html:option value="<%=\"\"+Constants.ORDER_BY_CUST_SKU%>">
            <app:storeMessage key="shop.catalog.text.ourSku#"/>
          </html:option>
          <html:option value="<%=\"\"+Constants.ORDER_BY_NAME%>">
            <app:storeMessage key="shop.catalog.text.productName"/>
          </html:option>
        </html:select>
  </td>
</tr>
</logic:greaterThan>
<html:hidden property="command" value="CCCCCCC"/>
</html:form>
</table>
</td>
</tr>

<!-- List of items -->
<logic:greaterThan name="SHOP_FORM" property="productListSize" value="0">
<tr>

<td>
  <table align="center" border="0" cellpadding="0" cellspacing="0">
  <!-- Page control -->
  <html:form styleId="items" action="/store/shop.do?action=addToCart">
  <logic:equal name="SHOP_FORM" property="allowPurchase" value="true">

  <tr><td colspan="<%=numCols%>" class="tableoutline">
  <img src="<%=IMGPath%>/cw_spacer.gif" height="1">
  </td></tr>

    <tr>
    <td colspan="<%=numCols%>">
    <table border="0" cellpadding="1" cellspacing="0" width="<%=Constants.TABLEWIDTH%>">
    <tr>
    <td>
      <a href="#" class="linkButton" onclick="setAndSubmit('items','command','addToCart');"
      ><img src='<%=IMGPath + "/b_addtocart.gif"%>' border="0"/><app:storeMessage key="global.label.addToCart"/></a>
    </td>
    <%if (viewAddInvCart) {%>
        <td>
            <% String add_img = IMGPath + "/b_addtocart.gif"; %>
            <a href="#" class="linkButton" onclick="setAndSubmit('items','command','sc_addToModInvCart');">
                <img src="<%=add_img%>" border="0"/>
                <app:storeMessage key="global.label.addToInventoryCart"/>
            </a>
        </td>
        <%}%>
        <td>
        <td>
     <a href='shop.do?action=clear' class="linkButton"
      ><img src="<%=IMGPath%>/b_clearqty.gif" border="0"/><app:storeMessage key="global.label.clearQuantities"/></a>
    </td>

    <td>
    <a href="../store/shoppingcart.do" class="linkButton"><img src="<%=IMGPath%>/b_viewcart.gif"
    border="0"><app:storeMessage key="global.label.shoppingCart"/></a>
    </td>
    <td>
    <%=ClwI18nUtil.getShoppingItemsString(request,shoppingCart)%>
    <logic:notEqual name="shoppingCart" property="newItemsQty" value="0">
    <br>
    <img src="<%=IMGPath%>/new.gif"/>&nbsp;<%=ClwI18nUtil.getNewShoppingItemsString(request,shoppingCart)%>
    <%
    shoppingCart.clearNewItems();
    %>
    </logic:notEqual>
    </td>
          <td>
    <a href="../store/checkout.do" class="linkButton"><img src=<%=IMGPath%>/b_checkout.gif
      border=0><app:storeMessage key="global.action.label.checkout"/></a>
    </td>

   </tr>
   </table>
   </td>
    </tr>

  </logic:equal>


 <%--------------------------------- HEADER -----------------------------------------%>
 <tr>
    <app:commonDisplayProdHeader
        viewOptionEditCartItems="<%=editCartItems%>"
        viewOptionQuickOrderView="<%=quickOrderView%>"
        viewOptionAddToCartList="<%=addToCartListView%>"
        viewOptionOrderGuide="false"
        viewOptionShoppingCart="<%=shoppingCartView%>"
        viewOptionCheckout="<%=checkoutView%>"
        viewOptionCatalog="true"
        />
 </tr>
<%-----------------------------------------------------------------------------------------%>

  <bean:define id="offset" name="SHOP_FORM" property="offset"/>
  <bean:define id="pagesize" name="SHOP_FORM" property="pageSize"/>

<%
   if(theForm.getOrderBy()==Constants.ORDER_BY_CUST_SKU) {
      theForm.sortBySku();
   }

   if(theForm.getOrderBy()==Constants.ORDER_BY_NAME) {
      theForm.sortByName();
   }

   if(theForm.getOrderBy()==Constants.ORDER_BY_CATEGORY) {
      theForm.sortByCategory();
   }

%>



  <logic:iterate id="item" name="SHOP_FORM" property="cartItems"
   offset="0" length="<%=\"\"+pagesize%>" indexId="kkk"
   type="com.cleanwise.service.api.value.ShoppingCartItemData">

     <bean:define id="itemId" name="item" property="product.productId"/>
     <bean:define id="quantityEl" value="<%=\"quantityElement[\"+kkk+\"]\"%>"/>
     <bean:define id="itemIdsEl" value="<%=\"itemIdsElement[\"+kkk+\"]\"%>"/>
     <html:hidden name="SHOP_FORM" property="<%=itemIdsEl%>" value="<%=\"\"+itemId%>"/>

     <% if(theForm.getOrderBy()==Constants.ORDER_BY_CATEGORY) { %>
     <% if(theForm.isCategoryChanged(((Integer)kkk).intValue())) { %>
       <tr>
        <td class="shopcategory" colspan = "<%=numCols%>"><bean:write name="item" property="categoryPath"/></td>
       </tr>
     <% } %>
     <% } %>
     <tr bgcolor="<%=ClwCustomizer.getEvenRowColor(session,(Integer)kkk)%>">

     <%String itemLink = "shop.do?action=item&source=catalog&itemId="+itemId;%>

      <app:commonDisplayProd
        viewOptionEditCartItems="<%=editCartItems%>"
        viewOptionQuickOrderView="<%=quickOrderView%>"
        viewOptionAddToCartList="<%=addToCartListView%>"
        viewOptionOrderGuide="false"
        viewOptionShoppingCart="<%=shoppingCartView%>"
        viewOptionCheckout="<%=checkoutView%>"
        viewOptionCatalog="true"
        name="item"
        link="<%=itemLink%>"
        index="<%=kkk%>"
        inputNameQuantity="<%=quantityEl%>"/>

  </tr>


   </logic:iterate>

   <logic:greaterThan name="SHOP_FORM" property="productListSize" value="<%=\"\"+pagesize%>">
     <tr>
      <td colspan="<%=numCols%>">

      <logic:notEqual name="SHOP_FORM" property="prevPage" value="-1">
        <bean:define id="prevPage" name="SHOP_FORM" property="prevPage"/>
        <% String linkHref = new String("shop.do?action=goPage&page=" + prevPage);%>
        <html:link href="<%=linkHref%>">Prev</html:link>
      </logic:notEqual>

      <logic:iterate id="pages" name="SHOP_FORM" property="pages"
      offset="0" indexId="ii" type="java.lang.Integer">
        <logic:notEqual name="SHOP_FORM" property="currentPage" value="<%=\"\"+ii%>" >
          <% String linkHref = new String("shop.do?action=goPage&page=" + ii);%>
          <html:link href="<%=linkHref%>">[<%=ii.intValue()+1%>]</html:link>
        </logic:notEqual>
        <logic:equal name="SHOP_FORM" property="currentPage" value="<%=\"\"+ii%>" >
         [<%=ii.intValue()+1%>]
        </logic:equal>
      </logic:iterate>

      <logic:notEqual name="SHOP_FORM" property="nextPage" value="-1">
        <bean:define id="nextPage" name="SHOP_FORM" property="nextPage"/>
        <% String linkHref = new String("shop.do?action=goPage&page=" + nextPage);%>
        <html:link href="<%=linkHref%>">Next</html:link>
      </logic:notEqual>
      </td>
      </tr>
    </logic:greaterThan>

  <logic:equal name="SHOP_FORM" property="allowPurchase" value="true">
    <tr>
    <td colspan="<%=numCols%>">
    <a href="#" class="linkButton" onclick="setAndSubmit('items','command','addToCart');"
    ><img src='<%=IMGPath + "/b_addtocart.gif"%>' border="0"/><app:storeMessage key="global.label.addToCart"/></a>
    <%if (viewAddInvCart) {%>
        &nbsp;&nbsp;
        <% String add_img = IMGPath + "/b_addtocart.gif"; %>
        <a href="#" class="linkButton" onclick="setAndSubmit('items','command','sc_addToModInvCart');">
            <img src="<%=add_img%>" border="0"/>
            <app:storeMessage key="global.label.addToInventoryCart"/>
        </a> <%}%>
        &nbsp;&nbsp;
    <a href='shop.do?action=clear' class="linkButton"
    ><img src="<%=IMGPath%>/b_clearqty.gif" border="0"/><app:storeMessage key="global.label.clearQuantities"/></a>
    </td>
    </tr>
  </logic:equal>
  <html:hidden property="command" value="CCCCCCC"/>
  </html:form>
  </table>
  </td>
  </tr>
</logic:greaterThan>
</table>
</td>
</tr>
</table>
<table align="center" border="0" cellpadding="0" cellspacing="0"
  width="<%=Constants.TABLEWIDTH%>">
<tr>
<td>
 <img src="<%=IMGPath%>/cw_left_footer_shop.gif" ALIGN="top">
</td>
<td>
 <img src="<%=IMGPath%>/cw_middle_footer_shop.gif" ALIGN="top"
   width="<%=Constants.TABLE_BOTTOM_MIDDLE_BORDER_WIDTH%>" height="8">
</td>
<td>
 <img src="<%=IMGPath%>/cw_right_footer_shop.gif" ALIGN="top">
</td>
</tr>
</table>



<script type="text/javascript" language="JavaScript">
  <!--
  var frms = document.forms;
  for(ii=0; ii<frms.length; ii++) {
	 //STJ-3881: first try to set the focus to the product name, and if that isn't successful
	 //try to set the focus to the sku
     var focusControl = frms[ii].elements["searchName"];
     if (focusControl!=null &&
        focusControl!= undefined &&
        focusControl.type!= "hidden" &&
        !focusControl.disabled) {
       focusControl.focus();
       break;
     }
     var focusControl = frms[ii].elements["searchSku"];
    if (focusControl!=null &&
       focusControl!= undefined &&
       focusControl.type!= "hidden" &&
       !focusControl.disabled) {
      focusControl.focus();
      break;
    }
  }
  // -->
</script>



