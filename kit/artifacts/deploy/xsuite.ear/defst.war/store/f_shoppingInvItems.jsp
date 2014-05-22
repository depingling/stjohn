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


    boolean
      viewOptionInventoryList = true,
      quickOrderView = false,
      checkoutView = false,
      editCartItems = true,
      shoppingCartView = true;

%>
<app:commonDefineProdColumnCount
    id="colCountInt"
    viewOptionEditCartItems="<%=editCartItems%>"
    viewOptionQuickOrderView="<%=quickOrderView%>"
    viewOptionAddToCartList="true"
    viewOptionOrderGuide="false"
    viewOptionShoppingCart="<%=shoppingCartView%>"
    viewOptionInvShoppingCart="true"
    viewOptionInventoryList="<%=viewOptionInventoryList%>"
/>

<%int COL_COUNT = ((Integer)pageContext.getAttribute("colCountInt")).intValue();%>


<table cellpadding="0" cellspacing="0" align="center"    width="100%">
<%--------------------------------- HEADER -----------------------------------------%>

   <tr>

  <app:commonDisplayProdHeader
          viewOptionEditCartItems="<%=editCartItems%>"
          viewOptionQuickOrderView="<%=quickOrderView%>"
          viewOptionAddToCartList="true"
          viewOptionOrderGuide="false"
          viewOptionShoppingCart="<%=shoppingCartView%>"
          viewOptionInvShoppingCart="true"
          viewOptionCheckout="<%=checkoutView%>"
          viewOptionInventoryList="<%=viewOptionInventoryList%>"
    />

   </tr>

<%-----------------------------------------------------------------------------------------%>


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
        <jsp:param name="tabnum" value="1"/>
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

