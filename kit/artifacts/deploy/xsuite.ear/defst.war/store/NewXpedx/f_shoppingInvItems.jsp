<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ShopTool" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.view.forms.ShoppingCartForm" %>
<%@ page import= "com.cleanwise.service.api.util.Utility" %>

<%@ page import="org.apache.struts.action.*" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<!--PAGESRCID=f_shoppingInvItems.jsp -->

<script language="JavaScript1.2">
    <!--
    function f_SetChecked(obj,prop) {
      dml=document.INVENTORY_SHOPPING_CART_FORM;
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
<style type="text/css">
.shopcharthead {
    color: #ffffff;
    font-weight: bold;
    background-color: #333333 ;
}
.shopcategory {

    font-weight: bold;
    color: #333333;
    background-color: #CCCCFF ;
}
.evenRowColor {
 background-color: #FFFFFF ;
}
.oddRowColor {
 background-color: #EAEAEA ;
}
.rep_line {
    background-color: #FFFFFF;
    layer-background-color: #FFFFFF;
    border-top: solid 1px black;
    font-style: italic;
    font-size:10px;
}

</style>

<script language="JavaScript" src="../externals/jsutil/scheduleCartUtil.js"></script>

<html:form styleId="schItemsForm" action="/store/scheduledCart.do">
<%
//String isGotoAnchor = (String)request.getAttribute("gotoAnchor");
if("true".equals(isGotoAnchor)){ %>
	<body onload="pageScroll()">
<%}else{%>
<body>
<%}%>
<bean:define id="theForm" name="INVENTORY_SHOPPING_CART_FORM" type="com.cleanwise.view.forms.ShoppingCartForm" />

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
		boolean showInvCartTotal = false;
    //------ determine the type of Shopping Cart : with budget or not ------------//
    //CostCenterDataVector costCenters = ShopTool.getAllCostCenters(request);
    //withBudget = (costCenters != null && costCenters.size() > 1);
    //----------------------------------------------------------------------------//
    String itemFilter = "";
    Boolean filterVal =Boolean.FALSE;

	AccountData accD = appUser.getUserAccount();
	  String isShowInvCartT = accD.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SHOW_INV_CART_TOTAL);
	  if(isShowInvCartT.equals("true")){
		showInvCartTotal=true;
	  }

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

  //String confirmation = theForm.getConfirmMessage();
  String confirmation = (String)request.getAttribute(Constants.XPEDX_CONFIRM_MSG);

  String errors = (cartErrors==null )? "" : "errors";
  String warnings = ((cartWarnings!=null && cartWarnings.size()>0) || (iwarnings!= null && iwarnings.size()>0))? "warnings" : "" ;
  String mess="&nbsp;";
  String messStyle = "color:#FFFFFF;";

  String submitParam = request.getParameter("submitparam");
  String visibilityVal = "hidden";

  String showHistory = request.getParameter("showHistory");

%>

<%
 //   {
%>

<%--<html:form action="/store/scheduledCart.do">--%>
<table cellpadding="0" cellspacing="0" align="center"  width="100%">

     <tr><td nowrap="nowrap"><b><app:storeMessage key="shoppingCart.text.scheduledCart"/></b></td></tr>
     <% itemFilter = "inventory"; %>
     <%@ include file="f_shoppingInvHeader.jsp" %>

<%--
     <jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_shoppingInvHeader.jsp")%>'>
        <jsp:param name="itemFilter" value="inventory"/>
    </jsp:include>
--%>

     <jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_inv_cart_oitems.jsp")%>'>
        <jsp:param name="jspFormName"value="INVENTORY_SHOPPING_CART_FORM"/>
        <jsp:param name="itemFilter" value="inventory"/>
        <jsp:param name="colCount" value="7"/>
        <jsp:param name="keyName" value="shop.og.table.header.title.inventory"/>
    </jsp:include>
</table>


<table   cellpadding="1" cellspacing="0" align="center"  width="<%=Constants.TABLEWIDTH800%>" >

   <tr >
    <td colspan="3" align="right">
      <table  border = "0" width="100%">
        <%-- 1 Buttons section--%>
		<%
           boolean isUsedPhysicalInventoryAlgorithm = ShopTool.isUsedPhysicalInventoryAlgorithm(request);
           boolean showUpdateButton = !ShopTool.isPhysicalInventoryCart(request);
           if(showUpdateButton){
        	if(ShopTool.isScheduledCart(request, session) ){
   				if( !(Utility.isTrue((String)appUser.getUserProperties().get(RefCodeNames.PROPERTY_TYPE_CD.CORPORATE_USER))) ) {
						showUpdateButton = false;
				}
			}
          }
		%>
		<%if(showUpdateButton) { %>
        <tr><td>
          <jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_saveOrderGuideButton.jsp")%>'>
            <jsp:param name="isDeleteVisible" value="<%=Boolean.toString(false)%>"/>
            <jsp:param name="isUpdateVisible" value="<%=Boolean.toString(true)%>"/>
            <jsp:param name="isCheckoutVisible" value="<%=Boolean.toString(false)%>"/>
            <jsp:param name="id" value=""/>
            <jsp:param name="autoUpdateSupport" value='<%=Boolean.toString(!"autoDistroPage".equals(showHistory))%>'/>
         </jsp:include>
        </td></tr>
		<% } %>

        </table>
     </td>
   </tr>
 </table>

<%--
  <html:hidden property="action" value="submitQty"/>
  <html:hidden property="action" value="BBBBBBB"/>
  <html:hidden property="submitparam" value="submitQty1"/>

--%>
<%--
</html:form>

<html:form action="/store/scheduledCart.do">
--%>
<table cellpadding="0" cellspacing="0" align="center"    width="100%">
    <tr><td nowrap="nowrap"><b><app:storeMessage key="shoppingCart.text.nonForecastedItems"/></b> </td></tr>
<%--
    <jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_shoppingInvHeader.jsp")%>'>
        <jsp:param name="itemFilter" value="regular"/>
    </jsp:include>
--%>
<%  itemFilter = "regular" ;%>
    <%@ include file="f_shoppingInvHeader.jsp" %>

    <jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_inv_cart_oitems.jsp")%>'>
        <jsp:param name="jspFormName"value="INVENTORY_SHOPPING_CART_FORM"/>
        <jsp:param name="itemFilter" value="regular"/>
        <jsp:param name="colCount" value="7"/>
        <jsp:param name="keyName" value="shop.og.table.header.title.regular"/>
    </jsp:include>
  </table>
  <table cellpadding="1" cellspacing="0" align="center"  width="<%=Constants.TABLEWIDTH800%>" >
  <tr>  <td class="tableoutline"><img src="images/spacer.gif" height="1"></td>  </tr>
  <tr>  <td>
  <%---------Total----------------%>
  <table   cellpadding="0" cellspacing="0" align="right"  >
    <tr>
      <td class="text" align="right">
        <div class="fivemargin">
          <b><app:storeMessage key="shoppingCart.text.total"/><b>
        </div>
      </td>

      <td class="text"><div class="fivemargin">
	  <%
		Double itemsAmt;

		if(!showInvCartTotal){
		   itemsAmt = new Double(theForm.getDiscretionaryCartTotalAmt());
		}else{
	       itemsAmt = new Double(theForm.getItemsAmt(request));
		}
		%>
        <%=ClwI18nUtil.getPriceShopping(request,itemsAmt)%>
      </td>
      <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>

    </tr>
  </table>
  </td></tr>
</table>
  <%-------------------------%>


  <table   cellpadding="1" cellspacing="0" align="center"  width="<%=Constants.TABLEWIDTH800%>" >

   <tr >
    <td colspan="3" align="right">
      <table  border = "0" width="100%">
        <%-- 1 Buttons section--%>
		<div><a name="buttonSection"></a></div>
        <tr><td>
          <jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_saveOrderGuideButton.jsp")%>'>
            <jsp:param name="isDeleteVisible" value="<%=Boolean.toString(true)%>"/>
            <jsp:param name="isUpdateVisible" value="<%=Boolean.toString(true)%>"/>
            <jsp:param name="isCheckoutVisible" value="<%=Boolean.toString(false)%>"/>
            <jsp:param name="id" value=""/>

          </jsp:include>

        </td></tr>

<%  visibilityVal = (submitParam != null && submitParam.equals("submitQty2")) ? "visible" : "hidden" ;%>
        <tr><td>
        <div id="submitQty2" style="visibility:<%=visibilityVal%>">
          <%@ include file="f_showCartMessages.jsp" %>
        </div>
        </td></tr>
      </table>
     </td>
   </tr>
   </table>
  <html:hidden property="action" value="submitQty"/>
 <html:hidden property="action" value="BBBBBBB"/>

  <html:hidden property="submitparam" value="submitQty2"/>

</body>
</html:form>
<%
  //  }
%>

<script type="text/javascript" language="JavaScript">
    <!--
    var ix = document.getElementById("IDX_0");
    if (ix != null) {
        ix.focus();
    }
    // -->
</script>



