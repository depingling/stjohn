<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.service.api.value.*"%>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<%
String display = null;
%>
<logic:equal name="toolBarTab" value="default">
        <%display = "report";%>
        
</logic:equal>

<logic:equal name="toolBarTab" value="survey">
        <%display = "survey";%>
        
</logic:equal>

<logic:equal name="toolBarTab" value="profile">
        <%display = "profile";%>
        
</logic:equal>

<logic:present name="Profiling.found.vector">
    <bean:size id="surveysFound"  name="Profiling.found.vector"/>
</logic:present>
<logic:notPresent name="Profiling.found.vector">
    <bean:define id="surveysFound"  value="0"/>
</logic:notPresent>



<% String storeDir=ClwCustomizer.getStoreDir(); %>
<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/> 
<!--
<"toolLink01"  value="customerAccountManagement.do?display=report"/>
<"toolLable01" value="shop.menu.reports"/>
<"toolSecondaryToolBar01" value=""/>

<"toolLink02"  value="customerAccountManagementProfile.do?action=customer_profile&display=profile"/>
<"toolLable02" value="shop.menu.profiles"/>
<"toolSecondaryToolBar02" value=""/>

<"toolLink03"  value=""/>
<"toolLable03" value=""/>
<"toolSecondaryToolBar03" value=""/>
-->





<bean:define id="headerLabel" value="shop.heading.reporting"/>

<% 
String t_templatorToolBar = ClwCustomizer.getStoreFilePath(request,"t_templatorToolBar.jsp"); 
String rootDir = (String) session.getAttribute("store.dir");
String toolLink01 = "/"+rootDir+"/userportal/customerAccountManagement.do";
String toolLink02 = "/"+rootDir+"/userportal/customerAccountManagementProfile.do?action=customer_profile";
String toolLink03 = "";
String tabs03="";
String display03="";
String toolLable03="";
%>

<logic:greaterThan name="surveysFound" value="0">
<%
toolLink03 = "/"+rootDir+"/userportal/customerAccountManagementSurveyDetail.do?action=view";   
display03 = "survey";
toolLable03="shop.menu.surveys";
%>
</logic:greaterThan>

<jsp:include flush='true' page="<%=t_templatorToolBar%>" >
   <jsp:param name="display"  value="<%=display%>"/>

   <jsp:param name="toolLink01"  value="<%=toolLink01%>"/>
   <jsp:param name="tabs01"  value=""/>
   <jsp:param name="display01"  value="report"/>
   <jsp:param name="toolLable01" value="shop.menu.reports"/>
   <jsp:param name="toolSecondaryToolBar01" value=""/>

   <jsp:param name="toolLink02"  value="<%=toolLink02%>"/>
   <jsp:param name="tabs02"  value=""/>
   <jsp:param name="display02"  value="profile"/>
   <jsp:param name="toolLable02" value="shop.menu.profiles"/>
   <jsp:param name="toolSecondaryToolBar02" value=""/>

   <jsp:param name="toolLink03"  value="<%=toolLink03%>"/>
   <jsp:param name="tabs03"  value="<%=tabs03%>"/>
   <jsp:param name="display03"  value="<%=display03%>"/>
   <jsp:param name="toolLable03" value="<%=toolLable03%>"/>
   <jsp:param name="toolSecondaryToolBar03" value=""/>


   <jsp:param name="color01" value="#FFFFFF"/>
   <jsp:param name="color02" value="#000000"/>
   <jsp:param name="color03" value="#ff9900"/>

   <jsp:param name="headerLabel" value="shop.heading.reporting"/>
</jsp:include>   

