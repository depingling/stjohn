<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<% String storeDir=ClwCustomizer.getStoreDir(); %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>connexion-tutorial</title>
<script src="/<%=storeDir%>/externals/AC_RunActiveContent.js" language="javascript"></script>
</head>
<body bgcolor="#ffffff">
<%String flashPath=ClwCustomizer.getRootFilePath(request, "content/connexion-tutorial.swf");%>
<%String flashName="content/connexion-tutorial";%>
    <script type="text/javascript">
        AC_FL_RunContent( 'codebase','http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,19,0','width','780','height','580','src','<%=flashPath%>','quality','high','pluginspage','http://www.macromedia.com/go/getflashplayer','movie','<%=flashName%>' );
    </script>
    <noscript>
      <object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,19,0" width="780" height="580">
        <param name="movie" value="<%=flashPath%>" />
        <param name="quality" value="high" />
        <embed src="<%=flashPath%>" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" width="780" height="580"></embed>
      </object>
      </noscript>
</body>
</html>

