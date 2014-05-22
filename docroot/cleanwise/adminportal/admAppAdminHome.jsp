<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>


<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<% 
boolean clwSwitch = ClwCustomizer.getClwSwitch();
%>

<app:setLocaleAndImages/>
<app:checkLogon/>
<bean:define id="Location" value="none" type="java.lang.String" 
  toScope="session"/>
<html:html>
<head>
<link rel="stylesheet" href="../externals/styles.css">
<style>
.tt {
  color: white;
  background-color: black;
}
.tt1 {
  border-right: solid 1px black;
  border-bottom: solid 1px black;
}

a.tt:link, a.tt:visited, a.tt:active	{
 color: #ffffff; 
 font-weight: bold;    font-size: 10pt; 	
 text-decoration: none
}
</style>

<SCRIPT TYPE="text/javascript" SRC="../externals/table-sort.js" 
  CHARSET="ISO-8859-1"></SCRIPT>

<title>Application Administrator Home</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#FFFFFF">
<table border=0 cellpadding="0" cellspacing="0">
<tr>
  <td>
    <jsp:include flush='true' page="ui/admToolbar.jsp"/>
  </td>
</tr>
<tr>
  <td>
    <jsp:include flush='true' page="ui/loginInfo.jsp"/>
  </td>
</tr>

<tr><td>
<%
if(clwSwitch) {
%>
<%
java.util.Hashtable loginSessions = 
  (java.util.Hashtable)
  pageContext.getAttribute(
    "login.session.vector", 
    PageContext.APPLICATION_SCOPE );

if ( null != loginSessions ) { 

%>

<%
CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
String logins = 
  "<table class=\"stpTable_sortable\" id=\"ts1\"> <thead><tr>" +
  "<th class=\"stpTH\">User Name</th>" +
  "<th class=\"stpTH\">Type</th>" + 
  "<th class=\"stpTH\">Login Date</th>" +
  "<th class=\"stpTH\">Last Access</th><th class=\"stpTH\">ID</th>" +
  "<th class=\"stpTH\">Last Request</th>";
  if(RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.getUser().getUserTypeCd())){
	logins = logins+"<th class=\"stpTH\">Kill</th>";
  }
  logins = logins+"</tr></thead> " +
  " <tbody id=\"itemTblBdy\">";

java.util.Enumeration sesKeys = loginSessions.keys();

while (sesKeys.hasMoreElements()) {

  javax.servlet.http.HttpSession thisSes = 
    (javax.servlet.http.HttpSession)sesKeys.nextElement();  
  String userName = null;

  try { userName = (String)thisSes.getAttribute("LoginUserName"); }
  catch (Exception e) {}
  if ( null == userName ) {
    loginSessions.remove(thisSes);
  }
  else {
    java.util.Date loginDate = 
      (java.util.Date)thisSes.getAttribute("LoginDate");

    String userId = (String)thisSes.getAttribute("LoginUserId"),
	 lastReq = (String)thisSes.getAttribute("LastRequest"),
	userType = (String)thisSes.getAttribute("UserType")
	;
    logins += "<tr><td class=\"stpTD\">" + userName + "</td>";
    logins += "<td class=\"stpTD\">" + userType + "</td>";
    logins += "<td class=\"stpTD\">" + loginDate + "</td>";
    logins += "<td class=\"stpTD\">" + 
      new java.util.Date(thisSes.getLastAccessedTime()) + 
      "</td>";
      
    logins +="<td class=\"stpTD\">" +thisSes.getId()+"</td>";
    logins += "<td class=\"stpTD\" width=200 >" + lastReq + "</td>";
	boolean killed = false;
	if(RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.getUser().getUserTypeCd()) && "invalidate".equals(request.getParameter("action"))){
        if(request.getParameter("id").equals(thisSes.getId())){
            thisSes.invalidate();
            logins +="<td class=\"stpTD\">KILLED</td>";
			killed = true;
        }
    }
	if(!killed && RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.getUser().getUserTypeCd())){
		logins = logins+"<td class=\"stpTD\"><a href=\"appAdminHome.do?action=invalidate&id="+thisSes.getId()+"\">Kill</a></td>";
	}
    logins += "</tr>" ;
    
  }

}

logins += "</tbody></table>";

%>

<br><b>Session count: </b>
<%= loginSessions.size() %>

<%= logins %>

<% } %>
<% } %>

</td></tr>

<tr> <td> <jsp:include flush='true' page="ui/admFooter.jsp"/> </td></tr>
</table>

</body>
</html:html>
