<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);%>
<%
    String entityMsg;
    if (appUser.isaAccountAdmin()) {
        entityMsg = ClwI18nUtil.getMessage(request,"admin2.home.text.managedAccounts",null);
    } else if (appUser.isaAdmin()) {
        entityMsg =  ClwI18nUtil.getMessage(request,"admin2.home.text.managedStores",null);
    } else {
        entityMsg = null;
    }
%>
<%if (Utility.isSet(entityMsg)) {%>
<table width="<%=Constants.TABLEWIDTH800%>">

    <tr><td>&nbsp;</td></tr>
    <tr><td><b><%=entityMsg%>:</b></td></tr>
    <logic:present name="ADMIN2_HOME_MGR_FORM" property="managedEntities">
        <logic:iterate id='entity' name='ADMIN2_HOME_MGR_FORM' property='managedEntities' type='com.cleanwise.service.api.value.BusEntityData'>
            <tr>
                <td>&nbsp;</td>
                <td>
                    <a href="admin2AdminHome.do?action=changeEntity&id=<bean:write name='entity' property='busEntityId'/>">
                        <bean:write name='entity' property='shortDesc'/>
                    </a>
                </td>
            </tr>
        </logic:iterate>
    </logic:present>
    <tr><td>&nbsp;</td></tr>
    <tr><td>&nbsp;</td></tr>

</table>
<%}%>
