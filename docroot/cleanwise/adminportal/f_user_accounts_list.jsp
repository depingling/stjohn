
<!-- 000 start of user accounts list -->

<% if (true) { %>
<bean:size id="v_userAccountsSize"  name="USER_DETAIL_FORM" 
  property="detail.userAccountRights" />
<logic:greaterThan name="v_userAccountsSize" value="0">
<b>User Accounts</b>
<table>
<logic:iterate id="arrele" name="USER_DETAIL_FORM" 
  property="rightsForms"
  type="UserRightsForm">
  
  <tr>
  
<% 
String rmaccturl = "userSiteConfig.do?tab=acctConfig"
+ "&action=rmAccount&userId=" + arrele.getUserData().getUserId()
+ "&accountId=" + arrele.getAccountData().getBusEntityId(); 
String requrl = (String)request.getRequestURI();
String onclickmsg = "return confirm('You are about to remove the "
 + arrele.getAccountData().getShortDesc() + " account from this user. "
 + "  Are you sure this is what you want to do?');";
 
if (requrl.indexOf("userSiteConfig") > 0 ) {
%>
<td><a href="<%=rmaccturl%>"  onclick="<%=onclickmsg%>" >
  <img src="../en/images/button_x.gif">  </a>  </td>
<% } %>

<td><bean:write name="arrele" property="accountData.shortDesc"/></td>
</tr>
</logic:iterate>
</table>
</logic:greaterThan>
<% } %>

<!-- 000 end of user accounts list -->
