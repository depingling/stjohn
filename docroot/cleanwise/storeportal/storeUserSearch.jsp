<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<script language="JavaScript1.2">
 <!--
function sortSubmit(val) {
 var actions;
 actions=document.all["action"];
 for(ii=actions.length-1; ii>=0; ii--) {
   if(actions[ii].value=='BBBBBBB') {
     actions[ii].value="sort";
     break;
   }
 }
 sortField=document.all["sortField"];
 sortField.value = val;

 document.forms[0].submit();
 return false;
 }

 function loginAsUser() {
	var userButtonCount = document.STORE_ADMIN_USER_FORM.selectedId.length;
	if (userButtonCount == undefined) {
		if(document.STORE_ADMIN_USER_FORM.selectedId.checked)
			document.all["loginAsFormUserId"].value = document.STORE_ADMIN_USER_FORM.selectedId.value;
	}	 
	else {
		for (var i=0; i < userButtonCount; i++)
		   {
		   if (document.STORE_ADMIN_USER_FORM.selectedId[i].checked)
		      {
			   document.all["loginAsFormUserId"].value = document.STORE_ADMIN_USER_FORM.selectedId[i].value;
		      }
		   }
	}
 	document.forms['loginAsForm'].submit();
 }
 -->
</script>

<bean:define id="theForm" name="STORE_ADMIN_USER_FORM" type="com.cleanwise.view.forms.StoreUserMgrForm"/>
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
<% //<html:errors/> %>
</font>
</center>

<jsp:include flush='true' page="locateStoreAccount.jsp">
    <jsp:param name="jspFormAction"           value="/storeportal/usersearch.do"/>
    <jsp:param name="jspFormName"             value="STORE_ADMIN_USER_FORM"/>
    <jsp:param name="jspSubmitIdent"          value=""/>
    <jsp:param name="jspReturnFilterProperty" value="accountFilter"/>
</jsp:include>    

<table ID=1443 cellspacing="0" border="0" class="mainbody" width="769">
<html:form styleId="1444" name="STORE_ADMIN_USER_FORM" action="storeportal/usersearch.do"
   scope="session" type="com.cleanwise.view.forms.StoreUserMgrForm">
    <tr>
        <td align="right"><b>Account(s)</b></td>
        <td>
            <%
            AccountDataVector accountDV = theForm.getAccountFilter();
            if (accountDV != null && accountDV.size() > 0) {
                for (int i = 0; i < accountDV.size(); i++) {
                    AccountData accountD = (AccountData) accountDV.get(i);
            %>
                &lt;<%=accountD.getBusEntity().getShortDesc()%>&gt;
            <% 
                } 
            %>
                <html:submit property="action" value="Clear Account Filter" styleClass='text'/>
            <% 
            } 
            %>
            <html:submit property="action" value="Locate Account" styleClass='text'/>
        </td>
    </tr>
  <tr> 
  	<td align="right"><b>Search Users:</b>
  	<input type="hidden" name="feedField" value="<%=feedField%>">
   	<input type="hidden" name="feedDesc" value="<%=feedDesc%>">
  	</td>
<td>
<html:text name="STORE_ADMIN_USER_FORM" property="searchField"/>

<html:radio name="STORE_ADMIN_USER_FORM" property="searchType" 
    value="id" />  ID
<html:radio name="STORE_ADMIN_USER_FORM" property="searchType" 
    value="nameBegins" />  Name(starts with)
<html:radio name="STORE_ADMIN_USER_FORM" property="searchType" 
    value="nameContains" />  Name(contains)
</td>
</tr>

<tr> 
<td align="right"><b>First Name:</b></td>
<td><html:text name="STORE_ADMIN_USER_FORM" property="firstName"/> </td>
</tr>

<tr> 
<td align="right"><b>Last Name:</b></td>
<td><html:text name="STORE_ADMIN_USER_FORM" property="lastName"/> </td>
</tr>

<tr>
<td align="right"><b>Type:</b></td>
<td>
	<html:select name="STORE_ADMIN_USER_FORM" property="userType" >
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
		<app:storeMessage  key="admin.button.create"/>
	</html:submit>
        <html:submit property="action">
                <app:storeMessage  key="admin.button.createClone"/>
        </html:submit>
        <html:button property="action" onclick="loginAsUser()">
        	<app:storeMessage  key="admin.button.loginAs"/>
        </html:button>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    Show Inactive:<html:checkbox name="STORE_ADMIN_USER_FORM" property="searchShowInactiveFl" />

    </td>
  </tr>
</table>


<logic:present name="Users.found.vector">
<bean:size id="rescount"  name="Users.found.vector"/>
Search result count:  <bean:write name="rescount" />
<% if(rescount.intValue()>=1000) { %>
 (Request Maximum)
<% } %>


<table ID=1445 cellspacing="0" border="0" width="769" class="results">
<tr align=left>
<th  class="tt"><a ID=1446 href="#pgsort" onclick="sortSubmit('id');"><b>User&nbsp;Id </b></a></th>
<th  class="tt"><a ID=1447 href="#pgsort" onclick="sortSubmit('name');"><b>Login Name</b></a></th>
<th  class="tt"><a ID=1448 href="#pgsort" onclick="sortSubmit('firstName');"><b>First Name</b></a></th>
<th  class="tt"><a ID=1449 href="#pgsort" onclick="sortSubmit('lastName');"><b>Last Name</b></a></th>
<th  class="tt"><a ID=1450 href="#pgsort" onclick="sortSubmit('type');"><b>User Type</b></a></th>
<th  class="tt"><a ID=1451 href="#pgsort" onclick="sortSubmit('status');"><b>Status</b></a></th>
<th  class="tt"><a ID=1452 href="#pgsort" onclick="sortSubmit('Select');"><b>Select</b></a></th>
</tr>

<tbody id="resTblBdy">
<logic:iterate id="arrele" name="Users.found.vector">

<tr>
<td><bean:write name="arrele" property="userId"/></td>
<td>
<bean:define id="eleid" name="arrele" property="userId"/>
<bean:define id="eletype" name="arrele" property="userTypeCd"/>
<a ID=1453 href="userdet.do?action=userdetail&userId=<%=eleid%>&type=<%=eletype%>">
<bean:write name="arrele" property="userName"/>
</a>
</td>
<td><bean:write name="arrele" property="firstName"/></td>
<td><bean:write name="arrele" property="lastName"/></td>
<td><bean:write name="arrele" property="userTypeCd"/></td>
<td><bean:write name="arrele" property="userStatusCd"/></td>
<td><html:radio name="STORE_ADMIN_USER_FORM" property="selectedId" value="<%=\"\"+eleid%>"/></td>

</tr>


</logic:iterate>
</tbody>
</table>

</logic:present>

  <html:hidden  property="action" value="BBBBBBB"/>
  <html:hidden  property="sortField" value="BBBBBBB"/>

</html:form>

	<html:form styleId="loginAsForm" action="userportal/proxyLogin.do">
    	<html:hidden property="<%=Constants.PARAMETER_OPERATION%>"
    		value="<%=Constants.PARAMETER_OPERATION_VALUE_PROXY_LOGIN%>"/>
    	<html:hidden styleId="loginAsFormUserId" property="userId" value=""/>
    </html:form>

</div>

