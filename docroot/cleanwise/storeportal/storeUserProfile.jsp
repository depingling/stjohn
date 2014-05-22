<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<table ID=1438  class="mainbody" width="<%=Constants.TABLEWIDTH%>">
<tr><td>
<html:form styleId="1439" name="USER_DETAIL_FORM" action="/storeportal/userProfile.do" scope="session" type="com.cleanwise.view.forms.UserMgrDetailForm">
<table ID=1440>
        <tr>
                <td>Current Password:</td>
                <td><html:password property="oldPassword" maxlength="30" /></td>
        </tr>
        <tr>
                <td>New Password: </td>
                <td>
                        <html:password property="password" maxlength="30" />
                </td>
        </tr>
        <tr>
                <td>Confirm Password: </td>
                <td>
                        <html:password property="confirmPassword" maxlength="30" />
                </td>
        </tr>
        <tr>
                <td colspan="4" align="left">
                        <html:hidden property="action" value="update_customer_profile"/>
                        <html:submit property="action">
                                <app:storeMessage  key="global.action.label.submit"/>
                        </html:submit>
                </td>
        </tr>
</table>
<html:hidden property="profileUpdate" value="true"/>
<input type="hidden" name="<%=Constants.FORCED_PASSWORD_UPDATE%>" value="true"/>
</html:form>
</td></tr></table>