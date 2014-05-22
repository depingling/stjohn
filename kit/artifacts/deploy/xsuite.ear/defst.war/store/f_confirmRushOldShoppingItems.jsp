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
 %>

<app:commonDefineProdColumnCount
    id="colCountInt"
          viewOptionEditCartItems="false"
          viewOptionQuickOrderView="false"
          viewOptionAddToCartList="false"
          viewOptionOrderGuide="false"
          viewOptionShoppingCart="false"
          viewOptionCheckout="true"
          viewOptionConfirmCheckout="true"
          viewOptionInventoryList="false"
    />
<%int colCount = ((Integer)pageContext.getAttribute("colCountInt")).intValue() ;%>

  <!-- List of items -->

<%--
  <!-- page contral bar -->
  <tr>
  <td class="shopcharthead"><div><app:storeMessage key="shoppingItems.text.orderQty"/></div></td>
  <td class="shopcharthead"><div><app:storeMessage key="shoppingItems.text.ourSkuNum"/></div></td>
  <td class="shopcharthead"><div><app:storeMessage key="shoppingItems.text.productName"/></div></td>
  <td class="shopcharthead"><div><app:storeMessage key="shoppingItems.text.size"/></div></td>
  <td class="shopcharthead"><div><app:storeMessage key="shoppingItems.text.pack"/></div></td>
  <td class="shopcharthead"><div><app:storeMessage key="shoppingItems.text.uom"/></div></td>
  <td class="shopcharthead"><div><app:storeMessage key="shoppingItems.text.color"/></div></td>
  <% if (!appUser.getUserAccount().isHideItemMfg()) { %>
  <% if(RefCodeNames.STORE_TYPE_CD.MANUFACTURER.equals(appUser.getUserStore().getStoreType().getValue())) { %>
    <td class="shopcharthead"><div>&nbsp;</div></td>
  <% } else { %>
    <td class="shopcharthead"><div><app:storeMessage key="shoppingItems.text.manufacturer"/></div></td>
  <% } %>
  <td class="shopcharthead"><div><app:storeMessage key="shoppingItems.text.mfgSkuNum"/></div></td>
  <% } %>
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
--%>
<%--------------------------------- HEADER -----------------------------------------%>

   <tr>

  <app:commonDisplayProdHeader
          viewOptionEditCartItems="false"
          viewOptionQuickOrderView="false"
          viewOptionAddToCartList="false"
          viewOptionOrderGuide="false"
          viewOptionShoppingCart="false"
          viewOptionCheckout="true"
          viewOptionConfirmCheckout="true"
          viewOptionInventoryList="false"
    />

   </tr>

<%-----------------------------------------------------------------------------------------%>



<%
    int rowIdx = 0;
    boolean isaMLAStore = RefCodeNames.STORE_TYPE_CD.MLA.equals(appUser.getUserStore().getStoreType().getValue());
  // for store type other than MLA, we need display distributor info
  // (which items belong to which distributor)
  if (! isaMLAStore) {

%>
<% /*
bean:define id="SCartDistV" name="CHECKOUT_FORM" property="cartDistributors" type="ShoppingCartDistDataVector"/>
<logic:iterate id="scdistD" name="SCartDistV"
  offset="0" indexId="DISTIDX"
     type="com.cleanwise.service.api.value.ShoppingCartDistData">

  <bean:define id="distItems" name="scdistD" property="shoppingCartItems"
    type="java.util.List"/>
*/ %>

<%
ShoppingCartDistDataVector SCartDistV = theForm.getCartDistributors();
java.util.List orderedItems = theForm.getItems();
for(int ii=0; ii<SCartDistV.size(); ii++) {
    ShoppingCartDistData scdistD = (ShoppingCartDistData) SCartDistV.get(ii);
    java.util.List distItems = scdistD.getShoppingCartItems();
%>
  <tr>
  <% if (scdistD.getPoNumber() != null) { %>
    <td class="shopcharthead" colspan="2">
      &nbsp;<%=scdistD.getDistributorName()%>
    </td>
    <td class="shopcharthead" colspan="<%=colCount -2%>">
    <b><app:storeMessage key="shop.checkout.text.poNumber"/>  </b>
            <%=scdistD.getPoNumber()%>
        </td>
  <% } else { %>
            <td class="shopcharthead" colspan="<%=colCount%>">
  <% } %>
  </tr>

<%
   /*
  <logic:iterate id="item" name="distItems"
   offset="0" indexId="itemsIdx"
   type="com.cleanwise.service.api.value.ShoppingCartItemData">
   */
   for(int jj=0; jj<distItems.size(); jj++) {
   Integer itemsIdx = new Integer(jj);
   ShoppingCartItemData item = (ShoppingCartItemData) distItems.get(jj);
   int itemId = item.getProduct().getProductId();
   boolean foundFl = false;
   for(int kk=0; kk<orderedItems.size(); kk++) {
       ShoppingCartItemData orderedItem = (ShoppingCartItemData) orderedItems.get(kk);
	   if(orderedItem.getProduct().getProductId()==itemId) {
	      foundFl = true;
		  break;
	   }
    }
	if(!foundFl) continue;

%>

     <% if(theForm.getSortBy()==Constants.ORDER_BY_CATEGORY) { %>
         <% if(theForm.isCategoryChanged(((Integer)itemsIdx).intValue())) { %>
           <tr>
           <td class="shopcategory" colspan="<%=colCount%>">&nbsp;&nbsp;&nbsp;<%=item.getCategoryPath()%></td>
           </tr>
         <% } %>
     <% } %>

     <%--
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
       <%=item.getActualSkuNum()%>&nbsp;
     </div></td>
     <td class="text"><div>
       <%=item.getProduct().getCatalogProductShortDesc() %>&nbsp;
     </div></td>
     <td class="text"><div>
       <%=item.getProduct().getSize()%>&nbsp;
     </div></td>
     <td class="text"><div>
       <%=item.getProduct().getPack()%>&nbsp;
     </div></td>
     <td class="text"><div>
       <%=item.getProduct().getUom()%>&nbsp;
     </div></td>
     <td class="text"><div>
       <%=item.getProduct().getColor()%>&nbsp;
     </div></td>
     <% if (!appUser.getUserAccount().isHideItemMfg()) { %>
     <td class="text"><div>
       <% if(RefCodeNames.STORE_TYPE_CD.MANUFACTURER.equals(appUser.getUserStore().getStoreType().getValue())) { %>
         &nbsp;
       <% } else { %>
       <%=item.getProduct().getManufacturerName()%>&nbsp;
       <% } %>
     </div></td>
     <td class="text"><div>
       <%=item.getProduct().getManufacturerSku()%>&nbsp;
     </div></td>
     <% } %>
     <td class="text"><div>
       <logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">
         <% /*bean:define id="price"  name="item" property="price"/> */%>
         <%=ClwI18nUtil.getPriceShopping(request,item.getPrice(),"&nbsp;")%>&nbsp;&nbsp;
		 <% /*
         <logic:equal name="item" property="contractFlag" value="true">
         <logic:equal name="<#=Constants.APP_USER#>" property="showWholeCatalog" value="true">
           *
         </logic:equal></logic:equal>
		 */%>
       </logic:equal>
       <logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="false">
         &nbsp;
       </logic:equal>
     </div></td>
     <td class="text"><div>
       <logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">
	     <% /*
         <bean:define id="amount"  name="item" property="amount"/>
		 */ %>
         <%=ClwI18nUtil.getPriceShopping(request,item.getAmount(),"&nbsp;")%>
       </logic:equal>
       <logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="false">
         &nbsp;
       </logic:equal>
     </div></td>

    <% if (appUser.getUserAccount().isShowSPL()) { %>
    <td align="center" class="text"><div>
	      <%
		  /*
          <logic:present name="item" property="product.catalogDistrMapping.standardProductList">
            <bean:define id="spl" name="item" property="product.catalogDistrMapping.standardProductList" type="java.lang.String"/>
		  */
		  String spl = item.getProduct().getCatalogDistrMapping().getStandardProductList();
		  if(spl = null) spl = "false";
		  %>
            <%if(com.cleanwise.service.api.util.Utility.isTrue(spl)){%>
              <app:storeMessage key="shoppingItems.text.y"/>
            <%}else{%>
              <app:storeMessage key="shoppingItems.text.n"/>
            <%}%>
		  <%
          /*
          </logic:present>
          <logic:notPresent name="item" property="product.catalogDistrMapping.standardProductList">
            <app:storeMessage key="shoppingItems.text.n"/>
          </logic:notPresent>
		  */
		  %>
    </div></td>
    <%} %>



     <%if(resaleItemsAllowed){%>
        <td class="text"><div>
		       <%
			   /*
                <logic:equal name="item" property="reSaleItem" value="true">
				*/
				if(item.getReSaleItem()) {
				%>
                   &nbsp;<app:storeMessage key="shoppingItems.text.y"/>
				<%
                 /*
                 </logic:equal>
                <logic:notEqual name="item" property="reSaleItem" value="true">
				 */
				 } else {
				 %>
                    &nbsp;<app:storeMessage key="shoppingItems.text.n"/>
				<%
				/*
                </logic:notEqual>
				*/
				}
				%>
        </div></td>
     <%}%>
     </tr>
     --%>

     <%String itemLink1 = "shop.do?action=item&source=orderGuide&itemId="
       +itemId+"&qty="+item.getQuantity();
       String trColor=ClwCustomizer.getEvenRowColor(request.getSession(),rowIdx++);
     %>
	 <% request.setAttribute("itemBeanAttr", item); %>
     <bean:define id="itemBean"  name="itemBeanAttr" type="com.cleanwise.service.api.value.ShoppingCartItemData" />

     <tr bgcolor="<%=trColor%>">
     <app:commonDisplayProd
             viewOptionEditCartItems="false"
             viewOptionQuickOrderView="false"
             viewOptionAddToCartList="false"
             viewOptionOrderGuide="false"
             viewOptionShoppingCart="false"
             viewOptionCheckout="true"
             viewOptionConfirmCheckout="true"
             viewOptionInventoryList="false"
             name="itemBean"
             link="<%=itemLink1%>"
             index="<%=itemsIdx%>"
             inputNameQuantity="<%= \"cartLine[\" + itemsIdx + \"].quantityString\" %>"/>
     </tr>


   <%
   /*
   </logic:iterate>
   */
   }
   %>
   <%
   /*
    <bean:define id="freightImplied" name="scdistD" property="distFreightImplied"
      type="java.util.List"/>
	*/
	 java.util.List freightImplied = scdistD.getDistFreightImplied();
     if(null != freightImplied && 0 < freightImplied.size()) {
	  /*
      <logic:iterate id="impliedD" name="freightImplied"
         offset="0" indexId="impliedIDX"
         type="com.cleanwise.service.api.value.FreightTableCriteriaData">
		 */
	 for(int mm=0; mm<freightImplied.size(); mm++) {
	 FreightTableCriteriaData impliedD = (FreightTableCriteriaData) freightImplied.get(mm);
    %>
    <tr>
      <td class="freighttext">&nbsp;</td>
      <td class="freighttext" colspan="<%=(colCount-1)%>" align="left">
      <%=impliedD.getShortDesc()%>:
	    <%
		/*
        <logic:present name="impliedD" property="freightAmount">
        <bean:define id="frAmt" name="impliedD" property="freightAmount" />
		*/
		BigDecimal frAmt = impliedD.getFreightAmount();
		if(frAmt==null) frAmt = new BigDecimal(0);
		%>
        <%=ClwI18nUtil.getPriceShopping(request,frAmt,"&nbsp;")%>
		<%
		/*
        </logic:present>
        <logic:notPresent name="impliedD" property="freightAmount">
        <#=ClwI18nUtil.getPriceShopping(request,new java.math.BigDecimal(0),"&nbsp;")#>
        </logic:notPresent>
		*/
		%>
      </td>
    </tr>
	<%
	/*
    </logic:iterate>
	*/
	}
	%>
    <%  }  %>

    <%
	/*
    <bean:define id="freightOptions" name="scdistD" property="distFreightOptions"
      type="java.util.List"/>
	*/
	  java.util.List freightOptions = scdistD.getDistFreightOptions();
      if(null != freightOptions && 0 < freightOptions.size()) {
    %>
    <tr>
      <td class="freighttext">&nbsp;</td>
      <td class="freighttext" colspan="<%=(colCount-1)%>"><app:storeMessage key="shop.checkout.text.shippingCost"/>
        (<%=scdistD.getDistSelectableFreightVendorName()%>):
        <%
		/*
        <bean:define id="selectedFrMsg" name="scdistD" property="distSelectableFreightMsg" type="String"/>
        <bean:define id="selectedFrAmt" name="scdistD" property="distSelectableFreightCost" />
		*/
        String selectedFrMsg = scdistD.getDistSelectableFreightMsg();
        BigDecimal selectedFrAmt = new BigDecimal(scdistD.getDistSelectableFreightCost());
		%>
    <%          if (null != selectedFrMsg && selectedFrMsg.trim().length() > 0) {  %>
        <%=selectedFrMsg%> />
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
	  <%
	  /*
                        <logic:present name="scdistD" property="salesTax">
              <bean:define id="salesTax" name="scdistD" property="salesTax" />
		*/
		BigDecimal salesTax = scdistD.getSalesTax();
		if(salesTax==null) salesTax = new BigDecimal(0);
       %>
              <%=ClwI18nUtil.getPriceShopping(request,salesTax,"&nbsp;")%>
		<%
        /*
                        </logic:present>
                        <logic:notPresent name="scdistD" property="salesTax">
                           <#=ClwI18nUtil.getPriceShopping(request,new java.math.BigDecimal(0),"&nbsp;")#>
                        </logic:notPresent>
		*/
        %>
      </td>
      <td colspan="<%=(colCount-4)%>" class="freighttext">&nbsp;</td>
    </tr>
    <% }%>
<%
/*
</logic:iterate>
*/
}
%>

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
     <%--
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
     <% if (!appUser.getUserAccount().isHideItemMfg()) { %>
     <td class="text"><div>
       <% if(RefCodeNames.STORE_TYPE_CD.MANUFACTURER.equals(appUser.getUserStore().getStoreType().getValue())) { %>
         &nbsp;
       <% } else { %>
         <bean:write name="item" property="product.manufacturerName"/>&nbsp;
       <% } %>
     </div></td>
     <td class="text"><div>
       <bean:write name="item" property="product.manufacturerSku"/>&nbsp;
     </div></td>
     <% } %>
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
     --%>

     <%String itemLink1 = "shop.do?action=item&source=orderGuide&itemId="
       +itemId+"&qty="+item.getQuantity();
       String trColor=ClwCustomizer.getEvenRowColor(request.getSession(),rowIdx++);
     %>

     <tr bgcolor="<%=trColor%>">
     <app:commonDisplayProd
             viewOptionEditCartItems="false"
             viewOptionQuickOrderView="false"
             viewOptionAddToCartList="false"
             viewOptionOrderGuide="false"
             viewOptionShoppingCart="false"
             viewOptionCheckout="true"
             viewOptionConfirmCheckout="true"
             viewOptionInventoryList="false"
             name="item"
             link="<%=itemLink1%>"
             index="<%=itemsIdx%>"
             inputNameQuantity="<%= \"cartLine[\" + itemsIdx + \"].quantityString\" %>"/>
     </tr>


   </logic:iterate>

<% } // end of if (! isaMLAStore) else %>





