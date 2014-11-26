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

<app:setLocaleAndImages/>
    <%
        String errorsAndMessagesPage = ClwCustomizer.getStoreFilePath(request, Constants.PORTAL_ESW, "errorsAndMessages.jsp");
    %>
    
    <jsp:include page="<%=errorsAndMessagesPage %>"/>
    
    <logic:present name="esw.ModuleIntegrationForm" property="currentMessage">      
            <bean:define id="currentMessage" name="esw.ModuleIntegrationForm" property="currentMessage"
                type="com.cleanwise.service.api.value.StoreMessageView"/>
            <h4>
                <bean:write name="esw.ModuleIntegrationForm" property="currentMessage.messageTitle"/>&nbsp;
            </h4>
            <p class="credit">
                <logic:notEmpty name="esw.ModuleIntegrationForm" property="currentMessage.messageAuthor">
                <app:storeMessage key="message.label.from" />: <bean:write name="esw.ModuleIntegrationForm" property="currentMessage.messageAuthor"/><br />
                <%=ClwI18nUtil.formatDateInp(request, currentMessage.getPostedDate())%>
                </logic:notEmpty>
            </p>
            <p>
                <bean:write filter="false" name="esw.ModuleIntegrationForm" property="currentMessage.messageBody"/>
            </p>
    </logic:present>
    
    <logic:notPresent name="esw.ModuleIntegrationForm" property="currentMessage">
        <logic:present name="esw.StoreMessageForm" property="currentMessage">      
            <bean:define id="currentMessage" name="esw.StoreMessageForm" property="currentMessage"
                type="com.cleanwise.service.api.value.StoreMessageView"/>
            <h4>
                <bean:write name="currentMessage" property="messageTitle"/>&nbsp;
            </h4>
            <p class="credit">
                <app:storeMessage key="message.label.from" />: <bean:write name="currentMessage" property="messageAuthor"/><br />
                <%=ClwI18nUtil.formatDateInp(request, currentMessage.getPostedDate())%>
            </p>
            <p>
                <bean:write filter="false" name="currentMessage" property="messageBody"/>
            </p>
        </logic:present>
    </logic:notPresent>