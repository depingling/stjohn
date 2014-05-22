
<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<%
	String feedField =  (String)request.getParameter("feedField");
	if(null == feedField) {
		feedField = new String("");
	}
	String feedDesc =  (String)request.getParameter("feedDesc");
	if(null == feedDesc) {
		feedDesc = new String("");
	}
	String submitFl =  (String)request.getParameter("submitFl");
	if(null == submitFl) {
		submitFl = "false";
	}
	String searchAccountId =  (String)
	  request.getParameter("searchAccountId");
	if(null == searchAccountId) {
		searchAccountId = new String("");
	}
%>

<script language="JavaScript1.2">
<!--

function passIdAndName(id, name) {
  var feedBackFieldName = document.USER_SEARCH_FORM.feedField.value;
  var feedDesc = document.USER_SEARCH_FORM.feedDesc.value;
  var submitFl = document.USER_SEARCH_FORM.submitFl.value;
  if(feedBackFieldName && ""!= feedBackFieldName) {
    window.opener.document.forms[0].elements[feedBackFieldName].value = id;
  }
  if(feedDesc && ""!= feedDesc) {
    window.opener.document.forms[0].elements[feedDesc].value = unescape(name.replace(/\+/g, ' '));
  }
  if(submitFl && "true"==submitFl) {
    window.opener.document.forms[0].submit();
  }
  self.close();
}

//-->
</script>


<div class="text">

<html:form name="USER_SEARCH_FORM" 
action="/adminportal/userlocate.do" focus="searchField"
type="com.cleanwise.view.forms.UserMgrSearchForm">

<input type="hidden" name="feedField" value="<%=feedField%>">
<input type="hidden" name="feedDesc" value="<%=feedDesc%>">
<input type="hidden" name="submitFl" value="<%=submitFl%>">
<input type="hidden" name="searchAccountId" value="<%=searchAccountId%>">


<table cellspacing="0" border="0" class="mainbody" width="769">
  <html:form name="USER_SEARCH_FORM" action="adminportal/userlocate.do"
    scope="session" type="com.cleanwise.view.forms.UserMgrSearchForm">
<% if (searchAccountId.length() > 0) { %>
<tr>
<td><b>Search Account Id:</b></td>
<td><%=searchAccountId%></td>
</tr>
<% } %>
<tr> <td><b>Find Users:</b></td>
<td colspan="3"> 
<html:text name="USER_SEARCH_FORM" property="searchField"/>	
</td>
</tr>
  
<tr> <td>&nbsp;</td>
<td colspan="3">
<html:radio name="USER_SEARCH_FORM" property="searchType" 
  value="id" />ID
<html:radio name="USER_SEARCH_FORM" property="searchType" 
  value="nameBegins" />Name(starts with)
<html:radio name="USER_SEARCH_FORM" property="searchType" 
  value="nameContains" />Name(contains)
</td>
</tr>  
    
  <tr> <td>
<% if (searchAccountId.length() > 0) { %>
 <b>For this account:</b>
<% } %>
&nbsp;</td>
       <td colspan="3">
	<html:submit property="action">
		<app:storeMessage  key="global.action.label.search"/>
	</html:submit>
	<html:submit property="action">
		<app:storeMessage  key="admin.button.viewall"/>
	</html:submit>
     </td>
     </html:form>
  </tr>

  <tr><td colspan="4">&nbsp;</td>
  </tr>
  
  </html:form>
</table>


<logic:present name="Users.found.vector">
<bean:size id="rescount"  name="Users.found.vector"/>
Search result count:  <bean:write name="rescount" />


<table cellspacing="0" border="0" width="769" class="results">
<tr align=left>
<td><a class="tableheader" href="userlocate.do?action=sort&sortField=id">User&nbsp;Id </td>
<td><a class="tableheader" href="userlocate.do?action=sort&sortField=name">Login Name </td>
<td><a class="tableheader" href="userlocate.do?action=sort&sortField=firstName">First Name </td>
<td><a class="tableheader" href="userlocate.do?action=sort&sortField=lastName">Last Name </td>
<td><a class="tableheader" href="userlocate.do?action=sort&sortField=type">User Type </td>
<td><a class="tableheader" href="userlocate.do?action=sort&sortField=workflowRoleCd">Workflow Role
</td>
<td><a class="tableheader" href="userlocate.do?action=sort&sortField=status">Status </td>
</tr>

<logic:iterate id="arrele" name="Users.found.vector">
<tr>
<td><bean:write name="arrele" property="userId"/></td>

<bean:define id="eleid" name="arrele" property="userId"/>
<bean:define id="eletype" name="arrele" property="userTypeCd"/>
<bean:define id="name" name="arrele" property="userName" 
    type="java.lang.String"/>
<bean:define id="fn" name="arrele" property="firstName"/></td>
<bean:define id="ln" name="arrele" property="lastName"/></td>

<% 
  String userdesc = fn + " " + ln +
    " (" + name + ") " + eletype;

  String onClick = new String("return passIdAndName('"+eleid+"', '"+ 
  java.net.URLEncoder.encode(userdesc) +"');"
   );
%>

<td>
<a href="javascript:void(0);" onclick="<%=onClick%>">
<bean:write name="arrele" property="userName"/>
</a>
</td>

<td><bean:write name="arrele" property="firstName"/></td>
<td><bean:write name="arrele" property="lastName"/></td>
<td><bean:write name="arrele" property="userTypeCd"/></td>
<td><bean:write name="arrele" property="workflowRoleCd"/></td>
<td><bean:write name="arrele" property="userStatusCd"/></td>
</tr>

</logic:iterate>
</table>
</logic:present>


</div>


