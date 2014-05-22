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

<%
  String action = request.getParameter("action");
  boolean saveRuleFl = false;
  if(RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_TOTAL.equals(action) ||
     RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_VELOCITY.equals(action) ||
     RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_SKU.equals(action) ||
     RefCodeNames.WORKFLOW_RULE_TYPE_CD.BUDGET_YTD.equals(action) || 
     RefCodeNames.WORKFLOW_RULE_TYPE_CD.BUDGET_REMAINING_PER_CC.equals(action) ||
     RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_SKU_QTY.equals(action) ||
     RefCodeNames.WORKFLOW_RULE_TYPE_CD.NON_ORDER_GUIDE_ITEM.equals(action) ||
     RefCodeNames.WORKFLOW_RULE_TYPE_CD.EVERY_ORDER.equals(action) || 
     RefCodeNames.WORKFLOW_RULE_TYPE_CD.BREAK_POINT.equals(action)) {
      saveRuleFl = true;
  }
  if(saveRuleFl) {
    com.cleanwise.view.forms.WorkflowDetailForm theForm = 
          (com.cleanwise.view.forms.WorkflowDetailForm) session.getAttribute("WORKFLOW_DETAIL_FORM");
    String ruleSeqS = ""+theForm.getRuleSeq();
%>      
  
<html:hidden name="WORKFLOW_DETAIL_FORM" property="ruleTypeCd" value="<%=action%>"/>
<html:hidden property="ruleSeqOrig" value="<%=ruleSeqS%>"/>
  
<tr class="mainbody">
<td colspan = "2">
<b>Rule Number:</b>&nbsp;<html:text name="WORKFLOW_DETAIL_FORM" property="ruleSeq" size="5"/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<b>Rule Group:</b>&nbsp;<html:text name="WORKFLOW_DETAIL_FORM" property="ruleGroup" size="5"/>
</td>
</tr>
<tr class="mainbody">
<td>
<%
  GroupDataVector groupDV =   (GroupDataVector) session.getAttribute("UserGroups");
  String formRuleAction = theForm.getRuleAction();
	String isMSIE = (String)session.getAttribute("IsMSIE");
	if (null == isMSIE) isMSIE = "";
%>

<%  
 String fwdmsg = "Forward for approval";
 String hold_for_rev = "Hold order for review";
 String emailmsg = "Send Email";
 String rejmsg = "Reject the order";
 String approvemsg = "Approve the order";
 %>
<%if(RefCodeNames.WORKFLOW_RULE_TYPE_CD.BREAK_POINT.equals(action)) { 
%>
<div id="BOX_breakPoint">
<span class="wkrule">
<html:hidden name="WORKFLOW_DETAIL_FORM" property="ruleAction" value="-" />
This rule breaks workflow if human action required by previous rules processed
</span>
</div>
<% } %>
<%if(RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_TOTAL.equals(action)) { 
%>
<div id="BOX_orderTotal">
<span class="wkrule">
If&nbsp;Order&nbsp;Total 
<html:select name="WORKFLOW_DETAIL_FORM" property="totalExp">
<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
<html:option value="==">==</html:option>
<html:option value=">">&gt;</html:option>
<html:option value=">=">&gt;=</html:option>
<html:option value="<">&lt;</html:option>
<html:option value="<=">&lt;=</html:option>
</html:select>
&nbsp;
<html:text name="WORKFLOW_DETAIL_FORM" property="totalValue"/>
<br>
Then 
&nbsp;
<%
 fwdmsg = "Forward for approval";
 hold_for_rev = "Hold order for review";
 emailmsg = "Send Email";
 rejmsg = "Reject the order";
 approvemsg = "Approve the order";
%>
<html:select name="WORKFLOW_DETAIL_FORM" property="ruleAction"
  onchange="f_userbox1(this.value);">
<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
<html:option value="<%=fwdmsg%>"><%=fwdmsg%></html:option>
<html:option value="<%=hold_for_rev%>"><%=hold_for_rev%></html:option>
<html:option value="<%=emailmsg%>"><%=emailmsg%></html:option>
<html:option value="<%=rejmsg%>"><%=rejmsg%></html:option>
<html:option value="<%=approvemsg%>"><%=approvemsg%></html:option>
</html:select>
</span>
</div>
<% } %>
<%if(RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_VELOCITY.equals(action)) { 
%>
<div id="BOX_orderVelocity">
<span class="wkrule">
<% Integer ruleSeqI = new Integer(theForm.getRuleSeq()); %>
<html:hidden name="WORKFLOW_DETAIL_FORM" property="ruleSeq" value="<%=ruleSeqI.toString()%>"/>
<html:hidden name="WORKFLOW_DETAIL_FORM" property="ruleTypeCd" value="<%=RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_VELOCITY%>"/>
If this order is less than 
<html:text name="WORKFLOW_DETAIL_FORM" property="timespanInDays"/>
 days since the last order.

<html:select name="WORKFLOW_DETAIL_FORM" property="ruleAction"
  onchange="f_userbox1(this.value);">
<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
<html:option value="<%=fwdmsg%>"><%=fwdmsg%></html:option>
<html:option value="<%=hold_for_rev%>"><%=hold_for_rev%></html:option>
<html:option value="<%=emailmsg%>"><%=emailmsg%></html:option>
<html:option value="<%=rejmsg%>"><%=rejmsg%></html:option>
<html:option value="<%=approvemsg%>"><%=approvemsg%></html:option>
</html:select>
</span>
</div>
<% } %>

<%if(RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_SKU.equals(action)) { 
%>
<div id="BOX_orderSku">
<span class="wkrule">
<% Integer ruleSeqI = new Integer(theForm.getRuleSeq()); %>
<html:hidden name="WORKFLOW_DETAIL_FORM" property="ruleSeq" value="<%=ruleSeqI.toString()%>"/>
<html:hidden name="WORKFLOW_DETAIL_FORM" property="ruleTypeCd" value="<%=RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_SKU%>"/>
If this order contains SKU 
<html:text name="WORKFLOW_DETAIL_FORM" property="orderSku"/>
 then, 

<html:select name="WORKFLOW_DETAIL_FORM" property="ruleAction"
  onchange="f_userbox1(this.value);">
<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
<html:option value="<%=fwdmsg%>"><%=fwdmsg%></html:option>
<html:option value="<%=hold_for_rev%>"><%=hold_for_rev%></html:option>
<html:option value="<%=emailmsg%>"><%=emailmsg%></html:option>
<html:option value="<%=rejmsg%>"><%=rejmsg%></html:option>
<html:option value="<%=approvemsg%>"><%=approvemsg%></html:option>
</html:select>
</span>
</div>

<% } %>
<%if(RefCodeNames.WORKFLOW_RULE_TYPE_CD.BUDGET_YTD.equals(action)) { 
%>
<div id="BOX_siteBuget">
<span class="wkrule">
<% Integer ruleSeqI = new Integer(theForm.getRuleSeq()); %>
<html:hidden name="WORKFLOW_DETAIL_FORM" property="ruleSeq" value="<%=ruleSeqI.toString()%>"/>
<html:hidden name="WORKFLOW_DETAIL_FORM" property="ruleTypeCd" value="<%=RefCodeNames.WORKFLOW_RULE_TYPE_CD.BUDGET_YTD%>"/>
If&nbsp;Budget&nbsp;Remaining
<html:select name="WORKFLOW_DETAIL_FORM" property="budgetExp">
<html:option value="==">==</html:option>
<html:option value="<">&lt;</html:option>
<html:option value="<=">&lt;=</html:option>
</html:select>
&nbsp; 
<html:text name="WORKFLOW_DETAIL_FORM" property="budgetValue"/>
<br>
Then
&nbsp; 
<html:select name="WORKFLOW_DETAIL_FORM" property="ruleAction"
  onchange="f_userbox2(this.value);">
<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
<html:option value="<%=fwdmsg%>"><%=fwdmsg%></html:option>
<html:option value="<%=hold_for_rev%>"><%=hold_for_rev%></html:option>
<html:option value="<%=emailmsg%>"><%=emailmsg%></html:option>
<html:option value="<%=rejmsg%>"><%=rejmsg%></html:option>
<html:option value="<%=approvemsg%>"><%=approvemsg%></html:option>
</html:select>
</span>
</div>

<% } %>
<%if(RefCodeNames.WORKFLOW_RULE_TYPE_CD.BUDGET_REMAINING_PER_CC.equals(action)) { 
%>
<div id="BOX_siteBuget">
<span class="wkrule">
<% Integer ruleSeqI = new Integer(theForm.getRuleSeq()); %>
<html:hidden name="WORKFLOW_DETAIL_FORM" property="ruleSeq" value="<%=ruleSeqI.toString()%>"/>
<html:hidden name="WORKFLOW_DETAIL_FORM" property="ruleTypeCd" value="<%=RefCodeNames.WORKFLOW_RULE_TYPE_CD.BUDGET_REMAINING_PER_CC%>"/>
If&nbsp;Budget&nbsp;Remaining
<html:select name="WORKFLOW_DETAIL_FORM" property="budgetExp">
<html:option value="==">==</html:option>
<html:option value="<">&lt;</html:option>
<html:option value="<=">&lt;=</html:option>
</html:select>
&nbsp; 
<html:text name="WORKFLOW_DETAIL_FORM" property="budgetValue"/>
<br>
Then
&nbsp; 
<html:select name="WORKFLOW_DETAIL_FORM" property="ruleAction"
  onchange="f_userbox2(this.value);">
<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
<html:option value="<%=fwdmsg%>"><%=fwdmsg%></html:option>
<html:option value="<%=hold_for_rev%>"><%=hold_for_rev%></html:option>
<html:option value="<%=emailmsg%>"><%=emailmsg%></html:option>
<html:option value="<%=rejmsg%>"><%=rejmsg%></html:option>
<html:option value="<%=approvemsg%>"><%=approvemsg%></html:option>
</html:select>
</span>
</div>

<% } %>
<%if(RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_SKU_QTY.equals(action)) { 
%>

<div id="BOX_orderSkuQty">
<span class="wkrule">
If combined number of SKUs 
<% Integer ruleSeqI = new Integer(theForm.getRuleSeq()); %>
<html:hidden name="WORKFLOW_DETAIL_FORM" property="ruleSeq" value="<%=ruleSeqI.toString()%>"/>
<html:hidden name="WORKFLOW_DETAIL_FORM" property="ruleTypeCd" value="<%=RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_SKU_QTY%>"/>
<html:textarea name="WORKFLOW_DETAIL_FORM" property="orderSku" rows="5" cols ="70"/>
is
<html:select name="WORKFLOW_DETAIL_FORM" property="totalExp">
 <html:option value="<%=RefCodeNames.RULE_EXPRESSION.LESS%>"><%=RefCodeNames.RULE_EXPRESSION.LESS%></html:option>
 <html:option value="<%=RefCodeNames.RULE_EXPRESSION.GREATER%>"><%=RefCodeNames.RULE_EXPRESSION.GREATER%></html:option>
</html:select>
than 
<html:text name="WORKFLOW_DETAIL_FORM" property="totalValue" size="4"/>
items, then
<html:select name="WORKFLOW_DETAIL_FORM" property="ruleAction"
  onchange="f_userbox1(this.value);">
<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
<html:option value="<%=fwdmsg%>"><%=fwdmsg%></html:option>
<html:option value="<%=hold_for_rev%>"><%=hold_for_rev%></html:option>
<html:option value="<%=emailmsg%>"><%=emailmsg%></html:option>
<html:option value="<%=rejmsg%>"><%=rejmsg%></html:option>
<html:option value="<%=approvemsg%>"><%=approvemsg%></html:option>
</html:select>
</span>
</div>
<% } %>

<%
if(RefCodeNames.WORKFLOW_RULE_TYPE_CD.NON_ORDER_GUIDE_ITEM.equals(action)) { 
%>
If item is ordered not on the template order guide then
<html:hidden name="WORKFLOW_DETAIL_FORM" property="ruleTypeCd" value="<%=RefCodeNames.WORKFLOW_RULE_TYPE_CD.NON_ORDER_GUIDE_ITEM%>"/>
<html:select name="WORKFLOW_DETAIL_FORM" property="ruleAction"
  onchange="f_userbox1(this.value);">
<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
<html:option value="<%=fwdmsg%>"><%=fwdmsg%></html:option>
<html:option value="<%=hold_for_rev%>"><%=hold_for_rev%></html:option>
<html:option value="<%=emailmsg%>"><%=emailmsg%></html:option>
<html:option value="<%=rejmsg%>"><%=rejmsg%></html:option>
<html:option value="<%=approvemsg%>"><%=approvemsg%></html:option>
</html:select>
<% } %>
<%
if(RefCodeNames.WORKFLOW_RULE_TYPE_CD.EVERY_ORDER.equals(action)) { 
%>
<div id="BOX_everyOrder">
<span class="wkrule">
<% Integer ruleSeqI = new Integer(theForm.getRuleSeq()); %>
<html:hidden name="WORKFLOW_DETAIL_FORM" property="ruleSeq" value="<%=ruleSeqI.toString()%>"/>
<html:hidden name="WORKFLOW_DETAIL_FORM" property="ruleTypeCd" value="<%=RefCodeNames.WORKFLOW_RULE_TYPE_CD.EVERY_ORDER%>"/>
For&nbsp;Every&nbsp;Order 
<html:select name="WORKFLOW_DETAIL_FORM" property="ruleAction"
  onchange="f_userbox1(this.value);">
<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
<html:option value="<%=fwdmsg%>"><%=fwdmsg%></html:option>
<html:option value="<%=hold_for_rev%>"><%=hold_for_rev%></html:option>
<html:option value="<%=emailmsg%>"><%=emailmsg%></html:option>
<html:option value="<%=rejmsg%>"><%=rejmsg%></html:option>
<html:option value="<%=approvemsg%>"><%=approvemsg%></html:option>
</html:select>
<html:text name="WORKFLOW_DETAIL_FORM" property="totalExp" size="30" maxlength="30"/>
<html:text name="WORKFLOW_DETAIL_FORM" property="totalValue" size="30" maxlength="30"/>
</div>
<% } %>

</td>

<bean:define id="accountId" type="java.lang.String" name="Account.id"/>
<%
    String onclickstr = new String("return popLocate('userlocate', 'emailUserId', 'emailUserName', '" + accountId + "');");
%>

<td width="375" class="mainbody">

<div id="BOX_userlocate" >
<span class="wkrule">
Notify: <html:text name="WORKFLOW_DETAIL_FORM" property="emailUserName"
  size="50"
  readonly="true" styleClass="mainbodylocatename"/>
<br>
User Id: <html:text name="WORKFLOW_DETAIL_FORM" property="emailUserId" size="7"/>
  <html:button onclick="<%=onclickstr%>" value="Locate User" property="action" />
</span>
</div>

<div id="BOX_agerule" >
<span class="wkrule">
<br>If the Order is not Updated in 
<html:select name="WORKFLOW_DETAIL_FORM" property="daysUntilNextAction">
<html:option value="1">1</html:option>
<html:option value="2">2</html:option>
<html:option value="3">3</html:option>
<html:option value="4">4</html:option>
<html:option value="5">5</html:option>
<html:option value="6">6</html:option>
<html:option value="7">7</html:option>
<html:option value="14">14</html:option>
<html:option value="30">30</html:option>
<html:option value="60">60</html:option>
<html:option value="90">90</html:option>
</html:select>
days<br>
Then 
<html:select name="WORKFLOW_DETAIL_FORM" property="nextActionCd">
<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
<html:option value="<%=rejmsg%>"><%=rejmsg%></html:option>
<html:option value="<%=approvemsg%>"><%=approvemsg%></html:option>
</html:select>
</span>
</div>

</td>

</tr>

  <!-- Order Placed User Groups -->
   <% 
    if(groupDV!=null && groupDV.size()>0) {
   %>   
  <tr class="mainbody">
  <td align="left">
  <div id="showAddRule">
   <b>Apply/Skip the Rule if Order Placed by User of Group:</b><br>
   <html:select name="WORKFLOW_DETAIL_FORM" property="applyRuleGroupId">      
   <html:option value=""><app:storeMessage  key="admin.select"/></html:option>
   <% 
     for(Iterator iter=groupDV.iterator(); iter.hasNext();) {
       GroupData gD = (GroupData) iter.next();
       String name = gD.getShortDesc();
       String idS = ""+gD.getGroupId();     
   %>    
       <html:option value="<%=idS%>"><%=name%></html:option>
   <% } %>
  </html:select>
  <br>
  <html:radio  name="WORKFLOW_DETAIL_FORM" property="applySkipFl" value="true"/>Apply
   &nbsp;&nbsp;&nbsp;     
  <html:radio  name="WORKFLOW_DETAIL_FORM" property="applySkipFl" value="false"/>Skip
  
  </div>
  </td>
  <td align="left">
  <div id="showAddRule">&nbsp;
  </div>
  </td>
  </tr>
<% } %>

  <!-- User groups -->
   <% 
    if(groupDV!=null && groupDV.size()>0) {
   %>   
  <tr class="mainbody">
  <td align="left">
  <div id="showAddRule">
   <b>Approvers Group:</b>
   <html:select name="WORKFLOW_DETAIL_FORM" property="approverGroupId">      
   <html:option value=""><app:storeMessage  key="admin.select"/></html:option>
   <% 
     for(Iterator iter=groupDV.iterator(); iter.hasNext();) {
       GroupData gD = (GroupData) iter.next();
       String name = gD.getShortDesc();
       String idS = ""+gD.getGroupId();     
   %>    
       <html:option value="<%=idS%>"><%=name%></html:option>
   <% } %>
  </html:select>
  <br>
  <%
    UserDataVector approvers = theForm.getApproverUsers();
    if(approvers!=null) {
  %>
    <b>Users:</b>
  <% 
    for(Iterator iter=approvers.iterator(); iter.hasNext();) {
    UserData uD = (UserData) iter.next();
  %>    
   <br><%=uD.getLastName()%>,&nbsp;<%=uD.getFirstName()%>&nbsp;(<%=uD.getUserName()%>) - <%=uD.getUserTypeCd()%>
  <% } %>
  <% } %>
  </div>
  </td>
  <td align="left">
  <div id="showAddRule">
   <b>Email User Group:</b><br>(Sends emails to users of the group instead of approvers)
   <html:select name="WORKFLOW_DETAIL_FORM" property="emailGroupId">      
   <html:option value=""><app:storeMessage  key="admin.select"/></html:option>
   <% 
     for(Iterator iter=groupDV.iterator(); iter.hasNext();) {
       GroupData gD = (GroupData) iter.next();
       String name = gD.getShortDesc();
       String idS = ""+gD.getGroupId();     
   %>    
       <html:option value="<%=idS%>"><%=name%></html:option>
   <% } %>
  </html:select>
  <br>
  <%
    UserDataVector emailUsers = theForm.getEmailUsers();
    if(emailUsers!=null) {
  %>
    <b>Users:</b>
  <% 
    for(Iterator iter=emailUsers.iterator(); iter.hasNext();) {
    UserData uD = (UserData) iter.next();
  %>    
   <br><%=uD.getLastName()%>,&nbsp;<%=uD.getFirstName()%>&nbsp;(<%=uD.getUserName()%>) - <%=uD.getUserTypeCd()%>
  <% } %>
  <% } %>
  </div>
  </td>
  </tr>
<% } %>
  <tr class="mainbody">
  <td colspan=2 align="center">
  <div id="showAddRule">
  <html:submit property="action" value="Save Rule" />
  </div>
  </td>
  </tr>
<% } %>
