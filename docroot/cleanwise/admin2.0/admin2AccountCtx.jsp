<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ page import="com.cleanwise.view.utils.Constants"%>
<%
    String
        sid = (String)session.getAttribute("Store.id"),
        sname = (String)session.getAttribute("Store.name"),
        aid = (String)session.getAttribute("Account.id"),
        aname = (String)session.getAttribute("Account.name")
    ;
%>
    <table ID=476 border="0" cellpadding="3" cellspacing="1" width="<%=Constants.TABLEWIDTH%>" class="stpTable">
      <logic:equal name="<%=Constants.APP_USER%>" property="aAdmin" value="true">
			<logic:notEqual name="<%=Constants.APP_USER%>" property="aAccountAdmin" value="true">
      <tr>
      <td class=stpLabel ><app:storeMessage key="admin2.account.detail.label.storeId"/>:</td><td><%=sid%></td>
      <td class=stpLabel ><app:storeMessage key="admin2.account.detail.label.storeName"/>:</td><td><%=sname%></td>
      </tr>
			</logic:notEqual>
	</logic:equal>
      <tr>
      <td class=stpLabel><app:storeMessage key="admin2.account.detail.label.accountId"/>:</td><td><%=aid%></td>
      <td class=stpLabel><app:storeMessage key="admin2.account.detail.label.accountName"/>:</td><td><%=aname%></td>
      </tr>
    </table>