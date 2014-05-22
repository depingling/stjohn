<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>

<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<table ID=729 >
  <tr bgcolor="#000000">
    <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ADMIN2_MGR_SITE_LOADER%>">
        <app:renderStatefulButton link="siteloader.do"
                                  name='<%=ClwI18nUtil.getMessage(request, "admin2.loadersmenu.name.siteLoader",null)%>'
                                  tabClassOff="tbar" tabClassOn="tbarOn"
                                  linkClassOff="tbar" linkClassOn="tbarOn"
                                  contains="loaders,siteloader"/>
    </app:authorizedForFunction>
    <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ADMIN2_MGR_BUDGET_LOADER%>">
        <app:renderStatefulButton link="budgetloader.do"
                                  name='<%=ClwI18nUtil.getMessage(request, "admin2.loadersmenu.name.budgetLoader",null)%>'
                                  tabClassOff="tbar" tabClassOn="tbarOn"
                                  linkClassOff="tbar" linkClassOn="tbarOn"
                                  contains="loaders,budgetloader"/>
    </app:authorizedForFunction>
    <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ADMIN2_MGR_SHOPPING_CONTROL_LOADER%>">
        <app:renderStatefulButton link="shoppingcontrolloader.do"
                                  name='<%=ClwI18nUtil.getMessage(request, "admin2.loadersmenu.name.shoppingcontrolloader",null)%>'
                                  tabClassOff="tbar" tabClassOn="tbarOn"
                                  linkClassOff="tbar" linkClassOn="tbarOn"
                                  contains="loaders,shoppingcontrolloader"/>
    </app:authorizedForFunction>
  </tr>
</table>



