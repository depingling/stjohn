
<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

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
 dml=document.WORKFLOW_SITES_FORM;
 len = dml.elements.length;
 var i=0;
 for( i=0 ; i<len ; i++) {
   if (dml.elements[i].name=='selectIds') {
     dml.elements[i].checked=val;
   }
 }
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
      window.location='accountWorkflowSites.do?action=assign_to_all_sites&workflowId=' + pwid;
  }
}
</script>

<body>

<jsp:include flush='true' page="ui/admToolbar.jsp"/>
<jsp:include flush='true' page="ui/admAccountToolbar.jsp"/>
<jsp:include flush='true' page="ui/loginInfo.jsp"/>

<% if ("Y".equals(isMSIE)) { %>
<iframe style="display:none; position:absolute; z-index:1; width:148; height:194"
   id="CalFrame"
   marginheight=0 marginwidth=0 noresize frameborder=0 scrolling=no 
   src="../externals/calendar.html">
</iframe>
<% }  %>

<div class="text">

<center><font color=red><html:errors/></font></center>

<table width="769"  class="mainbody"> <%-- Main table. --%>
<bean:define id="wid" name="WORKFLOW_SITES_FORM" property="id"/>
<%
String acturl = "adminportal/accountWorkflowSites.do?workflowId=" + wid;
%>

<html:form action="<%=acturl%>"
  name="WORKFLOW_SITES_FORM"
  scope="session" type="com.cleanwise.view.forms.WorkflowSitesForm">

<bean:define id="aid" type="java.lang.String"
  name="Account.id"/>

<html:hidden name="WORKFLOW_SITES_FORM" property="busEntityId"
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
<bean:write name="WORKFLOW_SITES_FORM" property="id"/>
</td>
<td><b>Name:</b></td>
<td>
<bean:write name="WORKFLOW_SITES_FORM" property="name"/>
</td>

</tr>

<tr>
<td><b>Type:</b></td>
<td>
<bean:write name="WORKFLOW_SITES_FORM" property="typeCd"/>
</td>
<td><b>Status:</b></td>
<td>
<bean:write name="WORKFLOW_SITES_FORM" property="statusCd"/>
</td>
</tr>

<tr valign="top"> <td colspan=4>
<table  class="results"> <%-- table block --%>
<tr>
	<html:submit property="action">
		<app:storeMessage  key="admin.button.listCurrentSites"/>
	</html:submit>
<% String oc = "f_assignSites(" + wid + ");" ; %>
	<html:button property="action" onclick="<%=oc%>">
		<app:storeMessage  key="admin.button.assignToAllSites"/>
	</html:button>

<!-- ****************  -->

<td align="right"><b>Find Site</b></td>
<td colspan=5>
<html:text name="WORKFLOW_SITES_FORM" property="searchField"/>
<html:radio name="WORKFLOW_SITES_FORM" property="searchType" 
    value="id" />  ID
<html:radio name="WORKFLOW_SITES_FORM" property="searchType" 
    value="nameBegins" />  Name(starts with)
<html:radio name="WORKFLOW_SITES_FORM" property="searchType" 
    value="nameContains" />  Name(contains)
</td>
</tr>

<tr> 
<td align="right"><b>City</b></td>
<td><html:text name="WORKFLOW_SITES_FORM" property="city"/> </td>
<td align="right"><b>County</b></td>
<td><html:text name="WORKFLOW_SITES_FORM" property="county"/> </td>
</tr>

<tr> 
<td align="right"><b>State</b></td>
<td><html:text name="WORKFLOW_SITES_FORM" property="state"/> </td>
<td align="right"><b>Zip</b></td>
<td><html:text name="WORKFLOW_SITES_FORM" property="postalCode"/>
</td><td>
	<html:submit property="action">
		<app:storeMessage  key="global.action.label.search"/>
	</html:submit>
	<html:submit property="action" 
          onclick="WORKFLOW_SITES_FORM.searchField.value=''">
	<app:storeMessage  key="admin.button.viewall"/>
	</html:submit>
</td></tr>
</table> <%-- search block --%>

</td>

</tr>
</table> <%-- Main table --%>

<%-- search results --%>  

<pg:pager maxPageItems="<%= Constants.MAX_PAGE_ITEMS %>"
          maxIndexPages="<%= Constants.MAX_INDEX_PAGES %>">
  <pg:param name="pg"/>
  <pg:param name="q"/>



<logic:present name="Workflow.sites.vector">
<bean:size id="rescount"  name="Workflow.sites.vector"/>
Search result count:  <bean:write name="rescount" />
<div style="width: 769; text-align: right;">
<a href="javascript:SetChecked(1)">[Check&nbsp;All]</a>
<a href="javascript:SetChecked(0)">[&nbsp;Clear]</a> <br>
</div>

<logic:greaterThan name="rescount" value="0">
<table width="769" border="0" class="results">
<tr align=left>
<td><a class="tableheader" href="accountWorkflowSites.do?action=sort&sortField=id">Site Id</td>
<td><a class="tableheader" href="accountWorkflowSites.do?action=sort&sortField=name">Site Name</td>
<td><a class="tableheader" href="accountWorkflowSites.do?action=sort&sortField=address">Street Address</td>
<td><a class="tableheader" href="accountWorkflowSites.do?action=sort&sortField=city">City</td>
<td><a class="tableheader" href="accountWorkflowSites.do?action=sort&sortField=state">State</td>
<td><a class="tableheader" href="accountWorkflowSites.do?action=sort&sortField=zipcode">Zip</td>
<td><a class="tableheader" href="accountWorkflowSites.do?action=sort&sortField=county">County</td>
<td><a class="tableheader" href="accountWorkflowSites.do?action=sort&sortField=status">Status</td>
<td class="tableheader">Select</td>
</tr>

<logic:iterate id="arrele" name="Workflow.sites.vector">
  <tr>
    <td><bean:write name="arrele" property="busEntity.busEntityId"/></td>
    <td>
  <bean:define id="eleid" name="arrele" property="busEntity.busEntityId"/>
  <a href="sitemgr.do?action=sitedetail&searchType=id&searchField=<%=eleid%>">
  <bean:write name="arrele" property="busEntity.shortDesc"/>
  </a>
    </td>
    <td><bean:write name="arrele" property="siteAddress.address1"/></td>
    <td><bean:write name="arrele" property="siteAddress.city"/></td>
    <td><bean:write name="arrele" property="siteAddress.stateProvinceCd"/></td>
    <td><bean:write name="arrele" property="siteAddress.postalCode"/></td>
    <td><bean:write name="arrele" property="siteAddress.countyCd"/></td>
    <td><bean:write name="arrele" property="busEntity.busEntityStatusCd"/></td>
<td>
<html:multibox name="WORKFLOW_SITES_FORM" property="selectIds" value="<%=(\"\"+eleid)%>" /></td>
<html:hidden name="WORKFLOW_SITES_FORM" property="displayIds" value="<%=(\"\"+eleid)%>" />
</td
</tr>

</logic:iterate>

<tr>
<td colspan="6"></td>
<td>
<html:submit property="action">
<app:storeMessage  key="global.action.label.save"/>
</html:submit>

<html:reset>
<app:storeMessage  key="admin.button.reset"/>
</html:reset>

</td>
</tr>
</table>

</logic:greaterThan>
</logic:present>

  <pg:index>
    Result Pages:
    <pg:prev>&nbsp;<a href="<%= pageUrl %>">[<< Prev]</a></pg:prev>
    <pg:pages><%
      if (pageNumber.intValue() < 10) { 
        %>&nbsp;<%
      }
      if (pageNumber == pagerPageNumber) { 
        %><b><%= pageNumber %></b><%
      } else { 
        %><a href="<%= pageUrl %>"><%= pageNumber %></a><%
      }
    %>
    </pg:pages>
    <pg:next>&nbsp;<a href="<%= pageUrl %>">[Next >>]</a></pg:next>
    <br></font>
  </pg:index>
</pg:pager>

</html:form>

</div>

<% if ("Y".equals(isMSIE)) { %>
<script for=document event="onclick()">
<!--
document.all.CalFrame.style.display="none";
//-->
</script>
<% }  %>

<jsp:include flush='true' page="ui/admFooter.jsp"/>

</body>

</html:html>






