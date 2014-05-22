<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.*" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<table align="center" border="0" cellpadding="0" cellspacing="0" width="769">
<tr>
<td class="smalltext"><br><div class="fivemargin"><app:custom pageElement="pages.footer.msg"/></div></td>
<td class="smalltext" align="right"><br><img src='<app:custom  pageElement="pages.logo1.image" addImagePath="true" encodeForHTML="true"/>' border="0"></td>
</tr>
<tr>
<td class="smalltext"> <span style="font-size: 10px">
User: <%=(String) session.getAttribute(com.cleanwise.view.utils.Constants.USER_NAME)%>&nbsp;&nbsp;&nbsp;&nbsp;
Server time: <%=(new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss")).format(new java.util.Date())%>&nbsp;&nbsp;&nbsp;&nbsp;
Session id: <%=session.getId()%>&nbsp;&nbsp;&nbsp;&nbsp;
Request id: <%=(String) request.getAttribute("requestId")%>&nbsp;&nbsp;&nbsp;&nbsp;
Server: <%=System.getenv( "HOSTNAME" )%>
</span>
</td>
</tr>
</table>
<head>
  <meta http-equiv="Pragma" content="no-cache">
  <meta http-equiv="Expires" content="-1">
  <meta http-equiv="Cache-Control" content="no-cache">
</head>
