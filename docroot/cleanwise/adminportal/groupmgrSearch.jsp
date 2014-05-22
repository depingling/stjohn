<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%--
THIS PAGE IS USED IN MULTIPLE PLACES!
It is used (at the time of writting) in the locate popup and in the group management screen...removed it from the locate popup.  This functionality is determined
based off the url and is controlled at the page level using the mode variable
--%>
<script language="JavaScript1.2">
<!--
function actionMultiSubmit(actionDef, action) {
  var aaal = document.getElementsByName('action');
  for ( var i = 0; i < 1; i++ ) {
    var aaa = aaal[i];
    aaa.value = action;
    aaa.form.submit();
  }
 return false;
}
 //-->
</script>
 
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
<input type="hidden" name="action" value="BBBB">
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
                <html:button property="action"
					onclick="actionMultiSubmit(null, 'adminGroupSearch');" >
                        <app:storeMessage  key="global.action.label.search"/>
                </html:button>
                &nbsp;
                <html:button property="action"
					onclick="actionMultiSubmit(null, 'adminGroupViewAll');" >
                        <app:storeMessage  key="admin.button.viewall"/>
                </html:button>
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
                <tr>
                        <td class="resultscolumna">
                                <%if (mode.equals("locate")){%>
                                        <%String onClick = new String("return passIdAndName('"+key+"','"+ name+"');");%>
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
