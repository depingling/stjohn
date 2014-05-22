<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<jsp:include flush='true' page="../general/checkBrowser.jsp"/>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<app:checkLogon/>
<% String storeDir=ClwCustomizer.getStoreDir(); %>
<% boolean clwSwitch=ClwCustomizer.getClwSwitch(); %>


<html:html>
<head>
<title>Site Note</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="../externals/styles.css">
</head>


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
<html:form action="/console/crcsiteNote.do" enctype="multipart/form-data">
<bean:define id="theId"  name="CRC_SITE_NOTE_FORM" property="busEntityId"/>
<html:hidden  name="CRC_SITE_NOTE_FORM" property="busEntityId" value="<%=theId.toString()%>"/>
</tr>
</table>

<tr>
<!-- note text -->
<table cellspacing="0" border="0" width="769"  class="results">
<% 
  CleanwiseUser appUser = (CleanwiseUser) session.getAttribute("ApplicationUser");
  String userName = appUser.getUser().getUserName();
  SimpleDateFormat sdfDay = new SimpleDateFormat("MM/dd/yyyy");
  Date currentDay = sdfDay.parse(sdfDay.format(new Date()));
  String action = request.getParameter("action");
  SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
  NoteJoinView noteVw = theForm.getNote();
  NoteData noteD = noteVw.getNote();
  String title = noteD.getTitle();
  String noteModBy = noteD.getModBy();
  Date noteAddDate = noteD.getAddDate();
  if(title!=null && title.trim().length()>0 && 
    !"editTitle".equals(action)){
  String titleLink = "crcsiteNote.do?action=editTitle&noteId="+noteD.getNoteId();
%>
<td class='tableheader'><%=title%></td>
<td>
<% if(currentDay.before(noteAddDate) && userName.equals(noteModBy)) { %> 
<a href='<%=titleLink%>'>[Edit]</a>
<% } else { %>
&nbsp;
<% } %>
</td>
</tr>
<% } else { %>
<tr>
<td><b>Title: </b>
<html:text size='80' name="CRC_SITE_NOTE_FORM" property="note.note.title"/></td>
</tr>
<% } %>
<% 
  NoteTextData paragraphToEdit = theForm.getParagraph();
  int paragraphToEditId = paragraphToEdit.getNoteTextId();
  NoteTextDataVector noteTextDV = noteVw.getNoteText(); 
  if(noteTextDV!=null) {
  for(Iterator iter = noteTextDV.iterator(); iter.hasNext(); ) {
  NoteTextData noteTextD = (NoteTextData) iter.next();
  String addedBy = noteTextD.getUserFirstName()+" "+noteTextD.getUserLastName();
  String modBy = noteTextD.getModBy();
  Date addedOn = noteTextD.getAddDate();
  Date modDate = noteTextD.getModDate();
  String addedOnS = sdf.format(addedOn);
  String noteText = noteTextD.getNoteText(); 
%>
<tr><td colspan='2'><b>
Added by <%=addedBy%> on <%=addedOnS%>
</b></td>
</tr>
<%
  if(noteTextD.getNoteTextId()!=paragraphToEditId) {
    noteText = noteText.replaceAll("\t","&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
    noteText = noteText.replaceAll("\r\n","<br>");
    noteText = noteText.replaceAll("\r","<br>");
    noteText = noteText.replaceAll("\n","<br>");
    String noteTextIdS = ""+noteTextD.getNoteTextId();
    String noteLink = "crcsiteNote.do?action=editNote&noteTextId="+noteTextD.getNoteTextId();
%>
<tr>
<td><%=noteText%></td>
<td>
<% if(currentDay.before(modDate) && userName.equals(modBy)) { %> 
<a href='<%=noteLink%>'>[Edit]</a>
<% } else { %>
&nbsp;
<% } %>
</td>
</tr>
<tr><td colspan='2'>&nbsp</td>
</tr>
<% } else { %>
<tr><td colspan='2'>
<html:textarea cols='80' rows='10' name="CRC_SITE_NOTE_FORM" property="paragraph.noteText"/>
</td>
</tr>
<% } %>
<% }} %>
<% if(paragraphToEditId<=0) { %>
<tr><td colspan='2'>
<html:textarea cols='80' rows='10' name="CRC_SITE_NOTE_FORM" property="paragraph.noteText"/>
</td>
</tr>
<% } %>
</table>
        <!-- Control buttons -->
<table cellspacing="0" border="0" width="769"  class="mainbody">
<tr width='100%'>
<td>
<input type="submit" name="action" class="smalltext" value="Save Note"/>
</tr>
</table>
</html:form>
</div>
</body>
</html:html>



