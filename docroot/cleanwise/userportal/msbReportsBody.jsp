
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.service.api.value.*"%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<table align=center CELLSPACING=0 CELLPADDING=0 width="<%=Constants.TABLEWIDTH%>">
<tr>
<td class="top3dk"  width="<%=Constants.TABLEWIDTH%>"><img src="../<%=ip%>images/cw_spacer.gif" height="5" width="1"></td>
</tr>
<bean:define id="toolBarTab" type="java.lang.String" value="reports"
toScope="request"/>
<jsp:include flush='true' page="msbToolbar.jsp"/> 

<tr>
<td>
<table align=center CELLSPACING=0 CELLPADDING=0 width="<%=Constants.TABLEWIDTH%>">
<tr>
<td  bgcolor="black" width="1"><img src="../<%=ip%>images/cw_spacer.gif" height="1" width="1"></td>
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
<td bgcolor="black" width="1"><img src="../<%=ip%>images/cw_spacer.gif" height="1" width="1"></td>
</tr>
</table>

</td>
</tr>

<tr>
<td><img src="../<%=ip%>images/cw_rootfooter.gif" WIDTH="<%=Constants.TABLEWIDTH%>" HEIGHT="23"></td>
</tr>

</table>


