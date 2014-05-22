
<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.view.utils.ShopTool" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="org.apache.struts.action.ActionErrors" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Locale" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>
<link rel="stylesheet" href="../externals/newxpedx_styles.css">

<script language="JavaScript1.2">
<!--

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
-->

</script>



<%
String storeDir=ClwCustomizer.getStoreDir();
String IPTH = (String) session.getAttribute("pages.store.images");

   ShoppingCartData shoppingCart = (ShoppingCartData) session.getAttribute(Constants.SHOPPING_CART);
   CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
   AccountData accD = appUser.getUserAccount();
   String showMyShoppingLists = accD.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SHOW_MY_SHOPPING_LISTS);
   SiteData thisSite = appUser.getSite();
%>

<bean:define id="theForm" name="SHOPPING_CART_FORM"
  type="com.cleanwise.view.forms.ShoppingCartForm"/>

<%--bread crumb --%>
<table class="breadcrumb">
<tr class="breadcrumb">
<td class="breadcrumb">
	<a class="breadcrumb" href="../userportal/msbsites.do">
	<app:storeMessage key="global.label.Home"/></a>
</td>
<td class="breadcrumb"><span class="breadcrumb">&nbsp;&gt;&nbsp;</span></td>
<td class="breadcrumb">
	<%--<a class="breadcrumb" href="#"><app:storeMessage key="shoppingCart.text.viewCart"/></a>--%>
	<div class="breadcrumbCurrent"><app:storeMessage key="shoppingCart.text.viewCart"/></div>
</td>
</tr>
<tr><td>&nbsp;</td></tr>
</table>
<%--End of Shopping Cart Header--%>

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
String shoppingCartName = (String) request.getParameter("shoppingCartName");

if (shoppingCartName == null) {
    shoppingCartName = Constants.SHOPPING_CART;
}
ShoppingCartData v_shoppingCart = (ShoppingCartData) session.getAttribute(shoppingCartName);
if (v_shoppingCart != null) {
  warnings = v_shoppingCart.getWarningMessages();
  iwarnings = v_shoppingCart.getItemMessages();
}
%>

<%
OrderData prevOrder = shoppingCart.getPrevOrderData();
if ( prevOrder != null ) {
%>

<table cellpadding="1" cellspacing="0" align="center"
  class="tbstd"  width="<%=Constants.TABLEWIDTH%>" >
  <tr><td rowspan=4><app:storeMessage key="shoppingCart.text.thisOrderWasCreatedFrom"/> </td></tr>
  <tr>
    <td><app:storeMessage key="shoppingCart.text.orderNumber"/></td>
    <td> <%= prevOrder.getOrderNum() %></td>
  </tr>
  <tr>
    <td><app:storeMessage key="shoppingCart.text.ordedBy"/></td>
    <td> <%= prevOrder.getUserFirstName() %> <%= prevOrder.getUserLastName() %> ( <%= prevOrder.getAddBy() %> )</td>
  </tr>
  <tr>
    <td><app:storeMessage key="shoppingCart.text.originalOrderDate"/></td>
    <td><i18n:formatDate value="<%=prevOrder.getOriginalOrderDate()%>"
          pattern="yyyy-M-d"  locale="<%=ClwI18nUtil.getUserLocale(request)%>"/></td>
  </tr>
</table>

<% } %>



<%--@ include file="f_shopping_msgs.jsp" --%>

<%-- if(theForm.getOrderGuideId()>0) { %>

<table cellpadding="1" cellspacing="0" align="center"  width="<%=Constants.TABLEWIDTH800%>" >
  <td class="genericerror" align="center">
    <b><app:storeMessage key="shoppingCart.text.orderGuideCreated" arg0="<%=theForm.getUserOrderGuideName()%>"/>
  </td>
</table>

<% }--%>
<%
  theForm.setOrderGuideId(0);
  theForm.setUserOrderGuideName("");
%>

<!-- No items in the shopping cart -->
<logic:equal name="SHOPPING_CART_FORM" property="cartItemsSize" value="0">
  <table cellpadding="1" cellspacing="0" align="center"   width="<%=Constants.TABLEWIDTH800%>" >
    <tr>
      <td class="text" align="center" style="color:#003399;">
        <br><b><app:storeMessage key="shop.checkout.text.cartIsEmpty" /></b><br>&nbsp;
      </td>
    </tr>
  </table>
</logic:equal>


<!-- There are items in the shopping cart -->
<logic:greaterThan name="SHOPPING_CART_FORM" property="cartItemsSize" value="0">

<%-- Order By Field--   REMOVED--%>
<%--
<table cellpadding="1" cellspacing="0" align="center"   width="<%=Constants.TABLEWIDTH800%>" >
   <tr>
      <td>
        <%@ include file="f_scart_buttons.jsp" %>
      </td>

      <jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_saveOrderGuideButton.jsp")%>'/>

    </tr>

</table>
--%>

<html:form action="/store/shoppingcart.do">
<%
//String isGotoAnchor = (String)request.getAttribute("gotoAnchor");
if("true".equals(isGotoAnchor)){ %>
	<body onload="pageScroll()">
<%}else{%>
<body>
<%}%>
<table cellpadding="1" cellspacing="0" align="center"   width="<%=Constants.TABLEWIDTH800%>" >


  <tr><td>

    <jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_shoppingItems.jsp")%>'>
    <jsp:param name="allowEdits" value="true"/>
    <jsp:param name="orderBy" value="<%=theForm.getOrderBy()%>"/>
    </jsp:include>

  </td></tr>
</table>


<table cellpadding="1" cellspacing="0" align="center"  width="<%=Constants.TABLEWIDTH800%>" >
  <tr>  <td class="tableoutline"><img src="images/spacer.gif" height="1"></td>  </tr>
  <tr>  <td>

  <table   cellpadding="0" cellspacing="0" align="right" width="<%=Constants.TABLEWIDTH800%>" >
    <tr>
	
      <td class="text" align="right" width="86%">
        <div class="fivemargin">
          <b><app:storeMessage key="shoppingCart.text.cartTotal"/><b>
       </div>
      </td>

      <td class="text" align="left" ><div class="fivemargin">
        <%Double itemsAmt = new Double(theForm.getItemsAmt(request)); %>
        <%=ClwI18nUtil.getPriceShopping(request,itemsAmt)%>&nbsp;
        <%--  </div> NG  --%>
      </td>
	  <td width="7%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
      
    </tr>
  </table>
  </td></tr>
</table>

<table   cellpadding="1" cellspacing="0" align="center"  width="<%=Constants.TABLEWIDTH800%>" >
  <tr >
    <td colspan="3" align="right">
      <table  border = "0" width="100%">
        <%-- 1 Buttons section--%>
		<a name="buttonSection"></a>
        <tr><td>
          <jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_saveOrderGuideButton.jsp")%>'/>
        </td></tr>

        <%-- 2 --%>
        <tr><td>
        <!-- add shopping lists section -->
		<%if (showMyShoppingLists.equals("true")) { %>
         <%-- <table ID=164 align = "right" border="0" cellpadding="0" cellspacing="0"  >
            <tr>
              <td align="right"> --%>
			  
                <jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_shoppingListCombo.jsp")%>'>
                    <jsp:param name="confirmMessage" value='<%=(theForm.getConfirmMessage()==null)? "" : theForm.getConfirmMessage()%>'/>
                    <jsp:param name="errorMessage" value='<%=(errors==null )? "" : "errors"%>'/>
                    <jsp:param name="warningMessage" value='<%=((warnings!=null && warnings.size()>0) || (iwarnings!= null && iwarnings.size()>0))? "warnings" : "" %>'/>
                </jsp:include>
         <%--     </td>
            </tr>
          </table> --%>
		  
			<%}else if(ShopTool.requestContainsErrors(request)) {%>
				<table cellpadding="1" cellspacing="0" align="center"  width="<%=Constants.TABLEWIDTH800%>" >
				<tr>
					<td style="color:#FF0000;" align="center"><html:errors/></td>
				</tr>
				</table>
	
		  <%}else{%>
		  <jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_shopping_msgs.jsp")%>'>
			<jsp:param name="shoppingCartName" value='<%=shoppingCartName%>'/>
			<jsp:param name="confirmMessage" value='<%=(theForm.getConfirmMessage()==null)? "" : theForm.getConfirmMessage()%>'/>
		  </jsp:include>
		 <%}%>
        </td></tr>

      </table>
   </td>

  </tr>

</table>
  <html:hidden property="action" value="submitQty"/>
  <html:hidden property="action" value="BBBBBBB"/>
  </body>
  </html:form>

</logic:greaterThan>



<%--@ include file="f_table_bottom.jsp" --%>

<%-- @ include file="f_shop_history_items_removed.jsp"

<jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_shop_history_items_removed.jsp")%>'>
  <jsp:param name="shoppingCartName" value="<%=Constants.SHOPPING_CART%>"/>
</jsp:include>
--%>

