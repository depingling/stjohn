<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>

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
	
	String showStoreMSBOnly = (String)request.getParameter("showStoreMSBOnly");
	%>

<script language="JavaScript1.2">
 <!-- 

function passIdAndName(id, name) {
  var feedBackFieldName = document.USER_MGR_LOCATE_FORM.feedField.value;
  var feedDesc = document.USER_MGR_LOCATE_FORM.feedDesc.value;

  if(feedBackFieldName && ""!= feedBackFieldName) {
    window.opener.document.forms[0].elements[feedBackFieldName].value = id;
  }
  if(feedDesc && ""!= feedDesc) {
    window.opener.document.forms[0].elements[feedDesc].value = unescape(name.replace(/\+/g, ' '));
  }
  self.close();
}
	function setAndSubmit( value) {
		 var aaa = document.forms[0].elements['sortBy'];
		 aaa.value=value;
		 aaa.form.submit();
		 return false;
		 }
	 
 //--> 
</script>


<div class="text">
<html:form name="USER_MGR_LOCATE_FORM" action="adminportal/usermgrLocate.do"
    scope="session" type="com.cleanwise.view.forms.UserMgrLocateForm">
  
  <table width="769" cellspacing="0" border="0" class="mainbody">
  <%--
  <html:form name="USER_MGR_LOCATE_FORM" action="adminportal/usermgrLocate.do"
    scope="session" type="com.cleanwise.view.forms.UserMgrLocateForm">
    --%>
  <tr> 
  	<td align="right"><b>Search Users:</b>
  	<input type="hidden" name="feedField" value="<%=feedField%>">
	<input type="hidden" name="feedDesc" value="<%=feedDesc%>">
	<input type="hidden" name="sortBy" value="">
  	</td>

<td>
<html:hidden name="USER_MGR_LOCATE_FORM" property="showStoreMSBOnly" value="<%=showStoreMSBOnly%>" />

<html:text name="USER_MGR_LOCATE_FORM" property="searchField"/>

<html:radio name="USER_MGR_LOCATE_FORM" property="searchType" 
    value="id" />  ID
<html:radio name="USER_MGR_LOCATE_FORM" property="searchType" 
    value="nameBegins" />  Name(starts with)
<html:radio name="USER_MGR_LOCATE_FORM" property="searchType" 
    value="nameContains" />  Name(contains)
</td>
</tr>

<tr> 
<td align="right"><b>First Name:</b></td>
<td><html:text name="USER_MGR_LOCATE_FORM" property="firstName"/> </td>
</tr>

<tr> 
<td align="right"><b>Last Name:</b></td>
<td><html:text name="USER_MGR_LOCATE_FORM" property="lastName"/> </td>
</tr>
<%
CleanwiseUser user = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
	//if(!user.isaStoreAdmin()){
%>
<tr>
<td align="right"><b>Type:</b></td>
<td>
	<html:select name="USER_MGR_LOCATE_FORM" property="userType" >
		<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
		<html:options  collection="Users.types.vector" property="value" />
	</html:select>
</td>
</tr>

<%
//}
%>
  <tr> <td>&nbsp;</td>
       <td>
	<html:submit property="action">
		<app:storeMessage  key="global.action.label.search"/>
	</html:submit>
     <%--</html:form> --%>
    </td>
  </tr>
</table>

<logic:present name="Users.found.vector">
<bean:size id="rescount"  name="Users.found.vector"/>
<% Integer n = (Integer)rescount; %>
<% if (n.intValue() < Constants.MAX_SITES_TO_RETURN) { %>
Search result count:  <bean:write name="rescount" />
<% } else { %>
Search results restricted to maximum of <bean:write name="rescount" />.
Narrow search criteria.
<% } %>

<%--maxPageItems="<%= Constants.MAX_PAGE_ITEMS %>" --%>
    
    

<table cellspacing="0" border="0" width="769" class="results">
<tr align=left>
<td><a class="tableheader" onclick="setAndSubmit('id');" href="#" >User&nbsp;Id</td>
<td><a class="tableheader" onclick="setAndSubmit('name');" href="#" >Login Name </td>
<td><a class="tableheader" onclick="setAndSubmit('firstName');" href="#" >First Name </td>
<td><a class="tableheader" onclick="setAndSubmit('lastName');" href="#" >Last Name </td>
<td><a class="tableheader" onclick="setAndSubmit('type');" href="#" >User Type </td>
<td><a class="tableheader" onclick="setAndSubmit('status');" href="#" >Status </td>
</tr>

<logic:iterate id="arrele" name="Users.found.vector">
<tr>
<td><bean:write name="arrele" property="userId"/></td>
<td>
<bean:define id="key" name="arrele" property="userId"/>
<bean:define id="name" name="arrele" property="userName" type="java.lang.String"/>
    <% String onClick = new String("return passIdAndName('"+key+"','"+ java.net.URLEncoder.encode(name) +"');");%>
    <a href="javascript:void(0);" onclick="<%=onClick%>">
		<bean:write name="arrele" property="userName"/>
	</a>	
</td>
<td><bean:write name="arrele" property="firstName"/></td>
<td><bean:write name="arrele" property="lastName"/></td>
<td><bean:write name="arrele" property="userTypeCd"/></td>
<td><bean:write name="arrele" property="userStatusCd"/></td>
</tr>

</logic:iterate>
</table>
</logic:present>

</html:form>
</div>


