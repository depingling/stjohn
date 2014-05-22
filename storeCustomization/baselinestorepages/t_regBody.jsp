<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>


<bean:define id="IMGPath" type="java.lang.String"
  name="pages.store.images"/>
<% CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER); %>

<div class="text">

<table width=769 cellSpacing=0 cellPadding=0 border=0 align=center>
<tr>
<TD class=tableoutline width=1><IMG src="<%=IMGPath%>/cw_spacer.gif"></TD>
<td class="top3dk" width="760"><img src="<%=IMGPath%>/cw_spacer.gif" height="23"></td>
<TD class=tableoutline width=1><IMG src="<%=IMGPath%>/cw_spacer.gif"></TD>
</tr>
</table>
<table width=769 cellSpacing=0 cellPadding=0 border=0 align=center>
<tr>
<TD class=tableoutline width=1><IMG src="<%=IMGPath%>/cw_spacer.gif"></TD>
<td class="top3dk" width="5"><img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="5"></td>
<td>

<html:form
  name="USER_DETAIL_FORM"
  action="/userportal/reg.do"
  scope="session" type="com.cleanwise.view.forms.UserMgrDetailForm"
>

<font color=red> <html:errors/> </font>

<br>
Register as a user for <b>
<bean:write name="ApplicationUser"
  property="userAccount.busEntity.shortDesc"/>
</b>.
<br>

<table width=750>
<tr>
<td class="blueformtext">
Login Name:
</td>
<td>
<html:text name="USER_DETAIL_FORM"
  property="detail.userData.userName" maxlength="15" />
</td>
</tr>
<tr>
<td class="blueformtext">
Password:
</td>
<td>
<html:password name="USER_DETAIL_FORM"
  property="password" maxlength="30" />
</td>
</tr>
<tr>
<td class="blueformtext">
Confirm Password:
</td>
<td>
<html:password name="USER_DETAIL_FORM"
  property="confirmPassword" />
</td>
</tr>
<tr>
<td class="bar_a" colspan=4>Contact Information</td>
</td>

<tr>
<td class="blueformtext">First Name:</td>
<td>
<html:text name="USER_DETAIL_FORM" property="detail.userData.firstName" maxlength="40" size="40" /><span class="reqind">*</span>
</td>

<td class="blueformtext">Phone:</td>
<td>
<html:text name="USER_DETAIL_FORM" property="detail.phone.phoneNum" maxlength="30" /><span class="reqind">*</span>
</td>
</tr>

<tr>
<td class="blueformtext">Last Name:</td>
<td>
<html:text name="USER_DETAIL_FORM" property="detail.userData.lastName" maxlength="40" size="40" /><span class="reqind">*</span>
</td>
<td class="blueformtext">Fax:</td>
<td>
<html:text name="USER_DETAIL_FORM" property="detail.fax.phoneNum" maxlength="30" />
</td>
</tr>

<tr>
<td class="blueformtext">Street Address 1:</td>
<td>
<html:text name="USER_DETAIL_FORM" property="detail.addressData.address1" maxlength="80" size="40" /><span class="reqind">*</span>
</td>
<td  class="blueformtext">Mobile:</td>
<td>
<html:text name="USER_DETAIL_FORM" property="detail.mobile.phoneNum" maxlength="30"/>
</td>
</tr>

<tr>
<td class="blueformtext">Street Address 2:</td>
<td>
<html:text name="USER_DETAIL_FORM" property="detail.addressData.address2" maxlength="80" size="40" />
</td>
<td class="blueformtext">Email:</td>
<td>
<html:text name="USER_DETAIL_FORM" property="detail.emailData.emailAddress" maxlength="255" size="40" />
</td>
</tr>

<tr>
<td  class="blueformtext">City:</td>
<td>
<html:text name="USER_DETAIL_FORM" property="detail.addressData.city" maxlength="40"/><span class="reqind">*</span>
</td>
<td colspan="2">&nbsp;</td>
</tr>

<tr>
<td class="blueformtext">Country:</td>
<td>
<html:select name="USER_DETAIL_FORM" property="detail.addressData.countryCd">
<html:option value=""><app:storeMessage  key="admin.select.country"/></html:option>
<html:options  collection="country.vector" labelProperty="uiName" property="shortDesc" />
</html:select><span class="reqind">*</span>
</td>
<td colspan="2">&nbsp;</td>
</tr>

<% if (appUser.getUserStore().isStateProvinceRequired()) { %>
<tr>
<td class="blueformtext">State/Province:</td>
<td>
<html:text size="20" maxlength="80" name="USER_DETAIL_FORM" property="detail.addressData.stateProvinceCd" /><span class="reqind">*</span>
</td>
<td colspan="2">&nbsp;</td>
</tr>
<%}%>

<tr>

<td class="blueformtext">Zip/Postal Code:</td>
<td>
<html:text name="USER_DETAIL_FORM" property="detail.addressData.postalCode" maxlength="15" /><span class="reqind">*</span>
</td>
<td>
        <html:submit property="action">
    <app:storeMessage  key="user.button.register"/>
  </html:submit>
</td>


</tr>

</table>

</html:form>

</td>
<td class="top3dk" width="5"><img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="5"></td>
<TD class=tableoutline width=1><IMG src="<%=IMGPath%>/cw_spacer.gif"></TD>
</tr>

<TR>
<TD colspan=6>
<IMG height=23  src="<%=IMGPath%>/cw_rootfooter.gif"   width=769></TD>
</TR>


</table>
</div>


