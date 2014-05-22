<!-- bean:define id="theForm" name="STORE_ADMIN_USER_FORM" type="com.cleanwise.view.forms.StoreUserMgrForm"/ -->
<%
	System.out
			.println("storeUserRightsConfig uuuuuuuuuuuuuuuusssssssss key33: "
					+ key);
%>
<!-- 000 start of user rights -->
<table ID=1442 class="a_tab1" cellspacing="0" width='750'>
	<tr>
		<td class="a_td1">
		<%
			String prop = "confPermForm[" + key + "].onAccount";
		%> <html:checkbox
			name="STORE_ADMIN_USER_FORM" property="<%=prop%>" /> <b>On
		Account</b> <br>
		<%
			prop = "confPermForm[" + key + "].creditCard";
		%> <html:checkbox
			name="STORE_ADMIN_USER_FORM" property="<%=prop%>" /> <b>Credit
		Card </b> <br>
		<%
			prop = "confPermForm[" + key + "].otherPayment";
		%> <html:checkbox
			name="STORE_ADMIN_USER_FORM" property="<%=prop%>" /> <b>Other
		Payment </b> <br>
		<%
			prop = "confPermForm[" + key + "].poNumRequired";
		%> <html:checkbox
			name="STORE_ADMIN_USER_FORM" property="<%=prop%>" /> <b>PO#
		Required</b> <br>
		<%
			prop = "confPermForm[" + key + "].showPrice";
		%> <html:checkbox
			name="STORE_ADMIN_USER_FORM" property="<%=prop%>" /> <b>Show
		Price</b> <br>
		<%
			prop = "confPermForm[" + key + "].contractItemsOnly";
		%> <html:checkbox
			name="STORE_ADMIN_USER_FORM" property="<%=prop%>" /> <b>Contract
		Items Only</b><br>
		
		<logic:equal name="STORE_ADMIN_USER_FORM" property="detail.userData.userTypeCd" value="<%=RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR%>">
			<html:checkbox name="STORE_ADMIN_USER_FORM" property="restrictAcctInvoices"/>
			<b>Restrict User to Invoices of Accounts</b>
		</logic:equal>	
		</td>

		<td class="a_td1">
		<%
			prop = "confPermForm[" + key + "].browseOnly";
		%> <html:checkbox
			name="STORE_ADMIN_USER_FORM" property="<%=prop%>" /> <b>Browse
		Only</b> <br>
		<!--<%
			prop = "confPermForm[" + key + "].salesPresentationOnly";
		%> <html:checkbox
			name="STORE_ADMIN_USER_FORM" property="<%=prop%>" /> <b>Sales
		Presentation Only</b> <br>
		--><%
			prop = "confPermForm[" + key + "].noReporting";
		%> <html:checkbox
			name="STORE_ADMIN_USER_FORM" property="<%=prop%>" /> <b>No
		Reporting</b> 
		<!-- aj added start --> 
		<logic:equal
			name="STORE_ADMIN_USER_FORM" property="detail.userData.userTypeCd"
			value="REPORTING_USER">
			<br>
			<%
				prop = "confPermForm[" + key + "].reportingManager";
			%>
			<html:checkbox name="STORE_ADMIN_USER_FORM" property="<%=prop%>" />
			<b>Reporting Manager</b>
			<br>
			<%
				prop = "confPermForm[" + key + "].reportingAssignAllAccts";
			%>
			<html:checkbox name="STORE_ADMIN_USER_FORM" property="<%=prop%>" />
			<b>Reporting - Assign All Accts</b>
		</logic:equal> 
		<!-- aj added end -->
		</td>
		<td class="a_td1">
		<%
			prop = "confPermForm[" + key + "].canApproveOrders";
		%> <%
 	boolean disabled = (key.intValue() != 0); // not basic permissions
 %>
		<html:checkbox name="STORE_ADMIN_USER_FORM" property="<%=prop%>"
			disabled="<%=disabled%>" /> <b>Can Approve Orders</b> <br>
		<br>
		<b>Order Notification Email </b><br>
		Send an email when an order belonging to a site for this user:<br>
		<br>
		<%
			prop = "confPermForm[" + key + "].orderDetailNotificationEmail";
		%> <html:checkbox
			name="STORE_ADMIN_USER_FORM" property="<%=prop%>" />Order Detail
		Notification <br>
		<%
			prop = "confPermForm[" + key + "].orderNotificationShippedEmail";
		%> <html:checkbox
			name="STORE_ADMIN_USER_FORM" property="<%=prop%>" />Shipping
		Notification <br>
		<%
			prop = "confPermForm[" + key
					+ "].orderNotificationNeedsApprovalEmail";
		%>
		<html:checkbox name="STORE_ADMIN_USER_FORM" property="<%=prop%>" />Needs
		Approval <br>
		<%
			prop = "confPermForm[" + key + "].orderNotificationApprovedEmail";
		%> <html:checkbox
			name="STORE_ADMIN_USER_FORM" property="<%=prop%>" />Was Approved <%
 	prop = "confPermForm[" + key + "].orderNotificationRejectedEmail";
 %>
		<html:checkbox name="STORE_ADMIN_USER_FORM" property="<%=prop%>" />Was
		Rejected <%
			prop = "confPermForm[" + key + "].orderNotificationModifiedEmail";
		%>
		<html:checkbox name="STORE_ADMIN_USER_FORM" property="<%=prop%>" />Was
		Modified
        <br>
        <br>
        <b>Work Order Notification</b><br>
            <% prop = "confPermForm[" + key + "].workOrderCompletedNotification";%>
        <html:checkbox name="STORE_ADMIN_USER_FORM" property="<%=prop%>" />Completed
            Notification<br>
            <% prop = "confPermForm[" + key + "].workOrderAcceptedByProviderNotification";%>
        <html:checkbox name="STORE_ADMIN_USER_FORM" property="<%=prop%>" />Accepted
            By Provider Notification<br>
            <% prop = "confPermForm[" + key + "].workOrderRejectedByProviderNotification";%>
        <html:checkbox name="STORE_ADMIN_USER_FORM" property="<%=prop%>" />Rejected
            By Provider Notification
        </td>
		<td class="a_td1">
		<%
			prop = "confPermForm[" + key + "].canEditShipTo";
		%> <html:checkbox
			name="STORE_ADMIN_USER_FORM" property="<%=prop%>" /> Can edit ship to
		information. <br>
		<%
			prop = "confPermForm[" + key + "].canEditBillTo";
		%> <html:checkbox
			name="STORE_ADMIN_USER_FORM" property="<%=prop%>" /> Can edit bill to
		information.
		<br><br>
        <b>Corporate Order Notification</b><br>
            <% prop = "confPermForm[" + key + "].cutoffTimeReminderEmail";%>
        <html:checkbox name="STORE_ADMIN_USER_FORM" property="<%=prop%>" />Cutoff Time Reminder<br>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Number of Times:&nbsp;&nbsp;
        <%
			prop = "confPermForm[" + key + "].cutoffTimeReminderEmailCount";
		%><html:text name="STORE_ADMIN_USER_FORM" property="<%=prop%>" maxlength="2" size="2"/><br>
            <% prop = "confPermForm[" + key + "].physicalInvNonComplSiteListingEmail";%>
        <html:checkbox name="STORE_ADMIN_USER_FORM" property="<%=prop%>" />Physical Inventory Non-Compliant Site Listing<br>
            <% prop = "confPermForm[" + key + "].physicalInvCountsPastDueEmail";%>
        <html:checkbox name="STORE_ADMIN_USER_FORM" property="<%=prop%>" />Physical Inventory Counts Past Due</td>
		
	</tr>



</table>

