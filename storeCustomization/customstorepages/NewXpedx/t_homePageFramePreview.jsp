<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.UiFrameView" %>


<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<%
    String jspName = "";
    UiFrameView homePageFrame = (UiFrameView)session.getAttribute("current.homepage.frame");
    jspName = ClwCustomizer.getStoreFilePath(request, homePageFrame.getFrameData().getJsp());
%>
<div>
    <% if (jspName.length() > 0) { %>
        <jsp:include page="<%=jspName%>"/>
    <% } %>
</div>