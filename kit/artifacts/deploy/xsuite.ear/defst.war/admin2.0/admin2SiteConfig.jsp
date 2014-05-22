<%@ page language="java" %>
<%@ page import="com.cleanwise.view.forms.Admin2SiteConfigMgrForm" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>


<bean:define id="theForm" name="ADMIN2_SITE_CONFIG_MGR_FORM" type="com.cleanwise.view.forms.Admin2SiteConfigMgrForm"/>
 
<% CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);%>
<%String confFunc = theForm.getConfFunction();%>

<html:html>

    <div class="text">

        <table border="0" cellpadding="0" cellspacing="0" width="<%=Constants.TABLEWIDTH800%>">
            <tr>
                <td>

                    <table border="0" cellpadding="0" cellspacing="0" width="100%" class="mainbody">

                        <tr>
                            <td><b><app:storeMessage key="admin2.site.detail.label.accountId"/>:</b></td>
                            <td><bean:write name="ADMIN2_SITE_CONFIG_MGR_FORM" property="account.busEntityId"/></td>
                            <td><b><app:storeMessage key="admin2.site.detail.label.accountName"/>:</b></td>
                            <td><bean:write name="ADMIN2_SITE_CONFIG_MGR_FORM" property="account.shortDesc"/></td>
                        </tr>

                        <tr>
                            <td><b><app:storeMessage key="admin2.site.detail.label.siteId"/>:</b></td>
                            <td><bean:write name="ADMIN2_SITE_CONFIG_MGR_FORM" property="site.busEntityId"/></td>
                            <td><b> <app:storeMessage key="admin2.site.detail.label.siteName"/>:</b></td>
                            <td><bean:write name="ADMIN2_SITE_CONFIG_MGR_FORM" property="site.shortDesc"/></td>
                        </tr>

                    </table>

                </td>

            </tr>

            <tr>
                <td>
                    <table width="100%">
                        <html:form action="/admin2.0/admin2SiteConfig.do?action=initConfig">
                            <tr>
                                <td><b><app:storeMessage key="admin2.user.config.text.configure"/> </b></td>
                                <td>
                                    <html:select name="ADMIN2_SITE_CONFIG_MGR_FORM" property="confFunction" onchange="this.form.submit();">
                                        <html:option value="<%=Admin2SiteConfigMgrForm.CONF_FUNUNCTION.USERS%>"><app:storeMessage key="admin2.site.config.text.users"/></html:option>
                                        <html:option value="<%=Admin2SiteConfigMgrForm.CONF_FUNUNCTION.CATALOG%>"><app:storeMessage key="admin2.site.config.text.catalog"/></html:option>
                                        <html:option value="<%=Admin2SiteConfigMgrForm.CONF_FUNUNCTION.DISTRIBUTOR_SCHEDULE%>"><app:storeMessage key="admin2.site.config.text.distributorSchedules"/></html:option>
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
                                <% if (Admin2SiteConfigMgrForm.CONF_FUNUNCTION.USERS.equals(confFunc)) { %>
                                <%@ include file="admin2SiteUserConfig.jsp" %>
                                <% } else if (Admin2SiteConfigMgrForm.CONF_FUNUNCTION.CATALOG.equals(confFunc)) { %>
                                <%@ include file="admin2SiteCatalogConfig.jsp" %>
                                <% } else if (Admin2SiteConfigMgrForm.CONF_FUNUNCTION.DISTRIBUTOR_SCHEDULE.equals(confFunc)) { %>
                                <%@ include file="admin2SiteDistSchConfig.jsp" %>
                                <%}%>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>

    </div>

</html:html>