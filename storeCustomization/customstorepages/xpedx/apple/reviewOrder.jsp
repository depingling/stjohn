<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.GregorianCalendar" %>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.util.Locale" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.view.utils.ShopTool" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<script language="JavaScript1.2">
<!--

function setAndSubmit(vv, value) {

 var aaa = document.forms['CHECKOUT_FORM'].elements[vv];
 aaa.value=value;
 aaa.form.submit();
 return false;

}
//-->

</script>

<style>
.checkoutSubHeader   {
        color: #FFFFFF;
        background-color: #669999;
        font-size: 14px;
        font-weight: bold;
        TEXT-DECORATION: none;
        padding-left: .5em;
        padding-right: .5em;

}

.checkoutSummaryHeader   {
        color: #336666;
        background-color: #99cccc;
        font-size: 12px;
        font-weight: bold;
        TEXT-DECORATION: none;
        padding-left: .5em;
        padding-right: .5em;
        border-width: 1px;
        border-style: solid;
        border-color: #336666;

}
</style>

<bean:define id="theForm" name="CHECKOUT_FORM"
  type="com.cleanwise.view.forms.CheckoutForm"/>
<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>

<%
boolean inventoryShopping = ShopTool.isInventoryShoppingOn(request);
CleanwiseUser appUser = ShopTool.getCurrentUser(request);
%>

<%String submit ="/store/checkout.do?action=submit";%>
<html:form action="<%=submit%>">

<html:hidden name="CHECKOUT_FORM" property="siteId"
  value="<%=String.valueOf(theForm.getSite().getSiteId())%>" />

<table    cellspacing="0"   cellpadding="0"
  class="tbstd"
   width="<%=Constants.TABLEWIDTH%>">


<% if (appUser.getUserAccount().isShowSPL()) {
    boolean isNonSPLExists = false;
    //java.util.List items = ShopTool.getCurrentShoppingCart(request).getItems();
    ShoppingCartItemDataVector items = theForm.getItems();
    if (items != null) {
      for (int i=0; i<items.size(); i++) {
        ShoppingCartItemData item = (ShoppingCartItemData)items.get(i);
        if (!com.cleanwise.service.api.util.Utility.isTrue(item.getProduct().getCatalogDistrMapping().getStandardProductList())) {
          isNonSPLExists = true;
          break;
        }
      }
    }
    if (isNonSPLExists) {
%>
<tr>
<td colspan="3" align="center" style="padding-top: 2em; padding-bottom: 2em;"><b><app:storeMessage key="shop.checkout.text.nonSPLItemsWarning"/></b></td>
</tr>
<%}} %>


   <!-- MAIN table -->
<tr><td style="padding: 2em;" colspan="3">
<jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_review_items_note.jsp")%>'/>
  </td>
</tr>

<tr><td><table cellspacing=2 cellpadding=2>
<tr>
<td><b><app:storeMessage key="shop.checkout.text.shippingComments"/></b></td>
<td>
<bean:write name="CHECKOUT_FORM" property="comments"/>
<html:hidden name="CHECKOUT_FORM" property="comments"/>
</td>
</tr>


<logic:equal name="<%=Constants.APP_USER%>" property="site.hasBudgets" value="true">
    <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.EXCLUDE_ORDER_FROM_BUDGET%>">
        <tr>
        <td><b><app:storeMessage key="shop.budgetInfo.text.oderWillNotAffectBudgetButWillRequireApproval"/></b></td>
        <td>
        <bean:write name="CHECKOUT_FORM" property="bypassBudget"/>
        <html:hidden name="CHECKOUT_FORM" property="bypassBudget"/>
        </td>
        </tr>
   </app:authorizedForFunction>
</logic:equal>





<%
/* -- Order Contact fields -- */
 CleanwiseUser appU = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
if ( appU.isaCustServiceRep()) {
%>
<tr><td class="text"><b><app:storeMessage key="shop.checkout.text.contactName"/></b></td>
<td>
<bean:write name="CHECKOUT_FORM" property="orderContactName" />
<html:hidden name="CHECKOUT_FORM" property="orderContactName" />
</td></tr>

<tr><td class="text"><b><app:storeMessage key="shop.checkout.text.contactPhoneNum"/></b></td>
<td>
<bean:write name="CHECKOUT_FORM" property="orderContactPhoneNum" />
<html:hidden name="CHECKOUT_FORM" property="orderContactPhoneNum" />
</td></tr>

<tr><td class="text"><b><app:storeMessage key="shop.checkout.text.contactEmail"/></b></td>
<td>
<bean:write name="CHECKOUT_FORM" property="orderContactEmail" />
<html:hidden name="CHECKOUT_FORM" property="orderContactEmail" />
</td></tr>

<tr><td class="text"><b><app:storeMessage key="shop.checkout.text.method"/></b></td>
<td>
<bean:write name="CHECKOUT_FORM"  property="orderOriginationMethod"/>
<html:hidden name="CHECKOUT_FORM"  property="orderOriginationMethod"/>
</td></tr>

<%}%>

<app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.VIEW_PARTIAL_ORD_CREDIT_CARD%>">
<tr>
<td class="text">
<b><app:storeMessage key="shop.checkout.text.confirmationOnlyOrder:"/></b></td>
<td>
<bean:write name="CHECKOUT_FORM" property="billingOrder"/>
<html:hidden name="CHECKOUT_FORM" property="billingOrder"/>
</td>
</tr>
</app:authorizedForFunction>
<%if ( appU.isaCustServiceRep()) {%>
<tr>
<td>
<b><app:storeMessage key="shop.checkout.text.originalPONumber"/></b>
</td>
<td>
<bean:write name="CHECKOUT_FORM" property="billingOriginalPurchaseOrder"/>
<html:hidden name="CHECKOUT_FORM" property="billingOriginalPurchaseOrder"/>
</td>
</tr>
<tr>
<td>
<b><app:storeMessage key="shop.checkout.text.distributorInvoiceNumber:"/></b>
</td>
<td>

<bean:write name="CHECKOUT_FORM" property="billingDistributorInvoice"/>
<html:hidden name="CHECKOUT_FORM" property="billingDistributorInvoice"/>
</td>
</tr>
<tr>
<td>
<b><app:storeMessage key="shop.checkout.text.bypasSmallOrderRouting"/></b>
</td>
<td>
<bean:write name="CHECKOUT_FORM" property="bypassOrderRouting"/>
<html:hidden name="CHECKOUT_FORM" property="bypassOrderRouting"/>
</td>
</tr>

<tr>
<td class="text" colspan=2>
<b><app:storeMessage key="shop.checkout.text.customerRequestedReshipmentOrderNumber"/></b><br>
<bean:write name="CHECKOUT_FORM" property="customerRequestedReshipOrderNum"/>
<html:hidden name="CHECKOUT_FORM" property="customerRequestedReshipOrderNum"/>
</td>
</tr>

<% } %>
<%-- Order Contact fields ------------------------ --%>

</table></td></tr>


    <tr>
    <td>
    <a href="#" class="linkButton" onclick="setAndSubmit('command','placeOrderForAll');"
    ><img src='<%=IMGPath + "/b_placeorder.gif"%>' border="0"/><app:storeMessage key="global.label.placeOrder"/></a>
    </td>
    </tr>
</table>


<table    cellspacing="0"   cellpadding="1"  class="tbstd"
   width="<%=Constants.TABLEWIDTH%>"> <!-- MAIN table -->

<%@ include file="f_item_table_headers.jsp" %>


  <logic:iterate id="scartItem" indexId="IDX"
     name="CHECKOUT_FORM" property="items"
     type="com.cleanwise.service.api.value.ShoppingCartItemData">




<!-- START: item display <%=IDX%> -->

<tr bgcolor="<%=ClwCustomizer.getEvenRowColor(session,(Integer)IDX)%>">

<%
 if (inventoryShopping) {
%>

 <% if ( scartItem.getIsaInventoryItem() ) { %>
<td  width=15>
<span class="inv_item">
i
<% if (ShopTool.getCurrentSite(request).isAnInventoryAutoOrderItem
  (scartItem.getProduct().getProductId())) { %>
a
<% } %>
</span>
</td>
  <td width=30 align="center" class="invqty_box">   <%=scartItem.getInventoryParValue()%>  </td>
  <td width=30 align="center">   <%=scartItem.getInventoryQtyOnHand()%>  </td>
  <% } else {%>

  <td width=30>&nbsp;</td>
  <td width=30 align="center" class="invqty_box">-</td>
  <td width=30 align="center">-</td>

  <% } %>
  <% } %>

  <td align="center"><%=scartItem.getQuantity()%> </td>

 <!-- Distributor inventory Quantity -->
  <%
  String distInvQtyS = "-";
  //String showDistInventory = ShopTool.getShowDistInventoryCode(request);
  if(RefCodeNames.DIST_INVENTORY_DISPLAY.SHOW_FLAG.equals(showDistInventory)) {
    int distInvQty = scartItem.getDistInventoryQty();
    if(distInvQty==0) distInvQtyS = ClwI18nUtil.getMessage(request,"global.text.u",null);
    if(distInvQty>0) distInvQtyS = ClwI18nUtil.getMessage(request,"global.text.a",null);

  %>
<td class="text"><div class="fivemargin"><%=distInvQtyS%>&nbsp;</div></td>
  <% } %>
  <%
  if(RefCodeNames.DIST_INVENTORY_DISPLAY.SHOW_QUANTITIES.equals(showDistInventory)) {
    int distInvQty = scartItem.getDistInventoryQty();
    if (distInvQty>=0) distInvQtyS = String.valueOf(distInvQty);
  %>
<td class="text"><div class="fivemargin"><%=distInvQtyS%>&nbsp;</div></td>
  <% } %>

     <td class="text"><div class="fivemargin">
       <bean:write name="scartItem" property="actualSkuNum"/>&nbsp;
     </div></td>
     <td class="text"><div class="fivemargin">
<%
String itemLink =
  "shop.do?action=item&source=shoppingCart"
    + "&itemId="+scartItem.getProduct().getProductId()
    + "&qty="+scartItem.getQuantity();
%>
     <html:link href="<%=itemLink%>">
       <bean:write name="scartItem" property="product.catalogProductShortDesc"/>&nbsp;
     </html:link>
     </div></td>
     <td class="text"><div class="fivemargin">
       <bean:write name="scartItem" property="product.pack"/>&nbsp;
     </div></td>
     <td class="text"><div class="fivemargin">
       <bean:write name="scartItem" property="product.uom"/>&nbsp;
     </div></td>

<td  align="right" class="text"><div class="fivemargin">
<logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">
  <bean:define id="price"  name="scartItem" property="price"/>
  <%=ClwI18nUtil.getPriceShopping(request,price,"<br>")%>
  <logic:equal name="scartItem" property="contractFlag" value="true">
    <logic:equal name="<%=Constants.APP_USER%>" property="showWholeCatalog"
      value="true">*</logic:equal>
  </logic:equal>
</logic:equal>

<logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="false">
&nbsp;</logic:equal>

</div>
</td>

<bean:define id="finalLineAmount"  name="scartItem" property="amount"/>
<td align="right" class="text"><div class="fivemargin">
<logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">
<%=ClwI18nUtil.getPriceShopping(request,finalLineAmount,"<br>")%>
</logic:equal>

<% if (appUser.getUserAccount().isShowSPL()) { %>
<td align="center" class="text"><div>
      <logic:present name="scartItem" property="product.catalogDistrMapping.standardProductList">
        <bean:define id="spl" name="scartItem" property="product.catalogDistrMapping.standardProductList" type="java.lang.String"/>
        <%if(com.cleanwise.service.api.util.Utility.isTrue(spl)){%>
          <app:storeMessage key="shoppingItems.text.y"/>
        <%}else{%>
          <app:storeMessage key="shoppingItems.text.n"/>
        <%}%>
      </logic:present>
      <logic:notPresent name="scartItem" property="product.catalogDistrMapping.standardProductList">
        <app:storeMessage key="shoppingItems.text.n"/>
      </logic:notPresent>
</div></td>
<%} %>



<logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="false">
&nbsp;
</logic:equal>
</div></td>

</tr>
<!-- END: item display <%=IDX%> -->
  </logic:iterate>
</table>

<%@ include file="f_order_summary.jsp" %>

<table    cellspacing="0"   cellpadding="0"
  class="tbstd"   width="<%=Constants.TABLEWIDTH%>">
    <tr><td>
    <a href="#" class="linkButton" onclick="setAndSubmit('command','placeOrderForAll');"
    ><img src='<%=IMGPath + "/b_placeorder.gif"%>' border="0"/><app:storeMessage key="global.label.placeOrder"/></a>
    </td></tr>
</table>

<jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_table_bottom1.jsp")%>'/>

<html:hidden property="action" value="submit"/>
<html:hidden property="command" value="undefined"/>

</html:form>





