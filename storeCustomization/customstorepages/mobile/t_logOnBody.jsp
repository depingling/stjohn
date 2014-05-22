<%@ page language="java"%>
<%@ page import="com.cleanwise.view.utils.Constants"%>
<%@ page import="com.cleanwise.service.api.util.Utility"%>
<%@ page contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app"%>

<app:setLocaleAndImages />

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session" />


<html:form action="userportal/logon.do" focus="j_username"
	onsubmit="return validate(this)">
	<table>

		<tr>
			<td colspan="2"><span class="subheadergeneric"> <app:storeMessage
				key="login.label.title" /></td>
		</tr>
		<tr>
			<td colspan="2">&nbsp;</td>
		</tr>
		<tr>
			<td class="text"><app:storeMessage key="login.label.username"/></td>
			<td><html:text name="LOGON" property="j_username" size="15"
				maxlength="45" tabindex="1"/></td>
		</tr>
		<tr>
			<td class="text"><app:storeMessage key="login.label.password" /></td>
			<td><html:password property="j_password" size="15"
				maxlength="20" tabindex="2"/></td>
		</tr>
		<tr>
			<td colspan="2">&nbsp;</td>
		</tr>
		<tr>

			<td colspan="2"><input type='submit' property="action"
				class='store_fb'
				value='<app:storeMessage key="global.action.label.submit" />' tabIndex="3"/>

			<input type="hidden" name="PageVisitTime"
				value="<%=new java.util.Date()%> "></td>
		</tr>


		<tr>
			<td colspan=2><br>
			<html:errors /> <br>
			<bean:define id="currUser" type="java.lang.String" name="LOGON"
				property="j_username" /> <% if (currUser.length() > 0) {  %> <logic:present
				name="LoginFailureCount">
				<logic:greaterThan name="LoginFailureCount" value="0">
					<a href="pwdAccess.do?userName=<%=Utility.encodeForURL(currUser)%>&action=send" tabIndex="99">[<app:storeMessage
						key="login.text.forgotPasswordQst" />]</a>
					<br>
					<br>
				</logic:greaterThan>
			</logic:present> <% } // End of user name length check. %>
			</td>
		</tr>

		<tr>
			<td colspan="2"></td>
		</tr>
	</table>
</html:form>
