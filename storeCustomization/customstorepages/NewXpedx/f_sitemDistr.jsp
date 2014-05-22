<bean:define id="checkoutForm" name="CHECKOUT_FORM" type="com.cleanwise.view.forms.CheckoutForm"/>
<bean:define id="SCartDistV" name="CHECKOUT_FORM" property="cartDistributors" type="ShoppingCartDistDataVector"/>

 <%------------------------------- SCartDistV   loop ----------------------------------------%>
<logic:iterate id="scdistD" name="SCartDistV"
       offset="0" indexId="DISTIDX"
       type="com.cleanwise.service.api.value.ShoppingCartDistData">

    <bean:define id="distName" name="scdistD" property="distributorName"/>
    <bean:define id="distItems" name="scdistD" property="shoppingCartItems" type="java.util.List"/>

    <%-------------------------------------------------------------------------------------%>
    <tr>
    <%
      boolean allowPoByVender = ("true".equals(request.getParameter("allowPoByVender")))?true:false;
      if(!allowPoByVender){%>
        <td class="shopcharthead" colspan="<%=COL_COUNT%>">
                &nbsp;<bean:write name="scdistD" property="distributorName"/>
        </td>
    <% } else { %>
      <td class="shopcharthead" colspan="<%=COL_COUNT-10%>">
        &nbsp;<bean:write name="scdistD" property="distributorName"/>
      </td>
      <td class="shopcharthead" colspan="6">
            <b><app:storeMessage key="shop.checkout.text.poNumber"/>  </b><br>
            <html:text name="CHECKOUT_FORM" property='<%="cartDistributors[" + DISTIDX + "].poNumber"%>' size="20" maxlength="22" />
            <%if(appUser.getPoNumRequired()){%>
                    <font color="red">*</font>
                <%}%>
                <br>
      </td>
      <td class="shopcharthead" colspan="4">
        &nbsp;
      </td>
    <% } %>
    </tr>
    <%-------------------------------------------------------------------------------------%>

      <logic:iterate id="sciD" name="distItems"
         offset="0" indexId="IDX"
         type="com.cleanwise.service.api.value.ShoppingCartItemData">

         <bean:define id="orderNumber" name="sciD" property="orderNumber"/>
         <bean:define id="itemId" name="sciD" property="product.productId" type="java.lang.Integer" />

    <%
      String
        quantityEl =     "quantityElement["+IDX+"]",
        itemIdsEl =      "itemIdsElement["+IDX+"]",
        orderNumbersEl = "orderNumbersElement["+IDX+"]"
      ;

      SiteInventoryInfoView inventoryItemInfo =
        ShopTool.getInventoryItem(request, itemId.intValue());
    %>

    <%
      if (checkoutView) {
          if ( sciD.getQuantity() > 0) {
          /* Only include items in the checkout screen if
             the item is being ordered.
          */
      %>

    <%  if(orderby==Constants.ORDER_BY_CATEGORY) { %>
    <%      if ( !prevGroup.equals(sciD.getCategoryPath()) ) { %>
      <tr> <td class="shopcategory" colspan="<%=COL_COUNT%>">

        <table cellpadding="0" cellspacing="0" align="center"  width=<%=Constants.TABLEWIDTH_m4%>
          <tr>
            <td class="shopcategory">&nbsp;&nbsp;&nbsp;<bean:write name="sciD" property="categoryPath"/></td>
            <td class="shopcategory" align="right">
    <%  if ( sciD.getProduct().getCostCenterId() > 0 ) { %>
            <%= sciD.getProduct().getCostCenterName() %>
    <% } %>
        </td></tr></table>
      </td></tr>
    <%                          }
          prevGroup = sciD.getCategoryPath();
          } %>
    <%@ include file="f_sitem.jsp" %>
    <%          }
      }
    %>

    </logic:iterate>
    <%-------------------------------------------------------------------------------------%>


    <%  if(null != scdistD.getDistFreightImplied() && 0 < scdistD.getDistFreightImplied().size()) {
    %>
    <bean:define id="freightImplied" name="scdistD" property="distFreightImplied"
      type="java.util.List"/>

    <logic:iterate id="impliedD" name="freightImplied"
         offset="0" indexId="impliedIDX"
         type="com.cleanwise.service.api.value.FreightTableCriteriaData">
    <tr>

      <td class="freighttext">&nbsp;</td>
      <td class="freighttext" colspan="2" align="left">
      <bean:write name="impliedD" property="shortDesc" />:
      </td>
      <td class="freighttext">
            <logic:equal name="impliedD" property="freightCriteriaTypeCd" value="<%=RefCodeNames.FREIGHT_CRITERIA_TYPE_CD.ESTIMATE%>">
                <%--should be i18n, but this is hard coded in the ShoppingCartDistData object as well.  Do both at same time to be consistent--%>
                        To Be Determined
                </logic:equal>
                <logic:notEqual name="impliedD" property="freightCriteriaTypeCd" value="<%=RefCodeNames.FREIGHT_CRITERIA_TYPE_CD.ESTIMATE%>">
                <logic:present name="impliedD" property="freightAmount">
                <bean:define id="frAmt" name="impliedD" property="freightAmount" />
                <%=ClwI18nUtil.getPriceShopping(request,frAmt,"&nbsp;")%>
                </logic:present>
                <logic:notPresent name="impliedD" property="freightAmount">
                <%=ClwI18nUtil.getPriceShopping(request,new java.math.BigDecimal(0),"&nbsp;")%>
                </logic:notPresent>
                </logic:notEqual>
      </td>
      <td colspan="<%=COL_COUNT-4%>" class="freighttext">&nbsp;</td>
    </tr>
    </logic:iterate>
     <%-------------------------------------------------------------------------------------%>

    <%  }

      if(null != scdistD.getDistFreightOptions() && 0 < scdistD.getDistFreightOptions().size()) {
    %>
    <bean:define id="freightOptions" name="scdistD" property="distFreightOptions"
      type="java.util.List"/>

    <tr>
      <td class="freighttext">&nbsp;</td>
      <td class="freighttext"><app:storeMessage key="shoppingItems.text.shippingCost"/> </td>
      <td class="freighttext" align="left" colspan="<%=COL_COUNT-3%>">
        <html:select name="CHECKOUT_FORM" property="distFreightVendor">
          <html:option value="" />
          <html:options collection="freightOptions" property="freightTableCriteriaId" labelProperty="shortDesc"  />
        </html:select>
        <bean:define id="selectedFrMsg" name="scdistD" property="distSelectableFreightMsg" type="String"/>
        <bean:define id="selectedFrAmt" name="scdistD" property="distSelectableFreightCost" />
    <%          if (null != selectedFrMsg && selectedFrMsg.trim().length() > 0) {  %>
        <bean:write name="scdistD" property="distSelectableFreightMsg" />
    <%          } else {  %>
        <%=ClwI18nUtil.getPriceShopping(request,selectedFrAmt,"&nbsp;")%>
    <%          }  %>
      </td>
      <td class="freighttext"  colspan="<%=COL_COUNT-6%>">&nbsp;</td>
    </tr>
    <%  } else {  %>
      <html:hidden name="CHECKOUT_FORM" property="distFreightVendor" value=""/>
    <%  }  %>
    <%  if(false){  %>
    <tr>
      <td class="freighttext">&nbsp;</td>
      <td class="freighttext" colspan="2" align="left"><app:storeMessage key="shoppingItems.text.tax"/></td>
      <td class="freighttext">
              <logic:present name="scdistD" property="salesTax">
              <bean:define id="salesTax" name="scdistD" property="salesTax" />
              <%=ClwI18nUtil.getPriceShopping(request,salesTax,"&nbsp;")%>
              </logic:present>
              <logic:notPresent name="scdistD" property="salesTax">
              <%=ClwI18nUtil.getPriceShopping(request,new java.math.BigDecimal(0),"&nbsp;")%>
              </logic:notPresent>
      </td>
      <td colspan="<%=COL_COUNT-4%>" class="freighttext">&nbsp;</td>
    </tr>
    <%  }  %>
  </logic:iterate>
 <%-------------------------------end of SCartDistV loop   ----------------------------%>
