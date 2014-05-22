<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.view.utils.*" %>

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

    //-->
</script>

<bean:define id="theForm" name="INVENTORY_SHOPPING_CART_FORM" type="com.cleanwise.view.forms.ShoppingCartForm" />

<%--bread crumb --%>
<table class="breadcrumb">
<tr class="breadcrumb">
<td class="breadcrumb" >
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
<%
    boolean isUsedPhysicalInventoryAlgorithm = ShopTool.isUsedPhysicalInventoryAlgorithm(request);
    boolean isPhysicalCartAvailable = ShopTool.isPhysicalCartAvailable(request);
%>
<% if(!isUsedPhysicalInventoryAlgorithm) {%>
<table>
<tr>
	<td><img src="<%=ClwCustomizer.getSIP(request,"StopSign.gif")%>" alt="" align="left" border="0"></td>
	<td style="font-weight:bold; color:red"><app:storeMessage key="shoppingCart.text.scheduledCartMessage"/></td>
</tr>
</table>
<% } %>
<%
    String storeDir = ClwCustomizer.getStoreDir();
    String IPTH = (String) session.getAttribute("pages.store.images");
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    SiteData thisSite = appUser.getSite();
%>

<%-- Object errors = request.getAttribute("org.apache.struts.action.ERROR");--%>


<!-- No items in the shopping cart -->
<logic:equal name="INVENTORY_SHOPPING_CART_FORM" property="cartItemsSize" value="0">

    <table cellpadding="1" cellspacing="0" align="center"
            width="<%=Constants.TABLEWIDTH800%>">
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
<table cellpadding="1" cellspacing="0" align="center"   width="<%=Constants.TABLEWIDTH800%>" >


  <tr><td>

                <jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_shoppingInvItems.jsp")%>'>
                    <jsp:param name="allowEdits" value="true"/>
                    <jsp:param name="orderBy" value="<%=theForm.getOrderBy()%>"/>
                </jsp:include>

  </td></tr>
</table>
<%--
  <html:hidden property="action" value="submitQty"/>
  <html:hidden property="action" value="BBBBBBB"/>
</html:form>
--%>
</logic:greaterThan>
<logic:equal name="INVENTORY_SHOPPING_CART_FORM" property="cartItemsSize" value="0">
<%
if(ShopTool.requestContainsErrors(request)) {%>
<table cellpadding="1" cellspacing="0" align="center"  width="<%=Constants.TABLEWIDTH800%>" >
<tr>
    <td style="color:#FF0000;" align="center"><html:errors/></td>
</tr>
</table>
<% } %>
</logic:equal>





