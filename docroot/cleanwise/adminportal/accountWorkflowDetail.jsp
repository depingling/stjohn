<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>

<jsp:include flush='true' page="../general/checkBrowser.jsp"/>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<app:checkLogon/>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="Location" value="account" type="java.lang.String" 
 toScope="session"/>
<bean:define id="theForm" name="WORKFLOW_DETAIL_FORM" type="com.cleanwise.view.forms.WorkflowDetailForm"/>
<bean:define id="wid" type="java.lang.String"  name="WORKFLOW_DETAIL_FORM" property="id"/>
<%
  String action = request.getParameter("action");
  String formRuleAction = theForm.getRuleAction();
	String isMSIE = (String)session.getAttribute("IsMSIE");
	if (null == isMSIE) isMSIE = "";
%>

<html:html>

<head>

<script language="JavaScript1.2">

function popLocate(pLoc, name, pDesc, pAccountId) {
  var loc = pLoc + ".do?feedField=" + name + "&amp;feedDesc=" + pDesc + "&amp;searchAccountId=" + pAccountId + "&amp;action=View+All";
  locatewin = window.open(loc,"Locate", "menubar=yes,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
  locatewin.focus();
  
  return false;
}

function f_hideBox(boxid) {
  obj = document.getElementById(boxid);
  if(obj != null) {
    document.getElementById(boxid).style.display = 'none';
  }
}

function f_hideAll() {
  /* Reset the radio button for rule type selection. */
  WORKFLOW_DETAIL_FORM.ruleTypeCd.value="";
  f_hideBox("BOX_userlocate");
  f_hideBox("BOX_agerule");
}

function f_showBox(boxid) {
  document.getElementById(boxid).style.display = 'block';  
}

function f_userbox1(actionVal) {
  switch (actionVal) {
  case "Forward for approval":
    f_hideBox("BOX_userlocate");
    f_showBox("BOX_agerule");
  break;
  case "Hold order for review":
    f_hideBox("BOX_userlocate");
    f_showBox("BOX_agerule");
  break;
  case "Send Email":
    f_showBox("BOX_userlocate");
    f_hideBox("BOX_agerule");
  break;
  default:
    f_hideBox("BOX_userlocate");
    f_hideBox("BOX_agerule");
  }
}

function f_userbox2(actionVal) {
  switch (actionVal) {
  case "Forward for approval":
    f_hideBox("BOX_userlocate");
    f_showBox("BOX_agerule");
  break;
  case "Hold order for review":
    f_hideBox("BOX_userlocate");
    f_showBox("BOX_agerule");
  break;
  case "Send Email":
    f_showBox("BOX_userlocate");
    f_hideBox("BOX_agerule");
  break;
  default:
    f_hideBox("BOX_userlocate");
    f_hideBox("BOX_agerule");
  }
}


function f_showBreakPointFields() {  
  window.location='accountWorkflowDetail.do?action=BreakPoint&workflowId=<%=wid%>&ruleSeq=0';
}

function f_showOrderTotalFields() {  
  window.location='accountWorkflowDetail.do?action=OrderTotal&workflowId=<%=wid%>&ruleSeq=0';
}

function f_showYTDBudgetRemaining() {  
  window.location='accountWorkflowDetail.do?action=BudgetYTD&workflowId=<%=wid%>&ruleSeq=0';
}

function f_showBudgetRemainingPerCCFields() {  
  window.location='accountWorkflowDetail.do?action=CostCenterBudget&workflowId=<%=wid%>&ruleSeq=0';
}

function f_showOrderVelocityFields() {  
  window.location='accountWorkflowDetail.do?action=OrderVelocity&workflowId=<%=wid%>&ruleSeq=0';
}

function f_showOrderSkuFields() {  
  window.location='accountWorkflowDetail.do?action=OrderSku&workflowId=<%=wid%>&ruleSeq=0';
}

function f_showOrderSkuQtyFields() {  
  window.location='accountWorkflowDetail.do?action=OrderSkuQty&workflowId=<%=wid%>&ruleSeq=0';
}

function f_showDistShiptoFields() {  
  window.location='accountWorkflowDetail.do?action=DistShiptoState&workflowId=<%=wid%>&ruleSeq=0';
}

function f_showNonOrderGuideItemFields() {  
  window.location='accountWorkflowDetail.do?action=NonOrderGuideItem&workflowId=<%=wid%>&ruleSeq=0';
}
function f_showEveryOrderFields() {
  window.location='accountWorkflowDetail.do?action=EveryOrder&workflowId=<%=wid%>&ruleSeq=0';
}
</script>

<link rel="stylesheet" href="../externals/styles.css">
<title>Application Administrator Home: Account Workflow</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<% if ("Y".equals(isMSIE)) { %>
<script language="JavaScript" src="../externals/calendar.js"></script>
<% } else {  %>
<script language="JavaScript" src="../externals/calendarNS.js"></script>
<% }  %>

<%if(RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_TOTAL.equals(action) ||
     RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_VELOCITY.equals(action) ||     
     RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_SKU.equals(action) ||     
     RefCodeNames.WORKFLOW_RULE_TYPE_CD.BUDGET_REMAINING_PER_CC.equals(action) ||     
     RefCodeNames.WORKFLOW_RULE_TYPE_CD.BUDGET_YTD.equals(action) ||     
     RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_SKU_QTY.equals(action) ||     
     RefCodeNames.WORKFLOW_RULE_TYPE_CD.EVERY_ORDER.equals(action) ||
     RefCodeNames.WORKFLOW_RULE_TYPE_CD.NON_ORDER_GUIDE_ITEM.equals(action))
{  %>
 <body onload='f_userbox1("<%=formRuleAction%>")'>
<% } else { %>
 <body onload='f_hideAll()'>
<% } %>
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


<html:form action="adminportal/accountWorkflowDetail.do"
  name="WORKFLOW_DETAIL_FORM"
  scope="session" type="com.cleanwise.view.forms.WorkflowDetailForm">
<html:hidden name="WORKFLOW_DETAIL_FORM" property="id"/>

<bean:define id="aid" type="java.lang.String"
  name="Account.id"/>

<html:hidden name="WORKFLOW_DETAIL_FORM" property="busEntityId"
  value="<%= aid %>" /> 

<jsp:include flush='true' page="accountWorkflowGeneral.jsp"/>

<br>
<%-- Rules for the workflow. --%>
<%  GroupDataVector groupDV =   (GroupDataVector) session.getAttribute("UserGroups"); %>

<table width="769" cellspacing=0 >
<logic:present name="Workflow.rulesjoin.vector">
<bean:size id="rescount"  name="Workflow.rulesjoin.vector"/>
  <tr>
    <th>Rule</th>
    <th>Group</th>
    <th colspan=3>Expression</td>
    <th>Action</th>
    <th>Apply/Skip Rule</th>
    <th>Approver Group</th>
    <th>Email Group</th>
  </tr>
<logic:greaterThan name="rescount" value="0">
<define:bean id="idx" type="java.lang.Integer" value="0"/>
<% 
  int ruleSeqPrev = 0; 
  int ruleCnt = 0;
  boolean nextRuleFl = false;
%>
<logic:iterate id="rulejoin" indexId="idx1" name="Workflow.rulesjoin.vector" type="WorkflowRuleJoinView">
<bean:define id="rules" type="WorkflowRuleDataVector"  name="rulejoin" property="rules"/>
<bean:define id="associations" type="WorkflowAssocDataVector"  name="rulejoin" property="associations"/>

<logic:iterate id="arrele" indexId="idx" name="rules" type="WorkflowRuleData">
<bean:define id="wrid" type="Integer"  name="arrele" property="workflowRuleId"/>
  <% 
  String ruleTypeCd = arrele.getRuleTypeCd();
  int ruleSeq = arrele.getRuleSeq();
  if (ruleSeq!=ruleSeqPrev ) {
    ruleSeqPrev = ruleSeq;
    nextRuleFl = true;
    ruleCnt++;
  } else {
    nextRuleFl = false;
  }
  if(RefCodeNames.WORKFLOW_RULE_TYPE_CD.BREAK_POINT.equals(ruleTypeCd)) {
  %>
  <tr class="textreadonly" align=center>
  <%
  } else if(ruleCnt%2!=0) {
  %>  
  <tr class="rowa" align=center>
  <% } else { %>
  <tr class="rowb" align=center>
  <% } %>
    <td>                    
<%-- 
Show the order or evaluation, this may not be the
same as the sequence number stored in the database.
--%>                         
    <%= ruleSeq %>
</td>
    
    <td><% if(nextRuleFl) { %><bean:write name="arrele" property="ruleGroup"/><% } else {%>&nbsp;<% } %></td>
    
    <td><% if(nextRuleFl) { %><%=ruleTypeCd%><% } else {%>&nbsp;<% } %></td>
    <td><bean:write name="arrele" property="ruleExp"/></td>
    <td><bean:write name="arrele" property="ruleExpValue"/></td>
    <% 
     String ruleAction = arrele.getRuleAction();
     if(ruleAction==null) ruleAction = "";
    %>
    <td><% if(nextRuleFl) { %>
      <%=ruleAction%>
      <% if(ruleAction.indexOf("Forward")==0) { %>
        <br>(<bean:write name="arrele" property="nextActionCd"/>)
      <% } %>
      <% if(ruleAction.indexOf("Send")==0) { %>
        <br>(<bean:write name="arrele" property="nextActionCd"/>)
      <% } %>
      <% if(ruleAction.indexOf("Hold")==0) { %>
        <br>(<bean:write name="arrele" property="nextActionCd"/>)
      <% } %>
      <% } else {%>&nbsp;<% } %>
    </td>
    <td>
    <% 
    if(associations!=null && associations.size()>0 && nextRuleFl ) {
       for(int ii=0; ii<associations.size(); ii++) {
        WorkflowAssocData waD = (WorkflowAssocData) associations.get(ii);
        String pref = 
            (RefCodeNames.WORKFLOW_ASSOC_CD.APPLY_FOR_GROUP_USERS.equals(waD.getWorkflowAssocCd()))?
              "Apply for: ":"Skip for: ";
        int applyGroupId = waD.getGroupId();
        String applyGroupName = " Group id "+applyGroupId;
        for(Iterator iter=groupDV.iterator(); iter.hasNext();) {
         GroupData gD = (GroupData) iter.next();
         if(gD.getGroupId()==applyGroupId) {
            applyGroupName = gD.getShortDesc();
            break;
         }
       } 
        
        if(ii>0)  { 
   %>
     <br> 
   <% } %>
     <%=pref%><%=applyGroupName%>
   <% } %>
    <% } else { %>&nbsp;<% } %>

    </td>
   </logic:notPresent>     
    <td><% if(nextRuleFl) { %>
    <% 
     int approverGroupId = arrele.getApproverGroupId();
     String approverGroupName = " - ";
     if(approverGroupId>0 && groupDV!=null) {
       for(Iterator iter=groupDV.iterator(); iter.hasNext();) {
         GroupData gD = (GroupData) iter.next();
         if(gD.getGroupId()==approverGroupId) {
            approverGroupName = gD.getShortDesc();
            break;
         }
       } 
     }
    %> 
    <%=approverGroupName%> 
    <% } else {%>&nbsp;<% } %>
    </td>
    <td><% if(nextRuleFl) { %>
    <% 
     int emailGroupId = arrele.getEmailGroupId();
     String emailGroupName = " - ";
     if(emailGroupId>0 && groupDV!=null) {
       for(Iterator iter=groupDV.iterator(); iter.hasNext();) {
         GroupData gD = (GroupData) iter.next();
         if(gD.getGroupId()==emailGroupId) {
            emailGroupName = gD.getShortDesc();
            break;
         }
       } 
     }
    %> 
    <%=emailGroupName%> 
    <% } else {%>&nbsp;<% } %>
    </td>
    <%
    String rmurl = "accountWorkflowDetail.do?action=rm_rule&amp;" +
      "workflowRuleId=" + wrid + "&amp;workflowId=" + wid;
    String upurl = "accountWorkflowDetail.do?action=mv_rule_up&amp;" +
      "workflowRuleId=" + wrid + "&amp;workflowId=" + wid;
    String downurl = "accountWorkflowDetail.do?action=mv_rule_down&amp;" +
      "workflowRuleId=" + wrid + "&amp;workflowId=" + wid;
    String editUrl = "accountWorkflowDetail.do?action="+ruleTypeCd+
      "&workflowId=" + wid+"&ruleSeq="+ruleSeq;
    %>
    <td>
    <% if(nextRuleFl) { %>
    <a style="tbar" href="<%=upurl%>"><img border="0" alt="[Move up]" src="../<%=ip%>images/button_point_up.gif"</a><a style="tbar" href="<%=downurl%>"><img border="0" alt="[Move down]" src="../<%=ip%>images/button_point_down.gif"</a><a style="tbar" href="<%=rmurl%>" ><img border="0" alt="[Remove]" src="../<%=ip%>images/button_x.gif"</a>
    <a style="tbar" href="<%=editUrl%>">[Edit]</a>
    <% } else { %>
     &nbsp;
    <% } %>
    </td>
  </tr>
</logic:iterate>
</logic:iterate>
</logic:greaterThan>
</logic:present>

</table><%-- Rules for the workflow. --%>

<% if (!wid.equals("0")) { %>
<table width="769"><%-- Define a new rule --%>
<tr>
<td class="mainbody" colspan="2">
<b>Define a rule:</b>
<html:radio name="WORKFLOW_DETAIL_FORM" property="ruleTypeCd"
  onclick="f_showBreakPointFields();"
  value="<%=RefCodeNames.WORKFLOW_RULE_TYPE_CD.BREAK_POINT%>">
Break&nbsp;Workflow&nbsp;Rule
</html:radio>

<html:radio name="WORKFLOW_DETAIL_FORM" property="ruleTypeCd"
  onclick="f_showOrderTotalFields();"
  value="<%=RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_TOTAL%>">
Order&nbsp;Total&nbsp;Rule
</html:radio>

<html:radio name="WORKFLOW_DETAIL_FORM" property="ruleTypeCd"
  onclick="f_showOrderVelocityFields();"
  value="<%=RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_VELOCITY%>">
Order&nbsp;Velocity&nbsp;Rule
</html:radio>

<html:radio name="WORKFLOW_DETAIL_FORM" property="ruleTypeCd"
  onclick="f_showOrderSkuFields();"
  value="<%=RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_SKU%>">
Order&nbsp;Sku&nbsp;Rule
</html:radio>

<html:radio name="WORKFLOW_DETAIL_FORM" property="ruleTypeCd"
  onclick="f_showBudgetRemainingPerCCFields();"
  value="<%=RefCodeNames.WORKFLOW_RULE_TYPE_CD.BUDGET_REMAINING_PER_CC%>">
Cost&nbsp;Center&nbsp;Budget
</html:radio>

<html:radio name="WORKFLOW_DETAIL_FORM" property="ruleTypeCd"
  onclick="f_showYTDBudgetRemaining();"
  value="<%=RefCodeNames.WORKFLOW_RULE_TYPE_CD.BUDGET_YTD%>">
Budget&nbsp;YTD&nbsp;Spending
</html:radio>

<html:radio name="WORKFLOW_DETAIL_FORM" property="ruleTypeCd"
  onclick="f_showOrderSkuQtyFields();"
  value="<%=RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_SKU_QTY%>">
Order&nbsp;Sku&nbsp;Quantity
</html:radio>

<html:radio name="WORKFLOW_DETAIL_FORM" property="ruleTypeCd"
  onclick="f_showNonOrderGuideItemFields();"
  value="<%=RefCodeNames.WORKFLOW_RULE_TYPE_CD.NON_ORDER_GUIDE_ITEM%>">
Items&nbsp;Not&nbsp;On&nbsp;Template&nbsp;Orderguide
</html:radio>

<html:radio name="WORKFLOW_DETAIL_FORM" property="ruleTypeCd"
  onclick="f_showEveryOrderFields();"
  value="<%=RefCodeNames.WORKFLOW_RULE_TYPE_CD.EVERY_ORDER%>">
Every&nbsp;Order&nbsp;Rule
</html:radio>
</td>
</tr>

<jsp:include flush='true' page="accountWorkflowRule.jsp"/>


</table> <%-- Define a new rule --%>
<% } %>
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






