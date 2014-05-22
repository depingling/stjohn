<%@page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@page import="com.cleanwise.service.api.value.*" %>
<%@page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@page import="java.util.*" %>
<jsp:include flush='true' page="../general/checkBrowser.jsp"/>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@taglib uri="/WEB-INF/application.tld" prefix="app" %>
<app:checkLogon/>
<% String storeDir = ClwCustomizer.getStoreDir(); %>
<% boolean clwSwitch = ClwCustomizer.getClwSwitch(); %>
<html:html>
<head>
<title>Store Administrator: Account. Contact Us Topics.</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="../externals/styles.css">
</head>
<bean:define id="theForm" name="ACCOUNT_CONTACT_US_TOPIC_MGR_FORM" type="com.cleanwise.view.forms.AccountContactUsTopicMgrForm"/>
<body bgcolor="#cccccc">
<script type="text/javascript" language="Javascript">
function delConf() {
    return confirm("Are you sure to delete this topic?");
}
</script>
<div class = "text">
<jsp:include flush='true' page="/storeportal/storeAdminNonTilesLayout.jsp"/>

<font color=red><html:errors/></font>
<table id="1549" cellspacing="0" border="0" width="769"  class="mainbody">
<tr>
<td><b>Account&nbsp;Id:</b></td>
<td><bean:write name="ACCOUNT_CONTACT_US_TOPIC_MGR_FORM" property="accountId"/></td>
 <td><b>Name:</b></td>
<td>
<td><bean:write name="ACCOUNT_CONTACT_US_TOPIC_MGR_FORM" property="accountName"/></td>
</td>
<html:form styleId="1550" action="/storeportal/accountmgrContactUsTopic.do">
<html:hidden name="ACCOUNT_CONTACT_US_TOPIC_MGR_FORM" property="accountId"/>
</tr>
</table>
<table id="1551" cellspacing="0" border="0" width="769"  class="results">
<% 
    PropertyData topicToEdit = theForm.getTopicToEdit();
    int topicToEditId = 0;
    if(topicToEdit != null) {
        topicToEditId = topicToEdit.getPropertyId();
    }
%>
<logic:notEmpty name="ACCOUNT_CONTACT_US_TOPIC_MGR_FORM" property="topics">
<tr>
<td class="tableheader">Select Account Contact Us Topic</td>
<td>&nbsp;</td>
</tr>
<logic:iterate name="ACCOUNT_CONTACT_US_TOPIC_MGR_FORM" property="topics" id="topic" type="com.cleanwise.service.api.value.PropertyData">
<% 
    int topicId = topic.getPropertyId();
    if(topicId != topicToEditId) {
        String delLink = "accountmgrContactUsTopic.do?action=deleteTopic&topicId=" + topic.getPropertyId();
        String editLink = "accountmgrContactUsTopic.do?action=editTopic&topicToEditId=" + topic.getPropertyId();
%>
<tr>
<td><bean:write name="topic" property="value"/></td>
<td><bean:write name="topic" property="localeCd"/></td>
<td><a id="1553" href="<%=editLink%>">[Edit]</a>&nbsp;<a ID=1554 href="<%=delLink%>" onclick="return delConf();">[Delete]</a></td>
</tr><%
    } else {
%><tr>
<td width="1%"><html:text name="ACCOUNT_CONTACT_US_TOPIC_MGR_FORM" property="topicToEdit.value" size="70" maxlength="1000"/></td>
<td><html:select name="ACCOUNT_CONTACT_US_TOPIC_MGR_FORM" property="topicToEdit.localeCd" >
<html:option value='<%=""%>'>Default</html:option><%
for(java.util.Iterator iter=ClwI18nUtil.getAllLocales().iterator(); iter.hasNext();) {
  String localeCd = (String) iter.next();
%>    <html:option value="<%=localeCd%>"><%=localeCd%></html:option>
    <% } %>
</html:select></td>
<td><input type=submit name="action" class="smalltext" value="Update Topic"/> </td>
</tr><%
    }
%></logic:iterate>
</logic:notEmpty>
<tr><td width="1%">
<html:text  name="ACCOUNT_CONTACT_US_TOPIC_MGR_FORM" property="topicName" size="70" maxlength="1000"/>
</td><td>
<html:select name="ACCOUNT_CONTACT_US_TOPIC_MGR_FORM" property="localeCd" >
<html:option value='<%=""%>'>Default</html:option><%
for(java.util.Iterator iter=ClwI18nUtil.getAllLocales().iterator(); iter.hasNext();) {
  String localeCd = (String) iter.next();
%>    <html:option value="<%=localeCd%>"><%=localeCd%></html:option>
    <% } %>
</html:select></td>
<td><input type=submit name="action" class="smalltext" value="Add Topic"/></td>
</tr>
</html:form>
</table>
</div>
<jsp:include flush='true' page="ui/admFooter.jsp"/>
</body>
</html:html>