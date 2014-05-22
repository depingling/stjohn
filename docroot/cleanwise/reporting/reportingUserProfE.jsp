<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="java.util.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="IMGPath" type="java.lang.String"
  name="pages.store.images"/>
<%
String storeDir=ClwCustomizer.getStoreDir();
CleanwiseUser user = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
String reportingToolbar = "/reporting/ui/reportingToolbar.jsp";
boolean showProfile = false; //modified for JD reporting user; need to set to correct value
%>

<span class="genericerror"><html:errors/></span>

  <TABLE width=750 cellSpacing=5 cellPadding=0 align=center border=0>

  <bean:define id="toolBarTab" type="java.lang.String" value="profile" toScope="request"/>


<html:form name="USER_DETAIL_FORM"
  action="reporting/reportingUserProfile.do"
  scope="session" type="com.cleanwise.view.forms.UserMgrDetailForm">

<tr>
</tr>
<TR>
  <TD colspan=3><IMG height=21
    src="<%=IMGPath%>/cw_account_profileyourprofile.gif"></TD></TR>
<TR>

<tr><td>First Name: </td>
<td>
<bean:write name="USER_DETAIL_FORM" property="detail.userData.firstName"/>
</td></tr>

<tr><td>Last Name: </td>
<td>
<bean:write name="USER_DETAIL_FORM" property="detail.userData.lastName" />
</td></tr>

<tr>
<td>Login Name: </td>
<td>
<bean:write name="USER_DETAIL_FORM" property="detail.userData.userName"/>
<html:hidden name="USER_DETAIL_FORM" property="detail.userData.userId"/>
</td>
</tr>

<tr>
<td>New Password: </td>
<td>
<html:password name="USER_DETAIL_FORM" property="password" maxlength="30" />
</td>
</tr>
<tr>
<td>Confirm Password: </td>
<td>
<html:password name="USER_DETAIL_FORM" property="confirmPassword" maxlength="30" />
</td>
</tr>
<% if (showProfile) {%>
<tr>
<td>Phone: </td><td>
<html:text name="USER_DETAIL_FORM" property="detail.phone.phoneNum" maxlength="30"/>
</td></tr>

<tr><td>Fax: </td>
<td>
<html:text name="USER_DETAIL_FORM" property="detail.fax.phoneNum" maxlength="30"/>
</td></tr>

<tr><td>Mobile: </td>
<td>
<html:text name="USER_DETAIL_FORM" property="detail.mobile.phoneNum" maxlength="30"/>
</td></tr>

<tr>
<td>Email: </td>
<td>
<html:text name="USER_DETAIL_FORM" property="detail.emailData.emailAddress"
size="40" maxlength="255"/>
</td></tr>

<tr valign=top>
<td>Contact&nbsp;Address</td>
</tr>
<tr>
<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
<td>
<table>
  <tr>
  <td>Address 1: </td>
  <td>
  <html:text size="20" maxlength="80"  name="USER_DETAIL_FORM" property="detail.addressData.address1"/>
  </td>
  </tr><tr>
  <td>Address 2: </td>
  <td>
  <html:text size="20" maxlength="80" name="USER_DETAIL_FORM" property="detail.addressData.address2"/>
  </td>
  </tr><tr>
  <td>City: </td>
  <td>
  <html:text size="20" maxlength="80"  name="USER_DETAIL_FORM" property="detail.addressData.city"/>
  </td>
  </tr><tr>
  <td>State/Province: </td>
  <td>
  <html:text size="20" maxlength="80" name="USER_DETAIL_FORM" property="detail.addressData.stateProvinceCd" />
  </td>
  </tr><tr>
  <td> Zip/Postal Code:</td>
  <td>
  <html:text size="20" maxlength="80" name="USER_DETAIL_FORM" property="detail.addressData.postalCode"/>
  </td>
  </tr><tr>
  <td> Country:</td>
  <td>
  <html:select name="USER_DETAIL_FORM" property="detail.addressData.countryCd">
  <html:option value=""><app:storeMessage  key="admin.select.country"/></html:option>
  <html:options  collection="countries.vector" property="value" />
  </html:select>
  </td>
  </tr>
</table>

</td>
</tr>
<% } %>

<tr>
<td>&nbsp;</td>
</tr>

<tr>
<td colspan="4" align="left" class='tableheader'>
<html:link href="javascript:{document.USER_DETAIL_FORM.reset();}">[Reset fields]</html:link>
<html:hidden property="action" value="update_customer_profile"/>
<html:link href="javascript:{document.USER_DETAIL_FORM.submit();}">[Submit]</html:link>
</td>
</html:form>

  </tr>
</table>






