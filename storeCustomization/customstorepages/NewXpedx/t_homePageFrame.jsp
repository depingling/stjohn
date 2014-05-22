<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.APIAccess" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.service.api.session.UiFrame" %>
<%@ page import="com.cleanwise.service.api.value.UiFrameView" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<!--PAGESRCID=t_homePageFrame.jsp-->
<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<%
    String jspName = "";
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute("ApplicationUser");
    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    UiFrame frBean = factory.getUiFrameAPI();
    int accountId = appUser.getUserAccount().getAccountId();
//    UiFrameView homePageFrame = frBean.getActiveFrameView(accountId);
    String userLocale = appUser.getUser().getPrefLocaleCd();
    UiFrameView homePageFrame = frBean.getLocaleFrameView(accountId, userLocale);

    if (homePageFrame != null) {
        session.setAttribute("current.homepage.frame", homePageFrame);
        jspName = ClwCustomizer.getStoreFilePath(request, homePageFrame.getFrameData().getJsp());
    }
%>
<div class="frameContent" style="width=398px;">
    <% if (jspName.length() > 0) { %>
        <%--<jsp:include page="<%=jspName%>"/>--%>

		<jsp:include flush='true' page="<%=jspName%>">
		<jsp:param name="jspFormName" value="SHOPPING_CART_FORM" />
		</jsp:include>
    <% } %>
</div>
