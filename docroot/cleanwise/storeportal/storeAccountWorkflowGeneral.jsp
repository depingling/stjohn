<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>


<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

  <bean:define id="wid" type="java.lang.String"  name="STORE_WORKFLOW_DETAIL_FORM" property="id"/>
<table ID=511 width="<%=Constants.TABLEWIDTH%>"  class="mainbody"> <%-- Header table. --%>
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
<bean:write name="STORE_WORKFLOW_DETAIL_FORM" property="id"/>
</td>
<td><b>Workflow Name:</b></td>
<td>
<html:text name="STORE_WORKFLOW_DETAIL_FORM" property="name"/>
<span class="reqind">*</span>
</td>
</tr>


<tr>
<td><b>Workflow Type:</b></td>
<td>
<% if (!wid.equals("0")) {
// This is an existing workflow.  Type is not to be modified.
%>
<bean:write name="STORE_WORKFLOW_DETAIL_FORM" property="typeCd"/>
<% } else { %>

<%
ArrayList v = (ArrayList)
  request.getSession().getAttribute("Workflow.type.vector");
int typelen = 0;
if (null != v) {
  typelen = v.size();
}
%>
<html:select name="STORE_WORKFLOW_DETAIL_FORM" property="typeCd">
<% if (typelen > 1) { %>
<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
<% } /* typelen check */ %>
<html:options  collection="Workflow.type.vector" property="value" />
</html:select>
<span class="reqind">*</span>
<% } /* workflow id check */ %>
</td>
<td><b>Workflow Status:</b></td>
<td>
<html:select name="STORE_WORKFLOW_DETAIL_FORM" property="statusCd">
<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
<html:options  collection="Workflow.status.vector" property="value" />
</html:select>
<span class="reqind">*</span>
</td>

</tr>
<tr>
<td></td>
<td colspan=4>
<html:submit property="action">
<app:storeMessage  key="global.action.label.save"/>
</html:submit>

<html:reset>
<app:storeMessage  key="admin.button.reset"/>
</html:reset>

<html:submit property="action">
<app:storeMessage  key="global.action.label.delete"/>
</html:submit>

<% if (!wid.equals("0")) { %>
<%
  String loc = "javascript:window.location='storeAccountWorkflowSites.do?action=sites&workflowId=" +
    wid + "' ";
%>
<html:button property="action" value="Sites" onclick='<%=loc%>' />
<% } %>
</td>
</tr>
</table>