

<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>


<% String storeDir=ClwCustomizer.getStoreDir(); %>
<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/> 

<bean:define id="toolLink01"  value="../userportal/templator.do?tabs=safetyToolBar&display=customsafeprograms"/>
<bean:define id="toolLable01" value="template.jd.bsc.menu.programs"/>
<bean:define id="toolSecondaryToolBar01" value=""/>

<bean:define id="toolLink02"  value="../userportal/msdsTemplate.do?tabs=safetyToolBar&display=msdsTemplate"/>
<bean:define id="toolLable02" value="template.jd.bsc.menu.safety"/>
<bean:define id="toolSecondaryToolBar02" value=""/>

<bean:define id="toolLink03"  value=""/>
<bean:define id="toolLable03" value=""/>
<bean:define id="toolSecondaryToolBar03" value=""/>

<bean:define id="toolLink04"  value=""/>
<bean:define id="toolLable04" value=""/>
<bean:define id="toolSecondaryToolBar04" value=""/>

<bean:define id="toolLink05"  value=""/>
<bean:define id="toolLable05" value=""/>
<bean:define id="toolSecondaryToolBar05" value=""/>

<%String display = (String)request.getParameter("display");%>

<bean:define id="color01" value="#FFFFFF"/>
<bean:define id="color02" value="#003366"/>
<bean:define id="color03" value="#006699"/>

<bean:define id="headerLabel" value="template.jd.bsc.heading.safety"/>

<%@ include file="t_templatorToolBar.jsp" %>
