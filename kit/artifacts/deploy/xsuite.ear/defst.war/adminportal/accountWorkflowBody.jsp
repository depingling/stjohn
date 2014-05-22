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

<jsp:include flush='true' page="ui/accountCtx.jsp"/>

<div class="text">
  <table width="769" border="0" cellspacing="0" cellpadding="0" class="mainbody">
  <html:form name="WORKFLOW_SEARCH_FORM" 
    action="adminportal/accountWorkflow.do"
    scope="session" type="com.cleanwise.view.forms.WorkflowMgrSearchForm">

<tr class="results"> <td><b>Find Workflow:</b></td>
<td colspan="3">
<html:text name="WORKFLOW_SEARCH_FORM" property="searchField"/>
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

<pg:pager maxPageItems="<%= Constants.MAX_PAGE_ITEMS %>"
          maxIndexPages="<%= Constants.MAX_INDEX_PAGES %>">
  <pg:param name="pg"/>
  <pg:param name="q"/>

<table width="769"  class="results">
<tr align=left>
<td><a class="tableheader" href="accountWorkflow.do?action=sort&sortField=id">Workflow&nbsp;Id</td>
<td><a class="tableheader" href="accountWorkflow.do?action=sort&sortField=name">Workflow&nbsp;Name</td>
<td><a class="tableheader" href="accountWorkflow.do?action=sort&sortField=type">Type</td>
<td><a class="tableheader" href="accountWorkflow.do?action=sort&sortField=status">Status</td>
</tr>

<logic:iterate id="arrele" name="Workflow.found.vector">
<pg:item>
<tr>
<td><bean:write name="arrele" property="workflowId"/></td>
<td>
<bean:define id="eleid" name="arrele" property="workflowId"/>
<a href="accountWorkflow.do?action=detail&searchType=id&searchField=<%=eleid%>">
<bean:write name="arrele" property="shortDesc"/>
</a>
</td>
<td><bean:write name="arrele" property="workflowTypeCd"/></td>
<td><bean:write name="arrele" property="workflowStatusCd"/></td>
</tr>
</pg:item>
</logic:iterate>
</table>

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

</logic:greaterThan>
</logic:present>

</div>


