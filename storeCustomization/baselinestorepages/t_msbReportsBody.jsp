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

<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>

<div class="text"><font color=red><html:errors/></font></div>

<table align=center CELLSPACING=0 CELLPADDING=0 width="<%=Constants.TABLEWIDTH%>">

<bean:define id="toolBarTab" type="java.lang.String" value="reports"
  toScope="request"/>
<jsp:include flush='true' page="f_msbToolbar.jsp"/> 

<tr>
<td>
<table align=center CELLSPACING=0 CELLPADDING=0 width="<%=Constants.TABLEWIDTH%>">
<tr>
<td  bgcolor="black" width="1"><img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1"></td>
<td>
<table align=center CELLSPACING=0 CELLPADDING=0 width="647">
<tr><td>
<br><br>
<br><br>
<br><br>
<br><br>
</td></tr>
</table>
</td>
<td bgcolor="black" width="1"><img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1"></td>
</tr>
</table>

</td>
</tr>

<tr>
<td><img src="<%=IMGPath%>/cw_rootfooter.gif" WIDTH="<%=Constants.TABLEWIDTH%>" HEIGHT="23"></td>
</tr>

</table>

