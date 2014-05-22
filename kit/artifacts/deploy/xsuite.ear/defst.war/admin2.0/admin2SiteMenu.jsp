<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@ page import="com.cleanwise.view.logic.Admin2SiteMgrLogic" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<table width="<%=Constants.TABLEWIDTH800%>">
  <tr bgcolor="#000000">
      <logic:present name="<%=Admin2SiteMgrLogic.ADMIN2_SITE_DETAIL_MGR_FORM%>">
          <!-- logic:present name="<%=Admin2SiteMgrLogic.ADMIN2_SITE_DETAIL_MGR_FORM%>" property="uiPage" -->

              <app:renderStatefulButton link="admin2SiteDetail.do?action=sitedetail"
                                        name='<%=ClwI18nUtil.getMessage(request, "admin2.sitemenu.name.detail",null)%>'
                                        tabClassOff="tbar"  tabClassOn="tbarOn"
                                        linkClassOff="tbar" linkClassOn="tbarOn"
                                        contains="admin2SiteDetail"/>
			
			<logic:greaterThan name="<%=Admin2SiteMgrLogic.ADMIN2_SITE_DETAIL_MGR_FORM%>" property="id" value="0">
			
              <app:renderStatefulButton link="admin2SiteConfig.do"
                                        name='<%=ClwI18nUtil.getMessage(request, "admin2.sitemenu.name.configuration",null)%>'
                                        tabClassOff="tbar" tabClassOn="tbarOn"
                                        linkClassOff="tbar" linkClassOn="tbarOn"
                                        contains="admin2SiteConfig"/>
              <app:renderStatefulButton link="admin2SiteBudgets.do"
                                        name='<%=ClwI18nUtil.getMessage(request, "admin2.sitemenu.name.budgets",null)%>'
                                        tabClassOff="tbar" tabClassOn="tbarOn"
                                        linkClassOff="tbar" linkClassOn="tbarOn"
                                        contains="admin2SiteBudgets"/>
              <app:renderStatefulButton link="admin2SiteWorkflow.do?action=init"
                                        name='<%=ClwI18nUtil.getMessage(request, "admin2.sitemenu.name.workflow",null)%>'
                                        tabClassOff="tbar" tabClassOn="tbarOn"
                                        linkClassOff="tbar" linkClassOn="tbarOn"
                                        contains="admin2SiteWorkflow"/>
              <app:renderStatefulButton link="admin2OrderGuideSearch.do?action=init"
                                        name='<%=ClwI18nUtil.getMessage(request, "admin2.sitemenu.name.orderGuides",null)%>'
                                        tabClassOff="tbar" tabClassOn="tbarOn"
                                        linkClassOff="tbar" linkClassOn="tbarOn"
                                        contains="admin2OrderGuideSearch"/>
			</logic:greaterThan>

          <!-- /logic:present-->
      </logic:present>

  </tr>
</table>
