<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.SessionTool" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>


<!--
<"toolLink01"  value="../userportal/templator.do?display=t_contactUsBody.jsp&tabs=contactUsToolBar"/>
<"toolLable01" value="shop.menu.telephoneAddress"/>

<"toolLink02"  value="../userportal/contactus_email.do?display=t_contactUsEmailBody.jsp&tabs=contactUsToolBar&action=compose_email_contact_msg"/>
<"toolLable02" value="shop.menu.email"/>
-->





<% 
String display = (String)request.getParameter("display");   
String t_templatorToolBar = ClwCustomizer.getStoreFilePath(request,"t_templatorToolBar.jsp"); 
String rootDir = (String) session.getAttribute("store.dir");
String toolLink01 = "/"+rootDir+"/userportal/templator.do";
String toolLink02 = "/"+rootDir+"/userportal/contactus_email.do?action=compose_email_contact_msg";
%>

<jsp:include flush='true' page="<%=t_templatorToolBar%>" >
   <jsp:param name="display"  value="<%=display%>"/>

   <jsp:param name="toolLink01"  value="<%=toolLink01%>"/>
   <jsp:param name="tabs01"  value="contactUsToolBar"/>
   <jsp:param name="display01"  value="t_contactUsBody"/>
   <jsp:param name="toolLable01" value="shop.menu.telephoneAddress"/>
   <jsp:param name="toolSecondaryToolBar01" value=""/>

   <jsp:param name="toolLink02"  value="<%=toolLink02%>"/>
   <jsp:param name="tabs02"  value="contactUsToolBar"/>
   <jsp:param name="display02"  value="t_contactUsEmailBody"/>
   <jsp:param name="toolLable02" value="shop.menu.email"/>
   <jsp:param name="toolSecondaryToolBar02" value=""/>

   <jsp:param name="color01" value="#FFFFFF"/>
   <jsp:param name="color02" value="#003399"/>
   <jsp:param name="color03" value="#66CC66"/>

   <jsp:param name="headerLabel" value="shop.heading.contactUs"/>
</jsp:include>   

