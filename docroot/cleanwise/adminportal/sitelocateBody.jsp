<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<script src="../externals/lib.js" language="javascript"></script>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<%
	String feedField =  (String)request.getParameter("feedField");
	if(null == feedField) {
		feedField = new String("");
	}
	String feedDesc =  (String)request.getParameter("feedDesc");
	if(null == feedDesc) {
		feedDesc = new String("");
	}
	String locateFilter =  (String)request.getParameter("locateFilter");
	if(null == locateFilter) {
		locateFilter = new String("");
	}
	
	String accountId = request.getParameter("accountid");
	if (null == accountId || "".equals(accountId)) {
		accountId = new String("0");
	}
	
	SiteViewVector sites = (SiteViewVector)session.getAttribute("Site.found.vector"); 
	if (null != sites && sites.size() > 0 && ! "0".equals(accountId)) {
		SiteViewVector newSites = new SiteViewVector(); 
		for (int i = 0; i < sites.size(); i++) {
			SiteView site = (SiteView) sites.get(i);
			if(String.valueOf(site.getAccountId()).equals(accountId)) {
				newSites.add(site);
			}			
		}
		session.setAttribute("Site.found.vector", newSites);
	}
%>

<script language="JavaScript1.2">
<!--

function passIdAndName(id, name) {
  var feedBackFieldName = document.SITE_SEARCH_FORM.feedField.value;
  var feedDesc = document.SITE_SEARCH_FORM.feedDesc.value;

  if(feedBackFieldName && ""!= feedBackFieldName) {
    window.opener.document.forms[0].elements[feedBackFieldName].value = id;
  }
  if(feedDesc && ""!= feedDesc) {
    window.opener.document.forms[0].elements[feedDesc].value = unescape(name.replace(/\+/g, ' '));
  }
  if(window.opener.document.forms[0].name == "CALL_OP_DETAIL_FORM"){
    popAcctForCallTracking();
  }
  self.close();
}

function popAcctForCallTracking(){

  window.opener.document.forms[0].change.value='order';   
  window.opener.document.forms[0].changefield.value='siteId';    
  window.opener.document.forms[0].submit();

}

//-->
</script>


<div class="text">

<html:form name="SITE_SEARCH_FORM" 
action="/adminportal/sitelocate.do" focus="searchField"
type="com.cleanwise.view.forms.SiteMgrSearchForm">
<input type="hidden" name="feedField" value="<%=feedField%>">
<input type="hidden" name="feedDesc" value="<%=feedDesc%>">
<input type="hidden" name="locateFilter" value="<%=locateFilter%>">
<input type="hidden" name="accountid" value="<%=accountId%>">

<table border="0"  class="mainbody">
  <html:form name="SITE_SEARCH_FORM" action="adminportal/sitemgr.do"
    scope="session" type="com.cleanwise.view.forms.SiteMgrSearchForm">
	<tr>
	          <td colspan="6" align=right>
<a href="#" onclick="f_reset_fields(document.SITE_SEARCH_FORM);">
<input type="button" value="Reset search fields"></a> 
</td>
</tr>
  <tr> <td align="right"><b>Find Site</b></td>
<td colspan=4 >
<html:text name="SITE_SEARCH_FORM" property="searchField"/>
<html:radio name="SITE_SEARCH_FORM" property="searchType" 
    value="id"           styleId="searchOpt0"/>  ID
<html:radio name="SITE_SEARCH_FORM" property="searchType" 
    value="nameBegins"   styleId="searchOpt1"/>  Name(starts with)
<html:radio name="SITE_SEARCH_FORM" property="searchType" 
    value="nameContains" styleId="searchOpt2" />  Name(contains)
</td>
</tr>

<script>
<!--
  var ix0 = document.getElementById("searchOpt0");
  var ix1 = document.getElementById("searchOpt1");
  var ix2 = document.getElementById("searchOpt2");
  if ( ix0 != null && ix0.checked == false &&
       ix2 != null && ix2.checked == false &&
       ix1 != null) {
	ix1.checked = true;
  }
// -->
</script>


<tr> 
<td align="right"><b>City</b></td>
<td><html:text name="SITE_SEARCH_FORM" property="city"/> </td>


<script language="JavaScript">

function f_get_accts(pLoc, name, pDesc) {
  var loc = pLoc + ".do?feedField=" + name + "&amp;feedDesc=" + pDesc;
  locatewin = window.open(loc,"LocateAccts", "menubar=yes,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
  locatewin.focus();
  return false;
}

</script>

<% 
String onClick = 
"f_get_accts('../adminportal/accountLocateMulti', 'accountIdList', '');";
%>

<td colspan=2 align="right"><b>Account(s)</b></td>
<td>
<html:text name="SITE_SEARCH_FORM" property="accountIdList"/> 
<html:button property="locateAccount"
 onclick="<%=onClick%>" value="Locate Account(s)"/>
</td>

</tr>

<tr> 
<td align="right"><b>State</b></td>
<td><html:text name="SITE_SEARCH_FORM" property="state"/> </td>
</tr>

  <tr> <td></td>
      <td colspan="3">
     <html:hidden name="SITE_SEARCH_FORM" property="searchType" value="full" />
	<html:submit property="action">
		<app:storeMessage  key="global.action.label.search"/>
	</html:submit>
     </html:form>
    </td>
  </tr>
</table>

</div>

<div>

<logic:present name="Site.found.vector">
<bean:size id="rescount"  name="Site.found.vector"/>
Search result count:  <bean:write name="rescount" />
<logic:greaterThan name="rescount" value="0">


<table width="769" border="0" class="results">
<tr align=left>
<td><a class="tableheader" href="sitelocate.do?action=sort&sortField=id">Site&nbsp;Id</td>
<td><a class="tableheader" href="sitelocate.do?action=sort&sortField=rank">Rank</td>
<td><a class="tableheader" href="sitelocate.do?action=sort&sortField=account">Account Name</td>
<td><a class="tableheader" href="sitelocate.do?action=sort&sortField=name">Site Name</td>
<td><a class="tableheader" href="sitelocate.do?action=sort&sortField=address">Street Address</td>
<td><a class="tableheader" href="sitelocate.do?action=sort&sortField=city">City</td>
<td><a class="tableheader" href="sitelocate.do?action=sort&sortField=state">State<br>Prov</td>
<td><a class="tableheader" href="sitelocate.do?action=sort&sortField=status">Status</td>
</tr>

<logic:iterate id="arrele" name="Site.found.vector">


<logic:greaterEqual name="arrele" property="targetFacilityRank" value="<%=com.cleanwise.service.api.util.Utility.TARGET_FACILITY_THRESHOLD.toString()%>">
<tr class="rowa">
</logic:greaterEqual>
<logic:lessThan name="arrele" property="targetFacilityRank" value="<%=com.cleanwise.service.api.util.Utility.TARGET_FACILITY_THRESHOLD.toString()%>">
<tr>
</logic:lessThan>

<td><bean:write name="arrele" property="id"/></td>
<td><bean:write name="arrele" property="targetFacilityRank"/></td>
<td>
<bean:write name="arrele" property="accountName"/>
</td>
<td>
  <bean:define id="key"  name="arrele" property="id"/>
  <bean:define id="name" name="arrele" property="name" type="java.lang.String"/>
    <% String onClick = new String("return passIdAndName('"+key+"', '"+ java.net.URLEncoder.encode(name) +"');");%>
    <a href="javascript:void(0);" onclick="<%=onClick%>">
	<bean:write name="arrele" property="name"/>
	</a>
</a>
</td>
<td>
<bean:write name="arrele" property="address"/>
</td>
<td>
<bean:write name="arrele" property="city"/>
</td>
<td>
<bean:write name="arrele" property="state"/>
</td>
<td><bean:write name="arrele" property="status"/></td>
</tr>

</logic:iterate>
</table>

</logic:greaterThan>
</logic:present>

</html:form>
</div>


