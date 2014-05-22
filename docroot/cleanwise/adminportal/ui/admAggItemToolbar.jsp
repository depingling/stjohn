<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<%
   String s = (String)request.getRequestURI();
   s += " " + request.getParameter("action");
   String style = null;
   style = "tbar";
%>
<table border="0" cellpadding="0" cellspacing="0" width="769">
  <tr bgcolor="#000000">
<td class="tbartext">
<a class=<%=style%> href="aggregateItem.do?action=init">Filter By Store, Account and Distributor</a>|
<a class=<%=style%> href="aggregateItem.do?action=catInit">Filter By Catalog</a>
</td>
  </tr>
</table>
