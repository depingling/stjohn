<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.StringUtils" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Locale" %>

<jsp:include flush='true' page="../general/checkBrowser.jsp"/>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<app:checkLogon/>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="callId" name="CALL_OP_DETAIL_FORM" property="callDetail.callId" type="java.lang.Integer"/>
<%
	String action = (String)request.getParameter("action");
%>
<html:html>

<head>
<link rel="stylesheet" href="../externals/styles.css">
<title>Operation Console Home: Call Tracking</title>
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

function popOrder(pLoc) {
var loc = pLoc;
orderwin = window.open(loc,"Order", "menubar=yes,resizable=yes,scrollbars=yes,toolbar=yes,status=yes,height=500,width=780,left=50,top=50");
orderwin.focus();

return false;
}
//-->
</script>

<body bgcolor="#FFFFFF">

<jsp:include flush='true' page="ui/consoleToolbar.jsp"/>
<jsp:include flush='true' page="ui/loginInfo.jsp"/>
<jsp:include flush='true' page="ui/callOpToolbar.jsp"/>


<div class="text">
<font color=red>
<html:errors/>
</font>

<table cellpadding="2" cellspacing="0" border="0" width="769" class="mainbody">
<html:form name="CALL_OP_NOTE_DETAIL_FORM" action="console/callOpNoteDetail.do"
scope="session" type="com.cleanwise.view.forms.CallOpDetailForm">

<tr> 
	<td colspan="6" class="largeheader">Call Note Detail</td>	
</tr>	

<tr><td colspan="6"><hr></td></tr>

<tr>
	<td><b>Call&nbsp;Id:</b></td>
	<td>
		<bean:write name="CALL_OP_DETAIL_FORM" property="callDetail.callId" scope="session"/>
		<html:hidden name="CALL_OP_DETAIL_FORM" property="callDetail.callId"/>
	</td>
	<td><b>Status:</b></td>
	<td>
		<bean:write name="CALL_OP_DETAIL_FORM" property="callDetail.callStatusCd" />
	</td>
	<td><b>Opened By:</b></td>
	<td><bean:write name="CALL_OP_DETAIL_FORM" property="callDetail.addBy" /></td>
</tr>

<tr>
	<td><b>Account Name:</b></td>
	<td><bean:write name="CALL_OP_DETAIL_FORM" property="accountName" /></td>
	<td><b>City:</b></td>
	<td><bean:write name="CALL_OP_DETAIL_FORM" property="siteCity" scope="session"/></td>	
	<td><b>Assigned To:</b></td>
	<td>
		<bean:write name="CALL_OP_DETAIL_FORM" property="assignedToId" />
	</td>	
</tr>	

<tr>
	<td><b>Site Name:</b></td>
	<td><bean:write name="CALL_OP_DETAIL_FORM" property="siteName" /></td>
	<td><b>State:</b></td>
	<td><bean:write name="CALL_OP_DETAIL_FORM" property="siteState" scope="session"/></td>	
	<td><b>ERP Order #:</b></td>
	<td>
		<bean:define id="erpOrderNum" name="CALL_OP_DETAIL_FORM" property="erpOrderNum" />
	 	<% String orderlink = new String("orderOpDetail.do?action=view&id=" + erpOrderNum);%>
		<a href="javascript:void(0);" onclick="popOrder('<%=orderlink%>');"><bean:write name="CALL_OP_DETAIL_FORM" property="erpOrderNum" /></a>
	</td>	
</tr>	

<tr>
	<td><b>Contact Name:</b></td>
	<td><bean:write name="CALL_OP_DETAIL_FORM" property="callDetail.contactName" /></td>
	<td><b>Zip:</b></td>
	<td><bean:write name="CALL_OP_DETAIL_FORM" property="siteZip" scope="session"/></td>	
	<td><b>Web Order #:</b></td>
	<td><bean:write name="CALL_OP_DETAIL_FORM" property="webOrderNum" /></td>	
</tr>	

<tr>
	<td><b>Contact Phone:</b></td>
	<td><bean:write name="CALL_OP_DETAIL_FORM" property="callDetail.contactPhoneNumber" /></td>
	<td><b>Type:</b></td>
	<td>
		<bean:write name="CALL_OP_DETAIL_FORM" property="callDetail.callTypeCd" />
	</td>	
	<td><b>Customer PO #:</b></td>
	<td><bean:write name="CALL_OP_DETAIL_FORM" property="custPoNum" /></td>	
</tr>	

<tr>
	<td><b>Contact Email:</b></td>
	<td><bean:write name="CALL_OP_DETAIL_FORM" property="callDetail.contactEmailAddress" /></td>
	<td><b>Severity:</b></td>
	<td>
		<bean:write name="CALL_OP_DETAIL_FORM" property="callDetail.callSeverityCd" />
	</td>	
	<td><b>Outbound PO #:</b></td>
	<td><bean:write name="CALL_OP_DETAIL_FORM" property="erpPoNum" /></td>	
</tr>	

<tr>
	<td><b>Customer Field 1:</b></td>
	<td><bean:write name="CALL_OP_DETAIL_FORM" property="callDetail.customerField1" /></td>
	<td colspan="4">&nbsp;</td>
</tr>	

<tr>
	<td><b>Product Name:</b></td>
	<td><bean:write name="CALL_OP_DETAIL_FORM" property="callDetail.productName" /></td>
	<td colspan="4">&nbsp;</td>
</tr>	

<tr>
	<td><b>Call Description:</b></td>
	<td colspan="5"><bean:write name="CALL_OP_DETAIL_FORM" property="callDetail.longDesc" /></td>
</tr>	

<tr>
	<td><b>Opened Date:</b></td>
	<td>
		<bean:define id="callAddDate" name="CALL_OP_DETAIL_FORM" property="callDetail.addDate" />
		<bean:define id="callAddTime" name="CALL_OP_DETAIL_FORM" property="callDetail.addTime" />
		<i18n:formatDate value="<%=callAddDate%>" pattern="MM/dd/yyyy" locale="<%=Locale.US%>"/>
		&nbsp;<i18n:formatDate value="<%=callAddTime%>" pattern="h:mm a" locale="<%=Locale.US%>"/>		
	</td>	
	<td><b>Closed Date:</b></td>
	<td><bean:write name="CALL_OP_DETAIL_FORM" property="closedDateS" /></td>
	<td colspan="2">&nbsp;</td>
</tr>	

<tr><td colspan="6"><hr></td></tr>

<tr>
	<td><b>Note Description:</b></td>

<% if (! "view".equals(action) ) {  %>	
	<td colspan="5">
		<html:text name="CALL_OP_NOTE_DETAIL_FORM" property="noteDetail.shortDesc" size="60" maxlength="255" />
		<span class="reqind">*</span>
	</td>
<%  } else if ("view".equals(action)) {  %>
	<td colspan="5"><bean:write name="CALL_OP_NOTE_DETAIL_FORM" property="noteDetail.shortDesc" /></td>
<% }  %>	
</tr>	

<tr>
	<td><b>Note Date:</b></td>
<% if ( "edit".equals(action) ||  "view".equals(action) ) {  %>	
	<td colspan="2">
		<bean:define id="addDate" name="CALL_OP_NOTE_DETAIL_FORM" property="noteDetail.addDate" />
		<bean:define id="addTime" name="CALL_OP_NOTE_DETAIL_FORM" property="noteDetail.addTime" />
		<i18n:formatDate value="<%=addDate%>" pattern="MM/dd/yyyy" locale="<%=Locale.US%>"/>
		&nbsp;<i18n:formatDate value="<%=addTime%>" pattern="h:mm a" locale="<%=Locale.US%>"/>		
	</td>	
<% } else { %>
	<td>
	<% Date currd = new Date(); %>
	<%= currd.toString() %>
	</td>
<% }  %>	
	<td><b>Note By:</b></td>
<% if ("edit".equals(action) ) {  %>	
	<td><bean:write name="CALL_OP_NOTE_DETAIL_FORM" property="noteDetail.addBy" scope="session"/></td>	
<% } else { %>	
	<td><%=session.getAttribute(Constants.USER_NAME)%></td>
<% }  %>	
	<td>&nbsp;</td>
</tr>	

<tr>
	<td colspan="6"><hr></td>
</tr>

<tr>
	<td colspan="6"><b>NOTE COMMENTS</b></td>
</tr>
<tr>
	<td colspan="6">
<% if ( ! "view".equals(action)  ) {  %>	
		<html:textarea name="CALL_OP_NOTE_DETAIL_FORM" cols="60" property="noteDetail.value"/>
		<span class="reqind" valign="top">*</span>		
<%  } else if ("view".equals(action)) {  %>
		<bean:define id="comments" name="CALL_OP_NOTE_DETAIL_FORM" property="noteDetail.value" type="java.lang.String"/>
		<% 	String newComments = new String("");
			if (null != comments && ! "".equals(comments)) {
				newComments = StringUtils.replaceString(comments, "\n", "<br>");	
			}
		%>	
		<%=newComments%>		
<% }  %>
	</td>
</tr>

<tr><td colspan="6"><hr></td></tr>

<tr>
<td colspan="6" align="center">
<% if ( ! "view".equals(action)  ) {  %>	
<html:submit property="action">
	<app:storeMessage  key="global.action.label.save"/>
</html:submit>
<html:reset>
	<app:storeMessage  key="admin.button.reset"/>
</html:reset>
<% }  %>
<html:submit property="action">
	<app:storeMessage  key="admin.button.back"/>
</html:submit>
</td>
</tr>
</html:form>

</table>
</div>

<jsp:include flush='true' page="ui/consoleFooter.jsp"/>
</body>

</html:html>






