<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="com.cleanwise.view.i18n.ClwMessageResourcesImpl"%>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer"%>
<%@ page import="com.cleanwise.view.utils.Constants"%>

<%@ taglib uri='/WEB-INF/application.tld' prefix='app' %>
<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='template' %>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%
	StringBuilder messageURL = new StringBuilder(50);
	messageURL.append("showMessage.do?");
	messageURL.append(Constants.PARAMETER_OPERATION);
	messageURL.append("=");
	messageURL.append(Constants.PARAMETER_OPERATION_VALUE_SHOW_MESSAGE);
	messageURL.append("&currentMessage.storeMessageId=");
%>
                <!-- Start Left Column - columns are reversed to allow expanding right column -->
                <div class="leftColumn">
					<%
						String orderSearchPage = ClwCustomizer.getStoreFilePath(request, Constants.PORTAL_ESW, "orderNumSearch.jsp");
					%>
			        <jsp:include page="<%=orderSearchPage%>">
                		<jsp:param name="orientation" value="<%=Constants.ORIENTATION_VERTICAL%>"/>
                	</jsp:include>
					<%
						String productSearchPage = ClwCustomizer.getStoreFilePath(request, Constants.PORTAL_ESW, "productSearch.jsp");
					%>
			        <jsp:include page="<%=productSearchPage%>">
                		<jsp:param name="orientation" value="<%=Constants.ORIENTATION_VERTICAL%>"/>
                	</jsp:include>
					<%
						String storeMessagesPage = ClwCustomizer.getStoreFilePath(request, Constants.PORTAL_ESW, "storeMessages.jsp");
					%>
                	<jsp:include page="<%=storeMessagesPage%>">
                		<jsp:param name="formBeanName" value="esw.DashboardForm"/>
                		<jsp:param name="messageURL" value="<%=messageURL.toString()%>"/>
                	</jsp:include>
                </div>
                <!-- End Left Column -->
                    