<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.view.forms.ShoppingCartForm" %>
<%@ page import="com.cleanwise.service.api.value.ShoppingInfoData" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ShopTool" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.session.*" %>
<%@ page import="com.cleanwise.service.api.util.Utility"%>

<%@ page import="java.util.Iterator" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.util.Date" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>
<%@ page language="java" %>

<!--PAGESRCID=f_phys_cart_oitems.jsp-->

<%
    CleanwiseUser appUser      = ShopTool.getCurrentUser(request);
    SiteData thisSite = appUser.getSite();
    String showDistInventory   = ShopTool.getShowDistInventoryCode(request);

    boolean isHideForNewXpdex = true;
    boolean withBudget = false;  //sciD.getProduct().getCostCenterId() > 0

    boolean inventoryShopping  = ShopTool.isInventoryShoppingOn(request);
    boolean resaleItemsAllowed = ShopTool.isResaleItemsAllowed(request);
    boolean
      quickOrderView = false,
      checkoutView = false,
      editCartItems = true,
      f_showSelectCol = false;

      boolean updateAutoDistro = appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.UPDATE_AUTO_DISTRO);

    //------ determine the type of Shopping Cart : with budget or not ------------
    CostCenterDataVector costCenters = ShopTool.getSiteCostCenters(request);
    withBudget = (costCenters != null && costCenters.size() > 1);
//    withBudget = thisSite.hasBudgets();
    boolean isPendingOrderBudjet = Utility.isTrue(request.getParameter("isPendingOrderBudjet"));
    //----------------------------------------------------------------------------

    String prevGroup = "";
    String tmp_itemorder = request.getParameter("orderBy");
    int orderby = 0;
    if (tmp_itemorder != null) {
        orderby = Integer.parseInt(tmp_itemorder);
    }

    Boolean filterVal=null;
    String itemFilter = request.getParameter("itemFilter");
    String keyName=  request.getParameter("keyName");
    if(keyName==null)   {
        keyName="shop.og.table.header.title.regular";
    }
    if (itemFilter != null && "regular".equals(itemFilter)) {
        filterVal = Boolean.FALSE;
    } else if (itemFilter != null && "inventory".equals(itemFilter)) {
        filterVal = Boolean.TRUE;
    }
    int rowCount = 0;
    String jspFormName = request.getParameter("jspFormName");
    int colCount = Integer.parseInt(request.getParameter("colCount"));
    boolean firstrow = true;
    String showHistory=  request.getParameter("showHistory");
    if(showHistory==null){
        showHistory = "scheduledCartPage";
    }
%>

<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>
<bean:define id="theForm" name="<%=jspFormName%>"  type="com.cleanwise.view.forms.ShoppingCartForm" />

<bean:define id="SCARTITEMS" name="<%=jspFormName%>" property="cartItems" type="java.util.List"/>

<%
    ShoppingCartData shoppingCart = ((ShoppingCartForm) theForm).getShoppingCart();

     if ( withBudget ) {
       shoppingCart.orderByCostCenter(SCARTITEMS);
     }else{
       shoppingCart.orderByCategory(SCARTITEMS);
     }
%>
<% CostCenterData wrk = null;
   CostCenterData costCenterData = null;
   int centerId = 0;
   String centerName = "";
%>
<logic:iterate id="sciD" name="SCARTITEMS"
               offset="0" indexId="IDX"
               type="com.cleanwise.service.api.value.ShoppingCartItemData">

<bean:define id="orderNumber" name="sciD" property="orderNumber"/>
<bean:define id="itemId" name="sciD" property="product.productId" type="java.lang.Integer"/>
<bean:define id="distName" name="sciD" property="product.catalogDistributorName"/>
<app:defineProdColumnCount id="colCountInt" viewOptionEditCartItems="<%=editCartItems%>" viewOptionQuickOrderView="<%=quickOrderView%>"
viewOptionAddToCartList="false" viewOptionOrderGuide="false" physicalInvCart="true"/>
<%colCount = ((Integer)pageContext.getAttribute("colCountInt")).intValue();%>
<% if(filterVal==null || ((ShoppingCartItemData)sciD).getIsaInventoryItem()==filterVal.booleanValue()) { %>

<% if (!withBudget) {%>
<!--without budget Group title-->
<% if (!prevGroup.equals(sciD.getCategoryPath())) {
rowCount=0; %>
<tr>
    <td class="shopcategory" colspan="<%=colCount%>">

        <table cellpadding="0" cellspacing="0" align="center" width="100%">
            <tr>
                <td class="shopcategory">
                   &nbsp;<bean:write name="sciD" property="categoryPath"/>
                </td>
              <%--
                <td class="shopcategory" align="right">
                    <% if (sciD.getProduct().getCostCenterId() > 0) { %>
                    <%= sciD.getProduct().getCostCenterName() %>
                    <% } %>
                </td>
              --%>
            </tr>
        </table>
    </td>
</tr>
<%
   }
   prevGroup = sciD.getCategoryPath();
}   %>

<% if (withBudget) {%>
 <!--with budget Group title-->
<%
 boolean isNewGroupHeader =(sciD.getProduct().getCostCenterId() == 0 )
                     ? (!prevGroup.equals(sciD.getCategoryPath()))
                     : (!prevGroup.equals(sciD.getProduct().getCostCenterName()));

  if( isNewGroupHeader ) {
    int itemCenterId = sciD.getProduct().getCostCenterId();
    String itemCenterName = sciD.getProduct().getCostCenterName();

    if (itemCenterId != 0){
      Iterator iter = costCenters.iterator();
      while (wrk !=null || iter.hasNext()) {
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
            if (!filterVal.booleanValue()) {

            if ( (wrk == null) ||((wrk != null) && (centerName.compareTo(wrk.getShortDesc()) > 0))){
               rowCount=0;
              %>

              <%@ include file="f_groupTitlePhysInc.jsp"  %>
              <% String styleClass = (((rowCount++) %2 )==0) ?  "evenRowColor" : "oddRowColor"; %>
              <tr class="<%=styleClass%>" >
                <td  style="padding-left:20px" colspan="<%=colCount%>"><app:storeMessage key="shoppingItems.text.noItemsForCostCenter"/></td>
              </tr>

              <%
              }
            }

            continue;
        }
        break;
      }
    } %>
    <app:defineProdColumnCount id="colCountInt" viewOptionEditCartItems="<%=editCartItems%>"
viewOptionQuickOrderView="<%=quickOrderView%>" viewOptionAddToCartList="false" viewOptionOrderGuide="false" physicalInvCart="true"/>
<%colCount = ((Integer)pageContext.getAttribute("colCountInt")).intValue();%>
     <%@ include file="f_groupTitlePhysInc.jsp"  %>
  <% }%>
<% } %>


<%----------------Shopping --Items---------------------------%>
<%
String styleClass = (((rowCount) %2 )==0) ?  "evenRowColor" : "oddRowColor";
%>
 <tr class="<%=styleClass%>">
 <%String itemLink = "shop.do?action=msItem&source=t_itemDetail.jsp&itemId="+ itemId + "&qty=" + sciD.getQuantity();%>
<% if (!showHistory.equals("autoDistroPage")) {%>

<app:displayProd viewOptionEditCartItems="<%=editCartItems%>" updateAutoDistro="<%=updateAutoDistro%>" viewOptionQuickOrderView="<%=quickOrderView%>"
viewOptionAddToCartList="false" viewOptionOrderGuide="false" physicalInvCart="true" name="sciD" link="<%=itemLink%>" index="<%=IDX%>" inputNameOnHand='<%="cartLine[" + IDX + "].inventoryQtyOnHandString"%>'
    inputNameQuantity='<%= "cartLine[" + IDX + "].quantityString"%>'
    xhrArgs="{sync:true,form:'schItemsForm',load:updatePage,handleAs:'xml',content:{action:'autoUpdateCart',requestType:'async'}}"/>

<%} else {%>
<% editCartItems = appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.EDIT_SITE_PAR_VALUES);  %>
<app:displayProd viewOptionEditCartItems="<%=editCartItems%>" updateAutoDistro="<%=updateAutoDistro%>" viewOptionQuickOrderView="<%=quickOrderView%>"
viewOptionAddToCartList="false" viewOptionOrderGuide="false" physicalInvCart="false"
    name="sciD" link="<%=itemLink%>" index="<%=IDX%>"  inputNameQuantity='<%= "cartLine[" + IDX + "].quantityString"%>'/>
<% } %>
    <app:defineProdColumnCount id="colCountInt" viewOptionEditCartItems="<%=editCartItems%>"
viewOptionQuickOrderView="<%=quickOrderView%>" viewOptionAddToCartList="false" viewOptionOrderGuide="false" />
<%colCount = ((Integer)pageContext.getAttribute("colCountInt")).intValue();%>
</tr>
<%
if (sciD.getIsaInventoryItem()) {
    if (showHistory.equals("scheduledCartPage")) {
%>
<tr>
    <td colspan="<%=colCount+1%>" align="center">
        <table  width="100%"  cellpadding="0" cellspacing="0">
            <%@ include file="f_sitem_audit.jsp" %>
        </table>
    </td>
</tr>
<%
    }
    if (inventoryShopping && showHistory.equals("autoDistroPage")) {
        java.util.List autoDistroItemHist = sciD.getAutoDistroShoppingCartHistory();
        if (null != autoDistroItemHist && autoDistroItemHist.size() > 0) {
%>
<tr>
    <td colspan="<%=colCount%>" align="center">
        <table  width="100%"  cellpadding="0" cellspacing="0">
<%
            for (int nAutoDistroItem = 0; nAutoDistroItem < autoDistroItemHist.size(); nAutoDistroItem++) {
                ShoppingInfoData shopInfoData = (ShoppingInfoData)autoDistroItemHist.get(nAutoDistroItem);
                String msgKey = shopInfoData.getMessageKey();
%>
            <tr>
                <td width="22" class="<%=styleClass%>" style="font-style: italic;">&nbsp;</td>
                <td width="400" class="<%=styleClass%>" style="font-style: italic;">
                    <%
                    if (msgKey == null) {
                    %>
                        <%=shopInfoData.getValue()%>
                    <%
                    }
                    else {
                    %>
                        <app:storeMessage key="<%=msgKey%>" arg0="<%=shopInfoData.getArg0()%>" arg1="<%=shopInfoData.getArg1()%>" arg2="<%=shopInfoData.getArg2()%>" arg3="<%=shopInfoData.getArg3()%>"/>
                    <%
                    }
                    %>
                </td>
                <td width="150" class="<%=styleClass%>" style="font-style: italic;">
                    <i18n:formatDate  value="<%=shopInfoData.getAddDate()%>" pattern="MM-dd-yyyy" locale="<%=ClwI18nUtil.getUserLocale(request)%>"/>
                </td>
                <td class="<%=styleClass%>" style="font-style: italic;">
                    <app:storeMessage key="shoppingItems.text.by"/>&nbsp;<%=shopInfoData.getModBy()%>
                </td>
                <td class="<%=styleClass%>" style="font-style: italic;">&nbsp;</td>
            </tr>
<%
            }
%>
        </table>
    </td>
</tr>
<%
        }
    }
}
%>
<%}
rowCount++;
%>
</logic:iterate>
<%
String maxVal = ((CostCenterData)costCenters.get(costCenters.size()-1)).getShortDesc();
ShoppingCartItemData sciD = null;
 if (withBudget) {
   if ( !filterVal.booleanValue() && (wrk != null) && (maxVal.compareTo( wrk.getShortDesc() )>0)) {
     for (int i = 0; i < costCenters.size(); i++) {
       rowCount=0;
       costCenterData = (CostCenterData)costCenters.get(i);
       centerId = costCenterData.getCostCenterId();
       centerName = costCenterData.getShortDesc();
       if (centerName.compareTo(wrk.getShortDesc()) > 0) { %>
       <%@ include file="f_groupTitlePhysInc.jsp"  %>
       <% String styleClass = (((rowCount++) %2 )==0) ?  "evenRowColor" : "oddRowColor"; %>
       <tr class="<%=styleClass%>" >
         <td  style="padding-left:20px" colspan="<%=colCount%>"><app:storeMessage key="shoppingItems.text.noItemsForCostCenter"/></td>
       </tr>
       <% }
       }
     }
   }
 %>
