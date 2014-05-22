
<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.view.utils.ShopTool" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Locale" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

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
-->

</script>



<%
String storeDir=ClwCustomizer.getStoreDir();
String IPTH = (String) session.getAttribute("pages.store.images");

   ShoppingCartData shoppingCart = (ShoppingCartData) session.getAttribute(Constants.SHOPPING_CART);
   CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
   SiteData thisSite = appUser.getSite();
%>

<bean:define id="theForm" name="SHOPPING_CART_FORM"
  type="com.cleanwise.view.forms.ShoppingCartForm"/>

<table cellpadding="1" cellspacing="0" align="center"
  class="tbstd"  width="<%=Constants.TABLEWIDTH%>" >

<tr>
<td rowspan="2" class="shoppingCartSubHeader" valign="top">
  <app:storeMessage key="shoppingCart.text.discretionaryCart"/></td>
<td class="shoponsiteservelt" valign="bottom">
<b><app:storeMessage key="shoppingCart.text.lastModified"/></b>
</td>
<td class="shoponsiteservelt" valign="bottom">
<i18n:formatDate value="<%=shoppingCart.getModDate()%>"
  pattern="yyyy-M-d k:mm"
  locale="<%=ClwI18nUtil.getUserLocale(request)%>"/>
</td>
<tr>
<td class="shoponsiteservelt" valign="top">
<b><app:storeMessage key="shoppingCart.text.modifiedBy"/></b>
</td>
<td class="shoponsiteservelt" valign="top">
<% if ( null != shoppingCart.getModBy() ) { %>
<%=shoppingCart.getModBy()%>
<% } %>
</td>
</tr>

</table>

<% Object errors =request.getAttribute("org.apache.struts.action.ERROR");
   if(errors!=null) {
%>
<table cellpadding="1" cellspacing="0" align="center"
  class="tbstd"  width="<%=Constants.TABLEWIDTH%>" >

<tr>
<td class="genericerror" align="center"><html:errors/></td>
</tr>

</table>
<%}%>

<table cellpadding="4" cellspacing="0" align="center"
  class="tbstd"  >
<html:form styleId="sct" action="store/shoppingcart.do">
<tr valign="top">
  <td>
    <jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_budgetInfoInc.jsp")%>'>
      <jsp:param name="jspFormName" 	        value="SHOPPING_CART_FORM" />
            <jsp:param name="showNotAffectBudgetButton" value="true"/>
            <jsp:param name="formId" value="sct"/>
    </jsp:include>
  </td>
</tr>
  <html:hidden property="command" value="CCCCCCC"/>
</html:form>
</table>


<%
OrderData prevOrder = shoppingCart.getPrevOrderData();

if ( prevOrder != null ) {
%>

<table cellpadding="1" cellspacing="0" align="center"
  class="tbstd"  width="<%=Constants.TABLEWIDTH%>" >
<tr><td rowspan=4><app:storeMessage key="shoppingCart.text.thisOrderWasCreatedFrom"/> </td></tr>
<tr><td><app:storeMessage key="shoppingCart.text.orderNumber"/></td>
   <td> <%= prevOrder.getOrderNum() %></td></tr>
<tr><td><app:storeMessage key="shoppingCart.text.ordedBy"/></td>
   <td> <%= prevOrder.getUserFirstName() %> <%= prevOrder.getUserLastName() %> ( <%= prevOrder.getAddBy() %> )</td></tr>
<tr><td><app:storeMessage key="shoppingCart.text.originalOrderDate"/></td>
  <td><i18n:formatDate value="<%=prevOrder.getOriginalOrderDate()%>"
    pattern="yyyy-M-d"  locale="<%=ClwI18nUtil.getUserLocale(request)%>"/></td>
</tr>
</table>

<% } %>


<jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_shopping_msgs.jsp")%>'/>

<% if(theForm.getOrderGuideId()>0) { %>

<table cellpadding="1" cellspacing="0" align="center"
  class="tbstd"  width="<%=Constants.TABLEWIDTH%>" >

<td class="genericerror" align="center">
<b><app:storeMessage key="shoppingCart.text.orderGuideCreated" arg0="<%=theForm.getUserOrderGuideName()%>"/>
</td>

</table>

<% }
  theForm.setOrderGuideId(0);
  theForm.setUserOrderGuideName("");
%>

<!-- No items in the shopping cart -->
<logic:equal name="SHOPPING_CART_FORM" property="cartItemsSize" value="0">

<table cellpadding="1" cellspacing="0" align="center"
  class="tbstd"  width="<%=Constants.TABLEWIDTH%>" >
<tr>
<td class="text" align="center">
  <br><b><app:storeMessage key="shoppingCart.text.shoppingCartIsEmpty" /></b><br>&nbsp;
</td>
</tr>


</table>

</logic:equal>



<!-- There are items in the shopping cart -->
<logic:greaterThan name="SHOPPING_CART_FORM" property="cartItemsSize" value="0">
<table cellpadding="1" cellspacing="0" align="center"
  class="tbstd"  width="<%=Constants.TABLEWIDTH%>" >

<tr>

<script type="text/javascript" language="JavaScript">
<!--
function f_subOrderByReq() {
  var orderByElement = document.getElementById("subOrderBy");
  orderByElement.submit();
}
//-->
</script>



<html:form action="/store/shoppingcart.do?action=sort" styleId="subOrderBy">
<td align="right">

  <b><app:storeMessage key="shoppingCart.text.orderBy"/></b>
   <html:select name="SHOPPING_CART_FORM" property="orderBy"  onchange="f_subOrderByReq()">
     <html:option value="<%=\"\"+Constants.ORDER_BY_CATEGORY%>">
     <app:storeMessage key="shoppingCart.text.category"/>
     </html:option>
     <html:option value="<%=\"\"+Constants.ORDER_BY_CUST_SKU%>">
     <app:storeMessage key="shoppingCart.text.ourSkuNum"/>
     </html:option>
     <html:option value="<%=\"\"+Constants.ORDER_BY_NAME%>">
     <app:storeMessage key="shoppingCart.text.productName"/>
     </html:option>
   </html:select>
</td>
</html:form>


</tr>
</table>
<table cellpadding="1" cellspacing="0" align="center"
  class="tbstd"  width="<%=Constants.TABLEWIDTH%>" >
  <tr>  <td class="tableoutline"><img src="images/spacer.gif" height="1"></td>  </tr>
</table>
<table cellpadding="1" cellspacing="0" align="center"
  class="tbstd"  width="<%=Constants.TABLEWIDTH%>" >




<html:form action="/store/shoppingcart.do">
<tr>
<td>
 <jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_scart_buttons1.jsp")%>'/>
</td>
<%--
<jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_saveOrderGuideButton.jsp")%>'/>
--%>
</tr>
</table>

<table cellpadding="1" cellspacing="0" align="center"
  class="tbstd"  width="<%=Constants.TABLEWIDTH%>" >
  <tr>  <td class="tableoutline"><img src="images/spacer.gif" height="1"></td>  </tr>
</table>

<table cellpadding="1" cellspacing="0" align="center"
  class="tbstd"  width="<%=Constants.TABLEWIDTH%>" >
<tr><td>

  <jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_shoppingItems.jsp")%>'>
  <jsp:param name="allowEdits" value="true"/>
  <jsp:param name="orderBy" value="<%=theForm.getOrderBy()%>"/>
  </jsp:include>

</td></tr>
</table>


<table cellpadding="1" cellspacing="0" align="center"
  class="tbstd"  width="<%=Constants.TABLEWIDTH%>" >
  <tr>  <td class="tableoutline"><img src="images/spacer.gif" height="1"></td>  </tr>
</table>

<table cellpadding="1" cellspacing="0" align="center"
  width="<%=Constants.TABLEWIDTH%>" >
    <tr>
<td>
<jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_scart_buttons1.jsp")%>'/>
</td>

<%
if(ShopTool.hasDiscretionaryCartAccessOpen(request)){ %>
<td class="text" align="right"><div class="fivemargin">
     <b><app:storeMessage key="shoppingCart.text.total"/><b>
   </div></td>
   <td class="text"><div class="fivemargin">
      <%--<bean:define id="itemsAmt"  name="SHOPPING_CART_FORM" property="itemsAmt"/>
      <%=ClwI18nUtil.getPriceShopping(request,itemsAmt," ")%>--%>
	  
	  <%--<%Double itemsAmt = new Double(theForm.getShoppingCart().getItemsCost()); %>
	  <%=ClwI18nUtil.getPriceShopping(request,itemsAmt)%>--%>
	  
	  <%Double itemsAmt = new Double(theForm.getItemsAmt(request)); %>
	  <%=ClwI18nUtil.getPriceShopping(request,itemsAmt)%>
   </div></td> 
   <td>&nbsp;</td> 

<% } %> 
   </tr>
<html:hidden property="action" value="submitQty"/>
<html:hidden property="action" value="BBBBBBB"/>
  </html:form>
</table>
</logic:greaterThan>

<jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_table_bottom1.jsp")%>'/>
<br>



