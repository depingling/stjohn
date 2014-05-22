<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.cleanwise.service.api.util.SessionDataUtil"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer"%>
<%@ page import="com.cleanwise.view.utils.Constants"%>
<%@ page import="java.util.Locale"%>

<%@ taglib uri='/WEB-INF/application.tld' prefix='app' %>
<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='template' %>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%
SessionDataUtil sessionData = Utility.getSessionDataUtil(request);
//If StJohn is accessed from Neptune application, Only Content section needs to be returned.
//The following if condtions do that thing.
%>

<%if(!sessionData.isContentOnly()) {%>
	<template:useAttribute id="ipAddressInfo" name="ipAddressInfo" ignore="true" classname="java.lang.String"/>
	<logic:present name="ipAddressInfo">
	 	<template:getAsString name="ipAddressInfo"/>
	</logic:present>
	
	
	<html xmlns="http://www.w3.org/1999/xhtml" >
		
	    <head>
		<title><template:getAsString name='title'/></title>
<%} %>
	
	<%
		String headerIncludesPage = ClwCustomizer.getStoreFilePath(request, Constants.USERPORTAL_ESW, "htmlHeaderIncludes.jsp");
	%>
  		
  		<jsp:include flush='true' page="<%= headerIncludesPage%>" />
<%if(!sessionData.isContentOnly()) {%>
		</head>
		<template:useAttribute id="body" name="body" ignore="true" classname="java.lang.String"/>
	  	<logic:present name="body">
	    	<template:getAsString name="body"/>
	  	</logic:present>
	  	<logic:notPresent name="body">
	  		<body>
	  	</logic:notPresent>
		
		
	<!-- START PAGE HEADER SECTION -->
		
		<template:useAttribute id="header" name="header" ignore="true" classname="java.lang.String"/>
	  	<logic:present name="header">
	    	<template:get name="header" flush="true" ignore="true"/>
	  	</logic:present>
		
	<!-- END PAGE HEADER SECTION -->
<%} %>

	<!-- START PAGE CONTENT -->
		<template:useAttribute id="content" name="content" ignore="true" classname="java.lang.String"/>
	  	<logic:present name="content">
	    	<template:get name="content" flush="true" ignore="true"/>
	  	</logic:present>
	<!-- END PAGE CONTENT -->

<%if(!sessionData.isContentOnly()) {%>
	<!-- START PAGE FOOTER SECTION -->
		
		<template:useAttribute id="footer" name="footer" ignore="true" classname="java.lang.String"/>
	  	<logic:present name="footer">
	    	<template:get name="footer" flush="true" ignore="true"/>
	  	</logic:present>
	  	
		
		
	<!-- END PAGE FOOTER SECTION -->
		</body>
		
	</html>
<%} %>
