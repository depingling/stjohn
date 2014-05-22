
<!-- 000 start of user account rights -->

<table class="a_tab1" cellspacing="0" width='750'>
<tr>
<td colspan=3>
<b><bean:write name="arrele" property="accountData.shortDesc"/></b>
</td>
</tr>
<tr>

<% String propname = "userRightsForm[" + userRightsIdx + "].onAccount"; %>

<td class="a_td1">
<html:checkbox name="USER_DETAIL_FORM" 
  property="<%=propname%>" />
<b>On Account</b>
<br>
<% propname = "userRightsForm[" + userRightsIdx + "].creditCard"; %>
<html:checkbox name="USER_DETAIL_FORM" 
  property="<%=propname%>" />
<b>Credit Card </b>
<br>
<% propname = "userRightsForm[" + userRightsIdx + "].otherPayment"; %>
<html:checkbox name="USER_DETAIL_FORM" 
  property="<%=propname%>" />
<b>Other Payment </b>
<br>
<% propname = "userRightsForm[" + userRightsIdx + "].poNumRequired"; %>
<html:checkbox name="USER_DETAIL_FORM" 
  property="<%=propname%>" />
<b>PO# Required</b>
<br>
<% propname = "userRightsForm[" + userRightsIdx + "].showPrice"; %>
<html:checkbox name="USER_DETAIL_FORM" 
  property="<%=propname%>" />
<b>Show Price</b>
<br>
<% propname = "userRightsForm[" + userRightsIdx + "].contractItemsOnly"; %>
<html:checkbox name="USER_DETAIL_FORM" 
  property="<%=propname%>" />
<b>Contract Items Only</b>
</td>

<td class="a_td1">
<% propname = "userRightsForm[" + userRightsIdx + "].browseOnly"; %>
<html:checkbox name="USER_DETAIL_FORM" 
  property="<%=propname%>" />
<b>Browse Only</b>
<br>
<% propname = "userRightsForm[" + userRightsIdx + "].salesPresentationOnly"; %>
<html:checkbox name="USER_DETAIL_FORM" 
  property="<%=propname%>" />
<b>Sales Presentation Only</b>
<br>
<% propname = "userRightsForm[" + userRightsIdx + "].noReporting"; %>
<html:checkbox name="USER_DETAIL_FORM" 
  property="<%=propname%>" />
<b>No Reporting</b>
</td>

<td class="a_td1">
<b>Order Notification Email
</b><br>
Send an email when an order belonging to a site for this user:<br>
<br>
<% propname = "userRightsForm[" + userRightsIdx + "].orderNotificationNeedsApprovalEmail"; %>
<html:checkbox name="USER_DETAIL_FORM" 
  property="<%=propname%>"/>
Needs Approval
<br>
<% propname = "userRightsForm[" + userRightsIdx + "].orderNotificationApprovedEmail"; %>
<html:checkbox name="USER_DETAIL_FORM" 
  property="<%=propname%>"/>
Was Approved
<% propname = "userRightsForm[" + userRightsIdx + "].orderNotificationRejectedEmail"; %>
<html:checkbox name="USER_DETAIL_FORM" 
  property="<%=propname%>"/>
Was Rejected
<% propname = "userRightsForm[" + userRightsIdx + "].orderNotificationModifiedEmail"; %>
<html:checkbox name="USER_DETAIL_FORM" 
  property="<%=propname%>"/>
Was Modified
</td>
<td class="a_td1">
<% propname = "userRightsForm[" + userRightsIdx + "].canEditShipTo"; %>
<html:checkbox name="USER_DETAIL_FORM" 
  property="<%=propname%>"/>
Can edit ship to information.
<br>
<% propname = "userRightsForm[" + userRightsIdx + "].canEditBillTo"; %>
<html:checkbox name="USER_DETAIL_FORM" 
  property="<%=propname%>"/>
Can edit bill to information.
</td>

</table>


<!-- 000 end of user account rights -->
