
<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<bean:define id="theForm" name="MSDS_FORM" type="com.cleanwise.view.forms.MsdsSpecsForm"/>
<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>
<% String storeDir=ClwCustomizer.getStoreDir(); %>


<html:html>
<head>

<title>MSDS & Specs</title>
<jsp:include flush='true' page="../userportal/htmlHeaderIncludes.jsp"/>
</script>
</head>
<body marginheight="0" topmargin="0" marginwidth="0" leftmargin="0">

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="storeLocale" value="en_US"/>
<bean:define id="storePrefix" value="CLW"/>

<%
String hs = ClwCustomizer.getFilePath(session, "..", "t_cwHeader.jsp"); 
String fs = ClwCustomizer.getFilePath(session, "..", "t_footer.jsp");
String lStoreMsds = ClwCustomizer.getFilePath(session, "..", "msdsTemplate.jsp");
String lMsdsToolbar = ClwCustomizer.getFilePath(session, "..", "t_msdsToolbar.jsp");

 

%>

  <jsp:include flush='true' page="<%=hs%>"/>
  
<table align="center" border="0" cellpadding="0" cellspacing="0" 
width="<%=Constants.TABLEWIDTH%>">
<% if(request.getAttribute("org.apache.struts.action.ERROR")!=null) { %>
<tr>
<td class="tableoutline" width="1" bgcolor="black"><img 
src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="1" width="1"></td>
<td class="genericerror" align="center"><html:errors/></td>
<td class="tableoutline" width="1" bgcolor="black"><img 
src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="1" width="1"></td>
</tr>
<% } %>
<tr>
<td class="tableoutline" width="1">
<img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="1" width="1"></td>
<td class="msdsdk"><jsp:include flush='true' page="<%=lMsdsToolbar%>"/></td>
<td class="tableoutline" width="1"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" 
height="1" width="1"></td>
</tr>
<!-- content -->
<tr>
<td class="tableoutline" width="1"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="1" width="1"></td>
<td>  <jsp:include flush='true' page="<%=lStoreMsds%>"/></td>
<td class="tableoutline" width="1"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="1" width="1"></td>  

<table align="center" border="0" cellpadding="0" cellspacing="0" 
width="<%=Constants.TABLEWIDTH%>">
<tr>
<td>
 <img src="/<%=storeDir%>/<%=ip%>images/cw_left_footer_shop.gif" ALIGN="top">
</td>
<td>
 <img src="/<%=storeDir%>/<%=ip%>images/cw_middle_footer_shop.gif" ALIGN="top" 
width="<%=Constants.TABLE_BOTTOM_MIDDLE_BORDER_WIDTH%>" height="8">
</td>
<td>
 <img src="/<%=storeDir%>/<%=ip%>images/cw_right_footer_shop.gif" ALIGN="top">
</td>
</tr>
</table>

  
  <jsp:include flush='true' page="<%=fs%>"/>

</body>
</html:html>






