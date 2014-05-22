<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<app:setLocaleAndImages/>
<app:checkLogon/>
<bean:define id="Location" value="none" type="java.lang.String" 
  toScope="session"/>
<html:html>
<head>
<link rel="stylesheet" href="../externals/styles.css">
<style>
.tt {
  color: white; background-color: black;
}
.tt1 {
  border-right: solid 1px black;
}

</style>
<title>EDI Inbound</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body>

<%
String action = request.getParameter("action");
if ( null == action ) { action = "init"; }
%>

<table border=0 width="769" cellpadding="0" cellspacing="0">
<tr>  <td> <jsp:include flush='true' page="ui/systemToolbar.jsp"/> </td></tr>
<tr>  <td> <jsp:include flush='true' page="ui/loginInfo.jsp"/> </td></tr>
<tr>  <td> <jsp:include flush='true' page="ui/admEdiToolbar.jsp"/> </td></tr>
</table>

<table bgcolor="#cccccc" width="769" >
<html:form 
  action="/adminportal/ediInbound.do?action=load-file" 
  enctype="multipart/form-data">

<% if ( action.equals("load-file")) { %>
<tr><td>
<b>File name: </b>
</td><td><bean:write name="fileName"/></td>
</tr>
<tr><td>
<b>File size: </b>
</td><td><bean:write name="size"/></td>
</tr>
<tr><td>
<b>Result: </b>
</td><td><bean:write name="data"/></td>
</tr>

<% } %>
<tr><td>


<b>Load EDI file:</b> 
<html:file name="EDI_INBOUND_FORM" property="theFile"
   accept="text/plain"/>
<input type=submit name=action value="Load File">
</html:form>

</td></tr>
</table>

<div class="admres">
<logic:present name="inbound.pending.vector">
<bean:size id="pendCount" name="inbound.pending.vector"/>
File(s) pending delivery: <%=pendCount%>
<ol>
<logic:iterate id="tf" type="java.io.File"
  name="inbound.pending.vector">

<%
String reqdir = "inbound";
String dumpFile = request.getParameter("dumpFile");
if ( null == dumpFile ) dumpFile = "---";
String msg = null;
if (tf.canRead()) {
  msg = "Can read";
  // read 65 byte header
  java.io.FileReader infile = null;
  try {
    msg = "";
    infile = new java.io.FileReader(tf);
    if ( dumpFile.equals(tf.getName())) {
      msg += "<pre><span style=\"font-size: 12pt;\">";
      char [] fLine = new char[1000];
      while ( infile.read(fLine, 0, fLine.length) > 0 ) {
        msg += new String(fLine);
        fLine = new char[1000];
      }
      msg += "</span></pre>";
    }
    else {
      char [] firstLine = new char[65];
      infile.read(firstLine, 0, firstLine.length);
      msg = new String(firstLine);
    }
  } 
  catch (Exception e) {
    msg = "Exception: " + e.getMessage();
  }
  finally {
    if ( infile != null ) { 
      try { infile.close(); }
      catch (Exception e) {}
    }
  }
}
else {
  msg = "Can't read the file.";
}

%>

<li><a href="ediInbound.do?dumpFile=<%=tf.getName()%>&dir=<%=reqdir%>"><%=tf.getName()%></a></li>&nbsp;&nbsp;&nbsp;&nbsp;<a title="<%=tf.getName()%>" type="text/plain" href="ediMgr.jsp?downloadFile=<%=tf.getName()%>&dir=<%=reqdir%>">[Download]</a>
<br><b><%=msg%></b>


</logic:iterate>

</ol>
</logic:present>
</div>

</body>
</html:html>
