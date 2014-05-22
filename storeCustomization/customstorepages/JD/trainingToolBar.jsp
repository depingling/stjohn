

<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>


<% String storeDir=ClwCustomizer.getStoreDir(); %>
<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/> 
<% 
String t_templatorToolBar = ClwCustomizer.getStoreFilePath(request,"t_templatorToolBar.jsp"); 
String rootDir = (String) session.getAttribute("store.dir");
String toolLink01 = "/"+rootDir+"/userportal/templator.do";
String toolLink02 = "/"+rootDir+"/userportal/msdsTemplate.do";
String toolLink03 = "/"+rootDir+"/userportal/templator.do";
%>




<%String display = (String)request.getParameter("display");%>

<bean:define id="color01" value="#FFFFFF"/>
<bean:define id="color02" value="#003366"/>
<bean:define id="color03" value="#006699"/>

<bean:define id="headerLabel" value="shop.heading.training"/>

<jsp:include flush="true" page="<%=t_templatorToolBar%>" >
    <jsp:param name="toolLink01" value="<%=toolLink01%>"/>
    <jsp:param name="tabs01" value="trainingToolBar"/>
    <jsp:param name="display01" value="customtrainingflash"/>
    <jsp:param name="toolLable01" value="template.jd.bsc.menu.trainingFlash"/>
    <jsp:param name="toolSecondaryToolBar01" value=""/>
 
    <jsp:param name="toolLink02" value="<%=toolLink02%>"/>
    <jsp:param name="tabs02" value="trainingToolBar"/>
    <jsp:param name="display02" value="customsupervisortools"/>
    <jsp:param name="toolLable02" value="template.jd.bsc.menu.toolsForSupervisor"/>
    <jsp:param name="toolSecondaryToolBar02" value=""/>

	<jsp:param name="toolLink03" value="<%=toolLink03%>"/>
    <jsp:param name="tabs03" value="trainingToolBar"/>
    <jsp:param name="display03" value="customcleanertools"/>
    <jsp:param name="toolLable03" value="template.jd.bsc.menu.toolsForCleaners"/>
    <jsp:param name="toolSecondaryToolBar03" value=""/>
	
    <jsp:param name="color01" value="#FFFFFF"/>
    <jsp:param name="color02" value="#003366"/>
    <jsp:param name="color03" value="#006699"/>

    <jsp:param name="headerLabel" value="shop.heading.training"/>
</jsp:include>    