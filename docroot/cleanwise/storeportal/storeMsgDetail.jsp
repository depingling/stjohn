<%@ page language="java"%>
<%@ page import="com.cleanwise.view.utils.Constants"%>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser"%>
<%@ page import="com.cleanwise.service.api.util.Utility"%>
<%@ page import="com.cleanwise.service.api.value.*"%>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames"%>
<%@ page import="com.cleanwise.service.api.util.I18nUtil"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app"%>
<%@include file="/externals/calendar/calendar.jsp"%>
<bean:define id="theForm" name="STORE_ADMIN_MSG_FORM"
	type="com.cleanwise.view.forms.StoreMsgMgrForm" /><%
	boolean isPublished = Utility.isTrue(theForm.getMessageDetail().getPublished());
	boolean isShowPreview = theForm.isShowPreview(); 
%><%if (isShowPreview) {%>
<link href="<%=request.getContextPath()%>/externals/esw/message_preview.css" rel="Stylesheet" type="text/css" media="all">
<!--[if IE 8]><link href="<%=request.getContextPath()%>/externals/esw/ie8.css" rel="stylesheet" type="text/css" media="screen" /><![endif]-->
<!--[if IE 7]><link href="<%=request.getContextPath()%>/externals/esw/ie7.css" rel="stylesheet" type="text/css" media="screen" /><![endif]-->
<!--[if IE 6]><link href="<%=request.getContextPath()%>/externals/esw/ie6.css" rel="stylesheet" type="text/css" media="screen" /><![endif]-->
<script type="text/javascript" src="<%=request.getContextPath()%>/externals/jquery/1.4.4/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/externals/jqueryui/1.8.9/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/externals/esw/library.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/externals/esw/scripts.js"></script>
<%}%>
<div class="text"><html:form name="STORE_ADMIN_MSG_FORM"
	action="storeportal/msgdetail.do" scope="session"
	type="com.cleanwise.view.forms.StoreMsgMgrForm">
	<table cellspacing="0" border="0" width="777" class="mainbody">
		<tr>
			<td class="largeheader" colspan="4">&nbsp;</td>
		</tr>
		<tr>
			<td align="right"><b><app:storeMessage key="storemessages.text.messageId" />:</b></td>
			<td><bean:write name="STORE_ADMIN_MSG_FORM"
				property="messageDetail.storeMessageId" /> <html:hidden
				name="STORE_ADMIN_MSG_FORM" property="messageDetail.storeMessageId" /></td>
		</tr>
		<tr>
			<td align="right"><b><app:storeMessage key="storemessages.text.status" />:<span class="reqind">*</span></b></td>
			<td><html:select name="STORE_ADMIN_MSG_FORM"
				property="messageDetail.storeMessageStatusCd">
				<html:option value="">
					<app:storeMessage  key="admin.select" />
				</html:option>
				<html:options collection="msg.status.vector" property="value" />
			</html:select></td>
		</tr>
		<tr>
			<td align="right"><b><app:storeMessage key="storemessages.text.messageTitle" />:<span class="reqind">*</span></b></td>
			<td><html:text name="STORE_ADMIN_MSG_FORM" readonly="<%=isPublished%>"
				property="messageDetail.messageTitle" size="50" maxlength="128" /></td>
		</tr>
		<tr>
			<td align="right"><b><app:storeMessage key="storemessages.text.datePosted" />:</b></td>
			<td><nobr><html:text styleId="postedDate" readonly="<%=isPublished%>"
				name="STORE_ADMIN_MSG_FORM" property="postedDate"
				size="10" maxlength="10" /><%if (isPublished == false) {%><a href="#"
				onClick="showCalendar('postedDate', event);return false;"
				title="<%=ClwI18nUtil.getMessage(request, "storemessages.text.chooseDate", null)%>"><img name="DATEFROM"
				src="<%=request.getContextPath()%>/externals/images/showCalendar.gif" width=19 height=19
				border=0 align="absmiddle" style="position: relative"
				onmouseover="window.status='<%=ClwI18nUtil.getMessage(request, "storemessages.text.chooseDate", null)%>';return true"
				onmouseout="window.status='';return true"></a><%}%></nobr></td>
		</tr>
		<tr>
			<td align="right"><b><app:storeMessage key="storemessages.text.endDate" />:</b></td>
			<td><nobr><html:text styleId="endDate" readonly="<%=isPublished%>"
				name="STORE_ADMIN_MSG_FORM" property="endDate"
				size="10" maxlength="10" /><%if (isPublished == false) {%><a href="#"
				onClick="showCalendar('endDate', event);return false;"
				title="<%=ClwI18nUtil.getMessage(request, "storemessages.text.chooseDate", null)%>"><img name="DATEFROM"
				src="<%=request.getContextPath()%>/externals/images/showCalendar.gif" width=19 height=19
				border=0 align="absmiddle" style="position: relative"
				onmouseover="window.status='<%=ClwI18nUtil.getMessage(request, "storemessages.text.chooseDate", null)%>';return true"
				onmouseout="window.status='';return true"></a><%}%></nobr></td>
		</tr>
		<tr>
			<td align="right" width="1%"><b><app:storeMessage key="storemessages.text.forcedRead" /></b></td>
			<td><nobr><html:checkbox styleId="forcedRead" onclick="refForcedRead();"
                name="STORE_ADMIN_MSG_FORM"
				property="forcedRead"></html:checkbox>&nbsp;<app:storeMessage key="storemessages.text.yes" />&nbsp;<html:text
				name="STORE_ADMIN_MSG_FORM" styleId="howManyTimes" property="howManyTimes"
				size="7" maxlength="7" /><app:storeMessage key="storemessages.text.howManyTimes" /></nobr></td>
		</tr>
		<tr>
			<td align="right"><b><app:storeMessage key="storemessages.text.language" />:</b><span class="reqind">*</span></td>
			<td><html:select name="STORE_ADMIN_MSG_FORM" disabled="<%=isPublished%>"
				property="messageDetail.languageCd">
				<html:option value="">
					<app:storeMessage  key="admin.select" />
				</html:option>
				<html:options collection="language.vector" labelProperty="uiName"
					property="languageCode" />
			</html:select></td>
		</tr>
		<tr>
			<td align="right"><b><app:storeMessage key="storemessages.text.country" />:</b></td>
			<td><html:select name="STORE_ADMIN_MSG_FORM" disabled="<%=isPublished%>"
				property="messageDetail.country">
				<html:option value="">
					<app:storeMessage  key="admin.select" />
				</html:option>
				<html:options collection="country.vector" labelProperty="uiName"
					property="countryCode" />
			</html:select></td>
		</tr>
		<tr>
			<td><b><app:storeMessage key="storemessages.text.messageAuthor" />:</b><span class="reqind">*</span></td>
			<td><html:text name="STORE_ADMIN_MSG_FORM"
				readonly="<%=isPublished%>"
				property="messageDetail.messageAuthor" size="25" maxlength="60" />
			</td>
		</tr>
		<tr>
			<td colspan="2"><b><app:storeMessage key="storemessages.text.messageAbstract" />:</b><span
				class="reqind">*</span></td>
		</tr>
		<tr>
			<td colspan="2"><html:textarea name="STORE_ADMIN_MSG_FORM"
				property="messageDetail.messageAbstract"
				style="width:100%; height:100px;" /></td>
		</tr>
		<tr>
			<td colspan="2"><b><app:storeMessage key="storemessages.text.messageBody" />:</b></td>
		</tr>
		<tr>
			<td colspan="2"><html:textarea name="STORE_ADMIN_MSG_FORM"
				styleId="messageBody"
				property="messageDetail.messageBody" rows="10" cols="10"
				style="width:100%;height:350px;" /></td>
		</tr>
		<tr>
			<td colspan="2" align="center"><html:submit property="action">
				<app:storeMessage  key="global.action.label.save" />
			</html:submit><html:submit property="action" disabled="<%=isPublished%>">
				<app:storeMessage  key="global.label.preview" />
			</html:submit><html:submit property="action" disabled="<%=isPublished%>">
				<app:storeMessage  key="global.label.publish" />
			</html:submit><logic:greaterThan name="STORE_ADMIN_MSG_FORM"
				property="messageDetail.storeMessageId" value="0">
				<html:submit property="action">
					<app:storeMessage  key="global.label.clone" />
				</html:submit>
			</logic:greaterThan></td>
		</tr>
	</table>
</html:form></div>
<%if (isShowPreview) {%>
<%-- Preview Area Start --%>
<div id="coverLayer">&nbsp;</div>
<div class="popUpWindow popUpMedium"><div class="popUpTop">&nbsp;</div>
<div class="popUpContent clearfix"><h1><app:storeMessage key="storemessages.text.messages" /></h1><hr />
<div id="message">
<h4><bean:write name="STORE_ADMIN_MSG_FORM" property="messageDetail.messageTitle"/></h4>
<p><bean:write name="STORE_ADMIN_MSG_FORM" property="messageDetail.messageAbstract" /><br/></p>
<p class="credit"><app:storeMessage key="message.label.from" />: <bean:write name="STORE_ADMIN_MSG_FORM" property="messageDetail.messageAuthor" /><br />
<%=ClwI18nUtil.formatDate(request, theForm.getMessageDetail().getPostedDate())%>
</p>
<p><bean:write filter="false" name="STORE_ADMIN_MSG_FORM" property="messageDetail.messageBody"/>
</p>
</div>
<hr /><div class="buttonRow"><a href="#" class="blueBtnLarge closePopUpBtn"><span><app:storeMessage key="global.action.label.close" /></span></a></div>
</div>
<div class="popUpBottom">&nbsp;</div>
</div>
<%-- Preview Area End --%>
<%}%>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/externals/ckeditor_3.6/ckeditor.js"></script>
<script type="text/javascript">
<%Locale userLocale = ClwI18nUtil.getUserLocale(request);%>
CalendarMetaInfo.init('<%=userLocale.toString()%>', '<%=I18nUtil.getDatePattern(userLocale)%>');
window.onload = function()
{
	refForcedRead();
	CKEDITOR.replace('messageBody');
<%if (isPublished) {%>
	CKEDITOR.on( 'instanceReady', function(ev) {
		ev.editor.setReadOnly(true);
	});<%}%><%if (isShowPreview) {%>
	preview();<%}%>
}
function refForcedRead() {
    var forcedRead = document.getElementById('forcedRead');
    var howManyTimes = document.getElementById('howManyTimes');
    if (forcedRead && howManyTimes) {
    	howManyTimes.disabled = forcedRead.checked == false; 
    }
}
<%if (isShowPreview) {%>
function closePopUp() {
    $('body').removeClass('hideSelects');
    $('div#coverLayer, div.loader, div.popUpWindow').css('visibility', 'hidden').remove();
    return false;
}
function preview() {
    $('div.popUpWindow #message').height('325');
    setWindowSize();
    $('div.popUpWindow a.closePopUpBtn').bind('click', closePopUp);
    $('div.popUpWindow').css('visibility', 'visible');
    $('body').addClass('hideSelects');
    $('div#coverLayer').css('visibility', 'visible');
}<%}%>
</script>