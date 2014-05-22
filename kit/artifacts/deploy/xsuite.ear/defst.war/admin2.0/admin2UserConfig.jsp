<%@ page language="java" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.value.UserData" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.forms.Admin2UserConfigMgrForm" %>
<%@ page import="com.cleanwise.service.api.dao.UniversalDAO" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="theForm" name="ADMIN2_USER_CONFIG_MGR_FORM" type="com.cleanwise.view.forms.Admin2UserConfigMgrForm"/>

<%
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

    UserData v_userData = theForm.getUser();
    String userTypeCd = v_userData.getUserTypeCd();
    String confFunc = theForm.getConfFunction();
%>

<table border="0" cellpadding="0" cellspacing="0" width="<%=Constants.TABLEWIDTH800%>">
    <tr><td>
        <table border="0" cellpadding="0" cellspacing="0" width="100%" bgcolor="#cccccc">

            <tr><td><b><app:storeMessage key="admin2.user.config.label.userId"/>:</b></td>
                <td><bean:write name="ADMIN2_USER_CONFIG_MGR_FORM" property="user.userId" filter="true"/>
                </td>
                <td> <b><app:storeMessage key="admin2.user.config.label.userName"/>:</b> </td>
                <td> <bean:write name="ADMIN2_USER_CONFIG_MGR_FORM" property="user.userName" filter="true"/>
                </td>
                <td><b><app:storeMessage key="admin2.user.config.label.modBy"/>:</b></td>
                <td><bean:write name="ADMIN2_USER_CONFIG_MGR_FORM" property="user.modBy"/></td>
            </tr>
            <tr>
                <td><b><app:storeMessage key="admin2.user.config.label.userType"/>:</b></td>
                <td><bean:write name="ADMIN2_USER_CONFIG_MGR_FORM" property="user.userTypeCd" filter="true"/></td>
                <td><b><app:storeMessage key="admin2.user.config.label.userStatus"/>:</b></td>
                <td><bean:write name="ADMIN2_USER_CONFIG_MGR_FORM" property="user.userStatusCd" filter="true"/>
                </td>
                <td><b><app:storeMessage key="admin2.user.config.label.modDate"/>:</b></td>
                <td><bean:write name="ADMIN2_USER_CONFIG_MGR_FORM" property="user.modDate"/>
                </td>
            </tr>
        </table>
    </td>

    </tr>
    <tr>
        <td>
            <table  width="100%">
                <html:form  action="/admin2.0/admin2UserConfig.do?action=initConfig">
                    <tr>
                        <td><b><app:storeMessage key="admin2.user.config.text.configure"/></b></td>
                        <td>
                            <html:select name="ADMIN2_USER_CONFIG_MGR_FORM" property="confFunction" onchange="this.form.submit();">
                                <% if(RefCodeNames.USER_TYPE_CD.DISTRIBUTOR.equals(userTypeCd)) { %>
                                <html:option value="<%=Admin2UserConfigMgrForm.CONF_FUNUNCTION.DISTRIBUTORS%>"><app:storeMessage key="admin2.user.config.text.distributors"/></html:option>
                                <% } else if (RefCodeNames.USER_TYPE_CD.SERVICE_PROVIDER.equals(userTypeCd)) { %>
                                <html:option value="<%=Admin2UserConfigMgrForm.CONF_FUNUNCTION.SERVICE_PROVIDERS%>"><app:storeMessage key="admin2.user.config.text.serviceProviders"/></html:option>
                                <html:option value="<%=Admin2UserConfigMgrForm.CONF_FUNUNCTION.GROUPS%>"><app:storeMessage key="admin2.user.config.text.groups"/></html:option>
                                <% } else { %>
                                <html:option value="<%=Admin2UserConfigMgrForm.CONF_FUNUNCTION.ACCOUNTS%>"><app:storeMessage key="admin2.user.config.text.accounts"/></html:option>
                                <html:option value="<%=Admin2UserConfigMgrForm.CONF_FUNUNCTION.SITES%>"><app:storeMessage key="admin2.user.config.text.sites"/></html:option>
                                <html:option value="<%=Admin2UserConfigMgrForm.CONF_FUNUNCTION.GROUPS%>"><app:storeMessage key="admin2.user.config.text.groups"/></html:option>
                                <html:option value="<%=Admin2UserConfigMgrForm.CONF_FUNUNCTION.CATALOGS%>"><app:storeMessage key="admin2.user.config.text.catalogsInfoOnly"/></html:option>
                                <html:option value="<%=Admin2UserConfigMgrForm.CONF_FUNUNCTION.ORDER_GUIDES%>"><app:storeMessage key="admin2.user.config.text.orderGuidesInfoOnly"/></html:option>
                                <%}%>
                            </html:select>
                        </td>
                    </tr>
                </html:form>
            </table>
        </td>
    </tr>
    <tr>
        <td>
            <table class="results" width="100%">
                <tr>
                    <td>

                        <% if (RefCodeNames.USER_TYPE_CD.DISTRIBUTOR.equals(userTypeCd)) { %>

                            <% if (Admin2UserConfigMgrForm.CONF_FUNUNCTION.DISTRIBUTORS.equals(confFunc)) { %>
                            <%@ include file="admin2UserDistConfig.jsp" %>
                            <% } %>

                        <% } else if (RefCodeNames.USER_TYPE_CD.SERVICE_PROVIDER.equals(userTypeCd)) { %>

                            <% if (Admin2UserConfigMgrForm.CONF_FUNUNCTION.SERVICE_PROVIDERS.equals(confFunc)) { %>
                            <%@ include file="admin2UserServiceProroviderConfig.jsp" %>
                            <% } else if (Admin2UserConfigMgrForm.CONF_FUNUNCTION.GROUPS.equals(confFunc)) { %>
                            <%@ include file="admin2UserGroupConfig.jsp" %>
                            <% } %>                    

                        <% } else { %>

                            <% if (Admin2UserConfigMgrForm.CONF_FUNUNCTION.ACCOUNTS.equals(confFunc)) { %>
                            <%@ include file="admin2UserAcctConfig.jsp" %>
                            <% } else if (Admin2UserConfigMgrForm.CONF_FUNUNCTION.SITES.equals(confFunc)) { %>
                            <%@ include file="admin2UserSiteConfig.jsp" %>
                            <% } else if (Admin2UserConfigMgrForm.CONF_FUNUNCTION.CATALOGS.equals(confFunc)) { %>
                            <%@ include file="admin2UserCatalogConfig.jsp" %>
                            <% } else if (Admin2UserConfigMgrForm.CONF_FUNUNCTION.ORDER_GUIDES.equals(confFunc)) { %>
                            <%@ include file="admin2UserOgConfig.jsp" %>
                            <% } else if (Admin2UserConfigMgrForm.CONF_FUNUNCTION.GROUPS.equals(confFunc)) { %>
                            <%@ include file="admin2UserGroupConfig.jsp" %>
                            <% } %>

                        <%}%>

                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>

