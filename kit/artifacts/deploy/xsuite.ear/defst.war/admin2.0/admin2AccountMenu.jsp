<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.forms.Admin2AccountConfigMgrForm" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@ page import="com.cleanwise.view.logic.Admin2AccountMgrLogic" %>


<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<table width="<%=Constants.TABLEWIDTH800%>">
  <tr bgcolor="#000000">

      <logic:present name="<%=Admin2AccountMgrLogic.ADMIN2_ACCOUNT_DETAIL_MGR_FORM%>">
          <logic:present name="<%=Admin2AccountMgrLogic.ADMIN2_ACCOUNT_DETAIL_MGR_FORM%>" property="accountData">
             <logic:present name="<%=Admin2AccountMgrLogic.ADMIN2_ACCOUNT_DETAIL_MGR_FORM%>" property="uiPage">

             <app:renderStatefulButton link="admin2AccountDetail.do?action=accountdetail"
                                        name='<%=ClwI18nUtil.getMessage(request, "admin2.accountmenu.name.detail",null)%>'
                                        tabClassOff="tbar"
                                        tabClassOn="tbarOn"
                                        linkClassOff="tbar"
                                        linkClassOn="tbarOn"
                                        contains="admin2AccountDetail,admin2AccountSearch"/>

             <logic:greaterThan name="<%=Admin2AccountMgrLogic.ADMIN2_ACCOUNT_DETAIL_MGR_FORM%>" property="accountData.accountId" value="0">

             <app:renderStatefulButton link="admin2AccountConfig.do"
									  name="Configuration" 
									  tabClassOff="tbar" 
									  tabClassOn="tbarOn"
 									  linkClassOff="tbar" 
 									  linkClassOn="tbarOn"
  									  contains="admin2AccountConfig"/>
			
			<app:renderStatefulButton link="admin2AccountFiscalCalendar.do?action=list"
 									  name="Fiscal Calendar" 
 									  tabClassOff="tbar" 
 									  tabClassOn="tbarOn"
  									  linkClassOff="tbar" 
  									  linkClassOn="tbarOn"
  									  contains="admin2AccountFiscalCalendar"/>
  									  
             </logic:greaterThan>

              
             </logic:present>
          </logic:present>
      </logic:present>
  </tr>
</table>
