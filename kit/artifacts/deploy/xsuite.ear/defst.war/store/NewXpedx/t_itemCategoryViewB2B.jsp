<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.value.CatalogCategoryData" %>
<%@ page import="com.cleanwise.service.api.value.ProductData" %>
<%@ page import="com.cleanwise.service.api.value.ShoppingCartItemData" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@ page import="com.cleanwise.view.taglibs.ClwPagerIndexTag" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.Pager" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="java.util.Date" %>
<%@ page import="com.cleanwise.service.api.value.OrderGuideData" %>
<%@ page import="com.cleanwise.view.forms.UserShopForm" %>
<%@ page import="com.cleanwise.view.utils.ShopTool" %>
<%@ page import="com.cleanwise.service.api.*" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.session.*" %>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<!--PAGESRCID=t_itemCategoryViewB2B.jsp -->
<script language="JavaScript1.2"> <!--
 function actionMultiSubmit(actionDef, action) {

  var actionElements = document.getElementsByName('action');
  if(actionElements.length){
  for ( var i = actionElements.length-1; i >=0; i-- ) {

    var element = actionElements[i];
    if(actionDef == element.value){
    element.value = action;
    element.form.submit();
    break;
     }
  }
   }  else if(actionElements){
   actionElements.value = action;
     actionElements.form.submit();
   }

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
--></script>

<script type="text/javascript">
// <![CDATA[
function changeOrderGuideOp(obj,id1) {

  txt = obj.options[obj.selectedIndex].value;
  f_hideBox("ICV_B2B_DETAIL_BOX_messageText");
  if ( txt.match('-2') ) {
    f_showBox("ICV_B2B_DETAIL_BOX_createNewList");
    f_hideBox("ICV_B2B_DETAIL_BOX_saveToList");
  } else if ( txt.match('-1') ) {
    f_hideBox("ICV_B2B_DETAIL_BOX_createNewList");
    f_hideBox("ICV_B2B_DETAIL_BOX_saveToList");
  } else {
    f_hideBox("ICV_B2B_DETAIL_BOX_createNewList");
    f_showBox("ICV_B2B_DETAIL_BOX_saveToList");
  }

}

function f_hideBox(boxid) {
//  document.getElementById(boxid).style.display = 'none';
  if (document.getElementById(boxid) != null){
    document.getElementById(boxid).style.display = 'none';
  }
}

function f_hideAll() {
  f_hideBox("ICV_B2B_DETAIL_BOX_selectList");
  f_hideBox("ICV_B2B_DETAIL_BOX_createNewList");
  f_hideBox("ICV_B2B_DETAIL_BOX_messageText");
}

function f_showBox(boxid) {
  if (document.getElementById(boxid) != null){
    document.getElementById(boxid).style.display = 'block';
    f_hideBox("ICV_B2B_DETAIL_BOX_messageText");
  }
}


// ]]>


</script>
<logic:present name="SHOP_FORM">
<html:form name="SHOP_FORM" action="/store/shop.do">
<html:hidden name="SHOP_FORM" property="groupedItemView" value="false"/>
<%
//String isGotoAnchor = (String)request.getAttribute("gotoAnchor");
if("true".equals(isGotoAnchor)){ %>
	<body onload="pageScroll()">
<%}else{%>
<body>
<%}%>
<bean:define id="theForm" name="SHOP_FORM" type="com.cleanwise.view.forms.UserShopForm"/>
<table cellpadding="5" cellspacing="0" align="center" width="100%">


<tr>


 <app:displayProdHeader sortLink="shop.do?action=newSort&sortField=" viewOptionEditCartItems="false" viewOptionQuickOrderView="false" viewOptionOrderGuide="false" viewOptionAddToCartList="true" viewOptionOrderGuide="false"/>

</tr>

<%
    int rowIdx = 0;
    String storeDir = ClwCustomizer.getStoreDir();
	
	UserShopForm shopForm = (UserShopForm) session.getAttribute("SHOP_FORM");
	List allItems = shopForm.getCartItems();
	int listSize = allItems.size();

%>


<logic:iterate id="item" name="SHOP_FORM" property="cartItems"
               offset="0" indexId="kidx"
               type="com.cleanwise.service.api.value.ShoppingCartItemData">

    <%
        ProductData product = item.getProduct();
        Date curDate = Constants.getCurrentDate();
        Date effDate = product.getEffDate();
        Date expDate = product.getExpDate();

    %>

    <bean:define id="itemId" name="item" property="product.productId" type="java.lang.Integer"/>
    <bean:define id="quantityEl" value="<%=\"quantityElement[\"+kidx+\"]\"%>"/>
    <bean:define id="onhandEl" value="<%=\"onhandElement[\"+kidx+\"]\"%>"/>
    <bean:define id="itemIdsEl" value="<%=\"itemIdsElement[\"+kidx+\"]\"%>"/>
    <%
        String itemLink = "shop.do?action=" + (!product.isItemGroup() ? "msItem&source=t_itemDetail.jsp" : "itemgroup&source=t_itemGroupDetail.jsp") + "&itemId=" + product.getProductId() + "&qty=" + item.getQuantity();
    
		%>
<%String styleClass = (((kidx) %2 )==0) ?  "evenRowColor" : "oddRowColor";%>
<tr >
	<td colspan="4"><img src="images/spacer.gif" height="4"></td>
</tr>
<tr class="<%=styleClass%>">
	
    <logic:equal name="SHOP_FORM" property="allowPurchase" value="true">
       <%if (!product.isItemGroup()) {%>
        <html:hidden name="SHOP_FORM" property='<%= itemIdsEl %>' value="<%=String.valueOf(product.getProductId())%>"/>
       <%}%>
	</logic:equal>

	<app:displayProd viewOptionEditCartItems="false" viewOptionQuickOrderView="false" 
	viewOptionAddToCartList="true" viewOptionOrderGuide="false" 
	altThumbImage="/<%=storeDir%>/en/images/noManXpedxImg.gif"
	name="item" link="<%=itemLink%>" index="<%=kidx%>" 
	inputNameOnHand="<%=onhandEl%>" inputNameQuantity="<%=quantityEl%>"/>
	<%
	 if(kidx == (listSize-7)){ %>
	  <td><a name="buttonSection"></a></td>
	<%}%>
</tr>
    <% rowIdx++; %>
		
</logic:iterate>
<table width="100%">
<tr>  <td class="tableoutline"><img src="images/spacer.gif" height="1"></td>  </tr>
</table>
<tr>
    <td align="center" colspan="7">
         <logic:equal name="SHOP_FORM" property="allowPurchase" value="true">
    <table align = "center" border="0" cellpadding="0" cellspacing="0" >
    <tr>
    <td>
    <table border="0" cellpadding="0" cellspacing="0" >
    <tr> <td align="center">
        <table border="0" cellpadding="0" cellspacing="0" >
		<tr >
			<td colspan="4"><img src="images/spacer.gif" height="6"></td>
		</tr>
            <tr>
			<%--<a name="buttonSection"></a>--%>
                    <%----- clearQuantities --%>
                <td align="right" valign="middle"><img src='<%=ClwCustomizer.getSIP(request,"buttonLeft.png")%>' border="0"></td>
                <td align="center" valign="middle" nowrap class="xpdexGradientButton">
                    <a class="xpdexGradientButton" href="#" onclick="actionMultiSubmit('hiddenAction', 'clear');">
                        <app:storeMessage key="global.label.clearQuantities"/>
                    </a>
                </td>
                <td align="left" valign="middle"><img src='<%=ClwCustomizer.getSIP(request,"buttonRight.png")%>' border="0"></td>
                <td>&nbsp;</td>
                    <%----- addToCart --%>
                <% if (!ShopTool.isPhysicalCartAvailable(request)) { %>
                <td align="right" valign="middle"><img src='<%=ClwCustomizer.getSIP(request,"buttonLeft.png")%>' border="0"></td>
                <td align="center" valign="middle" nowrap class="xpdexGradientButton">
                    <a class="xpdexGradientButton" href="#" onclick="actionMultiSubmit('hiddenAction', 'addToActiveXpedxCart');">
                            <app:storeMessage key="global.label.addToCart"/>
                </td>
                <td align="left" valign="middle"><img src='<%=ClwCustomizer.getSIP(request,"buttonRight.png")%>' border="0"></td>
                <td>&nbsp;</td>
                <% } %>
                    <%----- checkout --%>
                <td align="right" valign="middle"><img src='<%=ClwCustomizer.getSIP(request,"buttonLeft.png")%>' border="0"></td>
                <td align="center" valign="middle" nowrap class="xpdexGradientButton">
                    <%--<a class="xpdexGradientButton" href="../store/checkout.do">
                        <app:storeMessage key="global.action.label.checkout"/>
                    </a>--%>
					<a class="xpdexGradientButton" href="#" onclick="actionMultiSubmit('hiddenAction', 'addToCartViewcart');">
                            <app:storeMessage key="global.action.label.checkout"/>
                </td>
                <td align="left" valign="middle"><img src='<%=ClwCustomizer.getSIP(request,"buttonRight.png")%>' border="0"></td>

            </tr>    </table>
    </td>
    </tr>
	<tr >
	<td colspan="4"><img src="images/spacer.gif" height="6"></td>
	</tr>
    <tr>
        <td align="center">
<%
CleanwiseUser appUser = (CleanwiseUser) session.getAttribute("ApplicationUser");
AccountData accD = appUser.getUserAccount();
	String showMyShoppingLists = accD.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SHOW_MY_SHOPPING_LISTS);
	
	if (showMyShoppingLists.equals("true")) { %>
            <table border="0" cellpadding="0" cellspacing="0">
                <tr>

                    <td align="right" valign="middle"><img src='<%=ClwCustomizer.getSIP(request,"buttonLeft.png")%>' border="0"></td>
                    <td align="center" valign="middle" nowrap class="xpdexGradientButton">
                        <a class="xpdexGradientButton" href="#buttonSection" onclick="f_showBox('ICV_B2B_DETAIL_BOX_selectList')">
                            <app:storeMessage key="shoppingCart.text.addCart"/>
                        </a>
                    </td>
                    <td align="left" valign="middle"><img src='<%=ClwCustomizer.getSIP(request,"buttonRight.png")%>' border="0"></td>

                </tr>
            </table>
	<% } %>
        </td>
    </tr>

          <%--Confirmation/Errors/Warnings message Area--%>
    <%
        String messCd = "";
        String messStyle = "color:#FFFFFF;";
        String mess = "&nbsp;";
        if (ShopTool.requestContainsErrors(request)) {
            messCd = "errors";
            messStyle = "color:#FF0000; white-space: normal; ";
        } else if ((ShopTool.cartContainsWarnings(request))) {
            messCd = "warnings";
            messStyle = "color:#FF0000; white-space: normal;";
        } else if (Utility.isSet(((UserShopForm) theForm).getConfirmMessage())) {
            messCd = "confirmation";
            messStyle = "color:#003399; ";
            mess = ((UserShopForm) theForm).getConfirmMessage();
        }

    %>
    <tr>
        <td align="center">
            <div id="ICV_B2B_DETAIL_BOX_messageText" style="visibility:visible">
                <table  border="0" cellpadding="0" cellspacing="0">
                    <tr>
                        <td align="center" style="<%=messStyle%>">
                            <%if (messCd.equals("errors")) {%>
                            <html:errors/>
                            <%} else if (messCd.equals("warnings")) {%>
                            <jsp:include flush='true'
                                         page='<%=ClwCustomizer.getStoreFilePath(request,"f_shopping_msgs.jsp")%>'/>
                            <%} else {%>
                            <b><%=mess%></b>
                            <%} %>
                        </td>
                        <%--<td style="padding-right:40px">&nbsp;</td>--%>
                    </tr>
                </table>
            </div>
        </td>
    </tr>
        <%-------------------------------------%>
		<%
			OrderGuideDataVector userList = null;
			//CleanwiseUser appUser = (CleanwiseUser) session.getAttribute("ApplicationUser");
			APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
			ShoppingServices shoppingServBeen = factory.getShoppingServicesAPI();

			int userId = appUser.getUserId();
			int siteId = appUser.getSite().getBusEntity().getBusEntityId();
			int catalogId = 0;
			Integer catalogIdI = (Integer) session.getAttribute(Constants.CATALOG_ID);
			if (catalogIdI != null) {
			    catalogId = catalogIdI.intValue();
			}

			userList = shoppingServBeen.getUserOrderGuides( userId,catalogId,siteId);

			ArrayList userIds = new ArrayList();
			ArrayList userNames = new ArrayList();
			for(int ii=0; ii<userList.size(); ii++) {
			    OrderGuideData ogD = (OrderGuideData) userList.get(ii);
			    userIds.add(""+ogD.getOrderGuideId());
			    userNames.add(ogD.getShortDesc());
			}
		%>
    <tr>
        <td align="center">
            <div id="ICV_B2B_DETAIL_BOX_selectList" style="display:none">

                <table width="100%" border="0"  align="center" cellpadding="0" cellspacing="0">
                    <tr align="center">
                        <td>
                            <table>
                                <tr>
                                    <td>
                                        <!-- Order guide select section -->

                                        <html:select name="SHOP_FORM" property="selectedShoppingListId"
                                                     onchange="changeOrderGuideOp(this,'-2')">
                                            <html:option value="-1">
                                                <app:storeMessage key="shoppingCart.text.selectList"/>
                                            </html:option>
                                            <html:option value="-2">
                                                <app:storeMessage key="shoppingCart.text.createNewList"/>
                                            </html:option>
											<% if(userIds.size()>0 ) {%>
												<% for(int ii=0; ii<userIds.size(); ii++) { %>
													<html:option value="<%=(String)userIds.get(ii)%>"><%=(String)userNames.get(ii)%></html:option>
												<% } %>
											<% } %>
                                        </html:select>
                                    </td>
                                    <td>
                                        <div id="ICV_B2B_DETAIL_BOX_saveToList" style="display:none">
                                            <table  border="0" cellpadding="0"
                                                    cellspacing="0">
                                                <tr>
                                                    <td align="right" valign="middle"><img
                                                            src='<%=ClwCustomizer.getSIP(request,"buttonLeft.png")%>'
                                                            border="0"></td>
                                                    <td align="center" valign="middle" nowrap
                                                        class="xpdexGradientButton">
                                                        <a class="xpdexGradientButton" href="#"
                                                           onclick="actionMultiSubmit('hiddenAction', 'updateUserOrderGuide');">
                                                            <app:storeMessage key="global.action.label.save"/>
                                                        </a>
                                                    </td>
                                                    <td align="left" valign="middle"><img
                                                            src='<%=ClwCustomizer.getSIP(request,"buttonRight.png")%>'
                                                            border="0"></td>
                                                </tr>
                                            </table>
                                        </div>
                                    </td>
                                </tr>
                            </table>

                        </td>

                    </tr>
                    <tr>
                        <td>
                            <div id="ICV_B2B_DETAIL_BOX_createNewList" style="display:none">
                                <table align="center" border="0" cellpadding="0" cellspacing="0">
                                    <tr>
                                        <td style="padding-left:40px">

                                            <html:text name="SHOP_FORM" property="orderGuideName" maxlength="30"/>
                                        </td>
                                        <td>&nbsp;</td>
                                        <td>
                                            <table align="right" border="0" cellpadding="0" cellspacing="0">
                                                <tr>
                                                    <td align="right" valign="middle"><img
                                                            src='<%=ClwCustomizer.getSIP(request,"buttonLeft.png")%>'
                                                            border="0"></td>
                                                    <td align="center" valign="middle" nowrap class="xpdexGradientButton">
                                                        <a class="xpdexGradientButton" href="#"
                                                           onclick="actionMultiSubmit('hiddenAction', 'saveUserOrderGuide');">
                                                            <app:storeMessage key="global.action.label.save"/>
                                                        </a>
                                                    </td>
                                                    <td align="left" valign="middle"><img
                                                            src='<%=ClwCustomizer.getSIP(request,"buttonRight.png")%>'
                                                            border="0"></td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                        </td>
                    </tr>
                </table>
            </div>

        </td>
    </tr>
    </table>
    </td>
    </tr>
    </table>
    </logic:equal>
    </td>
</tr>

</table>
<html:hidden property="action" value="hiddenAction"/>
<html:hidden property="source" value="t_itemCategoryViewB2B.jsp"/>
</body>
</html:form>
</logic:present>
