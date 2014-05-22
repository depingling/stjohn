<%!private boolean authorizedStoreMgrTabSite=false;%>
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
 dml=document.STORE_ADMIN_SITE_FORM;
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
<html:form styleId="1296" name="STORE_ADMIN_SITE_FORM" action="/storeportal/siteworkflow.do"
scope="session" type="com.cleanwise.view.forms.StoreSiteMgrForm">
<bean:define  id="aId" name="STORE_ADMIN_SITE_FORM"  property="accountId" type="java.lang.String"/>
<table ID=1297 width="769"  class="mainbody"> <%-- Main table. --%>
<tr>
<tr><td><b>Account&nbsp;Id:</b></td>
<td><%=aId%></td>
<td><b>Account&nbsp;Name:</b></td>
<td><bean:write name="STORE_ADMIN_SITE_FORM" property="accountName" scope="session"/></td>
</tr>
<td><b>Site&nbsp;Id:</b> </td>
<td><bean:write name="STORE_ADMIN_SITE_FORM" property="id" scope="session"/></td>
<td><b>Site&nbsp;Name:</b> </td>
<td><bean:write name="STORE_ADMIN_SITE_FORM" property="name" scope="session"/></td>
</tr>

<logic:present name="Site.workflow">

<tr>
<td><b>Workflow Id:</b></td>
<td>
<bean:write name="Site.workflow" property="workflowId"/>
<bean:define id="wid" type="java.lang.Integer"
   name="Site.workflow" property="workflowId"/>
</td>
<td><b>Name:</b></td>
<td><bean:write name="Site.workflow" property="shortDesc"/></td>
</tr>
</logic:present>

<tr><td><b>Type:</b></td>
<td>
    <html:select name="STORE_ADMIN_SITE_FORM"
                 property="workflowTypeCd"
                 onchange="actionSubmit('0','site_workflow')">
        <html:options collection="Site.workflow.type.vector" property="value"/>
    </html:select>     
</td>
<td><b>Status:</b></td>
    <td><logic:present name="Site.workflow">
    <bean:write name="Site.workflow" property="workflowStatusCd"/></logic:present>
    </td>
</tr>
</table>
<br>
<table ID=1298 width="769" cellspacing=0 >
<logic:notPresent name="Site.workflow">
<tr>
<th>No Workflow Defined Yet</th>
</tr>
</logic:notPresent>
</table> <%-- Main table --%>
<br>
<%-- Rules for the workflow. --%>
<table ID=1299 width="769" cellspacing=0 >
<logic:present name="Site.workflow.rules.vector">
<bean:size id="rescount"  name="Site.workflow.rules.vector"/>
  <tr>
    <th>Rule</th>
    <th colspan=3>Expression</th>
    <th>Action</th>
  </tr>
<logic:greaterThan name="rescount" value="0">
<define:bean id="idx" type="java.lang.Integer" value="0"/>

<logic:iterate id="arrele" indexId="idx" name="Site.workflow.rules.vector">
<bean:define id="wrid" type="java.lang.Integer"
  name="arrele" property="workflowRuleId"/>

  <% if ( (idx.intValue()%2) == 0 ) { %>
  <tr class="rowa" align=center>
  <% } else { %>
  <tr class="rowb" align=center>
  <% } %>

    <td>
<%--
Show the order or evaluation, this may not be the
same as the sequence number stored in the database.
--%>
    <%= idx.intValue() + 1 %>
</td>
    <td><bean:write name="arrele" property="ruleTypeCd"/></td>
    <td><bean:write name="arrele" property="ruleExp"/></td>
    <td><bean:write name="arrele" property="ruleExpValue"/></td>

    <td>
      <bean:write name="arrele" property="ruleAction"/>
      <logic:match name="arrele" property="ruleAction"
        value="Forward" >
        <br>(<bean:write name="arrele" property="nextActionCd"/>)
      </logic:match>
      <logic:match name="arrele" property="ruleAction"
        value="Send" >
        <br>(<bean:write name="arrele" property="nextActionCd"/>)
      </logic:match>
    </td>
  </tr>
</logic:iterate>
</logic:greaterThan>
</logic:present>
<div style="width:769; text-align:right;">

<app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.STORE_MGR_TAB_ACCOUNT%>">
<%authorizedStoreMgrTabSite=true;%>
</app:authorizedForFunction>

<bean:define id="htWRC" name="Site.workflow.rules.hashtable"/>

<td colspan="7" align="right" ><a ID=1300 href="javascript:clearAssignedWorkflow()">[&nbsp;Clear]</a></td>
</div>
<table ID=1301  width="<%=Constants.TABLEWIDTH%>">
 <thead>
<tr class=stpTH align=left>
<th></th>
<th class="stpTH"><a ID=1302  href="siteworkflow.do?action=sortWorkflows&sortField=id"> Workflow&nbsp;Id</a></th>
<th class="stpTH"><a ID=1303  href="siteworkflow.do?action=sortWorkflows&sortField=name">Workflow&nbsp;Name</a></th>
<th class="stpTH"><a ID=1304  href="siteworkflow.do?action=sortWorkflows&sortField=type">Type</a></th>
<th class="stpTH"><a ID=1305  href="siteworkflow.do?action=sortWorkflows&sortField=status">Status</a></th>
<th class="stpTH">Assigned</th>
</tr>
 </thead>
    <tbody id="resTblBdy">
<logic:iterate id="arrele" name="Site.workflow.found.vector">
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
<td align="center"><html:radio name="STORE_ADMIN_SITE_FORM" property="selectedWorkflowId" value="<%=String.valueOf(eleid)%>"/></td>


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
    <th>Rule</th>
    <th colspan=3>Expression</th>
    <th>Action</th>
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

<html:submit property="action" value="Assign"/>
 </td>
</table>
<html:hidden property="action" value="hiddenAction"/>
</table><%-- Rules for the workflow. --%>
</html:form>

</div>
</body>

</html:html>
