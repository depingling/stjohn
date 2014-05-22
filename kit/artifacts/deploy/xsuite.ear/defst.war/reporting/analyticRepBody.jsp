<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.*" %>
<%@ page import="org.apache.struts.action.ActionErrors" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.ArrayList" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>
<% String storeDir=ClwCustomizer.getStoreDir(); %>
<% String onKeyPress="return submitenter(this,event,'Submit');"; %>
<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="theForm" name="ANALYTIC_REPORT_FORM"
  type="com.cleanwise.view.forms.AnalyticReportForm"/>
<bean:define id="theUserCd" name="<%=Constants.APP_USER%>"
  property="user.userTypeCd"  type="java.lang.String"/>
<%
boolean showCategories = false;
if (
   RefCodeNames.USER_TYPE_CD.ADMINISTRATOR.equals(theUserCd) ||
   RefCodeNames.USER_TYPE_CD.REPORTING_USER.equals(theUserCd))
{
showCategories = true;
}
%>

<script language="JavaScript1.2">
<!--

<%--
submits the form when tehre are multiple buttons present (locate buttons)
--%>
function submitSearch(){
	document.forms[0].setFilter.value='setFilter'; document.forms[0].submit();
}
function keyDown(e)
{
    var keycode = document.all ? event.keyCode : e.which;
    var realkey = String.fromCharCode(keycode)
    if ((""+keycode)=="13")
    {
       submitSearch();
    }
}

document.onkeydown = keyDown;
if ( document.layers )
{
  document.captureEvents(Event.KEYDOWN);
}


//-->
</script>

<!-- SSS -->
<table cellpadding="0" cellspacing="0" width="750">
<%
Object errors =request.getAttribute("org.apache.struts.action.ERROR");
   if(errors!=null) {
%>
  <tr> <td align="center"><font color=red><html:errors/></font></td></tr>
<% } %>

<!-- action="reporting/analyticRep"-->
<% if(theForm.getShowFiltesFl()) { %>
  <tr>
<html:form name="ANALYTIC_REPORT_FORM" action="reporting/analyticRep"
    scope="session" type="com.cleanwise.view.forms.AnalyticReportForm">

  <td colspan="2"><b>Category:</b>
  <html:hidden property="setFilter" value="" />
  <html:select name="ANALYTIC_REPORT_FORM" property="categoryFilter"
      onchange="document.forms[0].setFilter.value='setFilter'; document.forms[0].submit();">
  <html:option value=""/>
  <html:options name="ANALYTIC_REPORT_FORM" property="categories" />
 </html:select>
 </td>
  </tr>
  <tr>
  <td align=center><b>Report Name:</b>
  <html:text name="ANALYTIC_REPORT_FORM" property="reportFilter"/></td>
  <td align=center><b>Report Description:</b>
  <html:text name="ANALYTIC_REPORT_FORM" property="reportDescFilter"/></td>
  </tr>
  <tr>
  <td>
  <button onclick="submitSearch();">Search</button>
  <button onclick="document.forms[0].setFilter.value='clearFilter'; document.forms[0].submit();">View All</button>
  </td>
</tr>
</html:form>
<% } %>

<% GenericReportViewVector reports = theForm.getFilteredReports();
if (reports.size()>0) {
%>
<%/*
  <tr>
<td colspan="2" align="left" class='mainbody'>
<jsp:include flush='true' page="f_reportingMsg.jsp"/>
</td>
  </tr>
*/%>
<tr>
<% if (showCategories) { %>
<td  class="aton" align=center><b>Category</b></td>
<% } %>

<td  class="aton" align=center>
<!-- <a href="analyticRep.do?action=sort&field=report"><b>Report Name<b></a> -->
<b>Report Name<b>
</td>
<td class="aton" align=center><b>Report Description<b></td>
</tr>
<%
   for(int ii=0; ii<reports.size(); ii++) {
    GenericReportView grVw = (GenericReportView) reports.get(ii);
    String href = "analyticRep.do?action=report&id="+grVw.getGenericReportId();
    String longDesc = grVw.getLongDesc();
    if(longDesc==null) longDesc="";
    //if(longDesc.length()>50) longDesc = longDesc.substring(0,50);
%>
<% if ( ( ii % 2 ) == 0 ) { %>
<tr>
<% } else { %>
<tr class="rowa">
<% } %>
<% if (showCategories) { %>
<td><%=grVw.getReportCategory()%></td>
<%  } %>
<td><A href="<%=href%>"><%=grVw.getReportName()%></A></td>
<td><%=longDesc%></td>
</tr>
<%

} // End of loop for all reports.
%>

<% } %>

<% GenericReportViewVector allReports = theForm.getReports();
 if(allReports.size()==0) {%>
<tr>
<td><b>No Reports Available<b></td>
</tr>
<%}%>
<tr><td>&nbsp;</td></tr>
</table>

<% for ( int j = reports.size(); j < 10 && j > 0; j--) {%>
<br>
<% } %>



