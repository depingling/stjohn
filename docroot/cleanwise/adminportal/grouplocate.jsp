<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<app:checkLogon/>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<html:html>

<head>
<link rel="stylesheet" href="../externals/styles.css">
<title>Application Administrator Home: Select a Group</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body class="results">




<script language="JavaScript1.2">
<!--

function passIdAndName(id, name) {
  var feedBackFieldName = document.GROUP_FORM.feedField.value;
  var feedDesc = document.GROUP_FORM.feedDesc.value;
  var submitFl = document.GROUP_FORM.submitFl.value;

  if(feedBackFieldName && ""!= feedBackFieldName) {
    window.opener.document.forms[0].elements[feedBackFieldName].value = id;
  }
  if(feedDesc && ""!= feedDesc) {
    window.opener.document.forms[0].elements[feedDesc].value = unescape(name.replace(/\+/g, ' '));
  }
  if(submitFl && submitFl=="true") {
    window.opener.document.forms[0].submit();
  }

  self.close();
}

//-->
</script>

<%--<jsp:include flush='true' page="../adminportal/groupmgrSearch.jsp"/>--%>
 
<%
String currentURI = request.getServletPath();
String mode = "manage";
if(currentURI.indexOf("locate")>0){
        mode="locate";
}

String feedField =  (String)request.getParameter("feedField");
if(null == feedField) {
        feedField = new String("");
}
String feedDesc =  (String)request.getParameter("feedDesc");
if(null == feedDesc) {
        feedDesc = new String("");
}
String locateFilter =  (String)request.getParameter("locateFilter");
if(null == locateFilter) {
        locateFilter = new String("");
}

String submitFl =  (String)request.getParameter("submitFl");
if(null == submitFl) {
        submitFl = new String("false");
}
%>


<app:checkLogon/>

<%if (mode.equals("locate")){%>
        <table>
<%}else{%>
        <table border="0" cellpadding="1" cellspacing="0" width="769" class="mainbody">
<%}%>
<html:form name="GROUP_FORM" action="<%=currentURI%>" type="com.cleanwise.view.forms.GroupForm">
<input type="hidden" name="feedField" value="<%=feedField%>">
<input type="hidden" name="feedDesc" value="<%=feedDesc%>">
<input type="hidden" name="locateFilter" value="<%=locateFilter%>">
<input type="hidden" name="submitFl" value="<%=submitFl%>">
        <tr>
                <td><b>Search Groups:</b></td>
                <td><html:text name="GROUP_FORM" property="groupName" size="30"/></td>
        </tr>
        <tr>
                <td><b>Group Type:</b></td>
                <td>
                        <html:select name="GROUP_FORM" property="groupType">
                                <html:option value=""><app:storeMessage  key="admin.select"/></html:option>
                                <html:options collection="group.type.vector" property="value"/>
                        </html:select>
                </td>
        </tr>
        <tr><td align="center">
                <html:submit property="action">
                        <app:storeMessage  key="global.action.label.search"/>
                </html:submit>
                &nbsp;
                <html:submit property="action">
                        <app:storeMessage  key="admin.button.viewall"/>
                </html:submit>
                <%if (mode.equals("manage")){%>
                &nbsp;
                <html:submit property="action">
                        <app:storeMessage  key="admin.button.create"/>
                </html:submit>
                <%}%>
        </td></tr>
</html:form>
</table>
<table cellpadding="2" cellspacing="0" border="0" width="769" class="results">
        <logic:present name="Groups.found.vector">
                <bean:size id="rescount"  name="Groups.found.vector"/>
                <tr>
                        <td>Search result count:  <bean:write name="rescount" /></td>
                </tr>
                <tr>
                        <td class="resultscolumna">Group Name</td>
                        <td>Group Type</td>
                        <td class="resultscolumna">Status</td>
                </tr>
                <logic:iterate id="grp" name="Groups.found.vector">
                <bean:define id="key"  name="grp" property="groupId"/>
                <bean:define id="name"  name="grp" property="shortDesc"/>
                <script type="text/javascript">
                     var name<%=key%> = "<%=name%>";
                </script>
                <tr>
                        <td class="resultscolumna">
                                <%if (mode.equals("locate")){%>
                                        <%String onClick = new String("return passIdAndName('"+key+"',name"+key+");");%>
                                        <a href="javascript:void(0);" onclick="<%=onClick%>"><bean:write name="grp" property="shortDesc"/></a>
                                <%}else{%>
                                        <%String linkHref="groupmgr.do?action=viewDetail&groupId=" + key;%>
                                        <a href=<%=linkHref%>><bean:write name="grp" property="shortDesc"/></a>
                                <%}%>
                        </td>
                        <td><bean:write name="grp" property="groupTypeCd"/></td>
                        <td class="resultscolumna"><bean:write name="grp" property="groupStatusCd"/></td>
                </tr>
                </logic:iterate>
        </logic:present>
</table>
<jsp:include flush='true' page="ui/admFooter.jsp"/>

</body>

</html:html>




