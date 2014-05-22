<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="java.util.Locale" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

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
%>

<script language="JavaScript1.2">
<!--
function popLocate(pLoc, name, pDesc) {
  var loc = pLoc + ".do?feedField=" + name + "&amp;feedDesc=" + pDesc;
  locatewin = window.open(loc,"accountLocate", "menubar=no,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
  locatewin.focus();
  return false;
}

function popLocateBusEntity(name, pDesc) {
  var locpage = "store";
  for(i = 0; i < document.forms[0].elements['searchBusEntityType'].length; i++) {
  	if(true == document.forms[0].searchBusEntityType[i].checked) {
		locpage = document.forms[0].searchBusEntityType[i].value;
		break;
	}
  }
  var loc = "../adminportal/" + locpage + "locate.do?feedField=" + name + "&amp;feedDesc=" + pDesc;
  locatewin = window.open(loc,"busEntityLocate", "menubar=no,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
  locatewin.focus();
  return false;
}

function passIdAndName(id, name) {
  var feedBackFieldName = document.WORKFLOW_SEARCH_FORM.feedField.value;
  var feedDesc = document.WORKFLOW_SEARCH_FORM.feedDesc.value;

  if(feedBackFieldName && ""!= feedBackFieldName) {
    window.opener.document.forms[0].elements[feedBackFieldName].value = id;
  }
  if(feedDesc && ""!= feedDesc) {
    window.opener.document.forms[0].elements[feedDesc].value = unescape(name.replace(/\+/g, ' '));
  }
  self.close();
}

//-->
</script>

<div class="text">
  <html:form name="WORKFLOW_SEARCH_FORM" 
    action="adminportal/workflowLocate.do"
    scope="session" type="com.cleanwise.view.forms.WorkflowMgrSearchForm">

  <table width="769" border="0" cellspacing="0" cellpadding="0" class="mainbody">

  <tr>
  	<td><b>BusEntity&nbsp;Type:</b></td>
  	<td colspan="2">
		<html:radio name="WORKFLOW_SEARCH_FORM" property="searchBusEntityType" value="store"/>
		&nbsp;Store		
		&nbsp;&nbsp;&nbsp;&nbsp;	
		<html:radio name="WORKFLOW_SEARCH_FORM" property="searchBusEntityType" value="account"/>					
		&nbsp;Account				
	</td>
	<td>&nbsp;</td>	
  </tr>	

  <tr>
  	<td><b>BusEntity&nbsp;Id:</b></td>
  	<td colspan="2"><html:text name="WORKFLOW_SEARCH_FORM" property="busEntityIdS"/>
			<html:button property="action"
   				onclick="popLocateBusEntity('busEntityIdS', '');"
   				value="Locate BusEntity"/>	
	</td>
	<td>&nbsp;</td>	
  </tr>	
  
  <tr class="results"> <td><b>Find Workflow:</b></td>
       <td colspan="3">
			<html:text name="WORKFLOW_SEARCH_FORM" property="searchField"/>
			<input type="hidden" name="feedField" value="<%=feedField%>">
			<input type="hidden" name="feedDesc" value="<%=feedDesc%>">			
       </td>
  </tr>
  <tr class="results"> <td></td>
       <td colspan="3">
 <html:radio name="WORKFLOW_SEARCH_FORM" property="searchType" 
  value="id" /> ID
 <html:radio name="WORKFLOW_SEARCH_FORM" property="searchType" 
  value="nameBegins" /> Name(starts with)
 <html:radio name="WORKFLOW_SEARCH_FORM" property="searchType" 
  value="nameContains" /> Name(contains)
         </td>
  </tr>
  <tr> <td></td>
       <td colspan="3">
	<html:submit property="action">
		<app:storeMessage  key="global.action.label.search"/>
	</html:submit>
	<html:submit property="action">
		<app:storeMessage  key="admin.button.viewall"/>
	</html:submit>
    </td>
  </tr>
</table>

<logic:present name="WORKFLOW_SEARCH_FORM" property="workflowDescList">
<bean:size id="rescount"  name="WORKFLOW_SEARCH_FORM" property="workflowDescList"/>
Search result count:  <bean:write name="rescount" />
<logic:greaterThan name="rescount" value="0">


<table width="769"  class="results">
<tr align=left>
<td><a class="tableheader" href="workflowLocate.do?action=sort&sortField=id">Workflow&nbsp;Id</td>
<td><a class="tableheader" href="workflowLocate.do?action=sort&sortField=accountname">BusEntity&nbsp;Name</td>
<td><a class="tableheader" href="workflowLocate.do?action=sort&sortField=busEntityType">BusEntity&nbsp;Type</td>
<td><a class="tableheader" href="workflowLocate.do?action=sort&sortField=name">Workflow&nbsp;Name</td>
<td><a class="tableheader" href="workflowLocate.do?action=sort&sortField=type">Type</td>
<td><a class="tableheader" href="workflowLocate.do?action=sort&sortField=status">Status</td>
</tr>

<logic:iterate id="arrele" name="WORKFLOW_SEARCH_FORM" property="workflowDescList" type="com.cleanwise.service.api.value.WorkflowDescData">
<tr>
<td><bean:write name="arrele" property="workflow.workflowId"/></td>
<td><bean:write name="arrele" property="busEntityShortDesc"/></td>
<td><bean:write name="arrele" property="busEntityTypeCd"/></td>
<td>
    <bean:define id="key"  name="arrele" property="workflow.workflowId"/>
    <bean:define id="name" name="arrele" property="workflow.shortDesc" type="String"/>

    <% String onClick = new String("return passIdAndName('"+key+"','"+ java.net.URLEncoder.encode(name) +"');");%>
    <a href="javascript:void(0);" onclick="<%=onClick%>">
<bean:write name="arrele" property="workflow.shortDesc"/>
</a>
</td>
<td><bean:write name="arrele" property="workflow.workflowTypeCd"/></td>
<td><bean:write name="arrele" property="workflow.workflowStatusCd"/></td>
</tr>
</logic:iterate>

</table>


</logic:greaterThan>
</logic:present>

</html:form>


</div>


