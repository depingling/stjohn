
<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>
<%@ page import="com.cleanwise.service.api.value.*"%>
<%@ page import="com.cleanwise.view.forms.QuickOrderForm" %>


<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
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
<%ShoppingCartData shoppingCart = (ShoppingCartData) session.getAttribute(Constants.SHOPPING_CART);%>
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
<bean:define id="theForm" name="QUICK_ORDER_FORM" type="com.cleanwise.view.forms.QuickOrderForm"/>



<!-- taken wholesale from the estore demo -->
<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>

<table align="center" border="0" cellpadding="0" cellspacing="0" width="<%=Constants.TABLEWIDTH%>">
<tr>
<td class="tableoutline" width="1"><img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1"></td>
<td><jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"t_shopToolbar.jsp")%>'/></td>
<td class="tableoutline" width="1"><img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1"></td>
</tr>

<!-- List of items -->
<logic:equal name="<%=Constants.APP_USER%>" property="allowPurchase" value="true">
<tr>
<td class="tableoutline" width="1"><img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1"></td>
<td>
  <table align="center" border="0" cellpadding="0" cellspacing="0" width="98%">
  
  <html:form styleId="qoct" action="/store/quickOrder.do?action=quickOrderResolveMfg">
 
  <tr bgcolor="#F0FFFF">
  <td colspan="4" class="subheadergeneric" align="center">
  <b> <app:storeMessage key="shop.quick.text.someSkusHaveMoreThanOneMapping"/></b>
  </td>
  </tr>
  <tr>
  <td colspan="4" class="tableoutline"><img src="/images/cw_spacer.gif" height="3"></td>  </tr>
  </tr>
  <!-- page contral bar -->
  <tr>
  <td colspan="4">
  <table border="0" cellpadding="0" cellspacing="0">
  <tr>
  <td>
    <a href="#" class="linkButton" onclick="setAndSubmit('qoct','command','addToCart');"
    ><img src='<%=IMGPath + "/b_addtocart.gif"%>' border="0"/><app:storeMessage key="global.label.addToCart"/></a>
  </td>
   <!-- Add to shopping cart -->
   <td>
    <a href="../store/shoppingcart.do" class="linkButton"><img src="<%=IMGPath%>/b_viewcart.gif" 
    border="0"><app:storeMessage key="global.label.shoppingCart"/></a>
   </td>
   <%if (viewAddInvCart) {%>
      <td>
          <% String add_img = IMGPath + "/b_addtocart.gif"; %>
          <a href="#" class="linkButton" onclick="setAndSubmit('qoct','command','qo_addToModInvCart');"> 
              <img src="<%=add_img%>" border="0"/>
              <app:storeMessage key="global.label.addToInventoryCart"/>
          </a>
      </td>
      <%}%>
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
   <!-------   ----------->
   </tr>
   </table>
   </td>
   </tr>
  <tr>
  <!-- Column heanders -->
  <td colspan="4" class="tableoutline"><img src="/images/cw_spacer.gif" height="1"></td>  </tr>
  </tr>
  <tr>
  <td class="shopcharthead"><div class="fivemargin"><app:storeMessage key="shoppingItems.text.#"/></div></td>
  <td class="shopcharthead"><div class="fivemargin"><app:storeMessage key="shoppingItems.text.sku#"/></div></td>
  <td class="shopcharthead"><div class="fivemargin"><app:storeMessage key="shoppingItems.text.quantity"/></div></td>
  <%if(theForm.getSkuType() == QuickOrderForm.MFG_SKU_TYPE){%>
	<td class="shopcharthead"><div class="fivemargin"><app:storeMessage key="shoppingItems.text.manufacturer"/></div></td>
  <%}else if(theForm.getSkuType() == QuickOrderForm.DEFAULT_SKU_TYPE){%>
	<td class="shopcharthead"><div class="fivemargin"><app:storeMessage key="shoppingItems.text.name"/></div></td>
  <%}%>
  </tr>
  <tr>
  <!-- Main table -->
  <td colspan="4" class="tableoutline"><img src="/images/cw_spacer.gif" height="1"></td>  </tr>
  </tr>
  <% int pagesize = theForm.getPageSize();
     for(int kkk=0; kkk<pagesize; kkk++) {
       String itemSkusEl = "itemSkusElement["+kkk+"]";
       String itemQtysEl = "itemQtysElement["+kkk+"]";
       String itemIdsEl = "itemIdsElement["+kkk+"]";
       String skuNum = theForm.getItemSkusElement(kkk);
       String skuQty = theForm.getItemQtysElement(kkk);
       if(skuNum!=null && skuNum.trim().length()>0) {
  %>
     <tr bgcolor="<%=ClwCustomizer.getEvenRowColor(session,new Integer(kkk))%>">
     <td class="text"><div class="fivemargin">
        <%=""+(kkk+1)%>
     </div></td>
     <td class="text"><div class="fivemargin">
         <%=skuNum%>
     </div></td>
     <td class="text"><div class="fivemargin">
        <html:hidden name="QUICK_ORDER_FORM" property="<%=itemQtysEl%>" value="<%=skuQty%>"/>
        <%=skuQty%>
     </div></td>
	 
	 
	 <%if(theForm.getSkuType() == QuickOrderForm.MFG_SKU_TYPE){%>
     <td class="text"><div class="fivemargin">
        <%
        List mfgNames = theForm.getMfgNamesElement(kkk);
        List itemMfgIds = theForm.getItemIdListsElement(kkk);
        %>
        <% if(mfgNames!= null && mfgNames.size()==1) { %>
           <%
            String mfgName = (String) mfgNames.get(0);
            Integer itemIdI = (Integer) itemMfgIds.get(0);
            int itemId = itemIdI.intValue();
           %>
          <html:hidden name="QUICK_ORDER_FORM" property="<%=itemIdsEl%>" value="<%=\"\"+itemId%>"/>
          <%=mfgName%>
        <% } else if(mfgNames!= null && mfgNames.size()>1) { %>
          <html:select name="QUICK_ORDER_FORM" property="<%=itemIdsEl%>" styleClass="text">
          <%
          for(int jj=0; jj<mfgNames.size(); jj++) {
            String mfgName = (String) mfgNames.get(jj);
            Integer itemIdI = (Integer) itemMfgIds.get(jj);
            int itemId = itemIdI.intValue();
          %>
          <html:option value="<%=\"\"+itemId%>"><%=mfgName%></html:option>
          <% } %>
          </html:select>
        <% } %>
     </div></td>
	 <%//end sku type check%>
	 <% } else if(theForm.getSkuType() == QuickOrderForm.DEFAULT_SKU_TYPE){%>
	 <td class="text"><div class="fivemargin">
        <%
        List shortDesc = theForm.getShortDescElement(kkk);
        List itemMfgIds = theForm.getItemIdListsElement(kkk);
        %>
        <% if(shortDesc!= null && shortDesc.size()==1) { %>
           <%
            String mfgName = (String) shortDesc.get(0);
            Integer itemIdI = (Integer) itemMfgIds.get(0);
            int itemId = itemIdI.intValue();
           %>
          <html:hidden name="QUICK_ORDER_FORM" property="<%=itemIdsEl%>" value="<%=\"\"+itemId%>"/>
          <%=mfgName%>
        <% } else if(shortDesc!= null && shortDesc.size()>1) { %>
          <html:select name="QUICK_ORDER_FORM" property="<%=itemIdsEl%>" styleClass="text">
          <%
          for(int jj=0; jj<shortDesc.size(); jj++) {
            String mfgName = (String) shortDesc.get(jj);
            Integer itemIdI = (Integer) itemMfgIds.get(jj);
            int itemId = itemIdI.intValue();
          %>
          <html:option value="<%=\"\"+itemId%>"><%=mfgName%></html:option>
          <% } %>
          </html:select>
        <% } %>
     </div></td>
	 <% } //end sku type check%>
	 
	 
	 
     </tr>
  <% }} %>
  <tr>
  <!-- Second control bar -->
  <td colspan="4" class="tableoutline"><img src="/images/cw_spacer.gif" height="3"></td>  </tr>
  </tr>
  <tr>
  <%if (viewAddInvCart) {%>
      <td>
          <a href="#" class="linkButton" onclick="setAndSubmit('qoct','command','addToCart');">
              <img src='<%=IMGPath + "/b_addtocart.gif"%>' border="0"/>
              <app:storeMessage key="global.label.addToCart"/>
          </a>
      </td>
      <td colspan="2">
          <% String add_img = IMGPath + "/b_addtocart.gif"; %>
          <a href="#" class="linkButton" onclick="setAndSubmit('qoct','command','qo_addToModInvCart');">
              <img src="<%=add_img%>" border="0"/>
              <app:storeMessage key="global.label.addToInventoryCart"/>
          </a>
      </td>
      <td></td>
      <%} else {%>
      <td colspan="4">
          <a href="#" class="linkButton" onclick="setAndSubmit('qoct','command','addToCart');">
              <img src='<%=IMGPath + "/b_addtocart.gif"%>' border="0"/>
              <app:storeMessage key="global.label.addToCart"/>
          </a>
      </td> 
      <%}%>
	
  </tr>
  <html:hidden property="command" value="CCCCCCC"/>
  </html:form>
  
  </logic:equal>
  </table>

  </td>
  <td class="tableoutline" width="1"><img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1"></td>
  </tr>
</table>
</td>
</tr>
<!-- Footer -->
<tr>
<td>
<table align="center" border="0" cellpadding="0" cellspacing="0" width="<%=Constants.TABLEWIDTH%>">
<tr>
<td>
 <img src="<%=IMGPath%>/cw_left_footer_shop.gif" ALIGN="top">
</td>
<td>
 <img src="<%=IMGPath%>/cw_middle_footer_shop.gif" ALIGN="top" width="<%=Constants.TABLE_BOTTOM_MIDDLE_BORDER_WIDTH%>" height="8">
</td>
<td>
 <img src="<%=IMGPath%>/cw_right_footer_shop.gif" ALIGN="top">
</td>
</tr>
</table>






