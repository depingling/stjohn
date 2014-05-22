<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
  <meta http-equiv="Pragma" content="no-cache">  
  <meta http-equiv="Expires" content="-1">
  <meta http-equiv="Cache-Control" content="no-cache">
  <meta http-equiv="Cache-Control" content="private">

<jsp:include page='googleAnalytics.jsp'/>

<script src="../externals/lib.js" language="javascript"></script>
<logic:present name="pages.css">
  <logic:equal name="pages.css" value="styles.css">
    <link rel="stylesheet" href='../externals/<app:custom pageElement="pages.css"/>'>
  </logic:equal>
  <logic:notEqual name="pages.css" value="styles.css">
    <link rel="stylesheet" href='../externals/styles.css'>
    <link rel="stylesheet" href='../externals/<app:custom pageElement="pages.css"/>'>
  </logic:notEqual>
</logic:present>
<logic:notPresent name="pages.css">
  <link rel="stylesheet" href='../externals/styles.css'>
</logic:notPresent>
<jsp:include page='<%=ClwCustomizer.getStoreFilePath(request,"htmlHeadersStore.jsp")%>'/>
<link id="newsPrinterOnlyStyle"  rel="stylesheet" type="text/css" href="../externals/newsPrinterOnlyStyle.css" media="print"/>


