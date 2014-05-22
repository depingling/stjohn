<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page import="com.cleanwise.view.forms.ShoppingCartForm" %>
<%@ page import="org.apache.struts.action.ActionErrors" %>
<%@ page import="org.apache.struts.action.ActionMessages" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

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

    //-->
 </script>


<bean:define id="theForm" name="INVENTORY_SHOPPING_CART_FORM" type="com.cleanwise.view.forms.ShoppingCartForm"/>


<%
    String storeDir = ClwCustomizer.getStoreDir();
    ShoppingCartData shoppingCart = ((ShoppingCartForm) theForm).getShoppingCart();
    String IPTH = (String) session.getAttribute("pages.store.images");
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    SiteData thisSite = appUser.getSite();
%>

<table cellpadding="1" cellspacing="0" align="center" class="tbstd" width="<%=Constants.TABLEWIDTH%>">

    <tr>
        <td rowspan="2" class="shoppingCartSubHeader" valign="top">
          <jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_scheduledCartTitle.jsp")%>'/>
        </td>
    </tr>

</table>

<% ActionErrors errors = (ActionErrors)request.getAttribute("org.apache.struts.action.ERROR");
    if (errors != null && errors.size()>0) {
%>
<table cellpadding="1" cellspacing="0" align="center"
       class="tbstd" width="<%=Constants.TABLEWIDTH%>">

    <tr>
        <td class="genericerror" align="center">
            <html:errors/>
        </td>
    </tr>

</table>
<%}%>
<% ActionMessages warnings = (ActionMessages) request.getAttribute("org.apache.struts.action.ACTION_MESSAGE");
    if (warnings != null && warnings.size()>0) {
%>
<table cellpadding="1" cellspacing="0" align="center"
       class="tbstd" width="<%=Constants.TABLEWIDTH%>">

    <tr>
        <td class="genericerror" align="center">
		<html:messages id="msg" message="true">
		<bean:write name="msg"/><br>
		</html:messages>
        </td>
    </tr>

</table>
<%}%>


<table cellpadding="4" cellspacing="0" align="center"  class="tbstd">
    <html:form styleId="sct" action="/store/scheduledCart.do">
        <tr valign="top">
            <td>
                <jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request, "f_budgetInfoInc.jsp")%>'>
                    <jsp:param name="jspFormName" value="INVENTORY_SHOPPING_CART_FORM"/>
                    <jsp:param name="showNotAffectBudgetButton" value="true"/>
                    <jsp:param name="formId" value="sct"/>
                </jsp:include>
            </td>
        </tr>
        <html:hidden property="command" value="CCCCCCC"/>
    </html:form>
</table>
<jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_shopping_msgs.jsp")%>'>
    <jsp:param name="shoppingCartName" value="<%=Constants.INVENTORY_SHOPPING_CART%>"/>
</jsp:include>
<!-- No items in the shopping cart -->
<logic:equal name="INVENTORY_SHOPPING_CART_FORM" property="cartItemsSize" value="0">

    <table cellpadding="1" cellspacing="0" align="center"
           class="tbstd" width="<%=Constants.TABLEWIDTH%>">
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
<table cellpadding="1" cellspacing="0" align="center"  class="tbstd" width="<%=Constants.TABLEWIDTH%>">

    <tr>
        <html:form action="/store/scheduledCart.do?action=sort" styleId="subOrderBy">
            <td align="right">
                <b><app:storeMessage key="shoppingCart.text.orderBy"/></b>

                <html:select name="INVENTORY_SHOPPING_CART_FORM" property="orderBy" onchange="f_subOrderByReq()">
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


<table cellpadding="0" cellspacing="0" align="center" class="tbstd"  width="<%=Constants.TABLEWIDTH%>" >

    <html:form action="/store/scheduledCart.do">
        <tr>
            <td>
                <table cellpadding="0" cellspacing="0" align="center"width="100%">
                    <tr>
                        <td class="tableoutline"><img src="images/spacer.gif" height="1"></td>
                    </tr>
                </table>
            </td>
        </tr>
        <tr>
            <td>
                <table cellpadding="0" cellspacing="0" align="center" width="100%">
                    <tr>
                        <jsp:include flush='true'
                                     page='<%=ClwCustomizer.getStoreFilePath(request,"f_inv_scart_buttons.jsp")%>'/>
                    </tr>
                </table>
            </td>
        </tr>
        <tr>
            <td>
                <table cellpadding="0" cellspacing="0" align="center" width="100%">
                    <tr>
                        <td class="tableoutline"><img src="images/spacer.gif" height="1"></td>
                    </tr>
                </table>
            </td>
        </tr>
        <tr>
            <td>
                <jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_shoppingInvItems.jsp")%>'>
                    <jsp:param name="allowEdits" value="true"/>
                    <jsp:param name="orderBy" value="<%=theForm.getOrderBy()%>"/>
                </jsp:include>

            </td>
        </tr>

        <tr>
            <td>
                <table cellpadding="0" cellspacing="0" align="center" width="100%">
                    <tr>
                        <td class="tableoutline"><img src="images/spacer.gif" height="1"></td>
                    </tr>
                </table>
            </td>
        </tr>

        <tr>
            <td>
                <table cellpadding="0" align="center" cellspacing="0" width="100%">

                    <tr>
                        <td>&nbsp;</td>
                        <td class="text" align="right">
                            <div class="fivemargin">
                                <b>
                                    <app:storeMessage key="shoppingCart.text.total"/>
                                </b>
                            </div>
                        </td>
                        <td class="text">
                            <div class="fivemargin">
                                <%--<bean:define id="itemsAmt" name="INVENTORY_SHOPPING_CART_FORM" property="itemsAmt"/>
                                <%=ClwI18nUtil.getPriceShopping(request, itemsAmt, " ")%>--%>

				<%Double itemsAmt = new Double(theForm.getItemsAmt(request)); %>
	 			 <%=ClwI18nUtil.getPriceShopping(request,itemsAmt)%>

                            </div>
                        </td>
                        <td>&nbsp;</td>
                    </tr>
                </table>
            </td>
        </tr>
        <html:hidden property="action" value="submitQty"/>
        <html:hidden property="action" value="BBBBBBB"/>

    </html:form>
</table>
</logic:greaterThan>

<jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_table_bottom1.jsp")%>'/>

<br>
<jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_shop_history_items_removed.jsp")%>'>
  <jsp:param name="shoppingCartName" value="<%=Constants.INVENTORY_SHOPPING_CART%>"/>
</jsp:include>




