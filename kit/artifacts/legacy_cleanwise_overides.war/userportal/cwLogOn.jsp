<%@ page language="java" %>
<%
String uri = "/defst/userportal/logon.do";
uri = uri + "?" + request.getQueryString();
uri = uri.replaceFirst("cleanwise", "defst");
%>
<script>
location.href ="<%=uri%>"
</script>