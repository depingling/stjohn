<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="org.apache.struts.action.ActionErrors" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<app:checkLogon/>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="theForm" name="ESTIMATOR_MGR_FORM" type="com.cleanwise.view.forms.EstimatorMgrForm"/>

<% { %>

<html:html>

<head>
<link rel="stylesheet" href="../externals/styles.css">
<title>Estimator Management</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#FFFFFF">
<center>



<script language="JavaScript1.2">
<!--


//-->
</script>
<%
String action = (String) request.getAttribute("action");
ActionErrors ae = (ActionErrors) request.getAttribute("org.apache.struts.action.ERROR");
boolean errorFl = (ae!=null && ae.size()>0)? true:false;
int selectedPage = theForm.getSelectedPage();
String style = "tbar";
String linkProducts = "estimatorMgr.do?action=Products&selectedPage=101";
String linkProcedures = "estimatorMgr.do?action=CleaningProcedures&selectedPage=111";
String linkCleaningSchedules = "estimatorMgr.do?action=CleaningSchedules&selectedPage=121";
%>
<jsp:include flush='true' page="../reporting/ui/reportingToolbar.jsp"/>
    <!-- Estimator magager toolbar -->
    <table  cellpadding="0" cellspacing="0" width="769" border='1'  bordercolor='#00000'>
    <tr bgcolor="#000000"> 
    <td class="tbartext" align='center'>&nbsp;&nbsp;&nbsp;</td>
    <td class="tbartext" align='center'>
    <% style = (selectedPage==101)? "tbarOn":"tbar"; %>
    <a class=<%= style %> href="<%=linkProducts%>">Products</a> 
    </td>
    <td class="tbartext" align='center'>
    <% style = (selectedPage==111)? "tbarOn":"tbar"; %>
    <a class=<%= style %> href="<%=linkProcedures%>">Cleaning Actions</a> 
    </td>
    <td class="tbartext" align='center'>
    <% style = (selectedPage==121)? "tbarOn":"tbar"; %>
    <a class=<%= style %> href="<%=linkCleaningSchedules%>">Cleaning Procedures</a> 
    </td>
    </tr>
    </table>

<div class="rptmid">
<font color=red>
<html:errors/>
</font>
  <% if(selectedPage==101) {%>
<jsp:include flush='true' page="estimatorMgrProduct.jsp"/>
  <% } %>
  <% if(selectedPage==111) {%>
<jsp:include flush='true' page="estimatorMgrCleanProc.jsp"/>
  <% } %>
  <% if(selectedPage==121) {%>
<jsp:include flush='true' page="estimatorMgrCleanSched.jsp"/>
  <% } %>
</div>
<jsp:include flush='true' page="/console/ui/consoleFooter.jsp"/>

</body>

</html:html>

<% } %>


