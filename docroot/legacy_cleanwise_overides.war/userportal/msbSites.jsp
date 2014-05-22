<%@ page language="java" %>
<%
String uri = "/@serverName@/userportal/logon.do";
uri = uri + "?" + request.getQueryString();
uri = uri.replaceFirst("cleanwise", "@serverName@");
%>
<script>
location.href ="<%=uri%>"
</script>