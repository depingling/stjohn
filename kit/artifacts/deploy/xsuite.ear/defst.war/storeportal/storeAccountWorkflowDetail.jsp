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
<bean:define id="theForm" name="STORE_WORKFLOW_DETAIL_FORM" type="com.cleanwise.view.forms.StoreWorkflowDetailForm"/>
<bean:define id="wid" type="java.lang.String"  name="STORE_WORKFLOW_DETAIL_FORM" property="id"/>

<%
    String action = request.getParameter("action");
    String lastRuleAction=theForm.getLastRuleAction();
    String formRuleAction = theForm.getRuleAction();
    boolean isLocateAction=theForm.isLocateFormAction(action);

    String isMSIE = (String)session.getAttribute("IsMSIE");
    if (null == isMSIE) isMSIE = "";
%>

<html:html>

<head>

<script language="JavaScript1.2">



function f_hideBox(boxid) {
    obj = document.getElementById(boxid);
    if(obj != null) {
        document.getElementById(boxid).style.display = 'none';
    }

}
function set_check_rule(a,la,fl)
{
    var ch_el;
    if(fl) {
        ch_el=document.getElementById(la);
    }
    else{
        ch_el=document.getElementById(a);
    }

    if(ch_el!=null)
    {

        f_check_rule(ch_el);
    }
}
function f_check_rule(elem)
{
    elem.checked=true;
}

function f_hideAll() {
    /* Reset the radio button for rule type selection. */
    /*document.forms['MainForm'].ruleTypeCd.value="";*/
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
    window.location='storeAccountWorkflowDetail.do?action=BreakPoint&workflowId=<%=wid%>&ruleSeq=0';
}

function f_showOrderTotalFields() {
    window.location='storeAccountWorkflowDetail.do?action=OrderTotal&workflowId=<%=wid%>&ruleSeq=0';
}

function f_showYTDBudgetRemaining() {
    window.location='storeAccountWorkflowDetail.do?action=BudgetYTD&workflowId=<%=wid%>&ruleSeq=0';
}

function f_showBudgetRemainingPerCCFields() {
    window.location='storeAccountWorkflowDetail.do?action=CostCenterBudget&workflowId=<%=wid%>&ruleSeq=0';
}

function f_showOrderVelocityFields() {
    window.location='storeAccountWorkflowDetail.do?action=OrderVelocity&workflowId=<%=wid%>&ruleSeq=0';
}

function f_showOrderSkuFields() {
    window.location='storeAccountWorkflowDetail.do?action=OrderSku&workflowId=<%=wid%>&ruleSeq=0';
}

function f_showOrderSkuQtyFields() {
    window.location='storeAccountWorkflowDetail.do?action=OrderSkuQty&workflowId=<%=wid%>&ruleSeq=0';
}

function f_showDistShiptoFields() {
    window.location='storeAccountWorkflowDetail.do?action=DistShiptoState&workflowId=<%=wid%>&ruleSeq=0';
}

function f_showNonOrderGuideItemFields() {
    window.location='storeAccountWorkflowDetail.do?action=NonOrderGuideItem&workflowId=<%=wid%>&ruleSeq=0';
}
function f_showEveryOrderFields() {
    window.location='storeAccountWorkflowDetail.do?action=EveryOrder&workflowId=<%=wid%>&ruleSeq=0';
}

function f_showFreightHandlerFields() {
    window.location = 'storeAccountWorkflowDetail.do?action=<%=RefCodeNames.WORKFLOW_RULE_TYPE_CD.FREIGHT_HANDLER%>&workflowId=<%=wid%>&ruleSeq=0';
}

function f_showRushOrderFields() {
    window.location = 'storeAccountWorkflowDetail.do?action=<%=RefCodeNames.WORKFLOW_RULE_TYPE_CD.RUSH_ORDER%>&workflowId=<%=wid%>&ruleSeq=0';
}
function f_showItemCategoryFields() {
    window.location = 'storeAccountWorkflowDetail.do?action=<%=RefCodeNames.WORKFLOW_RULE_TYPE_CD.ITEM_CATEGORY%>&workflowId=<%=wid%>&ruleSeq=0';
}
function f_showItemPriceFields() {
    window.location = 'storeAccountWorkflowDetail.do?action=<%=RefCodeNames.WORKFLOW_RULE_TYPE_CD.ITEM_PRICE%>&workflowId=<%=wid%>&ruleSeq=0';
}
function f_showWorkOrderBudgetFields() {
    window.location = 'storeAccountWorkflowDetail.do?action=<%=RefCodeNames.WORKFLOW_RULE_TYPE_CD.WORK_ORDER_BUDGET%>&workflowId=<%=wid%>&ruleSeq=0';
}
function f_showWorkOrderBudgetSpendingForCostCenterFields() {
    window.location = 'storeAccountWorkflowDetail.do?action=<%=RefCodeNames.WORKFLOW_RULE_TYPE_CD.WORK_ORDER_BUDGET_SPENDING_FOR_COST_CENTER%>&workflowId=<%=wid%>&ruleSeq=0';
}

function f_showWorkOrderTotalFields() {
    window.location = 'storeAccountWorkflowDetail.do?action=<%=RefCodeNames.WORKFLOW_RULE_TYPE_CD.WORK_ORDER_TOTAL%>&workflowId=<%=wid%>&ruleSeq=0';
}
function f_showUserLimitFields() {
    window.location = 'storeAccountWorkflowDetail.do?action=<%= RefCodeNames.WORKFLOW_RULE_TYPE_CD.USER_LIMIT%>&workflowId=<%=wid%>&ruleSeq=0';
}
function f_showOrderBudgetFields() {
	window.location = 'storeAccountWorkflowDetail.do?action=<%= RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_BUDGET_PERIOD_CHANGED%>&workflowId=<%=wid%>&ruleSeq=0';
}
function f_showWorkOrderAnyFields() {
    window.location = 'storeAccountWorkflowDetail.do?action=<%=RefCodeNames.WORKFLOW_RULE_TYPE_CD.WORK_ORDER_ANY%>&workflowId=<%=wid%>&ruleSeq=0';
}
function f_showWorkOrderExcludeOrderFromBudget() {
    window.location = 'storeAccountWorkflowDetail.do?action=<%=RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_EXCLUDED_FROM_BUDGET%>&workflowId=<%=wid%>&ruleSeq=0';
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

<%
if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_TOTAL.equals(action) ||
        RefCodeNames.WORKFLOW_RULE_TYPE_CD.BREAK_POINT.equals(action) ||
        RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_VELOCITY.equals(action) ||
        RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_SKU.equals(action) ||
        RefCodeNames.WORKFLOW_RULE_TYPE_CD.BUDGET_REMAINING_PER_CC.equals(action) ||
        RefCodeNames.WORKFLOW_RULE_TYPE_CD.BUDGET_YTD.equals(action) ||
        RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_SKU_QTY.equals(action) ||
        RefCodeNames.WORKFLOW_RULE_TYPE_CD.EVERY_ORDER.equals(action) ||
        RefCodeNames.WORKFLOW_RULE_TYPE_CD.NON_ORDER_GUIDE_ITEM.equals(action) ||
        RefCodeNames.WORKFLOW_RULE_TYPE_CD.FREIGHT_HANDLER.equals(action) ||
        RefCodeNames.WORKFLOW_RULE_TYPE_CD.ITEM_CATEGORY.equals(action) ||
		RefCodeNames.WORKFLOW_RULE_TYPE_CD.ITEM_PRICE.equals(action) ||
        RefCodeNames.WORKFLOW_RULE_TYPE_CD.RUSH_ORDER.equals(action) ||
        RefCodeNames.WORKFLOW_RULE_TYPE_CD.WORK_ORDER_BUDGET.equals(action) ||
        RefCodeNames.WORKFLOW_RULE_TYPE_CD.USER_LIMIT.equals(action) ||
        RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_BUDGET_PERIOD_CHANGED.equals(action) ||
        RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_EXCLUDED_FROM_BUDGET.equals(action) ||
        isLocateAction) {
%>
<body onLoad="f_userbox1('<%=formRuleAction%>'); set_check_rule('<%=action%>','<%=lastRuleAction%>','<%=isLocateAction%>');">
<% } else if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.WORK_ORDER_ANY.equals(action)) { %>
<body onload="f_hideBox('BOX_userlocate'); f_hideBox('showAddRule'); f_showBox('BOX_agerule'); set_check_rule('<%=action%>','<%=lastRuleAction%>','<%=isLocateAction%>');">
<% } else if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.WORK_ORDER_TOTAL.equals(action) ||
              RefCodeNames.WORKFLOW_RULE_TYPE_CD.WORK_ORDER_BUDGET_SPENDING_FOR_COST_CENTER.equals(action)) { %>
<body onload="f_userbox1('<%=formRuleAction%>'); f_hideBox('showAddRule'); set_check_rule('<%=action%>','<%=lastRuleAction%>','<%=isLocateAction%>');">
<% } else { %>
<body onload='f_hideAll()'>
<% } %>

 <div class="text">
<% if ("Y".equals(isMSIE)) { %>
<iframe style="display:none; position:absolute; z-index:1; width:148; height:194"
        id="CalFrame"
        marginheight=0 marginwidth=0 noresize frameborder=0 scrolling=no
        src="../externals/calendar.html">
</iframe>
<% }  %>

  <table ID=501 width="<%=Constants.TABLEWIDTH%>" border="0" cellspacing="0" cellpadding="0">
        <html:form styleId="MainForm" action="storeportal/storeAccountWorkflowDetail.do"
                   name="STORE_WORKFLOW_DETAIL_FORM"
                   scope="session" type="com.cleanwise.view.forms.StoreWorkflowDetailForm">
        <html:hidden name="STORE_WORKFLOW_DETAIL_FORM" property="id"/>

        <bean:define id="aid" type="java.lang.String"
                     name="Account.id"/>

        <html:hidden name="STORE_WORKFLOW_DETAIL_FORM" property="busEntityId"
                     value="<%= aid %>" /> <tr>
      <td><jsp:include flush='true' page="storeAccountWorkflowGeneral.jsp"/> </td>
    </tr>
    <tr>
      <td> <br>
            <%-- Rules for the workflow. --%>
        <%  GroupDataVector groupDV =   (GroupDataVector) session.getAttribute("UserGroups"); %>

        <table ID=502 width="<%=Constants.TABLEWIDTH%>" cellspacing=0 >
            <logic:present name="Workflow.rulesjoin.vector">
                <bean:size id="rescount"  name="Workflow.rulesjoin.vector"/>
            <tr>
                <th>Rule</th>
                <th>Group</th>
            <th colspan=3>Expression </th>
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

<%if(RefCodeNames.WORKFLOW_RULE_TYPE_CD.USER_LIMIT.equals(ruleTypeCd)) {  %>
<td><% if(nextRuleFl) { %><%="OrderTotal"%><% } else {%>&nbsp;<% } %></td>
<td>>= <bean:write name="arrele" property="ruleExpValue"/></td>
<td>within <bean:write name="arrele" property="ruleExp"/> day(s)</td>
<%} else {%>
<td><% if(nextRuleFl) { %><%=ruleTypeCd%><% } else {%>&nbsp;<% } %></td>
<td><bean:write name="arrele" property="ruleExp"/></td>
<td><bean:write name="arrele" property="ruleExpValue"/></td>
<%}%>

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
    String rmurl = "storeAccountWorkflowDetail.do?action=rm_rule&amp;" +
            "workflowRuleId=" + wrid + "&amp;workflowId=" + wid;
    String upurl = "storeAccountWorkflowDetail.do?action=mv_rule_up&amp;" +
            "workflowRuleId=" + wrid + "&amp;workflowId=" + wid;
    String downurl = "storeAccountWorkflowDetail.do?action=mv_rule_down&amp;" +
            "workflowRuleId=" + wrid + "&amp;workflowId=" + wid;
    String editUrl = "storeAccountWorkflowDetail.do?action="+ruleTypeCd+
            "&workflowId=" + wid+"&ruleSeq="+ruleSeq;
%>
<td>
    <% if(nextRuleFl) { %>
    <a ID=503 style="tbar" href="<%=upurl%>"><img ID=504 border="0" alt="[Move up]" src="../<%=ip%>images/button_point_up.gif"></a><a ID=505 style="tbar" href="<%=downurl%>"><img ID=506 border="0" alt="[Move down]" src="../<%=ip%>images/button_point_down.gif"></a><a ID=507 style="tbar" href="<%=rmurl%>" ><img ID=508 border="0" alt="[Remove]" src="../<%=ip%>images/button_x.gif"></a>
    <a ID=509 style="tbar" href="<%=editUrl%>">[Edit]</a>
    <% } else { %>
    &nbsp;
    <% } %>
</td>
</tr>
</logic:iterate>
</logic:iterate>
</logic:greaterThan>
</logic:present>
        </table>
      </td>
    </tr>
    <tr>
      <td><% if (!wid.equals("0")) { %>
<table ID=510 width="<%=Constants.TABLEWIDTH%>"><%-- Define a new rule --%>
    <tr>
        <td class="mainbody" colspan="2">
            <b>Define a rule:</b>

            <logic:notEqual name="STORE_WORKFLOW_DETAIL_FORM" property="typeCd" value="<%=RefCodeNames.WORKFLOW_TYPE_CD.WORK_ORDER_WORKFLOW%>">
            
            <html:radio styleId= "<%=RefCodeNames.WORKFLOW_RULE_TYPE_CD.BREAK_POINT%>" name="STORE_WORKFLOW_DETAIL_FORM" property="ruleTypeCd"
                        onclick="f_showBreakPointFields();"
                        value="<%=RefCodeNames.WORKFLOW_RULE_TYPE_CD.BREAK_POINT%>">
                Break&nbsp;Workflow&nbsp;Rule
            </html:radio>

            <html:radio styleId= "<%=RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_TOTAL%>"  name="STORE_WORKFLOW_DETAIL_FORM" property="ruleTypeCd"
                        onclick="f_showOrderTotalFields();"
                        value="<%=RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_TOTAL%>">
                Order&nbsp;Total&nbsp;Rule
            </html:radio>

            <html:radio  styleId= "<%=RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_VELOCITY%>" name="STORE_WORKFLOW_DETAIL_FORM" property="ruleTypeCd"
                         onclick="f_showOrderVelocityFields();"
                         value="<%=RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_VELOCITY%>">
                Order&nbsp;Velocity&nbsp;Rule
            </html:radio>

            <html:radio styleId= "<%=RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_SKU%>" name="STORE_WORKFLOW_DETAIL_FORM" property="ruleTypeCd"
                        onclick="f_showOrderSkuFields();"
                        value="<%=RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_SKU%>">
                Order&nbsp;Sku&nbsp;Rule
            </html:radio>

            <html:radio styleId= "<%=RefCodeNames.WORKFLOW_RULE_TYPE_CD.BUDGET_REMAINING_PER_CC%>" name="STORE_WORKFLOW_DETAIL_FORM" property="ruleTypeCd"
                        onclick="f_showBudgetRemainingPerCCFields();"
                        value="<%=RefCodeNames.WORKFLOW_RULE_TYPE_CD.BUDGET_REMAINING_PER_CC%>">
                Cost&nbsp;Center&nbsp;Budget
            </html:radio>

            <html:radio styleId= "<%=RefCodeNames.WORKFLOW_RULE_TYPE_CD.BUDGET_YTD%>" name="STORE_WORKFLOW_DETAIL_FORM" property="ruleTypeCd"
                        onclick="f_showYTDBudgetRemaining();"
                        value="<%=RefCodeNames.WORKFLOW_RULE_TYPE_CD.BUDGET_YTD%>">
                Budget&nbsp;YTD&nbsp;Spending
            </html:radio>

            <html:radio styleId= "<%=RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_SKU_QTY%>"  name="STORE_WORKFLOW_DETAIL_FORM" property="ruleTypeCd"
                        onclick="f_showOrderSkuQtyFields();"
                        value="<%=RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_SKU_QTY%>">
                Order&nbsp;Sku&nbsp;Quantity
            </html:radio>

            <html:radio styleId= "<%=RefCodeNames.WORKFLOW_RULE_TYPE_CD.NON_ORDER_GUIDE_ITEM%>"  name="STORE_WORKFLOW_DETAIL_FORM" property="ruleTypeCd"
                        onclick="f_showNonOrderGuideItemFields();"
                        value="<%=RefCodeNames.WORKFLOW_RULE_TYPE_CD.NON_ORDER_GUIDE_ITEM%>">
                Items&nbsp;Not&nbsp;On&nbsp;Template&nbsp;Orderguide
            </html:radio>

            <html:radio styleId= "<%=RefCodeNames.WORKFLOW_RULE_TYPE_CD.EVERY_ORDER%>"  name="STORE_WORKFLOW_DETAIL_FORM" property="ruleTypeCd"
                        onclick="f_showEveryOrderFields();"
                        value="<%=RefCodeNames.WORKFLOW_RULE_TYPE_CD.EVERY_ORDER%>">
                Every&nbsp;Order&nbsp;Rule
            </html:radio>

            <html:radio styleId= "<%=RefCodeNames.WORKFLOW_RULE_TYPE_CD.FREIGHT_HANDLER%>"  name="STORE_WORKFLOW_DETAIL_FORM" property="ruleTypeCd"
                        onclick="f_showFreightHandlerFields();"
                        value="<%=RefCodeNames.WORKFLOW_RULE_TYPE_CD.FREIGHT_HANDLER%>">
                Freight&nbsp;Handler&nbsp;Rule
            </html:radio>

            <html:radio styleId="<%=RefCodeNames.WORKFLOW_RULE_TYPE_CD.RUSH_ORDER%>"
                        name="STORE_WORKFLOW_DETAIL_FORM" property="ruleTypeCd"
                        onclick="f_showRushOrderFields();"
                        value="<%=RefCodeNames.WORKFLOW_RULE_TYPE_CD.RUSH_ORDER%>">
                Rush&nbsp;Order&nbsp;Rule
            </html:radio>

            <html:radio styleId="<%=RefCodeNames.WORKFLOW_RULE_TYPE_CD.ITEM_CATEGORY%>"
                        name="STORE_WORKFLOW_DETAIL_FORM" property="ruleTypeCd"
                        onclick="f_showItemCategoryFields();"
                        value="<%=RefCodeNames.WORKFLOW_RULE_TYPE_CD.ITEM_CATEGORY%>">
                Item&nbsp;Category&nbsp;Rule
            </html:radio>
			
			<html:radio styleId="<%=RefCodeNames.WORKFLOW_RULE_TYPE_CD.ITEM_PRICE%>"
                        name="STORE_WORKFLOW_DETAIL_FORM" property="ruleTypeCd"
                        onclick="f_showItemPriceFields();"
                        value="<%=RefCodeNames.WORKFLOW_RULE_TYPE_CD.ITEM_PRICE%>">
                Item&nbsp;Price&nbsp;Rule (not line total)
            </html:radio>

            <html:radio styleId="<%=RefCodeNames.WORKFLOW_RULE_TYPE_CD.USER_LIMIT%>"
                         name="STORE_WORKFLOW_DETAIL_FORM" property="ruleTypeCd"
                         onclick="f_showUserLimitFields();"
                         value="<%=RefCodeNames.WORKFLOW_RULE_TYPE_CD.USER_LIMIT%>">
                    User&nbsp;Limit&nbsp;Rule
             </html:radio>
             <nobr><html:radio styleId="<%=RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_BUDGET_PERIOD_CHANGED%>"
                         name="STORE_WORKFLOW_DETAIL_FORM" property="ruleTypeCd"
                         onclick="f_showOrderBudgetFields();"
                         value="<%=RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_BUDGET_PERIOD_CHANGED%>">Order&nbsp;Budget&nbsp;Period&nbsp;Changed
             </html:radio>
			<!-- Begin: Order Exclude From Budget -->
            <html:radio styleId="<%=RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_EXCLUDED_FROM_BUDGET%>"
                        name="STORE_WORKFLOW_DETAIL_FORM" property="ruleTypeCd"
                        onclick="f_showWorkOrderExcludeOrderFromBudget();"
                        value="<%=RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_EXCLUDED_FROM_BUDGET%>">
                Order&nbsp;Excluded&nbsp;from&nbsp;Budget
            </html:radio>
            <!-- End: Order Exclude From Budget -->
			 
			 </nobr>

             </logic:notEqual>

            <logic:equal name="STORE_WORKFLOW_DETAIL_FORM" property="typeCd" value="<%=RefCodeNames.WORKFLOW_TYPE_CD.WORK_ORDER_WORKFLOW%>">
            <!--
            <html:radio styleId="<%=RefCodeNames.WORKFLOW_RULE_TYPE_CD.WORK_ORDER_BUDGET%>"
                        name="STORE_WORKFLOW_DETAIL_FORM" property="ruleTypeCd"
                        onclick="f_showWorkOrderBudgetFields();"
                        value="<%=RefCodeNames.WORKFLOW_RULE_TYPE_CD.WORK_ORDER_BUDGET%>">
                Work&nbsp;Order&nbsp;Budget&nbsp;Rule
            </html:radio>
            -->      
            <html:radio styleId="<%=RefCodeNames.WORKFLOW_RULE_TYPE_CD.WORK_ORDER_BUDGET_SPENDING_FOR_COST_CENTER%>"
                        name="STORE_WORKFLOW_DETAIL_FORM" property="ruleTypeCd"
                        onclick="f_showWorkOrderBudgetSpendingForCostCenterFields();"
                        value="<%=RefCodeNames.WORKFLOW_RULE_TYPE_CD.WORK_ORDER_BUDGET_SPENDING_FOR_COST_CENTER%>">
                Budget&nbsp;Spending&nbsp;for&nbsp;Cost&nbsp;Center
            </html:radio>
            <html:radio styleId="<%=RefCodeNames.WORKFLOW_RULE_TYPE_CD.WORK_ORDER_TOTAL%>"
                        name="STORE_WORKFLOW_DETAIL_FORM" property="ruleTypeCd"
                        onclick="f_showWorkOrderTotalFields();"
                        value="<%=RefCodeNames.WORKFLOW_RULE_TYPE_CD.WORK_ORDER_TOTAL%>">
                Work&nbsp;Order&nbsp;Total&nbsp;Rule
            </html:radio>
            <html:radio styleId="<%=RefCodeNames.WORKFLOW_RULE_TYPE_CD.WORK_ORDER_ANY%>"
                        name="STORE_WORKFLOW_DETAIL_FORM" property="ruleTypeCd"
                        onclick="f_showWorkOrderAnyFields();"
                        value="<%=RefCodeNames.WORKFLOW_RULE_TYPE_CD.WORK_ORDER_ANY%>">
                Work&nbsp;Order&nbsp;Any&nbsp;Rule
            </html:radio>
            </logic:equal>
            
        </td>
    </tr>

</table> <%-- Define a new rule --%>
<% } %></td>
    </html:form>  </tr>
    <% if(isLocateAction){   %>
    <tr>
      <td><jsp:include flush='true' page="locateStoreDistributor.jsp">
        <jsp:param name="jspFormAction" 	value="/storeportal/storeAccountWorkflowDetail.do" />
        <jsp:param name="jspFormName" 	value="STORE_WORKFLOW_DETAIL_FORM" />
        <jsp:param name="jspSubmitIdent" 	value="" />
        <jsp:param name="isSingleSelect" 	value="true" />
        <jsp:param name="jspReturnFilterPropertyAlt" value="distDummyConvert"/>
        <jsp:param name="jspReturnFilterProperty" value="distFilter"/>
        </jsp:include> <jsp:include flush='true' page="locateStoreUser.jsp">
        <jsp:param name="jspFormAction" 	value="/storeportal/storeAccountWorkflowDetail.do" />
        <jsp:param name="jspFormName" 	value="STORE_WORKFLOW_DETAIL_FORM" />
        <jsp:param name="jspSubmitIdent" 	value="" />
        <jsp:param name="isSingleSelect" 	value="true" />
        <jsp:param name="jspReturnFilterPropertyAlt" value="userDummyConvert"/>
        <jsp:param name="jspReturnFilterProperty" value="userFilter"/>
        </jsp:include></td>
    </tr>
    <%}%>
    <tr>
      <td><jsp:include flush='true' page="storeAccountWorkflowRule.jsp"/></td>
    </tr>
  </table>
</div>


<% if ("Y".equals(isMSIE)) { %>
<script for=document event="onclick()">
    <!--
    document.all.CalFrame.style.display="none";
    //-->
</script>
<% }  %>


</body>

</html:html>

