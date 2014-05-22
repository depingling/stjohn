<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.text.SimpleDateFormat" %>
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


<html:html>
<head>
<title>Site Notes</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="../externals/styles.css">
</head>

<script language="JavaScript1.2">
<!--


//-->
</script>

<bean:define id="theForm" name="CRC_SITE_NOTE_FORM" type="com.cleanwise.view.forms.NoteMgrForm"/>

<body bgcolor="#cccccc">

<div class = "text">

<table cellspacing="0" border="0" width="769"  class="mainbody">
<tr>
<td><b>Site&nbsp;Id:</b>
<bean:write name="CRC_SITE_NOTE_FORM" property="busEntityId"/></td>
 <td><b>Name:</b>
 <bean:write name="CRC_SITE_NOTE_FORM" property="busEntityName"/></td>
</td>
<html:form action="/console/crcsiteNote.do" focus="skuTempl" >
<bean:define id="theId"  name="CRC_SITE_NOTE_FORM" property="busEntityId"/>
<html:hidden  name="CRC_SITE_NOTE_FORM" property="busEntityId" value="<%=theId.toString()%>"/>
</tr>
<tr>
<td colspan='2'>
<input type=submit name="action" class="smalltext" value="Add Note"/>
</td>
</tr>
</table>
<table cellspacing="0" border="0" width="769"  class="results">
<% 
  NoteViewVector notes = theForm.getNoteList(); 
  if(notes!=null && notes.size()>0) {
%>
<tr>
<td class="tableheader"><a href='crcsiteNote.do?action=sortNotes&field=noteTitle'>Note Title</a></td>
<td class="tableheader"><a href='crcsiteNote.do?action=sortNotes&field=modifyDate'>Modify Date</a></td>
</tr>
<% 
     SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
     for(Iterator iter = notes.iterator(); iter.hasNext();) {
       NoteView note = (NoteView) iter.next();
       String noteIdS = ""+note.getNoteId();
       String noteTitle = note.getTitle();
       Date modDate = note.getModDate();
       String modDateS = sdf.format(modDate);
   String noteLink = "crcsiteNote.do?action=readNote&noteId="+note.getNoteId();
%>
<tr>
<td><a href="<%=noteLink%>"><%=noteTitle%></a> </td>
<td><%=modDateS%></td>
</tr>
<%}%>
<%}%>
</html:form>

</table>

</div>
</body>
</html:html>

