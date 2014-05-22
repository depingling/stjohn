<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>

<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<%-- String IPTH = (String) session.getAttribute("pages.store.images"); --%>
<bean:define id="theForm" name="SHOP_FORM" type="com.cleanwise.view.forms.UserShopForm"/>


<style type="text/css">

.shoppingCartGreyBorder {
        border-left: 1px solid #dedede;
        border-collapse: collapse;
}
</style>

<%
  String showDelete   =  request.getParameter("isDeleteVisible");
  String showUpdate   =  request.getParameter("isUpdateVisible");
  String showCheckout =  request.getParameter("isCheckoutVisible");
  String showSubmitCart =  request.getParameter("isSubmitVisible");
  String id = (request.getParameter("id")== null) ? "" : request.getParameter("id") ;
  boolean includeAutoUpdateSupport = Utility.isTrue(request.getParameter("autoUpdateSupport"));

%>


     <table align = "right" border="0" cellpadding="0" cellspacing="0" >
     <tr>
	 <%--<a name="buttonSection"></a>--%>
<% if ( (showDelete== null) ||(showDelete!= null && (new Boolean(showDelete)).booleanValue())) { %>
   <% //if(appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.APPROVE_ORDERS_ACCESS)) { %>
       <td align="right" valign="middle"><img src='<%=ClwCustomizer.getSIP(request,"buttonLeft.png")%>' border="0"></td>
       <td align="center" valign="middle" nowrap class="xpdexGradientButton">
       <%if (ShopTool.isPhysicalCartAvailable(request)) {%>
           <a class="xpdexGradientButton" href="#" onclick="actionMultiSubmit('submitQty<%=id%>','removePhysSelectedNewxpedx');"><app:storeMessage key="shoppingCart.text.deleteSelected"/></a>
       <%} else {%>
	   <% if(ShopTool.isModernInventoryCartAvailable(request) && !(ShopTool.hasDiscretionaryCartAccessOpen(request))){ %>
               <a class="xpdexGradientButton" href="#" onclick="actionMultiSubmit('submitQty<%=id%>','removeInvSelectedNewxpedx');"><app:storeMessage key="shoppingCart.text.deleteSelected"/></a>
           <%}else{%>
	       <a class="xpdexGradientButton" href="#" onclick="actionMultiSubmit('submitQty<%=id%>','removeSelected');"><app:storeMessage key="shoppingCart.text.deleteSelected"/></a>
	   <%}%>
       <%}%>
       </td>
       <td align="left" valign="middle"><img src='<%=ClwCustomizer.getSIP(request,"buttonRight.png")%>' border="0"></td>
       <td>&nbsp;</td>
     <%// } %>
<% } %>
<% if ( (showUpdate== null) ||( (showUpdate!= null )&& (new Boolean(showUpdate)).booleanValue())) { %>

   <%// if(appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.CHANGE_LOCATION_ACCESS)) { %>
     <td align="right" valign="middle"><img src='<%=ClwCustomizer.getSIP(request,"buttonLeft.png")%>' border="0"></td>
     <td align="center" valign="middle" nowrap class="xpdexGradientButton">
         <% if(includeAutoUpdateSupport) { %>
               <%if (ShopTool.isPhysicalCartAvailable(request)) {%>
					<a class="xpdexGradientButton" href="#" onclick="if(xhrPost){ hrPost=true; return;} else{ hrPost=true; actionMultiSubmit('submitQty<%=id%>','updateCartPhysical');}"><app:storeMessage key="shoppingCart.text.updateCart"/></a>
			   <%}else{ %>
         			<a class="xpdexGradientButton" href="#" onclick="if(xhrPost){ hrPost=true; return;} else{ hrPost=true; actionMultiSubmit('submitQty<%=id%>','updateCart');}"><app:storeMessage key="shoppingCart.text.updateCart"/></a>
               <%} %>
         <% } else { %>
         <a class="xpdexGradientButton" href="#" onclick="actionMultiSubmit('submitQty<%=id%>','updateCart')"><app:storeMessage key="shoppingCart.text.updateCart"/></a>
         <%}%>
     </td>
     <td align="left" valign="middle"><img src='<%=ClwCustomizer.getSIP(request,"buttonRight.png")%>' border="0"></td>
     <td>&nbsp;</td>
  <%// } %>
<% } %>
<% if ( (showCheckout== null) ||( (showCheckout!= null) && (new Boolean(showCheckout)).booleanValue())) { %>
   <%// if(appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.CHANGE_LOCATION_ACCESS)) { %>
         <td align="right" valign="middle"><img src='<%=ClwCustomizer.getSIP(request,"buttonLeft.png")%>' border="0"></td>
         <td align="center" valign="middle" nowrap class="xpdexGradientButton">
               <a class="xpdexGradientButton" href="../store/checkout.do"><app:storeMessage key="global.action.label.checkout"/></a>
         </td>
         <td align="left" valign="middle"><img src='<%=ClwCustomizer.getSIP(request,"buttonRight.png")%>' border="0"></td>
  <%// } %>
<% } %>
<% if ( ( (showSubmitCart!= null) && (new Boolean(showSubmitCart)).booleanValue())) { %>
   <%// if(appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.CHANGE_LOCATION_ACCESS)) { %>
     <td align="right" valign="middle"><img src='<%=ClwCustomizer.getSIP(request,"buttonLeft.png")%>' border="0"></td>
     <td align="center" valign="middle" nowrap class="xpdexGradientButton">
           <a href="#" class="xpdexGradientButton" onclick="setAndSubmit('CHECKOUT_FORM','command','placeOrderForAll');"><app:storeMessage key="shoppingCart.text.submitCart"/></a>
     </td>
     <td align="left" valign="middle"><img src='<%=ClwCustomizer.getSIP(request,"buttonRight.png")%>' border="0"></td>
     
  <%// } %>
<% } %>



     </tr>
    </table>

