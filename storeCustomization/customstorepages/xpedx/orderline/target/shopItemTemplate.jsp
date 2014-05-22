<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ShopTool" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="java.util.Date" %>
<%@ page import="com.cleanwise.service.api.value.*"%>


<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
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
function setCommandAndSubmit(value) {
  var aaa = document.forms[0].elements['command'];
  aaa.value=value;
  aaa.form.submit();
  return false;

}
//-->
</script>

<%
String storeDir=ClwCustomizer.getStoreDir();
ShoppingCartData shoppingCart = (ShoppingCartData) session.getAttribute(Constants.SHOPPING_CART);
%>


<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>

<bean:define id="theForm" name="SHOP_FORM" type="com.cleanwise.view.forms.UserShopForm"/>

<table align="center" border="0" cellpadding="0" cellspacing="0" width="<%=Constants.TABLEWIDTH%>">
<!-- shopping toolbar -->
<tr>
<td class="tableoutline" width="1" bgcolor="black">
  <img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1">
</td>
<td>
  <jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"t_shopToolbar.jsp")%>'/>
</td>
<td class="tableoutline" width="1" bgcolor="black">
  <img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1">
</td>
</tr>
<%
  String source = request.getParameter("source");
  if(source==null) source = "";
  int itemId = theForm.getItemId();
  String addToCartUrl ="/store/shop.do?action=itemToCart&source="+source;
%>
<html:form action="<%=addToCartUrl%>">
<html:hidden name="SHOP_FORM" property="itemId" value="<%=\"\"+itemId%>"/>

<!-- item picture and long description -->
<bean:define id="item" name="SHOP_FORM" property="itemDetail"
 type="com.cleanwise.service.api.value.ShoppingCartItemData"/>
<tr>
<td class="tableoutline" width="1"> <img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1"></td>
<td><jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_shopping_msgs.jsp")%>'/></td>
<td class="tableoutline" width="1"><img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1"></td>
</tr>
<tr>
<td class="tableoutline" width="1">
  <img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1">
</td>
<td class="text">
  <table align="center" border="0" cellpadding="0" cellspacing="0" width="98%">
<!--  -->
  <logic:equal name="SHOP_FORM" property="allowPurchase" value="true">
    <tr>
    <td colspan="12">
    <table border="0" cellpadding="0" cellspacing="0">
    <tr>
    <td>
        <% if (ShopTool.isModernInventoryShopping(request)
              && !ShopTool.hasDiscretionaryCartAccessOpen(request)) { %>
            <% String add_img = IMGPath + "/b_addtocart.gif"; %>
           <a href="#" class="linkButton" onclick="setCommandAndSubmit('toInvCart');">
                <img src="<%=add_img%>" border="0"/>
                <app:storeMessage key="global.label.addToInventoryCart"/>
            </a>
    <%} else { %>
      <a href="#" class="linkButton" onclick="actionMultiSubmit('BBBBBBB', 'og_addToCart');"
      ><img src='<%=IMGPath + "/b_addtocart.gif"%>' border="0"/><app:storeMessage key="global.label.addToDiscretionaryCart"/></a>
    <%}%>
    </td>
    <td>
     <a href='<%="shop.do?action=itemClear&source="+source%>' class="linkButton"
      ><img src="<%=IMGPath%>/b_clearqty.gif" border="0"/><app:storeMessage key="global.label.clearQuantities"/></a>
    </td>
        <% if (ShopTool.hasDiscretionaryCartAccessOpen(request)) { %>
  <td>
  <a href="../store/shoppingcart.do" class="linkButton"><img src="<%=IMGPath%>/b_viewcart.gif"
    border="0"><app:storeMessage key="global.label.discretionaryCart"/></a>
  </td>
  <td><%=ClwI18nUtil.getShoppingItemsString(request,shoppingCart)%>
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
   <%} %>
   </tr>
   </table>
   </td>
    </tr>
   <tr><td colspan="12" class="tableoutline"><img src="<%=IMGPath%>/cw_spacer.gif" height="1"></td></tr>
  </logic:equal>
<!-- -->
    <tr>
    <td >
    <% String image=item.getProduct().getImage();
       if(image!=null && image.trim().length()>0) {
    %>
      <img src="/<%=storeDir%>/<%=item.getProduct().getImage()%>">
    <% } else { %>
      &nbsp;
    <% } %>
    </td>
    <td align="left" valign="top">
     <% String skuDesc = item.getProduct().getCustomerProductShortDesc();
          if(skuDesc==null || skuDesc.trim().length()==0) {
            skuDesc = item.getProduct().getShortDesc();
          }
    %>
    <table border="0" cellpadding="0" cellspacing="0">
      <tr>
      <td class="customerltbkground" align="center" valign="top">
       <div class="itemheadmargin">
       <span class="shopitemhead">
        <%=skuDesc%>
        </div>
      </td>
      </tr>
       <tr>
       <td class="text"><div class="itemheadmargin">
        <bean:write name="item" property="product.longDesc"/>
           <logic:present name="item" property="product.certCompaniesBusEntityDV">
               <bean:size  id="certCompSize"  name="item" property="product.certCompaniesBusEntityDV"/>
                <logic:greaterThan name="certCompSize" value="0">
             <br>
                    <span class="greencerthead">  <app:storeMessage key="shoppingItems.detail.text.cerifiedBy"/>  </span>

                <span class="greencert">
             <logic:iterate id="certComp" name="item" property="product.certCompaniesBusEntityDV"
                            type="com.cleanwise.service.api.value.BusEntityData">
                 <br>
                 <bean:write name="certComp" property="shortDesc"/>
             </logic:iterate>
                </span>
               </logic:greaterThan>
           </logic:present>
        <tr>
       </div></td>
       </tr>
       <td align="right" valign="bottom">
       <div class="itemheadmargin">



       <% String msds=item.getProduct().getMsds();
          if(msds!=null && msds.trim().length()>0) {
       %>

         <a href="/<%=storeDir%>/<%=msds%>" target="_blank" class="linkButton"
         ><img border="0" src="<%=IMGPath%>/b_document.gif"/><app:storeMessage key="global.label.msds"/></a>
       <% } %>
       <% String ded=item.getProduct().getDed();
          if(ded!=null && ded.trim().length()>0) {
       %>
          <a href="/<%=storeDir%>/<%=ded%>" target="_blank" class="linkButton"
      ><img border="0" src="<%=IMGPath%>/b_document.gif"/><app:storeMessage key="global.label.ded"/></a>
       <% } %>
       <% String spec=item.getProduct().getSpec();
          if(spec!=null && spec.trim().length()>0) {
       %>
          <a href="/<%=storeDir%>/<%=spec%>" target="_blank" class="linkButton"
        ><img border="0" src="<%=IMGPath%>/b_document.gif"/><app:storeMessage key="global.label.productSpec"/></a>
       <% } %>
       </div>
       </td>
       </tr>

    </table>
    </td>
    </tr>
  </table>
</td>
<td class="tableoutline" width="1"><img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1"></td>
</tr>

<!-- List of items -->
<tr>
<td class="tableoutline" width="1" bgcolor="black">
  <img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1">
</td>
<td>
  <table align="center" border="0" cellpadding="0" cellspacing="0"  width="98%">
  <tr>
  <td colspan="12" class="tableoutline"><img src="<%=IMGPath%>/cw_spacer.gif" height="1"></td></tr>
  <tr>
  <td class="shopcharthead">&nbsp;</td>
  <td class="shopcharthead" align="center" width="35">
    <app:storeMessage key="shoppingItems.text.maxOrderQty"/>
  </td>

   <td class="shopcharthead"><div class="fivemargin">
    <logic:equal name="SHOP_FORM" property="allowPurchase" value="true">
      <app:storeMessage key="shoppingItems.text.qty"/>
    </logic:equal>
    <logic:equal name="SHOP_FORM" property="allowPurchase" value="false">
    &nbsp;
    </logic:equal>
   </div></td>
   <td class="shopcharthead"><div class="fivemargin">
  <%
  String showDistInventory = ShopTool.getShowDistInventoryCode(request);
  if(RefCodeNames.DIST_INVENTORY_DISPLAY.SHOW_FLAG.equals(showDistInventory) ||
     RefCodeNames.DIST_INVENTORY_DISPLAY.SHOW_QUANTITIES.equals(showDistInventory)) {
  %>
   <app:storeMessage key="shoppingItems.text.distInv"/>
  <% } else { %>
     &nbsp;
  <% } %>
  </div></td>
   <td class="shopcharthead"><div class="fivemargin"><app:storeMessage key="shoppingItems.text.ourSkuNum"/></div></td>
   <td class="shopcharthead"><div class="fivemargin"><app:storeMessage key="shoppingItems.text.productName"/></div></td>
   <td class="shopcharthead"><div class="fivemargin"><app:storeMessage key="shoppingItems.text.size"/></div></td>
   <td class="shopcharthead"><div class="fivemargin"><app:storeMessage key="shoppingItems.text.pack"/></div></td>
   <td class="shopcharthead"><div class="fivemargin"><app:storeMessage key="shoppingItems.text.uom"/></div></td>
   <td class="shopcharthead"><div class="fivemargin"><app:storeMessage key="shoppingItems.text.color"/></div></td>
   <td class="shopcharthead"><div class="fivemargin">
     <logic:equal name="SHOP_FORM" property="showPrice" value="true">
       <app:storeMessage key="shoppingItems.text.price"/>
     </logic:equal>
     <logic:equal name="SHOP_FORM" property="showPrice" value="false">
       &nbsp;
     </logic:equal>
   </div></td>
  </tr>

  <tr>
  <td colspan="12" class="tableoutline"><img src="<%=IMGPath%>/cw_spacer.gif" height="1"></td></tr>
   <tr>
    <td>
    <% if (ShopTool.isInventoryShoppingOn(request) && item.getIsaInventoryItem() ) { %>
      <span class="inv_item">
        i
        <% if (ShopTool.getCurrentSite(request).isAnInventoryAutoOrderItem(item.getProduct().getProductId())) { %>
        a
        <% } %>
      </span>
    <%} else { %>
       &nbsp;
    <%} %>
    </td>


    <td align="center">
      <logic:greaterThan name="item" property="maxOrderQty" value="-1">
        <bean:write name="item" property="maxOrderQty"/>
      </logic:greaterThan>
    </td>

   <td class="text"><div class="fivemargin">
     <% Date curDate = Constants.getCurrentDate();
        Date effDate = item.getProduct().getEffDate();
        Date expDate = item.getProduct().getExpDate();
        if(effDate != null && effDate.compareTo(curDate)<=0 &&
          (expDate==null || expDate.compareTo(curDate)>0)) {
     %>

<% /* do not allow a quantity if the item is no longer on the
      contract. */
 %>

<logic:equal name="item" property="contractFlag" value="true">
      <logic:equal name="SHOP_FORM" property="allowPurchase" value="true">
        <html:text name="SHOP_FORM" property="quantityDetail" value="<%=theForm.getQuantityDetail()%>" size="3"/>
      </logic:equal>
</logic:equal>

<logic:equal name="item" property="contractFlag" value="false">
       &nbsp;
</logic:equal>

      <logic:equal name="SHOP_FORM" property="allowPurchase" value="false">
       &nbsp;
      </logic:equal>
     <% } else { %>
       <app:storeMessage key="shoppingItems.text.n_sl_a"/>
     <% } %>
   </div></td>
   <!-- Distributor inventory Quantity -->
   <td class="text"><div class="fivemargin">
  <%
  String distInvQtyS = "-";
  if(RefCodeNames.DIST_INVENTORY_DISPLAY.SHOW_FLAG.equals(showDistInventory)) {
    int distInvQty = item.getDistInventoryQty();
    if(distInvQty==0) distInvQtyS = ClwI18nUtil.getMessage(request,"global.text.u",null);
    if(distInvQty>0) distInvQtyS = ClwI18nUtil.getMessage(request,"global.text.a",null);

  %>
    <%=distInvQtyS%>&nbsp;
  <% } else if(RefCodeNames.DIST_INVENTORY_DISPLAY.SHOW_QUANTITIES.equals(showDistInventory)) {
    int distInvQty = item.getDistInventoryQty();
    if (distInvQty>=0) distInvQtyS = String.valueOf(distInvQty);
  %>
    <%=distInvQtyS%>&nbsp;
  <% } else { %>
    &nbsp;
  <% } %>
   </div></td>
   <td class="text"><div class="fivemargin">
<logic:equal name="item" property="contractFlag" value="true">
   <%=item.getActualSkuNum()%>
</logic:equal>

<logic:equal name="item" property="contractFlag" value="false">
 <app:storeMessage key="shoppingItems.text.discontinued"/>
</logic:equal>

   </div>
</td>
   <td class="text"><div class="fivemargin">
     <%=skuDesc%>
   </div></td>
   <td class="text"><div class="fivemargin">
     <bean:write name="item" property="product.size"/>
   </div></td>
   <td class="text"><div class="fivemargin">
     <bean:write name="item" property="product.pack"/>
   </div></td>
   <td class="text"><div class="fivemargin">
     <bean:write name="item" property="product.uom"/>
   </div></td>
   <td class="text"><div class="fivemargin">
     <bean:write name="item" property="product.color"/>
   </div></td>
   <td class="text"><div class="fivemargin">
     <logic:equal name="SHOP_FORM" property="showPrice" value="true">
     <bean:define id="itemPrice" name="item" property="price"/>
     <%=ClwI18nUtil.getPriceShopping(request,itemPrice,"&nbsp;")%>
     <logic:equal name="item" property="contractFlag" value="true">
     <logic:equal name="SHOP_FORM" property="showWholeCatalog" value="true">
     *
     </logic:equal>
     </logic:equal>
     </logic:equal>
   </div></td>
   </tr>

  <!--------- -------->
  <tr><td colspan="12" class="tableoutline"><img src="<%=IMGPath%>/cw_spacer.gif" height="3"></td></tr>
  <tr>
    <logic:equal name="SHOP_FORM" property="allowPurchase" value="true">
    <td colspan="12">
        <% if (ShopTool.isModernInventoryShopping(request)
              && !ShopTool.hasDiscretionaryCartAccessOpen(request)) { %>
            <% String add_img = IMGPath + "/b_addtocart.gif"; %>
           <a href="#" class="linkButton" onclick="setCommandAndSubmit('toInvCart');">
                <img src="<%=add_img%>" border="0"/>
                <app:storeMessage key="global.label.addToInventoryCart"/>
            </a>
    <%} else { %>
      <a href="#" class="linkButton" onclick="actionMultiSubmit('BBBBBBB', 'og_addToCart');"
      ><img src='<%=IMGPath + "/b_addtocart.gif"%>' border="0"/><app:storeMessage key="global.label.addToDiscretionaryCart"/></a>
    <%}%>
    &nbsp;&nbsp;
    <a href='<%="shop.do?action=itemClear&source="+source%>' class="linkButton"
      ><img src="<%=IMGPath%>/b_clearqty.gif" border="0"/><app:storeMessage key="global.label.clearQuantities"/></a>
    </td>
    </logic:equal>
    <logic:equal name="SHOP_FORM" property="allowPurchase" value="false">
    <td colspan="12">
      &nbsp;
    </td>
    </logic:equal>
  </tr>

  </table>
  </td>
  <td class="tableoutline" width="1" bgcolor="black">
  <img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1">
  </td>
  </tr>
  <html:hidden property="action" value="BBBBBBB"/>
  <html:hidden property="action" value="CCCCCCC"/>
  <html:hidden property="command" value=""/>
  </html:form>
</table>
</td>
</tr>
<tr>
<td>

<jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_table_bottom1.jsp")%>'/>
