
<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page import="com.cleanwise.service.api.value.*"%>
<%@ page import="java.util.Iterator"%>


<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%
 String query=request.getQueryString();
 String currUri=SessionTool.getActualRequestedURI(request) +"?"; 
 if(query!=null){
     currUri+=query+"&";
 }
 currUri = SessionTool.removeRequestParameter(currUri,"section");

SessionTool st = new SessionTool(request);
CleanwiseUser appUser = st.getUserData(); 
AccountData ad = appUser.getUserAccount();
%>


<% 
if ( ad.getHowToCleanTopicId() > 0 ) {
  NoteViewVector noteVwV = ad.getNoteList(); 
%>

<td>
<br><br>
<ul>

<% 
for(Iterator iter = noteVwV.iterator(); iter.hasNext(); ) {
NoteView noteVw = (NoteView)iter.next();

String noteTitle = noteVw.getTitle();
int noteId = noteVw.getNoteId();
String href = currUri + "action=readNote&noteId="+noteId+"&section=how";
%> 
<li class="trainingbullet"><span class="text"><%=noteTitle%></span></li>
<% 
NoteJoinView noteJVw = ad.getNote(noteId);
NoteAttachmentDataVector naDV = noteJVw.getNoteAttachment();
if(naDV!=null &&naDV.size()>0) {
%>

           <menu>
       <%
for(Iterator iter1 = naDV.iterator(); iter1.hasNext();) {
          NoteAttachmentData naD = (NoteAttachmentData) iter1.next();
          String fileName = naD.getFileName();
          String hrefDownlad = currUri + "action=downloadAttachment&file="+fileName+"&section=how";
          String hrefDownlad2 = "/defst/trainingContent/training.do?action=downloadAttachment&file="+fileName+"&section=how"
	  + "&noteId=" + noteId ;

       %>
  <li DINGBAT='&nbsp' class="trainingbullet"><a href="<%=hrefDownlad2%>" ><%=fileName%></a></li>
<% } %>
          </menu>

<%
}
%>

<% } /* help topic is set */ %>


</ul></td>

<% } %>



