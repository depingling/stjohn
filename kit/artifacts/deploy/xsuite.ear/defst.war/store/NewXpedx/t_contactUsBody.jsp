<%@ page language="java" %>
<%@page import="com.cleanwise.view.utils.*" %>
<%@page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@page import="com.cleanwise.service.api.*" %>
<%@page import="com.cleanwise.service.api.session.*" %>
<%@page import="com.cleanwise.service.api.value.*" %>
<%@page import="com.cleanwise.service.api.util.*" %>
<%@page import="com.cleanwise.view.utils.*" %>

<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="IMGPath" type="java.lang.String"
  name="pages.store.images"/>

<bean:define id="AccountId" type="java.lang.String" name="pages.account.id"/>
<%CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);%>
<html:form name="NEW_XPEDX_CONTACT_US_FORM" action="userportal/new_xpedx_contactus.do">
<html:hidden property="action" value="send_email"/>
<%-----------------bread crumb-----------------------%>

<table class="breadcrumb">
  <tr class="breadcrumb">
    <td class="breadcrumb"><a class="breadcrumb" href="../userportal/msbsites.do?action=goHome"><app:storeMessage key="breadcrumb.label.home"/></a></td>
    <td class="breadcrumb"><span class="breadcrumb">&nbsp;&gt;&nbsp;</span></td>
    <td class="breadcrumb">
	<%--<a class="breadcrumb" href="new_xpedx_contactus.do"><app:storeMessage key="shop.menu.main.contactUs"/></a>--%>
	<div class="breadcrumbCurrent"><app:storeMessage key="shop.menu.main.contactUs"/></div>
	</td>
  </tr>
  <tr><td>&nbsp;</td></tr>

</table>

<table border="0" cellpadding="2" cellspacing="2" width="1%">

<logic:present name="pages.contactus.text">
<tr>
    <td colspan="4"><b><app:storeMessage key="newxpdex.contactUs.label.contactByPhone"/></b></td>
</tr>
<tr>
    <td colspan="4"><app:custom pageElement="pages.contactus.text"/></td>
</tr>
<%--
<tr>
    <td colspan="4">
<logic:iterate id="contact" indexId="idx" name="<%=Constants.APP_USER%>" property="contactUsList" type="com.cleanwise.view.utils.ContactUsInfo">
<%
String pNum = contact.getPhone();
String callHours = contact.getCallHours();
%>
<app:storeMessage key="contactus.text.phone:"/><%=pNum%><br>
<app:storeMessage key="pages.contact.msg" arg0="<%=callHours%>"/><br><br>
</logic:iterate>
</td>
</tr>
--%>
<tr><td>&nbsp;</td></tr>
</logic:present>
<tr>
    <td colspan="4"><b><app:storeMessage key="newxpdex.contactUs.label.contactOnline"/></b></td>
</tr>
<tr><td>&nbsp;</td></tr>
<tr>
    <td colspan="4"><app:storeMessage key="newxpdex.contactUs.label.header"/></td>
</tr>
<tr><td>&nbsp;</td></tr>
<tr>
    <td><nobr><font color="red">*</font>&nbsp;<app:storeMessage key="newxpdex.contactUs.label.from"/>:</nobr></td>
    <td colspan="3"><html:text maxlength="100" name="NEW_XPEDX_CONTACT_US_FORM" property="from" style="width:100%"/></td>
</tr>
<tr>
    <td align="right"><nobr><font color="red">*</font>&nbsp;<app:storeMessage key="newxpdex.contactUs.label.email"/>:</nobr></td>
    <td><html:text maxlength="50" name="NEW_XPEDX_CONTACT_US_FORM" property="email" style="width:150px;"/></td>
    <td align="right"><nobr>&nbsp;<app:storeMessage key="newxpdex.contactUs.label.phone"/>:</nobr></td>
    <td><html:text maxlength="50" name="NEW_XPEDX_CONTACT_US_FORM" property="phone" style="width:150px;"/></td>
</tr>
<tr>
    <td align="right"><nobr><app:storeMessage key="newxpdex.contactUs.label.order"/>:</nobr></td>
    <td><html:text maxlength="50" name="NEW_XPEDX_CONTACT_US_FORM" property="order" style="width:150px;"/></td>
</tr>
<tr>
    <td align="right"><font color="red">*</font></td>
    <td colspan="3"><html:select name="NEW_XPEDX_CONTACT_US_FORM" property="topic" style="width:100%;">
<option value=""><app:storeMessage key="newxpdex.contactUs.label.selectTopic"/></option>
<html:options  collection="contact.us.topics" property="propertyId" labelProperty="value" />
    </html:select></td>
</tr>
<tr><td></td></tr>
<tr>
    <td>&nbsp;</td>
    <td colspan="3" valign="bottom"><app:storeMessage key="newxpdex.contactUs.label.questionsComment"/>:</td>
</tr>
<tr>
    <td>&nbsp;</td>
    <td colspan="3"><html:textarea name="NEW_XPEDX_CONTACT_US_FORM" property="comment" style="width:100%;height:100px;"/></td>
</tr>
<tr>
    <td>&nbsp;</td>
    <td colspan="3" style="color:#FF0000;">
<app:storeMessage key="newxpdex.label.indicatesRequiredField"/>
    </td>
</tr>
<%
String leftButtonURL = ClwCustomizer.getSIP(request,"buttonLeft.png");
String rightButtonURL = ClwCustomizer.getSIP(request,"buttonRight.png");
%><tr>
    <td>&nbsp;</td>
    <td align="right" colspan="3">
<table cellpadding="0" cellspacing="0" border="0"><tr>
<td align="right" valign="middle"><img src="<%=leftButtonURL%>" border="0"></td>
<td align="center" valign="middle" nowrap class="xpdexGradientButton"><a href="#"
    class="xpdexGradientButton"
    onclick="return submitForm();"><app:storeMessage key="global.action.label.submit"/></a></td>
<td align="left" valign="middle"><img src="<%=rightButtonURL%>" border="0"></td>
</tr></table>
    </td>
</tr>
</table>
<table align="center">
<tr><td align="center" colspan="4" style="color:#FF0000"><html:errors/></td></tr>
<logic:notEmpty name="successMessage" scope="request">
<tr><td colspan="4" style="color:#003399; font-weight:bold"><bean:write name="successMessage" scope="request"/></td></tr>
</logic:notEmpty>
</table>
</html:form>
<script language="javascript" type="text/javascript">
function submitForm() {
    var frm = document.forms[0];
    if (frm) {
        frm.submit();
    }
}
</script>
