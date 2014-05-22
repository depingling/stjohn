<%@ page language="java" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<%
   String s = (String)request.getRequestURI();
   String style = null;

%>
<table border="0" cellpadding="0" cellspacing="0" width="769">
<tr bgcolor="#000000">
<td class="tbartext">
<%
  if(s.indexOf("invoiceOp") > 0){
    style="tbarOn";
  }else{
    style="tbar";
  }
%>
<a class=<%=style%> href="invoiceOp.do">Distributor Invoices</a> |
<%
  if(s.indexOf("invoiceCustOp") > 0){
    style="tbarOn";
  }else{
    style="tbar";
  }
%>
<a class=<%=style%> href="invoiceCustOp.do">Customer Invoices</a>
</td>
</tr>
</table>
