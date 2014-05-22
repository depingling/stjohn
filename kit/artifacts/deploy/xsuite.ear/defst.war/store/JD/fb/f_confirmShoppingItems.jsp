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
<%@ page import="java.util.HashMap" %>

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
 int rowIdx = 0;
 
 //Create counter for the discountPerDist on the Request Level (f_confirmShoppingItems.jsp can be called several times)
 Integer ct = (Integer) request.getAttribute("discountCounterConf");
 if (ct == null) {
     ct = new Integer(0);
 }
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

<%int colCount = ((Integer)pageContext.getAttribute("colCountInt")).intValue();%>


  <!-- List of items -->


  <!-- page contral bar -->
  <%--
  <tr>
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

  <bean:define id="distItems" name="scdistD" property="shoppingCartItems"
    type="java.util.List"/>

  <tr>
  <logic:notEmpty name="scdistD" property="poNumber">
    <td class="shopcharthead" colspan="<%=colCount-10%>">
      &nbsp;<bean:write name="scdistD" property="distributorName"/>
    </td>
    <td class="shopcharthead" colspan="10">
    <b><app:storeMessage key="shop.checkout.text.poNumber"/>  </b>
            <bean:write name="scdistD" property="poNumber"/>
        </td>
  </logic:notEmpty>
  <logic:empty name="scdistD" property="poNumber">
      <td class="shopcharthead" colspan="<%=colCount%>">
	      &nbsp;<bean:write name="scdistD" property="distributorName"/>
	  </td>
  </logic:empty>
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

    <bean:define id="freightImplied" name="scdistD" property="distFreightImplied"
      type="java.util.List"/>
    <%  if(null != freightImplied && 0 < freightImplied.size()) {
    %>
      <logic:iterate id="impliedD" name="freightImplied"
         offset="0" indexId="impliedIDX"
         type="com.cleanwise.service.api.value.FreightTableCriteriaData"><%
         boolean toBeDetermined = RefCodeNames.FREIGHT_CRITERIA_TYPE_CD.ESTIMATE.equals(impliedD.getFreightCriteriaTypeCd());
         BigDecimal freightAmount = impliedD.getFreightAmount();
         BigDecimal handlingAmount = impliedD.getHandlingAmount();
         if (handlingAmount == null) {
           handlingAmount = new BigDecimal(0);
         } else {
           handlingAmount = handlingAmount.setScale(2,BigDecimal.ROUND_HALF_UP);
         }
         if (freightAmount != null) {
           handlingAmount = handlingAmount.add(freightAmount.setScale(2,BigDecimal.ROUND_HALF_UP));    
         }
         if (toBeDetermined || handlingAmount.compareTo(BigDecimal.ZERO) != 0) {
%>
    <tr>
      <td class="freighttext">&nbsp;</td>
      <td class="freighttext" colspan="<%=(colCount-1)%>" align="left">
      <bean:write name="impliedD" property="shortDesc" />:
      <logic:equal name="impliedD" property="freightCriteriaTypeCd" value="<%=RefCodeNames.FREIGHT_CRITERIA_TYPE_CD.ESTIMATE%>">
To Be Determined
      </logic:equal>
      <logic:notEqual name="impliedD" property="freightCriteriaTypeCd" value="<%=RefCodeNames.FREIGHT_CRITERIA_TYPE_CD.ESTIMATE%>">
<%=ClwI18nUtil.getPriceShopping(request,handlingAmount,"&nbsp;")%>
      </logic:notEqual>
      </td>
    </tr><%} %>
    </logic:iterate>
    <%  }

    // Fuel Surcharge Output
    if (null != scdistD.getDistFuelSurchargeList() && 0 < scdistD.getDistFuelSurchargeList().size()) {
     %><bean:define id="fuelSurchargeList" name="scdistD" property="distFuelSurchargeList" type="java.util.List"/>
        <logic:iterate id="fuelSurchargeCriteria" name="fuelSurchargeList" offset="0" indexId="impliedIDX" type="com.cleanwise.service.api.value.FreightTableCriteriaData"><%
        boolean toBeDetermined = RefCodeNames.FREIGHT_CRITERIA_TYPE_CD.ESTIMATE.equals(fuelSurchargeCriteria.getFreightCriteriaTypeCd());
        BigDecimal fuelSurchargeAmt = fuelSurchargeCriteria.getHandlingAmount();
        if (toBeDetermined || (fuelSurchargeAmt != null && BigDecimal.ZERO.compareTo(fuelSurchargeAmt) != 0)) {
%>
        <tr>
            <td class="freighttext">&nbsp;</td>
            <td class="freighttext" colspan="<%=(colCount-1)%>" align="left">
                <bean:write name="fuelSurchargeCriteria" property="shortDesc" />:
                <logic:equal name="fuelSurchargeCriteria" property="freightCriteriaTypeCd" value="<%=RefCodeNames.FREIGHT_CRITERIA_TYPE_CD.ESTIMATE%>">
                    <%--should be i18n, but this is hard coded in the ShoppingCartDistData object as well.  Do both at same time to be consistent--%>
To Be Determined
                </logic:equal>
                <logic:notEqual name="fuelSurchargeCriteria" property="freightCriteriaTypeCd" value="<%=RefCodeNames.FREIGHT_CRITERIA_TYPE_CD.ESTIMATE%>">
<%=ClwI18nUtil.getPriceShopping(request, fuelSurchargeAmt, "&nbsp;")%>
                </logic:notEqual>
            </td>
        </tr><%} %>
        </logic:iterate>
    <%
    }
    %><%
    // Small Order Fee Output
    if (null != scdistD.getDistSmallOrderFeeList() && 0 < scdistD.getDistSmallOrderFeeList().size()) {
    %><bean:define id="smallOrderFeeList" name="scdistD" property="distSmallOrderFeeList" type="java.util.List"/>
        <logic:iterate id="smallOrderFeeCriteria" name="smallOrderFeeList" offset="0" indexId="impliedIDX" type="com.cleanwise.service.api.value.FreightTableCriteriaData"><%
        boolean toBeDetermined = RefCodeNames.FREIGHT_CRITERIA_TYPE_CD.ESTIMATE.equals(smallOrderFeeCriteria.getFreightCriteriaTypeCd());
        BigDecimal smallOrderFeeAmt = smallOrderFeeCriteria.getHandlingAmount();
        if (toBeDetermined || (smallOrderFeeAmt != null && smallOrderFeeAmt.compareTo(BigDecimal.ZERO) != 0)) {
%>        
        <tr>
            <td class="freighttext">&nbsp;</td>
            <td class="freighttext" colspan="<%=(colCount-1)%>" align="left">
                <bean:write name="smallOrderFeeCriteria" property="shortDesc" />:
                <logic:equal name="smallOrderFeeCriteria" property="freightCriteriaTypeCd" value="<%=RefCodeNames.FREIGHT_CRITERIA_TYPE_CD.ESTIMATE%>">
                    <%--should be i18n, but this is hard coded in the ShoppingCartDistData object as well.  Do both at same time to be consistent--%>
To Be Determined
                </logic:equal>
                <logic:notEqual name="smallOrderFeeCriteria" property="freightCriteriaTypeCd" value="<%=RefCodeNames.FREIGHT_CRITERIA_TYPE_CD.ESTIMATE%>">
<%=ClwI18nUtil.getPriceShopping(request, smallOrderFeeAmt, "&nbsp;")%>
                </logic:notEqual>
            </td>
        </tr><%}%>
        </logic:iterate><%
    }    %>
    <!-- Added by SVC for Discount: Begin -->
    <% if(null != scdistD.getDistDiscountImplied() && 0 < scdistD.getDistDiscountImplied().size()) {
    %>
        <bean:define id="chtForm" name="CHECKOUT_FORM"
             type="com.cleanwise.view.forms.CheckoutForm"/>
        <%  /*** Added by SVC ***/
        HashMap discountPerDistHM = chtForm.getDiscountAmtPerDist();

        int frTblId = scdistD.getFrTblId();
        int distId = scdistD.getDistId(frTblId); 
        Integer distIdInt = new Integer( distId );
        BigDecimal distIdBD = new BigDecimal(0);
        distIdBD = (BigDecimal)discountPerDistHM.get(distIdInt);
    %>
    <bean:define id="discountImplied" name="scdistD" property="distDiscountImplied"
      type="java.util.List"/>
    <logic:iterate id="impliedDisc" name="discountImplied"
         offset="0" indexId="impliedIDX"
         type="com.cleanwise.service.api.value.FreightTableCriteriaData"><%
if (distIdBD != null && distIdBD.compareTo(BigDecimal.ZERO) != 0) {
    %><tr>
      <td class="freighttext">&nbsp;</td>
      <td class="freighttext" colspan="<%=(colCount-1)%>" align="left">
      <% 
           int cti = ct.intValue(); //convert ct Integer Onject into primitive int
      %>
        <logic:present name="impliedDisc" property="discount">
        <bean:define id="disAmt" name="impliedDisc" property="discount" />
        <% /*** new code for Discount in DOLLARS and PERCENTAGE-DOLLARS (multiple distributors) ***/ %>
        <%-- if (discountPerDist.size() > 0 ) { --%>
           <% BigDecimal dAmt2 = distIdBD; %>
           <% BigDecimal zeroValue1 = new BigDecimal(0); 
              if ( dAmt2.compareTo(zeroValue1)>0 ) {           
                 dAmt2 = dAmt2.negate(); 
              } 
              dAmt2 = dAmt2.setScale(2, BigDecimal.ROUND_HALF_UP);
           %>            
           <% if (dAmt2.compareTo(new BigDecimal(0))!=0 ) {  %>
               <bean:write name="impliedDisc" property="shortDesc" />:
               <%=ClwI18nUtil.getPriceShopping(request,dAmt2,"&nbsp;")%>
               <%//=ClwI18nUtil.getPriceShopping(request,disAmt,"&nbsp;")%>
           <% } %>
           <% 
              ct = new Integer(cti+1); //increment int cti and convert it into Integer Object ct
              request.setAttribute("discountCounterConf", ct);
           %>
        <%-- } --%>
        </logic:present>
        <logic:notPresent name="impliedDisc" property="discount">
        <%//=ClwI18nUtil.getPriceShopping(request,new java.math.BigDecimal(0),"&nbsp;")%>
        </logic:notPresent>
      </td>
    </tr><%} %>
    </logic:iterate>
    <%  }  %>

    <!-- Added by SVC for Discount: End -->

    <bean:define id="freightOptions" name="scdistD" property="distFreightOptions"
      type="java.util.List"/>
    <%  if(null != freightOptions && 0 < freightOptions.size()) {
    %>
    <tr>
      <td class="freighttext">&nbsp;</td>
      <td class="freighttext" colspan="<%=(colCount-1)%>"><app:storeMessage key="shop.checkout.text.shippingCost"/>
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





