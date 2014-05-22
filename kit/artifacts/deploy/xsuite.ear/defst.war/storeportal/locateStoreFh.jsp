<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.cleanwise.view.forms.StorePortalForm" %>
<%@ page import="com.cleanwise.view.utils.SessionAttributes" %>
<%@ page import="com.cleanwise.view.forms.AggregateItemMgrForm"%>
<%@ page import="com.cleanwise.view.forms.LocateStoreFhForm"%>


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

  //LocateStoreAccountForm theForm = null;
  String jspFormNestProperty = request.getParameter("jspFormNestProperty");
  if(jspFormNestProperty != null){
	jspFormNestProperty = jspFormNestProperty + ".locateStoreFhForm";
  }else{
	jspFormNestProperty = "locateStoreFhForm";
  }
 %>
  <logic:present name="<%=jspFormName%>" property="<%=jspFormNestProperty%>" >
  <bean:define id="theForm" name="<%=jspFormName%>" property="<%=jspFormNestProperty%>" type="LocateStoreFhForm"/>

  <%
  if(theForm != null && theForm.getLocateFhFl()){
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
  jspSubmitIdent += "#"+SessionAttributes.SEARCH_FORM.LOCATE_STORE_FH_FORM;
%>


<html:html>
<script language="JavaScript1.2">
<!--
function SetCheckedFh (val) {
var dml=document.forms['locateStoreFhForm'];
var ellen = dml.elements.length;
  //alert('next_form='+ellen);
  for(j=0; j<ellen; j++) {
    if (dml.elements[j].name=='<%=jspFormNestProperty%>.selected') {
      dml.elements[j].checked=val;

    }
  }

 }



function SetAndSubmitFh(name, val) {
  var dml=document.forms['locateStoreFhForm'];
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
<html:form  styleId="locateStoreFhForm" action="<%=jspFormAction%>"  scope="session" focus="mainField">
<div class="rptmid">
Find  Freight Handlers
<table ID=353 width="750"  border="0"  class="mainbody">

  <html:hidden property="jspSubmitIdent" value="<%=jspSubmitIdent%>"/>
  <%String prop=jspFormNestProperty+".property";%>
  <html:hidden property="<%=prop%>" value="<%=jspReturnFilterProperty%>"/>
  <%prop=jspFormNestProperty+".name";%>
  <html:hidden property="<%=prop%>" value="<%=jspFormName%>"/>

  <tr> <td align="right">
	   <%prop=jspFormNestProperty+".searchField";%>

      <b>Search Freight Handlers:</b></td>
	  <td><html:text styleId="mainField" property="<%=prop%>" onkeypress="return submitenter(this,event)"/>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <%prop=jspFormNestProperty+".searchType";%>
        <html:radio property="<%=prop%>" value="id" /> ID <html:radio property="<%=prop%>" value="nameBegins" />
        Name(starts with) <html:radio property="<%=prop%>" value="nameContains" />
        Name(contains) </td>
  </tr>
 <tr>

  <tr>
      <td colspan='4'>
	<html:submit property="action" value="Search"/>
	<%prop=jspFormNestProperty+".showInactiveFl";%>
	Show Inactive: <html:checkbox property="<%=prop%>"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<html:submit property="action" value="Cancel"/>
	<html:submit property="action" value="Return Selected"/></td>

  </tr>
</table>


<!-- logic:present name="locateStoreFreightHandlersForm.FreightHandler" -->
<% if(((LocateStoreFhForm)theForm).getFreightHandler()!=null) {
 int rescount = theForm.getFreightHandler().size();

%>
Search result count: <%=rescount%>
<% if(rescount>0) { %>

<table ID=354  width="750"  border="0" class="results">
<tr align=left>
<td><a ID=355 class="tableheader">Id</a></td>
<td><<a ID=356 class="tableheader">Name</a></td>
<td>
<a ID=357 href="javascript:SetCheckedFh(1)">[Check&nbsp;All]</a><br>
<a ID=358 href="javascript:SetCheckedFh(0)">[&nbsp;Clear]</a>
</td>

<td><a ID=359 class="tableheader">Address 1</a></td>
<td><a ID=360 class="tableheader">City</a></td>
<td><a ID=361 class="tableheader">State</a></td>
<td><a ID=362 class="tableheader">Status</a></td>
<td><a ID=363 class="tableheader">EDI Routing Code</a></td>
</tr>

<%
  String propName = jspFormNestProperty + ".freightHandler";
  prop=jspFormNestProperty+".freightHandler";
  String selectBoxProp = jspFormNestProperty+".selected";

 %>


<logic:iterate id="arrele" name="<%=jspFormName%>" property="<%=prop%>"
       type="com.cleanwise.service.api.value.FreightHandlerView">
    <bean:define id="key"  name="arrele" property="busEntityData.busEntityId"/>
       <bean:define id="name" name="arrele" property="busEntityData.shortDesc" type="String"/>
    <% String linkHref = "javascript: SetAndSubmitFh ('"+selectBoxProp+"',"+key+");";%>

<tr>
<td><bean:write name="arrele" property="busEntityData.busEntityId"/></td>
<td><a ID=364 href="<%=linkHref%>"><bean:write name="arrele" property="busEntityData.shortDesc"/></a></td>
<td><html:multibox property="<%=selectBoxProp%>" value="<%=key.toString()%>"/></td>
<td><bean:write name="arrele" property="primaryAddress.address1"/></td>
<td><bean:write name="arrele" property="primaryAddress.city"/></td>
<td><bean:write name="arrele" property="primaryAddress.stateProvinceCd"/></td>
<td><bean:write name="arrele" property="busEntityData.busEntityStatusCd"/></td>
<td><bean:write name="arrele" property="ediRoutingCd"/></td>
    
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

<%}%>
</logic:present> <%--main form--%>