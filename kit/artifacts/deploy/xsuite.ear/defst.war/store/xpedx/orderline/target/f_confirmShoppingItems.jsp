<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.GregorianCalendar" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.view.utils.ShopTool" %>
<%@ page import="com.cleanwise.service.api.value.ShoppingCartData" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>
<bean:define id="theForm" name="CHECKOUT_FORM" type="com.cleanwise.view.forms.CheckoutForm"/>
<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<%
 CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
 String utype = appUser.getUser().getUserTypeCd();
 boolean resaleItemsAllowed = ShopTool.isResaleItemsAllowed(request);
 int colCount = 10;
 if (appUser.getUserAccount().isShowSPL()) {
   colCount++;
 }
 %>
  <!-- List of items -->


  <!-- page contral bar -->
  <tr>
  <%-- td class="shopcharthead"><div><app:storeMessage key="shoppingItems.text.maxOrderQty"/></div></td --%>
  <td class="shopcharthead"><div><app:storeMessage key="shoppingItems.text.orderQty"/></div></td>
  <td class="shopcharthead"><div><app:storeMessage key="shoppingItems.text.ourSkuNum"/></div></td>
  <td class="shopcharthead"><div><app:storeMessage key="shoppingItems.text.productName"/></div></td>
  <td class="shopcharthead"><div><app:storeMessage key="shoppingItems.text.size"/></div></td>
  <td class="shopcharthead"><div><app:storeMessage key="shoppingItems.text.pack"/></div></td>
  <td class="shopcharthead"><div><app:storeMessage key="shoppingItems.text.uom"/></div></td>
  <td class="shopcharthead"><div><app:storeMessage key="shoppingItems.text.color"/></div></td>
  <logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">
    <td class="shopcharthead"><div><app:storeMessage key="shoppingItems.text.price"/></div></td>
    <td class="shopcharthead"><div><app:storeMessage key="shoppingItems.text.amount"/></div></td>
  </logic:equal>
  <logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="false">
     <td class="shopcharthead" align="center" colspan="2">&nbsp;</td>
  </logic:equal>
  <% if (appUser.getUserAccount().isShowSPL()) { %>
    <td class="shopcharthead"><div class="fivemargin"><app:storeMessage key="shoppingItems.text.spl"/></div></td>
  <%} %>
  <% if(resaleItemsAllowed){%>
     <td class="shopcharthead"><div><font color="red"><app:storeMessage key="shoppingItems.text.reSaleItem"/></font></div></td>
  <%}else{%>
      <td class="shopcharthead" align="center">&nbsp;</td>
  <%}%>
  </tr>

<%
    boolean isaMLAStore = RefCodeNames.STORE_TYPE_CD.MLA.equals
      (appUser.getUserStore().getStoreType().getValue());
  // for store type other than MLA, we need display distributor info
  // (which items belong to which distributor)
  if (! isaMLAStore) {
%>

<bean:define id="SCartDistV" name="CHECKOUT_FORM" property="cartDistributors" type="ShoppingCartDistDataVector"/>

<logic:iterate id="scdistD" name="SCartDistV"
  offset="0" indexId="DISTIDX"
     type="com.cleanwise.service.api.value.ShoppingCartDistData">

  <bean:define id="distName" name="scdistD" property="distributorName"/>
  <bean:define id="distItems" name="scdistD" property="shoppingCartItems"
    type="java.util.List"/>

  <tr>
    <td class="shopcharthead" colspan="<%=colCount%>">
      &nbsp;<bean:write name="scdistD" property="distributorName"/>
    </td>
  </tr>


  <logic:iterate id="item" name="distItems"
   offset="0" indexId="itemsIdx"
   type="com.cleanwise.service.api.value.ShoppingCartItemData">
     <bean:define id="orderNumber" name="item" property="orderNumber"/>
     <bean:define id="itemId" name="item" property="product.productId"/>
     <bean:define id="itemIdQtyElement" value="<%=\"itemIdQtyElement[\"+itemsIdx+\"]\"%>"/>

     <% if(theForm.getSortBy()==Constants.ORDER_BY_CATEGORY) { %>
     <% if(theForm.isCategoryChanged(((Integer)itemsIdx).intValue())) { %>
       <tr>
       <td class="shopcategory" colspan="<%=colCount%>">&nbsp;&nbsp;&nbsp;<bean:write name="item" property="categoryPath"/></td>
       </tr>
     <% } %>
     <% } %>
     <tr bgcolor="<%=ClwCustomizer.getEvenRowColor(session,(Integer)itemsIdx)%>">
<%--
    <td width=10 align="center">
      <logic:greaterThan name="item" property="maxOrderQty" value="-1">
        <bean:write name="item" property="maxOrderQty"/>
      </logic:greaterThan>
    </td>
    --%>

     <td class="text"><div>
     <% Date curDate = Constants.getCurrentDate();
        Date effDate = item.getProduct().getEffDate();
        Date expDate = item.getProduct().getExpDate();
        if(effDate != null && effDate.compareTo(curDate)<=0 &&
          (expDate==null || expDate.compareTo(curDate)>0)) {
     %>
     <bean:write name="item" property="quantity"/>
     <% } else { %>
       N/A
     <% } %>
     </div></td>
     <td class="text"><div>
       <bean:write name="item" property="actualSkuNum"/>&nbsp;
     </div></td>
     <td class="text"><div>
       <bean:write name="item" property="product.catalogProductShortDesc"/>&nbsp;
     </div></td>
     <td class="text"><div>
       <bean:write name="item" property="product.size"/>&nbsp;
     </div></td>
     <td class="text"><div>
       <bean:write name="item" property="product.pack"/>&nbsp;
     </div></td>
     <td class="text"><div>
       <bean:write name="item" property="product.uom"/>&nbsp;
     </div></td>
     <td class="text"><div>
       <bean:write name="item" property="product.color"/>&nbsp;
     </div></td>
     <td class="text"><div>
       <logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">
         <bean:define id="price"  name="item" property="price"/>
         <%=ClwI18nUtil.getPriceShopping(request,price,"&nbsp;")%>&nbsp;&nbsp;
         <logic:equal name="item" property="contractFlag" value="true">
         <logic:equal name="<%=Constants.APP_USER%>" property="showWholeCatalog" value="true">
           *
         </logic:equal></logic:equal>
       </logic:equal>
       <logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="false">
         &nbsp;
       </logic:equal>
     </div></td>
     <td class="text"><div>
       <logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">
         <bean:define id="amount"  name="item" property="amount"/>
         <%=ClwI18nUtil.getPriceShopping(request,amount,"&nbsp;")%>
       </logic:equal>
       <logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="false">
         &nbsp;
       </logic:equal>
     </div></td>

<% if (appUser.getUserAccount().isShowSPL()) { %>
<td align="center" class="text"><div>
      <logic:present name="item" property="product.catalogDistrMapping.standardProductList">
        <bean:define id="spl" name="item" property="product.catalogDistrMapping.standardProductList" type="java.lang.String"/>
        <%if(com.cleanwise.service.api.util.Utility.isTrue(spl)){%>
          <app:storeMessage key="shoppingItems.text.y"/>
        <%}else{%>
          <app:storeMessage key="shoppingItems.text.n"/>
        <%}%>
      </logic:present>
      <logic:notPresent name="item" property="product.catalogDistrMapping.standardProductList">
        <app:storeMessage key="shoppingItems.text.n"/>
      </logic:notPresent>
</div></td>
<%} %>



     <%if(resaleItemsAllowed){%>
        <td class="text"><div>
                <logic:equal name="item" property="reSaleItem" value="true">
                        &nbsp;<app:storeMessage key="shoppingItems.text.y"/>
                </logic:equal>
                <logic:notEqual name="item" property="reSaleItem" value="true">
                        &nbsp;<app:storeMessage key="shoppingItems.text.n"/>
                </logic:notEqual>
        </div></td>
     <%}%>
     </tr>
   </logic:iterate>

    <bean:define id="freightImplied" name="scdistD" property="distFreightImplied"
      type="java.util.List"/>
    <%  if(null != freightImplied && 0 < freightImplied.size()) {
    %>
      <logic:iterate id="impliedD" name="freightImplied"
         offset="0" indexId="impliedIDX"
         type="com.cleanwise.service.api.value.FreightTableCriteriaData">
    <tr>
      <td class="freighttext">&nbsp;</td>
      <td class="freighttext" colspan="<%=(colCount-1)%>" align="left">
      <bean:write name="impliedD" property="shortDesc" />:
      <bean:define id="frAmt" name="impliedD" property="freightAmount" />
      <%=ClwI18nUtil.getPriceShopping(request,frAmt,"&nbsp;")%>
      </td>
    </tr>
    </logic:iterate>
    <%  }  %>


    <bean:define id="freightOptions" name="scdistD" property="distFreightOptions"
      type="java.util.List"/>
    <%  if(null != freightOptions && 0 < freightOptions.size()) {
    %>
    <tr>
      <td class="freighttext">&nbsp;</td>
      <td class="freighttext" colspan="<%=(colCount-1)%>"><app:storeMessage key="shop.checkout.text.shippingMethod"/>
        (<bean:write name="scdistD" property="distSelectableFreightVendorName" />):

        <bean:define id="selectedFrMsg" name="scdistD" property="distSelectableFreightMsg" type="String"/>
        <bean:define id="selectedFrAmt" name="scdistD" property="distSelectableFreightCost" />
    <%          if (null != selectedFrMsg && selectedFrMsg.trim().length() > 0) {  %>
        <bean:write name="scdistD" property="distSelectableFreightMsg" />
    <%          } else {  %>
        <%=ClwI18nUtil.getPriceShopping(request,selectedFrAmt,"&nbsp;")%>
    <%          }  %>
      </td>
    </tr>
    <%  } else {  %>
      <html:hidden name="CHECKOUT_FORM" property="distFreightVendor" value=""/>
    <%  }  %>
    <%  if(false){  %>
    <tr>
      <td class="freighttext">&nbsp;</td>
      <td class="freighttext" colspan="2" align="left"><app:storeMessage key="shop.checkout.text.tax"/></td>
      <td class="freighttext">
                        <logic:present name="scdistD" property="salesTax">
              <bean:define id="salesTax" name="scdistD" property="salesTax" />
              <%=ClwI18nUtil.getPriceShopping(request,salesTax,"&nbsp;")%>
                        </logic:present>
                        <logic:notPresent name="scdistD" property="salesTax">
                           <%=ClwI18nUtil.getPriceShopping(request,new java.math.BigDecimal(0),"&nbsp;")%>
                        </logic:notPresent>
      </td>
      <td colspan="<%=(colCount-4)%>" class="freighttext">&nbsp;</td>
    </tr>
    <% }%>
</logic:iterate>

<%  } else {   %>

  <logic:iterate id="item" name="CHECKOUT_FORM" property="items"
   offset="0" indexId="itemsIdx"
   type="com.cleanwise.service.api.value.ShoppingCartItemData">
     <bean:define id="orderNumber" name="item" property="orderNumber"/>
     <bean:define id="itemId" name="item" property="product.productId"/>
     <bean:define id="itemIdQtyElement" value="<%=\"itemIdQtyElement[\"+itemsIdx+\"]\"%>"/>

     <% if(theForm.getSortBy()==Constants.ORDER_BY_CATEGORY) { %>
     <% if(theForm.isCategoryChanged(((Integer)itemsIdx).intValue())) { %>
       <tr>
       <td class="shopcategory" colspan="<%=colCount%>"><bean:write name="item" property="categoryPath"/></td>
       </tr>
     <% } %>
     <% } %>
     <tr bgcolor="<%=ClwCustomizer.getEvenRowColor(session,(Integer)itemsIdx)%>">
     <td class="text"><div>
     <% Date curDate = Constants.getCurrentDate();
        Date effDate = item.getProduct().getEffDate();
        Date expDate = item.getProduct().getExpDate();
        if(effDate != null && effDate.compareTo(curDate)<=0 &&
          (expDate==null || expDate.compareTo(curDate)>0)) {
     %>
     <bean:write name="item" property="quantity"/>
     <% } else { %>
       N/A
     <% } %>
     </div></td>
     <td class="text"><div>
       <bean:write name="item" property="actualSkuNum"/>&nbsp;
     </div></td>
     <td class="text"><div>
       <bean:write name="item" property="product.catalogProductShortDesc"/>&nbsp;
     </div></td>
     <td class="text"><div>
       <bean:write name="item" property="product.size"/>&nbsp;
     </div></td>
     <td class="text"><div>
       <bean:write name="item" property="product.pack"/>&nbsp;
     </div></td>
     <td class="text"><div>
       <bean:write name="item" property="product.uom"/>&nbsp;
     </div></td>
     <td class="text"><div>
       <bean:write name="item" property="product.color"/>&nbsp;
     </div></td>
     <td class="text"><div>
       <logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">
         <bean:define id="price"  name="item" property="price"/>
         <%=ClwI18nUtil.getPriceShopping(request,price,"&nbsp;")%>&nbsp;&nbsp;
         <logic:equal name="item" property="contractFlag" value="true">
         <logic:equal name="<%=Constants.APP_USER%>" property="showWholeCatalog" value="true">
           *
         </logic:equal></logic:equal>
       </logic:equal>
       <logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="false">
         &nbsp;
       </logic:equal>
     </div></td>
     <td class="text"><div>
       <logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">
         <bean:define id="amount"  name="item" property="amount"/>
         <%=ClwI18nUtil.getPriceShopping(request,amount,"&nbsp;")%>
       </logic:equal>
       <logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="false">
         &nbsp;
       </logic:equal>
     </div></td>
<% if (appUser.getUserAccount().isShowSPL()) { %>
<td align="center" class="text"><div>
      <logic:present name="item" property="product.catalogDistrMapping.standardProductList">
        <bean:define id="spl" name="item" property="product.catalogDistrMapping.standardProductList" type="java.lang.String"/>
        <%if(com.cleanwise.service.api.util.Utility.isTrue(spl)){%>
          <app:storeMessage key="shoppingItems.text.y"/>
        <%}else{%>
          <app:storeMessage key="shoppingItems.text.n"/>
        <%}%>
      </logic:present>
      <logic:notPresent name="item" property="product.catalogDistrMapping.standardProductList">
        <app:storeMessage key="shoppingItems.text.n"/>
      </logic:notPresent>
</div></td>
<%} %>

     <%if(resaleItemsAllowed){%>
        <td class="text"><div>
                <logic:equal name="item" property="reSaleItem" value="true">
                        &nbsp;<app:storeMessage key="shoppingItems.text.y"/>
                </logic:equal>
                <logic:notEqual name="item" property="reSaleItem" value="true">
                        &nbsp;<app:storeMessage key="shoppingItems.text.n"/>
                </logic:notEqual>
        </div></td>
     <%}%>
     </tr>
   </logic:iterate>

<% } // end of if (! isaMLAStore) else %>





