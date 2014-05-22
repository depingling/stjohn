<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<script language="JavaScript">

function f_tcb() {
  if ( document.USER_SEARCH_FORM.searchField.value == "" ) { 
    document.USER_SEARCH_FORM.searchType[1].checked='true';
  } 
}

</script>

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
%>

<div class="text">
<center>
<font color=red>
<html:errors/>
</font>
</center>
<table cellspacing="0" border="0" class="mainbody" width="769">
<html:form name="USER_SEARCH_FORM" action="adminportal/usermgr.do"
   scope="session" type="com.cleanwise.view.forms.UserMgrSearchForm">
  <tr> 
  	<td align="right"><b>Search Users:</b>
  	<input type="hidden" name="feedField" value="<%=feedField%>">
	<input type="hidden" name="feedDesc" value="<%=feedDesc%>">
  	</td>
<td>
<html:text name="USER_SEARCH_FORM" property="searchField"
    onfocus="javascript: f_tcb();"
/>

<html:radio name="USER_SEARCH_FORM" property="searchType" 
    value="id" />  ID
<html:radio name="USER_SEARCH_FORM" property="searchType" 
    value="nameBegins" />  Name(starts with)
<html:radio name="USER_SEARCH_FORM" property="searchType" 
    value="nameContains" />  Name(contains)
</td>
</tr>

<tr> 
<td align="right"><b>First Name:</b></td>
<td><html:text name="USER_SEARCH_FORM" property="firstName"/> </td>
</tr>

<tr> 
<td align="right"><b>Last Name:</b></td>
<td><html:text name="USER_SEARCH_FORM" property="lastName"/> </td>
</tr>

<tr>
<td align="right"><b>Type:</b></td>
<td>
	<html:select name="USER_SEARCH_FORM" property="userType" >
		<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
		<html:options  collection="Users.types.vector" property="value" />
	</html:select>
</td>
</tr>

  <tr> <td>&nbsp;</td>
       <td>
	<html:submit property="action">
		<app:storeMessage  key="global.action.label.search"/>
	</html:submit>
        <html:submit property="action">
		<app:storeMessage  key="admin.button.viewall"/>
	</html:submit>
        <html:submit property="action">
		<app:storeMessage  key="admin.button.create"/>
	</html:submit>
        <html:submit property="action">
                <app:storeMessage  key="admin.button.createClone"/>
        </html:submit>
    </td>
  </tr>
</table>


<logic:present name="Users.found.vector">
<bean:size id="rescount"  name="Users.found.vector"/>
Search result count:  <bean:write name="rescount" />


<table cellspacing="0" border="0" width="769" class="results">
<tr align=left>
<td><a class="tableheader" href="usermgr.do?action=sort&sortField=id">User&nbsp;Id </td>
<td><a class="tableheader" href="usermgr.do?action=sort&sortField=name">Login Name </td>
<td><a class="tableheader" href="usermgr.do?action=sort&sortField=firstName">First Name </td>
<td><a class="tableheader" href="usermgr.do?action=sort&sortField=lastName">Last Name </td>
<td><a class="tableheader" href="usermgr.do?action=sort&sortField=type">User Type </td>
<td><a class="tableheader" href="usermgr.do?action=sort&sortField=status">Status </td>
<td><a class="tableheader" href="usermgr.do?action=sort&sortField=status">Select</td>
</tr>

<logic:iterate id="arrele" name="Users.found.vector">

<tr>
<td><bean:write name="arrele" property="userId"/></td>
<td>
<bean:define id="eleid" name="arrele" property="userId"/>
<bean:define id="eletype" name="arrele" property="userTypeCd"/>
<a href="usermgr.do?action=userdetail&searchType=userId&searchField=<%=eleid%>&type=<%=eletype%>">
<bean:write name="arrele" property="userName"/>
</a>
</td>
<td><bean:write name="arrele" property="firstName"/></td>
<td><bean:write name="arrele" property="lastName"/></td>
<td><bean:write name="arrele" property="userTypeCd"/></td>
<td><bean:write name="arrele" property="userStatusCd"/></td>
<td><html:radio name="USER_SEARCH_FORM" property="selectedId" value="<%=\"\"+eleid%>"/></td>

</tr>


</logic:iterate>
</table>

</logic:present>
</html:form>

</div>
 




