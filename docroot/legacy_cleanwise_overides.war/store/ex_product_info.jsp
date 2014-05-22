<%@ page language="java" %>

<%
String pg = request.getRequestURI()+'?'+request.getQueryString();
pg = pg.replaceFirst("cleanwise","defst");
response.sendRedirect(pg);
%>
