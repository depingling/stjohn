<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page import="com.cleanwise.view.forms.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>

<jsp:include flush='true' page="../general/checkBrowser.jsp"/>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<app:checkLogon/>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="v_userData" name="USER_DETAIL_FORM" property="detail.userData" 
  type="UserData"/>
<bean:define id="userId" name="USER_DETAIL_FORM" property="detail.userData.userId" type="java.lang.Integer"/>
<bean:define id="userType" name="USER_DETAIL_FORM" property="detail.userData.userTypeCd" />
<bean:define id="Location" value="user" type="java.lang.String" toScope="session"/>
<%
        String isMSIE = (String)session.getAttribute("IsMSIE");
        if (null == isMSIE) isMSIE = "";
%>

<html:html>

<head>
<link rel="stylesheet" href="../externals/styles.css">
<title>Application Administrator Home: User Detail</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<script language="JavaScript1.2">
<!--
function popLocate(pLoc, name, pDesc) {
var loc = pLoc + ".do?feedField=" + name + "&amp;feedDesc=" + pDesc;
locatewin = window.open(loc,"Locate", "menubar=no,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
locatewin.focus();

return false;
}

//-->
</script>

<% if ("Y".equals(isMSIE)) { %>
<script language="JavaScript" src="../externals/calendar.js"></script>
<% } else {  %>
<script language="JavaScript" src="../externals/calendarNS.js"></script>
<% }  %>

<body bgcolor="#FFFFFF">

<jsp:include flush='true' page="ui/admToolbar.jsp"/>
<jsp:include flush='true' page="ui/admUserToolbar.jsp"/>
<jsp:include flush='true' page="ui/loginInfo.jsp"/>

<% if ("Y".equals(isMSIE)) { %>
<iframe style="display:none; position:absolute; z-index:1; width:148; height:194" id="CalFrame"
    marginheight=0 marginwidth=0 noresize frameborder=0 scrolling=no src="../externals/calendar.html"></iframe>
<% }  %>

<div class="text">
<font color=red> <html:errors/> </font>
<html:form name="USER_DETAIL_FORM" action="adminportal/usermgrDetail.do"
scope="session" type="com.cleanwise.view.forms.UserMgrDetailForm">
<table cellpadding="2" cellspacing="0" border="0" width="769" class="mainbody">

<tr>
<td colspan=4 class="largeheader">User Detail</td>
</tr>

<tr>
<td><b>User&nbsp;Id:</b></td>
<td>
<bean:write name="USER_DETAIL_FORM" property="detail.userData.userId" scope="session"/>
<html:hidden property="detail.userData.userId"/>
</td>

<%
if( null == userId || 0 == userId.intValue() ) {
%>
<td><b>Password:</b><span class="reqind">*</span></td>
<td>
<html:password name="USER_DETAIL_FORM" property="password" maxlength="30" />
</td>
<%  } else {  %>
<td><b>New Password:</b></td>
<td>
<html:password name="USER_DETAIL_FORM" property="password" maxlength="30" />
</td>
<%  }  %>


</tr>
<tr>
<td><b>User Type:</b><span class="reqind">*</span></td>
<%
if( null == userId || 0 == userId.intValue() ) {
%>
<td>
<html:hidden property="change" value="" />
<html:select name="USER_DETAIL_FORM" property="detail.userData.userTypeCd" onchange="document.forms[0].change.value='type'; document.forms[0].submit();">
<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
<html:options  collection="Users.types.vector" property="value" />
</html:select>
</td>
<%  } else {  %>
<td>
<bean:write name="USER_DETAIL_FORM" property="detail.userData.userTypeCd" filter="true" />
</td>
<%  }  %>

<%
if( null == userId || 0 == userId.intValue() ) {
%>
<td><b>Confirm Password:</b><span class="reqind">*</span></td>
<td>
<html:password name="USER_DETAIL_FORM" property="confirmPassword" />
</td>
<%  } else {  %>
<td><b>Confirm New Password:</b></td>
<td>
<html:password name="USER_DETAIL_FORM" property="confirmPassword" />
</td>
<%  }  %>

<td rowspan=4><b>Preferred Language:</b><span class="reqind">*</span>
<html:select name="USER_DETAIL_FORM" property="detail.userData.prefLocaleCd">
<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
<html:options  collection="Users.locales.vector" property="value" />
</html:select>
<br>
<%@ include file="f_user_accounts_list.jsp" %>
</td>

</tr>
<tr>
<td><b>Login Name:</b><span class="reqind">*</span></td>
<td>
<html:text name="USER_DETAIL_FORM" property="detail.userData.userName" maxlength="15" />
</td>
<td><b>Status:</b><span class="reqind">*</span></td>
<td>
<html:select name="USER_DETAIL_FORM" property="detail.userData.userStatusCd">
<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
<html:options  collection="Users.status.vector" property="value" />
</html:select>
</td>
</tr>

<tr>
<td><b>User Active Date:</b><span class="reqind">*</span>
</td>
<td>
<% if ("Y".equals(isMSIE)) { %>
                <html:text name="USER_DETAIL_FORM" property="effDate" maxlength="10" />
                        <a href="#" onClick="event.cancelBubble=true; return ShowCalendar(document.forms[0].DEFSADD2, document.forms[0].effDate, null, -7300, 7300);" title="Choose Date"
                ><img name="DEFSADD2" src="../externals/images/showCalendar.gif" width=19 height=19 border=0 align=absmiddle style="position:relative" onmouseover="window.status='Choose Date';return true" onmouseout="window.status='';return true"></a>
                        <br><span class="smalltext">(mm/dd/yyyy)</span>
<% } else {  %>

<html:text name="USER_DETAIL_FORM" property="effDate" maxlength="10" />
<a href="javascript:show_calendar('forms[0].effDate');" onmouseover="window.status='Choose Date';return true;" onmouseout="window.status='';return true;" title="Choose Date"><img src="../externals/images/showCalendar.gif" width=19 height=19 border=0></a>
            
<br><span class="smalltext">(mm/dd/yyyy)</span>
<% }  %>
</td>
<td><b>User Inactive Date:</b></td>
<td>
<% if ("Y".equals(isMSIE)) { %>
                <html:text name="USER_DETAIL_FORM" property="expDate" maxlength="10"/>
                        <a href="#" onClick="event.cancelBubble=true; return ShowCalendar(document.forms[0].DEFSADD1, document.forms[0].expDate, null, -7300, 7300);" title="Choose Date"
                ><img name="DEFSADD1" src="../externals/images/showCalendar.gif" width=19 height=19 border=0 align=absmiddle style="position:relative" onmouseover="window.status='Choose Date';return true" onmouseout="window.status='';return true"></a>
            <br><span class="smalltext">(mm/dd/yyyy)</span>
<% } else {  %>
                <html:text name="USER_DETAIL_FORM" property="expDate" maxlength="10"/>
                        <a href="javascript:show_calendar('forms[0].expDate');" onmouseover="window.status='Date Picker';return true;" onmouseout="window.status='';return true;"><img src="../externals/images/showCalendar.gif" width=19 height=19 border=0></a>
            <br><span class="smalltext">(mm/dd/yyyy)</span>
<% }  %>
</td>
</tr>


<% if (RefCodeNames.USER_TYPE_CD.DISTRIBUTOR.equals(userType)) {%>
<tr>
        <td><b>Distribution Center Id:</b><span class="reqind">*</span></td>
        <td><html:text name="USER_DETAIL_FORM" property="distributionCenterId" size="2" maxlength="2"/></td>
        <td colspan="2">&nbsp;</td>
</tr>
<tr>
        <td><b>Label Height:</b></td>
        <td><html:text name="USER_DETAIL_FORM" property="manifestLabelHeight" /></td>
        <td><b>Label Width:</b></td>
<td>
<html:text name="USER_DETAIL_FORM" property="manifestLabelWidth" />
</td>
</tr>
<tr>
        <td><b>Label Type:</b></td>
        <td>
<html:select name="USER_DETAIL_FORM" property="manifestLabelType">
<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
<html:options  collection="MANIFEST_LABEL_TYPE_CD" property="value" />
</html:select>
        </td>
        <td><b>Label Print Mode:</b></td>
        <td>
<html:select name="USER_DETAIL_FORM" property="manifestLabelPrintMode">
<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
<html:options  collection="MANIFEST_LABEL_MODE_CD" property="value" />
</html:select>
        </td>
</tr>
<% } %>


<%  if (RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR.equals(userType) ||
		RefCodeNames.USER_TYPE_CD.ADMINISTRATOR.equals(userType) ||
		RefCodeNames.USER_TYPE_CD.CUSTOMER_SERVICE.equals(userType) ||
		RefCodeNames.USER_TYPE_CD.CRC_MANAGER.equals(userType)
	)
{%>
	<logic:iterate indexId="i" id="aStore" name="USER_DETAIL_FORM" property="stores.values" type="BusEntityData">
		<bean:define id="aStoreId" name="aStore" property="busEntityId"/>
		<tr>
		<td><bean:write name="aStore" property="shortDesc" /></td>
		<%String selectedStr = "stores.selected["+i+"]";%>
		<td><html:multibox name="USER_DETAIL_FORM" property="<%=selectedStr%>" value="true"/></td>
		</tr>
	</logic:iterate>
<% }  %>



<%
 if (RefCodeNames.USER_TYPE_CD.CUSTOMER_SERVICE.equals(userType) ||
     RefCodeNames.USER_TYPE_CD.CRC_MANAGER.equals(userType)) {
%>

<tr>
<td><b>Customer Service Role:</b></td>
<td>
<html:select name="USER_DETAIL_FORM" property="customerServiceRoleCd">
<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
<html:options  collection="CustomerService.role.vector" property="value" />
</html:select>
</td>
<td colspan="2">&nbsp;</td>
</tr>

<% }  %>

</table>

<table cellpadding="2" cellspacing="0" border="0" width="769" class="mainbody">
<tr>
<td colspan="4" class="largeheader"><br>Contact Information</td>
</tr>
<tr>
<td><b>First Name:</b><span class="reqind">*</span></td>
<td>
<html:text name="USER_DETAIL_FORM" property="detail.userData.firstName" maxlength="40" size="40" />
</td>

<td><b>Phone:</b><span class="reqind">*</span></td>
<td>
<html:text name="USER_DETAIL_FORM" property="detail.phone.phoneNum" maxlength="30" />
</td>
</tr>

<tr>
<td><b>Last Name:</b><span class="reqind">*</span></td>
<td>
<html:text name="USER_DETAIL_FORM" property="detail.userData.lastName" maxlength="40" size="40" />
</td>
<td><b>Fax:</b></td>
<td>
<html:text name="USER_DETAIL_FORM" property="detail.fax.phoneNum" maxlength="30" />
</td>
</tr>

<tr>
<td><b>Street Address 1:</b><span class="reqind">*</span></td>
<td>
<html:text name="USER_DETAIL_FORM" property="detail.addressData.address1" maxlength="80" size="40" />
</td>
<td><b>Mobile:</b></td>
<td>
<html:text name="USER_DETAIL_FORM" property="detail.mobile.phoneNum" maxlength="30"/>
</td>
</tr>

<tr>
<td><b>Street Address 2:</b></td>
<td>
<html:text name="USER_DETAIL_FORM" property="detail.addressData.address2" maxlength="80" size="40" />
</td>
<td><b>Email:</b></td>
<td>
<html:text name="USER_DETAIL_FORM" property="detail.emailData.emailAddress" maxlength="255" size="40" />
</td>
</tr>

<tr>
<td><b>City:</b><span class="reqind">*</span></td>
<td>
<html:text name="USER_DETAIL_FORM" property="detail.addressData.city" maxlength="40"/>
</td>
<td colspan="2">&nbsp;</td>
</tr>

<tr>
<td><b>Country:</b><span class="reqind">*</span></td>
<td>
<html:select name="USER_DETAIL_FORM" property="detail.addressData.countryCd">
<html:option value=""><app:storeMessage  key="admin.select.country"/></html:option>
<html:options  collection="countries.vector" property="value" />
</html:select>
</td>
<td colspan="2">&nbsp;</td>
</tr>

<tr>
<td><b>State/Province:</b><span class="reqind">*</span></td>
<td>
<html:text size="20" maxlength="80" name="USER_DETAIL_FORM" property="detail.addressData.stateProvinceCd" />
</td>
<td colspan="2">&nbsp;</td>
</tr>

<tr>

<td><b>Zip/Postal Code:</b><span class="reqind">*</span></td>
<td>
<html:text name="USER_DETAIL_FORM" property="detail.addressData.postalCode" maxlength="15" />
</td>

</tr>

<tr>
<td colspan="4" align="center">
<html:submit property="action">
<app:storeMessage  key="admin.button.saveUserDetail"/>
</html:submit>
<html:reset>
<app:storeMessage  key="admin.button.reset"/>
</html:reset>&nbsp;
</td>
</tr>

</table>

</html:form>

</div>

<% if ("Y".equals(isMSIE)) { %>
<script for=document event="onclick()">
<!--
document.all.CalFrame.style.display="none";
//-->
</script>
<% }  %>

<jsp:include flush='true' page="ui/admFooter.jsp"/>
</body>

</html:html>



