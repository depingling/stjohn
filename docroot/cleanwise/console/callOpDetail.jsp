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
<bean:define id="closedate" name="CALL_OP_DETAIL_FORM" property="closedDateS" />
<bean:define id="assignedtoid" name="CALL_OP_DETAIL_FORM" property="assignedToId" />
<bean:define id="statuscd" name="CALL_OP_DETAIL_FORM" property="callDetail.callStatusCd" />

<%
	String action = (String)request.getParameter("action");
	if ( null == action ) {
		action = (String)request.getParameter("orgaction");
	}
	if (null == action) action = "add";
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

function popLocateSite(pLoc, name, pDesc) {
var loc = pLoc + ".do?feedField=" + name + "&amp;feedDesc=" + pDesc + "&amp;accountid=" + document.forms[0].accountId.value;
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
<html:form name="CALL_OP_DETAIL_FORM" action="console/callOpDetail.do"
scope="session" type="com.cleanwise.view.forms.CallOpDetailForm">

<tr> 
	<td colspan="6" class="largeheader">Call Detail
		<html:hidden property="change" value="" />
		<html:hidden property="orgaction" value="<%=action%>" />
	</td>	
</tr>	

<tr> 
	<td colspan="6"><hr></td>	
</tr>	

<% if ( !"view".equals(action) ) {  %>

<tr>
	<td><b>Call&nbsp;Id:</b></td>
	<td>
		<bean:write name="CALL_OP_DETAIL_FORM" property="callDetail.callId" scope="session"/>
		<html:hidden property="callDetail.callId"/>
	</td>
	<td><b>Status:</b></td>
	<td>
		<html:select name="CALL_OP_DETAIL_FORM" property="statusCd">
			<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
			<html:options  collection="Call.status.vector" property="value" />				
		</html:select>
		<span class="reqind">*</span>
	</td>
	<td><b>Opened By:</b></td>
<% if ("edit".equals(action) ) {  %>	
	<td><bean:write name="CALL_OP_DETAIL_FORM" property="callDetail.addBy" scope="session"/></td>	
<% } else { %>	
	<td><%=session.getAttribute(Constants.USER_NAME)%></td>
<% }  %>	
</tr>

<tr>
	<td><b>Account Id:</b></td>
	<td>
		<html:text name="CALL_OP_DETAIL_FORM" property="accountId" size="8" maxlength="30" />
		<html:button property="action" 
   			onclick="popLocate('../adminportal/accountlocate', 'accountId', '');"
   			value="Locate Account" styleClass="smallbutton"/>
	</td>
	<td><b>City:</b></td>
	<td><bean:write name="CALL_OP_DETAIL_FORM" property="siteCity" scope="session"/></td>	
	<td><b>Assigned To:</b></td>
	<td>
	    <bean:define id="userlist" name="CALL_OP_DETAIL_FORM" property="customerServiceUserList" />
		<html:select name="CALL_OP_DETAIL_FORM" property="assignedToId">
			<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
			<html:options collection="userlist" property="userId" labelProperty="userName"/>
		</html:select>											
	</td>	
</tr>	

<tr>
	<td><b>Site Id:</b></td>
	<td>
		<html:text name="CALL_OP_DETAIL_FORM" property="siteId" size="8" maxlength="30" onchange="document.forms[0].change.value='order'; document.forms[0].changefield.value='siteId'; document.forms[0].submit();"/>
		<html:button property="action"
   			onclick="popLocateSite('../adminportal/sitelocate', 'siteId', '');"
   			value="Locate Site" styleClass="smallbutton" />			
	</td>
	<td><b>State:</b></td>
	<td><bean:write name="CALL_OP_DETAIL_FORM" property="siteState" scope="session"/></td>	
	<td><b>Email Message:</b></td>
	<td>
		<html:text name="CALL_OP_DETAIL_FORM" property="emailMessage" maxlength="100" />
	</td>		
</tr>	

<tr>
	<td><b>Contact Name:</b></td>
	<td>
		<html:text name="CALL_OP_DETAIL_FORM" property="callDetail.contactName" maxlength="30" />
		<span class="reqind">*</span>
	</td>
	<td><b>Zip:</b></td>
	<td><bean:write name="CALL_OP_DETAIL_FORM" property="siteZip" scope="session"/></td>	
	<td><b>ERP Order #:</b></td>
	<td>		
		<html:hidden property="changefield" value="" />
		<html:text name="CALL_OP_DETAIL_FORM" property="erpOrderNum" maxlength="30" 
			onchange="document.forms[0].change.value='order'; document.forms[0].changefield.value='erpOrderNum'; document.forms[0].submit();"/>
	</td>	
</tr>	

<tr>
	<td><b>Contact Phone:</b></td>
	<td>
		<html:text name="CALL_OP_DETAIL_FORM" property="callDetail.contactPhoneNumber" maxlength="30" />
		<span class="reqind">*</span>	
	</td>
	<td><b>Type:</b></td>
	<td>
		<html:select name="CALL_OP_DETAIL_FORM" property="callDetail.callTypeCd">
			<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
			<html:options  collection="Call.type.vector" property="value" />				
		</html:select>
		<span class="reqind">*</span>
	</td>	
	<td><b>Web Order #:</b></td>
	<td>
		<html:text name="CALL_OP_DETAIL_FORM" property="webOrderNum" maxlength="30" 
			onchange="document.forms[0].change.value='order'; document.forms[0].changefield.value='webOrderNum'; document.forms[0].submit();"/>
	</td>	
</tr>	

<tr>
	<td><b>Contact Email:</b></td>
	<td><html:text name="CALL_OP_DETAIL_FORM" property="callDetail.contactEmailAddress" maxlength="30" /></td>
	<td><b>Severity:</b></td>
	<td>
		<html:select name="CALL_OP_DETAIL_FORM" property="severityCd">
			<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
			<html:options  collection="Call.severity.vector" property="value" />				
		</html:select>
		<span class="reqind">*</span>
	</td>	
	<td><b>Customer PO #:</b></td>
	<td>
		<html:text name="CALL_OP_DETAIL_FORM" property="custPoNum" maxlength="30" 
			onchange="document.forms[0].change.value='order'; document.forms[0].changefield.value='custPoNum'; document.forms[0].submit();"/>
	</td>	
</tr>	

<tr>
	<td><b>Customer Field 1:</b></td>
	<td><html:text name="CALL_OP_DETAIL_FORM" property="callDetail.customerField1" maxlength="30" /></td>
	<td colspan="2">&nbsp;</td>
	<td><b>Outbound PO #:</b></td>
	<td>
		<html:text name="CALL_OP_DETAIL_FORM" property="erpPoNum" maxlength="30" 
			onchange="document.forms[0].change.value='order'; document.forms[0].changefield.value='erpPoNum'; document.forms[0].submit();" />
	</td>		
</tr>	

<tr>
	<td><b>Product Name:</b></td>
	<td><html:text name="CALL_OP_DETAIL_FORM" property="callDetail.productName" maxlength="30" /></td>
	<td colspan="4">&nbsp;</td>
</tr>	

<tr><td colspan="6"><hr></td></tr>

<tr>
	<td><b>Call Description:</b></td>
	<td colspan="5">
		<html:text name="CALL_OP_DETAIL_FORM" property="callDetail.longDesc" size="62" maxlength="255" />
		<span class="reqind">*</span>
	</td>		
</tr>	

<tr>
	<td><b>Opened Date:</b></td>
<% if ("edit".equals(action) ) {  %>	
	<td><bean:write name="CALL_OP_DETAIL_FORM" property="callDetail.addDate" scope="session"/></td>	
<% } else { %>
	<td>
	<% Date currd = new Date(); %>
	<%= currd.toString() %>
	</td>
<% }  %>	
	<td><b>Closed Date:</b></td>
	<td><html:text name="CALL_OP_DETAIL_FORM" property="closedDateS" maxlength="30" /></td>
	<td colspan="2">&nbsp;</td>
</tr>	

<tr><td colspan="6">&nbsp;</td></tr>

<tr>
	<td colspan="6"><b>CALL COMMENTS</b></td>
</tr>
<tr>
	<td colspan="6">
		<html:textarea name="CALL_OP_DETAIL_FORM" cols="60" property="callDetail.comments"/>
		<span class="reqind" valign="top">*</span>		
	</td>
</tr>


<%  } else if ("view".equals(action)) {  %>

<tr>
	<td><b>Call&nbsp;Id:</b></td>
	<td>
		<bean:write name="CALL_OP_DETAIL_FORM" property="callDetail.callId" scope="session"/>
		<html:hidden name="CALL_OP_DETAIL_FORM" property="callDetail.callId"/>
	</td>
	<td><b>Status:</b></td>
	<td>
		<html:select name="CALL_OP_DETAIL_FORM" property="statusCd" >
			<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
			<html:options  collection="Call.status.vector" property="value" />				
		</html:select>
		<span class="reqind">*</span>
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
	    <bean:define id="userlist" name="CALL_OP_DETAIL_FORM" property="customerServiceUserList" />
		<html:select name="CALL_OP_DETAIL_FORM" property="assignedToId">
			<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
			<html:options collection="userlist" property="userId" labelProperty="userName"/>
		</html:select>												
	</td>	
</tr>	

<tr>
	<td><b>Site Name:</b></td>
	<td><bean:write name="CALL_OP_DETAIL_FORM" property="siteName" /></td>
	<td><b>State:</b></td>
	<td><bean:write name="CALL_OP_DETAIL_FORM" property="siteState" scope="session"/></td>	
	<td><b>Email Message:</b></td>
	<td>
		<html:text name="CALL_OP_DETAIL_FORM" property="emailMessage" maxlength="100" />
	</td>		
</tr>	

<tr>
	<td><b>Contact Name:</b></td>
	<td><bean:write name="CALL_OP_DETAIL_FORM" property="callDetail.contactName" /></td>
	<td><b>Zip:</b></td>
	<td><bean:write name="CALL_OP_DETAIL_FORM" property="siteZip" scope="session"/></td>	
	<td><b>ERP Order #:</b></td>
	<td>
		<bean:define id="orderStatusId" name="CALL_OP_DETAIL_FORM" property="callDetail.orderId" />
	 	<% String orderlink = new String("orderOpDetail.do?action=view&id=" + orderStatusId);%>
		<a href="javascript:void(0);" onclick="popOrder('<%=orderlink%>');"><bean:write name="CALL_OP_DETAIL_FORM" property="erpOrderNum" /></a>
	</td>	
</tr>	

<tr>
	<td><b>Contact Phone:</b></td>
	<td><bean:write name="CALL_OP_DETAIL_FORM" property="callDetail.contactPhoneNumber" /></td>
	<td><b>Type:</b></td>
	<td>
		<bean:write name="CALL_OP_DETAIL_FORM" property="callDetail.callTypeCd" />
	</td>	
	<td><b>Web Order #:</b></td>
	<td><bean:write name="CALL_OP_DETAIL_FORM" property="webOrderNum" /></td>	
</tr>	

<tr>
	<td><b>Contact Email:</b></td>
	<td><bean:write name="CALL_OP_DETAIL_FORM" property="callDetail.contactEmailAddress" /></td>
	<td><b>Severity:</b></td>
	<td>
		<html:select name="CALL_OP_DETAIL_FORM" property="severityCd">
			<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
			<html:options  collection="Call.severity.vector" property="value" />				
		</html:select>
		<span class="reqind">*</span>
	</td>	
	<td><b>Customer PO #:</b></td>
	<td><bean:write name="CALL_OP_DETAIL_FORM" property="custPoNum" /></td>	
</tr>	

<tr>
	<td><b>Customer Field 1:</b></td>
	<td><bean:write name="CALL_OP_DETAIL_FORM" property="callDetail.customerField1" /></td>
	<td colspan="2">&nbsp;</td>
	<td><b>Outbound PO #:</b></td>
	<td><bean:write name="CALL_OP_DETAIL_FORM" property="erpPoNum" /></td>		
</tr>	

<tr>
	<td><b>Product Name:</b></td>
	<td><bean:write name="CALL_OP_DETAIL_FORM" property="callDetail.productName" /></td>
	<td colspan="4">&nbsp;</td>
</tr>	

<tr><td colspan="6"><hr></td></tr>

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
	<% if ( "".equals(closedate) ) {  %>
	<td><html:text name="CALL_OP_DETAIL_FORM" property="closedDateS" maxlength="30"/></td>
	<% } else {  %>
	<td><bean:write name="CALL_OP_DETAIL_FORM" property="closedDateS" /></td>
	<% }  %>
	<td colspan="2">&nbsp;</td>
</tr>	

<tr><td colspan="6"><hr></td></tr>

<tr>
	<td colspan="6"><b>CALL COMMENTS</b></td>
</tr>
<tr>
	<td colspan="6">
		<bean:define id="comments" name="CALL_OP_DETAIL_FORM" property="callDetail.comments" type="java.lang.String"/>
		<% 	String newComments = new String("");
			if (null != comments && ! "".equals(comments)) {
				newComments = StringUtils.replaceString(comments, "\n", "<br>");	
			}
		%>	
		<%=newComments%>
	</td>
</tr>

<%  }  // end of if action ==  "view" %>

<tr><td colspan="6"><hr></td></tr>

<tr>
	<td colspan="6"><b>CALL NOTES</b></td>
</tr>

<tr>
	<td colspan="2"><b>Note Date</b></td>
	<td colspan="3"><b>Note Description</b></td>
	<td><b>Note By</b></td>
</tr>

<bean:size id="noteNumber" name="CALL_OP_DETAIL_FORM" property="callNotesList" />
<logic:iterate id="callNote" name="CALL_OP_DETAIL_FORM" property="callNotesList"
     offset="0" length="<%=noteNumber.toString()%>" type="com.cleanwise.service.api.value.CallPropertyData"> 
 <bean:define id="noteId"  name="callNote" property="callPropertyId"/>
 <bean:define id="addDate" name="callNote" property="addDate"/>
 <bean:define id="addTime" name="callNote" property="addTime"/>
 <% String linkHref = new String("callOpNoteDetail.do?action=view&id=" + noteId + "&callid=" + callId);%>

 <tr>
 	<td colspan="2">
		<i18n:formatDate value="<%=addDate%>" pattern="MM/dd/yyyy" locale="<%=Locale.US%>"/>
		&nbsp;<i18n:formatDate value="<%=addTime%>" pattern="h:mm a" locale="<%=Locale.US%>"/>
	</td>
	<td colspan="3"><a href="<%=linkHref%>"><bean:write name="callNote" property="shortDesc"/></a></td> 
	<td colspan="2"><bean:write name="callNote" property="addBy"/></td>
 </tr>
 </logic:iterate>	 

 <tr><td colspan="6"><hr></td></tr>
 
<tr>
<td colspan="6" align="center">

<html:submit property="action">
	<app:storeMessage  key="global.action.label.save"/>
</html:submit>
<html:reset>
	<app:storeMessage  key="admin.button.reset"/>
</html:reset>

<html:submit property="action">
	<app:storeMessage  key="admin.button.addNote"/>
</html:submit>
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






