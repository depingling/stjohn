
<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>

 

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>





<bean:define id="theForm" name="SHOP_FORM" type="com.cleanwise.view.forms.UserShopForm"/>
<%

   boolean inventoryShopping = ShopTool.isInventoryShoppingOn(request);
   
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
  if ( document.Personalized.elements[0].checked == true ||
       document.Personalized.elements[0].value == "showInfo" ) {
     loc = "printerFriendlyOrderGuidePersonalized.do?action=pdfPrintPers";
  }
  prtwin = window.open(loc,"printer_friendly",
    "menubar=yes,resizable=yes,scrollbars=yes,toolbar=yes,status=yes,height=500, width=775,left=100,top=165");
  prtwin.focus();

  return false;
}

function viewExcelFormat() {
  var loc = "printerFriendlyOrderGuide.do?action=excelPrint";
  if ( document.Personalized.elements[0].checked == true ||
       document.Personalized.elements[0].value == "showInfo" ) {
     loc = "printerFriendlyOrderGuidePersonalized.do?action=excelPrintPers";
  }
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

<table cellpadding="0" cellspacing="0" align="center"  class="tbstd" width="<%=Constants.TABLEWIDTH%>" >
    <tr>
        <td>
            <jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"t_shopToolbar.jsp")%>'/>
        </td>
    </tr>
</table>

<logic:equal name="SHOP_FORM" property="userOrderGuideNumber" value="0">
    <logic:equal name="SHOP_FORM" property="templateOrderGuideNumber" value="0">
        <table cellpadding="0" cellspacing="0" align="center"   class="tbstd" width="<%=Constants.TABLEWIDTH%>" >
            <tr>
                <td align="center">
                    <b><app:storeMessage key="shop.og.text.noOrderGuideAvailable"/></b>
                </td>
            </tr>
        </table>
    </logic:equal>
</logic:equal>



<!-- Order guide select section -->
<% if(theForm.getUserOrderGuideNumber()>0 || theForm.getTemplateOrderGuideNumber()>0) {%>

<table cellpadding="0" cellspacing="0" align="center"  class="tbstd" width="<%=Constants.TABLEWIDTH%>" >

    <tr>
        <td width="57px">&nbsp;</td>

        <td>
            <html:form  action="/store/shop.do?action=orderGuideSelect">

                <br>

                <html:select name="SHOP_FORM" property="templateOrderGuideId" onchange="javascript:  document.forms[0].submit();"  >
                    <html:option value="-1"><app:storeMessage key="shop.og.text.templateOrderGuides"/></html:option>
                    <logic:iterate id="templateOrderGuideOpt" name="SHOP_FORM" property="templateOrderGuideOptions" type="com.cleanwise.service.api.value.OrderGuideData">
                        <html:option value="<%=String.valueOf(templateOrderGuideOpt.getOrderGuideId())%>">
                            <bean:write name="templateOrderGuideOpt" property="shortDesc"/>
                        </html:option>
                    </logic:iterate>
                    <html:option value="-2"><app:storeMessage key="shop.og.text.myOrderGuides"/></html:option>
                    <html:options  name="SHOP_FORM" property="userOrderGuideIds" labelName="SHOP_FORM" labelProperty="userOrderGuideNames"/>
                </html:select>

                <html:hidden property="orderBy"/>
            </html:form>
        </td>

        <td align=center width="300px">

            <logic:greaterThan name="SHOP_FORM" property="orderGuideId" value="0">
            <logic:greaterThan name="SHOP_FORM" property="cartItemsSize" value="0">

            <% int orderByFormIndex = 1; %>

            <form name="Personalized">
                <br>&nbsp;
                <app:storeMessage key="shop.og.text.includeLocationInformation"/>
                <input type="radio" name="pers" value="yes" Checked><app:storeMessage key="shop.og.text.yes"/>
                <input type="radio" name="pers" value="no"><app:storeMessage key="shop.og.text.no"/>
                <br>

                <% String b_print = IMGPath + "/b_print.gif"; %>
                <a href="#" class="linkButton" onclick="viewPrinterFriendly();">
                    <img src="<%=b_print%>" border="0"/>
                    <app:storeMessage key="global.label.printerFriendly"/>
                </a>
                &nbsp;&nbsp;&nbsp;&nbsp;
                <% String b_excel = IMGPath + "/b_excel.gif"; %>
                <a href="#" class="linkButton" onclick="viewExcelFormat();">
                    <img src="<%=b_excel%>" border="0"/>
                    <app:storeMessage key="global.label.excelFormat"/>
                </a>

            </form>

            <%
                orderByFormIndex = 2;
            %>

        </td>

        <td>
            <br>
            <html:form action="/store/shop.do?action=orderGuideSelect">

                <b><app:storeMessage key="shop.og.text.orderBy"/></b>
                <%
                    String formSubmitString =
                            "javascript: { document.forms[" + orderByFormIndex + "].submit();}";
                %>
                <html:select name="SHOP_FORM" property="orderBy" onchange="<%=formSubmitString%>" >
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

<jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_shopping_msgs.jsp")%>'>
    <jsp:param name="shoppingCartName" value="<%=Constants.SHOPPING_CART%>"/>
</jsp:include>
<jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_shopping_msgs.jsp")%>'>
    <jsp:param name="shoppingCartName" value="<%=Constants.INVENTORY_SHOPPING_CART%>"/>
</jsp:include>


<logic:greaterThan name="SHOP_FORM" property="orderGuideId" value="0">

    <logic:equal name="SHOP_FORM" property="cartItemsSize" value="0">

        <table cellpadding="0" cellspacing="0" align="center" class="tbstd" width="<%=Constants.TABLEWIDTH%>" >
            <tr>
                <td class="text" align="center">
                    <b><app:storeMessage key="shop.og.text.noItemsInOrderGuide"/></b>
                </td>
            </tr>
        </table>
    </logic:equal>

    <logic:greaterThan name="SHOP_FORM" property="cartItemsSize" value="0">

        <table cellpadding="0" cellspacing="0" align="center" class="tbstd" width="<%=Constants.TABLEWIDTH%>" >
            <tr>
                <td class="tableoutline">
                    <img src="<%=IMGPath%>/cw_spacer.gif" height="3"/>
                </td>
            </tr>
        </table>

        <%

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

        <table cellpadding="0" cellspacing="0" align="center" width="<%=Constants.TABLEWIDTH%>" >
            <tr>
                <td>
        
                     <jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_og_list_mod.jsp")%>'>
                        <jsp:param name="inventoryShopping" value="<%=inventoryShopping%>"/>
                        <jsp:param name="numberOfIventoryItems" value="<%=numberOfIventoryItems%>"/>
                        <jsp:param name="numberOfRegularItems" value="<%=numberOfRegularItems%>"/>
                        <jsp:param name="jspFormName" value="SHOP_FORM"/>
                    </jsp:include>

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





