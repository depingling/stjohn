<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="org.apache.struts.action.ActionErrors" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<app:checkLogon/>

<script language="JavaScript1.2">
    <!--
    function getHref(href, i) {
        try {
            var elArray = document.forms[0].elements['format_' + i];
            var format = '';
            for (var j = 0; j < elArray.length; j++) {
                if (elArray[j].checked) {
                    format = elArray[j].value;
                    break;
                }
            }
            window.location = href + "&format=" + format;
        } catch(e) {
            window.location = href;
        }
    }

    function setAndSubmit(fid, vv, value) {
        var pNum = document.forms['ANALYTIC_REPORT_FORM_ID'].elements['genericReportPageNum'].value
        window.location = "analyticRep.do?action=nextSheet&genericReportPageNum=" + pNum;
    }
    //-->
</script>

<% String storeDir=ClwCustomizer.getStoreDir(); %>
<% String onKeyPress="return submitenter(this,event,'Submit');"; %>
<% boolean showParamFl = false; 
   boolean mayShareFl = false;
   boolean mayProtectFl = false;
   boolean mayDeleteFl = false;
   boolean showAllReportsFl = false;
   boolean showRequesterFl = false, showFilterFl = true, showCategoryFl = false;
   boolean showErrorFl = false;
  CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
  int userId = appUser.getUser().getUserId();
  String userType = appUser.getUser().getUserTypeCd();
    if(RefCodeNames.USER_TYPE_CD.ADMINISTRATOR.equals(userType)
            ||RefCodeNames.USER_TYPE_CD.CRC_MANAGER.equals(userType)
            ||RefCodeNames.USER_TYPE_CD.CUSTOMER_SERVICE.equals(userType)
            ||RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(userType))
    {
        if(RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(userType))
        {
            showAllReportsFl = true;
        }
        showParamFl = true;
        mayShareFl = true;
        mayProtectFl = true;
        mayDeleteFl = true;
        showRequesterFl = true;
        showErrorFl = true;
        showCategoryFl = true;
    }
    else {
        showFilterFl = false;
    }
%>

<html:form styleId="ANALYTIC_REPORT_FORM_ID" name="ANALYTIC_REPORT_FORM" action="reporting/analyticRep"
    scope="session" type="com.cleanwise.view.forms.AnalyticReportForm">

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="theForm" name="ANALYTIC_REPORT_FORM" 
  type="com.cleanwise.view.forms.AnalyticReportForm"/>

<% if (showFilterFl){ %>
<table cellpadding="0" cellspacing="0" border="0" width="750">
 <tbody>
<!-- -->
  <tr> 
   <td colspan="2" class="subheader"><b>Filters<b></td>
  </tr>
  <tr> 
  <td><b>Category:</b>
  <html:text name="ANALYTIC_REPORT_FORM" property="archCategoryFilter"/></td>
  <td><b>Report Name:</b>
  <html:text name="ANALYTIC_REPORT_FORM" property="archReportFilter"/></td>
  </tr>
  <!--
  <tr> 
  <td><b>Min Date</b> (mm/dd/yyyy):
  <html:text name="ANALYTIC_REPORT_FORM" property="archMinDateFilter"/></td>
  <td><b>Max Date</b> (mm/dd/yyyy):
  <html:text name="ANALYTIC_REPORT_FORM" property="archMaxDateFilter"/></td>
  </tr>
  -->
  <% if(showAllReportsFl) {%>
  <tr> 
  <td>
<html:checkbox name="ANALYTIC_REPORT_FORM" property="myReportsFl"/>
<b>My reports only.</b>
</td>
  <td>&nbsp;</td>
  </tr>
  <% } %>
  <tr>
  <td>
  <html:hidden property="setFilter" value="" />
  <button onclick="document.forms[0].setFilter.value='setArchiveFilter'; document.forms[0].submit();">Search</button>
  </td>
</tr>

</tbody>
</table>
<% } %>
<table cellpadding="3" cellspacing="0" border="0" width="750">
<% PreparedReportViewVector preparedReports = theForm.getPreparedReports();
   int prepQty = 0; 
   if(preparedReports!=null) prepQty = preparedReports.size();
%>
<tr>
<% int colQty = 2; %>
<% if (showCategoryFl) { colQty++; %>
<td class="rptmid_blue"><a class="rptmid_blue" href="analyticRep.do?action=prepared&field=Category"><b>Category<b></a></td>
<% } %>
<td class="rptmid_blue"><a class="rptmid_blue" href="analyticRep.do?action=prepared&field=Report"><b>Report Name<b></a></td>
<td class="rptmid_blue">Excel/Pdf/Html</td>
<% if(showParamFl) {
 colQty++; 
%>
<td  class="rptmid_blue"><b>Parameters</b></td>
<% } %>
<% 
if(showRequesterFl) { 
 colQty++; 
%>
<td  class="rptmid_blue"><a  class="rptmid_blue" href="analyticRep.do?action=prepared&field=User"><b>User<b></a></td>
<% } %>
<td  class="rptmid_blue"><a class="rptmid_blue" href="analyticRep.do?action=prepared&field=Date"><b>Generated Date<b></a></td>
<% colQty++; %>
<td  class="rptmid_blue"><a class="rptmid_blue" href="analyticRep.do?action=prepared&field=Status"><b>Status<b></a></td>
<% colQty++; %>

<% if(mayShareFl) { 
 colQty++; 
%>
<td  class="rptmid_blue"><a class="rptmid_blue"><b>Share<b></td>
<% } %>
<%  
 colQty++; 
%>
<% if(mayProtectFl) {  
colQty++; %><td  class="rptmid_blue"><b>Protect<b></td> <% 
} %>

<%  
 colQty++; 
%>
<td  class="rptmid_blue"><b>Select<b></td>
</tr>
<%
   int counter = 0;
   SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");
   for(int ii=0; ii<prepQty; ii++) {
    PreparedReportView prVw = (PreparedReportView) preparedReports.get(ii);
    int requesterId = prVw.getRequesterId();
    String resultIdS = ""+prVw.getReportResultId();
    String href = "analyticRep.do?action=prepared&id="+prVw.getReportResultId();
    Date date = prVw.getReportDate(); 
    String dateS = (date==null)?"":sdf.format(date);
    
%>
<% if ((counter % 2) == 0) { %>
<tr>
<% } else { %>
<tr class="rowa">
<% } %>
<% if (showCategoryFl) { %>
<td><%=prVw.getReportCategory()%></td>
<% } %>

<% if(showErrorFl ||
     !RefCodeNames.REPORT_RESULT_STATUS_CD.FAILED.equals(prVw.getReportResultStatusCd())) { %>
<td><A href="#" onclick="getHref('<%=href%>',<%=ii%>)"><%=prVw.getReportName()%></A></td>
<td>
    <table>
        <tr>
            <td><input id="format_<%=ii%>_excel" type="radio" name="format_<%=ii%>" value="<%=Constants.REPORT_FORMAT.EXCEL%>" checked="yes"/></td>
            <td><input id="format_<%=ii%>_pdf" type="radio" name="format_<%=ii%>" value="<%=Constants.REPORT_FORMAT.PDF%>"/></td>
            <td><input id="format_<%=ii%>_html" type="radio" name="format_<%=ii%>" value="<%=Constants.REPORT_FORMAT.HTML%>"/></td>
        </tr>
   </table>
</td>
<% } else { %>
<td><%=prVw.getReportName()%></td>
<td></td>
<%}%>
<% if(showParamFl) { %>
<td><%=prVw.getReportParameters()%></td>
<% } %>
<% if(showRequesterFl) { %>
<td><%=prVw.getRequesterName()%></td>
<% } %>
<td><%=dateS%></td>
<td>
<% if(RefCodeNames.REPORT_RESULT_STATUS_CD.FAILED.equals(prVw.getReportResultStatusCd())) { %>
 <%=prVw.getReportResultStatusCd()%>
<% } else if(prVw.getReadFl() ) { %>
  Viewed
<% } else {%>
 <%=prVw.getReportResultStatusCd()%>
<% } %>
</td>
<% if(mayShareFl) {
  String shareRef = "shareRep.do?action=init&id="+prVw.getReportResultId();
%>
<td><A href="<%=shareRef%>">[X]</A></td></td>
<% } %>
<% if(mayProtectFl) {
  String protectedVal = prVw.getProtectedFl();
  if(!"Y".equals(protectedVal)) protectedVal="N";
  String newProtectedVal = ("Y".equalsIgnoreCase(protectedVal))?"N":"Y";
  String protectRef = "analyticRep.do?action=protect&id="+prVw.getReportResultId()+"&val="+newProtectedVal;
%>
<td><A href="<%=protectRef%>">[<%=protectedVal%>]</A></td></td>
<% } %>
<% if(mayDeleteFl || requesterId==userId) {
  counter++;
%>
<td><html:multibox name="ANALYTIC_REPORT_FORM" property="resultSelected" value="<%=resultIdS%>" /></td>
<% } %>

</tr>
<%}%>
<% if(counter>0){%>
<tr>
<td colspan="<%=colQty%>" align="right">
  <html:submit styleClass="store_fb" property="action" 
    value="Delete Selected Results"/>
</td>
</tr>
<% } %>
</table>
<html:hidden property="genericReportPageNum"/>

</html:form>





