

<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<bean:define id="headerLabel" value="template.jd.bsc.heading.operations"/>

<% 
String t_templatorToolBar = ClwCustomizer.getStoreFilePath(request,"t_templatorToolBar.jsp"); 
String rootDir = (String) session.getAttribute("store.dir");

%>
<jsp:include flush='true' page="<%=t_templatorToolBar%>" >

   <jsp:param name="toolLink01"  value=""/>
   <jsp:param name="tabs01"  value="marketingToolBar"/>
   <jsp:param name="display01"  value="services"/>
   <jsp:param name="toolLable01" value="template.jd.bsc.menu.child.services"/>
   <jsp:param name="toolSecondaryToolBar01" value=""/>

   <jsp:param name="toolLink02"  value=""/>
   <jsp:param name="tabs02"  value="marketingToolBar"/>
   <jsp:param name="display02"  value="training"/>
   <jsp:param name="toolLable02" value="template.jd.bsc.menu.child.training"/> 
   <jsp:param name="toolSecondaryToolBar02" value=""/>

   <jsp:param name="toolLink03"  value=""/>
   <jsp:param name="tabs03"  value="marketingToolBar"/>
   <jsp:param name="display03"  value="auditing"/>
   <jsp:param name="toolLable03" value="template.jd.bsc.menu.child.auditing"/>
   <jsp:param name="toolSecondaryToolBar03" value=""/>

   <jsp:param name="toolLink04"  value=""/>
   <jsp:param name="tabs04"  value="marketingToolBar"/>
   <jsp:param name="display04"  value="sqf"/>
   <jsp:param name="toolLable04" value="template.jd.bsc.menu.child.sqf"/>
   <jsp:param name="toolSecondaryToolBar04" value=""/>

   <jsp:param name="toolLink05"  value=""/>
   <jsp:param name="tabs05"  value="marketingToolBar"/>
   <jsp:param name="display05"  value="consulting"/>
   <jsp:param name="toolLable05" value="template.jd.bsc.menu.child.consulting"/>
   <jsp:param name="toolSecondaryToolBar05" value=""/>

   <jsp:param name="color01" value="#FFFFFF"/>
   <jsp:param name="color02" value="#003366"/>
   <jsp:param name="color03" value="#006699"/>


</jsp:include>    
