<%!private boolean authorizedStoreMgrTabSite=false;%>
<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames"%>

<jsp:include flush='true' page="../general/checkBrowser.jsp"/>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<app:checkLogon/>
<script language="JavaScript1.2">
<!--
function SetChecked(val) {
 dml=document.STORE_WORKFLOW_SITES_FORM;
 len = dml.elements.length;
 var i=0;
 for( i=0 ; i<len ; i++) {
   if (dml.elements[i].name=='selectIds') {
     dml.elements[i].checked=val;
   }
 }
}
function submitenter(myfield,e)
{
var keycode;
if (window.event) keycode = window.event.keyCode;
else if (e) keycode = e.which;
else return true;

if (keycode == 13)
   {
   var s=document.forms[0].elements['search'];
   if(s!=null) s.click();
   return false;
   }
else
   return true;
}

//-->
</script>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="Location" value="account" type="java.lang.String"
 toScope="session"/>
<%
	String isMSIE = (String)session.getAttribute("IsMSIE");
	if (null == isMSIE) isMSIE = "";
%>

<html:html>

<head>
<link rel="stylesheet" href="../externals/styles.css">
<title>Application Administrator Home: Account Workflow Sites</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<script language="JavaScript1.2">

function f_assignSites(pwid) {
  if (confirm("Warning: this action will assign this workflow" +
        " to all the sites for this account.  Continue?")) {
      window.location='storeAccountWorkflowSites.do?action=assign_to_all_sites&workflowId=' + pwid;
  }
}

</script>

<body>



<% if ("Y".equals(isMSIE)) { %>
<iframe style="display:none; position:absolute; z-index:1; width:148; height:194"
   id="CalFrame"
   marginheight=0 marginwidth=0 noresize frameborder=0 scrolling=no
   src="../externals/calendar.html">
</iframe>
<% }  %>

<div class="text">

 <bean:define id="wid" name="STORE_WORKFLOW_SITES_FORM" property="id"/>
 <bean:define id="aid" type="java.lang.String"
  name="Account.id"/>
<%
String acturl = "storeportal/storeAccountWorkflowSites.do?workflowId=" + wid;
%>
<html:form styleId="514" action="<%=acturl%>"
  name="STORE_WORKFLOW_SITES_FORM"
  scope="session" type="com.cleanwise.view.forms.StoreWorkflowSitesForm" focus="mainField">
<app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.STORE_MGR_TAB_SITE%>">
<%authorizedStoreMgrTabSite=true;%>
</app:authorizedForFunction>
<table ID=515 width="769"  class="mainbody"> <%-- Main table. --%>
<html:hidden name="STORE_WORKFLOW_SITES_FORM" property="busEntityId"
  value="<%= aid %>" />

  <tr>
  <td><b>Store&nbsp;Id:</b></td><td><bean:write name="Store.id"/></td>
  <td><b>Store&nbsp;Name:</b></td><td><bean:write name="Store.name"/></td>
  </tr>
  <tr>
  <td><b>Account&nbsp;Id:</b></td><td><bean:write name="Account.id"/></td>
  <td><b>Account&nbsp;Name:</b></td><td><bean:write name="Account.name"/></td>
  </tr>

<tr>
<td><b>Workflow Id:</b></td>
<td>
<bean:write name="STORE_WORKFLOW_SITES_FORM" property="id"/>
</td>
<td><b>Name:</b></td>
<td>
<bean:write name="STORE_WORKFLOW_SITES_FORM" property="name"/>
</td>

</tr>

<tr>
<td><b>Type:</b></td>
<td>
<bean:write name="STORE_WORKFLOW_SITES_FORM" property="typeCd"/>
</td>
<td><b>Status:</b></td>
<td>
<bean:write name="STORE_WORKFLOW_SITES_FORM" property="statusCd"/>
</td>
</tr>

<tr valign="top"> <td colspan=4>
<table ID=516  class="results"> <%-- table block --%>
<tr>

	<html:submit  property="action">
        <app:storeMessage    key="admin.button.listCurrentSites"/>
    </html:submit>
<% String oc = "f_assignSites(" + wid + ");" ; %>
	<html:button property="action" onclick="<%=oc%>">
		<app:storeMessage  key="admin.button.assignToAllSites"/>
	</html:button>

<!-- ****************  -->

<td align="right"><b>Find Site</b></td>
<td colspan=5>
<html:text styleId="mainField" name="STORE_WORKFLOW_SITES_FORM" onkeypress="return submitenter(this,event)" property="searchField"/>
<html:radio name="STORE_WORKFLOW_SITES_FORM" property="searchType"
    value="id" />  ID
<html:radio name="STORE_WORKFLOW_SITES_FORM" property="searchType"
    value="nameBegins" />  Name(starts with)
<html:radio name="STORE_WORKFLOW_SITES_FORM" property="searchType"
    value="nameContains" />  Name(contains)
</td>
</tr>

<tr>
<td align="right"><b>City</b></td>
<td><html:text name="STORE_WORKFLOW_SITES_FORM" onkeypress="return submitenter(this,event)"  property="city"/> </td>
<td align="right"><b>County</b></td>
<td><html:text name="STORE_WORKFLOW_SITES_FORM" onkeypress="return submitenter(this,event)" property="county"/> </td>
<td><html:submit styleId="search" property="action">
		<app:storeMessage  key="global.action.label.search"/>
	</html:submit></td>
</tr>

<tr>
<td align="right"><b>State</b></td>
<td><html:text name="STORE_WORKFLOW_SITES_FORM" onkeypress="return submitenter(this,event)"  property="state"/> </td>
<td align="right"><b>Zip</b></td>
<td><html:text name="STORE_WORKFLOW_SITES_FORM" onkeypress="return submitenter(this,event)"  property="postalCode"/>
</td><td>

	Show Inactive: <html:checkbox property="showInactiveFl"/>
</td></tr>
</table> <%-- search block --%>

</td>

</tr>
</table> <%-- Main table --%>

<%-- search results --%>





<logic:present name="Workflow.sites.vector">
<bean:size id="rescount"  name="Workflow.sites.vector"/>
Search result count:  <bean:write name="rescount" />
<div style="width: 769; text-align: right;">
<a ID=517 href="javascript:SetChecked(1)">[Check&nbsp;All]</a>
<a ID=518 href="javascript:SetChecked(0)">[&nbsp;Clear]</a> <br>
</div>

<logic:greaterThan name="rescount" value="0">
<table class="stpTable_sortable" id="ts1" >
<thead>
<tr class=stpTH align=left>
<th class="stpTH">Site Id</th>
<th class="stpTH">Site Name</th>
<th class="stpTH">Street Address</th>
<th class="stpTH">City</th>
<th class="stpTH">State</th>
<th class="stpTH">Zip</th>
<th class="stpTH">County</th>
<th class="stpTH">Status</th>
<!--<td>Select</td> -->
</tr>
</thead>
<tbody>
<logic:iterate id="arrele" name="Workflow.sites.vector">
  <tr class=stpTD>
    <td class=stpTD><bean:write name="arrele" property="id"/></td>
    <td class=stpTD>
  <bean:define id="eleid" name="arrele" property="id"/>
  <%if(authorizedStoreMgrTabSite) { %>
  <a ID=519 href="sitesearch.do?action=sitedetail&searchType=id&searchField=<%=eleid%>">
  <bean:write name="arrele" property="name"/>
  </a>
  <%} else {%>
  <bean:write name="arrele" property="name"/>
  <%}%>

    </td>
    <td class=stpTD><bean:write name="arrele" property="address"/></td>
    <td class=stpTD><bean:write name="arrele" property="city"/></td>
    <td class=stpTD><bean:write name="arrele" property="state"/></td>
    <td class=stpTD><bean:write name="arrele" property="postalCode"/></td>
    <td class=stpTD><bean:write name="arrele" property="county"/></td>
    <td class=stpTD><bean:write name="arrele" property="status"/></td>
<td >
<html:multibox name="STORE_WORKFLOW_SITES_FORM" property="selectIds" value="<%= (\"\"+eleid)%>" />
<html:hidden name="STORE_WORKFLOW_SITES_FORM" property="displayIds" value="<%= (\"\"+eleid)%>" /></td>
</tr>

</logic:iterate>
 </tbody>

</table><table ID=520 width="<%=Constants.TABLEWIDTH%>">
<tr>
<td align="right" ></td>
<td align="right">
<html:submit property="action">
<app:storeMessage  key="global.action.label.save"/>
</html:submit>

<!--
<html:reset>
<app:storeMessage  key="admin.button.reset"/>
</html:reset>  -->

</td>
</tr></table>
</logic:greaterThan>
</logic:present>



</html:form>

</div>

<% if ("Y".equals(isMSIE)) { %>
<script for=document event="onclick()">
<!--
document.all.CalFrame.style.display="none";
//-->
</script>
<% }  %>



</body>

</html:html>






