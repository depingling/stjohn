<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="Location" value="contract" type="java.lang.String" toScope="session"/>
<bean:define id="theForm" name="REPORT_LOCATE_FORM" type="com.cleanwise.view.forms.ReportLocateForm"/>
<%

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

<div class="text">
<table>
<html:form name="REPORT_LOCATE_FORM" action="reporting/analyticRep"
    scope="session" type="com.cleanwise.view.forms.ReportLocateForm">
 <tbody>
<input type="hidden" name="feedField" value="<%=feedField%>">
<input type="hidden" name="feedDesc" value="<%=feedDesc%>">
<input type="hidden" name="submitFl" value="<%=submitFl%>">

  <tr> 
  <td><b>Category Filter:</b>
  <html:text name="REPORT_LOCATE_FORM" property="categoryFilter"/></td>
  </tr>
  <tr>
  <td><b>3 Report Filter:</b>
  <html:text name="REPORT_LOCATE_FORM" property="reportFilter"/></td>
  </tr>
  <tr>
  <td>
  <html:submit property="action" value="Set Filter"/>
  <html:submit property="action" value="Clear Filter"/>
  </td>
</tr>

</tbody></html:form>
</table>
<table cellpadding="0" cellspacing="0" border="0" width="769" class="mainbody">
<tr>
<% 
String hrefIdSort = 
   "reportLocate.do?action=sort&field=id&feedField="+feedField+"&feedDesc="+feedDesc+"&submitFl="+submitFl;
String hrefCategorySort = 
   "reportLocate.do?action=sort&field=category&feedField="+feedField+"&feedDesc="+feedDesc+"&submitFl="+submitFl;
String hrefReportSort = 
   "reportLocate.do?action=sort&field=report&feedField="+feedField+"&feedDesc="+feedDesc+"&submitFl="+submitFl;
%>
<td><a class="tableheader" href="<%=hrefIdSort%>"><b>Id<b></td>
<td><a class="tableheader" href="<%=hrefCategorySort%>"><b>Category<b></td>
<td><a class="tableheader" href="<%=hrefReportSort%>"><b>Report<b></td>
</tr>
<% GenericReportViewVector reports = theForm.getFilteredReports();
   for(int ii=0; ii<reports.size(); ii++) {
    GenericReportView grVw = (GenericReportView) reports.get(ii);
%>
<% if(ii%2==0) { %>
<tr class="rowb">
<% }else{ %>
<tr class="rowb">
<% } %>
<td><%=grVw.getGenericReportId()%></td>
<td><%=grVw.getReportCategory()%></td>
 <%
 String reportName = grVw.getReportName();
 reportName = reportName.replaceAll("'", "-");
  
 String onClick = new String("return passIdAndName(\""+grVw.getGenericReportId()+"\",\""+reportName+"\");");%>
<td><A href="javascript:void(0);" onclick='<%=onClick%>'><%=grVw.getReportName()%></a></td>
</tr>
<%}%>
</table>

</div>
