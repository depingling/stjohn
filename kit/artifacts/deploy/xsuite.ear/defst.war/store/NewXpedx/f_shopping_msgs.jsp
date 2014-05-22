<%@ page import="com.cleanwise.service.api.value.ShoppingCartData" %>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@ page import="java.util.ArrayList" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<style type="text/css">

.warningline {
    BACKGROUND-COLOR: #FFFFFF;
    color: #FF0000;
    font-size: 11px;
    border-top: solid 0px #003366;
}


</style>
<table cellpadding="0" cellspacing="0" border="0" align="center" >

<%
    String shoppingCartName = (String) request.getParameter("shoppingCartName");
	String confirmation = request.getParameter("confirmMessage");
	
	Object errors =request.getAttribute("org.apache.struts.action.ERROR");
    if (shoppingCartName == null) {
        shoppingCartName = Constants.SHOPPING_CART;
    }
    ShoppingCartData v_shoppingCart = (ShoppingCartData) session.getAttribute(shoppingCartName);
    if (v_shoppingCart != null) {

        java.util.List warnings = v_shoppingCart.getWarningMessages();
        v_shoppingCart.setWarningMessages(null);
        for (int widx = 0; widx < warnings.size(); widx++) {
            String warningMsg = (String) warnings.get(widx);
%>

<% if (warningMsg != null && warningMsg.length()>0 && errors==null) {%>
  <tr>
    <td class="warningline"><%= warningMsg %></td>
  </tr>
<%}%>
<% } /* end of loop on warnings */ %>


<%
java.util.List iwarnings = v_shoppingCart.getItemMessages();
if ( null != iwarnings && iwarnings.size() > 0 ) {
%>

<%--
<tr>
<td class="warningline" colspan="3">
<b><app:storeMessage key="shoppingMessages.text.shoppingRestrictions"/></b></td>
</tr>

<tr>
<td><app:storeMessage key="shoppingMessages.text.ourSkuNum"/></td>
<td><app:storeMessage key="shoppingMessages.text.productName"/></td>
<td> </td>
</tr>
--%>

<%
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
        //String pointer = ClwI18nUtil.getMessage(request,"shoppingItems.text.itemNo",null)+ "=" + cii.mCartItemData.getActualSkuNum();
%>

<tr>
<%--
<td class="warningline"> <%=pointer%>&nbsp;:&nbsp;<%=message%></td>
--%>
<td class="warningline"><%=message%></td>
<%--
<td class="warningline"><%=cii.mCartItemData.getActualSkuNum()%></td>
<td class="warningline"><%=cii.mCartItemData.getItemDesc()%></td>
<td> <%=message%></td>
--%>
</tr>
<% } /* end of loop on item warnings */

// Reset the item warnings list.
 v_shoppingCart.setItemMessages(null);

%>

<% }else if(confirmation!=null && confirmation.trim().length() > 0) {
	String mess= confirmation;
	String messStyle = "color:#003399; ";
%>
	<td   align="center" style="<%=messStyle%>">
	<b><%=mess%></b>
	</td>
<%}%>
<% } %>
</table>

