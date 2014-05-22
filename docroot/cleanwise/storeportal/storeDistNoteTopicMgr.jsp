<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<jsp:include flush='true' page="../general/checkBrowser.jsp"/>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<script language="JavaScript">
<!--
function addNote(topicId) {
  document.forms[0].topicId.value=topicId;
  document.forms[0].submit();
}
//-->
</script>

<bean:define id="theForm" name="STORE_DIST_NOTE_MGR_FORM" type="com.cleanwise.view.forms.StoreDistNoteMgrForm"/>

<div class = "text">

<table ID=781 cellspacing="0" border="0" width="769"  class="mainbody">
<tr>
<td><b>Distributor&nbsp;Id:</b></td>
<td><bean:write name="STORE_DIST_NOTE_MGR_FORM" property="busEntityId"/></td>
<td><b>Name:</b></td>
<td>
<td><bean:write name="STORE_DIST_NOTE_MGR_FORM" property="busEntityName"/></td>
</td>
</tr>
</table>

<html:form styleId="782" action="/storeportal/distnotemgr.do">
<bean:define id="theId"  name="STORE_DIST_NOTE_MGR_FORM" property="busEntityId"/>
<html:hidden  name="STORE_DIST_NOTE_MGR_FORM" property="busEntityId" value="<%=theId.toString()%>"/>
<input type="hidden"  name="topicId" value=""/>
<input type="hidden"  name="action" value="Add Note"/>

<table ID=783 cellspacing="0" border="0" width="769">
<%
  PropertyDataVector topics = theForm.getTopics();
  if (topics != null && topics.size() > 0) {
    int i = 0;
    ArrayList noteTitles = theForm.getNoteTitles();
    for (Iterator iter = topics.iterator(); iter.hasNext();) {
      PropertyData propD = (PropertyData) iter.next();
      %>
      <tr><td class="notesheader" colspan="2"><%=propD.getValue()%></td></tr>
    <%
      if (noteTitles != null && noteTitles.size() > 0) {
        NoteViewVector notes = (NoteViewVector)noteTitles.get(i);
        i++;
        if (notes != null && notes.size() > 0) {
          for (Iterator noteiter = notes.iterator(); noteiter.hasNext();) {
            NoteView note = (NoteView) noteiter.next();
            String noteTitle = note.getTitle();
            String noteLink = "distnotemgr.do?action=readNote&noteId="+note.getNoteId()+"&topicId="+propD.getPropertyId();
    %>
      <tr>
        <td class="notestxt">&nbsp;&nbsp;&nbsp;<a ID=784 href="<%=noteLink%>"><%=noteTitle%></a></td>
      </tr>
       <%} %>
     <%} %>
   <%} %>
     <tr>
        <td class="addnotelnk">&nbsp;&nbsp;&nbsp;<a ID=785 href="#" onclick="addNote(<%=propD.getPropertyId()%>); return false;">Add Note</a></td>
     </tr>
 <%} %>
<%}%>
</table>

</html:form>
</div>

