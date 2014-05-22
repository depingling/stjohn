<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.service.api.value.*"%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<head>
<META HTTP-EQUIV=Refresh CONTENT="10; URL="../userportal/customerhome.do">
  <meta http-equiv="Pragma" content="no-cache">  
  <meta http-equiv="Expires" content="-1">
  <meta http-equiv="Cache-Control" content="no-cache">
  <meta http-equiv="Cache-Control" content="private">
</head>
<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>

<div class="text"><font color=red><html:errors/></font></div>

<table CELLSPACING=0 CELLPADDING=0 width="<%=Constants.TABLEWIDTH%>" align=center>
<tr>
<td>
	<bean:write filter="false" name="systemMessage"/>
	<br>
	<a href="../userportal/logon.do" alt="sys_message_continue" id="sys_message_continue">Click Here</a> to continue otherwise you will automatically be directed to the log-in screen in 10 seconds.
    <br>
    <br>
    <br>
    <br>
    <br>
</td>
</tr>
</table>




