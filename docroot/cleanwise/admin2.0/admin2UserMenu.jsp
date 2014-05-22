<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.forms.Admin2UserConfigMgrForm" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@ page import="com.cleanwise.view.logic.Admin2UserMgrLogic" %>


<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<table width="<%=Constants.TABLEWIDTH800%>">
  <tr bgcolor="#000000">

      <logic:present name="<%=Admin2UserMgrLogic.ADMIN2_USER_DETAIL_MGR_FORM%>">
          <logic:present name="<%=Admin2UserMgrLogic.ADMIN2_USER_DETAIL_MGR_FORM%>" property="uiPage">

              <app:renderStatefulButton link="admin2UserDetail.do?action=userdetail"
                                        name='<%=ClwI18nUtil.getMessage(request, "admin2.usermenu.name.detail",null)%>'
                                        tabClassOff="tbar"
                                        tabClassOn="tbarOn"
                                        linkClassOff="tbar"
                                        linkClassOn="tbarOn"
                                        contains="admin2UserDetail,admin2UserSearch"/>

		<logic:greaterThan name="<%=Admin2UserMgrLogic.ADMIN2_USER_DETAIL_MGR_FORM%>" property="id" value="0">

                  <app:renderStatefulButton link="admin2UserConfig.do?confFunc=<%=Admin2UserConfigMgrForm.CONF_FUNUNCTION.ACCOUNTS%>"
                                            name='<%=ClwI18nUtil.getMessage(request, "admin2.usermenu.name.configuration",null)%>'
                                            tabClassOff="tbar"
                                            tabClassOn="tbarOn"
                                            linkClassOff="tbar"
                                            linkClassOn="tbarOn"
                                            contains="admin2UserConfig"/>
		</logic:greaterThan>

          </logic:present>
      </logic:present>
  </tr>
</table>
