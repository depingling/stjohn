

<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page import="com.cleanwise.service.api.value.*"%>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%
  SessionTool st = new SessionTool(request);
  CleanwiseUser appUser = st.getUserData(); 
  AccountData actd = appUser.getUserAccount();

  String display = (String)request.getParameter("display");   
  String t_templatorToolBar = ClwCustomizer.getStoreFilePath(request,"t_templatorToolBar.jsp"); 
  String rootDir = (String) session.getAttribute("store.dir");
       
  String toolLink01 = "/"+rootDir+"/userportal/templator.do?section=tips";
  String toolLink02 = "/"+rootDir+"/userportal/templator.do?section=sar";
  String toolLink03 = "/"+rootDir+"/userportal/templator.do?section=gloss";
  String toolSecondaryToolBar03 = 
      ClwCustomizer.getStoreFilePath(request,"t_training_glossary_alpha_nav.jsp");

  String toolLink04 = "";
  String toolLable04 = "";
  String tabs04 = "";
  String display04 = "";
  if ( actd.hasNotes() ) { 
     toolLink04 = "/"+rootDir+"/userportal/templator.do?section=how";
     tabs04 = "trainingToolBar";
     display04 = "f_trainingController";
     toolLable04="shop.menu.howToClean";
  } 

%>

<jsp:include flush='true' page="<%=t_templatorToolBar%>" >
   <jsp:param name="display"  value="<%=display%>"/>

   <jsp:param name="toolLink01"  value="<%=toolLink01%>"/>
   <jsp:param name="tabs01"  value="trainingToolBar"/>
   <jsp:param name="display01"  value="f_trainingController"/>
   <jsp:param name="toolLable01" value="shop.menu.trainTips"/>
   <jsp:param name="toolSecondaryToolBar01" value=""/>

   <jsp:param name="toolLink02"  value="<%=toolLink02%>"/>
   <jsp:param name="tabs02"  value="trainingToolBar"/>
   <jsp:param name="display02"  value="f_trainingController"/>
   <jsp:param name="toolLable02" value="shop.menu.safety"/>
   <jsp:param name="toolSecondaryToolBar02" value=""/>

   <jsp:param name="toolLink03"  value="<%=toolLink03%>"/>
   <jsp:param name="tabs03"  value="trainingToolBar"/>
   <jsp:param name="display03"  value="f_trainingController"/>
   <jsp:param name="toolLable03" value="shop.menu.glossary"/>
   <jsp:param name="toolSecondaryToolBar03" value="<%=toolSecondaryToolBar03%>"/>
  
   <jsp:param name="toolLink04"  value="<%=toolLink04%>"/>
   <jsp:param name="tabs04"  value="<%=tabs04%>"/>
   <jsp:param name="display04"  value="<%=display04%>"/>
   <jsp:param name="toolLable04" value="<%=toolLable04%>"/>
   <jsp:param name="toolSecondaryToolBar04" value=""/>

   <jsp:param name="color01" value="#FFFFFF"/>
   <jsp:param name="color02" value="#9C0000"/>
   <jsp:param name="color03" value="#CE3031"/>

   <jsp:param name="headerLabel" value="shop.heading.training"/>
</jsp:include>   



