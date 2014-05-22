
<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.value.*"%>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Locale" %>


<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>




<bean:define id="theForm" name="SHOP_FORM" type="com.cleanwise.view.forms.UserShopForm"/>
<%
   int TABIDX = 0;
   ShoppingCartData shoppingCart = (ShoppingCartData) session.getAttribute(Constants.SHOPPING_CART);
   String itemsString =ClwI18nUtil.getShoppingItemsString(request,shoppingCart);
   boolean inventoryShopping = ShopTool.isInventoryShoppingOn(request);
   String  ogListUI = ShopTool.getOGListUI(request,RefCodeNames.INVENTORY_OG_LIST_UI.SEPARATED_LIST);
%>

<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>


<script language="JavaScript1.2">
<!--

fmtprt= new Image();
fmtprt.src="<%=IMGPath%>/printerfriendly.gif";

fmtexc= new Image();
fmtexc.src="<%=IMGPath%>/excelformat.gif";

function viewPrinterFriendly() {
  var loc = "printerFriendlyOrderGuide.do?action=pdfPrint";
  
  prtwin = window.open(loc,"printer_friendly",
    "menubar=yes,resizable=yes,scrollbars=yes,toolbar=yes,status=yes,height=500, width=775,left=100,top=165");
  prtwin.focus();

  return false;
}

function viewExcelFormat() {
  var loc = "printerFriendlyOrderGuide.do?action=excelPrint";
  
  <% if (theForm.getAppUser().getUserAccount().isHideItemMfg()) { %>
    loc = loc + "&showMfg=n&showMfgSku=n";
  <% } %>
  prtwin = window.open(loc,"excel_format",
    "menubar=yes,resizable=yes,scrollbars=yes,toolbar=yes,status=yes,height=500, width=775,left=100,top=165");
  prtwin.focus();

  return false;
}

function actionMultiSubmit(actionDef, action) {

  var aaal = document.getElementsByName('action');
  for ( var i = 0; i < 1; i++ ) {
    var aaa = aaal[i];
    aaa.value = action;
    aaa.form.submit();
  }

 return false;
}

//-->

</script>

<table ID=162 cellpadding="0" cellspacing="0" align="center"
  class="tbstd" width="<%=Constants.TABLEWIDTH%>" >
<tr>
<td>  <jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"t_shopToolbar.jsp")%>'/></td>
</tr>
</table>

<logic:equal name="SHOP_FORM" property="userOrderGuideNumber" value="0">
<logic:equal name="SHOP_FORM" property="templateOrderGuideNumber" value="0">
<table ID=163 cellpadding="0" cellspacing="0" align="center"
  class="tbstd" width="<%=Constants.TABLEWIDTH%>" >
<tr>
<td align="center">
<b><app:storeMessage key="shop.og.text.noOrderGuideAvailable"/></b>
</td>
</tr>
</table>
</logic:equal>
</logic:equal>



<%
int  COL_COUNT = 13;

if (inventoryShopping) {
  COL_COUNT = COL_COUNT + 3;
}

boolean firstrow = true;
int rowIdx = 0;

String prevCategory = "", thisItemCategory = "";
 %>


<!-- Order guide select section -->
<% if(theForm.getUserOrderGuideNumber()>0 ||
      theForm.getTemplateOrderGuideNumber()>0) {%>

<table ID=164 cellpadding="0" cellspacing="0" align="center"
  class="tbstd" width="<%=Constants.TABLEWIDTH%>" >

  <html:form styleId="165"  action="/store/shop.do?action=orderGuideSelect">
<tr>
<td width="57px">&nbsp;</td>
  <td>
<br>
  <html:select name="SHOP_FORM" property="templateOrderGuideId"
          onchange="javascript:  document.forms[0].submit();"  >
  <html:option value="-1"><app:storeMessage key="shop.og.text.templateOrderGuides"/></html:option>
  <html:options  name="SHOP_FORM" property="templateOrderGuideIds"
   labelName="SHOP_FORM" labelProperty="templateOrderGuideNames"/>
  <html:options  name="SHOP_FORM" property="userOrderGuideIds"
   labelName="SHOP_FORM" labelProperty="userOrderGuideNames"/>
  </html:select>

  <html:hidden property="orderBy"/>
  </html:form>
</td>
<td align=center width="300px">

<logic:greaterThan name="SHOP_FORM" property="orderGuideId" value="0">
<logic:greaterThan name="SHOP_FORM" property="cartItemsSize" value="0">

<% int orderByFormIndex = 1; %>

<form ID=166 name="Personalized">
<br>&nbsp;

<br>

 <% String b_print = IMGPath + "/b_print.gif"; %>
  <a ID=167 href="#" class="linkButton" onclick="viewPrinterFriendly();"
  ><img ID=168 src="<%=b_print%>" border="0"/><app:storeMessage key="global.label.printerFriendly"/></a>
&nbsp;&nbsp;&nbsp;&nbsp;
 <% String b_excel = IMGPath + "/b_excel.gif"; %>
  <a ID=169 href="#" class="linkButton" onclick="viewExcelFormat();"
  ><img ID=170 src="<%=b_excel%>" border="0"/><app:storeMessage key="global.label.excelFormat"/></a>

</form>

<%
orderByFormIndex = 2;
%>

</td>

<td>
<br>
      <html:form styleId="171" action="/store/shop.do?action=orderGuideSelect">
  <b><app:storeMessage key="shop.og.text.orderBy"/></b>
<%
String formSubmitString =
  "javascript: { document.forms[" + orderByFormIndex + "].submit();}";
%>
<html:select name="SHOP_FORM" property="orderBy"
 onchange="<%=formSubmitString%>" >
<html:option value="<%=\"\"+Constants.ORDER_BY_CATEGORY%>">
   <app:storeMessage key="shop.og.text.category"/>
</html:option>
<html:option value="<%=\"\"+Constants.ORDER_BY_CUST_SKU%>">
   <app:storeMessage key="shop.og.text.ourSkuNum"/>
</html:option>
<html:option value="<%=\"\"+Constants.ORDER_BY_NAME%>">
   <app:storeMessage key="shop.og.text.productName"/>
</html:option>
        </html:select>
      </html:form>
</td>
</logic:greaterThan> <% /* Items are available for display. */ %>

</logic:greaterThan> <% /* Only display the printer
      friendly order guide button if
      an order guide is selected. */ %>


</tr>
</table>
<% } %>

<%@ include file="f_shopping_msgs.jsp" %>


<logic:greaterThan name="SHOP_FORM" property="orderGuideId" value="0">
<logic:equal name="SHOP_FORM" property="cartItemsSize" value="0">

<table ID=172 cellpadding="0" cellspacing="0" align="center"
  class="tbstd" width="<%=Constants.TABLEWIDTH%>" >

<tr>
<td class="text" align="center">
  <b><app:storeMessage key="shop.og.text.noItemsInOrderGuide"/></b>
</td>
  </tr>
</table>
</logic:equal>

<logic:greaterThan name="SHOP_FORM" property="cartItemsSize" value="0">

<table ID=173 cellpadding="0" cellspacing="0" align="center"
  class="tbstd" width="<%=Constants.TABLEWIDTH%>" >
  <tr>  <td class="tableoutline"><img ID=174 src="<%=IMGPath%>/cw_spacer.gif" height="3"></td>  </tr>
</table>

<%
   String recalc_img = IMGPath + "/b_recalculate.gif";
   String upd_inv_img = IMGPath + "/b_updateinventory.gif";
   int numberOfIventoryItems = 0;
   int numberOfRegularItems = 0;
%>

<logic:iterate id="tmpitem" name="SHOP_FORM" property="cartItems"
   offset="0" indexId="tidx"
   type="com.cleanwise.service.api.value.ShoppingCartItemData">

<%
if (tmpitem.getIsaInventoryItem()) {
++numberOfIventoryItems;
} else {
++numberOfRegularItems;
}
%>
</logic:iterate>

    <table ID=175 cellpadding="0" cellspacing="0" align="center" width="<%=Constants.TABLEWIDTH%>" >
        <tr>
            <td>
                <% 

                String numberOfIventoryItemsStr = String.valueOf(numberOfIventoryItems);
                String numberOfRegularItemsStr = String.valueOf(numberOfRegularItems);
                String TABIDX_STR = String.valueOf(TABIDX);
                
                if (inventoryShopping && RefCodeNames.INVENTORY_OG_LIST_UI.COMMON_LIST.equals(ogListUI)) {%>

                <jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_og_common_list.jsp")%>'>
                   <jsp:param name="numberOfIventoryItems" 	value="<%=numberOfIventoryItemsStr%>" /> 
                   <jsp:param name="numberOfRegularItems" 	value="<%=numberOfRegularItemsStr%>" /> 
                   <jsp:param name="TABIDX" 	value="<%=TABIDX_STR%>" /> 
                </jsp:include>
                <%} else {
                %>
                <jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_og_separated_list.jsp")%>'>
                   <jsp:param name="numberOfIventoryItems" 	value="<%=numberOfIventoryItemsStr%>" /> 
                   <jsp:param name="numberOfRegularItems" 	value="<%=numberOfRegularItemsStr%>" /> 
                   <jsp:param name="TABIDX" 	value="<%=TABIDX_STR%>" /> 
                </jsp:include>
                
                <%}%>
            </td>

        </tr>
    </table>
</logic:greaterThan>
</logic:greaterThan>

<%@ include file="f_table_bottom.jsp" %>



<script type="text/javascript" language="JavaScript">
<!--
  var ix = document.getElementById("IDX_0");
  if (ix != null) {
  ix.focus();
  }
  // -->
</script>





