<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@ page import="com.cleanwise.view.utils.SessionTool" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<app:checkLogon/>
<%
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    SessionTool st = new SessionTool(request);
    String tabname;
%>

<html:html>
<head>
    <title>
        <tiles:getAsString name="title"/>
    </title>
    <!-- meta http-equiv="Content-Type" content="text/html; charset=UTF-8" -->
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="-1">
     <script>
        var djConfig = {parseOnLoad: false, isDebug: false, usePlainJson: true},locale
        djConfig.locale = '<%=st.getUserLocaleCode(request).toString().replace('_','-').toLowerCase()%>';
    </script>
    <script src="../externals/dojo_1.1.0/dojo/dojo.js" language="javascript"></script>
    <script src="../externals/lib.js" language="javascript"></script>
    <script src="../externals/table-sort.js" language="javascript"></script>

    <link rel="stylesheet" type="text/css" href='../externals/dojo_1.1.0/clw/admin2/themes/admin2/Admin2.css'>
    <link rel="stylesheet" type="text/css" href='../externals/dojo_1.1.0/clw/admin2/themes/admin2/Calendar.css'>
    <link rel="stylesheet" type="text/css" href='../externals/dojo_1.1.0/clw/admin2/themes/admin2/Menu.css'>

    <script language="JavaScript" type="text/javascript">
        dojo.require("clw.admin2.UiStatusControlMenu");
        dojo.require("clw.admin2.form.DateTextBox");
    </script>

    <logic:present name="pages.css">
        <logic:equal name="pages.css" value="styles.css">
            <link rel="stylesheet" href='../externals/<app:custom pageElement="pages.css"/>'>
        </logic:equal>
        <logic:notEqual name="pages.css" value="styles.css">
            <link rel="stylesheet" href='../externals/styles.css'>
            <link rel="stylesheet" href='../externals/<app:custom pageElement="pages.css"/>'>
        </logic:notEqual>
    </logic:present>

    <logic:notPresent name="pages.css">
        <link rel="stylesheet" href='../externals/styles.css'>
    </logic:notPresent>

</head>

<body class="admin2">

    <%--render the image and the menuing system--%>
<table border="0" cellpadding="0" cellspacing="0" width="<%=Constants.TABLEWIDTH800%>" style="border-collapse: collapse;">

    <tr valign="top">

        <td align="left" valign="middle">
     <% String storeLogo = (String)session.getAttribute("pages.logo1.image");
        if (Utility.isSet(storeLogo)) { %>
            <img  src='<app:custom pageElement="pages.logo1.image" addImagePath="true" encodeForHTML="true"/>' border="0"><BR>
            <bean:write name="ADMIN2_HOME_MGR_FORM" property="currentStore.shortDesc"/>
     <% } %>
            <logic:equal name="<%=Constants.APP_USER%>" property="aAccountAdmin" value="true"> / <bean:write name="ADMIN2_HOME_MGR_FORM" property="currentAccount.shortDesc"/></logic:equal>
        </td>
            <%--The navigation bar--%>
        <td align="right" colspan="4">
            <jsp:include flush='true' page="../general/navMenu.jsp"/>
            <br>
            <% java.util.Date currd = new java.util.Date(); %>
            <%= currd.toString() %>
            <br>
            Server: <%=java.net.InetAddress.getLocalHost()%>
        </td>
            <%--END The navigation bar--%>
    </tr>
</table>
<%
    int headers = 0;
    int MAX_HEADERS_ALLOWED = 8;
%>
<table border="0" cellpadding="0" cellspacing="0" width="<%=Constants.TABLEWIDTH800%>" style="border-collapse: collapse;">
    <tr>

        <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ADMIN2_MGR_TAB_USER%>">
        <%
            headers++;
            if (headers >= MAX_HEADERS_ALLOWED) {
                headers = 0;
        %>
    </tr>
    <tr>
        <%}%>
        <%tabname = ClwI18nUtil.getMessage(request, "admin2.layout.tab.user", null);%>
        <app:renderStatefulButton link="admin2UserSearch.do"
                                  name="<%=tabname%>"
                                  tabClassOff="atoff"
                                  tabClassOn="aton"
                                  linkClassOff="tbar2"
                                  linkClassOn="tbar"
                                  contains="admin2UserSearch,admin2UserDetail,admin2User"/>

        </app:authorizedForFunction>



        <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ADMIN2_MGR_TAB_ACCOUNT%>">
        <%headers++;
            if (headers >= MAX_HEADERS_ALLOWED) {
                headers = 0;
        %>
    </tr>
    <tr>
        <%}%>
        <%tabname = ClwI18nUtil.getMessage(request, "admin2.layout.tab.account", null);%>
        <app:renderStatefulButton link="admin2AccountSearch.do"
                                  name="<%=tabname%>"
                                  tabClassOff="atoff"
                                  tabClassOn="aton"
                                  linkClassOff="tbar2"
                                  linkClassOn="tbar"
                                  contains="admin2AccountSearch,admin2AccountDetail,admin2Account"/>
        </app:authorizedForFunction>












        <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ADMIN2_MGR_TAB_SITE%>">

        <%  headers++;
            if (headers >= MAX_HEADERS_ALLOWED) {
                headers = 0;
        %>

    </tr>
    <tr>
        <%}%>

        <%tabname = ClwI18nUtil.getMessage(request, "admin2.layout.tab.site", null);%>
        <app:renderStatefulButton link="admin2SiteSearch.do"
                                  name="<%=tabname%>"
                                  tabClassOff="atoff"
                                  tabClassOn="aton"
                                  linkClassOff="tbar2"
                                  linkClassOn="tbar"
                                  contains="admin2SiteSearch,admin2SiteDetail,admin2SiteWorkflow,admin2OrderGuideSearch,admin2SiteBudgets,admin2SiteConfig"/>

        </app:authorizedForFunction>

        <% if (appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.ADMIN2_MGR_SITE_LOADER) ||
               appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.ADMIN2_MGR_BUDGET_LOADER) ||
               appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.ADMIN2_MGR_SHOPPING_CONTROL_LOADER)) {

           String loaderLink = "siteloader.do";
           if (!appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.ADMIN2_MGR_SITE_LOADER)) {
        	   if (appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.ADMIN2_MGR_BUDGET_LOADER)) {
               		loaderLink = "budgetloader.do";
        	   }else{
        		    loaderLink = "shoppingcontrolloader.do";
        	   }
           }
        %>


             <%  headers++;
                 if (headers >= MAX_HEADERS_ALLOWED) {
                     headers = 0;
             %>

         </tr>
         <tr>
             <%}%>

             <%tabname = ClwI18nUtil.getMessage(request, "admin2.layout.tab.loader", null);%>
             <app:renderStatefulButton link="<%=loaderLink%>"
                                       name="<%=tabname%>"
                                       tabClassOff="atoff"
                                       tabClassOn="aton"
                                       linkClassOff="tbar2"
                                       linkClassOn="tbar"
                                       contains=""/>
          <% } %>


        <%
            headers++;
            if (headers >= MAX_HEADERS_ALLOWED) {
                headers = 0;
        %>

    </tr>
    <tr>
        <%}%>
          <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ADMIN2_MGR_TAB_PROFILE%>">

             <%  headers++;
                 if (headers >= MAX_HEADERS_ALLOWED) {
                     headers = 0;
             %>

         </tr>
         <tr>
             <%}%>

             <%tabname = ClwI18nUtil.getMessage(request, "admin2.layout.tab.myPassword", null);%>
             <app:renderStatefulButton link="admin2UProfile.do"
                                       name="<%=tabname%>"
                                       tabClassOff="atoff"
                                       tabClassOn="aton"
                                       linkClassOff="tbar2"
                                       linkClassOn="tbar"/>

            </app:authorizedForFunction>

             <%
                 headers++;
                 if (headers >= MAX_HEADERS_ALLOWED) {
                     headers = 0;
             %>

         </tr>
    <%}%>

</table>
    <%--insert any sub menu if present --%>
<tiles:useAttribute id="subMenu" name="subMenu" ignore="true" classname="java.lang.String"/>
<logic:present name="subMenu">
    <tiles:insert attribute="subMenu"/>
</logic:present>

    <%--display any errors if present--%>
<table  width="<%=Constants.TABLEWIDTH800%>">
    <tr>
        <td align="center">
            <div class="text"><font color=red>
                <html:errors/>
            </font></div>
        </td>
    </tr>
</table>

    <%--insert the body content --%>
<tiles:insert attribute="body"/>


    <%--render the footer --%>
<table border="0" cellpadding="0" cellspacing="0" width="<%=Constants.TABLEWIDTH800%>">
    <tr>
        <td align="left">
            <app:custom pageElement="pages.footer.msg"/>
        </td>
    </tr>
</table>

</body>

</html:html>
