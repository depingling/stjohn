<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>
<div class="text">
<table cellpadding="2" cellspacing="0" border="0" width="<%=Constants.TABLEWIDTH800%>" class="mainbody">

    <html:form name="ADMIN2_USER_PROFILE_MGR_FORM"
               action="/admin2.0/admin2UProfile.do"
               scope="session" type="com.cleanwise.view.forms.Admin2UserProfileMgrForm">

        <tr>
            <td><b><app:storeMessage key="admin2.userProfile.label.userFirstName"/>:</b></td>
            <td><bean:write name="ADMIN2_USER_PROFILE_MGR_FORM" property="user.firstName"/></td>

            <td><b><app:storeMessage key="admin2.userProfile.label.userLastName"/>:</b></td>
            <td><bean:write name="ADMIN2_USER_PROFILE_MGR_FORM" property="user.lastName" /></td>
        </tr>

        <tr>
            <td><b><app:storeMessage key="admin2.userProfile.label.userLoginName"/>:</b></td>
            <td><bean:write name="ADMIN2_USER_PROFILE_MGR_FORM" property="user.userName"/></td>

            <td><b><app:storeMessage key="admin2.userProfile.label.userType"/>:</b></td>
            <td><bean:write name="ADMIN2_USER_PROFILE_MGR_FORM" property="user.userTypeCd"/></td>
        </tr>

        <tr>
            <td><b><app:storeMessage key="admin2.userProfile.label.newPassword"/>:</b></td>
            <td colspan="3"><html:password name="ADMIN2_USER_PROFILE_MGR_FORM" property="password" maxlength="30" /></td>
        </tr>

        <tr>
            <td><b><app:storeMessage key="admin2.userProfile.label.confirmPassword"/>:</b></td>
            <td colspan="3"><html:password name="ADMIN2_USER_PROFILE_MGR_FORM" property="confirmPassword" maxlength="30"/></td>
        </tr>

        <tr>
            <td colspan="4">&nbsp;</td>
        </tr>

        <tr>
            <td colspan="4" align="center" class='tableheader'>
                <html:submit property="action">
                    <app:storeMessage  key="global.action.label.save"/>
                </html:submit>
                <html:reset>
                    <app:storeMessage  key="admin2.button.resetFields"/>
                </html:reset>
            </td>
        </tr>
        
        <logic:present  name="ADMIN2_USER_PROFILE_MGR_FORM" property="confirmationMessage">
            <tr>
                <td colspan="4" class="admin2ConfMessage" align="center"><bean:write name="ADMIN2_USER_PROFILE_MGR_FORM" property="confirmationMessage"/></td>
            </tr>
        </logic:present>

    </html:form>

</table>
</div>





