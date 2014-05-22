<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.cleanwise.view.forms.StorePortalForm" %>
<%@ page import="com.cleanwise.view.utils.SessionAttributes" %>
<%@ page import="com.cleanwise.view.utils.RequestPropertyNames" %>
<%@ page import="com.cleanwise.view.forms.AggregateItemMgrForm"%>
<%@ page import="com.cleanwise.view.forms.ReportingLocateStoreSiteForm"%>
<%@ page import="com.cleanwise.view.forms.AnalyticReportForm"%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>


<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<%
  String jspFormName = request.getParameter("jspFormName");
  if(jspFormName == null){
	throw new RuntimeException("jspFormName cannot be null");
  }

  //LocateStoreSiteForm theForm = null;
  String jspFormNestProperty = request.getParameter("jspFormNestProperty");
  if(jspFormNestProperty != null){
	jspFormNestProperty = jspFormNestProperty + ".reportingLocateStoreSiteForm";
  }else{
	jspFormNestProperty = "reportingLocateStoreSiteForm";
  }
 %>
 <bean:define id="mainForm" name="<%=jspFormName%>" scope="session"/>
  <logic:present name="<%=jspFormName%>" property="<%=jspFormNestProperty%>" >

  <bean:define id="theForm" name="<%=jspFormName%>" property="<%=jspFormNestProperty%>" type="ReportingLocateStoreSiteForm"/>

  <%
  if(theForm != null && theForm.getReportingLocateStoreSiteFl()){
  String jspFormAction = request.getParameter("jspFormAction");
  if(jspFormAction == null){
	throw new RuntimeException("jspFormAction cannot be null");
  }
  String jspSubmitIdent = request.getParameter("jspSubmitIdent");
  if(jspSubmitIdent == null){
	throw new RuntimeException("jspSubmitIdent cannot be null");
  }
  String jspReturnFilterProperty = request.getParameter("jspReturnFilterProperty");
  if(jspReturnFilterProperty == null){
	throw new RuntimeException("jspReturnFilterProperty cannot be null");
  }
  String jspDataSourceType = request.getParameter("jspDataSourceType");
  if (jspDataSourceType == null) {
      jspDataSourceType = "";
  }
  theForm.setDataSourceType(jspDataSourceType);
  String maxSites = request.getParameter("jspMaxSites");
  jspSubmitIdent += "#"+SessionAttributes.SEARCH_FORM.LOCATE_REPORT_SITE_FORM;
%>


<html:html>
<script language="JavaScript1.2">
<!--
function SetCheckedSites (val) {
var dml=document.forms['reportingLocateStoreSiteForm'];
var ellen = dml.elements.length;
  //alert('next_form='+ellen);
  for(j=0; j<ellen; j++) {
    if (dml.elements[j].name=='<%=jspFormNestProperty%>.selected') {
      dml.elements[j].checked=val;

    }
  }

 }



function SetAndSubmitSites(name, val) {
  var dml=document.forms['reportingLocateStoreSiteForm'];
  var ellen = dml[name].length;
  if(ellen>0) {
  for(j=0; j<ellen; j++) {
    if(dml[name][j].value==val) {
      found = true;
      dml[name][j].checked=1;
    } else {
      dml[name][j].checked=0;
    }
  }
  } else {
    dml[name].checked=1;
  }
  var iiLast = dml['action'].length-1;
  dml['action'][iiLast].value='Return Selected';
  dml.submit();
}

function submitenter(myfield,e)
{
var keycode;
if (window.event) keycode = window.event.keyCode;
else if (e) keycode = e.which;
else return true;

if (keycode == 13)
   {
   myfield.form.submit();
   return false;
   }
else
   return true;
}

//-->
</script>



<body>

<script src="../externals/lib.js" language="javascript"></script>
<html:form styleId="reportingLocateStoreSiteForm" action="<%=jspFormAction%>"  scope="session" focus="mainField">
<html:hidden property="<%=RequestPropertyNames.PROPERTY_NAME_LOCATE_SITE_TYPE%>" value="<%=RequestPropertyNames.PROPERTY_VALUE_LOCATE_SITE_REPORT%>"/>
<html:hidden property="dataSourceType" value="<%=jspDataSourceType%>"/>

<div class="rptmid">
Find Sites
<table ID=392 width="750"  border="0"  class="mainbody">

  <html:hidden property="jspSubmitIdent" value="<%=jspSubmitIdent%>"/>
  <%String prop=jspFormNestProperty+".property";%>
  <html:hidden property="<%=prop%>" value="<%=jspReturnFilterProperty%>"/>
  <%prop=jspFormNestProperty+".name";%>
  <html:hidden property="<%=prop%>" value="<%=jspFormName%>"/>

  <tr> <td align="right">
	   <%prop=jspFormNestProperty+".searchField";%>

      <b>Search Site:</b></td>
	  <td><html:text styleId="mainField" property="<%=prop%>" onkeypress="return submitenter(this,event)"/>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <%prop=jspFormNestProperty+".searchType";%>
        <%if (!jspDataSourceType.equals("DW")){ %>
          <html:radio property="<%=prop%>" value="id" /> ID
        <% }%>
          <html:radio property="<%=prop%>" value="nameBegins" /> Name(starts with)
          <html:radio property="<%=prop%>" value="nameContains" /> Name(contains) </td>
  </tr>
 <tr>
<td align="right"><b>City:</b></td>
<%prop=jspFormNestProperty+".city" ;%>
<td><html:text  property="<%=prop%>" onkeypress="return submitenter(this,event)"/> </td>
</tr>
 <tr>
<td align="right"><b>State:</b></td>
<%prop=jspFormNestProperty+".state" ;%>
<td><html:text  property="<%=prop%>" onkeypress="return submitenter(this,event)"/> </td>
</tr>



  <tr>
      <td colspan='4'>
	<html:submit property="action" value="Search"/>
	<%prop=jspFormNestProperty+".showInactiveFl";%>
	Show Inactive: <html:checkbox property="<%=prop%>"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<html:submit property="action" value="Cancel"/>
	<html:submit property="action" value="Return Selected"/></td>

  </tr>
</table>


<!-- logic:present name="locateStoreSiteForm.Sites" -->
<% if(theForm.getSites()!=null) {
 int rescount = theForm.getSites().size();

%>
Search result count: <%=rescount%>
<% if(rescount>0)   {
if(maxSites!=null && maxSites.length()>0){ %>
<div>&nbsp;<%=maxSites%> (Request Maximum)&nbsp;</div>
<%}else if(rescount>=Constants.MAX_SITES_TO_RETURN){ %>
<div>&nbsp;1000 (Request Maximum)&nbsp;</div>
<%}%>
<table ID=393  width="750"  border="0" class="results">
<tr align=left>
<% if (!jspDataSourceType.equals("DW")) { %>
<td><a ID=394 class="tableheader">Site Id</a></td>
<% } %>
<td><a ID=395 class="tableheader">Site Name</a></td>
<td>
<a ID=396 href="javascript:SetCheckedSites(1)">[Check&nbsp;All]</a><br>
<a ID=397 href="javascript:SetCheckedSites(0)">[&nbsp;Clear]</a>
</td>
<% if (!jspDataSourceType.equals("DW")) { %>
<td><a ID=398 class="tableheader">Rank</a></td>
<%}%>
<td><a ID=399 class="tableheader">Account Name</a></td>
<td><a ID=400 class="tableheader">Street Address</a></td>
<td><a ID=401 class="tableheader">City</a></td>
<td><a ID=402 class="tableheader">State</a></td>
<td><a ID=403 class="tableheader">Status</a></td>
</tr>

<%
  String propName = jspFormNestProperty + ".sites";
  prop=jspFormNestProperty+".sites";
  String selectBoxProp = jspFormNestProperty+".selected";

 %>


<logic:iterate id="arrele" name="<%=jspFormName%>" property="<%=prop%>"
       type="com.cleanwise.service.api.value.SiteView">
    <bean:define id="key"  name="arrele" property="id" />
         <% String linkHref = "javascript: SetAndSubmitSites('"+selectBoxProp+"',"+key+");";%>
<tr>
<% if (!jspDataSourceType.equals("DW")) { %>
<td><bean:write name="arrele" property="id"/></td>
<%} %>
<td><a ID=404 href="<%=linkHref%>"><bean:write name="arrele" property="name"/></a></td>
<td><html:multibox property="<%=selectBoxProp%>" value="<%=key.toString()%>"/></td>
<% if (!jspDataSourceType.equals("DW")) { %>
<td><bean:write name="arrele" property="targetFacilityRank"/></td>
<% } %>
<td><bean:write name="arrele" property="accountName"/></td>
<td><bean:write name="arrele" property="address"/></td>
<td><bean:write name="arrele" property="city"/></td>
<td><bean:write name="arrele" property="state"/></td>
<td><bean:write name="arrele" property="status"/></td>
</tr>
</logic:iterate>
</table>
<%}%>
<%}%>
<!-- /logic:present -->
</div>
<html:hidden  property="action" value="Search"/>
</html:form>
</body>

</html:html>

<%}//if(theForm.getLocateSiteFl())%>
</logic:present> <%--main form--%>
