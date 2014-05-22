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

<% boolean clwSwitch=ClwCustomizer.getClwSwitch(); %>

<bean:define id="theForm" name="STORE_DIST_NOTE_MGR_FORM" type="com.cleanwise.view.forms.StoreDistNoteMgrForm"/>

<div class = "text">
<font color=red><html:errors/></font>
<%
  String action = request.getParameter("action");
  String errors = (String) request.getAttribute("errors");
  int topicId = theForm.getTopicId();
  if(topicId <= 0){
%>
   <jsp:include flush='true' page="storeDistNoteTopicMgr.jsp"/>
<%
  } else {
%>
   <jsp:include flush='true' page="storeDistNoteTextMgr.jsp"/>
<%}%>
</div>
