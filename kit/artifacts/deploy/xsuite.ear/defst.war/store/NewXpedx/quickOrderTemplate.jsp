<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page import="com.cleanwise.view.forms.QuickOrderForm" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ page import="com.cleanwise.service.api.session.*" %>
<%@ page import="com.cleanwise.service.api.value.*"%>
<%@ page import="com.cleanwise.service.api.util.*"%>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.net.URLEncoder" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<style type="text/css">

.warninglineQuickOrder {
    BACKGROUND-COLOR: #ECECEC;
    color: #FF0000;
    font-size: 11px;
    border-top: solid 0px #003366;
}

</style>

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
	} else if(actionElements){
		actionElements.value = action;
		actionElements.form.submit();
	}
	return false;
}

--></script>

<%
	ShoppingCartData shoppingCart=null;
	if(ShopTool.isModernInventoryCartAvailable(request) && !(ShopTool.hasDiscretionaryCartAccessOpen(request))){
		shoppingCart = (ShoppingCartData) session.getAttribute(Constants.INVENTORY_SHOPPING_CART);
	}else{
		shoppingCart = (ShoppingCartData) session.getAttribute(Constants.SHOPPING_CART);
	}
    SiteData site = null;
	/*
    boolean viewAddInvCart = false;
    if (shoppingCart != null) {
        site = shoppingCart.getSite();
        if (site != null) {
            viewAddInvCart
                    = site.hasModernInventoryShopping() && site.hasInventoryShoppingOn();
        }
    }*/
    CleanwiseUser appUser = ShopTool.getCurrentUser(request);
	String jspFormName = request.getParameter("jspFormName");
%>

<table width="100%">

 <html:form name="<%=jspFormName%>" action="/store/quickOrder.do?">
 <bean:define id="thisForm" name="<%=jspFormName%>"  type="com.cleanwise.view.forms.QuickOrderForm"/>
<tr><td colspan="2" class="xpdexMenuHeader"><app:storeMessage key="template.xpedx.homepage.header.expressOrder"/></td></tr>
<tr><td><img src="<%=ClwCustomizer.getSIP(request,"spacer.gif")%>" border="0" height="4"></td></tr>

<tr>
  <td align="center"><app:storeMessage key="shoppingItems.text.QTY"/></td>
  <td align="center"><app:storeMessage key="shoppingItems.text.item#"/></td>
</tr>

<%
    int incPageSize = thisForm.getIncPageSize();
    int pageSize = 5 + incPageSize;

     for(int kidx=0; kidx<pageSize; kidx++) {
       String itemSkusEl = "itemSkusElement["+kidx+"]";
       String itemQtysEl = "itemQtysElement["+kidx+"]";
  %>
     <tr>

     <td align="center" class="text"><div class="fivemargin">
        <html:text name="QUICK_ORDER_FORM" property="<%=itemQtysEl%>" size="4"/>
     </div></td>

	 <td align="right" class="text"><div class="fivemargin">
        <html:text name="QUICK_ORDER_FORM" property="<%=itemSkusEl%>" size="12"/>
     </div></td>
     </tr>
  <% } %>

     <script type="text/javascript">
         function increasePageSize() {
             document.QUICK_ORDER_FORM.incPageSize.value =  <%=(incPageSize + 5)%>;
         }
     </script>
  <tr>
      <td colspan="2" align="right" class="notestxt">
         <a href="#" onclick="increasePageSize(); actionMultiSubmit('BBBBBBB', 'addMoreFields');" class="notestxt"><app:storeMessage key="shoppingCart.text.addMoreFields"/></a>
        <html:hidden property="incPageSize"/>
      </td>
  </tr>

<%
String messCd = "";
String mess="&nbsp;";
String messStyle = "color:#FFFFFF;";

Object errors =request.getAttribute("org.apache.struts.action.ERROR");
String confirmation = thisForm.getConfirmMessage();
String errorMess = request.getParameter("errorMessage");
java.util.List warnings = thisForm.getWarningMessage();

if (errorMess!=null || errors!=null) {
	messCd = "errors";
  mess = errorMess;
  messStyle = "color:#FF0000; white-space: normal; ";
} else if ((errors==null) && (warnings!=null) && (warnings.size()>0)){
	 messCd = "warnings";
 // mess = warnings;
  messStyle = "color:#FF0000; white-space: normal;";
} else if ((errors==null) && (confirmation!=null) && (confirmation.trim().length() > 0)){
	messCd = "confirmation";
  mess= confirmation;
  messStyle = "color:#003399; ";
}

%>


    <tr>
		<td colspan="2" align="center" >
		<table border="0" cellpadding="0" cellspacing="0" >
		<tr><td style="<%=messStyle%>">
		<%if (messCd.equals("errors")) {%>
			<html:errors/>

        <%} else if (messCd.equals("warnings")) {

			ShoppingCartData v_shoppingCart = shoppingCart;

			java.util.List iwarnings = v_shoppingCart.getItemMessages();
			v_shoppingCart.setItemMessages(null);
			v_shoppingCart.setWarningMessages(null);

			if ( null != iwarnings && iwarnings.size() > 0 ) {

				for (int widx = 0; widx < iwarnings.size(); widx++) {
					ShoppingCartData.CartItemInfo cii =
						(ShoppingCartData.CartItemInfo) iwarnings.get(widx);

					ArrayList al = cii.getI18nItemMessageAL();
					String key = (String) al.get(0);

					Object[]  params = null;
					if(al.size()>1) {
						params = new Object[al.size()-1];
						for(int ii=0; ii<al.size()-1; ii++) {
							params[ii] = (Object)al.get(ii+1);
						}
					}

					String message = ClwI18nUtil.getMessage(request,key,params);
					%>
					<tr>
					<td class="warninglineQuickOrder"><%=message%></td>
					</tr>
					<%
				}
			}
		} else {%>
			<b><%=mess%></b>

		<%}%>
		</td></tr>
		</table>
		</td>
    </tr>



  <tr><td colspan="2" align=center>
	<table border="0" cellpadding="0" cellspacing="0" >

        <tr>

		<%-- add to cart --%>
		<td align="right" valign="middle"><img src='<%=ClwCustomizer.getSIP(request,"buttonLeft.png")%>' border="0"></td>
     <td align="center" valign="middle" nowrap class="xpdexGradientButton">

		<% if(ShopTool.isModernInventoryCartAvailable(request) && !(ShopTool.hasDiscretionaryCartAccessOpen(request))){ %>
           <a class="xpdexGradientButton" href="#" onclick="actionMultiSubmit('BBBBBBB', 'addToNewXpedxInvCart');"><app:storeMessage key="shoppingCart.text.addToCart"/></a>
		<% } else { %>
			<a class="xpdexGradientButton" href="#" onclick="actionMultiSubmit('BBBBBBB', 'addToNewXpedxCart');"><app:storeMessage key="shoppingCart.text.addToCart"/></a>
		<% } %>

     </td>
     <td align="left" valign="middle"><img src='<%=ClwCustomizer.getSIP(request,"buttonRight.png")%>' border="0"></td>
     <td>&nbsp;</td>

		<%-- checkout --%>

		<td align="right" valign="middle"><img src='<%=ClwCustomizer.getSIP(request,"buttonLeft.png")%>' border="0"></td>
         <td align="center" valign="middle" nowrap class="xpdexGradientButton">
                <a class="xpdexGradientButton" href="#" onclick="actionMultiSubmit('BBBBBBB', 'addToCartCheckout');"><app:storeMessage key="global.action.label.checkout"/></a>
         </td>
         <td align="left" valign="middle"><img src='<%=ClwCustomizer.getSIP(request,"buttonRight.png")%>' border="0"></td>

        </tr>
	</table>

	</td></tr>

<html:hidden property="action" value="BBBBBBB"/>
<html:hidden property="action" value="CCCCCCC"/>


</html:form>
</table>


