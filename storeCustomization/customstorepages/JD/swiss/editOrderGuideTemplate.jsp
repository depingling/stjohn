
<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="java.util.Date" %>
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

<bean:define id="theForm" name="EDIT_ORDER_GUIDE_FORM" type="com.cleanwise.view.forms.EditOrderGuideForm"/>

<%
String storeDir=ClwCustomizer.getStoreDir();
ShoppingCartData shoppingCart = (ShoppingCartData) session.getAttribute(Constants.SHOPPING_CART);
CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
SiteData thisSite = appUser.getSite();
String cw_spacer = ClwCustomizer.getSIP(request,"cw_spacer.gif");
String b_addtocart = ClwCustomizer.getSIP(request,"b_addtocart.gif");
String b_undo = ClwCustomizer.getSIP(request,"b_undo.gif");
String b_save = ClwCustomizer.getSIP(request,"b_save.gif");
String b_remove = ClwCustomizer.getSIP(request,"b_remove.gif");
String b_recalculate = ClwCustomizer.getSIP(request,"b_recalculate.gif");
String cw_shopunderlogo = ClwCustomizer.getSIP(request,"cw_shopunderlogo.gif");
String cw_left_footer_shop = ClwCustomizer.getSIP(request,"cw_left_footer_shop.gif");
String cw_middle_footer_shop = ClwCustomizer.getSIP(request,"cw_middle_footer_shop.gif");
String cw_right_footer_shop = ClwCustomizer.getSIP(request,"cw_right_footer_shop.gif");
int rowIdx = 0;
%>

<app:commonDefineProdColumnCount
    id="colCountInt"
            viewOptionEditCartItems="false"
            viewOptionQuickOrderView="false"
            viewOptionAddToCartList="true"
            viewOptionOrderGuide="false"
            viewOptionEditOrderGuide="true"
            viewOptionShoppingCart="false"
            viewOptionCheckout="false"
            viewOptionInventoryList="false"
/>

<%int numCols = ((Integer)pageContext.getAttribute("colCountInt")).intValue();%>


<table align="center" border="0" cellpadding="0" cellspacing="0" width="<%=Constants.TABLEWIDTH%>">
<html:form styleId="eogt" action="/store/editOrderGuide.do?action=editOrderGuideSubmit">
<html:hidden name="EDIT_ORDER_GUIDE_FORM" property="inputOrderGuideId" value="<%=\"\"+theForm.getOrderGuideId()%>"/>
<!-- Title -->

<tr>
<td class="tableoutline" width="1"><img src="<%=cw_spacer%>" height="1" width="1"></td>
<td><jsp:include flush='true'
    page='<%=ClwCustomizer.getStoreFilePath(request,"t_shopToolbar.jsp")%>'/></td>
<td class="tableoutline" width="1"><img src="<%=cw_spacer%>" height="1" width="1"></td>
</tr>
</table>
<!-- control -->
<table align="center" border="0" cellpadding="0" cellspacing="0" width="<%=Constants.TABLEWIDTH%>">
<tr>
<td class="tableoutline" width="1"><img src="<%=cw_spacer%>" height="1" width="1"></td>
<% if (theForm.getItemsSize() == 0) { %>
<td class="text" height=200 valign=top>
<% } else { %>
<td class="text" valign=top>
<% } %>
  <table><tr><td width="57px"></td>
  <td>

  <logic:equal name="EDIT_ORDER_GUIDE_FORM" property="userOrderGuideNumber" value="0">
    <app:storeMessage key="shop.editOg.text.noCustomOGCreaated"/>
  </logic:equal>


  <logic:greaterThan name="EDIT_ORDER_GUIDE_FORM" property="userOrderGuideNumber" value="0">
  <html:select name="EDIT_ORDER_GUIDE_FORM" property="orderGuideId"
   onchange="javascript: document.forms[0].submit();">
  <html:option value="-1"><app:storeMessage key="shop.editOg.text.myOrderGuides"/></html:option>
  <html:options  name="EDIT_ORDER_GUIDE_FORM" property="userOrderGuideIds" labelName="EDIT_ORDER_GUIDE_FORM" labelProperty="userOrderGuideNames"/>

  </html:select>
  </logic:greaterThan>


  </td>
  </tr>
</table>

</td>
<td class="tableoutline" width="1"><img src="<%=cw_spacer%>" height="1" width="1"></td>
</tr>
<tr>
<td class="tableoutline" width="1"><img src="<%=cw_spacer%>" height="1" width="1"></td>
<td class="text">


<logic:greaterThan name="EDIT_ORDER_GUIDE_FORM"
        property="orderGuideId" value="0">

<logic:greaterThan name="EDIT_ORDER_GUIDE_FORM" property="itemsSize" value="0">
  <table>


    <tr>
      <td width="23px"></td>
      <td>
        <b><app:storeMessage key="shop.editOg.text.editName"/></b>
        <html:text name="EDIT_ORDER_GUIDE_FORM" property="shortDesc" size="30" maxlength="30"/>
      </td>
      <td width="214px">
<table>
<tr><td><b><app:storeMessage key="shop.editOg.text.addedBy"/></b></td><td>
<bean:write name="EDIT_ORDER_GUIDE_FORM" property="orderGuide.addBy"/>
</td></tr>
<tr><td><b><app:storeMessage key="shop.editOg.text.addDate"/></b></td><td>
<bean:write name="EDIT_ORDER_GUIDE_FORM" property="orderGuide.addDate"/>
</td></tr>

<tr><td><b><app:storeMessage key="shop.editOg.text.modifiedBy"/></b></td><td>
<bean:write name="EDIT_ORDER_GUIDE_FORM" property="orderGuide.modBy"/>
</td></tr>

<tr><td><b><app:storeMessage key="shop.editOg.text.modifyDate"/></b></td><td>
<bean:write name="EDIT_ORDER_GUIDE_FORM" property="orderGuide.modDate"/>
</td></tr>
</table>

</td>
      <td align="right">
        <b><app:storeMessage key="shop.editOg.text.orderBy"/></b>

        <html:select name="EDIT_ORDER_GUIDE_FORM" property="orderBy" onchange="javascript: { document.forms[0].submit();}">
          <html:option value="<%=\"\"+Constants.ORDER_BY_CATEGORY%>">
            <app:storeMessage key="shop.editOg.text.category"/>
          </html:option>
          <html:option value="<%=\"\"+Constants.ORDER_BY_CUST_SKU%>">
            <app:storeMessage key="shop.editOg.text.ourSku#"/>
          </html:option>
          <html:option value="<%=\"\"+Constants.ORDER_BY_NAME%>">
            <app:storeMessage key="shop.editOg.text.productName"/>
          </html:option>
        </html:select>
      </td>
    </tr>
  </table>
  </logic:greaterThan>

</logic:greaterThan>  <% /* order guide id > 0 */ %>


</td>
<td class="tableoutline" width="1"><img src="<%=cw_spacer%>" height="1" width="1"></td>
</tr>
<!-- List of items -->
<logic:notEqual name="EDIT_ORDER_GUIDE_FORM" property="orderGuideId" value="-1">
<tr>
<td class="tableoutline" width="1"><img src="<%=cw_spacer%>" height="1" width="1"></td>
<td colspan="1"  class="tableoutline"><img src="<%=cw_spacer%>" HEIGHT="3"></td>
<td class="tableoutline" width="1"><img src="<%=cw_spacer%>" height="1" width="1"></td>
</tr>
<!-- No items in the shopping cart -->
<logic:equal name="EDIT_ORDER_GUIDE_FORM" property="itemsSize" value="0">
<td class="tableoutline" width="1"><img src="<%=cw_spacer%>" height="1" width="1"></td>
<tr bgcolor="#F0FFFF">
<td class="tableoutline" width="1"><img src="<%=cw_spacer%>" height="1" width="1"></td>
<td class="genericerror" align="center">
  <br><b><app:storeMessage key="shop.editOg.text.ogHasNoItems"/></b><br>&nbsp;
</td>
<td class="tableoutline" width="1"><img src="<%=cw_spacer%>" height="1" width="1"></td>
</tr>

<tr>
<td class="tableoutline" width="1"><img src="<%=cw_spacer%>" height="1" width="1"></td>
<td colspan="1"  class="tableoutline"><img src="<%=cw_spacer%>" HEIGHT="3"></td>
<td class="tableoutline" width="1"><img src="<%=cw_spacer%>" height="1" width="1"></td>
</tr>
<tr>
<td class="tableoutline" width="1"><img src="<%=cw_spacer%>" height="1" width="1"></td>
<td>
 <table align="left" border="0" cellpadding="0" cellspacing="0">
  <logic:greaterThan name="EDIT_ORDER_GUIDE_FORM" property="removedItemsSize" value="0">
  <td>
<%
if ( thisSite.isSetupForSharedOrderGuides() &&
        request.getParameter("findItemsToAdd.x") == null ) { %>
  <a href="#" class="linkButton" onclick="setAndSubmit('eogt','command','findItemsToAdd');"
  ><img src='<%=b_addtocart%>' border="0"/><app:storeMessage key="global.label.addFromCart"/></a>
<% } %>
  <a href="editOrderGuide.do?action=editOrderGuideUndoRemove" class="linkButton"
  ><img src='<%=b_undo%>' border="0"/><app:storeMessage key="global.label.undoRemove"/></a>
    </logic:greaterThan>
  </td>
  <logic:greaterThan name="<%=Constants.SHOPPING_CART%>" property="itemsQty" value="0">
    <td>
<% if ( ! thisSite.isSetupForSharedOrderGuides() ){  %>
  <a href="#" class="linkButton" onclick="setAndSubmit('eogt','command','addFromCart');"
  ><img src='<%=b_addtocart%>' border="0"/><app:storeMessage key="global.label.addFromCart"/></a>
<% } %>
</td>
  </logic:greaterThan>
  <td>
  <a href="#" class="linkButton" onclick="setAndSubmit('eogt','command','save');"
  ><img src='<%=b_save%>' border="0"/><app:storeMessage key="global.action.label.save"/></a>
  </td>
  <td>
  <a href="#" class="linkButton" onclick="setAndSubmit('eogt','command','deleteOrderGuide');"
  ><img src='<%=b_remove%>' border="0"/><app:storeMessage key="global.label.deleteOrderGuide"/></a>
  </td>
  </table>
</td>
<td class="tableoutline" width="1"><img src="<%=cw_spacer%>" height="1" width="1"></td>
</tr>
</logic:equal>
<!-- There are items in the shopping cart -->
<logic:greaterThan name="EDIT_ORDER_GUIDE_FORM" property="itemsSize" value="0">
<%String submitQty ="/store/editOrderGuide.do?action=submitQty";%>
<tr>
  <td class="tableoutline" width="1"><img src="<%=cw_spacer%>" height="1" width="1"></td>
  <td>
    <table align="center" border="0" cellpadding="0" cellspacing="0" width="98%">
    <!-- page contral bar -->
    <tr>
    <td colspan="<%=numCols%>">
      <table border="0" cellpadding="0" cellspacing="2">
      <tr>
       <logic:greaterThan name="<%=Constants.SHOPPING_CART%>" property="itemsQty" value="0">
       <td>
<% if ( ! thisSite.isSetupForSharedOrderGuides() ){  %>
  <a href="#" class="linkButton" onclick="setAndSubmit('eogt','command','addFromCart');"
  ><img src='<%=b_addtocart%>' border="0"/><app:storeMessage key="global.label.addFromCart"/></a>
<% } %>
</td>
       </logic:greaterThan>
       <td>
  <a href="#" class="linkButton" onclick="setAndSubmit('eogt','command','recalc');"
  ><img src='<%=b_recalculate%>' border="0"/><app:storeMessage key="global.label.recalculate"/></a>
       </td>
       <td>
  <a href="#" class="linkButton" onclick="setAndSubmit('eogt','command','removeSelected');"
  ><img src='<%=b_remove%>' border="0"/><app:storeMessage key="global.label.removeSelected"/></a>
       </td>
       <td>
  <a href="#" class="linkButton" onclick="setAndSubmit('eogt','command','removeAll');"
  ><img src='<%=b_remove%>' border="0"/><app:storeMessage key="global.label.removeAll"/></a>
       </td>
       <td>
  <a href="#" class="linkButton" onclick="setAndSubmit('eogt','command','save');"
  ><img src='<%=b_save%>' border="0"/><app:storeMessage key="global.action.label.save"/></a>
       </td>
      </tr>
     </table>
    </td>
    </tr>
    <tr>
    <td colspan="<%=numCols%>"  class="tableoutline"><img src="<%=cw_spacer%>" HEIGHT="3"></td>
    </tr>
    <%--
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
    <td class="shopcharthead" align="center"><app:storeMessage key="shoppingItems.text.pack"/></td>
<!--
    <td class="shopcharthead" align="center">UOM</td>
-->
    <td class="shopcharthead" align="center">Category</td>
    <% if (!appUser.getUserAccount().isHideItemMfg()) {
         if(RefCodeNames.STORE_TYPE_CD.MANUFACTURER.equals(appUser.getUserStore().getStoreType().getValue())) { %>
           <td class="shopcharthead" align="center">&nbsp;</td>
         <% } else { %>
           <td class="shopcharthead" align="center"><app:storeMessage key="shoppingItems.text.mfg"/></td>
         <% } %>
         <td class="shopcharthead" align="center"><app:storeMessage key="shoppingItems.text.mfgSkuNum"/></td>
    <% } %>
    <td class="shopcharthead" align="center"><app:storeMessage key="shoppingItems.text.price"/></td>
    <td class="shopcharthead" align="center"><app:storeMessage key="shoppingItems.text.amount"/></td>
    <td class="shopcharthead" align="center"><app:storeMessage key="global.action.label.select"/></td>
    </tr>
    --%>
    <tr>
    <td colspan="<%=numCols%>"  class="tableoutline"><img src="<%=cw_spacer%>" HEIGHT="3"></td>
    </tr>
<%--------------------------------- HEADER -----------------------------------------%>
 <tr>
    <app:commonDisplayProdHeader
        viewOptionEditCartItems="false"
        viewOptionQuickOrderView="false"
        viewOptionAddToCartList="true"
        viewOptionOrderGuide="false"
        viewOptionEditOrderGuide="true"
        viewOptionShoppingCart="false"
        viewOptionCheckout="false"
        viewOptionInventoryList="false"
        />
 </tr>
<%-----------------------------------------------------------------------------------------%>




    <bean:define id="offset" name="EDIT_ORDER_GUIDE_FORM" property="offset"/>
    <bean:define id="pagesize" name="EDIT_ORDER_GUIDE_FORM" property="pageSize"/>
    <logic:iterate id="item" name="EDIT_ORDER_GUIDE_FORM" property="items"
         offset="0" indexId="kkk"
         type="com.cleanwise.service.api.value.ShoppingCartItemData">
       <bean:define id="orderNumber" name="item" property="orderNumber"/>
       <bean:define id="itemId" name="item" property="product.productId"/>
       <bean:define id="quantityEl" value="<%=\"quantityElement[\"+kkk+\"]\"%>"/>
       <bean:define id="itemIdsEl" value="<%=\"itemIdsElement[\"+kkk+\"]\"%>"/>
       <bean:define id="orderNumbersEl" value="<%=\"orderNumbersElement[\"+kkk+\"]\"%>"/>
       <bean:define id="categoryIdsEl" value="<%=\"categoryIdsElement[\"+kkk+\"]\"%>"/>
       <% if(theForm.getOrderBy()==Constants.ORDER_BY_CATEGORY) { %>
       <% if(theForm.isCategoryChanged(((Integer)kkk).intValue())) { %>
         <tr>
         <td class="shopcategory" colspan="<%=numCols%>"><bean:write name="item" property="categoryPath"/></td>
         </tr>
       <% } %>
       <% } %>
       <%--
       <tr bgcolor="<%=ClwCustomizer.getEvenRowColor(session,(Integer)kkk)%>">
       <td class="text" align="center">
       <% Date curDate = Constants.getCurrentDate();
          Date effDate = item.getProduct().getEffDate();
          Date expDate = item.getProduct().getExpDate();
          if(effDate != null && effDate.compareTo(curDate)<=0 &&
            (expDate==null || expDate.compareTo(curDate)>0)) {
       %>
       <logic:equal name="item" property="duplicateFlag" value="false">
       <html:text name="EDIT_ORDER_GUIDE_FORM" property="<%=quantityEl%>" size="3"/>
       </logic:equal>
       <logic:equal name="item" property="duplicateFlag" value="true">
       <html:text name="EDIT_ORDER_GUIDE_FORM" property="<%=quantityEl%>" readonly="true" styleClass="customerltbkground" size="3"/>
       </logic:equal>
       <html:hidden name="EDIT_ORDER_GUIDE_FORM" property="<%=itemIdsEl%>" value="<%=\"\"+itemId %>"/>
       <html:hidden name="EDIT_ORDER_GUIDE_FORM" property="<%=orderNumbersEl%>" value="<%=\"\"+orderNumber %>"/>
       <% } else { %>
         <app:storeMessage key="shoppingItems.text.n_sl_a"/>
       <% } %>
       </td>
       <td class="text" align="center">
         <bean:write name="item" property="actualSkuNum"/>&nbsp;
       </td>
       <td class="text" align="center">
       <%String itemLink = "shop.do?action=item&source=editOrderGuide&itemId="+itemId+"&qty="+item.getQuantity();%>
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
      <bean:write name="item" property="categoryPath"/>
     </td>
     <% if (!appUser.getUserAccount().isHideItemMfg()) { %>
       <td class="text" align="center">
         <% if(RefCodeNames.STORE_TYPE_CD.MANUFACTURER.equals(appUser.getUserStore().getStoreType().getValue())) { %>
           &nbsp;
         <% } else { %>
           <bean:write name="item" property="product.manufacturerName"/>&nbsp;
         <% } %>
       </td>
       <td class="text" align="center">
         <bean:write name="item" property="product.manufacturerSku"/>&nbsp;
       </td>
     <% } %>
     <td class="text" align="center">
       <bean:define id="price"  name="item" property="price"/>
       <%=ClwI18nUtil.getPriceShopping(request,price,"<br>")%>
       <logic:equal name="item" property="contractFlag" value="true">
       <logic:equal name="<%=Constants.APP_USER%>" property="showWholeCatalog" value="true">
         *
       </logic:equal>
       </logic:equal>
     </td>
     <logic:equal name="item" property="duplicateFlag" value="false">
     <td class="text" align="center">
        <bean:define id="amount"  name="item" property="amount"/>
        <%=ClwI18nUtil.getPriceShopping(request,amount,"<br>")%>
     </td>
     <td class="text" align="center">
     <html:multibox name="EDIT_ORDER_GUIDE_FORM" property="selectBox" value="<%=\"\"+(((Integer)itemId).longValue()*10000+((Integer)orderNumber).longValue())%>" />
     </td>
     </logic:equal>
     <logic:equal name="item" property="duplicateFlag" value="true">
     <td class="text" align="center">
        <bean:define id="amount"  name="item" property="amount"/>
        <%=ClwI18nUtil.getPriceShopping(request,amount,"<br>")%>
     </td>
     <td><app:storeMessage key="shoppingItems.text.dupl"/></td>
     </logic:equal>
     </div></td>
      </tr>
       --%>

       <% String itemLink1 = "shop.do?action=item&source=editOrderGuide&itemId="
         +itemId+"&qty="+item.getQuantity();
         String trColor=ClwCustomizer.getEvenRowColor(request.getSession(),rowIdx++);
       %>

  <tr bgcolor="<%=trColor%>">
  <app:commonDisplayProd
    viewOptionShoppingCart="false"
    viewOptionCheckout="false"
    viewOptionEditCartItems="true"
    viewOptionQuickOrderView="false"
    viewOptionAddToCartList="true"
    viewOptionOrderGuide="false"
    viewOptionEditOrderGuide="true"
    viewOptionInventoryList="false"
    viewOptionShoppingCart="false"
    viewOptionCheckout="false"
    name="item"
    link="<%=itemLink1%>"
    index="<%=kkk%>"
    inputNameQuantity="<%=quantityEl%>"/>
  </tr>


     </logic:iterate>
     <!-- bottom control -->
     <tr>
     <td colspan="<%=numCols%>"  class="tableoutline"><img src="<%=cw_spacer%>" HEIGHT="3"></td>
     </tr>
     <tr>
     <td colspan = "<%=(numCols-3)%>" >
     <table align="left" border="0" cellpadding="0" cellspacing="2">
<td>

      <logic:equal name="<%=Constants.APP_USER%>" property="allowPurchase" value="true">
<%
if ( thisSite.isSetupForSharedOrderGuides() &&
        request.getParameter("findItemsToAdd.x") == null ) { %>
  <a href="#" class="linkButton" onclick="setAndSubmit('eogt','command','findItemsToAdd');"
  ><img src='<%=b_addtocart%>' border="0"/><app:storeMessage key="global.label.addFromCart"/></a>
<% }  %>
 </logic:equal>


<% if ( ! thisSite.isSetupForSharedOrderGuides() ){  %>
<logic:greaterThan name="<%=Constants.SHOPPING_CART%>" property="itemsQty" value="0">
  <a href="#" class="linkButton" onclick="setAndSubmit('eogt','command','addFromCart');"
  ><img src='<%=b_addtocart%>' border="0"/><app:storeMessage key="global.label.addFromCart"/></a>
</logic:greaterThan>
<% } %>

</td>
<td>
  <a href="#" class="linkButton" onclick="setAndSubmit('eogt','command','recalc');"
  ><img src='<%=b_recalculate%>' border="0"/><app:storeMessage key="global.label.recalculate"/></a>
</td>
<td>
  <a href="#" class="linkButton" onclick="setAndSubmit('eogt','command','removeSelected');"
  ><img src='<%=b_remove%>' border="0"/><app:storeMessage key="global.label.removeSelected"/></a>
</td>
<td>
  <a href="#" class="linkButton" onclick="setAndSubmit('eogt','command','removeAll');"
  ><img src='<%=b_remove%>' border="0"/><app:storeMessage key="global.label.removeAll"/></a>
</td>
<td>
  <a href="#" class="linkButton" onclick="setAndSubmit('eogt','command','save');"
  ><img src='<%=b_save%>' border="0"/><app:storeMessage key="global.action.label.save"/></a>
</td>
</tr>
 </table>
     </td>
     <td class="text" align="rigth"><div class="fivemargin">
        <b><app:storeMessage key="shoppingItems.text.totalExcludingVOC:"/><b>
     </div></td>
     <td class="text" bgcolor=><div class="fivemargin">
        <bean:define id="i_total_amount"        name="EDIT_ORDER_GUIDE_FORM" property="itemsAmt"/>
        <%=ClwI18nUtil.getPriceShopping(request,i_total_amount,"<br>")%>
</td>
     <td>&nbsp;</td>
     </tr>
  </table>
  </td>
  </logic:greaterThan> <!-- there are items in the order guide -->
  <td class="tableoutline" width="1"><img src="<%=cw_spacer%>" height="1" width="1"></td>
  </tr>
</logic:notEqual> <!-- order guide selected -->
</table>


<% if ("findItemsToAdd".equals(request.getParameter("command")) ||
        request.getParameter("findItemsToAdd.x") != null ) { %>
<table class="tbstd" align="center" cellspacing=0>
  <tr>
    <tr>
    <td colspan="<%=numCols%>"  class="tableoutline"><img src="<%=cw_spacer%>" HEIGHT="3"></td>
    </tr>
  <tr>
  <td class="shopcharthead" align="center" colspan=<%=numCols%>>
  <app:storeMessage key="shop.editOg.text.shoppingCartItems"/></td></tr>
    <tr>
    <td colspan="<%=numCols%>"  class="tableoutline"><img src="<%=cw_spacer%>" HEIGHT="3"></td>
    </tr>

      <%--------------------------------- HEADER -----------------------------------------%>
<%--
       <tr>
          <app:commonDisplayProdHeader
              viewOptionEditCartItems="false"
              viewOptionQuickOrderView="false"
              viewOptionAddToCartList="true"
              viewOptionOrderGuide="false"
              viewOptionEditOrderGuide="true"
              viewOptionShoppingCart="false"
              viewOptionCheckout="false"
              viewOptionInventoryList="false"
              />
       </tr>
--%>
      <%-----------------------------------------------------------------------------------------%>

    <%-- --%>
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
    <td class="shopcharthead" align="center"><app:storeMessage key="shoppingItems.text.pack"/></td>
<!--
    <td class="shopcharthead" align="center">UOM</td>
-->
    <td class="shopcharthead" align="center"><app:storeMessage key="shoppingItems.text.category"/></td>
    <%  if (!appUser.getUserAccount().isHideItemMfg()) {  %>
          <td class="shopcharthead" align="center"><app:storeMessage key="shoppingItems.text.mfg"/></td>
          <td class="shopcharthead" align="center"><app:storeMessage key="shoppingItems.text.mfgSkuNum"/></td>
    <% } %>
    <td class="shopcharthead" align="center"><app:storeMessage key="shoppingItems.text.price"/></td>
    <td class="shopcharthead" align="center"><app:storeMessage key="shoppingItems.text.amount"/></td>
    <td class="shopcharthead" align="center"><app:storeMessage key="global.action.label.select"/></td>
    </tr>
    <%-- --%>

   <logic:iterate id="item" name="EDIT_ORDER_GUIDE_FORM" property="itemsAvailable"
         offset="0" indexId="jdx"
         type="com.cleanwise.service.api.value.ShoppingCartItemData">

       <bean:define id="orderNumber" name="item" property="orderNumber"/>
       <bean:define id="itemId" name="item" property="product.productId"/>
       <bean:define id="quantityEl" value="<%=\"quantityElement[\"+jdx+\"]\"%>"/>
       <bean:define id="itemIdsEl" value="<%=\"itemIdsElement[\"+jdx+\"]\"%>"/>
       <bean:define id="orderNumbersEl" value="<%=\"orderNumbersElement[\"+jdx+\"]\"%>"/>
       <bean:define id="categoryIdsEl" value="<%=\"categoryIdsElement[\"+jdx+\"]\"%>"/>
       <% if(theForm.getOrderBy()==Constants.ORDER_BY_CATEGORY) { %>
       <% if(theForm.isCategoryChanged2(((Integer)jdx).intValue())) { %>
         <tr>
         <td class="shopcategory" colspan="<%=numCols%>"><bean:write name="item" property="categoryPath"/></td>
         </tr>
       <% } %>
       <% } %>

       <tr bgcolor="<%=ClwCustomizer.getEvenRowColor(session,(Integer)jdx)%>">
       <html:hidden name="EDIT_ORDER_GUIDE_FORM" property="<%=itemIdsEl%>" value="<%=\"\"+itemId %>"/>
       <html:hidden name="EDIT_ORDER_GUIDE_FORM" property="<%=orderNumbersEl%>" value="<%=\"\"+orderNumber %>"/>
       <td class="text" align="center"><logic:equal name="<%=Constants.APP_USER%>" property="allowPurchase" value="true">
         <bean:write name="item" property="quantity"/></logic:equal>&nbsp;
       </td>

       <td class="text" align="center">
         <bean:write name="item" property="actualSkuNum"/>&nbsp;
       </td>
       <td class="text" align="center">
       <%String itemLink = "shop.do?action=item&source=editOrderGuide&itemId="+itemId+"&qty="+item.getQuantity();%>
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

      <% if(item.getProduct().getCatalogCategories()!= null) { %>
      <% if(item.getProduct().getCatalogCategories().size()>1) { %>
        <html:select name="EDIT_ORDER_GUIDE_FORM" property="<%=\"\"+categoryIdsEl%>" styleClass="text">
         <logic:iterate id="category" name="item" property="product.catalogCategories"
         offset="0" indexId="ii"
         type="CatalogCategoryData">
        <html:option  value="<%=\"\"+category.getCatalogCategoryId()%>"><%=category.getCatalogCategoryShortDesc()%></html:option>
         </logic:iterate>
       </html:select>
      <% } else if(!item.getProduct().getCatalogCategories().isEmpty()) { %>
           <%=((CatalogCategoryData)item.getProduct().getCatalogCategories().get(0)).getCatalogCategoryShortDesc()%>
      <% } %>
      <% } else { %>
       <app:storeMessage key="shoppingItems.text.n_sl_a"/>
      <% } %>
     </td>
     <% if (!appUser.getUserAccount().isHideItemMfg()) { %>
       <td class="text" align="center">
            <bean:write name="item" property="product.manufacturerName"/>&nbsp;
       </td>
       <td class="text" align="center">
         <bean:write name="item" property="product.manufacturerSku"/>&nbsp;
       </td>
     <% } %>
     <td class="text" align="center">
       <bean:define id="price"  name="item" property="price"/>
       <%=ClwI18nUtil.getPriceShopping(request,price,"<br>")%>
       <logic:equal name="item" property="contractFlag" value="true">
       <logic:equal name="<%=Constants.APP_USER%>" property="showWholeCatalog" value="true">
         *
       </logic:equal>
       </logic:equal>
     </td>

     <td class="text" align="center">
        <bean:define id="amount"  name="item" property="amount"/>
        <%=ClwI18nUtil.getPriceShopping(request,amount,"<br>")%>
     </td>

     <td class="text" align="center">
     <html:multibox name="EDIT_ORDER_GUIDE_FORM" property="selectToAddBox"
        value="<%=\"\"+(((Integer)itemId).longValue()*10000+((Integer)orderNumber).longValue())%>" />
     </td>


     </tr>
     </logic:iterate>

    <tr>
    <td colspan="<%=numCols%>"  class="tableoutline"><img src="<%=cw_spacer%>" HEIGHT="1"></td>
</tr><tr>
    <td colspan="<%=numCols%>" align=right>
  <a href="#" class="linkButton" onclick="setAndSubmit('eogt','command','addSelectedItems');"
  ><img src='<%=b_addtocart%>' border="0"/><app:storeMessage key="global.label.addSelected"/></a>
</td>
    </tr>

</table>

<% } %>

<!-- bottom -->
<table align="center" border="0" cellpadding="0" cellspacing="0" width="<%=Constants.TABLEWIDTH%>">
<tr>
<td><img src="<%=cw_left_footer_shop%>" ALIGN="top"></td>
<td><img src="<%=cw_middle_footer_shop%>" ALIGN="top" width="<%=Constants.TABLE_BOTTOM_MIDDLE_BORDER_WIDTH%>" height="8"></td>
<td><img src="<%=cw_right_footer_shop%>" ALIGN="top"></td>
</tr>
  <html:hidden property="command" value="CCCCCCC"/>
</html:form>
</table>


