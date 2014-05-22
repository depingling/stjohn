<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.view.utils.*" %>
﻿<%@ page import="java.text.DateFormat" %>
﻿<%@ page import="com.cleanwise.service.api.util.*" %>


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

<%
    String storeDir = ClwCustomizer.getStoreDir();
    String IPTH = (String) session.getAttribute("pages.store.images");
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    SiteData thisSite = appUser.getSite();
%>
<%
String confMessage;
if(ShopTool.isPhysicalCartCompliant(request)){
	confMessage = ClwI18nUtil.getMessage(request, "shop.checkout.text.actionMessage.physicalInventoryCompliant", null );
	%>
<table>
<tr><td align="center"><b><font color="#003399"><%=confMessage%></font></b></td> </tr>
</table>
<%
}else{
	String linkMess = ClwI18nUtil.getMessage(request, "shop.checkout.text.hereLink", null );
	String link = "<a href=\"physicalCart.do\">"+linkMess+ "</a>";
	Object[] param = new Object[2];
    param[0] = ":::::::";
    PhysicalInventoryPeriod invPeriod = ShopTool.getCurrentPhysicalPeriod(request);
    if(invPeriod != null && invPeriod.getEndDate() != null){
    	param[1] = ClwI18nUtil.formatDate(request,invPeriod.getEndDate(),DateFormat.MEDIUM);
    }else{
    	param[1] = "error";
    }
	confMessage = ClwI18nUtil.getMessage(request, "shop.checkout.text.actionMessage.physicalInventoryNonCompliant", param );
	confMessage = confMessage.replace(param[0].toString(), link);
	%>
	<table width="800">
    <tr>
    <td><img src="<%=ClwCustomizer.getSIP(request,"StopSign.gif")%>" alt="" align="left" border="0"></td>
	<td style="font-weight:bold; color:red"><%=confMessage%></td>
    </tr>
	</table>
	<%
}
%>

<%--End of Shopping Cart Header--%>






