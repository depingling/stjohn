<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<app:checkLogon/>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="theForm" name="ORDER_OP_DETAIL_FORM"
  type="com.cleanwise.view.forms.OrderOpDetailForm"/>

<% 
 String action = (String)request.getParameter("action"); 
 String noteEditIdS = (String)request.getParameter("noteId");
 int noteEditId = (noteEditIdS==null)?0:Integer.parseInt(noteEditIdS);
 boolean addLineFl = ("addSiteNoteLine".equals(action))?true:false;
 boolean addNoteFl = ("addSiteNote".equals(action))?true:false;
%>


<html:html>

<head>
<title>Operations Console Home: Crs Site Notes</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="../externals/styles.css">
</head>

<body onload="return init();">
  <div class="text">
  <center>
    <font color=red>
      <html:errors/>
    </font>
  </center>

  <table border="0" cellpadding="1" cellspacing="0" width="750" class="mainbody">
  <html:form name="ORDER_OP_DETAIL_FORM" action="/console/orderOpDetail.do"
                              type="com.cleanwise.view.forms.OrderOpDetailForm">
    <% if(!addNoteFl) { %>
        <tr><td colspan='2' class='mediumheader'>
         <a href='orderOpDetail.do?action=addSiteNote'>[Add Note]</a>
        </td>
        </tr>
    <% } else { 
     OrderStatusDescData orderDescD = theForm.getOrderStatusDetail();
     int siteId = orderDescD.getOrderDetail().getSiteId();
    %>
    <tr><td>
    <table>
    <tr><td class='mediumheader'>
        Title: <html:text size='70' property='siteNoteTitle' value=''/></td></tr>
    <tr><td class="results">
      <html:textarea cols='80' rows='2' property='siteNoteLine' value=''/></td>
    </tr>
    </table>
    </td>
    <td><html:submit  property="action">Add Note</html:submit></td>
    </tr>
    <html:hidden property='siteId' value='<%=""+siteId%>'/>
    <% } %>

 
    <%
     SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
     NoteJoinViewVector njVwV = theForm.getSiteNotes();
     for(Iterator iter = njVwV.iterator(); iter.hasNext(); ) {
     NoteJoinView njVw = (NoteJoinView) iter.next();
     NoteData nD = njVw.getNote();
     NoteTextDataVector ntDV = njVw.getNoteText();
     int noteId = nD.getNoteId();
     String addRef = "orderOpDetail.do?action=addSiteNoteLine&noteId="+noteId;
    %>
    <tr><td colspan='2' class='mediumheader'><%=nD.getTitle()%>
     <a href='<%=addRef%>'>[Add Line]</a>
    </td>
    </tr>
    <% int sch =0;
      for(Iterator iter1 = ntDV.iterator(); iter1.hasNext(); ) {
      NoteTextData ntD = (NoteTextData) iter1.next();
    %>
    <% if(sch++>0) {%>
    <tr><td colspan="2"><hr></td></tr>
    <% } %>
    <% if(sch==1 && addLineFl && noteEditId==noteId) { %>
    <tr><td><html:textarea cols='80' rows='2' property='siteNoteLine' value=''/></td>
    <html:hidden property='noteId' value='<%=""+noteId%>'/>
    <td><html:submit  property="action">Save Line</html:submit></td></tr>
    <% } %>
    <tr><td class="results"><%=ntD.getNoteText()%></td>
       <td><%=ntD.getUserFirstName()%> <%=ntD.getUserLastName()%>
       <%=sdf.format(ntD.getAddDate())%>
       </td>
    </tr>
  <% } %>
  <% }  %>
  <tr><td colspan='2'><hr></td></tr>
  </html:form>
  </table>
</body>
</html:html>
