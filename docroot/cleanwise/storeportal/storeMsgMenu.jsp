<%@ page language="java"%>
<%@ page import="com.cleanwise.view.utils.Constants"%>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer"%>
<%@ page import="java.util.*"%>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames"%>
<%@ page import="com.cleanwise.service.api.value.*"%>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app"%>

<bean:define id="theForm" name="STORE_ADMIN_MSG_FORM"
	type="com.cleanwise.view.forms.StoreMsgMgrForm" />
<%
    int msgId = theForm.getMessageDetail().getStoreMessageId();
%>
<table width="<%=Constants.TABLEWIDTH%>">
	<tr bgcolor="#000000">
		<%
		    if (msgId > 0) {
		        String detail = ClwI18nUtil.getMessage(request, "storemessages.text.detail", null);
		        String configuration = ClwI18nUtil.getMessage(request, "storemessages.text.configuration", null);
		%>
		<app:renderStatefulButton link="msgdetail.do"
			name="<%=detail%>" tabClassOff="tbar" tabClassOn="tbarOn"
			linkClassOff="tbar" linkClassOn="tbarOn" contains="msgdetail" />
		<app:renderStatefulButton link="msgconfig.do" name="<%=configuration%>"
			tabClassOff="tbar" tabClassOn="tbarOn" linkClassOff="tbar"
			linkClassOn="tbarOn" contains="msgconfig" />
		<%
		    } else {
		%>
		<td class="tbartext">&nbsp;</td>
		<%
		    }
		%>
	</tr>
</table>