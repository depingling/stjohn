<%@page language="java" %>
<%@page import="com.cleanwise.view.utils.*" %>
<%@page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>
<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>
<bean:define id="cipclass" type="java.lang.String" value = "rtext2"/>
<bean:define id="appUser" name="ApplicationUser" type="com.cleanwise.view.utils.CleanwiseUser"/>
<bean:define id="theForm" name="USER_DETAIL_FORM"  type="com.cleanwise.view.forms.UserMgrDetailForm"/>
<html:form styleId="newProfileForm" action="/userportal/customerAccountManagementProfileNewXpedx.do" method="post">
<input type="hidden" name="action"/>

<table border="0" cellpadding="2" cellspacing="2">
    <tr>
        <td colspan="4">
        <table class="breadcrumb">
        <tr class="breadcrumb">
            <td class="breadcrumb"><a class="breadcrumb" href="../userportal/msbsites.do?action=goHome"><app:storeMessage key="breadcrumb.label.home"/></a></td>
            <td class="breadcrumb"><span class="breadcrumb">&nbsp;&gt;&nbsp;</span></td>
            <td class="breadcrumb">
			<%--<a class="breadcrumb" href="customerAccountManagementProfileNewXpedx.do"><app:storeMessage key="newxpdex.userProfile.header"/></a>--%>
			<div class="breadcrumbCurrent"><app:storeMessage key="newxpdex.userProfile.header"/></div>
			</td>
        </tr>
        <tr><td>&nbsp;</td></tr>
        </table>
        </td>
    </tr>
	<%--
    <tr>
        <td colspan="2" align="center"><b><app:storeMessage key="newxpdex.userProfile.header"/></b></td>
    </tr>
	--%>
    <tr>
        <td></td>
        <td align="right"><app:storeMessage key="newxpdex.userProfile.label.userName"/>:</td>
        <td>&nbsp;<bean:write name="USER_DETAIL_FORM" property="detail.userData.userName"/></td>
    </tr>
    <tr>
        <td width="1%"></td>
        <td width="1%" align="right"><app:storeMessage key="newxpdex.userProfile.label.language"/>:</td>
        <td width="1%">&nbsp;<html:select name="USER_DETAIL_FORM" property="detail.userData.prefLocaleCd" style="width:175px;">
            <html:options  collection="Users.locales.vector" property="value" />
        </html:select></td>
        <td width="*">&nbsp;</td>
    </tr>
    <tr>
        <td></td>
        <td align="right"><app:storeMessage key="shop.userProfile.text.phone"/>:</td>
        <td width="1%">&nbsp;<html:text property="detail.phone.phoneNum" style="width:175px;"/></td>
    </tr>
    <tr>
        <td></td>
        <td align="right"><app:storeMessage key="shop.userProfile.text.mobile"/>:</td>
        <td>&nbsp;<html:text name="USER_DETAIL_FORM" property="detail.mobile.phoneNum" style="width:175px;"/></td>
    </tr>
    <tr>
        <td></td>
        <td align="right"><app:storeMessage key="shop.userProfile.text.fax"/>:</td>
        <td>&nbsp;<html:text name="USER_DETAIL_FORM" property="detail.fax.phoneNum" style="width:175px;"/></td>
    </tr>
    <tr>
        <td></td>
        <td align="right"><app:storeMessage key="shop.userProfile.text.email"/>:</td>
        <td>&nbsp;<html:text name="USER_DETAIL_FORM" property="detail.emailData.emailAddress" style="width:175px;"/></td>
    </tr>
    <tr><td></td><td></td><td>
<table><tr><td>
<%
    String leftButtonURL = ClwCustomizer.getSIP(request,"buttonLeft.png");
    String rightButtonURL = ClwCustomizer.getSIP(request,"buttonRight.png");
%>
<table cellpadding="0" cellspacing="0" border="0"><tr>
<td align="right" valign="middle"><img src="<%=leftButtonURL%>" border="0"></td>
<td align="center" valign="middle" nowrap class="xpdexGradientButton"><a href="#"
    class="xpdexGradientButton"
    onclick="return submitUpdateProfile();"><app:storeMessage key="newxpdex.userProfile.label.updateUserProfile"/></a></td>
<td align="left" valign="middle"><img src="<%=rightButtonURL%>" border="0"></td>
</tr></table></td><td>

<%
    AccountData accD = appUser.getUserAccount();
	String allowChangePass = accD.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_USER_CHANGE_PASSWORD);
	if(allowChangePass.equals("true")){
%>
<table cellpadding="0" cellspacing="0" border="0"><tr id="panel5">
<td align="right" valign="middle"><img src="<%=leftButtonURL%>" border="0"></td>
<td align="center" valign="middle" nowrap class="xpdexGradientButton"><a href="#"
    class="xpdexGradientButton"
    onclick="return showPassword();"><app:storeMessage key="global.action.label.changePassword"/></a></td>
<td align="left" valign="middle"><img src="<%=rightButtonURL%>" border="0"></td>
</tr></table>
<%}%>
</td></tr></table>
    </td></tr>
	<tr><td>&nbsp;</td></tr>
<%
String visibility = request.getAttribute("changePasswordError") == null ? "hidden" : "visible";
%>
    <tr id="panel1" style="visibility:<%=visibility%>;">
        <td colspan="2" align="right"><nobr><font color="red">*</font><app:storeMessage
            key="newxpdex.userProfile.label.oldPassword"/>:</nobr></td>
        <td>&nbsp;<html:password name="USER_DETAIL_FORM" property="oldPassword" style="width:175px;"/></td>
    </tr>
    <tr id="panel2" style="visibility:<%=visibility%>;">
        <td colspan="2" align="right"><nobr><font color="red">*</font><app:storeMessage
            key="newxpdex.userProfile.label.newPassword"/>:</nobr></td>
        <td>&nbsp;<html:password name="USER_DETAIL_FORM" property="password" style="width:175px;"/></td>
    </tr>
    <tr id="panel3" style="visibility:<%=visibility%>;">
        <td colspan="2" align="right"><nobr><font color="red">*</font><app:storeMessage
            key="newxpdex.userProfile.label.confirmNewPassword"/>:</nobr></td>
        <td>&nbsp;<html:password name="USER_DETAIL_FORM" property="confirmPassword" style="width:175px;"/></td>
    </tr>
    <tr id="panel4" style="visibility:<%=visibility%>;"><td colspan="2"></td><td align="center" width="1%">
<table><tr><td>&nbsp;&nbsp;&nbsp;</td><td>
<table cellpadding="0" cellspacing="0" border="0"><tr>
<td align="right" valign="middle"><img src="<%=leftButtonURL%>" border="0"></td>
<td align="center" valign="middle" nowrap class="xpdexGradientButton"><a href="#"
    class="xpdexGradientButton"
    onclick="return submitCancelPassword();"><app:storeMessage key="global.action.label.cancel"/></a></td>
<td align="left" valign="middle"><img src="<%=rightButtonURL%>" border="0"></td>
</tr></table></td><td>
<table cellpadding="0" cellspacing="0" border="0"><tr>
<td align="right" valign="middle"><img src="<%=leftButtonURL%>" border="0"></td>
<td align="center" valign="middle" nowrap class="xpdexGradientButton"><a href="#"
    class="xpdexGradientButton"
    onclick="return submitChangePassword();"><app:storeMessage key="global.action.label.save"/></a></td>
<td align="left" valign="middle"><img src="<%=rightButtonURL%>" border="0"></td>
</tr></table></td></tr></table>
    </td></tr>
	<tr id="panel6" style="visibility:<%=visibility%>;">
    <td>&nbsp;</td>
    <td colspan="3" style="color:#FF0000;">
<app:storeMessage key="newxpdex.label.indicatesRequiredField"/>
    </td>
</tr>
<%
Object errors =request.getAttribute("org.apache.struts.action.ERROR");
if(request.getAttribute("changePasswordError")!=null || errors!=null){ %>
    <tr id="panel7"><td colspan="4" align="center"><font color="red"><html:errors/></font></td></tr>
<%}else{%>
<logic:notEmpty name="successMessage" scope="request">

    <tr id="panel7"><td colspan="4" align="center"><font color="blue"><bean:write name="successMessage" scope="request"/></font></td></tr>

</logic:notEmpty>
<%}%>
</table>

</html:form>
<script type="text/javascript" language="javascript">
function submitCancelPassword() {
    var frm = document.getElementById('newProfileForm');
    if (frm) {
        frm.elements['action'].value = 'cancelPassword';
        frm.submit();
    }
    return false;
}
function submitChangePassword() {
    var frm = document.getElementById('newProfileForm');
    if (frm) {
        frm.elements['action'].value = 'updatePassword';
        frm.submit();
    }
    return false;
}
function submitUpdateProfile() {
    var frm = document.getElementById('newProfileForm');
    if (frm) {
        frm.elements['action'].value = 'updateProfile';
        frm.submit();
    }
    return false;
}
function showPassword() {
    setObjVisibility('panel1', 'visible');
    setObjVisibility('panel2', 'visible');
    setObjVisibility('panel3', 'visible');
    setObjVisibility('panel4', 'visible');
	setObjVisibility('panel5', 'hidden');
	setObjVisibility('panel6', 'visible');
	setObjVisibility('panel7', 'hidden');
}
function setObjVisibility(id, value) {
    var panel = document.getElementById(id);
    if (panel) {
        panel.style.visibility = value;
    }
}
</script>
