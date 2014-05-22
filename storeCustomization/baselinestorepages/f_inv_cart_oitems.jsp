<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.value.ShoppingCartData" %>
<%@ page import="com.cleanwise.service.api.value.ShoppingCartItemData" %>
<%@ page import="com.cleanwise.view.forms.ShoppingCartForm" %>
<%@ page import="com.cleanwise.service.api.value.ShoppingInfoData" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ShopTool" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.util.Date" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>
<%@ page language="java" %>
<!--PAGESRCID=f_inv_cart_oitems.jsp-->
<%

    CleanwiseUser appUser      = ShopTool.getCurrentUser(request);
    String showDistInventory   = ShopTool.getShowDistInventoryCode(request);
    boolean inventoryShopping  = ShopTool.isInventoryShoppingOn(request);
    boolean resaleItemsAllowed = ShopTool.isResaleItemsAllowed(request);

    String prevCategory = "";
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
    int colIdx = 0;
    int rowCount = 0;
    String jspFormName = request.getParameter("jspFormName");
    int colCount = Integer.parseInt(request.getParameter("colCount"));
    boolean firstrow = true;
        int tabNum = 0;
    String tabNumS = request.getParameter("tabnum");
        if(tabNumS!=null && tabNumS.trim().length()>0) {
           tabNum = Integer.parseInt(tabNumS);
        }
%>

<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>
<bean:define id="theForm" name="<%=jspFormName%>"  type="com.cleanwise.view.forms.ShoppingCartForm"/>

<bean:define id="SCARTITEMS" name="<%=jspFormName%>" property="cartItems" type="java.util.List"/>

<%
    int scartItemQty = SCARTITEMS.size();
        int rownum = 0;
        int onHandId = 0;
    ShoppingCartData shoppingCart = ((ShoppingCartForm) theForm).getShoppingCart();


    if (orderby == Constants.ORDER_BY_CATEGORY) {
        shoppingCart.orderByCategory(SCARTITEMS);
    }

%>

<logic:iterate id="sciD" name="SCARTITEMS"
               offset="0" indexId="IDX"
               type="com.cleanwise.service.api.value.ShoppingCartItemData">

<bean:define id="orderNumber" name="sciD" property="orderNumber"/>
<bean:define id="itemId" name="sciD" property="product.productId" type="java.lang.Integer"/>
<bean:define id="distName" name="sciD" property="product.catalogDistributorName"/>
<%
colIdx=0;
rownum++;
%>
<% if(filterVal==null || ((ShoppingCartItemData)sciD).getIsaInventoryItem()==filterVal.booleanValue()) { %>

<%if(firstrow) {%>
<tr><td class="shopcategory"></td><td class="shopchartheadTitle" colspan="<%=colCount-1%>"><app:storeMessage key="<%=keyName%>"/></td></tr>
<%firstrow=false;%>
<%}%>


<% if (orderby == Constants.ORDER_BY_CATEGORY) { %>
<% if (!prevCategory.equals(sciD.getCategoryPath())) { %>
<tr>
    <td class="shopcategory" colspan="<%=colCount%>">

        <table cellpadding="0" cellspacing="0" align="center"
               width="<%=Constants.TABLEWIDTH_m4%>">
            <tr>
                <td class="shopcategory">
                    <bean:write name="sciD" property="categoryPath"/>
                </td>
                <td class="shopcategory" align="right">
                    <% if (sciD.getProduct().getCostCenterId() > 0) { %>
                    <%= sciD.getProduct().getCostCenterName() %>
                    <% } %>
                </td>
            </tr>
        </table>
    </td>
</tr>
<%
        }
        prevCategory = sciD.getCategoryPath();
    } %>
<%--
<tr bgcolor="<%=ClwCustomizer.getEvenRowColor(session,rowCount)%>">

<%
    Date
            curDate = Constants.getCurrentDate(),
            effDate = sciD.getProduct().getEffDate(),
            expDate = sciD.getProduct().getExpDate();


    if (effDate != null && effDate.compareTo(curDate) <= 0 &&
            (expDate == null || expDate.compareTo(curDate) > 0)) { %>

<%
        if (sciD.getIsaInventoryItem()) { %>

<td width=15>
       <span class="inv_item">
        i
        <% if (sciD.getAutoOrderEnable()) { %>
        a
        <% } %>
      </span>            <%colIdx++;%>

</td>

<td width=10 align="center">
    <%=sciD.getInventoryParValue()%><%colIdx++;%>
</td>
<td width=10 align="center">
    <html:text name="<%=jspFormName%>" size="3"
        property='<%= "cartLine[" + IDX + "].inventoryQtyOnHandString"%>'
    tabindex='<%=String.valueOf(tabNum*2*scartItemQty+rownum + 10)%>'
        styleId='<%="IDX_" + (onHandId++)%>'
        />
<%colIdx++;%>
</td>

<% } else { //inventory shopping is on but this is not an inventoy item%>
<td width=15 align="center">-<%colIdx++;%></td>
<td width=10 align="center">-<%colIdx++;%></td>
<td width=10 align="center">-<%colIdx++;%></td>
<% }//end sciD.getIsaInventoryItem()%>
<td align="center">

    <logic:equal name="sciD" property="duplicateFlag" value="false">
        <html:text name="<%=jspFormName%>"
                   property='<%= "cartLine[" + IDX + "].quantityString" %>'
                   tabindex='<%=String.valueOf((tabNum*2+1)*scartItemQty +rownum + 10)%>'
                   size="3"/>
    </logic:equal>

    <logic:equal name="sciD" property="duplicateFlag" value="true">
        <html:text name="<%=jspFormName%>"
                   property='<%= "cartLine[" + IDX + "].quantityString" %>'
                   readonly="true"
                   tabindex='<%=String.valueOf((tabNum*2+1)*scartItemQty +rownum + 10)%>'
                   styleClass="customerltbkground" size="3"/>
    </logic:equal>
  <%colIdx++;%>

</td>    <% } else { %>  <td colspan="3">     N/A  <%colIdx+=3;%></td>   <% } %>
<!-- Distributor inventory Quantity -->
<%
    String distInvQtyS = "-";
    if (RefCodeNames.DIST_INVENTORY_DISPLAY.SHOW_FLAG.equals(showDistInventory)) {
        int distInvQty = sciD.getDistInventoryQty();
        if (distInvQty == 0) distInvQtyS = ClwI18nUtil.getMessage(request, "global.text.u", null);
        if (distInvQty > 0) distInvQtyS = ClwI18nUtil.getMessage(request, "global.text.a", null);

%>
<td class="text">
    <div><%=distInvQtyS%>&nbsp;<%colIdx++;%></div>
</td>
<% } %>
<%
    if (RefCodeNames.DIST_INVENTORY_DISPLAY.SHOW_QUANTITIES.equals(showDistInventory)) {
        int distInvQty = sciD.getDistInventoryQty();
        if (distInvQty >= 0) distInvQtyS = String.valueOf(distInvQty);
%>
<td class="text">
    <div><%=distInvQtyS%>&nbsp;<%colIdx++;%></div>
</td>
<% } %>

<td class="text">
    <div>
            <bean:write name="sciD" property="actualSkuNum"/>&nbsp;
        </div><%colIdx++;%>
</td>
<td class="text">
    <div>
        <%
            String itemLink = "shop.do?action=item&source=shoppingCart&itemId="
                    + itemId + "&qty=" + sciD.getQuantity();
        %>
        <html:link href="<%=itemLink%>" tabindex='<%=String.valueOf(IDX.intValue() + 1000)%>'>
            <bean:write name="sciD" property="product.catalogProductShortDesc"/>
            &nbsp;
        </html:link>
    </div>  <%colIdx++;%>
</td>
<td class="text">
    <div>
        <bean:write name="sciD" property="product.size"/>
        &nbsp;</div><%colIdx++;%>
</td>
<td class="text">
    <div>
        <bean:write name="sciD" property="product.pack"/>
        &nbsp;</div><%colIdx++;%>
</td>
<td class="text">
    <div>
        <bean:write name="sciD" property="product.uom"/>
        &nbsp;</div><%colIdx++;%>
</td>

<td align="right" class="text">
   <div>
        <logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">
            <bean:define id="price" name="sciD" property="price"/>
            <%=ClwI18nUtil.getPriceShopping(request, price, "<br>")%>
            <logic:equal name="sciD" property="contractFlag" value="true">
                <logic:equal name="<%=Constants.APP_USER%>" property="showWholeCatalog"
                             value="true">*
                </logic:equal>
            </logic:equal>
        </logic:equal>

        <logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="false">
            &nbsp;
        </logic:equal>
    </div>  <%colIdx++;%>
</td>


<% BigDecimal finalLineAmount = new BigDecimal(sciD.getAmount()); %>
<td align="right" class="text">
    <div>
        <logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">
            <%=ClwI18nUtil.getPriceShopping(request, finalLineAmount, "<br>")%>
        </logic:equal>
        <logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="false">
            &nbsp;
        </logic:equal>
    </div> <%colIdx++;%>
</td>

<% if (appUser.getUserAccount().isShowSPL()) { %>
<td align="center" class="text">
    <div>
        <logic:present name="sciD" property="product.catalogDistrMapping.standardProductList">
            <bean:define id="spl" name="sciD" property="product.catalogDistrMapping.standardProductList"
                         type="java.lang.String"/>
            <%if (com.cleanwise.service.api.util.Utility.isTrue(spl)) {%>
            <app:storeMessage key="shoppingItems.text.y"/>
            <%} else {%>
            <app:storeMessage key="shoppingItems.text.n"/>
            <%}%>
        </logic:present>
        <logic:notPresent name="sciD" property="product.catalogDistrMapping.standardProductList">
            <app:storeMessage key="shoppingItems.text.n"/>
        </logic:notPresent>
    </div>   <%colIdx++;%>
</td>
<%} %>

<td class="text">&nbsp; <%colIdx++;%></td>

<%
    boolean itemIsResale = sciD.getReSaleItem();
%>

<% if (resaleItemsAllowed) { %>
<td class="text" align="center">
    <div>
        <%if (itemIsResale) {%>Y<%} else {%>N<%}%>
    </div>  <%colIdx++;%>
</td>
<% } %>


<logic:equal name="sciD" property="duplicateFlag" value="true">
    <td class="text">
        <div>
            <%=ClwI18nUtil.getPriceShopping(request, finalLineAmount, "<br>")%>
        </div>   <%colIdx++;%>
    </td>
    <td>Dupl<%colIdx++;%></td>
</logic:equal>
<td width=15 align="left" class="text">
<%if (!sciD.getIsaInventoryItem()) { %>

    <html:multibox name="<%=jspFormName%>" property="selectBox" value="<%=new Long(itemId.toString()).toString()%>"/>

<%}%> <%colIdx++;%>
</td>


</tr>
<tr><td colspan="<%=colCount%>" align="center">
<table width="90%"  cellpadding="0" cellspacing="0">
    <%@ include file="f_sitem_audit.jsp" %>
</table>
</td></tr>
--%>
<%String itemLink1 = "shop.do?action=item&source=orderGuide&itemId="
  +itemId+"&qty="+sciD.getQuantity();
  String trColor=ClwCustomizer.getEvenRowColor(request.getSession(),rowCount++);
%>

<tr bgcolor="<%=trColor%>">
<app:commonDisplayProd
        viewOptionEditCartItems="true"
        viewOptionQuickOrderView="false"
        viewOptionAddToCartList="true"
        viewOptionOrderGuide="false"
        viewOptionShoppingCart="true"
        viewOptionInvShoppingCart="true"
        viewOptionCheckout="false"
        viewOptionInventoryList="true"
        name="sciD"
        link="<%=itemLink1%>"
        index="<%=IDX%>"
        inputNameQuantity="<%= \"cartLine[\" + IDX + \"].quantityString\" %>"
        inputNameOnHand="<%= \"cartLine[\" + IDX + \"].inventoryQtyOnHandString\"%>"
        />
</tr>

<%}%>
</logic:iterate>
