<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@page import="com.cleanwise.view.utils.CleanwiseUser"%>
<%@page import="com.cleanwise.service.api.value.AllStoreData"%>
<%@page import="com.cleanwise.service.api.value.AllStoreDataVector"%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<jsp:include flush='true' page="ui/loginInfo.jsp"/>
<table ID=521 width="<%=Constants.TABLEWIDTH%>">
	<tr><td>&nbsp;</td></tr>
	<tr><td><b>Managed Stores:</b></td></tr>
	<% if ("yes".equals(System.getProperty("multi.store.db"))) { %>
	      <%
	         CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);
	         AllStoreDataVector allStoreDataVector = appUser.getMultiDbStores();
	      %>
	     <logic:iterate id='store' name='<%=Constants.APP_USER%>' property='multiDbStores' type='com.cleanwise.service.api.value.AllStoreData'>
	       <tr>
		     <td>&nbsp;</td>
		     <td><a ID=522 href="storeAdminHome.do?action=changeStore&id=<bean:write name='store' property='storeId'/>"><bean:write name='store' property='storeName'/></a></td>
	       </tr>
	     </logic:iterate>
	<% } else { %>
	<logic:iterate id='store' name='<%=Constants.APP_USER%>' property='stores' type='com.cleanwise.service.api.value.BusEntityData'>
	  <tr>
		<td>&nbsp;</td>
		<td><a ID=522 href="storeAdminHome.do?action=changeStore&id=<bean:write name='store' property='busEntityId'/>"><bean:write name='store' property='shortDesc'/></a></td>
	  </tr>
	</logic:iterate>
	<% } %>
	<tr><td>&nbsp;</td></tr>
	<tr><td>&nbsp;</td></tr>
</table>