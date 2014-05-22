<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.cleanwise.view.forms.LocateStoreUserForm" %>
<%@ page import="com.cleanwise.view.forms.StorePortalForm" %>
<%@ page import="com.cleanwise.view.utils.SessionAttributes" %>
<%@ page import="com.cleanwise.view.forms.AggregateItemMgrForm"%>


<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>


<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<%
  String jspFormName = request.getParameter("jspFormName");
  if(jspFormName == null){
	throw new RuntimeException("jspFormName cannot be null");
  }

  //LocateStoreUserForm theForm = null;
  String jspFormNestProperty = request.getParameter("jspFormNestProperty");
  if(jspFormNestProperty != null){
	jspFormNestProperty = jspFormNestProperty + ".locateStoreUserForm";
  }else{
	jspFormNestProperty = "locateStoreUserForm";
  }
 %>
  <logic:present name="<%=jspFormName%>" property="<%=jspFormNestProperty%>" >
  <bean:define id="theForm" name="<%=jspFormName%>" property="<%=jspFormNestProperty%>" type="LocateStoreUserForm"/>

  <%
  if(theForm != null && theForm.getLocateUserFl()){
  String jspFormAction = request.getParameter("jspFormAction");
  if(jspFormAction == null){
	throw new RuntimeException("jspFormAction cannot be null");
  }
  String jspSubmitIdent = request.getParameter("jspSubmitIdent");
  if(jspSubmitIdent == null){
	throw new RuntimeException("jspSubmitIdent cannot be null");
  }
  String jspReturnFilterProperty = request.getParameter("jspReturnFilterProperty");
  if(jspReturnFilterProperty == null){
	throw new RuntimeException("jspReturnFilterProperty cannot be null");
  }
  jspSubmitIdent += "#"+SessionAttributes.SEARCH_FORM.LOCATE_STORE_USER_FORM;
%>
<%
  String jspUserTypesAutorized = request.getParameter("jspUserTypesAutorized");
  theForm.setUserTypesAutorized(jspUserTypesAutorized);
%>

<html:html>
<script language="JavaScript1.2">
<!--
function SetCheckedUsers (val) {
var dml=document.forms['locateStoreUserForm'];
var ellen = dml.elements.length;
  //alert('next_form='+ellen);
  for(j=0; j<ellen; j++) {
    if (dml.elements[j].name=='<%=jspFormNestProperty%>.selected') {
      dml.elements[j].checked=val;

    }
  }

 }



function SetAndSubmitUsers(name, val) {
  var dml=document.forms['locateStoreUserForm'];
  var ellen = dml[name].length;
  if(ellen>0) {
  for(j=0; j<ellen; j++) {
    if(dml[name][j].value==val) {
      found = true;
      dml[name][j].checked=1;
    } else {
      dml[name][j].checked=0;
    }
  }
  } else {
    dml[name].checked=1;
  }
  var iiLast = dml['action'].length-1;
  dml['action'][iiLast].value='Return Selected';
  dml.submit();
}

function submitenter(myfield,e)
{
var keycode;
if (window.event) keycode = window.event.keyCode;
else if (e) keycode = e.which;
else return true;

if (keycode == 13)
   {
   myfield.form.submit();
   return false;
   }
else
   return true;
}

//-->
</script>



<body>

<script src="../externals/lib.js" language="javascript"></script>
<html:form  styleId="locateStoreUserForm" action="<%=jspFormAction%>"  scope="session" focus="mainField">
<div class="rptmid">
Find Users
<table ID=405 width="750"  border="0"  class="mainbody">

  <html:hidden property="jspSubmitIdent" value="<%=jspSubmitIdent%>"/>
  <%String prop=jspFormNestProperty+".property";%>
  <html:hidden property="<%=prop%>" value="<%=jspReturnFilterProperty%>"/>
  <%prop=jspFormNestProperty+".name";%>
  <html:hidden property="<%=prop%>" value="<%=jspFormName%>"/>

  <tr> <td align="right">
	   <%prop=jspFormNestProperty+".searchField";%>

      <b>Search User:</b></td>
	  <td><html:text styleId="mainField" property="<%=prop%>" onkeypress="return submitenter(this,event)"/>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <%prop=jspFormNestProperty+".searchType";%>
        <html:radio property="<%=prop%>" value="id" /> ID <html:radio property="<%=prop%>" value="nameBegins" />
        Name(starts with) <html:radio property="<%=prop%>" value="nameContains" />
        Name(contains) </td>
  </tr>
 <tr>
<td align="right"><b>First Name:</b></td>
<%prop=jspFormNestProperty+".firstName" ;%>
<td><html:text  property="<%=prop%>" onkeypress="return submitenter(this,event)"/> </td>
</tr>
 <tr>
<td align="right"><b>Last Name:</b></td>
<%prop=jspFormNestProperty+".lastName" ;%>
<td><html:text  property="<%=prop%>" onkeypress="return submitenter(this,event)"/> </td>
</tr>

<tr>
<td align="right"><b>Type:</b></td>
 <%prop=jspFormNestProperty+".userType" ;%>
<td>
	<html:select  property="<%=prop%>" >
		<html:option value="">Select</html:option>
		<html:options  collection="Locate.users.types.vector" property="value" />
	</html:select>
</td>
</tr>

  <tr>
      <td colspan='4'>
	<html:submit property="action" value="Search"/>
	<%prop=jspFormNestProperty+".showInactiveFl";%>
	Show Inactive: <html:checkbox property="<%=prop%>"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<html:submit property="action" value="Cancel"/>
	<html:submit property="action" value="Return Selected"/></td>

  </tr>
</table>


<!-- logic:present name="locateStoreUserForm.Users" -->
<% if(theForm.getUsers()!=null) {
 int rescount = theForm.getUsers().size();

%>
Search result count: <%=rescount%>
<% if(rescount>0) { %>

<table ID=406  width="750"  border="0" class="results">
<tr align=left>
<td><a ID=407 class="tableheader">User Id</a></td>
<td><a ID=408 class="tableheader">Name </a></td>
<td>
<a ID=409 href="javascript:SetCheckedUsers(1)">[Check&nbsp;All]</a><br>
<a ID=410 href="javascript:SetCheckedUsers(0)">[&nbsp;Clear]</a>
</td>
<td><a ID=411 class="tableheader">First Name</a></td>
<td><a ID=412 class="tableheader">Last Name</a></td>
<td><a ID=413 class="tableheader">Type</a></td>
<td><a ID=414 class="tableheader">Status</a></td>
</tr>

<%
  String propName = jspFormNestProperty + ".users";
  prop=jspFormNestProperty+".users";
  String selectBoxProp = jspFormNestProperty+".selected";

 %>


<logic:iterate id="arrele" name="<%=jspFormName%>" property="<%=prop%>"
       type="com.cleanwise.service.api.value.UserData">
    <bean:define id="key"  name="arrele" property="userId"/>
    <bean:define id="name" name="arrele" property="userName" type="String"/>
    <% String linkHref = "javascript: SetAndSubmitUsers ('"+selectBoxProp+"',"+key+");";%>

<tr>
<td><bean:write name="arrele" property="userId"/></td>
<td><a ID=415 href="<%=linkHref%>"><bean:write name="arrele" property="userName"/></a></td>
<td><html:multibox property="<%=selectBoxProp%>" value="<%=key.toString()%>"/></td>
<td><bean:write name="arrele" property="firstName"/></td>
<td><bean:write name="arrele" property="lastName"/></td>
<td><bean:write name="arrele" property="userTypeCd"/></td>
<td><bean:write name="arrele" property="userStatusCd"/></td>
</tr>
</logic:iterate>
</table>
<%}%>
<%}%>
<!-- /logic:present -->
</div>
<html:hidden  property="action" value="Search"/>
</html:form>
</body>

</html:html>

<%}//if(theForm.getLocateUserFl())%>
</logic:present> <%--main form--%>
