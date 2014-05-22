<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="java.util.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<%String storeDir=ClwCustomizer.getStoreDir();%>

<logic:equal name="ApplicationUser" property="aAdmin" value="true">
    <logic:notEqual name="ApplicationUser" property="aStoreAdmin" value="true">
       <logic:notEqual name="ApplicationUser" property="aAccountAdmin" value="true">
		    <table cellpadding="0" cellspacing="0" class="headernavtable" style="border-collapse: collapse;">
			<tr>
			<td align="right">
				<a class="headernavlinkstart" target="manta" onClick="window.name = 'stjohn'" href="/<%=storeDir+"/storeportal/storeAdminHome.do?action=gomantaadmin"%>">new admin</a>
			</td>
			<td align="right">
				<a class="headernavlinkstart" target="orca" onClick="window.name = 'stjohn'" href="/<%=storeDir+"/storeportal/storeAdminHome.do?action=goorcaadmin"%>">services</a>
			</td>
			
			<td align="right">
				<a class="headernavlinkstart" href="/<%=storeDir+"/storeportal/storeAdminHome.do"%>">store</a>
			</td>
			<td align="right">
				<a class="headernavlinkstart" href="/<%=storeDir+"/adminportal/appAdminHome.do"%>">admin</a>
			</td>
            <td align="right">
				<a class="headernavlinkstart" href="/<%=storeDir+"/admin2.0/admin2AdminHome.do"%>">admin 2.0</a>
			</td>
			<td align="right">
				<a class="headernavlinkstart" href="/<%=storeDir+"/adminportal/systemhome.do"%>">system</a>
			</td>
			<logic:equal name="ApplicationUser" property="hasReporting" value="true">
			<td align="right">
				<a class="headernavlinkstart" href="/<%=storeDir+"/reporting/analyticRep.do"%>">reporting</a>
			</td>
			</logic:equal>
            <td align="right">
				<a class="headernavlinkstart" href="/<%=storeDir+"/uimanager/uiHome.do"%>">ui</a>
			</td>
			<logic:equal name="<%=Constants.CW_LOGOUT_ENABLED%>" value="true">
			<td align="right">
				<a class="headernavlink" href="../logoff.do">log out</a>
			</td>
			</logic:equal>
			</tr>
		    </table>
       </logic:notEqual>
    </logic:notEqual>
</logic:equal>
<logic:equal name="ApplicationUser" property="aCustServiceRep" value="true">
		    <table cellpadding="0" cellspacing="0" class="headernavtable" style="border-collapse: collapse;">
			<tr>
			<td align="right">
				<a class="headernavlinkstart" href="/<%=storeDir+"/console/consolehome.do"%>">ops</a>
			</td>
			<logic:equal name="ApplicationUser" property="hasReporting" value="true">
			<td align="right">
				<a class="headernavlinkstart" href="/<%=storeDir+"/reporting/analyticRep.do"%>">reporting</a>
			</td>
			</logic:equal>
			<logic:equal name="<%=Constants.CW_LOGOUT_ENABLED%>" value="true">
			<td align="right">
				<a class="headernavlink" href="../logoff.do">log out</a>
			</td>
			</logic:equal>
			</tr>
		    </table>
</logic:equal>
<logic:equal name="ApplicationUser" property="aReportingUser" value="true">
		    <table cellpadding="0" cellspacing="0" class="headernavtable" style="border-collapse: collapse;">
			<tr>
			<logic:equal name="<%=Constants.CW_LOGOUT_ENABLED%>" value="true">
			<td align="right">
				<a class="headernavlink" href="../logoff.do">log out</a>
			</td>
			</logic:equal>
			</tr>
		    </table>
</logic:equal>
<logic:equal name="ApplicationUser" property="aStoreAdmin" value="true">
			<table cellpadding="0" cellspacing="0" class="headernavtable" style="border-collapse: collapse;">
			<tr>
            <td align="right">
				<a class="headernavlinkstart" href="/<%=storeDir+"/storeportal/storeAdminHome.do"%>">home</a>
			</td>
			<td align="right">
				<a class="headernavlinkstart" href="/<%=storeDir+"/admin2.0/admin2AdminHome.do"%>">admin 2.0</a>
			</td>
            <logic:equal name="ApplicationUser" property="hasReporting" value="true">
			<td align="right">
				<a class="headernavlinkstart" href="/<%=storeDir+"/reporting/analyticRep.do"%>">reporting</a>
			</td>
			</logic:equal>
			<logic:equal name="<%=Constants.CW_LOGOUT_ENABLED%>" value="true">
			<td align="right">
				<a class="headernavlink" href="../logoff.do">log out</a>
			</td>
			</logic:equal>
			</tr>
		    </table>
</logic:equal>
<logic:equal name="ApplicationUser" property="aAccountAdmin" value="true">
			<table cellpadding="0" cellspacing="0" class="headernavtable" style="border-collapse: collapse;">
			<tr>
            <td align="right">
				<a class="headernavlinkstart" href="/<%=storeDir+"/admin2.0/admin2AdminHome.do"%>">home</a>
			</td>
			<logic:equal name="<%=Constants.CW_LOGOUT_ENABLED%>" value="true">
			<td align="right">
				<a class="headernavlink" href="../logoff.do">log out</a>
			</td>
			</logic:equal>
			</tr>
		    </table>
</logic:equal>
<logic:equal name="ApplicationUser" property="aDistributor" value="true">
		  <table cellpadding="0" cellspacing="0" class="headernavtable">
		  <tr>
		  <td align="right">
				<a class="headernavlinkstart" href="/<%=storeDir+"/storeportal/storeAdminHome.do"%>">store</a>
		  </td>
		  <td align="right">
			<a class="headernavlinkstart" href="/<%=storeDir+"/distributorportal/distributorhome.do"%>">distributor</a>
		  </td>
		  <logic:equal name="ApplicationUser" property="hasReporting" value="true">
			<td align="right">
						<a class="headernavlinkstart" href="/<%=storeDir+"/reporting/analyticRep.do"%>">reporting</a>
			</td>
		  </logic:equal>
		  <td align="right">
			<a class="headernavlink" href="../logoff.do">log out</a>
		  </td>
		  </tr>
		  </table>
</logic:equal>
<logic:equal name="ApplicationUser" property="aServiceProvider" value="true">
		  <table cellpadding="0" cellspacing="0" class="headernavtable">
		  <tr>
		  <td align="right">
				<a class="headernavlinkstart" href="/<%=storeDir+"/storeportal/storeAdminHome.do"%>">store</a>
		  </td>
		  <td align="right">
			<a class="headernavlinkstart" href="/<%=storeDir+"/serviceproviderportal/serviceproviderhome.do"%>">service provider</a>
		  </td>
		  <logic:equal name="ApplicationUser" property="hasReporting" value="true">
			<td align="right">
						<a class="headernavlinkstart" href="/<%=storeDir+"/reporting/analyticRep.do"%>">reporting</a>
			</td>
		  </logic:equal>
		  <td align="right">
			<a class="headernavlink" href="../logoff.do">log out</a>
		  </td>
		  </tr>
		  </table>
</logic:equal>