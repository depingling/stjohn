<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.service.api.value.*"%>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.SessionTool" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>

<%
    SessionTool st = new SessionTool(request);
    CleanwiseUser appUser = st.getUserData();
    AccountData actd = appUser.getUserAccount();

    String display = (String) request.getParameter("display");
    String t_templatorToolBar = ClwCustomizer.getStoreFilePath(request, "t_templatorToolBar.jsp");
    String rootDir = (String) session.getAttribute("store.dir");

    String toolLink01 = "/" + rootDir + "/store/pendingOrders.do";
    String toolLink02 = "/" + rootDir + "/store/orderstatus.do?action=search_all_sites_init";


%>

<jsp:include flush='true' page="<%=t_templatorToolBar%>" >

    <jsp:param name="display"  value="<%=display%>"/>

   <jsp:param name="toolLink01"  value="<%=toolLink01%>"/>
   <jsp:param name="tabs01"  value="pendingOrders"/>
   <jsp:param name="display01"  value="pendingOrders"/>
   <jsp:param name="toolLable01" value="msbSites.text.pendingOrders"/>
   <jsp:param name="toolSecondaryToolBar01" value=""/>

   <jsp:param name="toolLink02"  value="<%=toolLink02%>"/>
   <jsp:param name="tabs02"  value="orderstatus"/>
   <jsp:param name="display02"  value="orderstatus"/>
   <jsp:param name="toolLable02" value="msbSites.text.allOrders"/>
   <jsp:param name="toolSecondaryToolBar02" value=""/>

   <jsp:param name="color01" value="#FFFFFF"/>
   <jsp:param name="color02" value="#000000"/>
   <jsp:param name="color03" value="#ff9900"/>

   <jsp:param name="headerLabel" value="msbSites.text.addShipToLocations"/>
</jsp:include>

