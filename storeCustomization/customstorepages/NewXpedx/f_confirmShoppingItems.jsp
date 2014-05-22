<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.GregorianCalendar" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.view.utils.ShopTool" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.value.ShoppingCartData" %>
<%@ page import="com.cleanwise.service.api.value.ShoppingCartItemData" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="com.cleanwise.service.api.util.Utility"%>

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

<style type="text/css">
.shopcategory {
    font-weight: bold;
    color: #333333;
    background-color: #CCCCFF ;
}
</style>
<%
 int rowCount = 0;
 boolean withBudget = false;  //sciD.getProduct().getCostCenterId() > 0
 boolean isPendingOrderBudjet = Utility.isTrue(request.getParameter("isPendingOrderBudjet"));

 CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
 String utype = appUser.getUser().getUserTypeCd();
 SiteData thisSite = appUser.getSite();
 String prevGroup = "";

 boolean resaleItemsAllowed = ShopTool.isResaleItemsAllowed(request);
 int COL_COUNT = 8;
 if (appUser.getUserAccount().isShowSPL()) {
   COL_COUNT++;
 }
 if (appUser.getUserAccount().isHideItemMfg()) {
  COL_COUNT = COL_COUNT - 2;
 }
 //------ determine the type of Shopping Cart : with budget or not ------------
 CostCenterDataVector costCenters = ShopTool.getSiteCostCenters(request);
 //withBudget = (costCenters != null && costCenters.size() > 1);
 withBudget = thisSite.hasBudgets();
//----------------------------------------------------------------------------

  ShoppingCartData shoppingCart = ShopTool.getCurrentShoppingCart(request);
//  theForm.setCartItems(shoppingCart.getItems());

 %>
  <!-- List of items -->


  <!-- page contral bar -->
  <tr>
  <td class="shopcharthead"><div><app:storeMessage key="shoppingItems.text.orderQty"/></div></td>
  <td class="shopcharthead"><div><app:storeMessage key="shoppingItems.text.uom"/></div></td>
  <td class="shopcharthead"><div><app:storeMessage key="shoppingItems.text.ourSkuNum"/></div></td>
  <td class="shopcharthead"><div><app:storeMessage key="shoppingItems.text.productName"/></div></td>
<%--
  <td class="shopcharthead"><div><app:storeMessage key="shoppingItems.text.size"/></div></td>
  <td class="shopcharthead"><div><app:storeMessage key="shoppingItems.text.pack"/></div></td>
  <td class="shopcharthead"><div><app:storeMessage key="shoppingItems.text.color"/></div></td>
  <% if (!appUser.getUserAccount().isHideItemMfg()) { %>
  <% if(RefCodeNames.STORE_TYPE_CD.MANUFACTURER.equals(appUser.getUserStore().getStoreType().getValue())) { %>
    <td class="shopcharthead"><div>&nbsp;</div></td>
  <% } else { %>
    <td class="shopcharthead"><div><app:storeMessage key="shoppingItems.text.manufacturer"/></div></td>
  <% } %>
  <td class="shopcharthead"><div><app:storeMessage key="shoppingItems.text.mfgSkuNum"/></div></td>
  <% } %>
--%>
  <logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">
    <td class="shopcharthead"><div><app:storeMessage key="shoppingItems.text.price"/></div></td>
    <td class="shopcharthead"><div><app:storeMessage key="shoppingItems.text.amount"/></div></td>
  </logic:equal>
  <logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="false">
     <td class="shopcharthead" align="center" colspan="2">&nbsp;</td>
  </logic:equal>
  <%-- if (appUser.getUserAccount().isShowSPL()) { %>
    <td class="shopcharthead"><div class="fivemargin"><app:storeMessage key="shoppingItems.text.spl"/></div></td>
  <%} %>
  <% if(resaleItemsAllowed){%>
     <td class="shopcharthead"><div><font color="red"><app:storeMessage key="shoppingItems.text.reSaleItem"/></font></div></td>
  <%}else{%>
      <td class="shopcharthead" align="center">&nbsp;</td>
  <%}--%>
  </tr>

  <%
  boolean isaMLAStore = RefCodeNames.STORE_TYPE_CD.MLA.equals(appUser.getUserStore().getStoreType().getValue());
  // for store type other than MLA, we need display distributor info
  // (which items belong to which distributor)
  if (! isaMLAStore) {
%>

<bean:define id="SCartDistV" name="CHECKOUT_FORM" property="cartDistributors" type="ShoppingCartDistDataVector"/>

<logic:iterate id="scdistD" name="SCartDistV"
  offset="0" indexId="DISTIDX"
     type="com.cleanwise.service.api.value.ShoppingCartDistData">
  <bean:define id="distItems" name="scdistD" property="shoppingCartItems" type="java.util.List"/>
<%
  if ( withBudget && DISTIDX ==0 ) {
    shoppingCart.orderByCostCenter(distItems);
  }else{
    shoppingCart.orderByCategory(distItems);

  }
  CostCenterData wrk = null;
  int centerId = 0;
  String centerName = "";
  CostCenterData costCenterData = null;
  boolean firstRow=true;
  String prevCategory="";
%>

  <logic:iterate id="sciD" name="distItems"
   offset="0" indexId="itemsIdx"
   type="com.cleanwise.service.api.value.ShoppingCartItemData">
     <bean:define id="orderNumber" name="sciD" property="orderNumber"/>
     <bean:define id="itemId" name="sciD" property="product.productId"/>
     <bean:define id="itemIdQtyElement" value="<%=\"itemIdQtyElement[\"+itemsIdx+\"]\"%>"/>
<% if (!withBudget) {%>
     <% if(theForm.getSortBy()==Constants.ORDER_BY_CATEGORY) { %>
	 <%--
     <% if(theForm.isCategoryChanged(((Integer)itemsIdx).intValue())) { %>
       <tr>
       <td class="shopcategory" colspan="<%=COL_COUNT%>">&nbsp;&nbsp;&nbsp;<bean:write name="sciD" property="categoryName"/></td>
       </tr>
     <% } %>
	 --%>

	 <% if(firstRow){%>
		<tr>
         <td class="shopcategory" colspan="<%=COL_COUNT%>">&nbsp;&nbsp;&nbsp;<bean:write name="sciD" property="categoryName"/></td>
        </tr>
		<% firstRow=false;
			prevCategory=sciD.getCategoryName();
		}else if(!sciD.getCategoryName().equals(prevCategory)){%>
		<tr>
         <td class="shopcategory" colspan="<%=COL_COUNT%>">&nbsp;&nbsp;&nbsp;<bean:write name="sciD" property="categoryName"/></td>
        </tr>
		<%prevCategory=sciD.getCategoryName();
		}%>

     <% } %>
    <%@ include file="f_confirmSitem.jsp" %>
<% } %>
<% if (withBudget && DISTIDX ==0) {%>
<% boolean isNewGroupHeader =(sciD.getProduct().getCostCenterId() == 0 )
                     ? (!prevGroup.equals(sciD.getCategoryPath()))
                     : (!prevGroup.equals(sciD.getProduct().getCostCenterName()));

  if( isNewGroupHeader ) {

    int itemCenterId = sciD.getProduct().getCostCenterId();
    String itemCenterName = sciD.getProduct().getCostCenterName();
    if (itemCenterId != 0){
      Iterator iter = costCenters.iterator();
      while (wrk !=null || iter.hasNext()) {
        //   calculation cost center attributes
        costCenterData = (CostCenterData) iter.next();
        centerId   = costCenterData.getCostCenterId();
        centerName = costCenterData.getShortDesc();

        if(itemCenterName.compareTo(centerName) == 0) {
          //display cost center group title for current shopping cart item
          wrk = costCenterData;
          break;
        }
        if(itemCenterName.compareTo(centerName) > 0) {
          //No Items in the shopping cart for this cost center
          if ( (wrk == null) ||((wrk != null) && (centerName.compareTo(wrk.getShortDesc()) > 0))){
            %>
            <%@ include file="f_groupTitleInc.jsp"  %>
            <%
            String styleClass = (((rowCount++) %2 )==0) ?  "evenRowColor" : "oddRowColor";
            %>
            <tr class="<%=styleClass%>" >
              <td  style="padding-left:20px" colspan="<%=COL_COUNT%>"><app:storeMessage key="shoppingItems.text.noItemsForCostCenter"/></td>
            </tr>
            <%
            }
          continue;
        }
        break;
      }
    } %>
     <%@ include file="f_groupTitleInc.jsp"  %>
  <% }%>
  <%@ include file="f_confirmSitem.jsp" %>
<% } %>
   </logic:iterate>
<%
if (withBudget && DISTIDX ==0) {  // DISTIDX ==0 applied to show only for the 1-st distributer
  if (costCenters != null && costCenters.size() > 0) {
    String maxVal = ((CostCenterData)costCenters.get(costCenters.size()-1)).getShortDesc();
    ShoppingCartItemData sciD = null;
    if ((wrk != null) && (maxVal.compareTo( wrk.getShortDesc() )>0)) {
      for (int i = 0; i < costCenters.size(); i++) {
        costCenterData = (CostCenterData)costCenters.get(i);
        centerId = costCenterData.getCostCenterId();
        centerName = costCenterData.getShortDesc();
        if (centerName.compareTo(wrk.getShortDesc()) > 0) { %>
          <%@ include file="f_groupTitleInc.jsp"  %>
          <% String styleClass = (((rowCount++) %2 )==0) ?  "evenRowColor" : "oddRowColor"; %>
          <tr class="<%=styleClass%>" >
            <td  style="padding-left:20px" colspan="<%=COL_COUNT%>"><app:storeMessage key="shoppingItems.text.noItemsForCostCenter"/></td>
          </tr>
      <% }
      }
    }
  }
}
%>
</logic:iterate>

<%--  } else {   %>

  <logic:iterate id="item" name="CHECKOUT_FORM" property="items"
   offset="0" indexId="itemsIdx"
   type="com.cleanwise.service.api.value.ShoppingCartItemData">
     <bean:define id="orderNumber" name="item" property="orderNumber"/>
     <bean:define id="itemId" name="item" property="product.productId"/>
     <bean:define id="itemIdQtyElement" value="<%=\"itemIdQtyElement[\"+itemsIdx+\"]\"%>"/>

     <% if(theForm.getSortBy()==Constants.ORDER_BY_CATEGORY) { %>
     <% if(theForm.isCategoryChanged(((Integer)itemsIdx).intValue())) { %>
       <tr>
       <td class="shopcategory" colspan="<%=COL_COUNT%>"><bean:write name="item" property="categoryName"/></td>
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
   </logic:iterate>
--%>
<% } // end of if (! isaMLAStore) else %>





