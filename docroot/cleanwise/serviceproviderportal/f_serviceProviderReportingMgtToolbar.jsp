<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.service.api.value.*"%>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames"%>

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
<logic:equal name="toolBarTab" value="invoice">
        <%display = "invoice";%>

</logic:equal>

<logic:present name="Profiling.found.vector">
    <bean:size id="surveysFound"  name="Profiling.found.vector"/>
</logic:present>
<logic:notPresent name="Profiling.found.vector">
    <bean:define id="surveysFound"  value="0"/>
</logic:notPresent>

<% String storeDir=ClwCustomizer.getStoreDir(); %>
<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<bean:define id="headerLabel" value="shop.heading.reporting"/>

<%
String t_templatorToolBar = "t_templatorToolBar.jsp";
String toolLink01 = "../serviceproviderportal/serviceProviderReporting.do";
String toolLink02 = "../serviceproviderportal/serviceProviderReportManagementProfile.do?action=sp_user_profile";
String toolLink03 = "";
String toolLink04 = "";


String tabs03="";
String display03="";
String toolLable03="";

String tabs04="";
String display04="";
String toolLable04="";
String toolSecondaryToolBar04 = "";
%>

<%--
<logic:greaterThan name="surveysFound" value="0">
<%
  toolLink03 = "../serviceproviderportal/customerAccountManagementSurvey.do?action=view";
  display03 = "survey";
  toolLable03="shop.menu.surveys";
%>
</logic:greaterThan>
--%>

<%--
 CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
if(appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.RUNTIME_INVOICE_TAB)) {
  toolLink04 = "../serviceproviderportal/customerAccountManagementInvoices.do";
  toolSecondaryToolBar04 = "../serviceproviderportal/customerAccountManagementInvoicesDetail.do?action=view";
  display04 = "invoice";
  toolLable04="shop.menu.invoices";
 }
--%>

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

   <jsp:param name="toolLink04"  value="<%=toolLink04%>"/>
   <jsp:param name="tabs04"  value="<%=tabs04%>"/>
   <jsp:param name="display04"  value="<%=display04%>"/>
   <jsp:param name="toolLable04" value="<%=toolLable04%>"/>
   <jsp:param name="toolSecondaryToolBar04" value="<%=toolSecondaryToolBar04%>"/>



   <jsp:param name="color01" value="#FFFFFF"/>
   <jsp:param name="color02" value="#003366"/>
   <jsp:param name="color03" value="#006699"/>

   <jsp:param name="headerLabel" value="shop.heading.reporting"/>
</jsp:include>

