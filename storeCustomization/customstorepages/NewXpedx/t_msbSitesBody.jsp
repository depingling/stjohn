<%@ page language="java" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.util.Utility"%>
<%@ page import="com.cleanwise.service.api.util.DBCriteria"%>
<%@ page import="com.cleanwise.service.api.dao.* "%>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.session.*" %>
<%@ page import="com.cleanwise.service.api.*" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="com.cleanwise.service.api.value.*"%>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<table align=center CELLSPACING=0 CELLPADDING=0 width="800">
<tr>
<td align="left" width="160" valign="top">
<table CELLSPACING=0 CELLPADDING=0>

	<bean:define id="theForm" name="SHOP_FORM" type="com.cleanwise.view.forms.UserShopForm"/>
	<%
		boolean isUsedPhysicalInventoryAlgorithm = ShopTool.isUsedPhysicalInventoryAlgorithm(request);
		boolean isPhysicalCartAvailable = ShopTool.isPhysicalCartAvailable(request);
		CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
		AccountData accD = appUser.getUserAccount();
		String showMyShoppingLists = accD.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SHOW_MY_SHOPPING_LISTS);
		String showExpressOrder = accD.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SHOW_EXPRESS_ORDER);
        SiteData thisSite = appUser.getSite();
        OrderGuideDataVector ogDV = (OrderGuideDataVector) session.getAttribute(SessionAttributes.ORDER_GUIDE.ALL_ORDER_GUIDES);
        if (ogDV.size() > 0 && showMyShoppingLists.equals("true")) { %>
		<tr>
		<td valign="top">
		<jsp:include page='<%=ClwCustomizer.getStoreFilePath(request,"xpdexGradientCantainer.jsp")%>'>
	                        <jsp:param name="content" value="ogMenu.jsp"/>
	                        <jsp:param name="width" value="150"/>

	                    </jsp:include>

		</td>
		</tr>
		<tr><td><img src="<%=ClwCustomizer.getSIP(request,"spacer.gif")%>" border="0" height="10"></td></tr>
	<% } %>

	<tr>
	<td valign="top">

                    <jsp:include page='<%=ClwCustomizer.getStoreFilePath(request,"xpdexGradientCantainer.jsp")%>'>
                        <jsp:param name="content" value="verticalMenu.jsp"/>
                        <jsp:param name="width" value="150"/>

                    </jsp:include>
	</td>
	</tr>
	<tr><td><img src="<%=ClwCustomizer.getSIP(request,"spacer.gif")%>" border="0" height="10"></td></tr>
	<tr>
		<td valign="top"> <jsp:include page='<%=ClwCustomizer.getStoreFilePath(request,"xpdexGradientCantainer.jsp")%>'>
	                        <jsp:param name="content" value="printExportMenu.jsp"/>
	                        <jsp:param name="width" value="150"/>

	                    </jsp:include>

		</td>
		</tr>
		<tr><td>
		<img src="<%=ClwCustomizer.getSIP(request,"spacer.gif")%>" border="0" height="10"></td></tr>
</table>
</td>


<td valign="top" align="left" width="400">
	<%
	  if (isUsedPhysicalInventoryAlgorithm && isPhysicalCartAvailable) {
        %>
        <span style="color:#FF0000;" class="shoppingCartButton">
          <app:storeMessage key="shoppingCart.text.physicalCart"/>
        </span>

<%
}
%>
<br>
<div class="itemGroupShortInfo"  style="background-color:#fff">

      <b class=rtopborder style="background-color:#fff">
           <b class=r1border style="background-color:#b5b5b5"></b>
           <b class=r2border style="background-color:#fff"></b>
           <b class=r3border style="background-color:#fff"></b>
           <b class=r4border style="background-color:#fff"></b>
      </b>

<div class="borderContent" style="border-left:1px solid #B5B5B5;border-right:1px solid #B5B5B5;background-color:white">
<%
    String homePageFrameJsp = (String)request.getAttribute("homePageFrameJspName");
    if (homePageFrameJsp == null || homePageFrameJsp.length() == 0) {
        homePageFrameJsp = "t_homePageFrame.jsp";
    }
%>
	<jsp:include page='<%=ClwCustomizer.getStoreFilePath(request,homePageFrameJsp)%>'/>
</div>

          <b class=rbottomborder style="background-color:#fff">
              <B class=r4border style="background-color:#fff"></b>
              <b class=r3border style="background-color:#fff"></b>
              <b class=r2border style="background-color:#fff"></b>
              <b class=r1border style="background-color:#b5b5b5"></b>
          </b>

</div>
</td>

<td align="right" valign="top" width="200">
<table valign="top" CELLSPACING=0 CELLPADDING=0>
<% if ((appUser.getSite() != null
            && ShopTool.isModernInventoryShopping(request)
            && !ShopTool.hasDiscretionaryCartAccessOpen(request)
            && session.getAttribute(Constants.INVENTORY_SHOPPING_CART) != null) || appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.AUTO_DISTRO_ACCESS)) {%>
	<tr>
	<td valign="top"><jsp:include page='<%=ClwCustomizer.getStoreFilePath(request,"xpdexGradientCantainer.jsp")%>'>
                        <jsp:param name="content" value="autoDistroInfo.jsp"/>
                        <jsp:param name="width" value="180"/>
                    </jsp:include>
	</td>
	</tr>
	<tr><td><img src="<%=ClwCustomizer.getSIP(request,"spacer.gif")%>" border="0" height="10"></td></tr>
	<% } %>

	<%if(showExpressOrder.equals("true") && (!ShopTool.isPhysicalCartAvailable(request))){%>
	<tr>
	<td colspan="2" valign="top">

                    <jsp:include page='<%=ClwCustomizer.getStoreFilePath(request,"xpdexGradientCantainer.jsp")%>'>
                        <jsp:param name="content" value="quickOrderTemplate.jsp"/>
                        <jsp:param name="width" value="180"/>
                        <jsp:param name="jspFormName" value="QUICK_ORDER_FORM"/>
                    </jsp:include>
	</tr>
	<tr><td><img src="<%=ClwCustomizer.getSIP(request,"spacer.gif")%>" border="0" height="10"></td></tr>
	<%}%>

	<tr>
	<td colspan="2" valign="top">

                    <jsp:include page='<%=ClwCustomizer.getStoreFilePath(request,"xpdexGradientCantainer.jsp")%>'>
                        <jsp:param name="content" value="appleNews.jsp"/>
                        <jsp:param name="width" value="180"/>

                    </jsp:include>
	</td>
	</tr>
</table>
</td>

</tr>
</table>
