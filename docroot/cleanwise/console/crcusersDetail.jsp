<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page import="com.cleanwise.view.forms.*" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<% boolean clwSwitch = ClwCustomizer.getClwSwitch(); %>

<app:checkLogon/>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<html:html>

<head>
<link rel="stylesheet" href="../externals/styles.css">
<title>Console Home: Users</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

</head>

<body>

<jsp:include flush='true' page="ui/consoleToolbar.jsp"/>
<jsp:include flush='true' page="ui/loginInfo.jsp"/>

<div class="text">
<center>
<font color=red>
<html:errors/>
</font>
</center>

<table cellspacing="0" border="0" width="769"  class="mainbody">
<html:form name="USER_DETAIL_FORM" scope="session"
 action="console/crcusersDetail.do"
 scope="session" 
 type="com.cleanwise.view.forms.UserMgrDetailForm">

<tr>
<td colspan=4 class="largeheader">User Detail</td>
</tr>

<tr>
<td><b>Mod By:</b></td>
<td align=left><bean:write name="USER_DETAIL_FORM" property="detail.userData.modBy"/></td>
</tr>

<tr>
<td><b>Mod Date:</b></td>
<td align=left><bean:write name="USER_DETAIL_FORM" property="detail.userData.modDate"/>
</td>
</tr>

<tr>
<td><b>User&nbsp;Id:</b></td>
<td>
<bean:write name="USER_DETAIL_FORM" property="detail.userData.userId" scope="session"/>
<html:hidden property="detail.userData.userId"/>
</td>

<bean:define id="vUserTypeCd" 
  name="USER_DETAIL_FORM" property="detail.userData.userTypeCd" 
  type="java.lang.String" />


<td colspan=2>
<% if (
vUserTypeCd.equals(RefCodeNames.USER_TYPE_CD.CUSTOMER) ||
vUserTypeCd.equals(RefCodeNames.USER_TYPE_CD.REPORTING_USER) ||
vUserTypeCd.equals(RefCodeNames.USER_TYPE_CD.MSB)
) { %>
<table><tr>
<td><b>Password:</b></td>
<td>
<html:password name="USER_DETAIL_FORM" property="password" maxlength="30" />
</td>
</tr><tr>

<td><b>New Password:</b></td>
<td>
<html:password name="USER_DETAIL_FORM" property="confirmPassword" maxlength="30" />
</td>
<td>
<html:submit property="action">
<app:storeMessage  key="admin.button.updatePassword"/>
</html:submit>
</td>
</tr></table>
<% } %>

</tr>
<tr>
<td><b>User Type:</b></td>

<td>
<bean:write name="USER_DETAIL_FORM" property="detail.userData.userTypeCd" />
</td>


</tr>

<tr>
<td><b>Login Name:</b></td>
<td>
<bean:write name="USER_DETAIL_FORM" property="detail.userData.userName"/>
</td>
<td><b>Status:</b>
<bean:write name="USER_DETAIL_FORM" property="detail.userData.userStatusCd"/>
<bean:define id="sts" name="USER_DETAIL_FORM" 
  property="detail.userData.userStatusCd"/>

<% if (
vUserTypeCd.equals(RefCodeNames.USER_TYPE_CD.CUSTOMER) ||
vUserTypeCd.equals(RefCodeNames.USER_TYPE_CD.REPORTING_USER) ||
vUserTypeCd.equals(RefCodeNames.USER_TYPE_CD.MSB)
) { %>

&nbsp;&nbsp;
<html:submit property="action">
<% if (null == sts) { %>
<app:storeMessage  key="admin.button.setToActive"/>
<% } else if (sts.equals(RefCodeNames.USER_STATUS_CD.ACTIVE)) { %>
<app:storeMessage  key="admin.button.setToInactive"/>
<% } else { %>
<app:storeMessage  key="admin.button.setToActive"/>
<% } %>
</html:submit>


<% } %>

</td>

<bean:size id="v_userAccountsSize"  name="USER_DETAIL_FORM" 
  property="detail.userAccountRights" />

<logic:greaterThan name="v_userAccountsSize" value="0">
<tr>
<td>
<b>User Accounts</b>
<ul>
<logic:iterate id="arrele" name="USER_DETAIL_FORM" 
  property="rightsForms"
  type="UserRightsForm">
<li><bean:write name="arrele" property="accountData.shortDesc"/></li>
</logic:iterate>
</ul>
</td>
</tr>
</logic:greaterThan>

</tr>


</table>

<table cellpadding="2" cellspacing="0" border="0" width="769" class="mainbody">
<tr>
<td colspan="4" class="largeheader"><br>Contact Information</td>
</tr>
<tr>
<td><b>First Name:</b></td>
<td>
<bean:write name="USER_DETAIL_FORM" property="detail.userData.firstName" />
</td>

<td><b>Phone:</b></td>
<td>
<bean:write name="USER_DETAIL_FORM" property="detail.phone.phoneNum" />
</td>
</tr>

<tr>
<td><b>Last Name:</b></td>
<td>
<bean:write name="USER_DETAIL_FORM" property="detail.userData.lastName"/>
</td>
<td><b>Fax:</b></td>
<td>
<bean:write name="USER_DETAIL_FORM" property="detail.fax.phoneNum"/>
</td>
</tr>

<tr>
<td><b>Street Address 1:</b></td>
<td>
<bean:write name="USER_DETAIL_FORM" property="detail.addressData.address1"/>
</td>
<td><b>Mobile:</b></td>
<td>
<bean:write name="USER_DETAIL_FORM" property="detail.mobile.phoneNum"/>
</td>
</tr>

<tr>
<td><b>Street Address 2:</b></td>
<td>
<bean:write name="USER_DETAIL_FORM" property="detail.addressData.address2"/>
</td>
<td><b>Email:</b></td>
<td>
<bean:write name="USER_DETAIL_FORM" property="detail.emailData.emailAddress"/>
</td>
</tr>

<tr>
<td><b>City:</b></td>
<td>
<bean:write name="USER_DETAIL_FORM" property="detail.addressData.city"/>
</td>
<td colspan="2">&nbsp;</td>
</tr>

<tr>
<td><b>Country:</b></td>
<td>
<bean:write name="USER_DETAIL_FORM" property="detail.addressData.countryCd"/>
</td>
<td colspan="2">&nbsp;</td>
</tr>

<tr>
<td><b>State/Province:</b></td>
<td>
<bean:write name="USER_DETAIL_FORM" property="detail.addressData.stateProvinceCd" />
</td>
<td colspan="2">&nbsp;</td>
</tr>

<tr>

<td><b>Zip/Postal Code:</b></td>
<td>
<bean:write name="USER_DETAIL_FORM" property="detail.addressData.postalCode"/>
</td>

</tr>

</table>



</html:form>

</div>

<jsp:include flush='true' page="ui/consoleFooter.jsp"/>

</body>

</html:html>






