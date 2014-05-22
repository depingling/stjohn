<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.GregorianCalendar" %>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
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

<bean:define id="theForm" name="CHECKOUT_FORM" type="com.cleanwise.view.forms.CheckoutForm"/>
<bean:define id="account" name="CHECKOUT_FORM" property="account" type="com.cleanwise.service.api.value.AccountData"/>
<bean:define id ="invShoppingCart" name="<%=Constants.INVENTORY_SHOPPING_CART%>" type="com.cleanwise.service.api.value.ShoppingCartData"/>
<bean:define id ="site" name="CHECKOUT_FORM"  property="site" type="com.cleanwise.service.api.value.SiteData"/>

<%
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    boolean inventoryShopping = ShopTool.isInventoryShoppingOn(request);
    int colIdx = 0;
    int rowIdx = 0;
    boolean firstrow = true;
    String prevCategory = "";
    String thisItemCategory = "";
    int COL_COUNT = 0;
%>

<script language="JavaScript1.2">
<!--

function setAndSubmit(fid, vv, value) {
 var aaa = document.forms[fid].elements[vv];
 aaa.value=value;
 aaa.form.submit();
 return false;
 }

function viewPrinterFriendly(oid) {
  var loc = "printerFriendlyOrder.do?action=printPdf&orderId=" + oid;
  <% if (appUser.getUserAccount().isHideItemMfg()) { %>
    loc = loc + "&showMfg=n&showMfgSku=n";
  <% } %>

  prtwin = window.open(loc,"printer_friendly",
    "menubar=yes,resizable=yes,scrollbars=yes,toolbar=yes,status=yes,height=500, width=775,left=100,top=165");
  prtwin.focus();

  return false;
}
//-->

</script>


<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>
<bean:define id="clwWorkflowRole" type="java.lang.String" name="ApplicationUser" property="user.workflowRoleCd"/>

<html:form styleId="invrelease" action="/store/earlyRelease.do?action=earlyrelease" name="CHECKOUT_FORM" method="POST">

<table class="tbstd" width="<%=Constants.TABLEWIDTH%>" cellpadding=0 cellspacing=0>
    <% if (request.getAttribute("org.apache.struts.action.ERROR") != null) { %>
    <tr>
        <td class="genericerror" align="center">
            <html:errors/>
        </td>
    </tr>
    <% } %>
    <tr>
        <td class="checkoutSubHeader" valign="top">
            <app:storeMessage key="shoppingCart.text.scheduledCart"/>
        </td>
    </tr>
</table>

<table class="tbstd" width="<%=Constants.TABLEWIDTH%>" cellpadding=0 cellspacing=0>
    <logic:equal name="CHECKOUT_FORM" property="itemsSize" value="0">
        <!-- No items in the shopping cart -->
        <% if (request.getAttribute("org.apache.struts.action.ERROR") == null) { %>
        <tr class="orderInfoHeader">
            <td class="text" align="center">
                <br><b>
                <app:storeMessage key="shop.checkout.text.shoppiningCartIsEmpty"/>
            </b><br>&nbsp;
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
<table class="tbstd" width="<%=Constants.TABLEWIDTH%>" cellpadding=0 cellspacing=0>

    <%
        ProcessOrderResultData orderRes = theForm.getOrderResult();
        if (orderRes != null) {
            pageContext.setAttribute("orderRes", orderRes);
            String sts = orderRes.getOrderStatusCd(); %>
    <tr>
        <td>
            <b>
                <app:storeMessage key="shop.checkout.text.yourOrderHasBeenPlaced"/>
            </b>  &nbsp;&nbsp;&nbsp;&nbsp;
        </td>
        <td>
           <b>
               <app:storeMessage key="shop.checkout.text.confirmationNumber"/>
           </b>&nbsp;&nbsp;&nbsp;&nbsp;
         <span style="font-size: 150%;">
                <b>  <bean:write name="orderRes" property="orderNum"/>  </b>
         </span>
        </td>
        <td>
            &nbsp;&nbsp;&nbsp;&nbsp;
            <b>
            <app:storeMessage key="shop.checkout.text.orderDate"/>
            &nbsp;&nbsp;&nbsp;&nbsp;
            <%=ClwI18nUtil.formatDate(request, orderRes.getOrderDate(), DateFormat.FULL)%>
            </b>
            &nbsp;&nbsp;&nbsp;&nbsp;
            <% String b_print = IMGPath + "/b_print.gif"; %>
            <a href="#" class="linkButton" onclick="viewPrinterFriendly('<%=orderRes.getOrderId()%>');">
            <img src="<%=b_print%>" border="0"/><app:storeMessage key="global.label.printerFriendly"/></a>
        </td>
        </tr>
        <% if (!appUser.getUserAccount().hideShippingComments()) { %>
        <tr>
            <td class="text" valign="top" colspan='3' align="left">
                <b>
                    <app:storeMessage key="shop.checkout.text.shippingComments"/>
                </b>
                <bean:write name="CHECKOUT_FORM" property="comments"/>
            </td>
        </tr>
        <%} %>
            <%} else { %>

        <tr>
            <td>&nbsp;</td>
        </tr>

        <tr>
            <td>
              <jsp:include flush='true' page="f_budgetInfoInc.jsp">
                                <jsp:param name="jspFormName" value="CHECKOUT_FORM"/>
                                <jsp:param name="formId" value="invrelease"/>
                            </jsp:include>

            </td>
        </tr>
        <% if (!appUser.getUserAccount().hideShippingComments()) { %>
        <tr>
            <td><b>
                <app:storeMessage key="shop.checkout.text.shippingComments"/>
            </b>
                <html:text name="CHECKOUT_FORM" property="comments" size="60" maxlength="255"/>
            </td>
        </tr>
            <%}%>
      <%}%>
</table>

<table class="tbstd" width="<%=Constants.TABLEWIDTH%>" cellpadding=0 cellspacing=0>
<tr>
    <td>&nbsp;</td>
</tr>
<logic:greaterThan name="CHECKOUT_FORM" property="itemsSize" value="0">
<tr>
<td>
<table  width="100%" cellpadding="0" cellspacing="0">
<tr>

    <% if (inventoryShopping) {   %>

    <td width=15 align="left" class="shopcharthead">&nbsp;<%COL_COUNT++;%></td>

    <td width=30 align="center" class="shopcharthead">
        <app:storeMessage key="shoppingItems.text.par"/>
       <%COL_COUNT++;%>
     </td>

     <td width=30 class="shopcharthead">
        <app:storeMessage key="shoppingItems.text.onHand"/>
       <%COL_COUNT++;%>
    </td>
    <% }%>

    <%
        String showDistInventory = ShopTool.getShowDistInventoryCode(request);
        if (RefCodeNames.DIST_INVENTORY_DISPLAY.SHOW_FLAG.equals(showDistInventory) ||
                RefCodeNames.DIST_INVENTORY_DISPLAY.SHOW_QUANTITIES.equals(showDistInventory)) {

    %>
    <td class="shopcharthead">
        <div class="fivemargin">
            <app:storeMessage key="shoppingItems.text.distInv"/>
          <%COL_COUNT++;%>
        </div>
    </td>
    <% } %>
      <td class="shopcharthead">
          <app:storeMessage key="shoppingItems.text.qty"/>
          <%COL_COUNT++;%>
      </td>

    <td class="shopcharthead">
        <div class="fivemargin">
            <app:storeMessage key="shoppingItems.text.ourSkuNum"/>
        </div>
        <%COL_COUNT++;%>
    </td>

    <td class="shopcharthead">
        <div class="fivemargin">
            <app:storeMessage key="shoppingItems.text.productName"/>
        </div>
        <%COL_COUNT++;%>
    </td>

    <td class="shopcharthead">
        <div class="fivemargin">
            <app:storeMessage key="shoppingItems.text.size"/>
        </div>
      <%COL_COUNT++;%>
    </td>

    <td class="shopcharthead">
        <div class="fivemargin">
            <app:storeMessage key="shoppingItems.text.pack"/>
        </div>
      <%COL_COUNT++;%>
    </td>

    <td class="shopcharthead">
        <div class="fivemargin">
            <app:storeMessage key="shoppingItems.text.uom"/>
        </div>
      <%COL_COUNT++;%>
    </td>

   <% if (!appUser.getUserAccount().isHideItemMfg()) { %>
    <td class="shopcharthead">
        <div class="fivemargin">
        <app:storeMessage key="shoppingItems.text.manufacturer"/>
        </div>
    <%COL_COUNT++;%>
    </td>
   <td class="shopcharthead">
        <div class="fivemargin"><app:storeMessage key="shoppingItems.text.mfgSkuNum"/></div>
    <%COL_COUNT++;%>
    </td>
    <% } %>

    <logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">

        <td class="shopcharthead">
            <div class="fivemargin">
                <app:storeMessage key="shoppingItems.text.price"/>
            </div>
         <%COL_COUNT++;%>
        </td>

        <td class="shopcharthead">
            <div class="fivemargin">
                <app:storeMessage key="shoppingItems.text.amount"/>
            </div>
          <%COL_COUNT++;%>
        </td>

    </logic:equal>

    <logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="false">
        <td class="shopcharthead" align="center" colspan="2">&nbsp;
         <%COL_COUNT+=2;%>
        </td>
    </logic:equal>


</tr>
<logic:iterate id="item" name="CHECKOUT_FORM" property="items"
               offset="0" indexId="kidx"
               type="com.cleanwise.service.api.value.ShoppingCartItemData">

<bean:define id="itemId" name="item" property="product.productId" type="java.lang.Integer"/>


<% if (item.getIsaInventoryItem() == false) { %>
<% if (invShoppingCart.getOrderBy() == Constants.ORDER_BY_CATEGORY) {
    thisItemCategory = item.getCategoryPath();
    if (null == thisItemCategory) {
        thisItemCategory = "";
    }
    if (firstrow) { %>
<tr>
    <td colspan="13" class="shopcategory">
        &nbsp;&nbsp;&nbsp;&nbsp;
        <bean:write name="item" property="categoryPath"/>
    </td>
    <% prevCategory = thisItemCategory; %>
</tr>
<%
    firstrow = false;
} else if (!prevCategory.equals(thisItemCategory)) {
    prevCategory = thisItemCategory; %>
<tr>
    <td colspan="13" class="shopcategory">
        &nbsp;&nbsp;&nbsp;&nbsp;
        <bean:write name="item" property="categoryPath"/>
    </td>
</tr>
<% } %>
<% } %>
<% } else if (item.getIsaInventoryItem()) {
    if (invShoppingCart.getOrderBy() == Constants.ORDER_BY_CATEGORY) {

        thisItemCategory = item.getCategoryPath();
        if (null == thisItemCategory) {
            thisItemCategory = "";
        }
        if (firstrow) { %>
<tr>
    <td colspan="13" class="shopcategory">&nbsp;&nbsp;&nbsp;&nbsp;
        <bean:write name="item" property="categoryPath"/>
    </td>
    <% prevCategory = thisItemCategory; %>
</tr>
<%
    firstrow = false;
} else if (!prevCategory.equals(thisItemCategory)) { %>
<% prevCategory = thisItemCategory;%>
<tr>
    <td colspan="13" class="shopcategory">&nbsp;&nbsp;&nbsp;&nbsp;
        <bean:write name="item" property="categoryPath"/>
    </td>
</tr>
<% } %>
<% } %>
<% } %>


<tr bgcolor="<%=ClwCustomizer.getEvenRowColor(request.getSession(),rowIdx++)%>">
<%
    Date curDate = Constants.getCurrentDate();
    Date effDate = item.getProduct().getEffDate();
    Date expDate = item.getProduct().getExpDate();
    if (effDate != null && effDate.compareTo(curDate) <= 0 &&
            (expDate == null || expDate.compareTo(curDate) > 0)) {
%>
<% if (inventoryShopping && item.getIsaInventoryItem()) { %>
<td>
<span class="inv_item">
i
<% if (ShopTool.getCurrentSite(request).isAnInventoryAutoOrderItem(item.getProduct().getProductId())) { %>
a
<% } %>
</span>
    <%colIdx++;%>
</td>

<td>
    <bean:write name="item" property="inventoryParValue"/>
    <%colIdx++;%>
</td>



<td>
    <% if (item.getInventoryQtyIsSet()) {
        int newOnHandVal = 0;
        try {
            newOnHandVal = Integer.parseInt(item.getInventoryQtyOnHandString());
        }
        catch (Exception e) {
            newOnHandVal = 0;
        }
        item.setInventoryQtyOnHand(newOnHandVal);
    %>

    <bean:write name="item" property="inventoryQtyOnHand"/>
    <% } %>
    <%colIdx++;%>
</td>
<%} else {%>
<td>&nbsp;<%colIdx++;%></td>
<td>-<%colIdx++;%></td>
<td>-<%colIdx++;%></td>
<%}%>

<td>
    <bean:write name="item"
                property="quantityString"/>
    <%colIdx++;%>
</td>

<% } else { %>
<td colspan="3">
    N/A
    <%colIdx = colIdx + colIdx;%>
</td>
<% } %>

<td class="text" align="center">
    <bean:write name="item" property="actualSkuNum"/>
    <%colIdx++;%>
</td>
<td class="text" align="left">
    <bean:write name="item" property="product.catalogProductShortDesc"/>
    <%colIdx++;%>
</td>
<td class="text" align="left">
    <bean:write name="item" property="product.size"/>
    <%colIdx++;%>
</td>
<td class="text" align="center">
    <bean:write name="item" property="product.pack"/>
    <%colIdx++;%>
</td>
<td class="text" align="center">
    <bean:write name="item" property="product.uom"/>
    <%colIdx++;%>
</td>
<% if (!appUser.getUserAccount().isHideItemMfg()) { %>
<td class="text" align="center">
    <bean:write name="item" property="product.manufacturerName"/>
    <%colIdx++;%>
</td>
<td class="text" align="center">
    <bean:write name="item" property="product.manufacturerSku"/>
    <%colIdx++;%>
</td>
<% } %>
<td class="text">
    <div class="fivemargin">
        <bean:define id="price" name="item" property="price"/>
        <%=ClwI18nUtil.getPriceShopping(request, price, "<br>")%>
        <%colIdx++;%>
    </div>
</td>

<%
    java.math.BigDecimal finalLineAmount = null;
    if (item.getIsaInventoryItem()) {
        finalLineAmount = new java.math.BigDecimal
                (item.getPrice() * item.getInventoryOrderQty());
    } else {
        finalLineAmount =
                new java.math.BigDecimal(item.getAmount());
    }
%>

<td class="text" align="center">
    <bean:define id="amount" name="item" property="amount"/>
    <%=ClwI18nUtil.getPriceShopping(request, finalLineAmount, "<br>")%>
    <%colIdx++;%>
</td>
</tr>
<% /* End of line item */ %>

<!-- END: item display <%=kidx%> -->
</logic:iterate>
 </table>
</td>
</tr>
<tr>
<td valign=top align="center">     <!-- Table billto beg -->
<table>
<tr>
<td valign="top">
    <table border="0" cellspacing="0" cellpadding="0" align="center">
        <tr>
            <td class="checkoutSummaryHeader" WIDTH="243" HEIGHT="21">
                <app:storeMessage key="shop.checkout.header.summary"/>
            </td>
        </tr>
        <tr>
            <td class="smalltext">
                <div class="fivemargin">
                    <b>
                        <app:storeMessage key="shop.checkout.text.subtotal"/>
                    </b>
                    <bean:define id="itemsAmt" name="CHECKOUT_FORM" property="cartAmt"/>
                    <%=ClwI18nUtil.getPriceShopping(request, itemsAmt, " ")%>
                    <br>
                    <logic:greaterThan name="CHECKOUT_FORM" property="freightAmt" value="0">
                        <bean:define id="freightAmt" name="CHECKOUT_FORM" property="freightAmt"/>
                        <b><app:storeMessage key="shop.checkout.text.freight"/></b>
                        <%=ClwI18nUtil.getPriceShopping(request, freightAmt, "&nbsp;")%>
                        <br>
                    </logic:greaterThan>

                    <logic:greaterThan name="CHECKOUT_FORM" property="handlingAmt" value="0">
                        <bean:define id="handlingAmt" name="CHECKOUT_FORM" property="handlingAmt"/>
                        <b><app:storeMessage key="shop.checkout.text.handling"/></b>
                        <%=ClwI18nUtil.getPriceShopping(request, handlingAmt, "&nbsp;")%>
                        <br>
                    </logic:greaterThan>

                    <logic:greaterThan name="CHECKOUT_FORM" property="salesTax" value="0">
                      <bean:define id="tax" name="CHECKOUT_FORM" property="salesTax"/>
                      <b><app:storeMessage key="shop.checkout.text.tax"/></b>
                      <%=ClwI18nUtil.getPriceShopping(request,tax,"&nbsp;")%>
                     <br>
                    </logic:greaterThan>

                    <b><app:storeMessage key="shop.checkout.text.totalExcludingVOC"/></b>
                    <%
                        BigDecimal lTotal = theForm.getCartAmt().add(theForm.getFreightAmt());
                        lTotal = lTotal.add(theForm.getHandlingAmt());
                        lTotal = lTotal.add(theForm.getSalesTax());
                    %>
                    <%=ClwI18nUtil.getPriceShopping(request, lTotal, "&nbsp;")%>
                </div>
            </td>
        </tr>
    </table>
</td>
<td valign="top">
<table  border="0" cellspacing="0" cellpadding="0" align="center">
    <tr>
        <td class="checkoutSummaryHeader" WIDTH="243" HEIGHT="21">
            <app:storeMessage key="shop.checkout.header.billingInformation"/>
        </td>
    </tr>
    <tr>
            <td class="smalltext">
                <div class="fivemargin">
                    <b>
                        <app:storeMessage key="shop.checkout.text.accountName"/>
                    </b>&nbsp;<bean:write name="account" property="busEntity.shortDesc"/><br>
                    <b>
                        <app:storeMessage key="shop.checkout.text.address1"/>
                    </b>&nbsp;<bean:write name="account" property="billingAddress.address1"/><br>
                        <% String address2 = account.getBillingAddress().getAddress2(); %>
                        <% if(address2!=null && address2.trim().length()>0) { %>
                    <b>
                        <app:storeMessage key="shop.checkout.text.address2"/>
                    </b>&nbsp;<%=address2%><br>
                        <% } %>
                        <% String address3 = account.getBillingAddress().getAddress3(); %>
                        <% if(address3!=null && address3.trim().length()>0) { %>
                    <b>
                        <app:storeMessage key="shop.checkout.text.address3"/>
                    </b>&nbsp;<%=address3%><br>
                        <% } %>
                        <% String address4 = account.getBillingAddress().getAddress4(); %>
                        <% if(address4!=null && address4.trim().length()>0) { %>
                    <b>
                        <app:storeMessage key="shop.checkout.text.address4"/>
                    </b>&nbsp;<%=address4%><br>
                        <%


                        }
                         if (appUser.getUserStore().isStateProvinceRequired()) {



                        %>
                    <b>
                        <app:storeMessage key="shop.checkout.text.cityStateZip:"/>
                    </b>&nbsp;
                        <bean:write name="account" property="billingAddress.city"/>,
                        <bean:write name="account" property="billingAddress.stateProvinceCd"/>
                        <bean:write name="account" property="billingAddress.postalCode"/>
                        <%} else { %>
                    <b>
                        <app:storeMessage key="shop.checkout.text.cityZip:"/>
                    </b>&nbsp;
                        <bean:write name="account" property="billingAddress.city"/>,
                        <bean:write name="account" property="billingAddress.postalCode"/>
                        <%}%>
                    <br>
                    <b>
                        <app:storeMessage key="shop.checkout.text.country:"/>
                    </b>&nbsp;
                        <bean:write name="account" property="billingAddress.countryCd"/>
            </td>

        </tr>

    </table>
</td>
<td valign="top" >
<table   border="0" cellspacing="0" cellpadding="0" align="center">
        <tr>
            <td class="checkoutSummaryHeader" WIDTH="243" HEIGHT="21">
                <app:storeMessage key="shop.checkout.header.shippingInformation"/>
            </td>
        </tr>
        <tr>
            <td class="smalltext">
                <div class="fivemargin">
                    <b>
                        <app:storeMessage key="shop.checkout.text.name:"/>
                    </b>&nbsp;
                        <bean:write name="site" property="siteAddress.name1"/>&nbsp;
                        <bean:write name="site" property="siteAddress.name2"/>
                    <br>
                    <b>
                        <app:storeMessage key="shop.checkout.text.siteName"/>
                    </b>&nbsp;<bean:write name="site" property="busEntity.shortDesc"/><br>
                    <b>
                        <app:storeMessage key="shop.checkout.text.address1"/>
                    </b>&nbsp;<bean:write name="site" property="siteAddress.address1"/><br>
                        <% String siteAddress2 = site.getSiteAddress().getAddress2(); %>
                        <% if(siteAddress2!=null && siteAddress2.trim().length()>0) { %>
                    <b>
                        <app:storeMessage key="shop.checkout.text.address2"/>
                    </b>&nbsp;<%=siteAddress2%><br>
                        <% } %>
                        <% String siteAddress3 = site.getSiteAddress().getAddress3(); %>
                        <% if(siteAddress3!=null && siteAddress3.trim().length()>0) { %>
                    <b>
                        <app:storeMessage key="shop.checkout.text.address3"/>
                    </b>&nbsp;<%=siteAddress3%><br>
                        <% } %>
                        <% String siteAddress4 = site.getSiteAddress().getAddress4(); %>
                        <% if(siteAddress4!=null && siteAddress4.trim().length()>0) { %>
                    <b>
                        <app:storeMessage key="shop.checkout.text.address4"/>
                    </b>&nbsp;<%=siteAddress4%><br>
                        <%

                        }
                         if (appUser.getUserStore().isStateProvinceRequired()) {


                        %>
                    <b>
                        <app:storeMessage key="shop.checkout.text.cityStateZip:"/>
                    </b>&nbsp;
                        <bean:write name="site" property="siteAddress.city"/>,
                        <bean:write name="site" property="siteAddress.stateProvinceCd"/>
                        <bean:write name="site" property="siteAddress.postalCode"/>
                        <%} else { %>
                    <b>
                        <app:storeMessage key="shop.checkout.text.cityZip:"/>
                    </b>&nbsp;
                        <bean:write name="site" property="siteAddress.city"/>,
                        <bean:write name="site" property="siteAddress.postalCode"/>
                        <%}%>
                    <br>
                    <b>
                        <app:storeMessage key="shop.checkout.text.country:"/>
                    </b>&nbsp;
                        <bean:write name="site" property="siteAddress.countryCd"/>
            </td>
        </tr>

    </table>
</td>
</tr>
</table>
</td>
</tr>

<% if (orderRes == null) {%>
<tr>
    <td colspan="13">
        <table border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td>
                    <logic:equal name="ApplicationUser" property="allowPurchase" value="true">
                        <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.INVENTORY_EARLY_RELEASE%>">
                            <a href="#" class="linkButton" onclick="setAndSubmit('invrelease','command','placeOrder');">
                                <img src='<%=IMGPath + "/b_placeorder.gif"%>' border="0"/>
                                <app:storeMessage key="global.label.placeOrder"/>
                            </a>
                        </app:authorizedForFunction>
                    </logic:equal>
                </td>
            </tr>
        </table>
    </td>
</tr>
<%}%>
</logic:greaterThan>
</table>

<jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_table_bottom1.jsp")%>' />
<html:hidden property="command"  value="placeOrder" />
</html:form>
