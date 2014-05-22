<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="java.util.Iterator" %>
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
<title>Site Note Topic</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="../externals/styles.css">
</head>


<bean:define id="theForm" name="SITE_NOTE_MGR_FORM" type="com.cleanwise.view.forms.NoteMgrForm"/>

<body bgcolor="#cccccc">


<div class = "text">

<table cellspacing="0" border="0" width="769"  class="mainbody">
<tr>
<td><b>Site&nbsp;Id:</b></td>
<td><bean:write name="SITE_NOTE_MGR_FORM" property="busEntityId"/></td>
 <td><b>Name:</b></td>
<td>
<td><bean:write name="SITE_NOTE_MGR_FORM" property="busEntityName"/></td>
</td>
<html:form action="/adminportal/sitemgrNote.do" focus="skuTempl" >
<bean:define id="theId"  name="SITE_NOTE_MGR_FORM" property="busEntityId"/>
<html:hidden  name="SITE_NOTE_MGR_FORM" property="busEntityId" value="<%=theId.toString()%>"/>
</tr>
</table>
<table cellspacing="0" border="0" width="769"  class="results">
<% 
  PropertyData topicToEdit = theForm.getTopicToEdit();
  int topicToEditId = 0;
  if(topicToEdit!=null) {
    topicToEditId = topicToEdit.getPropertyId();
  }
  PropertyDataVector topics = theForm.getTopics(); 
  if(topics!=null && topics.size()>0) {
%>
<tr>
<td class="tableheader">Select Site Note Topic</td>
<td>&nbsp;</td>
</tr>
<% 
 for(Iterator iter = topics.iterator(); iter.hasNext();) {
   PropertyData propD = (PropertyData) iter.next();
   int topicId = propD.getPropertyId();
   if(topicId!=topicToEditId) {
     String topicLink = "sitemgrNote.do?action=loadTopic&topicId="+propD.getPropertyId();
     String delLink = "sitemgrNote.do?action=deleteTopic&topicId="+propD.getPropertyId();
     String editLink = "sitemgrNote.do?action=editTopic&topicToEditId="+propD.getPropertyId();
     String topicName = propD.getValue();
     String topicIdS = ""+topicId;
   
%>
<tr>
<td><a href="<%=topicLink%>"><%=topicName%></a> </td>
<td><a href="<%=editLink%>">[Edit]</a>&nbsp;<a href="<%=delLink%>">[Delete]</a> </td>
</tr>
<%} else {%>
<tr>
<td><html:text name="SITE_NOTE_MGR_FORM" property="topicToEdit.value"/> </td>
<td><input type=submit name="action" class="smalltext" value="Update Topic"/> </td>
</tr>
<%}%>
<%}%>
<%}%>
<% if(topicToEditId==0) { %>
<tr><td colsan = '2'>
<html:text  name="SITE_NOTE_MGR_FORM" property="topicName"/>
<input type=submit name="action" class="smalltext" value="Add Topic"/>
</td>
</tr>
<%}%>
</html:form>

</table>

</div>
</body>
</html:html>

