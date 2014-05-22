<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<jsp:include flush='true' page="../general/checkBrowser.jsp"/>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<app:checkLogon/>
<% String storeDir=ClwCustomizer.getStoreDir(); %>
<% boolean clwSwitch=ClwCustomizer.getClwSwitch(); %>

<script language="JavaScript1.2">
<!--

//-->
</script>

<html:html>
<head>
<title>Site Notes</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="../externals/styles.css">
</head>


<bean:define id="theForm" name="CRC_SITE_NOTE_FORM" type="com.cleanwise.view.forms.NoteMgrForm"/>

<body bgcolor="#cccccc">


<div class = "text">

<jsp:include flush='true' page="ui/consoleToolbar.jsp"/>
<jsp:include flush='true' page="ui/consoleSiteToolbar.jsp"/>
<jsp:include flush='true' page="ui/loginInfo.jsp"/>
<font color=red><html:errors/></font>
<% 
  String action = (String) session.getAttribute("LAST_ACTION");
  String errors = (String) request.getAttribute("errors");
  if("Add Note".equals(action) ||
      "readNote".equals(action) ||
      "editNote".equals(action) ||
      "editTitle".equals(action) ||
      "Delete Selected".equals(action) ||
      "Save Note".equals(action)) { 
%>
<jsp:include flush='true' page="crcsiteNoteText.jsp"/>
<%} else { %>
<jsp:include flush='true' page="crcsiteNoteTitle.jsp"/>
<%}%>
<jsp:include flush='true' page="ui/consoleFooter.jsp"/>
</body>
</html:html>

