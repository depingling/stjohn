<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<bean:define id="spath" name="pages.store.templates"/>

<link rel="stylesheet" href='../externals/styles.css'>
<template:insert template="cwTemplate.jsp">
  <template:put name="title">
  Problem Report
  </template:put>
  <template:put name="content" content="/admin2.0/t_excBody.jsp"/>
  <template:put name="footer"  content="/admin2.0/t_footer.jsp"/>
</template:insert>

