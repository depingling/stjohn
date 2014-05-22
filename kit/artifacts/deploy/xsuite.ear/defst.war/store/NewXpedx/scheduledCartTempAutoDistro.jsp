<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page import="com.cleanwise.view.forms.ShoppingCartForm" %>
<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>
<link rel="stylesheet" href="../externals/newxpedx_styles.css">

<script type="text/javascript" language="JavaScript">
    <!--
    function f_subOrderByReq() {
        var orderByElement = document.getElementById("subOrderBy");
        orderByElement.submit();
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

<bean:define id="theForm" name="INVENTORY_SHOPPING_CART_FORM" type="com.cleanwise.view.forms.ShoppingCartForm" />

<%--bread crumb --%>
<table class="breadcrumb">
<tr class="breadcrumb">
<td class="breadcrumb">
	<a class="breadcrumb" href="../userportal/msbsites.do"><app:storeMessage key="global.label.Home"/></a>
</td>
<td class="breadcrumb"><a class="breadcrumb">></a></td>
<td class="breadcrumb">
	<%--<a class="breadcrumb" href="#">Auto-Distro</a>--%>
	<div class="breadcrumbCurrent"><app:storeMessage key="breadcrumb.label.autoDistro"/></div>
</td>
</tr class="breadcrumb">
<tr><td>&nbsp;</td></tr>
</table>

<%--End of Shopping Cart Header--%>

<%

CleanwiseUser appUser = ShopTool.getCurrentUser(request);
    int COL_COUNT = 0;
    boolean isHideForNewXpdex = true;
    //boolean withBudget = false;  //sciD.getProduct().getCostCenterId() > 0
    boolean inventoryShopping = true;
    boolean resaleItemsAllowed = false;
    boolean
      quickOrderView = false,
      checkoutView = false,
      editCartItems = true,
      f_showSelectCol = false;

    //------ determine the type of Shopping Cart : with budget or not ------------//
    //CostCenterDataVector costCenters = ShopTool.getAllCostCenters(request);
    //withBudget = (costCenters != null && costCenters.size() > 1);
    //----------------------------------------------------------------------------//
    String itemFilter = "";
    Boolean filterVal =Boolean.FALSE;

    String storeDir = ClwCustomizer.getStoreDir();
    String IPTH = (String) session.getAttribute("pages.store.images");

    SiteData thisSite = appUser.getSite();

%>
<%---------  preparing error/worning/confirmation messages  -----------------%>
<%
    Object cartErrors = request.getAttribute("org.apache.struts.action.ERROR");
    java.util.List cartWarnings = null;
    java.util.List iwarnings = null;
    String shoppingCartName = (String) request.getParameter("shoppingCartName");

    if (shoppingCartName == null) {
      shoppingCartName = Constants.SHOPPING_CART;
    }
    ShoppingCartData v_shoppingCart = (ShoppingCartData) session.getAttribute(shoppingCartName);
    if (v_shoppingCart != null) {
      cartWarnings = v_shoppingCart.getWarningMessages();
      iwarnings = v_shoppingCart.getItemMessages();
    }

  String confirmation = (theForm.getConfirmMessage()==null)? "" : theForm.getConfirmMessage();
  String errors = (cartErrors==null )? "" : "errors";
  String warnings = ((cartWarnings!=null && cartWarnings.size()>0) || (iwarnings!= null && iwarnings.size()>0))? "warnings" : "" ;
  String mess="&nbsp;";
  String messStyle = "color:#FFFFFF;";

  String submitParam = request.getParameter("submitparam");
  String visibilityVal = "hidden";
%>
<%-- Object errors = request.getAttribute("org.apache.struts.action.ERROR");--%>


<!-- No items in the shopping cart -->
<logic:equal name="INVENTORY_SHOPPING_CART_FORM" property="cartItemsSize" value="0">

    <table border="0" cellpadding="0" cellspacing="0" align="center"
            width="<%=Constants.TABLEWIDTH800%>"
		   style="{border-left: 0; border-right:0;}">
        <tr>
            <td class="text" align="center">
                <br><b>
                <app:storeMessage key="shoppingCart.text.shoppingCartIsEmpty"/>
            </b><br>&nbsp;
            </td>
        </tr>
 </table>

</logic:equal>


<!-- There are items in the shopping cart -->
<logic:greaterThan name="INVENTORY_SHOPPING_CART_FORM" property="cartItemsSize" value="0">
<%--
<html:form action="/store/scheduledCart.do">
--%>
<html:form name="INVENTORY_SHOPPING_CART_FORM" action="/store/scheduledCart.do">
<%
		//String isGotoAnchor = (String)request.getAttribute("gotoAnchor");
		if("true".equals(isGotoAnchor)){ %>
			<body onload="pageScroll()">
		<%}else{%>
			<body>
		<%}%>
<table border="0" cellpadding="0" cellspacing="0" align="center"   width="<%=Constants.TABLEWIDTH800%>"
style="{border-left: 0; border-right:0;}">


  <tr><td>
			<%--
                <jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_shoppingInvItems.jsp")%>'>
                    <jsp:param name="allowEdits" value="true"/>
                    <jsp:param name="orderBy" value="<%=theForm.getOrderBy()%>"/>
                </jsp:include>
			--%>

				<%--<tr><td nowrap="nowrap"><b><app:storeMessage key="shoppingCart.text.scheduledCart"/></b></td></tr>--%>
				<% itemFilter = "inventory"; %>
				<%@ include file="f_shoppingInvHeader.jsp" %>

				<jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_inv_cart_oitems.jsp")%>'>
				<jsp:param name="jspFormName"value="INVENTORY_SHOPPING_CART_FORM"/>
				<jsp:param name="itemFilter" value="inventory"/>
				<jsp:param name="colCount" value="7"/>
				<jsp:param name="keyName" value="shop.og.table.header.title.inventory"/>
				<jsp:param name="showHistory" value="autoDistroPage"/>
				</jsp:include>

  </td></tr>

</table>
<table width="<%=Constants.TABLEWIDTH800%>">
<tr>  <td class="tableoutline" ><img src="images/spacer.gif" height="1"></td>  </tr>
<app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.UPDATE_AUTO_DISTRO%>">
   <tr>
     <td  align=right><a name="buttonSection"></a>
        <table border="0" cellpadding="0" cellspacing="0" >
          <tr>
            <app:xpedxButton label='shoppingCart.text.updateAutoDistro'
                      url="#"
                      onClick='actionMultiSubmit("submitQty", "updateAutoDistro");'
              />
          </tr>
        </table>
     </td>
  </tr>
<%  visibilityVal = (submitParam != null && submitParam.equals("submitQty2")) ? "visible" : "hidden" ;%>
  <tr><td>
  <div id="submitQty2" style="visibility:<%=visibilityVal%>">
    <%@ include file="f_showCartMessages.jsp" %>
  </div>
  </td></tr>

</app:authorizedForFunction>
</table>

  <html:hidden property="action" value="submitQty"/>
  <html:hidden property="action" value="BBBBBBB"/>
  <html:hidden property="submitparam" value="submitQty2"/>
  </body>
</html:form>

</logic:greaterThan>



