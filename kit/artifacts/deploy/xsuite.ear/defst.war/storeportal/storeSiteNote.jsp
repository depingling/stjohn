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


<script language="JavaScript1.2">
<!--

//-->
</script>

<html:html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="../externals/styles.css">
</head>


<bean:define id="theForm" name="SITE_NOTE_MGR_FORM" type="com.cleanwise.view.forms.NoteMgrForm"/>

<body bgcolor="#cccccc">


<div class = "text">

<% 
  String action = (String) session.getAttribute("LAST_ACTION");
  int topicId = theForm.getTopicId();
  if(topicId<=0){
%>
<jsp:include flush='true' page="storeSiteNoteTopic.jsp"/>
<%} else {
  if("Add Note".equals(action) ||
      "readNote".equals(action) ||
      "editNote".equals(action) ||
      "editTitle".equals(action) ||
      "Delete Selected".equals(action) ||
      "Save Note".equals(action)) { 
%>
<jsp:include flush='true' page="storeSiteNoteText.jsp"/>
<%} else { %>
<jsp:include flush='true' page="storeSiteNoteTitle.jsp"/>
<%}%>
<%}%>
</body>
</html:html>

