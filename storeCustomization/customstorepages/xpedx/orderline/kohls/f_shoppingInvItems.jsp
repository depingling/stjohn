<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ShopTool" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<script language="JavaScript1.2">
    <!--
    function f_SetChecked(val, prop) {
        dml = document.CHECKOUT_FORM;
        len = dml.elements.length;
        var i = 0;
        for (i = 0; i < len; i++) {
            if (dml.elements[i].name == prop && dml.elements[i].disabled != true) {
                dml.elements[i].checked = val;
            }
        }
    }

    //-->
</script>

<bean:define id="theForm" name="INVENTORY_SHOPPING_CART_FORM" type="com.cleanwise.view.forms.ShoppingCartForm" scope="session"/>

<%
    CleanwiseUser appUser = ShopTool.getCurrentUser(request);
    int COL_COUNT = 0;
%>

<table cellpadding="0" cellspacing="0" align="center"    width="100%">

<tr>
    <% boolean inventoryShopping = true;
       boolean resaleItemsAllowed = false;
    %>


    <% if (inventoryShopping) {

    %>

    <td width=15 align="left" class="shopcharthead">&nbsp;<%COL_COUNT++;%></td>

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

  <td class="shopcharthead" align="center">
    <app:storeMessage key="shoppingItems.text.maxOrderQty"/>
    <%COL_COUNT++;%>
  </td>

    <td class="shopcharthead" align="center">
         &nbsp;<app:storeMessage key="shoppingItems.text.qty"/>
         <%COL_COUNT++;%>
    </td>

    <td class="shopcharthead" align="center">
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

    <% if (appUser.getUserAccount().isShowSPL()) { %>
    <td class="shopcharthead">
        <div class="fivemargin">
            <app:storeMessage key="shoppingItems.text.spl"/>
        </div>
     <%COL_COUNT++;%>
    </td>
    <%} %>

    <td class="shopcharthead">&nbsp;<%COL_COUNT++;%></td>

    <% if (resaleItemsAllowed) { %>
    <td class="shopcharthead">
        <div class="fivemargin">
            <font color="red">
                <app:storeMessage key="shoppingItems.text.reSaleItem"/>
            </font>
         <%COL_COUNT++;%>
        </div>
    </td>
    <% } %>
    <td width=15 align="left" class="shopcharthead">
        <app:storeMessage key="global.action.label.select"/>
        <%COL_COUNT++;%>
    </td>
</tr>

<tr>
    <td colspan="<%=COL_COUNT%>" class="tableoutline"><img src="/images/cw_spacer.gif" height="1"></td>
</tr>



<%
    {
%>
     <jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_inv_cart_oitems.jsp")%>'>
        <jsp:param name="jspFormName"value="INVENTORY_SHOPPING_CART_FORM"/>
        <jsp:param name="itemFilter" value="inventory"/>
        <jsp:param name="colCount" value="<%=COL_COUNT%>"/>
        <jsp:param name="keyName" value="shop.og.table.header.title.inventory"/>
    </jsp:include>

    <jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_inv_cart_oitems.jsp")%>'>
        <jsp:param name="jspFormName"value="INVENTORY_SHOPPING_CART_FORM"/>
        <jsp:param name="itemFilter" value="regular"/>
        <jsp:param name="colCount" value="<%=COL_COUNT%>"/>
        <jsp:param name="keyName" value="shop.og.table.header.title.regular"/>
    </jsp:include>

<%
    }
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

