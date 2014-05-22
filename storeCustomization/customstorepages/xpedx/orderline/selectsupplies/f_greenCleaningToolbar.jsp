<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.SessionTool" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>


<%
String display = (String)request.getParameter("display");
String t_templatorToolBar = ClwCustomizer.getStoreFilePath(request,"t_templatorToolBar.jsp");
String rootDir = (String) session.getAttribute("store.dir");
%>

<jsp:include flush='true' page="<%=t_templatorToolBar%>" >
   <jsp:param name="display"  value="<%=display%>"/>

   <jsp:param name="toolLink01"  value=""/>
   <jsp:param name="tabs01"  value=""/>
   <jsp:param name="display01"  value=""/>
   <jsp:param name="toolLable01" value=""/>
   <jsp:param name="toolSecondaryToolBar01" value=""/>


   <jsp:param name="color01" value="#FFFFFF"/>
   <jsp:param name="color02" value="#000000"/>
   <jsp:param name="color03" value="#ff9900"/>

   <jsp:param name="headerLabel" value="template.xpedx.apple.menu.header.green"/>
</jsp:include>  