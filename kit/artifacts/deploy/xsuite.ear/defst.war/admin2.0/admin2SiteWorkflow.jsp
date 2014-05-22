<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.Hashtable"%>

<jsp:include flush='true' page="../general/checkBrowser.jsp"/>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<script language="JavaScript1.2">
<!--
function clearAssignedWorkflow() {
 dml=document.ADMIN2_SITE_WORKFLOW_MGR_FORM;
 len = dml.elements.length;
 var i=0;
 for( i=0 ; i<len ; i++) {
   if (dml.elements[i].name=='selectedWorkflowId') {
     dml.elements[i].checked=0;
   }
 }
}

function actionSubmit(formNum, action) {
    var actions;
    actions = document.forms[formNum]["action"];
    for (ii = actions.length - 1; ii >= 0; ii--) {
        if (actions[ii].value == 'hiddenAction') {
            actions[ii].value = action;
            document.forms[formNum].submit();
            break;
        }
    }
    return false;
}

function show_workflow_rule(id)
{

if(eval("document.getElementById(id)").style.display=='none'){
eval("document.getElementById(id)").style.display='block';
eval("document.getElementById('viewdetail'+id)").innerText="[-]";
}

else {
eval("document.getElementById(id)").style.display='none';
eval("document.getElementById('viewdetail'+id)").innerText="[+]";
}

}


//-->
</script>

<html:html>

<head>

<link rel="stylesheet" href="../externals/styles.css">
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body>
<div class="text">
<%!private boolean authorizedStoreMgrTabSite=false;%>

<html:form styleId="1296" name="ADMIN2_SITE_WORKFLOW_MGR_FORM" action="/admin2.0/admin2SiteWorkflow.do"
scope="session" type="com.cleanwise.view.forms.Admin2SiteWorkflowMgrForm">
<bean:define id="theForm" name="ADMIN2_SITE_WORKFLOW_MGR_FORM" type="com.cleanwise.view.forms.Admin2SiteWorkflowMgrForm"/>

<bean:define  id="aId" name="ADMIN2_SITE_WORKFLOW_MGR_FORM"  property="accountId" type="java.lang.String"/>

<table ID=1297 width="769"  class="mainbody"> <%-- Main table. --%>
<tr>
  <td><b><app:storeMessage key="admin2.site.detail.label.accountId"/>:</b></td>
  <td><bean:write name="ADMIN2_SITE_WORKFLOW_MGR_FORM" property="accountId" scope="session"/></td>
  <td><b><app:storeMessage key="admin2.site.detail.label.accountName"/>:</b></td>
  <td><bean:write name="ADMIN2_SITE_WORKFLOW_MGR_FORM" property="accountName" scope="session"/></td>
</tr>
<tr>
  <td><b><app:storeMessage key="admin2.site.detail.label.siteId"/>:</b></td>
  <td><bean:write name="ADMIN2_SITE_WORKFLOW_MGR_FORM" property="siteData.siteId" scope="session"/></td>
  <td><b><app:storeMessage key="admin2.site.detail.label.siteName"/>:</b></td>
  <td><bean:write name="ADMIN2_SITE_WORKFLOW_MGR_FORM" property="siteData.busEntity.shortDesc" scope="session"/></td>
</tr>

<% String workflowStatusCd ="";
  if (theForm.getSiteWorkflow()!=null) {
    Integer wid = theForm.getSiteWorkflow().getWorkflowId();
    workflowStatusCd = theForm.getSiteWorkflow().getWorkflowStatusCd();
  }
%>
<tr>
  <td><b><app:storeMessage key="admin2.site.workflow.label.type"/>:</b></td>
  <td>
    <html:select name="ADMIN2_SITE_WORKFLOW_MGR_FORM"
                 property="workflowTypeCd"
                 onchange="actionSubmit('0','init')">
        <html:options collection="Site.workflow.type.vector" property="value"/>
    </html:select>
  </td>
  <td><b><app:storeMessage key="admin2.site.workflow.label.status"/>:</b></td>
  <td><%=workflowStatusCd%></td>
</tr>
</table>
<br>
<table ID=1298 width="769" cellspacing=0 >
<% if (theForm.getSiteWorkflow()==null) {%>
  <tr>
    <th><app:storeMessage key="admin2.errors.noWorkflowDefinedYet"/></th>
  </tr>
<% } %>
</table> <%-- Main table --%>
<br>
<%-- Rules for the workflow. --%>
<table ID=1299 width="769" cellspacing=0 >
<% if (theForm.getWorkflowRules()!= null) {
   int rescount = theForm.getWorkflowRules().size();
  %>
  <tr>
    <th><app:storeMessage key="admin2.site.workflow.label.rule"/></th>
    <th colspan=3><app:storeMessage key="admin2.site.workflow.label.expression"/></th>
    <th><app:storeMessage key="admin2.site.workflow.label.action"/></th>
  </tr>
  <% if (rescount > 0){
     int idx = 0;
     WorkflowRuleDataVector workflowRules = theForm.getWorkflowRules();
     Iterator iter = workflowRules.iterator();
     while (iter.hasNext()) {
       WorkflowRuleData rule = (WorkflowRuleData) iter.next();
       int wrid = rule.getWorkflowRuleId();
    %>
    <% if ( (idx%2) == 0 ) { %>
      <tr class="rowa" align=center>
        <% } else { %>
        <tr class="rowb" align=center>
          <% } %>

          <td>
          <%--
          Show the order or evaluation, this may not be the
          same as the sequence number stored in the database.
          --%>
          <%= idx + 1 %>
          </td>
          <td><%=rule.getRuleTypeCd()%></td>
          <td><%=rule.getRuleExp()%></td>
          <td><%=rule.getRuleExpValue()%></td>

          <td>
            <%=rule.getRuleAction()%>
            <% if (rule.getRuleAction().matches("Forward")){%>
              <br>(<%=rule.getNextActionCd()%>)
            <%}%>
            <% if (rule.getRuleAction().matches("Send")){%>
              <br>(<%=rule.getNextActionCd()%>)
            <%}%>
          </td>
        </tr>
    <%  idx++; %>
    <%}%>
  <%}%>
<%}%>
<div style="width:769; text-align:right;">
<%/*
<app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ADMIN2_MGR_TAB_ACCOUNT% >">
<%authorizedStoreMgrTabSite=true;% >
</app:authorizedForFunction>
*/%>
<bean:define id="htWRC" name="ADMIN2_SITE_WORKFLOW_MGR_FORM" property="workflowRulesTab"/>

<td colspan="7" align="right" ><a ID=1300 href="javascript:clearAssignedWorkflow()"><app:storeMessage key="admin2.label.clear"/></a></td>
</div>
<table ID=1301  width="<%=Constants.TABLEWIDTH%>">
 <thead>
<tr class=stpTH align=left>
<th></th>
<th class="stpTH"><a ID=1302  href="admin2SiteWorkflow.do?action=sortWorkflows&sortField=id"><app:storeMessage key="admin2.site.workflow.label.workflowId"/></a></th>
<th class="stpTH"><a ID=1303  href="admin2SiteWorkflow.do?action=sortWorkflows&sortField=name"><app:storeMessage key="admin2.site.workflow.label.workflowName"/></a></th>
<th class="stpTH"><a ID=1304  href="admin2SiteWorkflow.do?action=sortWorkflows&sortField=type"><app:storeMessage key="admin2.site.workflow.label.workflowType"/></a></th>
<th class="stpTH"><a ID=1305  href="admin2SiteWorkflow.do?action=sortWorkflows&sortField=status"><app:storeMessage key="admin2.site.workflow.label.workflowStatus"/></a></th>
<th class="stpTH"><app:storeMessage key="admin2.site.workflow.label.workflowAssigned"/></th>
</tr>
 </thead>
    <tbody id="resTblBdy">
<logic:iterate id="arrele" name="ADMIN2_SITE_WORKFLOW_MGR_FORM" property="workflowFound">

<bean:define id="eleid" name="arrele" property="workflowId"/>
<tr class=stpTD>
<td><div id="viewdetailwId<%=eleid%>" class="styleText" style="cursor:hand" onClick="show_workflow_rule('wId<%=eleid%>');">[+]</div></td>
<td  class=stpTD><bean:write name="arrele" property="workflowId"/> </td>
<td  class=stpTD>

    <%if(authorizedStoreMgrTabSite){%>
       <a ID=1306 href="storeAccountWorkflow.do?action=initAccountAndGetWorkflow&accountId=<%=aId%>&searchField=<%=eleid%>">
       <bean:write name="arrele" property="shortDesc"/></a>
    <%} else {%>
       <bean:write name="arrele" property="shortDesc"/>
    <%}%>


</td>
<td class=stpTD><bean:write name="arrele" property="workflowTypeCd"/></td>
<td class=stpTD><bean:write name="arrele" property="workflowStatusCd"/></td>
<td align="center"><html:radio name="ADMIN2_SITE_WORKFLOW_MGR_FORM" property="selectedWorkflowId" value="<%=String.valueOf(eleid)%>"/></td>


</tr>
<tr><td colspan="7" >




<%-- Rules for the workflow. --%>
<table id="wId<%=eleid%>" width="769" cellspacing=0 style="cursor:hand ; display:none" onClick="show_workflow_rule('wId<%=eleid%>');" >
<%
 WorkflowRuleDataVector wrv=( WorkflowRuleDataVector)((Hashtable)htWRC).get(eleid);
if( wrv!=null){
int rescount= wrv.size();
 %>
  <tr>
    <th><app:storeMessage key="admin2.site.workflow.label.rule"/></th>
    <th colspan=3><app:storeMessage key="admin2.site.workflow.label.expression"/></th>
    <th><app:storeMessage key="admin2.site.workflow.label.action"/></th>
  </tr>
<%if(rescount>0){
int idx=0;
Iterator iter= wrv.iterator();
while(iter.hasNext()){
WorkflowRuleData  wrd  =(WorkflowRuleData) iter.next();
int wrid= wrd.getWorkflowRuleId();

  if ( (idx%2) == 0 ) { %>
  <tr class="rowa" align=center>
  <% } else { %>
  <tr class="rowb" align=center>
  <% } %>

    <td>
<%--
Show the order or evaluation, this may not be the
same as the sequence number stored in the database.
--%>
    <%= idx + 1 %>

</td>
    <td><%=wrd.getRuleTypeCd()%></td>
    <td><%=wrd.getRuleExp()%></td>
    <td><%=wrd.getRuleExpValue()%></td>

    <td>
    <%=wrd.getRuleAction()%>
    <%if (wrd.getRuleAction()!=null&&wrd.getRuleAction().indexOf("Forward")>-1){%>
    <br>(<%=wrd.getNextActionCd()%>)
   <%}%>
      <%if (wrd.getRuleAction()!=null&&wrd.getRuleAction().indexOf("Send")>-1){%>
        <br>(<%=wrd.getNextActionCd()%>)
     <%}%>
    </td>
  </tr>
<%idx++;} } }%>

</table> </td></tr>

</logic:iterate>
    </tbody>
     <td colspan="6" align="right" >
<html:reset>
    <app:storeMessage  key="admin.button.reset"/>
</html:reset>
<html:submit property="action">
    <app:storeMessage  key="admin2.site.workflow.button.assign"/>
</html:submit>
 </td>
</table>
<html:hidden property="action" value="hiddenAction"/>
</table><%-- Rules for the workflow. --%>
</html:form>

</div>
</body>

</html:html>
