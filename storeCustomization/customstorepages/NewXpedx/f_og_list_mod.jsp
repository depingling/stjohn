
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.service.api.value.*"%>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Locale" %>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page import="com.cleanwise.service.api.value.ShoppingCartData" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>
<!--PAGESRCID=f_og_list_mod.jsp -->
<script language="JavaScript1.2">
<!--
function f_SetChecked(obj,prop) {
 dml=document.SHOP_FORM;
 len = dml.elements.length;
 var val = 0;
 if (obj.checked){
   val=1;
 }
 var i=0;
 for( i=0 ; i<len ; i++) {
   if (dml.elements[i].name==prop && dml.elements[i].disabled!=true) {
     dml.elements[i].checked=val;
   }
 }
}

function actionMultiSubmit(actionDef, action) {
 var aaa = document.getElementsByName('action');

 for(ii=aaa.length-1; ii>=0; ii--) {
   var actionObj = aaa[ii];
   if(actionObj.value==actionDef) {
     actionObj.value=action;
     actionObj.form.submit();
     break;
   }
 }
 return false;
 }

function setAndSubmit(fid, vv, value) {
  var aaa = document.forms[fid].elements[vv];
  aaa.value=value;
  aaa.form.submit();
  return false;

}


<%
String isGotoAnchor = (String)request.getAttribute("gotoAnchor");
if("true".equals(isGotoAnchor)){
%>
	
function getAnchorPosition (anchorName) {
  if (document.layers) {
    var anchor = document.anchors[anchorName];
    return { x: anchor.x, y: anchor.y };
  }
  else if (document.getElementById) {
  
    var anchor = document.getElementById(anchorName);
	if(anchor==null){
        anchor = document.anchors[anchorName];
	}
    var coords = {x: 0, y: 0 };
    while (anchor) {
      coords.x += anchor.offsetLeft;
      coords.y += anchor.offsetTop;
      anchor = anchor.offsetParent;
    }
    return coords;
  }
}

/* for Internet Explorer */
/*@cc_on @*/
/*@if (@_win32)
  document.write("<script id=__ie_onload defer><\/script>");
  var script = document.getElementById("__ie_onload");
  script.onreadystatechange = function() {
    if (this.readyState == "complete") {
      pageScroll(); // call the onload handler
    }
  };
/*@end @*/

function pageScroll() {
  // quit if this function has already been called
  if (arguments.callee.done) return;

  // flag this function so we don't do the same thing twice
  arguments.callee.done = true;

  // kill the timer
  if (_timer) clearInterval(_timer);

	//alert(getAnchorPosition("buttonSection").y);
    window.scrollTo(100,parseInt(getAnchorPosition("buttonSection").y)); 
};

/* for Mozilla/Opera9 */
if (document.addEventListener) {
  document.addEventListener("DOMContentLoaded", pageScroll, false);
}



/* for Safari */
if (/WebKit/i.test(navigator.userAgent)) { // sniff
  var _timer = setInterval(function() {
    if (/loaded|complete/.test(document.readyState)) {
      pageScroll(); // call the onload handler
    }
  }, 10);
}

<%}%>

//-->
</script>

<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>
<bean:define id="theForm" name="SHOP_FORM" type="com.cleanwise.view.forms.UserShopForm"/>

<%
CleanwiseUser appUser = ShopTool.getCurrentUser(request);
    ShoppingCartData shoppingCart = (ShoppingCartData) session.getAttribute(Constants.SHOPPING_CART);
String storeDir=ClwCustomizer.getStoreDir();
    boolean inventoryShopping = Boolean.getBoolean(request.getParameter("inventoryShopping"));
	boolean modernInvShopping = ShopTool.isModernInventoryShopping(request); 
    int numberOfIventoryItems = Integer.parseInt(request.getParameter("numberOfIventoryItems"));
    int numberOfRegularItems = Integer.parseInt(request.getParameter("numberOfRegularItems"));
    String jspFormName = request.getParameter("jspFormName");
    String recalc_img = IMGPath + "/b_recalculate.gif";
    String  ogListUI = ShopTool.getOGListUI(request,RefCodeNames.INVENTORY_OG_LIST_UI.SEPARATED_LIST);
	boolean isHideForNewXpdex = true;
	boolean isUser=theForm.isUserOrderGuide(theForm.getOrderGuideId());
	boolean
  quickOrderView = false,
  checkoutView = false,
  editCartItems = true,
  f_showSelectCol = false;

String
 allowEdits = request.getParameter("allowEdits"),
 thisView = request.getParameter("view"),
 showSelectCol = request.getParameter("showSelectCol"),
 tmp_itemorder = request.getParameter("orderBy")
;

if ( null == thisView ) thisView = "";

if (      thisView.equals("quickOrder") ) { quickOrderView = true; }
else if ( thisView.equals("checkout") )   { checkoutView = true; }

if ( allowEdits != null && allowEdits.equals("false")) {
  editCartItems = false;
}
if ( showSelectCol != null && showSelectCol.equals("true")) {
  f_showSelectCol = true;
  if ( appUser.canMakePurchases() == false )
  {
   f_showSelectCol = false;
  }
}

boolean isUsedPhysicalInventoryAlgorithm = ShopTool.isUsedPhysicalInventoryAlgorithm(request);
boolean isPhysicalCartAvailable = ShopTool.isPhysicalCartAvailable(request);

%>
<bean:define id="theForm" name="<%=jspFormName%>"  type="com.cleanwise.view.forms.UserShopForm"/>

 <html:form name="SHOP_FORM" action="/store/shop.do">
<%
//String isGotoAnchor = (String)request.getAttribute("gotoAnchor");
if("true".equals(isGotoAnchor)){ %>
	<body onload="pageScroll()">
<%}else{%>
<body>
<%}%>
<table border="0" align=center CELLSPACING=0 CELLPADDING=0
	width="<%=Constants.TABLEWIDTH%>"
	style="{border-bottom: black 2px solid;}" >

    <tr>
        
      <app:displayProdHeader viewOptionEditCartItems="false" viewOptionQuickOrderView="<%=quickOrderView%>" 
	  viewOptionAddToCartList="false" viewOptionOrderGuide="true" 
	  onClickDeleteAllCheckBox="SetCheckedGlobalRev('electBox')"/>
		

    </tr>

  <% if(RefCodeNames.INVENTORY_OG_LIST_UI.COMMON_LIST.equals(ogListUI))  {%>

     <jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_oitems.jsp")%>'>
        <jsp:param name="jspFormName"value="SHOP_FORM"/>
        <jsp:param name="keyName" value="shop.og.table.header.title.regular"/>
    </jsp:include>

    <%} else { %>

    <jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_oitems.jsp")%>'>
        <jsp:param name="jspFormName"value="SHOP_FORM"/>
        <jsp:param name="itemFilter" value="inventory"/>
        <jsp:param name="keyName" value="shop.og.table.header.title.inventory"/>
    </jsp:include>


    <jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_oitems.jsp")%>'>
        <jsp:param name="jspFormName"value="SHOP_FORM"/>
        <jsp:param name="itemFilter" value="regular"/>
        <jsp:param name="keyName" value="shop.og.table.header.title.regular"/>
    </jsp:include>

    <%}%>

 </table>
 <br>
 <%--Errors Area%>
<% Object errors =request.getAttribute("org.apache.struts.action.ERROR");
   if(errors!=null) {
%>
<table cellpadding="1" cellspacing="0" align="center"  width="<%=Constants.TABLEWIDTH800%>" >
  <tr>
    <td class="genericerror" align="center"><html:errors/></td>
  </tr>
</table>
<%}%>
<%End of Errors Area--%>
<%
Object errors =request.getAttribute("org.apache.struts.action.ERROR");
java.util.List warnings = null;
java.util.List iwarnings = null;
/*String shoppingCartName = (String) request.getParameter("shoppingCartName");

if (shoppingCartName == null) {
    shoppingCartName = Constants.SHOPPING_CART;
}
ShoppingCartData v_shoppingCart = (ShoppingCartData) session.getAttribute(shoppingCartName);
if (v_shoppingCart != null) {
  warnings = v_shoppingCart.getWarningMessages();
  iwarnings = v_shoppingCart.getItemMessages();
} */
%>

 <table   cellpadding="0" cellspacing="0" align="center"  width="<%=Constants.TABLEWIDTH%>" >
  <tr >
  <a name="buttonSection"></a>
    <td colspan="3" align="right">
      <table align = "right" border="0" cellpadding="0" cellspacing="0" >
		<%-- delete selected --%>
		<% if(isUser){ %>
		<td align="right" valign="middle"><img src='<%=ClwCustomizer.getSIP(request,"buttonLeft.png")%>' border="0"></td>
       <td align="center" valign="middle" nowrap class="xpdexGradientButton">
           <a class="xpdexGradientButton" href="#" onclick="actionMultiSubmit('BBBBBBB','removeSelected');"><app:storeMessage key="shoppingCart.text.deleteSelected"/></a>
       </td>
       <td align="left" valign="middle"><img src='<%=ClwCustomizer.getSIP(request,"buttonRight.png")%>' border="0"></td>
       <td>&nbsp;</td>
	   <% } %>
		<%-- update list --%>
		<% if(isUser){ %>
		<td align="right" valign="middle"><img src='<%=ClwCustomizer.getSIP(request,"buttonLeft.png")%>' border="0"></td>
     <td align="center" valign="middle" nowrap class="xpdexGradientButton">
           <a class="xpdexGradientButton" href="#" onclick="actionMultiSubmit('BBBBBBB','updateList');"><app:storeMessage key="shoppingCart.text.updateList"/></a>
     </td>
     <td align="left" valign="middle"><img src='<%=ClwCustomizer.getSIP(request,"buttonRight.png")%>' border="0"></td>
     <td>&nbsp;</td>
		<% } %>
	 
        <% if (!ShopTool.isPhysicalCartAvailable(request)) { %>
		<%-- add to cart --%>

		<td align="right" valign="middle"><img src='<%=ClwCustomizer.getSIP(request,"buttonLeft.png")%>' border="0"></td>
     <td align="center" valign="middle" nowrap class="xpdexGradientButton">
	 
		<% if(ShopTool.isModernInventoryCartAvailable(request) && !(ShopTool.hasDiscretionaryCartAccessOpen(request))){ %>
           <a class="xpdexGradientButton" href="#" onclick="actionMultiSubmit('BBBBBBB', 'og_addToNewXpedxInvCart');"><app:storeMessage key="shoppingCart.text.addToCart"/></a>
		<% } else { %>
			<a class="xpdexGradientButton" href="#" onclick="actionMultiSubmit('BBBBBBB', 'og_addToCart');"><app:storeMessage key="shoppingCart.text.addToCart"/></a>
		<% } %>
		
     </td>
     <td align="left" valign="middle"><img src='<%=ClwCustomizer.getSIP(request,"buttonRight.png")%>' border="0"></td>

     <td>&nbsp;</td>
	 
		<%-- checkout --%>
		
		<td align="right" valign="middle"><img src='<%=ClwCustomizer.getSIP(request,"buttonLeft.png")%>' border="0"></td>
         <td align="center" valign="middle" nowrap class="xpdexGradientButton">
                <a class="xpdexGradientButton" href="#" onclick="actionMultiSubmit('BBBBBBB', 'og_addToCartViewCart');"><app:storeMessage key="global.action.label.checkout"/></a>
         </td>
         <td align="left" valign="middle"><img src='<%=ClwCustomizer.getSIP(request,"buttonRight.png")%>' border="0"></td>
        <% } %>

      </table>
   </td>

  </tr>

</table>

<html:hidden property="action" value="BBBBBBB"/>
<html:hidden property="action" value="CCCCCCC"/>
</body>
</html:form>
