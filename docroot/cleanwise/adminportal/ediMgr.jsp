<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<app:setLocaleAndImages/>
<app:checkLogon/>

<%

String reqdir = request.getParameter("dir");
if ( null == reqdir ) reqdir = "outbound";

String downloadFile = request.getParameter("downloadFile");
if (null != downloadFile && downloadFile.length() > 0 ) {
%>

<%
  // Send this file back to the client.

  response.setContentType("text/plain;charset=ISO-8859-4");
  String f2 = "../xsuite/edi/" + reqdir + "/" + downloadFile;
  java.io.FileReader df = null;

  df = new java.io.FileReader(f2);
  char [] fl = new char[1];
  while ( df.read(fl, 0, fl.length) > 0 ) {
    response.getWriter().print(fl);
	java.util.Arrays.fill(fl, '\0');
  }
  response.flushBuffer();
  if ( df != null ) { 
    try { df.close(); } catch (Exception e) {}
  }
%>

<% } else { %>


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

<title>EDI <%=reqdir%></title>
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

<%
java.io.File [] pendFiles = new java.io.File[0];
try {
  String storageDir = "../xsuite/edi/" + reqdir;
  java.io.File sDir = new java.io.File(storageDir);
  pendFiles = sDir.listFiles();
  request.setAttribute("files.found.vector", pendFiles);
} catch (Exception e) {}
%>

<div class="admres">

File(s) found: <%=pendFiles.length%>

<logic:present name="files.found.vector">
<ol>

<%
for ( int i = (pendFiles.length -1 ); i >= 0; i-- ) {
java.io.File tf = (java.io.File)pendFiles[i];

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
      msg += "<pre><span style=\"font-size: 12pt;\" >";
      char [] fLine = new char[1];
      while ( infile.read(fLine, 0, fLine.length) > 0 ) {
        msg += new String(fLine);
	java.util.Arrays.fill(fLine, '\0');
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

<li><a href="ediMgr.jsp?dumpFile=<%=tf.getName()%>&dir=<%=reqdir%>"><%=tf.getName()%></a></li>&nbsp;&nbsp;&nbsp;&nbsp;<a href="ediMgr.jsp?downloadFile=<%=tf.getName()%>&dir=<%=reqdir%>">[Download]</a>
<br><b><%=msg%></b>

<% } /* End of the for loop on files listed. */ %> 

</ol>
</logic:present>
</div>

</body>
</html:html>

<% } %>
