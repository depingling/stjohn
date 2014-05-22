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
<title>Search for Account Notes</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="../externals/styles.css">
</head>

<script language="JavaScript1.2">
<!--


//-->
</script>

<bean:define id="theForm" name="ACCOUNT_NOTE_MGR_FORM" type="com.cleanwise.view.forms.NoteMgrForm"/>

<body bgcolor="#cccccc">


<div class = "text">

<table ID=1541 cellspacing="0" border="0" width="769"  class="mainbody">
<tr>
<td><b>Account&nbsp;Id:</b>
<bean:write name="ACCOUNT_NOTE_MGR_FORM" property="busEntityId"/></td>
 <td><b>Name:</b>
 <bean:write name="ACCOUNT_NOTE_MGR_FORM" property="busEntityName"/></td>
</td>
<html:form styleId="1542" action="/storeportal/accountmgrNote.do" >
<bean:define id="acctId" name="ACCOUNT_NOTE_MGR_FORM" property="busEntityId"/>
<html:hidden  name="ACCOUNT_NOTE_MGR_FORM" property="busEntityId" value="<%=acctId.toString()%>"/>
</tr>
<tr>
<td colspan='2'><b>Topic:</b>
<bean:write name="ACCOUNT_NOTE_MGR_FORM" property="topicName"/>&nbsp;&nbsp;&nbsp;
<a ID=1543 href="accountmgrNote.do?action=selectTopic">[Change Topic]</a>
</td>
</tr>
<tr>
<td colspan='2'><b>Key Words:</b>
<html:text name="ACCOUNT_NOTE_MGR_FORM" size='50' property="keyWord"/>&nbsp;&nbsp;&nbsp;
<b>Include Note Text:</b>
<html:checkbox name="ACCOUNT_NOTE_MGR_FORM" property="textAlsoFl"/>
</td>
</tr>
<tr>
<td colspan='2'>
<input type=submit name="action" class="smalltext" value="Search"/>
<input type=submit name="action" class="smalltext" value="View All"/>
<input type=submit name="action" class="smalltext" value="Add Note"/>
</td>
</tr>
</table>
<table ID=1544 cellspacing="0" border="0" width="769"  class="results">
<% 
  NoteViewVector notes = theForm.getNoteList(); 
  if(notes!=null && notes.size()>0) {
%>
<tr>
<td class="tableheader"><a ID=1545 href='accountmgrNote.do?action=sortNotes&field=noteTitle'>Note Title</a></td>
<td class="tableheader"><a ID=1546 href='accountmgrNote.do?action=sortNotes&field=modifyDate'>Modify Date</a></td>
<td class="tableheader"><a ID=1547 href='accountmgrNote.do?action=sortNotes&field=searchRating'>Search Rating</a></td>
<td class="tableheader">Keywords Found</td>
<td>&nbsp;</td>
</tr>
<% 
     SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
     for(Iterator iter = notes.iterator(); iter.hasNext();) {
       NoteView note = (NoteView) iter.next();
       String noteIdS = ""+note.getNoteId();
       String noteTitle = note.getTitle();
       int rating = note.getSearchRate();
       Date modDate = note.getModDate();
       String modDateS = sdf.format(modDate);
       ArrayList kwAL = note.getKeyWords();
       String kwListS = "";
       if(kwAL!=null) {
           for(Iterator iter1 = kwAL.iterator(); iter1.hasNext(); ) {
             String kw = (String) iter1.next();
             if(kwListS.length()>0) kwListS += ", ";
             kwListS += kw;
           } 
       }
   String noteLink = "accountmgrNote.do?action=readNote&noteId="+note.getNoteId();
%>
<tr>
<td><a ID=1548 href="<%=noteLink%>"><%=noteTitle%></a> </td>
<td><%=modDateS%></td>
<td><%=rating%></td>
<td><%=kwListS%></td>
<td><html:multibox name="ACCOUNT_NOTE_MGR_FORM" property="selectorBox" value="<%=noteIdS%>" /></td>
</tr>
<%}%>
<tr>
<td colspan = '5' align='RIGHT'>
<input type=submit name="action" class="smalltext" value="Delete Notes"/>
</td>
</tr>
<%}%>
</html:form>

</table>

</div>
</body>
</html:html>

