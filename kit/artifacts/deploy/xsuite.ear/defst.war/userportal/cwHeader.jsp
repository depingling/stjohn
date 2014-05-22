<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>

<%-- 
This header is here as a placeholder.  I merely includes the store specific
header.  Do not modify this file.  Modify the header in the store and 
language specific directory.

durval (11/19/01)

--%>

<% 
String hs = ClwCustomizer.getStoreFilePath(request, "t_cwHeader.jsp"); 
%>

<jsp:include flush='true' page="<%=hs%>"/>


