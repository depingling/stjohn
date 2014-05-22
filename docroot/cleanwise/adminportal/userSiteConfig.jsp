<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.dao.*" %>
<%@ page import="com.cleanwise.view.forms.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>



<script language="JavaScript1.2">
<!--
function popLocate(pLoc, name, pDesc) {
  var loc = pLoc + ".do?feedField=" + name + "&feedDesc=" + pDesc;
  locatewin = window.open(loc,"Locate", "menubar=no,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
  locatewin.focus();

  return false;
}

//-->
</script>

<app:checkLogon/>


<html:html>

<head>
<link rel="stylesheet" href="../externals/styles.css">
<title>Application Administrator Home: User Configuration</title>

<script type="text/javascript">

function f_check_search()
{
 var x=document.getElementsByName("searchType");
 for (var i = 0; i < x.length ; i++)
 {
  if (x[i].value == "nameBegins")
  {
    x[i].checked = true;
  }
 }                   
}

function f_check_boxes()
{
  f_set_vals(1);
}

function f_uncheck_boxes()
{
  f_set_vals(0);
}

function f_set_vals(pVal)
{
 var x=document.getElementsByName("selectIds");
 //alert('x.length='+x.length + ' pVal=' + pVal);
 for (var i = 0; i < x.length ; i++)
 {
  if (x[i].name == "selectIds")
  {
   if ( pVal == 1 )
   {
    x[i].checked = true;
   }
   else
   {
    x[i].checked = false;
   }
  }
 }

}

</script>

<style>
.cfg { 
  border-top: solid 1px black;
  border-right: solid 1px black;
  background-color: #cccccc;
  font-weight: bold;
  text-align: center;

}
.cfg_on { 
  border-top: solid 1px black;
  border-right: solid 1px black;
  background-color: white;
  font-weight: bold;
  text-align: center;
}

</style>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body>

<jsp:include flush='true' page="ui/admToolbar.jsp"/>
<jsp:include flush='true' page="ui/admUserToolbar.jsp"/>
<jsp:include flush='true' page="ui/loginInfo.jsp"/>
<jsp:include flush='true' page="ui/userInfo.jsp"/>

<bean:define id="v_userData" name="USER_DETAIL_FORM" 
  property="detail.userData" 
  type="com.cleanwise.service.api.value.UserData"
/>

<%
String reqTab = request.getParameter("tab");
if ( null == reqTab ) {
  reqTab = (String)request.getSession().getAttribute("reqTab");
}

String actionIs = request.getParameter("action");
if ( null == actionIs ) {
  actionIs = "none";
}

if ( actionIs.equals("init") || null == reqTab ) {
  reqTab = "acctConfig";
}

request.getSession().setAttribute("reqTab", reqTab);

%>

<table cellpadding=5>
<tr>
<td class="cfg">Configure:</td>
<td 
<% if (reqTab.equals("acctConfig")) { %>class="cfg_on" 
<% } else { %>class="cfg"<% } %>
style="border-left: solid 1px black;">
<a href="userSiteConfig.do?tab=acctConfig">Accounts</a>
</td>

<td 
<% if (reqTab.equals("sitesConfig")) { %>class="cfg_on" 
<% } else { %>class="cfg"<% } %>
>
<a href="userSiteConfig.do?tab=sitesConfig&resetSiteList=y">Sites</a>
</td>

<td 
<% if (reqTab.equals("permConfig")) { %>class="cfg_on" 
<% } else { %>class="cfg"<% } %>
>
<a href="userSiteConfig.do?tab=permConfig">Permissions</a>
</td>

<td 
<% if (reqTab.equals("groupConfig")) { %>class="cfg_on" 
<% } else { %>class="cfg"<% } %>
>
<a href="userSiteConfig.do?tab=groupConfig">Groups</a>
</td>
</tr>
</table>

<font color=red><html:errors/></font>

<table class="results"><tr><td>

<% 
String f1sub = "adminportal/userSiteConfig.do?userid=" +
  v_userData.getUserId() + "&tab=" + reqTab;
String f2sub = "adminportal/usermgrDetail.do?goto=userconfig&userid=" +
  v_userData.getUserId() + "&tab=" + reqTab;
%>


<% if (reqTab.equals("acctConfig")) { %>

<div id="acctConfig">
<html:form name="USER_SITE_CONFIG_FORM" 
 action="<%=f1sub%>"
 scope="session" 
 type="com.cleanwise.view.forms.UserMgrSiteConfigForm">

 
 
 
<table width="769" class="results">
<tr><td colspan=3>Locate an account to add to the user account list.</td></tr>
<tr><td class="mainbodylocatename"><b>Account Id:</b>
<span class="reqind">*</span>
<html:text size="6" name="USER_SITE_CONFIG_FORM" property="accountId" />
<html:button onclick="return popLocate('accountlocate', 'accountId', 'accountName');" 
  value="Locate Account" property="action" />
<br>
<html:text name="USER_SITE_CONFIG_FORM" property="accountName" styleClass="mainbodylocatename"/>
<html:submit property="action"><app:storeMessage  key="admin.button.addAccount"/></html:submit>
</td>
<td rowspan=2><%@ include file="f_user_accounts_list.jsp" %></td>
</tr>
</table>
</html:form>
</div>
<% } %>

<% if (reqTab.equals("sitesConfig")) { %>

<div id="sitesConfig">
<html:form name="USER_SITE_CONFIG_FORM" 
 action="<%=f1sub%>"
 scope="session" type="com.cleanwise.view.forms.UserMgrSiteConfigForm">


<table width="769"  class="results">
<tr><td colspan=2>Search for sites belonging to the accounts configured for 
this user.</td>
</td>
  <tr> <td align="right"><b>Find Site</b></td>
    <td>
      <html:text name="USER_SITE_CONFIG_FORM" property="searchField" 
        onfocus="javascript:f_check_search();" />
      <html:radio name="USER_SITE_CONFIG_FORM" property="searchType" 
    value="id" />  ID
      <html:radio name="USER_SITE_CONFIG_FORM" property="searchType" 
    value="nameBegins" />  Name(starts with)
      <html:radio name="USER_SITE_CONFIG_FORM" property="searchType" 
    value="nameContains" />  Name(contains)
    </td>
<td rowspan=3><%@ include file="f_user_accounts_list.jsp" %></td>
  </tr>

  <tr> 
    <td align="right"><b>City</b></td>
    <td><html:text name="USER_SITE_CONFIG_FORM" property="city"/> </td>
  </tr>

  <tr> 
    <td align="right"><b>State</b></td>
    <td><html:text name="USER_SITE_CONFIG_FORM" property="state"/> </td>
  </tr>
    
  <tr> <td>&nbsp;</td>
       <td colspan="3">
        <html:hidden property="siteconfig" value="true"/>
	<html:submit property="action">
		<app:storeMessage  key="global.action.label.search"/>
	</html:submit>
	<html:submit property="action" value ="Search Configured Sites"/>
 	<html:submit property="action">
		<app:storeMessage  key="admin.button.configureAll"/>
	</html:submit>
     </td>
  </tr>
  </tr>
  
</table>

<logic:present name="user.siteview.vector">
<bean:size id="rescount"  name="user.siteview.vector"/>
Search result count:  <bean:write name="rescount" />
<logic:greaterThan name="rescount" value="0">

<table>
<tr align=left>
<td><a class="tableheader" href="userSiteConfig.do?action=sort&siteconfig=true&sortField=id">Site Id</td>
<td><a class="tableheader" href="userSiteConfig.do?action=sort&siteconfig=true&sortField=name">Name</td>
<td><a class="tableheader" href="userSiteConfig.do?action=sort&siteconfig=true&sortField=address">Street Address</td>
<td><a class="tableheader" href="userSiteConfig.do?action=sort&siteconfig=true&sortField=city">City</td>
<td><a class="tableheader" href="userSiteConfig.do?action=sort&siteconfig=true&sortField=state">State</td>
<td><a class="tableheader" href="userSiteConfig.do?action=sort&siteconfig=true&sortField=zipcode">Zip</td>
<td><a class="tableheader" href="userSiteConfig.do?action=sort&siteconfig=true&sortField=status">Status</td>
<td class="tableheader">
<a href="javascript:f_check_boxes();">[ Select All ]<br></a>
<a href="javascript:f_uncheck_boxes();">[ Clear All ]</a>
</td>
</tr>

<logic:iterate id="arrele" name="user.siteview.vector" 
 type="com.cleanwise.service.api.value.SiteView">

<tr>
<td><bean:write name="arrele" property="id"/></td>
<td>

<%
String eleid = String.valueOf(arrele.getId());
%>

<a href="sitemgr.do?action=sitedetail&searchType=id&searchField=<%=eleid%>">
<bean:write name="arrele" property="name"/>
</a>
</td>
<td>
<bean:write name="arrele" property="address"/>
</td>
<td>
<bean:write name="arrele" property="city"/>
</td>
<td>
<bean:write name="arrele" property="state"/>
</td>
<td>
<bean:write name="arrele" property="postalCode"/>
</td>
<td><bean:write name="arrele" property="status"/></td>
<td>
<html:multibox name="USER_SITE_CONFIG_FORM" property="selectIds" 
  value="<%=eleid%>" />
</td>
<html:hidden name="USER_SITE_CONFIG_FORM" property="displayIds" 
  value="<%=eleid%>" />
</tr>

</logic:iterate>
<td colspan="6"></td>
<td>
<html:submit property="action">
<app:storeMessage  key="admin.button.saveUserSites"/>
</html:submit>
</td>
</tr>

</table>

</logic:greaterThan>
</logic:present>

</html:form>

</div>
<% } %>

<% if (reqTab.equals("permConfig")) { %>
<div id="permConfig">

<html:form name="USER_DETAIL_FORM" 
  action="<%=f2sub%>"
  scope="session" type="com.cleanwise.view.forms.UserMgrDetailForm">

<table width="769">
<tr>
  <td colspan="4"><b>Default User Rights</b>
<%@ include file="f_user_rights_config.jsp" %>
</td>
</tr>
</table>


<bean:size id="userAccountsSize"  name="USER_DETAIL_FORM" 
  property="detail.userAccountRights" />
<logic:greaterThan name="userAccountsSize" value="1">

<b>Account specific rights</b>

<logic:iterate id="arrele" name="USER_DETAIL_FORM" 
  property="rightsForms" indexId="userRightsIdx"
  type="UserRightsForm">
<%@ include file="f_user_account_rights_config.jsp" %>
<br>
</logic:iterate>

</logic:greaterThan>


<table width="769"  class="results">
<tr>
<td>&nbsp;</td>
<td align="center">
<html:submit property="action">
<app:storeMessage  key="admin.button.saveUserPermissions"/>
</html:submit>
<html:reset>
<app:storeMessage  key="admin.button.reset"/>
</html:reset>&nbsp;
</td>
</tr>
</table>
</html:form>
</div>
<% } %>

<% if (reqTab.equals("groupConfig")) { %>
<div id="groupConfig">
<html:form name="USER_DETAIL_FORM" 
  action="<%=f2sub%>"
  scope="session" type="com.cleanwise.view.forms.UserMgrDetailForm">

<table width="769"  class="results">
<tr>
<td><b>Member of Group:</b></td>
<td valign="top">
<%
java.util.HashMap groups = (java.util.HashMap)
  request.getSession().getAttribute("Users.groups.map");
if ( null != groups ) {
  java.util.Iterator itr = groups.values().iterator();
  while ( itr != null && itr.hasNext() )
  {
  String gname = (String)itr.next();  
%>
<br><html:multibox name="USER_DETAIL_FORM" property="memberOfGroups" 
  value="<%=gname%>"/><%=gname%>
<% 
} // end of while
} // if on groups
%>
</td>
</tr>

<tr>
<td colspan="4" align="center">
<html:submit property="action">
<app:storeMessage  key="admin.button.saveUserGroups"/>
</html:submit>
<html:reset>
<app:storeMessage  key="admin.button.reset"/>
</html:reset>&nbsp;
</td>
</tr>
</table>

<table cellpadding=4 class="adm_panel">
<tr>
<td colspan=6 class="aton"><b>Groups Information</b></td>
</tr>
<tr>
<td>
<% boolean headerDone = false; %>
<logic:iterate id="ugrep" name="USER_DETAIL_FORM" 
  property="groupsReport"
  type="UniversalDAO.dbrow">

<% if (!headerDone) { headerDone = true; %>
<%=ugrep.toHtmlTableHeader()%>
<% } %>

<%=ugrep.toHtmlTableRow()%>
</logic:iterate>
</td>
</tr>


</table>

</html:form>

</div>
<% } %>

</td></tr></table> <!-- end of results -->


<jsp:include flush='true' page="ui/admFooter.jsp"/>
</body>

</html:html>




