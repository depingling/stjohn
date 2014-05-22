<%@ page language="java" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.util.Utility"%>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.taglibs.DojoPopupProgrammaticTag" %>
<%@ page import="com.cleanwise.view.taglibs.DojoSelectorProgrammaticTag" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
 <style type="text/css">

.xpedxMainMenuTab {
    text-decoration: none;
    text-transform: capitalize;
    border: 0;
    font-weight: bold;
    font-size: 12px;
    padding:0;
    margin:0;
    color: white;
    cursor: pointer;
    cursor: hand;
    background: url( '<%=ClwCustomizer.getSIP(request,"mainmenubg.gif")%>' );
}

</style>
<!--PAGESRCID=f_cwHeaderNavDefault.jsp-->
<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>
<%
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute("ApplicationUser");
    String displayHelpSection = request.getParameter("displayHelpSection");
    String enableShop = request.getParameter("enableShop");
    String shopUrl = "../userportal/msbsites.do"; //request.getParameter("shopUrl");
    String allowAssetManagement = request.getParameter("allowAssetManagement");
%>
<script language="JavaScript">
    function goto(url) {
        document.location.href = url;
    }

    function gotonewwindow(url) {
        window.open(url,'_blank');
    }
    var ready = false;
    dojo.addOnLoad(function(){ready=true;})
</script>

<img src="<%=IMGPath%>/cw_spacer.gif" height="12">
<table id="menuTable" valign="top" align="center" border="0" cellpadding="0" cellspacing="0" width="<%=Constants.TABLEWIDTH800%>">
    <tr>
      <td align="right" width="6"><img  hspace="0" src='<%=ClwCustomizer.getSIP(request,"leftMainToolbar.gif")%>' border="0"></td>
      <td>

        <table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" >

      <tr align="center">


    <% if (appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.SHOP_ACCESS)) {%>

          <% if (Utility.isTrue(enableShop)) {%>
		  <%
			String path = request.getRequestURI();

			if (request.getQueryString() != null) {
				path += '?' + request.getQueryString();
			}

			int isLogOnPage = path.indexOf("logon");
			int isHomePage = path.indexOf("msbSites");
			int isMsbsitesPage = path.indexOf("msbsites");
			int isCatPage = path.indexOf("openCategory");
			int isItemPage = path.indexOf("itemDetail");
			int isMultiProdPage = path.indexOf("itemgroup");
		  %>

		  <%
			if(isHomePage!= -1 || isLogOnPage!=-1 || isMsbsitesPage!=-1 || isCatPage!=-1 || isItemPage!=-1 || isMultiProdPage!=-1){
		  %>

    <app:dojoSelectorProgrammatic id="shopSelector"
                                  targetWidth="70px"
                                  module="clw.NewXpedx"
                                  onClick="<%=\"goto('\"+shopUrl+\"');\"%>"
                                  key="shop.menu.main.shopm"
                                  targetTabStyle="xpedxMainMenuTab"
                                  startupEvent="<%=DojoSelectorProgrammaticTag.ONMOUSEOVER%>" />
            <%}else{%>

    <app:dojoPopupProgrammatic width="185"
                               targetWidth="70px"
                               name="SHOP_FORM"
                               property="catalogMenu"
                               id="sPopup"
                               module="clw.NewXpedx"
                               onClick="<%=\"goto('\"+shopUrl+\"');\"%>"
                               key="shop.menu.main.shopm"
                               targetTabStyle="xpedxMainMenuTab"
                               startupEvent="<%=DojoPopupProgrammaticTag.ONMOUSEOVER%>" />

			<%}%>
          <%} else {%>
              <td class="xpedxMainMenuTab"> &nbsp; </td>
          <%}%>
     <%}%>

    <% if(appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.AUTO_DISTRO_ACCESS)) {%>

    <app:dojoSelectorProgrammatic
            id="autoDistroSelector"
            module="clw.NewXpedx"
            onClick="<%=\"goto('../store/scheduledCart.do?action=showAutoDistro');\"%>"
            key="shop.menu.main.autoDistro"
            targetTabStyle="xpedxMainMenuTab"
            startupEvent="<%=DojoSelectorProgrammaticTag.ONMOUSEOVER%>"/>
    <% } %>
    <%  if(appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.TRACK_ORDER_ACCESS)) {%>

    <app:dojoPopupProgrammatic name="MSB_SITE_FORM"
                               property="trackOrderPopup"
                               id="trackOrderPopup"
                               module="clw.NewXpedx"
                               onClick="<%=\"goto('../store/orderstatus.do?action=initXPEDXListOrders&orderStatus=\"+Constants.ORDER_STATUS_ALL+\"');\"%>"
                               key="shop.menu.main.trackOrder"
                               targetTabStyle="xpedxMainMenuTab"
                               startupEvent="<%=DojoPopupProgrammaticTag.ONMOUSEOVER%>"/>
    <% } %>

    <% if(appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.REPORTS_ACCESS)) {%>

    <app:dojoSelectorProgrammatic
            id="reportsMenuSelector"
            module="clw.NewXpedx"
            onClick="<%=\"goto('../userportal/customerAccountManagement.do');\"%>"
            key="shop.menu.main.reports"
            targetTabStyle="xpedxMainMenuTab"
            startupEvent="<%=DojoSelectorProgrammaticTag.ONMOUSEOVER%>"/>

    <% } %>
    <%--
    <% if(appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.PROFILE_ACCESS)) {%>
        <td id="profileMenuTd"
            class="xpedxMainMenuTab"
            dojoType="clw.NewXpedx.MenuSelector"
            baseClass="xpedxMainMenuTab"
		    onclick="goto('../userportal/customerAccountManagementProfileNewXpedx.do');">
            <app:storeMessage key="shop.menu.main.profile"/>
        </td>
    <% } %>
    --%>
    <% if(appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.USER_PROFILE_ACCESS) ||
          appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.STORE_PROFILE_ACCESS)
            ) {%>

    <app:dojoPopupProgrammatic name="MSB_SITE_FORM"
                               property="profilePopup"
                               id="profilePopup"
                               module="clw.NewXpedx"
                               key="shop.menu.main.profile"
                               targetTabStyle="xpedxMainMenuTab"
                               startupEvent="<%=DojoPopupProgrammaticTag.ONMOUSEOVER%>"/>

    <% } %>


    <% if(appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.PRODUCT_INFORMATION_ACCESS)) {%>

    <app:dojoSelectorProgrammatic
            id="productInfoMenuSelector"
            module="clw.NewXpedx"
            onClick="<%=\"goto('../userportal/newTemplator.do?display=f_educatorController.jsp');\"%>"
            key="shop.menu.main.productInformationWrapped"
            targetTabStyle="xpedxMainMenuTab"
            startupEvent="<%=DojoSelectorProgrammaticTag.ONMOUSEOVER%>"/>

    <% } %>
    <% if(appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.MSDS_ACCESS)) {
    	String protocol = "http";
    	if (request.isSecure()) {
    		protocol = protocol + "s";
    	}
    	String link = "gotonewwindow('" + protocol + "://www.msdsonfile.com/mctx/msds/msdsonfile.jsp');";
    %>

        <app:dojoSelectorProgrammatic
            id="msdsMenuSelector"
            module="clw.NewXpedx"
            onClick="<%=link%>"
            key="shop.menu.main.MSDS"
            targetTabStyle="xpedxMainMenuTab"
            startupEvent="<%=DojoSelectorProgrammaticTag.ONMOUSEOVER%>"/>

    <% } %>
    <% if(appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.FAQ_ACCESS)) {%>

                 <%-- if(appUser.getUser().getUserName().equals("yuriyapple")) { --%>
      <% String faqLink = appUser.getUserAccount().getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.FAQ_LINK);
          if (faqLink==null || faqLink.length() == 0) { %>

      <app:dojoSelectorProgrammatic
              id="faqMenuSelector"
              module="clw.NewXpedx"
              onClick="<%=\"goto('../userportal/faq.do');\"%>"
              key="shop.menu.main.faq"
              targetTabStyle="xpedxMainMenuTab"
              startupEvent="<%=DojoSelectorProgrammaticTag.ONMOUSEOVER%>"/>
      <%} else {%>

      <app:dojoSelectorProgrammatic
              id="faqMenuSelector"
              module="clw.NewXpedx"
              onClick="<%=\"gotonewwindow('\"+faqLink+\"');\"%>"
              key="shop.menu.main.faq"
              targetTabStyle="xpedxMainMenuTab"
              startupEvent="<%=DojoSelectorProgrammaticTag.ONMOUSEOVER%>"/>
      <% } %>
      <% } %>
      <% if(appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.CONTACT_US_ACCESS)) {%>

      <app:dojoSelectorProgrammatic
              id="contactUsMenuSelector"
              module="clw.NewXpedx"
              onClick="<%=\"goto('../userportal/new_xpedx_contactus.do');\"%>"
              key="shop.menu.main.contactUs"
              targetTabStyle="xpedxMainMenuTab"
              startupEvent="<%=DojoSelectorProgrammaticTag.ONMOUSEOVER%>"/>
      <% } %>
      <% if(appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.MAINTENANCE_NEWS) ||
              appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.MAINTENANCE_TEMPLATE) ||
              appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.MAINTENANCE_FAQ) || appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.EDIT_SITE_PAR_VALUES)){%>

      <app:dojoPopupProgrammatic name="MSB_SITE_FORM"
                                 property="maintenancePopup"
                                 id="maintenancePopup"
                                 module="clw.NewXpedx"
                                 key="shop.menu.main.maintenance"
                                 targetTabStyle="xpedxMainMenuTab"
                                 startupEvent="<%=DojoPopupProgrammaticTag.ONMOUSEOVER%>" />

      <% } %>
    <%-- else  {%>

                        <td  class="xpdexGradientMenu">
                            <a class=class="xpdexGradientMenu" href="#" onClick="javascript:return false;"></a>
                        </td>
    <% } --%>


                    </tr>
                </table>
        </td>
      <td align="left" width="6"><img src='<%=ClwCustomizer.getSIP(request,"rightMainToolbar.gif")%>' border="0"></td>

    </tr>
</table>
<img src="<%=IMGPath%>/cw_spacer.gif" height="12">

