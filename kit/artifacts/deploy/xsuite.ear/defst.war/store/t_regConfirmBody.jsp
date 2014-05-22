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
Thank you.
 You are now registered as a user for <b>
<bean:write name="ApplicationUser"
  property="userAccount.busEntity.shortDesc"/>
</b>.
<br>
<bean:define id="emailaddr" name="USER_DETAIL_FORM"
 property="detail.emailData.emailAddress"
 type="java.lang.String" />

<table width=750>


<tr>
<td colspan=2>
<% if ( null != emailaddr && emailaddr.trim().length() > 0 ) { %>
A confirmation email has been sent to <%=emailaddr.trim()%> with your user name
and password.
<% } else { %>
Please call our Customer Service center at
<bean:write name="ApplicationUser" property="userStore.primaryPhone.phoneNum"/>
 to activate your login.
<% } %>

</td>
</tr>

<tr>
<td class="blueformtext">
Login Name:
</td>
<td>
<bean:write name="USER_DETAIL_FORM" property="detail.userData.userName" />
</td>

<tr>
<td class="bar_a" colspan=4>Contact Information</td>
</td>

<tr>
<td class="blueformtext">First Name:</td>
<td>
<bean:write name="USER_DETAIL_FORM" property="detail.userData.firstName" />
</td>

<td class="blueformtext">Phone:</td>
<td>
<bean:write name="USER_DETAIL_FORM" property="detail.phone.phoneNum" />
</td>
</tr>

<tr>
<td class="blueformtext">Last Name:</td>
<td>
<bean:write name="USER_DETAIL_FORM" property="detail.userData.lastName"  />
</td>
<td class="blueformtext">Fax:</td>
<td>
<bean:write name="USER_DETAIL_FORM" property="detail.fax.phoneNum" />
</td>
</tr>

<tr>
<td class="blueformtext">Street Address 1:</td>
<td>
<bean:write name="USER_DETAIL_FORM" property="detail.addressData.address1"  />
</td>
<td  class="blueformtext">Mobile:</td>
<td>
<bean:write name="USER_DETAIL_FORM" property="detail.mobile.phoneNum" />
</td>
</tr>

<tr>
<td class="blueformtext">Street Address 2:</td>
<td>
<bean:write name="USER_DETAIL_FORM" property="detail.addressData.address2" />
</td>
<td class="blueformtext">Email:</td>
<td>
<bean:write name="USER_DETAIL_FORM" property="detail.emailData.emailAddress" />
</td>
</tr>

<tr>
<td  class="blueformtext">City:</td>
<td>
<bean:write  name="USER_DETAIL_FORM" property="detail.addressData.city" />
</td>
<td colspan="2">&nbsp;</td>
</tr>

<tr>
<td class="blueformtext">Country:</td>
<td>
<bean:write name="USER_DETAIL_FORM" property="detail.addressData.countryCd"/>
</td>
<td colspan="2">&nbsp;</td>
</tr>
<% if (appUser.getUserStore().isStateProvinceRequired()) { %>
<tr>
<td class="blueformtext">State/Province:</td>
<td>
<bean:write name="USER_DETAIL_FORM" property="detail.addressData.stateProvinceCd" />
</td>
<td colspan="2">&nbsp;</td>
</tr>
<%} %>

<tr>

<td class="blueformtext">Zip/Postal Code:</td>
<td>
<bean:write name="USER_DETAIL_FORM" property="detail.addressData.postalCode" />
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


