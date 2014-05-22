<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="java.util.Locale" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<jsp:include flush='true' page="storeAccountCtx.jsp"/>

<div class="text">
  <table ID=498 width="<%=Constants.TABLEWIDTH%>" border="0" cellspacing="0" cellpadding="0" class="mainbody">
  <html:form styleId="499" name="STORE_WORKFLOW_SEARCH_FORM"
    action="storeportal/storeAccountWorkflow.do"
    scope="session" type="com.cleanwise.view.forms.StoreWorkflowMgrSearchForm">

<tr class="results"> <td><b>Find Workflow:</b></td>
<td colspan="3">
<html:text name="STORE_WORKFLOW_SEARCH_FORM" property="searchField"/>
</td>
</tr>

  <tr class="results"> <td></td>
       <td colspan="3">
 <html:radio name="STORE_WORKFLOW_SEARCH_FORM" property="searchType"
  value="id" /> ID
 <html:radio name="STORE_WORKFLOW_SEARCH_FORM" property="searchType"
  value="nameBegins" /> Name(starts with)
 <html:radio name="STORE_WORKFLOW_SEARCH_FORM" property="searchType"
  value="nameContains" /> Name(contains)
         </td>
  </tr>
  <tr> <td></td>
       <td colspan="3">
	<html:submit property="action">
		<app:storeMessage  key="global.action.label.search"/>
	</html:submit>
     &nbsp;
    <html:submit property="action">
		<app:storeMessage  key="admin.button.create"/>
	</html:submit>
     </html:form>
    </td>
  </tr>
</table>

<logic:present name="Workflow.found.vector">
<bean:size id="rescount"  name="Workflow.found.vector"/>
Search result count:  <bean:write name="rescount" />
<logic:greaterThan name="rescount" value="0">



<table class="stpTable_sortable" id="ts1"  >
    <thead>
<tr class=stpTH align=left>
<th class="stpTH">Workflow&nbsp;Id</th>
<th class="stpTH">Workflow&nbsp;Name</th>
<th class="stpTH">Type</th>
<th class="stpTH">Status</th>
</tr>
 </thead>
    <tbody>
<logic:iterate id="arrele" name="Workflow.found.vector">

<tr class=stpTD>
<td  class=stpTD><bean:write name="arrele" property="workflowId"/></td>
<td  class=stpTD><bean:define id="eleid" name="arrele" property="workflowId"/>
<a ID=500 href="storeAccountWorkflow.do?action=detail&searchType=id&searchField=<%=eleid%>">
<bean:write name="arrele" property="shortDesc"/></a>
</td>
<td class=stpTD><bean:write name="arrele" property="workflowTypeCd"/></td>
<td class=stpTD><bean:write name="arrele" property="workflowStatusCd"/></td>
</tr>

</logic:iterate>
    </tbody>
</table>




</logic:greaterThan>
</logic:present>

</div>


