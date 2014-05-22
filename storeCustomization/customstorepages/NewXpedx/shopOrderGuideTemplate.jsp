
<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.value.*"%>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.util.*" %>
<%@ page import="com.cleanwise.view.forms.UserShopForm"%>
<%@ page import="org.apache.struts.action.ActionErrors" %>
<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>




<bean:define id="theForm" name="SHOP_FORM" type="com.cleanwise.view.forms.UserShopForm"/>

<%
   int TABIDX = 0;
String storeDir=ClwCustomizer.getStoreDir();
   ShoppingCartData shoppingCart = (ShoppingCartData) session.getAttribute(Constants.SHOPPING_CART);
   String itemsString =ClwI18nUtil.getShoppingItemsString(request,shoppingCart);
   boolean inventoryShopping = ShopTool.isInventoryShoppingOn(request);
   String  ogListUI = ShopTool.getOGListUI(request,RefCodeNames.INVENTORY_OG_LIST_UI.SEPARATED_LIST);
%>


<script language="JavaScript1.2">
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

//-->

</script>
<%--
<table ID=162 cellpadding="0" cellspacing="0" align="center"
  class="tbstd" width="<%=Constants.TABLEWIDTH%>" >
<tr>
<td>  <jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"t_shopToolbar.jsp")%>'/></td>
</tr>
</table>
--%>

<html:form name="SHOP_FORM" action="/store/shop.do">

<!-- Order guide selected section -->
<% 
	String modName = "";
	UserShopForm shopForm = (UserShopForm) session.getAttribute("SHOP_FORM");
	if(shopForm!=null && shopForm.getModifiedName()!=null) {
		modName = shopForm.getModifiedName();
	}else{
		modName = shopForm.getOrderGuideName();
	}
	
	if(theForm.getOrderGuideId() > 0) {//theForm.getUserOrderGuideNumber()>0 || theForm.getTemplateOrderGuideNumber()>0) { %>

	<%--bread crumb --%>
	<table class="breadcrumb">
	<tr class="breadcrumb">
	<td class="breadcrumb">
		<a class="breadcrumb" href="../userportal/msbsites.do">
		<app:storeMessage key="global.label.Home"/></a>
	</td>
        <td class="breadcrumb"><span class="breadcrumb">&nbsp;&gt;&nbsp;</span></td>
		<%--
	<td class="breadcrumb">
		<a class="breadcrumb" href="#">
		<app:storeMessage key="template.xpedx.homepage.header.myShoppingList"/>&nbsp;&gt;&nbsp;</a>
	</td>--%>

	<td class="breadcrumb">
		<%--<a class="breadcrumb" href="#"><%=theForm.getOrderGuideName()%></a>--%>
		<div class="breadcrumbCurrent"><%=theForm.getOrderGuideName()%></div>
	</td>
	</tr>
	</table>

	<br>
	<table border="0" cellpadding="0" cellspacing="0" align="center" 
	       class="tbstd" width="<%=Constants.TABLEWIDTH%>"
		   style="{border-left: 0; border-right:0;}">
    <tr>
        
		<td align="left" width="120" valign="middle">
		<a style="font-size: 13px; color:#000000;font-weight: bold;text-decoration:none">
		<app:storeMessage key="template.xpedx.homepage.header.myShoppingList"/></a>
		</td>
		<td align="left" valign="bottom"  width="20%" >
		<input id="modifiedName" type="text" name="modifiedName" value="<%=modName%>" size="30">
		<% 
			boolean isUser=theForm.isUserOrderGuide(theForm.getOrderGuideId()); %>
			
		<%	if(!modName.equals(theForm.getOrderGuideName())){ %>
					<html:hidden property="action" value="updateList"/>
		<% } %>
		
        	
        </td>
		<td align="left" valign="top">
		<% 
		if(isUser){ %>
		<table align="left" valign="top" border="0" cellpadding="0" cellspacing="0" >
			<td align="left" valign="middle"><img src='<%=ClwCustomizer.getSIP(request,"buttonLeft.png")%>' border="0"></td>
	       <td align="center" valign="middle" nowrap class="xpdexGradientButton">
	           <a class="xpdexGradientButton" href="#" onclick="actionMultiSubmit('BBBBBBB','deleteList');"><app:storeMessage key="shoppingCart.text.deleteList"/></a>
	       </td>
	       <td align="left" valign="middle"><img src='<%=ClwCustomizer.getSIP(request,"buttonRight.png")%>' border="0"></td>
	       <td>&nbsp;</td>
		</table>
		<% }else { %>&nbsp;<% } %>
	   </td>
    </tr>
	</table>
	<br>
<% } %>

<%
String messCd = "";
String mess="&nbsp;";
String messStyle = "color:#FFFFFF;";

Object errors =request.getAttribute("org.apache.struts.action.ERROR");
String confirmation = theForm.getConfirmMessage();
String errorMess = request.getParameter("errorMessage");
//String warnings = theForm.getWarningMessage();

java.util.List warnings = null;
java.util.List iwarnings = null;
String shoppingCartName = (String) request.getParameter("shoppingCartName");

if (shoppingCartName == null) {
    shoppingCartName = Constants.SHOPPING_CART;
}
ShoppingCartData v_shoppingCart = (ShoppingCartData) session.getAttribute(shoppingCartName);
if (v_shoppingCart != null) {
  warnings = v_shoppingCart.getWarningMessages();
  iwarnings = v_shoppingCart.getItemMessages();
}

if (errorMess!=null || errors!=null) {
	messCd = "errors";
  mess = errorMess;
  messStyle = "color:#FF0000; white-space: normal; ";
} else if ((warnings!=null && warnings.size()>0) || (iwarnings!= null && iwarnings.size()>0)){
	 messCd = "warnings";
  mess = messCd;
  messStyle = "color:#FF0000; white-space: normal;";
} else if (confirmation!=null && confirmation.trim().length() > 0) {
	messCd = "confirmation";
  mess= confirmation;
  messStyle = "color:#003399; ";
}
%>

<logic:equal name="SHOP_FORM" property="orderGuideId" value="0">
	<table border="0" cellpadding="0" cellspacing="0" align="center" width="<%=Constants.TABLEWIDTH%>" >
            <tr>
                <td   align="center" style="<%=messStyle%>">
                    <b><%=mess%></b>
                </td>
            </tr>
    </table>
</logic:equal>

<logic:equal name="SHOP_FORM" property="userOrderGuideNumber" value="0">
<logic:equal name="SHOP_FORM" property="templateOrderGuideNumber" value="0">
<table ID=163 cellpadding="0" cellspacing="0" align="center"
  width="<%=Constants.TABLEWIDTH%>" >
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

<%--
<%@ include file="f_shopping_msgs.jsp" %>
--%>

<logic:greaterThan name="SHOP_FORM" property="orderGuideId" value="0">
<logic:equal name="SHOP_FORM" property="cartItemsSize" value="0">

<table ID=172 cellpadding="0" cellspacing="0" align="center" border="0"
  width="<%=Constants.TABLEWIDTH%>" >

<tr>
<td class="text" align="center">
  <b><font color="#003399"><app:storeMessage key="shop.og.text.zeroItemsInOrderGuide"/></font></b>
</td>
  </tr>
</table>
<html:hidden property="action" value="BBBBBBB"/>
</logic:equal>

<logic:greaterThan name="SHOP_FORM" property="cartItemsSize" value="0">


<%
   //String recalc_img = IMGPath + "/b_recalculate.gif";
   //String upd_inv_img = IMGPath + "/b_updateinventory.gif";
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
	
	<table border="0" cellpadding="0" cellspacing="0" align="center" width="<%=Constants.TABLEWIDTH%>" >
            <tr>
			
			<td align="center" style="<%=messStyle%>">

			<%if (messCd.equals("errors")) {%>
								
				<html:errors/>
            <%} else if (messCd.equals("warnings")) {%>

                <jsp:include flush='true'

                                    page='<%=ClwCustomizer.getStoreFilePath(request,"f_shopping_msgs.jsp")%>'/>

			<%} else {%>

				<b><%=mess%></b>

			<%}%>	

		</td>
            </tr>
		</table>
	
</logic:greaterThan>
</logic:greaterThan>

</html:form>
<%--
<%@ include file="f_table_bottom.jsp" %>
--%>

<script type="text/javascript" language="JavaScript">
<!--
  var ix = document.getElementById("IDX_0");
  if (ix != null) {
  ix.focus();
  }
  // -->
</script>





