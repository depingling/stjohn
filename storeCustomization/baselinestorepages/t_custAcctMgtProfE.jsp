<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="IMGPath" type="java.lang.String"
  name="pages.store.images"/>
<bean:define id="theForm" name="USER_DETAIL_FORM"  type="com.cleanwise.view.forms.UserMgrDetailForm"/>

<% CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER); %>

<div class="text">
<span class="genericerror"><html:errors/></span>

<TABLE cellSpacing=5 cellPadding=0 border=0>

<html:form name="USER_DETAIL_FORM"
  action="userportal/customerAccountManagementProfileEdit.do"
  scope="session" type="com.cleanwise.view.forms.UserMgrDetailForm">

<tr>
<TD colspan=3 class=text vAlign=top>
      <span class=fivemargin>
      <br><app:storeMessage key="shop.userProfile.text.makeChangesToYourProfileAndClickSubmit"/>
      </span>
      <BR><br>
</td>
</tr>
        <TR>
          <TD colspan=3>
            <div class="bar_a">
             <app:storeMessage key="shop.userProfile.text.yourProfile"/>
             </div>
          </TD></TR>


<tr><td><app:storeMessage key="shop.userProfile.text.firstName"/> </td>
<td>

    <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.EDIT_USER_PROFILE_NAME%>">
        <html:text name="USER_DETAIL_FORM" property="detail.userData.firstName" maxlength="255"/>
    </app:authorizedForFunction>
    <app:notAuthorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.EDIT_USER_PROFILE_NAME%>">
        <bean:write name="USER_DETAIL_FORM" property="detail.userData.firstName"/>
    </app:notAuthorizedForFunction>
</td></tr>

<tr><td><app:storeMessage key="shop.userProfile.text.lastName"/> </td>
<td>
    <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.EDIT_USER_PROFILE_NAME%>">
        <html:text name="USER_DETAIL_FORM" property="detail.userData.lastName" maxlength="255"/>
    </app:authorizedForFunction>
    <app:notAuthorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.EDIT_USER_PROFILE_NAME%>">
        <bean:write name="USER_DETAIL_FORM" property="detail.userData.lastName" />
    </app:notAuthorizedForFunction>
</td></tr>

<tr>
<td><app:storeMessage key="shop.userProfile.text.userName"/> </td>
<td>
<bean:write name="USER_DETAIL_FORM" property="detail.userData.userName"/>
<html:hidden name="USER_DETAIL_FORM" property="detail.userData.userId"/>
</td>
</tr>

<tr>
<td><app:storeMessage key="shop.userProfile.text.language"/></td>
<td>
	<html:select name="USER_DETAIL_FORM" property="detail.languageData.shortDesc">
	   <html:options  collection="languages.vector" labelProperty="uiName" property="shortDesc" />
	</html:select>
</td>
</tr>

<%
String allowChangePassw =
  appUser.getUserAccount().getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_USER_CHANGE_PASSWORD);
if (!Utility.isSet(allowChangePassw) || Utility.isTrue(allowChangePassw)) {
%>
<tr>
<td><app:storeMessage key="shop.userProfile.text.currentPassword"/></td>
<td><html:password property="oldPassword" maxlength="30" /></td>
</tr>
<tr>
<td><app:storeMessage key="shop.userProfile.text.newPassword"/> </td>
<td>
<html:password name="USER_DETAIL_FORM" property="password" maxlength="30" />
</td>
</tr>
<tr>
<td><app:storeMessage key="shop.userProfile.text.confirmPassword"/> </td>
<td>
<html:password name="USER_DETAIL_FORM" property="confirmPassword" maxlength="30" />
</td>

</tr>

<% } %>

<tr>
<td><app:storeMessage key="shop.userProfile.text.companyName"/></td>
<td>
<bean:write name="USER_DETAIL_FORM" property="accountName" />
</td>
</tr>

<tr>
<td><app:storeMessage key="shop.userProfile.text.phone"/> </td><td>
<html:text name="USER_DETAIL_FORM" property="detail.phone.phoneNum" maxlength="30"/>
</td></tr>

<tr><td><app:storeMessage key="shop.userProfile.text.fax"/> </td>
<td>
<html:text name="USER_DETAIL_FORM" property="detail.fax.phoneNum" maxlength="30"/>
</td></tr>

<tr><td><app:storeMessage key="shop.userProfile.text.mobile"/> </td>
<td>
<html:text name="USER_DETAIL_FORM" property="detail.mobile.phoneNum" maxlength="30"/>
</td></tr>

<tr>
<td><app:storeMessage key="shop.userProfile.text.email"/> </td>
<td>
<html:text name="USER_DETAIL_FORM" property="detail.emailData.emailAddress"
size="40" maxlength="255"/>
</td></tr>

<tr valign=top>
<td><app:storeMessage key="shop.userProfile.text.contactAddress"/></td>
</tr>
<tr>
<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
<td>
<table>
  <tr>
  <td><app:storeMessage key="shop.userProfile.text.address1"/>: </td>
  <td>
  <html:text size="20" maxlength="80"  name="USER_DETAIL_FORM" property="detail.addressData.address1"/>
  </td>
  </tr><tr>
  <td><app:storeMessage key="shop.userProfile.text.address2"/>: </td>
  <td>
  <html:text size="20" maxlength="80" name="USER_DETAIL_FORM" property="detail.addressData.address2"/>
  </td>
  </tr><tr>
  <td><app:storeMessage key="shop.userProfile.text.city"/>: </td>
  <td>
  <html:text size="20" maxlength="80"  name="USER_DETAIL_FORM" property="detail.addressData.city"/>
  </td>
  </tr>
  <% if (appUser.getUserStore().isStateProvinceRequired()) { %>
  <tr>
  <td><app:storeMessage key="shop.userProfile.text.stateProvince"/>: </td>
  <td>
  <html:text size="20" maxlength="80" name="USER_DETAIL_FORM" property="detail.addressData.stateProvinceCd" />
  </td>
  </tr>
  <%} %>
  <tr>
  <td><app:storeMessage key="shop.userProfile.text.zipPostalCode"/>: </td>
  <td>
  <html:text size="20" maxlength="80" name="USER_DETAIL_FORM" property="detail.addressData.postalCode"/>
  </td>
  </tr><tr>
  <td><app:storeMessage key="shop.userProfile.text.country"/>: </td>
  <td>
  <html:select name="USER_DETAIL_FORM" property="detail.countryData.countryCode">
  <html:options  collection="country.vector" labelProperty="uiName" property="countryCode" />
  </html:select>

  </td>
  </tr>
</table>

</td>
</tr>

<tr valign=top>
<td><app:storeMessage key="shop.userProfile.text.billingAddress"/></td>
</tr>
<tr>
<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
<td>
<table>
  <tr>
  <td><app:storeMessage key="shop.userProfile.text.address1"/>: </td>
  <td>
  <bean:write name="USER_DETAIL_FORM" property="accountAddress.address1"/>
  </td>
  </tr><tr>
  <td><app:storeMessage key="shop.userProfile.text.address2"/>: </td>
  <td>
  <bean:write name="USER_DETAIL_FORM" property="accountAddress.address2"/>
  </td>
  </tr><tr>
  <td><app:storeMessage key="shop.userProfile.text.city"/>: </td>
  <td>
  <bean:write name="USER_DETAIL_FORM" property="accountAddress.city"/>
  </td>
  </tr>
  <% if (appUser.getUserStore().isStateProvinceRequired()) { %>
  <tr>
  <td><app:storeMessage key="shop.userProfile.text.stateProvince"/>: </td>
  <td>
  <bean:write name="USER_DETAIL_FORM" property="accountAddress.stateProvinceCd"/>
  </td>
  </tr>
  <% } %>
  <tr>
  <td><app:storeMessage key="shop.userProfile.text.zipPostalCode"/>: </td>
  <td>
  <bean:write name="USER_DETAIL_FORM" property="accountAddress.postalCode"/>
  </td>
  </tr><tr>
  <td><app:storeMessage key="shop.userProfile.text.country"/>: </td>
  <td>
  <bean:write name="USER_DETAIL_FORM" property="accountAddress.countryCd"/>
  </td>
  </tr>
</table>
</td>
</tr>

<tr>
<td>&nbsp;</td>
</tr>

<tr>
<td colspan="4" align="left">
<html:link href="javascript:{document.USER_DETAIL_FORM.reset();}">
  [<app:storeMessage key="shop.userProfile.text.resetFields"/>]
</html:link>
<html:hidden property="action" value="update_customer_profile"/>
<html:link href="javascript:{document.USER_DETAIL_FORM.submit();}">
  [<app:storeMessage key="global.action.label.submit"/>]
</html:link>
</td>
</table>
</html:form>


