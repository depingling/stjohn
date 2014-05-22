<%@ page language="java" %>

<%
java.util.Hashtable loginSessions = 
  (java.util.Hashtable)
  pageContext.getAttribute(
    "login.session.vector", 
    PageContext.APPLICATION_SCOPE );

int numbSessions = 0;
if ( null != loginSessions ) {
  numbSessions = loginSessions.size();
}

%>

<html>
<body>
<b>Request Server Info:</b> 
 <%=request.getServerName()%>:<%=request.getServerPort()%><br><br>
<b>RMI Server:</b> 
 <%=System.getProperty("java.rmi.server.codebase")%><br><br>
<b>Build Info:</b> 
 Build 36.00.267.04, built on March 13, 2014 02:25:06PM<br><br>
<b>Number of sessions:</b>
 <%=numbSessions%><br><br>
<%

 Cookie[] cs = request.getCookies();

%>

<style>
.tbh { font-weight: bold; }
.tdh { font-weight: bold; border: 1px solid grey; }
</style>
<%
if(cs!=null){
%>
<span class="tbh">Number of cookies: <%=cs.length%></span>

<%
for ( int i = 0; i < cs.length; i++ ) {
  Cookie thisCookie = cs[i];
%>
<br>
<table>
<tr>
<td class="tdh">Name </td> <td> <%=thisCookie.getName()%></td>
</tr> <tr>
<td class="tdh">Value </td> <td> <%=thisCookie.getValue()%></td>
</tr> <tr>
<td class="tdh">Domain </td> <td> <%=thisCookie.getDomain()%></td>
</tr> <tr>
<td class="tdh">MaxAge </td> <td> <%=thisCookie.getMaxAge()%></td>
</tr> <tr>
<td class="tdh">Path </td> <td> <%=thisCookie.getPath()%></td>
</tr> <tr>
<td class="tdh">Secure </td> <td> <%=thisCookie.getSecure()%></td>
</tr> <tr>
<td class="tdh">This Cookie </td> <td> <%=thisCookie%></td>
</tr>
</table>
<%
}
}
%>


</body>
</html>
