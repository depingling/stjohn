
<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="java.util.Date" %>
<%@ page import="com.cleanwise.service.api.value.*"%>
<%@ page import="java.util.Locale" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<script language="JavaScript">
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

function setAndSubmit(fid, vv, value) {
  var aaa = document.forms[fid].elements[vv];
  aaa.value=value;
  aaa.form.submit();
  return false;

}
-->
</script>


<% String storeDir=ClwCustomizer.getStoreDir(); %>
<bean:define id="theForm" name="JANITOR_CLOSET_FORM" type="com.cleanwise.view.forms.JanitorClosetForm"/>
<% 
CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER); 
ShoppingCartData shoppingCart = (ShoppingCartData) session.getAttribute(Constants.SHOPPING_CART);
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

<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>
<jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_shopping_msgs.jsp")%>'>
    <jsp:param name="shoppingCartName" value="<%=Constants.SHOPPING_CART%>"/>
</jsp:include>
<jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_shopping_msgs.jsp")%>'>
    <jsp:param name="shoppingCartName" value="<%=Constants.INVENTORY_SHOPPING_CART%>"/>
</jsp:include>

<table align="center" border="0" cellpadding="1" cellspacing="0" width="<%=Constants.TABLEWIDTH%>">

<logic:equal name="JANITOR_CLOSET_FORM" property="cartItemsSize" value="0">
<tr>
<td class="tableoutline" width="1"><img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1"></td>
<td class="text" align="center">
  <b><app:storeMessage key="shop.janitorCloset.text.noItemsInJanitorCloset"/></b>
</td>
<td class="tableoutline" width="1"><img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1"></td>
</tr>
</logic:equal>

<logic:greaterThan name="JANITOR_CLOSET_FORM" property="cartItemsSize" value="0">

<tr>
<td class="tableoutline" width="1"><img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1"></td>
<td>
  <table align="center" border="0" cellpadding="0" cellspacing="0" width="98%">
  <html:form styleId='jct' action="/store/janitorCloset.do?action=janitorClosetSubmit">
  <tr bgcolor="#F0FFFF">
  <td colspan="10" class="text" align="center">
    
<b><app:storeMessage key="shop.janitorCloset.text.orderBy"/>&nbsp;
    <html:select name="JANITOR_CLOSET_FORM" property="orderBy" onchange="javascript:document.forms[0].submit();">          
          <html:option value="<%=\"\"+Constants.ORDER_BY_CATEGORY%>">           
            <app:storeMessage key="shop.janitorCloset.text.category"/>
          </html:option>
          <html:option value="<%=\"\"+Constants.ORDER_BY_CUST_SKU%>">           
            <app:storeMessage key="shop.janitorCloset.text.ourSku#"/>
          </html:option>
          <html:option value="<%=\"\"+Constants.ORDER_BY_NAME%>">               
            <app:storeMessage key="shop.janitorCloset.text.productName"/>
          </html:option>
        </html:select> 
  
  </td>
  </tr>
  <tr>
  <td colspan="10" class="tableoutline"><img src="/images/cw_spacer.gif" height="3"></td>  </tr>
  </tr>
  <!-- page contral bar -->
  <logic:equal name="<%=Constants.APP_USER%>" property="allowPurchase" value="true">
    <tr>
    <td colspan="10">
    <table border="0" cellpadding="0" cellspacing="0">
    <tr>
    <td>&nbsp;
    <%if (viewAddInvCart) {%>
          <% String add_img = IMGPath + "/b_addtocart.gif"; %>
              <a href="#" class="linkButton" onclick="setAndSubmit('jct','command','jct_addToModInvCart');">
                  <img src="<%=add_img%>" border="0"/>
                  <app:storeMessage key="global.label.addToInventoryCart"/>
              </a>
      <%}%>
    &nbsp;&nbsp;
    <a href='janitorCloset.do?action=janitorClosetClear' class="linkButton"
    ><img src="<%=IMGPath%>/b_clearqty.gif" border="0"/><app:storeMessage key="global.label.clearQuantities"/></a>
    </td>
   </tr>
   </table>

   </td>
    </tr>
  </logic:equal>
  <tr>
  <!-- Column heanders -->
  <td colspan="10" class="tableoutline"><img src="/images/cw_spacer.gif" height="1"></td>  </tr>
  </tr>
  <tr>
  <td class="shopcharthead" align="center">
    <logic:equal name="<%=Constants.APP_USER%>" property="allowPurchase" value="true">
      <app:storeMessage key="shoppingItems.text.qty"/>
    </logic:equal>
    <logic:equal name="<%=Constants.APP_USER%>" property="allowPurchase" value="false">
    &nbsp;
    </logic:equal>
   </td>
  <td class="shopcharthead" align="center"><app:storeMessage key="shoppingItems.text.ourSkuNum"/></td>
  <td class="shopcharthead" align="center"><app:storeMessage key="shoppingItems.text.productName"/></td>
  <td class="shopcharthead" align="center"><app:storeMessage key="shoppingItems.text.size"/></td>
  <td class="shopcharthead" align="center"><app:storeMessage key="shoppingItems.text.manufacturer"/></td>
  <logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">
    <td class="shopcharthead" align="center"><app:storeMessage key="shoppingItems.text.price"/></td>
    <td class="shopcharthead" align="center"><app:storeMessage key="shoppingItems.text.lastAmount"/></td>
  </logic:equal>
  <logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="false">
     <td class="shopcharthead" align="center"colspan="2">&nbsp;</td>
  </logic:equal>
  <td class="shopcharthead" align="center"><app:storeMessage key="shoppingItems.text.lastOrder"/></td>
  <td class="shopcharthead" align="center"><app:storeMessage key="shoppingItems.text.yearQty"/></td>
  <logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">
     <td class="shopcharthead" align="center"><app:storeMessage key="shoppingItems.text.yearAmount"/></td>
  </logic:equal>
  <logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="false">
     <td class="shopcharthead" align="center">&nbsp;</td>
  </logic:equal>

  </tr>
  <tr>
  <!-- Main table -->
  <td colspan="10" class="tableoutline"><img src="/images/cw_spacer.gif" height="1"></td>  
  </tr>
  <logic:iterate id="item" name="JANITOR_CLOSET_FORM" property="cartItems"
    indexId="kkk"
   type="com.cleanwise.service.api.value.ShoppingCartItemData">
     <bean:define id="itemId" name="item" property="product.productId"/>
     <bean:define id="quantityEl" value="<%=\"quantityElement[\"+kkk+\"]\"%>"/>
     <bean:define id="itemIdsEl" value="<%=\"itemIdsElement[\"+kkk+\"]\"%>"/>
     <html:hidden name="JANITOR_CLOSET_FORM" property="<%=itemIdsEl%>" value="<%=\"\"+itemId%>"/>
     <% if(theForm.getOrderBy()==Constants.ORDER_BY_CATEGORY) { %>
     <% if(theForm.isCategoryChanged(((Integer)kkk).intValue())) { %>
       <tr>
       <td class="shopcategory" colspan = "10"><bean:write name="item" property="categoryPath"/></td>
       </tr>
     <% } %>
     <% } %>
     <tr bgcolor="<%=ClwCustomizer.getEvenRowColor(session,(Integer)kkk)%>">
     <td class="text" align="center">
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
<logic:equal name="<%=Constants.APP_USER%>" 
  property="allowPurchase" value="true">
<html:text name="JANITOR_CLOSET_FORM" property="<%=quantityEl%>" size="3"/>
</logic:equal>

</logic:equal>


<logic:equal name="<%=Constants.APP_USER%>" property="allowPurchase" value="false">
&nbsp;
</logic:equal>

     <% } else { %>
       <app:storeMessage key="shoppingItems.text.n_sl_a"/>
     <% } %>
</td>

     <td class="text" align="center">

<logic:equal name="item" property="contractFlag" value="false">
<app:storeMessage key="shoppingItems.text.discontinued"/><br>
</logic:equal>
<bean:write name="item" property="actualSkuNum"/>
     </td>

     <td class="text" align="center">
<%String itemLink = "shop.do?action=item&source=janitorCloset&itemId="+itemId+"&qty="+item.getQuantity();%>
<logic:equal name="item" property="contractFlag" value="true">
     <html:link href="<%=itemLink%>">
       <bean:write name="item" property="product.catalogProductShortDesc"/>&nbsp;
     </html:link>
</logic:equal>

<logic:equal name="item" property="contractFlag" value="false">
       <bean:write name="item" property="product.catalogProductShortDesc"/>&nbsp;
</logic:equal>


     </td>
     <td class="text" align="center">
       <bean:write name="item" property="product.size"/>&nbsp;
     </td>
     <!-- manufacturer -->
     <td class="text" align="center">
         <bean:write name="item" property="product.manufacturerName"/>&nbsp;
     </td>
     <!-- price and last amount -->
     <logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">
       <td class="text" style="text-align: right; padding: 3px">
         <bean:define id="price"  name="item" property="price"/>
         <%=ClwI18nUtil.getPriceShopping(request,price,"<br>")%>
       <logic:equal name="item" property="contractFlag" value="true">
       <logic:equal name="<%=Constants.APP_USER%>" property="showWholeCatalog" value="true">
       *
       </logic:equal>
       </logic:equal>
       </td>
       <td class="text" style="text-align: right; padding: 3px">
         <bean:define id="lastAmt"  name="item" property="shoppingHistory.lastAmt"/>
         <%=ClwI18nUtil.getPriceShopping(request,lastAmt,"<br>")%>
       </td>
      </logic:equal>
      <logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="false">
       <td class="text" align="center">
       &nbsp;
       </td>
       <td class="text" align="center">
       &nbsp;
       </td>
       </logic:equal>
     <!-- Last order date -->
<%
if (item.getShoppingHistory().getLastDate() != null) {
%>
     <td class="text" align="right">
       <bean:define id="lastDate" name="item" property="shoppingHistory.lastDate" type="Date"/>
       <%=ClwI18nUtil.formatDateInp(request,lastDate)%>
     </td>
<% } else { %>
<td><app:storeMessage key="shoppingItems.text.noDate"/></td>
<% } %>

     <td class="text" align="center">
       <bean:write name="item" property="shoppingHistory.y2dQty"/>
     </td>
     <logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">
       <td class="text" align="right">
         <bean:define id="y2dAmt"  name="item" property="shoppingHistory.y2dAmt"/>
         <%=ClwI18nUtil.getPriceShopping(request,y2dAmt,"<br>")%> 
       </td>
     </logic:equal>
      <logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="false">
       <td class="text" align="center">
       &nbsp;
       </td>
     </logic:equal>
     </tr>
   </logic:iterate>
  <tr>
  <!-- Second control bar -->
  <td colspan="10" class="tableoutline"><img src="/images/cw_spacer.gif" height="3"></td>  </tr>
  <logic:equal name="<%=Constants.APP_USER%>" property="allowPurchase" value="true">
    <tr>
    <td colspan="10">
    <%if (viewAddInvCart) {%>
    &nbsp;&nbsp;
    <% String add_img = IMGPath + "/b_addtocart.gif"; %>
    <a href="#" class="linkButton" onclick="setAndSubmit('jct','command','jct_addToModInvCart');">
    <img src="<%=add_img%>" border="0"/>
    <app:storeMessage key="global.label.addToInventoryCart"/>
    </a> <%}%>
    &nbsp;&nbsp;
    <a href='janitorCloset.do?action=janitorClosetClear' class="linkButton"
    ><img src="<%=IMGPath%>/b_clearqty.gif" border="0"/><app:storeMessage key="global.label.clearQuantities"/></a>
    </td>
    </tr>
  </logic:equal>
  <html:hidden property="command" value="CCCCCCC"/>
  </html:form>
  </table>
  </td>
  <td class="tableoutline" width="1"><img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1"></td>
  </tr>
</logic:greaterThan>
</table>
</td>
</tr>
</table>

<%@ include file="f_table_bottom.jsp" %>     






