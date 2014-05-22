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
<bean:define id="theForm" name="SPENDING_ESTIMATOR_FORM" type="com.cleanwise.view.forms.SpendingEstimatorForm"/>
<bean:define id="theUserCd" name="<%=Constants.APP_USER%>" 
  property="user.userTypeCd"  type="java.lang.String"/>
<%
boolean showCategories = false;
if (
   RefCodeNames.USER_TYPE_CD.ADMINISTRATOR.equals(theUserCd) )
{
showCategories = true;
}
%>


<!-- SSS -->
<table cellpadding="0" cellspacing="0" width="750">
<% 
Object errors =request.getAttribute("org.apache.struts.action.ERROR");
   if(errors!=null) { 
%>
  <tr> <td align="center"><font color=red><html:errors/></font></td></tr>
<% } %>


<% GenericReportViewVector reports = theForm.getReports();
if (reports!=null && reports.size()>0) {
%>
	
<tr>
<td  class="aton" align=center>
<!-- <a href="analyticRep.do?action=sort&field=report"><b>Report Name<b></a> -->
<b>Report Name<b>
</td>
<td class="aton" align=center><b>Report Description<b></td>
</tr>
<% 
   for(int ii=0; ii<reports.size(); ii++) {
    GenericReportView grVw = (GenericReportView) reports.get(ii);
    String href = "estimator.do?action=reportRequest&reportId=" + grVw.getGenericReportId()+"&selectedPage=9";
    String longDesc = grVw.getLongDesc();
    if(longDesc==null) longDesc="";
%>
<tr>
<td><A href="<%=href%>"><%=grVw.getReportName()%></A></td>
<td><%=longDesc%></td>
</tr>
<%

} // End of loop for all reports.
%>

<% } else { %>

<tr>
<td><b>No Reports Available<b></td>
</tr>
<%}%>
<tr><td>&nbsp;</td></tr>
</table>

<% for ( int j = reports.size(); j < 10 && j > 0; j--) {%>
<br>
<% } %>



