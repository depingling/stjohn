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
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<app:checkLogon/>
<% String storeDir=ClwCustomizer.getStoreDir(); %>
<% boolean clwSwitch=ClwCustomizer.getClwSwitch(); %>

<script language="JavaScript1.2">
<!--
function f_st() {

  if (  !document.forms[0].skuType[1].checked &&
        !document.forms[0].skuType[2].checked ) {
        document.forms[0].skuType[0].checked='true';
        }
}

function popManufLocate(name,name1,formNum) {
  var loc = "manuflocate.do?feedField=" + name+"&feedDesc="+name1+"&returnFormNum="+formNum;
  locatewin = window.open(loc,"tickersearch", "menubar=no,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
  locatewin.focus();

  return false;
}


//-->
</script>

<html:html>
<head>
<title>Account Note</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="../externals/styles.css">
</head>


<bean:define id="theForm" name="ACCOUNT_NOTE_MGR_FORM" type="com.cleanwise.view.forms.NoteMgrForm"/>
<body bgcolor="#cccccc">


<div class = "text">

<table cellspacing="0" border="0" width="769"  class="mainbody">
<tr>
<td><b>Account&nbsp;Id:</b>
<bean:write name="ACCOUNT_NOTE_MGR_FORM" property="busEntityId"/></td>
 <td><b>Name:</b>
 <bean:write name="ACCOUNT_NOTE_MGR_FORM" property="busEntityName"/></td>
</td>
<html:form action="/adminportal/accountmgrNote.do" enctype="multipart/form-data">
<bean:define id="theId"  name="ACCOUNT_NOTE_MGR_FORM" property="busEntityId"/>
<html:hidden  name="ACCOUNT_NOTE_MGR_FORM" property="busEntityId" value="<%=theId.toString()%>"/>
</tr>
<tr>
<td colspan='2'><b>Topic:</b>
<bean:write name="ACCOUNT_NOTE_MGR_FORM" property="topicName"/>&nbsp;&nbsp;&nbsp;
<a href="accountmgrNote.do?action=selectTopic">[Change Topic]</a>
</td>
</table>

<tr>
<!-- note text -->
<table cellspacing="0" border="0" width="769"  class="results">
<% 
  String action = request.getParameter("action");
  SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
  NoteJoinView noteVw = theForm.getNote();
  NoteData noteD = noteVw.getNote();
  String title = noteD.getTitle();
  if(title!=null && title.trim().length()>0 && 
    !"editTitle".equals(action)){
  String titleLink = "accountmgrNote.do?action=editTitle&noteId="+noteD.getNoteId();
%>
<td class='tableheader'><%=title%></td>
<td><a href='<%=titleLink%>'>[Edit]</a></td>
<td>&nbsp;</td>
</tr>
<% } else { %>
<tr>
<td><b>Title: </b>
<html:text size='80' name="ACCOUNT_NOTE_MGR_FORM" property="note.note.title"/></td>
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
  Date addedOn = noteTextD.getAddDate();
  String addedOnS = sdf.format(addedOn);
  String noteText = noteTextD.getNoteText(); 
%>
<tr><td colspan='3'><b>
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
    String noteLink = "accountmgrNote.do?action=editNote&noteTextId="+noteTextD.getNoteTextId();
%>
<tr>
<td><%=noteText%></td>
<td><a href='<%=noteLink%>'>[Edit]</a></td>
<td><html:multibox name="ACCOUNT_NOTE_MGR_FORM" property="selectorBox" value="<%=noteTextIdS%>" /></td>
</tr>
<tr><td colspan='3'>&nbsp</td>
</tr>
<% } else { %>
<tr><td colspan='3'>
<html:textarea cols='80' rows='10' name="ACCOUNT_NOTE_MGR_FORM" property="paragraph.noteText"/>
</td>
</tr>
<% } %>
<% }} %>
<% //Attachmets
  NoteAttachmentDataVector noteAttachDV = noteVw.getNoteAttachment(); 
  if(noteAttachDV!=null && noteAttachDV.size()>0) {
%>
<tr><td colspan='3'><b>Attachmets</b></td>
</tr>
<%
  for(Iterator iter = noteAttachDV.iterator(); iter.hasNext(); ) {
    NoteAttachmentData naD = (NoteAttachmentData) iter.next();
    String fileName = naD.getFileName();
    String href = "accountmgrNote.do?action=attachment&noteId="+noteD.getNoteId()+"&file="+fileName;
%>
<tr><td colspan='2'><A href="<%=href%>"><%=fileName%></A></td>
<td><html:multibox name="ACCOUNT_NOTE_MGR_FORM" property="attachSelectBox" value="<%=fileName%>" /></td>
</tr>
 
<%
  }}
%>
<% if(paragraphToEditId<=0) { %>
<tr><td colspan3>
<html:textarea cols='80' rows='10' name="ACCOUNT_NOTE_MGR_FORM" property="paragraph.noteText"/>
</td>
</tr>
<tr><td colspan='3'>
<b>Add Attachment: </b>
       <html:file name="ACCOUNT_NOTE_MGR_FORM" property="attachFile" size="80"/>
          <!--    accept="image/jpeg,image/gif"/> -->

</td>
</tr>
<% } %>
</table>
        <!-- Control buttons -->
<table cellspacing="0" border="0" width="769"  class="mainbody">
<tr width='100%'>
<td>
<input type="submit" name="action" class="smalltext" value="Save Note"/>
</td>
<td align='right'>
<input align='right' type="submit" name="action" class="smalltext" value="Delete Selected"/>
</td>
</tr>
</html:form>
</div>
</body>
</html:html>



