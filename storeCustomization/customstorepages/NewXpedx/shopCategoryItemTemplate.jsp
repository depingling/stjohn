<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ShopTool" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page import="com.cleanwise.service.api.dao.* "%>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.session.*" %>
<%@ page import="com.cleanwise.service.api.*" %>
<!--PAGESRCID=shopCategoryItemTemplate.jsp-->
<table align=center CELLSPACING=0 CELLPADDING=0 width="800">
<tr>
<td valign="top">
<table CELLSPACING=0 CELLPADDING=0 width="140">
	<% CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
		AccountData accD = appUser.getUserAccount();
		String showMyShoppingLists = accD.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SHOW_MY_SHOPPING_LISTS);
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

        </jsp:include></td>
	</tr>
	<tr><td><img src="<%=ClwCustomizer.getSIP(request,"spacer.gif")%>" border="0" height="10"></td></tr>
	<tr>
		<td valign="top"> <jsp:include page='<%=ClwCustomizer.getStoreFilePath(request,"xpdexGradientCantainer.jsp")%>'>
	                        <jsp:param name="content" value="printExportMenu.jsp"/>
	                        <jsp:param name="width" value="150"/>

	                    </jsp:include>

		</td>
		</tr>
		</table>
		</td><td valign="top">
		<table CELLSPACING=0 CELLPADDING=0 width="625">
	<tr>
        <td rowspan="2" valign="top">
            <table width="100%">
                <tr>
                    <td>
                        <jsp:include page='<%=ClwCustomizer.getStoreFilePath(request,"t_msbSiteCategoryNavigator.jsp")%>'/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <%String source = request.getParameter("source");%>
                        <%if (Utility.isSet(source)) {%>
                        <jsp:include page='<%=ClwCustomizer.getStoreFilePath(request,source)%>'/>
                        <%} else {%>
                        <% if (RefCodeNames.SHOP_UI_TYPE.B2B.equals(ShopTool.getShopUIType(request, RefCodeNames.SHOP_UI_TYPE.B2C))) {%>
                        <jsp:include page = '<%=ClwCustomizer.getStoreFilePath(request,"t_itemCategoryViewB2B.jsp")%>'/>
                        <%} else { %>
                        <jsp:include page = '<%=ClwCustomizer.getStoreFilePath(request,"t_itemCategoryView.jsp")%>'/>
                        <%}
                        }%>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
	</table>

	<% if (RefCodeNames.SHOP_UI_TYPE.B2C.equals(ShopTool.getShopUIType(request, RefCodeNames.SHOP_UI_TYPE.B2C))) {%>
	</td></tr>

</table>
<%}%>
